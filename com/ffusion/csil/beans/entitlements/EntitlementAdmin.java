package com.ffusion.csil.beans.entitlements;

import java.io.Serializable;

public class EntitlementAdmin
  implements Serializable
{
  private int jdField_try;
  private int jdField_if;
  private boolean jdField_do;
  private boolean jdField_int;
  private String jdField_new;
  private String a;
  private String jdField_for;
  
  public EntitlementAdmin() {}
  
  public EntitlementAdmin(int paramInt1, int paramInt2)
  {
    this.jdField_try = paramInt1;
    this.jdField_if = paramInt2;
  }
  
  public EntitlementAdmin(EntitlementGroupMember paramEntitlementGroupMember, int paramInt)
  {
    this(paramEntitlementGroupMember.getEntitlementGroupId(), paramInt);
    this.jdField_new = paramEntitlementGroupMember.getMemberType();
    this.a = paramEntitlementGroupMember.getMemberSubType();
    this.jdField_for = paramEntitlementGroupMember.getId();
  }
  
  public boolean equals(Object paramObject)
  {
    try
    {
      EntitlementAdmin localEntitlementAdmin = (EntitlementAdmin)paramObject;
      if (this.jdField_new == null) {
        return (localEntitlementAdmin.getGranteeGroupId() == this.jdField_try) && (localEntitlementAdmin.getTargetGroupId() == this.jdField_if) && (localEntitlementAdmin.canAdminister() == this.jdField_do) && (localEntitlementAdmin.canExtend() == this.jdField_int);
      }
      return (localEntitlementAdmin.getGranteeGroupId() == this.jdField_try) && (localEntitlementAdmin.getGranteeMemberType().equals(this.jdField_new)) && (localEntitlementAdmin.getGranteeMemberSubType().equals(this.a)) && (localEntitlementAdmin.getGranteeMemberId().equals(this.jdField_for)) && (localEntitlementAdmin.getTargetGroupId() == this.jdField_if) && (localEntitlementAdmin.canAdminister() == this.jdField_do) && (localEntitlementAdmin.canExtend() == this.jdField_int);
    }
    catch (Exception localException) {}
    return false;
  }
  
  public void setGranteeGroupId(int paramInt)
  {
    this.jdField_try = paramInt;
  }
  
  public int getGranteeGroupId()
  {
    return this.jdField_try;
  }
  
  public void setGranteeMemberType(String paramString)
  {
    this.jdField_new = paramString;
  }
  
  public String getGranteeMemberType()
  {
    return this.jdField_new;
  }
  
  public void setGranteeMemberSubType(String paramString)
  {
    this.a = paramString;
  }
  
  public String getGranteeMemberSubType()
  {
    return this.a;
  }
  
  public void setGranteeMemberId(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getGranteeMemberId()
  {
    return this.jdField_for;
  }
  
  public void setTargetGroupId(int paramInt)
  {
    this.jdField_if = paramInt;
  }
  
  public int getTargetGroupId()
  {
    return this.jdField_if;
  }
  
  public void setAdminister(boolean paramBoolean)
  {
    this.jdField_do = paramBoolean;
  }
  
  public boolean canAdminister()
  {
    return this.jdField_do;
  }
  
  public void setExtend(boolean paramBoolean)
  {
    this.jdField_int = paramBoolean;
  }
  
  public boolean canExtend()
  {
    return this.jdField_int;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.EntitlementAdmin
 * JD-Core Version:    0.7.0.1
 */