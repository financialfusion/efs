package com.ffusion.tasks.register;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.register.RegisterPayees;
import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.beans.register.TransactionCategories;
import com.ffusion.beans.util.RegisterUtil;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddRegisterTransaction
  extends EditRegisterTransaction
  implements Task
{
  protected String _accountID = null;
  protected String _accountSessionName = "Account";
  protected String _addNewPayeeToList = "false";
  protected RegisterPayees _registerPayees = null;
  protected String _registerPayeesCollectionName = "RegisterPayees";
  
  public AddRegisterTransaction()
  {
    this.beanSessionName = "RegisterTransaction";
    this.collectionSessionName = "RegisterTransactions";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    this._registerPayees = ((RegisterPayees)localHttpSession.getAttribute(this._registerPayeesCollectionName));
    if (localSecureUser == null)
    {
      this.error = 20009;
      return this.taskErrorURL;
    }
    Account localAccount = null;
    if ((this._accountID == null) || (this._accountID.length() <= 0))
    {
      localAccount = (Account)localHttpSession.getAttribute(this._accountSessionName);
      if (localAccount == null)
      {
        this.error = 20001;
        return this.taskErrorURL;
      }
    }
    if ((this.totalAmount == null) || (this.totalAmount.length() <= 0))
    {
      this.error = 20102;
      return this.taskErrorURL;
    }
    try
    {
      double d = Double.parseDouble(this.totalAmount);
      if (d < 0.0D)
      {
        this.error = 20130;
        return this.taskErrorURL;
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 20102;
      return this.taskErrorURL;
    }
    RegisterTransaction localRegisterTransaction = (RegisterTransaction)localHttpSession.getAttribute(this.beanSessionName);
    if (localRegisterTransaction == null)
    {
      this.error = 20003;
      return this.taskErrorURL;
    }
    if (!validateInput(localRegisterTransaction, this._addNewPayeeToList, this._registerPayees)) {
      return this.taskErrorURL;
    }
    if ((!this.splitCategories) && (localRegisterTransaction.getRegisterTypeValue() != 25) && (localRegisterTransaction.getRegisterTypeValue() != 16))
    {
      localRegisterTransaction.setCurrent(null);
      localRegisterTransaction.setAmount(this.totalAmount);
      if (this.totalAmountCurrency != null) {
        localRegisterTransaction.getAmountValue().setCurrencyCode(this.totalAmountCurrency);
      }
    }
    convertEmptyStringsToNull(localRegisterTransaction);
    if (!RegisterUtil.isTransactionCreditType(localRegisterTransaction.getRegisterTypeValue())) {
      localRegisterTransaction.setAmountsNegative();
    }
    if (localAccount == null)
    {
      if (!jdMethod_int(localHttpSession, localRegisterTransaction, this._accountID, localSecureUser)) {
        return this.serviceErrorURL;
      }
    }
    else if (RegisterUtil.isRegisterEnabled(localAccount))
    {
      if (!jdMethod_int(localHttpSession, localRegisterTransaction, localAccount.getID(), localSecureUser)) {
        return this.serviceErrorURL;
      }
    }
    else
    {
      this.error = 20013;
      return super.getTaskErrorURL();
    }
    return this.successURL;
  }
  
  private boolean jdMethod_int(HttpSession paramHttpSession, RegisterTransaction paramRegisterTransaction, String paramString, SecureUser paramSecureUser)
  {
    paramRegisterTransaction.getTransactionCategories().clearInvalidTransactionCategories();
    paramRegisterTransaction.set("ACCOUNT", paramString);
    try
    {
      paramRegisterTransaction = Register.addRegisterTransaction(paramSecureUser, paramRegisterTransaction, new HashMap());
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      RegisterTransactions localRegisterTransactions = (RegisterTransactions)paramHttpSession.getAttribute(this.collectionSessionName);
      if (localRegisterTransactions != null)
      {
        localRegisterTransactions.add(paramRegisterTransaction);
        paramHttpSession.setAttribute(this.collectionSessionName, localRegisterTransactions);
      }
    }
    else
    {
      return false;
    }
    return true;
  }
  
  public void setAccountID(String paramString)
  {
    this._accountID = paramString;
  }
  
  public String getAccountID()
  {
    return this._accountID;
  }
  
  public void setAccountSessionName(String paramString)
  {
    this._accountSessionName = paramString;
  }
  
  public String getAccountSessionName()
  {
    return this._accountSessionName;
  }
  
  public String getAddNewPayeeToList()
  {
    return this._addNewPayeeToList;
  }
  
  public void setAddNewPayeeToList(String paramString)
  {
    this._addNewPayeeToList = paramString;
  }
  
  public RegisterPayees getRegisterPayees()
  {
    return this._registerPayees;
  }
  
  public void setRegisterPayees(RegisterPayees paramRegisterPayees)
  {
    this._registerPayees = paramRegisterPayees;
  }
  
  public String getRegisterPayeesCollectionName()
  {
    return this._registerPayeesCollectionName;
  }
  
  public void setRegisterPayeesCollectionName(String paramString)
  {
    this._registerPayeesCollectionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.AddRegisterTransaction
 * JD-Core Version:    0.7.0.1
 */