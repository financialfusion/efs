package com.ffusion.tasks;

public abstract interface ValidatingTask
  extends Task
{
  public abstract void setProcess(String paramString);
  
  public abstract void setValidate(String paramString);
  
  public abstract void setValidInputURL(String paramString);
  
  public abstract void setSuccessURL(String paramString);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ValidatingTask
 * JD-Core Version:    0.7.0.1
 */