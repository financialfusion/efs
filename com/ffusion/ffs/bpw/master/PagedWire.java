package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Wire;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;

public class PagedWire
  extends PagedTmpTable
{
  public String getTmpTableName()
    throws FFSException
  {
    return "BPW_WireTempHist";
  }
  
  protected void jdMethod_try(PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "PagedWire.createSessionData";
    FFSDebug.log(str1 + " : start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      Wire.createSessionData(localFFSConnectionHolder, paramPagingInfo);
      localFFSConnectionHolder.conn.commit();
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
  
  protected PagingInfo jdMethod_byte(PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "PagedWire.getSessionPage";
    FFSDebug.log(str1 + " : start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramPagingInfo = Wire.getSessionPage(localFFSConnectionHolder, paramPagingInfo);
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
    paramPagingInfo = f(paramPagingInfo);
    return paramPagingInfo;
  }
  
  private static PagingInfo f(PagingInfo paramPagingInfo)
    throws BPWException
  {
    String str1 = "PagedWire.validateWire";
    if (paramPagingInfo == null)
    {
      paramPagingInfo = new PagingInfo();
      paramPagingInfo.setStatusCode(16000);
      paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "PagingInfo " }, "WIRE_MESSAGE"));
      FFSDebug.log(str1 + " : " + "Null PagingInfo passed", 0);
      return paramPagingInfo;
    }
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    String[] arrayOfString = (String[])localHashMap2.get("TransType");
    if ((arrayOfString != null) && (arrayOfString.length > 0)) {
      for (int i = 0; i < arrayOfString.length; i++) {
        if ((!arrayOfString[i].equalsIgnoreCase("SINGLE")) && (!arrayOfString[i].equalsIgnoreCase("TEMPLATE")) && (!arrayOfString[i].equalsIgnoreCase("RECURRING")) && (!arrayOfString[i].equalsIgnoreCase("RECTEMPLATE")) && (!arrayOfString[i].equalsIgnoreCase("BATCH")) && (!arrayOfString[i].equalsIgnoreCase("BATCHWIRE")))
        {
          paramPagingInfo.setStatusCode(19390);
          paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(19390, new String[] { arrayOfString[i] }, "WIRE_MESSAGE"));
          FFSDebug.log(str1, " : ", "Invalid trans type in BPWHist:", arrayOfString[i], 0);
          return paramPagingInfo;
        }
      }
    }
    g(paramPagingInfo);
    String str2 = paramPagingInfo.getStartDate();
    String str3 = paramPagingInfo.getEndDate();
    FFSDebug.log(str1 + " : StartDate: ", str2, ", EndDate: ", str3, 6);
    if (Integer.parseInt(str2) > Integer.parseInt(str3))
    {
      paramPagingInfo.setStatusCode(17080);
      paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(17080, null, "WIRE_MESSAGE"));
      FFSDebug.log(str1 + " : " + " Start Date is later than End Date", 0);
      return paramPagingInfo;
    }
    paramPagingInfo.setStatusCode(0);
    paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    return paramPagingInfo;
  }
  
  private static void g(PagingInfo paramPagingInfo)
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
    FFSDebug.log("PagedWire.fixPagingDates: StartDate=", paramPagingInfo.getStartDate(), " EndDate=", paramPagingInfo.getEndDate(), 6);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.PagedWire
 * JD-Core Version:    0.7.0.1
 */