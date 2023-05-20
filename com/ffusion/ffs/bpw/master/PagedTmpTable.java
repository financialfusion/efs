package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.util.beans.PagingContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public abstract class PagedTmpTable
  implements PagedData, FFSConst, ACHConsts, DBConsts, BPWResource
{
  public static final int MAX_PAGE_SIZE_DEFAULT_INT = 250;
  public static final String MAX_PAGE_SIZE_DEFAULT_STR = "250";
  
  public PagingInfo getPagedData(PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "PagedTmpTable.getPagedData";
    jdMethod_char(paramPagingInfo);
    paramPagingInfo = jdMethod_case(paramPagingInfo);
    if (paramPagingInfo.getStatusCode() != 0)
    {
      FFSDebug.log(str1 + ": Validating failed.", 6);
      return paramPagingInfo;
    }
    try
    {
      PagingContext localPagingContext = paramPagingInfo.getPagingContext();
      str2 = localPagingContext.getDirection();
      if (str2.equals("FIRST") == true) {
        paramPagingInfo = jdMethod_int(paramPagingInfo);
      } else if (str2.equals("NEXT") == true) {
        paramPagingInfo = jdMethod_else(paramPagingInfo);
      } else if (str2.equals("PREVIOUS") == true) {
        paramPagingInfo = jdMethod_if(paramPagingInfo);
      } else if (str2.equals("LAST") == true) {
        paramPagingInfo = jdMethod_do(paramPagingInfo);
      } else {
        throw new BPWException("PagingInfo contains an invalid direction value.");
      }
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + " failed";
      FFSDebug.log(localThrowable, str2, 0);
      throw new BPWException(localThrowable, str2);
    }
    return paramPagingInfo;
  }
  
  protected PagingInfo jdMethod_int(PagingInfo paramPagingInfo)
    throws FFSException
  {
    jdMethod_char(paramPagingInfo);
    String str = paramPagingInfo.getPagingContext().getSessionId();
    if ((str == null) || (str.trim().length() == 0))
    {
      jdMethod_try(paramPagingInfo);
      if (paramPagingInfo.getStatusCode() != 0) {
        return paramPagingInfo;
      }
    }
    paramPagingInfo = jdMethod_byte(paramPagingInfo);
    return paramPagingInfo;
  }
  
  protected PagingInfo jdMethod_else(PagingInfo paramPagingInfo)
    throws FFSException
  {
    jdMethod_char(paramPagingInfo);
    paramPagingInfo = jdMethod_byte(paramPagingInfo);
    return paramPagingInfo;
  }
  
  protected PagingInfo jdMethod_if(PagingInfo paramPagingInfo)
    throws FFSException
  {
    jdMethod_char(paramPagingInfo);
    paramPagingInfo = jdMethod_byte(paramPagingInfo);
    return paramPagingInfo;
  }
  
  protected PagingInfo jdMethod_do(PagingInfo paramPagingInfo)
    throws FFSException
  {
    jdMethod_char(paramPagingInfo);
    paramPagingInfo = jdMethod_byte(paramPagingInfo);
    return paramPagingInfo;
  }
  
  public String getTmpTableName()
    throws FFSException
  {
    return "";
  }
  
  public void cleanup(FFSConnectionHolder paramFFSConnectionHolder, int paramInt)
    throws FFSException
  {
    FFSDebug.log("PagedTmpTable.cleanup start, interval=" + paramInt, 6);
    StringBuffer localStringBuffer = new StringBuffer("SELECT SessionId FROM ");
    String str1 = getTmpTableName();
    localStringBuffer.append(str1);
    localStringBuffer.append(" WHERE SubmitDate < ?");
    long l1 = System.currentTimeMillis();
    long l2 = l1 - paramInt * 60 * 1000;
    Date localDate = new Date(l2);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
    String str2 = localSimpleDateFormat.format(localDate);
    Object[] arrayOfObject = { str2 };
    FFSResultSet localFFSResultSet = null;
    int i = 0;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      paramFFSConnectionHolder.conn.commit();
      while (localFFSResultSet.getNextRow())
      {
        String str3 = localFFSResultSet.getColumnString("SessionId");
        if ((str3 != null) && (str3.trim().length() > 0))
        {
          i += jdMethod_int(paramFFSConnectionHolder, str3);
          paramFFSConnectionHolder.conn.commit();
          FFSDebug.log("PagedTmpTable.cleanup cleaned dta for sessionId= " + str3, 6);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** PagedTmpTable.cleanup failed. Error: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, 0);
      throw new BPWException(str4);
    }
    FFSDebug.log("PagedTmpTable.cleanup done, rows cleaned= " + i, 6);
  }
  
  protected void jdMethod_char(PagingInfo paramPagingInfo)
    throws FFSException
  {
    if (paramPagingInfo == null) {
      return;
    }
    paramPagingInfo.setStatusCode(0);
    paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
  }
  
  protected PagingInfo jdMethod_case(PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "PagedTmpTable.validate";
    FFSDebug.log(str1 + " : start ", 6);
    try
    {
      if (paramPagingInfo == null) {
        throw new FFSException("PagingInfo is null !");
      }
      if (paramPagingInfo.getPagingContext() == null) {
        throw new FFSException("PagingContext is null !");
      }
      jdMethod_char(paramPagingInfo);
      paramPagingInfo = jdMethod_for(paramPagingInfo);
      if (paramPagingInfo.getStatusCode() != 0) {
        return paramPagingInfo;
      }
      paramPagingInfo = a(paramPagingInfo);
      if (paramPagingInfo.getStatusCode() != 0) {
        return paramPagingInfo;
      }
      paramPagingInfo = jdMethod_new(paramPagingInfo);
      if (paramPagingInfo.getStatusCode() != 0) {
        return paramPagingInfo;
      }
      return paramPagingInfo;
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + " failed";
      FFSDebug.log(localThrowable, str2, 0);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  private PagingInfo jdMethod_for(PagingInfo paramPagingInfo)
  {
    String str1 = "PagedTmpTable.validateDirection";
    FFSDebug.log(str1 + ": Starts ...", 6);
    PagingContext localPagingContext = paramPagingInfo.getPagingContext();
    String str2 = localPagingContext.getDirection();
    if (str2 == null)
    {
      str2 = "FIRST";
      localPagingContext.setDirection(str2);
    }
    if ((str2.equals("FIRST") != true) && (str2.equals("NEXT") != true) && (str2.equals("PREVIOUS") != true) && (str2.equals("LAST") != true))
    {
      paramPagingInfo.setStatusCode(28030);
      try
      {
        paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(28030, null, "PAYMENT_MESSAGE"));
      }
      catch (Exception localException) {}
    }
    FFSDebug.log(str1 + ": direction = " + str2, 6);
    FFSDebug.log(str1 + ": Done.", 6);
    return paramPagingInfo;
  }
  
  private PagingInfo a(PagingInfo paramPagingInfo)
  {
    String str1 = "PagedTmpTable.validatePageSize";
    FFSDebug.log(str1 + ": Starts ....", 6);
    PagingContext localPagingContext = paramPagingInfo.getPagingContext();
    int i;
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      String str3 = localPropertyConfig.otherProperties.getProperty("bpw.paging.maximum.pagesize", "250");
      FFSDebug.log(str1 + ": server maximum size = " + str3, 6);
      i = Integer.parseInt(str3);
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      i = 250;
    }
    String str2 = (String)localPagingContext.getMap().get("PAGE_SIZE");
    str2 = str2.trim();
    FFSDebug.log(str1 + ": requested page size = " + str2, 6);
    int j;
    if (str2 == null)
    {
      j = 0;
      localPagingContext.getMap().put("PAGE_SIZE", Integer.toString(j));
      FFSDebug.log(str1 + ": set requested page size = " + j, 6);
    }
    else
    {
      try
      {
        j = Integer.parseInt(str2);
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        paramPagingInfo.setStatusCode(28040);
        try
        {
          paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(28040, null, "PAYMENT_MESSAGE"));
        }
        catch (Exception localException3) {}
        return paramPagingInfo;
      }
    }
    if (j == -1)
    {
      j = i;
      localPagingContext.getMap().put("PAGE_SIZE", Integer.toString(j));
      FFSDebug.log(str1 + ": set requested page size = " + j, 6);
    }
    if (j > i)
    {
      paramPagingInfo.setStatusCode(28000);
      try
      {
        paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(28000, null, "PAYMENT_MESSAGE"));
      }
      catch (Exception localException1) {}
      return paramPagingInfo;
    }
    if ((j < 1) && (j != 0))
    {
      paramPagingInfo.setStatusCode(28040);
      try
      {
        paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(28040, null, "PAYMENT_MESSAGE"));
      }
      catch (Exception localException2) {}
      return paramPagingInfo;
    }
    FFSDebug.log(str1 + ": Done", 6);
    return paramPagingInfo;
  }
  
  private PagingInfo jdMethod_new(PagingInfo paramPagingInfo)
    throws BPWException
  {
    String str1 = "PagedTmpTable.validateDate: ";
    FFSDebug.log(str1 + "Starts ...", 6);
    String str2 = paramPagingInfo.getStartDate();
    String str3 = paramPagingInfo.getEndDate();
    String str4;
    if (!jdMethod_char(str2))
    {
      FFSDebug.log(str1 + "startDate = " + str2 + ".", 6);
      if (str2.length() > 8) {
        str2 = str2.substring(0, 8);
      }
      if (!BPWUtil.validateDate(str2, "yyyyMMdd"))
      {
        str4 = BPWLocaleUtil.getMessage(17205, null, "WIRE_MESSAGE");
        paramPagingInfo.setStatusCode(17205);
        paramPagingInfo.setStatusMsg(str4);
        FFSDebug.log(str1, str4 + ": " + str2, 0);
        return paramPagingInfo;
      }
    }
    if (!jdMethod_char(str3))
    {
      FFSDebug.log(str1 + "endDate = " + str3 + ".", 6);
      if (str3.length() > 8) {
        str3 = str3.substring(0, 8);
      }
      if (!BPWUtil.validateDate(str3, "yyyyMMdd"))
      {
        str4 = BPWLocaleUtil.getMessage(17206, null, "WIRE_MESSAGE");
        paramPagingInfo.setStatusCode(17206);
        paramPagingInfo.setStatusMsg(str4);
        FFSDebug.log(str1, str4 + ": " + str3, 0);
        return paramPagingInfo;
      }
    }
    FFSDebug.log(str1 + "Done", 6);
    paramPagingInfo.setStatusCode(0);
    paramPagingInfo.setStatusMsg("Success");
    return paramPagingInfo;
  }
  
  private boolean jdMethod_char(String paramString)
  {
    return (paramString == null) || (paramString.trim().length() == 0);
  }
  
  protected void jdMethod_try(PagingInfo paramPagingInfo)
    throws FFSException
  {}
  
  protected PagingInfo jdMethod_byte(PagingInfo paramPagingInfo)
    throws FFSException
  {
    return paramPagingInfo;
  }
  
  private int jdMethod_int(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("PagedTmpTable.delete start, histId=" + paramString, 6);
    Object[] arrayOfObject = { paramString };
    int i = 0;
    StringBuffer localStringBuffer = new StringBuffer("DELETE FROM ");
    String str = getTmpTableName();
    localStringBuffer.append(str);
    localStringBuffer.append(" WHERE SessionId=?");
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** PagedTmpTable.delete failed: " + localException.toString() + FFSDebug.stackTrace(localException));
      throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
    }
    FFSDebug.log("PagedTmpTable.delete done, sessionId=" + paramString, 6);
    return i;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.PagedTmpTable
 * JD-Core Version:    0.7.0.1
 */