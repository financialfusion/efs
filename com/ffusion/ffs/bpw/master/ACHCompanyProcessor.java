package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.ACHBatch;
import com.ffusion.ffs.bpw.db.ACHCompany;
import com.ffusion.ffs.bpw.db.ACHSameDayEffDate;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompanyList;
import com.ffusion.ffs.bpw.interfaces.ACHCompanySummaryList;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.ACHSameDayEffDateInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;

public class ACHCompanyProcessor
  implements DBConsts, FFSConst, OFXConsts, ACHConsts
{
  private int z = 1;
  
  public ACHCompanyProcessor()
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.z = localPropertyConfig.LogLevel;
  }
  
  public ACHCompanyInfo addACHCompany(ACHCompanyInfo paramACHCompanyInfo)
    throws FFSException
  {
    FFSDebug.log("ACHCompanyProcessor.addACHCompany start", 6);
    if (paramACHCompanyInfo == null)
    {
      paramACHCompanyInfo = new ACHCompanyInfo();
      paramACHCompanyInfo.setStatusCode(16000);
      localObject1 = "ACHCompanyInfo object  is null";
      paramACHCompanyInfo.setStatusMsg((String)localObject1);
      FFSDebug.log("ACHCompanyProcessor.addACHCompany, " + (String)localObject1, 0);
      return paramACHCompanyInfo;
    }
    if (!a(paramACHCompanyInfo)) {
      return paramACHCompanyInfo;
    }
    Object localObject1 = new FFSConnectionHolder();
    Object localObject2;
    try
    {
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable)
    {
      localObject2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException(localThrowable, "Could not get connection");
    }
    try
    {
      ACHCompanyInfo localACHCompanyInfo = ACHCompany.getCompByACHId(paramACHCompanyInfo.getCompACHId(), (FFSConnectionHolder)localObject1);
      if ((localACHCompanyInfo != null) && (localACHCompanyInfo.getStatusCode() == 0))
      {
        localACHCompanyInfo.setStatusCode(23210);
        localACHCompanyInfo.setStatusMsg("Company Exists");
        ((FFSConnectionHolder)localObject1).conn.commit();
        localObject2 = localACHCompanyInfo;
        return localObject2;
      }
      if (!ACHCompany.checkACHFIAndCustomerStatus(paramACHCompanyInfo, (FFSConnectionHolder)localObject1))
      {
        ((FFSConnectionHolder)localObject1).conn.commit();
        localObject2 = paramACHCompanyInfo;
        return localObject2;
      }
      paramACHCompanyInfo = ACHCompany.create(paramACHCompanyInfo, (FFSConnectionHolder)localObject1);
      if (paramACHCompanyInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        localObject2 = paramACHCompanyInfo;
        return localObject2;
      }
      a((FFSConnectionHolder)localObject1, paramACHCompanyInfo, paramACHCompanyInfo.getSubmittedBy(), "A new ACHCompany " + paramACHCompanyInfo.getCompACHId() + " has been added in BPTW.", 4241);
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (Exception localException)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      localObject2 = "*** ACHCompanyProcessor.addACHCompany failed: " + localException.toString();
      String str = FFSDebug.stackTrace(localException);
      FFSDebug.log(str, 0);
      throw new FFSException(localException, (String)localObject2);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
    FFSDebug.log("ACHCompanyProcessor.addACHCompany: done", 6);
    return paramACHCompanyInfo;
  }
  
  public ACHCompanyInfo modACHCompany(ACHCompanyInfo paramACHCompanyInfo)
    throws FFSException
  {
    FFSDebug.log("ACHCompanyProcessor.modACHCompany start", 6);
    if (paramACHCompanyInfo == null)
    {
      paramACHCompanyInfo = new ACHCompanyInfo();
      paramACHCompanyInfo.setStatusCode(16000);
      paramACHCompanyInfo.setStatusMsg("ACHCompanyInfo object  is null");
      FFSDebug.log("ACHCompanyProcessor.modACHCompany, ACHCompanyInfo object  is null");
      return paramACHCompanyInfo;
    }
    String str1 = paramACHCompanyInfo.getCompId();
    if ((str1 == null) || (str1.trim().equals("")))
    {
      paramACHCompanyInfo.setStatusCode(16010);
      localObject1 = "compInfo  contains null  compId";
      paramACHCompanyInfo.setStatusMsg("ACHCompanyProcessor.modACHCompany, " + (String)localObject1);
      FFSDebug.log((String)localObject1, 0);
      return paramACHCompanyInfo;
    }
    if ((paramACHCompanyInfo.getCompName() == null) || (paramACHCompanyInfo.getCompName().trim().length() == 0))
    {
      paramACHCompanyInfo.setStatusCode(16000);
      localObject1 = "CompName   is null";
      paramACHCompanyInfo.setStatusMsg((String)localObject1);
      FFSDebug.log("ACHCompanyProcessor.modACHCompany, " + (String)localObject1);
      return paramACHCompanyInfo;
    }
    Object localObject1 = new FFSConnectionHolder();
    String str2;
    try
    {
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable)
    {
      str2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable, "Could not get connection");
    }
    try
    {
      paramACHCompanyInfo = ACHCompany.updateACHCompany(paramACHCompanyInfo, (FFSConnectionHolder)localObject1);
      ACHCompanyInfo localACHCompanyInfo;
      if (paramACHCompanyInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        localACHCompanyInfo = paramACHCompanyInfo;
        return localACHCompanyInfo;
      }
      if (this.z >= 3)
      {
        localACHCompanyInfo = ACHCompany.getCompanyInfo(str1, (FFSConnectionHolder)localObject1);
        localACHCompanyInfo.setAgentId(paramACHCompanyInfo.getAgentId());
        localACHCompanyInfo.setAgentType(paramACHCompanyInfo.getAgentType());
        localACHCompanyInfo.setAgentName(paramACHCompanyInfo.getAgentName());
        a((FFSConnectionHolder)localObject1, localACHCompanyInfo, paramACHCompanyInfo.getSubmittedBy(), "An ACHCompany " + localACHCompanyInfo.getCompACHId() + " has been modified in BPTW.", 4242);
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (Exception localException)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      str2 = "*** ACHCompanyProcessor.modACHCompany failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
    FFSDebug.log("ACHCompanyProcessor.modACHCompany: done", 6);
    return paramACHCompanyInfo;
  }
  
  public ACHCompanyInfo canACHCompany(ACHCompanyInfo paramACHCompanyInfo)
    throws FFSException
  {
    String str1 = "ACHCompanyProcessor.canACHCompany:";
    FFSDebug.log(str1 + " start", 6);
    if (paramACHCompanyInfo == null)
    {
      paramACHCompanyInfo = new ACHCompanyInfo();
      paramACHCompanyInfo.setStatusCode(16000);
      str2 = "ACHCompanyInfo object  is null";
      paramACHCompanyInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + str2);
      return paramACHCompanyInfo;
    }
    String str2 = paramACHCompanyInfo.getCompId();
    if ((str2 == null) || (str2.trim().equals("")))
    {
      paramACHCompanyInfo.setStatusCode(16010);
      localObject1 = "compInfo  is null compId";
      paramACHCompanyInfo.setStatusMsg(str1 + (String)localObject1);
      FFSDebug.log((String)localObject1, 0);
      return paramACHCompanyInfo;
    }
    Object localObject1 = new FFSConnectionHolder();
    Object localObject2;
    try
    {
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable)
    {
      localObject2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException(localThrowable, "Could not get connection");
    }
    try
    {
      if (ACHBatch.isTransactionPendingForThisCompany((FFSConnectionHolder)localObject1, str2) == true)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "Failed to cancel ACHCompany " + paramACHCompanyInfo.getCompId(), 6);
        paramACHCompanyInfo.setStatusCode(24240);
        paramACHCompanyInfo.setStatusMsg("Pending transaction(s) exists for this ACHCompany, compId =" + paramACHCompanyInfo.getCompId());
        localACHCompanyInfo = paramACHCompanyInfo;
        return localACHCompanyInfo;
      }
      ACHCompanyInfo localACHCompanyInfo = ACHCompany.getCompanyInfo(str2, (FFSConnectionHolder)localObject1);
      paramACHCompanyInfo = ACHCompany.updateStatusForACHCompany(paramACHCompanyInfo, "CLOSED", (FFSConnectionHolder)localObject1);
      if (paramACHCompanyInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        FFSDebug.log(str1 + "Failed to cancel ACHCompany " + paramACHCompanyInfo.getCompId(), 6);
        localObject2 = paramACHCompanyInfo;
        return localObject2;
      }
      localACHCompanyInfo.setCompStatus("CLOSED");
      localACHCompanyInfo.setAgentId(paramACHCompanyInfo.getAgentId());
      localACHCompanyInfo.setAgentType(paramACHCompanyInfo.getAgentType());
      localACHCompanyInfo.setAgentName(paramACHCompanyInfo.getAgentName());
      a((FFSConnectionHolder)localObject1, localACHCompanyInfo, paramACHCompanyInfo.getSubmittedBy(), "Cancel an ACH Company.", 4243);
      a((FFSConnectionHolder)localObject1, str2);
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (Exception localException)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      localObject2 = "*** " + str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, (String)localObject2);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
    FFSDebug.log(str1 + " done", 6);
    return paramACHCompanyInfo;
  }
  
  public ACHCompanyInfo getACHCompany(String paramString)
    throws FFSException
  {
    FFSDebug.log("ACHCompanyProcessor.getACHCompany start", 6);
    if (paramString == null)
    {
      localACHCompanyInfo = new ACHCompanyInfo();
      localACHCompanyInfo.setStatusCode(16000);
      localObject1 = "compId  is null";
      localACHCompanyInfo.setStatusMsg((String)localObject1);
      FFSDebug.log("ACHCompanyProcessor.getACHCompany, " + (String)localObject1, 0);
      return localACHCompanyInfo;
    }
    ACHCompanyInfo localACHCompanyInfo = null;
    Object localObject1 = new FFSConnectionHolder();
    String str1;
    try
    {
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable)
    {
      str1 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str1, 0);
      throw new FFSException(localThrowable, "Could not get connection");
    }
    try
    {
      localACHCompanyInfo = ACHCompany.getCompanyInfo(paramString, (FFSConnectionHolder)localObject1);
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (Exception localException)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      str1 = "*** ACHCompanyProcessor.getACHCompany failed: ";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException, str1);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
    FFSDebug.log("ACHCompanyProcessor.getACHCompany: done", 6);
    return localACHCompanyInfo;
  }
  
  public ACHCompanyInfo activateCompany(String paramString)
    throws FFSException
  {
    FFSDebug.log("ACHCompanyProcessor.activateCompany start", 6);
    ACHCompanyInfo localACHCompanyInfo1 = null;
    if (paramString == null)
    {
      localACHCompanyInfo1 = new ACHCompanyInfo();
      localACHCompanyInfo1.setStatusCode(16000);
      localObject1 = "compId  is null";
      localACHCompanyInfo1.setStatusMsg((String)localObject1);
      FFSDebug.log("ACHCompanyProcessor.activateCompany, " + (String)localObject1);
      return localACHCompanyInfo1;
    }
    Object localObject1 = new FFSConnectionHolder();
    String str1;
    try
    {
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable)
    {
      str1 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str1, 0);
      throw new FFSException(localThrowable, "Could not get connection");
    }
    try
    {
      localACHCompanyInfo1 = ACHCompany.activateCompany(paramString, (FFSConnectionHolder)localObject1);
      ACHCompanyInfo localACHCompanyInfo2;
      if (localACHCompanyInfo1.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        localACHCompanyInfo2 = localACHCompanyInfo1;
        return localACHCompanyInfo2;
      }
      if (this.z >= 3)
      {
        localACHCompanyInfo2 = ACHCompany.getCompanyInfo(paramString, (FFSConnectionHolder)localObject1);
        a((FFSConnectionHolder)localObject1, localACHCompanyInfo2, localACHCompanyInfo2.getSubmittedBy(), "Activate an ACHCompany.", 4242);
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (Exception localException)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      str1 = "*** ACHCompanyProcessor.activateCompany failed: ";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException, str1);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
    FFSDebug.log("ACHCompanyProcessor.activateCompany: done", 6);
    return localACHCompanyInfo1;
  }
  
  private static boolean a(ACHCompanyInfo paramACHCompanyInfo)
  {
    FFSDebug.log("ACHCompanyProcessor.validateCompInfo: done", 6);
    String str;
    if ((paramACHCompanyInfo.getODFIACHId() == null) || (paramACHCompanyInfo.getODFIACHId().trim().length() == 0))
    {
      paramACHCompanyInfo.setStatusCode(16010);
      str = "ODFIACHId   contains null ";
      paramACHCompanyInfo.setStatusMsg(str);
      FFSDebug.log("ACHCompanyProcessor.validateCompInfo, " + str, 0);
      return false;
    }
    if ((paramACHCompanyInfo.getCompName() == null) || (paramACHCompanyInfo.getCompName().trim().length() == 0))
    {
      paramACHCompanyInfo.setStatusCode(16010);
      str = "CompName   contains null ";
      paramACHCompanyInfo.setStatusMsg(str);
      FFSDebug.log("ACHCompanyProcessor.validateCompInfo, " + str, 0);
      return false;
    }
    if (paramACHCompanyInfo.getCompName().trim().length() > 16)
    {
      paramACHCompanyInfo.setStatusCode(16015);
      str = "Company Name  Exceeds maximum length: " + paramACHCompanyInfo.getCompName().trim().length();
      paramACHCompanyInfo.setStatusMsg(str);
      FFSDebug.log("ACHCompanyProcessor.validateCompInfo, " + str, 0);
      return false;
    }
    if ((paramACHCompanyInfo.getCompACHId() == null) || (paramACHCompanyInfo.getCompACHId().trim().length() == 0))
    {
      paramACHCompanyInfo.setStatusCode(16010);
      str = "CompACHId   contains null ";
      paramACHCompanyInfo.setStatusMsg(str);
      FFSDebug.log("ACHCompanyProcessor.validateCompInfo, " + str, 0);
      return false;
    }
    if ((paramACHCompanyInfo.getCustomerId() == null) || (paramACHCompanyInfo.getCustomerId().trim().length() == 0))
    {
      paramACHCompanyInfo.setStatusCode(16010);
      str = "CustomerId   contains null ";
      paramACHCompanyInfo.setStatusMsg(str);
      FFSDebug.log("ACHCompanyProcessor.validateCompInfo, " + str, 0);
      return false;
    }
    FFSDebug.log("ACHCompanyProcessor.validateCompInfo: done", 6);
    return true;
  }
  
  public ACHCompanyList getACHCompanyList(ACHCompanyList paramACHCompanyList)
    throws FFSException
  {
    String str1 = "ACHCompanyProcessor.getACHCompanyList: ";
    FFSDebug.log(str1 + " start", 6);
    if (paramACHCompanyList == null)
    {
      paramACHCompanyList = new ACHCompanyList();
      paramACHCompanyList.setStatusCode(16000);
      localObject1 = ": Parameter ACHCompanyList  is null";
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramACHCompanyList;
    }
    Object localObject1 = new FFSConnectionHolder();
    String str2;
    try
    {
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable)
    {
      str2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable, "Could not get connection");
    }
    try
    {
      paramACHCompanyList = ACHCompany.getACHCompanyList(paramACHCompanyList, (FFSConnectionHolder)localObject1);
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (Exception localException)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      str2 = str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
    FFSDebug.log(str1 + " done", 6);
    return paramACHCompanyList;
  }
  
  public ACHCompanySummaryList getACHCompanySummaries(ACHCompanySummaryList paramACHCompanySummaryList)
    throws FFSException
  {
    String str1 = "ACHCompanyProcessor.getACHCompanySummaries: ";
    FFSDebug.log(str1 + " start", 6);
    if (paramACHCompanySummaryList == null)
    {
      paramACHCompanySummaryList = new ACHCompanySummaryList();
      paramACHCompanySummaryList.setStatusCode(16000);
      localObject1 = ": Parameter ACHCompanySummaryList  is null";
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramACHCompanySummaryList;
    }
    Object localObject1 = new FFSConnectionHolder();
    String str2;
    try
    {
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable)
    {
      str2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable, "Could not get connection");
    }
    try
    {
      paramACHCompanySummaryList = ACHCompany.getACHCompanySummaries(paramACHCompanySummaryList, (FFSConnectionHolder)localObject1);
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (Exception localException)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      str2 = str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
    FFSDebug.log(str1 + " done", 6);
    return paramACHCompanySummaryList;
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, ACHCompanyInfo paramACHCompanyInfo, String paramString1, String paramString2, int paramInt)
    throws FFSException
  {
    if (this.z >= 3) {
      ACHCompany.doTransAuditLog(paramFFSConnectionHolder, paramACHCompanyInfo, paramString1, paramString2, paramInt);
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    ACHSameDayEffDateInfo localACHSameDayEffDateInfo = new ACHSameDayEffDateInfo();
    localACHSameDayEffDateInfo.setObjectId(paramString);
    localACHSameDayEffDateInfo.setObjectType(1);
    ACHSameDayEffDate.setACHSameDayEffDateInfo(paramFFSConnectionHolder, localACHSameDayEffDateInfo);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.ACHCompanyProcessor
 * JD-Core Version:    0.7.0.1
 */