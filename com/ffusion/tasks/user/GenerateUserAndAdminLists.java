package com.ffusion.tasks.user;

import com.ffusion.beans.business.Business;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementAdmins;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GenerateUserAndAdminLists
  implements UserTask
{
  protected String _entAdminsName;
  protected String _employeesName;
  protected String _userName;
  protected String _groupName;
  protected String _userAdminName;
  protected String _groupAdminName;
  protected String _adminIdsName;
  protected String _userIdsName;
  protected EntitlementGroup _bizEntitleGroup;
  protected int _error;
  protected String _successURL;
  protected String _nextURL;
  protected String _taskErrorURL = "TE";
  protected String _serviceErrorURL = "SE";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this._nextURL = this._successURL;
    this._error = 0;
    this._bizEntitleGroup = ((Business)localHttpSession.getAttribute("Business")).getEntitlementGroup();
    EntitlementAdmins localEntitlementAdmins = (EntitlementAdmins)localHttpSession.getAttribute(this._entAdminsName);
    BusinessEmployees localBusinessEmployees1 = (BusinessEmployees)localHttpSession.getAttribute(this._employeesName);
    ArrayList localArrayList1 = new ArrayList();
    try
    {
      localArrayList1 = getAllGroupsInBiz();
    }
    catch (CSILException localCSILException)
    {
      this._error = MapError.mapError(localCSILException);
      this._nextURL = this._serviceErrorURL;
    }
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    BusinessEmployees localBusinessEmployees2 = new BusinessEmployees(localLocale);
    ArrayList localArrayList2 = new ArrayList();
    BusinessEmployees localBusinessEmployees3 = new BusinessEmployees(localLocale);
    ArrayList localArrayList3 = new ArrayList();
    int j;
    Object localObject;
    int k;
    EntitlementAdmin localEntitlementAdmin;
    for (int i = 0; i < localBusinessEmployees1.size(); i++)
    {
      j = 0;
      localObject = (BusinessEmployee)localBusinessEmployees1.get(i);
      for (k = 0; k < localEntitlementAdmins.size(); k++)
      {
        localEntitlementAdmin = (EntitlementAdmin)localEntitlementAdmins.get(k);
        if (("USER".equals(localEntitlementAdmin.getGranteeMemberType())) && (Integer.toString(1).equals(localEntitlementAdmin.getGranteeMemberSubType())) && (((BusinessEmployee)localObject).getId().equals(localEntitlementAdmin.getGranteeMemberId())) && (((BusinessEmployee)localObject).getEntitlementGroupId() == localEntitlementAdmin.getGranteeGroupId()))
        {
          j = 1;
          break;
        }
      }
      if (j != 0) {
        localBusinessEmployees3.add(localObject);
      } else {
        localBusinessEmployees2.add(localObject);
      }
    }
    for (i = 0; i < localArrayList1.size(); i++)
    {
      j = 0;
      localObject = (EntitlementGroup)localArrayList1.get(i);
      for (k = 0; k < localEntitlementAdmins.size(); k++)
      {
        localEntitlementAdmin = (EntitlementAdmin)localEntitlementAdmins.get(k);
        if ((localEntitlementAdmin.getGranteeMemberType() == null) && (localEntitlementAdmin.getGranteeMemberSubType() == null) && (localEntitlementAdmin.getGranteeMemberId() == null) && (((EntitlementGroup)localObject).getGroupId() == localEntitlementAdmin.getGranteeGroupId()))
        {
          j = 1;
          break;
        }
      }
      ArrayList localArrayList4 = new ArrayList();
      localArrayList4.add(((EntitlementGroup)localObject).getGroupName());
      localArrayList4.add(((EntitlementGroup)localObject).getEntGroupType());
      localArrayList4.add(Integer.toString(((EntitlementGroup)localObject).getGroupId()));
      if (j != 0) {
        localArrayList3.add(localArrayList4);
      } else {
        localArrayList2.add(localArrayList4);
      }
    }
    localHttpSession.setAttribute(this._userName, localBusinessEmployees2);
    localHttpSession.setAttribute(this._groupName, localArrayList2);
    localHttpSession.setAttribute(this._userAdminName, localBusinessEmployees3);
    localHttpSession.setAttribute(this._groupAdminName, localArrayList3);
    return this._nextURL;
  }
  
  public ArrayList getAllGroupsInBiz()
    throws CSILException
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(this._bizEntitleGroup);
    EntitlementGroups localEntitlementGroups1 = Entitlements.getChildrenEntitlementGroups(this._bizEntitleGroup.getGroupId());
    for (int i = 0; i < localEntitlementGroups1.size(); i++)
    {
      EntitlementGroup localEntitlementGroup1 = localEntitlementGroups1.getGroup(i);
      localArrayList.add(localEntitlementGroup1);
      EntitlementGroups localEntitlementGroups2 = Entitlements.getChildrenEntitlementGroups(localEntitlementGroup1.getGroupId());
      for (int j = 0; j < localEntitlementGroups2.size(); j++)
      {
        EntitlementGroup localEntitlementGroup2 = localEntitlementGroups2.getGroup(j);
        localArrayList.add(localEntitlementGroup2);
      }
    }
    return localArrayList;
  }
  
  public void setEntAdminsName(String paramString)
  {
    this._entAdminsName = paramString;
  }
  
  public void setEmployeesName(String paramString)
  {
    this._employeesName = paramString;
  }
  
  public void setUserAdminName(String paramString)
  {
    this._userAdminName = paramString;
  }
  
  public void setGroupAdminName(String paramString)
  {
    this._groupAdminName = paramString;
  }
  
  public void setAdminIdsName(String paramString)
  {
    this._adminIdsName = paramString;
  }
  
  public void setUserIdsName(String paramString)
  {
    this._userIdsName = paramString;
  }
  
  public void setUserName(String paramString)
  {
    this._userName = paramString;
  }
  
  public void setGroupName(String paramString)
  {
    this._groupName = paramString;
  }
  
  public boolean setError(int paramInt)
  {
    this._error = paramInt;
    return false;
  }
  
  public String getError()
  {
    return String.valueOf(this._error);
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this._taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this._serviceErrorURL = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this._successURL = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GenerateUserAndAdminLists
 * JD-Core Version:    0.7.0.1
 */