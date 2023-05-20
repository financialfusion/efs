package com.ffusion.fileimporter.fileadapters.custom;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.beans.fileimporter.ImportError;
import com.ffusion.beans.fileimporter.MappingDefinition;
import com.ffusion.beans.fileimporter.Record;
import com.ffusion.beans.fileimporter.Records;
import com.ffusion.csil.FIException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public abstract class ACHMapper
  extends Mapper
{
  public void open(HashMap paramHashMap)
    throws FIException
  {
    super.open(paramHashMap);
  }
  
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
    String str = localMappingDefinition.getOutputFormatName();
    if (str == null) {
      throw new FIException(getClass().getName(), 2, "Output format not found in mapping definition");
    }
    long l = System.currentTimeMillis();
    ACHEntries localACHEntries = new ACHEntries();
    Iterator localIterator = paramRecords.iterator();
    for (int i = 1; localIterator.hasNext(); i++)
    {
      Record localRecord = (Record)localIterator.next();
      ACHEntry localACHEntry = new ACHEntry(localLocale);
      localACHEntry.setID(String.valueOf(l + i));
      ACHPayee localACHPayee = new ACHPayee();
      localACHPayee.setScope("ACHBatch");
      localACHEntry.setAchPayee(localACHPayee);
      localACHEntries.add(localACHEntry);
      processRecord(paramErrorMessages, localMappingDefinition, localACHEntry, localRecord);
      localACHEntry.setRecordNumber(localRecord.getRecordNumber());
      localACHEntry.setLineNumber(localRecord.getLineNumber());
      localACHEntry.setLineContent(localRecord.getLineContent());
    }
    if (localACHEntries.size() == 0)
    {
      paramErrorMessages.add(new ImportError(3, getImportError("NoEntries", localLocale), paramErrorMessages.isEmpty() ? getImportError("FileDoesNotContainEntries", localLocale) : getImportError("NoValidEntriesFound", localLocale), null, null, null));
      paramErrorMessages.setOperationCanContinue(false);
    }
    paramHashMap.put("ACHEntries", localACHEntries);
  }
  
  protected Integer getIntegerACHAccountType(ErrorMessages paramErrorMessages, Record paramRecord, String paramString, boolean paramBoolean, Locale paramLocale)
    throws FIException
  {
    Integer localInteger = super.getIntegerACHAccountType(paramErrorMessages, paramRecord, paramString, paramBoolean, paramLocale);
    if ((localInteger != null) && (localInteger.intValue() > 4) && (!isValidTransactionCode(localInteger)))
    {
      Object[] arrayOfObject = { paramString, String.valueOf(localInteger.intValue()) };
      paramErrorMessages.add(new ImportError(1, getImportError("InvalidData", paramLocale), getImportError("FieldNotValidTransactionCode", arrayOfObject, paramLocale), paramRecord.getLineContent(), paramRecord.getLineNumber(), paramRecord.getRecordNumber()));
    }
    return localInteger;
  }
  
  protected static boolean isValidTransactionCode(Integer paramInteger)
  {
    if (paramInteger == null) {
      return false;
    }
    int i = paramInteger.shortValue();
    short[] arrayOfShort = { 22, 23, 27, 28, 32, 33, 37, 38, 42, 43, 47, 48, 52, 53 };
    for (int j = 0; j < arrayOfShort.length; j++) {
      if (arrayOfShort[j] == i) {
        return true;
      }
    }
    return false;
  }
  
  protected static void setTransactionCode(ErrorMessages paramErrorMessages, Record paramRecord, Integer paramInteger, ACHEntry paramACHEntry, boolean paramBoolean, Locale paramLocale)
    throws FIException
  {
    if (paramInteger == null) {
      return;
    }
    if (isValidTransactionCode(paramInteger))
    {
      Boolean localBoolean = paramACHEntry.getAmountIsDebitObject();
      paramACHEntry.setTransactionCode(paramInteger.shortValue());
      if (paramBoolean)
      {
        if (localBoolean != null)
        {
          boolean bool1 = localBoolean.booleanValue();
          if (bool1 != paramACHEntry.getAmountIsDebitValue())
          {
            Object[] arrayOfObject1;
            if (bool1 == true)
            {
              arrayOfObject1 = new Object[] { paramInteger };
              paramErrorMessages.add(new ImportError(1, getImportError("InvalidData", paramLocale), getImportError("TranCodeNotValidDebitOnly", arrayOfObject1, paramLocale), paramRecord.getLineContent(), paramRecord.getLineNumber(), paramRecord.getRecordNumber()));
            }
            else
            {
              arrayOfObject1 = new Object[] { paramInteger };
              paramErrorMessages.add(new ImportError(1, getImportError("InvalidData", paramLocale), getImportError("TranCodeNotValidCreditOnly", arrayOfObject1, paramLocale), paramRecord.getLineContent(), paramRecord.getLineNumber(), paramRecord.getRecordNumber()));
            }
          }
        }
      }
      else
      {
        double d = 0.0D;
        boolean bool2 = paramACHEntry.getPrenoteValue();
        if (paramACHEntry.getAmountValue() != null) {
          d = paramACHEntry.getAmountValue().doubleValue();
        }
        Object[] arrayOfObject2;
        if (d == 0.0D)
        {
          if (!bool2)
          {
            arrayOfObject2 = new Object[] { paramInteger };
            paramErrorMessages.add(new ImportError(1, getImportError("InvalidData", paramLocale), getImportError("NotPrenoteAmountIsZero", arrayOfObject2, paramLocale), paramRecord.getLineContent(), paramRecord.getLineNumber(), paramRecord.getRecordNumber()));
          }
        }
        else if (bool2)
        {
          arrayOfObject2 = new Object[] { paramInteger };
          paramErrorMessages.add(new ImportError(1, getImportError("InvalidData", paramLocale), getImportError("PrenoteAmountNotZero", arrayOfObject2, paramLocale), paramRecord.getLineContent(), paramRecord.getLineNumber(), paramRecord.getRecordNumber()));
        }
      }
    }
  }
  
  public abstract void processRecord(ErrorMessages paramErrorMessages, MappingDefinition paramMappingDefinition, ACHEntry paramACHEntry, Record paramRecord)
    throws FIException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.custom.ACHMapper
 * JD-Core Version:    0.7.0.1
 */