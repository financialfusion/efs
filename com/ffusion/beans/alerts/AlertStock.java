package com.ffusion.beans.alerts;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.text.Collator;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class AlertStock
  extends ExtendABean
  implements Serializable
{
  public static final String ALERTSTOCK = "ALERTSTOCK";
  public static final String SYMBOL = "Symbol";
  public static final String AMOUNT = "Amount";
  public static final String CRITERIA = "Criteria";
  public static final String ONETIME = "Onetime";
  private Alert a0q = null;
  protected int ID;
  protected String id;
  protected String symbol;
  protected String onetime;
  protected String criteria;
  protected double amount;
  protected String IDStr;
  
  public AlertStock() {}
  
  public AlertStock(Locale paramLocale)
  {
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    setLocale(paramLocale);
  }
  
  public void setAlert(Alert paramAlert)
  {
    this.a0q = paramAlert;
  }
  
  public Alert getAlert()
  {
    return this.a0q;
  }
  
  protected int getUniqueID()
  {
    while (this.ID == 0)
    {
      Random localRandom = new Random(new Date().getTime());
      Integer localInteger = new Integer(Math.abs(localRandom.nextInt()) * 10000 % 10000);
      this.ID = localInteger.intValue();
    }
    return this.ID;
  }
  
  public String getID()
  {
    this.IDStr = "";
    if ((this.symbol != null) && (this.symbol.length() > 0)) {
      this.IDStr = (this.IDStr + this.symbol + "_");
    }
    String str = getAmount();
    if ((str != null) && (str.length() > 0)) {
      this.IDStr = (this.IDStr + str + "_");
    }
    if ((this.criteria != null) && (this.criteria.length() > 0)) {
      this.IDStr = (this.IDStr + this.criteria + "_");
    }
    if ((this.onetime != null) && (this.onetime.length() > 0)) {
      this.IDStr = (this.IDStr + this.onetime + "_");
    }
    this.IDStr = this.IDStr.replace(' ', '_');
    this.IDStr = this.IDStr.replace('\t', '_');
    this.IDStr = this.IDStr.replace('[', '_');
    this.IDStr = this.IDStr.replace(']', '_');
    this.IDStr = this.IDStr.replace('(', '_');
    this.IDStr = this.IDStr.replace(')', '_');
    this.IDStr = this.IDStr.replace('=', '_');
    this.IDStr = this.IDStr.replace(',', '_');
    this.IDStr = this.IDStr.replace('"', '_');
    this.IDStr = this.IDStr.replace('\\', '_');
    this.IDStr = this.IDStr.replace('/', '_');
    this.IDStr = this.IDStr.replace('?', '_');
    this.IDStr = this.IDStr.replace('@', '_');
    this.IDStr = this.IDStr.replace(':', '_');
    this.IDStr = this.IDStr.replace(';', '_');
    return this.IDStr;
  }
  
  public String getSymbol()
  {
    return this.symbol;
  }
  
  public void setSymbol(String paramString)
  {
    this.symbol = paramString;
  }
  
  public String getAmount()
  {
    return String.valueOf(this.amount);
  }
  
  public double getAmountValue()
  {
    return this.amount;
  }
  
  public void setAmount(String paramString)
  {
    if (paramString != null) {
      this.amount = Double.parseDouble(paramString);
    }
  }
  
  public String getCriteria()
  {
    return this.criteria;
  }
  
  public void setCriteria(String paramString)
  {
    this.criteria = paramString;
  }
  
  public String getOnetime()
  {
    return this.onetime;
  }
  
  public void setOnetime(String paramString)
  {
    this.onetime = paramString;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "Amount");
  }
  
  public boolean equals(Object paramObject)
  {
    AlertStock localAlertStock = null;
    try
    {
      localAlertStock = (AlertStock)paramObject;
    }
    catch (Exception localException)
    {
      return false;
    }
    String str1 = getID();
    String str2 = localAlertStock.getID();
    return str1.equalsIgnoreCase(str2);
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Collator localCollator = doGetCollator();
    AlertStock localAlertStock = (AlertStock)paramObject;
    int i = 1;
    if (paramString.equalsIgnoreCase("Symbol")) {
      i = localCollator.compare(this.symbol, localAlertStock.getSymbol());
    } else if (paramString.equalsIgnoreCase("Amount"))
    {
      if (getAmountValue() - localAlertStock.getAmountValue() == 0.0D) {
        i = 0;
      } else if (getAmountValue() - localAlertStock.getAmountValue() < 0.0D) {
        i = -1;
      }
    }
    else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public void set(AlertStock paramAlertStock)
  {
    if ((this == paramAlertStock) || (paramAlertStock == null)) {
      return;
    }
    setAmount(paramAlertStock.getAmount());
    setSymbol(paramAlertStock.getSymbol());
    setOnetime(paramAlertStock.getOnetime());
    setCriteria(paramAlertStock.getCriteria());
    super.set(paramAlertStock);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equalsIgnoreCase("Symbol")) {
        this.symbol = paramString2;
      } else if (paramString1.equalsIgnoreCase("Onetime")) {
        this.onetime = paramString2;
      } else if (paramString1.equalsIgnoreCase("Amount"))
      {
        if (paramString2 != null) {
          this.amount = Double.parseDouble(paramString2);
        }
      }
      else if (paramString1.equalsIgnoreCase("Onetime")) {
        this.onetime = paramString2;
      } else if (paramString1.equalsIgnoreCase("Criteria")) {
        this.criteria = paramString2;
      } else {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception:", localException);
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
    XMLHandler.appendBeginTag(localStringBuffer, "ALERTSTOCK");
    XMLHandler.appendTag(localStringBuffer, "Symbol", this.symbol);
    XMLHandler.appendTag(localStringBuffer, "Criteria", this.criteria);
    XMLHandler.appendTag(localStringBuffer, "Amount", this.amount);
    XMLHandler.appendTag(localStringBuffer, "Onetime", this.onetime);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ALERTSTOCK");
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
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      AlertStock.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.alerts.AlertStock
 * JD-Core Version:    0.7.0.1
 */