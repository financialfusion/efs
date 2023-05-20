package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfoLog;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankAcctFromV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInfoV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInfoAggregate;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

public class RecPmtInstruction
  implements FFSConst, DBConsts
{
  private RecPmtInfo jW;
  private static Hashtable jV = new Hashtable();
  
  public RecPmtInstruction()
  {
    this.jW = new RecPmtInfo();
  }
  
  public RecPmtInstruction(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("RecPmtInstruction(recsrvrTID) start, recSrvrTID=" + paramString, 6);
    this.jW = new RecPmtInfo();
    this.jW.RecSrvrTID = null;
    String str = "SELECT CustomerID,PayeeID, PayeeListID,BankID, AcctDebitID, AcctDebitType,PayAcct,DateCreate, CurDef, Amount,InitialAmt, FinalAmt, InstanceCount, Frequency,StartDate,EndDate,LogID, Status, Memo, FIID, SubmittedBy,PaymentName, TemplateID, PaymentType, BatchID FROM BPW_RecPmtInstruction WHERE RecSrvrTID=?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      FFSDebug.log("Reading RecPmtInstructions for recsrvrTID = " + paramString, 6);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        this.jW.RecSrvrTID = paramString;
        this.jW.CustomerID = localFFSResultSet.getColumnString("CustomerID");
        this.jW.PayeeID = localFFSResultSet.getColumnString("PayeeID");
        this.jW.PayeeListID = localFFSResultSet.getColumnInt("PayeeListID");
        this.jW.BankID = localFFSResultSet.getColumnString("BankID");
        this.jW.AcctDebitID = localFFSResultSet.getColumnString("AcctDebitID");
        this.jW.AcctDebitType = localFFSResultSet.getColumnString("AcctDebitType");
        this.jW.PayAcct = localFFSResultSet.getColumnString("PayAcct");
        this.jW.DateCreate = localFFSResultSet.getColumnString("DateCreate");
        this.jW.CurDef = localFFSResultSet.getColumnString("CurDef");
        this.jW.setAmt(localFFSResultSet.getColumnString("Amount"));
        this.jW.setInitialAmount(localFFSResultSet.getColumnString("InitialAmt"));
        this.jW.setFinalAmount(localFFSResultSet.getColumnString("FinalAmt"));
        this.jW.InstanceCount = localFFSResultSet.getColumnInt("InstanceCount");
        this.jW.setRecFrequency(localFFSResultSet.getColumnInt("Frequency"));
        this.jW.StartDate = localFFSResultSet.getColumnInt("StartDate");
        this.jW.EndDate = localFFSResultSet.getColumnInt("EndDate");
        this.jW.LogID = localFFSResultSet.getColumnString("LogID");
        this.jW.Status = localFFSResultSet.getColumnString("Status");
        this.jW.Memo = localFFSResultSet.getColumnString("Memo");
        this.jW.FIID = localFFSResultSet.getColumnString("FIID");
        this.jW.submittedBy = localFFSResultSet.getColumnString("SubmittedBy");
        this.jW.paymentName = localFFSResultSet.getColumnString("PaymentName");
        this.jW.templateID = localFFSResultSet.getColumnString("TemplateID");
        this.jW.PaymentType = localFFSResultSet.getColumnString("PaymentType");
        this.jW.batchID = localFFSResultSet.getColumnString("BatchID");
        this.jW.ExtdPmtInfo = jdMethod_long(paramFFSConnectionHolder, this.jW.FIID, paramString);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("**** Exception in PmtInstruction.instructor:" + localException1.toString() + ": " + FFSDebug.stackTrace(localException1));
      throw new BPWException(localException1.toString());
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
        FFSDebug.log("*** PmtInstruction.instructor: failed:" + localException2.toString());
      }
    }
    FFSDebug.log("RecPmtInstruction(recsrvrTID) done, recSrvrTID=" + paramString, 6);
  }
  
  public void setRecPmtInfo(RecPmtInfo paramRecPmtInfo)
  {
    this.jW = paramRecPmtInfo;
  }
  
  public RecPmtInfo getRecPmtInfo()
  {
    return this.jW;
  }
  
  public void setStatus(String paramString)
  {
    this.jW.Status = paramString;
  }
  
  public String getStatus()
  {
    return this.jW.Status;
  }
  
  public void setFrequency(int paramInt)
  {
    this.jW.setRecFrequency(paramInt);
  }
  
  public int getFrequency()
  {
    return this.jW.getRecFrequencyValue();
  }
  
  public void setStartDate(int paramInt)
  {
    this.jW.StartDate = paramInt;
  }
  
  public void setEndDate(int paramInt)
  {
    this.jW.EndDate = paramInt;
  }
  
  public int getStartDate()
  {
    return this.jW.StartDate;
  }
  
  public int getEndDate()
  {
    return this.jW.EndDate;
  }
  
  public void setInstanceCount(int paramInt)
  {
    this.jW.InstanceCount = paramInt;
  }
  
  public int getInstanceCount()
  {
    return this.jW.InstanceCount;
  }
  
  public void setLogID(String paramString)
  {
    this.jW.LogID = paramString;
  }
  
  public String getLogID()
  {
    return this.jW.LogID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.jW.CustomerID = paramString;
  }
  
  public String getCustomerID()
  {
    return this.jW.CustomerID;
  }
  
  public void setAcctDebitID(String paramString)
  {
    this.jW.AcctDebitID = paramString;
  }
  
  public String getAcctDebitID()
  {
    return this.jW.AcctDebitID;
  }
  
  public String getAcctDebitType()
  {
    return this.jW.AcctDebitType;
  }
  
  public void setPayeeID(String paramString)
  {
    this.jW.PayeeID = paramString;
  }
  
  public String getPayeeID()
  {
    return this.jW.PayeeID;
  }
  
  public void setPayeeListID(int paramInt)
  {
    this.jW.PayeeListID = paramInt;
  }
  
  public int getPayeeListID()
  {
    return this.jW.PayeeListID;
  }
  
  public String getAmt()
  {
    return this.jW.getAmt();
  }
  
  public void setAmt(String paramString)
  {
    this.jW.setAmt(paramString);
  }
  
  public double getFinalAmt()
  {
    return this.jW.FinalAmt;
  }
  
  public String getFinalAmount()
  {
    return this.jW.getFinalAmount();
  }
  
  public void setFinalAmt(double paramDouble)
  {
    this.jW.FinalAmt = paramDouble;
  }
  
  public void setFinalAmount(String paramString)
  {
    this.jW.setFinalAmount(paramString);
  }
  
  public double getInitialAmt()
  {
    return this.jW.InitialAmt;
  }
  
  public String getInitialAmount()
  {
    return this.jW.getInitialAmount();
  }
  
  public void setInitialAmt(double paramDouble)
  {
    this.jW.InitialAmt = paramDouble;
  }
  
  public void setInitialAmount(String paramString)
  {
    this.jW.setInitialAmount(paramString);
  }
  
  public String getPayAcct()
  {
    return this.jW.PayAcct;
  }
  
  public void setRecSrvrTID()
    throws BPWException
  {
    this.jW.RecSrvrTID = DBUtil.getNextIndexString("RecSrvrTID");
  }
  
  public void setRecSrvrTID(String paramString)
  {
    this.jW.RecSrvrTID = paramString;
  }
  
  public String getRecSrvrTID()
  {
    return this.jW.RecSrvrTID;
  }
  
  public void setFIID(String paramString)
  {
    this.jW.FIID = paramString;
  }
  
  public String getFIID()
  {
    return this.jW.FIID;
  }
  
  public void setSubmittedBy(String paramString)
  {
    this.jW.submittedBy = paramString;
  }
  
  public String getSubmittedBy()
  {
    return this.jW.submittedBy;
  }
  
  public void mapFromPmtInfoV1(TypePmtInfoV1Aggregate paramTypePmtInfoV1Aggregate, com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException, OFXException
  {
    String str = String.valueOf(paramTypePmtInfoV1Aggregate.TrnAmt);
    if (!FFSUtil.isPositive(str)) {
      throw new OFXException("Invalid amount", 2012);
    }
    this.jW.setAmt(str);
    this.jW.PayAcct = paramTypePmtInfoV1Aggregate.PayAcct;
    this.jW.CurDef = BPWUtil.validateCurrencyEnum(paramEnumCurrencyEnum);
    this.jW.BankID = paramTypePmtInfoV1Aggregate.BankAcctFrom.BankID;
    this.jW.AcctDebitID = paramTypePmtInfoV1Aggregate.BankAcctFrom.AcctID;
    this.jW.AcctDebitType = MsgBuilder.getAcctType(paramTypePmtInfoV1Aggregate.BankAcctFrom.AcctType);
    this.jW.DateCreate = DBUtil.getCurrentLogDate();
    if (paramTypePmtInfoV1Aggregate.MemoExists) {
      this.jW.Memo = paramTypePmtInfoV1Aggregate.Memo;
    }
  }
  
  public void mapFromPmtInfoV1(TypePmtInfoAggregate paramTypePmtInfoAggregate, com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException, OFXException
  {
    String str = String.valueOf(paramTypePmtInfoAggregate.TrnAmt);
    if (!FFSUtil.isPositive(str)) {
      throw new OFXException("Invalid amount", 2012);
    }
    this.jW.setAmt(str);
    this.jW.PayAcct = paramTypePmtInfoAggregate.PayAcct;
    this.jW.CurDef = BPWUtil.validateCurrencyEnum(paramEnumCurrencyEnum);
    this.jW.BankID = paramTypePmtInfoAggregate.BankAcctFrom.BankID;
    this.jW.AcctDebitID = paramTypePmtInfoAggregate.BankAcctFrom.AcctID;
    this.jW.AcctDebitType = MsgBuilder.getAcctType(paramTypePmtInfoAggregate.BankAcctFrom.AcctType);
    this.jW.DateCreate = DBUtil.getCurrentLogDate();
    if (paramTypePmtInfoAggregate.MemoExists) {
      this.jW.Memo = paramTypePmtInfoAggregate.Memo;
    }
  }
  
  public static RecPmtInstruction getRecPmtInstr(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    RecPmtInstruction localRecPmtInstruction = new RecPmtInstruction(paramString, paramFFSConnectionHolder);
    if (localRecPmtInstruction.jW.RecSrvrTID == null) {
      return null;
    }
    return localRecPmtInstruction;
  }
  
  public static RecPmtInfo getRecPmtInfo(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    RecPmtInstruction localRecPmtInstruction = new RecPmtInstruction(paramString, paramFFSConnectionHolder);
    if (localRecPmtInstruction.jW.RecSrvrTID == null) {
      return null;
    }
    return localRecPmtInstruction.getRecPmtInfo();
  }
  
  public static RecPmtInfo[] getRecPmtById(String[] paramArrayOfString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    RecPmtInfo localRecPmtInfo = null;
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      localRecPmtInfo = new RecPmtInfo();
      localRecPmtInfo.statusCode = 16504;
      localRecPmtInfo.statusMsg = "RecSrvr ID Array  is null or empty";
      FFSDebug.log("RecPmtInstruction.getRecPmtById (multiple): failed  null or empty srvr TId array passed", 0);
      RecPmtInfo[] arrayOfRecPmtInfo = { localRecPmtInfo };
      return arrayOfRecPmtInfo;
    }
    int i = paramArrayOfString.length;
    ArrayList localArrayList = new ArrayList();
    for (int j = 0; j < i; j++)
    {
      localRecPmtInfo = getRecPmtInfo(paramArrayOfString[j], paramFFSConnectionHolder);
      if (localRecPmtInfo == null)
      {
        localRecPmtInfo = new RecPmtInfo();
        localRecPmtInfo.statusCode = 17020;
        localRecPmtInfo.statusMsg = "No Records Found";
      }
      else
      {
        localRecPmtInfo.statusCode = 0;
        localRecPmtInfo.statusMsg = "Success";
      }
      localArrayList.add(localRecPmtInfo);
      try
      {
        paramFFSConnectionHolder.conn.commit();
      }
      catch (Exception localException)
      {
        throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
      }
    }
    return (RecPmtInfo[])localArrayList.toArray(new RecPmtInfo[0]);
  }
  
  public boolean storeToDB(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("RecPmtInstruction.storeToDB start, recSrvrTID=" + this.jW.RecSrvrTID, 6);
    String str = "INSERT INTO BPW_RecPmtInstruction(RecSrvrTID, CustomerID, FIID, PayeeID,PayeeListID,BankID, AcctDebitID, AcctDebitType,PayAcct,DateCreate, CurDef, Amount,InitialAmt, FinalAmt,StartDate,EndDate, InstanceCount, Frequency, LogID,Status, Memo, LastModified, SubmittedBy, PaymentType ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { this.jW.RecSrvrTID, this.jW.CustomerID, this.jW.FIID, this.jW.PayeeID, new Integer(this.jW.PayeeListID), this.jW.BankID, this.jW.AcctDebitID, this.jW.AcctDebitType, this.jW.PayAcct, this.jW.DateCreate, this.jW.CurDef, FFSUtil.formatToScale(this.jW.getAmt()), FFSUtil.formatToScale(this.jW.getInitialAmount()), FFSUtil.formatToScale(this.jW.getFinalAmount()), new Integer(this.jW.StartDate), new Integer(this.jW.EndDate), new Integer(this.jW.InstanceCount), new Integer(this.jW.getRecFrequencyValue()), this.jW.LogID, this.jW.Status, this.jW.Memo, FFSUtil.getDateString("yyyyMMddHHmmss"), this.jW.submittedBy, this.jW.getPaymentType() };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** RecPmtInstruction.storeToDB failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("RecPmtInstruction.storeToDB done, recSrvrTID=" + this.jW.RecSrvrTID, 6);
    return true;
  }
  
  public void removeFromDB(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("RecPmtInstruction.removeFromDB start, recSrvrTID=" + this.jW.RecSrvrTID, 6);
    String str = "DELETE FROM BPW_RecPmtInstruction WHERE RecSrvrTID=?";
    Object[] arrayOfObject = { this.jW.RecSrvrTID };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** RecPmtInstruction.removeFromDB failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("RecPmtInstruction.removeFromDB done, recSrvrTID=" + this.jW.RecSrvrTID, 6);
  }
  
  public boolean updateRecPmt(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("RecPmtInstruction.updateRecPmt start, recSrvrTID=" + this.jW.RecSrvrTID, 6);
    return updateRecPayment(this.jW, paramFFSConnectionHolder);
  }
  
  public static void updateStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws BPWException
  {
    FFSDebug.log("RecPmtInstruction.updateStatus start, recSrvrTID=" + paramString1, 6);
    try
    {
      String str = "UPDATE BPW_RecPmtInstruction SET Status=? WHERE RecSrvrTID=?";
      localObject = new Object[] { paramString2, paramString1 };
      DBUtil.executeStatement(paramFFSConnectionHolder, str, (Object[])localObject);
    }
    catch (Exception localException)
    {
      Object localObject = "*** RecPmtInstruction.updateStatus failed: " + localException.toString();
      FFSDebug.log((String)localObject);
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("RecPmtInstruction.updateStatus done, recSrvrTID=" + paramString1, 6);
  }
  
  public static String getStatus(String paramString)
    throws BPWException
  {
    return "ACTIVE";
  }
  
  public static void delete(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    FFSDebug.log("RecPmtInstruction.delete start, recSrvrTID=" + paramString, 6);
    String str1 = "DELETE FROM BPW_RecPmtInstruction WHERE RecSrvrTID=?";
    Object[] arrayOfObject = { paramString };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "****RecPmtInstr.delete failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("RecPmtInstruction.delete done, recSrvrTID=" + paramString, 6);
  }
  
  private static FFSResultSet jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, int paramInt)
    throws FFSException
  {
    FFSDebug.log("RecPmtInstruction.getOldRecSrvrTID: start ageDay=" + paramInt, 6);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd00");
    String str1 = localSimpleDateFormat.format(localCalendar.getTime());
    int i = Integer.parseInt(str1);
    String str2 = "SELECT RecSrvrTID FROM BPW_RecPmtInstruction WHERE EndDate <= ? AND Status IN (?,?) AND PaymentName IS NULL";
    Object[] arrayOfObject = { new Integer(i), "POSTEDON", "CANCELEDON" };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str3 = "*** RecPmtInstruction.getOldRecSrvrTID failed:";
      System.out.println(str3 + localException.toString());
      throw new FFSException(localException.toString());
    }
    return localFFSResultSet;
  }
  
  private static FFSResultSet jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, int paramInt)
    throws FFSException
  {
    FFSDebug.log("RecPmtInstruction.getOldRecTemplateSrvrTID: start ageDay=" + paramInt, 6);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String str1 = localSimpleDateFormat.format(localCalendar.getTime());
    String str2 = "SELECT RecSrvrTID FROM BPW_RecPmtInstruction WHERE LastModified <= ? AND Status IN (?) AND PaymentType = ?";
    Object[] arrayOfObject = { str1, "CANCELEDON", "RECTEMPLATE" };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException)
    {
      throw new FFSException(localException.toString());
    }
    return localFFSResultSet;
  }
  
  private static FFSResultSet a(FFSConnectionHolder paramFFSConnectionHolder, int paramInt, String paramString)
    throws FFSException
  {
    String str1 = "RecPmtInstruction.getOldRecSrvrTIDByCustId: ";
    FFSDebug.log(str1, "start ageDay=" + paramInt, " custId=", paramString, 6);
    String str2 = "SELECT RecSrvrTID FROM BPW_RecPmtInstruction WHERE EndDate <= ? AND CustomerID = ? AND Status IN (?,?) AND PaymentName IS NULL";
    Object[] arrayOfObject = { new Integer(paramInt), paramString, "POSTEDON", "CANCELEDON" };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + "failed:";
      System.out.println(str3 + localException.toString());
      throw new FFSException(localException.toString());
    }
    return localFFSResultSet;
  }
  
  private static FFSResultSet jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, int paramInt, String paramString)
    throws FFSException
  {
    String str1 = "RecPmtInstruction.getOldRecSrvrTemplateTIDByCustId: ";
    FFSDebug.log(str1, "start ageDay=" + paramInt, " custId=", paramString, 6);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String str2 = localSimpleDateFormat.format(localCalendar.getTime());
    String str3 = "SELECT RecSrvrTID FROM BPW_RecPmtInstruction WHERE LastModified <= ? AND CustomerID = ? AND Status IN (?) AND PaymentType = ?";
    Object[] arrayOfObject = { str2, paramString, "CANCELEDON", "RECTEMPLATE" };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
    }
    catch (Exception localException)
    {
      throw new FFSException(localException.toString());
    }
    return localFFSResultSet;
  }
  
  public static RecPmtInfo[] getHist(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, String paramString4, long paramLong)
    throws Exception
  {
    FFSDebug.log("RecPmtInstruction.getHist start, custID=" + paramString1 + ",acctID=" + paramString2 + ",startDate=" + paramInt1 + ",endDate=" + paramInt2, 6);
    RecPmtInfo localRecPmtInfo = null;
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    long l = 0L;
    String str = "SELECT RecSrvrTID, Frequency, StartDate, EndDate, InstanceCount, Status, Amount, FinalAmt, FIID FROM BPW_RecPmtInstruction WHERE CustomerID = ? AND StartDate <= ? AND (EndDate >= ? OR EndDate = -1) ";
    if ((paramString2 != null) && (paramString2.trim().length() > 0)) {
      str = str + "AND AcctDebitID = '" + paramString2 + "' ";
    }
    str = str + " AND Status = 'WILLPROCESSON' ";
    boolean bool = true;
    if ((paramString3 != null) && (paramString3.length() > 0)) {
      bool = DBUtil.checkStatusQuery(paramString3, "WILLPROCESSON");
    }
    if (bool) {
      try
      {
        Object[] arrayOfObject = { paramString1, new Integer(paramInt2), new Integer(paramInt1) };
        if (jV.get(paramString4) == null)
        {
          localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
          jV.put(paramString4, localFFSResultSet);
        }
        else
        {
          localFFSResultSet = (FFSResultSet)jV.get(paramString4);
        }
        while (localFFSResultSet.getNextRow())
        {
          localRecPmtInfo = new RecPmtInfo();
          localRecPmtInfo.RecSrvrTID = localFFSResultSet.getColumnString("RecSrvrTID");
          localRecPmtInfo.setRecFrequency(localFFSResultSet.getColumnInt("Frequency"));
          localRecPmtInfo.StartDate = localFFSResultSet.getColumnInt("StartDate");
          localRecPmtInfo.EndDate = localFFSResultSet.getColumnInt("EndDate");
          localRecPmtInfo.InstanceCount = localFFSResultSet.getColumnInt("InstanceCount");
          localRecPmtInfo.Status = localFFSResultSet.getColumnString("Status");
          localRecPmtInfo.setAmt(localFFSResultSet.getColumnString("Amount"));
          localRecPmtInfo.setFinalAmount(localFFSResultSet.getColumnString("FinalAmt"));
          localRecPmtInfo.FIID = localFFSResultSet.getColumnString("FIID");
          localArrayList.add(localRecPmtInfo);
          l += 1L;
          if (l == paramLong)
          {
            RecPmtInfo[] arrayOfRecPmtInfo = (RecPmtInfo[])localArrayList.toArray(new RecPmtInfo[0]);
            return arrayOfRecPmtInfo;
          }
        }
        localFFSResultSet.close();
        jV.remove(paramString4);
      }
      catch (Exception localException)
      {
        localException = localException;
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        if (jV != null) {
          jV.remove(paramString4);
        }
        throw new Exception(localException.toString() + FFSDebug.stackTrace(localException));
      }
      finally {}
    }
    FFSDebug.log("RecPmtInstruction.getHist done, rows=" + l, 6);
    return (RecPmtInfo[])localArrayList.toArray(new RecPmtInfo[0]);
  }
  
  public static boolean isBatchDone(String paramString)
  {
    return !jV.containsKey(paramString);
  }
  
  public static void clearBatch(String paramString)
  {
    try
    {
      if (jV.containsKey(paramString))
      {
        FFSResultSet localFFSResultSet = (FFSResultSet)jV.get(paramString);
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
        jV.remove(paramString);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** PmtInstruction.clearBatch failed:" + localException.toString());
    }
  }
  
  private static String jdMethod_long(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws Exception
  {
    FFSDebug.log("RecPmtInstruction.getExtdPmtInfo start, fIId =" + paramString1 + ", recSrvrTID =" + paramString2, 6);
    Hashtable localHashtable = BPWExtraInfo.getXtraInfo(paramFFSConnectionHolder, paramString1, paramString2, "IFXRECPMT");
    String str = FFSUtil.hashtableToString(localHashtable);
    FFSDebug.log("RecPmtInstruction.getExtdPmtInfo end , extdPmtInfo =" + str, 6);
    return str;
  }
  
  public static int cleanup(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws FFSException
  {
    String str1 = "RecPmtInstruction.cleanup ";
    int i = 0;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd00");
    String str2 = localSimpleDateFormat.format(localCalendar.getTime());
    int j = Integer.parseInt(str2);
    FFSResultSet localFFSResultSet = null;
    Object localObject1 = null;
    int k = 1;
    int m = Purge.getPageSize();
    ArrayList localArrayList = new ArrayList();
    String str3;
    String str4;
    try
    {
      while (k <= 2)
      {
        if ((paramString != null) && (paramString.trim().length() != 0)) {
          localFFSResultSet = a(paramFFSConnectionHolder, j, paramString);
        } else {
          localFFSResultSet = jdMethod_if(paramFFSConnectionHolder, paramInt);
        }
        try
        {
          for (;;)
          {
            if (localFFSResultSet.getNextRow()) {
              try
              {
                localArrayList.add(localFFSResultSet.getColumnString(1));
                if (localArrayList.size() >= m)
                {
                  i += jdMethod_for(paramFFSConnectionHolder, localArrayList);
                  localArrayList.clear();
                }
              }
              catch (Exception localException1)
              {
                localObject1 = localException1;
              }
            }
          }
          try
          {
            if (localArrayList.size() > 0) {
              jdMethod_for(paramFFSConnectionHolder, localArrayList);
            }
          }
          catch (Exception localException2)
          {
            localObject1 = localException2;
          }
        }
        catch (Exception localException3)
        {
          FFSDebug.log(str1 + " SQLException: " + localException3.getMessage());
          FFSDebug.log(str1 + " Attempting retry # " + ++k);
        }
      }
      FFSDebug.log("----> Done deleting rec payments", 3);
      if (localObject1 != null) {
        throw localObject1;
      }
    }
    catch (FFSException localFFSException1)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = FFSDebug.stackTrace(localFFSException1);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException1;
    }
    catch (Exception localException4)
    {
      str3 = "***  " + str1 + ": failed:";
      str4 = FFSDebug.stackTrace(localException4);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException4, str3);
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
      catch (Exception localException9) {}
    }
    try
    {
      while (k <= 2)
      {
        if ((paramString != null) && (paramString.trim().length() != 0)) {
          localFFSResultSet = jdMethod_if(paramFFSConnectionHolder, paramInt, paramString);
        } else {
          localFFSResultSet = jdMethod_do(paramFFSConnectionHolder, paramInt);
        }
        try
        {
          for (;;)
          {
            if (localFFSResultSet.getNextRow()) {
              try
              {
                localArrayList.add(localFFSResultSet.getColumnString(1));
                if (localArrayList.size() >= m)
                {
                  i += deleteBatch(paramFFSConnectionHolder, (String[])localArrayList.toArray(new String[0]));
                  localArrayList.clear();
                }
              }
              catch (Exception localException5)
              {
                localObject1 = localException5;
              }
            }
          }
          try
          {
            if (localArrayList.size() > 0) {
              i += deleteBatch(paramFFSConnectionHolder, (String[])localArrayList.toArray(new String[0]));
            }
          }
          catch (Exception localException6)
          {
            localObject1 = localException6;
          }
        }
        catch (Exception localException7)
        {
          FFSDebug.log(str1 + " SQLException: " + localException7.getMessage());
          FFSDebug.log(str1 + " Attempting retry # " + ++k);
        }
      }
      FFSDebug.log("----> Done deleting rec payments", 3);
      if (localObject1 != null) {
        throw localObject1;
      }
    }
    catch (FFSException localFFSException2)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = FFSDebug.stackTrace(localFFSException2);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException2;
    }
    catch (Exception localException8)
    {
      str3 = "***  " + str1 + ": failed:";
      str4 = FFSDebug.stackTrace(localException8);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException8, str3);
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
      catch (Exception localException10) {}
    }
    FFSDebug.log(str1 + "end, No of rows deleted = " + i, 6);
    return i;
  }
  
  private static int jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, ArrayList paramArrayList)
    throws FFSException
  {
    String[] arrayOfString = (String[])paramArrayList.toArray(new String[0]);
    EventInfoLog.deleteBatchByInstructionIdsAndType(paramFFSConnectionHolder, arrayOfString, "PMTTRN");
    BPWExtraInfo.deleteBatch(paramFFSConnectionHolder, arrayOfString, "IFXRECPMT");
    deleteBatch(paramFFSConnectionHolder, arrayOfString);
    RecSrvrTIDToSrvrTID.deleteBatchByRecSrvrTID(paramFFSConnectionHolder, arrayOfString);
    RecSrvrTrans.deleteBatch(paramFFSConnectionHolder, arrayOfString);
    paramFFSConnectionHolder.conn.commit();
    FFSDebug.log("----> Committed RecPayment Cleanup.  Removed " + arrayOfString.length + " payments.", 3);
    return arrayOfString.length;
  }
  
  public static int deleteBatch(FFSConnectionHolder paramFFSConnectionHolder, String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("RecPmtInstruction.deleteBatch start.", 6);
    String str1 = "DELETE FROM BPW_RecPmtInstruction WHERE RecSrvrTID=?";
    try
    {
      DBUtil.executeStatementBatch(paramFFSConnectionHolder, str1, DBUtil.arrayStringToArrayList(paramArrayOfString));
    }
    catch (Exception localException)
    {
      String str2 = "*** RecPmtInstruction.deleteBatch failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("RecPmtInstruction.deleteBatch done. # of recurring payments deleted: " + paramArrayOfString.length, 6);
    return paramArrayOfString.length;
  }
  
  public static PmtInfo insertRecPayment(RecPmtInfo paramRecPmtInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    paramRecPmtInfo.OriginatedDate = DBUtil.getCurrentLogDate();
    paramRecPmtInfo.RecSrvrTID = DBUtil.getNextIndexString("RecSrvrTID");
    paramRecPmtInfo.Status = "ACTIVE";
    String str = "INSERT INTO BPW_RecPmtInstruction(RecSrvrTID, CustomerID, PayeeID, PayeeListID, AcctDebitID, AcctDebitType, Amount, CurDef, BankID, DateCreate, StartDate, Frequency, InstanceCount, EndDate, Memo, PaymentName, SubmittedBy, LogID, FIID, Status, PaymentType, BatchID ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramRecPmtInfo.RecSrvrTID, paramRecPmtInfo.CustomerID, paramRecPmtInfo.PayeeID, new Integer(paramRecPmtInfo.PayeeListID), paramRecPmtInfo.AcctDebitID, paramRecPmtInfo.AcctDebitType, FFSUtil.formatToScale(paramRecPmtInfo.getAmt()), paramRecPmtInfo.CurDef, paramRecPmtInfo.BankID, paramRecPmtInfo.OriginatedDate, new Integer(paramRecPmtInfo.StartDate), new Integer(paramRecPmtInfo.getRecFrequencyValue()), new Integer(paramRecPmtInfo.InstanceCount), new Integer(paramRecPmtInfo.EndDate), paramRecPmtInfo.Memo, paramRecPmtInfo.paymentName, paramRecPmtInfo.submittedBy, paramRecPmtInfo.LogID, paramRecPmtInfo.FIID, paramRecPmtInfo.Status, paramRecPmtInfo.PaymentType, paramRecPmtInfo.batchID };
    DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    return paramRecPmtInfo;
  }
  
  public static boolean updateRecPayment(RecPmtInfo paramRecPmtInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    paramRecPmtInfo.DateCreate = DBUtil.getCurrentLogDate();
    try
    {
      String str = "UPDATE BPW_RecPmtInstruction SET CustomerID=?,PayeeID=?,PayeeListID=?,BankID=?, AcctDebitID=?,AcctDebitType=?,PayAcct=?,DateCreate=?, CurDef=?, Amount=?,InitialAmt=?,FinalAmt=?, InstanceCount=?,Frequency=?, StartDate=?, EndDate=?, LogID=?, Status=?, Memo=?, SubmittedBy=?, LastModified=?, PaymentName=? WHERE RecSrvrTID=?";
      Object[] arrayOfObject = { paramRecPmtInfo.CustomerID, paramRecPmtInfo.PayeeID, new Integer(paramRecPmtInfo.PayeeListID), paramRecPmtInfo.BankID, paramRecPmtInfo.AcctDebitID, paramRecPmtInfo.AcctDebitType, paramRecPmtInfo.PayAcct, paramRecPmtInfo.DateCreate, paramRecPmtInfo.CurDef, FFSUtil.formatToScale(paramRecPmtInfo.getAmt()), FFSUtil.formatToScale(paramRecPmtInfo.getInitialAmount()), FFSUtil.formatToScale(paramRecPmtInfo.getFinalAmount()), new Integer(paramRecPmtInfo.InstanceCount), new Integer(paramRecPmtInfo.getRecFrequencyValue()), new Integer(paramRecPmtInfo.StartDate), new Integer(paramRecPmtInfo.EndDate), paramRecPmtInfo.LogID, paramRecPmtInfo.Status, paramRecPmtInfo.Memo, paramRecPmtInfo.submittedBy, FFSUtil.getDateString("yyyyMMddHHmmss"), paramRecPmtInfo.paymentName, paramRecPmtInfo.RecSrvrTID };
      DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** RecPmtInstruction.updateRecPmt failed: " + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("RecPmtInstruction.updateRecPmt done, recSrvrTID=" + paramRecPmtInfo.RecSrvrTID, 6);
    return true;
  }
  
  public static RecPmtInfo getRecPayment(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    RecPmtInfo localRecPmtInfo = null;
    Object[] arrayOfObject = { paramString };
    String str = "SELECT CustomerID,PayeeID, PayeeListID, AcctDebitID, AcctDebitType, Amount, CurDef, BankID, DateCreate, StartDate, Memo, PaymentName, SubmittedBy, LogID, FIID, Frequency, InstanceCount, EndDate FROM BPW_RecPmtInstruction WHERE RecSrvrTID=?";
    FFSResultSet localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
    if (localFFSResultSet.getNextRow())
    {
      localRecPmtInfo.RecSrvrTID = paramString;
      localRecPmtInfo.CustomerID = localFFSResultSet.getColumnString("CustomerID");
      localRecPmtInfo.PayeeID = localFFSResultSet.getColumnString("PayeeID");
      localRecPmtInfo.PayeeListID = localFFSResultSet.getColumnInt("PayeeListID");
      localRecPmtInfo.AcctDebitID = localFFSResultSet.getColumnString("AcctDebitID");
      localRecPmtInfo.AcctDebitType = localFFSResultSet.getColumnString("AcctDebitType");
      localRecPmtInfo.setAmt(localFFSResultSet.getColumnString("Amount"));
      localRecPmtInfo.CurDef = localFFSResultSet.getColumnString("CurDef");
      localRecPmtInfo.BankID = localFFSResultSet.getColumnString("BankID");
      localRecPmtInfo.OriginatedDate = localFFSResultSet.getColumnString("DateCreate");
      localRecPmtInfo.StartDate = localFFSResultSet.getColumnInt("StartDate");
      localRecPmtInfo.Memo = localFFSResultSet.getColumnString("Memo");
      localRecPmtInfo.paymentName = localFFSResultSet.getColumnString("PaymentName");
      localRecPmtInfo.submittedBy = localFFSResultSet.getColumnString("SubmittedBy");
      localRecPmtInfo.LogID = localFFSResultSet.getColumnString("LogID");
      localRecPmtInfo.FIID = localFFSResultSet.getColumnString("FIID");
      localRecPmtInfo.setRecFrequency(localFFSResultSet.getColumnInt("Frequency"));
      localRecPmtInfo.InstanceCount = localFFSResultSet.getColumnInt("InstanceCount");
      localRecPmtInfo.EndDate = localFFSResultSet.getColumnInt("EndDate");
    }
    return localRecPmtInfo;
  }
  
  public static void updateStatusLastModified(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws BPWException
  {
    FFSDebug.log("RecPmtInstruction.updateStatusLastModified start, recSrvrTID=" + paramString1, 6);
    try
    {
      String str = "UPDATE BPW_RecPmtInstruction SET Status=?, LastModified=? WHERE RecSrvrTID=?";
      localObject = new Object[] { paramString2, paramString3, paramString1 };
      DBUtil.executeStatement(paramFFSConnectionHolder, str, (Object[])localObject);
    }
    catch (Exception localException)
    {
      Object localObject = "*** RecPmtInstruction.updateStatus failed: " + localException.toString();
      FFSDebug.log((String)localObject);
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("RecPmtInstruction.updateStatusLastModified done, recSrvrTID=" + paramString1, 6);
  }
  
  public static void updateStatusLastModifiedByBatchId(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws BPWException
  {
    FFSDebug.log("RecPmtInstruction.updateStatusLastModifiedByBatchId start, batchId=" + paramString1, 6);
    try
    {
      String str = "UPDATE BPW_RecPmtInstruction SET Status=?, LastModified=? WHERE BatchID=?";
      localObject = new Object[] { paramString2, FFSUtil.getDateString("yyyyMMddHHmmss"), paramString1 };
      DBUtil.executeStatement(paramFFSConnectionHolder, str, (Object[])localObject);
    }
    catch (Exception localException)
    {
      Object localObject = "*** RecPmtInstruction.updateStatus failed: " + localException.toString();
      FFSDebug.log((String)localObject);
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("RecPmtInstruction.updateStatusLastModifiedByBatchId done, batchId=" + paramString1, 6);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.RecPmtInstruction
 * JD-Core Version:    0.7.0.1
 */