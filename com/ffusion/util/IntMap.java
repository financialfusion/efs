package com.ffusion.util;

public final class IntMap
{
  private static final int a = 31;
  private static final int jdField_if = 179;
  private static final int jdField_case = 517;
  private static final double jdField_for = 0.5D;
  private int jdField_try;
  private int jdField_int;
  private int jdField_char;
  private int[] jdField_new;
  private Object[] jdField_do;
  private int jdField_byte;
  
  public IntMap()
  {
    this.jdField_byte = 179;
    this.jdField_int = 89;
    this.jdField_char = 89;
    this.jdField_new = new int['³'];
    this.jdField_do = new Object['³'];
  }
  
  public IntMap(int paramInt)
  {
    this.jdField_byte = ((int)(paramInt / 0.5D));
    this.jdField_byte = (this.jdField_byte < 31 ? 31 : this.jdField_byte);
    this.jdField_byte += (this.jdField_byte + 1) % 2;
    this.jdField_int = ((int)(this.jdField_byte * 0.5D));
    this.jdField_char = (this.jdField_byte / 2);
    this.jdField_new = new int[this.jdField_byte];
    this.jdField_do = new Object[this.jdField_byte];
  }
  
  public final int size()
  {
    return this.jdField_try;
  }
  
  public final boolean isEmpty()
  {
    return this.jdField_try == 0;
  }
  
  public final void clear()
  {
    for (int i = 0; i < this.jdField_byte; i++) {
      this.jdField_do[i] = null;
    }
    this.jdField_try = 0;
  }
  
  public final void put(int paramInt, Object paramObject)
  {
    if (++this.jdField_try > this.jdField_int) {
      a();
    }
    a(paramInt, paramObject);
  }
  
  public final Object get(int paramInt)
  {
    int i = (paramInt * 517 & 0x7FFFFFFF) % this.jdField_byte;
    Object localObject = null;
    while (((localObject = this.jdField_do[i]) != null) && (paramInt != this.jdField_new[i])) {
      i = (i + this.jdField_char) % this.jdField_byte;
    }
    return localObject;
  }
  
  public final boolean containsKey(int paramInt)
  {
    for (int i = (paramInt * 517 & 0x7FFFFFFF) % this.jdField_byte; this.jdField_do[i] != null; i = (i + this.jdField_char) % this.jdField_byte) {
      if (paramInt == this.jdField_new[i]) {
        return true;
      }
    }
    return false;
  }
  
  private int a(int paramInt, Object paramObject)
  {
    for (int i = (paramInt * 517 & 0x7FFFFFFF) % this.jdField_byte; this.jdField_do[i] != null; i = (i + this.jdField_char) % this.jdField_byte) {}
    this.jdField_new[i] = paramInt;
    this.jdField_do[i] = paramObject;
    return i;
  }
  
  private void a()
  {
    int i = this.jdField_byte;
    this.jdField_byte = (this.jdField_byte * 2 + 1);
    this.jdField_int = ((int)(this.jdField_byte * 0.5D));
    this.jdField_char = i;
    int[] arrayOfInt = this.jdField_new;
    this.jdField_new = new int[this.jdField_byte];
    Object[] arrayOfObject = this.jdField_do;
    this.jdField_do = new Object[this.jdField_byte];
    for (int j = 0; j < i; j++)
    {
      Object localObject = arrayOfObject[j];
      if (localObject != null) {
        a(arrayOfInt[j], localObject);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.IntMap
 * JD-Core Version:    0.7.0.1
 */