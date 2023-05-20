package com.ffusion.fileimporter.fileadapters.custom;

import com.ffusion.beans.Currency;
import com.ffusion.beans.ach.ACHAccountTypes;
import com.ffusion.beans.ach.ACHClassCode;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.beans.fileimporter.MappingDefinition;
import com.ffusion.beans.fileimporter.Record;
import com.ffusion.csil.FIException;
import com.ffusion.util.RoutingNumberUtil;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;

public class CustomARC
  extends ACHMapper
  implements ACHClassCode, ACHAccountTypes
{
  public void open(HashMap paramHashMap)
    throws FIException
  {
    super.open(paramHashMap);
    paramHashMap.put("AchClassCode", new Integer(1));
    paramHashMap.put("AchClassCodeText", "ARC");
  }
  
  public void processRecord(ErrorMessages paramErrorMessages, MappingDefinition paramMappingDefinition, ACHEntry paramACHEntry, Record paramRecord)
    throws FIException
  {
    Locale localLocale = Locale.US;
    if (paramACHEntry.getLocale() != null) {
      localLocale = paramACHEntry.getLocale();
    }
    String str1 = getStringAttribute(paramErrorMessages, paramRecord, "Consumer Name", nameRequired(paramMappingDefinition.getMatchRecordsBy()), localLocale);
    String str2 = getStringAttribute(paramErrorMessages, paramRecord, "Consumer Routing Number", accountRequired(paramMappingDefinition.getMatchRecordsBy()), localLocale);
    String str3 = getStringAttribute(paramErrorMessages, paramRecord, "Consumer Account Number", accountRequired(paramMappingDefinition.getMatchRecordsBy()), localLocale);
    String str4 = getStringAttribute(paramErrorMessages, paramRecord, "Check Serial Number", idRequired(paramMappingDefinition.getMatchRecordsBy()), localLocale);
    Currency localCurrency = getCurrencyAttribute(paramErrorMessages, paramRecord, "Check Amount", paramMappingDefinition.getMoneyFormatType(), true, localLocale);
    String str5 = getStringAttribute(paramErrorMessages, paramRecord, "Discretionary Data", false, localLocale);
    Boolean localBoolean = getBooleanAttribute(paramErrorMessages, paramRecord, "Prenote Status", false, localLocale);
    paramACHEntry.setAmountIsDebit(true);
    ACHPayee localACHPayee = paramACHEntry.getAchPayee();
    localACHPayee.setAccountType(1);
    localACHPayee.setName(str1);
    localACHPayee.setNickName(str1);
    if ((str2 != null) && (str2.length() == 8)) {
      str2 = str2 + RoutingNumberUtil.getRoutingNumber_CheckDigit(str2);
    }
    localACHPayee.setRoutingNumber(str2);
    localACHPayee.setAccountNumber(str3);
    if (localBoolean != null) {
      paramACHEntry.setPrenote(localBoolean.toString());
    }
    if (localCurrency != null) {
      paramACHEntry.setAmount(localCurrency.getAmountValue().abs());
    }
    paramACHEntry.setCheckSerialNumber(str4);
    paramACHEntry.setDiscretionaryData(str5);
    paramACHEntry.setAchPayee(localACHPayee);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.custom.CustomARC
 * JD-Core Version:    0.7.0.1
 */