package com.ffusion.services.checkimaging;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.checkimaging.ImageRequest;
import com.ffusion.beans.checkimaging.ImageResult;
import com.ffusion.beans.checkimaging.ImageResults;
import com.ffusion.checkimaging.CheckImagingException;
import com.ffusion.checkimaging.adapters.CheckImagingAdapter;
import com.ffusion.services.CheckImageService;
import com.ffusion.util.XMLTag;
import java.util.HashMap;

public class CheckImagingService
  implements CheckImageService
{
  public static final String SERVICE_INIT_XML = "checkimaging.xml";
  protected String serverURL;
  protected String doNotSearchBeforeDate;
  protected int maxAge;
  protected String logFilename;
  protected String feeLogging;
  protected String fee;
  protected String image_fee_message_code;
  protected String image_fee_message_retail;
  protected String image_fee_message_biz;
  protected String smtpHost;
  protected String mailFrom;
  protected String imagingRetailOn;
  protected String imagingRetailOffsets;
  protected String imagingBizOn;
  protected String imagingBizOffsets;
  protected String logoPath;
  protected String stmtMaxImages;
  protected String timeout;
  protected String maxsearchitems;
  protected String maximageitems;
  protected String ignoretier;
  protected String repository;
  protected String frontViewType;
  protected String backViewType;
  protected String compression;
  protected XMLTag tag;
  
  public void initialize(HashMap paramHashMap)
    throws CheckImagingException
  {
    paramHashMap.put("URL", "checkimaging.xml");
    CheckImagingAdapter.initialize(paramHashMap);
    a();
  }
  
  public ImageResults getImageIndex(SecureUser paramSecureUser, ImageRequest paramImageRequest, HashMap paramHashMap)
    throws CheckImagingException
  {
    ImageResults localImageResults = CheckImagingAdapter.getImageIndex(paramSecureUser, paramImageRequest);
    return localImageResults;
  }
  
  public ImageResult getImage(SecureUser paramSecureUser, ImageResult paramImageResult, HashMap paramHashMap)
    throws CheckImagingException
  {
    ImageResult localImageResult = CheckImagingAdapter.getImage(paramSecureUser, paramImageResult);
    return localImageResult;
  }
  
  public ImageResult getImageStatus(SecureUser paramSecureUser, ImageResult paramImageResult, HashMap paramHashMap)
    throws CheckImagingException
  {
    ImageResult localImageResult = CheckImagingAdapter.getImageStatus(paramSecureUser, paramImageResult);
    return localImageResult;
  }
  
  public ImageResults getImagePackageId(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CheckImagingException
  {
    ImageResults localImageResults = CheckImagingAdapter.getImagePackageId(paramSecureUser, paramImageResults);
    return localImageResults;
  }
  
  public ImageResults getPendingImages(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CheckImagingException
  {
    ImageResults localImageResults = CheckImagingAdapter.getPendingImages(paramSecureUser, paramHashMap);
    return localImageResults;
  }
  
  public ImageResults setPendingImages(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CheckImagingException
  {
    ImageResults localImageResults = CheckImagingAdapter.setPendingImages(paramSecureUser, paramImageResults, paramHashMap);
    return localImageResults;
  }
  
  public ImageResults emailPendingImageStatus(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CheckImagingException
  {
    ImageResults localImageResults = CheckImagingAdapter.emailPendingImageStatus(paramSecureUser, paramImageResults, paramHashMap);
    return localImageResults;
  }
  
  public ImageResult emailImage(SecureUser paramSecureUser, ImageResult paramImageResult, HashMap paramHashMap)
    throws CheckImagingException
  {
    ImageResult localImageResult = CheckImagingAdapter.emailImage(paramSecureUser, paramImageResult, paramHashMap);
    return localImageResult;
  }
  
  public ImageResults deleteImageResults(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CheckImagingException
  {
    ImageResults localImageResults = CheckImagingAdapter.deleteImageResults(paramSecureUser, paramImageResults);
    return localImageResults;
  }
  
  public ImageResults deleteImageRequests(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CheckImagingException
  {
    ImageResults localImageResults = CheckImagingAdapter.deleteImageRequests(paramSecureUser, paramImageResults);
    return localImageResults;
  }
  
  private void a()
  {
    this.serverURL = CheckImagingAdapter.getServerURL();
    this.doNotSearchBeforeDate = CheckImagingAdapter.getSearchDate();
    this.maxAge = CheckImagingAdapter.getMaxAgeInt();
    this.logFilename = CheckImagingAdapter.getLogFileName();
    this.feeLogging = CheckImagingAdapter.getFeeLogging();
    this.fee = CheckImagingAdapter.getImageFee();
    this.image_fee_message_code = CheckImagingAdapter.getImageFeeMessageCode();
    this.image_fee_message_retail = CheckImagingAdapter.getImageFeeMessageRetail();
    this.image_fee_message_biz = CheckImagingAdapter.getImageFeeMessageBiz();
    this.smtpHost = CheckImagingAdapter.getSMTPHost();
    this.mailFrom = CheckImagingAdapter.getEmailFrom();
    this.imagingRetailOn = CheckImagingAdapter.getImagingRetailOn();
    this.imagingRetailOffsets = CheckImagingAdapter.getImagingRetailOffsets();
    this.imagingBizOn = CheckImagingAdapter.getImagingBizOn();
    this.imagingBizOffsets = CheckImagingAdapter.getImagingBizOffsets();
    this.logoPath = CheckImagingAdapter.getLogoPath();
    this.stmtMaxImages = CheckImagingAdapter.getStmtMaxImages();
    this.tag = CheckImagingAdapter.getErrorTag();
    this.timeout = CheckImagingAdapter.getTimeOut();
    this.maxsearchitems = CheckImagingAdapter.getMaxSearchItems();
    this.maximageitems = CheckImagingAdapter.getMaxImageItems();
    this.ignoretier = CheckImagingAdapter.getIgnoreTier();
    this.repository = CheckImagingAdapter.getRepository();
    this.frontViewType = CheckImagingAdapter.getFrontViewType();
    this.backViewType = CheckImagingAdapter.getBackViewType();
    this.compression = CheckImagingAdapter.getCompression();
  }
  
  public HashMap getConfigProperties(SecureUser paramSecureUser, HashMap paramHashMap)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("SERVER_URL", this.serverURL);
    localHashMap.put("DO_NOT_SEARCH_BEFORE_DATE", this.doNotSearchBeforeDate);
    localHashMap.put("IMAGE_MAX_AGE", getMaxAge());
    localHashMap.put("FEE_LOG_FILE", this.logFilename);
    localHashMap.put("FEE_LOGGING", this.feeLogging);
    localHashMap.put("IMAGE_FEE", this.fee);
    localHashMap.put("IMAGE_FEE_MESSAGE_CODE", this.image_fee_message_code);
    localHashMap.put("IMAGE_FEE_MESSAGE_RETAIL", this.image_fee_message_retail);
    localHashMap.put("IMAGE_FEE_MESSAGE_BIZ", this.image_fee_message_biz);
    localHashMap.put("SMTP_HOST", this.smtpHost);
    localHashMap.put("EMAIL_FROM_ADDRESS", this.mailFrom);
    localHashMap.put("IMAGING_RETAIL_ON", this.imagingRetailOn);
    localHashMap.put("IMAGING_RETAIL_OFFSETS", this.imagingRetailOffsets);
    localHashMap.put("IMAGING_BIZ_ON", this.imagingBizOn);
    localHashMap.put("IMAGING_BIZ_OFFSETS", this.imagingBizOffsets);
    localHashMap.put("STATEMENT_LOGO_PATH", this.logoPath);
    localHashMap.put("STATEMENT_MAX_IMAGES", this.stmtMaxImages);
    localHashMap.put("TRACEID_ERRORS", this.tag);
    return localHashMap;
  }
  
  public String getServerURL()
  {
    return this.serverURL;
  }
  
  public String getSMTPHost()
  {
    return this.smtpHost;
  }
  
  public String getEmailFrom()
  {
    return this.mailFrom;
  }
  
  public String getMaxAge()
  {
    return Integer.toString(this.maxAge);
  }
  
  public int getMaxAgeInt()
  {
    return this.maxAge;
  }
  
  public String getImageFee()
  {
    return this.fee;
  }
  
  public String getSearchDate()
  {
    return this.doNotSearchBeforeDate;
  }
  
  public String getFeeLogging()
  {
    return this.feeLogging;
  }
  
  public String getImageFeeMessageCode()
  {
    return this.image_fee_message_code;
  }
  
  public String getImageFeeMessageRetail()
  {
    return this.image_fee_message_retail;
  }
  
  public String getImageFeeMessageBiz()
  {
    return this.image_fee_message_biz;
  }
  
  public XMLTag getErrorTag()
  {
    return this.tag;
  }
  
  public String getImagingRetailOn()
  {
    return this.imagingRetailOn;
  }
  
  public String getImagingRetailOffsets()
  {
    return this.imagingRetailOffsets;
  }
  
  public String getImagingBizOn()
  {
    return this.imagingBizOn;
  }
  
  public String getImagingBizOffsets()
  {
    return this.imagingBizOffsets;
  }
  
  public String getLogoPath()
  {
    return this.logoPath;
  }
  
  public String getStmtMaxImages()
  {
    return this.stmtMaxImages;
  }
  
  public String getLogFileName()
  {
    return this.logFilename;
  }
  
  public String getTimeOut()
  {
    return this.timeout;
  }
  
  public String getMaxSearchItems()
  {
    return this.maxsearchitems;
  }
  
  public String getMaxImageItems()
  {
    return this.maximageitems;
  }
  
  public String getIgnoreTier()
  {
    return this.ignoretier;
  }
  
  public String getRepository()
  {
    return this.repository;
  }
  
  public String getFrontViewType()
  {
    return this.frontViewType;
  }
  
  public String getBackViewType()
  {
    return this.backViewType;
  }
  
  public String getCompression()
  {
    return this.compression;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.checkimaging.CheckImagingService
 * JD-Core Version:    0.7.0.1
 */