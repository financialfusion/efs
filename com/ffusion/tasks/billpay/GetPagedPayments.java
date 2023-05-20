package com.ffusion.tasks.billpay;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.PayeeI18N;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.csil.core.Billpay;
import com.ffusion.services.BillPay;
import com.ffusion.tasks.util.GetPagedData;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.PagingContext;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetPagedPayments
  extends GetPagedData
  implements Task
{
  protected static String DATEFORMAT = "yyyyMMdd";
  protected String _accountsCollection = "BillPayAccounts";
  protected String _payeesCollection = "Payees";
  protected String _recPaymentsCollection = "RecPayments";
  protected String _status = "PAYMENT_STATUS_PENDING";
  protected String _pmtType = null;
  protected String _accountId;
  protected String _amount;
  protected String _payeeId;
  
  public GetPagedPayments()
  {
    setDataSetName("PendingPayments");
  }
  
  public void setFirstPage(String paramString)
  {
    setPage("first");
  }
  
  public void setLastPage(String paramString)
  {
    setPage("last");
  }
  
  public void setNextPage(String paramString)
  {
    setPage("next");
  }
  
  public void setPreviousPage(String paramString)
  {
    setPage("previous");
  }
  
  public void setCollectionSessionName(String paramString)
  {
    setDataSetName(paramString);
  }
  
  public void setStatus(String paramString)
  {
    this._status = paramString;
  }
  
  public String getStatus()
  {
    return this._status;
  }
  
  public String getAccountId()
  {
    return this._accountId;
  }
  
  public void setAccountId(String paramString)
  {
    this._accountId = paramString;
  }
  
  public String getPayeeId()
  {
    return this._payeeId;
  }
  
  public void setPayeeId(String paramString)
  {
    this._payeeId = paramString;
  }
  
  public String getAmount()
  {
    return this._amount;
  }
  
  public void setAmount(String paramString)
  {
    this._amount = paramString;
  }
  
  public String getCollectionSessionName()
  {
    return getDataSetName();
  }
  
  public void setAccountsCollection(String paramString)
  {
    this._accountsCollection = paramString;
  }
  
  public String getAccountsCollection()
  {
    return this._accountsCollection;
  }
  
  public void setPayeesCollection(String paramString)
  {
    this._payeesCollection = paramString;
  }
  
  public void setRecPaymentsCollection(String paramString)
  {
    this._recPaymentsCollection = paramString;
  }
  
  public void setType(String paramString)
  {
    this._pmtType = paramString;
  }
  
  public String getType()
  {
    return this._pmtType;
  }
  
  protected FilteredList getFirstPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = jdMethod_for(localHttpSession, paramHashMap);
    if (localSecureUser == null) {
      return null;
    }
    this._pagingContext.setDirection("FIRST");
    Payments localPayments = Billpay.getPagedPayments(localSecureUser, paramPagingContext, paramHashMap);
    jdMethod_for(paramHashMap, localHttpSession, localPayments, localSecureUser);
    if ((getDateFormat() != null) && ((localPayments instanceof Payments))) {
      ((Payments)localPayments).setDateFormat(getDateFormat());
    }
    return localPayments;
  }
  
  protected FilteredList getNextPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = jdMethod_for(localHttpSession, paramHashMap);
    if (localSecureUser == null) {
      return null;
    }
    this._pagingContext.setDirection("NEXT");
    Payments localPayments = Billpay.getPagedPayments(localSecureUser, paramPagingContext, paramHashMap);
    jdMethod_for(paramHashMap, localHttpSession, localPayments, localSecureUser);
    if ((getDateFormat() != null) && ((localPayments instanceof Payments))) {
      ((Payments)localPayments).setDateFormat(getDateFormat());
    }
    return localPayments;
  }
  
  private SecureUser jdMethod_for(HttpSession paramHttpSession, HashMap paramHashMap)
    throws Exception
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute(this._accountsCollection);
    if (localAccounts == null) {
      return null;
    }
    paramHashMap.put("PAYMENT_ACCOUNTS", localAccounts);
    Payees localPayees = (Payees)paramHttpSession.getAttribute(this._payeesCollection);
    if (localPayees == null) {
      return null;
    }
    paramHashMap.put("PAYMENT_PAYEES", localPayees);
    BillPay localBillPay = (BillPay)paramHttpSession.getAttribute("com.ffusion.services.BillPay");
    if (localBillPay != null) {
      paramHashMap.put("SERVICE", localBillPay);
    }
    if ((this._accountId != null) && (this._accountId.length() > 0)) {
      paramHashMap.put("AcctDebitNumber", this._accountId);
    }
    if ((this._amount != null) && (this._amount.length() > 0))
    {
      Currency localCurrency = new Currency(this._amount, localSecureUser.getLocale());
      if ((localCurrency == null) || (localCurrency.equals(0.0D))) {
        this.error = 2018;
      } else {
        paramHashMap.put("Amount", this._amount);
      }
    }
    if ((this._payeeId != null) && (this._payeeId.length() > 0)) {
      paramHashMap.put("PayeeIdList", this._payeeId);
    }
    paramHashMap.put("PAYMENT_STATUS", this._status);
    paramHashMap.put("PmtType", this._pmtType);
    return localSecureUser;
  }
  
  private void jdMethod_for(Payee paramPayee, SecureUser paramSecureUser)
  {
    if ((paramPayee != null) && (paramPayee.getPayeeI18NInfo() != null))
    {
      PayeeI18N localPayeeI18N = (PayeeI18N)paramPayee.getPayeeI18NInfo().get(paramSecureUser.getLocaleLanguage());
      if (localPayeeI18N != null) {
        paramPayee.setCurrentLanguage(paramSecureUser.getLocaleLanguage());
      } else {
        paramPayee.setCurrentLanguage("en_US");
      }
    }
  }
  
  private void jdMethod_for(HashMap paramHashMap, HttpSession paramHttpSession, FilteredList paramFilteredList, SecureUser paramSecureUser)
  {
    RecPayments localRecPayments = (RecPayments)paramHashMap.get("PAYMENT_RECPAYMENTS");
    if ((localRecPayments != null) && (!localRecPayments.isEmpty())) {
      paramHttpSession.setAttribute(this._recPaymentsCollection, localRecPayments);
    }
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute(this._accountsCollection);
    if (localAccounts == null) {
      return;
    }
    Iterator localIterator = paramFilteredList.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof Payment))
      {
        Payment localPayment = (Payment)localObject;
        Account localAccount = localAccounts.getByID(localPayment.getAccountID());
        if (localAccount != null) {
          localPayment.setAccount(localAccount);
        }
        jdMethod_for(localPayment.getPayee(), paramSecureUser);
      }
    }
  }
  
  protected FilteredList getPreviousPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = jdMethod_for(localHttpSession, paramHashMap);
    if (localSecureUser == null) {
      return null;
    }
    this._pagingContext.setDirection("PREVIOUS");
    Payments localPayments = Billpay.getPagedPayments(localSecureUser, paramPagingContext, paramHashMap);
    jdMethod_for(paramHashMap, localHttpSession, localPayments, localSecureUser);
    if ((getDateFormat() != null) && ((localPayments instanceof Payments))) {
      ((Payments)localPayments).setDateFormat(getDateFormat());
    }
    return localPayments;
  }
  
  protected FilteredList getLastPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = jdMethod_for(localHttpSession, paramHashMap);
    if (localSecureUser == null) {
      return null;
    }
    this._pagingContext.setDirection("LAST");
    Payments localPayments = Billpay.getPagedPayments(localSecureUser, paramPagingContext, paramHashMap);
    jdMethod_for(paramHashMap, localHttpSession, localPayments, localSecureUser);
    if ((getDateFormat() != null) && ((localPayments instanceof Payments))) {
      ((Payments)localPayments).setDateFormat(getDateFormat());
    }
    return localPayments;
  }
  
  protected Object getValueForSortCriterion(ReportSortCriterion paramReportSortCriterion, Object paramObject)
  {
    Payment localPayment = (Payment)paramObject;
    Object localObject;
    if (paramReportSortCriterion.getName().equals("DateToPost"))
    {
      localObject = "";
      DateFormat localDateFormat = DateFormatUtil.getFormatter(DATEFORMAT);
      if (localPayment.getPayDate() != null)
      {
        localObject = localDateFormat.format(localPayment.getPayDateValue().getTime());
        if (localObject != null)
        {
          String str = (String)localPayment.get("BPW_DATE_FORMAT_EXTENSION");
          if (str != null) {
            localObject = (String)localObject + str;
          }
        }
      }
      return localObject;
    }
    if (paramReportSortCriterion.getName().equals("PayeeName"))
    {
      localObject = localPayment.getPayee();
      if ((localObject != null) && (((Payee)localObject).getName("en_US") != null)) {
        return ((Payee)localObject).getName("en_US").toUpperCase(localPayment.getLocale());
      }
      return localPayment.getPayeeName().toUpperCase(localPayment.getLocale());
    }
    if (paramReportSortCriterion.getName().equals("AcctDebitId")) {
      return localPayment.getAccountID();
    }
    if (paramReportSortCriterion.getName().equals("Frequency"))
    {
      if ((localPayment instanceof RecPayment)) {
        localObject = ((RecPayment)localPayment).getFrequency();
      } else {
        localObject = (String)localPayment.get("FrequencyValue");
      }
      return ((String)localObject).toUpperCase(localPayment.getLocale());
    }
    if (paramReportSortCriterion.getName().equals("Status"))
    {
      localObject = Payment.mapPmtStatusToStr(localPayment.getStatus());
      return localObject;
    }
    if (paramReportSortCriterion.getName().equals("Amount")) {
      return localPayment.getAmountForBPW();
    }
    if (paramReportSortCriterion.getName().equals("TransactionIndex")) {
      return new Long(localPayment.getTransactionIndex());
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.GetPagedPayments
 * JD-Core Version:    0.7.0.1
 */