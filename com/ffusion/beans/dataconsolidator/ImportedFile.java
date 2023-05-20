package com.ffusion.beans.dataconsolidator;

import com.ffusion.beans.DateTime;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.text.Collator;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;

public class ImportedFile
  extends com.ffusion.beans.ExtendABean
  implements Comparable
{
  public static final String IMPORTEDFILE = "IMPORTEDFILE";
  public static final String FILEID = "FILEID";
  public static final String FILENAME = "FILENAME";
  public static final String DATASOURCE = "DATASOURCE";
  public static final String IMPORTTIME = "IMPORTTIME";
  public static final String IMPORTTIMEVALUE = "IMPORTTIMEVALUE";
  private String aYc = null;
  private String aYe = null;
  private DateTime aYb = null;
  private int aYd;
  private String aYf = null;
  
  public ImportedFile()
  {
    this.aYd = -1;
  }
  
  public ImportedFile(int paramInt, String paramString1, String paramString2, Calendar paramCalendar)
  {
    this.aYd = paramInt;
    this.aYc = paramString1;
    this.aYe = paramString2;
    this.aYb = new DateTime(paramCalendar, this.locale);
    this.aYf = null;
  }
  
  public ImportedFile(int paramInt, String paramString1, String paramString2, DateTime paramDateTime)
  {
    this.aYd = paramInt;
    this.aYc = paramString1;
    this.aYe = paramString2;
    this.aYb = paramDateTime;
    this.aYf = null;
  }
  
  public ImportedFile(int paramInt, String paramString1, String paramString2, DateTime paramDateTime, String paramString3)
  {
    this.aYd = paramInt;
    this.aYc = paramString1;
    this.aYe = paramString2;
    this.aYb = paramDateTime;
    this.aYf = paramString3;
  }
  
  public void setDataSource(int paramInt)
  {
    this.aYd = paramInt;
  }
  
  public void setFileID(String paramString)
  {
    this.aYc = paramString;
  }
  
  public void setFileName(String paramString)
  {
    this.aYe = paramString;
  }
  
  public void setImportTimeValue(DateTime paramDateTime)
  {
    this.aYb = paramDateTime;
  }
  
  public void setDataClassification(String paramString)
  {
    this.aYf = paramString;
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.aYb != null) {
      this.aYb.setFormat(paramString);
    }
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.aYb != null) {
      this.aYb.setLocale(paramLocale);
    }
  }
  
  public int getDataSource()
  {
    return this.aYd;
  }
  
  public String getFileID()
  {
    return this.aYc;
  }
  
  public String getFileName()
  {
    return this.aYe;
  }
  
  public DateTime getImportTimeValue()
  {
    return this.aYb;
  }
  
  public String getImportTime()
  {
    if (this.aYb != null) {
      return this.aYb.toString();
    }
    return null;
  }
  
  public String getDataClassification()
  {
    return this.aYf;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof ImportedFile))) {
      return false;
    }
    ImportedFile localImportedFile = (ImportedFile)paramObject;
    return (this.aYd == localImportedFile.getDataSource()) && (((this.aYc == null) && (localImportedFile.getFileID() == null)) || ((this.aYc.equals(localImportedFile.getFileID())) && (((this.aYe == null) && (localImportedFile.getFileName() == null)) || ((this.aYe.equals(localImportedFile.getFileName())) && (((this.aYb == null) && (localImportedFile.getImportTimeValue() == null)) || (this.aYb.equals(localImportedFile.getImportTimeValue())))))));
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() == 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      if (str1.equalsIgnoreCase("FILENAME")) {
        return this.aYe.equals(str2);
      }
      if (str1.equalsIgnoreCase("FILEID")) {
        return this.aYc.equals(str2);
      }
      if (str1.equalsIgnoreCase("DATASOURCE"))
      {
        if (this.aYd == Integer.parseInt(str2)) {
          return true;
        }
      }
      else if (str1.equalsIgnoreCase("IMPORTTIME")) {
        return getImportTime().equals(str2);
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "FILENAME");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ImportedFile localImportedFile = (ImportedFile)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if (paramString.equals("FILENAME")) {
      i = com.ffusion.util.beans.ExtendABean.compareStrings(this.aYe, localImportedFile.getFileName(), localCollator);
    } else if ((paramString.equals("FILEID")) && (this.aYc != null)) {
      i = com.ffusion.util.beans.ExtendABean.compareStrings(this.aYc, localImportedFile.getFileID(), localCollator);
    } else if ((paramString.equals("IMPORTTIME")) && (this.aYb != null)) {
      i = com.ffusion.util.beans.ExtendABean.compareStrings(getImportTime(), localImportedFile.getImportTime(), localCollator);
    } else if (paramString.equals("DATASOURCE"))
    {
      if (this.aYd == localImportedFile.getDataSource()) {
        i = 0;
      }
    }
    else if (paramString.equals("IMPORTTIMEVALUE")) {
      i = getImportTimeValue().compare(localImportedFile.getImportTimeValue());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public void set(ImportedFile paramImportedFile)
  {
    super.set(paramImportedFile);
    setLocale(paramImportedFile.locale);
    setFileID(paramImportedFile.getFileID());
    setFileName(paramImportedFile.getFileName());
    setImportTimeValue(paramImportedFile.getImportTimeValue());
    setDataSource(paramImportedFile.getDataSource());
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("FILENAME")) {
        this.aYe = paramString2;
      } else if (paramString1.equals("FILEID")) {
        this.aYc = paramString2;
      } else if (paramString1.equals("DATASOURCE")) {
        this.aYd = Integer.parseInt(paramString2);
      } else {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception", localException);
    }
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
    XMLHandler.appendBeginTag(localStringBuffer, "IMPORTEDFILE");
    XMLHandler.appendTag(localStringBuffer, "FILEID", this.aYc);
    XMLHandler.appendTag(localStringBuffer, "FILENAME", this.aYe);
    XMLHandler.appendTag(localStringBuffer, "DATASOURCE", this.aYd);
    XMLHandler.appendTag(localStringBuffer, "IMPORTTIME", this.aYb.toString());
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "IMPORTEDFILE");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  public boolean areObjectsEqual(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == paramObject2) {
      return true;
    }
    if ((paramObject1 == null) || (paramObject2 == null)) {
      return false;
    }
    return paramObject1.equals(paramObject2);
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.dataconsolidator.ImportedFile
 * JD-Core Version:    0.7.0.1
 */