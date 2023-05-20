package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompanyList;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.ACHSameDayEffDateInfo;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

public class ACHSameDayEffDate
  implements FFSConst, DBConsts, SQLConsts, ACHConsts
{
  private static final String j8 = ";";
  
  public static ACHSameDayEffDateInfo setACHSameDayEffDateInfo(FFSConnectionHolder paramFFSConnectionHolder, ACHSameDayEffDateInfo paramACHSameDayEffDateInfo)
    throws FFSException
  {
    String str = "ACHSameDayEffDate.setACHSameDayEffDateInfo: ";
    FFSDebug.log(str + "start ...", 6);
    if (!a(paramACHSameDayEffDateInfo))
    {
      FFSDebug.log(str + "failed, status = " + paramACHSameDayEffDateInfo.getStatusMsg(), 6);
      return paramACHSameDayEffDateInfo;
    }
    int i = paramACHSameDayEffDateInfo.getObjectType();
    if (i == 0)
    {
      if (!validateCustomerSameDayEffDate(paramFFSConnectionHolder, paramACHSameDayEffDateInfo))
      {
        FFSDebug.log(str + "failed, status = " + paramACHSameDayEffDateInfo.getStatusMsg(), 6);
        return paramACHSameDayEffDateInfo;
      }
    }
    else if (!validateCompanySameDayEffDate(paramFFSConnectionHolder, paramACHSameDayEffDateInfo))
    {
      FFSDebug.log(str + "failed, status = " + paramACHSameDayEffDateInfo.getStatusMsg(), 6);
      return paramACHSameDayEffDateInfo;
    }
    a(paramFFSConnectionHolder, paramACHSameDayEffDateInfo);
    if (paramACHSameDayEffDateInfo.getStatusCode() != 0)
    {
      FFSDebug.log(str + "failed, status = " + paramACHSameDayEffDateInfo.getStatusMsg(), 6);
      return paramACHSameDayEffDateInfo;
    }
    if (i == 0) {
      jdMethod_if(paramFFSConnectionHolder, paramACHSameDayEffDateInfo);
    }
    paramACHSameDayEffDateInfo.setStatusCode(0);
    paramACHSameDayEffDateInfo.setStatusMsg("Success");
    FFSDebug.log(str + "end", 6);
    return paramACHSameDayEffDateInfo;
  }
  
  public static boolean validateCustomerSameDayEffDate(FFSConnectionHolder paramFFSConnectionHolder, ACHSameDayEffDateInfo paramACHSameDayEffDateInfo)
    throws FFSException
  {
    HashSet localHashSet = paramACHSameDayEffDateInfo.getSECs();
    if ((localHashSet != null) && (!localHashSet.isEmpty()))
    {
      CustomerInfo localCustomerInfo = Customer.getCustomerInfo(paramACHSameDayEffDateInfo.getObjectId(), paramFFSConnectionHolder, paramACHSameDayEffDateInfo);
      if (localCustomerInfo == null)
      {
        paramACHSameDayEffDateInfo.setStatusCode(19130);
        localObject = BPWLocaleUtil.getMessage(19130, new String[] { paramACHSameDayEffDateInfo.getObjectId() }, "TRANSFER_MESSAGE");
        paramACHSameDayEffDateInfo.setStatusMsg((String)localObject);
        return false;
      }
      Object localObject = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, localCustomerInfo.fIID);
      if (((BPWFIInfo)localObject).getStatusCode() != 0)
      {
        paramACHSameDayEffDateInfo.setStatusCode(23230);
        paramACHSameDayEffDateInfo.setStatusMsg("BPWFIID does not exist : with FIId = " + localCustomerInfo.fIID);
        return false;
      }
      if ((((BPWFIInfo)localObject).getACHSameDayEffDate() == null) || (((BPWFIInfo)localObject).getACHSameDayEffDate().compareTo("Y") != 0))
      {
        paramACHSameDayEffDateInfo.setStatusCode(25002);
        paramACHSameDayEffDateInfo.setStatusMsg("ACH SameDayEffectiveDate of Customer must be disabled");
        return false;
      }
    }
    return true;
  }
  
  public static boolean validateCompanySameDayEffDate(FFSConnectionHolder paramFFSConnectionHolder, ACHSameDayEffDateInfo paramACHSameDayEffDateInfo)
    throws FFSException
  {
    HashSet localHashSet1 = paramACHSameDayEffDateInfo.getSECs();
    if ((localHashSet1 != null) && (!localHashSet1.isEmpty()))
    {
      ACHCompanyInfo localACHCompanyInfo = ACHCompany.getCompanyInfo(paramACHSameDayEffDateInfo.getObjectId(), paramFFSConnectionHolder);
      if (localACHCompanyInfo.getStatusCode() != 0)
      {
        paramACHSameDayEffDateInfo.setStatusCode(localACHCompanyInfo.getStatusCode());
        paramACHSameDayEffDateInfo.setStatusMsg(localACHCompanyInfo.getStatusMsg());
        return false;
      }
      String str = localACHCompanyInfo.getCustomerId();
      ACHSameDayEffDateInfo localACHSameDayEffDateInfo = new ACHSameDayEffDateInfo();
      localACHSameDayEffDateInfo.setObjectId(str);
      localACHSameDayEffDateInfo.setObjectType(0);
      localACHSameDayEffDateInfo = getACHSameDayEffDateInfo(paramFFSConnectionHolder, localACHSameDayEffDateInfo);
      HashSet localHashSet2 = localACHSameDayEffDateInfo.getSECs();
      if ((localHashSet2 == null) || (localHashSet2.size() == 0))
      {
        paramACHSameDayEffDateInfo.setStatusCode(25003);
        paramACHSameDayEffDateInfo.setStatusMsg("ACH SameDayEffectiveDate of Company must be disabled");
        return false;
      }
      if (!localHashSet2.containsAll(localHashSet1))
      {
        paramACHSameDayEffDateInfo.setStatusCode(25004);
        paramACHSameDayEffDateInfo.setStatusMsg("ACH SameDayEffectDate of Company is invalid");
        return false;
      }
    }
    return true;
  }
  
  public static ACHSameDayEffDateInfo getACHSameDayEffDateInfo(FFSConnectionHolder paramFFSConnectionHolder, ACHSameDayEffDateInfo paramACHSameDayEffDateInfo)
    throws FFSException
  {
    String str1 = "ACHSameDayEffDate.getACHSameDayEffDateInfo: ";
    FFSDebug.log(str1 + "start ...", 6);
    if (!a(paramACHSameDayEffDateInfo))
    {
      FFSDebug.log(str1 + "failed, status =" + paramACHSameDayEffDateInfo.getStatusMsg(), 6);
      return paramACHSameDayEffDateInfo;
    }
    HashSet localHashSet = null;
    String str2 = "SELECT SECs from ACH_SameDayEffDate  WHERE ObjectId = ? AND ObjectType = ? ";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramACHSameDayEffDateInfo.getObjectId(), new Integer(paramACHSameDayEffDateInfo.getObjectType()) };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      String str3 = null;
      if (localFFSResultSet.getNextRow())
      {
        str3 = localFFSResultSet.getColumnString("SECs");
        paramACHSameDayEffDateInfo.setStatusCode(0);
        paramACHSameDayEffDateInfo.setStatusMsg("Success");
      }
      else
      {
        paramACHSameDayEffDateInfo.setStatusCode(16020);
        paramACHSameDayEffDateInfo.setStatusMsg("ACHSameDayEffDateInfo with ObjectId =" + paramACHSameDayEffDateInfo.getObjectId() + ", and ObjectType =" + paramACHSameDayEffDateInfo.getObjectType() + " record not found");
      }
      if (str3 != null) {
        localHashSet = jdMethod_try(str3, ";");
      }
      paramACHSameDayEffDateInfo.setSECs(localHashSet);
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
    FFSDebug.log(str1 + "end, status =" + paramACHSameDayEffDateInfo.getStatusMsg(), 6);
    return paramACHSameDayEffDateInfo;
  }
  
  public static boolean isSameDayEffDate(FFSConnectionHolder paramFFSConnectionHolder, int paramInt, String paramString1, String paramString2)
    throws FFSException
  {
    String str = "ACHSameDayEffDate.isSameDayEffDate: ";
    FFSDebug.log(str + "start ...", 6);
    ACHSameDayEffDateInfo localACHSameDayEffDateInfo = new ACHSameDayEffDateInfo();
    localACHSameDayEffDateInfo.setObjectId(paramString1);
    localACHSameDayEffDateInfo.setObjectType(paramInt);
    if (!a(localACHSameDayEffDateInfo))
    {
      FFSDebug.log(str + "end, Some error occured: " + localACHSameDayEffDateInfo.getStatusMsg(), 6);
      return false;
    }
    if (paramString2 == null)
    {
      FFSDebug.log(str + "end, Some error occured: input SEC is null", 6);
      return false;
    }
    localACHSameDayEffDateInfo = getACHSameDayEffDateInfo(paramFFSConnectionHolder, localACHSameDayEffDateInfo);
    if (localACHSameDayEffDateInfo.getStatusCode() != 0)
    {
      FFSDebug.log(str + "end, Some error occured: " + localACHSameDayEffDateInfo.getStatusMsg(), 6);
      return false;
    }
    HashSet localHashSet = localACHSameDayEffDateInfo.getSECs();
    if (localHashSet == null)
    {
      FFSDebug.log(str + "end, Some error occured: " + "ACHSameDayEffDateInfo.SEC is null", 6);
      return false;
    }
    boolean bool = localHashSet.contains(paramString2);
    FFSDebug.log(str + "end isSameDayEffDate =" + bool, 6);
    return bool;
  }
  
  private static boolean a(ACHSameDayEffDateInfo paramACHSameDayEffDateInfo)
  {
    if (paramACHSameDayEffDateInfo.getObjectId() == null)
    {
      paramACHSameDayEffDateInfo.setStatusCode(25000);
      paramACHSameDayEffDateInfo.setStatusMsg("ACH SameDayEffectiveDate.ObjectId must not be null");
      return false;
    }
    int i = paramACHSameDayEffDateInfo.getObjectType();
    if ((i != 0) && (i != 1))
    {
      paramACHSameDayEffDateInfo.setStatusCode(25001);
      paramACHSameDayEffDateInfo.setStatusMsg("Invalid ObjectType for ACH SameDayEffectiveDate. Valid values are: 0, 1. We got: " + i);
      return false;
    }
    paramACHSameDayEffDateInfo.setStatusCode(0);
    paramACHSameDayEffDateInfo.setStatusMsg("Success");
    return true;
  }
  
  private static HashSet jdMethod_try(String paramString1, String paramString2)
  {
    HashSet localHashSet = new HashSet();
    if ((paramString1 == null) || (paramString2 == null)) {
      return localHashSet;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
    while (localStringTokenizer.hasMoreTokens()) {
      localHashSet.add(localStringTokenizer.nextToken());
    }
    return localHashSet;
  }
  
  private static String a(HashSet paramHashSet, String paramString)
  {
    if ((paramHashSet == null) || (paramHashSet.isEmpty() == true) || (paramString == null)) {
      return null;
    }
    Iterator localIterator = paramHashSet.iterator();
    StringBuffer localStringBuffer = new StringBuffer();
    while (localIterator.hasNext()) {
      localStringBuffer.append(localIterator.next()).append(paramString);
    }
    String str = localStringBuffer.toString();
    int i = str.length();
    if (i == 0) {
      return str;
    }
    str = str.substring(0, i - paramString.length());
    return str;
  }
  
  public static int deleteACHSameDayEffDate(FFSConnectionHolder paramFFSConnectionHolder, ACHSameDayEffDateInfo paramACHSameDayEffDateInfo)
    throws FFSException
  {
    String str1 = "ACHSameDayEffDate.deleteACHSameDayEffDate: ";
    FFSDebug.log(str1 + "start, ObjectId =" + paramACHSameDayEffDateInfo.getObjectId() + ", ObjectType =" + paramACHSameDayEffDateInfo.getObjectType(), 6);
    String str2 = "DELETE from ACH_SameDayEffDate  WHERE ObjectId = ? AND ObjectType = ? ";
    Object[] arrayOfObject = { paramACHSameDayEffDateInfo.getObjectId(), new Integer(paramACHSameDayEffDateInfo.getObjectType()) };
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException, str3);
    }
    if (i == 0)
    {
      paramACHSameDayEffDateInfo.setStatusCode(16020);
      paramACHSameDayEffDateInfo.setStatusMsg("ACHSameDayEffDateInfo with ObjectId =" + paramACHSameDayEffDateInfo.getObjectId() + ", and ObjectType =" + paramACHSameDayEffDateInfo.getObjectType() + " record not found");
    }
    else
    {
      paramACHSameDayEffDateInfo.setStatusCode(0);
      paramACHSameDayEffDateInfo.setStatusMsg("Success");
    }
    FFSDebug.log(str1 + "end, no of rows deleted =" + i, 6);
    return i;
  }
  
  private static int a(FFSConnectionHolder paramFFSConnectionHolder, ACHSameDayEffDateInfo paramACHSameDayEffDateInfo)
    throws FFSException
  {
    String str1 = "ACHSameDayEffDate.updateSameDayEffDate: ";
    FFSDebug.log(str1 + "start ...", 6);
    String str2 = a(paramACHSameDayEffDateInfo.getSECs(), ";");
    int i = 0;
    ACHSameDayEffDateInfo localACHSameDayEffDateInfo = new ACHSameDayEffDateInfo();
    localACHSameDayEffDateInfo.setObjectId(paramACHSameDayEffDateInfo.getObjectId());
    localACHSameDayEffDateInfo.setObjectType(paramACHSameDayEffDateInfo.getObjectType());
    localACHSameDayEffDateInfo = getACHSameDayEffDateInfo(paramFFSConnectionHolder, localACHSameDayEffDateInfo);
    if (localACHSameDayEffDateInfo.getStatusCode() == 0) {
      i = 1;
    }
    localACHSameDayEffDateInfo = null;
    String str3 = null;
    Object[] arrayOfObject = null;
    if (i == 1)
    {
      str3 = "UPDATE ACH_SameDayEffDate Set SECs = ?  WHERE ObjectId = ? AND ObjectType = ? ";
      arrayOfObject = new Object[3];
      arrayOfObject[0] = str2;
      arrayOfObject[1] = paramACHSameDayEffDateInfo.getObjectId();
      arrayOfObject[2] = new Integer(paramACHSameDayEffDateInfo.getObjectType());
    }
    else
    {
      str3 = "INSERT into ACH_SameDayEffDate (ObjectId, ObjectType, SECs) values (?, ?, ? )";
      arrayOfObject = new Object[3];
      arrayOfObject[0] = paramACHSameDayEffDateInfo.getObjectId();
      arrayOfObject[1] = new Integer(paramACHSameDayEffDateInfo.getObjectType());
      arrayOfObject[2] = str2;
    }
    int j = 0;
    try
    {
      j = DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      str4 = "*** " + str1 + " failed: ";
      str5 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str4, str5, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str4, str5, 0);
      throw new FFSException(localException, str4);
    }
    if (j == 0)
    {
      paramACHSameDayEffDateInfo.setStatusCode(16020);
      paramACHSameDayEffDateInfo.setStatusMsg("ACHSameDayEffDateInfo with ObjectId =" + paramACHSameDayEffDateInfo.getObjectId() + ", and ObjectType =" + paramACHSameDayEffDateInfo.getObjectType() + " record not found");
    }
    else
    {
      paramACHSameDayEffDateInfo.setStatusCode(0);
      paramACHSameDayEffDateInfo.setStatusMsg("Success");
    }
    FFSDebug.log(str1 + "end, no of rows updated/inserted =" + j, 6);
    return j;
  }
  
  private static void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, ACHSameDayEffDateInfo paramACHSameDayEffDateInfo)
    throws FFSException
  {
    HashSet localHashSet1 = paramACHSameDayEffDateInfo.getSECs();
    ACHCompanyList localACHCompanyList = new ACHCompanyList();
    localACHCompanyList.setCustomerId(paramACHSameDayEffDateInfo.getObjectId());
    localACHCompanyList = ACHCompany.getACHCompanyList(localACHCompanyList, paramFFSConnectionHolder);
    if (localACHCompanyList.getStatusCode() == 0)
    {
      ACHCompanyInfo[] arrayOfACHCompanyInfo = localACHCompanyList.getACHCompanyInfoList();
      if (arrayOfACHCompanyInfo != null) {
        for (int i = 0; i < arrayOfACHCompanyInfo.length; i++)
        {
          ACHSameDayEffDateInfo localACHSameDayEffDateInfo = new ACHSameDayEffDateInfo();
          localACHSameDayEffDateInfo.setObjectId(arrayOfACHCompanyInfo[i].getCompId());
          localACHSameDayEffDateInfo.setObjectType(1);
          localACHSameDayEffDateInfo = getACHSameDayEffDateInfo(paramFFSConnectionHolder, localACHSameDayEffDateInfo);
          HashSet localHashSet2 = localACHSameDayEffDateInfo.getSECs();
          if ((localHashSet2 != null) && (!localHashSet2.isEmpty()))
          {
            boolean bool = false;
            if ((localHashSet1 == null) || (localHashSet1.isEmpty()))
            {
              localACHSameDayEffDateInfo.setSECs(null);
              bool = true;
            }
            else
            {
              bool = localHashSet2.retainAll(localHashSet1);
            }
            if (bool == true) {
              a(paramFFSConnectionHolder, localACHSameDayEffDateInfo);
            }
          }
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.ACHSameDayEffDate
 * JD-Core Version:    0.7.0.1
 */