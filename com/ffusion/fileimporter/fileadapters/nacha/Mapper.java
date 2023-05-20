package com.ffusion.fileimporter.fileadapters.nacha;

import com.ffusion.beans.Currency;
import com.ffusion.beans.ach.ACHAccountTypes;
import com.ffusion.beans.ach.ACHAddenda;
import com.ffusion.beans.ach.ACHAddendas;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.csil.FIException;
import java.math.BigDecimal;
import java.util.Locale;

public abstract class Mapper
  implements ACHAccountTypes
{
  protected ACHEntry entry;
  protected ACHPayee payee;
  private long aVz = System.currentTimeMillis();
  private static int aVy = 0;
  
  public void reset()
  {
    this.entry = new ACHEntry();
    this.entry.setID(String.valueOf(this.aVz + aVy++));
    this.payee = new ACHPayee();
    this.payee.setScope("ACHBatch");
    this.payee.setAccountType(0);
    this.entry.setAchPayee(this.payee);
  }
  
  public abstract ACHEntry processEntry(String paramString)
    throws FIException;
  
  public void processAddenda(String paramString, ACHEntry paramACHEntry)
    throws FIException
  {}
  
  public static void processAddendaTypeFive(String paramString, ACHEntry paramACHEntry)
    throws FIException
  {
    String str1 = paramString.substring(83, 87);
    String str2 = paramString.substring(3, 83);
    ACHAddendas localACHAddendas = paramACHEntry.getAddendas();
    if (localACHAddendas == null)
    {
      localACHAddendas = new ACHAddendas();
      paramACHEntry.setAddendas(localACHAddendas);
    }
    ACHAddenda localACHAddenda = new ACHAddenda();
    localACHAddenda.setID(str1);
    localACHAddenda.setPmtRelatedInfo(str2);
    localACHAddendas.add(localACHAddenda);
  }
  
  protected static Currency getCurrency(String paramString)
  {
    int i = paramString.length();
    if (i < 3)
    {
      paramString = "000" + paramString;
      i += 3;
    }
    paramString = paramString.substring(0, i - 2) + "." + paramString.substring(i - 2, i);
    Currency localCurrency = null;
    try
    {
      localCurrency = new Currency(new BigDecimal(paramString), Locale.getDefault());
    }
    catch (Exception localException)
    {
      localCurrency = new Currency(new BigDecimal("0.0"), Locale.getDefault());
    }
    return localCurrency;
  }
  
  protected static String getACHField(String paramString, int paramInt1, int paramInt2)
  {
    String str = paramString.substring(paramInt1, paramInt1 + paramInt2);
    str = str.trim();
    if (str.length() == 0) {
      return null;
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.nacha.Mapper
 * JD-Core Version:    0.7.0.1
 */