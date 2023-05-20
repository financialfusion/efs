package com.ffusion.ffs.db;

import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

public class PrepareStmtCacheStresser
{
  FFSConnectionHolder jdField_if = null;
  FFSDBProperties a = null;
  
  public static void main(String[] paramArrayOfString)
  {
    try
    {
      PrepareStmtCacheStresser localPrepareStmtCacheStresser = new PrepareStmtCacheStresser();
      localPrepareStmtCacheStresser.go(paramArrayOfString);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public PrepareStmtCacheStresser()
  {
    init();
  }
  
  public void init()
  {
    try
    {
      Object localObject = null;
      Properties localProperties = loadProps("bpwdb.prop");
      FFSProperties localFFSProperties = new FFSProperties(localProperties);
      this.a = new FFSDBProperties(localFFSProperties);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      System.exit(1);
    }
  }
  
  public Properties loadProps(String paramString)
    throws FileNotFoundException
  {
    Properties localProperties = new Properties();
    InputStream localInputStream = null;
    try
    {
      ClassLoader localClassLoader = PrepareStmtCacheStresser.class.getClassLoader();
      if (localClassLoader == null) {
        localClassLoader = ClassLoader.getSystemClassLoader();
      }
      localInputStream = localClassLoader.getResourceAsStream(paramString);
      if (localInputStream == null) {
        throw new FileNotFoundException(paramString + " not found.");
      }
      localProperties.load(localInputStream);
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    finally
    {
      try
      {
        if (localInputStream != null) {
          localInputStream.close();
        }
        localInputStream = null;
      }
      catch (Exception localException2) {}
    }
    return localProperties;
  }
  
  public static void freeConnection(FFSConnection paramFFSConnection)
    throws Exception
  {
    try
    {
      paramFFSConnection.rollback();
      System.out.println("Transaction successfully rolled back ....");
    }
    catch (Throwable localThrowable)
    {
      System.out.println("Transaction rollback exception ....");
    }
    paramFFSConnection.pruneCache();
    paramFFSConnection.close();
  }
  
  a[] a()
  {
    a[] arrayOfa = new a[9];
    int i = 0;
    arrayOfa[i] = new a();
    arrayOfa[i].a = "UPDATE SCH_ScheduleInfo SET Status=? WHERE InstructionType=? AND Status=?";
    Object[] arrayOfObject1 = { "Active", "FUNDTRN", "Processing" };
    arrayOfa[i].jdField_do = arrayOfObject1;
    arrayOfa[i].jdField_for = 1;
    i++;
    arrayOfa[i] = new a();
    arrayOfa[i].a = "SELECT  STATUS FROM BPW_CustPayeeRoute  WHERE CustomerID=?";
    Object[] arrayOfObject2 = { "18" };
    arrayOfa[i].jdField_do = arrayOfObject2;
    arrayOfa[i].jdField_for = 2;
    i++;
    arrayOfa[i] = new a();
    arrayOfa[i].a = "SELECT SrvrTID FROM BPW_PmtInstruction WHERE CustomerID=? AND PayeeListID=? AND Status=?";
    Object[] arrayOfObject3 = { "18", "0", "FUNDSALLOCATED" };
    arrayOfa[i].jdField_do = arrayOfObject3;
    arrayOfa[i].jdField_for = 2;
    i++;
    arrayOfa[i] = new a();
    arrayOfa[i].a = "UPDATE BPW_PmtInstruction SET PayAcct=? WHERE SrvrTID=?";
    Object[] arrayOfObject4 = { "12345", "1" };
    arrayOfa[i].jdField_do = arrayOfObject4;
    arrayOfa[i].jdField_for = 1;
    i++;
    arrayOfa[i] = new a();
    arrayOfa[i].a = "UPDATE BPW_CustPayeeRoute SET RouteID=? WHERE CustomerID=?";
    Object[] arrayOfObject5 = { "1", "18" };
    arrayOfa[i].jdField_do = arrayOfObject5;
    arrayOfa[i].jdField_for = 1;
    i++;
    arrayOfa[i] = new a();
    arrayOfa[i].a = "SELECT  RouteID FROM BPW_CustPayeeRoute  WHERE CustomerID=?";
    Object[] arrayOfObject6 = { "18" };
    arrayOfa[i].jdField_do = arrayOfObject6;
    arrayOfa[i].jdField_for = 2;
    i++;
    arrayOfa[i] = new a();
    arrayOfa[i].a = "SELECT  Status FROM BPW_Payee WHERE PayeeID=?";
    Object[] arrayOfObject7 = { "INPROCESS", "0" };
    arrayOfa[i].jdField_do = arrayOfObject6;
    arrayOfa[i].jdField_for = 2;
    i++;
    arrayOfa[i] = new a();
    arrayOfa[i].a = "UPDATE BPW_Payee SET Status=? WHERE PayeeID=?";
    Object[] arrayOfObject8 = { "INPROCESS", "0" };
    arrayOfa[i].jdField_do = arrayOfObject8;
    arrayOfa[i].jdField_for = 1;
    i++;
    arrayOfa[i] = new a();
    arrayOfa[i].a = "UPDATE SCH_ScheduleInfo SET Status=? WHERE InstructionType=? AND Status=?";
    Object[] arrayOfObject9 = { "Active", "FUNDTRN", "Processing" };
    arrayOfa[i].jdField_do = arrayOfObject9;
    arrayOfa[i].jdField_for = 1;
    return arrayOfa;
  }
  
  public void go(String[] paramArrayOfString)
    throws Exception
  {
    this.jdField_if = new FFSConnectionHolder();
    this.jdField_if.conn = DBUtil.getConnection();
    try
    {
      a(this.jdField_if);
      this.jdField_if.conn.commit();
    }
    catch (Exception localException)
    {
      this.jdField_if.conn.rollback();
      localException.printStackTrace();
    }
    finally
    {
      DBUtil.freeConnection(this.jdField_if.conn);
    }
  }
  
  public void doDatabaseOperation(a parama, boolean paramBoolean)
  {
    try
    {
      String str = parama.a;
      Object[] arrayOfObject = parama.jdField_do;
      if (parama.jdField_for == 1) {
        doWriteOperation(this.jdField_if, str, arrayOfObject, paramBoolean);
      } else if (parama.jdField_for == 2) {
        doReadOperation(this.jdField_if, str, arrayOfObject, paramBoolean);
      }
      this.jdField_if.conn.commit();
    }
    catch (Exception localException)
    {
      this.jdField_if.conn.rollback();
      localException.printStackTrace();
    }
  }
  
  public void doWriteOperation(FFSConnectionHolder paramFFSConnectionHolder, String paramString, Object[] paramArrayOfObject, boolean paramBoolean)
  {
    try
    {
      if (paramBoolean)
      {
        FFSDebug.log("#########################Executing statement =" + paramString, 6);
        int i = DBUtil.executeStatement(paramFFSConnectionHolder, paramString, paramArrayOfObject);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void doReadOperation(FFSConnectionHolder paramFFSConnectionHolder, String paramString, Object[] paramArrayOfObject, boolean paramBoolean)
    throws Exception
  {
    FFSResultSet localFFSResultSet = null;
    try
    {
      if (paramBoolean)
      {
        FFSDebug.log("############################Executing statement =" + paramString, 6);
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, paramString, paramArrayOfObject);
      }
    }
    catch (Exception localException1)
    {
      throw localException1;
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
      catch (Exception localException2) {}
    }
  }
  
  public FFSConnectionHolder getDBConnection(FFSDBProperties paramFFSDBProperties)
    throws Exception
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = ConnectionFactory.getNewConnection(paramFFSDBProperties);
    localFFSConnectionHolder.conn.setAutoCommit(false);
    return localFFSConnectionHolder;
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str1 = "SELECT IndexName, Value FROM SCH_Locker WHERE Value=?";
    Object[] arrayOfObject = { "0" };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      int i = 0;
      a[] arrayOfa = a();
      while (localFFSResultSet.getNextRow() == true)
      {
        String str2 = localFFSResultSet.getColumnString("IndexName");
        String str3 = localFFSResultSet.getColumnString("Value");
        FFSDebug.log("idxName = " + str2);
        FFSDebug.log("value = " + str3);
        for (int j = 0; j < 1000; j++)
        {
          a locala = arrayOfa[(j % arrayOfa.length)];
          if (locala != null) {
            doDatabaseOperation(locala, true);
          }
        }
        FFSDebug.log("Getting record no " + i++);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log(localException1.toString());
      FFSDebug.stackTrace(localException1);
      throw localException1;
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
      catch (Exception localException2) {}
    }
  }
  
  class a
  {
    public static final int jdField_int = 1;
    public static final int jdField_if = 2;
    public String a;
    public Object[] jdField_do;
    public int jdField_for;
    
    a() {}
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.db.PrepareStmtCacheStresser
 * JD-Core Version:    0.7.0.1
 */