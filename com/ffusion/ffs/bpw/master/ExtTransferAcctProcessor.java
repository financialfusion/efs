package com.ffusion.ffs.bpw.master;

import com.ffusion.beans.Currency;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.ffs.bpw.db.BPWFI;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.ExternalTransferAccount;
import com.ffusion.ffs.bpw.db.ExternalTransferCompany;
import com.ffusion.ffs.bpw.db.Transfer;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctList;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctStateMachine;
import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWTrackingIDGenerator;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.fx.FXException;
import com.ffusion.fx.FXUtil;
import com.ffusion.services.fx.FXService;
import com.ffusion.util.enums.UserAssignedAmount;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ExtTransferAcctProcessor
  implements DBConsts, FFSConst, OFXConsts, BPWResource
{
  private TransferProcessor ag = new TransferProcessor();
  
  public ExtTransferAcctInfo addExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    String str1 = "ExtTransferAcctProcessor.addExtTransferAccount: ";
    FFSDebug.log(str1, "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    ExtTransferAcctInfo localExtTransferAcctInfo1 = null;
    String str2 = null;
    if (paramExtTransferAcctInfo == null)
    {
      str3 = "failed: Null ExternalTransferAcctInfo Object passed";
      FFSDebug.log("***", str1, str3, 0);
      paramExtTransferAcctInfo = new ExtTransferAcctInfo();
      paramExtTransferAcctInfo.setStatusCode(16000);
      str4 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctInfo" }, "TRANSFER_MESSAGE");
      paramExtTransferAcctInfo.setStatusMsg(str4);
      return paramExtTransferAcctInfo;
    }
    String str3 = paramExtTransferAcctInfo.getAcctScope();
    if ("UNMANAGED".equalsIgnoreCase(str3))
    {
      str4 = BPWLocaleUtil.getMessage(21190, null, "TRANSFER_MESSAGE");
      FFSDebug.log(str1, str4, 0);
      paramExtTransferAcctInfo.setStatusCode(21190);
      paramExtTransferAcctInfo.setStatusMsg(str4);
      return paramExtTransferAcctInfo;
    }
    String str4 = paramExtTransferAcctInfo.getAcctDest();
    String str5;
    if ("INTERNAL".equalsIgnoreCase(str4))
    {
      str5 = BPWLocaleUtil.getMessage(21640, null, "TRANSFER_MESSAGE");
      FFSDebug.log(str1, str5, 0);
      paramExtTransferAcctInfo.setStatusCode(21640);
      paramExtTransferAcctInfo.setStatusMsg(str5);
      return paramExtTransferAcctInfo;
    }
    str2 = paramExtTransferAcctInfo.getAcctId();
    String str6;
    if ((str2 != null) && (str2.trim().length() > 0))
    {
      str5 = "failed: AcctId already present in passed account info";
      FFSDebug.log("***", str1, str5, 0);
      paramExtTransferAcctInfo.setStatusCode(21110);
      str6 = BPWLocaleUtil.getMessage(21110, null, "TRANSFER_MESSAGE");
      paramExtTransferAcctInfo.setStatusMsg(str6);
      return paramExtTransferAcctInfo;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      str6 = "failed: Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log("****", str1, str6, 0);
      throw new FFSException(localThrowable1, str6);
    }
    try
    {
      localExtTransferAcctInfo1 = ExternalTransferAccount.add(localFFSConnectionHolder, paramExtTransferAcctInfo);
      if (localExtTransferAcctInfo1.getStatusCode() == 0) {
        localFFSConnectionHolder.conn.commit();
      } else {
        localFFSConnectionHolder.conn.rollback();
      }
      ExtTransferAcctInfo localExtTransferAcctInfo2 = localExtTransferAcctInfo1;
      return localExtTransferAcctInfo2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str6 = "failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log("****", str1, str6, 0);
      throw new FFSException(localThrowable2, str6);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public ExtTransferAcctInfo modExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ExtTransferAcctProcessor.modExtTransferAccount: ";
    FFSDebug.log(str1, "start", 6);
    if (paramExtTransferAcctInfo == null)
    {
      paramExtTransferAcctInfo = new ExtTransferAcctInfo();
      paramExtTransferAcctInfo.setStatusCode(16000);
      str2 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctInfo" }, "TRANSFER_MESSAGE");
      paramExtTransferAcctInfo.setStatusMsg(str2);
      FFSDebug.log(str1, str2, 0);
      return paramExtTransferAcctInfo;
    }
    String str2 = paramExtTransferAcctInfo.getAcctScope();
    if ("UNMANAGED".equalsIgnoreCase(str2))
    {
      str3 = BPWLocaleUtil.getMessage(21200, null, "TRANSFER_MESSAGE");
      FFSDebug.log(str1, str3, 0);
      paramExtTransferAcctInfo.setStatusCode(21200);
      paramExtTransferAcctInfo.setStatusMsg(str3);
      return paramExtTransferAcctInfo;
    }
    String str3 = paramExtTransferAcctInfo.getAcctDest();
    if ("INTERNAL".equalsIgnoreCase(str3))
    {
      localObject1 = BPWLocaleUtil.getMessage(21650, null, "TRANSFER_MESSAGE");
      FFSDebug.log(str1, (String)localObject1, 0);
      paramExtTransferAcctInfo.setStatusCode(21650);
      paramExtTransferAcctInfo.setStatusMsg((String)localObject1);
      return paramExtTransferAcctInfo;
    }
    Object localObject1 = new FFSConnectionHolder();
    String str4;
    try
    {
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      str4 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str1, str4, 0);
      throw new FFSException(localThrowable1, "Could not get connection");
    }
    try
    {
      if (paramBoolean) {
        paramExtTransferAcctInfo = ExternalTransferAccount.modifyPrenote((FFSConnectionHolder)localObject1, paramExtTransferAcctInfo, false);
      } else {
        paramExtTransferAcctInfo = ExternalTransferAccount.modify((FFSConnectionHolder)localObject1, paramExtTransferAcctInfo);
      }
      if (paramExtTransferAcctInfo.getStatusCode() == 0) {
        ((FFSConnectionHolder)localObject1).conn.commit();
      } else {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
    }
    catch (Throwable localThrowable2)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      str4 = "failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str1, str4, str5, 0);
      throw new FFSException(localThrowable2, str4);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
    FFSDebug.log(str1, "Done", 6);
    return paramExtTransferAcctInfo;
  }
  
  public ExtTransferAcctInfo canExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    String str1 = "ExtTransferAcctProcessor.canExtTransferAccount: ";
    FFSDebug.log(str1, "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    String str2 = null;
    String[] arrayOfString = null;
    if (paramExtTransferAcctInfo == null)
    {
      str3 = "failed: ExternalTransferAcctInfo Object passed is null";
      FFSDebug.log("***", str1, str3, 0);
      paramExtTransferAcctInfo = new ExtTransferAcctInfo();
      paramExtTransferAcctInfo.setStatusCode(16000);
      str4 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctInfo" }, "TRANSFER_MESSAGE");
      paramExtTransferAcctInfo.setStatusMsg(str4);
      return paramExtTransferAcctInfo;
    }
    String str3 = paramExtTransferAcctInfo.getAcctScope();
    if ("UNMANAGED".equalsIgnoreCase(str3))
    {
      str4 = BPWLocaleUtil.getMessage(21210, null, "TRANSFER_MESSAGE");
      FFSDebug.log(str1, str4, 0);
      paramExtTransferAcctInfo.setStatusCode(21210);
      paramExtTransferAcctInfo.setStatusMsg(str4);
      return paramExtTransferAcctInfo;
    }
    String str4 = paramExtTransferAcctInfo.getAcctDest();
    String str5;
    if ("INTERNAL".equalsIgnoreCase(str4))
    {
      str5 = BPWLocaleUtil.getMessage(21660, null, "TRANSFER_MESSAGE");
      FFSDebug.log(str1, str5, 0);
      paramExtTransferAcctInfo.setStatusCode(21660);
      paramExtTransferAcctInfo.setStatusMsg(str5);
      return paramExtTransferAcctInfo;
    }
    str2 = paramExtTransferAcctInfo.getAcctId();
    Object localObject1;
    if ((str2 == null) || (str2.trim().length() == 0))
    {
      str5 = "failed: AcctId is null";
      FFSDebug.log("****", str1, str5, 0);
      paramExtTransferAcctInfo.setStatusCode(16010);
      localObject1 = BPWLocaleUtil.getMessage(16010, new String[] { "ExtTransferAcctInfo", "Account Id" }, "TRANSFER_MESSAGE");
      paramExtTransferAcctInfo.setStatusMsg((String)localObject1);
      return paramExtTransferAcctInfo;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable1)
    {
      localObject1 = "failed: Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log("****", str1, (String)localObject1, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    try
    {
      arrayOfString = Transfer.getActiveTransfersByExtAcctId(localFFSConnectionHolder, str2, false);
      if ((arrayOfString == null) || (arrayOfString.length == 0))
      {
        arrayOfString = Transfer.getActiveTransfersByExtAcctId(localFFSConnectionHolder, str2, true);
        if ((arrayOfString != null) && (arrayOfString.length > 0))
        {
          paramExtTransferAcctInfo.setStatusCode(21100);
          String str6 = BPWLocaleUtil.getMessage(21100, null, "TRANSFER_MESSAGE");
          paramExtTransferAcctInfo.setStatusMsg(str6);
          localObject1 = paramExtTransferAcctInfo;
          return localObject1;
        }
      }
      else
      {
        int i = 1;
        try
        {
          paramExtTransferAcctInfo = ExternalTransferAccount.getExternalTransferAccount(localFFSConnectionHolder, paramExtTransferAcctInfo, true, true);
          int j = paramExtTransferAcctInfo.getVerifyStatus();
          if ((j == 2) || (j == 3))
          {
            i = 0;
            for (int k = 0; k < arrayOfString.length; k++)
            {
              TransferInfo localTransferInfo = new TransferInfo();
              localTransferInfo.setSrvrTId(arrayOfString[k]);
              localTransferInfo.setTrnId(BPWTrackingIDGenerator.getNextId());
              localTransferInfo = this.ag.cancTransfer(localTransferInfo);
              if (localTransferInfo.getStatusCode() != 0) {
                i = 1;
              }
            }
          }
        }
        catch (FFSException localFFSException)
        {
          i = 1;
        }
        if (i != 0)
        {
          paramExtTransferAcctInfo.setStatusCode(21100);
          str7 = BPWLocaleUtil.getMessage(21100, null, "TRANSFER_MESSAGE");
          paramExtTransferAcctInfo.setStatusMsg(str7);
          ExtTransferAcctInfo localExtTransferAcctInfo2 = paramExtTransferAcctInfo;
          return localExtTransferAcctInfo2;
        }
      }
      paramExtTransferAcctInfo = ExternalTransferAccount.cancel(localFFSConnectionHolder, paramExtTransferAcctInfo);
      if (paramExtTransferAcctInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
      }
      else
      {
        localFFSConnectionHolder.conn.commit();
        paramExtTransferAcctInfo.setStatusCode(0);
        paramExtTransferAcctInfo.setStatusMsg("Success");
      }
      ExtTransferAcctInfo localExtTransferAcctInfo1 = paramExtTransferAcctInfo;
      return localExtTransferAcctInfo1;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      String str7 = "failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log("****", str1, str7, 0);
      throw new FFSException(localThrowable2, str7);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public ExtTransferAcctInfo getExtTransferAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    String str1 = "ExtTransferAcctProcessor.getExtTransferAccount: ";
    FFSDebug.log(str1, "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    ExtTransferAcctInfo localExtTransferAcctInfo1 = null;
    String str3;
    if (paramExtTransferAcctInfo == null)
    {
      String str2 = "failed: ExtTransferAcctInfo object is null";
      FFSDebug.log("***", str1, str2, 0);
      localExtTransferAcctInfo1 = new ExtTransferAcctInfo();
      localExtTransferAcctInfo1.setStatusCode(16000);
      str3 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctInfo" }, "TRANSFER_MESSAGE");
      localExtTransferAcctInfo1.setStatusMsg(str3);
      return localExtTransferAcctInfo1;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str3 = "failed: Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log("****", str1, str3, 0);
      throw new FFSException(localThrowable1, str3);
    }
    try
    {
      localExtTransferAcctInfo1 = ExternalTransferAccount.getExternalTransferAccount(localFFSConnectionHolder, paramExtTransferAcctInfo);
      localFFSConnectionHolder.conn.commit();
      ExtTransferAcctInfo localExtTransferAcctInfo2 = localExtTransferAcctInfo1;
      return localExtTransferAcctInfo2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str3 = "failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log("****", str1, str3, 0);
      throw new FFSException(localThrowable2, str3);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public ExtTransferAcctList getExtTransferAcctList(ExtTransferAcctList paramExtTransferAcctList)
    throws FFSException
  {
    String str1 = "ExtTransferAcctProcessor.getExtTransferAcctList: ";
    FFSDebug.log(str1, "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    ExtTransferAcctList localExtTransferAcctList1 = null;
    String str3;
    if (paramExtTransferAcctList == null)
    {
      String str2 = "failed: ExternalTransferAcctList object is null";
      FFSDebug.log("***", str1, str2, 0);
      localExtTransferAcctList1 = new ExtTransferAcctList();
      localExtTransferAcctList1.setStatusCode(16000);
      str3 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctInfo" }, "TRANSFER_MESSAGE");
      localExtTransferAcctList1.setStatusMsg(str3);
      return localExtTransferAcctList1;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str3 = "failed: Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log("****", str1, str3, 0);
      throw new FFSException(localThrowable1, str3);
    }
    try
    {
      localExtTransferAcctList1 = ExternalTransferAccount.getExternalTransferAccountList(localFFSConnectionHolder, paramExtTransferAcctList);
      localFFSConnectionHolder.conn.commit();
      ExtTransferAcctList localExtTransferAcctList2 = localExtTransferAcctList1;
      return localExtTransferAcctList2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str3 = "failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log("****", str1, str3, 0);
      throw new FFSException(localThrowable2, str3);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public ExtTransferAcctInfo verifyExtTransferAccount(String paramString, ExtTransferAcctInfo paramExtTransferAcctInfo, int[] paramArrayOfInt)
    throws FFSException
  {
    String str1 = "ExtTransferAcctProcessor.verifyExtTransferAccount: ";
    FFSDebug.log(str1, "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    Object localObject1;
    Object localObject2;
    if (paramExtTransferAcctInfo == null)
    {
      String str2 = "failed: ExtTransferAcctInfo object is null";
      FFSDebug.log("***", str1, str2, 0);
      localObject1 = new ExtTransferAcctInfo();
      ((ExtTransferAcctInfo)localObject1).setStatusCode(16000);
      localObject2 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctInfo" }, "TRANSFER_MESSAGE");
      ((ExtTransferAcctInfo)localObject1).setStatusMsg((String)localObject2);
      return localObject1;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      localObject1 = "failed: Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log("****", str1, (String)localObject1, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    try
    {
      BPWFIInfo localBPWFIInfo = BPWFI.getBPWFIInfo(localFFSConnectionHolder, paramString);
      ExtTransferAcctInfo localExtTransferAcctInfo1;
      if (localBPWFIInfo == null)
      {
        localObject1 = "failed: BPWFIInfo is not found";
        FFSDebug.log("***", str1, (String)localObject1, 0);
        paramExtTransferAcctInfo.setStatusCode(16000);
        localObject2 = BPWLocaleUtil.getMessage(16000, new String[] { "BPWFIInfo" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg((String)localObject2);
        localExtTransferAcctInfo1 = paramExtTransferAcctInfo;
        return localExtTransferAcctInfo1;
      }
      if (localBPWFIInfo.getETFCompanyID() == null)
      {
        localObject1 = "failed: BPWFIInfo ETF Company is not set up";
        FFSDebug.log("***", str1, (String)localObject1, 0);
        paramExtTransferAcctInfo.setStatusCode(21690);
        localObject2 = BPWLocaleUtil.getMessage(21690, null, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg((String)localObject2);
        localExtTransferAcctInfo1 = paramExtTransferAcctInfo;
        return localExtTransferAcctInfo1;
      }
      if ((localBPWFIInfo.getETFDepositAcct() == null) || (localBPWFIInfo.getETFDepositAcctType() == null))
      {
        localObject1 = "failed: BPWFIInfo ETF Deposit Account is not set up";
        FFSDebug.log("***", str1, (String)localObject1, 0);
        paramExtTransferAcctInfo.setStatusCode(16000);
        localObject2 = BPWLocaleUtil.getMessage(16000, new String[] { localBPWFIInfo.getETFDepositAcct() == null ? "Deposit Account" : "Deposit Account Type" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg((String)localObject2);
        localExtTransferAcctInfo1 = paramExtTransferAcctInfo;
        return localExtTransferAcctInfo1;
      }
      localObject1 = ExternalTransferAccount.getDepositedAmounts(localFFSConnectionHolder, paramExtTransferAcctInfo, localBPWFIInfo.getETFVirtualUserID());
      localObject2 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int i = 2;
      try
      {
        String str3 = ((PropertyConfig)localObject2).otherProperties.getProperty("bpw.external.transfer.account.verify.deposits", "2");
        i = new Integer(str3).intValue();
      }
      catch (Exception localException) {}
      ExtTransferAcctInfo localExtTransferAcctInfo2 = new ExtTransferAcctInfo();
      if ((paramArrayOfInt.length != i) || (localObject1.length < i))
      {
        localExtTransferAcctInfo2 = ExternalTransferAccount.depositOrVerifyAccount(localFFSConnectionHolder, paramExtTransferAcctInfo, false);
      }
      else
      {
        localObject3 = new ArrayList();
        for (int j = 0; j < paramArrayOfInt.length; j++) {
          ((ArrayList)localObject3).add("" + paramArrayOfInt[j]);
        }
        for (j = 0; j < paramArrayOfInt.length; j++) {
          if (((ArrayList)localObject3).contains("" + localObject1[j])) {
            ((ArrayList)localObject3).remove("" + localObject1[j]);
          }
        }
        if (((ArrayList)localObject3).size() == 0)
        {
          localExtTransferAcctInfo2 = ExternalTransferAccount.verifyAccountSuccess(localFFSConnectionHolder, paramExtTransferAcctInfo);
          localExtTransferAcctInfo2 = ExternalTransferAccount.activateOrDeactivateAccount(localFFSConnectionHolder, paramExtTransferAcctInfo, true);
        }
        else
        {
          localExtTransferAcctInfo2 = ExternalTransferAccount.depositOrVerifyAccount(localFFSConnectionHolder, paramExtTransferAcctInfo, false);
        }
      }
      localFFSConnectionHolder.conn.commit();
      Object localObject3 = localExtTransferAcctInfo2;
      return localObject3;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      localObject1 = "failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log("****", str1, (String)localObject1, 0);
      throw new FFSException(localThrowable2, (String)localObject1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public ExtTransferAcctInfo depositAmountsForVerify(String paramString, ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    String str1 = "ExtTransferAcctProcessor.depositAmountsForVerify: ";
    FFSDebug.log(str1, "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    Object localObject2;
    Object localObject3;
    if (paramExtTransferAcctInfo == null)
    {
      String str2 = "failed: ExtTransferAcctInfo object is null";
      FFSDebug.log("***", str1, str2, 0);
      localObject2 = new ExtTransferAcctInfo();
      ((ExtTransferAcctInfo)localObject2).setStatusCode(16000);
      localObject3 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctInfo" }, "TRANSFER_MESSAGE");
      ((ExtTransferAcctInfo)localObject2).setStatusMsg((String)localObject3);
      return localObject2;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      localObject2 = "failed: Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log("****", str1, (String)localObject2, 0);
      throw new FFSException(localThrowable1, (String)localObject2);
    }
    try
    {
      if ((!ExtTransferAcctStateMachine.canDeposit(paramExtTransferAcctInfo)) && (!ExtTransferAcctStateMachine.canReDeposit(paramExtTransferAcctInfo)))
      {
        localObject1 = "failed: ExtTransferAcctInfo in wrong state for Deposit";
        FFSDebug.log("***", str1, (String)localObject1, 0);
        paramExtTransferAcctInfo.setStatusCode(21700);
        localObject2 = BPWLocaleUtil.getMessage(21700, null, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg((String)localObject2);
        localObject3 = paramExtTransferAcctInfo;
        return localObject3;
      }
      Object localObject1 = BPWFI.getBPWFIInfo(localFFSConnectionHolder, paramString);
      ExtTransferAcctInfo localExtTransferAcctInfo1;
      if (localObject1 == null)
      {
        localObject2 = "failed: BPWFIInfo is not found";
        FFSDebug.log("***", str1, (String)localObject2, 0);
        paramExtTransferAcctInfo.setStatusCode(16000);
        localObject3 = BPWLocaleUtil.getMessage(16000, new String[] { "BPWFIInfo" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg((String)localObject3);
        localExtTransferAcctInfo1 = paramExtTransferAcctInfo;
        return localExtTransferAcctInfo1;
      }
      if (((BPWFIInfo)localObject1).getETFCompanyID() == null)
      {
        localObject2 = "failed: BPWFIInfo ETF Company is not set up";
        FFSDebug.log("***", str1, (String)localObject2, 0);
        paramExtTransferAcctInfo.setStatusCode(21690);
        localObject3 = BPWLocaleUtil.getMessage(21690, null, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg((String)localObject3);
        localExtTransferAcctInfo1 = paramExtTransferAcctInfo;
        return localExtTransferAcctInfo1;
      }
      if ((((BPWFIInfo)localObject1).getETFDepositAcct() == null) || (((BPWFIInfo)localObject1).getETFDepositAcctType() == null))
      {
        localObject2 = "failed: BPWFIInfo ETF Deposit Account is not set up";
        FFSDebug.log("***", str1, (String)localObject2, 0);
        paramExtTransferAcctInfo.setStatusCode(16000);
        localObject3 = BPWLocaleUtil.getMessage(16000, new String[] { ((BPWFIInfo)localObject1).getETFDepositAcct() == null ? "Deposit Account" : "Deposit Account Type" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg((String)localObject3);
        localExtTransferAcctInfo1 = paramExtTransferAcctInfo;
        return localExtTransferAcctInfo1;
      }
      localObject2 = new ExtTransferCompanyInfo();
      ((ExtTransferCompanyInfo)localObject2).setCompId(((BPWFIInfo)localObject1).getETFCompanyID());
      localObject2 = ExternalTransferCompany.getExternalTransferCompany(localFFSConnectionHolder, (ExtTransferCompanyInfo)localObject2);
      localObject3 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int i = 2;
      try
      {
        String str3 = ((PropertyConfig)localObject3).otherProperties.getProperty("bpw.external.transfer.account.verify.deposits", "2");
        i = new Integer(str3).intValue();
      }
      catch (Exception localException) {}
      String str4 = FFSUtil.getDateString("yyyyMMdd");
      int j = 0;
      String str5 = BPWTrackingIDGenerator.getNextId();
      for (int k = 0; (j == 0) && (k < i); k++)
      {
        localObject4 = new Random(System.currentTimeMillis());
        int m = ((BPWFIInfo)localObject1).getETFMaxDepositAmt() - ((BPWFIInfo)localObject1).getETFMinDepositAmt();
        int n = ((Random)localObject4).nextInt(m);
        n += ((BPWFIInfo)localObject1).getETFMinDepositAmt();
        FFSDebug.log("***", str1, "DepositAmount " + (k + 1) + " = " + n, 0);
        TransferInfo localTransferInfo = new TransferInfo();
        localTransferInfo.setExtTransferCompanyInfo((ExtTransferCompanyInfo)localObject2);
        localTransferInfo.setCustomerId(((BPWFIInfo)localObject1).getETFVirtualUserID());
        localTransferInfo.setSubmittedBy(((BPWFIInfo)localObject1).getETFVirtualUserID());
        localTransferInfo.setTrnId(BPWTrackingIDGenerator.getNextId());
        localTransferInfo.setLogId(str5);
        localTransferInfo.setFIId(((BPWFIInfo)localObject1).getFIId());
        localTransferInfo.setAccountFromNum(((BPWFIInfo)localObject1).getETFDepositAcct());
        if (((BPWFIInfo)localObject1).getETFDepositAcctType().equalsIgnoreCase("CHECKING")) {
          localTransferInfo.setAccountFromType("CHECKING");
        }
        if (((BPWFIInfo)localObject1).getETFDepositAcctType().equalsIgnoreCase("SAVINGS")) {
          localTransferInfo.setAccountFromType("SAVINGS");
        }
        if ((paramExtTransferAcctInfo != null) && (paramExtTransferAcctInfo.getRecipientType() != null)) {
          if (paramExtTransferAcctInfo.getRecipientType().equals("PERSONAL")) {
            localTransferInfo.setTypeDetail("PPD");
          } else {
            localTransferInfo.setTypeDetail("CCD");
          }
        }
        localTransferInfo.setAccountToInfo(paramExtTransferAcctInfo);
        localTransferInfo.setAccountToId(paramExtTransferAcctInfo.getAcctId());
        localTransferInfo.setDateCreate(str4);
        localTransferInfo.setDateDue(str4);
        localTransferInfo.setDateToPost(str4);
        localTransferInfo.setTransferType("Current");
        localTransferInfo.setTransferCategory("USER_ENTRY");
        localTransferInfo.setBankFromRtn(((BPWFIInfo)localObject1).getFIRTN());
        localTransferInfo.setPrcStatus("POSTEDON");
        localTransferInfo.setTransferDest("ITOE");
        int i1 = Transfer.getValidTransferDueDate(localFFSConnectionHolder, localTransferInfo);
        localTransferInfo.setDateDue("" + i1);
        localTransferInfo.setDateToPost("" + i1);
        localTransferInfo.setStatusCode(0);
        localTransferInfo.setStatusMsg("Success");
        BigDecimal localBigDecimal = new BigDecimal("" + n);
        localTransferInfo.setAmountCurrency(((BPWFIInfo)localObject1).getCurrencyCode());
        localTransferInfo.setToAmountCurrency(paramExtTransferAcctInfo.getCurrencyCode());
        if (((BPWFIInfo)localObject1).getCurrencyCode().equals(paramExtTransferAcctInfo.getCurrencyCode()))
        {
          localTransferInfo.setAmount("" + localBigDecimal.movePointLeft(2));
          localTransferInfo.setToAmount("0.00");
        }
        else
        {
          localTransferInfo.setAmount("0.00");
          localBigDecimal = a(localTransferInfo, localBigDecimal.movePointLeft(2));
          localTransferInfo.setToAmount("" + localBigDecimal);
          localTransferInfo.setUserAssignedAmount(UserAssignedAmount.TO);
        }
        localTransferInfo.setAction("Add");
        FFSDebug.log("***", str1, " TransferInfo = : " + localTransferInfo.toString(), 0);
        this.ag.addTransfer(localTransferInfo);
        if (localTransferInfo.getStatusCode() != 0)
        {
          FFSDebug.log("***", str1, " failed: " + localTransferInfo.getStatusCode() + " = " + localTransferInfo.getStatusMsg(), 0);
          j = 1;
        }
      }
      ExtTransferAcctInfo localExtTransferAcctInfo2 = new ExtTransferAcctInfo();
      if (j == 0) {
        localExtTransferAcctInfo2 = ExternalTransferAccount.depositOrVerifyAccount(localFFSConnectionHolder, paramExtTransferAcctInfo, true);
      }
      localExtTransferAcctInfo2 = ExternalTransferAccount.getExternalTransferAccount(localFFSConnectionHolder, paramExtTransferAcctInfo);
      localFFSConnectionHolder.conn.commit();
      Object localObject4 = localExtTransferAcctInfo2;
      return localObject4;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      localObject2 = "failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log("****", str1, (String)localObject2, 0);
      throw new FFSException(localThrowable2, (String)localObject2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public ExtTransferAcctInfo activateExtTransferAcct(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    String str1 = "ExtTransferAcctProcessor.activateExtTransferAcct: ";
    FFSDebug.log(str1, "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    Object localObject2;
    Object localObject3;
    if (paramExtTransferAcctInfo == null)
    {
      String str2 = "failed: ExtTransferAcctInfo object is null";
      FFSDebug.log("***", str1, str2, 0);
      localObject2 = new ExtTransferAcctInfo();
      ((ExtTransferAcctInfo)localObject2).setStatusCode(16000);
      localObject3 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctInfo" }, "TRANSFER_MESSAGE");
      ((ExtTransferAcctInfo)localObject2).setStatusMsg((String)localObject3);
      return localObject2;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      localObject2 = "failed: Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log("****", str1, (String)localObject2, 0);
      throw new FFSException(localThrowable1, (String)localObject2);
    }
    try
    {
      if (!ExtTransferAcctStateMachine.canActivate(paramExtTransferAcctInfo))
      {
        localObject1 = "failed: ExtTransferAcctInfo in wrong state for Activate";
        FFSDebug.log("***", str1, (String)localObject1, 0);
        paramExtTransferAcctInfo.setStatusCode(21700);
        localObject2 = BPWLocaleUtil.getMessage(21700, null, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg((String)localObject2);
        localObject3 = paramExtTransferAcctInfo;
        return localObject3;
      }
      ExternalTransferAccount.activateOrDeactivateAccount(localFFSConnectionHolder, paramExtTransferAcctInfo, true);
      localFFSConnectionHolder.conn.commit();
      Object localObject1 = paramExtTransferAcctInfo;
      return localObject1;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      localObject2 = "failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log("****", str1, (String)localObject2, 0);
      throw new FFSException(localThrowable2, (String)localObject2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public ExtTransferAcctInfo inactivateExtTransferAcct(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    String str1 = "ExtTransferAcctProcessor.inactivateExtTransferAcct: ";
    FFSDebug.log(str1, "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    Object localObject2;
    Object localObject3;
    if (paramExtTransferAcctInfo == null)
    {
      String str2 = "failed: ExtTransferAcctInfo object is null";
      FFSDebug.log("***", str1, str2, 0);
      localObject2 = new ExtTransferAcctInfo();
      ((ExtTransferAcctInfo)localObject2).setStatusCode(16000);
      localObject3 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctInfo" }, "TRANSFER_MESSAGE");
      ((ExtTransferAcctInfo)localObject2).setStatusMsg((String)localObject3);
      return localObject2;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      localObject2 = "failed: Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log("****", str1, (String)localObject2, 0);
      throw new FFSException(localThrowable1, (String)localObject2);
    }
    try
    {
      if (!ExtTransferAcctStateMachine.canDeactivate(paramExtTransferAcctInfo))
      {
        localObject1 = "failed: ExtTransferAcctInfo in wrong state for DeActivate";
        FFSDebug.log("***", str1, (String)localObject1, 0);
        paramExtTransferAcctInfo.setStatusCode(21700);
        localObject2 = BPWLocaleUtil.getMessage(21700, null, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg((String)localObject2);
        localObject3 = paramExtTransferAcctInfo;
        return localObject3;
      }
      ExternalTransferAccount.activateOrDeactivateAccount(localFFSConnectionHolder, paramExtTransferAcctInfo, false);
      localFFSConnectionHolder.conn.commit();
      Object localObject1 = paramExtTransferAcctInfo;
      return localObject1;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      localObject2 = "failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log("****", str1, (String)localObject2, 0);
      throw new FFSException(localThrowable2, (String)localObject2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  private BigDecimal a(TransferInfo paramTransferInfo, BigDecimal paramBigDecimal)
  {
    String str1 = "EXTTransferAcctProcessor.getFXDepositAmount";
    BigDecimal localBigDecimal = new BigDecimal(paramBigDecimal.toString());
    try
    {
      FXService localFXService = (FXService)FFSRegistry.lookup("FXSERVICE");
      HashMap localHashMap = new HashMap();
      FXRate localFXRate = localFXService.getFXRate(Integer.parseInt(paramTransferInfo.getCustomerId()), paramTransferInfo.getAmountCurrency(), paramTransferInfo.getToAmountCurrency(), localHashMap);
      if (localFXRate != null)
      {
        localBigDecimal = paramBigDecimal.multiply(localFXRate.getBuyPrice().getAmountValue());
        try
        {
          int i = FXUtil.getNumDecimals(paramTransferInfo.getToAmountCurrency(), localHashMap);
          localBigDecimal = localBigDecimal.setScale(i, 5);
        }
        catch (FXException localFXException2)
        {
          FFSDebug.log("****", str1, "Unable to get FX rate: " + localFXException2, 0);
        }
      }
      else
      {
        String str2 = "FX rate not availaible for " + paramTransferInfo.getAmountCurrency() + " to " + paramTransferInfo.getToAmountCurrency();
        FFSDebug.log("****", str1, str2, 1);
      }
    }
    catch (FXException localFXException1)
    {
      FFSDebug.log("****", str1, "Unable to get FX rate: " + localFXException1, 0);
    }
    return localBigDecimal;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.ExtTransferAcctProcessor
 * JD-Core Version:    0.7.0.1
 */