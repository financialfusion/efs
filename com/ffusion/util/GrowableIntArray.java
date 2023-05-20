package com.ffusion.util;

public class GrowableIntArray
{
  private static final int jdField_if = 8;
  private int[] jdField_do;
  private int a;
  private int jdField_for;
  
  public GrowableIntArray()
    throws Exception
  {
    this(8);
  }
  
  public GrowableIntArray(int paramInt)
    throws Exception
  {
    if (paramInt < 1) {
      throw new Exception("Cannot create a GrowableIntArray of size: " + paramInt);
    }
    this.jdField_for = paramInt;
    this.a = 0;
    this.jdField_do = new int[paramInt];
  }
  
  public void add(int paramInt)
  {
    if (this.a == this.jdField_for)
    {
      int[] arrayOfInt = this.jdField_do;
      this.jdField_for *= 2;
      this.jdField_do = new int[this.jdField_for];
      System.arraycopy(arrayOfInt, 0, this.jdField_do, 0, this.a);
    }
    this.jdField_do[this.a] = paramInt;
    this.a += 1;
  }
  
  public int getElementAt(int paramInt)
  {
    return this.jdField_do[paramInt];
  }
  
  public int[] getElements()
  {
    int[] arrayOfInt = new int[this.a];
    System.arraycopy(this.jdField_do, 0, arrayOfInt, 0, this.a);
    return arrayOfInt;
  }
  
  public int size()
  {
    return this.a;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.GrowableIntArray
 * JD-Core Version:    0.7.0.1
 */