package com.ffusion.beans.positivepay;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

public class PPayCheckRecord
  extends ExtendABean
  implements Comparable, Serializable
{
  private static final String a5a = "                              ";
  public static final String PPAYCHECKRECORD = "PPAYCHECKRECORD";
  public static final String ACCOUNTID = "ACCOUNTID";
  public static final String BANKID = "BANKID";
  public static final String BANKNAME = "BANKNAME";
  public static final String ROUTINGNUMBER = "ROUTINGNUMBER";
  public static final String CHECKNUMBER = "CHECKNUMBER";
  public static final String CHECKDATE = "CHECKDATE";
  public static final String AMOUNT = "AMOUNT";
  public static final String VOIDCHECK = "VOIDCHECK";
  public static final String ADDITIONALDATA = "ADDITIONALDATA";
  private String a49 = null;
  private String a5c = null;
  private String a47 = null;
  private String a48 = null;
  private String a5b = null;
  private DateTime a45 = null;
  private Currency a46 = null;
  private boolean a44 = false;
  private String a5d = null;
  
  public PPayCheckRecord()
  {
    this.datetype = "SHORT";
  }
  
  public PPayCheckRecord(Locale paramLocale)
  {
    super(paramLocale);
    this.datetype = "SHORT";
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.a46 != null) {
      this.a46.setLocale(paramLocale);
    }
    if (this.a45 != null) {
      this.a45.setLocale(paramLocale);
    }
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() == 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      if (str1.equalsIgnoreCase("ACCOUNTID")) {
        return this.a49.equals(str2);
      }
      if (str1.equalsIgnoreCase("CHECKNUMBER")) {
        return this.a5b.equals(str2);
      }
      if (str1.equalsIgnoreCase("BANKID")) {
        return this.a5c.equals(str2);
      }
      if (str1.equalsIgnoreCase("BANKNAME")) {
        return this.a47.equalsIgnoreCase(str2);
      }
      if (str1.equalsIgnoreCase("ROUTINGNUMBER")) {
        return this.a48.equals(str2);
      }
      if (str1.equalsIgnoreCase("CHECKDATE")) {
        try
        {
          int i = this.a45.compare(new DateTime(str2, this.locale), null);
          if (i != 0) {
            return false;
          }
        }
        catch (InvalidDateTimeException localInvalidDateTimeException)
        {
          DebugLog.throwing("Problem with date format when comparing checkdate.", localInvalidDateTimeException);
          return false;
        }
      }
      if (str1.equalsIgnoreCase("VOIDCHECK")) {
        return this.a44 == Boolean.valueOf(str2).booleanValue();
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
    PPayCheckRecord localPPayCheckRecord = (PPayCheckRecord)paramObject;
    int i = 1;
    if ((paramString.equals("ACCOUNTID")) && (this.a49 != null) && (localPPayCheckRecord.getAccountID() != null))
    {
      i = this.a49.compareToIgnoreCase(localPPayCheckRecord.getAccountID());
    }
    else if ((paramString.equals("CHECKDATE")) && (this.a45 != null) && (localPPayCheckRecord.getCheckDate() != null))
    {
      i = this.a45.compare(localPPayCheckRecord.getCheckDate(), null);
    }
    else if (paramString.equals("CHECKNUMBER"))
    {
      ArrayList localArrayList = h(this.a5b, localPPayCheckRecord.getCheckNumber());
      i = ((String)localArrayList.get(0)).compareToIgnoreCase((String)localArrayList.get(1));
    }
    else if ((paramString.equals("BANKID")) && (this.a5c != null) && (localPPayCheckRecord.getBankID() != null))
    {
      i = this.a5c.compareToIgnoreCase(localPPayCheckRecord.getBankID());
    }
    else if ((paramString.equals("BANKNAME")) && (this.a47 != null) && (localPPayCheckRecord.getBankName() != null))
    {
      i = this.a47.compareToIgnoreCase(localPPayCheckRecord.getBankName());
    }
    else if ((paramString.equals("ROUTINGNUMBER")) && (this.a48 != null) && (localPPayCheckRecord.getRoutingNumber() != null))
    {
      i = this.a48.compareToIgnoreCase(localPPayCheckRecord.getRoutingNumber());
    }
    else if ((paramString.equals("AMOUNT")) && (this.a46 != null) && (localPPayCheckRecord.getAmount() != null))
    {
      i = this.a46.compareTo(localPPayCheckRecord.getAmount());
    }
    else if (paramString.equals("VOIDCHECK"))
    {
      i = new Boolean(this.a44).toString().compareToIgnoreCase(new Boolean(localPPayCheckRecord.getVoidCheck()).toString());
    }
    else
    {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof PPayCheckRecord)) {
      return false;
    }
    PPayCheckRecord localPPayCheckRecord = (PPayCheckRecord)paramObject;
    return (this.a44 == localPPayCheckRecord.getVoidCheck()) && (jdMethod_for(this.a49, localPPayCheckRecord.getAccountID())) && (jdMethod_for(this.a5b, localPPayCheckRecord.getCheckNumber())) && (jdMethod_for(this.a5c, localPPayCheckRecord.getBankID())) && (jdMethod_for(this.a47, localPPayCheckRecord.getBankName())) && (jdMethod_for(this.a48, localPPayCheckRecord.getRoutingNumber())) && (jdMethod_for(this.a5d, localPPayCheckRecord.getAdditionalData())) && (jdMethod_for(this.a45, localPPayCheckRecord.getCheckDate())) && (jdMethod_for(this.a46, localPPayCheckRecord.getAmount()));
  }
  
  public void setAccountID(String paramString)
  {
    this.a49 = paramString;
  }
  
  public String getAccountID()
  {
    return this.a49;
  }
  
  public void setBankName(String paramString)
  {
    this.a47 = paramString;
  }
  
  public String getBankName()
  {
    return this.a47;
  }
  
  public void setRoutingNumber(String paramString)
  {
    this.a48 = paramString;
  }
  
  public String getRoutingNumber()
  {
    return this.a48;
  }
  
  public void setCheckNumber(String paramString)
  {
    this.a5b = paramString;
  }
  
  public String getCheckNumber()
  {
    return this.a5b;
  }
  
  public void setBankID(String paramString)
  {
    this.a5c = paramString;
  }
  
  public String getBankID()
  {
    return this.a5c;
  }
  
  public void setCheckDate(DateTime paramDateTime)
  {
    this.a45 = paramDateTime;
  }
  
  public DateTime getCheckDate()
  {
    return this.a45;
  }
  
  public void setAmount(Currency paramCurrency)
  {
    this.a46 = paramCurrency;
  }
  
  public Currency getAmount()
  {
    return this.a46;
  }
  
  public String getAmountNoSymbol()
  {
    return this.a46.getCurrencyStringNoSymbol();
  }
  
  public void setVoidCheck(boolean paramBoolean)
  {
    this.a44 = paramBoolean;
  }
  
  public boolean getVoidCheck()
  {
    return this.a44;
  }
  
  public void setAdditionalData(String paramString)
  {
    this.a5d = paramString;
  }
  
  public String getAdditionalData()
  {
    return this.a5d;
  }
  
  public void set(PPayCheckRecord paramPPayCheckRecord)
  {
    super.set(paramPPayCheckRecord);
    setCheckNumber(paramPPayCheckRecord.getCheckNumber());
    setAccountID(paramPPayCheckRecord.getAccountID());
    setBankID(paramPPayCheckRecord.getBankID());
    setBankName(paramPPayCheckRecord.getBankName());
    setRoutingNumber(paramPPayCheckRecord.getRoutingNumber());
    setCheckDate(paramPPayCheckRecord.getCheckDate());
    setAmount(paramPPayCheckRecord.getAmount());
    setVoidCheck(paramPPayCheckRecord.getVoidCheck());
    setAdditionalData(paramPPayCheckRecord.getAdditionalData());
    this.datetype = paramPPayCheckRecord.datetype;
    setLocale(paramPPayCheckRecord.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("ACCOUNTID")) {
        this.a49 = paramString2;
      } else if (paramString1.equals("CHECKNUMBER")) {
        this.a5b = paramString2;
      } else if (paramString1.equals("BANKID")) {
        this.a5c = paramString2;
      } else if (paramString1.equals("BANKNAME")) {
        this.a47 = paramString2;
      } else if (paramString1.equals("ROUTINGNUMBER")) {
        this.a48 = paramString2;
      } else if (paramString1.equals("VOIDCHECK")) {
        this.a44 = Boolean.valueOf(paramString2).booleanValue();
      } else if (paramString1.equals("ADDITIONALDATA")) {
        this.a5d = paramString2;
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
    XMLHandler.appendBeginTag(localStringBuffer, "PPAYCHECKRECORD");
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTID", this.a49);
    XMLHandler.appendTag(localStringBuffer, "BANKID", this.a5c);
    XMLHandler.appendTag(localStringBuffer, "BANKNAME", this.a47);
    XMLHandler.appendTag(localStringBuffer, "ROUTINGNUMBER", this.a48);
    XMLHandler.appendTag(localStringBuffer, "CHECKNUMBER", this.a5b);
    if (this.a45 != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "CHECKDATE");
      localStringBuffer.append(this.a45.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "CHECKDATE");
    }
    if (this.a46 != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "AMOUNT");
      localStringBuffer.append(this.a46.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "AMOUNT");
    }
    XMLHandler.appendTag(localStringBuffer, "VOIDCHECK", new Boolean(this.a44).toString());
    XMLHandler.appendTag(localStringBuffer, "ADDITIONALDATA", this.a5d);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "PPAYCHECKRECORD");
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
  
  private static ArrayList h(String paramString1, String paramString2)
  {
    ArrayList localArrayList = new ArrayList();
    int i = paramString1.length();
    int j = paramString2.length();
    int k = i - j;
    String str = null;
    if (k == 0)
    {
      localArrayList.add(paramString1);
      localArrayList.add(paramString2);
      return localArrayList;
    }
    if (k > 0)
    {
      str = paramString2;
    }
    else if (k < 0)
    {
      str = paramString1;
      k *= -1;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    while (k > 0) {
      if (k <= "                              ".length())
      {
        localStringBuffer.append("                              ".substring(0, k));
        localStringBuffer.append(str);
        k = 0;
      }
      else
      {
        localStringBuffer.append("                              ");
        k -= "                              ".length();
      }
    }
    if (str == paramString1)
    {
      localArrayList.add(localStringBuffer.toString());
      localArrayList.add(paramString2);
    }
    else
    {
      localArrayList.add(paramString1);
      localArrayList.add(localStringBuffer.toString());
    }
    return localArrayList;
  }
  
  private boolean jdMethod_for(Object paramObject1, Object paramObject2)
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
    public a()
    {
      super();
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      PPayCheckRecord.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("CHECKDATE"))
      {
        if (PPayCheckRecord.this.a45 == null) {
          PPayCheckRecord.this.a45 = new DateTime(PPayCheckRecord.this.locale);
        }
        PPayCheckRecord.this.a45.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("AMOUNT"))
      {
        if (PPayCheckRecord.this.a46 == null)
        {
          PPayCheckRecord.this.a46 = new Currency();
          PPayCheckRecord.this.a46.setLocale(PPayCheckRecord.this.locale);
        }
        PPayCheckRecord.this.a46.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.positivepay.PPayCheckRecord
 * JD-Core Version:    0.7.0.1
 */