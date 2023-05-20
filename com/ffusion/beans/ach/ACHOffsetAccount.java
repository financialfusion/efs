package com.ffusion.beans.ach;

import com.ffusion.beans.Currency;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.XMLStrings;
import com.ffusion.beans.util.BeansConverter;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Sortable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.util.Locale;

public class ACHOffsetAccount
  extends ExtendABean
  implements ACHAccountTypes, XMLStrings, XMLable, Sortable, Cloneable
{
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.ach.resources";
  protected String id;
  protected String number;
  protected String nickName;
  protected int type = 1;
  protected String routingNumber;
  protected long totalCreditAmount;
  protected long totalDebitAmount;
  protected int totalNumberCredits;
  protected int totalNumberDebits;
  
  public ACHOffsetAccount() {}
  
  public ACHOffsetAccount(String paramString)
  {
    this.number = paramString;
  }
  
  public ACHOffsetAccount(Locale paramLocale)
  {
    this.locale = paramLocale;
  }
  
  public ACHOffsetAccount(String paramString1, String paramString2, int paramInt, String paramString3)
  {
    this.number = paramString1;
    this.nickName = paramString2;
    this.type = paramInt;
    this.routingNumber = paramString3;
  }
  
  public String getID()
  {
    return this.id;
  }
  
  public void setID(String paramString)
  {
    this.id = paramString;
  }
  
  public String getNumber()
  {
    return this.number;
  }
  
  public void setNumber(String paramString)
  {
    this.number = paramString;
  }
  
  public void setNickName(String paramString)
  {
    this.nickName = paramString;
  }
  
  public void setType(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      setType(i);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      for (int j = 1; j <= 4; j++)
      {
        String str = ResourceUtil.getString("ACHAccountType" + j, "com.ffusion.beans.ach.resources", this.locale);
        if (str.equalsIgnoreCase(paramString))
        {
          setType(j);
          break;
        }
      }
    }
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
  
  public String getType()
  {
    return ResourceUtil.getString("ACHAccountType" + this.type, "com.ffusion.beans.ach.resources", this.locale);
  }
  
  public int getTypeValue()
  {
    return this.type;
  }
  
  public String getNickName()
  {
    return this.nickName;
  }
  
  public String getRoutingNum()
  {
    return this.routingNumber;
  }
  
  public void setRoutingNum(String paramString)
  {
    this.routingNumber = paramString;
  }
  
  public void setTotalCreditAmount(long paramLong)
  {
    this.totalCreditAmount = paramLong;
  }
  
  public void addTotalCreditAmount(long paramLong)
  {
    this.totalCreditAmount += paramLong;
  }
  
  public String getTotalCreditAmount()
  {
    return new Currency(new BigDecimal(BeansConverter.getAmountAddDecimal("" + this.totalCreditAmount)), this.locale).toString();
  }
  
  public long getTotalCreditAmountValue()
  {
    return this.totalCreditAmount;
  }
  
  public void setTotalDebitAmount(long paramLong)
  {
    this.totalDebitAmount = paramLong;
  }
  
  public void addTotalDebitAmount(long paramLong)
  {
    this.totalDebitAmount += paramLong;
  }
  
  public String getTotalDebitAmount()
  {
    return new Currency(new BigDecimal(BeansConverter.getAmountAddDecimal("" + this.totalDebitAmount)), this.locale).toString();
  }
  
  public long getTotalDebitAmountValue()
  {
    return this.totalDebitAmount;
  }
  
  public int getTotalNumberCredits()
  {
    return this.totalNumberCredits;
  }
  
  public void setTotalNumberCredits(int paramInt)
  {
    this.totalNumberCredits = paramInt;
  }
  
  public void setTotalNumberCredits(String paramString)
  {
    try
    {
      setTotalNumberCredits(Integer.valueOf(paramString).intValue());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      setTotalNumberCredits(0);
    }
  }
  
  public int getTotalNumberDebits()
  {
    return this.totalNumberDebits;
  }
  
  public void setTotalNumberDebits(int paramInt)
  {
    this.totalNumberDebits = paramInt;
  }
  
  public void setTotalNumberDebits(String paramString)
  {
    try
    {
      setTotalNumberDebits(Integer.valueOf(paramString).intValue());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      setTotalNumberDebits(0);
    }
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "OFFSETACCOUNT");
    XMLHandler.appendTag(localStringBuffer, "ID", this.id);
    XMLHandler.appendTag(localStringBuffer, "OFFSETACCOUNTNUMBER", this.number);
    XMLHandler.appendTag(localStringBuffer, "OFFSETACCOUNTNAME", this.nickName);
    XMLHandler.appendTag(localStringBuffer, "OFFSETACCOUNTTYPE", getType());
    XMLHandler.appendTag(localStringBuffer, "OFFSETACCOUNTBANKID", this.routingNumber);
    XMLHandler.appendTag(localStringBuffer, "TOTAL_CREDIT", "" + this.totalCreditAmount);
    XMLHandler.appendTag(localStringBuffer, "TOTAL_DEBIT", "" + this.totalDebitAmount);
    XMLHandler.appendTag(localStringBuffer, "TOTAL_NUM_CREDIT", this.totalNumberCredits);
    XMLHandler.appendTag(localStringBuffer, "TOTAL_NUM_DEBIT", this.totalNumberDebits);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "OFFSETACCOUNT");
    return localStringBuffer.toString();
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("OFFSETACCOUNTNUMBER")) {
        this.number = paramString2;
      } else if (paramString1.equals("OFFSETACCOUNTNAME")) {
        this.nickName = paramString2;
      } else if (paramString1.equals("OFFSETACCOUNTTYPE")) {
        setType(paramString2);
      } else if (paramString1.equals("OFFSETACCOUNTBANKID")) {
        this.routingNumber = paramString2;
      } else if (paramString1.equals("ID")) {
        this.id = paramString2;
      } else if (paramString1.equalsIgnoreCase("TOTAL_CREDIT")) {
        try
        {
          setTotalCreditAmount(Long.valueOf(paramString2).longValue());
        }
        catch (Exception localException1) {}
      } else if (paramString1.equalsIgnoreCase("TOTAL_DEBIT")) {
        try
        {
          setTotalDebitAmount(Long.valueOf(paramString2).longValue());
        }
        catch (Exception localException2) {}
      } else if (paramString1.equalsIgnoreCase("TOTAL_NUM_CREDIT")) {
        setTotalNumberCredits(paramString2);
      } else if (paramString1.equalsIgnoreCase("TOTAL_NUM_DEBIT")) {
        setTotalNumberDebits(paramString2);
      } else {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException3)
    {
      DebugLog.throwing("Exception", localException3);
    }
    return bool;
  }
  
  public boolean set(ACHOffsetAccount paramACHOffsetAccount)
  {
    boolean bool = true;
    if ((paramACHOffsetAccount == null) || (paramACHOffsetAccount == this)) {
      return true;
    }
    try
    {
      this.id = paramACHOffsetAccount.getID();
      this.number = paramACHOffsetAccount.getNumber();
      this.nickName = paramACHOffsetAccount.getNickName();
      this.type = paramACHOffsetAccount.getTypeValue();
      this.routingNumber = paramACHOffsetAccount.getRoutingNum();
      this.id = paramACHOffsetAccount.getID();
      this.totalCreditAmount = paramACHOffsetAccount.getTotalCreditAmountValue();
      this.totalDebitAmount = paramACHOffsetAccount.getTotalDebitAmountValue();
      this.totalNumberCredits = paramACHOffsetAccount.getTotalNumberCredits();
      this.totalNumberDebits = paramACHOffsetAccount.getTotalNumberDebits();
      super.set(paramACHOffsetAccount);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception", localException);
    }
    return bool;
  }
  
  public Object clone()
  {
    try
    {
      ACHOffsetAccount localACHOffsetAccount = (ACHOffsetAccount)super.clone();
      return localACHOffsetAccount;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ACHOffsetAccount localACHOffsetAccount = (ACHOffsetAccount)paramObject;
    int i = 1;
    if ((paramString.equals("OFFSETACCOUNTNUMBER")) && (this.number != null) && (localACHOffsetAccount.getNumber() != null)) {
      i = numStringCompare(this.number, localACHOffsetAccount.getNumber());
    } else if ((paramString.equals("ID")) && (this.id != null) && (localACHOffsetAccount.getID() != null)) {
      i = this.id.compareTo(localACHOffsetAccount.getID());
    } else if ((paramString.equals("OFFSETACCOUNTNAME")) && (this.nickName != null) && (localACHOffsetAccount.getNickName() != null)) {
      i = this.nickName.compareToIgnoreCase(localACHOffsetAccount.getNickName());
    } else if (paramString.equals("OFFSETACCOUNTTYPE")) {
      i = getTypeValue() - localACHOffsetAccount.getTypeValue();
    } else if (paramString.equals("OFFSETACCOUNTTYPESTRING")) {
      i = getType().compareTo(localACHOffsetAccount.getType());
    } else if ((paramString.equals("OFFSETACCOUNTBANKID")) && (this.routingNumber != null) && (localACHOffsetAccount.getRoutingNum() != null)) {
      i = numStringCompare(this.routingNumber, localACHOffsetAccount.getRoutingNum());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public String toXML()
  {
    return getXML();
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
      localThrowable.printStackTrace();
    }
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
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
      ACHOffsetAccount.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.ACHOffsetAccount
 * JD-Core Version:    0.7.0.1
 */