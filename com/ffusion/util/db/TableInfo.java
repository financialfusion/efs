package com.ffusion.util.db;

import java.util.ArrayList;

class TableInfo
  extends ArrayList
{
  protected void add(String paramString, int paramInt)
  {
    add(new a(paramString, paramInt));
  }
  
  protected String getName(int paramInt)
  {
    return ((a)get(paramInt)).a;
  }
  
  protected int getType(int paramInt)
  {
    return ((a)get(paramInt)).jdField_if;
  }
  
  class a
  {
    protected int jdField_if;
    protected String a;
    
    protected a(String paramString, int paramInt)
    {
      this.a = paramString;
      this.jdField_if = paramInt;
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.db.TableInfo
 * JD-Core Version:    0.7.0.1
 */