package com.ffusion.fileimporter.fileadapters.custom;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.beans.fileimporter.ImportError;
import com.ffusion.beans.fileimporter.MappingDefinition;
import com.ffusion.beans.fileimporter.Record;
import com.ffusion.beans.fileimporter.Records;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransferBank;
import com.ffusion.beans.wiretransfers.WireTransferBanks;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.FIException;
import com.ffusion.util.settings.AccountSettings;
import com.ffusion.util.settings.SystemException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class CustomWireTransfer
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
    long l = System.currentTimeMillis();
    Accounts localAccounts = new Accounts();
    WireTransfers localWireTransfers = new WireTransfers(localLocale);
    Iterator localIterator = paramRecords.iterator();
    for (int i = 1; localIterator.hasNext(); i++)
    {
      Record localRecord = (Record)localIterator.next();
      WireTransfer localWireTransfer = new WireTransfer();
      localWireTransfer.setWireType("SINGLE");
      localWireTransfer.setID(String.valueOf(l + i));
      WireTransferPayee localWireTransferPayee = new WireTransferPayee();
      localWireTransfer.setWirePayee(localWireTransferPayee);
      WireTransferBank localWireTransferBank1 = new WireTransferBank();
      WireTransferBanks localWireTransferBanks = new WireTransferBanks();
      localWireTransferPayee.setDestinationBank(localWireTransferBank1);
      localWireTransferPayee.setIntermediaryBanks(localWireTransferBanks);
      localWireTransfers.add(localWireTransfer);
      String str2 = getStringAttribute(paramErrorMessages, localRecord, "Financial Institution ID", idRequired(localMappingDefinition.getMatchRecordsBy()), localLocale);
      localWireTransfer.setBankID(str2);
      String str3 = getStringAttribute(paramErrorMessages, localRecord, "Business ID", idRequired(localMappingDefinition.getMatchRecordsBy()), localLocale);
      localWireTransfer.setCustomerID(str3);
      String str4 = getStringAttribute(paramErrorMessages, localRecord, "User ID", idRequired(localMappingDefinition.getMatchRecordsBy()), localLocale);
      localWireTransfer.setSubmittedBy(str4);
      localWireTransfer.setUserID(str4);
      String str5 = getStringAttribute(paramErrorMessages, localRecord, "Source Account Number", true, localLocale);
      String str6 = getStringAttribute(paramErrorMessages, localRecord, "Source Routing Number", true, localLocale);
      Integer localInteger = getIntegerAttribute(paramErrorMessages, localRecord, "Source Account Type", true, localLocale);
      localWireTransfer.setFromAccountRoutingNum(str6);
      if (localInteger != null)
      {
        try
        {
          localWireTransfer.setFromAccountID(AccountSettings.buildAccountId(str5, localInteger.toString()));
        }
        catch (SystemException localSystemException1)
        {
          throw new FIException(getClass().getName(), 2, "System settings have not been initialized.", localSystemException1);
        }
        localWireTransfer.setFromAccountNum(str5);
        localWireTransfer.setFromAccountType(localInteger.intValue());
      }
      else
      {
        try
        {
          localWireTransfer.setFromAccountID(AccountSettings.buildAccountId(str5, "0"));
        }
        catch (SystemException localSystemException2)
        {
          throw new FIException(getClass().getName(), 2, "System settings have not been initialized.", localSystemException2);
        }
        localWireTransfer.setFromAccountNum(str5);
        localWireTransfer.setFromAccountType(0);
      }
      Currency localCurrency = getCurrencyAttribute(paramErrorMessages, localRecord, "Amount", localMappingDefinition.getMoneyFormatType(), true, localLocale);
      localWireTransfer.setAmount(localCurrency);
      DateTime localDateTime1 = getDateTimeAttribute(paramErrorMessages, localRecord, "Due Date", localMappingDefinition.getDateFormat(), true, localLocale);
      if (localDateTime1 != null) {
        localDateTime1.setFormat("SHORT");
      }
      localWireTransfer.setDueDate(localDateTime1);
      String str7 = getStringAttribute(paramErrorMessages, localRecord, "Amount Currency", false, localLocale);
      localWireTransfer.setAmtCurrencyType(str7);
      String str8 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Currency", false, localLocale);
      localWireTransfer.setPayeeCurrencyType(str8);
      String str9 = getStringAttribute(paramErrorMessages, localRecord, "Exchange Rate", false, localLocale);
      localWireTransfer.setExchangeRate(str9);
      String str10 = getStringAttribute(paramErrorMessages, localRecord, "Contract Number", false, localLocale);
      localWireTransfer.setContractNumber(str10);
      String str11 = getStringAttribute(paramErrorMessages, localRecord, "Math Rule", false, localLocale);
      localWireTransfer.setMathRule(str11);
      DateTime localDateTime2 = getDateTimeAttribute(paramErrorMessages, localRecord, "Settlement Date", localMappingDefinition.getDateFormat(), false, localLocale);
      if (localDateTime2 != null) {
        localDateTime2.setFormat("SHORT");
      }
      localWireTransfer.setSettlementDate(localDateTime2);
      String str12 = getStringAttribute(paramErrorMessages, localRecord, "Wire Destination", false, localLocale);
      localWireTransfer.setWireDestination(str12);
      String str13 = getStringAttribute(paramErrorMessages, localRecord, "Reference For Beneficiary Comments", false, localLocale);
      localWireTransfer.setComment(str13);
      String str14 = getStringAttribute(paramErrorMessages, localRecord, "Originator To Beneficiary Info Line1", false, localLocale);
      localWireTransfer.setOrigToBeneficiaryInfo1(str14);
      String str15 = getStringAttribute(paramErrorMessages, localRecord, "Originator To Beneficiary Info Line2", false, localLocale);
      localWireTransfer.setOrigToBeneficiaryInfo2(str15);
      String str16 = getStringAttribute(paramErrorMessages, localRecord, "Originator To Beneficiary Info Line3", false, localLocale);
      localWireTransfer.setOrigToBeneficiaryInfo3(str16);
      String str17 = getStringAttribute(paramErrorMessages, localRecord, "Originator To Beneficiary Info Line4", false, localLocale);
      localWireTransfer.setOrigToBeneficiaryInfo4(str17);
      String str18 = getStringAttribute(paramErrorMessages, localRecord, "Bank To Bank Info Line1", false, localLocale);
      localWireTransfer.setBankToBankInfo1(str18);
      String str19 = getStringAttribute(paramErrorMessages, localRecord, "Bank To Bank Info Line2", false, localLocale);
      localWireTransfer.setBankToBankInfo2(str19);
      String str20 = getStringAttribute(paramErrorMessages, localRecord, "Bank To Bank Info Line3", false, localLocale);
      localWireTransfer.setBankToBankInfo3(str20);
      String str21 = getStringAttribute(paramErrorMessages, localRecord, "Bank To Bank Info Line4", false, localLocale);
      localWireTransfer.setBankToBankInfo4(str21);
      String str22 = getStringAttribute(paramErrorMessages, localRecord, "Bank To Bank Info Line5", false, localLocale);
      localWireTransfer.setBankToBankInfo5(str22);
      String str23 = getStringAttribute(paramErrorMessages, localRecord, "Bank To Bank Info Line6", false, localLocale);
      localWireTransfer.setBankToBankInfo6(str23);
      String str24 = getStringAttribute(paramErrorMessages, localRecord, "By Order Of Name", false, localLocale);
      localWireTransfer.setByOrderOfName(str24);
      String str25 = getStringAttribute(paramErrorMessages, localRecord, "By Order Of Address Line1", false, localLocale);
      localWireTransfer.setByOrderOfAddress1(str25);
      String str26 = getStringAttribute(paramErrorMessages, localRecord, "By Order Of Address Line2", false, localLocale);
      localWireTransfer.setByOrderOfAddress2(str26);
      String str27 = getStringAttribute(paramErrorMessages, localRecord, "By Order Of Address Line3", false, localLocale);
      localWireTransfer.setByOrderOfAddress3(str27);
      String str28 = getStringAttribute(paramErrorMessages, localRecord, "By Order Of Account Number", false, localLocale);
      localWireTransfer.setByOrderOfAccount(str28);
      String str29 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Name", nameRequired(localMappingDefinition.getMatchRecordsBy()), localLocale);
      String str30 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Account Number", true, localLocale);
      String str31 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Account Type", true, localLocale);
      localWireTransferPayee.setName(str29);
      localWireTransferPayee.setAccountNum(str30);
      localWireTransferPayee.setAccountType(str31);
      String str32 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Nick Name", false, localLocale);
      localWireTransferPayee.setNickName(str32);
      String str33 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Contact Name", false, localLocale);
      localWireTransferPayee.setContactName(str33);
      localWireTransferPayee.setContact(str33);
      String str34 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Street Address Line1", false, localLocale);
      String str35 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Street Address Line2", false, localLocale);
      String str36 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Street Address Line3", false, localLocale);
      localWireTransferPayee.setStreet(str34);
      localWireTransferPayee.setStreet2(str35);
      localWireTransferPayee.setStreet3(str36);
      String str37 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary City", false, localLocale);
      String str38 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary State", false, localLocale);
      localWireTransferPayee.setCity(str37);
      localWireTransferPayee.setState(str38);
      String str39 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Postal Code", false, localLocale);
      localWireTransferPayee.setZipCode(str39);
      String str40 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Country", false, localLocale);
      localWireTransferPayee.setCountry(str40);
      String str41 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Phone", false, localLocale);
      localWireTransferPayee.setPhone(str41);
      String str42 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Bank Route", false, localLocale);
      localWireTransferPayee.setStatus(str42);
      String str43 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Bank ID", idRequired(localMappingDefinition.getMatchRecordsBy()), localLocale);
      String str44 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Bank Name", nameRequired(localMappingDefinition.getMatchRecordsBy()), localLocale);
      localWireTransferBank1.setBankName(str44);
      localWireTransferBank1.setID(str43);
      String str45 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Bank Fed RTN", false, localLocale);
      String str46 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Bank SWIFT RTN", false, localLocale);
      String str47 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Bank CHIPS RTN", false, localLocale);
      String str48 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Bank Other RTN", false, localLocale);
      localWireTransferBank1.setRoutingFedWire(str45);
      localWireTransferBank1.setRoutingSwift(str46);
      localWireTransferBank1.setRoutingChips(str47);
      localWireTransferBank1.setRoutingOther(str48);
      String str49 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Bank Street Address Line1", false, localLocale);
      String str50 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Bank Street Address Line2", false, localLocale);
      String str51 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Bank Street Address Line3", false, localLocale);
      localWireTransferBank1.setStreet(str49);
      localWireTransferBank1.setStreet2(str50);
      localWireTransferBank1.setStreet3(str51);
      String str52 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Bank City", false, localLocale);
      String str53 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Bank State", false, localLocale);
      localWireTransferBank1.setCity(str52);
      localWireTransferBank1.setState(str53);
      String str54 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Bank Postal Code", false, localLocale);
      String str55 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Bank Country", false, localLocale);
      localWireTransferBank1.setZipCode(str54);
      localWireTransferBank1.setCountry(str55);
      String str56 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank ID", false, localLocale);
      String str57 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank Name", false, localLocale);
      String str58;
      String str59;
      String str60;
      String str61;
      String str62;
      String str63;
      String str64;
      String str65;
      String str66;
      String str67;
      String str68;
      Object localObject;
      if (((str56 != null) && (str56.length() > 0)) || ((str57 != null) && (str57.length() > 0)))
      {
        str58 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank Fed RTN", false, localLocale);
        str59 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank SWIFT RTN", false, localLocale);
        str60 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank CHIPS RTN", false, localLocale);
        str61 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank Other RTN", false, localLocale);
        str62 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank Street Address Line1", false, localLocale);
        str63 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank Street Address Line2", false, localLocale);
        str64 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank Street Address Line3", false, localLocale);
        str65 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank City", false, localLocale);
        str66 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank State", false, localLocale);
        str67 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank Postal Code", false, localLocale);
        str68 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank Country", false, localLocale);
        localObject = localWireTransferBanks.create();
        ((WireTransferBank)localObject).setID(str56);
        ((WireTransferBank)localObject).setBankName(str57);
        ((WireTransferBank)localObject).setRoutingFedWire(str58);
        ((WireTransferBank)localObject).setRoutingSwift(str59);
        ((WireTransferBank)localObject).setRoutingChips(str60);
        ((WireTransferBank)localObject).setRoutingOther(str61);
        ((WireTransferBank)localObject).setStreet(str62);
        ((WireTransferBank)localObject).setStreet2(str63);
        ((WireTransferBank)localObject).setStreet3(str64);
        ((WireTransferBank)localObject).setCity(str65);
        ((WireTransferBank)localObject).setState(str66);
        ((WireTransferBank)localObject).setZipCode(str67);
        ((WireTransferBank)localObject).setCountry(str68);
      }
      if (localWireTransferBanks.size() > 0)
      {
        str58 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank1 ID", false, localLocale);
        str59 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank1 Name", false, localLocale);
        if (((str58 != null) && (str58.length() > 0)) || ((str59 != null) && (str59.length() > 0)))
        {
          str60 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank1 Fed RTN", false, localLocale);
          str61 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank1 SWIFT RTN", false, localLocale);
          str62 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank1 CHIPS RTN", false, localLocale);
          str63 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank1 Other RTN", false, localLocale);
          str64 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank1 Street Address Line1", false, localLocale);
          str65 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank1 Street Address Line2", false, localLocale);
          str66 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank1 Street Address Line3", false, localLocale);
          str67 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank1 City", false, localLocale);
          str68 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank1 State", false, localLocale);
          localObject = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank1 Postal Code", false, localLocale);
          String str69 = getStringAttribute(paramErrorMessages, localRecord, "Beneficiary Intermediary Bank1 Country", false, localLocale);
          WireTransferBank localWireTransferBank2 = localWireTransferBanks.create();
          localWireTransferBank2.setID(str58);
          localWireTransferBank2.setBankName(str59);
          localWireTransferBank2.setRoutingFedWire(str60);
          localWireTransferBank2.setRoutingSwift(str61);
          localWireTransferBank2.setRoutingChips(str62);
          localWireTransferBank2.setRoutingOther(str63);
          localWireTransferBank2.setStreet(str64);
          localWireTransferBank2.setStreet2(str65);
          localWireTransferBank2.setStreet3(str66);
          localWireTransferBank2.setCity(str67);
          localWireTransferBank2.setState(str68);
          localWireTransferBank2.setZipCode((String)localObject);
          localWireTransferBank2.setCountry(str69);
        }
      }
      localWireTransfer.setRecordNumber(localRecord.getRecordNumber());
      localWireTransfer.setLineNumber(localRecord.getLineNumber());
      localWireTransfer.setLineContent(localRecord.getLineContent());
    }
    if (localWireTransfers.size() == 0)
    {
      paramErrorMessages.add(new ImportError(3, getImportError("NoWireTransfers", localLocale), paramErrorMessages.isEmpty() ? getImportError("FileDoesNotContainWireTransfers", localLocale) : getImportError("NoValidWireTransfersFound", localLocale), null, null, null));
      paramErrorMessages.setOperationCanContinue(false);
    }
    paramHashMap.put("ImportWireTransfers", localWireTransfers);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.custom.CustomWireTransfer
 * JD-Core Version:    0.7.0.1
 */