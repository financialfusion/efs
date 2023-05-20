package com.ffusion.tasks.ach;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyACHCompanyLimits
  extends BaseTask
  implements Task
{
  protected String companiesName = "ACHCOMPANIES";
  protected String groupId;
  protected String operationName;
  protected String objectType;
  protected int period;
  private static final String AB = "com.ffusion.util.logging.audit.task";
  private static final String AA = "AuditMessage_ModifyACHCompanyLimits_1";
  private static final String Az = "AuditMessage_ModifyACHCompanyLimits_2";
  private static final String Ay = "AuditMessage_ModifyACHCompanyLimits_3";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ACHCompanies localACHCompanies = (ACHCompanies)localHttpSession.getAttribute(this.companiesName);
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localACHCompanies == null)
    {
      this.error = 16505;
    }
    else
    {
      Iterator localIterator1 = localACHCompanies.iterator();
      while (localIterator1.hasNext())
      {
        ACHCompany localACHCompany = (ACHCompany)localIterator1.next();
        String str2 = (String)localHttpSession.getAttribute(localACHCompany.getCompanyID());
        if (!Currency.isValid(str2, localLocale))
        {
          this.error = 16117;
          break;
        }
        try
        {
          Entitlement localEntitlement = new Entitlement(this.operationName, this.objectType, localACHCompany.getCompanyID());
          Limit localLimit1 = new Limit();
          localLimit1.setEntitlement(localEntitlement);
          localLimit1.setPeriod(this.period);
          Limits localLimits = Entitlements.getGroupLimits(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), Integer.parseInt(this.groupId), localLimit1, null);
          Iterator localIterator2 = localLimits.iterator();
          Object[] arrayOfObject = null;
          LocalizableString localLocalizableString = null;
          Limit localLimit2 = null;
          if (localIterator2.hasNext())
          {
            localLimit2 = (Limit)localIterator2.next();
            if ((str2 == null) || (str2.length() <= 0))
            {
              Entitlements.deleteGroupLimit(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localLimit2);
              arrayOfObject = new Object[1];
              arrayOfObject[0] = localACHCompany.getCompanyID();
              localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_ModifyACHCompanyLimits_1", arrayOfObject);
            }
            else
            {
              localLimit2.setData(str2);
              localLimit2 = Entitlements.modifyGroupLimit(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localLimit2);
              arrayOfObject = new Object[2];
              arrayOfObject[0] = localACHCompany.getCompanyID();
              arrayOfObject[1] = str2;
              localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_ModifyACHCompanyLimits_2", arrayOfObject);
            }
          }
          else if ((str2 != null) && (str2.length() > 0))
          {
            localLimit2 = localLimit1;
            localLimit2.setLimitName(null);
            localLimit2.setGroupId(Integer.parseInt(this.groupId));
            localLimit2.setData(str2);
            localLimit2 = Entitlements.addGroupLimit(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localLimit2);
            arrayOfObject = new Object[2];
            arrayOfObject[0] = localACHCompany.getCompanyID();
            arrayOfObject[1] = str2;
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_ModifyACHCompanyLimits_3", arrayOfObject);
          }
          if (localLocalizableString != null)
          {
            String str3 = TrackingIDGenerator.GetNextID();
            try
            {
              Currency localCurrency = new Currency(str2, localLocale, localLocale.getISO3Country());
              Initialize.audit(localSecureUser, localLocalizableString, str3, 3225, localCurrency);
            }
            catch (Exception localException)
            {
              Initialize.audit(localSecureUser, localLocalizableString, str3, 3225);
            }
          }
          str1 = this.successURL;
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException, localHttpSession);
          str1 = this.serviceErrorURL;
        }
      }
    }
    return str1;
  }
  
  public final void setCompaniesInSessionName(String paramString)
  {
    this.companiesName = paramString;
  }
  
  public final String getCompaniesInSessionName()
  {
    return this.companiesName;
  }
  
  public void setGroupId(String paramString)
  {
    this.groupId = paramString;
  }
  
  public void setOperationName(String paramString)
  {
    this.operationName = paramString;
  }
  
  public void setObjectType(String paramString)
  {
    this.objectType = paramString;
  }
  
  public void setPeriod(String paramString)
  {
    try
    {
      this.period = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.ModifyACHCompanyLimits
 * JD-Core Version:    0.7.0.1
 */