package com.ffusion.beans.bcreport;

import com.ffusion.beans.Currency;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.reporting.IReportResult;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class ConsumerServiceChargeRpt
  extends ExtendABean
  implements IReportResult, ExportFormats
{
  private static final String SJ = System.getProperty("line.separator", "\n");
  private ConsumerServiceChargeEntries SI;
  
  public ConsumerServiceChargeRpt(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public ConsumerServiceChargeRpt(ConsumerServiceChargeEntries paramConsumerServiceChargeEntries)
  {
    this.SI = paramConsumerServiceChargeEntries;
  }
  
  public void setConsumerServiceChargeEntries(ConsumerServiceChargeEntries paramConsumerServiceChargeEntries)
  {
    this.SI = paramConsumerServiceChargeEntries;
  }
  
  public ConsumerServiceChargeEntries getConsumerServiceChargeEntries()
  {
    return this.SI;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = false;
    if ((paramObject != null) && ((paramObject instanceof ConsumerServiceChargeRpt)))
    {
      ConsumerServiceChargeRpt localConsumerServiceChargeRpt = (ConsumerServiceChargeRpt)paramObject;
      ConsumerServiceChargeEntries localConsumerServiceChargeEntries = localConsumerServiceChargeRpt.getConsumerServiceChargeEntries();
      if (this.SI == null) {
        bool = (bool) && (localConsumerServiceChargeEntries == null);
      } else {
        bool = (bool) && (this.SI.equals(localConsumerServiceChargeEntries));
      }
    }
    return bool;
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
  
  protected StringBuffer getDelimitedReport(char paramChar)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.SI != null)
    {
      Object localObject1 = null;
      Object localObject2 = null;
      Object localObject3 = null;
      int i = 0;
      int j = 1;
      int k = 2;
      int m = 5;
      localStringBuffer.append(ReportConsts.getColumnName(500, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(501, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(502, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(503, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(504, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(505, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(506, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(507, this.locale));
      localStringBuffer.append(SJ);
      Iterator localIterator1 = this.SI.iterator();
      while (localIterator1.hasNext())
      {
        ConsumerServiceChargeEntry localConsumerServiceChargeEntry = (ConsumerServiceChargeEntry)localIterator1.next();
        if (!localConsumerServiceChargeEntry.getMarketSegment().equals(localObject1))
        {
          localObject1 = localConsumerServiceChargeEntry.getMarketSegment();
          localObject2 = null;
          localObject3 = null;
          jdMethod_int(i, localStringBuffer, paramChar);
          localStringBuffer.append((String)localObject1);
          localStringBuffer.append(SJ);
        }
        if (!localConsumerServiceChargeEntry.getServicePackage().equals(localObject2))
        {
          localObject2 = localConsumerServiceChargeEntry.getServicePackage();
          localObject3 = null;
          jdMethod_int(j, localStringBuffer, paramChar);
          localStringBuffer.append((String)localObject2);
          localStringBuffer.append(SJ);
        }
        if (!localConsumerServiceChargeEntry.getCustomerName().equals(localObject3))
        {
          localObject3 = localConsumerServiceChargeEntry.getCustomerName();
          jdMethod_int(k, localStringBuffer, paramChar);
          localStringBuffer.append((String)localObject3);
          localStringBuffer.append(paramChar);
          localStringBuffer.append(localConsumerServiceChargeEntry.getUserName());
          localStringBuffer.append(paramChar);
          localStringBuffer.append(localConsumerServiceChargeEntry.getCustomerNumber());
          localStringBuffer.append(SJ);
        }
        Iterator localIterator2 = localConsumerServiceChargeEntry.getServiceCharges().iterator();
        while (localIterator2.hasNext())
        {
          ServiceChargeEntry localServiceChargeEntry = (ServiceChargeEntry)localIterator2.next();
          jdMethod_int(m, localStringBuffer, paramChar);
          localStringBuffer.append(localServiceChargeEntry.getServiceCode());
          localStringBuffer.append(paramChar);
          localStringBuffer.append(localServiceChargeEntry.getNumTransactions());
          localStringBuffer.append(paramChar);
          jdMethod_null(localStringBuffer, localServiceChargeEntry.getTotalTransactionValue());
          localStringBuffer.append(SJ);
        }
      }
    }
    return localStringBuffer;
  }
  
  private void jdMethod_int(int paramInt, StringBuffer paramStringBuffer, char paramChar)
  {
    for (int i = 0; i < paramInt; i++) {
      paramStringBuffer.append(paramChar);
    }
  }
  
  private void jdMethod_null(StringBuffer paramStringBuffer, Currency paramCurrency)
  {
    if (paramCurrency != null)
    {
      paramCurrency.setFormat("DECIMAL");
      paramStringBuffer.append(paramCurrency.toString());
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bcreport.ConsumerServiceChargeRpt
 * JD-Core Version:    0.7.0.1
 */