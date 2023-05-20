package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.db.Transfer;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;
import java.util.HashMap;

public class TransferFundsRsltProcessor
  implements BPWResource, FFSConst, DBConsts
{
  private static final String[] aK = { "FUNDSFAILED", "FUNDSPENDING", "FUNDSPROCESSED", "NOFUNDS" };
  private static final String[] aL = { "INFUNDSPROCESSING", "FUNDSPENDING" };
  private static final String[] aM = { "FUNDSREVERTED", "FUNDSREVERTFAILED" };
  private static final String[] aJ = { "INFUNDSREVERT", "FUNDSREVERTPENDING" };
  
  public static void processFundsTransferRslt(TransferInfo[] paramArrayOfTransferInfo)
    throws FFSException
  {
    if (paramArrayOfTransferInfo == null)
    {
      try
      {
        FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRslt:", BPWLocaleUtil.getMessage(21270, null, "TRANSFER_MESSAGE"), 0);
      }
      catch (Throwable localThrowable1)
      {
        FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRslt:", FFSDebug.stackTrace(localThrowable1), 0);
      }
      return;
    }
    if (paramArrayOfTransferInfo.length == 0)
    {
      try
      {
        FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRslt:", BPWLocaleUtil.getMessage(21230, null, "TRANSFER_MESSAGE"), 0);
      }
      catch (Throwable localThrowable2)
      {
        FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRslt:", FFSDebug.stackTrace(localThrowable2), 0);
      }
      return;
    }
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    boolean bool = a(localPropertyConfig);
    int i = 1;
    FFSConnectionHolder localFFSConnectionHolder = null;
    Object localObject1;
    if ((paramArrayOfTransferInfo[0] != null) && (paramArrayOfTransferInfo[0].getDbTransKey() != null))
    {
      localObject1 = (DBConnCache)FFSRegistry.lookup("DBCONNCACHE");
      String str1 = paramArrayOfTransferInfo[0].getDbTransKey();
      localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup(str1);
    }
    if (localFFSConnectionHolder == null)
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      i = 0;
    }
    if (localFFSConnectionHolder.conn == null)
    {
      localObject1 = null;
      try
      {
        localObject1 = BPWLocaleUtil.getMessage(20020, null, "WIRE_MESSAGE");
      }
      catch (Throwable localThrowable6)
      {
        FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRslt:", FFSDebug.stackTrace(localThrowable6), 0);
      }
      FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRslt:", (String)localObject1, 0);
      StringBuffer localStringBuffer1 = new StringBuffer("TransferFundsRsltProcessor.processFundsTransferRslt:");
      localStringBuffer1.append((String)localObject1);
      throw new FFSException(localStringBuffer1.toString());
    }
    try
    {
      FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRslt:", BPWLocaleUtil.getMessage(21290, null, "TRANSFER_MESSAGE"), 6);
    }
    catch (Throwable localThrowable3)
    {
      FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRslt:", FFSDebug.stackTrace(localThrowable3), 0);
    }
    try
    {
      int j = paramArrayOfTransferInfo.length;
      for (int k = 0; k < j; k++) {
        try
        {
          processOneFundsTransferRslt(paramArrayOfTransferInfo[k], localFFSConnectionHolder);
        }
        catch (Throwable localThrowable7)
        {
          String str3 = "";
          if (paramArrayOfTransferInfo[k] != null) {
            str3 = paramArrayOfTransferInfo[k].toString();
          }
          try
          {
            FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRslt:", BPWLocaleUtil.getMessage(21340, new String[] { str3, FFSDebug.stackTrace(localThrowable7) }, "TRANSFER_MESSAGE"), 0);
          }
          catch (Throwable localThrowable9)
          {
            FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRslt:", FFSDebug.stackTrace(localThrowable9), 0);
          }
        }
      }
      if (i == 0) {
        localFFSConnectionHolder.conn.commit();
      }
    }
    catch (Throwable localThrowable4)
    {
      if (i == 0) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str2 = null;
      try
      {
        str2 = BPWLocaleUtil.getMessage(21330, new String[] { FFSDebug.stackTrace(localThrowable4) }, "TRANSFER_MESSAGE");
      }
      catch (Throwable localThrowable8)
      {
        FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRslt:", FFSDebug.stackTrace(localThrowable8), 0);
      }
      FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRslt:", str2, 0);
      StringBuffer localStringBuffer2 = new StringBuffer("TransferFundsRsltProcessor.processFundsTransferRslt:");
      localStringBuffer2.append(str2);
      throw new FFSException(localThrowable4, localStringBuffer2.toString());
    }
    finally
    {
      if (i == 0) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
    try
    {
      FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRslt:", BPWLocaleUtil.getMessage(21300, null, "TRANSFER_MESSAGE"), 6);
    }
    catch (Throwable localThrowable5)
    {
      FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRslt:", FFSDebug.stackTrace(localThrowable5), 0);
    }
  }
  
  public static void processOneFundsTransferRslt(TransferInfo paramTransferInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    boolean bool1 = a(localPropertyConfig);
    TransferInfo localTransferInfo = a(paramTransferInfo, paramFFSConnectionHolder, "TransferFundsRsltProcessor.processOneFundsTransferRslt:");
    if (localTransferInfo == null) {
      return;
    }
    try
    {
      FFSDebug.log("TransferFundsRsltProcessor.processOneFundsTransferRslt:", BPWLocaleUtil.getMessage(21310, new String[] { localTransferInfo.toString() }, "TRANSFER_MESSAGE"), 6);
    }
    catch (Throwable localThrowable1)
    {
      FFSDebug.log("TransferFundsRsltProcessor.processOneFundsTransferRslt:", FFSDebug.stackTrace(localThrowable1), 0);
    }
    boolean bool2 = a(paramTransferInfo, aK, localTransferInfo, aL, "TransferFundsRsltProcessor.processOneFundsTransferRslt:", bool1);
    String str1 = paramTransferInfo.getPrcStatus();
    if ((!bool2) || (str1.equalsIgnoreCase("FUNDSFAILED")))
    {
      if (!a(paramFFSConnectionHolder, localTransferInfo, bool1)) {
        paramTransferInfo.setPrcStatus("LIMIT_REVERT_FAILED");
      }
      a(localTransferInfo, paramTransferInfo);
      paramTransferInfo = Transfer.updateTransferForFundsRslt(paramFFSConnectionHolder, localTransferInfo);
      return;
    }
    if (str1.equalsIgnoreCase("NOFUNDS"))
    {
      int i = localTransferInfo.getFundsRetry();
      int j = 3;
      try
      {
        j = Integer.parseInt(localPropertyConfig.otherProperties.getProperty("bpw.transfer.funds.retries", String.valueOf(3)));
      }
      catch (Throwable localThrowable5)
      {
        j = 3;
      }
      if (i >= j)
      {
        if (!a(paramFFSConnectionHolder, localTransferInfo, bool1))
        {
          localTransferInfo.setPrcStatus("LIMIT_REVERT_FAILED");
          Transfer.updateStatus(paramFFSConnectionHolder, localTransferInfo, false);
          return;
        }
        paramTransferInfo.setAction("Failed");
        a(localTransferInfo, paramTransferInfo);
        paramTransferInfo = Transfer.updateTransferForFundsRslt(paramFFSConnectionHolder, localTransferInfo);
      }
      else
      {
        i++;
        paramTransferInfo.setFundsRetry(i);
        paramTransferInfo.setPrcStatus("WILLPROCESSON");
        try
        {
          String str2 = localTransferInfo.getProcessDate();
          if (str2.length() == 10) {
            str2 = str2.substring(0, 8);
          }
          int k = Integer.parseInt(str2);
          int m = SmartCalendar.getBusinessDay(localTransferInfo.getFIId(), k, true);
          String str3 = String.valueOf(m);
          if (str3.length() == 8)
          {
            m *= 100;
            str3 = String.valueOf(m);
          }
          paramTransferInfo.setProcessDate(str3);
        }
        catch (Throwable localThrowable6)
        {
          FFSDebug.log("TransferFundsRsltProcessor.processOneFundsTransferRslt:", FFSDebug.stackTrace(localThrowable6), 0);
        }
        a(localTransferInfo, paramTransferInfo);
        paramTransferInfo = Transfer.updateTransferForFundsRslt(paramFFSConnectionHolder, localTransferInfo);
      }
    }
    else
    {
      a(localTransferInfo, paramTransferInfo);
      paramTransferInfo = Transfer.updateTransferForFundsRslt(paramFFSConnectionHolder, localTransferInfo);
    }
    if ((bool1) && (paramTransferInfo.getStatusCode() == 0)) {
      try
      {
        a(paramFFSConnectionHolder, localTransferInfo, 5253, BPWLocaleUtil.getLocalizableMessage(21320, new Object[] { localTransferInfo.getSrvrTId() }, "TRANSFER_MESSAGE"));
      }
      catch (Throwable localThrowable2)
      {
        try
        {
          FFSDebug.log("TransferFundsRsltProcessor.processOneFundsTransferRslt:", BPWLocaleUtil.getMessage(21340, new String[] { localTransferInfo.toString(), FFSDebug.stackTrace(localThrowable2) }, "TRANSFER_MESSAGE"), 0);
        }
        catch (Throwable localThrowable4)
        {
          FFSDebug.log("TransferFundsRsltProcessor.processOneFundsTransferRslt:", FFSDebug.stackTrace(localThrowable4), 0);
        }
      }
    }
    try
    {
      if (paramTransferInfo.getStatusCode() == 0) {
        FFSDebug.log("TransferFundsRsltProcessor.processOneFundsTransferRslt:", BPWLocaleUtil.getMessage(21320, new String[] { localTransferInfo.toString() }, "TRANSFER_MESSAGE"), 6);
      } else {
        FFSDebug.log("TransferFundsRsltProcessor.processOneFundsTransferRslt:", BPWLocaleUtil.getMessage(21340, new String[] { localTransferInfo.toString(), BPWLocaleUtil.getMessage(21470, null, "TRANSFER_MESSAGE") }, "TRANSFER_MESSAGE"), 0);
      }
    }
    catch (Throwable localThrowable3)
    {
      FFSDebug.log("TransferFundsRsltProcessor.processOneFundsTransferRslt:", FFSDebug.stackTrace(localThrowable3), 0);
    }
  }
  
  public static void processFundsTransferRevertRslt(TransferInfo[] paramArrayOfTransferInfo)
    throws Exception
  {
    if (paramArrayOfTransferInfo == null)
    {
      try
      {
        FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRevertRslt:", BPWLocaleUtil.getMessage(21270, null, "TRANSFER_MESSAGE"), 0);
      }
      catch (Throwable localThrowable1)
      {
        FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRevertRslt:", FFSDebug.stackTrace(localThrowable1), 0);
      }
      return;
    }
    if (paramArrayOfTransferInfo.length == 0)
    {
      try
      {
        FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRevertRslt:", BPWLocaleUtil.getMessage(21230, null, "TRANSFER_MESSAGE"), 0);
      }
      catch (Throwable localThrowable2)
      {
        FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRevertRslt:", FFSDebug.stackTrace(localThrowable2), 0);
      }
      return;
    }
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    boolean bool = a(localPropertyConfig);
    int i = 1;
    FFSConnectionHolder localFFSConnectionHolder = null;
    Object localObject1;
    if ((paramArrayOfTransferInfo[0] != null) && (paramArrayOfTransferInfo[0].getDbTransKey() != null))
    {
      localObject1 = (DBConnCache)FFSRegistry.lookup("DBCONNCACHE");
      String str1 = paramArrayOfTransferInfo[0].getDbTransKey();
      localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup(str1);
    }
    if (localFFSConnectionHolder == null)
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      i = 0;
    }
    if (localFFSConnectionHolder.conn == null)
    {
      localObject1 = null;
      try
      {
        localObject1 = BPWLocaleUtil.getMessage(20020, null, "WIRE_MESSAGE");
      }
      catch (Throwable localThrowable6)
      {
        FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRevertRslt:", FFSDebug.stackTrace(localThrowable6), 0);
      }
      FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRevertRslt:", (String)localObject1, 0);
      StringBuffer localStringBuffer1 = new StringBuffer("TransferFundsRsltProcessor.processFundsTransferRevertRslt:");
      localStringBuffer1.append((String)localObject1);
      throw new FFSException(localStringBuffer1.toString());
    }
    try
    {
      FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRevertRslt:", BPWLocaleUtil.getMessage(21350, null, "TRANSFER_MESSAGE"), 6);
    }
    catch (Throwable localThrowable3)
    {
      FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRevertRslt:", FFSDebug.stackTrace(localThrowable3), 0);
    }
    try
    {
      int j = paramArrayOfTransferInfo.length;
      for (int k = 0; k < j; k++) {
        try
        {
          processOneFundsTransferRevertRslt(paramArrayOfTransferInfo[k], localFFSConnectionHolder);
        }
        catch (Throwable localThrowable7)
        {
          String str3 = "";
          if (paramArrayOfTransferInfo[k] != null) {
            str3 = paramArrayOfTransferInfo[k].toString();
          }
          try
          {
            FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRevertRslt:", BPWLocaleUtil.getMessage(21400, new String[] { str3, FFSDebug.stackTrace(localThrowable7) }, "TRANSFER_MESSAGE"), 0);
          }
          catch (Throwable localThrowable9)
          {
            FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRevertRslt:", FFSDebug.stackTrace(localThrowable9), 0);
          }
        }
      }
      if (i == 0) {
        localFFSConnectionHolder.conn.commit();
      }
    }
    catch (Throwable localThrowable4)
    {
      if (i == 0) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str2 = null;
      try
      {
        str2 = BPWLocaleUtil.getMessage(21390, new String[] { FFSDebug.stackTrace(localThrowable4) }, "TRANSFER_MESSAGE");
      }
      catch (Throwable localThrowable8)
      {
        FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRevertRslt:", FFSDebug.stackTrace(localThrowable8), 0);
      }
      FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRevertRslt:", str2, 0);
      StringBuffer localStringBuffer2 = new StringBuffer("TransferFundsRsltProcessor.processFundsTransferRevertRslt:");
      localStringBuffer2.append(str2);
      throw new FFSException(localThrowable4, localStringBuffer2.toString());
    }
    finally
    {
      if (i == 0) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
    try
    {
      FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRevertRslt:", BPWLocaleUtil.getMessage(21360, null, "TRANSFER_MESSAGE"), 6);
    }
    catch (Throwable localThrowable5)
    {
      FFSDebug.log("TransferFundsRsltProcessor.processFundsTransferRevertRslt:", FFSDebug.stackTrace(localThrowable5), 0);
    }
  }
  
  public static void processOneFundsTransferRevertRslt(TransferInfo paramTransferInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    boolean bool1 = a(localPropertyConfig);
    TransferInfo localTransferInfo = a(paramTransferInfo, paramFFSConnectionHolder, "TransferFundsRsltProcessor.processOneFundsTransferRevertRslt:");
    if (localTransferInfo == null) {
      return;
    }
    try
    {
      FFSDebug.log("TransferFundsRsltProcessor.processOneFundsTransferRevertRslt:", BPWLocaleUtil.getMessage(21370, new String[] { localTransferInfo.toString() }, "TRANSFER_MESSAGE"), 6);
    }
    catch (Throwable localThrowable1)
    {
      FFSDebug.log("TransferFundsRsltProcessor.processOneFundsTransferRevertRslt:", FFSDebug.stackTrace(localThrowable1), 0);
    }
    boolean bool2 = a(paramTransferInfo, aM, localTransferInfo, aJ, "TransferFundsRsltProcessor.processOneFundsTransferRevertRslt:", bool1);
    if (!bool2) {
      return;
    }
    a(localTransferInfo, paramTransferInfo);
    paramTransferInfo = Transfer.updateTransferForFundsRslt(paramFFSConnectionHolder, localTransferInfo);
    if ((bool1) && (paramTransferInfo.getStatusCode() == 0)) {
      try
      {
        a(paramFFSConnectionHolder, localTransferInfo, 5255, BPWLocaleUtil.getLocalizableMessage(21380, new Object[] { localTransferInfo.getSrvrTId() }, "TRANSFER_MESSAGE"));
      }
      catch (Throwable localThrowable2)
      {
        FFSDebug.log("TransferFundsRsltProcessor.processOneFundsTransferRevertRslt:", BPWLocaleUtil.getMessage(21400, new String[] { localTransferInfo.toString(), FFSDebug.stackTrace(localThrowable2) }, "TRANSFER_MESSAGE"), 0);
      }
    }
    try
    {
      if (paramTransferInfo.getStatusCode() == 0) {
        FFSDebug.log("TransferFundsRsltProcessor.processOneFundsTransferRevertRslt:", BPWLocaleUtil.getMessage(21380, new String[] { localTransferInfo.toString() }, "TRANSFER_MESSAGE"), 6);
      } else {
        FFSDebug.log("TransferFundsRsltProcessor.processOneFundsTransferRevertRslt:", BPWLocaleUtil.getMessage(21400, new String[] { localTransferInfo.toString(), BPWLocaleUtil.getMessage(21470, null, "TRANSFER_MESSAGE") }, "TRANSFER_MESSAGE"), 0);
      }
    }
    catch (Throwable localThrowable3)
    {
      FFSDebug.log("TransferFundsRsltProcessor.processOneFundsTransferRevertRslt:", FFSDebug.stackTrace(localThrowable3), 0);
    }
  }
  
  private static TransferInfo a(TransferInfo paramTransferInfo, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
  {
    TransferInfo localTransferInfo = null;
    if (paramTransferInfo == null)
    {
      try
      {
        FFSDebug.log(paramString, BPWLocaleUtil.getMessage(21220, null, "TRANSFER_MESSAGE"), 0);
      }
      catch (Throwable localThrowable1)
      {
        FFSDebug.log(paramString, FFSDebug.stackTrace(localThrowable1), 0);
      }
      return null;
    }
    if ((paramTransferInfo.getSrvrTId() == null) || (paramTransferInfo.getSrvrTId().trim().length() == 0))
    {
      try
      {
        String str1 = BPWLocaleUtil.getMessage(21240, null, "TRANSFER_MESSAGE");
        FFSDebug.log(paramString, str1, 0);
        paramTransferInfo.setStatusCode(21240);
        paramTransferInfo.setStatusMsg(str1);
      }
      catch (Throwable localThrowable2)
      {
        FFSDebug.log(paramString, FFSDebug.stackTrace(localThrowable2), 0);
      }
      return null;
    }
    try
    {
      localTransferInfo = Transfer.getTransferInfo(paramFFSConnectionHolder, paramTransferInfo, false);
    }
    catch (Throwable localThrowable3)
    {
      try
      {
        String str3 = BPWLocaleUtil.getMessage(21410, new String[] { localThrowable3.toString() }, "TRANSFER_MESSAGE");
        FFSDebug.log(paramString, str3, FFSDebug.stackTrace(localThrowable3), 0);
        paramTransferInfo.setStatusCode(21410);
        paramTransferInfo.setStatusMsg(str3);
      }
      catch (Throwable localThrowable5)
      {
        FFSDebug.log(paramString, FFSDebug.stackTrace(localThrowable5), 0);
      }
      return null;
    }
    if (localTransferInfo.getStatusCode() == 16020)
    {
      try
      {
        String str2 = BPWLocaleUtil.getMessage(21260, new String[] { paramTransferInfo.getSrvrTId() }, "TRANSFER_MESSAGE");
        FFSDebug.log(paramString, str2, 0);
        paramTransferInfo.setStatusCode(21260);
        paramTransferInfo.setStatusMsg(str2);
      }
      catch (Throwable localThrowable4)
      {
        FFSDebug.log(paramString, FFSDebug.stackTrace(localThrowable4), 0);
      }
      return null;
    }
    return localTransferInfo;
  }
  
  private static boolean a(TransferInfo paramTransferInfo1, String[] paramArrayOfString1, TransferInfo paramTransferInfo2, String[] paramArrayOfString2, String paramString, boolean paramBoolean)
  {
    String str1 = paramTransferInfo1.getPrcStatus();
    String str2 = paramTransferInfo2.getPrcStatus();
    LocalizableString localLocalizableString;
    if (!a(str1, paramArrayOfString1))
    {
      try
      {
        String str3 = BPWLocaleUtil.getMessage(21250, new String[] { str1, paramTransferInfo1.getSrvrTId() }, "TRANSFER_MESSAGE");
        FFSDebug.log(paramString, str3, 0);
        if (paramBoolean)
        {
          localLocalizableString = BPWLocaleUtil.getLocalizableMessage(21250, new Object[] { str1, paramTransferInfo1.getSrvrTId() }, "TRANSFER_MESSAGE");
          a(paramTransferInfo2, localLocalizableString);
        }
        paramTransferInfo1.setStatusCode(21250);
        paramTransferInfo1.setStatusMsg(str3);
      }
      catch (Throwable localThrowable1)
      {
        FFSDebug.log(paramString, FFSDebug.stackTrace(localThrowable1), 0);
      }
      return false;
    }
    if (!a(str2, paramArrayOfString2))
    {
      try
      {
        String str4 = BPWLocaleUtil.getMessage(21250, new String[] { str2, paramTransferInfo1.getSrvrTId() }, "TRANSFER_MESSAGE");
        FFSDebug.log(paramString, str4, 0);
        if (paramBoolean)
        {
          localLocalizableString = BPWLocaleUtil.getLocalizableMessage(21250, new Object[] { str2, paramTransferInfo1.getSrvrTId() }, "TRANSFER_MESSAGE");
          a(paramTransferInfo2, localLocalizableString);
        }
        paramTransferInfo1.setStatusCode(21250);
        paramTransferInfo1.setStatusMsg(str4);
      }
      catch (Throwable localThrowable2)
      {
        FFSDebug.log(paramString, FFSDebug.stackTrace(localThrowable2), 0);
      }
      return false;
    }
    return true;
  }
  
  private static boolean a(String paramString, String[] paramArrayOfString)
  {
    boolean bool = false;
    if ((paramString != null) && (paramString.trim().length() > 0))
    {
      int i = paramArrayOfString.length;
      for (int j = 0; (j < i) && (!bool); j++) {
        if (paramString.equalsIgnoreCase(paramArrayOfString[j])) {
          bool = true;
        }
      }
    }
    return bool;
  }
  
  private static boolean a(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, boolean paramBoolean)
  {
    try
    {
      FFSDebug.log("TransferFundsRsltProcessor.revertLimit: ", BPWLocaleUtil.getMessage(21430, new String[] { paramTransferInfo.toString() }, "TRANSFER_MESSAGE"), 6);
    }
    catch (Throwable localThrowable1)
    {
      FFSDebug.log("TransferFundsRsltProcessor.revertLimit: ", FFSDebug.stackTrace(localThrowable1), 0);
    }
    try
    {
      int i = LimitCheckApprovalProcessor.processExternalTransferReject(paramFFSConnectionHolder, paramTransferInfo, new HashMap());
      str1 = LimitCheckApprovalProcessor.mapToStatus(i);
      if ((str1 != null) && (str1.equalsIgnoreCase("LIMIT_REVERT_FAILED")))
      {
        paramTransferInfo.setPrcStatus("LIMIT_REVERT_FAILED");
        try
        {
          String str2 = BPWLocaleUtil.getMessage(21450, new String[] { paramTransferInfo.toString(), "" }, "TRANSFER_MESSAGE");
          FFSDebug.log("TransferFundsRsltProcessor.revertLimit: ", str2, 0);
          if (paramBoolean)
          {
            LocalizableString localLocalizableString2 = BPWLocaleUtil.getLocalizableMessage(21450, new Object[] { paramTransferInfo.toString(), "" }, "TRANSFER_MESSAGE");
            a(paramTransferInfo, localLocalizableString2);
          }
        }
        catch (Throwable localThrowable5)
        {
          FFSDebug.log("TransferFundsRsltProcessor.revertLimit: ", FFSDebug.stackTrace(localThrowable5), 0);
        }
        return false;
      }
    }
    catch (Throwable localThrowable2)
    {
      try
      {
        String str1 = BPWLocaleUtil.getMessage(21450, new String[] { paramTransferInfo.toString(), FFSDebug.stackTrace(localThrowable2) }, "TRANSFER_MESSAGE");
        FFSDebug.log("TransferFundsRsltProcessor.revertLimit: ", str1, 0);
        if (paramBoolean)
        {
          LocalizableString localLocalizableString1 = BPWLocaleUtil.getLocalizableMessage(21450, new Object[] { paramTransferInfo.toString(), FFSDebug.stackTrace(localThrowable2) }, "TRANSFER_MESSAGE");
          a(paramTransferInfo, localLocalizableString1);
        }
      }
      catch (Throwable localThrowable4)
      {
        FFSDebug.log("TransferFundsRsltProcessor.revertLimit: ", FFSDebug.stackTrace(localThrowable4), 0);
      }
      return false;
    }
    try
    {
      FFSDebug.log("TransferFundsRsltProcessor.revertLimit: ", BPWLocaleUtil.getMessage(21440, new String[] { paramTransferInfo.toString() }, "TRANSFER_MESSAGE"), 6);
    }
    catch (Throwable localThrowable3)
    {
      FFSDebug.log("TransferFundsRsltProcessor.revertLimit: ", FFSDebug.stackTrace(localThrowable3), 0);
    }
    return true;
  }
  
  private static boolean a(PropertyConfig paramPropertyConfig)
  {
    boolean bool = false;
    if (paramPropertyConfig.LogLevel >= 4) {
      bool = true;
    }
    return bool;
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, int paramInt, ILocalizable paramILocalizable)
  {
    if (paramTransferInfo == null) {
      return;
    }
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    int i = 0;
    AuditLogRecord localAuditLogRecord = null;
    if (paramILocalizable == null) {
      return;
    }
    try
    {
      str3 = paramTransferInfo.getOriginatingUserId();
      str4 = paramTransferInfo.getAmount();
      if ((str4 == null) || (str4.trim().length() == 0)) {
        str4 = "-1";
      }
      str1 = paramTransferInfo.buildToAcctId();
      str2 = paramTransferInfo.buildFromAcctId();
      if (paramTransferInfo.getCustomerId().equals(paramTransferInfo.getSubmittedBy())) {
        i = 0;
      } else {
        i = Integer.parseInt(paramTransferInfo.getCustomerId());
      }
      localAuditLogRecord = new AuditLogRecord(str3, "", "", paramILocalizable, paramTransferInfo.getLogId(), paramInt, i, new BigDecimal(str4), paramTransferInfo.getAmountCurrency(), paramTransferInfo.getSrvrTId(), paramTransferInfo.getPrcStatus(), str1, paramTransferInfo.getBankToRtn(), str2, paramTransferInfo.getBankFromRtn(), -1);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    }
    catch (Throwable localThrowable1)
    {
      try
      {
        FFSDebug.log("TransferFundsRstProcessor.doAuditLog:", FFSDebug.stackTrace(localThrowable1), 0);
      }
      catch (Throwable localThrowable2)
      {
        FFSDebug.log("TransferFundsRstProcessor.doAuditLog:", FFSDebug.stackTrace(localThrowable2), 0);
      }
    }
  }
  
  private static void a(TransferInfo paramTransferInfo, ILocalizable paramILocalizable)
  {
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        String str1 = BPWLocaleUtil.getMessage(20020, null, "WIRE_MESSAGE");
        FFSDebug.log("TransferFundsRsltProcessor.doErrorAuditLog:", str1, 0);
        return;
      }
      a(localFFSConnectionHolder, paramTransferInfo, 5258, paramILocalizable);
    }
    catch (Throwable localThrowable1)
    {
      try
      {
        String str2 = BPWLocaleUtil.getMessage(20020, null, "WIRE_MESSAGE");
        FFSDebug.log("TransferFundsRsltProcessor.doErrorAuditLog:", str2, FFSDebug.stackTrace(localThrowable1), 0);
      }
      catch (Throwable localThrowable2)
      {
        FFSDebug.log("TransferFundsRsltProcessor.doErrorAuditLog:", FFSDebug.stackTrace(localThrowable2), 0);
      }
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  private static void a(TransferInfo paramTransferInfo1, TransferInfo paramTransferInfo2)
  {
    paramTransferInfo1.setPrcStatus(paramTransferInfo2.getPrcStatus());
    paramTransferInfo1.setFundsRetry(paramTransferInfo2.getFundsRetry());
    if ((paramTransferInfo2.getAmount() != null) && (paramTransferInfo2.getPrcStatus().equalsIgnoreCase("FUNDSPROCESSED"))) {
      paramTransferInfo1.setAmount(paramTransferInfo2.getAmount());
    }
    if ((paramTransferInfo2.getToAmount() != null) && (paramTransferInfo2.getPrcStatus().equalsIgnoreCase("FUNDSPROCESSED"))) {
      paramTransferInfo1.setToAmount(paramTransferInfo2.getToAmount());
    }
    if (paramTransferInfo2.getOrigAmount() != null) {
      paramTransferInfo1.setOrigAmount(paramTransferInfo2.getOrigAmount());
    }
    if (paramTransferInfo2.getOrigCurrency() != null) {
      paramTransferInfo1.setOrigCurrency(paramTransferInfo2.getOrigCurrency());
    }
    if (paramTransferInfo2.getAction() != null) {
      paramTransferInfo1.setAction(paramTransferInfo2.getAction());
    }
    if (paramTransferInfo2.getExtInfo() != null) {
      paramTransferInfo1.setExtInfo(paramTransferInfo2.getExtInfo());
    }
    if (paramTransferInfo2.getProcessDate() != null) {
      paramTransferInfo1.setProcessDate(paramTransferInfo2.getProcessDate());
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.TransferFundsRsltProcessor
 * JD-Core Version:    0.7.0.1
 */