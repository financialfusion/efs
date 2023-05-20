package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWBankAcctInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class ACHCompOffsetAcct
  implements DBConsts, FFSConst, ACHConsts
{
  public static ACHCompOffsetAcctInfo createACHCompOffsetAcct(FFSConnectionHolder paramFFSConnectionHolder, ACHCompOffsetAcctInfo paramACHCompOffsetAcctInfo)
    throws FFSException
  {
    String str1 = "ACHCompOffsetAcct.createACHCompOffsetAcct";
    FFSDebug.log(str1 + ": start ...", 6);
    paramACHCompOffsetAcctInfo = a(paramACHCompOffsetAcctInfo);
    if (paramACHCompOffsetAcctInfo.getStatusCode() != 0) {
      return paramACHCompOffsetAcctInfo;
    }
    try
    {
      ACHCompanyInfo localACHCompanyInfo = ACHCompany.getCompanyInfo(paramACHCompOffsetAcctInfo.getCompId(), paramFFSConnectionHolder);
      if ((localACHCompanyInfo == null) || (localACHCompanyInfo.getStatusCode() != 0))
      {
        paramACHCompOffsetAcctInfo.setStatusCode(23430);
        paramACHCompOffsetAcctInfo.setStatusMsg("ACHCompany does not exist");
        localObject1 = paramACHCompOffsetAcctInfo;
        return localObject1;
      }
      localObject1 = ACHFI.getACHFIInfo(paramFFSConnectionHolder, localACHCompanyInfo.getODFIACHId());
      paramACHCompOffsetAcctInfo.setFIId(((ACHFIInfo)localObject1).getFIId());
      boolean bool = jdMethod_if(paramFFSConnectionHolder, paramACHCompOffsetAcctInfo.getCompId(), paramACHCompOffsetAcctInfo.getBankRTN(), paramACHCompOffsetAcctInfo.getAcctNumber());
      if (bool == true)
      {
        paramACHCompOffsetAcctInfo.setStatusCode(23410);
        localObject2 = "BPW Bank Account already exists AcctNumber: " + paramACHCompOffsetAcctInfo.getAcctNumber() + " BankRTN: " + paramACHCompOffsetAcctInfo.getBankRTN();
        paramACHCompOffsetAcctInfo.setStatusMsg((String)localObject2);
        FFSDebug.log(str1 + ": " + (String)localObject2, 6);
        localObject3 = paramACHCompOffsetAcctInfo;
        return localObject3;
      }
      paramACHCompOffsetAcctInfo = (ACHCompOffsetAcctInfo)BPWBankAcct.createBPWBankAcctinfo(paramFFSConnectionHolder, paramACHCompOffsetAcctInfo);
      if ((paramACHCompOffsetAcctInfo == null) || (paramACHCompOffsetAcctInfo.getStatusCode() != 0))
      {
        FFSDebug.log(str1 + " failed: " + (paramACHCompOffsetAcctInfo != null ? paramACHCompOffsetAcctInfo.getStatusMsg() : " null"));
        localObject2 = paramACHCompOffsetAcctInfo;
        return localObject2;
      }
      Object localObject2 = "INSERT INTO ACH_CompOffsetAcct (AcctId, CompId) VALUES (?, ?)";
      Object localObject3 = { paramACHCompOffsetAcctInfo.getAcctId(), paramACHCompOffsetAcctInfo.getCompId() };
      DBUtil.executeStatement(paramFFSConnectionHolder, (String)localObject2, (Object[])localObject3);
      paramACHCompOffsetAcctInfo.setStatusCode(0);
      paramACHCompOffsetAcctInfo.setStatusMsg("Success");
    }
    catch (Exception localException)
    {
      Object localObject1 = str1 + ": failed: ";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException, (String)localObject1 + str2);
    }
    finally
    {
      FFSDebug.log(str1 + " end.", 6);
    }
    return paramACHCompOffsetAcctInfo;
  }
  
  private static ACHCompOffsetAcctInfo a(ACHCompOffsetAcctInfo paramACHCompOffsetAcctInfo)
  {
    String str = "ACHCompOffsetAcct.validateCompOffsetAcctinfo";
    if (paramACHCompOffsetAcctInfo == null)
    {
      paramACHCompOffsetAcctInfo = new ACHCompOffsetAcctInfo();
      paramACHCompOffsetAcctInfo.setStatusCode(16000);
      paramACHCompOffsetAcctInfo.setStatusMsg("ACHCompOffsetAcctInfo:  is null");
      FFSDebug.log(str + ": " + " is null");
    }
    else
    {
      paramACHCompOffsetAcctInfo.setStatusCode(0);
    }
    return paramACHCompOffsetAcctInfo;
  }
  
  public static ACHCompOffsetAcctInfo getACHCompOffsetAcctByAcctId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "ACHCompOffsetAcct.getACHCompOffsetAcctByAcctId";
    FFSDebug.log(str1 + " start: account ID = " + paramString, 6);
    ACHCompOffsetAcctInfo localACHCompOffsetAcctInfo = new ACHCompOffsetAcctInfo();
    FFSResultSet localFFSResultSet = null;
    try
    {
      String str2 = "SELECT a.AcctId, a.AcctNumber, a.FIId, a.BankRTN, a.AcctType, a.NameOnAcct, a.AcctStatus, a.AcctNickName, a.AcctDesc, a.BankName, a.BranchName, a.AcctGroup, a.AcctRank, a.Currency, a.AddrLine1, a.AddrLine2, a.City, a.State, a.ZipCode, a.Country, a.DayPhone, a.SubmitDate, a.LastChangeDate, a.ActivationDate, a.AmtLimit, b.CompId FROM BPW_BankAcct a, ACH_CompOffsetAcct b WHERE a.AcctId = ? AND a.AcctId = b.AcctId AND a.AcctStatus != 'CLOSED' ";
      localObject1 = new Object[] { paramString };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject1);
      if (localFFSResultSet.getNextRow())
      {
        BPWBankAcct.setBPWBankAcctInfoDetails(localACHCompOffsetAcctInfo, localFFSResultSet);
        localACHCompOffsetAcctInfo.setCompId(localFFSResultSet.getColumnString("CompID"));
        localACHCompOffsetAcctInfo.setStatusCode(0);
        localACHCompOffsetAcctInfo.setStatusMsg("Success");
      }
      else
      {
        localACHCompOffsetAcctInfo.setStatusCode(16020);
        localACHCompOffsetAcctInfo.setStatusMsg("AcctId = " + paramString + ": " + " record not found");
      }
    }
    catch (Exception localException1)
    {
      Object localObject1 = str1 + ": failed: ";
      String str3 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException1, (String)localObject1 + str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log(str1 + " failed: " + FFSDebug.stackTrace(localException2), 0);
      }
      FFSDebug.log(str1 + " end.", 6);
    }
    return localACHCompOffsetAcctInfo;
  }
  
  private static String p(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    String str1 = "SELECT * from ACH_CompOffsetAcct WHERE AcctId = ?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
    String str2 = null;
    if (localFFSResultSet.getNextRow()) {
      str2 = localFFSResultSet.getColumnString("CompID");
    }
    if (localFFSResultSet != null)
    {
      localFFSResultSet.close();
      localFFSResultSet = null;
    }
    return str2;
  }
  
  private static String[] o(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    String str = "SELECT * from ACH_CompOffsetAcct WHERE CompId = ?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
    ArrayList localArrayList = new ArrayList();
    while (localFFSResultSet.getNextRow()) {
      localArrayList.add(localFFSResultSet.getColumnString("AcctId"));
    }
    if (localFFSResultSet != null)
    {
      localFFSResultSet.close();
      localFFSResultSet = null;
    }
    return (String[])localArrayList.toArray(new String[0]);
  }
  
  public static ACHCompOffsetAcctInfo[] getACHCompOffsetAcctsByCompId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "ACHCompOffsetAcct.getACHCompOffsetAcctsByCompId";
    FFSDebug.log(str1 + " start: compID = " + paramString, 6);
    ACHCompOffsetAcctInfo[] arrayOfACHCompOffsetAcctInfo = null;
    if ((paramString == null) || (paramString.length() == 0))
    {
      FFSDebug.log(str1 + "failed: compID " + " is null", 6);
      return arrayOfACHCompOffsetAcctInfo;
    }
    FFSResultSet localFFSResultSet = null;
    try
    {
      String str2 = "SELECT a.AcctId, a.AcctNumber, a.FIId, a.BankRTN, a.AcctType, a.NameOnAcct, a.AcctStatus, a.AcctNickName, a.AcctDesc, a.BankName, a.BranchName, a.AcctGroup, a.AcctRank, a.Currency, a.AddrLine1, a.AddrLine2, a.City, a.State, a.ZipCode, a.Country, a.DayPhone, a.SubmitDate, a.LastChangeDate, a.ActivationDate, a.AmtLimit, b.CompId FROM BPW_BankAcct a, ACH_CompOffsetAcct b WHERE b.CompId = ? AND a.AcctId = b.AcctId AND a.AcctStatus != 'CLOSED' ";
      localObject1 = new Object[] { paramString };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject1);
      localObject2 = new ArrayList();
      while (localFFSResultSet.getNextRow())
      {
        ACHCompOffsetAcctInfo localACHCompOffsetAcctInfo = new ACHCompOffsetAcctInfo();
        BPWBankAcct.setBPWBankAcctInfoDetails(localACHCompOffsetAcctInfo, localFFSResultSet);
        localACHCompOffsetAcctInfo.setCompId(localFFSResultSet.getColumnString("CompID"));
        localACHCompOffsetAcctInfo.setStatusCode(0);
        localACHCompOffsetAcctInfo.setStatusMsg("Success");
        ((ArrayList)localObject2).add(localACHCompOffsetAcctInfo);
      }
      arrayOfACHCompOffsetAcctInfo = (ACHCompOffsetAcctInfo[])((ArrayList)localObject2).toArray(new ACHCompOffsetAcctInfo[0]);
    }
    catch (Exception localException1)
    {
      Object localObject1 = str1 + ": failed: ";
      Object localObject2 = FFSDebug.stackTrace(localException1);
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException(localException1, (String)localObject1 + (String)localObject2);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log(str1 + " failed: " + FFSDebug.stackTrace(localException2), 0);
      }
      FFSDebug.log(str1 + " end.", 6);
    }
    return arrayOfACHCompOffsetAcctInfo;
  }
  
  public static ACHCompOffsetAcctInfo deleteACHCompOffsetAcct(FFSConnectionHolder paramFFSConnectionHolder, ACHCompOffsetAcctInfo paramACHCompOffsetAcctInfo)
    throws FFSException
  {
    String str1 = "ACHCompOffsetAcct.deleteACHCompOffsetAcct";
    FFSDebug.log(str1 + " start...", 6);
    paramACHCompOffsetAcctInfo = a(paramACHCompOffsetAcctInfo);
    if (paramACHCompOffsetAcctInfo.getStatusCode() != 0) {
      return paramACHCompOffsetAcctInfo;
    }
    FFSResultSet localFFSResultSet = null;
    try
    {
      String str2 = paramACHCompOffsetAcctInfo.getAcctId();
      str3 = "SELECT BatchId from ACH_Batch where OffsetAccountID = ?  and BatchStatus = 'WILLPROCESSON'";
      localObject1 = new Object[] { str2 };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, (Object[])localObject1);
      ACHCompOffsetAcctInfo localACHCompOffsetAcctInfo;
      if (localFFSResultSet.getNextRow())
      {
        paramACHCompOffsetAcctInfo.setStatusCode(17207);
        paramACHCompOffsetAcctInfo.setStatusMsg("An active transaction is present for this OffSetAcctId , Entry present in ACH_Batch");
        FFSDebug.log(str1 + ", " + paramACHCompOffsetAcctInfo.getStatusMsg(), 6);
        localACHCompOffsetAcctInfo = paramACHCompOffsetAcctInfo;
        return localACHCompOffsetAcctInfo;
      }
      str3 = "SELECT RecBatchId from ACH_RecBatch where OffsetAccountID = ?  and BatchStatus = 'WILLPROCESSON'";
      localObject1[0] = str2;
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, (Object[])localObject1);
      if (localFFSResultSet.getNextRow())
      {
        paramACHCompOffsetAcctInfo.setStatusCode(17207);
        paramACHCompOffsetAcctInfo.setStatusMsg("An active transaction is present for this OffSetAcctId , Entry present in ACH_RecBatch");
        FFSDebug.log(str1 + ", " + paramACHCompOffsetAcctInfo.getStatusMsg(), 6);
        localACHCompOffsetAcctInfo = paramACHCompOffsetAcctInfo;
        return localACHCompOffsetAcctInfo;
      }
      str3 = "SELECT RecordId from ACH_Record a, ACH_Batch b  Where a.BatchId=b.BatchId And a.OffsetAccountID = ?  And b.BatchStatus ='WILLPROCESSON' And a.RecordStatus = 'WILLPROCESSON'";
      localObject1[0] = str2;
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, (Object[])localObject1);
      if (localFFSResultSet.getNextRow())
      {
        paramACHCompOffsetAcctInfo.setStatusCode(17207);
        paramACHCompOffsetAcctInfo.setStatusMsg("An active transaction is present for this OffSetAcctId , Entry present in ACH_Record");
        FFSDebug.log(str1 + ", " + paramACHCompOffsetAcctInfo.getStatusMsg(), 6);
        localACHCompOffsetAcctInfo = paramACHCompOffsetAcctInfo;
        return localACHCompOffsetAcctInfo;
      }
      str3 = "SELECT a.RecRecordId from ACH_RecRecord a, ACH_RecBatch b  Where a.BatchId=b.RecBatchId And a.OffsetAccountID = ?  And b.BatchStatus ='WILLPROCESSON' And a.RecordStatus = 'WILLPROCESSON'";
      localObject1[0] = str2;
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, (Object[])localObject1);
      if (localFFSResultSet.getNextRow())
      {
        paramACHCompOffsetAcctInfo.setStatusCode(17207);
        paramACHCompOffsetAcctInfo.setStatusMsg("An active transaction is present for this OffSetAcctId , Entry present in ACH_RecRecord");
        FFSDebug.log(str1 + ", " + paramACHCompOffsetAcctInfo.getStatusMsg(), 6);
        localACHCompOffsetAcctInfo = paramACHCompOffsetAcctInfo;
        return localACHCompOffsetAcctInfo;
      }
      str3 = "DELETE FROM ACH_CompOffsetAcct WHERE AcctId = ?";
      localObject1[0] = str2;
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, (Object[])localObject1);
      BPWBankAcctInfo localBPWBankAcctInfo = BPWBankAcct.deleteBPWBankInfoByAcctId(paramFFSConnectionHolder, str2);
      paramACHCompOffsetAcctInfo.setStatusCode(localBPWBankAcctInfo.getStatusCode());
      paramACHCompOffsetAcctInfo.setStatusMsg(localBPWBankAcctInfo.getStatusMsg());
    }
    catch (Exception localException1)
    {
      String str3 = str1 + ": failed: ";
      Object localObject1 = FFSDebug.stackTrace(localException1);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localException1, str3 + (String)localObject1);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log(str1 + " failed: " + FFSDebug.stackTrace(localException2), 0);
      }
      FFSDebug.log(str1 + " end.", 6);
    }
    return paramACHCompOffsetAcctInfo;
  }
  
  public static ACHCompOffsetAcctInfo modACHCompOffsetAcct(FFSConnectionHolder paramFFSConnectionHolder, ACHCompOffsetAcctInfo paramACHCompOffsetAcctInfo)
    throws FFSException
  {
    String str1 = "ACHCompOffsetAcct.modACHCompOffsetAcct";
    FFSDebug.log(str1 + " start...", 6);
    paramACHCompOffsetAcctInfo = a(paramACHCompOffsetAcctInfo);
    if (paramACHCompOffsetAcctInfo.getStatusCode() != 0)
    {
      FFSDebug.log(str1 + " failed: " + paramACHCompOffsetAcctInfo.getStatusMsg());
      return paramACHCompOffsetAcctInfo;
    }
    try
    {
      boolean bool = jdMethod_if(paramFFSConnectionHolder, paramACHCompOffsetAcctInfo.getCompId(), paramACHCompOffsetAcctInfo.getBankRTN(), paramACHCompOffsetAcctInfo.getAcctNumber(), paramACHCompOffsetAcctInfo.getAcctId());
      if (bool == true)
      {
        paramACHCompOffsetAcctInfo.setStatusCode(23410);
        str2 = "BPW Bank Account already exists AcctNumber: " + paramACHCompOffsetAcctInfo.getAcctNumber() + " BankRTN: " + paramACHCompOffsetAcctInfo.getBankRTN();
        paramACHCompOffsetAcctInfo.setStatusMsg(str2);
        FFSDebug.log(str1 + ": " + str2, 6);
        localObject1 = paramACHCompOffsetAcctInfo;
        return localObject1;
      }
      paramACHCompOffsetAcctInfo = (ACHCompOffsetAcctInfo)BPWBankAcct.modBPWBankAcct(paramFFSConnectionHolder, paramACHCompOffsetAcctInfo);
    }
    catch (Exception localException)
    {
      String str2 = str1 + ": failed: ";
      Object localObject1 = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localException, str2 + (String)localObject1);
    }
    finally
    {
      FFSDebug.log(str1 + " end.", 6);
    }
    return paramACHCompOffsetAcctInfo;
  }
  
  public static HashSet getAcctIDsByCompId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "ACHCompOffsetAcct.getAcctIDsByCompId";
    FFSDebug.log(str1 + " start:  compID = " + paramString, 6);
    HashSet localHashSet = new HashSet();
    FFSResultSet localFFSResultSet = null;
    try
    {
      String str2 = " SELECT AcctId FROM ACH_CompOffsetAcct WHERE CompId = ?";
      localObject1 = new Object[] { paramString };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject1);
      while (localFFSResultSet.getNextRow()) {
        localHashSet.add(localFFSResultSet.getColumnString("AcctId"));
      }
    }
    catch (Exception localException1)
    {
      Object localObject1 = str1 + ": failed: ";
      String str3 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException1, (String)localObject1 + str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log(str1 + " failed: " + FFSDebug.stackTrace(localException2), 0);
      }
      FFSDebug.log(str1 + " end.", 6);
    }
    return localHashSet;
  }
  
  private static boolean jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    String str1 = "ACHCompOffsetAcct.exitACHCompOffsetAcctByCompIdBankRTNAcctNum";
    FFSDebug.log(str1 + " start: comp ID = " + paramString1, 6);
    FFSDebug.log(str1 + " start: bankRTN = " + paramString2, 6);
    FFSDebug.log(str1 + " start: bankAcctNum = " + paramString3, 6);
    ACHCompOffsetAcctInfo localACHCompOffsetAcctInfo = new ACHCompOffsetAcctInfo();
    FFSResultSet localFFSResultSet = null;
    boolean bool = false;
    try
    {
      String str2 = "SELECT a.AcctId, a.AcctNumber, a.FIId, a.BankRTN, a.AcctType, a.NameOnAcct, a.AcctStatus, a.AcctNickName, a.AcctDesc, a.BankName, a.BranchName, a.AcctGroup, a.AcctRank, a.Currency, a.AddrLine1, a.AddrLine2, a.City, a.State, a.ZipCode, a.Country, a.DayPhone, a.SubmitDate, a.LastChangeDate, a.ActivationDate, a.AmtLimit, b.CompId FROM BPW_BankAcct a, ACH_CompOffsetAcct b WHERE b.CompId = ? AND a.BankRTN = ? AND a.AcctNumber = ? AND a.AcctId = b.AcctId AND a.AcctStatus != 'CLOSED' ";
      localObject1 = new Object[] { paramString1, paramString2, paramString3 };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject1);
      if (localFFSResultSet.getNextRow()) {
        bool = true;
      } else {
        bool = false;
      }
    }
    catch (Exception localException1)
    {
      bool = false;
      Object localObject1 = str1 + ": failed: ";
      String str3 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException1, (String)localObject1 + str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log(str1 + " failed: " + FFSDebug.stackTrace(localException2), 0);
      }
    }
    FFSDebug.log(str1 + " end. exists: " + bool, 6);
    return bool;
  }
  
  private static boolean jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4)
    throws FFSException
  {
    String str1 = "ACHCompOffsetAcct.existACHCompOffsetAcctByCompIdBankRTNAcctNum";
    FFSDebug.log(str1 + " start: comp ID = " + paramString1, 6);
    FFSDebug.log(str1 + " start: bankRTN = " + paramString2, 6);
    FFSDebug.log(str1 + " start: bankAcctNum = " + paramString3, 6);
    FFSDebug.log(str1 + " start: acctId = " + paramString4, 6);
    ACHCompOffsetAcctInfo localACHCompOffsetAcctInfo = new ACHCompOffsetAcctInfo();
    FFSResultSet localFFSResultSet = null;
    boolean bool = false;
    try
    {
      String str2 = "SELECT a.AcctId, a.AcctNumber, a.FIId, a.BankRTN, a.AcctType, a.NameOnAcct, a.AcctStatus, a.AcctNickName, a.AcctDesc, a.BankName, a.BranchName, a.AcctGroup, a.AcctRank, a.Currency, a.AddrLine1, a.AddrLine2, a.City, a.State, a.ZipCode, a.Country, a.DayPhone, a.SubmitDate, a.LastChangeDate, a.ActivationDate, a.AmtLimit, b.CompId FROM BPW_BankAcct a, ACH_CompOffsetAcct b WHERE b.CompId = ? AND a.BankRTN = ? AND a.AcctNumber = ? AND b.AcctId != ? AND  a.AcctId = b.AcctId AND a.AcctStatus != 'CLOSED' ";
      localObject1 = new Object[] { paramString1, paramString2, paramString3, paramString4 };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject1);
      if (localFFSResultSet.getNextRow()) {
        bool = true;
      } else {
        bool = false;
      }
    }
    catch (Exception localException1)
    {
      bool = false;
      Object localObject1 = str1 + ": failed: ";
      String str3 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException1, (String)localObject1 + str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log(str1 + " failed: " + FFSDebug.stackTrace(localException2), 0);
      }
    }
    FFSDebug.log(str1 + " end. exists: " + bool, 6);
    return bool;
  }
  
  public static ACHCompOffsetAcctInfo deleteOffsetAcctByCompId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "ACHCompOffsetAcct.deleteOffsetAcctByCompId";
    FFSDebug.log(str1 + " start...", 6);
    ACHCompOffsetAcctInfo localACHCompOffsetAcctInfo1 = new ACHCompOffsetAcctInfo();
    localACHCompOffsetAcctInfo1.setStatusCode(0);
    localACHCompOffsetAcctInfo1.setStatusMsg("Success");
    try
    {
      HashSet localHashSet = getAcctIDsByCompId(paramFFSConnectionHolder, paramString);
      if (!localHashSet.isEmpty())
      {
        str2 = null;
        localObject1 = localHashSet.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          str2 = (String)((Iterator)localObject1).next();
          localACHCompOffsetAcctInfo1.setAcctId(str2);
          localACHCompOffsetAcctInfo1 = deleteACHCompOffsetAcct(paramFFSConnectionHolder, localACHCompOffsetAcctInfo1);
          if (localACHCompOffsetAcctInfo1.getStatusCode() != 0)
          {
            ACHCompOffsetAcctInfo localACHCompOffsetAcctInfo2 = localACHCompOffsetAcctInfo1;
            return localACHCompOffsetAcctInfo2;
          }
        }
      }
    }
    catch (FFSException localFFSException)
    {
      str2 = str1 + ": failed: ";
      localObject1 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log((String)localObject1, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str2 = str1 + ": failed: ";
      Object localObject1 = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localException, str2 + (String)localObject1);
    }
    finally
    {
      FFSDebug.log(str1 + " end.", 6);
    }
    return localACHCompOffsetAcctInfo1;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.ACHCompOffsetAcct
 * JD-Core Version:    0.7.0.1
 */