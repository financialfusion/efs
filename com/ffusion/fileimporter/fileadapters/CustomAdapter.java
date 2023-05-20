package com.ffusion.fileimporter.fileadapters;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.beans.fileimporter.FieldDefinition;
import com.ffusion.beans.fileimporter.FieldDefinitions;
import com.ffusion.beans.fileimporter.ImportError;
import com.ffusion.beans.fileimporter.MappingDefinition;
import com.ffusion.beans.fileimporter.OutputFormat;
import com.ffusion.beans.fileimporter.OutputFormatDisplayName;
import com.ffusion.beans.fileimporter.OutputFormatDisplayNames;
import com.ffusion.beans.fileimporter.OutputFormats;
import com.ffusion.beans.fileimporter.Record;
import com.ffusion.beans.fileimporter.Records;
import com.ffusion.csil.FIException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.efs.adapters.fileimporter.FileImporterAdapter;
import com.ffusion.fileimporter.fileadapters.custom.ICustomAdapter;
import com.ffusion.fileimporter.fileadapters.custom.ICustomMappingAdapter;
import com.ffusion.util.DelimiterTokenizer;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLTag;
import com.ffusion.util.logging.DebugLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class CustomAdapter
  implements IFileAdapter, ICustomAdapter
{
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.fileimporter.resources";
  private static final String aVw = "OUTPUTFORMAT";
  private static final String aVx = "NAME";
  private static final String aVu = "ENTITLEMENTNAME";
  private static final String aVl = "REQUIRED";
  private static final String aVs = "MEMO";
  private static final String aVp = "FIELDDEFINITIONLIST";
  private static final String aVv = "CMADAPTERCLASS";
  private static final String aVo = "CMADAPTERSETTINGS";
  private static final String aVn = "MATCHRECORDOPTIONS";
  private static final String aVr = "MATCHRECORDOPTIONSDISPLAYNAME";
  private static final String aVq = "CATEGORIES";
  private static final String aVm = "CATEGORY";
  private static final char[] aVt = { '\t', ';', ',', ' ', '|', '\t', ';', ',', ' ', '|', '\t', ';', ',', ' ', '|' };
  protected HashMap mappingAdapters = new HashMap();
  protected OutputFormats outputFormats = new OutputFormats();
  
  private static void jdMethod_for(HashMap paramHashMap, OutputFormats paramOutputFormats)
    throws Exception
  {
    Iterator localIterator1 = paramHashMap.keySet().iterator();
    int i = "AdapterSettingsTag".length();
    while (localIterator1.hasNext())
    {
      Object localObject = localIterator1.next();
      if ((localObject instanceof String))
      {
        String str1 = (String)localObject;
        if ((str1.startsWith("AdapterSettingsTag")) && (str1.length() > i))
        {
          String str2 = str1.substring(i);
          XMLTag localXMLTag1 = (XMLTag)paramHashMap.get(localObject);
          for (XMLTag localXMLTag2 = localXMLTag1.getContainedTag("OUTPUTFORMAT"); localXMLTag2 != null; localXMLTag2 = localXMLTag1.getContainedTag("OUTPUTFORMAT"))
          {
            HashMap localHashMap1 = localXMLTag2.getTagHashMap();
            String str3 = (String)localHashMap1.get("NAME");
            OutputFormat localOutputFormat = paramOutputFormats.findByName(str3);
            if (localOutputFormat != null)
            {
              String str4 = (String)localHashMap1.get("DISPLAYNAME");
              localOutputFormat.setName(str2, str4);
              XMLTag localXMLTag3 = localXMLTag2.getContainedTag("FIELDDEFINITIONLIST");
              ArrayList localArrayList1 = localXMLTag3.getContainedTagList();
              ArrayList localArrayList2 = new ArrayList();
              ArrayList localArrayList3 = new ArrayList();
              Iterator localIterator2 = localArrayList1.iterator();
              while (localIterator2.hasNext())
              {
                XMLTag localXMLTag4 = (XMLTag)localIterator2.next();
                if (localXMLTag4 != null)
                {
                  HashMap localHashMap2 = localXMLTag4.getTagHashMap();
                  String str5 = (String)localHashMap2.get("NAME");
                  if (str5 != null)
                  {
                    String str6 = (String)localHashMap2.get("MEMO");
                    String str7 = (String)localHashMap2.get("DISPLAYNAME");
                    if (str6 == null) {
                      str6 = "";
                    }
                    if (str7 == null) {
                      str7 = "";
                    }
                    localArrayList2.add(str7);
                    localArrayList3.add(str6);
                  }
                }
              }
              localOutputFormat.setFieldList(str2, localArrayList2);
              localOutputFormat.setMemoFieldList(str2, localArrayList3);
            }
            else
            {
              throw new FIException("An invalid output format name was specified.", 8);
            }
            localXMLTag1.removeContainedTag("OUTPUTFORMAT");
          }
        }
      }
    }
  }
  
  public void initialize(HashMap paramHashMap)
    throws FIException
  {
    XMLTag localXMLTag1 = (XMLTag)paramHashMap.get("AdapterSettingsTag");
    if (localXMLTag1 == null) {
      return;
    }
    ArrayList localArrayList1 = localXMLTag1.getContainedTagList();
    Iterator localIterator1 = localArrayList1.iterator();
    Object localObject1;
    while (localIterator1.hasNext())
    {
      localObject1 = (XMLTag)localIterator1.next();
      if (((XMLTag)localObject1).getTagName().equalsIgnoreCase("OUTPUTFORMAT"))
      {
        ArrayList localArrayList2 = ((XMLTag)localObject1).getContainedTagList();
        OutputFormat localOutputFormat = new OutputFormat();
        this.outputFormats.add(localOutputFormat);
        ArrayList localArrayList3 = new ArrayList();
        ArrayList localArrayList4 = new ArrayList();
        ArrayList localArrayList5 = new ArrayList();
        localOutputFormat.setFieldList(localArrayList3);
        localOutputFormat.setRequiredFieldList(localArrayList4);
        localOutputFormat.setMemoFieldList(localArrayList5);
        ArrayList localArrayList6 = new ArrayList();
        localOutputFormat.setMatchRecordOptions(localArrayList6);
        ArrayList localArrayList7 = new ArrayList();
        localOutputFormat.setMatchRecordOptionsDisplayNames(localArrayList7);
        ArrayList localArrayList8 = new ArrayList();
        localOutputFormat.setCategories(localArrayList8);
        Object localObject2 = localArrayList2.iterator();
        Object localObject3;
        Object localObject4;
        while (((Iterator)localObject2).hasNext())
        {
          localObject3 = (XMLTag)((Iterator)localObject2).next();
          localObject4 = ((XMLTag)localObject3).getTagName();
          String str1 = ((XMLTag)localObject3).getTagContent();
          if (((String)localObject4).equalsIgnoreCase("NAME"))
          {
            localOutputFormat.setName(str1);
          }
          else if (((String)localObject4).equalsIgnoreCase("ENTITLEMENTNAME"))
          {
            localOutputFormat.setEntitlementName(str1);
          }
          else
          {
            ArrayList localArrayList9;
            Iterator localIterator2;
            XMLTag localXMLTag2;
            if (((String)localObject4).equalsIgnoreCase("MATCHRECORDOPTIONS"))
            {
              localArrayList9 = ((XMLTag)localObject3).getContainedTagList();
              localIterator2 = localArrayList9.iterator();
              while (localIterator2.hasNext())
              {
                localXMLTag2 = (XMLTag)localIterator2.next();
                if (localXMLTag2 != null) {
                  localArrayList6.add(localXMLTag2.getTagContent());
                }
              }
            }
            else if (((String)localObject4).equalsIgnoreCase("MATCHRECORDOPTIONSDISPLAYNAME"))
            {
              localArrayList9 = ((XMLTag)localObject3).getContainedTagList();
              localIterator2 = localArrayList9.iterator();
              while (localIterator2.hasNext())
              {
                localXMLTag2 = (XMLTag)localIterator2.next();
                if (localXMLTag2 != null) {
                  localArrayList7.add(localXMLTag2.getTagContent());
                }
              }
            }
            else if (((String)localObject4).equalsIgnoreCase("CMADAPTERCLASS"))
            {
              localOutputFormat.setCmAdapterClass(str1);
            }
            else if (((String)localObject4).equalsIgnoreCase("CMADAPTERSETTINGS"))
            {
              localOutputFormat.setCmAdapterSettings(((XMLTag)localObject3).getTagHashMap());
            }
            else if (((String)localObject4).equalsIgnoreCase("FIELDDEFINITIONLIST"))
            {
              localArrayList9 = ((XMLTag)localObject3).getContainedTagList();
              localIterator2 = localArrayList9.iterator();
              while (localIterator2.hasNext())
              {
                localXMLTag2 = (XMLTag)localIterator2.next();
                if (localXMLTag2 != null)
                {
                  int i = 1;
                  String str2 = "";
                  XMLTag localXMLTag3 = localXMLTag2.getContainedTag("NAME");
                  XMLTag localXMLTag4 = localXMLTag2.getContainedTag("REQUIRED");
                  XMLTag localXMLTag5 = localXMLTag2.getContainedTag("MEMO");
                  if ((localXMLTag4 != null) && (localXMLTag4.getTagContent().equalsIgnoreCase("false"))) {
                    i = 0;
                  }
                  if (localXMLTag5 != null) {
                    str2 = localXMLTag5.getTagContent();
                  }
                  if (localXMLTag3 != null)
                  {
                    localArrayList3.add(localXMLTag3.getTagContent());
                    if (i != 0) {
                      localArrayList4.add("TRUE");
                    } else {
                      localArrayList4.add("FALSE");
                    }
                    localArrayList5.add(str2);
                  }
                }
              }
            }
            else if (((String)localObject4).equalsIgnoreCase("CATEGORIES"))
            {
              localArrayList9 = ((XMLTag)localObject3).getContainedTagList();
              localIterator2 = localArrayList9.iterator();
              while (localIterator2.hasNext())
              {
                localXMLTag2 = (XMLTag)localIterator2.next();
                if ((localXMLTag2 != null) && (localXMLTag2.getTagName().equalsIgnoreCase("CATEGORY"))) {
                  localArrayList8.add(localXMLTag2.getTagContent());
                }
              }
            }
          }
        }
        localObject2 = null;
        try
        {
          localObject3 = Class.forName(localOutputFormat.getCmAdapterClass());
          localObject2 = (ICustomMappingAdapter)((Class)localObject3).newInstance();
        }
        catch (Exception localException2)
        {
          localObject4 = new FIException("CustomMappingAdapter class init failed.", 3, localException2);
          DebugLog.throwing("CustomAdapter.initialize", (Throwable)localObject4);
          throw ((Throwable)localObject4);
        }
        ((ICustomMappingAdapter)localObject2).initialize(localOutputFormat.getCmAdapterSettings());
        this.mappingAdapters.put(localOutputFormat.getName(), localObject2);
      }
    }
    try
    {
      jdMethod_for(paramHashMap, this.outputFormats);
    }
    catch (Exception localException1)
    {
      localObject1 = new FIException("CustomMappingAdapter class init failed.", 8, localException1);
      DebugLog.throwing("CustomAdapter.initialize", (Throwable)localObject1);
      throw ((Throwable)localObject1);
    }
  }
  
  public void open(HashMap paramHashMap)
    throws FIException
  {}
  
  public void close(HashMap paramHashMap)
    throws FIException
  {}
  
  public static String getImportError(String paramString, Locale paramLocale)
  {
    return ResourceUtil.getString("ErrorMessage_" + paramString, "com.ffusion.beans.fileimporter.resources", paramLocale);
  }
  
  public static String getImportError(String paramString, Object[] paramArrayOfObject, Locale paramLocale)
  {
    if (paramArrayOfObject == null) {
      return ResourceUtil.getString("ErrorMessage_" + paramString, "com.ffusion.beans.fileimporter.resources", paramLocale);
    }
    return MessageFormat.format(ResourceUtil.getString("ErrorMessage_" + paramString, "com.ffusion.beans.fileimporter.resources", paramLocale), paramArrayOfObject);
  }
  
  public void processFile(InputStream paramInputStream, HashMap paramHashMap)
    throws FIException
  {
    String str = "CustomAdapter.processFile";
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
    FileImporterAdapter localFileImporterAdapter = (FileImporterAdapter)paramHashMap.get("FileImporterAdapter");
    MappingDefinition localMappingDefinition = (MappingDefinition)paramHashMap.get("MappingDefinition");
    OutputFormat localOutputFormat = this.outputFormats.findByName(localMappingDefinition.getOutputFormatName());
    paramHashMap.put("OUTPUTFORMAT", localOutputFormat);
    if (localFileImporterAdapter == null) {
      throw new FIException(str, 1);
    }
    if (!this.mappingAdapters.containsKey(localMappingDefinition.getOutputFormatName())) {
      throw new FIException(str, 9800);
    }
    if (localMappingDefinition == null) {
      throw new FIException(str, 9801);
    }
    ErrorMessages localErrorMessages = new ErrorMessages();
    paramHashMap.put("ImportErrors", localErrorMessages);
    SecureUser localSecureUser = (SecureUser)paramHashMap.get("USER");
    Locale localLocale = Locale.US;
    if ((localSecureUser != null) && (localSecureUser.getLocale() != null)) {
      localLocale = localSecureUser.getLocale();
    }
    Records localRecords = null;
    if (localMappingDefinition.getInputFormatType() == 0) {
      localRecords = jdMethod_int(localBufferedReader, localMappingDefinition, localErrorMessages, localLocale);
    } else if (localMappingDefinition.getInputFormatType() == 1) {
      localRecords = jdMethod_for(localBufferedReader, localMappingDefinition, localErrorMessages, localLocale);
    } else {
      throw new FIException(str, 9802);
    }
    if (localRecords == null) {
      throw new FIException(str, 9803);
    }
    ICustomMappingAdapter localICustomMappingAdapter = (ICustomMappingAdapter)this.mappingAdapters.get(localMappingDefinition.getOutputFormatName());
    localICustomMappingAdapter.open(paramHashMap);
    localICustomMappingAdapter.process(localErrorMessages, localRecords, paramHashMap);
  }
  
  private Records jdMethod_int(BufferedReader paramBufferedReader, MappingDefinition paramMappingDefinition, ErrorMessages paramErrorMessages, Locale paramLocale)
    throws FIException
  {
    Records localRecords = new Records();
    FieldDefinitions localFieldDefinitions = paramMappingDefinition.getFieldDefinitions();
    int i = 0;
    Iterator localIterator1 = localFieldDefinitions.iterator();
    int m;
    while (localIterator1.hasNext())
    {
      FieldDefinition localFieldDefinition1 = (FieldDefinition)localIterator1.next();
      m = localFieldDefinition1.getFieldNumber();
      if (m != 0) {
        i++;
      }
    }
    try
    {
      int j = jdMethod_for(paramBufferedReader, paramMappingDefinition);
      int k = 0;
      m = aVt[paramMappingDefinition.getFieldDelimiterType()];
      int n = 0;
      int i1 = 0;
      if ((paramMappingDefinition.getFieldDelimiterType() >= 5) && (paramMappingDefinition.getFieldDelimiterType() <= 9)) {
        n = 1;
      }
      if ((paramMappingDefinition.getFieldDelimiterType() >= 10) && (paramMappingDefinition.getFieldDelimiterType() <= 14)) {
        i1 = 1;
      }
      j++;
      String str1;
      while ((str1 = paramBufferedReader.readLine()) != null)
      {
        if (str1.trim().length() != 0)
        {
          k++;
          DelimiterTokenizer localDelimiterTokenizer = new DelimiterTokenizer(str1, m);
          int i2 = localDelimiterTokenizer.countTokens();
          String[] arrayOfString = new String[i2];
          for (int i3 = 0; i3 < arrayOfString.length; i3++) {
            arrayOfString[i3] = localDelimiterTokenizer.nextToken();
          }
          Record localRecord = new Record(str1, new Integer(j), new Integer(k));
          Iterator localIterator2 = localFieldDefinitions.iterator();
          while (localIterator2.hasNext())
          {
            FieldDefinition localFieldDefinition2 = (FieldDefinition)localIterator2.next();
            String str2 = null;
            int i4 = localFieldDefinition2.getFieldNumber();
            if ((i4 == 0) || (i4 > arrayOfString.length))
            {
              if (localFieldDefinition2.getDefaultValue() != null) {
                str2 = localFieldDefinition2.getDefaultValue().trim();
              }
            }
            else
            {
              str2 = arrayOfString[(i4 - 1)].trim();
              if ((str2.length() == 0) && (localFieldDefinition2.getDefaultValue() != null)) {
                str2 = localFieldDefinition2.getDefaultValue().trim();
              }
            }
            if (str2 != null)
            {
              if (((n != 0) && (str2.indexOf("\"") == 0) && (str2.lastIndexOf("\"") == str2.length() - 1)) || ((i1 != 0) && (str2.indexOf("'") == 0) && (str2.lastIndexOf("'") == str2.length() - 1))) {
                str2 = str2.substring(1, str2.length() - 1).trim();
              }
              localRecord.put(localFieldDefinition2.getName(), str2);
            }
          }
          localRecords.add(localRecord);
        }
        j++;
      }
    }
    catch (Exception localException)
    {
      throw new FIException(9608, localException);
    }
    return localRecords;
  }
  
  private Records jdMethod_for(BufferedReader paramBufferedReader, MappingDefinition paramMappingDefinition, ErrorMessages paramErrorMessages, Locale paramLocale)
    throws FIException
  {
    Records localRecords = new Records();
    int i = paramMappingDefinition.getRecordLength();
    try
    {
      int j = jdMethod_for(paramBufferedReader, paramMappingDefinition);
      int k = 0;
      char[] arrayOfChar = new char[i];
      for (;;)
      {
        k++;
        String str = jdMethod_for(paramMappingDefinition, paramBufferedReader, arrayOfChar, paramErrorMessages, paramLocale);
        if (str == null) {
          break;
        }
        if (paramMappingDefinition.getRecordDelimiterType() != 2) {
          j++;
        }
        Object localObject1;
        if ((str.length() != i) && (i > 0))
        {
          localObject1 = new Object[] { String.valueOf(str.length()), String.valueOf(i) };
          paramErrorMessages.add(new ImportError(1, getImportError("InvalidRecordLength", paramLocale), getImportError("FoundXExpectedY", (Object[])localObject1, paramLocale), str, new Integer(j), new Integer(k)));
        }
        else
        {
          localObject1 = new Record(str, new Integer(j), new Integer(k));
          Iterator localIterator = paramMappingDefinition.getFieldDefinitions().iterator();
          while (localIterator.hasNext())
          {
            FieldDefinition localFieldDefinition = (FieldDefinition)localIterator.next();
            int m = localFieldDefinition.getFieldStart();
            int n = localFieldDefinition.getFieldEnd();
            Object localObject2;
            if ((m == 0) || (n == 0))
            {
              if (localFieldDefinition.getDefaultValue() != null)
              {
                localObject2 = localFieldDefinition.getDefaultValue().trim();
                if (((String)localObject2).length() > 0) {
                  ((Record)localObject1).put(localFieldDefinition.getName(), localObject2);
                }
              }
            }
            else
            {
              m--;
              if (m < 0)
              {
                localObject2 = new Object[] { localFieldDefinition.getName(), String.valueOf(localFieldDefinition.getFieldStart()) };
                paramErrorMessages.add(new ImportError(1, getImportError("InvalidFieldDefinition", paramLocale), getImportError("FieldStartIsNeg", (Object[])localObject2, paramLocale), str, new Integer(j), new Integer(k)));
              }
              else if (m >= str.length())
              {
                localObject2 = new Object[] { String.valueOf(str.length()), localFieldDefinition.getName(), String.valueOf(localFieldDefinition.getFieldStart()) };
                paramErrorMessages.add(new ImportError(1, getImportError("InvalidRecordLength", paramLocale), getImportError("FieldStartGTLineLength", (Object[])localObject2, paramLocale), str, new Integer(j), new Integer(k)));
              }
              else if (n < m)
              {
                localObject2 = new Object[] { localFieldDefinition.getName(), String.valueOf(localFieldDefinition.getFieldEnd()), String.valueOf(localFieldDefinition.getFieldStart()) };
                paramErrorMessages.add(new ImportError(1, getImportError("InvalidFieldDefinition", paramLocale), getImportError("FieldEndLTStart", (Object[])localObject2, paramLocale), str, new Integer(j), new Integer(k)));
              }
              else if (n > str.length())
              {
                localObject2 = new Object[] { String.valueOf(str.length()), localFieldDefinition.getName(), String.valueOf(localFieldDefinition.getFieldEnd()) };
                paramErrorMessages.add(new ImportError(1, getImportError("InvalidRecordLength", paramLocale), getImportError("FieldEndGTLineLength", (Object[])localObject2, paramLocale), str, new Integer(j), new Integer(k)));
              }
              else
              {
                localObject2 = str.substring(m, n).trim();
                localObject2 = localObject2 == null ? "" : localObject2;
                if ((localFieldDefinition.getDefaultValue() != null) && (((String)localObject2).length() == 0)) {
                  localObject2 = localFieldDefinition.getDefaultValue().trim();
                }
                ((Record)localObject1).put(localFieldDefinition.getName(), localObject2);
              }
            }
          }
          localRecords.add(localObject1);
        }
      }
    }
    catch (Exception localException)
    {
      throw new FIException(9608, localException);
    }
    return localRecords;
  }
  
  private int jdMethod_for(BufferedReader paramBufferedReader, MappingDefinition paramMappingDefinition)
    throws IOException
  {
    if (paramMappingDefinition.getRecordDelimiterType() == 2)
    {
      paramBufferedReader.skip(paramMappingDefinition.getNumHeaderChars());
      return 0;
    }
    int i = paramMappingDefinition.getNumHeaderLines();
    for (int j = 0; j < i; j++) {
      paramBufferedReader.readLine();
    }
    return i;
  }
  
  private String jdMethod_for(MappingDefinition paramMappingDefinition, BufferedReader paramBufferedReader, char[] paramArrayOfChar, ErrorMessages paramErrorMessages, Locale paramLocale)
    throws IOException
  {
    String str = null;
    if (paramMappingDefinition.getRecordDelimiterType() == 2)
    {
      int i = jdMethod_for(paramBufferedReader, paramArrayOfChar, paramMappingDefinition.getRecordLength(), paramErrorMessages, paramLocale);
      if (i == -1) {
        str = null;
      } else {
        str = new String(paramArrayOfChar, 0, i);
      }
    }
    else
    {
      str = paramBufferedReader.readLine();
    }
    return str;
  }
  
  private int jdMethod_for(BufferedReader paramBufferedReader, char[] paramArrayOfChar, int paramInt, ErrorMessages paramErrorMessages, Locale paramLocale)
    throws IOException
  {
    int i = 0;
    int j = 0;
    int k = paramInt;
    while ((i != -1) && (j < paramInt))
    {
      i = paramBufferedReader.read(paramArrayOfChar, j, k);
      j += i;
      k -= i;
    }
    if ((i == -1) && (j > 0))
    {
      Object[] arrayOfObject = { String.valueOf(j), String.valueOf(paramInt) };
      paramErrorMessages.add(new ImportError(1, getImportError("IncompleteRecordAtEnd", paramLocale), getImportError("ReadXExpectedY", arrayOfObject, paramLocale), new String(paramArrayOfChar, 0, j), null, null));
      j = -1;
    }
    return j;
  }
  
  public ArrayList getOutputFormatNames(HashMap paramHashMap)
    throws FIException
  {
    ArrayList localArrayList = null;
    SecureUser localSecureUser = null;
    if (paramHashMap != null) {
      localSecureUser = (SecureUser)paramHashMap.get("SecureUser");
    }
    if (this.outputFormats != null)
    {
      localArrayList = new ArrayList();
      Iterator localIterator = this.outputFormats.iterator();
      while (localIterator.hasNext())
      {
        OutputFormat localOutputFormat = (OutputFormat)localIterator.next();
        if ((localOutputFormat.getEntitlementName() != null) && (localSecureUser != null))
        {
          EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
          try
          {
            if (!Entitlements.checkEntitlement(localEntitlementGroupMember, new Entitlement(localOutputFormat.getEntitlementName(), null, null))) {
              continue;
            }
          }
          catch (Exception localException) {}
        }
        else
        {
          localArrayList.add(localOutputFormat.getName());
        }
      }
    }
    return localArrayList;
  }
  
  public OutputFormatDisplayNames getOutputFormatDisplayNames(String paramString, HashMap paramHashMap)
    throws FIException
  {
    OutputFormatDisplayNames localOutputFormatDisplayNames = null;
    SecureUser localSecureUser = null;
    if (paramHashMap != null) {
      localSecureUser = (SecureUser)paramHashMap.get("SecureUser");
    }
    if (this.outputFormats != null)
    {
      localOutputFormatDisplayNames = new OutputFormatDisplayNames();
      Iterator localIterator = this.outputFormats.iterator();
      while (localIterator.hasNext())
      {
        OutputFormat localOutputFormat = (OutputFormat)localIterator.next();
        if ((localOutputFormat.getEntitlementName() != null) && (localSecureUser != null))
        {
          EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
          try
          {
            if (!Entitlements.checkEntitlement(localEntitlementGroupMember, new Entitlement(localOutputFormat.getEntitlementName(), null, null))) {
              continue;
            }
          }
          catch (Exception localException) {}
        }
        else
        {
          localOutputFormatDisplayNames.add(new OutputFormatDisplayName(paramString, localOutputFormat.getName(), localOutputFormat.getName(paramString)));
        }
      }
    }
    return localOutputFormatDisplayNames;
  }
  
  public ArrayList getOutputFormatNamesByCategory(String paramString, HashMap paramHashMap)
    throws FIException
  {
    ArrayList localArrayList = null;
    SecureUser localSecureUser = null;
    if (paramHashMap != null) {
      localSecureUser = (SecureUser)paramHashMap.get("SecureUser");
    }
    if (this.outputFormats != null)
    {
      localArrayList = new ArrayList();
      Iterator localIterator = this.outputFormats.iterator();
      while (localIterator.hasNext())
      {
        OutputFormat localOutputFormat = (OutputFormat)localIterator.next();
        if ((localOutputFormat.getEntitlementName() != null) && (localSecureUser != null))
        {
          EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
          try
          {
            if (!Entitlements.checkEntitlement(localEntitlementGroupMember, new Entitlement(localOutputFormat.getEntitlementName(), null, null))) {
              continue;
            }
          }
          catch (Exception localException) {}
        }
        else if ((localOutputFormat.getCategories() != null) && (localOutputFormat.getCategories().contains(paramString)))
        {
          localArrayList.add(localOutputFormat.getName());
        }
      }
    }
    return localArrayList;
  }
  
  public OutputFormatDisplayNames getOutputFormatDisplayNamesByCategory(String paramString1, String paramString2, HashMap paramHashMap)
    throws FIException
  {
    OutputFormatDisplayNames localOutputFormatDisplayNames = null;
    SecureUser localSecureUser = null;
    if (paramHashMap != null) {
      localSecureUser = (SecureUser)paramHashMap.get("SecureUser");
    }
    if (this.outputFormats != null)
    {
      localOutputFormatDisplayNames = new OutputFormatDisplayNames();
      Iterator localIterator = this.outputFormats.iterator();
      while (localIterator.hasNext())
      {
        OutputFormat localOutputFormat = (OutputFormat)localIterator.next();
        if ((localOutputFormat.getEntitlementName() != null) && (localSecureUser != null))
        {
          EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
          try
          {
            if (!Entitlements.checkEntitlement(localEntitlementGroupMember, new Entitlement(localOutputFormat.getEntitlementName(), null, null))) {
              continue;
            }
          }
          catch (Exception localException) {}
        }
        else if ((localOutputFormat.getCategories() != null) && (localOutputFormat.getCategories().contains(paramString2)))
        {
          localOutputFormatDisplayNames.add(new OutputFormatDisplayName(paramString1, localOutputFormat.getName(), localOutputFormat.getName(paramString1)));
        }
      }
    }
    return localOutputFormatDisplayNames;
  }
  
  public OutputFormat getOutputFormat(String paramString, HashMap paramHashMap)
    throws FIException
  {
    return this.outputFormats.findByName(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.CustomAdapter
 * JD-Core Version:    0.7.0.1
 */