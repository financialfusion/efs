package com.ffusion.services.fileimporter;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fileimporter.FieldDefinitions;
import com.ffusion.beans.fileimporter.FileTypeDisplayName;
import com.ffusion.beans.fileimporter.FileTypeDisplayNames;
import com.ffusion.beans.fileimporter.FileTypeEntitlement;
import com.ffusion.beans.fileimporter.MappingDefinition;
import com.ffusion.beans.fileimporter.MappingDefinitions;
import com.ffusion.beans.fileimporter.OutputFormat;
import com.ffusion.beans.fileimporter.OutputFormatDisplayNames;
import com.ffusion.csil.FIException;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.efs.adapters.fileimporter.FileImporterAdapter;
import com.ffusion.fileimporter.fileadapters.IFileAdapter;
import com.ffusion.fileimporter.fileadapters.banklookup.SwiftAdapter;
import com.ffusion.fileimporter.fileadapters.custom.ICustomAdapter;
import com.ffusion.services.FileImporter2;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.LocaleUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditLog;
import com.ffusion.util.logging.DebugLog;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

public class FileImporterService
  implements FileImporter2
{
  private static final String jdField_case = "com.ffusion.util.logging.audit.fileimporterservice";
  private static final String jdField_void = "AuditMessage_1";
  private static final String jdField_try = "AuditMessage_2";
  private static final String jdField_else = ".err";
  private static final String jdField_long = "process";
  private static final String a = "archive";
  private static final String jdField_int = "error";
  private static final String jdField_for = "-";
  private static final String jdField_if = "yyyyMMdd";
  private static final String jdField_byte = "HHmmss";
  private static final long jdField_char = 86400000L;
  private static final String jdField_do = "FILE_PATH";
  private static final String jdField_new = ".xml";
  private static final String jdField_goto = "\\/:*?\"<>|'&;~`#()";
  private static FileImporterService jdField_null;
  protected HashMap _initHashMap = null;
  protected HashMap _typeToAdapterMap = null;
  protected ArrayList _uploadFileTypes = null;
  protected ArrayList _allFileTypes = null;
  protected FileImporterAdapter _fileImporterAdapter = null;
  protected HashMap _allLocalizedFileTypes = null;
  private long c;
  private RandomAccessFile b;
  
  public FileImporterService()
  {
    jdField_null = this;
  }
  
  public static FileImporterService getInstance()
  {
    return jdField_null;
  }
  
  private HashMap jdField_if()
    throws Exception
  {
    HashMap localHashMap1 = null;
    ArrayList localArrayList = LocaleUtil.getXMLFilesWithBase("fileimporter");
    if ((localArrayList != null) && (!localArrayList.isEmpty()))
    {
      InputStream localInputStream = null;
      String str1 = null;
      String str2 = null;
      String str3 = null;
      XMLTag localXMLTag1 = null;
      HashMap localHashMap2 = null;
      String str4 = null;
      String str5 = null;
      XMLTag localXMLTag2 = null;
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext()) {
        try
        {
          str1 = (String)localIterator.next();
          str3 = a(str1);
          localInputStream = ResourceUtil.getResourceAsStream(this, str1);
          str2 = Strings.streamToString(localInputStream, "UTF-8");
          XMLTag localXMLTag3 = new XMLTag(true);
          localXMLTag3.build(str2);
          localXMLTag1 = localXMLTag3.getContainedTag("FILEADAPTER");
          HashMap localHashMap3 = null;
          while (localXMLTag1 != null)
          {
            localHashMap2 = localXMLTag1.getTagHashMap();
            str4 = (String)localHashMap2.get("FILETYPE");
            str5 = (String)localHashMap2.get("DISPLAYNAME");
            localXMLTag2 = localXMLTag1.getContainedTag("ADAPTERSETTINGS");
            FileTypeDisplayName localFileTypeDisplayName = new FileTypeDisplayName(str3, str4, str5);
            a locala = new a(localFileTypeDisplayName, localXMLTag2);
            if (localHashMap3 == null) {
              localHashMap3 = new HashMap();
            }
            localHashMap3.put(str4, locala);
            localXMLTag3.removeContainedTag("FILEADAPTER");
            localXMLTag1 = localXMLTag3.getContainedTag("FILEADAPTER");
          }
          if ((localHashMap3 != null) && (!localHashMap3.isEmpty()))
          {
            if (localHashMap1 == null) {
              localHashMap1 = new HashMap();
            }
            localHashMap1.put(str3, localHashMap3);
          }
          localInputStream.close();
        }
        finally
        {
          localInputStream.close();
        }
      }
    }
    return localHashMap1;
  }
  
  private static void a(String paramString, HashMap paramHashMap1, HashMap paramHashMap2, HashMap paramHashMap3)
  {
    String str1 = null;
    HashMap localHashMap = null;
    String str2 = null;
    a locala = null;
    Iterator localIterator = paramHashMap1.keySet().iterator();
    while (localIterator.hasNext())
    {
      str1 = (String)localIterator.next();
      localHashMap = (HashMap)paramHashMap1.get(str1);
      locala = (a)localHashMap.get(paramString);
      if (locala != null)
      {
        FileTypeDisplayNames localFileTypeDisplayNames = (FileTypeDisplayNames)paramHashMap2.get(str1);
        if (localFileTypeDisplayNames == null) {
          localFileTypeDisplayNames = new FileTypeDisplayNames();
        }
        localFileTypeDisplayNames.add(locala.jdField_if);
        paramHashMap2.put(str1, localFileTypeDisplayNames);
        if (locala.a != null)
        {
          str2 = "AdapterSettingsTag" + str1;
          paramHashMap3.put(str2, locala.a);
        }
      }
    }
  }
  
  private static String a(String paramString)
  {
    String str = null;
    if (paramString != null)
    {
      int i = paramString.indexOf("fileimporter");
      if (i >= 0)
      {
        i += "fileimporter".length() + 1;
        int j = paramString.indexOf(".xml");
        if (j > i) {
          str = paramString.substring(i, j);
        }
      }
    }
    return str;
  }
  
  public void initialize()
    throws FIException
  {
    InputStream localInputStream = null;
    try
    {
      if (!this._initHashMap.containsKey("fileimporter.xml"))
      {
        this._allLocalizedFileTypes = new HashMap();
        HashMap localHashMap1 = jdField_if();
        localInputStream = ResourceUtil.getResourceAsStream(this, "fileimporter.xml");
        if (localInputStream != null)
        {
          localObject1 = Strings.streamToString(localInputStream, "UTF-8");
          XMLTag localXMLTag1 = new XMLTag(true);
          localXMLTag1.build((String)localObject1);
          XMLTag localXMLTag2 = localXMLTag1.getContainedTag("DB_PROPERTIES");
          if (localXMLTag2 != null)
          {
            this._fileImporterAdapter = new FileImporterAdapter();
            this._fileImporterAdapter.initialize(localXMLTag2.getTagHashMap());
          }
          XMLTag localXMLTag3 = localXMLTag1.getContainedTag("FILEADAPTER");
          FileTypeEntitlement localFileTypeEntitlement = null;
          while (localXMLTag3 != null)
          {
            HashMap localHashMap2 = localXMLTag3.getTagHashMap();
            String str1 = (String)localHashMap2.get("FILETYPE");
            String str2 = (String)localHashMap2.get("ENTITLEMENTNAME");
            FileAdapterInfo localFileAdapterInfo = new FileAdapterInfo();
            localFileAdapterInfo.processDir = ((String)localHashMap2.get("PROCESSDIR"));
            localFileAdapterInfo.archiveDir = ((String)localHashMap2.get("ARCHIVEDIR"));
            localFileAdapterInfo.errorDir = ((String)localHashMap2.get("ERRORDIR"));
            localFileAdapterInfo.className = ((String)localHashMap2.get("ADAPTERCLASS"));
            String str3 = (String)localHashMap2.get("DISPLAYFORUPLOAD");
            localFileTypeEntitlement = new FileTypeEntitlement(str1, str2);
            this._allFileTypes.add(localFileTypeEntitlement);
            if (str3.equalsIgnoreCase("true")) {
              this._uploadFileTypes.add(localFileTypeEntitlement);
            }
            Object localObject2 = null;
            try
            {
              Class localClass = Class.forName(localFileAdapterInfo.className);
              localObject2 = localClass.newInstance();
            }
            catch (Exception localException2)
            {
              localObject4 = new FIException("FileImporterService: Failed to initialize class " + localFileAdapterInfo.className + ".", 3, localException2);
              DebugLog.throwing("FileImporterService.initialize", (Throwable)localObject4);
              throw ((Throwable)localObject4);
            }
            localFileAdapterInfo.fileAdapterInstance = ((IFileAdapter)localObject2);
            Object localObject3 = localHashMap2.get("ADAPTERSETTINGS");
            if ((localObject3 == null) || ((localObject3 instanceof String))) {
              localObject3 = new HashMap();
            }
            Object localObject4 = localXMLTag3.getContainedTag("ADAPTERSETTINGS");
            ((HashMap)localObject3).put("AdapterSettingsTag", localObject4);
            if ((localHashMap1 != null) && (!localHashMap1.isEmpty())) {
              a(str1, localHashMap1, this._allLocalizedFileTypes, (HashMap)localObject3);
            }
            try
            {
              ((IFileAdapter)localObject2).initialize((HashMap)localObject3);
              this._typeToAdapterMap.put(str1, localFileAdapterInfo);
            }
            catch (Exception localException3)
            {
              DebugLog.log(Level.WARNING, "Error initializing " + str1 + " adapter: " + localException3.getMessage());
            }
            localXMLTag1.removeContainedTag("FILEADAPTER");
            localXMLTag3 = localXMLTag1.getContainedTag("FILEADAPTER");
          }
          Collections.sort(this._allFileTypes);
          Collections.sort(this._uploadFileTypes);
        }
      }
    }
    catch (Exception localException1)
    {
      Object localObject1 = new FIException(3, localException1);
      DebugLog.throwing("FileImporterService.initialize", (Throwable)localObject1);
      throw ((Throwable)localObject1);
    }
    finally
    {
      if (localInputStream != null) {
        try
        {
          localInputStream.close();
        }
        catch (Exception localException4) {}
      }
    }
  }
  
  public ArrayList getUploadFileTypes(HashMap paramHashMap)
    throws FIException
  {
    if (this._uploadFileTypes != null) {
      return this._uploadFileTypes;
    }
    FIException localFIException = new FIException("FileImporterService getUploadFileTypes failed.", 0);
    DebugLog.throwing("FileImporterService.getUploadFileTypes", localFIException);
    throw localFIException;
  }
  
  public ArrayList getUploadFileTypeDisplayNames(String paramString, HashMap paramHashMap)
    throws FIException
  {
    if ((paramString == null) || ("en_US".equals(paramString))) {
      return getUploadFileTypes(paramHashMap);
    }
    FileTypeDisplayNames localFileTypeDisplayNames = (FileTypeDisplayNames)this._allLocalizedFileTypes.get(paramString);
    if ((localFileTypeDisplayNames != null) && (this._uploadFileTypes != null))
    {
      localObject = null;
      FileTypeDisplayName localFileTypeDisplayName = null;
      Iterator localIterator = this._uploadFileTypes.iterator();
      ArrayList localArrayList = new ArrayList();
      while (localIterator.hasNext())
      {
        localObject = (FileTypeEntitlement)localIterator.next();
        localFileTypeDisplayName = localFileTypeDisplayNames.getByName(((FileTypeEntitlement)localObject).getFileType());
        if (localFileTypeDisplayName != null)
        {
          FileTypeEntitlement localFileTypeEntitlement = new FileTypeEntitlement(((FileTypeEntitlement)localObject).getFileType(), ((FileTypeEntitlement)localObject).getEntitlementName(), localFileTypeDisplayName.getDisplayName());
          localArrayList.add(localFileTypeEntitlement);
        }
      }
      return localArrayList;
    }
    Object localObject = new FIException("FileImporterService getUploadFileTypeDisplayNames failed.", 0);
    DebugLog.throwing("FileImporterService.getUploadFileTypeDisplayNames", (Throwable)localObject);
    throw ((Throwable)localObject);
  }
  
  public ArrayList getAllFileTypes(HashMap paramHashMap)
    throws FIException
  {
    if (this._allFileTypes != null) {
      return this._allFileTypes;
    }
    FIException localFIException = new FIException("FileImporterService getAllFileTypes failed.", 0);
    DebugLog.throwing("FileImporterService.getAllFileTypes", localFIException);
    throw localFIException;
  }
  
  public ArrayList getOutputFormatNames(HashMap paramHashMap)
    throws FIException
  {
    Iterator localIterator = this._typeToAdapterMap.values().iterator();
    while (localIterator.hasNext())
    {
      FileAdapterInfo localFileAdapterInfo = (FileAdapterInfo)localIterator.next();
      if ((localFileAdapterInfo.fileAdapterInstance instanceof ICustomAdapter))
      {
        ICustomAdapter localICustomAdapter = (ICustomAdapter)localFileAdapterInfo.fileAdapterInstance;
        return localICustomAdapter.getOutputFormatNames(paramHashMap);
      }
    }
    return null;
  }
  
  public OutputFormatDisplayNames getOutputFormatDisplayNames(String paramString, HashMap paramHashMap)
    throws FIException
  {
    Iterator localIterator = this._typeToAdapterMap.values().iterator();
    while (localIterator.hasNext())
    {
      FileAdapterInfo localFileAdapterInfo = (FileAdapterInfo)localIterator.next();
      if ((localFileAdapterInfo.fileAdapterInstance instanceof ICustomAdapter))
      {
        ICustomAdapter localICustomAdapter = (ICustomAdapter)localFileAdapterInfo.fileAdapterInstance;
        return localICustomAdapter.getOutputFormatDisplayNames(paramString, paramHashMap);
      }
    }
    return null;
  }
  
  public ArrayList getOutputFormatNamesByCategory(String paramString, HashMap paramHashMap)
    throws FIException
  {
    Iterator localIterator = this._typeToAdapterMap.values().iterator();
    while (localIterator.hasNext())
    {
      FileAdapterInfo localFileAdapterInfo = (FileAdapterInfo)localIterator.next();
      if ((localFileAdapterInfo.fileAdapterInstance instanceof ICustomAdapter))
      {
        ICustomAdapter localICustomAdapter = (ICustomAdapter)localFileAdapterInfo.fileAdapterInstance;
        return localICustomAdapter.getOutputFormatNamesByCategory(paramString, paramHashMap);
      }
    }
    return null;
  }
  
  public OutputFormatDisplayNames getOutputFormatDisplayNamesByCategory(String paramString1, String paramString2, HashMap paramHashMap)
    throws FIException
  {
    Iterator localIterator = this._typeToAdapterMap.values().iterator();
    while (localIterator.hasNext())
    {
      FileAdapterInfo localFileAdapterInfo = (FileAdapterInfo)localIterator.next();
      if ((localFileAdapterInfo.fileAdapterInstance instanceof ICustomAdapter))
      {
        ICustomAdapter localICustomAdapter = (ICustomAdapter)localFileAdapterInfo.fileAdapterInstance;
        return localICustomAdapter.getOutputFormatDisplayNamesByCategory(paramString1, paramString2, paramHashMap);
      }
    }
    return null;
  }
  
  public void upload(SecureUser paramSecureUser, byte[] paramArrayOfByte, String paramString1, String paramString2, HashMap paramHashMap)
    throws FIException
  {
    if (!this._typeToAdapterMap.containsKey(paramString1))
    {
      localObject = new FIException(0);
      DebugLog.throwing("FileImporterService.processFiles", (Throwable)localObject);
      throw ((Throwable)localObject);
    }
    Object localObject = (FileAdapterInfo)this._typeToAdapterMap.get(paramString1);
    String str1 = a(System.currentTimeMillis(), "yyyyMMdd");
    String str2 = a(System.currentTimeMillis(), "HHmmss");
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramSecureUser.getBPWFIID() != null) {
      localStringBuffer.append(paramSecureUser.getBPWFIID()).append("-");
    }
    if (paramSecureUser.getBusinessCustId() != null) {
      localStringBuffer.append(jdField_if(paramSecureUser.getBusinessCustId())).append("-");
    }
    localStringBuffer.append(paramSecureUser.getUserName()).append("-");
    localStringBuffer.append(str1).append("-");
    localStringBuffer.append(str2).append("-");
    localStringBuffer.append(paramString2);
    String str3 = localStringBuffer.toString();
    IFileAdapter localIFileAdapter = ((FileAdapterInfo)localObject).fileAdapterInstance;
    File localFile = new File(((FileAdapterInfo)localObject).processDir);
    if (localFile.exists())
    {
      if (!localFile.isDirectory()) {
        throw new FIException(7);
      }
    }
    else {
      localFile.mkdirs();
    }
    paramHashMap.put("USER", paramSecureUser);
    paramHashMap.put("FileImporterAdapter", this._fileImporterAdapter);
    paramHashMap.put("FILETYPE", paramString1);
    a(paramArrayOfByte, localFile, str3, (FileAdapterInfo)localObject, localIFileAdapter, paramHashMap);
  }
  
  private void a(byte[] paramArrayOfByte, File paramFile, String paramString, FileAdapterInfo paramFileAdapterInfo, IFileAdapter paramIFileAdapter, HashMap paramHashMap)
    throws FIException
  {
    String str1 = a(System.currentTimeMillis(), "yyyyMMdd");
    String str2 = a(System.currentTimeMillis(), "HHmmss");
    String str3 = paramFileAdapterInfo.errorDir;
    String str4 = paramFileAdapterInfo.archiveDir;
    File localFile1 = new File(paramFile, paramString);
    FileOutputStream localFileOutputStream = null;
    FileInputStream localFileInputStream = null;
    try
    {
      localFile1 = new File(paramFile, paramString);
      localFileOutputStream = new FileOutputStream(localFile1);
      localFileOutputStream.write(paramArrayOfByte);
      localFileOutputStream.close();
      localFileOutputStream = null;
      try
      {
        if ((paramIFileAdapter instanceof SwiftAdapter))
        {
          HashMap localHashMap = jdField_if(localFile1);
          if (localHashMap != null)
          {
            localObject1 = (SwiftAdapter)paramIFileAdapter;
            ((SwiftAdapter)localObject1).initCountries(localHashMap);
          }
        }
      }
      catch (Exception localException1)
      {
        DebugLog.log("Error while loading countries");
        localException1.printStackTrace();
      }
      localFileInputStream = new FileInputStream(localFile1);
      paramIFileAdapter.processFile(localFileInputStream, paramHashMap);
      localFileInputStream.close();
      localFileInputStream = null;
      File localFile2 = new File(str4);
      if (!localFile2.exists()) {
        localFile2.mkdirs();
      }
      if (localFile2.isDirectory())
      {
        localObject1 = (ArrayList)paramHashMap.get("ImportErrors");
        if ((localObject1 == null) || (((ArrayList)localObject1).isEmpty()))
        {
          localObject2 = new File(localFile2, paramString);
          a(localFile1, (File)localObject2);
        }
        else
        {
          localObject2 = new StringBuffer();
          for (int i = 0; i < ((ArrayList)localObject1).size(); i++) {
            ((StringBuffer)localObject2).append(((ArrayList)localObject1).get(i).toString());
          }
          localObject3 = ((StringBuffer)localObject2).toString();
          a(str3, paramString, paramArrayOfByte, (String)localObject3);
        }
      }
      else
      {
        throw new FIException(7);
      }
    }
    catch (FIException localFIException)
    {
      DebugLog.throwing("FileImporterService.doUploadIO", localFIException);
      localObject1 = a(str1, str2, localFIException);
      localObject2 = ((StringBuffer)localObject1).toString();
      a(str3, paramString, paramArrayOfByte, (String)localObject2);
      throw localFIException;
    }
    catch (Exception localException2)
    {
      DebugLog.throwing("FileImporterService.doUploadIO", localException2);
      Object localObject1 = a(str1, str2, localException2);
      Object localObject2 = ((StringBuffer)localObject1).toString();
      a(str3, paramString, paramArrayOfByte, (String)localObject2);
      Object localObject3 = new FIException(2, localException2);
      DebugLog.throwing("FileImporterService.doUploadIO", (Throwable)localObject3);
      throw ((Throwable)localObject3);
    }
    finally
    {
      if (localFileInputStream != null) {
        try
        {
          localFileInputStream.close();
        }
        catch (Exception localException3) {}
      }
      if (localFileOutputStream != null) {
        try
        {
          localFileOutputStream.close();
        }
        catch (Exception localException4) {}
      }
      if ((localFile1 != null) && (localFile1.exists())) {
        localFile1.delete();
      }
    }
  }
  
  private void a(File paramFile, String paramString1, String paramString2, IFileAdapter paramIFileAdapter, HashMap paramHashMap)
    throws FIException
  {
    if (paramFile == null) {
      return;
    }
    String str1 = a(System.currentTimeMillis(), "yyyyMMdd");
    String str2 = a(System.currentTimeMillis(), "HHmmss");
    Object localObject1 = null;
    FileInputStream localFileInputStream = null;
    String str3 = paramFile.getName();
    Object localObject2;
    try
    {
      localFileInputStream = new FileInputStream(paramFile);
    }
    catch (Exception localException1)
    {
      localObject2 = new FIException(7, localException1);
      DebugLog.throwing("FileImporterService.doUploadIO", (Throwable)localObject2);
      a(paramString2, paramFile.getName(), (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    try
    {
      if ((paramIFileAdapter instanceof SwiftAdapter))
      {
        HashMap localHashMap = jdField_if(paramFile);
        if (localHashMap != null)
        {
          localObject2 = (SwiftAdapter)paramIFileAdapter;
          ((SwiftAdapter)localObject2).initCountries(localHashMap);
        }
      }
    }
    catch (Exception localException2)
    {
      DebugLog.log("Error while loading countries");
      localException2.printStackTrace();
    }
    try
    {
      try
      {
        paramIFileAdapter.processFile(localFileInputStream, paramHashMap);
      }
      finally
      {
        localFileInputStream.close();
        localFileInputStream = null;
      }
      File localFile = new File(paramString1);
      if (!localFile.exists()) {
        localFile.mkdirs();
      }
      if (localFile.isDirectory())
      {
        localObject2 = (ArrayList)paramHashMap.get("ImportErrors");
        if ((localObject2 == null) || (((ArrayList)localObject2).isEmpty()))
        {
          localObject4 = new File(localFile, str3);
          a(paramFile, (File)localObject4);
        }
        else
        {
          localObject4 = new StringBuffer();
          for (int i = 0; i < ((ArrayList)localObject2).size(); i++) {
            ((StringBuffer)localObject4).append(((ArrayList)localObject2).get(i).toString());
          }
          localObject5 = ((StringBuffer)localObject4).toString();
          a(paramString2, str3, paramFile, (String)localObject5);
        }
      }
      else
      {
        throw new FIException(7);
      }
    }
    catch (FIException localFIException)
    {
      DebugLog.throwing("FileImporterService.doUploadIO", localFIException);
      localObject2 = a(str1, str2, localFIException);
      localObject4 = ((StringBuffer)localObject2).toString();
      a(paramString2, str3, paramFile, (String)localObject4);
      throw localFIException;
    }
    catch (Exception localException3)
    {
      DebugLog.throwing("FileImporterService.doUploadIO", localException3);
      localObject2 = a(str1, str2, localException3);
      Object localObject4 = ((StringBuffer)localObject2).toString();
      a(paramString2, str3, paramFile, (String)localObject4);
      Object localObject5 = new FIException(2, localException3);
      DebugLog.throwing("FileImporterService.doUploadIO", (Throwable)localObject5);
      throw ((Throwable)localObject5);
    }
    finally
    {
      if (localFileInputStream != null) {
        try
        {
          localFileInputStream.close();
        }
        catch (Exception localException4) {}
      }
      if (localObject1 != null) {
        try
        {
          localObject1.close();
        }
        catch (Exception localException5) {}
      }
      try
      {
        paramFile.delete();
      }
      catch (Exception localException6) {}
    }
  }
  
  private void a(File paramFile1, File paramFile2)
    throws IOException, SecurityException
  {
    Object localObject1 = new FileOutputStream(paramFile2);
    localObject1 = new BufferedOutputStream((OutputStream)localObject1);
    byte[] arrayOfByte = new byte[32768];
    Object localObject2 = new FileInputStream(paramFile1);
    localObject2 = new BufferedInputStream((InputStream)localObject2);
    for (int i = ((InputStream)localObject2).read(arrayOfByte); i > 0; i = ((InputStream)localObject2).read(arrayOfByte)) {
      ((OutputStream)localObject1).write(arrayOfByte, 0, i);
    }
    ((OutputStream)localObject1).flush();
    ((OutputStream)localObject1).close();
    ((InputStream)localObject2).close();
  }
  
  private void a(String paramString1, String paramString2, byte[] paramArrayOfByte, String paramString3)
    throws FIException
  {
    File localFile1 = new File(paramString1);
    if (!localFile1.exists()) {
      localFile1.mkdirs();
    }
    Object localObject = null;
    if (localFile1.isDirectory())
    {
      File localFile2 = new File(localFile1, paramString2);
      File localFile3 = new File(localFile1, paramString2 + ".err");
      a(localFile2, paramArrayOfByte);
      a(localFile3, paramString3);
    }
    else
    {
      throw new FIException(7);
    }
  }
  
  private void a(String paramString1, String paramString2, Throwable paramThrowable)
    throws FIException
  {
    File localFile1 = new File(paramString1);
    if (!localFile1.exists()) {
      localFile1.mkdirs();
    }
    Object localObject = null;
    if (localFile1.isDirectory())
    {
      File localFile2 = new File(localFile1, paramString2 + ".err");
      a(localFile2, paramThrowable);
    }
    else
    {
      throw new FIException(7);
    }
  }
  
  private void a(String paramString1, String paramString2, byte[] paramArrayOfByte, Throwable paramThrowable)
    throws FIException
  {
    File localFile1 = new File(paramString1);
    if (!localFile1.exists()) {
      localFile1.mkdirs();
    }
    Object localObject = null;
    if (localFile1.isDirectory())
    {
      File localFile2 = new File(localFile1, paramString2);
      File localFile3 = new File(localFile1, paramString2 + ".err");
      a(localFile2, paramArrayOfByte);
      a(localFile3, paramThrowable);
    }
    else
    {
      throw new FIException(7);
    }
  }
  
  private void a(String paramString1, String paramString2, File paramFile, String paramString3)
    throws FIException
  {
    File localFile1 = new File(paramString1);
    if (!localFile1.exists()) {
      localFile1.mkdirs();
    }
    Object localObject = null;
    if (localFile1.isDirectory())
    {
      File localFile2 = new File(localFile1, paramString2);
      File localFile3 = new File(localFile1, paramString2 + ".err");
      try
      {
        a(paramFile, localFile2);
      }
      catch (SecurityException localSecurityException)
      {
        throw new FIException(7, localSecurityException);
      }
      catch (IOException localIOException)
      {
        throw new FIException(7, localIOException);
      }
      a(localFile3, paramString3);
    }
    else
    {
      throw new FIException(7);
    }
  }
  
  private void a(File paramFile, String paramString)
  {
    a(paramFile, paramString.getBytes());
  }
  
  private void a(File paramFile, byte[] paramArrayOfByte)
  {
    BufferedOutputStream localBufferedOutputStream = null;
    try
    {
      localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(paramFile));
      localBufferedOutputStream.write(paramArrayOfByte);
    }
    catch (FileNotFoundException localFileNotFoundException) {}catch (IOException localIOException) {}finally
    {
      if (localBufferedOutputStream != null) {
        try
        {
          localBufferedOutputStream.close();
        }
        catch (Exception localException) {}
      }
    }
  }
  
  private void a(File paramFile, Throwable paramThrowable)
  {
    PrintStream localPrintStream = null;
    BufferedOutputStream localBufferedOutputStream = null;
    try
    {
      localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(paramFile));
      localPrintStream = new PrintStream(localBufferedOutputStream);
      paramThrowable.printStackTrace(localPrintStream);
    }
    catch (FileNotFoundException localFileNotFoundException) {}catch (IOException localIOException) {}finally
    {
      if (localPrintStream != null) {
        try
        {
          localPrintStream.close();
        }
        catch (Exception localException1) {}
      }
      if (localBufferedOutputStream != null) {
        try
        {
          localBufferedOutputStream.close();
        }
        catch (Exception localException2) {}
      }
    }
  }
  
  public OutputFormat getOutputFormat(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws FIException
  {
    Iterator localIterator = this._typeToAdapterMap.values().iterator();
    while (localIterator.hasNext())
    {
      FileAdapterInfo localFileAdapterInfo = (FileAdapterInfo)localIterator.next();
      if ((localFileAdapterInfo.fileAdapterInstance instanceof ICustomAdapter))
      {
        ICustomAdapter localICustomAdapter = (ICustomAdapter)localFileAdapterInfo.fileAdapterInstance;
        return localICustomAdapter.getOutputFormat(paramString, paramHashMap);
      }
    }
    return null;
  }
  
  public FieldDefinitions getFieldDefinitionList(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws FIException
  {
    MappingDefinition localMappingDefinition = this._fileImporterAdapter.getMappingDefinition(paramSecureUser.getBusinessID(), paramString, paramHashMap);
    return localMappingDefinition == null ? null : localMappingDefinition.getFieldDefinitions();
  }
  
  public void addMappingDefinition(SecureUser paramSecureUser, MappingDefinition paramMappingDefinition, HashMap paramHashMap)
    throws FIException
  {
    this._fileImporterAdapter.addMappingDefinition(paramSecureUser.getBusinessID(), paramMappingDefinition, paramHashMap);
  }
  
  public void removeMappingDefinition(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws FIException
  {
    this._fileImporterAdapter.removeMappingDefinition(paramSecureUser.getBusinessID(), paramInt, paramHashMap);
  }
  
  public void modifyMappingDefinition(SecureUser paramSecureUser, MappingDefinition paramMappingDefinition, HashMap paramHashMap)
    throws FIException
  {
    this._fileImporterAdapter.modifyMappingDefinition(paramSecureUser.getBusinessID(), paramMappingDefinition, paramHashMap);
  }
  
  public ArrayList getMappingNames(SecureUser paramSecureUser, HashMap paramHashMap)
    throws FIException
  {
    return this._fileImporterAdapter.getMappingNames(paramSecureUser.getBusinessID(), paramHashMap);
  }
  
  public ArrayList getMappingNamesByCategory(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws FIException
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = getOutputFormatNamesByCategory(paramString, paramHashMap);
    MappingDefinitions localMappingDefinitions = this._fileImporterAdapter.getMappingDefinitions(paramSecureUser.getBusinessID(), paramHashMap);
    Iterator localIterator = localMappingDefinitions.iterator();
    while (localIterator.hasNext())
    {
      MappingDefinition localMappingDefinition = (MappingDefinition)localIterator.next();
      String str = localMappingDefinition.getOutputFormatName();
      if (localArrayList2.contains(str)) {
        localArrayList1.add(str);
      }
    }
    return localArrayList1;
  }
  
  public MappingDefinition getMappingDefinition(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws FIException
  {
    return this._fileImporterAdapter.getMappingDefinition(paramSecureUser.getBusinessID(), paramString, paramHashMap);
  }
  
  public MappingDefinitions getMappingDefinitions(SecureUser paramSecureUser, HashMap paramHashMap)
    throws FIException
  {
    return this._fileImporterAdapter.getMappingDefinitions(paramSecureUser.getBusinessID(), paramHashMap);
  }
  
  public void removeBusiness(int paramInt, HashMap paramHashMap)
    throws FIException
  {
    this._fileImporterAdapter.removeBusiness(paramInt, paramHashMap);
  }
  
  public void processFiles(String paramString, HashMap paramHashMap)
    throws FIException
  {
    Object localObject;
    if (this._typeToAdapterMap.containsKey(paramString))
    {
      localObject = (FileAdapterInfo)this._typeToAdapterMap.get(paramString);
      paramHashMap.put("FileImporterAdapter", this._fileImporterAdapter);
      IFileAdapter localIFileAdapter = ((FileAdapterInfo)localObject).fileAdapterInstance;
      File localFile = new File(((FileAdapterInfo)localObject).processDir);
      if ((localFile.exists()) && (a(localFile))) {
        localIFileAdapter.open(paramHashMap);
      } else {
        return;
      }
      if (localFile.exists()) {
        a(localFile, ((FileAdapterInfo)localObject).processDir, "", localIFileAdapter, paramString, (FileAdapterInfo)localObject, paramHashMap);
      }
      localIFileAdapter.close(paramHashMap);
    }
    else
    {
      localObject = new FIException(0);
      DebugLog.throwing("FileImporterService.processFiles", (Throwable)localObject);
      throw ((Throwable)localObject);
    }
  }
  
  public void cleanup(String paramString1, String paramString2, int paramInt, HashMap paramHashMap)
    throws FIException
  {
    Object localObject1;
    if (this._typeToAdapterMap.containsKey(paramString1))
    {
      localObject1 = (FileAdapterInfo)this._typeToAdapterMap.get(paramString1);
      String str;
      if (paramString2.equalsIgnoreCase("process"))
      {
        str = ((FileAdapterInfo)localObject1).processDir;
      }
      else if (paramString2.equalsIgnoreCase("archive"))
      {
        str = ((FileAdapterInfo)localObject1).archiveDir;
      }
      else if (paramString2.equalsIgnoreCase("error"))
      {
        str = ((FileAdapterInfo)localObject1).errorDir;
      }
      else
      {
        localObject2 = new FIException(5);
        DebugLog.throwing("FileImporterService.cleanup", (Throwable)localObject2);
        throw ((Throwable)localObject2);
      }
      Object localObject2 = new File(str);
      if (((File)localObject2).exists()) {
        a((File)localObject2, paramInt);
      }
    }
    else
    {
      localObject1 = new FIException(0);
      DebugLog.throwing("FileImporterService.cleanup", (Throwable)localObject1);
      throw ((Throwable)localObject1);
    }
  }
  
  private void a(ILocalizable paramILocalizable, HashMap paramHashMap)
  {
    try
    {
      String str1 = TrackingIDGenerator.GetNextID();
      int i = 2500;
      SecureUser localSecureUser = (SecureUser)paramHashMap.get("SecureUser");
      String str2 = null;
      if (localSecureUser == null)
      {
        str2 = "";
      }
      else
      {
        if (localSecureUser.getUserType() == 2) {
          str2 = "";
        } else {
          str2 = String.valueOf(localSecureUser.getProfileID());
        }
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = localSecureUser.getUserName();
        arrayOfObject[1] = paramILocalizable;
        paramILocalizable = new LocalizableString("com.ffusion.util.logging.audit.fileimporterservice", "AuditMessage_2", arrayOfObject);
      }
      AuditLog.log(str2, paramILocalizable, str1, i);
    }
    catch (Exception localException)
    {
      DebugLog.log("FileImporterService.audit: Error while logging: " + localException.getMessage());
    }
  }
  
  private boolean a(File paramFile)
  {
    String[] arrayOfString = paramFile.list();
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < arrayOfString.length; i++)
    {
      File localFile1 = new File(paramFile, arrayOfString[i]);
      if (localFile1.isDirectory()) {
        localArrayList.add(localFile1);
      } else {
        return true;
      }
    }
    i = 0;
    boolean bool;
    for (int j = 0; (i == 0) && (j < localArrayList.size()); j++)
    {
      File localFile2 = (File)localArrayList.get(j);
      bool = a(localFile2);
    }
    return bool;
  }
  
  private void a(File paramFile, int paramInt)
  {
    String[] arrayOfString = paramFile.list();
    String str = paramFile.getAbsolutePath();
    for (int i = 0; i < arrayOfString.length; i++)
    {
      File localFile = new File(str + File.separator + arrayOfString[i]);
      if (localFile.isDirectory())
      {
        a(localFile, paramInt);
      }
      else
      {
        long l1 = localFile.lastModified();
        Date localDate = new Date(System.currentTimeMillis() - l1);
        long l2 = localDate.getTime() / 86400000L;
        int j = (int)l2;
        if ((j > paramInt) && (!localFile.delete()))
        {
          FIException localFIException = new FIException(6);
          DebugLog.throwing("FileImporterService.recursivelyClean", localFIException);
        }
      }
    }
  }
  
  private void a(File paramFile, String paramString1, String paramString2, IFileAdapter paramIFileAdapter, String paramString3, FileAdapterInfo paramFileAdapterInfo, HashMap paramHashMap)
    throws FIException
  {
    String[] arrayOfString = paramFile.list();
    String str1 = paramFile.getAbsolutePath();
    ArrayList localArrayList = new ArrayList();
    File localFile1 = new File(str1);
    File localFile2;
    for (int i = 0; i < arrayOfString.length; i++)
    {
      localFile2 = new File(localFile1, arrayOfString[i]);
      if (localFile2.isDirectory())
      {
        localArrayList.add(localFile2);
      }
      else
      {
        Object localObject1 = null;
        try
        {
          String str2 = paramFileAdapterInfo.processDir;
          String str3 = localFile2.getAbsolutePath();
          String str4 = localFile2.getAbsolutePath().substring(str2.length() + 1);
          paramHashMap.put("FILE_PATH", str4);
          String str5 = "";
          int j = str4.lastIndexOf(File.separator);
          if (j != -1) {
            str5 = str4.substring(0, j + 1);
          }
          String str6 = paramFileAdapterInfo.archiveDir + File.separator + str5;
          String str7 = paramFileAdapterInfo.errorDir + File.separator + str5;
          paramHashMap.put("BAI_FILE_IDENTIFIER", localFile2.getName());
          paramHashMap.put("FILE_NAME", localFile2.getName());
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = localFile2.getName();
          LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.fileimporterservice", "AuditMessage_1", arrayOfObject);
          a(localLocalizableString, paramHashMap);
          a(localFile2, str6, str7, paramIFileAdapter, paramHashMap);
        }
        catch (Exception localException)
        {
          DebugLog.throwing("FileImporterService.recursivelyProcess", localException);
        }
        finally
        {
          paramHashMap.remove("BAI_FILE_IDENTIFIER");
        }
      }
    }
    for (i = 0; i < localArrayList.size(); i++)
    {
      localFile2 = (File)localArrayList.get(i);
      paramString1 = localFile2.getAbsolutePath() + File.separator;
      paramString2 = paramString2 + a(File.separator, localFile2.getAbsolutePath(), paramString1);
      a(localFile2, paramString1, paramString2, paramIFileAdapter, paramString3, paramFileAdapterInfo, paramHashMap);
    }
  }
  
  private String a(long paramLong, String paramString)
  {
    Date localDate = new Date(paramLong);
    return DateFormatUtil.getFormatter(paramString).format(localDate);
  }
  
  private StringBuffer a(String paramString1, String paramString2, Exception paramException)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      localStringBuffer.append("Date and time when error occured: " + paramString1 + " " + paramString2);
      localStringBuffer.append("\n");
      localStringBuffer.append(paramException.toString());
      localStringBuffer.append("\n");
      StringWriter localStringWriter = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
      paramException.printStackTrace(localPrintWriter);
      localPrintWriter.close();
      localStringWriter.close();
      localStringBuffer.append(localStringWriter.toString());
    }
    catch (IOException localIOException) {}
    return localStringBuffer;
  }
  
  private String a(String paramString1, String paramString2, String paramString3)
  {
    String str1 = "";
    if (!paramString3.equals(paramString2))
    {
      int i = paramString2.lastIndexOf(paramString1);
      if (i != -1) {
        if (i == paramString2.length() - 1)
        {
          String str2 = paramString2.substring(0, i);
          int j = str2.lastIndexOf(paramString1);
          str1 = str2.substring(j + 1) + paramString1;
        }
        else
        {
          str1 = paramString2.substring(i + 1) + paramString1;
        }
      }
    }
    return str1;
  }
  
  private String a(File paramFile, String paramString, boolean paramBoolean)
  {
    long l = paramFile.lastModified();
    String str1 = a(l, "yyyyMMdd");
    String str2 = a(l, "HHmmss");
    int i = paramString.lastIndexOf(".");
    String str3 = "";
    String str4 = "";
    if (i != -1)
    {
      str3 = paramString.substring(0, i);
      str4 = paramString.substring(i, paramString.length());
    }
    else
    {
      str3 = paramString;
    }
    if (!paramBoolean) {
      return str3 + "-" + str1 + "-" + str2 + str4;
    }
    return str3 + "-" + str1 + "-" + str2 + ".err";
  }
  
  private HashMap jdField_if(File paramFile)
    throws Exception
  {
    this.b = new RandomAccessFile(paramFile, "r");
    this.c = this.b.length();
    this.b.seek(this.c);
    String str1 = this.b.readLine();
    while (str1 == null)
    {
      this.c -= 1L;
      this.b.seek(this.c);
      str1 = this.b.readLine();
      this.b.seek(this.c);
    }
    String str2 = null;
    HashMap localHashMap = new HashMap();
    while (((str2 = a()) != null) && (!str2.startsWith("FI"))) {
      if (str2.startsWith("CT"))
      {
        String str3 = str2.substring(3, 5);
        String str4 = str2.substring(5);
        if (str3 != null) {
          str3 = str3.trim();
        }
        if (str4 != null) {
          str4 = str4.trim();
        }
        localHashMap.put(str3, str4);
      }
    }
    this.b.close();
    return localHashMap;
  }
  
  private String a()
    throws IOException
  {
    String str = "";
    if (this.c < 0L) {
      return null;
    }
    while (this.c >= 0L)
    {
      this.b.seek(this.c);
      int i = this.b.readByte();
      char c1 = (char)i;
      if ((i == 13) || (i == 10))
      {
        this.b.seek(this.c - 1L);
        int j = this.b.readByte();
        if (((i == 10) && (j == 13)) || ((i == 13) && (j == 10))) {
          this.c -= 1L;
        }
        this.c -= 1L;
        break;
      }
      str = c1 + str;
      this.c -= 1L;
    }
    return str;
  }
  
  private static String jdField_if(String paramString)
  {
    StringBuffer localStringBuffer = paramString == null ? null : new StringBuffer(paramString.length());
    if (paramString != null) {
      for (int i = 0; i < paramString.length(); i++)
      {
        char c1 = paramString.charAt(i);
        if ("\\/:*?\"<>|'&;~`#()".indexOf(c1) != -1) {
          c1 = '.';
        }
        localStringBuffer.append(c1);
      }
    }
    return paramString == null ? null : localStringBuffer.toString();
  }
  
  private class a
  {
    public FileTypeDisplayName jdField_if;
    public XMLTag a;
    
    public a(FileTypeDisplayName paramFileTypeDisplayName, XMLTag paramXMLTag)
    {
      this.jdField_if = paramFileTypeDisplayName;
      this.a = paramXMLTag;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.fileimporter.FileImporterService
 * JD-Core Version:    0.7.0.1
 */