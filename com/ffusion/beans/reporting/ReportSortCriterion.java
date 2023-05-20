package com.ffusion.beans.reporting;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;

public class ReportSortCriterion
  extends ExtendABean
{
  static final String aWz = "SORT_CRITERION";
  public static final String ORDINAL = "ORDINAL";
  public static final String CRITERIONNAME = "CRITERIONNAME";
  public static final String ASC = "ASC";
  private int aWB;
  private String aWy = "";
  private boolean aWA;
  
  protected ReportSortCriterion()
  {
    this.aWA = true;
    this.aWy = "";
  }
  
  public ReportSortCriterion(int paramInt, String paramString, boolean paramBoolean)
  {
    this.aWB = paramInt;
    if (paramString != null) {
      this.aWy = paramString;
    }
    this.aWA = paramBoolean;
  }
  
  public void setOrdinal(int paramInt)
  {
    this.aWB = paramInt;
  }
  
  public int getOrdinal()
  {
    return this.aWB;
  }
  
  public void setName(String paramString)
  {
    if (paramString != null) {
      this.aWy = paramString;
    } else {
      this.aWy = "";
    }
  }
  
  public String getName()
  {
    return this.aWy;
  }
  
  public void setAsc(boolean paramBoolean)
  {
    this.aWA = paramBoolean;
  }
  
  public boolean getAsc()
  {
    return this.aWA;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)paramObject;
    int i = 1;
    if (paramString.equals("CRITERIONNAME")) {
      i = this.aWy.compareToIgnoreCase(localReportSortCriterion.getName());
    } else if (paramString.equals("ORDINAL")) {
      i = this.aWB - localReportSortCriterion.getOrdinal();
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "SORT_CRITERION");
    XMLHandler.appendTag(localStringBuffer, "ORDINAL", this.aWB);
    XMLHandler.appendTag(localStringBuffer, "CRITERIONNAME", this.aWy);
    XMLHandler.appendTag(localStringBuffer, "ASC", new Boolean(this.aWA).toString());
    XMLHandler.appendEndTag(localStringBuffer, "SORT_CRITERION");
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
    if (paramString1.equals("ORDINAL")) {
      this.aWB = Integer.parseInt(paramString2);
    } else if (paramString1.equals("CRITERIONNAME")) {
      this.aWy = paramString2;
    } else if (paramString1.equals("ASC")) {
      this.aWA = new Boolean(paramString2).booleanValue();
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.reporting.ReportSortCriterion
 * JD-Core Version:    0.7.0.1
 */