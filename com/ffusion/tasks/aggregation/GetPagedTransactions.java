package com.ffusion.tasks.aggregation;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.aggregation.Accounts;
import com.ffusion.beans.aggregation.Transaction;
import com.ffusion.beans.aggregation.Transactions;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.csil.core.AccountAggregation;
import com.ffusion.services.AccountAggregation2;
import com.ffusion.tasks.util.GetPagedData;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.PagingContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPagedTransactions
  extends GetPagedData
{
  private String Ei = "Account";
  private String Eh = "AggregationService";
  String Eg = "P";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = super.validateAmounts(this._criteria);
    if (this.error != 0) {
      return super.getTaskErrorURL();
    }
    return super.process(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public GetPagedTransactions()
  {
    super.setDataSetName("Transactions");
  }
  
  public String getAccountName()
  {
    return this.Ei;
  }
  
  public void setAccountName(String paramString)
  {
    this.Ei = paramString;
  }
  
  public String getServiceName()
  {
    return this.Eh;
  }
  
  public void setServiceName(String paramString)
  {
    this.Eh = paramString;
  }
  
  public String getDataClassification()
  {
    return this.Eg;
  }
  
  public void setDataClassification(String paramString)
  {
    this.Eg = paramString;
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
    if (paramReportSortCriterion.getName().equals("TransactionType")) {
      return localTransaction.getType();
    }
    if (paramReportSortCriterion.getName().equals("TransactionReferenceNumber")) {
      return localTransaction.getReferenceNumber();
    }
    if (paramReportSortCriterion.getName().equals("Description")) {
      return localTransaction.getDescription();
    }
    if (paramReportSortCriterion.getName().equals("Memo")) {
      return localTransaction.getMemo();
    }
    return null;
  }
  
  protected FilteredList getFirstPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    AccountAggregation2 localAccountAggregation2 = (AccountAggregation2)localHttpSession.getAttribute(this.Eh);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    com.ffusion.beans.accounts.Account localAccount = (com.ffusion.beans.accounts.Account)localHttpSession.getAttribute(this.Ei);
    if (localAccount == null)
    {
      this.error = 1002;
      return null;
    }
    com.ffusion.beans.aggregation.Account localAccount1 = jdMethod_for(localAccount, localSecureUser.getLocale());
    if (localSecureUser == null)
    {
      this.error = 1037;
      return null;
    }
    paramHashMap.put("SERVICE", localAccountAggregation2);
    paramHashMap.put("DATA_CLASSIFICATION", this.Eg);
    Transactions localTransactions = AccountAggregation.getPagedTransactions(localSecureUser, localAccount1, paramPagingContext, paramHashMap);
    localTransactions.setDateFormat(getDateFormat());
    return localTransactions;
  }
  
  protected FilteredList getNextPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    AccountAggregation2 localAccountAggregation2 = (AccountAggregation2)localHttpSession.getAttribute(this.Eh);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    com.ffusion.beans.accounts.Account localAccount = (com.ffusion.beans.accounts.Account)localHttpSession.getAttribute(this.Ei);
    if (localAccount == null)
    {
      this.error = 1002;
      return null;
    }
    com.ffusion.beans.aggregation.Account localAccount1 = jdMethod_for(localAccount, localSecureUser.getLocale());
    if (localSecureUser == null)
    {
      this.error = 1037;
      return null;
    }
    paramHashMap.put("SERVICE", localAccountAggregation2);
    paramHashMap.put("DATA_CLASSIFICATION", this.Eg);
    Transactions localTransactions = AccountAggregation.getNextTransactions(localSecureUser, localAccount1, paramPagingContext, paramHashMap);
    localTransactions.setDateFormat(getDateFormat());
    return localTransactions;
  }
  
  protected FilteredList getLastPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    this.error = 20112;
    return null;
  }
  
  protected FilteredList getPreviousPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    AccountAggregation2 localAccountAggregation2 = (AccountAggregation2)localHttpSession.getAttribute(this.Eh);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    com.ffusion.beans.accounts.Account localAccount = (com.ffusion.beans.accounts.Account)localHttpSession.getAttribute(this.Ei);
    if (localAccount == null)
    {
      this.error = 1002;
      return null;
    }
    com.ffusion.beans.aggregation.Account localAccount1 = jdMethod_for(localAccount, localSecureUser.getLocale());
    if (localSecureUser == null)
    {
      this.error = 1037;
      return null;
    }
    paramHashMap.put("SERVICE", localAccountAggregation2);
    paramHashMap.put("DATA_CLASSIFICATION", this.Eg);
    Transactions localTransactions = AccountAggregation.getPreviousTransactions(localSecureUser, localAccount1, paramPagingContext, paramHashMap);
    localTransactions.setDateFormat(getDateFormat());
    return localTransactions;
  }
  
  private com.ffusion.beans.aggregation.Account jdMethod_for(com.ffusion.beans.accounts.Account paramAccount, Locale paramLocale)
  {
    Accounts localAccounts = new Accounts(paramLocale);
    com.ffusion.beans.aggregation.Account localAccount = localAccounts.create(paramAccount.getNumber());
    localAccount.setLocale(paramAccount.getLocale());
    localAccount.setInstitutionID(paramAccount.getRoutingNum());
    localAccount.setNickName(paramAccount.getNickName());
    localAccount.setNumber(paramAccount.getNumber());
    localAccount.setInstitutionName(paramAccount.getBankName());
    localAccount.setType(String.valueOf(com.ffusion.beans.aggregation.Account.getAggregationTypeFromAccountType(paramAccount.getTypeValue())));
    localAccount.setStatus(paramAccount.getStatus());
    localAccount.setID(paramAccount.getID());
    return localAccount;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.aggregation.GetPagedTransactions
 * JD-Core Version:    0.7.0.1
 */