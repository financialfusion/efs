package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.csil.core.Banking;
import com.ffusion.services.Banking3;
import com.ffusion.tasks.util.GetPagedData;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.PagingContext;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPagedTransactions
  extends GetPagedData
  implements Task
{
  protected String bankingServiceName = "com.ffusion.services.Banking";
  public static final String GETPAGEDTRANSACTIONS = "GetPagedTransactions";
  String em = "Account";
  String ek;
  String el = null;
  
  public GetPagedTransactions()
  {
    setDataSetName("Transactions");
    this.ek = "P";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = validateAmounts(this._criteria);
    if (this.error != 0) {
      return super.getTaskErrorURL();
    }
    d();
    this._criteria.getSearchCriteria().put("DataClassification", this.ek);
    return super.process(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  private void d()
  {
    if (this.el == null) {
      return;
    }
    if ((getStartDateValue() != null) && (getEndDateValue() != null)) {
      return;
    }
    try
    {
      int i = Integer.parseInt(this.el);
      Object localObject1 = getStartDateValue();
      Object localObject2 = getEndDateValue();
      if (localObject1 == null)
      {
        if (localObject2 == null)
        {
          localObject3 = Calendar.getInstance();
          ((Calendar)localObject3).setTime(new Date());
          ((Calendar)localObject3).add(5, 0 - i);
          localObject4 = Calendar.getInstance();
          localObject1 = new DateTime();
          localObject2 = new DateTime();
          ((DateTime)localObject1).setTime(((Calendar)localObject3).getTime());
          ((DateTime)localObject2).setTime(((Calendar)localObject4).getTime());
        }
        else
        {
          localObject3 = Calendar.getInstance();
          ((Calendar)localObject3).setTime(((DateTime)localObject2).getTime());
          ((Calendar)localObject3).add(5, 0 - i);
          localObject1 = new DateTime();
          ((DateTime)localObject1).setTime(((Calendar)localObject3).getTime());
        }
      }
      else if (localObject2 == null)
      {
        localObject3 = Calendar.getInstance();
        ((Calendar)localObject3).setTime(((DateTime)localObject1).getTime());
        localObject2 = new DateTime();
        ((DateTime)localObject2).setTime(((Calendar)localObject3).getTime());
      }
      ((DateTime)localObject1).set(11, 0);
      ((DateTime)localObject1).set(12, 0);
      ((DateTime)localObject1).set(13, 0);
      ((DateTime)localObject1).set(14, 0);
      ((DateTime)localObject2).set(11, 23);
      ((DateTime)localObject2).set(12, 59);
      ((DateTime)localObject2).set(13, 59);
      ((DateTime)localObject2).set(14, 999);
      Object localObject3 = DateFormatUtil.getFormatter(getDateFormat());
      Object localObject4 = ((DateFormat)localObject3).format(((DateTime)localObject1).getTime());
      String str = ((DateFormat)localObject3).format(((DateTime)localObject2).getTime());
      setStartDate((String)localObject4);
      setEndDate(str);
    }
    catch (Exception localException) {}
  }
  
  public void setbankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
  
  public void setAccountName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.em = "Account";
    } else {
      this.em = paramString;
    }
  }
  
  public String getAccountName()
  {
    return this.em;
  }
  
  public void setTransactionsName(String paramString)
  {
    setDataSetName(paramString);
  }
  
  public String getTransactionsName()
  {
    return getDataSetName();
  }
  
  public String getDataClassification()
  {
    return this.ek;
  }
  
  public void setDataClassification(String paramString)
  {
    this.ek = paramString;
  }
  
  public void setDefaultDateRange(String paramString)
  {
    this.el = paramString;
  }
  
  public String getDefaultDateRange(String paramString)
  {
    return this.el;
  }
  
  protected FilteredList getFirstPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Account localAccount = (Account)localHttpSession.getAttribute(this.em);
    Banking3 localBanking3 = (Banking3)localHttpSession.getAttribute(this.bankingServiceName);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localAccount == null)
    {
      this.error = 1002;
      return null;
    }
    if (localSecureUser == null)
    {
      this.error = 1037;
      return null;
    }
    paramHashMap.put("SERVICE", localBanking3);
    paramHashMap.put("DATA_CLASSIFICATION", this.ek);
    Transactions localTransactions = Banking.getPagedTransactions(localSecureUser, localAccount, paramPagingContext, paramHashMap);
    localTransactions.setDateFormat(getDateFormat());
    return localTransactions;
  }
  
  protected FilteredList getNextPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Account localAccount = (Account)localHttpSession.getAttribute(this.em);
    Banking3 localBanking3 = (Banking3)localHttpSession.getAttribute(this.bankingServiceName);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localAccount == null)
    {
      this.error = 1002;
      return null;
    }
    if (localSecureUser == null)
    {
      this.error = 1037;
      return null;
    }
    paramHashMap.put("SERVICE", localBanking3);
    paramHashMap.put("DATA_CLASSIFICATION", this.ek);
    Transactions localTransactions = Banking.getNextTransactions(localSecureUser, localAccount, paramPagingContext, paramHashMap);
    localTransactions.setDateFormat(getDateFormat());
    return localTransactions;
  }
  
  protected FilteredList getPreviousPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Account localAccount = (Account)localHttpSession.getAttribute(this.em);
    Banking3 localBanking3 = (Banking3)localHttpSession.getAttribute(this.bankingServiceName);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localAccount == null)
    {
      this.error = 1002;
      return null;
    }
    if (localSecureUser == null)
    {
      this.error = 1037;
      return null;
    }
    paramHashMap.put("SERVICE", localBanking3);
    paramHashMap.put("DATA_CLASSIFICATION", this.ek);
    Transactions localTransactions = Banking.getPreviousTransactions(localSecureUser, localAccount, paramPagingContext, paramHashMap);
    localTransactions.setDateFormat(getDateFormat());
    return localTransactions;
  }
  
  protected FilteredList getLastPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    this.error = 20112;
    return null;
  }
  
  protected Object getValueForSortCriterion(ReportSortCriterion paramReportSortCriterion, Object paramObject)
  {
    Transaction localTransaction = (Transaction)paramObject;
    if (paramReportSortCriterion.getName().equals("ProcessingDate")) {
      return localTransaction.getDateValue();
    }
    if (paramReportSortCriterion.getName().equals("Amount")) {
      return localTransaction.getAmountValue();
    }
    if (paramReportSortCriterion.getName().equals("TransactionIndex")) {
      return new Long(localTransaction.getTransactionIndex());
    }
    if (paramReportSortCriterion.getName().equals("TransactionType")) {
      return localTransaction.getType();
    }
    if (paramReportSortCriterion.getName().equals("TransactionReferenceNumber")) {
      return localTransaction.getReferenceNumber();
    }
    if (paramReportSortCriterion.getName().equals("BankReferenceNumber")) {
      return localTransaction.getBankReferenceNumber();
    }
    if (paramReportSortCriterion.getName().equals("CustomerReferenceNumber")) {
      return localTransaction.getCustomerReferenceNumber();
    }
    if (paramReportSortCriterion.getName().equals("RunningBalance")) {
      return localTransaction.getRunningBalanceValue();
    }
    if (paramReportSortCriterion.getName().equals("Description")) {
      return localTransaction.getDescription();
    }
    if (paramReportSortCriterion.getName().equals("Memo")) {
      return localTransaction.getMemo();
    }
    if (paramReportSortCriterion.getName().equals("DueDate")) {
      return localTransaction.getDueDate();
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetPagedTransactions
 * JD-Core Version:    0.7.0.1
 */