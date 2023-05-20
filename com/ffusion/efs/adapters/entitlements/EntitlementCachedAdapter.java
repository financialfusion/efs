package com.ffusion.efs.adapters.entitlements;

import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementAdmins;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Entitlements;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyList;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.beans.entitlements.ObjectTypePropertyList;
import com.ffusion.services.IForeignExchangeService;
import com.ffusion.util.ReadWriteLock;
import com.ffusion.util.db.ConnectionHolder;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBCookie;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.db.PoolException;
import com.ffusion.util.db.ReopenableDBCookie;
import com.ffusion.util.entitlements.EntitlementsUtil;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

public class EntitlementCachedAdapter
  implements EntitlementDefines
{
  public static final String REAP_INTERVAL = "ReapInterval";
  public static final String CACHE_MAX_SIZE = "CacheMaxSize";
  public static final String LIMIT_BASE_CURRENCY = "LimitBaseCurrency";
  private static final String jdField_for = "DB_PROPERTIES";
  private static final int jdField_do = 1000;
  static final int jdField_byte = 2500;
  private static EntitlementAdapter jdField_char;
  private static EntitlementCacheManager jdField_if;
  private static ReadWriteLock jdField_new = new ReadWriteLock();
  private static b jdField_int;
  private static int a;
  private static int jdField_case;
  private String jdField_try = "USD";
  private IForeignExchangeService jdField_else = null;
  
  public EntitlementCachedAdapter(Properties paramProperties, boolean paramBoolean)
    throws EntitlementException
  {
    String str1 = "EntitlementCachedAdapter.initialize";
    try
    {
      jdField_char = new EntitlementAdapter(paramProperties, paramBoolean);
      String str2 = paramProperties.getProperty("CacheMaxSize");
      this.jdField_try = paramProperties.getProperty("LimitBaseCurrency");
      try
      {
        jdField_case = str2 == null ? 2500 : Integer.parseInt(str2);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        jdField_case = 0;
      }
      if (jdField_case <= 0) {
        jdField_case = 0;
      }
      jdField_if = new EntitlementCacheManager(jdField_char.getEntitlementMap(false), jdField_char.getObjectTypeMap(false), jdField_case, true);
      String str3 = paramProperties.getProperty("ReapInterval");
      a = str3 == null ? 1000 : Integer.parseInt(str3);
      jdField_int = new b(a);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str1, 1, "Unable to initialize the entitlement cached adapter");
    }
  }
  
  public EntitlementCachedAdapter(HashMap paramHashMap, boolean paramBoolean)
    throws EntitlementException
  {
    Properties localProperties = (Properties)paramHashMap.get("DB_PROPERTIES");
    String str1 = "EntitlementCachedAdapter.initialize";
    try
    {
      jdField_char = new EntitlementAdapter(paramHashMap, paramBoolean);
      String str2 = localProperties.getProperty("CacheMaxSize");
      try
      {
        jdField_case = str2 == null ? 2500 : Integer.parseInt(str2);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        jdField_case = 0;
      }
      if (jdField_case <= 0) {
        jdField_case = 0;
      }
      jdField_if = new EntitlementCacheManager(jdField_char.getEntitlementMap(false), jdField_char.getObjectTypeMap(false), jdField_case, true);
      String str3 = localProperties.getProperty("ReapInterval");
      a = str3 == null ? 1000 : Integer.parseInt(str3);
      jdField_int = new b(a);
      this.jdField_try = localProperties.getProperty("LimitBaseCurrency");
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str1, 1, "Unable to initialize the entitlement cached adapter");
    }
  }
  
  public void shutDown()
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.shutDown";
    jdField_int.a();
    try
    {
      jdField_char.shutDown();
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 25, "Unable to shutDown the entitlement cached adapter");
    }
    jdField_char = null;
    jdField_int = null;
  }
  
  public EntitlementGroup getEntitlementGroup(int paramInt)
    throws EntitlementException
  {
    String str1 = "EntitlementCachedAdapter.getEntitlementGroup";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str1, 3);
    }
    jdField_new.getReadLock();
    EntitlementGroup localEntitlementGroup1;
    try
    {
      localEntitlementGroup1 = jdField_if.getEntitlementGroup(paramInt);
      if (localEntitlementGroup1 == null)
      {
        localEntitlementGroup1 = jdField_char.getEntitlementGroup(paramInt);
        jdField_new.escalateLock();
        int i = localEntitlementGroup1.getParentId();
        Object localObject1 = localEntitlementGroup1;
        while (i != 0)
        {
          EntitlementGroup localEntitlementGroup2 = jdField_if.getEntitlementGroup(i);
          if (localEntitlementGroup2 == null)
          {
            localEntitlementGroup2 = jdField_char.getEntitlementGroup(i);
            jdField_if.addEntitlementGroup(localEntitlementGroup2);
          }
          ArrayList localArrayList = jdField_if.getChildGroups(localEntitlementGroup2.getGroupId());
          String str2 = Integer.toString(((EntitlementGroup)localObject1).getGroupId());
          if ((localArrayList != null) && (!localArrayList.contains(str2))) {
            localArrayList.add(str2);
          }
          i = localEntitlementGroup2.getParentId();
          localObject1 = localEntitlementGroup2;
          localEntitlementGroup2 = null;
        }
        jdField_if.addEntitlementGroup(localEntitlementGroup1);
      }
    }
    finally
    {
      jdField_new.releaseLock();
    }
    return localEntitlementGroup1;
  }
  
  public EntitlementGroup getEntitlementGroupNoCache(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getEntitlementGroupNoCache";
    return jdField_char.getEntitlementGroup(paramInt);
  }
  
  public boolean checkEntitlement(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.checkEntitlement";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    a(paramEntitlement);
    return jdField_do(paramEntitlementGroupMember, paramEntitlement);
  }
  
  public boolean checkEntitlementUnsafe(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.checkEntitlement";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    a(paramEntitlement);
    return jdField_if(paramEntitlementGroupMember, paramEntitlement);
  }
  
  public Entitlement checkEntitlement(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.checkEntitlement";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    Entitlements localEntitlements = EntitlementUtil.getEntitlements(paramMultiEntitlement);
    for (int i = 0; i < localEntitlements.size(); i++)
    {
      Entitlement localEntitlement = localEntitlements.getEntitlement(i);
      a(localEntitlement);
      if (!jdField_do(paramEntitlementGroupMember, localEntitlement)) {
        return localEntitlement;
      }
    }
    return null;
  }
  
  private boolean jdField_do(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement)
    throws EntitlementException
  {
    jdField_new.getReadLock();
    try
    {
      boolean bool = a(paramEntitlementGroupMember, paramEntitlement);
      return bool;
    }
    finally
    {
      jdField_new.releaseLock();
    }
  }
  
  private boolean a(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement)
    throws EntitlementException
  {
    int i = jdField_if.checkEntitlement(paramEntitlementGroupMember, paramEntitlement);
    if (i == 2)
    {
      Entitlements localEntitlements = a(paramEntitlementGroupMember);
      i = jdField_if.checkEntitlement(paramEntitlementGroupMember, paramEntitlement);
    }
    return i == 1;
  }
  
  private boolean a(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement)
    throws EntitlementException
  {
    jdField_new.getReadLock();
    try
    {
      boolean bool = jdField_if(paramConnection, paramEntitlementGroupMember, paramEntitlement);
      return bool;
    }
    finally
    {
      jdField_new.releaseLock();
    }
  }
  
  private boolean jdField_if(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement)
    throws EntitlementException
  {
    int i = jdField_if.checkEntitlement(paramEntitlementGroupMember, paramEntitlement);
    if (i == 2)
    {
      Entitlements localEntitlements = a(paramConnection, paramEntitlementGroupMember);
      i = jdField_if.checkEntitlement(paramEntitlementGroupMember, paramEntitlement);
    }
    return i == 1;
  }
  
  private boolean jdField_if(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement)
    throws EntitlementException
  {
    int i = jdField_if.checkEntitlement(paramEntitlementGroupMember, paramEntitlement);
    if (i == 2)
    {
      Entitlements localEntitlements = getCumulativeEntitlements(paramEntitlementGroupMember);
      jdField_if.setRestrictedEntitlements(paramEntitlementGroupMember, localEntitlements);
      i = jdField_if.checkEntitlement(paramEntitlementGroupMember, paramEntitlement);
    }
    return i == 1;
  }
  
  public boolean checkEntitlement(int paramInt, Entitlement paramEntitlement)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.checkEntitlement";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    a(paramEntitlement);
    return jdField_do(paramInt, paramEntitlement);
  }
  
  public Entitlement checkEntitlement(int paramInt, MultiEntitlement paramMultiEntitlement)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.checkEntitlement";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    Entitlements localEntitlements = EntitlementUtil.getEntitlements(paramMultiEntitlement);
    for (int i = 0; i < localEntitlements.size(); i++)
    {
      Entitlement localEntitlement = localEntitlements.getEntitlement(i);
      a(localEntitlement);
      if (!jdField_do(paramInt, localEntitlement)) {
        return localEntitlement;
      }
    }
    return null;
  }
  
  private boolean jdField_do(int paramInt, Entitlement paramEntitlement)
    throws EntitlementException
  {
    jdField_new.getReadLock();
    try
    {
      boolean bool = jdField_if(paramInt, paramEntitlement);
      return bool;
    }
    finally
    {
      jdField_new.releaseLock();
    }
  }
  
  private boolean jdField_if(int paramInt, Entitlement paramEntitlement)
    throws EntitlementException
  {
    int i = jdField_if.checkEntitlement(paramInt, paramEntitlement);
    if (i == 2)
    {
      Entitlements localEntitlements = jdField_for(paramInt);
      i = jdField_if.checkEntitlement(paramInt, paramEntitlement);
    }
    return i == 1;
  }
  
  private boolean a(Connection paramConnection, int paramInt, Entitlement paramEntitlement)
    throws EntitlementException
  {
    jdField_new.getReadLock();
    try
    {
      boolean bool = jdField_if(paramConnection, paramInt, paramEntitlement);
      return bool;
    }
    finally
    {
      jdField_new.releaseLock();
    }
  }
  
  private boolean jdField_if(Connection paramConnection, int paramInt, Entitlement paramEntitlement)
    throws EntitlementException
  {
    int i = jdField_if.checkEntitlement(paramInt, paramEntitlement);
    if (i == 2)
    {
      Entitlements localEntitlements = jdField_if(paramConnection, paramInt);
      i = jdField_if.checkEntitlement(paramInt, paramEntitlement);
    }
    return i == 1;
  }
  
  private boolean a(int paramInt, Entitlement paramEntitlement)
    throws EntitlementException
  {
    int i = jdField_if.checkEntitlement(paramInt, paramEntitlement);
    if (i == 2)
    {
      Entitlements localEntitlements = getCumulativeEntitlements(paramInt);
      jdField_if.setRestrictedEntitlements(paramInt, localEntitlements);
      i = jdField_if.checkEntitlement(paramInt, paramEntitlement);
    }
    return i == 1;
  }
  
  public Entitlements getCumulativeEntitlements(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getCumulativeEntitlements";
    jdField_new.getReadLock();
    try
    {
      Entitlements localEntitlements = jdField_for(paramInt);
      return localEntitlements;
    }
    finally
    {
      jdField_new.releaseLock();
    }
  }
  
  private Entitlements jdField_for(int paramInt)
    throws EntitlementException
  {
    String str1 = "EntitlementCachedAdapter.getCumulativeEntitlements";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str1, 3);
    }
    Entitlements localEntitlements = jdField_if.getRestrictedEntitlements(paramInt);
    if (localEntitlements == null)
    {
      EntitlementGroup localEntitlementGroup1 = null;
      Connection localConnection = null;
      localEntitlementGroup1 = jdField_if.getEntitlementGroup(paramInt);
      try
      {
        localConnection = DBUtil.getConnection(jdField_char.jdField_void, true, 2);
        if (localEntitlementGroup1 == null) {
          localEntitlementGroup1 = jdField_char.jdField_byte(localConnection, paramInt);
        }
        localEntitlements = jdField_char.jdField_char(localConnection, paramInt);
        jdField_new.escalateLock();
        if (!jdField_if.isGroupInCache(paramInt))
        {
          jdField_if.addEntitlementGroup(localEntitlementGroup1);
          int i = localEntitlementGroup1.getParentId();
          Object localObject1 = localEntitlementGroup1;
          while (i != 0)
          {
            EntitlementGroup localEntitlementGroup2 = jdField_if.getEntitlementGroup(i);
            if (localEntitlementGroup2 == null)
            {
              localEntitlementGroup2 = jdField_char.jdField_byte(localConnection, i);
              jdField_if.addEntitlementGroup(localEntitlementGroup2);
            }
            ArrayList localArrayList = jdField_if.getChildGroups(localEntitlementGroup2.getGroupId());
            String str2 = Integer.toString(((EntitlementGroup)localObject1).getGroupId());
            if ((localArrayList != null) && (!localArrayList.contains(str2))) {
              localArrayList.add(str2);
            }
            i = localEntitlementGroup2.getParentId();
            localObject1 = localEntitlementGroup2;
            localEntitlementGroup2 = null;
          }
        }
        jdField_if.setRestrictedEntitlements(paramInt, localEntitlements);
      }
      catch (PoolException localPoolException)
      {
        throw new EntitlementException(2, localPoolException);
      }
      finally
      {
        jdField_new.deEscalateLock();
        DBUtil.returnConnection(jdField_char.jdField_void, localConnection);
        localConnection = null;
      }
    }
    return localEntitlements;
  }
  
  private Entitlements jdField_if(Connection paramConnection, int paramInt)
    throws EntitlementException
  {
    String str1 = "EntitlementCachedAdapter.getCumulativeEntitlements";
    if (!a(paramConnection, paramInt)) {
      throw new EntitlementException(str1, 3);
    }
    Entitlements localEntitlements = jdField_if.getRestrictedEntitlements(paramInt);
    if (localEntitlements == null)
    {
      EntitlementGroup localEntitlementGroup1 = jdField_if.getEntitlementGroup(paramInt);
      if (localEntitlementGroup1 == null) {
        localEntitlementGroup1 = jdField_char.jdField_byte(paramConnection, paramInt);
      }
      localEntitlements = jdField_char.jdField_char(paramConnection, paramInt);
      jdField_new.escalateLock();
      try
      {
        if (!jdField_if.isGroupInCache(paramInt))
        {
          jdField_if.addEntitlementGroup(localEntitlementGroup1);
          int i = localEntitlementGroup1.getParentId();
          Object localObject1 = localEntitlementGroup1;
          while (i != 0)
          {
            EntitlementGroup localEntitlementGroup2 = jdField_if.getEntitlementGroup(i);
            if (localEntitlementGroup2 == null)
            {
              localEntitlementGroup2 = jdField_char.getEntitlementGroup(i);
              jdField_if.addEntitlementGroup(localEntitlementGroup2);
            }
            ArrayList localArrayList = jdField_if.getChildGroups(localEntitlementGroup2.getGroupId());
            String str2 = Integer.toString(((EntitlementGroup)localObject1).getGroupId());
            if ((localArrayList != null) && (!localArrayList.contains(str2))) {
              localArrayList.add(str2);
            }
            i = localEntitlementGroup2.getParentId();
            localObject1 = localEntitlementGroup2;
            localEntitlementGroup2 = null;
          }
        }
        jdField_if.setRestrictedEntitlements(paramInt, localEntitlements);
      }
      finally
      {
        jdField_new.deEscalateLock();
      }
    }
    return localEntitlements;
  }
  
  public Entitlements getCumulativeEntitlementsNoCache(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getCumulativeEntitlements";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    EntitlementGroup localEntitlementGroup = jdField_char.getEntitlementGroup(paramInt);
    Entitlements localEntitlements;
    if (localEntitlementGroup.getParentId() != 0)
    {
      localEntitlements = getCumulativeEntitlementsNoCache(localEntitlementGroup.getParentId());
      localEntitlements.addAll(jdField_char.getRestrictedEntitlements(paramInt));
    }
    else
    {
      localEntitlements = jdField_char.getRestrictedEntitlements(paramInt);
    }
    return localEntitlements;
  }
  
  public Entitlements getCumulativeEntitlementsNoCache(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getCumulativeEntitlements";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    Entitlements localEntitlements = getCumulativeEntitlementsNoCache(paramEntitlementGroupMember.getEntitlementGroupId());
    localEntitlements.addAll(jdField_char.getRestrictedEntitlements(paramEntitlementGroupMember));
    return localEntitlements;
  }
  
  public Entitlements getCumulativeEntitlements(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getCumulativeEntitlements";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    jdField_new.getReadLock();
    try
    {
      Entitlements localEntitlements = a(paramEntitlementGroupMember);
      return localEntitlements;
    }
    finally
    {
      jdField_new.releaseLock();
    }
  }
  
  private Entitlements a(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    Entitlements localEntitlements = jdField_if.getRestrictedEntitlements(paramEntitlementGroupMember);
    if (localEntitlements == null)
    {
      Connection localConnection = null;
      localEntitlements = jdField_char.getCumulativeEntitlements(paramEntitlementGroupMember);
      jdField_new.escalateLock();
      try
      {
        jdField_if.setRestrictedEntitlements(paramEntitlementGroupMember, localEntitlements);
        Object localObject1 = jdField_if.getEntitlementGroup(paramEntitlementGroupMember.getEntitlementGroupId());
        if (localObject1 == null)
        {
          localConnection = DBUtil.getConnection(jdField_char.jdField_void, true, 2);
          localObject1 = jdField_char.jdField_byte(localConnection, paramEntitlementGroupMember.getEntitlementGroupId());
          jdField_if.addEntitlementGroup((EntitlementGroup)localObject1);
          int i = ((EntitlementGroup)localObject1).getParentId();
          while (i != 0)
          {
            EntitlementGroup localEntitlementGroup = jdField_if.getEntitlementGroup(i);
            if (localEntitlementGroup == null)
            {
              localEntitlementGroup = jdField_char.jdField_byte(localConnection, i);
              jdField_if.addEntitlementGroup(localEntitlementGroup);
            }
            ArrayList localArrayList2 = jdField_if.getChildGroups(localEntitlementGroup.getGroupId());
            String str = Integer.toString(((EntitlementGroup)localObject1).getGroupId());
            if ((localArrayList2 != null) && (!localArrayList2.contains(str))) {
              localArrayList2.add(str);
            }
            i = localEntitlementGroup.getParentId();
            localObject1 = localEntitlementGroup;
            localEntitlementGroup = null;
          }
        }
        ArrayList localArrayList1 = jdField_if.getGroupMembers(paramEntitlementGroupMember.getEntitlementGroupId());
        if ((localArrayList1 != null) && (!localArrayList1.contains(paramEntitlementGroupMember))) {
          localArrayList1.add(paramEntitlementGroupMember);
        }
      }
      catch (PoolException localPoolException)
      {
        throw new EntitlementException(2, localPoolException);
      }
      finally
      {
        DBUtil.returnConnection(jdField_char.jdField_void, localConnection);
        localConnection = null;
        jdField_new.deEscalateLock();
      }
    }
    return localEntitlements;
  }
  
  private Entitlements a(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str1 = "EntitlementCachedAdapter.getCumulativeEntitlements";
    if (!verifyMemberExists(paramConnection, paramEntitlementGroupMember)) {
      throw new EntitlementException(str1, 5);
    }
    Entitlements localEntitlements = jdField_if.getRestrictedEntitlements(paramEntitlementGroupMember);
    if (localEntitlements == null)
    {
      localEntitlements = jdField_char.jdField_if(paramConnection, paramEntitlementGroupMember);
      jdField_new.escalateLock();
      try
      {
        jdField_if.setRestrictedEntitlements(paramEntitlementGroupMember, localEntitlements);
        Object localObject1 = jdField_if.getEntitlementGroup(paramEntitlementGroupMember.getEntitlementGroupId());
        if (localObject1 == null)
        {
          localObject1 = jdField_char.getEntitlementGroup(paramEntitlementGroupMember.getEntitlementGroupId());
          jdField_if.addEntitlementGroup((EntitlementGroup)localObject1);
          int i = ((EntitlementGroup)localObject1).getParentId();
          while (i != 0)
          {
            EntitlementGroup localEntitlementGroup = jdField_if.getEntitlementGroup(i);
            if (localEntitlementGroup == null)
            {
              localEntitlementGroup = jdField_char.getEntitlementGroup(i);
              jdField_if.addEntitlementGroup(localEntitlementGroup);
            }
            ArrayList localArrayList2 = jdField_if.getChildGroups(localEntitlementGroup.getGroupId());
            String str2 = Integer.toString(((EntitlementGroup)localObject1).getGroupId());
            if ((localArrayList2 != null) && (!localArrayList2.contains(str2))) {
              localArrayList2.add(str2);
            }
            i = localEntitlementGroup.getParentId();
            localObject1 = localEntitlementGroup;
            localEntitlementGroup = null;
          }
        }
        ArrayList localArrayList1 = jdField_if.getGroupMembers(paramEntitlementGroupMember.getEntitlementGroupId());
        if ((localArrayList1 != null) && (!localArrayList1.contains(paramEntitlementGroupMember))) {
          localArrayList1.add(paramEntitlementGroupMember);
        }
      }
      finally
      {
        jdField_new.deEscalateLock();
      }
    }
    return localEntitlements;
  }
  
  public EntitlementGroups getTopEntitlementGroups()
    throws EntitlementException
  {
    return jdField_char.getTopEntitlementGroups();
  }
  
  public EntitlementGroups getTopEntitlementGroupsBySvcBureau(int paramInt)
    throws EntitlementException
  {
    return jdField_char.getTopEntitlementGroupsBySvcBureau(paramInt);
  }
  
  public EntitlementGroup addEntitlementGroup(EntitlementGroupMember paramEntitlementGroupMember, EntitlementGroup paramEntitlementGroup)
    throws EntitlementException
  {
    String str1 = "EntitlementCachedAdapter.addEntitlementGroup";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str1, 5);
    }
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember, paramEntitlementGroup.getParentId());
    if (jdField_char.canExtend(localEntitlementAdmin))
    {
      EntitlementGroup localEntitlementGroup1 = jdField_char.addEntitlementGroup(paramEntitlementGroup, paramEntitlementGroupMember.getEntitlementGroupId());
      jdField_new.getWriteLock();
      try
      {
        jdField_if.addEntitlementGroup(localEntitlementGroup1);
        int i = localEntitlementGroup1.getParentId();
        Object localObject1 = localEntitlementGroup1;
        while (i != 0)
        {
          EntitlementGroup localEntitlementGroup2 = jdField_if.getEntitlementGroup(i);
          if (localEntitlementGroup2 == null)
          {
            localEntitlementGroup2 = jdField_char.getEntitlementGroup(i);
            jdField_if.addEntitlementGroup(localEntitlementGroup2);
          }
          ArrayList localArrayList = jdField_if.getChildGroups(localEntitlementGroup2.getGroupId());
          String str2 = Integer.toString(((EntitlementGroup)localObject1).getGroupId());
          if ((localArrayList != null) && (!localArrayList.contains(str2))) {
            localArrayList.add(str2);
          }
          i = localEntitlementGroup2.getParentId();
          localObject1 = localEntitlementGroup2;
          localEntitlementGroup2 = null;
        }
      }
      finally
      {
        jdField_new.releaseLock();
      }
      return localEntitlementGroup1;
    }
    throw new EntitlementException(str1, 17);
  }
  
  public void modifyEntitlementGroup(EntitlementGroupMember paramEntitlementGroupMember, EntitlementGroup paramEntitlementGroup, boolean paramBoolean)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.modifyEntitlementGroup";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    EntitlementGroup localEntitlementGroup = jdField_char.getEntitlementGroup(paramEntitlementGroup.getGroupId());
    if (paramBoolean)
    {
      EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember, paramEntitlementGroup.getGroupId());
      if (!jdField_char.isAdministratorOf(localEntitlementAdmin)) {
        throw new EntitlementException(str, 18);
      }
      if (localEntitlementGroup.getParentId() != paramEntitlementGroup.getParentId())
      {
        localEntitlementAdmin.setTargetGroupId(paramEntitlementGroup.getParentId());
        if (!jdField_char.canExtend(localEntitlementAdmin)) {
          throw new EntitlementException(str, 17);
        }
      }
    }
    jdField_char.modifyEntitlementGroup(paramEntitlementGroup);
    if (localEntitlementGroup.getParentId() != paramEntitlementGroup.getParentId())
    {
      jdField_new.getWriteLock();
      try
      {
        jdField_do(localEntitlementGroup.getGroupId());
        jdField_if.modifyEntitlementGroup(paramEntitlementGroup);
      }
      finally
      {
        jdField_new.releaseLock();
      }
    }
  }
  
  public void deleteEntitlementGroup(EntitlementGroupMember paramEntitlementGroupMember, EntitlementGroup paramEntitlementGroup)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.deleteEntitlementGroup";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember, paramEntitlementGroup.getGroupId());
    if (jdField_char.isAdministratorOf(localEntitlementAdmin))
    {
      jdField_new.getWriteLock();
      try
      {
        jdField_do(paramEntitlementGroup.getGroupId());
        jdField_char.deleteEntitlementGroup(paramEntitlementGroup);
      }
      finally
      {
        jdField_new.releaseLock();
      }
    }
    else
    {
      throw new EntitlementException(str, 18);
    }
  }
  
  public Entitlements getRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember, int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getRestrictedEntitlements";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    return jdField_char.getRestrictedEntitlements(paramInt);
  }
  
  public Entitlements getRestrictedEntitlementsUnsafe(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getRestrictedEntitlementsUnsafe";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    return jdField_char.getRestrictedEntitlements(paramInt);
  }
  
  public Entitlements getRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getRestrictedEntitlements";
    if (!verifyMemberExists(paramEntitlementGroupMember1)) {
      throw new EntitlementException(str, 5);
    }
    if (!verifyMemberExists(paramEntitlementGroupMember2)) {
      throw new EntitlementException(str, 5);
    }
    return jdField_char.getRestrictedEntitlements(paramEntitlementGroupMember2);
  }
  
  public Entitlements getRestrictedEntitlementsUnsafe(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getRestrictedEntitlementsUnsafe";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    return jdField_char.getRestrictedEntitlements(paramEntitlementGroupMember);
  }
  
  public void setRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember, int paramInt, Entitlements paramEntitlements)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.setRestrictedEntitlements";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember, paramInt);
    if (jdField_char.isAdministratorOf(localEntitlementAdmin))
    {
      jdField_char.setRestrictedEntitlements(paramInt, paramEntitlements);
      jdField_new.getWriteLock();
      try
      {
        jdField_do(paramInt);
      }
      finally
      {
        jdField_new.releaseLock();
      }
    }
    else
    {
      throw new EntitlementException(str, 18);
    }
  }
  
  public void setRestrictedEntitlementsUnsafe(EntitlementGroupMember paramEntitlementGroupMember, int paramInt, Entitlements paramEntitlements)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.setRestrictedEntitlements";
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember, paramInt);
    if (jdField_char.isAdministratorOf(localEntitlementAdmin)) {
      jdField_char.setRestrictedEntitlements(paramInt, paramEntitlements);
    } else {
      throw new EntitlementException(str, 18);
    }
  }
  
  public void setRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2, Entitlements paramEntitlements)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.setRestrictedEntitlements";
    if (!verifyMemberExists(paramEntitlementGroupMember1)) {
      throw new EntitlementException(str, 5);
    }
    if (!verifyMemberExists(paramEntitlementGroupMember2)) {
      throw new EntitlementException(str, 5);
    }
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember1, paramEntitlementGroupMember2.getEntitlementGroupId());
    if (jdField_char.isAdministratorOf(localEntitlementAdmin))
    {
      jdField_char.setRestrictedEntitlements(paramEntitlementGroupMember2, paramEntitlements);
      jdField_new.getWriteLock();
      try
      {
        jdField_if(paramEntitlementGroupMember2);
      }
      finally
      {
        jdField_new.releaseLock();
      }
    }
    else
    {
      throw new EntitlementException(str, 18);
    }
  }
  
  public void setRestrictedEntitlementsUnsafe(EntitlementGroupMember paramEntitlementGroupMember, Entitlements paramEntitlements)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.setRestrictedEntitlementsUnsafe";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    jdField_char.setRestrictedEntitlements(paramEntitlementGroupMember, paramEntitlements);
    jdField_new.getWriteLock();
    try
    {
      jdField_if(paramEntitlementGroupMember);
    }
    finally
    {
      jdField_new.releaseLock();
    }
  }
  
  public void addRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember, int paramInt, Entitlements paramEntitlements)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.addRestrictedEntitlements";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember, paramInt);
    if (jdField_char.isAdministratorOf(localEntitlementAdmin))
    {
      jdField_char.addRestrictedEntitlements(paramInt, paramEntitlements);
      jdField_new.getWriteLock();
      try
      {
        jdField_do(paramInt);
      }
      finally
      {
        jdField_new.releaseLock();
      }
    }
    else
    {
      throw new EntitlementException(str, 18);
    }
  }
  
  public void addRestrictedEntitlementsUnsafeNoDupCheck(EntitlementGroupMember paramEntitlementGroupMember, int paramInt, Entitlements paramEntitlements, boolean paramBoolean)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.addRestrictedEntitlementsUnsafeNoDupCheck";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    jdField_char.addRestrictedEntitlementsUnsafeNoDupCheck(paramInt, paramEntitlements);
    if (paramBoolean)
    {
      jdField_new.getWriteLock();
      try
      {
        jdField_do(paramInt);
      }
      finally
      {
        jdField_new.releaseLock();
      }
    }
  }
  
  public void addRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2, Entitlements paramEntitlements)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.addRestrictedEntitlements";
    if (!verifyMemberExists(paramEntitlementGroupMember1)) {
      throw new EntitlementException(str, 5);
    }
    if (!verifyMemberExists(paramEntitlementGroupMember2)) {
      throw new EntitlementException(str, 5);
    }
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember1, paramEntitlementGroupMember2.getEntitlementGroupId());
    if (jdField_char.isAdministratorOf(localEntitlementAdmin))
    {
      jdField_char.addRestrictedEntitlements(paramEntitlementGroupMember2, paramEntitlements);
      jdField_new.getWriteLock();
      try
      {
        jdField_if(paramEntitlementGroupMember2);
      }
      finally
      {
        jdField_new.releaseLock();
      }
    }
    else
    {
      throw new EntitlementException(str, 18);
    }
  }
  
  public void addRestrictedEntitlementsUnsafeNoDupCheck(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2, Entitlements paramEntitlements, boolean paramBoolean)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.addRestrictedEntitlements";
    if (!verifyMemberExists(paramEntitlementGroupMember1)) {
      throw new EntitlementException(str, 5);
    }
    if (!verifyMemberExists(paramEntitlementGroupMember2)) {
      throw new EntitlementException(str, 5);
    }
    jdField_char.addRestrictedEntitlementsUnsafeNoDupCheck(paramEntitlementGroupMember2, paramEntitlements);
    if (paramBoolean)
    {
      jdField_new.getWriteLock();
      try
      {
        jdField_if(paramEntitlementGroupMember2);
      }
      finally
      {
        jdField_new.releaseLock();
      }
    }
  }
  
  public void removeRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember, int paramInt, Entitlements paramEntitlements)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.removeRestrictedEntitlements";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember, paramInt);
    if (jdField_char.isAdministratorOf(localEntitlementAdmin))
    {
      jdField_new.getWriteLock();
      try
      {
        jdField_do(paramInt);
        jdField_char.removeRestrictedEntitlements(paramInt, paramEntitlements);
      }
      finally
      {
        jdField_new.releaseLock();
      }
    }
    else
    {
      throw new EntitlementException(str, 18);
    }
  }
  
  public void removeRestrictedEntitlementsUnsafe(int paramInt, Entitlements paramEntitlements)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.removeRestrictedEntitlementsUnsafe";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    jdField_new.getWriteLock();
    try
    {
      jdField_do(paramInt);
      jdField_char.removeRestrictedEntitlements(paramInt, paramEntitlements);
    }
    finally
    {
      jdField_new.releaseLock();
    }
  }
  
  public void removeRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2, Entitlements paramEntitlements)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.removeRestrictedEntitlements";
    if (!verifyMemberExists(paramEntitlementGroupMember1)) {
      throw new EntitlementException(str, 5);
    }
    if (!verifyMemberExists(paramEntitlementGroupMember2)) {
      throw new EntitlementException(str, 5);
    }
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember1, paramEntitlementGroupMember2.getEntitlementGroupId());
    if (jdField_char.isAdministratorOf(localEntitlementAdmin))
    {
      jdField_new.getWriteLock();
      try
      {
        jdField_if(paramEntitlementGroupMember2);
        jdField_char.removeRestrictedEntitlements(paramEntitlementGroupMember2, paramEntitlements);
      }
      finally
      {
        jdField_new.releaseLock();
      }
    }
    else
    {
      throw new EntitlementException(str, 18);
    }
  }
  
  public void removeRestrictedEntitlementsUnsafe(EntitlementGroupMember paramEntitlementGroupMember, Entitlements paramEntitlements)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.removeRestrictedEntitlementsUnsafe";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    jdField_new.getWriteLock();
    try
    {
      jdField_if(paramEntitlementGroupMember);
      jdField_char.removeRestrictedEntitlements(paramEntitlementGroupMember, paramEntitlements);
    }
    finally
    {
      jdField_new.releaseLock();
    }
  }
  
  public EntitlementGroups getChildrenEntitlementGroups(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getChildrenEntitlementGroups";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    return jdField_char.getEntitlementGroupsByParentId(paramInt);
  }
  
  public EntitlementGroup getChildrenEntitlementGroups(int paramInt, ReopenableDBCookie paramReopenableDBCookie)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getChildrenEntitlementGroups";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    return jdField_char.getEntitlementGroupsByParentId(paramInt, paramReopenableDBCookie);
  }
  
  public EntitlementGroups getChildrenByGroupType(int paramInt, String paramString)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getChildrenByGroupType";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    if (paramString == null) {
      throw new NullPointerException();
    }
    return jdField_char.getChildrenByGroupType(paramInt, paramString);
  }
  
  public EntitlementGroup getChildrenByGroupType(int paramInt, String paramString, DBCookie paramDBCookie)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getChildrenByGroupType";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    if (paramString == null) {
      throw new NullPointerException();
    }
    return jdField_char.getChildrenByGroupType(paramInt, paramString, paramDBCookie);
  }
  
  public EntitlementGroups getEntitlementGroupsByType(String paramString)
    throws EntitlementException
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    return jdField_char.getEntitlementGroupsByType(paramString);
  }
  
  public EntitlementGroups getEntitlementGroupsByTypeAndSvcBureau(String paramString, int paramInt)
    throws EntitlementException
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    return jdField_char.getEntitlementGroupsByTypeAndSvcBureau(paramString, paramInt);
  }
  
  public EntitlementGroup getEntitlementGroupByNameAndSvcBureau(String paramString1, String paramString2, int paramInt)
    throws EntitlementException
  {
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    if (paramString2 == null) {
      throw new NullPointerException();
    }
    return jdField_char.getEntitlementGroupByNameAndSvcBureau(paramString1, paramString2, paramInt);
  }
  
  public Limits getCompressedLimits(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getCompressedLimits";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    jdField_new.getReadLock();
    try
    {
      Limits localLimits = a(paramInt);
      return localLimits;
    }
    finally
    {
      jdField_new.releaseLock();
    }
  }
  
  private Limits a(int paramInt)
    throws EntitlementException
  {
    String str1 = "EntitlementCachedAdapter.getCompressedLimits";
    Limits localLimits = jdField_if.getCompressedLimits(paramInt);
    if (localLimits != null) {
      return localLimits;
    }
    EntitlementGroup localEntitlementGroup1 = jdField_if.getEntitlementGroup(paramInt);
    if (localEntitlementGroup1 == null) {
      localEntitlementGroup1 = jdField_char.getEntitlementGroup(paramInt);
    }
    if (localEntitlementGroup1.getParentId() != 0) {
      localLimits = a(a(localEntitlementGroup1.getParentId()), jdField_char.getGroupLimits(paramInt), jdField_char.getEntitlementGroup(paramInt));
    } else {
      localLimits = jdField_char.getCumulativeLimits(paramInt);
    }
    jdField_if(paramInt, localLimits);
    jdField_new.escalateLock();
    try
    {
      if (!jdField_if.isGroupInCache(paramInt))
      {
        jdField_if.addEntitlementGroup(localEntitlementGroup1);
        int i = localEntitlementGroup1.getParentId();
        Object localObject1 = localEntitlementGroup1;
        while (i != 0)
        {
          EntitlementGroup localEntitlementGroup2 = jdField_if.getEntitlementGroup(i);
          if (localEntitlementGroup2 == null)
          {
            localEntitlementGroup2 = jdField_char.getEntitlementGroup(i);
            jdField_if.addEntitlementGroup(localEntitlementGroup2);
          }
          ArrayList localArrayList = jdField_if.getChildGroups(localEntitlementGroup2.getGroupId());
          String str2 = Integer.toString(((EntitlementGroup)localObject1).getGroupId());
          if ((localArrayList != null) && (!localArrayList.contains(str2))) {
            localArrayList.add(str2);
          }
          i = localEntitlementGroup2.getParentId();
          localObject1 = localEntitlementGroup2;
          localEntitlementGroup2 = null;
        }
      }
      jdField_if.addCompressedLimits(paramInt, localLimits);
    }
    finally
    {
      jdField_new.deEscalateLock();
    }
    return localLimits;
  }
  
  public Limits getCumulativeLimits(int paramInt)
    throws EntitlementException
  {
    String str1 = "EntitlementCachedAdapter.getCumulativeLimits";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str1, 3);
    }
    jdField_new.getReadLock();
    Limits localLimits;
    try
    {
      localLimits = jdField_if.getGroupLimits(paramInt);
      if (localLimits == null)
      {
        EntitlementGroup localEntitlementGroup1 = jdField_if.getEntitlementGroup(paramInt);
        if (localEntitlementGroup1 == null) {
          localEntitlementGroup1 = jdField_char.getEntitlementGroup(paramInt);
        }
        if (localEntitlementGroup1.getParentId() != 0)
        {
          localLimits = jdField_char.getCumulativeLimits(localEntitlementGroup1.getParentId());
          localLimits.addAll(jdField_char.getGroupLimits(paramInt));
        }
        else
        {
          localLimits = jdField_char.getGroupLimits(paramInt);
        }
        jdField_if(paramInt, localLimits);
        jdField_new.escalateLock();
        if (!jdField_if.isGroupInCache(paramInt))
        {
          jdField_if.addEntitlementGroup(localEntitlementGroup1);
          int i = localEntitlementGroup1.getParentId();
          Object localObject1 = localEntitlementGroup1;
          while (i != 0)
          {
            EntitlementGroup localEntitlementGroup2 = jdField_if.getEntitlementGroup(i);
            if (localEntitlementGroup2 == null)
            {
              localEntitlementGroup2 = jdField_char.getEntitlementGroup(i);
              jdField_if.addEntitlementGroup(localEntitlementGroup2);
            }
            ArrayList localArrayList = jdField_if.getChildGroups(localEntitlementGroup2.getGroupId());
            String str2 = Integer.toString(((EntitlementGroup)localObject1).getGroupId());
            if ((localArrayList != null) && (!localArrayList.contains(str2))) {
              localArrayList.add(str2);
            }
            i = localEntitlementGroup2.getParentId();
            localObject1 = localEntitlementGroup2;
            localEntitlementGroup2 = null;
          }
        }
        jdField_if.addGroupLimits(paramInt, localLimits);
      }
    }
    finally
    {
      jdField_new.releaseLock();
    }
    return localLimits;
  }
  
  public Limits getCumulativeLimits(Connection paramConnection, int paramInt)
    throws EntitlementException
  {
    String str1 = "EntitlementCachedAdapter.getCumulativeLimits";
    if (!a(paramConnection, paramInt)) {
      throw new EntitlementException(str1, 3);
    }
    jdField_new.getReadLock();
    Limits localLimits;
    try
    {
      localLimits = jdField_if.getGroupLimits(paramInt);
      if (localLimits == null)
      {
        EntitlementGroup localEntitlementGroup1 = jdField_if.getEntitlementGroup(paramInt);
        if (localEntitlementGroup1 == null) {
          localEntitlementGroup1 = jdField_char.jdField_byte(paramConnection, paramInt);
        }
        if (localEntitlementGroup1.getParentId() != 0)
        {
          localLimits = jdField_char.jdMethod_goto(paramConnection, localEntitlementGroup1.getParentId());
          localLimits.addAll(jdField_char.jdField_for(paramConnection, paramInt));
        }
        else
        {
          localLimits = jdField_char.jdField_for(paramConnection, paramInt);
        }
        a(paramConnection, paramInt, localLimits);
        jdField_new.escalateLock();
        if (!jdField_if.isGroupInCache(paramInt))
        {
          jdField_if.addEntitlementGroup(localEntitlementGroup1);
          int i = localEntitlementGroup1.getParentId();
          Object localObject1 = localEntitlementGroup1;
          while (i != 0)
          {
            EntitlementGroup localEntitlementGroup2 = jdField_if.getEntitlementGroup(i);
            if (localEntitlementGroup2 == null)
            {
              localEntitlementGroup2 = jdField_char.getEntitlementGroup(i);
              jdField_if.addEntitlementGroup(localEntitlementGroup2);
            }
            ArrayList localArrayList = jdField_if.getChildGroups(localEntitlementGroup2.getGroupId());
            String str2 = Integer.toString(((EntitlementGroup)localObject1).getGroupId());
            if ((localArrayList != null) && (!localArrayList.contains(str2))) {
              localArrayList.add(str2);
            }
            i = localEntitlementGroup2.getParentId();
            localObject1 = localEntitlementGroup2;
            localEntitlementGroup2 = null;
          }
        }
        jdField_if.addGroupLimits(paramInt, localLimits);
      }
    }
    finally
    {
      jdField_new.releaseLock();
    }
    return localLimits;
  }
  
  public Limits getCompressedLimits(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getCompressedLimits";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    jdField_new.getReadLock();
    try
    {
      Limits localLimits = getCompressedLimitsWithReadLock(paramEntitlementGroupMember);
      return localLimits;
    }
    finally
    {
      jdField_new.releaseLock();
    }
  }
  
  public Limits getCompressedLimitsWithReadLock(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str1 = "EntitlementCachedAdapter.getCompressedLimits";
    Limits localLimits = jdField_if.getCompressedLimits(paramEntitlementGroupMember);
    if (localLimits != null) {
      return localLimits;
    }
    localLimits = a(a(paramEntitlementGroupMember.getEntitlementGroupId()), jdField_char.getGroupLimits(paramEntitlementGroupMember), paramEntitlementGroupMember);
    jdField_if(paramEntitlementGroupMember, localLimits);
    jdField_new.escalateLock();
    try
    {
      Object localObject1 = jdField_if.getEntitlementGroup(paramEntitlementGroupMember.getEntitlementGroupId());
      if (localObject1 == null)
      {
        localObject1 = jdField_char.getEntitlementGroup(paramEntitlementGroupMember.getEntitlementGroupId());
        jdField_if.addEntitlementGroup((EntitlementGroup)localObject1);
        int i = ((EntitlementGroup)localObject1).getParentId();
        while (i != 0)
        {
          EntitlementGroup localEntitlementGroup = jdField_if.getEntitlementGroup(i);
          if (localEntitlementGroup == null)
          {
            localEntitlementGroup = jdField_char.getEntitlementGroup(i);
            jdField_if.addEntitlementGroup(localEntitlementGroup);
          }
          ArrayList localArrayList2 = jdField_if.getChildGroups(localEntitlementGroup.getGroupId());
          String str2 = Integer.toString(((EntitlementGroup)localObject1).getGroupId());
          if ((localArrayList2 != null) && (!localArrayList2.contains(str2))) {
            localArrayList2.add(str2);
          }
          i = localEntitlementGroup.getParentId();
          localObject1 = localEntitlementGroup;
          localEntitlementGroup = null;
        }
      }
      ArrayList localArrayList1 = jdField_if.getGroupMembers(paramEntitlementGroupMember.getEntitlementGroupId());
      if ((localArrayList1 != null) && (!localArrayList1.contains(paramEntitlementGroupMember))) {
        localArrayList1.add(paramEntitlementGroupMember);
      }
      jdField_if.addCompressedLimits(paramEntitlementGroupMember, localLimits);
    }
    finally
    {
      jdField_new.deEscalateLock();
    }
    return localLimits;
  }
  
  public Limits getCumulativeLimits(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str1 = "EntitlementCachedAdapter.getCumulativeLimits";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str1, 5);
    }
    Connection localConnection = null;
    jdField_new.getReadLock();
    Limits localLimits;
    try
    {
      localLimits = jdField_if.getGroupLimits(paramEntitlementGroupMember);
      if (localLimits == null)
      {
        localConnection = DBUtil.getConnection(jdField_char.jdField_void, true, 2);
        localLimits = jdField_char.jdMethod_goto(localConnection, paramEntitlementGroupMember.getEntitlementGroupId());
        localLimits.addAll(jdField_char.jdField_try(localConnection, paramEntitlementGroupMember));
        jdField_if(paramEntitlementGroupMember, localLimits);
        jdField_new.escalateLock();
        Object localObject1 = jdField_if.getEntitlementGroup(paramEntitlementGroupMember.getEntitlementGroupId());
        if (localObject1 == null)
        {
          localObject1 = jdField_char.jdField_byte(localConnection, paramEntitlementGroupMember.getEntitlementGroupId());
          jdField_if.addEntitlementGroup((EntitlementGroup)localObject1);
          int i = ((EntitlementGroup)localObject1).getParentId();
          while (i != 0)
          {
            EntitlementGroup localEntitlementGroup = jdField_if.getEntitlementGroup(i);
            if (localEntitlementGroup == null)
            {
              localEntitlementGroup = jdField_char.jdField_byte(localConnection, i);
              jdField_if.addEntitlementGroup(localEntitlementGroup);
            }
            ArrayList localArrayList2 = jdField_if.getChildGroups(localEntitlementGroup.getGroupId());
            String str2 = Integer.toString(((EntitlementGroup)localObject1).getGroupId());
            if ((localArrayList2 != null) && (!localArrayList2.contains(str2))) {
              localArrayList2.add(str2);
            }
            i = localEntitlementGroup.getParentId();
            localObject1 = localEntitlementGroup;
            localEntitlementGroup = null;
          }
        }
        ArrayList localArrayList1 = jdField_if.getGroupMembers(paramEntitlementGroupMember.getEntitlementGroupId());
        if ((localArrayList1 != null) && (!localArrayList1.contains(paramEntitlementGroupMember))) {
          localArrayList1.add(paramEntitlementGroupMember);
        }
        jdField_if.addGroupLimits(paramEntitlementGroupMember, localLimits);
      }
    }
    catch (PoolException localPoolException)
    {
      throw new EntitlementException(localPoolException, str1, 2);
    }
    finally
    {
      DBUtil.returnConnection(jdField_char.jdField_void, localConnection);
      localConnection = null;
      jdField_new.releaseLock();
    }
    return localLimits;
  }
  
  public Limits getCumulativeLimits(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str1 = "EntitlementCachedAdapter.getCumulativeLimits";
    if (!verifyMemberExists(paramConnection, paramEntitlementGroupMember)) {
      throw new EntitlementException(str1, 5);
    }
    jdField_new.getReadLock();
    Limits localLimits;
    try
    {
      localLimits = jdField_if.getGroupLimits(paramEntitlementGroupMember);
      if (localLimits == null)
      {
        localLimits = jdField_char.jdMethod_goto(paramConnection, paramEntitlementGroupMember.getEntitlementGroupId());
        localLimits.addAll(jdField_char.jdField_try(paramConnection, paramEntitlementGroupMember));
        a(paramConnection, paramEntitlementGroupMember, localLimits);
        jdField_new.escalateLock();
        Object localObject1 = jdField_if.getEntitlementGroup(paramEntitlementGroupMember.getEntitlementGroupId());
        if (localObject1 == null)
        {
          localObject1 = jdField_char.getEntitlementGroup(paramEntitlementGroupMember.getEntitlementGroupId());
          jdField_if.addEntitlementGroup((EntitlementGroup)localObject1);
          int i = ((EntitlementGroup)localObject1).getParentId();
          while (i != 0)
          {
            EntitlementGroup localEntitlementGroup = jdField_if.getEntitlementGroup(i);
            if (localEntitlementGroup == null)
            {
              localEntitlementGroup = jdField_char.getEntitlementGroup(i);
              jdField_if.addEntitlementGroup(localEntitlementGroup);
            }
            ArrayList localArrayList2 = jdField_if.getChildGroups(localEntitlementGroup.getGroupId());
            String str2 = Integer.toString(((EntitlementGroup)localObject1).getGroupId());
            if ((localArrayList2 != null) && (!localArrayList2.contains(str2))) {
              localArrayList2.add(str2);
            }
            i = localEntitlementGroup.getParentId();
            localObject1 = localEntitlementGroup;
            localEntitlementGroup = null;
          }
        }
        ArrayList localArrayList1 = jdField_if.getGroupMembers(paramEntitlementGroupMember.getEntitlementGroupId());
        if ((localArrayList1 != null) && (!localArrayList1.contains(paramEntitlementGroupMember))) {
          localArrayList1.add(paramEntitlementGroupMember);
        }
        jdField_if.addGroupLimits(paramEntitlementGroupMember, localLimits);
      }
    }
    finally
    {
      jdField_new.releaseLock();
    }
    return localLimits;
  }
  
  public Limits getCompressedLimits(EntitlementGroupMember paramEntitlementGroupMember, Limit paramLimit)
    throws EntitlementException
  {
    return a(getCumulativeLimits(paramEntitlementGroupMember, paramLimit), paramEntitlementGroupMember);
  }
  
  public Limits getCumulativeLimits(EntitlementGroupMember paramEntitlementGroupMember, Limit paramLimit)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getCumulativeLimits";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    Limits localLimits = jdField_char.getCumulativeLimits(paramEntitlementGroupMember, paramLimit);
    a(paramEntitlementGroupMember, localLimits);
    return localLimits;
  }
  
  public Limits getCompressedLimits(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2, Limit paramLimit)
    throws EntitlementException
  {
    return a(getCumulativeLimits(paramEntitlementGroupMember1, paramEntitlementGroupMember2, paramLimit), paramEntitlementGroupMember2);
  }
  
  public Limits getCumulativeLimits(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2, Limit paramLimit)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getCumulativeLimits";
    if (!verifyMemberExists(paramEntitlementGroupMember1)) {
      throw new EntitlementException(str, 5);
    }
    if (!verifyMemberExists(paramEntitlementGroupMember2)) {
      throw new EntitlementException(str, 19);
    }
    Limits localLimits = jdField_char.getCumulativeLimits(paramEntitlementGroupMember2, paramLimit);
    a(paramEntitlementGroupMember2, localLimits);
    return localLimits;
  }
  
  public Limits getCompressedLimits(EntitlementGroupMember paramEntitlementGroupMember, int paramInt, Limit paramLimit)
    throws EntitlementException
  {
    return a(getCumulativeLimits(paramEntitlementGroupMember, paramInt, paramLimit), getEntitlementGroup(paramInt));
  }
  
  public Limits getCumulativeLimits(EntitlementGroupMember paramEntitlementGroupMember, int paramInt, Limit paramLimit)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getCumulativeLimits";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    Limits localLimits = jdField_char.getCumulativeLimits(paramInt, paramLimit);
    a(paramInt, localLimits);
    return localLimits;
  }
  
  public Limits getGroupLimits(EntitlementGroupMember paramEntitlementGroupMember, int paramInt, Limit paramLimit, HashMap paramHashMap)
    throws EntitlementException
  {
    String str1 = "EntitlementCachedAdapter.getGroupLimits";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str1, 5);
    }
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str1, 3);
    }
    Limits localLimits = jdField_char.getGroupLimits(paramInt, paramLimit);
    String str2 = null;
    if (paramHashMap != null) {
      str2 = (String)paramHashMap.get("HaveWriteLock");
    }
    if ((str2 != null) && (str2.equals("yes"))) {
      jdField_do(paramInt, localLimits);
    } else {
      a(paramInt, localLimits);
    }
    return localLimits;
  }
  
  public Limits getGroupLimits(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2, Limit paramLimit, HashMap paramHashMap)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getGroupLimits";
    if (!verifyMemberExists(paramEntitlementGroupMember1)) {
      throw new EntitlementException(str, 5);
    }
    if (!verifyMemberExists(paramEntitlementGroupMember2)) {
      throw new EntitlementException(str, 19);
    }
    Limits localLimits = jdField_char.getGroupLimits(paramEntitlementGroupMember2, paramLimit);
    a(paramEntitlementGroupMember2, localLimits);
    return localLimits;
  }
  
  public Limits getGroupLimits(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getGroupLimits";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    Limits localLimits = jdField_char.getGroupLimits(paramInt);
    a(paramInt, localLimits);
    return localLimits;
  }
  
  public Limits getGroupLimits(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str = "Entitlements.getGroupLimits";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    Limits localLimits = jdField_char.getGroupLimits(paramEntitlementGroupMember);
    a(paramEntitlementGroupMember, localLimits);
    return localLimits;
  }
  
  public Limit addGroupLimit(EntitlementGroupMember paramEntitlementGroupMember, Limit paramLimit)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.addGroupLimit";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    if (!jdField_int(paramLimit.getGroupId())) {
      throw new EntitlementException(str, 3);
    }
    jdField_if(paramLimit.getEntitlement());
    if (!paramLimit.isPeriodValid()) {
      throw new EntitlementException(str, 20);
    }
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember, paramLimit.getGroupId());
    if (jdField_char.isAdministratorOf(localEntitlementAdmin))
    {
      Limit localLimit = jdField_char.addGroupLimit(paramLimit);
      jdField_new.getWriteLock();
      try
      {
        if (paramLimit.getMember() == null) {
          jdField_do(paramLimit.getGroupId());
        } else {
          jdField_if(paramLimit.getMember());
        }
      }
      finally
      {
        jdField_new.releaseLock();
      }
      return localLimit;
    }
    throw new EntitlementException(str, 18);
  }
  
  public Limit addGroupLimitUnsafe(EntitlementGroupMember paramEntitlementGroupMember, Limit paramLimit)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.addGroupLimit";
    jdField_if(paramLimit.getEntitlement());
    if (!paramLimit.isPeriodValid()) {
      throw new EntitlementException(str, 20);
    }
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember, paramLimit.getGroupId());
    if (jdField_char.isAdministratorOf(localEntitlementAdmin)) {
      return jdField_char.addGroupLimit(paramLimit);
    }
    throw new EntitlementException(str, 18);
  }
  
  public void deleteGroupLimit(EntitlementGroupMember paramEntitlementGroupMember, Limit paramLimit)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.deleteGroupLimit";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember, paramLimit.getGroupId());
    if (jdField_char.isAdministratorOf(localEntitlementAdmin))
    {
      jdField_new.getWriteLock();
      try
      {
        if (paramLimit.getMember() == null) {
          jdField_do(paramLimit.getGroupId());
        } else {
          jdField_if(paramLimit.getMember());
        }
        jdField_char.deleteGroupLimit(paramLimit);
      }
      finally
      {
        jdField_new.releaseLock();
      }
    }
    else
    {
      throw new EntitlementException(str, 18);
    }
  }
  
  public void deleteGroupLimitUnsafe(EntitlementGroupMember paramEntitlementGroupMember, Limit paramLimit)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.deleteGroupLimit";
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember, paramLimit.getGroupId());
    if (jdField_char.isAdministratorOf(localEntitlementAdmin)) {
      jdField_char.deleteGroupLimit(paramLimit);
    } else {
      throw new EntitlementException(str, 18);
    }
  }
  
  public void deleteGroupLimitUnsafe(Limit paramLimit)
    throws EntitlementException
  {
    jdField_new.getWriteLock();
    try
    {
      if (paramLimit.getMember() == null) {
        jdField_do(paramLimit.getGroupId());
      } else {
        jdField_if(paramLimit.getMember());
      }
      jdField_char.deleteGroupLimit(paramLimit);
    }
    finally
    {
      jdField_new.releaseLock();
    }
  }
  
  public Limit modifyGroupLimit(EntitlementGroupMember paramEntitlementGroupMember, Limit paramLimit)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.modifyGroupLimit";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    if (!jdField_int(paramLimit.getGroupId())) {
      throw new EntitlementException(str, 3);
    }
    jdField_if(paramLimit.getEntitlement());
    if (!paramLimit.isPeriodValid()) {
      throw new EntitlementException(str, 20);
    }
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember, paramLimit.getGroupId());
    if (jdField_char.isAdministratorOf(localEntitlementAdmin))
    {
      Limit localLimit = jdField_char.getGroupLimit(paramLimit.getLimitId());
      if (localLimit.getGroupId() != paramLimit.getGroupId())
      {
        localEntitlementAdmin.setTargetGroupId(localLimit.getGroupId());
        if (!jdField_char.isAdministratorOf(localEntitlementAdmin)) {
          throw new EntitlementException(str, 18);
        }
      }
      jdField_char.modifyGroupLimit(paramLimit);
      jdField_new.getWriteLock();
      try
      {
        if (paramLimit.getMember() == null) {
          jdField_do(paramLimit.getGroupId());
        } else {
          jdField_if(paramLimit.getMember());
        }
      }
      finally
      {
        jdField_new.releaseLock();
      }
      return paramLimit;
    }
    throw new EntitlementException(str, 18);
  }
  
  public Limit modifyGroupLimitUnsafe(EntitlementGroupMember paramEntitlementGroupMember, Limit paramLimit)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.modifyGroupLimit";
    jdField_if(paramLimit.getEntitlement());
    if (!paramLimit.isPeriodValid()) {
      throw new EntitlementException(str, 20);
    }
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember, paramLimit.getGroupId());
    if (jdField_char.isAdministratorOf(localEntitlementAdmin))
    {
      Limit localLimit = jdField_char.getGroupLimit(paramLimit.getLimitId());
      if (localLimit.getGroupId() != paramLimit.getGroupId())
      {
        localEntitlementAdmin.setTargetGroupId(localLimit.getGroupId());
        if (!jdField_char.isAdministratorOf(localEntitlementAdmin)) {
          throw new EntitlementException(str, 18);
        }
      }
      jdField_char.modifyGroupLimit(paramLimit);
      return paramLimit;
    }
    throw new EntitlementException(str, 18);
  }
  
  public void checkLimitsDelete(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, float paramFloat, Date paramDate)
    throws EntitlementException
  {
    checkLimitsDelete(paramEntitlementGroupMember, paramEntitlement, new BigDecimal(paramFloat), paramDate);
  }
  
  public void checkLimitsDelete(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, Date paramDate)
    throws EntitlementException
  {
    try
    {
      checkLimitsDelete(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, this.jdField_try, paramDate);
    }
    catch (NoClassDefFoundError localNoClassDefFoundError)
    {
      checkLimitsDelete(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, "USD", paramDate);
    }
  }
  
  public void checkLimitsDelete(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.checkLimitsDelete";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    jdField_if(paramEntitlement);
    jdField_char.checkLimitsDelete(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, paramString, paramDate, getCumulativeLimits(paramEntitlementGroupMember));
  }
  
  public void checkLimitsDelete(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.checkLimitsDelete";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    Entitlements localEntitlements = EntitlementUtil.getEntitlements(paramMultiEntitlement);
    for (int i = 0; i < localEntitlements.size(); i++) {
      jdField_if(localEntitlements.getEntitlement(i));
    }
    jdField_char.checkLimitsDelete(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, paramDate, getCumulativeLimits(paramEntitlementGroupMember));
  }
  
  public void checkLimitsDelete(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.checkLimitsDelete";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    Entitlements localEntitlements = EntitlementUtil.getEntitlements(paramMultiEntitlement);
    for (int i = 0; i < localEntitlements.size(); i++) {
      jdField_if(localEntitlements.getEntitlement(i));
    }
    jdField_char.checkLimitsDelete(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, paramString, paramDate, getCumulativeLimits(paramEntitlementGroupMember));
  }
  
  public void checkLimitsDelete(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, float paramFloat, Date paramDate)
    throws EntitlementException
  {
    checkLimitsDelete(paramConnection, paramEntitlementGroupMember, paramEntitlement, new BigDecimal(paramFloat), paramDate);
  }
  
  public void checkLimitsDelete(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, Date paramDate)
    throws EntitlementException
  {
    try
    {
      checkLimitsDelete(paramConnection, paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, this.jdField_try, paramDate);
    }
    catch (NoClassDefFoundError localNoClassDefFoundError)
    {
      checkLimitsDelete(paramConnection, paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, "USD", paramDate);
    }
  }
  
  public void checkLimitsDelete(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.checkLimitsDelete";
    if (paramEntitlementGroupMember.getEntitlementGroupId() == -1) {
      paramEntitlementGroupMember = getMember(paramConnection, paramEntitlementGroupMember);
    } else if (!verifyMemberExists(paramConnection, paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    jdField_if(paramEntitlement);
    jdField_char.checkLimitsDelete(paramConnection, paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, paramString, paramDate, getCumulativeLimits(paramConnection, paramEntitlementGroupMember));
  }
  
  public void checkLimitsDelete(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate)
    throws EntitlementException
  {
    try
    {
      checkLimitsDelete(paramConnection, paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, this.jdField_try, paramDate);
    }
    catch (NoClassDefFoundError localNoClassDefFoundError)
    {
      checkLimitsDelete(paramConnection, paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, "USD", paramDate);
    }
  }
  
  public void checkLimitsDelete(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.checkLimitsDelete";
    if (paramEntitlementGroupMember.getEntitlementGroupId() == -1) {
      paramEntitlementGroupMember = getMember(paramConnection, paramEntitlementGroupMember);
    } else if (!verifyMemberExists(paramConnection, paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    Entitlements localEntitlements = EntitlementUtil.getEntitlements(paramMultiEntitlement);
    for (int i = 0; i < localEntitlements.size(); i++) {
      jdField_if(localEntitlements.getEntitlement(i));
    }
    jdField_char.checkLimitsDelete(paramConnection, paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, paramString, paramDate, getCumulativeLimits(paramConnection, paramEntitlementGroupMember));
  }
  
  public Limits confirmLimitsBeforeAdd(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, float paramFloat, Date paramDate)
    throws EntitlementException
  {
    return confirmLimitsBeforeAdd(paramEntitlementGroupMember, paramEntitlement, new BigDecimal(paramFloat), paramDate);
  }
  
  public Limits confirmLimitsBeforeAdd(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, Date paramDate)
    throws EntitlementException
  {
    try
    {
      return confirmLimitsBeforeAdd(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, this.jdField_try, paramDate);
    }
    catch (NoClassDefFoundError localNoClassDefFoundError) {}
    return confirmLimitsBeforeAdd(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, "USD", paramDate);
  }
  
  public Limits confirmLimitsBeforeAdd(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.confirmLimitsBeforeAdd";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    jdField_if(paramEntitlement);
    return jdField_char.confirmLimitsBeforeAdd(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, paramString, paramDate, getCumulativeLimits(paramEntitlementGroupMember));
  }
  
  public Limits confirmLimitsBeforeAdd(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate)
    throws EntitlementException
  {
    try
    {
      return confirmLimitsBeforeAdd(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, this.jdField_try, paramDate);
    }
    catch (NoClassDefFoundError localNoClassDefFoundError) {}
    return confirmLimitsBeforeAdd(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, "USD", paramDate);
  }
  
  public Limits confirmLimitsBeforeAdd(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.confirmLimitsBeforeAdd";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    Entitlements localEntitlements = EntitlementUtil.getEntitlements(paramMultiEntitlement);
    for (int i = 0; i < localEntitlements.size(); i++) {
      jdField_if(localEntitlements.getEntitlement(i));
    }
    return jdField_char.confirmLimitsBeforeAdd(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, paramString, paramDate, getCumulativeLimits(paramEntitlementGroupMember));
  }
  
  public Limits checkLimitsAdd(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, float paramFloat, Date paramDate)
    throws EntitlementException
  {
    return checkLimitsAdd(paramEntitlementGroupMember, paramEntitlement, new BigDecimal(paramFloat), paramDate);
  }
  
  public Limits checkLimitsAdd(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, Date paramDate)
    throws EntitlementException
  {
    try
    {
      return checkLimitsAdd(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, this.jdField_try, paramDate);
    }
    catch (NoClassDefFoundError localNoClassDefFoundError) {}
    return checkLimitsAdd(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, "USD", paramDate);
  }
  
  public Limits checkLimitsAdd(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.checkLimitsAdd";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    jdField_if(paramEntitlement);
    return jdField_char.checkLimitsAdd(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, paramString, paramDate, getCumulativeLimits(paramEntitlementGroupMember));
  }
  
  public Limits checkLimitsAdd(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate)
    throws EntitlementException
  {
    try
    {
      return checkLimitsAdd(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, this.jdField_try, paramDate);
    }
    catch (NoClassDefFoundError localNoClassDefFoundError) {}
    return checkLimitsAdd(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, "USD", paramDate);
  }
  
  public Limits checkLimitsAdd(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.checkLimitsAdd";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    Entitlements localEntitlements = EntitlementUtil.getEntitlements(paramMultiEntitlement);
    for (int i = 0; i < localEntitlements.size(); i++) {
      jdField_if(localEntitlements.getEntitlement(i));
    }
    return jdField_char.checkLimitsAdd(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, paramString, paramDate, getCumulativeLimits(paramEntitlementGroupMember));
  }
  
  public Limits checkLimitsAdd(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, float paramFloat, Date paramDate)
    throws EntitlementException
  {
    return checkLimitsAdd(paramConnection, paramEntitlementGroupMember, paramEntitlement, new BigDecimal(paramFloat), paramDate);
  }
  
  public Limits checkLimitsAdd(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, Date paramDate)
    throws EntitlementException
  {
    try
    {
      return checkLimitsAdd(paramConnection, paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, this.jdField_try, paramDate);
    }
    catch (NoClassDefFoundError localNoClassDefFoundError) {}
    return checkLimitsAdd(paramConnection, paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, "USD", paramDate);
  }
  
  public Limits checkLimitsAdd(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.checkLimitsAdd";
    if (paramEntitlementGroupMember.getEntitlementGroupId() == -1) {
      paramEntitlementGroupMember = getMember(paramConnection, paramEntitlementGroupMember);
    } else if (!verifyMemberExists(paramConnection, paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    jdField_if(paramEntitlement);
    return jdField_char.checkLimitsAdd(paramConnection, paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, paramDate, getCumulativeLimits(paramConnection, paramEntitlementGroupMember));
  }
  
  public Limits checkLimitsAdd(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate)
    throws EntitlementException
  {
    try
    {
      return checkLimitsAdd(paramConnection, paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, this.jdField_try, paramDate);
    }
    catch (NoClassDefFoundError localNoClassDefFoundError) {}
    return checkLimitsAdd(paramConnection, paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, "USD", paramDate);
  }
  
  public Limits checkLimitsAdd(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.checkLimitsAdd";
    if (paramEntitlementGroupMember.getEntitlementGroupId() == -1) {
      paramEntitlementGroupMember = getMember(paramConnection, paramEntitlementGroupMember);
    } else if (!verifyMemberExists(paramConnection, paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    Entitlements localEntitlements = EntitlementUtil.getEntitlements(paramMultiEntitlement);
    for (int i = 0; i < localEntitlements.size(); i++) {
      jdField_if(localEntitlements.getEntitlement(i));
    }
    return jdField_char.checkLimitsAdd(paramConnection, paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, paramString, paramDate, getCumulativeLimits(paramConnection, paramEntitlementGroupMember));
  }
  
  public Limits checkLimitsEdit(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, float paramFloat1, float paramFloat2, Date paramDate1, Date paramDate2)
    throws EntitlementException
  {
    return checkLimitsEdit(paramEntitlementGroupMember, paramEntitlement, new BigDecimal(paramFloat1), new BigDecimal(paramFloat2), paramDate1, paramDate2);
  }
  
  public Limits checkLimitsEdit(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, Date paramDate1, Date paramDate2)
    throws EntitlementException
  {
    try
    {
      return checkLimitsEdit(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal1, paramBigDecimal2, this.jdField_try, paramDate1, paramDate2);
    }
    catch (NoClassDefFoundError localNoClassDefFoundError) {}
    return checkLimitsEdit(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal1, paramBigDecimal2, "USD", paramDate1, paramDate2);
  }
  
  public Limits checkLimitsEdit(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, String paramString, Date paramDate1, Date paramDate2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.checkLimitsEdit";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    jdField_if(paramEntitlement);
    return jdField_char.checkLimitsEdit(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal1, paramBigDecimal2, paramString, paramDate1, paramDate2, getCumulativeLimits(paramEntitlementGroupMember));
  }
  
  public Limits checkLimitsEdit(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, Date paramDate1, Date paramDate2)
    throws EntitlementException
  {
    try
    {
      return checkLimitsEdit(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal1, paramBigDecimal2, this.jdField_try, paramDate1, paramDate2);
    }
    catch (NoClassDefFoundError localNoClassDefFoundError) {}
    return checkLimitsEdit(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal1, paramBigDecimal2, "USD", paramDate1, paramDate2);
  }
  
  public Limits checkLimitsEdit(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, String paramString, Date paramDate1, Date paramDate2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.checkLimitsEdit";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    Entitlements localEntitlements = EntitlementUtil.getEntitlements(paramMultiEntitlement);
    for (int i = 0; i < localEntitlements.size(); i++) {
      jdField_if(localEntitlements.getEntitlement(i));
    }
    return jdField_char.checkLimitsEdit(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal1, paramBigDecimal2, paramString, paramDate1, paramDate2, getCumulativeLimits(paramEntitlementGroupMember));
  }
  
  public Limits checkLimitsEdit(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, float paramFloat1, float paramFloat2, Date paramDate1, Date paramDate2)
    throws EntitlementException
  {
    return checkLimitsEdit(paramConnection, paramEntitlementGroupMember, paramEntitlement, new BigDecimal(paramFloat1), new BigDecimal(paramFloat2), paramDate1, paramDate2);
  }
  
  public Limits checkLimitsEdit(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, Date paramDate1, Date paramDate2)
    throws EntitlementException
  {
    try
    {
      return checkLimitsEdit(paramConnection, paramEntitlementGroupMember, paramEntitlement, paramBigDecimal1, paramBigDecimal2, this.jdField_try, paramDate1, paramDate2);
    }
    catch (NoClassDefFoundError localNoClassDefFoundError) {}
    return checkLimitsEdit(paramConnection, paramEntitlementGroupMember, paramEntitlement, paramBigDecimal1, paramBigDecimal2, "USD", paramDate1, paramDate2);
  }
  
  public Limits checkLimitsEdit(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, String paramString, Date paramDate1, Date paramDate2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.checkLimitsEdit";
    if (paramEntitlementGroupMember.getEntitlementGroupId() == -1) {
      paramEntitlementGroupMember = getMember(paramConnection, paramEntitlementGroupMember);
    } else if (!verifyMemberExists(paramConnection, paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    return jdField_char.checkLimitsEdit(paramConnection, paramEntitlementGroupMember, paramEntitlement, paramBigDecimal1, paramBigDecimal2, paramString, paramDate1, paramDate2, getCumulativeLimits(paramConnection, paramEntitlementGroupMember));
  }
  
  public Limits checkLimitsEdit(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, Date paramDate1, Date paramDate2)
    throws EntitlementException
  {
    try
    {
      return checkLimitsEdit(paramConnection, paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal1, paramBigDecimal2, this.jdField_try, paramDate1, paramDate2);
    }
    catch (NoClassDefFoundError localNoClassDefFoundError) {}
    return checkLimitsEdit(paramConnection, paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal1, paramBigDecimal2, "USD", paramDate1, paramDate2);
  }
  
  public Limits checkLimitsEdit(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, String paramString, Date paramDate1, Date paramDate2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.checkLimitsEdit";
    if (paramEntitlementGroupMember.getEntitlementGroupId() == -1) {
      paramEntitlementGroupMember = getMember(paramConnection, paramEntitlementGroupMember);
    } else if (!verifyMemberExists(paramConnection, paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    return jdField_char.checkLimitsEdit(paramConnection, paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal1, paramBigDecimal2, paramString, paramDate1, paramDate2, getCumulativeLimits(paramConnection, paramEntitlementGroupMember));
  }
  
  public EntitlementGroups getGroupsAdministeredBy(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getGroupsAdministeredBy";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    return jdField_char.getGroupsAdministeredBy(paramInt);
  }
  
  public EntitlementGroups getGroupsAdministeredBy(int paramInt, String paramString)
    throws EntitlementException
  {
    String str = "EntitlementCahcedAdapter.getGroupsAdministeredBy";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    return jdField_char.getGroupsAdministeredBy(paramInt, paramString);
  }
  
  public EntitlementGroups getGroupsAdministeredBy(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getGroupsAdministeredBy";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    return jdField_char.getGroupsAdministeredBy(paramEntitlementGroupMember);
  }
  
  public EntitlementGroups getAdministratorsFor(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getAdministratorsFor";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    return jdField_char.getAdministratorsFor(paramInt);
  }
  
  public EntitlementAdmins getAdminsForGroup(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getAdminsForGroup";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    return jdField_char.getAdminInfoForTarget(paramInt);
  }
  
  public EntitlementAdmins getAdminInfoFor(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getAdminInfoFor";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    return jdField_char.getAdminInfoFor(paramEntitlementGroupMember);
  }
  
  public EntitlementAdmins getAdminInfoFor(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getAdminInfoFor";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    return jdField_char.getAdminInfoFor(paramInt);
  }
  
  public void addAdministratorGroup(EntitlementGroupMember paramEntitlementGroupMember, EntitlementAdmin paramEntitlementAdmin)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.addAdministratorGroup";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    a(paramEntitlementAdmin);
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember, paramEntitlementAdmin.getTargetGroupId());
    if (jdField_char.isAdministratorOf(localEntitlementAdmin)) {
      jdField_char.addAdministratorGroup(paramEntitlementAdmin);
    } else {
      throw new EntitlementException(str, 18);
    }
  }
  
  public void setAdministratorGroup(EntitlementGroupMember paramEntitlementGroupMember, int paramInt, EntitlementAdmins paramEntitlementAdmins)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.setAdministratorGroup";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember, paramInt);
    if (jdField_char.isAdministratorOf(localEntitlementAdmin)) {
      jdField_char.setAdministratorGroup(paramInt, paramEntitlementAdmins);
    } else {
      throw new EntitlementException(str, 18);
    }
  }
  
  public void deleteAdministratorGroup(EntitlementGroupMember paramEntitlementGroupMember, EntitlementAdmin paramEntitlementAdmin)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.deleteAdministratorGroup";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    a(paramEntitlementAdmin);
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember, paramEntitlementAdmin.getTargetGroupId());
    if (jdField_char.isAdministratorOf(localEntitlementAdmin)) {
      jdField_char.deleteAdministratorGroup(paramEntitlementAdmin);
    } else {
      throw new EntitlementException(str, 18);
    }
  }
  
  public void modifyAdministratorGroup(EntitlementGroupMember paramEntitlementGroupMember, EntitlementAdmin paramEntitlementAdmin1, EntitlementAdmin paramEntitlementAdmin2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.modifyAdministratorGroup";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    a(paramEntitlementAdmin1);
    a(paramEntitlementAdmin2);
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember, paramEntitlementAdmin1.getTargetGroupId());
    if (jdField_char.isAdministratorOf(localEntitlementAdmin)) {
      jdField_char.modifyAdministratorGroup(paramEntitlementAdmin1, paramEntitlementAdmin2);
    } else {
      throw new EntitlementException(str, 18);
    }
  }
  
  public boolean canAdminister(EntitlementAdmin paramEntitlementAdmin)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.canAdminister";
    a(paramEntitlementAdmin);
    return jdField_char.isAdministratorOf(paramEntitlementAdmin);
  }
  
  public boolean canAdministerAnyGroup(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.canAdministerAnyGroup";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    EntitlementGroups localEntitlementGroups = jdField_char.getGroupsAdministeredBy(paramEntitlementGroupMember);
    if (localEntitlementGroups.size() != 0) {
      return true;
    }
    localEntitlementGroups = jdField_char.getGroupsAdministeredBy(paramEntitlementGroupMember.getEntitlementGroupId());
    return localEntitlementGroups.size() != 0;
  }
  
  public boolean canAdministerAnyGroupOfType(EntitlementGroupMember paramEntitlementGroupMember, String paramString)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.canAdministerAnyGroupOfType";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    EntitlementGroups localEntitlementGroups = jdField_char.getGroupsAdministeredBy(paramEntitlementGroupMember);
    EntitlementGroup localEntitlementGroup;
    for (int i = 0; i < localEntitlementGroups.size(); i++)
    {
      localEntitlementGroup = (EntitlementGroup)localEntitlementGroups.get(i);
      if (localEntitlementGroup.getEntGroupType().equals(paramString)) {
        return true;
      }
    }
    localEntitlementGroups = jdField_char.getGroupsAdministeredBy(paramEntitlementGroupMember.getEntitlementGroupId());
    for (i = 0; i < localEntitlementGroups.size(); i++)
    {
      localEntitlementGroup = (EntitlementGroup)localEntitlementGroups.get(i);
      if (localEntitlementGroup.getEntGroupType().equals(paramString)) {
        return true;
      }
    }
    return false;
  }
  
  public boolean canExtend(EntitlementAdmin paramEntitlementAdmin)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.canExtend";
    a(paramEntitlementAdmin);
    return jdField_char.canExtend(paramEntitlementAdmin);
  }
  
  public boolean entitlementGroupExists(String paramString1, String paramString2, int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.entitlementGroupExists";
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    if (paramString2 == null) {
      throw new NullPointerException();
    }
    return jdField_char.entitlementGroupExists(paramString1, paramString2, paramInt);
  }
  
  public boolean entitlementTypeExists(String paramString)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.entitlementTypeExists";
    if (paramString == null) {
      throw new NullPointerException();
    }
    return jdField_char.entitlementTypeExists(paramString);
  }
  
  public boolean limitExists(Limit paramLimit)
    throws EntitlementException
  {
    return jdField_char.limitExists(paramLimit);
  }
  
  public void addMember(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.addMember";
    if (!verifyMemberExists(paramEntitlementGroupMember1)) {
      throw new EntitlementException(str, 5);
    }
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember1, paramEntitlementGroupMember2.getEntitlementGroupId());
    if (jdField_char.isAdministratorOf(localEntitlementAdmin)) {
      jdField_char.addMember(paramEntitlementGroupMember2);
    } else {
      throw new EntitlementException(str, 18);
    }
  }
  
  public void modifyMember(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.modifyMember";
    if (!verifyMemberExists(paramEntitlementGroupMember1)) {
      throw new EntitlementException(str, 5);
    }
    int i = paramEntitlementGroupMember2.getEntitlementGroupId();
    EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
    jdField_char.getMember(paramEntitlementGroupMember2);
    localEntitlementGroupMember.set(paramEntitlementGroupMember2);
    paramEntitlementGroupMember2.setEntitlementGroupId(i);
    EntitlementAdmin localEntitlementAdmin1 = new EntitlementAdmin(paramEntitlementGroupMember1, localEntitlementGroupMember.getEntitlementGroupId());
    EntitlementAdmin localEntitlementAdmin2 = new EntitlementAdmin(paramEntitlementGroupMember1, paramEntitlementGroupMember2.getEntitlementGroupId());
    if ((jdField_char.isAdministratorOf(localEntitlementAdmin1)) && (jdField_char.isAdministratorOf(localEntitlementAdmin2)))
    {
      jdField_char.modifyMember(paramEntitlementGroupMember2, localEntitlementGroupMember.getEntitlementGroupId());
      jdField_new.getWriteLock();
      try
      {
        jdField_if(localEntitlementGroupMember);
      }
      finally
      {
        jdField_new.releaseLock();
      }
    }
    else
    {
      throw new EntitlementException(str, 18);
    }
  }
  
  public EntitlementGroupMember getMember(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    return jdField_char.getMember(paramEntitlementGroupMember);
  }
  
  public EntitlementGroupMember getMember(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    return jdField_char.jdField_for(paramConnection, paramEntitlementGroupMember);
  }
  
  public EntitlementGroupMembers getMembers(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getMembers";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    return jdField_char.getMembers(paramInt);
  }
  
  public EntitlementGroupMembers getMembers(int paramInt, ConnectionHolder paramConnectionHolder)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getMembers";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    return jdField_char.getMembers(paramInt, paramConnectionHolder);
  }
  
  public int getNumMembers(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getNumMembers";
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    return jdField_char.getNumMembers(paramInt);
  }
  
  public void removeMember(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.removeMember";
    if (!verifyMemberExists(paramEntitlementGroupMember1)) {
      throw new EntitlementException(str, 5);
    }
    if (!verifyMemberExists(paramEntitlementGroupMember2)) {
      throw new EntitlementException(str, 5);
    }
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(paramEntitlementGroupMember1, paramEntitlementGroupMember2.getEntitlementGroupId());
    if (jdField_char.isAdministratorOf(localEntitlementAdmin))
    {
      jdField_new.getWriteLock();
      try
      {
        jdField_if(paramEntitlementGroupMember2);
        jdField_char.removeMember(paramEntitlementGroupMember2);
      }
      finally
      {
        jdField_new.releaseLock();
      }
    }
    else
    {
      throw new EntitlementException(str, 18);
    }
  }
  
  public boolean isObjectInUse(String paramString1, String paramString2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.isObjectInUse";
    if (!jdField_char.getObjectTypeMap(false).containsKey(paramString1)) {
      throw new EntitlementException(str, 21);
    }
    return jdField_char.isObjectInUse(paramString1, paramString2);
  }
  
  public ArrayList getEntitlementTypes(String paramString1, String paramString2)
    throws EntitlementException
  {
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    return jdField_char.getEntitlementTypes(paramString1, paramString2);
  }
  
  public ArrayList getEntitlementTypes(HashMap paramHashMap)
    throws EntitlementException
  {
    return jdField_char.getEntitlementTypes(paramHashMap);
  }
  
  public EntitlementTypePropertyList getEntitlementTypeWithProperties(String paramString)
    throws EntitlementException
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    return jdField_char.getEntitlementTypeWithProperties(paramString);
  }
  
  public EntitlementTypePropertyLists getEntitlementTypesWithProperties(String paramString1, String paramString2)
    throws EntitlementException
  {
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    return jdField_char.getEntitlementTypesWithProperties(paramString1, paramString2);
  }
  
  public EntitlementTypePropertyLists getEntitlementTypesWithProperties(HashMap paramHashMap)
    throws EntitlementException
  {
    return jdField_char.getEntitlementTypesWithProperties(paramHashMap);
  }
  
  public ArrayList getRestrictedEntitlementTypes(int paramInt, String paramString1, String paramString2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getRestrictedEntitlementTypes1";
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    return jdField_char.getRestrictedEntitlementTypes(paramInt, paramString1, paramString2);
  }
  
  public EntitlementTypePropertyLists getRestrictedEntitlementTypesWithProperties(int paramInt, String paramString1, String paramString2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getRestrictedEntitlementTypesWithProperties1";
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    return jdField_char.getRestrictedEntitlementTypesWithProperties(paramInt, paramString1, paramString2);
  }
  
  public ArrayList getRestrictedEntitlementTypes(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getRestrictedEntitlementTypes2";
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    return jdField_char.getRestrictedEntitlementTypes(paramEntitlementGroupMember, paramString1, paramString2);
  }
  
  public EntitlementTypePropertyLists getRestrictedEntitlementTypesWithProperties(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getRestrictedEntitlementTypesWithProperties2";
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    return jdField_char.getRestrictedEntitlementTypesWithProperties(paramEntitlementGroupMember, paramString1, paramString2);
  }
  
  public ArrayList getCumulativeEntitlementTypes(int paramInt, String paramString1, String paramString2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getCumulativeEntitlementTypes1";
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    return jdField_char.getCumulativeEntitlementTypes(paramInt, paramString1, paramString2);
  }
  
  public EntitlementTypePropertyLists getCumulativeEntitlementTypesWithProperties(int paramInt, String paramString1, String paramString2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getCumulativeEntitlementTypesWithProperties1";
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    return jdField_char.getCumulativeEntitlementTypesWithProperties(paramInt, paramString1, paramString2);
  }
  
  public ArrayList getCumulativeEntitlementTypes(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getCumulativeEntitlementTypes2";
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    return jdField_char.getCumulativeEntitlementTypes(paramEntitlementGroupMember, paramString1, paramString2);
  }
  
  public EntitlementTypePropertyLists getCumulativeEntitlementTypesWithProperties(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getCumulativeEntitlementTypesWithProperties2";
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    return jdField_char.getCumulativeEntitlementTypesWithProperties(paramEntitlementGroupMember, paramString1, paramString2);
  }
  
  public ArrayList getLimitTypes(String paramString1, String paramString2)
    throws EntitlementException
  {
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    return jdField_char.getLimitTypes(paramString1, paramString2);
  }
  
  public ArrayList getLimitTypes(HashMap paramHashMap)
    throws EntitlementException
  {
    return jdField_char.getLimitTypes(paramHashMap);
  }
  
  public LimitTypePropertyList getLimitTypeWithProperties(String paramString)
    throws EntitlementException
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    return jdField_char.getLimitTypePropertyList(paramString);
  }
  
  public LimitTypePropertyLists getLimitTypesWithProperties(String paramString1, String paramString2)
    throws EntitlementException
  {
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    return jdField_char.getLimitTypesWithProperties(paramString1, paramString2);
  }
  
  public LimitTypePropertyLists getLimitTypesWithProperties(HashMap paramHashMap)
    throws EntitlementException
  {
    return jdField_char.getLimitTypesWithProperties(paramHashMap);
  }
  
  public ObjectTypePropertyList getObjectTypeWithProperties(String paramString)
    throws EntitlementException
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    return jdField_char.getObjectTypePropertyList(paramString);
  }
  
  public ArrayList getGroupLimitTypes(int paramInt, String paramString1, String paramString2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getGroupLimitTypes1";
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    return jdField_char.getGroupLimitTypes(paramInt, paramString1, paramString2);
  }
  
  public LimitTypePropertyLists getGroupLimitTypesWithProperties(int paramInt, String paramString1, String paramString2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getGroupLimitTypesWithProperties1";
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    return jdField_char.getGroupLimitTypesWithProperties(paramInt, paramString1, paramString2);
  }
  
  public ArrayList getGroupLimitTypes(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getGroupLimitTypes2";
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    return jdField_char.getGroupLimitTypes(paramEntitlementGroupMember, paramString1, paramString2);
  }
  
  public LimitTypePropertyLists getGroupLimitTypesWithProperties(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getGroupLimitTypesWithProperties2";
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    return jdField_char.getGroupLimitTypesWithProperties(paramEntitlementGroupMember, paramString1, paramString2);
  }
  
  public ArrayList getCumulativeLimitTypes(int paramInt, String paramString1, String paramString2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getCumulativeLimitTypes1";
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    return jdField_char.getCumulativeLimitTypes(paramInt, paramString1, paramString2);
  }
  
  public LimitTypePropertyLists getCumulativeLimitTypesWithProperties(int paramInt, String paramString1, String paramString2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getCumulativeLimitTypesWithProperties1";
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    if (!jdField_int(paramInt)) {
      throw new EntitlementException(str, 3);
    }
    return jdField_char.getCumulativeLimitTypesWithProperties(paramInt, paramString1, paramString2);
  }
  
  public ArrayList getCumulativeLimitTypes(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getCumulativeLimitTypes2";
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    return jdField_char.getCumulativeLimitTypes(paramEntitlementGroupMember, paramString1, paramString2);
  }
  
  public LimitTypePropertyLists getCumulativeLimitTypesWithProperties(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.getCumulativeLimitTypesWithProperties2";
    if (paramString1 == null) {
      throw new NullPointerException();
    }
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str, 5);
    }
    return jdField_char.getCumulativeLimitTypesWithProperties(paramEntitlementGroupMember, paramString1, paramString2);
  }
  
  public Entitlements getPositiveEntitlements(EntitlementGroupMember paramEntitlementGroupMember, HashMap paramHashMap1, HashMap paramHashMap2)
    throws EntitlementException
  {
    String str1 = "EntitlementCachedAdapter.getPositiveEntitlements";
    if (!verifyMemberExists(paramEntitlementGroupMember)) {
      throw new EntitlementException(str1, 5);
    }
    Entitlements localEntitlements = new Entitlements();
    Iterator localIterator1 = paramHashMap1.keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str2 = (String)localIterator1.next();
      ArrayList localArrayList1 = (ArrayList)paramHashMap1.get(str2);
      Object localObject1;
      if ((localArrayList1 == null) || (localArrayList1.size() == 0))
      {
        if (str2.equals("")) {
          localObject1 = new Entitlement(null, null, null);
        } else {
          localObject1 = new Entitlement(str2, null, null);
        }
        a((Entitlement)localObject1);
        if (checkEntitlement(paramEntitlementGroupMember, (Entitlement)localObject1)) {
          localEntitlements.add(localObject1);
        }
      }
      else
      {
        localObject1 = localArrayList1.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          String str3 = (String)((Iterator)localObject1).next();
          ArrayList localArrayList2 = (ArrayList)paramHashMap2.get(str2.concat(str3));
          Object localObject2;
          if ((localArrayList2 == null) || (localArrayList2.size() == 0))
          {
            if (str2.equals("")) {
              localObject2 = new Entitlement(null, str3, null);
            } else {
              localObject2 = new Entitlement(str2, str3, null);
            }
            a((Entitlement)localObject2);
            if (checkEntitlement(paramEntitlementGroupMember, (Entitlement)localObject2)) {
              localEntitlements.add(localObject2);
            }
          }
          else
          {
            localObject2 = null;
            MultiEntitlement localMultiEntitlement = null;
            try
            {
              if (str3.equals("Account"))
              {
                localObject2 = DBUtil.getConnection(jdField_char.jdField_void, true, 2);
                localMultiEntitlement = new MultiEntitlement();
                localMultiEntitlement.setObjects(new String[] { "Account" }, new String[1]);
                localMultiEntitlement.setOperations(new String[] { str2.equals("") ? null : str2 });
              }
              Iterator localIterator2 = localArrayList2.iterator();
              while (localIterator2.hasNext())
              {
                String str4 = (String)localIterator2.next();
                Entitlement localEntitlement;
                if (str2.equals("")) {
                  localEntitlement = new Entitlement(null, str3, str4);
                } else {
                  localEntitlement = new Entitlement(str2, str3, str4);
                }
                a(localEntitlement);
                if (str3.equals("Account"))
                {
                  String[] arrayOfString = localMultiEntitlement.getObjectIds();
                  arrayOfString[0] = str4;
                  localMultiEntitlement.setObjects(localMultiEntitlement.getObjectTypes(), arrayOfString);
                  if (EntitlementsUtil.checkAccountEntitlement((Connection)localObject2, this, paramEntitlementGroupMember, localMultiEntitlement, paramEntitlementGroupMember.getBusinessID()) == null) {
                    localEntitlements.add(localEntitlement);
                  }
                }
                else if (checkEntitlement(paramEntitlementGroupMember, localEntitlement))
                {
                  localEntitlements.add(localEntitlement);
                }
              }
            }
            catch (PoolException localPoolException)
            {
              throw new EntitlementException(localPoolException, str1, 2);
            }
            finally
            {
              DBUtil.returnConnection(jdField_char.jdField_void, (Connection)localObject2);
              localObject2 = null;
              localMultiEntitlement = null;
            }
          }
        }
      }
    }
    return localEntitlements;
  }
  
  public void cleanup(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws EntitlementException
  {
    jdField_char.cleanup(paramInt1, paramInt2, paramHashMap);
  }
  
  public int cleanupCacheChanges(long paramLong)
    throws EntitlementException
  {
    return jdField_char.cleanupCacheChanges(paramLong);
  }
  
  private void a(Entitlement paramEntitlement)
    throws EntitlementException
  {
    String str1 = paramEntitlement.getOperationName();
    String str2 = paramEntitlement.getObjectType();
    String str3 = paramEntitlement.getObjectId();
    if ((str2 == null) && ((str3 != null) || (str1 == null))) {
      throw new EntitlementException(16);
    }
    HashMap localHashMap;
    if (str1 != null)
    {
      localHashMap = jdField_char.getEntitlementMap(false);
      if (!localHashMap.containsKey(str1)) {
        throw new EntitlementException(16);
      }
    }
    if (str2 != null)
    {
      localHashMap = jdField_char.getObjectTypeMap(false);
      if (!localHashMap.containsKey(str2)) {
        throw new EntitlementException(16);
      }
    }
  }
  
  private void jdField_if(Entitlement paramEntitlement)
    throws EntitlementException
  {
    String str1 = paramEntitlement.getOperationName();
    String str2 = paramEntitlement.getObjectType();
    String str3 = paramEntitlement.getObjectId();
    if ((str2 == null) && ((str3 != null) || (str1 == null))) {
      throw new EntitlementException(16);
    }
    HashMap localHashMap;
    if (str1 != null)
    {
      localHashMap = jdField_char.getLimitTypeMap(false);
      if (!localHashMap.containsKey(str1)) {
        throw new EntitlementException(16);
      }
    }
    if (str2 != null)
    {
      localHashMap = jdField_char.getObjectTypeMap(false);
      if (!localHashMap.containsKey(str2)) {
        throw new EntitlementException(16);
      }
    }
  }
  
  private boolean a(Entitlement paramEntitlement1, Entitlement paramEntitlement2)
    throws EntitlementException
  {
    int i = 0;
    if (paramEntitlement2.getOperationName() != null) {
      i += 4;
    }
    if (paramEntitlement2.getObjectType() != null) {
      i += 2;
    }
    if (paramEntitlement2.getObjectId() != null) {
      i++;
    }
    switch (i)
    {
    case 0: 
      return false;
    case 2: 
      return !paramEntitlement2.getObjectType().equals(paramEntitlement1.getObjectType());
    case 3: 
      return (!paramEntitlement2.getObjectType().equals(paramEntitlement1.getObjectType())) || (!paramEntitlement2.getObjectId().equals(paramEntitlement1.getObjectId()));
    case 4: 
      return !paramEntitlement2.getOperationName().equals(paramEntitlement1.getOperationName());
    case 6: 
      return (!paramEntitlement2.getOperationName().equals(paramEntitlement1.getOperationName())) || (!paramEntitlement2.getObjectType().equals(paramEntitlement1.getObjectType()));
    case 7: 
      return (!paramEntitlement2.getOperationName().equals(paramEntitlement1.getOperationName())) || (!paramEntitlement2.getObjectType().equals(paramEntitlement1.getObjectType())) || (!paramEntitlement2.getObjectId().equals(paramEntitlement1.getObjectId()));
    }
    throw new EntitlementException(16);
  }
  
  private boolean jdField_int(int paramInt)
  {
    if (jdField_if.isGroupInCache(paramInt)) {
      return true;
    }
    try
    {
      jdField_char.getEntitlementGroup(paramInt);
    }
    catch (Exception localException)
    {
      return false;
    }
    return true;
  }
  
  private boolean a(Connection paramConnection, int paramInt)
  {
    if (jdField_if.isGroupInCache(paramInt)) {
      return true;
    }
    try
    {
      jdField_char.jdField_byte(paramConnection, paramInt);
    }
    catch (Exception localException)
    {
      return false;
    }
    return true;
  }
  
  public boolean verifyMemberExists(EntitlementGroupMember paramEntitlementGroupMember)
  {
    if (paramEntitlementGroupMember == null) {
      throw new NullPointerException();
    }
    if (jdField_if.isGroupMemberInCache(paramEntitlementGroupMember)) {
      return true;
    }
    int i = paramEntitlementGroupMember.getEntitlementGroupId();
    try
    {
      paramEntitlementGroupMember = jdField_char.getMember(paramEntitlementGroupMember);
      if (paramEntitlementGroupMember.getEntitlementGroupId() != i)
      {
        boolean bool1 = false;
        return bool1;
      }
    }
    catch (Exception localException)
    {
      boolean bool2 = false;
      return bool2;
    }
    finally
    {
      paramEntitlementGroupMember.setEntitlementGroupId(i);
    }
    return true;
  }
  
  public boolean verifyMemberExists(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember)
  {
    if (paramEntitlementGroupMember == null) {
      throw new NullPointerException();
    }
    if (jdField_if.isGroupMemberInCache(paramEntitlementGroupMember)) {
      return true;
    }
    int i = paramEntitlementGroupMember.getEntitlementGroupId();
    try
    {
      paramEntitlementGroupMember = jdField_char.jdField_for(paramConnection, paramEntitlementGroupMember);
      if (paramEntitlementGroupMember.getEntitlementGroupId() != i)
      {
        boolean bool1 = false;
        return bool1;
      }
    }
    catch (Exception localException)
    {
      boolean bool2 = false;
      return bool2;
    }
    finally
    {
      paramEntitlementGroupMember.setEntitlementGroupId(i);
    }
    return true;
  }
  
  private void a(EntitlementAdmin paramEntitlementAdmin)
    throws EntitlementException
  {
    String str = "EntitlementCachedAdapter.verifyAdminExists";
    if (!jdField_int(paramEntitlementAdmin.getGranteeGroupId())) {
      throw new EntitlementException(str, 22);
    }
    if ((paramEntitlementAdmin.getGranteeMemberType() != null) || (paramEntitlementAdmin.getGranteeMemberSubType() != null) || (paramEntitlementAdmin.getGranteeMemberId() != null))
    {
      EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
      localEntitlementGroupMember.setMemberType(paramEntitlementAdmin.getGranteeMemberType());
      localEntitlementGroupMember.setMemberSubType(paramEntitlementAdmin.getGranteeMemberSubType());
      localEntitlementGroupMember.setId(paramEntitlementAdmin.getGranteeMemberId());
      localEntitlementGroupMember.setEntitlementGroupId(paramEntitlementAdmin.getGranteeGroupId());
      if (!verifyMemberExists(localEntitlementGroupMember)) {
        throw new EntitlementException(str, 23);
      }
    }
    if (!jdField_int(paramEntitlementAdmin.getTargetGroupId())) {
      throw new EntitlementException(str, 24);
    }
  }
  
  private Limits a(Limits paramLimits1, Limits paramLimits2, Object paramObject)
    throws EntitlementException
  {
    Limits localLimits = new Limits();
    localLimits.addAll(paramLimits2);
    CompressedLimitsHelper localCompressedLimitsHelper = new CompressedLimitsHelper(this.jdField_else, jdField_char.getLimitTypeMap(false), jdField_char.getObjectTypeMap(false), this);
    int i = 0;
    String str = "0";
    Object localObject1;
    Object localObject2;
    if ((paramObject instanceof EntitlementGroup))
    {
      localObject1 = ((EntitlementGroup)paramObject).getEntGroupType();
      if ("ServicesPackage".equals(localObject1))
      {
        i = 2;
        str = String.valueOf(((EntitlementGroup)paramObject).getGroupId());
      }
      else if ("Business".equals(localObject1))
      {
        i = 3;
        str = String.valueOf(((EntitlementGroup)paramObject).getGroupId());
      }
      else if ("Division".equals(localObject1))
      {
        i = 3;
        str = String.valueOf(((EntitlementGroup)paramObject).getParentId());
      }
      else if ("Group".equals(localObject1))
      {
        i = 3;
        localObject2 = jdField_char.getEntitlementGroup(((EntitlementGroup)paramObject).getParentId());
        str = String.valueOf(((EntitlementGroup)localObject2).getParentId());
      }
    }
    else if ((paramObject instanceof EntitlementGroupMember))
    {
      localObject1 = jdField_char.getEntitlementGroup(((EntitlementGroupMember)paramObject).getEntitlementGroupId());
      localObject2 = ((EntitlementGroup)localObject1).getEntGroupType();
      if (("Business".equals(localObject2)) || ("Division".equals(localObject2)) || ("Group".equals(localObject2)) || ("USER".equals(localObject2)))
      {
        i = 4;
        str = ((EntitlementGroupMember)paramObject).getId();
      }
    }
    for (int j = 0; j < paramLimits2.size(); j++)
    {
      localObject2 = (Limit)paramLimits2.get(j);
      localCompressedLimitsHelper.getWhatToDo((Limit)localObject2, i, str);
    }
    for (j = 0; j < paramLimits1.size(); j++)
    {
      localObject2 = (Limit)paramLimits1.get(j);
      int k = localCompressedLimitsHelper.getWhatToDo((Limit)localObject2, i, str);
      if (k == 2)
      {
        localLimits.add(localObject2);
      }
      else if (k != 1)
      {
        Iterator localIterator = localLimits.iterator();
        while (localIterator.hasNext())
        {
          Limit localLimit = (Limit)localIterator.next();
          if ((((Limit)localObject2).getEntitlement().equals(localLimit.getEntitlement())) && (((Limit)localObject2).getPeriod() == localLimit.getPeriod()))
          {
            localIterator.remove();
            break;
          }
        }
        localLimits.add(localObject2);
      }
    }
    return localLimits;
  }
  
  private Limits a(Limits paramLimits, Object paramObject)
    throws EntitlementException
  {
    Limits localLimits = new Limits();
    CompressedLimitsHelper localCompressedLimitsHelper = new CompressedLimitsHelper(this.jdField_else, jdField_char.getLimitTypeMap(false), jdField_char.getObjectTypeMap(false), this);
    int i = 0;
    String str = "0";
    Object localObject1;
    Object localObject2;
    if ((paramObject instanceof EntitlementGroup))
    {
      localObject1 = ((EntitlementGroup)paramObject).getEntGroupType();
      if ("ServicesPackage".equals(localObject1))
      {
        i = 2;
        str = String.valueOf(((EntitlementGroup)paramObject).getGroupId());
      }
      else if ("Business".equals(localObject1))
      {
        i = 3;
        str = String.valueOf(((EntitlementGroup)paramObject).getGroupId());
      }
      else if ("Division".equals(localObject1))
      {
        i = 3;
        str = String.valueOf(((EntitlementGroup)paramObject).getParentId());
      }
      else if ("Group".equals(localObject1))
      {
        i = 3;
        localObject2 = getEntitlementGroup(((EntitlementGroup)paramObject).getParentId());
        str = String.valueOf(((EntitlementGroup)localObject2).getParentId());
      }
    }
    else if ((paramObject instanceof EntitlementGroupMember))
    {
      localObject1 = getEntitlementGroup(((EntitlementGroupMember)paramObject).getEntitlementGroupId());
      localObject2 = ((EntitlementGroup)localObject1).getEntGroupType();
      if (("Business".equals(localObject2)) || ("Division".equals(localObject2)) || ("Group".equals(localObject2)) || ("USER".equals(localObject2)))
      {
        i = 4;
        str = ((EntitlementGroupMember)paramObject).getId();
      }
    }
    for (int j = 0; j < paramLimits.size(); j++)
    {
      localObject2 = (Limit)paramLimits.get(j);
      int k = localCompressedLimitsHelper.getWhatToDo((Limit)localObject2, i, str);
      if (k == 2)
      {
        localLimits.add(localObject2);
      }
      else if (k != 1)
      {
        Iterator localIterator = localLimits.iterator();
        while (localIterator.hasNext())
        {
          Limit localLimit = (Limit)localIterator.next();
          if ((((Limit)localObject2).getEntitlement().equals(localLimit.getEntitlement())) && (((Limit)localObject2).getPeriod() == localLimit.getPeriod()))
          {
            localIterator.remove();
            break;
          }
        }
        localLimits.add(localObject2);
      }
    }
    return localLimits;
  }
  
  private void a(EntitlementGroupMember paramEntitlementGroupMember, Limits paramLimits)
    throws EntitlementException
  {
    jdField_new.getReadLock();
    try
    {
      jdField_if(paramEntitlementGroupMember, paramLimits);
    }
    finally
    {
      jdField_new.releaseLock();
    }
  }
  
  private void jdField_if(EntitlementGroupMember paramEntitlementGroupMember, Limits paramLimits)
    throws EntitlementException
  {
    Iterator localIterator = paramLimits.iterator();
    while (localIterator.hasNext()) {
      try
      {
        if (!a(paramEntitlementGroupMember, ((Limit)localIterator.next()).getEntitlement())) {
          localIterator.remove();
        }
      }
      catch (EntitlementException localEntitlementException)
      {
        if (localEntitlementException.code != 16) {
          throw new EntitlementException(localEntitlementException);
        }
      }
    }
  }
  
  private void jdField_if(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, Limits paramLimits)
    throws EntitlementException
  {
    jdField_new.getReadLock();
    try
    {
      a(paramConnection, paramEntitlementGroupMember, paramLimits);
    }
    finally
    {
      jdField_new.releaseLock();
    }
  }
  
  private void a(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, Limits paramLimits)
    throws EntitlementException
  {
    Iterator localIterator = paramLimits.iterator();
    while (localIterator.hasNext()) {
      try
      {
        if (!jdField_if(paramConnection, paramEntitlementGroupMember, ((Limit)localIterator.next()).getEntitlement())) {
          localIterator.remove();
        }
      }
      catch (EntitlementException localEntitlementException)
      {
        if (localEntitlementException.code != 16) {
          throw new EntitlementException(localEntitlementException);
        }
      }
    }
  }
  
  private void a(int paramInt, Limits paramLimits)
    throws EntitlementException
  {
    jdField_new.getReadLock();
    try
    {
      jdField_if(paramInt, paramLimits);
    }
    finally
    {
      jdField_new.releaseLock();
    }
  }
  
  private void jdField_if(int paramInt, Limits paramLimits)
    throws EntitlementException
  {
    Iterator localIterator = paramLimits.iterator();
    while (localIterator.hasNext()) {
      try
      {
        if (!jdField_if(paramInt, ((Limit)localIterator.next()).getEntitlement())) {
          localIterator.remove();
        }
      }
      catch (EntitlementException localEntitlementException)
      {
        if (localEntitlementException.code != 16) {
          throw new EntitlementException(localEntitlementException);
        }
      }
    }
  }
  
  private void jdField_if(Connection paramConnection, int paramInt, Limits paramLimits)
    throws EntitlementException
  {
    jdField_new.getReadLock();
    try
    {
      a(paramConnection, paramInt, paramLimits);
    }
    finally
    {
      jdField_new.releaseLock();
    }
  }
  
  private void a(Connection paramConnection, int paramInt, Limits paramLimits)
    throws EntitlementException
  {
    Iterator localIterator = paramLimits.iterator();
    while (localIterator.hasNext()) {
      try
      {
        if (!jdField_if(paramConnection, paramInt, ((Limit)localIterator.next()).getEntitlement())) {
          localIterator.remove();
        }
      }
      catch (EntitlementException localEntitlementException)
      {
        if (localEntitlementException.code != 16) {
          throw new EntitlementException(localEntitlementException);
        }
      }
    }
  }
  
  private void jdField_do(int paramInt, Limits paramLimits)
    throws EntitlementException
  {
    Iterator localIterator = paramLimits.iterator();
    while (localIterator.hasNext()) {
      try
      {
        if (!a(paramInt, ((Limit)localIterator.next()).getEntitlement())) {
          localIterator.remove();
        }
      }
      catch (EntitlementException localEntitlementException)
      {
        if (localEntitlementException.code != 16) {
          throw new EntitlementException(localEntitlementException);
        }
      }
    }
  }
  
  private void jdField_do(int paramInt)
    throws EntitlementException
  {
    ArrayList localArrayList1 = jdField_if.getChildGroups(paramInt);
    if (localArrayList1 != null)
    {
      localObject = localArrayList1.iterator();
      while (((Iterator)localObject).hasNext()) {
        jdField_do(Integer.parseInt((String)((Iterator)localObject).next()));
      }
    }
    Object localObject = jdField_if.getGroupMembers(paramInt);
    if (localObject != null) {
      for (int i = ((ArrayList)localObject).size() - 1; i >= 0; i--) {
        jdField_if((EntitlementGroupMember)((ArrayList)localObject).get(i));
      }
    }
    EntitlementGroup localEntitlementGroup = jdField_if.getEntitlementGroup(paramInt);
    if (localEntitlementGroup != null)
    {
      jdField_if.deleteEntitlementGroup(localEntitlementGroup.getGroupId());
      int j = localEntitlementGroup.getParentId();
      boolean bool = jdField_if.isGroupInCache(j);
      if (bool)
      {
        ArrayList localArrayList2 = jdField_if.getChildGroups(j);
        localArrayList2.remove(Integer.toString(j));
      }
    }
  }
  
  private void jdField_if(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    jdField_if.deleteEntitlementGroupMember(paramEntitlementGroupMember);
    int i = paramEntitlementGroupMember.getEntitlementGroupId();
    boolean bool = jdField_if.isGroupInCache(i);
    if (bool)
    {
      ArrayList localArrayList = jdField_if.getGroupMembers(i);
      localArrayList.remove(paramEntitlementGroupMember);
    }
  }
  
  public void acquireWriteLock()
  {
    jdField_new.getWriteLock();
  }
  
  public void releaseWriteLock()
    throws EntitlementException
  {
    jdField_if = new EntitlementCacheManager(jdField_char.getEntitlementMap(false), jdField_char.getObjectTypeMap(false), jdField_case, false);
    jdField_new.releaseLock();
  }
  
  private void jdField_if(int paramInt)
    throws EntitlementException
  {
    ArrayList localArrayList1 = jdField_if.getChildGroups(paramInt);
    if (localArrayList1 != null)
    {
      localObject = localArrayList1.iterator();
      while (((Iterator)localObject).hasNext()) {
        jdField_if(Integer.parseInt((String)((Iterator)localObject).next()));
      }
    }
    Object localObject = jdField_if.getGroupMembers(paramInt);
    if (localObject != null) {
      for (int i = ((ArrayList)localObject).size() - 1; i >= 0; i--) {
        jdField_do((EntitlementGroupMember)((ArrayList)localObject).get(i));
      }
    }
    EntitlementGroup localEntitlementGroup = jdField_if.getEntitlementGroup(paramInt);
    if (localEntitlementGroup != null)
    {
      jdField_if.deleteEntitlementGroup(localEntitlementGroup.getGroupId());
      int j = localEntitlementGroup.getParentId();
      boolean bool = jdField_if.isGroupInCache(j);
      if (bool)
      {
        ArrayList localArrayList2 = jdField_if.getChildGroups(j);
        localArrayList2.remove(Integer.toString(j));
      }
    }
    jdField_char.cleanupCache(paramInt);
  }
  
  private void jdField_do(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    jdField_if(paramEntitlementGroupMember);
    jdField_char.cleanupCache(paramEntitlementGroupMember);
  }
  
  public void setFXService(IForeignExchangeService paramIForeignExchangeService)
  {
    this.jdField_else = paramIForeignExchangeService;
    jdField_char.setFXService(paramIForeignExchangeService);
  }
  
  private class a
    extends Level
  {
    public a()
    {
      super(150);
    }
  }
  
  class b
    extends Thread
  {
    private int jdField_try;
    private long jdField_for = -1L;
    private long jdField_if = -1L;
    private boolean jdField_do;
    private boolean jdField_char;
    private Connection jdField_goto;
    private String jdField_byte;
    private PreparedStatement jdField_int;
    private PreparedStatement jdField_new;
    private int jdField_case;
    private HashSet jdField_else = new HashSet();
    private Level a = new EntitlementCachedAdapter.a(EntitlementCachedAdapter.this);
    
    public b(int paramInt)
    {
      this.jdField_try = paramInt;
      this.jdField_do = true;
      this.jdField_char = false;
      try
      {
        this.jdField_goto = DBUtil.getConnection(EntitlementCachedAdapter.jdField_char.jdField_void, true, 2, true);
        this.jdField_byte = ConnectionPool.getDBType(EntitlementCachedAdapter.jdField_char.jdField_void);
        this.jdField_int = DBUtil.prepareStatement(this.jdField_goto, "SELECT group_id, change_time, member_type, member_subtype, member_id FROM ent_cache_changes WHERE change_maker <> ? AND change_time > ? AND change_time <= ?", true);
        this.jdField_new = DBUtil.prepareStatement(this.jdField_goto, EntitlementCachedAdapter.jdField_char.jdField_for(), true);
        if (!"ASE".equalsIgnoreCase(this.jdField_byte))
        {
          DatabaseMetaData localDatabaseMetaData = this.jdField_goto.getMetaData();
          if (localDatabaseMetaData.supportsTransactionIsolationLevel(1)) {
            this.jdField_goto.setTransactionIsolation(1);
          }
        }
        this.jdField_if = (EntitlementCachedAdapter.jdField_char.a(this.jdField_goto, this.jdField_new).getTime() - 1L);
        this.jdField_for = this.jdField_if;
      }
      catch (Exception localException)
      {
        DebugLog.log(Level.WARNING, "Entitlements - Can not connect to database: " + localException.toString());
        this.jdField_goto = null;
        this.jdField_int = null;
        this.jdField_new = null;
        this.jdField_case += 1;
      }
      start();
    }
    
    public void run()
    {
      long l1 = this.jdField_try;
      long l3 = 0L;
      int i = 0;
      while (!this.jdField_char)
      {
        try
        {
          if (l1 > 0L) {
            sleep(l1);
          }
        }
        catch (Exception localException1) {}
        long l2 = System.currentTimeMillis();
        try
        {
          if (this.jdField_goto == null)
          {
            this.jdField_goto = DBUtil.getConnection(EntitlementCachedAdapter.jdField_char.jdField_void, true, 2, true);
            this.jdField_byte = ConnectionPool.getDBType(EntitlementCachedAdapter.jdField_char.jdField_void);
            this.jdField_int = DBUtil.prepareStatement(this.jdField_goto, "SELECT group_id, change_time, member_type, member_subtype, member_id FROM ent_cache_changes WHERE change_maker <> ? AND change_time > ? AND change_time <= ?", true);
            this.jdField_new = DBUtil.prepareStatement(this.jdField_goto, EntitlementCachedAdapter.jdField_char.jdField_for(), true);
            if (!"ASE".equalsIgnoreCase(this.jdField_byte))
            {
              localObject1 = this.jdField_goto.getMetaData();
              if (((DatabaseMetaData)localObject1).supportsTransactionIsolationLevel(1)) {
                this.jdField_goto.setTransactionIsolation(1);
              }
            }
            if (this.jdField_if == -1L)
            {
              this.jdField_if = (EntitlementCachedAdapter.jdField_char.a(this.jdField_goto, this.jdField_new).getTime() - 1L);
              this.jdField_for = this.jdField_if;
            }
            DebugLog.log(Level.INFO, "Entitlements - Reaper thread recovered database connection.");
          }
          l3 = EntitlementCachedAdapter.jdField_char.a(this.jdField_goto, this.jdField_new).getTime() - 1L;
          Object localObject1 = EntitlementCachedAdapter.jdField_char.getItemsModifiedSince(this.jdField_goto, this.jdField_int, this.jdField_for, l3);
          HashSet localHashSet = null;
          if (!((HashSet)localObject1).isEmpty())
          {
            localHashSet = new HashSet();
            Iterator localIterator1 = ((HashSet)localObject1).iterator();
            Object localObject2 = null;
            while (localIterator1.hasNext())
            {
              localObject2 = localIterator1.next();
              if ((this.jdField_else == null) || (!this.jdField_else.contains(localObject2))) {
                localHashSet.add(localObject2);
              }
            }
            if (!localHashSet.isEmpty())
            {
              EntitlementCachedAdapter.jdField_new.getWriteLock();
              try
              {
                Iterator localIterator2 = localHashSet.iterator();
                Object localObject3 = null;
                while (localIterator2.hasNext())
                {
                  localObject3 = ((ItemChange)localIterator2.next()).item;
                  if ((localObject3 instanceof Integer)) {
                    EntitlementCachedAdapter.this.jdField_if(((Integer)localObject3).intValue());
                  } else {
                    EntitlementCachedAdapter.this.jdField_do((EntitlementGroupMember)localObject3);
                  }
                }
              }
              finally
              {
                EntitlementCachedAdapter.jdField_new.releaseLock();
              }
            }
          }
          this.jdField_for = this.jdField_if;
          this.jdField_if = l3;
          this.jdField_else = localHashSet;
          this.jdField_case = 0;
        }
        catch (Exception localException2)
        {
          if (this.jdField_case % 60 == 0) {
            DebugLog.log(Level.WARNING, "Entitlements - Reaper thread could not connect to database: " + localException2.toString());
          }
          try
          {
            DBUtil.closeStatement(this.jdField_new);
            DBUtil.closeAll(EntitlementCachedAdapter.jdField_char.jdField_void, this.jdField_goto, this.jdField_int);
          }
          catch (Exception localException4) {}
          this.jdField_goto = null;
          this.jdField_int = null;
          this.jdField_new = null;
          this.jdField_case += 1;
        }
        l1 = this.jdField_try - (System.currentTimeMillis() - l2);
        DebugLog.log(this.a, "Entitlements - Reaper thread ran at " + l3 + " and took " + (this.jdField_try - l1) + " milliseconds.");
        if (l1 <= 0L) {
          l1 = 0L;
        }
      }
      try
      {
        DBUtil.closeStatement(this.jdField_new);
        DBUtil.closeAll(EntitlementCachedAdapter.jdField_char.jdField_void, this.jdField_goto, this.jdField_int);
      }
      catch (Exception localException3) {}
      this.jdField_goto = null;
      this.jdField_int = null;
      this.jdField_new = null;
      this.jdField_do = false;
    }
    
    public void a()
    {
      this.jdField_char = true;
      interrupt();
      while (this.jdField_do) {
        try
        {
          Thread.sleep(50L);
        }
        catch (Exception localException) {}
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.efs.adapters.entitlements.EntitlementCachedAdapter
 * JD-Core Version:    0.7.0.1
 */