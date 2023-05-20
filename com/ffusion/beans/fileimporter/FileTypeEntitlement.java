package com.ffusion.beans.fileimporter;

public class FileTypeEntitlement
  implements Comparable
{
  private String jdField_if;
  private String a;
  private String jdField_do;
  
  public FileTypeEntitlement(String paramString1, String paramString2)
  {
    this.jdField_if = paramString1;
    this.a = paramString2;
    this.jdField_do = paramString1;
  }
  
  public FileTypeEntitlement(String paramString1, String paramString2, String paramString3)
  {
    this.jdField_if = paramString1;
    this.a = paramString2;
    this.jdField_do = paramString3;
  }
  
  public void setFileType(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getFileType()
  {
    return this.jdField_if;
  }
  
  public void setEntitlementName(String paramString)
  {
    this.a = paramString;
  }
  
  public String getEntitlementName()
  {
    return this.a;
  }
  
  public void setDisplayName(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getDisplayName()
  {
    return this.jdField_do;
  }
  
  public int compareTo(Object paramObject)
  {
    FileTypeEntitlement localFileTypeEntitlement = (FileTypeEntitlement)paramObject;
    return this.jdField_if.compareTo(localFileTypeEntitlement.getFileType());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.fileimporter.FileTypeEntitlement
 * JD-Core Version:    0.7.0.1
 */