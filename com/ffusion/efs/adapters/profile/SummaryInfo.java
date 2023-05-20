package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.csil.core.FX;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class SummaryInfo
{
  private ArrayList jdField_new = new ArrayList();
  private ArrayList jdField_for = new ArrayList();
  private ArrayList jdField_int = new ArrayList();
  private ArrayList a = new ArrayList();
  private ArrayList jdField_byte = new ArrayList();
  private ArrayList jdField_if = new ArrayList();
  private ArrayList jdField_case = new ArrayList();
  private ArrayList jdField_try = new ArrayList();
  private ArrayList jdField_do = new ArrayList();
  public String prevBusiness = null;
  public String prevFromAcct = null;
  public long numRows = 0L;
  public String prevStatus = null;
  public String currentCurrencyCode = "USD";
  public String currBusiness;
  public String currFromAccount;
  public String currStatus;
  public boolean sameBusiness = false;
  public boolean sameAccount = false;
  public boolean bLastBusiness = false;
  private SecureUser jdField_char;
  public Locale locale;
  
  public void setLocale(Locale paramLocale)
  {
    this.locale = paramLocale;
  }
  
  public Locale getLocale()
  {
    return this.locale;
  }
  
  public void setUser(SecureUser paramSecureUser)
  {
    if (paramSecureUser.getBaseCurrency() == null) {
      paramSecureUser.setBaseCurrency("USD");
    }
    this.jdField_char = paramSecureUser;
  }
  
  public SecureUser getUser()
  {
    return this.jdField_char;
  }
  
  public void setCurrentCurrencyCode(String paramString)
  {
    this.currentCurrencyCode = paramString;
  }
  
  public String getCurrentCurrencyCode()
  {
    return this.currentCurrencyCode;
  }
  
  public void addGrandTotal(Object paramObject)
  {
    addItemToTotal(paramObject, this.jdField_new);
  }
  
  public void resetGrandTotal()
  {
    this.jdField_new.clear();
  }
  
  public void addBusinessTotal(Object paramObject)
  {
    addItemToTotal(paramObject, this.jdField_for);
  }
  
  public void resetBusinessTotal()
  {
    this.jdField_for.clear();
  }
  
  public void addBankTotal(Object paramObject)
  {
    addItemToTotal(paramObject, this.jdField_int);
  }
  
  public void resetBankTotal()
  {
    this.jdField_int.clear();
  }
  
  public void addACHTotal(Object paramObject)
  {
    addItemToTotal(paramObject, this.jdField_if);
  }
  
  public void resetACHTotal()
  {
    this.jdField_if.clear();
  }
  
  public void addFileTotal(Object paramObject)
  {
    addItemToTotal(paramObject, this.jdField_case);
  }
  
  public void resetFileTotal()
  {
    this.jdField_case.clear();
  }
  
  public void addDivisionTotal(Object paramObject)
  {
    addItemToTotal(paramObject, this.jdField_try);
  }
  
  public void resetDivisionTotal()
  {
    this.jdField_try.clear();
  }
  
  public void addCompanyTotal(Object paramObject)
  {
    addItemToTotal(paramObject, this.jdField_do);
  }
  
  public void resetCompanyTotal()
  {
    this.jdField_do.clear();
  }
  
  public void addAccountTotal(Object paramObject)
  {
    addItemToTotal(paramObject, this.a);
  }
  
  public void resetAccountTotal()
  {
    this.a.clear();
  }
  
  public void addStatusTotal(Object paramObject)
  {
    addItemToTotal(paramObject, this.jdField_byte);
  }
  
  public void resetStatusTotal()
  {
    this.jdField_byte.clear();
  }
  
  protected void addItemToTotal(Object paramObject, ArrayList paramArrayList)
  {
    if (paramObject == null) {
      return;
    }
    Object localObject;
    if ((paramObject instanceof SummaryItem))
    {
      localObject = a(paramArrayList, ((SummaryItem)paramObject).getCurrencyCode());
      ((SummaryItem)localObject).add((SummaryItem)paramObject);
    }
    else if ((paramObject instanceof SummaryItem[]))
    {
      localObject = (SummaryItem[])paramObject;
      for (int i = 0; i < localObject.length; i++)
      {
        SummaryItem localSummaryItem = a(paramArrayList, localObject[i].getCurrencyCode());
        localSummaryItem.add(localObject[i]);
      }
    }
  }
  
  private SummaryItem a(ArrayList paramArrayList, String paramString)
  {
    SummaryItem localSummaryItem = null;
    int i = 0;
    String str = null;
    for (int j = 0; j < paramArrayList.size(); j++)
    {
      str = ((SummaryItem)paramArrayList.get(j)).getCurrencyCode();
      if (paramString.equals(str))
      {
        localSummaryItem = (SummaryItem)paramArrayList.get(j);
        break;
      }
      if (paramString.compareTo(str) > 0) {
        i = j + 1;
      }
    }
    if (localSummaryItem == null)
    {
      localSummaryItem = new SummaryItem();
      localSummaryItem.setCurrencyCode(paramString);
      if (i > paramArrayList.size()) {
        paramArrayList.add(localSummaryItem);
      } else {
        paramArrayList.add(i, localSummaryItem);
      }
    }
    return localSummaryItem;
  }
  
  public SummaryItem getGrandTotalSummaryItem()
  {
    return a(this.jdField_new, this.currentCurrencyCode);
  }
  
  public SummaryItem getBusinessTotalSummaryItem()
  {
    return a(this.jdField_for, this.currentCurrencyCode);
  }
  
  public SummaryItem getDivisionTotalSummaryItem()
  {
    return a(this.jdField_try, this.currentCurrencyCode);
  }
  
  public SummaryItem getCompanyTotalSummaryItem()
  {
    return a(this.jdField_do, this.currentCurrencyCode);
  }
  
  public SummaryItem getBankTotalSummaryItem()
  {
    return a(this.jdField_int, this.currentCurrencyCode);
  }
  
  public SummaryItem getACHTotalSummaryItem()
  {
    return a(this.jdField_if, this.currentCurrencyCode);
  }
  
  public SummaryItem getFileTotalSummaryItem()
  {
    return a(this.jdField_case, this.currentCurrencyCode);
  }
  
  public SummaryItem getAccountTotalSummaryItem()
  {
    return a(this.a, this.currentCurrencyCode);
  }
  
  public SummaryItem getStatusTotalSummaryItem()
  {
    return a(this.jdField_byte, this.currentCurrencyCode);
  }
  
  public SummaryItem[] getBankTotalSummary()
  {
    return (SummaryItem[])this.jdField_int.toArray(new SummaryItem[0]);
  }
  
  public SummaryItem[] getAccountTotalSummary()
  {
    return (SummaryItem[])this.a.toArray(new SummaryItem[0]);
  }
  
  public SummaryItem[] getStatusTotalSummary()
  {
    return (SummaryItem[])this.jdField_byte.toArray(new SummaryItem[0]);
  }
  
  public SummaryItem[] getACHTotalSummary()
  {
    return (SummaryItem[])this.jdField_if.toArray(new SummaryItem[0]);
  }
  
  public SummaryItem[] getFileTotalSummary()
  {
    return (SummaryItem[])this.jdField_case.toArray(new SummaryItem[0]);
  }
  
  public SummaryItem[] getDivisionTotalSummary()
  {
    return (SummaryItem[])this.jdField_try.toArray(new SummaryItem[0]);
  }
  
  public SummaryItem[] getCompanyTotalSummary()
  {
    return (SummaryItem[])this.jdField_do.toArray(new SummaryItem[0]);
  }
  
  public SummaryItem[] getGrandTotalSummary()
  {
    return (SummaryItem[])this.jdField_new.toArray(new SummaryItem[0]);
  }
  
  public SummaryItem[] getBusinessTotalSummary()
  {
    return (SummaryItem[])this.jdField_for.toArray(new SummaryItem[0]);
  }
  
  public Currency getBaseCurrencyTotalCredit(SummaryItem[] paramArrayOfSummaryItem)
  {
    return a(true, paramArrayOfSummaryItem);
  }
  
  public Currency getBaseCurrencyTotalDebit(SummaryItem[] paramArrayOfSummaryItem)
  {
    return a(false, paramArrayOfSummaryItem);
  }
  
  private Currency a(boolean paramBoolean, SummaryItem[] paramArrayOfSummaryItem)
  {
    if (this.jdField_char == null) {
      return null;
    }
    Currency localCurrency1 = new Currency("0.0", this.jdField_char.getBaseCurrency(), this.locale);
    for (int i = 0; i < paramArrayOfSummaryItem.length; i++)
    {
      SummaryItem localSummaryItem = paramArrayOfSummaryItem[i];
      if (localSummaryItem.getCurrencyCode().equals(this.jdField_char.getBaseCurrency()))
      {
        if (paramBoolean) {
          localCurrency1.addAmount(localSummaryItem.totalCreditsAmount);
        } else {
          localCurrency1.addAmount(localSummaryItem.totalDebitsAmount);
        }
      }
      else {
        try
        {
          Currency localCurrency2 = new Currency(paramBoolean ? localSummaryItem.totalCreditsAmount : localSummaryItem.totalDebitsAmount, localSummaryItem.currencyCode, this.locale);
          localCurrency1.addAmount(FX.convertToBaseCurrency(this.jdField_char, localCurrency2, new HashMap()));
        }
        catch (Exception localException)
        {
          localException.printStackTrace(System.out);
        }
      }
    }
    return localCurrency1;
  }
  
  public Currency getBusinessGrandTotalCredit()
  {
    return a(true, getBusinessTotalSummary());
  }
  
  public Currency getBusinessGrandTotalDebit()
  {
    return a(false, getBusinessTotalSummary());
  }
  
  public Currency getGrandTotalCredit()
  {
    return a(true, getGrandTotalSummary());
  }
  
  public Currency getGrandTotalDebit()
  {
    return a(false, getGrandTotalSummary());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.SummaryInfo
 * JD-Core Version:    0.7.0.1
 */