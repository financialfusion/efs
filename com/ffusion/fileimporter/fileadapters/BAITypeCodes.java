package com.ffusion.fileimporter.fileadapters;

import com.ffusion.beans.dataconsolidator.BAITypeCodeInfo;
import com.ffusion.beans.dataconsolidator.DCModule;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.DataConsolidator;
import com.ffusion.dataconsolidator.adapter.DCUtil;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.logging.Level;

public class BAITypeCodes
{
  public static boolean isAccountHistoryCode(int paramInt)
  {
    return a(paramInt, "Account History");
  }
  
  public static boolean isAccountSummaryCode(int paramInt)
  {
    return a(paramInt, "Account Summary");
  }
  
  public static boolean isExtAccountSummaryCode(int paramInt)
  {
    return a(paramInt, "Extended Account Summary");
  }
  
  public static boolean isTransactionCode(int paramInt)
  {
    return a(paramInt, "Account Transactions");
  }
  
  public static boolean isLockboxSummaryCode(int paramInt)
  {
    return a(paramInt, "Lockbox Summary");
  }
  
  public static boolean isLockboxTransactionCode(int paramInt)
  {
    return a(paramInt, "Lockbox Transactions");
  }
  
  public static boolean isLockboxCreditItemCode(int paramInt)
  {
    return a(paramInt, "Lockbox Credit Items");
  }
  
  public static boolean isDspSummaryCode(int paramInt)
  {
    return a(paramInt, "Disbursement Summary");
  }
  
  public static boolean isDspTransactionCode(int paramInt)
  {
    return a(paramInt, "Disbursement Transactions");
  }
  
  private static boolean a(int paramInt, String paramString)
  {
    BAITypeCodeInfo localBAITypeCodeInfo = null;
    try
    {
      localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(paramInt);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log(Level.WARNING, "TypeCode " + paramInt + " not found from " + "baiTypeCodes.xml");
      if (paramString.equals("Account Transactions")) {
        localBAITypeCodeInfo = DCUtil.genericObjectForUnknownBAITypeCodes(paramInt, "TypeCode " + paramInt + " not found from " + "baiTypeCodes.xml", 2, 0, -1);
      } else if (paramString.equals("Extended Account Summary")) {
        localBAITypeCodeInfo = DCUtil.genericObjectForUnknownBAITypeCodes(paramInt, "TypeCode " + paramInt + " not found from " + "baiTypeCodes.xml", 1, 0, -1);
      } else {
        return false;
      }
    }
    ArrayList localArrayList = localBAITypeCodeInfo.getModules();
    for (int i = 0; i < localArrayList.size(); i++)
    {
      DCModule localDCModule = (DCModule)localArrayList.get(i);
      if (localDCModule.getModuleName().equals(paramString)) {
        return true;
      }
    }
    return false;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.BAITypeCodes
 * JD-Core Version:    0.7.0.1
 */