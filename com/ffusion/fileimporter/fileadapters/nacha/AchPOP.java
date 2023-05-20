package com.ffusion.fileimporter.fileadapters.nacha;

import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.csil.FIException;

public class AchPOP
  extends Mapper
{
  public ACHEntry processEntry(String paramString)
    throws FIException
  {
    int i = 0;
    i++;
    String str1 = getACHField(paramString, i, 2);
    i += 2;
    String str2 = getACHField(paramString, i, 9);
    i += 9;
    String str3 = getACHField(paramString, i, 17);
    i += 17;
    String str4 = getACHField(paramString, i, 10);
    i += 10;
    String str5 = getACHField(paramString, i, 9);
    i += 9;
    String str6 = getACHField(paramString, i, 4);
    i += 4;
    String str7 = getACHField(paramString, i, 2);
    i += 2;
    String str8 = getACHField(paramString, i, 22);
    i += 22;
    String str9 = getACHField(paramString, i, 2);
    i += 2;
    i++;
    this.entry.setTransactionCode(str1);
    this.payee.setRoutingNumber(str2);
    this.payee.setAccountNumber(str3);
    this.entry.setAmount(getCurrency(str4));
    this.entry.setCheckSerialNumber(str5);
    this.entry.setTerminalCity(str6);
    this.entry.setTerminalState(str7);
    this.payee.setName(str8);
    this.payee.setNickName(str8);
    this.entry.setDiscretionaryData(str9);
    this.payee.setScope("ACHBatch");
    return this.entry;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.nacha.AchPOP
 * JD-Core Version:    0.7.0.1
 */