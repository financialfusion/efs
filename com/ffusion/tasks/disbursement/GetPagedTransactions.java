package com.ffusion.tasks.disbursement;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.disbursement.DisbursementAccount;
import com.ffusion.beans.disbursement.DisbursementSummaries;
import com.ffusion.beans.disbursement.DisbursementSummary;
import com.ffusion.beans.disbursement.DisbursementTransaction;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.csil.core.Disbursements;
import com.ffusion.tasks.util.GetPagedData;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetPagedTransactions
  extends GetPagedData
  implements Task
{
  static final String Ed = "Account";
  String Ef = "Account";
  String Ec;
  String Ee;
  
  public GetPagedTransactions()
  {
    setDataSetName("DisbursementItems");
    this.Ec = "P";
  }
  
  public void setAccountID(String paramString)
  {
    this.Ee = paramString;
  }
  
  public String getAccountID()
  {
    return this.Ee;
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
    return this.Ec;
  }
  
  public void setDataClassification(String paramString)
  {
    this.Ec = paramString;
  }
  
  protected FilteredList getFirstPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.Ee == null)
    {
      this.error = 21000;
      return null;
    }
    DisbursementSummaries localDisbursementSummaries = (DisbursementSummaries)localHttpSession.getAttribute("DisbursementSummaries");
    if (localDisbursementSummaries == null)
    {
      this.error = 21001;
      return null;
    }
    DisbursementSummary localDisbursementSummary = localDisbursementSummaries.getByAccountID(this.Ee);
    DisbursementAccount localDisbursementAccount = localDisbursementSummary.getAccount();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null) {
      return null;
    }
    paramHashMap.put("DATA_CLASSIFICATION", this.Ec);
    return Disbursements.getPagedTransactions(localSecureUser, localDisbursementAccount, paramPagingContext, paramHashMap);
  }
  
  protected FilteredList getNextPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.Ee == null)
    {
      this.error = 21000;
      return null;
    }
    DisbursementSummaries localDisbursementSummaries = (DisbursementSummaries)localHttpSession.getAttribute("DisbursementSummaries");
    if (localDisbursementSummaries == null)
    {
      this.error = 21001;
      return null;
    }
    DisbursementSummary localDisbursementSummary = localDisbursementSummaries.getByAccountID(this.Ee);
    DisbursementAccount localDisbursementAccount = localDisbursementSummary.getAccount();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null) {
      return null;
    }
    paramHashMap.put("DATA_CLASSIFICATION", this.Ec);
    return Disbursements.getNextTransactions(localSecureUser, localDisbursementAccount, paramPagingContext, paramHashMap);
  }
  
  protected FilteredList getPreviousPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.Ee == null)
    {
      this.error = 21000;
      return null;
    }
    DisbursementSummaries localDisbursementSummaries = (DisbursementSummaries)localHttpSession.getAttribute("DisbursementSummaries");
    if (localDisbursementSummaries == null)
    {
      this.error = 21001;
      return null;
    }
    DisbursementSummary localDisbursementSummary = localDisbursementSummaries.getByAccountID(this.Ee);
    DisbursementAccount localDisbursementAccount = localDisbursementSummary.getAccount();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null) {
      return null;
    }
    paramHashMap.put("DATA_CLASSIFICATION", this.Ec);
    return Disbursements.getPreviousTransactions(localSecureUser, localDisbursementAccount, paramPagingContext, paramHashMap);
  }
  
  protected FilteredList getLastPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    this.error = 20112;
    return null;
  }
  
  protected Object getValueForSortCriterion(ReportSortCriterion paramReportSortCriterion, Object paramObject)
  {
    DisbursementTransaction localDisbursementTransaction = (DisbursementTransaction)paramObject;
    if (paramReportSortCriterion.getName().equals("CheckDate")) {
      return localDisbursementTransaction.getCheckDate();
    }
    if (paramReportSortCriterion.getName().equals("CheckAmount")) {
      return localDisbursementTransaction.getCheckAmount();
    }
    if (paramReportSortCriterion.getName().equals("CheckNumber")) {
      return localDisbursementTransaction.getCheckNumber();
    }
    if (paramReportSortCriterion.getName().equals("CheckReferenceNumber")) {
      return localDisbursementTransaction.getCheckReferenceNumber();
    }
    if (paramReportSortCriterion.getName().equals("TransactionIndex")) {
      return new Long(localDisbursementTransaction.getTransactionIndex());
    }
    if (paramReportSortCriterion.getName().equals("BankReferenceNumber")) {
      return localDisbursementTransaction.getBankReferenceNumber();
    }
    if (paramReportSortCriterion.getName().equals("CustomerReferenceNumber")) {
      return localDisbursementTransaction.getCustomerReferenceNumber();
    }
    if (paramReportSortCriterion.getName().equals("Memo")) {
      return localDisbursementTransaction.getMemo();
    }
    if (paramReportSortCriterion.getName().equals("Payee")) {
      return localDisbursementTransaction.getPayee();
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.disbursement.GetPagedTransactions
 * JD-Core Version:    0.7.0.1
 */