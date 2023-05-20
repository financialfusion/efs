package com.ffusion.tasks.banking;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.tasks.util.GetPagedData;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.PagingContext;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetPagedTransfers
  extends GetPagedData
  implements Task
{
  protected static String DATEFORMAT = "yyyyMMdd";
  protected String _status = "TRANSFER_STATUS_PENDING";
  protected String _accountsCollection = "BankingAccounts";
  protected String _bankingServiceName = "com.ffusion.services.Banking";
  protected String _transType = "TRANS_TYPE_SINGLE";
  protected String _destType = "BOTH";
  protected String _fromAccountId;
  protected String _toAccountId;
  protected String _amount;
  
  public GetPagedTransfers()
  {
    setDataSetName("PendingTransfers");
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
  
  public void setBankingServiceName(String paramString)
  {
    this._bankingServiceName = paramString;
  }
  
  public void setType(String paramString)
  {
    this._transType = paramString;
  }
  
  public String getType()
  {
    return this._transType;
  }
  
  public void setDestination(String paramString)
  {
    this._destType = paramString;
  }
  
  public String getDestination()
  {
    return this._destType;
  }
  
  public void setStatus(String paramString)
  {
    this._status = paramString;
  }
  
  public String getStatus()
  {
    return this._status;
  }
  
  public String getFromAccountId()
  {
    return this._fromAccountId;
  }
  
  public void setFromAccountId(String paramString)
  {
    this._fromAccountId = paramString;
  }
  
  public String getToAccountId()
  {
    return this._toAccountId;
  }
  
  public void setToAccountId(String paramString)
  {
    this._toAccountId = paramString;
  }
  
  public String getAmount()
  {
    return this._amount;
  }
  
  public void setAmount(String paramString)
  {
    this._amount = paramString;
  }
  
  protected FilteredList getFirstPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = jdMethod_new(localHttpSession, paramHashMap);
    if (localSecureUser == null) {
      return null;
    }
    this._pagingContext.setDirection("FIRST");
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    if ((this._fromAccountId != null) && (this._fromAccountId.length() > 0)) {
      paramHashMap.put("FromAcct", this._fromAccountId);
    }
    if ((this._toAccountId != null) && (this._toAccountId.length() > 0)) {
      paramHashMap.put("ToAcct", this._toAccountId);
    }
    if ((this._amount != null) && (this._amount.length() > 0)) {
      paramHashMap.put("Amount", this._amount);
    }
    Transfers localTransfers = com.ffusion.csil.core.Banking.getPagedTransfers(localSecureUser, paramPagingContext, paramHashMap);
    jdMethod_int(paramHashMap, localHttpSession, localTransfers);
    if ((getDateFormat() != null) && ((localTransfers instanceof Transfers))) {
      ((Transfers)localTransfers).setDateFormat(getDateFormat());
    }
    return localTransfers;
  }
  
  protected FilteredList getNextPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = jdMethod_new(localHttpSession, paramHashMap);
    if (localSecureUser == null) {
      return null;
    }
    this._pagingContext.setDirection("NEXT");
    Transfers localTransfers = com.ffusion.csil.core.Banking.getPagedTransfers(localSecureUser, paramPagingContext, paramHashMap);
    jdMethod_int(paramHashMap, localHttpSession, localTransfers);
    if ((getDateFormat() != null) && ((localTransfers instanceof Transfers))) {
      ((Transfers)localTransfers).setDateFormat(getDateFormat());
    }
    return localTransfers;
  }
  
  protected FilteredList getLastPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = jdMethod_new(localHttpSession, paramHashMap);
    if (localSecureUser == null) {
      return null;
    }
    this._pagingContext.setDirection("LAST");
    Transfers localTransfers = com.ffusion.csil.core.Banking.getPagedTransfers(localSecureUser, paramPagingContext, paramHashMap);
    jdMethod_int(paramHashMap, localHttpSession, localTransfers);
    if ((getDateFormat() != null) && ((localTransfers instanceof Transfers))) {
      ((Transfers)localTransfers).setDateFormat(getDateFormat());
    }
    return localTransfers;
  }
  
  private SecureUser jdMethod_new(HttpSession paramHttpSession, HashMap paramHashMap)
    throws Exception
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute(this._accountsCollection);
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHttpSession.getAttribute(this._bankingServiceName);
    if ((localSecureUser == null) || (localAccounts == null) || (localBanking == null)) {
      return null;
    }
    paramHashMap.put("SERVICE", localBanking);
    paramHashMap.put("TRANSFER_ACCOUNTS", localAccounts);
    paramHashMap.put("TRANSFER_STATUS", this._status);
    paramHashMap.put("TransType", this._transType);
    paramHashMap.put("Dests", this._destType);
    return localSecureUser;
  }
  
  protected FilteredList getPreviousPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = jdMethod_new(localHttpSession, paramHashMap);
    if (localSecureUser == null) {
      return null;
    }
    this._pagingContext.setDirection("PREVIOUS");
    Transfers localTransfers = com.ffusion.csil.core.Banking.getPagedTransfers(localSecureUser, paramPagingContext, paramHashMap);
    jdMethod_int(paramHashMap, localHttpSession, localTransfers);
    if ((getDateFormat() != null) && ((localTransfers instanceof Transfers))) {
      ((Transfers)localTransfers).setDateFormat(getDateFormat());
    }
    return localTransfers;
  }
  
  protected Object getValueForSortCriterion(ReportSortCriterion paramReportSortCriterion, Object paramObject)
  {
    Transfer localTransfer = (Transfer)paramObject;
    String str;
    if (paramReportSortCriterion.getName().equals("DateToPost"))
    {
      str = "";
      DateFormat localDateFormat = DateFormatUtil.getFormatter(DATEFORMAT);
      if (localTransfer.getDate() != null)
      {
        if ((localTransfer.getTransferDestination().equals("INTERNAL")) || (localTransfer.getTransferDestination().equals("ITOI"))) {
          str = localDateFormat.format(localTransfer.getDateToPostValue().getTime());
        } else {
          str = localDateFormat.format(localTransfer.getDateValue().getTime());
        }
        if (str != null) {
          str = str + "00";
        }
      }
      return str;
    }
    if (paramReportSortCriterion.getName().equals("Destination")) {
      return localTransfer.getTransferDestination();
    }
    if (paramReportSortCriterion.getName().equals("FromAcctId")) {
      return localTransfer.getFromAccountID();
    }
    if (paramReportSortCriterion.getName().equals("ToAcctId")) {
      return localTransfer.getToAccountID();
    }
    if (paramReportSortCriterion.getName().equals("Frequency"))
    {
      if ((localTransfer instanceof RecTransfer)) {
        str = com.ffusion.services.banksim2.Banking.getFrequencyString(((RecTransfer)localTransfer).getFrequencyValue());
      } else {
        str = (String)localTransfer.get("FrequencyValue");
      }
      return str;
    }
    if (paramReportSortCriterion.getName().equals("Status"))
    {
      str = Transfer.mapXferStatusToStr(localTransfer.getStatus());
      return str;
    }
    if (paramReportSortCriterion.getName().equals("TransactionType")) {
      return localTransfer.getTransferType();
    }
    if (paramReportSortCriterion.getName().equals("Amount")) {
      return localTransfer.getAmountForBPW();
    }
    if (paramReportSortCriterion.getName().equals("ToAmount")) {
      return localTransfer.getToAmountForBPW();
    }
    if (paramReportSortCriterion.getName().equals("TransactionIndex")) {
      return new Long(localTransfer.getTransactionIndex());
    }
    return null;
  }
  
  private void jdMethod_int(HashMap paramHashMap, HttpSession paramHttpSession, FilteredList paramFilteredList)
  {
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute(this._accountsCollection);
    if (localAccounts == null) {
      return;
    }
    Iterator localIterator = paramFilteredList.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof Transfer))
      {
        Transfer localTransfer = (Transfer)localObject;
        Account localAccount = localAccounts.getByID(localTransfer.getFromAccountID());
        if (localAccount != null) {
          localTransfer.setFromAccount(localAccount);
        }
        localAccount = localAccounts.getByID(localTransfer.getToAccountID());
        if (localAccount != null) {
          localTransfer.setToAccount(localAccount);
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetPagedTransfers
 * JD-Core Version:    0.7.0.1
 */