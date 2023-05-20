package com.ffusion.services.dummy;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fileimporter.FieldDefinitions;
import com.ffusion.beans.fileimporter.MappingDefinition;
import com.ffusion.beans.fileimporter.MappingDefinitions;
import com.ffusion.beans.fileimporter.OutputFormat;
import com.ffusion.csil.FIException;
import com.ffusion.fileimporter.fileadapters.IFileAdapter;
import com.ffusion.services.FileImporter;
import com.ffusion.services.fileimporter.FileAdapterInfo;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class DummyFileImporterService
  implements FileImporter
{
  public void initialize()
    throws FIException
  {}
  
  public ArrayList getUploadFileTypes(HashMap paramHashMap)
    throws FIException
  {
    return null;
  }
  
  public ArrayList getAllFileTypes(HashMap paramHashMap)
    throws FIException
  {
    return null;
  }
  
  public ArrayList getOutputFormatNames(HashMap paramHashMap)
    throws FIException
  {
    return null;
  }
  
  public ArrayList getOutputFormatNamesByCategory(String paramString, HashMap paramHashMap)
    throws FIException
  {
    return null;
  }
  
  public void upload(SecureUser paramSecureUser, byte[] paramArrayOfByte, String paramString1, String paramString2, HashMap paramHashMap)
    throws FIException
  {}
  
  private void jdMethod_if(byte[] paramArrayOfByte, File paramFile, String paramString, FileAdapterInfo paramFileAdapterInfo, IFileAdapter paramIFileAdapter, HashMap paramHashMap)
    throws FIException
  {}
  
  public OutputFormat getOutputFormat(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws FIException
  {
    return null;
  }
  
  public FieldDefinitions getFieldDefinitionList(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws FIException
  {
    return null;
  }
  
  public void addMappingDefinition(SecureUser paramSecureUser, MappingDefinition paramMappingDefinition, HashMap paramHashMap)
    throws FIException
  {}
  
  public void removeMappingDefinition(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws FIException
  {}
  
  public void modifyMappingDefinition(SecureUser paramSecureUser, MappingDefinition paramMappingDefinition, HashMap paramHashMap)
    throws FIException
  {}
  
  public ArrayList getMappingNames(SecureUser paramSecureUser, HashMap paramHashMap)
    throws FIException
  {
    return null;
  }
  
  public ArrayList getMappingNamesByCategory(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws FIException
  {
    return null;
  }
  
  public MappingDefinition getMappingDefinition(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws FIException
  {
    return null;
  }
  
  public MappingDefinitions getMappingDefinitions(SecureUser paramSecureUser, HashMap paramHashMap)
    throws FIException
  {
    return null;
  }
  
  public void removeBusiness(int paramInt, HashMap paramHashMap)
    throws FIException
  {}
  
  public void processFiles(String paramString, HashMap paramHashMap)
    throws FIException
  {}
  
  public void cleanup(String paramString1, String paramString2, int paramInt, HashMap paramHashMap)
    throws FIException
  {}
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.dummy.DummyFileImporterService
 * JD-Core Version:    0.7.0.1
 */