package com.ffusion.ffs.bpw.interfaces;

public class OFXException
  extends Exception
{
  private int a = -1;
  private String jdField_do = null;
  private int jdField_if = -1;
  
  public OFXException()
  {
    super("OFX general error.");
  }
  
  public OFXException(String paramString)
  {
    super(paramString);
  }
  
  public OFXException(int paramInt)
  {
    this.a = paramInt;
  }
  
  public OFXException(String paramString, int paramInt)
  {
    super(paramString);
    this.a = paramInt;
  }
  
  public OFXException(String paramString, int paramInt1, int paramInt2)
  {
    super(paramString);
    this.a = paramInt1;
    this.jdField_if = paramInt2;
  }
  
  public String getExceptionMsg()
  {
    return getMessage();
  }
  
  public int getErrorCode()
  {
    return this.a;
  }
  
  public int getStatus()
  {
    return this.jdField_if;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.OFXException
 * JD-Core Version:    0.7.0.1
 */