package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import java.math.BigInteger;

public class IndexEntry
{
  private String jdField_else;
  private int a;
  private int jdField_do;
  private BigInteger jdField_byte;
  private BigInteger jdField_int;
  private int jdField_if;
  private BigInteger jdField_goto;
  private static final BigInteger jdField_try = new BigInteger("-1");
  private static final BigInteger jdField_new = new BigInteger("0");
  private static final BigInteger jdField_case = new BigInteger("1");
  private static final int jdField_char = -1;
  private static final int jdField_for = 0;
  private static final int jdField_long = 1;
  
  public IndexEntry(String paramString, int paramInt)
  {
    this.jdField_else = paramString;
    this.a = -1;
    this.jdField_do = -1;
    this.jdField_byte = jdField_try;
    this.jdField_int = jdField_try;
    this.jdField_if = paramInt;
    this.jdField_goto = new BigInteger(String.valueOf(paramInt));
  }
  
  public synchronized int getNextIndexInt()
    throws Exception
  {
    if (this.a == this.jdField_do) {
      jdField_if();
    }
    this.a += 1;
    return this.a;
  }
  
  public synchronized String getNextIndexString()
    throws Exception
  {
    if (this.jdField_byte.compareTo(this.jdField_int) == 0) {
      a();
    }
    this.jdField_byte = this.jdField_byte.add(jdField_case);
    return this.jdField_byte.toString();
  }
  
  public synchronized int getLastAssnInt()
    throws Exception
  {
    if (this.a == -1) {
      jdField_if();
    }
    return this.a;
  }
  
  public synchronized String getLastAssnStr()
    throws Exception
  {
    if (this.jdField_byte.compareTo(jdField_try) == 0) {
      a();
    }
    return this.jdField_byte.toString();
  }
  
  private void jdField_if()
    throws Exception
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    FFSResultSet localFFSResultSet = null;
    try
    {
      String str2 = localFFSConnectionHolder.conn.getDatabaseType();
      String str1;
      if ((str2.startsWith("DB2")) || (str2.startsWith("ORA")))
      {
        str1 = "SELECT IndexValue FROM BPW_InternalIndices WHERE IndexName = ? FOR UPDATE";
      }
      else if (str2.startsWith("MSSQL"))
      {
        str1 = "SELECT IndexValue FROM BPW_InternalIndices WITH (XLOCK) WHERE IndexName = ?";
      }
      else
      {
        str1 = "SELECT IndexValue FROM BPW_InternalIndices HOLDLOCK WHERE IndexName = ?";
        DBUtil.executeStatement(localFFSConnectionHolder, "begin tran", null);
      }
      Object[] arrayOfObject1 = { this.jdField_else };
      int i = 0;
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str1, arrayOfObject1);
      if (localFFSResultSet.getNextRow() == true) {
        i = localFFSResultSet.getColumnInt("IndexValue");
      } else {
        throw new Exception("Index name does not exist: " + this.jdField_else);
      }
      int j = i + this.jdField_if;
      if (j < 0)
      {
        i = 0;
        j = i + this.jdField_if;
      }
      String str3 = "UPDATE BPW_InternalIndices SET IndexValue =? WHERE IndexName= ?";
      Object[] arrayOfObject2 = { new Integer(j), this.jdField_else };
      int k = DBUtil.executeStatement(localFFSConnectionHolder, str3, arrayOfObject2);
      localFFSConnectionHolder.conn.commit();
      this.a = i;
      this.jdField_do = j;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      throw localException;
    }
    finally
    {
      if (localFFSResultSet != null) {
        localFFSResultSet.close();
      }
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  private void a()
    throws Exception
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    FFSResultSet localFFSResultSet = null;
    try
    {
      String str2 = localFFSConnectionHolder.conn.getDatabaseType();
      String str1;
      if ((str2.startsWith("DB2")) || (str2.startsWith("ORA")))
      {
        str1 = "SELECT IndexString FROM BPW_InternalIndices WHERE IndexName = ? FOR UPDATE";
      }
      else if (str2.startsWith("MSSQL"))
      {
        str1 = "SELECT IndexString FROM BPW_InternalIndices WITH (XLOCK) WHERE IndexName = ?";
      }
      else
      {
        str1 = "SELECT IndexString FROM BPW_InternalIndices HOLDLOCK WHERE IndexName = ?";
        DBUtil.executeStatement(localFFSConnectionHolder, "begin tran", null);
      }
      Object[] arrayOfObject1 = { this.jdField_else };
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str1, arrayOfObject1);
      BigInteger localBigInteger1;
      if (localFFSResultSet.getNextRow() == true) {
        localBigInteger1 = new BigInteger(localFFSResultSet.getColumnString("IndexString"));
      } else {
        throw new FFSException("Index name does not exist: " + this.jdField_else);
      }
      BigInteger localBigInteger2 = localBigInteger1.add(this.jdField_goto);
      if (localBigInteger2.bitLength() > 104)
      {
        localBigInteger1 = jdField_new;
        localBigInteger2 = localBigInteger1.add(this.jdField_goto);
      }
      String str3 = "UPDATE BPW_InternalIndices SET IndexString =? WHERE IndexName= ?";
      Object[] arrayOfObject2 = { localBigInteger2.toString(), this.jdField_else };
      int i = DBUtil.executeStatement(localFFSConnectionHolder, str3, arrayOfObject2);
      if ((str2.startsWith("DB2")) || (str2.startsWith("ORA")))
      {
        localFFSConnectionHolder.conn.commit();
      }
      else if (str2.startsWith("MSSQL"))
      {
        localFFSConnectionHolder.conn.commit();
      }
      else
      {
        DBUtil.executeStatement(localFFSConnectionHolder, "commit transaction", null);
        localFFSConnectionHolder.conn.commit();
      }
      this.jdField_byte = localBigInteger1;
      this.jdField_int = localBigInteger2;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      throw localException;
    }
    finally
    {
      if (localFFSResultSet != null) {
        localFFSResultSet.close();
      }
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("IndexName = ");
    localStringBuffer.append(this.jdField_else);
    localStringBuffer.append(";");
    localStringBuffer.append("LastAssignedInt = ");
    localStringBuffer.append(this.a);
    localStringBuffer.append(";");
    localStringBuffer.append("LastAvailInt = ");
    localStringBuffer.append(this.jdField_do);
    localStringBuffer.append(";");
    localStringBuffer.append("LastAssignedBInt = ");
    localStringBuffer.append(this.jdField_byte.toString());
    localStringBuffer.append(";");
    localStringBuffer.append("LastAvailBInt = ");
    localStringBuffer.append(this.jdField_int.toString());
    localStringBuffer.append(";");
    localStringBuffer.append("Reserved Block Size = ");
    localStringBuffer.append(this.jdField_if);
    localStringBuffer.append(";");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.IndexEntry
 * JD-Core Version:    0.7.0.1
 */