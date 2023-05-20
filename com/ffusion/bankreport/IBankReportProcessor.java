package com.ffusion.bankreport;

import com.ffusion.bankreport.adapter.IBankReportAdapter;
import com.ffusion.beans.bankreport.BankReportDefinition;
import java.io.InputStream;
import java.util.HashMap;

public abstract interface IBankReportProcessor
{
  public abstract void processReport(BankReportDefinition paramBankReportDefinition, InputStream paramInputStream, HashMap paramHashMap)
    throws BRException;
  
  public abstract IBankReportAdapter getBankReportAdapter();
  
  public abstract void setBankReportAdapter(IBankReportAdapter paramIBankReportAdapter);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.bankreport.IBankReportProcessor
 * JD-Core Version:    0.7.0.1
 */