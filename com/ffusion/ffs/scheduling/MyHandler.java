package com.ffusion.ffs.scheduling;

import com.ffusion.ffs.db.FFSConnection;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;

public class MyHandler
{
  public void eventHandler(String paramString1, String paramString2, int paramInt1, int paramInt2, FFSConnection paramFFSConnection)
  {
    System.out.println("---" + getClass().getName() + "(" + paramString1 + "," + paramString2 + "," + paramInt1 + "," + paramInt2 + ") " + Calendar.getInstance().getTime().toString());
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.scheduling.MyHandler
 * JD-Core Version:    0.7.0.1
 */