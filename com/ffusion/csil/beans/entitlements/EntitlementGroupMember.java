package com.ffusion.csil.beans.entitlements;

import com.ffusion.util.beans.ExtendABean;
import java.io.Serializable;
import java.util.Locale;

public class EntitlementGroupMember
  extends ExtendABean
  implements Serializable
{
  public static final String ID = "ID";
  public static final String TYPE = "TYPE";
  public static final String SUBTYPE = "SUBTYPE";
  public static final String GROUPID = "GROUPID";
  public static final String BUSINESSID = "BUSINESSID";
  protected String _memberId;
  protected String _memberType;
  protected String _memberSubType;
  protected int _groupID;
  protected String _agentID;
  protected String _agentType;
  protected int _businessID;
  
  public EntitlementGroupMember() {}
  
  public EntitlementGroupMember(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == null) {
      return false;
    }
    if (!(paramObject instanceof EntitlementGroupMember)) {
      return false;
    }
    EntitlementGroupMember localEntitlementGroupMember = (EntitlementGroupMember)paramObject;
    String str1 = localEntitlementGroupMember.getId();
    String str2 = localEntitlementGroupMember.getMemberType();
    String str3 = localEntitlementGroupMember.getMemberSubType();
    if (((str1 != null) && (this._memberId == null)) || ((str1 == null) && (this._memberId != null))) {
      return false;
    }
    if (((str2 != null) && (this._memberType == null)) || ((str2 == null) && (this._memberType != null))) {
      return false;
    }
    if (((str3 != null) && (this._memberSubType == null)) || ((str3 == null) && (this._memberSubType != null))) {
      return false;
    }
    if ((str1 != null) && (!str1.equals(this._memberId))) {
      return false;
    }
    if ((str2 != null) && (!str2.equals(this._memberType))) {
      return false;
    }
    if ((str3 != null) && (!str3.equals(this._memberSubType))) {
      return false;
    }
    return this._groupID == localEntitlementGroupMember.getEntitlementGroupId();
  }
  
  public void setId(String paramString)
  {
    this._memberId = paramString;
  }
  
  public String getId()
  {
    return this._memberId;
  }
  
  public void setMemberType(String paramString)
  {
    this._memberType = paramString;
  }
  
  public String getMemberType()
  {
    return this._memberType;
  }
  
  public void setMemberSubType(String paramString)
  {
    this._memberSubType = paramString;
  }
  
  public String getMemberSubType()
  {
    return this._memberSubType;
  }
  
  public void setEntitlementGroupId(int paramInt)
  {
    this._groupID = paramInt;
  }
  
  public int getEntitlementGroupId()
  {
    return this._groupID;
  }
  
  public void setAgentID(String paramString)
  {
    this._agentID = paramString;
  }
  
  public String getAgentID()
  {
    return this._agentID;
  }
  
  public void setAgentType(String paramString)
  {
    this._agentType = paramString;
  }
  
  public void setBusinessID(int paramInt)
  {
    this._businessID = paramInt;
  }
  
  public int getBusinessID()
  {
    return this._businessID;
  }
  
  public String getAgentType()
  {
    return this._agentType;
  }
  
  public void set(EntitlementGroupMember paramEntitlementGroupMember)
  {
    setId(paramEntitlementGroupMember.getId());
    setMemberType(paramEntitlementGroupMember.getMemberType());
    setEntitlementGroupId(paramEntitlementGroupMember.getEntitlementGroupId());
    setMemberSubType(paramEntitlementGroupMember.getMemberSubType());
    setBusinessID(paramEntitlementGroupMember.getBusinessID());
    super.set(paramEntitlementGroupMember);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ID")) {
      this._memberId = paramString2;
    } else if (paramString1.equals("TYPE")) {
      this._memberType = paramString2;
    } else if (paramString1.equals("SUBTYPE")) {
      this._memberSubType = paramString2;
    } else if (paramString1.equals("GROUPID")) {
      this._groupID = Integer.parseInt(paramString2);
    } else if (paramString1.equals("BUSINESSID")) {
      this._businessID = Integer.parseInt(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.EntitlementGroupMember
 * JD-Core Version:    0.7.0.1
 */