package com.ffusion.beans.messages;

import com.ffusion.beans.ExtendABean;
import java.util.Locale;

public class GlobalMessageI18N
  extends ExtendABean
  implements GlobalMessageConsts
{
  public static final String GLOBAL_MESSAGE_ID = "GLOBAL_MESSAGE_ID";
  public static final String LANGUAGE = "LANGUAGE";
  public static final String TO_GROUP_NAME = "TO_GROUP_NAME";
  public static final String FROM_NAME = "FROM_NAME";
  public static final String SUBJECT = "SUBJECT";
  public static final String MESSAGE_TEXT = "MESSAGE_TEXT";
  private int aow;
  private String aoy;
  private String aox;
  private String aoB;
  private String aoA;
  private String aoz;
  
  public GlobalMessageI18N(String paramString)
  {
    if ((paramString != null) && (paramString.length() == 5) && (paramString.charAt(2) == '_'))
    {
      this.aoy = paramString;
      this.locale = new Locale(paramString.substring(0, 2), paramString.substring(3, 5));
    }
    else
    {
      throw new IllegalArgumentException();
    }
  }
  
  public GlobalMessageI18N(Locale paramLocale)
  {
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.locale = paramLocale;
    if ((paramLocale.getCountry().equals("")) || (paramLocale.getLanguage().equals(""))) {
      throw new IllegalArgumentException();
    }
    this.aoy = (paramLocale.getLanguage() + "_" + paramLocale.getCountry());
  }
  
  public void setGlobalMsgID(int paramInt)
  {
    this.aow = paramInt;
  }
  
  public void setGlobalMsgID(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      this.aow = i;
    }
    catch (Exception localException) {}
  }
  
  public int getGlobalMsgIDValue()
  {
    return this.aow;
  }
  
  public String getGlobalMsgID()
  {
    return String.valueOf(this.aow);
  }
  
  public void setToGroupName(String paramString)
  {
    if (validStringData(paramString) == true) {
      this.aox = paramString;
    }
  }
  
  public String getToGroupName()
  {
    return this.aox;
  }
  
  public void setFromName(String paramString)
  {
    if (paramString != null) {
      this.aoB = paramString;
    }
  }
  
  public String getFromName()
  {
    return this.aoB;
  }
  
  public void setSubject(String paramString)
  {
    if (validStringData(paramString) == true) {
      this.aoA = paramString;
    }
  }
  
  public String getSubject()
  {
    return this.aoA;
  }
  
  public void setMsgText(String paramString)
  {
    if (validStringData(paramString) == true) {
      this.aoz = paramString;
    }
  }
  
  public String getMsgText()
  {
    return this.aoz;
  }
  
  public String getLanguage()
  {
    return this.aoy;
  }
  
  public void setLanguage(String paramString)
  {
    this.aoy = paramString;
  }
  
  protected boolean validStringData(String paramString)
  {
    return (paramString != null) && (paramString.trim().length() > 0);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.GlobalMessageI18N
 * JD-Core Version:    0.7.0.1
 */