package com.ffusion.alert.db;

import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.alert.interfaces.IAEStatusConstants;
import com.ffusion.alert.shared.AEUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class DeliveryInfo
  implements IAEDeliveryInfo, Cloneable, IAEStatusConstants
{
  public static final int l = 1;
  public static final int g = 1073741824;
  public static final int x = 1073741824;
  private static final String k = "alertId,deliveryOrder,channelId,deliveryString,maxDelay,properties";
  private static final String v = "insert into AE_DeliveryInfo({|||id,|}alertId,deliveryOrder,channelId,deliveryString,maxDelay,properties) values({|||AE_Seq.nextval,|}?,?,?,?,?,?)";
  private static final String s = "update AE_DeliveryInfo set alertId=?, deliveryOrder=?, channelId=?,deliveryString=?, maxDelay=?, properties=? where id=?";
  private static final String o = "select id,alertId,deliveryOrder,channelId,deliveryString,maxDelay,properties from AE_DeliveryInfo where id=? ";
  private static final String w = "select id,alertId,deliveryOrder,channelId,deliveryString,maxDelay,properties from AE_DeliveryInfo where alertId=? and properties < 1073741824 order by deliveryOrder, id";
  private static final String r = "select id,alertId,deliveryOrder,channelId,deliveryString,maxDelay,properties from AE_DeliveryInfo where alertId=? order by deliveryOrder, id";
  private static final String i = "select deliveryString from AE_DeliveryInfo where id=?";
  public static final int q = 1;
  public static final int f = 2;
  private int h;
  private int t;
  private int j;
  private int p;
  private String y;
  private String n;
  private long m;
  private int u;
  
  public DeliveryInfo() {}
  
  public DeliveryInfo(int paramInt, IAEDeliveryInfo paramIAEDeliveryInfo)
  {
    setDeliveryOrder(paramIAEDeliveryInfo.getDeliveryOrder());
    jdMethod_if(paramInt);
    setDeliveryChannelName(paramIAEDeliveryInfo.getDeliveryChannelName());
    setDeliveryProperties(paramIAEDeliveryInfo.getDeliveryProperties());
    setMaxDelay(paramIAEDeliveryInfo.getMaxDelay());
    setSuspended(paramIAEDeliveryInfo.isSuspended());
  }
  
  public final int getId()
  {
    return this.h;
  }
  
  private final void jdMethod_int(int paramInt)
  {
    this.h = paramInt;
  }
  
  public final int jdMethod_int()
  {
    return this.t;
  }
  
  public final void jdMethod_do(int paramInt)
  {
    this.t = paramInt;
  }
  
  public final int getDeliveryOrder()
  {
    return this.j;
  }
  
  public final void setDeliveryOrder(int paramInt)
  {
    this.j = paramInt;
  }
  
  public final int jdMethod_if()
  {
    return this.p;
  }
  
  public final void jdMethod_if(int paramInt)
  {
    this.p = paramInt;
  }
  
  public final String getDeliveryChannelName()
  {
    return this.y;
  }
  
  public final void setDeliveryChannelName(String paramString)
  {
    this.y = paramString;
  }
  
  public final Properties getDeliveryProperties()
  {
    return AEUtils.jdMethod_if(this.n);
  }
  
  public final void setDeliveryProperties(Properties paramProperties)
  {
    this.n = AEUtils.a(paramProperties);
  }
  
  public final String jdMethod_for()
  {
    return this.n;
  }
  
  final void a(String paramString)
  {
    this.n = paramString;
  }
  
  public final long getMaxDelay()
  {
    return this.m;
  }
  
  public final void setMaxDelay(long paramLong)
  {
    this.m = paramLong;
  }
  
  public final int jdMethod_do()
  {
    return this.u;
  }
  
  private final void a(int paramInt)
  {
    this.u = paramInt;
  }
  
  public final boolean jdMethod_for(int paramInt)
  {
    return (this.u & paramInt) != 0;
  }
  
  public final void a(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean) {
      this.u |= paramInt;
    } else {
      this.u &= (paramInt ^ 0xFFFFFFFF);
    }
  }
  
  public final boolean isSuspended()
  {
    return jdMethod_for(1);
  }
  
  public final void setSuspended(boolean paramBoolean)
  {
    a(1, paramBoolean);
  }
  
  public final boolean a()
  {
    return jdMethod_for(1);
  }
  
  public void a(AERepository paramAERepository, DBConnection paramDBConnection)
    throws AEException
  {
    boolean bool1 = false;
    try
    {
      bool1 = paramDBConnection.aU();
      if (bool1) {
        paramDBConnection.jdMethod_try(false);
      }
      boolean bool2 = DBSqlUtils.jdMethod_if(paramDBConnection.aS(), this.n);
      Object[] arrayOfObject;
      if (this.h == 0)
      {
        arrayOfObject = new Object[] { new Integer(this.t), new Integer(this.j), new Integer(this.p), bool2 ? " " : this.n, this.m == 0L ? null : new Long(this.m), new Integer(this.u) };
        paramDBConnection.jdMethod_if("insert into AE_DeliveryInfo({|||id,|}alertId,deliveryOrder,channelId,deliveryString,maxDelay,properties) values({|||AE_Seq.nextval,|}?,?,?,?,?,?)", arrayOfObject);
        this.h = paramAERepository.h(paramDBConnection);
      }
      else
      {
        arrayOfObject = new Object[] { new Integer(this.t), new Integer(this.j), new Integer(this.p), bool2 ? " " : this.n, this.m == 0L ? null : new Long(this.m), new Integer(this.u), new Integer(this.h) };
        paramDBConnection.jdMethod_if("update AE_DeliveryInfo set alertId=?, deliveryOrder=?, channelId=?,deliveryString=?, maxDelay=?, properties=? where id=?", arrayOfObject);
      }
      if (bool2)
      {
        arrayOfObject = new Object[] { this.n };
        paramDBConnection.a("select deliveryString from AE_DeliveryInfo where id=?", this.h, arrayOfObject);
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
          paramDBConnection.jdMethod_try(true);
        }
      }
      catch (SQLException localSQLException4)
      {
        throw new AEException(1, DBSqlUtils.a(localSQLException4));
      }
    }
  }
  
  public static DeliveryInfo a(DBConnection paramDBConnection, int paramInt)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      Object[] arrayOfObject = { new Integer(paramInt) };
      localDBResultSet = paramDBConnection.I("select id,alertId,deliveryOrder,channelId,deliveryString,maxDelay,properties from AE_DeliveryInfo where id=? ");
      DeliveryInfo localDeliveryInfo1 = null;
      localDBResultSet.a(arrayOfObject);
      if (localDBResultSet.a())
      {
        localDeliveryInfo1 = new DeliveryInfo();
        a(localDBResultSet, localDeliveryInfo1);
      }
      DeliveryInfo localDeliveryInfo2 = localDeliveryInfo1;
      return localDeliveryInfo2;
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
  
  public static DeliveryInfo[] a(DBConnection paramDBConnection, int paramInt, boolean paramBoolean)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      Object[] arrayOfObject = { new Integer(paramInt) };
      DeliveryInfo localDeliveryInfo = null;
      ArrayList localArrayList = new ArrayList();
      localDBResultSet = paramDBConnection.I(paramBoolean ? "select id,alertId,deliveryOrder,channelId,deliveryString,maxDelay,properties from AE_DeliveryInfo where alertId=? and properties < 1073741824 order by deliveryOrder, id" : "select id,alertId,deliveryOrder,channelId,deliveryString,maxDelay,properties from AE_DeliveryInfo where alertId=? order by deliveryOrder, id");
      localDBResultSet.a(arrayOfObject);
      while (localDBResultSet.a())
      {
        localDeliveryInfo = new DeliveryInfo();
        a(localDBResultSet, localDeliveryInfo);
        localArrayList.add(localDeliveryInfo);
      }
      DeliveryInfo[] arrayOfDeliveryInfo1 = new DeliveryInfo[localArrayList.size()];
      localArrayList.toArray(arrayOfDeliveryInfo1);
      DeliveryInfo[] arrayOfDeliveryInfo2 = arrayOfDeliveryInfo1;
      return arrayOfDeliveryInfo2;
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
  
  private static void a(DBResultSet paramDBResultSet, DeliveryInfo paramDeliveryInfo)
    throws SQLException
  {
    paramDeliveryInfo.jdMethod_int(paramDBResultSet.jdMethod_int(1));
    paramDeliveryInfo.jdMethod_do(paramDBResultSet.jdMethod_int(2));
    paramDeliveryInfo.setDeliveryOrder(paramDBResultSet.jdMethod_int(3));
    paramDeliveryInfo.jdMethod_if(paramDBResultSet.jdMethod_int(4));
    paramDeliveryInfo.a(paramDBResultSet.jdMethod_do(5));
    paramDeliveryInfo.setMaxDelay(paramDBResultSet.jdMethod_char(6));
    paramDeliveryInfo.a(paramDBResultSet.jdMethod_int(7));
  }
  
  public Object clone()
  {
    Object localObject = null;
    try
    {
      localObject = super.clone();
    }
    catch (CloneNotSupportedException localCloneNotSupportedException) {}
    return localObject;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.db.DeliveryInfo
 * JD-Core Version:    0.7.0.1
 */