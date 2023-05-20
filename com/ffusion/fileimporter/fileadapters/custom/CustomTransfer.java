package com.ffusion.fileimporter.fileadapters.custom;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.beans.fileimporter.ImportError;
import com.ffusion.beans.fileimporter.MappingDefinition;
import com.ffusion.beans.fileimporter.Record;
import com.ffusion.beans.fileimporter.Records;
import com.ffusion.csil.FIException;
import com.ffusion.util.RoutingNumberUtil;
import com.ffusion.util.enums.UserAssignedAmount;
import com.ffusion.util.settings.AccountSettings;
import com.ffusion.util.settings.SystemException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class CustomTransfer
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
    Accounts localAccounts = (Accounts)paramHashMap.get("TransferAccounts");
    if (localAccounts == null) {
      throw new FIException(getClass().getName(), 2, "Accounts not found in Extra hash map");
    }
    long l = System.currentTimeMillis();
    Transfers localTransfers = new Transfers(localLocale);
    localAccounts.setFilter("All");
    Iterator localIterator = paramRecords.iterator();
    for (int i = 1; localIterator.hasNext(); i++)
    {
      Record localRecord = (Record)localIterator.next();
      Transfer localTransfer = new Transfer();
      localTransfer.setID(String.valueOf(l + i));
      localTransfer.setRecordNumber(localRecord.getRecordNumber());
      localTransfer.setLineNumber(localRecord.getLineNumber());
      localTransfer.setLineContent(localRecord.getLineContent());
      String str2 = getStringAttribute(paramErrorMessages, localRecord, "Source Routing Number", true, localLocale);
      String str3 = getStringAttribute(paramErrorMessages, localRecord, "Source Account Number", true, localLocale);
      Integer localInteger1 = getIntegerACHAccountType(paramErrorMessages, localRecord, "Source Account Type", true, localLocale);
      String str4 = getStringAttribute(paramErrorMessages, localRecord, "Destination Routing Number", true, localLocale);
      String str5 = getStringAttribute(paramErrorMessages, localRecord, "Destination Account Number", true, localLocale);
      Integer localInteger2 = getIntegerACHAccountType(paramErrorMessages, localRecord, "Destination Account Type", true, localLocale);
      Currency localCurrency = getCurrencyAttribute(paramErrorMessages, localRecord, "Amount", localMappingDefinition.getMoneyFormatType(), true, localLocale);
      DateTime localDateTime = getDateTimeAttribute(paramErrorMessages, localRecord, "Due Date", localMappingDefinition.getDateFormat(), false, localLocale);
      String str6 = getStringAttribute(paramErrorMessages, localRecord, "Memo", false, localLocale);
      String str7 = getStringAttribute(paramErrorMessages, localRecord, "Amount Currency", false, localLocale);
      if (localDateTime != null)
      {
        localDateTime.setFormat("SHORT");
        localTransfer.setDate(localDateTime);
      }
      localTransfers.add(localTransfer);
      if (!RoutingNumberUtil.isValidRoutingNumber(str2)) {
        paramErrorMessages.add(new ImportError(3, getImportError("InvalidData", localLocale), getImportError("SourceRoutingNumberNotValid", localLocale), localRecord.getLineContent(), localRecord.getLineNumber(), localRecord.getRecordNumber()));
      }
      if (!RoutingNumberUtil.isValidRoutingNumber(str4)) {
        paramErrorMessages.add(new ImportError(3, getImportError("InvalidData", localLocale), getImportError("DestinationRoutingNumberNotValid", localLocale), localRecord.getLineContent(), localRecord.getLineNumber(), localRecord.getRecordNumber()));
      }
      if (localInteger1 != null)
      {
        try
        {
          localTransfer.setFromAccountID(AccountSettings.buildAccountId(str3, localInteger1.toString()));
        }
        catch (SystemException localSystemException1)
        {
          throw new FIException(getClass().getName(), 2, "System settings have not been initialized.", localSystemException1);
        }
        localTransfer.setFromAccount(localAccounts.getByIDAndRoutingNum(localTransfer.getFromAccountID(), str2));
        localTransfer.setFromAccountNumber(str3);
        localTransfer.setFromAccountNumber(localInteger1.intValue());
      }
      else
      {
        try
        {
          localTransfer.setFromAccountID(AccountSettings.buildAccountId(str3, "0"));
        }
        catch (SystemException localSystemException2)
        {
          throw new FIException(getClass().getName(), 2, "System settings have not been initialized.", localSystemException2);
        }
        localTransfer.setFromAccount(localAccounts.getByIDAndRoutingNum(localTransfer.getFromAccountID(), str2));
        localTransfer.setFromAccountNumber(str3);
        localTransfer.setFromAccountNumber(0);
      }
      if (localInteger2 != null)
      {
        try
        {
          localTransfer.setToAccountID(AccountSettings.buildAccountId(str5, localInteger2.toString()));
        }
        catch (SystemException localSystemException3)
        {
          throw new FIException(getClass().getName(), 2, "System settings have not been initialized.", localSystemException3);
        }
        localTransfer.setToAccount(localAccounts.getByIDAndRoutingNum(localTransfer.getToAccountID(), str4));
        localTransfer.setToAccountNumber(str5);
        localTransfer.setToAccountNumber(localInteger2.intValue());
      }
      else
      {
        try
        {
          localTransfer.setToAccountID(AccountSettings.buildAccountId(str5, "0"));
        }
        catch (SystemException localSystemException4)
        {
          throw new FIException(getClass().getName(), 2, "System settings have not been initialized.", localSystemException4);
        }
        localTransfer.setToAccount(localAccounts.getByIDAndRoutingNum(localTransfer.getToAccountID(), str4));
        localTransfer.setToAccountNumber(str5);
        localTransfer.setToAccountNumber(0);
      }
      if (localTransfer.getFromAccount() == null) {
        paramErrorMessages.add(new ImportError(3, getImportError("InvalidData", localLocale), getImportError("SourceAccountNotFound", localLocale), localRecord.getLineContent(), localRecord.getLineNumber(), localRecord.getRecordNumber()));
      } else if (!localTransfer.getFromAccount().isFilterable("TransferFrom")) {
        paramErrorMessages.add(new ImportError(3, getImportError("InvalidData", localLocale), getImportError("SourceAccountNotTransferFrom", localLocale), localRecord.getLineContent(), localRecord.getLineNumber(), localRecord.getRecordNumber()));
      }
      if (localTransfer.getToAccount() == null) {
        paramErrorMessages.add(new ImportError(3, getImportError("InvalidData", localLocale), getImportError("DestAccountNotFound", localLocale), localRecord.getLineContent(), localRecord.getLineNumber(), localRecord.getRecordNumber()));
      } else if (!localTransfer.getToAccount().isFilterable("TransferTo")) {
        paramErrorMessages.add(new ImportError(3, getImportError("InvalidData", localLocale), getImportError("DestAccountNotTransferTo", localLocale), localRecord.getLineContent(), localRecord.getLineNumber(), localRecord.getRecordNumber()));
      }
      if ((localTransfer.getFromAccountID() != null) && (localTransfer.getFromAccountID().equals(localTransfer.getToAccountID()))) {
        paramErrorMessages.add(new ImportError(3, getImportError("InvalidData", localLocale), getImportError("DestAccountSameAsSource", localLocale), localRecord.getLineContent(), localRecord.getLineNumber(), localRecord.getRecordNumber()));
      }
      if ((localTransfer.getFromAccount() != null) && (localTransfer.getToAccount() != null) && (localTransfer.getFromAccount().isFilterable("ExternalTransferFrom")) && (localTransfer.getToAccount().isFilterable("ExternalTransferTo"))) {
        paramErrorMessages.add(new ImportError(3, getImportError("InvalidData", localLocale), getImportError("TransferFromExternal", localLocale), localRecord.getLineContent(), localRecord.getLineNumber(), localRecord.getRecordNumber()));
      }
      localTransfer.setAmount(localCurrency);
      if (str7 != null)
      {
        localTransfer.setAmtCurrency(str7);
        if (localTransfer.getAmountValue() != null)
        {
          String str8 = str7;
          String str9 = localTransfer.getToAccount() == null ? null : localTransfer.getToAccount().getCurrencyCode();
          if (str9 == null) {
            str9 = "";
          }
          String str10 = localTransfer.getFromAccount() == null ? null : localTransfer.getFromAccount().getCurrencyCode();
          if (str10 == null) {
            str10 = "";
          }
          localTransfer.setUserAssignedAmountFlag(UserAssignedAmount.SINGLE);
          if (!str9.equals(str10)) {
            if (str9.equals(str8))
            {
              localTransfer.setUserAssignedAmountFlag(UserAssignedAmount.TO);
              localTransfer.setToAmount(localTransfer.getAmountValue());
            }
            else
            {
              localTransfer.setUserAssignedAmountFlag(UserAssignedAmount.FROM);
            }
          }
        }
      }
      if (str6 != null) {
        localTransfer.setMemo(str6);
      }
    }
    if (localTransfers.size() == 0)
    {
      paramErrorMessages.add(new ImportError(3, getImportError("NoTransfers", localLocale), paramErrorMessages.isEmpty() ? getImportError("FileDoesNotContainTransfers", localLocale) : getImportError("NoValidTransfersFound", localLocale), null, null, null));
      paramErrorMessages.setOperationCanContinue(false);
    }
    paramHashMap.put("ImportTransfers", localTransfers);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.custom.CustomTransfer
 * JD-Core Version:    0.7.0.1
 */