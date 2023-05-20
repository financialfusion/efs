package com.ffusion.beans.ach;

import com.ffusion.beans.Currency;
import com.ffusion.beans.ExtendABean;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import com.ffusion.util.logging.DebugLog;
import java.util.Locale;

public class ACHCompanySummary
  extends ExtendABean
  implements Localeable, XMLable
{
  protected String companyID;
  protected long numBatches;
  protected long numBatchEntries;
  protected Currency totalDebit;
  protected Currency totalCredit;
  protected Currency grandTotal;
  
  public ACHCompanySummary(String paramString)
  {
    this.companyID = paramString;
  }
  
  public ACHCompanySummary(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public String getCompanyID()
  {
    return this.companyID;
  }
  
  public void setCompanyID(String paramString)
  {
    this.companyID = paramString;
  }
  
  public void setTotalDebit(Currency paramCurrency)
  {
    this.totalDebit = paramCurrency;
  }
  
  public Currency getTotalDebit()
  {
    return this.totalDebit;
  }
  
  public void setTotalCredit(Currency paramCurrency)
  {
    this.totalCredit = paramCurrency;
  }
  
  public Currency getTotalCredit()
  {
    return this.totalCredit;
  }
  
  public void setGrandTotal(Currency paramCurrency)
  {
    this.grandTotal = paramCurrency;
  }
  
  public Currency getGrandTotal()
  {
    return this.grandTotal;
  }
  
  public long getNumBatches()
  {
    return this.numBatches;
  }
  
  public void setNumBatches(long paramLong)
  {
    this.numBatches = paramLong;
  }
  
  public long getNumBatchEntries()
  {
    return this.numBatchEntries;
  }
  
  public void setNumBatchEntries(long paramLong)
  {
    this.numBatchEntries = paramLong;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ACHCOMPANYSUMMARY");
    XMLHandler.appendTag(localStringBuffer, "COID", this.companyID);
    XMLHandler.appendTag(localStringBuffer, "COSUMMARYNUMBATCHES", this.numBatches);
    XMLHandler.appendTag(localStringBuffer, "COSUMMARYNUMENTRIES", this.numBatchEntries);
    XMLHandler.appendTag(localStringBuffer, "COSUMMARYTOTALDEBIT", this.totalDebit.doubleValue());
    XMLHandler.appendTag(localStringBuffer, "COSUMMARYTOTALCREDIT", this.totalCredit.doubleValue());
    XMLHandler.appendTag(localStringBuffer, "COSUMMARYGRANDTOTAL", this.grandTotal.doubleValue());
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ACHCOMPANYSUMMARY");
    return localStringBuffer.toString();
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("COID")) {
        this.companyID = paramString2;
      } else if (paramString1.equals("COSUMMARYNUMBATCHES")) {
        this.numBatches = Long.parseLong(paramString2);
      } else if (paramString1.equals("COSUMMARYNUMENTRIES")) {
        this.numBatchEntries = Long.parseLong(paramString2);
      } else if (paramString1.equals("COSUMMARYTOTALDEBIT")) {
        this.totalDebit = new Currency(paramString2, this.locale);
      } else if (paramString1.equals("COSUMMARYTOTALCREDIT")) {
        this.totalCredit = new Currency(paramString2, this.locale);
      } else if (paramString1.equals("COSUMMARYGRANDTOTAL")) {
        this.grandTotal = new Currency(paramString2, this.locale);
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
  
  public boolean set(ACHCompanySummary paramACHCompanySummary)
  {
    boolean bool = true;
    try
    {
      this.companyID = paramACHCompanySummary.getCompanyID();
      this.numBatches = paramACHCompanySummary.getNumBatches();
      this.numBatchEntries = paramACHCompanySummary.getNumBatchEntries();
      this.totalDebit = paramACHCompanySummary.getTotalDebit();
      this.totalCredit = paramACHCompanySummary.getTotalCredit();
      this.grandTotal = paramACHCompanySummary.getGrandTotal();
      super.set(paramACHCompanySummary);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception", localException);
    }
    return bool;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ACHCompanySummary localACHCompanySummary = (ACHCompanySummary)paramObject;
    int i = 1;
    if ((paramString.equals("COID")) && (this.companyID != null) && (localACHCompanySummary.getCompanyID() != null))
    {
      i = this.companyID.compareTo(localACHCompanySummary.getCompanyID());
    }
    else
    {
      long l;
      if (paramString.equals("COSUMMARYNUMBATCHES"))
      {
        l = this.numBatches - localACHCompanySummary.getNumBatches();
        i = l == 0L ? 0 : l < 0L ? -1 : 1;
      }
      else if (paramString.equals("COSUMMARYNUMENTRIES"))
      {
        l = this.numBatchEntries - localACHCompanySummary.getNumBatchEntries();
        i = l == 0L ? 0 : l < 0L ? -1 : 1;
      }
      else if ((paramString.equals("COSUMMARYTOTALDEBIT")) && (this.totalDebit != null) && (localACHCompanySummary.getTotalDebit() != null))
      {
        i = this.totalDebit.compareTo(localACHCompanySummary.getTotalDebit());
      }
      else if ((paramString.equals("COSUMMARYTOTALCREDIT")) && (this.totalCredit != null) && (localACHCompanySummary.getTotalCredit() != null))
      {
        i = this.totalCredit.compareTo(localACHCompanySummary.getTotalCredit());
      }
      else if ((paramString.equals("COSUMMARYGRANDTOTAL")) && (this.grandTotal != null) && (localACHCompanySummary.getGrandTotal() != null))
      {
        i = this.grandTotal.compareTo(localACHCompanySummary.getGrandTotal());
      }
      else
      {
        i = super.compare(paramObject, paramString);
      }
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
      ACHCompanySummary.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      super.startElement(paramString);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.ACHCompanySummary
 * JD-Core Version:    0.7.0.1
 */