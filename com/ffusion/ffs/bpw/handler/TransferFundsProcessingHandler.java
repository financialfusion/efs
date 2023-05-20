package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.custimpl.transfers.TransferFundsProcessing;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.db.SQLConsts;
import com.ffusion.ffs.bpw.db.Transfer;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class TransferFundsProcessingHandler
  extends TransferFundsBaseHandler
{
  private TransferFundsProcessing iI = null;
  
  public TransferFundsProcessingHandler()
  {
    aa();
    this.iI = new TransferFundsProcessing();
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    a(paramInt, paramEventInfoArray, paramFFSConnectionHolder, false, "TransferFundsProcessingHandler.eventHandler:");
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    a(paramInt, paramEventInfoArray, paramFFSConnectionHolder, true, "TransferFundsProcessingHandler.resubmitEventHandler:");
  }
  
  protected void b(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    jdMethod_if(paramFFSConnectionHolder, false, 0, paramString);
  }
  
  protected void c(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    int i = Transfer.getMaxProcessNumberByProcessId(paramFFSConnectionHolder, this.iD);
    i++;
    jdMethod_if(paramFFSConnectionHolder, true, i, paramString);
    jdMethod_if(paramFFSConnectionHolder, false, i, paramString);
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean, int paramInt, String paramString)
    throws Exception
  {
    Connection localConnection = null;
    if (paramFFSConnectionHolder != null) {
      localConnection = paramFFSConnectionHolder.conn.getConnection();
    }
    if (localConnection == null) {
      return;
    }
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    PreparedStatement localPreparedStatement3 = null;
    ResultSet localResultSet = null;
    String str = null;
    TransferInfo[] arrayOfTransferInfo = null;
    try
    {
      str = DBConnCache.getNewBatchKey();
      DBConnCache.bind(str, paramFFSConnectionHolder);
      HashMap localHashMap = new HashMap();
      localHashMap.put("DBTransKey", str);
      if (paramBoolean)
      {
        localHashMap.put("ProcessId", this.iD);
        localHashMap.put("PossibleDuplicate", String.valueOf(Boolean.TRUE));
      }
      StringBuffer localStringBuffer = new StringBuffer();
      if ((paramBoolean) && (this.iB)) {
        localStringBuffer.append(SQLConsts.GET_RESUBMIT_TRANSFER_FOR_FILEBASED_SCHEDULE);
      } else if ((paramBoolean) && (!this.iB)) {
        localStringBuffer.append(SQLConsts.GET_RESUBMIT_TRANSFER_FOR_NONFILEBASED_ETFFUNDTRN);
      } else {
        localStringBuffer.append(SQLConsts.GET_TRANSFER_FOR_ETFFUNDTRN);
      }
      localStringBuffer.append(" order by ").append("TypeDetail");
      localPreparedStatement1 = localConnection.prepareStatement(localStringBuffer.toString());
      localPreparedStatement2 = localConnection.prepareStatement("UPDATE BPW_Transfer SET Status=? WHERE SrvrTId=?");
      localPreparedStatement3 = localConnection.prepareStatement("UPDATE BPW_Transfer SET ProcessNumber = ?, LastProcessId = ? WHERE SrvrTId=?");
      localObject1 = new Hashtable();
      j = 1;
      localObject2 = Z();
      LocalizableString localLocalizableString = null;
      while (j > 0)
      {
        j = 0;
        localPreparedStatement1.clearParameters();
        if (this.iz != 0) {
          localPreparedStatement1.setMaxRows(this.iz);
        }
        localPreparedStatement1.setString(1, this.iw);
        if ((paramBoolean) && (this.iB))
        {
          localPreparedStatement1.setString(2, this.iD);
          localPreparedStatement1.setInt(3, paramInt);
        }
        else if ((paramBoolean) && (!this.iB))
        {
          localPreparedStatement1.setString(2, this.iD);
        }
        else
        {
          localPreparedStatement1.setString(2, (String)localObject2);
        }
        localResultSet = localPreparedStatement1.executeQuery();
        ArrayList localArrayList = new ArrayList();
        while (localResultSet.next())
        {
          TransferInfo localTransferInfo = a(paramFFSConnectionHolder, localResultSet, localHashMap, paramString);
          localLocalizableString = BPWLocaleUtil.getLocalizableMessage(21310, new Object[] { localTransferInfo.getSrvrTId() }, "TRANSFER_MESSAGE");
          a(paramFFSConnectionHolder, localTransferInfo, 5252, localLocalizableString, paramString);
          localArrayList.add(localTransferInfo);
          j++;
        }
        if (j > 0)
        {
          int k = localArrayList.size();
          arrayOfTransferInfo = new TransferInfo[k];
          arrayOfTransferInfo = (TransferInfo[])localArrayList.toArray(arrayOfTransferInfo);
          for (int m = 0; m < k; m++)
          {
            localPreparedStatement3.clearParameters();
            localPreparedStatement3.setInt(1, paramInt);
            localPreparedStatement3.setString(2, this.iD);
            localPreparedStatement3.setString(3, arrayOfTransferInfo[m].getSrvrTId());
            localPreparedStatement3.executeUpdate();
          }
          localConnection.commit();
          for (m = 0; m < k; m++)
          {
            localPreparedStatement2.clearParameters();
            localPreparedStatement2.setString(1, "INFUNDSPROCESSING");
            localPreparedStatement2.setString(2, arrayOfTransferInfo[m].getSrvrTId());
            localPreparedStatement2.executeUpdate();
          }
          this.iI.processScheduledTransferFunds(arrayOfTransferInfo, (Hashtable)localObject1);
          localConnection.commit();
        }
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject1;
      int j;
      Object localObject2;
      localConnection.rollback();
      if ((arrayOfTransferInfo != null) && (arrayOfTransferInfo.length > 0))
      {
        int i = arrayOfTransferInfo.length;
        localObject1 = null;
        for (j = 0; j < i; j++)
        {
          localObject1 = BPWLocaleUtil.getMessage(21340, new String[] { arrayOfTransferInfo[j].getSrvrTId(), localThrowable.toString() }, "TRANSFER_MESSAGE");
          FFSDebug.log((String)localObject1, 0);
          if (this.iy)
          {
            localObject2 = BPWLocaleUtil.getLocalizableMessage(21340, new Object[] { arrayOfTransferInfo[j].getSrvrTId(), localThrowable.toString() }, "TRANSFER_MESSAGE");
            a(arrayOfTransferInfo[j], 5252, (ILocalizable)localObject2, paramString);
          }
        }
      }
      throw new FFSException(FFSDebug.stackTrace(localThrowable));
    }
    finally
    {
      if (localResultSet != null) {
        localResultSet.close();
      }
      if (localPreparedStatement1 != null) {
        localPreparedStatement1.close();
      }
      if (localPreparedStatement2 != null) {
        localPreparedStatement2.close();
      }
      if (localPreparedStatement3 != null) {
        localPreparedStatement3.close();
      }
      if (str != null) {
        DBConnCache.unbind(str);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.TransferFundsProcessingHandler
 * JD-Core Version:    0.7.0.1
 */