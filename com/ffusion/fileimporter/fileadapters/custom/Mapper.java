package com.ffusion.fileimporter.fileadapters.custom;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.beans.fileimporter.ImportError;
import com.ffusion.beans.fileimporter.Record;
import com.ffusion.csil.FIException;
import com.ffusion.util.ResourceUtil;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;

public abstract class Mapper
  implements ICustomMappingAdapter
{
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.fileimporter.resources";
  protected static final String CREDIT = "credit";
  protected static final String DEBIT = "debit";
  protected static final int MONEY_FORMAT_AS_IS = 0;
  protected static final int MONEY_FORMAT_2_DEC = 1;
  protected static final int MONEY_FORMAT_4_DEC = 2;
  
  public void initialize(HashMap paramHashMap)
    throws FIException
  {}
  
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
  
  protected static DateTime getDateTimeAttribute(ErrorMessages paramErrorMessages, Record paramRecord, String paramString1, String paramString2, boolean paramBoolean, Locale paramLocale)
  {
    String str = getStringAttribute(paramErrorMessages, paramRecord, paramString1, paramBoolean, paramLocale);
    if (str == null) {
      return null;
    }
    DateTime localDateTime = null;
    try
    {
      localDateTime = new DateTime(str, Locale.getDefault(), paramString2);
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      Object[] arrayOfObject = { paramString1, str };
      paramErrorMessages.add(new ImportError(1, getImportError("InvalidData", paramLocale), getImportError("InvalidDateField", arrayOfObject, paramLocale), paramRecord.getLineContent(), paramRecord.getLineNumber(), paramRecord.getRecordNumber()));
    }
    return localDateTime;
  }
  
  protected static Currency getCurrencyAttribute(ErrorMessages paramErrorMessages, Record paramRecord, String paramString, int paramInt, boolean paramBoolean, Locale paramLocale)
  {
    String str = getStringAttribute(paramErrorMessages, paramRecord, paramString, paramBoolean, paramLocale);
    if (str == null) {
      return null;
    }
    double d = 0.0D;
    try
    {
      d = Double.parseDouble(str);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      Object[] arrayOfObject = { paramString, str };
      paramErrorMessages.add(new ImportError(1, getImportError("InvalidData", paramLocale), getImportError("InvalidAmountWillUse0", arrayOfObject, paramLocale), paramRecord.getLineContent(), paramRecord.getLineNumber(), paramRecord.getRecordNumber()));
    }
    if (paramInt == 1) {
      d /= 100.0D;
    } else if (paramInt == 2) {
      d /= 10000.0D;
    }
    return new Currency(new BigDecimal(Double.toString(d)), Locale.getDefault());
  }
  
  protected static Boolean getBooleanAttribute(ErrorMessages paramErrorMessages, Record paramRecord, String paramString, boolean paramBoolean, Locale paramLocale)
  {
    String str = getStringAttribute(paramErrorMessages, paramRecord, paramString, paramBoolean, paramLocale);
    if (str == null) {
      return null;
    }
    int i = str.charAt(0);
    boolean bool;
    switch (i)
    {
    case 49: 
    case 84: 
    case 89: 
    case 116: 
    case 121: 
      bool = true;
      break;
    default: 
      bool = false;
    }
    return new Boolean(bool);
  }
  
  protected static String getStringAttribute(ErrorMessages paramErrorMessages, Record paramRecord, String paramString, boolean paramBoolean, Locale paramLocale)
  {
    String str = (String)paramRecord.get(paramString);
    if (str != null) {
      str = str.trim();
    }
    if ((str == null) || (str.length() == 0))
    {
      if (paramBoolean)
      {
        Object[] arrayOfObject = { paramString };
        paramErrorMessages.add(new ImportError(1, getImportError("MissingData", paramLocale), getImportError("MandatoryFieldMissing", arrayOfObject, paramLocale), paramRecord.getLineContent(), paramRecord.getLineNumber(), paramRecord.getRecordNumber()));
      }
      return null;
    }
    return str;
  }
  
  protected static String getCreditDebitAttribute(ErrorMessages paramErrorMessages, Record paramRecord, String paramString, boolean paramBoolean, Locale paramLocale)
  {
    String str1 = null;
    String str2 = getStringAttribute(paramErrorMessages, paramRecord, paramString, paramBoolean, paramLocale);
    if (str2 == null) {
      return null;
    }
    str2 = str2.toLowerCase();
    if (str2.startsWith("c")) {
      str2 = "credit";
    } else if (str2.startsWith("d")) {
      str2 = "debit";
    }
    if (str2.equals("credit"))
    {
      str1 = "credit";
    }
    else if (str2.equals("debit"))
    {
      str1 = "debit";
    }
    else
    {
      Object[] arrayOfObject = { paramString, str2 };
      paramErrorMessages.add(new ImportError(1, getImportError("InvalidData", paramLocale), getImportError("FieldMustBeCRorDB", arrayOfObject, paramLocale), paramRecord.getLineContent(), paramRecord.getLineNumber(), paramRecord.getRecordNumber()));
    }
    return str1;
  }
  
  protected static Integer getIntegerAttribute(ErrorMessages paramErrorMessages, Record paramRecord, String paramString, boolean paramBoolean, Locale paramLocale)
    throws FIException
  {
    String str = getStringAttribute(paramErrorMessages, paramRecord, paramString, paramBoolean, paramLocale);
    if (str == null) {
      return null;
    }
    Integer localInteger = null;
    try
    {
      localInteger = new Integer(str);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      Object[] arrayOfObject = { paramString, str };
      paramErrorMessages.add(new ImportError(1, getImportError("InvalidData", paramLocale), getImportError("FieldMustBeInteger", arrayOfObject, paramLocale), paramRecord.getLineContent(), paramRecord.getLineNumber(), paramRecord.getRecordNumber()));
    }
    return localInteger;
  }
  
  protected Integer getIntegerACHAccountType(ErrorMessages paramErrorMessages, Record paramRecord, String paramString, boolean paramBoolean, Locale paramLocale)
    throws FIException
  {
    String str = getStringAttribute(paramErrorMessages, paramRecord, paramString, paramBoolean, paramLocale);
    if (str == null) {
      return null;
    }
    Integer localInteger = null;
    try
    {
      localInteger = new Integer(str);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      if ((str.equalsIgnoreCase("C")) || (str.equalsIgnoreCase("CHECKING")))
      {
        localInteger = new Integer(1);
      }
      else if ((str.equalsIgnoreCase("S")) || (str.equalsIgnoreCase("SAVINGS")))
      {
        localInteger = new Integer(2);
      }
      else if ((str.equalsIgnoreCase("L")) || (str.equalsIgnoreCase("LOAN")))
      {
        localInteger = new Integer(3);
      }
      else if ((str.equalsIgnoreCase("G")) || (str.equalsIgnoreCase("LEDGER")) || (str.equalsIgnoreCase("GENERAL LEDGER")))
      {
        localInteger = new Integer(4);
      }
      else
      {
        Object[] arrayOfObject = { paramString, str };
        paramErrorMessages.add(new ImportError(1, getImportError("InvalidData", paramLocale), getImportError("FieldMustBeNumber", arrayOfObject, paramLocale), paramRecord.getLineContent(), paramRecord.getLineNumber(), paramRecord.getRecordNumber()));
      }
    }
    return localInteger;
  }
  
  protected static void debugPrintRecord(Record paramRecord, int paramInt)
  {
    System.out.println("Record " + paramInt);
    System.out.println(paramRecord.toString());
  }
  
  protected static boolean nameRequired(int paramInt)
  {
    return (paramInt == 0) || (paramInt == 4) || (paramInt == 2);
  }
  
  protected static boolean accountRequired(int paramInt)
  {
    return (paramInt == 3) || (paramInt == 4);
  }
  
  protected static boolean idRequired(int paramInt)
  {
    return (paramInt == 1) || (paramInt == 4) || (paramInt == 2);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.custom.Mapper
 * JD-Core Version:    0.7.0.1
 */