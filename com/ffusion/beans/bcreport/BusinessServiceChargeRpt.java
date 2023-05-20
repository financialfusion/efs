package com.ffusion.beans.bcreport;

import com.ffusion.beans.Currency;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.beans.LocaleableBean;
import java.util.HashMap;
import java.util.Iterator;

public class BusinessServiceChargeRpt
  extends LocaleableBean
  implements IReportResult, ExportFormats
{
  private static String SE = System.getProperty("line.separator", "\n");
  private BusinessServiceChargeEntries SD;
  
  public BusinessServiceChargeRpt(BusinessServiceChargeEntries paramBusinessServiceChargeEntries)
  {
    this.SD = paramBusinessServiceChargeEntries;
  }
  
  public BusinessServiceChargeEntries getBusinessServiceChargeEntries()
  {
    return this.SD;
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
    if (this.SD != null)
    {
      localStringBuffer.append(ReportConsts.getColumnName(400, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(401, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(402, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(403, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(404, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(405, this.locale));
      localStringBuffer.append(SE);
      String str1 = "";
      String str2 = "";
      String str3 = "";
      Iterator localIterator1 = this.SD.iterator();
      while (localIterator1.hasNext())
      {
        BusinessServiceChargeEntry localBusinessServiceChargeEntry = (BusinessServiceChargeEntry)localIterator1.next();
        if (!localBusinessServiceChargeEntry.getMarketSegment().equals(str1))
        {
          str1 = localBusinessServiceChargeEntry.getMarketSegment();
          str2 = "";
          str3 = "";
          localStringBuffer.append(localBusinessServiceChargeEntry.getMarketSegment());
          localStringBuffer.append(SE);
        }
        if (!localBusinessServiceChargeEntry.getServicePackage().equals(str2))
        {
          str2 = localBusinessServiceChargeEntry.getServicePackage();
          str3 = "";
          jdMethod_for(1, localStringBuffer, paramChar);
          localStringBuffer.append(localBusinessServiceChargeEntry.getServicePackage());
          localStringBuffer.append(SE);
        }
        if (!localBusinessServiceChargeEntry.getBusiness().equals(str3))
        {
          str3 = localBusinessServiceChargeEntry.getBusiness();
          jdMethod_for(2, localStringBuffer, paramChar);
          localStringBuffer.append(localBusinessServiceChargeEntry.getBusiness());
          localStringBuffer.append(SE);
        }
        Iterator localIterator2 = localBusinessServiceChargeEntry.getServiceCharges().iterator();
        while (localIterator2.hasNext())
        {
          ServiceChargeEntry localServiceChargeEntry = (ServiceChargeEntry)localIterator2.next();
          jdMethod_for(3, localStringBuffer, paramChar);
          localStringBuffer.append(localServiceChargeEntry.getServiceCode());
          localStringBuffer.append(paramChar);
          localStringBuffer.append(localServiceChargeEntry.getNumTransactions());
          localStringBuffer.append(paramChar);
          jdMethod_long(localStringBuffer, localServiceChargeEntry.getTotalTransactionValue());
          localStringBuffer.append(SE);
        }
      }
    }
    return localStringBuffer;
  }
  
  private void jdMethod_for(int paramInt, StringBuffer paramStringBuffer, char paramChar)
  {
    for (int i = 0; i < paramInt; i++) {
      paramStringBuffer.append(paramChar);
    }
  }
  
  private String jdMethod_for(String paramString, char paramChar)
  {
    char[] arrayOfChar = paramString.toCharArray();
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    for (int j = i; j < arrayOfChar.length; j++) {
      if (arrayOfChar[j] != paramChar) {
        localStringBuffer.append(arrayOfChar[j]);
      }
    }
    return localStringBuffer.toString();
  }
  
  private void jdMethod_long(StringBuffer paramStringBuffer, Currency paramCurrency)
  {
    if (paramCurrency != null)
    {
      paramCurrency.setFormat("DECIMAL");
      paramStringBuffer.append(paramCurrency.toString());
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bcreport.BusinessServiceChargeRpt
 * JD-Core Version:    0.7.0.1
 */