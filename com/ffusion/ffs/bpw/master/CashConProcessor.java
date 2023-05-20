package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.CashCon;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.BankingDays;
import com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo;
import com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfoList;
import com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo;
import com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfoList;
import com.ffusion.ffs.bpw.interfaces.CCCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.CCCompanyInfoList;
import com.ffusion.ffs.bpw.interfaces.CCEntryHistInfo;
import com.ffusion.ffs.bpw.interfaces.CCEntryInfo;
import com.ffusion.ffs.bpw.interfaces.CCEntrySummaryInfoList;
import com.ffusion.ffs.bpw.interfaces.CCLocationInfo;
import com.ffusion.ffs.bpw.interfaces.CCLocationInfoList;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSCacheManager;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import java.util.Calendar;
import java.util.HashMap;

public class CashConProcessor
  implements FFSConst, ACHConsts, BPWResource
{
  private PropertyConfig ze = null;
  private int zc = 1;
  private FFSCacheManager zd = null;
  private PagedCashCon zb = null;
  
  public CCCompanyInfo addCCCompany(CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.addCCCompany: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCCompanyInfo == null)
    {
      paramCCCompanyInfo = new CCCompanyInfo();
      paramCCCompanyInfo.setStatusCode(16000);
      localObject1 = "CCCompanyInfo object  is null";
      paramCCCompanyInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCCompanyInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
      paramCCCompanyInfo = CashCon.addCCCompany((FFSConnectionHolder)localObject1, paramCCCompanyInfo);
      if (paramCCCompanyInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCCompanyInfo.getStatusMsg(), 6);
        CCCompanyInfo localCCCompanyInfo = paramCCCompanyInfo;
        return localCCCompanyInfo;
      }
      if (this.zc >= 4) {
        CashCon.logCCompanyTransAuditLog((FFSConnectionHolder)localObject1, paramCCCompanyInfo, "Add a Cash Con Company", 5300);
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCCompanyInfo.getStatusMsg(), 6);
    return paramCCCompanyInfo;
  }
  
  public CCCompanyInfo cancelCCCompany(CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.cancelCCCompany: ";
    FFSDebug.log(str1 + "start...", 6);
    if (paramCCCompanyInfo == null)
    {
      paramCCCompanyInfo = new CCCompanyInfo();
      paramCCCompanyInfo.setStatusCode(16000);
      localObject1 = "CCCompanyInfo object  is null";
      paramCCCompanyInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCCompanyInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
      paramCCCompanyInfo = CashCon.cancelCCCompany((FFSConnectionHolder)localObject1, paramCCCompanyInfo);
      if (paramCCCompanyInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCCompanyInfo.getStatusMsg(), 6);
        CCCompanyInfo localCCCompanyInfo = paramCCCompanyInfo;
        return localCCCompanyInfo;
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCCompanyInfo.getStatusMsg(), 6);
    return paramCCCompanyInfo;
  }
  
  public CCCompanyInfo modCCCompany(CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.modCCCompany: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCCompanyInfo == null)
    {
      paramCCCompanyInfo = new CCCompanyInfo();
      paramCCCompanyInfo.setStatusCode(16000);
      localObject1 = "CCCompanyInfo object  is null";
      paramCCCompanyInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCCompanyInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
      paramCCCompanyInfo = CashCon.modCCCompany((FFSConnectionHolder)localObject1, paramCCCompanyInfo);
      if (paramCCCompanyInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCCompanyInfo.getStatusMsg(), 6);
        CCCompanyInfo localCCCompanyInfo = paramCCCompanyInfo;
        return localCCCompanyInfo;
      }
      if (this.zc >= 4) {
        CashCon.logCCompanyTransAuditLog((FFSConnectionHolder)localObject1, paramCCCompanyInfo, "Modify a Cash Con Company", 5301);
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCCompanyInfo.getStatusMsg(), 6);
    return paramCCCompanyInfo;
  }
  
  public CCCompanyInfo getCCCompany(CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.getCCCompany: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCCompanyInfo == null)
    {
      paramCCCompanyInfo = new CCCompanyInfo();
      paramCCCompanyInfo.setStatusCode(16000);
      localObject1 = "CCCompanyInfo object  is null";
      paramCCCompanyInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCCompanyInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
      paramCCCompanyInfo = CashCon.getCCCompany((FFSConnectionHolder)localObject1, paramCCCompanyInfo);
      if (paramCCCompanyInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCCompanyInfo.getStatusMsg(), 6);
        CCCompanyInfo localCCCompanyInfo = paramCCCompanyInfo;
        return localCCCompanyInfo;
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCCompanyInfo.getStatusMsg(), 6);
    return paramCCCompanyInfo;
  }
  
  public CCCompanyInfoList getCCCompanyList(CCCompanyInfoList paramCCCompanyInfoList)
    throws FFSException
  {
    String str1 = "CashConProcessor.getCCCompanyList: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCCompanyInfoList == null)
    {
      paramCCCompanyInfoList = new CCCompanyInfoList();
      paramCCCompanyInfoList.setStatusCode(16000);
      localObject1 = "CCCompanyInfoList object  is null";
      paramCCCompanyInfoList.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCCompanyInfoList;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
      paramCCCompanyInfoList = CashCon.getCCCompanyList((FFSConnectionHolder)localObject1, paramCCCompanyInfoList);
      if (paramCCCompanyInfoList.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCCompanyInfoList.getStatusMsg(), 6);
        CCCompanyInfoList localCCCompanyInfoList = paramCCCompanyInfoList;
        return localCCCompanyInfoList;
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCCompanyInfoList.getStatusMsg(), 6);
    return paramCCCompanyInfoList;
  }
  
  public CutOffInfo getNextCashConCutOff(String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "CashConProcessor.getNextCashConCutOff: ";
    FFSDebug.log(str1 + "start, compId =" + paramString1 + ", transactionType =" + paramString2, 6);
    CutOffInfo localCutOffInfo1 = null;
    if (paramString1 == null)
    {
      localCutOffInfo1 = new CutOffInfo();
      localCutOffInfo1.setStatusCode(16000);
      localObject1 = "CompId  is null";
      localCutOffInfo1.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return localCutOffInfo1;
    }
    if (paramString2 == null)
    {
      localCutOffInfo1 = new CutOffInfo();
      localCutOffInfo1.setStatusCode(16000);
      localObject1 = "TransactionType  is null";
      localCutOffInfo1.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return localCutOffInfo1;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
      localCutOffInfo1 = CashCon.getNextCashConCutOff((FFSConnectionHolder)localObject1, paramString1, paramString2);
      if (localCutOffInfo1.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + localCutOffInfo1.getStatusMsg(), 6);
        CutOffInfo localCutOffInfo2 = localCutOffInfo1;
        return localCutOffInfo2;
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + localCutOffInfo1.getStatusMsg(), 6);
    return localCutOffInfo1;
  }
  
  public CCCompanyAcctInfo addCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.addCCCompanyAcct: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCCompanyAcctInfo == null)
    {
      paramCCCompanyAcctInfo = new CCCompanyAcctInfo();
      paramCCCompanyAcctInfo.setStatusCode(16000);
      localObject1 = "CCCompanyAcctInfo object  is null";
      paramCCCompanyAcctInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCCompanyAcctInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
      paramCCCompanyAcctInfo = CashCon.addCCCompanyAcct((FFSConnectionHolder)localObject1, paramCCCompanyAcctInfo);
      if (paramCCCompanyAcctInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCCompanyAcctInfo.getStatusMsg(), 6);
        CCCompanyAcctInfo localCCCompanyAcctInfo = paramCCCompanyAcctInfo;
        return localCCCompanyAcctInfo;
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCCompanyAcctInfo.getStatusMsg(), 6);
    return paramCCCompanyAcctInfo;
  }
  
  public CCCompanyAcctInfo cancelCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.cancelCCCompanyAcct: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCCompanyAcctInfo == null)
    {
      paramCCCompanyAcctInfo = new CCCompanyAcctInfo();
      paramCCCompanyAcctInfo.setStatusCode(16000);
      localObject1 = "CCCompanyAcctInfo object  is null";
      paramCCCompanyAcctInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCCompanyAcctInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
      paramCCCompanyAcctInfo = CashCon.cancelCCCompanyAcct((FFSConnectionHolder)localObject1, paramCCCompanyAcctInfo);
      if (paramCCCompanyAcctInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCCompanyAcctInfo.getStatusMsg(), 6);
        CCCompanyAcctInfo localCCCompanyAcctInfo = paramCCCompanyAcctInfo;
        return localCCCompanyAcctInfo;
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCCompanyAcctInfo.getStatusMsg(), 6);
    return paramCCCompanyAcctInfo;
  }
  
  public CCCompanyAcctInfo modCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.modCCCompanyAcct: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCCompanyAcctInfo == null)
    {
      paramCCCompanyAcctInfo = new CCCompanyAcctInfo();
      paramCCCompanyAcctInfo.setStatusCode(16000);
      localObject1 = "CCCompanyAcctInfo object  is null";
      paramCCCompanyAcctInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCCompanyAcctInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
      paramCCCompanyAcctInfo = CashCon.modCCCompanyAcct((FFSConnectionHolder)localObject1, paramCCCompanyAcctInfo);
      if (paramCCCompanyAcctInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCCompanyAcctInfo.getStatusMsg(), 6);
        CCCompanyAcctInfo localCCCompanyAcctInfo = paramCCCompanyAcctInfo;
        return localCCCompanyAcctInfo;
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCCompanyAcctInfo.getStatusMsg(), 6);
    return paramCCCompanyAcctInfo;
  }
  
  public CCCompanyAcctInfo getCCCompanyAcct(CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.getCCCompanyAcct: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCCompanyAcctInfo == null)
    {
      paramCCCompanyAcctInfo = new CCCompanyAcctInfo();
      paramCCCompanyAcctInfo.setStatusCode(16000);
      localObject1 = "CCCompanyAcctInfo object  is null";
      paramCCCompanyAcctInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCCompanyAcctInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
      paramCCCompanyAcctInfo = CashCon.getCCCompanyAcct((FFSConnectionHolder)localObject1, paramCCCompanyAcctInfo);
      if (paramCCCompanyAcctInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCCompanyAcctInfo.getStatusMsg(), 6);
        CCCompanyAcctInfo localCCCompanyAcctInfo = paramCCCompanyAcctInfo;
        return localCCCompanyAcctInfo;
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCCompanyAcctInfo.getStatusMsg(), 6);
    return paramCCCompanyAcctInfo;
  }
  
  public CCCompanyAcctInfoList getCCCompanyAcctList(CCCompanyAcctInfoList paramCCCompanyAcctInfoList)
    throws FFSException
  {
    String str1 = "CashConProcessor.getCCCompanyAcctList: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCCompanyAcctInfoList == null)
    {
      paramCCCompanyAcctInfoList = new CCCompanyAcctInfoList();
      paramCCCompanyAcctInfoList.setStatusCode(16000);
      localObject1 = "CCCompanyAcctInfoList object  is null";
      paramCCCompanyAcctInfoList.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCCompanyAcctInfoList;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
      paramCCCompanyAcctInfoList = CashCon.getCCCompanyAcctList((FFSConnectionHolder)localObject1, paramCCCompanyAcctInfoList);
      if (paramCCCompanyAcctInfoList.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCCompanyAcctInfoList.getStatusMsg(), 6);
        CCCompanyAcctInfoList localCCCompanyAcctInfoList = paramCCCompanyAcctInfoList;
        return localCCCompanyAcctInfoList;
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCCompanyAcctInfoList.getStatusMsg(), 6);
    return paramCCCompanyAcctInfoList;
  }
  
  public CCCompanyCutOffInfo addCCCompanyCutOff(CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.addCCCompanyCutOff: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCCompanyCutOffInfo == null)
    {
      paramCCCompanyCutOffInfo = new CCCompanyCutOffInfo();
      paramCCCompanyCutOffInfo.setStatusCode(16000);
      localObject1 = "CCCompanyCutOffInfo object  is null";
      paramCCCompanyCutOffInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCCompanyCutOffInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
      paramCCCompanyCutOffInfo = CashCon.addCCCompanyCutOff((FFSConnectionHolder)localObject1, paramCCCompanyCutOffInfo);
      if (paramCCCompanyCutOffInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCCompanyCutOffInfo.getStatusMsg(), 6);
        CCCompanyCutOffInfo localCCCompanyCutOffInfo = paramCCCompanyCutOffInfo;
        return localCCCompanyCutOffInfo;
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCCompanyCutOffInfo.getStatusMsg(), 6);
    return paramCCCompanyCutOffInfo;
  }
  
  public CCCompanyCutOffInfo cancelCCCompanyCutOff(CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.cancelCCCompanyCutOff: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCCompanyCutOffInfo == null)
    {
      paramCCCompanyCutOffInfo = new CCCompanyCutOffInfo();
      paramCCCompanyCutOffInfo.setStatusCode(16000);
      localObject1 = "CCCompanyCutOffInfo object  is null";
      paramCCCompanyCutOffInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCCompanyCutOffInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
      paramCCCompanyCutOffInfo = CashCon.cancelCCCompanyCutOff((FFSConnectionHolder)localObject1, paramCCCompanyCutOffInfo);
      if (paramCCCompanyCutOffInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCCompanyCutOffInfo.getStatusMsg(), 6);
        CCCompanyCutOffInfo localCCCompanyCutOffInfo = paramCCCompanyCutOffInfo;
        return localCCCompanyCutOffInfo;
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCCompanyCutOffInfo.getStatusMsg(), 6);
    return paramCCCompanyCutOffInfo;
  }
  
  public CCCompanyCutOffInfo getCCCompanyCutOff(CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.getCCCompanyCutOff: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCCompanyCutOffInfo == null)
    {
      paramCCCompanyCutOffInfo = new CCCompanyCutOffInfo();
      paramCCCompanyCutOffInfo.setStatusCode(16000);
      localObject1 = "CCCompanyCutOffInfo object  is null";
      paramCCCompanyCutOffInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCCompanyCutOffInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
      paramCCCompanyCutOffInfo = CashCon.getCCCompanyCutOff((FFSConnectionHolder)localObject1, paramCCCompanyCutOffInfo);
      if (paramCCCompanyCutOffInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCCompanyCutOffInfo.getStatusMsg(), 6);
        CCCompanyCutOffInfo localCCCompanyCutOffInfo = paramCCCompanyCutOffInfo;
        return localCCCompanyCutOffInfo;
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCCompanyCutOffInfo.getStatusMsg(), 6);
    return paramCCCompanyCutOffInfo;
  }
  
  public CCCompanyCutOffInfoList getCCCompanyCutOffList(CCCompanyCutOffInfoList paramCCCompanyCutOffInfoList)
    throws FFSException
  {
    String str1 = "CashConProcessor.getCCCompanyCutOffList: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCCompanyCutOffInfoList == null)
    {
      paramCCCompanyCutOffInfoList = new CCCompanyCutOffInfoList();
      paramCCCompanyCutOffInfoList.setStatusCode(16000);
      localObject1 = "CCCompanyCutOffInfoList object  is null";
      paramCCCompanyCutOffInfoList.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCCompanyCutOffInfoList;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
      paramCCCompanyCutOffInfoList = CashCon.getCCCompanyCutOffList((FFSConnectionHolder)localObject1, paramCCCompanyCutOffInfoList);
      if (paramCCCompanyCutOffInfoList.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCCompanyCutOffInfoList.getStatusMsg(), 6);
        CCCompanyCutOffInfoList localCCCompanyCutOffInfoList = paramCCCompanyCutOffInfoList;
        return localCCCompanyCutOffInfoList;
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCCompanyCutOffInfoList.getStatusMsg(), 6);
    return paramCCCompanyCutOffInfoList;
  }
  
  public CCLocationInfo addCCLocation(CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.addCCLocation: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCLocationInfo == null)
    {
      paramCCLocationInfo = new CCLocationInfo();
      paramCCLocationInfo.setStatusCode(16000);
      localObject1 = "CCLocationInfo object  is null";
      paramCCLocationInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCLocationInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
      paramCCLocationInfo = CashCon.addCCLocation((FFSConnectionHolder)localObject1, paramCCLocationInfo);
      if (paramCCLocationInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCLocationInfo.getStatusMsg(), 6);
        CCLocationInfo localCCLocationInfo = paramCCLocationInfo;
        return localCCLocationInfo;
      }
      if (this.zc >= 4) {
        CashCon.logCCLocationTransAuditLog((FFSConnectionHolder)localObject1, paramCCLocationInfo, "Add a Cash Con Location", 5303);
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCLocationInfo.getStatusMsg(), 6);
    return paramCCLocationInfo;
  }
  
  public CCLocationInfo cancelCCLocation(CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.cancelCCLocation: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCLocationInfo == null)
    {
      paramCCLocationInfo = new CCLocationInfo();
      paramCCLocationInfo.setStatusCode(16000);
      localObject1 = "CCLocationInfo object  is null";
      paramCCLocationInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCLocationInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
      paramCCLocationInfo = CashCon.cancelCCLocation((FFSConnectionHolder)localObject1, paramCCLocationInfo);
      if (paramCCLocationInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCLocationInfo.getStatusMsg(), 6);
        CCLocationInfo localCCLocationInfo = paramCCLocationInfo;
        return localCCLocationInfo;
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCLocationInfo.getStatusMsg(), 6);
    return paramCCLocationInfo;
  }
  
  public CCLocationInfo modCCLocation(CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.modCCLocation: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCLocationInfo == null)
    {
      paramCCLocationInfo = new CCLocationInfo();
      paramCCLocationInfo.setStatusCode(16000);
      localObject1 = "CCLocationInfo object  is null";
      paramCCLocationInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCLocationInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
      paramCCLocationInfo = CashCon.modCCLocation((FFSConnectionHolder)localObject1, paramCCLocationInfo);
      if (paramCCLocationInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCLocationInfo.getStatusMsg(), 6);
        CCLocationInfo localCCLocationInfo = paramCCLocationInfo;
        return localCCLocationInfo;
      }
      if (this.zc >= 4) {
        CashCon.logCCLocationTransAuditLog((FFSConnectionHolder)localObject1, paramCCLocationInfo, "Modifiy a Cash Con Location", 5304);
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCLocationInfo.getStatusMsg(), 6);
    return paramCCLocationInfo;
  }
  
  public CCLocationInfo getCCLocation(CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.getCCLocation: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCLocationInfo == null)
    {
      paramCCLocationInfo = new CCLocationInfo();
      paramCCLocationInfo.setStatusCode(16000);
      localObject1 = "CCLocationInfo object  is null";
      paramCCLocationInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCLocationInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
      paramCCLocationInfo = CashCon.getCCLocation((FFSConnectionHolder)localObject1, paramCCLocationInfo);
      if (paramCCLocationInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCLocationInfo.getStatusMsg(), 6);
        CCLocationInfo localCCLocationInfo = paramCCLocationInfo;
        return localCCLocationInfo;
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCLocationInfo.getStatusMsg(), 6);
    return paramCCLocationInfo;
  }
  
  public CCLocationInfoList getCCLocationList(CCLocationInfoList paramCCLocationInfoList)
    throws FFSException
  {
    String str1 = "CashConProcessor.getCCLocationList: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCLocationInfoList == null)
    {
      paramCCLocationInfoList = new CCLocationInfoList();
      paramCCLocationInfoList.setStatusCode(16000);
      localObject1 = "CCLocationInfoList object  is null";
      paramCCLocationInfoList.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCLocationInfoList;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
      paramCCLocationInfoList = CashCon.getCCLocationList((FFSConnectionHolder)localObject1, paramCCLocationInfoList);
      if (paramCCLocationInfoList.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCLocationInfoList.getStatusMsg(), 6);
        CCLocationInfoList localCCLocationInfoList = paramCCLocationInfoList;
        return localCCLocationInfoList;
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCLocationInfoList.getStatusMsg(), 6);
    return paramCCLocationInfoList;
  }
  
  public CCEntryInfo addCCEntry(CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.addCCEntry: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCEntryInfo == null)
    {
      paramCCEntryInfo = new CCEntryInfo();
      paramCCEntryInfo.setStatusCode(16000);
      localObject1 = "CCEntryInfo object  is null";
      paramCCEntryInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCEntryInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
      paramCCEntryInfo = CashCon.validateCCEntryInfo((FFSConnectionHolder)localObject1, paramCCEntryInfo);
      CCEntryInfo localCCEntryInfo1;
      if (paramCCEntryInfo.getStatusCode() != 0)
      {
        FFSDebug.log(str1 + "failed, Reason =" + paramCCEntryInfo.getStatusMsg(), 0);
        localCCEntryInfo1 = paramCCEntryInfo;
        return localCCEntryInfo1;
      }
      if (paramCCEntryInfo.getEntryId() != null)
      {
        paramCCEntryInfo.setStatusCode(24500);
        paramCCEntryInfo.setStatusMsg("CC Entry Id must be Null");
        localCCEntryInfo1 = paramCCEntryInfo;
        return localCCEntryInfo1;
      }
      paramCCEntryInfo.setEntryId(DBUtil.getNextIndexStringWithPadding("CCEntry", 32, '0'));
      paramCCEntryInfo.setCreatedDate(FFSUtil.getDateString("yyyy/MM/dd HH:mm:ss"));
      int i = LimitCheckApprovalProcessor.processCCEntryAdd((FFSConnectionHolder)localObject1, paramCCEntryInfo, null);
      str2 = LimitCheckApprovalProcessor.mapToStatus(i);
      FFSDebug.log(str1, "retStatus LimitCheck:" + str2, 6);
      CCEntryInfo localCCEntryInfo2;
      if (("LIMIT_CHECK_FAILED".equals(str2)) || ("LIMIT_REVERT_FAILED".equals(str2)) || ("APPROVAL_FAILED".equals(str2)) || ("APPROVAL_NOT_ALLOWED".equals(str2)))
      {
        paramCCEntryInfo.setStatus(str2);
        ((FFSConnectionHolder)localObject1).conn.rollback();
        localCCEntryInfo2 = paramCCEntryInfo;
        return localCCEntryInfo2;
      }
      paramCCEntryInfo.setStatus(str2);
      paramCCEntryInfo = CashCon.addCCEntry((FFSConnectionHolder)localObject1, paramCCEntryInfo);
      if (paramCCEntryInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCEntryInfo.getStatusMsg(), 6);
        localCCEntryInfo2 = paramCCEntryInfo;
        return localCCEntryInfo2;
      }
      if (this.zc >= 4)
      {
        int j = 5309;
        if (paramCCEntryInfo.getTransactionType().equals("CONCENTRATION")) {
          j = 5309;
        } else {
          j = 5306;
        }
        CashCon.logCCEntryTransAuditLog((FFSConnectionHolder)localObject1, paramCCEntryInfo, "Add a Cash Con Entry", j);
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCEntryInfo.getStatusMsg(), 6);
    return paramCCEntryInfo;
  }
  
  public CCEntryInfo cancelCCEntry(CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.cancelCCEntry: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCEntryInfo == null)
    {
      paramCCEntryInfo = new CCEntryInfo();
      paramCCEntryInfo.setStatusCode(16000);
      localObject1 = "CCEntryInfo object  is null";
      paramCCEntryInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCEntryInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
      CCEntryInfo localCCEntryInfo1 = new CCEntryInfo();
      localCCEntryInfo1.setEntryId(paramCCEntryInfo.getEntryId());
      localCCEntryInfo1 = CashCon.getCCEntry((FFSConnectionHolder)localObject1, localCCEntryInfo1);
      CCEntryInfo localCCEntryInfo2;
      if (localCCEntryInfo1.getStatusCode() != 0)
      {
        paramCCEntryInfo.setStatusCode(localCCEntryInfo1.getStatusCode());
        paramCCEntryInfo.setStatusMsg(localCCEntryInfo1.getStatusMsg());
        localCCEntryInfo2 = paramCCEntryInfo;
        return localCCEntryInfo2;
      }
      if ((localCCEntryInfo1.getStatus().compareTo("WILLPROCESSON") != 0) && (localCCEntryInfo1.getStatus().compareTo("APPROVAL_REJECTED") != 0) && (localCCEntryInfo1.getStatus().compareTo("APPROVAL_PENDING") != 0))
      {
        paramCCEntryInfo.setStatusCode(24220);
        paramCCEntryInfo.setStatusMsg("Invalid status for this operation. We can only cancel Entries which have status as WILLPROCESSON, APPROVAL_REJECTED or APPROVAL_PENDING, But this entry has status =" + localCCEntryInfo1.getStatus());
        localCCEntryInfo2 = paramCCEntryInfo;
        return localCCEntryInfo2;
      }
      int i = LimitCheckApprovalProcessor.processCCEntryDelete((FFSConnectionHolder)localObject1, localCCEntryInfo1, null);
      str3 = LimitCheckApprovalProcessor.mapToStatus(i);
      FFSDebug.log(str1, "retStatus LimitCheck:" + str3, 6);
      CCEntryInfo localCCEntryInfo3;
      if ("LIMIT_REVERT_FAILED".equals(str3))
      {
        FFSDebug.log(str1, " Limit Revert Failed.", 6);
        localCCEntryInfo3 = paramCCEntryInfo;
        return localCCEntryInfo3;
      }
      paramCCEntryInfo = CashCon.cancelCCEntry((FFSConnectionHolder)localObject1, paramCCEntryInfo);
      if (paramCCEntryInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCEntryInfo.getStatusMsg(), 6);
        localCCEntryInfo3 = paramCCEntryInfo;
        return localCCEntryInfo3;
      }
      if (this.zc >= 4)
      {
        int j = 5311;
        if (paramCCEntryInfo.getTransactionType().equals("CONCENTRATION")) {
          j = 5311;
        } else {
          j = 5308;
        }
        CashCon.logCCEntryTransAuditLog((FFSConnectionHolder)localObject1, paramCCEntryInfo, "Cancel a Cash Con Entry", j);
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCEntryInfo.getStatusMsg(), 6);
    return paramCCEntryInfo;
  }
  
  public CCEntryInfo modCCEntry(CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.modCCEntry: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCEntryInfo == null)
    {
      paramCCEntryInfo = new CCEntryInfo();
      paramCCEntryInfo.setStatusCode(16000);
      localObject1 = "CCEntryInfo object  is null";
      paramCCEntryInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCEntryInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
      paramCCEntryInfo = CashCon.validateCCEntryInfo((FFSConnectionHolder)localObject1, paramCCEntryInfo);
      if (paramCCEntryInfo.getStatusCode() != 0)
      {
        FFSDebug.log(str1 + "failed, Reason =" + paramCCEntryInfo.getStatusMsg(), 0);
        localCCEntryInfo1 = paramCCEntryInfo;
        return localCCEntryInfo1;
      }
      CCEntryInfo localCCEntryInfo1 = new CCEntryInfo();
      localCCEntryInfo1.setEntryId(paramCCEntryInfo.getEntryId());
      localCCEntryInfo1 = CashCon.getCCEntry((FFSConnectionHolder)localObject1, localCCEntryInfo1);
      CCEntryInfo localCCEntryInfo2;
      if (localCCEntryInfo1.getStatusCode() != 0)
      {
        paramCCEntryInfo.setStatusCode(localCCEntryInfo1.getStatusCode());
        paramCCEntryInfo.setStatusMsg(localCCEntryInfo1.getStatusMsg());
        localCCEntryInfo2 = paramCCEntryInfo;
        return localCCEntryInfo2;
      }
      if ((localCCEntryInfo1.getStatus().compareTo("WILLPROCESSON") != 0) && (localCCEntryInfo1.getStatus().compareTo("APPROVAL_REJECTED") != 0) && (localCCEntryInfo1.getStatus().compareTo("APPROVAL_PENDING") != 0))
      {
        paramCCEntryInfo.setStatusCode(24220);
        paramCCEntryInfo.setStatusMsg("Invalid status for this operation. We can only modify Entries which have status as WILLPROCESSON, APPROVAL_REJECTED or APPROVAL_PENDING, But this entry has status =" + localCCEntryInfo1.getStatus());
        localCCEntryInfo2 = paramCCEntryInfo;
        return localCCEntryInfo2;
      }
      int i = LimitCheckApprovalProcessor.processCCEntryDelete((FFSConnectionHolder)localObject1, localCCEntryInfo1, null);
      str3 = LimitCheckApprovalProcessor.mapToStatus(i);
      FFSDebug.log(str1, "retStatus LimitCheck:" + str3, 6);
      CCEntryInfo localCCEntryInfo3;
      if ("LIMIT_REVERT_FAILED".equals(str3))
      {
        FFSDebug.log(str1, " Limit Revert Failed.", 6);
        localCCEntryInfo3 = paramCCEntryInfo;
        return localCCEntryInfo3;
      }
      i = LimitCheckApprovalProcessor.processCCEntryAdd((FFSConnectionHolder)localObject1, paramCCEntryInfo, null);
      str3 = LimitCheckApprovalProcessor.mapToStatus(i);
      FFSDebug.log(str1, "retStatus LimitCheck:" + str3, 6);
      if (("LIMIT_CHECK_FAILED".equals(str3)) || ("LIMIT_REVERT_FAILED".equals(str3)) || ("APPROVAL_FAILED".equals(str3)) || ("APPROVAL_NOT_ALLOWED".equals(str3)))
      {
        localCCEntryInfo3 = paramCCEntryInfo;
        return localCCEntryInfo3;
      }
      paramCCEntryInfo.setStatus(str3);
      paramCCEntryInfo = CashCon.modCCEntry((FFSConnectionHolder)localObject1, paramCCEntryInfo);
      if (paramCCEntryInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCEntryInfo.getStatusMsg(), 6);
        localCCEntryInfo3 = paramCCEntryInfo;
        return localCCEntryInfo3;
      }
      if (this.zc >= 4)
      {
        int j = 5310;
        if (paramCCEntryInfo.getTransactionType().equals("CONCENTRATION")) {
          j = 5310;
        } else {
          j = 5307;
        }
        CashCon.logCCEntryTransAuditLog((FFSConnectionHolder)localObject1, paramCCEntryInfo, "Modify a Cash Con Entry", j);
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCEntryInfo.getStatusMsg(), 6);
    return paramCCEntryInfo;
  }
  
  public CCEntryInfo getCCEntry(CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.getCCEntry: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCEntryInfo == null)
    {
      paramCCEntryInfo = new CCEntryInfo();
      paramCCEntryInfo.setStatusCode(16000);
      localObject1 = "CCEntryInfo object  is null";
      paramCCEntryInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCEntryInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
      paramCCEntryInfo = CashCon.getCCEntry((FFSConnectionHolder)localObject1, paramCCEntryInfo);
      if (paramCCEntryInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCEntryInfo.getStatusMsg(), 6);
        CCEntryInfo localCCEntryInfo = paramCCEntryInfo;
        return localCCEntryInfo;
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCEntryInfo.getStatusMsg(), 6);
    return paramCCEntryInfo;
  }
  
  public CCEntryHistInfo getCCEntryHist(CCEntryHistInfo paramCCEntryHistInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.getCCEntryHist: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCEntryHistInfo == null)
    {
      paramCCEntryHistInfo = new CCEntryHistInfo();
      paramCCEntryHistInfo.setStatusCode(16000);
      localObject1 = "CCEntryHistInfo object  is null";
      paramCCEntryHistInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCEntryHistInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
      paramCCEntryHistInfo = CashCon.getCCEntryHist((FFSConnectionHolder)localObject1, paramCCEntryHistInfo);
      if (paramCCEntryHistInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCEntryHistInfo.getStatusMsg(), 6);
        CCEntryHistInfo localCCEntryHistInfo = paramCCEntryHistInfo;
        return localCCEntryHistInfo;
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCEntryHistInfo.getStatusMsg(), 6);
    return paramCCEntryHistInfo;
  }
  
  public CCEntrySummaryInfoList getCCEntrySummaryList(CCEntrySummaryInfoList paramCCEntrySummaryInfoList)
    throws FFSException
  {
    String str1 = "CashConProcessor.getCCEntrySummaryList: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (paramCCEntrySummaryInfoList == null)
    {
      paramCCEntrySummaryInfoList = new CCEntrySummaryInfoList();
      paramCCEntrySummaryInfoList.setStatusCode(16000);
      localObject1 = "CCEntrySummaryInfoList object  is null";
      paramCCEntrySummaryInfoList.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramCCEntrySummaryInfoList;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
      paramCCEntrySummaryInfoList = CashCon.getCCEntrySummaryList((FFSConnectionHolder)localObject1, paramCCEntrySummaryInfoList);
      if (paramCCEntrySummaryInfoList.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "end, Status =" + paramCCEntrySummaryInfoList.getStatusMsg(), 6);
        CCEntrySummaryInfoList localCCEntrySummaryInfoList = paramCCEntrySummaryInfoList;
        return localCCEntrySummaryInfoList;
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCEntrySummaryInfoList.getStatusMsg(), 6);
    return paramCCEntrySummaryInfoList;
  }
  
  public void processApprovalResult(String paramString1, String paramString2, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "CashConProcessor.processApprovalResult: ";
    FFSDebug.log(str1, "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    int i = 0;
    String str2 = null;
    FFSDebug.log(str1, "srvrTid:", paramString1, " decision:", paramString2, 6);
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      i = 17504;
      str2 = "Decision from Approval System is null";
      throw new FFSException(i, str1 + str2);
    }
    if ((paramString1 == null) || (paramString1.trim().length() == 0))
    {
      i = 17501;
      str2 = "Transaction id is null";
      throw new FFSException(i, str1 + str2);
    }
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      CCEntryInfo localCCEntryInfo = new CCEntryInfo();
      localCCEntryInfo.setEntryId(paramString1);
      localCCEntryInfo = CashCon.getCCEntry(localFFSConnectionHolder, localCCEntryInfo);
      if (localCCEntryInfo.getStatusCode() != 0)
      {
        i = 17502;
        str2 = "Transaction id is invalid";
        throw new FFSException(i, str1 + str2);
      }
      if (!localCCEntryInfo.getStatus().equalsIgnoreCase("APPROVAL_PENDING"))
      {
        i = 17503;
        str2 = "Transaction not waiting for Approval";
        throw new FFSException(i, str1 + str2);
      }
      int j = 5309;
      if (localCCEntryInfo.getTransactionType().equals("CONCENTRATION")) {
        j = 5309;
      } else {
        j = 5306;
      }
      String str7;
      if (paramString2.compareTo("Approved") == 0)
      {
        if (FFSUtil.isOlderThanToday(localCCEntryInfo.getDueDate()))
        {
          FFSDebug.log(str1 + "The due date of the CashCon entry is expired" + ": " + localCCEntryInfo.getDueDate(), 0);
          throw new FFSException(26026, "The due date of the CashCon entry is expired");
        }
        localCCEntryInfo.setStatus("WILLPROCESSON");
        CashCon.updateEntryStatus(localFFSConnectionHolder, "WILLPROCESSON", localCCEntryInfo.getEntryId());
      }
      else if (paramString2.compareTo("Rejected") == 0)
      {
        int k = LimitCheckApprovalProcessor.processCCEntryReject(localFFSConnectionHolder, localCCEntryInfo, paramHashMap);
        if (LimitCheckApprovalProcessor.mapToStatus(k).equals("LIMIT_REVERT_SUCCEEDED"))
        {
          FFSDebug.log(str1, "LIMIT REVERT SUCCEEDED", 6);
          localCCEntryInfo.setStatus("APPROVAL_REJECTED");
          CashCon.updateEntryStatus(localFFSConnectionHolder, "APPROVAL_REJECTED", localCCEntryInfo.getEntryId());
        }
        else
        {
          FFSDebug.log(str1, "LIMIT REVERT FAILED", 6);
          try
          {
            if (this.zc >= 4) {
              CashCon.logCCEntryTransAuditLog(localFFSConnectionHolder, localCCEntryInfo, "Failed to revert limit.", j);
            }
            localFFSConnectionHolder.conn.commit();
          }
          catch (Exception localException2)
          {
            str7 = "*** " + str1 + " failed to write audit log.";
            String str8 = null;
            localFFSConnectionHolder.conn.rollback();
            str8 = FFSDebug.stackTrace(localException2);
            FFSDebug.log(str7, str8, 0);
          }
          i = 17505;
          str2 = "Limit Revert Failed";
          throw new FFSException(i, str1 + str2);
        }
      }
      else
      {
        i = 17506;
        str2 = "Decision from Approval System is invalid";
        throw new FFSException(i, str1 + str2);
      }
      try
      {
        if (this.zc >= 4)
        {
          String str4 = localCCEntryInfo.getStatus();
          str6 = "";
          if (str4.equals("WILLPROCESSON")) {
            str6 = "The transaction is being approved.";
          } else if (str4.equals("APPROVAL_REJECTED")) {
            str6 = "The transaction is being rejected.";
          }
          CashCon.logCCEntryTransAuditLog(localFFSConnectionHolder, localCCEntryInfo, str6, j);
          localFFSConnectionHolder.conn.commit();
        }
      }
      catch (Exception localException1)
      {
        String str6 = "*** " + str1 + " failed to write audit log.";
        str7 = null;
        localFFSConnectionHolder.conn.rollback();
        str7 = FFSDebug.stackTrace(localException1);
        FFSDebug.log(str6, str7, 0);
      }
      if (localCCEntryInfo.getStatusCode() != 0) {
        localFFSConnectionHolder.conn.rollback();
      } else {
        localFFSConnectionHolder.conn.commit();
      }
      FFSDebug.log(str1, "Done", 6);
    }
    catch (FFSException localFFSException)
    {
      String str3 = "*** " + str1 + " failed: ";
      String str5 = null;
      localFFSConnectionHolder.conn.rollback();
      str5 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str5, 0);
      throw localFFSException;
    }
    finally
    {
      if (localFFSConnectionHolder != null) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
  }
  
  public PagingInfo getPagedCashCon(PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "CashConProcessor.getPagedCashCon ";
    FFSDebug.log(str1 + "start...", 6);
    if (paramPagingInfo == null) {
      throw new FFSException("PagingInfo is null !");
    }
    try
    {
      this.zb.getPagedData(paramPagingInfo);
    }
    catch (FFSException localFFSException)
    {
      str2 = "*** " + str1 + " failed: ";
      str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str2 = "*** " + str1 + "failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
    return paramPagingInfo;
  }
  
  public BankingDays getBankingDaysInRange(BankingDays paramBankingDays, HashMap paramHashMap)
    throws FFSException
  {
    String str = "CashConProcessor.getBankingDaysInRange: ";
    FFSDebug.log(str + "start. FIID = " + paramBankingDays.getFiID() + ", startDate= " + DBUtil.calendarToString(paramBankingDays.getStartDate()) + ", endDate = " + DBUtil.calendarToString(paramBankingDays.getEndDate()), 6);
    int i = (int)((paramBankingDays.getEndDate().getTimeInMillis() - paramBankingDays.getStartDate().getTimeInMillis()) / 86400000L + 1L);
    boolean[] arrayOfBoolean = new boolean[i];
    int j = 0;
    Calendar localCalendar1 = Calendar.getInstance();
    Calendar localCalendar2 = (Calendar)paramBankingDays.getStartDate().clone();
    Calendar localCalendar3 = paramBankingDays.getEndDate();
    while ((localCalendar2.get(6) < localCalendar1.get(6)) && (localCalendar2.get(1) <= localCalendar1.get(1)))
    {
      arrayOfBoolean[j] = false;
      j++;
      localCalendar2.add(5, 1);
    }
    while ((localCalendar2.before(localCalendar3)) || (localCalendar2.equals(localCalendar3)))
    {
      if (jdMethod_goto(paramBankingDays.getFiID(), BPWUtil.calendarToDueDateFormatInt(localCalendar2))) {
        arrayOfBoolean[j] = true;
      } else {
        arrayOfBoolean[j] = false;
      }
      j++;
      localCalendar2.add(5, 1);
    }
    paramBankingDays.setBankingDays(arrayOfBoolean);
    return paramBankingDays;
  }
  
  private boolean jdMethod_goto(String paramString, int paramInt)
  {
    try
    {
      int i = SmartCalendar.getACHPayday(paramString, paramInt);
      return i == paramInt;
    }
    catch (Exception localException) {}
    return false;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.CashConProcessor
 * JD-Core Version:    0.7.0.1
 */