package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.ValueInfo;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.util.FFSDebug;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

public class BPWRecXferExtraInfo
{
  public static String getValue(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("BPWRecXferExtraInfo.getValue Start : recSrvrTID = " + paramString1 + "name = " + paramString2, 6);
    String str1 = null;
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    String str3 = null;
    String str4 = null;
    if (paramString1 == null)
    {
      str1 = "BPWRecXferExtraInfo.getValue: failed. recSrvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "BPWRecXferExtraInfo.getValue: failed. Connection holder is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      str1 = "BPWRecXferExtraInfo.getValue: failed.  Name is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    Object[] arrayOfObject = { paramString1, paramString2 };
    str3 = "SELECT Value FROM BPW_RecXferExtraInfo WHERE RecSrvrTID = ? AND Name = ? ";
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        str4 = localFFSResultSet.getColumnString("Value");
      }
    }
    catch (Exception localException1)
    {
      str1 = "BPWRecXferExtraInfo.getValue: failed ";
      str2 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str1, str2, 0);
      throw new BPWException(str1);
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
        str1 = "BPWRecXferExtraInfo.getValue: failed ";
        str2 = FFSDebug.stackTrace(localException2);
        FFSDebug.log(str1, str2, 0);
      }
    }
    if (str4 == null)
    {
      str1 = "BPWRecXferExtraInfo.getValue: failed. No value found for name " + paramString2;
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    FFSDebug.log("BPWRecXferExtraInfo.getValue End : value = " + str4, 6);
    return str4;
  }
  
  public static HashMap getHashMap(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("BPWRecXferExtraInfo.getHashMap Start: recSrvrTID = " + paramString, 6);
    String str1 = null;
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    HashMap localHashMap = null;
    String str3 = null;
    String str4 = null;
    if (paramString == null)
    {
      str1 = "BPWRecXferExtraInfo.getHashMap: failed. recSrvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "BPWRecXferExtraInfo.getHashMap: failed. Connection holder is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    Object[] arrayOfObject = { paramString };
    String str5 = "SELECT Name,Value FROM BPW_RecXferExtraInfo WHERE RecSrvrTID = ?";
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str5, arrayOfObject);
      localHashMap = new HashMap();
      while (localFFSResultSet.getNextRow())
      {
        str3 = localFFSResultSet.getColumnString("Name");
        str4 = localFFSResultSet.getColumnString("Value");
        if ((str3 != null) && (str4 != null)) {
          localHashMap.put(str3, str4);
        }
      }
    }
    catch (Exception localException1)
    {
      str1 = "BPWRecXferExtraInfo.getHashMap: failed ";
      str2 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str1, str2, 0);
      throw new BPWException(str1);
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
        str1 = "BPWRecXferExtraInfo.getHashMap: failed ";
        str2 = FFSDebug.stackTrace(localException2);
        FFSDebug.log(str1, str2, 0);
      }
    }
    FFSDebug.log("BPWRecXferExtraInfo.getHashMap  End: ", 6);
    return localHashMap;
  }
  
  public static void insertValue(String paramString1, String paramString2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("BPWRecXferExtraInfo.insertValue Start: recSrvrTID = " + paramString1 + " name = " + paramString2 + " value = " + paramString3, 6);
    String str1 = null;
    String str2 = null;
    if (paramString1 == null)
    {
      str1 = "BPWRecXferExtraInfo.insertValue: failed. recSrvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "BPWRecXferExtraInfo.insertValue: failed. Connection holder is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      str1 = "BPWRecXferExtraInfo.insertValue: failed.  Name is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if ((paramString3 == null) || (paramString3.trim().length() == 0))
    {
      str1 = "BPWRecXferExtraInfo.insertValue: failed. recXferXtraInfo Value is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    try
    {
      String str3 = "INSERT INTO  BPW_RecXferExtraInfo (RecSrvrTID,Name,Value) VALUES (?,?,?)";
      Object[] arrayOfObject = { paramString1, paramString2, paramString3 };
      DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
    }
    catch (Exception localException)
    {
      str1 = "BPWRecXferExtraInfo.insertValue: failed ";
      str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str1, str2, 0);
      throw new BPWException(str1);
    }
    FFSDebug.log("BPWRecXferExtraInfo.insertValue End: ", 6);
  }
  
  public static void insertHashtable(String paramString, Hashtable paramHashtable, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("BPWRecXferExtraInfo.insertHashtable Start: recSrvrTID = " + paramString, 6);
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    if (paramString == null)
    {
      str1 = "BPWRecXferExtraInfo.insertHashtable: failed. recSrvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "BPWRecXferExtraInfo.insertHashtable: failed. Null connection holder passed";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramHashtable == null)
    {
      str1 = "BPWRecXferExtraInfo.insertHashtable: failed. recXferXtraInfo table object is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    Enumeration localEnumeration = paramHashtable.keys();
    while (localEnumeration.hasMoreElements())
    {
      str3 = (String)localEnumeration.nextElement();
      Object localObject = paramHashtable.get(str3);
      if ((localObject instanceof ValueInfo)) {
        str4 = ((ValueInfo)localObject).toString();
      } else {
        str4 = (String)localObject;
      }
      try
      {
        insertValue(paramString, str3, str4, paramFFSConnectionHolder);
      }
      catch (BPWException localBPWException)
      {
        throw localBPWException;
      }
      catch (Exception localException)
      {
        str1 = "BPWRecXferExtraInfo.insertHashtable: failed ";
        str2 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str1, str2, 0);
        throw new BPWException(str1);
      }
    }
    FFSDebug.log("BPWRecXferExtraInfo.insertHashtable: End", 6);
  }
  
  public static void updateValue(String paramString1, String paramString2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("BPWRecXferExtraInfo.updateValue Start: recSrvrTID = " + paramString1 + " name = " + paramString2 + " value = " + paramString3, 6);
    String str1 = null;
    String str2 = null;
    int i = 0;
    if (paramString1 == null)
    {
      str1 = "BPWRecXferExtraInfo.updateValue: failed. recSrvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "BPWRecXferExtraInfo.updateValue: failed. Connection holder is null ";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      str1 = "BPWRecXferExtraInfo.updateValue: failed.  Name is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if ((paramString3 == null) || (paramString3.trim().length() == 0))
    {
      str1 = "BPWRecXferExtraInfo.updateValue: failed.  Value is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    try
    {
      String str3 = "UPDATE BPW_RecXferExtraInfo set Value = ? WHERE RecSrvrTID = ? AND Name = ? ";
      Object[] arrayOfObject = { paramString3, paramString1, paramString2 };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
      if (i == 0)
      {
        str1 = "BPWRecXferExtraInfo.updateValue: failed No such key exists by the name " + paramString2;
        FFSDebug.log(str1, 0);
        throw new BPWException(str1);
      }
    }
    catch (Exception localException)
    {
      str1 = "BPWRecXferExtraInfo.updateValue: failed ";
      str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str1, str2, 0);
      throw new BPWException(str1);
    }
    FFSDebug.log("BPWExtraInfo.updateValue End: noOfRowsUpdated = " + i, 6);
  }
  
  public static void updateHashtable(String paramString, Hashtable paramHashtable, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("BPWRecXferExtraInfo.updateHashtable Start: recSrvrTID = " + paramString, 6);
    String str1 = null;
    String str2 = null;
    Object localObject1 = null;
    Object localObject2 = null;
    if (paramString == null)
    {
      str1 = "BPWRecXferExtraInfo.updateHashTable: failed. recSrvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "BPWRecXferExtraInfo.updateHashtable: failed. Connection holder is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramHashtable == null)
    {
      str1 = "BPWRecXferExtraInfo.updateHashtable: failed. recXferXtraInfo table object is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    try
    {
      deleteAllWithRecSrvrTID(paramString, paramFFSConnectionHolder);
      insertHashtable(paramString, paramHashtable, paramFFSConnectionHolder);
    }
    catch (BPWException localBPWException)
    {
      throw localBPWException;
    }
    catch (Exception localException)
    {
      str1 = "BPWRecXferExtraInfo.updateHashtable: failed ";
      str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str1, str2, 0);
      throw new BPWException(str1);
    }
    FFSDebug.log("BPWExtraInfo.updateHashtable: end", 6);
  }
  
  public static void deleteValue(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("BPWRecXferExtraInfo.deleteValue Start: recSrvrTID = " + paramString1 + " name = " + paramString2, 6);
    String str1 = null;
    String str2 = null;
    int i = 0;
    if (paramString1 == null)
    {
      str1 = "BPWRecXferExtraInfo.deleteValue: failed. recSrvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "BPWRecXferExtraInfo.deleteValue: failed. Connection holder is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramString2 == null)
    {
      str1 = "BPWRecXferExtraInfo.deleteValue: failed.  name object is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    try
    {
      String str3 = "DELETE FROM BPW_RecXferExtraInfo WHERE RecSrvrTID = ? And Name = ?";
      Object[] arrayOfObject = { paramString1, paramString2 };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
    }
    catch (Exception localException)
    {
      str1 = "BPWRecXferExtraInfo.deleteValue: failed ";
      str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str1, str2, 0);
      throw new BPWException(str1);
    }
    FFSDebug.log("BPWRecXferExtraInfo.deleteValue: end, noOfRowsDeleted =" + i, 6);
  }
  
  public static void deleteAllWithRecSrvrTID(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("BPWRecXferExtraInfo.deleteAllWithrecSrvrTID Start: recSrvrTID = " + paramString, 6);
    String str1 = null;
    String str2 = null;
    int i = 0;
    if (paramString == null)
    {
      str1 = "BPWRecXferExtraInfo.deleteAllWithrecSrvrTID: failed. recSrvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "BPWRecXferExtraInfo.deleteAllWithrecSrvrTID: failed. Connection holder is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    try
    {
      String str3 = "DELETE FROM BPW_RecXferExtraInfo WHERE RecSrvrTID = ?";
      Object[] arrayOfObject = { paramString };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
    }
    catch (Exception localException)
    {
      str1 = "BPWRecXferExtraInfo.deleteAllWithrecSrvrTID: failed ";
      str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str1, str2, 0);
      throw new BPWException(str1);
    }
    FFSDebug.log("BPWRecXferExtraInfo.deleteAllWithrecSrvrTID: end, noOfRowsDeleted =" + i, 6);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.BPWRecXferExtraInfo
 * JD-Core Version:    0.7.0.1
 */