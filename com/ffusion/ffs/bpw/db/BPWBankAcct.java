package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.BPWBankAcctInfo;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import java.io.PrintStream;

public class BPWBankAcct
  implements DBConsts, FFSConst, ACHConsts
{
  public static BPWBankAcctInfo createBPWBankAcctinfo(FFSConnectionHolder paramFFSConnectionHolder, BPWBankAcctInfo paramBPWBankAcctInfo)
    throws FFSException
  {
    String str1 = "BPWBankAcctInfo.create";
    FFSDebug.log(str1 + " start ...", 6);
    paramBPWBankAcctInfo = a(paramBPWBankAcctInfo);
    if (paramBPWBankAcctInfo.getStatusCode() != 0) {
      return paramBPWBankAcctInfo;
    }
    try
    {
      BPWFIInfo localBPWFIInfo = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, paramBPWBankAcctInfo.getFIId());
      if ((localBPWFIInfo == null) || (localBPWFIInfo.getStatusCode() != 0))
      {
        paramBPWBankAcctInfo.setStatusCode(23230);
        paramBPWBankAcctInfo.setStatusMsg(BPWLocaleUtil.getMessage(23230, new String[] { "FIID: " + paramBPWBankAcctInfo.getFIId() }, "OFX_MESSAGE"));
        FFSDebug.log(str1 + ": " + "BPWFIID does not exist :" + " with FIID = " + paramBPWBankAcctInfo.getFIId(), 6);
        localObject1 = paramBPWBankAcctInfo;
        return localObject1;
      }
      localObject1 = DBUtil.getNextIndexStringWithPadding("AcctId", 10, '0');
      str2 = FFSUtil.getDateString();
      paramBPWBankAcctInfo.setAcctStatus("ACTIVE");
      paramBPWBankAcctInfo.setSubmitDate(str2);
      paramBPWBankAcctInfo.setActivationDate(str2);
      paramBPWBankAcctInfo.setLastChangeDate(str2);
      String str3 = "INSERT INTO BPW_BankAcct (AcctId, AcctNumber, FIId, BankRTN, AcctType, NameOnAcct, AcctStatus, AcctNickName, AcctDesc, BankName, BranchName, AcctGroup, AcctRank, Currency, AddrLine1, AddrLine2, City, State, ZipCode, Country, DayPhone, SubmitDate, LastChangeDate, ActivationDate, AmtLimit) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      Object[] arrayOfObject = { localObject1, paramBPWBankAcctInfo.getAcctNumber(), paramBPWBankAcctInfo.getFIId(), paramBPWBankAcctInfo.getBankRTN(), paramBPWBankAcctInfo.getAcctType(), paramBPWBankAcctInfo.getNameOnAcct(), paramBPWBankAcctInfo.getAcctStatus(), paramBPWBankAcctInfo.getAcctNickName(), paramBPWBankAcctInfo.getAcctDesc(), paramBPWBankAcctInfo.getBankName(), paramBPWBankAcctInfo.getBranchName(), paramBPWBankAcctInfo.getAcctGroup(), paramBPWBankAcctInfo.getAcctRank(), paramBPWBankAcctInfo.getCurrency(), paramBPWBankAcctInfo.getAddrLine1(), paramBPWBankAcctInfo.getAddrLine2(), paramBPWBankAcctInfo.getCity(), paramBPWBankAcctInfo.getState(), paramBPWBankAcctInfo.getZipCode(), paramBPWBankAcctInfo.getCountry(), paramBPWBankAcctInfo.getDayPhone(), paramBPWBankAcctInfo.getSubmitDate(), paramBPWBankAcctInfo.getLastChangeDate(), paramBPWBankAcctInfo.getActivationDate(), paramBPWBankAcctInfo.getAmtLimit() };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
      paramBPWBankAcctInfo.setAcctId((String)localObject1);
      paramBPWBankAcctInfo.setStatusCode(0);
      paramBPWBankAcctInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "OFX_MESSAGE"));
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
    return paramBPWBankAcctInfo;
  }
  
  private static BPWBankAcctInfo jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, String paramString, Object[] paramArrayOfObject)
    throws FFSException
  {
    String str1 = "BPWBankAcct.getBPWBankAcctInfo";
    FFSDebug.log(str1 + " start...", 6);
    BPWBankAcctInfo localBPWBankAcctInfo = new BPWBankAcctInfo();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, paramString, paramArrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        setBPWBankAcctInfoDetails(localBPWBankAcctInfo, localFFSResultSet);
        localBPWBankAcctInfo.setStatusCode(0);
        localBPWBankAcctInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "OFX_MESSAGE"));
      }
      else
      {
        localBPWBankAcctInfo.setStatusCode(16020);
        localBPWBankAcctInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "BankAcctInfo" }, "OFX_MESSAGE"));
      }
    }
    catch (Exception localException1)
    {
      String str2 = str1 + ": failed: ";
      String str3 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException1, str2 + str3);
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
    return localBPWBankAcctInfo;
  }
  
  public static BPWBankAcctInfo getBPWBankAcctInfoById(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "BPWBankAcct.getBPWBankAcctInfoById";
    FFSDebug.log(str1 + " start...", 6);
    String str2 = "SELECT * FROM BPW_BankAcct WHERE AcctId = ? AND AcctStatus != 'CLOSED' ";
    Object[] arrayOfObject = { paramString };
    FFSDebug.log(str1 + " end", 6);
    return jdMethod_if(paramFFSConnectionHolder, str2, arrayOfObject);
  }
  
  public static BPWBankAcctInfo deleteBPWBankInfoByAcctId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "BPWBankAcct.deleteBPWBankInfoByAcctId";
    FFSDebug.log(str1 + ": start ...", 6);
    BPWBankAcctInfo localBPWBankAcctInfo = new BPWBankAcctInfo();
    localBPWBankAcctInfo.setAcctId(paramString);
    try
    {
      String str2 = "DELETE FROM BPW_BankAcct WHERE AcctId = ?";
      localObject1 = new Object[] { paramString };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject1);
      if (i > 0)
      {
        localBPWBankAcctInfo.setStatusCode(0);
        localBPWBankAcctInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "OFX_MESSAGE"));
      }
      else
      {
        localBPWBankAcctInfo.setStatusCode(16020);
        localBPWBankAcctInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "BPWBankAcctInfo" }, "OFX_MESSAGE"));
      }
    }
    catch (Exception localException)
    {
      Object localObject1 = str1 + ": failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, (String)localObject1 + str3);
    }
    finally
    {
      FFSDebug.log(str1 + " end.", 6);
    }
    return localBPWBankAcctInfo;
  }
  
  public static BPWBankAcctInfo modBPWBankAcct(FFSConnectionHolder paramFFSConnectionHolder, BPWBankAcctInfo paramBPWBankAcctInfo)
    throws FFSException
  {
    String str1 = "BPWBankAcct.modBPWBankInfoByAcctId";
    FFSDebug.log(str1 + ": start ...", 6);
    paramBPWBankAcctInfo = a(paramBPWBankAcctInfo);
    if (paramBPWBankAcctInfo.getStatusCode() != 0) {
      return paramBPWBankAcctInfo;
    }
    try
    {
      String str2 = "UPDATE BPW_BankAcct set AcctNumber = ?, BankRTN = ?, AcctType = ?, NameOnAcct = ?, AcctNickName = ?, AcctStatus = ?, AcctDesc = ?, BankName = ?, BranchName = ?, AcctGroup = ?, AcctRank = ?, Currency = ?, AddrLine1 = ?, AddrLine2 = ?, City = ?, State = ?, ZipCode = ?, Country = ?, DayPhone = ?, LastChangeDate = ?, AmtLimit = ? WHERE AcctId = ?";
      str3 = FFSUtil.getDateString();
      localObject1 = new Object[] { paramBPWBankAcctInfo.getAcctNumber(), paramBPWBankAcctInfo.getBankRTN(), paramBPWBankAcctInfo.getAcctType(), paramBPWBankAcctInfo.getNameOnAcct(), paramBPWBankAcctInfo.getAcctNickName(), paramBPWBankAcctInfo.getAcctStatus(), paramBPWBankAcctInfo.getAcctDesc(), paramBPWBankAcctInfo.getBankName(), paramBPWBankAcctInfo.getBranchName(), paramBPWBankAcctInfo.getAcctGroup(), paramBPWBankAcctInfo.getAcctRank(), paramBPWBankAcctInfo.getCurrency(), paramBPWBankAcctInfo.getAddrLine1(), paramBPWBankAcctInfo.getAddrLine2(), paramBPWBankAcctInfo.getCity(), paramBPWBankAcctInfo.getState(), paramBPWBankAcctInfo.getZipCode(), paramBPWBankAcctInfo.getCountry(), paramBPWBankAcctInfo.getDayPhone(), str3, paramBPWBankAcctInfo.getAmtLimit(), paramBPWBankAcctInfo.getAcctId() };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject1);
      if (i > 0)
      {
        paramBPWBankAcctInfo.setStatusCode(0);
        paramBPWBankAcctInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "OFX_MESSAGE"));
      }
      else
      {
        paramBPWBankAcctInfo.setStatusCode(16020);
        paramBPWBankAcctInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "BPWBankAcctInfo" }, "OFX_MESSAGE"));
      }
    }
    catch (Exception localException)
    {
      String str3 = str1 + ": failed: ";
      Object localObject1 = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localException, str3);
    }
    finally
    {
      FFSDebug.log(str1 + " end.", 6);
    }
    return paramBPWBankAcctInfo;
  }
  
  public static void setBPWBankAcctInfoDetails(BPWBankAcctInfo paramBPWBankAcctInfo, FFSResultSet paramFFSResultSet)
    throws Exception
  {
    paramBPWBankAcctInfo.setAcctId(paramFFSResultSet.getColumnString("AcctId"));
    paramBPWBankAcctInfo.setAcctNumber(paramFFSResultSet.getColumnString("AcctNumber"));
    paramBPWBankAcctInfo.setFIId(paramFFSResultSet.getColumnString("FIId"));
    paramBPWBankAcctInfo.setBankRTN(paramFFSResultSet.getColumnString("BankRTN"));
    paramBPWBankAcctInfo.setAcctType(paramFFSResultSet.getColumnString("AcctType"));
    paramBPWBankAcctInfo.setNameOnAcct(paramFFSResultSet.getColumnString("NameOnAcct"));
    paramBPWBankAcctInfo.setAcctStatus(paramFFSResultSet.getColumnString("AcctStatus"));
    paramBPWBankAcctInfo.setAcctDesc(paramFFSResultSet.getColumnString("AcctDesc"));
    paramBPWBankAcctInfo.setBankName(paramFFSResultSet.getColumnString("BankName"));
    paramBPWBankAcctInfo.setBranchName(paramFFSResultSet.getColumnString("BranchName"));
    paramBPWBankAcctInfo.setAcctGroup(paramFFSResultSet.getColumnString("AcctGroup"));
    paramBPWBankAcctInfo.setAcctRank(paramFFSResultSet.getColumnString("AcctRank"));
    paramBPWBankAcctInfo.setCurrency(paramFFSResultSet.getColumnString("Currency"));
    paramBPWBankAcctInfo.setAddrLine1(paramFFSResultSet.getColumnString("AddrLine1"));
    paramBPWBankAcctInfo.setAddrLine2(paramFFSResultSet.getColumnString("AddrLine2"));
    paramBPWBankAcctInfo.setCity(paramFFSResultSet.getColumnString("City"));
    paramBPWBankAcctInfo.setState(paramFFSResultSet.getColumnString("State"));
    paramBPWBankAcctInfo.setZipCode(paramFFSResultSet.getColumnString("ZipCode"));
    paramBPWBankAcctInfo.setCountry(paramFFSResultSet.getColumnString("Country"));
    paramBPWBankAcctInfo.setDayPhone(paramFFSResultSet.getColumnString("DayPhone"));
    paramBPWBankAcctInfo.setSubmitDate(paramFFSResultSet.getColumnString("SubmitDate"));
    paramBPWBankAcctInfo.setLastChangeDate(paramFFSResultSet.getColumnString("LastChangeDate"));
    paramBPWBankAcctInfo.setActivationDate(paramFFSResultSet.getColumnString("ActivationDate"));
    paramBPWBankAcctInfo.setAmtLimit(paramFFSResultSet.getColumnString("AmtLimit"));
    paramBPWBankAcctInfo.setAcctNickName(paramFFSResultSet.getColumnString("AcctNickName"));
  }
  
  private static BPWBankAcctInfo a(BPWBankAcctInfo paramBPWBankAcctInfo)
    throws BPWException
  {
    String str = "BPWBankAcct.validateBankAcctInfo";
    if (paramBPWBankAcctInfo == null)
    {
      paramBPWBankAcctInfo = new BPWBankAcctInfo();
      paramBPWBankAcctInfo.setStatusCode(16000);
      paramBPWBankAcctInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "BPWBankAcctInfo: " }, "OFX_MESSAGE"));
      FFSDebug.log(str + ": " + " is null");
      return paramBPWBankAcctInfo;
    }
    if ((paramBPWBankAcctInfo.getAcctNumber() == null) || (paramBPWBankAcctInfo.getAcctNumber().length() == 0))
    {
      paramBPWBankAcctInfo.setStatusCode(16010);
      paramBPWBankAcctInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "BPWBankAcctInfo", "acctNumber" }, "OFX_MESSAGE"));
      FFSDebug.log(str + ": " + " contains null ", 6);
      return paramBPWBankAcctInfo;
    }
    if ((paramBPWBankAcctInfo.getAcctType() == null) || (paramBPWBankAcctInfo.getAcctType().length() == 0))
    {
      paramBPWBankAcctInfo.setStatusCode(16010);
      paramBPWBankAcctInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "BPWBankAcct", "acctType" }, "OFX_MESSAGE"));
      FFSDebug.log(str + ": " + " contains null ", 6);
      return paramBPWBankAcctInfo;
    }
    if ((paramBPWBankAcctInfo.getBankRTN() == null) || (paramBPWBankAcctInfo.getBankRTN().length() == 0))
    {
      paramBPWBankAcctInfo.setStatusCode(16010);
      paramBPWBankAcctInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "BPWBankAcct", "bankRTN" }, "OFX_MESSAGE"));
      FFSDebug.log(str + ": " + " contains null ", 6);
      return paramBPWBankAcctInfo;
    }
    paramBPWBankAcctInfo.setStatusCode(0);
    return paramBPWBankAcctInfo;
  }
  
  private static void jdMethod_if(BPWBankAcctInfo paramBPWBankAcctInfo)
  {
    System.out.println("=========================================");
    System.out.println("AcctId = " + paramBPWBankAcctInfo.getAcctId());
    System.out.println("FIId = " + paramBPWBankAcctInfo.getFIId());
    System.out.println("AcctNumber = " + paramBPWBankAcctInfo.getAcctNumber());
    System.out.println("AcctRank = " + paramBPWBankAcctInfo.getAcctRank());
    System.out.println("Acct Type = " + paramBPWBankAcctInfo.getAcctType());
    System.out.println("Acct Status = " + paramBPWBankAcctInfo.getAcctStatus());
    System.out.println("Name on Account = " + paramBPWBankAcctInfo.getNameOnAcct());
    System.out.println("Acct Description = " + paramBPWBankAcctInfo.getAcctDesc());
    System.out.println("Branch Name = " + paramBPWBankAcctInfo.getBranchName());
    System.out.println("Bank Name = " + paramBPWBankAcctInfo.getBankName());
    System.out.println("Acct Group = " + paramBPWBankAcctInfo.getAcctGroup());
    System.out.println("Currency = " + paramBPWBankAcctInfo.getCurrency());
    System.out.println("Addr line 1 = " + paramBPWBankAcctInfo.getAddrLine1());
    System.out.println("Addr line 2 = " + paramBPWBankAcctInfo.getAddrLine2());
    System.out.println("City = " + paramBPWBankAcctInfo.getCity());
    System.out.println("State = " + paramBPWBankAcctInfo.getState());
    System.out.println("Zipcode = " + paramBPWBankAcctInfo.getZipCode());
    System.out.println("Country = " + paramBPWBankAcctInfo.getCountry());
    System.out.println("Day phone = " + paramBPWBankAcctInfo.getDayPhone());
    System.out.println("Submnit date = " + paramBPWBankAcctInfo.getSubmitDate());
    System.out.println("Last change date = " + paramBPWBankAcctInfo.getLastChangeDate());
    System.out.println("Activation date = " + paramBPWBankAcctInfo.getActivationDate());
    System.out.println("Amount limit = " + paramBPWBankAcctInfo.getAmtLimit());
    System.out.println("Status Code = " + paramBPWBankAcctInfo.getStatusCode());
    System.out.println("Status Msg = " + paramBPWBankAcctInfo.getStatusMsg());
    System.out.println("=========================================\n");
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.BPWBankAcct
 * JD-Core Version:    0.7.0.1
 */