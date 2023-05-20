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

public class CustomCCDCredit
  extends ACHMapper
  implements ACHClassCode, ACHAccountTypes
{
  public void open(HashMap paramHashMap)
    throws FIException
  {
    super.open(paramHashMap);
    paramHashMap.put("AchClassCode", new Integer(12));
    paramHashMap.put("AchClassCodeText", "CCD");
    paramHashMap.put("AchServiceClassCodeText", "220");
  }
  
  public void processRecord(ErrorMessages paramErrorMessages, MappingDefinition paramMappingDefinition, ACHEntry paramACHEntry, Record paramRecord)
    throws FIException
  {
    Locale localLocale = Locale.US;
    if (paramACHEntry.getLocale() != null) {
      localLocale = paramACHEntry.getLocale();
    }
    String str1 = getStringAttribute(paramErrorMessages, paramRecord, "Remote Location Name", nameRequired(paramMappingDefinition.getMatchRecordsBy()), localLocale);
    String str2 = getStringAttribute(paramErrorMessages, paramRecord, "Remote Routing Number", accountRequired(paramMappingDefinition.getMatchRecordsBy()), localLocale);
    String str3 = getStringAttribute(paramErrorMessages, paramRecord, "Remote Account Number", accountRequired(paramMappingDefinition.getMatchRecordsBy()), localLocale);
    Integer localInteger = getIntegerACHAccountType(paramErrorMessages, paramRecord, "Remote Account Type", accountRequired(paramMappingDefinition.getMatchRecordsBy()), localLocale);
    Currency localCurrency = getCurrencyAttribute(paramErrorMessages, paramRecord, "Amount", paramMappingDefinition.getMoneyFormatType(), true, localLocale);
    String str4 = getStringAttribute(paramErrorMessages, paramRecord, "Local Identification Number", idRequired(paramMappingDefinition.getMatchRecordsBy()), localLocale);
    Boolean localBoolean = getBooleanAttribute(paramErrorMessages, paramRecord, "Prenote Status", false, localLocale);
    String str5 = getStringAttribute(paramErrorMessages, paramRecord, "Discretionary Data", false, localLocale);
    String str6 = getStringAttribute(paramErrorMessages, paramRecord, "Payment Related Information", false, localLocale);
    paramACHEntry.setAmountIsDebit(false);
    ACHPayee localACHPayee = paramACHEntry.getAchPayee();
    localACHPayee.setAccountType(0);
    localACHPayee.setName(str1);
    localACHPayee.setNickName(str1);
    if ((str2 != null) && (str2.length() == 8)) {
      str2 = str2 + RoutingNumberUtil.getRoutingNumber_CheckDigit(str2);
    }
    localACHPayee.setRoutingNumber(str2);
    localACHPayee.setAccountNumber(str3);
    if (localInteger != null) {
      localACHPayee.setAccountType(localInteger.intValue());
    }
    if (localBoolean != null) {
      paramACHEntry.setPrenote(localBoolean.toString());
    }
    if (localCurrency != null) {
      paramACHEntry.setAmount(localCurrency.getAmountValue().abs());
    }
    paramACHEntry.setIdentificationNumber(str4);
    localACHPayee.setUserAccountNumber(str4);
    paramACHEntry.setDiscretionaryData(str5);
    setTransactionCode(paramErrorMessages, paramRecord, localInteger, paramACHEntry, true, localLocale);
    if (str6 != null) {
      paramACHEntry.setAddendaString(str6);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.custom.CustomCCDCredit
 * JD-Core Version:    0.7.0.1
 */