package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.lang.reflect.Method;

public class ORCCRespFileHandler
  implements DBConsts, FFSConst, BPWResource
{
  private boolean h2;
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("ORCCRespFileHandler.eventHandler: begin, eventSeq=" + paramInt, 6);
    if (paramInt == 0) {
      this.h2 = false;
    } else if (paramInt == 1) {
      this.h2 = true;
    } else if (paramInt == 2)
    {
      if (this.h2) {
        try
        {
          Class localClass = Class.forName("com.ffusion.ffs.bpw.fulfill.orcc.ORCCRespFileHandlerImpl");
          Object localObject = localClass.newInstance();
          Class[] arrayOfClass = { FFSConnectionHolder.class };
          Object[] arrayOfObject = { paramFFSConnectionHolder };
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
    FFSDebug.log("ORCCRespFileHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== ORCCRespFileHandler.resubmitEventHandler: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length + ",instructionType=" + paramEventInfoArray._array[0].InstructionType);
    eventHandler(paramInt, paramEventInfoArray, paramFFSConnectionHolder);
    FFSDebug.log("=== ORCCRespFileHandler.resubmitEventHandler: end");
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.ORCCRespFileHandler
 * JD-Core Version:    0.7.0.1
 */