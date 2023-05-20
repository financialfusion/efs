package com.ffusion.alert.engine;

import com.ffusion.alert.db.AERepository;
import com.ffusion.alert.db.AlertInstance;
import com.ffusion.alert.db.AppInfoCache;
import com.ffusion.alert.db.ChannelInfo;
import com.ffusion.alert.db.DBConnection;
import com.ffusion.alert.db.DBSqlUtils;
import com.ffusion.alert.db.DeliveryInfo;
import com.ffusion.alert.interfaces.AEApplicationInfo;
import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.AEScheduleInfo;
import com.ffusion.alert.interfaces.IAEAlertDefinition;
import com.ffusion.alert.interfaces.IAEChannelInfo;
import com.ffusion.alert.interfaces.IAEPropsConstants;
import com.ffusion.alert.shared.AEIntMap;
import com.ffusion.alert.shared.AELog;
import com.ffusion.alert.shared.AELogParams;
import com.ffusion.alert.shared.AEResourceBundle;
import com.ffusion.alert.shared.AEUtils;
import java.sql.SQLException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

public class AEConfigManager
  implements IAEErrConstants, IAEPropsConstants
{
  private static int cs = 3;
  private AppInfoCache cp;
  private HashMap cr;
  private AEIntMap cm;
  private AERepository ct;
  private DeliveryEngine cl;
  private AlertEngine cn;
  private Properties cq;
  private static Properties co = new Properties();
  
  public AEConfigManager(AERepository paramAERepository, AlertEngine paramAlertEngine)
    throws AEException
  {
    this.ct = paramAERepository;
    this.cn = paramAlertEngine;
  }
  
  public void a(DeliveryEngine paramDeliveryEngine)
    throws AEException
  {
    this.cl = paramDeliveryEngine;
  }
  
  public void jdMethod_if(DBConnection paramDBConnection)
    throws AEException
  {
    this.cp = new AppInfoCache();
    this.cp.jdMethod_case(paramDBConnection);
    this.cr = new HashMap();
    this.cm = new AEIntMap();
    this.cq = this.ct.g(paramDBConnection);
    if (!jdMethod_if(T())) {
      R();
    }
    jdMethod_do(paramDBConnection);
  }
  
  public int jdMethod_for(AEApplicationInfo paramAEApplicationInfo)
    throws AEException
  {
    return this.cp.jdMethod_int(paramAEApplicationInfo);
  }
  
  public boolean a(DBConnection paramDBConnection, AEApplicationInfo paramAEApplicationInfo, boolean paramBoolean)
    throws AEException
  {
    if (paramAEApplicationInfo == null) {
      throw new AEException(1012, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_AEAPPLICATIONINFO")));
    }
    if (paramBoolean)
    {
      if (paramAEApplicationInfo.getName().equals("Universal Alert Engine")) {
        throw new AEException(14, AERepository.H("ERR_NAME_EXISTS"));
      }
      if (this.cp.G(paramAEApplicationInfo.getName())) {
        throw new AEException(14, AERepository.H("ERR_NAME_EXISTS"));
      }
      if (this.cp.jdMethod_if(paramDBConnection, paramAEApplicationInfo))
      {
        this.cp.a(paramDBConnection, paramAEApplicationInfo);
        return false;
      }
      this.cp.a(this.ct, paramDBConnection, paramAEApplicationInfo, false);
      return true;
    }
    this.cp.a(this.ct, paramDBConnection, paramAEApplicationInfo, true);
    return true;
  }
  
  public int a(DBConnection paramDBConnection, String paramString, boolean paramBoolean)
    throws AEException
  {
    synchronized (this.cr)
    {
      int i = 0;
      try
      {
        i = q(paramString);
        Iterator localIterator = this.cr.values().iterator();
        while (localIterator.hasNext())
        {
          ChannelInfo localChannelInfo = (ChannelInfo)localIterator.next();
          if ((localChannelInfo.isInstalled()) && (localChannelInfo.jdMethod_do() == i)) {
            throw new AEException(1019, AEInstance.a.a("ERR_DELETING_APP_IN_USE", paramString));
          }
        }
      }
      catch (AEException localAEException)
      {
        if (localAEException.getErrorCode() != 13) {
          throw new AEException(localAEException.getErrorCode(), localAEException.getMessage(), localAEException);
        }
      }
      boolean bool = this.cp.jdMethod_if(paramDBConnection, paramString, paramBoolean);
      if (bool) {
        return i;
      }
      return -1 * i;
    }
  }
  
  public boolean a(DBConnection paramDBConnection, String paramString, AEApplicationInfo paramAEApplicationInfo, boolean paramBoolean)
    throws AEException
  {
    if (paramAEApplicationInfo == null) {
      throw new AEException(1012, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_AEAPPLICATIONINFO")));
    }
    if ((!paramString.equals(paramAEApplicationInfo.getName())) && (this.cp.G(paramAEApplicationInfo.getName()))) {
      throw new AEException(14, AERepository.H("ERR_NAME_EXISTS"));
    }
    synchronized (this.cr)
    {
      if (paramBoolean)
      {
        if (this.cp.jdMethod_if(paramDBConnection, paramAEApplicationInfo))
        {
          this.cp.jdMethod_if(paramString, paramAEApplicationInfo);
          int i = q(paramAEApplicationInfo.getName());
          Iterator localIterator = this.cr.values().iterator();
          while (localIterator.hasNext())
          {
            localObject1 = (ChannelInfo)localIterator.next();
            if (((ChannelInfo)localObject1).jdMethod_do() == i) {
              ((ChannelInfo)localObject1).a(paramAEApplicationInfo.getName());
            }
          }
          return false;
        }
        bool = this.cp.a(paramDBConnection, paramString, paramAEApplicationInfo, paramBoolean, false);
        return bool;
      }
      boolean bool = this.cp.a(paramDBConnection, paramString, paramAEApplicationInfo, paramBoolean, true);
      int j = q(paramAEApplicationInfo.getName());
      Object localObject1 = this.cr.values().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        ChannelInfo localChannelInfo = (ChannelInfo)((Iterator)localObject1).next();
        if (localChannelInfo.jdMethod_do() == j) {
          localChannelInfo.a(paramAEApplicationInfo.getName());
        }
      }
      return bool;
    }
  }
  
  public void jdMethod_int(DBConnection paramDBConnection)
    throws AEException
  {
    this.cp.jdMethod_byte(paramDBConnection);
  }
  
  public AEApplicationInfo[] P()
    throws AEException
  {
    return this.cp.aE();
  }
  
  public int q(String paramString)
    throws AEException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new AEException(1012, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_STRING_APPNAME")));
    }
    return this.cp.F(paramString);
  }
  
  public AEApplicationInfo b(int paramInt)
    throws AEException
  {
    return this.cp.s(paramInt);
  }
  
  private void jdMethod_do(DBConnection paramDBConnection)
    throws AEException
  {
    synchronized (this.cr)
    {
      this.cr.clear();
      this.cm.jdMethod_if();
      ArrayList localArrayList = ChannelInfo.jdMethod_do(paramDBConnection);
      for (int i = 0; i < localArrayList.size(); i++)
      {
        ChannelInfo localChannelInfo = (ChannelInfo)localArrayList.get(i);
        if (localChannelInfo.jdMethod_do() != 0) {
          localChannelInfo.a(b(localChannelInfo.jdMethod_do()).getName());
        }
        this.cr.put(localChannelInfo.getChannelName(), localChannelInfo);
        this.cm.jdMethod_if(localChannelInfo.getId(), localChannelInfo);
      }
    }
  }
  
  private void jdMethod_try(DBConnection paramDBConnection)
    throws AEException
  {
    synchronized (this.cr)
    {
      ArrayList localArrayList = new ArrayList(this.cr.size());
      Iterator localIterator = this.cr.values().iterator();
      while (localIterator.hasNext())
      {
        ChannelInfo localChannelInfo1 = (ChannelInfo)localIterator.next();
        if (localChannelInfo1.isLoaded()) {
          localArrayList.add(localChannelInfo1.getChannelName());
        }
      }
      jdMethod_do(paramDBConnection);
      int i = localArrayList.size();
      for (int j = 0; j < i; j++)
      {
        String str = (String)localArrayList.get(j);
        ChannelInfo localChannelInfo2 = (ChannelInfo)this.cr.get(str);
        if (localChannelInfo2 != null) {
          localChannelInfo2.a(true);
        }
      }
    }
  }
  
  public IAEChannelInfo a(ChannelInfo paramChannelInfo, boolean paramBoolean)
    throws AEException
  {
    ChannelInfo localChannelInfo1 = null;
    String str = paramChannelInfo.getApplicationName();
    if ((str != null) && (str.length() != 0))
    {
      if (str.equals("Universal Alert Engine")) {
        throw new AEException(1018, AEInstance.a.a("ERR_RESERVED_APPLICATION", "Universal Alert Engine"));
      }
      paramChannelInfo.jdMethod_int(q(str));
    }
    synchronized (this.cr)
    {
      if (this.cr.containsKey(paramChannelInfo.getChannelName()))
      {
        localObject1 = w(paramChannelInfo.getChannelName());
        if ((localObject1 != null) && (((ChannelInfo)localObject1).a() == 0)) {
          throw new AEException(1029, AEInstance.a.a("ERR_INVALID_CHANNEL_NAME"));
        }
        throw new AEException(1006, AEInstance.a.a("ERR_CHANNEL_NAME_EXISTS"));
      }
      Object localObject1 = this.ct.aL();
      ChannelInfo localChannelInfo2 = ChannelInfo.a((DBConnection)localObject1, paramChannelInfo.getChannelName());
      if (localChannelInfo2 == null)
      {
        try
        {
          paramChannelInfo.a(this.ct, (DBConnection)localObject1);
          if (!paramBoolean)
          {
            this.cr.put(paramChannelInfo.getChannelName(), paramChannelInfo);
            this.cm.jdMethod_if(paramChannelInfo.getId(), paramChannelInfo);
          }
        }
        finally
        {
          this.ct.k((DBConnection)localObject1);
        }
      }
      else
      {
        this.cr.put(localChannelInfo2.getChannelName(), localChannelInfo2);
        this.cm.jdMethod_if(localChannelInfo2.getId(), localChannelInfo2);
        localChannelInfo2.a(str);
      }
      if (localChannelInfo2 != null) {
        return null;
      }
      localChannelInfo1 = (ChannelInfo)paramChannelInfo.clone();
    }
    return localChannelInfo1;
  }
  
  public IAEChannelInfo jdMethod_do(String paramString, boolean paramBoolean)
    throws AEException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new AEException(1012, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_STRING_CHANNELNAME")));
    }
    ChannelInfo localChannelInfo1 = null;
    int i = q("Universal Alert Engine");
    synchronized (this.cr)
    {
      ChannelInfo localChannelInfo2 = w(paramString);
      if (localChannelInfo2 == null)
      {
        if (paramBoolean) {
          return null;
        }
        throw new AEException(1007, AEInstance.a.a("ERR_CHANNEL_NAME_NOT_FOUND"));
      }
      if (localChannelInfo2.jdMethod_do() == i) {
        throw new AEException(1018, AEInstance.a.a("ERR_RESERVED_CHANNEL", paramString));
      }
      if (localChannelInfo2.isLoaded()) {
        throw new AEException(1002, AEInstance.a.a("ERR_CR_ALREADY_LOADED", paramString));
      }
      DBConnection localDBConnection = this.ct.aL();
      ChannelInfo localChannelInfo3 = ChannelInfo.a(localDBConnection, paramString);
      int j = 0;
      int k = localChannelInfo2.a();
      localChannelInfo2.jdMethod_do(0);
      try
      {
        localChannelInfo2.a(this.ct, localDBConnection);
        if ((localChannelInfo3 != null) && (localChannelInfo3.a() > 0)) {
          j = 1;
        }
      }
      catch (AEException localAEException)
      {
        localChannelInfo2.jdMethod_do(k);
        throw new AEException(localAEException.getErrorCode(), localAEException);
      }
      finally
      {
        this.ct.k(localDBConnection);
      }
      if (j != 0) {
        localChannelInfo1 = (ChannelInfo)localChannelInfo2.clone();
      } else {
        localChannelInfo1 = null;
      }
    }
    return localChannelInfo1;
  }
  
  public void jdMethod_new(DBConnection paramDBConnection)
    throws AEException
  {
    synchronized (this.cr)
    {
      ChannelInfo.a(paramDBConnection);
      jdMethod_try(paramDBConnection);
    }
  }
  
  public IAEChannelInfo n(String paramString)
    throws AEException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new AEException(1012, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_STRING_CHANNELNAME")));
    }
    ChannelInfo localChannelInfo1 = null;
    int i = q("Universal Alert Engine");
    synchronized (this.cr)
    {
      ChannelInfo localChannelInfo2 = w(paramString);
      if (localChannelInfo2 == null) {
        throw new AEException(1007, AEInstance.a.a("ERR_CHANNEL_NAME_NOT_FOUND"));
      }
      if (localChannelInfo2.jdMethod_do() == i) {
        throw new AEException(1018, AEInstance.a.a("ERR_RESERVED_CHANNEL", paramString));
      }
      if (localChannelInfo2.isLoaded()) {
        throw new AEException(1002, AEInstance.a.a("ERR_CR_ALREADY_LOADED", paramString));
      }
      if (!localChannelInfo2.isLoadable())
      {
        localChannelInfo2.jdMethod_if(true);
        DBConnection localDBConnection = this.ct.aL();
        try
        {
          localChannelInfo2.a(this.ct, localDBConnection);
        }
        finally
        {
          this.ct.k(localDBConnection);
        }
      }
      if (this.cl != null) {
        try
        {
          this.cl.jdMethod_if(localChannelInfo2);
          localChannelInfo2.a(true);
          localChannelInfo2.jdMethod_do(false);
        }
        catch (AEException localAEException)
        {
          localChannelInfo2.jdMethod_do(true);
          throw new AEException(localAEException.getErrorCode(), localAEException.getMessage(), localAEException);
        }
      }
      localChannelInfo1 = (ChannelInfo)localChannelInfo2.clone();
    }
    return localChannelInfo1;
  }
  
  IAEChannelInfo t(String paramString)
    throws AEException
  {
    ChannelInfo localChannelInfo1 = null;
    synchronized (this.cr)
    {
      ChannelInfo localChannelInfo2 = w(paramString);
      if (localChannelInfo2 != null) {
        localChannelInfo1 = (ChannelInfo)localChannelInfo2.clone();
      }
    }
    return localChannelInfo1;
  }
  
  public void jdMethod_for(DBConnection paramDBConnection)
    throws AEException
  {
    if (this.cl == null) {
      throw new AEException(1025, AEInstance.a.a("ERR_ENGINE_START_REQD_FOR_CHANNEL_OP", AEInstance.a.a("PARAM_CHANNEL_OP_LOADED")));
    }
    synchronized (this.cr)
    {
      jdMethod_do(paramDBConnection);
      Iterator localIterator = this.cr.values().iterator();
      while (localIterator.hasNext())
      {
        ChannelInfo localChannelInfo = (ChannelInfo)localIterator.next();
        try
        {
          if ((localChannelInfo.a() < 1) || (localChannelInfo.isLoaded()) || (localChannelInfo.isLoadable()))
          {
            this.cl.jdMethod_if(localChannelInfo);
            localChannelInfo.a(true);
            localChannelInfo.jdMethod_do(false);
          }
        }
        catch (AEException localAEException)
        {
          localChannelInfo.jdMethod_do(true);
          AELog.a(localAEException, 0);
        }
      }
    }
  }
  
  public IAEChannelInfo z(String paramString)
    throws AEException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new AEException(1012, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_STRING_CHANNELNAME")));
    }
    ChannelInfo localChannelInfo1 = null;
    int i = q("Universal Alert Engine");
    synchronized (this.cr)
    {
      ChannelInfo localChannelInfo2 = w(paramString);
      if (localChannelInfo2 == null) {
        throw new AEException(1007, AEInstance.a.a("ERR_CHANNEL_NAME_NOT_FOUND"));
      }
      if (localChannelInfo2.jdMethod_do() == i) {
        throw new AEException(1018, AEInstance.a.a("ERR_RESERVED_CHANNEL", paramString));
      }
      if ((localChannelInfo2.isLoaded()) && (this.cl != null))
      {
        this.cl.e(localChannelInfo2.getId());
        localChannelInfo2.a(false);
        localChannelInfo2.jdMethod_do(false);
      }
      if (localChannelInfo2.isLoadable())
      {
        localChannelInfo2.jdMethod_if(false);
        DBConnection localDBConnection = this.ct.aL();
        try
        {
          localChannelInfo2.a(this.ct, localDBConnection);
        }
        finally
        {
          this.ct.k(localDBConnection);
        }
      }
      localChannelInfo2.jdMethod_do(false);
      localChannelInfo1 = (ChannelInfo)localChannelInfo2.clone();
    }
    return localChannelInfo1;
  }
  
  public void V()
    throws AEException
  {
    if (this.cl == null) {
      throw new AEException(1025, AEInstance.a.a("ERR_ENGINE_START_REQD_FOR_CHANNEL_OP", AEInstance.a.a("PARAM_CHANNEL_OP_UNLOADED")));
    }
    synchronized (this.cr)
    {
      Iterator localIterator = this.cr.values().iterator();
      while (localIterator.hasNext())
      {
        ChannelInfo localChannelInfo = (ChannelInfo)localIterator.next();
        try
        {
          if (localChannelInfo.isLoaded())
          {
            this.cl.e(localChannelInfo.getId());
            localChannelInfo.a(false);
            localChannelInfo.jdMethod_do(false);
          }
        }
        catch (AEException localAEException)
        {
          AELog.a(localAEException, 0);
        }
      }
    }
  }
  
  public IAEChannelInfo jdMethod_if(ChannelInfo paramChannelInfo, boolean paramBoolean)
    throws AEException
  {
    if (paramChannelInfo == null) {
      throw new AEException(1012, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_CHANNELINFO")));
    }
    ChannelInfo localChannelInfo1 = null;
    int i = q("Universal Alert Engine");
    synchronized (this.cr)
    {
      ChannelInfo localChannelInfo2 = c(paramChannelInfo.getId());
      if ((localChannelInfo2 == null) && (!paramBoolean)) {
        throw new AEException(1007, AEInstance.a.a("ERR_CHANNEL_NAME_NOT_FOUND"));
      }
      if (localChannelInfo2.jdMethod_do() == i) {
        throw new AEException(1018, AEInstance.a.a("ERR_RESERVED_CHANNEL", localChannelInfo2.getChannelName()));
      }
      String str1 = localChannelInfo2.getChannelName();
      String str2 = paramChannelInfo.getChannelName();
      int j = !str1.equals(str2) ? 1 : 0;
      if ((j != 0) && (this.cr.containsKey(str2))) {
        throw new AEException(1006, AEInstance.a.a("ERR_CHANNEL_NAME_EXISTS"));
      }
      String str3 = localChannelInfo2.getClassName();
      Properties localProperties = localChannelInfo2.getInitInfo();
      int k = localChannelInfo2.a();
      int m = localChannelInfo2.getNumWorkers();
      localChannelInfo2.setChannelName(paramChannelInfo.getChannelName());
      localChannelInfo2.setClassName(paramChannelInfo.getClassName());
      localChannelInfo2.setInitInfo(paramChannelInfo.getInitInfo() == null ? null : (Properties)paramChannelInfo.getInitInfo().clone());
      localChannelInfo2.setNumWorkers(paramChannelInfo.getNumWorkers());
      DBConnection localDBConnection = this.ct.aL();
      int n = 1;
      try
      {
        ChannelInfo localChannelInfo3 = ChannelInfo.a(localDBConnection, localChannelInfo2.getChannelName());
        if ((localChannelInfo3 != null) && (localChannelInfo3.getClassName().equals(localChannelInfo2.getClassName())) && (localChannelInfo3.getInitInfo().equals(localChannelInfo2.getInitInfo())) && (localChannelInfo3.a() == localChannelInfo2.a()) && (localChannelInfo3.getNumWorkers() == localChannelInfo2.getNumWorkers())) {
          n = 0;
        }
        if (n != 0) {
          localChannelInfo2.a(this.ct, localDBConnection);
        }
      }
      catch (AEException localAEException1)
      {
        localChannelInfo2.setChannelName(str1);
        localChannelInfo2.setClassName(str3);
        localChannelInfo2.setInitInfo(localProperties);
        localChannelInfo2.jdMethod_do(k);
        localChannelInfo2.setNumWorkers(m);
        throw localAEException1;
      }
      finally
      {
        this.ct.k(localDBConnection);
      }
      if (j != 0)
      {
        this.cr.remove(str1);
        this.cr.put(str2, localChannelInfo2);
      }
      if (localChannelInfo2.isLoaded())
      {
        this.cl.e(localChannelInfo2.getId());
        localChannelInfo2.a(false);
        localChannelInfo2.jdMethod_do(false);
        try
        {
          this.cl.jdMethod_if(localChannelInfo2);
          localChannelInfo2.a(true);
        }
        catch (AEException localAEException2)
        {
          localChannelInfo2.jdMethod_do(true);
          throw new AEException(localAEException2.getErrorCode(), localAEException2.getMessage(), localAEException2);
        }
      }
      if (n != 0) {
        localChannelInfo1 = (ChannelInfo)localChannelInfo2.clone();
      }
    }
    return localChannelInfo1;
  }
  
  public ChannelInfo w(String paramString)
  {
    synchronized (this.cr)
    {
      ChannelInfo localChannelInfo = (ChannelInfo)this.cr.get(paramString);
      return localChannelInfo;
    }
  }
  
  public ChannelInfo c(int paramInt)
  {
    synchronized (this.cr)
    {
      ChannelInfo localChannelInfo = (ChannelInfo)this.cm.jdMethod_if(paramInt);
      return localChannelInfo;
    }
  }
  
  public ChannelInfo[] a(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
    throws AEException
  {
    ArrayList localArrayList = null;
    synchronized (this.cr)
    {
      localArrayList = new ArrayList(this.cr.size());
      Iterator localIterator = this.cr.values().iterator();
      while (localIterator.hasNext())
      {
        ChannelInfo localChannelInfo = (ChannelInfo)localIterator.next();
        if ((localChannelInfo.isInstalled()) && ((localChannelInfo.jdMethod_do() == paramInt) || ((!paramBoolean2) && (localChannelInfo.jdMethod_do() == 0))) && ((!paramBoolean1) || (localChannelInfo.isLoaded()))) {
          localArrayList.add(localChannelInfo.clone());
        }
      }
    }
    ??? = new ChannelInfo[localArrayList.size()];
    localArrayList.toArray((Object[])???);
    Arrays.sort((Object[])???, ChannelInfo.jdMethod_for());
    return ???;
  }
  
  public IAEAlertDefinition a(AlertInstance paramAlertInstance)
    throws AEException
  {
    AEApplicationInfo localAEApplicationInfo = b(paramAlertInstance.aB());
    paramAlertInstance.C(localAEApplicationInfo.getName());
    if (paramAlertInstance.getNextRaised() == 0L) {
      paramAlertInstance.p(paramAlertInstance.l(2) ? 2 : 1);
    } else {
      paramAlertInstance.p(0);
    }
    DeliveryInfo[] arrayOfDeliveryInfo = (DeliveryInfo[])paramAlertInstance.getDeliveryInfo();
    synchronized (this.cr)
    {
      for (int i = 0; i < arrayOfDeliveryInfo.length; i++)
      {
        int j = arrayOfDeliveryInfo[i].jdMethod_if();
        ChannelInfo localChannelInfo = c(j);
        arrayOfDeliveryInfo[i].setDeliveryChannelName(localChannelInfo.getChannelName());
      }
    }
    return paramAlertInstance;
  }
  
  public boolean jdMethod_do(Properties paramProperties)
    throws AEException
  {
    boolean bool1 = false;
    if (paramProperties == null) {
      throw new AEException(1012, AEInstance.a.a("ERR_PARAM_REQUIRED", AEInstance.a.a("PARAM_ENGINE_PROPERTIES")));
    }
    synchronized (this.cq)
    {
      String str1 = a(paramProperties, "cluster.failureInterval");
      String str2 = a(paramProperties, "scheduler.interval");
      String str3 = a(paramProperties, "scheduler.batch.size");
      String str4 = a(paramProperties, "log.level");
      String str5 = a(paramProperties, "log.file");
      String str6 = a(paramProperties, "log.console");
      String str7 = a(paramProperties, "audit.history");
      String str8 = a(paramProperties, "db.connection.max");
      String str9 = a(paramProperties, "audit.cleanup");
      String str10 = a(paramProperties, "audit.pool.size");
      boolean bool2 = v(str2);
      boolean bool3 = x(str3);
      boolean bool4 = false;
      bool4 |= A(str4);
      bool4 |= k(str5);
      bool4 |= r(str6);
      m(str7);
      boolean bool5 = l(str8);
      boolean bool6 = o(str9);
      boolean bool7 = u(str10);
      boolean bool8 = B(str1);
      Object localObject2;
      Object localObject3;
      Object localObject4;
      if ((str1 != null) || (str2 != null))
      {
        localObject1 = str1 == null ? y("cluster.failureInterval") : str1;
        localObject2 = str2 == null ? y("scheduler.interval") : str2;
        if (!jdMethod_int((String)localObject1, (String)localObject2))
        {
          localObject3 = null;
          localObject4 = null;
          if (str1 != null)
          {
            localObject3 = str1;
            localObject4 = "PARAM_FAILURE_INTERVAL_PROPERTY";
          }
          else
          {
            localObject3 = str2;
            localObject4 = "PARAM_SCHED_INTERVAL_PROPERTY";
          }
          throw new AEException(1021, AEInstance.a.a("ERR_INVALID_PROPERTY", localObject3, AEInstance.a.a((String)localObject4)));
        }
      }
      Object localObject1 = this.ct.aL();
      try
      {
        localObject2 = null;
        localObject3 = null;
        try
        {
          ((DBConnection)localObject1).jdMethod_try(false);
          jdMethod_for("scheduler.interval", str2);
          jdMethod_for("scheduler.batch.size", str3);
          jdMethod_for("cluster.failureInterval", str1);
          jdMethod_for("log.level", str4);
          jdMethod_for("log.file", str5);
          jdMethod_for("log.console", str6);
          jdMethod_for("audit.history", str7);
          jdMethod_for("db.connection.max", str8);
          jdMethod_for("audit.cleanup", str9);
          jdMethod_for("audit.pool.size", str10);
          bool1 = this.ct.a((DBConnection)localObject1, this.cq);
          if (bool6)
          {
            localObject4 = AlertInstance.a((DBConnection)localObject1, q("Universal Alert Engine"), "DBCleanup", true);
            if (((ArrayList)localObject4).size() != 1) {
              throw new AEException(1028, AEInstance.a.a("ERR_INTERNAL"));
            }
            localObject3 = (AlertInstance)((ArrayList)localObject4).get(0);
            long l = ((AlertInstance)localObject3).getNextRaised();
            SchedulerEngine localSchedulerEngine = this.cn.q();
            if (localSchedulerEngine == null) {
              ((AlertInstance)localObject3).a(this.ct, (DBConnection)localObject1, 0L);
            } else {
              synchronized (localSchedulerEngine)
              {
                AlertEngine.b localb = new AlertEngine.b(((AlertInstance)localObject3).getId());
                localObject2 = localSchedulerEngine.a(localb, (DBConnection)localObject1, (ArrayList)localObject4, false);
              }
            }
            if ((l == 0L) && ((localObject2 == null) || (((ArrayList)localObject2).size() == 0))) {
              throw new AEException(1027, AEInstance.a.a("ERR_ALERT_NOT_ACTIVE"));
            }
            ??? = new AEScheduleInfo(U().getTime(), 9223372036854775807L, 86400000L, 0, Calendar.getInstance().getTimeZone(), true);
            a((AlertInstance)localObject3);
            this.cn.a((DBConnection)localObject1, (AlertInstance)localObject3, ((AlertInstance)localObject3).getId(), ((AlertInstance)localObject3).getUserId(), ((AlertInstance)localObject3).getType(), ((AlertInstance)localObject3).getPriority(), (AEScheduleInfo)???, ((AlertInstance)localObject3).getDeliveryInfo(), null, AEUtils.a(new RepositoryCleaner()));
          }
          if (bool5) {
            this.ct.x(L());
          }
          if (bool4)
          {
            localObject4 = O();
            AELog.a((AELogParams)localObject4);
          }
          if (bool7) {
            this.cn.j().jdMethod_long(S());
          }
          if (bool2)
          {
            if (this.cn.G() != null) {
              this.cn.G().jdMethod_if(jdMethod_new("scheduler.interval", y("scheduler.interval")));
            }
            if (this.cn.q() != null) {
              this.cn.q().jdMethod_for(jdMethod_new("scheduler.interval", y("scheduler.interval")));
            }
          }
          if ((bool3) && (this.cn.q() != null)) {
            this.cn.q().g(a(y("scheduler.batch.size"), true, "PARAM_SCHED_BATCH_SIZE_PROPERTY"));
          }
          if ((bool8) && (this.cn.G() != null)) {
            this.cn.G().jdMethod_do(jdMethod_new("cluster.failureInterval", y("cluster.failureInterval")));
          }
          ((DBConnection)localObject1).a0();
          try
          {
            ((DBConnection)localObject1).jdMethod_try(true);
          }
          catch (SQLException localSQLException1) {}
        }
        catch (SQLException localSQLException2)
        {
          try
          {
            ((DBConnection)localObject1).a1();
            if ((localObject2 != null) && (this.cn.q() != null)) {
              synchronized (this.cn.q())
              {
                if (((ArrayList)localObject2).size() > 0)
                {
                  localObject3 = (AlertInstance)((ArrayList)localObject2).get(0);
                  ((AlertInstance)localObject3).jdMethod_if(this.ct, (DBConnection)localObject1);
                  if (this.cn.q().a((DBConnection)localObject1, (AlertInstance)((ArrayList)localObject2).get(0), 1, false)) {
                    this.cn.q().ar();
                  }
                }
                else
                {
                  localObject3 = AlertInstance.jdMethod_if((DBConnection)localObject1, ((AlertInstance)localObject3).getId());
                  if (this.cn.q().a((DBConnection)localObject1, (AlertInstance)localObject3, 1, false)) {
                    this.cn.q().ar();
                  }
                }
                ((DBConnection)localObject1).a0();
              }
            }
          }
          catch (SQLException l) {}
          throw new AEException(1, DBSqlUtils.a(localSQLException2));
        }
        catch (AEException localAEException)
        {
          try
          {
            ((DBConnection)localObject1).a1();
            if ((localObject2 != null) && (this.cn.q() != null)) {
              synchronized (this.cn.q())
              {
                if (((ArrayList)localObject2).size() > 0)
                {
                  localObject3 = (AlertInstance)((ArrayList)localObject2).get(0);
                  ((AlertInstance)localObject3).jdMethod_if(this.ct, (DBConnection)localObject1);
                  if (this.cn.q().a((DBConnection)localObject1, (AlertInstance)((ArrayList)localObject2).get(0), 1, false)) {
                    this.cn.q().ar();
                  }
                }
                else
                {
                  localObject3 = AlertInstance.jdMethod_if((DBConnection)localObject1, ((AlertInstance)localObject3).getId());
                  if (this.cn.q().a((DBConnection)localObject1, (AlertInstance)localObject3, 1, false)) {
                    this.cn.q().ar();
                  }
                }
                ((DBConnection)localObject1).a0();
              }
            }
          }
          catch (SQLException localSQLException3) {}
          throw localAEException;
        }
        finally
        {
          try
          {
            ((DBConnection)localObject1).jdMethod_try(true);
          }
          catch (SQLException localSQLException4) {}
        }
      }
      finally
      {
        this.ct.k((DBConnection)localObject1);
      }
    }
    return bool1;
  }
  
  public Properties T()
  {
    Properties localProperties = new Properties();
    synchronized (this.cq)
    {
      localProperties.setProperty("log.level", y("log.level"));
      localProperties.setProperty("log.file", y("log.file"));
      localProperties.setProperty("log.console", y("log.console"));
      localProperties.setProperty("scheduler.interval", y("scheduler.interval"));
      localProperties.setProperty("scheduler.batch.size", y("scheduler.batch.size"));
      localProperties.setProperty("cluster.failureInterval", y("cluster.failureInterval"));
      localProperties.setProperty("audit.history", y("audit.history"));
      localProperties.setProperty("db.connection.max", y("db.connection.max"));
      localProperties.setProperty("audit.cleanup", y("audit.cleanup"));
      localProperties.setProperty("audit.pool.size", y("audit.pool.size"));
    }
    return localProperties;
  }
  
  public AELogParams O()
    throws AEException
  {
    AELogParams localAELogParams;
    synchronized (this.cq)
    {
      boolean bool1 = jdMethod_do(y("log.console"), "PARAM_LOG_CONSOLE_PROPERTY");
      String str = y("log.file");
      boolean bool2 = (str != null) && (str.length() > 0);
      int i = s(y("log.level"));
      localAELogParams = AELogParams.a(bool1, bool2, str, i);
    }
    return localAELogParams;
  }
  
  public long M()
    throws AEException
  {
    long l;
    synchronized (this.cq)
    {
      l = jdMethod_new("scheduler.interval", y("scheduler.interval"));
    }
    return l;
  }
  
  public int W()
    throws AEException
  {
    int i;
    synchronized (this.cq)
    {
      i = a(y("scheduler.batch.size"), true, "PARAM_SCHED_BATCH_SIZE_PROPERTY");
    }
    return i;
  }
  
  public long Q()
    throws AEException
  {
    long l;
    synchronized (this.cq)
    {
      l = jdMethod_new("cluster.failureInterval", y("cluster.failureInterval"));
    }
    return l;
  }
  
  public long N()
    throws AEException
  {
    long l;
    synchronized (this.cq)
    {
      l = jdMethod_new("audit.history", y("audit.history"));
    }
    return l;
  }
  
  public int L()
    throws AEException
  {
    int i;
    synchronized (this.cq)
    {
      i = a(y("db.connection.max"), true, "PARAM_DB_CONN_MAX_PROPERTY");
    }
    return i;
  }
  
  public Date U()
    throws AEException
  {
    Date localDate;
    synchronized (this.cq)
    {
      localDate = p(y("audit.cleanup"));
    }
    return localDate;
  }
  
  public int S()
    throws AEException
  {
    int i;
    synchronized (this.cq)
    {
      i = a(y("audit.pool.size"), true, "PARAM_AUDIT_POOL_SIZE_PROPERTY");
    }
    return i;
  }
  
  private String y(String paramString)
  {
    if (this.cq.containsKey(paramString)) {
      return this.cq.getProperty(paramString);
    }
    return co.getProperty(paramString);
  }
  
  private void jdMethod_for(String paramString1, String paramString2)
    throws AEException
  {
    if ((paramString2 == null) && (this.cq.containsKey(paramString1))) {
      this.cq.remove(paramString1);
    } else if (paramString2 != null) {
      this.cq.setProperty(paramString1, paramString2);
    }
  }
  
  private int s(String paramString)
    throws AEException
  {
    if (paramString.equals("none")) {
      return -2147483648;
    }
    if (paramString.equals("normal")) {
      return 0;
    }
    if (paramString.equals("verbose")) {
      return 1;
    }
    if (paramString.equals("all")) {
      return 2147483647;
    }
    throw new AEException(1021, AEInstance.a.a("ERR_INVALID_PROPERTY", paramString, AEInstance.a.a("PARAM_LOG_LEVEL_PROPERTY")));
  }
  
  private long jdMethod_new(String paramString1, String paramString2)
    throws AEException
  {
    long l1 = 60000L;
    char c = paramString2.charAt(paramString2.length() - 1);
    String str = null;
    if (!Character.isDigit(c))
    {
      if (paramString1.equals("scheduler.interval"))
      {
        str = "PARAM_SCHED_INTERVAL_PROPERTY";
        switch (c)
        {
        case 's': 
          l1 = 1000L;
          break;
        case 'm': 
          l1 = 60000L;
          break;
        default: 
          throw new AEException(1021, AEInstance.a.a("ERR_INVALID_PROPERTY", paramString2, AEInstance.a.a("PARAM_SCHED_INTERVAL_PROPERTY")));
        }
      }
      else if (paramString1.equals("cluster.failureInterval"))
      {
        str = "PARAM_FAILURE_INTERVAL_PROPERTY";
        switch (c)
        {
        case 's': 
          l1 = 1000L;
          break;
        case 'm': 
          l1 = 60000L;
          break;
        default: 
          throw new AEException(1021, AEInstance.a.a("ERR_INVALID_PROPERTY", paramString2, AEInstance.a.a("PARAM_FAILURE_INTERVAL_PROPERTY")));
        }
      }
      else if (paramString1.equals("audit.history"))
      {
        str = "PARAM_AUDIT_HISTORY_PROPERTY";
        switch (c)
        {
        case 'd': 
          l1 = 86400000L;
          break;
        case 'h': 
          l1 = 3600000L;
          break;
        default: 
          throw new AEException(1021, AEInstance.a.a("ERR_INVALID_PROPERTY", paramString2, AEInstance.a.a("PARAM_AUDIT_HISTORY_PROPERTY")));
        }
      }
      paramString2 = paramString2.substring(0, paramString2.length() - 1);
    }
    long l2 = 0L;
    try
    {
      l2 = Long.parseLong(paramString2) * l1;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new AEException(1021, AEInstance.a.a("ERR_INVALID_PROPERTY", paramString2, AEInstance.a.a(str)), localNumberFormatException);
    }
    return l2;
  }
  
  private boolean jdMethod_do(String paramString1, String paramString2)
    throws AEException
  {
    if (paramString1.equalsIgnoreCase("true")) {
      return true;
    }
    if (paramString1.equalsIgnoreCase("false")) {
      return false;
    }
    throw new AEException(1021, AEInstance.a.a("ERR_INVALID_PROPERTY", paramString1, AEInstance.a.a(paramString2)));
  }
  
  private int a(String paramString1, boolean paramBoolean, String paramString2)
    throws AEException
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(paramString1);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new AEException(1021, AEInstance.a.a("ERR_INVALID_PROPERTY", paramString1, AEInstance.a.a(paramString2)), localNumberFormatException);
    }
    if ((paramBoolean) && (i < 1)) {
      throw new AEException(1021, AEInstance.a.a("ERR_INVALID_PROPERTY", paramString1, AEInstance.a.a(paramString2)));
    }
    return i;
  }
  
  private Date p(String paramString)
    throws AEException
  {
    Calendar localCalendar = Calendar.getInstance();
    Date localDate1 = localCalendar.getTime();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("MM/dd/yy");
    String str = localSimpleDateFormat.format(localDate1) + " " + paramString;
    localSimpleDateFormat = new SimpleDateFormat("MM/dd/yy HH:mm");
    ParsePosition localParsePosition = new ParsePosition(0);
    localSimpleDateFormat.setLenient(false);
    Date localDate2 = localSimpleDateFormat.parse(str, localParsePosition);
    if (localDate2 == null) {
      throw new AEException(1021, AEInstance.a.a("ERR_INVALID_PROPERTY", paramString, AEInstance.a.a("PARAM_AUDIT_CLEANUP_TIME_PROPERTY")));
    }
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    localGregorianCalendar.setTime(localDate2);
    if (localCalendar.after(localGregorianCalendar)) {
      localGregorianCalendar.add(5, 1);
    }
    return localGregorianCalendar.getTime();
  }
  
  private boolean v(String paramString)
    throws AEException
  {
    if (paramString == null) {
      return this.cq.containsKey("scheduler.interval");
    }
    long l1 = jdMethod_new("scheduler.interval", paramString);
    long l2 = jdMethod_new("scheduler.interval", y("scheduler.interval"));
    return l1 != l2;
  }
  
  private boolean x(String paramString)
    throws AEException
  {
    if (paramString == null) {
      return this.cq.containsKey("scheduler.batch.size");
    }
    int i = a(paramString, true, "PARAM_SCHED_BATCH_SIZE_PROPERTY");
    int j = a(y("scheduler.batch.size"), true, "PARAM_SCHED_BATCH_SIZE_PROPERTY");
    return i != j;
  }
  
  private boolean B(String paramString)
    throws AEException
  {
    if (paramString == null) {
      return this.cq.containsKey("cluster.failureInterval");
    }
    long l1 = jdMethod_new("cluster.failureInterval", paramString);
    long l2 = jdMethod_new("cluster.failureInterval", y("cluster.failureInterval"));
    return l1 != l2;
  }
  
  private boolean A(String paramString)
    throws AEException
  {
    if (paramString == null) {
      return this.cq.containsKey("log.level");
    }
    int i = s(paramString);
    int j = s(y("log.level"));
    return i != j;
  }
  
  private boolean k(String paramString)
    throws AEException
  {
    if (paramString == null) {
      return this.cq.containsKey("log.file");
    }
    String str = y("log.file");
    return !paramString.equals(str);
  }
  
  private boolean r(String paramString)
    throws AEException
  {
    if (paramString == null) {
      return this.cq.containsKey("log.console");
    }
    boolean bool1 = jdMethod_do(paramString, "PARAM_LOG_CONSOLE_PROPERTY");
    boolean bool2 = jdMethod_do(y("log.console"), "PARAM_LOG_CONSOLE_PROPERTY");
    return bool1 != bool2;
  }
  
  private boolean m(String paramString)
    throws AEException
  {
    if (paramString == null) {
      return this.cq.containsKey("audit.history");
    }
    long l1 = jdMethod_new("audit.history", paramString);
    long l2 = jdMethod_new("audit.history", y("audit.history"));
    return l1 != l2;
  }
  
  private boolean l(String paramString)
    throws AEException
  {
    if (paramString == null) {
      return this.cq.containsKey("db.connection.max");
    }
    int i = a(paramString, true, "PARAM_DB_CONN_MAX_PROPERTY");
    if (i < cs) {
      i = cs;
    }
    int j = a(y("db.connection.max"), true, "PARAM_DB_CONN_MAX_PROPERTY");
    return i != j;
  }
  
  private boolean o(String paramString)
    throws AEException
  {
    if (paramString == null) {
      return this.cq.containsKey("audit.cleanup");
    }
    Date localDate1 = p(paramString);
    Date localDate2 = p(y("audit.cleanup"));
    return !localDate1.equals(localDate2);
  }
  
  private boolean u(String paramString)
    throws AEException
  {
    if (paramString == null) {
      return this.cq.containsKey("audit.pool.size");
    }
    int i = a(paramString, true, "PARAM_AUDIT_POOL_SIZE_PROPERTY");
    int j = a(y("audit.pool.size"), true, "PARAM_AUDIT_POOL_SIZE_PROPERTY");
    return i != j;
  }
  
  private static final String a(Properties paramProperties, String paramString)
  {
    if (!paramProperties.containsKey(paramString)) {
      return null;
    }
    return (String)paramProperties.get(paramString);
  }
  
  private boolean jdMethod_if(Properties paramProperties)
    throws AEException
  {
    String str1 = a(paramProperties, "cluster.failureInterval");
    String str2 = a(paramProperties, "scheduler.interval");
    return jdMethod_int(str1, str2);
  }
  
  private boolean jdMethod_int(String paramString1, String paramString2)
    throws AEException
  {
    try
    {
      return jdMethod_new("scheduler.interval", paramString2) <= jdMethod_new("cluster.failureInterval", paramString1);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new AEException(1021, AEInstance.a.a("ERR_INVALID_PROPERTY", paramString1, AEInstance.a.a("PARAM_FAILURE_INTERVAL_PROPERTY")));
    }
  }
  
  private void R()
    throws AEException
  {
    String str1 = y("cluster.failureInterval");
    String str2 = y("scheduler.interval");
    str2 = str2.trim();
    char c = str2.charAt(str2.length() - 1);
    if (!Character.isDigit(c)) {
      str2 = str2.substring(0, str2.length() - 1);
    }
    int i = -1;
    try
    {
      i = Integer.parseInt(str2);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new AEException(1021, AEInstance.a.a("ERR_INVALID_PROPERTY", str2, AEInstance.a.a("PARAM_SCHED_INTERVAL_PROPERTY")));
    }
    int j = (int)Math.ceil(i * 1.5D);
    str1 = Integer.toString(j);
    if (!Character.isDigit(c)) {
      str1 = str1 + c;
    }
    jdMethod_for("cluster.failureInterval", str1);
  }
  
  static
  {
    co.setProperty("log.level", "normal");
    co.setProperty("log.file", "AlertEngine.log");
    co.setProperty("log.console", "true");
    co.setProperty("scheduler.interval", "1m");
    co.setProperty("scheduler.batch.size", "100");
    co.setProperty("cluster.failureInterval", "90s");
    co.setProperty("audit.history", "7d");
    co.setProperty("db.connection.max", "50");
    co.setProperty("audit.cleanup", "03:00");
    co.setProperty("audit.pool.size", "10000");
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.engine.AEConfigManager
 * JD-Core Version:    0.7.0.1
 */