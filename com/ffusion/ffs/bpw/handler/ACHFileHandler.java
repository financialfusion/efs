package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.interfaces.ACHFileInfo;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.IACHFileAdapter;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;

public class ACHFileHandler
  implements DBConsts, FFSConst, BPWResource
{
  private boolean jM;
  private String jO = "com.ffusion.ffs.bpw.fulfill.achadapter.ACHFileAdapter";
  private IACHFileAdapter jN = null;
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("ACHFileHandler.eventHandler: begin, eventSeq=" + paramInt, 6);
    Object localObject;
    if (paramInt == 0)
    {
      this.jM = true;
      localObject = Class.forName(this.jO);
      this.jN = ((IACHFileAdapter)((Class)localObject).newInstance());
      this.jN.start(paramEventInfoArray._array[0].cutOffId, paramEventInfoArray._array[0].processId);
    }
    else if (paramInt != 3)
    {
      if (paramInt == 1)
      {
        if (this.jM)
        {
          try
          {
            if ((paramEventInfoArray != null) && (paramEventInfoArray._array != null))
            {
              localObject = new ACHFileInfo[paramEventInfoArray._array.length];
              for (int i = 0; i < paramEventInfoArray._array.length; i++)
              {
                localObject[i] = new ACHFileInfo();
                if (paramEventInfoArray._array[i] != null) {
                  if ((paramEventInfoArray._array[i].InstructionType != null) && (paramEventInfoArray._array[i].InstructionType.compareTo("ACHFILETRN") == 0))
                  {
                    localObject[i].setFileId(paramEventInfoArray._array[i].InstructionID);
                  }
                  else
                  {
                    FFSDebug.log("ACHFileHandler.eventHandler: Invalid InstructionType = " + paramEventInfoArray._array[i].InstructionType, 0);
                    FFSDebug.log("ACHFileHandler.eventHandler: This instrcution is skipped. Id: " + paramEventInfoArray._array[i].InstructionID, 0);
                  }
                }
              }
              this.jN.processACHFiles((ACHFileInfo[])localObject, paramFFSConnectionHolder);
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
          FFSDebug.log("ACHFileHandler.eventHandler: invalid eventSequence = " + paramInt, 0);
          throw new Exception("ACHFileHandler.eventHandler: invalid eventSequence!");
        }
      }
      else if ((paramInt != 4) && (paramInt == 2)) {
        this.jN.shutdown();
      }
    }
    FFSDebug.log("ACHFileHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== ACHFileHandler.resubmitEventHandler: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length + ",instructionType=" + paramEventInfoArray._array[0].InstructionType);
    eventHandler(paramInt, paramEventInfoArray, paramFFSConnectionHolder);
    FFSDebug.log("=== ACHFileHandler.resubmitEventHandler: end");
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.ACHFileHandler
 * JD-Core Version:    0.7.0.1
 */