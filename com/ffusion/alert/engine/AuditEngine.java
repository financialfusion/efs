package com.ffusion.alert.engine;

import com.ffusion.alert.db.AERepository;
import com.ffusion.alert.db.AlertInstance;
import com.ffusion.alert.db.AlertRecovery;
import com.ffusion.alert.db.AuditInfo;
import com.ffusion.alert.db.DBConnection;
import com.ffusion.alert.db.DBSqlUtils;
import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.IAEAuditInfo;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.alert.shared.AELog;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuditEngine
  implements Runnable, IAEErrConstants, IAEMsgConstants
{
  private static final int bS = 20;
  private static final long bY = 1000L;
  private static final long b0 = 300000L;
  private AERepository bZ;
  private AEConfigManager bV;
  private DBConnection b1;
  private Thread bW;
  private boolean bR;
  private IAlertMessageQueue bU;
  private int bX;
  private ArrayList bT;
  
  public AuditEngine(AERepository paramAERepository, AEConfigManager paramAEConfigManager, int paramInt)
  {
    this.bZ = paramAERepository;
    this.bV = paramAEConfigManager;
    this.bU = new AlertMessageQueue();
    this.bX = paramInt;
  }
  
  public void jdMethod_char()
    throws AEException
  {
    this.bR = false;
    this.bT = new ArrayList(this.bX);
    synchronized (this.bT)
    {
      for (int i = 0; i < this.bX; i++) {
        this.bT.add(new AuditInfo());
      }
    }
    this.b1 = this.bZ.aL();
    try
    {
      this.b1.jdMethod_try(false);
    }
    catch (SQLException localSQLException)
    {
      this.bZ.k(this.b1);
      this.b1 = null;
      throw new AEException(1, DBSqlUtils.a(localSQLException));
    }
    this.bW = new Thread(this, "AuditEngine");
    this.bW.start();
  }
  
  public void jdMethod_else()
    throws AEException
  {
    this.bR = true;
    synchronized (this.bU)
    {
      this.bW.interrupt();
    }
    try
    {
      this.bW.join();
      this.bW = null;
    }
    catch (InterruptedException localInterruptedException) {}
    if (this.b1.aT())
    {
      try
      {
        this.b1.jdMethod_try(true);
      }
      catch (SQLException localSQLException)
      {
        this.bZ.k(this.b1);
        this.b1 = null;
        throw new AEException(1, DBSqlUtils.a(localSQLException));
      }
      this.bZ.k(this.b1);
    }
    this.b1 = null;
    this.bT.clear();
    this.bT = null;
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, IAEDeliveryInfo paramIAEDeliveryInfo, int paramInt6, long paramLong, int paramInt7, String paramString1, String paramString2)
  {
    AuditInfo localAuditInfo = jdMethod_byte();
    localAuditInfo.jdMethod_do(paramInt1);
    localAuditInfo.a(paramInt2);
    localAuditInfo.jdMethod_new(paramInt3);
    localAuditInfo.jdMethod_char(paramInt4);
    localAuditInfo.jdMethod_for(paramInt5);
    localAuditInfo.jdMethod_byte(paramInt6);
    localAuditInfo.jdMethod_do(paramLong);
    localAuditInfo.a(System.currentTimeMillis());
    localAuditInfo.a(paramIAEDeliveryInfo);
    localAuditInfo.jdMethod_try(paramInt7);
    localAuditInfo.jdMethod_for(paramString1);
    localAuditInfo.jdMethod_if(paramString2);
    this.bU.a(localAuditInfo);
  }
  
  public void run()
  {
    AuditInfo localAuditInfo = null;
    int i = 0;
    long l = 1000L;
    for (;;)
    {
      try
      {
        boolean bool = true;
        synchronized (this.bU)
        {
          if (this.bR) {
            break;
          }
          if (localAuditInfo == null)
          {
            bool = this.bU.a();
            localAuditInfo = (AuditInfo)this.bU.jdMethod_do();
          }
          else
          {
            bool = false;
          }
        }
        if (((bool) || (i > 0)) && (!this.b1.aT()))
        {
          this.b1 = this.bZ.l(this.b1);
          this.b1.jdMethod_try(false);
        }
        localAuditInfo.a(this.bZ, this.b1, false);
        this.b1.a0();
        a(localAuditInfo);
        localAuditInfo = null;
        i = 0;
        l = 1000L;
      }
      catch (SQLException localSQLException1)
      {
        i++;
        AELog.a(localSQLException1, AEInstance.a, "ERR_AUDIT_STORE_FAILED", 0);
      }
      catch (AEException localAEException1)
      {
        i++;
        AELog.a(localAEException1, AEInstance.a, "ERR_AUDIT_STORE_FAILED", 0);
      }
      catch (InterruptedException localInterruptedException1) {}
      if (i > 20)
      {
        i = 20;
        try
        {
          AELog.a("AuditEngine.run() - audit failed retry: sleeping " + l + " (ms): ", 0);
          Thread.sleep(l);
        }
        catch (InterruptedException localInterruptedException2) {}
        if (l != 300000L)
        {
          l *= 2L;
          if (l > 300000L) {
            l = 300000L;
          }
        }
      }
    }
    try
    {
      if (!this.b1.aT())
      {
        this.b1 = this.bZ.l(this.b1);
        this.b1.jdMethod_try(false);
      }
      Object[] arrayOfObject = this.bU.jdMethod_if();
      if (localAuditInfo != null) {
        try
        {
          localAuditInfo.a(this.bZ, this.b1, false);
          this.b1.a0();
        }
        catch (SQLException localSQLException3)
        {
          AELog.a(localSQLException3, AEInstance.a, "ERR_AUDIT_STORE_FAILED", 0);
        }
        catch (AEException localAEException3)
        {
          AELog.a(localAEException3, AEInstance.a, "ERR_AUDIT_STORE_FAILED", 0);
        }
        finally
        {
          a(localAuditInfo);
          localAuditInfo = null;
        }
      }
      for (int j = 0; j < arrayOfObject.length; j++) {
        try
        {
          localAuditInfo = (AuditInfo)arrayOfObject[j];
          localAuditInfo.a(this.bZ, this.b1, false);
          this.b1.a0();
        }
        catch (SQLException localSQLException4)
        {
          AELog.a(localSQLException4, AEInstance.a, "ERR_AUDIT_STORE_FAILED", 0);
        }
        catch (AEException localAEException4)
        {
          AELog.a(localAEException4, AEInstance.a, "ERR_AUDIT_STORE_FAILED", 0);
        }
        finally
        {
          a(localAuditInfo);
          localAuditInfo = null;
        }
      }
    }
    catch (SQLException localSQLException2)
    {
      AELog.a(localSQLException2, AEInstance.a, "ERR_AUDIT_STORE_FAILED", 0);
    }
    catch (AEException localAEException2)
    {
      AELog.a(localAEException2, AEInstance.a, "ERR_AUDIT_STORE_FAILED", 0);
    }
    finally
    {
      if (localAuditInfo != null)
      {
        a(localAuditInfo);
        localAuditInfo = null;
      }
    }
  }
  
  public IAEAuditInfo[] a(DBConnection paramDBConnection, int paramInt, long paramLong1, long paramLong2)
    throws AEException
  {
    ArrayList localArrayList = AuditInfo.a(paramDBConnection, paramInt, paramLong1, paramLong2);
    AuditInfo[] arrayOfAuditInfo = a(paramDBConnection, localArrayList);
    return arrayOfAuditInfo;
  }
  
  public IAEAuditInfo[] jdMethod_if(DBConnection paramDBConnection, int paramInt, long paramLong1, long paramLong2)
    throws AEException
  {
    ArrayList localArrayList = AuditInfo.jdMethod_if(paramDBConnection, paramInt, paramLong1, paramLong2);
    AuditInfo[] arrayOfAuditInfo = a(paramDBConnection, localArrayList);
    return arrayOfAuditInfo;
  }
  
  public IAEAuditInfo[] a(DBConnection paramDBConnection, int paramInt, String paramString, long paramLong1, long paramLong2)
    throws AEException
  {
    ArrayList localArrayList = AuditInfo.a(paramDBConnection, paramInt, paramString, paramLong1, paramLong2);
    AuditInfo[] arrayOfAuditInfo = a(paramDBConnection, localArrayList);
    return arrayOfAuditInfo;
  }
  
  public IAEAuditInfo[] a(DBConnection paramDBConnection, String paramString, int paramInt, long paramLong1, long paramLong2)
    throws AEException
  {
    ArrayList localArrayList = AuditInfo.a(paramDBConnection, paramString, paramInt, paramLong1, paramLong2);
    AuditInfo[] arrayOfAuditInfo = a(paramDBConnection, localArrayList);
    return arrayOfAuditInfo;
  }
  
  private AuditInfo[] a(DBConnection paramDBConnection, ArrayList paramArrayList)
    throws AEException
  {
    AuditInfo[] arrayOfAuditInfo = new AuditInfo[paramArrayList.size()];
    paramArrayList.toArray(arrayOfAuditInfo);
    return arrayOfAuditInfo;
  }
  
  public void jdMethod_case()
    throws AEException
  {
    DBConnection localDBConnection = this.bZ.aL();
    try
    {
      localDBConnection.jdMethod_try(false);
      long l = System.currentTimeMillis() - this.bV.N();
      AELog.a(AEInstance.do, "MSG_AUDIT_LOG_CLEANUP_BEGIN", 1);
      AlertRecovery.jdMethod_if(localDBConnection, l);
      AELog.a(AEInstance.do, "MSG_AUDIT_LOG_CLEANUP_END", 1);
      localDBConnection.a0();
      AELog.a(AEInstance.do, "MSG_CR_LOG_CLEANUP_BEGIN", 1);
      AlertRecovery.jdMethod_do(localDBConnection, l);
      AELog.a(AEInstance.do, "MSG_CR_LOG_CLEANUP_END", 1);
      AELog.a(AEInstance.do, "MSG_ALERT_CLEANUP_BEGIN", 1);
      AlertInstance.a(localDBConnection, l);
      AELog.a(AEInstance.do, "MSG_ALERT_CLEANUP_END", 1);
      localDBConnection.a0();
      AELog.a(AEInstance.do, "MSG_CHANNEL_CLEANUP_BEGIN", 1);
      this.bV.jdMethod_new(localDBConnection);
      AELog.a(AEInstance.do, "MSG_CHANNEL_CLEANUP_END", 1);
      localDBConnection.a0();
      AELog.a(AEInstance.do, "MSG_APP_CLEANUP_BEGIN", 1);
      this.bV.jdMethod_int(localDBConnection);
      AELog.a(AEInstance.do, "MSG_APP_CLEANUP_END", 1);
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
      this.bZ.k(localDBConnection);
      localDBConnection = null;
    }
  }
  
  public void jdMethod_long(int paramInt)
  {
    int i = paramInt - this.bX;
    this.bX = paramInt;
    if ((this.bT != null) && (i != 0)) {
      synchronized (this.bT)
      {
        int j = this.bT.size();
        if (i > 0)
        {
          while ((i > 0) && (j < paramInt))
          {
            this.bT.add(new AuditInfo());
            i--;
            j++;
          }
          this.bT.notifyAll();
        }
        else if (i < 0)
        {
          while ((i < 0) && (j != 0))
          {
            this.bT.remove(j - 1);
            i++;
            j--;
          }
        }
      }
    }
  }
  
  private AuditInfo jdMethod_byte()
  {
    synchronized (this.bT)
    {
      try
      {
        int i = 0;
        for (;;)
        {
          i = this.bT.size();
          if (i > 0) {
            break;
          }
          this.bT.wait();
        }
        return (AuditInfo)this.bT.remove(i - 1);
      }
      catch (InterruptedException localInterruptedException)
      {
        return new AuditInfo();
      }
    }
  }
  
  private void a(AuditInfo paramAuditInfo)
  {
    paramAuditInfo.jdMethod_new();
    synchronized (this.bT)
    {
      if (this.bT.size() < this.bX)
      {
        this.bT.add(paramAuditInfo);
        this.bT.notifyAll();
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.engine.AuditEngine
 * JD-Core Version:    0.7.0.1
 */