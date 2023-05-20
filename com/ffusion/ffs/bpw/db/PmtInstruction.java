package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWExtdHist;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInvoice;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.scheduling.db.EventInfoLog;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

public class PmtInstruction
  implements FFSConst, DBConsts
{
  private PmtInfo j1;
  private static PropertyConfig jY = null;
  private static Hashtable j2 = new Hashtable();
  private static Hashtable jZ = new Hashtable();
  private static final String j0 = " ORDER BY CAST(SrvrTID AS INTEGER) ASC";
  private static final String j3 = " ORDER BY CAST(SrvrTID AS INTEGER) DESC";
  private static String jX = " AND SrvrTID in (Select RecordId from BPW_ExtraInfo  where ( RecordType in ('IFXPMT','IFXRECPMT')  AND ";
  
  public PmtInstruction()
  {
    this.j1 = new PmtInfo();
    jY = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
  }
  
  public PmtInstruction(PmtInfo paramPmtInfo)
  {
    this();
    setPmtInfo(paramPmtInfo);
  }
  
  public PmtInstruction(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.constructor start, srvrTID=" + paramString, 6);
    this.j1 = new PmtInfo();
    this.j1.SrvrTID = null;
    jY = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String[] arrayOfString = new String[2];
    String str1 = "SELECT CustomerID,PayeeID,PayeeListID, BankID, AcctDebitID,AcctDebitType, PayAcct,DateCreate, CurDef, Amount, RouteID, StartDate, Status, LogID, Memo, PaymentType, RecSrvrTID, FIID, LastModified, SubmittedBy, BatchID, PaymentName, TemplateID, ImmediateFundAllocation, ImmediateProcessing FROM BPW_PmtInstruction WHERE SrvrTID=?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        this.j1.SrvrTID = paramString;
        this.j1.CustomerID = localFFSResultSet.getColumnString("CustomerID");
        this.j1.PayeeID = localFFSResultSet.getColumnString("PayeeID");
        this.j1.PayeeListID = localFFSResultSet.getColumnInt("PayeeListID");
        this.j1.BankID = localFFSResultSet.getColumnString("BankID");
        this.j1.AcctDebitID = localFFSResultSet.getColumnString("AcctDebitID");
        this.j1.AcctDebitType = localFFSResultSet.getColumnString("AcctDebitType");
        this.j1.PayAcct = localFFSResultSet.getColumnString("PayAcct");
        this.j1.OriginatedDate = localFFSResultSet.getColumnString("DateCreate");
        this.j1.CurDef = localFFSResultSet.getColumnString("CurDef");
        this.j1.setAmt(localFFSResultSet.getColumnString("Amount"));
        this.j1.setRouteId(localFFSResultSet.getColumnInt("RouteID"));
        this.j1.StartDate = localFFSResultSet.getColumnInt("StartDate");
        this.j1.Status = localFFSResultSet.getColumnString("Status");
        this.j1.LogID = localFFSResultSet.getColumnString("LogID");
        this.j1.Memo = localFFSResultSet.getColumnString("Memo");
        this.j1.PaymentType = localFFSResultSet.getColumnString("PaymentType");
        this.j1.RecSrvrTID = localFFSResultSet.getColumnString("RecSrvrTID");
        this.j1.FIID = localFFSResultSet.getColumnString("FIID");
        this.j1.lastModified = localFFSResultSet.getColumnString("LastModified");
        this.j1.submittedBy = localFFSResultSet.getColumnString("SubmittedBy");
        this.j1.batchID = localFFSResultSet.getColumnString("BatchID");
        this.j1.paymentName = localFFSResultSet.getColumnString("PaymentName");
        this.j1.templateID = localFFSResultSet.getColumnString("TemplateID");
        this.j1.setImmediateFundAllocation(ag(localFFSResultSet.getColumnString("ImmediateFundAllocation")));
        this.j1.setImmediateProcessing(ag(localFFSResultSet.getColumnString("ImmediateProcessing")));
        this.j1.ExtdPmtInfo = jdMethod_null(paramFFSConnectionHolder, this.j1.FIID, paramString);
        HashMap localHashMap = jdMethod_try(this.j1.SrvrTID, paramFFSConnectionHolder);
        if ((localHashMap != null) && (localHashMap.size() != 0))
        {
          String str2 = (String)localHashMap.get("INVOICE");
          if (str2 != null)
          {
            PmtInvoice localPmtInvoice = new PmtInvoice();
            localPmtInvoice.parse(str2);
            localHashMap.remove("INVOICE");
            localHashMap.put("INVOICE", localPmtInvoice);
          }
          this.j1.extraFields = localHashMap;
        }
        arrayOfString = SrvrTrans.findTransDtBySrvrTID(paramString, paramFFSConnectionHolder);
        this.j1.AdjustedDueDt = arrayOfString[0];
        this.j1.StatusDt = arrayOfString[1];
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** PmtInstruction( SrvrTID " + paramString + " ) failed:" + localException1.toString());
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
        FFSDebug.log("*** PmtInstruction( srvrTID ) failed:" + localException2.toString());
      }
    }
    FFSDebug.log("PmtInstruction.constructor done, srvrTID=" + paramString, 6);
  }
  
  public void setPmtInfo(PmtInfo paramPmtInfo)
  {
    this.j1 = paramPmtInfo;
  }
  
  public PmtInfo getPmtInfo()
  {
    return this.j1;
  }
  
  public void setCustomerID(String paramString)
  {
    this.j1.CustomerID = paramString;
  }
  
  public String getCustomerID()
  {
    return this.j1.CustomerID;
  }
  
  public void setPayeeID(String paramString)
  {
    this.j1.PayeeID = paramString;
  }
  
  public void setBankID(String paramString)
  {
    this.j1.BankID = paramString;
  }
  
  public String getBankID()
  {
    return this.j1.BankID;
  }
  
  public String getPayeeID()
  {
    return this.j1.PayeeID;
  }
  
  public void setPayeeListID(int paramInt)
  {
    this.j1.PayeeListID = paramInt;
  }
  
  public int getPayeeListID()
  {
    return this.j1.PayeeListID;
  }
  
  public String getPayAcct()
  {
    return this.j1.PayAcct;
  }
  
  public String getAcctDebitID()
  {
    return this.j1.AcctDebitID;
  }
  
  public String getAcctDebitType()
  {
    return this.j1.AcctDebitType;
  }
  
  public void setSrvrTID()
    throws BPWException
  {
    this.j1.SrvrTID = DBUtil.getNextIndexString("SrvrTID");
  }
  
  public void setSrvrTID(String paramString)
  {
    this.j1.SrvrTID = paramString;
  }
  
  public String getSrvrTID()
  {
    return this.j1.SrvrTID;
  }
  
  public void setRouteID(int paramInt)
  {
    this.j1.setRouteId(paramInt);
  }
  
  public int getRouteID()
  {
    return this.j1.getRouteId();
  }
  
  public void setStartDate(int paramInt)
  {
    this.j1.StartDate = paramInt;
  }
  
  public int getStartDate()
  {
    return this.j1.StartDate;
  }
  
  public void setStatus(String paramString)
  {
    this.j1.Status = paramString;
  }
  
  public String getStatus()
  {
    return this.j1.Status;
  }
  
  public String getAmt()
  {
    return this.j1.getAmt();
  }
  
  public void setAmt(String paramString)
  {
    this.j1.setAmt(paramString);
  }
  
  public static String getStatus(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.getStatus start, srvrTID=" + paramString, 6);
    String str1 = null;
    String str2 = "SELECT CustomerID,PayeeID,PayeeListID, BankID, AcctDebitID,AcctDebitType, PayAcct,DateCreate, CurDef, Amount, RouteID, StartDate, Status, LogID, Memo, PaymentType, RecSrvrTID, FIID, LastModified, SubmittedBy, BatchID, PaymentName, TemplateID, ImmediateFundAllocation, ImmediateProcessing FROM BPW_PmtInstruction WHERE SrvrTID=?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        str1 = localFFSResultSet.getColumnString(13);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** PmtInstruction getStatus failed:" + localException1.toString());
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
        FFSDebug.log("*** PmtInstruction getStatus failed:" + localException2.toString());
      }
    }
    FFSDebug.log("PmtInstruction.getStatus done, srvrTID=" + paramString, 6);
    return str1;
  }
  
  public void mapFromPmtInfoV1(TypePmtInfoV1Aggregate paramTypePmtInfoV1Aggregate, String paramString1, String paramString2, String paramString3, TypeUserData paramTypeUserData, com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException, OFXException
  {
    String str = String.valueOf(paramTypePmtInfoV1Aggregate.TrnAmt);
    if (!FFSUtil.isPositive(str)) {
      throw new OFXException("Invalid amount", 2012);
    }
    this.j1.setAmt(str);
    this.j1.PayAcct = paramTypePmtInfoV1Aggregate.PayAcct;
    this.j1.CurDef = BPWUtil.validateCurrencyEnum(paramEnumCurrencyEnum);
    this.j1.BankID = paramTypePmtInfoV1Aggregate.BankAcctFrom.BankID;
    this.j1.AcctDebitID = paramTypePmtInfoV1Aggregate.BankAcctFrom.AcctID;
    this.j1.AcctDebitType = MsgBuilder.getAcctType(paramTypePmtInfoV1Aggregate.BankAcctFrom.AcctType);
    this.j1.OriginatedDate = DBUtil.getCurrentLogDate();
    if (paramTypePmtInfoV1Aggregate.MemoExists) {
      this.j1.Memo = paramTypePmtInfoV1Aggregate.Memo;
    }
    this.j1.PaymentType = paramString1;
    this.j1.RecSrvrTID = paramString2;
    this.j1.FIID = paramString3;
    this.j1.submittedBy = paramTypeUserData._submittedBy;
  }
  
  public void mapFromPmtInfoV1(TypePmtInfoAggregate paramTypePmtInfoAggregate, String paramString1, String paramString2, String paramString3, TypeUserData paramTypeUserData, com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException, OFXException
  {
    String str = String.valueOf(paramTypePmtInfoAggregate.TrnAmt);
    if (!FFSUtil.isPositive(str)) {
      throw new OFXException("Invalid amount", 2012);
    }
    this.j1.setAmt(str);
    this.j1.PayAcct = paramTypePmtInfoAggregate.PayAcct;
    this.j1.CurDef = BPWUtil.validateCurrencyEnum(paramEnumCurrencyEnum);
    this.j1.BankID = paramTypePmtInfoAggregate.BankAcctFrom.BankID;
    this.j1.AcctDebitID = paramTypePmtInfoAggregate.BankAcctFrom.AcctID;
    this.j1.AcctDebitType = MsgBuilder.getAcctType(paramTypePmtInfoAggregate.BankAcctFrom.AcctType);
    this.j1.OriginatedDate = DBUtil.getCurrentLogDate();
    if (paramTypePmtInfoAggregate.MemoExists) {
      this.j1.Memo = paramTypePmtInfoAggregate.Memo;
    }
    this.j1.PaymentType = paramString1;
    this.j1.RecSrvrTID = paramString2;
    this.j1.FIID = paramString3;
    this.j1.submittedBy = paramTypeUserData._submittedBy;
  }
  
  public static PmtInstruction getPmtInstr(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    PmtInstruction localPmtInstruction = new PmtInstruction(paramString, paramFFSConnectionHolder);
    if (localPmtInstruction.j1.SrvrTID == null) {
      return null;
    }
    return localPmtInstruction;
  }
  
  public static PmtInfo getPmtInfo(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    PmtInstruction localPmtInstruction = new PmtInstruction(paramString, paramFFSConnectionHolder);
    if (localPmtInstruction.j1.SrvrTID == null) {
      return null;
    }
    return localPmtInstruction.getPmtInfo();
  }
  
  public boolean storeToDB(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.storeToDB start, srvrTID=" + this.j1.SrvrTID, 6);
    String str1 = a(this.j1.getImmediateFundAllocation());
    String str2 = a(this.j1.getImmediateProcessing());
    String str3 = "INSERT INTO BPW_PmtInstruction(SrvrTID, CustomerID, PayeeID, PayeeListID, BankID,AcctDebitID,AcctDebitType, PayAcct,DateCreate, CurDef, Amount,RouteID, StartDate, Status,LogID, Memo, PaymentType, RecSrvrTID, FIID, SubmittedBy, ImmediateFundAllocation, ImmediateProcessing) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { this.j1.SrvrTID, this.j1.CustomerID, this.j1.PayeeID, new Integer(this.j1.PayeeListID), this.j1.BankID, this.j1.AcctDebitID, this.j1.AcctDebitType, this.j1.PayAcct, this.j1.OriginatedDate, this.j1.CurDef, FFSUtil.formatToScale(this.j1.getAmt()), new Integer(this.j1.getRouteId()), new Integer(this.j1.StartDate), this.j1.Status, this.j1.LogID, this.j1.Memo, this.j1.PaymentType, this.j1.RecSrvrTID, this.j1.FIID, this.j1.submittedBy, str1, str2 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
      if ((jY.EnforcePayment) && (CustRoute.getCustomerRoute(this.j1.CustomerID, getRouteID(), paramFFSConnectionHolder) == null)) {
        CustRoute.create(paramFFSConnectionHolder, this.j1.CustomerID, getRouteID());
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** PmtInstruction.storeToDB failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("PmtInstruction.storeToDB done, srvrTID=" + this.j1.SrvrTID, 6);
    return true;
  }
  
  public void removeFromDB(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.removeFromDB start, srvrTID=" + this.j1.SrvrTID, 6);
    String str = "DELETE FROM BPW_PmtInstruction WHERE SrvrTID=?";
    Object[] arrayOfObject = { this.j1.SrvrTID };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** PmtInstruction.removeFromDB failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("PmtInstruction.removeFromDB done, srvrTID=" + this.j1.SrvrTID, 6);
  }
  
  public static int updateStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.updateStatus start, srvrTID=" + paramString1, 6);
    int i = -1;
    try
    {
      String str = "UPDATE BPW_PmtInstruction SET Status=? WHERE SrvrTID=?";
      localObject = new Object[] { paramString2, paramString1 };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str, (Object[])localObject);
    }
    catch (Exception localException)
    {
      Object localObject = "*** Payment.updateStatus failed:" + localException.toString();
      FFSDebug.log((String)localObject);
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("PmtInstruction.updateStatus done, srvrTID=" + paramString1, 6);
    return i;
  }
  
  public static int updateStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.updateStatus start, srvrTID=" + paramString1, 6);
    int i = -1;
    try
    {
      String str = "UPDATE BPW_PmtInstruction SET Status=?, ExtdPmtInfo=? WHERE SrvrTID=?";
      localObject = new Object[] { paramString2, paramString3, paramString1 };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str, (Object[])localObject);
    }
    catch (Exception localException)
    {
      Object localObject = "*** Payment.updateStatus failed:" + localException.toString();
      FFSDebug.log((String)localObject);
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("PmtInstruction.updateStatus done, srvrTID=" + paramString1, 6);
    return i;
  }
  
  public boolean updatePmt(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.updatePmt start, srvrTID=" + this.j1.SrvrTID, 6);
    try
    {
      String str1 = a(this.j1.getImmediateFundAllocation());
      String str2 = a(this.j1.getImmediateProcessing());
      String str3 = "UPDATE BPW_PmtInstruction SET CustomerID=?,PayeeID=?, PayeeListID=?,BankID=?,AcctDebitID=?, AcctDebitType=?, PayAcct=?,DateCreate=?, CurDef=?, Amount=?,RouteID=?, StartDate=?, Status=?, Memo=?, SubmittedBy=?, LastModified=?, ImmediateFundAllocation=?, ImmediateProcessing=? WHERE SrvrTID=?";
      Object[] arrayOfObject = { this.j1.CustomerID, this.j1.PayeeID, new Integer(this.j1.PayeeListID), this.j1.BankID, this.j1.AcctDebitID, this.j1.AcctDebitType, this.j1.PayAcct, this.j1.OriginatedDate, this.j1.CurDef, FFSUtil.formatToScale(this.j1.getAmt()), new Integer(getRouteID()), new Integer(this.j1.StartDate), this.j1.Status, this.j1.Memo, this.j1.submittedBy, this.j1.lastModified, str1, str2, this.j1.SrvrTID };
      DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** PmtInstruction.updatePmt failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("PmtInstruction.updatePmt done, srvrTID=" + this.j1.SrvrTID, 6);
    return true;
  }
  
  public static int updateStartDate(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.updateStartDate start, srvrTID=" + paramString, 6);
    int i = 0;
    try
    {
      String str = "UPDATE BPW_PmtInstruction SET StartDate=? WHERE SrvrTID=?";
      Object[] arrayOfObject = { new Integer(paramInt), paramString };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** PmtInstruction.updateStartDate failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("PmtInstruction.updateStartDate done, srvrTID=" + paramString + ",rows=" + i, 6);
    return i;
  }
  
  public static void delete(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.delete start, srvrTID=" + paramString, 6);
    String str1 = "DELETE FROM BPW_PmtInstruction WHERE SrvrTID=?";
    Object[] arrayOfObject = { paramString };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** PmtInstr.delete failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("PmtInstruction.delete done, srvrTID=" + paramString, 6);
  }
  
  public static boolean hasPendingPmt(String paramString, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.hasPendingPmt start, uid=" + paramString, 6);
    boolean bool = true;
    String str1 = "SELECT SrvrTID FROM BPW_PmtInstruction WHERE CustomerID=? AND PayeeListID=? AND (Status in (?,?,?,?,?) OR (PaymentType = ? AND Status = ?))";
    String str2 = "SELECT RecSrvrTID FROM BPW_RecPmtInstruction WHERE CustomerID=? AND PayeeListID=? AND (Status = ? OR (PaymentType = ? AND Status = ?))";
    Object[] arrayOfObject1 = { paramString, new Integer(paramInt), "WILLPROCESSON", "INFUNDSALLOC", "FUNDSALLOCATED", "APPROVAL_PENDING", "APPROVAL_REJECTED", "TEMPLATE", "ACTIVE" };
    Object[] arrayOfObject2 = { paramString, new Integer(paramInt), "WILLPROCESSON", "RECTEMPLATE", "ACTIVE" };
    FFSResultSet localFFSResultSet1 = null;
    FFSResultSet localFFSResultSet2 = null;
    try
    {
      localFFSResultSet1 = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject1);
      if (!localFFSResultSet1.getNextRow())
      {
        localFFSResultSet2 = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject2);
        if (!localFFSResultSet2.getNextRow()) {
          bool = false;
        }
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** PmtInstruction.hasPendingPmt failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet1 != null)
        {
          localFFSResultSet1.close();
          localFFSResultSet1 = null;
        }
        if (localFFSResultSet2 != null)
        {
          localFFSResultSet2.close();
          localFFSResultSet2 = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** PmtInstruction.hasPendingPmt failed:" + localException2.toString());
      }
    }
    FFSDebug.log("PmtInstruction.hasPendingPmt done, uid=" + paramString + ",result=" + bool, 6);
    return bool;
  }
  
  public static boolean hasPendingPmt(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.hasPendingPmt start, payeeID=" + paramString, 6);
    boolean bool = true;
    String str1 = "SELECT CustomerID FROM BPW_PmtInstruction WHERE PayeeListID=? AND Status in (?,?,?,?,?)";
    String str2 = "SELECT CustomerID FROM BPW_RecPmtInstruction WHERE PayeeListID=? AND Status = ?";
    Object[] arrayOfObject1 = { new Integer(paramString), "WILLPROCESSON", "INFUNDSALLOC", "FUNDSALLOCATED", "APPROVAL_PENDING", "APPROVAL_REJECTED" };
    Object[] arrayOfObject2 = { new Integer(paramString), "WILLPROCESSON" };
    FFSResultSet localFFSResultSet1 = null;
    FFSResultSet localFFSResultSet2 = null;
    try
    {
      localFFSResultSet1 = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject1);
      if (!localFFSResultSet1.getNextRow())
      {
        localFFSResultSet2 = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject2);
        if (!localFFSResultSet2.getNextRow()) {
          bool = false;
        }
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** PmtInstruction.hasPendingPmt failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet1 != null)
        {
          localFFSResultSet1.close();
          localFFSResultSet1 = null;
        }
        if (localFFSResultSet2 != null)
        {
          localFFSResultSet2.close();
          localFFSResultSet2 = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** PmtInstruction.hasPendingPmt failed:" + localException2.toString());
      }
    }
    FFSDebug.log("PmtInstruction.hasPendingPmt done, payeeID=" + paramString, 6);
    return bool;
  }
  
  private static FFSResultSet jdMethod_int(FFSConnectionHolder paramFFSConnectionHolder, int paramInt)
    throws FFSException
  {
    FFSDebug.log("PmtInstruction.getOldSrvrTID: start ageDay=" + paramInt, 6);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd01");
    String str1 = localSimpleDateFormat.format(localCalendar.getTime());
    int i = Integer.parseInt(str1);
    String str2 = "SELECT SrvrTID FROM BPW_PmtInstruction WHERE StartDate <= ? AND Status IN ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) AND PaymentType NOT IN (?)";
    Object[] arrayOfObject = { new Integer(i), "NOFUNDSON_NOTIF", "FAILEDON_NOTIF", "FUNDSFAILEDON_NOTIF", "FUNDSREVERTED_NOTIF", "FUNDSREVERTFAILED_NOTIF", "POSTEDON", "CANCELEDON", "PROCESSEDON", "NOFUNDSON", "FAILEDON", "FUNDSREVERTED", "FUNDSREVERTFAILED", "TEMPLATE" };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str3 = "*** PmtInstruction.getOldSrvrTID failed:";
      System.out.println(str3 + localException.toString());
      throw new FFSException(localException.toString());
    }
    return localFFSResultSet;
  }
  
  private static FFSResultSet jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, int paramInt)
    throws FFSException
  {
    FFSDebug.log("PmtInstruction.getOldTemplateSrvrTID: start ageDay=" + paramInt, 6);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String str1 = localSimpleDateFormat.format(localCalendar.getTime());
    String str2 = "SELECT SrvrTID FROM BPW_PmtInstruction WHERE LastModified <= ? AND Status IN (?) AND PaymentType = ?";
    Object[] arrayOfObject = { str1, "CANCELEDON", "TEMPLATE" };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str3 = "*** PmtInstruction.getOldTemplateSrvrTID failed: ";
      throw new FFSException(str3 + localException.toString());
    }
    return localFFSResultSet;
  }
  
  private static FFSResultSet jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, int paramInt, String paramString)
    throws FFSException
  {
    String str1 = "PmtInstruction.getOldSrvrTID: ";
    FFSDebug.log(str1, "start ageDay=" + paramInt, "custId=" + paramString, 6);
    String str2 = "SELECT SrvrTID FROM BPW_PmtInstruction WHERE StartDate <= ? AND CustomerID = ? AND Status IN ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) AND PaymentType NOT IN (?)";
    Object[] arrayOfObject = { new Integer(paramInt), paramString, "NOFUNDSON_NOTIF", "FAILEDON_NOTIF", "FUNDSFAILEDON_NOTIF", "FUNDSREVERTED_NOTIF", "FUNDSREVERTFAILED_NOTIF", "POSTEDON", "CANCELEDON", "PROCESSEDON", "NOFUNDSON", "FAILEDON", "FUNDSREVERTED", "FUNDSREVERTFAILED", "TEMPLATE" };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed:";
      System.out.println(str3 + localException.toString());
      throw new FFSException(localException.toString());
    }
    return localFFSResultSet;
  }
  
  private static FFSResultSet jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, int paramInt, String paramString)
    throws FFSException
  {
    String str1 = "PmtInstruction.getOldTemplateSrvrTIDByCustId: ";
    FFSDebug.log(str1, "start ageDay=" + paramInt, "custId=" + paramString, 6);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String str2 = localSimpleDateFormat.format(localCalendar.getTime());
    String str3 = "SELECT SrvrTID FROM BPW_PmtInstruction WHERE LastModified <= ? AND CustomerID = ? AND Status IN (?) AND PaymentType = ?";
    Object[] arrayOfObject = { str2, paramString, "CANCELEDON", "TEMPLATE" };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str4 = "*** " + str1 + " failed:";
      throw new FFSException(str4 + localException.toString());
    }
    return localFFSResultSet;
  }
  
  public static PmtInfo[] getPmtBatchByStatus(String paramString1, String paramString2, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("PmtInstruction.getPmtBatchByStatus start, status=" + paramString2, 6);
    String str = "SELECT CustomerID,PayeeID,PayeeListID, BankID, AcctDebitID,AcctDebitType, PayAcct,DateCreate, CurDef, Amount, StartDate, Memo, PaymentType, SrvrTID, FIID, LastModified, SubmittedBy, LogID, ImmediateFundAllocation, ImmediateProcessing FROM BPW_PmtInstruction WHERE FIID=? AND Status=?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    ArrayList localArrayList = new ArrayList();
    FFSResultSet localFFSResultSet = null;
    try
    {
      int i = 0;
      if (jZ.get(paramString2 + paramString1) == null)
      {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
        jZ.put(paramString2 + paramString1, localFFSResultSet);
      }
      else
      {
        localFFSResultSet = (FFSResultSet)jZ.get(paramString2 + paramString1);
      }
      while (localFFSResultSet.getNextRow())
      {
        PmtInfo localPmtInfo = new PmtInfo();
        localPmtInfo.Status = paramString2;
        localPmtInfo.CustomerID = localFFSResultSet.getColumnString("CustomerID");
        localPmtInfo.PayeeID = localFFSResultSet.getColumnString("PayeeID");
        localPmtInfo.PayeeListID = localFFSResultSet.getColumnInt("PayeeListID");
        localPmtInfo.BankID = localFFSResultSet.getColumnString("BankID");
        localPmtInfo.AcctDebitID = localFFSResultSet.getColumnString("AcctDebitID");
        localPmtInfo.AcctDebitType = localFFSResultSet.getColumnString("AcctDebitType");
        localPmtInfo.PayAcct = localFFSResultSet.getColumnString("PayAcct");
        localPmtInfo.OriginatedDate = localFFSResultSet.getColumnString("DateCreate");
        localPmtInfo.CurDef = localFFSResultSet.getColumnString("CurDef");
        localPmtInfo.setAmt(localFFSResultSet.getColumnString("Amount"));
        localPmtInfo.StartDate = localFFSResultSet.getColumnInt("StartDate");
        localPmtInfo.Memo = localFFSResultSet.getColumnString("Memo");
        localPmtInfo.PaymentType = localFFSResultSet.getColumnString("PaymentType");
        localPmtInfo.SrvrTID = localFFSResultSet.getColumnString("SrvrTID");
        localPmtInfo.FIID = localFFSResultSet.getColumnString("FIID");
        localPmtInfo.lastModified = localFFSResultSet.getColumnString("LastModified");
        localPmtInfo.submittedBy = localFFSResultSet.getColumnString("SubmittedBy");
        localPmtInfo.LogID = localFFSResultSet.getColumnString("LogID");
        localPmtInfo.setImmediateFundAllocation(ag(localFFSResultSet.getColumnString("ImmediateFundAllocation")));
        localPmtInfo.setImmediateProcessing(ag(localFFSResultSet.getColumnString("ImmediateProcessing")));
        HashMap localHashMap = jdMethod_try(localPmtInfo.SrvrTID, paramFFSConnectionHolder);
        Object localObject1;
        if ((localHashMap != null) && (localHashMap.size() != 0))
        {
          localObject1 = (String)localHashMap.get("INVOICE");
          if (localObject1 != null)
          {
            PmtInvoice localPmtInvoice = new PmtInvoice();
            localPmtInvoice.parse((String)localObject1);
            localHashMap.remove("INVOICE");
            localHashMap.put("INVOICE", localPmtInvoice);
          }
          localPmtInfo.extraFields = localHashMap;
        }
        localArrayList.add(localPmtInfo);
        i++;
        if (i == paramInt)
        {
          localObject1 = (PmtInfo[])localArrayList.toArray(new PmtInfo[localArrayList.size()]);
          return localObject1;
        }
      }
      localFFSResultSet.close();
      jZ.remove(paramString2 + paramString1);
    }
    catch (Exception localException)
    {
      localException = localException;
      if (localFFSResultSet != null) {
        localFFSResultSet.close();
      }
      jZ.remove(paramString2 + paramString1);
      FFSDebug.log("*** PmtInstruction.getPmtBatchByStatus failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    finally {}
    FFSDebug.log("PmtInstruction.getPmtBatchByStatus done, size=" + localArrayList.size(), 6);
    return (PmtInfo[])localArrayList.toArray(new PmtInfo[localArrayList.size()]);
  }
  
  public static boolean isStatusBatchDone(String paramString1, String paramString2)
  {
    return !jZ.containsKey(paramString2 + paramString1);
  }
  
  public static void clearStatusBatch(String paramString1, String paramString2)
  {
    try
    {
      if (jZ.containsKey(paramString2 + paramString1))
      {
        FFSResultSet localFFSResultSet = (FFSResultSet)jZ.get(paramString2 + paramString1);
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
        jZ.remove(paramString2 + paramString1);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** PmtInstruction.clearStatusBatch failed:" + localException.toString());
    }
  }
  
  public static Vector getPmtByStatus(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("PmtInstruction.getPmtByStatus start, status=" + paramString, 6);
    String str1 = "SELECT CustomerID,PayeeID,PayeeListID, BankID, AcctDebitID,AcctDebitType, PayAcct,DateCreate, CurDef, Amount, StartDate, Memo, PaymentType, SrvrTID, FIID, LastModified, SubmittedBy, LogID, ImmediateFundAllocation, ImmediateProcessing FROM BPW_PmtInstruction WHERE FIID=? AND Status=?";
    Object[] arrayOfObject = { paramString };
    Vector localVector = new Vector();
    FFSResultSet localFFSResultSet = null;
    try
    {
      int i = 0;
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        PmtInfo localPmtInfo = new PmtInfo();
        localPmtInfo.Status = paramString;
        localPmtInfo.CustomerID = localFFSResultSet.getColumnString("CustomerID");
        localPmtInfo.PayeeID = localFFSResultSet.getColumnString("PayeeID");
        localPmtInfo.PayeeListID = localFFSResultSet.getColumnInt("PayeeListID");
        localPmtInfo.BankID = localFFSResultSet.getColumnString("BankID");
        localPmtInfo.AcctDebitID = localFFSResultSet.getColumnString("AcctDebitID");
        localPmtInfo.AcctDebitType = localFFSResultSet.getColumnString("AcctDebitType");
        localPmtInfo.PayAcct = localFFSResultSet.getColumnString("PayAcct");
        localPmtInfo.OriginatedDate = localFFSResultSet.getColumnString("DateCreate");
        localPmtInfo.CurDef = localFFSResultSet.getColumnString("CurDef");
        localPmtInfo.setAmt(localFFSResultSet.getColumnString("Amount"));
        localPmtInfo.StartDate = localFFSResultSet.getColumnInt("StartDate");
        localPmtInfo.Memo = localFFSResultSet.getColumnString("Memo");
        localPmtInfo.PaymentType = localFFSResultSet.getColumnString("PaymentType");
        localPmtInfo.SrvrTID = localFFSResultSet.getColumnString("SrvrTID");
        localPmtInfo.FIID = localFFSResultSet.getColumnString("FIID");
        localPmtInfo.lastModified = localFFSResultSet.getColumnString("LastModified");
        localPmtInfo.submittedBy = localFFSResultSet.getColumnString("SubmittedBy");
        localPmtInfo.LogID = localFFSResultSet.getColumnString("LogID");
        localPmtInfo.setImmediateFundAllocation(ag(localFFSResultSet.getColumnString("ImmediateFundAllocation")));
        localPmtInfo.setImmediateProcessing(ag(localFFSResultSet.getColumnString("ImmediateProcessing")));
        HashMap localHashMap = jdMethod_try(localPmtInfo.SrvrTID, paramFFSConnectionHolder);
        if ((localHashMap != null) && (localHashMap.size() != 0))
        {
          String str2 = (String)localHashMap.get("INVOICE");
          if (str2 != null)
          {
            PmtInvoice localPmtInvoice = new PmtInvoice();
            localPmtInvoice.parse(str2);
            localHashMap.remove("INVOICE");
            localHashMap.put("INVOICE", localPmtInvoice);
          }
          localPmtInfo.extraFields = localHashMap;
        }
        localVector.addElement(localPmtInfo);
        i++;
      }
      localFFSResultSet.close();
    }
    catch (Exception localException)
    {
      localException = localException;
      if (localFFSResultSet != null) {
        localFFSResultSet.close();
      }
      FFSDebug.log("*** PmtInstruction.getPmtByStatus failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    finally {}
    FFSDebug.log("PmtInstruction.getPmtByStatus done, size=" + localVector.size(), 6);
    return localVector;
  }
  
  public static Vector getPmtByStatusAndCustomerID(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.getPmtByStatusAndCustomerID start, status=" + paramString1 + ",CustomerID=" + paramString2, 6);
    String str1 = "SELECT CustomerID,PayeeID,PayeeListID, BankID, AcctDebitID,AcctDebitType, PayAcct,DateCreate, CurDef, Amount, StartDate, Memo, PaymentType, SrvrTID, FIID, LastModified, SubmittedBy, LogID, ImmediateFundAllocation, ImmediateProcessing FROM BPW_PmtInstruction WHERE Status=? AND CustomerID=? ";
    Object[] arrayOfObject = { paramString1, paramString2 };
    Vector localVector = new Vector();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        PmtInfo localPmtInfo = new PmtInfo();
        localPmtInfo.Status = paramString1;
        localPmtInfo.CustomerID = localFFSResultSet.getColumnString("CustomerID");
        localPmtInfo.PayeeID = localFFSResultSet.getColumnString("PayeeID");
        localPmtInfo.PayeeListID = localFFSResultSet.getColumnInt("PayeeListID");
        localPmtInfo.BankID = localFFSResultSet.getColumnString("BankID");
        localPmtInfo.AcctDebitID = localFFSResultSet.getColumnString("AcctDebitID");
        localPmtInfo.AcctDebitType = localFFSResultSet.getColumnString("AcctDebitType");
        localPmtInfo.PayAcct = localFFSResultSet.getColumnString("PayAcct");
        localPmtInfo.OriginatedDate = localFFSResultSet.getColumnString("DateCreate");
        localPmtInfo.CurDef = localFFSResultSet.getColumnString("CurDef");
        localPmtInfo.setAmt(localFFSResultSet.getColumnString("Amount"));
        localPmtInfo.StartDate = localFFSResultSet.getColumnInt("StartDate");
        localPmtInfo.Memo = localFFSResultSet.getColumnString("Memo");
        localPmtInfo.PaymentType = localFFSResultSet.getColumnString("PaymentType");
        localPmtInfo.SrvrTID = localFFSResultSet.getColumnString("SrvrTID");
        localPmtInfo.FIID = localFFSResultSet.getColumnString("FIID");
        localPmtInfo.lastModified = localFFSResultSet.getColumnString("LastModified");
        localPmtInfo.submittedBy = localFFSResultSet.getColumnString("SubmittedBy");
        localPmtInfo.LogID = localFFSResultSet.getColumnString("LogID");
        localPmtInfo.setImmediateFundAllocation(ag(localFFSResultSet.getColumnString("ImmediateFundAllocation")));
        localPmtInfo.setImmediateProcessing(ag(localFFSResultSet.getColumnString("ImmediateProcessing")));
        HashMap localHashMap = jdMethod_try(localPmtInfo.SrvrTID, paramFFSConnectionHolder);
        if ((localHashMap != null) && (localHashMap.size() != 0))
        {
          String str2 = (String)localHashMap.get("INVOICE");
          if (str2 != null)
          {
            PmtInvoice localPmtInvoice = new PmtInvoice();
            localPmtInvoice.parse(str2);
            localHashMap.remove("INVOICE");
            localHashMap.put("INVOICE", localPmtInvoice);
          }
          localPmtInfo.extraFields = localHashMap;
        }
        localVector.addElement(localPmtInfo);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** PmtInstruction.getPmtByStatusAndCustomerID failed:" + localException1.toString());
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
        FFSDebug.log("*** PmtInstruction.getPmtByStatusAndCustomerID failed:" + localException2.toString());
      }
    }
    FFSDebug.log("PmtInstruction.getPmtByStatusAndCustomerID done, size=" + localVector.size(), 6);
    return localVector;
  }
  
  public static HashMap getAllPmtHistory(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String[] paramArrayOfString, String paramString2, String paramString3, String paramString4, String paramString5, long paramLong)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.getAllPmtHistory start custId =" + paramString1, 6);
    String str1 = "";
    String str2 = "";
    if ((paramString2 != null) && (paramString2.length() > 0)) {
      str1 = ah(paramString2);
    }
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0)) {
      str2 = jdMethod_new(paramArrayOfString);
    }
    String str3 = jdMethod_new(paramString3, paramString4);
    String str4 = "SELECT A.SrvrTID, A.CustomerID, A.PayeeID, A.PayeeListID, A.PayAcct, A.RouteID, A.Amount, A.BankID, A.AcctDebitID, A.AcctDebitType, A.Memo, A.ExtdPmtInfo, A.DateCreate, A.CurDef, A.StartDate, A.Status, A.LogID, A.PaymentType,B.Submitdate,B.DtProcessed,A.FIID, A.ImmediateFundAllocation, A.ImmediateProcessing FROM BPW_PmtInstruction A,BPW_SrvrTrans B WHERE (A.SrvrTID = B.SrvrTID) and A.CustomerID = ? " + str3 + str1 + str2;
    str4 = str4 + " ORDER BY CAST(SrvrTID AS INTEGER) ASC";
    Object[] arrayOfObject = { paramString1 };
    FFSDebug.log("PmtInstruction.getAllPmtHistory done ", 6);
    return a(paramFFSConnectionHolder, str4, paramLong, arrayOfObject);
  }
  
  public static HashMap getPmtHistoryByPage(FFSConnectionHolder paramFFSConnectionHolder, long paramLong1, String paramString1, String[] paramArrayOfString, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, long paramLong2)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.getPmtHistoryByPage start custId =" + paramString1, 6);
    if (paramString3 == null) {
      paramString3 = "Next";
    }
    String str1 = "";
    String str2 = "";
    String str3 = "";
    String str4 = "";
    if (paramString3.equalsIgnoreCase("Next"))
    {
      str3 = "SELECT A.SrvrTID, A.CustomerID, A.PayeeID, A.PayeeListID, A.PayAcct, A.RouteID, A.Amount, A.BankID, A.AcctDebitID, A.AcctDebitType, A.Memo, A.ExtdPmtInfo, A.DateCreate, A.CurDef, A.StartDate, A.Status, A.LogID, A.PaymentType,B.Submitdate,B.DtProcessed,A.FIID, A.ImmediateFundAllocation, A.ImmediateProcessing FROM BPW_PmtInstruction A,BPW_SrvrTrans B WHERE (A.SrvrTID = B.SrvrTID) AND CAST(A.SrvrTID AS INTEGER) > ? AND A.CustomerID = ?";
      str4 = " ORDER BY CAST(SrvrTID AS INTEGER) ASC";
    }
    else
    {
      str3 = "SELECT A.SrvrTID, A.CustomerID, A.PayeeID, A.PayeeListID, A.PayAcct, A.RouteID, A.Amount, A.BankID, A.AcctDebitID, A.AcctDebitType, A.Memo, A.ExtdPmtInfo, A.DateCreate, A.CurDef, A.StartDate, A.Status, A.LogID, A.PaymentType,B.Submitdate,B.DtProcessed,A.FIID, A.ImmediateFundAllocation, A.ImmediateProcessing FROM BPW_PmtInstruction A,BPW_SrvrTrans B WHERE (A.SrvrTID = B.SrvrTID) AND CAST(A.SrvrTID AS INTEGER) < ? AND A.CustomerID = ?";
      str4 = " ORDER BY CAST(SrvrTID AS INTEGER) DESC";
    }
    if ((paramString2 != null) && (paramString2.length() > 0)) {
      str1 = ah(paramString2);
    }
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0)) {
      str2 = jdMethod_new(paramArrayOfString);
    }
    String str5 = jdMethod_new(paramString4, paramString5);
    str3 = str3 + str5 + str1 + str2;
    str3 = str3 + str4;
    Object[] arrayOfObject = { paramLong1 + "", paramString1 };
    FFSDebug.log("PmtInstruction.getPmtHistoryByPage done ", 6);
    return a(paramFFSConnectionHolder, str3, paramLong2, arrayOfObject, paramString3);
  }
  
  public static HashMap getPmtHistoryByRange(FFSConnectionHolder paramFFSConnectionHolder, long paramLong1, long paramLong2, String paramString1, String[] paramArrayOfString, String paramString2, String paramString3, String paramString4, String paramString5)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.getPmtHistoryByRange start custID =" + paramString1, 6);
    String str1 = "";
    String str2 = "";
    if ((paramString2 != null) && (paramString2.length() > 0)) {
      str1 = ah(paramString2);
    }
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0)) {
      str2 = jdMethod_new(paramArrayOfString);
    }
    String str3 = jdMethod_new(paramString3, paramString4);
    String str4 = "SELECT A.SrvrTID, A.CustomerID, A.PayeeID, A.PayeeListID, A.PayAcct, A.RouteID, A.Amount, A.BankID, A.AcctDebitID, A.AcctDebitType, A.Memo, A.ExtdPmtInfo, A.DateCreate, A.CurDef, A.StartDate, A.Status, A.LogID, A.PaymentType,B.Submitdate,B.DtProcessed,A.FIID, A.ImmediateFundAllocation, A.ImmediateProcessing FROM BPW_PmtInstruction A,BPW_SrvrTrans B WHERE (A.SrvrTID = B.SrvrTID) AND CAST(A.SrvrTID AS INTEGER) >= ? AND CAST (A.SrvrTID AS INTEGER) <= ? AND A.CustomerID = ? " + str3 + str1 + str2;
    str4 = str4 + " ORDER BY CAST(SrvrTID AS INTEGER) ASC";
    Object[] arrayOfObject = { paramLong1 + "", paramLong2 + "", paramString1 };
    FFSDebug.log("PmtInstruction.getPmtHistoryByRange done ", 6);
    return a(paramFFSConnectionHolder, str4, arrayOfObject);
  }
  
  private static HashMap a(FFSConnectionHolder paramFFSConnectionHolder, String paramString, Object[] paramArrayOfObject)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.getResultForPmtHistoryByRange stmt =" + paramString, 6);
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap1 = new HashMap();
    FFSResultSet localFFSResultSet = null;
    int i = 0;
    long l1 = 0L;
    long l2 = 0L;
    PmtInfo localPmtInfo = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, paramString, paramArrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        i++;
        l1 = localFFSResultSet.getColumnLong(1);
        localPmtInfo = a(paramFFSConnectionHolder, localFFSResultSet);
        HashMap localHashMap2 = jdMethod_try(localPmtInfo.SrvrTID, paramFFSConnectionHolder);
        if ((localHashMap2 != null) && (localHashMap2.size() != 0))
        {
          String str = (String)localHashMap2.get("INVOICE");
          if (str != null)
          {
            PmtInvoice localPmtInvoice = new PmtInvoice();
            localPmtInvoice.parse(str);
            localHashMap2.remove("INVOICE");
            localHashMap2.put("INVOICE", localPmtInvoice);
          }
          localPmtInfo.extraFields = localHashMap2;
        }
        localArrayList.add(localPmtInfo);
      }
      if (localArrayList.size() > 0) {
        l2 = Long.parseLong(((PmtInfo)localArrayList.get(0)).SrvrTID);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log(localException1, "*** PmtInstruction.getResultForPmtHistoryByRange failed :" + localException1.toString());
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
        FFSDebug.log("*** PmtInstruction.getResultForPmtHistoryByRange  failed:" + localException2.toString());
      }
    }
    localHashMap1.put("QRYRESULT", (PmtInfo[])localArrayList.toArray(new PmtInfo[0]));
    localHashMap1.put("MINKEYVAL", new Long(l2));
    localHashMap1.put("MAXKEYVAL", new Long(l1));
    localHashMap1.put("RECCOUNT", new Long(localArrayList.size()));
    return localHashMap1;
  }
  
  private static HashMap a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, long paramLong, Object[] paramArrayOfObject, String paramString2)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.getResultForPmtHistoryByPage stmt =" + paramString1, 6);
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap1 = new HashMap();
    FFSResultSet localFFSResultSet = null;
    int i = 0;
    long l1 = 0L;
    long l2 = 0L;
    int j = 0;
    PmtInfo localPmtInfo = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, paramString1, paramArrayOfObject);
      if (paramLong == 0L)
      {
        a(paramFFSConnectionHolder, localFFSResultSet, localArrayList);
        j = localArrayList.size();
        i = j;
        if (j > 0) {
          l1 = Long.parseLong(((PmtInfo)localArrayList.get(j - 1)).SrvrTID);
        }
      }
      else
      {
        while (localFFSResultSet.getNextRow())
        {
          i++;
          if (i <= paramLong)
          {
            l1 = localFFSResultSet.getColumnLong(1);
            localPmtInfo = a(paramFFSConnectionHolder, localFFSResultSet);
            HashMap localHashMap2 = jdMethod_try(localPmtInfo.SrvrTID, paramFFSConnectionHolder);
            if ((localHashMap2 != null) && (localHashMap2.size() != 0))
            {
              String str = (String)localHashMap2.get("INVOICE");
              if (str != null)
              {
                PmtInvoice localPmtInvoice = new PmtInvoice();
                localPmtInvoice.parse(str);
                localHashMap2.remove("INVOICE");
                localHashMap2.put("INVOICE", localPmtInvoice);
              }
              localPmtInfo.extraFields = localHashMap2;
            }
            if (paramString2.equalsIgnoreCase("Next")) {
              localArrayList.add(localPmtInfo);
            } else {
              localArrayList.add(0, localPmtInfo);
            }
          }
        }
      }
      if (localArrayList.size() > 0)
      {
        l2 = Long.parseLong(((PmtInfo)localArrayList.get(0)).SrvrTID);
        if (paramString2.equalsIgnoreCase("Next")) {
          l2 = Long.parseLong(((PmtInfo)localArrayList.get(0)).SrvrTID);
        } else {
          l2 = Long.parseLong(((PmtInfo)localArrayList.get(localArrayList.size() - 1)).SrvrTID);
        }
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log(localException1, "*** PmtInstruction.getResultForPmtHistoryByPage failed :" + localException1.toString());
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
        FFSDebug.log("*** PmtInstruction.getResultForPmtHistoryByPage failed:" + localException2.toString());
      }
    }
    localHashMap1.put("QRYRESULT", (PmtInfo[])localArrayList.toArray(new PmtInfo[0]));
    if (paramString2.equalsIgnoreCase("Next"))
    {
      localHashMap1.put("MINKEYVAL", new Long(l2));
      localHashMap1.put("MAXKEYVAL", new Long(l1));
    }
    else
    {
      localHashMap1.put("MINKEYVAL", new Long(l1));
      localHashMap1.put("MAXKEYVAL", new Long(l2));
    }
    localHashMap1.put("RECCOUNT", new Long(i));
    return localHashMap1;
  }
  
  private static HashMap a(FFSConnectionHolder paramFFSConnectionHolder, String paramString, long paramLong, Object[] paramArrayOfObject)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.getResultForAllPmtHistory start, stmt=" + paramString, 6);
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap1 = new HashMap();
    FFSResultSet localFFSResultSet = null;
    int i = 0;
    long l1 = 0L;
    long l2 = 0L;
    PmtInfo localPmtInfo = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, paramString, paramArrayOfObject);
      int j = 0;
      if (paramLong == 0L)
      {
        a(paramFFSConnectionHolder, localFFSResultSet, localArrayList);
        j = localArrayList.size();
        i = j;
        if (j > 0) {
          l1 = Long.parseLong(((PmtInfo)localArrayList.get(j - 1)).SrvrTID);
        }
      }
      else
      {
        while (localFFSResultSet.getNextRow())
        {
          i++;
          if (i <= paramLong)
          {
            l1 = localFFSResultSet.getColumnLong(1);
            localPmtInfo = a(paramFFSConnectionHolder, localFFSResultSet);
            HashMap localHashMap2 = jdMethod_try(localPmtInfo.SrvrTID, paramFFSConnectionHolder);
            if ((localHashMap2 != null) && (localHashMap2.size() != 0))
            {
              String str = (String)localHashMap2.get("INVOICE");
              if (str != null)
              {
                PmtInvoice localPmtInvoice = new PmtInvoice();
                localPmtInvoice.parse(str);
                localHashMap2.remove("INVOICE");
                localHashMap2.put("INVOICE", localPmtInvoice);
              }
              localPmtInfo.extraFields = localHashMap2;
            }
            localArrayList.add(localPmtInfo);
          }
          else
          {
            l1 = localFFSResultSet.getColumnLong(1);
          }
        }
      }
      if (localArrayList.size() > 0) {
        l2 = Long.parseLong(((PmtInfo)localArrayList.get(0)).SrvrTID);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log(localException1, "*** PmtInstruction.getResultForAllPmtHistory failed:" + localException1.toString());
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
        FFSDebug.log("*** PmtInstruction.getResultForAllPmtHistory failed:" + localException2.toString());
      }
    }
    localHashMap1.put("QRYRESULT", (PmtInfo[])localArrayList.toArray(new PmtInfo[0]));
    localHashMap1.put("MINKEYVAL", new Long(l2));
    localHashMap1.put("MAXKEYVAL", new Long(l1));
    localHashMap1.put("RECCOUNT", new Long(i));
    return localHashMap1;
  }
  
  private static PmtInfo a(FFSConnectionHolder paramFFSConnectionHolder, FFSResultSet paramFFSResultSet)
    throws Exception
  {
    FFSDebug.log("PmtInstruction.populateFromResultSet start", 6);
    PmtInfo localPmtInfo = new PmtInfo();
    localPmtInfo.SrvrTID = paramFFSResultSet.getColumnString("SrvrTID");
    localPmtInfo.CustomerID = paramFFSResultSet.getColumnString("CustomerID");
    localPmtInfo.PayeeID = paramFFSResultSet.getColumnString("PayeeID");
    localPmtInfo.PayeeListID = paramFFSResultSet.getColumnInt("PayeeListID");
    localPmtInfo.PayAcct = paramFFSResultSet.getColumnString("PayAcct");
    localPmtInfo.setAmt(paramFFSResultSet.getColumnString("Amount"));
    localPmtInfo.BankID = paramFFSResultSet.getColumnString("BankID");
    localPmtInfo.AcctDebitID = paramFFSResultSet.getColumnString("AcctDebitID");
    localPmtInfo.AcctDebitType = paramFFSResultSet.getColumnString("AcctDebitType");
    localPmtInfo.Memo = paramFFSResultSet.getColumnString("Memo");
    localPmtInfo.ExtdPmtInfo = paramFFSResultSet.getColumnString("ExtdPmtInfo");
    localPmtInfo.OriginatedDate = paramFFSResultSet.getColumnString("DateCreate");
    localPmtInfo.CurDef = paramFFSResultSet.getColumnString("CurDef");
    localPmtInfo.StartDate = paramFFSResultSet.getColumnInt("StartDate");
    localPmtInfo.Status = paramFFSResultSet.getColumnString("Status");
    localPmtInfo.LogID = paramFFSResultSet.getColumnString("LogID");
    localPmtInfo.PaymentType = paramFFSResultSet.getColumnString("PaymentType");
    localPmtInfo.StatusDt = paramFFSResultSet.getColumnString("Submitdate");
    localPmtInfo.AdjustedDueDt = paramFFSResultSet.getColumnString("DtProcessed");
    localPmtInfo.FIID = paramFFSResultSet.getColumnString("FIID");
    localPmtInfo.setImmediateFundAllocation(ag(paramFFSResultSet.getColumnString("ImmediateFundAllocation")));
    localPmtInfo.setImmediateProcessing(ag(paramFFSResultSet.getColumnString("ImmediateProcessing")));
    localPmtInfo.payeeInfo = Payee.findPayeeByID("" + localPmtInfo.PayeeID, paramFFSConnectionHolder);
    return localPmtInfo;
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, FFSResultSet paramFFSResultSet, ArrayList paramArrayList)
    throws Exception
  {
    PmtInfo localPmtInfo = null;
    while (paramFFSResultSet.getNextRow())
    {
      localPmtInfo = a(paramFFSConnectionHolder, paramFFSResultSet);
      HashMap localHashMap = jdMethod_try(localPmtInfo.SrvrTID, paramFFSConnectionHolder);
      if ((localHashMap != null) && (localHashMap.size() != 0))
      {
        String str = (String)localHashMap.get("INVOICE");
        if (str != null)
        {
          PmtInvoice localPmtInvoice = new PmtInvoice();
          localPmtInvoice.parse(str);
          localHashMap.remove("INVOICE");
          localHashMap.put("INVOICE", localPmtInvoice);
        }
        localPmtInfo.extraFields = localHashMap;
      }
      paramArrayList.add(localPmtInfo);
    }
  }
  
  private static String ah(String paramString)
  {
    String str1 = " AND Status in ( ";
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
    int i = 1;
    String str2 = "";
    while (localStringTokenizer.hasMoreTokens()) {
      if (i != 0)
      {
        str2 = str2 + "'" + localStringTokenizer.nextToken().trim() + "'";
        i = 0;
      }
      else
      {
        str2 = str2 + ",'" + localStringTokenizer.nextToken().trim() + "'";
      }
    }
    str1 = str1 + str2.toString() + " ) ";
    return str1;
  }
  
  private static String jdMethod_new(String[] paramArrayOfString)
  {
    String str = " AND AcctDebitID in ( ";
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < paramArrayOfString.length; i++) {
      if (i == 0) {
        localStringBuffer.append("'" + paramArrayOfString[i] + "'");
      } else {
        localStringBuffer.append(",'" + paramArrayOfString[i] + "'");
      }
    }
    str = str + localStringBuffer.toString() + " ) ";
    return str;
  }
  
  private static String jdMethod_new(String paramString1, String paramString2)
  {
    String str = "";
    if ((paramString1 == null) || (paramString1.trim().equals(""))) {
      str = " AND StartDate < " + paramString2 + " ";
    } else {
      str = " AND StartDate > " + paramString1 + " " + " AND StartDate < " + paramString2 + " ";
    }
    return str;
  }
  
  public static PmtInfo[] getHist(FFSConnectionHolder paramFFSConnectionHolder, BPWExtdHist paramBPWExtdHist, int paramInt1, int paramInt2)
    throws Exception
  {
    String str1 = paramBPWExtdHist.getAcctId();
    String str2 = paramBPWExtdHist.getRequiredStatus();
    String str3 = "SELECT SrvrTID, StartDate, Status, Amount, FIID FROM BPW_PmtInstruction  WHERE CustomerID = ? AND StartDate >= ? AND StartDate <= ? ";
    if ((str1 != null) && (str1.trim().length() > 0)) {
      str3 = str3 + "AND AcctDebitID = '" + str1 + "' ";
    }
    String str4 = "";
    if ((str2 != null) && (str2.length() > 0)) {
      str4 = DBUtil.buildStatusQueryString(str2);
    }
    str3 = str3 + str4;
    String str5 = "";
    String str6 = paramBPWExtdHist.getBankId();
    String str7 = paramBPWExtdHist.getAcctType();
    String str8 = paramBPWExtdHist.getRecSrvrTid();
    String str9 = paramBPWExtdHist.getPayeeListId();
    String str10 = paramBPWExtdHist.getBillingAcct();
    String str11 = paramBPWExtdHist.getPayeeId();
    if (str6 != null) {
      str5 = str5 + " And BankID = '" + str6 + "' ";
    }
    if (str7 != null) {
      str5 = str5 + " And AcctDebitType = '" + str7 + "' ";
    }
    if (str8 != null) {
      str5 = str5 + DBUtil.buildWhereClauseStringColumn(str8, "RecSrvrTID");
    }
    if (str9 != null) {
      str5 = str5 + DBUtil.buildWhereClauseIntColumn(str9, "PayeeListID");
    }
    if (str10 != null) {
      str5 = str5 + DBUtil.buildWhereClauseStringColumn(str10, "PayAcct");
    }
    if (str11 != null) {
      str5 = str5 + DBUtil.buildWhereClauseStringColumn(str11, "PayeeID");
    }
    if ((str5 != null) && (!str5.trim().equals(""))) {
      str3 = str3 + str5;
    }
    String str12 = "";
    String str13 = "";
    ArrayList localArrayList = null;
    if ((paramBPWExtdHist.getExtraInfo() != null) && (paramBPWExtdHist.getExtraInfo().size() > 0))
    {
      localObject1 = paramBPWExtdHist.getExtraInfo().keySet();
      if (localObject1 == null) {
        return null;
      }
      localObject2 = ((Set)localObject1).iterator();
      if (localObject2 == null) {
        return null;
      }
      int i = 1;
      String str14 = "";
      while (((Iterator)localObject2).hasNext())
      {
        str13 = (String)((Iterator)localObject2).next();
        localArrayList = (ArrayList)paramBPWExtdHist.getExtraInfo().get(str13);
        if (i != 0) {
          i = 0;
        } else {
          str12 = str12 + " And ";
        }
        str14 = "";
        for (int j = 0; j < localArrayList.size(); j++) {
          if (j == 0) {
            str14 = str14 + " ( Name ='" + str13 + "' And Value = '" + localArrayList.get(j) + "' )";
          } else {
            str14 = str14 + " OR ( Name ='" + str13 + "' And Value = '" + localArrayList.get(j) + "' )";
          }
        }
        if ((str14 != null) && (!str14.trim().equals("")))
        {
          str14 = "(" + str14 + ")";
          str12 = str12 + str14;
        }
      }
    }
    Object localObject1 = null;
    if ((str12 != null) && (!str12.trim().equals("")))
    {
      localObject1 = jX + str12 + " ))";
      str3 = str3 + (String)localObject1;
    }
    localObject1 = null;
    Object localObject2 = { paramBPWExtdHist.getCustId(), new Integer(paramInt1), new Integer(paramInt2) };
    return a(paramFFSConnectionHolder, str3, (Object[])localObject2, paramBPWExtdHist.getHistId(), paramBPWExtdHist.getPageSize());
  }
  
  public static PmtInfo[] getHist(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, String paramString4, long paramLong)
    throws Exception
  {
    FFSDebug.log("PmtInstruction.getHist start, custID=" + paramString1 + ",acctID=" + paramString2 + ",startDate=" + paramInt1 + ",endDate=" + paramInt2 + ",status=" + paramString3, 6);
    String str1 = "SELECT SrvrTID, StartDate, Status, Amount, FIID FROM BPW_PmtInstruction  WHERE CustomerID = ? AND StartDate >= ? AND StartDate <= ? ";
    if ((paramString2 != null) && (paramString2.trim().length() > 0)) {
      str1 = str1 + "AND AcctDebitID = '" + paramString2 + "' ";
    }
    String str2 = "";
    if ((paramString3 != null) && (paramString3.length() > 0)) {
      str2 = DBUtil.buildStatusQueryString(paramString3);
    }
    str1 = str1 + str2;
    Object[] arrayOfObject = { paramString1, new Integer(paramInt1), new Integer(paramInt2) };
    return a(paramFFSConnectionHolder, str1, arrayOfObject, paramString4, paramLong);
  }
  
  private static PmtInfo[] a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, Object[] paramArrayOfObject, String paramString2, long paramLong)
    throws Exception
  {
    FFSDebug.log("PmtInstruction.executeHistQuery start", 6);
    PmtInfo localPmtInfo = null;
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    long l = 0L;
    try
    {
      if (j2.get(paramString2) == null)
      {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, paramString1, paramArrayOfObject);
        j2.put(paramString2, localFFSResultSet);
      }
      else
      {
        localFFSResultSet = (FFSResultSet)j2.get(paramString2);
      }
      while (localFFSResultSet.getNextRow())
      {
        localPmtInfo = new PmtInfo();
        localPmtInfo.SrvrTID = localFFSResultSet.getColumnString("SrvrTID");
        localPmtInfo.StartDate = localFFSResultSet.getColumnInt("StartDate");
        localPmtInfo.Status = localFFSResultSet.getColumnString("Status");
        localPmtInfo.setAmt(localFFSResultSet.getColumnString("Amount"));
        localPmtInfo.FIID = localFFSResultSet.getColumnString("FIID");
        HashMap localHashMap = jdMethod_try(localPmtInfo.SrvrTID, paramFFSConnectionHolder);
        Object localObject1;
        if ((localHashMap != null) && (localHashMap.size() != 0))
        {
          localObject1 = (String)localHashMap.get("INVOICE");
          if (localObject1 != null)
          {
            PmtInvoice localPmtInvoice = new PmtInvoice();
            localPmtInvoice.parse((String)localObject1);
            localHashMap.remove("INVOICE");
            localHashMap.put("INVOICE", localPmtInvoice);
          }
          localPmtInfo.extraFields = localHashMap;
        }
        localArrayList.add(localPmtInfo);
        l += 1L;
        if (l == paramLong)
        {
          localObject1 = (PmtInfo[])localArrayList.toArray(new PmtInfo[0]);
          return localObject1;
        }
      }
      localFFSResultSet.close();
      j2.remove(paramString2);
    }
    catch (Exception localException)
    {
      localException = localException;
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
      if (j2 != null) {
        j2.remove(paramString2);
      }
      throw new Exception(localException.toString() + FFSDebug.stackTrace(localException));
    }
    finally {}
    FFSDebug.log("PmtInstruction.executeHistQuery done, rows=" + l, 6);
    return (PmtInfo[])localArrayList.toArray(new PmtInfo[0]);
  }
  
  public static boolean isBatchDone(String paramString)
  {
    return !j2.containsKey(paramString);
  }
  
  public static void clearBatch(String paramString)
  {
    try
    {
      if (j2.containsKey(paramString))
      {
        FFSResultSet localFFSResultSet = (FFSResultSet)j2.get(paramString);
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
        j2.remove(paramString);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** PmtInstruction.clearBatch failed:" + localException.toString());
    }
  }
  
  public static PmtInfo[] getPmtInfo(String[] paramArrayOfString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.getPmtInfo (multiple) : start", 6);
    int i = 0;
    PmtInfo localPmtInfo = null;
    ArrayList localArrayList = new ArrayList();
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      localPmtInfo = new PmtInfo();
      localPmtInfo.statusCode = 16504;
      localPmtInfo.statusMsg = "ID Array is null or empty";
      FFSDebug.log("PmtInstruction.getPmtInfo (multiple): failed null or empty id array passed", 0);
      PmtInfo[] arrayOfPmtInfo = { localPmtInfo };
      return arrayOfPmtInfo;
    }
    i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
    {
      localPmtInfo = getPmtInfo(paramArrayOfString[j], paramFFSConnectionHolder);
      if (localPmtInfo == null)
      {
        localPmtInfo = new PmtInfo();
        localPmtInfo.statusCode = 17020;
        localPmtInfo.statusMsg = "No Records Found";
      }
      else
      {
        localPmtInfo.statusCode = 0;
        localPmtInfo.statusMsg = "Success";
      }
      try
      {
        paramFFSConnectionHolder.conn.commit();
      }
      catch (Exception localException)
      {
        throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
      }
      localArrayList.add(localPmtInfo);
    }
    return (PmtInfo[])localArrayList.toArray(new PmtInfo[0]);
  }
  
  private static String jdMethod_null(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws Exception
  {
    FFSDebug.log("PmtInstruction.getExtdPmtInfo start, fIId =" + paramString1 + ", srvrTID =" + paramString2, 6);
    Hashtable localHashtable = BPWExtraInfo.getXtraInfo(paramFFSConnectionHolder, paramString1, paramString2, "IFXPMT");
    String str = FFSUtil.hashtableToString(localHashtable);
    FFSDebug.log("PmtInstruction.getExtdPmtInfo end , extdPmtInfo =" + str, 6);
    return str;
  }
  
  private static HashMap jdMethod_try(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.getPmtExtraInfo start :srvrTID = " + paramString, 6);
    String str = null;
    HashMap localHashMap = null;
    if (paramFFSConnectionHolder == null)
    {
      str = "*** PmtInstruction.getPmtExtraInfo failed:ConnectionHolder is null ";
      FFSDebug.log(str, 0);
      throw new BPWException(str);
    }
    try
    {
      localHashMap = PmtExtraInfo.getHashMap(paramString, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      str = "*** PmtInstruction.getPmtExtraInfo failed:" + localException.toString();
      FFSDebug.log(str, 0);
      throw new BPWException(str);
    }
    FFSDebug.log("PmtInstruction.getPmtExtraInfo end :", 6);
    return localHashMap;
  }
  
  public static int cleanup(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws FFSException
  {
    String str1 = "PmtInstruction.cleanup ";
    int i = 0;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd01");
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
          localFFSResultSet = jdMethod_do(paramFFSConnectionHolder, j, paramString);
        } else {
          localFFSResultSet = jdMethod_int(paramFFSConnectionHolder, paramInt);
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
                  i += jdMethod_int(paramFFSConnectionHolder, localArrayList);
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
              jdMethod_int(paramFFSConnectionHolder, localArrayList);
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
      FFSDebug.log("----> Done deleting payments", 3);
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
    localArrayList.clear();
    k = 1;
    try
    {
      while (k <= 2)
      {
        if ((paramString != null) && (paramString.trim().length() != 0)) {
          localFFSResultSet = jdMethod_for(paramFFSConnectionHolder, paramInt, paramString);
        } else {
          localFFSResultSet = jdMethod_for(paramFFSConnectionHolder, paramInt);
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
                  paramFFSConnectionHolder.conn.commit();
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
            if (localArrayList.size() > 0)
            {
              i += deleteBatch(paramFFSConnectionHolder, (String[])localArrayList.toArray(new String[0]));
              paramFFSConnectionHolder.conn.commit();
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
      FFSDebug.log("----> Done deleting payment templates", 3);
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
  
  private static int jdMethod_int(FFSConnectionHolder paramFFSConnectionHolder, ArrayList paramArrayList)
    throws FFSException
  {
    String[] arrayOfString = (String[])paramArrayList.toArray(new String[0]);
    EventInfoLog.deleteBatchByInstructionIdsAndType(paramFFSConnectionHolder, arrayOfString, "PMTTRN");
    BPWExtraInfo.deleteBatch(paramFFSConnectionHolder, arrayOfString, "IFXPMT");
    deleteBatch(paramFFSConnectionHolder, arrayOfString);
    SrvrTrans.deleteBatch(paramFFSConnectionHolder, arrayOfString);
    paramFFSConnectionHolder.conn.commit();
    FFSDebug.log("----> Committed Payment Cleanup.  Removed " + arrayOfString.length + " payments.", 3);
    return arrayOfString.length;
  }
  
  public static PmtInfo insertPayment(PmtInfo paramPmtInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    paramPmtInfo.OriginatedDate = DBUtil.getCurrentLogDate();
    paramPmtInfo.SrvrTID = DBUtil.getNextIndexString("SrvrTID");
    paramPmtInfo.Status = "ACTIVE";
    String str1 = a(paramPmtInfo.getImmediateFundAllocation());
    String str2 = a(paramPmtInfo.getImmediateProcessing());
    String str3 = "INSERT INTO BPW_PmtInstruction(SrvrTID, CustomerID, PayeeID, PayeeListID, PayAcct, RouteID, AcctDebitID, AcctDebitType, Amount, BankID, DateCreate, CurDef, StartDate, Status,Memo, PaymentName, PaymentType, SubmittedBy, LogID, FIID, BatchID, ImmediateFundAllocation, ImmediateProcessing ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramPmtInfo.SrvrTID, paramPmtInfo.CustomerID, paramPmtInfo.PayeeID, new Integer(paramPmtInfo.PayeeListID), paramPmtInfo.PayAcct, new Integer(0), paramPmtInfo.AcctDebitID, paramPmtInfo.AcctDebitType, FFSUtil.formatToScale(paramPmtInfo.getAmt()), paramPmtInfo.BankID, paramPmtInfo.OriginatedDate, paramPmtInfo.CurDef, new Integer(0), paramPmtInfo.Status, paramPmtInfo.Memo, paramPmtInfo.paymentName, paramPmtInfo.PaymentType, paramPmtInfo.submittedBy, paramPmtInfo.LogID, paramPmtInfo.FIID, paramPmtInfo.batchID, str1, str2 };
    DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
    return paramPmtInfo;
  }
  
  public static PmtInfo updatePayment(PmtInfo paramPmtInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    paramPmtInfo.lastModified = FFSUtil.getDateString("yyyyMMddHHmmss");
    String str1 = a(paramPmtInfo.getImmediateFundAllocation());
    String str2 = a(paramPmtInfo.getImmediateProcessing());
    String str3 = "UPDATE BPW_PmtInstruction SET CustomerID=?,PayeeID=?, PayeeListID=?, AcctDebitID=?, AcctDebitType=?, Amount=?,BankID=?,Memo=?,PaymentName=?, SubmittedBy=?, LastModified=?, ImmediateFundAllocation=?, ImmediateProcessing=?, StartDate=?, Status=? WHERE SrvrTID=?";
    Object[] arrayOfObject = { paramPmtInfo.CustomerID, paramPmtInfo.PayeeID, new Integer(paramPmtInfo.PayeeListID), paramPmtInfo.AcctDebitID, paramPmtInfo.AcctDebitType, FFSUtil.formatToScale(paramPmtInfo.getAmt()), paramPmtInfo.BankID, paramPmtInfo.Memo, paramPmtInfo.paymentName, paramPmtInfo.submittedBy, paramPmtInfo.lastModified, str1, str2, new Integer(paramPmtInfo.StartDate), paramPmtInfo.Status, paramPmtInfo.SrvrTID };
    DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
    return paramPmtInfo;
  }
  
  public static PmtInfo getPayment(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    PmtInfo localPmtInfo = null;
    String str = "SELECT CustomerID, PayeeID, PayeeListID, AcctDebitID, AcctDebitType, Amount, BankID, DateCreate, StartDate, Memo, PaymentName, SubmittedBy, LogID, FIID, ImmediateFundAllocation, ImmediateProcessing FROM BPW_PmtInstruction WHERE SrvrTID=?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
    if (localFFSResultSet.getNextRow())
    {
      localPmtInfo = new PmtInfo();
      localPmtInfo.SrvrTID = paramString;
      localPmtInfo.CustomerID = localFFSResultSet.getColumnString("CustomerID");
      localPmtInfo.PayeeID = localFFSResultSet.getColumnString("PayeeID");
      localPmtInfo.PayeeListID = localFFSResultSet.getColumnInt("PayeeListID");
      localPmtInfo.AcctDebitID = localFFSResultSet.getColumnString("AcctDebitID");
      localPmtInfo.AcctDebitType = localFFSResultSet.getColumnString("AcctDebitType");
      localPmtInfo.setAmt(localFFSResultSet.getColumnString("Amount"));
      localPmtInfo.BankID = localFFSResultSet.getColumnString("BankID");
      localPmtInfo.OriginatedDate = localFFSResultSet.getColumnString("DateCreate");
      localPmtInfo.StartDate = localFFSResultSet.getColumnInt("StartDate");
      localPmtInfo.Memo = localFFSResultSet.getColumnString("Memo");
      localPmtInfo.paymentName = localFFSResultSet.getColumnString("PaymentName");
      localPmtInfo.submittedBy = localFFSResultSet.getColumnString("SubmittedBy");
      localPmtInfo.LogID = localFFSResultSet.getColumnString("LogID");
      localPmtInfo.FIID = localFFSResultSet.getColumnString("FIID");
      localPmtInfo.setImmediateFundAllocation(ag(localFFSResultSet.getColumnString("ImmediateFundAllocation")));
      localPmtInfo.setImmediateProcessing(ag(localFFSResultSet.getColumnString("ImmediateProcessing")));
    }
    return localPmtInfo;
  }
  
  public static ArrayList getPaymentsByBatchId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    String str1 = "PmtInstruction.getPaymentsByBatchId: ";
    FFSDebug.log(str1 + "start, BatchId=" + paramString, 6);
    String str2 = "SELECT CustomerID,PayeeID,PayeeListID, BankID, AcctDebitID,AcctDebitType, PayAcct,DateCreate, CurDef, Amount, RouteID, StartDate, Status, LogID, Memo, PaymentType, RecSrvrTID, FIID, LastModified, SubmittedBy, SrvrTID, ImmediateFundAllocation, ImmediateProcessing FROM BPW_PmtInstruction WHERE BatchID=?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
    ArrayList localArrayList = new ArrayList();
    while (localFFSResultSet.getNextRow())
    {
      PmtInfo localPmtInfo = new PmtInfo();
      localPmtInfo.SrvrTID = localFFSResultSet.getColumnString("SrvrTID");
      localPmtInfo.CustomerID = localFFSResultSet.getColumnString("CustomerID");
      localPmtInfo.PayeeID = localFFSResultSet.getColumnString("PayeeID");
      localPmtInfo.PayeeListID = localFFSResultSet.getColumnInt("PayeeListID");
      localPmtInfo.BankID = localFFSResultSet.getColumnString("BankID");
      localPmtInfo.AcctDebitID = localFFSResultSet.getColumnString("AcctDebitID");
      localPmtInfo.AcctDebitType = localFFSResultSet.getColumnString("AcctDebitType");
      localPmtInfo.PayAcct = localFFSResultSet.getColumnString("PayAcct");
      localPmtInfo.OriginatedDate = localFFSResultSet.getColumnString("DateCreate");
      localPmtInfo.CurDef = localFFSResultSet.getColumnString("CurDef");
      localPmtInfo.setAmt(localFFSResultSet.getColumnString("Amount"));
      localPmtInfo.setRouteId(localFFSResultSet.getColumnInt("RouteID"));
      localPmtInfo.StartDate = localFFSResultSet.getColumnInt("StartDate");
      localPmtInfo.Status = localFFSResultSet.getColumnString("Status");
      localPmtInfo.LogID = localFFSResultSet.getColumnString("LogID");
      localPmtInfo.Memo = localFFSResultSet.getColumnString("Memo");
      localPmtInfo.PaymentType = localFFSResultSet.getColumnString("PaymentType");
      localPmtInfo.RecSrvrTID = localFFSResultSet.getColumnString("RecSrvrTID");
      localPmtInfo.FIID = localFFSResultSet.getColumnString("FIID");
      localPmtInfo.lastModified = localFFSResultSet.getColumnString("LastModified");
      localPmtInfo.submittedBy = localFFSResultSet.getColumnString("SubmittedBy");
      localPmtInfo.setImmediateFundAllocation(ag(localFFSResultSet.getColumnString("ImmediateFundAllocation")));
      localPmtInfo.setImmediateProcessing(ag(localFFSResultSet.getColumnString("ImmediateProcessing")));
      localArrayList.add(localPmtInfo);
    }
    return localArrayList;
  }
  
  public static PmtInfo getLastPaymentByPayeeId(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt)
    throws Exception
  {
    String str1 = "PmtInstruction.getLastPaymentByPayeeId: ";
    FFSDebug.log(str1 + "start, fiid=" + paramString1 + "customerId=" + paramString2 + "payeeListId=" + paramInt, 6);
    ArrayList localArrayList = new ArrayList();
    PmtInfo localPmtInfo1 = null;
    PmtInfo localPmtInfo2 = null;
    String str2 = "SELECT CustomerID, PayeeID, PayeeListID, BankID, AcctDebitID, AcctDebitType, PayAcct, DateCreate, CurDef, Amount, StartDate, Status, LogID, Memo, PaymentType, SrvrTID, FIID, LastModified, SubmittedBy, ImmediateFundAllocation, ImmediateProcessing FROM BPW_PmtInstruction WHERE FIID = ? AND CustomerID = ? AND PayeeListID = ? AND Status IN (?, ?) ORDER BY StartDate DESC";
    Object[] arrayOfObject = { paramString1, paramString2, new Integer(paramInt), "PROCESSEDON", "FAILEDON" };
    int i = 0;
    int j = 1;
    FFSResultSet localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
    try
    {
      while (localFFSResultSet.getNextRow())
      {
        localPmtInfo1 = new PmtInfo();
        localPmtInfo1.CustomerID = localFFSResultSet.getColumnString("CustomerID");
        localPmtInfo1.PayeeID = localFFSResultSet.getColumnString("PayeeID");
        localPmtInfo1.PayeeListID = localFFSResultSet.getColumnInt("PayeeListID");
        localPmtInfo1.BankID = localFFSResultSet.getColumnString("BankID");
        localPmtInfo1.AcctDebitID = localFFSResultSet.getColumnString("AcctDebitID");
        localPmtInfo1.AcctDebitType = localFFSResultSet.getColumnString("AcctDebitType");
        localPmtInfo1.PayAcct = localFFSResultSet.getColumnString("PayAcct");
        localPmtInfo1.OriginatedDate = localFFSResultSet.getColumnString("DateCreate");
        localPmtInfo1.CurDef = localFFSResultSet.getColumnString("CurDef");
        localPmtInfo1.setAmt(localFFSResultSet.getColumnString("Amount"));
        localPmtInfo1.StartDate = localFFSResultSet.getColumnInt("StartDate");
        localPmtInfo1.Status = localFFSResultSet.getColumnString("Status");
        localPmtInfo1.LogID = localFFSResultSet.getColumnString("LogID");
        localPmtInfo1.Memo = localFFSResultSet.getColumnString("Memo");
        localPmtInfo1.PaymentType = localFFSResultSet.getColumnString("PaymentType");
        localPmtInfo1.SrvrTID = localFFSResultSet.getColumnString("SrvrTID");
        localPmtInfo1.FIID = localFFSResultSet.getColumnString("FIID");
        localPmtInfo1.lastModified = localFFSResultSet.getColumnString("LastModified");
        localPmtInfo1.submittedBy = localFFSResultSet.getColumnString("SubmittedBy");
        localPmtInfo1.setImmediateFundAllocation(ag(localFFSResultSet.getColumnString("ImmediateFundAllocation")));
        localPmtInfo1.setImmediateProcessing(ag(localFFSResultSet.getColumnString("ImmediateProcessing")));
        if (j != 0)
        {
          i = localPmtInfo1.StartDate;
          j = 0;
        }
        if (i != localPmtInfo1.StartDate) {
          break;
        }
        localArrayList.add(localPmtInfo1);
      }
      if (localArrayList.size() > 1)
      {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        Object localObject1 = null;
        int k = 0;
        for (int m = 0; m < localArrayList.size(); m++)
        {
          String str3 = ((PmtInfo)localArrayList.get(m)).OriginatedDate;
          Date localDate = localSimpleDateFormat.parse(str3);
          if ((localObject1 == null) || (localDate.compareTo((Date)localObject1) < 0))
          {
            localObject1 = localDate;
            k = m;
          }
        }
        localPmtInfo2 = (PmtInfo)localArrayList.get(k);
      }
      else if (localArrayList.size() == 1)
      {
        localPmtInfo2 = (PmtInfo)localArrayList.get(0);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("***" + str1 + "failed: " + localException1.toString(), 0);
      throw new Exception(localException1.toString() + FFSDebug.stackTrace(localException1));
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
        FFSDebug.log("*** PmtInstruction getLastPaymentByPayeeId failed:" + localException2.toString(), 0);
      }
    }
    FFSDebug.log("PmtInstruction.getLastPaymentByPayeeId done, fiid=" + paramString1 + "customerId=" + paramString2 + "payeeListId=" + paramInt, 6);
    return localPmtInfo2;
  }
  
  public static int deleteBatch(FFSConnectionHolder paramFFSConnectionHolder, String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("PmtInstruction.deleteBatch start.", 6);
    String str1 = "DELETE FROM BPW_PmtInstruction WHERE SrvrTID=?";
    try
    {
      DBUtil.executeStatementBatch(paramFFSConnectionHolder, str1, DBUtil.arrayStringToArrayList(paramArrayOfString));
    }
    catch (Exception localException)
    {
      String str2 = "*** PmtInstr.deleteBatch failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("PmtInstruction.deleteBatch done. Deleted " + paramArrayOfString.length + " payments.", 6);
    return paramArrayOfString.length;
  }
  
  public static int updateStatusLastModified(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.updateStatusLastModified start, srvrTID=" + paramString1, 6);
    int i = -1;
    try
    {
      String str = "UPDATE BPW_PmtInstruction SET Status=?, LastModified=? WHERE SrvrTID=?";
      localObject = new Object[] { paramString2, paramString3, paramString1 };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str, (Object[])localObject);
    }
    catch (Exception localException)
    {
      Object localObject = "*** PmtInstruction.updateStatusLastModified failed:" + localException.toString();
      FFSDebug.log((String)localObject);
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("PmtInstruction.updateStatusLastModified done, srvrTID=" + paramString1, 6);
    return i;
  }
  
  public static int updateStatusLastModifiedByBatchId(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.updateStatusLastModifiedByBatchId start, batchId=" + paramString1, 6);
    int i = -1;
    try
    {
      String str = "UPDATE BPW_PmtInstruction SET Status=?, LastModified=? WHERE BatchID=?";
      localObject = new Object[] { paramString2, FFSUtil.getDateString("yyyyMMddHHmmss"), paramString1 };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str, (Object[])localObject);
    }
    catch (Exception localException)
    {
      Object localObject = "*** PmtInstruction.updateStatusLastModifiedByBatchId failed:" + localException.toString();
      FFSDebug.log((String)localObject);
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("PmtInstruction.updateStatusLastModifiedByBatchId done, batchId=" + paramString1, 6);
    return i;
  }
  
  public static PmtInfo getRecModelMostRecentInstance(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    FFSDebug.log("PmtInstruction.getRecModelMostRecentInstance start, recSrvrTId=" + paramString, 6);
    PmtInfo localPmtInfo = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      String str = "SELECT SrvrTID, StartDate FROM BPW_PmtInstruction WHERE RecSrvrTID = ? AND Status IN (?, ?, ?, ?, ?) ORDER BY StartDate DESC";
      localObject1 = new Object[] { paramString, "WILLPROCESSON", "INFUNDSALLOC", "FUNDSALLOCATED", "APPROVAL_PENDING", "APPROVAL_REJECTED" };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, (Object[])localObject1);
      if (localFFSResultSet.getNextRow())
      {
        localPmtInfo = new PmtInfo();
        localPmtInfo.SrvrTID = localFFSResultSet.getColumnString("SrvrTID");
        localPmtInfo.StartDate = localFFSResultSet.getColumnInt("StartDate");
      }
    }
    catch (Exception localException1)
    {
      Object localObject1 = "*** PmtInstruction.getRecModelNextInstance failed:" + localException1.toString();
      FFSDebug.log((String)localObject1);
      throw new BPWException(localException1, (String)localObject1);
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
        FFSDebug.log("*** PmtInstruction getRecModelNextInstance failed:" + localException2.toString(), 0);
      }
    }
    FFSDebug.log("PmtInstruction.getRecModelMostRecentInstance done, recSrvrTId=" + paramString, 6);
    return localPmtInfo;
  }
  
  private static Boolean ag(String paramString)
  {
    Boolean localBoolean = null;
    if (paramString != null) {
      if (paramString.trim().equalsIgnoreCase("Y")) {
        localBoolean = new Boolean(true);
      } else if (paramString.trim().equalsIgnoreCase("N")) {
        localBoolean = new Boolean(false);
      }
    }
    return localBoolean;
  }
  
  private static String a(Boolean paramBoolean)
  {
    String str = null;
    if (paramBoolean != null) {
      str = paramBoolean.booleanValue() ? new String("Y") : new String("N");
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.PmtInstruction
 * JD-Core Version:    0.7.0.1
 */