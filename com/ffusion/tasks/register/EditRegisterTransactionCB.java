package com.ffusion.tasks.register;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.register.RegisterCategories;
import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.beans.register.TransactionCategories;
import com.ffusion.beans.register.TransactionCategory;
import com.ffusion.beans.util.RegisterUtil;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditRegisterTransactionCB
  extends ExtendedBaseTask
  implements Task
{
  protected String totalAmount = null;
  protected String totalAmountCurrency = null;
  protected boolean splitCategories = false;
  protected boolean debitType = false;
  
  public EditRegisterTransactionCB()
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
    RegisterTransaction localRegisterTransaction = (RegisterTransaction)localHttpSession.getAttribute(this.beanSessionName);
    if (localRegisterTransaction == null)
    {
      this.error = 20003;
      return this.taskErrorURL;
    }
    double d;
    try
    {
      d = Double.parseDouble(this.totalAmount);
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
    if (!this.splitCategories)
    {
      if ((this.totalAmount == null) || (this.totalAmount.length() <= 0))
      {
        this.error = 20102;
        return this.taskErrorURL;
      }
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
      int i = 0;
      if ((localRegisterTransaction.getStatusValue() == 1) || (RegisterUtil.isReconciled(localRegisterTransaction.getStatusValue()))) {
        i = 1;
      }
      if (((i == 0) && (!RegisterUtil.isTransactionCreditType(localRegisterTransaction.getRegisterTypeValue()))) || (this.debitType)) {
        localRegisterTransaction.setAmountsNegative();
      }
      if (localRegisterTransaction.getStatusValue() == 0)
      {
        localRegisterTransaction.setCurrent(null);
        setTotalAmount(localRegisterTransaction.getAmountValue().getAmountValue().toString());
      }
      else if ((localRegisterTransaction.getStatusValue() != 0) && (this.debitType) && (d > 0.0D))
      {
        setTotalAmount(d * -1.0D + "");
      }
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
      if (!modifyRegisterTransaction(localHttpSession, localRegisterTransaction, localSecureUser)) {
        return this.serviceErrorURL;
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
  
  protected boolean modifyRegisterTransaction(HttpSession paramHttpSession, RegisterTransaction paramRegisterTransaction, SecureUser paramSecureUser)
  {
    paramRegisterTransaction.getTransactionCategories().clearInvalidTransactionCategories();
    try
    {
      Register.modifyRegisterTransaction(paramSecureUser, paramRegisterTransaction, null);
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
        localRegisterTransactions.removeByRegisterId(paramRegisterTransaction.getRegisterId());
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
  
  protected boolean validateInput(RegisterTransaction paramRegisterTransaction)
  {
    if (this.validate != null)
    {
      if ((this.validate.indexOf("TYPE") != -1) && (paramRegisterTransaction.getTypeValue() == 0))
      {
        this.error = 20104;
        return false;
      }
      if (this.validate.indexOf("DATE_ISSUED") != -1)
      {
        localObject = paramRegisterTransaction.getDateIssuedValue();
        if ((localObject == null) || (!((DateTime)localObject).isValid()) || (((DateTime)localObject).toString().length() > 10))
        {
          this.error = 20116;
          return false;
        }
      }
      paramRegisterTransaction.setCurrent(null);
      Object localObject = paramRegisterTransaction.getAmountValue();
      if ((this.validate.indexOf("AMOUNT") != -1) && ((localObject == null) || (((Currency)localObject).toString().length() > 53)))
      {
        this.error = 20102;
        return false;
      }
      if ((paramRegisterTransaction.getPayeeName() != null) && (paramRegisterTransaction.getPayeeName().length() > 40))
      {
        this.error = 20108;
        return false;
      }
      if ((paramRegisterTransaction.getMemo() != null) && (paramRegisterTransaction.getMemo().length() > 255))
      {
        this.error = 20128;
        return false;
      }
      if ((paramRegisterTransaction.getReferenceNumber() != null) && (paramRegisterTransaction.getReferenceNumber().length() > 20))
      {
        this.error = 20103;
        return false;
      }
      TransactionCategories localTransactionCategories = paramRegisterTransaction.getTransactionCategories();
      if (localTransactionCategories == null)
      {
        this.error = 20114;
        return false;
      }
      if (localTransactionCategories.hasInvalidCategory())
      {
        this.error = 20101;
        return false;
      }
      if (this.splitCategories)
      {
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = localTransactionCategories.iterator();
        while (localIterator.hasNext())
        {
          TransactionCategory localTransactionCategory = (TransactionCategory)localIterator.next();
          String str = localTransactionCategory.getRegisterCategoryId();
          if (localArrayList.contains(str))
          {
            this.error = 20124;
            return false;
          }
          localArrayList.add(str);
        }
      }
      try
      {
        double d = Double.parseDouble(this.totalAmount);
        localTransactionCategories.setCurrent(null);
        if ((localTransactionCategories.getAmountValue() == null) || (!localTransactionCategories.getAmountValue().equals(d)))
        {
          if (localTransactionCategories.getAmountValue().equals(d * -1.0D))
          {
            this.error = 20129;
            return false;
          }
          this.error = 20120;
          return false;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        this.error = 20102;
        return false;
      }
      this.validate = null;
    }
    return true;
  }
  
  public void setTotalAmount(String paramString)
  {
    this.totalAmount = paramString;
  }
  
  public String getTotalAmount()
  {
    if (this.totalAmountCurrency == null) {
      return this.totalAmount;
    }
    return new Currency(this.totalAmount, this.totalAmountCurrency, this.locale).getCurrencyStringNoSymbolNoComma();
  }
  
  public void setSplitCategories(String paramString)
  {
    this.splitCategories = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setSplitCategories(boolean paramBoolean)
  {
    this.splitCategories = paramBoolean;
  }
  
  public boolean getSplitCategories()
  {
    return this.splitCategories;
  }
  
  public void setDebitType(String paramString)
  {
    this.debitType = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getTotalAmountCurrency()
  {
    return this.totalAmountCurrency;
  }
  
  public void setTotalAmountCurrency(String paramString)
  {
    this.totalAmountCurrency = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.EditRegisterTransactionCB
 * JD-Core Version:    0.7.0.1
 */