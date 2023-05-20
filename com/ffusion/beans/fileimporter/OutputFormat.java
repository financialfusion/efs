package com.ffusion.beans.fileimporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class OutputFormat
  extends OutputFormatI18N
{
  private String jdField_long;
  private ArrayList jdField_null;
  private ArrayList jdField_int;
  private ArrayList jdField_goto;
  private String jdField_try;
  private HashMap jdField_new;
  private ArrayList jdField_else;
  private String jdField_void;
  private String jdField_char;
  private HashMap jdField_case = new HashMap();
  
  public String getEntitlementName()
  {
    return this.jdField_long;
  }
  
  public void setEntitlementName(String paramString)
  {
    this.jdField_long = paramString;
  }
  
  public ArrayList getRequiredFieldList()
  {
    return this.jdField_null;
  }
  
  public void setRequiredFieldList(ArrayList paramArrayList)
  {
    this.jdField_null = paramArrayList;
  }
  
  public ArrayList getMatchRecordOptions()
  {
    return this.jdField_int;
  }
  
  public void setMatchRecordOptions(ArrayList paramArrayList)
  {
    this.jdField_int = paramArrayList;
  }
  
  public ArrayList getMatchRecordOptionsDisplayNames()
  {
    return this.jdField_goto;
  }
  
  public void setMatchRecordOptionsDisplayNames(ArrayList paramArrayList)
  {
    this.jdField_goto = paramArrayList;
  }
  
  public String getCmAdapterClass()
  {
    return this.jdField_try;
  }
  
  public void setCmAdapterClass(String paramString)
  {
    this.jdField_try = paramString;
  }
  
  public HashMap getCmAdapterSettings()
  {
    return this.jdField_new;
  }
  
  public void setCmAdapterSettings(HashMap paramHashMap)
  {
    this.jdField_new = paramHashMap;
  }
  
  public ArrayList getCategories()
  {
    return this.jdField_else;
  }
  
  public void setCategories(ArrayList paramArrayList)
  {
    this.jdField_else = paramArrayList;
  }
  
  public void setCurrentCategory(String paramString)
  {
    this.jdField_void = paramString;
  }
  
  public String getCurrentCategory()
  {
    return this.jdField_void;
  }
  
  public boolean getContainsCategory()
  {
    return this.jdField_else.contains(this.jdField_void);
  }
  
  public void setCurrentMatchRecordOption(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public String getCurrentMatchRecordOption()
  {
    return this.jdField_char;
  }
  
  public boolean getContainsMatchRecordOption()
  {
    if (this.jdField_char.indexOf(",") > -1)
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(this.jdField_char, ",");
      while (localStringTokenizer.hasMoreTokens())
      {
        String str = localStringTokenizer.nextToken();
        if (!this.jdField_int.contains(str)) {
          return false;
        }
      }
      return true;
    }
    return this.jdField_int.contains(this.jdField_char);
  }
  
  public boolean getContainsMatchRecordOptions()
  {
    return this.jdField_int.size() > 0;
  }
  
  public String getName(String paramString)
  {
    if ((paramString == null) || ("en_US".equals(paramString))) {
      return super.getName();
    }
    OutputFormatI18N localOutputFormatI18N = (OutputFormatI18N)this.jdField_case.get(paramString);
    if (localOutputFormatI18N == null) {
      return super.getName();
    }
    return localOutputFormatI18N.getName();
  }
  
  public void setName(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || ("en_US".equals(paramString1)))
    {
      setName(paramString2);
    }
    else
    {
      OutputFormatI18N localOutputFormatI18N = (OutputFormatI18N)this.jdField_case.get(paramString1);
      if (localOutputFormatI18N == null) {
        localOutputFormatI18N = new OutputFormatI18N();
      }
      localOutputFormatI18N.setLanguage(paramString1);
      localOutputFormatI18N.setName(paramString2);
      this.jdField_case.put(paramString1, localOutputFormatI18N);
    }
  }
  
  public ArrayList getFieldList(String paramString)
  {
    if ((paramString == null) || ("en_US".equals(paramString))) {
      return super.getFieldList();
    }
    OutputFormatI18N localOutputFormatI18N = (OutputFormatI18N)this.jdField_case.get(paramString);
    if (localOutputFormatI18N == null) {
      return super.getFieldList();
    }
    return localOutputFormatI18N.getFieldList();
  }
  
  public void setFieldList(String paramString, ArrayList paramArrayList)
  {
    if ((paramString == null) || ("en_US".equals(paramString)))
    {
      setFieldList(paramArrayList);
    }
    else
    {
      OutputFormatI18N localOutputFormatI18N = (OutputFormatI18N)this.jdField_case.get(paramString);
      if (localOutputFormatI18N == null) {
        localOutputFormatI18N = new OutputFormatI18N();
      }
      localOutputFormatI18N.setLanguage(paramString);
      localOutputFormatI18N.setFieldList(paramArrayList);
      this.jdField_case.put(paramString, localOutputFormatI18N);
    }
  }
  
  public ArrayList getMemoFieldList(String paramString)
  {
    if ((paramString == null) || ("en_US".equals(paramString))) {
      return super.getMemoFieldList();
    }
    OutputFormatI18N localOutputFormatI18N = (OutputFormatI18N)this.jdField_case.get(paramString);
    if (localOutputFormatI18N == null) {
      return super.getMemoFieldList();
    }
    return localOutputFormatI18N.getMemoFieldList();
  }
  
  public void setMemoFieldList(String paramString, ArrayList paramArrayList)
  {
    if ((paramString == null) || ("en_US".equals(paramString)))
    {
      setMemoFieldList(paramArrayList);
    }
    else
    {
      OutputFormatI18N localOutputFormatI18N = (OutputFormatI18N)this.jdField_case.get(paramString);
      if (localOutputFormatI18N == null) {
        localOutputFormatI18N = new OutputFormatI18N();
      }
      localOutputFormatI18N.setLanguage(paramString);
      localOutputFormatI18N.setMemoFieldList(paramArrayList);
      this.jdField_case.put(paramString, localOutputFormatI18N);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.fileimporter.OutputFormat
 * JD-Core Version:    0.7.0.1
 */