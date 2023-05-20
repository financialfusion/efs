package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.lang.reflect.Method;

public class CheckFreeRespFileHandler
  implements DBConsts, FFSConst, BPWResource
{
  private boolean i8;
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CheckFreeRespFileHandler.eventHandler: begin, eventSeq=" + paramInt, 6);
    boolean bool = false;
    if (paramInt == 0) {
      this.i8 = false;
    } else if (paramInt == 1) {
      this.i8 = true;
    } else if (paramInt == 2)
    {
      if (this.i8) {
        try
        {
          Class localClass = Class.forName("com.ffusion.ffs.bpw.fulfill.checkfree.CheckFreeRespFileHandlerImpl");
          Object localObject = localClass.newInstance();
          Class[] arrayOfClass = { FFSConnectionHolder.class, Boolean.class };
          Object[] arrayOfObject = { paramFFSConnectionHolder, new Boolean(bool) };
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
    FFSDebug.log("CheckFreeRespFileHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== CheckFreeRespFileHandler.resubmitEventHandler: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length + ",instructionType=" + paramEventInfoArray._array[0].InstructionType);
    FFSDebug.log("WARNING STARTS: CheckFree RESPONSE FILES POSSIBLE ,DUPLICATE PROCESSING.BPW is about to start processing CheckFree response files for ,the second time. This may happen either as a crash recovery , that is executed automatically by BPW Server, or as a result of explicitly issuing a resubmit event instruction ,for CHECKFREE_RESPTRN schedule (Manual Crash Recovery). Please note that this process may result in processing some records for the second time");
    boolean bool = true;
    Class localClass = Class.forName("com.ffusion.ffs.bpw.fulfill.checkfree.CheckFreeRespFileHandlerImpl");
    Object localObject = localClass.newInstance();
    Class[] arrayOfClass = { FFSConnectionHolder.class, Boolean.class };
    Object[] arrayOfObject = { paramFFSConnectionHolder, new Boolean(bool) };
    Method localMethod = localClass.getMethod("processResponseFiles", arrayOfClass);
    localMethod.invoke(localObject, arrayOfObject);
    FFSDebug.log("WARNING ENDS: CheckFree RESPONSE FILES POSSIBLE ,DUPLICATE PROCESSING ENDS.");
    FFSDebug.log("=== CheckFreeRespFileHandler.resubmitEventHandler: end");
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.CheckFreeRespFileHandler
 * JD-Core Version:    0.7.0.1
 */