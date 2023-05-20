package com.ffusion.beans.bcreport;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.util.Date;

public class CasePerformanceData
  extends ExtendABean
{
  public static final String CASEPERFORMANCEDATA = "CASEPERFORMANCEDATA";
  public static final String CASETYPE = "CASETYPE";
  public static final String COUNT = "COUNT";
  public static final String NEW_CASES_COUNT = "NEW_CASES_COUNT";
  public static final String OPEN_CASES_COUNT = "OPEN_CASES_COUNT";
  public static final String OPENED_CASES_COUNT = "OPENED_CASES_COUNT";
  public static final String TIME_TO_OPEN_AVERAGE = "TIME_TO_OPEN_AVERAGE";
  public static final String TIME_TO_OPEN_MIN = "TIME_TO_OPEN_MIN";
  public static final String TIME_TO_OPEN_MAX = "TIME_TO_OPEN_MAX";
  public static final String TIME_TO_OPEN_STDDEV = "TIME_TO_OPEN_STDDEV";
  public static final String SINGLE_AGENT_IN_PROGRESS = "SINGLE_AGENT_IN_PROGRESS";
  public static final String SINGLE_AGENT_CLOSED = "SINGLE_AGENT_CLOSED";
  public static final String SINGLE_AGENT_IN_PROGRESS_PERCENT = "SINGLE_AGENT_IN_PROGRESS_PERCENT";
  public static final String SINGLE_AGENT_CLOSED_PERCENT = "SINGLE_AGENT_CLOSED_PERCENT";
  public static final String HELP_REQ_IN_PROGRESS = "HELP_REQ_IN_PROGRESS";
  public static final String HELP_REQ_IN_PROGRESS_PERCENT = "HELP_REQ_IN_PROGRESS_PERCENT";
  public static final String HELP_REQ_CLOSED = "HELP_REQ_CLOSED";
  public static final String HELP_REQ_CLOSED_PERCENT = "HELP_REQ_CLOSED_PERCENT";
  public static final String HELP_PROVIDED_CLOSED = "HELP_PROV_CLOSED";
  public static final String HELP_PROVIDED = "HELP_PROV";
  public static final String HELP_PROVIDED_CLOSED_PERCENT = "HELP_PROV_CLOSED_PERCENT";
  public static final String START_DATE = "START_DATE";
  public static final String END_DATE = "END_DATE";
  private int a3Z = 86400000;
  private int a35 = 3600000;
  private int a39 = 60000;
  private int a3S = 1000;
  private String a3O = null;
  private Date a4a;
  private Date a38;
  private int a37 = 0;
  private int a3R = 0;
  private int a30 = 0;
  private int a3W = 0;
  private int a34 = 0;
  private int a3Y = 0;
  private int a4b = 0;
  private int a4d = 0;
  private int a36 = 0;
  private float a3U = 0.0F;
  private int a3T = 0;
  private float a3X = 0.0F;
  private int a3Q = 0;
  private float a4c = 0.0F;
  private int a32 = 0;
  private float a3P = 0.0F;
  private int a31 = 0;
  private int a33 = 0;
  private float a3V = 0.0F;
  
  public void setCaseType(String paramString)
  {
    this.a3O = paramString;
  }
  
  public String getCaseType()
  {
    return this.a3O;
  }
  
  public void setCount(int paramInt)
  {
    setOpenCasesCount(paramInt);
  }
  
  public int getCount()
  {
    return getOpenCasesCount();
  }
  
  public int getNewCasesCount()
  {
    return this.a3R;
  }
  
  public void setNewCasesCount(int paramInt)
  {
    if (paramInt < 0) {
      this.a3R = 0;
    } else {
      this.a3R = paramInt;
    }
  }
  
  public int getOpenCasesCount()
  {
    return this.a30;
  }
  
  public void setOpenCasesCount(int paramInt)
  {
    if (paramInt < 0) {
      this.a30 = 0;
    } else {
      this.a30 = paramInt;
    }
  }
  
  public int getOpenedCasesCount()
  {
    return this.a3W;
  }
  
  public void setOpenedCasesCount(int paramInt)
  {
    if (paramInt < 0) {
      this.a3W = 0;
    } else {
      this.a3W = paramInt;
    }
  }
  
  public void setStartDate(Date paramDate)
  {
    this.a4a = paramDate;
  }
  
  public void setStartDate(long paramLong)
  {
    this.a4a = new Date(paramLong);
  }
  
  public Date getStartDate()
  {
    return this.a4a;
  }
  
  public String getStartDateValue()
  {
    if (this.a4a == null) {
      return null;
    }
    String str = ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", this.locale);
    DateTime localDateTime = new DateTime(this.a4a, this.locale, str);
    return localDateTime.toString();
  }
  
  public void setEndDate(Date paramDate)
  {
    this.a38 = paramDate;
  }
  
  public void setEndDate(long paramLong)
  {
    this.a38 = new Date(paramLong);
  }
  
  public Date getEndDate()
  {
    return this.a38;
  }
  
  public String getEndDateValue()
  {
    if (this.a38 == null) {
      return null;
    }
    String str = ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", this.locale);
    DateTime localDateTime = new DateTime(this.a38, this.locale, str);
    return localDateTime.toString();
  }
  
  public void setTimeToOpenAvg(int paramInt)
  {
    this.a34 = paramInt;
  }
  
  public int getTimeToOpenAvg()
  {
    return this.a34;
  }
  
  public String getTimeToOpenAvgString()
  {
    return r(this.a34);
  }
  
  public void setTimeToOpenMin(int paramInt)
  {
    this.a3Y = paramInt;
  }
  
  public void setTimeToOpenMax(int paramInt)
  {
    this.a4b = paramInt;
  }
  
  public int getTimeToOpenMax()
  {
    return this.a4b;
  }
  
  public String getTimeToOpenMaxString()
  {
    return r(this.a4b);
  }
  
  public int getTimeToOpenMin()
  {
    return this.a3Y;
  }
  
  public String getTimeToOpenMinString()
  {
    return r(this.a3Y);
  }
  
  public void setTimeToOpenStdDev(int paramInt)
  {
    this.a4d = paramInt;
  }
  
  public int getTimeToOpenStdDev()
  {
    return this.a4d;
  }
  
  public String getTimeToOpenStdDevString()
  {
    return r(this.a4d);
  }
  
  public void setResInProgSingleCount(int paramInt)
  {
    this.a36 = paramInt;
  }
  
  public int getResInProgSingleCount()
  {
    return this.a36;
  }
  
  public void setResInProgSinglePct(float paramFloat)
  {
    this.a3U = paramFloat;
  }
  
  public float getResInProgSinglePct()
  {
    return this.a3U;
  }
  
  public void setResClosedSingleCount(int paramInt)
  {
    this.a3T = paramInt;
  }
  
  public int getResClosedSingleCount()
  {
    return this.a3T;
  }
  
  public void setResClosedSinglePct(float paramFloat)
  {
    this.a3X = paramFloat;
  }
  
  public float getResClosedSinglePct()
  {
    return this.a3X;
  }
  
  public void setResInProgHelpRqCount(int paramInt)
  {
    this.a3Q = paramInt;
  }
  
  public int getResInProgHelpRqCount()
  {
    return this.a3Q;
  }
  
  public void setResInProgHelpRqPct(float paramFloat)
  {
    this.a4c = paramFloat;
  }
  
  public float getResInProgHelpRqPct()
  {
    return this.a4c;
  }
  
  public void setResClosedHelpRqCount(int paramInt)
  {
    this.a32 = paramInt;
  }
  
  public int getResClosedHelpRqCount()
  {
    return this.a32;
  }
  
  public void setResClosedHelpRqPct(float paramFloat)
  {
    this.a3P = paramFloat;
  }
  
  public float getResClosedHelpRqPct()
  {
    return this.a3P;
  }
  
  public void setResHelpedAndClosedCount(int paramInt)
  {
    this.a31 = paramInt;
  }
  
  public int getResHelpedAndClosedCount()
  {
    return this.a31;
  }
  
  public void setResHelpedCount(int paramInt)
  {
    this.a33 = paramInt;
  }
  
  public int getResHelpedCount()
  {
    return this.a33;
  }
  
  public void setResHelpedAndClosedPct(float paramFloat)
  {
    this.a3V = paramFloat;
  }
  
  public float getResHelpedAndClosedPct()
  {
    return this.a3V;
  }
  
  private String s(int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    int j = 0;
    if (paramInt == 0) {
      return new String("0");
    }
    if (paramInt == -1) {
      return new String("-1");
    }
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    while (paramInt > this.a3Z)
    {
      paramInt -= this.a3Z;
      k++;
    }
    while (paramInt > this.a35)
    {
      paramInt -= this.a35;
      m++;
    }
    while (paramInt > this.a39)
    {
      paramInt -= this.a39;
      n++;
    }
    while (paramInt > this.a3S)
    {
      paramInt -= this.a3S;
      i1++;
    }
    if (k != 0)
    {
      i = 1;
      j = 1;
      localStringBuffer.append(k);
      localStringBuffer.append("D ");
    }
    if (m != 0)
    {
      i = 1;
      localStringBuffer.append(m);
      localStringBuffer.append("H ");
    }
    if ((n != 0) && (j == 0))
    {
      i = 1;
      localStringBuffer.append(n);
      localStringBuffer.append("min ");
    }
    if (i == 0)
    {
      localStringBuffer.append(i1);
      localStringBuffer.append("sec ");
    }
    return localStringBuffer.toString();
  }
  
  private String r(int paramInt)
  {
    int i = paramInt / 60;
    int j = i / 60;
    String str = "" + j + ":" + i % 60;
    return str;
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new ExtendABean.InternalXMLHandler(this), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "CASEPERFORMANCEDATA");
    XMLHandler.appendTag(localStringBuffer, "CASETYPE", this.a3O);
    XMLHandler.appendTag(localStringBuffer, "NEW_CASES_COUNT", String.valueOf(this.a3R));
    XMLHandler.appendTag(localStringBuffer, "OPEN_CASES_COUNT", String.valueOf(this.a30));
    XMLHandler.appendTag(localStringBuffer, "OPENED_CASES_COUNT", String.valueOf(this.a3W));
    XMLHandler.appendTag(localStringBuffer, "START_DATE", getStartDateValue());
    XMLHandler.appendTag(localStringBuffer, "END_DATE", getEndDateValue());
    XMLHandler.appendTag(localStringBuffer, "TIME_TO_OPEN_AVERAGE", String.valueOf(this.a34));
    XMLHandler.appendTag(localStringBuffer, "TIME_TO_OPEN_MIN", String.valueOf(this.a3Y));
    XMLHandler.appendTag(localStringBuffer, "TIME_TO_OPEN_MAX", String.valueOf(this.a4b));
    XMLHandler.appendTag(localStringBuffer, "TIME_TO_OPEN_STDDEV", String.valueOf(this.a4d));
    XMLHandler.appendTag(localStringBuffer, "SINGLE_AGENT_IN_PROGRESS", String.valueOf(this.a36));
    XMLHandler.appendTag(localStringBuffer, "SINGLE_AGENT_IN_PROGRESS_PERCENT", String.valueOf(this.a3U));
    XMLHandler.appendTag(localStringBuffer, "SINGLE_AGENT_CLOSED", String.valueOf(this.a3T));
    XMLHandler.appendTag(localStringBuffer, "SINGLE_AGENT_CLOSED_PERCENT", String.valueOf(this.a3X));
    XMLHandler.appendTag(localStringBuffer, "HELP_REQ_IN_PROGRESS", String.valueOf(this.a3Q));
    XMLHandler.appendTag(localStringBuffer, "HELP_REQ_IN_PROGRESS_PERCENT", String.valueOf(this.a4c));
    XMLHandler.appendTag(localStringBuffer, "HELP_REQ_CLOSED", String.valueOf(this.a32));
    XMLHandler.appendTag(localStringBuffer, "HELP_REQ_CLOSED_PERCENT", String.valueOf(this.a3P));
    XMLHandler.appendTag(localStringBuffer, "HELP_PROV_CLOSED", String.valueOf(this.a31));
    XMLHandler.appendTag(localStringBuffer, "HELP_PROV", String.valueOf(this.a33));
    XMLHandler.appendTag(localStringBuffer, "HELP_PROV_CLOSED_PERCENT", String.valueOf(this.a3V));
    XMLHandler.appendEndTag(localStringBuffer, "CASEPERFORMANCEDATA");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bcreport.CasePerformanceData
 * JD-Core Version:    0.7.0.1
 */