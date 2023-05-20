package com.ffusion.ffs.bpw.serviceMsg;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInterRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInterRsV1DateUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraRsV1DateUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSonRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSonRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStatusV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeUserInfoV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInterRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInterRsDateUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRsDateUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStatusAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRsCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeWireRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeWireRsUn;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RsCmBuilder
  implements DBConsts
{
  public TypeSonRsV1Aggregate buildSonRsV1(TypeSonRqV1Aggregate paramTypeSonRqV1Aggregate, TypeUserData paramTypeUserData)
    throws BPWException
  {
    TypeSonRsV1Aggregate localTypeSonRsV1Aggregate = new TypeSonRsV1Aggregate();
    Date localDate = new Date();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String str = localSimpleDateFormat.format(localDate);
    localTypeSonRsV1Aggregate.DtServer = str;
    localTypeSonRsV1Aggregate.TSKeyExpireExists = false;
    localTypeSonRsV1Aggregate.DtProfUpExists = false;
    localTypeSonRsV1Aggregate.DtAcctUpExists = false;
    localTypeSonRsV1Aggregate.SessCookieExists = paramTypeSonRqV1Aggregate.SessCookieExists;
    localTypeSonRsV1Aggregate.SessCookie = paramTypeSonRqV1Aggregate.SessCookie;
    localTypeSonRsV1Aggregate.FIExists = paramTypeSonRqV1Aggregate.FIExists;
    localTypeSonRsV1Aggregate.FI = paramTypeSonRqV1Aggregate.FI;
    localTypeSonRsV1Aggregate.Language = paramTypeSonRqV1Aggregate.Language;
    if (paramTypeSonRqV1Aggregate.UserInfoV1Un.__memberName.equals("UserKey"))
    {
      localTypeSonRsV1Aggregate.UserKeyExists = false;
      localTypeSonRsV1Aggregate.UserKey = paramTypeSonRqV1Aggregate.UserInfoV1Un.UserKey;
    }
    else if (!paramTypeSonRqV1Aggregate.UserInfoV1Un.__memberName.equals("UserV1Cm"))
    {
      throw new BPWException("Error UserInfo Message !");
    }
    if (localTypeSonRsV1Aggregate.Status == null) {
      localTypeSonRsV1Aggregate.Status = buildOFX151StatusV1("Signon is successful.", 0, com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumSeverityEnum.INFO);
    }
    return localTypeSonRsV1Aggregate;
  }
  
  public static void updateStatusV1Aggregate(TypeStatusV1Aggregate paramTypeStatusV1Aggregate, int paramInt)
  {
    paramTypeStatusV1Aggregate.Code = paramInt;
    paramTypeStatusV1Aggregate.Severity = MsgBuilder.getOFX151CmStatusSeverity(paramInt);
    paramTypeStatusV1Aggregate.Message = MsgBuilder.getCmStatusMessage(paramInt);
    paramTypeStatusV1Aggregate.MessageExists = true;
  }
  
  public static void updateStatusV1Aggregate(TypeStatusAggregate paramTypeStatusAggregate, int paramInt)
  {
    paramTypeStatusAggregate.Code = paramInt;
    paramTypeStatusAggregate.Severity = MsgBuilder.getOFX200CmStatusSeverity(paramInt);
    paramTypeStatusAggregate.Message = MsgBuilder.getCmStatusMessage(paramInt);
    paramTypeStatusAggregate.MessageExists = true;
  }
  
  public static TypeTrnRsV1Cm buildTrnRsCmV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRqCm paramTypeTrnRqCm, int paramInt)
  {
    String str = MsgBuilder.getCmStatusMessage(paramInt);
    return a(paramTypeTrnRqCm, paramInt, str);
  }
  
  public static TypeTrnRsV1Cm buildTrnRsCmV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRqCm paramTypeTrnRqCm, int paramInt, String paramString)
  {
    String str;
    if (paramInt == 2000) {
      str = paramString;
    } else {
      str = MsgBuilder.getCmStatusMessage(paramInt);
    }
    return a(paramTypeTrnRqCm, paramInt, str);
  }
  
  private static TypeTrnRsV1Cm a(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRqCm paramTypeTrnRqCm, int paramInt, String paramString)
  {
    TypeTrnRsV1Cm localTypeTrnRsV1Cm = new TypeTrnRsV1Cm();
    localTypeTrnRsV1Cm.TrnUID = paramTypeTrnRqCm.TrnUID;
    localTypeTrnRsV1Cm.CltCookieExists = paramTypeTrnRqCm.CltCookieExists;
    localTypeTrnRsV1Cm.CltCookie = paramTypeTrnRqCm.CltCookie;
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumSeverityEnum localEnumSeverityEnum = MsgBuilder.getOFX151CmStatusSeverity(paramInt);
    localTypeTrnRsV1Cm.Status = buildOFX151StatusV1(paramString, paramInt, localEnumSeverityEnum);
    return localTypeTrnRsV1Cm;
  }
  
  public static TypeTrnRsCm buildTrnRsCmV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRqCm paramTypeTrnRqCm, int paramInt)
  {
    String str = MsgBuilder.getCmStatusMessage(paramInt);
    return a(paramTypeTrnRqCm, paramInt, str);
  }
  
  public static TypeTrnRsCm buildTrnRsCmV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRqCm paramTypeTrnRqCm, int paramInt, String paramString)
  {
    String str;
    if (paramInt == 2000) {
      str = paramString;
    } else {
      str = MsgBuilder.getCmStatusMessage(paramInt);
    }
    return a(paramTypeTrnRqCm, paramInt, str);
  }
  
  private static TypeTrnRsCm a(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRqCm paramTypeTrnRqCm, int paramInt, String paramString)
  {
    TypeTrnRsCm localTypeTrnRsCm = new TypeTrnRsCm();
    localTypeTrnRsCm.TrnUID = paramTypeTrnRqCm.TrnUID;
    localTypeTrnRsCm.CltCookieExists = paramTypeTrnRqCm.CltCookieExists;
    localTypeTrnRsCm.CltCookie = paramTypeTrnRqCm.CltCookie;
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumSeverityEnum localEnumSeverityEnum = MsgBuilder.getOFX200CmStatusSeverity(paramInt);
    localTypeTrnRsCm.Status = buildOFX200StatusV1(paramString, paramInt, localEnumSeverityEnum);
    return localTypeTrnRsCm;
  }
  
  public static TypeTrnRsV1Cm buildTrnRsCmV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRqCm paramTypeTrnRqCm, TypeUserData paramTypeUserData)
  {
    TypeTrnRsV1Cm localTypeTrnRsV1Cm = new TypeTrnRsV1Cm();
    localTypeTrnRsV1Cm.TrnUID = paramTypeTrnRqCm.TrnUID;
    localTypeTrnRsV1Cm.CltCookieExists = paramTypeTrnRqCm.CltCookieExists;
    localTypeTrnRsV1Cm.CltCookie = paramTypeTrnRqCm.CltCookie;
    localTypeTrnRsV1Cm.Status = buildOFX151StatusV1(null, 0, com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumSeverityEnum.INFO);
    return localTypeTrnRsV1Cm;
  }
  
  public static TypeTrnRsCm buildTrnRsCmV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRqCm paramTypeTrnRqCm, TypeUserData paramTypeUserData)
  {
    TypeTrnRsCm localTypeTrnRsCm = new TypeTrnRsCm();
    localTypeTrnRsCm.TrnUID = paramTypeTrnRqCm.TrnUID;
    localTypeTrnRsCm.CltCookieExists = paramTypeTrnRqCm.CltCookieExists;
    localTypeTrnRsCm.CltCookie = paramTypeTrnRqCm.CltCookie;
    localTypeTrnRsCm.Status = buildOFX200StatusV1(null, 0, com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumSeverityEnum.INFO);
    return localTypeTrnRsCm;
  }
  
  public static TypeStatusV1Aggregate buildOFX151StatusV1(String paramString, int paramInt, com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumSeverityEnum paramEnumSeverityEnum)
  {
    TypeStatusV1Aggregate localTypeStatusV1Aggregate = new TypeStatusV1Aggregate();
    localTypeStatusV1Aggregate.Code = paramInt;
    if (paramString != null) {
      localTypeStatusV1Aggregate.MessageExists = true;
    }
    localTypeStatusV1Aggregate.Message = paramString;
    localTypeStatusV1Aggregate.Severity = paramEnumSeverityEnum;
    return localTypeStatusV1Aggregate;
  }
  
  public static TypeStatusAggregate buildOFX200StatusV1(String paramString, int paramInt, com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumSeverityEnum paramEnumSeverityEnum)
  {
    TypeStatusAggregate localTypeStatusAggregate = new TypeStatusAggregate();
    localTypeStatusAggregate.Code = paramInt;
    if (paramString != null) {
      localTypeStatusAggregate.MessageExists = true;
    }
    localTypeStatusAggregate.Message = paramString;
    localTypeStatusAggregate.Severity = paramEnumSeverityEnum;
    return localTypeStatusAggregate;
  }
  
  public static void updateRsXferPrcSts(String paramString1, String paramString2, com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeXferPrcStsAggregate paramTypeXferPrcStsAggregate)
  {
    if (paramString1.equals("POSTEDON")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumXferStatusEnum.POSTEDON;
    } else if (paramString1.equals("FAILEDON")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumXferStatusEnum.FAILEDON;
    } else if (paramString1.equals("WILLPROCESSON")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumXferStatusEnum.WILLPROCESSON;
    } else if (paramString1.equals("APPROVAL_PENDING")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumXferStatusEnum.WILLPROCESSON;
    } else if (paramString1.equals("NOFUNDSON")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumXferStatusEnum.NOFUNDSON;
    } else if (paramString1.equals("CANCELEDON")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumXferStatusEnum.CANCELEDON;
    }
    paramTypeXferPrcStsAggregate.DtXferPrc = paramString2;
  }
  
  public static void updateRsXferPrcSts(String paramString1, String paramString2, com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferPrcStsAggregate paramTypeXferPrcStsAggregate)
  {
    if (paramString1.equals("POSTEDON")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumXferStatusEnum.POSTEDON;
    } else if (paramString1.equals("FAILEDON")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumXferStatusEnum.FAILEDON;
    } else if (paramString1.equals("WILLPROCESSON")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumXferStatusEnum.WILLPROCESSON;
    } else if (paramString1.equals("APPROVAL_PENDING")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumXferStatusEnum.WILLPROCESSON;
    } else if (paramString1.equals("NOFUNDSON")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumXferStatusEnum.NOFUNDSON;
    } else if (paramString1.equals("CANCELEDON")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumXferStatusEnum.CANCELEDON;
    }
    paramTypeXferPrcStsAggregate.DtXferPrc = paramString2;
  }
  
  public static void updateRsInterPrcSts(String paramString1, String paramString2, com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeXferPrcStsAggregate paramTypeXferPrcStsAggregate)
  {
    if (paramString1.equals("POSTEDON")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumXferStatusEnum.POSTEDON;
    } else if (paramString1.equals("FAILEDON")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumXferStatusEnum.FAILEDON;
    } else if (paramString1.equals("WILLPROCESSON")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumXferStatusEnum.WILLPROCESSON;
    } else if (paramString1.equals("NOFUNDSON")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumXferStatusEnum.NOFUNDSON;
    } else if (paramString1.equals("CANCELEDON")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumXferStatusEnum.CANCELEDON;
    }
    paramTypeXferPrcStsAggregate.DtXferPrc = paramString2;
  }
  
  public static void updateRsInterPrcSts(String paramString1, String paramString2, com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferPrcStsAggregate paramTypeXferPrcStsAggregate)
  {
    if (paramString1.equals("POSTEDON")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumXferStatusEnum.POSTEDON;
    } else if (paramString1.equals("FAILEDON")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumXferStatusEnum.FAILEDON;
    } else if (paramString1.equals("WILLPROCESSON")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumXferStatusEnum.WILLPROCESSON;
    } else if (paramString1.equals("NOFUNDSON")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumXferStatusEnum.NOFUNDSON;
    } else if (paramString1.equals("CANCELEDON")) {
      paramTypeXferPrcStsAggregate.XferPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumXferStatusEnum.CANCELEDON;
    }
    paramTypeXferPrcStsAggregate.DtXferPrc = paramString2;
  }
  
  public static void updateXferRsDatePosted(String paramString, TypeIntraRsV1Aggregate paramTypeIntraRsV1Aggregate)
  {
    if (!paramTypeIntraRsV1Aggregate.IntraRsV1DateUnExists)
    {
      paramTypeIntraRsV1Aggregate.IntraRsV1DateUnExists = true;
      TypeIntraRsV1DateUn localTypeIntraRsV1DateUn = new TypeIntraRsV1DateUn();
      paramTypeIntraRsV1Aggregate.IntraRsV1DateUn = localTypeIntraRsV1DateUn;
    }
    paramTypeIntraRsV1Aggregate.IntraRsV1DateUn.__memberName = "DtPosted";
    paramTypeIntraRsV1Aggregate.IntraRsV1DateUn.DtXferPrj = null;
    paramTypeIntraRsV1Aggregate.IntraRsV1DateUn.DtPosted = paramString;
  }
  
  public static void updateXferRsDatePosted(String paramString, TypeIntraRsAggregate paramTypeIntraRsAggregate)
  {
    if (!paramTypeIntraRsAggregate.IntraRsDateUnExists)
    {
      paramTypeIntraRsAggregate.IntraRsDateUnExists = true;
      TypeIntraRsDateUn localTypeIntraRsDateUn = new TypeIntraRsDateUn();
      paramTypeIntraRsAggregate.IntraRsDateUn = localTypeIntraRsDateUn;
    }
    paramTypeIntraRsAggregate.IntraRsDateUn.__memberName = "DtPosted";
    paramTypeIntraRsAggregate.IntraRsDateUn.DtXferPrj = null;
    paramTypeIntraRsAggregate.IntraRsDateUn.DtPosted = paramString;
  }
  
  public static void updateXferRsDateXferPrj(String paramString, TypeIntraRsV1Aggregate paramTypeIntraRsV1Aggregate)
  {
    if (!paramTypeIntraRsV1Aggregate.IntraRsV1DateUnExists)
    {
      paramTypeIntraRsV1Aggregate.IntraRsV1DateUnExists = true;
      TypeIntraRsV1DateUn localTypeIntraRsV1DateUn = new TypeIntraRsV1DateUn();
      paramTypeIntraRsV1Aggregate.IntraRsV1DateUn = localTypeIntraRsV1DateUn;
    }
    paramTypeIntraRsV1Aggregate.IntraRsV1DateUn.__memberName = "DtXferPrj";
    paramTypeIntraRsV1Aggregate.IntraRsV1DateUn.DtXferPrj = paramString;
    paramTypeIntraRsV1Aggregate.IntraRsV1DateUn.DtPosted = null;
  }
  
  public static void updateXferRsDateXferPrj(String paramString, TypeIntraRsAggregate paramTypeIntraRsAggregate)
  {
    if (!paramTypeIntraRsAggregate.IntraRsDateUnExists)
    {
      paramTypeIntraRsAggregate.IntraRsDateUnExists = true;
      TypeIntraRsDateUn localTypeIntraRsDateUn = new TypeIntraRsDateUn();
      paramTypeIntraRsAggregate.IntraRsDateUn = localTypeIntraRsDateUn;
    }
    paramTypeIntraRsAggregate.IntraRsDateUn.__memberName = "DtXferPrj";
    paramTypeIntraRsAggregate.IntraRsDateUn.DtXferPrj = paramString;
    paramTypeIntraRsAggregate.IntraRsDateUn.DtPosted = null;
  }
  
  public static void updateWireRsDateXferPrj(String paramString, TypeWireRsV1Aggregate paramTypeWireRsV1Aggregate)
  {
    if (!paramTypeWireRsV1Aggregate.WireRsV1UnExists)
    {
      paramTypeWireRsV1Aggregate.WireRsV1UnExists = true;
      TypeWireRsV1Un localTypeWireRsV1Un = new TypeWireRsV1Un();
      paramTypeWireRsV1Aggregate.WireRsV1Un = localTypeWireRsV1Un;
    }
    paramTypeWireRsV1Aggregate.WireRsV1Un.__memberName = "DtXferPrj";
    paramTypeWireRsV1Aggregate.WireRsV1Un.DtXferPrj = paramString;
    paramTypeWireRsV1Aggregate.WireRsV1Un.DtPosted = null;
  }
  
  public static void updateWireRsDateXferPrj(String paramString, TypeWireRsAggregate paramTypeWireRsAggregate)
  {
    if (!paramTypeWireRsAggregate.WireRsUnExists)
    {
      paramTypeWireRsAggregate.WireRsUnExists = true;
      TypeWireRsUn localTypeWireRsUn = new TypeWireRsUn();
      paramTypeWireRsAggregate.WireRsUn = localTypeWireRsUn;
    }
    paramTypeWireRsAggregate.WireRsUn.__memberName = "DtXferPrj";
    paramTypeWireRsAggregate.WireRsUn.DtXferPrj = paramString;
    paramTypeWireRsAggregate.WireRsUn.DtPosted = null;
  }
  
  public static void updateWireRsDatePosted(String paramString, TypeWireRsV1Aggregate paramTypeWireRsV1Aggregate)
  {
    if (!paramTypeWireRsV1Aggregate.WireRsV1UnExists)
    {
      paramTypeWireRsV1Aggregate.WireRsV1UnExists = true;
      TypeWireRsV1Un localTypeWireRsV1Un = new TypeWireRsV1Un();
      paramTypeWireRsV1Aggregate.WireRsV1Un = localTypeWireRsV1Un;
    }
    paramTypeWireRsV1Aggregate.WireRsV1Un.__memberName = "DtPosted";
    paramTypeWireRsV1Aggregate.WireRsV1Un.DtXferPrj = null;
    paramTypeWireRsV1Aggregate.WireRsV1Un.DtPosted = paramString;
  }
  
  public static void updateWireRsDatePosted(String paramString, TypeWireRsAggregate paramTypeWireRsAggregate)
  {
    if (!paramTypeWireRsAggregate.WireRsUnExists)
    {
      paramTypeWireRsAggregate.WireRsUnExists = true;
      TypeWireRsUn localTypeWireRsUn = new TypeWireRsUn();
      paramTypeWireRsAggregate.WireRsUn = localTypeWireRsUn;
    }
    paramTypeWireRsAggregate.WireRsUn.__memberName = "DtPosted";
    paramTypeWireRsAggregate.WireRsUn.DtXferPrj = null;
    paramTypeWireRsAggregate.WireRsUn.DtPosted = paramString;
  }
  
  public static void updateInterRsDatePosted(String paramString, TypeInterRsV1Aggregate paramTypeInterRsV1Aggregate)
  {
    if (!paramTypeInterRsV1Aggregate.InterRsV1DateUnExists)
    {
      paramTypeInterRsV1Aggregate.InterRsV1DateUnExists = true;
      TypeInterRsV1DateUn localTypeInterRsV1DateUn = new TypeInterRsV1DateUn();
      paramTypeInterRsV1Aggregate.InterRsV1DateUn = localTypeInterRsV1DateUn;
    }
    paramTypeInterRsV1Aggregate.InterRsV1DateUn.__memberName = "DtPosted";
    paramTypeInterRsV1Aggregate.InterRsV1DateUn.DtXferPrj = null;
    paramTypeInterRsV1Aggregate.InterRsV1DateUn.DtPosted = paramString;
  }
  
  public static void updateWireFeeAndConfirmMsg(float paramFloat, String paramString, TypeWireRsV1Aggregate paramTypeWireRsV1Aggregate)
  {
    if (!paramTypeWireRsV1Aggregate.FeeExists) {
      paramTypeWireRsV1Aggregate.FeeExists = true;
    }
    if (!paramTypeWireRsV1Aggregate.ConfMsgExists) {
      paramTypeWireRsV1Aggregate.ConfMsgExists = true;
    }
    paramTypeWireRsV1Aggregate.Fee = paramFloat;
    paramTypeWireRsV1Aggregate.ConfMsg = paramString;
  }
  
  public static void updateWireFeeAndConfirmMsg(float paramFloat, String paramString, TypeWireRsAggregate paramTypeWireRsAggregate)
  {
    if (!paramTypeWireRsAggregate.FeeExists) {
      paramTypeWireRsAggregate.FeeExists = true;
    }
    if (!paramTypeWireRsAggregate.ConfMsgExists) {
      paramTypeWireRsAggregate.ConfMsgExists = true;
    }
    paramTypeWireRsAggregate.Fee = paramFloat;
    paramTypeWireRsAggregate.ConfMsg = paramString;
  }
  
  public static void updateInterRsDatePosted(String paramString, TypeInterRsAggregate paramTypeInterRsAggregate)
  {
    if (!paramTypeInterRsAggregate.InterRsDateUnExists)
    {
      paramTypeInterRsAggregate.InterRsDateUnExists = true;
      TypeInterRsDateUn localTypeInterRsDateUn = new TypeInterRsDateUn();
      paramTypeInterRsAggregate.InterRsDateUn = localTypeInterRsDateUn;
    }
    paramTypeInterRsAggregate.InterRsDateUn.__memberName = "DtPosted";
    paramTypeInterRsAggregate.InterRsDateUn.DtXferPrj = null;
    paramTypeInterRsAggregate.InterRsDateUn.DtPosted = paramString;
  }
  
  public static void updateInterRsDateInterPrj(String paramString, TypeInterRsV1Aggregate paramTypeInterRsV1Aggregate)
  {
    if (!paramTypeInterRsV1Aggregate.InterRsV1DateUnExists)
    {
      paramTypeInterRsV1Aggregate.InterRsV1DateUnExists = true;
      TypeInterRsV1DateUn localTypeInterRsV1DateUn = new TypeInterRsV1DateUn();
      paramTypeInterRsV1Aggregate.InterRsV1DateUn = localTypeInterRsV1DateUn;
    }
    paramTypeInterRsV1Aggregate.InterRsV1DateUn.__memberName = "DtXferPrj";
    paramTypeInterRsV1Aggregate.InterRsV1DateUn.DtXferPrj = paramString;
    paramTypeInterRsV1Aggregate.InterRsV1DateUn.DtPosted = null;
  }
  
  public static void updateInterRsDateInterPrj(String paramString, TypeInterRsAggregate paramTypeInterRsAggregate)
  {
    if (!paramTypeInterRsAggregate.InterRsDateUnExists)
    {
      paramTypeInterRsAggregate.InterRsDateUnExists = true;
      TypeInterRsDateUn localTypeInterRsDateUn = new TypeInterRsDateUn();
      paramTypeInterRsAggregate.InterRsDateUn = localTypeInterRsDateUn;
    }
    paramTypeInterRsAggregate.InterRsDateUn.__memberName = "DtXferPrj";
    paramTypeInterRsAggregate.InterRsDateUn.DtXferPrj = paramString;
    paramTypeInterRsAggregate.InterRsDateUn.DtPosted = null;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.serviceMsg.RsCmBuilder
 * JD-Core Version:    0.7.0.1
 */