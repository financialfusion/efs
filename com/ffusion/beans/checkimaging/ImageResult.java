package com.ffusion.beans.checkimaging;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.checkimaging.adapters.CheckImageDefines;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Sortable;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;

public class ImageResult
  extends ExtendABean
  implements CheckImageDefines, Comparable, Sortable, Serializable
{
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.checkimaging.resources";
  public static final String RSRC_DRCR_PREFIX = "DrCr_";
  public static final String NOTREQUESTED = "NOT REQUESTED";
  public static final String PENDING = "PENDING";
  public static final String AVAILABLE = "AVAILABLE";
  public static final String ERROR = "ERROR";
  public static final String IMAGE_RESULT = "IMAGERESULT";
  public static final String DATE_PLACED = "DP";
  public static final String POSTING_DATE = "PD";
  public static final String REQUESTED_DATE = "RD";
  public static final String DATE_FORMAT = "DF";
  public static final String AMOUNT = "AT";
  public static final String FEE = "FE";
  public static final String IMAGE = "IG";
  public static final String DRCR = "DC";
  public static final String ACCOUNT_NUMBER = "AN";
  public static final String FRONTVIEWHANDLE = "FV";
  public static final String BACKVIEWHANDLE = "BV";
  public static final String IMAGEHANDLE = "IH";
  public static final String CHECK_NUMBER = "CN";
  public static final String ROUTING_TRANSIT_NUMBER = "RT";
  public static final String TRANS_CODE = "TC";
  public static final String CAPTURE_SEQUENCE = "CS";
  public static final String SEQUENCE_NUMBER = "SN";
  public static final String DEPOSIT_TRACE_ID = "DT";
  public static final String ENTRY_NUMBER = "EN";
  public static final String TRACER_NUMBER = "TN";
  public static final String STATUS = "ST";
  public static final String PACKAGE_ID = "PID";
  public static final String BANK_ID = "BID";
  public static final String ERROR_CODE = "EC";
  public static final String DEMO_IMAGE_FRONT = "DIF";
  public static final String DEMO_IMAGE_BACK = "DIB";
  public static final String USER_ID = "UID";
  public static final String PENDING_IMAGE_ID = "ID";
  public static final String T_IMAGE_RESULT = "IMAGERESULT";
  public static final String T_DATE_PLACED = "DATEPLACED";
  public static final String T_POSTING_DATE = "POSTINGDATE";
  public static final String T_REQUESTED_DATE = "REQUESTED_DATE";
  public static final String T_DATE_FORMAT = "DATEFORMAT";
  public static final String T_AMOUNT = "AMOUNT";
  public static final String T_FEE = "FEE";
  public static final String T_IMAGE = "IMAGE";
  public static final String T_DRCR = "DRCR";
  public static final String T_ACCOUNT_ID = "ACCOUNTID";
  public static final String T_FRONTVIEWHANDLE = "FRONTVIEWHANDLE";
  public static final String T_BACKVIEWHANDLE = "BACKVIEWHANDLE";
  public static final String T_IMAGEHANDLE = "IMAGEHANDLE";
  public static final String T_CHECK_NUMBER = "CHECKNUMBER";
  public static final String T_ROUTING_TRANSIT_NUMBER = "ROUTINGTRANS";
  public static final String T_TRANS_CODE = "TRANSCODE";
  public static final String T_CAPTURESEQUENCE = "CAPTURESEQUENCE";
  public static final String T_SEQUENCENUMBER = "SEQUENCENUMBER";
  public static final String T_ENTRY_NUMBER = "ENTRYNUMBER";
  public static final String T_TRACER_NUMBER = "TRACERNUMBER";
  public static final String T_STATUS = "STATUS";
  protected int compressionType;
  protected String imageHandle;
  protected String accountID;
  protected String userId;
  protected String drcr;
  protected String checkNumber;
  protected String routingTransitNumber;
  protected String transCode;
  protected String sequenceNumber;
  protected String captureSequence;
  protected String depositTraceID;
  protected String entryNumber;
  protected String tracerNumber;
  protected String status;
  protected String estimate;
  protected String chargeAccount;
  protected String packageId;
  protected String bankId;
  protected String errorCode;
  protected String demoImageFront;
  protected String demoImageBack;
  protected String depID;
  protected String frontViewHandle;
  protected String backViewHandle;
  protected String storageTier;
  protected String returnCode;
  protected String reasonCode;
  protected DateTime datePlaced;
  protected DateTime postingDate;
  protected DateTime requestedDate;
  protected Currency amount;
  protected Currency fee;
  protected HashMap imageHashMap;
  protected boolean feeFlag = false;
  protected boolean feeProcessed = false;
  protected boolean previouslyRequestedFlag = false;
  
  public ImageResult() {}
  
  public ImageResult(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.datePlaced = new DateTime(paramLocale);
    this.postingDate = new DateTime(paramLocale);
    this.requestedDate = new DateTime(paramLocale);
    setDateFormat("yyyyMMdd");
    this.amount = new Currency("0", paramLocale);
    this.fee = new Currency("0", paramLocale);
    setStatus("NOT REQUESTED");
    setErrorCode("0");
    this.imageHashMap = null;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "PD");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ImageResult localImageResult = (ImageResult)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("POSTINGDATE")) && (this.postingDate != null) && (localImageResult.getPostingDate() != null)) {
      i = getPostingDate().compareTo(localImageResult.getPostingDate());
    } else if ((paramString.equals("DRCR")) && (getDrCrDisplayName() != null) && (localImageResult.getDrCrDisplayName() != null)) {
      i = localCollator.compare(getDrCrDisplayName(), localImageResult.getDrCrDisplayName());
    } else if ((paramString.equals("AMOUNT")) && (getAmountValue() != null) && (localImageResult.getAmountValue() != null)) {
      i = getAmountValue().getAmountValue().compareTo(localImageResult.getAmountValue().getAmountValue());
    } else if ((paramString.equals("ACCOUNTID")) && (getAccountID() != null) && (localImageResult.getAccountID() != null)) {
      i = localCollator.compare(getAccountID(), localImageResult.getAccountID());
    } else if (paramString.equals("CHECKNUMBER"))
    {
      if ((getCheckNumber() != null) && (localImageResult.getCheckNumber() != null)) {
        i = localCollator.compare(getCheckNumber(), localImageResult.getCheckNumber());
      } else if ((getCheckNumber() == null) && (localImageResult.getCheckNumber() == null)) {
        i = 0;
      } else if (getCheckNumber() == null) {
        i = -1;
      } else if (localImageResult.getCheckNumber() == null) {
        i = 1;
      }
    }
    else if ((paramString.equals("ROUTINGTRANS")) && (getRoutingTransitNumber() != null) && (localImageResult.getRoutingTransitNumber() != null)) {
      i = localCollator.compare(getRoutingTransitNumber(), localImageResult.getRoutingTransitNumber());
    } else if ((paramString.equals("TRANSCODE")) && (getTransCode() != null) && (localImageResult.getTransCode() != null)) {
      i = localCollator.compare(getTransCode(), localImageResult.getTransCode());
    } else if ((paramString.equals("CAPTURESEQUENCE")) && (getCaptureSequence() != null) && (localImageResult.getCaptureSequence() != null)) {
      i = localCollator.compare(getCaptureSequence(), localImageResult.getCaptureSequence());
    } else if ((paramString.equals("SEQUENCENUMBER")) && (getSequenceNumber() != null) && (localImageResult.getSequenceNumber() != null)) {
      i = localCollator.compare(getSequenceNumber(), localImageResult.getSequenceNumber());
    } else if ((paramString.equals("ENTRYNUMBER")) && (getEntryNumber() != null) && (localImageResult.getEntryNumber() != null)) {
      i = localCollator.compare(getEntryNumber(), localImageResult.getEntryNumber());
    } else if ((paramString.equals("TRACERNUMBER")) && (getTracerNumber() != null) && (localImageResult.getTracerNumber() != null)) {
      i = localCollator.compare(getTracerNumber(), localImageResult.getTracerNumber());
    } else if ((paramString.equals("STATUS")) && (getStatus() != null) && (localImageResult.getStatus() != null)) {
      i = localCollator.compare(getStatus(), localImageResult.getStatus());
    } else {
      super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setAccountID(String paramString)
  {
    this.accountID = paramString;
    this.chargeAccount = paramString;
  }
  
  public String getAccountID()
  {
    return this.accountID;
  }
  
  public void setFrontViewHandle(String paramString)
  {
    this.frontViewHandle = paramString;
  }
  
  public String getFrontViewHandle()
  {
    return this.frontViewHandle;
  }
  
  public void setBackViewHandle(String paramString)
  {
    this.backViewHandle = paramString;
  }
  
  public String getBackViewHandle()
  {
    return this.backViewHandle;
  }
  
  public void setHandle(String paramString)
  {
    this.imageHandle = paramString;
  }
  
  public String getHandle()
  {
    return this.imageHandle;
  }
  
  public void setCompressionType(int paramInt)
  {
    this.compressionType = paramInt;
  }
  
  public void setCompressionType(String paramString)
  {
    this.compressionType = Integer.valueOf(paramString).intValue();
  }
  
  public int getCompressionType()
  {
    return this.compressionType;
  }
  
  public String getCompressionTypeValue()
  {
    return Integer.toString(this.compressionType);
  }
  
  public void setImageHandle(String paramString)
  {
    this.imageHandle = paramString;
  }
  
  public String getImageHandle()
  {
    return this.imageHandle;
  }
  
  public void setPackageId(String paramString)
  {
    this.packageId = paramString;
  }
  
  public String getPackageId()
  {
    return this.packageId;
  }
  
  public void setBankId(String paramString)
  {
    this.bankId = paramString;
  }
  
  public String getBankId()
  {
    return this.bankId;
  }
  
  public void setStorageTier(String paramString)
  {
    this.storageTier = paramString;
  }
  
  public String getStorageTier()
  {
    return this.storageTier;
  }
  
  public void setReturnCode(String paramString)
  {
    this.returnCode = paramString;
  }
  
  public String getReturnCode()
  {
    return this.returnCode;
  }
  
  public void setReasonCode(String paramString)
  {
    this.reasonCode = paramString;
  }
  
  public String getReasonCode()
  {
    return this.reasonCode;
  }
  
  public void setAmount(String paramString)
  {
    if (paramString.equals("")) {
      this.amount = null;
    } else {
      this.amount = new Currency(paramString, this.locale);
    }
  }
  
  public void setAmountValue(Currency paramCurrency)
  {
    this.amount = paramCurrency;
  }
  
  public Currency getAmountValue()
  {
    return this.amount;
  }
  
  public String getAmount()
  {
    return this.amount.toString();
  }
  
  public void setDrCr(String paramString)
  {
    this.drcr = paramString;
  }
  
  public String getDrCr()
  {
    return this.drcr;
  }
  
  public String getDrCrDisplayName()
  {
    if ((this.drcr == null) || (this.drcr.length() == 0)) {
      return this.drcr;
    }
    String str = this.drcr;
    try
    {
      str = ResourceUtil.getString("DrCr_" + this.drcr, "com.ffusion.beans.checkimaging.resources", this.locale);
      if ((str == null) || (str.length() == 0)) {
        str = this.drcr;
      }
    }
    catch (MissingResourceException localMissingResourceException) {}
    return str;
  }
  
  public void setChargeAccount(String paramString)
  {
    this.chargeAccount = paramString;
  }
  
  public String getChargeAccount()
  {
    return this.chargeAccount;
  }
  
  public void setCheckNumber(String paramString)
  {
    this.checkNumber = paramString;
  }
  
  public String getCheckNumber()
  {
    return this.checkNumber;
  }
  
  public void setRoutingTransitNumber(String paramString)
  {
    this.routingTransitNumber = paramString;
  }
  
  public String getRoutingTransitNumber()
  {
    return this.routingTransitNumber;
  }
  
  public void setTransCode(String paramString)
  {
    this.transCode = paramString;
  }
  
  public String getTransCode()
  {
    return this.transCode;
  }
  
  public void setCaptureSequence(String paramString)
  {
    this.captureSequence = paramString;
  }
  
  public String getCaptureSequence()
  {
    return this.captureSequence;
  }
  
  public void setSequenceNumber(String paramString)
  {
    this.sequenceNumber = paramString;
  }
  
  public String getSequenceNumber()
  {
    return this.sequenceNumber;
  }
  
  public void setDepositTraceID(String paramString)
  {
    this.depositTraceID = paramString;
  }
  
  public String getDepositTraceID()
  {
    return this.depositTraceID;
  }
  
  public void setEntryNumber(String paramString)
  {
    this.entryNumber = paramString;
  }
  
  public String getEntryNumber()
  {
    return this.entryNumber;
  }
  
  public void setTracerNumber(String paramString)
  {
    this.tracerNumber = paramString;
  }
  
  public String getTracerNumber()
  {
    return this.tracerNumber;
  }
  
  public void setPostingDate(String paramString)
  {
    try
    {
      this.postingDate.fromString(paramString);
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public String getPostingDate()
  {
    return this.postingDate.toString();
  }
  
  public Calendar getPostingDateValue()
  {
    return this.postingDate;
  }
  
  public void setDatePlaced(String paramString)
  {
    try
    {
      this.datePlaced.fromString(paramString);
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public String getDatePlaced()
  {
    return this.datePlaced.toString();
  }
  
  public Calendar getDatePlacedValue()
  {
    return this.datePlaced;
  }
  
  public String getTimePlaced()
  {
    String str = this.datePlaced.get(11) + ":" + this.datePlaced.get(12) + ":" + this.datePlaced.get(13);
    return str;
  }
  
  public void setRequestedDate(String paramString)
  {
    try
    {
      this.requestedDate.fromString(paramString);
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public String getRequestedDate()
  {
    return this.requestedDate.toString();
  }
  
  public Calendar getRequestedDateValue()
  {
    return this.requestedDate;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datePlaced.setFormat(paramString);
    this.postingDate.setFormat(paramString);
    this.requestedDate.setFormat(paramString);
  }
  
  public String getDateFormat()
  {
    return this.datePlaced.getFormat();
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setDepositID(String paramString)
  {
    this.depID = paramString;
  }
  
  public String getDepositID()
  {
    return this.depID;
  }
  
  public void setEstimate(String paramString)
  {
    this.estimate = paramString;
  }
  
  public String getEstimate()
  {
    return this.estimate;
  }
  
  public void setFee(String paramString)
  {
    if (paramString.equals("")) {
      this.fee = null;
    } else {
      this.fee = new Currency(paramString, this.locale);
    }
  }
  
  public void setFeeValue(Currency paramCurrency)
  {
    this.fee = paramCurrency;
  }
  
  public Currency getFeeValue()
  {
    return this.fee;
  }
  
  public String getFee()
  {
    return this.fee.toString();
  }
  
  public void setFeeFlag(String paramString)
  {
    this.feeFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getFeeFlag()
  {
    return String.valueOf(this.feeFlag);
  }
  
  public String getDemoImageFront()
  {
    return this.demoImageFront;
  }
  
  public String getDemoImageBack()
  {
    return this.demoImageBack;
  }
  
  public void setFeeProcessed(String paramString)
  {
    this.feeProcessed = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getFeeProcessed()
  {
    return String.valueOf(this.feeProcessed);
  }
  
  public void setPreviouslyRequested(String paramString)
  {
    this.previouslyRequestedFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getPreviouslyRequested()
  {
    return String.valueOf(this.previouslyRequestedFlag);
  }
  
  public void setImage(HashMap paramHashMap)
  {
    this.imageHashMap = ((HashMap)paramHashMap.clone());
  }
  
  public HashMap getImage()
  {
    return this.imageHashMap;
  }
  
  public void setErrorCode(String paramString)
  {
    this.errorCode = paramString;
  }
  
  public String getErrorCode()
  {
    return this.errorCode;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("UID")) {
      this.userId = paramString2;
    } else if (paramString1.equals("DP")) {
      this.datePlaced.fromXMLFormat(paramString2);
    } else if (paramString1.equals("PD")) {
      this.postingDate.fromXMLFormat(paramString2);
    } else if (paramString1.equals("RD")) {
      this.requestedDate.fromXMLFormat(paramString2);
    } else if (paramString1.equals("DF")) {
      setDateFormat(paramString2);
    } else if (paramString1.equals("AT")) {
      this.amount.fromString(paramString2);
    } else if (paramString1.equals("FE")) {
      this.fee.fromString(paramString2);
    } else if (!paramString1.equals("IG")) {
      if (paramString1.equals("DC")) {
        this.drcr = paramString2;
      } else if (paramString1.equals("account_id")) {
        setAccountID(paramString2);
      } else if (paramString1.equals("FV")) {
        this.frontViewHandle = paramString2;
      } else if (paramString1.equals("BV")) {
        this.backViewHandle = paramString2;
      } else if (paramString1.equals("IH")) {
        this.imageHandle = paramString2;
      } else if (paramString1.equals("CN")) {
        this.checkNumber = paramString2;
      } else if (paramString1.equals("RT")) {
        this.routingTransitNumber = paramString2;
      } else if (paramString1.equals("TC")) {
        this.transCode = paramString2;
      } else if (paramString1.equals("CS")) {
        this.captureSequence = paramString2;
      } else if (paramString1.equals("SN")) {
        this.sequenceNumber = paramString2;
      } else if (paramString1.equals("DT")) {
        this.depositTraceID = paramString2;
      } else if (paramString1.equals("EN")) {
        this.entryNumber = paramString2;
      } else if (paramString1.equals("TN")) {
        this.tracerNumber = paramString2;
      } else if (paramString1.equals("PID")) {
        this.packageId = paramString2;
      } else if (paramString1.equals("BID")) {
        this.bankId = paramString2;
      } else if (paramString1.equals("EC")) {
        this.errorCode = paramString2;
      } else if (paramString1.equals("ST")) {
        this.status = paramString2;
      } else if (paramString1.equals("DIF")) {
        this.demoImageFront = paramString2;
      } else if (paramString1.equals("DIB")) {
        this.demoImageBack = paramString2;
      } else {
        bool = super.set(paramString1, paramString2);
      }
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "IMAGERESULT");
    XMLHandler.appendTag(localStringBuffer, "UID", this.userId);
    XMLHandler.appendTag(localStringBuffer, "DP", this.datePlaced.toXMLFormat());
    XMLHandler.appendTag(localStringBuffer, "PD", this.postingDate.toXMLFormat());
    XMLHandler.appendTag(localStringBuffer, "RD", this.requestedDate.toXMLFormat());
    XMLHandler.appendTag(localStringBuffer, "DF", this.datePlaced.getFormat());
    XMLHandler.appendTag(localStringBuffer, "AT", this.amount.doubleValue());
    XMLHandler.appendTag(localStringBuffer, "FE", this.fee.doubleValue());
    XMLHandler.appendTag(localStringBuffer, "DC", this.drcr);
    XMLHandler.appendTag(localStringBuffer, "account_id", this.accountID);
    XMLHandler.appendTag(localStringBuffer, "FV", this.frontViewHandle);
    XMLHandler.appendTag(localStringBuffer, "BV", this.backViewHandle);
    XMLHandler.appendTag(localStringBuffer, "IH", this.imageHandle);
    XMLHandler.appendTag(localStringBuffer, "CN", this.checkNumber);
    XMLHandler.appendTag(localStringBuffer, "RT", this.routingTransitNumber);
    XMLHandler.appendTag(localStringBuffer, "TC", this.transCode);
    XMLHandler.appendTag(localStringBuffer, "CS", this.captureSequence);
    XMLHandler.appendTag(localStringBuffer, "SN", this.sequenceNumber);
    XMLHandler.appendTag(localStringBuffer, "DT", this.depositTraceID);
    XMLHandler.appendTag(localStringBuffer, "EN", this.entryNumber);
    XMLHandler.appendTag(localStringBuffer, "TN", this.tracerNumber);
    XMLHandler.appendTag(localStringBuffer, "PID", this.packageId);
    XMLHandler.appendTag(localStringBuffer, "BID", this.bankId);
    XMLHandler.appendTag(localStringBuffer, "EC", this.errorCode);
    XMLHandler.appendTag(localStringBuffer, "ST", this.status);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "IMAGERESULT");
    return localStringBuffer.toString();
  }
  
  public String xmlImageRequest()
  {
    String str = "\t\t\t\t<handle>" + this.imageHandle + "</handle>\n";
    return str;
  }
  
  public void set(ImageResult paramImageResult)
  {
    this.compressionType = paramImageResult.getCompressionType();
    this.imageHandle = paramImageResult.getImageHandle();
    this.accountID = paramImageResult.getAccountID();
    this.userId = paramImageResult.getUserId();
    this.drcr = paramImageResult.getDrCr();
    this.checkNumber = paramImageResult.getCheckNumber();
    this.routingTransitNumber = paramImageResult.getRoutingTransitNumber();
    this.transCode = paramImageResult.getTransCode();
    this.sequenceNumber = paramImageResult.getSequenceNumber();
    this.captureSequence = paramImageResult.getCaptureSequence();
    this.depositTraceID = paramImageResult.getDepositTraceID();
    this.entryNumber = paramImageResult.getEntryNumber();
    this.tracerNumber = paramImageResult.getTracerNumber();
    this.status = paramImageResult.getStatus();
    this.estimate = paramImageResult.getEstimate();
    this.chargeAccount = paramImageResult.getChargeAccount();
    this.packageId = paramImageResult.getPackageId();
    this.bankId = paramImageResult.getBankId();
    this.errorCode = paramImageResult.getErrorCode();
    this.demoImageFront = paramImageResult.getDemoImageFront();
    this.demoImageBack = paramImageResult.getDemoImageBack();
    this.depID = paramImageResult.getDepositID();
    this.frontViewHandle = paramImageResult.getFrontViewHandle();
    this.backViewHandle = paramImageResult.getBackViewHandle();
    this.storageTier = paramImageResult.getStorageTier();
    this.returnCode = paramImageResult.getReturnCode();
    this.reasonCode = paramImageResult.getReasonCode();
    if (paramImageResult.getDatePlacedValue() != null) {
      this.datePlaced = ((DateTime)paramImageResult.getDatePlacedValue().clone());
    }
    if (paramImageResult.getPostingDateValue() != null) {
      this.postingDate = ((DateTime)paramImageResult.getPostingDateValue().clone());
    }
    if (paramImageResult.getRequestedDateValue() != null) {
      this.requestedDate = ((DateTime)paramImageResult.getRequestedDateValue().clone());
    }
    if (paramImageResult.getAmountValue() != null) {
      this.amount = ((Currency)paramImageResult.getAmountValue().clone());
    }
    if (paramImageResult.getFeeValue() != null) {
      this.fee = ((Currency)paramImageResult.getFeeValue().clone());
    }
    if (paramImageResult.imageHashMap != null) {
      this.imageHashMap = ((HashMap)paramImageResult.imageHashMap.clone());
    }
    this.feeFlag = paramImageResult.feeFlag;
    this.feeProcessed = paramImageResult.feeProcessed;
    this.previouslyRequestedFlag = paramImageResult.previouslyRequestedFlag;
    super.set(paramImageResult);
  }
  
  public Object clone()
  {
    try
    {
      ImageResult localImageResult = (ImageResult)super.clone();
      if (getDatePlacedValue() != null) {
        localImageResult.datePlaced = ((DateTime)getDatePlacedValue().clone());
      }
      if (getPostingDateValue() != null) {
        localImageResult.postingDate = ((DateTime)getPostingDateValue().clone());
      }
      if (getRequestedDateValue() != null) {
        localImageResult.requestedDate = ((DateTime)getRequestedDateValue().clone());
      }
      if (getAmountValue() != null) {
        localImageResult.amount = ((Currency)getAmountValue().clone());
      }
      if (getFeeValue() != null) {
        localImageResult.fee = ((Currency)getFeeValue().clone());
      }
      if (this.imageHashMap != null) {
        localImageResult.imageHashMap = ((HashMap)this.imageHashMap.clone());
      }
      return localImageResult;
    }
    catch (Exception localException) {}
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.checkimaging.ImageResult
 * JD-Core Version:    0.7.0.1
 */