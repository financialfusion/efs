package com.ffusion.tasks.util;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.PagingContext;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class GetPagedData
  extends BaseTask
{
  public static final String PAGE_RECENT = "recent";
  public static final String PAGE_FIRST = "first";
  public static final String PAGE_NEXT = "next";
  public static final String PAGE_PREV = "previous";
  public static final String PAGE_LAST = "last";
  public static final String PAGE_CURRENT = "current";
  public static final String SERVICE_ERROR_SESSION_NAME = "com.ffusion.tasks.BaseTask.taskErrorURL";
  public static final String TASK_ERROR_SESSION_NAME = "com.ffusion.tasks.BaseTask.serviceErrorURL";
  public static final String SERVICE_ERROR = "SE";
  public static final String SERVICE_ERROR_WHY = "SE_WHY";
  public static final String SERVICE_ERROR_WHERE = "SE_WHERE";
  public static final String TASK_ERROR = "TE";
  public static final String TASKTIMEOUTSECS = "TaskTimeoutSecs";
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected int error = 0;
  protected int _maxAmountLength = 32;
  String dK = "recent";
  protected PagingContext _pagingContext;
  String dS;
  String dJ;
  String dO;
  String dM;
  String dL;
  String dN;
  protected int _totalPages;
  protected int _currentPage;
  protected boolean _enforceMaxDateRangeCheck = false;
  protected boolean _enforceNoFutureDatesCheck = false;
  protected int _maxDaysInDateRange = 90;
  protected ReportCriteria _criteria = new ReportCriteria();
  String dR = null;
  protected ReportSortCriterion _currentSortCriterion = null;
  String dI;
  protected String _nextURL;
  String dQ = null;
  String dP = null;
  
  public String getDataSetName()
  {
    return this.dO;
  }
  
  public void setDataSetName(String paramString)
  {
    this.dO = paramString;
  }
  
  public void setPage(String paramString)
  {
    if ((paramString == null) || ((!paramString.equals("recent")) && (!paramString.equals("first")) && (!paramString.equals("next")) && (!paramString.equals("previous")) && (!paramString.equals("current")) && (!paramString.equals("last")))) {
      this.dK = "recent";
    } else {
      this.dK = paramString;
    }
  }
  
  public String getPage()
  {
    return this.dK;
  }
  
  public void setStartDate(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0) && (!paramString.equalsIgnoreCase(this.dS))) {
      this.dQ = paramString;
    } else {
      this.dQ = null;
    }
  }
  
  protected DateTime getStartDateValue()
  {
    if ((this.dQ == null) || (this.dQ.length() == 0)) {
      return null;
    }
    DateTime localDateTime = null;
    try
    {
      localDateTime = new DateTime(this.dQ, null, getDateFormat());
      localDateTime.set(11, 0);
      localDateTime.set(12, 0);
      localDateTime.set(13, 0);
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
    return localDateTime;
  }
  
  public String getStartDate()
  {
    String str1 = this.dQ;
    try
    {
      DateFormat localDateFormat1 = DateFormatUtil.getFormatter(this.dS);
      DateFormat localDateFormat2 = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
      DateTime localDateTime = new DateTime(localDateFormat2.parse(str1), Locale.getDefault());
      String str2 = localDateFormat1.format(localDateTime.getTime());
      return str2;
    }
    catch (Exception localException) {}
    return str1;
  }
  
  public void setEndDate(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0) && (!paramString.equalsIgnoreCase(this.dS))) {
      this.dP = paramString;
    } else {
      this.dP = null;
    }
  }
  
  public String getEndDate()
  {
    String str1 = this.dP;
    try
    {
      DateFormat localDateFormat1 = DateFormatUtil.getFormatter(this.dS);
      DateFormat localDateFormat2 = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
      DateTime localDateTime = new DateTime(localDateFormat2.parse(str1), Locale.getDefault());
      String str2 = localDateFormat1.format(localDateTime.getTime());
      return str2;
    }
    catch (Exception localException) {}
    return str1;
  }
  
  protected DateTime getEndDateValue()
  {
    if ((this.dP == null) || (this.dP.length() == 0)) {
      return null;
    }
    DateTime localDateTime = null;
    try
    {
      localDateTime = new DateTime(this.dP, null, getDateFormat());
      localDateTime.set(11, 23);
      localDateTime.set(12, 59);
      localDateTime.set(13, 59);
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
    return localDateTime;
  }
  
  public void setDateFormat(String paramString)
  {
    this._criteria.getReportOptions().setProperty("DATEFORMAT", paramString);
  }
  
  public String getDateFormat()
  {
    return this._criteria.getReportOptions().getProperty("DATEFORMAT");
  }
  
  public void setMaxDaysInDateRange(String paramString)
  {
    try
    {
      this._criteria.getReportOptions().setProperty("MAX_DAYS_IN_DATE_RANGE", paramString);
      this._maxDaysInDateRange = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public void setDateFormatParam(String paramString)
  {
    this.dS = paramString;
  }
  
  public String getDateFormatParam()
  {
    return this.dS;
  }
  
  public String getPageNumber()
  {
    return this._pagingContext.getPageNumber() + "";
  }
  
  public String getIsFirstPage()
  {
    return String.valueOf(this._pagingContext.isFirstPage());
  }
  
  public String getIsLastPage()
  {
    return String.valueOf(this._pagingContext.isLastPage());
  }
  
  public void setClearSearchCriteria(String paramString)
  {
    if ((this._criteria == null) || (this._criteria.getSearchCriteria() == null)) {
      return;
    }
    this._criteria.getSearchCriteria().clear();
    this.dR = null;
  }
  
  public void setSearchCriteriaKey(String paramString)
  {
    this.dR = paramString;
  }
  
  public String getSearchCriteriaKey()
  {
    return this.dR;
  }
  
  public void setSearchCriteriaValue(String paramString)
  {
    if (this.dR == null) {
      return;
    }
    if (this._criteria == null)
    {
      this._criteria = new ReportCriteria();
      this._criteria.setSearchCriteria(new Properties());
    }
    if ((paramString != null) && (paramString.length() > 0)) {
      this._criteria.getSearchCriteria().setProperty(this.dR, paramString);
    } else {
      this._criteria.getSearchCriteria().remove(this.dR);
    }
  }
  
  public String getSearchCriteriaValue()
  {
    if (this.dR == null) {
      return null;
    }
    if (this._criteria == null) {
      return null;
    }
    if (this._criteria.getSearchCriteria() == null) {
      return null;
    }
    return this._criteria.getSearchCriteria().getProperty(this.dR);
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this._nextURL = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      this.error = 0;
      Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
      this.error = validateDates(localLocale);
      if (this.error != 0) {
        return this.taskErrorURL;
      }
      if (this._enforceMaxDateRangeCheck)
      {
        this.error = setFillBlankDateValues();
        if (this.error != 0) {
          return this.taskErrorURL;
        }
        this.error = validateMaximumDateRange();
        if (this.error != 0) {
          return this.taskErrorURL;
        }
      }
      this.error = setDatesInReportCriteria(localLocale);
      if (this.error != 0) {
        return this.taskErrorURL;
      }
      HashMap localHashMap1 = new HashMap();
      if ((this.dJ != null) && (this.dJ.length() > 0)) {
        localHashMap1.put("PAGESIZE", this.dJ);
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      localHashMap1.put("Locale", getLocale(localHttpSession, localSecureUser));
      FilteredList localFilteredList = null;
      if ((this.dK.equals("next")) && (this._pagingContext != null) && (!this._pagingContext.isLastPage()))
      {
        localFilteredList = getNextPage(paramHttpServletRequest, this._pagingContext, localHashMap1);
        this._currentPage += 1;
      }
      else if ((this.dK.equals("previous")) && (this._pagingContext != null) && (!this._pagingContext.isFirstPage()))
      {
        localFilteredList = getPreviousPage(paramHttpServletRequest, this._pagingContext, localHashMap1);
        this._currentPage -= 1;
      }
      else if ((this.dK.equals("last")) && (this._pagingContext != null))
      {
        localFilteredList = getLastPage(paramHttpServletRequest, this._pagingContext, localHashMap1);
        this._currentPage = this._totalPages;
      }
      else
      {
        if ((this.dK.equals("next")) && (this._pagingContext != null) && (this._pagingContext.isLastPage())) {
          localFilteredList = (FilteredList)localHttpSession.getAttribute(this.dO);
        }
        if ((this.dK.equals("previous")) && (this._pagingContext != null) && (this._pagingContext.isFirstPage())) {
          localFilteredList = (FilteredList)localHttpSession.getAttribute(this.dO);
        }
        if (this.dK.equals("current")) {
          localFilteredList = (FilteredList)localHttpSession.getAttribute(this.dO);
        }
        if (localFilteredList == null)
        {
          this._pagingContext = new PagingContext(getStartDateValue(), getEndDateValue());
          HashMap localHashMap2 = new HashMap();
          localHashMap2.put("ReportCriteria", this._criteria);
          this._pagingContext.setMap(localHashMap2);
          localFilteredList = getFirstPage(paramHttpServletRequest, this._pagingContext, localHashMap1);
          if (this.error != 0) {
            return this.taskErrorURL;
          }
          this._currentPage = 1;
        }
      }
      localFilteredList.setLocale(localLocale);
      processData(localFilteredList);
      c();
      int i = this._pagingContext.getPageNumber();
      if (0 == getTotalPages()) {
        i = 0;
      } else if (("first".equals(getPage())) || ("recent".equals(getPage())) || (("current".equals(getPage())) && (i == 0))) {
        i = 1;
      } else if ("last".equals(getPage())) {
        i = getTotalPages();
      } else if ("next".equals(getPage())) {
        i++;
      } else if ("previous".equals(getPage())) {
        i--;
      }
      this._pagingContext.setPageNumber(i);
      localHttpSession.setAttribute(this.dO, localFilteredList);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      this._nextURL = this.serviceErrorURL;
    }
    catch (Exception localException)
    {
      this.error = -1009;
      this._nextURL = this.serviceErrorURL;
    }
    return this._nextURL;
  }
  
  private void c()
  {
    String str1 = (String)this._pagingContext.getMap().get("TOTAL_TRANS");
    String str2 = (String)this._pagingContext.getMap().get("PAGE_SIZE");
    if ((str1 != null) && (str2 != null))
    {
      int i = Integer.parseInt(str1);
      int j = Integer.parseInt(str2);
      if (j == 0)
      {
        this._totalPages = 1;
      }
      else if (i == 0)
      {
        this._totalPages = 1;
      }
      else
      {
        this._totalPages = (i / j);
        if (i % j != 0) {
          this._totalPages += 1;
        }
      }
    }
  }
  
  protected void processData(FilteredList paramFilteredList)
    throws Exception
  {
    if ((paramFilteredList == null) || (paramFilteredList.size() == 0))
    {
      this._pagingContext.setFirstPage(true);
      this._pagingContext.setLastPage(true);
      return;
    }
    HashMap localHashMap = this._pagingContext.getMap();
    if (localHashMap == null)
    {
      localHashMap = new HashMap();
      this._pagingContext.setMap(localHashMap);
    }
    boolean bool1 = true;
    boolean bool2 = true;
    int i = 1;
    for (int j = 0; j < this._criteria.getSortCriteria().size(); j++)
    {
      localObject2 = (ReportSortCriterion)this._criteria.getSortCriteria().get(j);
      localObject3 = getValueForSortCriterion((ReportSortCriterion)localObject2, paramFilteredList.get(0));
      localObject4 = getValueForSortCriterion((ReportSortCriterion)localObject2, paramFilteredList.get(paramFilteredList.size() - 1));
      Object localObject5 = localHashMap.get("LOWER_BOUND_" + ((ReportSortCriterion)localObject2).getName());
      Object localObject6 = localHashMap.get("UPPER_BOUND_" + ((ReportSortCriterion)localObject2).getName());
      if (((ReportSortCriterion)localObject2).getAsc())
      {
        i = 1;
        localHashMap.put("SORT_VALUE_MIN_" + ((ReportSortCriterion)localObject2).getName(), localObject3);
        localHashMap.put("SORT_VALUE_MAX_" + ((ReportSortCriterion)localObject2).getName(), localObject4);
        if ((localObject5 != null) && (localObject3 != null) && (!localObject5.equals(localObject3))) {
          bool1 = false;
        }
        if ((localObject6 != null) && (localObject4 != null) && (!localObject6.equals(localObject4))) {
          bool2 = false;
        }
      }
      else
      {
        i = 0;
        localHashMap.put("SORT_VALUE_MIN_" + ((ReportSortCriterion)localObject2).getName(), localObject4);
        localHashMap.put("SORT_VALUE_MAX_" + ((ReportSortCriterion)localObject2).getName(), localObject3);
        if ((localObject5 != null) && (localObject4 != null) && (!localObject5.equals(localObject4))) {
          bool2 = false;
        }
        if ((localObject6 != null) && (localObject3 != null) && (!localObject6.equals(localObject3))) {
          bool1 = false;
        }
      }
    }
    Object localObject1 = getValueForSortCriterion(new ReportSortCriterion(1, "TransactionIndex", true), paramFilteredList.get(0));
    Object localObject2 = getValueForSortCriterion(new ReportSortCriterion(1, "TransactionIndex", true), paramFilteredList.get(paramFilteredList.size() - 1));
    Object localObject3 = localHashMap.get("LOWER_BOUND_TransactionIndex");
    Object localObject4 = localHashMap.get("UPPER_BOUND_TransactionIndex");
    if (i != 0)
    {
      localHashMap.put("SORT_VALUE_MIN_TransactionIndex", localObject1);
      localHashMap.put("SORT_VALUE_MAX_TransactionIndex", localObject2);
      if ((localObject3 != null) && (localObject1 != null) && (!localObject3.equals(localObject1))) {
        bool1 = false;
      }
      if ((localObject4 != null) && (localObject2 != null) && (!localObject4.equals(localObject2))) {
        bool2 = false;
      }
    }
    else
    {
      localHashMap.put("SORT_VALUE_MIN_TransactionIndex", localObject2);
      localHashMap.put("SORT_VALUE_MAX_TransactionIndex", localObject1);
      if ((localObject3 != null) && (localObject2 != null) && (!localObject3.equals(localObject2))) {
        bool2 = false;
      }
      if ((localObject4 != null) && (localObject1 != null) && (!localObject4.equals(localObject1))) {
        bool1 = false;
      }
    }
    this._pagingContext.setFirstPage(bool1);
    this._pagingContext.setLastPage(bool2);
  }
  
  public void setPageSize(String paramString)
  {
    this.dJ = paramString;
  }
  
  public String getPageSize()
  {
    return this.dJ;
  }
  
  public String getSortCriteriaOrdinal()
  {
    if (this._currentSortCriterion == null) {
      return "";
    }
    return String.valueOf(this._currentSortCriterion.getOrdinal());
  }
  
  public void setSortCriteriaOrdinal(String paramString)
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      return;
    }
    if (this._criteria == null)
    {
      this._criteria = new ReportCriteria();
      this._criteria.setSearchCriteria(new Properties());
    }
    for (int j = 0; j < this._criteria.getSortCriteria().size(); j++)
    {
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)this._criteria.getSortCriteria().get(j);
      if (localReportSortCriterion.getOrdinal() == i)
      {
        this._currentSortCriterion = localReportSortCriterion;
        return;
      }
    }
    this._currentSortCriterion = this._criteria.getSortCriteria().create(i, "", true);
  }
  
  public String getSortCriteriaName()
  {
    if (this._currentSortCriterion == null) {
      return "";
    }
    return this._currentSortCriterion.getName();
  }
  
  public void setSortCriteriaName(String paramString)
  {
    if (this._currentSortCriterion == null) {
      return;
    }
    this._currentSortCriterion.setName(paramString);
  }
  
  public String getSortCriteriaAsc()
  {
    if (this._currentSortCriterion == null) {
      return "";
    }
    return String.valueOf(this._currentSortCriterion.getAsc());
  }
  
  public void setSortCriteriaAsc(String paramString)
  {
    if (this._currentSortCriterion == null) {
      return;
    }
    try
    {
      this._currentSortCriterion.setAsc(Boolean.valueOf(paramString).booleanValue());
    }
    catch (Exception localException) {}
  }
  
  public void setSortedBy(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
    String str1 = new String();
    if (localStringTokenizer.hasMoreTokens()) {
      str1 = (String)localStringTokenizer.nextElement();
    }
    int i = 0;
    if (localStringTokenizer.hasMoreTokens())
    {
      String str2 = (String)localStringTokenizer.nextElement();
      if ((str2 != null) && (str2.trim().equalsIgnoreCase("REVERSE"))) {
        i = 1;
      }
    }
    this._currentSortCriterion.setName(str1);
    this._currentSortCriterion.setAsc(i == 0);
  }
  
  public String getSortedBy()
  {
    if (this._currentSortCriterion == null) {
      return "";
    }
    if (this._currentSortCriterion.getAsc()) {
      return this._currentSortCriterion.getName();
    }
    return this._currentSortCriterion.getName() + ",REVERSE";
  }
  
  public void setClearSortCriteria(String paramString)
  {
    if (this._criteria == null) {
      return;
    }
    this._criteria.getSortCriteria().clear();
    this._currentSortCriterion = null;
  }
  
  public void setCompareSortCriterion(String paramString)
  {
    this.dI = paramString;
  }
  
  public String getCompareSortCriterion()
  {
    return this.dI;
  }
  
  public void setNoSortImage(String paramString)
  {
    this.dN = paramString;
  }
  
  public String getNoSortImage()
  {
    return this.dN;
  }
  
  public void setAscendingSortImage(String paramString)
  {
    this.dL = paramString;
  }
  
  public String getAscendingSortImage()
  {
    return this.dL;
  }
  
  public void setDescendingSortImage(String paramString)
  {
    this.dM = paramString;
  }
  
  public String getDescendingSortImage()
  {
    return this.dM;
  }
  
  public String getSortImage()
  {
    String str1 = this.dI.trim();
    int i = 0;
    String str2;
    if (str1.indexOf(",REVERSE") != -1)
    {
      int j = str1.indexOf(",REVERSE");
      str2 = str1.substring(0, j);
      i = 1;
    }
    else
    {
      i = 0;
      str2 = str1;
    }
    if (this._currentSortCriterion.getName().equalsIgnoreCase(str2))
    {
      if (i != 0)
      {
        if (this._currentSortCriterion.getAsc()) {
          return this.dL;
        }
        return this.dM;
      }
      if (this._currentSortCriterion.getAsc()) {
        return this.dM;
      }
      return this.dL;
    }
    return this.dN;
  }
  
  public String getToggleSortedBy()
  {
    if ((this._currentSortCriterion == null) || (this._currentSortCriterion.getName() == null) || (this._currentSortCriterion.getName().length() == 0)) {
      return this.dI;
    }
    String str1 = this.dI.trim();
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
        return this._currentSortCriterion.getName() + ",REVERSE";
      }
      return this._currentSortCriterion.getName();
    }
    return this.dI;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public String getSuccessURL()
  {
    return this.successURL;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public String getTaskErrorURL()
  {
    return this.taskErrorURL;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public String getServiceErrorURL()
  {
    return this.serviceErrorURL;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public String getMaximumTransactionIndex()
  {
    HashMap localHashMap = this._pagingContext.getMap();
    if (localHashMap == null) {
      return null;
    }
    Object localObject = localHashMap.get("UPPER_BOUND_TransactionIndex");
    if (localObject == null) {
      return null;
    }
    return localObject.toString();
  }
  
  public String getMinimumTransactionIndex()
  {
    HashMap localHashMap = this._pagingContext.getMap();
    if (localHashMap == null) {
      return null;
    }
    Object localObject = localHashMap.get("LOWER_BOUND_TransactionIndex");
    if (localObject == null) {
      return null;
    }
    return localObject.toString();
  }
  
  public String getMaximumPageTransactionIndex()
  {
    HashMap localHashMap = this._pagingContext.getMap();
    if (localHashMap == null) {
      return null;
    }
    Object localObject = localHashMap.get("SORT_VALUE_MAX_TransactionIndex");
    if (localObject == null) {
      return null;
    }
    return localObject.toString();
  }
  
  public String getMinimumPageTransactionIndex()
  {
    HashMap localHashMap = this._pagingContext.getMap();
    if (localHashMap == null) {
      return null;
    }
    Object localObject = localHashMap.get("SORT_VALUE_MIN_TransactionIndex");
    if (localObject == null) {
      return null;
    }
    return localObject.toString();
  }
  
  public static long getTaskTimeoutValue(ServletContext paramServletContext)
  {
    long l = 120L;
    try
    {
      String str = (String)paramServletContext.getAttribute("TaskTimeoutSecs");
      if (str != null) {
        l = Long.parseLong(str);
      }
      if (l == 0L) {
        l = 120L;
      }
    }
    catch (Throwable localThrowable) {}
    return l * 1000L;
  }
  
  public int getTotalPages()
  {
    return this._totalPages;
  }
  
  public String getTotalPagesString()
  {
    return this._totalPages + "";
  }
  
  public int getCurrentPage()
  {
    return this._currentPage;
  }
  
  public void setCurrentPage(int paramInt)
  {
    this._currentPage = paramInt;
  }
  
  public PagingContext getPagingContext()
  {
    return this._pagingContext;
  }
  
  protected abstract FilteredList getFirstPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception;
  
  protected abstract FilteredList getNextPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception;
  
  protected abstract FilteredList getPreviousPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception;
  
  protected abstract FilteredList getLastPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception;
  
  protected abstract Object getValueForSortCriterion(ReportSortCriterion paramReportSortCriterion, Object paramObject)
    throws Exception;
  
  protected int validateMaximumDateRange()
  {
    String str = this._criteria.getSearchCriteria().getProperty("Date Range Type");
    if ("Absolute".equals(str))
    {
      DateTime localDateTime1 = null;
      DateTime localDateTime2 = null;
      DateFormat localDateFormat = DateFormatUtil.getFormatter(this.dS);
      if ((this.dP == null) || (this.dP.length() == 0)) {
        return 1022;
      }
      if ((this.dQ == null) || (this.dQ.length() == 0)) {
        return 1021;
      }
      try
      {
        localDateTime1 = new DateTime(localDateFormat.parse(this.dQ), Locale.getDefault());
        localDateTime1 = jdMethod_for(localDateTime1.getTime());
      }
      catch (ParseException localParseException1)
      {
        return 1021;
      }
      try
      {
        localDateTime2 = new DateTime(localDateFormat.parse(this.dP), Locale.getDefault());
        localDateTime2 = jdMethod_int(localDateTime2.getTime());
      }
      catch (ParseException localParseException2)
      {
        return 1022;
      }
      DateTime localDateTime3 = new DateTime(localDateTime2, Locale.getDefault());
      localDateTime3.add(5, -1 * this._maxDaysInDateRange);
      localDateTime3 = jdMethod_for(localDateTime3.getTime());
      if (localDateTime1.before(localDateTime3)) {
        return 148;
      }
      if (localDateTime2.before(localDateTime1)) {
        return 1023;
      }
    }
    return 0;
  }
  
  protected int setFillBlankDateValues()
  {
    DateTime localDateTime1 = null;
    DateTime localDateTime2 = null;
    DateFormat localDateFormat = DateFormatUtil.getFormatter(this.dS);
    if (((this.dP == null) || (this.dP.length() == 0) || (this.dP.equalsIgnoreCase(this.dS))) && ((this.dQ == null) || (this.dQ.length() == 0) || (this.dQ.equalsIgnoreCase(this.dS))))
    {
      localDateTime2 = jdMethod_int(new Date());
      this.dP = localDateFormat.format(localDateTime2.getTime());
      localDateTime1 = jdMethod_for(localDateTime2.getTime());
      localDateTime1.add(5, -this._maxDaysInDateRange);
      this.dQ = localDateFormat.format(localDateTime1.getTime());
    }
    else if (((this.dP == null) || (this.dP.length() == 0) || (this.dP.equalsIgnoreCase(this.dS))) && (this.dQ != null) && (this.dQ.length() != 0) && (!this.dQ.equalsIgnoreCase(this.dS)))
    {
      try
      {
        localDateTime1 = new DateTime(localDateFormat.parse(this.dQ), Locale.getDefault());
        localDateTime1 = jdMethod_for(localDateTime1.getTime());
      }
      catch (ParseException localParseException1)
      {
        return 1021;
      }
      localDateTime2 = jdMethod_int(localDateTime1.getTime());
      localDateTime2.add(5, this._maxDaysInDateRange);
      if (localDateTime2.getTimeInMillis() > System.currentTimeMillis()) {
        localDateTime2 = jdMethod_int(new Date());
      }
      this.dP = localDateFormat.format(localDateTime2.getTime());
    }
    else if ((this.dP != null) && (this.dP.length() != 0) && (!this.dP.equalsIgnoreCase(this.dS)) && ((this.dQ == null) || (this.dQ.length() == 0) || (this.dQ.equalsIgnoreCase(this.dS))))
    {
      try
      {
        localDateTime2 = new DateTime(localDateFormat.parse(this.dP), Locale.getDefault());
        localDateTime2 = jdMethod_int(localDateTime2.getTime());
      }
      catch (ParseException localParseException2)
      {
        return 1022;
      }
      localDateTime1 = jdMethod_for(localDateTime2.getTime());
      localDateTime1.add(5, -this._maxDaysInDateRange);
      this.dQ = localDateFormat.format(localDateTime1.getTime());
    }
    return 0;
  }
  
  public void setEnforceMaxDateRangeCheck(String paramString)
  {
    this._enforceMaxDateRangeCheck = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setEnforceNoFutureDatesCheck(String paramString)
  {
    this._enforceNoFutureDatesCheck = Boolean.valueOf(paramString).booleanValue();
  }
  
  private DateTime jdMethod_int(Date paramDate)
  {
    DateTime localDateTime = new DateTime(paramDate, Locale.getDefault());
    localDateTime.set(11, 23);
    localDateTime.set(12, 59);
    localDateTime.set(13, 59);
    localDateTime.set(14, 999);
    return localDateTime;
  }
  
  private DateTime jdMethod_for(Date paramDate)
  {
    DateTime localDateTime = new DateTime(paramDate, Locale.getDefault());
    localDateTime.set(11, 0);
    localDateTime.set(12, 0);
    localDateTime.set(13, 0);
    localDateTime.set(14, 0);
    return localDateTime;
  }
  
  protected int setDatesInReportCriteria(Locale paramLocale)
  {
    int i = 0;
    DateTime localDateTime;
    DateFormat localDateFormat3;
    String str;
    if ((this.dQ != null) && (this.dQ.length() > 0) && (!this.dQ.equalsIgnoreCase(this.dS))) {
      try
      {
        DateFormat localDateFormat1 = DateFormatUtil.getFormatter(this.dS, paramLocale);
        localDateTime = new DateTime(localDateFormat1.parse(this.dQ), paramLocale);
        localDateTime.set(11, 0);
        localDateTime.set(12, 0);
        localDateTime.set(13, 0);
        localDateTime.set(14, 0);
        localDateFormat3 = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
        str = localDateFormat3.format(localDateTime.getTime());
        this._criteria.getSearchCriteria().setProperty("StartDate", str);
      }
      catch (Exception localException1)
      {
        i = 1021;
        return i;
      }
    }
    if ((this.dP != null) && (this.dP.length() > 0) && (!this.dP.equalsIgnoreCase(this.dS))) {
      try
      {
        DateFormat localDateFormat2 = DateFormatUtil.getFormatter(this.dS, paramLocale);
        localDateTime = new DateTime(localDateFormat2.parse(this.dP), paramLocale);
        localDateTime.set(11, 23);
        localDateTime.set(12, 59);
        localDateTime.set(13, 59);
        localDateTime.set(14, 999);
        localDateFormat3 = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
        str = localDateFormat3.format(localDateTime.getTime());
        this._criteria.getSearchCriteria().setProperty("EndDate", str);
      }
      catch (Exception localException2)
      {
        i = 1022;
        return i;
      }
    }
    return i;
  }
  
  protected int validateDates(Locale paramLocale)
  {
    int i = 0;
    DateTime localDateTime1 = null;
    DateTime localDateTime2 = null;
    if ((this.dQ != null) && (this.dQ.length() > 0) && (!this.dQ.equalsIgnoreCase(this.dS))) {
      try
      {
        DateFormat localDateFormat1 = DateFormatUtil.getFormatter(this.dS, paramLocale);
        localDateTime1 = new DateTime(localDateFormat1.parse(this.dQ), paramLocale);
      }
      catch (Exception localException1)
      {
        i = 1021;
        return i;
      }
    }
    if ((this.dP != null) && (this.dP.length() > 0) && (!this.dP.equalsIgnoreCase(this.dS))) {
      try
      {
        DateFormat localDateFormat2 = DateFormatUtil.getFormatter(this.dS, paramLocale);
        localDateTime2 = new DateTime(localDateFormat2.parse(this.dP), paramLocale);
      }
      catch (Exception localException2)
      {
        i = 1022;
        return i;
      }
    }
    if ((localDateTime1 != null) && (localDateTime2 != null) && (localDateTime1.after(localDateTime2))) {
      return 79;
    }
    if (this._enforceNoFutureDatesCheck)
    {
      DateTime localDateTime3 = new DateTime();
      if ((localDateTime1 != null) && (localDateTime1.after(localDateTime3))) {
        return 149;
      }
      if ((localDateTime2 != null) && (localDateTime2.after(localDateTime3))) {
        return 150;
      }
    }
    return i;
  }
  
  protected int validateAmounts(ReportCriteria paramReportCriteria)
  {
    if (paramReportCriteria == null) {
      return 0;
    }
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    if ((localProperties == null) || (localProperties.isEmpty())) {
      return 0;
    }
    double d1 = 0.0D;
    double d2 = 0.0D;
    int i = 0;
    int j = 0;
    String str = localProperties.getProperty("MinimumAmount");
    if ((str != null) && (str.length() > 0))
    {
      try
      {
        d1 = Double.parseDouble(str);
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        return 1049;
      }
      if (d1 < 0.0D) {
        return 1050;
      }
      if (str.trim().length() > this._maxAmountLength) {
        return 1018;
      }
      i = 1;
    }
    str = localProperties.getProperty("MaximumAmount");
    if ((str != null) && (str.length() > 0))
    {
      try
      {
        d2 = Double.parseDouble(str);
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        return 1049;
      }
      if (d2 < 0.0D) {
        return 1050;
      }
      if (str.trim().length() > this._maxAmountLength) {
        return 1018;
      }
      j = 1;
    }
    if ((i != 0) && (j != 0) && (d1 > d2)) {
      return 1051;
    }
    return 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.GetPagedData
 * JD-Core Version:    0.7.0.1
 */