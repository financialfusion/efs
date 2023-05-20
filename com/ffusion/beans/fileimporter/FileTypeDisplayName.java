package com.ffusion.beans.fileimporter;

import com.ffusion.util.beans.ExtendABean;
import java.util.Locale;

public class FileTypeDisplayName
  extends ExtendABean
{
  private String a;
  private String jdField_if;
  
  public FileTypeDisplayName(String paramString1, String paramString2, String paramString3)
  {
    super.setLocale(paramString1);
    this.a = paramString2;
    this.jdField_if = paramString3;
  }
  
  public FileTypeDisplayName(Locale paramLocale, String paramString1, String paramString2)
  {
    super.setLocale(paramLocale);
    this.a = paramString1;
    this.jdField_if = paramString2;
  }
  
  public String getName()
  {
    return this.a;
  }
  
  public String getDisplayName()
  {
    return this.jdField_if;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    FileTypeDisplayName localFileTypeDisplayName = (FileTypeDisplayName)paramObject;
    int i = 1;
    if ((paramString.equals("NAME")) && (this.a != null) && (localFileTypeDisplayName.getName() != null)) {
      i = this.a.compareTo(localFileTypeDisplayName.getName());
    } else if ((paramString.equals("DISPLAY_NAME")) && (this.jdField_if != null) && (localFileTypeDisplayName.getDisplayName() != null)) {
      i = ExtendABean.compareStrings(this.jdField_if, localFileTypeDisplayName.getDisplayName(), doGetCollator());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.fileimporter.FileTypeDisplayName
 * JD-Core Version:    0.7.0.1
 */