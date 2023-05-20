package com.ffusion.webservices.api;

import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.user.User;
import com.ffusion.csil.core.Initialize;
import com.ffusion.services.Banking2;
import com.ffusion.services.BillPay2;
import com.ffusion.tasks.accounts.GetAccounts;
import com.ffusion.tasks.accounts.GetBusinessAccounts;
import com.ffusion.tasks.accounts.MergeBalances;
import com.ffusion.tasks.banking.AddTransfer;
import com.ffusion.tasks.banking.CancelTransfer;
import com.ffusion.tasks.banking.EditTransfer;
import com.ffusion.tasks.banking.GetConsumerTransferAccounts;
import com.ffusion.tasks.banking.GetRecTransfers;
import com.ffusion.tasks.banking.GetTransactions;
import com.ffusion.tasks.banking.GetTransfers;
import com.ffusion.tasks.billpay.AddExtPayee;
import com.ffusion.tasks.billpay.AddPayment;
import com.ffusion.tasks.billpay.CancelPayment;
import com.ffusion.tasks.billpay.DeleteExtPayee;
import com.ffusion.tasks.billpay.EditExtPayee;
import com.ffusion.tasks.billpay.EditPayment;
import com.ffusion.tasks.billpay.GetConsumerBillPayAccounts;
import com.ffusion.tasks.billpay.GetPayees;
import com.ffusion.tasks.billpay.GetPayments;
import com.ffusion.tasks.business.GetBusinessByEmployee;
import com.ffusion.tasks.user.GetOneToOneUser;
import com.ffusion.util.PropertiesUtil;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.webservices.servletfacade.HttpServletFacade;
import com.ffusion.webservices.servletfacade.HttpServletRequestFacade;
import com.ffusion.webservices.servletfacade.HttpServletResponseFacade;
import com.ffusion.webservices.servletfacade.ServletContextFacade;
import com.ffusion.webservices.servletfacade.SessionManager;
import com.ffusion.webservices.util.BeanUtil;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import javax.servlet.http.HttpSession;

public class EFS
  implements IEFS
{
  public String authenticate(String paramString1, String paramString2, String paramString3)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.authenticate : " + paramString1);
    Initialize.initialize();
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.createSession();
    localHttpServletRequestFacade.setSession(localHttpSession);
    Locale localLocale = Locale.getDefault();
    localHttpSession.setAttribute("java.util.Locale", localLocale);
    try
    {
      a(paramString1, paramString2, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade, localHttpSession, null);
      User localUser = (User)localHttpSession.getAttribute("User");
      String str = (String)localUser.get("BUSINESS_ID");
      if ((str != null) && (str.length() > 0)) {
        a(localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade, localHttpSession);
      } else {
        a(localUser.getCustId(), (String)localUser.get("PROCESSOR_PIN"), localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade, localHttpSession);
      }
    }
    catch (WSException localWSException)
    {
      localHttpSession.invalidate();
      throw localWSException;
    }
    return localHttpSession.getId();
  }
  
  private void a(String paramString1, String paramString2, HttpServletFacade paramHttpServletFacade, HttpServletRequestFacade paramHttpServletRequestFacade, HttpServletResponseFacade paramHttpServletResponseFacade, HttpSession paramHttpSession, String paramString3)
    throws WSException
  {
    Banking2 localBanking2 = null;
    Properties localProperties = PropertiesUtil.getPropertiesFromResource(this, "services.properties");
    if (localProperties == null) {
      DebugLog.log(Level.WARNING, "services.properties file not found in classpath, assuming adapters are in use");
    }
    if (localProperties != null)
    {
      paramHttpSession.setAttribute("services.properties", localProperties);
      try
      {
        DebugLog.log(Level.FINE, "Loading properties file, assuming services");
        String str = localProperties.getProperty("BANKINGSERVICE");
        DebugLog.log(Level.CONFIG, "Creating an instance of Banking2 service using " + str);
        Class localClass = Class.forName(str);
        localBanking2 = (Banking2)localClass.newInstance();
        paramHttpSession.setAttribute("com.ffusion.services.Banking", localBanking2);
        DebugLog.log(Level.FINEST, "Banking2 service is named \"com.ffusion.services.Banking\" in the session");
        DebugLog.log(Level.FINEST, "Calling " + str + ".initialize");
        int i = localBanking2.initialize(localProperties.getProperty("BANKINGINIT"));
        if (i != 0)
        {
          DebugLog.log(Level.SEVERE, "banking.initialize failed with service error code : " + Integer.toString(i));
          throw new WSException("Service Initialization Error", 2);
        }
        str = localProperties.getProperty("BILLPAYSERVICE");
        DebugLog.log(Level.CONFIG, "Creating an instance of BillPay2 service using " + str);
        localClass = Class.forName(str);
        BillPay2 localBillPay2 = (BillPay2)localClass.newInstance();
        paramHttpSession.setAttribute("com.ffusion.services.BillPay", localBillPay2);
        DebugLog.log(Level.FINEST, "BillPay2 service is named \"com.ffusion.services.BillPay\" in the session");
        DebugLog.log(Level.FINEST, "Calling " + str + ".initialize");
        i = localBillPay2.initialize(localProperties.getProperty("BILLPAYINIT"));
        if (i != 0)
        {
          DebugLog.log(Level.SEVERE, "billpay.initialize failed with service error code : " + Integer.toString(i));
          throw new WSException("Service Initialization Error", 2);
        }
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        DebugLog.log(Level.SEVERE, "Service class not found : " + localClassNotFoundException.toString());
        throw new WSException("Service Initialization Error", 2);
      }
      catch (InstantiationException localInstantiationException)
      {
        DebugLog.log(Level.SEVERE, "Service intantiation failed : " + localInstantiationException.toString());
        throw new WSException("Service Initialization Error", 2);
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        DebugLog.log(Level.SEVERE, "Service illegal access : " + localIllegalAccessException.toString());
        throw new WSException("Service Initialization Error", 2);
      }
      catch (Throwable localThrowable)
      {
        DebugLog.log(Level.SEVERE, "Service error : " + localThrowable.getMessage());
        throw new WSException("Service Initialization Error Throwing", 2);
      }
    }
    GetOneToOneUser localGetOneToOneUser = new GetOneToOneUser();
    localGetOneToOneUser.setUserName(paramString1);
    localGetOneToOneUser.setPassword(paramString2);
    localGetOneToOneUser.setUserType(paramString3);
    localGetOneToOneUser.setAuthenticate("true");
    Base.processTask(localGetOneToOneUser, paramHttpServletFacade, paramHttpServletRequestFacade, paramHttpServletResponseFacade);
  }
  
  private void a(HttpServletFacade paramHttpServletFacade, HttpServletRequestFacade paramHttpServletRequestFacade, HttpServletResponseFacade paramHttpServletResponseFacade, HttpSession paramHttpSession)
    throws WSException
  {
    GetBusinessByEmployee localGetBusinessByEmployee = new GetBusinessByEmployee();
    User localUser = (User)paramHttpSession.getAttribute("User");
    localGetBusinessByEmployee.setBusinessEmployeeId(localUser.getId());
    Base.processTask(localGetBusinessByEmployee, paramHttpServletFacade, paramHttpServletRequestFacade, paramHttpServletResponseFacade);
    com.ffusion.tasks.banking.SignOn localSignOn = new com.ffusion.tasks.banking.SignOn();
    Business localBusiness = (Business)paramHttpSession.getAttribute("Business");
    localSignOn.setUserName(localBusiness.getBusinessName());
    localSignOn.setPassword(localBusiness.getBusinessCIF());
    Base.processTask(localSignOn, paramHttpServletFacade, paramHttpServletRequestFacade, paramHttpServletResponseFacade);
    GetBusinessAccounts localGetBusinessAccounts = new GetBusinessAccounts();
    localGetBusinessAccounts.setAccountsName("BankingAccounts");
    localGetBusinessAccounts.setCheckEntitlements("true");
    Base.processTask(localGetBusinessAccounts, paramHttpServletFacade, paramHttpServletRequestFacade, paramHttpServletResponseFacade);
    com.ffusion.tasks.billpay.SignOn localSignOn1 = new com.ffusion.tasks.billpay.SignOn();
    localSignOn1.setUserName(localBusiness.getBusinessName());
    localSignOn1.setPassword(localBusiness.getBusinessCIF());
    Base.processTask(localSignOn1, paramHttpServletFacade, paramHttpServletRequestFacade, paramHttpServletResponseFacade);
    com.ffusion.tasks.accounts.SignOn localSignOn2 = new com.ffusion.tasks.accounts.SignOn();
    localSignOn2.setUserName(localUser.getId());
    Base.processTask(localSignOn2, paramHttpServletFacade, paramHttpServletRequestFacade, paramHttpServletResponseFacade);
    GetAccounts localGetAccounts = new GetAccounts();
    localGetAccounts.setAccountsName("Accounts");
    localGetAccounts.setServiceName("com.ffusion.services.Banking");
    localGetAccounts.setCheckEntitlements("true");
    Base.processTask(localGetAccounts, paramHttpServletFacade, paramHttpServletRequestFacade, paramHttpServletResponseFacade);
    MergeBalances localMergeBalances = new MergeBalances();
    localMergeBalances.setProfileAccountsName("BankingAccounts");
    localMergeBalances.setBackendAccountsName("Accounts");
    Base.processTask(localMergeBalances, paramHttpServletFacade, paramHttpServletRequestFacade, paramHttpServletResponseFacade);
  }
  
  private void a(String paramString1, String paramString2, HttpServletFacade paramHttpServletFacade, HttpServletRequestFacade paramHttpServletRequestFacade, HttpServletResponseFacade paramHttpServletResponseFacade, HttpSession paramHttpSession)
    throws WSException
  {
    com.ffusion.tasks.banking.SignOn localSignOn = new com.ffusion.tasks.banking.SignOn();
    localSignOn.setUserName(paramString1);
    localSignOn.setPassword(paramString2);
    Base.processTask(localSignOn, paramHttpServletFacade, paramHttpServletRequestFacade, paramHttpServletResponseFacade);
    com.ffusion.tasks.billpay.SignOn localSignOn1 = new com.ffusion.tasks.billpay.SignOn();
    localSignOn1.setUserName(paramString1);
    localSignOn1.setPassword(paramString2);
    Base.processTask(localSignOn1, paramHttpServletFacade, paramHttpServletRequestFacade, paramHttpServletResponseFacade);
  }
  
  public void invalidate(String paramString)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.invalidate");
    Base.validateSession(paramString);
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    SessionManager.deleteSession(localHttpSession);
  }
  
  public com.ffusion.webservices.beans.RecTransfer deleteRecTransfer(String paramString1, String paramString2)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.deleteRecTransfer");
    Base.validateSession(paramString1);
    Base.validateTransferID(paramString2);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString1);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString1 + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    com.ffusion.beans.banking.RecTransfer localRecTransfer = null;
    RecTransfers localRecTransfers = (RecTransfers)localHttpSession.getAttribute("RecTransfers");
    if (localRecTransfers != null) {
      localRecTransfer = (com.ffusion.beans.banking.RecTransfer)localRecTransfers.getByID(paramString2);
    }
    if (localRecTransfer == null)
    {
      DebugLog.log(Level.WARNING, "recTransfer with id : " + paramString2 + " could not be found");
      throw new WSException("Transfer not found : ", 17);
    }
    localHttpSession.setAttribute("Transfer", localRecTransfer);
    CancelTransfer localCancelTransfer = new CancelTransfer();
    Base.processTask(localCancelTransfer, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    localRecTransfer = (com.ffusion.beans.banking.RecTransfer)localHttpSession.getAttribute("Transfer");
    return BeanUtil.TransformRecTransfer(localRecTransfer);
  }
  
  public com.ffusion.webservices.beans.Transfer deleteTransfer(String paramString1, String paramString2)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.deleteTransfer");
    Base.validateSession(paramString1);
    Base.validateTransferID(paramString2);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString1);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString1 + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    com.ffusion.beans.banking.Transfer localTransfer = null;
    Transfers localTransfers = (Transfers)localHttpSession.getAttribute("Transfers");
    if (localTransfers != null) {
      localTransfer = localTransfers.getByID(paramString2);
    }
    if (localTransfer == null)
    {
      DebugLog.log(Level.WARNING, "transfer with id : " + paramString2 + " could not be found");
      throw new WSException("Transfer not found : ", 17);
    }
    localHttpSession.setAttribute("Transfer", localTransfer);
    CancelTransfer localCancelTransfer = new CancelTransfer();
    Base.processTask(localCancelTransfer, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    localTransfer = (com.ffusion.beans.banking.Transfer)localHttpSession.getAttribute("Transfer");
    return BeanUtil.TransformTransfer(localTransfer);
  }
  
  public com.ffusion.webservices.beans.Account[] getBankingAccounts(String paramString)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.getBankingAccounts");
    Base.validateSession(paramString);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    GetAccounts localGetAccounts = new GetAccounts();
    localGetAccounts.setServiceName("com.ffusion.services.Banking");
    localGetAccounts.setCheckEntitlements("true");
    Base.processTask(localGetAccounts, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute("Accounts");
    com.ffusion.webservices.beans.Account[] arrayOfAccount = null;
    if (localAccounts != null)
    {
      int i = localAccounts.size();
      arrayOfAccount = new com.ffusion.webservices.beans.Account[i];
      for (int j = 0; j < i; j++) {
        arrayOfAccount[j] = BeanUtil.TransformAccount((com.ffusion.beans.accounts.Account)localAccounts.get(j));
      }
    }
    localHttpSession.setAttribute("BankingAccounts", localAccounts);
    return arrayOfAccount;
  }
  
  public com.ffusion.webservices.beans.Transaction[] getTransactionsByDate(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.getTransactionsByDate");
    Base.validateSession(paramString1);
    Base.validateAccount(paramString2, paramString3);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString1);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString1 + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    localHttpSession.setAttribute("Account", Base.getAccountFromSession(localHttpSession, paramString2));
    GetTransactions localGetTransactions = new GetTransactions();
    localGetTransactions.setStartDate(paramString4);
    localGetTransactions.setEndDate(paramString5);
    Base.processTask(localGetTransactions, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    com.ffusion.beans.accounts.Account localAccount = (com.ffusion.beans.accounts.Account)localHttpSession.getAttribute("Account");
    Transactions localTransactions = localAccount.getTransactions();
    if (paramString4 != null) {
      localTransactions.setFilter("DATE>=" + paramString4);
    }
    if (paramString5 != null) {
      localTransactions.setFilterOnFilter("DATE<=" + paramString5);
    }
    com.ffusion.webservices.beans.Transaction[] arrayOfTransaction = null;
    if (localTransactions != null)
    {
      int i = localTransactions.size();
      arrayOfTransaction = new com.ffusion.webservices.beans.Transaction[i];
      DebugLog.log("\tGot " + i + " transactions");
      for (int j = 0; j < i; j++) {
        arrayOfTransaction[j] = BeanUtil.TransformTransaction((com.ffusion.beans.banking.Transaction)localTransactions.get(j));
      }
    }
    localTransactions.setFilter("ALL");
    return arrayOfTransaction;
  }
  
  public com.ffusion.webservices.beans.Transaction[] getTransactions(String paramString1, String paramString2, String paramString3)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.getTransactions");
    return getTransactionsByDate(paramString1, paramString2, paramString3, null, null);
  }
  
  public com.ffusion.webservices.beans.RecTransfer modifyRecTransfer(String paramString, com.ffusion.webservices.beans.RecTransfer paramRecTransfer)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.modifyRecTransfer");
    Base.validateSession(paramString);
    Base.validateTransfer(paramRecTransfer);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    com.ffusion.beans.banking.RecTransfer localRecTransfer = null;
    RecTransfers localRecTransfers = (RecTransfers)localHttpSession.getAttribute("RecTransfers");
    if (localRecTransfers != null) {
      localRecTransfer = (com.ffusion.beans.banking.RecTransfer)localRecTransfers.getByID(paramRecTransfer.getId());
    }
    if (localRecTransfer == null)
    {
      DebugLog.log(Level.WARNING, "recTransfer with id : " + paramRecTransfer.getId() + " could not be found");
      throw new WSException("Transfer not found : ", 17);
    }
    Base.updateRecTransfer(localHttpSession, localRecTransfer, paramRecTransfer);
    localHttpSession.setAttribute("Transfer", localRecTransfer);
    EditTransfer localEditTransfer = new EditTransfer();
    localEditTransfer.setInitialize("true");
    Base.processTask(localEditTransfer, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    localEditTransfer.setInitialize("false");
    localEditTransfer.setProcess("true");
    Base.processTask(localEditTransfer, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    localRecTransfer = (com.ffusion.beans.banking.RecTransfer)localHttpSession.getAttribute("Transfer");
    return BeanUtil.TransformRecTransfer(localRecTransfer);
  }
  
  public com.ffusion.webservices.beans.Transfer modifyTransfer(String paramString, com.ffusion.webservices.beans.Transfer paramTransfer)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.modifyTransfer");
    Base.validateSession(paramString);
    Base.validateTransfer(paramTransfer);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    com.ffusion.beans.banking.Transfer localTransfer = null;
    Transfers localTransfers = (Transfers)localHttpSession.getAttribute("Transfers");
    if (localTransfers != null) {
      localTransfer = localTransfers.getByID(paramTransfer.getId());
    }
    if (localTransfer == null)
    {
      DebugLog.log(Level.WARNING, "transfer with id : " + paramTransfer.getId() + " could not be found");
      throw new WSException("Transfer not found : ", 17);
    }
    Base.updateTransfer(localHttpSession, localTransfer, paramTransfer);
    localHttpSession.setAttribute("Transfer", localTransfer);
    EditTransfer localEditTransfer = new EditTransfer();
    localEditTransfer.setInitialize("true");
    Base.processTask(localEditTransfer, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    localEditTransfer.setInitialize("false");
    localEditTransfer.setProcess("true");
    Base.processTask(localEditTransfer, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    localTransfer = (com.ffusion.beans.banking.Transfer)localHttpSession.getAttribute("Transfer");
    return BeanUtil.TransformTransfer(localTransfer);
  }
  
  public com.ffusion.webservices.beans.Transfer[] getTransfers(String paramString1, String paramString2, String paramString3)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.getTransfers");
    Base.validateSession(paramString1);
    Base.validateAccount(paramString2, paramString3);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString1);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString1 + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    GetTransfers localGetTransfers = new GetTransfers();
    Base.processTask(localGetTransfers, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    Transfers localTransfers = (Transfers)localHttpSession.getAttribute("Transfers");
    com.ffusion.webservices.beans.Transfer[] arrayOfTransfer = null;
    if (localTransfers != null)
    {
      int i = localTransfers.size();
      arrayOfTransfer = new com.ffusion.webservices.beans.Transfer[i];
      DebugLog.log("Banking.getTransfers: " + i);
      for (int j = 0; j < i; j++) {
        arrayOfTransfer[j] = BeanUtil.TransformTransfer((com.ffusion.beans.banking.Transfer)localTransfers.get(j));
      }
    }
    return arrayOfTransfer;
  }
  
  public com.ffusion.webservices.beans.RecTransfer[] getRecTransfers(String paramString1, String paramString2, String paramString3)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.getRecTransfers");
    Base.validateSession(paramString1);
    Base.validateAccount(paramString2, paramString3);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString1);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString1 + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    GetRecTransfers localGetRecTransfers = new GetRecTransfers();
    Base.processTask(localGetRecTransfers, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    RecTransfers localRecTransfers = (RecTransfers)localHttpSession.getAttribute("RecTransfers");
    com.ffusion.webservices.beans.RecTransfer[] arrayOfRecTransfer = null;
    if (localRecTransfers != null)
    {
      int i = localRecTransfers.size();
      arrayOfRecTransfer = new com.ffusion.webservices.beans.RecTransfer[i];
      DebugLog.log("Banking.getRecTransfers: " + i);
      for (int j = 0; j < i; j++) {
        arrayOfRecTransfer[j] = BeanUtil.TransformRecTransfer((com.ffusion.beans.banking.RecTransfer)localRecTransfers.get(j));
      }
    }
    return arrayOfRecTransfer;
  }
  
  public com.ffusion.webservices.beans.Transfer addTransfer(String paramString, com.ffusion.webservices.beans.Transfer paramTransfer)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.addTrasfer");
    Base.validateSession(paramString);
    Base.validateTransfer(paramTransfer);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    AddTransfer localAddTransfer = new AddTransfer();
    localAddTransfer.setProcess("true");
    Base.updateTransfer(localHttpSession, localAddTransfer, paramTransfer);
    Base.processTask(localAddTransfer, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    return BeanUtil.TransformTransfer((com.ffusion.beans.banking.Transfer)localHttpSession.getAttribute("Transfer"));
  }
  
  public com.ffusion.webservices.beans.RecTransfer addRecTransfer(String paramString, com.ffusion.webservices.beans.RecTransfer paramRecTransfer)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.addRecTransfer");
    Base.validateSession(paramString);
    Base.validateTransfer(paramRecTransfer);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    AddTransfer localAddTransfer = new AddTransfer();
    localAddTransfer.setProcess("true");
    Base.updateRecTransfer(localHttpSession, localAddTransfer, paramRecTransfer);
    Base.processTask(localAddTransfer, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    return BeanUtil.TransformRecTransfer((com.ffusion.beans.banking.RecTransfer)localHttpSession.getAttribute("Transfer"));
  }
  
  public com.ffusion.webservices.beans.Account[] getBillpayAccounts(String paramString)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.WebServices.getBillpayAccounts");
    Base.validateSession(paramString);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    GetConsumerBillPayAccounts localGetConsumerBillPayAccounts = new GetConsumerBillPayAccounts();
    Base.processTask(localGetConsumerBillPayAccounts, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute("BillPayAccounts");
    com.ffusion.webservices.beans.Account[] arrayOfAccount = null;
    if (localAccounts != null)
    {
      int i = localAccounts.size();
      arrayOfAccount = new com.ffusion.webservices.beans.Account[i];
      for (int j = 0; j < i; j++) {
        arrayOfAccount[j] = BeanUtil.TransformAccount((com.ffusion.beans.accounts.Account)localAccounts.get(j));
      }
    }
    return arrayOfAccount;
  }
  
  public com.ffusion.webservices.beans.Payee[] getPayees(String paramString)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.getPayees");
    Base.validateSession(paramString);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    GetPayees localGetPayees = new GetPayees();
    Base.processTask(localGetPayees, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    Payees localPayees = (Payees)localHttpSession.getAttribute("Payees");
    com.ffusion.webservices.beans.Payee[] arrayOfPayee = null;
    if (localPayees != null)
    {
      int i = localPayees.size();
      arrayOfPayee = new com.ffusion.webservices.beans.Payee[i];
      for (int j = 0; j < i; j++) {
        arrayOfPayee[j] = BeanUtil.TransformPayee((com.ffusion.beans.billpay.Payee)localPayees.get(j));
      }
    }
    return arrayOfPayee;
  }
  
  public com.ffusion.webservices.beans.Payee[] getGlobalPayees(String paramString)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.getGlobalPayees");
    Base.validateSession(paramString);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    GetPayees localGetPayees = new GetPayees();
    localGetPayees.setServiceName("GLOBALPAYEESERVICE");
    Base.processTask(localGetPayees, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    Payees localPayees = (Payees)localHttpSession.getAttribute("Payees");
    com.ffusion.webservices.beans.Payee[] arrayOfPayee = null;
    if (localPayees != null)
    {
      int i = localPayees.size();
      arrayOfPayee = new com.ffusion.webservices.beans.Payee[i];
      for (int j = 0; j < i; j++) {
        arrayOfPayee[j] = BeanUtil.TransformPayee((com.ffusion.beans.billpay.Payee)localPayees.get(j));
      }
    }
    return arrayOfPayee;
  }
  
  public com.ffusion.webservices.beans.Payee addPayee(String paramString, com.ffusion.webservices.beans.Payee paramPayee)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.addPayee");
    Base.validateSession(paramString);
    Base.validatePayee(paramPayee);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    AddExtPayee localAddExtPayee = new AddExtPayee();
    BeanUtil.UpdatePayee(localAddExtPayee, paramPayee);
    localAddExtPayee.setProcess("true");
    Base.processTask(localAddExtPayee, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    return BeanUtil.TransformPayee((com.ffusion.beans.billpay.Payee)localHttpSession.getAttribute("Payee"));
  }
  
  public com.ffusion.webservices.beans.Payee modifyPayee(String paramString, com.ffusion.webservices.beans.Payee paramPayee)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.modifyPayee");
    Base.validateSession(paramString);
    Base.validatePayee(paramPayee);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    EditExtPayee localEditExtPayee = new EditExtPayee();
    com.ffusion.beans.billpay.Payee localPayee = Base.getPayeeFromSession(localHttpSession, paramPayee.getId());
    localHttpSession.setAttribute("Payee", localPayee);
    localEditExtPayee.setInitialize("true");
    Base.processTask(localEditExtPayee, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    BeanUtil.UpdatePayee(localEditExtPayee, paramPayee);
    localEditExtPayee.setProcess("true");
    Base.processTask(localEditExtPayee, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    return BeanUtil.TransformPayee((com.ffusion.beans.billpay.Payee)localHttpSession.getAttribute("Payee"));
  }
  
  public com.ffusion.webservices.beans.Payee deletePayee(String paramString1, String paramString2)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.deletePayee");
    Base.validateSession(paramString1);
    Base.validatePayeeID(paramString2);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString1);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString1 + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    com.ffusion.beans.billpay.Payee localPayee = Base.getPayeeFromSession(localHttpSession, paramString2);
    localHttpSession.putValue("Payee", localPayee);
    DeleteExtPayee localDeleteExtPayee = new DeleteExtPayee();
    localDeleteExtPayee.setPayeeID(localPayee.getID());
    localDeleteExtPayee.setProcess("true");
    Base.processTask(localDeleteExtPayee, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    return BeanUtil.TransformPayee(localPayee);
  }
  
  public com.ffusion.webservices.beans.Payment addPayment(String paramString, com.ffusion.webservices.beans.Payment paramPayment)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.addPayment");
    Base.validateSession(paramString);
    Base.validatePayment(paramPayment);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    AddPayment localAddPayment = new AddPayment();
    BeanUtil.UpdatePayment(localAddPayment, paramPayment);
    localAddPayment.setAccount(Base.getAccountFromSession(localHttpSession, paramPayment.getAccountID()));
    localAddPayment.setPayee(Base.getPayeeFromSession(localHttpSession, paramPayment.getPayeeID()));
    localAddPayment.setProcess("true");
    Base.processTask(localAddPayment, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    return BeanUtil.TransformPayment((com.ffusion.beans.billpay.Payment)localHttpSession.getAttribute("Payment"));
  }
  
  public com.ffusion.webservices.beans.RecPayment addRecPayment(String paramString, com.ffusion.webservices.beans.RecPayment paramRecPayment)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.addRecPayment");
    Base.validateSession(paramString);
    Base.validatePayment(paramRecPayment);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    AddPayment localAddPayment = new AddPayment();
    BeanUtil.UpdateRecPayment(localAddPayment, paramRecPayment);
    localAddPayment.setAccount(Base.getAccountFromSession(localHttpSession, paramRecPayment.getAccountID()));
    localAddPayment.setPayee(Base.getPayeeFromSession(localHttpSession, paramRecPayment.getPayeeID()));
    localAddPayment.setProcess("true");
    Base.processTask(localAddPayment, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    return BeanUtil.TransformRecPayment((com.ffusion.beans.billpay.RecPayment)localHttpSession.getAttribute("Payment"));
  }
  
  public com.ffusion.webservices.beans.Payment modifyPayment(String paramString, com.ffusion.webservices.beans.Payment paramPayment)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.modifyPayment");
    Base.validateSession(paramString);
    Base.validatePayment(paramPayment);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    com.ffusion.beans.billpay.Payment localPayment = Base.getPaymentFromSession(localHttpSession, paramPayment.getId());
    localHttpSession.setAttribute("Payment", localPayment);
    EditPayment localEditPayment = new EditPayment();
    localEditPayment.setInitialize("true");
    Base.processTask(localEditPayment, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    BeanUtil.UpdatePayment(localEditPayment, paramPayment);
    localEditPayment.setAccount(Base.getAccountFromSession(localHttpSession, paramPayment.getAccountID()));
    localEditPayment.setPayee(Base.getPayeeFromSession(localHttpSession, paramPayment.getPayeeID()));
    localEditPayment.setProcess("true");
    Base.processTask(localEditPayment, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    return BeanUtil.TransformPayment((com.ffusion.beans.billpay.Payment)localHttpSession.getAttribute("Payment"));
  }
  
  public com.ffusion.webservices.beans.RecPayment modifyRecPayment(String paramString, com.ffusion.webservices.beans.RecPayment paramRecPayment)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.modifyRecPayment");
    Base.validateSession(paramString);
    Base.validatePayment(paramRecPayment);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    com.ffusion.beans.billpay.RecPayment localRecPayment = Base.getRecPaymentFromSession(localHttpSession, paramRecPayment.getId());
    localHttpSession.setAttribute("Payment", localRecPayment);
    EditPayment localEditPayment = new EditPayment();
    localEditPayment.setInitialize("true");
    Base.processTask(localEditPayment, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    BeanUtil.UpdateRecPayment(localRecPayment, paramRecPayment);
    localEditPayment.set(localRecPayment);
    localEditPayment.setAccount(Base.getAccountFromSession(localHttpSession, paramRecPayment.getAccountID()));
    localEditPayment.setPayee(Base.getPayeeFromSession(localHttpSession, paramRecPayment.getPayeeID()));
    localEditPayment.setProcess("true");
    Base.processTask(localEditPayment, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    return BeanUtil.TransformRecPayment((com.ffusion.beans.billpay.RecPayment)localHttpSession.getAttribute("Payment"));
  }
  
  public com.ffusion.webservices.beans.Payment deletePayment(String paramString1, String paramString2)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.deletePayment");
    Base.validateSession(paramString1);
    Base.validatePaymentID(paramString2);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString1);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString1 + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    com.ffusion.beans.billpay.Payment localPayment = Base.getPaymentFromSession(localHttpSession, paramString2);
    localHttpSession.setAttribute("Payment", localPayment);
    CancelPayment localCancelPayment = new CancelPayment();
    Base.processTask(localCancelPayment, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    return BeanUtil.TransformPayment(localPayment);
  }
  
  public com.ffusion.webservices.beans.RecPayment deleteRecPayment(String paramString1, String paramString2)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.deleteRecPayment");
    Base.validateSession(paramString1);
    Base.validatePaymentID(paramString2);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString1);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString1 + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    com.ffusion.beans.billpay.RecPayment localRecPayment = Base.getRecPaymentFromSession(localHttpSession, paramString2);
    localHttpSession.setAttribute("Payment", localRecPayment);
    CancelPayment localCancelPayment = new CancelPayment();
    Base.processTask(localCancelPayment, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    return BeanUtil.TransformRecPayment(localRecPayment);
  }
  
  public com.ffusion.webservices.beans.Payment[] getPayments(String paramString1, String paramString2, String paramString3)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.getPayments");
    Base.validateSession(paramString1);
    Base.validateAccount(paramString2, paramString3);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString1);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString1 + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    GetPayments localGetPayments = new GetPayments();
    Base.processTask(localGetPayments, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    Payments localPayments = (Payments)localHttpSession.getAttribute("Payments");
    com.ffusion.webservices.beans.Payment[] arrayOfPayment = null;
    if (localPayments != null)
    {
      int i = localPayments.size();
      arrayOfPayment = new com.ffusion.webservices.beans.Payment[i];
      for (int j = 0; j < i; j++) {
        arrayOfPayment[j] = BeanUtil.TransformPayment((com.ffusion.beans.billpay.Payment)localPayments.get(j));
      }
    }
    return arrayOfPayment;
  }
  
  public com.ffusion.webservices.beans.RecPayment[] getRecPayments(String paramString1, String paramString2, String paramString3)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.getRecPayments");
    Base.validateSession(paramString1);
    Base.validateAccount(paramString2, paramString3);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString1);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString1 + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    GetPayments localGetPayments = new GetPayments();
    Base.processTask(localGetPayments, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    RecPayments localRecPayments = (RecPayments)localHttpSession.getAttribute("RecPayments");
    com.ffusion.webservices.beans.RecPayment[] arrayOfRecPayment = null;
    if (localRecPayments != null)
    {
      int i = localRecPayments.size();
      arrayOfRecPayment = new com.ffusion.webservices.beans.RecPayment[i];
      for (int j = 0; j < i; j++) {
        arrayOfRecPayment[j] = BeanUtil.TransformRecPayment((com.ffusion.beans.billpay.RecPayment)localRecPayments.get(j));
      }
    }
    return arrayOfRecPayment;
  }
  
  public com.ffusion.webservices.beans.Account[] getConsumerTransferFromAccounts(String paramString)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.getBankingAccounts");
    Base.validateSession(paramString);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    GetConsumerTransferAccounts localGetConsumerTransferAccounts = new GetConsumerTransferAccounts();
    Base.processTask(localGetConsumerTransferAccounts, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute("TransferFromAccounts");
    com.ffusion.webservices.beans.Account[] arrayOfAccount = null;
    if (localAccounts != null)
    {
      int i = localAccounts.size();
      arrayOfAccount = new com.ffusion.webservices.beans.Account[i];
      for (int j = 0; j < i; j++) {
        arrayOfAccount[j] = BeanUtil.TransformAccount((com.ffusion.beans.accounts.Account)localAccounts.get(j));
      }
    }
    return arrayOfAccount;
  }
  
  public com.ffusion.webservices.beans.Account[] getConsumerTransferToAccounts(String paramString)
    throws WSException
  {
    DebugLog.log("com.ffusion.webservice.api.EFS.getBankingAccounts");
    Base.validateSession(paramString);
    HttpServletFacade localHttpServletFacade = new HttpServletFacade(new ServletContextFacade());
    HttpServletRequestFacade localHttpServletRequestFacade = new HttpServletRequestFacade();
    HttpServletResponseFacade localHttpServletResponseFacade = new HttpServletResponseFacade();
    HttpSession localHttpSession = SessionManager.getSessionForID(paramString);
    if (localHttpSession == null)
    {
      DebugLog.log(Level.SEVERE, "session with id : " + paramString + " could not be found");
      throw new WSException("Requested session is not found or has timed out", 8);
    }
    localHttpServletRequestFacade.setSession(localHttpSession);
    GetConsumerTransferAccounts localGetConsumerTransferAccounts = new GetConsumerTransferAccounts();
    Base.processTask(localGetConsumerTransferAccounts, localHttpServletFacade, localHttpServletRequestFacade, localHttpServletResponseFacade);
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute("TransferToAccounts");
    com.ffusion.webservices.beans.Account[] arrayOfAccount = null;
    if (localAccounts != null)
    {
      int i = localAccounts.size();
      arrayOfAccount = new com.ffusion.webservices.beans.Account[i];
      for (int j = 0; j < i; j++) {
        arrayOfAccount[j] = BeanUtil.TransformAccount((com.ffusion.beans.accounts.Account)localAccounts.get(j));
      }
    }
    return arrayOfAccount;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.api.EFS
 * JD-Core Version:    0.7.0.1
 */