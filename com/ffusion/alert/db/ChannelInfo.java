package com.ffusion.alert.db;

import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.IAEChannelInfo;
import com.ffusion.alert.shared.AEUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.text.CollationKey;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Properties;

public class ChannelInfo
  implements IAEChannelInfo, Cloneable
{
  public static final int d = 1;
  private static final String jdField_byte = "className,initInfo,numInstances,numWorkers,name,appId,properties";
  private static final String h = "insert into AE_Channel({|||id,|}className,initInfo,numInstances,numWorkers,name,appId,properties) values({|||AE_Seq.nextval,|}?,?,?,?,?,?,?)";
  private static final String jdField_try = "update AE_Channel set className=?, initInfo=?, numInstances=?, numWorkers=?, name=?, appId=?, properties=? where id=?";
  private static final String e = "delete from AE_Channel where numInstances <= 0 and id not in( select channelId from AE_CrashRecovery ) and id not in( select channelId from AE_DeliveryInfo )";
  private static final String jdField_null = "select id,className,initInfo,numInstances,numWorkers,name,appId,properties from AE_Channel where id=?";
  private static final String b = "select id,className,initInfo,numInstances,numWorkers,name,appId,properties from AE_Channel where name=?";
  private static final String jdField_if = "select id,className,initInfo,numInstances,numWorkers,name,appId,properties from AE_Channel";
  private static final String jdField_case = "select id,className,initInfo,numInstances,numWorkers,name,appId,properties from AE_Channel where numInstances > 0";
  private static final String jdField_new = "select initInfo from AE_Channel where id=?";
  private int jdField_int;
  private String g;
  private Properties a;
  private int jdField_void;
  private int jdField_do;
  private String jdField_char;
  private int f;
  private int c;
  private boolean jdField_for;
  private String jdField_goto;
  private boolean jdField_long;
  private static final a jdField_else = new a(null);
  
  public final int getId()
  {
    return this.jdField_int;
  }
  
  private final void a(int paramInt)
  {
    this.jdField_int = paramInt;
  }
  
  public final String getClassName()
  {
    return this.g;
  }
  
  public final void setClassName(String paramString)
  {
    this.g = paramString;
  }
  
  public final Properties getInitInfo()
  {
    return this.a;
  }
  
  public final void setInitInfo(Properties paramProperties)
  {
    this.a = paramProperties;
  }
  
  public final int a()
  {
    return this.jdField_void;
  }
  
  public final void jdField_do(int paramInt)
  {
    this.jdField_void = paramInt;
  }
  
  public final int getNumWorkers()
  {
    return this.jdField_do;
  }
  
  public final void setNumWorkers(int paramInt)
  {
    this.jdField_do = paramInt;
  }
  
  public final String getChannelName()
  {
    return this.jdField_char;
  }
  
  public final void setChannelName(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public final int jdField_do()
  {
    return this.f;
  }
  
  public final void jdField_int(int paramInt)
  {
    this.f = paramInt;
  }
  
  public final boolean isLoaded()
  {
    return this.jdField_for;
  }
  
  public final void a(boolean paramBoolean)
  {
    this.jdField_for = paramBoolean;
  }
  
  public final boolean isFailed()
  {
    return this.jdField_long;
  }
  
  public final void jdField_do(boolean paramBoolean)
  {
    this.jdField_long = paramBoolean;
  }
  
  public final boolean isInstalled()
  {
    return this.jdField_void > 0;
  }
  
  public final String getApplicationName()
  {
    return this.jdField_goto;
  }
  
  public final void a(String paramString)
  {
    this.jdField_goto = paramString;
  }
  
  private final int jdField_if()
  {
    return this.c;
  }
  
  private final void jdField_for(int paramInt)
  {
    this.c = paramInt;
  }
  
  public final boolean jdField_if(int paramInt)
  {
    return (this.c & paramInt) != 0;
  }
  
  public final void a(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean) {
      this.c |= paramInt;
    } else {
      this.c &= (paramInt ^ 0xFFFFFFFF);
    }
  }
  
  public final boolean isLoadable()
  {
    return jdField_if(1);
  }
  
  public final void jdField_if(boolean paramBoolean)
  {
    a(1, paramBoolean);
  }
  
  public Object clone()
  {
    Properties localProperties = this.a == null ? null : (Properties)this.a.clone();
    ChannelInfo localChannelInfo = null;
    try
    {
      localChannelInfo = (ChannelInfo)super.clone();
    }
    catch (CloneNotSupportedException localCloneNotSupportedException) {}
    localChannelInfo.setInitInfo(localProperties);
    return localChannelInfo;
  }
  
  public void a(AERepository paramAERepository, DBConnection paramDBConnection)
    throws AEException
  {
    boolean bool1 = false;
    try
    {
      bool1 = paramDBConnection.aU();
      if (bool1) {
        paramDBConnection.jdField_try(false);
      }
      String str = AEUtils.a(this.a);
      boolean bool2 = DBSqlUtils.jdField_if(paramDBConnection.aS(), str);
      Object[] arrayOfObject;
      if (this.jdField_int == 0)
      {
        arrayOfObject = new Object[] { this.g, bool2 ? " " : str, new Integer(this.jdField_void), new Integer(this.jdField_do), this.jdField_char, this.f == 0 ? null : new Integer(this.f), new Integer(this.c) };
        paramDBConnection.jdField_if("insert into AE_Channel({|||id,|}className,initInfo,numInstances,numWorkers,name,appId,properties) values({|||AE_Seq.nextval,|}?,?,?,?,?,?,?)", arrayOfObject);
        this.jdField_int = paramAERepository.h(paramDBConnection);
      }
      else
      {
        arrayOfObject = new Object[] { this.g, bool2 ? " " : str, new Integer(this.jdField_void), new Integer(this.jdField_do), this.jdField_char, this.f == 0 ? null : new Integer(this.f), new Integer(this.c), new Integer(this.jdField_int) };
        paramDBConnection.jdField_if("update AE_Channel set className=?, initInfo=?, numInstances=?, numWorkers=?, name=?, appId=?, properties=? where id=?", arrayOfObject);
      }
      if (bool2)
      {
        arrayOfObject = new Object[] { str };
        paramDBConnection.a("select initInfo from AE_Channel where id=?", this.jdField_int, arrayOfObject);
      }
      if (bool1) {
        paramDBConnection.a0();
      }
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
          paramDBConnection.jdField_try(true);
        }
      }
      catch (SQLException localSQLException4)
      {
        throw new AEException(1, DBSqlUtils.a(localSQLException4));
      }
    }
  }
  
  public static void a(DBConnection paramDBConnection)
    throws AEException
  {
    try
    {
      int i = paramDBConnection.K("delete from AE_Channel where numInstances <= 0 and id not in( select channelId from AE_CrashRecovery ) and id not in( select channelId from AE_DeliveryInfo )");
    }
    catch (SQLException localSQLException)
    {
      throw new AEException(1, DBSqlUtils.a(localSQLException));
    }
  }
  
  public static ChannelInfo a(DBConnection paramDBConnection, int paramInt)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      localDBResultSet = paramDBConnection.I("select id,className,initInfo,numInstances,numWorkers,name,appId,properties from AE_Channel where id=?");
      ChannelInfo localChannelInfo1 = null;
      Object[] arrayOfObject = { new Integer(paramInt) };
      localDBResultSet.a(arrayOfObject);
      if (localDBResultSet.a())
      {
        localChannelInfo1 = new ChannelInfo();
        a(localDBResultSet, localChannelInfo1);
      }
      ChannelInfo localChannelInfo2 = localChannelInfo1;
      return localChannelInfo2;
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
          localDBResultSet.jdField_for();
        }
      }
      catch (SQLException localSQLException2) {}
    }
  }
  
  public static ChannelInfo a(DBConnection paramDBConnection, String paramString)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      localDBResultSet = paramDBConnection.I("select id,className,initInfo,numInstances,numWorkers,name,appId,properties from AE_Channel where name=?");
      ChannelInfo localChannelInfo1 = null;
      Object[] arrayOfObject = { paramString };
      localDBResultSet.a(arrayOfObject);
      if (localDBResultSet.a())
      {
        localChannelInfo1 = new ChannelInfo();
        a(localDBResultSet, localChannelInfo1);
      }
      ChannelInfo localChannelInfo2 = localChannelInfo1;
      return localChannelInfo2;
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
          localDBResultSet.jdField_for();
        }
      }
      catch (SQLException localSQLException2) {}
    }
  }
  
  public static ArrayList jdField_if(DBConnection paramDBConnection)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      ArrayList localArrayList1 = new ArrayList();
      localDBResultSet = paramDBConnection.I("select id,className,initInfo,numInstances,numWorkers,name,appId,properties from AE_Channel where numInstances > 0");
      ChannelInfo localChannelInfo = null;
      localDBResultSet.jdField_try();
      while (localDBResultSet.a())
      {
        localChannelInfo = new ChannelInfo();
        a(localDBResultSet, localChannelInfo);
        localArrayList1.add(localChannelInfo);
      }
      ArrayList localArrayList2 = localArrayList1;
      return localArrayList2;
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
          localDBResultSet.jdField_for();
        }
      }
      catch (SQLException localSQLException2) {}
    }
  }
  
  public static ArrayList jdField_do(DBConnection paramDBConnection)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      ArrayList localArrayList1 = new ArrayList();
      localDBResultSet = paramDBConnection.I("select id,className,initInfo,numInstances,numWorkers,name,appId,properties from AE_Channel");
      ChannelInfo localChannelInfo = null;
      localDBResultSet.jdField_try();
      while (localDBResultSet.a())
      {
        localChannelInfo = new ChannelInfo();
        a(localDBResultSet, localChannelInfo);
        localArrayList1.add(localChannelInfo);
      }
      ArrayList localArrayList2 = localArrayList1;
      return localArrayList2;
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
          localDBResultSet.jdField_for();
        }
      }
      catch (SQLException localSQLException2) {}
    }
  }
  
  private static void a(DBResultSet paramDBResultSet, ChannelInfo paramChannelInfo)
    throws SQLException
  {
    paramChannelInfo.a(paramDBResultSet.jdField_int(1));
    paramChannelInfo.setClassName(paramDBResultSet.jdField_try(2));
    paramChannelInfo.setInitInfo(AEUtils.jdField_if(paramDBResultSet.jdField_do(3)));
    paramChannelInfo.jdField_do(paramDBResultSet.jdField_int(4));
    paramChannelInfo.setNumWorkers(paramDBResultSet.jdField_int(5));
    paramChannelInfo.setChannelName(paramDBResultSet.jdField_try(6));
    paramChannelInfo.jdField_int(paramDBResultSet.jdField_int(7));
    paramChannelInfo.jdField_for(paramDBResultSet.jdField_int(8));
  }
  
  public static Comparator jdField_for()
  {
    return jdField_else;
  }
  
  private static class a
    implements Comparator
  {
    private a() {}
    
    public int compare(Object paramObject1, Object paramObject2)
    {
      Collator localCollator = Collator.getInstance();
      CollationKey localCollationKey1 = localCollator.getCollationKey(((IAEChannelInfo)paramObject1).getChannelName());
      CollationKey localCollationKey2 = localCollator.getCollationKey(((IAEChannelInfo)paramObject2).getChannelName());
      return localCollationKey1.compareTo(localCollationKey2);
    }
    
    a(ChannelInfo.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.db.ChannelInfo
 * JD-Core Version:    0.7.0.1
 */