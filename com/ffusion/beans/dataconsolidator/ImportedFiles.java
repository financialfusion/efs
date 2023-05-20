package com.ffusion.beans.dataconsolidator;

import com.ffusion.beans.DateTime;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

public class ImportedFiles
  extends FilteredList
{
  public static String IMPORTEDFILES = "IMPORTEDFILES";
  protected String datetype = "SHORT";
  
  public ImportedFile create(int paramInt, String paramString1, String paramString2, Calendar paramCalendar)
  {
    ImportedFile localImportedFile = new ImportedFile(paramInt, paramString1, paramString2, paramCalendar);
    super.add(localImportedFile);
    return localImportedFile;
  }
  
  public ImportedFile create(int paramInt, String paramString1, String paramString2, DateTime paramDateTime)
  {
    ImportedFile localImportedFile = new ImportedFile(paramInt, paramString1, paramString2, paramDateTime);
    super.add(localImportedFile);
    return localImportedFile;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ImportedFile localImportedFile = (ImportedFile)localIterator.next();
      localImportedFile.setDateFormat(this.datetype);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ImportedFiles)) {
      return false;
    }
    ImportedFiles localImportedFiles = (ImportedFiles)paramObject;
    if (size() != localImportedFiles.size()) {
      return false;
    }
    if ((this.locale != null) && (localImportedFiles.locale != null))
    {
      if (!this.locale.equals(localImportedFiles.locale)) {
        return false;
      }
    }
    else
    {
      if ((this.locale != null) && (localImportedFiles.locale == null)) {
        return false;
      }
      if ((this.locale == null) && (localImportedFiles.locale != null)) {
        return false;
      }
    }
    return containsAll(localImportedFiles);
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public ImportedFile create()
  {
    ImportedFile localImportedFile = new ImportedFile();
    super.add(localImportedFile);
    return localImportedFile;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, IMPORTEDFILES);
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ImportedFile)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, IMPORTEDFILES);
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
    public a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("IMPORTEDFILE"))
      {
        ImportedFile localImportedFile = ImportedFiles.this.create();
        localImportedFile.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.dataconsolidator.ImportedFiles
 * JD-Core Version:    0.7.0.1
 */