package com.ffusion.alert.plugins.message;

public class MessageImpl
  implements Message
{
  private String a = "";
  private StringBuffer jdField_do = new StringBuffer("");
  private String jdField_if = null;
  
  public MessageImpl() {}
  
  private String a(StringBuffer paramStringBuffer)
  {
    String str = "";
    if (paramStringBuffer != null) {
      str = paramStringBuffer.toString();
    }
    return str;
  }
  
  public MessageImpl(String paramString)
  {
    this();
    setSubject(paramString);
  }
  
  public MessageImpl(String paramString1, String paramString2)
  {
    this(paramString1);
    setContent(paramString2);
  }
  
  public String getSubject()
  {
    return this.a;
  }
  
  public void setSubject(String paramString)
  {
    this.a = paramString;
  }
  
  public String getContentValue()
  {
    return a(this.jdField_do);
  }
  
  public void setContent(String paramString)
  {
    this.jdField_do = new StringBuffer(paramString);
  }
  
  public String getType()
  {
    return this.jdField_if;
  }
  
  public void setType(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getContent()
  {
    return a(this.jdField_do);
  }
  
  private StringBuffer a()
  {
    return this.jdField_do;
  }
  
  public void addContent(String paramString)
  {
    if (a() != null) {
      a().append(paramString);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.message.MessageImpl
 * JD-Core Version:    0.7.0.1
 */