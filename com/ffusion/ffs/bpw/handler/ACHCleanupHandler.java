package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.db.ACHBatch;
import com.ffusion.ffs.bpw.db.ACHFile;
import com.ffusion.ffs.bpw.db.ACHPayee;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.interfaces.ACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.ACHFileInfo;
import com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ACHCleanupHandler
  implements DBConsts, FFSConst, BPWResource
{
  private PropertyConfig jQ = null;
  private int jP;
  
  public ACHCleanupHandler()
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.jP = localPropertyConfig.LogLevel;
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    if (paramInt == 2)
    {
      this.jQ = ((PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG"));
      String str1 = this.jQ.otherProperties.getProperty("bpw.ach.cleanup.interval", "30");
      int i = 30;
      if ((str1 == null) || (str1.trim().length() == 0))
      {
        String str2 = "No wait interval specified default value (30 minutes) will be used";
        FFSDebug.log(str2);
        i = 30;
      }
      else
      {
        try
        {
          i = Integer.parseInt(str1);
        }
        catch (Throwable localThrowable)
        {
          i = 30;
        }
      }
      i += 1440;
      String str3 = FFSUtil.getCutOffTime(i);
      FFSDebug.log("ACHCleanupHandler.eventHandler: begin cleanIncompleteBatches", 6);
      f(paramFFSConnectionHolder, str3);
      g(paramFFSConnectionHolder, str3);
      FFSDebug.log("ACHCleanupHandler.eventHandler: cleanIncompleteBatches end", 6);
      cleanIncompleteACHFiles(paramFFSConnectionHolder, str3);
      String str4 = paramEventInfoArray._array[0].FIId;
      updateMaturedPayeePrenoteStatus(paramFFSConnectionHolder, str4);
    }
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("ACHCleanupHandler.resubmitEventHandler: begin", 6);
    eventHandler(paramInt, paramEventInfoArray, paramFFSConnectionHolder);
    FFSDebug.log("ACHCleanupHandler.resubmitEventHandler: end", 6);
  }
  
  private void f(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    jdMethod_if(paramFFSConnectionHolder, paramString, false);
  }
  
  private void g(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    jdMethod_if(paramFFSConnectionHolder, paramString, true);
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    ACHBatchInfo[] arrayOfACHBatchInfo = ACHBatch.getIncompleteBatches(paramFFSConnectionHolder, paramString, paramBoolean);
    if (arrayOfACHBatchInfo != null)
    {
      int i = arrayOfACHBatchInfo.length;
      for (int j = 0; j < i; j++)
      {
        ACHBatch.updateACHBatchStatus(arrayOfACHBatchInfo[j], "CANCELEDON", paramFFSConnectionHolder, paramBoolean);
        String str = "Cancel incomplete batch";
        if (paramBoolean) {
          str = "Cancel incomplete recurring batch";
        }
        jdMethod_if(paramFFSConnectionHolder, arrayOfACHBatchInfo[j], str);
      }
    }
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, String paramString)
    throws FFSException
  {
    if (this.jP >= 4) {
      ACHBatch.doTransAuditLog(paramFFSConnectionHolder, paramACHBatchInfo, paramString, paramACHBatchInfo.getSubmittedBy(), 4304);
    }
  }
  
  public void cleanIncompleteACHFiles(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    FFSDebug.log("ACHCleanupHandler.eventHandler: begin cleanIncompleteACHFiles", 6);
    ACHFileInfo[] arrayOfACHFileInfo = null;
    if (this.jP >= 4) {
      arrayOfACHFileInfo = ACHFile.getACHFileInfoToBeDeleted(paramFFSConnectionHolder, true, paramString);
    }
    ACHFile.cancelIncompleteACHFiles(paramFFSConnectionHolder, paramString);
    if ((this.jP >= 4) && (arrayOfACHFileInfo != null) && (arrayOfACHFileInfo.length > 0)) {
      for (int i = 0; i < arrayOfACHFileInfo.length; i++) {
        ACHFile.doTransAuditLog(paramFFSConnectionHolder, arrayOfACHFileInfo[i], arrayOfACHFileInfo[i].getSubmittedBy(), "Cancel an incomplete ACHFile", 4304);
      }
    }
    FFSDebug.log("ACHCleanupHandler.eventHandler: cleanIncompleteACHFiles end", 6);
  }
  
  public void updateMaturedPayeePrenoteStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    String str1 = "ACHCleanupHandler.updateMaturedPayeePrenoteStatus: ";
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
    SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("yyyy/MM/dd");
    localSimpleDateFormat2.setLenient(false);
    String str4 = localSimpleDateFormat2.format(localDate);
    ACHPayeeInfo[] arrayOfACHPayeeInfo = null;
    if (this.jP >= 4) {
      arrayOfACHPayeeInfo = ACHPayee.getMaturedACHPayeeInfo(paramFFSConnectionHolder, str3);
    }
    int k = ACHPayee.updateMaturedACHPayeePrenoteStatus(paramFFSConnectionHolder, str4);
    FFSDebug.log(str1 + "matured ACH Payee prenotes: " + k, 6);
    if ((this.jP >= 4) && (arrayOfACHPayeeInfo != null) && (arrayOfACHPayeeInfo.length > 0)) {
      for (int m = 0; m < arrayOfACHPayeeInfo.length; m++) {
        ACHPayee.doTransAuditLog(paramFFSConnectionHolder, arrayOfACHPayeeInfo[m], arrayOfACHPayeeInfo[m].getSubmittedBy(), "Update prenote status of an ACHPayee", 4304);
      }
    }
    FFSDebug.log(str1 + " ends", 6);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.ACHCleanupHandler
 * JD-Core Version:    0.7.0.1
 */