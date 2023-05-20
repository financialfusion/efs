package com.ffusion.beans.istatements;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.XMLStrings;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.checkimaging.ImageResults;
import com.ffusion.beans.messages.Messages;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Sortable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import com.ffusion.util.settings.AccountSettings;
import com.ffusion.util.settings.SystemException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;

public class Statement
  extends ExtendABean
  implements Sortable, Comparable, XMLStrings
{
  public static final String RESOURCE_BUNDLE = "com.ffusion.util.months";
  protected String id;
  protected String accountNumber;
  protected String accountName;
  protected String accountCurrency;
  protected DateTime statementStartDate;
  protected DateTime statementEndDate;
  protected DateTime statementDate;
  protected Transactions transactions;
  protected Messages messages;
  protected InterestBalanceSummary ibs;
  protected ReserveSummary resSum;
  protected DailyBalanceSummaries dbss;
  protected BalanceSummary bs;
  protected InterestSummary is;
  protected MonthlyAccountSummary mas;
  protected ImageResults imageResults;
  
  public Statement(String paramString)
  {
    this.accountNumber = paramString;
    this.messages = new Messages();
  }
  
  public Statement(String paramString1, String paramString2)
  {
    this.accountNumber = paramString1;
    this.id = paramString2;
    this.messages = new Messages();
  }
  
  public Statement(Locale paramLocale)
  {
    super(paramLocale);
    this.messages = new Messages(paramLocale);
    if (this.messages != null) {
      this.messages.setLocale(paramLocale);
    }
    if (this.ibs != null) {
      this.ibs.setLocale(paramLocale);
    }
    if (this.resSum != null) {
      this.resSum.setLocale(paramLocale);
    }
    if (this.dbss != null) {
      this.dbss.setLocale(paramLocale);
    }
    if (this.bs != null) {
      this.bs.setLocale(paramLocale);
    }
    if (this.is != null) {
      this.is.setLocale(paramLocale);
    }
    if (this.mas != null) {
      this.mas.setLocale(paramLocale);
    }
  }
  
  public void setID(String paramString)
  {
    this.id = paramString;
  }
  
  public String getID()
  {
    return this.id;
  }
  
  public void setAccountNumber(String paramString)
  {
    this.accountNumber = paramString;
  }
  
  public String getAccountNumber()
  {
    return this.accountNumber;
  }
  
  public String getAccountNumberMasked()
  {
    try
    {
      return AccountSettings.getMaskedAccountNumber(getAccountNumber(), 4, 'x');
    }
    catch (SystemException localSystemException) {}
    return "";
  }
  
  public void setAccountName(String paramString)
  {
    this.accountName = paramString;
  }
  
  public String getAccountName()
  {
    return this.accountName;
  }
  
  public void setAccountCurrency(String paramString)
  {
    this.accountCurrency = paramString;
  }
  
  public String getAccountCurrency()
  {
    return this.accountCurrency;
  }
  
  public void setTransactions(Transactions paramTransactions)
  {
    this.transactions = paramTransactions;
  }
  
  public Transactions getTransactions()
  {
    return this.transactions;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.statementStartDate != null) {
      this.statementStartDate.setLocale(paramLocale);
    }
    if (this.statementEndDate != null) {
      this.statementEndDate.setLocale(paramLocale);
    }
    if (this.statementDate != null) {
      this.statementDate.setLocale(paramLocale);
    }
    if (this.messages != null) {
      this.messages.setLocale(paramLocale);
    }
    if (this.ibs != null) {
      this.ibs.setLocale(paramLocale);
    }
    if (this.resSum != null) {
      this.resSum.setLocale(paramLocale);
    }
    if (this.dbss != null) {
      this.dbss.setLocale(paramLocale);
    }
    if (this.bs != null) {
      this.bs.setLocale(paramLocale);
    }
    if (this.is != null) {
      this.is.setLocale(paramLocale);
    }
    if (this.mas != null) {
      this.mas.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.statementStartDate != null) {
      this.statementStartDate.setFormat(paramString);
    }
    if (this.statementEndDate != null) {
      this.statementEndDate.setFormat(paramString);
    }
    if (this.statementDate != null) {
      this.statementDate.setFormat(paramString);
    }
  }
  
  public String getStatementStartDate()
  {
    if (this.statementStartDate != null) {
      return this.statementStartDate.toString();
    }
    return "";
  }
  
  public String getStatementStartMonth()
  {
    if (this.statementStartDate != null) {
      return String.valueOf(this.statementStartDate.get(2) + 1);
    }
    return String.valueOf(Calendar.getInstance().get(2));
  }
  
  public String getStatementStartMonthDesc()
  {
    if (this.statementStartDate != null) {
      return getMonthString(String.valueOf(this.statementStartDate.get(2) + 1));
    }
    return getMonthString(String.valueOf(Calendar.getInstance().get(2)));
  }
  
  public String getMonthString(String paramString)
  {
    return ResourceUtil.getString("Month" + paramString, "com.ffusion.util.months", this.locale);
  }
  
  public String getStatementStartYear()
  {
    if (this.statementStartDate != null) {
      return String.valueOf(this.statementStartDate.get(1));
    }
    return String.valueOf(Calendar.getInstance().get(1));
  }
  
  public String getStatementEndMonthDesc()
  {
    if (this.statementEndDate != null) {
      return getMonthString(String.valueOf(this.statementEndDate.get(2) + 1));
    }
    return getMonthString(String.valueOf(Calendar.getInstance().get(2)));
  }
  
  public String getStatementEndMonth()
  {
    if (this.statementEndDate != null) {
      return String.valueOf(this.statementEndDate.get(2) + 1);
    }
    return String.valueOf(Calendar.getInstance().get(2));
  }
  
  public String getStatementEndYear()
  {
    if (this.statementEndDate != null) {
      return String.valueOf(this.statementEndDate.get(1));
    }
    return String.valueOf(Calendar.getInstance().get(1));
  }
  
  public DateTime getStatementStartDateValue()
  {
    return this.statementStartDate;
  }
  
  public void setStatementStartDate(String paramString)
  {
    try
    {
      if (this.statementStartDate == null) {
        this.statementStartDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.statementStartDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      System.out.println("Exception: " + localInvalidDateTimeException);
      this.statementStartDate = null;
    }
  }
  
  public void setStatementStartDate(DateTime paramDateTime)
  {
    this.statementStartDate = paramDateTime;
    this.statementStartDate.setLocale(this.locale);
    this.statementStartDate.setFormat(getDateFormat());
  }
  
  public String getStatementEndDate()
  {
    if (this.statementEndDate != null) {
      return this.statementEndDate.toString();
    }
    return "";
  }
  
  public DateTime getStatementEndDateValue()
  {
    return this.statementEndDate;
  }
  
  public void setStatementEndDate(String paramString)
  {
    try
    {
      if (this.statementEndDate == null) {
        this.statementEndDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.statementEndDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      System.out.println("Exception: " + localInvalidDateTimeException);
      this.statementEndDate = null;
    }
  }
  
  public void setStatementEndDate(DateTime paramDateTime)
  {
    this.statementEndDate = paramDateTime;
    this.statementEndDate.setLocale(this.locale);
    this.statementEndDate.setFormat(getDateFormat());
  }
  
  public String getStatementDate()
  {
    if (this.statementDate != null) {
      return this.statementDate.toString();
    }
    return "";
  }
  
  public DateTime getStatementDateValue()
  {
    return this.statementDate;
  }
  
  public void setStatementDate(String paramString)
  {
    try
    {
      if (this.statementDate == null) {
        this.statementDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.statementDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      System.out.println("Exception: " + localInvalidDateTimeException);
      this.statementDate = null;
    }
  }
  
  public void setStatementDate(DateTime paramDateTime)
  {
    this.statementDate = paramDateTime;
    this.statementDate.setLocale(this.locale);
    this.statementDate.setFormat(getDateFormat());
  }
  
  public void setImageResults(ImageResults paramImageResults)
  {
    this.imageResults = ((ImageResults)paramImageResults.clone());
  }
  
  public ImageResults getImageResults()
  {
    return this.imageResults;
  }
  
  public Messages getMessages()
  {
    return this.messages;
  }
  
  public void setMessages(Messages paramMessages)
  {
    this.messages = paramMessages;
  }
  
  public InterestBalanceSummary getInterestBalanceSummary()
  {
    return this.ibs;
  }
  
  public void setInterestBalanceSummary(InterestBalanceSummary paramInterestBalanceSummary)
  {
    this.ibs = paramInterestBalanceSummary;
  }
  
  public ReserveSummary getReserveSummary()
  {
    return this.resSum;
  }
  
  public void setReserveSummary(ReserveSummary paramReserveSummary)
  {
    this.resSum = paramReserveSummary;
  }
  
  public DailyBalanceSummaries getDailyBalanceSummaries()
  {
    return this.dbss;
  }
  
  public void setDailyBalanceSummaries(DailyBalanceSummaries paramDailyBalanceSummaries)
  {
    this.dbss = paramDailyBalanceSummaries;
  }
  
  public BalanceSummary getBalanceSummary()
  {
    return this.bs;
  }
  
  public void setBalanceSummary(BalanceSummary paramBalanceSummary)
  {
    this.bs = paramBalanceSummary;
  }
  
  public InterestSummary getInterestSummary()
  {
    return this.is;
  }
  
  public void setInterestSummary(InterestSummary paramInterestSummary)
  {
    this.is = paramInterestSummary;
  }
  
  public MonthlyAccountSummary getMonthlyAccountSummary()
  {
    return this.mas;
  }
  
  public void setMonthlyAccountSummary(MonthlyAccountSummary paramMonthlyAccountSummary)
  {
    this.mas = paramMonthlyAccountSummary;
  }
  
  public void set(Statement paramStatement)
  {
    super.set(paramStatement);
    setID(paramStatement.getID());
    setAccountNumber(paramStatement.getAccountNumber());
    setAccountName(paramStatement.getAccountName());
    setAccountCurrency(paramStatement.getAccountCurrency());
    if (paramStatement.getTransactions() != null) {
      setTransactions((Transactions)paramStatement.getTransactions().clone());
    }
    if (paramStatement.getStatementStartDateValue() != null) {
      setStatementStartDate((DateTime)paramStatement.getStatementStartDateValue().clone());
    }
    if (paramStatement.getStatementEndDateValue() != null) {
      setStatementEndDate((DateTime)paramStatement.getStatementEndDateValue().clone());
    }
    if (paramStatement.getStatementDateValue() != null) {
      setStatementDate((DateTime)paramStatement.getStatementDateValue().clone());
    }
    if (paramStatement.getMessages() != null) {
      setMessages((Messages)paramStatement.getMessages().clone());
    }
    if (paramStatement.getInterestBalanceSummary() != null) {
      setInterestBalanceSummary((InterestBalanceSummary)paramStatement.getInterestBalanceSummary().clone());
    }
    if (paramStatement.getReserveSummary() != null) {
      setReserveSummary((ReserveSummary)paramStatement.getReserveSummary().clone());
    }
    if (paramStatement.getDailyBalanceSummaries() != null) {
      setDailyBalanceSummaries((DailyBalanceSummaries)paramStatement.getDailyBalanceSummaries().clone());
    }
    if (paramStatement.getBalanceSummary() != null) {
      setBalanceSummary((BalanceSummary)paramStatement.getBalanceSummary().clone());
    }
    if (paramStatement.getInterestSummary() != null) {
      setInterestSummary((InterestSummary)paramStatement.getInterestSummary().clone());
    }
    if (paramStatement.getMonthlyAccountSummary() != null) {
      setMonthlyAccountSummary((MonthlyAccountSummary)paramStatement.getMonthlyAccountSummary().clone());
    }
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "ACCOUNT_NUMBER");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Statement localStatement = (Statement)paramObject;
    int i = 1;
    if ((paramString.equals("ACCOUNT_NUMBER")) && (getAccountNumber() != null) && (localStatement.getAccountNumber() != null)) {
      i = getAccountNumber().compareTo(localStatement.getAccountNumber());
    } else if ((paramString.equals("STMT_ID")) && (getID() != null) && (localStatement.getID() != null)) {
      i = getID().compareTo(localStatement.getID());
    } else if ((paramString.equals("FROM_DATE")) && (getStatementStartDateValue() != null) && (localStatement.getStatementStartDateValue() != null)) {
      i = getStatementStartDateValue().equals(localStatement.getStatementStartDateValue()) ? 0 : getStatementStartDateValue().before(localStatement.getStatementStartDateValue()) ? -1 : 1;
    } else if ((paramString.equals("TO_DATE")) && (getStatementEndDateValue() != null) && (localStatement.getStatementEndDateValue() != null)) {
      i = getStatementEndDateValue().equals(localStatement.getStatementEndDateValue()) ? 0 : getStatementEndDateValue().before(localStatement.getStatementEndDateValue()) ? -1 : 1;
    } else if ((paramString.equals("STMT_DATE")) && (getStatementDateValue() != null) && (localStatement.getStatementDateValue() != null)) {
      i = getStatementDateValue().equals(localStatement.getStatementDateValue()) ? 0 : getStatementDateValue().before(localStatement.getStatementDateValue()) ? -1 : 1;
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=<>!", true);
    if ((localStringTokenizer.countTokens() == 3) || (localStringTokenizer.countTokens() == 4))
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      String str3 = localStringTokenizer.nextToken();
      if (localStringTokenizer.countTokens() == 1)
      {
        str2 = str2 + str3;
        str3 = localStringTokenizer.nextToken();
      }
      if ((str1.equals("ACCOUNT_NUMBER")) && (getAccountNumber() != null)) {
        return isFilterable(getAccountNumber(), str2, str3);
      }
      if ((str1.equals("STMT_ID")) && (getID() != null)) {
        return isFilterable(getID(), str2, str3);
      }
      if ((str1.equals("FROM_DATE")) && (this.statementStartDate != null)) {
        return this.statementStartDate.isFilterable("VALUE" + str2 + str3);
      }
      if ((str1.equals("TO_DATE")) && (this.statementEndDate != null)) {
        return this.statementEndDate.isFilterable("VALUE" + str2 + str3);
      }
      if ((str1.equals("STMT_DATE")) && (this.statementDate != null)) {
        return this.statementDate.isFilterable("VALUE" + str2 + str3);
      }
      return super.isFilterable(paramString);
    }
    return false;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "STATEMENT");
    XMLHandler.appendTag(localStringBuffer, "STMT_ID", this.id);
    XMLHandler.appendTag(localStringBuffer, "ACCOUNT_NUMBER", this.accountNumber);
    XMLHandler.appendTag(localStringBuffer, "ACCOUNT_NAME", this.accountName);
    XMLHandler.appendTag(localStringBuffer, "CURRENCY_CODE", this.accountCurrency);
    XMLHandler.appendTag(localStringBuffer, "FROM_DATE", getStatementStartDate());
    XMLHandler.appendTag(localStringBuffer, "TO_DATE", getStatementEndDate());
    XMLHandler.appendTag(localStringBuffer, "STMT_DATE", getStatementDate());
    if (this.transactions != null) {
      localStringBuffer.append(this.transactions.toXML());
    }
    if (this.messages != null) {
      localStringBuffer.append(this.messages.getXML());
    }
    if (this.ibs != null) {
      localStringBuffer.append(this.ibs.getXML());
    }
    if (this.resSum != null) {
      localStringBuffer.append(this.resSum.getXML());
    }
    if (this.dbss != null) {
      localStringBuffer.append(this.dbss.getXML());
    }
    if (this.bs != null) {
      localStringBuffer.append(this.bs.getXML());
    }
    if (this.is != null) {
      localStringBuffer.append(this.is.getXML());
    }
    if (this.mas != null) {
      localStringBuffer.append(this.mas.getXML());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "STATEMENT");
    return localStringBuffer.toString();
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("STMT_ID")) {
      this.id = paramString2;
    } else if (paramString1.equals("STMT_DATE")) {
      setStatementDate(paramString2);
    } else if (paramString1.equals("ACCOUNT_NUMBER")) {
      this.accountNumber = paramString2;
    } else if (paramString1.equals("ACCOUNT_NAME")) {
      this.accountName = paramString2;
    } else if (paramString1.equals("CURRENCY_CODE")) {
      this.accountCurrency = paramString2;
    } else if (paramString1.equals("FROM_DATE")) {
      setStatementStartDate(paramString2);
    } else if (paramString1.equals("TO_DATE")) {
      setStatementEndDate(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
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
  
  public void setNameAddress1(String paramString)
  {
    put("NAME_ADDR1", paramString);
  }
  
  public void setNameAddress2(String paramString)
  {
    put("NAME_ADDR2", paramString);
  }
  
  public void setNameAddress3(String paramString)
  {
    put("NAME_ADDR3", paramString);
  }
  
  public void setNameAddress4(String paramString)
  {
    put("NAME_ADDR4", paramString);
  }
  
  public void setNameAddress5(String paramString)
  {
    put("NAME_ADDR5", paramString);
  }
  
  public void setNameAddress6(String paramString)
  {
    put("NAME_ADDR6", paramString);
  }
  
  public String getNameAddress1()
  {
    return (String)get("NAME_ADDR1");
  }
  
  public String getNameAddress2()
  {
    return (String)get("NAME_ADDR2");
  }
  
  public String getNameAddress3()
  {
    return (String)get("NAME_ADDR3");
  }
  
  public String getNameAddress4()
  {
    return (String)get("NAME_ADDR4");
  }
  
  public String getNameAddress5()
  {
    return (String)get("NAME_ADDR5");
  }
  
  public String getNameAddress6()
  {
    return (String)get("NAME_ADDR6");
  }
  
  public String getProdCode()
  {
    return (String)get("PROD_CODE");
  }
  
  public String getBankID()
  {
    return (String)get("BANK_ID");
  }
  
  class a
    extends ExtendABean.InternalXMLHandler
  {
    a()
    {
      super();
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("TRANSACTIONS"))
      {
        if (Statement.this.transactions == null) {
          Statement.this.transactions = new Transactions(Statement.this.locale);
        }
        Statement.this.transactions.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("DAILY_BALANCE_SUMMARIES"))
      {
        if (Statement.this.dbss == null)
        {
          Statement.this.dbss = new DailyBalanceSummaries(Statement.this.locale);
          Statement.this.dbss.setDateFormat(Statement.this.datetype);
        }
        Statement.this.dbss.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("BALANCE_SUMMARY"))
      {
        if (Statement.this.bs == null)
        {
          Statement.this.bs = new BalanceSummary(Statement.this.locale);
          Statement.this.bs.setDateFormat(Statement.this.datetype);
        }
        Statement.this.bs.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("INTEREST_BALANCE_SUMMARY"))
      {
        if (Statement.this.ibs == null)
        {
          Statement.this.ibs = new InterestBalanceSummary(Statement.this.locale);
          Statement.this.ibs.setDateFormat(Statement.this.datetype);
        }
        Statement.this.ibs.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("RESERVE_SUMMARY"))
      {
        if (Statement.this.resSum == null)
        {
          Statement.this.resSum = new ReserveSummary(Statement.this.locale);
          Statement.this.resSum.setDateFormat(Statement.this.datetype);
        }
        Statement.this.resSum.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("INTEREST_SUMMARY"))
      {
        if (Statement.this.is == null)
        {
          Statement.this.is = new InterestSummary(Statement.this.locale);
          Statement.this.is.setDateFormat(Statement.this.datetype);
        }
        Statement.this.is.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("MONTHLY_ACCOUNT_SUMMARY"))
      {
        if (Statement.this.mas == null)
        {
          Statement.this.mas = new MonthlyAccountSummary(Statement.this.locale);
          Statement.this.mas.setDateFormat(Statement.this.datetype);
        }
        Statement.this.mas.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.istatements.Statement
 * JD-Core Version:    0.7.0.1
 */