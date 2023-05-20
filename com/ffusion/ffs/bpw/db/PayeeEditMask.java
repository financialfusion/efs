package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.util.Vector;

public class PayeeEditMask
  implements FFSConst, DBConsts
{
  public static void addPayeeEditMask(FFSConnectionHolder paramFFSConnectionHolder, String paramString, String[] paramArrayOfString)
    throws BPWException
  {
    String str1 = "PayeeEditMask.addPayeeEditMask: ";
    FFSDebug.log(str1 + "start, payeeId=" + paramString, 6);
    String str2 = "INSERT INTO BPW_PayeeEditMask( PayeeID, ObsoleteEditMask, ValidEditMask ) VALUES(?,?,?)";
    int i = 0;
    if ((paramArrayOfString != null) && (paramArrayOfString.length != 0))
    {
      try
      {
        for (int j = 0; j < paramArrayOfString.length; j++) {
          if (payeeEditMaskExist(paramFFSConnectionHolder, paramString, paramArrayOfString[j]) == true)
          {
            FFSDebug.log(str1 + "Mask exists for this payee, payeeId =" + paramString + " mask=" + paramArrayOfString[j], 6);
          }
          else
          {
            Object[] arrayOfObject = { paramString, "", paramArrayOfString[j] };
            DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
          }
        }
      }
      catch (Exception localException)
      {
        FFSDebug.log("*** " + str1 + "failed:" + localException.toString());
        throw new BPWException(localException, localException.toString());
      }
      i = paramArrayOfString.length;
    }
    FFSDebug.log(str1 + "done, masks added=" + i, 6);
  }
  
  public static void modPayeeEditMask(FFSConnectionHolder paramFFSConnectionHolder, String paramString, String[] paramArrayOfString)
    throws BPWException
  {
    String str = "PayeeEditMask.modPayeeEditMask: ";
    FFSDebug.log(str + "start, payeeId=" + paramString, 6);
    deletePayeeEditMask(paramString, paramFFSConnectionHolder);
    addPayeeEditMask(paramFFSConnectionHolder, paramString, paramArrayOfString);
    FFSDebug.log(str + "done.", 6);
  }
  
  public static boolean payeeEditMaskExist(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws BPWException
  {
    String str1 = "PayeeEditMask.payeeEditMaskExist: ";
    FFSDebug.log(str1 + "start, payeeId = " + paramString1 + ", mask = " + paramString2, 6);
    boolean bool = false;
    String str2 = "SELECT ValidEditMask FROM BPW_PayeeEditMask WHERE PayeeID=? and ValidEditMask=?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        bool = true;
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** " + str1 + "failed:" + localException1.toString() + ". payeeId = " + paramString1 + ", mask = " + paramString2);
      throw new BPWException(localException1.toString());
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
          FFSDebug.log("***" + str1 + "Failed to close result set." + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "done, exists=" + bool, 6);
    return bool;
  }
  
  public static String[] getPayeeEditMask(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    String str1 = "PayeeEditMask.getPayeeEditMask: ";
    FFSDebug.log(str1 + "start, payeeId=" + paramString, 6);
    String str2 = "SELECT ValidEditMask FROM BPW_PayeeEditMask WHERE PayeeID=?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    Vector localVector = new Vector();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        String str3 = localFFSResultSet.getColumnString(1);
        localVector.addElement(str3);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** " + str1 + "failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
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
          FFSDebug.log("***" + str1 + "Failed to close result set." + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "done, payeeId=" + paramString, 6);
    if (localVector.isEmpty()) {
      return null;
    }
    return (String[])localVector.toArray(new String[0]);
  }
  
  public static int deletePayeeEditMask(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    String str1 = "PayeeEditMask.deletePayeeEditMask: ";
    FFSDebug.log(str1 + "start, payeeId=" + paramString, 6);
    String str2 = "DELETE FROM BPW_PayeeEditMask WHERE PayeeID = ? ";
    Object[] arrayOfObject = { paramString };
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** " + str1 + "failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log(str1 + "done, masks deleted =" + i, 6);
    return i;
  }
  
  public static boolean checkPayeeEditMask(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws Exception
  {
    if ((paramString2 == null) || (paramString2.length() == 0)) {
      return false;
    }
    String[] arrayOfString = getPayeeEditMask(paramString1, paramFFSConnectionHolder);
    if (arrayOfString != null) {
      for (int i = 0; i < arrayOfString.length; i++) {
        if (paramString2.length() == arrayOfString[i].length())
        {
          char[] arrayOfChar1 = arrayOfString[i].toCharArray();
          char[] arrayOfChar2 = paramString2.toCharArray();
          int j = 1;
          for (int k = 0; k < arrayOfChar1.length; k++) {
            if (!jdMethod_if(arrayOfChar1[k], arrayOfChar2[k]))
            {
              j = 0;
              break;
            }
          }
          if (j != 0) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  private static boolean jdMethod_if(char paramChar1, char paramChar2)
  {
    if (paramChar1 == '*') {
      return Character.isLetter(paramChar2);
    }
    if (paramChar1 == '&') {
      return Character.isLetterOrDigit(paramChar2);
    }
    if (paramChar1 == '#') {
      return Character.isDigit(paramChar2);
    }
    if (Character.isDigit(paramChar1)) {
      return paramChar2 == paramChar1;
    }
    if (Character.isLetterOrDigit(paramChar1)) {
      return paramChar2 == paramChar1;
    }
    return false;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.PayeeEditMask
 * JD-Core Version:    0.7.0.1
 */