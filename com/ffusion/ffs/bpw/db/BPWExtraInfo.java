package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.ValueInfo;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class BPWExtraInfo
{
  public static int addXtraInfoVal(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws FFSException
  {
    FFSDebug.log("BPWExtraInfo.addXtraInfoVal: Start", 6);
    FFSDebug.log("BPWExtraInfo.addXtraInfoVal: fIId=", paramString1, " recordId=", paramString2, " recordType=", paramString3, " name=", paramString4, " Value=", paramString5, 6);
    if (paramFFSConnectionHolder == null)
    {
      FFSDebug.log("BPWExtraInfo.addXtraInfoVal: failed. Null connection holder passed for Name=" + paramString4 + ", Value=" + paramString5 + ", RecordID=" + paramString2, 0);
      return 16000;
    }
    if ((paramString1 == null) || (paramString1.trim().length() == 0))
    {
      FFSDebug.log("BPWExtraInfo.addXtraInfoVal: failed. FIID is missing for Name=" + paramString4 + ", Value=" + paramString5 + ", RecordID=" + paramString2, 0);
      return 16000;
    }
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      FFSDebug.log("BPWExtraInfo.addXtraInfoVal: failed. RecordId is missing for Name=" + paramString4 + ", Value=" + paramString5 + ", RecordType=" + paramString3, 0);
      return 16000;
    }
    if ((paramString3 == null) || (paramString3.trim().length() == 0))
    {
      FFSDebug.log("BPWExtraInfo.addXtraInfoVal: failed. RecordType is missing for Name=" + paramString4 + ", Value=" + paramString5 + ", RecordID=" + paramString2, 0);
      return 16000;
    }
    if ((paramString4 == null) || (paramString4.trim().length() == 0))
    {
      FFSDebug.log("BPWExtraInfo.addXtraInfoVal: failed. Xtra info Name is missing for Value=" + paramString5 + ", RecordID=" + paramString2, 0);
      return 16000;
    }
    if ((paramString5 == null) || (paramString5.trim().length() == 0))
    {
      FFSDebug.log("BPWExtraInfo.addXtraInfoVal: failed. Xtra info Value is missing for Name=" + paramString4 + ", RecordID=" + paramString2, 0);
      return 16000;
    }
    try
    {
      String str1 = "INSERT INTO BPW_ExtraInfo (FIId,RecordId,RecordType,Name,Value) VALUES (?,?,?,?,?)";
      localObject = new Object[] { paramString1, paramString2, paramString3, paramString4, paramString5 };
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, (Object[])localObject);
    }
    catch (Exception localException)
    {
      Object localObject = "BPWExtraInfo.addXtraInfoVal: failed  for Name=" + paramString4 + ", Value=" + paramString5 + ", RecordID=" + paramString2;
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject, str2, 0);
      throw new FFSException(localException, (String)localObject);
    }
    FFSDebug.log("BPWExtraInfo.addXtraInfoVal: End", 6);
    return 0;
  }
  
  public static int modXtraInfoVal(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws FFSException
  {
    FFSDebug.log("BPWExtraInfo.modXtraInfoVal: Start", 6);
    FFSDebug.log("BPWExtraInfo.modXtraInfoVal: fIId=", paramString1, " recordId=", paramString2, " recordType=", paramString3, " name=", paramString4, " Value=", paramString5, 6);
    if (paramFFSConnectionHolder == null)
    {
      FFSDebug.log("BPWExtraInfo.modXtraInfoVal: failed. Null connection holder passed", 0);
      return 16000;
    }
    if ((paramString1 == null) || (paramString1.trim().length() == 0))
    {
      FFSDebug.log("BPWExtraInfo.modXtraInfoVal: failed. FIID is missing", 0);
      return 16000;
    }
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      FFSDebug.log("BPWExtraInfo.modXtraInfoVal: failed. RecordId is missing", 0);
      return 16000;
    }
    if ((paramString3 == null) || (paramString3.trim().length() == 0))
    {
      FFSDebug.log("BPWExtraInfo.modXtraInfoVal: failed. RecordType is missing", 0);
      return 16000;
    }
    if ((paramString4 == null) || (paramString4.trim().length() == 0))
    {
      FFSDebug.log("BPWExtraInfo.modXtraInfoVal: failed. Xtra info Name is missing", 0);
      return 16000;
    }
    if ((paramString5 == null) || (paramString5.trim().length() == 0))
    {
      FFSDebug.log("BPWExtraInfo.modXtraInfoVal: failed. Xtra info Value is missing", 0);
      return 16000;
    }
    try
    {
      String str1 = "UPDATE BPW_ExtraInfo set Value = ?  WHERE FIId = ? AND RecordId = ? AND RecordType = ? AND Name = ?";
      localObject = new Object[] { paramString5, paramString1, paramString2, paramString3, paramString4 };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, (Object[])localObject);
      if (i == 0)
      {
        FFSDebug.log("BPWExtraInfo.modXtraInfoVal: failed, Key:", paramString4, " does not exist in DB", 6);
        return 19270;
      }
    }
    catch (Exception localException)
    {
      Object localObject = "BPWExtraInfo.modXtraInfoVal: failed ";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject, str2, 0);
      throw new FFSException(localException, (String)localObject);
    }
    FFSDebug.log("BPWExtraInfo.modXtraInfoVal: End", 6);
    return 0;
  }
  
  public static int delXtraInfoVal(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4)
    throws FFSException
  {
    FFSDebug.log("BPWExtraInfo.delXtraInfoVal: Start", 6);
    FFSDebug.log("BPWExtraInfo.delXtraInfoVal: fIId=", paramString1, "recordId=", paramString2, "recordType=", paramString3, "name=", paramString4, 6);
    if (paramFFSConnectionHolder == null)
    {
      FFSDebug.log("BPWExtraInfo.delXtraInfoVal: failed. Null connection holder passed", 0);
      return 16000;
    }
    if ((paramString1 == null) || (paramString1.trim().length() == 0))
    {
      FFSDebug.log("BPWExtraInfo.delXtraInfoVal: failed. FIID is missing", 0);
      return 16000;
    }
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      FFSDebug.log("BPWExtraInfo.delXtraInfoVal: failed. RecordId is missing", 0);
      return 16000;
    }
    if ((paramString3 == null) || (paramString3.trim().length() == 0))
    {
      FFSDebug.log("BPWExtraInfo.delXtraInfoVal: failed. RecordType is missing", 0);
      return 16000;
    }
    if ((paramString4 == null) || (paramString4.trim().length() == 0))
    {
      FFSDebug.log("BPWExtraInfo.delXtraInfoVal: failed. Xtra info Name is missing", 0);
      return 16000;
    }
    try
    {
      String str1 = "DELETE FROM BPW_ExtraInfo  WHERE FIId = ? AND RecordId = ? AND RecordType = ? AND Name = ?";
      localObject = new Object[] { paramString1, paramString2, paramString3, paramString4 };
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, (Object[])localObject);
    }
    catch (Exception localException)
    {
      Object localObject = "BPWExtraInfo.delXtraInfoVal: failed ";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject, str2, 0);
      throw new FFSException(localException, (String)localObject);
    }
    FFSDebug.log("BPWExtraInfo.delXtraInfoVal: End", 6);
    return 0;
  }
  
  public static int processXtraInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, Hashtable paramHashtable)
    throws FFSException
  {
    FFSDebug.log("BPWExtraInfo.processXtraInfo: Start", 6);
    if (paramFFSConnectionHolder == null)
    {
      FFSDebug.log("BPWExtraInfo.processXtraInfo: failed. Null connection holder passed", 0);
      return 16000;
    }
    if (paramHashtable == null)
    {
      FFSDebug.log("BPWExtraInfo.processXtraInfo: failed. Extra info is missing", 0);
      return 16000;
    }
    Enumeration localEnumeration = paramHashtable.keys();
    String str1 = null;
    ValueInfo localValueInfo1 = null;
    ValueInfo localValueInfo2 = null;
    String str2 = null;
    int i = 0;
    try
    {
      while (localEnumeration.hasMoreElements())
      {
        str1 = (String)localEnumeration.nextElement();
        localValueInfo1 = (ValueInfo)paramHashtable.get(str1);
        FFSDebug.log("BPWExtraInfo.processXtraInfo: Key=", str1, 6);
        if (localValueInfo1 == null)
        {
          FFSDebug.log("BPWExtraInfo.processXtraInfo: failed. value if null for ", str1, 0);
          return 16000;
        }
        str2 = localValueInfo1.getAction();
        FFSDebug.log("BPWExtraInfo.processXtraInfo: value=", localValueInfo1.toString(), 6);
        FFSDebug.log("BPWExtraInfo.processXtraInfo: action=", str2, 6);
        if (str2 == null)
        {
          FFSDebug.log("BPWExtraInfo.processXtraInfo: action is not specified for ", str1, 0);
        }
        else if (str2.equals("add"))
        {
          localValueInfo2 = getXtraInfoVal(paramFFSConnectionHolder, paramString1, paramString2, paramString3, str1);
          if (localValueInfo2 != null)
          {
            FFSDebug.log("BPWExtraInfo.processXtraInfo: failed.", "Duplicate key exists", 0);
            return 19270;
          }
          i = addXtraInfoVal(paramFFSConnectionHolder, paramString1, paramString2, paramString3, str1, localValueInfo1.toString());
          if (i != 0)
          {
            FFSDebug.log("BPWExtraInfo.processXtraInfo: failed. ", 0);
            return 19270;
          }
        }
        else if (str2.equals("mod"))
        {
          i = modXtraInfoVal(paramFFSConnectionHolder, paramString1, paramString2, paramString3, str1, localValueInfo1.toString());
          if (i != 0)
          {
            FFSDebug.log("BPWExtraInfo.processXtraInfo: failed. ", 0);
            return 19270;
          }
        }
        else if (str2.equals("del"))
        {
          i = delXtraInfoVal(paramFFSConnectionHolder, paramString1, paramString2, paramString3, str1);
          if (i != 0)
          {
            FFSDebug.log("BPWExtraInfo.processXtraInfo: failed. ", 0);
            return 19270;
          }
        }
      }
    }
    catch (Exception localException)
    {
      String str3 = "BPWExtraInfo.processXtraInfo: failed ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log("BPWExtraInfo.processXtraInfo: End", 6);
    return 0;
  }
  
  public static ValueInfo getXtraInfoVal(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4)
    throws FFSException
  {
    FFSDebug.log("BPWExtraInfo.getXtraInfoVal: Start", 6);
    FFSResultSet localFFSResultSet = null;
    String str1 = null;
    String str2 = null;
    ValueInfo localValueInfo1 = null;
    Object[] arrayOfObject = { paramString1, paramString2, paramString3, paramString4 };
    str1 = "SELECT Name, Value FROM BPW_ExtraInfo WHERE FIId = ? AND RecordId = ? AND RecordType = ? AND Name = ? ";
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        str2 = localFFSResultSet.getColumnString("Value");
        localValueInfo1 = new ValueInfo();
        localValueInfo1.setValue(str2);
        FFSDebug.log("BPWExtraInfo.getXtraInfoVal: End", 6);
        ValueInfo localValueInfo2 = localValueInfo1;
        return localValueInfo2;
      }
    }
    catch (Exception localException1)
    {
      String str3 = "BPWExtraInfo.getXtraInfoVal: failed ";
      String str4 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException1, str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        String str5 = "BPWExtraInfo.getXtraInfoVal: failed ";
        String str6 = FFSDebug.stackTrace(localException2);
        FFSDebug.log(str5, str6, 0);
      }
    }
    FFSDebug.log("BPWExtraInfo.getXtraInfoVal: End", 6);
    return null;
  }
  
  public static Hashtable getXtraInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    FFSDebug.log("BPWExtraInfo.getXtraInfo(Single RecordType): Start", 6);
    String[] arrayOfString = { paramString3 };
    FFSDebug.log("BPWExtraInfo.getXtraInfo(Single RecordType): End", 6);
    return getXtraInfo(paramFFSConnectionHolder, paramString1, paramString2, arrayOfString);
  }
  
  public static Hashtable getXtraInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("BPWExtraInfo.getXtraInfo(Multi RecordType): Start", 6);
    FFSResultSet localFFSResultSet = null;
    StringBuffer localStringBuffer = null;
    Hashtable localHashtable = null;
    String str1 = null;
    String str2 = null;
    ValueInfo localValueInfo = null;
    Object[] arrayOfObject = { paramString1, paramString2 };
    localStringBuffer = new StringBuffer();
    localStringBuffer.append("SELECT Name, Value FROM BPW_ExtraInfo WHERE FIId = ? AND RecordId = ? ");
    if (paramArrayOfString.length > 1)
    {
      localStringBuffer.append(" AND RecordType IN (");
      localStringBuffer.append("'");
      localStringBuffer.append(paramArrayOfString[0]);
      localStringBuffer.append("'");
      for (int i = 1; i < paramArrayOfString.length; i++)
      {
        localStringBuffer.append(",'");
        localStringBuffer.append(paramArrayOfString[i]);
        localStringBuffer.append("'");
      }
      localStringBuffer.append(") ");
    }
    else if (paramArrayOfString.length == 1)
    {
      localStringBuffer.append(" AND RecordType = '");
      localStringBuffer.append(paramArrayOfString[0]);
      localStringBuffer.append("'");
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      localHashtable = new Hashtable();
      while (localFFSResultSet.getNextRow())
      {
        str1 = localFFSResultSet.getColumnString("Name");
        str2 = localFFSResultSet.getColumnString("Value");
        if ((str1 != null) && (str2 != null))
        {
          localValueInfo = new ValueInfo();
          localValueInfo.setValue(str2);
          localHashtable.put(str1, localValueInfo);
        }
      }
    }
    catch (Exception localException1)
    {
      String str3 = "BPWExtraInfo.getXtraInfoVal: failed ";
      String str4 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException1, str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        String str5 = "BPWExtraInfo.getXtraInfoVal: failed ";
        String str6 = FFSDebug.stackTrace(localException2);
        FFSDebug.log(str5, str6, 0);
      }
    }
    FFSDebug.log("BPWExtraInfo.getXtraInfo(Multi RecordType): End", 6);
    return localHashtable;
  }
  
  public static int processImplicitMod(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, Hashtable paramHashtable)
    throws FFSException
  {
    FFSDebug.log("BPWExtraInfo.processImplicitMod: Start", 6);
    if ((paramString1 == null) || (paramString1.trim().length() == 0))
    {
      FFSDebug.log("BPWExtraInfo.processImplicitMod: failed. FIID is missing", 0);
      return 16000;
    }
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      FFSDebug.log("BPWExtraInfo.processImplicitMod: failed. RecordId is missing", 0);
      return 16000;
    }
    if ((paramString3 == null) || (paramString3.trim().length() == 0))
    {
      FFSDebug.log("BPWExtraInfo.processImplicitMod: failed. RecordType is missing", 0);
      return 16000;
    }
    delXtraInfoByRecordId(paramFFSConnectionHolder, paramString2, paramString3);
    if ((paramHashtable != null) && (paramHashtable.size() > 0))
    {
      String str = null;
      ValueInfo localValueInfo = null;
      Enumeration localEnumeration = paramHashtable.keys();
      while (localEnumeration.hasMoreElements())
      {
        str = (String)localEnumeration.nextElement();
        localValueInfo = (ValueInfo)paramHashtable.get(str);
        localValueInfo.setAction("add");
        addXtraInfoVal(paramFFSConnectionHolder, paramString1, paramString2, paramString3, str, localValueInfo.toString());
      }
    }
    FFSDebug.log("BPWExtraInfo.processImplicitMod: end", 6);
    return 0;
  }
  
  public static int delXtraInfoByRecordId(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("BPWExtraInfo.delXtraInfoByRecordId: Start, recordId =" + paramString1, 6);
    if (paramFFSConnectionHolder == null)
    {
      FFSDebug.log("BPWExtraInfo.delXtraInfoByRecordId: failed. Null connection holder passed", 0);
      return 16000;
    }
    if ((paramString1 == null) || (paramString1.trim().length() == 0))
    {
      FFSDebug.log("BPWExtraInfo.delXtraInfoByRecordId: failed. recordId is missing", 0);
      return 16000;
    }
    int i = 0;
    try
    {
      String str1 = "DELETE FROM BPW_ExtraInfo  WHERE RecordId = ? And RecordType = ?";
      localObject = new Object[] { paramString1, paramString2 };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, (Object[])localObject);
    }
    catch (Exception localException)
    {
      Object localObject = "BPWExtraInfo.delXtraInfoByRecordId: failed ";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject, str2, 0);
      throw new FFSException(localException, (String)localObject);
    }
    FFSDebug.log("BPWExtraInfo.delXtraInfoByRecordId: end, noOfRowsDeleted =" + i, 6);
    return 0;
  }
  
  public static int deleteBatch(FFSConnectionHolder paramFFSConnectionHolder, String[] paramArrayOfString, String paramString)
    throws FFSException
  {
    FFSDebug.log("BPWExtraInfo.deleteBatch: Start.", 6);
    if (paramFFSConnectionHolder == null)
    {
      FFSDebug.log("BPWExtraInfo.deleteBatch: failed. Null connection holder passed", 0);
      return 0;
    }
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      FFSDebug.log("BPWExtraInfo.deleteBatch: failed. recordIds is missing", 0);
      return 0;
    }
    try
    {
      String str1 = "DELETE FROM BPW_ExtraInfo  WHERE RecordId = ? And RecordType = ?";
      localObject = new ArrayList();
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(paramArrayOfString[i]);
        localArrayList.add(paramString);
        ((ArrayList)localObject).add(localArrayList);
      }
      DBUtil.executeStatementBatch(paramFFSConnectionHolder, str1, (ArrayList)localObject);
    }
    catch (Exception localException)
    {
      Object localObject = "BPWExtraInfo.deleteBatch: failed ";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject, str2, 0);
      throw new FFSException(localException, (String)localObject);
    }
    FFSDebug.log("BPWExtraInfo.deleteBatch: end. No of rows deleted = " + paramArrayOfString.length, 6);
    return paramArrayOfString.length;
  }
  
  public static int deleteBatch(FFSConnectionHolder paramFFSConnectionHolder, String[] paramArrayOfString)
    throws BPWException
  {
    FFSDebug.log("BPWExtraInfo.deleteBatch: Start.", 6);
    if (paramFFSConnectionHolder == null)
    {
      FFSDebug.log("BPWExtraInfo.deleteBatch: failed. Null connection holder passed", 0);
      return 0;
    }
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      FFSDebug.log("BPWExtraInfo.deleteBatch: failed. recordIds is missing", 0);
      return 0;
    }
    String str1 = "DELETE FROM BPW_ExtraInfo WHERE RecordId = ?";
    try
    {
      DBUtil.executeStatementBatch(paramFFSConnectionHolder, str1, DBUtil.arrayStringToArrayList(paramArrayOfString));
    }
    catch (Exception localException)
    {
      String str2 = "*** BPWExtraInfo.deleteBatch failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("BPWExtraInfo.deleteBatch done. # of rows deleted: " + paramArrayOfString.length, 6);
    return paramArrayOfString.length;
  }
  
  public static int processXtraInfoAsString(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws FFSException
  {
    Hashtable localHashtable = null;
    Enumeration localEnumeration = null;
    String str1 = null;
    String str2 = null;
    int i = 0;
    ValueInfo localValueInfo = null;
    FFSDebug.log("BPWExtraInfo.processXtraInfoAsString: Start", 6);
    if (paramFFSConnectionHolder == null)
    {
      FFSDebug.log("BPWExtraInfo.processXtraInfoAsString: failed. Null connection holder passed", 0);
      return 16000;
    }
    if ((paramString4 == null) || (paramString4.trim().equals("")))
    {
      FFSDebug.log("BPWExtraInfo.processXtraInfoAsString: failed. Extrainfo String is missing", 0);
      return 16000;
    }
    if ((paramString1 == null) || (paramString1.trim().equals("")))
    {
      FFSDebug.log("BPWExtraInfo.processXtraInfoAsString: failed. fIId String is missing", 0);
      return 16000;
    }
    if ((paramString2 == null) || (paramString2.trim().equals("")))
    {
      FFSDebug.log("BPWExtraInfo.processXtraInfoAsString: failed. recordId String is missing", 0);
      return 16000;
    }
    if ((paramString3 == null) || (paramString3.trim().equals("")))
    {
      FFSDebug.log("BPWExtraInfo.processXtraInfoAsString: failed. recordType String is missing", 0);
      return 16000;
    }
    try
    {
      localHashtable = FFSUtil.stringToHashtable(paramString4);
      localEnumeration = localHashtable.keys();
      while (localEnumeration.hasMoreElements())
      {
        str1 = (String)localEnumeration.nextElement();
        str2 = (String)localHashtable.get(str1);
        FFSDebug.log("BPWExtraInfo.processXtraInfoAsString: Key=", str1, 6);
        FFSDebug.log("BPWExtraInfo.processXtraInfoAsString: value=", str2, 6);
        localValueInfo = new ValueInfo();
        localValueInfo.setAction(paramString5);
        localValueInfo.setValue(str2);
        localHashtable.put(str1, localValueInfo);
      }
      i = processXtraInfo(paramFFSConnectionHolder, paramString1, paramString2, paramString3, localHashtable);
      FFSDebug.log("BPWExtraInfo.processXtraInfoAsString: End", 6);
      return i;
    }
    catch (Exception localException)
    {
      String str3 = "BPWExtraInfo.processXtraInfoAsString: failed ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException, str3);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.BPWExtraInfo
 * JD-Core Version:    0.7.0.1
 */