package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.ACHBatch;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;

public class PagedACH
  extends PagedTmpTable
{
  public String getTmpTableName()
    throws FFSException
  {
    return "BPW_ACHTempHist";
  }
  
  protected void jdMethod_try(PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "PagedACH.createSessionData";
    FFSDebug.log(str1 + " : start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      ACHBatch.createSessionData(localFFSConnectionHolder, paramPagingInfo);
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
    String str1 = "PagedACH.getSessionPage";
    FFSDebug.log(str1 + " : start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramPagingInfo = ACHBatch.getSessionPage(localFFSConnectionHolder, paramPagingInfo);
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
    paramPagingInfo = b(paramPagingInfo);
    return paramPagingInfo;
  }
  
  private static PagingInfo b(PagingInfo paramPagingInfo)
  {
    String str1 = "PagedACH.validateACHBatch";
    if (paramPagingInfo == null)
    {
      paramPagingInfo = new PagingInfo();
      paramPagingInfo.setStatusCode(16000);
      paramPagingInfo.setStatusMsg(" is null");
      FFSDebug.log(str1 + " : " + "Null PagingInfo passed", 0);
      return paramPagingInfo;
    }
    String str2 = paramPagingInfo.getStartDate();
    String str3 = paramPagingInfo.getEndDate();
    if ((str2 == null) || (str2.trim().length() == 0)) {
      paramPagingInfo.setStartDate(String.valueOf(FFSUtil.getInstanceDate(-30)));
    } else if (str2.trim().length() == 8) {
      paramPagingInfo.setStartDate(str2.trim() + "00");
    }
    if ((str3 == null) || (str3.trim().length() == 0)) {
      paramPagingInfo.setEndDate(String.valueOf(FFSUtil.getInstanceDate(30)));
    } else if (str3.trim().length() == 8) {
      paramPagingInfo.setEndDate(str3.trim() + "00");
    }
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
  
  private static void c(PagingInfo paramPagingInfo)
  {
    if ((paramPagingInfo.getStartDate() == null) || (paramPagingInfo.getStartDate().trim().length() == 0)) {
      paramPagingInfo.setStartDate(new String("0000000000"));
    } else if (paramPagingInfo.getStartDate().trim().length() == 8) {
      paramPagingInfo.setStartDate(paramPagingInfo.getStartDate().trim() + "00");
    }
    if ((paramPagingInfo.getEndDate() == null) || (paramPagingInfo.getEndDate().trim().length() == 0))
    {
      Integer localInteger = new Integer(DBUtil.getCurrentStartDate() + 1000000);
      paramPagingInfo.setEndDate(localInteger.toString());
    }
    else if (paramPagingInfo.getEndDate().trim().length() == 8)
    {
      paramPagingInfo.setEndDate(paramPagingInfo.getEndDate().trim() + "00");
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.PagedACH
 * JD-Core Version:    0.7.0.1
 */