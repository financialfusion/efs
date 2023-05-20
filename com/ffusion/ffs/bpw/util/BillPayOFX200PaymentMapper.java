package com.ffusion.ffs.bpw.util;

import com.ffusion.ffs.bpw.enums.FrequencyType;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumFreqEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAddressCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeExtdPayeeAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeExtdPayeeCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeDelRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeModRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeModRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtCancRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtModRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtPrcStsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtCancRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtModRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecurrInstAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStatusAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSubAddressCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRsCm;

public class BillPayOFX200PaymentMapper
{
  public static boolean mapPmtInfoToPmtRq(PmtInfo paramPmtInfo, TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData)
  {
    if ((paramPmtInfo == null) || (paramTypePmtTrnRqV1 == null) || (paramTypeUserData == null))
    {
      FFSDebug.log("Cannot map from/to null objects", 0);
      return false;
    }
    try
    {
      mapPmtInfoToPmtTrnRq(paramPmtInfo, paramTypePmtTrnRqV1, paramTypeUserData);
    }
    catch (Exception localException)
    {
      FFSDebug.log("Error mapping PmtInfo object into OFX object", 0);
      FFSDebug.log(localException.toString(), 0);
      return false;
    }
    return true;
  }
  
  public static boolean mapRecPmtInfoToRecPmtRq(RecPmtInfo paramRecPmtInfo, TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData)
  {
    if ((paramRecPmtInfo == null) || (paramTypeRecPmtTrnRqV1 == null) || (paramTypeUserData == null))
    {
      FFSDebug.log("Cannot map from/to null objects", 0);
      return false;
    }
    try
    {
      mapRecPmtInfoToRecPmtTrnRq(paramRecPmtInfo, paramTypeRecPmtTrnRqV1, paramTypeUserData);
    }
    catch (Exception localException)
    {
      FFSDebug.log("Error mapping RecPmtInfo object into OFX object", 0);
      return false;
    }
    return true;
  }
  
  public static boolean mapPayeeInfoToPayeeRq(PayeeInfo paramPayeeInfo, CustomerInfo paramCustomerInfo, CustomerPayeeInfo paramCustomerPayeeInfo, String paramString, TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, TypeUserData paramTypeUserData)
  {
    paramTypePayeeTrnRqV1.PayeeTrnRq = new TypePayeeTrnRqV1Aggregate();
    paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm = new TypeTrnRqCm();
    paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm.TrnUID = paramPayeeInfo.TranID;
    paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm.CltCookieExists = false;
    paramTypePayeeTrnRqV1.PayeeTrnRq.TrnRqCm.TANExists = false;
    paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn = new TypePayeeTrnRqUn();
    if (paramString.equalsIgnoreCase("add"))
    {
      paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.__preValueTag = "PAYEERQ";
      paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.__memberName = "PayeeRq";
      paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeRq = new TypePayeeRqAggregate();
      paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeRq.PayeeRqUn = new TypePayeeRqUn();
      if ((paramPayeeInfo.PayeeID != null) && (paramPayeeInfo.PayeeID.trim().length() > 0))
      {
        paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeRq.PayeeRqUn.__memberName = "PayeeID";
        paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeRq.PayeeRqUn.PayeeID = paramPayeeInfo.PayeeID;
      }
      else
      {
        paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeRq.PayeeRqUn.__memberName = "Payee";
      }
      paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeRq.PayeeRqUn.Payee = a(paramPayeeInfo);
      paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeRq.BankAcctToExists = false;
      if (paramCustomerPayeeInfo.PayAcct != null)
      {
        paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeRq.PayAcctExists = true;
        paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeRq.PayAcct = new String[1];
        paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeRq.PayAcct[0] = paramCustomerPayeeInfo.PayAcct;
      }
      else
      {
        paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeRq.PayAcctExists = false;
      }
      paramTypeUserData = a(paramPayeeInfo, paramCustomerInfo, paramTypeUserData);
    }
    else if (paramString.equalsIgnoreCase("mod"))
    {
      paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.__preValueTag = "PAYEEMODRQ";
      paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.__memberName = "PayeeModRq";
      paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeModRq = new TypePayeeModRqAggregate();
      paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeModRq.PayeeLstID = Integer.toString(paramCustomerPayeeInfo.PayeeListID);
      paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeModRq.PayeeModRqCmExists = true;
      paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeModRq.PayeeModRqCm = new TypePayeeModRqCm();
      paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeModRq.PayeeModRqCm.BankAcctToExists = false;
      paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeModRq.PayeeModRqCm.Payee = a(paramPayeeInfo);
      if (paramCustomerPayeeInfo.PayAcct != null)
      {
        paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeModRq.PayAcctExists = true;
        paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeModRq.PayAcct = new String[1];
        paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeModRq.PayAcct[0] = paramCustomerPayeeInfo.PayAcct;
      }
      else
      {
        paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeModRq.PayAcctExists = false;
      }
      paramTypeUserData = a(paramPayeeInfo, paramCustomerInfo, paramTypeUserData);
    }
    else if (paramString.equalsIgnoreCase("del"))
    {
      paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.__preValueTag = "PAYEEDELRQ";
      paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.__memberName = "PayeeDelRq";
      paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeDelRq = new TypePayeeDelRqAggregate();
      paramTypePayeeTrnRqV1.PayeeTrnRq.PayeeTrnRqUn.PayeeDelRq.PayeeLstID = Integer.toString(paramCustomerPayeeInfo.PayeeListID);
      paramTypeUserData = a(paramPayeeInfo, paramCustomerInfo, paramTypeUserData);
    }
    else
    {
      return false;
    }
    return true;
  }
  
  public static PmtInfo mapPmtRsToPmtInfo(TypePmtTrnRsV1 paramTypePmtTrnRsV1, TypeUserData paramTypeUserData)
  {
    if ((paramTypePmtTrnRsV1 == null) || (paramTypeUserData == null)) {
      return null;
    }
    PmtInfo localPmtInfo = new PmtInfo();
    a(paramTypePmtTrnRsV1, paramTypeUserData, localPmtInfo);
    return localPmtInfo;
  }
  
  public static RecPmtInfo mapRecPmtRsToPmtInfo(TypeRecPmtTrnRsV1 paramTypeRecPmtTrnRsV1, TypeUserData paramTypeUserData)
  {
    if ((paramTypeRecPmtTrnRsV1 == null) || (paramTypeUserData == null)) {
      return null;
    }
    RecPmtInfo localRecPmtInfo = new RecPmtInfo();
    a(paramTypeRecPmtTrnRsV1, paramTypeUserData, localRecPmtInfo);
    return localRecPmtInfo;
  }
  
  public static CustomerPayeeInfo mapPayeeRsToCustomerPayeeInfo(TypePayeeTrnRsV1 paramTypePayeeTrnRsV1, TypeUserData paramTypeUserData)
  {
    if ((paramTypePayeeTrnRsV1 == null) || (paramTypeUserData == null)) {
      return null;
    }
    CustomerPayeeInfo localCustomerPayeeInfo = new CustomerPayeeInfo();
    a(paramTypePayeeTrnRsV1, paramTypeUserData, localCustomerPayeeInfo);
    return localCustomerPayeeInfo;
  }
  
  public static PayeeInfo mapPayeeRsToPayeeInfo(TypePayeeTrnRsV1 paramTypePayeeTrnRsV1, TypeUserData paramTypeUserData, PayeeInfo paramPayeeInfo)
  {
    if ((paramTypePayeeTrnRsV1 == null) || (paramTypeUserData == null)) {
      return null;
    }
    if (paramPayeeInfo == null) {
      paramPayeeInfo = new PayeeInfo();
    }
    paramPayeeInfo.setStatusCode(paramTypePayeeTrnRsV1.PayeeTrnRs.TrnRsCm.Status.Code);
    paramPayeeInfo.setStatusMsg(paramTypePayeeTrnRsV1.PayeeTrnRs.TrnRsCm.Status.Message);
    return paramPayeeInfo;
  }
  
  public static boolean mapPmtInfoToPmtTrnRq(PmtInfo paramPmtInfo, TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    paramTypePmtTrnRqV1.PmtTrnRq = new TypePmtTrnRqV1Aggregate();
    paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm = new TypeTrnRqCm();
    paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm.TrnUID = paramPmtInfo.getLogID();
    paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm.CltCookieExists = false;
    paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm.TANExists = false;
    paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn = new TypePmtTrnRqUn();
    String str = paramPmtInfo.getAction();
    if (str.equalsIgnoreCase("add"))
    {
      paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.__preValueTag = "PMTRQ";
      paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.__memberName = "PmtRq";
      paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq = new TypePmtRqAggregate();
      if ((paramPmtInfo.getCurDef() != null) && (paramPmtInfo.getCurDef().trim().length() > 0))
      {
        paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.CurDefExists = true;
        paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.CurDef = MsgBuilder.getOFX200CurrencyEnum(BPWUtil.validateCurrencyString(paramPmtInfo.getCurDef()));
      }
      else
      {
        paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.CurDefExists = false;
      }
      paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo = a(paramPmtInfo);
    }
    else if (str.equalsIgnoreCase("mod"))
    {
      paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.__preValueTag = "PMTMODCRQ";
      paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.__memberName = "PmtModRq";
      paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtModRq = new TypePmtModRqAggregate();
      if ((paramPmtInfo.getCurDef() != null) && (paramPmtInfo.getCurDef().trim().length() > 0))
      {
        paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtModRq.CurDefExists = true;
        paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtModRq.CurDef = MsgBuilder.getOFX200CurrencyEnum(BPWUtil.validateCurrencyString(paramPmtInfo.getCurDef()));
      }
      else
      {
        paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.CurDefExists = false;
      }
      paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtModRq.SrvrTID = paramPmtInfo.getSrvrTID();
      paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtModRq.PmtInfo = a(paramPmtInfo);
    }
    else if (str.equalsIgnoreCase("del"))
    {
      paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.__preValueTag = "PMTCANCRQ";
      paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.__memberName = "PmtCancRq";
      paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtCancRq = new TypePmtCancRqAggregate();
      paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtCancRq.SrvrTID = paramPmtInfo.getSrvrTID();
    }
    else
    {
      FFSDebug.log("Invalid action in PmtInfo object", 0);
    }
    paramTypeUserData = a(paramPmtInfo, paramTypeUserData);
    return true;
  }
  
  public static boolean mapRecPmtInfoToRecPmtTrnRq(RecPmtInfo paramRecPmtInfo, TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    paramTypeRecPmtTrnRqV1.RecPmtTrnRq = new TypeRecPmtTrnRqV1Aggregate();
    paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm = new TypeTrnRqCm();
    paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm.TrnUID = paramRecPmtInfo.getLogID();
    paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm.CltCookieExists = false;
    paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm.TANExists = false;
    paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn = new TypeRecPmtTrnRqUn();
    String str = paramRecPmtInfo.getAction();
    if (str.equals("add"))
    {
      paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.__preValueTag = "RECPMTRQ";
      paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.__memberName = "RecPmtRq";
      paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq = new TypeRecPmtRqAggregate();
      if ((paramRecPmtInfo.getCurDef() != null) && (paramRecPmtInfo.getCurDef().trim().length() > 0))
      {
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.CurDefExists = true;
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.CurDef = MsgBuilder.getOFX200CurrencyEnum(BPWUtil.validateCurrencyString(paramRecPmtInfo.getCurDef()));
      }
      else
      {
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.CurDefExists = false;
      }
      if (!FFSUtil.isZero(paramRecPmtInfo.getInitialAmount()))
      {
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.InitialAmtExists = true;
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.InitialAmt = Double.parseDouble(paramRecPmtInfo.getInitialAmount());
      }
      else
      {
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.InitialAmtExists = false;
      }
      if (!FFSUtil.isZero(paramRecPmtInfo.getFinalAmount()))
      {
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.FinalAmtExists = true;
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.FinalAmt = Double.parseDouble(paramRecPmtInfo.getFinalAmount());
      }
      else
      {
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.FinalAmtExists = false;
      }
      paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.RecurrInst = new TypeRecurrInstAggregate();
      try
      {
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.RecurrInst.Freq = EnumFreqEnum.from_int(paramRecPmtInfo.getRecFrequency().getIFXFreq());
      }
      catch (Exception localException1)
      {
        throw new Exception("Recurring frequency not valid. Freq = " + paramRecPmtInfo.getRecFrequencyStr());
      }
      if (paramRecPmtInfo.getInstanceCount() > 0)
      {
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.RecurrInst.NInsts = paramRecPmtInfo.getInstanceCount();
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.RecurrInst.NInstsExists = true;
      }
      else
      {
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.RecurrInst.NInstsExists = false;
      }
      paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.PmtInfo = a(paramRecPmtInfo);
    }
    else if (str.equals("mod"))
    {
      paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.__preValueTag = "RECPMTMODRQ";
      paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.__memberName = "RecPmtModRq";
      paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq = new TypeRecPmtModRqAggregate();
      paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.RecSrvrTID = paramRecPmtInfo.RecSrvrTID;
      if ((paramRecPmtInfo.getCurDef() != null) && (paramRecPmtInfo.getCurDef().trim().length() > 0))
      {
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.CurDefExists = true;
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.CurDef = MsgBuilder.getOFX200CurrencyEnum(BPWUtil.validateCurrencyString(paramRecPmtInfo.getCurDef()));
      }
      else
      {
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.CurDefExists = false;
      }
      if (!FFSUtil.isZero(paramRecPmtInfo.getInitialAmount()))
      {
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.InitialAmtExists = true;
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.InitialAmt = Double.parseDouble(paramRecPmtInfo.getInitialAmount());
      }
      else
      {
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.InitialAmtExists = false;
      }
      if (!FFSUtil.isZero(paramRecPmtInfo.getFinalAmount()))
      {
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.FinalAmtExists = true;
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.FinalAmt = Double.parseDouble(paramRecPmtInfo.getFinalAmount());
      }
      else
      {
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.FinalAmtExists = false;
      }
      paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.RecurrInst = new TypeRecurrInstAggregate();
      try
      {
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.RecurrInst.Freq = EnumFreqEnum.from_int(paramRecPmtInfo.getRecFrequency().getIFXFreq());
      }
      catch (Exception localException2)
      {
        throw new Exception("Recurring frequency not valid. Freq = " + paramRecPmtInfo.getRecFrequencyStr());
      }
      if (paramRecPmtInfo.getInstanceCount() > 0)
      {
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.RecurrInst.NInsts = paramRecPmtInfo.getInstanceCount();
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.RecurrInst.NInstsExists = true;
      }
      else
      {
        paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.RecurrInst.NInstsExists = false;
      }
      paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.PmtInfo = a(paramRecPmtInfo);
    }
    else if (str.equals("del"))
    {
      paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.__preValueTag = "RECPMTCANCRQ";
      paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.__memberName = "RecPmtCancRq";
      paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtCancRq = new TypeRecPmtCancRqAggregate();
      paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtCancRq.RecSrvrTID = paramRecPmtInfo.getRecSrvrTID();
      paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtCancRq.CanPending = true;
    }
    else
    {
      FFSDebug.log("Invalid action in RecPmtInfo object", 0);
    }
    paramTypeUserData = a(paramRecPmtInfo, paramTypeUserData);
    return true;
  }
  
  private static TypePmtInfoAggregate a(PmtInfo paramPmtInfo)
  {
    TypePmtInfoAggregate localTypePmtInfoAggregate = new TypePmtInfoAggregate();
    localTypePmtInfoAggregate.BankAcctFrom = new TypeBankAcctFromAggregate();
    localTypePmtInfoAggregate.BankAcctFrom.BankID = paramPmtInfo.getBankID();
    localTypePmtInfoAggregate.BankAcctFrom.AcctID = paramPmtInfo.getAcctDebitID();
    localTypePmtInfoAggregate.BankAcctFrom.BranchIDExists = false;
    localTypePmtInfoAggregate.BankAcctFrom.AcctKeyExists = false;
    try
    {
      localTypePmtInfoAggregate.BankAcctFrom.AcctType = new AccountTypesMap().getAccountEnumOFX200(paramPmtInfo.AcctDebitType);
    }
    catch (Exception localException)
    {
      FFSDebug.log("Error mapping AcctDebitType", 0);
      FFSDebug.log(localException.toString(), 0);
    }
    localTypePmtInfoAggregate.TrnAmt = Double.parseDouble(paramPmtInfo.getAmt());
    if ((paramPmtInfo.getPayAcct() == null) || (paramPmtInfo.getPayAcct().trim().equals(""))) {
      localTypePmtInfoAggregate.PayAcct = "NONE";
    } else {
      localTypePmtInfoAggregate.PayAcct = paramPmtInfo.getPayAcct();
    }
    localTypePmtInfoAggregate.DtDue = new Integer(paramPmtInfo.getStartDate()).toString().substring(0, 8);
    if (paramPmtInfo.getPayeeListID() == 0)
    {
      FFSDebug.log("payeeListId is null", 0);
      localTypePmtInfoAggregate.PayeeLstID = null;
      localTypePmtInfoAggregate.PayeeLstIDExists = false;
    }
    else
    {
      localTypePmtInfoAggregate.PayeeLstID = new Integer(paramPmtInfo.getPayeeListID()).toString();
      localTypePmtInfoAggregate.PayeeLstIDExists = true;
      FFSDebug.log("payeeListId is: " + localTypePmtInfoAggregate.PayeeLstID, 0);
    }
    if ((paramPmtInfo.getMemo() != null) && (paramPmtInfo.getMemo().trim().length() != 0))
    {
      localTypePmtInfoAggregate.MemoExists = true;
      localTypePmtInfoAggregate.Memo = paramPmtInfo.getMemo();
    }
    else
    {
      localTypePmtInfoAggregate.MemoExists = false;
      localTypePmtInfoAggregate.Memo = null;
    }
    localTypePmtInfoAggregate.BillRefInfoExists = false;
    localTypePmtInfoAggregate.NameOnAcctExists = false;
    localTypePmtInfoAggregate.BankAcctToExists = false;
    localTypePmtInfoAggregate.PayeeUn = jdMethod_if(paramPmtInfo);
    return localTypePmtInfoAggregate;
  }
  
  private static TypePayeeUn jdMethod_if(PmtInfo paramPmtInfo)
  {
    TypePayeeUn localTypePayeeUn = new TypePayeeUn();
    localTypePayeeUn.__memberName = "Payee";
    if ((paramPmtInfo.getPayeeID() != null) && (paramPmtInfo.getPayeeID().trim().length() > 0))
    {
      localTypePayeeUn.PayeeID = paramPmtInfo.getPayeeID();
      localTypePayeeUn.Payee = a(paramPmtInfo.getPayeeInfo());
    }
    else if (paramPmtInfo.getPayeeInfo() != null)
    {
      PayeeInfo localPayeeInfo = paramPmtInfo.getPayeeInfo();
      localTypePayeeUn.Payee = a(localPayeeInfo);
    }
    else
    {
      FFSDebug.log("PayeeID and PayeeInfo both null", 6);
      localTypePayeeUn = null;
    }
    return localTypePayeeUn;
  }
  
  private static TypePayeeAggregate a(PayeeInfo paramPayeeInfo)
  {
    TypePayeeAggregate localTypePayeeAggregate = new TypePayeeAggregate();
    if (paramPayeeInfo == null) {
      return localTypePayeeAggregate;
    }
    localTypePayeeAggregate.Name = paramPayeeInfo.PayeeName;
    localTypePayeeAggregate.City = paramPayeeInfo.City;
    localTypePayeeAggregate.State = paramPayeeInfo.State;
    localTypePayeeAggregate.PostalCode = paramPayeeInfo.Zipcode;
    if ((paramPayeeInfo.Country != null) && (paramPayeeInfo.Country.trim().length() > 0))
    {
      localTypePayeeAggregate.CountryExists = true;
      localTypePayeeAggregate.Country = paramPayeeInfo.Country;
    }
    else
    {
      localTypePayeeAggregate.CountryExists = false;
    }
    localTypePayeeAggregate.Phone = paramPayeeInfo.Phone;
    if ((paramPayeeInfo.Phone == null) || (paramPayeeInfo.Phone.trim().equals(""))) {
      localTypePayeeAggregate.Phone = "NONE";
    } else {
      localTypePayeeAggregate.Phone = paramPayeeInfo.Phone;
    }
    localTypePayeeAggregate.AddressCm = new TypeAddressCm();
    localTypePayeeAggregate.AddressCm.Addr1 = paramPayeeInfo.Addr1;
    if ((paramPayeeInfo.Addr2 != null) && (paramPayeeInfo.Addr2.trim().length() > 0))
    {
      localTypePayeeAggregate.AddressCm.SubAddressCmExists = true;
      localTypePayeeAggregate.AddressCm.SubAddressCm = new TypeSubAddressCm();
      localTypePayeeAggregate.AddressCm.SubAddressCm.Addr2 = paramPayeeInfo.Addr2;
      if ((paramPayeeInfo.Addr3 != null) && (paramPayeeInfo.Addr3.trim().length() > 0))
      {
        localTypePayeeAggregate.AddressCm.SubAddressCm.Addr3Exists = true;
        localTypePayeeAggregate.AddressCm.SubAddressCm.Addr3 = paramPayeeInfo.Addr3;
      }
      else
      {
        localTypePayeeAggregate.AddressCm.SubAddressCm.Addr3Exists = false;
        localTypePayeeAggregate.AddressCm.SubAddressCm.Addr3 = null;
      }
    }
    else
    {
      localTypePayeeAggregate.AddressCm.SubAddressCmExists = false;
      localTypePayeeAggregate.AddressCm.SubAddressCm = null;
    }
    return localTypePayeeAggregate;
  }
  
  private static boolean a(TypePmtTrnRsV1 paramTypePmtTrnRsV1, TypeUserData paramTypeUserData, PmtInfo paramPmtInfo)
  {
    paramPmtInfo.setStatusCode(paramTypePmtTrnRsV1.PmtTrnRs.TrnRsCm.Status.Code);
    paramPmtInfo.setStatusMsg(paramTypePmtTrnRsV1.PmtTrnRs.TrnRsCm.Status.Message);
    if (paramPmtInfo.statusCode == 0)
    {
      if (paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode == null) {
        FFSDebug.log("a2 pmtprccode is null", 0);
      }
      paramPmtInfo.setStatus(a(paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode));
      paramPmtInfo.setDateToPost(paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc);
      paramPmtInfo.setSrvrTID(paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.SrvrTID);
      paramPmtInfo.setLogID(paramTypePmtTrnRsV1.PmtTrnRs.TrnRsCm.TrnUID);
    }
    paramPmtInfo = a(paramTypeUserData, paramPmtInfo);
    return true;
  }
  
  private static boolean a(TypeRecPmtTrnRsV1 paramTypeRecPmtTrnRsV1, TypeUserData paramTypeUserData, PmtInfo paramPmtInfo)
  {
    paramPmtInfo.setStatusCode(paramTypeRecPmtTrnRsV1.RecPmtTrnRs.TrnRsCm.Status.Code);
    paramPmtInfo.setStatusMsg(paramTypeRecPmtTrnRsV1.RecPmtTrnRs.TrnRsCm.Status.Message);
    if (paramPmtInfo.statusCode == 0)
    {
      paramPmtInfo.setSrvrTID(paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecSrvrTID);
      paramPmtInfo.setLogID(paramTypeRecPmtTrnRsV1.RecPmtTrnRs.TrnRsCm.TrnUID);
    }
    paramPmtInfo = a(paramTypeUserData, paramPmtInfo);
    return true;
  }
  
  private static boolean a(TypePayeeTrnRsV1 paramTypePayeeTrnRsV1, TypeUserData paramTypeUserData, CustomerPayeeInfo paramCustomerPayeeInfo)
  {
    paramCustomerPayeeInfo.setStatusCode(paramTypePayeeTrnRsV1.PayeeTrnRs.TrnRsCm.Status.Code);
    paramCustomerPayeeInfo.setStatusMsg(paramTypePayeeTrnRsV1.PayeeTrnRs.TrnRsCm.Status.Message);
    if ((paramTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUnExists) && (paramTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs != null))
    {
      paramCustomerPayeeInfo.PayeeListID = Integer.parseInt(paramTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayeeLstID);
      if ((paramTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.ExtdPayeeExists) && (paramTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.ExtdPayee.ExtdPayeeCmExists)) {
        paramCustomerPayeeInfo.PayeeID = paramTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.ExtdPayee.ExtdPayeeCm.PayeeID;
      }
    }
    return true;
  }
  
  private static String a(EnumPmtProcessStatusEnum paramEnumPmtProcessStatusEnum)
  {
    FFSDebug.log("1", 0);
    String str = null;
    if (paramEnumPmtProcessStatusEnum == null) {
      return str;
    }
    FFSDebug.log("2", 0);
    switch (paramEnumPmtProcessStatusEnum.value())
    {
    case 0: 
      str = "CANCELEDON";
      break;
    case 1: 
      str = "FAILEDON";
      break;
    case 2: 
      str = "NOFUNDSON";
      break;
    case 3: 
      str = "PROCESSEDON";
      break;
    case 4: 
      str = "WILLPROCESSON";
      break;
    default: 
      str = null;
    }
    FFSDebug.log("3", 0);
    return str;
  }
  
  private static TypeUserData a(PayeeInfo paramPayeeInfo, CustomerInfo paramCustomerInfo, TypeUserData paramTypeUserData)
  {
    String str = paramPayeeInfo.getSubmittedBy();
    if (str == null) {
      str = paramCustomerInfo.getCustomerID();
    }
    return a(paramTypeUserData, paramCustomerInfo.fIID, paramCustomerInfo.getCustomerID(), str, paramPayeeInfo.TranID);
  }
  
  private static TypeUserData a(PmtInfo paramPmtInfo, TypeUserData paramTypeUserData)
  {
    String str = paramPmtInfo.getSubmittedBy();
    if (str == null) {
      str = paramPmtInfo.getCustomerID();
    }
    return a(paramTypeUserData, paramPmtInfo.getFIID(), paramPmtInfo.getCustomerID(), str, paramPmtInfo.getLogID());
  }
  
  private static PmtInfo a(TypeUserData paramTypeUserData, PmtInfo paramPmtInfo)
  {
    paramPmtInfo.setFIID(paramTypeUserData._fid);
    paramPmtInfo.setCustomerID(paramTypeUserData._uid);
    paramPmtInfo.setSubmittedBy(paramTypeUserData._submittedBy);
    paramPmtInfo.setLogID(paramTypeUserData._tran_id);
    return paramPmtInfo;
  }
  
  private static TypeUserData a(TypeUserData paramTypeUserData, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    paramTypeUserData._ofxHeader = "100";
    paramTypeUserData._version = "200";
    paramTypeUserData._security = "NONE";
    paramTypeUserData._oldFileUID = "";
    paramTypeUserData._newFileUID = "";
    paramTypeUserData._data = "OFXSGML";
    paramTypeUserData._encoding = "USASCII";
    paramTypeUserData._charSet = "1252";
    paramTypeUserData._compression = "";
    paramTypeUserData._org = "";
    paramTypeUserData._userDefined = null;
    paramTypeUserData._fid = paramString1;
    paramTypeUserData._uid = paramString2;
    paramTypeUserData._submittedBy = paramString3;
    paramTypeUserData._tran_id = paramString4;
    return paramTypeUserData;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.BillPayOFX200PaymentMapper
 * JD-Core Version:    0.7.0.1
 */