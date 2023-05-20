package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.ACHBatch;
import com.ffusion.ffs.bpw.db.ACHPayee;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.interfaces.ACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.ACHPayeeList;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;

public class ACHPayeeProcessor
  implements DBConsts, FFSConst, OFXConsts
{
  private int y = 1;
  
  public ACHPayeeProcessor()
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.y = localPropertyConfig.LogLevel;
  }
  
  public ACHPayeeInfo addACHPayee(ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    String str1 = " ACHPayeeProcessor.addACHPayee: ";
    FFSDebug.log(str1 + "start", 6);
    if (paramACHPayeeInfo == null)
    {
      paramACHPayeeInfo = new ACHPayeeInfo();
      paramACHPayeeInfo.setStatusCode(16000);
      paramACHPayeeInfo.setStatusMsg("PAYEEINFO:  is null");
      return paramACHPayeeInfo;
    }
    if (paramACHPayeeInfo.getManagedPayee() == null)
    {
      paramACHPayeeInfo.setStatusCode(23600);
      localObject1 = "Invalid managedPayee value";
      paramACHPayeeInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramACHPayeeInfo;
    }
    if ("N".compareTo(paramACHPayeeInfo.getManagedPayee()) == 0)
    {
      paramACHPayeeInfo.setStatusCode(23620);
      localObject1 = "Unable to add an unmanaged payee.";
      paramACHPayeeInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramACHPayeeInfo;
    }
    Object localObject1 = new FFSConnectionHolder();
    ACHPayeeInfo localACHPayeeInfo1 = null;
    String str2;
    try
    {
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      str2 = "***" + str1 + "failed:" + "Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable1, str2);
    }
    try
    {
      localACHPayeeInfo1 = a((FFSConnectionHolder)localObject1, paramACHPayeeInfo);
      if (localACHPayeeInfo1.getStatusCode() != 0) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      } else {
        ((FFSConnectionHolder)localObject1).conn.commit();
      }
      FFSDebug.log(str1 + "done", 6);
      ACHPayeeInfo localACHPayeeInfo2 = localACHPayeeInfo1;
      return localACHPayeeInfo2;
    }
    catch (Throwable localThrowable2)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      str2 = "***" + str1 + "failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable2, str2);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
  }
  
  private ACHPayeeInfo a(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    String str = "ACHPayeeProcessor.addACHPayeeTX: ";
    FFSDebug.log(str + "start", 6);
    ACHPayeeInfo localACHPayeeInfo = null;
    try
    {
      localACHPayeeInfo = addACHPayeeOnlyTX(paramFFSConnectionHolder, paramACHPayeeInfo);
      if ((localACHPayeeInfo.getStatusCode() == 0) && (localACHPayeeInfo.getDoPrenote() == true))
      {
        ACHBatchProcessor localACHBatchProcessor = new ACHBatchProcessor();
        localObject = localACHBatchProcessor.createPrenoteBatchTX(paramFFSConnectionHolder, paramACHPayeeInfo);
        if ((localObject != null) && (((ACHBatchInfo)localObject).getStatusCode() != 0))
        {
          localACHPayeeInfo.setStatusCode(((ACHBatchInfo)localObject).getStatusCode());
          localACHPayeeInfo.setStatusMsg(((ACHBatchInfo)localObject).getStatusMsg());
        }
        localACHPayeeInfo.setPrenoteMaturedDate(ACHPayee.getPrenoteMaturedDate(paramFFSConnectionHolder, localACHPayeeInfo));
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject = "***" + str + "failed:" + localThrowable.toString() + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject, 0);
      throw new FFSException(localThrowable, (String)localObject);
    }
    return localACHPayeeInfo;
  }
  
  public ACHPayeeInfo addACHPayeeOnlyTX(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    String str1 = "ACHPayeeProcessor.addACHPayeeOnlyTX: ";
    FFSDebug.log(str1 + "start", 6);
    ACHPayeeInfo localACHPayeeInfo = null;
    try
    {
      if (!paramACHPayeeInfo.checkAndSetPrenoteStatuses())
      {
        FFSDebug.log(str1 + paramACHPayeeInfo.getStatusMsg(), 0);
        return paramACHPayeeInfo;
      }
      localACHPayeeInfo = ACHPayee.create(paramFFSConnectionHolder, paramACHPayeeInfo);
      if (localACHPayeeInfo.getStatusCode() == 0) {
        a(paramFFSConnectionHolder, paramACHPayeeInfo, paramACHPayeeInfo.getSubmittedBy(), "A new ACH Payee " + paramACHPayeeInfo.getPayeeName() + " has been added in BPTW ", 4231);
      }
      return localACHPayeeInfo;
    }
    catch (Throwable localThrowable)
    {
      String str2 = "***" + str1 + "failed:" + localThrowable.toString() + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  public ACHPayeeInfo[] addACHPayee(ACHPayeeInfo[] paramArrayOfACHPayeeInfo)
    throws FFSException
  {
    String str1 = "ACHPayeeProcessor.addACHPayee (Multiple): ";
    FFSDebug.log(str1 + "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    Object localObject1;
    Object localObject3;
    if ((paramArrayOfACHPayeeInfo == null) || (paramArrayOfACHPayeeInfo.length == 0))
    {
      String str2 = "***" + str1 + " Null or Empty ACHPayeeInfo Array passed";
      FFSDebug.log(str2, 0);
      localObject1 = new ACHPayeeInfo();
      ((ACHPayeeInfo)localObject1).setStatusCode(16000);
      ((ACHPayeeInfo)localObject1).setStatusMsg("ACHPayeeInfo  is null");
      localObject3 = new ACHPayeeInfo[] { localObject1 };
      return localObject3;
    }
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      localObject1 = "***" + str1 + "failed: Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    try
    {
      ACHPayeeInfo localACHPayeeInfo = null;
      for (int i = 0; i < paramArrayOfACHPayeeInfo.length; i++)
      {
        if (paramArrayOfACHPayeeInfo[i] == null)
        {
          localObject3 = "***" + str1 + "failed: Null ACHPayeeInfo Object passed";
          FFSDebug.log((String)localObject3, 0);
          localACHPayeeInfo = new ACHPayeeInfo();
          localACHPayeeInfo.setStatusCode(16000);
          localACHPayeeInfo.setStatusMsg("ACHPayeeInfo  is null");
        }
        else
        {
          localACHPayeeInfo = a(localFFSConnectionHolder, paramArrayOfACHPayeeInfo[i]);
        }
        paramArrayOfACHPayeeInfo[i] = localACHPayeeInfo;
      }
      localFFSConnectionHolder.conn.commit();
      FFSDebug.log(str1 + "done", 6);
      localObject2 = paramArrayOfACHPayeeInfo;
      return localObject2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      Object localObject2 = "***" + str1 + "failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException(localThrowable2, (String)localObject2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public ACHPayeeInfo modACHPayeeInfo(ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    String str = "ACHPayeeProcessor.modACHPayee ";
    FFSDebug.log(str, "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    ACHPayeeInfo localACHPayeeInfo1 = null;
    if (paramACHPayeeInfo == null)
    {
      paramACHPayeeInfo = new ACHPayeeInfo();
      paramACHPayeeInfo.setStatusCode(16000);
      paramACHPayeeInfo.setStatusMsg("PAYEEINFO:  is null");
      return paramACHPayeeInfo;
    }
    ACHPayeeInfo localACHPayeeInfo2 = getACHPayeeInfo(paramACHPayeeInfo.getPayeeID());
    if (localACHPayeeInfo2.getStatusCode() != 0)
    {
      localACHPayeeInfo1.setStatusCode(localACHPayeeInfo2.getStatusCode());
      localACHPayeeInfo1.setStatusMsg(localACHPayeeInfo2.getStatusMsg());
      return localACHPayeeInfo1;
    }
    Object localObject2;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      localObject2 = str + "failed:" + "Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException(localThrowable1, (String)localObject2);
    }
    try
    {
      localACHPayeeInfo1 = modACHPayeeInfoOnlyTX(localFFSConnectionHolder, paramACHPayeeInfo);
      if (localACHPayeeInfo1.getStatusCode() != 0)
      {
        FFSDebug.log(str, "Rollback transaction", 6);
        localFFSConnectionHolder.conn.rollback();
        localObject1 = str + "failed:" + localACHPayeeInfo1.getStatusMsg();
        FFSDebug.log((String)localObject1, 0);
        localFFSConnectionHolder.conn.rollback();
        localObject2 = localACHPayeeInfo1;
        return localObject2;
      }
      Object localObject1 = new ACHBatchProcessor();
      if (!localACHPayeeInfo1.getDoPrenote())
      {
        if (localACHPayeeInfo2.getDoPrenote() == true)
        {
          FFSDebug.log(str, "Prenote is not required anymore. Canceling previous created prenote batch.", 6);
          ((ACHBatchProcessor)localObject1).cancelPrenoteBatchTX(localFFSConnectionHolder, paramACHPayeeInfo);
        }
      }
      else if (!localACHPayeeInfo2.getDoPrenote())
      {
        FFSDebug.log(str, "Prenote is required this time. Creating prenote batch.", 6);
        localObject2 = ((ACHBatchProcessor)localObject1).createPrenoteBatchTX(localFFSConnectionHolder, paramACHPayeeInfo);
        if ((localObject2 != null) && (((ACHBatchInfo)localObject2).getStatusCode() != 0))
        {
          localACHPayeeInfo1.setStatusCode(((ACHBatchInfo)localObject2).getStatusCode());
          localACHPayeeInfo1.setStatusMsg(((ACHBatchInfo)localObject2).getStatusMsg());
        }
      }
      else if (!localACHPayeeInfo2.comparePrenoteInfo(localACHPayeeInfo1))
      {
        FFSDebug.log(str, "Prenote info has been changed. Canceling previous created prenote batch.", 6);
        ((ACHBatchProcessor)localObject1).cancelPrenoteBatchTX(localFFSConnectionHolder, paramACHPayeeInfo);
        FFSDebug.log(str, "Prenote info has been changed. Creating new prenote batch.", 6);
        localObject2 = ((ACHBatchProcessor)localObject1).createPrenoteBatchTX(localFFSConnectionHolder, paramACHPayeeInfo);
        if ((localObject2 != null) && (((ACHBatchInfo)localObject2).getStatusCode() != 0))
        {
          localACHPayeeInfo1.setStatusCode(((ACHBatchInfo)localObject2).getStatusCode());
          localACHPayeeInfo1.setStatusMsg(((ACHBatchInfo)localObject2).getStatusMsg());
        }
      }
      if (localACHPayeeInfo1.getStatusCode() == 0)
      {
        FFSDebug.log(str, "Commit transaction", 6);
        localFFSConnectionHolder.conn.commit();
      }
      else
      {
        FFSDebug.log(str, "Rollback transaction", 6);
        localFFSConnectionHolder.conn.rollback();
        localObject2 = str + "failed:" + localACHPayeeInfo1.getStatusMsg();
        FFSDebug.log((String)localObject2, 0);
      }
    }
    catch (Throwable localThrowable2)
    {
      FFSDebug.log(str, "Doing RollBack", 6);
      localFFSConnectionHolder.conn.rollback();
      localObject2 = str + "failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException(localThrowable2, (String)localObject2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localACHPayeeInfo1;
  }
  
  public ACHPayeeInfo modACHPayeeInfoOnlyTX(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    String str1 = "ACHPayeeProcessor.modACHPayee: ";
    FFSDebug.log(str1, "start", 6);
    ACHPayeeInfo localACHPayeeInfo = null;
    try
    {
      if (!paramACHPayeeInfo.checkAndSetPrenoteStatuses())
      {
        FFSDebug.log(str1 + paramACHPayeeInfo.getStatusMsg(), 0);
        return paramACHPayeeInfo;
      }
      localACHPayeeInfo = ACHPayee.modify(paramFFSConnectionHolder, paramACHPayeeInfo);
      if (localACHPayeeInfo.getStatusCode() == 0) {
        a(paramFFSConnectionHolder, paramACHPayeeInfo, paramACHPayeeInfo.getSubmittedBy(), "An ACH Payee " + localACHPayeeInfo.getPayeeID() + " has been modified in BPTW", 4232);
      }
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log(str1, "Doing RollBack", 6);
      String str2 = str1 + "failed:" + localThrowable.toString() + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable, str2);
    }
    return localACHPayeeInfo;
  }
  
  public ACHPayeeInfo canACHPayeeInfo(ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    String str = "ACHPayeeProcessor.canACHPayeeInfo: ";
    FFSDebug.log(str + "start. PayeeId: " + paramACHPayeeInfo.getPayeeID(), 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    ACHPayeeInfo localACHPayeeInfo = null;
    Object localObject1;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      localObject1 = "***" + str + "failed:" + "Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    try
    {
      ACHBatchInfo[] arrayOfACHBatchInfo = ACHBatch.getActiveACHBatchesByPayeeId(localFFSConnectionHolder, paramACHPayeeInfo.getPayeeID(), false);
      Object localObject2;
      if (!a(arrayOfACHBatchInfo))
      {
        paramACHPayeeInfo.setStatusCode(23460);
        paramACHPayeeInfo.setStatusMsg("ACHPayee still has active batches");
        localObject1 = str + "ACHPayee still has active batches";
        FFSDebug.log((String)localObject1, 0);
        localObject2 = paramACHPayeeInfo;
        return localObject2;
      }
      arrayOfACHBatchInfo = ACHBatch.getActiveACHBatchesByPayeeId(localFFSConnectionHolder, paramACHPayeeInfo.getPayeeID(), true);
      if ((arrayOfACHBatchInfo != null) && (arrayOfACHBatchInfo.length > 0))
      {
        paramACHPayeeInfo.setStatusCode(23460);
        paramACHPayeeInfo.setStatusMsg("ACHPayee still has active batches");
        localObject1 = str + "ACHPayee still has active batches";
        FFSDebug.log((String)localObject1, 0);
        localObject2 = paramACHPayeeInfo;
        return localObject2;
      }
      localObject1 = new ACHBatchProcessor();
      ((ACHBatchProcessor)localObject1).cancelPrenoteBatchTX(localFFSConnectionHolder, paramACHPayeeInfo);
      localACHPayeeInfo = canACHPayeeInfoOnlyTX(localFFSConnectionHolder, paramACHPayeeInfo);
      if (localACHPayeeInfo.getStatusCode() == 0)
      {
        FFSDebug.log(str, "Commit transaction", 6);
        localFFSConnectionHolder.conn.commit();
      }
      else
      {
        FFSDebug.log(str, "Rollback transaction", 6);
        localFFSConnectionHolder.conn.rollback();
        localObject2 = str + "failed:" + localACHPayeeInfo.getStatusMsg();
        FFSDebug.log((String)localObject2, 0);
      }
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      localObject1 = "***" + str + "failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable2, (String)localObject1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str + "done", 6);
    return localACHPayeeInfo;
  }
  
  public ACHPayeeInfo canACHPayeeInfoOnlyTX(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    String str1 = "ACHPayeeProcessor.canACHPayeeInfoOnly: ";
    FFSDebug.log(str1 + "start", 6);
    ACHPayeeInfo localACHPayeeInfo1 = null;
    ACHPayeeInfo localACHPayeeInfo2 = null;
    try
    {
      if (this.y >= 3) {
        localACHPayeeInfo2 = ACHPayee.getACHPayeeInfo(paramFFSConnectionHolder, paramACHPayeeInfo.getPayeeID());
      }
      localACHPayeeInfo1 = ACHPayee.delete(paramFFSConnectionHolder, paramACHPayeeInfo);
      if (localACHPayeeInfo1.getStatusCode() == 0) {
        a(paramFFSConnectionHolder, localACHPayeeInfo2, paramACHPayeeInfo.getSubmittedBy(), "An ACH Payee " + localACHPayeeInfo1.getPayeeID() + " has been canceled in BPTW", 4233);
      }
    }
    catch (Throwable localThrowable)
    {
      String str2 = "***" + str1 + "failed:" + localThrowable.toString() + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable, str2);
    }
    FFSDebug.log(str1 + "done", 6);
    return localACHPayeeInfo1;
  }
  
  public ACHPayeeInfo getACHPayeeInfo(String paramString)
    throws FFSException
  {
    FFSDebug.log("ACHPayeeProcessor.getACHPayeeInfo start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    ACHPayeeInfo localACHPayeeInfo1 = null;
    String str;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str = "***ACHPayeeProcessor.getACHPayeeInfo failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable1, str);
    }
    try
    {
      localACHPayeeInfo1 = ACHPayee.getACHPayeeInfo(localFFSConnectionHolder, paramString, true);
      localFFSConnectionHolder.conn.commit();
      ACHPayeeInfo localACHPayeeInfo2 = localACHPayeeInfo1;
      return localACHPayeeInfo2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str = "***ACHPayeeProcessor.getACHPayeeInfo failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable2, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public ACHPayeeInfo activatePayee(String paramString)
    throws FFSException
  {
    FFSDebug.log("ACHPayeeProcessor.activatePayee start", 6);
    ACHPayeeInfo localACHPayeeInfo1 = null;
    if (paramString == null)
    {
      localACHPayeeInfo1 = new ACHPayeeInfo();
      localACHPayeeInfo1.setStatusCode(16000);
      localObject1 = "payeeId  is null";
      localACHPayeeInfo1.setStatusMsg((String)localObject1);
      FFSDebug.log("ACHPayeeProcessor.activatePayee, " + (String)localObject1);
      return localACHPayeeInfo1;
    }
    Object localObject1 = new FFSConnectionHolder();
    String str1;
    try
    {
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable)
    {
      str1 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str1, 0);
      throw new FFSException(localThrowable, "Could not get connection");
    }
    try
    {
      localACHPayeeInfo1 = ACHPayee.activatePayee(paramString, (FFSConnectionHolder)localObject1);
      if (localACHPayeeInfo1.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        ACHPayeeInfo localACHPayeeInfo2 = localACHPayeeInfo1;
        return localACHPayeeInfo2;
      }
      a((FFSConnectionHolder)localObject1, localACHPayeeInfo1, localACHPayeeInfo1.getSubmittedBy(), "Activate an ACH Payee.", 4232);
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (Exception localException)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      str1 = "*** ACHPayeeProcessor.activatePayee failed: ";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException, str1);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
    FFSDebug.log("ACHPayeeProcessor.activatePayee: done", 6);
    return localACHPayeeInfo1;
  }
  
  public ACHPayeeList getACHPayeeList(ACHPayeeList paramACHPayeeList)
    throws FFSException
  {
    String str = "ACHPayeeProcessor.getACHPayeeList: ";
    FFSDebug.log(str + "start...", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    Object localObject1;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      localObject1 = "***ACHPayeeProcessor.getACHPayeesByCustomer failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    try
    {
      ACHPayeeList localACHPayeeList = ACHPayee.getACHPayeeList(localFFSConnectionHolder, paramACHPayeeList);
      localFFSConnectionHolder.conn.commit();
      localObject1 = localACHPayeeList;
      return localObject1;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      localObject1 = "***" + str + " failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable2, (String)localObject1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
      FFSDebug.log(str + "end.", 6);
    }
  }
  
  public ACHPayeeInfo updateACHPayeePrenoteStatus(ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    String str1 = "ACHPayeeProcessor.updateACHPayeePrenoteStatus: ";
    FFSDebug.log(str1 + "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str2;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable)
    {
      str2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str1 + str2, 0);
      throw new FFSException(localThrowable, "Could not get connection");
    }
    try
    {
      paramACHPayeeInfo = ACHPayee.updateACHPayeePrenoteStatus(localFFSConnectionHolder, paramACHPayeeInfo);
      if (paramACHPayeeInfo.getStatusCode() == 0) {
        a(localFFSConnectionHolder, paramACHPayeeInfo, paramACHPayeeInfo.getSubmittedBy(), "An ACH Prenote Payee " + paramACHPayeeInfo.getPayeeID() + " has been added in BPTW", 4232);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "***" + str1 + "failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str1 + str2, 0);
      throw new FFSException(localException, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1 + "done", 6);
    return paramACHPayeeInfo;
  }
  
  public ACHPayeeInfo updateACHPayeePrenoteSubmitDate(ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    String str1 = "ACHPayeeProcessor.updateACHPayeePrenoteSubmitDate: ";
    FFSDebug.log(str1 + "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str2;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable)
    {
      str2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str1 + str2, 0);
      throw new FFSException(localThrowable, "Could not get connection");
    }
    try
    {
      paramACHPayeeInfo = ACHPayee.updateACHPayeePrenoteSubmitDate(localFFSConnectionHolder, paramACHPayeeInfo);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "***" + str1 + "failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str1 + str2, 0);
      throw new FFSException(localException, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1 + "done", 6);
    return paramACHPayeeInfo;
  }
  
  private static boolean a(ACHBatchInfo[] paramArrayOfACHBatchInfo)
    throws FFSException
  {
    if (paramArrayOfACHBatchInfo != null)
    {
      int i = paramArrayOfACHBatchInfo.length;
      for (int j = 0; j < i; j++) {
        if (!paramArrayOfACHBatchInfo[j].getBatchCategory().equals("ACH_BATCH_PRENOTE")) {
          return false;
        }
      }
    }
    return true;
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo, String paramString1, String paramString2, int paramInt)
    throws FFSException
  {
    if (this.y >= 3) {
      ACHPayee.doTransAuditLog(paramFFSConnectionHolder, paramACHPayeeInfo, paramString1, paramString2, paramInt);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.ACHPayeeProcessor
 * JD-Core Version:    0.7.0.1
 */