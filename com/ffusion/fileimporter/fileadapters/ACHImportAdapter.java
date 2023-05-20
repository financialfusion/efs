package com.ffusion.fileimporter.fileadapters;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHClassCode;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.beans.fileimporter.ImportError;
import com.ffusion.csil.FIException;
import com.ffusion.fileimporter.fileadapters.nacha.AchARC;
import com.ffusion.fileimporter.fileadapters.nacha.AchCCD;
import com.ffusion.fileimporter.fileadapters.nacha.AchCIE;
import com.ffusion.fileimporter.fileadapters.nacha.AchCTX;
import com.ffusion.fileimporter.fileadapters.nacha.AchPOP;
import com.ffusion.fileimporter.fileadapters.nacha.AchPPD;
import com.ffusion.fileimporter.fileadapters.nacha.AchRCK;
import com.ffusion.fileimporter.fileadapters.nacha.AchTEL;
import com.ffusion.fileimporter.fileadapters.nacha.AchWEB;
import com.ffusion.fileimporter.fileadapters.nacha.AchXCK;
import com.ffusion.fileimporter.fileadapters.nacha.Mapper;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.RoutingNumberUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.logging.DebugLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class ACHImportAdapter
  implements IFileAdapter, ACHClassCode
{
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.ach.resources";
  protected boolean transactionsOnly = false;
  protected boolean validateForUploadOnly = false;
  protected String fileImportFailed = null;
  protected String batchImportFailed = null;
  protected String skippingLine = null;
  protected boolean importMultipleBatches = false;
  
  public void initialize(HashMap paramHashMap)
    throws FIException
  {
    String str1 = "ACHImportAdapter : initialize";
    String str2 = null;
    try
    {
      str2 = (String)paramHashMap.get("ALLOWMULTIBATCH");
      if (str2 != null) {
        this.importMultipleBatches = Boolean.valueOf(str2).booleanValue();
      }
    }
    catch (Exception localException)
    {
      FIException localFIException = new FIException(str1, 9610, "Performed a get on a null HashMap", localException);
      DebugLog.throwing("Performed a get on a null HashMap", localFIException);
      throw localFIException;
    }
  }
  
  public static String getImportError(String paramString, Locale paramLocale)
  {
    return ResourceUtil.getString("ACHImportError_" + paramString, "com.ffusion.beans.ach.resources", paramLocale);
  }
  
  public static String getImportError(String paramString, Object[] paramArrayOfObject, Locale paramLocale)
  {
    if (paramArrayOfObject == null) {
      return ResourceUtil.getString("ACHImportError_" + paramString, "com.ffusion.beans.ach.resources", paramLocale);
    }
    return MessageFormat.format(ResourceUtil.getString("ACHImportError_" + paramString, "com.ffusion.beans.ach.resources", paramLocale), paramArrayOfObject);
  }
  
  public void open(HashMap paramHashMap)
    throws FIException
  {}
  
  public void close(HashMap paramHashMap)
    throws FIException
  {}
  
  public void processFile(InputStream paramInputStream, HashMap paramHashMap)
    throws FIException
  {
    processFile(new BufferedReader(new InputStreamReader(paramInputStream)), paramHashMap);
  }
  
  public void processFile(StringBuffer paramStringBuffer, HashMap paramHashMap)
    throws FIException
  {
    processFile(new BufferedReader(new StringReader(paramStringBuffer.toString())), paramHashMap);
  }
  
  protected void addError(ErrorMessages paramErrorMessages, String paramString1, Object[] paramArrayOfObject, String paramString2, Locale paramLocale)
  {
    addError(paramErrorMessages, paramString1, paramArrayOfObject, paramString2, null, null, paramLocale);
  }
  
  protected void addError(ErrorMessages paramErrorMessages, String paramString1, Object[] paramArrayOfObject, String paramString2, int paramInt, Locale paramLocale)
  {
    addError(paramErrorMessages, paramString1, paramArrayOfObject, paramString2, new Integer(paramInt), null, paramLocale);
  }
  
  protected void addError(ErrorMessages paramErrorMessages, String paramString1, Object[] paramArrayOfObject, String paramString2, int paramInt1, int paramInt2, Locale paramLocale)
  {
    addError(paramErrorMessages, paramString1, paramArrayOfObject, paramString2, new Integer(paramInt1), new Integer(paramInt2), paramLocale);
  }
  
  protected void addError(ErrorMessages paramErrorMessages, String paramString1, Object[] paramArrayOfObject, String paramString2, Integer paramInteger1, Integer paramInteger2, Locale paramLocale)
  {
    String str1 = getImportError(paramString1, paramArrayOfObject, paramLocale);
    if (this.skippingLine.indexOf(paramString1) != -1) {
      str1 = str1 + "  " + getImportError("SkippingLineMsg", paramArrayOfObject, paramLocale);
    }
    int i = Integer.parseInt(paramString1.substring(1, 2));
    if ((this.validateForUploadOnly) && (i % 2 == 1)) {
      i++;
    }
    String str2 = getImportError("Category_" + paramString1.substring(0, 1), paramLocale);
    String str3 = getImportError("Priority_" + (i % 2 == 1 ? "WARN" : "FAIL"), paramLocale);
    if (!this.validateForUploadOnly)
    {
      if (this.fileImportFailed.indexOf(paramString1) != -1) {
        str1 = str1 + "  " + ResourceUtil.getString("ACHImportError_FileImportFailedMsg", "com.ffusion.beans.ach.resources", paramLocale);
      }
      if (this.batchImportFailed.indexOf(paramString1) != -1) {
        str1 = str1 + "  " + ResourceUtil.getString("ACHImportError_BatchImportFailedMsg", "com.ffusion.beans.ach.resources", paramLocale);
      }
    }
    paramErrorMessages.add(new ImportError(i, str2 + " " + str3, str1, paramString2, paramInteger1, paramInteger2));
  }
  
  public void processFile(BufferedReader paramBufferedReader, HashMap paramHashMap)
    throws FIException
  {
    try
    {
      boolean bool = true;
      StringBuffer localStringBuffer = new StringBuffer();
      int i = 0;
      long l = 0L;
      for (;;)
      {
        String str = paramBufferedReader.readLine();
        if (str == null) {
          break;
        }
        i++;
        localStringBuffer.append(str).append("\n");
        if (str.length() >= 1)
        {
          l += str.length();
          int j = str.charAt(0);
          switch (j)
          {
          case 49: 
          case 53: 
          case 54: 
          case 55: 
          case 56: 
          case 57: 
            break;
          case 50: 
          case 51: 
          case 52: 
          default: 
            bool = false;
          }
        }
      }
      if (l / i > 100L) {
        bool = false;
      }
      processFile(new BufferedReader(new StringReader(localStringBuffer.toString())), bool, paramHashMap);
    }
    catch (IOException localIOException)
    {
      throw new FIException(9608, localIOException);
    }
  }
  
  public void processFile(BufferedReader paramBufferedReader, boolean paramBoolean, HashMap paramHashMap)
    throws FIException
  {
    ErrorMessages localErrorMessages = new ErrorMessages();
    ACHEntries localACHEntries = new ACHEntries();
    SecureUser localSecureUser = (SecureUser)paramHashMap.get("USER");
    Locale localLocale = Locale.US;
    if ((localSecureUser != null) && (localSecureUser.getLocale() != null)) {
      localLocale = localSecureUser.getLocale();
    }
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    int i = -1;
    ACHEntry localACHEntry = null;
    int j = 0;
    int k = 0;
    int m = 0;
    long l1 = 0L;
    long l2 = 0L;
    long l3 = 0L;
    long l4 = 0L;
    long l5 = 0L;
    String str6 = "";
    long l6 = 0L;
    long l7 = 0L;
    long l8 = 0L;
    long l9 = 0L;
    long l10 = 0L;
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap = new HashMap();
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    int i5 = 0;
    boolean bool1 = true;
    String str7 = null;
    String str8 = null;
    int i6 = 0;
    int i7 = 0;
    int i8 = 0;
    ACHCompany localACHCompany1 = (ACHCompany)paramHashMap.get("ACHCOMPANY");
    ACHCompanies localACHCompanies = (ACHCompanies)paramHashMap.get("ACHCOMPANIES");
    ACHCompany localACHCompany2 = null;
    try
    {
      Object localObject1 = null;
      paramHashMap.put("ACHEntries", localACHEntries);
      if (paramHashMap.get("strictACHValidation") != null) {
        bool1 = new Boolean((String)paramHashMap.get("strictACHValidation")).booleanValue();
      }
      if (paramHashMap.get("transactionsOnly") != null) {
        this.transactionsOnly = new Boolean((String)paramHashMap.get("transactionsOnly")).booleanValue();
      } else {
        this.transactionsOnly = false;
      }
      int i9 = 1;
      int i10 = 0;
      String str9 = null;
      int i11 = 0;
      boolean bool2 = false;
      int i12 = 0;
      int i13 = 0;
      int i14 = 0;
      int i15 = 94;
      int i16 = 10;
      String str10 = null;
      String str11 = null;
      StringBuffer localStringBuffer = new StringBuffer();
      Object localObject3;
      while ((i9 != 0) && ((i5 == 0) || (this.validateForUploadOnly)))
      {
        str11 = str10;
        while (localStringBuffer.length() < i15)
        {
          str10 = paramBufferedReader.readLine();
          if (str10 != null)
          {
            localStringBuffer.append(str10);
            if (paramBoolean) {
              if ((str10.startsWith("1")) && (i1 == 0) && (str10.length() >= 40) && (str10.length() <= i15)) {
                l5 += i15 - str10.length();
              } else if ((str10.startsWith("9")) && (i2 == 0) && (str10.length() >= 55) && (str10.length() <= i15)) {
                l5 += i15 - str10.length();
              }
            }
          }
        }
        if ((str10 == null) && (localStringBuffer.length() == 0)) {
          break;
        }
        str10 = localStringBuffer.toString();
        localStringBuffer.setLength(0);
        int i17;
        Object localObject2;
        if ((str10.length() > i15) && (!paramBoolean))
        {
          i17 = i15;
          if ((i1 == 0) && (str10.startsWith("1")))
          {
            localObject2 = str10.substring(i15);
            if ((!((String)localObject2).startsWith("5200")) && (!((String)localObject2).startsWith("5220")) && (!((String)localObject2).startsWith("5225")) && (!((String)localObject2).startsWith("5280")))
            {
              localObject3 = str10.substring(40);
              if (((String)localObject3).indexOf("5200") >= 0) {
                i17 = ((String)localObject3).indexOf("5200") + 40;
              }
              if (((String)localObject3).indexOf("5220") >= 0) {
                i17 = ((String)localObject3).indexOf("5220") + 40;
              }
              if (((String)localObject3).indexOf("5225") >= 0) {
                i17 = ((String)localObject3).indexOf("5225") + 40;
              }
              if (((String)localObject3).indexOf("5280") >= 0) {
                i17 = ((String)localObject3).indexOf("5280") + 40;
              }
              if (i17 > i15) {
                i17 = i15;
              }
            }
          }
          localStringBuffer.append(str10.substring(i17));
          str10 = str10.substring(0, i17);
        }
        i10++;
        str9 = String.valueOf(i10);
        l5 += str10.length();
        if (str10.length() != 0)
        {
          i17 = str10.charAt(0);
          i11 = str10.length() == i15 ? 1 : 0;
          localObject2 = new Object[] { str9 };
          localObject3 = new Object[] { str9, String.valueOf(i17) };
          if ((i17 == 49) && (i1 != 0))
          {
            i9 = 0;
            localErrorMessages.setOperationCanContinue(false);
            addError(localErrorMessages, "0816", (Object[])localObject2, str10, i10, localLocale);
          }
          else
          {
            if ((bool2) && (i17 != 55)) {
              addError(localErrorMessages, "6120", (Object[])localObject2, str11, i10 - 1, j, localLocale);
            }
            if ((i12 != 0) && (i17 != 53) && (!this.transactionsOnly))
            {
              addError(localErrorMessages, "0703", (Object[])localObject3, str10, i10, j, localLocale);
            }
            else if ((i13 != 0) && (i17 != 54) && (!this.transactionsOnly))
            {
              addError(localErrorMessages, "0705", (Object[])localObject3, str10, i10, j, localLocale);
            }
            else if ((i14 != 0) && (m != 0) && (i17 != 54) && (i17 != 55) && (i17 != 56) && (!this.transactionsOnly))
            {
              addError(localErrorMessages, "0706", (Object[])localObject3, str10, i10, j, localLocale);
            }
            else if ((i14 != 0) && (m == 0) && (i17 != 54) && (i17 != 56) && (!this.transactionsOnly))
            {
              addError(localErrorMessages, "0708", (Object[])localObject3, str10, i10, j, localLocale);
            }
            else if ((i7 != i8) && ((i17 == 53) || (i17 == 57)))
            {
              addError(localErrorMessages, "0709", (Object[])localObject3, str10, i10, j, localLocale);
              i5 = i4 != 0 ? 0 : 1;
            }
            else if ((i17 == 56) && (i7 <= i8) && (!this.transactionsOnly))
            {
              addError(localErrorMessages, "0710", (Object[])localObject2, str10, i10, j, localLocale);
            }
            else if (localErrorMessages.size() > 100)
            {
              i9 = 0;
              localErrorMessages.setOperationCanContinue(false);
              addError(localErrorMessages, "0818", (Object[])localObject2, str10, i10, localLocale);
            }
            else
            {
              Object localObject4;
              Object localObject5;
              Object localObject6;
              Object localObject8;
              Object localObject10;
              Object localObject13;
              Object localObject17;
              Object localObject18;
              Object localObject19;
              Object localObject20;
              Object localObject24;
              Object localObject15;
              Object localObject16;
              Object localObject7;
              Object localObject9;
              switch (i17)
              {
              case 49: 
                i1 = 1;
                i12 = 1;
                if ((i10 != 1) && (!this.transactionsOnly))
                {
                  localObject4 = new Object[] { str9, str9 };
                  addError(localErrorMessages, "0701", (Object[])localObject4, str10, i10, localLocale);
                }
                i11 = (str10.length() >= 40) && (str10.length() <= i15) ? 1 : 0;
                if (i11 != 0)
                {
                  localObject4 = str10.substring(1, 3);
                  if ((!"01".equals(localObject4)) && (!this.transactionsOnly))
                  {
                    localObject5 = new Object[] { str9, localObject4 };
                    addError(localErrorMessages, "1500", (Object[])localObject5, str10, i10, localLocale);
                  }
                  localObject5 = str10.substring(3, 4);
                  if ((!" ".equals(localObject5)) && (!this.transactionsOnly))
                  {
                    localObject6 = new Object[] { str9, localObject5 };
                    addError(localErrorMessages, "1501", (Object[])localObject6, str10, i10, localLocale);
                  }
                  localObject6 = str10.substring(4, 13);
                  if ((!RoutingNumberUtil.isValidRoutingNumber((String)localObject6, true)) && (!this.transactionsOnly))
                  {
                    localObject8 = new Object[] { str9, localObject6 };
                    addError(localErrorMessages, "1502", (Object[])localObject8, str10, i10, localLocale);
                  }
                  localObject5 = str10.substring(13, 14);
                  if ((!" ".equals(localObject5)) && (!this.transactionsOnly) && (bool1))
                  {
                    localObject8 = new Object[] { str9, localObject5 };
                    addError(localErrorMessages, "1503", (Object[])localObject8, str10, i10, localLocale);
                  }
                  localObject8 = str10.substring(14, 23);
                  if ((!RoutingNumberUtil.isValidRoutingNumber((String)localObject8, true)) && (!this.transactionsOnly) && (bool1))
                  {
                    localObject10 = new Object[] { str9, localObject8 };
                    addError(localErrorMessages, "1504", (Object[])localObject10, str10, i10, localLocale);
                  }
                  localObject10 = str10.substring(23, 29);
                  localObject13 = ((String)localObject10).substring(0, 2);
                  String str12 = ((String)localObject10).substring(2, 4);
                  String str13 = ((String)localObject10).substring(4, 6);
                  if ((Strings.countNumbers((String)localObject10) == 6) && (!this.transactionsOnly))
                  {
                    localObject17 = new GregorianCalendar();
                    int i28 = ((Calendar)localObject17).get(1) - 1;
                    int i30 = Integer.parseInt((String)localObject13) + 2000;
                    if ((i30 < i28) || (i30 > i28 + 2))
                    {
                      Object[] arrayOfObject3 = { str9, localObject13 };
                      addError(localErrorMessages, "1505", arrayOfObject3, str10, i10, localLocale);
                    }
                    int i32 = Integer.parseInt(str12);
                    if ((i32 < 1) || (i32 > 12))
                    {
                      Object[] arrayOfObject4 = { str9, str12 };
                      addError(localErrorMessages, "1506", arrayOfObject4, str10, i10, localLocale);
                    }
                    int i33 = Integer.parseInt(str13);
                    if ((i33 < 1) || (i33 > 31))
                    {
                      localObject23 = new Object[] { str9, str13 };
                      addError(localErrorMessages, "1507", (Object[])localObject23, str10, i10, localLocale);
                    }
                  }
                  else if (!this.transactionsOnly)
                  {
                    localObject17 = new Object[] { str9, localObject10 };
                    addError(localErrorMessages, "1515", (Object[])localObject17, str10, i10, localLocale);
                  }
                  localObject17 = str10.substring(29, 33);
                  localObject18 = ((String)localObject17).substring(0, 2);
                  localObject19 = ((String)localObject17).substring(2, 4);
                  if ((Strings.countNumbers((String)localObject17) == 4) && (!this.transactionsOnly))
                  {
                    if (Integer.parseInt((String)localObject18) > 23)
                    {
                      localObject20 = new Object[] { str9, localObject18 };
                      addError(localErrorMessages, "1508", (Object[])localObject20, str10, i10, localLocale);
                    }
                    if (Integer.parseInt((String)localObject19) > 59)
                    {
                      localObject20 = new Object[] { str9, localObject19 };
                      addError(localErrorMessages, "1509", (Object[])localObject20, str10, i10, localLocale);
                    }
                  }
                  else if (!this.transactionsOnly)
                  {
                    localObject20 = new Object[] { str9, localObject17 };
                    addError(localErrorMessages, "1516", (Object[])localObject20, str10, i10, localLocale);
                  }
                  localObject20 = str10.substring(33, 34);
                  if ((!this.transactionsOnly) && ((!Character.isLetterOrDigit(((String)localObject20).charAt(0))) || (!((String)localObject20).equals(((String)localObject20).toUpperCase()))))
                  {
                    localObject22 = new Object[] { str9, localObject20 };
                    addError(localErrorMessages, "1510", (Object[])localObject22, str10, i10, localLocale);
                  }
                  Object localObject22 = str10.substring(34, 37);
                  if ((!"094".equals(localObject22)) && (!this.transactionsOnly))
                  {
                    localObject23 = new Object[] { str9, localObject22 };
                    addError(localErrorMessages, "1512", (Object[])localObject23, str10, i10, localLocale);
                  }
                  Object localObject23 = str10.substring(37, 39);
                  if ((!"10".equals(localObject23)) && (!this.transactionsOnly))
                  {
                    localObject24 = new Object[] { str9, localObject23 };
                    addError(localErrorMessages, "1513", (Object[])localObject24, str10, i10, localLocale);
                  }
                  if (Strings.countNumbers((String)localObject23) == 2) {
                    i16 = Integer.parseInt((String)localObject23);
                  }
                  localObject24 = str10.substring(39, 40);
                  if ((!"1".equals(localObject24)) && (!this.transactionsOnly))
                  {
                    Object[] arrayOfObject6 = { str9, localObject24 };
                    addError(localErrorMessages, "1514", arrayOfObject6, str10, i10, localLocale);
                  }
                }
                break;
              case 53: 
                l4 += 1L;
                i7++;
                n = 1;
                i3 = 0;
                i12 = 0;
                i6 = 0;
                i14 = 0;
                i13 = 1;
                j = 0;
                k = 0;
                localArrayList.add(new Integer(i10));
                if (l4 > 1L) {
                  i4 = 1;
                }
                if (str10.length() > 5) {
                  str2 = str10.substring(1, 4);
                }
                if ((!"200".equals(str2)) && (!"220".equals(str2)) && (!"225".equals(str2)))
                {
                  localObject4 = new Object[] { str9, str2 };
                  addError(localErrorMessages, "5400", (Object[])localObject4, str10, i10, localLocale);
                  i5 = (i4 != 0) && (i6 == 0) ? 0 : 1;
                }
                if (str10.length() > 51) {
                  str3 = str10.substring(40, 50).trim();
                }
                localACHCompany2 = null;
                if (!this.transactionsOnly)
                {
                  if ((localACHCompany1 != null) && (!localACHCompany1.getCompanyID().equals(str3)))
                  {
                    localObject4 = new Object[] { str9, str3 };
                    addError(localErrorMessages, "5409", (Object[])localObject4, str10, i10, localLocale);
                    i5 = (i4 != 0) && (i6 == 0) ? 0 : 1;
                  }
                  if (localACHCompanies != null) {
                    localACHCompany2 = localACHCompanies.getByCompanyID(str3);
                  }
                  if ((localACHCompany2 == null) || (!localACHCompany2.getActiveValue()))
                  {
                    localObject4 = new Object[] { str9, str3 };
                    addError(localErrorMessages, "5401", (Object[])localObject4, str10, i10, localLocale);
                    i5 = (i4 != 0) && (i6 == 0) ? 0 : 1;
                  }
                }
                if (str10.length() > 54) {
                  str1 = str10.substring(50, 53);
                }
                localHashMap.put("SEC_" + l4, str1);
                if ("ARC".equalsIgnoreCase(str1))
                {
                  localObject1 = new AchARC();
                  i = 1;
                }
                else if ("CCD".equalsIgnoreCase(str1))
                {
                  localObject1 = new AchCCD();
                  i = 12;
                }
                else if ("CIE".equalsIgnoreCase(str1))
                {
                  localObject1 = new AchCIE();
                  i = 2;
                }
                else if ("CTX".equalsIgnoreCase(str1))
                {
                  localObject1 = new AchCTX();
                  i = 13;
                }
                else if ("POP".equalsIgnoreCase(str1))
                {
                  localObject1 = new AchPOP();
                  i = 5;
                }
                else if ("PPD".equalsIgnoreCase(str1))
                {
                  localObject1 = new AchPPD();
                  i = 6;
                }
                else if ("RCK".equalsIgnoreCase(str1))
                {
                  localObject1 = new AchRCK();
                  i = 17;
                }
                else if ("TEL".equalsIgnoreCase(str1))
                {
                  localObject1 = new AchTEL();
                  i = 9;
                }
                else if ("WEB".equalsIgnoreCase(str1))
                {
                  localObject1 = new AchWEB();
                  i = 10;
                }
                else if ("XCK".equalsIgnoreCase(str1))
                {
                  localObject1 = new AchXCK();
                  i = 18;
                }
                else
                {
                  localObject4 = new Object[] { str9, str1 };
                  addError(localErrorMessages, "5402", (Object[])localObject4, str10, i10, localLocale);
                  localErrorMessages.setOperationCanContinue(false);
                  i5 = (i4 != 0) && (i6 == 0) ? 0 : 1;
                  break label7209;
                }
                if (l4 == 1L)
                {
                  paramHashMap.put("AchClassCode", new Integer(i));
                  paramHashMap.put("AchClassCodeText", str1);
                  paramHashMap.put("AchServiceClassCodeText", str2);
                  str8 = str1;
                  str7 = str3;
                }
                else if (this.importMultipleBatches)
                {
                  if ((str8 == null) || (!str8.equals(str1)))
                  {
                    localObject4 = new Object[] { str9, str1, str8 };
                    addError(localErrorMessages, "5110", (Object[])localObject4, str10, i10, localLocale);
                  }
                  else if ((str7 == null) || (!str7.equals(str3)))
                  {
                    localObject4 = new Object[] { str9, str3, str7 };
                    addError(localErrorMessages, "5111", (Object[])localObject4, str10, i10, localLocale);
                  }
                  else
                  {
                    i6 = 1;
                  }
                }
                else
                {
                  localObject4 = new Object[] { str9 };
                  addError(localErrorMessages, "5112", (Object[])localObject4, str10, i10, localLocale);
                }
                if ((localACHCompany2 != null) && (!localACHCompany2.getClassCodeEntitled(str1)))
                {
                  localObject4 = new Object[] { str9, str1 };
                  addError(localErrorMessages, "5403", (Object[])localObject4, str10, i10, localLocale);
                  i5 = i4 != 0 ? 0 : 1;
                }
                if ((!this.transactionsOnly) && (i11 != 0))
                {
                  localObject4 = str10.substring(69, 75);
                  localObject5 = ((String)localObject4).substring(0, 2);
                  localObject6 = ((String)localObject4).substring(2, 4);
                  localObject8 = ((String)localObject4).substring(4, 6);
                  if (Strings.countNumbers((String)localObject4) == 6)
                  {
                    localObject10 = new GregorianCalendar();
                    localObject13 = new GregorianCalendar();
                    int i24 = ((Calendar)localObject10).get(1) - 1;
                    int i25 = Integer.parseInt((String)localObject5) + 2000;
                    if ((i25 < i24) || (i25 > i24 + 2))
                    {
                      localObject17 = new Object[] { str9, localObject5 };
                      addError(localErrorMessages, "5304", (Object[])localObject17, str10, i10, localLocale);
                    }
                    int i26 = Integer.parseInt((String)localObject6);
                    if ((i26 < 1) || (i26 > 12))
                    {
                      localObject18 = new Object[] { str9, localObject6 };
                      addError(localErrorMessages, "5305", (Object[])localObject18, str10, i10, localLocale);
                    }
                    int i29 = Integer.parseInt((String)localObject8);
                    if ((i29 < 1) || (i29 > 31))
                    {
                      localObject19 = new Object[] { str9, localObject8 };
                      addError(localErrorMessages, "5306", (Object[])localObject19, str10, i10, localLocale);
                    }
                    ((Calendar)localObject13).set(i25, i26, i29);
                    ((Calendar)localObject10).add(5, 1);
                    if (((Calendar)localObject13).before(localObject10))
                    {
                      localObject19 = new DateTime((Calendar)localObject10, localLocale);
                      localObject20 = new Object[] { str9, localObject4, ((DateTime)localObject19).toString() };
                      addError(localErrorMessages, "5307", (Object[])localObject20, str10, i10, localLocale);
                    }
                    str4 = str10.substring(79, 87);
                    str5 = str10.substring(87, 94);
                  }
                  else
                  {
                    localObject10 = new Object[] { str9, localObject4 };
                    addError(localErrorMessages, "5308", (Object[])localObject10, str10, i10, localLocale);
                  }
                }
                break;
              case 54: 
                l6 += 1L;
                j++;
                i13 = 0;
                i14 = 1;
                bool2 = false;
                m = 0;
                if (i11 != 0)
                {
                  localObject4 = str10.substring(1, 3);
                  localObject5 = str10.substring(78, 79);
                  int i18 = 0;
                  int i19 = 0;
                  int i20 = 0;
                  int i22 = 0;
                  if (("24".equals(localObject4)) || ("34".equals(localObject4)) || ("44".equals(localObject4)) || ("54".equals(localObject4)) || ("29".equals(localObject4)) || ("39".equals(localObject4)) || ("49".equals(localObject4)))
                  {
                    i22 = 1;
                    if (("29".equals(localObject4)) || ("39".equals(localObject4)) || ("49".equals(localObject4))) {
                      i20 = 1;
                    } else {
                      i19 = 1;
                    }
                  }
                  else if (("23".equals(localObject4)) || ("33".equals(localObject4)) || ("43".equals(localObject4)) || ("53".equals(localObject4)) || ("28".equals(localObject4)) || ("38".equals(localObject4)) || ("48".equals(localObject4)))
                  {
                    i18 = 1;
                    if (("28".equals(localObject4)) || ("38".equals(localObject4)) || ("48".equals(localObject4))) {
                      i20 = 1;
                    } else {
                      i19 = 1;
                    }
                  }
                  else if (("22".equals(localObject4)) || ("32".equals(localObject4)) || ("42".equals(localObject4)) || ("52".equals(localObject4)))
                  {
                    i19 = 1;
                  }
                  else if (("27".equals(localObject4)) || ("37".equals(localObject4)) || ("47".equals(localObject4)))
                  {
                    i20 = 1;
                  }
                  else
                  {
                    localObject15 = new Object[] { str9, localObject4 };
                    addError(localErrorMessages, "6200", (Object[])localObject15, str10, i10, j, localLocale);
                  }
                  if (("220".equals(str2)) && (i20 != 0))
                  {
                    localObject15 = new Object[] { str9, localObject4 };
                    addError(localErrorMessages, "6201", (Object[])localObject15, str10, i10, j, localLocale);
                  }
                  if (("225".equals(str2)) && (i19 != 0))
                  {
                    localObject15 = new Object[] { str9, localObject4 };
                    addError(localErrorMessages, "6202", (Object[])localObject15, str10, i10, j, localLocale);
                  }
                  String str14;
                  if ((i == 18) || (i == 1) || (i == 17))
                  {
                    localObject15 = str10.substring(39, 54).trim();
                    if (((String)localObject15).length() == 0) {
                      addError(localErrorMessages, "6210", (Object[])localObject2, str10, i10, j, localLocale);
                    }
                    if (i == 18)
                    {
                      localObject16 = str10.substring(54, 60).trim();
                      if (((String)localObject16).length() == 0) {
                        addError(localErrorMessages, "6216", (Object[])localObject2, str10, i10, j, localLocale);
                      }
                      str14 = str10.substring(60, 76).trim();
                      if (str14.length() == 0) {
                        addError(localErrorMessages, "6217", (Object[])localObject2, str10, i10, j, localLocale);
                      }
                    }
                  }
                  else if (i == 2)
                  {
                    localObject15 = str10.substring(54, 76).trim();
                    if (((String)localObject15).length() == 0) {
                      addError(localErrorMessages, "6211", (Object[])localObject2, str10, i10, j, localLocale);
                    }
                  }
                  else if (i == 13)
                  {
                    localObject15 = str10.substring(54, 58).trim();
                    if ((((String)localObject15).length() == 0) || (Strings.countNumbers((String)localObject15) != 4)) {
                      addError(localErrorMessages, "6212", (Object[])localObject2, str10, i10, j, localLocale);
                    }
                  }
                  else if (i == 5)
                  {
                    localObject15 = str10.substring(39, 48).trim();
                    if (((String)localObject15).length() == 0) {
                      addError(localErrorMessages, "6213", (Object[])localObject2, str10, i10, j, localLocale);
                    }
                    localObject16 = str10.substring(48, 52).trim();
                    if (((String)localObject16).length() == 0) {
                      addError(localErrorMessages, "6214", (Object[])localObject2, str10, i10, j, localLocale);
                    }
                    str14 = str10.substring(52, 54).trim();
                    if (str14.length() == 0) {
                      addError(localErrorMessages, "6215", (Object[])localObject2, str10, i10, j, localLocale);
                    }
                  }
                  localObject15 = str10.substring(29, 39).trim();
                  localObject16 = localObject15;
                  int i27 = ((String)localObject16).length();
                  if (i27 < 3)
                  {
                    localObject16 = "000" + (String)localObject16;
                    i27 += 3;
                  }
                  localObject16 = ((String)localObject16).substring(0, i27 - 2) + "." + ((String)localObject16).substring(i27 - 2, i27);
                  Currency localCurrency = null;
                  int i31 = 0;
                  try
                  {
                    localCurrency = new Currency(new BigDecimal((String)localObject16), Locale.getDefault());
                  }
                  catch (Exception localException2)
                  {
                    i31 = 1;
                  }
                  if ((Strings.countNumbers((String)localObject15) != 10) || (i31 != 0) || (localCurrency == null))
                  {
                    localObject21 = new Object[] { str9, localObject15 };
                    addError(localErrorMessages, "6206", (Object[])localObject21, str10, i10, j, localLocale);
                  }
                  else if (localCurrency.getAmountValue().doubleValue() > 0.0D)
                  {
                    if (i18 != 0)
                    {
                      localObject21 = new Object[] { str9, localObject15, localObject4 };
                      addError(localErrorMessages, "6207", (Object[])localObject21, str10, i10, j, localLocale);
                    }
                    else if (i22 != 0)
                    {
                      localObject21 = new Object[] { str9, localObject15, localObject4 };
                      addError(localErrorMessages, "6221", (Object[])localObject21, str10, i10, j, localLocale);
                    }
                  }
                  else if (localCurrency.getAmountValue().doubleValue() == 0.0D)
                  {
                    if ((i18 == 0) && (i22 == 0))
                    {
                      localObject21 = new Object[] { str9, localObject4 };
                      addError(localErrorMessages, "6222", (Object[])localObject21, str10, i10, j, localLocale);
                    }
                    else if (i22 != 0)
                    {
                      if ((i != 12) && (i != 13))
                      {
                        localObject21 = new Object[] { str9, str1 };
                        addError(localErrorMessages, "6208", (Object[])localObject21, str10, i10, j, localLocale);
                      }
                      else if ((((String)localObject5).length() == 0) || (!"1".equals(localObject5)))
                      {
                        localObject21 = new Object[] { str9, str1 };
                        addError(localErrorMessages, "6209", (Object[])localObject21, str10, i10, j, localLocale);
                      }
                    }
                  }
                  if (i19 != 0)
                  {
                    l3 += Long.parseLong((String)localObject15);
                    l9 += Long.parseLong((String)localObject15);
                  }
                  else if (i20 != 0)
                  {
                    l2 += Long.parseLong((String)localObject15);
                    l8 += Long.parseLong((String)localObject15);
                  }
                  Object localObject21 = str10.substring(12, 29);
                  if (((String)localObject21).trim().length() == 0) {
                    addError(localErrorMessages, "6205", (Object[])localObject2, str10, i10, j, localLocale);
                  } else {
                    for (int i34 = 0; i34 < ((String)localObject21).length(); i34++)
                    {
                      char c = ((String)localObject21).charAt(i34);
                      if ((!Character.isLetterOrDigit(c)) && (c != '-') && (c != ' '))
                      {
                        localObject24 = new Object[] { str9, localObject21 };
                        addError(localErrorMessages, "6204", (Object[])localObject24, str10, i10, j, localLocale);
                      }
                    }
                  }
                  String str15 = str10.substring(3, 12);
                  if (!RoutingNumberUtil.isValidRoutingNumber(str15, true))
                  {
                    Object[] arrayOfObject5 = { str9, str15 };
                    addError(localErrorMessages, "6218", arrayOfObject5, str10, i10, j, localLocale);
                  }
                  long l12 = Long.parseLong(str15.substring(0, 8));
                  l1 += l12;
                  l10 += l12;
                  int i35 = String.valueOf(l1).length();
                  if (i35 > 10) {
                    l1 = Long.parseLong(String.valueOf(l1).substring(i35 - 10));
                  }
                  i35 = String.valueOf(l10).length();
                  if (i35 > 10) {
                    l10 = Long.parseLong(String.valueOf(l10).substring(i35 - 10));
                  }
                  bool2 = "1".equals(localObject5);
                  if ((!"0".equals(localObject5)) && (!"1".equals(localObject5)) && (!this.transactionsOnly)) {
                    addError(localErrorMessages, "6119", (Object[])localObject2, str10, i10, j, localLocale);
                  }
                  if (bool2)
                  {
                    m = 1;
                    if (i == 13) {
                      m = Integer.parseInt(str10.substring(54, 58));
                    }
                  }
                  if ((i != 12) && (i != 2) && (i != 13) && (i != 6) && (i != 10))
                  {
                    bool2 = false;
                    m = 0;
                  }
                  if (((l4 <= 1L) || (i6 != 0)) && ((i20 != 0) || (i19 != 0) || (i22 != 0) || (i18 != 0)))
                  {
                    ((Mapper)localObject1).reset();
                    localACHEntry = ((Mapper)localObject1).processEntry(str10);
                    localACHEntry.setRecordNumber(new Integer(j));
                    localACHEntry.setLineNumber(new Integer(i10));
                    localACHEntry.setLineContent(str10);
                    localACHEntry.setServiceClassCode(str2);
                    localACHEntries.add(localACHEntry);
                  }
                }
                break;
              case 55: 
                l7 += 1L;
                k++;
                i14 = 1;
                m--;
                bool2 = m > 0;
                if (i11 != 0)
                {
                  localObject4 = str10.substring(1, 3);
                  if ((!"05".equals(localObject4)) && (!this.transactionsOnly))
                  {
                    localObject5 = new Object[] { str9, localObject4 };
                    addError(localErrorMessages, "7100", (Object[])localObject5, str10, i10, localLocale);
                  }
                  else
                  {
                    localObject5 = str10.substring(3, 83);
                    if (("CCD".equalsIgnoreCase(str1)) && (localACHCompany2 != null) && (((String)localObject5).length() >= 3))
                    {
                      localObject7 = ((String)localObject5).substring(0, 3).toUpperCase();
                      if ("TXP".equals(localObject7))
                      {
                        if (!localACHCompany2.getTaxPaymentEntitledValue())
                        {
                          localObject9 = new Object[] { str9, localACHCompany2.getCompanyName() + "/" + localACHCompany2.getCompanyID() };
                          addError(localErrorMessages, "7201", (Object[])localObject9, str10, i10, localLocale);
                          i5 = (i4 != 0) && (i6 == 0) ? 0 : 1;
                        }
                      }
                      else if (("DED".equals(localObject7)) && (!localACHCompany2.getChildSupportPaymentEntitledValue()))
                      {
                        localObject9 = new Object[] { str9, localACHCompany2.getCompanyName() + "/" + localACHCompany2.getCompanyID() };
                        addError(localErrorMessages, "7202", (Object[])localObject9, str10, i10, localLocale);
                        i5 = (i4 != 0) && (i6 == 0) ? 0 : 1;
                      }
                    }
                    if ((l4 > 1L) && (i6 == 0)) {
                      break label7209;
                    }
                    ((Mapper)localObject1).processAddenda(str10, localACHEntry);
                  }
                }
                break;
              case 56: 
                i14 = 0;
                i3 = 1;
                i8++;
                if (i11 != 0)
                {
                  if (!this.transactionsOnly)
                  {
                    localObject4 = str10.substring(1, 4);
                    if ((str2 != null) && (!str2.equals(localObject4)))
                    {
                      localObject5 = new Object[] { str9, localObject4, str2 };
                      addError(localErrorMessages, "8300", (Object[])localObject5, str10, i10, localLocale);
                    }
                    localObject5 = str10.substring(4, 10);
                    if (Integer.valueOf((String)localObject5).intValue() != j + k)
                    {
                      localObject7 = new Object[] { str9, String.valueOf(j + k), localObject5 };
                      addError(localErrorMessages, "8301", (Object[])localObject7, str10, i10, localLocale);
                    }
                    localObject7 = str10.substring(10, 20);
                    if (Long.parseLong((String)localObject7) != l1)
                    {
                      localObject9 = new Object[] { str9, String.valueOf(l1), localObject7 };
                      addError(localErrorMessages, "8302", (Object[])localObject9, str10, i10, localLocale);
                    }
                    localObject9 = str10.substring(20, 32);
                    localHashMap.put("Debit_" + l4, localObject9);
                    if (Long.parseLong((String)localObject9) != l2)
                    {
                      localObject11 = new Object[] { str9, String.valueOf(l2), localObject9 };
                      addError(localErrorMessages, "8303", (Object[])localObject11, str10, i10, localLocale);
                    }
                    Object localObject11 = str10.substring(32, 44);
                    localHashMap.put("Credit_" + l4, localObject11);
                    if (Long.parseLong((String)localObject11) != l3)
                    {
                      localObject14 = new Object[] { str9, String.valueOf(l3), localObject11 };
                      addError(localErrorMessages, "8304", (Object[])localObject14, str10, i10, localLocale);
                    }
                    Object localObject14 = str10.substring(44, 54).trim();
                    if (!str3.equals(localObject14))
                    {
                      localObject15 = new Object[] { str9, localObject14, str3 };
                      addError(localErrorMessages, "8305", (Object[])localObject15, str10, i10, localLocale);
                    }
                    localObject15 = str10.substring(79, 87);
                    if (!str4.equals(localObject15))
                    {
                      localObject16 = new Object[] { str9, localObject15, str4 };
                      addError(localErrorMessages, "8306", (Object[])localObject16, str10, i10, localLocale);
                    }
                    localObject16 = str10.substring(87, 94);
                    if (!str5.equals(localObject16))
                    {
                      Object[] arrayOfObject2 = { str9, localObject16, str5 };
                      addError(localErrorMessages, "8307", arrayOfObject2, str10, i10, localLocale);
                    }
                  }
                  l2 = 0L;
                  l3 = 0L;
                  l1 = 0L;
                }
                break;
              case 57: 
                i11 = (str10.length() >= 55) && (str10.length() <= i15) ? 1 : 0;
                if (i11 != 0)
                {
                  if (i2 != 0) {
                    continue;
                  }
                  i2 = 1;
                  localObject4 = str10.substring(1, 7);
                  if ((Long.parseLong((String)localObject4) != l4) && (!this.transactionsOnly))
                  {
                    localObject5 = new Object[] { str9, String.valueOf(l4), localObject4 };
                    addError(localErrorMessages, "9500", (Object[])localObject5, str10, i10, localLocale);
                  }
                  Object[] arrayOfObject1;
                  if ((l4 > 1L) && (!this.importMultipleBatches) && (!this.validateForUploadOnly))
                  {
                    localObject5 = new StringBuffer();
                    localObject7 = (Integer)localArrayList.remove(0);
                    localObject9 = localArrayList.iterator();
                    int i21 = localArrayList.size() - 1;
                    for (int i23 = 0; i23 <= i21; i23++)
                    {
                      ((StringBuffer)localObject5).append(String.valueOf(((Iterator)localObject9).next()));
                      if (i23 < i21) {
                        ((StringBuffer)localObject5).append(",");
                      }
                    }
                    arrayOfObject1 = new Object[] { str9, ((Integer)localObject7).toString(), ((StringBuffer)localObject5).toString() };
                    addError(localErrorMessages, "9501", arrayOfObject1, str10, i10, localLocale);
                  }
                  str6 = str10.substring(7, 13);
                  localObject5 = str10.substring(13, 21);
                  if ((Long.parseLong((String)localObject5) != l6 + l7) && (!this.transactionsOnly))
                  {
                    localObject7 = new Object[] { str9, String.valueOf(l6 + l7), localObject5 };
                    addError(localErrorMessages, "9503", (Object[])localObject7, str10, i10, localLocale);
                  }
                  localObject7 = str10.substring(21, 31);
                  if ((Long.parseLong((String)localObject7) != l10) && (!this.transactionsOnly))
                  {
                    localObject9 = new Object[] { str9, String.valueOf(l10), localObject7 };
                    addError(localErrorMessages, "9504", (Object[])localObject9, str10, i10, localLocale);
                  }
                  localObject9 = str10.substring(31, 43);
                  if ((Long.parseLong((String)localObject9) != l8) && (!this.transactionsOnly))
                  {
                    localObject12 = new Object[] { str9, String.valueOf(l8), localObject9 };
                    addError(localErrorMessages, "9505", (Object[])localObject12, str10, i10, localLocale);
                  }
                  Object localObject12 = str10.substring(43, 55);
                  if ((Long.parseLong((String)localObject12) != l9) && (!this.transactionsOnly))
                  {
                    arrayOfObject1 = new Object[] { str9, String.valueOf(l9), localObject12 };
                    addError(localErrorMessages, "9506", arrayOfObject1, str10, i10, localLocale);
                  }
                }
                break;
              case 50: 
              case 51: 
              case 52: 
              default: 
                addError(localErrorMessages, "0817", (Object[])localObject3, str10, i10, localLocale);
                i9 = 0;
                localErrorMessages.setOperationCanContinue(false);
                label7209:
                if (i11 == 0) {
                  if (str10.length() > i15)
                  {
                    localObject4 = new Object[] { str9, str9 };
                    addError(localErrorMessages, "0813", (Object[])localObject4, str10, i10, localLocale);
                    i5 = 1;
                  }
                  else
                  {
                    localObject4 = new Object[] { str9, str9 };
                    addError(localErrorMessages, "0714", (Object[])localObject4, str10, i10, localLocale);
                  }
                }
                break;
              }
            }
          }
        }
      }
      if ((i2 != 0) && (i5 == 0))
      {
        long l11 = l5 / (i15 * i16);
        if (l5 % (i15 * i16) > 0L) {
          l11 += 1L;
        }
        if ((Long.parseLong(str6) != l11) && (!this.transactionsOnly))
        {
          localObject3 = new Object[] { str9, String.valueOf(l11), str6 };
          addError(localErrorMessages, "9502", (Object[])localObject3, str10, i10, localLocale);
        }
      }
    }
    catch (IOException localIOException)
    {
      throw new FIException(9608, localIOException);
    }
    catch (Exception localException1)
    {
      i5 = 1;
    }
    if ((i1 == 0) && (i5 == 0)) {
      addError(localErrorMessages, "0800", null, null, localLocale);
    }
    if ((i2 == 0) && (i5 == 0)) {
      addError(localErrorMessages, "0811", null, null, localLocale);
    }
    if ((n == 0) && (i5 == 0)) {
      addError(localErrorMessages, "0802", null, null, localLocale);
    }
    if ((i3 == 0) && (i5 == 0) && (!this.transactionsOnly)) {
      addError(localErrorMessages, "0707", null, null, localLocale);
    }
    if ((l6 == 0L) && (i5 == 0)) {
      addError(localErrorMessages, "0804", null, null, localLocale);
    }
    if ((str1 == null) || (i5 != 0) || (localACHEntries.size() == 0)) {
      localErrorMessages.setOperationCanContinue(false);
    }
    paramHashMap.put("ACHEntries", localACHEntries);
    paramHashMap.put("ImportErrors", localErrorMessages);
    paramHashMap.put("SECLimitList", localHashMap);
  }
  
  public void setValidateForUploadOnly(String paramString)
  {
    this.validateForUploadOnly = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.ACHImportAdapter
 * JD-Core Version:    0.7.0.1
 */