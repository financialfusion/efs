package com.ffusion.beans.reporting;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.util.Locale;

public class ReportHeading
  extends ExtendABean
{
  static final String aVR = "REPORT_HEADING";
  public static final String LABEL = "LABEL";
  public static final String JUSTIFICATION = "JUSTIFICATION";
  private String aVT;
  private String aVQ = "LEFT";
  private boolean aVS = false;
  
  public ReportHeading(Locale paramLocale)
  {
    this.locale = paramLocale;
  }
  
  public String getLabel()
  {
    return this.aVT;
  }
  
  public void setLabel(String paramString)
  {
    this.aVT = paramString;
  }
  
  public String getJustification()
  {
    return this.aVQ;
  }
  
  public void setJustification(String paramString)
  {
    this.aVQ = paramString;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("label=").append(this.aVT);
    localStringBuffer.append("justification=").append(this.aVQ);
    return localStringBuffer.toString();
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    int i = compare(paramObject, "LABEL");
    if (i == 0) {
      i = compare(paramObject, "JUSTIFICATION");
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ReportHeading localReportHeading = (ReportHeading)paramObject;
    int i = 1;
    if (paramString.equals("LABEL")) {
      i = this.aVT.compareToIgnoreCase(localReportHeading.getLabel());
    } else if (paramString.equals("JUSTIFICATION")) {
      i = this.aVT.compareToIgnoreCase(localReportHeading.getJustification());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "REPORT_HEADING");
    XMLHandler.appendTag(localStringBuffer, "LABEL", this.aVT);
    XMLHandler.appendTag(localStringBuffer, "JUSTIFICATION", this.aVQ);
    XMLHandler.appendEndTag(localStringBuffer, "REPORT_HEADING");
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
    if (paramString1.equals("LABEL")) {
      this.aVT = paramString2;
    } else if (paramString1.equals("JUSTIFICATION")) {
      this.aVQ = paramString2;
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  void jdMethod_case(boolean paramBoolean)
  {
    this.aVS = paramBoolean;
  }
  
  public boolean isSectionHeading()
  {
    return this.aVS;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.reporting.ReportHeading
 * JD-Core Version:    0.7.0.1
 */