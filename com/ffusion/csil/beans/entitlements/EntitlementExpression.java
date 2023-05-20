package com.ffusion.csil.beans.entitlements;

import java.util.ArrayList;

public class EntitlementExpression
{
  public static final int OPERATION_OR = 0;
  public static final int OPERATION_AND = 1;
  public static final int OPERATION_LEAF = 2;
  private int a = 1;
  private ArrayList jdField_if = new ArrayList();
  
  public EntitlementExpression(int paramInt, ArrayList paramArrayList)
  {
    this.a = paramInt;
    this.jdField_if = paramArrayList;
  }
  
  public int getOperation()
  {
    return this.a;
  }
  
  public ArrayList getChildren()
  {
    return this.jdField_if;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.EntitlementExpression
 * JD-Core Version:    0.7.0.1
 */