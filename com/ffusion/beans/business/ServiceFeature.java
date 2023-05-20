package com.ffusion.beans.business;

import com.ffusion.beans.ExtendABean;
import java.io.Serializable;
import java.util.Locale;

public class ServiceFeature
  extends ExtendABean
  implements BusinessDefines, Serializable
{
  private String h8;
  private String h6;
  private String h4;
  private String h1;
  private String h2;
  private String h7;
  private boolean h3 = false;
  private boolean h5 = false;
  public static final String DEFAULT_SERVICE_CHARGE = "";
  public static final String SERVICE_FEATURE = "SERVICE_FEATURE";
  public static final String SERVICE_CHARGE = "SERVICE_CHARGE";
  public static final String SELECTED_HISTORY = "SELECTED";
  public static final String UNSELECTED_HISTORY = "UNSELECTED";
  public static final String BEAN_NAME = ServiceFeature.class.getName();
  
  public ServiceFeature()
  {
    setServiceCharge("");
  }
  
  public ServiceFeature(Locale paramLocale)
  {
    setLocale(paramLocale);
    setServiceCharge("");
  }
  
  public String getFeatureName()
  {
    return this.h8;
  }
  
  public void setFeatureName(String paramString)
  {
    this.h8 = paramString;
  }
  
  public String getDisplayName()
  {
    return this.h6;
  }
  
  public void setDisplayName(String paramString)
  {
    this.h6 = paramString;
  }
  
  public void setFeatureCategory(String paramString)
  {
    this.h4 = paramString;
  }
  
  public String getFeatureCategory()
  {
    return this.h4;
  }
  
  public void setDescription(String paramString)
  {
    this.h7 = paramString;
  }
  
  public String getDescription()
  {
    return this.h7;
  }
  
  public void setHide(boolean paramBoolean)
  {
    this.h3 = paramBoolean;
  }
  
  public boolean getHide()
  {
    return this.h3;
  }
  
  public void setChargable(boolean paramBoolean)
  {
    this.h5 = paramBoolean;
  }
  
  public boolean getChargable()
  {
    return this.h5;
  }
  
  public void setSelected(String paramString)
  {
    this.h2 = paramString;
  }
  
  public String getSelected()
  {
    return this.h2;
  }
  
  public void setServiceCharge(String paramString)
  {
    if (paramString == null) {
      this.h1 = "";
    } else {
      this.h1 = paramString;
    }
  }
  
  public String getServiceCharge()
  {
    return this.h1;
  }
  
  public void set(ServiceFeature paramServiceFeature)
  {
    setFeatureName(paramServiceFeature.getFeatureName());
    setDisplayName(paramServiceFeature.getDisplayName());
    setFeatureCategory(paramServiceFeature.getFeatureCategory());
    setServiceCharge(paramServiceFeature.getServiceCharge());
    setSelected(paramServiceFeature.getSelected());
    setDescription(paramServiceFeature.getDescription());
    setHide(paramServiceFeature.getHide());
    setChargable(paramServiceFeature.getChargable());
  }
  
  public boolean isSelected()
  {
    boolean bool = false;
    if (this.h2 != null) {
      bool = this.h2.equals("1");
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.business.ServiceFeature
 * JD-Core Version:    0.7.0.1
 */