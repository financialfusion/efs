package com.ffusion.tasks.admin;

import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetRestrictedACHCompaniesForGroup
  extends BaseTask
  implements AdminTask
{
  protected String _achCompaniesName = "ACHCOMPANIES";
  protected String _groupACHCompaniesName = "GroupACHCompanies";
  private int acR = -1;
  private String acQ = "";
  private int acS = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    ACHCompanies localACHCompanies1 = (ACHCompanies)localHttpSession.getAttribute(this._achCompaniesName);
    if (localACHCompanies1 == null)
    {
      this.error = 16505;
      str1 = this.taskErrorURL;
    }
    else
    {
      ACHCompanies localACHCompanies2 = (ACHCompanies)localACHCompanies1.clone();
      try
      {
        com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = null;
        if (this.acQ.length() > 0)
        {
          localObject1 = new EntitlementGroupMember();
          ((EntitlementGroupMember)localObject1).setEntitlementGroupId(this.acR);
          ((EntitlementGroupMember)localObject1).setId(this.acQ);
          ((EntitlementGroupMember)localObject1).setMemberType("USER");
          ((EntitlementGroupMember)localObject1).setMemberSubType(Integer.toString(this.acS));
          localEntitlements = com.ffusion.csil.core.Entitlements.getCumulativeEntitlements((EntitlementGroupMember)localObject1);
        }
        else
        {
          localEntitlements = com.ffusion.csil.core.Entitlements.getCumulativeEntitlements(this.acR);
        }
        Object localObject1 = new HashMap(localEntitlements.size());
        Iterator localIterator = localEntitlements.iterator();
        String str2 = null;
        Object localObject2;
        while (localIterator.hasNext())
        {
          localObject2 = (Entitlement)localIterator.next();
          str2 = ((Entitlement)localObject2).getObjectType();
          if ((str2 != null) && (str2.equals("ACHCompany")) && (((Entitlement)localObject2).getOperationName().equals("ACHBatch"))) {
            ((HashMap)localObject1).put(((Entitlement)localObject2).getObjectId(), localObject2);
          }
        }
        if (((HashMap)localObject1).size() == 0)
        {
          localHttpSession.setAttribute(this._groupACHCompaniesName, localACHCompanies2);
        }
        else
        {
          localObject2 = localACHCompanies2.iterator();
          while (((Iterator)localObject2).hasNext())
          {
            ACHCompany localACHCompany = (ACHCompany)((Iterator)localObject2).next();
            if (((HashMap)localObject1).containsKey(localACHCompany.getCompanyID())) {
              ((Iterator)localObject2).remove();
            }
          }
        }
        localHttpSession.setAttribute(this._groupACHCompaniesName, localACHCompanies2);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        this.error = 4512;
        str1 = this.serviceErrorURL;
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
      finally
      {
        this.acR = -1;
        this.acQ = "";
        this.acS = -1;
      }
    }
    return str1;
  }
  
  public void setGroupID(String paramString)
  {
    this.acR = Integer.parseInt(paramString);
  }
  
  public void setMemberID(String paramString)
  {
    this.acQ = paramString;
  }
  
  public void setUserType(String paramString)
  {
    this.acS = Integer.parseInt(paramString);
  }
  
  public void setGroupACHCompaniesInSessionName(String paramString)
  {
    this._groupACHCompaniesName = paramString;
  }
  
  public void setACHCompaniesInSessionName(String paramString)
  {
    this._achCompaniesName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetRestrictedACHCompaniesForGroup
 * JD-Core Version:    0.7.0.1
 */