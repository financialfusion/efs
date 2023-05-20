package com.ffusion.beans.bankreport;

import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.ExtendABean;
import java.util.Locale;
import java.util.StringTokenizer;

public class BankReport
  extends ExtendABean
{
  private int jdField_do;
  private String jdField_if;
  private DateTime a;
  private DateTime jdField_int;
  private int jdField_for;
  
  public int getReportID()
  {
    return this.jdField_do;
  }
  
  public void setReportID(int paramInt)
  {
    this.jdField_do = paramInt;
  }
  
  public String getType()
  {
    return this.jdField_if;
  }
  
  public void setType(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public DateTime getImportTime()
  {
    return this.a;
  }
  
  public void setImportTime(DateTime paramDateTime)
  {
    this.a = paramDateTime;
  }
  
  public DateTime getGeneratedTime()
  {
    return this.jdField_int;
  }
  
  public void setGeneratedTime(DateTime paramDateTime)
  {
    this.jdField_int = paramDateTime;
  }
  
  public int getDirectoryID()
  {
    return this.jdField_for;
  }
  
  public void setDirectoryID(int paramInt)
  {
    this.jdField_for = paramInt;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("reportID=").append(this.jdField_do);
    localStringBuffer.append(" type=").append(this.jdField_if);
    localStringBuffer.append(" importTime=").append(this.a);
    localStringBuffer.append(" generatedTime=").append(this.jdField_int);
    localStringBuffer.append(" directoryID=").append(this.jdField_for);
    return localStringBuffer.toString();
  }
  
  public BankReport() {}
  
  public BankReport(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.a != null) {
      this.a.setLocale(paramLocale);
    }
    if (this.jdField_int != null) {
      this.jdField_int.setLocale(paramLocale);
    }
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
        return this.jdField_do == i;
      }
      if (str1.equalsIgnoreCase("TYPE"))
      {
        if (this.jdField_if == null) {
          return false;
        }
        return this.jdField_if.equalsIgnoreCase(str2);
      }
      if (str1.equalsIgnoreCase("IMPORT_TIME"))
      {
        if (this.a == null) {
          return false;
        }
        return this.a.isFilterable(paramString);
      }
      if (str1.equalsIgnoreCase("GENERATED_TIME"))
      {
        if (this.jdField_int == null) {
          return false;
        }
        return this.jdField_int.isFilterable(paramString);
      }
      if (str1.equalsIgnoreCase("DIRECTORY_ID"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (Exception localException2)
        {
          return false;
        }
        return this.jdField_for == i;
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "TYPE");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    BankReport localBankReport = (BankReport)paramObject;
    int i = 1;
    if (paramString.equalsIgnoreCase("REPORT_ID")) {
      i = this.jdField_do - localBankReport.getReportID();
    } else if (paramString.equalsIgnoreCase("TYPE")) {
      i = this.jdField_if.compareTo(localBankReport.getType());
    } else if (paramString.equalsIgnoreCase("IMPORT_TIME")) {
      i = this.a.compare(localBankReport.getImportTime());
    } else if (paramString.equalsIgnoreCase("GENERATED_TIME")) {
      i = this.jdField_int.compare(localBankReport.getGeneratedTime());
    } else if (paramString.equalsIgnoreCase("DIRECTORY_ID")) {
      i = this.jdField_for - localBankReport.getDirectoryID();
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof BankReport))
    {
      BankReport localBankReport = (BankReport)paramObject;
      if (localBankReport == this) {
        return true;
      }
      if (localBankReport.getReportID() != this.jdField_do) {
        return false;
      }
    }
    else
    {
      return false;
    }
    return true;
  }
  
  public void set(BankReport paramBankReport)
  {
    super.set(paramBankReport);
    setReportID(paramBankReport.getReportID());
    setType(paramBankReport.getType());
    setImportTime(paramBankReport.getImportTime());
    setGeneratedTime(paramBankReport.getGeneratedTime());
    setDirectoryID(paramBankReport.getDirectoryID());
    setLocale(paramBankReport.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
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
        this.jdField_do = i;
      }
      else if (paramString1.equalsIgnoreCase("TYPE"))
      {
        this.jdField_if = paramString2;
      }
      else if (paramString1.equalsIgnoreCase("IMPORT_TIME"))
      {
        if (this.a == null)
        {
          this.a = new DateTime(this.locale);
          this.a.setFormat(this.datetype);
        }
        this.a.fromXMLFormat(paramString2);
      }
      else if (paramString1.equalsIgnoreCase("GENERATED_TIME"))
      {
        if (this.jdField_int == null)
        {
          this.jdField_int = new DateTime(this.locale);
          this.jdField_int.setFormat(this.datetype);
        }
        this.jdField_int.fromXMLFormat(paramString2);
      }
      else if (paramString1.equalsIgnoreCase("DIRECTORY_ID"))
      {
        try
        {
          i = Integer.parseInt(paramString2);
        }
        catch (Exception localException3)
        {
          return false;
        }
        this.jdField_for = i;
      }
      else
      {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException1) {}
    return bool;
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
    XMLHandler.appendBeginTag(localStringBuffer, "BANK_REPORT");
    XMLHandler.appendTag(localStringBuffer, "REPORT_ID", this.jdField_do);
    XMLHandler.appendTag(localStringBuffer, "TYPE", this.jdField_if);
    if (this.a != null) {
      XMLHandler.appendTag(localStringBuffer, "IMPORT_TIME", this.a.toXMLFormat());
    }
    if (this.jdField_int != null) {
      XMLHandler.appendTag(localStringBuffer, "GENERATED_TIME", this.jdField_int.toXMLFormat());
    }
    XMLHandler.appendTag(localStringBuffer, "DIRECTORY_ID", this.jdField_for);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "BANK_REPORT");
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
      BankReport.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bankreport.BankReport
 * JD-Core Version:    0.7.0.1
 */