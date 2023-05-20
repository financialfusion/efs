package com.ffusion.alert.db;

import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.AEServerInfo;
import com.ffusion.alert.shared.AEResourceBundle;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public final class ServerInfo
  extends AEServerInfo
  implements IAEErrConstants
{
  public static final int ex = 0;
  public static final int eu = 1;
  private static final String et = "userName, password, serverURL, contextFactory, adminjndi, clientjndi, serverRole, pingTime, active ";
  private static final String el = "select id, userName, password, serverURL, contextFactory, adminjndi, clientjndi, serverRole, pingTime, active from AE_Cluster";
  private static final String ez = "insert into AE_Cluster( {|||id,|}userName, password, serverURL, contextFactory, adminjndi, clientjndi, serverRole, pingTime, active ) values({|||AE_Seq.nextval,|}?,?,?,?,?,?,?,?,?)";
  private static final String ev = "update AE_Cluster set userName = ?, password = ?, serverURL = ?, contextFactory = ?, adminjndi = ?, clientjndi= ?, serverRole = ?, pingTime = ?, active = ? where id = ?";
  private static final String es = "delete from AE_Cluster where id = ?";
  private static final String ew = "update AE_Cluster set serverRole = 2";
  private static final String em = "update AE_Cluster set serverRole = 1 where id = ?";
  private static final String en = "select id, userName, password, serverURL, contextFactory, adminjndi, clientjndi, serverRole, pingTime, active from AE_Cluster where active = 1";
  private static final String eF = "select id, userName, password, serverURL, contextFactory, adminjndi, clientjndi, serverRole, pingTime, active from AE_Cluster where id = ?";
  private static final String eA = "update AE_Cluster set active = 0";
  private static final String eq = "update AE_Cluster set active = 0 where id = ?";
  private static final String eG = "update AE_Cluster set active = 1 where id = ?";
  private static final String eD = "lock table AE_Cluster in exclusive mode";
  private static final String ey = "select id, userName, password, serverURL, contextFactory, adminjndi, clientjndi, serverRole, pingTime, active from AE_Cluster";
  private static final String ep = "select id, userName, password, serverURL, contextFactory, adminjndi, clientjndi, serverRole, pingTime, active from AE_Cluster where serverURL = ?";
  private static final String eE = "{SELECT GETDATE()|SELECT GETDATE()|Select current timestamp from AE_Repository|Select SYSDATE from DUAL |SELECT GETDATE()}";
  private static final String eH = "Update AE_Cluster set pingTime = ? where id = ?";
  private static final int eB = -1;
  private int er = -1;
  private long eI;
  private int eC;
  static AEResourceBundle eo = new AEResourceBundle("com.ffusion.alert.db.lang.AEErrStrings");
  
  public ServerInfo() {}
  
  public ServerInfo(AEServerInfo paramAEServerInfo)
  {
    this.ei = paramAEServerInfo.getUserName();
    this.ek = paramAEServerInfo.getPassword();
    this.ej = paramAEServerInfo.getServerURL();
    this.eg = paramAEServerInfo.getContextFactory();
    this.eh = paramAEServerInfo.getAdminJNDI();
    this.ee = paramAEServerInfo.getClientJNDI();
    this.ef = paramAEServerInfo.getServerRole();
  }
  
  public static long d(DBConnection paramDBConnection)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      localDBResultSet = paramDBConnection.I("{SELECT GETDATE()|SELECT GETDATE()|Select current timestamp from AE_Repository|Select SYSDATE from DUAL |SELECT GETDATE()}");
      localDBResultSet.jdMethod_try();
      long l1 = 0L;
      if (localDBResultSet.a())
      {
        Timestamp localTimestamp = localDBResultSet.jdMethod_long(1);
        l1 = localTimestamp.getTime();
      }
      long l2 = l1;
      return l2;
    }
    catch (SQLException localSQLException1)
    {
      throw new AEException(1, localSQLException1);
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
  }
  
  public final int aG()
  {
    return this.er;
  }
  
  final void v(int paramInt)
  {
    this.er = paramInt;
  }
  
  public final void t(int paramInt)
  {
    this.ef = paramInt;
  }
  
  public final long aH()
  {
    return this.eI;
  }
  
  public final void jdMethod_try(long paramLong)
  {
    this.eI = paramLong;
  }
  
  final void u(int paramInt)
  {
    this.eC = paramInt;
  }
  
  public final int aF()
  {
    return this.eC;
  }
  
  public final void jdMethod_long(DBConnection paramDBConnection)
    throws AEException
  {
    try
    {
      paramDBConnection.K("update AE_Cluster set active = 0");
      Object[] arrayOfObject = { new Integer(this.er) };
      paramDBConnection.jdMethod_if("update AE_Cluster set active = 1 where id = ?", arrayOfObject);
      this.eC = 1;
    }
    catch (SQLException localSQLException)
    {
      throw new AEException(1, DBSqlUtils.a(localSQLException));
    }
  }
  
  public static final ServerInfo jdMethod_void(DBConnection paramDBConnection)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      ServerInfo localServerInfo1 = null;
      localDBResultSet = paramDBConnection.I("select id, userName, password, serverURL, contextFactory, adminjndi, clientjndi, serverRole, pingTime, active from AE_Cluster where active = 1");
      localDBResultSet.jdMethod_try();
      if (localDBResultSet.a())
      {
        localServerInfo1 = new ServerInfo();
        a(localDBResultSet, localServerInfo1);
      }
      ServerInfo localServerInfo2 = localServerInfo1;
      return localServerInfo2;
    }
    catch (SQLException localSQLException1)
    {
      throw new AEException(1, localSQLException1);
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
  }
  
  public static final boolean jdMethod_null(DBConnection paramDBConnection)
    throws AEException
  {
    boolean bool = paramDBConnection.aU();
    try
    {
      if (bool) {
        paramDBConnection.jdMethod_try(false);
      }
      paramDBConnection.K("lock table AE_Cluster in exclusive mode");
      return bool;
    }
    catch (SQLException localSQLException1)
    {
      try
      {
        if (bool)
        {
          paramDBConnection.a1();
          paramDBConnection.jdMethod_try(true);
        }
      }
      catch (SQLException localSQLException2) {}
      throw new AEException(1, DBSqlUtils.a(localSQLException1));
    }
  }
  
  public static final void a(DBConnection paramDBConnection, boolean paramBoolean)
    throws AEException
  {
    try
    {
      paramDBConnection.a0();
      if (paramBoolean) {
        paramDBConnection.jdMethod_try(true);
      }
    }
    catch (SQLException localSQLException)
    {
      throw new AEException(1, DBSqlUtils.a(localSQLException));
    }
  }
  
  public final void b(DBConnection paramDBConnection)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      Object[] arrayOfObject = { new Integer(this.er) };
      localDBResultSet = paramDBConnection.I("select id, userName, password, serverURL, contextFactory, adminjndi, clientjndi, serverRole, pingTime, active from AE_Cluster where id = ?");
      localDBResultSet.a(arrayOfObject);
      localDBResultSet.a();
      a(localDBResultSet, this);
    }
    catch (SQLException localSQLException1)
    {
      throw new AEException(1, localSQLException1);
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
  }
  
  public static final ServerInfo a(String paramString, DBConnection paramDBConnection)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      localDBResultSet = paramDBConnection.I("select id, userName, password, serverURL, contextFactory, adminjndi, clientjndi, serverRole, pingTime, active from AE_Cluster");
      localDBResultSet.jdMethod_try();
      ServerInfo localServerInfo = null;
      while (localDBResultSet.a())
      {
        localObject1 = localDBResultSet.jdMethod_byte();
        if (paramString.equalsIgnoreCase(localObject1[3]))
        {
          localServerInfo = new ServerInfo();
          a(localDBResultSet, localServerInfo);
        }
      }
      Object localObject1 = localServerInfo;
      return localObject1;
    }
    catch (SQLException localSQLException1)
    {
      throw new AEException(1, localSQLException1);
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
  }
  
  public static AEServerInfo[] jdMethod_goto(DBConnection paramDBConnection)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      localDBResultSet = paramDBConnection.I("select id, userName, password, serverURL, contextFactory, adminjndi, clientjndi, serverRole, pingTime, active from AE_Cluster");
      localDBResultSet.jdMethod_try();
      ArrayList localArrayList = new ArrayList();
      while (localDBResultSet.a())
      {
        localObject1 = new ServerInfo();
        a(localDBResultSet, (ServerInfo)localObject1);
        localArrayList.add(localObject1);
      }
      Object localObject1 = new AEServerInfo[localArrayList.size()];
      localArrayList.toArray((Object[])localObject1);
      Object localObject2 = localObject1;
      return localObject2;
    }
    catch (SQLException localSQLException1)
    {
      throw new AEException(1, localSQLException1);
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
  }
  
  public void a(AERepository paramAERepository, DBConnection paramDBConnection, boolean paramBoolean)
    throws AEException
  {
    boolean bool = false;
    try
    {
      bool = paramDBConnection.aU();
      if ((paramBoolean) && (bool)) {
        paramDBConnection.jdMethod_try(false);
      }
      Object[] arrayOfObject = { new Timestamp(this.eI), new Integer(this.er) };
      paramDBConnection.jdMethod_if("Update AE_Cluster set pingTime = ? where id = ?", arrayOfObject);
      if ((paramBoolean) && (bool)) {
        paramDBConnection.a0();
      }
    }
    catch (SQLException localSQLException1)
    {
      try
      {
        if ((paramBoolean) && (bool)) {
          paramDBConnection.a1();
        }
      }
      catch (SQLException localSQLException2) {}
      throw new AEException(1, DBSqlUtils.a(localSQLException1));
    }
    finally
    {
      try
      {
        if ((paramBoolean) && (bool)) {
          paramDBConnection.jdMethod_try(true);
        }
      }
      catch (SQLException localSQLException3)
      {
        throw new AEException(1, DBSqlUtils.a(localSQLException3));
      }
    }
  }
  
  public void jdMethod_if(AERepository paramAERepository, DBConnection paramDBConnection, boolean paramBoolean)
    throws AEException
  {
    if ((this.eh == null) || (this.eh.equals(""))) {
      throw new AEException(15, eo.a("ERR_BAD_SERVER_INFO", eo.a("PARAM_SERVER_INFO_ADMIN_JNDI"), this.ej));
    }
    if ((this.ee == null) || (this.ee.equals(""))) {
      throw new AEException(15, eo.a("ERR_BAD_SERVER_INFO", eo.a("PARAM_SERVER_INFO_CLIENT_JNDI"), this.ej));
    }
    if ((this.eg == null) || (this.eg.equals(""))) {
      throw new AEException(15, eo.a("ERR_BAD_SERVER_INFO", eo.a("PARAM_SERVER_INFO_CTX_FACTORY"), this.ej));
    }
    boolean bool = false;
    if ((this.ej == null) || (this.ej.indexOf(':') == -1) || (this.ej.indexOf('/') == -1)) {
      throw new AEException(1032, eo.a("ERR_MALFORMED_SERVER_URL", this.ej));
    }
    try
    {
      bool = paramDBConnection.aU();
      if ((paramBoolean) && (bool)) {
        paramDBConnection.jdMethod_try(false);
      }
      Object[] arrayOfObject;
      if (this.er == -1)
      {
        arrayOfObject = new Object[] { this.ei, this.ek, this.ej, this.eg, this.eh, this.ee, new Integer(this.ef), new Timestamp(this.eI), new Integer(0) };
        paramDBConnection.jdMethod_if("insert into AE_Cluster( {|||id,|}userName, password, serverURL, contextFactory, adminjndi, clientjndi, serverRole, pingTime, active ) values({|||AE_Seq.nextval,|}?,?,?,?,?,?,?,?,?)", arrayOfObject);
        this.er = paramAERepository.h(paramDBConnection);
      }
      else
      {
        arrayOfObject = new Object[] { this.ei, this.ek, this.ej, this.eg, this.eh, this.ee, new Integer(this.ef), new Timestamp(this.eI), new Integer(this.eC), new Integer(this.er) };
        paramDBConnection.jdMethod_if("update AE_Cluster set userName = ?, password = ?, serverURL = ?, contextFactory = ?, adminjndi = ?, clientjndi= ?, serverRole = ?, pingTime = ?, active = ? where id = ?", arrayOfObject);
      }
      if (this.ef == 1) {
        jdMethod_char(paramDBConnection);
      }
      if ((paramBoolean) && (bool)) {
        paramDBConnection.a0();
      }
    }
    catch (SQLException localSQLException1)
    {
      try
      {
        if ((paramBoolean) && (bool)) {
          paramDBConnection.a1();
        }
      }
      catch (SQLException localSQLException2) {}
      throw new AEException(1, DBSqlUtils.a(localSQLException1));
    }
    finally
    {
      try
      {
        if ((paramBoolean) && (bool)) {
          paramDBConnection.jdMethod_try(true);
        }
      }
      catch (SQLException localSQLException3)
      {
        throw new AEException(1, DBSqlUtils.a(localSQLException3));
      }
    }
  }
  
  public boolean c(DBConnection paramDBConnection)
    throws AEException
  {
    boolean bool1 = paramDBConnection.aU();
    try
    {
      if (bool1) {
        paramDBConnection.jdMethod_try(false);
      }
      if (this.er == -1)
      {
        boolean bool2 = false;
        return bool2;
      }
      Object[] arrayOfObject = { new Integer(this.er) };
      paramDBConnection.jdMethod_if("delete from AE_Cluster where id = ?", arrayOfObject);
      this.er = -1;
      if (bool1) {
        paramDBConnection.a0();
      }
    }
    catch (SQLException localSQLException1)
    {
      try
      {
        if (bool1) {
          paramDBConnection.a1();
        }
      }
      catch (SQLException localSQLException2) {}
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
      catch (SQLException localSQLException3)
      {
        throw new AEException(1, DBSqlUtils.a(localSQLException3));
      }
    }
    return true;
  }
  
  public boolean jdMethod_char(DBConnection paramDBConnection)
    throws AEException
  {
    boolean bool1 = paramDBConnection.aU();
    try
    {
      if (bool1) {
        paramDBConnection.jdMethod_try(false);
      }
      if (this.er == -1)
      {
        boolean bool2 = false;
        return bool2;
      }
      paramDBConnection.K("update AE_Cluster set serverRole = 2");
      Object[] arrayOfObject = { new Integer(this.er) };
      paramDBConnection.jdMethod_if("update AE_Cluster set serverRole = 1 where id = ?", arrayOfObject);
      this.ef = 1;
      if (bool1) {
        paramDBConnection.a0();
      }
    }
    catch (SQLException localSQLException1)
    {
      try
      {
        if (bool1) {
          paramDBConnection.a1();
        }
      }
      catch (SQLException localSQLException2) {}
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
      catch (SQLException localSQLException3)
      {
        throw new AEException(1, DBSqlUtils.a(localSQLException3));
      }
    }
    return true;
  }
  
  public boolean jdMethod_else(DBConnection paramDBConnection)
    throws AEException
  {
    boolean bool = paramDBConnection.aU();
    try
    {
      if (bool) {
        paramDBConnection.jdMethod_try(false);
      }
      Object[] arrayOfObject = { new Integer(this.er) };
      paramDBConnection.jdMethod_if("update AE_Cluster set active = 0 where id = ?", arrayOfObject);
      if (bool) {
        paramDBConnection.a0();
      }
    }
    catch (SQLException localSQLException1)
    {
      try
      {
        if (bool) {
          paramDBConnection.a1();
        }
      }
      catch (SQLException localSQLException2) {}
      throw new AEException(1, DBSqlUtils.a(localSQLException1));
    }
    finally
    {
      try
      {
        if (bool) {
          paramDBConnection.jdMethod_try(true);
        }
      }
      catch (SQLException localSQLException3)
      {
        throw new AEException(1, DBSqlUtils.a(localSQLException3));
      }
    }
    return true;
  }
  
  private static void a(DBResultSet paramDBResultSet, ServerInfo paramServerInfo)
    throws SQLException
  {
    paramServerInfo.v(paramDBResultSet.jdMethod_int(1));
    paramServerInfo.setUserName(paramDBResultSet.jdMethod_try(2));
    paramServerInfo.setPassword(paramDBResultSet.jdMethod_try(3));
    paramServerInfo.setServerURL(paramDBResultSet.jdMethod_try(4));
    paramServerInfo.setContextFactory(paramDBResultSet.jdMethod_try(5));
    paramServerInfo.setAdminJNDI(paramDBResultSet.jdMethod_try(6));
    paramServerInfo.setClientJNDI(paramDBResultSet.jdMethod_try(7));
    paramServerInfo.t(paramDBResultSet.jdMethod_int(8));
    paramServerInfo.jdMethod_try(paramDBResultSet.jdMethod_long(9).getTime());
    paramServerInfo.u(paramDBResultSet.jdMethod_int(10));
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.db.ServerInfo
 * JD-Core Version:    0.7.0.1
 */