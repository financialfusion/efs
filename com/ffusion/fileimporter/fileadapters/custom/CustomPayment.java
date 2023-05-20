package com.ffusion.fileimporter.fileadapters.custom;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.beans.fileimporter.ImportError;
import com.ffusion.beans.fileimporter.MappingDefinition;
import com.ffusion.beans.fileimporter.Record;
import com.ffusion.beans.fileimporter.Records;
import com.ffusion.csil.FIException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class CustomPayment
  extends Mapper
{
  public void process(ErrorMessages paramErrorMessages, Records paramRecords, HashMap paramHashMap)
    throws FIException
  {
    if (paramHashMap == null) {
      throw new FIException(getClass().getName(), 2, "Extra hash map is null");
    }
    MappingDefinition localMappingDefinition = (MappingDefinition)paramHashMap.get("MappingDefinition");
    if (localMappingDefinition == null) {
      throw new FIException(getClass().getName(), 2, "Mapping definition not found");
    }
    String str1 = localMappingDefinition.getOutputFormatName();
    if (str1 == null) {
      throw new FIException(getClass().getName(), 2, "Output format not found in mapping definition");
    }
    SecureUser localSecureUser = (SecureUser)paramHashMap.get("USER");
    Locale localLocale = Locale.US;
    if ((localSecureUser != null) && (localSecureUser.getLocale() != null)) {
      localLocale = localSecureUser.getLocale();
    }
    Accounts localAccounts = (Accounts)paramHashMap.get("BillPayAccounts");
    if (localAccounts == null) {
      throw new FIException(getClass().getName(), 2, "Accounts not found in Extra hash map");
    }
    Payees localPayees = (Payees)paramHashMap.get("Payees");
    if (localPayees == null) {
      throw new FIException(getClass().getName(), 2, "Payees not found in Extra hash map");
    }
    long l = System.currentTimeMillis();
    Payments localPayments = new Payments(localLocale);
    Iterator localIterator = paramRecords.iterator();
    for (int i = 1; localIterator.hasNext(); i++)
    {
      Record localRecord = (Record)localIterator.next();
      Payment localPayment = (Payment)localPayments.createNoAdd();
      localPayment.setRecordNumber(localRecord.getRecordNumber());
      localPayment.setLineNumber(localRecord.getLineNumber());
      localPayment.setLineContent(localRecord.getLineContent());
      localPayment.setID(String.valueOf(l + i));
      String str2 = getStringAttribute(paramErrorMessages, localRecord, "Payee Name", true, localLocale);
      Currency localCurrency = getCurrencyAttribute(paramErrorMessages, localRecord, "Amount", localMappingDefinition.getMoneyFormatType(), true, localLocale);
      DateTime localDateTime = getDateTimeAttribute(paramErrorMessages, localRecord, "Due Date", localMappingDefinition.getDateFormat(), true, localLocale);
      String str3 = getStringAttribute(paramErrorMessages, localRecord, "Memo", false, localLocale);
      String str4 = getStringAttribute(paramErrorMessages, localRecord, "Amount Currency", false, localLocale);
      localPayment.setPayeeName(str2);
      Payee localPayee = localPayees.getByName(str2);
      int j = 1;
      if (localPayee == null)
      {
        paramErrorMessages.add(new ImportError(3, getImportError("InvalidData", localLocale), getImportError("PayeeNotFound", localLocale), localRecord.getLineContent(), localRecord.getLineNumber(), localRecord.getRecordNumber()));
        j = 0;
      }
      else
      {
        localPayment.setPayee(localPayee);
      }
      if (localDateTime != null)
      {
        localDateTime.setFormat("SHORT");
        localPayment.setPayDate(localDateTime);
      }
      localPayment.setAmount(localCurrency);
      if (str4 != null) {
        localPayment.setAmtCurrency(str4);
      }
      if (str3 != null) {
        localPayment.setMemo(str3);
      }
      if (j != 0) {
        localPayments.add(localPayment);
      }
    }
    if (localPayments.size() == 0)
    {
      paramErrorMessages.add(new ImportError(3, getImportError("NoPayments", localLocale), paramErrorMessages.isEmpty() ? getImportError("FileDoesNotContainPayments", localLocale) : getImportError("NoValidPaymentsFound", localLocale), null, null, null));
      paramErrorMessages.setOperationCanContinue(false);
    }
    paramHashMap.put("ImportPayments", localPayments);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.custom.CustomPayment
 * JD-Core Version:    0.7.0.1
 */