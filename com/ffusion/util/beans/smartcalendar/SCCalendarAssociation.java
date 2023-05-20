package com.ffusion.util.beans.smartcalendar;

import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.BankIdentifier;
import com.ffusion.util.beans.ExtendABean;
import java.util.Locale;

public class SCCalendarAssociation
  extends ExtendABean
{
  public static final String SC_CALENDAR_ASSOCIATION = "SC_CALENDAR_ASSOCIATION";
  public static final String BANK_IDENTIFIER = "BANK_IDENTIFIER";
  public static final String CALENDAR_ID = "CALENDAR_ID";
  private BankIdentifier k;
  private int j;
  
  public SCCalendarAssociation(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public BankIdentifier getBankIdentifier()
  {
    return this.k;
  }
  
  public void setBankIdentifier(BankIdentifier paramBankIdentifier)
  {
    this.k = paramBankIdentifier;
  }
  
  public int getCalendarId()
  {
    return this.j;
  }
  
  public void setCalendarId(int paramInt)
  {
    this.j = paramInt;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("bankIdentifier=(").append(this.k).append(")");
    localStringBuffer.append("calendarId=").append(this.j);
    return localStringBuffer.toString();
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.k != null) {
      this.k.setLocale(paramLocale);
    }
  }
  
  public int compare(Object paramObject, String paramString)
  {
    SCCalendarAssociation localSCCalendarAssociation = (SCCalendarAssociation)paramObject;
    int i = 1;
    if (paramString.equals("BANK_IDENTIFIER"))
    {
      i = this.k.compare(localSCCalendarAssociation.getBankIdentifier(), "BANK_DIRECTORY_TYPE");
      if (i == 0) {
        i = this.k.compare(localSCCalendarAssociation.getBankIdentifier(), "BANK_DIRECTORY_ID");
      }
    }
    else if (paramString.equals("CALENDAR_ID"))
    {
      i = this.j - localSCCalendarAssociation.getCalendarId();
    }
    else
    {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "SC_CALENDAR_ASSOCIATION");
    if (this.k != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "BANK_IDENTIFIER");
      localStringBuffer.append(this.k.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "BANK_IDENTIFIER");
    }
    XMLHandler.appendTag(localStringBuffer, "CALENDAR_ID", this.j);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "SC_CALENDAR_ASSOCIATION");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new InternalXMLHandler(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new InternalXMLHandler());
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("CALENDAR_ID")) {
      this.j = Integer.parseInt(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  class InternalXMLHandler
    extends XMLHandler
  {
    public InternalXMLHandler() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      SCCalendarAssociation.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("BANK_IDENTIFIER"))
      {
        if (SCCalendarAssociation.this.k == null) {
          SCCalendarAssociation.this.k = new BankIdentifier(SCCalendarAssociation.this.locale);
        }
        SCCalendarAssociation.this.k.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.smartcalendar.SCCalendarAssociation
 * JD-Core Version:    0.7.0.1
 */