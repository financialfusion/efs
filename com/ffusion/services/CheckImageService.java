package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.checkimaging.ImageRequest;
import com.ffusion.beans.checkimaging.ImageResult;
import com.ffusion.beans.checkimaging.ImageResults;
import com.ffusion.checkimaging.CheckImagingException;
import com.ffusion.util.XMLTag;
import java.io.Serializable;
import java.util.HashMap;

public abstract interface CheckImageService
  extends Serializable
{
  public static final int ERROR_NONE = 0;
  public static final int SERVICE_ERROR_INVALID_SERVICE = 29100;
  public static final int SERVICE_NOT_INITIALIZED = 29101;
  public static final String IMAGE_LOG_ENTRY = "ImageLogEntry";
  
  public abstract void initialize(HashMap paramHashMap)
    throws CheckImagingException;
  
  public abstract ImageResults getImageIndex(SecureUser paramSecureUser, ImageRequest paramImageRequest, HashMap paramHashMap)
    throws CheckImagingException;
  
  public abstract ImageResult getImage(SecureUser paramSecureUser, ImageResult paramImageResult, HashMap paramHashMap)
    throws CheckImagingException;
  
  public abstract ImageResult getImageStatus(SecureUser paramSecureUser, ImageResult paramImageResult, HashMap paramHashMap)
    throws CheckImagingException;
  
  public abstract ImageResults getImagePackageId(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CheckImagingException;
  
  public abstract ImageResults getPendingImages(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CheckImagingException;
  
  public abstract ImageResults setPendingImages(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CheckImagingException;
  
  public abstract ImageResults emailPendingImageStatus(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CheckImagingException;
  
  public abstract ImageResult emailImage(SecureUser paramSecureUser, ImageResult paramImageResult, HashMap paramHashMap)
    throws CheckImagingException;
  
  public abstract ImageResults deleteImageResults(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CheckImagingException;
  
  public abstract ImageResults deleteImageRequests(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CheckImagingException;
  
  public abstract HashMap getConfigProperties(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CheckImagingException;
  
  public abstract String getServerURL();
  
  public abstract String getSMTPHost();
  
  public abstract String getEmailFrom();
  
  public abstract String getMaxAge();
  
  public abstract int getMaxAgeInt();
  
  public abstract String getImageFee();
  
  public abstract String getSearchDate();
  
  public abstract String getFeeLogging();
  
  public abstract String getImageFeeMessageCode();
  
  public abstract String getImageFeeMessageRetail();
  
  public abstract String getImageFeeMessageBiz();
  
  public abstract XMLTag getErrorTag();
  
  public abstract String getImagingRetailOn();
  
  public abstract String getImagingRetailOffsets();
  
  public abstract String getImagingBizOn();
  
  public abstract String getImagingBizOffsets();
  
  public abstract String getLogoPath();
  
  public abstract String getStmtMaxImages();
  
  public abstract String getLogFileName();
  
  public abstract String getTimeOut();
  
  public abstract String getMaxSearchItems();
  
  public abstract String getMaxImageItems();
  
  public abstract String getIgnoreTier();
  
  public abstract String getRepository();
  
  public abstract String getFrontViewType();
  
  public abstract String getBackViewType();
  
  public abstract String getCompression();
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.CheckImageService
 * JD-Core Version:    0.7.0.1
 */