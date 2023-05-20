package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Wire;
import com.ffusion.ffs.bpw.db.WirePayee;
import com.ffusion.ffs.bpw.interfaces.BPWBankInfo;
import com.ffusion.ffs.bpw.interfaces.BPWHist;
import com.ffusion.ffs.bpw.interfaces.BPWPayeeList;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.ffs.bpw.interfaces.WirePayeeInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;

public class WirePayeeProcessor
  implements DBConsts, FFSConst, OFXConsts
{
  private int aI = 0;
  
  public WirePayeeProcessor()
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str = null;
    try
    {
      str = localPropertyConfig.otherProperties.getProperty("bpw.wire.audit");
      if (str == null) {
        this.aI = 0;
      } else {
        this.aI = Integer.parseInt(str);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("WireProcessor. Invalid Audit log level value", str, 0);
      this.aI = 0;
    }
  }
  
  public WirePayeeInfo addWirePayee(WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    FFSDebug.log("WirePayeeProcessor.addWirePayee start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    WirePayeeInfo localWirePayeeInfo1 = null;
    String str1 = null;
    String str2;
    if (paramWirePayeeInfo == null)
    {
      str2 = "***WirePayeeProcessor.addWirePayee failed: Null WirePayeeInfo Object passed";
      FFSDebug.log(str2, 0);
      paramWirePayeeInfo = new WirePayeeInfo();
      paramWirePayeeInfo.setStatusCode(16000);
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "WirePayeeInfo " }, "WIRE_MESSAGE"));
      return paramWirePayeeInfo;
    }
    str1 = paramWirePayeeInfo.getPayeeGroup();
    if ("UNMANAGED".equals(str1))
    {
      str2 = "***WirePayeeProcessor.addWirePayee failed:Unmanaged payee cannot be added as a separate entity.";
      FFSDebug.log(str2, 0);
      paramWirePayeeInfo.setStatusCode(19820);
      paramWirePayeeInfo.setStatusMsg("Unmanaged payee cannot be added as a separate entity.");
      return paramWirePayeeInfo;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    String str3;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      str3 = "***WirePayeeProcessor.addWirePayee failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable1, str3);
    }
    try
    {
      localWirePayeeInfo1 = WirePayee.create(localFFSConnectionHolder, paramWirePayeeInfo);
      if (localWirePayeeInfo1.getStatusCode() == 0)
      {
        if (this.aI >= 3) {
          logAudit(localFFSConnectionHolder, localWirePayeeInfo1, "Add");
        }
        localFFSConnectionHolder.conn.commit();
      }
      else
      {
        paramWirePayeeInfo.setStatusCode(localWirePayeeInfo1.getStatusCode());
        if (this.aI >= 1) {
          a(paramWirePayeeInfo, "Add", null);
        }
        localFFSConnectionHolder.conn.rollback();
      }
      WirePayeeInfo localWirePayeeInfo2 = localWirePayeeInfo1;
      return localWirePayeeInfo2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      if (this.aI >= 1) {
        a(paramWirePayeeInfo, "Add", "Add wire payee failed, unknown error occurred.");
      }
      str3 = "***WirePayeeProcessor.addWirePayee failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable2, str3);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public WirePayeeInfo[] addWirePayee(WirePayeeInfo[] paramArrayOfWirePayeeInfo)
    throws FFSException
  {
    FFSDebug.log("WirePayeeProcessor.addWirePayee (Multiple) start", 6);
    Object localObject;
    if ((paramArrayOfWirePayeeInfo == null) || (paramArrayOfWirePayeeInfo.length == 0))
    {
      String str = "***WirePayee.addWirePayee failed: Null or Empty WirePayeeInfo Array passed";
      FFSDebug.log(str, 0);
      localObject = new WirePayeeInfo();
      ((WirePayeeInfo)localObject).setStatusCode(16000);
      ((WirePayeeInfo)localObject).setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "WirePayeeInfo " }, "WIRE_MESSAGE"));
      WirePayeeInfo[] arrayOfWirePayeeInfo = { localObject };
      return arrayOfWirePayeeInfo;
    }
    try
    {
      for (int i = 0; i < paramArrayOfWirePayeeInfo.length; i++) {
        paramArrayOfWirePayeeInfo[i] = addWirePayee(paramArrayOfWirePayeeInfo[i]);
      }
      return paramArrayOfWirePayeeInfo;
    }
    catch (Throwable localThrowable)
    {
      localObject = "***WirePayeeProcessor.addWirePayee (Multiple) failed:" + localThrowable.toString() + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject, 0);
      throw new FFSException(localThrowable, (String)localObject);
    }
  }
  
  public WirePayeeInfo getWirePayee(String paramString)
    throws FFSException
  {
    FFSDebug.log("WirePayeeProcessor.getWirePayeeInfo start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    WirePayeeInfo localWirePayeeInfo1 = null;
    if (paramString == null)
    {
      String str1 = "***WirePayeeProcessor.getWirePayee failed: payeeID is null";
      FFSDebug.log(str1, 0);
      localWirePayeeInfo1 = new WirePayeeInfo();
      localWirePayeeInfo1.setStatusCode(16000);
      localWirePayeeInfo1.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "payeeID " }, "WIRE_MESSAGE"));
      return localWirePayeeInfo1;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    String str2;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str2 = "***WirePayeeProcessor.getWirePayee failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable1, str2);
    }
    try
    {
      localWirePayeeInfo1 = WirePayee.getWirePayeeInfo(localFFSConnectionHolder, paramString, true);
      localFFSConnectionHolder.conn.commit();
      WirePayeeInfo localWirePayeeInfo2 = localWirePayeeInfo1;
      return localWirePayeeInfo2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "***WirePayeeProcessor.getWirePayee failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable2, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public WirePayeeInfo canWirePayee(WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    FFSDebug.log("WirePayeeProcessor.canWirePayee start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    String str1 = null;
    String[] arrayOfString = null;
    String str2 = null;
    String str3;
    if (paramWirePayeeInfo == null)
    {
      str3 = "***WirePayee.canWirePayee failed: Wire Payee Object passed is null";
      FFSDebug.log(str3, 0);
      paramWirePayeeInfo = new WirePayeeInfo();
      paramWirePayeeInfo.setStatusCode(16000);
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "WirePayeeInfo " }, "WIRE_MESSAGE"));
      return paramWirePayeeInfo;
    }
    str1 = paramWirePayeeInfo.getPayeeId();
    if ((str1 == null) || (str1.trim().length() == 0))
    {
      str3 = "***WirePayee.delete failed: PayeeID is null";
      FFSDebug.log(str3, 0);
      paramWirePayeeInfo.setStatusCode(16010);
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "WirePayeeInfo ", "PayeeID" }, "WIRE_MESSAGE"));
      if (this.aI >= 1) {
        a(paramWirePayeeInfo, "Can", null);
      }
      return paramWirePayeeInfo;
    }
    str2 = paramWirePayeeInfo.getPayeeGroup();
    if ("UNMANAGED".equals(str2))
    {
      str3 = "***WirePayeeProcessor.canWirePayee failed:Unmanaged payee cannot be cancelled as a separate entity.";
      FFSDebug.log(str3, 0);
      paramWirePayeeInfo.setStatusCode(19840);
      paramWirePayeeInfo.setStatusMsg("Unmanaged payee cannot be cancelled as a separate entity.");
      return paramWirePayeeInfo;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    String str4;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      str4 = "***WirePayeeProcessor.canWirePayeeInfo failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str4, 0);
      throw new FFSException(localThrowable1, str4);
    }
    try
    {
      arrayOfString = Wire.getUnprocessedWires(localFFSConnectionHolder, str1, false);
      if ((arrayOfString == null) || (arrayOfString.length == 0))
      {
        arrayOfString = Wire.getUnprocessedWires(localFFSConnectionHolder, str1, true);
        if ((arrayOfString != null) && (arrayOfString.length > 0))
        {
          paramWirePayeeInfo.setStatusCode(19160);
          paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(19160, null, "WIRE_MESSAGE"));
          if (this.aI >= 1) {
            a(paramWirePayeeInfo, "Can", null);
          }
          localWirePayeeInfo = paramWirePayeeInfo;
          return localWirePayeeInfo;
        }
      }
      else
      {
        paramWirePayeeInfo.setStatusCode(19160);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(19160, null, "WIRE_MESSAGE"));
        if (this.aI >= 1) {
          a(paramWirePayeeInfo, "Can", null);
        }
        localWirePayeeInfo = paramWirePayeeInfo;
        return localWirePayeeInfo;
      }
      paramWirePayeeInfo = WirePayee.delete(localFFSConnectionHolder, paramWirePayeeInfo);
      if (paramWirePayeeInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        if (this.aI >= 1) {
          a(paramWirePayeeInfo, "Can", null);
        }
        localWirePayeeInfo = paramWirePayeeInfo;
        return localWirePayeeInfo;
      }
      if (this.aI >= 3) {
        logAudit(localFFSConnectionHolder, paramWirePayeeInfo, "Can");
      }
      localFFSConnectionHolder.conn.commit();
      paramWirePayeeInfo.setStatusCode(0);
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      WirePayeeInfo localWirePayeeInfo = paramWirePayeeInfo;
      return localWirePayeeInfo;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      if (this.aI >= 1) {
        a(paramWirePayeeInfo, "Can", "Delete wire payee failed, unknown error occurred");
      }
      str4 = "***WirePayeeProcessor.canWirePayee failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str4, 0);
      throw new FFSException(localThrowable2, str4);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public WirePayeeInfo modWirePayee(WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    String str1 = "WirePayeeProcessor.modWirePayee: ";
    FFSDebug.log(str1, "start", 6);
    String str2 = null;
    ArrayList localArrayList = null;
    if (paramWirePayeeInfo == null)
    {
      paramWirePayeeInfo.setStatusCode(16000);
      localObject1 = "WirePayeeInfo  is null";
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "WirePayeeInfo " }, "WIRE_MESSAGE"));
      FFSDebug.log("WirePayeeProcessor.modWirePayee, " + (String)localObject1);
      return paramWirePayeeInfo;
    }
    str2 = paramWirePayeeInfo.getPayeeGroup();
    if ("UNMANAGED".equals(str2))
    {
      localObject1 = "failed. Unmanaged payee cannot be modified as a separate entity.";
      FFSDebug.log(str1, (String)localObject1, 0);
      paramWirePayeeInfo.setStatusCode(19830);
      paramWirePayeeInfo.setStatusMsg("Unmanaged payee cannot be modified as a separate entity.");
      return paramWirePayeeInfo;
    }
    Object localObject1 = new FFSConnectionHolder();
    Object localObject2;
    try
    {
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      localObject2 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException(localThrowable1, "Could not get connection");
    }
    try
    {
      paramWirePayeeInfo = WirePayee.modifyPayee((FFSConnectionHolder)localObject1, paramWirePayeeInfo);
      if (paramWirePayeeInfo.getStatusCode() == 0)
      {
        BPWHist localBPWHist = new BPWHist();
        localObject2 = new String[] { "APPROVAL_PENDING", "APPROVAL_REJECTED" };
        localBPWHist.setPayeeId(paramWirePayeeInfo.getPayeeId());
        localBPWHist.setStatusList((String[])localObject2);
        localBPWHist = Wire.getWireInfoByStatusPayee((FFSConnectionHolder)localObject1, localBPWHist, false);
        if ((localBPWHist.getStatusCode() != 0) && (localBPWHist.getStatusCode() != 16020))
        {
          ((FFSConnectionHolder)localObject1).conn.rollback();
          FFSDebug.log("WirePayeeProcessor.modWirePayee failed ", "because failed to retrieve wire by paeyee", 0);
          paramWirePayeeInfo.setStatusCode(19770);
          paramWirePayeeInfo.setStatusMsg("Failed to retrieve wire transfer by payee Id.");
          if (this.aI >= 1) {
            a(paramWirePayeeInfo, "Mod", null);
          }
          localObject3 = paramWirePayeeInfo;
          return localObject3;
        }
        ((FFSConnectionHolder)localObject1).conn.commit();
        if (localBPWHist.getStatusCode() == 0)
        {
          localObject3 = null;
          String str3 = null;
          WireInfo[] arrayOfWireInfo = (WireInfo[])localBPWHist.getTrans();
          localArrayList = new ArrayList();
          for (int i = 0; i < arrayOfWireInfo.length; i++)
          {
            FFSDebug.log(str1, " wire for limit revert:" + arrayOfWireInfo[i], 6);
            int j = 0;
            str3 = arrayOfWireInfo[i].getPrcStatus();
            j = LimitCheckApprovalProcessor.processWireDelete((FFSConnectionHolder)localObject1, arrayOfWireInfo[i], null);
            localObject3 = LimitCheckApprovalProcessor.mapToStatus(j);
            FFSDebug.log(str1, " LimitCheckRevert retStatus:" + (String)localObject3, 6);
            if ("LIMIT_REVERT_FAILED".equals(localObject3))
            {
              FFSDebug.log(str1, " Limit Revert Failed.", 6);
              arrayOfWireInfo[i].setPrcStatus((String)localObject3);
            }
            else
            {
              j = LimitCheckApprovalProcessor.processWireAdd((FFSConnectionHolder)localObject1, arrayOfWireInfo[i], null);
              localObject3 = LimitCheckApprovalProcessor.mapToStatus(j);
              FFSDebug.log(str1, " LimitCheckAdd retStatus:" + (String)localObject3, 6);
              if (("LIMIT_CHECK_FAILED".equals(localObject3)) || ("LIMIT_REVERT_FAILED".equals(localObject3)) || ("APPROVAL_FAILED".equals(localObject3)))
              {
                FFSDebug.log(str1, " Limit Check Failed.", 6);
                arrayOfWireInfo[i].setPrcStatus((String)localObject3);
              }
              else if ("APPROVAL_PENDING".equals(localObject3))
              {
                arrayOfWireInfo[i].setPrcStatus("APPROVAL_PENDING");
              }
              else if ("APPROVAL_NOT_ALLOWED".equals(localObject3))
              {
                arrayOfWireInfo[i].setPrcStatus("APPROVAL_NOT_ALLOWED");
              }
              else
              {
                String str4 = arrayOfWireInfo[i].getWireType().trim();
                FFSDebug.log(str1, "NO_NEED_APPROVAL case, wireType=", str4, 6);
                if (str4.equalsIgnoreCase("TEMPLATE")) {
                  arrayOfWireInfo[i].setPrcStatus("TEMPLATE");
                } else if (str4.equalsIgnoreCase("RECTEMPLATE")) {
                  arrayOfWireInfo[i].setPrcStatus("RECTEMPLATE");
                } else {
                  arrayOfWireInfo[i].setPrcStatus("CREATED");
                }
              }
            }
            FFSDebug.log(str1, " single wire Instance before status update:" + arrayOfWireInfo[i], 6);
            int k = arrayOfWireInfo[i].getStatusCode();
            String str5 = arrayOfWireInfo[i].getStatusMsg();
            Wire.updateStatus((FFSConnectionHolder)localObject1, arrayOfWireInfo[i]);
            arrayOfWireInfo[i].setStatusCode(k);
            arrayOfWireInfo[i].setStatusMsg(str5);
            ((FFSConnectionHolder)localObject1).conn.commit();
            if (!str3.equals(arrayOfWireInfo[i].getPrcStatus()))
            {
              Hashtable localHashtable = arrayOfWireInfo[i].getExtInfo();
              if (localHashtable == null)
              {
                localHashtable = new Hashtable();
                arrayOfWireInfo[i].setExtInfo(localHashtable);
              }
              localHashtable.put("WIRE_OLDSTATUS", str3);
              localArrayList.add(arrayOfWireInfo[i]);
            }
          }
        }
        if (this.aI >= 3) {
          logAudit((FFSConnectionHolder)localObject1, paramWirePayeeInfo, "Mod");
        }
        ((FFSConnectionHolder)localObject1).conn.commit();
        if ((localArrayList != null) && (localArrayList.size() > 0))
        {
          localObject3 = paramWirePayeeInfo.getExtInfo();
          if (localObject3 == null)
          {
            localObject3 = new Hashtable();
            paramWirePayeeInfo.setExtInfo((Hashtable)localObject3);
          }
          ((Hashtable)localObject3).put("MOD_BENE_WIRES_LIST", localArrayList);
        }
      }
      else
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        if (this.aI >= 1) {
          a(paramWirePayeeInfo, "Mod", null);
        }
      }
    }
    catch (Throwable localThrowable2)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      if (this.aI >= 1) {
        a(paramWirePayeeInfo, "Mod", "Modify wire payee failed, unknown error occurred.");
      }
      localObject2 = "*** WirePayeeProcessor.modWirePayee failed: ";
      Object localObject3 = FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log((String)localObject3, 0);
      throw new FFSException(localThrowable2, (String)localObject2);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
    FFSDebug.log("WirePayeeProcessor.modWirePayee: done", 6);
    return paramWirePayeeInfo;
  }
  
  public BPWPayeeList getWirePayeeInfoByType(BPWPayeeList paramBPWPayeeList)
    throws FFSException
  {
    FFSDebug.log("WirePayeeProcessor.getWirePayeeInfoByType start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str = "***WirePayeeProcessor.getWirePayeeInfoByType failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable1, str);
    }
    try
    {
      paramBPWPayeeList = WirePayee.getWirePayeeInfoByType(localFFSConnectionHolder, paramBPWPayeeList);
      localFFSConnectionHolder.conn.commit();
      BPWPayeeList localBPWPayeeList = paramBPWPayeeList;
      return localBPWPayeeList;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str = "***WirePayeeProcessor.getWirePayeeInfoByType failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable2, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWPayeeList getWirePayeeInfoByStatus(BPWPayeeList paramBPWPayeeList)
    throws FFSException
  {
    FFSDebug.log("WirePayeeProcessor.getWirePayeeInfoByStatus start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str = "***WirePayeeProcessor.getWirePayeeInfoByStatus failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable1, str);
    }
    try
    {
      paramBPWPayeeList = WirePayee.getWirePayeeInfoByStatus(localFFSConnectionHolder, paramBPWPayeeList);
      localFFSConnectionHolder.conn.commit();
      BPWPayeeList localBPWPayeeList = paramBPWPayeeList;
      return localBPWPayeeList;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str = "***WirePayeeProcessor.getWirePayeeInfoByStatus failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable2, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWPayeeList getWirePayeeInfoByGroup(BPWPayeeList paramBPWPayeeList)
    throws FFSException
  {
    FFSDebug.log("WirePayeeProcessor.getWirePayeeInfoByGroup start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str = "***WirePayeeProcessor.getWirePayeeInfoByGroup failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable1, str);
    }
    try
    {
      paramBPWPayeeList = WirePayee.getWirePayeeInfoByGroup(localFFSConnectionHolder, paramBPWPayeeList);
      localFFSConnectionHolder.conn.commit();
      BPWPayeeList localBPWPayeeList = paramBPWPayeeList;
      return localBPWPayeeList;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str = "***WirePayeeProcessor.getWirePayeeInfoByGroup failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable2, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWPayeeList getWirePayeeInfoByCustomer(BPWPayeeList paramBPWPayeeList)
    throws FFSException
  {
    FFSDebug.log("WirePayeeProcessor.getWirePayeeInfoByCustomer start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str = "***WirePayeeProcessor.getWirePayeeInfoByCustomer failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable1, str);
    }
    try
    {
      paramBPWPayeeList = WirePayee.getWirePayeeInfoByCustomer(localFFSConnectionHolder, paramBPWPayeeList);
      localFFSConnectionHolder.conn.commit();
      BPWPayeeList localBPWPayeeList = paramBPWPayeeList;
      return localBPWPayeeList;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str = "***WirePayeeProcessor.getWirePayeeInfoByCustomer failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable2, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public int addIntermediaryBanksToBeneficiary(String paramString, BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws FFSException
  {
    String str1 = "WirePayeeProcessor.addIntermediaryBanksToBeneficiary:";
    FFSDebug.log(str1, " start", 6);
    WirePayeeInfo localWirePayeeInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    int i = 0;
    String str2;
    if (paramString == null)
    {
      str2 = "***" + str1 + " failed:" + " Null Payee ID is passed";
      FFSDebug.log(str2, 0);
      return -1;
    }
    if ((paramArrayOfBPWBankInfo == null) || (paramArrayOfBPWBankInfo.length == 0))
    {
      str2 = "***" + str1 + " failed:" + " Null or empty Intermediary bank array is passed";
      FFSDebug.log(str2, 0);
      return -1;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      String str4 = " failed: Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log("*** ", str1, str4, 0);
      throw new FFSException(localThrowable1, str4);
    }
    try
    {
      localWirePayeeInfo = WirePayee.getWirePayeeInfo(localFFSConnectionHolder, paramString, true);
      if (localWirePayeeInfo.getStatusCode() != 0)
      {
        String str3 = "***" + str1 + " failed:" + " PayeeId '" + paramString + "' does not exist in BPW database";
        FFSDebug.log(str3, 0);
        int k = -1;
        return k;
      }
      for (int j = 0; j < paramArrayOfBPWBankInfo.length; j++) {
        i += WirePayee.createPayeeIntermediateBankLink(localFFSConnectionHolder, paramString, paramArrayOfBPWBankInfo[j], j + 1);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      String str5 = "failed:" + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log("*** ", str1, str5, 0);
      throw new FFSException(localThrowable2, str5);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return i;
  }
  
  public int delIntermediaryBanksFromBeneficiary(String paramString, BPWBankInfo[] paramArrayOfBPWBankInfo)
    throws FFSException
  {
    String str1 = "WirePayeeProcessor.delIntermediaryBanksFromBeneficiary:";
    FFSDebug.log(str1, " start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    WirePayeeInfo localWirePayeeInfo = null;
    int i = 0;
    String str2;
    if (paramString == null)
    {
      str2 = "***" + str1 + " failed:" + " Null Payee ID is passed";
      FFSDebug.log(str2, 0);
      return -1;
    }
    if ((paramArrayOfBPWBankInfo == null) || (paramArrayOfBPWBankInfo.length == 0))
    {
      str2 = "***" + str1 + " failed:" + " Null or empty Intermediary bank array is passed";
      FFSDebug.log(str2, 0);
      return -1;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      String str4 = " failed: Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log("*** ", str1, str4, 0);
      throw new FFSException(localThrowable1, str4);
    }
    try
    {
      localWirePayeeInfo = WirePayee.getWirePayeeInfo(localFFSConnectionHolder, paramString, true);
      if (localWirePayeeInfo.getStatusCode() != 0)
      {
        String str3 = "***" + str1 + " failed:" + " PayeeId '" + paramString + "' does not exist in BPW database";
        FFSDebug.log(str3, 0);
        int k = -1;
        return k;
      }
      for (int j = 0; j < paramArrayOfBPWBankInfo.length; j++) {
        if (WirePayee.removePayeeIntermediateBankLink(localFFSConnectionHolder, paramString, paramArrayOfBPWBankInfo[j])) {
          i++;
        }
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      String str5 = "failed:" + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log("*** ", str1, str5, 0);
      throw new FFSException(localThrowable2, str5);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return i;
  }
  
  public WirePayeeInfo getWirePayee(WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    FFSDebug.log("WirePayeeProcessor.getWirePayee start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    Object localObject1;
    if (paramWirePayeeInfo == null)
    {
      String str = "***WirePayeeProcessor.getWirePayee failed: searchCriteria is null";
      FFSDebug.log(str, 0);
      localObject1 = new WirePayeeInfo();
      ((WirePayeeInfo)localObject1).setStatusCode(16000);
      ((WirePayeeInfo)localObject1).setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "WirePayeeInfo " }, "WIRE_MESSAGE"));
      return localObject1;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      localObject1 = "***WirePayeeProcessor.getWirePayee failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    try
    {
      paramWirePayeeInfo = WirePayee.getWirePayee(localFFSConnectionHolder, paramWirePayeeInfo);
      localFFSConnectionHolder.conn.commit();
      WirePayeeInfo localWirePayeeInfo = paramWirePayeeInfo;
      return localWirePayeeInfo;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      localObject1 = "***WirePayeeProcessor.getWirePayee failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable2, (String)localObject1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public static void logAudit(FFSConnectionHolder paramFFSConnectionHolder, WirePayeeInfo paramWirePayeeInfo, String paramString)
    throws Exception
  {
    String str1 = "WirePayeeProcessor.logAudit";
    String str2 = null;
    String str3 = null;
    AuditLogRecord localAuditLogRecord = null;
    str2 = paramWirePayeeInfo.getStatus();
    str3 = "WirePayee ";
    int i = 4065;
    if ("Add".equalsIgnoreCase(paramString))
    {
      i = 4065;
      str3 = "A wire payee has been added successfully";
    }
    else if ("Mod".equalsIgnoreCase(paramString))
    {
      i = 4066;
      str3 = "A wire payee has been modified successfully";
    }
    else if ("Can".equalsIgnoreCase(paramString))
    {
      i = 4067;
      paramWirePayeeInfo.setStatus("CLOSED");
      str3 = "A wire payee has been deleted successfully";
    }
    try
    {
      String str4 = null;
      str5 = null;
      String str6 = paramWirePayeeInfo.getSubmittedBy();
      int j = 0;
      str4 = paramWirePayeeInfo.getBankAcctId();
      if (paramWirePayeeInfo.getBeneficiaryBankInfo() != null) {
        str5 = paramWirePayeeInfo.getBeneficiaryBankInfo().getFedRTN();
      }
      if ((paramWirePayeeInfo.getCustomerID() != null) && (paramWirePayeeInfo.getCustomerID().length() > 0)) {
        if (paramWirePayeeInfo.getCustomerID().equals(paramWirePayeeInfo.getSubmittedBy())) {
          j = 0;
        } else {
          j = Integer.parseInt(paramWirePayeeInfo.getCustomerID());
        }
      }
      localAuditLogRecord = new AuditLogRecord(str6, null, null, str3, paramWirePayeeInfo.getExtId(), i, j, new BigDecimal(0.0D), null, paramWirePayeeInfo.getPayeeId(), paramWirePayeeInfo.getStatus(), str4, str5, null, null, -1);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      String str5 = str1 + " failed " + localNumberFormatException.toString();
      FFSDebug.log(str5 + FFSDebug.stackTrace(localNumberFormatException), 0);
      throw new FFSException(localNumberFormatException, str5);
    }
    paramWirePayeeInfo.setStatus(str2);
  }
  
  private void a(WirePayeeInfo paramWirePayeeInfo, String paramString1, String paramString2)
  {
    String str1 = "WirePayeeProcessor.logError";
    FFSConnectionHolder localFFSConnectionHolder = null;
    String str2 = paramString2;
    int i = paramWirePayeeInfo.getStatusCode();
    String str3 = null;
    if (paramWirePayeeInfo.getExtId() == null) {
      return;
    }
    AuditLogRecord localAuditLogRecord = null;
    str3 = paramWirePayeeInfo.getStatus();
    int j = 4065;
    if ("Add".equalsIgnoreCase(paramString1))
    {
      j = 4065;
      if (paramString2 == null) {
        str2 = "Add wire payee failed, ";
      }
    }
    else if ("Mod".equalsIgnoreCase(paramString1))
    {
      j = 4066;
      if (paramString2 == null) {
        str2 = "Modify wire payee failed, ";
      }
    }
    else if ("Can".equalsIgnoreCase(paramString1))
    {
      j = 4067;
      if (paramString2 == null) {
        str2 = "Delete wire payee failed, ";
      }
    }
    FFSDebug.log("logError: statusCode=" + i, 6);
    if (paramString2 == null) {
      switch (i)
      {
      case 23330: 
        str2 = str2 + "already exists.";
        break;
      case 19250: 
        str2 = str2 + "beneficiary wire bank does not exist.";
        break;
      case 19260: 
        str2 = str2 + "intermediary wire bank does not exist.";
        break;
      case 19190: 
        str2 = str2 + "null or invalid Customer Id.";
        break;
      case 19520: 
        str2 = str2 + "The combination of Bank Id, Account Number and Customer Id already exists for another wire payee";
        break;
      case 19160: 
        str2 = str2 + "unprocessed wire transfers exists for this payee";
        break;
      default: 
        if ((paramWirePayeeInfo.getStatusMsg() != null) && (paramWirePayeeInfo.getStatusMsg().length() > 0)) {
          str2 = str2 + paramWirePayeeInfo.getStatusMsg();
        } else {
          str2 = str2 + "due to unknown error.";
        }
        break;
      }
    }
    try
    {
      String str4 = null;
      str5 = null;
      String str6 = paramWirePayeeInfo.getSubmittedBy();
      int k = 0;
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        String str7 = str1 + "Can not get DB Connection.";
        FFSDebug.log(str7, 0);
      }
      str4 = paramWirePayeeInfo.getBankAcctId();
      if (paramWirePayeeInfo.getBeneficiaryBankInfo() != null) {
        str5 = paramWirePayeeInfo.getBeneficiaryBankInfo().getFedRTN();
      }
      if ((paramWirePayeeInfo.getCustomerID() != null) && (paramWirePayeeInfo.getCustomerID().length() > 0)) {
        if (paramWirePayeeInfo.getCustomerID().equals(paramWirePayeeInfo.getSubmittedBy())) {
          k = 0;
        } else {
          k = Integer.parseInt(paramWirePayeeInfo.getCustomerID());
        }
      }
      localAuditLogRecord = new AuditLogRecord(str6, null, null, str2, paramWirePayeeInfo.getExtId(), j, k, new BigDecimal(0.0D), null, paramWirePayeeInfo.getPayeeId(), paramWirePayeeInfo.getStatus(), str4, str5, null, null, -1);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, localFFSConnectionHolder.conn.getConnection());
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      String str5 = str1 + " failed " + localException.toString();
      FFSDebug.log(str5 + FFSDebug.stackTrace(localException), 0);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    paramWirePayeeInfo.setStatus(str3);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.WirePayeeProcessor
 * JD-Core Version:    0.7.0.1
 */