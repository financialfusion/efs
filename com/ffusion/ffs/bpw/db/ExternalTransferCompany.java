package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyList;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import java.util.ArrayList;

public class ExternalTransferCompany
  implements DBConsts, FFSConst, ACHConsts, BPWResource
{
  public static boolean isBPWManagedCustomer()
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    try
    {
      String str = localPropertyConfig.otherProperties.getProperty("bpw.transfer.manageCustomers", "BPW");
      return str.equalsIgnoreCase("BPW");
    }
    catch (Exception localException) {}
    return true;
  }
  
  public static ExtTransferCompanyInfo add(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws FFSException
  {
    String str1 = "ExternalTransferCompany.add: ";
    FFSDebug.log(str1, "start ...", 6);
    String str2 = null;
    String str3 = null;
    String str4 = null;
    Object localObject;
    if (paramExtTransferCompanyInfo == null)
    {
      localObject = "failed: Null ExtTransferCompanyInfo Object passed";
      FFSDebug.log("******", str1, (String)localObject, 0);
      paramExtTransferCompanyInfo = new ExtTransferCompanyInfo();
      paramExtTransferCompanyInfo.setStatusCode(16000);
      paramExtTransferCompanyInfo.setStatusMsg("ExtTransferCompanyInfo  is null");
      return paramExtTransferCompanyInfo;
    }
    if (!a(paramExtTransferCompanyInfo))
    {
      localObject = "failed. ";
      FFSDebug.log("****", str1, (String)localObject, paramExtTransferCompanyInfo.getStatusMsg(), 0);
      return paramExtTransferCompanyInfo;
    }
    try
    {
      if (!jdMethod_do(paramFFSConnectionHolder, paramExtTransferCompanyInfo))
      {
        localObject = "failed: ";
        FFSDebug.log("******", str1, (String)localObject, paramExtTransferCompanyInfo.getStatusMsg(), 0);
        return paramExtTransferCompanyInfo;
      }
      paramExtTransferCompanyInfo.setStatus("ACTIVE");
      str3 = jdMethod_if(paramFFSConnectionHolder, paramExtTransferCompanyInfo);
      if (str3 != null)
      {
        paramExtTransferCompanyInfo.setStatusCode(19700);
        paramExtTransferCompanyInfo.setStatusMsg("Duplicate record.");
        localObject = "failed: ";
        FFSDebug.log("******", str1, (String)localObject, paramExtTransferCompanyInfo.getStatusMsg(), 0);
        return paramExtTransferCompanyInfo;
      }
      paramExtTransferCompanyInfo.setCompId(DBUtil.getNextIndexStringWithPadding("ETFCOMPID", 18, '0'));
      paramExtTransferCompanyInfo.setCreateDate(FFSUtil.getDateString());
      str4 = "INSERT INTO ETF_Company (CompId, FIId, CustomerId, CompName, CompACHId, CompDescription, SubmittedBy, CreateDate, LogId, Status) VALUES (?,?,?,?,?,?,?,?,?,?)";
      FFSDebug.log(str1, "adding external transfer company: " + paramExtTransferCompanyInfo, 6);
      str2 = paramExtTransferCompanyInfo.getCustomerId();
      if ((str2 == null) || (str2.trim().length() == 0)) {
        str2 = "-1";
      }
      localObject = new Object[] { paramExtTransferCompanyInfo.getCompId(), paramExtTransferCompanyInfo.getFiId(), str2, paramExtTransferCompanyInfo.getCompName(), paramExtTransferCompanyInfo.getCompACHId(), paramExtTransferCompanyInfo.getCompDescription(), paramExtTransferCompanyInfo.getSubmittedBy(), paramExtTransferCompanyInfo.getCreateDate(), paramExtTransferCompanyInfo.getLogId(), "ACTIVE" };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str4, (Object[])localObject);
      FFSDebug.log(str1, "Number of records added " + i, 6);
      paramExtTransferCompanyInfo.setStatusCode(0);
      paramExtTransferCompanyInfo.setStatusMsg("Success");
      FFSDebug.log(str1, "End", 6);
      return paramExtTransferCompanyInfo;
    }
    catch (Throwable localThrowable)
    {
      String str5 = "***" + str1 + "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str5, 0);
      throw new FFSException(localThrowable, str5);
    }
  }
  
  public static ExtTransferCompanyInfo getExternalTransferCompany(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws FFSException
  {
    String str1 = "ExternalTransferCompany.getExternalTransferCompany: ";
    FFSDebug.log(str1, "start ...", 6);
    FFSResultSet localFFSResultSet = null;
    String str2 = null;
    if (paramExtTransferCompanyInfo == null)
    {
      str3 = "failed: Null ExtTransferCompanyInfo Object passed";
      FFSDebug.log("******", str1, str3, 0);
      paramExtTransferCompanyInfo = new ExtTransferCompanyInfo();
      paramExtTransferCompanyInfo.setStatusCode(16000);
      paramExtTransferCompanyInfo.setStatusMsg("ExtTransferCompanyInfo  is null");
      return paramExtTransferCompanyInfo;
    }
    str2 = paramExtTransferCompanyInfo.getCompId();
    if (str2 == null)
    {
      str3 = "failed: Null compId field passed";
      FFSDebug.log("******", str1, str3, 0);
      paramExtTransferCompanyInfo.setStatusCode(16010);
      paramExtTransferCompanyInfo.setStatusMsg("compId  contains null ");
      return paramExtTransferCompanyInfo;
    }
    String str3 = "SELECT a.CompId, a.FIId, a.CustomerId, a.CompName, a.CompACHId, a.CompDescription, a.SubmittedBy, a.CreateDate, a.LogId, a.Status FROM ETF_Company a  WHERE a.CompId = ?  AND a.Status <> 'CANCELEDON' ";
    Object[] arrayOfObject = { str2 };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        a(localFFSResultSet, paramExtTransferCompanyInfo);
        paramExtTransferCompanyInfo.setStatusCode(0);
        paramExtTransferCompanyInfo.setStatusMsg("Success");
      }
      else
      {
        paramExtTransferCompanyInfo.setStatusCode(16020);
        paramExtTransferCompanyInfo.setStatusMsg(" record not found");
      }
      FFSDebug.log("***", str1, "End", 6);
      ExtTransferCompanyInfo localExtTransferCompanyInfo = paramExtTransferCompanyInfo;
      return localExtTransferCompanyInfo;
    }
    catch (Throwable localThrowable)
    {
      String str4 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("****", str1, str4, 0);
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
          FFSDebug.log("****", str1, "failed to close ResultSet", localException.toString(), 0);
        }
      }
    }
  }
  
  private static String jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws FFSException
  {
    String str1 = "ExternalTransferCompany.getExternalTransferCompanyIDByACHIdCustIdStatus: ";
    FFSDebug.log(str1, "start ...", 6);
    FFSResultSet localFFSResultSet = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    if (paramExtTransferCompanyInfo == null)
    {
      localObject1 = "failed: Null ExtTransferCompanyInfo Object passed";
      FFSDebug.log("******", str1, (String)localObject1, 0);
      paramExtTransferCompanyInfo = new ExtTransferCompanyInfo();
      paramExtTransferCompanyInfo.setStatusCode(16000);
      paramExtTransferCompanyInfo.setStatusMsg("ExtTransferCompanyInfo  is null");
      return null;
    }
    str3 = paramExtTransferCompanyInfo.getCompACHId();
    str4 = paramExtTransferCompanyInfo.getCustomerId();
    str5 = paramExtTransferCompanyInfo.getFiId();
    str6 = paramExtTransferCompanyInfo.getStatus();
    Object localObject1 = new StringBuffer("SELECT a.CompId, a.FIId, a.CustomerId, a.CompName, a.CompACHId, a.CompDescription, a.SubmittedBy, a.CreateDate, a.LogId, a.Status FROM ETF_Company a  WHERE a.CompACHId = ?  AND a.Status = ? ");
    Object[] arrayOfObject = null;
    if ((str4 != null) && (str4.trim().length() != 0))
    {
      ((StringBuffer)localObject1).append(" AND a.CustomerId = ?");
      arrayOfObject = new Object[] { str3, str6, str4 };
    }
    else
    {
      ((StringBuffer)localObject1).append(" AND a.FIId = ? AND a.CustomerId = ?");
      arrayOfObject = new Object[] { str3, str6, str5, "-1" };
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, ((StringBuffer)localObject1).toString(), arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        str2 = localFFSResultSet.getColumnString("CompId");
      }
      FFSDebug.log("***", str1, "End", 6);
      String str7 = str2;
      return str7;
    }
    catch (Throwable localThrowable)
    {
      String str8 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("****", str1, str8, 0);
      throw new FFSException(localThrowable, str8);
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
          FFSDebug.log("****", str1, "failed to close ResultSet", localException.toString(), 0);
        }
      }
    }
  }
  
  public static ExtTransferCompanyList getExternalTransferCompanyList(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferCompanyList paramExtTransferCompanyList)
    throws FFSException
  {
    String str1 = "ExternalTransferCompany.getExternalTransferCompanyList: ";
    FFSDebug.log(str1, "start ...", 6);
    FFSResultSet localFFSResultSet = null;
    String str2 = null;
    String[] arrayOfString1 = null;
    String[] arrayOfString2 = null;
    StringBuffer localStringBuffer = null;
    ArrayList localArrayList1 = null;
    Object[] arrayOfObject = null;
    ArrayList localArrayList2 = null;
    ExtTransferCompanyInfo localExtTransferCompanyInfo = null;
    Object localObject1;
    if (paramExtTransferCompanyList == null)
    {
      localObject1 = "failed: Null ExtTransferCompanyList Object passed";
      FFSDebug.log("******", str1, (String)localObject1, 0);
      paramExtTransferCompanyList = new ExtTransferCompanyList();
      paramExtTransferCompanyList.setStatusCode(16000);
      paramExtTransferCompanyList.setStatusMsg("ExtTransferCompanyList  is null");
      return paramExtTransferCompanyList;
    }
    str2 = paramExtTransferCompanyList.getFIId();
    if ((str2 != null) && (str2.trim().length() == 0)) {
      str2 = null;
    }
    arrayOfString1 = paramExtTransferCompanyList.getCustomerId();
    if ((arrayOfString1 != null) && (arrayOfString1.length == 0)) {
      arrayOfString1 = null;
    }
    arrayOfString2 = paramExtTransferCompanyList.getCompId();
    if ((arrayOfString2 != null) && (arrayOfString2.length == 0)) {
      arrayOfString2 = null;
    }
    localArrayList1 = new ArrayList();
    localStringBuffer = new StringBuffer();
    localStringBuffer.append("SELECT a.CompId, a.FIId, a.CustomerId, a.CompName, a.CompACHId, a.CompDescription, a.SubmittedBy, a.CreateDate, a.LogId, a.Status FROM ETF_Company a ");
    if ((str2 == null) && (arrayOfString1 == null))
    {
      if (arrayOfString2 == null)
      {
        paramExtTransferCompanyList.setStatusCode(16000);
        paramExtTransferCompanyList.setStatusMsg("FiId, customerId, compId ExtTransferCompanyList  is null");
        return paramExtTransferCompanyList;
      }
    }
    else if ((str2 != null) && (arrayOfString1 == null))
    {
      localStringBuffer.append(" WHERE a.FIId = ? AND a.CustomerId = ?");
      localArrayList1.add(str2);
      localArrayList1.add("-1");
    }
    else
    {
      if (isBPWManagedCustomer()) {
        localStringBuffer.append(", BPW_Customer b WHERE a.CustomerId = b.CustomerID AND");
      } else {
        localStringBuffer.append(" WHERE");
      }
      DBUtil.appendArrayToCondition(localStringBuffer, arrayOfString1, " a.CustomerId IN (", localArrayList1);
    }
    if (arrayOfString2 != null)
    {
      if ((str2 == null) && (arrayOfString1 == null)) {
        localStringBuffer.append(" WHERE");
      } else {
        localStringBuffer.append(" AND");
      }
      DBUtil.appendArrayToCondition(localStringBuffer, arrayOfString2, " a.CompId IN (", localArrayList1);
    }
    localStringBuffer.append(" AND a.Status <> 'CANCELEDON' ");
    try
    {
      arrayOfObject = localArrayList1.toArray(new Object[0]);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      localArrayList2 = new ArrayList();
      while (localFFSResultSet.getNextRow())
      {
        localExtTransferCompanyInfo = new ExtTransferCompanyInfo();
        a(localFFSResultSet, localExtTransferCompanyInfo);
        localExtTransferCompanyInfo.setStatusCode(0);
        localExtTransferCompanyInfo.setStatusMsg("Success");
        localArrayList2.add(localExtTransferCompanyInfo);
      }
      if (localArrayList2.size() == 0)
      {
        paramExtTransferCompanyList.setStatusCode(16020);
        paramExtTransferCompanyList.setStatusMsg("ExtTransferCompanyInfo  record not found");
      }
      else
      {
        paramExtTransferCompanyList.setExtTransferCompanys((ExtTransferCompanyInfo[])localArrayList2.toArray(new ExtTransferCompanyInfo[0]));
        paramExtTransferCompanyList.setStatusCode(0);
        paramExtTransferCompanyList.setStatusMsg("Success");
      }
      FFSDebug.log("***", str1, "End", 6);
      localObject1 = paramExtTransferCompanyList;
      return localObject1;
    }
    catch (Throwable localThrowable)
    {
      String str3 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("****", str1, str3, 0);
      throw new FFSException(localThrowable, str3);
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
          FFSDebug.log("****", str1, "failed to close ResultSet", localException.toString(), 0);
        }
      }
    }
  }
  
  private static void a(FFSResultSet paramFFSResultSet, ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws FFSException
  {
    paramExtTransferCompanyInfo.setCompId(paramFFSResultSet.getColumnString("CompId"));
    paramExtTransferCompanyInfo.setFiId(paramFFSResultSet.getColumnString("FIId"));
    if (paramFFSResultSet.getColumnString("CustomerId").equals("-1")) {
      paramExtTransferCompanyInfo.setCustomerId(null);
    } else {
      paramExtTransferCompanyInfo.setCustomerId(paramFFSResultSet.getColumnString("CustomerId"));
    }
    paramExtTransferCompanyInfo.setCompName(paramFFSResultSet.getColumnString("CompName"));
    paramExtTransferCompanyInfo.setCompACHId(paramFFSResultSet.getColumnString("CompACHId"));
    paramExtTransferCompanyInfo.setCompDescription(paramFFSResultSet.getColumnString("CompDescription"));
    paramExtTransferCompanyInfo.setStatus(paramFFSResultSet.getColumnString("Status"));
    paramExtTransferCompanyInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
    paramExtTransferCompanyInfo.setCreateDate(paramFFSResultSet.getColumnString("CreateDate"));
    paramExtTransferCompanyInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
  }
  
  private static boolean a(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
  {
    String str1 = "ExternalTransferCompany.validateETFCompInfo: ";
    FFSDebug.log(str1, ": start", 6);
    FFSDebug.log(str1, ": CustomerId = ", paramExtTransferCompanyInfo.getCustomerId(), ", CompName = ", paramExtTransferCompanyInfo.getCompName(), ", CompACHId = ", paramExtTransferCompanyInfo.getCompACHId(), ", FiId = ", paramExtTransferCompanyInfo.getFiId(), ", SubmittedBy = ", paramExtTransferCompanyInfo.getSubmittedBy(), ", LogId = ", paramExtTransferCompanyInfo.getLogId(), 6);
    String str2;
    if (((paramExtTransferCompanyInfo.getCustomerId() == null) || (paramExtTransferCompanyInfo.getCustomerId().trim().length() == 0)) && ((paramExtTransferCompanyInfo.getFiId() == null) || (paramExtTransferCompanyInfo.getFiId().trim().length() == 0)))
    {
      paramExtTransferCompanyInfo.setStatusCode(16010);
      str2 = "CustomerId and FiId  contains null ";
      paramExtTransferCompanyInfo.setStatusMsg(str2);
      FFSDebug.log(str1, str2, 0);
      return false;
    }
    if ((paramExtTransferCompanyInfo.getCompName() == null) || (paramExtTransferCompanyInfo.getCompName().trim().length() == 0))
    {
      paramExtTransferCompanyInfo.setStatusCode(16010);
      str2 = "Company Name contains null ";
      paramExtTransferCompanyInfo.setStatusMsg(str2);
      FFSDebug.log(str1, str2, 0);
      return false;
    }
    if ((paramExtTransferCompanyInfo.getCompACHId() == null) || (paramExtTransferCompanyInfo.getCompACHId().trim().length() == 0))
    {
      paramExtTransferCompanyInfo.setStatusCode(16010);
      str2 = "Company ACH Id  contains null ";
      paramExtTransferCompanyInfo.setStatusMsg(str2);
      FFSDebug.log(str1, str2, 0);
      return false;
    }
    if ((paramExtTransferCompanyInfo.getSubmittedBy() == null) || (paramExtTransferCompanyInfo.getSubmittedBy().trim().length() == 0))
    {
      paramExtTransferCompanyInfo.setStatusCode(16010);
      str2 = "SubmittedBy  contains null ";
      paramExtTransferCompanyInfo.setStatusMsg(str2);
      FFSDebug.log(str1, str2, 0);
      return false;
    }
    if ((paramExtTransferCompanyInfo.getLogId() == null) || (paramExtTransferCompanyInfo.getLogId().trim().length() == 0))
    {
      paramExtTransferCompanyInfo.setStatusCode(16010);
      str2 = "LogId  contains null ";
      paramExtTransferCompanyInfo.setStatusMsg(str2);
      FFSDebug.log(str1, str2, 0);
      return false;
    }
    FFSDebug.log(str1, ": done", 6);
    return true;
  }
  
  public static ExtTransferCompanyInfo cancel(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws FFSException
  {
    String str1 = "ExternalTransferCompany.delete: ";
    FFSDebug.log("***", str1, "start ...", 6);
    if (paramExtTransferCompanyInfo == null)
    {
      str2 = "failed: Extarnal Transfer Company info Object passed is null";
      FFSDebug.log("****", str1, str2, 0);
      paramExtTransferCompanyInfo = new ExtTransferCompanyInfo();
      paramExtTransferCompanyInfo.setStatusCode(16000);
      paramExtTransferCompanyInfo.setStatusMsg("ExtTransferCompanyInfo  is null");
      return paramExtTransferCompanyInfo;
    }
    if (paramExtTransferCompanyInfo.getCompId() == null)
    {
      str2 = "***ExternalTransferCompany.delete failed: CompId is null";
      FFSDebug.log(str2, 0);
      paramExtTransferCompanyInfo.setStatusCode(16010);
      paramExtTransferCompanyInfo.setStatusMsg("CompId  contains null ");
      return paramExtTransferCompanyInfo;
    }
    String str2 = paramExtTransferCompanyInfo.getCustomerId();
    String str3 = paramExtTransferCompanyInfo.getFiId();
    Object localObject1;
    if ((str2 == null) && (str3 == null))
    {
      localObject1 = "***ExternalTransferCompany.delete failed: CustomerId and FiId are null";
      FFSDebug.log((String)localObject1, 0);
      paramExtTransferCompanyInfo.setStatusCode(16010);
      paramExtTransferCompanyInfo.setStatusMsg("CustomerId and FiId  contains null ");
      return paramExtTransferCompanyInfo;
    }
    try
    {
      localObject1 = new StringBuffer("Update ETF_Company set Status = ?  WHERE CompId = ? ");
      localObject2 = null;
      if (str2 != null)
      {
        ((StringBuffer)localObject1).append(" and CustomerId = ?");
        localObject2 = new Object[] { "CANCELEDON", paramExtTransferCompanyInfo.getCompId(), str2 };
      }
      else
      {
        ((StringBuffer)localObject1).append(" and FIId = ? and CustomerId = ?");
        localObject2 = new Object[] { "CANCELEDON", paramExtTransferCompanyInfo.getCompId(), str3, "-1" };
      }
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, ((StringBuffer)localObject1).toString(), (Object[])localObject2);
      FFSDebug.log(str1, "Number of ExtTransferCompanyInfo records closed " + i, 6);
      FFSDebug.log("***", str1, "End", 6);
      if (i == 0)
      {
        paramExtTransferCompanyInfo.setStatusCode(16020);
        paramExtTransferCompanyInfo.setStatusMsg("ExtTransferCompanyInfo  record not found");
      }
      else if (i > 1)
      {
        paramExtTransferCompanyInfo.setStatusCode(23270);
        paramExtTransferCompanyInfo.setStatusMsg("More than One Record found For CompId =" + paramExtTransferCompanyInfo.getCompId());
      }
      else
      {
        paramExtTransferCompanyInfo.setStatusCode(0);
        paramExtTransferCompanyInfo.setStatusMsg("Success");
      }
      return paramExtTransferCompanyInfo;
    }
    catch (Throwable localThrowable)
    {
      Object localObject2 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("****", str1, (String)localObject2, 0);
      throw new FFSException(localThrowable, (String)localObject2);
    }
  }
  
  public static ExtTransferCompanyInfo modify(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws FFSException
  {
    String str1 = "ExternalTransferCompany.modify: ";
    FFSDebug.log(str1, "start ...", 6);
    StringBuffer localStringBuffer = null;
    String str2 = null;
    String str3;
    if (paramExtTransferCompanyInfo == null)
    {
      str3 = "failed: Extarnal Transfer Company info Object passed is null";
      FFSDebug.log("****", str1, str3, 0);
      paramExtTransferCompanyInfo = new ExtTransferCompanyInfo();
      paramExtTransferCompanyInfo.setStatusCode(16000);
      paramExtTransferCompanyInfo.setStatusMsg("ExtTransferCompanyInfo  is null");
      return paramExtTransferCompanyInfo;
    }
    if (paramExtTransferCompanyInfo.getCompId() == null)
    {
      str3 = "failed: CompId is null";
      FFSDebug.log("**** ", str1, str3, 0);
      paramExtTransferCompanyInfo.setStatusCode(16010);
      paramExtTransferCompanyInfo.setStatusMsg("ExtTransferCompanyInfo  contains null  CompId");
      return paramExtTransferCompanyInfo;
    }
    if (!a(paramExtTransferCompanyInfo))
    {
      str3 = "failed: ";
      FFSDebug.log("***", str1, str3, paramExtTransferCompanyInfo.getStatusMsg(), 0);
      return paramExtTransferCompanyInfo;
    }
    try
    {
      if (!jdMethod_do(paramFFSConnectionHolder, paramExtTransferCompanyInfo))
      {
        str3 = "*** ExternalTransferCompany.modify failed: ";
        FFSDebug.log(str3, paramExtTransferCompanyInfo.getStatusMsg(), 0);
        return paramExtTransferCompanyInfo;
      }
      str2 = jdMethod_if(paramFFSConnectionHolder, paramExtTransferCompanyInfo);
      FFSDebug.log("******", str1, str2, 6);
      if ((str2 != null) && (!str2.equals(paramExtTransferCompanyInfo.getCompId())))
      {
        paramExtTransferCompanyInfo.setStatusCode(19700);
        paramExtTransferCompanyInfo.setStatusMsg("Duplicate record.");
        str3 = "failed: ";
        FFSDebug.log("******", str1, str3, paramExtTransferCompanyInfo.getStatusMsg(), 0);
        return paramExtTransferCompanyInfo;
      }
      str3 = paramExtTransferCompanyInfo.getCustomerId();
      str4 = paramExtTransferCompanyInfo.getFiId();
      if ((str3 == null) && (str4 == null))
      {
        localObject = "***ExternalTransferCompany.modify failed: CustomerId and FiId are null";
        FFSDebug.log((String)localObject, 0);
        paramExtTransferCompanyInfo.setStatusCode(16010);
        paramExtTransferCompanyInfo.setStatusMsg("CustomerId and FiId  contains null ");
        return paramExtTransferCompanyInfo;
      }
      Object localObject = null;
      localStringBuffer = new StringBuffer("Update ETF_Company set CompName = ?, CompACHId = ?, CompDescription = ?  WHERE CompId = ? ");
      if (str3 != null)
      {
        localStringBuffer.append(" and CustomerId = ?");
        localObject = new Object[] { paramExtTransferCompanyInfo.getCompName(), paramExtTransferCompanyInfo.getCompACHId(), paramExtTransferCompanyInfo.getCompDescription(), paramExtTransferCompanyInfo.getCompId(), str3 };
      }
      else
      {
        localStringBuffer.append(" and FIId = ? and CustomerId = ?");
        localObject = new Object[] { paramExtTransferCompanyInfo.getCompName(), paramExtTransferCompanyInfo.getCompACHId(), paramExtTransferCompanyInfo.getCompDescription(), paramExtTransferCompanyInfo.getCompId(), str4, "-1" };
      }
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject);
      FFSDebug.log(str1, "Number of ExtTransferCompanyInfo records closed " + i, 6);
      if (i == 0)
      {
        paramExtTransferCompanyInfo.setStatusCode(16020);
        paramExtTransferCompanyInfo.setStatusMsg("ExtTransferCompanyInfo  record not found");
      }
      else
      {
        paramExtTransferCompanyInfo.setStatusCode(0);
        paramExtTransferCompanyInfo.setStatusMsg("Success");
      }
      FFSDebug.log(str1, "End", 6);
      return paramExtTransferCompanyInfo;
    }
    catch (Throwable localThrowable)
    {
      String str4 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, str4, 0);
      throw new FFSException(localThrowable, str4);
    }
  }
  
  private static boolean jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws FFSException
  {
    String str1 = null;
    String str2 = null;
    CustomerInfo localCustomerInfo = null;
    BPWFIInfo localBPWFIInfo = null;
    if (paramExtTransferCompanyInfo == null) {
      return false;
    }
    str1 = paramExtTransferCompanyInfo.getCustomerId();
    if ((str1 != null) && (str1.trim().length() == 0)) {
      str1 = null;
    }
    str2 = paramExtTransferCompanyInfo.getFiId();
    if ((str2 != null) && (str2.trim().length() == 0)) {
      str2 = null;
    }
    if ((str1 == null) && (str2 == null))
    {
      paramExtTransferCompanyInfo.setStatusCode(16010);
      paramExtTransferCompanyInfo.setStatusMsg("Customer Id and FI Id  contains null ");
      return false;
    }
    try
    {
      if (str1 != null)
      {
        if (isBPWManagedCustomer())
        {
          localCustomerInfo = Customer.getCustomerInfo(str1, paramFFSConnectionHolder, paramExtTransferCompanyInfo);
          String str3;
          if ((localCustomerInfo == null) || (localCustomerInfo.getCustomerID() == null))
          {
            paramExtTransferCompanyInfo.setStatusCode(19130);
            str3 = BPWLocaleUtil.getMessage(19130, new String[] { str1 }, "TRANSFER_MESSAGE");
            paramExtTransferCompanyInfo.setStatusMsg(str3);
            return false;
          }
          if ((str2 != null) && (!str2.equals(localCustomerInfo.fIID)))
          {
            paramExtTransferCompanyInfo.setStatusCode(19130);
            str3 = BPWLocaleUtil.getMessage(19130, new String[] { localCustomerInfo.fIID }, "TRANSFER_MESSAGE");
            paramExtTransferCompanyInfo.setStatusMsg(str3);
            return false;
          }
          paramExtTransferCompanyInfo.setFiId(localCustomerInfo.fIID);
        }
      }
      else
      {
        localBPWFIInfo = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, str2);
        if (localBPWFIInfo.getStatusCode() != 0)
        {
          paramExtTransferCompanyInfo.setStatusCode(localBPWFIInfo.getStatusCode());
          paramExtTransferCompanyInfo.setStatusMsg(localBPWFIInfo.getStatusMsg());
          return false;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      String str4 = "***ExternalTransferCompany.validateCustomer failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, 0);
      throw new FFSException(localThrowable, str4);
    }
    return true;
  }
  
  public static String[] getETFCompanyIdsByCustomer(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "ExternalTransferCompany.getETFCompanyByCustomer: ";
    FFSDebug.log(str1, "start ...", 6);
    FFSResultSet localFFSResultSet = null;
    String[] arrayOfString1 = null;
    ArrayList localArrayList = null;
    if (paramString == null)
    {
      str2 = "failed: Null custId passed";
      FFSDebug.log("******", str1, str2, 0);
      return arrayOfString1;
    }
    String str2 = "SELECT a.CompId, a.FIId, a.CustomerId, a.CompName, a.CompACHId, a.CompDescription, a.SubmittedBy, a.CreateDate, a.LogId, a.Status FROM ETF_Company a  WHERE a.CustomerId = ?  AND a.Status <> 'CANCELEDON' ";
    Object[] arrayOfObject = { paramString };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      localArrayList = new ArrayList();
      while (localFFSResultSet.getNextRow()) {
        localArrayList.add(localFFSResultSet.getColumnString(1));
      }
      if (localArrayList.size() > 0) {
        arrayOfString1 = (String[])localArrayList.toArray(new String[0]);
      }
      FFSDebug.log("***", str1, "End", 6);
      String[] arrayOfString2 = arrayOfString1;
      return arrayOfString2;
    }
    catch (Throwable localThrowable)
    {
      String str3 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("****", str1, str3, 0);
      throw new FFSException(localThrowable, str3);
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
          FFSDebug.log("****", str1, "failed to close ResultSet", localException.toString(), 0);
        }
      }
    }
  }
  
  public static String[] getCustomerIdList(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "ExternalTransferCompany.getCustomerIdList: ";
    FFSDebug.log(str1, "start, FIID =" + paramString, 6);
    if (paramString == null)
    {
      localObject1 = "failed: Null fIId passed";
      FFSDebug.log("******", str1, (String)localObject1, 0);
      return null;
    }
    Object localObject1 = null;
    String[] arrayOfString = null;
    ArrayList localArrayList = new ArrayList();
    String str2 = "SELECT Distinct comp.CustomerId from ETF_Company comp, BPW_Customer cust  WHERE comp.CustomerId = cust.CustomerID AND cust.FIID = ?  AND comp.Status != 'CANCELEDON'";
    Object[] arrayOfObject = { paramString };
    try
    {
      localObject1 = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (((FFSResultSet)localObject1).getNextRow()) {
        localArrayList.add(((FFSResultSet)localObject1).getColumnString("CustomerId"));
      }
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str3 = "*** " + str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException1, str3);
    }
    finally
    {
      if (localObject1 != null) {
        try
        {
          ((FFSResultSet)localObject1).close();
          localObject1 = null;
        }
        catch (Exception localException2)
        {
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    if (localArrayList.size() > 0) {
      arrayOfString = (String[])localArrayList.toArray(new String[0]);
    }
    FFSDebug.log(str1, "End", 6);
    return arrayOfString;
  }
  
  public static ExtTransferCompanyInfo[] getExtTransferCompArrayByFIId(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt)
    throws FFSException
  {
    String str1 = "ExternalTransferCompany.getCustomerIdArrayByFIId :";
    String str2 = "fIId =" + paramString1 + ", startCompId = " + paramString2 + ", size = " + paramInt;
    FFSDebug.log(str1 + "start " + str2, 6);
    if ((paramString1 == null) || (paramString1.trim().length() == 0))
    {
      FFSDebug.log(str1 + "end, Error - FIID is null", 0);
      return null;
    }
    StringBuffer localStringBuffer = new StringBuffer("SELECT a.CompId, a.FIId, a.CustomerId, a.CompName, a.CompACHId, a.CompDescription, a.SubmittedBy, a.CreateDate, a.LogId, a.Status FROM ETF_Company a , BPW_Customer cust  Where a.Status != 'CANCELEDON' AND a.CustomerId = cust.CustomerID ");
    ArrayList localArrayList1 = new ArrayList();
    DBUtil.appendToCondition(localStringBuffer, paramString1, " AND cust.FIID = ?", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramString2, " AND a.CompId > ?", localArrayList1);
    localStringBuffer.append(" ORDER BY a.CompId ASC");
    String str3 = localStringBuffer.toString();
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = (Object[])localArrayList1.toArray(new Object[0]);
    ExtTransferCompanyInfo localExtTransferCompanyInfo = null;
    ArrayList localArrayList2 = new ArrayList();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      while ((localFFSResultSet.getNextRow()) && (localArrayList2.size() < paramInt))
      {
        localExtTransferCompanyInfo = new ExtTransferCompanyInfo();
        a(localFFSResultSet, localExtTransferCompanyInfo);
        localExtTransferCompanyInfo.setStatusCode(0);
        localExtTransferCompanyInfo.setStatusMsg("Success");
        localArrayList2.add(localExtTransferCompanyInfo);
      }
    }
    catch (FFSException localFFSException)
    {
      str4 = "*** " + str1 + " failed: ";
      str5 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str4, str5, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, str5, 0);
      throw new FFSException(localException1, str4);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException2)
        {
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "end, count =" + localArrayList2.size(), 6);
    return (ExtTransferCompanyInfo[])localArrayList2.toArray(new ExtTransferCompanyInfo[0]);
  }
  
  public static ExtTransferCompanyInfo[] getETFCompanyByFIIDAndCustomerId(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str = "ExternalTransferCompany.getETFCompanyByFIIDAndCustomerId: ";
    FFSDebug.log(str + "Start. FIID=" + paramString1 + ", CustomerId=" + paramString2, 6);
    if ("FIID" == null) {
      return null;
    }
    if ((paramString2 == null) || (paramString2.length() == 0))
    {
      localExtTransferCompanyList = new ExtTransferCompanyList();
      localExtTransferCompanyList.setFIId(paramString1);
      localExtTransferCompanyList = getExternalTransferCompanyList(paramFFSConnectionHolder, localExtTransferCompanyList);
      return localExtTransferCompanyList.getExtTransferCompanys();
    }
    ExtTransferCompanyList localExtTransferCompanyList = new ExtTransferCompanyList();
    String[] arrayOfString = new String[1];
    arrayOfString[0] = paramString2;
    localExtTransferCompanyList.setCustomerId(arrayOfString);
    localExtTransferCompanyList = getExternalTransferCompanyList(paramFFSConnectionHolder, localExtTransferCompanyList);
    return localExtTransferCompanyList.getExtTransferCompanys();
  }
  
  public static ExtTransferCompanyInfo[] getExternalTransferCompany(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "ExternalTransferCompany.getExternalTransferCompany: ";
    FFSDebug.log(str1, "start ...", 6);
    FFSResultSet localFFSResultSet = null;
    ExtTransferCompanyInfo localExtTransferCompanyInfo = null;
    ExtTransferCompanyInfo[] arrayOfExtTransferCompanyInfo1 = null;
    ArrayList localArrayList = null;
    if (paramString1 == null)
    {
      str2 = "failed: Null custId passed";
      FFSDebug.log("******", str1, str2, 0);
      return null;
    }
    if (paramString2 == null)
    {
      str2 = "failed: Null fIId passed";
      FFSDebug.log("******", str1, str2, 0);
      return null;
    }
    String str2 = "SELECT a.CompId, a.FIId, a.CustomerId, a.CompName, a.CompACHId, a.CompDescription, a.SubmittedBy, a.CreateDate, a.LogId, a.Status FROM ETF_Company a  WHERE a.CustomerId = ?  OR (a.FIId = ? AND a.CustomerId = '-1') AND a.Status <> 'CANCELEDON'  ORDER BY a.CustomerId DESC, a.FIId DESC";
    Object[] arrayOfObject = { paramString1, paramString2 };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      localArrayList = new ArrayList();
      while (localFFSResultSet.getNextRow())
      {
        localExtTransferCompanyInfo = new ExtTransferCompanyInfo();
        a(localFFSResultSet, localExtTransferCompanyInfo);
        localExtTransferCompanyInfo.setStatusCode(0);
        localExtTransferCompanyInfo.setStatusMsg("Success");
        localArrayList.add(localExtTransferCompanyInfo);
      }
      if (localArrayList.size() > 0) {
        arrayOfExtTransferCompanyInfo1 = (ExtTransferCompanyInfo[])localArrayList.toArray(new ExtTransferCompanyInfo[0]);
      }
      FFSDebug.log("***", str1, "End", 6);
      ExtTransferCompanyInfo[] arrayOfExtTransferCompanyInfo2 = arrayOfExtTransferCompanyInfo1;
      return arrayOfExtTransferCompanyInfo2;
    }
    catch (Throwable localThrowable)
    {
      String str3 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("****", str1, str3, 0);
      throw new FFSException(localThrowable, str3);
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
          FFSDebug.log("****", str1, "failed to close ResultSet", localException.toString(), 0);
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.ExternalTransferCompany
 * JD-Core Version:    0.7.0.1
 */