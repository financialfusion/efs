package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.IntraTrnRslt;
import com.ffusion.ffs.bpw.serviceMsg.BPWMsgBroker;
import com.ffusion.ffs.bpw.serviceMsg.RsCmBuilder;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBCCAcctFromV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeXferInfoV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBCCAcctFromUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRsCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferInfoAggregate;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class SrvrTrans
  implements FFSConst, DBConsts, BPWResource
{
  private String xJ;
  private String xB;
  private String xK;
  private String xL;
  private String xD;
  private String xF;
  private String xz;
  private int xy;
  private String xG;
  private String xI;
  private String xC;
  private String xA;
  private String xH;
  private String xE;
  
  public void setCheckNum(String paramString)
  {
    this.xE = paramString;
  }
  
  public String getCheckNum()
  {
    return this.xE;
  }
  
  public void setTransFor(String paramString)
  {
    this.xH = paramString;
  }
  
  public String getTransFor()
  {
    return this.xH;
  }
  
  public void setMemo(String paramString)
  {
    this.xA = paramString;
  }
  
  public String getMemo()
  {
    return this.xA;
  }
  
  public void setDtProcessed(String paramString)
  {
    this.xC = paramString;
  }
  
  public String getDtProcessed()
  {
    return this.xC;
  }
  
  public void setSrvrTID(String paramString)
  {
    this.xJ = paramString;
  }
  
  public String getSrvrTID()
  {
    return this.xJ;
  }
  
  public void setSyncType(String paramString)
  {
    this.xK = paramString;
  }
  
  public String getSyncType()
  {
    return this.xK;
  }
  
  public void setBankID(String paramString)
  {
    this.xL = paramString;
  }
  
  public String getBankID()
  {
    return this.xL;
  }
  
  public void setAcctID(String paramString)
  {
    this.xD = paramString;
  }
  
  public String getAcctID()
  {
    return this.xD;
  }
  
  public void setAcctType(String paramString)
  {
    this.xF = paramString;
  }
  
  public String getAcctType()
  {
    return this.xF;
  }
  
  public void setStatus(String paramString)
  {
    this.xI = paramString;
  }
  
  public String getStatus()
  {
    return this.xI;
  }
  
  public void setResponse(String paramString)
  {
    this.xz = paramString;
  }
  
  public String getResponse()
  {
    return this.xz;
  }
  
  public void setSubmitdate(String paramString)
  {
    this.xG = paramString;
  }
  
  public String getSubmitdate()
  {
    return this.xG;
  }
  
  public void setCustomerID(String paramString)
  {
    this.xB = paramString;
  }
  
  public String getCustomerID()
  {
    return this.xB;
  }
  
  public static String[] findResponseBySrvrTID(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("SrvrTrans.findResponseBySrvrTID start, srvrTID=" + paramString, 6);
    String[] arrayOfString = new String[2];
    String str = "SELECT SyncType,Response FROM BPW_SrvrTrans WHERE SrvrTID=?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        arrayOfString[0] = localFFSResultSet.getColumnString(1);
        arrayOfString[1] = localFFSResultSet.getColumnString(2);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** SrvrTrans.findResponseBySrvrTID failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** SrvrTrans.findResponseBySrvrTID  failed:" + localException2.toString());
      }
    }
    FFSDebug.log("SrvrTrans.findResponseBySrvrTID done, srvrTID=" + paramString, 6);
    return arrayOfString;
  }
  
  public void updateResponseBySrvrTID(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder, String paramString3)
    throws BPWException
  {
    FFSDebug.log("SrvrTrans.updateResponseBySrvrTID start, srvrTID=" + paramString2, 6);
    String str1 = FFSUtil.getDateString();
    String str2 = "UPDATE BPW_SrvrTrans SET Response=?, Submitdate=?, Status=?, DtProcessed=?, Memo=?, TransFor=?, CheckNum =? WHERE SrvrTID=?";
    try
    {
      byte[] arrayOfByte = paramString1.getBytes("UTF-8");
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
      int i = arrayOfByte.length;
      Object[] arrayOfObject = { paramString1, str1, paramString3, this.xC, this.xA, this.xH, this.xE, paramString2 };
      int j = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject, i);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** SrvrTrans.updateResponseBySrvrTID failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("SrvrTrans.updateResponseBySrvrTID done, srvrTID=" + paramString2, 6);
  }
  
  public void updatePmtStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws BPWException
  {
    FFSDebug.log("SrvrTrans.updatePmtStatus start, srvrTID=" + paramString2, 6);
    String str = "UPDATE BPW_SrvrTrans SET Response=?, Submitdate=?, Status=?, DtProcessed=?, Memo=?, TransFor=?, CheckNum =? WHERE SrvrTID=?";
    try
    {
      byte[] arrayOfByte = paramString1.getBytes("UTF-8");
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
      int i = arrayOfByte.length;
      Object[] arrayOfObject = { paramString1, paramString3, paramString2 };
      int j = DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject, i);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** SrvrTrans.updatePmtStatus failed:" + FFSDebug.stackTrace(localException));
      throw new BPWException(FFSDebug.stackTrace(localException));
    }
    FFSDebug.log("SrvrTrans.updatePmtStatus done, srvrTID=" + paramString2, 6);
  }
  
  public static String[] getSrvrTrans(String paramString1, String paramString2, String paramString3, String paramString4)
    throws BPWException
  {
    FFSDebug.log("SrvrTrans.getSrvrTrans start, acctID=" + paramString2, 6);
    String[] arrayOfString = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str = "SELECT Response FROM BPW_SrvrTrans WHERE CustomerID=? AND AcctID=? AND AcctType=? AND SyncType=?";
    Object[] arrayOfObject = { paramString1, paramString2, paramString3, paramString4 };
    Vector localVector = new Vector();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        localVector.addElement(localFFSResultSet.getColumnString(1));
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log(localException1, "*** SrvrTrans.getTrans failed:" + localException1.toString());
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
        FFSDebug.log("*** SrvrTrans.getTrans failed:" + FFSDebug.stackTrace(localException2), 0);
      }
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    arrayOfString = (String[])localVector.toArray(new String[0]);
    FFSDebug.log("SrvrTrans.getSrvrTrans done, acctID=" + paramString2, 6);
    return arrayOfString;
  }
  
  public static String[] getSrvrTransWithBank(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws BPWException
  {
    FFSDebug.log("SrvrTrans.getSrvrTransWithBank start, acctID=" + paramString2, 6);
    FFSDebug.log("SrvrTrans.getSrvrTransWithBank  bankId=" + paramString5, 6);
    String[] arrayOfString = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str = "SELECT Response FROM BPW_SrvrTrans WHERE CustomerID=? AND AcctID=? AND AcctType=? AND SyncType=? AND BankID=? ";
    Object[] arrayOfObject = { paramString1, paramString2, paramString3, paramString4, paramString5 };
    Vector localVector = new Vector();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        localVector.addElement(localFFSResultSet.getColumnString(1));
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log(localException1, "*** SrvrTrans.getSrvrTransWithBank failed:" + localException1.toString());
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
        FFSDebug.log("*** SrvrTrans.getSrvrTransWithBank failed:" + FFSDebug.stackTrace(localException2), 0);
      }
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    arrayOfString = (String[])localVector.toArray(new String[0]);
    FFSDebug.log("SrvrTrans.getSrvrTransWithBank done, acctID=" + paramString2, 6);
    return arrayOfString;
  }
  
  public static String[] getSrvrTrans(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws BPWException
  {
    FFSDebug.log("SrvrTrans.getSrvrTrans start, acctID=" + paramString2, 6);
    String[] arrayOfString = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str = "SELECT Response FROM BPW_SrvrTrans WHERE CustomerID=? AND AcctID=? AND AcctType=? AND SyncType=? AND Status=?";
    Object[] arrayOfObject = { paramString1, paramString2, paramString3, paramString4, paramString5 };
    Vector localVector = new Vector();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        localVector.addElement(localFFSResultSet.getColumnString(1));
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log(localException1, "*** SrvrTrans.getTrans failed:" + localException1.toString());
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
        FFSDebug.log("*** SrvrTrans.getTrans failed:" + FFSDebug.stackTrace(localException2), 0);
      }
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    arrayOfString = (String[])localVector.toArray(new String[0]);
    FFSDebug.log("SrvrTrans.getSrvrTrans done, acctID=" + paramString2, 6);
    return arrayOfString;
  }
  
  public void updateSrvrTrans(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("SrvrTrans.updateSrvrTrans start, SrvrTID=" + this.xJ, 6);
    String str1 = FFSUtil.getDateString();
    String str2 = "UPDATE BPW_SrvrTrans SET BankID=?, AcctID=?, AcctType=?, Response=?, Submitdate=?, Status=?, DtProcessed=?, Memo=?, TransFor=?, CheckNum =? WHERE SrvrTID=?";
    try
    {
      byte[] arrayOfByte = this.xz.getBytes("UTF-8");
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
      int i = arrayOfByte.length;
      Object[] arrayOfObject = { this.xL, this.xD, this.xF, this.xz, str1, this.xI, this.xC, this.xA, this.xH, this.xE, this.xJ };
      int j = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject, i);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** SrvrTrans.updateSrvrTrans failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("SrvrTrans.updateSrvrTrans done, SrvrTID=" + this.xJ, 6);
  }
  
  public static void cancelSrvrTrans(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("SrvrTrans.cancelSrvrTrans start, srvrTID=" + paramString, 6);
    String str = "DELETE FROM BPW_SrvrTrans WHERE SrvrTID=?";
    Object[] arrayOfObject = { paramString };
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** SrvrTrans.cancelTrans failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("SrvrTrans.cancelSrvrTrans done, srvrTID=" + paramString, 6);
  }
  
  public void storeToDB(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("SrvrTrans.storeToDB start, srvrTID=" + this.xJ, 6);
    String str = "INSERT INTO BPW_SrvrTrans( SrvrTID, BankID, AcctID, AcctType, CustomerID, Response, Token,Submitdate, SyncType, Status, DtProcessed, Memo, TransFor, CheckNum) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    try
    {
      byte[] arrayOfByte = this.xz.getBytes("UTF-8");
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
      int i = arrayOfByte.length;
      Object[] arrayOfObject = { this.xJ, this.xL, this.xD, this.xF, this.xB, this.xz, new Integer(0), this.xG, this.xK, this.xI, this.xC, this.xA, this.xH, this.xE };
      int j = DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject, i);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** SrvrTrans.storeToDB failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("SrvrTrans.storeToDB done, srvrTID=" + this.xJ, 6);
  }
  
  public static SrvrTrans[] getAcctByCustomerID(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("SrvrTrans.getAcctByCustomerID start, custID=" + paramString1, 6);
    SrvrTrans[] arrayOfSrvrTrans = null;
    String str = "SELECT DISTINCT AcctID, AcctType FROM BPW_SrvrTrans WHERE CustomerID=? AND SyncType=? ";
    Object[] arrayOfObject = { paramString1, paramString2 };
    ArrayList localArrayList = new ArrayList();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        SrvrTrans localSrvrTrans = new SrvrTrans();
        localSrvrTrans.xD = localFFSResultSet.getColumnString(1);
        localSrvrTrans.xF = localFFSResultSet.getColumnString(2);
        localSrvrTrans.xB = paramString1;
        localSrvrTrans.xK = paramString2;
        localArrayList.add(localSrvrTrans);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log(localException1, "*** SrvrTrans.getAcctByCustomerID failed:" + localException1.toString());
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
        FFSDebug.log("*** SrvrTrans.getAcctByCustomerID failed:" + localException2.toString());
      }
    }
    arrayOfSrvrTrans = (SrvrTrans[])localArrayList.toArray(new SrvrTrans[0]);
    FFSDebug.log("SrvrTrans.getAcctByCustomerID done, rows=" + arrayOfSrvrTrans.length, 6);
    return arrayOfSrvrTrans;
  }
  
  public static void updatePmtStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws Exception
  {
    FFSDebug.log("updatePmtStatus starts. srvrTID; " + paramString1 + ", status: " + paramString2, 6);
    if ((paramString1 == null) || (paramString2 == null))
    {
      FFSDebug.log("updatePmtStatus failed. srvrTID; " + paramString1 + ", status: " + paramString2, 0);
      return;
    }
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String str1 = localSimpleDateFormat.format(new Date());
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String[] arrayOfString = findResponseBySrvrTID(paramString1, paramFFSConnectionHolder);
    String str2 = arrayOfString[1];
    Object localObject;
    String str3;
    if (arrayOfString[0].startsWith("OFX151"))
    {
      localObject = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(str2, "PmtTrnRsV1", "OFX151");
      if (localObject != null)
      {
        if ("WILLPROCESSON".equalsIgnoreCase(paramString2))
        {
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum.WILLPROCESSON;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsV1Cm.Status.Code = 0;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsV1Cm.Status.Severity = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumSeverityEnum.INFO;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsV1Cm.Status.Message = null;
        }
        else if ("PROCESSEDON".equalsIgnoreCase(paramString2))
        {
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum.PROCESSEDON;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsV1Cm.Status.Code = 0;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsV1Cm.Status.Severity = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumSeverityEnum.INFO;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsV1Cm.Status.Message = null;
        }
        else if ("NOFUNDSON".equalsIgnoreCase(paramString2))
        {
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum.NOFUNDSON;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsV1Cm.Status.Code = 10504;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsV1Cm.Status.Severity = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumSeverityEnum.ERROR;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsV1Cm.Status.Message = BPWLocaleUtil.getMessage(10504, null, "OFX_MESSAGE");
        }
        else if ("FUNDSFAILEDON".equalsIgnoreCase(paramString2))
        {
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum.FAILEDON;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsV1Cm.Status.Code = 2000;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsV1Cm.Status.Severity = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumSeverityEnum.ERROR;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsV1Cm.Status.Message = BPWLocaleUtil.getMessage(17001, null, "OFX_MESSAGE");
        }
        else if ("FAILEDON".equalsIgnoreCase(paramString2))
        {
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum.FAILEDON;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsV1Cm.Status.Code = 2000;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsV1Cm.Status.Severity = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumSeverityEnum.ERROR;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsV1Cm.Status.Message = BPWLocaleUtil.getMessage(2000, null, "OFX_MESSAGE");
        }
        else
        {
          throw new BPWException("Unsupported payment status: " + paramString2);
        }
      }
      else {
        throw new BPWException("No response found! srvrTID: " + paramString1);
      }
      str3 = localBPWMsgBroker.buildMsg(localObject, "PmtTrnRsV1", "OFX151");
      updatePmtResp(paramFFSConnectionHolder, str3, paramString1, paramString2);
    }
    else if (arrayOfString[0].startsWith("OFX200"))
    {
      localObject = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(str2, "PmtTrnRsV1", "OFX200");
      if (localObject != null)
      {
        if ("WILLPROCESSON".equalsIgnoreCase(paramString2))
        {
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum.WILLPROCESSON;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsCm.Status.Code = 0;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsCm.Status.Severity = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumSeverityEnum.INFO;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsCm.Status.Message = null;
        }
        else if ("PROCESSEDON".equalsIgnoreCase(paramString2))
        {
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum.PROCESSEDON;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsCm.Status.Code = 0;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsCm.Status.Severity = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumSeverityEnum.INFO;
        }
        else if ("NOFUNDSON".equalsIgnoreCase(paramString2))
        {
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum.NOFUNDSON;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsCm.Status.Code = 10504;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsCm.Status.Severity = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumSeverityEnum.INFO;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsCm.Status.Message = BPWLocaleUtil.getMessage(10504, null, "OFX_MESSAGE");
        }
        else if ("FUNDSFAILEDON".equalsIgnoreCase(paramString2))
        {
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum.FAILEDON;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsCm.Status.Code = 2000;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsCm.Status.Severity = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumSeverityEnum.INFO;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsCm.Status.Message = BPWLocaleUtil.getMessage(17001, null, "OFX_MESSAGE");
        }
        else if ("FAILEDON".equalsIgnoreCase(paramString2))
        {
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum.FAILEDON;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsCm.Status.Code = 2000;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsCm.Status.Severity = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumSeverityEnum.INFO;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.TrnRsCm.Status.Message = BPWLocaleUtil.getMessage(2000, null, "OFX_MESSAGE");
        }
        else
        {
          throw new BPWException("Unsupported payment status: " + paramString2);
        }
      }
      else {
        throw new BPWException("No response found! srvrTID: " + paramString1);
      }
      str3 = localBPWMsgBroker.buildMsg(localObject, "PmtTrnRsV1", "OFX200");
      updatePmtResp(paramFFSConnectionHolder, str3, paramString1, paramString2);
    }
    FFSDebug.log("updatePmtStatus ends. srvrTID; " + paramString1 + ", status: " + paramString2, 6);
  }
  
  public static void updatePmtResp(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws BPWException
  {
    FFSDebug.log("SrvrTrans.updatePmtResp start, srvrTID=" + paramString2, 6);
    String str1 = FFSUtil.getDateString();
    String str2 = "UPDATE BPW_SrvrTrans SET Response=?, Status=?, Submitdate=? WHERE SrvrTID=?";
    try
    {
      byte[] arrayOfByte = paramString1.getBytes("UTF-8");
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
      int i = arrayOfByte.length;
      Object[] arrayOfObject = { paramString1, paramString3, str1, paramString2 };
      int j = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject, i);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** SrvrTrans.updatePmtResp failed:" + FFSDebug.stackTrace(localException));
      throw new BPWException(FFSDebug.stackTrace(localException));
    }
    FFSDebug.log("SrvrTrans.updatePmtResp done, srvrTID=" + paramString2, 6);
  }
  
  public static String[] findTransDtBySrvrTID(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("SrvrTrans.findTransDtBySrvrTID start, srvrTID=" + paramString, 6);
    String[] arrayOfString = new String[2];
    String str = "SELECT DtProcessed,Submitdate FROM BPW_SrvrTrans WHERE SrvrTID=?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        arrayOfString[0] = localFFSResultSet.getColumnString(1);
        arrayOfString[1] = localFFSResultSet.getColumnString(2);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** SrvrTrans.findTransDtBySrvrTID failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** SrvrTrans.findTransDtBySrvrTID  failed:" + localException2.toString());
      }
    }
    FFSDebug.log("SrvrTrans.findTransDtBySrvrTID done, srvrTID=" + paramString, 6);
    return arrayOfString;
  }
  
  public static int deleteBatch(FFSConnectionHolder paramFFSConnectionHolder, String[] paramArrayOfString)
    throws BPWException
  {
    FFSDebug.log("SrvrTrans.deleteBatch start.", 6);
    String str = "DELETE FROM BPW_SrvrTrans WHERE SrvrTID=?";
    try
    {
      DBUtil.executeStatementBatch(paramFFSConnectionHolder, str, DBUtil.arrayStringToArrayList(paramArrayOfString));
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** SrvrTrans.deleteBatch failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("SrvrTrans.deleteBatch done. No of rows = " + paramArrayOfString.length, 6);
    return paramArrayOfString.length;
  }
  
  public static boolean updateTransferStatus(FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean, String paramString1, String paramString2, IntraTrnRslt paramIntraTrnRslt)
    throws BPWException
  {
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("SrvrTrans.updateTransferStatus: AuditAgent is null!!");
    }
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String[] arrayOfString = findResponseBySrvrTID(paramIntraTrnRslt.srvrTid, paramFFSConnectionHolder);
    if ((arrayOfString[0] == null) || (arrayOfString[0].trim().equals("")))
    {
      FFSDebug.log("SrvrTrans.updateTransferStatus: call, SrvrTID " + paramIntraTrnRslt.srvrTid + " not found!", 0);
      return false;
    }
    if ((arrayOfString[1] == null) || (arrayOfString[1].trim().equals("")))
    {
      FFSDebug.log("SrvrTrans.updateTransferStatus: call, SrvrTrans response not found for SrvrTID=" + paramIntraTrnRslt.srvrTid, 0);
      return false;
    }
    String str = arrayOfString[1];
    Object localObject;
    if (arrayOfString[0].startsWith("OFX151"))
    {
      localObject = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1)localBPWMsgBroker.parseMsg(str, "IntraTrnRsV1", "OFX151");
      if (localObject != null)
      {
        RsCmBuilder.updateRsXferPrcSts(paramString2, paramString1, ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1)localObject).IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferPrcSts);
        RsCmBuilder.updateStatusV1Aggregate(((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1)localObject).IntraTrnRs.TrnRsV1Cm.Status, paramIntraTrnRslt.status);
        if (paramIntraTrnRslt.confirmationNum != null) {
          if (((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1)localObject).IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferInfo.BCCAcctFromV1Un.__memberName.equals("CCAcctFrom"))
          {
            ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1)localObject).IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferInfo.BCCAcctFromV1Un.CCAcctFrom.AcctKey = paramIntraTrnRslt.confirmationNum;
            ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1)localObject).IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferInfo.BCCAcctFromV1Un.CCAcctFrom.AcctKeyExists = true;
          }
          else
          {
            ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1)localObject).IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferInfo.BCCAcctFromV1Un.BankAcctFrom.AcctKey = paramIntraTrnRslt.confirmationNum;
            ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1)localObject).IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferInfo.BCCAcctFromV1Un.BankAcctFrom.AcctKeyExists = true;
          }
        }
        if (paramBoolean == true) {
          RsCmBuilder.updateXferRsDatePosted(paramString1, ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1)localObject).IntraTrnRs.IntraTrnRsV1Un.IntraRs);
        }
        localAuditAgent.updateXferRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1)localObject, paramFFSConnectionHolder, paramString2);
      }
      else
      {
        FFSDebug.log("SrvrTrans.updateTransferStatus: call, SrvrTrans response not found for SrvrTID=" + paramIntraTrnRslt.srvrTid, 0);
        throw new BPWException("No response found!");
      }
    }
    else if (arrayOfString[0].startsWith("OFX200"))
    {
      localObject = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1)localBPWMsgBroker.parseMsg(str, "IntraTrnRsV1", "OFX200");
      if (localObject != null)
      {
        RsCmBuilder.updateRsXferPrcSts(paramString2, paramString1, ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1)localObject).IntraTrnRs.IntraTrnRsUn.IntraRs.XferPrcSts);
        RsCmBuilder.updateStatusV1Aggregate(((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1)localObject).IntraTrnRs.TrnRsCm.Status, paramIntraTrnRslt.status);
        if (paramIntraTrnRslt.confirmationNum != null) {
          if (((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1)localObject).IntraTrnRs.IntraTrnRsUn.IntraRs.XferInfo.BCCAcctFromUn.__memberName.equals("CCAcctFrom"))
          {
            ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1)localObject).IntraTrnRs.IntraTrnRsUn.IntraRs.XferInfo.BCCAcctFromUn.CCAcctFrom.AcctKey = paramIntraTrnRslt.confirmationNum;
            ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1)localObject).IntraTrnRs.IntraTrnRsUn.IntraRs.XferInfo.BCCAcctFromUn.CCAcctFrom.AcctKeyExists = true;
          }
          else
          {
            ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1)localObject).IntraTrnRs.IntraTrnRsUn.IntraRs.XferInfo.BCCAcctFromUn.BankAcctFrom.AcctKey = paramIntraTrnRslt.confirmationNum;
            ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1)localObject).IntraTrnRs.IntraTrnRsUn.IntraRs.XferInfo.BCCAcctFromUn.BankAcctFrom.AcctKeyExists = true;
          }
        }
        if (paramBoolean == true) {
          RsCmBuilder.updateXferRsDatePosted(paramString1, ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1)localObject).IntraTrnRs.IntraTrnRsUn.IntraRs);
        }
        localAuditAgent.updateXferRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1)localObject, paramFFSConnectionHolder, paramString2);
      }
      else
      {
        FFSDebug.log("SrvrTrans.updateTransferStatus: call, SrvrTrans response not found for SrvrTID=" + paramIntraTrnRslt.srvrTid, 0);
        throw new BPWException("No response found!");
      }
    }
    else
    {
      FFSDebug.log("SrvrTrans.updateTransferStatus: call, SyncType is not valid=" + arrayOfString[0], 0);
      return false;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.SrvrTrans
 * JD-Core Version:    0.7.0.1
 */