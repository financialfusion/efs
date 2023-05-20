package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.ACHSameDayEffDate;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.ACHSameDayEffDateInfo;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;

public class ACHSameDayEffDateProcessor
  implements DBConsts, FFSConst, ACHConsts, BPWResource
{
  public ACHSameDayEffDateInfo setACHSameDayEffDateInfo(ACHSameDayEffDateInfo paramACHSameDayEffDateInfo)
    throws FFSException
  {
    String str1 = "SameDayEffDateProcessor.setSameDayEffDateInfo: ";
    FFSDebug.log(str1 + "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      paramACHSameDayEffDateInfo = ACHSameDayEffDate.setACHSameDayEffDateInfo(localFFSConnectionHolder, paramACHSameDayEffDateInfo);
      if (paramACHSameDayEffDateInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        ACHSameDayEffDateInfo localACHSameDayEffDateInfo = paramACHSameDayEffDateInfo;
        return localACHSameDayEffDateInfo;
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      String str3 = null;
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localFFSConnectionHolder != null) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
    return paramACHSameDayEffDateInfo;
  }
  
  public ACHSameDayEffDateInfo getACHSameDayEffDateInfo(ACHSameDayEffDateInfo paramACHSameDayEffDateInfo)
    throws FFSException
  {
    String str1 = "SameDayEffDateProcessor.getSameDayEffDateInfo: ";
    FFSDebug.log(str1 + "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramACHSameDayEffDateInfo = ACHSameDayEffDate.getACHSameDayEffDateInfo(localFFSConnectionHolder, paramACHSameDayEffDateInfo);
      if (paramACHSameDayEffDateInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        ACHSameDayEffDateInfo localACHSameDayEffDateInfo = paramACHSameDayEffDateInfo;
        return localACHSameDayEffDateInfo;
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      String str3 = null;
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localFFSConnectionHolder != null) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
    return paramACHSameDayEffDateInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.ACHSameDayEffDateProcessor
 * JD-Core Version:    0.7.0.1
 */