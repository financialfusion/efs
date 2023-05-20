package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.custimpl.FundsAllocator;
import com.ffusion.ffs.bpw.db.Payee;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BankInfo;
import com.ffusion.ffs.bpw.interfaces.FundsAllocInfo;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWRegistryUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.beans.LocalizableString;
import java.math.BigDecimal;
import java.util.HashMap;

public class FundsAllocProcessor
{
  public static int revertFundImmediate(String paramString1, String paramString2, boolean paramBoolean, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    PmtInstruction localPmtInstruction = PmtInstruction.getPmtInstr(paramString1, paramFFSConnectionHolder);
    if (localPmtInstruction == null)
    {
      String str = "*** FundAllocProcessor.revertFund(srvrTId): could not find the SrvrTID=" + paramString1 + " in BPW_PmtInstruction";
      FFSDebug.console(str);
      FFSDebug.log(str);
      throw new BPWException(str);
    }
    return revertFundImmediate(localPmtInstruction.getPmtInfo(), paramString2, paramBoolean, paramString3, paramFFSConnectionHolder);
  }
  
  public static int revertFundImmediate(PmtInfo paramPmtInfo, String paramString1, boolean paramBoolean, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      localObject1 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      paramString2 = ((PropertyConfig)localObject1).otherProperties.getProperty("bpw.pmt.bankinfo.lookup.level", String.valueOf("STRICT"));
    }
    Object localObject1 = Payee.findPayeeByID(paramPmtInfo.getPayeeID(), paramFFSConnectionHolder);
    if (localObject1 == null)
    {
      localObject2 = "*** FundAllocProcessor.revertFund failed: could not find the PayeeID=" + paramPmtInfo.getPayeeID() + " in BPW_Payee for pmt of SrvrTID=" + paramPmtInfo.getSrvrTID();
      FFSDebug.console((String)localObject2);
      FFSDebug.log((String)localObject2);
      throw new BPWException((String)localObject2);
    }
    Object localObject2 = BPWRegistryUtil.getBankInfo(paramPmtInfo.getBankID());
    String str = null;
    if (localObject2 == null)
    {
      if (paramString2.equalsIgnoreCase("STRICT"))
      {
        localObject3 = "*** FundAllocProcessor.revertFund failed : could not find the BankID in BPW_Bank, bpw.pmt.bankinfo.lookup.level = STRICT ";
        FFSDebug.console((String)localObject3);
        FFSDebug.log((String)localObject3, 0);
        throw new Exception((String)localObject3);
      }
      if (paramString2.equalsIgnoreCase("LENIENT"))
      {
        localObject3 = "*** FundAllocProcessor.revertFund : bpw.pmt.bankinfo.lookup.level = LENIENT ";
        FFSDebug.log((String)localObject3, 1);
      }
      else if (paramString2.equalsIgnoreCase("NONE"))
      {
        localObject3 = "*** FundAllocProcessor.revertFund : bpw.pmt.bankinfo.lookup.level = NONE ";
        FFSDebug.log((String)localObject3, 6);
      }
      else
      {
        localObject3 = "*** FundAllocProcessor.revertFund: Incorrect value for bpw.pmt.bankinfo.lookup.level =" + paramString2;
        FFSDebug.console((String)localObject3);
        FFSDebug.log((String)localObject3);
        throw new Exception((String)localObject3);
      }
      str = String.valueOf(-1);
    }
    else
    {
      str = ((BankInfo)localObject2).debitGLAcct;
    }
    Object localObject3 = new FundsAllocInfo(paramPmtInfo.getFIID(), paramPmtInfo.getCustomerID(), paramPmtInfo.getBankID(), paramPmtInfo.getAcctDebitID(), paramPmtInfo.getAcctDebitType(), paramPmtInfo.getAmt(), paramPmtInfo.getCurDef(), paramPmtInfo.getSrvrTID(), paramPmtInfo.getPayeeID(), ((PayeeInfo)localObject1).PayeeName, 1, paramBoolean, str, paramString1, paramPmtInfo.getRecSrvrTID(), paramPmtInfo);
    FFSDebug.log("*** FundAllocProcessor.revertFund: Fund reverted for: " + paramPmtInfo.getSrvrTID(), 6);
    FundsAllocator localFundsAllocator = new FundsAllocator();
    int i = localFundsAllocator.processRevertFundsAllocation((FundsAllocInfo)localObject3);
    return i;
  }
  
  public static void revertLimit(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo, int paramInt)
    throws Exception
  {
    String str1 = RecPmtProcessor2.deleteLimit(paramFFSConnectionHolder, paramPmtInfo, new HashMap(), "FAILEDON", false);
    if (!str1.equalsIgnoreCase("LIMIT_REVERT_SUCCEEDED"))
    {
      FFSDebug.log("FundsAllocProcessor.revertLimit Failed to revert limit", 0);
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      if (localPropertyConfig.LogLevel >= 3)
      {
        int i = Integer.parseInt(paramPmtInfo.getCustomerID());
        BigDecimal localBigDecimal = FFSUtil.getBigDecimal(paramPmtInfo.getAmt());
        String str2 = BPWUtil.getAccountIDWithAccountType(paramPmtInfo.getAcctDebitID(), paramPmtInfo.getAcctDebitType());
        LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(402, null, "BILLPAY_AUDITLOG_MESSAGE");
        PmtProcessor2.logTransAuditLogError(paramPmtInfo.getSubmittedBy(), null, null, localLocalizableString, paramPmtInfo.getLogID(), paramInt, i, localBigDecimal, paramPmtInfo.getCurDef(), paramPmtInfo.getSrvrTID(), str1, paramPmtInfo.getPayAcct(), null, str2, null);
      }
      throw new OFXException(paramPmtInfo.getStatusMsg(), 2000);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.FundsAllocProcessor
 * JD-Core Version:    0.7.0.1
 */