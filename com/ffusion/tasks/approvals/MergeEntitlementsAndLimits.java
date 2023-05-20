package com.ffusion.tasks.approvals;

import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyList;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyLists;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MergeEntitlementsAndLimits
  extends BaseTask
  implements IApprovalsTask
{
  public static final String PROP_NAME_IS_LIMITABLE = "is limitable";
  public static final String PROP_NAME_CREDIT_LIMIT_OP_NAME = "credit limit operation name";
  public static final String PROP_NAME_DEBIT_LIMIT_OP_NAME = "debit limit operation name";
  public static final String PROP_VALUE_TRUE = "true";
  private String aNF;
  private String aNE;
  private boolean aNG;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = getSuccessURL();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (this.aNE == null)
    {
      this.error = 31005;
      str1 = getTaskErrorURL();
    }
    else if (this.aNF == null)
    {
      this.error = 31006;
      str1 = getTaskErrorURL();
    }
    else
    {
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = (EntitlementTypePropertyLists)localHttpSession.getAttribute(this.aNE);
      LimitTypePropertyLists localLimitTypePropertyLists = (LimitTypePropertyLists)localHttpSession.getAttribute(this.aNF);
      if (localEntitlementTypePropertyLists == null)
      {
        this.error = 31007;
        str1 = getTaskErrorURL();
      }
      else if (localLimitTypePropertyLists == null)
      {
        this.error = 31008;
        str1 = getTaskErrorURL();
      }
      else
      {
        Iterator localIterator = localEntitlementTypePropertyLists.iterator();
        while (localIterator.hasNext())
        {
          EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
          LimitTypePropertyList localLimitTypePropertyList = null;
          if (this.aNG)
          {
            String str2 = null;
            String str3 = null;
            String str4 = null;
            if (localEntitlementTypePropertyList.isPropertySet("display name")) {
              str3 = localEntitlementTypePropertyList.getPropertyValue("display name", 0);
            } else {
              str3 = localEntitlementTypePropertyList.getOperationName();
            }
            if (localEntitlementTypePropertyList.getOperationName().equals("Overall-ACH Payment Approval"))
            {
              str4 = "Overall-ACH Payment Approval (Credit)";
            }
            else
            {
              str2 = localEntitlementTypePropertyList.getPropertyValue("display parent", 0);
              str4 = EntitlementsUtil.getACHLimitName(str2, str3, false);
            }
            localLimitTypePropertyList = localLimitTypePropertyLists.getByOperationName(str4);
            if (localLimitTypePropertyList != null)
            {
              localEntitlementTypePropertyList.addProperty("is limitable", "true");
              localEntitlementTypePropertyList.addProperty("credit limit operation name", localLimitTypePropertyList.getOperationName());
            }
            if (localEntitlementTypePropertyList.getOperationName().equals("Overall-ACH Payment Approval"))
            {
              str4 = "Overall-ACH Payment Approval (Debit)";
            }
            else
            {
              str2 = localEntitlementTypePropertyList.getPropertyValue("display parent", 0);
              str4 = EntitlementsUtil.getACHLimitName(str2, str3, true);
            }
            localLimitTypePropertyList = localLimitTypePropertyLists.getByOperationName(str4);
            if (localLimitTypePropertyList != null)
            {
              if (!localEntitlementTypePropertyList.isPropertySet("is limitable")) {
                localEntitlementTypePropertyList.addProperty("is limitable", "true");
              }
              localEntitlementTypePropertyList.addProperty("debit limit operation name", localLimitTypePropertyList.getOperationName());
            }
          }
          else
          {
            localLimitTypePropertyList = localLimitTypePropertyLists.getByOperationName(localEntitlementTypePropertyList.getOperationName());
            if (localLimitTypePropertyList != null) {
              localEntitlementTypePropertyList.addProperty("is limitable", "true");
            }
          }
        }
      }
    }
    return str1;
  }
  
  public void setLimitsCollectionName(String paramString)
  {
    this.aNF = ((paramString == null) || (paramString.length() == 0) ? null : paramString);
  }
  
  public void setEntitlementsCollectionName(String paramString)
  {
    this.aNE = ((paramString == null) || (paramString.length() == 0) ? null : paramString);
  }
  
  public void setMergeForACH(String paramString)
  {
    Boolean localBoolean = new Boolean(paramString);
    this.aNG = localBoolean.booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.MergeEntitlementsAndLimits
 * JD-Core Version:    0.7.0.1
 */