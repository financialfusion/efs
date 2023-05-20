package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.ServiceFeature;
import com.ffusion.beans.business.ServiceFeatures;
import com.ffusion.beans.business.ServicesPackage;
import com.ffusion.beans.business.ServicesPackages;
import com.ffusion.beans.business.TransactionLimit;
import com.ffusion.beans.business.TransactionLimits;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyLists;
import com.ffusion.csil.core.Business;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyServicesPackage
  extends AddServicesPackage
{
  protected boolean _autoEntitleBusinesses = true;
  boolean h0 = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.hW = 0;
    String str = this.hR;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    boolean bool = true;
    if (this.initFlag) {
      bool = init(localHttpSession);
    }
    if (bool)
    {
      this.hU = ((ServicesPackages)localHttpSession.getAttribute(this.servicePackagesCollectionName));
      if ((this.hU == null) || (this.hU.size() == 0))
      {
        this.hW = 4115;
        return this.hS;
      }
      if (this.authenticationFlag) {
        processAuthenticationSettings(paramHttpServletRequest);
      }
      if (validateInput(localHttpSession))
      {
        if (this.hZ)
        {
          this.hZ = false;
          try
          {
            HashMap localHashMap = new HashMap();
            SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
            setServiceFeatures((ServiceFeatures)localHttpSession.getAttribute("ServicesPackageFeatures"));
            if (this.h0)
            {
              localObject1 = getServiceFeatures().iterator();
              while (((Iterator)localObject1).hasNext())
              {
                localObject2 = (ServiceFeature)((Iterator)localObject1).next();
                if (localHttpSession.getAttribute(((ServiceFeature)localObject2).getFeatureName()) != null)
                {
                  ((ServiceFeature)localObject2).setSelected("1");
                  localHttpSession.removeAttribute(((ServiceFeature)localObject2).getFeatureName());
                }
                else
                {
                  ((ServiceFeature)localObject2).setSelected("0");
                }
              }
            }
            setLimits();
            setChargeableFeatures((ServiceFeatures)localHttpSession.getAttribute(this.chargeableFeaturesCollectionName));
            Object localObject1 = (ServicesPackage)localHttpSession.getAttribute("OldServicesPackage");
            if (localObject1 == null)
            {
              this.hW = 4116;
              return this.hS;
            }
            Object localObject2 = Business.getServiceFeaturesByGroupId(localSecureUser, ((ServicesPackage)localObject1).getIdValue(), null);
            ((ServicesPackage)localObject1).setServiceFeatures((ServiceFeatures)localObject2);
            ServiceFeatures localServiceFeatures = Business.getChargeableServiceFeaturesByGroupId(localSecureUser, ((ServicesPackage)localObject1).getIdValue(), null);
            ((ServicesPackage)localObject1).setChargeableFeatures(localServiceFeatures);
            Object localObject3 = Business.getTXLimitsByGroupId(localSecureUser, ((ServicesPackage)localObject1).getIdValue(), "per package", null);
            ((ServicesPackage)localObject1).setTransactionLimits((TransactionLimits)localObject3);
            localObject2 = getTransactionLimits();
            for (int i = 0; i < ((TransactionLimits)localObject2).size(); i++) {
              try
              {
                localObject3 = (TransactionLimit)((TransactionLimits)localObject2).get(i);
                if (((TransactionLimit)localObject3).getData().length() > 30)
                {
                  this.hW = 129;
                  str = this.hS;
                }
              }
              catch (Exception localException) {}
            }
            if (this.hW == 0) {
              Business.modifyServicesPackage(localSecureUser, this, (ServicesPackage)localObject1, this._autoEntitleBusinesses, localHashMap);
            }
          }
          catch (CSILException localCSILException)
          {
            this.hW = MapError.mapError(localCSILException);
            str = this.hT;
          }
          if (this.hW == 0)
          {
            localHttpSession.setAttribute(this.hQ, this);
            this.hU.removeById(getIdValue());
            this.hU.add(this);
            localHttpSession.removeAttribute("OldServicesPackage");
          }
        }
      }
      else {
        str = this.hS;
      }
    }
    else
    {
      str = this.hS;
    }
    return str;
  }
  
  public boolean init(HttpSession paramHttpSession)
  {
    ServicesPackage localServicesPackage1 = (ServicesPackage)paramHttpSession.getAttribute("ServicesPackage");
    if (localServicesPackage1 == null)
    {
      this.hW = 4116;
      return false;
    }
    set(localServicesPackage1);
    ServicesPackage localServicesPackage2 = new ServicesPackage();
    localServicesPackage2.set(localServicesPackage1);
    paramHttpSession.setAttribute("OldServicesPackage", localServicesPackage2);
    TransactionLimits localTransactionLimits1 = (TransactionLimits)paramHttpSession.getAttribute("ServicesPackageTransactionLimits");
    if (localTransactionLimits1 == null)
    {
      this.hW = 4125;
      return false;
    }
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    LimitTypePropertyLists localLimitTypePropertyLists = Business.getLimitTypeProps();
    TransactionLimits localTransactionLimits2 = new TransactionLimits(localLimitTypePropertyLists, "per package", this.locale);
    if (!this.usePeriodicLimits)
    {
      removePeriodicLimits(localTransactionLimits1);
      removePeriodicLimits(localTransactionLimits2);
    }
    localTransactionLimits2.set(localTransactionLimits1);
    setTransactionLimits(localTransactionLimits2);
    paramHttpSession.setAttribute("ServicesPackageTransactionLimits", localTransactionLimits2);
    this.initFlag = false;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if (this.usePeriodicLimits) {
      initializeLimits(localSecureUser, localLimitTypePropertyLists);
    }
    return true;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setAutoEntitleBusinesses(String paramString)
  {
    try
    {
      this._autoEntitleBusinesses = Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException) {}
  }
  
  public String getAutoEntitleBusinesses()
  {
    return String.valueOf(this._autoEntitleBusinesses);
  }
  
  public void setEditFeatures(String paramString)
  {
    this.h0 = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.ModifyServicesPackage
 * JD-Core Version:    0.7.0.1
 */