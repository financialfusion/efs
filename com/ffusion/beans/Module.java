package com.ffusion.beans;

import java.util.Comparator;

public class Module
  extends ExtendABean
{
  private int a5W;
  private String a5V;
  private String a5X;
  
  public Module(int paramInt, String paramString)
  {
    this.a5W = paramInt;
    this.a5V = paramString;
    this.a5X = paramString;
  }
  
  public Module(int paramInt, String paramString1, String paramString2)
  {
    this.a5W = paramInt;
    this.a5V = paramString1;
    this.a5X = paramString2;
  }
  
  public int getId()
  {
    return this.a5W;
  }
  
  public void setId(int paramInt)
  {
    this.a5W = paramInt;
  }
  
  public String getName()
  {
    return this.a5V;
  }
  
  public void setName(String paramString)
  {
    this.a5V = paramString;
  }
  
  public String getDisplayName()
  {
    return this.a5X;
  }
  
  public void setDisplayName(String paramString)
  {
    this.a5X = paramString;
  }
  
  public static Comparator getDescNameComparator()
  {
    new Comparator()
    {
      public int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        Module localModule1 = (Module)paramAnonymousObject1;
        Module localModule2 = (Module)paramAnonymousObject2;
        return localModule1.a5V.compareTo(localModule2.a5V);
      }
    };
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.Module
 * JD-Core Version:    0.7.0.1
 */