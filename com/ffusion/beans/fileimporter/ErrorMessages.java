package com.ffusion.beans.fileimporter;

import java.util.ArrayList;
import java.util.Collections;

public class ErrorMessages
  extends ArrayList
{
  private boolean a = true;
  
  public void add(ErrorMessage paramErrorMessage)
  {
    super.add(paramErrorMessage);
  }
  
  public boolean operationCanContinue()
  {
    return this.a;
  }
  
  public String getOperationCanContinue()
  {
    return String.valueOf(this.a);
  }
  
  public void setOperationCanContinue(boolean paramBoolean)
  {
    this.a = paramBoolean;
  }
  
  public void sort()
  {
    Collections.sort(this);
  }
  
  public int getSize()
  {
    return size();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.fileimporter.ErrorMessages
 * JD-Core Version:    0.7.0.1
 */