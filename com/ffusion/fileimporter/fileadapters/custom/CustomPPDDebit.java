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

public class CustomPPDDebit
  extends ACHMapper
  implements ACHClassCode, ACHAccountTypes
{
  public void open(HashMap paramHashMap)
    throws FIException
  {
    super.open(paramHashMap);
    paramHashMap.put("AchClassCode", new Integer(6));
    paramHashMap.put("AchClassCodeText", "PPD");
    paramHashMap.put("AchServiceClassCodeText", "225");
  }
  
  public void processRecord(ErrorMessages paramErrorMessages, MappingDefinition paramMappingDefinition, ACHEntry paramACHEntry, Record paramRecord)
    throws FIException
  {
    Locale localLocale = Locale.US;
    if (paramACHEntry.getLocale() != null) {
      localLocale = paramACHEntry.getLocale();
    }
    String str1 = getStringAttribute(paramErrorMessages, paramRecord, "Consumer Name", nameRequired(paramMappingDefinition.getMatchRecordsBy()), localLocale);
    String str2 = getStringAttribute(paramErrorMessages, paramRecord, "Consumer Identification Number", idRequired(paramMappingDefinition.getMatchRecordsBy()), localLocale);
    String str3 = getStringAttribute(paramErrorMessages, paramRecord, "Consumer Routing Number", accountRequired(paramMappingDefinition.getMatchRecordsBy()), localLocale);
    String str4 = getStringAttribute(paramErrorMessages, paramRecord, "Consumer Account Number", accountRequired(paramMappingDefinition.getMatchRecordsBy()), localLocale);
    Integer localInteger = getIntegerACHAccountType(paramErrorMessages, paramRecord, "Consumer Account Type", accountRequired(paramMappingDefinition.getMatchRecordsBy()), localLocale);
    Currency localCurrency = getCurrencyAttribute(paramErrorMessages, paramRecord, "Amount", paramMappingDefinition.getMoneyFormatType(), true, localLocale);
    String str5 = getStringAttribute(paramErrorMessages, paramRecord, "Discretionary Data", false, localLocale);
    Boolean localBoolean = getBooleanAttribute(paramErrorMessages, paramRecord, "Prenote Status", false, localLocale);
    String str6 = getStringAttribute(paramErrorMessages, paramRecord, "Payment Related Information", false, localLocale);
    paramACHEntry.setAmountIsDebit(true);
    ACHPayee localACHPayee = paramACHEntry.getAchPayee();
    localACHPayee.setAccountType(0);
    localACHPayee.setName(str1);
    localACHPayee.setNickName(str1);
    localACHPayee.setUserAccountNumber(str2);
    if ((str3 != null) && (str3.length() == 8)) {
      str3 = str3 + RoutingNumberUtil.getRoutingNumber_CheckDigit(str3);
    }
    localACHPayee.setRoutingNumber(str3);
    localACHPayee.setAccountNumber(str4);
    if (localInteger != null) {
      localACHPayee.setAccountType(localInteger.intValue());
    }
    if (localBoolean != null) {
      paramACHEntry.setPrenote(localBoolean.toString());
    }
    if (localCurrency != null) {
      paramACHEntry.setAmount(localCurrency.getAmountValue().abs());
    }
    paramACHEntry.setDiscretionaryData(str5);
    setTransactionCode(paramErrorMessages, paramRecord, localInteger, paramACHEntry, true, localLocale);
    if (str6 != null) {
      paramACHEntry.setAddendaString(str6);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.custom.CustomPPDDebit
 * JD-Core Version:    0.7.0.1
 */