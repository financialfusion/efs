package com.ffusion.beans.reporting;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;

public class ReportIdentification
  extends ExtendABean
{
  static final String aV8 = "REPORTIDENTIFICATION";
  public static final String REPORTID = "REPORTID";
  public static final String REPORTNAME = "REPORTNAME";
  public static final String DESCRIPTION = "DESCRIPTION";
  private int aV7;
  private String aV9;
  private String aV6;
  
  protected ReportIdentification() {}
  
  public ReportIdentification(int paramInt, String paramString1, String paramString2)
  {
    this.aV7 = paramInt;
    this.aV9 = paramString1;
    this.aV6 = paramString2;
  }
  
  public void setReportID(int paramInt)
  {
    this.aV7 = paramInt;
  }
  
  public int getReportID()
  {
    return this.aV7;
  }
  
  public void setReportName(String paramString)
  {
    if (paramString == null) {
      this.aV9 = paramString;
    } else {
      this.aV9 = paramString.trim();
    }
  }
  
  public String getReportName()
  {
    return this.aV9;
  }
  
  public void setDescription(String paramString)
  {
    this.aV6 = paramString;
  }
  
  public String getDescription()
  {
    return this.aV6;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ReportIdentification localReportIdentification = (ReportIdentification)paramObject;
    int i = 0;
    if (paramString.equals("REPORTID")) {
      i = this.aV7 - localReportIdentification.getReportID();
    } else if (paramString.equals("REPORTNAME")) {
      i = Strings.compareToIgnoreCase(this.aV9, localReportIdentification.getReportName());
    } else if (paramString.equals("DESCRIPTION")) {
      i = Strings.compareToIgnoreCase(this.aV6, localReportIdentification.getDescription());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "REPORTIDENTIFICATION");
    XMLHandler.appendTag(localStringBuffer, "REPORTID", this.aV7);
    XMLHandler.appendTag(localStringBuffer, "REPORTNAME", this.aV9);
    XMLHandler.appendTag(localStringBuffer, "DESCRIPTION", this.aV6);
    XMLHandler.appendEndTag(localStringBuffer, "REPORTIDENTIFICATION");
    return localStringBuffer.toString();
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
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("REPORTID")) {
      this.aV7 = Integer.parseInt(paramString2);
    } else if (paramString1.equals("REPORTNAME")) {
      this.aV9 = paramString2;
    } else if (paramString1.equals("DESCRIPTION")) {
      this.aV6 = paramString2;
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.reporting.ReportIdentification
 * JD-Core Version:    0.7.0.1
 */