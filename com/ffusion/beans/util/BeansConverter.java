package com.ffusion.beans.util;

import com.ffusion.beans.Currency;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.ach.ACHAddenda;
import com.ffusion.beans.ach.ACHAddendas;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.beans.ach.ACHOffsetAccount;
import com.ffusion.beans.ach.ACHOffsetAccounts;
import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.beans.ach.TaxForm;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.PayeeI18N;
import com.ffusion.beans.billpay.PayeeRoute;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.PaymentBatch;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.cashcon.CashCon;
import com.ffusion.beans.cashcon.CashConAccount;
import com.ffusion.beans.cashcon.CashConAccounts;
import com.ffusion.beans.cashcon.CashConCompanies;
import com.ffusion.beans.cashcon.CashConCompany;
import com.ffusion.beans.cashcon.CashCons;
import com.ffusion.beans.cashcon.Location;
import com.ffusion.beans.cashcon.LocationSearchResult;
import com.ffusion.beans.cashcon.LocationSearchResults;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.beans.exttransfers.ExtTransferAccounts;
import com.ffusion.beans.exttransfers.ExtTransferCompanies;
import com.ffusion.beans.exttransfers.ExtTransferCompany;
import com.ffusion.beans.wiretransfers.WireAccountMap;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.ffs.bpw.interfaces.ACHAddendaInfo;
import com.ffusion.ffs.bpw.interfaces.ACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.ACHRecordInfo;
import com.ffusion.ffs.bpw.interfaces.CCEntryInfo;
import com.ffusion.ffs.bpw.interfaces.CCLocationInfo;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctList;
import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyList;
import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
import com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.RecIntraTrnInfo;
import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumFreqEnum;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.LocaleUtil;
import com.ffusion.util.RoutingNumberUtil;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;

public class BeansConverter
{
  public static final String DATEFORMAT = "yyyyMMdd";
  public static final String[] FreqStrs = { "WEEKLY", "BIWEEKLY", "TWICEMONTHLY", "MONTHLY", "FOURWEEKS", "BIMONTHLY", "QUARTERLY", "SEMIANNUALLY", "ANNUALLY" };
  public static final int[] FreqMap = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
  public static final int[] BPWFreqMap = { 9, 2, 8, 4, 3, 1, 5, 6, 0 };
  public static final EnumFreqEnum[] FreqEnums = { EnumFreqEnum.WEEKLY, EnumFreqEnum.BIWEEKLY, EnumFreqEnum.TWICEMONTHLY, EnumFreqEnum.MONTHLY, EnumFreqEnum.FOURWEEKS, EnumFreqEnum.BIMONTHLY, EnumFreqEnum.QUARTERLY, EnumFreqEnum.SEMIANNUALLY, EnumFreqEnum.ANUALLY };
  public static int[] acctMap = { 1, 2, 7, 12, 4, 5, 6, 8, 9, 10, 11, 13, 3, 14, 15, 3, 16 };
  public static String[] acctTypesStr = { "CHECKING", "SAVINGS", "CREDITLINE", "MONEYMRKT", "LOAN", "MORTGAGE", "HOMEEQUITY", "CD", "IRA", "STOCKBOND", "BROKERAGE", "BUSINESSLOAN", "CREDIT", "FIXEDDEPOSIT", "OTHER", "CREDITCARD", "LEDGER" };
  
  public static Object bpwToEfsBean(Object paramObject, HashMap paramHashMap)
    throws Exception
  {
    String str = "BeansConverter.bpwToEfsBean";
    if (paramObject == null) {
      throw new Exception("BeansConverter.bpwToEfsBeanInvalid BPW Bean passed");
    }
    if ((paramObject instanceof WireInfo)) {
      return jdMethod_try(paramObject, paramHashMap);
    }
    if ((paramObject instanceof ACHBatchInfo)) {
      return bpwToEfsACHBatchBean(paramObject, paramHashMap);
    }
    if ((paramObject instanceof PmtInfo)) {
      return jdMethod_null(paramObject, paramHashMap);
    }
    if ((paramObject instanceof CCEntryInfo)) {
      return jdMethod_if(paramObject, paramHashMap);
    }
    if ((paramObject instanceof IntraTrnInfo)) {
      return jdMethod_long(paramObject, paramHashMap);
    }
    if ((paramObject instanceof TransferInfo)) {
      return jdMethod_goto(paramObject, paramHashMap);
    }
    if ((paramObject instanceof CCLocationInfo)) {
      return jdMethod_byte(paramObject, paramHashMap);
    }
    if ((paramObject instanceof com.ffusion.ffs.bpw.interfaces.BankingDays)) {
      return jdMethod_case(paramObject, paramHashMap);
    }
    throw new Exception("BeansConverter.bpwToEfsBeanInvalid BPW Bean Type passed");
  }
  
  public static Object efsToBpwBean(Object paramObject, HashMap paramHashMap)
    throws Exception
  {
    String str = "BeansConverter.efsToBpwBean";
    if (paramObject == null) {
      throw new Exception("BeansConverter.efsToBpwBeanInvalid EFS Bean passed");
    }
    if ((paramObject instanceof WireTransfer)) {
      return jdMethod_new(paramObject, paramHashMap);
    }
    if ((paramObject instanceof ACHBatch)) {
      return jdMethod_for(paramObject, paramHashMap);
    }
    if ((paramObject instanceof Payment)) {
      return jdMethod_void(paramObject, paramHashMap);
    }
    if ((paramObject instanceof CashCon)) {
      return jdMethod_do(paramObject, paramHashMap);
    }
    if ((paramObject instanceof Transfer))
    {
      Transfer localTransfer = (Transfer)paramObject;
      if (localTransfer.getTransferDestination().equals("INTERNAL")) {
        return jdMethod_else(paramObject, paramHashMap);
      }
      return jdMethod_int(paramObject, paramHashMap);
    }
    if ((paramObject instanceof Location)) {
      return jdMethod_char(paramObject, paramHashMap);
    }
    if ((paramObject instanceof com.ffusion.beans.banking.BankingDays)) {
      return a(paramObject, paramHashMap);
    }
    throw new Exception("BeansConverter.efsToBpwBeanInvalid EFS Bean Type passed");
  }
  
  private static Object jdMethod_new(Object paramObject, HashMap paramHashMap)
    throws Exception
  {
    String str = "BeansConverter.efsToBpwWireBean";
    if (paramObject == null) {
      throw new Exception("BeansConverter.efsToBpwWireBeanInvalid EFS Bean passed");
    }
    WireTransfer localWireTransfer = (WireTransfer)paramObject;
    return localWireTransfer.getWireInfo();
  }
  
  private static Object jdMethod_try(Object paramObject, HashMap paramHashMap)
    throws Exception
  {
    String str = "BeansConverter.bpwToEfsWireBean";
    if (paramObject == null) {
      throw new Exception("BeansConverter.bpwToEfsWireBeanInvalid BPW Bean passed");
    }
    WireTransfer localWireTransfer = new WireTransfer();
    localWireTransfer.setWireInfo((WireInfo)paramObject);
    return localWireTransfer;
  }
  
  private static Object jdMethod_for(Object paramObject, HashMap paramHashMap)
    throws Exception
  {
    return null;
  }
  
  public static Object bpwToEfsACHBatchBean(Object paramObject, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "BeansConverter.bpwToEfsACHBatchBean";
    ACHPayees localACHPayees = (ACHPayees)paramHashMap.get("ACHPayees");
    ACHBatch localACHBatch = (ACHBatch)paramHashMap.get("ACHBatch");
    if (localACHBatch == null) {
      localACHBatch = new ACHBatch();
    }
    if ((paramObject == null) || (!(paramObject instanceof ACHBatchInfo))) {
      throw new Exception("BeansConverter.bpwToEfsACHBatchBeanInvalid BPW Bean passed");
    }
    if (localACHPayees == null)
    {
      String str2 = "ACHPayeeInfos";
      ACHPayeeInfo[] arrayOfACHPayeeInfo = (ACHPayeeInfo[])paramHashMap.get(str2);
      if (arrayOfACHPayeeInfo == null) {
        throw new Exception("BeansConverter.bpwToEfsACHBatchBeanInvalid ACHPayeeInfo[] passed in extra HashMap");
      }
      localACHPayees = new ACHPayees();
      for (int i = 0; i < arrayOfACHPayeeInfo.length; i++)
      {
        ACHPayee localACHPayee = (ACHPayee)localACHPayees.create();
        setPayeeFromPayeeInfo(localACHPayee, arrayOfACHPayeeInfo[i]);
      }
    }
    setBatchFromBatchInfo(localACHBatch, localACHPayees, (ACHBatchInfo)paramObject);
    return localACHBatch;
  }
  
  public static void setBatchFromBatchInfo(ACHBatch paramACHBatch, ACHPayees paramACHPayees, ACHBatchInfo paramACHBatchInfo)
  {
    setBatchHeaderFromBatchInfo(paramACHBatch, paramACHBatchInfo);
    ACHEntries localACHEntries1 = new ACHEntries();
    paramACHBatch.setACHEntries(localACHEntries1);
    ACHRecordInfo[] arrayOfACHRecordInfo = paramACHBatchInfo.getRecords();
    ACHEntry localACHEntry = null;
    int i = 0;
    int j = 0;
    long l1 = 0L;
    long l2 = 0L;
    paramACHBatch.getOffsetAccounts().clear();
    ACHEntries localACHEntries2 = new ACHEntries();
    for (int k = 0; k < arrayOfACHRecordInfo.length; k++)
    {
      localObject1 = arrayOfACHRecordInfo[k];
      localObject2 = null;
      localObject2 = ((ACHRecordInfo)localObject1).getRecordCategory();
      if (localObject2 == null) {
        localObject2 = "";
      }
      if ((((String)localObject2).equalsIgnoreCase("BE")) || (((String)localObject2).equalsIgnoreCase("BB"))) {
        localACHEntry = (ACHEntry)localACHEntries2.create();
      } else {
        localACHEntry = (ACHEntry)localACHEntries1.create();
      }
      localACHEntry.setBpwEntryObject(localObject1);
      localACHEntry.setOffsetAccountID(((ACHRecordInfo)localObject1).getOffsetAccountID());
      localObject3 = ((ACHRecordInfo)localObject1).getPayeeId();
      localACHEntry.setAchPayeeID((String)localObject3);
      Object localObject4;
      if ((paramACHPayees != null) && (!((String)localObject2).equalsIgnoreCase("BE")) && (!((String)localObject2).equalsIgnoreCase("BB")))
      {
        localObject4 = paramACHPayees.getByID((String)localObject3);
        localACHEntry.setAchPayee((ACHPayee)localObject4);
      }
      getEntryFromRecordInfo(paramACHBatch.getStandardEntryClassCodeValue(), localACHEntry, (ACHRecordInfo)localObject1);
      localACHEntry.setID(((ACHRecordInfo)localObject1).getRecordId());
      localACHEntry.setTrackingID(((ACHRecordInfo)localObject1).getPairedID());
      if ((!((String)localObject2).equalsIgnoreCase("BE")) && (!((String)localObject2).equalsIgnoreCase("BB")))
      {
        if (localACHEntry.getAmountIsDebitValue())
        {
          j++;
          l2 += ((ACHRecordInfo)localObject1).getFieldValueLong("Amount");
        }
        else
        {
          i++;
          l1 += ((ACHRecordInfo)localObject1).getFieldValueLong("Amount");
        }
      }
      else if (((String)localObject2).equalsIgnoreCase("BE"))
      {
        localObject4 = paramACHBatch.getOffsetAccounts().getByID(localACHEntry.getOffsetAccountID());
        if (localObject4 == null)
        {
          localObject4 = paramACHBatch.getOffsetAccounts().create();
          ((ACHOffsetAccount)localObject4).setID(localACHEntry.getOffsetAccountID());
          ((ACHOffsetAccount)localObject4).setRoutingNum(RoutingNumberUtil.getRoutingNumber_EightDigits(localACHEntry.getOffsetAccountBankID()) + RoutingNumberUtil.getRoutingNumber_CheckDigit(localACHEntry.getOffsetAccountBankID()));
          ((ACHOffsetAccount)localObject4).setNumber(localACHEntry.getOffsetAccountNumber());
          ((ACHOffsetAccount)localObject4).setNickName(localACHEntry.getOffsetAccountName());
          ((ACHOffsetAccount)localObject4).setType(localACHEntry.getOffsetAccountType());
        }
        if (localACHEntry.getAmountIsDebitValue())
        {
          ((ACHOffsetAccount)localObject4).setTotalNumberDebits(((ACHOffsetAccount)localObject4).getTotalNumberDebits() + 1);
          ((ACHOffsetAccount)localObject4).addTotalDebitAmount(((ACHRecordInfo)localObject1).getFieldValueLong("Amount"));
        }
        else
        {
          ((ACHOffsetAccount)localObject4).setTotalNumberCredits(((ACHOffsetAccount)localObject4).getTotalNumberCredits() + 1);
          ((ACHOffsetAccount)localObject4).addTotalCreditAmount(((ACHRecordInfo)localObject1).getFieldValueLong("Amount"));
        }
      }
      else if (((String)localObject2).equalsIgnoreCase("BB"))
      {
        paramACHBatch.setBpwBalancedBatchObject(localObject1);
        paramACHBatch.setOffsetAccountID(localACHEntry.getOffsetAccountID());
        paramACHBatch.setOffsetAccountBankID(RoutingNumberUtil.getRoutingNumber_EightDigits(localACHEntry.getOffsetAccountBankID()) + RoutingNumberUtil.getRoutingNumber_CheckDigit(localACHEntry.getOffsetAccountBankID()));
        paramACHBatch.setOffsetAccountName(localACHEntry.getOffsetAccountName());
        paramACHBatch.setOffsetAccountNumber(localACHEntry.getOffsetAccountNumber());
        paramACHBatch.setOffsetAccountType(localACHEntry.getOffsetAccountType());
        localACHEntries2.remove(localACHEntry);
        localObject4 = paramACHBatch.getOffsetAccount();
        if (localACHEntry.getAmountIsDebitValue())
        {
          ((ACHOffsetAccount)localObject4).setTotalNumberDebits(1);
          ((ACHOffsetAccount)localObject4).setTotalDebitAmount(((ACHRecordInfo)localObject1).getFieldValueLong("Amount"));
        }
        else
        {
          ((ACHOffsetAccount)localObject4).setTotalNumberCredits(1);
          ((ACHOffsetAccount)localObject4).setTotalCreditAmount(((ACHRecordInfo)localObject1).getFieldValueLong("Amount"));
        }
      }
      if (((ACHRecordInfo)localObject1).getAddendaCount().intValue() != 0)
      {
        localObject4 = ((ACHRecordInfo)localObject1).getAddenda();
        ACHAddendas localACHAddendas = new ACHAddendas();
        localACHEntry.setAddendas(localACHAddendas);
        for (int m = 0; m < localObject4.length; m++)
        {
          Object localObject5 = localObject4[m];
          ACHAddenda localACHAddenda = (ACHAddenda)localACHAddendas.create();
          localACHAddenda.setBpwAddendaObject(localObject5);
          String str2 = localObject5.getFieldValueString("Payment_Related_Information");
          localACHAddenda.setPmtRelatedInfo(str2);
        }
      }
      if ((paramACHBatch.getTaxForm() != null) || (paramACHBatch.getTaxFormID() != null)) {
        if (((ACHRecordInfo)localObject1).getTaxFormId() != null) {
          localACHEntry.setTaxFormID(((ACHRecordInfo)localObject1).getTaxFormId());
        } else {
          localACHEntry.setTaxFormID(paramACHBatch.getTaxForm() != null ? paramACHBatch.getTaxForm().getID() : paramACHBatch.getTaxFormID());
        }
      }
      if ((!((String)localObject2).equalsIgnoreCase("BB")) && (!((String)localObject2).equalsIgnoreCase("BE")))
      {
        localObject4 = localACHEntry.getAchPayee();
        if (localObject4 == null)
        {
          localObject4 = new ACHPayee();
          localACHEntry.setAchPayee((ACHPayee)localObject4);
          ((ACHPayee)localObject4).setName(localACHEntry.getOffsetAccountName());
          ((ACHPayee)localObject4).setAccountNumber(localACHEntry.getOffsetAccountNumber());
          ((ACHPayee)localObject4).setAccountType(localACHEntry.getOffsetAccountType());
          ((ACHPayee)localObject4).setRoutingNumber(RoutingNumberUtil.getRoutingNumber_EightDigits(localACHEntry.getOffsetAccountBankID()) + RoutingNumberUtil.getRoutingNumber_CheckDigit(localACHEntry.getOffsetAccountBankID()));
          ((ACHPayee)localObject4).setUserAccountNumber(localACHEntry.getIdentificationNumber());
        }
      }
    }
    String str1 = getAmountAddDecimal("" + (l1 + l2));
    Object localObject1 = new BigDecimal(str1);
    paramACHBatch.setAmount((BigDecimal)localObject1);
    paramACHBatch.setTotalDebitAmount(new BigDecimal(getAmountAddDecimal("" + l2)));
    paramACHBatch.setTotalCreditAmount(new BigDecimal(getAmountAddDecimal("" + l1)));
    Object localObject2 = localACHEntries2.iterator();
    Object localObject3 = null;
    while (((Iterator)localObject2).hasNext())
    {
      localACHEntry = (ACHEntry)((Iterator)localObject2).next();
      localObject3 = localACHEntries1.getByTrackingID(localACHEntry.getTrackingID());
      if (localObject3 != null)
      {
        ((ACHEntry)localObject3).setBpwBalancedEntryObject(localACHEntry.getBpwEntryObject());
        ((ACHEntry)localObject3).setOffsetAccountID(localACHEntry.getOffsetAccountID());
        ((ACHEntry)localObject3).setOffsetAccountBankID(RoutingNumberUtil.getRoutingNumber_EightDigits(localACHEntry.getOffsetAccountBankID()) + RoutingNumberUtil.getRoutingNumber_CheckDigit(localACHEntry.getOffsetAccountBankID()));
        ((ACHEntry)localObject3).setOffsetAccountName(localACHEntry.getOffsetAccountName());
        ((ACHEntry)localObject3).setOffsetAccountNumber(localACHEntry.getOffsetAccountNumber());
        ((ACHEntry)localObject3).setOffsetAccountType(localACHEntry.getOffsetAccountType());
      }
    }
    paramACHBatch.setTotalNumberCredits(i);
    paramACHBatch.setTotalNumberDebits(j);
    paramACHBatch.setNumberEntries(localACHEntries1.size());
  }
  
  public static void getEntryFromRecordInfo(int paramInt, ACHEntry paramACHEntry, ACHRecordInfo paramACHRecordInfo)
  {
    long l = 0L;
    int i = 0;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    int j = 1;
    int k = 0;
    String str1 = paramACHRecordInfo.getFieldValueString("Discretionary_Data");
    str2 = paramACHRecordInfo.getFieldValueString("DFI_Account_Number");
    str3 = paramACHRecordInfo.getFieldValueString("Receiving_DFI_Identification");
    str4 = paramACHRecordInfo.getFieldValueString("Check_Digit");
    i = paramACHRecordInfo.getFieldValueShort("Transaction_Code");
    l = paramACHRecordInfo.getFieldValueLong("Amount");
    String str5 = null;
    String str6 = null;
    String str7 = "Individual_Identification_Number";
    if ((paramInt == 10) || (paramInt == 9) || (paramInt == 2) || (paramInt == 11) || (paramInt == 12) || (paramInt == 13) || (paramInt == 4) || (paramInt == 6))
    {
      if ((paramInt == 12) || (paramInt == 13) || (paramInt == 11)) {
        str7 = "Identification_Number";
      }
      str6 = paramACHRecordInfo.getFieldValueString(str7);
    }
    str7 = "Individual_Name";
    if ((paramInt == 12) || (paramInt == 13) || (paramInt == 11)) {
      str7 = "Receiving_Company_Name";
    }
    if (paramInt != 18) {
      str5 = paramACHRecordInfo.getFieldValueString(str7);
    }
    if (str6 != null) {
      paramACHEntry.setIdentificationNumber(str6);
    }
    if (str5 != null) {
      paramACHEntry.setOffsetAccountName(str5);
    }
    if ((i >= 20) && (i <= 29))
    {
      j = 1;
      if (i >= 25) {
        k = 1;
      }
    }
    else if ((i >= 30) && (i <= 39))
    {
      j = 2;
      if (i >= 35) {
        k = 1;
      }
    }
    else if ((i >= 40) && (i <= 49))
    {
      j = 4;
      if (i >= 45) {
        k = 1;
      }
    }
    else if ((i >= 50) && (i <= 56))
    {
      j = 3;
      if (i >= 55) {
        k = 1;
      }
    }
    if ((i == 23) || (i == 28) || (i == 33) || (i == 38) || (i == 43) || (i == 48) || (i == 53)) {
      paramACHEntry.setPrenote("true");
    }
    String str8 = getAmountAddDecimal("" + l);
    if (k != 0) {
      paramACHEntry.setAmountIsDebit("true");
    } else {
      paramACHEntry.setAmountIsDebit("false");
    }
    BigDecimal localBigDecimal = new BigDecimal(str8);
    paramACHEntry.setAmount(localBigDecimal);
    paramACHEntry.setOffsetAccountType(j);
    paramACHEntry.setOffsetAccountBankID(str3 + str4);
    paramACHEntry.setOffsetAccountNumber(str2);
    if (str1 != null) {
      paramACHEntry.setDiscretionaryData(str1);
    }
    if ((paramInt == 1) || (paramInt == 5) || (paramInt == 17) || (paramInt == 18)) {
      paramACHEntry.setCheckSerialNumber(paramACHRecordInfo.getFieldValueString("Check_Serial_Number"));
    }
    if (paramInt == 5)
    {
      paramACHEntry.setTerminalCity(paramACHRecordInfo.getFieldValueString("Terminal_City4"));
      paramACHEntry.setTerminalState(paramACHRecordInfo.getFieldValueString("Terminal_State"));
    }
    if (paramInt == 18)
    {
      paramACHEntry.setProcessControlField(paramACHRecordInfo.getFieldValueString("Process_Control_Field"));
      paramACHEntry.setItemResearchNumber(paramACHRecordInfo.getFieldValueString("Item_Research_Number"));
    }
    if (paramInt == 10) {
      paramACHEntry.setPaymentTypeCode(paramACHRecordInfo.getFieldValueString("Payment_Type_Code"));
    }
  }
  
  public static void setBatchHeaderFromBatchInfo(ACHBatch paramACHBatch, ACHBatchInfo paramACHBatchInfo)
  {
    Hashtable localHashtable = paramACHBatchInfo.getMemo();
    String str1 = paramACHBatchInfo.getDueDate();
    if (str1 == null) {
      str1 = paramACHBatchInfo.getDtProcessed();
    }
    if ((str1 != null) && (str1.length() > 8)) {
      str1 = str1.substring(0, 8);
    }
    paramACHBatch.setID(paramACHBatchInfo.getBatchId());
    if ((paramACHBatchInfo.getBatchType() != null) && ((paramACHBatchInfo.getBatchType().equalsIgnoreCase("Recurring")) || (paramACHBatchInfo.getBatchType().equalsIgnoreCase("Repetitive"))))
    {
      paramACHBatch.setRecID(paramACHBatchInfo.getRecBatchId());
      if (paramACHBatchInfo.getRecBatchId() == null) {
        paramACHBatch.setRecID(paramACHBatchInfo.getBatchId());
      }
    }
    String str2 = paramACHBatch.getDateFormat();
    paramACHBatch.setDateFormat("yyyyMMdd");
    paramACHBatch.setDate(str1);
    if (paramACHBatchInfo.getDtProcessed() != null) {
      paramACHBatch.setProcessedOnDate(paramACHBatchInfo.getDtProcessed());
    } else {
      paramACHBatch.setProcessedOnDate(str1);
    }
    paramACHBatch.setDateFormat("yyyy/MM/dd HH:mm:ss");
    paramACHBatch.setSubmitDate(paramACHBatchInfo.getSubmitDate());
    paramACHBatch.setDateFormat(str2);
    paramACHBatch.setNumberEntries(new Long(paramACHBatchInfo.getTotalBatchSize()).intValue());
    paramACHBatch.setStandardEntryClassCode(paramACHBatchInfo.getClassCode());
    paramACHBatch.setOffsetAccountID(paramACHBatchInfo.getOffsetAccountID());
    String str3 = null;
    if (localHashtable != null)
    {
      paramACHBatch.setName((String)localHashtable.get("NAME"));
      str3 = (String)localHashtable.get("TAXID");
      paramACHBatch.setTaxFormID(str3);
      paramACHBatch.setOriginalID((String)localHashtable.get("ORIGINALID"));
      paramACHBatch.setCompanyID((String)localHashtable.get("COMPANYID"));
      str4 = (String)localHashtable.get("CREDIT");
      if (str4 == null)
      {
        int i = paramACHBatch.getStandardEntryClassCodeValue();
        if ((i == 1) || (i == 18) || (i == 10) || (i == 5) || (i == 17) || (i == 9)) {
          str4 = "1";
        }
        if (i == 2) {
          str4 = "0";
        }
      }
      if (str4 != null) {
        paramACHBatch.setDebitBatch(str4);
      }
      String str5 = (String)localHashtable.get("TOTALENTRIES");
      if (str5 != null) {
        paramACHBatch.setNumberEntries(new Integer(str5).intValue());
      }
      String str6 = paramACHBatchInfo.getLogId();
      if (str6 == null) {
        str6 = (String)localHashtable.get("TRACKINGID");
      }
      if (str6 != null) {
        paramACHBatch.setTrackingID(str6);
      }
      if (localHashtable.get("TEMPLATEID") != null) {
        paramACHBatch.setTemplateID((String)localHashtable.get("TEMPLATEID"));
      }
      if (localHashtable.get("SCOPE") != null) {
        paramACHBatch.setBatchScope((String)localHashtable.get("SCOPE"));
      }
    }
    if ("BatchBalancedBatch".equalsIgnoreCase(paramACHBatchInfo.getBatchBalanceType()))
    {
      paramACHBatch.setBatchType(2);
      str4 = (String)(localHashtable == null ? null : localHashtable.get("BB_ACCOUNTID"));
      if (str4 != null)
      {
        paramACHBatch.setOffsetAccountID(str4);
        paramACHBatch.setOffsetAccountNumber((String)localHashtable.get("BB_ACCOUNTNUMBER"));
        paramACHBatch.setOffsetAccountBankID((String)localHashtable.get("BB_ACCOUNTBANKID"));
        paramACHBatch.setOffsetAccountType((String)localHashtable.get("BB_ACCOUNTTYPE"));
        paramACHBatch.setOffsetAccountName((String)localHashtable.get("BB_ACCOUNTNAME"));
      }
    }
    else if ("EntryBalancedBatch".equalsIgnoreCase(paramACHBatchInfo.getBatchBalanceType()))
    {
      paramACHBatch.setBatchType(3);
    }
    else
    {
      paramACHBatch.setBatchType(1);
    }
    paramACHBatch.setCategory(paramACHBatchInfo.getBatchCategory());
    paramACHBatch.setHeaderCompName(paramACHBatchInfo.getHeaderCompName());
    paramACHBatch.setSubmittedBy(paramACHBatchInfo.getSubmittedBy());
    String str4 = "ACHBatch";
    if (paramACHBatchInfo.getBatchCategory() != null)
    {
      if (paramACHBatchInfo.getBatchCategory().equals("ACH_BATCH_TAX")) {
        str4 = "TaxPayment";
      }
      if (paramACHBatchInfo.getBatchCategory().equals("ACH_BATCH_CHILD_SUPPORT")) {
        str4 = "ChildSupportPayment";
      }
    }
    paramACHBatch.setACHType(str4);
    paramACHBatch.setStatus(ACHBatch.mapBPWStatusToInt(paramACHBatchInfo.getBatchStatus()));
    long l1 = paramACHBatchInfo.getNonOffBatchCreditSum();
    long l2 = paramACHBatchInfo.getNonOffBatchDebitSum();
    String str7 = getAmountAddDecimal("" + (l1 + l2));
    BigDecimal localBigDecimal = new BigDecimal(str7);
    paramACHBatch.setAmount(localBigDecimal);
    paramACHBatch.setTotalDebitAmount(new BigDecimal(getAmountAddDecimal("" + l2)));
    paramACHBatch.setTotalCreditAmount(new BigDecimal(getAmountAddDecimal("" + l1)));
    ACHRecordInfo localACHRecordInfo = paramACHBatchInfo.getBatchHeader();
    paramACHBatch.setCoDiscretionaryData((String)localACHRecordInfo.getFieldValueObject("Company_Discretionary_Data"));
    paramACHBatch.setCoEntryDesc((String)localACHRecordInfo.getFieldValueObject("Company_Entry_Description"));
    if ("REVERSAL".equalsIgnoreCase(paramACHBatch.getCoEntryDesc())) {
      paramACHBatch.setCanEdit("false");
    }
    paramACHBatch.setCoID(paramACHBatchInfo.getCompId());
    paramACHBatch.setCoName(paramACHBatchInfo.getCompName());
    if ((paramACHBatchInfo instanceof RecACHBatchInfo))
    {
      RecACHBatchInfo localRecACHBatchInfo = null;
      localRecACHBatchInfo = (RecACHBatchInfo)paramACHBatchInfo;
      paramACHBatch.setNumberPayments(localRecACHBatchInfo.getPmtsCount());
      paramACHBatch.setFrequency(getBPWFrequency(localRecACHBatchInfo.getFrequency()));
      paramACHBatch.set("StartDate", localRecACHBatchInfo.getStartDate());
    }
  }
  
  public static int getBPWFrequency(String paramString)
  {
    for (int i = 0; i < FreqStrs.length; i++) {
      if (paramString.equals(FreqStrs[i])) {
        return FreqMap[i];
      }
    }
    if ((paramString.equalsIgnoreCase("SEMIMONTHLY")) || (paramString.equalsIgnoreCase("TWICEMONTHLY"))) {
      return 3;
    }
    return -1;
  }
  
  public static String getBPWFrequency(int paramInt)
  {
    for (int i = 0; i < FreqMap.length; i++) {
      if (paramInt == FreqMap[i]) {
        return FreqStrs[i];
      }
    }
    return null;
  }
  
  public static String getAmountAddDecimal(String paramString)
  {
    String str = paramString;
    int i = 0;
    if (str.startsWith("-"))
    {
      i = 1;
      str = str.substring(1);
    }
    int j = str.length();
    if (j == 1)
    {
      str = "0" + str;
      j = 2;
    }
    if (j <= 2) {
      str = "0." + str;
    } else {
      str = str.substring(0, j - 2) + "." + str.substring(j - 2);
    }
    if (i != 0) {
      str = "-" + str;
    }
    return str;
  }
  
  public static Object bpwToEfsACHPayeeBean(Object paramObject, HashMap paramHashMap)
    throws Exception
  {
    String str = "BeansConverter.bpwToEfsACHPayeeBean";
    ACHPayees localACHPayees = (ACHPayees)paramHashMap.get("ACHPayees");
    ACHPayee localACHPayee = null;
    if (localACHPayees != null) {
      localACHPayee = (ACHPayee)localACHPayees.create();
    } else {
      localACHPayee = new ACHPayee();
    }
    if ((paramObject == null) || (!(paramObject instanceof ACHPayeeInfo))) {
      throw new Exception("BeansConverter.bpwToEfsACHPayeeBeanInvalid BPW Bean passed");
    }
    setPayeeFromPayeeInfo(localACHPayee, (ACHPayeeInfo)paramObject);
    return localACHPayee;
  }
  
  public static void setPayeeFromPayeeInfo(ACHPayee paramACHPayee, ACHPayeeInfo paramACHPayeeInfo)
  {
    String str1 = null;
    paramACHPayee.setID(paramACHPayeeInfo.getPayeeID());
    paramACHPayee.setTrackingID(paramACHPayeeInfo.getLogId());
    paramACHPayee.setBankName(paramACHPayeeInfo.getContactName());
    paramACHPayee.setRoutingNumber(paramACHPayeeInfo.getBankRTN());
    paramACHPayee.setAccountNumber(paramACHPayeeInfo.getBankAcctId());
    if (paramACHPayeeInfo.getSecurePayee() == 0) {
      paramACHPayee.setSecurePayee(false);
    } else if (paramACHPayeeInfo.getSecurePayee() == 1) {
      paramACHPayee.setSecurePayee(true);
    }
    String str2 = paramACHPayeeInfo.getBankAcctType();
    if ("Checking".equalsIgnoreCase(str2)) {
      paramACHPayee.setAccountType(1);
    }
    if ("Savings".equalsIgnoreCase(str2)) {
      paramACHPayee.setAccountType(2);
    }
    if ("Loan".equalsIgnoreCase(str2)) {
      paramACHPayee.setAccountType(3);
    }
    if ("Ledger".equalsIgnoreCase(str2)) {
      paramACHPayee.setAccountType(4);
    }
    paramACHPayee.setName(paramACHPayeeInfo.getPayeeName());
    paramACHPayee.setUserAccountNumber(paramACHPayeeInfo.getPayAcct());
    paramACHPayee.setCompanyID(paramACHPayeeInfo.getCompId());
    Hashtable localHashtable = paramACHPayeeInfo.getMemo();
    if (localHashtable != null) {}
    String str3 = paramACHPayeeInfo.getPayeeGroup();
    if (str3 != null) {
      if (str3.equals("Business")) {
        paramACHPayee.setPayeeGroup(3);
      } else if (str3.equals("User")) {
        paramACHPayee.setPayeeGroup(1);
      } else {
        paramACHPayee.setPayeeGroup(2);
      }
    }
    str1 = paramACHPayeeInfo.getNickName();
    if ((str1 == null) || (str1.length() == 0)) {
      paramACHPayee.setNickName(paramACHPayeeInfo.getPayeeName());
    } else {
      paramACHPayee.setNickName(str1);
    }
    if ("Not Required".equalsIgnoreCase(paramACHPayeeInfo.getPrenoteCreditStatus())) {
      paramACHPayee.setPrenoteStatus("PRENOTE_NOT_REQUIRED");
    } else if ("Pending".equalsIgnoreCase(paramACHPayeeInfo.getPrenoteCreditStatus())) {
      paramACHPayee.setPrenoteStatus("PRENOTE_PENDING");
    } else if ("Failed".equalsIgnoreCase(paramACHPayeeInfo.getPrenoteCreditStatus())) {
      paramACHPayee.setPrenoteStatus("PRENOTE_FAILED");
    } else {
      paramACHPayee.setPrenoteStatus("PRENOTE_SUCCEEDED");
    }
    String str4 = paramACHPayee.getDateFormat();
    paramACHPayee.setDateFormat("yyyy/MM/dd");
    if (paramACHPayeeInfo.getPrenoteSubmitDate() != null) {
      paramACHPayee.setPrenoteSubmitDate(paramACHPayeeInfo.getPrenoteSubmitDate());
    }
    paramACHPayee.setDateFormat("yyyyMMdd");
    if (paramACHPayeeInfo.getPrenoteMaturedDate() != null) {
      paramACHPayee.setPrenoteMatureDate(paramACHPayeeInfo.getPrenoteMaturedDate());
    }
    paramACHPayee.setDateFormat(str4);
    if ("Y".equalsIgnoreCase(paramACHPayeeInfo.getManagedPayee())) {
      paramACHPayee.setScope("ACHCompany");
    } else if ("N".equalsIgnoreCase(paramACHPayeeInfo.getManagedPayee())) {
      paramACHPayee.setScope("ACHBatch");
    } else if ("Template".equalsIgnoreCase(paramACHPayeeInfo.getManagedPayee())) {
      paramACHPayee.setScope("ACHTemplate");
    }
    paramACHPayee.setTrackingID(paramACHPayeeInfo.getLogId());
    paramACHPayee.setSubmittedBy(paramACHPayeeInfo.getSubmittedBy());
  }
  
  private static Object jdMethod_else(Object paramObject, HashMap paramHashMap)
    throws Exception
  {
    return null;
  }
  
  private static Object jdMethod_long(Object paramObject, HashMap paramHashMap)
    throws Exception
  {
    Transfers localTransfers = new Transfers();
    Transfer localTransfer = (Transfer)localTransfers.createNoAdd();
    if (!(paramObject instanceof IntraTrnInfo)) {
      throw new Exception("bpwBean not IntraTrnInfo object");
    }
    IntraTrnInfo localIntraTrnInfo = (IntraTrnInfo)paramObject;
    Accounts localAccounts = new Accounts();
    setXferFromIntraTrnInfo(localTransfer, localIntraTrnInfo, localAccounts, false);
    return localTransfer;
  }
  
  public static void setXferFromIntraTrnInfo(Transfer paramTransfer, IntraTrnInfo paramIntraTrnInfo, Accounts paramAccounts, boolean paramBoolean)
  {
    paramTransfer.setID(paramIntraTrnInfo.srvrTid);
    paramTransfer.setTrackingID(paramIntraTrnInfo.logId);
    paramTransfer.setAmount(new BigDecimal(paramIntraTrnInfo.amount));
    paramTransfer.setAmtCurrency(paramIntraTrnInfo.curDef);
    if ((paramIntraTrnInfo.toAmount != null) && (paramIntraTrnInfo.toAmount.length() > 0))
    {
      paramTransfer.setToAmount(new BigDecimal(paramIntraTrnInfo.toAmount));
      paramTransfer.setToAmtCurrency(paramIntraTrnInfo.toAmtCurrency);
      paramTransfer.setUserAssignedAmountFlag(paramIntraTrnInfo.userAssignedAmount);
    }
    String str1 = paramTransfer.getDateFormat();
    paramTransfer.setDateFormat("yyyyMMdd");
    String str2 = paramIntraTrnInfo.dateToPost;
    if ((str2 != null) && (str2.length() > 8)) {
      str2 = str2.substring(0, 8);
    }
    paramTransfer.setDate(str2);
    paramTransfer.setDateFormat(str1);
    String str3 = paramIntraTrnInfo.acctTypeFrom;
    int i = getBPWAccountType(str3);
    String str4 = paramIntraTrnInfo.acctIdFrom;
    Object localObject;
    if (paramAccounts != null)
    {
      localObject = paramAccounts.getByAccountNumberAndType(str4, i);
      if ((localObject == null) || (!((Account)localObject).isFilterable("TransferFrom"))) {
        if (paramBoolean)
        {
          localObject = paramAccounts.createNoAdd("Restricted", 0);
          ((Account)localObject).setNickName("Account");
          paramTransfer.setAccountEntitled("false");
        }
        else
        {
          localObject = paramAccounts.createNoAdd(str4, i);
          ((Account)localObject).setBankID(paramIntraTrnInfo.bankId);
          ((Account)localObject).setCurrencyCode(paramIntraTrnInfo.curDef);
        }
      }
      paramTransfer.setFromAccount((Account)localObject);
    }
    else
    {
      localObject = new Account();
      ((Account)localObject).setID(str4, Integer.toString(i));
      ((Account)localObject).setBankID(paramIntraTrnInfo.bankId);
      ((Account)localObject).setCurrencyCode(paramIntraTrnInfo.curDef);
      paramTransfer.setFromAccount((Account)localObject);
    }
    str3 = paramIntraTrnInfo.acctTypeTo;
    i = getBPWAccountType(str3);
    str4 = paramIntraTrnInfo.acctIdTo;
    if (paramAccounts != null)
    {
      localObject = paramAccounts.getByAccountNumberAndType(str4, i);
      if ((localObject == null) || (!((Account)localObject).isFilterable("TransferTo"))) {
        if (paramBoolean)
        {
          localObject = paramAccounts.createNoAdd("Restricted", 0);
          ((Account)localObject).setNickName("Account");
          paramTransfer.setAccountEntitled("false");
        }
        else
        {
          localObject = paramAccounts.createNoAdd(str4, i);
          ((Account)localObject).setBankID(paramIntraTrnInfo.bankId);
          ((Account)localObject).setCurrencyCode(paramIntraTrnInfo.curDef);
        }
      }
      paramTransfer.setToAccount((Account)localObject);
    }
    else
    {
      localObject = new Account();
      ((Account)localObject).setID(str4, Integer.toString(i));
      ((Account)localObject).setBankID(paramIntraTrnInfo.bankId);
      ((Account)localObject).setCurrencyCode(paramIntraTrnInfo.curDef);
      paramTransfer.setToAccount((Account)localObject);
    }
    paramTransfer.setSubmittedBy(paramIntraTrnInfo.submittedBy);
    paramTransfer.mapXferStatusToInt(paramIntraTrnInfo.status);
    if ((paramIntraTrnInfo.extraFields != null) && ((paramIntraTrnInfo.extraFields instanceof HashMap)))
    {
      localObject = (HashMap)paramIntraTrnInfo.extraFields;
      if (((HashMap)localObject).get("Memo") != null) {
        paramTransfer.setMemo((String)((HashMap)localObject).get("Memo"));
      }
    }
    paramTransfer.setRecTransferID(paramIntraTrnInfo.recSrvrTid);
    paramTransfer.put("Frequency", "");
    paramTransfer.put("NumberTransfers", "");
  }
  
  public static void setRecXferFromRecIntraTrnInfo(RecTransfer paramRecTransfer, RecIntraTrnInfo paramRecIntraTrnInfo, Accounts paramAccounts, boolean paramBoolean)
  {
    paramRecTransfer.setID(paramRecIntraTrnInfo.recSrvrTid);
    paramRecTransfer.setTrackingID(paramRecIntraTrnInfo.logId);
    paramRecTransfer.setAmount(new BigDecimal(paramRecIntraTrnInfo.amount));
    paramRecTransfer.setAmtCurrency(paramRecIntraTrnInfo.curDef);
    String str1 = paramRecTransfer.getDateFormat();
    paramRecTransfer.setDateFormat("yyyyMMdd");
    String str2 = paramRecIntraTrnInfo.dateToPost;
    if ((str2 != null) && (str2.length() > 8)) {
      str2 = str2.substring(0, 8);
    }
    paramRecTransfer.setDate(str2);
    paramRecTransfer.setDateFormat(str1);
    String str3 = paramRecIntraTrnInfo.acctTypeFrom;
    int i = getBPWAccountType(str3);
    String str4 = paramRecIntraTrnInfo.acctIdFrom;
    Object localObject;
    if (paramAccounts != null)
    {
      localObject = paramAccounts.getByAccountNumberAndType(str4, i);
      if ((localObject == null) || (!((Account)localObject).isFilterable("TransferFrom"))) {
        if (paramBoolean)
        {
          localObject = paramAccounts.createNoAdd("Restricted", 0);
          ((Account)localObject).setNickName("Account");
          paramRecTransfer.setAccountEntitled("false");
        }
        else
        {
          localObject = paramAccounts.createNoAdd(str4, i);
          ((Account)localObject).setBankID(paramRecIntraTrnInfo.bankId);
          ((Account)localObject).setCurrencyCode(paramRecIntraTrnInfo.curDef);
        }
      }
      paramRecTransfer.setFromAccount((Account)localObject);
    }
    else
    {
      localObject = new Account();
      ((Account)localObject).setID(str4, Integer.toString(i));
      ((Account)localObject).setBankID(paramRecIntraTrnInfo.bankId);
      ((Account)localObject).setCurrencyCode(paramRecIntraTrnInfo.curDef);
      paramRecTransfer.setFromAccount((Account)localObject);
    }
    str3 = paramRecIntraTrnInfo.acctTypeTo;
    i = getBPWAccountType(str3);
    str4 = paramRecIntraTrnInfo.acctIdTo;
    if (paramAccounts != null)
    {
      localObject = paramAccounts.getByAccountNumberAndType(str4, i);
      if ((localObject == null) || (!((Account)localObject).isFilterable("TransferTo"))) {
        if (paramBoolean)
        {
          localObject = paramAccounts.createNoAdd("Restricted", 0);
          ((Account)localObject).setNickName("Account");
          paramRecTransfer.setAccountEntitled("false");
        }
        else
        {
          localObject = paramAccounts.createNoAdd(str4, i);
          ((Account)localObject).setBankID(paramRecIntraTrnInfo.bankId);
          ((Account)localObject).setCurrencyCode(paramRecIntraTrnInfo.curDef);
        }
      }
      paramRecTransfer.setToAccount((Account)localObject);
    }
    else
    {
      localObject = new Account();
      ((Account)localObject).setID(str4, Integer.toString(i));
      ((Account)localObject).setBankID(paramRecIntraTrnInfo.bankId);
      ((Account)localObject).setCurrencyCode(paramRecIntraTrnInfo.curDef);
      paramRecTransfer.setToAccount((Account)localObject);
    }
    paramRecTransfer.mapXferStatusToInt(paramRecIntraTrnInfo.status);
    if ((paramRecIntraTrnInfo.extraFields != null) && ((paramRecIntraTrnInfo.extraFields instanceof HashMap)))
    {
      localObject = (HashMap)paramRecIntraTrnInfo.extraFields;
      if (((HashMap)localObject).get("Memo") != null) {
        paramRecTransfer.setMemo((String)((HashMap)localObject).get("Memo"));
      }
    }
    paramRecTransfer.setRecTransferID(paramRecIntraTrnInfo.recSrvrTid);
    paramRecTransfer.setFrequency(getFrequency(EnumFreqEnum.from_int(paramRecIntraTrnInfo.freq)));
    if (paramRecIntraTrnInfo.nInstsExists) {
      paramRecTransfer.setNumberTransfers(paramRecIntraTrnInfo.nInsts);
    } else {
      paramRecTransfer.setNumberTransfers(999);
    }
  }
  
  private static Object jdMethod_void(Object paramObject, HashMap paramHashMap)
    throws Exception
  {
    return null;
  }
  
  private static Object jdMethod_null(Object paramObject, HashMap paramHashMap)
    throws Exception
  {
    Accounts localAccounts = new Accounts();
    Payees localPayees = new Payees();
    Payments localPayments = new Payments();
    if (!(paramObject instanceof PmtInfo)) {
      throw new Exception("bpwBean not PmtInfo object");
    }
    PmtInfo localPmtInfo = (PmtInfo)paramObject;
    if (localPmtInfo.payeeInfo == null) {
      throw new Exception("PayeeInfo is null");
    }
    localPayees.add(PayeeInfoToPayee(localPmtInfo.payeeInfo));
    Payment localPayment = (Payment)localPayments.createNoAdd();
    setPmtFromPmtInfo(localPayment, localPmtInfo, localPayees, localAccounts, false);
    return localPayment;
  }
  
  public static void setPaymentBatchFromPaymentBatchInfo(PaymentBatch paramPaymentBatch, PaymentBatchInfo paramPaymentBatchInfo, Payees paramPayees, Accounts paramAccounts, boolean paramBoolean)
  {
    paramPaymentBatch.setID(paramPaymentBatchInfo.getBatchID());
    paramPaymentBatch.setTemplateID(paramPaymentBatchInfo.getBatchID());
    paramPaymentBatch.setFIID(paramPaymentBatchInfo.getFIID());
    paramPaymentBatch.setCustomerID(paramPaymentBatchInfo.getCustomerId());
    paramPaymentBatch.setTemplateName(paramPaymentBatchInfo.getBatchName());
    paramPaymentBatch.setBatchType(paramPaymentBatchInfo.getBatchType());
    paramPaymentBatch.setPaymentCount(paramPaymentBatchInfo.getPaymentCount());
    String str1 = paramPaymentBatch.getDateFormat();
    paramPaymentBatch.setDateFormat("yyyyMMdd");
    String str2 = paramPaymentBatchInfo.getSubmitDate();
    if ((str2 != null) && (str2.length() > 8)) {
      str2 = str2.substring(0, 8);
    }
    paramPaymentBatch.setSubmitDate(str2);
    paramPaymentBatch.setDateFormat(str1);
    paramPaymentBatch.setStatus(paramPaymentBatchInfo.getBatchStatus());
    paramPaymentBatch.setTrackingID(paramPaymentBatchInfo.getLogId());
    paramPaymentBatch.setError(paramPaymentBatchInfo.getStatusCode());
    paramPaymentBatch.setAmount(new Currency(paramPaymentBatchInfo.getTotalAmount(), paramPaymentBatchInfo.getAmountCurrency(), paramPaymentBatch.getLocale()));
    paramPaymentBatch.setSubmittedBy(paramPaymentBatchInfo.getSubmittedBy());
    if ((paramPaymentBatchInfo.getPayments() != null) && (paramPaymentBatchInfo.getPayments().length > 0))
    {
      Payments localPayments = paramPaymentBatch.getPayments();
      if (localPayments == null)
      {
        localPayments = new Payments(paramPaymentBatch.getLocale());
        paramPaymentBatch.setPayments(localPayments);
      }
      PmtInfo[] arrayOfPmtInfo = paramPaymentBatchInfo.getPayments();
      for (int i = 0; i < arrayOfPmtInfo.length; i++)
      {
        PmtInfo localPmtInfo = arrayOfPmtInfo[i];
        Payment localPayment = (Payment)localPayments.getFirstByFilter("TRACKINGID=" + localPmtInfo.LogID);
        if (((localPmtInfo.Status != null) && (localPmtInfo.Status.equalsIgnoreCase("CANCELEDON"))) || ((localPmtInfo.action != null) && (localPmtInfo.action.equalsIgnoreCase("del"))) || ((localPayment != null) && (localPayment.getStatus() == 3)))
        {
          if (localPayment != null) {
            localPayments.remove(localPayment);
          }
        }
        else
        {
          if (localPayment == null) {
            localPayment = (Payment)localPayments.create();
          }
          setPmtFromPmtInfo(localPayment, localPmtInfo, paramPayees, paramAccounts, paramBoolean);
        }
      }
    }
  }
  
  public static void setPaymentBatchInfoFromPaymentBatch(PaymentBatchInfo paramPaymentBatchInfo, PaymentBatch paramPaymentBatch)
  {
    paramPaymentBatchInfo.setBatchID(paramPaymentBatch.getID());
    paramPaymentBatchInfo.setFIID(paramPaymentBatch.getFIID());
    paramPaymentBatchInfo.setCustomerId(paramPaymentBatch.getCustomerID());
    paramPaymentBatchInfo.setBatchName(paramPaymentBatch.getTemplateName());
    paramPaymentBatchInfo.setBatchType(paramPaymentBatch.getBatchType());
    paramPaymentBatchInfo.setPaymentCount(paramPaymentBatch.getPaymentCount());
    DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyyMMdd");
    String str = "";
    if (paramPaymentBatch.getSubmitDateValue() != null) {
      str = localDateFormat.format(paramPaymentBatch.getSubmitDateValue().getTime());
    }
    paramPaymentBatchInfo.setSubmitDate(str);
    paramPaymentBatchInfo.setBatchStatus(paramPaymentBatch.getStatus());
    paramPaymentBatchInfo.setLogId(paramPaymentBatch.getTrackingID());
    paramPaymentBatchInfo.setStatusCode(paramPaymentBatch.getErrorValue());
    if (paramPaymentBatch.getAmountValue() != null)
    {
      paramPaymentBatchInfo.setTotalAmount(paramPaymentBatch.getAmountValue().getCurrencyStringNoSymbolNoComma());
      paramPaymentBatchInfo.setAmountCurrency(paramPaymentBatch.getAmountValue().getCurrencyCode());
    }
    paramPaymentBatch.setLocale(paramPaymentBatch.getLocale());
    paramPaymentBatchInfo.setSubmittedBy(paramPaymentBatch.getSubmittedBy());
    if (paramPaymentBatch.getPayments() != null)
    {
      Payments localPayments = paramPaymentBatch.getPayments();
      PmtInfo[] arrayOfPmtInfo = new PmtInfo[localPayments.size()];
      paramPaymentBatchInfo.setPayments(arrayOfPmtInfo);
      for (int i = 0; i < localPayments.size(); i++)
      {
        PmtInfo localPmtInfo = new PmtInfo();
        arrayOfPmtInfo[i] = localPmtInfo;
        setPmtInfoFromPmt(localPmtInfo, (Payment)localPayments.get(i));
        if (paramPaymentBatch.getBatchType().equalsIgnoreCase("PAYMENT")) {
          localPmtInfo.setPaymentType("Current");
        }
      }
    }
  }
  
  public static void setPmtFromPmtInfo(Payment paramPayment, PmtInfo paramPmtInfo, Payees paramPayees, Accounts paramAccounts, boolean paramBoolean)
  {
    paramPayment.setID(paramPmtInfo.SrvrTID);
    paramPayment.setReferenceNumber(paramPmtInfo.SrvrTID);
    paramPayment.setTrackingID(paramPmtInfo.LogID);
    Currency localCurrency = new Currency(new BigDecimal(paramPmtInfo.getAmt()), paramPmtInfo.CurDef, paramPayment.getLocale());
    paramPayment.setAmount(localCurrency);
    paramPayment.setAmtCurrency(paramPmtInfo.CurDef);
    String str1 = paramPayment.getDateFormat();
    paramPayment.setDateFormat("yyyyMMdd");
    String str2 = String.valueOf(paramPmtInfo.StartDate);
    if ((str2 != null) && (str2.length() > 8))
    {
      paramPayment.put("BPW_DATE_FORMAT_EXTENSION", str2.substring(8));
      str2 = str2.substring(0, 8);
    }
    paramPayment.setPayDate(str2);
    paramPayment.setDateFormat(str1);
    if (paramPmtInfo.OriginatedDate != null) {
      try
      {
        paramPayment.put("IssueDate", new com.ffusion.beans.DateTime(paramPmtInfo.OriginatedDate, paramPayment.getLocale(), "yyyy/MM/dd HH:mm:ss"));
      }
      catch (Exception localException)
      {
        DebugLog.log(Level.INFO, "BeansConverter.setPmtFromPmtInfo: Invalid date format for IssueDate " + paramPmtInfo.OriginatedDate);
      }
    }
    String str3 = paramPmtInfo.AcctDebitType;
    int i = getBPWAccountType(str3);
    String str4 = paramPmtInfo.AcctDebitID;
    if (paramAccounts != null)
    {
      localObject = paramAccounts.getByAccountNumberAndType(str4, i);
      if ((localObject == null) || (!((Account)localObject).isFilterable("BillPay"))) {
        if (paramBoolean)
        {
          localObject = paramAccounts.createNoAdd("Restricted", 0);
          ((Account)localObject).setNickName("Account");
          paramPayment.setAccountEntitled("false");
        }
        else
        {
          localObject = paramAccounts.createNoAdd(str4, i);
          ((Account)localObject).setBankID(paramPmtInfo.BankID);
        }
      }
      paramPayment.setAccount((Account)localObject);
    }
    Object localObject = null;
    if (paramPayees != null) {
      localObject = paramPayees.getByHostID(paramPmtInfo.PayeeID);
    }
    paramPayment.setPayee((Payee)localObject);
    if ((localObject == null) && (paramPmtInfo.payeeInfo != null)) {
      paramPayment.setPayeeName(paramPmtInfo.payeeInfo.PayeeName);
    }
    paramPayment.setConfirmationNum(paramPmtInfo.ConfirmationNumber);
    paramPayment.setSubmittedBy(paramPmtInfo.submittedBy);
    paramPayment.setMemo(paramPmtInfo.Memo);
    paramPayment.setTemplateName(paramPmtInfo.paymentName);
    paramPayment.setTemplateID(paramPmtInfo.templateID);
    paramPayment.setPaymentType(paramPmtInfo.PaymentType);
    paramPayment.setFIID(paramPmtInfo.FIID);
    paramPayment.setCustomerID(paramPmtInfo.CustomerID);
    paramPayment.mapPmtStatusToInt(paramPmtInfo.Status);
    paramPayment.setRecPaymentID(paramPmtInfo.RecSrvrTID);
    if (((paramPayment instanceof RecPayment)) && ((paramPmtInfo instanceof RecPmtInfo)))
    {
      RecPayment localRecPayment = (RecPayment)paramPayment;
      RecPmtInfo localRecPmtInfo = (RecPmtInfo)paramPmtInfo;
      localRecPayment.setRecPaymentID(localRecPmtInfo.RecSrvrTID);
      if (paramPayment.getID() == null) {
        paramPayment.setID(localRecPmtInfo.RecSrvrTID);
      }
      localRecPayment.setFrequency(mapBPWFreqToFreq(localRecPmtInfo.getRecFrequencyValue()));
      if ((localRecPmtInfo.InstanceCount == 0) && (localRecPmtInfo.EndDate == -1)) {
        localRecPayment.setNumberPayments(999);
      } else {
        localRecPayment.setNumberPayments(localRecPmtInfo.InstanceCount);
      }
    }
    else
    {
      paramPayment.set("Frequency", "");
      paramPayment.set("NumberPayments", "");
    }
  }
  
  public static void xsetPmtFromPmtInfo(Payment paramPayment, PmtInfo paramPmtInfo, Payees paramPayees, Accounts paramAccounts, boolean paramBoolean)
  {
    setPmtFromPmtInfo(paramPayment, paramPmtInfo, paramPayees, paramAccounts, paramBoolean);
    if (((paramPayment instanceof RecPayment)) && ((paramPmtInfo instanceof RecPmtInfo)))
    {
      RecPayment localRecPayment = (RecPayment)paramPayment;
      RecPmtInfo localRecPmtInfo = (RecPmtInfo)paramPmtInfo;
      localRecPayment.setFrequency(mapBPWFreqToFreq(localRecPmtInfo.getRecFrequencyValue()));
    }
  }
  
  public static void setRecPmtFromRecPmtInfo(RecPayment paramRecPayment, RecPmtInfo paramRecPmtInfo, Payees paramPayees, Accounts paramAccounts, boolean paramBoolean)
  {
    setPmtFromPmtInfo(paramRecPayment, paramRecPmtInfo, paramPayees, paramAccounts, paramBoolean);
  }
  
  public static void setPmtInfoFromPmt(PmtInfo paramPmtInfo, Payment paramPayment)
  {
    paramPmtInfo.SrvrTID = paramPayment.getID();
    paramPmtInfo.LogID = paramPayment.getTrackingID();
    paramPmtInfo.setAmt(String.valueOf(paramPayment.getAmountValue().doubleValue()));
    paramPmtInfo.CurDef = paramPayment.getAmountValue().getCurrencyCode();
    DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyyMMdd");
    String str = "";
    if (paramPayment.getPayDateValue() != null)
    {
      str = localDateFormat.format(paramPayment.getPayDateValue().getTime());
      try
      {
        paramPmtInfo.StartDate = Integer.parseInt(str);
      }
      catch (NumberFormatException localNumberFormatException1) {}
    }
    paramPmtInfo.BankID = paramPayment.getAccount().getRoutingNum();
    paramPmtInfo.AcctDebitType = WireAccountMap.mapAccountTypeToStr(paramPayment.getAccount().getTypeValue());
    paramPmtInfo.AcctDebitID = paramPayment.getAccount().getNumber();
    paramPmtInfo.PayeeID = paramPayment.getPayee().getHostID();
    int i = 0;
    try
    {
      i = Integer.parseInt(paramPayment.getPayee().getID());
    }
    catch (NumberFormatException localNumberFormatException2) {}
    paramPmtInfo.PayeeListID = i;
    paramPmtInfo.payeeInfo = payeeToPayeeInfo(paramPayment.getPayee());
    paramPmtInfo.ConfirmationNumber = paramPayment.getConfirmationNum();
    paramPmtInfo.submittedBy = paramPayment.getSubmittedBy();
    paramPmtInfo.Memo = paramPayment.getMemo();
    paramPmtInfo.Status = Payment.mapPmtStatusToStr(paramPayment.getStatus());
    paramPmtInfo.RecSrvrTID = paramPayment.getRecPaymentID();
    paramPmtInfo.paymentName = paramPayment.getTemplateName();
    paramPmtInfo.templateID = paramPayment.getTemplateID();
    paramPmtInfo.PaymentType = paramPayment.getPaymentType();
    paramPmtInfo.FIID = paramPayment.getFIID();
    paramPmtInfo.CustomerID = paramPayment.getCustomerID();
    paramPmtInfo.action = paramPayment.getAction();
    paramPmtInfo.PayAcct = paramPayment.getPayee().getUserAccountNumber();
    if (((paramPayment instanceof RecPayment)) && ((paramPmtInfo instanceof RecPmtInfo)))
    {
      RecPayment localRecPayment = (RecPayment)paramPayment;
      RecPmtInfo localRecPmtInfo = (RecPmtInfo)paramPmtInfo;
      localRecPmtInfo.RecSrvrTID = localRecPayment.getRecPaymentID();
      localRecPmtInfo.InstanceCount = localRecPayment.getNumberPaymentsValue();
      localRecPmtInfo.setRecFrequency(mapFreqToBPWFreq(localRecPayment.getFrequencyValue()));
      localRecPmtInfo.setInitialAmount(String.valueOf(paramPayment.getAmountValue().doubleValue()));
      localRecPmtInfo.setFinalAmount(String.valueOf(paramPayment.getAmountValue().doubleValue()));
    }
  }
  
  public static Payee PayeeInfoToPayee(PayeeInfo paramPayeeInfo)
  {
    Payee localPayee = new Payee();
    localPayee.setID(paramPayeeInfo.PayeeID);
    localPayee.setFiID(paramPayeeInfo.FIID);
    HashMap localHashMap = new HashMap();
    localHashMap = paramPayeeInfo.PayeeNamesI18N;
    PayeeRoute localPayeeRoute = new PayeeRoute();
    if (paramPayeeInfo.PayeeRouteInfo != null)
    {
      localPayeeRoute.setCurrencyCode(paramPayeeInfo.PayeeRouteInfo.CurrencyCode);
      localPayeeRoute.setCustAcctRequired(paramPayeeInfo.PayeeRouteInfo.CustAcctRequired);
      localPayeeRoute.setRouteID(Integer.toString(paramPayeeInfo.PayeeRouteInfo.RouteID));
      localPayeeRoute.setAccountID(paramPayeeInfo.PayeeRouteInfo.AcctID);
      localPayeeRoute.setAcctType(paramPayeeInfo.PayeeRouteInfo.AcctType);
      localPayeeRoute.setBankIdentifier(paramPayeeInfo.PayeeRouteInfo.BankID);
    }
    localPayee.setPayeeRoute(localPayeeRoute);
    localPayee.setName(paramPayeeInfo.PayeeName);
    if (localHashMap != null)
    {
      Set localSet = localHashMap.keySet();
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        String str2 = (String)localHashMap.get(str1);
        localPayee.setName(str1, str2);
        localPayee.setSearchLanguage(str1);
      }
    }
    localPayee.setID(paramPayeeInfo.PayeeID);
    localPayee.setStreet(paramPayeeInfo.Addr1);
    localPayee.setStreet2(paramPayeeInfo.Addr2);
    localPayee.setStreet3(paramPayeeInfo.Addr3);
    localPayee.setCity(paramPayeeInfo.City);
    localPayee.setState(paramPayeeInfo.State);
    localPayee.setZipCode(paramPayeeInfo.Zipcode);
    localPayee.setCountry(paramPayeeInfo.Country);
    localPayee.setPhone(paramPayeeInfo.Phone);
    localPayee.setNickName(paramPayeeInfo.NickName);
    localPayee.setDaysToPay(paramPayeeInfo.DaysToPay);
    localPayee.setHostID(paramPayeeInfo.PayeeID);
    localPayee.set("IDScope", paramPayeeInfo.PayeeLevelType);
    localPayee.setStatus(2);
    localPayee.setContactName(paramPayeeInfo.ContactName);
    localPayee.setTrackingID(paramPayeeInfo.TranID);
    return localPayee;
  }
  
  public static PayeeInfo payeeToPayeeInfo(Payee paramPayee)
  {
    PayeeInfo localPayeeInfo = new PayeeInfo();
    PayeeRouteInfo localPayeeRouteInfo = new PayeeRouteInfo();
    localPayeeInfo.setPayeeRouteInfo(localPayeeRouteInfo);
    localPayeeInfo.PayeeRouteInfo = localPayeeInfo.getPayeeRouteInfo();
    localPayeeInfo.FIID = paramPayee.getFiID();
    if (paramPayee.getPayeeRoute() != null)
    {
      localPayeeInfo.PayeeRouteInfo.AcctID = paramPayee.getPayeeRoute().getAccountID();
      localPayeeInfo.PayeeRouteInfo.AcctType = paramPayee.getPayeeRoute().getAcctType();
      localPayeeInfo.PayeeRouteInfo.CurrencyCode = paramPayee.getPayeeRoute().getCurrencyCode();
      localPayeeInfo.PayeeRouteInfo.CustAcctRequired = paramPayee.getPayeeRoute().isCustAcctRequired();
      if (paramPayee.getPayeeRoute().getRouteID() != null) {
        localPayeeInfo.PayeeRouteInfo.RouteID = Integer.parseInt(paramPayee.getPayeeRoute().getRouteID());
      }
      localPayeeInfo.PayeeRouteInfo.BankID = paramPayee.getPayeeRoute().getBankIdentifier();
    }
    localPayeeInfo.PayeeNamesI18N = new HashMap();
    HashMap localHashMap = new HashMap();
    localHashMap = paramPayee.getPayeeI18NInfo();
    Set localSet = localHashMap.keySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      PayeeI18N localPayeeI18N = (PayeeI18N)localHashMap.get(str1);
      String str2 = localPayeeI18N.getName();
      localPayeeInfo.PayeeNamesI18N.put(str1, str2);
    }
    localPayeeInfo.PayeeName = paramPayee.getName("en_US");
    localPayeeInfo.PayeeID = paramPayee.getHostID();
    localPayeeInfo.Addr1 = paramPayee.getStreet();
    localPayeeInfo.Addr2 = paramPayee.getStreet2();
    localPayeeInfo.Addr3 = paramPayee.getStreet3();
    localPayeeInfo.City = paramPayee.getCity();
    localPayeeInfo.State = paramPayee.getState();
    localPayeeInfo.Zipcode = paramPayee.getZipCode();
    localPayeeInfo.Country = paramPayee.getCountry();
    localPayeeInfo.Phone = paramPayee.getPhone();
    localPayeeInfo.NickName = paramPayee.getNickName();
    localPayeeInfo.DaysToPay = paramPayee.getDaysToPayValue();
    localPayeeInfo.PayeeLevelType = ((String)paramPayee.get("IDScope"));
    localPayeeInfo.TranID = paramPayee.getTrackingID();
    localPayeeInfo.PayeeRouteInfo.PayeeID = paramPayee.getHostID();
    localPayeeInfo.ContactName = paramPayee.getContactName();
    return localPayeeInfo;
  }
  
  public static int getBPWAccountType(String paramString)
  {
    for (int i = 0; i < acctTypesStr.length; i++) {
      if (acctTypesStr[i].equalsIgnoreCase(paramString)) {
        return acctMap[i];
      }
    }
    return 0;
  }
  
  public static int getFrequency(EnumFreqEnum paramEnumFreqEnum)
  {
    for (int i = 0; i < FreqEnums.length; i++) {
      if (paramEnumFreqEnum.value() == FreqEnums[i].value()) {
        return FreqMap[i];
      }
    }
    if ((paramEnumFreqEnum.value() == 0) || (paramEnumFreqEnum.value() == 1)) {
      return 9;
    }
    DebugLog.log(Level.INFO, "NON-Standard getFrequency() EnumFreqEnum = " + paramEnumFreqEnum.value());
    return -1;
  }
  
  public static int mapFreqToBPWFreq(int paramInt)
  {
    for (int i = 0; i < FreqMap.length; i++) {
      if (paramInt == FreqMap[i]) {
        return BPWFreqMap[i];
      }
    }
    return -1;
  }
  
  public static int mapBPWFreqToFreq(int paramInt)
  {
    for (int i = 0; i < BPWFreqMap.length; i++) {
      if (paramInt == BPWFreqMap[i]) {
        return FreqMap[i];
      }
    }
    return -1;
  }
  
  private static Object jdMethod_if(Object paramObject, HashMap paramHashMap)
    throws Exception
  {
    if (!(paramObject instanceof CCEntryInfo)) {
      throw new Exception("bpwBean not CCEntryInfo object");
    }
    CCEntryInfo localCCEntryInfo = (CCEntryInfo)paramObject;
    CashCon localCashCon = null;
    CashCons localCashCons = new CashCons();
    if (localCCEntryInfo.getTransactionType() != null) {
      if (localCCEntryInfo.getTransactionType().equals("CONCENTRATION")) {
        localCashCon = (CashCon)localCashCons.createConcentrationNoAdd();
      } else if (localCCEntryInfo.getTransactionType().equals("DISBURSEMENT")) {
        localCashCon = (CashCon)localCashCons.createDisbursementNoAdd();
      }
    }
    setCashConFromCCEntryInfo(localCashCon, localCCEntryInfo, paramHashMap);
    return localCashCon;
  }
  
  public static void setCashConFromCCEntryInfo(CashCon paramCashCon, CCEntryInfo paramCCEntryInfo)
  {
    setCashConFromCCEntryInfo(paramCashCon, paramCCEntryInfo, null);
  }
  
  public static void setCashConFromCCEntryInfo(CashCon paramCashCon, CCEntryInfo paramCCEntryInfo, HashMap paramHashMap)
  {
    if ((paramCashCon == null) || (paramCCEntryInfo == null)) {
      return;
    }
    String str1 = getAmountAddDecimal(paramCCEntryInfo.getAmount());
    BigDecimal localBigDecimal = new BigDecimal(str1);
    paramCashCon.setAmount(localBigDecimal);
    paramCashCon.setLocationID(paramCCEntryInfo.getLocationId());
    paramCashCon.setSubmittedBy(paramCCEntryInfo.getSubmittedBy());
    paramCashCon.setID(paramCCEntryInfo.getEntryId());
    paramCashCon.bpwObject = paramCCEntryInfo;
    String str2 = paramCCEntryInfo.getDueDate();
    if ((str2 != null) && (str2.length() > 8)) {
      str2 = str2.substring(0, 8);
    }
    String str3 = paramCashCon.getDateFormat();
    paramCashCon.setDateFormat("yyyyMMdd");
    paramCashCon.setSubmitDate(str2);
    str2 = paramCCEntryInfo.getProcessedTime();
    paramCashCon.setDateFormat("yyyy/MM/dd HH:mm");
    paramCashCon.setProcessedOnDate(str2);
    paramCashCon.setDateFormat(str3);
    paramCashCon.setTrackingID(paramCCEntryInfo.getLogId());
    paramCashCon.setCompanyID(paramCCEntryInfo.getCompId());
    paramCashCon.setStatus(CashCon.mapBPWStatusToInt(paramCCEntryInfo.getStatus()));
    if (paramHashMap != null)
    {
      LocationSearchResults localLocationSearchResults = (LocationSearchResults)paramHashMap.get("LocationResults");
      if (localLocationSearchResults != null)
      {
        String str4 = paramCashCon.getLocationID();
        LocationSearchResult localLocationSearchResult = localLocationSearchResults.getByBPWID(str4);
        if (localLocationSearchResult != null)
        {
          paramCashCon.setDivisionID("" + localLocationSearchResult.getDivisionID());
          paramCashCon.setDivisionName((String)localLocationSearchResult.get("DivisionName"));
          paramCashCon.setLocationName(localLocationSearchResult.getLocationName());
          paramCashCon.setCompanyID(localLocationSearchResult.getCompID());
        }
      }
    }
  }
  
  private static Object jdMethod_do(Object paramObject, HashMap paramHashMap)
    throws Exception
  {
    return null;
  }
  
  private static Object jdMethod_int(Object paramObject, HashMap paramHashMap)
    throws Exception
  {
    String str = "BeansConverter.efsToBpwTransferBean";
    if (paramObject == null) {
      throw new Exception("BeansConverter.efsToBpwTransferBeanInvalid EFS Bean passed");
    }
    Transfer localTransfer = (Transfer)paramObject;
    return localTransfer.getTransferInfo();
  }
  
  private static Object jdMethod_goto(Object paramObject, HashMap paramHashMap)
    throws Exception
  {
    String str = "BeansConverter.bpwToEfsTransferBean";
    if (paramObject == null) {
      throw new Exception("BeansConverter.bpwToEfsTransferBeanInvalid BPW Bean passed");
    }
    Transfer localTransfer = new Transfer();
    localTransfer.setTransferInfo((TransferInfo)paramObject, null);
    return localTransfer;
  }
  
  public static ExtTransferCompany getExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws Exception
  {
    String str = "GetExtTransferCompany";
    if (paramExtTransferCompanyInfo == null) {
      throw new Exception("GetExtTransferCompanyInvalid BPW Bean passed");
    }
    ExtTransferCompany localExtTransferCompany = new ExtTransferCompany();
    localExtTransferCompany.setBpwID(paramExtTransferCompanyInfo.getCompId());
    localExtTransferCompany.setCustID(paramExtTransferCompanyInfo.getCustomerId());
    localExtTransferCompany.setCompanyName(paramExtTransferCompanyInfo.getCompName());
    localExtTransferCompany.setCompanyID(paramExtTransferCompanyInfo.getCompACHId());
    localExtTransferCompany.setBatchDescription(paramExtTransferCompanyInfo.getCompDescription());
    return localExtTransferCompany;
  }
  
  public static ExtTransferCompanyInfo getExtTransferCompanyInfo(ExtTransferCompany paramExtTransferCompany)
    throws Exception
  {
    String str = "GetExtTransferCompanyInfo";
    if (paramExtTransferCompany == null) {
      throw new Exception("GetExtTransferCompanyInfoInvalid EFS Bean passed");
    }
    ExtTransferCompanyInfo localExtTransferCompanyInfo = new ExtTransferCompanyInfo();
    localExtTransferCompanyInfo.setCompId(paramExtTransferCompany.getBpwID());
    localExtTransferCompanyInfo.setCompACHId(paramExtTransferCompany.getCompanyID());
    localExtTransferCompanyInfo.setCustomerId(paramExtTransferCompany.getCustID());
    localExtTransferCompanyInfo.setCompName(paramExtTransferCompany.getCompanyName());
    localExtTransferCompanyInfo.setCompDescription(paramExtTransferCompany.getBatchDescription());
    return localExtTransferCompanyInfo;
  }
  
  public static ExtTransferCompanies getExtTransferCompanies(ExtTransferCompanyList paramExtTransferCompanyList)
    throws Exception
  {
    String str1 = "GetExtTransferCompanies";
    if (paramExtTransferCompanyList == null) {
      throw new Exception("GetExtTransferCompaniesInvalid BPW Bean passed");
    }
    ExtTransferCompanyInfo[] arrayOfExtTransferCompanyInfo = paramExtTransferCompanyList.getExtTransferCompanys();
    ExtTransferCompany localExtTransferCompany = new ExtTransferCompany();
    ExtTransferCompanies localExtTransferCompanies = new ExtTransferCompanies();
    for (int i = 0; i < arrayOfExtTransferCompanyInfo.length; i++)
    {
      String str2 = arrayOfExtTransferCompanyInfo[i].getCustomerId();
      if ((str2 != null) && (str2.trim().length() > 0))
      {
        localExtTransferCompany = getExtTransferCompany(arrayOfExtTransferCompanyInfo[i]);
        localExtTransferCompanies.add(localExtTransferCompany);
      }
    }
    return localExtTransferCompanies;
  }
  
  public static ExtTransferAccount getExtTransferAccount(ExtTransferAccount paramExtTransferAccount, ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws Exception
  {
    String str1 = "GetExtTransferAccount";
    if (paramExtTransferAcctInfo == null) {
      throw new Exception("GetExtTransferAccountInvalid BPW Bean passed");
    }
    paramExtTransferAccount.setBpwID(paramExtTransferAcctInfo.getAcctId());
    paramExtTransferAccount.setNumber(paramExtTransferAcctInfo.getAcctNum());
    paramExtTransferAccount.setAcctBankIDType(paramExtTransferAcctInfo.getBankRtnType());
    paramExtTransferAccount.setRoutingNumber(paramExtTransferAcctInfo.getAcctBankRtn());
    paramExtTransferAccount.setCurrencyCode(paramExtTransferAcctInfo.getCurrencyCode());
    paramExtTransferAccount.setRecipientName(paramExtTransferAcctInfo.getRecipientName());
    paramExtTransferAccount.setNickname(paramExtTransferAcctInfo.getNickName());
    paramExtTransferAccount.setAcctCategory(paramExtTransferAcctInfo.getAcctCategory());
    paramExtTransferAccount.setRecipientId(paramExtTransferAcctInfo.getRecipientId());
    String str2 = paramExtTransferAcctInfo.getPrenote();
    paramExtTransferAccount.setPrenote((str2 != null) && (str2.equalsIgnoreCase("Y")));
    if ("PERSONAL".equalsIgnoreCase(paramExtTransferAcctInfo.getRecipientType())) {
      paramExtTransferAccount.setRecipientType(ExtTransferAccount.RECIPIENT_TYPE_PERSONAL);
    } else if ("BUSINESS".equalsIgnoreCase(paramExtTransferAcctInfo.getRecipientType())) {
      paramExtTransferAccount.setRecipientType(ExtTransferAccount.RECIPIENT_TYPE_BUSINESS);
    }
    paramExtTransferAccount.setType(getEfsExtTransferAcctType(paramExtTransferAcctInfo.getAcctType()));
    if ("ACTIVE".equals(paramExtTransferAcctInfo.getStatus())) {
      paramExtTransferAccount.setStatusValue(1);
    }
    if ("INACTIVE".equals(paramExtTransferAcctInfo.getStatus())) {
      paramExtTransferAccount.setStatusValue(2);
    }
    if ("CANCELEDON".equals(paramExtTransferAcctInfo.getStatus())) {
      paramExtTransferAccount.setStatusValue(3);
    }
    try
    {
      com.ffusion.beans.DateTime localDateTime1 = new com.ffusion.beans.DateTime();
      localDateTime1.setFormat("yyyy/MM/dd HH:mm:ss");
      localDateTime1.fromString(paramExtTransferAcctInfo.getDepositDate());
      paramExtTransferAccount.setDepositDateValue(localDateTime1);
    }
    catch (Exception localException1) {}
    paramExtTransferAccount.setVerifyFailedCountValue(paramExtTransferAcctInfo.getVerifyFailedCount());
    paramExtTransferAccount.setPrimaryAcctHolder(paramExtTransferAcctInfo.getPrimaryAcctHolder());
    paramExtTransferAccount.setSecondaryAcctHolder(paramExtTransferAcctInfo.getSecondaryAcctHolder());
    paramExtTransferAccount.setCheckNumber(paramExtTransferAcctInfo.getCheckNumber());
    try
    {
      com.ffusion.beans.DateTime localDateTime2 = new com.ffusion.beans.DateTime();
      localDateTime2.setFormat("yyyy/MM/dd HH:mm:ss");
      localDateTime2.fromString(paramExtTransferAcctInfo.getCreateDate());
      paramExtTransferAccount.setCreateDateValue(localDateTime2);
    }
    catch (Exception localException2) {}
    if (0 == paramExtTransferAcctInfo.getVerifyStatus()) {
      paramExtTransferAccount.setVerifyStatusValue(0);
    }
    if (3 == paramExtTransferAcctInfo.getVerifyStatus()) {
      paramExtTransferAccount.setVerifyStatusValue(3);
    }
    if (2 == paramExtTransferAcctInfo.getVerifyStatus()) {
      paramExtTransferAccount.setVerifyStatusValue(2);
    }
    if (1 == paramExtTransferAcctInfo.getVerifyStatus()) {
      paramExtTransferAccount.setVerifyStatusValue(1);
    }
    if (4 == paramExtTransferAcctInfo.getVerifyStatus()) {
      paramExtTransferAccount.setVerifyStatusValue(4);
    }
    if (5 == paramExtTransferAcctInfo.getVerifyStatus()) {
      paramExtTransferAccount.setVerifyStatusValue(5);
    }
    return paramExtTransferAccount;
  }
  
  public static int getEfsExtTransferAcctType(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return 0;
    }
    if ("Savings".equalsIgnoreCase(paramString)) {
      return 2;
    }
    if ("Checking".equalsIgnoreCase(paramString)) {
      return 1;
    }
    if ("CREDIT".equalsIgnoreCase(paramString)) {
      return 3;
    }
    if ("CREDITLINE".equalsIgnoreCase(paramString)) {
      return 7;
    }
    if ("LOAN".equalsIgnoreCase(paramString)) {
      return 4;
    }
    if ("MORTGAGE".equalsIgnoreCase(paramString)) {
      return 5;
    }
    if ("HOMEEQUITY".equalsIgnoreCase(paramString)) {
      return 6;
    }
    if ("CD".equalsIgnoreCase(paramString)) {
      return 8;
    }
    if ("IRA".equalsIgnoreCase(paramString)) {
      return 9;
    }
    if ("STOCKBOND".equalsIgnoreCase(paramString)) {
      return 10;
    }
    if ("BROKERAGE".equalsIgnoreCase(paramString)) {
      return 11;
    }
    if ("MONEYMRKT".equalsIgnoreCase(paramString)) {
      return 12;
    }
    if ("BUSINESSLOAN".equalsIgnoreCase(paramString)) {
      return 13;
    }
    if ("FIXEDDEPOSIT".equalsIgnoreCase(paramString)) {
      return 14;
    }
    if ("OTHER".equalsIgnoreCase(paramString)) {
      return 15;
    }
    if ("LEDGER".equalsIgnoreCase(paramString)) {
      return 16;
    }
    return 0;
  }
  
  public static String getACHAcctTypeFromEFS(int paramInt)
  {
    if (paramInt == 1) {
      return "Checking";
    }
    if (paramInt == 2) {
      return "Savings";
    }
    if (paramInt == 3) {
      return "CREDIT";
    }
    if (paramInt == 7) {
      return "CREDITLINE";
    }
    if (paramInt == 4) {
      return "LOAN";
    }
    if (paramInt == 5) {
      return "MORTGAGE";
    }
    if (paramInt == 6) {
      return "HOMEEQUITY";
    }
    if (paramInt == 8) {
      return "CD";
    }
    if (paramInt == 9) {
      return "IRA";
    }
    if (paramInt == 10) {
      return "STOCKBOND";
    }
    if (paramInt == 11) {
      return "BROKERAGE";
    }
    if (paramInt == 12) {
      return "MONEYMRKT";
    }
    if (paramInt == 13) {
      return "BUSINESSLOAN";
    }
    if (paramInt == 14) {
      return "FIXEDDEPOSIT";
    }
    if (paramInt == 15) {
      return "OTHER";
    }
    if (paramInt == 16) {
      return "LEDGER";
    }
    return "UNKNOWN";
  }
  
  public static ExtTransferAcctInfo getExtTransferAccountInfo(ExtTransferAccount paramExtTransferAccount, ExtTransferCompany paramExtTransferCompany)
    throws Exception
  {
    String str = "GetExtTransferAccountInfo";
    if (paramExtTransferAccount == null) {
      throw new Exception("GetExtTransferAccountInfoInvalid BPW Bean passed");
    }
    ExtTransferAcctInfo localExtTransferAcctInfo = new ExtTransferAcctInfo();
    localExtTransferAcctInfo.setAcctId(paramExtTransferAccount.getBpwID());
    localExtTransferAcctInfo.setRecipientName(paramExtTransferAccount.getRecipientName());
    localExtTransferAcctInfo.setRecipientId(paramExtTransferAccount.getRecipientId());
    if (paramExtTransferAccount.getRecipientTypeValue() == ExtTransferAccount.RECIPIENT_TYPE_VALUE_PERSONAL) {
      localExtTransferAcctInfo.setRecipientType("PERSONAL");
    } else if (paramExtTransferAccount.getRecipientTypeValue() == ExtTransferAccount.RECIPIENT_TYPE_VALUE_BUSINESS) {
      localExtTransferAcctInfo.setRecipientType("BUSINESS");
    }
    localExtTransferAcctInfo.setAcctType(getACHAcctTypeFromEFS(paramExtTransferAccount.getTypeValue()));
    localExtTransferAcctInfo.setAcctNum(paramExtTransferAccount.getNumber());
    localExtTransferAcctInfo.setAcctBankRtn(paramExtTransferAccount.getRoutingNumber());
    localExtTransferAcctInfo.setBankRtnType(paramExtTransferAccount.getAcctBankIDType());
    localExtTransferAcctInfo.setCurrencyCode(paramExtTransferAccount.getCurrencyCode());
    localExtTransferAcctInfo.setPrenote(paramExtTransferAccount.getPrenoteValue() ? "Y" : "N");
    localExtTransferAcctInfo.setCustomerId(paramExtTransferCompany.getCustID());
    localExtTransferAcctInfo.setNickName(paramExtTransferAccount.getNickname());
    localExtTransferAcctInfo.setAcctCategory(paramExtTransferAccount.getAcctCategory());
    if (paramExtTransferAccount.getStatusValue() == 1) {
      localExtTransferAcctInfo.setStatus("ACTIVE");
    }
    if (paramExtTransferAccount.getStatusValue() == 2) {
      localExtTransferAcctInfo.setStatus("INACTIVE");
    }
    if (paramExtTransferAccount.getStatusValue() == 3) {
      localExtTransferAcctInfo.setStatus("CANCELEDON");
    }
    if (paramExtTransferAccount.getVerifyStatusValue() == 0) {
      localExtTransferAcctInfo.setVerifyStatus(0);
    }
    if (paramExtTransferAccount.getVerifyStatusValue() == 1) {
      localExtTransferAcctInfo.setVerifyStatus(1);
    }
    if (paramExtTransferAccount.getVerifyStatusValue() == 2) {
      localExtTransferAcctInfo.setVerifyStatus(2);
    }
    if (paramExtTransferAccount.getVerifyStatusValue() == 3) {
      localExtTransferAcctInfo.setVerifyStatus(3);
    }
    if (paramExtTransferAccount.getVerifyStatusValue() == 4) {
      localExtTransferAcctInfo.setVerifyStatus(4);
    }
    if (paramExtTransferAccount.getVerifyStatusValue() == 5) {
      localExtTransferAcctInfo.setVerifyStatus(5);
    }
    localExtTransferAcctInfo.setVerifyFailedCount(paramExtTransferAccount.getVerifyFailedCountValue());
    localExtTransferAcctInfo.setPrimaryAcctHolder(paramExtTransferAccount.getPrimaryAcctHolder());
    localExtTransferAcctInfo.setSecondaryAcctHolder(paramExtTransferAccount.getSecondaryAcctHolder());
    localExtTransferAcctInfo.setCheckNumber(paramExtTransferAccount.getCheckNumber());
    return localExtTransferAcctInfo;
  }
  
  public static ExtTransferAccounts getExtTransferAccounts(ExtTransferAcctList paramExtTransferAcctList, Locale paramLocale)
    throws Exception
  {
    String str1 = "GetExtTransferAccounts";
    if (paramExtTransferAcctList == null) {
      throw new Exception("GetExtTransferAccountsInvalid BPW Bean passed");
    }
    ExtTransferAcctInfo[] arrayOfExtTransferAcctInfo = paramExtTransferAcctList.getExtTransferAccts();
    ExtTransferAccounts localExtTransferAccounts = new ExtTransferAccounts(paramLocale);
    for (int i = 0; i < arrayOfExtTransferAcctInfo.length; i++)
    {
      String str2 = arrayOfExtTransferAcctInfo[i].getAcctId();
      if ((str2 != null) && (str2.trim().length() > 0))
      {
        ExtTransferAccount localExtTransferAccount = new ExtTransferAccount();
        localExtTransferAccount = getExtTransferAccount(localExtTransferAccount, arrayOfExtTransferAcctInfo[i]);
        localExtTransferAccounts.add(localExtTransferAccount);
      }
    }
    return localExtTransferAccounts;
  }
  
  private static Object jdMethod_byte(Object paramObject, HashMap paramHashMap)
  {
    if (paramObject == null) {
      return null;
    }
    CCLocationInfo localCCLocationInfo = (CCLocationInfo)paramObject;
    Locale localLocale = LocaleUtil.getDefaultLocale();
    Location localLocation = new Location(localLocale);
    String str;
    try
    {
      CashConCompanies localCashConCompanies = (CashConCompanies)paramHashMap.get("CashConCompanies");
      CashConCompany localCashConCompany = localCashConCompanies.getByID(localCCLocationInfo.getCompId());
      CashConAccounts localCashConAccounts = localCashConCompany.getConcAccounts();
      CashConAccount localCashConAccount = localCashConAccounts.getByID(localCCLocationInfo.getConcentrateAcctId());
      str = localCashConAccount.getCurrency();
    }
    catch (Exception localException)
    {
      str = "USD";
    }
    localLocation.setLocationName(localCCLocationInfo.getLocationName());
    localLocation.setLocationBPWID(localCCLocationInfo.getLocationId());
    localLocation.setLocationID(localCCLocationInfo.getCCLocationId());
    localLocation.setActive(localCCLocationInfo.getStatus().equals("ACTIVE"));
    localLocation.setLocalRoutingNumber(localCCLocationInfo.getBankRtn());
    localLocation.setLocalBankName(localCCLocationInfo.getBankName());
    localLocation.setLocalAccountNumber(localCCLocationInfo.getAccountNum());
    localLocation.setLocalAccountType(getEfsExtTransferAcctType(localCCLocationInfo.getAccountType()));
    localLocation.setCashConCompanyBPWID(localCCLocationInfo.getCompId());
    localLocation.setConcAccountBPWID(localCCLocationInfo.getConcentrateAcctId());
    localLocation.setDisbAccountBPWID(localCCLocationInfo.getDisburseAcctId());
    if ((localCCLocationInfo.getDepositMin() != null) && (!localCCLocationInfo.getDepositMin().equals("null"))) {
      localLocation.setDepositMinimum(a(localCCLocationInfo.getDepositMin(), str, localLocale));
    }
    if ((localCCLocationInfo.getDepositMax() != null) && (!localCCLocationInfo.getDepositMax().equals("null"))) {
      localLocation.setDepositMaximum(a(localCCLocationInfo.getDepositMax(), str, localLocale));
    }
    if (localCCLocationInfo.getAnticipatoryDepos() != null) {
      localLocation.setAnticDeposit(a(localCCLocationInfo.getAnticipatoryDepos(), str, localLocale));
    }
    if (localCCLocationInfo.getThresholdDeposAmt() != null) {
      localLocation.setThreshDeposit(a(localCCLocationInfo.getThresholdDeposAmt(), str, localLocale));
    }
    if (localCCLocationInfo.getConsolidateDepos() != null) {
      localLocation.setConsolidateDeposits(localCCLocationInfo.getConsolidateDepos().equalsIgnoreCase("Y"));
    } else {
      localLocation.setConsolidateDeposits(false);
    }
    if (localCCLocationInfo.getDepositPrenote() != null) {
      localLocation.setDepositPrenote(localCCLocationInfo.getDepositPrenote().equalsIgnoreCase("Y"));
    } else {
      localLocation.setDepositPrenote(false);
    }
    if (localCCLocationInfo.getDisbursePrenote() != null) {
      localLocation.setDisbursementPrenote(localCCLocationInfo.getDisbursePrenote().equalsIgnoreCase("Y"));
    } else {
      localLocation.setDisbursementPrenote(false);
    }
    localLocation.setSubmittedBy(localCCLocationInfo.getSubmittedBy());
    localLocation.setLogId(localCCLocationInfo.getLogId());
    localLocation.setDisPrenoteStatus(localCCLocationInfo.getDisPrenoteStatus());
    localLocation.setDepPrenoteStatus(localCCLocationInfo.getDepPrenoteStatus());
    return localLocation;
  }
  
  private static Object jdMethod_char(Object paramObject, HashMap paramHashMap)
  {
    if (paramObject == null) {
      return null;
    }
    Location localLocation = (Location)paramObject;
    CCLocationInfo localCCLocationInfo = new CCLocationInfo();
    localCCLocationInfo.setLocationName(localLocation.getLocationName());
    localCCLocationInfo.setLocationId(localLocation.getLocationBPWID());
    localCCLocationInfo.setCCLocationId(localLocation.getLocationID());
    localCCLocationInfo.setStatus(localLocation.isActive() ? "ACTIVE" : "INACTIVE");
    localCCLocationInfo.setBankRtn(localLocation.getLocalRoutingNumber());
    localCCLocationInfo.setBankName(localLocation.getLocalBankName());
    localCCLocationInfo.setAccountNum(localLocation.getLocalAccountNumber());
    localCCLocationInfo.setAccountType(getACHAcctTypeFromEFS(localLocation.getLocalAccountType()));
    localCCLocationInfo.setCompId(localLocation.getCashConCompanyBPWID());
    localCCLocationInfo.setConcentrateAcctId(localLocation.getConcAccountBPWID());
    localCCLocationInfo.setDisburseAcctId(localLocation.getDisbAccountBPWID());
    if (localLocation.getDepositMinimum() != null) {
      localCCLocationInfo.setDepositMin(a(localLocation.getDepositMinimum()));
    }
    if (localLocation.getDepositMaximum() != null) {
      localCCLocationInfo.setDepositMax(a(localLocation.getDepositMaximum()));
    }
    if (localLocation.getAnticDeposit() != null) {
      localCCLocationInfo.setAnticipatoryDepos(a(localLocation.getAnticDeposit()));
    }
    if (localLocation.getThreshDeposit() != null) {
      localCCLocationInfo.setThresholdDeposAmt(a(localLocation.getThreshDeposit()));
    }
    localCCLocationInfo.setConsolidateDepos(localLocation.getConsolidateDeposits() ? "Y" : "N");
    localCCLocationInfo.setDepositPrenote(localLocation.getDepositPrenote() ? "Y" : "N");
    localCCLocationInfo.setDisbursePrenote(localLocation.getDisbursementPrenote() ? "Y" : "N");
    localCCLocationInfo.setSubmittedBy(localLocation.getSubmittedBy());
    localCCLocationInfo.setLogId(localLocation.getLogId());
    localCCLocationInfo.setDisPrenoteStatus(localLocation.getDisPrenoteStatus());
    localCCLocationInfo.setDepPrenoteStatus(localLocation.getDepPrenoteStatus());
    return localCCLocationInfo;
  }
  
  private static Currency a(String paramString1, String paramString2, Locale paramLocale)
  {
    BigDecimal localBigDecimal = new BigDecimal(paramString1);
    localBigDecimal = localBigDecimal.movePointLeft(2);
    Currency localCurrency = new Currency(localBigDecimal, paramString2, paramLocale);
    return localCurrency;
  }
  
  private static String a(Currency paramCurrency)
  {
    BigInteger localBigInteger = paramCurrency.getAmountValue().movePointRight(2).toBigInteger();
    String str = localBigInteger.toString();
    return str;
  }
  
  private static Object jdMethod_case(Object paramObject, HashMap paramHashMap)
  {
    if (paramObject == null) {
      return null;
    }
    com.ffusion.ffs.bpw.interfaces.BankingDays localBankingDays = (com.ffusion.ffs.bpw.interfaces.BankingDays)paramObject;
    com.ffusion.beans.banking.BankingDays localBankingDays1 = new com.ffusion.beans.banking.BankingDays(localBankingDays.getFiID(), localBankingDays.getCustomerID(), localBankingDays.getTransType(), localBankingDays.getCompId(), localBankingDays.getSec(), localBankingDays.getStartDate(), localBankingDays.getEndDate(), localBankingDays.getBankingDays());
    return localBankingDays1;
  }
  
  private static Object a(Object paramObject, HashMap paramHashMap)
  {
    if (paramObject == null) {
      return null;
    }
    com.ffusion.beans.banking.BankingDays localBankingDays = (com.ffusion.beans.banking.BankingDays)paramObject;
    com.ffusion.ffs.bpw.interfaces.BankingDays localBankingDays1 = new com.ffusion.ffs.bpw.interfaces.BankingDays(localBankingDays.getFiID(), localBankingDays.getCustomerID(), localBankingDays.getTransType(), localBankingDays.getCompId(), localBankingDays.getSec(), localBankingDays.getStartDate(), localBankingDays.getEndDate(), localBankingDays.getBankingDays());
    return localBankingDays1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.BeansConverter
 * JD-Core Version:    0.7.0.1
 */