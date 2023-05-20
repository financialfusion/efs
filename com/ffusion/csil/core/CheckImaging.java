package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.checkimaging.ImageRequest;
import com.ffusion.beans.checkimaging.ImageResult;
import com.ffusion.beans.checkimaging.ImageResults;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.handlers.CheckImagingHandler;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.PerfLog;
import java.util.HashMap;

public class CheckImaging
  extends Initialize
{
  private static Entitlement aqO = new Entitlement("CheckImaging", null, null);
  private static final String aqP = "com.ffusion.util.logging.audit.checkimaging";
  private static final String aqQ = "AuditMessage_1";
  private static final String aqN = "AuditEntFault_1";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    CheckImagingHandler.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return CheckImagingHandler.getService();
  }
  
  public static ImageResults getImageIndex(SecureUser paramSecureUser, ImageRequest paramImageRequest, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImaging.getImageIndex";
    if (jdMethod_char(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      ImageResults localImageResults = CheckImagingHandler.getImageIndex(paramSecureUser, paramImageRequest, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localImageResults;
    }
    throw new CSILException(str, 20001);
  }
  
  public static ImageResult getImage(SecureUser paramSecureUser, ImageResult paramImageResult, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImaging.getImage";
    if (jdMethod_char(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      ImageResult localImageResult = CheckImagingHandler.getImage(paramSecureUser, paramImageResult, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localImageResult;
    }
    throw new CSILException(str, 20001);
  }
  
  public static ImageResult getImageStatus(SecureUser paramSecureUser, ImageResult paramImageResult, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImaging.getImageStatus";
    if (jdMethod_char(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      ImageResult localImageResult = CheckImagingHandler.getImageStatus(paramSecureUser, paramImageResult, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localImageResult;
    }
    throw new CSILException(str, 20001);
  }
  
  public static ImageResults getImagePackageId(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImaging.getImagePackageId";
    if (jdMethod_char(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      ImageResults localImageResults = CheckImagingHandler.getImagePackageId(paramSecureUser, paramImageResults, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localImageResults;
    }
    throw new CSILException(str, 20001);
  }
  
  public static ImageResults getPendingImages(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImaging.getPendingImages";
    if (jdMethod_char(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      ImageResults localImageResults = CheckImagingHandler.getPendingImages(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localImageResults;
    }
    throw new CSILException(str, 20001);
  }
  
  public static ImageResults setPendingImages(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImaging.setPendingImages";
    if (jdMethod_char(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      ImageResults localImageResults = CheckImagingHandler.setPendingImages(paramSecureUser, paramImageResults, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localImageResults;
    }
    throw new CSILException(str, 20001);
  }
  
  public static ImageResults emailPendingImageStatus(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImaging.emailPendingImageStatus";
    if (jdMethod_char(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      ImageResults localImageResults = CheckImagingHandler.emailPendingImageStatus(paramSecureUser, paramImageResults, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localImageResults;
    }
    throw new CSILException(str, 20001);
  }
  
  public static ImageResult emailImage(SecureUser paramSecureUser, ImageResult paramImageResult, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImaging.emailImage";
    if (jdMethod_char(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      ImageResult localImageResult = CheckImagingHandler.emailImage(paramSecureUser, paramImageResult, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localImageResult;
    }
    throw new CSILException(str, 20001);
  }
  
  public static ImageResult logFee(SecureUser paramSecureUser, ImageResult paramImageResult, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "CheckImaging.logFee";
    if (jdMethod_char(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = ((String)paramHashMap.get("ImageLogEntry"));
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.checkimaging", "AuditMessage_1", arrayOfObject);
      audit(paramSecureUser, localLocalizableString, str2, 3500);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return paramImageResult;
    }
    throw new CSILException(str1, 20001);
  }
  
  public static ImageResults deleteImageResults(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImaging.deleteImageResult";
    if (jdMethod_char(paramSecureUser))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      ImageResults localImageResults = CheckImagingHandler.deleteImageResults(paramSecureUser, paramImageResults, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localImageResults;
    }
    throw new CSILException(str, 20001);
  }
  
  public static ImageResults deleteImageRequests(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImaging.deleteImageRequests";
    if (jdMethod_char(paramSecureUser))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      ImageResults localImageResults = CheckImagingHandler.deleteImageRequests(paramSecureUser, paramImageResults, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localImageResults;
    }
    throw new CSILException(str, 20001);
  }
  
  public static HashMap getConfigProperties(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CheckImaging.getConfigProperties";
    if (jdMethod_char(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      HashMap localHashMap = CheckImagingHandler.getConfigProperties(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localHashMap;
    }
    throw new CSILException(str, 20001);
  }
  
  private static boolean jdMethod_char(SecureUser paramSecureUser)
    throws CSILException
  {
    boolean bool = Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aqO);
    if (!bool)
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.checkimaging", "AuditEntFault_1", null);
      Initialize.logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.CheckImaging
 * JD-Core Version:    0.7.0.1
 */