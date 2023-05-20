package com.ffusion.csil.beans.entitlements;

import com.ffusion.util.beans.ExtendABean;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class EntitlementGroupProperties
  extends ExtendABean
  implements Serializable
{
  private String U = null;
  private Properties T;
  
  public EntitlementGroupProperties(Properties paramProperties)
  {
    this.T = paramProperties;
  }
  
  public Object clone()
  {
    EntitlementGroupProperties localEntitlementGroupProperties = (EntitlementGroupProperties)super.clone();
    if (this.T == null) {
      return localEntitlementGroupProperties;
    }
    localEntitlementGroupProperties.T = new Properties();
    Iterator localIterator = this.T.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localEntitlementGroupProperties.T.setProperty(str, this.T.getProperty(str));
    }
    return localEntitlementGroupProperties;
  }
  
  public Enumeration getPropertyNames()
  {
    return this.T.propertyNames();
  }
  
  public void setCurrentProperty(String paramString)
  {
    this.U = paramString;
  }
  
  public String getCurrentProperty()
  {
    return this.U;
  }
  
  public void setValueOfCurrentProperty(String paramString)
  {
    if (this.U != null) {
      this.T.setProperty(this.U, paramString);
    }
  }
  
  public String getValueOfCurrentProperty()
  {
    if (this.U == null) {
      return "";
    }
    return this.T.getProperty(this.U);
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.EntitlementGroupProperties
 * JD-Core Version:    0.7.0.1
 */