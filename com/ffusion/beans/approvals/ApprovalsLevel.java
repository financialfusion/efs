package com.ffusion.beans.approvals;

import com.ffusion.beans.Currency;
import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.Locale;
import java.util.StringTokenizer;

public class ApprovalsLevel
  extends ExtendABean
{
  private int aZt;
  private String aZv;
  private int aZy;
  private Currency aZu;
  private Currency aZx;
  private String aZs;
  private int aZw;
  
  public int getLevelID()
  {
    return this.aZt;
  }
  
  public void setLevelID(int paramInt)
  {
    this.aZt = paramInt;
  }
  
  public String getLevelType()
  {
    return this.aZv;
  }
  
  public void setLevelType(String paramString)
  {
    this.aZv = paramString;
  }
  
  public int getBusinessID()
  {
    return this.aZy;
  }
  
  public void setBusinessID(int paramInt)
  {
    this.aZy = paramInt;
  }
  
  public Currency getMinAmount()
  {
    return this.aZu;
  }
  
  public void setMinAmount(Currency paramCurrency)
  {
    this.aZu = paramCurrency;
  }
  
  public Currency getMaxAmount()
  {
    return this.aZx;
  }
  
  public void setMaxAmount(Currency paramCurrency)
  {
    this.aZx = paramCurrency;
  }
  
  public String getOperationType()
  {
    return this.aZs;
  }
  
  public void setOperationType(String paramString)
  {
    this.aZs = paramString;
  }
  
  public int getObjectType()
  {
    return this.aZw;
  }
  
  public void setObjectType(int paramInt)
  {
    this.aZw = paramInt;
  }
  
  public ApprovalsLevel(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.aZu != null) {
      this.aZu.setLocale(paramLocale);
    }
    if (this.aZx != null) {
      this.aZx.setLocale(paramLocale);
    }
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() == 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      int i;
      if (str1.equalsIgnoreCase("LEVELID"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          return false;
        }
        return this.aZt == i;
      }
      if (str1.equalsIgnoreCase("LEVELTYPE")) {
        return this.aZv.equals(str2);
      }
      if (str1.equalsIgnoreCase("OPERATIONTYPE")) {
        return this.aZs.equals(str2);
      }
      if (str1.equalsIgnoreCase("BUSINESSID"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          return false;
        }
        return this.aZy == i;
      }
      if (str1.equalsIgnoreCase("OBJECT_TYPE"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException3)
        {
          return false;
        }
        return this.aZw == i;
      }
      if (str1.equalsIgnoreCase("MINAMOUNT")) {
        return this.aZu.equals(new BigDecimal(str2));
      }
      if (str1.equalsIgnoreCase("MAXAMOUNT")) {
        return this.aZx.equals(new BigDecimal(str2));
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "MINAMOUNT");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ApprovalsLevel localApprovalsLevel = (ApprovalsLevel)paramObject;
    int i = 1;
    if ((paramString.equals("MINAMOUNT")) && (this.aZu != null) && (localApprovalsLevel.getMinAmount() != null))
    {
      i = this.aZu.compareTo(localApprovalsLevel.getMinAmount());
    }
    else if ((paramString.equals("MAXAMOUNT")) && (this.aZx != null) && (localApprovalsLevel.getMaxAmount() != null))
    {
      i = this.aZx.compareTo(localApprovalsLevel.getMaxAmount());
    }
    else if (paramString.equals("LEVELID"))
    {
      i = this.aZt - localApprovalsLevel.getLevelID();
    }
    else
    {
      Collator localCollator;
      if (paramString.equals("LEVELTYPE"))
      {
        localCollator = doGetCollator();
        i = localCollator.compare(this.aZv, localApprovalsLevel.getLevelType());
      }
      else if (paramString.equals("BUSINESSID"))
      {
        i = this.aZy - localApprovalsLevel.getBusinessID();
      }
      else if (paramString.equals("OBJECT_TYPE"))
      {
        i = this.aZw - localApprovalsLevel.getObjectType();
      }
      else if (paramString.equals("OPERATIONTYPE"))
      {
        localCollator = doGetCollator();
        i = localCollator.compare(this.aZs, localApprovalsLevel.getOperationType());
      }
      else
      {
        i = super.compare(paramObject, paramString);
      }
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsLevel)) {
      return false;
    }
    ApprovalsLevel localApprovalsLevel = (ApprovalsLevel)paramObject;
    return (this.aZt == localApprovalsLevel.getLevelID()) && (areObjectsEqual(this.aZv, localApprovalsLevel.getLevelType())) && (this.aZy == localApprovalsLevel.getBusinessID()) && (this.aZw == localApprovalsLevel.getObjectType()) && (areObjectsEqual(this.aZu, localApprovalsLevel.getMinAmount())) && (areObjectsEqual(this.aZs, localApprovalsLevel.getOperationType())) && (areObjectsEqual(this.aZx, localApprovalsLevel.getMaxAmount()));
  }
  
  public void set(ApprovalsLevel paramApprovalsLevel)
  {
    super.set(paramApprovalsLevel);
    setLevelID(paramApprovalsLevel.getLevelID());
    setLevelType(paramApprovalsLevel.getLevelType());
    setBusinessID(paramApprovalsLevel.getBusinessID());
    setObjectType(paramApprovalsLevel.getObjectType());
    setMinAmount(paramApprovalsLevel.getMinAmount());
    setMaxAmount(paramApprovalsLevel.getMaxAmount());
    setLocale(paramApprovalsLevel.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("LEVELID")) {
        this.aZt = Integer.parseInt(paramString2);
      } else if (paramString1.equals("LEVELTYPE")) {
        this.aZv = paramString2;
      } else if (paramString1.equals("BUSINESSID")) {
        this.aZy = Integer.parseInt(paramString2);
      } else if (paramString1.equals("OBJECT_TYPE")) {
        this.aZw = Integer.parseInt(paramString2);
      } else if (paramString1.equals("MINAMOUNT")) {
        this.aZu = new Currency(paramString2, this.locale);
      } else if (paramString1.equals("MAXAMOUNT")) {
        this.aZx = new Currency(paramString2, this.locale);
      } else if (paramString1.equals("OPERATIONTYPE")) {
        this.aZs = paramString2;
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
    XMLHandler.appendBeginTag(localStringBuffer, "APPROVALS_LEVEL");
    XMLHandler.appendTag(localStringBuffer, "LEVELID", this.aZt);
    XMLHandler.appendTag(localStringBuffer, "LEVELTYPE", this.aZv);
    XMLHandler.appendTag(localStringBuffer, "BUSINESSID", this.aZy);
    XMLHandler.appendTag(localStringBuffer, "OBJECT_TYPE", this.aZw);
    XMLHandler.appendTag(localStringBuffer, "OPERATIONTYPE", this.aZs);
    if (this.aZu != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "MINAMOUNT");
      localStringBuffer.append(this.aZu.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "MINAMOUNT");
    }
    if (this.aZx != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "MAXAMOUNT");
      localStringBuffer.append(this.aZx.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "MAXAMOUNT");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "APPROVALS_LEVEL");
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
  
  public boolean areObjectsEqual(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == paramObject2) {
      return true;
    }
    if ((paramObject1 == null) || (paramObject2 == null)) {
      return false;
    }
    return paramObject1.equals(paramObject2);
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("levelID=").append(this.aZt);
    localStringBuffer.append(" levelType=").append(this.aZv);
    localStringBuffer.append(" operationType=").append(this.aZs);
    localStringBuffer.append(" businessID=").append(this.aZy);
    localStringBuffer.append(" objectType=").append(this.aZw);
    localStringBuffer.append(" minAmount=").append(this.aZu);
    localStringBuffer.append(" maxAmount=").append(this.aZx);
    return localStringBuffer.toString();
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      ApprovalsLevel.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("MINAMOUNT"))
      {
        if (ApprovalsLevel.this.aZu == null)
        {
          ApprovalsLevel.this.aZu = new Currency();
          ApprovalsLevel.this.aZu.setLocale(ApprovalsLevel.this.locale);
        }
        ApprovalsLevel.this.aZu.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("MAXAMOUNT"))
      {
        if (ApprovalsLevel.this.aZx == null)
        {
          ApprovalsLevel.this.aZx = new Currency();
          ApprovalsLevel.this.aZx.setLocale(ApprovalsLevel.this.locale);
        }
        ApprovalsLevel.this.aZx.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsLevel
 * JD-Core Version:    0.7.0.1
 */