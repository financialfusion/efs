package com.ffusion.alert.engine;

import com.ffusion.alert.db.AERepository;
import com.ffusion.alert.db.DBConnection;
import com.ffusion.alert.db.ServerInfo;
import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.shared.AELog;

public class ClusterHeartbeat
  implements Runnable, EngineThreadConstants
{
  private static final long cP = 60000L;
  private static final int cR = 20;
  private static final long cJ = 1000L;
  private static final long cT = 600000L;
  private static final int cK = 1;
  private static final int cF = 2;
  private static final int cH = 3;
  private long cV;
  private long cQ;
  private long cW;
  private long c0;
  private long cZ;
  private ServerInfo cM;
  private Thread cO;
  private int cX;
  private int cG;
  private AERepository cL;
  private DBConnection cY;
  private AlertEngine cN;
  private boolean cS = false;
  private Object cU = new Object();
  private a cI;
  private Thread cE;
  
  public ClusterHeartbeat(AERepository paramAERepository, AlertEngine paramAlertEngine, ServerInfo paramServerInfo, long paramLong1, long paramLong2)
    throws AEException
  {
    this.cL = paramAERepository;
    this.cM = paramServerInfo;
    this.cN = paramAlertEngine;
    this.cX = 0;
    this.cV = paramLong1;
    this.cQ = (paramLong1 >> 3);
    this.cW = paramLong2;
  }
  
  public synchronized void ag()
    throws AEException
  {
    if (this.cX != 0) {
      return;
    }
    this.cX = 1;
    this.cY = this.cL.aL();
    if (this.cM.getServerRole() == 1)
    {
      ServerInfo localServerInfo = ServerInfo.jdMethod_void(this.cY);
      if ((localServerInfo != null) && (!this.cM.equals(localServerInfo)))
      {
        this.cG = 3;
      }
      else
      {
        this.cM.jdMethod_long(this.cY);
        this.cM.jdMethod_try(ServerInfo.d(this.cY));
        this.cM.a(this.cL, this.cY, true);
        AELog.a(AEInstance.do, "MSG_ENGINE_ACTIVATING", this.cM.getServerURL(), 0);
        this.cN.k();
        this.cG = 1;
      }
    }
    else
    {
      this.cG = 3;
    }
    this.c0 = ServerInfo.d(this.cY);
    this.cZ = this.c0;
    this.cO = new Thread(this, "ClusterHeartbeat");
    this.cO.start();
  }
  
  public void ah()
    throws AEException
  {
    synchronized (this)
    {
      if (this.cX != 1) {
        return;
      }
      this.cX = 3;
    }
    try
    {
      synchronized (this.cU)
      {
        if (ab()) {
          try
          {
            this.cU.wait();
          }
          catch (InterruptedException localInterruptedException2) {}
        }
        if (this.cG != 3)
        {
          this.cN.jdMethod_if(true);
          this.cM.jdMethod_else(this.cY);
        }
        this.cO.interrupt();
        this.cO.join(60000L);
      }
      AELog.a("ClusterHeartbeat.stop() - thread has joined", 1);
    }
    catch (InterruptedException localInterruptedException1) {}
    this.cL.k(this.cY);
    this.cY = null;
  }
  
  public void ac()
    throws AEException
  {
    synchronized (this.cU)
    {
      if (ab()) {
        try
        {
          this.cU.wait();
        }
        catch (InterruptedException localInterruptedException) {}
      }
      this.cS = true;
      if (this.cG == 1) {
        this.cN.r();
      }
    }
  }
  
  public void ad()
    throws AEException
  {
    synchronized (this.cU)
    {
      if (ab()) {
        try
        {
          this.cU.wait();
        }
        catch (InterruptedException localInterruptedException) {}
      }
      this.cS = false;
      ServerInfo localServerInfo = null;
      localServerInfo = ServerInfo.jdMethod_void(this.cY);
      if ((this.cG == 1) && (this.cM.equals(localServerInfo))) {
        this.cN.h();
      }
    }
  }
  
  public boolean aj()
  {
    synchronized (this.cU)
    {
      return this.cS;
    }
  }
  
  public void run()
  {
    AELog.a("ClusterHeartbeat.run() - Start up, interval = ", this.cV, false, 1);
    int i = 0;
    int j = 0;
    long l1 = 1000L;
    do
    {
      try
      {
        for (;;)
        {
          if (this.cX == 1)
          {
            this.cM.b(this.cY);
            ServerInfo localServerInfo = null;
            localServerInfo = ServerInfo.jdMethod_void(this.cY);
            if ((this.cM.equals(localServerInfo)) && ((this.cG == 1) || ((this.cG == 2) && (!aj()))))
            {
              this.cM.jdMethod_try(ServerInfo.d(this.cY));
              this.cM.a(this.cL, this.cY, true);
            }
            synchronized (this.cU)
            {
              if (!aj())
              {
                switch (this.cG)
                {
                case 1: 
                  if (!ab())
                  {
                    if (!this.cM.equals(localServerInfo)) {
                      if (this.cM.getServerRole() == 1)
                      {
                        if (localServerInfo == null) {
                          AELog.a(AEInstance.do, "MSG_ENGINE_PRIMARY_ACTIVATION_PENDING_NO_ACTIVE", this.cM.getServerURL(), 0);
                        } else {
                          AELog.a(AEInstance.do, "MSG_ENGINE_PRIMARY_ACTIVATION_PENDING", this.cM.getServerURL(), localServerInfo.getServerURL(), 0);
                        }
                        this.cM.jdMethod_long(this.cY);
                        this.cG = 2;
                      }
                      else
                      {
                        if (localServerInfo == null) {
                          AELog.a(AEInstance.do, "MSG_ENGINE_DEACTIVATING_NO_ACTIVE", this.cM.getServerURL(), 0);
                        } else {
                          AELog.a(AEInstance.do, "MSG_ENGINE_DEACTIVATING", this.cM.getServerURL(), localServerInfo.getServerURL(), 0);
                        }
                        f(3);
                      }
                    }
                  }
                  else if ((ae() == 3) && (ai()))
                  {
                    try
                    {
                      af();
                    }
                    catch (Exception localException2)
                    {
                      AELog.a(localException2, 0);
                    }
                    this.cG = 3;
                    AELog.a(AEInstance.do, "MSG_ENGINE_DEACTIVATED", this.cM.getServerURL(), 0);
                  }
                  break;
                case 3: 
                  long l3;
                  if (localServerInfo == null) {
                    l3 = 9223372036854775807L;
                  } else {
                    l3 = ServerInfo.d(this.cY) - localServerInfo.aH();
                  }
                  if ((l3 > this.cW) || (this.cM.getServerRole() == 1))
                  {
                    this.cM.jdMethod_try(ServerInfo.d(this.cY));
                    this.cM.a(this.cL, this.cY, true);
                    this.cM.jdMethod_long(this.cY);
                    this.cG = 2;
                    if (this.cM.getServerRole() == 1)
                    {
                      if (localServerInfo == null) {
                        AELog.a(AEInstance.do, "MSG_ENGINE_PRIMARY_ACTIVATION_PENDING_NO_ACTIVE", this.cM.getServerURL(), 0);
                      } else {
                        AELog.a(AEInstance.do, "MSG_ENGINE_PRIMARY_ACTIVATION_PENDING", this.cM.getServerURL(), localServerInfo.getServerURL(), 0);
                      }
                    }
                    else if (localServerInfo == null) {
                      AELog.a(AEInstance.do, "MSG_ENGINE_ACTIVATION_PENDING_NO_ACTIVE", this.cM.getServerURL(), 0);
                    } else {
                      AELog.a(AEInstance.do, "MSG_ENGINE_ACTIVATION_PENDING", localServerInfo.getServerURL(), localServerInfo.aH(), true, this.cM.getServerURL(), 0);
                    }
                  }
                  break;
                case 2: 
                  if (ab())
                  {
                    if (ai()) {
                      try
                      {
                        af();
                        this.cG = 1;
                        AELog.a(AEInstance.do, "MSG_ENGINE_ACTIVATED", this.cM.getServerURL(), 0);
                      }
                      catch (AEException localAEException4)
                      {
                        AELog.a(localAEException4, AEInstance.do, "MSG_ENGINE_DEACTIVATED", this.cM.getServerURL(), 0);
                        this.cG = 3;
                      }
                    }
                  }
                  else if (!this.cM.equals(localServerInfo))
                  {
                    this.cG = 3;
                    AELog.a(AEInstance.do, "MSG_ENGINE_DEACTIVATED", this.cM.getServerURL(), 0);
                  }
                  else
                  {
                    AELog.a(AEInstance.do, "MSG_ENGINE_ACTIVATING", this.cM.getServerURL(), 0);
                    int k = (this.cM.getServerRole() == 1) && ((this.cN.jdMethod_goto()) || (this.cN.d() == 2)) ? 2 : 1;
                    f(k);
                  }
                  break;
                }
                if ((j != 0) && (this.cN.d() == 2))
                {
                  if (this.cG == 1)
                  {
                    AELog.a("ClusterHeartbeat.run() - resuming the engine from emergency suspend.", 0);
                    f(4);
                  }
                }
                else if (ae() == 4)
                {
                  af();
                  AELog.a("ClusterHeartbeat.run() - engine resumed from emergency suspend.", 0);
                }
              }
            }
            j = 0;
            i = 0;
            long l2 = ServerInfo.d(this.cY);
            while (this.cZ - l2 < this.cQ) {
              this.cZ += this.cV;
            }
            long l4 = this.cZ - l2;
            if (ab())
            {
              a(l4);
              continue;
            }
            try
            {
              AELog.a("ClusterHeartbeat.run() - sleeping ", l4, false, 1);
              Thread.sleep(l4);
            }
            catch (InterruptedException localInterruptedException2)
            {
              AELog.a("ClusterHeartbeat.run() - Interrupted", 1);
            }
          }
        }
      }
      catch (AEException localAEException1)
      {
        for (;;)
        {
          i++;
          try
          {
            if (!this.cY.aT())
            {
              AELog.a("ClusterHeartbeat.run() - renewing stale connection", 1);
              this.cY = this.cL.l(this.cY);
            }
            else
            {
              AELog.a(localAEException1, "ClusterHeartbeat.run()", 0);
            }
          }
          catch (AEException localAEException2)
          {
            AELog.a(localAEException2, "ClusterHeartbeat.run() - failed renewing connection", 0);
          }
        }
        if (i > 20)
        {
          i = 20;
          try
          {
            if ((this.cG == 1) && (this.cN.d() != 2))
            {
              AELog.a("ClusterHeartbeat.run() - exceeded maximum heartbeat failure: suspending engine.", 0);
              this.cN.r();
            }
            if (j == 0)
            {
              j = 1;
              l1 = 1000L;
            }
          }
          catch (AEException localAEException3)
          {
            AELog.a(localAEException1, "ClusterHeartbeat.run() - falied emergency suspend.", 0);
          }
          try
          {
            AELog.a("ClusterHeartbeat.run() - heartbeat suspend: sleeping " + l1 + " (ms): ", 0);
            Thread.sleep(l1);
          }
          catch (InterruptedException localInterruptedException1) {}
          if (l1 != 600000L)
          {
            l1 *= 2L;
            if (l1 > 600000L) {
              l1 = 600000L;
            }
          }
        }
      }
      catch (Exception localException1)
      {
        AELog.a(localException1, "ClusterHeartbeat.run() internal error - ", 0);
      }
    } while (this.cX != 3);
    AELog.a("ClusterHeartbeat.run() - done - returning", 1);
  }
  
  void jdMethod_do(long paramLong)
  {
    this.cW = paramLong;
  }
  
  void jdMethod_if(long paramLong)
  {
    this.cV = paramLong;
  }
  
  private boolean ab()
  {
    return this.cI != null;
  }
  
  private boolean ai()
  {
    return (this.cE != null) && (!this.cE.isAlive());
  }
  
  private int ae()
  {
    if (this.cI == null) {
      return 0;
    }
    return this.cI.jdMethod_if();
  }
  
  private void f(int paramInt)
  {
    if (ab())
    {
      AELog.a("ClusterHeartbeat.launchAction() - Cannot perform action ", paramInt, " because an action is already pending: ", ae(), 1);
      return;
    }
    AELog.a("ClusterHeartbeat.launchAction() - starting action ", paramInt, 1);
    this.cI = new a(paramInt);
    this.cE = new Thread(this.cI, "HeartbeatActionWorker " + paramInt);
    this.cE.start();
  }
  
  private void a(long paramLong)
  {
    if (!ab()) {
      return;
    }
    try
    {
      this.cE.join(paramLong);
    }
    catch (InterruptedException localInterruptedException) {}
  }
  
  private void af()
    throws AEException
  {
    if ((!ab()) || (!ai())) {
      return;
    }
    try
    {
      Throwable localThrowable = this.cI.a();
      if (localThrowable == null) {
        return;
      }
      if ((localThrowable instanceof AEException)) {
        throw ((AEException)localThrowable);
      }
      throw new AEException(1034, localThrowable);
    }
    finally
    {
      AELog.a("ClusterHeartbeat.completePendingAction() - completing action ", this.cI.jdMethod_if(), 1);
      this.cI = null;
      this.cE = null;
      this.cU.notifyAll();
    }
  }
  
  private class a
    implements Runnable
  {
    public static final int jdField_new = 0;
    public static final int jdField_if = 1;
    public static final int jdField_do = 2;
    public static final int jdField_int = 3;
    public static final int jdField_try = 4;
    private int a;
    Throwable jdField_for;
    
    a(int paramInt)
    {
      this.a = paramInt;
    }
    
    public void run()
    {
      try
      {
        switch (this.a)
        {
        case 1: 
          ClusterHeartbeat.this.cN.k();
          break;
        case 2: 
          ClusterHeartbeat.this.cN.u();
          break;
        case 3: 
          ClusterHeartbeat.this.cN.jdField_if(false);
          break;
        case 4: 
          ClusterHeartbeat.this.cN.h();
        }
      }
      catch (Throwable localThrowable)
      {
        this.jdField_for = localThrowable;
      }
    }
    
    public Throwable a()
    {
      return this.jdField_for;
    }
    
    public int jdField_if()
    {
      return this.a;
    }
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.engine.ClusterHeartbeat
 * JD-Core Version:    0.7.0.1
 */