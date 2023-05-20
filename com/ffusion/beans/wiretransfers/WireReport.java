package com.ffusion.beans.wiretransfers;

import com.ffusion.beans.Currency;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.Sortable;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class WireReport
  extends ExtendABean
  implements WireReportConsts, IReportResult, ExportFormats, Sortable, Serializable
{
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  protected WireTransfers _wires = null;
  protected Currency total;
  
  public WireReport(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this._wires != null) {
      this._wires.setLocale(paramLocale);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof WireReport))) {
      return false;
    }
    WireReport localWireReport = (WireReport)paramObject;
    WireTransfers localWireTransfers = localWireReport.getWires();
    if (this._wires == null) {
      return localWireTransfers == null;
    }
    return this._wires.equals(localWireTransfers);
  }
  
  public int compare(Object paramObject, String paramString)
  {
    WireReport localWireReport = (WireReport)paramObject;
    int i = 1;
    if ((paramString.equals("AMOUNT")) && (this.total != null) && (localWireReport.getAmountValue() != null)) {
      i = this.total.compareTo(localWireReport.getAmountValue());
    } else if ((paramString.equals("WIRE_PAYEE")) && (localWireReport.getWires().size() > 0) && (((WireTransfer)localWireReport.getWires().get(0)).getWirePayee().getPayeeName() != null) && (getWires().size() > 0) && (((WireTransfer)getWires().get(0)).getWirePayee().getPayeeName() != null)) {
      i = ((WireTransfer)getWires().get(0)).getWirePayee().getPayeeName().compareTo(((WireTransfer)localWireReport.getWires().get(0)).getWirePayee().getPayeeName());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public void setWires(WireTransfers paramWireTransfers)
  {
    this._wires = paramWireTransfers;
  }
  
  public WireTransfers getWires()
  {
    return this._wires;
  }
  
  public void setTotal(Currency paramCurrency)
  {
    this.total = paramCurrency;
  }
  
  public String getTotal()
  {
    if (this.total != null) {
      return this.total.toString();
    }
    return "";
  }
  
  public Currency getAmountValue()
  {
    return this.total;
  }
  
  public Object export(String paramString, HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = null;
    if (paramString.equals("COMMA")) {
      localStringBuffer = getDelimitedReport(',');
    } else if (paramString.equals("TAB")) {
      localStringBuffer = getDelimitedReport('\t');
    }
    return localStringBuffer;
  }
  
  public void set(WireReport paramWireReport)
  {
    super.set(paramWireReport);
    setWires(paramWireReport.getWires());
    setLocale(paramWireReport.locale);
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
    XMLHandler.appendBeginTag(localStringBuffer, "WIRE_REPORT");
    if (this._wires != null) {
      XMLHandler.appendTag(localStringBuffer, "WIRE_TRANSFERS", this._wires.getXML());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "WIRE_REPORT");
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
  
  protected StringBuffer getDelimitedReport(char paramChar)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this._wires != null)
    {
      localStringBuffer.append(ReportConsts.getColumnName(600, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(605, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(601, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(602, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(603, this.locale));
      localStringBuffer.append(_lineSeparator);
      Iterator localIterator = this._wires.iterator();
      WireTransfer localWireTransfer = null;
      while (localIterator.hasNext())
      {
        localWireTransfer = (WireTransfer)localIterator.next();
        appendString(localStringBuffer, localWireTransfer.getDateToPost());
        localStringBuffer.append(paramChar);
        appendString(localStringBuffer, localWireTransfer.getFromAccountNum());
        localStringBuffer.append(paramChar);
        if (localWireTransfer.getWireSource().equals("HOST")) {
          appendString(localStringBuffer, "HOST");
        } else {
          appendString(localStringBuffer, localWireTransfer.getWirePayee().getPayeeName());
        }
        localStringBuffer.append(paramChar);
        appendString(localStringBuffer, localWireTransfer.getAmount());
        localStringBuffer.append(paramChar);
        appendString(localStringBuffer, localWireTransfer.getStatusName());
        localStringBuffer.append(_lineSeparator);
      }
    }
    return localStringBuffer;
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("WIRE_TRANSFERS"))
      {
        if (WireReport.this._wires == null) {
          WireReport.this._wires = new WireTransfers(WireReport.this.locale);
        }
        WireReport.this._wires.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.wiretransfers.WireReport
 * JD-Core Version:    0.7.0.1
 */