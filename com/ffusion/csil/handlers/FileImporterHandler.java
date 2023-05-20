package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fileimporter.FieldDefinitions;
import com.ffusion.beans.fileimporter.MappingDefinition;
import com.ffusion.beans.fileimporter.MappingDefinitions;
import com.ffusion.beans.fileimporter.OutputFormat;
import com.ffusion.beans.fileimporter.OutputFormatDisplayNames;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.FIException;
import com.ffusion.services.FileImporter2;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;

public class FileImporterHandler
{
  private static final String jdField_if = "File Importer";
  private static FileImporter2 a = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "FileImporterHandler.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "File Importer", str, 20107);
    a = (FileImporter2)HandlerUtil.instantiateService(localHashMap, str, 20107);
    try
    {
      a.initialize();
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return a;
  }
  
  public static ArrayList getUploadFileTypes(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getUploadFileTypes(paramHashMap);
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing("FileImporterHandler.getUploadFileTypes", localCSILException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getUploadFileTypeDisplayNames(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getUploadFileTypeDisplayNames(paramString, paramHashMap);
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing("FileImporterHandler.getUploadFileTypeDisplayNames", localCSILException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getAllFileTypes(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getAllFileTypes(paramHashMap);
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing("FileImporterHandler.getUploadFileTypes", localCSILException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getOutputFormatNames(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      paramHashMap.put("SecureUser", paramSecureUser);
      return a.getOutputFormatNames(paramHashMap);
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing("FileImporterHandler.getOutputFormatNames", localCSILException);
      throw localCSILException;
    }
  }
  
  public static OutputFormatDisplayNames getOutputFormatDisplayNames(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      paramHashMap.put("SecureUser", paramSecureUser);
      return a.getOutputFormatDisplayNames(paramString, paramHashMap);
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing("FileImporterHandler.getOutputFormatDisplayNames", localCSILException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getOutputFormatNamesByCategory(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      paramHashMap.put("SecureUser", paramSecureUser);
      return a.getOutputFormatNamesByCategory(paramString, paramHashMap);
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing("FileImporterHandler.getOutputFormatNames", localCSILException);
      throw localCSILException;
    }
  }
  
  public static OutputFormatDisplayNames getOutputFormatDisplayNamesByCategory(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      paramHashMap.put("SecureUser", paramSecureUser);
      return a.getOutputFormatDisplayNamesByCategory(paramString1, paramString2, paramHashMap);
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing("FileImporterHandler.getOutputFormatDisplayNamesByCategory", localCSILException);
      throw localCSILException;
    }
  }
  
  public static void upload(SecureUser paramSecureUser, byte[] paramArrayOfByte, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a.upload(paramSecureUser, paramArrayOfByte, paramString1, paramString2, paramHashMap);
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing("FileImporterHandler.upload", localCSILException);
      throw localCSILException;
    }
  }
  
  public static OutputFormat getOutputFormat(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getOutputFormat(paramSecureUser, paramString, paramHashMap);
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing("FileImporterHandler.getOutputFormat", localCSILException);
      throw localCSILException;
    }
  }
  
  public static FieldDefinitions getFieldDefinitionList(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getFieldDefinitionList(paramSecureUser, paramString, paramHashMap);
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing("FileImporterHandler.getFieldDefinitionList", localCSILException);
      throw localCSILException;
    }
  }
  
  public static void addMappingDefinition(SecureUser paramSecureUser, MappingDefinition paramMappingDefinition, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a.addMappingDefinition(paramSecureUser, paramMappingDefinition, paramHashMap);
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing("FileImporterHandler.addMappingDefinition", localCSILException);
      throw localCSILException;
    }
  }
  
  public static void removeMappingDefinition(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a.removeMappingDefinition(paramSecureUser, paramInt, paramHashMap);
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing("FileImporterHandler.removeMappingDefinition", localCSILException);
      throw localCSILException;
    }
  }
  
  public static void modifyMappingDefinition(SecureUser paramSecureUser, MappingDefinition paramMappingDefinition, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a.modifyMappingDefinition(paramSecureUser, paramMappingDefinition, paramHashMap);
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing("FileImporterHandler.modifyMappingDefinition", localCSILException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getMappingNames(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getMappingNames(paramSecureUser, paramHashMap);
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing("FileImporterHandler.getMappingNames", localCSILException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getMappingNamesByCategory(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getMappingNamesByCategory(paramSecureUser, paramString, paramHashMap);
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing("FileImporterHandler.getMappingNamesByCategory", localCSILException);
      throw localCSILException;
    }
  }
  
  public static MappingDefinition getMappingDefinition(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getMappingDefinition(paramSecureUser, paramString, paramHashMap);
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing("FileImporterHandler.getMappingDefinition", localCSILException);
      throw localCSILException;
    }
  }
  
  public static MappingDefinitions getMappingDefinitions(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getMappingDefinitions(paramSecureUser, paramHashMap);
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing("FileImporterHandler.getMappingDefinitions", localCSILException);
      throw localCSILException;
    }
  }
  
  public static void removeBusiness(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a.removeBusiness(paramInt, paramHashMap);
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing("FileImporterHandler.removeBusiness", localCSILException);
      throw localCSILException;
    }
  }
  
  public static void processFiles(String paramString, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a.processFiles(paramString, paramHashMap);
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing("FileImporterHandler.processFiles", localCSILException);
      throw localCSILException;
    }
  }
  
  public static void processFiles(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    processFiles(paramString, paramHashMap);
  }
  
  public static void cleanup(String paramString1, String paramString2, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a.cleanup(paramString1, paramString2, paramInt, paramHashMap);
    }
    catch (FIException localFIException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localFIException), localFIException);
      DebugLog.throwing("FileImporterHandler.cleanup", localCSILException);
      throw localCSILException;
    }
  }
  
  private static int a(FIException paramFIException)
  {
    switch (paramFIException.getCode())
    {
    case 0: 
      return 20108;
    case 1: 
      return 20113;
    case 2: 
      return 20109;
    case 3: 
      return 20112;
    case 4: 
      return 20108;
    case 5: 
      return 20111;
    case 6: 
      return 20111;
    case 7: 
      return 20112;
    case 9604: 
      return 22029;
    }
    return 20112;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.FileImporterHandler
 * JD-Core Version:    0.7.0.1
 */