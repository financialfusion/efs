package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.db.DBInstructionType;
import com.ffusion.ffs.bpw.db.ExternalTransferAccount;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.IExternalTransferAdapter;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.util.beans.LocalizableString;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExternalTransferHandler
  implements DBConsts, FFSConst, BPWResource
{
  private String jt = "com.ffusion.ffs.bpw.fulfill.achadapter.ExternalTransferAdapter";
  private IExternalTransferAdapter js = null;
  private boolean jq = false;
  private int jr;
  
  public ExternalTransferHandler()
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.jr = localPropertyConfig.LogLevel;
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str = "ExternalTransferHandler.eventHandler: ";
    FFSDebug.log(str + " begin, eventSeq=" + paramInt, 6);
    if (paramInt == 0) {
      firstEventHandler();
    } else if (paramInt == 2) {
      lastEventHandler(paramFFSConnectionHolder, paramEventInfoArray, false, false);
    }
    FFSDebug.log(str + " end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== ExternalTransferHandler.resubmitEventHandler: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length + ",instructionType=" + paramEventInfoArray._array[0].InstructionType);
    if (paramInt == 0)
    {
      firstEventHandler();
    }
    else if (paramInt == 2)
    {
      boolean bool1 = false;
      boolean bool2 = false;
      if (paramEventInfoArray._array[0].ScheduleFlag == -1) {
        bool1 = true;
      } else {
        bool2 = true;
      }
      lastEventHandler(paramFFSConnectionHolder, paramEventInfoArray, bool1, bool2);
    }
    FFSDebug.log("=== ExternalTransferHandler.resubmitEventHandler: end");
  }
  
  public void firstEventHandler()
    throws Exception
  {
    this.jq = true;
    Class localClass = Class.forName(this.jt);
    this.js = ((IExternalTransferAdapter)localClass.newInstance());
    this.js.start();
  }
  
  public void lastEventHandler(FFSConnectionHolder paramFFSConnectionHolder, EventInfoArray paramEventInfoArray, boolean paramBoolean1, boolean paramBoolean2)
    throws Exception
  {
    if (this.jq)
    {
      updateMaturedExtAccountPrenoteStatus(paramFFSConnectionHolder, paramEventInfoArray._array[0].FIId);
      try
      {
        if ((paramEventInfoArray != null) && (paramEventInfoArray._array != null) && (paramEventInfoArray._array[0] != null)) {
          if ((paramEventInfoArray._array[0].InstructionType != null) && (paramEventInfoArray._array[0].InstructionType.compareTo("ETFTRN") == 0))
          {
            if (paramEventInfoArray._array[0].cutOffId != null)
            {
              CutOffInfo localCutOffInfo = new CutOffInfo();
              localCutOffInfo.setCutOffId(paramEventInfoArray._array[0].cutOffId);
              localCutOffInfo = DBInstructionType.getCutOffById(paramFFSConnectionHolder, localCutOffInfo);
              if (localCutOffInfo.getStatusCode() == 0) {
                this.js.processOneCutOff(paramFFSConnectionHolder, localCutOffInfo, paramEventInfoArray._array[0].FIId, paramEventInfoArray._array[0].processId, paramEventInfoArray._array[0].createEmptyFile, paramBoolean1, paramBoolean2);
              }
            }
            else
            {
              this.js.processRunNow(paramFFSConnectionHolder, paramEventInfoArray._array[0].FIId, paramEventInfoArray._array[0].processId, paramEventInfoArray._array[0].createEmptyFile, paramBoolean1, paramBoolean2);
            }
          }
          else
          {
            FFSDebug.log("Invalid InstructionType = " + paramEventInfoArray._array[0].InstructionType, 0);
            FFSDebug.log("This instruction is skipped. Id: " + paramEventInfoArray._array[0].InstructionID, 0);
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        throw localException;
      }
    }
  }
  
  public void updateMaturedExtAccountPrenoteStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    String str1 = "ExternalTransferHandler.updateMaturedExtAccountPrenoteStatus: ";
    FFSDebug.log(str1 + "begins", 6);
    Calendar localCalendar = Calendar.getInstance();
    SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyyMMdd");
    String str2 = localSimpleDateFormat1.format(localCalendar.getTime());
    int i = Integer.parseInt(str2);
    for (int j = 0; j < 6; j++) {
      i = SmartCalendar.getBusinessDay(paramString, i, false);
    }
    String str3 = new Integer(i).toString();
    Date localDate = localSimpleDateFormat1.parse(str3);
    SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    localSimpleDateFormat2.setLenient(false);
    String str4 = localSimpleDateFormat2.format(localDate);
    Object localObject = null;
    if (this.jr >= 4) {}
    int k = 0;
    if (localObject != null) {
      k = localObject.length;
    }
    if ((this.jr >= 4) && (k > 0)) {
      for (int m = 0; m < k; m++)
      {
        LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizableMessage(1065, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
        ExternalTransferAccount.logExtTransferAccountTransAuditLog(paramFFSConnectionHolder, localObject[m], localLocalizableString, "Update prenote status of an External Account", 5251);
      }
    }
    FFSDebug.log(str1 + " ends", 6);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.ExternalTransferHandler
 * JD-Core Version:    0.7.0.1
 */