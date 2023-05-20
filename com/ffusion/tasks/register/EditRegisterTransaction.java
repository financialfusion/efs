package com.ffusion.tasks.register;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.register.RegisterPayee;
import com.ffusion.beans.register.RegisterPayees;
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
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditRegisterTransaction
  extends ExtendedBaseTask
  implements Task
{
  protected String totalAmount = null;
  protected String totalAmountCurrency = null;
  protected boolean splitCategories = false;
  protected boolean debitType = false;
  protected String _addNewPayeeToList = "false";
  protected RegisterPayees _registerPayees = null;
  protected String _registerPayeesCollectionName = "RegisterPayees";
  protected boolean _respectDebitTypeOnly = false;
  protected int originalRegisterType = -1;
  protected String originalAmount = null;
  protected String originalAmountCurrency = null;
  protected String originalReferenceNumber = null;
  protected String originalIssuedDate = null;
  private String EQ = null;
  private String ER = Boolean.FALSE.toString();
  
  public EditRegisterTransaction()
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
    this._registerPayees = ((RegisterPayees)localHttpSession.getAttribute(this._registerPayeesCollectionName));
    RegisterTransaction localRegisterTransaction = (RegisterTransaction)localHttpSession.getAttribute(this.beanSessionName);
    if (localRegisterTransaction == null)
    {
      this.error = 20003;
      return this.taskErrorURL;
    }
    convertEmptyStringsToNull(localRegisterTransaction);
    if (!validateInput(localRegisterTransaction, this._addNewPayeeToList, this._registerPayees)) {
      return this.taskErrorURL;
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
    if (!this.splitCategories)
    {
      localRegisterTransaction.setCurrent(null);
      localRegisterTransaction.setAmount(this.totalAmount);
      if (this.totalAmountCurrency != null) {
        localRegisterTransaction.getAmountValue().setCurrencyCode(this.totalAmountCurrency);
      }
    }
    if (this._respectDebitTypeOnly)
    {
      if (this.debitType) {
        localRegisterTransaction.setAmountsNegative();
      }
    }
    else if ((!RegisterUtil.isTransactionCreditType(localRegisterTransaction.getRegisterTypeValue())) || (this.debitType)) {
      localRegisterTransaction.setAmountsNegative();
    }
    if (!modifyRegisterTransaction(localHttpSession, localRegisterTransaction, localSecureUser)) {
      return this.serviceErrorURL;
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
  
  private boolean jdMethod_for(String paramString, RegisterPayees paramRegisterPayees)
  {
    Iterator localIterator = paramRegisterPayees.iterator();
    if (paramString == null) {
      return false;
    }
    while (localIterator.hasNext())
    {
      String str = ((RegisterPayee)localIterator.next()).getName();
      if (paramString.equals(str)) {
        return true;
      }
    }
    return false;
  }
  
  protected boolean validateInput(RegisterTransaction paramRegisterTransaction, String paramString, RegisterPayees paramRegisterPayees)
  {
    String str1 = paramRegisterTransaction.getReferenceNumber();
    DateTime localDateTime = paramRegisterTransaction.getDateIssuedValue();
    paramRegisterTransaction.setCurrent(null);
    Currency localCurrency = paramRegisterTransaction.getAmountValue();
    Object localObject1;
    Object localObject2;
    if (this.validate != null)
    {
      if ((this.validate.indexOf("TYPE") != -1) && (paramRegisterTransaction.getTypeValue() == -1))
      {
        this.error = 20104;
        return false;
      }
      if ((this.validate.indexOf("REGISTER_TYPE") != -1) && (paramRegisterTransaction.getRegisterTypeValue() == -1))
      {
        this.error = 20131;
        return false;
      }
      if ((this.validate.indexOf("DATE_ISSUED") != -1) && ((localDateTime == null) || (!localDateTime.isValid()) || (localDateTime.toString().length() > 10)))
      {
        this.error = 20116;
        return false;
      }
      if ((this.validate.indexOf("AMOUNT") != -1) && ((localCurrency == null) || (localCurrency.toString().length() > 53)))
      {
        this.error = 20102;
        return false;
      }
      if (((paramRegisterTransaction.getPayeeName() != null) && (paramRegisterTransaction.getPayeeName().length() > 40)) || (((paramRegisterTransaction.getPayeeName() == null) || (paramRegisterTransaction.getPayeeName().length() == 0)) && ("true".equals(paramString))))
      {
        this.error = 20108;
        return false;
      }
      if ((jdMethod_for(paramRegisterTransaction.getPayeeName(), paramRegisterPayees)) && ("true".equals(paramString)))
      {
        this.error = 20127;
        return false;
      }
      if ((paramRegisterTransaction.getMemo() != null) && (paramRegisterTransaction.getMemo().length() > 255))
      {
        this.error = 20128;
        return false;
      }
      if ((str1 != null) && (str1.length() > 20))
      {
        this.error = 20103;
        return false;
      }
      TransactionCategories localTransactionCategories = paramRegisterTransaction.getTransactionCategories();
      if ((localTransactionCategories == null) || (localTransactionCategories.size() == 0))
      {
        this.error = 20114;
        return false;
      }
      if (localTransactionCategories.hasInvalidCategory())
      {
        this.error = 20101;
        return false;
      }
      if ((localTransactionCategories.hasNegativeAmount()) || (localTransactionCategories.hasInvalidAmount()))
      {
        this.error = 20102;
        return false;
      }
      for (int j = 0; j < localTransactionCategories.size(); j++)
      {
        localObject1 = (TransactionCategory)localTransactionCategories.get(j);
        if (localObject1 == null)
        {
          this.error = 20005;
          return false;
        }
        if (((TransactionCategory)localObject1).hasInvalidAmount())
        {
          this.error = 20130;
          return false;
        }
      }
      if (this.splitCategories)
      {
        ArrayList localArrayList = new ArrayList();
        localObject1 = localTransactionCategories.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          TransactionCategory localTransactionCategory = (TransactionCategory)((Iterator)localObject1).next();
          localObject2 = localTransactionCategory.getRegisterCategoryId();
          if (localArrayList.contains(localObject2))
          {
            this.error = 20124;
            return false;
          }
          localArrayList.add(localObject2);
        }
        try
        {
          double d = Double.parseDouble(this.totalAmount);
          localTransactionCategories.setCurrent(null);
          if ((localTransactionCategories.getAmountValue() == null) || (!localTransactionCategories.getAmountValue().equals(d)))
          {
            this.error = 20120;
            return false;
          }
        }
        catch (NumberFormatException localNumberFormatException)
        {
          this.error = 20102;
          return false;
        }
      }
      this.validate = null;
    }
    int i = paramRegisterTransaction.getStatusValue();
    int k = paramRegisterTransaction.getRegisterTypeValue();
    if ((i == 2) || (i == 3) || (i == 4) || (k == 25) || (k == 16))
    {
      if (paramRegisterTransaction.getRegisterTypeValue() != this.originalRegisterType)
      {
        this.error = 20134;
        return false;
      }
      if ((this.originalReferenceNumber != null) && (!this.originalReferenceNumber.equals(str1)))
      {
        this.error = 20135;
        return false;
      }
      localObject1 = localDateTime.getString();
      if ((localObject1 == null) || (!((String)localObject1).equals(this.originalIssuedDate)))
      {
        this.error = 20136;
        return false;
      }
      String str2 = localCurrency.getStringAbsolute();
      localObject2 = null;
      String str3 = null;
      if (this.originalAmountCurrency != null)
      {
        localObject2 = new Currency(this.originalAmount, this.originalAmountCurrency, this.locale);
        str3 = ((Currency)localObject2).getStringAbsolute();
      }
      if ((str2 != null) && (!str2.equals(str3)))
      {
        this.error = 20133;
        return false;
      }
    }
    return true;
  }
  
  public void setOriginalAmount(String paramString)
  {
    this.originalAmount = paramString;
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
  
  public String getTotalAmountWithSymbols()
  {
    if (this.totalAmountCurrency == null) {
      return this.totalAmount;
    }
    return new Currency(this.totalAmount, this.totalAmountCurrency, this.locale).toString();
  }
  
  public String getOriginalAmountWithSymbols()
  {
    if (this.originalAmountCurrency == null) {
      return this.originalAmount;
    }
    return new Currency(this.originalAmount, this.originalAmountCurrency, this.locale).toString();
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
  
  public void setOriginalAmountCurrency(String paramString)
  {
    this.originalAmountCurrency = paramString;
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
  
  protected void convertEmptyStringsToNull(RegisterTransaction paramRegisterTransaction)
  {
    String str1 = paramRegisterTransaction.getReferenceNumber();
    String str2 = paramRegisterTransaction.getMemo();
    String str3 = paramRegisterTransaction.getPayeeName();
    if ((str1 != null) && (str1.length() == 0)) {
      paramRegisterTransaction.setReferenceNumber(null);
    }
    if ((str2 != null) && (str2.length() == 0)) {
      paramRegisterTransaction.setMemo(null);
    }
    if ((str3 != null) && (str3.length() == 0)) {
      paramRegisterTransaction.setPayeeName(null);
    }
  }
  
  public void setOriginalReferenceNumber(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.originalReferenceNumber = null;
    } else {
      this.originalReferenceNumber = paramString;
    }
  }
  
  public void setOriginalRegisterType(String paramString)
  {
    try
    {
      this.originalRegisterType = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this.originalRegisterType = -1;
    }
  }
  
  public void setOriginalIssuedDate(String paramString)
  {
    this.originalIssuedDate = paramString;
  }
  
  public String getOriginalAmount()
  {
    if (this.originalAmountCurrency == null) {
      return this.originalAmount;
    }
    return new Currency(this.originalAmount, this.originalAmountCurrency, this.locale).getCurrencyStringNoSymbolNoComma();
  }
  
  public void setDisableAmountEdit(String paramString)
  {
    this.EQ = paramString;
  }
  
  public void setOrigAmountNegative(String paramString)
  {
    this.ER = paramString;
  }
  
  public String getOrigAmountNegative()
  {
    return this.ER;
  }
  
  public void setRespectDebitTypeFlagOnly(String paramString)
  {
    this._respectDebitTypeOnly = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.EditRegisterTransaction
 * JD-Core Version:    0.7.0.1
 */