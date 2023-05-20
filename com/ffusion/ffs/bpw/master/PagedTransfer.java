package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Transfer;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;

public class PagedTransfer
  extends PagedTmpTable
{
  public String getTmpTableName()
    throws FFSException
  {
    return "BPW_TransfrTmpHist";
  }
  
  protected void jdMethod_try(PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str = "PagedTransfer.createSessionData";
    FFSDebug.log(str + " : start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      PagingContext localPagingContext = paramPagingInfo.getPagingContext();
      localObject1 = localPagingContext.getMap();
      HashMap localHashMap = (HashMap)((HashMap)localObject1).get("SEARCH_CRITERIA");
      String[] arrayOfString1 = null;
      String[] arrayOfString2 = null;
      if (localHashMap != null)
      {
        arrayOfString1 = (String[])localHashMap.get("Dests");
        arrayOfString2 = (String[])localHashMap.get("TransType");
      }
      paramPagingInfo.setTotalTrans(0);
      if (arrayOfString1 != null)
      {
        if ((jdMethod_do(arrayOfString1)) || (jdMethod_for(arrayOfString2)))
        {
          Transfer.createSessionData(localFFSConnectionHolder, paramPagingInfo);
          FFSDebug.log(str + "pagingInfo.getStatusCode = " + paramPagingInfo.getStatusCode(), 6);
          if (paramPagingInfo.getStatusCode() != 0)
          {
            localFFSConnectionHolder.conn.rollback();
            paramPagingInfo.getPagingContext().setSessionId(null);
            return;
          }
        }
        if (jdMethod_int(arrayOfString1) == true)
        {
          XferSyncProcessor.createSessionData(localFFSConnectionHolder, paramPagingInfo);
          FFSDebug.log(str + "pagingInfo.getStatusCode = " + paramPagingInfo.getStatusCode(), 6);
          if (paramPagingInfo.getStatusCode() != 0)
          {
            localFFSConnectionHolder.conn.rollback();
            paramPagingInfo.getPagingContext().setSessionId(null);
            return;
          }
        }
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      Object localObject1 = str + " failed " + localThrowable.toString();
      FFSDebug.log((String)localObject1 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, (String)localObject1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  protected PagingInfo jdMethod_byte(PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "PagedTransfer.getSessionPage";
    FFSDebug.log(str1 + " : start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramPagingInfo = Transfer.getSessionPage(localFFSConnectionHolder, paramPagingInfo);
      localFFSConnectionHolder.conn.commit();
      PagingInfo localPagingInfo = paramPagingInfo;
      return localPagingInfo;
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = str1 + " failed " + localThrowable.toString();
      FFSDebug.log(str2 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  protected PagingInfo jdMethod_case(PagingInfo paramPagingInfo)
    throws FFSException
  {
    paramPagingInfo = super.jdMethod_case(paramPagingInfo);
    if (paramPagingInfo.getStatusCode() != 0) {
      return paramPagingInfo;
    }
    paramPagingInfo = d(paramPagingInfo);
    return paramPagingInfo;
  }
  
  private static PagingInfo d(PagingInfo paramPagingInfo)
    throws BPWException
  {
    String str1 = "PagedTransfer.validateTransfer";
    if (paramPagingInfo == null)
    {
      paramPagingInfo = new PagingInfo();
      paramPagingInfo.setStatusCode(16000);
      paramPagingInfo.setStatusMsg("PagingInfo  is null");
      FFSDebug.log(str1 + " : " + "Null PagingInfo passed", 0);
      return paramPagingInfo;
    }
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    String[] arrayOfString1 = (String[])localHashMap2.get("StatusList");
    int j;
    if ((arrayOfString1 != null) && (arrayOfString1.length > 0))
    {
      int i = 0;
      j = 0;
      for (int k = 0; k < arrayOfString1.length; k++) {
        if (("TEMPLATE".equalsIgnoreCase(arrayOfString1[k])) || ("RECTEMPLATE".equalsIgnoreCase(arrayOfString1[k])))
        {
          i = 1;
          if (j != 0)
          {
            paramPagingInfo.setStatusCode(19340);
            paramPagingInfo.setStatusMsg("Status list cannot contain status TEMPLATE or RECTEMPLATE with any other status");
            FFSDebug.log(str1, " : ", "Status list cannot contain status TEMPLATE or RECTEMPLATE with any other status", 0);
            return paramPagingInfo;
          }
        }
        else
        {
          j = 1;
          if (i != 0)
          {
            paramPagingInfo.setStatusCode(19340);
            paramPagingInfo.setStatusMsg("Status list cannot contain status TEMPLATE or RECTEMPLATE with any other status");
            FFSDebug.log(str1, " : ", "Status list cannot contain status TEMPLATE or RECTEMPLATE with any other status", 0);
            return paramPagingInfo;
          }
        }
      }
    }
    String[] arrayOfString2 = (String[])localHashMap2.get("TransType");
    if ((arrayOfString2 != null) && (arrayOfString2.length > 0)) {
      for (j = 0; j < arrayOfString2.length; j++) {
        if ((!arrayOfString2[j].equalsIgnoreCase("Current")) && (!arrayOfString2[j].equalsIgnoreCase("Recurring")) && (!arrayOfString2[j].equalsIgnoreCase("Recmodel")) && (!arrayOfString2[j].equalsIgnoreCase("BATCHTEMPLATE")) && (!arrayOfString2[j].equalsIgnoreCase("TEMPLATE")) && (!arrayOfString2[j].equalsIgnoreCase("Repetitive")) && (!arrayOfString2[j].equalsIgnoreCase("RECTEMPLATE")))
        {
          paramPagingInfo.setStatusCode(19390);
          paramPagingInfo.setStatusMsg("Invalid trans type in BPWHist:");
          FFSDebug.log(str1, " : ", "Invalid trans type in BPWHist:", arrayOfString2[j], 0);
          return paramPagingInfo;
        }
      }
    }
    e(paramPagingInfo);
    String str2 = paramPagingInfo.getStartDate();
    String str3 = paramPagingInfo.getEndDate();
    FFSDebug.log(str1 + " : StartDate: ", str2, ", EndDate: ", str3, 6);
    if (Integer.parseInt(str2) > Integer.parseInt(str3))
    {
      paramPagingInfo.setStatusCode(17080);
      paramPagingInfo.setStatusMsg("Start date cannot be after end date");
      FFSDebug.log(str1 + " : " + " Start Date is later than End Date", 0);
      return paramPagingInfo;
    }
    paramPagingInfo.setStatusCode(0);
    paramPagingInfo.setStatusMsg("Success");
    return paramPagingInfo;
  }
  
  private static void e(PagingInfo paramPagingInfo)
  {
    String str1 = paramPagingInfo.getStartDate();
    String str2 = paramPagingInfo.getEndDate();
    if ((str1 == null) || (str1.trim().length() == 0)) {
      paramPagingInfo.setStartDate(String.valueOf(FFSUtil.getInstanceDate(-30)));
    } else if (str1.trim().length() == 8) {
      paramPagingInfo.setStartDate(str1.trim() + "00");
    }
    if ((str2 == null) || (str2.trim().length() == 0)) {
      paramPagingInfo.setEndDate(String.valueOf(FFSUtil.getInstanceDate(30)));
    } else if (str2.trim().length() == 8) {
      paramPagingInfo.setEndDate(str2.trim() + "00");
    }
    FFSDebug.log("PagedTransfer.fixPagingDates: StartDate=", paramPagingInfo.getStartDate(), " EndDate=", paramPagingInfo.getEndDate(), 6);
  }
  
  private boolean jdMethod_for(String[] paramArrayOfString)
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
      return false;
    }
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      String str = paramArrayOfString[i];
      if ((str.equalsIgnoreCase("TEMPLATE")) || (str.equalsIgnoreCase("RECTEMPLATE")) || (str.equalsIgnoreCase("BATCHTEMPLATE"))) {
        return true;
      }
    }
    return false;
  }
  
  private boolean jdMethod_do(String[] paramArrayOfString)
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
      return false;
    }
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      String str = paramArrayOfString[i];
      if ((str.equalsIgnoreCase("ITOE")) || (str.equalsIgnoreCase("EXTERNAL")) || (str.equalsIgnoreCase("ETOI"))) {
        return true;
      }
    }
    return false;
  }
  
  private boolean jdMethod_int(String[] paramArrayOfString)
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
      return false;
    }
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      String str = paramArrayOfString[i];
      if ((str.equalsIgnoreCase("INTERNAL")) || (str.equalsIgnoreCase("ITOI"))) {
        return true;
      }
    }
    return false;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.PagedTransfer
 * JD-Core Version:    0.7.0.1
 */