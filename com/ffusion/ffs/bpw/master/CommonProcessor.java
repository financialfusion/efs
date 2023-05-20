package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.custimpl.limits.CustomLimitCheckApprovalProcessor;
import com.ffusion.ffs.bpw.db.CustPayee;
import com.ffusion.ffs.bpw.db.CustPayeeRoute;
import com.ffusion.ffs.bpw.db.CustRoute;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.db.RecPmtInstruction;
import com.ffusion.ffs.bpw.db.RecSrvrTrans;
import com.ffusion.ffs.bpw.db.RecXferInstruction;
import com.ffusion.ffs.bpw.db.SrvrTrans;
import com.ffusion.ffs.bpw.db.XferInstruction;
import com.ffusion.ffs.bpw.fulfill.FulfillAgent;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerRouteInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PmtTrnRslt;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.serviceMsg.BPWMsgBroker;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsUn;
import java.util.HashMap;

public class CommonProcessor
  implements FFSConst, DBConsts, BPWResource
{
  public static void deletePayee(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    FFSResultSet localFFSResultSet = null;
    try
    {
      deletePaymentSchedulesByPayeeID(paramFFSConnectionHolder, paramString);
      Object[] arrayOfObject = { paramString };
      DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_ORCCLinkCrossReference WHERE PayeeID = ?", arrayOfObject);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT CustomerID,Status,PayeeListID FROM BPW_CustomerPayee WHERE PayeeID=?", arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        String str = localFFSResultSet.getColumnString(1);
        int i = localFFSResultSet.getColumnInt(3);
        DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_CustPayeeRoute  WHERE CustomerID = ? AND PayeeListID = ?", new Object[] { str, new Integer(i) });
      }
      DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_CustomerPayee SET Status = 'CLOSED' WHERE PayeeID = ?", arrayOfObject);
      DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_Payee WHERE PayeeID=?", arrayOfObject);
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** CommonProcessor.deletePayee failed: " + FFSDebug.stackTrace(localException1));
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
        FFSDebug.log("*** CommonProcessor.deletePayee failed to close:" + localException2.toString(), 0);
      }
    }
  }
  
  public static void closeCustomerPayee(FFSConnectionHolder paramFFSConnectionHolder, String paramString, Integer paramInteger)
    throws Exception
  {
    try
    {
      deletePaymentSchedulesByPayeeListID(paramFFSConnectionHolder, paramString, paramInteger);
      Object[] arrayOfObject = { paramString, paramInteger };
      DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_CustomerPayee SET Status = 'CLOSED' WHERE CustomerID=? AND PayeeListID = ?", arrayOfObject);
      DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_CustPayeeRoute  WHERE CustomerID = ? AND PayeeListID = ?", arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("***CommonProcessor.closeCustomerPayee failed: " + FFSDebug.stackTrace(localException));
      throw localException;
    }
  }
  
  public static void deleteCustomer(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws Exception
  {
    try
    {
      deletePaymentSchedulesByCustomerID(paramFFSConnectionHolder, paramString);
      if (paramInt == 1) {
        deleteXferSchedulesByCustomerID(paramFFSConnectionHolder, paramString);
      }
      Object[] arrayOfObject = { paramString };
      DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_CustomerPayee WHERE CustomerID = ?", arrayOfObject);
      DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_Customer WHERE CustomerID = ?", arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("***CommonProcessor.deleteCustomer failed: " + FFSDebug.stackTrace(localException));
      throw localException;
    }
  }
  
  public static void deleteCustomerWithRouteID(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws Exception
  {
    CustomerRouteInfo localCustomerRouteInfo = CustRoute.getCustomerRoute(paramString, paramInt, paramFFSConnectionHolder);
    String str;
    if (localCustomerRouteInfo != null)
    {
      try
      {
        str = localCustomerRouteInfo.Status;
        if ((str == null) || (str.length() == 0))
        {
          localObject1 = "Failed to find customer with customerID: " + paramString;
          FFSDebug.log((String)localObject1, 0);
          throw new Exception((String)localObject1);
        }
        deletePaymentSchedulesByCustomerID(paramFFSConnectionHolder, paramString, paramInt);
        closeCustPayees(paramString, paramInt, paramFFSConnectionHolder);
        CustRoute.deleteCustRoute(paramString, paramInt, paramFFSConnectionHolder);
        Object localObject1 = CustRoute.getAllCustomerRoute(paramString, paramFFSConnectionHolder);
        if (localObject1.length == 0)
        {
          Object[] arrayOfObject = { paramString };
          DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_CustomerPayee WHERE CustomerID = ?", arrayOfObject);
          DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_Customer WHERE CustomerID = ?", arrayOfObject);
        }
        return;
      }
      finally {}
    }
    else
    {
      str = "Failed to find customer-route with customerID: " + paramString + ", routeID=" + paramInt;
      FFSDebug.log(str, 0);
      throw new BPWException(str, 1000);
    }
  }
  
  public static void deletePaymentSchedulesByPayeeID(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    FFSDebug.log("CommonProcessor.deletePaymentScheduleByPayeeID start payeeID=" + paramString, 6);
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT SrvrTID, RouteID, FIID FROM BPW_PmtInstruction WHERE PayeeID=? ", new Object[] { paramString });
      String str1;
      while (localFFSResultSet.getNextRow())
      {
        str1 = localFFSResultSet.getColumnString(1);
        int i = localFFSResultSet.getColumnInt(2);
        String str2 = localFFSResultSet.getColumnString(3);
        a(paramFFSConnectionHolder, str1, i, str2);
      }
      localFFSResultSet.close();
      localFFSResultSet = null;
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT RecSrvrTID FROM BPW_RecPmtInstruction WHERE PayeeID=? ", new Object[] { paramString });
      while (localFFSResultSet.getNextRow())
      {
        str1 = localFFSResultSet.getColumnString(1);
        jdMethod_for(paramFFSConnectionHolder, str1);
      }
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
      }
      catch (Exception localException) {}
    }
    FFSDebug.log("CommonProcessor.deletePaymentScheduleByPayeeID done payeeID=" + paramString, 6);
  }
  
  public static void deleteXferSchedulesByCustomerID(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    FFSResultSet localFFSResultSet = null;
    HashMap localHashMap = new HashMap();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT SrvrTID, LogID FROM BPW_XferInstruction WHERE CustomerID=? ", new Object[] { paramString });
      String str1;
      String str2;
      while (localFFSResultSet.getNextRow())
      {
        str1 = localFFSResultSet.getColumnString(1);
        str2 = localFFSResultSet.getColumnString(2);
        a(paramString, localHashMap, str2);
        ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "INTRATRN", str1);
        EventInfo.cancelEvent(paramFFSConnectionHolder, "INTRATRN", str1);
        SrvrTrans.cancelSrvrTrans(str1, paramFFSConnectionHolder);
        XferInstruction.delete(paramFFSConnectionHolder, str1);
      }
      localFFSResultSet.close();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT RecSrvrTID, LogID FROM BPW_RecXferInstruction WHERE CustomerID=? ", new Object[] { paramString });
      while (localFFSResultSet.getNextRow())
      {
        str1 = localFFSResultSet.getColumnString(1);
        str2 = localFFSResultSet.getColumnString(2);
        a(paramString, localHashMap, str2);
        ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "RECINTRATRN", str1);
        EventInfo.cancelEvent(paramFFSConnectionHolder, "RECINTRATRN", str1);
        RecSrvrTrans.cancelRecSrvrTrans(str1, paramFFSConnectionHolder);
        RecXferInstruction.delete(paramFFSConnectionHolder, str1);
      }
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
      }
      catch (Exception localException) {}
    }
  }
  
  public static void deletePaymentSchedulesByCustomerID(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    FFSResultSet localFFSResultSet = null;
    HashMap localHashMap = new HashMap();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT SrvrTID, RouteID, FIID, LogID FROM BPW_PmtInstruction WHERE CustomerID=? ", new Object[] { paramString });
      String str1;
      while (localFFSResultSet.getNextRow())
      {
        str1 = localFFSResultSet.getColumnString(1);
        int i = localFFSResultSet.getColumnInt(2);
        String str3 = localFFSResultSet.getColumnString(3);
        String str4 = localFFSResultSet.getColumnString(4);
        a(paramString, localHashMap, str4);
        a(paramFFSConnectionHolder, str1, i, str3);
      }
      localFFSResultSet.close();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT RecSrvrTID, LogID FROM BPW_RecPmtInstruction WHERE CustomerID=? ", new Object[] { paramString });
      while (localFFSResultSet.getNextRow())
      {
        str1 = localFFSResultSet.getColumnString(1);
        String str2 = localFFSResultSet.getColumnString(2);
        a(paramString, localHashMap, str2);
        jdMethod_for(paramFFSConnectionHolder, str1);
      }
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
      }
      catch (Exception localException) {}
    }
  }
  
  private static void a(String paramString1, HashMap paramHashMap, String paramString2)
  {
    if (jdMethod_char()) {
      try
      {
        CustomLimitCheckApprovalProcessor.cancelApproval(paramString1, paramString2, paramHashMap);
      }
      catch (Exception localException)
      {
        String str = "Error message while deleting Approval Item for CustomerID = " + paramString1 + " and logId = " + paramString2 + " " + localException.getMessage();
        FFSDebug.log(str, 0);
      }
    }
  }
  
  private static boolean jdMethod_char()
  {
    PropertyConfig localPropertyConfig = PropertyConfig.getRegisteredProperties();
    String str = localPropertyConfig.otherProperties.getProperty("bpw.limit.check.approval", "N");
    return str.equalsIgnoreCase("Y");
  }
  
  public static void deletePaymentSchedulesByCustomerID(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws Exception
  {
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT SrvrTID, FIID FROM BPW_PmtInstruction WHERE CustomerID=? and RouteID=? ", new Object[] { paramString, new Integer(paramInt) });
      String str1;
      while (localFFSResultSet.getNextRow())
      {
        str1 = localFFSResultSet.getColumnString(1);
        String str2 = localFFSResultSet.getColumnString(2);
        a(paramFFSConnectionHolder, str1, paramInt, str2);
      }
      localFFSResultSet.close();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT RecSrvrTID FROM BPW_RecPmtInstruction rpi, BPW_Payee pe WHERE rpi.CustomerID=? and rpi.PayeeID=pe.PayeeID AND pe.RouteID = ? ", new Object[] { paramString, new Integer(paramInt) });
      while (localFFSResultSet.getNextRow())
      {
        str1 = localFFSResultSet.getColumnString(1);
        jdMethod_for(paramFFSConnectionHolder, str1);
      }
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
      }
      catch (Exception localException) {}
    }
  }
  
  public static void deletePaymentSchedulesByPayeeListID(FFSConnectionHolder paramFFSConnectionHolder, String paramString, Integer paramInteger)
    throws Exception
  {
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT SrvrTID, RouteID, FIID FROM BPW_PmtInstruction WHERE CustomerID=? AND PayeeListID=? ", new Object[] { paramString, paramInteger });
      String str1;
      while (localFFSResultSet.getNextRow())
      {
        str1 = localFFSResultSet.getColumnString(1);
        int i = localFFSResultSet.getColumnInt(2);
        String str2 = localFFSResultSet.getColumnString(3);
        a(paramFFSConnectionHolder, str1, i, str2);
      }
      localFFSResultSet.close();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT RecSrvrTID FROM BPW_RecPmtInstruction WHERE CustomerID=? AND PayeeListID=? ", new Object[] { paramString, paramInteger });
      while (localFFSResultSet.getNextRow())
      {
        str1 = localFFSResultSet.getColumnString(1);
        jdMethod_for(paramFFSConnectionHolder, str1);
      }
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
      }
      catch (Exception localException) {}
    }
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, int paramInt, String paramString2)
    throws Exception
  {
    FulfillAgent localFulfillAgent = (FulfillAgent)FFSRegistry.lookup("FULFILLAGENT");
    String str = localFulfillAgent.findPaymentInstructionType(paramString2, paramInt);
    ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "FUNDTRN", paramString1);
    ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "RECFUNDTRN", paramString1);
    ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, str, paramString1);
    EventInfo.cancelEvent(paramFFSConnectionHolder, "FUNDTRN", paramString1);
    EventInfo.cancelEvent(paramFFSConnectionHolder, "RECFUNDTRN", paramString1);
    EventInfo.cancelEvent(paramFFSConnectionHolder, str, paramString1);
    SrvrTrans.cancelSrvrTrans(paramString1, paramFFSConnectionHolder);
    PmtInstruction.delete(paramFFSConnectionHolder, paramString1);
  }
  
  private static void jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "RECPMTTRN", paramString);
    EventInfo.cancelEvent(paramFFSConnectionHolder, "RECPMTTRN", paramString);
    RecSrvrTrans.cancelRecSrvrTrans(paramString, paramFFSConnectionHolder);
    RecPmtInstruction.delete(paramFFSConnectionHolder, paramString);
  }
  
  public static void closeCustPayees(String paramString, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    CustomerPayeeInfo[] arrayOfCustomerPayeeInfo = CustPayee.getCustPayeeByUIDAndRID(paramString, paramInt, paramFFSConnectionHolder);
    for (int i = 0; i < arrayOfCustomerPayeeInfo.length; i++)
    {
      CustPayeeRoute.updateCustPayeeRouteStatus(arrayOfCustomerPayeeInfo[i].CustomerID, arrayOfCustomerPayeeInfo[i].PayeeListID, paramInt, "CLOSED", paramFFSConnectionHolder);
      CustPayee.updateStatus(arrayOfCustomerPayeeInfo[i].CustomerID, arrayOfCustomerPayeeInfo[i].PayeeListID, "CLOSED", paramFFSConnectionHolder);
    }
  }
  
  public static void disableCustPayees(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_CustomerPayee SET Status = 'INACTIVE' WHERE CustomerID = ? AND Status != 'CLOSED'", new Object[] { paramString });
  }
  
  public static void enableCustPayees(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_CustomerPayee SET Status = 'ACTIVE' WHERE CustomerID = ? AND Status = 'INACTIVE'", new Object[] { paramString });
  }
  
  public static int getEndDate(int paramInt1, int paramInt2, int paramInt3)
    throws BPWException
  {
    if (paramInt3 == 0) {
      return -1;
    }
    ScheduleInfo localScheduleInfo = new ScheduleInfo();
    try
    {
      localScheduleInfo.StartDate = paramInt1;
      localScheduleInfo.CurInstanceNum = paramInt3;
      localScheduleInfo.Frequency = paramInt2;
      ScheduleInfo.computeNextInstanceDate(localScheduleInfo);
    }
    catch (Exception localException)
    {
      String str = "Exception in CommonProcessor.getEndDate: " + localException.getMessage();
      throw new BPWException(str);
    }
    return localScheduleInfo.NextInstanceDate;
  }
  
  public static int mapOFX151FreqToBPWFreq(int paramInt)
  {
    int i = 10;
    switch (paramInt)
    {
    case 0: 
    case 1: 
      i = 0;
      break;
    case 2: 
      i = 1;
      break;
    case 3: 
      i = 2;
      break;
    case 4: 
      i = 3;
      break;
    case 5: 
      i = 4;
      break;
    case 6: 
      i = 5;
      break;
    case 7: 
      i = 6;
      break;
    case 8: 
      i = 7;
      break;
    case 9: 
      i = 8;
      break;
    case 10: 
      i = 9;
    }
    return i;
  }
  
  public static int mapOFX200FreqToBPWFreq(int paramInt)
  {
    int i = 10;
    switch (paramInt)
    {
    case 0: 
    case 1: 
      i = 0;
      break;
    case 2: 
      i = 1;
      break;
    case 3: 
      i = 2;
      break;
    case 4: 
      i = 3;
      break;
    case 5: 
      i = 4;
      break;
    case 6: 
      i = 5;
      break;
    case 7: 
      i = 6;
      break;
    case 8: 
      i = 7;
      break;
    case 9: 
      i = 8;
      break;
    case 10: 
      i = 9;
    }
    return i;
  }
  
  public static int mapBPWFreqToOFX151Freq(int paramInt)
  {
    int i = -1;
    switch (paramInt)
    {
    case 0: 
      i = 0;
      break;
    case 1: 
      i = 2;
      break;
    case 2: 
      i = 3;
      break;
    case 3: 
      i = 4;
      break;
    case 4: 
      i = 5;
      break;
    case 5: 
      i = 6;
      break;
    case 6: 
      i = 7;
      break;
    case 7: 
      i = 8;
      break;
    case 8: 
      i = 9;
      break;
    case 9: 
      i = 10;
    }
    return i;
  }
  
  public static int mapBPWFreqToOFX200Freq(int paramInt)
  {
    int i = -1;
    switch (paramInt)
    {
    case 0: 
      i = 0;
      break;
    case 1: 
      i = 2;
      break;
    case 2: 
      i = 3;
      break;
    case 3: 
      i = 4;
      break;
    case 4: 
      i = 5;
      break;
    case 5: 
      i = 6;
      break;
    case 6: 
      i = 7;
      break;
    case 7: 
      i = 8;
      break;
    case 8: 
      i = 9;
      break;
    case 9: 
      i = 10;
    }
    return i;
  }
  
  public static String mapSchFreqToString(int paramInt)
  {
    switch (paramInt)
    {
    case 0: 
      return "ANNUALLY";
    case 1: 
      return "BIMONTHLY";
    case 2: 
      return "BIWEEKLY";
    case 3: 
      return "FOURWEEKS";
    case 4: 
      return "MONTHLY";
    case 5: 
      return "QUARTERLY";
    case 6: 
      return "SEMIANNUALLY";
    case 7: 
      return "TRIANNUALLY";
    case 8: 
      return "SEMIMONTHLY";
    case 9: 
      return "WEEKLY";
    case 10: 
      return "NONE";
    case 11: 
      return "DAILY";
    case 12: 
      return "IMMEDIATELY";
    }
    return "";
  }
  
  public static void failWillProcessOnPaymentByPayeeListID(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws Exception
  {
    FFSResultSet localFFSResultSet = null;
    try
    {
      AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
      BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
      BackendProcessor localBackendProcessor = new BackendProcessor();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT SrvrTID FROM BPW_PmtInstruction WHERE CustomerID=? AND PayeeListID=? AND Status=? ", new Object[] { paramString, new Integer(paramInt), "WILLPROCESSON" });
      String str1;
      String[] arrayOfString;
      Object localObject1;
      while (localFFSResultSet.getNextRow())
      {
        str1 = localFFSResultSet.getColumnString(1);
        PmtInstruction.updateStatus(paramFFSConnectionHolder, str1, "FAILEDON");
        arrayOfString = SrvrTrans.findResponseBySrvrTID(str1, paramFFSConnectionHolder);
        localObject1 = arrayOfString[1];
        PmtTrnRslt localPmtTrnRslt = new PmtTrnRslt();
        localPmtTrnRslt.status = 2000;
        localPmtTrnRslt.message = BPWLocaleUtil.getMessage(10501, null, "API_MESSAGE");
        if (arrayOfString[0] != null)
        {
          Object localObject2;
          String str2;
          if (arrayOfString[0].startsWith("OFX151"))
          {
            localObject2 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localBPWMsgBroker.parseMsg((String)localObject1, "PmtTrnRsV1", "OFX151");
            str2 = localBackendProcessor.updatePmtStatus((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject2, localPmtTrnRslt);
            localAuditAgent.updatePmtRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject2, paramFFSConnectionHolder, str2);
          }
          else if (arrayOfString[0].startsWith("OFX200"))
          {
            localObject2 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localBPWMsgBroker.parseMsg((String)localObject1, "PmtTrnRsV1", "OFX200");
            str2 = localBackendProcessor.updatePmtStatus((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject2, localPmtTrnRslt);
            localAuditAgent.updatePmtRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject2, paramFFSConnectionHolder, str2);
          }
        }
        ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "FUNDTRN", str1);
        ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "RECFUNDTRN", str1);
        EventInfo.cancelEvent(paramFFSConnectionHolder, "FUNDTRN", str1);
        EventInfo.cancelEvent(paramFFSConnectionHolder, "RECFUNDTRN", str1);
      }
      localFFSResultSet.close();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT RecSrvrTID FROM BPW_RecPmtInstruction WHERE CustomerID=? AND PayeeListID=? AND (Status = ? OR (PaymentType = ? AND Status = ?))", new Object[] { paramString, new Integer(paramInt), "WILLPROCESSON" });
      while (localFFSResultSet.getNextRow())
      {
        str1 = localFFSResultSet.getColumnString(1);
        RecPmtInstruction.updateStatus(paramFFSConnectionHolder, str1, "FAILEDON");
        arrayOfString = RecSrvrTrans.findResponseByRecSrvrTID(str1, paramFFSConnectionHolder);
        if (arrayOfString[0].startsWith("OFX151"))
        {
          localObject1 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "RecPmtTrnRsV1", "OFX151");
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localObject1).RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecurrInst.NInsts = 0;
          localAuditAgent.updateRecPmtRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localObject1, paramFFSConnectionHolder, "FAILEDON");
        }
        else if (arrayOfString[0].startsWith("OFX200"))
        {
          localObject1 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "RecPmtTrnRsV1", "OFX200");
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localObject1).RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecurrInst.NInsts = 0;
          localAuditAgent.updateRecPmtRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localObject1, paramFFSConnectionHolder, "FAILEDON");
        }
        ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "RECPMTTRN", str1);
        EventInfo.cancelEvent(paramFFSConnectionHolder, "RECPMTTRN", str1);
      }
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
      }
      catch (Exception localException) {}
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.CommonProcessor
 * JD-Core Version:    0.7.0.1
 */