package com.ffusion.beans.accounts;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class AcctGeneralLedgerRpt
  extends ExtendABean
  implements IReportResult, ExportFormats, Serializable
{
  public static final String ACCT_GENERAL_LEDGER_RPT = "ACCT_GENERAL_LEDGER_RPT";
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  private Accounts Sw = null;
  
  public AcctGeneralLedgerRpt(Locale paramLocale)
  {
    super.setLocale(paramLocale);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof AcctGeneralLedgerRpt))) {
      return false;
    }
    AcctGeneralLedgerRpt localAcctGeneralLedgerRpt = (AcctGeneralLedgerRpt)paramObject;
    Accounts localAccounts = localAcctGeneralLedgerRpt.getAccounts();
    if (this.Sw == null) {
      return localAccounts == null;
    }
    return this.Sw.equals(localAccounts);
  }
  
  public void setAccounts(Accounts paramAccounts)
  {
    this.Sw = paramAccounts;
  }
  
  public Accounts getAccounts()
  {
    return this.Sw;
  }
  
  public ArrayList getReportOutput()
  {
    ArrayList localArrayList = new ArrayList();
    if (this.Sw == null) {
      return null;
    }
    for (int i = 0; i < this.Sw.size(); i++)
    {
      Account localAccount = (Account)this.Sw.get(i);
      Transactions localTransactions = localAccount.getTransactions();
      Currency localCurrency1 = new Currency();
      localCurrency1.setAmount(new BigDecimal(0.0D));
      Currency localCurrency2 = new Currency();
      localCurrency2.setAmount(new BigDecimal(0.0D));
      Locale localLocale = null;
      if (localTransactions != null) {
        for (int j = 0; j < localTransactions.size(); j++)
        {
          Transaction localTransaction = (Transaction)localTransactions.get(j);
          Currency localCurrency3 = localTransaction.getAmountValue();
          if (localCurrency3 != null)
          {
            if (localLocale == null) {
              localLocale = localCurrency3.getLocale();
            }
            if (localCurrency3.doubleValue() >= 0.0D) {
              localCurrency1.addAmount(localCurrency3);
            } else {
              localCurrency2.addAmount(localCurrency3);
            }
          }
        }
      }
      localCurrency2.setLocale(localLocale);
      localCurrency1.setLocale(localLocale);
      localArrayList.add(localAccount);
      localArrayList.add(localCurrency2);
      localArrayList.add(localCurrency1);
    }
    return localArrayList;
  }
  
  public Object export(String paramString, HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = null;
    if (paramString.equals("COMMA")) {
      localStringBuffer = getDelimitedReport(',');
    } else if (paramString.equals("TAB")) {
      localStringBuffer = getDelimitedReport('\t');
    }
    return localStringBuffer;
  }
  
  protected StringBuffer getDelimitedReport(char paramChar)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.Sw != null)
    {
      Iterator localIterator1 = this.Sw.iterator();
      Account localAccount = null;
      while (localIterator1.hasNext())
      {
        localStringBuffer.append(ReportConsts.getColumnName(225, this.locale));
        localStringBuffer.append(paramChar);
        localStringBuffer.append(ReportConsts.getColumnName(226, this.locale));
        localStringBuffer.append(paramChar);
        localStringBuffer.append(ReportConsts.getColumnName(227, this.locale));
        localStringBuffer.append(_lineSeparator);
        localAccount = (Account)localIterator1.next();
        String str1 = localAccount.getID();
        if (str1 != null) {
          localStringBuffer.append(str1);
        }
        localStringBuffer.append(paramChar);
        String str2 = localAccount.getBankID();
        if (str2 != null) {
          localStringBuffer.append(str2);
        }
        localStringBuffer.append(paramChar);
        String str3 = localAccount.getRoutingNum();
        if (str3 != null) {
          localStringBuffer.append(str3);
        }
        localStringBuffer.append(_lineSeparator);
        Transactions localTransactions = localAccount.getTransactions();
        if (localTransactions != null)
        {
          localStringBuffer.append(ReportConsts.getColumnName(228, this.locale));
          localStringBuffer.append(paramChar);
          localStringBuffer.append(ReportConsts.getColumnName(229, this.locale));
          localStringBuffer.append(paramChar);
          localStringBuffer.append(ReportConsts.getColumnName(230, this.locale));
          localStringBuffer.append(paramChar);
          localStringBuffer.append(ReportConsts.getColumnName(231, this.locale));
          localStringBuffer.append(paramChar);
          localStringBuffer.append(ReportConsts.getColumnName(232, this.locale));
          localStringBuffer.append(paramChar);
          localStringBuffer.append(ReportConsts.getColumnName(234, this.locale));
          localStringBuffer.append(paramChar);
          localStringBuffer.append(ReportConsts.getColumnName(235, this.locale));
          localStringBuffer.append(_lineSeparator);
          Iterator localIterator2 = localTransactions.iterator();
          while (localIterator2.hasNext())
          {
            Transaction localTransaction = (Transaction)localIterator2.next();
            DateTime localDateTime = localTransaction.getDateValue();
            if (localDateTime != null) {
              localStringBuffer.append(localDateTime.toString());
            }
            localStringBuffer.append(paramChar);
            String str4 = localTransaction.getType();
            if (str4 != null) {
              localStringBuffer.append(str4);
            }
            localStringBuffer.append(paramChar);
            String str5 = localTransaction.getPayeePayor();
            if (str5 != null) {
              localStringBuffer.append(str5);
            }
            localStringBuffer.append(paramChar);
            String str6 = localTransaction.getDescription();
            if (str6 != null) {
              localStringBuffer.append(str6);
            }
            localStringBuffer.append(paramChar);
            String str7 = localTransaction.getReferenceNumber();
            if (str7 != null) {
              localStringBuffer.append(str7);
            }
            localStringBuffer.append(paramChar);
            Object localObject1 = null;
            Object localObject2 = null;
            Currency localCurrency1 = localTransaction.getAmountValue();
            BigDecimal localBigDecimal = localCurrency1.getAmountValue();
            Currency localCurrency2 = new Currency(localCurrency1.getAmountValue(), localCurrency1.getLocale());
            if (localCurrency1.isNegative())
            {
              localCurrency2.setAmount(localBigDecimal.negate());
              localObject1 = localCurrency2;
              localObject2 = null;
            }
            else
            {
              localCurrency2.setAmount(localBigDecimal);
              localObject1 = null;
              localObject2 = localCurrency2;
            }
            if (localObject1 != null) {
              jdMethod_try(localStringBuffer, localObject1);
            }
            localStringBuffer.append(paramChar);
            if (localObject2 != null) {
              jdMethod_try(localStringBuffer, localObject2);
            }
            localStringBuffer.append(_lineSeparator);
          }
        }
      }
    }
    return localStringBuffer;
  }
  
  private void jdMethod_try(StringBuffer paramStringBuffer, Currency paramCurrency)
  {
    if (paramCurrency != null) {
      paramStringBuffer.append(paramCurrency.removeCharFromString(paramCurrency.toString(), ','));
    }
  }
  
  public void set(AcctGeneralLedgerRpt paramAcctGeneralLedgerRpt)
  {
    super.set(paramAcctGeneralLedgerRpt);
    setAccounts(paramAcctGeneralLedgerRpt.getAccounts());
    setLocale(paramAcctGeneralLedgerRpt.locale);
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ACCT_GENERAL_LEDGER_RPT");
    if (this.Sw != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "TRANSACTIONS");
      localStringBuffer.append(this.Sw.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "TRANSACTIONS");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ACCT_GENERAL_LEDGER_RPT");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("TRANSACTIONS"))
      {
        if (AcctGeneralLedgerRpt.this.Sw == null) {
          AcctGeneralLedgerRpt.this.Sw = new Accounts(AcctGeneralLedgerRpt.this.locale);
        }
        AcctGeneralLedgerRpt.this.Sw.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.AcctGeneralLedgerRpt
 * JD-Core Version:    0.7.0.1
 */