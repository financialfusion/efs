package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.lang.reflect.Method;

public class RPPSRespFileHandler
  implements DBConsts, FFSConst, BPWResource
{
  private boolean jk;
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("RPPSRespFileHandler.eventHandler: begin, eventSeq=" + paramInt, 6);
    if (paramInt == 0) {
      this.jk = false;
    } else if (paramInt == 1) {
      this.jk = true;
    } else if (paramInt == 2)
    {
      if (this.jk)
      {
        String str = "1000";
        if ((paramEventInfoArray != null) && (paramEventInfoArray._array != null) && (paramEventInfoArray._array.length != 0) && (paramEventInfoArray._array[0] != null)) {
          str = paramEventInfoArray._array[0].FIId;
        }
        try
        {
          Class localClass = Class.forName("com.ffusion.ffs.bpw.fulfill.rpps.RPPSRespFileHandlerImpl");
          Object localObject = localClass.newInstance();
          Class[] arrayOfClass = { FFSConnectionHolder.class, String.class };
          Object[] arrayOfObject = { paramFFSConnectionHolder, str };
          Method localMethod = localClass.getMethod("processResponseFiles", arrayOfClass);
          localMethod.invoke(localObject, arrayOfObject);
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          throw localException;
        }
      }
    }
    else if ((paramInt == 3) || (paramInt != 4)) {}
    FFSDebug.log("RPPSRespFileHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== RPPSRespFileHandler.resubmitEventHandler: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length + ",instructionType=" + paramEventInfoArray._array[0].InstructionType);
    eventHandler(paramInt, paramEventInfoArray, paramFFSConnectionHolder);
    FFSDebug.log("=== RPPSRespFileHandler.resubmitEventHandler: end");
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.RPPSRespFileHandler
 * JD-Core Version:    0.7.0.1
 */