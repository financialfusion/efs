package com.ffusion.beans.positivepay;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class PPayAccount
  extends ExtendABean
  implements Comparable, Serializable
{
  public static final String PPAYACCOUNT = "PPAYACCOUNT";
  public static final String ACCOUNTID = "ACCOUNTID";
  public static final String BANKID = "BANKID";
  public static final String BANKNAME = "BANKNAME";
  public static final String ROUTINGNUMBER = "ROUTINGNUMBER";
  public static final String NICKNAME = "NICKNAME";
  public static final String CURRENCYTYPE = "CURRENCYTYPE";
  private String a5j = null;
  private String a5m = null;
  private String a5o = null;
  private String a5n = null;
  private String a5l = null;
  private String a5k = null;
  
  public PPayAccount(Locale paramLocale)
  {
    this.locale = paramLocale;
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() == 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      if (str1.equalsIgnoreCase("ACCOUNTID")) {
        return this.a5j.equals(str2);
      }
      if (str1.equalsIgnoreCase("NICKNAME")) {
        return this.a5l.equals(str2);
      }
      if (str1.equalsIgnoreCase("BANKID")) {
        return this.a5m.equals(str2);
      }
      if (str1.equalsIgnoreCase("BANKNAME")) {
        return this.a5o.equals(str2);
      }
      if (str1.equalsIgnoreCase("ROUTINGNUMBER")) {
        return this.a5n.equals(str2);
      }
      if (str1.equalsIgnoreCase("CURRENCYTYPE")) {
        return this.a5k.equals(str2);
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "ACCOUNTID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    PPayAccount localPPayAccount = (PPayAccount)paramObject;
    int i = 1;
    if ((paramString.equals("ACCOUNTID")) && (this.a5j != null) && (localPPayAccount.getAccountID() != null)) {
      i = this.a5j.compareToIgnoreCase(localPPayAccount.getAccountID());
    } else if ((paramString.equals("NICKNAME")) && (this.a5l != null) && (localPPayAccount.getNickName() != null)) {
      i = this.a5l.compareToIgnoreCase(localPPayAccount.getNickName());
    } else if ((paramString.equals("BANKID")) && (this.a5m != null) && (localPPayAccount.getBankID() != null)) {
      i = this.a5m.compareToIgnoreCase(localPPayAccount.getBankID());
    } else if ((paramString.equals("BANKNAME")) && (this.a5o != null) && (localPPayAccount.getBankName() != null)) {
      i = this.a5o.compareToIgnoreCase(localPPayAccount.getBankName());
    } else if ((paramString.equals("ROUTINGNUMBER")) && (this.a5n != null) && (localPPayAccount.getRoutingNumber() != null)) {
      i = this.a5n.compareToIgnoreCase(localPPayAccount.getRoutingNumber());
    } else if ((paramString.equals("CURRENCYTYPE")) && (this.a5k != null) && (localPPayAccount.getCurrencyType() != null)) {
      i = this.a5k.compareToIgnoreCase(localPPayAccount.getCurrencyType());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof PPayAccount)) {
      return false;
    }
    PPayAccount localPPayAccount = (PPayAccount)paramObject;
    return (jdMethod_new(this.a5j, localPPayAccount.getAccountID())) && (jdMethod_new(this.a5l, localPPayAccount.getNickName())) && (jdMethod_new(this.a5o, localPPayAccount.getBankName())) && (jdMethod_new(this.a5n, localPPayAccount.getRoutingNumber())) && (jdMethod_new(this.a5m, localPPayAccount.getBankID())) && (jdMethod_new(this.a5k, localPPayAccount.getCurrencyType()));
  }
  
  public void setAccountID(String paramString)
  {
    if ((this.a5j != null) && (!this.a5j.equals(paramString)))
    {
      DebugLog.log(Level.WARNING, "PPayAccount.setAccountID.  Cannot modify the value of account ID.");
      return;
    }
    this.a5j = paramString;
  }
  
  public String getAccountID()
  {
    return this.a5j;
  }
  
  public String getAccountNumber()
  {
    if ((this.a5j == null) || (this.a5j.endsWith("-"))) {
      return null;
    }
    int i = this.a5j.indexOf("-");
    return i > 0 ? this.a5j.substring(0, i) : this.a5j;
  }
  
  public void setNickName(String paramString)
  {
    this.a5l = paramString;
  }
  
  public String getNickName()
  {
    return this.a5l;
  }
  
  public void setBankID(String paramString)
  {
    if ((this.a5m != null) && (!this.a5m.equals(paramString)))
    {
      DebugLog.log(Level.WARNING, "PPayAccount.setBankID.  Cannot modify the value of bank ID.");
      return;
    }
    this.a5m = paramString;
  }
  
  public String getBankID()
  {
    return this.a5m;
  }
  
  public void setBankName(String paramString)
  {
    this.a5o = paramString;
  }
  
  public String getBankName()
  {
    return this.a5o;
  }
  
  public void setRoutingNumber(String paramString)
  {
    if ((this.a5n != null) && (!this.a5n.equals(paramString)))
    {
      DebugLog.log(Level.WARNING, "PPayAccount.setRoutingNumber.  Cannot modify the value of routing number.");
      return;
    }
    this.a5n = paramString;
  }
  
  public String getRoutingNumber()
  {
    return this.a5n;
  }
  
  public void setCurrencyType(String paramString)
  {
    this.a5k = paramString;
  }
  
  public String getCurrencyType()
  {
    return this.a5k;
  }
  
  public void set(PPayAccount paramPPayAccount)
  {
    super.set(paramPPayAccount);
    setNickName(paramPPayAccount.getNickName());
    setAccountID(paramPPayAccount.getAccountID());
    setBankID(paramPPayAccount.getBankID());
    setBankName(paramPPayAccount.getBankName());
    setRoutingNumber(paramPPayAccount.getRoutingNumber());
    setCurrencyType(paramPPayAccount.getCurrencyType());
    setLocale(paramPPayAccount.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("ACCOUNTID")) {
        setAccountID(paramString2);
      } else if (paramString1.equals("NICKNAME")) {
        this.a5l = paramString2;
      } else if (paramString1.equals("BANKID")) {
        setBankID(paramString2);
      } else if (paramString1.equals("BANKNAME")) {
        this.a5o = paramString2;
      } else if (paramString1.equals("ROUTINGNUMBER")) {
        setRoutingNumber(paramString2);
      } else if (paramString1.equals("CURRENCYTYPE")) {
        this.a5k = paramString2;
      } else {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception", localException);
    }
    return bool;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "PPAYACCOUNT");
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTID", this.a5j);
    XMLHandler.appendTag(localStringBuffer, "BANKID", this.a5m);
    XMLHandler.appendTag(localStringBuffer, "BANKNAME", this.a5o);
    XMLHandler.appendTag(localStringBuffer, "ROUTINGNUMBER", this.a5n);
    XMLHandler.appendTag(localStringBuffer, "NICKNAME", this.a5l);
    XMLHandler.appendTag(localStringBuffer, "CURRENCYTYPE", this.a5k);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "PPAYACCOUNT");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  private boolean jdMethod_new(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == paramObject2) {
      return true;
    }
    if ((paramObject1 == null) || (paramObject2 == null)) {
      return false;
    }
    return paramObject1.equals(paramObject2);
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
      PPayAccount.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.positivepay.PPayAccount
 * JD-Core Version:    0.7.0.1
 */