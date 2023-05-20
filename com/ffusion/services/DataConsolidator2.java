package com.ffusion.services;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.dataconsolidator.BAITypeCodeInfo;
import com.ffusion.dataconsolidator.adapter.DCException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public abstract interface DataConsolidator2
  extends DataConsolidator
{
  public static final String BAITYPECODES_FILENAME = "baiTypeCodes.xml";
  public static final String BAITYPECODEDCMODULE_FILENAME = "baiTypeCodeDCModule.xml";
  
  public abstract BAITypeCodeInfo getBAITypeCodeInfo(int paramInt)
    throws DCException;
  
  public abstract ArrayList getBAITypeCodeInfoList(int paramInt)
    throws DCException;
  
  public abstract int getBAICode(String paramString1, String paramString2)
    throws DCException;
  
  public abstract AccountHistories getHistory(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException;
  
  public abstract AccountSummaries getSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.DataConsolidator2
 * JD-Core Version:    0.7.0.1
 */