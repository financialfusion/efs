package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.bpw.serviceMsg.RsCmBuilder;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtPrcStsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRqCm;

public class OFX151Handler
  implements BPWResource, DBConsts
{
  public static TypePmtInqTrnRsV1 processPmtInqTrnRq(TypePmtInqTrnRqV1 paramTypePmtInqTrnRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    TypePmtInqTrnRsV1 localTypePmtInqTrnRsV1 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str1 = a(paramTypePmtInqTrnRqV1);
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      PmtInfo localPmtInfo = PmtInstruction.getPmtInfo(str1, localFFSConnectionHolder);
      if (localPmtInfo == null)
      {
        str2 = MsgBuilder.getCmStatusMessage(2018);
        throw new OFXException(str2, 2018);
      }
      str2 = Integer.toString(localPmtInfo.StartDate);
      int i = jdMethod_case(localPmtInfo.Status);
      localTypePmtInqTrnRsV1 = a(paramTypePmtInqTrnRqV1, i, str2.substring(0, 8));
    }
    catch (OFXException localOFXException)
    {
      localTypePmtInqTrnRsV1 = jdMethod_try(paramTypePmtInqTrnRqV1.PmtInqTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (Exception localException)
    {
      String str2 = "*** OFX151Handler.processPmtInqTrnRq failed:";
      FFSDebug.log(str2);
      FFSDebug.console(str2 + localException.toString());
      localTypePmtInqTrnRsV1 = jdMethod_try(paramTypePmtInqTrnRqV1.PmtInqTrnRq.TrnRqCm, 2000, "Server internal error");
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTypePmtInqTrnRsV1;
  }
  
  private static String a(TypePmtInqTrnRqV1 paramTypePmtInqTrnRqV1)
  {
    return paramTypePmtInqTrnRqV1.PmtInqTrnRq.PmtInqRq.SrvrTID;
  }
  
  private static int jdMethod_case(String paramString)
  {
    int i;
    if (paramString.equals("PROCESSEDON")) {
      i = 3;
    } else if (paramString.equals("NOFUNDSON")) {
      i = 2;
    } else if (paramString.equals("FUNDSFAILEDON")) {
      i = 2;
    } else if (paramString.equals("FAILEDON")) {
      i = 1;
    } else if (paramString.equals("CANCELEDON")) {
      i = 0;
    } else {
      i = 4;
    }
    return i;
  }
  
  private static TypePmtInqTrnRsV1 a(TypePmtInqTrnRqV1 paramTypePmtInqTrnRqV1, int paramInt, String paramString)
  {
    EnumPmtProcessStatusEnum localEnumPmtProcessStatusEnum = EnumPmtProcessStatusEnum.from_int(paramInt);
    TypePmtInqTrnRsV1 localTypePmtInqTrnRsV1 = new TypePmtInqTrnRsV1();
    localTypePmtInqTrnRsV1.PmtInqTrnRs = new TypePmtInqTrnRsV1Aggregate();
    localTypePmtInqTrnRsV1.PmtInqTrnRs.PmtInqRsExists = true;
    localTypePmtInqTrnRsV1.PmtInqTrnRs.PmtInqRs = new TypePmtInqRsV1Aggregate();
    localTypePmtInqTrnRsV1.PmtInqTrnRs.PmtInqRs.CheckNumExists = false;
    localTypePmtInqTrnRsV1.PmtInqTrnRs.PmtInqRs.SrvrTID = paramTypePmtInqTrnRqV1.PmtInqTrnRq.PmtInqRq.SrvrTID;
    localTypePmtInqTrnRsV1.PmtInqTrnRs.PmtInqRs.PmtPrcSts = new TypePmtPrcStsAggregate();
    localTypePmtInqTrnRsV1.PmtInqTrnRs.PmtInqRs.PmtPrcSts.PmtPrcCode = localEnumPmtProcessStatusEnum;
    localTypePmtInqTrnRsV1.PmtInqTrnRs.PmtInqRs.PmtPrcSts.DtPmtPrc = paramString;
    localTypePmtInqTrnRsV1.PmtInqTrnRs.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(paramTypePmtInqTrnRqV1.PmtInqTrnRq.TrnRqCm, null);
    return localTypePmtInqTrnRsV1;
  }
  
  private static TypePmtInqTrnRsV1 jdMethod_try(TypeTrnRqCm paramTypeTrnRqCm, int paramInt, String paramString)
  {
    TypePmtInqTrnRsV1 localTypePmtInqTrnRsV1 = new TypePmtInqTrnRsV1();
    localTypePmtInqTrnRsV1.PmtInqTrnRs = new TypePmtInqTrnRsV1Aggregate();
    localTypePmtInqTrnRsV1.PmtInqTrnRs.PmtInqRsExists = false;
    localTypePmtInqTrnRsV1.PmtInqTrnRs.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, paramInt, paramString);
    return localTypePmtInqTrnRsV1;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.OFX151Handler
 * JD-Core Version:    0.7.0.1
 */