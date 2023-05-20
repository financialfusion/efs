package com.ffusion.alert.db;

import com.ffusion.alert.interfaces.AEApplicationInfo;
import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.shared.AEIntMap;
import java.sql.SQLException;
import java.text.CollationKey;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

public class AppInfoCache
  implements IAEErrConstants
{
  private static final String d9 = "name,password";
  private static final String ea = "insert into AE_Application({|||id,|}name,password) values({|||AE_Seq.nextval,|}?,?)";
  private static final String d7 = "update AE_Application set name=?, password=? where id=?";
  private static final String d6 = "delete from AE_Application where password is null and id not in( select appId from AE_Channel ) and id not in( select appId from AE_Alert )";
  private static final String ec = "select id,name,password from AE_Application";
  private static final String d5 = "select id,name,password from AE_Application where name = ?";
  private static final String d3 = "select id,name,password from AE_Application where id = ?";
  private HashMap d2 = new HashMap();
  private AEIntMap d4 = new AEIntMap();
  private static final a ed = new a(null);
  public static final String eb = "Universal Alert Engine";
  public static final String d8 = "UAE Admin Channel";
  
  public synchronized int a(AERepository paramAERepository, DBConnection paramDBConnection, AEApplicationInfo paramAEApplicationInfo, boolean paramBoolean)
    throws AEException
  {
    boolean bool = false;
    try
    {
      if (this.d2.containsKey(paramAEApplicationInfo.getName()))
      {
        b localb = (b)this.d2.get(paramAEApplicationInfo.getName());
        if (localb.jdField_if.getPassword() == null) {
          throw new AEException(13, AERepository.H("ERR_INVALID_NAME_OR_PASS"));
        }
        throw new AEException(14, AERepository.H("ERR_NAME_EXISTS"));
      }
      bool = paramDBConnection.aU();
      if (bool) {
        paramDBConnection.jdMethod_try(false);
      }
      Object[] arrayOfObject = { paramAEApplicationInfo.getName(), paramAEApplicationInfo.getPassword() };
      paramDBConnection.jdMethod_if("insert into AE_Application({|||id,|}name,password) values({|||AE_Seq.nextval,|}?,?)", arrayOfObject);
      int i = paramAERepository.h(paramDBConnection);
      if (paramBoolean) {
        a(i, paramAEApplicationInfo);
      }
      if (bool) {
        paramDBConnection.a0();
      }
      int j = i;
      return j;
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
  }
  
  public synchronized boolean a(DBConnection paramDBConnection, String paramString, AEApplicationInfo paramAEApplicationInfo, boolean paramBoolean1, boolean paramBoolean2)
    throws AEException
  {
    try
    {
      b localb = (b)this.d2.get(paramString);
      if (localb == null)
      {
        if (!paramBoolean1) {
          throw new AEException(13, AERepository.H("ERR_INVALID_NAME_OR_PASS"));
        }
        localb = (b)this.d2.get(paramAEApplicationInfo.getName());
        if (localb == null) {
          throw new AEException(13, AERepository.H("ERR_INVALID_NAME_OR_PASS"));
        }
        return false;
      }
      Object[] arrayOfObject = { paramAEApplicationInfo.getName() };
      DBResultSet localDBResultSet = paramDBConnection.I("select id,name,password from AE_Application where name = ?");
      localDBResultSet.a(arrayOfObject);
      Object localObject;
      AEApplicationInfo localAEApplicationInfo;
      for (boolean bool = false; localDBResultSet.a(); bool = a(paramAEApplicationInfo, localAEApplicationInfo))
      {
        localObject = localDBResultSet.jdMethod_try(2);
        String str = localDBResultSet.jdMethod_try(3);
        localAEApplicationInfo = new AEApplicationInfo((String)localObject, str);
      }
      if (!bool)
      {
        localObject = new Object[] { paramAEApplicationInfo.getName(), paramAEApplicationInfo.getPassword(), new Integer(localb.a) };
        paramDBConnection.jdMethod_if("update AE_Application set name=?, password=? where id=?", (Object[])localObject);
      }
      if (paramBoolean2) {
        a(localb, paramString, paramAEApplicationInfo);
      }
      return !bool;
    }
    catch (SQLException localSQLException)
    {
      throw new AEException(1, DBSqlUtils.a(localSQLException));
    }
  }
  
  private static boolean a(AEApplicationInfo paramAEApplicationInfo1, AEApplicationInfo paramAEApplicationInfo2)
  {
    if ((paramAEApplicationInfo1 == null) || (paramAEApplicationInfo2 == null)) {
      return false;
    }
    if ((paramAEApplicationInfo1.getPassword() == null) && (paramAEApplicationInfo2.getPassword() != null)) {
      return false;
    }
    if ((paramAEApplicationInfo2.getPassword() == null) && (paramAEApplicationInfo1.getPassword() != null)) {
      return false;
    }
    if (paramAEApplicationInfo1.getName().equals(paramAEApplicationInfo2.getName()))
    {
      if ((paramAEApplicationInfo1.getPassword() == null) && (paramAEApplicationInfo2.getPassword() == null)) {
        return true;
      }
      if (paramAEApplicationInfo1.getPassword().equals(paramAEApplicationInfo2.getPassword())) {
        return true;
      }
    }
    return false;
  }
  
  public synchronized boolean jdMethod_if(DBConnection paramDBConnection, String paramString, boolean paramBoolean)
    throws AEException
  {
    b localb = (b)this.d2.get(paramString);
    if (localb == null) {
      throw new AEException(13, AERepository.H("ERR_INVALID_NAME_OR_PASS"));
    }
    if ((!paramBoolean) && (localb.jdField_if.getPassword() == null)) {
      throw new AEException(13, AERepository.H("ERR_INVALID_NAME_OR_PASS"));
    }
    AEApplicationInfo localAEApplicationInfo = new AEApplicationInfo(paramString, null);
    return a(paramDBConnection, paramString, localAEApplicationInfo, paramBoolean, true);
  }
  
  public synchronized void jdMethod_byte(DBConnection paramDBConnection)
    throws AEException
  {
    try
    {
      int i = paramDBConnection.K("delete from AE_Application where password is null and id not in( select appId from AE_Channel ) and id not in( select appId from AE_Alert )");
      jdMethod_case(paramDBConnection);
    }
    catch (SQLException localSQLException)
    {
      throw new AEException(1, DBSqlUtils.a(localSQLException));
    }
  }
  
  public synchronized void jdMethod_case(DBConnection paramDBConnection)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      this.d2.clear();
      this.d4.jdMethod_if();
      localDBResultSet = paramDBConnection.I("select id,name,password from AE_Application");
      AEApplicationInfo localAEApplicationInfo = null;
      localDBResultSet.jdMethod_try();
      while (localDBResultSet.a())
      {
        int i = localDBResultSet.jdMethod_int(1);
        String str1 = localDBResultSet.jdMethod_try(2);
        String str2 = localDBResultSet.jdMethod_try(3);
        localAEApplicationInfo = new AEApplicationInfo(str1, str2);
        a(i, localAEApplicationInfo);
      }
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
  }
  
  public synchronized void jdMethod_if(String paramString, AEApplicationInfo paramAEApplicationInfo)
    throws AEException
  {
    b localb = (b)this.d2.get(paramString);
    if (localb == null) {
      throw new AEException(13, AERepository.H("ERR_INVALID_NAME_OR_PASS"));
    }
    a(localb, paramString, paramAEApplicationInfo);
  }
  
  private synchronized void a(b paramb, String paramString, AEApplicationInfo paramAEApplicationInfo)
  {
    paramb.jdField_if = paramAEApplicationInfo;
    this.d2.remove(paramString);
    this.d2.put(paramb.jdField_if.getName(), paramb);
  }
  
  public synchronized boolean a(DBConnection paramDBConnection, AEApplicationInfo paramAEApplicationInfo)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      Object[] arrayOfObject = { paramAEApplicationInfo.getName() };
      localDBResultSet = paramDBConnection.I("select id,name,password from AE_Application where name = ?");
      localDBResultSet.a(arrayOfObject);
      for (int i = 0; localDBResultSet.a(); i = 1)
      {
        j = localDBResultSet.jdMethod_int(1);
        String str1 = localDBResultSet.jdMethod_try(2);
        String str2 = localDBResultSet.jdMethod_try(3);
        if (this.d2.containsKey(str1))
        {
          boolean bool = true;
          return bool;
        }
        paramAEApplicationInfo = new AEApplicationInfo(str1, str2);
        a(j, paramAEApplicationInfo);
      }
      int j = i;
      return j;
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
  }
  
  /* Error */
  public synchronized boolean jdMethod_if(DBConnection paramDBConnection, AEApplicationInfo paramAEApplicationInfo)
    throws AEException
  {
    // Byte code:
    //   0: iconst_1
    //   1: anewarray 21	java/lang/Object
    //   4: dup
    //   5: iconst_0
    //   6: aload_2
    //   7: invokevirtual 8	com/ffusion/alert/interfaces/AEApplicationInfo:getName	()Ljava/lang/String;
    //   10: aastore
    //   11: astore_3
    //   12: aload_1
    //   13: ldc 31
    //   15: invokevirtual 32	com/ffusion/alert/db/DBConnection:I	(Ljava/lang/String;)Lcom/ffusion/alert/db/DBResultSet;
    //   18: astore 4
    //   20: aload 4
    //   22: aload_3
    //   23: invokevirtual 33	com/ffusion/alert/db/DBResultSet:a	([Ljava/lang/Object;)V
    //   26: aload 4
    //   28: invokevirtual 34	com/ffusion/alert/db/DBResultSet:a	()Z
    //   31: ifeq +14 -> 45
    //   34: iconst_1
    //   35: istore 5
    //   37: aload 4
    //   39: invokevirtual 54	com/ffusion/alert/db/DBResultSet:for	()V
    //   42: iload 5
    //   44: ireturn
    //   45: iconst_0
    //   46: istore 5
    //   48: aload 4
    //   50: invokevirtual 54	com/ffusion/alert/db/DBResultSet:for	()V
    //   53: iload 5
    //   55: ireturn
    //   56: astore 6
    //   58: aload 4
    //   60: invokevirtual 54	com/ffusion/alert/db/DBResultSet:for	()V
    //   63: aload 6
    //   65: athrow
    //   66: astore_3
    //   67: new 14	com/ffusion/alert/interfaces/AEException
    //   70: dup
    //   71: iconst_1
    //   72: aload_3
    //   73: invokestatic 29	com/ffusion/alert/db/DBSqlUtils:a	(Ljava/sql/SQLException;)Ljava/sql/SQLException;
    //   76: invokespecial 30	com/ffusion/alert/interfaces/AEException:<init>	(ILjava/lang/Throwable;)V
    //   79: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	80	0	this	AppInfoCache
    //   0	80	1	paramDBConnection	DBConnection
    //   0	80	2	paramAEApplicationInfo	AEApplicationInfo
    //   11	12	3	arrayOfObject	Object[]
    //   66	7	3	localSQLException	SQLException
    //   18	41	4	localDBResultSet	DBResultSet
    //   35	19	5	bool	boolean
    //   56	8	6	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   26	37	56	finally
    //   45	48	56	finally
    //   56	58	56	finally
    //   0	42	66	java/sql/SQLException
    //   45	53	66	java/sql/SQLException
    //   56	66	66	java/sql/SQLException
  }
  
  public synchronized int jdMethod_int(AEApplicationInfo paramAEApplicationInfo)
    throws AEException
  {
    if (paramAEApplicationInfo == null) {
      throw new AEException(13, AERepository.H("ERR_INVALID_NAME_OR_PASS"));
    }
    String str = paramAEApplicationInfo.getName();
    if ((str == null) || (str.equals("Universal Alert Engine"))) {
      throw new AEException(13, AERepository.H("ERR_INVALID_NAME_OR_PASS"));
    }
    b localb = (b)this.d2.get(str);
    if (localb == null) {
      throw new AEException(13, AERepository.H("ERR_INVALID_NAME_OR_PASS"));
    }
    if ((paramAEApplicationInfo.getPassword() == null) || (localb.jdField_if.getPassword() == null) || (!paramAEApplicationInfo.getPassword().equals(localb.jdField_if.getPassword()))) {
      throw new AEException(13, AERepository.H("ERR_INVALID_NAME_OR_PASS"));
    }
    return localb.a;
  }
  
  public synchronized int F(String paramString)
    throws AEException
  {
    b localb = (b)this.d2.get(paramString);
    if (localb == null) {
      throw new AEException(13, AERepository.H("ERR_INVALID_NAME_OR_PASS"));
    }
    return localb.a;
  }
  
  public synchronized AEApplicationInfo s(int paramInt)
    throws AEException
  {
    b localb = (b)this.d4.jdMethod_if(paramInt);
    if (localb == null) {
      throw new AEException(13, AERepository.H("ERR_INVALID_NAME_OR_PASS"));
    }
    return localb.jdField_if;
  }
  
  public synchronized boolean G(String paramString)
    throws AEException
  {
    return this.d2.containsKey(paramString);
  }
  
  public synchronized AEApplicationInfo[] aE()
    throws AEException
  {
    Iterator localIterator = this.d2.values().iterator();
    ArrayList localArrayList = new ArrayList();
    while (localIterator.hasNext())
    {
      localObject = ((b)localIterator.next()).jdField_if;
      if ((((AEApplicationInfo)localObject).getPassword() != null) && (!((AEApplicationInfo)localObject).getName().equals("Universal Alert Engine"))) {
        localArrayList.add(localObject);
      }
    }
    Collections.sort(localArrayList, ed);
    Object localObject = new AEApplicationInfo[localArrayList.size()];
    localArrayList.toArray((Object[])localObject);
    return localObject;
  }
  
  private final void a(int paramInt, AEApplicationInfo paramAEApplicationInfo)
  {
    b localb = new b(paramInt, paramAEApplicationInfo);
    this.d2.put(localb.jdField_if.getName(), localb);
    this.d4.jdMethod_if(paramInt, localb);
  }
  
  public class b
  {
    public int a;
    public AEApplicationInfo jdField_if;
    
    public b(int paramInt, AEApplicationInfo paramAEApplicationInfo)
    {
      this.a = paramInt;
      this.jdField_if = paramAEApplicationInfo;
    }
  }
  
  private static class a
    implements Comparator
  {
    private a() {}
    
    public int compare(Object paramObject1, Object paramObject2)
    {
      Collator localCollator = Collator.getInstance();
      CollationKey localCollationKey1 = localCollator.getCollationKey(((AEApplicationInfo)paramObject1).getName());
      CollationKey localCollationKey2 = localCollator.getCollationKey(((AEApplicationInfo)paramObject2).getName());
      return localCollationKey1.compareTo(localCollationKey2);
    }
    
    a(AppInfoCache.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.db.AppInfoCache
 * JD-Core Version:    0.7.0.1
 */