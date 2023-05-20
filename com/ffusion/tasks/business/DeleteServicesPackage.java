package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.business.ServiceFeature;
import com.ffusion.beans.business.ServiceFeatures;
import com.ffusion.beans.business.ServicesPackage;
import com.ffusion.beans.business.ServicesPackages;
import com.ffusion.beans.business.TransactionLimits;
import com.ffusion.beans.user.Users;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyLists;
import com.ffusion.csil.core.Business;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.LocalizableProperty;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteServicesPackage
  extends BaseTask
  implements BusinessTask
{
  int hq;
  protected String servicePackagesCollectionName = "ServicesPackages";
  protected String userType = "Business";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    TransactionLimits localTransactionLimits = null;
    ServiceFeatures localServiceFeatures = null;
    try
    {
      HashMap localHashMap = null;
      Object localObject1;
      if (this.userType.equalsIgnoreCase("Business"))
      {
        localObject1 = Business.getBusinessesByServicesPackage(localSecureUser, this.hq, localHashMap);
        if (((Businesses)localObject1).size() > 0)
        {
          this.error = 4124;
          localHttpSession.setAttribute("BusinessesByServicesPackage", localObject1);
          return str;
        }
        localHttpSession.removeAttribute("BusinessesByServicesPackage");
      }
      else
      {
        localObject1 = UserAdmin.getUsersByServicesPackage(localSecureUser, this.hq, localHashMap);
        if (((Users)localObject1).size() > 0)
        {
          this.error = 4124;
          localHttpSession.setAttribute("UsersByServicesPackage", localObject1);
          return str;
        }
        localHttpSession.removeAttribute("UsersByServicesPackage");
      }
      try
      {
        localTransactionLimits = Business.getTXLimitsByGroupId(localSecureUser, this.hq, "per package", localHashMap);
        localServiceFeatures = Business.getServiceFeaturesByGroupId(localSecureUser, this.hq, localHashMap);
      }
      catch (CSILException localCSILException2) {}
      Business.deleteServicesPackage(localSecureUser, this.hq, localHashMap);
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      ServicesPackages localServicesPackages = (ServicesPackages)localHttpSession.getAttribute(this.servicePackagesCollectionName);
      if (localServicesPackages != null)
      {
        ServicesPackage localServicesPackage = localServicesPackages.getById(this.hq);
        if (localServicesPackage != null)
        {
          HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 4, Integer.toString(this.hq));
          localServicesPackage.logDeletion(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
          Object localObject2;
          Object localObject3;
          Object localObject4;
          if (localTransactionLimits != null) {
            try
            {
              LimitTypePropertyLists localLimitTypePropertyLists = Entitlements.getLimitTypesWithProperties(new HashMap());
              localObject2 = localTransactionLimits.getLimits(localServicesPackage.getEntitlementGroup(), localLimitTypePropertyLists);
              localObject3 = localTransactionLimits.iterator();
              while (((Iterator)localObject3).hasNext())
              {
                localObject4 = (Limit)((Iterator)localObject3).next();
                localHistoryTracker.logLimitDelete((Limit)localObject4, localLimitTypePropertyLists);
              }
            }
            catch (CSILException localCSILException3)
            {
              DebugLog.log(Level.SEVERE, "Add History of transaction limits failed for DeleteServicesPackage: " + localCSILException3.toString());
            }
          }
          if (localServiceFeatures != null)
          {
            LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.history.resources", "SELECTED", null);
            localObject2 = localServiceFeatures.iterator();
            while (((Iterator)localObject2).hasNext())
            {
              localObject3 = (ServiceFeature)((Iterator)localObject2).next();
              localObject4 = null;
              try
              {
                EntitlementTypePropertyList localEntitlementTypePropertyList = Entitlements.getEntitlementTypeWithProperties(((ServiceFeature)localObject3).getFeatureName());
                if (localEntitlementTypePropertyList != null) {
                  localObject4 = localEntitlementTypePropertyList.getPropertiesMap();
                } else {
                  localObject4 = new HashMap();
                }
              }
              catch (CSILException localCSILException4)
              {
                localObject4 = new HashMap();
              }
              LocalizableProperty localLocalizableProperty = new LocalizableProperty("display name", (HashMap)localObject4, ((ServiceFeature)localObject3).getFeatureName());
              if (((ServiceFeature)localObject3).isSelected())
              {
                localHistoryTracker.logChange(ServiceFeature.BEAN_NAME, "SERVICE_FEATURE", localLocalizableString, null, localLocalizableProperty);
                if ((((ServiceFeature)localObject3).getChargable()) && (!((ServiceFeature)localObject3).getServiceCharge().equals(""))) {
                  localHistoryTracker.logChange(ServiceFeature.BEAN_NAME, "SERVICE_CHARGE", ((ServiceFeature)localObject3).getServiceCharge(), null, localLocalizableProperty);
                }
              }
            }
          }
          try
          {
            HistoryAdapter.addHistory(localHistoryTracker.getHistories());
          }
          catch (ProfileException localProfileException)
          {
            DebugLog.log(Level.SEVERE, "Add History failed for DeleteServicesPackage: " + localProfileException.toString());
          }
        }
        localServicesPackages.removeById(this.hq);
        localHttpSession.setAttribute(this.servicePackagesCollectionName, localServicesPackages);
      }
    }
    return str;
  }
  
  public void setServicesPackageId(String paramString)
  {
    try
    {
      this.hq = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setUserType(String paramString)
  {
    this.userType = paramString;
  }
  
  public void setServicePackagesCollectionName(String paramString)
  {
    this.servicePackagesCollectionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.DeleteServicesPackage
 * JD-Core Version:    0.7.0.1
 */