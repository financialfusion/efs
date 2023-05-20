package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.lang.reflect.Method;

public class MetavanteRespFileHandler
  implements DBConsts, FFSConst, BPWResource
{
  private boolean h8;
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("MetavanteRespFileHandler.eventHandler: begin, eventSeq=" + paramInt, 6);
    if (paramInt == 0) {
      this.h8 = false;
    } else if (paramInt == 1) {
      this.h8 = true;
    } else if (paramInt == 2)
    {
      if (this.h8) {
        try
        {
          Class localClass = Class.forName("com.ffusion.ffs.bpw.fulfill.metavante.MetavanteRespFileHandlerImpl");
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
    FFSDebug.log("MetavanteRespFileHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== MetavanteRespFileHandler.resubmitEventHandler: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length + ",instructionType=" + paramEventInfoArray._array[0].InstructionType);
    eventHandler(paramInt, paramEventInfoArray, paramFFSConnectionHolder);
    FFSDebug.log("=== MetavanteRespFileHandler.resubmitEventHandler: end");
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.MetavanteRespFileHandler
 * JD-Core Version:    0.7.0.1
 */