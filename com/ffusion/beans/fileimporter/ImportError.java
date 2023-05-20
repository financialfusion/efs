package com.ffusion.beans.fileimporter;

import com.ffusion.util.beans.LocalizableString;

public class ImportError
  extends ErrorMessage
  implements Comparable
{
  private String jdField_int;
  private Integer jdField_do;
  private Integer jdField_for;
  private int jdField_new;
  private static final String jdField_if = "com.ffusion.beans.fileimporter.resources";
  private static final String a = "FileImport_0";
  private static final String jdField_case = "FileImport_1";
  private static final String jdField_byte = "FileImport_2";
  private static final String jdField_try = "FileImport_3";
  public static final int PRIORITY_VERY_HIGH = 4;
  public static final int PRIORITY_HIGH = 3;
  public static final int PRIORITY_MEDIUM = 2;
  public static final int PRIORITY_LOW = 1;
  
  public ImportError(int paramInt, String paramString1, String paramString2, String paramString3, Integer paramInteger1, Integer paramInteger2)
  {
    super(paramString1, paramString2);
    this.jdField_new = paramInt;
    this.jdField_int = paramString3;
    this.jdField_do = paramInteger1;
    this.jdField_for = paramInteger2;
  }
  
  public String toString()
  {
    LocalizableString localLocalizableString = null;
    Object[] arrayOfObject = null;
    String str = System.getProperty("line.separator");
    if (this.jdField_do != null)
    {
      if (this.jdField_for != null)
      {
        arrayOfObject = new Object[5];
        arrayOfObject[0] = this.title;
        arrayOfObject[1] = this.jdField_do;
        arrayOfObject[2] = this.jdField_for;
        arrayOfObject[3] = this.message;
        if (this.jdField_int != null) {
          arrayOfObject[4] = this.jdField_int;
        }
        localLocalizableString = new LocalizableString("com.ffusion.beans.fileimporter.resources", "FileImport_0", arrayOfObject);
      }
      else
      {
        arrayOfObject = new Object[4];
        arrayOfObject[0] = this.title;
        arrayOfObject[1] = this.jdField_do;
        arrayOfObject[2] = this.message;
        if (this.jdField_int != null) {
          arrayOfObject[3] = this.jdField_int;
        }
        localLocalizableString = new LocalizableString("com.ffusion.beans.fileimporter.resources", "FileImport_1", arrayOfObject);
      }
    }
    else if (this.jdField_for != null)
    {
      arrayOfObject = new Object[4];
      arrayOfObject[0] = this.title;
      arrayOfObject[1] = this.jdField_for;
      arrayOfObject[2] = this.message;
      if (this.jdField_int != null) {
        arrayOfObject[3] = this.jdField_int;
      }
      localLocalizableString = new LocalizableString("com.ffusion.beans.fileimporter.resources", "FileImport_2", arrayOfObject);
    }
    else
    {
      arrayOfObject = new Object[3];
      arrayOfObject[0] = this.title;
      arrayOfObject[1] = this.message;
      if (this.jdField_int != null) {
        arrayOfObject[2] = this.jdField_int;
      }
      localLocalizableString = new LocalizableString("com.ffusion.beans.fileimporter.resources", "FileImport_3", arrayOfObject);
    }
    return localLocalizableString.toString();
  }
  
  public int compareTo(Object paramObject)
  {
    ImportError localImportError = (ImportError)paramObject;
    if (this.jdField_new > localImportError.jdField_new) {
      return -1;
    }
    if (this.jdField_new < localImportError.jdField_new) {
      return 1;
    }
    int i = this.title.compareTo(localImportError.title);
    if (i != 0) {
      return i;
    }
    if (this.jdField_do != null)
    {
      if (localImportError.jdField_do != null)
      {
        if (this.jdField_do.intValue() > localImportError.jdField_do.intValue()) {
          return 1;
        }
        if (this.jdField_do.intValue() < localImportError.jdField_do.intValue()) {
          return -1;
        }
      }
      else
      {
        return -1;
      }
    }
    else if (localImportError.jdField_do != null) {
      return 1;
    }
    i = this.message.compareTo(localImportError.message);
    if (i != 0) {
      return i;
    }
    if (this.jdField_int != null)
    {
      if (localImportError.jdField_int != null)
      {
        i = this.jdField_int.compareTo(localImportError.jdField_int);
        if (i != 0) {
          return i;
        }
      }
      else
      {
        return -1;
      }
    }
    else if (localImportError.jdField_int != null) {
      return 1;
    }
    return 0;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public String getMessage()
  {
    return encode(this.message);
  }
  
  public String getLineContent()
  {
    return this.jdField_int == null ? null : encode(this.jdField_int);
  }
  
  public Integer getLineNumber()
  {
    return this.jdField_do;
  }
  
  public Integer getRecordNumber()
  {
    return this.jdField_for;
  }
  
  public String encode(String paramString)
  {
    int i = paramString.length();
    StringBuffer localStringBuffer = new StringBuffer(i + i / 5);
    int j = 1;
    for (int k = 0; k < i; k++)
    {
      char c = paramString.charAt(k);
      switch (c)
      {
      case '&': 
        localStringBuffer.append("&amp;");
        j = 1;
        break;
      case '<': 
        localStringBuffer.append("&lt;");
        j = 1;
        break;
      case '>': 
        localStringBuffer.append("&gt;");
        j = 1;
        break;
      default: 
        localStringBuffer.append(c);
      }
    }
    return j != 0 ? localStringBuffer.toString() : paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.fileimporter.ImportError
 * JD-Core Version:    0.7.0.1
 */