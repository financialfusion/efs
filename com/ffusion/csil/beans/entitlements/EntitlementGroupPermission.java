package com.ffusion.csil.beans.entitlements;

import java.io.Serializable;

public class EntitlementGroupPermission
  implements Serializable
{
  private EntitlementGroupMember jdField_int;
  private EntitlementGroup jdField_new;
  private Entitlements jdField_do;
  private Limits jdField_for;
  private EntitlementTypePropertyLists jdField_if;
  private LimitTypePropertyLists a;
  
  public void setEntitlementGroupMember(EntitlementGroupMember paramEntitlementGroupMember)
  {
    this.jdField_int = paramEntitlementGroupMember;
  }
  
  public EntitlementGroupMember getEntitlementGroupMember()
  {
    return this.jdField_int;
  }
  
  public void setEntitlementGroup(EntitlementGroup paramEntitlementGroup)
  {
    this.jdField_new = paramEntitlementGroup;
  }
  
  public EntitlementGroup getEntitlementGroup()
  {
    return this.jdField_new;
  }
  
  public void setEntitlements(Entitlements paramEntitlements)
  {
    this.jdField_do = paramEntitlements;
  }
  
  public Entitlements getEntitlements()
  {
    return this.jdField_do;
  }
  
  public void setLimits(Limits paramLimits)
  {
    this.jdField_for = paramLimits;
  }
  
  public Limits getLimits()
  {
    return this.jdField_for;
  }
  
  public void setEntitlementTypePropertyLists(EntitlementTypePropertyLists paramEntitlementTypePropertyLists)
  {
    this.jdField_if = paramEntitlementTypePropertyLists;
  }
  
  public EntitlementTypePropertyLists getEntitlementTypePropertyLists()
  {
    return this.jdField_if;
  }
  
  public void setLimitTypePropertyLists(LimitTypePropertyLists paramLimitTypePropertyLists)
  {
    this.a = paramLimitTypePropertyLists;
  }
  
  public LimitTypePropertyLists getLimitTypePropertyLists()
  {
    return this.a;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.EntitlementGroupPermission
 * JD-Core Version:    0.7.0.1
 */