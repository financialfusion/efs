package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.db.Payee;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSDebug;

public class RecFundsAllocHandler
{
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("=== RecFundsAllocHandler.eventHander: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length, 6);
    try
    {
      String str1;
      if (paramInt != 0)
      {
        if (paramInt == 1) {
          for (int i = 0; i < paramEventInfoArray._array.length; i++)
          {
            str1 = paramEventInfoArray._array[i].InstructionID;
            PmtInstruction localPmtInstruction = PmtInstruction.getPmtInstr(str1, paramFFSConnectionHolder);
            Object localObject1;
            if (localPmtInstruction == null)
            {
              localObject1 = "*** RecFundsAllocHandler.eventHandler failed: could not find the SrvrTID=" + str1 + " in BPW_PmtInstruction";
              FFSDebug.console((String)localObject1);
              FFSDebug.log((String)localObject1);
            }
            else
            {
              localObject1 = localPmtInstruction.getPmtInfo();
              PayeeInfo localPayeeInfo = Payee.findPayeeByID(((PmtInfo)localObject1).PayeeID, paramFFSConnectionHolder);
              Object localObject2;
              if (localPayeeInfo == null)
              {
                localObject2 = "*** RecFundsAllocHandler.eventHandler failed: could not find the PayeeID=" + ((PmtInfo)localObject1).PayeeID + " in BPW_Payee for pmt of SrvrTID=" + str1;
                FFSDebug.console((String)localObject2);
                FFSDebug.log((String)localObject2);
              }
              else
              {
                localObject2 = ScheduleInfo.getScheduleInfo(paramEventInfoArray._array[i].FIId, "RECFUNDTRN", str1, paramFFSConnectionHolder);
                if ((localObject2 != null) && (((ScheduleInfo)localObject2).StatusOption != 1))
                {
                  String str2 = PmtInstruction.getStatus(str1, paramFFSConnectionHolder);
                  if (str2.equals("WILLPROCESSON"))
                  {
                    ((ScheduleInfo)localObject2).CurInstanceNum -= 1;
                    ScheduleInfo.computeNextInstanceDate((ScheduleInfo)localObject2);
                    ScheduleInfo.modifySchedule(paramFFSConnectionHolder, ((ScheduleInfo)localObject2).ScheduleID, (ScheduleInfo)localObject2);
                  }
                  else if ((!str2.equals("INFUNDSALLOC")) && (!str2.equals("NOFUNDSON")) && (!str2.equals("NOFUNDSON_NOTIF")))
                  {
                    ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "RECFUNDTRN", str1);
                  }
                  else
                  {
                    ScheduleInfo.moveNextInstanceDate((ScheduleInfo)localObject2, -1);
                    ScheduleInfo localScheduleInfo = a(((ScheduleInfo)localObject2).NextInstanceDate, ((ScheduleInfo)localObject2).LogID, ((PmtInfo)localObject1).FIID);
                    String str3 = ScheduleInfo.createSchedule(paramFFSConnectionHolder, "FUNDTRN", str1, localScheduleInfo);
                  }
                }
              }
            }
          }
        }
        if ((paramInt == 2) || (paramInt == 3) || (paramInt != 4)) {}
      }
      FFSDebug.log("==== RecFundsAllocHandler.eventHander: end", 6);
    }
    catch (Exception localException)
    {
      str1 = "*** RecFundsAllocHandler.eventHandler failed:";
      FFSDebug.log(str1 + localException.toString());
      throw new FFSException(localException.toString());
    }
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== RecFundsAllocHandler.resubmitEventHandler: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length + ",instructionType=" + paramEventInfoArray._array[0].InstructionType);
    eventHandler(paramInt, paramEventInfoArray, paramFFSConnectionHolder);
    FFSDebug.log("=== RecFundsAllocHandler.resubmitEventHandler: end");
  }
  
  private ScheduleInfo a(int paramInt, String paramString1, String paramString2)
    throws OFXException, Exception
  {
    ScheduleInfo localScheduleInfo = new ScheduleInfo();
    localScheduleInfo.FIId = paramString2;
    localScheduleInfo.Status = "Active";
    localScheduleInfo.Frequency = 10;
    localScheduleInfo.StartDate = paramInt;
    localScheduleInfo.NextInstanceDate = paramInt;
    localScheduleInfo.InstanceCount = 1;
    localScheduleInfo.LogID = paramString1;
    localScheduleInfo.CurInstanceNum = 1;
    return localScheduleInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.RecFundsAllocHandler
 * JD-Core Version:    0.7.0.1
 */