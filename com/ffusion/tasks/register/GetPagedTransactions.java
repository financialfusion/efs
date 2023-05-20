package com.ffusion.tasks.register;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.util.GetPagedData;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.InvalidDateTimeException;
import com.ffusion.util.beans.PagingContext;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetPagedTransactions
  extends GetPagedData
  implements Task
{
  public static final String GETPAGEDTRANSACTIONS = "GetPagedTransactions";
  String Ex = "Account";
  String Et;
  private boolean Ev;
  private String Eu = null;
  private String Ew = null;
  private static String Es = "MM/dd/yyyy HH:mm:ss";
  
  public GetPagedTransactions()
  {
    setDataSetName("RegisterTransactions");
    this.Et = "P";
    this.Ev = false;
  }
  
  public void setAccountName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.Ex = "Account";
    } else {
      this.Ex = paramString;
    }
  }
  
  public String getFutureTransactionsTable()
  {
    return this.Ev + "";
  }
  
  public void setFutureTransactionsTable(String paramString)
  {
    try
    {
      this.Ev = Boolean.valueOf(paramString).booleanValue();
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public String getAccountName()
  {
    return this.Ex;
  }
  
  public void setTransactionsName(String paramString)
  {
    setDataSetName(paramString);
  }
  
  public PagingContext getPagingContext()
  {
    return this._pagingContext;
  }
  
  public void setPagingContext(PagingContext paramPagingContext)
  {
    this._pagingContext = paramPagingContext;
  }
  
  public String getTransactionsName()
  {
    return getDataSetName();
  }
  
  public String getDataClassification()
  {
    return this.Et;
  }
  
  public void setDataClassification(String paramString)
  {
    this.Et = paramString;
  }
  
  protected FilteredList getFirstPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    if (jdMethod_for(paramPagingContext, paramHashMap))
    {
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      Account localAccount = (Account)localHttpSession.getAttribute(this.Ex);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      if (localAccount == null)
      {
        this.error = 20001;
        return null;
      }
      if (localSecureUser == null)
      {
        this.error = 20009;
        return null;
      }
      paramHashMap.put("DATA_CLASSIFICATION", this.Et);
      ((ReportCriteria)paramPagingContext.getMap().get("ReportCriteria")).getSearchCriteria().setProperty("AccountID", localAccount.getID());
      RegisterTransactions localRegisterTransactions = Register.getPagedTransactions(localSecureUser, paramPagingContext, paramHashMap);
      return jdMethod_for(localRegisterTransactions);
    }
    return jdMethod_for(new RegisterTransactions(), paramPagingContext);
  }
  
  protected FilteredList getNextPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    if (jdMethod_for(paramPagingContext, paramHashMap))
    {
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      Account localAccount = (Account)localHttpSession.getAttribute(this.Ex);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      if (localAccount == null)
      {
        this.error = 20001;
        return null;
      }
      if (localSecureUser == null)
      {
        this.error = 20009;
        return null;
      }
      paramHashMap.put("DATA_CLASSIFICATION", this.Et);
      ((ReportCriteria)paramPagingContext.getMap().get("ReportCriteria")).getSearchCriteria().setProperty("AccountID", localAccount.getID());
      RegisterTransactions localRegisterTransactions = Register.getNextTransactions(localSecureUser, paramPagingContext, paramHashMap);
      return jdMethod_for(localRegisterTransactions);
    }
    return jdMethod_for(new RegisterTransactions(), paramPagingContext);
  }
  
  protected FilteredList getPreviousPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    if (jdMethod_for(paramPagingContext, paramHashMap))
    {
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      Account localAccount = (Account)localHttpSession.getAttribute(this.Ex);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      if (localAccount == null)
      {
        this.error = 20001;
        return null;
      }
      if (localSecureUser == null)
      {
        this.error = 20009;
        return null;
      }
      paramHashMap.put("DATA_CLASSIFICATION", this.Et);
      ((ReportCriteria)paramPagingContext.getMap().get("ReportCriteria")).getSearchCriteria().setProperty("AccountID", localAccount.getID());
      RegisterTransactions localRegisterTransactions = Register.getPreviousTransactions(localSecureUser, paramPagingContext, paramHashMap);
      return jdMethod_for(localRegisterTransactions);
    }
    return jdMethod_for(new RegisterTransactions(), paramPagingContext);
  }
  
  protected FilteredList getLastPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    if (jdMethod_for(paramPagingContext, paramHashMap))
    {
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      Account localAccount = (Account)localHttpSession.getAttribute(this.Ex);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      if (localAccount == null)
      {
        this.error = 20001;
        return null;
      }
      if (localSecureUser == null)
      {
        this.error = 20009;
        return null;
      }
      paramHashMap.put("DATA_CLASSIFICATION", this.Et);
      ((ReportCriteria)paramPagingContext.getMap().get("ReportCriteria")).getSearchCriteria().setProperty("AccountID", localAccount.getID());
      RegisterTransactions localRegisterTransactions = Register.getLastTransactions(localSecureUser, paramPagingContext, paramHashMap);
      return jdMethod_for(localRegisterTransactions);
    }
    return jdMethod_for(new RegisterTransactions(), paramPagingContext);
  }
  
  protected Object getValueForSortCriterion(ReportSortCriterion paramReportSortCriterion, Object paramObject)
  {
    RegisterTransaction localRegisterTransaction = (RegisterTransaction)paramObject;
    if (paramReportSortCriterion.getName().equals("Date Issued")) {
      return localRegisterTransaction.getDateIssuedValue();
    }
    if (paramReportSortCriterion.getName().equals("Date Cleared")) {
      return localRegisterTransaction.getDateValue();
    }
    if (paramReportSortCriterion.getName().equals("Payee Name")) {
      return localRegisterTransaction.getPayeeName();
    }
    if (paramReportSortCriterion.getName().equals("Type")) {
      return localRegisterTransaction.getType();
    }
    if (paramReportSortCriterion.getName().equals("Reference Number")) {
      return localRegisterTransaction.getReferenceNumber();
    }
    if (paramReportSortCriterion.getName().equals("TransactionIndex")) {
      return new Integer(localRegisterTransaction.getRegisterIdValue());
    }
    if (paramReportSortCriterion.getName().equals("Amount"))
    {
      localRegisterTransaction.setCurrent(null);
      return localRegisterTransaction.getAmountValue();
    }
    return null;
  }
  
  private boolean jdMethod_for(PagingContext paramPagingContext, HashMap paramHashMap)
  {
    if (this.Ev) {
      paramPagingContext.getMap().put("TransactionsFilter", "Future");
    } else {
      paramPagingContext.getMap().put("TransactionsFilter", "Current");
    }
    String str1 = super.getSearchCriteriaKey();
    super.setSearchCriteriaKey("Date Range Type");
    String str2 = super.getSearchCriteriaValue();
    if ((str2 != null) && ("Relative".equals(str2)))
    {
      super.setSearchCriteriaKey(str1);
      return true;
    }
    super.setSearchCriteriaKey(str1);
    Object localObject1 = super.getStartDateValue();
    this.Eu = super.getStartDate();
    if (localObject1 == null)
    {
      paramPagingContext.setStartDate(null);
      this._criteria.getSearchCriteria().remove("StartDate");
    }
    else
    {
      localObject1 = (com.ffusion.util.beans.DateTime)((com.ffusion.util.beans.DateTime)localObject1).clone();
    }
    Object localObject2 = super.getEndDateValue();
    this.Ew = super.getEndDate();
    if (localObject2 == null)
    {
      paramPagingContext.setEndDate(null);
      this._criteria.getSearchCriteria().remove("EndDate");
    }
    else
    {
      localObject2 = (com.ffusion.util.beans.DateTime)super.getEndDateValue().clone();
    }
    if ((localObject2 == null) || (localObject1 == null)) {
      return true;
    }
    Calendar localCalendar = Calendar.getInstance();
    String str3;
    String str4;
    com.ffusion.util.beans.DateTime localDateTime;
    if (this.Ev)
    {
      if ((localObject1 != null) && ((localCalendar.after(localObject1)) || (jdMethod_for(localCalendar, (Calendar)localObject1))))
      {
        localCalendar.add(5, 1);
        str3 = this._criteria.getSearchCriteria().getProperty("StartTime");
        str4 = "HH:mm";
        if ((str3 != null) && (str3.trim().length() != 0) && (!str3.trim().equalsIgnoreCase(str4)))
        {
          localDateTime = null;
          try
          {
            Locale localLocale1 = (Locale)paramHashMap.get("Locale");
            if (localLocale1 == null) {
              localLocale1 = Locale.getDefault();
            }
            localDateTime = new com.ffusion.util.beans.DateTime(str3, localLocale1, str4);
            ((com.ffusion.util.beans.DateTime)localObject1).set(11, localDateTime.get(11));
            ((com.ffusion.util.beans.DateTime)localObject1).set(12, localDateTime.get(12));
            ((com.ffusion.util.beans.DateTime)localObject1).set(13, localDateTime.get(13));
            ((com.ffusion.util.beans.DateTime)localObject1).set(14, localDateTime.get(14));
            ((com.ffusion.util.beans.DateTime)localObject1).add(5, 1);
          }
          catch (InvalidDateTimeException localInvalidDateTimeException1)
          {
            ((com.ffusion.util.beans.DateTime)localObject1).set(14, 0);
            ((com.ffusion.util.beans.DateTime)localObject1).set(13, 0);
            ((com.ffusion.util.beans.DateTime)localObject1).set(12, 0);
            ((com.ffusion.util.beans.DateTime)localObject1).set(11, 0);
            ((com.ffusion.util.beans.DateTime)localObject1).add(5, 1);
          }
        }
        else
        {
          ((com.ffusion.util.beans.DateTime)localObject1).set(14, 0);
          ((com.ffusion.util.beans.DateTime)localObject1).set(13, 0);
          ((com.ffusion.util.beans.DateTime)localObject1).set(12, 0);
          ((com.ffusion.util.beans.DateTime)localObject1).set(11, 0);
          ((com.ffusion.util.beans.DateTime)localObject1).add(5, 1);
        }
        this._criteria.getSearchCriteria().setProperty("StartDate", DateFormatUtil.getFormatter(Es).format(((com.ffusion.util.beans.DateTime)localObject1).getTime()));
        localCalendar.add(5, -1);
      }
      if ((localObject2 == null) || (((com.ffusion.util.beans.DateTime)localObject1).after(localObject2))) {
        return false;
      }
    }
    else if (!this.Ev)
    {
      if ((localObject2 != null) && (localObject1 != null) && (localCalendar.before(localObject2)) && (!jdMethod_for(localCalendar, (Calendar)localObject1)))
      {
        this.Ew = super.getEndDate();
        str3 = this._criteria.getSearchCriteria().getProperty("EndTime");
        str4 = "HH:mm";
        if ((str3 != null) && (str3.trim().length() != 0) && (!str3.trim().equalsIgnoreCase(str4)))
        {
          localDateTime = null;
          try
          {
            Locale localLocale2 = (Locale)paramHashMap.get("Locale");
            if (localLocale2 == null) {
              localLocale2 = Locale.getDefault();
            }
            localDateTime = new com.ffusion.util.beans.DateTime(str3, localLocale2, str4);
            ((com.ffusion.util.beans.DateTime)localObject2).set(11, localDateTime.get(11));
            ((com.ffusion.util.beans.DateTime)localObject2).set(12, localDateTime.get(12));
            ((com.ffusion.util.beans.DateTime)localObject2).set(13, localDateTime.get(13));
            ((com.ffusion.util.beans.DateTime)localObject2).set(14, localDateTime.get(14));
          }
          catch (InvalidDateTimeException localInvalidDateTimeException2)
          {
            ((com.ffusion.util.beans.DateTime)localObject2).set(11, 23);
            ((com.ffusion.util.beans.DateTime)localObject2).set(12, 59);
            ((com.ffusion.util.beans.DateTime)localObject2).set(13, 59);
            ((com.ffusion.util.beans.DateTime)localObject2).set(14, 999);
          }
        }
        else
        {
          ((com.ffusion.util.beans.DateTime)localObject2).set(11, 23);
          ((com.ffusion.util.beans.DateTime)localObject2).set(12, 59);
          ((com.ffusion.util.beans.DateTime)localObject2).set(13, 59);
          ((com.ffusion.util.beans.DateTime)localObject2).set(14, 999);
        }
        this._criteria.getSearchCriteria().setProperty("EndDate", DateFormatUtil.getFormatter(Es).format(((com.ffusion.util.beans.DateTime)localObject2).getTime()));
      }
      if (((com.ffusion.util.beans.DateTime)localObject1).after(localObject2)) {
        return false;
      }
    }
    return true;
  }
  
  private boolean jdMethod_for(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    return (paramCalendar1.get(5) == paramCalendar2.get(5)) && (paramCalendar1.get(2) == paramCalendar2.get(2)) && (paramCalendar1.get(1) == paramCalendar2.get(1));
  }
  
  private void n()
  {
    if (this.Eu != null)
    {
      super.setStartDate(this.Eu);
      this._criteria.getSearchCriteria().setProperty("StartDate", this.Eu);
      this.Eu = null;
    }
    if (this.Ew != null)
    {
      super.setEndDate(this.Ew);
      this._criteria.getSearchCriteria().setProperty("EndDate", this.Ew);
      this.Ew = null;
    }
  }
  
  private FilteredList jdMethod_for(RegisterTransactions paramRegisterTransactions)
  {
    paramRegisterTransactions.setDateFormat(getDateFormat());
    n();
    return paramRegisterTransactions;
  }
  
  private FilteredList jdMethod_for(RegisterTransactions paramRegisterTransactions, PagingContext paramPagingContext)
  {
    paramPagingContext.getMap().put("TOTAL_TRANS", "0");
    return jdMethod_for(new RegisterTransactions());
  }
  
  public void setCompareSortCriterion(String paramString)
  {
    if (paramString.equals("DateIssued")) {
      paramString = "Date Issued";
    } else if (paramString.equals("DateCleared")) {
      paramString = "Date Cleared";
    } else if (paramString.equals("PayeeName")) {
      paramString = "Payee Name";
    } else if (paramString.equals("ReferenceNumber")) {
      paramString = "Reference Number";
    } else if (paramString.equals("DateIssued".concat(",REVERSE"))) {
      paramString = "Date Issued".concat(",REVERSE");
    } else if (paramString.equals("DateCleared".concat(",REVERSE"))) {
      paramString = "Date Cleared".concat(",REVERSE");
    } else if (paramString.equals("PayeeName".concat(",REVERSE"))) {
      paramString = "Payee Name".concat(",REVERSE");
    } else if (paramString.equals("ReferenceNumber".concat(",REVERSE"))) {
      paramString = "Reference Number".concat(",REVERSE");
    }
    super.setCompareSortCriterion(paramString);
  }
  
  public void setSortCriteriaName(String paramString)
  {
    if (this._currentSortCriterion == null) {
      return;
    }
    if (paramString.equals("DateIssued")) {
      paramString = "Date Issued";
    } else if (paramString.equals("DateCleared")) {
      paramString = "Date Cleared";
    } else if (paramString.equals("PayeeName")) {
      paramString = "Payee Name";
    } else if (paramString.equals("ReferenceNumber")) {
      paramString = "Reference Number";
    } else if (paramString.equals("DateIssued".concat(",REVERSE"))) {
      paramString = "Date Issued".concat(",REVERSE");
    } else if (paramString.equals("DateCleared".concat(",REVERSE"))) {
      paramString = "Date Cleared".concat(",REVERSE");
    } else if (paramString.equals("PayeeName".concat(",REVERSE"))) {
      paramString = "Payee Name".concat(",REVERSE");
    } else if (paramString.equals("ReferenceNumber".concat(",REVERSE"))) {
      paramString = "Reference Number".concat(",REVERSE");
    }
    super.setSortCriteriaName(paramString);
  }
  
  public String getSortImage()
  {
    String str1 = super.getCompareSortCriterion().trim();
    String str2;
    if (str1.indexOf(",REVERSE") != -1)
    {
      int i = str1.indexOf(",REVERSE");
      str2 = str1.substring(0, i);
    }
    else
    {
      str2 = str1;
    }
    if (this._currentSortCriterion.getName().equalsIgnoreCase(str2))
    {
      if (this._currentSortCriterion.getAsc()) {
        return super.getAscendingSortImage();
      }
      return super.getDescendingSortImage();
    }
    return super.getNoSortImage();
  }
  
  public void setSearchCriteriaKey(String paramString)
  {
    if (paramString.equals("DateRange")) {
      paramString = "Date Range";
    }
    super.setSearchCriteriaKey(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.GetPagedTransactions
 * JD-Core Version:    0.7.0.1
 */