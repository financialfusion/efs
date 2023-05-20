package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fileimporter.FieldDefinitions;
import com.ffusion.beans.fileimporter.MappingDefinition;
import com.ffusion.beans.fileimporter.MappingDefinitions;
import com.ffusion.beans.fileimporter.OutputFormat;
import com.ffusion.csil.FIException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract interface FileImporter
{
  public static final String SERVICE_INIT_XML = "fileimporter.xml";
  public static final String SERVICE_INIT_BASE_FILE_NAME = "fileimporter";
  public static final String ADAPTER_SETTINGS_TAG = "AdapterSettingsTag";
  public static final String FILE_IMPORTER_ADAPTER = "FileImporterAdapter";
  public static final String FILE_ADAPTER = "FILEADAPTER";
  public static final String MESSAGE_BROKER = "MESSAGEBROKER";
  public static final String FILE_TYPE = "FILETYPE";
  public static final String ENTITLEMENT_NAME = "ENTITLEMENTNAME";
  public static final String PROCESS_DIR = "PROCESSDIR";
  public static final String ARCHIVE_DIR = "ARCHIVEDIR";
  public static final String ERROR_DIR = "ERRORDIR";
  public static final String ADAPTER_CLASS = "ADAPTERCLASS";
  public static final String DISPLAY_FOR_UPLOAD = "DISPLAYFORUPLOAD";
  public static final String ADAPTER_SETTINGS = "ADAPTERSETTINGS";
  public static final String DB_PROPERTIES = "DB_PROPERTIES";
  public static final String DISPLAY_NAME = "DISPLAYNAME";
  
  public abstract void initialize()
    throws FIException;
  
  public abstract ArrayList getUploadFileTypes(HashMap paramHashMap)
    throws FIException;
  
  public abstract ArrayList getAllFileTypes(HashMap paramHashMap)
    throws FIException;
  
  public abstract void upload(SecureUser paramSecureUser, byte[] paramArrayOfByte, String paramString1, String paramString2, HashMap paramHashMap)
    throws FIException;
  
  public abstract ArrayList getOutputFormatNames(HashMap paramHashMap)
    throws FIException;
  
  public abstract ArrayList getOutputFormatNamesByCategory(String paramString, HashMap paramHashMap)
    throws FIException;
  
  public abstract OutputFormat getOutputFormat(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws FIException;
  
  public abstract FieldDefinitions getFieldDefinitionList(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws FIException;
  
  public abstract void addMappingDefinition(SecureUser paramSecureUser, MappingDefinition paramMappingDefinition, HashMap paramHashMap)
    throws FIException;
  
  public abstract void removeMappingDefinition(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws FIException;
  
  public abstract void modifyMappingDefinition(SecureUser paramSecureUser, MappingDefinition paramMappingDefinition, HashMap paramHashMap)
    throws FIException;
  
  public abstract ArrayList getMappingNames(SecureUser paramSecureUser, HashMap paramHashMap)
    throws FIException;
  
  public abstract ArrayList getMappingNamesByCategory(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws FIException;
  
  public abstract MappingDefinition getMappingDefinition(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws FIException;
  
  public abstract MappingDefinitions getMappingDefinitions(SecureUser paramSecureUser, HashMap paramHashMap)
    throws FIException;
  
  public abstract void removeBusiness(int paramInt, HashMap paramHashMap)
    throws FIException;
  
  public abstract void processFiles(String paramString, HashMap paramHashMap)
    throws FIException;
  
  public abstract void cleanup(String paramString1, String paramString2, int paramInt, HashMap paramHashMap)
    throws FIException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.FileImporter
 * JD-Core Version:    0.7.0.1
 */