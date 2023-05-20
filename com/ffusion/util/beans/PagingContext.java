package com.ffusion.util.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class PagingContext
  implements Serializable
{
  public static final String DIRECTION_FIRST = "FIRST";
  public static final String DIRECTION_LAST = "LAST";
  public static final String DIRECTION_NEXT = "NEXT";
  public static final String DIRECTION_PREVIOUS = "PREVIOUS";
  public static final String SORT_VALUE_MIN = "SORT_VALUE_MIN_";
  public static final String SORT_VALUE_MAX = "SORT_VALUE_MAX_";
  public static final String PAGE_SIZE = "PAGE_SIZE";
  public static final String TOTAL_TRANS = "TOTAL_TRANS";
  public static final String LOWER_BOUND = "LOWER_BOUND_";
  public static final String UPPER_BOUND = "UPPER_BOUND_";
  public static final String CURSORID = "TransactionIndex";
  public static final String SEARCH_CRITERIA = "SEARCH_CRITERIA";
  public static final int PAGE_SIZE_REQ_SERVER_MAX = -1;
  public static final int PAGE_SIZE_REQ_ALL_RECORDS = 0;
  public static final String TRANSACTIONS_ALL = "All";
  public static final String TRANSACTIONS_CURRENT = "Current";
  public static final String TRANSACTIONS_FUTURE = "Future";
  public static final String TRANSACTIONS_FILTER_KEY = "TransactionsFilter";
  private Calendar jdField_do = null;
  private Calendar jdField_byte = null;
  private long a = 0L;
  private long jdField_case = 0L;
  private String jdField_goto = "FIRST";
  private String jdField_if = null;
  private boolean jdField_new = false;
  private boolean jdField_else = false;
  private HashMap jdField_long = null;
  private String jdField_char = null;
  private boolean jdField_for = true;
  private ArrayList jdField_try = null;
  private int jdField_int;
  
  public PagingContext(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    this.jdField_do = paramCalendar1;
    this.jdField_byte = paramCalendar2;
    this.jdField_goto = "FIRST";
    this.jdField_int = 0;
  }
  
  public Calendar getStartDate()
  {
    return this.jdField_do;
  }
  
  public void setStartDate(Calendar paramCalendar)
  {
    this.jdField_do = paramCalendar;
  }
  
  public int getPageNumber()
  {
    return this.jdField_int;
  }
  
  public void setPageNumber(int paramInt)
  {
    this.jdField_int = paramInt;
  }
  
  public Calendar getEndDate()
  {
    return this.jdField_byte;
  }
  
  public void setEndDate(Calendar paramCalendar)
  {
    this.jdField_byte = paramCalendar;
  }
  
  public long getFirstIndex()
  {
    return this.a;
  }
  
  public void setFirstIndex(long paramLong)
  {
    this.a = paramLong;
  }
  
  public long getLastIndex()
  {
    return this.jdField_case;
  }
  
  public void setLastIndex(long paramLong)
  {
    this.jdField_case = paramLong;
  }
  
  public String getDirection()
  {
    return this.jdField_goto;
  }
  
  public void setDirection(String paramString)
  {
    this.jdField_goto = paramString;
  }
  
  public String getSortedBy()
  {
    return this.jdField_if;
  }
  
  public void setSortedBy(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public boolean isFirstPage()
  {
    return this.jdField_new;
  }
  
  public void setFirstPage(boolean paramBoolean)
  {
    this.jdField_new = paramBoolean;
  }
  
  public boolean isLastPage()
  {
    return this.jdField_else;
  }
  
  public void setLastPage(boolean paramBoolean)
  {
    this.jdField_else = paramBoolean;
  }
  
  public HashMap getMap()
  {
    return this.jdField_long;
  }
  
  public void setMap(HashMap paramHashMap)
  {
    this.jdField_long = paramHashMap;
  }
  
  public String getSessionId()
  {
    return this.jdField_char;
  }
  
  public void setSessionId(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public boolean isAscending()
  {
    return this.jdField_for;
  }
  
  public void setAscending()
  {
    this.jdField_for = true;
  }
  
  public void setDescending()
  {
    this.jdField_for = false;
  }
  
  public void setSortCriteriaList(ArrayList paramArrayList)
  {
    this.jdField_try = paramArrayList;
  }
  
  public ArrayList getSortCriteriaList()
  {
    return this.jdField_try;
  }
  
  public Object clone()
  {
    Calendar localCalendar1 = null;
    Calendar localCalendar2 = null;
    if (this.jdField_do != null) {
      localCalendar1 = (Calendar)this.jdField_do.clone();
    }
    if (this.jdField_byte != null) {
      localCalendar2 = (Calendar)this.jdField_byte.clone();
    }
    PagingContext localPagingContext = new PagingContext(localCalendar1, localCalendar2);
    if (this.jdField_long != null) {
      localPagingContext.jdField_long = ((HashMap)this.jdField_long.clone());
    } else {
      localPagingContext.jdField_long = null;
    }
    localPagingContext.a = this.a;
    localPagingContext.jdField_case = this.jdField_case;
    localPagingContext.jdField_goto = this.jdField_goto;
    localPagingContext.jdField_if = this.jdField_if;
    localPagingContext.jdField_new = this.jdField_new;
    localPagingContext.jdField_else = this.jdField_else;
    localPagingContext.jdField_char = this.jdField_char;
    localPagingContext.jdField_for = this.jdField_for;
    localPagingContext.jdField_int = this.jdField_int;
    if (this.jdField_try != null) {
      localPagingContext.jdField_try = ((ArrayList)this.jdField_try.clone());
    } else {
      localPagingContext.jdField_try = null;
    }
    return localPagingContext;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.PagingContext
 * JD-Core Version:    0.7.0.1
 */