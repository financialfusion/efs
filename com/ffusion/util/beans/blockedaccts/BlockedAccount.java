package com.ffusion.util.beans.blockedaccts;

import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.text.Collator;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class BlockedAccount
  extends ExtendABean
{
  public static final String BLOCKED_ACCOUNT = "BLOCKED_ACCOUNT";
  private String f;
  private String b;
  private String e;
  private String c;
  private int d = 0;
  
  public BlockedAccount()
  {
    this.f = null;
    this.b = null;
    this.e = null;
    this.c = null;
  }
  
  public BlockedAccount(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    this.f = paramString1;
    this.b = paramString2;
    this.e = paramString3;
    this.d = paramInt;
    this.c = null;
  }
  
  public BlockedAccount(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4)
  {
    this(paramString1, paramString2, paramString3, paramInt);
    this.c = paramString4;
  }
  
  public void setRoutingNumber(String paramString)
  {
    if (paramString != null) {
      this.f = paramString.trim();
    } else {
      this.f = null;
    }
  }
  
  public String getRoutingNumber()
  {
    return this.f;
  }
  
  public void setBankName(String paramString)
  {
    if (paramString != null) {
      this.b = paramString.trim();
    } else {
      this.b = null;
    }
  }
  
  public String getBankName()
  {
    return this.b;
  }
  
  public void setAccountNumber(String paramString)
  {
    if (paramString != null) {
      this.e = paramString.trim();
    } else {
      this.e = null;
    }
  }
  
  public String getAccountNumber()
  {
    return this.e;
  }
  
  public void setStrippedAccountNumber(String paramString)
  {
    if (paramString != null) {
      this.c = paramString.trim();
    } else {
      this.c = null;
    }
  }
  
  public String getStrippedAccountNumber()
  {
    return this.c;
  }
  
  public void setUserDirectoryID(int paramInt)
  {
    this.d = paramInt;
  }
  
  public void setUserDirectoryID(String paramString)
  {
    try
    {
      this.d = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this.d = 0;
    }
  }
  
  public int getUserDirectoryID()
  {
    return this.d;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Collator localCollator = doGetCollator();
    int i = -1;
    if ((paramObject instanceof BlockedAccount))
    {
      BlockedAccount localBlockedAccount = (BlockedAccount)paramObject;
      if ("ROUTING_NUMBER".equals(paramString))
      {
        if (this.f != null)
        {
          if (localBlockedAccount.f == null) {
            i = 1;
          } else {
            i = this.f.toLowerCase().compareTo(localBlockedAccount.f.toLowerCase());
          }
        }
        else if (localBlockedAccount.f == null) {
          i = 0;
        }
      }
      else if ("ACCOUNT_NUMBER".equals(paramString))
      {
        if (this.e != null)
        {
          if (localBlockedAccount.e == null) {
            i = 1;
          } else {
            i = this.e.toLowerCase().compareTo(localBlockedAccount.e.toLowerCase());
          }
        }
        else if (localBlockedAccount.e == null) {
          i = 0;
        }
      }
      else if ("BANK_NAME".equals(paramString)) {
        i = localCollator.compare(this.b == null ? null : this.b.toLowerCase(this.locale), localBlockedAccount.b == null ? null : localBlockedAccount.b.toLowerCase(this.locale));
      } else if ("STRIPPED_ACCOUNT_NUMBER".equals(paramString))
      {
        if (this.c != null)
        {
          if (localBlockedAccount.c == null) {
            i = 1;
          } else {
            i = this.c.toLowerCase().compareTo(localBlockedAccount.c.toLowerCase());
          }
        }
        else if (localBlockedAccount.c == null) {
          i = 0;
        }
      }
      else if ("DIRECTORY_ID".equals(paramString)) {
        i = this.d - localBlockedAccount.d;
      } else {
        i = super.compare(paramObject, paramString);
      }
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof BlockedAccount))
    {
      BlockedAccount localBlockedAccount = (BlockedAccount)paramObject;
      if ((this.d == localBlockedAccount.d) && (((this.f != null) && (this.f.equals(localBlockedAccount.f))) || ((this.f == null) && (localBlockedAccount.f == null) && (((this.e != null) && (this.e.equals(localBlockedAccount.e))) || ((this.e == null) && (localBlockedAccount.e == null) && (((this.b != null) && (this.b.equals(localBlockedAccount.b))) || ((this.b == null) && (localBlockedAccount.b == null) && (((this.c != null) && (this.c.equals(localBlockedAccount.c))) || ((this.c == null) && (localBlockedAccount.c == null)))))))))) {
        return true;
      }
    }
    return false;
  }
  
  public boolean isEquivalentExcludingBankName(BlockedAccount paramBlockedAccount)
  {
    boolean bool = false;
    if ((paramBlockedAccount != null) && (this.d == paramBlockedAccount.d) && (((this.f != null) && (this.f.equals(paramBlockedAccount.f))) || ((this.f == null) && (paramBlockedAccount.f == null) && (((this.c != null) && (this.c.equals(paramBlockedAccount.c))) || ((this.c == null) && (paramBlockedAccount.c == null)))))) {
      bool = true;
    }
    return bool;
  }
  
  public boolean isEquivalentBlockedAccount(BlockedAccount paramBlockedAccount)
  {
    boolean bool = false;
    if ((paramBlockedAccount != null) && (this.d == paramBlockedAccount.d) && (((this.f != null) && (this.f.equals(paramBlockedAccount.f))) || ((this.f == null) && (paramBlockedAccount.f == null) && (((this.c != null) && (this.c.equals(paramBlockedAccount.c))) || ((this.c == null) && (paramBlockedAccount.c == null) && (((this.b != null) && (this.b.equals(paramBlockedAccount.b))) || ((this.b == null) && (paramBlockedAccount.b == null)))))))) {
      bool = true;
    }
    return bool;
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=<>!", true);
    if ((localStringTokenizer.countTokens() == 3) || (localStringTokenizer.countTokens() == 4))
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      String str3 = localStringTokenizer.nextToken();
      if (localStringTokenizer.countTokens() == 1)
      {
        str2 = str2 + str3;
        str3 = localStringTokenizer.nextToken();
      }
      if (str1.equals("ROUTING_NUMBER")) {
        return isFilterable(this.f, str2, str3);
      }
      if (str1.equals("BANK_NAME")) {
        return isFilterable(this.b, str2, str3);
      }
      if (str1.equals("ACCOUNT_NUMBER")) {
        return isFilterable(this.e, str2, str3);
      }
      return super.isFilterable(paramString);
    }
    return false;
  }
  
  public int hashCode()
  {
    return toString().hashCode();
  }
  
  public String toString()
  {
    String str1 = this.c != null ? this.c : "";
    String str2 = this.b != null ? this.b : "";
    String str3 = this.f != null ? this.f : "";
    return str3 + " " + str2 + " " + str1 + " " + this.d;
  }
  
  public String getXML(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, paramString);
    XMLHandler.appendTag(localStringBuffer, "ROUTING_NUMBER", this.f);
    XMLHandler.appendTag(localStringBuffer, "ACCOUNT_NUMBER", this.e);
    XMLHandler.appendTag(localStringBuffer, "STRIPPED_ACCOUNT_NUMBER", this.c);
    XMLHandler.appendTag(localStringBuffer, "BANK_NAME", this.b);
    XMLHandler.appendTag(localStringBuffer, "DIRECTORY_ID", this.d);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, paramString);
    return localStringBuffer.toString();
  }
  
  public String getXML()
  {
    return getXML("BLOCKED_ACCOUNT");
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if ("ROUTING_NUMBER".equals(paramString1)) {
      setRoutingNumber(paramString2);
    } else if ("ACCOUNT_NUMBER".equals(paramString1)) {
      setAccountNumber(paramString2);
    } else if ("BANK_NAME".equals(paramString1)) {
      setBankName(paramString2);
    } else if ("DIRECTORY_ID".equals(paramString1)) {
      setUserDirectoryID(paramString2);
    } else if ("STRIPPED_ACCOUNT_NUMBER".equals(paramString1)) {
      setStrippedAccountNumber(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log(Level.FINE, "BlockedAccount.setXML : " + localThrowable.getMessage() + " occured when creating a Blocked Account from an XML string.");
    }
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends ExtendABean.InternalXMLHandler
  {
    a()
    {
      super();
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      BlockedAccount.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.blockedaccts.BlockedAccount
 * JD-Core Version:    0.7.0.1
 */