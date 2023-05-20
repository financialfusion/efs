package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.ACHTotals;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.BPWExtdHist;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;

public class ACHTotalsProcessor
  implements DBConsts, FFSConst, ACHConsts, BPWResource
{
  public BPWExtdHist getACHTotals(BPWExtdHist paramBPWExtdHist)
    throws FFSException
  {
    FFSDebug.log("ACHTotalsProcessor.getACHTotals: start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramBPWExtdHist = ACHTotals.getACHTotals(localFFSConnectionHolder, paramBPWExtdHist);
      BPWExtdHist localBPWExtdHist = paramBPWExtdHist;
      return localBPWExtdHist;
    }
    catch (Exception localException)
    {
      String str = "ACHTotalsProcessor.getACHTotals failed " + localException.toString();
      FFSDebug.log(str + FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWExtdHist getACHParticipantTotals(BPWExtdHist paramBPWExtdHist)
    throws FFSException
  {
    FFSDebug.log("ACHTotalsProcessor.getACHParticipantTotals: start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramBPWExtdHist = ACHTotals.getACHParticipantTotals(localFFSConnectionHolder, paramBPWExtdHist);
      BPWExtdHist localBPWExtdHist = paramBPWExtdHist;
      return localBPWExtdHist;
    }
    catch (Exception localException)
    {
      String str = "ACHTotalsProcessor.getACHParticipantTotals failed " + localException.toString();
      FFSDebug.log(str + FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.ACHTotalsProcessor
 * JD-Core Version:    0.7.0.1
 */