package com.ffusion.beans.bcreport;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;

public class LoginActivitySummary
  extends ExtendABean
{
  public static final String LOGINACTIVITYSUMMARY = "LOGINACTIVITYSUMMARY";
  public static final String START_DATE = "START_DATE";
  public static final String END_DATE = "END_DATE";
  public static final String NUM_LOGINS = "NUM_LOGINS";
  public static final String NUM_SUCCESSFUL = "NUM_SUCCESSFUL";
  public static final String NUM_FAILED = "NUM_FAILED";
  public static final String PERCENT_SUCCESSFUL = "PERCENT_SUCCESSFUL";
  public static final String PERCENT_FAILED = "PERCENT_FAILED";
  private DateTime a4f;
  private DateTime a4h;
  private int a4l;
  private int a4j;
  private int a4g;
  private float a4i;
  private float a4k;
  String a4e = "SHORT";
  
  public LoginActivitySummary(DateTime paramDateTime1, DateTime paramDateTime2, int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2)
  {
    this.a4f = paramDateTime1;
    this.a4h = paramDateTime2;
    this.a4l = paramInt1;
    this.a4j = paramInt2;
    this.a4g = paramInt3;
    this.a4i = paramFloat1;
    this.a4k = paramFloat2;
  }
  
  public void setStartDate(DateTime paramDateTime)
  {
    this.a4f = paramDateTime;
  }
  
  public DateTime getStartDateValue()
  {
    return this.a4f;
  }
  
  public String getStartDate()
  {
    String str = "";
    if (this.a4f != null) {
      str = this.a4f.toString();
    }
    return str;
  }
  
  public void setEndDate(DateTime paramDateTime)
  {
    this.a4h = paramDateTime;
  }
  
  public void setDateFormat(String paramString)
  {
    this.a4e = paramString;
  }
  
  public DateTime getEndDateValue()
  {
    return this.a4h;
  }
  
  public String getEndDate()
  {
    String str = "";
    if (this.a4h != null) {
      str = this.a4h.toString();
    }
    return str;
  }
  
  public String getDateFormat()
  {
    return this.a4e;
  }
  
  public void setNumLogins(int paramInt)
  {
    this.a4l = paramInt;
  }
  
  public int getNumLogins()
  {
    return this.a4l;
  }
  
  public void setNumSuccessful(int paramInt)
  {
    this.a4j = paramInt;
  }
  
  public int getNumSuccessful()
  {
    return this.a4j;
  }
  
  public void setNumFailed(int paramInt)
  {
    this.a4g = paramInt;
  }
  
  public int getNumFailed()
  {
    return this.a4g;
  }
  
  public void setPercentSuccessful(float paramFloat)
  {
    this.a4i = paramFloat;
  }
  
  public float getPercentSuccessful()
  {
    return this.a4i;
  }
  
  public void setPercentFailed(float paramFloat)
  {
    this.a4k = paramFloat;
  }
  
  public float getPercentFailed()
  {
    return this.a4k;
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
    XMLHandler.appendBeginTag(localStringBuffer, "LOGINACTIVITYSUMMARY");
    XMLHandler.appendTag(localStringBuffer, "START_DATE", getStartDate());
    XMLHandler.appendTag(localStringBuffer, "END_DATE", getEndDate());
    XMLHandler.appendTag(localStringBuffer, "NUM_LOGINS", String.valueOf(this.a4l));
    XMLHandler.appendTag(localStringBuffer, "NUM_SUCCESSFUL", String.valueOf(this.a4j));
    XMLHandler.appendTag(localStringBuffer, "NUM_FAILED", String.valueOf(this.a4g));
    XMLHandler.appendTag(localStringBuffer, "PERCENT_SUCCESSFUL", String.valueOf(this.a4i));
    XMLHandler.appendTag(localStringBuffer, "PERCENT_FAILED", String.valueOf(this.a4k));
    XMLHandler.appendEndTag(localStringBuffer, "LOGINACTIVITYSUMMARY");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bcreport.LoginActivitySummary
 * JD-Core Version:    0.7.0.1
 */