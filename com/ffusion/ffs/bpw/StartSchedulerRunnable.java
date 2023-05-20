package com.ffusion.ffs.bpw;

import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.scheduling.Scheduler;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;

public class StartSchedulerRunnable
  implements Runnable, FFSConst, BPWResource
{
  private Scheduler y7;
  private long za = 60000L;
  private long y9 = 2L * this.za;
  private long y5 = this.za;
  private boolean y3 = false;
  private boolean y6 = true;
  private BPWServer y4 = null;
  private String y8 = "PRIMARY";
  
  public StartSchedulerRunnable(BPWServer paramBPWServer, Scheduler paramScheduler, long paramLong, String paramString)
  {
    this.y7 = paramScheduler;
    if (paramLong < this.za) {
      FFSDebug.log("*** Schedule wait time " + paramLong + " milliseconds" + " is less than the default time " + this.za + " milliseconds.", 1);
    }
    this.za = paramLong;
    this.y9 = (2L * this.za);
    this.y5 = this.za;
    this.y4 = paramBPWServer;
    this.y8 = paramString;
  }
  
  public synchronized void run()
  {
    try
    {
      FFSDebug.log("StartScheduleRunnable _scheduler._keeprunning" + this.y7._keeprunning, 6);
      if ((!this.y8.equalsIgnoreCase("PRIMARY")) && (!this.y8.equalsIgnoreCase("SECONDARY")))
      {
        FFSDebug.log(" ==== StartSchedulerRunnable: Invalid Scheduler Role.", 6);
        return;
      }
      if (this.y8.equalsIgnoreCase("SECONDARY"))
      {
        this.y6 = false;
        try
        {
          FFSProperties localFFSProperties = BPWServer.getProperties();
          long l = 60000L;
          String str4 = localFFSProperties.getProperty("cluster.scheduler.secondary.delay");
          if ((str4 != null) && (str4.length() >= 0)) {
            try
            {
              l = Long.parseLong(str4.trim());
              if (l < 0L) {
                throw new Exception("Error: secondary dalay time less than 0");
              }
            }
            catch (Exception localException4)
            {
              FFSDebug.log(" ==== Invalid Scheduler Secondary Delay Time ( " + str4 + " ). Use configuration property 'cluster.scheduler.secondary.delay' to fix this problem.");
              FFSDebug.console(" ==== Invalid Scheduler Secondary Delay Time ( " + str4 + " ). Use configuration property 'cluster.scheduler.secondary.delay' to fix this problem.");
              FFSDebug.console(" ==== Defaulting the delay time to 60000 millisecondes ....");
              FFSDebug.log(" ==== Defaulting the delay time to 60000 milliseconds ....");
              l = 60000L;
            }
          }
          FFSDebug.console(" ==== Waiting " + l + " milliseconds for Primary Scheduler Server(s) to start ....");
          FFSDebug.log(" ==== Waiting " + l + " milliseconds for Primary Scheduler Server(s) to start ....");
          wait(l);
        }
        catch (InterruptedException localInterruptedException1)
        {
          String str1 = "*** StartSchedulerRunnable.run: Error:" + FFSDebug.stackTrace(localInterruptedException1);
          FFSDebug.log(str1);
          FFSDebug.console(str1);
          return;
        }
      }
      FFSDebug.console(" ==== Starting Scheduler process ....");
      FFSDebug.log(" ==== Starting Scheduler process ....");
      for (;;)
      {
        if ((this.y7 != null) && (this.y7._keeprunning) && (!Thread.interrupted()))
        {
          FFSDebug.log("StartScheduleRunnable _scheduler.ownsLock()" + this.y7.ownsLock(), 6);
          try
          {
            if ((!this.y7.schIsRunning()) && (this.y7.getSchedulerLock()))
            {
              if (!this.y6) {
                try
                {
                  wait(this.y9);
                }
                catch (InterruptedException localInterruptedException2)
                {
                  FFSDebug.log("*** StartSchedulerRunnable.run: scheduler thread stopped.... ", 6);
                  break label694;
                }
              }
              FFSConnection localFFSConnection = DBUtil.getConnection();
              try
              {
                this.y7.start(localFFSConnection);
                this.y4.setSchedulerStatus(true);
                FFSDebug.console(" ==== Scheduler started ....");
                FFSDebug.log(" ==== Scheduler started ....");
                this.y4.startPurger("PURGEENGINE");
                FFSDebug.console(" ==== Purger Engine started....");
                FFSDebug.log(" ==== Purger Engine started....");
              }
              catch (Exception localException3)
              {
                String str3 = "*** StartSchedulerRunnable.run: Fail to start Scheduler:" + FFSDebug.stackTrace(localException3);
                FFSDebug.log(str3);
                FFSDebug.console(str3);
              }
              finally
              {
                DBUtil.freeConnection(localFFSConnection);
              }
            }
            else if (this.y7.checkSchedulerLock() == true)
            {
              FFSDebug.log("StartScheduleRunnable calling scheduler.flipLock. ", 6);
              this.y7.flipLock();
            }
            else if (this.y7.schThreadCreated() == true)
            {
              FFSDebug.log("This server does not have the scheduler lock. Stop the scheduler to clean up the ScheduleTimer threads.", 1);
              this.y3 = true;
              this.y7.stop();
            }
          }
          catch (Exception localException1)
          {
            FFSDebug.log("The server no longer owns the scheduler lock. Stopping the scheduler.", 1);
            this.y3 = true;
            this.y7.stop();
          }
          try
          {
            if ((this.y3 == true) && (this.y5 == this.za))
            {
              this.y5 = this.y9;
              this.y3 = false;
            }
            else if ((!this.y3) && (this.y5 == this.y9))
            {
              this.y5 = this.za;
            }
            wait(this.y5);
            this.y6 = false;
          }
          catch (InterruptedException localInterruptedException3)
          {
            FFSDebug.log("*** StartSchedulerRunnable.run: scheduler thread stopped.... ", 6);
          }
        }
      }
    }
    catch (Exception localException2)
    {
      label694:
      String str2 = "*** StartSchedulerRunnable.run: Error:" + FFSDebug.stackTrace(localException2);
      FFSDebug.log(str2);
      FFSDebug.console(str2);
    }
    FFSDebug.log("*** StartSchedulerRunnable.run: scheduler thread stopped.... ", 6);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.StartSchedulerRunnable
 * JD-Core Version:    0.7.0.1
 */