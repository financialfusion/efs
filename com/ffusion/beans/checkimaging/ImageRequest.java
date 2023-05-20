package com.ffusion.beans.checkimaging;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.checkimaging.adapters.CheckImageDefines;
import com.ffusion.util.FieldValidation;
import com.ffusion.util.Sortable;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;

public class ImageRequest
  extends ExtendABean
  implements CheckImageDefines, Sortable, Serializable
{
  protected String accountID;
  protected String completeAccountID;
  protected String transType;
  protected String traceIDFrom;
  protected String traceIDTo;
  protected String checkNumberFrom;
  protected String checkNumberTo;
  protected String routingTransitNumber;
  protected String depositTraceId;
  protected String depositAccountID;
  protected String depositAmount = "-1";
  protected String returnCode = "-1";
  protected String onUs;
  protected DateTime postingDateFrom;
  protected DateTime postingDateTo;
  protected Currency amountFrom;
  protected Currency amountTo;
  protected String InvalidPostingDateFrom;
  protected String InvalidPostingDateTo;
  protected String InvalidAmountFrom;
  protected String InvalidAmountTo;
  protected String InvalidCheckNumberFrom;
  protected String InvalidCheckNumberTo;
  protected String datetype = "MM/dd/yyyy";
  private boolean Ws;
  protected String userAmountFrom;
  protected String userAmountTo;
  static final String Wr = "\t\t\t\t<connector>AND</connector>\n";
  
  public ImageRequest() {}
  
  public ImageRequest(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public void setAccountID(String paramString)
  {
    if (paramString != null)
    {
      this.completeAccountID = paramString;
      this.accountID = D(paramString);
    }
  }
  
  public String getAccountID()
  {
    return this.accountID;
  }
  
  public String getCompleteAccountID()
  {
    return this.completeAccountID;
  }
  
  public void setAmountTo(String paramString)
  {
    this.userAmountTo = paramString;
    if (Currency.isValid(paramString, this.locale)) {
      this.InvalidAmountTo = null;
    } else {
      this.InvalidAmountTo = paramString;
    }
    if ((paramString == null) || (paramString.equals(""))) {
      this.amountTo = null;
    } else {
      this.amountTo = new Currency(paramString, this.locale);
    }
  }
  
  public void setAmountTo(Currency paramCurrency)
  {
    this.amountTo = paramCurrency;
  }
  
  public Currency getAmountToValue()
  {
    return this.amountTo;
  }
  
  public String getAmountTo()
  {
    String str;
    try
    {
      str = this.amountTo.toString();
    }
    catch (Exception localException)
    {
      str = "";
    }
    return str;
  }
  
  public String getUserAmountFrom()
  {
    return this.userAmountFrom;
  }
  
  public String getUserAmountTo()
  {
    return this.userAmountTo;
  }
  
  public void setAmountFrom(String paramString)
  {
    this.userAmountFrom = paramString;
    if (Currency.isValid(paramString, this.locale)) {
      this.InvalidAmountFrom = null;
    } else {
      this.InvalidAmountFrom = paramString;
    }
    if (paramString.equals("")) {
      this.amountFrom = null;
    } else {
      this.amountFrom = new Currency(paramString, this.locale);
    }
  }
  
  public void setAmountFrom(Currency paramCurrency)
  {
    this.amountFrom = paramCurrency;
  }
  
  public Currency amountFromValue()
  {
    return this.amountFrom;
  }
  
  public String getAmountFrom()
  {
    String str;
    try
    {
      str = this.amountFrom.toString();
    }
    catch (Exception localException)
    {
      str = "";
    }
    return str;
  }
  
  public void setPostingDateFrom(String paramString)
  {
    if ((paramString == null) || (paramString.equals(""))) {
      this.postingDateFrom = null;
    } else {
      try
      {
        this.postingDateFrom = new DateTime(paramString, this.locale, this.datetype);
        this.InvalidPostingDateFrom = null;
      }
      catch (InvalidDateTimeException localInvalidDateTimeException)
      {
        this.InvalidPostingDateFrom = paramString;
      }
    }
  }
  
  public String getPostingDateFrom()
  {
    String str;
    try
    {
      str = this.postingDateFrom.toString();
    }
    catch (Exception localException)
    {
      str = "";
    }
    return str;
  }
  
  public String getInvalidPostingDateFrom()
  {
    return this.InvalidPostingDateFrom;
  }
  
  public Calendar getPostingDateFromValue()
  {
    return this.postingDateFrom;
  }
  
  public void setPostingDateTo(String paramString)
  {
    if ((paramString == null) || (paramString.equals(""))) {
      this.postingDateTo = null;
    } else {
      try
      {
        this.postingDateTo = new DateTime(paramString, this.locale, this.datetype);
        this.InvalidPostingDateTo = null;
      }
      catch (InvalidDateTimeException localInvalidDateTimeException)
      {
        this.InvalidPostingDateTo = paramString;
      }
    }
  }
  
  public String getPostingDateTo()
  {
    String str;
    try
    {
      str = this.postingDateTo.toString();
    }
    catch (Exception localException)
    {
      str = "";
    }
    return str;
  }
  
  public String getInvalidPostingDateTo()
  {
    return this.InvalidPostingDateTo;
  }
  
  public Calendar getPostingDateToValue()
  {
    return this.postingDateTo;
  }
  
  public void setDateFormat(String paramString)
  {
    try
    {
      this.postingDateFrom.setFormat(paramString);
      this.postingDateTo.setFormat(paramString);
    }
    catch (Exception localException) {}
    this.datetype = paramString;
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public void setAmountFormat(String paramString)
  {
    this.amountFrom.setFormat(paramString);
    this.amountTo.setFormat(paramString);
  }
  
  public String getAmountFormat()
  {
    if (this.amountFrom == null) {
      return "";
    }
    return this.amountFrom.getFormat();
  }
  
  public void setCheckNumberFrom(String paramString)
  {
    if ((paramString == null) || (paramString.equals("")))
    {
      this.checkNumberFrom = null;
    }
    else
    {
      if (FieldValidation.num(paramString, true)) {
        this.InvalidCheckNumberFrom = null;
      } else {
        this.InvalidCheckNumberFrom = paramString;
      }
      this.checkNumberFrom = paramString;
    }
  }
  
  public String getCheckNumberFrom()
  {
    return this.checkNumberFrom;
  }
  
  public void setDepositTraceId(String paramString)
  {
    this.depositTraceId = paramString;
  }
  
  public String getDepositTraceId()
  {
    return this.depositTraceId;
  }
  
  public void setDepositAccountID(String paramString)
  {
    this.depositAccountID = paramString;
  }
  
  public String getDepositAccountID()
  {
    return this.depositAccountID;
  }
  
  public void setDepositAmount(String paramString)
  {
    this.depositAmount = paramString;
  }
  
  public String getDepositAmount()
  {
    return this.depositAmount;
  }
  
  public void setCheckNumberTo(String paramString)
  {
    if ((paramString == null) || (paramString.equals("")))
    {
      this.checkNumberTo = null;
    }
    else
    {
      if (FieldValidation.num(paramString, true)) {
        this.InvalidCheckNumberTo = null;
      } else {
        this.InvalidCheckNumberTo = paramString;
      }
      this.checkNumberTo = paramString;
    }
  }
  
  public String getCheckNumberTo()
  {
    return this.checkNumberTo;
  }
  
  public void setTraceIDFrom(String paramString)
  {
    this.traceIDFrom = paramString;
  }
  
  public String getTraceIDFrom()
  {
    return this.traceIDFrom;
  }
  
  public void setTraceIDTo(String paramString)
  {
    this.traceIDTo = paramString;
  }
  
  public String getTraceIDTo()
  {
    return this.traceIDTo;
  }
  
  public void setOnUs(String paramString)
  {
    this.onUs = paramString;
  }
  
  public String getOnUs()
  {
    return this.onUs;
  }
  
  public void setReturnCode(String paramString)
  {
    this.returnCode = paramString;
  }
  
  public String getReturnCode()
  {
    return this.returnCode;
  }
  
  public void setRoutingTransitNumber(String paramString)
  {
    this.routingTransitNumber = paramString;
  }
  
  public String getRoutingTransitNumber()
  {
    return this.routingTransitNumber;
  }
  
  public void setTransType(String paramString)
  {
    this.transType = paramString;
  }
  
  public String getTransType()
  {
    return this.transType;
  }
  
  private String D(String paramString)
  {
    int i = 0;
    int j = 0;
    String str = "";
    i = paramString.length();
    while ((j < i) && (paramString.charAt(j) == '0')) {
      j++;
    }
    if (j >= i) {
      str = "";
    } else {
      str = paramString.substring(j, i);
    }
    return str;
  }
  
  public String xmlSearchCriteria()
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    StringBuffer localStringBuffer = new StringBuffer(1000);
    if (!checkRequiredFields())
    {
      localStringBuffer.append("ERROR");
    }
    else
    {
      localStringBuffer.append("\t\t\t\t<connector>AND</connector>\n");
      localStringBuffer.append(buildField(this.accountID, "Account"));
      localStringBuffer.append(buildField(this.transType, "Credit/Debit"));
      if ((this.depositAccountID != null) && (this.depositAccountID.trim().length() > 0)) {
        localStringBuffer.append(buildField(this.depositAccountID, "Deposit Account"));
      }
      localStringBuffer.append(buildField(this.routingTransitNumber, "Routing"));
      localStringBuffer.append(buildField(this.depositTraceId, "Deposit Sequence"));
      localStringBuffer.append(buildField(this.onUs, "On Us"));
      str1 = getDateFormat();
      setDateFormat("yyyyMMdd");
      if (this.postingDateFrom != null) {
        str3 = this.postingDateFrom.toString();
      }
      if (this.postingDateTo != null) {
        str4 = this.postingDateTo.toString();
      }
      setDateFormat(str1);
      localStringBuffer.append(buildRange(str3, str4, "Cycle Date"));
      if ((this.traceIDFrom != null) && (this.traceIDFrom.length() == 8)) {
        localStringBuffer.append(buildRange(this.traceIDFrom, this.traceIDTo, "Sequence Number"));
      } else if ((this.traceIDFrom != null) && (this.traceIDFrom.length() == 12)) {
        localStringBuffer.append(buildRange(this.traceIDFrom, this.traceIDTo, "Capture Sequence"));
      }
      if (!this.returnCode.equals("-1")) {
        localStringBuffer.append(buildField(this.returnCode, "Return Item"));
      }
      if ((this.amountTo != null) && (this.amountFrom != null))
      {
        str2 = getAmountFormat();
        setAmountFormat("#.00");
        if (this.amountFrom != null) {
          str5 = this.amountFrom.toString();
        }
        if (this.amountTo != null) {
          str6 = this.amountTo.toString();
        }
        setAmountFormat(str2);
        localStringBuffer.append(buildRange(str5, str6, "Amount"));
      }
      localStringBuffer.append(buildRange(this.checkNumberFrom, this.checkNumberTo, "Serial"));
      if (!this.depositAmount.equals("-1")) {
        localStringBuffer.append(buildField(this.depositAmount, "Deposit Amount"));
      }
    }
    return localStringBuffer.toString();
  }
  
  protected boolean checkRequiredFields()
  {
    int i = 3;
    int j = 0;
    if ((this.accountID != null) && (!this.accountID.equals(""))) {
      j++;
    }
    if ((this.depositAccountID != null) && (!this.depositAccountID.equals(""))) {
      j++;
    }
    if ((this.postingDateFrom != null) && (!this.postingDateFrom.equals(""))) {
      j++;
    }
    if ((this.postingDateTo != null) && (!this.postingDateTo.equals(""))) {
      j++;
    }
    if ((this.traceIDFrom != null) && (!this.traceIDFrom.equals(""))) {
      j++;
    }
    if ((this.traceIDTo != null) && (!this.traceIDTo.equals(""))) {
      j++;
    }
    if ((this.amountFrom != null) && (!this.amountFrom.equals(""))) {
      j++;
    }
    if ((this.amountTo != null) && (!this.amountTo.equals(""))) {
      j++;
    }
    if ((this.routingTransitNumber != null) && (!this.routingTransitNumber.equals(""))) {
      j++;
    }
    if ((this.depositTraceId != null) && (!this.depositTraceId.equals(""))) {
      j++;
    }
    return j >= i;
  }
  
  protected static String buildField(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString1.length() == 0)) {
      return "";
    }
    if ((paramString2 == null) || (paramString2.length() == 0)) {
      return "";
    }
    return generateFieldXml(paramString2, paramString1, null);
  }
  
  protected static String buildRange(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1 == null) && (paramString2 == null)) {
      return "";
    }
    if ((paramString1 == null) || (paramString1.length() == 0)) {
      return "";
    }
    if ((paramString2 == null) || (paramString2.length() == 0)) {
      return "";
    }
    if ((paramString3 == null) || (paramString3.length() == 0)) {
      return "";
    }
    if (paramString1.equalsIgnoreCase(paramString2)) {
      paramString2 = null;
    }
    return generateFieldXml(paramString3, paramString1, paramString2);
  }
  
  protected static String generateFieldXml(String paramString1, String paramString2, String paramString3)
  {
    String str1 = "";
    String str2 = "";
    if ((paramString2 != null) && (paramString2.length() > 0)) {
      str1 = "\t\t\t\t\t<value>" + paramString2 + "</value>\n";
    }
    if ((paramString3 != null) && (paramString3.length() > 0)) {
      str2 = "\t\t\t\t\t<value>" + paramString3 + "</value>\n";
    }
    return new String("\t\t\t\t<field>\n\t\t\t\t\t<name>" + paramString1 + "</name>\n" + "\t\t\t\t\t<operator>" + "BETWEEN" + "</operator>\n" + str1 + str2 + "\t\t\t\t</field>\n");
  }
  
  public String getInvalidAmountFrom()
  {
    return this.InvalidAmountFrom;
  }
  
  public String getInvalidAmountTo()
  {
    return this.InvalidAmountTo;
  }
  
  public String getInvalidCheckNumberFrom()
  {
    return this.InvalidCheckNumberFrom;
  }
  
  public String getInvalidCheckNumberTo()
  {
    return this.InvalidCheckNumberTo;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.checkimaging.ImageRequest
 * JD-Core Version:    0.7.0.1
 */