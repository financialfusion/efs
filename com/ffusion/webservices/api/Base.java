package com.ffusion.webservices.api;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.tasks.Task;
import com.ffusion.tasks.util.TaskUtil;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.webservices.util.BeanUtil;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Base
{
  protected static void processTask(Task paramTask, HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws WSException
  {
    String str1 = paramTask.getClass().getName();
    String str2 = null;
    try
    {
      DebugLog.log(Level.FINEST, "Calling " + str1 + ".process");
      str2 = paramTask.process(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
    }
    catch (IOException localIOException)
    {
      DebugLog.log(Level.SEVERE, "IO error in " + str1 + " : " + localIOException.toString());
      throw new WSException("IO error occured, unable to process request", 3);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing("Exception in processTask: ", localThrowable);
      throw new WSException("Unable to process request: Unknown error occured with error code : " + paramTask.getError(), 3);
    }
    int i = 0;
    try
    {
      i = Integer.parseInt(paramTask.getError());
    }
    catch (Exception localException) {}
    if ((str2 != null) || (i != 0))
    {
      if ("TE".equals(str2))
      {
        DebugLog.log(Level.WARNING, "Task error occured in " + str1 + " with error code : " + i);
        throw new WSException("Unable to process request: Task error occured with error code : " + i + " " + TaskUtil.getTaskErrorDescription(i, Locale.US), 4, i);
      }
      if ("SE".equals(str2))
      {
        DebugLog.log(Level.SEVERE, "Service error occured in " + str1 + " with error code : " + i);
        throw new WSException("Unable to process request: Service error occured with error code : " + i + " " + TaskUtil.getServiceErrorDescription(i, Locale.US), 5);
      }
      DebugLog.log(Level.SEVERE, "Unknown error occured in " + str1 + " with error code : " + i);
      throw new WSException("Unable to process request: Unknown error occured with error code : " + i, 3);
    }
  }
  
  protected static Account getAccountFromSession(HttpSession paramHttpSession, String paramString)
    throws WSException
  {
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute("Accounts");
    if (localAccounts == null)
    {
      DebugLog.log(Level.SEVERE, "Accountscould not be found in the session");
      throw new WSException("User has not accounts or get accounts failed, no accounts found", 9);
    }
    Account localAccount = localAccounts.getByID(paramString);
    if (localAccount == null)
    {
      DebugLog.log(Level.SEVERE, "Account with id " + paramString + " could not be found in the accounts collection");
      throw new WSException("Account not found or id : " + paramString, 10);
    }
    return localAccount;
  }
  
  protected static void validateSession(String paramString)
    throws WSException
  {
    if (paramString == null)
    {
      DebugLog.log(Level.SEVERE, "null sessionID");
      throw new WSException("Invalid session or session not provided", 6);
    }
    DebugLog.log(Level.FINEST, "sessionID : " + paramString);
  }
  
  protected static void validateAccount(String paramString1, String paramString2)
    throws WSException
  {
    if (paramString1 == null)
    {
      DebugLog.log(Level.SEVERE, "null accoundID");
      throw new WSException("Invalid account id or account id not provided", 11);
    }
    if (paramString2 == null)
    {
      DebugLog.log(Level.SEVERE, "null bankID");
      throw new WSException("Invalid bank id or bank id not provided", 12);
    }
  }
  
  protected static void validateTransferID(String paramString)
    throws WSException
  {
    if (paramString == null)
    {
      DebugLog.log(Level.SEVERE, "null transferID");
      throw new WSException("Invalid transfer or transfer not provided", 7);
    }
  }
  
  protected static void validateTransfer(com.ffusion.webservices.beans.Transfer paramTransfer)
    throws WSException
  {
    if (paramTransfer == null)
    {
      DebugLog.log(Level.SEVERE, "null transfer");
      throw new WSException("Invalid transfer or transfer not provided", 7);
    }
  }
  
  protected static void validatePayee(com.ffusion.webservices.beans.Payee paramPayee)
    throws WSException
  {
    if (paramPayee == null)
    {
      DebugLog.log(Level.SEVERE, "null payee");
      throw new WSException("Invalid payee or payee not provided", 14);
    }
  }
  
  protected static void validatePayeeID(String paramString)
    throws WSException
  {
    if (paramString == null)
    {
      DebugLog.log(Level.SEVERE, "null payeeID");
      throw new WSException("Invalid payee or payee not provided", 14);
    }
  }
  
  protected static void validatePayment(com.ffusion.webservices.beans.Payment paramPayment)
    throws WSException
  {
    if (paramPayment == null)
    {
      DebugLog.log(Level.SEVERE, "null payment");
      throw new WSException("Invalid payment or payment not provided", 13);
    }
  }
  
  protected static void validatePaymentID(String paramString)
    throws WSException
  {
    if (paramString == null)
    {
      DebugLog.log(Level.SEVERE, "null paymentID");
      throw new WSException("Invalid payment or payment not provided", 13);
    }
  }
  
  protected static void updateTransfer(HttpSession paramHttpSession, com.ffusion.beans.banking.Transfer paramTransfer, com.ffusion.webservices.beans.Transfer paramTransfer1)
    throws WSException
  {
    BeanUtil.UpdateTransfer(paramTransfer, paramTransfer1);
    paramTransfer.setFromAccount(getAccountFromSession(paramHttpSession, paramTransfer.getFromAccountID()));
    paramTransfer.setToAccount(getAccountFromSession(paramHttpSession, paramTransfer.getToAccountID()));
  }
  
  protected static void updateRecTransfer(HttpSession paramHttpSession, com.ffusion.beans.banking.RecTransfer paramRecTransfer, com.ffusion.webservices.beans.RecTransfer paramRecTransfer1)
    throws WSException
  {
    BeanUtil.UpdateRecTransfer(paramRecTransfer, paramRecTransfer1);
    paramRecTransfer.setFromAccount(getAccountFromSession(paramHttpSession, paramRecTransfer.getFromAccountID()));
    paramRecTransfer.setToAccount(getAccountFromSession(paramHttpSession, paramRecTransfer.getToAccountID()));
  }
  
  protected static void updatePayment(HttpSession paramHttpSession, com.ffusion.beans.billpay.Payment paramPayment, com.ffusion.webservices.beans.Payment paramPayment1)
    throws WSException
  {
    BeanUtil.UpdatePayment(paramPayment, paramPayment1);
    paramPayment.setAccount(getAccountFromSession(paramHttpSession, paramPayment1.getAccountID()));
    paramPayment.setPayee(getPayeeFromSession(paramHttpSession, paramPayment1.getPayeeID()));
  }
  
  protected static com.ffusion.beans.billpay.Payee getPayeeFromSession(HttpSession paramHttpSession, String paramString)
    throws WSException
  {
    Payees localPayees = (Payees)paramHttpSession.getAttribute("Payees");
    com.ffusion.beans.billpay.Payee localPayee = localPayees.getByID(paramString);
    if (localPayee == null)
    {
      DebugLog.log(Level.SEVERE, "payee with id : " + paramString + " could not be found");
      throw new WSException("Payee not found : ", 15);
    }
    return localPayee;
  }
  
  protected static com.ffusion.beans.billpay.Payment getPaymentFromSession(HttpSession paramHttpSession, String paramString)
    throws WSException
  {
    Payments localPayments = (Payments)paramHttpSession.getAttribute("Payments");
    com.ffusion.beans.billpay.Payment localPayment = localPayments.getByID(paramString);
    if (localPayment == null)
    {
      DebugLog.log(Level.SEVERE, "payment with id : " + paramString + " could not be found");
      throw new WSException("Payment not found : ", 16);
    }
    return localPayment;
  }
  
  protected static RecPayment getRecPaymentFromSession(HttpSession paramHttpSession, String paramString)
    throws WSException
  {
    RecPayments localRecPayments = (RecPayments)paramHttpSession.getAttribute("RecPayments");
    RecPayment localRecPayment = (RecPayment)localRecPayments.getByID(paramString);
    if (localRecPayment == null)
    {
      DebugLog.log(Level.SEVERE, "payment with id : " + paramString + " could not be found");
      throw new WSException("Payment not found : ", 16);
    }
    return localRecPayment;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.api.Base
 * JD-Core Version:    0.7.0.1
 */