package com.ffusion.services.bptw.OFX151;

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
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices.IOFX151BPWServices;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices.OFX151BPWServicesBean;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices.OFX151BPWServicesHome;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSignOnMsgsRqV1Aggregate;
import com.ffusion.util.ArrayUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Properties;
import javax.ejb.CreateException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.transaction.TransactionRolledbackException;

public class BillPay
  extends com.ffusion.services.FFServer.OFX151.BillPay
  implements com.ffusion.services.bptw.BillPay
{
  private IOFX151BPWServices bh = null;
  private OFX151BPWServicesBean bg = null;
  private static final String bj = "com.ibm.websphere.naming.jndicache.cacheobject";
  private static final String bi = "none";
  
  private Context jdMethod_if(String paramString1, String paramString2, String paramString3)
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
    Context localContext = null;
    try
    {
      if (this.useNoEJB)
      {
        this.bg = new OFX151BPWServicesBean();
      }
      else
      {
        localContext = jdMethod_if(this.provider_url, this.context_username, this.context_password);
        Object localObject = localContext.lookup(this.callback_JNDI_name);
        OFX151BPWServicesHome localOFX151BPWServicesHome = (OFX151BPWServicesHome)PortableRemoteObject.narrow(localObject, OFX151BPWServicesHome.class);
        this.bh = localOFX151BPWServicesHome.create();
        if (this.bh == null) {
          System.out.println("bpw handler is null");
        }
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
  
  protected void removeHandler()
  {
    if (this.useNoEJB) {
      return;
    }
    try
    {
      if (this.debugService) {
        System.out.println("##################### Removing Handler ################");
      }
      if (this.bh == null)
      {
        System.out.println("bpw handler is null");
      }
      else
      {
        this.bh.remove();
        this.bh = null;
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
    localBPWHist.setCustId(this.userID);
    getHandler();
    try
    {
      localBPWHist = this.bh.getPmtHistory(localBPWHist);
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
          arrayOfRecPmtInfo = this.bh.getRecPmtById((String[])localObject);
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
      if (this.bh != null) {
        return this.bh.processPayeeTrnRqV1(paramTypePayeeTrnRqV1, this._ud);
      }
      return this.bg.processPayeeTrnRqV1(paramTypePayeeTrnRqV1, this._ud);
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
      if (this.bh != null) {
        return this.bh.processPayeeSyncRqV1(paramTypePayeeSyncRqV1, this._ud);
      }
      return this.bg.processPayeeSyncRqV1(paramTypePayeeSyncRqV1, this._ud);
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
      if (this.bh != null) {
        return this.bh.processPmtTrnRqV1(paramTypePmtTrnRqV1, this._ud);
      }
      return this.bg.processPmtTrnRqV1(paramTypePmtTrnRqV1, this._ud);
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
      if (this.bh != null) {
        return this.bh.processRecPmtTrnRqV1(paramTypeRecPmtTrnRqV1, this._ud);
      }
      return this.bg.processRecPmtTrnRqV1(paramTypeRecPmtTrnRqV1, this._ud);
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
      if (this.bh != null) {
        return this.bh.processPmtSyncRqV1(paramTypePmtSyncRqV1, this._ud);
      }
      return this.bg.processPmtSyncRqV1(paramTypePmtSyncRqV1, this._ud);
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
      if (this.bh != null) {
        return this.bh.processRecPmtSyncRqV1(paramTypeRecPmtSyncRqV1, this._ud);
      }
      return this.bg.processRecPmtSyncRqV1(paramTypeRecPmtSyncRqV1, this._ud);
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
 * Qualified Name:     com.ffusion.services.bptw.OFX151.BillPay
 * JD-Core Version:    0.7.0.1
 */