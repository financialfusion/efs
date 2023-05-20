package com.ffusion.services.bptw.OFX151;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.util.BeansConverter;
import com.ffusion.ffs.bpw.interfaces.BPWHist;
import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices.IOFX151BPWServices;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices.OFX151BPWServicesBean;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices.OFX151BPWServicesHome;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSignOnMsgsRqV1Aggregate;
import com.ffusion.util.ArrayUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.Properties;
import javax.ejb.CreateException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.transaction.TransactionRolledbackException;

public class Banking
  extends com.ffusion.services.FFServer.OFX151.Banking
  implements com.ffusion.services.bptw.Banking
{
  private IOFX151BPWServices bm = null;
  private OFX151BPWServicesBean bl = null;
  private static final String bo = "com.ibm.websphere.naming.jndicache.cacheobject";
  private static final String bn = "none";
  
  private Context jdMethod_do(String paramString1, String paramString2, String paramString3)
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
        if (this.bl == null) {
          this.bl = new OFX151BPWServicesBean();
        }
      }
      else if (this.bm == null)
      {
        localContext = jdMethod_do(this.provider_url, this.context_username, this.context_password);
        Object localObject = localContext.lookup(this.callback_JNDI_name);
        OFX151BPWServicesHome localOFX151BPWServicesHome = (OFX151BPWServicesHome)PortableRemoteObject.narrow(localObject, OFX151BPWServicesHome.class);
        this.bm = localOFX151BPWServicesHome.create();
        if (this.bm == null) {
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
      if (this.bm == null)
      {
        System.out.println("bpw handler is null");
      }
      else
      {
        this.bm.remove();
        this.bm = null;
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace(System.out);
    }
  }
  
  protected void processTransactionsInSignOnMsgsRqV1(TypeSignOnMsgsRqV1Aggregate paramTypeSignOnMsgsRqV1Aggregate) {}
  
  protected TypeIntraTrnRsV1 processIntraTrnRqV1(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1)
  {
    try
    {
      if (this.bm != null) {
        return this.bm.processIntraTrnRqV1(paramTypeIntraTrnRqV1, this._ud);
      }
      return this.bl.processIntraTrnRqV1(paramTypeIntraTrnRqV1, this._ud);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return null;
  }
  
  protected TypeRecIntraTrnRsV1 processRecIntraTrnRqV1(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1)
  {
    try
    {
      if (this.bm != null) {
        return this.bm.processRecIntraTrnRqV1(paramTypeRecIntraTrnRqV1, this._ud);
      }
      return this.bl.processRecIntraTrnRqV1(paramTypeRecIntraTrnRqV1, this._ud);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return null;
  }
  
  protected TypeIntraSyncRsV1 processIntraSyncRqV1(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1)
  {
    try
    {
      if (this.bm != null) {
        return this.bm.processIntraSyncRqV1(paramTypeIntraSyncRqV1, this._ud);
      }
      return this.bl.processIntraSyncRqV1(paramTypeIntraSyncRqV1, this._ud);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return null;
  }
  
  protected TypeRecIntraSyncRsV1 processRecIntraSyncRqV1(TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1)
  {
    try
    {
      if (this.bm != null) {
        return this.bm.processRecIntraSyncRqV1(paramTypeRecIntraSyncRqV1, this._ud);
      }
      return this.bl.processRecIntraSyncRqV1(paramTypeRecIntraSyncRqV1, this._ud);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return null;
  }
  
  public int getTransfers(Accounts paramAccounts, Transfers paramTransfers)
  {
    if ((paramAccounts != null) && (paramAccounts.size() > 0))
    {
      this.account = ((Account)paramAccounts.get(0));
      if ((this.account != null) && (this.account.getNumber() != null) && (this.account.getNumber().length() > 0)) {
        return super.getTransfers(paramAccounts, paramTransfers);
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
      localBPWHist = this.bm.getIntraHistory(localBPWHist);
    }
    catch (Exception localException1)
    {
      DebugLog.throwing("getPageTransfers ", localException1);
      removeHandler();
      return 0;
    }
    removeHandler();
    if (localBPWHist != null)
    {
      IntraTrnInfo[] arrayOfIntraTrnInfo = null;
      try
      {
        localBPWHist.setTrans(ArrayUtil.convertReferences(localBPWHist.getTrans(), new IntraTrnInfo[0].getClass()));
      }
      catch (Exception localException2) {}
      if ((localBPWHist.getTrans() instanceof IntraTrnInfo[])) {
        arrayOfIntraTrnInfo = (IntraTrnInfo[])localBPWHist.getTrans();
      } else {
        DebugLog.log("getPageTransfers: NOT IntraTrnInfo object");
      }
      IntraTrnInfo localIntraTrnInfo = null;
      if (arrayOfIntraTrnInfo != null) {
        for (int i = 0; i < arrayOfIntraTrnInfo.length; i++)
        {
          Transfer localTransfer = (Transfer)paramTransfers.create();
          localIntraTrnInfo = arrayOfIntraTrnInfo[i];
          BeansConverter.setXferFromIntraTrnInfo(localTransfer, localIntraTrnInfo, paramAccounts, false);
          if (((localIntraTrnInfo.srvrTid == null) || (localIntraTrnInfo.srvrTid.length() == 0)) && (localIntraTrnInfo.recSrvrTid != null) && (localIntraTrnInfo.recSrvrTid.length() > 0)) {
            paramTransfers.remove(localTransfer);
          }
        }
      }
    }
    return 0;
  }
  
  public int getRecTransfers(Accounts paramAccounts, RecTransfers paramRecTransfers)
  {
    if ((paramAccounts != null) && (paramAccounts.size() > 0))
    {
      this.account = ((Account)paramAccounts.get(0));
      if ((this.account != null) && (this.account.getNumber() != null) && (this.account.getNumber().length() > 0)) {
        return super.getRecTransfers(paramAccounts, paramRecTransfers);
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
      localBPWHist = this.bm.getIntraHistory(localBPWHist);
    }
    catch (Exception localException1)
    {
      DebugLog.throwing("getPageTransfers ", localException1);
      removeHandler();
      return 0;
    }
    removeHandler();
    paramAccounts = new Accounts();
    if (localBPWHist != null)
    {
      IntraTrnInfo[] arrayOfIntraTrnInfo = null;
      try
      {
        localBPWHist.setTrans(ArrayUtil.convertReferences(localBPWHist.getTrans(), new IntraTrnInfo[0].getClass()));
      }
      catch (Exception localException2) {}
      if ((localBPWHist.getTrans() instanceof IntraTrnInfo[])) {
        arrayOfIntraTrnInfo = (IntraTrnInfo[])localBPWHist.getTrans();
      } else {
        DebugLog.log("getPageTransfers: NOT IntraTrnInfo object");
      }
      IntraTrnInfo localIntraTrnInfo = null;
      if (arrayOfIntraTrnInfo != null)
      {
        for (int i = 0; i < arrayOfIntraTrnInfo.length; i++)
        {
          localIntraTrnInfo = arrayOfIntraTrnInfo[i];
          String str1 = localIntraTrnInfo.acctTypeFrom;
          int j = com.ffusion.services.banksim2.Banking.getBPWAccountType(str1);
          String str2 = localIntraTrnInfo.acctIdFrom;
          Account localAccount = paramAccounts.getByAccountNumberAndType(str2, j);
          if (localAccount == null)
          {
            localAccount = paramAccounts.create(localIntraTrnInfo.bankId, str2, j);
            localAccount.setFilterable("TransferFrom");
          }
        }
        if (paramAccounts.size() > 0) {
          return super.getRecTransfers(paramAccounts, paramRecTransfers);
        }
      }
    }
    return 0;
  }
  
  public String getBankID()
  {
    return this.bankID;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.bptw.OFX151.Banking
 * JD-Core Version:    0.7.0.1
 */