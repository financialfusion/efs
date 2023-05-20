package com.ffusion.ffs.util;

class TaskQueue
{
  private FFSTimerTask[] a = new FFSTimerTask[''];
  private int jdField_if = 0;
  
  void a(FFSTimerTask paramFFSTimerTask)
  {
    if (++this.jdField_if == this.a.length)
    {
      FFSTimerTask[] arrayOfFFSTimerTask = new FFSTimerTask[2 * this.a.length];
      System.arraycopy(this.a, 0, arrayOfFFSTimerTask, 0, this.jdField_if);
      this.a = arrayOfFFSTimerTask;
    }
    this.a[this.jdField_if] = paramFFSTimerTask;
    a(this.jdField_if);
  }
  
  FFSTimerTask jdMethod_do()
  {
    return this.a[1];
  }
  
  void jdMethod_for()
  {
    this.a[1] = this.a[this.jdField_if];
    this.a[(this.jdField_if--)] = null;
    jdField_if(1);
  }
  
  void a(long paramLong)
  {
    this.a[1].jdField_for = paramLong;
    jdField_if(1);
  }
  
  boolean jdField_if()
  {
    return this.jdField_if == 0;
  }
  
  void a()
  {
    for (int i = 1; i <= this.jdField_if; i++) {
      this.a[i] = null;
    }
    this.jdField_if = 0;
  }
  
  private void a(int paramInt)
  {
    while (paramInt > 1)
    {
      int i = paramInt >> 1;
      if (this.a[i].jdField_for <= this.a[paramInt].jdField_for) {
        break;
      }
      FFSTimerTask localFFSTimerTask = this.a[i];
      this.a[i] = this.a[paramInt];
      this.a[paramInt] = localFFSTimerTask;
      paramInt = i;
    }
  }
  
  private void jdField_if(int paramInt)
  {
    int i;
    while ((i = paramInt << 1) <= this.jdField_if)
    {
      if ((i < this.jdField_if) && (this.a[i].jdField_for > this.a[(i + 1)].jdField_for)) {
        i++;
      }
      if (this.a[paramInt].jdField_for <= this.a[i].jdField_for) {
        break;
      }
      FFSTimerTask localFFSTimerTask = this.a[i];
      this.a[i] = this.a[paramInt];
      this.a[paramInt] = localFFSTimerTask;
      paramInt = i;
    }
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.TaskQueue
 * JD-Core Version:    0.7.0.1
 */