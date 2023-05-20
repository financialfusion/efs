package com.ffusion.alert.plugins;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Locale;

public class AlertUserInfo
  extends ExtendABean
  implements Serializable
{
  public static final String ALERTUSERINFO = "ALERTUSERINFO";
  public static final String DIRECTORYID = "DIRECTORYID";
  public static final String ACCOUNTID = "ACCOUNTID";
  public static final String BALANCE = "BALANCE";
  public static final String NSF = "NSF";
  protected String directoryID;
  protected String accountID;
  protected double balance;
  protected boolean nsf;
  
  public AlertUserInfo() {}
  
  public AlertUserInfo(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
  }
  
  public String getDirectoryID()
  {
    return this.directoryID;
  }
  
  public void setDirectoryID(String paramString)
  {
    this.directoryID = paramString;
  }
  
  public String getAccountID()
  {
    return this.accountID;
  }
  
  public void setAccountID(String paramString)
  {
    this.accountID = paramString;
  }
  
  public String getBalance()
  {
    return String.valueOf(this.balance);
  }
  
  public double getBalanceValue()
  {
    return this.balance;
  }
  
  public void setBalance(String paramString)
  {
    this.balance = Double.parseDouble(paramString);
  }
  
  public void setBalance(double paramDouble)
  {
    this.balance = paramDouble;
  }
  
  public String getNsf()
  {
    return String.valueOf(this.nsf);
  }
  
  public boolean getNsfValue()
  {
    return this.nsf;
  }
  
  public void setNsf(String paramString)
  {
    if (paramString.trim().equalsIgnoreCase("true")) {
      this.nsf = true;
    } else {
      this.nsf = false;
    }
  }
  
  public void setNsf(boolean paramBoolean)
  {
    this.nsf = paramBoolean;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "DIRECTORYID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    AlertUserInfo localAlertUserInfo = (AlertUserInfo)paramObject;
    int i = 1;
    if ((paramString.equals("DIRECTORYID")) && (this.directoryID != null) && (localAlertUserInfo.getDirectoryID() != null)) {
      i = this.directoryID.compareTo(localAlertUserInfo.getDirectoryID());
    } else if ((paramString.equals("ACCOUNTID")) && (this.accountID != null) && (localAlertUserInfo.getAccountID() != null)) {
      i = this.accountID.compareTo(localAlertUserInfo.getAccountID());
    } else if (paramString.equals("BALANCE")) {
      i = getBalanceValue() == localAlertUserInfo.getBalanceValue() ? 0 : 1;
    } else if (paramString.equals("NSF")) {
      i = getNsfValue() == localAlertUserInfo.getNsfValue() ? 0 : 1;
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public void set(AlertUserInfo paramAlertUserInfo)
  {
    setDirectoryID(paramAlertUserInfo.getDirectoryID());
    setAccountID(paramAlertUserInfo.getAccountID());
    setBalance(paramAlertUserInfo.getBalanceValue());
    setNsf(paramAlertUserInfo.getNsfValue());
    super.set(paramAlertUserInfo);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("DIRECTORYID")) {
        this.directoryID = paramString2;
      } else if (paramString1.equals("ACCOUNTID")) {
        this.accountID = paramString2;
      } else if (paramString1.equals("BALANCE")) {
        this.balance = Double.parseDouble(paramString2);
      } else if (paramString1.equals("NSF")) {
        setNsf(paramString2);
      } else {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException)
    {
      System.out.println("Exception: " + localException);
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
    XMLHandler.appendBeginTag(localStringBuffer, "ALERTUSERINFO");
    XMLHandler.appendTag(localStringBuffer, "DIRECTORYID", this.directoryID);
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTID", this.accountID);
    XMLHandler.appendTag(localStringBuffer, "BALANCE", this.balance);
    XMLHandler.appendTag(localStringBuffer, "NSF", String.valueOf(this.nsf));
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ALERTUSERINFO");
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
    paramXMLHandler.continueWith(new a(), "ALERTUSERINFO");
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      AlertUserInfo.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.AlertUserInfo
 * JD-Core Version:    0.7.0.1
 */