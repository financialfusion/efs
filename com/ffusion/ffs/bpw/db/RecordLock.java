package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.util.FFSDebug;

class RecordLock
{
  private String a;
  private boolean jdField_if;
  
  RecordLock(String paramString)
  {
    this.a = paramString;
    this.jdField_if = false;
  }
  
  String a()
  {
    return this.a;
  }
  
  synchronized void jdMethod_do()
  {
    if (this.jdField_if) {
      try
      {
        wait();
        this.jdField_if = true;
      }
      catch (InterruptedException localInterruptedException)
      {
        FFSDebug.log(FFSDebug.stackTrace(localInterruptedException), 6);
      }
    } else {
      this.jdField_if = true;
    }
  }
  
  synchronized void jdField_if()
  {
    this.jdField_if = false;
    notify();
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.RecordLock
 * JD-Core Version:    0.7.0.1
 */