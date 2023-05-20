package com.ffusion.alert.engine;

import com.ffusion.alert.adminEJB.IAEAlertAdmin;
import com.ffusion.alert.adminEJB.IAEAlertAdminHome;
import com.ffusion.alert.clientEJB.IAEAlertClient;
import com.ffusion.alert.clientEJB.IAEAlertClientHome;
import com.ffusion.alert.db.AERepository;
import com.ffusion.alert.db.AlertInstance;
import com.ffusion.alert.db.AlertRecovery;
import com.ffusion.alert.db.AppInfoCache;
import com.ffusion.alert.db.ChannelInfo;
import com.ffusion.alert.db.DBConnection;
import com.ffusion.alert.db.DBSqlUtils;
import com.ffusion.alert.db.DeliveryInfo;
import com.ffusion.alert.db.ServerInfo;
import com.ffusion.alert.interfaces.AEApplicationInfo;
import com.ffusion.alert.interfaces.AEDBParams;
import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.AEScheduleInfo;
import com.ffusion.alert.interfaces.AEServerInfo;
import com.ffusion.alert.interfaces.IAEAlertDefinition;
import com.ffusion.alert.interfaces.IAEAlertEngine;
import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEAuditInfo;
import com.ffusion.alert.interfaces.IAEChannelInfo;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.alert.interfaces.IAEPropsConstants;
import com.ffusion.alert.shared.AELog;
import com.ffusion.alert.shared.AELogParams;
import com.ffusion.alert.shared.AEResourceBundle;
import com.ffusion.alert.shared.AEUtils;
import com.ffusion.alert.shared.AlertVersion;
import com.ffusion.alert.shared.IUnaryPredicate;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.TimeZone;
import java.util.TreeSet;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

public class AlertEngine
  implements IAEAlertEngine, IAEErrConstants, IAEPropsConstants
{
  public static final int cf = 1;
  public static final int b4 = 10;
  public static final int cb = 3;
  public static final String ch = "DBCleanup";
  public static final int ci = 0;
  public static final int b8 = 1;
  public static final int ce = 2;
  public static final int cj = 3;
  private int cg = 0;
  private AERepository b7;
  private AuditEngine cc;
  private SchedulerEngine b3;
  private DeliveryEngine b9;
  private AEConfigManager ck;
  private ClusterHeartbeat cd;
  private AEDBParams b5;
  private ServerInfo ca;
  private static final IUnaryPredicate b6 = new IUnaryPredicate()
  {
    public boolean a(Object paramAnonymousObject)
    {
      return true;
    }
  };
  private static final a b2 = new a(null);
  
  public AlertEngine(AEDBParams paramAEDBParams)
    throws AEException
  {
    this.b5 = paramAEDBParams;
    this.b7 = new AERepository(paramAEDBParams, 10);
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      this.b7.f(localDBConnection);
      this.ck = new AEConfigManager(this.b7, this);
      this.ck.jdMethod_if(localDBConnection);
      AELogParams localAELogParams = this.ck.O();
      AELog.a(localAELogParams);
      AELog.a(AlertVersion.a(), 0);
      this.b7.x(this.ck.L());
      this.cc = new AuditEngine(this.b7, this.ck, this.ck.S());
    }
    catch (AEException localAEException)
    {
      this.b7.k(localDBConnection);
      this.b7.aK();
      throw localAEException;
    }
    this.b7.k(localDBConnection);
  }
  
  public void jdMethod_new(String paramString)
    throws AEException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new AEException(1030, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_SERVER_URL")));
    }
    DBConnection localDBConnection = this.b7.aL();
    ServerInfo localServerInfo;
    try
    {
      localServerInfo = ServerInfo.a(paramString, localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (localServerInfo == null) {
      throw new AEException(1030, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString));
    }
    IAEAlertAdmin localIAEAlertAdmin = null;
    try
    {
      localIAEAlertAdmin = jdMethod_if(localServerInfo);
    }
    catch (Exception localException1)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localException1);
    }
    try
    {
      localIAEAlertAdmin.init(this.b5);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localRemoteException);
    }
    finally
    {
      try
      {
        localIAEAlertAdmin.remove();
      }
      catch (Exception localException3) {}
    }
  }
  
  public void w()
    throws AEException
  {
    int i = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    DBConnection localDBConnection = this.b7.aL();
    AEServerInfo[] arrayOfAEServerInfo = null;
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    int j = 0;
    label239:
    while (j < arrayOfAEServerInfo.length)
    {
      ServerInfo localServerInfo = (ServerInfo)arrayOfAEServerInfo[j];
      IAEAlertAdmin localIAEAlertAdmin = null;
      try
      {
        localIAEAlertAdmin = jdMethod_if(localServerInfo);
      }
      catch (Exception localException1)
      {
        AELog.a(localException1, "Unable to init server", 0);
        i = 1;
        localStringBuffer.append(localServerInfo.getServerURL());
        localStringBuffer.append("\n");
        break label239;
      }
      try
      {
        if (!localIAEAlertAdmin.isInitialized()) {
          localIAEAlertAdmin.init(this.b5);
        }
        try
        {
          localIAEAlertAdmin.remove();
        }
        catch (Exception localException2)
        {
          AELog.a(localException2, "Unable to init server", 0);
        }
        j++;
      }
      catch (Exception localException3)
      {
        AELog.a(localException3, "Unable to init server", 0);
        i = 1;
        localStringBuffer.append(localServerInfo.getServerURL());
        localStringBuffer.append("\n");
      }
      finally
      {
        try
        {
          localIAEAlertAdmin.remove();
        }
        catch (Exception localException5)
        {
          AELog.a(localException5, "Unable to init server", 0);
        }
      }
    }
    if (i != 0) {
      throw new AEException(2002, AEInstance.a.a("ERR_OPERATION_NOT_COMPLETED", AEInstance.a.a("OPERATION_INIT"), localStringBuffer.toString()));
    }
  }
  
  public static void a(AEDBParams paramAEDBParams)
    throws AEException
  {
    AERepository localAERepository = new AERepository(paramAEDBParams, 1);
    DBConnection localDBConnection = localAERepository.aL();
    try
    {
      localAERepository.f(localDBConnection);
      localAERepository.jdMethod_if(localDBConnection, null);
    }
    finally
    {
      localAERepository.k(localDBConnection);
      localAERepository.aK();
    }
  }
  
  public static void jdMethod_if(AEDBParams paramAEDBParams)
    throws AEException
  {
    AERepository localAERepository = new AERepository(paramAEDBParams, 1);
    DBConnection localDBConnection = localAERepository.aL();
    try
    {
      localAERepository.f(localDBConnection);
    }
    finally
    {
      localAERepository.k(localDBConnection);
      localAERepository.aK();
    }
  }
  
  public static void a(AEDBParams paramAEDBParams, boolean paramBoolean)
    throws AEException
  {
    AERepository localAERepository = new AERepository(paramAEDBParams, 1);
    DBConnection localDBConnection = localAERepository.aL();
    try
    {
      try
      {
        localAERepository.f(localDBConnection);
        if (!paramBoolean) {
          throw new AEException(1010, AEInstance.a.a("ERR_REPOSITORY_EXISTS"));
        }
      }
      catch (AEException localAEException1)
      {
        if (localAEException1.getErrorCode() != 5) {
          throw localAEException1;
        }
      }
      localAERepository.a(localDBConnection, null);
      try
      {
        localDBConnection.jdMethod_try(false);
        AEApplicationInfo localAEApplicationInfo = new AEApplicationInfo("Universal Alert Engine", "Universal Alert Engine");
        AppInfoCache localAppInfoCache = new AppInfoCache();
        int i = localAppInfoCache.a(localAERepository, localDBConnection, localAEApplicationInfo, true);
        ChannelInfo localChannelInfo = new ChannelInfo();
        localChannelInfo.setChannelName("UAE Admin Channel");
        localChannelInfo.setClassName("com.ffusion.alert.engine.InternalAdminChannel");
        localChannelInfo.jdMethod_do(1);
        localChannelInfo.setNumWorkers(1);
        localChannelInfo.jdMethod_int(i);
        localChannelInfo.jdMethod_if(true);
        localChannelInfo.a(localAERepository, localDBConnection);
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.set(11, 3);
        localCalendar.set(12, 0);
        localCalendar.set(13, 0);
        localCalendar.set(14, 0);
        localCalendar.add(5, 1);
        AEScheduleInfo localAEScheduleInfo = new AEScheduleInfo(localCalendar.getTime().getTime(), 9223372036854775807L, 86400000L, 0, localCalendar.getTimeZone(), true);
        DeliveryInfo[] arrayOfDeliveryInfo = { new DeliveryInfo() };
        arrayOfDeliveryInfo[0].setDeliveryOrder(1);
        arrayOfDeliveryInfo[0].jdMethod_if(localChannelInfo.getId());
        arrayOfDeliveryInfo[0].setDeliveryProperties(new Properties());
        arrayOfDeliveryInfo[0].setMaxDelay(3600000L);
        AlertInstance localAlertInstance = new AlertInstance();
        localAlertInstance.D("DBCleanup");
        localAlertInstance.m(2);
        localAlertInstance.r(-2147483648);
        localAlertInstance.n(i);
        localAlertInstance.jdMethod_if(AEUtils.a(new RepositoryCleaner()));
        localAlertInstance.jdMethod_if(localAEScheduleInfo);
        localAlertInstance.jdMethod_if(arrayOfDeliveryInfo);
        localAlertInstance.jdMethod_new(localAEScheduleInfo.getStart());
        localAlertInstance.jdMethod_if(localAERepository, localDBConnection);
        localDBConnection.a0();
        try
        {
          localDBConnection.jdMethod_try(true);
        }
        catch (SQLException localSQLException1) {}
        localAERepository.k(localDBConnection);
      }
      catch (SQLException localSQLException2)
      {
        try
        {
          localDBConnection.a1();
        }
        catch (SQLException localSQLException3) {}
        throw new AEException(1, DBSqlUtils.a(localSQLException2));
      }
      catch (AEException localAEException2)
      {
        try
        {
          localDBConnection.a1();
        }
        catch (SQLException localSQLException4) {}
        throw localAEException2;
      }
      finally
      {
        try
        {
          localDBConnection.jdMethod_try(true);
        }
        catch (SQLException localSQLException5) {}
      }
      localAERepository.aK();
    }
    finally
    {
      localAERepository.k(localDBConnection);
      localAERepository.aK();
    }
  }
  
  public void jdMethod_try(String paramString)
    throws AEException
  {
    if (paramString == null) {
      throw new AEException(1030, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_SERVER_URL")));
    }
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      this.ca = ServerInfo.a(paramString, localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (this.ca == null) {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString));
    }
    b();
  }
  
  public void i(String paramString)
    throws AEException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new AEException(1030, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_SERVER_URL")));
    }
    DBConnection localDBConnection = this.b7.aL();
    ServerInfo localServerInfo = null;
    try
    {
      localServerInfo = ServerInfo.a(paramString, localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (localServerInfo == null) {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString));
    }
    if (localServerInfo.equals(this.ca))
    {
      n();
    }
    else
    {
      IAEAlertAdmin localIAEAlertAdmin = null;
      try
      {
        localIAEAlertAdmin = jdMethod_if(localServerInfo);
      }
      catch (Exception localException1)
      {
        throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString), localException1);
      }
      try
      {
        localIAEAlertAdmin.start(localServerInfo.getServerURL());
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString), localRemoteException);
      }
      finally
      {
        try
        {
          localIAEAlertAdmin.remove();
        }
        catch (Exception localException3) {}
      }
    }
  }
  
  public void s()
    throws AEException
  {
    int i = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    DBConnection localDBConnection = this.b7.aL();
    AEServerInfo[] arrayOfAEServerInfo = null;
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    int j = 0;
    label249:
    while (j < arrayOfAEServerInfo.length)
    {
      ServerInfo localServerInfo = (ServerInfo)arrayOfAEServerInfo[j];
      if (localServerInfo.equals(this.ca))
      {
        n();
      }
      else
      {
        IAEAlertAdmin localIAEAlertAdmin = null;
        try
        {
          localIAEAlertAdmin = jdMethod_if(localServerInfo);
        }
        catch (Exception localException1)
        {
          AELog.a(localException1, "Unable to start server", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
          break label249;
        }
        try
        {
          localIAEAlertAdmin.start(localServerInfo.getServerURL());
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException2)
          {
            AELog.a(localException2, "Unable to start server", 0);
          }
          j++;
        }
        catch (Exception localException3)
        {
          AELog.a(localException3, "Unable to start server", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
        }
        finally
        {
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException5)
          {
            AELog.a(localException5, "Unable to start server", 0);
          }
        }
      }
    }
    if (i != 0) {
      throw new AEException(2002, AEInstance.a.a("ERR_OPERATION_NOT_COMPLETED", AEInstance.a.a("OPERATION_START"), localStringBuffer.toString()));
    }
  }
  
  private IAEAlertAdmin jdMethod_if(ServerInfo paramServerInfo)
    throws AEException
  {
    try
    {
      IAEAlertAdmin localIAEAlertAdmin = null;
      boolean bool = false;
      while (localIAEAlertAdmin == null) {
        try
        {
          Context localContext = AEUtils.a(paramServerInfo.getContextFactory(), paramServerInfo.getServerURL(), paramServerInfo.getUserName(), paramServerInfo.getPassword(), bool);
          IAEAlertAdminHome localIAEAlertAdminHome;
          if (paramServerInfo.getContextFactory().equals("weblogic.jndi.WLInitialContextFactory"))
          {
            localIAEAlertAdminHome = (IAEAlertAdminHome)localContext.lookup(paramServerInfo.getAdminJNDI());
          }
          else
          {
            Object localObject = localContext.lookup(paramServerInfo.getAdminJNDI());
            localIAEAlertAdminHome = (IAEAlertAdminHome)PortableRemoteObject.narrow(localObject, IAEAlertAdminHome.class);
          }
          localIAEAlertAdmin = localIAEAlertAdminHome.create();
        }
        catch (RemoteException localRemoteException)
        {
          if (bool) {
            throw localRemoteException;
          }
          bool = true;
        }
      }
      return localIAEAlertAdmin;
    }
    catch (Exception localException)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramServerInfo.getServerURL()), localException);
    }
  }
  
  private IAEAlertClient a(ServerInfo paramServerInfo)
    throws AEException
  {
    try
    {
      IAEAlertClient localIAEAlertClient = null;
      boolean bool = false;
      while (localIAEAlertClient == null) {
        try
        {
          Context localContext = AEUtils.a(paramServerInfo.getContextFactory(), paramServerInfo.getServerURL(), paramServerInfo.getUserName(), paramServerInfo.getPassword(), bool);
          IAEAlertClientHome localIAEAlertClientHome;
          if (paramServerInfo.getContextFactory().equals("weblogic.jndi.WLInitialContextFactory"))
          {
            localIAEAlertClientHome = (IAEAlertClientHome)localContext.lookup(paramServerInfo.getClientJNDI());
          }
          else
          {
            Object localObject = localContext.lookup(paramServerInfo.getClientJNDI());
            localIAEAlertClientHome = (IAEAlertClientHome)PortableRemoteObject.narrow(localObject, IAEAlertClientHome.class);
          }
          localIAEAlertClient = localIAEAlertClientHome.create();
        }
        catch (RemoteException localRemoteException)
        {
          if (bool) {
            throw localRemoteException;
          }
          bool = true;
        }
      }
      return localIAEAlertClient;
    }
    catch (Exception localException)
    {
      throw new AEException(3001, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramServerInfo.getServerURL()), localException);
    }
  }
  
  private ServerInfo a(ServerInfo paramServerInfo, boolean paramBoolean)
    throws AEException
  {
    if (paramServerInfo.getUserName() == null) {
      paramServerInfo.setUserName("");
    }
    if (paramServerInfo.getPassword() == null) {
      paramServerInfo.setPassword("");
    }
    return paramServerInfo;
  }
  
  private void F()
    throws AEException
  {
    if (this.cd == null) {
      return;
    }
    this.cd.ah();
    this.cd = null;
  }
  
  public void jdMethod_goto(String paramString)
    throws AEException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new AEException(1030, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_SERVER_URL")));
    }
    DBConnection localDBConnection = this.b7.aL();
    ServerInfo localServerInfo = null;
    try
    {
      localServerInfo = ServerInfo.a(paramString, localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (localServerInfo == null) {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString));
    }
    if (localServerInfo.equals(this.ca))
    {
      g();
    }
    else
    {
      IAEAlertAdmin localIAEAlertAdmin = null;
      try
      {
        localIAEAlertAdmin = jdMethod_if(localServerInfo);
      }
      catch (Exception localException1)
      {
        throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString), localException1);
      }
      try
      {
        localIAEAlertAdmin.stop();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString), localRemoteException);
      }
      finally
      {
        try
        {
          localIAEAlertAdmin.remove();
        }
        catch (Exception localException3) {}
      }
    }
  }
  
  public void e()
    throws AEException
  {
    int i = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    DBConnection localDBConnection = this.b7.aL();
    AEServerInfo[] arrayOfAEServerInfo = null;
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    int j = 0;
    label244:
    while (j < arrayOfAEServerInfo.length)
    {
      ServerInfo localServerInfo = (ServerInfo)arrayOfAEServerInfo[j];
      if (localServerInfo.equals(this.ca))
      {
        g();
      }
      else
      {
        IAEAlertAdmin localIAEAlertAdmin = null;
        try
        {
          localIAEAlertAdmin = jdMethod_if(localServerInfo);
        }
        catch (Exception localException1)
        {
          AELog.a(localException1, "Unable to stop server", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
          break label244;
        }
        try
        {
          localIAEAlertAdmin.stop();
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException2)
          {
            AELog.a(localException2, "Unable to stop server", 0);
          }
          j++;
        }
        catch (Exception localException3)
        {
          AELog.a(localException3, "Unable to stop server", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
        }
        finally
        {
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException5)
          {
            AELog.a(localException5, "Unable to stop server", 0);
          }
        }
      }
    }
    if (i != 0) {
      throw new AEException(2002, AEInstance.a.a("ERR_OPERATION_NOT_COMPLETED", AEInstance.a.a("OPERATION_STOP"), localStringBuffer.toString()));
    }
  }
  
  public void jdMethod_char(String paramString)
    throws AEException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new AEException(1030, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_SERVER_URL")));
    }
    DBConnection localDBConnection = this.b7.aL();
    ServerInfo localServerInfo = null;
    try
    {
      localServerInfo = ServerInfo.a(paramString, localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (localServerInfo == null) {
      throw new AEException(1030, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString));
    }
    if (localServerInfo.equals(this.ca))
    {
      y();
    }
    else
    {
      IAEAlertAdmin localIAEAlertAdmin = null;
      try
      {
        localIAEAlertAdmin = jdMethod_if(localServerInfo);
      }
      catch (Exception localException1)
      {
        throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString), localException1);
      }
      try
      {
        localIAEAlertAdmin.suspend();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString), localRemoteException);
      }
      finally
      {
        try
        {
          localIAEAlertAdmin.remove();
        }
        catch (Exception localException3) {}
      }
    }
  }
  
  public void t()
    throws AEException
  {
    int i = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    DBConnection localDBConnection = this.b7.aL();
    AEServerInfo[] arrayOfAEServerInfo = null;
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    int j = 0;
    label244:
    while (j < arrayOfAEServerInfo.length)
    {
      ServerInfo localServerInfo = (ServerInfo)arrayOfAEServerInfo[j];
      if (localServerInfo.equals(this.ca))
      {
        y();
      }
      else
      {
        IAEAlertAdmin localIAEAlertAdmin = null;
        try
        {
          localIAEAlertAdmin = jdMethod_if(localServerInfo);
        }
        catch (Exception localException1)
        {
          AELog.a(localException1, "Unable to suspend server", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
          break label244;
        }
        try
        {
          localIAEAlertAdmin.suspend();
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException2)
          {
            AELog.a(localException2, "Unable to stop server", 0);
          }
          j++;
        }
        catch (Exception localException3)
        {
          AELog.a(localException3, "Unable to stop server", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
        }
        finally
        {
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException5)
          {
            AELog.a(localException5, "Unable to stop server", 0);
          }
        }
      }
    }
    if (i != 0) {
      throw new AEException(2002, AEInstance.a.a("ERR_OPERATION_NOT_COMPLETED", AEInstance.a.a("OPERATION_SUSPEND"), localStringBuffer.toString()));
    }
  }
  
  public void jdMethod_case(String paramString)
    throws AEException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new AEException(1030, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_SERVER_URL")));
    }
    DBConnection localDBConnection = this.b7.aL();
    ServerInfo localServerInfo = null;
    try
    {
      localServerInfo = ServerInfo.a(paramString, localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (localServerInfo == null) {
      throw new AEException(1030, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString));
    }
    if (localServerInfo.equals(this.ca))
    {
      K();
    }
    else
    {
      IAEAlertAdmin localIAEAlertAdmin = null;
      try
      {
        localIAEAlertAdmin = jdMethod_if(localServerInfo);
      }
      catch (Exception localException1)
      {
        throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString), localException1);
      }
      try
      {
        localIAEAlertAdmin.resume();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString), localRemoteException);
      }
      finally
      {
        try
        {
          localIAEAlertAdmin.remove();
        }
        catch (Exception localException3) {}
      }
    }
  }
  
  public void f()
    throws AEException
  {
    int i = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    DBConnection localDBConnection = this.b7.aL();
    AEServerInfo[] arrayOfAEServerInfo = null;
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    int j = 0;
    label244:
    while (j < arrayOfAEServerInfo.length)
    {
      ServerInfo localServerInfo = (ServerInfo)arrayOfAEServerInfo[j];
      if (localServerInfo.equals(this.ca))
      {
        K();
      }
      else
      {
        IAEAlertAdmin localIAEAlertAdmin = null;
        try
        {
          localIAEAlertAdmin = jdMethod_if(localServerInfo);
        }
        catch (Exception localException1)
        {
          AELog.a(localException1, "Unable to resume server", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
          break label244;
        }
        try
        {
          localIAEAlertAdmin.resume();
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException2)
          {
            AELog.a(localException2, "Unable to resume server", 0);
          }
          j++;
        }
        catch (Exception localException3)
        {
          AELog.a(localException3, "Unable to resume server", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
        }
        finally
        {
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException5)
          {
            AELog.a(localException5, "Unable to resume server", 0);
          }
        }
      }
    }
    if (i != 0) {
      throw new AEException(2002, AEInstance.a.a("ERR_OPERATION_NOT_COMPLETED", AEInstance.a.a("OPERATION_RESUME"), localStringBuffer.toString()));
    }
  }
  
  public boolean jdMethod_null(String paramString)
    throws AEException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new AEException(1030, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_SERVER_URL")));
    }
    DBConnection localDBConnection = this.b7.aL();
    ServerInfo localServerInfo = null;
    try
    {
      localServerInfo = ServerInfo.a(paramString, localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (localServerInfo.equals(this.ca))
    {
      J();
      return true;
    }
    IAEAlertAdmin localIAEAlertAdmin = null;
    try
    {
      localIAEAlertAdmin = jdMethod_if(localServerInfo);
    }
    catch (Exception localException1)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString), localException1);
    }
    try
    {
      localIAEAlertAdmin.shutdown();
      boolean bool = false;
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString), localRemoteException);
    }
    finally
    {
      try
      {
        localIAEAlertAdmin.remove();
      }
      catch (Exception localException3) {}
    }
  }
  
  public void D()
    throws AEException
  {
    int i = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    DBConnection localDBConnection = this.b7.aL();
    AEServerInfo[] arrayOfAEServerInfo = null;
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    J();
    int j = 0;
    label244:
    while (j < arrayOfAEServerInfo.length)
    {
      ServerInfo localServerInfo = (ServerInfo)arrayOfAEServerInfo[j];
      if (!localServerInfo.equals(this.ca))
      {
        IAEAlertAdmin localIAEAlertAdmin = null;
        try
        {
          localIAEAlertAdmin = jdMethod_if(localServerInfo);
        }
        catch (Exception localException1)
        {
          AELog.a(localException1, "Unable to shutdown server", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
          break label244;
        }
        try
        {
          localIAEAlertAdmin.shutdown();
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException2)
          {
            AELog.a(localException2, "Unable to shutdown server", 0);
          }
          j++;
        }
        catch (Exception localException3)
        {
          AELog.a(localException3, "Unable to shutdown server", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
        }
        finally
        {
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException5)
          {
            AELog.a(localException5, "Unable to shutdown server", 0);
          }
        }
      }
    }
    if (i != 0) {
      throw new AEException(2002, AEInstance.a.a("ERR_OPERATION_NOT_COMPLETED", AEInstance.a.a("OPERATION_SHUTDOWN"), localStringBuffer.toString()));
    }
  }
  
  private void i()
    throws AEException
  {
    if (this.cd == null) {
      throw new AEException(1023, AEInstance.a.a("ERR_INCORRECT_STATE", AEInstance.a.a("PARAM_STATE_STARTED"), AEInstance.a.a("PARAM_STATE_SUSPENDED")));
    }
    this.cd.ac();
  }
  
  private void A()
    throws AEException
  {
    if ((this.cd != null) && (!this.cd.aj())) {
      return;
    }
    if (this.cd == null) {
      throw new AEException(1024, AEInstance.a.a("ERR_INCORRECT_STATE", AEInstance.a.a("PARAM_STATE_SUSPENDED"), AEInstance.a.a("PARAM_STATE_RESUMED")));
    }
    this.cd.ad();
  }
  
  void u()
    throws AEException
  {
    if (this.b3 != null) {
      this.b3.jdMethod_int(false);
    }
    this.b3 = new SchedulerEngine(this.b7, this.ck, this.b9, this.ck.M(), this.ck.W());
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      a(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    this.b3.jdMethod_new(false);
    this.cg = 1;
  }
  
  private boolean c()
  {
    return (this.cd != null) && (!this.cd.aj());
  }
  
  private boolean z()
  {
    if (this.cd != null) {
      return this.cd.aj();
    }
    return false;
  }
  
  private boolean H()
  {
    return this.cd == null;
  }
  
  private void b()
    throws AEException
  {
    if (c()) {
      return;
    }
    if (z()) {
      throw new AEException(1022, AEInstance.a.a("ERR_INCORRECT_STATE", AEInstance.a.a("PARAM_STATE_STOPPED"), AEInstance.a.a("PARAM_STATE_STARTED")));
    }
    this.cd = new ClusterHeartbeat(this.b7, this, this.ca, this.ck.M(), this.ck.Q());
    this.cd.ag();
  }
  
  public void n()
    throws AEException
  {
    if (this.ca != null) {
      b();
    } else {
      k();
    }
  }
  
  synchronized void jdMethod_null()
    throws AEException
  {
    if (this.cg == 1) {
      return;
    }
    if (this.cg != 0) {
      throw new AEException(1022, AEInstance.a.a("ERR_INCORRECT_STATE", AEInstance.a.a("PARAM_STATE_STOPPED"), AEInstance.a.a("PARAM_STATE_STARTED")));
    }
    try
    {
      this.b9 = new DeliveryEngine(this.b7, this.cc, this);
      this.ck.a(this.b9);
      DBConnection localDBConnection = this.b7.aL();
      this.ck.jdMethod_for(localDBConnection);
      this.b7.k(localDBConnection);
      this.cc.jdMethod_char();
    }
    catch (AEException localAEException1)
    {
      try
      {
        a(true);
      }
      catch (AEException localAEException2) {}
      throw localAEException1;
    }
  }
  
  synchronized void k()
    throws AEException
  {
    if (this.cg == 1) {
      return;
    }
    if (this.cg != 0) {
      throw new AEException(1022, AEInstance.a.a("ERR_INCORRECT_STATE", AEInstance.a.a("PARAM_STATE_STOPPED"), AEInstance.a.a("PARAM_STATE_STARTED")));
    }
    try
    {
      this.b9 = new DeliveryEngine(this.b7, this.cc, this);
      this.ck.a(this.b9);
      DBConnection localDBConnection = this.b7.aL();
      this.ck.jdMethod_for(localDBConnection);
      this.b3 = new SchedulerEngine(this.b7, this.ck, this.b9, this.ck.M(), this.ck.W());
      this.cc.jdMethod_char();
      a(localDBConnection);
      this.b7.k(localDBConnection);
      this.b3.jdMethod_new(false);
    }
    catch (AEException localAEException1)
    {
      try
      {
        a(true);
      }
      catch (AEException localAEException2) {}
      throw localAEException1;
    }
    this.cg = 1;
  }
  
  public void g()
    throws AEException
  {
    if (this.ca != null) {
      F();
    } else {
      jdMethod_if(true);
    }
  }
  
  public synchronized void jdMethod_if(boolean paramBoolean)
    throws AEException
  {
    if (this.cg == 0) {
      return;
    }
    a(paramBoolean);
  }
  
  public synchronized void J()
    throws AEException
  {
    if ((this.cg != 0) || (this.cd != null)) {
      throw new AEException(1022, AEInstance.a.a("ERR_INCORRECT_STATE", AEInstance.a.a("PARAM_STATE_STOPPED"), AEInstance.a.a("PARAM_STATE_SHUTDOWN")));
    }
    this.cg = 3;
    this.ca = null;
    this.cc = null;
    this.ck = null;
    if (this.b7 != null)
    {
      this.b7.aK();
      this.b7 = null;
    }
    AELog.jdMethod_if();
  }
  
  private void a(boolean paramBoolean)
    throws AEException
  {
    try
    {
      if (this.b9 != null)
      {
        if (this.b3 != null)
        {
          this.b3.jdMethod_int(paramBoolean);
          this.b3 = null;
        }
        this.cc.jdMethod_else();
        this.ck.V();
        this.ck.a(null);
        this.b9.aa();
        this.b9 = null;
      }
    }
    finally
    {
      this.cg = 0;
    }
  }
  
  public void y()
    throws AEException
  {
    if (this.ca != null) {
      i();
    } else {
      r();
    }
  }
  
  synchronized void r()
    throws AEException
  {
    if (this.cg == 2) {
      return;
    }
    if (this.cg != 1) {
      throw new AEException(1023, AEInstance.a.a("ERR_INCORRECT_STATE", AEInstance.a.a("PARAM_STATE_STARTED"), AEInstance.a.a("PARAM_STATE_SUSPENDED")));
    }
    this.b3.ao();
    AELog.a();
    this.cg = 2;
  }
  
  public void K()
    throws AEException
  {
    if (this.ca != null) {
      A();
    } else {
      h();
    }
  }
  
  synchronized void h()
    throws AEException
  {
    if (this.cg == 1) {
      return;
    }
    if (this.cg != 2) {
      throw new AEException(1024, AEInstance.a.a("ERR_INCORRECT_STATE", AEInstance.a.a("PARAM_STATE_SUSPENDED"), AEInstance.a.a("PARAM_STATE_RESUMED")));
    }
    this.b3.aq();
    this.cg = 1;
  }
  
  public boolean C()
    throws AEException
  {
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      AEServerInfo[] arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
      boolean bool = arrayOfAEServerInfo.length != 0;
      return bool;
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
  }
  
  public boolean I()
    throws AEException
  {
    ServerInfo localServerInfo = null;
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      localServerInfo = ServerInfo.jdMethod_void(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (localServerInfo == null) {
      return false;
    }
    if (this.ca.equals(localServerInfo)) {
      return p();
    }
    return d(localServerInfo.getServerURL());
  }
  
  synchronized int d()
  {
    return this.cg;
  }
  
  synchronized boolean jdMethod_goto()
  {
    return this.cg == 1;
  }
  
  public synchronized boolean l()
  {
    if (this.ca != null) {
      return H();
    }
    return this.cg == 0;
  }
  
  public boolean p()
  {
    if (this.ca != null) {
      return c();
    }
    return jdMethod_goto();
  }
  
  public boolean B()
  {
    if (this.ca != null) {
      return z();
    }
    return d() == 2;
  }
  
  public boolean jdMethod_else(String paramString)
    throws AEException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new AEException(1030, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_SERVER_URL")));
    }
    DBConnection localDBConnection = this.b7.aL();
    ServerInfo localServerInfo = null;
    try
    {
      localServerInfo = ServerInfo.a(paramString, localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (localServerInfo.equals(this.ca)) {
      return B();
    }
    IAEAlertAdmin localIAEAlertAdmin = null;
    try
    {
      localIAEAlertAdmin = jdMethod_if(localServerInfo);
    }
    catch (Exception localException1)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString), localException1);
    }
    try
    {
      boolean bool = localIAEAlertAdmin.isSuspended();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString), localRemoteException);
    }
    finally
    {
      try
      {
        localIAEAlertAdmin.remove();
      }
      catch (Exception localException3) {}
    }
  }
  
  public boolean b(String paramString)
    throws AEException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new AEException(1030, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_SERVER_URL")));
    }
    DBConnection localDBConnection = this.b7.aL();
    ServerInfo localServerInfo = null;
    try
    {
      localServerInfo = ServerInfo.a(paramString, localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (localServerInfo.equals(this.ca)) {
      return true;
    }
    IAEAlertAdmin localIAEAlertAdmin = null;
    try
    {
      localIAEAlertAdmin = jdMethod_if(localServerInfo);
    }
    catch (Exception localException1)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString), localException1);
    }
    try
    {
      boolean bool = localIAEAlertAdmin.isInitialized();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString), localRemoteException);
    }
    finally
    {
      try
      {
        localIAEAlertAdmin.remove();
      }
      catch (Exception localException3) {}
    }
  }
  
  public boolean d(String paramString)
    throws AEException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new AEException(1030, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_SERVER_URL")));
    }
    DBConnection localDBConnection = this.b7.aL();
    ServerInfo localServerInfo = null;
    try
    {
      localServerInfo = ServerInfo.a(paramString, localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (localServerInfo.equals(this.ca)) {
      return p();
    }
    IAEAlertAdmin localIAEAlertAdmin = null;
    try
    {
      localIAEAlertAdmin = jdMethod_if(localServerInfo);
    }
    catch (Exception localException1)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString), localException1);
    }
    try
    {
      boolean bool = localIAEAlertAdmin.isRunning();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString), localRemoteException);
    }
    finally
    {
      try
      {
        localIAEAlertAdmin.remove();
      }
      catch (Exception localException3) {}
    }
  }
  
  public boolean m()
    throws AEException
  {
    DBConnection localDBConnection = this.b7.aL();
    AEServerInfo[] arrayOfAEServerInfo = null;
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    int i = 0;
    label187:
    while (i < arrayOfAEServerInfo.length)
    {
      ServerInfo localServerInfo = (ServerInfo)arrayOfAEServerInfo[i];
      if (localServerInfo.equals(this.ca))
      {
        if ((p()) || (B())) {
          return false;
        }
      }
      else
      {
        IAEAlertAdmin localIAEAlertAdmin = null;
        try
        {
          localIAEAlertAdmin = jdMethod_if(localServerInfo);
        }
        catch (Exception localException1)
        {
          break label187;
        }
        try
        {
          if ((localIAEAlertAdmin.isRunning()) || (localIAEAlertAdmin.isSuspended()))
          {
            boolean bool = false;
            return bool;
          }
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException2) {}
          i++;
        }
        catch (RemoteException localRemoteException) {}finally
        {
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException5) {}
        }
      }
    }
    return true;
  }
  
  public boolean x()
    throws AEException
  {
    DBConnection localDBConnection = this.b7.aL();
    AEServerInfo[] arrayOfAEServerInfo = null;
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    int i = 0;
    label170:
    while (i < arrayOfAEServerInfo.length)
    {
      ServerInfo localServerInfo = (ServerInfo)arrayOfAEServerInfo[i];
      if (localServerInfo.equals(this.ca))
      {
        if (p()) {
          return true;
        }
      }
      else
      {
        IAEAlertAdmin localIAEAlertAdmin = null;
        try
        {
          localIAEAlertAdmin = jdMethod_if(localServerInfo);
        }
        catch (Exception localException1)
        {
          break label170;
        }
        try
        {
          if (localIAEAlertAdmin.isRunning())
          {
            boolean bool = true;
            return bool;
          }
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException2) {}
          i++;
        }
        catch (RemoteException localRemoteException) {}finally
        {
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException5) {}
        }
      }
    }
    return false;
  }
  
  public int createAlert(AEApplicationInfo paramAEApplicationInfo, String paramString1, int paramInt1, int paramInt2, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, String paramString2)
    throws AEException
  {
    AEServerInfo[] arrayOfAEServerInfo = null;
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (arrayOfAEServerInfo.length == 0)
    {
      if (!p()) {
        throw new AEException(3000, AEInstance.a.a("ERR_ENGINE_NOT_RUNNING"));
      }
      int i = this.ck.jdMethod_for(paramAEApplicationInfo);
      return jdMethod_if(i, paramString1, paramInt1, paramInt2, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, AEUtils.a(paramString2));
    }
    ServerInfo localServerInfo = null;
    localDBConnection = this.b7.aL();
    try
    {
      localServerInfo = ServerInfo.jdMethod_void(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if ((localServerInfo == null) || (localServerInfo.equals(this.ca)) || (m()))
    {
      int j = this.ck.jdMethod_for(paramAEApplicationInfo);
      return jdMethod_if(j, paramString1, paramInt1, paramInt2, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, AEUtils.a(paramString2));
    }
    IAEAlertClient localIAEAlertClient = null;
    try
    {
      localIAEAlertClient = a(localServerInfo);
    }
    catch (Exception localException1)
    {
      throw new AEException(3001, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localException1);
    }
    try
    {
      int k = localIAEAlertClient.createAlert(paramAEApplicationInfo, paramString1, paramInt1, paramInt2, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramString2);
      return k;
    }
    catch (RemoteException localRemoteException)
    {
      throw new AEException(3001, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localRemoteException);
    }
    finally
    {
      try
      {
        localIAEAlertClient.remove();
      }
      catch (Exception localException3) {}
    }
  }
  
  public int createAlert(AEApplicationInfo paramAEApplicationInfo, String paramString, int paramInt1, int paramInt2, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, byte[] paramArrayOfByte)
    throws AEException
  {
    AEServerInfo[] arrayOfAEServerInfo = null;
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (arrayOfAEServerInfo.length == 0)
    {
      if (!p()) {
        throw new AEException(3000, AEInstance.a.a("ERR_ENGINE_NOT_RUNNING"));
      }
      int i = this.ck.jdMethod_for(paramAEApplicationInfo);
      return jdMethod_if(i, paramString, paramInt1, paramInt2, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramArrayOfByte);
    }
    ServerInfo localServerInfo = null;
    localDBConnection = this.b7.aL();
    try
    {
      localServerInfo = ServerInfo.jdMethod_void(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if ((localServerInfo == null) || (localServerInfo.equals(this.ca)) || (m()))
    {
      int j = this.ck.jdMethod_for(paramAEApplicationInfo);
      return jdMethod_if(j, paramString, paramInt1, paramInt2, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramArrayOfByte);
    }
    IAEAlertClient localIAEAlertClient = null;
    try
    {
      localIAEAlertClient = a(localServerInfo);
    }
    catch (Exception localException1)
    {
      throw new AEException(3001, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localException1);
    }
    try
    {
      int k = localIAEAlertClient.createAlert(paramAEApplicationInfo, paramString, paramInt1, paramInt2, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramArrayOfByte);
      return k;
    }
    catch (RemoteException localRemoteException)
    {
      throw new AEException(3001, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localRemoteException);
    }
    finally
    {
      try
      {
        localIAEAlertClient.remove();
      }
      catch (Exception localException3) {}
    }
  }
  
  private DeliveryInfo[] a(int paramInt1, int paramInt2, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo)
    throws AEException
  {
    if ((paramArrayOfIAEDeliveryInfo == null) || (paramArrayOfIAEDeliveryInfo.length == 0)) {
      throw new AEException(1009, AEInstance.a.a("ERR_INVALID_DELIVERY_INFO"));
    }
    int i = paramArrayOfIAEDeliveryInfo.length;
    DeliveryInfo[] arrayOfDeliveryInfo = new DeliveryInfo[i];
    for (int j = 0; j < i; j++)
    {
      if (paramArrayOfIAEDeliveryInfo[j] == null) {
        throw new AEException(1009, AEInstance.a.a("ERR_INVALID_DELIVERY_INFO"));
      }
      if (paramArrayOfIAEDeliveryInfo[j].getDeliveryProperties() == null) {
        throw new AEException(1012, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_DELIVERY_PROPERTIES")));
      }
      if ((paramInt2 != 0) && (paramArrayOfIAEDeliveryInfo[j].getId() != 0))
      {
        localObject = (DeliveryInfo)paramArrayOfIAEDeliveryInfo[j];
        if (((DeliveryInfo)localObject).jdMethod_int() == paramInt2)
        {
          arrayOfDeliveryInfo[j] = localObject;
          continue;
        }
      }
      Object localObject = this.ck.w(paramArrayOfIAEDeliveryInfo[j].getDeliveryChannelName());
      if (localObject == null) {
        throw new AEException(1007, AEInstance.a.a("ERR_CHANNEL_NAME_NOT_FOUND"));
      }
      if ((((ChannelInfo)localObject).jdMethod_do() != 0) && (((ChannelInfo)localObject).jdMethod_do() != paramInt1)) {
        throw new AEException(1007, AEInstance.a.a("ERR_CHANNEL_NAME_NOT_FOUND"));
      }
      arrayOfDeliveryInfo[j] = new DeliveryInfo(((ChannelInfo)localObject).getId(), paramArrayOfIAEDeliveryInfo[j]);
    }
    return arrayOfDeliveryInfo;
  }
  
  int jdMethod_if(int paramInt1, String paramString, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, byte[] paramArrayOfByte)
    throws AEException
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
      throw new AEException(1012, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_STRING_MESSAGE")));
    }
    AlertInstance localAlertInstance = new AlertInstance();
    paramAEScheduleInfo = a(paramInt2, paramAEScheduleInfo);
    DeliveryInfo[] arrayOfDeliveryInfo = a(paramInt1, 0, paramArrayOfIAEDeliveryInfo);
    localAlertInstance.m(paramInt2);
    localAlertInstance.r(paramInt3);
    localAlertInstance.n(paramInt1);
    localAlertInstance.o(0);
    localAlertInstance.D(paramString);
    localAlertInstance.jdMethod_if(paramAEScheduleInfo);
    localAlertInstance.jdMethod_if(paramArrayOfByte);
    localAlertInstance.jdMethod_if(arrayOfDeliveryInfo);
    localAlertInstance.jdMethod_if(new int[arrayOfDeliveryInfo.length]);
    localAlertInstance.jdMethod_new(paramAEScheduleInfo.getStart());
    this.ck.a(localAlertInstance);
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      localDBConnection.jdMethod_try(false);
      localAlertInstance.jdMethod_if(this.b7, localDBConnection);
      if ((this.b3 != null) && (this.b3.a(localDBConnection, localAlertInstance, 1, false))) {
        this.b3.ar();
      }
      localDBConnection.a0();
    }
    catch (SQLException localSQLException1)
    {
      try
      {
        localDBConnection.a1();
      }
      catch (SQLException localSQLException2) {}
      throw new AEException(1, DBSqlUtils.a(localSQLException1));
    }
    catch (AEException localAEException)
    {
      try
      {
        localDBConnection.a1();
      }
      catch (SQLException localSQLException3) {}
      throw localAEException;
    }
    finally
    {
      try
      {
        localDBConnection.jdMethod_try(true);
      }
      catch (SQLException localSQLException4) {}
      this.b7.k(localDBConnection);
    }
    return localAlertInstance.getId();
  }
  
  public void a(AEApplicationInfo paramAEApplicationInfo)
    throws AEException
  {
    int i = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    DBConnection localDBConnection = this.b7.aL();
    AEServerInfo[] arrayOfAEServerInfo = null;
    arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    boolean bool = false;
    try
    {
      bool = this.ck.a(localDBConnection, paramAEApplicationInfo, arrayOfAEServerInfo.length != 0);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if ((arrayOfAEServerInfo.length == 0) || (!bool)) {
      return;
    }
    int j = 0;
    label306:
    while (j < arrayOfAEServerInfo.length)
    {
      ServerInfo localServerInfo = (ServerInfo)arrayOfAEServerInfo[j];
      if ((this.ca != null) && (localServerInfo.equals(this.ca)))
      {
        try
        {
          a(paramAEApplicationInfo);
        }
        catch (Exception localException1)
        {
          AELog.a(localException1, "Unable to add application", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
        }
      }
      else
      {
        IAEAlertAdmin localIAEAlertAdmin = null;
        try
        {
          localIAEAlertAdmin = jdMethod_if(localServerInfo);
        }
        catch (Exception localException2)
        {
          AELog.a(localException2, "Unable to add application", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
          break label306;
        }
        try
        {
          localIAEAlertAdmin.addApplication(paramAEApplicationInfo);
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException3) {}
          j++;
        }
        catch (Exception localException4)
        {
          AELog.a(localException4, "Unable to add application", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
        }
        finally
        {
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException6) {}
        }
      }
    }
    if (i != 0) {
      throw new AEException(1034, AEInstance.a.a("ERR_OPERATION_NOT_COMPLETED", AEInstance.a.a("OPERATION_ADDAPPLICATION"), localStringBuffer.toString()));
    }
  }
  
  public void g(String paramString)
    throws AEException
  {
    int i = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new AEException(1012, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_STRING_APPNAME")));
    }
    if (paramString.equals("Universal Alert Engine")) {
      throw new AEException(1018, AEInstance.a.a("ERR_RESERVED_APPLICATION", "Universal Alert Engine"));
    }
    DBConnection localDBConnection = this.b7.aL();
    AEServerInfo[] arrayOfAEServerInfo = null;
    arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    int j = 0;
    try
    {
      localDBConnection.jdMethod_try(false);
      int k = this.ck.a(localDBConnection, paramString, arrayOfAEServerInfo.length != 0);
      if (k > 0)
      {
        j = 1;
      }
      else
      {
        k = -1 * k;
        j = 0;
      }
      ArrayList localArrayList = AlertInstance.jdMethod_int(localDBConnection, k, true);
      a(localDBConnection, localArrayList, new c(k, null, false, 0, 0));
      localDBConnection.a0();
    }
    catch (SQLException localSQLException1)
    {
      try
      {
        localDBConnection.a1();
      }
      catch (SQLException localSQLException2) {}
      throw new AEException(1, DBSqlUtils.a(localSQLException1));
    }
    catch (AEException localAEException)
    {
      try
      {
        localDBConnection.a1();
      }
      catch (SQLException localSQLException3) {}
      throw localAEException;
    }
    finally
    {
      try
      {
        localDBConnection.jdMethod_try(true);
      }
      catch (SQLException localSQLException4) {}
      this.b7.k(localDBConnection);
    }
    if ((arrayOfAEServerInfo.length == 0) || (j == 0)) {
      return;
    }
    int m = 0;
    label455:
    while (m < arrayOfAEServerInfo.length)
    {
      ServerInfo localServerInfo = (ServerInfo)arrayOfAEServerInfo[m];
      if ((this.ca == null) || (!localServerInfo.equals(this.ca)))
      {
        IAEAlertAdmin localIAEAlertAdmin = null;
        try
        {
          localIAEAlertAdmin = jdMethod_if(localServerInfo);
        }
        catch (Exception localException1)
        {
          AELog.a(localException1, "Unable to remove application", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
          break label455;
        }
        try
        {
          localIAEAlertAdmin.removeApplication(paramString);
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException2) {}
          m++;
        }
        catch (Exception localException3)
        {
          AELog.a(localException3, "Unable to remove application", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
        }
        finally
        {
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException5) {}
        }
      }
    }
    if (i != 0) {
      throw new AEException(1034, AEInstance.a.a("ERR_OPERATION_NOT_COMPLETED", AEInstance.a.a("OPERATION_REMOVEAPPLICATION"), localStringBuffer.toString()));
    }
  }
  
  public void a(String paramString, AEApplicationInfo paramAEApplicationInfo)
    throws AEException
  {
    int i = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new AEException(1012, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_STRING_APPNAME")));
    }
    if (paramString.equals("Universal Alert Engine")) {
      throw new AEException(1018, AEInstance.a.a("ERR_RESERVED_APPLICATION", "Universal Alert Engine"));
    }
    DBConnection localDBConnection = this.b7.aL();
    AEServerInfo[] arrayOfAEServerInfo = null;
    arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    boolean bool = false;
    try
    {
      bool = this.ck.a(localDBConnection, paramString, paramAEApplicationInfo, arrayOfAEServerInfo.length != 0);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if ((arrayOfAEServerInfo.length == 0) || (!bool)) {
      return;
    }
    int j = 0;
    label386:
    while (j < arrayOfAEServerInfo.length)
    {
      ServerInfo localServerInfo = (ServerInfo)arrayOfAEServerInfo[j];
      if ((this.ca != null) && (localServerInfo.equals(this.ca)))
      {
        try
        {
          a(paramString, paramAEApplicationInfo);
        }
        catch (Exception localException1)
        {
          AELog.a(localException1, "Unable to update application", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
        }
      }
      else
      {
        IAEAlertAdmin localIAEAlertAdmin = null;
        try
        {
          localIAEAlertAdmin = jdMethod_if(localServerInfo);
        }
        catch (Exception localException2)
        {
          AELog.a(localException2, "Unable to update application", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
          break label386;
        }
        try
        {
          localIAEAlertAdmin.updateApplication(paramString, paramAEApplicationInfo);
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException3) {}
          j++;
        }
        catch (Exception localException4)
        {
          AELog.a(localException4, "Unable to update application", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
        }
        finally
        {
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException6) {}
        }
      }
    }
    if (i != 0) {
      throw new AEException(1034, AEInstance.a.a("ERR_OPERATION_NOT_COMPLETED", AEInstance.a.a("OPERATION_UPDATEAPPLICATION"), localStringBuffer.toString()));
    }
  }
  
  public AEApplicationInfo[] jdMethod_void()
    throws AEException
  {
    return this.ck.P();
  }
  
  public int e(String paramString)
    throws AEException
  {
    return this.ck.q(paramString);
  }
  
  public IAEChannelInfo a(String paramString1, String paramString2, Properties paramProperties, int paramInt)
    throws AEException
  {
    return a(paramString1, paramString2, paramProperties, paramInt, null);
  }
  
  public IAEChannelInfo a(String paramString1, String paramString2, Properties paramProperties, int paramInt, String paramString3)
    throws AEException
  {
    int i = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    if ((paramString1 == null) || (paramString1.length() == 0)) {
      throw new AEException(1012, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_STRING_CHANNELNAME")));
    }
    if ((paramString2 == null) || (paramString2.length() == 0)) {
      throw new AEException(1012, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_STRING_CLASSNAME")));
    }
    ChannelInfo localChannelInfo = new ChannelInfo();
    localChannelInfo.setClassName(paramString2);
    localChannelInfo.setInitInfo(paramProperties);
    localChannelInfo.jdMethod_do(1);
    localChannelInfo.setNumWorkers(paramInt);
    localChannelInfo.setChannelName(paramString1);
    localChannelInfo.a(paramString3);
    DBConnection localDBConnection = this.b7.aL();
    AEServerInfo[] arrayOfAEServerInfo = null;
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    IAEChannelInfo localIAEChannelInfo = this.ck.a(localChannelInfo, arrayOfAEServerInfo.length != 0);
    if ((arrayOfAEServerInfo.length == 0) || (localIAEChannelInfo == null)) {
      return localIAEChannelInfo;
    }
    int j = 0;
    label454:
    while (j < arrayOfAEServerInfo.length)
    {
      ServerInfo localServerInfo = (ServerInfo)arrayOfAEServerInfo[j];
      if ((this.ca != null) && (localServerInfo.equals(this.ca)))
      {
        try
        {
          a(paramString1, paramString2, paramProperties, paramInt, paramString3);
        }
        catch (Exception localException1)
        {
          AELog.a(localException1, "Unable to install delivery channel", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
        }
      }
      else
      {
        IAEAlertAdmin localIAEAlertAdmin = null;
        try
        {
          localIAEAlertAdmin = jdMethod_if(localServerInfo);
        }
        catch (Exception localException2)
        {
          AELog.a(localException2, "Unable to install delivery channel", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
          break label454;
        }
        try
        {
          localIAEAlertAdmin.installDeliveryChannel(paramString1, paramString2, paramProperties, paramInt, paramString3);
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException3) {}
          j++;
        }
        catch (Exception localException4)
        {
          AELog.a(localException4, "Unable to install delivery channel", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
        }
        finally
        {
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException6) {}
        }
      }
    }
    if (i != 0) {
      throw new AEException(1034, AEInstance.a.a("ERR_OPERATION_NOT_COMPLETED", AEInstance.a.a("OPERATION_INSTALLCHANNEL"), localStringBuffer.toString()));
    }
    return localIAEChannelInfo;
  }
  
  public IAEChannelInfo jdMethod_int(String paramString)
    throws AEException
  {
    int i = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    DBConnection localDBConnection = this.b7.aL();
    AEServerInfo[] arrayOfAEServerInfo = null;
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    IAEChannelInfo localIAEChannelInfo = this.ck.jdMethod_do(paramString, arrayOfAEServerInfo.length != 0);
    if ((arrayOfAEServerInfo.length == 0) || (localIAEChannelInfo == null)) {
      return localIAEChannelInfo;
    }
    int j = 0;
    label266:
    while (j < arrayOfAEServerInfo.length)
    {
      ServerInfo localServerInfo = (ServerInfo)arrayOfAEServerInfo[j];
      if ((this.ca == null) || (!localServerInfo.equals(this.ca)))
      {
        IAEAlertAdmin localIAEAlertAdmin = null;
        try
        {
          localIAEAlertAdmin = jdMethod_if(localServerInfo);
        }
        catch (Exception localException1)
        {
          AELog.a(localException1, "Unable to remove channel", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
          break label266;
        }
        try
        {
          localIAEAlertAdmin.uninstallDeliveryChannel(paramString);
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException2) {}
          j++;
        }
        catch (Exception localException3)
        {
          AELog.a(localException3, "Unable to remove channel", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
        }
        finally
        {
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException5) {}
        }
      }
    }
    if (i != 0) {
      throw new AEException(1034, AEInstance.a.a("ERR_OPERATION_NOT_COMPLETED", AEInstance.a.a("OPERATION_REMOVECHANNEL"), localStringBuffer.toString()));
    }
    return localIAEChannelInfo;
  }
  
  public IAEChannelInfo f(String paramString)
    throws AEException
  {
    AEServerInfo[] arrayOfAEServerInfo = null;
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (arrayOfAEServerInfo.length == 0) {
      return this.ck.n(paramString);
    }
    ServerInfo localServerInfo = null;
    localDBConnection = this.b7.aL();
    try
    {
      localServerInfo = ServerInfo.jdMethod_void(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if ((localServerInfo == null) || (localServerInfo.equals(this.ca)) || (m())) {
      return this.ck.n(paramString);
    }
    IAEAlertAdmin localIAEAlertAdmin = null;
    try
    {
      localIAEAlertAdmin = jdMethod_if(localServerInfo);
    }
    catch (Exception localException1)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localException1);
    }
    try
    {
      IAEChannelInfo localIAEChannelInfo = localIAEAlertAdmin.loadDeliveryChannel(paramString);
      return localIAEChannelInfo;
    }
    catch (RemoteException localRemoteException)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localRemoteException);
    }
    finally
    {
      try
      {
        localIAEAlertAdmin.remove();
      }
      catch (Exception localException3) {}
    }
  }
  
  public IAEChannelInfo c(String paramString)
    throws AEException
  {
    AEServerInfo[] arrayOfAEServerInfo = null;
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (arrayOfAEServerInfo.length == 0) {
      return this.ck.z(paramString);
    }
    ServerInfo localServerInfo = null;
    localDBConnection = this.b7.aL();
    try
    {
      localServerInfo = ServerInfo.jdMethod_void(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if ((localServerInfo == null) || (localServerInfo.equals(this.ca)) || (m())) {
      return this.ck.z(paramString);
    }
    IAEAlertAdmin localIAEAlertAdmin = null;
    try
    {
      localIAEAlertAdmin = jdMethod_if(localServerInfo);
    }
    catch (Exception localException1)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localException1);
    }
    try
    {
      IAEChannelInfo localIAEChannelInfo = localIAEAlertAdmin.unloadDeliveryChannel(paramString);
      return localIAEChannelInfo;
    }
    catch (RemoteException localRemoteException)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localRemoteException);
    }
    finally
    {
      try
      {
        localIAEAlertAdmin.remove();
      }
      catch (Exception localException3) {}
    }
  }
  
  public IAEChannelInfo a(IAEChannelInfo paramIAEChannelInfo)
    throws AEException
  {
    int i = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    DBConnection localDBConnection = this.b7.aL();
    AEServerInfo[] arrayOfAEServerInfo = null;
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    IAEChannelInfo localIAEChannelInfo = this.ck.jdMethod_if((ChannelInfo)paramIAEChannelInfo, arrayOfAEServerInfo.length != 0);
    if ((arrayOfAEServerInfo.length == 0) || (localIAEChannelInfo == null)) {
      return localIAEChannelInfo;
    }
    int j = 0;
    label269:
    while (j < arrayOfAEServerInfo.length)
    {
      ServerInfo localServerInfo = (ServerInfo)arrayOfAEServerInfo[j];
      if ((this.ca == null) || (!localServerInfo.equals(this.ca)))
      {
        IAEAlertAdmin localIAEAlertAdmin = null;
        try
        {
          localIAEAlertAdmin = jdMethod_if(localServerInfo);
        }
        catch (Exception localException1)
        {
          AELog.a(localException1, "Unable to update channel", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
          break label269;
        }
        try
        {
          localIAEAlertAdmin.updateDeliveryChannel(paramIAEChannelInfo);
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException2) {}
          j++;
        }
        catch (Exception localException3)
        {
          AELog.a(localException3, "Unable to update channel", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
        }
        finally
        {
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException5) {}
        }
      }
    }
    if (i != 0) {
      throw new AEException(1034, AEInstance.a.a("ERR_OPERATION_NOT_COMPLETED", AEInstance.a.a("OPERATION_UPDATECHANNEL"), localStringBuffer.toString()));
    }
    return localIAEChannelInfo;
  }
  
  public IAEChannelInfo[] jdMethod_void(String paramString)
    throws AEException
  {
    int i = 0;
    if ((paramString != null) && (paramString.length() != 0))
    {
      i = this.ck.q(paramString);
      if (i == this.ck.q("Universal Alert Engine")) {
        throw new AEException(1018, AEInstance.a.a("ERR_RESERVED_APPLICATION", "Universal Alert Engine"));
      }
    }
    return this.ck.a(i, false, true);
  }
  
  public IAEChannelInfo[] jdMethod_do(AEApplicationInfo paramAEApplicationInfo)
    throws AEException
  {
    int i = this.ck.jdMethod_for(paramAEApplicationInfo);
    return this.ck.a(i, false, false);
  }
  
  public IAEChannelInfo[] jdMethod_byte(String paramString)
    throws AEException
  {
    int i = 0;
    if ((paramString != null) && (paramString.length() != 0))
    {
      i = this.ck.q(paramString);
      if (i == this.ck.q("Universal Alert Engine")) {
        throw new AEException(1018, AEInstance.a.a("ERR_RESERVED_APPLICATION", "Universal Alert Engine"));
      }
    }
    AEServerInfo[] arrayOfAEServerInfo = null;
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (arrayOfAEServerInfo.length == 0) {
      return this.ck.a(i, true, true);
    }
    ServerInfo localServerInfo = null;
    localDBConnection = this.b7.aL();
    try
    {
      localServerInfo = ServerInfo.jdMethod_void(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if ((localServerInfo == null) || (localServerInfo.equals(this.ca)) || (m())) {
      return this.ck.a(i, true, true);
    }
    IAEAlertAdmin localIAEAlertAdmin = null;
    try
    {
      localIAEAlertAdmin = jdMethod_if(localServerInfo);
    }
    catch (Exception localException1)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localException1);
    }
    try
    {
      IAEChannelInfo[] arrayOfIAEChannelInfo = localIAEAlertAdmin.getLoadedDeliveryChannels(paramString);
      return arrayOfIAEChannelInfo;
    }
    catch (RemoteException localRemoteException)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localRemoteException);
    }
    finally
    {
      try
      {
        localIAEAlertAdmin.remove();
      }
      catch (Exception localException3) {}
    }
  }
  
  public IAEChannelInfo[] jdMethod_if(AEApplicationInfo paramAEApplicationInfo)
    throws AEException
  {
    AEServerInfo[] arrayOfAEServerInfo = null;
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (arrayOfAEServerInfo.length == 0)
    {
      if (!p()) {
        throw new AEException(3000, AEInstance.a.a("ERR_ENGINE_NOT_RUNNING"));
      }
      int i = this.ck.jdMethod_for(paramAEApplicationInfo);
      return this.ck.a(i, true, false);
    }
    ServerInfo localServerInfo = null;
    localDBConnection = this.b7.aL();
    try
    {
      localServerInfo = ServerInfo.jdMethod_void(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if ((localServerInfo == null) || (localServerInfo.equals(this.ca)) || (m()))
    {
      int j = this.ck.jdMethod_for(paramAEApplicationInfo);
      return this.ck.a(j, true, false);
    }
    IAEAlertClient localIAEAlertClient = null;
    try
    {
      localIAEAlertClient = a(localServerInfo);
    }
    catch (Exception localException1)
    {
      throw new AEException(3001, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localException1);
    }
    try
    {
      IAEChannelInfo[] arrayOfIAEChannelInfo = localIAEAlertClient.getLoadedDeliveryChannels(paramAEApplicationInfo);
      return arrayOfIAEChannelInfo;
    }
    catch (RemoteException localRemoteException)
    {
      throw new AEException(3001, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localRemoteException);
    }
    finally
    {
      try
      {
        localIAEAlertClient.remove();
      }
      catch (Exception localException3) {}
    }
  }
  
  public boolean jdMethod_long(String paramString)
  {
    ChannelInfo localChannelInfo = this.ck.w(paramString);
    boolean bool = false;
    if (localChannelInfo != null) {
      bool = localChannelInfo.isLoaded();
    }
    return bool;
  }
  
  private ArrayList a(ArrayList paramArrayList, IUnaryPredicate paramIUnaryPredicate)
    throws AEException
  {
    if ((this.cg == 1) || (this.cg == 2))
    {
      ArrayList localArrayList = this.b3.jdMethod_for(paramIUnaryPredicate);
      if (localArrayList.size() > 0)
      {
        TreeSet localTreeSet = new TreeSet(b2);
        localTreeSet.addAll(localArrayList);
        Iterator localIterator = localTreeSet.iterator();
        int i = this.ck.q("Universal Alert Engine");
        while (localIterator.hasNext())
        {
          AlertInstance localAlertInstance = (AlertInstance)localIterator.next();
          if (localAlertInstance.getId() == i) {
            localIterator.remove();
          }
        }
        localTreeSet.addAll(paramArrayList);
        return new ArrayList(localTreeSet);
      }
    }
    return paramArrayList;
  }
  
  public IAEAlertDefinition[] jdMethod_do(boolean paramBoolean)
    throws AEException
  {
    ArrayList localArrayList = new ArrayList();
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      int i = this.ck.q("Universal Alert Engine");
      localArrayList = AlertInstance.jdMethod_if(localDBConnection, i, paramBoolean);
      localArrayList = a(localArrayList, b6);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    IAEAlertDefinition[] arrayOfIAEAlertDefinition = a(localArrayList);
    return arrayOfIAEAlertDefinition;
  }
  
  public IAEAlertDefinition[] getAppAlerts(AEApplicationInfo paramAEApplicationInfo, boolean paramBoolean)
    throws AEException
  {
    int i = this.ck.jdMethod_for(paramAEApplicationInfo);
    return jdMethod_do(i, paramBoolean);
  }
  
  public IAEAlertDefinition[] jdMethod_if(String paramString, boolean paramBoolean)
    throws AEException
  {
    int i = this.ck.q(paramString);
    return jdMethod_do(i, paramBoolean);
  }
  
  private IAEAlertDefinition[] jdMethod_do(int paramInt, boolean paramBoolean)
    throws AEException
  {
    if (paramInt == this.ck.q("Universal Alert Engine")) {
      throw new AEException(1018, AEInstance.a.a("ERR_RESERVED_APPLICATION", "Universal Alert Engine"));
    }
    ArrayList localArrayList = new ArrayList();
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      localArrayList = AlertInstance.jdMethod_int(localDBConnection, paramInt, paramBoolean);
      localArrayList = a(localArrayList, new c(paramInt, null, false, 0, 0));
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    IAEAlertDefinition[] arrayOfIAEAlertDefinition = a(localArrayList);
    return arrayOfIAEAlertDefinition;
  }
  
  public IAEAlertDefinition[] getAppAlertsForChannelPaged(AEApplicationInfo paramAEApplicationInfo, boolean paramBoolean, String paramString, int paramInt1, int paramInt2)
    throws AEException
  {
    int i = this.ck.jdMethod_for(paramAEApplicationInfo);
    return a(i, paramBoolean, paramString, paramInt1, paramInt2);
  }
  
  public IAEAlertDefinition[] a(String paramString1, boolean paramBoolean, String paramString2, int paramInt1, int paramInt2)
    throws AEException
  {
    int i = this.ck.q(paramString1);
    return a(i, paramBoolean, paramString2, paramInt1, paramInt2);
  }
  
  private IAEAlertDefinition[] a(int paramInt1, boolean paramBoolean, String paramString, int paramInt2, int paramInt3)
    throws AEException
  {
    if (paramInt1 == this.ck.q("Universal Alert Engine")) {
      throw new AEException(1018, AEInstance.a.a("ERR_RESERVED_APPLICATION", "Universal Alert Engine"));
    }
    if (paramInt3 <= 0) {
      throw new AEException(1035, AEInstance.a.a("ERR_INVALID_PAGE_SIZE", String.valueOf(paramInt3)));
    }
    if (paramInt2 < 0) {
      paramInt2 = 0;
    }
    int i = 0;
    if ((paramString != null) && (paramString.length() != 0))
    {
      localObject1 = this.ck.w(paramString);
      if (localObject1 == null) {
        throw new AEException(1007, AEInstance.a.a("ERR_CHANNEL_NAME_NOT_FOUND"));
      }
      i = ((ChannelInfo)localObject1).getId();
    }
    Object localObject1 = new ArrayList();
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      localObject1 = AlertInstance.a(localDBConnection, paramInt1, paramBoolean, i, paramInt2, paramInt3);
      localObject1 = a((ArrayList)localObject1, new c(paramInt1, null, false, i, paramInt2));
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    while (((ArrayList)localObject1).size() > paramInt3) {
      ((ArrayList)localObject1).remove(((ArrayList)localObject1).size() - 1);
    }
    IAEAlertDefinition[] arrayOfIAEAlertDefinition = a((ArrayList)localObject1);
    return arrayOfIAEAlertDefinition;
  }
  
  public IAEAlertDefinition[] a(String paramString1, String paramString2, boolean paramBoolean)
    throws AEException
  {
    int i = this.ck.q(paramString1);
    return a(i, paramString2, new c(i, paramString2, true, 0, 0), paramBoolean);
  }
  
  public IAEAlertDefinition[] a(String paramString, boolean paramBoolean)
    throws AEException
  {
    ArrayList localArrayList = new ArrayList();
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      int i = this.ck.q("Universal Alert Engine");
      localArrayList = AlertInstance.a(localDBConnection, paramString, i, paramBoolean);
      localArrayList = a(localArrayList, new c(0, paramString, true, 0, 0));
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    IAEAlertDefinition[] arrayOfIAEAlertDefinition = a(localArrayList);
    return arrayOfIAEAlertDefinition;
  }
  
  public IAEAlertDefinition[] getUserAlerts(AEApplicationInfo paramAEApplicationInfo, String paramString, boolean paramBoolean)
    throws AEException
  {
    int i = this.ck.jdMethod_for(paramAEApplicationInfo);
    return a(i, paramString, new c(i, paramString, true, 0, 0), paramBoolean);
  }
  
  private IAEAlertDefinition[] a(int paramInt, String paramString, IUnaryPredicate paramIUnaryPredicate, boolean paramBoolean)
    throws AEException
  {
    if (paramInt == this.ck.q("Universal Alert Engine")) {
      throw new AEException(1018, AEInstance.a.a("ERR_RESERVED_APPLICATION", "Universal Alert Engine"));
    }
    ArrayList localArrayList = new ArrayList();
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      localArrayList = AlertInstance.a(localDBConnection, paramInt, paramString, paramBoolean);
      localArrayList = a(localArrayList, paramIUnaryPredicate);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    IAEAlertDefinition[] arrayOfIAEAlertDefinition = a(localArrayList);
    return arrayOfIAEAlertDefinition;
  }
  
  private IAEAlertDefinition[] a(ArrayList paramArrayList)
    throws AEException
  {
    AlertInstance[] arrayOfAlertInstance = new AlertInstance[paramArrayList.size()];
    paramArrayList.toArray(arrayOfAlertInstance);
    for (int i = 0; i < arrayOfAlertInstance.length; i++) {
      this.ck.a(arrayOfAlertInstance[i]);
    }
    return arrayOfAlertInstance;
  }
  
  public IAEAlertDefinition jdMethod_null(int paramInt)
    throws AEException
  {
    return jdMethod_if(0, paramInt);
  }
  
  public IAEAlertDefinition[] a(int[] paramArrayOfInt)
    throws AEException
  {
    IAEAlertDefinition[] arrayOfIAEAlertDefinition = new IAEAlertDefinition[paramArrayOfInt.length];
    for (int i = 0; i < paramArrayOfInt.length; i++) {
      arrayOfIAEAlertDefinition[i] = jdMethod_if(0, paramArrayOfInt[i]);
    }
    return arrayOfIAEAlertDefinition;
  }
  
  public IAEAlertDefinition getAlert(AEApplicationInfo paramAEApplicationInfo, int paramInt)
    throws AEException
  {
    int i = this.ck.jdMethod_for(paramAEApplicationInfo);
    return jdMethod_if(i, paramInt);
  }
  
  private IAEAlertDefinition jdMethod_if(int paramInt1, int paramInt2)
    throws AEException
  {
    AlertInstance localAlertInstance1 = null;
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      localAlertInstance1 = AlertInstance.jdMethod_if(localDBConnection, paramInt2);
      if (localAlertInstance1 == null) {
        throw new AEException(1026, AEInstance.a.a("ERR_NO_SUCH_ALERT"));
      }
      if (localAlertInstance1.aB() == this.ck.q("Universal Alert Engine")) {
        throw new AEException(1018, AEInstance.a.a("ERR_RESERVED_ALERT", Integer.toString(paramInt2)));
      }
      if ((paramInt1 != 0) && (paramInt1 != localAlertInstance1.aB())) {
        throw new AEException(1020, AEInstance.a.a("ERR_APP_NOT_OWNER", this.ck.b(paramInt1).getName()));
      }
      if ((localAlertInstance1.getNextRaised() == 0L) && (!localAlertInstance1.l(2)) && (this.b3 != null))
      {
        ArrayList localArrayList = this.b3.jdMethod_for(new b(paramInt2));
        if ((localArrayList != null) && (localArrayList.size() > 0))
        {
          AlertInstance localAlertInstance2 = (AlertInstance)localArrayList.get(0);
          localAlertInstance1.jdMethod_new(localAlertInstance2.getNextRaised());
        }
      }
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    this.ck.a(localAlertInstance1);
    return localAlertInstance1;
  }
  
  public IAEAlertDefinition a(int paramInt1, String paramString1, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, String paramString2)
    throws AEException
  {
    AEServerInfo[] arrayOfAEServerInfo = null;
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (arrayOfAEServerInfo.length == 0) {
      return a(0, paramInt1, paramString1, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramString2, null);
    }
    ServerInfo localServerInfo = null;
    localDBConnection = this.b7.aL();
    try
    {
      localServerInfo = ServerInfo.jdMethod_void(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if ((localServerInfo == null) || (localServerInfo.equals(this.ca)) || (m())) {
      return a(0, paramInt1, paramString1, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramString2, null);
    }
    IAEAlertAdmin localIAEAlertAdmin = null;
    try
    {
      localIAEAlertAdmin = jdMethod_if(localServerInfo);
    }
    catch (Exception localException1)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localException1);
    }
    try
    {
      IAEAlertDefinition localIAEAlertDefinition = localIAEAlertAdmin.updateAlert(paramInt1, paramString1, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramString2);
      return localIAEAlertDefinition;
    }
    catch (RemoteException localRemoteException)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localRemoteException);
    }
    finally
    {
      try
      {
        localIAEAlertAdmin.remove();
      }
      catch (Exception localException3) {}
    }
  }
  
  public IAEAlertDefinition a(int paramInt1, String paramString, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, byte[] paramArrayOfByte)
    throws AEException
  {
    AEServerInfo[] arrayOfAEServerInfo = null;
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (arrayOfAEServerInfo.length == 0) {
      return a(0, paramInt1, paramString, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, null, paramArrayOfByte);
    }
    ServerInfo localServerInfo = null;
    localDBConnection = this.b7.aL();
    try
    {
      localServerInfo = ServerInfo.jdMethod_void(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if ((localServerInfo == null) || (localServerInfo.equals(this.ca)) || (m())) {
      return a(0, paramInt1, paramString, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, null, paramArrayOfByte);
    }
    IAEAlertAdmin localIAEAlertAdmin = null;
    try
    {
      localIAEAlertAdmin = jdMethod_if(localServerInfo);
    }
    catch (Exception localException1)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localException1);
    }
    try
    {
      IAEAlertDefinition localIAEAlertDefinition = localIAEAlertAdmin.updateAlert(paramInt1, paramString, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramArrayOfByte);
      return localIAEAlertDefinition;
    }
    catch (RemoteException localRemoteException)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localRemoteException);
    }
    finally
    {
      try
      {
        localIAEAlertAdmin.remove();
      }
      catch (Exception localException3) {}
    }
  }
  
  public IAEAlertDefinition updateAlert(AEApplicationInfo paramAEApplicationInfo, int paramInt1, String paramString1, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, String paramString2)
    throws AEException
  {
    AEServerInfo[] arrayOfAEServerInfo = null;
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (arrayOfAEServerInfo.length == 0)
    {
      if (!p()) {
        throw new AEException(3000, AEInstance.a.a("ERR_ENGINE_NOT_RUNNING"));
      }
      int i = this.ck.jdMethod_for(paramAEApplicationInfo);
      return a(i, paramInt1, paramString1, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramString2, null);
    }
    ServerInfo localServerInfo = null;
    localDBConnection = this.b7.aL();
    try
    {
      localServerInfo = ServerInfo.jdMethod_void(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if ((localServerInfo == null) || (localServerInfo.equals(this.ca)) || (m()))
    {
      int j = this.ck.jdMethod_for(paramAEApplicationInfo);
      return a(j, paramInt1, paramString1, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramString2, null);
    }
    IAEAlertClient localIAEAlertClient = null;
    try
    {
      localIAEAlertClient = a(localServerInfo);
    }
    catch (Exception localException1)
    {
      throw new AEException(3001, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localException1);
    }
    try
    {
      IAEAlertDefinition localIAEAlertDefinition = localIAEAlertClient.updateAlert(paramAEApplicationInfo, paramInt1, paramString1, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramString2);
      return localIAEAlertDefinition;
    }
    catch (RemoteException localRemoteException)
    {
      throw new AEException(3001, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localRemoteException);
    }
    finally
    {
      try
      {
        localIAEAlertClient.remove();
      }
      catch (Exception localException3) {}
    }
  }
  
  public IAEAlertDefinition updateAlert(AEApplicationInfo paramAEApplicationInfo, int paramInt1, String paramString, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, byte[] paramArrayOfByte)
    throws AEException
  {
    AEServerInfo[] arrayOfAEServerInfo = null;
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (arrayOfAEServerInfo.length == 0)
    {
      if (!p()) {
        throw new AEException(3000, AEInstance.a.a("ERR_ENGINE_NOT_RUNNING"));
      }
      int i = this.ck.jdMethod_for(paramAEApplicationInfo);
      return a(i, paramInt1, paramString, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, null, paramArrayOfByte);
    }
    ServerInfo localServerInfo = null;
    localDBConnection = this.b7.aL();
    try
    {
      localServerInfo = ServerInfo.jdMethod_void(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if ((localServerInfo == null) || (localServerInfo.equals(this.ca)) || (m()))
    {
      int j = this.ck.jdMethod_for(paramAEApplicationInfo);
      return a(j, paramInt1, paramString, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, null, paramArrayOfByte);
    }
    IAEAlertClient localIAEAlertClient = null;
    try
    {
      localIAEAlertClient = a(localServerInfo);
    }
    catch (Exception localException1)
    {
      throw new AEException(3001, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localException1);
    }
    try
    {
      IAEAlertDefinition localIAEAlertDefinition = localIAEAlertClient.updateAlert(paramAEApplicationInfo, paramInt1, paramString, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramArrayOfByte);
      return localIAEAlertDefinition;
    }
    catch (RemoteException localRemoteException)
    {
      throw new AEException(3001, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localRemoteException);
    }
    finally
    {
      try
      {
        localIAEAlertClient.remove();
      }
      catch (Exception localException3) {}
    }
  }
  
  private IAEAlertDefinition a(int paramInt1, int paramInt2, String paramString1, int paramInt3, int paramInt4, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, String paramString2, byte[] paramArrayOfByte)
    throws AEException
  {
    AlertInstance localAlertInstance = null;
    DBConnection localDBConnection = this.b7.aL();
    int i = 0;
    try
    {
      localAlertInstance = AlertInstance.jdMethod_if(localDBConnection, paramInt2);
      if (localAlertInstance == null) {
        throw new AEException(1026, AEInstance.a.a("ERR_NO_SUCH_ALERT"));
      }
      if (localAlertInstance.aB() == this.ck.q("Universal Alert Engine")) {
        throw new AEException(1018, AEInstance.a.a("ERR_RESERVED_ALERT", Integer.toString(paramInt2)));
      }
      if ((paramInt1 != 0) && (paramInt1 != localAlertInstance.aB())) {
        throw new AEException(1020, AEInstance.a.a("ERR_APP_NOT_OWNER", this.ck.b(paramInt1).getName()));
      }
      long l = localAlertInstance.getNextRaised();
      ArrayList localArrayList1 = null;
      try
      {
        localDBConnection.jdMethod_try(false);
        b localb = new b(paramInt2);
        if (this.b3 != null)
        {
          ArrayList localArrayList2 = new ArrayList(1);
          localArrayList2.add(localAlertInstance);
          localArrayList1 = this.b3.a(localb, localDBConnection, localArrayList2, false);
        }
        else
        {
          localAlertInstance.a(this.b7, localDBConnection, 0L);
        }
        if ((l == 0L) && ((localArrayList1 == null) || (localArrayList1.size() == 0)))
        {
          i = 1;
          throw new AEException(1027, AEInstance.a.a("ERR_ALERT_NOT_ACTIVE"));
        }
        a(localDBConnection, localAlertInstance, paramInt2, paramString1, paramInt3, paramInt4, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramString2, paramArrayOfByte);
        localDBConnection.a0();
        try
        {
          localDBConnection.jdMethod_try(true);
        }
        catch (SQLException localSQLException1) {}
      }
      catch (SQLException localSQLException2)
      {
        try
        {
          localDBConnection.a1();
          if ((localArrayList1 != null) && (this.b3 != null))
          {
            if (localArrayList1.size() > 0)
            {
              localAlertInstance = (AlertInstance)localArrayList1.get(0);
              localAlertInstance.jdMethod_if(this.b7, localDBConnection);
              if (this.b3.a(localDBConnection, (AlertInstance)localArrayList1.get(0), 1, false)) {
                this.b3.ar();
              }
            }
            else
            {
              localAlertInstance = AlertInstance.jdMethod_if(localDBConnection, paramInt2);
              if (this.b3.a(localDBConnection, localAlertInstance, 1, false)) {
                this.b3.ar();
              }
            }
            localDBConnection.a0();
          }
        }
        catch (SQLException localSQLException3) {}
        throw new AEException(1, DBSqlUtils.a(localSQLException2));
      }
      catch (AEException localAEException)
      {
        try
        {
          localDBConnection.a1();
          if ((localArrayList1 != null) && (this.b3 != null))
          {
            if (localArrayList1.size() > 0)
            {
              localAlertInstance = (AlertInstance)localArrayList1.get(0);
              localAlertInstance.jdMethod_if(this.b7, localDBConnection);
              if (this.b3.a(localDBConnection, (AlertInstance)localArrayList1.get(0), 1, false)) {
                this.b3.ar();
              }
            }
            else if (i == 0)
            {
              localAlertInstance = AlertInstance.jdMethod_if(localDBConnection, paramInt2);
              if (this.b3.a(localDBConnection, localAlertInstance, 1, false)) {
                this.b3.ar();
              }
            }
            localDBConnection.a0();
          }
        }
        catch (SQLException localSQLException4) {}
        throw localAEException;
      }
      finally
      {
        try
        {
          localDBConnection.jdMethod_try(true);
        }
        catch (SQLException localSQLException5) {}
      }
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    IAEAlertDefinition localIAEAlertDefinition = jdMethod_null(paramInt2);
    return localIAEAlertDefinition;
  }
  
  void a(DBConnection paramDBConnection, AlertInstance paramAlertInstance, int paramInt1, String paramString1, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, String paramString2, byte[] paramArrayOfByte)
    throws AEException, SQLException
  {
    paramAlertInstance.D(paramString1);
    paramAlertInstance.m(paramInt2);
    paramAlertInstance.r(paramInt3);
    paramAEScheduleInfo = a(paramInt2, paramAEScheduleInfo);
    DeliveryInfo[] arrayOfDeliveryInfo1 = a(paramAlertInstance.aB(), paramAlertInstance.getId(), paramArrayOfIAEDeliveryInfo);
    DeliveryInfo[] arrayOfDeliveryInfo2 = (DeliveryInfo[])paramAlertInstance.getDeliveryInfo();
    for (int i = 0; i < arrayOfDeliveryInfo2.length; i++)
    {
      j = 0;
      for (int k = 0; k < arrayOfDeliveryInfo1.length; k++) {
        if (arrayOfDeliveryInfo2[i].getId() == arrayOfDeliveryInfo1[k].getId())
        {
          j = 1;
          break;
        }
      }
      if (j == 0)
      {
        arrayOfDeliveryInfo2[i].a(1073741824, true);
        arrayOfDeliveryInfo2[i].a(this.b7, paramDBConnection);
      }
    }
    paramAlertInstance.jdMethod_if(arrayOfDeliveryInfo1);
    if ((paramString2 != null) && (paramString2.length() != 0)) {
      paramAlertInstance.E(paramString2);
    } else {
      paramAlertInstance.jdMethod_if(paramArrayOfByte);
    }
    AlertInstance localAlertInstance = AlertInstance.jdMethod_if(paramDBConnection, paramInt1);
    int j = localAlertInstance.getSequence();
    long l = 0L;
    if (paramInt2 == 2) {
      l = paramAEScheduleInfo.getNext(System.currentTimeMillis());
    } else {
      l = paramAEScheduleInfo.getStart();
    }
    paramAlertInstance.jdMethod_new(l);
    paramAlertInstance.jdMethod_if(paramAEScheduleInfo);
    paramAlertInstance.o(j);
    paramAlertInstance.jdMethod_if(this.b7, paramDBConnection);
    if ((this.b3 != null) && (this.b3.a(paramDBConnection, paramAlertInstance, 1, false))) {
      this.b3.ar();
    }
  }
  
  public void jdMethod_void(int paramInt)
    throws AEException
  {
    AEServerInfo[] arrayOfAEServerInfo = null;
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (arrayOfAEServerInfo.length == 0) {
      a(0, paramInt);
    }
    ServerInfo localServerInfo = null;
    localDBConnection = this.b7.aL();
    try
    {
      localServerInfo = ServerInfo.jdMethod_void(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if ((localServerInfo == null) || (localServerInfo.equals(this.ca)) || (m()))
    {
      a(0, paramInt);
      return;
    }
    IAEAlertAdmin localIAEAlertAdmin = null;
    try
    {
      localIAEAlertAdmin = jdMethod_if(localServerInfo);
    }
    catch (Exception localException1)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localException1);
    }
    try
    {
      localIAEAlertAdmin.cancelAlert(paramInt);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localRemoteException);
    }
    finally
    {
      try
      {
        localIAEAlertAdmin.remove();
      }
      catch (Exception localException3) {}
    }
  }
  
  public void cancelAlert(AEApplicationInfo paramAEApplicationInfo, int paramInt)
    throws AEException
  {
    AEServerInfo[] arrayOfAEServerInfo = null;
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (arrayOfAEServerInfo.length == 0)
    {
      if (!p()) {
        throw new AEException(3000, AEInstance.a.a("ERR_ENGINE_NOT_RUNNING"));
      }
      int i = this.ck.jdMethod_for(paramAEApplicationInfo);
      a(i, paramInt);
      return;
    }
    ServerInfo localServerInfo = null;
    localDBConnection = this.b7.aL();
    try
    {
      localServerInfo = ServerInfo.jdMethod_void(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if ((localServerInfo == null) || (localServerInfo.equals(this.ca)) || (m()))
    {
      int j = this.ck.jdMethod_for(paramAEApplicationInfo);
      a(j, paramInt);
      return;
    }
    IAEAlertClient localIAEAlertClient = null;
    try
    {
      localIAEAlertClient = a(localServerInfo);
    }
    catch (Exception localException1)
    {
      throw new AEException(3001, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localException1);
    }
    try
    {
      localIAEAlertClient.cancelAlert(paramAEApplicationInfo, paramInt);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new AEException(3001, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localRemoteException);
    }
    finally
    {
      try
      {
        localIAEAlertClient.remove();
      }
      catch (Exception localException3) {}
    }
  }
  
  private void a(int paramInt1, int paramInt2)
    throws AEException
  {
    AlertInstance localAlertInstance = null;
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      localDBConnection.jdMethod_try(false);
      localAlertInstance = AlertInstance.jdMethod_if(localDBConnection, paramInt2);
      if (localAlertInstance == null) {
        throw new AEException(1026, AEInstance.a.a("ERR_NO_SUCH_ALERT"));
      }
      if (localAlertInstance.aB() == this.ck.q("Universal Alert Engine")) {
        throw new AEException(1018, AEInstance.a.a("ERR_RESERVED_ALERT", Integer.toString(paramInt2)));
      }
      if ((paramInt1 != 0) && (paramInt1 != localAlertInstance.aB())) {
        throw new AEException(1020, AEInstance.a.a("ERR_APP_NOT_OWNER", this.ck.b(paramInt1).getName()));
      }
      if (localAlertInstance.getNextRaised() > 0L)
      {
        localAlertInstance.jdMethod_for(2, true);
        if (this.b3 == null)
        {
          localAlertInstance.jdMethod_new(0L);
          localAlertInstance.jdMethod_if(this.b7, localDBConnection);
        }
      }
      ArrayList localArrayList1 = null;
      if (this.b3 != null)
      {
        ArrayList localArrayList2 = new ArrayList(1);
        localArrayList2.add(localAlertInstance);
        b localb = new b(localAlertInstance.getId());
        localArrayList1 = this.b3.a(localb, localDBConnection, localArrayList2, true);
      }
      if ((!localAlertInstance.l(2)) && (localArrayList1 != null) && (localArrayList1.size() > 0))
      {
        localAlertInstance = (AlertInstance)localArrayList1.get(0);
        localAlertInstance.jdMethod_new(0L);
        localAlertInstance.jdMethod_for(2, true);
        localAlertInstance.jdMethod_if(this.b7, localDBConnection);
      }
      localDBConnection.a0();
    }
    catch (SQLException localSQLException1)
    {
      try
      {
        localDBConnection.a1();
      }
      catch (SQLException localSQLException2) {}
      throw new AEException(1, DBSqlUtils.a(localSQLException1));
    }
    catch (AEException localAEException)
    {
      try
      {
        localDBConnection.a1();
      }
      catch (SQLException localSQLException3) {}
      throw localAEException;
    }
    finally
    {
      try
      {
        localDBConnection.jdMethod_try(true);
      }
      catch (SQLException localSQLException4) {}
      this.b7.k(localDBConnection);
    }
  }
  
  public void jdMethod_if(String paramString1, String paramString2)
    throws AEException
  {
    if (this.ca == null)
    {
      a(paramString1, paramString2);
      return;
    }
    ServerInfo localServerInfo = null;
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      localServerInfo = ServerInfo.jdMethod_void(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if ((localServerInfo == null) || (localServerInfo.equals(this.ca)) || (m()))
    {
      a(paramString1, paramString2);
      return;
    }
    IAEAlertAdmin localIAEAlertAdmin = null;
    try
    {
      localIAEAlertAdmin = jdMethod_if(localServerInfo);
    }
    catch (Exception localException1)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localException1);
    }
    try
    {
      localIAEAlertAdmin.cancelUserAlerts(paramString1, paramString2);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new AEException(2002, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", localServerInfo.getServerURL()), localRemoteException);
    }
    finally
    {
      try
      {
        localIAEAlertAdmin.remove();
      }
      catch (Exception localException3) {}
    }
  }
  
  private void a(String paramString1, String paramString2)
    throws AEException
  {
    int i = this.ck.q(paramString1);
    if (i == this.ck.q("Universal Alert Engine")) {
      throw new AEException(1018, AEInstance.a.a("ERR_RESERVED_APPLICATION", "Universal Alert Engine"));
    }
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      localDBConnection.jdMethod_try(false);
      ArrayList localArrayList = AlertInstance.a(localDBConnection, i, paramString2, true);
      a(localDBConnection, localArrayList, new c(i, paramString2, true, 0, 0));
      localDBConnection.a0();
    }
    catch (SQLException localSQLException1)
    {
      try
      {
        localDBConnection.a1();
      }
      catch (SQLException localSQLException2) {}
      throw new AEException(1, DBSqlUtils.a(localSQLException1));
    }
    catch (AEException localAEException)
    {
      try
      {
        localDBConnection.a1();
      }
      catch (SQLException localSQLException3) {}
      throw localAEException;
    }
    finally
    {
      try
      {
        localDBConnection.jdMethod_try(true);
      }
      catch (SQLException localSQLException4) {}
      this.b7.k(localDBConnection);
    }
  }
  
  public void a(DBConnection paramDBConnection, ArrayList paramArrayList, c paramc)
    throws AEException
  {
    TreeSet localTreeSet = new TreeSet(b2);
    Iterator localIterator = paramArrayList.iterator();
    Object localObject;
    while (localIterator.hasNext())
    {
      localObject = (AlertInstance)localIterator.next();
      ((AlertInstance)localObject).jdMethod_for(2, true);
      localTreeSet.add(localObject);
      if (this.b3 == null)
      {
        ((AlertInstance)localObject).jdMethod_new(0L);
        ((AlertInstance)localObject).jdMethod_if(this.b7, paramDBConnection);
      }
    }
    if (this.b3 != null)
    {
      localObject = this.b3.a(paramc, paramDBConnection, paramArrayList, true);
      localIterator = ((ArrayList)localObject).iterator();
      while (localIterator.hasNext())
      {
        AlertInstance localAlertInstance = (AlertInstance)localIterator.next();
        if (!localTreeSet.contains(localAlertInstance))
        {
          localAlertInstance.jdMethod_for(2, true);
          localAlertInstance.jdMethod_new(0L);
          localAlertInstance.jdMethod_if(this.b7, paramDBConnection);
          localTreeSet.add(localAlertInstance);
        }
      }
    }
  }
  
  public IAEAuditInfo[] jdMethod_if(Date paramDate1, Date paramDate2)
    throws AEException
  {
    a(paramDate1, paramDate2);
    int i = this.ck.q("Universal Alert Engine");
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      IAEAuditInfo[] arrayOfIAEAuditInfo = this.cc.a(localDBConnection, i, paramDate1.getTime(), paramDate2.getTime());
      return arrayOfIAEAuditInfo;
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
  }
  
  public IAEAuditInfo[] a(String paramString, Date paramDate1, Date paramDate2)
    throws AEException
  {
    int i = this.ck.q(paramString);
    if (i == this.ck.q("Universal Alert Engine")) {
      throw new AEException(1018, AEInstance.a.a("ERR_RESERVED_APPLICATION", "Universal Alert Engine"));
    }
    a(paramDate1, paramDate2);
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      IAEAuditInfo[] arrayOfIAEAuditInfo = this.cc.jdMethod_if(localDBConnection, i, paramDate1.getTime(), paramDate2.getTime());
      return arrayOfIAEAuditInfo;
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
  }
  
  public IAEAuditInfo[] a(String paramString1, String paramString2, Date paramDate1, Date paramDate2)
    throws AEException
  {
    int i = this.ck.q(paramString1);
    return a(i, paramString2, paramDate1, paramDate2);
  }
  
  public IAEAuditInfo[] jdMethod_if(String paramString, Date paramDate1, Date paramDate2)
    throws AEException
  {
    a(paramDate1, paramDate2);
    int i = this.ck.q("Universal Alert Engine");
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      IAEAuditInfo[] arrayOfIAEAuditInfo = this.cc.a(localDBConnection, paramString, i, paramDate1.getTime(), paramDate2.getTime());
      return arrayOfIAEAuditInfo;
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
  }
  
  public IAEAuditInfo[] a(AEApplicationInfo paramAEApplicationInfo, String paramString, Date paramDate1, Date paramDate2)
    throws AEException
  {
    int i = this.ck.jdMethod_for(paramAEApplicationInfo);
    return a(i, paramString, paramDate1, paramDate2);
  }
  
  private IAEAuditInfo[] a(int paramInt, String paramString, Date paramDate1, Date paramDate2)
    throws AEException
  {
    if (paramInt == this.ck.q("Universal Alert Engine")) {
      throw new AEException(1018, AEInstance.a.a("ERR_RESERVED_APPLICATION", "Universal Alert Engine"));
    }
    a(paramDate1, paramDate2);
    DBConnection localDBConnection = this.b7.aL();
    try
    {
      IAEAuditInfo[] arrayOfIAEAuditInfo = this.cc.a(localDBConnection, paramInt, paramString, paramDate1.getTime(), paramDate2.getTime());
      return arrayOfIAEAuditInfo;
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
  }
  
  public void a(Properties paramProperties)
    throws AEException
  {
    int i = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    DBConnection localDBConnection = this.b7.aL();
    AEServerInfo[] arrayOfAEServerInfo = null;
    arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    boolean bool = false;
    try
    {
      bool = this.ck.jdMethod_do(paramProperties);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if ((arrayOfAEServerInfo.length == 0) || (!bool)) {
      return;
    }
    int j = 0;
    while (j < arrayOfAEServerInfo.length)
    {
      ServerInfo localServerInfo = (ServerInfo)arrayOfAEServerInfo[j];
      if ((this.ca == null) || (!localServerInfo.equals(this.ca)))
      {
        IAEAlertAdmin localIAEAlertAdmin = null;
        try
        {
          localIAEAlertAdmin = jdMethod_if(localServerInfo);
          localIAEAlertAdmin.setEngineProperties(paramProperties);
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException1) {}
          j++;
        }
        catch (Exception localException2)
        {
          AELog.a(localException2, "Unable to set engine properties", 0);
          i = 1;
          localStringBuffer.append(localServerInfo.getServerURL());
          localStringBuffer.append("\n");
        }
        finally
        {
          try
          {
            localIAEAlertAdmin.remove();
          }
          catch (Exception localException4) {}
        }
      }
    }
    if (i != 0) {
      throw new AEException(1034, AEInstance.a.a("ERR_OPERATION_NOT_COMPLETED", AEInstance.a.a("OPERATION_UPDATEENGINEPROPS"), localStringBuffer.toString()));
    }
  }
  
  public Properties v()
    throws AEException
  {
    return this.ck.T();
  }
  
  public AEServerInfo[] jdMethod_long()
    throws AEException
  {
    DBConnection localDBConnection = this.b7.aL();
    AEServerInfo[] arrayOfAEServerInfo = ServerInfo.jdMethod_goto(localDBConnection);
    this.b7.k(localDBConnection);
    return arrayOfAEServerInfo;
  }
  
  public AEServerInfo a(AEServerInfo paramAEServerInfo)
    throws AEException
  {
    ServerInfo localServerInfo1 = new ServerInfo(paramAEServerInfo);
    String str = localServerInfo1.getServerURL();
    if (str == null) {
      throw new AEException(1030, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_SERVER_URL")));
    }
    DBConnection localDBConnection = this.b7.aL();
    ServerInfo localServerInfo2 = null;
    try
    {
      localServerInfo2 = ServerInfo.a(str, localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    localDBConnection = this.b7.aL();
    if (localServerInfo2 != null) {
      throw new AEException(1031, AEInstance.a.a("ERR_CLUSTER_NODE_ALREADY_EXISTS", str));
    }
    try
    {
      localServerInfo1.jdMethod_if(this.b7, localDBConnection, true);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    return localServerInfo1;
  }
  
  public AEServerInfo a(String paramString, AEServerInfo paramAEServerInfo)
    throws AEException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new AEException(1030, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_SERVER_URL")));
    }
    DBConnection localDBConnection = this.b7.aL();
    ServerInfo localServerInfo1 = null;
    ServerInfo localServerInfo2 = null;
    try
    {
      localServerInfo1 = ServerInfo.a(paramString, localDBConnection);
      localServerInfo2 = ServerInfo.a(paramAEServerInfo.getServerURL(), localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (localServerInfo1 == null) {
      throw new AEException(1030, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString));
    }
    if ((!paramString.equals(paramAEServerInfo.getServerURL())) && (localServerInfo2 != null)) {
      throw new AEException(1031, AEInstance.a.a("ERR_CLUSTER_NODE_ALREADY_EXISTS", paramAEServerInfo.getServerURL()));
    }
    localDBConnection = this.b7.aL();
    localServerInfo1.setUserName(paramAEServerInfo.getUserName());
    localServerInfo1.setPassword(paramAEServerInfo.getPassword());
    localServerInfo1.setServerURL(paramAEServerInfo.getServerURL());
    localServerInfo1.setContextFactory(paramAEServerInfo.getContextFactory());
    localServerInfo1.setAdminJNDI(paramAEServerInfo.getAdminJNDI());
    localServerInfo1.setClientJNDI(paramAEServerInfo.getClientJNDI());
    localServerInfo1.t(paramAEServerInfo.getServerRole());
    try
    {
      localServerInfo1.jdMethod_if(this.b7, localDBConnection, true);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    return localServerInfo1;
  }
  
  public void h(String paramString)
    throws AEException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new AEException(1030, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_SERVER_URL")));
    }
    DBConnection localDBConnection = this.b7.aL();
    ServerInfo localServerInfo = null;
    boolean bool;
    try
    {
      localServerInfo = ServerInfo.a(paramString, localDBConnection);
      if (localServerInfo == null) {
        throw new AEException(1030, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_SERVER_URL")));
      }
      if (localServerInfo.equals(this.ca))
      {
        if (this.cd != null) {
          throw new AEException(1022, AEInstance.a.a("ERR_INCORRECT_STATE", AEInstance.a.a("PARAM_STATE_STOPPED"), AEInstance.a.a("PARAM_STATE_REMOVED")));
        }
      }
      else
      {
        IAEAlertAdmin localIAEAlertAdmin = null;
        int i = 1;
        try
        {
          localIAEAlertAdmin = jdMethod_if(localServerInfo);
        }
        catch (Exception localException1)
        {
          i = 0;
        }
        if (i != 0) {
          try
          {
            if ((localIAEAlertAdmin.isSuspended()) || (localIAEAlertAdmin.isRunning())) {
              throw new AEException(1022, AEInstance.a.a("ERR_INCORRECT_STATE", AEInstance.a.a("PARAM_STATE_STOPPED"), AEInstance.a.a("PARAM_STATE_REMOVED")));
            }
          }
          catch (RemoteException localRemoteException) {}
        }
        try
        {
          localIAEAlertAdmin.remove();
        }
        catch (Exception localException2) {}
      }
      bool = localServerInfo.c(localDBConnection);
    }
    finally
    {
      this.b7.k(localDBConnection);
    }
    if (!bool) {
      throw new AEException(1030, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString));
    }
  }
  
  public void j(String paramString)
    throws AEException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new AEException(1030, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_SERVER_URL")));
    }
    if (!m()) {
      throw new AEException(2003, AEInstance.a.a("ERR_CANT_CHANGE_PRIMARY"));
    }
    DBConnection localDBConnection = this.b7.aL();
    ServerInfo localServerInfo = null;
    localServerInfo = ServerInfo.a(paramString, localDBConnection);
    if (localServerInfo == null) {
      throw new AEException(1030, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString));
    }
    boolean bool = localServerInfo.jdMethod_char(localDBConnection);
    this.b7.k(localDBConnection);
    if (!bool) {
      throw new AEException(1030, AEInstance.a.a("ERR_CLUSTER_NODE_NOT_FOUND", paramString));
    }
  }
  
  private void a(DBConnection paramDBConnection)
    throws AEException
  {
    AELog.a("AlertEngine.doRecovery() - starting...", 1);
    AlertRecovery.jdMethod_do(this.b7, paramDBConnection);
    AELog.a("AlertEngine.doRecovery() - sequence reset phase complete. Begginning re-dispatch phase...", 1);
    ArrayList localArrayList = AlertRecovery.e(paramDBConnection);
    for (int i = 0; i < localArrayList.size(); i++)
    {
      IAEAlertInstance localIAEAlertInstance = (IAEAlertInstance)localArrayList.get(i);
      this.b9.a(localIAEAlertInstance);
      AELog.a("AlertEngine.doRecovery() - re-dispatched alert ", localIAEAlertInstance.getId(), " sequence ", localIAEAlertInstance.getSequence(), 1);
    }
    AELog.a("AlertEngine.doRecovery() - recovery complete.", 1);
  }
  
  private AEScheduleInfo a(int paramInt, AEScheduleInfo paramAEScheduleInfo)
    throws AEException
  {
    switch (paramInt)
    {
    case 0: 
      long l = System.currentTimeMillis();
      paramAEScheduleInfo = new AEScheduleInfo(l, l, 0L, 0, TimeZone.getDefault(), true);
      break;
    case 1: 
      a(paramAEScheduleInfo, true);
      if ((paramAEScheduleInfo.getStart() != paramAEScheduleInfo.getEnd()) || (paramAEScheduleInfo.getInterval() != 0L) || (paramAEScheduleInfo.getIntervalType() != 0)) {
        paramAEScheduleInfo = new AEScheduleInfo(paramAEScheduleInfo.getStart(), paramAEScheduleInfo.getStart(), 0L, 0, paramAEScheduleInfo.getRegularTimeZone(), paramAEScheduleInfo.respectsDST());
      }
      break;
    case 2: 
      a(paramAEScheduleInfo, false);
      break;
    default: 
      throw new AEException(1016, AEInstance.a.a("ERR_BAD_ALERT_TYPE"));
    }
    return paramAEScheduleInfo;
  }
  
  private void a(AEScheduleInfo paramAEScheduleInfo, boolean paramBoolean)
    throws AEException
  {
    if (paramAEScheduleInfo == null) {
      throw new AEException(1012, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_AESCHEDULEINFO")));
    }
    if (paramAEScheduleInfo.getRegularTimeZone() == null) {
      throw new AEException(1011, AEInstance.a.a("ERR_SCHED_TZ_REQUIRED"));
    }
    if (paramBoolean) {
      return;
    }
    if (paramAEScheduleInfo.getStart() > paramAEScheduleInfo.getEnd()) {
      throw new AEException(1013, AEInstance.a.a("ERR_BAD_START_END"));
    }
    if ((paramAEScheduleInfo.getInterval() < 1L) || ((paramAEScheduleInfo.getIntervalType() == 0) && (paramAEScheduleInfo.getInterval() < 1000L))) {
      throw new AEException(1014, AEInstance.a.a("ERR_SCHED_BAD_INTERVAL"));
    }
    if (paramAEScheduleInfo.getIntervalType() == 3)
    {
      int i = paramAEScheduleInfo.getFirstSemiMonthlyDay();
      int j = paramAEScheduleInfo.getSecondSemiMonthlyDay();
      if ((i == -1) || (j == -1)) {
        throw new AEException(1033, AEInstance.a.a("ERR_SCHED_BAD_SEMI_MONTHLY_DAYS"));
      }
      if (i == j) {
        throw new AEException(1033, AEInstance.a.a("ERR_SCHED_BAD_SEMI_MONTHLY_DAYS"));
      }
    }
    switch (paramAEScheduleInfo.getIntervalType())
    {
    case 0: 
    case 1: 
    case 2: 
    case 3: 
      break;
    default: 
      throw new AEException(1015, AEInstance.a.a("ERR_SCHED_BAD_INTERVAL_TYPE"));
    }
  }
  
  public void o()
    throws AEException
  {
    this.cc.jdMethod_case();
  }
  
  SchedulerEngine q()
  {
    return this.b3;
  }
  
  AuditEngine j()
  {
    return this.cc;
  }
  
  ClusterHeartbeat G()
  {
    return this.cd;
  }
  
  private void a(Date paramDate1, Date paramDate2)
    throws AEException
  {
    if (paramDate1 == null) {
      throw new AEException(1012, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_DATE_BEGIN")));
    }
    if (paramDate2 == null) {
      throw new AEException(1012, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_DATE_END")));
    }
    if (paramDate1.getTime() > paramDate2.getTime()) {
      throw new AEException(1013, AEInstance.a.a("ERR_BAD_START_END"));
    }
  }
  
  public DeliveryEngine E()
  {
    return this.b9;
  }
  
  private static class c
    implements IUnaryPredicate
  {
    int jdField_do;
    String jdField_if;
    boolean jdField_int;
    int jdField_new;
    int jdField_for;
    
    public c(int paramInt1, String paramString, boolean paramBoolean, int paramInt2, int paramInt3)
    {
      this.jdField_do = paramInt1;
      this.jdField_if = paramString;
      this.jdField_int = paramBoolean;
      this.jdField_new = paramInt2;
      this.jdField_for = paramInt3;
    }
    
    public boolean a(Object paramObject)
    {
      try
      {
        AlertInstance localAlertInstance = (AlertInstance)paramObject;
        if (localAlertInstance.getId() <= this.jdField_for) {
          return false;
        }
        if ((this.jdField_do != 0) && (localAlertInstance.aB() != this.jdField_do)) {
          return false;
        }
        if (this.jdField_int)
        {
          String str = localAlertInstance.getUserId();
          if ((this.jdField_if == null) && (str != null)) {
            return false;
          }
          if (this.jdField_if != null)
          {
            if (str == null) {
              return false;
            }
            if (!this.jdField_if.equals(str)) {
              return false;
            }
          }
        }
        if (this.jdField_new != 0)
        {
          int i = 0;
          DeliveryInfo[] arrayOfDeliveryInfo = (DeliveryInfo[])localAlertInstance.getDeliveryInfo();
          for (int j = 0; j < arrayOfDeliveryInfo.length; j++) {
            if (arrayOfDeliveryInfo[j].jdField_if() == this.jdField_new)
            {
              i = 1;
              break;
            }
          }
          if (i == 0) {
            return false;
          }
        }
      }
      catch (ClassCastException localClassCastException)
      {
        return false;
      }
      return true;
    }
  }
  
  static class b
    implements IUnaryPredicate
  {
    private int a;
    
    public b(int paramInt)
    {
      this.a = paramInt;
    }
    
    public boolean a(Object paramObject)
    {
      AlertInstance localAlertInstance = null;
      try
      {
        localAlertInstance = (AlertInstance)paramObject;
      }
      catch (ClassCastException localClassCastException)
      {
        return false;
      }
      return localAlertInstance.getId() == this.a;
    }
  }
  
  private static class a
    implements Comparator
  {
    private a() {}
    
    public int compare(Object paramObject1, Object paramObject2)
    {
      try
      {
        AlertInstance localAlertInstance1 = (AlertInstance)paramObject1;
        AlertInstance localAlertInstance2 = (AlertInstance)paramObject2;
        if (localAlertInstance1.getId() < localAlertInstance2.getId()) {
          return -1;
        }
        if (localAlertInstance1.getId() == localAlertInstance2.getId()) {
          return 0;
        }
        return 1;
      }
      catch (ClassCastException localClassCastException) {}
      return 1;
    }
    
    a(AlertEngine.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.engine.AlertEngine
 * JD-Core Version:    0.7.0.1
 */