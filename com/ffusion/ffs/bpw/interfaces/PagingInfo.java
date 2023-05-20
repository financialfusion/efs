package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.util.beans.PagingContext;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class PagingInfo
  implements Serializable
{
  protected PagingContext jdField_for = null;
  protected ArrayList jdField_new = null;
  protected HashMap jdField_int = null;
  protected int jdField_do = 0;
  protected String jdField_if = "Success";
  public static final String DATEFORMAT = "yyyyMMdd";
  protected static SimpleDateFormat a = new SimpleDateFormat("yyyyMMdd");
  
  public int getStatusCode()
  {
    return this.jdField_do;
  }
  
  public void setStatusCode(int paramInt)
  {
    this.jdField_do = paramInt;
  }
  
  public String getStatusMsg()
  {
    return this.jdField_if;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public PagingContext getPagingContext()
  {
    return this.jdField_for;
  }
  
  public void setPagingContext(PagingContext paramPagingContext)
  {
    this.jdField_for = paramPagingContext;
  }
  
  public ArrayList getPagingResult()
  {
    return this.jdField_new;
  }
  
  public void setPagingResult(ArrayList paramArrayList)
  {
    this.jdField_new = paramArrayList;
  }
  
  public HashMap getMap()
  {
    return this.jdField_int;
  }
  
  public String getStartDate()
  {
    HashMap localHashMap1 = this.jdField_for.getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    String str = (String)localHashMap2.get("StartDate");
    if (((str == null) || (str.length() == 0)) && (this.jdField_for.getStartDate() != null))
    {
      str = a.format(this.jdField_for.getStartDate().getTime());
      localHashMap2.put("StartDate", str);
    }
    return str;
  }
  
  public void setStartDate(String paramString)
  {
    HashMap localHashMap1 = this.jdField_for.getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    localHashMap2.put("StartDate", paramString);
  }
  
  public String getEndDate()
  {
    HashMap localHashMap1 = this.jdField_for.getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    String str = (String)localHashMap2.get("EndDate");
    if (((str == null) || (str.length() == 0)) && (this.jdField_for.getEndDate() != null))
    {
      str = a.format(this.jdField_for.getEndDate().getTime());
      localHashMap2.put("EndDate", str);
    }
    return str;
  }
  
  public void setEndDate(String paramString)
  {
    HashMap localHashMap1 = this.jdField_for.getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    localHashMap2.put("EndDate", paramString);
  }
  
  public int getTotalTrans()
  {
    HashMap localHashMap1 = this.jdField_for.getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    Integer localInteger = (Integer)localHashMap2.get("TotalTrans");
    int i;
    if (localInteger == null) {
      i = 0;
    } else {
      i = localInteger.intValue();
    }
    return i;
  }
  
  public void setTotalTrans(int paramInt)
  {
    HashMap localHashMap1 = this.jdField_for.getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    Integer localInteger = new Integer(paramInt);
    localHashMap2.put("TotalTrans", localInteger);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.PagingInfo
 * JD-Core Version:    0.7.0.1
 */