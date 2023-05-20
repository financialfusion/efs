package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.checkimaging.ImageRequest;
import com.ffusion.beans.checkimaging.ImageResult;
import com.ffusion.beans.checkimaging.ImageResults;
import com.ffusion.checkimaging.CheckImagingException;
import com.ffusion.csil.CSILException;
import com.ffusion.services.CheckImageService;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;

public class CheckImagingHandler
{
  private static final String jdField_if = "Check Imaging";
  private static CheckImageService a = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImagingHandler.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Check Imaging", str, 20107);
    a = (CheckImageService)HandlerUtil.instantiateService(localHashMap, str, 20107);
    try
    {
      a.initialize(paramHashMap);
    }
    catch (CheckImagingException localCheckImagingException)
    {
      CSILException localCSILException = new CSILException(29102, localCheckImagingException);
      DebugLog.throwing(str, localCheckImagingException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return a;
  }
  
  public static ImageResults getImageIndex(SecureUser paramSecureUser, ImageRequest paramImageRequest, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImagingHandler.getImageIndex";
    try
    {
      return a.getImageIndex(paramSecureUser, paramImageRequest, paramHashMap);
    }
    catch (CheckImagingException localCheckImagingException)
    {
      CSILException localCSILException = new CSILException(29104, localCheckImagingException);
      DebugLog.throwing("CheckImagingHandler.getImageIndex", localCheckImagingException);
      throw localCSILException;
    }
  }
  
  public static ImageResult getImage(SecureUser paramSecureUser, ImageResult paramImageResult, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImagingHandler.getImage";
    try
    {
      return a.getImage(paramSecureUser, paramImageResult, paramHashMap);
    }
    catch (CheckImagingException localCheckImagingException)
    {
      CSILException localCSILException = new CSILException(29105, localCheckImagingException);
      DebugLog.throwing("CheckImagingHandler.getImage", localCheckImagingException);
      throw localCSILException;
    }
  }
  
  public static ImageResult getImageStatus(SecureUser paramSecureUser, ImageResult paramImageResult, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImagingHandler.getImageStatus";
    try
    {
      return a.getImageStatus(paramSecureUser, paramImageResult, paramHashMap);
    }
    catch (CheckImagingException localCheckImagingException)
    {
      CSILException localCSILException = new CSILException(29106, localCheckImagingException);
      DebugLog.throwing("CheckImagingHandler.getImageStatus", localCheckImagingException);
      throw localCSILException;
    }
  }
  
  public static ImageResults getImagePackageId(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImagingHandler.getImagePackageId";
    try
    {
      return a.getImagePackageId(paramSecureUser, paramImageResults, paramHashMap);
    }
    catch (CheckImagingException localCheckImagingException)
    {
      CSILException localCSILException = new CSILException(29107, localCheckImagingException);
      DebugLog.throwing("CheckImagingHandler.getImagePackageId", localCheckImagingException);
      throw localCSILException;
    }
  }
  
  public static ImageResults getPendingImages(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImagingHandler.getPendingImages";
    try
    {
      return a.getPendingImages(paramSecureUser, paramHashMap);
    }
    catch (CheckImagingException localCheckImagingException)
    {
      CSILException localCSILException = new CSILException(29108, localCheckImagingException);
      DebugLog.throwing("CheckImagingHandler.getPendingImages", localCheckImagingException);
      throw localCSILException;
    }
  }
  
  public static ImageResults setPendingImages(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImagingHandler.setPendingImages";
    try
    {
      return a.setPendingImages(paramSecureUser, paramImageResults, paramHashMap);
    }
    catch (CheckImagingException localCheckImagingException)
    {
      CSILException localCSILException = new CSILException(29109, localCheckImagingException);
      DebugLog.throwing("CheckImagingHandler.setPendingImages", localCheckImagingException);
      throw localCSILException;
    }
  }
  
  public static ImageResults emailPendingImageStatus(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImagingHandler.emailPendingImageStatus";
    try
    {
      return a.emailPendingImageStatus(paramSecureUser, paramImageResults, paramHashMap);
    }
    catch (CheckImagingException localCheckImagingException)
    {
      CSILException localCSILException = new CSILException(29110, localCheckImagingException);
      DebugLog.throwing("CheckImagingHandler.emailPendingImageStatus", localCheckImagingException);
      throw localCSILException;
    }
  }
  
  public static ImageResult emailImage(SecureUser paramSecureUser, ImageResult paramImageResult, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImagingHandler.emailImage";
    try
    {
      return a.emailImage(paramSecureUser, paramImageResult, paramHashMap);
    }
    catch (CheckImagingException localCheckImagingException)
    {
      CSILException localCSILException = new CSILException(29111, localCheckImagingException);
      DebugLog.throwing("CheckImagingHandler.emailImage", localCheckImagingException);
      throw localCSILException;
    }
  }
  
  public static ImageResults deleteImageResults(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImagingHandler.deleteImageResults";
    try
    {
      return a.deleteImageResults(paramSecureUser, paramImageResults, paramHashMap);
    }
    catch (CheckImagingException localCheckImagingException)
    {
      CSILException localCSILException = new CSILException(29113, localCheckImagingException);
      DebugLog.throwing("CheckImagingHandler.deleteImageResults", localCheckImagingException);
      throw localCSILException;
    }
  }
  
  public static ImageResults deleteImageRequests(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImagingHandler.deleteImageRequests";
    try
    {
      return a.deleteImageRequests(paramSecureUser, paramImageResults, paramHashMap);
    }
    catch (CheckImagingException localCheckImagingException)
    {
      CSILException localCSILException = new CSILException(29114, localCheckImagingException);
      DebugLog.throwing("CheckImagingHandler.deleteImageRequests", localCheckImagingException);
      throw localCSILException;
    }
  }
  
  public static HashMap getConfigProperties(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImagingHandler.getConfigProperties";
    try
    {
      return a.getConfigProperties(paramSecureUser, paramHashMap);
    }
    catch (CheckImagingException localCheckImagingException)
    {
      CSILException localCSILException = new CSILException(29115, localCheckImagingException);
      DebugLog.throwing("CheckImagingHandler.getConfigProperties", localCheckImagingException);
      throw localCSILException;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.CheckImagingHandler
 * JD-Core Version:    0.7.0.1
 */