package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PmtInvoice;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

public class PmtExtraInfo
  implements FFSConst, DBConsts
{
  public static final String NAME_INVOICE = "INVOICE";
  
  public static String getValue(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PmtExtraInfo.getValue Start : srvrTID = " + paramString1 + "name = " + paramString2, 6);
    String str1 = null;
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    String str3 = null;
    String str4 = null;
    if (paramString1 == null)
    {
      str1 = "PmtExtraInfo.getValue: failed. srvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "PmtExtraInfo.getValue: failed. Connection holder is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      str1 = "PmtExtraInfo.getValue: failed.  Name is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    Object[] arrayOfObject = { paramString1, paramString2 };
    str3 = "SELECT Value FROM BPW_PmtExtraInfo WHERE SrvrTID = ? AND Name = ? ";
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        str4 = localFFSResultSet.getColumnString("Value");
      }
    }
    catch (Exception localException1)
    {
      str1 = "PmtExtraInfo.getValue: failed ";
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
        str1 = "PmtExtraInfo.getValue: failed ";
        str2 = FFSDebug.stackTrace(localException2);
        FFSDebug.log(str1, str2, 0);
      }
    }
    if (str4 == null)
    {
      str1 = "PmtExtraInfo.getValue: failed. No value found for name " + paramString2;
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    FFSDebug.log("PmtExtraInfo.getValue End: value = " + str4, 6);
    return str4;
  }
  
  public static HashMap getHashMap(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PmtExtraInfo.getHashMap Start: srvrTID = " + paramString, 6);
    String str1 = null;
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    HashMap localHashMap = null;
    String str3 = null;
    String str4 = null;
    if (paramString == null)
    {
      str1 = "PmtExtraInfo.getHashMap: failed. srvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "PmtExtraInfo.getHashMap: failed. Connection holder is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    Object[] arrayOfObject = { paramString };
    String str5 = "SELECT Name,Value FROM BPW_PmtExtraInfo WHERE SrvrTID = ?";
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
      str1 = "PmtExtraInfo.getHashMap: failed ";
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
        str1 = "PmtExtraInfo.getHashMap: failed ";
        str2 = FFSDebug.stackTrace(localException2);
        FFSDebug.log(str1, str2, 0);
      }
    }
    FFSDebug.log("PmtExtraInfo.getHashMap End.", 6);
    return localHashMap;
  }
  
  public static void insertValue(String paramString1, String paramString2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PmtExtraInfo.insertValue Start: srvrTID = " + paramString1 + " name = " + paramString2 + " value = " + paramString3, 6);
    String str1 = null;
    String str2 = null;
    if (paramString1 == null)
    {
      str1 = "PmtExtraInfo.insertValue: failed. srvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "PmtExtraInfo.insertValue: failed. Connection holder is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      str1 = "PmtExtraInfo.insertValue: failed.  Name is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if ((paramString3 == null) || (paramString3.trim().length() == 0))
    {
      str1 = "PmtExtraInfo.insertValue: failed.  Value is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    try
    {
      String str3 = "INSERT INTO  BPW_PmtExtraInfo (SrvrTID,Name,Value) VALUES (?,?,?)";
      Object[] arrayOfObject = { paramString1, paramString2, paramString3 };
      DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
    }
    catch (Exception localException)
    {
      str1 = "PmtExtraInfo.insertValue: failed ";
      str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str1, str2, 0);
      throw new BPWException(str1);
    }
    FFSDebug.log("PmtExtraInfo.insertValue End.", 6);
  }
  
  public static void insertHashtable(String paramString, Hashtable paramHashtable, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PmtExtraInfo.insertHashtable Start: srvrTID = " + paramString, 6);
    String str1 = null;
    String str2 = null;
    String str3 = null;
    Object localObject = null;
    if (paramString == null)
    {
      str1 = "PmtExtraInfo.insertHashtable: failed. srvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "PmtExtraInfo.insertHashtable: failed. Null connection holder passed";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramHashtable == null)
    {
      str1 = "PmtExtraInfo.insertHashtable: failed.  table object is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    Enumeration localEnumeration = paramHashtable.keys();
    while (localEnumeration.hasMoreElements())
    {
      str3 = (String)localEnumeration.nextElement();
      localObject = paramHashtable.get(str3);
      if (str3.equals("INVOICE")) {
        localObject = ((PmtInvoice)localObject).build();
      }
      try
      {
        insertValue(paramString, str3, (String)localObject, paramFFSConnectionHolder);
      }
      catch (BPWException localBPWException)
      {
        throw localBPWException;
      }
      catch (Exception localException)
      {
        str1 = "PmtExtraInfo.insertHashtable: failed ";
        str2 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str1, str2, 0);
        throw new BPWException(str1);
      }
    }
    FFSDebug.log("PmtExtraInfo.insertHashtable: End.", 6);
  }
  
  public static void updateValue(String paramString1, String paramString2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PmtExtraInfo.updateValue Start: srvrTID = " + paramString1 + " name = " + paramString2 + " value = " + paramString3, 6);
    String str1 = null;
    String str2 = null;
    int i = 0;
    if (paramString1 == null)
    {
      str1 = "PmtExtraInfo.updateValue: failed. srvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "PmtExtraInfo.updateValue: failed. Connection holder is null ";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      str1 = "PmtExtraInfo.updateValue: failed.  Name is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if ((paramString3 == null) || (paramString3.trim().length() == 0))
    {
      str1 = "PmtExtraInfo.updateValue: failed.  Value is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    try
    {
      String str3 = "UPDATE BPW_PmtExtraInfo set Value = ? WHERE SrvrTID = ? AND Name = ? ";
      Object[] arrayOfObject = { paramString3, paramString1, paramString2 };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
      if (i == 0)
      {
        str1 = "PmtExtraInfo.updateValue: failed No such key exists by the name " + paramString2;
        FFSDebug.log(str1, 0);
        throw new BPWException(str1);
      }
    }
    catch (Exception localException)
    {
      str1 = "PmtExtraInfo.updateValue: failed ";
      str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str1, str2, 0);
      throw new BPWException(str1);
    }
    FFSDebug.log("PmtExtraInfo.updateValue End: noOfRowsUpdated = " + i, 6);
  }
  
  public static void updateHashtable(String paramString, Hashtable paramHashtable, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PmtExtraInfo.updateHashtable Start: srvrTID = " + paramString, 6);
    String str1 = null;
    String str2 = null;
    Object localObject1 = null;
    Object localObject2 = null;
    if (paramString == null)
    {
      str1 = "PmtExtraInfo.updateHashTable: failed. srvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "PmtExtraInfo.updateHashtable: failed. Connection holder is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramHashtable == null)
    {
      str1 = "PmtExtraInfo.updateHashtable: failed.  table object is null";
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
      str1 = "PmtExtraInfo.updateHashtable: failed ";
      str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str1, str2, 0);
      throw new BPWException(str1);
    }
    FFSDebug.log("PmtExtraInfo.updateHashtable: End.", 6);
  }
  
  public static void deleteValue(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PmtExtraInfo.deleteValue Start: srvrTID = " + paramString1 + " name = " + paramString2, 6);
    String str1 = null;
    String str2 = null;
    int i = 0;
    if (paramString1 == null)
    {
      str1 = "PmtExtraInfo.deleteValue: failed. srvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "PmtExtraInfo.deleteValue: failed. Connection holder is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramString2 == null)
    {
      str1 = "PmtExtraInfo.deleteValue: failed.  name object is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    try
    {
      String str3 = "DELETE FROM BPW_PmtExtraInfo WHERE SrvrTID = ? And Name = ?";
      Object[] arrayOfObject = { paramString1, paramString2 };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
    }
    catch (Exception localException)
    {
      str1 = "PmtExtraInfo.deleteValue: failed ";
      str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str1, str2, 0);
      throw new BPWException(str1);
    }
    FFSDebug.log("PmtExtraInfo.deleteValue: End: noOfRowsDeleted =" + i, 6);
  }
  
  public static void deleteAllWithSrvrTID(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PmtExtraInfo.deleteAllWithSrvrTID Start: srvrTID = " + paramString, 6);
    String str1 = null;
    String str2 = null;
    int i = 0;
    if (paramString == null)
    {
      str1 = "PmtExtraInfo.deleteAllWithSrvrTID: failed. srvrTID is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (paramFFSConnectionHolder == null)
    {
      str1 = "PmtExtraInfo.deleteAllWithSrvrTID: failed. Connection holder is null";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    try
    {
      String str3 = "DELETE FROM BPW_PmtExtraInfo WHERE SrvrTID = ?";
      Object[] arrayOfObject = { paramString };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
    }
    catch (Exception localException)
    {
      str1 = "PmtExtraInfo.deleteAllWithSrvrTID: failed ";
      str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str1, str2, 0);
      throw new BPWException(str1);
    }
    FFSDebug.log("PmtExtraInfo.deleteAllWithSrvrTID: End: noOfRowsDeleted =" + i, 6);
  }
  
  public static int deleteBatch(FFSConnectionHolder paramFFSConnectionHolder, String[] paramArrayOfString)
    throws BPWException
  {
    FFSDebug.log("PmtExtraInfo.deleteBatch start.", 6);
    String str1 = "DELETE FROM BPW_PmtExtraInfo WHERE SrvrTID = ?";
    try
    {
      DBUtil.executeStatementBatch(paramFFSConnectionHolder, str1, DBUtil.arrayStringToArrayList(paramArrayOfString));
    }
    catch (Exception localException)
    {
      String str2 = "*** PmtExtraInfo.deleteBatch failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("PmtExtraInfo.deleteBatch done. Deleted extra info for " + paramArrayOfString.length + " payments", 6);
    return paramArrayOfString.length;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.PmtExtraInfo
 * JD-Core Version:    0.7.0.1
 */