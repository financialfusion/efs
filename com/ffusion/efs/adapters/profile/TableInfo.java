package com.ffusion.efs.adapters.profile;

import java.util.ArrayList;

public class TableInfo
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


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.TableInfo
 * JD-Core Version:    0.7.0.1
 */