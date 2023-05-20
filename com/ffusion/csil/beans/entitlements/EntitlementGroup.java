package com.ffusion.csil.beans.entitlements;

import com.ffusion.util.Sortable;
import com.ffusion.util.beans.ExtendABean;
import java.io.Serializable;
import java.text.Collator;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

public class EntitlementGroup
  extends ExtendABean
  implements Serializable, Cloneable, Sortable
{
  public static final int ROOT_GROUP_ID = 0;
  public static final String NAME = "NAME";
  public static final String DISPLAY_NAME = "DISPLAYNAME";
  protected int groupId;
  protected String name;
  protected int parentId;
  protected Date modifiedDate;
  protected Date retrievalDate;
  protected String svc_bureau_id;
  protected EntitlementGroupProperties props;
  protected String ent_group_type;
  
  public EntitlementGroup()
  {
    this.props = new EntitlementGroupProperties(new Properties());
  }
  
  public EntitlementGroup(EntitlementGroup paramEntitlementGroup)
  {
    this.groupId = paramEntitlementGroup.groupId;
    this.name = paramEntitlementGroup.name;
    this.parentId = paramEntitlementGroup.parentId;
    this.modifiedDate = paramEntitlementGroup.modifiedDate;
    this.retrievalDate = paramEntitlementGroup.retrievalDate;
    this.svc_bureau_id = paramEntitlementGroup.svc_bureau_id;
    this.props = paramEntitlementGroup.props;
    this.ent_group_type = paramEntitlementGroup.ent_group_type;
  }
  
  public Object clone()
  {
    EntitlementGroup localEntitlementGroup = (EntitlementGroup)super.clone();
    localEntitlementGroup.groupId = this.groupId;
    localEntitlementGroup.name = this.name;
    localEntitlementGroup.parentId = this.parentId;
    if (this.modifiedDate != null) {
      localEntitlementGroup.modifiedDate = new Date(this.modifiedDate.getTime());
    }
    if (this.retrievalDate != null) {
      localEntitlementGroup.retrievalDate = new Date(this.retrievalDate.getTime());
    }
    localEntitlementGroup.svc_bureau_id = this.svc_bureau_id;
    if (this.props != null) {
      localEntitlementGroup.props = ((EntitlementGroupProperties)this.props.clone());
    }
    localEntitlementGroup.ent_group_type = this.ent_group_type;
    return localEntitlementGroup;
  }
  
  public void setGroupId(int paramInt)
  {
    this.groupId = paramInt;
  }
  
  public int getGroupId()
  {
    return this.groupId;
  }
  
  public void setGroupName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getGroupName()
  {
    return this.name;
  }
  
  public void setParentId(int paramInt)
  {
    this.parentId = paramInt;
  }
  
  public int getParentId()
  {
    return this.parentId;
  }
  
  public void setModifiedDate(Date paramDate)
  {
    this.modifiedDate = paramDate;
  }
  
  public Date getModifiedDate()
  {
    return this.modifiedDate;
  }
  
  public void setRetrievalDate(Date paramDate)
  {
    this.retrievalDate = paramDate;
  }
  
  public Date getRetrievalDate()
  {
    return this.retrievalDate;
  }
  
  public void setSvcBureauId(String paramString)
  {
    this.svc_bureau_id = paramString;
  }
  
  public String getSvcBureauId()
  {
    return this.svc_bureau_id;
  }
  
  public EntitlementGroupProperties getProperties()
  {
    return this.props;
  }
  
  public void setProperties(EntitlementGroupProperties paramEntitlementGroupProperties)
  {
    this.props = paramEntitlementGroupProperties;
  }
  
  public void setGroupPropertiesKey(String paramString)
  {
    this.props.setCurrentProperty(paramString);
  }
  
  public String getGroupPropertiesValue()
  {
    return this.props.getValueOfCurrentProperty();
  }
  
  public void setEntGroupType(String paramString)
  {
    this.ent_group_type = paramString;
  }
  
  public String getEntGroupType()
  {
    return this.ent_group_type;
  }
  
  public boolean equals(Object paramObject)
  {
    try
    {
      EntitlementGroup localEntitlementGroup = (EntitlementGroup)paramObject;
      if (localEntitlementGroup.groupId == this.groupId) {
        return true;
      }
    }
    catch (Exception localException)
    {
      return false;
    }
    return false;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Collator localCollator = doGetCollator();
    EntitlementGroup localEntitlementGroup = (EntitlementGroup)paramObject;
    int i = 1;
    if ("DISPLAYNAME".equals(paramString))
    {
      i = localCollator.compare(a(), localEntitlementGroup.a());
    }
    else if ("NAME".equals(paramString))
    {
      paramString = "GroupName";
      i = super.compare(paramObject, paramString);
    }
    else
    {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  private String a()
  {
    String str = null;
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("display name");
    localStringBuffer.append('_');
    localStringBuffer.append(this.locale.getLanguage());
    localStringBuffer.append('_');
    localStringBuffer.append(this.locale.getCountry());
    if (str == null)
    {
      this.props.setCurrentProperty(localStringBuffer.toString());
      str = this.props.getValueOfCurrentProperty();
    }
    if (str == null)
    {
      this.props.setCurrentProperty("display name");
      str = this.props.getValueOfCurrentProperty();
    }
    if (str == null) {
      str = getGroupName();
    }
    if (str == null) {
      str = "";
    }
    return str;
  }
  
  public int hashCode()
  {
    return this.groupId;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.EntitlementGroup
 * JD-Core Version:    0.7.0.1
 */