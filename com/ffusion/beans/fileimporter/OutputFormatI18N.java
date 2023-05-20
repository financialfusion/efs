package com.ffusion.beans.fileimporter;

import com.ffusion.util.beans.ExtendABean;
import java.util.ArrayList;

public class OutputFormatI18N
  extends ExtendABean
{
  private String jdField_if;
  private String a;
  private ArrayList jdField_do;
  private ArrayList jdField_for;
  
  public String getName()
  {
    return this.jdField_if;
  }
  
  public void setName(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getLanguage()
  {
    return this.a;
  }
  
  public void setLanguage(String paramString)
  {
    this.a = paramString;
  }
  
  public ArrayList getFieldList()
  {
    return this.jdField_do;
  }
  
  public void setFieldList(ArrayList paramArrayList)
  {
    this.jdField_do = paramArrayList;
  }
  
  public ArrayList getMemoFieldList()
  {
    return this.jdField_for;
  }
  
  public void setMemoFieldList(ArrayList paramArrayList)
  {
    this.jdField_for = paramArrayList;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.fileimporter.OutputFormatI18N
 * JD-Core Version:    0.7.0.1
 */