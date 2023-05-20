package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.CustPayee;
import com.ffusion.ffs.bpw.db.CustPayeeRoute;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Payee;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.db.Trans;
import com.ffusion.ffs.bpw.fulfill.FulfillAgent;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.serviceMsg.RsCmBuilder;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumIDScopeEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeExtdPayeeV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeExtdPayeeV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeDelRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeDelRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeModRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeModRqV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeModRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeModRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeRqV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRqCm;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;

public class PayeeProcessor
  implements DBConsts, OFXConsts, BPWResource, FFSConst
{
  private int ah = 1;
  private boolean aj = false;
  private boolean ai = false;
  
  public PayeeProcessor()
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.ah = localPropertyConfig.LogLevel;
    this.aj = localPropertyConfig.UseExtdPayeeID;
    this.ai = localPropertyConfig.OneCustPayeeToOnePersonalPayee;
  }
  
  public TypePayeeTrnRsV1 processPayeeTrnRqV1(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    return processPayeeTrnRqV1(paramTypePayeeTrnRqV1, paramTypeUserData, null);
  }
  
  public TypePayeeTrnRsV1 processPayeeTrnRqV1(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    TypePayeeTrnRsV1 localTypePayeeTrnRsV1 = null;
    try
    {
      String str1 = null;
      if (paramTypeUserData != null) {
        str1 = paramTypeUserData._tran_id;
      }
      str2 = paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.__memberName;
      if (str2.equals("PayeeRq")) {
        localTypePayeeTrnRsV1 = jdMethod_do(paramTypePayeeTrnRqV1, paramTypeUserData, str1);
      } else if (str2.equals("PayeeModRq")) {
        localTypePayeeTrnRsV1 = jdMethod_if(paramTypePayeeTrnRqV1, paramTypeUserData, str1);
      } else if (str2.equals("PayeeDelRq")) {
        localTypePayeeTrnRsV1 = a(paramTypePayeeTrnRqV1, paramTypeUserData, str1);
      } else {
        throw new OFXException("Unsupported request type", 2000);
      }
    }
    catch (OFXException localOFXException)
    {
      localTypePayeeTrnRsV1 = jdMethod_int(paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (Exception localException)
    {
      String str2 = "*** PayeeProcessor.processPayeeTrnRqV1 failed:";
      FFSDebug.log(localException, str2);
      FFSDebug.console(str2 + localException.toString());
      localTypePayeeTrnRsV1 = jdMethod_int(paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm, 2000, "Server internal error");
    }
    return localTypePayeeTrnRsV1;
  }
  
  private TypePayeeTrnRsV1 jdMethod_do(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws BPWException
  {
    FFSDebug.log("PayeeProcessor.processPayeeRqV1 start, uid=" + PmtProcessor.getUID(paramTypeUserData), 6);
    TypePayeeTrnRsV1 localTypePayeeTrnRsV1 = null;
    String str1 = paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.PayeeRqV1Un.__memberName;
    String str2 = null;
    int i = -1;
    int j = jdMethod_for();
    String str3 = PmtProcessor.getUID(paramTypeUserData);
    FFSConnection[] arrayOfFFSConnection = DBUtil.getConnections(2);
    FFSConnectionHolder localFFSConnectionHolder1 = new FFSConnectionHolder();
    localFFSConnectionHolder1.conn = arrayOfFFSConnection[0];
    FFSConnectionHolder localFFSConnectionHolder2 = new FFSConnectionHolder();
    localFFSConnectionHolder2.conn = arrayOfFFSConnection[1];
    try
    {
      Object localObject2;
      Object localObject4;
      Object localObject1;
      if (Trans.checkDuplicateTIDAndSaveTID(paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm.TrnUID))
      {
        if (this.ah >= 3)
        {
          int k = Integer.parseInt(str3);
          localObject2 = BPWLocaleUtil.getMessage(101, null, "BILLPAY_AUDITLOG_MESSAGE");
          localObject3 = new String[] { localObject2 };
          localObject4 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject3, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(localFFSConnectionHolder1.conn.getConnection(), paramTypeUserData._submittedBy, null, null, (ILocalizable)localObject4, paramString, 4416, k, null, null, null, "NEW", null, null, null, null, 0);
        }
        localTypePayeeTrnRsV1 = jdMethod_int(paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm, 2019, " ");
        localFFSConnectionHolder1.conn.commit();
        localObject1 = localTypePayeeTrnRsV1;
        return localObject1;
      }
      Object localObject5;
      if (!this.ai)
      {
        if (str1.equals("PayeeRqV1Cm"))
        {
          localObject2 = new Payee();
          ((Payee)localObject2).setRouteID(j);
          str2 = ((Payee)localObject2).addPayee(paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.PayeeRqV1Un.PayeeRqV1Cm.Payee, localFFSConnectionHolder1, paramTypeUserData._tran_id);
          localObject1 = Payee.findPayeeByID(str2, localFFSConnectionHolder1);
          if ((((PayeeInfo)localObject1).Status.equals("INACTIVE")) || (((PayeeInfo)localObject1).Status.equals("CLOSED"))) {
            throw new OFXException("Payee is not Active", 2000);
          }
        }
        else if (str1.equals("PayeeID"))
        {
          str2 = paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.PayeeRqV1Un.PayeeID;
          if (!this.aj) {
            localObject1 = Payee.findPayeeByID(str2, localFFSConnectionHolder1);
          } else {
            localObject1 = Payee.findPayeeByExtendedID(str2, localFFSConnectionHolder1);
          }
          if (localObject1 == null) {
            throw new OFXException(" ", 10510);
          }
          if ((((PayeeInfo)localObject1).Status.equals("INACTIVE")) || (((PayeeInfo)localObject1).Status.equals("CLOSED"))) {
            throw new OFXException("Payee is not Active", 2000);
          }
        }
        else
        {
          throw new OFXException("Unsupported request type", 2000);
        }
        localObject2 = new CustPayee();
        localObject3 = jdMethod_if(paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.PayAcct);
        localObject4 = null;
        if (paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.NameOnAcctExists) {
          localObject4 = jdMethod_if(paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.NameOnAcct);
        }
        i = ((CustPayee)localObject2).addCustPayee(paramTypeUserData._uid, ((PayeeInfo)localObject1).PayeeID, (String)localObject3, (String)localObject4, ((PayeeInfo)localObject1).RouteID, localFFSConnectionHolder1, localFFSConnectionHolder2);
      }
      else
      {
        localObject2 = new CustPayee();
        localObject3 = jdMethod_if(paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.PayAcct);
        Object localObject6;
        if (str1.equals("PayeeRqV1Cm"))
        {
          localObject4 = new Payee();
          str2 = ((Payee)localObject4).matchGlobalPayee(paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.PayeeRqV1Un.PayeeRqV1Cm.Payee, localFFSConnectionHolder1);
          if (str2 == null)
          {
            localObject5 = ((Payee)localObject4).matchPayees(paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.PayeeRqV1Un.PayeeRqV1Cm.Payee, localFFSConnectionHolder1);
            if (localObject5.length > 0)
            {
              localObject6 = ((CustPayee)localObject2).matchCustPayees(paramTypeUserData._uid, (String[])localObject5, (String)localObject3, localFFSConnectionHolder1);
              if (localObject6 == null)
              {
                ((Payee)localObject4).setRouteID(j);
                str2 = ((Payee)localObject4).addPayeeNoMatch(paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.PayeeRqV1Un.PayeeRqV1Cm.Payee, localFFSConnectionHolder1, paramTypeUserData._tran_id);
              }
              else
              {
                str2 = ((CustomerPayeeInfo)localObject6).PayeeID;
              }
            }
            else
            {
              ((Payee)localObject4).setRouteID(j);
              str2 = ((Payee)localObject4).addPayeeNoMatch(paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.PayeeRqV1Un.PayeeRqV1Cm.Payee, localFFSConnectionHolder1, paramTypeUserData._tran_id);
            }
          }
          localObject1 = Payee.findPayeeByID(str2, localFFSConnectionHolder1);
          if ((((PayeeInfo)localObject1).Status.equals("INACTIVE")) || (((PayeeInfo)localObject1).Status.equals("CLOSED"))) {
            throw new OFXException("Payee is not Active", 2000);
          }
        }
        else if (str1.equals("PayeeID"))
        {
          str2 = paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.PayeeRqV1Un.PayeeID;
          if (!this.aj) {
            localObject1 = Payee.findPayeeByID(str2, localFFSConnectionHolder1);
          } else {
            localObject1 = Payee.findPayeeByExtendedID(str2, localFFSConnectionHolder1);
          }
          if (localObject1 == null) {
            throw new OFXException(" ", 10510);
          }
          if ((((PayeeInfo)localObject1).Status.equals("INACTIVE")) || (((PayeeInfo)localObject1).Status.equals("CLOSED"))) {
            throw new OFXException("Payee is not Active", 2000);
          }
          localObject4 = new String[1];
          localObject4[0] = str2;
          localObject5 = ((CustPayee)localObject2).matchCustPayees(paramTypeUserData._uid, (String[])localObject4, (String)localObject3, localFFSConnectionHolder1);
          if (localObject5 == null)
          {
            localObject6 = new Payee();
            if (((PayeeInfo)localObject1).PayeeType == 3) {
              str2 = ((Payee)localObject6).addPayeeNoMatch((PayeeInfo)localObject1, localFFSConnectionHolder1, paramTypeUserData._tran_id);
            }
          }
          localObject1 = Payee.findPayeeByID(str2, localFFSConnectionHolder1);
        }
        else
        {
          throw new OFXException("Unsupported request type", 2000);
        }
        localObject4 = null;
        if (paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.NameOnAcctExists) {
          localObject4 = jdMethod_if(paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.NameOnAcct);
        }
        i = ((CustPayee)localObject2).addCustPayee(paramTypeUserData._uid, ((PayeeInfo)localObject1).PayeeID, (String)localObject3, (String)localObject4, ((PayeeInfo)localObject1).RouteID, localFFSConnectionHolder1, localFFSConnectionHolder2);
      }
      localTypePayeeTrnRsV1 = a(paramTypePayeeTrnRqV1, str3, i, (PayeeInfo)localObject1);
      if (this.ah >= 3)
      {
        int m = Integer.parseInt(str3);
        localObject3 = BPWLocaleUtil.getMessage(100, null, "BILLPAY_AUDITLOG_MESSAGE");
        localObject4 = new String[] { localObject3 };
        localObject5 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject4, "BILLPAY_AUDITLOG_MESSAGE");
        TransAuditLog.logTransAuditLog(localFFSConnectionHolder1.conn.getConnection(), paramTypeUserData._submittedBy, null, null, (ILocalizable)localObject5, paramString, 4415, m, null, null, null, ((PayeeInfo)localObject1).Status, null, null, null, null, 0);
      }
      localFFSConnectionHolder1.conn.commit();
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder1.conn.rollback();
      localTypePayeeTrnRsV1 = jdMethod_int(paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder1.conn.rollback();
      String str4 = localException.toString();
      Object localObject3 = "*** PayeeProcessor.processPayeeRqV1Add failed:";
      FFSDebug.log(localException, (String)localObject3);
      FFSDebug.console((String)localObject3 + str4);
      localTypePayeeTrnRsV1 = jdMethod_int(paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm, 2000, "Server internal error");
    }
    finally
    {
      localFFSConnectionHolder2.conn.rollback();
      DBUtil.freeConnection(localFFSConnectionHolder1.conn);
      DBUtil.freeConnection(localFFSConnectionHolder2.conn);
    }
    FFSDebug.log("PayeeProcessor.processPayeeRqV1 end, uid=" + str3 + ", payeeListID=" + i, 6);
    return localTypePayeeTrnRsV1;
  }
  
  private TypePayeeTrnRsV1 jdMethod_if(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws BPWException
  {
    FFSDebug.log("PayeeProcessor.processPayeeRqV1Mod start, uid=" + PmtProcessor.getUID(paramTypeUserData), 6);
    TypePayeeTrnRsV1 localTypePayeeTrnRsV1 = null;
    boolean bool = paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeModRq.PayeeModRqV1CmExists;
    FFSConnection[] arrayOfFFSConnection = DBUtil.getConnections(2);
    FFSConnectionHolder localFFSConnectionHolder1 = new FFSConnectionHolder();
    localFFSConnectionHolder1.conn = arrayOfFFSConnection[0];
    FFSConnectionHolder localFFSConnectionHolder2 = new FFSConnectionHolder();
    localFFSConnectionHolder2.conn = arrayOfFFSConnection[1];
    String str1 = PmtProcessor.getUID(paramTypeUserData);
    try
    {
      if (Trans.checkDuplicateTIDAndSaveTID(paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm.TrnUID))
      {
        if (this.ah >= 3)
        {
          int i = Integer.parseInt(str1);
          str2 = BPWLocaleUtil.getMessage(103, null, "BILLPAY_AUDITLOG_MESSAGE");
          String[] arrayOfString1 = { str2 };
          LocalizableString localLocalizableString1 = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString1, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(localFFSConnectionHolder1.conn.getConnection(), paramTypeUserData._submittedBy, null, null, localLocalizableString1, paramString, 4418, i, null, null, null, "ACTIVE", null, null, null, null, 0);
        }
        localTypePayeeTrnRsV1 = jdMethod_int(paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm, 2019, " ");
        localFFSConnectionHolder1.conn.commit();
        localObject1 = localTypePayeeTrnRsV1;
        return localObject1;
      }
      Object localObject1 = jdMethod_if(paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeModRq.PayAcct);
      str2 = null;
      if (paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeModRq.NameOnAcctExists) {
        str2 = jdMethod_if(paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeModRq.NameOnAcct);
      }
      int j;
      try
      {
        j = Integer.parseInt(paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeModRq.PayeeLstID);
      }
      catch (Exception localException2)
      {
        throw new OFXException(" ", 10519);
      }
      CustomerPayeeInfo localCustomerPayeeInfo = new CustomerPayeeInfo(str1, "", j, (String)localObject1, str2, "", 0, "", "", -1, -1);
      PayeeInfo localPayeeInfo = modPayee(localFFSConnectionHolder1, localFFSConnectionHolder2, bool, localCustomerPayeeInfo, bool ? paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeModRq.PayeeModRqV1Cm.Payee : null, paramTypeUserData._tran_id);
      localTypePayeeTrnRsV1 = jdMethod_if(paramTypePayeeTrnRqV1, str1, localCustomerPayeeInfo.PayeeListID, localPayeeInfo);
      if (this.ah >= 3)
      {
        int k = Integer.parseInt(str1);
        String str4 = BPWLocaleUtil.getMessage(102, null, "BILLPAY_AUDITLOG_MESSAGE");
        String[] arrayOfString2 = { str4 };
        LocalizableString localLocalizableString2 = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString2, "BILLPAY_AUDITLOG_MESSAGE");
        TransAuditLog.logTransAuditLog(localFFSConnectionHolder1.conn.getConnection(), paramTypeUserData._submittedBy, null, null, localLocalizableString2, paramString, 4417, k, null, null, null, localPayeeInfo.Status, null, null, null, null, 0);
      }
      localFFSConnectionHolder1.conn.commit();
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder1.conn.rollback();
      localTypePayeeTrnRsV1 = jdMethod_int(paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (Exception localException1)
    {
      localFFSConnectionHolder1.conn.rollback();
      String str2 = localException1.toString();
      String str3 = "*** PayeeProcessor.processPayeeRqV1Mod failed:";
      FFSDebug.log(localException1, str3);
      FFSDebug.console(str3 + str2);
      localTypePayeeTrnRsV1 = jdMethod_int(paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm, 2000, "Server internal error");
    }
    finally
    {
      localFFSConnectionHolder2.conn.rollback();
      DBUtil.freeConnection(localFFSConnectionHolder1.conn);
      DBUtil.freeConnection(localFFSConnectionHolder2.conn);
    }
    FFSDebug.log("PayeeProcessor.processPayeeRqV1Mod end, uid=" + str1, 6);
    return localTypePayeeTrnRsV1;
  }
  
  private TypePayeeTrnRsV1 a(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws BPWException
  {
    FFSDebug.log("Start PayeeProcessor.processPayeeRqV1Del start, uid= " + PmtProcessor.getUID(paramTypeUserData), 6);
    TypePayeeTrnRsV1 localTypePayeeTrnRsV11 = null;
    String str1 = null;
    String str2 = PmtProcessor.getUID(paramTypeUserData);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      if (Trans.checkDuplicateTIDAndSaveTID(paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm.TrnUID))
      {
        if (this.ah >= 3)
        {
          int i = Integer.parseInt(str2);
          String str3 = BPWLocaleUtil.getMessage(105, null, "BILLPAY_AUDITLOG_MESSAGE");
          localObject2 = new String[] { str3 };
          localObject3 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject2, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), paramTypeUserData._submittedBy, null, null, (ILocalizable)localObject3, paramString, 4420, i, null, null, null, "ACTIVE", null, null, null, null, 0);
        }
        localTypePayeeTrnRsV11 = jdMethod_int(paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm, 2019, " ");
        localFFSConnectionHolder.conn.commit();
        TypePayeeTrnRsV1 localTypePayeeTrnRsV12 = localTypePayeeTrnRsV11;
        return localTypePayeeTrnRsV12;
      }
      int j;
      try
      {
        j = Integer.parseInt(paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeDelRq.PayeeLstID);
      }
      catch (Exception localException2)
      {
        throw new OFXException(" ", 10519);
      }
      localObject1 = new CustPayee();
      ((CustPayee)localObject1).findCustPayeeByPayeeListID(str2, j, localFFSConnectionHolder);
      localObject2 = ((CustPayee)localObject1).getStatus();
      if (localObject2 == null) {
        throw new OFXException(" ", 10519);
      }
      if (((String)localObject2).equals("CLOSED")) {
        throw new OFXException(" ", 10519);
      }
      Object localObject3 = CustPayeeRoute.getActiveCustPayeeRoute(str2, j, localFFSConnectionHolder);
      if (localObject3 == null) {
        throw new OFXException(" ", 10519);
      }
      if ((((CustPayeeRoute)localObject3).Status.equals("CLOSED")) || (((CustPayeeRoute)localObject3).Status.equals("CANC")) || (((CustPayeeRoute)localObject3).Status.equals("PENDING")) || (((CustPayeeRoute)localObject3).Status.equals("CANC_INPROCESS"))) {
        throw new OFXException(" ", 10519);
      }
      String str4 = null;
      if (((CustPayeeRoute)localObject3).Status.equals("NEW"))
      {
        if (!PmtInstruction.hasPendingPmt(str2, j, localFFSConnectionHolder)) {
          str4 = "CLOSED";
        } else {
          str4 = "PENDING";
        }
      }
      else if ((((CustPayeeRoute)localObject3).Status.equals("FAILEDON")) || (((CustPayeeRoute)localObject3).Status.equals("ERROR"))) {
        str4 = "CLOSED";
      } else if (!PmtInstruction.hasPendingPmt(str2, j, localFFSConnectionHolder)) {
        str4 = "CANC";
      } else {
        str4 = "PENDING";
      }
      str1 = ((CustPayee)localObject1).getPayeeID();
      PayeeInfo localPayeeInfo = Payee.findPayeeByID(str1, localFFSConnectionHolder);
      CustPayeeRoute.updateCustPayeeRouteStatus(str2, j, localPayeeInfo.RouteID, str4, localFFSConnectionHolder);
      if (str4.equals("CLOSED")) {
        CustPayee.updateStatus(str2, j, str4, localFFSConnectionHolder);
      }
      if ((this.ai) && (localPayeeInfo.PayeeType == 3)) {
        Payee.updateStatus(str1, "CLOSED", localFFSConnectionHolder);
      }
      localTypePayeeTrnRsV11 = a(paramTypePayeeTrnRqV1, str2, j);
      if (this.ah >= 3)
      {
        int k = Integer.parseInt(str2);
        String str5 = BPWLocaleUtil.getMessage(104, null, "BILLPAY_AUDITLOG_MESSAGE");
        String[] arrayOfString = { str5 };
        LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString, "BILLPAY_AUDITLOG_MESSAGE");
        TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), paramTypeUserData._submittedBy, null, null, localLocalizableString, paramString, 4419, k, null, null, null, localPayeeInfo.Status, null, null, null, null, 0);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder.conn.rollback();
      localTypePayeeTrnRsV11 = jdMethod_int(paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (Exception localException1)
    {
      localFFSConnectionHolder.conn.rollback();
      Object localObject1 = localException1.toString();
      Object localObject2 = "*** PayeeProcessor.processPayeeRqV1Del failed:";
      FFSDebug.log(localException1, (String)localObject2);
      FFSDebug.console((String)localObject2 + (String)localObject1);
      localTypePayeeTrnRsV11 = jdMethod_int(paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm, 2000, "Server internal error");
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("PayeeProcessor.processPayeeRqV1Del end, uid=" + str2, 6);
    return localTypePayeeTrnRsV11;
  }
  
  public PayeeInfo modPayee(FFSConnectionHolder paramFFSConnectionHolder1, FFSConnectionHolder paramFFSConnectionHolder2, boolean paramBoolean, CustomerPayeeInfo paramCustomerPayeeInfo, TypePayeeV1Aggregate paramTypePayeeV1Aggregate, String paramString)
    throws Exception
  {
    String str1 = paramCustomerPayeeInfo.CustomerID;
    int i = paramCustomerPayeeInfo.PayeeListID;
    String str2 = paramCustomerPayeeInfo.PayAcct;
    String str3 = paramCustomerPayeeInfo.NameOnAcct;
    CustPayee localCustPayee1 = new CustPayee();
    localCustPayee1.findCustPayeeByPayeeListID(str1, i, paramFFSConnectionHolder1);
    Object localObject1 = null;
    localObject1 = localCustPayee1.getPayeeID();
    if (localObject1 == null) {
      throw new OFXException(" ", 10519);
    }
    FFSDebug.log("===In the mod payee mothed : payeeID = " + (String)localObject1, 6);
    PayeeInfo localPayeeInfo = Payee.findPayeeByID((String)localObject1, paramFFSConnectionHolder1);
    if ((localPayeeInfo.Status.equals("INACTIVE")) || (localPayeeInfo.Status.equals("CLOSED"))) {
      throw new OFXException("Payee is not Active", 2000);
    }
    String str4 = localCustPayee1.getStatus();
    if (str4 == null) {
      throw new OFXException(" ", 10519);
    }
    if (str4.equals("CLOSED")) {
      throw new OFXException(" ", 10515);
    }
    CustPayeeRoute localCustPayeeRoute = CustPayeeRoute.getActiveCustPayeeRoute(str1, i, paramFFSConnectionHolder1);
    if (localCustPayeeRoute == null) {
      throw new OFXException(" ", 10515);
    }
    if ((localCustPayeeRoute.Status.equals("CLOSED")) || (localCustPayeeRoute.Status.equals("CANC")) || (localCustPayeeRoute.Status.equals("PENDING")) || (localCustPayeeRoute.Status.equals("CANC_INPROCESS"))) {
      throw new OFXException(" ", 10515);
    }
    int j = 0;
    String str5 = localCustPayee1.getPayAcct();
    if (str5 == null)
    {
      if ((str2 == null) || (str2.equals(""))) {
        j = 0;
      } else {
        j = 1;
      }
    }
    else if (str5.equals(str2)) {
      j = 0;
    } else {
      j = 1;
    }
    int k = 0;
    String str6 = localCustPayee1.getNameOnAcct();
    if (str6 == null)
    {
      if ((str3 == null) || (str3.equals(""))) {
        k = 0;
      } else {
        k = 1;
      }
    }
    else if (str6.equals(str3)) {
      k = 0;
    } else {
      k = 1;
    }
    int m = jdMethod_for();
    String str7 = null;
    if (paramBoolean)
    {
      localObject2 = new Payee();
      if (!this.ai)
      {
        ((Payee)localObject2).setRouteID(m);
        ((Payee)localObject2).addPayee(paramTypePayeeV1Aggregate, paramFFSConnectionHolder1, paramString);
        str7 = ((Payee)localObject2).getPayeeID();
      }
      else
      {
        str7 = ((Payee)localObject2).matchGlobalPayee(paramTypePayeeV1Aggregate, paramFFSConnectionHolder1);
        if (str7 == null)
        {
          ((Payee)localObject2).setRouteID(m);
          ((Payee)localObject2).addPayeeNoMatch(paramTypePayeeV1Aggregate, paramFFSConnectionHolder1, paramString);
          str7 = ((Payee)localObject2).getPayeeID();
        }
      }
      if (((String)localObject1).equals(str7))
      {
        if ((j != 0) || (k != 0))
        {
          localCustPayee1.modCustPayee(str2, str3, str5, (String)localObject1, "MODACCT", paramFFSConnectionHolder1);
          if ((localCustPayeeRoute.Status.equals("FAILEDON")) || (localCustPayeeRoute.Status.equals("ERROR"))) {
            throw new OFXException(" ", 10515);
          }
          if (!localCustPayeeRoute.Status.equals("NEW")) {
            CustPayeeRoute.updateCustPayeeRouteStatus(str1, i, m, "MODACCT", paramFFSConnectionHolder1);
          }
          a(str1, i, str2, str3, paramFFSConnectionHolder1);
        }
      }
      else
      {
        if ((localPayeeInfo.PayeeType == 0) || (localPayeeInfo.PayeeType == 1) || (localPayeeInfo.PayeeType == 2)) {
          throw new OFXException(" ", 10515);
        }
        if ((localCustPayeeRoute.Status.equals("NEW")) || (localCustPayeeRoute.Status.equals("FAILEDON")) || (localCustPayeeRoute.Status.equals("ERROR")))
        {
          CustPayeeRoute.updateCustPayeeRouteStatus(str1, i, m, "CLOSED", paramFFSConnectionHolder1);
          CustPayee.updateStatus(str1, i, "CLOSED", paramFFSConnectionHolder1);
        }
        else
        {
          CustPayeeRoute.updateCustPayeeRouteStatus(str1, i, m, "CANC", paramFFSConnectionHolder1);
        }
        CustPayee localCustPayee2 = new CustPayee();
        paramCustomerPayeeInfo.PayeeListID = localCustPayee2.addCustPayee(str1, str7, str2, str3, ((Payee)localObject2).getRouteID(), paramFFSConnectionHolder1, paramFFSConnectionHolder2);
        a(str1, i, paramCustomerPayeeInfo.PayeeListID, str2, str3, str7, paramTypePayeeV1Aggregate, paramFFSConnectionHolder1);
        localObject1 = str7;
      }
    }
    else if ((j != 0) || (k != 0))
    {
      localCustPayee1.modCustPayee(str2, str3, str5, (String)localObject1, "MODACCT", paramFFSConnectionHolder1);
      if ((localCustPayeeRoute.Status.equals("FAILEDON")) || (localCustPayeeRoute.Status.equals("ERROR"))) {
        throw new OFXException(" ", 10515);
      }
      if (!localCustPayeeRoute.Status.equals("NEW")) {
        CustPayeeRoute.updateCustPayeeRouteStatus(str1, i, m, "MODACCT", paramFFSConnectionHolder1);
      }
      a(str1, i, str2, str3, paramFFSConnectionHolder1);
    }
    Object localObject2 = Payee.findPayeeByID((String)localObject1, paramFFSConnectionHolder1);
    return localObject2;
  }
  
  private void a(String paramString1, int paramInt, String paramString2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSResultSet localFFSResultSet = null;
    try
    {
      AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT SrvrTID FROM BPW_PmtInstruction WHERE CustomerID=? AND PayeeListID=? AND Status=?", new Object[] { paramString1, new Integer(paramInt), "WILLPROCESSON" });
      String str;
      while (localFFSResultSet.getNextRow())
      {
        str = localFFSResultSet.getColumnString(1);
        DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_PmtInstruction SET PayAcct=? WHERE SrvrTID=?", new Object[] { paramString2, str });
        localAuditAgent.updatePayAcctInPmtRsV1(str, paramString2, paramString3, paramFFSConnectionHolder);
      }
      localFFSResultSet.close();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT RecSrvrTID FROM BPW_RecPmtInstruction WHERE CustomerID=? AND PayeeListID=? AND Status=?", new Object[] { paramString1, new Integer(paramInt), "WILLPROCESSON" });
      while (localFFSResultSet.getNextRow())
      {
        str = localFFSResultSet.getColumnString(1);
        DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_RecPmtInstruction SET PayAcct=? WHERE RecSrvrTID=?", new Object[] { paramString2, str });
        localAuditAgent.updatePayAcctInRecPmtRsV1(str, paramString2, paramString3, paramFFSConnectionHolder);
      }
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
      catch (Exception localException) {}
    }
  }
  
  private void a(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, String paramString4, TypePayeeV1Aggregate paramTypePayeeV1Aggregate, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSResultSet localFFSResultSet = null;
    try
    {
      AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
      PayeeInfo localPayeeInfo = Payee.findPayeeByID(paramString4, paramFFSConnectionHolder);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT SrvrTID FROM BPW_PmtInstruction WHERE CustomerID=? AND PayeeListID=? AND Status IN (?,?,?,?,?)", new Object[] { paramString1, new Integer(paramInt1), "WILLPROCESSON", "FUNDSALLOCATED", "NOFUNDSON", "INFUNDSALLOC", "FUNDSALLOCACTIVE" });
      String str;
      while (localFFSResultSet.getNextRow())
      {
        str = localFFSResultSet.getColumnString(1);
        DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_PmtInstruction SET PayAcct=?,PayeeListID=?,PayeeID=? WHERE SrvrTID=?", new Object[] { paramString2, new Integer(paramInt2), paramString4, str });
        localAuditAgent.updatePayeeInPmtRsV1(str, paramString2, paramString3, Integer.toString(paramInt2), localPayeeInfo, paramTypePayeeV1Aggregate, paramFFSConnectionHolder);
      }
      localFFSResultSet.close();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT RecSrvrTID FROM BPW_RecPmtInstruction WHERE CustomerID=? AND PayeeListID=? AND Status IN (?,?,?,?,?)", new Object[] { paramString1, new Integer(paramInt1), "WILLPROCESSON", "FUNDSALLOCATED", "NOFUNDSON", "INFUNDSALLOC", "FUNDSALLOCACTIVE" });
      while (localFFSResultSet.getNextRow())
      {
        str = localFFSResultSet.getColumnString(1);
        DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_RecPmtInstruction SET PayAcct=?,PayeeListID=?,PayeeID=? WHERE RecSrvrTID=?", new Object[] { paramString2, new Integer(paramInt2), paramString4, str });
        localAuditAgent.updatePayeeInRecPmtRsV1(str, paramString2, paramString3, Integer.toString(paramInt2), localPayeeInfo, paramTypePayeeV1Aggregate, paramFFSConnectionHolder);
      }
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
      catch (Exception localException) {}
    }
  }
  
  private TypePayeeTrnRsV1 a(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, String paramString, int paramInt, PayeeInfo paramPayeeInfo)
  {
    FFSDebug.log("PayeeProcessor.buildPayeeAddRsV1, uid=" + paramString + ",payeeListID=" + paramInt, 6);
    TypePayeeTrnRsV1 localTypePayeeTrnRsV1 = new TypePayeeTrnRsV1();
    localTypePayeeTrnRsV1.PayeeTrnRs = new TypePayeeTrnRsV1Aggregate();
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1UnExists = true;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un = new TypePayeeTrnRsV1Un();
    localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm, null);
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.__memberName = "PayeeRs";
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs = new TypePayeeRsV1Aggregate();
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeLstID = String.valueOf(paramInt);
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayAcct = paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.PayAcct;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.NameOnAcct = paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.NameOnAcct;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.NameOnAcctExists = paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.NameOnAcctExists;
    if (paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.PayeeRqV1Un.__memberName.equals("PayeeID"))
    {
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1CmExists = false;
    }
    else
    {
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1CmExists = true;
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm = new TypePayeeRsV1Cm();
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm.Payee = paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.PayeeRqV1Un.PayeeRqV1Cm.Payee;
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm.BankAcctToExists = paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.PayeeRqV1Un.PayeeRqV1Cm.BankAcctToExists;
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm.BankAcctTo = paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeRq.PayeeRqV1Un.PayeeRqV1Cm.BankAcctTo;
    }
    if ((!this.aj) || (!paramPayeeInfo.ExtdPayeeID.equals("0")))
    {
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.ExtdPayeeExists = true;
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.ExtdPayee = new TypeExtdPayeeV1Aggregate();
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.ExtdPayee.ExtdPayeeV1CmExists = true;
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.ExtdPayee.ExtdPayeeV1Cm = new TypeExtdPayeeV1Cm();
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.ExtdPayee.ExtdPayeeV1Cm.PayeeID = (!this.aj ? paramPayeeInfo.PayeeID : paramPayeeInfo.ExtdPayeeID);
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.ExtdPayee.ExtdPayeeV1Cm.Name = paramPayeeInfo.PayeeName;
      if (paramPayeeInfo.PayeeType == 3) {
        localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.ExtdPayee.ExtdPayeeV1Cm.IDScope = EnumIDScopeEnum.USER;
      } else {
        localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.ExtdPayee.ExtdPayeeV1Cm.IDScope = EnumIDScopeEnum.GLOBAL;
      }
    }
    return localTypePayeeTrnRsV1;
  }
  
  private TypePayeeTrnRsV1 jdMethod_if(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, String paramString, int paramInt, PayeeInfo paramPayeeInfo)
  {
    TypePayeeTrnRsV1 localTypePayeeTrnRsV1 = new TypePayeeTrnRsV1();
    localTypePayeeTrnRsV1.PayeeTrnRs = new TypePayeeTrnRsV1Aggregate();
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1UnExists = true;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un = new TypePayeeTrnRsV1Un();
    localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm, null);
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.__memberName = "PayeeModRs";
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeModRs = new TypePayeeModRsV1Aggregate();
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeModRs.PayeeModRsV1CmExists = paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeModRq.PayeeModRqV1CmExists;
    if (paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeModRq.PayeeModRqV1CmExists)
    {
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeModRs.PayeeModRsV1Cm = new TypePayeeModRsV1Cm();
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeModRs.PayeeModRsV1Cm.Payee = paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeModRq.PayeeModRqV1Cm.Payee;
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeModRs.PayeeModRsV1Cm.BankAcctToExists = paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeModRq.PayeeModRqV1Cm.BankAcctToExists;
      if (paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeModRq.PayeeModRqV1Cm.BankAcctToExists) {
        localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeModRs.PayeeModRsV1Cm.BankAcctTo = paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeModRq.PayeeModRqV1Cm.BankAcctTo;
      }
    }
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs = null;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeModRs.PayeeLstID = String.valueOf(paramInt);
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeModRs.PayAcct = paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeModRq.PayAcct;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeModRs.NameOnAcct = paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeModRq.NameOnAcct;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeModRs.NameOnAcctExists = paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqV1Un.PayeeModRq.NameOnAcctExists;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeDelRs = null;
    if ((!this.aj) || (!paramPayeeInfo.ExtdPayeeID.equals("0")))
    {
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeModRs.ExtdPayeeExists = true;
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeModRs.ExtdPayee = new TypeExtdPayeeV1Aggregate();
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeModRs.ExtdPayee.ExtdPayeeV1CmExists = true;
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeModRs.ExtdPayee.ExtdPayeeV1Cm = new TypeExtdPayeeV1Cm();
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeModRs.ExtdPayee.ExtdPayeeV1Cm.PayeeID = (!this.aj ? paramPayeeInfo.PayeeID : paramPayeeInfo.ExtdPayeeID);
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeModRs.ExtdPayee.ExtdPayeeV1Cm.Name = paramPayeeInfo.PayeeName;
      if (paramPayeeInfo.PayeeType == 3) {
        localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeModRs.ExtdPayee.ExtdPayeeV1Cm.IDScope = EnumIDScopeEnum.USER;
      } else {
        localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeModRs.ExtdPayee.ExtdPayeeV1Cm.IDScope = EnumIDScopeEnum.GLOBAL;
      }
    }
    return localTypePayeeTrnRsV1;
  }
  
  private TypePayeeTrnRsV1 a(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, String paramString, int paramInt)
  {
    TypePayeeTrnRsV1 localTypePayeeTrnRsV1 = new TypePayeeTrnRsV1();
    localTypePayeeTrnRsV1.PayeeTrnRs = new TypePayeeTrnRsV1Aggregate();
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1UnExists = true;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un = new TypePayeeTrnRsV1Un();
    localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm, null);
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.__memberName = "PayeeDelRs";
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeDelRs = new TypePayeeDelRsV1Aggregate();
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeDelRs.PayeeLstID = String.valueOf(paramInt);
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs = null;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeModRs = null;
    return localTypePayeeTrnRsV1;
  }
  
  private TypePayeeTrnRsV1 jdMethod_int(TypeTrnRqCm paramTypeTrnRqCm, int paramInt, String paramString)
  {
    TypePayeeTrnRsV1Aggregate localTypePayeeTrnRsV1Aggregate = new TypePayeeTrnRsV1Aggregate();
    localTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1UnExists = false;
    localTypePayeeTrnRsV1Aggregate.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, paramInt, paramString);
    return new TypePayeeTrnRsV1(localTypePayeeTrnRsV1Aggregate);
  }
  
  private int jdMethod_for()
    throws BPWException
  {
    FulfillAgent localFulfillAgent = (FulfillAgent)FFSRegistry.lookup("FULFILLAGENT");
    if (localFulfillAgent == null) {
      throw new BPWException("No Fulfillment system.");
    }
    return localFulfillAgent.getDefaultFulfillment();
  }
  
  private String jdMethod_if(String[] paramArrayOfString)
    throws OFXException
  {
    String str = null;
    if (paramArrayOfString == null) {
      str = null;
    } else if (paramArrayOfString.length == 0) {
      str = null;
    } else if (paramArrayOfString.length == 1) {
      str = paramArrayOfString[0];
    } else {
      throw new OFXException("Multiple pay accounts not supported", 2000);
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.PayeeProcessor
 * JD-Core Version:    0.7.0.1
 */