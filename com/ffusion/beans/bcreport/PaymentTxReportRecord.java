package com.ffusion.beans.bcreport;

import com.ffusion.beans.DateTime;
import java.math.BigDecimal;

public class PaymentTxReportRecord
  extends TransactionReportRecord
{
  protected String payeeName;
  protected String paymentId;
  
  public PaymentTxReportRecord(int paramInt, DateTime paramDateTime, String paramString1, String paramString2, String paramString3, String paramString4, BigDecimal paramBigDecimal, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13)
  {
    super(paramInt, paramDateTime, paramString1, paramString2, paramString3, paramString4, paramBigDecimal, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11);
    this.payeeName = paramString12;
    this.paymentId = paramString13;
  }
  
  public String getPayeeName()
  {
    return this.payeeName;
  }
  
  public void setPayeeName(String paramString)
  {
    this.payeeName = paramString;
  }
  
  public String getPaymentId()
  {
    return this.paymentId;
  }
  
  public void setPaymentId(String paramString)
  {
    this.paymentId = paramString;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(super.toString() + ",payeeName=" + getPayeeName() + ",paymentId=" + getPaymentId());
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bcreport.PaymentTxReportRecord
 * JD-Core Version:    0.7.0.1
 */