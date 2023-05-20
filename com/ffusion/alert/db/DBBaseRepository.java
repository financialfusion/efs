package com.ffusion.alert.db;

import com.ffusion.alert.interfaces.AEDBParams;
import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.shared.AEResourceBundle;
import com.ffusion.alert.shared.AEUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public abstract class DBBaseRepository
  implements DBSQLConstants, IAEErrConstants
{
  protected static final int e9 = 2;
  private static final String e8 = "select version from ";
  private DBConnectionPool e6;
  private String e7;
  private Properties e4;
  private static AEResourceBundle e5 = new AEResourceBundle("com.ffusion.alert.db.lang.AEErrStrings");
  
  protected DBBaseRepository(AEDBParams paramAEDBParams, int paramInt, String paramString)
  {
    this.e6 = new DBConnectionPool(paramAEDBParams, paramInt);
    this.e7 = ("select " + paramString + ".currval from dual");
  }
  
  protected abstract String aR();
  
  protected abstract a[] aQ();
  
  public static String H(String paramString)
  {
    return e5.a(paramString);
  }
  
  public static String a(String paramString, Throwable paramThrowable)
  {
    return e5.a(paramString, paramThrowable);
  }
  
  public static String jdMethod_try(String paramString1, String paramString2)
  {
    return e5.a(paramString1, paramString2);
  }
  
  public static String a(String paramString1, String paramString2, Throwable paramThrowable)
  {
    return e5.a(paramString1, paramString2, paramThrowable);
  }
  
  public static String a(String paramString1, String paramString2, String paramString3)
  {
    return e5.a(paramString1, paramString2, paramString3);
  }
  
  public static String a(String paramString1, String paramString2, String paramString3, Throwable paramThrowable)
  {
    return e5.a(paramString1, paramString2, paramString3, paramThrowable);
  }
  
  public static AEResourceBundle aJ()
  {
    return e5;
  }
  
  public boolean aM()
  {
    try
    {
      aP();
      return true;
    }
    catch (AEException localAEException) {}
    return false;
  }
  
  public boolean i(DBConnection paramDBConnection)
  {
    try
    {
      j(paramDBConnection);
      return true;
    }
    catch (AEException localAEException) {}
    return false;
  }
  
  public int aP()
    throws AEException
  {
    DBConnection localDBConnection = this.e6.jdMethod_for();
    try
    {
      int i = j(localDBConnection);
      return i;
    }
    finally
    {
      this.e6.a(localDBConnection);
    }
  }
  
  public int j(DBConnection paramDBConnection)
    throws AEException
  {
    String str = aR();
    int i = 0;
    DBResultSet localDBResultSet = null;
    try
    {
      localDBResultSet = paramDBConnection.I("select version from " + str);
      localDBResultSet.jdMethod_try();
      localDBResultSet.a();
      i = localDBResultSet.jdMethod_int(1);
    }
    catch (SQLException localSQLException1)
    {
      throw new AEException(5, H("ERR_NO_REPOSITORY"), DBSqlUtils.a(localSQLException1));
    }
    finally
    {
      try
      {
        if (localDBResultSet != null) {
          localDBResultSet.jdMethod_for();
        }
      }
      catch (SQLException localSQLException2) {}
    }
    return i;
  }
  
  public void aN()
    throws AEException
  {
    f(null);
  }
  
  public void f(DBConnection paramDBConnection)
    throws AEException
  {
    int i;
    if (paramDBConnection == null) {
      i = aP();
    } else {
      i = j(paramDBConnection);
    }
    if (i < 2) {
      throw new AEException(6, H("ERR_REPOSITORY_TOO_OLD"));
    }
    if (i > 2) {
      throw new AEException(7, H("ERR_REPOSITORY_TOO_NEW"));
    }
  }
  
  private static void a(IDBProgress paramIDBProgress, String paramString)
  {
    if (paramIDBProgress != null) {
      paramIDBProgress.a(paramString);
    }
  }
  
  public void a(DBConnection paramDBConnection, IDBProgress paramIDBProgress)
    throws AEException
  {
    String str = aR();
    a[] arrayOfa = aQ();
    int i = paramDBConnection.aS().getConnectionType();
    if (i(paramDBConnection))
    {
      if (paramIDBProgress != null) {
        paramIDBProgress.jdMethod_if(2 * arrayOfa.length);
      }
      a(paramDBConnection, arrayOfa, arrayOfa.length, paramIDBProgress);
    }
    else if (paramIDBProgress != null)
    {
      paramIDBProgress.jdMethod_if(arrayOfa.length);
    }
    int j = 0;
    a(paramIDBProgress, H("MSG_CREATING_REP"));
    while (j < arrayOfa.length)
    {
      if (arrayOfa[j].a != null) {
        try
        {
          paramDBConnection.J(arrayOfa[j].a);
        }
        catch (SQLException localSQLException)
        {
          try
          {
            a(paramDBConnection, arrayOfa, j, null);
          }
          catch (AEException localAEException) {}
          throw new AEException(8, H("ERR_CREATING_REPOSITORY"), DBSqlUtils.a(localSQLException));
        }
      }
      j++;
      if (paramIDBProgress != null) {
        paramIDBProgress.a(1);
      }
    }
  }
  
  private void a(DBConnection paramDBConnection, a[] paramArrayOfa, int paramInt, IDBProgress paramIDBProgress)
    throws AEException
  {
    this.e4 = null;
    if (i(paramDBConnection))
    {
      a(paramIDBProgress, H("MSG_DELETING_REP"));
      while (paramInt > 0)
      {
        paramInt--;
        if (paramArrayOfa[paramInt].jdField_if != null) {
          try
          {
            paramDBConnection.J(paramArrayOfa[paramInt].jdField_if);
          }
          catch (SQLException localSQLException)
          {
            throw new AEException(9, H("ERR_DESTROYING_REPOSITORY"), DBSqlUtils.a(localSQLException));
          }
        }
        if (paramIDBProgress != null) {
          paramIDBProgress.a(1);
        }
      }
    }
  }
  
  public void jdMethod_if(DBConnection paramDBConnection, IDBProgress paramIDBProgress)
    throws AEException
  {
    a[] arrayOfa = aQ();
    if (paramIDBProgress != null) {
      paramIDBProgress.jdMethod_if(arrayOfa.length);
    }
    a(paramDBConnection, arrayOfa, arrayOfa.length, paramIDBProgress);
  }
  
  public String aO()
  {
    AEDBParams localAEDBParams = this.e6.a();
    String str1 = localAEDBParams.getDBName();
    String str2 = localAEDBParams.getMachine();
    String str3 = localAEDBParams.getPort();
    return str2 + ":" + str3 + " (" + str1 + ")";
  }
  
  int h(DBConnection paramDBConnection)
    throws SQLException
  {
    int i = 0;
    DBResultSet localDBResultSet = null;
    try
    {
      switch (paramDBConnection.aS().getConnectionType())
      {
      case 3: 
      case 6: 
        localDBResultSet = paramDBConnection.I("values identity_val_local()");
        break;
      case 4: 
        localDBResultSet = paramDBConnection.I(this.e7);
        break;
      case 1: 
      case 2: 
        localDBResultSet = paramDBConnection.I("select @@identity");
        break;
      case 5: 
        localDBResultSet = paramDBConnection.I("select @@identity");
        break;
      default: 
        localDBResultSet = null;
      }
      localDBResultSet.a(null);
      localDBResultSet.a();
      if ((paramDBConnection.aS().getConnectionType() == 1) || (paramDBConnection.aS().getConnectionType() == 2)) {
        i = localDBResultSet.jdMethod_else(1);
      } else {
        i = localDBResultSet.jdMethod_int(1);
      }
      int j = i;
      return j;
    }
    finally
    {
      try
      {
        if (localDBResultSet != null) {
          localDBResultSet.jdMethod_for();
        }
      }
      catch (SQLException localSQLException) {}
    }
  }
  
  public DBConnection aL()
    throws AEException
  {
    return this.e6.jdMethod_for();
  }
  
  public void k(DBConnection paramDBConnection)
    throws AEException
  {
    this.e6.a(paramDBConnection);
  }
  
  public DBConnection l(DBConnection paramDBConnection)
    throws AEException
  {
    return this.e6.jdMethod_if(paramDBConnection);
  }
  
  public void aK()
  {
    this.e6.jdMethod_if();
  }
  
  public int aI()
  {
    return this.e6.jdMethod_do();
  }
  
  public void x(int paramInt)
  {
    this.e6.jdMethod_if(paramInt);
  }
  
  public Properties g(DBConnection paramDBConnection)
    throws AEException
  {
    if (this.e4 != null) {
      return this.e4;
    }
    DBResultSet localDBResultSet = null;
    try
    {
      localDBResultSet = paramDBConnection.I("select properties from " + aR());
      localDBResultSet.jdMethod_try();
      if (localDBResultSet.a()) {
        this.e4 = AEUtils.jdMethod_if(localDBResultSet.jdMethod_do(1));
      }
      localDBResultSet.jdMethod_for();
    }
    catch (SQLException localSQLException1)
    {
      throw new AEException(1, DBSqlUtils.a(localSQLException1));
    }
    finally
    {
      try
      {
        if (localDBResultSet != null) {
          localDBResultSet.jdMethod_for();
        }
      }
      catch (SQLException localSQLException2) {}
    }
    return this.e4;
  }
  
  public boolean a(DBConnection paramDBConnection, Properties paramProperties)
    throws AEException
  {
    boolean bool1 = false;
    Object[] arrayOfObject1 = 0;
    try
    {
      bool1 = paramDBConnection.aU();
      if (bool1) {
        paramDBConnection.jdMethod_try(false);
      }
      String str = AEUtils.a(paramProperties);
      this.e4 = null;
      g(paramDBConnection);
      if (!this.e4.equals(paramProperties)) {
        arrayOfObject1 = 1;
      }
      boolean bool2 = DBSqlUtils.jdMethod_if(paramDBConnection.aS(), str);
      Object[] arrayOfObject2 = { bool2 ? " " : str, new Integer(2) };
      paramDBConnection.jdMethod_if("update " + aR() + " set properties=? where version=?", arrayOfObject2);
      if (bool2)
      {
        arrayOfObject3 = new Object[] { str };
        paramDBConnection.a("select properties from " + aR() + " where version=?", 2, arrayOfObject3);
      }
      if (bool1) {
        paramDBConnection.a0();
      }
      this.e4 = paramProperties;
      Object[] arrayOfObject3 = arrayOfObject1;
      return arrayOfObject3;
    }
    catch (IOException localIOException)
    {
      try
      {
        if (bool1) {
          paramDBConnection.a1();
        }
      }
      catch (SQLException localSQLException2) {}
      throw new AEException(1, localIOException);
    }
    catch (SQLException localSQLException1)
    {
      try
      {
        if (bool1) {
          paramDBConnection.a1();
        }
      }
      catch (SQLException localSQLException3) {}
      throw new AEException(1, DBSqlUtils.a(localSQLException1));
    }
    finally
    {
      try
      {
        if (bool1) {
          paramDBConnection.jdMethod_try(true);
        }
      }
      catch (SQLException localSQLException4)
      {
        throw new AEException(1, DBSqlUtils.a(localSQLException4));
      }
    }
  }
  
  protected static class a
  {
    public String a;
    public String jdField_if;
    
    a(String paramString1, String paramString2)
    {
      this.a = paramString1;
      this.jdField_if = paramString2;
    }
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.db.DBBaseRepository
 * JD-Core Version:    0.7.0.1
 */