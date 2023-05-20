package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.ExternalTransferCompany;
import com.ffusion.ffs.bpw.db.Transfer;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyList;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;

public class ExtTransferCompProcessor
  implements DBConsts, FFSConst, OFXConsts
{
  public ExtTransferCompanyInfo addExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws FFSException
  {
    String str1 = "ExtTransferCompProcessor.addExtTransferCompany: ";
    FFSDebug.log(str1, "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    ExtTransferCompanyInfo localExtTransferCompanyInfo1 = null;
    String str2 = null;
    String str3;
    String str4;
    if (paramExtTransferCompanyInfo == null)
    {
      str3 = "failed: Null ExtTransferCompanyInfo Object passed";
      FFSDebug.log("***", str1, str3, 0);
      paramExtTransferCompanyInfo = new ExtTransferCompanyInfo();
      paramExtTransferCompanyInfo.setStatusCode(16000);
      str4 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferCompanyInfo" }, "TRANSFER_MESSAGE");
      paramExtTransferCompanyInfo.setStatusMsg(str4);
      return paramExtTransferCompanyInfo;
    }
    str2 = paramExtTransferCompanyInfo.getCompId();
    if ((str2 != null) && (str2.trim().length() > 0))
    {
      str3 = "failed: CompId already present in passed company info";
      FFSDebug.log("***", str1, str3, 0);
      paramExtTransferCompanyInfo.setStatusCode(21120);
      str4 = BPWLocaleUtil.getMessage(21120, null, "TRANSFER_MESSAGE");
      paramExtTransferCompanyInfo.setStatusMsg(str4);
      return paramExtTransferCompanyInfo;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      str4 = "failed: Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log("****", str1, str4, 0);
      throw new FFSException(localThrowable1, str4);
    }
    try
    {
      localExtTransferCompanyInfo1 = ExternalTransferCompany.add(localFFSConnectionHolder, paramExtTransferCompanyInfo);
      if (localExtTransferCompanyInfo1.getStatusCode() == 0) {
        localFFSConnectionHolder.conn.commit();
      } else {
        localFFSConnectionHolder.conn.rollback();
      }
      ExtTransferCompanyInfo localExtTransferCompanyInfo2 = localExtTransferCompanyInfo1;
      return localExtTransferCompanyInfo2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str4 = "failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log("****", str1, str4, 0);
      throw new FFSException(localThrowable2, str4);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public ExtTransferCompanyInfo modExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws FFSException
  {
    String str1 = "ExtTransferCompProcessor.modExtTransferCompany: ";
    FFSDebug.log(str1, "start", 6);
    if (paramExtTransferCompanyInfo == null)
    {
      paramExtTransferCompanyInfo = new ExtTransferCompanyInfo();
      paramExtTransferCompanyInfo.setStatusCode(16000);
      localObject1 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferCompanyInfo" }, "TRANSFER_MESSAGE");
      paramExtTransferCompanyInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1, (String)localObject1, 0);
      return paramExtTransferCompanyInfo;
    }
    Object localObject1 = new FFSConnectionHolder();
    String str2;
    try
    {
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      str2 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str1, str2, 0);
      throw new FFSException(localThrowable1, "Could not get connection");
    }
    try
    {
      paramExtTransferCompanyInfo = ExternalTransferCompany.modify((FFSConnectionHolder)localObject1, paramExtTransferCompanyInfo);
      if (paramExtTransferCompanyInfo.getStatusCode() == 0) {
        ((FFSConnectionHolder)localObject1).conn.commit();
      } else {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
    }
    catch (Throwable localThrowable2)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      str2 = "failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str1, str2, str3, 0);
      throw new FFSException(localThrowable2, str2);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
    FFSDebug.log(str1, "Done", 6);
    return paramExtTransferCompanyInfo;
  }
  
  public ExtTransferCompanyInfo canExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws FFSException
  {
    String str1 = "ExtTransferCompProcessor.canExtTransferCompany: ";
    FFSDebug.log(str1, "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    String str2 = null;
    String str3 = null;
    String[] arrayOfString = null;
    String str4;
    Object localObject2;
    if (paramExtTransferCompanyInfo == null)
    {
      str4 = "failed: ExtTransferCompanyInfo Object passed is null";
      FFSDebug.log("***", str1, str4, 0);
      paramExtTransferCompanyInfo = new ExtTransferCompanyInfo();
      paramExtTransferCompanyInfo.setStatusCode(16000);
      localObject2 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferCompanyInfo" }, "TRANSFER_MESSAGE");
      paramExtTransferCompanyInfo.setStatusMsg((String)localObject2);
      return paramExtTransferCompanyInfo;
    }
    str2 = paramExtTransferCompanyInfo.getCompId();
    if ((str2 == null) || (str2.trim().length() == 0))
    {
      str4 = "failed: CompId is null";
      FFSDebug.log("****", str1, str4, 0);
      paramExtTransferCompanyInfo.setStatusCode(16010);
      localObject2 = BPWLocaleUtil.getMessage(16010, new String[] { "ExtTransferCompanyInfo", "CompId" }, "TRANSFER_MESSAGE");
      paramExtTransferCompanyInfo.setStatusMsg((String)localObject2);
      return paramExtTransferCompanyInfo;
    }
    str3 = paramExtTransferCompanyInfo.getCustomerId();
    if ((str3 == null) || (str3.trim().length() == 0))
    {
      str4 = "failed: custId is null";
      FFSDebug.log("****", str1, str4, 0);
      paramExtTransferCompanyInfo.setStatusCode(16010);
      localObject2 = BPWLocaleUtil.getMessage(16010, new String[] { "ExtTransferCompanyInfo", "Customer Id" }, "TRANSFER_MESSAGE");
      paramExtTransferCompanyInfo.setStatusMsg((String)localObject2);
      return paramExtTransferCompanyInfo;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      localObject2 = "failed: Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log("****", str1, (String)localObject2, 0);
      throw new FFSException(localThrowable1, (String)localObject2);
    }
    try
    {
      arrayOfString = Transfer.getActiveTransfersByCustomer(localFFSConnectionHolder, str3, false);
      if ((arrayOfString == null) || (arrayOfString.length == 0))
      {
        arrayOfString = Transfer.getActiveTransfersByCustomer(localFFSConnectionHolder, str3, true);
        if ((arrayOfString != null) && (arrayOfString.length > 0))
        {
          paramExtTransferCompanyInfo.setStatusCode(21090);
          localObject1 = BPWLocaleUtil.getMessage(21090, null, "TRANSFER_MESSAGE");
          paramExtTransferCompanyInfo.setStatusMsg((String)localObject1);
          localObject2 = paramExtTransferCompanyInfo;
          return localObject2;
        }
      }
      else
      {
        paramExtTransferCompanyInfo.setStatusCode(21090);
        localObject1 = BPWLocaleUtil.getMessage(21090, null, "TRANSFER_MESSAGE");
        paramExtTransferCompanyInfo.setStatusMsg((String)localObject1);
        localObject2 = paramExtTransferCompanyInfo;
        return localObject2;
      }
      paramExtTransferCompanyInfo = ExternalTransferCompany.cancel(localFFSConnectionHolder, paramExtTransferCompanyInfo);
      if (paramExtTransferCompanyInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
      }
      else
      {
        localFFSConnectionHolder.conn.commit();
        paramExtTransferCompanyInfo.setStatusCode(0);
        paramExtTransferCompanyInfo.setStatusMsg("Success");
      }
      Object localObject1 = paramExtTransferCompanyInfo;
      return localObject1;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      localObject2 = "failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log("****", str1, (String)localObject2, 0);
      throw new FFSException(localThrowable2, (String)localObject2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public ExtTransferCompanyInfo getExtTransferCompany(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws FFSException
  {
    String str1 = "ExtTransferCompProcessor.getExtTransferAccount: ";
    FFSDebug.log(str1, "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    ExtTransferCompanyInfo localExtTransferCompanyInfo1 = null;
    String str3;
    if (paramExtTransferCompanyInfo == null)
    {
      String str2 = "failed: ExtTransferCompanyInfo object is null";
      FFSDebug.log("***", str1, str2, 0);
      localExtTransferCompanyInfo1 = new ExtTransferCompanyInfo();
      localExtTransferCompanyInfo1.setStatusCode(16000);
      str3 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferCompanyInfo" }, "TRANSFER_MESSAGE");
      localExtTransferCompanyInfo1.setStatusMsg(str3);
      return localExtTransferCompanyInfo1;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str3 = "failed: Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log("****", str1, str3, 0);
      throw new FFSException(localThrowable1, str3);
    }
    try
    {
      localExtTransferCompanyInfo1 = ExternalTransferCompany.getExternalTransferCompany(localFFSConnectionHolder, paramExtTransferCompanyInfo);
      localFFSConnectionHolder.conn.commit();
      ExtTransferCompanyInfo localExtTransferCompanyInfo2 = localExtTransferCompanyInfo1;
      return localExtTransferCompanyInfo2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str3 = "failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log("****", str1, str3, 0);
      throw new FFSException(localThrowable2, str3);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public ExtTransferCompanyList getExtTransferCompanyList(ExtTransferCompanyList paramExtTransferCompanyList)
    throws FFSException
  {
    String str1 = "ExtTransferCompProcessorgetExtTransferAccount: ";
    FFSDebug.log(str1, "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    ExtTransferCompanyList localExtTransferCompanyList1 = null;
    String str3;
    if (paramExtTransferCompanyList == null)
    {
      String str2 = "failed: ExternalTransferCompanyList object is null";
      FFSDebug.log("***", str1, str2, 0);
      localExtTransferCompanyList1 = new ExtTransferCompanyList();
      localExtTransferCompanyList1.setStatusCode(16000);
      str3 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferCompanyList" }, "TRANSFER_MESSAGE");
      localExtTransferCompanyList1.setStatusMsg(str3);
      return localExtTransferCompanyList1;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str3 = "failed: Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log("****", str1, str3, 0);
      throw new FFSException(localThrowable1, str3);
    }
    try
    {
      localExtTransferCompanyList1 = ExternalTransferCompany.getExternalTransferCompanyList(localFFSConnectionHolder, paramExtTransferCompanyList);
      localFFSConnectionHolder.conn.commit();
      ExtTransferCompanyList localExtTransferCompanyList2 = localExtTransferCompanyList1;
      return localExtTransferCompanyList2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str3 = "failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log("****", str1, str3, 0);
      throw new FFSException(localThrowable2, str3);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.ExtTransferCompProcessor
 * JD-Core Version:    0.7.0.1
 */