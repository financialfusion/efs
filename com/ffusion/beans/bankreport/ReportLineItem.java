package com.ffusion.beans.bankreport;

import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean;
import java.util.Locale;
import java.util.StringTokenizer;

public class ReportLineItem
  extends ExtendABean
{
  private int a;
  private int jdField_new;
  private String jdField_do;
  private String jdField_if;
  private String jdField_int;
  private boolean jdField_for;
  
  public int getReportID()
  {
    return this.a;
  }
  
  public void setReportID(int paramInt)
  {
    this.a = paramInt;
  }
  
  public int getLineNumber()
  {
    return this.jdField_new;
  }
  
  public void setLineNumber(int paramInt)
  {
    this.jdField_new = paramInt;
  }
  
  public String getData()
  {
    return this.jdField_do;
  }
  
  public void setData(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getRoutingNum()
  {
    return this.jdField_if;
  }
  
  public void setRoutingNum(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getAccountID()
  {
    return this.jdField_int;
  }
  
  public void setAccountID(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public boolean getForcePageBreak()
  {
    return this.jdField_for;
  }
  
  public void setForcePageBreak(boolean paramBoolean)
  {
    this.jdField_for = paramBoolean;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("reportID=").append(this.a);
    localStringBuffer.append(" lineNumber=").append(this.jdField_new);
    localStringBuffer.append(" data=").append(this.jdField_do);
    localStringBuffer.append(" routingNum=").append(this.jdField_if);
    localStringBuffer.append(" accountID=").append(this.jdField_int);
    localStringBuffer.append(" forcePageBreak=").append(this.jdField_for);
    return localStringBuffer.toString();
  }
  
  public ReportLineItem() {}
  
  public ReportLineItem(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() == 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      int i;
      if (str1.equalsIgnoreCase("REPORT_ID"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (Exception localException1)
        {
          return false;
        }
        return this.a == i;
      }
      if (str1.equalsIgnoreCase("LINE_NUMBER"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (Exception localException2)
        {
          return false;
        }
        return this.jdField_new == i;
      }
      if (str1.equalsIgnoreCase("DATA"))
      {
        if (this.jdField_do == null) {
          return false;
        }
        return this.jdField_do.equalsIgnoreCase(str2);
      }
      if (str1.equalsIgnoreCase("ROUTING_NUM"))
      {
        if (this.jdField_if == null) {
          return false;
        }
        return this.jdField_if.equalsIgnoreCase(str2);
      }
      if (str1.equalsIgnoreCase("ACCOUNT_ID"))
      {
        if (this.jdField_int == null) {
          return false;
        }
        return this.jdField_int.equalsIgnoreCase(str2);
      }
      if (str1.equalsIgnoreCase("FORCE_PAGE_BREAK"))
      {
        boolean bool;
        try
        {
          bool = Boolean.getBoolean(str2);
        }
        catch (Exception localException3)
        {
          return false;
        }
        return this.jdField_for == bool;
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "LINE_NUMBER");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ReportLineItem localReportLineItem = (ReportLineItem)paramObject;
    int i = 1;
    if (paramString.equalsIgnoreCase("REPORT_ID")) {
      i = this.a - localReportLineItem.getReportID();
    } else if (paramString.equalsIgnoreCase("LINE_NUMBER")) {
      i = this.jdField_new - localReportLineItem.getLineNumber();
    } else if (paramString.equalsIgnoreCase("DATA")) {
      i = this.jdField_do.compareTo(localReportLineItem.getData());
    } else if (paramString.equalsIgnoreCase("ROUTING_NUM")) {
      i = this.jdField_if.compareTo(localReportLineItem.getRoutingNum());
    } else if (paramString.equalsIgnoreCase("ACCOUNT_ID")) {
      i = this.jdField_int.compareTo(localReportLineItem.getAccountID());
    } else if (paramString.equalsIgnoreCase("FORCE_PAGE_BREAK")) {
      i = (this.jdField_for ? 1 : 0) - (localReportLineItem.getForcePageBreak() ? 1 : 0);
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof ReportLineItem))
    {
      ReportLineItem localReportLineItem = (ReportLineItem)paramObject;
      if (localReportLineItem == this) {
        return true;
      }
      if ((localReportLineItem.getReportID() != this.a) || (localReportLineItem.getLineNumber() != this.jdField_new)) {
        return false;
      }
    }
    else
    {
      return false;
    }
    return true;
  }
  
  public void set(ReportLineItem paramReportLineItem)
  {
    super.set(paramReportLineItem);
    setReportID(paramReportLineItem.getReportID());
    setLineNumber(paramReportLineItem.getLineNumber());
    setData(paramReportLineItem.getData());
    setRoutingNum(paramReportLineItem.getRoutingNum());
    setAccountID(paramReportLineItem.getAccountID());
    setForcePageBreak(paramReportLineItem.getForcePageBreak());
    setLocale(paramReportLineItem.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool1 = true;
    try
    {
      int i;
      if (paramString1.equalsIgnoreCase("REPORT_ID"))
      {
        try
        {
          i = Integer.parseInt(paramString2);
        }
        catch (Exception localException2)
        {
          return false;
        }
        this.a = i;
      }
      else if (paramString1.equalsIgnoreCase("LINE_NUMBER"))
      {
        try
        {
          i = Integer.parseInt(paramString2);
        }
        catch (Exception localException3)
        {
          return false;
        }
        this.jdField_new = i;
      }
      else if (paramString1.equalsIgnoreCase("DATA"))
      {
        this.jdField_do = paramString2;
      }
      else if (paramString1.equalsIgnoreCase("ROUTING_NUM"))
      {
        this.jdField_if = paramString2;
      }
      else if (paramString1.equalsIgnoreCase("ACCOUNT_ID"))
      {
        this.jdField_int = paramString2;
      }
      else if (paramString1.equalsIgnoreCase("FORCE_PAGE_BREAK"))
      {
        boolean bool2;
        try
        {
          bool2 = Boolean.getBoolean(paramString2);
        }
        catch (Exception localException4)
        {
          return false;
        }
        this.jdField_for = bool2;
      }
      else
      {
        bool1 = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException1) {}
    return bool1;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "REPORT_LINE_ITEM");
    XMLHandler.appendTag(localStringBuffer, "REPORT_ID", this.a);
    XMLHandler.appendTag(localStringBuffer, "LINE_NUMBER", this.jdField_new);
    XMLHandler.appendTag(localStringBuffer, "DATA", this.jdField_do);
    XMLHandler.appendTag(localStringBuffer, "ROUTING_NUM", this.jdField_if);
    XMLHandler.appendTag(localStringBuffer, "ACCOUNT_ID", this.jdField_int);
    XMLHandler.appendTag(localStringBuffer, "FORCE_PAGE_BREAK", String.valueOf(this.jdField_for));
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "REPORT_LINE_ITEM");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      ReportLineItem.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bankreport.ReportLineItem
 * JD-Core Version:    0.7.0.1
 */