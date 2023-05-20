package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import java.util.ArrayList;

public class ACHBatchTemplate
  implements FFSConst, DBConsts, SQLConsts, ACHConsts
{
  private static final int kh = 0;
  private static final int kg = 1;
  private static final int kf = 2;
  
  public static ACHBatchTemplateInfo create(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchTemplateInfo paramACHBatchTemplateInfo)
    throws FFSException
  {
    FFSDebug.log("***ACHBatchTemplate.create start ...", 6);
    if (paramACHBatchTemplateInfo == null)
    {
      paramACHBatchTemplateInfo = new ACHBatchTemplateInfo();
      paramACHBatchTemplateInfo.setStatusCode(16000);
      paramACHBatchTemplateInfo.setStatusMsg("achBatchTemplateInfo object  is null");
      FFSDebug.log("ACHBatchTemplate.create, ACHBatchTemplateInfo  object  is null", 6);
      return paramACHBatchTemplateInfo;
    }
    String str1 = paramACHBatchTemplateInfo.getTemplateName();
    String str2 = paramACHBatchTemplateInfo.getTemplateExtId();
    if ((str1 == null) || (str1.trim().length() == 0))
    {
      paramACHBatchTemplateInfo.setStatusCode(16010);
      str3 = "achBatchTemplateInfo  contains null  templateName";
      paramACHBatchTemplateInfo.setStatusMsg("ACHBatchTemplate.create, " + str3);
      FFSDebug.log(str3, 0);
      return paramACHBatchTemplateInfo;
    }
    String str3 = "SELECT TemplateId, CompId, CompData, ODFIACHId, BatchType, TemplateExtId, TemplateName, NickName, TemplateGroup, TemplateRank, ExtdInfo, CompEntryDesc, SrvClassCode, CountryCode, OriginCurrCode, DestCurrCode, MsgAuthCode, SubmitDate, BatchStatus, AchVersion, LogId, StdClassCode, FrgnExInd, FrgnExRefInd, FrgnExRef, OrigStatusCode  FROM ACH_BatchTemplate WHERE TemplateName = ? AND TemplateExtId = ?";
    Object[] arrayOfObject = { str1, str2 };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        a(localFFSResultSet, paramACHBatchTemplateInfo);
        paramACHBatchTemplateInfo.setStatusCode(23290);
        paramACHBatchTemplateInfo.setStatusMsg("Batch Template already exists  With Template name = " + str1 + " ," + " And Template ExtID = " + str2);
        localObject1 = paramACHBatchTemplateInfo;
        return localObject1;
      }
      paramACHBatchTemplateInfo.setTemplateId(DBUtil.getNextIndexStringWithPadding("TEMPLATEID", 10, '0'));
      paramACHBatchTemplateInfo.setSubmitDate(FFSUtil.getDateString());
      paramACHBatchTemplateInfo.setTemplateStatus("ACTIVE");
      Object localObject1 = paramACHBatchTemplateInfo.getBatchType();
      if ((localObject1 == null) || (((String)localObject1).trim().length() == 0)) {
        paramACHBatchTemplateInfo.setBatchType("TEMPLATE");
      }
      str2 = paramACHBatchTemplateInfo.getTemplateExtId();
      if ((str2 == null) || (str2.trim().length() == 0)) {
        paramACHBatchTemplateInfo.setTemplateExtId(paramACHBatchTemplateInfo.getTemplateId());
      }
      str4 = ACHFI.getACHFIStatus(paramFFSConnectionHolder, paramACHBatchTemplateInfo.getOdfiId());
      if ((str4 == null) || (str4.equals("CLOSED")))
      {
        str5 = "***ACHBatchTemplate.create failed: ACH FI with specified ODFIACHID does not exist";
        FFSDebug.log(str5, 0);
        paramACHBatchTemplateInfo.setStatusCode(23170);
        paramACHBatchTemplateInfo.setStatusMsg("FIId does not exist :" + paramACHBatchTemplateInfo.getOdfiId());
        localObject2 = paramACHBatchTemplateInfo;
        return localObject2;
      }
      String str5 = ACHCompany.getCompanyStatus(paramACHBatchTemplateInfo.getCompId(), paramFFSConnectionHolder);
      if ((str5 == null) || (str5.equals("CLOSED")))
      {
        localObject2 = "***ACHBatchTemplate.create failed: ACHCompany with specified CompId does not exist";
        FFSDebug.log((String)localObject2, 0);
        paramACHBatchTemplateInfo.setStatusCode(16080);
        paramACHBatchTemplateInfo.setStatusMsg("Company does not exist:" + paramACHBatchTemplateInfo.getCompId());
        localObject3 = paramACHBatchTemplateInfo;
        return localObject3;
      }
      Object localObject2 = "INSERT INTO ACH_BatchTemplate (TemplateId, CompId, CompData, ODFIACHId, BatchType, TemplateExtId, TemplateName, NickName, TemplateGroup, TemplateRank, ExtdInfo, CompEntryDesc, SrvClassCode, CountryCode, OriginCurrCode, DestCurrCode, MsgAuthCode, StdClassCode,FrgnExInd, FrgnExRefInd, FrgnExRef, OrigStatusCode, SubmitDate, BatchStatus, AchVersion, LogId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      Object localObject3 = { paramACHBatchTemplateInfo.getTemplateId(), paramACHBatchTemplateInfo.getCompId(), paramACHBatchTemplateInfo.getCompData(), paramACHBatchTemplateInfo.getOdfiId(), paramACHBatchTemplateInfo.getBatchType(), paramACHBatchTemplateInfo.getTemplateExtId(), paramACHBatchTemplateInfo.getTemplateName(), paramACHBatchTemplateInfo.getNickName(), paramACHBatchTemplateInfo.getTemplateGroup(), paramACHBatchTemplateInfo.getTemplateRank(), paramACHBatchTemplateInfo.getExtdInfo(), paramACHBatchTemplateInfo.getCompEntryDesc(), paramACHBatchTemplateInfo.getClassCode(), paramACHBatchTemplateInfo.getCountryCode(), paramACHBatchTemplateInfo.getOriginCurrCode(), paramACHBatchTemplateInfo.getDestCurrCode(), paramACHBatchTemplateInfo.getMsgAuthCode(), paramACHBatchTemplateInfo.getStdClassCode(), paramACHBatchTemplateInfo.getFrgnExInd(), paramACHBatchTemplateInfo.getFrgnExRefInd(), paramACHBatchTemplateInfo.getFrgnExRef(), paramACHBatchTemplateInfo.getOrigStatusCode(), paramACHBatchTemplateInfo.getSubmitDate(), paramACHBatchTemplateInfo.getTemplateStatus(), paramACHBatchTemplateInfo.getAchVersion(), paramACHBatchTemplateInfo.getLogId() };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, (String)localObject2, (Object[])localObject3);
      FFSDebug.log("ACHBatchTemplate.create: Number of records added " + i, 6);
      FFSDebug.log("***ACHBatchTemplate.create end ", 6);
      paramACHBatchTemplateInfo.setStatusCode(0);
      paramACHBatchTemplateInfo.setStatusMsg("Success");
      ACHBatchTemplateInfo localACHBatchTemplateInfo = paramACHBatchTemplateInfo;
      return localACHBatchTemplateInfo;
    }
    catch (Throwable localThrowable)
    {
      String str4 = "***ACHBatchTemplate.create failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, 0);
      throw new FFSException(localThrowable, str4);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log("***ACHBatchTemplate.create failed to close resultset", 0);
        }
      }
    }
  }
  
  public static ACHBatchTemplateInfo modify(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchTemplateInfo paramACHBatchTemplateInfo)
    throws FFSException
  {
    FFSDebug.log("***ACHBatchTemplate.modify start ...", 6);
    if (paramACHBatchTemplateInfo == null)
    {
      paramACHBatchTemplateInfo = new ACHBatchTemplateInfo();
      paramACHBatchTemplateInfo.setStatusCode(16000);
      paramACHBatchTemplateInfo.setStatusMsg("achBatchTemplateInfo object  is null");
      FFSDebug.log("ACHBatchTemplate.modify, ACHBatchTemplateInfo  is null", 6);
      return paramACHBatchTemplateInfo;
    }
    String str1 = paramACHBatchTemplateInfo.getTemplateId();
    if ((str1 == null) || (str1.trim().equals("")))
    {
      paramACHBatchTemplateInfo.setStatusCode(16010);
      str2 = "achBatchTemplateInfo  is null templateId";
      paramACHBatchTemplateInfo.setStatusMsg("ACHBatchTemplate.modify, " + str2);
      FFSDebug.log(str2, 0);
      return paramACHBatchTemplateInfo;
    }
    String str2 = "UPDATE ACH_BatchTemplate SET CompData = ?, BatchType = ?, NickName = ?, TemplateGroup = ?, TemplateRank = ?, ExtdInfo = ?, CompEntryDesc = ?, SrvClassCode = ?, CountryCode = ?, OriginCurrCode = ?, DestCurrCode = ?, MsgAuthCode = ?, AchVersion = ?, StdClassCode = ?, FrgnExInd = ?, FrgnExRefInd = ?, FrgnExRef = ?, OrigStatusCode = ?  WHERE TemplateId = ?";
    Object[] arrayOfObject = { paramACHBatchTemplateInfo.getCompData(), paramACHBatchTemplateInfo.getBatchType(), paramACHBatchTemplateInfo.getNickName(), paramACHBatchTemplateInfo.getTemplateGroup(), paramACHBatchTemplateInfo.getTemplateRank(), paramACHBatchTemplateInfo.getExtdInfo(), paramACHBatchTemplateInfo.getCompEntryDesc(), paramACHBatchTemplateInfo.getClassCode(), paramACHBatchTemplateInfo.getCountryCode(), paramACHBatchTemplateInfo.getOriginCurrCode(), paramACHBatchTemplateInfo.getDestCurrCode(), paramACHBatchTemplateInfo.getMsgAuthCode(), paramACHBatchTemplateInfo.getAchVersion(), paramACHBatchTemplateInfo.getStdClassCode(), paramACHBatchTemplateInfo.getFrgnExInd(), paramACHBatchTemplateInfo.getFrgnExRefInd(), paramACHBatchTemplateInfo.getFrgnExRef(), paramACHBatchTemplateInfo.getOrigStatusCode(), paramACHBatchTemplateInfo.getTemplateId() };
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      FFSDebug.log("ACHBatchTemplate.modify: Number of records updated " + i, 6);
      FFSDebug.log("***ACHBatchTemplate.modify end", 6);
      if (i == 0)
      {
        paramACHBatchTemplateInfo.setStatusCode(16020);
        paramACHBatchTemplateInfo.setStatusMsg("ACH_BATCHTEMPLATE  record not found");
        return paramACHBatchTemplateInfo;
      }
      paramACHBatchTemplateInfo.setStatusCode(0);
      paramACHBatchTemplateInfo.setStatusMsg("Success");
      return paramACHBatchTemplateInfo;
    }
    catch (Throwable localThrowable)
    {
      String str3 = "*** ACHBatchTemplate.modify failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable, str3);
    }
  }
  
  public static ACHBatchTemplateInfo delete(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchTemplateInfo paramACHBatchTemplateInfo)
    throws FFSException
  {
    FFSDebug.log("***achBatchTemplate.delete start ...", 6);
    if (paramACHBatchTemplateInfo == null)
    {
      paramACHBatchTemplateInfo = new ACHBatchTemplateInfo();
      paramACHBatchTemplateInfo.setStatusCode(16000);
      str1 = "ACHBatchTemplateInfo object  is null";
      paramACHBatchTemplateInfo.setStatusMsg(str1);
      FFSDebug.log("ACHBatchTemplate.delete, " + str1, 6);
      return paramACHBatchTemplateInfo;
    }
    String str1 = paramACHBatchTemplateInfo.getTemplateId();
    if ((str1 == null) || (str1.trim().equals("")))
    {
      paramACHBatchTemplateInfo.setStatusCode(16010);
      localObject = "achBatchTemplateInfo  is null templateId";
      paramACHBatchTemplateInfo.setStatusMsg("ACHBatchTemplate.delete, " + (String)localObject);
      FFSDebug.log((String)localObject, 0);
      return paramACHBatchTemplateInfo;
    }
    Object localObject = { paramACHBatchTemplateInfo.getTemplateId() };
    try
    {
      String str2 = "DELETE FROM ACH_BatchTemplate WHERE TemplateId = ?";
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      FFSDebug.log("ACHBatchTemplate.delete: Number of template deleted " + i, 6);
      if (i == 0)
      {
        paramACHBatchTemplateInfo.setStatusCode(16020);
        paramACHBatchTemplateInfo.setStatusMsg("ACHBatchTemplateInfo  record not found");
        return paramACHBatchTemplateInfo;
      }
      paramACHBatchTemplateInfo.setStatusCode(0);
      paramACHBatchTemplateInfo.setStatusMsg("Success");
      return paramACHBatchTemplateInfo;
    }
    catch (Throwable localThrowable)
    {
      String str3 = "*** ACHBatchTemplate.delete failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 6);
      throw new FFSException(localThrowable, str3);
    }
  }
  
  public static ACHBatchTemplateInfo getACHBatchTemplate(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("***ACHBatchTemplate.getACHBatchTemplate start ...", 6);
    ACHBatchTemplateInfo localACHBatchTemplateInfo1 = null;
    if (paramString == null)
    {
      str1 = "***ACHBatchTemplate.getACHBatchTemplate failed: templateId is null";
      FFSDebug.log(str1, 0);
      localACHBatchTemplateInfo1 = new ACHBatchTemplateInfo();
      localACHBatchTemplateInfo1.setStatusCode(16000);
      localACHBatchTemplateInfo1.setStatusMsg("TemplateId  is null");
      return localACHBatchTemplateInfo1;
    }
    String str1 = "SELECT TemplateId, CompId, CompData, ODFIACHId, BatchType, TemplateExtId, TemplateName, NickName, TemplateGroup, TemplateRank, ExtdInfo, CompEntryDesc, SrvClassCode, CountryCode, OriginCurrCode, DestCurrCode, MsgAuthCode, SubmitDate, BatchStatus, AchVersion, LogId,StdClassCode, FrgnExInd, FrgnExRefInd, FrgnExRef, OrigStatusCode  FROM ACH_BatchTemplate WHERE TemplateId = ?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localACHBatchTemplateInfo1 = new ACHBatchTemplateInfo();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        a(localFFSResultSet, localACHBatchTemplateInfo1);
        FFSDebug.log("***ACHBatchTemplate.getACHBatchTemplate end", 6);
        localACHBatchTemplateInfo2 = localACHBatchTemplateInfo1;
        return localACHBatchTemplateInfo2;
      }
      localACHBatchTemplateInfo1.setStatusCode(16020);
      localACHBatchTemplateInfo1.setStatusMsg("ACH_BatchTemplate record not found");
      FFSDebug.log("***ACHBatchTemplate.getACHBatchTemplate end", 6);
      ACHBatchTemplateInfo localACHBatchTemplateInfo2 = localACHBatchTemplateInfo1;
      return localACHBatchTemplateInfo2;
    }
    catch (Throwable localThrowable)
    {
      String str2 = "*** ACHBatchTemplate.getACHBatchTemplate failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log("***ACHBatchTemplate.getACHBatchTemplate failed to close resultset", 0);
        }
      }
    }
  }
  
  public static ACHBatchTemplateInfo[] getACHBatchTemplate(FFSConnectionHolder paramFFSConnectionHolder, String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("***ACHBatchTemplate.getACHBatchTemplate start", 6);
    ACHBatchTemplateInfo localACHBatchTemplateInfo = null;
    ArrayList localArrayList = new ArrayList();
    Object localObject;
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      String str = "***ACHBatchTemplate.getACHBatchTemplate  (Multiple) failed: Null or Empty TemplateId Array passed";
      FFSDebug.log(str, 0);
      localACHBatchTemplateInfo = new ACHBatchTemplateInfo();
      localACHBatchTemplateInfo.setStatusCode(16000);
      localACHBatchTemplateInfo.setStatusMsg("TemplateId Array is null");
      localObject = new ACHBatchTemplateInfo[] { localACHBatchTemplateInfo };
      return localObject;
    }
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      if (paramArrayOfString[i] == null)
      {
        localObject = "***ACHBatchTemplate.getACHBatchTemplate (Multiple) failedNull TemplateId Object passed";
        FFSDebug.log((String)localObject, 0);
        localACHBatchTemplateInfo = new ACHBatchTemplateInfo();
        localACHBatchTemplateInfo.setStatusCode(16000);
        localACHBatchTemplateInfo.setStatusMsg("ACHBatchTemplateInfo  is null");
      }
      else
      {
        localACHBatchTemplateInfo = getACHBatchTemplate(paramFFSConnectionHolder, paramArrayOfString[i]);
      }
      localArrayList.add(localACHBatchTemplateInfo);
    }
    return (ACHBatchTemplateInfo[])localArrayList.toArray(new ACHBatchTemplateInfo[0]);
  }
  
  public static ACHBatchTemplateInfo[] getACHBatchTemplateByCompany(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("***ACHBatchTemplate.getACHBatchTemplateByCompany start ...", 6);
    if (paramString == null)
    {
      localObject1 = "***ACHBatchTemplate.getACHBatchTemplateByCompany failed: compId is null";
      FFSDebug.log((String)localObject1, 0);
      localACHBatchTemplateInfo = new ACHBatchTemplateInfo();
      localACHBatchTemplateInfo.setStatusCode(16000);
      localACHBatchTemplateInfo.setStatusMsg("compId  is null");
      localObject2 = new ACHBatchTemplateInfo[] { localACHBatchTemplateInfo };
      return localObject2;
    }
    Object localObject1 = new ArrayList();
    ACHBatchTemplateInfo localACHBatchTemplateInfo = null;
    Object localObject2 = "SELECT TemplateId, CompId, CompData, ODFIACHId, BatchType, TemplateExtId, TemplateName, NickName, TemplateGroup, TemplateRank, ExtdInfo, CompEntryDesc, SrvClassCode, CountryCode, OriginCurrCode, DestCurrCode, MsgAuthCode, SubmitDate, BatchStatus, AchVersion, LogId,StdClassCode, FrgnExInd, FrgnExRefInd, FrgnExRef, OrigStatusCode  FROM ACH_BatchTemplate WHERE CompId = ?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, (String)localObject2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localACHBatchTemplateInfo = new ACHBatchTemplateInfo();
        a(localFFSResultSet, localACHBatchTemplateInfo);
        ((ArrayList)localObject1).add(localACHBatchTemplateInfo);
      }
      if (localACHBatchTemplateInfo == null)
      {
        localACHBatchTemplateInfo = new ACHBatchTemplateInfo();
        localACHBatchTemplateInfo.setStatusCode(16020);
        localACHBatchTemplateInfo.setStatusMsg("ACH_BatchTemplate record not found");
        localACHBatchTemplateInfo.setStatusMsg(" record not found");
        ((ArrayList)localObject1).add(localACHBatchTemplateInfo);
      }
      ACHBatchTemplateInfo[] arrayOfACHBatchTemplateInfo = (ACHBatchTemplateInfo[])((ArrayList)localObject1).toArray(new ACHBatchTemplateInfo[0]);
      FFSDebug.log("***ACHBatchTemplate.getACHBatchTemplateByCompany end", 6);
      localObject3 = arrayOfACHBatchTemplateInfo;
      return localObject3;
    }
    catch (Throwable localThrowable)
    {
      Object localObject3 = "***ACHBatchTemplate.getACHBatchTemplateByCompany failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject3, 0);
      throw new FFSException(localThrowable, (String)localObject3);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log("***ACHBatchTemplate.getACHBatchTemplateByCompany failed to close resultset", 0);
        }
      }
    }
  }
  
  public static ACHBatchTemplateInfo[] getACHBatchTemplateByGroup(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("***ACHBatchTemplate.getACHBatchTemplateByGroup start ...", 6);
    if (paramString1 == null)
    {
      localObject1 = "***ACHBatchTemplate.getACHBatchTemplateByGroup failed: compId is null";
      FFSDebug.log((String)localObject1, 0);
      localACHBatchTemplateInfo = new ACHBatchTemplateInfo();
      localACHBatchTemplateInfo.setStatusCode(16000);
      localACHBatchTemplateInfo.setStatusMsg("compId  is null");
      localObject2 = new ACHBatchTemplateInfo[] { localACHBatchTemplateInfo };
      return localObject2;
    }
    if (paramString2 == null)
    {
      localObject1 = "***ACHBatchTemplate.getACHBatchTemplateByGroup failed: templateGroup is null";
      FFSDebug.log((String)localObject1, 0);
      localACHBatchTemplateInfo = new ACHBatchTemplateInfo();
      localACHBatchTemplateInfo.setStatusCode(16000);
      localACHBatchTemplateInfo.setStatusMsg("templateGroup  is null");
      localObject2 = new ACHBatchTemplateInfo[] { localACHBatchTemplateInfo };
      return localObject2;
    }
    Object localObject1 = new ArrayList();
    ACHBatchTemplateInfo localACHBatchTemplateInfo = null;
    Object localObject2 = "SELECT TemplateId, CompId, CompData, ODFIACHId, BatchType, TemplateExtId, TemplateName, NickName, TemplateGroup, TemplateRank, ExtdInfo, CompEntryDesc, SrvClassCode, CountryCode, OriginCurrCode, DestCurrCode, MsgAuthCode, SubmitDate, BatchStatus, AchVersion, LogId,StdClassCode, FrgnExInd, FrgnExRefInd, FrgnExRef, OrigStatusCode  FROM ACH_BatchTemplate WHERE CompId = ? AND TemplateGroup = ?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, (String)localObject2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localACHBatchTemplateInfo = new ACHBatchTemplateInfo();
        a(localFFSResultSet, localACHBatchTemplateInfo);
        ((ArrayList)localObject1).add(localACHBatchTemplateInfo);
      }
      if (localACHBatchTemplateInfo == null)
      {
        localACHBatchTemplateInfo = new ACHBatchTemplateInfo();
        localACHBatchTemplateInfo.setStatusCode(16020);
        localACHBatchTemplateInfo.setStatusMsg("ACH_BatchTemplate record not found");
        localACHBatchTemplateInfo.setStatusMsg(" record not found");
        ((ArrayList)localObject1).add(localACHBatchTemplateInfo);
      }
      ACHBatchTemplateInfo[] arrayOfACHBatchTemplateInfo = (ACHBatchTemplateInfo[])((ArrayList)localObject1).toArray(new ACHBatchTemplateInfo[0]);
      FFSDebug.log("***ACHBatchTemplate.getACHBatchTemplateByGroup end", 6);
      localObject3 = arrayOfACHBatchTemplateInfo;
      return localObject3;
    }
    catch (Throwable localThrowable)
    {
      Object localObject3 = "***ACHBatchTemplate.getACHBatchTemplateByGroup failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject3, 0);
      throw new FFSException(localThrowable, (String)localObject3);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log("***ACHBatchTemplate.getACHBatchTemplateByGroup failed to close resultset", 0);
        }
      }
    }
  }
  
  public static int deleteTemplateByODFIACHId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("***ACHBatchTemplate.deleteTemplateByODFIACHId start ...", 6);
    String str1 = "DELETE from ACH_BatchTemplate WHERE ODFIACHId = ?";
    Object[] arrayOfObject = { paramString };
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
      FFSDebug.log("ACHBatchTemplate.deleteTemplateByODFIACHId: Number of records deleted " + i, 6);
      return i;
    }
    catch (Throwable localThrowable)
    {
      String str2 = "***ACHBatchTemplate.deleteTemplateByODFIACHId failed " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  public static int deleteTemplateByCompId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("***ACHBatchTemplate.deleteTemplateByCompId start ...", 6);
    String str1 = "DELETE from ACH_BatchTemplate WHERE CompId = ?";
    Object[] arrayOfObject = { paramString };
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
      FFSDebug.log("ACHBatchTemplate.deleteTemplateByCompId: Number of records deleted " + i, 6);
      return i;
    }
    catch (Throwable localThrowable)
    {
      String str2 = "***ACHBatchTemplate.deleteTemplateByCompId failed " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  private static void a(FFSResultSet paramFFSResultSet, ACHBatchTemplateInfo paramACHBatchTemplateInfo)
    throws FFSException
  {
    paramACHBatchTemplateInfo.setTemplateId(paramFFSResultSet.getColumnString(1));
    paramACHBatchTemplateInfo.setCompId(paramFFSResultSet.getColumnString(2));
    paramACHBatchTemplateInfo.setCompData(paramFFSResultSet.getColumnString(3));
    paramACHBatchTemplateInfo.setOdfiId(paramFFSResultSet.getColumnString(4));
    paramACHBatchTemplateInfo.setBatchType(paramFFSResultSet.getColumnString(5));
    paramACHBatchTemplateInfo.setTemplateExtId(paramFFSResultSet.getColumnString(6));
    paramACHBatchTemplateInfo.setTemplateName(paramFFSResultSet.getColumnString(7));
    paramACHBatchTemplateInfo.setNickName(paramFFSResultSet.getColumnString(8));
    paramACHBatchTemplateInfo.setTemplateGroup(paramFFSResultSet.getColumnString(9));
    paramACHBatchTemplateInfo.setTemplateRank(paramFFSResultSet.getColumnString(10));
    paramACHBatchTemplateInfo.setExtdInfo(paramFFSResultSet.getColumnString(11));
    paramACHBatchTemplateInfo.setCompEntryDesc(paramFFSResultSet.getColumnString(12));
    paramACHBatchTemplateInfo.setClassCode(paramFFSResultSet.getColumnString(13));
    paramACHBatchTemplateInfo.setCountryCode(paramFFSResultSet.getColumnString(14));
    paramACHBatchTemplateInfo.setOriginCurrCode(paramFFSResultSet.getColumnString(15));
    paramACHBatchTemplateInfo.setDestCurrCode(paramFFSResultSet.getColumnString(16));
    paramACHBatchTemplateInfo.setMsgAuthCode(paramFFSResultSet.getColumnString(17));
    paramACHBatchTemplateInfo.setSubmitDate(paramFFSResultSet.getColumnString(18));
    paramACHBatchTemplateInfo.setTemplateStatus(paramFFSResultSet.getColumnString(19));
    paramACHBatchTemplateInfo.setAchVersion(paramFFSResultSet.getColumnString(20));
    paramACHBatchTemplateInfo.setLogId(paramFFSResultSet.getColumnString(21));
    paramACHBatchTemplateInfo.setStdClassCode(paramFFSResultSet.getColumnString(22));
    paramACHBatchTemplateInfo.setFrgnExInd(paramFFSResultSet.getColumnString(23));
    paramACHBatchTemplateInfo.setFrgnExRefInd(paramFFSResultSet.getColumnString(24));
    paramACHBatchTemplateInfo.setFrgnExRef(paramFFSResultSet.getColumnString(25));
    paramACHBatchTemplateInfo.setOrigStatusCode(paramFFSResultSet.getColumnString(26));
    paramACHBatchTemplateInfo.setStatusCode(0);
    paramACHBatchTemplateInfo.setStatusMsg("Success");
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.ACHBatchTemplate
 * JD-Core Version:    0.7.0.1
 */