package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.achagent.ACHAgent;
import com.ffusion.ffs.bpw.interfaces.ACHAddendaInfo;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import java.util.ArrayList;
import java.util.Calendar;

public class ACHAddenda
  implements FFSConst, DBConsts, SQLConsts, ACHConsts
{
  public static ACHAddendaInfo addAddenda(ACHAddendaInfo paramACHAddendaInfo, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHAddenda.addAddenda (isRecurring: " + paramBoolean + " )";
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    Object localObject1 = null;
    FFSDebug.log(str1 + ": start. ", 6);
    Object localObject2;
    if (paramACHAddendaInfo == null)
    {
      paramACHAddendaInfo = new ACHAddendaInfo();
      paramACHAddendaInfo.setStatusCode(16000);
      localObject2 = "ACHAddendaInfo object  is null";
      paramACHAddendaInfo.setStatusMsg((String)localObject2);
      FFSDebug.log(str1 + " failed, " + (String)localObject2, 0);
      return paramACHAddendaInfo;
    }
    if (paramFFSConnectionHolder == null)
    {
      paramACHAddendaInfo.setStatusCode(16000);
      localObject2 = "FFSConnectionHolder object  is null";
      paramACHAddendaInfo.setStatusMsg((String)localObject2);
      FFSDebug.log(str1 + " failed, " + (String)localObject2, 0);
      return paramACHAddendaInfo;
    }
    try
    {
      if (paramBoolean)
      {
        str2 = "INSERT INTO ACH_RecAddenda (RecAddendaId, RecordId, AddendaSeqNum, AddendaType,RecordType, FrgnPmtAmt, FrgnRcvrAcctNum, FrgnRcvingDFIId, FrgnTraceNum,NetworkIdCode, PmtInfo, RefInfo_1, RefInfo_2, TerminalCity, TerminalIdCode, TerminalLoc, TerminalState, TraceNum, TransDate, TransDesc, TransSerialNum, TransCode, SubmittedBy, SubmitDate, AddendaStatus, AddendaContent, LogId, CardAuthCode) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        str3 = DBUtil.getNextIndexStringWithPadding("RecAddendaID", 32, '0');
      }
      else
      {
        str2 = "INSERT INTO ACH_Addenda (AddendaId, RecordId, AddendaSeqNum, AddendaType,RecordType, FrgnPmtAmt, FrgnRcvrAcctNum, FrgnRcvingDFIId, FrgnTraceNum,NetworkIdCode, PmtInfo, RefInfo_1, RefInfo_2, TerminalCity, TerminalIdCode, TerminalLoc, TerminalState, TraceNum, TransDate, TransDesc, TransSerialNum, TransCode, SubmittedBy, SubmitDate, AddendaStatus, AddendaContent, LogId,CardAuthCode) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        str3 = DBUtil.getNextIndexStringWithPadding("AddendaID", 32, '0');
      }
      paramACHAddendaInfo.setAddendaId(str3);
      FFSDebug.log(str1 + ":: addendaId=", paramACHAddendaInfo.getAddendaId(), 6);
      FFSDebug.log(str1 + ":: recordId=", paramACHAddendaInfo.getRecordId(), 6);
      if (paramACHAddendaInfo.getFieldValueObject("Transaction_Date") != null)
      {
        str4 = String.valueOf(Calendar.getInstance().get(1));
        localObject2 = new StringBuffer();
        str6 = null;
        ((StringBuffer)localObject2).append(str4.substring(2));
        ((StringBuffer)localObject2).append(paramACHAddendaInfo.getFieldValueObject("Transaction_Date"));
        str6 = (String)paramACHAddendaInfo.getFieldValueObject("Transaction_Time");
        if (str6 == null) {
          str6 = "000000";
        }
        ((StringBuffer)localObject2).append(str6);
        str5 = ((StringBuffer)localObject2).toString();
      }
      ACHAgent.build(paramACHAddendaInfo);
      if (!validateAddenda(paramACHAddendaInfo))
      {
        localObject2 = paramACHAddendaInfo.getStatusMsg();
        FFSDebug.log(str1 + " failed, " + (String)localObject2, 0);
        return paramACHAddendaInfo;
      }
      localObject2 = new Object[] { paramACHAddendaInfo.getAddendaId(), paramACHAddendaInfo.getRecordId(), paramACHAddendaInfo.getFieldValueObject("Addenda_Sequence_Number") == null ? null : paramACHAddendaInfo.getFieldValueObject("Addenda_Sequence_Number").toString(), paramACHAddendaInfo.getFieldValueObject("Addenda_Type_Code") == null ? null : paramACHAddendaInfo.getFieldValueObject("Addenda_Type_Code").toString(), Integer.toString(7), paramACHAddendaInfo.getFieldValueObject("Foreign_Payment_Amount") == null ? null : paramACHAddendaInfo.getFieldValueObject("Foreign_Payment_Amount").toString(), paramACHAddendaInfo.getFieldValueObject("Foreign_Receivers_Account_Number") == null ? null : paramACHAddendaInfo.getFieldValueObject("Foreign_Receivers_Account_Number").toString(), paramACHAddendaInfo.getFieldValueObject("Foreign_Receiving_DFI_Identification") == null ? null : paramACHAddendaInfo.getFieldValueObject("Foreign_Receiving_DFI_Identification").toString(), paramACHAddendaInfo.getFieldValueObject("Foreign_Trace_Number") == null ? null : paramACHAddendaInfo.getFieldValueObject("Foreign_Trace_Number").toString(), paramACHAddendaInfo.getFieldValueObject("Network_Identification_Code") == null ? null : paramACHAddendaInfo.getFieldValueObject("Network_Identification_Code").toString(), paramACHAddendaInfo.getFieldValueObject("Payment_Related_Information") == null ? null : paramACHAddendaInfo.getFieldValueObject("Payment_Related_Information").toString(), paramACHAddendaInfo.getFieldValueObject("Reference_Information1") == null ? null : paramACHAddendaInfo.getFieldValueObject("Reference_Information1").toString(), paramACHAddendaInfo.getFieldValueObject("Reference_Information2") == null ? null : paramACHAddendaInfo.getFieldValueObject("Reference_Information2").toString(), paramACHAddendaInfo.getFieldValueObject("Terminal_City") == null ? null : paramACHAddendaInfo.getFieldValueObject("Terminal_City").toString(), paramACHAddendaInfo.getFieldValueObject("Terminal_Identification_Code") == null ? null : paramACHAddendaInfo.getFieldValueObject("Terminal_Identification_Code").toString(), paramACHAddendaInfo.getFieldValueObject("Terminal_Location") == null ? null : paramACHAddendaInfo.getFieldValueObject("Terminal_Location").toString(), paramACHAddendaInfo.getFieldValueObject("Terminal_State") == null ? null : paramACHAddendaInfo.getFieldValueObject("Terminal_State").toString(), paramACHAddendaInfo.getFieldValueObject("Trace_Number") == null ? null : paramACHAddendaInfo.getFieldValueObject("Trace_Number").toString(), str5, paramACHAddendaInfo.getFieldValueObject("Transaction_Description") == null ? null : paramACHAddendaInfo.getFieldValueObject("Transaction_Description").toString(), paramACHAddendaInfo.getFieldValueObject("Transaction_Serial_Number") == null ? null : paramACHAddendaInfo.getFieldValueObject("Transaction_Serial_Number").toString(), paramACHAddendaInfo.getFieldValueObject("Transaction_Type_Code") == null ? null : paramACHAddendaInfo.getFieldValueObject("Transaction_Type_Code").toString(), paramACHAddendaInfo.getSubmittedBy(), FFSUtil.getDateString(), "WILLPROCESSON", paramACHAddendaInfo.getAddendaContent(), paramACHAddendaInfo.getLogId(), paramACHAddendaInfo.getFieldValueObject("Authorization_Code_Or_Card_Expiration_Date") == null ? null : paramACHAddendaInfo.getFieldValueObject("Authorization_Code_Or_Card_Expiration_Date").toString() };
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject2);
    }
    catch (Exception localException)
    {
      String str6 = "*** " + str1 + " : failed:";
      String str7 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str7, 0);
      throw new FFSException(localException, str6);
    }
    paramACHAddendaInfo.setStatusCode(0);
    paramACHAddendaInfo.setStatusMsg("Success");
    FFSDebug.log(str1 + ": done", 6);
    return paramACHAddendaInfo;
  }
  
  public static ACHAddendaInfo updateACHAddenda(FFSConnectionHolder paramFFSConnectionHolder, ACHAddendaInfo paramACHAddendaInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHAddenda.updateACHAddenda";
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    Object localObject1 = null;
    if (paramBoolean) {
      str2 = "UPDATE ACH_RecAddenda set AddendaSeqNum=?, AddendaType = ?,RecordType =?, FrgnPmtAmt =?, FrgnRcvrAcctNum =?, FrgnRcvingDFIId =?, FrgnTraceNum =?,NetworkIdCode =?, PmtInfo =?, RefInfo_1 =?, RefInfo_2 =?, TerminalCity =?, TerminalIdCode =?, TerminalLoc =?, TerminalState =?, TraceNum =?, TransDate =?, TransDesc =?, TransSerialNum =?, TransCode =?, SubmittedBy =?, SubmitDate =?, AddendaContent=? WHERE RecAddendaId = ? ";
    } else {
      str2 = "UPDATE ACH_Addenda set AddendaSeqNum=?, AddendaType = ?,RecordType =?, FrgnPmtAmt =?, FrgnRcvrAcctNum =?, FrgnRcvingDFIId =?, FrgnTraceNum =?,NetworkIdCode =?, PmtInfo =?, RefInfo_1 =?, RefInfo_2 =?, TerminalCity =?, TerminalIdCode =?, TerminalLoc =?, TerminalState =?, TraceNum =?, TransDate =?, TransDesc =?, TransSerialNum =?, TransCode =?, SubmittedBy =?, SubmitDate =?, AddendaContent=? WHERE AddendaId = ? ";
    }
    FFSDebug.log(str1 + ": start" + "isRecurring: " + paramBoolean, 6);
    Object localObject2;
    if (paramACHAddendaInfo == null)
    {
      paramACHAddendaInfo = new ACHAddendaInfo();
      paramACHAddendaInfo.setStatusCode(16000);
      localObject2 = "ACHAddendaInfo object  is null";
      paramACHAddendaInfo.setStatusMsg((String)localObject2);
      FFSDebug.log(str1 + " failed, " + (String)localObject2, 0);
      return paramACHAddendaInfo;
    }
    if (paramFFSConnectionHolder == null)
    {
      paramACHAddendaInfo.setStatusCode(16000);
      localObject2 = "FFSConnectionHolder object  is null";
      paramACHAddendaInfo.setStatusMsg((String)localObject2);
      FFSDebug.log(str1 + " failed, " + (String)localObject2, 0);
      return paramACHAddendaInfo;
    }
    try
    {
      str3 = paramACHAddendaInfo.getAddendaId();
      FFSDebug.log(str1 + ":: addendaId=", str3, 6);
      FFSDebug.log(str1 + ":: recordId=", paramACHAddendaInfo.getRecordId(), 6);
      if (paramACHAddendaInfo.getFieldValueObject("Transaction_Date") != null)
      {
        str4 = String.valueOf(Calendar.getInstance().get(1));
        localObject2 = new StringBuffer();
        str6 = null;
        ((StringBuffer)localObject2).append(str4.substring(2));
        ((StringBuffer)localObject2).append(paramACHAddendaInfo.getFieldValueObject("Transaction_Date"));
        str6 = (String)paramACHAddendaInfo.getFieldValueObject("Transaction_Time");
        if (str6 == null) {
          str6 = "000000";
        }
        ((StringBuffer)localObject2).append(str6);
        str5 = ((StringBuffer)localObject2).toString();
      }
      ACHAgent.build(paramACHAddendaInfo);
      localObject2 = new Object[] { paramACHAddendaInfo.getFieldValueObject("Addenda_Sequence_Number") == null ? null : paramACHAddendaInfo.getFieldValueObject("Addenda_Sequence_Number").toString(), paramACHAddendaInfo.getFieldValueObject("Addenda_Type_Code") == null ? null : paramACHAddendaInfo.getFieldValueObject("Addenda_Type_Code").toString(), Integer.toString(7), paramACHAddendaInfo.getFieldValueObject("Foreign_Payment_Amount") == null ? null : paramACHAddendaInfo.getFieldValueObject("Foreign_Payment_Amount").toString(), paramACHAddendaInfo.getFieldValueObject("Foreign_Receivers_Account_Number") == null ? null : paramACHAddendaInfo.getFieldValueObject("Foreign_Receivers_Account_Number").toString(), paramACHAddendaInfo.getFieldValueObject("Foreign_Receiving_DFI_Identification") == null ? null : paramACHAddendaInfo.getFieldValueObject("Foreign_Receiving_DFI_Identification").toString(), paramACHAddendaInfo.getFieldValueObject("Foreign_Trace_Number") == null ? null : paramACHAddendaInfo.getFieldValueObject("Foreign_Trace_Number").toString(), paramACHAddendaInfo.getFieldValueObject("Network_Identification_Code") == null ? null : paramACHAddendaInfo.getFieldValueObject("Network_Identification_Code").toString(), paramACHAddendaInfo.getFieldValueObject("Payment_Related_Information") == null ? null : paramACHAddendaInfo.getFieldValueObject("Payment_Related_Information").toString(), paramACHAddendaInfo.getFieldValueObject("Reference_Information1") == null ? null : paramACHAddendaInfo.getFieldValueObject("Reference_Information1").toString(), paramACHAddendaInfo.getFieldValueObject("Reference_Information2") == null ? null : paramACHAddendaInfo.getFieldValueObject("Reference_Information2").toString(), paramACHAddendaInfo.getFieldValueObject("Terminal_City") == null ? null : paramACHAddendaInfo.getFieldValueObject("Terminal_City").toString(), paramACHAddendaInfo.getFieldValueObject("Terminal_Identification_Code") == null ? null : paramACHAddendaInfo.getFieldValueObject("Terminal_Identification_Code").toString(), paramACHAddendaInfo.getFieldValueObject("Terminal_Location") == null ? null : paramACHAddendaInfo.getFieldValueObject("Terminal_Location").toString(), paramACHAddendaInfo.getFieldValueObject("Terminal_State") == null ? null : paramACHAddendaInfo.getFieldValueObject("Terminal_State").toString(), paramACHAddendaInfo.getFieldValueObject("Trace_Number") == null ? null : paramACHAddendaInfo.getFieldValueObject("Trace_Number").toString(), str5, paramACHAddendaInfo.getFieldValueObject("Transaction_Description") == null ? null : paramACHAddendaInfo.getFieldValueObject("Transaction_Description").toString(), paramACHAddendaInfo.getFieldValueObject("Transaction_Serial_Number") == null ? null : paramACHAddendaInfo.getFieldValueObject("Transaction_Serial_Number").toString(), paramACHAddendaInfo.getFieldValueObject("Transaction_Type_Code") == null ? null : paramACHAddendaInfo.getFieldValueObject("Transaction_Type_Code").toString(), paramACHAddendaInfo.getSubmittedBy(), FFSUtil.getDateString(), paramACHAddendaInfo.getAddendaContent(), str3 };
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject2);
    }
    catch (Exception localException)
    {
      String str6 = "*** " + str1 + ": failed:";
      String str7 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str7, 0);
      throw new FFSException(localException, str6);
    }
    paramACHAddendaInfo.setStatusCode(0);
    paramACHAddendaInfo.setStatusMsg("Success");
    FFSDebug.log(str1 + ": done", 6);
    return paramACHAddendaInfo;
  }
  
  public static ACHAddendaInfo canACHAddenda(FFSConnectionHolder paramFFSConnectionHolder, ACHAddendaInfo paramACHAddendaInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHAddenda.canACHAddendaStatus";
    String str2 = "DELETE FROM ACH_Addenda  WHERE AddendaId = ?";
    String str3 = null;
    FFSDebug.log(str1 + ": start. " + "isRecurring: " + paramBoolean, 6);
    if (paramBoolean == true) {
      str2 = "DELETE FROM ACH_RecAddenda  WHERE RecAddendaId = ?";
    } else {
      str2 = "DELETE FROM ACH_Addenda  WHERE AddendaId = ?";
    }
    Object localObject;
    if (paramFFSConnectionHolder == null)
    {
      paramACHAddendaInfo.setStatusCode(16000);
      localObject = "FFSConnectionHolder object  is null";
      paramACHAddendaInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1 + " failed, " + (String)localObject, 0);
      return paramACHAddendaInfo;
    }
    try
    {
      str3 = paramACHAddendaInfo.getAddendaId();
      FFSDebug.log(str1 + ":: addendaId=", str3, 6);
      localObject = new Object[] { str3 };
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
    }
    catch (Exception localException)
    {
      String str4 = "*** " + str1 + ": failed:";
      String str5 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str5, 0);
      throw new FFSException(localException, str4);
    }
    paramACHAddendaInfo.setStatusCode(0);
    paramACHAddendaInfo.setStatusMsg("Success");
    FFSDebug.log(str1 + ": done", 6);
    return paramACHAddendaInfo;
  }
  
  public static ACHAddendaInfo updateACHAddendaStatus(ACHAddendaInfo paramACHAddendaInfo, String paramString, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHAddenda.updateACHAddendaStatus";
    String str2 = "UPDATE ACH_Addenda set AddendaStatus = ?  WHERE AddendaId = ?";
    String str3 = null;
    FFSDebug.log(str1 + ": start. " + "isRecurring: " + paramBoolean, 6);
    if (paramBoolean == true) {
      str2 = "UPDATE ACH_RecAddenda set AddendaStatus = ?  WHERE RecAddendaId = ?";
    } else {
      str2 = "UPDATE ACH_Addenda set AddendaStatus = ?  WHERE AddendaId = ?";
    }
    Object localObject;
    if (paramFFSConnectionHolder == null)
    {
      paramACHAddendaInfo.setStatusCode(16000);
      localObject = "FFSConnectionHolder object  is null";
      paramACHAddendaInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1 + " failed, " + (String)localObject, 0);
      return paramACHAddendaInfo;
    }
    try
    {
      str3 = paramACHAddendaInfo.getAddendaId();
      FFSDebug.log(str1 + ":: addendaId=", str3, 6);
      localObject = new Object[] { paramString, str3 };
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
    }
    catch (Exception localException)
    {
      String str4 = "*** " + str1 + ": failed:";
      String str5 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str5, 0);
      throw new FFSException(localException, str4);
    }
    paramACHAddendaInfo.setStatusCode(0);
    paramACHAddendaInfo.setStatusMsg("Success");
    FFSDebug.log(str1 + ": done", 6);
    return paramACHAddendaInfo;
  }
  
  public static ACHAddendaInfo[] getACHAddendas(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean1, boolean paramBoolean2)
    throws Exception
  {
    String str1 = "ACHAddenda.getACHAddendas ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    ACHAddendaInfo[] arrayOfACHAddendaInfo = null;
    ACHAddendaInfo localACHAddendaInfo = null;
    ArrayList localArrayList = null;
    FFSDebug.log(str1 + ": start, " + "recordId= " + paramString1 + "parseAddenda= " + paramBoolean1 + "isRecurring= " + paramBoolean2, 6);
    if (paramString1 == null)
    {
      localObject1 = "recordId  is null";
      FFSDebug.log(str1 + "failed, " + (String)localObject1, 0);
      return null;
    }
    if (paramFFSConnectionHolder == null)
    {
      localObject1 = "FFSConnectionHolder object  is null";
      FFSDebug.log(str1 + "failed, " + (String)localObject1, 0);
      return null;
    }
    if (paramBoolean2) {
      str2 = "SELECT RecAddendaId, RecordId, AddendaSeqNum, AddendaType, RecordType, FrgnPmtAmt, FrgnRcvrAcctNum, FrgnRcvingDFIId, FrgnTraceNum, NetworkIdCode, PmtInfo, RefInfo_1, RefInfo_2, TerminalCity, TerminalIdCode, TerminalLoc, TerminalState, TraceNum, TransDate, TransDesc, TransSerialNum, TransCode, SubmittedBy, SubmitDate, AddendaStatus, AddendaContent, LogId, CardAuthCode  FROM ACH_RecAddenda  WHERE RecordId = ? AND AddendaStatus != 'CANCELEDON' ORDER BY RecAddendaId";
    } else {
      str2 = "SELECT AddendaId, RecordId, AddendaSeqNum, AddendaType, RecordType, FrgnPmtAmt, FrgnRcvrAcctNum, FrgnRcvingDFIId, FrgnTraceNum, NetworkIdCode, PmtInfo, RefInfo_1, RefInfo_2, TerminalCity, TerminalIdCode, TerminalLoc, TerminalState, TraceNum, TransDate, TransDesc, TransSerialNum, TransCode, SubmittedBy, SubmitDate, AddendaStatus, AddendaContent, LogId, CardAuthCode  FROM ACH_Addenda  WHERE RecordId = ? AND AddendaStatus != 'CANCELEDON' ORDER BY AddendaId";
    }
    Object localObject1 = { paramString1 };
    localArrayList = new ArrayList();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject1);
      while (localFFSResultSet.getNextRow())
      {
        localACHAddendaInfo = new ACHAddendaInfo();
        localACHAddendaInfo.setClassCode(paramString2);
        a(localACHAddendaInfo, localFFSResultSet, paramBoolean1, paramBoolean2);
        localArrayList.add(localACHAddendaInfo);
      }
    }
    catch (Exception localException1)
    {
      throw localException1;
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
        FFSDebug.log("***  " + str1 + ": failed to close:" + localException2.toString(), 0);
      }
    }
    arrayOfACHAddendaInfo = (ACHAddendaInfo[])localArrayList.toArray(new ACHAddendaInfo[0]);
    FFSDebug.log(str1 + ": done", 6);
    return arrayOfACHAddendaInfo;
  }
  
  private static void a(ACHAddendaInfo paramACHAddendaInfo, FFSResultSet paramFFSResultSet, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    if (paramBoolean2 == true)
    {
      paramACHAddendaInfo.setAddendaId(paramFFSResultSet.getColumnString("RecAddendaId"));
      paramACHAddendaInfo.setRecordId(paramFFSResultSet.getColumnString("RecordId"));
      paramACHAddendaInfo.setSubmitDate(paramFFSResultSet.getColumnString("SubmitDate"));
      paramACHAddendaInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
      paramACHAddendaInfo.setAddendaStatus(paramFFSResultSet.getColumnString("AddendaStatus"));
      paramACHAddendaInfo.setAddendaContent(paramFFSResultSet.getColumnString("AddendaContent"));
      paramACHAddendaInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
    }
    else
    {
      paramACHAddendaInfo.setAddendaId(paramFFSResultSet.getColumnString("AddendaId"));
      paramACHAddendaInfo.setRecordId(paramFFSResultSet.getColumnString("RecordId"));
      paramACHAddendaInfo.setSubmitDate(paramFFSResultSet.getColumnString("SubmitDate"));
      paramACHAddendaInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
      paramACHAddendaInfo.setAddendaStatus(paramFFSResultSet.getColumnString("AddendaStatus"));
      paramACHAddendaInfo.setAddendaContent(paramFFSResultSet.getColumnString("AddendaContent"));
      paramACHAddendaInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
    }
    if (paramBoolean1 == true) {
      ACHAgent.parse(paramACHAddendaInfo);
    }
    paramACHAddendaInfo.setStatusCode(0);
    paramACHAddendaInfo.setStatusMsg("Success");
  }
  
  public static boolean validateAddenda(ACHAddendaInfo paramACHAddendaInfo)
  {
    String str = paramACHAddendaInfo.getAddendaContent();
    if (str == null) {
      return true;
    }
    int i = str.length();
    int j = 3;
    while ((j <= 82) && (j < i - 1))
    {
      char c = str.charAt(j);
      j++;
      if (c < ' ')
      {
        paramACHAddendaInfo.setStatusCode(23710);
        paramACHAddendaInfo.setStatusMsg("Addenda content has non-printable character: " + c + ", position: " + j);
        return false;
      }
    }
    return true;
  }
  
  public static int generateACHAddendasFromACHRecAddendas(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "ACHAddenda.generateACHAddendasFromACHRecAddendas:";
    FFSDebug.log(str1 + ": start, " + "recRecordId= " + paramString1 + "recordId= " + paramString2, 6);
    if (paramFFSConnectionHolder == null)
    {
      localObject1 = str1 + "failed, FFSConnectionHolder object is Null";
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException((String)localObject1);
    }
    Object localObject1 = null;
    String str2 = null;
    Object[] arrayOfObject = null;
    int i = 0;
    try
    {
      String str3 = "SELECT RecAddendaId, RecordId, AddendaSeqNum, AddendaType, RecordType, FrgnPmtAmt, FrgnRcvrAcctNum, FrgnRcvingDFIId, FrgnTraceNum, NetworkIdCode, PmtInfo, RefInfo_1, RefInfo_2, TerminalCity, TerminalIdCode, TerminalLoc, TerminalState, TraceNum, TransDate, TransDesc, TransSerialNum, TransCode, SubmittedBy, SubmitDate, AddendaStatus, AddendaContent, LogId, CardAuthCode  FROM ACH_RecAddenda  WHERE RecordId = ?  AND AddendaStatus != 'CANCELEDON' Order by RecAddendaId ";
      localObject2 = new Object[] { paramString1 };
      localObject1 = DBUtil.openResultSet(paramFFSConnectionHolder, str3, (Object[])localObject2);
      while (((FFSResultSet)localObject1).getNextRow())
      {
        str2 = DBUtil.getNextIndexStringWithPadding("AddendaID", 32, '0');
        str3 = "INSERT INTO ACH_Addenda (AddendaId, RecordId, AddendaSeqNum, AddendaType,RecordType, FrgnPmtAmt, FrgnRcvrAcctNum, FrgnRcvingDFIId, FrgnTraceNum,NetworkIdCode, PmtInfo, RefInfo_1, RefInfo_2, TerminalCity, TerminalIdCode, TerminalLoc, TerminalState, TraceNum, TransDate, TransDesc, TransSerialNum, TransCode, SubmittedBy, SubmitDate, AddendaStatus, AddendaContent, LogId,CardAuthCode) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        arrayOfObject = jdMethod_if(str2, paramString2, (FFSResultSet)localObject1);
        DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
        i++;
      }
    }
    catch (FFSException localFFSException)
    {
      localObject2 = "***  " + str1 + ": failed:";
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str4, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      Object localObject2 = "***  " + str1 + ": failed:";
      String str4 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException1, (String)localObject2);
    }
    finally
    {
      try
      {
        if (localObject1 != null)
        {
          ((FFSResultSet)localObject1).close();
          localObject1 = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("***  " + str1 + ": failed to close:" + localException2.toString(), 0);
      }
    }
    FFSDebug.log(str1 + ": end, recordCount =" + i, 6);
    return i;
  }
  
  private static Object[] jdMethod_if(String paramString1, String paramString2, FFSResultSet paramFFSResultSet)
    throws FFSException
  {
    Object[] arrayOfObject = { paramString1, paramString2, paramFFSResultSet.getColumnString("AddendaSeqNum"), paramFFSResultSet.getColumnString("AddendaType"), String.valueOf(7), paramFFSResultSet.getColumnString("FrgnPmtAmt"), paramFFSResultSet.getColumnString("FrgnRcvrAcctNum"), paramFFSResultSet.getColumnString("FrgnRcvingDFIId"), paramFFSResultSet.getColumnString("FrgnTraceNum"), paramFFSResultSet.getColumnString("NetworkIdCode"), paramFFSResultSet.getColumnString("PmtInfo"), paramFFSResultSet.getColumnString("RefInfo_1"), paramFFSResultSet.getColumnString("RefInfo_2"), paramFFSResultSet.getColumnString("TerminalCity"), paramFFSResultSet.getColumnString("TerminalIdCode"), paramFFSResultSet.getColumnString("TerminalLoc"), paramFFSResultSet.getColumnString("TerminalState"), paramFFSResultSet.getColumnString("TraceNum"), paramFFSResultSet.getColumnString("TransDate"), paramFFSResultSet.getColumnString("TransDesc"), paramFFSResultSet.getColumnString("TransSerialNum"), paramFFSResultSet.getColumnString("TransCode"), paramFFSResultSet.getColumnString("SubmittedBy"), FFSUtil.getDateString(), "WILLPROCESSON", paramFFSResultSet.getColumnString("AddendaContent"), paramFFSResultSet.getColumnString("LogId"), paramFFSResultSet.getColumnString("CardAuthCode") };
    return arrayOfObject;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.ACHAddenda
 * JD-Core Version:    0.7.0.1
 */