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

public class CustomCTXCreditOrDebit
  extends ACHMapper
  implements ACHClassCode, ACHAccountTypes
{
  public void open(HashMap paramHashMap)
    throws FIException
  {
    super.open(paramHashMap);
    paramHashMap.put("AchClassCode", new Integer(13));
    paramHashMap.put("AchClassCodeText", "CTX");
    paramHashMap.put("AchServiceClassCodeText", "200");
  }
  
  public void processRecord(ErrorMessages paramErrorMessages, MappingDefinition paramMappingDefinition, ACHEntry paramACHEntry, Record paramRecord)
    throws FIException
  {
    Locale localLocale = Locale.US;
    if (paramACHEntry.getLocale() != null) {
      localLocale = paramACHEntry.getLocale();
    }
    String str1 = getStringAttribute(paramErrorMessages, paramRecord, "Trading Partner Name", nameRequired(paramMappingDefinition.getMatchRecordsBy()), localLocale);
    String str2 = getStringAttribute(paramErrorMessages, paramRecord, "Trading Partner Name or Identification Number", idRequired(paramMappingDefinition.getMatchRecordsBy()), localLocale);
    String str3 = getStringAttribute(paramErrorMessages, paramRecord, "Trading Partner Routing Number", accountRequired(paramMappingDefinition.getMatchRecordsBy()), localLocale);
    String str4 = getStringAttribute(paramErrorMessages, paramRecord, "Trading Partner Account Number", accountRequired(paramMappingDefinition.getMatchRecordsBy()), localLocale);
    Integer localInteger = getIntegerACHAccountType(paramErrorMessages, paramRecord, "Trading Partner Account Type", accountRequired(paramMappingDefinition.getMatchRecordsBy()), localLocale);
    String str5 = getStringAttribute(paramErrorMessages, paramRecord, "Identification Number", false, localLocale);
    String str6 = getStringAttribute(paramErrorMessages, paramRecord, "Discretionary Data", false, localLocale);
    String str7 = null;
    if (!isValidTransactionCode(localInteger)) {
      str7 = getCreditDebitAttribute(paramErrorMessages, paramRecord, "Credit or Debit", false, localLocale);
    }
    Currency localCurrency = getCurrencyAttribute(paramErrorMessages, paramRecord, "Amount", paramMappingDefinition.getMoneyFormatType(), true, localLocale);
    Boolean localBoolean = getBooleanAttribute(paramErrorMessages, paramRecord, "Prenote Status", false, localLocale);
    String str8 = getStringAttribute(paramErrorMessages, paramRecord, "Payment Related Information", false, localLocale);
    ACHPayee localACHPayee = paramACHEntry.getAchPayee();
    localACHPayee.setAccountType(0);
    localACHPayee.setName(str1);
    localACHPayee.setNickName(str1);
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
    if (str7 != null) {
      paramACHEntry.setAmountIsDebit(str7.equals("debit"));
    }
    if (localCurrency != null) {
      paramACHEntry.setAmount(localCurrency.getAmountValue().abs());
    }
    paramACHEntry.setIdentificationNumber(str5);
    localACHPayee.setUserAccountNumber(str5);
    paramACHEntry.setDiscretionaryData(str6);
    setTransactionCode(paramErrorMessages, paramRecord, localInteger, paramACHEntry, false, localLocale);
    if (str8 != null) {
      paramACHEntry.setAddendaString(str8);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.custom.CustomCTXCreditOrDebit
 * JD-Core Version:    0.7.0.1
 */