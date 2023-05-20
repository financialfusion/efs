package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.interfaces.ACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.IACHBatchAdapter;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;

public class ACHBatchHandler
  implements DBConsts, FFSConst, BPWResource
{
  private boolean jK;
  private String jL = "com.ffusion.ffs.bpw.fulfill.achadapter.ACHBatchAdapter";
  private IACHBatchAdapter jJ = null;
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("ACHBatchHandler.eventHandler: begin, eventSeq=" + paramInt, 6);
    Object localObject;
    int i;
    if (paramInt == 0)
    {
      this.jK = true;
      localObject = Class.forName(this.jL);
      this.jJ = ((IACHBatchAdapter)((Class)localObject).newInstance());
      i = 0;
      String str = null;
      if ((paramEventInfoArray._array != null) && (paramEventInfoArray._array.length > 0))
      {
        i = paramEventInfoArray._array[0].createEmptyFile;
        str = paramEventInfoArray._array[0].FIId;
      }
      this.jJ.start(paramFFSConnectionHolder, paramEventInfoArray._array[0].cutOffId, paramEventInfoArray._array[0].processId, i, str);
    }
    else if (paramInt != 3)
    {
      if (paramInt == 1)
      {
        if (this.jK)
        {
          try
          {
            if ((paramEventInfoArray != null) && (paramEventInfoArray._array != null))
            {
              localObject = new ACHBatchInfo[paramEventInfoArray._array.length];
              for (i = 0; i < paramEventInfoArray._array.length; i++)
              {
                localObject[i] = new ACHBatchInfo();
                if (paramEventInfoArray._array[i] != null) {
                  if ((paramEventInfoArray._array[i].InstructionType != null) && (paramEventInfoArray._array[i].InstructionType.compareTo("ACHBATCHTRN") == 0))
                  {
                    localObject[i].setBatchId(paramEventInfoArray._array[i].InstructionID);
                  }
                  else
                  {
                    FFSDebug.log("ACHBatchHandler.eventHandler: Invalid InstructionType = " + paramEventInfoArray._array[i].InstructionType, 0);
                    FFSDebug.log("ACHBatchHandler.eventHandler: This instrcution is skipped. Id: " + paramEventInfoArray._array[i].InstructionID, 0);
                  }
                }
              }
              this.jJ.processACHBatches((ACHBatchInfo[])localObject, paramFFSConnectionHolder);
            }
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
            throw localException;
          }
        }
        else
        {
          FFSDebug.log("ACHBatchHandler.eventHandler: invalid eventSequence = " + paramInt, 0);
          throw new Exception("ACHBatchHandler.eventHandler: invalid eventSequence!");
        }
      }
      else if ((paramInt != 4) && (paramInt == 2)) {
        this.jJ.shutdown(paramFFSConnectionHolder);
      }
    }
    FFSDebug.log("ACHBatchHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== ACHBatchHandler.resubmitEventHandler: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length + ",instructionType=" + paramEventInfoArray._array[0].InstructionType);
    eventHandler(paramInt, paramEventInfoArray, paramFFSConnectionHolder);
    FFSDebug.log("=== ACHBatchHandler.resubmitEventHandler: end");
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.ACHBatchHandler
 * JD-Core Version:    0.7.0.1
 */