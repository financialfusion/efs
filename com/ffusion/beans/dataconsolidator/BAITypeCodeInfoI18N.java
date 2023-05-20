package com.ffusion.beans.dataconsolidator;

import com.ffusion.util.beans.ExtendABean;

public class BAITypeCodeInfoI18N
  extends ExtendABean
{
  public static final String SORTFIELD_LANGUAGE = "Language";
  public static final String SORTFIELD_DESCRIPTION = "Desc";
  private final String jdField_if;
  private String a;
  
  public BAITypeCodeInfoI18N(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString1.length() == 0)) {
      this.jdField_if = "en_US";
    } else {
      this.jdField_if = paramString1;
    }
    setDescription(paramString2);
  }
  
  public BAITypeCodeInfoI18N(String paramString)
  {
    this("en_US", paramString);
  }
  
  public String getLanguage()
  {
    return this.jdField_if;
  }
  
  public String getDescription()
  {
    return this.a;
  }
  
  public void setDescription(String paramString)
  {
    this.a = (paramString == null ? "" : paramString);
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer("language = ");
    localStringBuffer.append(this.jdField_if);
    localStringBuffer.append("\tdescription = ");
    localStringBuffer.append(this.a);
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.dataconsolidator.BAITypeCodeInfoI18N
 * JD-Core Version:    0.7.0.1
 */