package com.ffusion.efs.tasks.entitlements;

import javax.servlet.http.HttpSession;

public class SignOff
  extends com.ffusion.tasks.SignOff
  implements Task
{
  protected void removeObjects(HttpSession paramHttpSession)
  {
    super.removeObjects(paramHttpSession);
    paramHttpSession.removeAttribute("Entitlement_EntitlementGroups");
    paramHttpSession.removeAttribute("Entitlement_EntitlementGroup");
    paramHttpSession.removeAttribute("Entitlement_Limits");
    paramHttpSession.removeAttribute("Entitlement_Limit");
    paramHttpSession.removeAttribute("Entitlement_Restricted_list");
    paramHttpSession.removeAttribute("Entitlement_CanAdminister");
    paramHttpSession.removeAttribute("Entitlement_Entitlements");
    paramHttpSession.removeAttribute("Entitlement_ArrayList");
    paramHttpSession.removeAttribute("Entitlement_HashMap");
    paramHttpSession.removeAttribute("Entitlement_Group_Member");
    paramHttpSession.removeAttribute("Entitlement_Group_Members");
    paramHttpSession.removeAttribute("Entitlement_Group_Num_Members");
    paramHttpSession.removeAttribute("Entitlement_Report");
    paramHttpSession.removeAttribute("Entitlement_Report_Results");
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.SignOff
 * JD-Core Version:    0.7.0.1
 */