package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.BPWBankInfo;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.util.ArrayList;

public class WireBank
  implements DBConsts, FFSConst, ACHConsts, BPWResource
{
  public static BPWBankInfo addWireBank(FFSConnectionHolder paramFFSConnectionHolder, BPWBankInfo paramBPWBankInfo)
    throws FFSException
  {
    String str1 = "WireBank.addWireBank: ";
    FFSDebug.log("***", str1, "start ...", 6);
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    Object localObject4 = null;
    Object localObject5 = null;
    Object localObject6 = null;
    String str2;
    if (paramBPWBankInfo == null)
    {
      str2 = " failed: Null BPWBankInfo Object passed";
      FFSDebug.log("****", str1, str2, 0);
      paramBPWBankInfo = new BPWBankInfo();
      paramBPWBankInfo.setStatusCode(16000);
      paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "BPWBankInfo" }, "WIRE_MESSAGE"));
      return paramBPWBankInfo;
    }
    if (!a(paramBPWBankInfo)) {
      return paramBPWBankInfo;
    }
    try
    {
      paramBPWBankInfo.setBankId(DBUtil.getNextIndexStringWithPadding("WIREBANKID", 10, '0'));
      str2 = "INSERT INTO BPW_WireBank  ( BankId, BankName,   AddressLine1, AddressLine2, AddressLine3, City, State, PostalCode,   Country, BankPhone, FedRTN, SwiftRTN, ChipsRTN, OtherRTN, ExtBankId )  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
      localObject7 = new Object[] { paramBPWBankInfo.getBankId(), paramBPWBankInfo.getBankName(), paramBPWBankInfo.getBankAddr1(), paramBPWBankInfo.getBankAddr2(), paramBPWBankInfo.getBankAddr3(), paramBPWBankInfo.getBankCity(), paramBPWBankInfo.getBankState(), paramBPWBankInfo.getBankPSCode(), paramBPWBankInfo.getBankCountry(), paramBPWBankInfo.getBankPhone(), paramBPWBankInfo.getFedRTN(), paramBPWBankInfo.getSwiftRTN(), paramBPWBankInfo.getChipsRTN(), paramBPWBankInfo.getOtherRTN(), paramBPWBankInfo.getExtBankId() };
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject7);
      paramBPWBankInfo.setStatusCode(0);
      paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      FFSDebug.log("***", str1, "end", paramBPWBankInfo.toString(), 6);
      return paramBPWBankInfo;
    }
    catch (Throwable localThrowable)
    {
      Object localObject7 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, (String)localObject7, 0);
      throw new FFSException(localThrowable, (String)localObject7);
    }
  }
  
  public static BPWBankInfo modWireBank(FFSConnectionHolder paramFFSConnectionHolder, BPWBankInfo paramBPWBankInfo)
    throws FFSException
  {
    String str1 = "WireBank.modWireBank: ";
    FFSDebug.log("***", str1, "start ...", 6);
    Object localObject1 = null;
    String str2 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    Object localObject4 = null;
    Object localObject5 = null;
    Object localObject6 = null;
    Object localObject7;
    if (paramBPWBankInfo == null)
    {
      localObject7 = " failed: Null BPWBankInfo Object passed";
      FFSDebug.log("****", str1, (String)localObject7, 0);
      paramBPWBankInfo = new BPWBankInfo();
      paramBPWBankInfo.setStatusCode(16000);
      paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "BPWBankInfo " }, "WIRE_MESSAGE"));
      return paramBPWBankInfo;
    }
    if ((paramBPWBankInfo.getBankId() == null) || (paramBPWBankInfo.getBankId().trim().length() == 0))
    {
      localObject7 = "failed: BankID is null";
      FFSDebug.log("***", str1, (String)localObject7, 0);
      paramBPWBankInfo.setStatusCode(16010);
      paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "WireBankInfo ", " bankId" }, "WIRE_MESSAGE"));
      return paramBPWBankInfo;
    }
    if (!a(paramBPWBankInfo)) {
      return paramBPWBankInfo;
    }
    try
    {
      str2 = "UPDATE BPW_WireBank  set BankName = ?, AddressLine1 = ?,  AddressLine2 = ?, AddressLine3 = ?, City = ?, State = ?, PostalCode = ?,  Country = ?, BankPhone = ?, FedRTN = ?, SwiftRTN = ?, ChipsRTN = ?,  OtherRTN = ?, ExtBankId = ?  WHERE BankId = ?";
      localObject7 = new Object[] { paramBPWBankInfo.getBankName(), paramBPWBankInfo.getBankAddr1(), paramBPWBankInfo.getBankAddr2(), paramBPWBankInfo.getBankAddr3(), paramBPWBankInfo.getBankCity(), paramBPWBankInfo.getBankState(), paramBPWBankInfo.getBankPSCode(), paramBPWBankInfo.getBankCountry(), paramBPWBankInfo.getBankPhone(), paramBPWBankInfo.getFedRTN(), paramBPWBankInfo.getSwiftRTN(), paramBPWBankInfo.getChipsRTN(), paramBPWBankInfo.getOtherRTN(), paramBPWBankInfo.getExtBankId(), paramBPWBankInfo.getBankId() };
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject7);
      paramBPWBankInfo.setStatusCode(0);
      paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      FFSDebug.log("***", str1, "end", 6);
      return paramBPWBankInfo;
    }
    catch (Throwable localThrowable)
    {
      String str3 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, str3, 0);
      throw new FFSException(localThrowable, str3);
    }
  }
  
  public static BPWBankInfo delWireBank(FFSConnectionHolder paramFFSConnectionHolder, BPWBankInfo paramBPWBankInfo)
    throws FFSException
  {
    String str1 = "WireBank.delWireBank: ";
    FFSDebug.log("***", str1, "start ...", 6);
    String str2 = null;
    String str3 = null;
    String[] arrayOfString = null;
    Object localObject;
    if (paramBPWBankInfo == null)
    {
      localObject = " failed: Null BPWBankInfo Object passed";
      FFSDebug.log("****", str1, (String)localObject, 0);
      paramBPWBankInfo = new BPWBankInfo();
      paramBPWBankInfo.setStatusCode(16000);
      paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "BPWBankInfo " }, "WIRE_MESSAGE"));
      return paramBPWBankInfo;
    }
    str3 = paramBPWBankInfo.getBankId();
    if ((str3 == null) || (str3.trim().length() == 0))
    {
      localObject = "failed: BankID is null";
      FFSDebug.log("***", str1, (String)localObject, 0);
      paramBPWBankInfo.setStatusCode(16010);
      paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "WireBankInfo ", " bankId" }, "WIRE_MESSAGE"));
      return paramBPWBankInfo;
    }
    try
    {
      arrayOfString = WirePayee.getPayeeIdsByBankId(paramFFSConnectionHolder, str3, true);
      if (arrayOfString != null)
      {
        localObject = "failed: Some payee uses this Bank, ID:" + str3;
        FFSDebug.log("***", str1, (String)localObject, 0);
        paramBPWBankInfo.setStatusCode(19240);
        paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(19240, null, "WIRE_MESSAGE"));
        return paramBPWBankInfo;
      }
      arrayOfString = WirePayee.getPayeeIdsByBankId(paramFFSConnectionHolder, str3, false);
      if (arrayOfString != null)
      {
        localObject = "failed: Some payee uses this Bank, ID:" + str3;
        FFSDebug.log("***", str1, (String)localObject, 0);
        paramBPWBankInfo.setStatusCode(19240);
        paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(19240, null, "WIRE_MESSAGE"));
        return paramBPWBankInfo;
      }
      str2 = "DELETE FROM BPW_WireBank WHERE BankId = ?";
      localObject = new Object[] { str3 };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      if (i == 0)
      {
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append("The Bank ");
        localStringBuffer.append(" record not found");
        localStringBuffer.append(", BankId:").append(str3);
        paramBPWBankInfo.setStatusCode(16020);
        String str5 = BPWLocaleUtil.getMessage(16020, new String[] { " BankId: " + str3 }, "WIRE_MESSAGE");
        paramBPWBankInfo.setStatusMsg(str5);
      }
      else
      {
        paramBPWBankInfo.setStatusCode(0);
        paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
      FFSDebug.log("***", str1, "end", 6);
      return paramBPWBankInfo;
    }
    catch (Throwable localThrowable)
    {
      String str4 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, str4, 0);
      throw new FFSException(localThrowable, str4);
    }
  }
  
  public static BPWBankInfo[] getWireBanks(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4)
    throws FFSException
  {
    String str1 = "WireBank.getWireBanks: ";
    StringBuffer localStringBuffer = null;
    ArrayList localArrayList = null;
    int i = 0;
    Object[] arrayOfObject = null;
    FFSDebug.log(str1, "start", 6);
    Object localObject;
    if ((paramString1 == null) || (paramString1.trim().length() == 0))
    {
      String str2 = "Failed, mandatory Bank Name paremeter is NULL";
      FFSDebug.log("****", str1, str2, 6);
      localObject = new BPWBankInfo[1];
      localObject[0] = new BPWBankInfo();
      localObject[0].setStatusCode(16010);
      localObject[0].setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "WireBank", "BankName" }, "WIRE_MESSAGE"));
      return localObject;
    }
    localArrayList = new ArrayList();
    localStringBuffer = new StringBuffer();
    localStringBuffer.append("SELECT  BankId, BankName, AddressLine1, AddressLine2, AddressLine3, City,  State, PostalCode, Country, BankPhone, FedRTN, SwiftRTN, ChipsRTN,  OtherRTN, ExtBankId  FROM BPW_WireBank ");
    localStringBuffer.append(" WHERE BankName LIKE '").append(paramString1.toUpperCase()).append("%'");
    if ((paramString2 != null) && (paramString2.trim().length() > 0))
    {
      localStringBuffer.append(" AND City = ? ");
      localArrayList.add(paramString2);
    }
    if ((paramString3 != null) && (paramString3.trim().length() > 0))
    {
      localStringBuffer.append(" AND State = ? ");
      localArrayList.add(paramString3);
    }
    if ((paramString4 != null) && (paramString4.trim().length() > 0))
    {
      localStringBuffer.append(" AND Country = ? ");
      localArrayList.add(paramString4);
    }
    localStringBuffer.append(" ORDER BY BankId ");
    i = localArrayList.size();
    arrayOfObject = new Object[i];
    for (int j = 0; j < i; j++) {
      arrayOfObject[j] = localArrayList.get(j);
    }
    try
    {
      return jdMethod_do(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
    }
    catch (Throwable localThrowable)
    {
      localObject = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, (String)localObject, 0);
      throw new FFSException(localThrowable, (String)localObject);
    }
  }
  
  public static BPWBankInfo[] getWireBanksByRTN(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "WireBank.getWireBanksByRTN: ";
    String str2 = null;
    FFSDebug.log(str1, "start", 6);
    if (paramFFSConnectionHolder == null)
    {
      localObject = " failed: Null database connection is passed";
      FFSDebug.log("****", str1, (String)localObject, 0);
      return null;
    }
    if (paramString == null)
    {
      localObject = " failed: Null RTN is passed";
      FFSDebug.log("****", str1, (String)localObject, 0);
      return null;
    }
    str2 = "SELECT  BankId, BankName, AddressLine1, AddressLine2, AddressLine3, City,  State, PostalCode, Country, BankPhone, FedRTN, SwiftRTN, ChipsRTN,  OtherRTN, ExtBankId  FROM BPW_WireBank  WHERE FedRTN = ?  OR SwiftRTN = ?  OR ChipsRTN = ?  OR OtherRTN = ? ";
    Object localObject = { paramString, paramString, paramString, paramString };
    try
    {
      return jdMethod_do(paramFFSConnectionHolder, str2, (Object[])localObject);
    }
    catch (Throwable localThrowable)
    {
      String str3 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, str3, 0);
      throw new FFSException(localThrowable, str3);
    }
  }
  
  public static BPWBankInfo getWireBanksByID(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "WireBank.getWireBanksByID: ";
    String str2 = null;
    BPWBankInfo[] arrayOfBPWBankInfo = null;
    FFSDebug.log(str1, "start", 6);
    FFSDebug.log(str1, "****+++++++bankId: ", paramString, 6);
    if (paramFFSConnectionHolder == null)
    {
      localObject = " failed: Null database connection is passed";
      FFSDebug.log("****", str1, (String)localObject, 0);
      return null;
    }
    if (paramString == null)
    {
      localObject = " failed: Null bankId is passed";
      FFSDebug.log("****", str1, (String)localObject, 0);
      return null;
    }
    str2 = "SELECT  BankId, BankName, AddressLine1, AddressLine2, AddressLine3, City,  State, PostalCode, Country, BankPhone, FedRTN, SwiftRTN, ChipsRTN,  OtherRTN, ExtBankId  FROM BPW_WireBank  WHERE BankId = ? ";
    Object localObject = { paramString };
    try
    {
      arrayOfBPWBankInfo = jdMethod_do(paramFFSConnectionHolder, str2, (Object[])localObject);
      FFSDebug.log(str1, "****+++++++bankInfos: " + arrayOfBPWBankInfo, 6);
      if (arrayOfBPWBankInfo == null) {
        return null;
      }
      FFSDebug.log(str1, "****+++++++bankInfos.length: " + arrayOfBPWBankInfo.length, 6);
      FFSDebug.log(str1, "****+++++++bankInfos[0]: " + arrayOfBPWBankInfo[0], 6);
      return arrayOfBPWBankInfo[0];
    }
    catch (Throwable localThrowable)
    {
      String str3 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, str3, 0);
      throw new FFSException(localThrowable, str3);
    }
  }
  
  public static BPWBankInfo getWireBanksByExtBankId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "WireBank.getWireBanksByExtBankID: ";
    String str2 = null;
    BPWBankInfo[] arrayOfBPWBankInfo = null;
    FFSDebug.log(str1, "start", 6);
    if (paramFFSConnectionHolder == null)
    {
      localObject = " failed: Null database connection is passed";
      FFSDebug.log("****", str1, (String)localObject, 0);
      return null;
    }
    if (paramString == null)
    {
      localObject = " failed: Null extBankId is passed";
      FFSDebug.log("****", str1, (String)localObject, 0);
      return null;
    }
    FFSDebug.log(str1, "extBankId:", paramString, 6);
    str2 = "SELECT  BankId, BankName, AddressLine1, AddressLine2, AddressLine3, City,  State, PostalCode, Country, BankPhone, FedRTN, SwiftRTN, ChipsRTN,  OtherRTN, ExtBankId  FROM BPW_WireBank  WHERE ExtBankId = ? ";
    Object localObject = { paramString };
    try
    {
      arrayOfBPWBankInfo = jdMethod_do(paramFFSConnectionHolder, str2, (Object[])localObject);
      if (arrayOfBPWBankInfo == null) {
        return null;
      }
      return arrayOfBPWBankInfo[0];
    }
    catch (Throwable localThrowable)
    {
      String str3 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, str3, 0);
      throw new FFSException(localThrowable, str3);
    }
  }
  
  public static BPWBankInfo getWireBanksByNameRtn(FFSConnectionHolder paramFFSConnectionHolder, BPWBankInfo paramBPWBankInfo)
    throws FFSException
  {
    String str1 = "WireBank.getWireBanksByNameRtn: ";
    String str2 = null;
    Object localObject1 = null;
    FFSResultSet localFFSResultSet = null;
    String str3 = null;
    FFSDebug.log(str1, "start", 6);
    if (paramBPWBankInfo == null)
    {
      localObject2 = " failed: Null bankInfo is passed";
      FFSDebug.log("****", str1, (String)localObject2, 0);
      paramBPWBankInfo = new BPWBankInfo();
      paramBPWBankInfo.setStatusCode(16000);
      paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "BPWBankInfo " }, "WIRE_MESSAGE"));
      return paramBPWBankInfo;
    }
    if (paramFFSConnectionHolder == null)
    {
      localObject2 = " failed: Null database connection is passed";
      FFSDebug.log("****", str1, (String)localObject2, 0);
      paramBPWBankInfo.setStatusCode(16000);
      paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder " }, "WIRE_MESSAGE"));
      return paramBPWBankInfo;
    }
    str3 = paramBPWBankInfo.getBankName();
    if (str3 != null) {
      paramBPWBankInfo.setBankName(str3.toUpperCase());
    }
    FFSDebug.log(str1, "BankName:", paramBPWBankInfo.getBankName(), ",SwiftRTN:", paramBPWBankInfo.getSwiftRTN(), ",ChipsRTN:", paramBPWBankInfo.getChipsRTN(), ",FedRTN:", paramBPWBankInfo.getFedRTN(), ",OtherRTN:", paramBPWBankInfo.getOtherRTN(), 6);
    str2 = "SELECT  BankId, BankName, AddressLine1, AddressLine2, AddressLine3, City,  State, PostalCode, Country, BankPhone, FedRTN, SwiftRTN, ChipsRTN,  OtherRTN, ExtBankId  FROM BPW_WireBank  WHERE BankName = ?  AND FedRTN = ?  AND SwiftRTN = ?  AND ChipsRTN = ?  AND OtherRTN = ? ";
    Object localObject2 = { paramBPWBankInfo.getBankName(), paramBPWBankInfo.getFedRTN(), paramBPWBankInfo.getSwiftRTN(), paramBPWBankInfo.getChipsRTN(), paramBPWBankInfo.getOtherRTN() };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject2);
      if (localFFSResultSet.getNextRow())
      {
        a(localFFSResultSet, paramBPWBankInfo);
        paramBPWBankInfo.setStatusCode(0);
        paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
      else
      {
        paramBPWBankInfo.setStatusCode(16020);
        paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "Wire Bank" }, "WIRE_MESSAGE"));
      }
      FFSDebug.log("***", str1, "end", 6);
      BPWBankInfo localBPWBankInfo = paramBPWBankInfo;
      return localBPWBankInfo;
    }
    catch (Throwable localThrowable)
    {
      String str4 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, str4, 0);
      throw new FFSException(localThrowable, str4);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log("***", str1, "failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
  }
  
  public static BPWBankInfo[] getWireBanksByPayee(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "WireBank.getWireBanksByPayee: ";
    String str2 = null;
    FFSDebug.log(str1, "start", 6);
    if (paramFFSConnectionHolder == null)
    {
      localObject = " failed: Null database connection is passed";
      FFSDebug.log("****", str1, (String)localObject, 0);
      return null;
    }
    if (paramString == null)
    {
      localObject = " failed: Null payeeId is passed";
      FFSDebug.log("****", str1, (String)localObject, 0);
      return null;
    }
    str2 = "SELECT  a.BankId, a.BankName, a.AddressLine1, a.AddressLine2, a.AddressLine3, a.City,  a.State, a.PostalCode, a.Country, a.BankPhone, a.FedRTN, a.SwiftRTN, a.ChipsRTN,   a.OtherRTN, a.ExtBankId  FROM BPW_WireBank a, BPW_PayeeIntrmdBnks b  WHERE a.BankId = b.BankId  AND b.PayeeId = ?  Order By b.Rank";
    Object localObject = { paramString };
    try
    {
      return jdMethod_do(paramFFSConnectionHolder, str2, (Object[])localObject);
    }
    catch (Throwable localThrowable)
    {
      String str3 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, str3, 0);
      throw new FFSException(localThrowable, str3);
    }
  }
  
  private static BPWBankInfo[] jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, String paramString, Object[] paramArrayOfObject)
    throws FFSException
  {
    String str1 = "WireBank.getWireBanks: ";
    ArrayList localArrayList = null;
    BPWBankInfo[] arrayOfBPWBankInfo1 = null;
    BPWBankInfo localBPWBankInfo = null;
    FFSResultSet localFFSResultSet = null;
    FFSDebug.log(str1, "start", 6);
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, paramString, paramArrayOfObject);
      localArrayList = new ArrayList();
      while (localFFSResultSet.getNextRow())
      {
        localBPWBankInfo = new BPWBankInfo();
        a(localFFSResultSet, localBPWBankInfo);
        localBPWBankInfo.setStatusCode(0);
        localBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
        localArrayList.add(localBPWBankInfo);
      }
      if (localArrayList.size() > 0) {
        arrayOfBPWBankInfo1 = (BPWBankInfo[])localArrayList.toArray(new BPWBankInfo[0]);
      }
      FFSDebug.log("***", str1, "end", 6);
      BPWBankInfo[] arrayOfBPWBankInfo2 = arrayOfBPWBankInfo1;
      return arrayOfBPWBankInfo2;
    }
    catch (Throwable localThrowable)
    {
      String str2 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, str2, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log("***", str1, "failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
  }
  
  private static void a(FFSResultSet paramFFSResultSet, BPWBankInfo paramBPWBankInfo)
    throws FFSException
  {
    paramBPWBankInfo.setBankId(paramFFSResultSet.getColumnString("BankId"));
    paramBPWBankInfo.setBankName(paramFFSResultSet.getColumnString("BankName"));
    paramBPWBankInfo.setBankAddr1(paramFFSResultSet.getColumnString("AddressLine1"));
    paramBPWBankInfo.setBankAddr2(paramFFSResultSet.getColumnString("AddressLine2"));
    paramBPWBankInfo.setBankAddr3(paramFFSResultSet.getColumnString("AddressLine3"));
    paramBPWBankInfo.setBankCity(paramFFSResultSet.getColumnString("City"));
    paramBPWBankInfo.setBankState(paramFFSResultSet.getColumnString("State"));
    paramBPWBankInfo.setBankPSCode(paramFFSResultSet.getColumnString("PostalCode"));
    paramBPWBankInfo.setBankCountry(paramFFSResultSet.getColumnString("Country"));
    paramBPWBankInfo.setBankPhone(paramFFSResultSet.getColumnString("BankPhone"));
    paramBPWBankInfo.setFedRTN(paramFFSResultSet.getColumnString("FedRTN"));
    paramBPWBankInfo.setSwiftRTN(paramFFSResultSet.getColumnString("SwiftRTN"));
    paramBPWBankInfo.setChipsRTN(paramFFSResultSet.getColumnString("ChipsRTN"));
    paramBPWBankInfo.setOtherRTN(paramFFSResultSet.getColumnString("OtherRTN"));
    paramBPWBankInfo.setExtBankId(paramFFSResultSet.getColumnString("ExtBankId"));
  }
  
  private static boolean a(BPWBankInfo paramBPWBankInfo)
    throws BPWException
  {
    String str1 = "WireBank.validateWireBankInfo: ";
    FFSDebug.log(str1, ": start", 6);
    String str2;
    if ((paramBPWBankInfo.getBankName() == null) || (paramBPWBankInfo.getBankName().trim().length() == 0))
    {
      paramBPWBankInfo.setStatusCode(16010);
      str2 = BPWLocaleUtil.getMessage(16010, new String[] { "BPWBankInfo", "BankName" }, "WIRE_MESSAGE");
      paramBPWBankInfo.setStatusMsg(str2);
      FFSDebug.log(str1, str2, 0);
      return false;
    }
    if ((paramBPWBankInfo.getBankCountry() == null) || (paramBPWBankInfo.getBankCountry().trim().length() == 0))
    {
      paramBPWBankInfo.setStatusCode(16010);
      str2 = BPWLocaleUtil.getMessage(16010, new String[] { "BPWBankInfo", "bankCountry" }, "WIRE_MESSAGE");
      paramBPWBankInfo.setStatusMsg(str2);
      FFSDebug.log(str1, str2, 0);
      return false;
    }
    paramBPWBankInfo.setBankName(paramBPWBankInfo.getBankName().toUpperCase());
    FFSDebug.log(str1, ": done", 6);
    return true;
  }
  
  private static boolean jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, BPWBankInfo paramBPWBankInfo)
    throws FFSException
  {
    String str1 = "WireBank.isDupExtBankId: ";
    FFSDebug.log(str1, ": start", 6);
    String str2 = null;
    String str3 = null;
    FFSResultSet localFFSResultSet = null;
    String str4 = paramBPWBankInfo.getExtBankId();
    if ((str4 == null) || (str4.trim().length() == 0)) {
      return false;
    }
    FFSDebug.log(str1, ": extBankId:", str4, 6);
    str3 = "SELECT  BankId, BankName, AddressLine1, AddressLine2, AddressLine3, City,  State, PostalCode, Country, BankPhone, FedRTN, SwiftRTN, ChipsRTN,  OtherRTN, ExtBankId  FROM BPW_WireBank  WHERE ExtBankId = ? ";
    Object[] arrayOfObject = { str4 };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        str2 = localFFSResultSet.getColumnString("BankId");
        if (!str2.equals(paramBPWBankInfo.getBankId()))
        {
          String str5 = BPWLocaleUtil.getMessage(19320, new String[] { str4 }, "WIRE_MESSAGE");
          FFSDebug.log("***", str1, str5, 0);
          paramBPWBankInfo.setStatusCode(19320);
          paramBPWBankInfo.setStatusMsg(str5);
          boolean bool = true;
          return bool;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      String str6 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, str6, 0);
      throw new FFSException(localThrowable, str6);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log("***", str1, "failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return false;
  }
  
  private static boolean a(FFSConnectionHolder paramFFSConnectionHolder, BPWBankInfo paramBPWBankInfo)
    throws FFSException
  {
    String str1 = "WireBank.isDupRTNCombination: ";
    FFSDebug.log(str1, ": start", 6);
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    String str3 = null;
    str2 = "SELECT  BankId, BankName, AddressLine1, AddressLine2, AddressLine3, City,  State, PostalCode, Country, BankPhone, FedRTN, SwiftRTN, ChipsRTN,  OtherRTN, ExtBankId  FROM BPW_WireBank  WHERE FedRTN = ?  AND SwiftRTN = ?  AND ChipsRTN = ?  AND OtherRTN = ? ";
    Object[] arrayOfObject = { paramBPWBankInfo.getFedRTN(), paramBPWBankInfo.getSwiftRTN(), paramBPWBankInfo.getChipsRTN(), paramBPWBankInfo.getOtherRTN() };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        str3 = localFFSResultSet.getColumnString("BankId");
        if (!str3.equals(paramBPWBankInfo.getBankId()))
        {
          StringBuffer localStringBuffer = new StringBuffer();
          localStringBuffer.append("A bank already exists");
          localStringBuffer.append(" for the RTN combination:");
          localStringBuffer.append(" swiftRTN:").append(paramBPWBankInfo.getSwiftRTN());
          localStringBuffer.append(", chipsRTN:").append(paramBPWBankInfo.getChipsRTN());
          localStringBuffer.append(", fedRTN:").append(paramBPWBankInfo.getFedRTN());
          localStringBuffer.append(", otherRTN:").append(paramBPWBankInfo.getOtherRTN());
          FFSDebug.log("***", str1, localStringBuffer.toString(), 0);
          paramBPWBankInfo.setStatusCode(19230);
          str4 = BPWLocaleUtil.getMessage(19230, new String[] { "swiftRTN", paramBPWBankInfo.getSwiftRTN(), "chipsRTN", paramBPWBankInfo.getChipsRTN(), "fedRTN", paramBPWBankInfo.getFedRTN(), "otherRTN", paramBPWBankInfo.getOtherRTN() }, "WIRE_MESSAGE");
          paramBPWBankInfo.setStatusMsg(str4);
          boolean bool = true;
          return bool;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      String str4 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, str4, 0);
      throw new FFSException(localThrowable, str4);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log("***", str1, "failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return false;
  }
  
  public static BPWBankInfo getWireBankByExtID(FFSConnectionHolder paramFFSConnectionHolder, BPWBankInfo paramBPWBankInfo)
    throws FFSException
  {
    String str1 = "WireBank.getWireBankByExtID: ";
    String str2 = null;
    Object localObject1 = null;
    String str3 = null;
    FFSResultSet localFFSResultSet = null;
    FFSDebug.log(str1, "start", 6);
    if (paramBPWBankInfo == null)
    {
      localObject2 = " failed: Null bank Information is passed";
      FFSDebug.log("****", str1, (String)localObject2, 0);
      paramBPWBankInfo = new BPWBankInfo();
      paramBPWBankInfo.setStatusCode(16000);
      paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "BPWBankInfo" }, "WIRE_MESSAGE"));
      return paramBPWBankInfo;
    }
    if (paramFFSConnectionHolder == null)
    {
      localObject2 = " failed: Null database connection is passed";
      FFSDebug.log("****", str1, (String)localObject2, 0);
      paramBPWBankInfo.setStatusCode(16000);
      paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder" }, "WIRE_MESSAGE"));
      return null;
    }
    str3 = paramBPWBankInfo.getExtBankId();
    if ((str3 == null) || (str3.trim().length() == 0))
    {
      localObject2 = " failed: ExtBankId is missing in passed bank Information";
      FFSDebug.log("****", str1, (String)localObject2, 0);
      paramBPWBankInfo.setStatusCode(16010);
      paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "BPWBankInfo", "ExtBankId " }, "WIRE_MESSAGE"));
      return paramBPWBankInfo;
    }
    str2 = "SELECT  BankId, BankName, AddressLine1, AddressLine2, AddressLine3, City,  State, PostalCode, Country, BankPhone, FedRTN, SwiftRTN, ChipsRTN,  OtherRTN, ExtBankId  FROM BPW_WireBank  WHERE ExtBankId = ? ";
    Object localObject2 = { str3 };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject2);
      if (localFFSResultSet.getNextRow())
      {
        a(localFFSResultSet, paramBPWBankInfo);
        paramBPWBankInfo.setStatusCode(0);
        paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
      else
      {
        paramBPWBankInfo.setStatusCode(16020);
        paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "Wire Bank " }, "WIRE_MESSAGE"));
      }
      FFSDebug.log("***", str1, "end", 6);
      BPWBankInfo localBPWBankInfo = paramBPWBankInfo;
      return localBPWBankInfo;
    }
    catch (Throwable localThrowable)
    {
      String str4 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, str4, 0);
      throw new FFSException(localThrowable, str4);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log("***", str1, "failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
  }
  
  public static BPWBankInfo getWireBankByNameAddr(FFSConnectionHolder paramFFSConnectionHolder, BPWBankInfo paramBPWBankInfo)
    throws FFSException
  {
    String str1 = "WireBank.getWireBankByNameAddr: ";
    String str2 = null;
    Object localObject1 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    FFSResultSet localFFSResultSet = null;
    FFSDebug.log(str1, "start", 6);
    if (paramBPWBankInfo == null)
    {
      localObject2 = " failed: Null bank Information is passed";
      FFSDebug.log("****", str1, (String)localObject2, 0);
      paramBPWBankInfo = new BPWBankInfo();
      paramBPWBankInfo.setStatusCode(16000);
      paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "BPWBankInfo" }, "WIRE_MESSAGE"));
      return paramBPWBankInfo;
    }
    if (paramFFSConnectionHolder == null)
    {
      localObject2 = " failed: Null database connection is passed";
      FFSDebug.log("****", str1, (String)localObject2, 0);
      paramBPWBankInfo.setStatusCode(16000);
      paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder" }, "WIRE_MESSAGE"));
      return paramBPWBankInfo;
    }
    str3 = paramBPWBankInfo.getBankName();
    str4 = paramBPWBankInfo.getBankAddr1();
    str5 = paramBPWBankInfo.getBankCity();
    str6 = paramBPWBankInfo.getBankCountry();
    if ((str3 == null) || (str3.trim().length() == 0))
    {
      localObject2 = " failed: Bank name is missing in passed bank Information";
      FFSDebug.log("****", str1, (String)localObject2, 0);
      paramBPWBankInfo.setStatusCode(16010);
      paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "BPWBankInfo", "Bank name" }, "WIRE_MESSAGE"));
      return paramBPWBankInfo;
    }
    str2 = "SELECT  BankId, BankName, AddressLine1, AddressLine2, AddressLine3, City,  State, PostalCode, Country, BankPhone, FedRTN, SwiftRTN, ChipsRTN,  OtherRTN, ExtBankId  FROM BPW_WireBank  WHERE BankName = ?  AND AddressLine1 = ?  AND City = ?  AND Country = ? ";
    Object localObject2 = { str3, str4, str5, str6 };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject2);
      if (localFFSResultSet.getNextRow())
      {
        a(localFFSResultSet, paramBPWBankInfo);
        paramBPWBankInfo.setStatusCode(0);
        paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
      else
      {
        paramBPWBankInfo.setStatusCode(16020);
        paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "Wire Bank " }, "WIRE_MESSAGE"));
      }
      FFSDebug.log("***", str1, "end", 6);
      BPWBankInfo localBPWBankInfo = paramBPWBankInfo;
      return localBPWBankInfo;
    }
    catch (Throwable localThrowable)
    {
      String str7 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, str7, 0);
      throw new FFSException(localThrowable, str7);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log("***", str1, "failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
  }
  
  public static BPWBankInfo getWireBanksByRTNCombination(FFSConnectionHolder paramFFSConnectionHolder, BPWBankInfo paramBPWBankInfo)
    throws FFSException
  {
    String str1 = "WireBank.getWireBanksByRTNCombination: ";
    StringBuffer localStringBuffer = null;
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = null;
    int i = 1;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    FFSDebug.log(str1, "start", 6);
    if (paramBPWBankInfo == null)
    {
      localObject1 = " failed: Null bankInfo is passed";
      FFSDebug.log("****", str1, (String)localObject1, 0);
      paramBPWBankInfo = new BPWBankInfo();
      paramBPWBankInfo.setStatusCode(16000);
      paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "BPWBankInfo" }, "WIRE_MESSAGE"));
      return paramBPWBankInfo;
    }
    if (paramFFSConnectionHolder == null)
    {
      localObject1 = " failed: Null database connection is passed";
      FFSDebug.log("****", str1, (String)localObject1, 0);
      paramBPWBankInfo.setStatusCode(16000);
      paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder" }, "WIRE_MESSAGE"));
      return paramBPWBankInfo;
    }
    FFSDebug.log(str1, ",SwiftRTN: ", paramBPWBankInfo.getSwiftRTN(), ",ChipsRTN: ", paramBPWBankInfo.getChipsRTN(), ",FedRTN: ", paramBPWBankInfo.getFedRTN(), ",OtherRTN: ", paramBPWBankInfo.getOtherRTN(), 6);
    str2 = paramBPWBankInfo.getFedRTN();
    str3 = paramBPWBankInfo.getSwiftRTN();
    str4 = paramBPWBankInfo.getChipsRTN();
    str5 = paramBPWBankInfo.getOtherRTN();
    localArrayList = new ArrayList();
    localStringBuffer = new StringBuffer();
    localStringBuffer.append("SELECT  BankId, BankName, AddressLine1, AddressLine2, AddressLine3, City,  State, PostalCode, Country, BankPhone, FedRTN, SwiftRTN, ChipsRTN,  OtherRTN, ExtBankId  FROM BPW_WireBank ");
    if (((str2 == null) || (str2.trim().length() == 0)) && ((str3 == null) || (str3.trim().length() == 0)) && ((str4 == null) || (str4.trim().length() == 0)) && ((str5 == null) || (str5.trim().length() == 0)))
    {
      localStringBuffer.append(" WHERE FedRTN is NULL ");
      localStringBuffer.append(" AND SwiftRTN is NULL ");
      localStringBuffer.append(" AND ChipsRTN is NULL ");
      localStringBuffer.append(" AND OtherRTN is NULL ");
    }
    else
    {
      if ((str2 != null) && (str2.trim().length() > 0))
      {
        localArrayList.add(str2);
        if (i != 0)
        {
          localStringBuffer.append(" WHERE FedRTN = ? ");
          i = 0;
        }
        else
        {
          localStringBuffer.append(" AND FedRTN = ? ");
        }
      }
      if ((str3 != null) && (str3.trim().length() > 0))
      {
        localArrayList.add(str3);
        if (i != 0)
        {
          localStringBuffer.append(" WHERE SwiftRTN = ? ");
          i = 0;
        }
        else
        {
          localStringBuffer.append(" AND SwiftRTN = ? ");
        }
      }
      if ((str4 != null) && (str4.trim().length() > 0))
      {
        localArrayList.add(str4);
        if (i != 0)
        {
          localStringBuffer.append(" WHERE ChipsRTN = ? ");
          i = 0;
        }
        else
        {
          localStringBuffer.append(" AND ChipsRTN = ? ");
        }
      }
      if ((str5 != null) && (str5.trim().length() > 0))
      {
        localArrayList.add(str5);
        if (i != 0)
        {
          localStringBuffer.append(" WHERE OtherRTN = ? ");
          i = 0;
        }
        else
        {
          localStringBuffer.append(" AND OtherRTN = ? ");
        }
      }
    }
    Object localObject1 = null;
    if (!localArrayList.isEmpty()) {
      localObject1 = localArrayList.toArray(new Object[0]);
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject1);
      if (localFFSResultSet.getNextRow())
      {
        a(localFFSResultSet, paramBPWBankInfo);
        paramBPWBankInfo.setStatusCode(0);
        paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
      else
      {
        paramBPWBankInfo.setStatusCode(16020);
        paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "Wire Bank" }, "WIRE_MESSAGE"));
      }
      FFSDebug.log("***", str1, "end", 6);
      BPWBankInfo localBPWBankInfo = paramBPWBankInfo;
      return localBPWBankInfo;
    }
    catch (Throwable localThrowable)
    {
      String str6 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, str6, 0);
      throw new FFSException(localThrowable, str6);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log("***", str1, "failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
  }
  
  public static BPWBankInfo findWireBank(FFSConnectionHolder paramFFSConnectionHolder, BPWBankInfo paramBPWBankInfo)
    throws FFSException
  {
    String str1 = "WireBank.findWireBank: ";
    FFSDebug.log(str1, ": start", 6);
    try
    {
      paramBPWBankInfo = getWireBankByExtID(paramFFSConnectionHolder, paramBPWBankInfo);
      Object localObject;
      if ((paramBPWBankInfo != null) && (paramBPWBankInfo.getStatusCode() == 0))
      {
        localObject = "A bank already exists for the Ext Bank ID:" + paramBPWBankInfo.getExtBankId();
        FFSDebug.log("***", str1, (String)localObject, 0);
        paramBPWBankInfo.setStatusCode(19230);
        localObject = BPWLocaleUtil.getMessage(19230, new String[] { "Bank ID:", paramBPWBankInfo.getExtBankId() }, "WIRE_MESSAGE");
        paramBPWBankInfo.setStatusCode(19230);
        paramBPWBankInfo.setStatusMsg((String)localObject);
        return paramBPWBankInfo;
      }
      if (((paramBPWBankInfo.getFedRTN() == null) || (paramBPWBankInfo.getFedRTN().trim().length() == 0)) && ((paramBPWBankInfo.getSwiftRTN() == null) || (paramBPWBankInfo.getSwiftRTN().trim().length() == 0)) && ((paramBPWBankInfo.getChipsRTN() == null) || (paramBPWBankInfo.getChipsRTN().trim().length() == 0)) && ((paramBPWBankInfo.getOtherRTN() == null) || (paramBPWBankInfo.getOtherRTN().trim().length() == 0)))
      {
        paramBPWBankInfo = getWireBankByNameAddr(paramFFSConnectionHolder, paramBPWBankInfo);
        if ((paramBPWBankInfo != null) && (paramBPWBankInfo.getStatusCode() == 0))
        {
          localObject = new StringBuffer();
          ((StringBuffer)localObject).append("A bank already exists");
          ((StringBuffer)localObject).append(" for the Name & Address: ");
          ((StringBuffer)localObject).append(" Name:").append(paramBPWBankInfo.getBankName());
          ((StringBuffer)localObject).append(", AddressLine1: ").append(paramBPWBankInfo.getBankAddr1());
          ((StringBuffer)localObject).append(", City: ").append(paramBPWBankInfo.getBankCity());
          ((StringBuffer)localObject).append(", Country: ").append(paramBPWBankInfo.getBankCountry());
          FFSDebug.log("***", str1, ((StringBuffer)localObject).toString(), 0);
          paramBPWBankInfo.setStatusCode(19230);
          paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(19230, new String[] { "Name:", paramBPWBankInfo.getBankName(), "AddressLine1:", paramBPWBankInfo.getBankAddr1(), "City:", paramBPWBankInfo.getBankCity(), "Country:", paramBPWBankInfo.getBankCountry() }, "WIRE_MESSAGE"));
          return paramBPWBankInfo;
        }
      }
      else
      {
        paramBPWBankInfo = getWireBanksByRTNCombination(paramFFSConnectionHolder, paramBPWBankInfo);
        if ((paramBPWBankInfo != null) && (paramBPWBankInfo.getStatusCode() == 0))
        {
          localObject = new StringBuffer();
          ((StringBuffer)localObject).append("A bank already exists");
          ((StringBuffer)localObject).append(" for the RTN combination:");
          ((StringBuffer)localObject).append(" swiftRTN: ").append(paramBPWBankInfo.getSwiftRTN());
          ((StringBuffer)localObject).append(", chipsRTN: ").append(paramBPWBankInfo.getChipsRTN());
          ((StringBuffer)localObject).append(", fedRTN: ").append(paramBPWBankInfo.getFedRTN());
          ((StringBuffer)localObject).append(", otherRTN: ").append(paramBPWBankInfo.getOtherRTN());
          FFSDebug.log("***", str1, ((StringBuffer)localObject).toString(), 0);
          paramBPWBankInfo.setStatusCode(19230);
          str2 = BPWLocaleUtil.getMessage(19230, new String[] { "swiftRTN: ", paramBPWBankInfo.getSwiftRTN(), "chipsRTN: ", paramBPWBankInfo.getChipsRTN(), "fedRTN:", paramBPWBankInfo.getFedRTN(), "otherRTN:", paramBPWBankInfo.getOtherRTN() }, "WIRE_MESSAGE");
          paramBPWBankInfo.setStatusMsg(str2);
          return paramBPWBankInfo;
        }
      }
      paramBPWBankInfo.setStatusCode(16020);
      paramBPWBankInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "BPWBankInfo" }, "WIRE_MESSAGE"));
    }
    catch (Throwable localThrowable)
    {
      String str2 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, str2, 0);
      throw new FFSException(localThrowable, str2);
    }
    FFSDebug.log(str1, ": done", 6);
    return paramBPWBankInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.WireBank
 * JD-Core Version:    0.7.0.1
 */