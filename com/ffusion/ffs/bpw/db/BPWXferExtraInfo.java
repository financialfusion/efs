package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.ValueInfo;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.util.FFSDebug;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

public class BPWXferExtraInfo
{
  public static String getValue(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("BPWXferExtraInfo.getValue Start : srvrTID = " + paramString1 + "name = " + paramString2, 6);
    String str1 = null;
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    String str3 = null;
    String str4 = null;
    if (paramString1 == null)
    {
      str1 = "BPWXferExtraInfo.getValue: failed. srvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "BPWXferExtraInfo.getValue: failed. Connection holder is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      str1 = "BPWXferExtraInfo.getValue: failed.  Name is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    Object[] arrayOfObject = { paramString1, paramString2 };
    str3 = "SELECT Value FROM BPW_XferExtraInfo WHERE SrvrTID = ? AND Name = ? ";
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        str4 = localFFSResultSet.getColumnString("Value");
      }
    }
    catch (Exception localException1)
    {
      str1 = "BPWXferExtraInfo.getValue: failed ";
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
        str1 = "BPWXferExtraInfo.getValue: failed ";
        str2 = FFSDebug.stackTrace(localException2);
        FFSDebug.log(str1, str2, 0);
      }
    }
    if (str4 == null)
    {
      str1 = "BPWXferExtraInfo.getValue: failed. No value found for name " + paramString2;
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    FFSDebug.log("BPWXferExtraInfo.getValue End : value = " + str4, 6);
    return str4;
  }
  
  public static HashMap getHashMap(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("BPWXferExtraInfo.getHashMap Start: srvrTID = " + paramString, 6);
    String str1 = null;
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    HashMap localHashMap = null;
    String str3 = null;
    String str4 = null;
    if (paramString == null)
    {
      str1 = "BPWXferExtraInfo.getHashMap: failed. srvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "BPWXferExtraInfo.getHashMap: failed. Connection holder is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    Object[] arrayOfObject = { paramString };
    String str5 = "SELECT Name,Value FROM BPW_XferExtraInfo WHERE SrvrTID = ?";
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
      str1 = "BPWXferExtraInfo.getHashMap: failed ";
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
        str1 = "BPWXferExtraInfo.getHashMap: failed ";
        str2 = FFSDebug.stackTrace(localException2);
        FFSDebug.log(str1, str2, 0);
      }
    }
    FFSDebug.log("BPWXferExtraInfo.getHashMap  End: ", 6);
    return localHashMap;
  }
  
  public static void insertValue(String paramString1, String paramString2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("BPWXferExtraInfo.insertValue Start: srvrTID = " + paramString1 + " name = " + paramString2 + " value = " + paramString3, 6);
    String str1 = null;
    String str2 = null;
    if (paramString1 == null)
    {
      str1 = "BPWXferExtraInfo.insertValue: failed. srvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "BPWXferExtraInfo.insertValue: failed. Connection holder is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      str1 = "BPWXferExtraInfo.insertValue: failed.  Name is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if ((paramString3 == null) || (paramString3.trim().length() == 0))
    {
      str1 = "BPWXferExtraInfo.insertValue: failed. XferXtraInfo Value is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    try
    {
      String str3 = "INSERT INTO  BPW_XferExtraInfo (SrvrTID,Name,Value) VALUES (?,?,?)";
      Object[] arrayOfObject = { paramString1, paramString2, paramString3 };
      DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
    }
    catch (Exception localException)
    {
      str1 = "BPWXferExtraInfo.insertValue: failed ";
      str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str1, str2, 0);
      throw new BPWException(str1);
    }
    FFSDebug.log("BPWXferExtraInfo.insertValue End: ", 6);
  }
  
  public static void insertHashtable(String paramString, Hashtable paramHashtable, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("BPWXferExtraInfo.insertHashtable Start: srvrTID = " + paramString, 6);
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    if (paramString == null)
    {
      str1 = "BPWXferExtraInfo.insertHashtable: failed. srvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "BPWXferExtraInfo.insertHashtable: failed. Null connection holder passed";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramHashtable == null)
    {
      str1 = "BPWXferExtraInfo.insertHashtable: failed. XferXtraInfo table object is null";
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
        str1 = "BPWXferExtraInfo.insertHashtable: failed ";
        str2 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str1, str2, 0);
        throw new BPWException(str1);
      }
    }
    FFSDebug.log("BPWXferExtraInfo.insertHashtable: End", 6);
  }
  
  public static void updateValue(String paramString1, String paramString2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("BPWXferExtraInfo.updateValue Start: srvrTID = " + paramString1 + " name = " + paramString2 + " value = " + paramString3, 6);
    String str1 = null;
    String str2 = null;
    int i = 0;
    if (paramString1 == null)
    {
      str1 = "BPWXferExtraInfo.updateValue: failed. srvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "BPWXferExtraInfo.updateValue: failed. Connection holder is null ";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      str1 = "BPWXferExtraInfo.updateValue: failed.  Name is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if ((paramString3 == null) || (paramString3.trim().length() == 0))
    {
      str1 = "BPWXferExtraInfo.updateValue: failed.  Value is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    try
    {
      String str3 = "UPDATE BPW_XferExtraInfo set Value = ? WHERE SrvrTID = ? AND Name = ? ";
      Object[] arrayOfObject = { paramString3, paramString1, paramString2 };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
      if (i == 0)
      {
        str1 = "BPWXferExtraInfo.updateValue: failed No such key exists by the name " + paramString2;
        FFSDebug.log(str1, 0);
        throw new BPWException(str1);
      }
    }
    catch (Exception localException)
    {
      str1 = "BPWXferExtraInfo.updateValue: failed ";
      str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str1, str2, 0);
      throw new BPWException(str1);
    }
    FFSDebug.log("BPWExtraInfo.updateValue End: noOfRowsUpdated = " + i, 6);
  }
  
  public static void updateHashtable(String paramString, Hashtable paramHashtable, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("BPWXferExtraInfo.updateHashtable Start: srvrTID = " + paramString, 6);
    String str1 = null;
    String str2 = null;
    Object localObject1 = null;
    Object localObject2 = null;
    if (paramString == null)
    {
      str1 = "BPWXferExtraInfo.updateHashTable: failed. srvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "BPWXferExtraInfo.updateHashtable: failed. Connection holder is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramHashtable == null)
    {
      str1 = "BPWXferExtraInfo.updateHashtable: failed. XferXtraInfo table object is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    try
    {
      deleteAllWithSrvrTID(paramString, paramFFSConnectionHolder);
      insertHashtable(paramString, paramHashtable, paramFFSConnectionHolder);
    }
    catch (BPWException localBPWException)
    {
      throw localBPWException;
    }
    catch (Exception localException)
    {
      str1 = "BPWXferExtraInfo.updateHashtable: failed ";
      str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str1, str2, 0);
      throw new BPWException(str1);
    }
    FFSDebug.log("BPWExtraInfo.updateHashtable: end", 6);
  }
  
  public static void deleteValue(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("BPWXferExtraInfo.deleteValue Start: srvrTID = " + paramString1 + " name = " + paramString2, 6);
    String str1 = null;
    String str2 = null;
    int i = 0;
    if (paramString1 == null)
    {
      str1 = "BPWXferExtraInfo.deleteValue: failed. srvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "BPWXferExtraInfo.deleteValue: failed. Connection holder is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramString2 == null)
    {
      str1 = "BPWXferExtraInfo.deleteValue: failed.  name object is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    try
    {
      String str3 = "DELETE FROM BPW_XferExtraInfo WHERE SrvrTID = ? And Name = ?";
      Object[] arrayOfObject = { paramString1, paramString2 };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
    }
    catch (Exception localException)
    {
      str1 = "BPWXferExtraInfo.deleteValue: failed ";
      str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str1, str2, 0);
      throw new BPWException(str1);
    }
    FFSDebug.log("BPWXferExtraInfo.deleteValue: end, noOfRowsDeleted =" + i, 6);
  }
  
  public static void deleteAllWithSrvrTID(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("BPWXferExtraInfo.deleteAllWithSrvrTID Start: srvrTID = " + paramString, 6);
    String str1 = null;
    String str2 = null;
    int i = 0;
    if (paramString == null)
    {
      str1 = "BPWXferExtraInfo.deleteAllWithSrvrTID: failed. srvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "BPWXferExtraInfo.deleteAllWithSrvrTID: failed. Connection holder is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    try
    {
      String str3 = "DELETE FROM BPW_XferExtraInfo WHERE SrvrTID = ?";
      Object[] arrayOfObject = { paramString };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
    }
    catch (Exception localException)
    {
      str1 = "BPWXferExtraInfo.deleteAllWithSrvrTID: failed ";
      str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str1, str2, 0);
      throw new BPWException(str1);
    }
    FFSDebug.log("BPWXferExtraInfo.deleteAllWithSrvrTID: end, noOfRowsDeleted =" + i, 6);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.BPWXferExtraInfo
 * JD-Core Version:    0.7.0.1
 */