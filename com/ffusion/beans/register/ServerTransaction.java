package com.ffusion.beans.register;

public class ServerTransaction
{
  private String jdField_if = null;
  private String a = null;
  private int jdField_do = 0;
  
  public ServerTransaction(String paramString1, String paramString2, int paramInt)
  {
    this.jdField_if = paramString1;
    this.a = paramString2;
    this.jdField_do = paramInt;
  }
  
  public void setServerTID(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getServerTID()
  {
    return this.jdField_if;
  }
  
  public void setRecServerTID(String paramString)
  {
    this.a = paramString;
  }
  
  public String getRecServerTID()
  {
    return this.a;
  }
  
  public void setRegCategoryID(int paramInt)
  {
    this.jdField_do = paramInt;
  }
  
  public int getRegCategoryID()
  {
    return this.jdField_do;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.register.ServerTransaction
 * JD-Core Version:    0.7.0.1
 */