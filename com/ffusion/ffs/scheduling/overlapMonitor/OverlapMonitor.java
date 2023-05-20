package com.ffusion.ffs.scheduling.overlapMonitor;

import com.ffusion.ffs.util.FFSDebug;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

public class OverlapMonitor
{
  public static final int BLOCKING_MODE_NOWAIT = 100;
  public static final int BLOCKING_MODE_TIMEDWAIT = 101;
  public static final int BLOCKING_MODE_WAIT = 102;
  private static OverlapMonitor jdField_do = null;
  private Hashtable a = null;
  private Hashtable jdField_if = null;
  private Hashtable jdField_for = null;
  
  public static synchronized OverlapMonitor getInstance()
  {
    if (jdField_do == null) {
      jdField_do = new OverlapMonitor();
    }
    return jdField_do;
  }
  
  public static synchronized void reset() {}
  
  private OverlapMonitor()
  {
    a();
  }
  
  public synchronized void register(String paramString1, String paramString2, HashSet paramHashSet)
  {
    if (paramString1 == null)
    {
      FFSDebug.log("OverlapMonitor.register: Error: An attempt was made to register a Financial Institution ID with value of null.", 0);
      return;
    }
    if (paramString2 == null)
    {
      FFSDebug.log("OverlapMonitor.register: Error: An attempt was made to register a Running Group Name with value of null.", 0);
      return;
    }
    if (paramHashSet == null)
    {
      FFSDebug.log("OverlapMonitor.register: Error: An attempt was made to register a Conflict Set with value of null.", 0);
      return;
    }
    a(paramString1, paramString2, paramHashSet);
  }
  
  public synchronized boolean acquireRunTokenNoWait(String paramString1, String paramString2, String paramString3)
  {
    return acquireRunToken(paramString1, paramString2, paramString3, 100, 0L);
  }
  
  public synchronized boolean acquireRunToken(String paramString1, String paramString2, String paramString3, long paramLong)
  {
    return acquireRunToken(paramString1, paramString2, paramString3, 101, paramLong);
  }
  
  public synchronized boolean acquireRunToken(String paramString1, String paramString2, String paramString3)
  {
    return acquireRunToken(paramString1, paramString2, paramString3, 102, 0L);
  }
  
  public synchronized boolean acquireRunToken(String paramString1, String paramString2, String paramString3, int paramInt, long paramLong)
  {
    if (paramString1 == null)
    {
      FFSDebug.log("OverlapMonitor.acquireRunToken: Error: An attempt was made to acquire a token using a null value for the Financial Institution ID.", 0);
      return false;
    }
    if (paramString2 == null)
    {
      FFSDebug.log("OverlapMonitor.acquireRunToken: Error: An attempt was made to acquire a token using a null value for the Instruction Type.", 0);
      return false;
    }
    if (paramString3 == null)
    {
      FFSDebug.log("OverlapMonitor.acquireRunToken: Error: An attempt was made to acquire a token using a null value for the Running Group Name.", 0);
      return false;
    }
    if (paramLong <= 0L) {
      paramLong = 1L;
    }
    try
    {
      long l1 = System.currentTimeMillis();
      long l2 = 0L;
      long l3 = paramLong;
      int i = 1;
      while (i == 1)
      {
        if (paramString3.equals("BillPay"))
        {
          if (a(paramString1, paramString2, paramString3) == true) {
            i = 0;
          }
        }
        else if (jdField_if(paramString1, paramString2, paramString3) == true)
        {
          i = 0;
          continue;
        }
        FFSDebug.log("OverlapMonitor.acquireRunToken: Cannot run (" + paramString1 + ", " + paramString2 + ", " + paramString3 + ").", 6);
        FFSDebug.log("Unable to run schedule (" + paramString1 + ", " + paramString2 + ", " + paramString3 + ")" + " at this time due to Schedule" + " concurrency conflicts.", 1);
        if (paramInt == 100) {
          return false;
        }
        if (paramInt == 101)
        {
          FFSDebug.log("The schedule (" + paramString1 + ", " + paramString2 + ", " + paramString3 + ")" + " will wait for " + l3 + " milliseconds for the " + "concurrency conflict to clear.", 1);
          wait(l3);
          l2 = System.currentTimeMillis() - l1;
          l3 = paramLong - l2;
          if (l3 <= 0L)
          {
            FFSDebug.log("The concurrency conflict for the schedule (" + paramString1 + ", " + paramString2 + ", " + paramString3 + ")" + " failed to clear after " + l2 + " milliseconds.", 6);
            return false;
          }
        }
        else
        {
          if (paramInt != 102) {
            FFSDebug.log("An invalid blocking mode (Mode:" + paramInt + ") has been " + "encountered. The default blocking " + "mode (BLOCKING_MODE_WAIT) will be " + "used.", 6);
          }
          FFSDebug.log("The schedule (" + paramString1 + ", " + paramString2 + ", " + paramString3 + ")" + " will wait for the " + " concurrency conflict to clear.", 1);
          wait();
        }
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      return false;
    }
    catch (Exception localException1)
    {
      FFSDebug.log(localException1, "*** OverlapMonitor.acquireRunToken failed: ");
      FFSDebug.console("*** OverlapMonitor.acquireRunToken failed: " + FFSDebug.stackTrace(localException1));
      return false;
    }
    try
    {
      FFSDebug.log("OverlapMonitor.acquireRunToken: (" + paramString1 + ", " + paramString2 + ", " + paramString3 + ")", 6);
      a(paramString1, paramString3);
    }
    catch (Exception localException2)
    {
      FFSDebug.log(localException2, "*** OverlapMonitor.acquireRunToken failed: ");
      FFSDebug.console("*** OverlapMonitor.acquireRunToken failed: " + FFSDebug.stackTrace(localException2));
      return false;
    }
    return true;
  }
  
  public synchronized void releaseRunToken(String paramString1, String paramString2)
  {
    if (paramString1 == null)
    {
      FFSDebug.log("OverlapMonitor.releaseRunToken: Error: An attempt was made to release a token with a Financial Institution ID with value of null.", 0);
      return;
    }
    if (paramString2 == null)
    {
      FFSDebug.log("OverlapMonitor.releaseRunToken: Error: An attempt was made to release a tokenwith a Running Group Name with value of null.", 0);
      return;
    }
    try
    {
      FFSDebug.log("OverlapMonitor.releaseRunToken: (" + paramString1 + ", " + paramString2 + ")", 6);
      jdField_if(paramString1, paramString2);
      notifyAll();
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** OverlapMonitor.releaseRunToken failed: ");
      FFSDebug.console("*** OverlapMonitor.releaseRunToken failed: " + FFSDebug.stackTrace(localException));
    }
  }
  
  private synchronized void a()
  {
    this.a = new Hashtable();
    this.jdField_if = new Hashtable();
    this.jdField_for = new Hashtable();
  }
  
  private static synchronized void jdField_if()
  {
    jdField_do = new OverlapMonitor();
  }
  
  private synchronized void a(String paramString1, String paramString2, HashSet paramHashSet)
  {
    Object localObject1 = (Hashtable)this.a.get(paramString1);
    if (localObject1 == null)
    {
      localObject1 = new Hashtable();
      this.a.put(paramString1, localObject1);
    }
    Object localObject2 = (RunToken)((Hashtable)localObject1).get(paramString2);
    if (localObject2 == null)
    {
      localObject2 = new RunToken(paramString1, paramString2);
      ((Hashtable)localObject1).put(paramString2, localObject2);
    }
    localObject1 = (Hashtable)this.jdField_for.get(paramString1);
    if (localObject1 == null)
    {
      localObject1 = new Hashtable();
      this.jdField_for.put(paramString1, localObject1);
    }
    localObject2 = new HashSet(paramHashSet);
    ((Hashtable)localObject1).put(paramString2, localObject2);
    localObject1 = (HashSet)this.jdField_if.get(paramString1);
    if (localObject1 == null)
    {
      localObject1 = new HashSet();
      this.jdField_if.put(paramString1, localObject1);
    }
  }
  
  private synchronized boolean a(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    ArrayList localArrayList = jdField_do();
    for (int i = 0; i < localArrayList.size(); i++) {
      if (a(paramString1, paramString2, paramString3, (String)localArrayList.get(i)) == true) {
        return false;
      }
    }
    return true;
  }
  
  private synchronized boolean jdField_if(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    return a(paramString1, paramString2, paramString3, paramString1) != true;
  }
  
  private boolean a(String paramString1, String paramString2, String paramString3, String paramString4)
    throws Exception
  {
    HashSet localHashSet1 = jdField_for(paramString1, paramString3);
    HashSet localHashSet2 = a(paramString4);
    if ((localHashSet2.size() == 0) || (localHashSet1.size() == 0)) {
      return false;
    }
    HashSet localHashSet3 = (HashSet)localHashSet2.clone();
    localHashSet3.retainAll(localHashSet1);
    if (localHashSet3.size() == 0) {
      return false;
    }
    FFSDebug.log("OverlapMonitor.conflictExists: Concurrency conflict(s):", 6);
    Iterator localIterator = localHashSet3.iterator();
    while (localIterator.hasNext() == true)
    {
      String str = (String)localIterator.next();
      FFSDebug.log("Schedule (" + paramString1 + ", " + paramString2 + ", " + paramString3 + ")" + " conflicts with running group (" + paramString4 + ", " + str + ").", 6);
    }
    return true;
  }
  
  private synchronized void a(String paramString1, String paramString2)
    throws Exception
  {
    RunToken localRunToken = jdField_do(paramString1, paramString2);
    localRunToken.grabToken();
    HashSet localHashSet = a(paramString1);
    localHashSet.add(paramString2);
  }
  
  private synchronized void jdField_if(String paramString1, String paramString2)
    throws Exception
  {
    RunToken localRunToken = jdField_do(paramString1, paramString2);
    localRunToken.releaseToken();
    if (localRunToken.getRunStatus() == 1000)
    {
      HashSet localHashSet = a(paramString1);
      localHashSet.remove(paramString2);
    }
  }
  
  private RunToken jdField_do(String paramString1, String paramString2)
    throws Exception
  {
    Hashtable localHashtable = (Hashtable)this.a.get(paramString1);
    if (localHashtable == null) {
      throw new Exception("OverlapMonitor: TokenTable does not exist for (" + paramString1 + ", " + paramString2 + ").");
    }
    RunToken localRunToken = (RunToken)localHashtable.get(paramString2);
    if (localRunToken == null) {
      throw new Exception("OverlapMonitor: RunToken does not exist for (" + paramString1 + ", " + paramString2 + ").");
    }
    return localRunToken;
  }
  
  private HashSet jdField_for(String paramString1, String paramString2)
    throws Exception
  {
    Hashtable localHashtable = (Hashtable)this.jdField_for.get(paramString1);
    if (localHashtable == null) {
      throw new Exception("OverlapMonitor: ConflictTable does not exist for (" + paramString1 + ", " + paramString2 + ").");
    }
    HashSet localHashSet = (HashSet)localHashtable.get(paramString2);
    if (localHashSet == null) {
      throw new Exception("OverlapMonitor: ConflictSet does not exist for (" + paramString1 + ", " + paramString2 + ").");
    }
    return localHashSet;
  }
  
  private HashSet a(String paramString)
    throws Exception
  {
    HashSet localHashSet = (HashSet)this.jdField_if.get(paramString);
    if (localHashSet == null) {
      throw new Exception("OverlapMonitor: RunningTable does not exist for (" + paramString + ").");
    }
    return localHashSet;
  }
  
  private ArrayList jdField_do()
    throws Exception
  {
    if (!this.jdField_if.isEmpty()) {
      return new ArrayList(this.jdField_if.keySet());
    }
    throw new Exception("OverlapMonitor: Server Running Table is empty.");
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.scheduling.overlapMonitor.OverlapMonitor
 * JD-Core Version:    0.7.0.1
 */