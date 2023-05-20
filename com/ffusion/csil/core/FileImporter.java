package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fileimporter.FieldDefinitions;
import com.ffusion.beans.fileimporter.FileTypeDisplayName;
import com.ffusion.beans.fileimporter.FileTypeDisplayNames;
import com.ffusion.beans.fileimporter.FileTypeEntitlement;
import com.ffusion.beans.fileimporter.MappingDefinition;
import com.ffusion.beans.fileimporter.MappingDefinitions;
import com.ffusion.beans.fileimporter.OutputFormat;
import com.ffusion.beans.fileimporter.OutputFormatDisplayName;
import com.ffusion.beans.fileimporter.OutputFormatDisplayNames;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.handlers.FileImporterHandler;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Locale;

public class FileImporter
  extends Initialize
{
  public static final String HOST_NAME = "HOST_NAME";
  private static Entitlement aAF = new Entitlement("File Upload", null, null);
  private static Entitlement aAN = new Entitlement("Custom File Upload", null, null);
  private static Entitlement aAL = new Entitlement("Custom File Upload Admin", null, null);
  private static final String aAC = " with upload types ";
  private static final String aAt = "com.ffusion.util.logging.audit.fileimporter";
  private static final String aAp = "AuditMessage_1";
  private static final String aAM = "AuditMessage_2";
  private static final String aAK = "AuditMessage_3";
  private static final String aAu = "AuditEntFault_1";
  private static final String aAs = "AuditEntFault_2";
  private static final String aAG = "AuditEntFault_3";
  private static final String aAE = "AuditEntFault_4";
  private static final String aAr = "AuditEntFault_5";
  private static final String aAx = "AuditEntFault_6";
  private static final String aAz = "AuditEntFault_7";
  private static final String aAA = "AuditEntFault_8";
  private static final String aAD = "AuditEntFault_9";
  private static final String aAI = "AuditEntFault_10";
  private static final String aAy = "AuditEntFault_11";
  private static final String aAJ = "AuditEntFault_12";
  private static final String aAB = "AuditEntFault_13";
  private static final String aAq = "AuditEntFault_14";
  private static final String aAH = "AuditEntFault_15";
  private static final String aAw = "AuditEntFault_16";
  private static final String aAv = "AuditEntFault_17";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    FileImporterHandler.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return FileImporterHandler.getService();
  }
  
  private static boolean jdMethod_int(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    ArrayList localArrayList = FileImporterHandler.getAllFileTypes(paramSecureUser, paramHashMap);
    int i = localArrayList.size();
    FileTypeEntitlement localFileTypeEntitlement = null;
    String str = null;
    boolean bool = false;
    for (int j = 0; (!bool) && (j < i); j++)
    {
      localFileTypeEntitlement = (FileTypeEntitlement)localArrayList.get(j);
      if (paramString.equals(localFileTypeEntitlement.getFileType()))
      {
        str = localFileTypeEntitlement.getEntitlementName();
        if ((str == null) || (Entitlements.checkEntitlement(localEntitlementGroupMember, new Entitlement(str, null, null))))
        {
          bool = true;
        }
        else
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = paramString;
          LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.fileimporter", "AuditEntFault_1", arrayOfObject);
          logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
        }
      }
    }
    return bool;
  }
  
  private static boolean jdMethod_for(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    ArrayList localArrayList = FileImporterHandler.getUploadFileTypes(paramSecureUser, paramHashMap);
    int i = localArrayList.size();
    FileTypeEntitlement localFileTypeEntitlement = null;
    String str = null;
    boolean bool = false;
    for (int j = 0; (!bool) && (j < i); j++)
    {
      localFileTypeEntitlement = (FileTypeEntitlement)localArrayList.get(j);
      if (paramString.equals(localFileTypeEntitlement.getFileType()))
      {
        str = localFileTypeEntitlement.getEntitlementName();
        if ((str == null) || (Entitlements.checkEntitlement(localEntitlementGroupMember, new Entitlement(str, null, null))))
        {
          bool = true;
        }
        else
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = paramString;
          LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.fileimporter", "AuditEntFault_2", arrayOfObject);
          logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
        }
      }
    }
    return bool;
  }
  
  public static ArrayList getUploadFileTypes(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAF))
    {
      localObject1 = "FileImporter.getUploadFileTypes";
      long l = System.currentTimeMillis();
      ArrayList localArrayList1 = FileImporterHandler.getUploadFileTypes(paramSecureUser, paramHashMap);
      ArrayList localArrayList2 = new ArrayList();
      EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
      String str1 = null;
      String str2 = null;
      int i = localArrayList1.size();
      for (int j = 0; j < i; j++)
      {
        localObject2 = (FileTypeEntitlement)localArrayList1.get(j);
        str1 = ((FileTypeEntitlement)localObject2).getFileType();
        str2 = ((FileTypeEntitlement)localObject2).getEntitlementName();
        if ((str2 == null) || (Entitlements.checkEntitlement(localEntitlementGroupMember, new Entitlement(str2, null, null)))) {
          localArrayList2.add(str1);
        }
      }
      PerfLog.log((String)localObject1, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append((String)localObject1);
      localStringBuffer.append(" with upload types ");
      Object localObject2 = localArrayList1.listIterator();
      while (((ListIterator)localObject2).hasNext())
      {
        localStringBuffer.append(" , ");
        Object localObject3 = ((ListIterator)localObject2).next();
        if ((localObject3 instanceof String)) {
          localStringBuffer.append(localObject3);
        }
      }
      debug(paramSecureUser, localStringBuffer.toString());
      return localArrayList2;
    }
    Object localObject1 = new LocalizableString("com.ffusion.util.logging.audit.fileimporter", "AuditEntFault_3", null);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject1, TrackingIDGenerator.GetNextID());
    throw new CSILException("FileImporter getUploadFileTypes failed.", 20001);
  }
  
  public static FileTypeDisplayNames getUploadFileTypeDisplayNames(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAF))
    {
      localObject1 = "FileImporter.getUploadFileTypes";
      long l = System.currentTimeMillis();
      ArrayList localArrayList = FileImporterHandler.getUploadFileTypeDisplayNames(paramSecureUser, paramString, paramHashMap);
      FileTypeDisplayNames localFileTypeDisplayNames = new FileTypeDisplayNames();
      EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
      String str1 = null;
      String str2 = null;
      int i = localArrayList.size();
      Object localObject3;
      for (int j = 0; j < i; j++)
      {
        localObject2 = (FileTypeEntitlement)localArrayList.get(j);
        str1 = ((FileTypeEntitlement)localObject2).getFileType();
        str2 = ((FileTypeEntitlement)localObject2).getEntitlementName();
        if ((str2 == null) || (Entitlements.checkEntitlement(localEntitlementGroupMember, new Entitlement(str2, null, null))))
        {
          localObject3 = new FileTypeDisplayName(paramString, ((FileTypeEntitlement)localObject2).getFileType(), ((FileTypeEntitlement)localObject2).getDisplayName());
          localFileTypeDisplayNames.add(localObject3);
        }
      }
      PerfLog.log((String)localObject1, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append((String)localObject1);
      localStringBuffer.append(" with upload types ");
      Object localObject2 = localFileTypeDisplayNames.listIterator();
      while (((ListIterator)localObject2).hasNext())
      {
        localStringBuffer.append(" , ");
        localObject3 = ((ListIterator)localObject2).next();
        if ((localObject3 instanceof FileTypeDisplayName)) {
          localStringBuffer.append(((FileTypeDisplayName)localObject3).getName());
        }
      }
      debug(paramSecureUser, localStringBuffer.toString());
      return localFileTypeDisplayNames;
    }
    Object localObject1 = new LocalizableString("com.ffusion.util.logging.audit.fileimporter", "AuditEntFault_3", null);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject1, TrackingIDGenerator.GetNextID());
    throw new CSILException("FileImporter getUploadFileTypeDisplayNames failed.", 20001);
  }
  
  public static HashMap upload(SecureUser paramSecureUser, byte[] paramArrayOfByte, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "FileImporter.upload";
    paramHashMap.put("FROM_SYSTEM", jdMethod_new(paramSecureUser, paramString1));
    paramHashMap.put("FILE_NAME", paramString2);
    paramHashMap.put("ACTIVITY_TYPE", "Upload");
    String str2 = TrackingIDGenerator.GetNextID();
    paramHashMap.put("TRACKING_ID", str2);
    String str3 = null;
    Object[] arrayOfObject1 = new Object[1];
    if (paramArrayOfByte.length <= 10000)
    {
      str3 = "AuditEntFault_16";
      arrayOfObject1[0] = Integer.toString(paramArrayOfByte.length);
    }
    else
    {
      str3 = "AuditEntFault_17";
      arrayOfObject1[0] = Integer.toString(paramArrayOfByte.length / 1024);
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.fileimporter", str3, arrayOfObject1);
    paramHashMap.put("COMMENT", localLocalizableString);
    int i = 2500;
    if (paramString1.equals("Positive Pay Check Record")) {
      i = 2903;
    }
    if (paramString1.startsWith("ACH")) {
      i = 1363;
    }
    MappingDefinition localMappingDefinition = (MappingDefinition)paramHashMap.get("MappingDefinition");
    ArrayList localArrayList1 = new ArrayList();
    if ((localMappingDefinition != null) && (localMappingDefinition.getMappingID() != 0) && ("Custom".equalsIgnoreCase(paramString1)))
    {
      OutputFormat localOutputFormat = FileImporterHandler.getOutputFormat(paramSecureUser, localMappingDefinition.getOutputFormatName(), paramHashMap);
      localArrayList1 = localOutputFormat.getCategories();
    }
    int j = 0;
    if (("ACH Import".equalsIgnoreCase(paramString1)) || (localArrayList1.contains("ACH")))
    {
      j = 1;
      i = 1362;
    }
    if (("Transfer Import".equalsIgnoreCase(paramString1)) || (localArrayList1.contains("TRANSFER")))
    {
      j = 1;
      i = 1912;
    }
    if (("Payment Import".equalsIgnoreCase(paramString1)) || (localArrayList1.contains("PAYMENT")))
    {
      j = 1;
      i = 2018;
    }
    if (j != 0) {
      paramHashMap.put("ACTIVITY_TYPE", "ACH Import Entries");
    }
    if ((j != 0) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAF)))
    {
      long l = System.currentTimeMillis();
      if (!jdMethod_int(paramSecureUser, paramString1, paramHashMap))
      {
        CSILException localCSILException1 = new CSILException("FileImporterHandler upload failed.", 20108);
        DebugLog.throwing("FileImporterHandler.upload", localCSILException1);
        throw localCSILException1;
      }
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      try
      {
        FileImporterHandler.upload(paramSecureUser, paramArrayOfByte, paramString1, paramString2, paramHashMap);
      }
      catch (CSILException localCSILException2)
      {
        throw localCSILException2;
      }
      String str4 = null;
      ArrayList localArrayList2 = (ArrayList)paramHashMap.get("ImportErrors");
      if ((localArrayList2 == null) || (localArrayList2.isEmpty()))
      {
        if (paramString1.startsWith("ACH")) {
          str4 = "AuditMessage_1";
        } else {
          str4 = "AuditMessage_2";
        }
      }
      else {
        str4 = "AuditMessage_3";
      }
      Object[] arrayOfObject3 = new Object[2];
      arrayOfObject3[0] = paramString1;
      arrayOfObject3[1] = paramString2;
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.fileimporter", str4, arrayOfObject3);
      audit(paramSecureUser, localLocalizableString, str2, i);
      debug(paramSecureUser, (String)localLocalizableString.localize(Locale.US));
      PerfLog.log(str1, l, true);
    }
    else
    {
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = paramString1;
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.fileimporter", "AuditEntFault_4", arrayOfObject2);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException("FileImporter upload failed.", 20001);
    }
    return paramHashMap;
  }
  
  private static String jdMethod_new(SecureUser paramSecureUser, String paramString)
  {
    String str = paramSecureUser.getBusinessCustId();
    if ((str == null) || (str.trim().equals(""))) {
      str = "";
    } else {
      str = str + ":";
    }
    if (paramString.equals("ACH")) {
      return "User " + str + paramSecureUser.getUserName();
    }
    if (paramString.equals("BAI")) {
      return "CBS File Importer - BAI Adapter";
    }
    if (paramString.equals("Positive Pay Check Record")) {
      return "User " + str + paramSecureUser.getUserName();
    }
    if (paramString.equals("Positive Pay Issues")) {
      return "CBS File Importer - Positive Pay";
    }
    return "User " + str + paramSecureUser.getUserName();
  }
  
  public static ArrayList getOutputFormatNames(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAL))
    {
      localObject1 = "FileImporter.getOutputFormatNames";
      long l = System.currentTimeMillis();
      ArrayList localArrayList = FileImporterHandler.getOutputFormatNames(paramSecureUser, paramHashMap);
      if (localArrayList == null) {
        localArrayList = new ArrayList();
      }
      PerfLog.log((String)localObject1, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append((String)localObject1);
      localStringBuffer.append(" with upload types ");
      ListIterator localListIterator = localArrayList.listIterator();
      while (localListIterator.hasNext())
      {
        localStringBuffer.append(" , ");
        Object localObject2 = localListIterator.next();
        if ((localObject2 instanceof String)) {
          localStringBuffer.append(localObject2);
        }
      }
      debug(paramSecureUser, localStringBuffer.toString());
      return localArrayList;
    }
    Object localObject1 = new LocalizableString("com.ffusion.util.logging.audit.fileimporter", "AuditEntFault_5", null);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject1, TrackingIDGenerator.GetNextID());
    throw new CSILException("FileImporter getOutputFormatNames failed.", 20001);
  }
  
  public static OutputFormatDisplayNames getOutputFormatDisplayNames(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAL))
    {
      localObject1 = "FileImporter.getOutputFormatNames";
      long l = System.currentTimeMillis();
      OutputFormatDisplayNames localOutputFormatDisplayNames = FileImporterHandler.getOutputFormatDisplayNames(paramSecureUser, paramString, paramHashMap);
      if (localOutputFormatDisplayNames == null) {
        localOutputFormatDisplayNames = new OutputFormatDisplayNames();
      }
      PerfLog.log((String)localObject1, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append((String)localObject1);
      localStringBuffer.append(" with upload types ");
      ListIterator localListIterator = localOutputFormatDisplayNames.listIterator();
      while (localListIterator.hasNext())
      {
        localStringBuffer.append(" , ");
        Object localObject2 = localListIterator.next();
        if ((localObject2 instanceof OutputFormatDisplayName)) {
          localStringBuffer.append(((OutputFormatDisplayName)localObject2).getName());
        }
      }
      debug(paramSecureUser, localStringBuffer.toString());
      return localOutputFormatDisplayNames;
    }
    Object localObject1 = new LocalizableString("com.ffusion.util.logging.audit.fileimporter", "AuditEntFault_5", null);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject1, TrackingIDGenerator.GetNextID());
    throw new CSILException("FileImporter getOutputFormatDisplayNames failed.", 20001);
  }
  
  public static ArrayList getOutputFormatNamesByCategory(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAL))
    {
      localObject1 = "FileImporter.getOutputFormatNamesByCategory";
      long l = System.currentTimeMillis();
      ArrayList localArrayList = FileImporterHandler.getOutputFormatNamesByCategory(paramSecureUser, paramString, paramHashMap);
      PerfLog.log((String)localObject1, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append((String)localObject1);
      localStringBuffer.append(" with upload types ");
      ListIterator localListIterator = localArrayList.listIterator();
      while (localListIterator.hasNext())
      {
        localStringBuffer.append(" , ");
        Object localObject2 = localListIterator.next();
        if ((localObject2 instanceof String)) {
          localStringBuffer.append(localObject2);
        }
      }
      debug(paramSecureUser, localStringBuffer.toString());
      return localArrayList;
    }
    Object localObject1 = new LocalizableString("com.ffusion.util.logging.audit.fileimporter", "AuditEntFault_6", null);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject1, TrackingIDGenerator.GetNextID());
    throw new CSILException("FileImporter getOutputFormatNamesByCategory failed.", 20001);
  }
  
  public static OutputFormatDisplayNames getOutputFormatDisplayNamesByCategory(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAL))
    {
      localObject1 = "FileImporter.getOutputFormatDisplayNamesByCategory";
      long l = System.currentTimeMillis();
      OutputFormatDisplayNames localOutputFormatDisplayNames = FileImporterHandler.getOutputFormatDisplayNamesByCategory(paramSecureUser, paramString1, paramString2, paramHashMap);
      PerfLog.log((String)localObject1, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append((String)localObject1);
      localStringBuffer.append(" with upload types ");
      ListIterator localListIterator = localOutputFormatDisplayNames.listIterator();
      while (localListIterator.hasNext())
      {
        localStringBuffer.append(" , ");
        Object localObject2 = localListIterator.next();
        if ((localObject2 instanceof OutputFormatDisplayName)) {
          localStringBuffer.append(((OutputFormatDisplayName)localObject2).getName());
        }
      }
      debug(paramSecureUser, localStringBuffer.toString());
      return localOutputFormatDisplayNames;
    }
    Object localObject1 = new LocalizableString("com.ffusion.util.logging.audit.fileimporter", "AuditEntFault_6", null);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject1, TrackingIDGenerator.GetNextID());
    throw new CSILException("FileImporter getOutputFormatDisplayNamesByCategory failed.", 20001);
  }
  
  public static OutputFormat getOutputFormat(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAL)) {
      return FileImporterHandler.getOutputFormat(paramSecureUser, paramString, paramHashMap);
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.fileimporter", "AuditEntFault_7", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException("FileImporter getOutputFormat failed.", 20001);
  }
  
  public static FieldDefinitions getFieldDefinitionList(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAL)) {
      return FileImporterHandler.getFieldDefinitionList(paramSecureUser, paramString, paramHashMap);
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.fileimporter", "AuditEntFault_8", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException("FileImporter getFieldDefinitionList failed.", 20001);
  }
  
  public static void addMappingDefinition(SecureUser paramSecureUser, MappingDefinition paramMappingDefinition, HashMap paramHashMap)
    throws CSILException
  {
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAL))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      FileImporterHandler.addMappingDefinition(paramSecureUser, paramMappingDefinition, paramHashMap);
    }
    else
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.fileimporter", "AuditEntFault_9", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException("FileImporter addMappingDefinition failed.", 20001);
    }
  }
  
  public static void removeMappingDefinition(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAL))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      FileImporterHandler.removeMappingDefinition(paramSecureUser, paramInt, paramHashMap);
    }
    else
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.fileimporter", "AuditEntFault_10", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException("FileImporter removeMappingDefinition failed.", 20001);
    }
  }
  
  public static void modifyMappingDefinition(SecureUser paramSecureUser, MappingDefinition paramMappingDefinition, HashMap paramHashMap)
    throws CSILException
  {
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAL))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      FileImporterHandler.modifyMappingDefinition(paramSecureUser, paramMappingDefinition, paramHashMap);
    }
    else
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.fileimporter", "AuditEntFault_11", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException("FileImporter modifyMappingDefinition failed.", 20001);
    }
  }
  
  public static ArrayList getMappingNames(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAN)) {
      return FileImporterHandler.getMappingNames(paramSecureUser, paramHashMap);
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.fileimporter", "AuditEntFault_12", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException("FileImporter getMappingNames failed.", 20001);
  }
  
  public static ArrayList getMappingNamesByCategory(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAN)) {
      return FileImporterHandler.getMappingNamesByCategory(paramSecureUser, paramString, paramHashMap);
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.fileimporter", "AuditEntFault_13", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException("FileImporter getMappingNames failed.", 20001);
  }
  
  public static MappingDefinition getMappingDefinition(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAN)) {
      return FileImporterHandler.getMappingDefinition(paramSecureUser, paramString, paramHashMap);
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.fileimporter", "AuditEntFault_14", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException("FileImporter getMappingDefinition failed.", 20001);
  }
  
  public static MappingDefinitions getMappingDefinitions(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAN)) {
      return FileImporterHandler.getMappingDefinitions(paramSecureUser, paramHashMap);
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.fileimporter", "AuditEntFault_15", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException("FileImporter getMappingDefinitions failed.", 20001);
  }
  
  public static void removeBusiness(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    FileImporterHandler.removeBusiness(paramInt, paramHashMap);
  }
  
  public static void processFiles(String paramString, HashMap paramHashMap)
    throws CSILException
  {
    FileImporterHandler.processFiles(paramString, paramHashMap);
  }
  
  public static void processFiles(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    if (jdMethod_int(paramSecureUser, paramString, paramHashMap))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      paramHashMap.put("SecureUser", paramSecureUser);
      FileImporterHandler.processFiles(paramSecureUser, paramString, paramHashMap);
    }
    else
    {
      throw new CSILException("FileImporter processFiles failed.", 20001);
    }
  }
  
  public static void cleanup(String paramString1, String paramString2, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    FileImporterHandler.cleanup(paramString1, paramString2, paramInt, paramHashMap);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.FileImporter
 * JD-Core Version:    0.7.0.1
 */