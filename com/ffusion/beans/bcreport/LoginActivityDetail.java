package com.ffusion.beans.bcreport;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;

public class LoginActivityDetail
  extends ExtendABean
{
  public static final String LOGINACTIVITYDETAIL = "LOGINACTIVITYDETAIL";
  public static final String DATE = "DATE";
  public static final String BUSINESS_NAME = "BUSINESS_NAME";
  public static final String USER_NAME = "USER_NAME";
  public static final String SUCCESS = "SUCCESS";
  private DateTime a4q;
  private String a4n;
  private String a4p;
  private boolean a4o;
  String a4m = "SHORT";
  
  public LoginActivityDetail(DateTime paramDateTime, String paramString1, String paramString2, boolean paramBoolean)
  {
    this.a4q = paramDateTime;
    this.a4n = paramString1;
    this.a4p = paramString2;
    this.a4o = paramBoolean;
  }
  
  public void setDate(DateTime paramDateTime)
  {
    this.a4q = paramDateTime;
  }
  
  public void setDate(String paramString)
  {
    try
    {
      if (this.a4q == null) {
        this.a4q = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.a4q.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setDateFormat(String paramString)
  {
    this.a4m = paramString;
  }
  
  public DateTime getDateValue()
  {
    return this.a4q;
  }
  
  public String getDate()
  {
    String str = null;
    if (this.a4q != null)
    {
      this.a4q.setFormat(this.a4m);
      str = this.a4q.toString();
    }
    return str;
  }
  
  public String getDateFormat()
  {
    return this.a4m;
  }
  
  public void setBusinessName(String paramString)
  {
    this.a4n = paramString;
  }
  
  public String getBusinessName()
  {
    return this.a4n;
  }
  
  public void setUserName(String paramString)
  {
    this.a4p = paramString;
  }
  
  public String getUserName()
  {
    return this.a4p;
  }
  
  public void setSuccess(boolean paramBoolean)
  {
    this.a4o = paramBoolean;
  }
  
  public String getSuccess()
  {
    if (this.a4o) {
      return "TRUE";
    }
    return "FALSE";
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
    XMLHandler.appendBeginTag(localStringBuffer, "LOGINACTIVITYDETAIL");
    XMLHandler.appendTag(localStringBuffer, "DATE", getDate());
    XMLHandler.appendTag(localStringBuffer, "BUSINESS_NAME", getBusinessName());
    XMLHandler.appendTag(localStringBuffer, "USER_NAME", getUserName());
    XMLHandler.appendTag(localStringBuffer, "SUCCESS", getSuccess());
    XMLHandler.appendEndTag(localStringBuffer, "LOGINACTIVITYDETAIL");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bcreport.LoginActivityDetail
 * JD-Core Version:    0.7.0.1
 */