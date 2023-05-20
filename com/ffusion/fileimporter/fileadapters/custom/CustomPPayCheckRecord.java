package com.ffusion.fileimporter.fileadapters.custom;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.beans.fileimporter.MappingDefinition;
import com.ffusion.beans.fileimporter.OutputFormat;
import com.ffusion.beans.fileimporter.Record;
import com.ffusion.beans.fileimporter.Records;
import com.ffusion.beans.positivepay.PPayCheckRecord;
import com.ffusion.beans.positivepay.PPayCheckRecords;
import com.ffusion.csil.FIException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class CustomPPayCheckRecord
  extends Mapper
{
  public void process(ErrorMessages paramErrorMessages, Records paramRecords, HashMap paramHashMap)
    throws FIException
  {
    if (paramHashMap == null) {
      throw new FIException(getClass().getName(), 2, "Extra hash map is null");
    }
    SecureUser localSecureUser = (SecureUser)paramHashMap.get("USER");
    Locale localLocale = Locale.US;
    if ((localSecureUser != null) && (localSecureUser.getLocale() != null)) {
      localLocale = localSecureUser.getLocale();
    }
    MappingDefinition localMappingDefinition = (MappingDefinition)paramHashMap.get("MappingDefinition");
    if (localMappingDefinition == null) {
      throw new FIException(getClass().getName(), 2, "Mapping definition not found");
    }
    String str1 = localMappingDefinition.getOutputFormatName();
    if (str1 == null) {
      throw new FIException(getClass().getName(), 2, "Output format not found in mapping definition");
    }
    OutputFormat localOutputFormat = (OutputFormat)paramHashMap.get("OUTPUTFORMAT");
    ArrayList localArrayList1 = localOutputFormat.getRequiredFieldList();
    ArrayList localArrayList2 = localOutputFormat.getFieldList();
    int i = 0;
    Iterator localIterator1 = localArrayList2.iterator();
    HashMap localHashMap = new HashMap();
    while (localIterator1.hasNext())
    {
      localObject = (String)localIterator1.next();
      localHashMap.put(localObject, (String)localArrayList1.get(i++));
    }
    Object localObject = new PPayCheckRecords(localLocale);
    Iterator localIterator2 = paramRecords.iterator();
    while (localIterator2.hasNext())
    {
      Record localRecord = (Record)localIterator2.next();
      PPayCheckRecord localPPayCheckRecord = new PPayCheckRecord();
      localPPayCheckRecord.setAccountID(getStringAttribute(paramErrorMessages, localRecord, "Account Number", Boolean.valueOf((String)localHashMap.get("Account Number")).booleanValue(), localLocale));
      localPPayCheckRecord.setRoutingNumber(getStringAttribute(paramErrorMessages, localRecord, "Routing Number", Boolean.valueOf((String)localHashMap.get("Routing Number")).booleanValue(), localLocale));
      localPPayCheckRecord.setCheckNumber(getStringAttribute(paramErrorMessages, localRecord, "Check Number", Boolean.valueOf((String)localHashMap.get("Check Number")).booleanValue(), localLocale));
      localPPayCheckRecord.setCheckDate(getDateTimeAttribute(paramErrorMessages, localRecord, "Check Date", localMappingDefinition.getDateFormat(), Boolean.valueOf((String)localHashMap.get("Check Date")).booleanValue(), localLocale));
      localPPayCheckRecord.setAmount(getCurrencyAttribute(paramErrorMessages, localRecord, "Amount", localMappingDefinition.getMoneyFormatType(), Boolean.valueOf((String)localHashMap.get("Amount")).booleanValue(), localLocale));
      Boolean localBoolean = getBooleanVoidAttributeValue(paramErrorMessages, localRecord, "Void Check", Boolean.valueOf((String)localHashMap.get("Void Check")).booleanValue(), localLocale);
      if (localBoolean != null) {
        localPPayCheckRecord.setVoidCheck(localBoolean.booleanValue());
      } else {
        localPPayCheckRecord.setVoidCheck(false);
      }
      String str2 = getStringAttribute(paramErrorMessages, localRecord, "Additional Data", Boolean.valueOf((String)localHashMap.get("Additional Data")).booleanValue(), localLocale);
      if (str2 != null) {
        localPPayCheckRecord.setAdditionalData(str2);
      } else {
        localPPayCheckRecord.setAdditionalData("");
      }
      localPPayCheckRecord.setBankID((String)paramHashMap.get("BankId"));
      localPPayCheckRecord.setBankName((String)paramHashMap.get("ProductName"));
      ((PPayCheckRecords)localObject).add(localPPayCheckRecord);
    }
    paramHashMap.put("PPayCheckRecords", localObject);
  }
  
  protected Boolean getBooleanVoidAttributeValue(ErrorMessages paramErrorMessages, Record paramRecord, String paramString, boolean paramBoolean, Locale paramLocale)
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
    case 86: 
    case 89: 
    case 116: 
    case 118: 
    case 121: 
      bool = true;
      break;
    default: 
      bool = false;
    }
    return new Boolean(bool);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.custom.CustomPPayCheckRecord
 * JD-Core Version:    0.7.0.1
 */