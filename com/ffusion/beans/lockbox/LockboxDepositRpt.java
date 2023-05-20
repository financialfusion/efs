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

public class LockboxDepositRpt
  extends ExtendABean
  implements LockboxRptConsts, IReportResult, ExportFormats, Serializable
{
  public static final String LB_DEPOSIT_RPT = "LB_DEPOSIT_RPT";
  public static final String LB_TRANSACTIONS = "LB_TRANSACTIONS";
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  private LockboxTransactions Sg = null;
  
  public LockboxDepositRpt(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof LockboxDepositRpt))) {
      return false;
    }
    LockboxDepositRpt localLockboxDepositRpt = (LockboxDepositRpt)paramObject;
    LockboxTransactions localLockboxTransactions = localLockboxDepositRpt.getLockboxTransactions();
    if (this.Sg == null) {
      return localLockboxTransactions == null;
    }
    return this.Sg.equals(localLockboxTransactions);
  }
  
  public void setLockboxTransactions(LockboxTransactions paramLockboxTransactions)
  {
    this.Sg = paramLockboxTransactions;
  }
  
  public LockboxTransactions getLockboxTransactions()
  {
    return this.Sg;
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
  
  public void set(LockboxDepositRpt paramLockboxDepositRpt)
  {
    super.set(paramLockboxDepositRpt);
    setLockboxTransactions(paramLockboxDepositRpt.getLockboxTransactions());
    setLocale(paramLockboxDepositRpt.locale);
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
    XMLHandler.appendBeginTag(localStringBuffer, "LB_DEPOSIT_RPT");
    if (this.Sg != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "LB_TRANSACTIONS");
      localStringBuffer.append(this.Sg.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "LB_TRANSACTIONS");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "LB_DEPOSIT_RPT");
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
  
  protected StringBuffer getDelimitedReport(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.Sg != null)
    {
      localStringBuffer.append(ReportConsts.getColumnName(60, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(66, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(62, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(67, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(69, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(70, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(71, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(72, this.locale));
      localStringBuffer.append(paramString);
      localStringBuffer.append(ReportConsts.getColumnName(73, this.locale));
      localStringBuffer.append(_lineSeparator);
      Iterator localIterator = this.Sg.iterator();
      LockboxTransaction localLockboxTransaction = null;
      while (localIterator.hasNext())
      {
        localLockboxTransaction = (LockboxTransaction)localIterator.next();
        jdMethod_new(localStringBuffer, localLockboxTransaction.getAccountID(), paramString);
        jdMethod_int(localStringBuffer, localLockboxTransaction.getProcessingDate(), paramString);
        jdMethod_new(localStringBuffer, localLockboxTransaction.getLockboxNumber(), paramString);
        jdMethod_int(localStringBuffer, localLockboxTransaction.getAmount(), paramString);
        jdMethod_int(localStringBuffer, localLockboxTransaction.getImmediateFloat(), paramString);
        jdMethod_int(localStringBuffer, localLockboxTransaction.getOneDayFloat(), paramString);
        jdMethod_int(localStringBuffer, localLockboxTransaction.getTwoDayFloat(), paramString);
        jdMethod_int(localStringBuffer, localLockboxTransaction.getNumRejectedChecks(), paramString);
        jdMethod_int(localStringBuffer, localLockboxTransaction.getRejectedAmount(), paramString);
        localStringBuffer.append(_lineSeparator);
      }
    }
    return localStringBuffer;
  }
  
  private static String jdMethod_int(Object paramObject)
  {
    if (paramObject == null) {
      return "0";
    }
    return paramObject.toString();
  }
  
  private void jdMethod_new(StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    if (paramString1 != null) {
      paramStringBuffer.append(paramString1);
    }
    paramStringBuffer.append(paramString2);
  }
  
  private void jdMethod_int(StringBuffer paramStringBuffer, Currency paramCurrency, String paramString)
  {
    if (paramCurrency != null)
    {
      paramCurrency.setFormat("DECIMAL");
      paramStringBuffer.append(paramCurrency.toString());
    }
    paramStringBuffer.append(paramString);
  }
  
  private void jdMethod_int(StringBuffer paramStringBuffer, int paramInt, String paramString)
  {
    paramStringBuffer.append(paramInt);
    paramStringBuffer.append(paramString);
  }
  
  private void jdMethod_int(StringBuffer paramStringBuffer, DateTime paramDateTime, String paramString)
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
      if (paramString.equals("LB_TRANSACTIONS"))
      {
        if (LockboxDepositRpt.this.Sg == null) {
          LockboxDepositRpt.this.Sg = new LockboxTransactions(LockboxDepositRpt.this.locale);
        }
        LockboxDepositRpt.this.Sg.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.lockbox.LockboxDepositRpt
 * JD-Core Version:    0.7.0.1
 */