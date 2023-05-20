package com.ffusion.services.bptw.OFX200;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.beans.util.BeansConverter;
import com.ffusion.ffs.bpw.interfaces.BPWHist;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices.IOFX200BPWServices;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices.OFX200BPWServicesHome;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSignOnMsgsRqV1Aggregate;
import com.ffusion.util.ArrayUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;
import javax.ejb.CreateException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.transaction.TransactionRolledbackException;

public class BillPay
  extends com.ffusion.services.banksim2.BillPay
  implements com.ffusion.services.bptw.BillPay
{
  private IOFX200BPWServices aI = null;
  private static final String aK = "com.ibm.websphere.naming.jndicache.cacheobject";
  private static final String aJ = "none";
  
  private Context a(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    Properties localProperties = new Properties();
    localProperties.put("java.naming.factory.initial", this.context_factory);
    localProperties.put("java.naming.provider.url", paramString1);
    localProperties.put("java.naming.security.principal", paramString2);
    localProperties.put("java.naming.security.credentials", paramString3);
    localProperties.put("com.ibm.websphere.naming.jndicache.cacheobject", "none");
    return new InitialContext(localProperties);
  }
  
  protected void getHandler()
  {
    if (this.aI != null) {
      return;
    }
    Context localContext = null;
    for (int i = 0; i < this.provider_url_list.size(); i++)
    {
      String str = (String)this.provider_url_list.get(i);
      try
      {
        localContext = a(str, this.context_username, this.context_password);
        Object localObject = localContext.lookup(this.callback_JNDI_name);
        OFX200BPWServicesHome localOFX200BPWServicesHome = (OFX200BPWServicesHome)PortableRemoteObject.narrow(localObject, OFX200BPWServicesHome.class);
        this.aI = localOFX200BPWServicesHome.create();
        if (this.aI != null) {
          return;
        }
      }
      catch (TransactionRolledbackException localTransactionRolledbackException)
      {
        System.out.println("Unexpected TransactionRolledbackException: " + localTransactionRolledbackException.toString());
      }
      catch (RemoteException localRemoteException)
      {
        System.out.println("RMI RemoteException: " + localRemoteException.toString());
      }
      catch (CreateException localCreateException)
      {
        System.out.println("EJB CreateException: " + localCreateException.toString());
      }
      catch (RemoveException localRemoveException)
      {
        System.out.println("EJB RemoteException: " + localRemoveException.toString());
      }
      catch (ObjectNotFoundException localObjectNotFoundException)
      {
        System.out.println("EJB ObjecNotFound Exception: " + localObjectNotFoundException.toString());
      }
      catch (NamingException localNamingException)
      {
        System.out.println("NamingException: " + localNamingException.toString());
      }
      catch (Throwable localThrowable)
      {
        System.out.println("UNKNOWN: " + localThrowable.toString());
      }
    }
    System.out.println("bpw handler is null");
  }
  
  protected void removeHandler()
  {
    try
    {
      if (debugService) {
        System.out.println("##################### Removing Handler ################");
      }
      if (this.aI == null)
      {
        System.out.println("bpw handler is null");
      }
      else
      {
        this.aI.remove();
        this.aI = null;
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace(System.out);
    }
  }
  
  public int getPayments(Accounts paramAccounts, Payments paramPayments, RecPayments paramRecPayments, Payees paramPayees)
  {
    if ((paramAccounts != null) && (paramAccounts.size() > 0))
    {
      this.account = ((Account)paramAccounts.get(0));
      if ((this.account != null) && (this.account.getNumber() != null) && (this.account.getNumber().length() > 0)) {
        return super.getPayments(paramAccounts, paramPayments, paramRecPayments, paramPayees);
      }
    }
    BPWHist localBPWHist = new BPWHist();
    localBPWHist.setVersion("200");
    localBPWHist.setCursorId(null);
    localBPWHist.setHistId(null);
    localBPWHist.setCustId(getProfileID());
    getHandler();
    try
    {
      localBPWHist = this.aI.getPmtHistory(localBPWHist);
    }
    catch (Exception localException1)
    {
      DebugLog.throwing("getPayments ", localException1);
      removeHandler();
      return 0;
    }
    removeHandler();
    ArrayList localArrayList = new ArrayList();
    if (localBPWHist != null)
    {
      PmtInfo[] arrayOfPmtInfo = null;
      try
      {
        localBPWHist.setTrans(ArrayUtil.convertReferences(localBPWHist.getTrans(), new PmtInfo[0].getClass()));
      }
      catch (Exception localException2) {}
      if ((localBPWHist.getTrans() instanceof PmtInfo[])) {
        arrayOfPmtInfo = (PmtInfo[])localBPWHist.getTrans();
      } else {
        DebugLog.log("getPaymentsPage: NOT PmtInfo object");
      }
      PmtInfo localPmtInfo = null;
      Object localObject;
      if (arrayOfPmtInfo != null) {
        for (int i = 0; i < arrayOfPmtInfo.length; i++)
        {
          localObject = (Payment)paramPayments.create();
          localPmtInfo = arrayOfPmtInfo[i];
          BeansConverter.setPmtFromPmtInfo((Payment)localObject, localPmtInfo, paramPayees, paramAccounts, false);
          if ((localPmtInfo.RecSrvrTID != null) && (localPmtInfo.RecSrvrTID.length() > 0) && (!localArrayList.contains(localPmtInfo.RecSrvrTID))) {
            localArrayList.add(localPmtInfo.RecSrvrTID);
          }
          if (((localPmtInfo.SrvrTID == null) || (localPmtInfo.SrvrTID.length() == 0)) && (localPmtInfo.RecSrvrTID != null) && (localPmtInfo.RecSrvrTID.length() > 0)) {
            paramPayments.remove(localObject);
          }
        }
      }
      if (localArrayList.size() > 0)
      {
        getHandler();
        RecPmtInfo[] arrayOfRecPmtInfo = null;
        try
        {
          localObject = (String[])localArrayList.toArray(new String[0]);
          arrayOfRecPmtInfo = this.aI.getRecPmtById((String[])localObject);
        }
        catch (Exception localException3)
        {
          DebugLog.throwing("getPaymentsPage ", localException3);
          removeHandler();
          return 0;
        }
        removeHandler();
        if (arrayOfRecPmtInfo != null)
        {
          for (int j = 0; j < arrayOfRecPmtInfo.length; j++)
          {
            RecPmtInfo localRecPmtInfo = arrayOfRecPmtInfo[j];
            RecPayment localRecPayment = (RecPayment)paramRecPayments.create();
            com.ffusion.services.banksim2.BillPay.setRecPmtFromRecPmtInfo(localRecPayment, localRecPmtInfo, paramPayments);
          }
          paramPayments.setFilter("All");
        }
      }
    }
    return 0;
  }
  
  protected void processTransactionsInSignOnMsgsRqV1(TypeSignOnMsgsRqV1Aggregate paramTypeSignOnMsgsRqV1Aggregate) {}
  
  protected TypePayeeTrnRsV1 processPayeeTrnRqV1(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1)
  {
    try
    {
      return this.aI.processPayeeTrnRqV1(paramTypePayeeTrnRqV1, this._ud);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return null;
  }
  
  protected TypePayeeSyncRsV1 processPayeeSyncRqV1(TypePayeeSyncRqV1 paramTypePayeeSyncRqV1)
  {
    try
    {
      return this.aI.processPayeeSyncRqV1(paramTypePayeeSyncRqV1, this._ud);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return null;
  }
  
  protected TypePmtTrnRsV1 processPmtTrnRqV1(TypePmtTrnRqV1 paramTypePmtTrnRqV1)
  {
    try
    {
      return this.aI.processPmtTrnRqV1(paramTypePmtTrnRqV1, this._ud);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return null;
  }
  
  protected TypeRecPmtTrnRsV1 processRecPmtTrnRqV1(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1)
  {
    try
    {
      return this.aI.processRecPmtTrnRqV1(paramTypeRecPmtTrnRqV1, this._ud);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return null;
  }
  
  protected TypePmtSyncRsV1 processPmtSyncRqV1(TypePmtSyncRqV1 paramTypePmtSyncRqV1)
  {
    try
    {
      return this.aI.processPmtSyncRqV1(paramTypePmtSyncRqV1, this._ud);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return null;
  }
  
  protected TypeRecPmtSyncRsV1 processRecPmtSyncRqV1(TypeRecPmtSyncRqV1 paramTypeRecPmtSyncRqV1)
  {
    try
    {
      return this.aI.processRecPmtSyncRqV1(paramTypeRecPmtSyncRqV1, this._ud);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return null;
  }
  
  public String getBankID()
  {
    return this.bankID;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.bptw.OFX200.BillPay
 * JD-Core Version:    0.7.0.1
 */