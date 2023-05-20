package com.ffusion.alert.shared;

import java.util.ArrayList;

public final class AEIntMap
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
  private static final Object jdField_else = new Object();
  
  public AEIntMap()
  {
    this.jdField_byte = 179;
    this.jdField_int = 89;
    this.jdField_char = 89;
    this.jdField_new = new int['³'];
    this.jdField_do = new Object['³'];
  }
  
  public AEIntMap(int paramInt)
  {
    this.jdField_byte = ((int)(paramInt / 0.5D));
    this.jdField_byte = (this.jdField_byte < 31 ? 31 : this.jdField_byte);
    this.jdField_byte += (this.jdField_byte + 1) % 2;
    this.jdField_int = ((int)(this.jdField_byte * 0.5D));
    this.jdField_char = (this.jdField_byte / 2);
    this.jdField_new = new int[this.jdField_byte];
    this.jdField_do = new Object[this.jdField_byte];
  }
  
  public final int jdField_for()
  {
    return this.jdField_try;
  }
  
  public final boolean jdField_int()
  {
    return this.jdField_try == 0;
  }
  
  public final void jdField_if()
  {
    for (int i = 0; i < this.jdField_byte; i++) {
      this.jdField_do[i] = null;
    }
    this.jdField_try = 0;
  }
  
  public int[] jdField_new()
  {
    int[] arrayOfInt = new int[this.jdField_try];
    int i = 0;
    int j = 0;
    while ((i < this.jdField_new.length) && (j < this.jdField_try))
    {
      if ((this.jdField_do[i] != null) && (this.jdField_do[i] != jdField_else))
      {
        arrayOfInt[j] = this.jdField_new[i];
        j++;
      }
      i++;
    }
    return arrayOfInt;
  }
  
  public ArrayList a()
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < this.jdField_byte; i++) {
      if ((this.jdField_do[i] != null) && (this.jdField_do[i] != jdField_else)) {
        localArrayList.add(this.jdField_do[i]);
      }
    }
    return localArrayList;
  }
  
  public final void jdField_if(int paramInt, Object paramObject)
  {
    if (++this.jdField_try > this.jdField_int) {
      jdField_do();
    }
    a(paramInt, paramObject);
  }
  
  public final Object jdField_if(int paramInt)
  {
    int i = (paramInt * 517 & 0x7FFFFFFF) % this.jdField_byte;
    Object localObject = null;
    while (((localObject = this.jdField_do[i]) != null) && ((paramInt != this.jdField_new[i]) || (this.jdField_do[i] == jdField_else))) {
      i = (i + this.jdField_char) % this.jdField_byte;
    }
    return localObject;
  }
  
  public final Object a(int paramInt)
  {
    int i = (paramInt * 517 & 0x7FFFFFFF) % this.jdField_byte;
    Object localObject = null;
    while ((localObject = this.jdField_do[i]) != null)
    {
      if ((paramInt == this.jdField_new[i]) && (this.jdField_do[i] != jdField_else))
      {
        this.jdField_try -= 1;
        this.jdField_do[i] = jdField_else;
        break;
      }
      i = (i + this.jdField_char) % this.jdField_byte;
    }
    return localObject;
  }
  
  public final boolean jdField_do(int paramInt)
  {
    for (int i = (paramInt * 517 & 0x7FFFFFFF) % this.jdField_byte; this.jdField_do[i] != null; i = (i + this.jdField_char) % this.jdField_byte) {
      if ((paramInt == this.jdField_new[i]) && (this.jdField_do[i] != jdField_else)) {
        return true;
      }
    }
    return false;
  }
  
  private int a(int paramInt, Object paramObject)
  {
    for (int i = (paramInt * 517 & 0x7FFFFFFF) % this.jdField_byte; (this.jdField_do[i] != null) && (this.jdField_do[i] != jdField_else); i = (i + this.jdField_char) % this.jdField_byte) {}
    this.jdField_new[i] = paramInt;
    this.jdField_do[i] = paramObject;
    return i;
  }
  
  private void jdField_do()
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
      if ((localObject != null) && (localObject != jdField_else)) {
        a(arrayOfInt[j], localObject);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.shared.AEIntMap
 * JD-Core Version:    0.7.0.1
 */