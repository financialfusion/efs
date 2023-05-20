package com.ffusion.services.exttransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.beans.exttransfers.ExtTransferAccounts;
import com.ffusion.beans.exttransfers.ExtTransferCompanies;
import com.ffusion.beans.exttransfers.ExtTransferCompany;
import com.ffusion.beans.util.BeansConverter;
import com.ffusion.csil.CSILException;
import com.ffusion.efs.adapters.profile.AccountAdapter;
import com.ffusion.ffs.bpw.BPWServices.BPWServices;
import com.ffusion.ffs.bpw.BPWServices.BPWServicesHome;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctList;
import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyList;
import com.ffusion.services.ExternalTransferAdmin2;
import com.ffusion.services.ExternalTransferAdminException;
import com.ffusion.services.banksim2.Base;
import com.ffusion.services.banksim2.Base.a;
import com.ffusion.util.ContextPool;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.TrackingIDGenerator;
import com.ffusion.util.settings.AccountSettings;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

public class ExternalTransferAdminService
  extends Base
  implements ExternalTransferAdmin2
{
  private static final String cS = "BPWCallBackJNDIName";
  private static final String cT = "BPWAdminCallBackJNDIName";
  private String cV = "BPWServices";
  private String cU = "bpw.BPWAdminHome";
  
  public int initialize(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      paramString = "banksim.xml";
    }
    int i = initialize(paramString, new a());
    return i;
  }
  
  public void processOFXRequest() {}
  
  protected BPWServices getBPWHandler()
    throws CSILException
  {
    BPWServices localBPWServices = null;
    ContextPool localContextPool = null;
    Context localContext = null;
    for (int i = 0; i < this.provider_url_list.size(); i++)
    {
      String str = (String)this.provider_url_list.get(i);
      try
      {
        if (debugService)
        {
          DebugLog.log(Level.INFO, "##################### Ping BPW Server ################");
          DebugLog.log(Level.INFO, "provider_url = " + str);
        }
        if (!ping(str)) {
          continue;
        }
      }
      catch (CSILException localCSILException)
      {
        continue;
      }
      try
      {
        localContextPool = getContextPool(str);
        localContext = localContextPool.getContext();
        Object localObject1 = localContext.lookup(this.cV);
        localObject2 = (BPWServicesHome)PortableRemoteObject.narrow(localObject1, BPWServicesHome.class);
        localBPWServices = ((BPWServicesHome)localObject2).create();
        localContextPool.returnContext(localContext);
        return localBPWServices;
      }
      catch (Throwable localThrowable)
      {
        Object localObject2;
        DebugLog.throwing("Couldn't get handler in BankSim service", localThrowable);
        if ((localContext != null) && (localContextPool != null)) {
          try
          {
            localContext = localContextPool.refreshContext(localContext);
            localObject2 = localContext.lookup(this.cV);
            BPWServicesHome localBPWServicesHome = (BPWServicesHome)PortableRemoteObject.narrow(localObject2, BPWServicesHome.class);
            localBPWServices = localBPWServicesHome.create();
            localContextPool.returnContext(localContext);
            return localBPWServices;
          }
          catch (Exception localException)
          {
            DebugLog.throwing("Couldn't refresh the contexts for " + str, localException);
            if ((localContextPool != null) && (localContext != null)) {
              localContextPool.returnContext(localContext);
            }
          }
        }
      }
    }
    throw new CSILException(25018);
  }
  
  protected void removeBPWHandler(BPWServices paramBPWServices)
  {
    if (paramBPWServices != null) {
      try
      {
        if (debugService) {
          DebugLog.log(Level.INFO, "##################### Removing BPW Handler ################");
        }
        paramBPWServices.remove();
      }
      catch (Throwable localThrowable)
      {
        DebugLog.throwing("Error removing bpw handler", localThrowable);
      }
    }
  }
  
  protected int mapError(int paramInt)
  {
    int i = 1;
    switch (paramInt)
    {
    case 0: 
      i = 0;
      break;
    case 2000: 
      i = 1;
    }
    return i;
  }
  
  public Accounts getAccountsForExtTransfer(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    Accounts localAccounts1 = new Accounts(paramSecureUser.getLocale());
    boolean bool = false;
    if ((paramHashMap != null) && (paramHashMap.get("isConsumer") != null)) {
      bool = Boolean.valueOf((String)paramHashMap.get("isConsumer")).booleanValue();
    }
    try
    {
      Accounts localAccounts2 = AccountAdapter.getAccountsById(paramInt);
      if ((localAccounts2 != null) && (localAccounts2.size() > 0))
      {
        Iterator localIterator = localAccounts2.iterator();
        while (localIterator.hasNext())
        {
          Account localAccount = (Account)localIterator.next();
          int i = 0;
          if ((bool) && ("2".equals(localAccount.getCoreAccount()))) {
            i = 1;
          } else if ((!bool) && ("0".equals(localAccount.getCoreAccount()))) {
            i = 1;
          }
          if (i != 0) {
            localAccounts1.add(localAccount);
          }
        }
      }
    }
    catch (Exception localException)
    {
      throw new CSILException("ExternalTransferAdminService.getaccounstsforextTransfer", 39708, localException.getMessage());
    }
    return localAccounts1;
  }
  
  public ExtTransferCompanies getExtTransferCompanies(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(25018);
    }
    int i = 0;
    String str1 = "";
    ExtTransferCompanies localExtTransferCompanies = new ExtTransferCompanies(paramSecureUser.getLocale());
    String[] arrayOfString = new String[1];
    ExtTransferCompanyList localExtTransferCompanyList = new ExtTransferCompanyList();
    try
    {
      localExtTransferCompanyList.setFIId(paramString2);
      arrayOfString[0] = paramString1;
      localExtTransferCompanyList.setCustomerId(arrayOfString);
      localExtTransferCompanyList = localBPWServices.getExtTransferCompanyList(localExtTransferCompanyList);
      i = localExtTransferCompanyList.getStatusCode();
      if (i != 0)
      {
        if (i != 16020)
        {
          DebugLog.log("Error occurred when getting External Transfer Companies:");
          DebugLog.log("*** BPW ErrorCode: " + i);
          str1 = localExtTransferCompanyList.getStatusMsg();
          DebugLog.log("*** BPW ErrorMsg: " + str1);
          throw new CSILException(39701, i, "BPW getExtransferCompanies", str1);
        }
      }
      else {
        localExtTransferCompanies = BeansConverter.getExtTransferCompanies(localExtTransferCompanyList);
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ExternalTransferAdminService.getExtTransferCompanies exception: " + localThrowable.getMessage());
      if ((localThrowable instanceof CSILException)) {
        throw ((CSILException)localThrowable);
      }
      String str2 = localThrowable.toString();
      if ((str2 != null) && (str2.indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
      throw new CSILException(39701);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    return localExtTransferCompanies;
  }
  
  public ExtTransferCompany addExtTransferCompany(SecureUser paramSecureUser, ExtTransferCompany paramExtTransferCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(25018);
    }
    int i = 0;
    String str1 = "";
    ExtTransferCompany localExtTransferCompany = null;
    ExtTransferCompanyInfo localExtTransferCompanyInfo = null;
    try
    {
      localExtTransferCompanyInfo = BeansConverter.getExtTransferCompanyInfo(paramExtTransferCompany);
      localExtTransferCompanyInfo.setFiId(paramString);
      localExtTransferCompanyInfo.setLogId(TrackingIDGenerator.GetNextID());
      localExtTransferCompanyInfo.setSubmittedBy("" + paramSecureUser.getProfileID());
      localExtTransferCompanyInfo = localBPWServices.addExtTransferCompany(localExtTransferCompanyInfo);
      i = localExtTransferCompanyInfo.getStatusCode();
      if (i != 0)
      {
        DebugLog.log("Error occurred when adding an External Transfer Company:");
        DebugLog.log("*** BPW ErrorCode: " + i);
        str1 = localExtTransferCompanyInfo.getStatusMsg();
        DebugLog.log("*** BPW ErrorMsg: " + str1);
        throw new CSILException(39700, i, "BPW addExtTransferCompany", str1);
      }
      localExtTransferCompany = BeansConverter.getExtTransferCompany(localExtTransferCompanyInfo);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ExternalTransferAdminService.addExtTransferCompany exception: " + localThrowable.getMessage());
      if ((localThrowable instanceof CSILException)) {
        throw ((CSILException)localThrowable);
      }
      String str2 = localThrowable.toString();
      if ((str2 != null) && (str2.indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
      throw new CSILException(39700);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    return localExtTransferCompany;
  }
  
  public void modifyExtTransferCompany(SecureUser paramSecureUser, ExtTransferCompany paramExtTransferCompany1, ExtTransferCompany paramExtTransferCompany2, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(25018);
    }
    int i = 0;
    String str1 = "";
    ExtTransferCompanyInfo localExtTransferCompanyInfo = null;
    try
    {
      localExtTransferCompanyInfo = BeansConverter.getExtTransferCompanyInfo(paramExtTransferCompany1);
      localExtTransferCompanyInfo.setLogId(TrackingIDGenerator.GetNextID());
      localExtTransferCompanyInfo.setSubmittedBy("" + paramSecureUser.getProfileID());
      localExtTransferCompanyInfo.setFiId(paramString);
      localExtTransferCompanyInfo = localBPWServices.modExtTransferCompany(localExtTransferCompanyInfo);
      i = localExtTransferCompanyInfo.getStatusCode();
      if (i != 0)
      {
        DebugLog.log("Error occurred when modifying an External Transfer Company:");
        DebugLog.log("*** BPW ErrorCode: " + i);
        str1 = localExtTransferCompanyInfo.getStatusMsg();
        DebugLog.log("*** BPW ErrorMsg: " + str1);
        throw new CSILException(39703, i, "BPW modifyExtTransferCompany", str1);
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ExternalTransferAdminService.modifyExtTransferCompany exception: " + localThrowable.getMessage());
      if ((localThrowable instanceof CSILException)) {
        throw ((CSILException)localThrowable);
      }
      String str2 = localThrowable.toString();
      if ((str2 != null) && (str2.indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
      throw new CSILException(39703);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
  }
  
  public void deleteExtTransferCompany(SecureUser paramSecureUser, ExtTransferCompany paramExtTransferCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(25018);
    }
    int i = 0;
    String str1 = "";
    ExtTransferCompanyInfo localExtTransferCompanyInfo = null;
    try
    {
      localExtTransferCompanyInfo = BeansConverter.getExtTransferCompanyInfo(paramExtTransferCompany);
      localExtTransferCompanyInfo = localBPWServices.canExtTransferCompany(localExtTransferCompanyInfo);
      i = localExtTransferCompanyInfo.getStatusCode();
      if (i != 0)
      {
        DebugLog.log("Error occurred when deleting an External Transfer Company:");
        DebugLog.log("*** BPW ErrorCode: " + i);
        str1 = localExtTransferCompanyInfo.getStatusMsg();
        DebugLog.log("*** BPW ErrorMsg: " + str1);
        i = mapError(localExtTransferCompanyInfo.getStatusCode());
        throw new CSILException(39704, i, "BPW deleteExtTransferCompany", str1);
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ExternalTransferAdminService.deleteExtTransferCompany exception: " + localThrowable.getMessage());
      if ((localThrowable instanceof CSILException)) {
        throw ((CSILException)localThrowable);
      }
      String str2 = localThrowable.toString();
      if ((str2 != null) && (str2.indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
      throw new CSILException(39702);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
  }
  
  public void addExtTransferAccount(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, ExtTransferCompany paramExtTransferCompany, HashMap paramHashMap)
    throws CSILException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(25018);
    }
    int i = 0;
    String str1 = "";
    ExtTransferAcctInfo localExtTransferAcctInfo = null;
    try
    {
      localExtTransferAcctInfo = BeansConverter.getExtTransferAccountInfo(paramExtTransferAccount, paramExtTransferCompany);
      localExtTransferAcctInfo.setLogId(TrackingIDGenerator.GetNextID());
      localExtTransferAcctInfo.setSubmittedBy("" + paramSecureUser.getProfileID());
      localExtTransferAcctInfo = localBPWServices.addExtTransferAccount(localExtTransferAcctInfo);
      i = localExtTransferAcctInfo.getStatusCode();
      if (i != 0)
      {
        DebugLog.log("Error occurred when adding an External Transfer Account:");
        DebugLog.log("*** BPW ErrorCode: " + i);
        str1 = localExtTransferAcctInfo.getStatusMsg();
        DebugLog.log("*** BPW ErrorMsg: " + str1);
        i = mapError(localExtTransferAcctInfo.getStatusCode());
        throw new CSILException(39704, i, "BPW addExtTransferAccount", str1);
      }
      paramExtTransferAccount.set(BeansConverter.getExtTransferAccount(paramExtTransferAccount, localExtTransferAcctInfo));
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ExternalTransferAdminService.addExtTransferAccount exception: " + localThrowable.getMessage());
      if ((localThrowable instanceof CSILException)) {
        throw ((CSILException)localThrowable);
      }
      String str2 = localThrowable.toString();
      if ((str2 != null) && (str2.indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
      throw new CSILException(39704);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
  }
  
  public void deleteExtTransferAccount(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, ExtTransferCompany paramExtTransferCompany, HashMap paramHashMap)
    throws CSILException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(25018);
    }
    int i = 0;
    String str1 = "";
    ExtTransferAcctInfo localExtTransferAcctInfo = null;
    try
    {
      localExtTransferAcctInfo = BeansConverter.getExtTransferAccountInfo(paramExtTransferAccount, paramExtTransferCompany);
      localExtTransferAcctInfo = localBPWServices.canExtTransferAccount(localExtTransferAcctInfo);
      if (localExtTransferAcctInfo.getStatusCode() != 0)
      {
        DebugLog.log("Error occurred when deleting an External Transfer Account:");
        i = localExtTransferAcctInfo.getStatusCode();
        str1 = localExtTransferAcctInfo.getStatusMsg();
        DebugLog.log("*** BPW ErrorCode: " + i);
        DebugLog.log("*** BPW ErrorMsg: " + str1);
        throw new CSILException(39706, i, "BPW deleteExtTransferAccount", str1);
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ExternalTransferAdminService.deleteExtTransferAccount exception: " + localThrowable.getMessage());
      if ((localThrowable instanceof CSILException)) {
        throw ((CSILException)localThrowable);
      }
      String str2 = localThrowable.toString();
      if ((str2 != null) && (str2.indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
      throw new CSILException(39706);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
  }
  
  public void modifyExtTransferAccount(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, ExtTransferCompany paramExtTransferCompany, HashMap paramHashMap)
    throws CSILException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(25018);
    }
    int i = 0;
    String str = "";
    ExtTransferAcctInfo localExtTransferAcctInfo = null;
    try
    {
      localExtTransferAcctInfo = BeansConverter.getExtTransferAccountInfo(paramExtTransferAccount, paramExtTransferCompany);
      localExtTransferAcctInfo.setLogId(TrackingIDGenerator.GetNextID());
      localExtTransferAcctInfo.setSubmittedBy("" + paramSecureUser.getProfileID());
      localExtTransferAcctInfo = localBPWServices.modExtTransferAccount(localExtTransferAcctInfo);
      if (localExtTransferAcctInfo.getStatusCode() != 0)
      {
        DebugLog.log("Error occurred when modifying an External Transfer Company:");
        i = localExtTransferAcctInfo.getStatusCode();
        str = localExtTransferAcctInfo.getStatusMsg();
        DebugLog.log("*** BPW ErrorCode: " + i);
        DebugLog.log("*** BPW ErrorMsg: " + str);
        throw new CSILException(39707, i, "BPW modifyExtTransferAccount", str);
      }
      if ((paramHashMap != null) && (paramHashMap.get("OriginalAccount") != null))
      {
        ExtTransferAccount localExtTransferAccount = (ExtTransferAccount)paramHashMap.get("OriginalAccount");
        if (localExtTransferAccount.getStatusValue() != paramExtTransferAccount.getStatusValue()) {
          if (paramExtTransferAccount.getStatusValue() == 1) {
            localExtTransferAcctInfo = localBPWServices.activateExtTransferAcct(localExtTransferAcctInfo);
          } else if (paramExtTransferAccount.getStatusValue() == 2) {
            localExtTransferAcctInfo = localBPWServices.inactivateExtTransferAcct(localExtTransferAcctInfo);
          }
        }
      }
      int j = Integer.parseInt(paramExtTransferCompany.getCustID());
      localObject1 = AccountAdapter.getAccountsById(j);
      Account localAccount = ((Accounts)localObject1).getByIDAndBankIDAndRoutingNum(AccountSettings.buildAccountId(paramExtTransferAccount.getNumber(), "" + paramExtTransferAccount.getTypeValue()), paramExtTransferAccount.getBankId(), paramExtTransferAccount.getRoutingNumber());
      if (localAccount != null)
      {
        localAccount.setNickName(paramExtTransferAccount.getNickname());
        localAccount.setCurrencyCode(paramExtTransferAccount.getCurrencyCode());
        AccountAdapter.modifyAccount(localAccount, j);
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ExternalTransferAdminService.modifyExtTransferAccount exception: " + localThrowable.getMessage());
      if ((localThrowable instanceof CSILException)) {
        throw ((CSILException)localThrowable);
      }
      Object localObject1 = localThrowable.toString();
      if ((localObject1 != null) && (((String)localObject1).indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
      throw new CSILException(39707);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
  }
  
  public ExtTransferAccounts getExtTransferAccounts(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, ExtTransferCompany paramExtTransferCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(25018);
    }
    int i = 0;
    String str1 = "";
    ExtTransferAccounts localExtTransferAccounts = new ExtTransferAccounts(paramSecureUser.getLocale());
    ExtTransferAcctList localExtTransferAcctList = new ExtTransferAcctList();
    try
    {
      String[] arrayOfString = new String[1];
      if ((paramExtTransferCompany.getCompanyID() != null) && (paramExtTransferCompany.getCompanyID().trim().length() > 0))
      {
        arrayOfString[0] = paramExtTransferCompany.getCompanyID();
        localExtTransferAcctList.setCompId(arrayOfString);
      }
      localObject1 = new String[1];
      localObject1[0] = paramExtTransferCompany.getCustID();
      localExtTransferAcctList.setCustomerId((String[])localObject1);
      if ((paramString != null) && (paramString.trim().length() > 0)) {
        localExtTransferAcctList.setFIId(paramString);
      }
      if ((paramExtTransferAccount.getNumber() != null) && (paramExtTransferAccount.getNumber().trim().length() > 0)) {
        localExtTransferAcctList.setAcctNum(paramExtTransferAccount.getNumber());
      }
      if ((paramExtTransferAccount.getNickname() != null) && (paramExtTransferAccount.getNickname().trim().length() > 0)) {
        localExtTransferAcctList.setNickName(paramExtTransferAccount.getNickname());
      }
      if (paramExtTransferAccount.getTypeValue() != 0) {
        localExtTransferAcctList.setAcctType(BeansConverter.getACHAcctTypeFromEFS(paramExtTransferAccount.getTypeValue()));
      }
      localExtTransferAcctList = localBPWServices.getExtTransferAcctList(localExtTransferAcctList);
      i = localExtTransferAcctList.getStatusCode();
      if (i != 0)
      {
        if (i != 16020)
        {
          DebugLog.log("Error occurred when getting an External Transfer Accounts:");
          DebugLog.log("*** BPW ErrorCode: " + i);
          str1 = localExtTransferAcctList.getStatusMsg();
          DebugLog.log("*** BPW ErrorMsg: " + str1);
          throw new CSILException(39705, i, "BPW getExtTransferAccounts", str1);
        }
      }
      else
      {
        localExtTransferAccounts = BeansConverter.getExtTransferAccounts(localExtTransferAcctList, paramSecureUser.getLocale());
        int j = Integer.parseInt(paramExtTransferCompany.getCustID());
        ExtTransferAccount localExtTransferAccount = null;
        Accounts localAccounts = AccountAdapter.getAccountsById(j);
        String str2 = paramSecureUser.getBankID();
        for (int k = 0; k < localExtTransferAccounts.size(); k++)
        {
          localExtTransferAccount = (ExtTransferAccount)localExtTransferAccounts.get(k);
          localExtTransferAccount.setBankId(str2);
          Account localAccount = localAccounts.getByIDAndBankIDAndRoutingNum(AccountSettings.buildAccountId(localExtTransferAccount.getNumber(), "" + localExtTransferAccount.getTypeValue()), localExtTransferAccount.getBankId(), localExtTransferAccount.getRoutingNumber());
          if (localAccount != null) {
            localExtTransferAccount.mapAccount(localAccount);
          } else {
            throw new CSILException(39711);
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof CSILException)) {
        throw ((CSILException)localThrowable);
      }
      DebugLog.log("ExternalTransferAdminService.getExtTransferAccounts exception: " + localThrowable.getMessage());
      Object localObject1 = localThrowable.toString();
      if ((localObject1 != null) && (((String)localObject1).indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
      throw new CSILException(39705);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    return localExtTransferAccounts;
  }
  
  public ExtTransferAccounts getExtTransferAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ExternalTransferAdminException
  {
    BPWServices localBPWServices = null;
    try
    {
      localBPWServices = getBPWHandler();
    }
    catch (Exception localException1)
    {
      throw new ExternalTransferAdminException(25018);
    }
    if (localBPWServices == null) {
      throw new ExternalTransferAdminException(25018);
    }
    ExtTransferAccounts localExtTransferAccounts = null;
    int i = -1;
    ExtTransferAcctList localExtTransferAcctList = new ExtTransferAcctList();
    String[] arrayOfString = { String.valueOf(paramSecureUser.getBusinessID()) };
    localExtTransferAcctList.setCustomerId(arrayOfString);
    try
    {
      localExtTransferAcctList = localBPWServices.getExtTransferAcctList(localExtTransferAcctList);
      i = localExtTransferAcctList.getStatusCode();
      if (i == 0) {
        localExtTransferAccounts = BeansConverter.getExtTransferAccounts(localExtTransferAcctList, paramSecureUser.getLocale());
      }
    }
    catch (Exception localException2)
    {
      DebugLog.log("Exception occurred during getting External Transfer Accounts:");
      localException2.printStackTrace();
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    if (i == 16020) {
      return new ExtTransferAccounts();
    }
    if (i != 0)
    {
      DebugLog.log("Error occurred when getting External Transfer Account for Business");
      DebugLog.log("*** BPW ErrorCode: " + i);
      DebugLog.log("*** BPW ErrorMsg: " + localExtTransferAcctList.getStatusMsg());
    }
    return localExtTransferAccounts;
  }
  
  public ExtTransferAccount verifyExtTransferAccount(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, int[] paramArrayOfInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = paramSecureUser.getBPWFIID();
    BPWServices localBPWServices = null;
    ExtTransferAcctInfo localExtTransferAcctInfo = null;
    ExtTransferCompany localExtTransferCompany = new ExtTransferCompany();
    localExtTransferCompany.setCustID("" + paramSecureUser.getProfileID());
    try
    {
      localExtTransferAcctInfo = BeansConverter.getExtTransferAccountInfo(paramExtTransferAccount, localExtTransferCompany);
      localBPWServices = getBPWHandler();
      localExtTransferAcctInfo = localBPWServices.verifyExtTransferAccount(str1, localExtTransferAcctInfo, paramArrayOfInt);
      int i = 0;
      String str2 = "";
      i = localExtTransferAcctInfo.getStatusCode();
      if (i != 0)
      {
        DebugLog.log("Error occurred when Verifying the External Transfer Account:");
        DebugLog.log("*** BPW ErrorCode: " + i);
        str2 = localExtTransferAcctInfo.getStatusMsg();
        DebugLog.log("*** BPW ErrorMsg: " + str2);
        i = mapError(localExtTransferAcctInfo.getStatusCode());
        throw new CSILException(39704, i, "BPW addExtTransferAccount", str2);
      }
      paramExtTransferAccount.set(BeansConverter.getExtTransferAccount(paramExtTransferAccount, localExtTransferAcctInfo));
    }
    catch (Exception localException)
    {
      DebugLog.log("Exception occurred verifyExtTransferAccount:");
      localException.printStackTrace();
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    return paramExtTransferAccount;
  }
  
  public ExtTransferAccount depositAmountsForVerify(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    BPWServices localBPWServices = null;
    ExtTransferAcctInfo localExtTransferAcctInfo = null;
    ExtTransferCompany localExtTransferCompany = new ExtTransferCompany();
    localExtTransferCompany.setCustID("" + paramSecureUser.getProfileID());
    if ((paramHashMap != null) && (paramHashMap.get("CustID") != null)) {
      localExtTransferCompany.setCustID((String)paramHashMap.get("CustID"));
    }
    try
    {
      localExtTransferAcctInfo = BeansConverter.getExtTransferAccountInfo(paramExtTransferAccount, localExtTransferCompany);
      localBPWServices = getBPWHandler();
      localExtTransferAcctInfo = localBPWServices.depositAmountsForVerify(paramAffiliateBank.getFIBPWID(), localExtTransferAcctInfo);
      int i = 0;
      String str = "";
      i = localExtTransferAcctInfo.getStatusCode();
      if (i != 0)
      {
        DebugLog.log("Error occurred when Depositing to the External Transfer Account:");
        DebugLog.log("*** BPW ErrorCode: " + i);
        str = localExtTransferAcctInfo.getStatusMsg();
        DebugLog.log("*** BPW ErrorMsg: " + str);
        i = mapError(localExtTransferAcctInfo.getStatusCode());
        throw new CSILException(39704, i, "BPW addExtTransferAccount", str);
      }
      paramExtTransferAccount.set(BeansConverter.getExtTransferAccount(paramExtTransferAccount, localExtTransferAcctInfo));
    }
    catch (Exception localException)
    {
      DebugLog.log("Exception occurred depositAmountsForVerify:");
      localException.printStackTrace();
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    return paramExtTransferAccount;
  }
  
  public String getNumberOfVerifyDeposits()
    throws CSILException
  {
    BPWServices localBPWServices = null;
    String str = "2";
    try
    {
      localBPWServices = getBPWHandler();
      str = localBPWServices.getBPWProperty(null, "bpw.external.transfer.account.verify.deposits");
    }
    catch (Exception localException)
    {
      DebugLog.log("Exception occurred getNumberOfVerifyDeposits:");
      localException.printStackTrace();
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    return str;
  }
  
  public class a
    extends Base.a
  {
    public a()
    {
      super();
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (paramString1.equals("BPWCallBackJNDIName")) {
        ExternalTransferAdminService.this.cV = paramString2;
      } else if (paramString1.equals("BPWAdminCallBackJNDIName")) {
        ExternalTransferAdminService.this.cU = paramString2;
      } else {
        super.attribute(paramString1, paramString2, paramBoolean);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.exttransfers.ExternalTransferAdminService
 * JD-Core Version:    0.7.0.1
 */