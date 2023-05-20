package com.ffusion.beans.lockbox;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class LockboxDepositItemRpt
  extends ExtendABean
  implements LockboxRptConsts, IReportResult, ExportFormats, Serializable
{
  public static final String LB_DEPOSIT_ITEM_RPT = "LB_DEPOSIT_ITEM_RPT";
  public static final String LB_CREDIT_ITEM = "LB_CREDIT_ITEM";
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  private LockboxCreditItems Sh = null;
  
  public LockboxDepositItemRpt(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof LockboxDepositItemRpt))) {
      return false;
    }
    LockboxDepositItemRpt localLockboxDepositItemRpt = (LockboxDepositItemRpt)paramObject;
    LockboxCreditItems localLockboxCreditItems = localLockboxDepositItemRpt.getLockboxCreditItems();
    if (this.Sh == null) {
      return localLockboxCreditItems == null;
    }
    return this.Sh.equals(localLockboxCreditItems);
  }
  
  public void setLockboxCreditItems(LockboxCreditItems paramLockboxCreditItems)
  {
    this.Sh = paramLockboxCreditItems;
  }
  
  public LockboxCreditItems getLockboxCreditItems()
  {
    return this.Sh;
  }
  
  public Object export(String paramString, HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = null;
    if (paramString.equals("COMMA")) {
      localStringBuffer = getDelimitedReport(",");
    } else if (paramString.equals("TAB")) {
      localStringBuffer = getDelimitedReport("\t");
    }
    return localStringBuffer;
  }
  
  public void set(LockboxDepositItemRpt paramLockboxDepositItemRpt)
  {
    super.set(paramLockboxDepositItemRpt);
    setLockboxCreditItems(paramLockboxDepositItemRpt.getLockboxCreditItems());
    setLocale(paramLockboxDepositItemRpt.locale);
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
    XMLHandler.appendBeginTag(localStringBuffer, "LB_DEPOSIT_ITEM_RPT");
    if (this.Sh != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "LB_CREDIT_ITEM");
      localStringBuffer.append(this.Sh.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "LB_CREDIT_ITEM");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "LB_DEPOSIT_ITEM_RPT");
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
  
  public String getDateFormat()
  {
    return super.getDateFormat();
  }
  
  protected StringBuffer getDelimitedReport(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.Sh != null)
    {
      localStringBuffer.append(ReportConsts.getColumnName(35, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(37, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(38, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(39, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(41, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(42, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(43, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(44, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(45, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(46, this.locale));
      localStringBuffer.append(_lineSeparator);
      Iterator localIterator = this.Sh.iterator();
      LockboxCreditItem localLockboxCreditItem = null;
      while (localIterator.hasNext())
      {
        localLockboxCreditItem = (LockboxCreditItem)localIterator.next();
        jdMethod_new(localStringBuffer, localLockboxCreditItem.getProcessingDate(), paramString);
        jdMethod_try(localStringBuffer, localLockboxCreditItem.getPayer(), paramString);
        jdMethod_new(localStringBuffer, localLockboxCreditItem.getCheckAmount(), paramString);
        jdMethod_try(localStringBuffer, localLockboxCreditItem.getCheckNumber(), paramString);
        jdMethod_try(localStringBuffer, localLockboxCreditItem.getCouponAccountNumber(), paramString);
        jdMethod_new(localStringBuffer, localLockboxCreditItem.getCouponAmount1(), paramString);
        jdMethod_new(localStringBuffer, localLockboxCreditItem.getCouponAmount2(), paramString);
        jdMethod_new(localStringBuffer, localLockboxCreditItem.getCouponDate1(), paramString);
        jdMethod_new(localStringBuffer, localLockboxCreditItem.getCouponDate2(), paramString);
        jdMethod_try(localStringBuffer, localLockboxCreditItem.getCouponReferenceNumber(), paramString);
        localStringBuffer.append(_lineSeparator);
      }
    }
    return localStringBuffer;
  }
  
  private void jdMethod_try(StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    if (paramString1 != null) {
      paramStringBuffer.append(paramString1);
    }
    paramStringBuffer.append(paramString2);
  }
  
  private void jdMethod_new(StringBuffer paramStringBuffer, Currency paramCurrency, String paramString)
  {
    if (paramCurrency != null)
    {
      paramCurrency.setFormat("DECIMAL");
      paramStringBuffer.append(paramCurrency.toString());
    }
    paramStringBuffer.append(paramString);
  }
  
  private void jdMethod_new(StringBuffer paramStringBuffer, DateTime paramDateTime, String paramString)
  {
    if (paramDateTime != null) {
      paramStringBuffer.append(paramDateTime.toString());
    }
    paramStringBuffer.append(paramString);
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("LB_CREDIT_ITEM"))
      {
        if (LockboxDepositItemRpt.this.Sh == null) {
          LockboxDepositItemRpt.this.Sh = new LockboxCreditItems(LockboxDepositItemRpt.this.locale);
        }
        LockboxDepositItemRpt.this.Sh.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.lockbox.LockboxDepositItemRpt
 * JD-Core Version:    0.7.0.1
 */