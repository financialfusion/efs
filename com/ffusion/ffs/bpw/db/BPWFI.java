package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
import com.ffusion.ffs.bpw.interfaces.ACHSameDayEffDateInfo;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.CommBankIdentifier;
import java.util.ArrayList;
import java.util.Hashtable;

public class BPWFI
  implements DBConsts, FFSConst, ACHConsts
{
  public static BPWFIInfo create(FFSConnectionHolder paramFFSConnectionHolder, BPWFIInfo paramBPWFIInfo)
    throws FFSException
  {
    FFSDebug.log("***BPWFI.create start ...", 6);
    if (paramBPWFIInfo == null)
    {
      String str = "***BPWFI.create failed: Null BPWFIInfo Object passed";
      FFSDebug.log(str, 0);
      BPWFIInfo localBPWFIInfo = new BPWFIInfo();
      localBPWFIInfo.setStatusCode(16000);
      localBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "BPWFIInfo " }, "OFX_MESSAGE"));
      return localBPWFIInfo;
    }
    if (!jdMethod_if(paramBPWFIInfo)) {
      return paramBPWFIInfo;
    }
    paramBPWFIInfo.setFIId(DBUtil.getNextIndexStringWithPadding("FIID", 4, '0'));
    paramBPWFIInfo = createWithGivenID(paramFFSConnectionHolder, paramBPWFIInfo);
    return paramBPWFIInfo;
  }
  
  public static BPWFIInfo createWithGivenID(FFSConnectionHolder paramFFSConnectionHolder, BPWFIInfo paramBPWFIInfo)
    throws FFSException
  {
    FFSDebug.log("***BPWFI.createWithGivenID start ...", 6);
    Object localObject1;
    Object localObject2;
    if (paramBPWFIInfo == null)
    {
      localObject1 = "***BPWFI.createWithGivenID failed: Null FI Object passed";
      FFSDebug.log((String)localObject1, 0);
      localObject2 = new BPWFIInfo();
      ((BPWFIInfo)localObject2).setStatusCode(16000);
      ((BPWFIInfo)localObject2).setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "BPWFIInfo " }, "OFX_MESSAGE"));
      return localObject2;
    }
    if (!jdMethod_if(paramBPWFIInfo)) {
      return paramBPWFIInfo;
    }
    try
    {
      localObject1 = m(paramFFSConnectionHolder, paramBPWFIInfo.getFIRTN());
      if (((BPWFIInfo)localObject1).getStatusCode() == 0)
      {
        ((BPWFIInfo)localObject1).setStatusCode(23300);
        ((BPWFIInfo)localObject1).setStatusMsg(BPWLocaleUtil.getMessage(23300, null, "OFX_MESSAGE"));
        return localObject1;
      }
      paramBPWFIInfo.setFIStatus("ACTIVE");
      paramBPWFIInfo.setSubmitDate(FFSUtil.getDateString());
      paramBPWFIInfo.setActivationDate(FFSUtil.getDateString());
      localObject2 = "INSERT INTO BPW_FIInfo (FIId, FIRTN, FIType, FIName, NickName, FIStatus, FIDesc, FIGroup, FIRank, AddrLine1, AddrLine2, AddrLine3,  City, State, PostalCode, Country, DayPhone, Extension, SubmitDate, ContactName , ActivationDate, AmtLimit, SWIFTRTN, CHIPSRTN, OtherRTN, LogId, CurrencyCode, ACHTransWarehouse, ACHMaxNoFutureDays, ACHSameDayEffDate, ETFDepositAcct, ETFDepositAcctType, ETFMinDepositAmount, ETFMaxDepositAmount) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      if ((paramBPWFIInfo.getCurrencyCode() == null) || (paramBPWFIInfo.getCurrencyCode().trim().equals(""))) {
        paramBPWFIInfo.setCurrencyCode(BPWLocaleUtil.getCurCode());
      }
      Object[] arrayOfObject = { paramBPWFIInfo.getFIId(), paramBPWFIInfo.getFIRTN(), paramBPWFIInfo.getFIType(), paramBPWFIInfo.getFIName(), paramBPWFIInfo.getNickName(), paramBPWFIInfo.getFIStatus(), paramBPWFIInfo.getFIDesc(), paramBPWFIInfo.getFIGroup(), paramBPWFIInfo.getFIRank(), paramBPWFIInfo.getAddr1(), paramBPWFIInfo.getAddr2(), paramBPWFIInfo.getAddr3(), paramBPWFIInfo.getCity(), paramBPWFIInfo.getState(), paramBPWFIInfo.getPostalCode(), paramBPWFIInfo.getCountry(), paramBPWFIInfo.getDayPhone(), paramBPWFIInfo.getExtension(), paramBPWFIInfo.getSubmitDate(), paramBPWFIInfo.getContactName(), paramBPWFIInfo.getActivationDate(), paramBPWFIInfo.getAmtLimit(), paramBPWFIInfo.getSwiftRTN(), paramBPWFIInfo.getChipsRTN(), paramBPWFIInfo.getOtherRTN(), paramBPWFIInfo.getLogId(), paramBPWFIInfo.getCurrencyCode(), new Integer(paramBPWFIInfo.getACHTransWareHouse()), new Integer(paramBPWFIInfo.getACHMaxNoFutureDays()), paramBPWFIInfo.getACHSameDayEffDate(), paramBPWFIInfo.getETFDepositAcct(), paramBPWFIInfo.getETFDepositAcctType(), new Integer(paramBPWFIInfo.getETFMinDepositAmt()), new Integer(paramBPWFIInfo.getETFMaxDepositAmt()) };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, (String)localObject2, arrayOfObject);
      if (null != paramBPWFIInfo.getMemo())
      {
        int j = BPWExtraInfo.processXtraInfo(paramFFSConnectionHolder, paramBPWFIInfo.getFIId(), paramBPWFIInfo.getFIId(), "BPWFI", paramBPWFIInfo.getMemo());
        if (j != 0)
        {
          paramBPWFIInfo.setStatusCode(19270);
          paramBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(19270, null, "OFX_MESSAGE"));
        }
        else
        {
          paramBPWFIInfo.setStatusCode(0);
          paramBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "OFX_MESSAGE"));
        }
      }
      else
      {
        paramBPWFIInfo.setStatusCode(0);
        paramBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "OFX_MESSAGE"));
      }
      FFSDebug.log("BPWFI.createWithGivenID: Number of records added (BPW_FIInfo) " + i, 6);
      FFSDebug.log("***BPWFI.createWithGivenID end", 6);
      return paramBPWFIInfo;
    }
    catch (Throwable localThrowable)
    {
      localObject2 = "***BPWFI.createWithGivenID failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException(localThrowable, (String)localObject2);
    }
  }
  
  public static BPWFIInfo modify(FFSConnectionHolder paramFFSConnectionHolder, BPWFIInfo paramBPWFIInfo)
    throws FFSException
  {
    String str1 = "BPWFI.modify: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramBPWFIInfo == null)
    {
      localObject = "***" + str1 + " failed: Null FI Object passed";
      FFSDebug.log((String)localObject, 0);
      localBPWFIInfo = new BPWFIInfo();
      localBPWFIInfo.setStatusCode(16000);
      localBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "BPWFIInfo " }, "OFX_MESSAGE"));
      return localBPWFIInfo;
    }
    if ((paramBPWFIInfo.getFIName() == null) || (paramBPWFIInfo.getFIName().trim().length() == 0))
    {
      paramBPWFIInfo.setStatusCode(16010);
      localObject = "FIName   contains null ";
      paramBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "BPWFIInfo", "FIName  " }, "OFX_MESSAGE"));
      FFSDebug.log(str1 + (String)localObject, 0);
      return paramBPWFIInfo;
    }
    if ((paramBPWFIInfo.getFIRTN() == null) || (paramBPWFIInfo.getFIRTN().trim().length() == 0))
    {
      paramBPWFIInfo.setStatusCode(16010);
      localObject = "FIRTN   contains null ";
      paramBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "BPWFIInfo", "FIRTN  " }, "OFX_MESSAGE"));
      FFSDebug.log("***" + str1 + (String)localObject, 0);
      return paramBPWFIInfo;
    }
    if (!jdMethod_do(paramBPWFIInfo)) {
      return paramBPWFIInfo;
    }
    Object localObject = m(paramFFSConnectionHolder, paramBPWFIInfo.getFIRTN());
    if ((((BPWFIInfo)localObject).getStatusCode() == 0) && (((BPWFIInfo)localObject).getFIId() != null) && (((BPWFIInfo)localObject).getFIId().equals(paramBPWFIInfo.getFIId()) != true))
    {
      paramBPWFIInfo.setStatusCode(16130);
      paramBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16130, null, "OFX_MESSAGE"));
      return paramBPWFIInfo;
    }
    BPWFIInfo localBPWFIInfo = getBPWFIInfo(paramFFSConnectionHolder, paramBPWFIInfo.getFIId());
    if (localBPWFIInfo.getStatusCode() != 0)
    {
      paramBPWFIInfo.setStatusCode(localBPWFIInfo.getStatusCode());
      paramBPWFIInfo.setStatusMsg(localBPWFIInfo.getStatusMsg());
      FFSDebug.log(str1 + paramBPWFIInfo.getStatusMsg(), 0);
    }
    a(paramFFSConnectionHolder, paramBPWFIInfo, localBPWFIInfo);
    String str2 = "UPDATE BPW_FIInfo SET FIRTN = ?, FIType = ?, FIName = ?, NickName = ?, FIDesc = ?, FIGroup = ?, FIRank = ?, AddrLine1 = ?, AddrLine2 = ?, AddrLine3 = ?,  City = ?, State = ?, PostalCode = ?, Country = ?, DayPhone = ?, Extension = ?, ContactName = ?, AmtLimit = ?, SWIFTRTN = ?, CHIPSRTN = ?, OtherRTN = ?, CurrencyCode = ?, ACHTransWarehouse = ?, ACHMaxNoFutureDays = ?, ACHSameDayEffDate = ?, ETFDepositAcct = ?, ETFDepositAcctType = ?, ETFMinDepositAmount = ?, ETFMaxDepositAmount = ? WHERE FIId = ? AND FIStatus != 'CLOSED'";
    if ((paramBPWFIInfo.getCurrencyCode() == null) || (paramBPWFIInfo.getCurrencyCode().trim().equals(""))) {
      paramBPWFIInfo.setCurrencyCode(BPWLocaleUtil.getCurCode());
    }
    Object[] arrayOfObject = { paramBPWFIInfo.getFIRTN(), paramBPWFIInfo.getFIType(), paramBPWFIInfo.getFIName(), paramBPWFIInfo.getNickName(), paramBPWFIInfo.getFIDesc(), paramBPWFIInfo.getFIGroup(), paramBPWFIInfo.getFIRank(), paramBPWFIInfo.getAddr1(), paramBPWFIInfo.getAddr2(), paramBPWFIInfo.getAddr3(), paramBPWFIInfo.getCity(), paramBPWFIInfo.getState(), paramBPWFIInfo.getPostalCode(), paramBPWFIInfo.getCountry(), paramBPWFIInfo.getDayPhone(), paramBPWFIInfo.getExtension(), paramBPWFIInfo.getContactName(), paramBPWFIInfo.getAmtLimit(), paramBPWFIInfo.getSwiftRTN(), paramBPWFIInfo.getChipsRTN(), paramBPWFIInfo.getOtherRTN(), paramBPWFIInfo.getCurrencyCode(), new Integer(paramBPWFIInfo.getACHTransWareHouse()), new Integer(paramBPWFIInfo.getACHMaxNoFutureDays()), paramBPWFIInfo.getACHSameDayEffDate(), paramBPWFIInfo.getETFDepositAcct(), paramBPWFIInfo.getETFDepositAcctType(), new Integer(paramBPWFIInfo.getETFMinDepositAmt()), new Integer(paramBPWFIInfo.getETFMaxDepositAmt()), paramBPWFIInfo.getFIId() };
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      FFSDebug.log(str1 + " Number of records updated (BPW_FIInfo) " + i, 6);
      if (i > 1)
      {
        paramFFSConnectionHolder.conn.rollback();
        paramBPWFIInfo.setStatusCode(23270);
        paramBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(23270, new String[] { "BPWFIInfo ", " fIId =", paramBPWFIInfo.getFIId() }, "OFX_MESSAGE"));
      }
      else if (i == 0)
      {
        paramBPWFIInfo.setStatusCode(16020);
        paramBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "BPWFIInfo " }, "OFX_MESSAGE"));
      }
      else
      {
        if (null != paramBPWFIInfo.getMemo())
        {
          FFSDebug.log(str1 + " Memo is not null:", 6);
          int j = BPWExtraInfo.processXtraInfo(paramFFSConnectionHolder, paramBPWFIInfo.getFIId(), paramBPWFIInfo.getFIId(), "BPWFI", paramBPWFIInfo.getMemo());
          if (j != 0)
          {
            paramBPWFIInfo.setStatusCode(19270);
            paramBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(19270, null, "OFX_MESSAGE"));
          }
          else
          {
            paramBPWFIInfo.setStatusCode(0);
            paramBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "OFX_MESSAGE"));
          }
        }
        else
        {
          paramBPWFIInfo.setStatusCode(0);
          paramBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "OFX_MESSAGE"));
        }
        paramBPWFIInfo.setETFVirtualUserID(n(paramFFSConnectionHolder, paramBPWFIInfo.getFIId()));
        paramBPWFIInfo.setETFCompanyID(l(paramFFSConnectionHolder, paramBPWFIInfo.getFIId()));
      }
      FFSDebug.log(str1 + " end", 6);
      return paramBPWFIInfo;
    }
    catch (Throwable localThrowable)
    {
      String str3 = "*** " + str1 + " failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable, str3);
    }
  }
  
  public static BPWFIInfo canBPWFIInfo(FFSConnectionHolder paramFFSConnectionHolder, BPWFIInfo paramBPWFIInfo)
    throws FFSException
  {
    FFSDebug.log("***BPWFI.cancel start ...", 6);
    Object localObject2;
    if (paramBPWFIInfo == null)
    {
      localObject1 = "***BPWFIInfo.canBPWFIInfo failed: FI Object passed is null";
      FFSDebug.log((String)localObject1, 0);
      localObject2 = new BPWFIInfo();
      ((BPWFIInfo)localObject2).setStatusCode(16000);
      ((BPWFIInfo)localObject2).setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "BPWFIInfo " }, "OFX_MESSAGE"));
      return localObject2;
    }
    if (paramBPWFIInfo.getFIId() == null)
    {
      localObject1 = "***BPWFIInfo.canBPWFIInfo failed: FIId is null";
      FFSDebug.log((String)localObject1, 0);
      paramBPWFIInfo.setStatusCode(16010);
      paramBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "BPWFIInfo ", " FIId" }, "OFX_MESSAGE"));
      return paramBPWFIInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject2 = new Object[] { paramBPWFIInfo.getFIId() };
      str = "UPDATE BPW_FIInfo SET FIStatus = 'CLOSED' WHERE FIId = ? AND FIStatus != 'CLOSED'";
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str, (Object[])localObject2);
      FFSDebug.log("BPWFI.canBPWFIInfo: Number of BPWFIInfo records deleted " + i, 6);
      FFSDebug.log("***BPWFI.canBPWFIInfo end", 6);
      Object localObject3;
      if (i > 1)
      {
        paramFFSConnectionHolder.conn.rollback();
        paramBPWFIInfo.setStatusCode(23270);
        paramBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(23270, new String[] { "BPWFIInfo " }, "OFX_MESSAGE"));
        localObject3 = paramBPWFIInfo;
        return localObject3;
      }
      if (i == 0)
      {
        paramBPWFIInfo.setStatusCode(16020);
        paramBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "BPWFIInfo " }, "OFX_MESSAGE"));
      }
      else
      {
        str = "SELECT ODFIACHId, FIId, ODFIName, RDFIACHId, RDFIName,  SubmitDate, ActivationDate, FIStatus, LogId, CashConDFI  FROM ACH_FIInfo WHERE FIId = ? AND FIStatus != 'CLOSED'";
        localObject1 = DBUtil.openResultSet(paramFFSConnectionHolder, str, (Object[])localObject2);
        localObject3 = null;
        while (((FFSResultSet)localObject1).getNextRow())
        {
          localObject3 = new ACHFIInfo();
          ((ACHFIInfo)localObject3).setODFIACHId(((FFSResultSet)localObject1).getColumnString(1));
          ACHFI.canACHFIInfo(paramFFSConnectionHolder, (ACHFIInfo)localObject3);
        }
        paramBPWFIInfo.setFIStatus("CLOSED");
        paramBPWFIInfo.setStatusCode(0);
        paramBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "OFX_MESSAGE"));
      }
    }
    catch (Throwable localThrowable1)
    {
      String str = "*** BPWFI.cancelBPWFI failed: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable1, str);
    }
    finally
    {
      if (localObject1 != null) {
        try
        {
          ((FFSResultSet)localObject1).close();
          localObject1 = null;
        }
        catch (Throwable localThrowable2)
        {
          FFSDebug.log("BPWFI.cancelBPWFI: failed " + FFSDebug.stackTrace(localThrowable2), 0);
        }
      }
    }
    return paramBPWFIInfo;
  }
  
  public static BPWFIInfo getBPWFIInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("***BPWFI.getBPWFIInfo start ...fIId =" + paramString, 6);
    if (paramString == null)
    {
      str = "***BPWFIInfoProcessor.canBPWFIInfo failed: FI Id passed is null";
      FFSDebug.log(str, 0);
      localObject1 = new BPWFIInfo();
      ((BPWFIInfo)localObject1).setStatusCode(16010);
      ((BPWFIInfo)localObject1).setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "BPWFIInfo ", " FIId" }, "OFX_MESSAGE"));
      return localObject1;
    }
    String str = "SELECT FIId, FIRTN, FIType, FIName, NickName, FIStatus, FIDesc, FIGroup, FIRank, AddrLine1, AddrLine2, AddrLine3, City, State, PostalCode, Country, DayPhone, Extension, SubmitDate, ContactName , ActivationDate, AmtLimit, SWIFTRTN, CHIPSRTN, OtherRTN, LogId, CurrencyCode, ACHTransWarehouse, ACHMaxNoFutureDays, ACHSameDayEffDate, ETFDepositAcct, ETFDepositAcctType, ETFMinDepositAmount, ETFMaxDepositAmount FROM BPW_FIInfo WHERE FIId = ?  AND FIStatus != 'CLOSED'";
    Object localObject1 = { paramString };
    FFSResultSet localFFSResultSet = null;
    Hashtable localHashtable = null;
    try
    {
      BPWFIInfo localBPWFIInfo = new BPWFIInfo();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, (Object[])localObject1);
      if (localFFSResultSet.getNextRow())
      {
        localBPWFIInfo = a(localFFSResultSet, localBPWFIInfo);
        FFSDebug.log("BPWFI.getBPWFIInfo XtraInfo:FIID:", localBPWFIInfo.getFIId(), 6);
        localHashtable = BPWExtraInfo.getXtraInfo(paramFFSConnectionHolder, paramString, paramString, "BPWFI");
        localBPWFIInfo.setMemo(localHashtable);
        FFSDebug.log("BPWFI.getBPWFIInfo ", "XtraInfo:" + localHashtable, 6);
        localBPWFIInfo.setETFVirtualUserID(n(paramFFSConnectionHolder, paramString));
        localBPWFIInfo.setETFCompanyID(l(paramFFSConnectionHolder, paramString));
      }
      else
      {
        localBPWFIInfo.setStatusCode(16020);
        localBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "BPWFIInfo " }, "OFX_MESSAGE"));
      }
      FFSDebug.log("***BPWFI.getBPWFIInfo end", 6);
      localObject2 = localBPWFIInfo;
      return localObject2;
    }
    catch (Throwable localThrowable1)
    {
      Object localObject2 = "*** BPWFI.getBPWFIInfo failed: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException(localThrowable1, (String)localObject2);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Throwable localThrowable2)
        {
          FFSDebug.log("BPWFI.getBPWFIInfo: failed " + FFSDebug.stackTrace(localThrowable2), 0);
        }
      }
    }
  }
  
  public static BPWFIInfo[] getBPWFIInfoByType(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("***BPWFI.getBPWFIInfoByType start ...", 6);
    ArrayList localArrayList = new ArrayList();
    BPWFIInfo localBPWFIInfo = null;
    String str = "SELECT FIId, FIRTN, FIType, FIName, NickName, FIStatus, FIDesc, FIGroup, FIRank, AddrLine1, AddrLine2, AddrLine3, City, State, PostalCode, Country, DayPhone, Extension, SubmitDate, ContactName , ActivationDate, AmtLimit, SWIFTRTN, CHIPSRTN, OtherRTN, LogId, CurrencyCode, ACHTransWarehouse, ACHMaxNoFutureDays, ACHSameDayEffDate, ETFDepositAcct, ETFDepositAcctType, ETFMinDepositAmount, ETFMaxDepositAmount FROM BPW_FIInfo WHERE FIType = ? AND FIStatus != 'CLOSED'";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    Hashtable localHashtable = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localBPWFIInfo = new BPWFIInfo();
        localBPWFIInfo = a(localFFSResultSet, localBPWFIInfo);
        FFSDebug.log("BPWFI.getBPWFIInfoByType XtraInfo:FIID:", localBPWFIInfo.getFIId(), 6);
        localHashtable = BPWExtraInfo.getXtraInfo(paramFFSConnectionHolder, localBPWFIInfo.getFIId(), localBPWFIInfo.getFIId(), "BPWFI");
        localBPWFIInfo.setMemo(localHashtable);
        FFSDebug.log("BPWFI.getBPWFIInfoByType ", "XtraInfo:" + localHashtable, 6);
        localBPWFIInfo.setETFVirtualUserID(n(paramFFSConnectionHolder, localBPWFIInfo.getFIId()));
        localBPWFIInfo.setETFCompanyID(l(paramFFSConnectionHolder, localBPWFIInfo.getFIId()));
        localArrayList.add(localBPWFIInfo);
      }
      if (localBPWFIInfo == null)
      {
        localBPWFIInfo = new BPWFIInfo();
        localBPWFIInfo.setStatusCode(16020);
        localBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "BPWFIInfo" }, "OFX_MESSAGE"));
        localArrayList.add(localBPWFIInfo);
      }
      BPWFIInfo[] arrayOfBPWFIInfo = (BPWFIInfo[])localArrayList.toArray(new BPWFIInfo[0]);
      FFSDebug.log("***BPWFI.getBPWFIInfoByType end", 6);
      localObject1 = arrayOfBPWFIInfo;
      return localObject1;
    }
    catch (Throwable localThrowable1)
    {
      Object localObject1 = "*** BPWFI.getBPWFIInfoByType failed: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Throwable localThrowable2)
        {
          FFSDebug.log("BPWFI.getBPWFIInfoByType: failed " + FFSDebug.stackTrace(localThrowable2), 0);
        }
      }
    }
  }
  
  public static BPWFIInfo[] getBPWFIInfoByStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("***BPWFI.getBPWFIInfoByStatus start ...", 6);
    ArrayList localArrayList = new ArrayList();
    BPWFIInfo localBPWFIInfo = null;
    String str = "SELECT FIId, FIRTN, FIType, FIName, NickName, FIStatus, FIDesc, FIGroup, FIRank, AddrLine1, AddrLine2, AddrLine3, City, State, PostalCode, Country, DayPhone, Extension, SubmitDate, ContactName , ActivationDate, AmtLimit, SWIFTRTN, CHIPSRTN, OtherRTN, LogId, CurrencyCode, ACHTransWarehouse, ACHMaxNoFutureDays, ACHSameDayEffDate, ETFDepositAcct, ETFDepositAcctType, ETFMinDepositAmount, ETFMaxDepositAmount FROM BPW_FIInfo WHERE FIStatus = ? AND FIStatus != 'CLOSED'";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    Hashtable localHashtable = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localBPWFIInfo = new BPWFIInfo();
        localBPWFIInfo = a(localFFSResultSet, localBPWFIInfo);
        FFSDebug.log("BPWFI.getBPWFIInfo XtraInfo:FIID:", localBPWFIInfo.getFIId(), 6);
        localHashtable = BPWExtraInfo.getXtraInfo(paramFFSConnectionHolder, localBPWFIInfo.getFIId(), localBPWFIInfo.getFIId(), "BPWFI");
        localBPWFIInfo.setMemo(localHashtable);
        FFSDebug.log("BPWFI.getBPWFIInfo ", "XtraInfo:" + localHashtable, 6);
        localBPWFIInfo.setETFVirtualUserID(n(paramFFSConnectionHolder, localBPWFIInfo.getFIId()));
        localBPWFIInfo.setETFCompanyID(l(paramFFSConnectionHolder, localBPWFIInfo.getFIId()));
        localArrayList.add(localBPWFIInfo);
      }
      if (localBPWFIInfo == null)
      {
        localBPWFIInfo = new BPWFIInfo();
        localBPWFIInfo.setStatusCode(16020);
        localBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "BPWFIInfo" }, "OFX_MESSAGE"));
        localArrayList.add(localBPWFIInfo);
      }
      BPWFIInfo[] arrayOfBPWFIInfo = (BPWFIInfo[])localArrayList.toArray(new BPWFIInfo[0]);
      FFSDebug.log("***BPWFI.getBPWFIInfoByStatus end", 6);
      localObject1 = arrayOfBPWFIInfo;
      return localObject1;
    }
    catch (Throwable localThrowable1)
    {
      Object localObject1 = "*** BPWFI.getBPWFIInfoByStatus failed: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Throwable localThrowable2)
        {
          FFSDebug.log("BPWFI.getBPWFIInfoByStatus: failed " + FFSDebug.stackTrace(localThrowable2), 0);
        }
      }
    }
  }
  
  public static BPWFIInfo[] getBPWFIInfoByGroup(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("***BPWFI.getBPWFIInfoByGroup start ...", 6);
    ArrayList localArrayList = new ArrayList();
    BPWFIInfo localBPWFIInfo = null;
    String str = "SELECT FIId, FIRTN, FIType, FIName, NickName, FIStatus, FIDesc, FIGroup, FIRank, AddrLine1, AddrLine2, AddrLine3, City, State, PostalCode, Country, DayPhone, Extension, SubmitDate, ContactName , ActivationDate, AmtLimit, SWIFTRTN, CHIPSRTN, OtherRTN, LogId, CurrencyCode, ACHTransWarehouse, ACHMaxNoFutureDays, ACHSameDayEffDate, ETFDepositAcct, ETFDepositAcctType, ETFMinDepositAmount, ETFMaxDepositAmount FROM BPW_FIInfo WHERE FIGroup = ? AND FIStatus != 'CLOSED'";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    Hashtable localHashtable = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localBPWFIInfo = new BPWFIInfo();
        localBPWFIInfo = a(localFFSResultSet, localBPWFIInfo);
        FFSDebug.log("BPWFI.getBPWFIInfoByGroup XtraInfo:FIID:", localBPWFIInfo.getFIId(), 6);
        localHashtable = BPWExtraInfo.getXtraInfo(paramFFSConnectionHolder, localBPWFIInfo.getFIId(), localBPWFIInfo.getFIId(), "BPWFI");
        localBPWFIInfo.setMemo(localHashtable);
        FFSDebug.log("BPWFI.getBPWFIInfoByGroup ", "XtraInfo:" + localHashtable, 6);
        localBPWFIInfo.setETFVirtualUserID(n(paramFFSConnectionHolder, localBPWFIInfo.getFIId()));
        localBPWFIInfo.setETFCompanyID(l(paramFFSConnectionHolder, localBPWFIInfo.getFIId()));
        localArrayList.add(localBPWFIInfo);
      }
      if (localBPWFIInfo == null)
      {
        localBPWFIInfo = new BPWFIInfo();
        localBPWFIInfo.setStatusCode(16020);
        localBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "BPWFIInfo" }, "OFX_MESSAGE"));
        localArrayList.add(localBPWFIInfo);
      }
      BPWFIInfo[] arrayOfBPWFIInfo = (BPWFIInfo[])localArrayList.toArray(new BPWFIInfo[0]);
      FFSDebug.log("***BPWFI.getBPWFIInfoByGroup end", 6);
      localObject1 = arrayOfBPWFIInfo;
      return localObject1;
    }
    catch (Throwable localThrowable1)
    {
      Object localObject1 = "*** BPWFI.getBPWFIInfoByGroup failed: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Throwable localThrowable2)
        {
          FFSDebug.log("BPWFI.getBPWFIInfoByGroup: failed " + FFSDebug.stackTrace(localThrowable2), 0);
        }
      }
    }
  }
  
  public static BPWFIInfo getBPWFIInfoByACHId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("***BPWFI.BPWFIInfoByACHId start. The odFIACHId is " + paramString, 6);
    BPWFIInfo localBPWFIInfo = null;
    ACHFIInfo localACHFIInfo = ACHFI.getACHFIInfo(paramFFSConnectionHolder, paramString);
    String str = localACHFIInfo.getFIId();
    if (str == null)
    {
      localBPWFIInfo = new BPWFIInfo();
      localBPWFIInfo.setStatusCode(16020);
      localBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "BPWFIInfo" }, "OFX_MESSAGE"));
    }
    else
    {
      localBPWFIInfo = getBPWFIInfo(paramFFSConnectionHolder, str);
    }
    return localBPWFIInfo;
  }
  
  public static String getBPWFIStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("***BPWFI.getBPWFIStatus start ...", 6);
    String str1 = "SELECT FIStatus FROM BPW_FIInfo WHERE FIId = ?";
    String str2 = null;
    if (paramString == null) {
      return null;
    }
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        str2 = localFFSResultSet.getColumnString(1);
      }
      String str3 = str2;
      return str3;
    }
    catch (Throwable localThrowable1)
    {
      String str4 = "*** BPWFI.getBPWFIStatus failed: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str4, 0);
      throw new FFSException(localThrowable1, str4);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Throwable localThrowable2)
        {
          FFSDebug.log("BPWFI.getBPWFIStatus: failed " + FFSDebug.stackTrace(localThrowable2), 0);
        }
      }
    }
  }
  
  public static int updateStatusForBPWFI(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    BPWFIInfo localBPWFIInfo = null;
    if ((paramString1 == null) || (paramString2 == null))
    {
      localBPWFIInfo = new BPWFIInfo();
      localBPWFIInfo.setStatusCode(16000);
      str1 = "ACHFIInfo object  is null";
      localBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "ACHFIInfo " }, "OFX_MESSAGE"));
      FFSDebug.log("ACHFI.updateStatusForBPWFI, " + str1);
      return -1;
    }
    FFSDebug.log("ACHFI.updateStatusForBPWFI: start, fIId=" + paramString1, 6);
    String str1 = "UPDATE BPW_FIInfo SET FIStatus = ?  WHERE FIId = ?";
    int i = 0;
    Object[] arrayOfObject = { paramString2, paramString1 };
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ACHFI.updateStatusForBPWFI failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
    FFSDebug.log("ACHFI.updateStatusForBPWFI: done", 6);
    return i;
  }
  
  public static BPWFIInfo activateBPWFI(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("BPWFI.activateBPWFI: start, fIID =" + paramString, 6);
    BPWFIInfo localBPWFIInfo1 = null;
    if (paramString == null)
    {
      localBPWFIInfo1 = new BPWFIInfo();
      localBPWFIInfo1.setStatusCode(16000);
      localBPWFIInfo1.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "fIID " }, "OFX_MESSAGE"));
      FFSDebug.log("FI ID is Null ", 0);
      return localBPWFIInfo1;
    }
    FFSResultSet localFFSResultSet = null;
    Hashtable localHashtable = null;
    String str1 = "SELECT FIId, FIRTN, FIType, FIName, NickName, FIStatus, FIDesc, FIGroup, FIRank, AddrLine1, AddrLine2, AddrLine3, City, State, PostalCode, Country, DayPhone, Extension, SubmitDate, ContactName , ActivationDate, AmtLimit, SWIFTRTN, CHIPSRTN, OtherRTN, LogId, CurrencyCode, ACHTransWarehouse, ACHMaxNoFutureDays, ACHSameDayEffDate, ETFDepositAcct, ETFDepositAcctType, ETFMinDepositAmount, ETFMaxDepositAmount FROM BPW_FIInfo WHERE FIId = ? ";
    Object[] arrayOfObject = { paramString };
    try
    {
      localBPWFIInfo1 = new BPWFIInfo();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localBPWFIInfo1 = a(localFFSResultSet, localBPWFIInfo1);
        FFSDebug.log("BPWFI.activateBPWFI XtraInfo:FIID:", localBPWFIInfo1.getFIId(), 6);
        localHashtable = BPWExtraInfo.getXtraInfo(paramFFSConnectionHolder, localBPWFIInfo1.getFIId(), localBPWFIInfo1.getFIId(), "BPWFI");
        localBPWFIInfo1.setMemo(localHashtable);
        FFSDebug.log("BPWFI.activateBPWFI ", "XtraInfo:" + localHashtable, 6);
        localBPWFIInfo1.setETFVirtualUserID(n(paramFFSConnectionHolder, localBPWFIInfo1.getFIId()));
        localBPWFIInfo1.setETFCompanyID(l(paramFFSConnectionHolder, localBPWFIInfo1.getFIId()));
        if (!localBPWFIInfo1.getFIStatus().equals("CLOSED"))
        {
          localBPWFIInfo1.setStatusCode(23260);
          localBPWFIInfo1.setStatusMsg(BPWLocaleUtil.getMessage(23260, new String[] { ", fIID ", paramString }, "OFX_MESSAGE"));
          BPWFIInfo localBPWFIInfo2 = localBPWFIInfo1;
          return localBPWFIInfo2;
        }
        str1 = "Update BPW_FIInfo set FIStatus = ?, ActivationDate = ?  Where FIId = ?";
        int i = 0;
        localBPWFIInfo1.setActivationDate(FFSUtil.getDateString());
        localBPWFIInfo1.setFIStatus("ACTIVE");
        localObject1 = new Object[] { localBPWFIInfo1.getFIStatus(), localBPWFIInfo1.getActivationDate(), localBPWFIInfo1.getFIId() };
        try
        {
          i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, (Object[])localObject1);
        }
        catch (Exception localException)
        {
          String str2 = "*** BPWFI.activateBPWFI failed: ";
          String str3 = FFSDebug.stackTrace(localException);
          FFSDebug.log(str3, 0);
          throw new FFSException(localException, str2);
        }
        localBPWFIInfo1.setStatusCode(0);
        localBPWFIInfo1.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "OFX_MESSAGE"));
      }
      else
      {
        localBPWFIInfo1.setStatusCode(16020);
        localBPWFIInfo1.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "BPWFIInfo " }, "OFX_MESSAGE"));
      }
    }
    catch (Throwable localThrowable1)
    {
      Object localObject1 = "*** BPWFI.activateBPWFI failed: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Throwable localThrowable2)
        {
          FFSDebug.log("BPWFI.activateBPWFI: failed " + FFSDebug.stackTrace(localThrowable2), 0);
        }
      }
    }
    FFSDebug.log("BPWFI.activateBPWFI: done", 6);
    return localBPWFIInfo1;
  }
  
  public static String[] getAllBPWFIIds(FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("***BPWFI.getAllBPWFIIds start ...", 6);
    String str1 = "SELECT FIId FROM BPW_FIInfo WHERE FIStatus != 'CLOSED'";
    ArrayList localArrayList = new ArrayList();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, null);
      while (localFFSResultSet.getNextRow()) {
        localArrayList.add(localFFSResultSet.getColumnString("FIID"));
      }
    }
    catch (Throwable localThrowable1)
    {
      String str2 = "*** BPWFI.getAllBPWFIIds failed: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable1, str2);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Throwable localThrowable2)
        {
          FFSDebug.log("BPWFI.getAllBPWFIIds: failed " + FFSDebug.stackTrace(localThrowable2), 0);
        }
      }
    }
    return (String[])localArrayList.toArray(new String[0]);
  }
  
  private static BPWFIInfo a(FFSResultSet paramFFSResultSet, BPWFIInfo paramBPWFIInfo)
    throws FFSException
  {
    paramBPWFIInfo.setFIId(paramFFSResultSet.getColumnString("FIId"));
    paramBPWFIInfo.setFIRTN(paramFFSResultSet.getColumnString("FIRTN"));
    paramBPWFIInfo.setFIType(paramFFSResultSet.getColumnString("FIType"));
    paramBPWFIInfo.setFIName(paramFFSResultSet.getColumnString("FIName"));
    paramBPWFIInfo.setNickName(paramFFSResultSet.getColumnString("NickName"));
    paramBPWFIInfo.setFIStatus(paramFFSResultSet.getColumnString("FIStatus"));
    paramBPWFIInfo.setFIDesc(paramFFSResultSet.getColumnString("FIDesc"));
    paramBPWFIInfo.setFIGroup(paramFFSResultSet.getColumnString("FIGroup"));
    paramBPWFIInfo.setFIRank(paramFFSResultSet.getColumnString("FIRank"));
    paramBPWFIInfo.setAddr1(paramFFSResultSet.getColumnString("AddrLine1"));
    paramBPWFIInfo.setAddr2(paramFFSResultSet.getColumnString("AddrLine2"));
    paramBPWFIInfo.setAddr3(paramFFSResultSet.getColumnString("AddrLine3"));
    paramBPWFIInfo.setCity(paramFFSResultSet.getColumnString("City"));
    paramBPWFIInfo.setState(paramFFSResultSet.getColumnString("State"));
    paramBPWFIInfo.setPostalCode(paramFFSResultSet.getColumnString("PostalCode"));
    paramBPWFIInfo.setCountry(paramFFSResultSet.getColumnString("Country"));
    paramBPWFIInfo.setDayPhone(paramFFSResultSet.getColumnString("DayPhone"));
    paramBPWFIInfo.setExtension(paramFFSResultSet.getColumnString("Extension"));
    paramBPWFIInfo.setSubmitDate(paramFFSResultSet.getColumnString("SubmitDate"));
    paramBPWFIInfo.setContactName(paramFFSResultSet.getColumnString("ContactName"));
    paramBPWFIInfo.setActivationDate(paramFFSResultSet.getColumnString("ActivationDate"));
    paramBPWFIInfo.setAmtLimit(paramFFSResultSet.getColumnString("AmtLimit"));
    paramBPWFIInfo.setSwiftRTN(paramFFSResultSet.getColumnString("SWIFTRTN"));
    paramBPWFIInfo.setChipsRTN(paramFFSResultSet.getColumnString("CHIPSRTN"));
    paramBPWFIInfo.setOtherRTN(paramFFSResultSet.getColumnString("OtherRTN"));
    paramBPWFIInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
    paramBPWFIInfo.setCurrencyCode(paramFFSResultSet.getColumnString("CurrencyCode"));
    paramBPWFIInfo.setACHTransWareHouse(paramFFSResultSet.getColumnInt("ACHTransWarehouse"));
    paramBPWFIInfo.setACHMaxNoFutureDays(paramFFSResultSet.getColumnInt("ACHMaxNoFutureDays"));
    paramBPWFIInfo.setACHSameDayEffDate(paramFFSResultSet.getColumnString("ACHSameDayEffDate"));
    paramBPWFIInfo.setETFDepositAcct(paramFFSResultSet.getColumnString("ETFDepositAcct"));
    paramBPWFIInfo.setETFDepositAcctType(paramFFSResultSet.getColumnString("ETFDepositAcctType"));
    paramBPWFIInfo.setETFMinDepositAmt(paramFFSResultSet.getColumnInt("ETFMinDepositAmount"));
    paramBPWFIInfo.setETFMaxDepositAmt(paramFFSResultSet.getColumnInt("ETFMaxDepositAmount"));
    paramBPWFIInfo.setStatusCode(0);
    paramBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "OFX_MESSAGE"));
    return paramBPWFIInfo;
  }
  
  private static boolean jdMethod_if(BPWFIInfo paramBPWFIInfo)
    throws BPWException
  {
    FFSDebug.log("BPWFI.vaildateBPWFIInfo: start", 6);
    String str;
    if ((paramBPWFIInfo.getFIRTN() == null) || (paramBPWFIInfo.getFIRTN().trim().length() == 0))
    {
      paramBPWFIInfo.setStatusCode(16010);
      str = "FIRTN   contains null ";
      paramBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "BPWFIInfo", "FIRTN  " }, "OFX_MESSAGE"));
      FFSDebug.log("BPWFI.vaildateBPWFIInfo, " + str, 0);
      return false;
    }
    if ((paramBPWFIInfo.getFIName() == null) || (paramBPWFIInfo.getFIName().trim().length() == 0))
    {
      paramBPWFIInfo.setStatusCode(16010);
      str = "FIName   contains null ";
      paramBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "BPWFIInfo", "FIName  " }, "OFX_MESSAGE"));
      FFSDebug.log("BPWFI.vaildateBPWFIInfo, " + str, 0);
      return false;
    }
    if (!jdMethod_do(paramBPWFIInfo)) {
      return false;
    }
    FFSDebug.log("BPWFI.vaildateBPWFIInfo: done", 6);
    return true;
  }
  
  private static boolean jdMethod_do(BPWFIInfo paramBPWFIInfo)
    throws BPWException
  {
    boolean bool = true;
    try
    {
      if (CommBankIdentifier.getBankIdentifierFlag()) {
        bool = CommBankIdentifier.isValidCheckZeros(paramBPWFIInfo.getFIRTN());
      } else {
        bool = BPWUtil.validateABA(paramBPWFIInfo.getFIRTN());
      }
      if (!bool)
      {
        paramBPWFIInfo.setStatusCode(23670);
        paramBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(23670, new String[] { " : ", paramBPWFIInfo.getFIRTN() }, "OFX_MESSAGE"));
      }
    }
    catch (Exception localException)
    {
      paramBPWFIInfo.setStatusCode(23670);
      paramBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(23670, new String[] { ". Exception: ", localException.toString() }, "OFX_MESSAGE"));
      bool = false;
    }
    return bool;
  }
  
  public static BPWFIInfo getBPWFIInfoByRTN(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("***BPWFI.getBPWFIInfoByRTN start ...", 6);
    BPWFIInfo localBPWFIInfo = null;
    FFSDebug.log("BPWFI.getBPWFIInfoByRTN: start", 6);
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      localBPWFIInfo = new BPWFIInfo();
      localBPWFIInfo.setStatusCode(16000);
      String str = "FIRTN   is null";
      localBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "FIRTN  " }, "OFX_MESSAGE"));
      FFSDebug.log("BPWFI.getBPWFIInfoByRTN, " + str);
      return localBPWFIInfo;
    }
    localBPWFIInfo = m(paramFFSConnectionHolder, paramString);
    FFSDebug.log("***BPWFI.getBPWFIInfoByRTN done ...", 6);
    return localBPWFIInfo;
  }
  
  private static BPWFIInfo m(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWFI.getFIInfoByRTN: start", 6);
    String str = "SELECT FIId, FIRTN, FIType, FIName, NickName, FIStatus, FIDesc, FIGroup, FIRank, AddrLine1, AddrLine2, AddrLine3, City, State, PostalCode, Country, DayPhone, Extension, SubmitDate, ContactName , ActivationDate, AmtLimit, SWIFTRTN, CHIPSRTN, OtherRTN, LogId, CurrencyCode, ACHTransWarehouse, ACHMaxNoFutureDays, ACHSameDayEffDate, ETFDepositAcct, ETFDepositAcctType, ETFMinDepositAmount, ETFMaxDepositAmount FROM BPW_FIInfo WHERE FIRTN = ? AND FIStatus != 'CLOSED'";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    Hashtable localHashtable = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      BPWFIInfo localBPWFIInfo = new BPWFIInfo();
      if (localFFSResultSet.getNextRow())
      {
        localBPWFIInfo = a(localFFSResultSet, localBPWFIInfo);
        localHashtable = BPWExtraInfo.getXtraInfo(paramFFSConnectionHolder, localBPWFIInfo.getFIId(), localBPWFIInfo.getFIId(), "BPWFI");
        localBPWFIInfo.setMemo(localHashtable);
        localBPWFIInfo.setETFVirtualUserID(n(paramFFSConnectionHolder, localBPWFIInfo.getFIId()));
        localBPWFIInfo.setETFCompanyID(l(paramFFSConnectionHolder, localBPWFIInfo.getFIId()));
        localBPWFIInfo.setStatusCode(0);
        localBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "OFX_MESSAGE"));
      }
      else
      {
        localBPWFIInfo.setStatusCode(16020);
        localBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "BPWFIInfo" }, "OFX_MESSAGE"));
      }
      FFSDebug.log("BPWFI.getFIInfoByRTN: done", 6);
      localObject1 = localBPWFIInfo;
      return localObject1;
    }
    catch (Throwable localThrowable1)
    {
      Object localObject1 = "***BPWFI.getBPWFIInfoByRTN failed: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Throwable localThrowable2)
        {
          FFSDebug.log("BPWFI.getBPWFIInfoByRTN: failed " + FFSDebug.stackTrace(localThrowable2), 0);
        }
      }
    }
  }
  
  public static BPWFIInfo[] getBPWFIInfo(FFSConnectionHolder paramFFSConnectionHolder, String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("***BPWFI.getBPWFIInfo (Multiple) start", 6);
    BPWFIInfo localBPWFIInfo = null;
    ArrayList localArrayList = new ArrayList();
    Object localObject;
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      String str = "***BPWFI.getBPWFIInfo  (Multiple) failed: Null or Empty fIId Array passed";
      FFSDebug.log(str, 0);
      localBPWFIInfo = new BPWFIInfo();
      localBPWFIInfo.setStatusCode(16000);
      localBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "fIId " }, "OFX_MESSAGE"));
      localObject = new BPWFIInfo[] { localBPWFIInfo };
      return localObject;
    }
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      if (paramArrayOfString[i] == null)
      {
        localObject = "***BPWFI.getBPWFIInfo (Multiple) failedNull TemplateId Object passed";
        FFSDebug.log((String)localObject, 0);
        localBPWFIInfo = new BPWFIInfo();
        localBPWFIInfo.setStatusCode(16000);
        localBPWFIInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "BPWFIInfo " }, "OFX_MESSAGE"));
      }
      else
      {
        localBPWFIInfo = getBPWFIInfo(paramFFSConnectionHolder, paramArrayOfString[i]);
      }
      localArrayList.add(localBPWFIInfo);
    }
    return (BPWFIInfo[])localArrayList.toArray(new BPWFIInfo[0]);
  }
  
  private static String n(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
  {
    String str = null;
    try
    {
      CustomerInfo[] arrayOfCustomerInfo = Customer.getVirtualCustomerByFI(paramString, paramFFSConnectionHolder);
      if ((arrayOfCustomerInfo != null) && (arrayOfCustomerInfo.length > 0)) {
        str = arrayOfCustomerInfo[0].customerID;
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("BPWFI.getVirtualUserID Exception:" + localException, 6);
    }
    return str;
  }
  
  private static String l(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
  {
    String str = null;
    try
    {
      ExtTransferCompanyInfo[] arrayOfExtTransferCompanyInfo = ExternalTransferCompany.getETFCompanyByFIIDAndCustomerId(paramFFSConnectionHolder, paramString, null);
      if ((arrayOfExtTransferCompanyInfo != null) && (arrayOfExtTransferCompanyInfo.length > 0))
      {
        str = arrayOfExtTransferCompanyInfo[0].getCompId();
        FFSDebug.log("getFI_ETFCompanyID:" + str, 6);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("BPWFI.getFI_ETFCompanyID Exception:" + localException, 6);
    }
    return str;
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, BPWFIInfo paramBPWFIInfo1, BPWFIInfo paramBPWFIInfo2)
    throws FFSException
  {
    String str1 = paramBPWFIInfo1.getACHSameDayEffDate();
    if ((str1 == null) || (str1.compareTo("N") == 0))
    {
      String str2 = paramBPWFIInfo2.getACHSameDayEffDate();
      if ((str2 != null) && (str2.compareTo("Y") == 0))
      {
        CustomerInfo[] arrayOfCustomerInfo = Customer.getCustomerByFI(paramBPWFIInfo1.getFIId(), paramFFSConnectionHolder);
        int i = 0;
        if (arrayOfCustomerInfo != null) {
          i = arrayOfCustomerInfo.length;
        }
        ACHSameDayEffDateInfo localACHSameDayEffDateInfo = null;
        for (int j = 0; j < i; j++)
        {
          localACHSameDayEffDateInfo = new ACHSameDayEffDateInfo();
          localACHSameDayEffDateInfo.setObjectId(arrayOfCustomerInfo[j].customerID);
          localACHSameDayEffDateInfo.setObjectType(0);
          FFSDebug.log("BPWFI.modify: Deleting all SECs for Customer =" + arrayOfCustomerInfo[j].customerID, 6);
          ACHSameDayEffDate.setACHSameDayEffDateInfo(paramFFSConnectionHolder, localACHSameDayEffDateInfo);
          localACHSameDayEffDateInfo = null;
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.BPWFI
 * JD-Core Version:    0.7.0.1
 */