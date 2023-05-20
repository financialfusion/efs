package com.ffusion.util.beans;

import com.ffusion.util.XMLHandler;
import java.util.Locale;

public class BankIdentifier
  extends ExtendABean
{
  public static final String BANK_IDENTIFIER = "BANK_IDENTIFIER";
  public static final String BANK_DIRECTORY_TYPE = "BANK_DIRECTORY_TYPE";
  public static final String BANK_DIRECTORY_ID = "BANK_DIRECTORY_ID";
  public static final String BANK_DIRECTORY_TYPE_FEDABA = "FedABA";
  public static final String BANK_DIRECTORY_TYPE_SWIFTBIC = "SWIFTBIC";
  private String x = "FedABA";
  private String w = "";
  
  public BankIdentifier(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public String getBankDirectoryType()
  {
    return this.x;
  }
  
  public void setBankDirectoryType(String paramString)
  {
    if (paramString == null) {
      paramString = "FedABA";
    }
    this.x = paramString;
  }
  
  public String getBankDirectoryId()
  {
    return this.w;
  }
  
  public void setBankDirectoryId(String paramString)
  {
    this.w = paramString;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("bankDirectoryType=").append(this.x);
    localStringBuffer.append("bankDirectoryId=").append(this.w);
    return localStringBuffer.toString();
  }
  
  public int compare(Object paramObject, String paramString)
  {
    BankIdentifier localBankIdentifier = (BankIdentifier)paramObject;
    int i = 1;
    if (paramString.equals("BANK_DIRECTORY_TYPE")) {
      i = this.x.compareToIgnoreCase(localBankIdentifier.getBankDirectoryType());
    } else if (paramString.equals("BANK_DIRECTORY_ID")) {
      i = this.w.compareToIgnoreCase(localBankIdentifier.getBankDirectoryId());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof BankIdentifier))
    {
      BankIdentifier localBankIdentifier = (BankIdentifier)paramObject;
      if (localBankIdentifier == this) {
        return true;
      }
      String str1 = localBankIdentifier.getBankDirectoryId();
      String str2 = getBankDirectoryId();
      if ((str1 != null) && (str2 != null))
      {
        if (!str1.equalsIgnoreCase(str2)) {
          return false;
        }
      }
      else if (str1 != str2) {
        return false;
      }
      String str3 = localBankIdentifier.getBankDirectoryType();
      String str4 = getBankDirectoryType();
      if ((str3 != null) && (str4 != null))
      {
        if (!str3.equalsIgnoreCase(str4)) {
          return false;
        }
      }
      else if (str3 != str4) {
        return false;
      }
    }
    else
    {
      return false;
    }
    return true;
  }
  
  public int hashCode()
  {
    int i = 0;
    if (this.w != null) {
      i = this.w.hashCode() * 3;
    }
    if (this.x != null) {
      i += this.x.hashCode() * 5;
    }
    return i;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BANK_IDENTIFIER");
    XMLHandler.appendTag(localStringBuffer, "BANK_DIRECTORY_TYPE", getBankDirectoryType());
    if (this.w != null) {
      XMLHandler.appendTag(localStringBuffer, "BANK_DIRECTORY_ID", this.w);
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "BANK_IDENTIFIER");
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
    if (paramString1.equals("BANK_DIRECTORY_TYPE")) {
      setBankDirectoryType(paramString2);
    } else if (paramString1.equals("BANK_DIRECTORY_ID")) {
      setBankDirectoryId(paramString2);
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
      BankIdentifier.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.BankIdentifier
 * JD-Core Version:    0.7.0.1
 */