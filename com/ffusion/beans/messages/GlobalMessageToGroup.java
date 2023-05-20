package com.ffusion.beans.messages;

public class GlobalMessageToGroup
{
  private int jdField_if;
  private int a;
  
  public GlobalMessageToGroup() {}
  
  public GlobalMessageToGroup(int paramInt1, int paramInt2)
  {
    this.jdField_if = paramInt1;
    this.a = paramInt2;
  }
  
  public void setToGroupID(int paramInt)
  {
    this.a = paramInt;
  }
  
  public int getToGroupID()
  {
    return this.a;
  }
  
  public void setToGroupType(int paramInt)
  {
    this.jdField_if = paramInt;
  }
  
  public int getToGroupType()
  {
    return this.jdField_if;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.GlobalMessageToGroup
 * JD-Core Version:    0.7.0.1
 */