package com.ffusion.tasks;

public abstract class InitService
  extends BaseTask
{
  protected String className;
  protected String initURL;
  
  public void setClassName(String paramString)
  {
    this.className = paramString;
  }
  
  public void setInitURL(String paramString)
  {
    this.initURL = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.InitService
 * JD-Core Version:    0.7.0.1
 */