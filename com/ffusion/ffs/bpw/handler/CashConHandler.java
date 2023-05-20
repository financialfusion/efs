package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.config.BPWAdmin;
import com.ffusion.ffs.bpw.db.CashCon;
import com.ffusion.ffs.bpw.db.DBInstructionType;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CCLocationInfo;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.ICashConAdapter;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CashConHandler
  implements DBConsts, FFSConst, BPWResource
{
  private String in = "com.ffusion.ffs.bpw.fulfill.achadapter.CashConAdapter";
  private ICashConAdapter im = null;
  private boolean ii = false;
  private BPWAdmin ik;
  private ICashConAdapter il;
  private int ij;
  
  public CashConHandler()
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.ij = localPropertyConfig.LogLevel;
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str = "CashConHandler.eventHandler: ";
    FFSDebug.log(str + " begin, eventSeq=" + paramInt, 6);
    if (paramInt == 0) {
      Y();
    } else if (paramInt == 2) {
      a(paramFFSConnectionHolder, paramEventInfoArray, false, false);
    }
    FFSDebug.log("CashConHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== CashConHandler.resubmitEventHandler: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length + ",instructionType=" + paramEventInfoArray._array[0].InstructionType);
    if (paramInt == 0)
    {
      Y();
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
      a(paramFFSConnectionHolder, paramEventInfoArray, bool1, bool2);
    }
    FFSDebug.log("=== CashConHandler.resubmitEventHandler: end");
  }
  
  private void Y()
    throws Exception
  {
    this.ii = true;
    Class localClass = Class.forName(this.in);
    this.il = ((ICashConAdapter)localClass.newInstance());
    this.il.start();
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, EventInfoArray paramEventInfoArray, boolean paramBoolean1, boolean paramBoolean2)
    throws Exception
  {
    if (this.ii)
    {
      updateMaturedLocationPrenoteStatus(paramFFSConnectionHolder, paramEventInfoArray._array[0].FIId);
      try
      {
        if ((paramEventInfoArray != null) && (paramEventInfoArray._array != null) && (paramEventInfoArray._array[0] != null)) {
          if ((paramEventInfoArray._array[0].InstructionType != null) && (paramEventInfoArray._array[0].InstructionType.compareTo("CASHCONTRN") == 0))
          {
            if (paramEventInfoArray._array[0].cutOffId != null)
            {
              CutOffInfo localCutOffInfo = new CutOffInfo();
              localCutOffInfo.setCutOffId(paramEventInfoArray._array[0].cutOffId);
              localCutOffInfo = DBInstructionType.getCutOffById(paramFFSConnectionHolder, localCutOffInfo);
              if (localCutOffInfo.getStatusCode() == 0) {
                this.il.processOneCutOff(paramFFSConnectionHolder, localCutOffInfo, paramEventInfoArray._array[0].FIId, paramEventInfoArray._array[0].processId, paramEventInfoArray._array[0].createEmptyFile, paramBoolean1, paramBoolean2);
              }
            }
            else
            {
              this.il.processRunNow(paramFFSConnectionHolder, paramEventInfoArray._array[0].FIId, paramEventInfoArray._array[0].processId, paramEventInfoArray._array[0].createEmptyFile, paramBoolean1, paramBoolean2);
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
        FFSDebug.log("***CashConHandler.lastEventHandler: Exception: " + localException.toString(), 0);
        throw localException;
      }
    }
  }
  
  public void updateMaturedLocationPrenoteStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    String str1 = "CashConHandler.updateMaturedLocationPrenoteStatus: ";
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
    CCLocationInfo[] arrayOfCCLocationInfo = null;
    if (this.ij >= 4) {
      arrayOfCCLocationInfo = CashCon.getMaturedDepositLocationInfo(paramFFSConnectionHolder, str3);
    }
    int k = CashCon.updateMaturedDepositLocationPrenoteStatus(paramFFSConnectionHolder, str4);
    FFSDebug.log(str1 + "matured deposit location prenotes: " + k, 6);
    int m = 0;
    if (arrayOfCCLocationInfo != null) {
      m = arrayOfCCLocationInfo.length;
    }
    int n;
    if ((this.ij >= 4) && (m > 0)) {
      for (n = 0; n < m; n++) {
        CashCon.logCCLocationTransAuditLog(paramFFSConnectionHolder, arrayOfCCLocationInfo[n], "Update prenote status of a Location", 5350);
      }
    }
    arrayOfCCLocationInfo = null;
    if (this.ij >= 4) {
      arrayOfCCLocationInfo = CashCon.getMaturedDisbursementLocationInfo(paramFFSConnectionHolder, str3);
    }
    k = CashCon.updateMaturedDisbursementLocationPrenoteStatus(paramFFSConnectionHolder, str4);
    FFSDebug.log(str1 + "matured disbursement location prenotes: " + k, 6);
    m = 0;
    if (arrayOfCCLocationInfo != null) {
      m = arrayOfCCLocationInfo.length;
    }
    if ((this.ij >= 4) && (m > 0)) {
      for (n = 0; n < m; n++) {
        CashCon.logCCLocationTransAuditLog(paramFFSConnectionHolder, arrayOfCCLocationInfo[n], "Update prenote status of a Location", 5350);
      }
    }
    FFSDebug.log(str1 + " ends", 6);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.CashConHandler
 * JD-Core Version:    0.7.0.1
 */