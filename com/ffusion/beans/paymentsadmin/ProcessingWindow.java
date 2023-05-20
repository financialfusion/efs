package com.ffusion.beans.paymentsadmin;

import com.ffusion.ffs.bpw.interfaces.ProcessingWindowInfo;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.ExtendABean;
import com.ffusion.util.beans.InvalidDateTimeException;
import java.text.Collator;
import java.util.Locale;

public class ProcessingWindow
  extends ExtendABean
  implements Comparable
{
  public static final String BPW_DATEFORMAT = "yyyy/MM/dd";
  public static final String BPW_TIMEFORMAT = "HH:mm:ss";
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.paymentsadmin.resources";
  protected String id;
  protected String bankId;
  protected String customerId;
  protected String startTime;
  protected String closeTime;
  protected String pmtType;
  protected String pmtSubType;
  protected DateTime createDate;
  protected String description;
  protected String submittedBy;
  
  public ProcessingWindow() {}
  
  public ProcessingWindow(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException("Locale cannot be null");
    }
    this.datetype = "SHORT";
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return 0;
  }
  
  public void set(ProcessingWindow paramProcessingWindow)
  {
    if (paramProcessingWindow == null) {
      return;
    }
    setId(paramProcessingWindow.getId());
    setBankId(paramProcessingWindow.getBankId());
    setCustomerId(paramProcessingWindow.getCustomerId());
    setStartTime(paramProcessingWindow.getStartTime());
    setCloseTime(paramProcessingWindow.getCloseTime());
    setPaymentType(paramProcessingWindow.getPaymentType());
    setPaymentSubType(paramProcessingWindow.getPaymentSubType());
    setCreateDate(paramProcessingWindow.getCreateDate());
    setDescription(paramProcessingWindow.getDescription());
    setSubmittedBy(paramProcessingWindow.getSubmittedBy());
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    if (paramString1.equalsIgnoreCase("ID")) {
      setId(paramString2);
    } else if (paramString1.equalsIgnoreCase("BANK_ID")) {
      setBankId(paramString2);
    } else if (paramString1.equalsIgnoreCase("CUSTOMER_ID")) {
      setCustomerId(paramString2);
    } else if (paramString1.equalsIgnoreCase("START_TIME")) {
      setStartTime(paramString2);
    } else if (paramString1.equalsIgnoreCase("CLOSE_TIME")) {
      setCloseTime(paramString2);
    } else if (paramString1.equalsIgnoreCase("PAYMENT_TYPE")) {
      setPaymentType(paramString2);
    } else if (paramString1.equalsIgnoreCase("PAYMENT_SUB_TYPE")) {
      setPaymentSubType(paramString2);
    } else if (paramString1.equalsIgnoreCase("CREATE_DATE")) {
      setCreateDate(paramString2);
    } else if (paramString1.equalsIgnoreCase("DESCRIPTION")) {
      setDescription(paramString2);
    } else if (paramString1.equalsIgnoreCase("SUBMITTED_BY")) {
      setSubmittedBy(paramString2);
    } else {
      return super.set(paramString1, paramString2);
    }
    return true;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setBankId(String paramString)
  {
    this.bankId = paramString;
  }
  
  public String getBankId()
  {
    return this.bankId;
  }
  
  public void setCustomerId(String paramString)
  {
    this.customerId = paramString;
  }
  
  public void setCustomerId(int paramInt)
  {
    this.customerId = String.valueOf(paramInt);
  }
  
  public String getCustomerId()
  {
    return this.customerId;
  }
  
  public void setStartTime(String paramString)
  {
    this.startTime = paramString;
  }
  
  public String getStartTime()
  {
    return this.startTime;
  }
  
  public void setCloseTime(String paramString)
  {
    this.closeTime = paramString;
  }
  
  public String getCloseTime()
  {
    return this.closeTime;
  }
  
  public void setPaymentType(String paramString)
  {
    this.pmtType = paramString;
  }
  
  public String getPaymentType()
  {
    return this.pmtType;
  }
  
  public void setPaymentSubType(String paramString)
  {
    this.pmtSubType = paramString;
  }
  
  public String getPaymentSubType()
  {
    return this.pmtSubType;
  }
  
  public void setCreateDate(DateTime paramDateTime)
  {
    this.createDate = paramDateTime;
  }
  
  public void setCreateDate(String paramString)
  {
    try
    {
      if (this.createDate == null) {
        this.createDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.createDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      localInvalidDateTimeException.printStackTrace();
    }
  }
  
  public DateTime getCreateDate()
  {
    return this.createDate;
  }
  
  public void setDescription(String paramString)
  {
    this.description = paramString;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setSubmittedBy(String paramString)
  {
    this.submittedBy = paramString;
  }
  
  public void setSubmittedBy(int paramInt)
  {
    this.submittedBy = String.valueOf(paramInt);
  }
  
  public String getSubmittedBy()
  {
    return this.submittedBy;
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.createDate != null) {
      this.createDate.setFormat(paramString);
    }
  }
  
  public ProcessingWindowInfo getProcessingWindowInfo()
  {
    ProcessingWindowInfo localProcessingWindowInfo = new ProcessingWindowInfo();
    localProcessingWindowInfo.setWindowId(this.id);
    localProcessingWindowInfo.setFIID(this.bankId);
    localProcessingWindowInfo.setCustomerId(this.customerId);
    localProcessingWindowInfo.setPmtType(this.pmtType);
    localProcessingWindowInfo.setPmtSubType(this.pmtSubType);
    localProcessingWindowInfo.setDescription(this.description);
    localProcessingWindowInfo.setSubmittedBy(this.submittedBy);
    if (this.startTime.length() == 5) {
      this.startTime += ":00";
    }
    localProcessingWindowInfo.setStartTime(this.startTime);
    if (this.closeTime.length() == 5) {
      this.closeTime += ":00";
    }
    localProcessingWindowInfo.setCloseTime(this.closeTime);
    return localProcessingWindowInfo;
  }
  
  public void setProcessingWindowInfo(ProcessingWindowInfo paramProcessingWindowInfo)
  {
    setId(paramProcessingWindowInfo.getWindowId());
    setBankId(paramProcessingWindowInfo.getFIID());
    setCustomerId(paramProcessingWindowInfo.getCustomerId());
    setPaymentType(paramProcessingWindowInfo.getPmtType());
    setPaymentSubType(paramProcessingWindowInfo.getPmtSubType());
    setDescription(paramProcessingWindowInfo.getDescription());
    setSubmittedBy(paramProcessingWindowInfo.getSubmittedBy());
    String str1 = paramProcessingWindowInfo.getStartTime();
    if ((str1 != null) && (str1.length() > 5)) {
      setStartTime(str1.substring(0, 5));
    } else {
      setStartTime(str1);
    }
    str1 = paramProcessingWindowInfo.getCloseTime();
    if ((str1 != null) && (str1.length() > 5)) {
      setCloseTime(str1.substring(0, 5));
    } else {
      setCloseTime(str1);
    }
    setDateFormat("yyyy/MM/dd");
    String str2 = paramProcessingWindowInfo.getDateCreate();
    if ((str2 != null) && (str2.length() > 10)) {
      str2 = str2.substring(0, 10);
    }
    setCreateDate(str2);
    setDateFormat("SHORT");
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("ID")) && (getId() != null)) {
      return isFilterable(getId(), paramString2, paramString3);
    }
    if ((paramString1.equals("BANK_ID")) && (getBankId() != null)) {
      return isFilterable(getBankId(), paramString2, paramString3);
    }
    if ((paramString1.equals("CUSTOMER_ID")) && (getCustomerId() != null)) {
      return isFilterable(getCustomerId(), paramString2, paramString3);
    }
    if ((paramString1.equals("START_TIME")) && (getStartTime() != null)) {
      return isFilterable(getStartTime(), paramString2, paramString3);
    }
    if ((paramString1.equals("CLOSE_TIME")) && (getCloseTime() != null)) {
      return isFilterable(getCloseTime(), paramString2, paramString3);
    }
    if (paramString1.equals("PAYMENT_TYPE")) {
      return isFilterable(getPaymentType(), paramString2, paramString3);
    }
    if (paramString1.equals("PAYMENT_SUB_TYPE")) {
      return isFilterable(getPaymentSubType(), paramString2, paramString3);
    }
    if (paramString1.equals("CREATE_DATE")) {
      return getCreateDate().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("DESCRIPTION")) && (getDescription() != null)) {
      return isFilterable(getDescription(), paramString2, paramString3);
    }
    if ((paramString1.equals("SUBMITTED_BY")) && (getSubmittedBy() != null)) {
      return isFilterable(getSubmittedBy(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public Object clone()
  {
    try
    {
      ProcessingWindow localProcessingWindow = (ProcessingWindow)super.clone();
      if (this.createDate != null) {
        localProcessingWindow.setCreateDate((DateTime)this.createDate.clone());
      }
      return localProcessingWindow;
    }
    catch (Exception localException) {}
    return super.clone();
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ProcessingWindow localProcessingWindow = (ProcessingWindow)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("ID")) && (this.id != null) && (localProcessingWindow.getId() != null)) {
      i = localCollator.compare(getId(), localProcessingWindow.getId());
    } else if ((paramString.equals("BANK_ID")) && (getBankId() != null) && (localProcessingWindow.getBankId() != null)) {
      i = localCollator.compare(getBankId(), localProcessingWindow.getBankId());
    } else if ((paramString.equals("CUSTOMER_ID")) && (getCustomerId() != null) && (localProcessingWindow.getCustomerId() != null)) {
      i = localCollator.compare(getCustomerId(), localProcessingWindow.getCustomerId());
    } else if ((paramString.equals("START_TIME")) && (getStartTime() != null) && (localProcessingWindow.getStartTime() != null)) {
      i = localCollator.compare(getStartTime(), localProcessingWindow.getStartTime());
    } else if ((paramString.equals("CLOSE_TIME")) && (getCloseTime() != null) && (localProcessingWindow.getCloseTime() != null)) {
      i = localCollator.compare(getCloseTime(), localProcessingWindow.getCloseTime());
    } else if ((paramString.equals("PAYMENT_TYPE")) && (getPaymentType() != null) && (localProcessingWindow.getPaymentType() != null)) {
      i = localCollator.compare(getPaymentType(), localProcessingWindow.getPaymentType());
    } else if ((paramString.equals("DESCRIPTION")) && (getDescription() != null) && (localProcessingWindow.getDescription() != null)) {
      i = localCollator.compare(getDescription(), localProcessingWindow.getDescription());
    } else if ((paramString.equals("SUBMITTED_BY")) && (getSubmittedBy() != null) && (localProcessingWindow.getSubmittedBy() != null)) {
      i = localCollator.compare(getSubmittedBy(), localProcessingWindow.getSubmittedBy());
    } else if ((paramString.equals("CREATE_DATE")) && (getCreateDate() != null) && (localProcessingWindow.getCreateDate() != null)) {
      i = getCreateDate().compare(localProcessingWindow.getCreateDate());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "PROCESSING_WINDOW");
    XMLHandler.appendTag(localStringBuffer, "ID", getId());
    XMLHandler.appendTag(localStringBuffer, "BANK_ID", getBankId());
    XMLHandler.appendTag(localStringBuffer, "CUSTOMER_ID", getCustomerId());
    XMLHandler.appendTag(localStringBuffer, "START_TIME", getStartTime());
    XMLHandler.appendTag(localStringBuffer, "CLOSE_TIME", getCloseTime());
    XMLHandler.appendTag(localStringBuffer, "PAYMENT_TYPE", getPaymentType());
    XMLHandler.appendTag(localStringBuffer, "PAYMENT_SUB_TYPE", getPaymentSubType());
    XMLHandler.appendTag(localStringBuffer, "CREATE_DATE", getCreateDate().toString());
    XMLHandler.appendTag(localStringBuffer, "DESCRIPTION", getDescription());
    XMLHandler.appendTag(localStringBuffer, "SUBMITTED_BY", getSubmittedBy());
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "PROCESSING_WINDOW");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.paymentsadmin.ProcessingWindow
 * JD-Core Version:    0.7.0.1
 */