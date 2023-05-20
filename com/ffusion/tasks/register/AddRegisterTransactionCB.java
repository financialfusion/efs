package com.ffusion.tasks.register;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.register.RegisterCategories;
import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.beans.register.TransactionCategories;
import com.ffusion.beans.util.RegisterUtil;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddRegisterTransactionCB
  extends EditRegisterTransactionCB
  implements Task
{
  protected String _accountID = null;
  protected String _accountSessionName = "Account";
  
  public AddRegisterTransactionCB()
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
    if (!this.splitCategories)
    {
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
    }
    RegisterTransaction localRegisterTransaction = (RegisterTransaction)localHttpSession.getAttribute(this.beanSessionName);
    if (localRegisterTransaction == null)
    {
      this.error = 20003;
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
    TransactionCategories localTransactionCategories = localRegisterTransaction.getTransactionCategories();
    if ((localTransactionCategories.hasNegativeAmount()) || (localTransactionCategories.hasInvalidAmount()))
    {
      this.error = 20102;
      return this.taskErrorURL;
    }
    try
    {
      HashMap localHashMap = new HashMap();
      RegisterCategories localRegisterCategories = Register.getRegisterCategories(localSecureUser, localHashMap);
      if (!RegisterUtil.isTransactionCreditType(localRegisterTransaction.getRegisterTypeValue())) {
        localRegisterTransaction.setAmountsNegative();
      }
      localRegisterTransaction.setCurrent(null);
      setTotalAmount(localRegisterTransaction.getAmountValue().getAmountValue().toString());
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.taskErrorURL;
    }
    if (validateInput(localRegisterTransaction))
    {
      if (this.totalAmountCurrency != null) {
        localRegisterTransaction.setCurrency(this.totalAmountCurrency);
      }
      if (localAccount == null)
      {
        if (!jdMethod_for(localHttpSession, localRegisterTransaction, this._accountID, localSecureUser)) {
          return this.serviceErrorURL;
        }
      }
      else if (RegisterUtil.isRegisterEnabled(localAccount))
      {
        if (!jdMethod_for(localHttpSession, localRegisterTransaction, localAccount.getID(), localSecureUser)) {
          return this.serviceErrorURL;
        }
      }
      else
      {
        this.error = 20013;
        return super.getTaskErrorURL();
      }
    }
    else
    {
      localRegisterTransaction.setAmountsPositive();
      if (Double.parseDouble(this.totalAmount) < 0.0D) {
        setTotalAmount(Double.parseDouble(this.totalAmount) * -1.0D + "");
      }
      return this.taskErrorURL;
    }
    return this.successURL;
  }
  
  private boolean jdMethod_for(HttpSession paramHttpSession, RegisterTransaction paramRegisterTransaction, String paramString, SecureUser paramSecureUser)
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
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.AddRegisterTransactionCB
 * JD-Core Version:    0.7.0.1
 */