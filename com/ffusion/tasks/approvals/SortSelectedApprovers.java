package com.ffusion.tasks.approvals;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsGroup;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SortSelectedApprovers
  extends BaseTask
  implements IApprovalsTask
{
  public static final String DEFAULT_SELECTED_ENTITY_PREFIX = "selectedEntity";
  private static final String aNP = "user";
  private static final String aNN = "group";
  private static final String aNK = "apprGroup";
  private static final String aNT = " (user)";
  private static final String aNJ = " (group)";
  private static final String aNM = " (approval group)";
  private static final int aNR = 0;
  private static final int aNO = 1;
  private static final int aNQ = 2;
  private static final int aNI = 3;
  private String aNL = "ApprovalsUsersCollection";
  private String aNS = "ApprovalsGroupsCollection";
  private String aNU = "ApprovalsApprovalsGroupsCollection";
  private String aNH = "selectedEntity";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = getSuccessURL();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = null;
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    ArrayList localArrayList3 = new ArrayList();
    ArrayList localArrayList4 = new ArrayList();
    ArrayList localArrayList5 = new ArrayList();
    ArrayList localArrayList6 = new ArrayList();
    HashMap localHashMap = new HashMap();
    this.error = 0;
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      str1 = getTaskErrorURL();
    }
    else
    {
      String str2;
      Object localObject1;
      Object localObject2;
      for (int i = 0;; i++)
      {
        str2 = this.aNH + i;
        localObject1 = (String)localHttpSession.getAttribute(str2);
        localObject2 = null;
        if (localObject1 == null) {
          break;
        }
        try
        {
          localObject2 = ad((String)localObject1);
        }
        catch (Exception localException)
        {
          str1 = getTaskErrorURL();
          this.error = 31004;
          break;
        }
        localObject1 = localObject2[3] + '-' + (String)localObject1;
        if ("user".equals(localObject2[0])) {
          localArrayList4.add(localObject1);
        } else if ("group".equals(localObject2[0])) {
          localArrayList5.add(localObject1);
        } else if ("apprGroup".equals(localObject2[0])) {
          localArrayList6.add(localObject1);
        }
        localHashMap.put(localObject1, localObject2);
        localHttpSession.removeAttribute(str2);
      }
      if (this.error == 0) {
        try
        {
          Collections.sort(localArrayList4);
          Collections.sort(localArrayList5);
          Collections.sort(localArrayList6);
          Iterator localIterator = localArrayList4.iterator();
          while (localIterator.hasNext())
          {
            str2 = (String)localIterator.next();
            localObject1 = (String[])localHashMap.get(str2);
            localObject2 = new EntitlementGroupMember();
            ((EntitlementGroupMember)localObject2).setId(localObject1[2]);
            ((EntitlementGroupMember)localObject2).setMemberType("USER");
            ((EntitlementGroupMember)localObject2).setMemberSubType(localObject1[1]);
            localObject2 = Entitlements.getMember((EntitlementGroupMember)localObject2);
            localArrayList1.add(localObject2);
          }
          localIterator = localArrayList5.iterator();
          while (localIterator.hasNext())
          {
            str2 = (String)localIterator.next();
            localObject1 = (String[])localHashMap.get(str2);
            localObject2 = Entitlements.getEntitlementGroup(Integer.parseInt(localObject1[2]));
            localArrayList2.add(localObject2);
          }
          localIterator = localArrayList6.iterator();
          while (localIterator.hasNext())
          {
            str2 = (String)localIterator.next();
            localObject1 = (String[])localHashMap.get(str2);
            localObject2 = new ApprovalsGroup(localSecureUser.getLocale());
            ((ApprovalsGroup)localObject2).setApprovalsGroupID(Integer.parseInt(localObject1[2]));
            ((ApprovalsGroup)localObject2).setGroupName(localObject1[3]);
            localArrayList3.add(localObject2);
          }
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str1 = getServiceErrorURL();
        }
        catch (NumberFormatException localNumberFormatException)
        {
          this.error = 31004;
          str1 = getTaskErrorURL();
        }
      }
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute(this.aNL, localArrayList1);
      localHttpSession.setAttribute(this.aNS, localArrayList2);
      localHttpSession.setAttribute(this.aNU, localArrayList3);
    }
    return str1;
  }
  
  public void setUserCollectionName(String paramString)
  {
    this.aNL = ((paramString == null) || (paramString.length() == 0) ? "ApprovalsUsersCollection" : paramString);
  }
  
  public String getUserCollectionName()
  {
    return this.aNL;
  }
  
  public void setGroupCollectionName(String paramString)
  {
    this.aNS = ((paramString == null) || (paramString.length() == 0) ? "ApprovalsGroupsCollection" : paramString);
  }
  
  public String getGroupCollectionName()
  {
    return this.aNS;
  }
  
  public void setApprovalGroupCollectionName(String paramString)
  {
    this.aNU = ((paramString == null) || (paramString.length() == 0) ? "ApprovalsApprovalsGroupsCollection" : paramString);
  }
  
  public String getApprovalGroupCollectionName()
  {
    return this.aNU;
  }
  
  public void setSelectedEntityPrefix(String paramString)
  {
    this.aNH = ((paramString == null) || (paramString.length() == 0) ? "selectedEntity" : paramString);
  }
  
  public String getSelectedEntityPrefix()
  {
    return this.aNH;
  }
  
  private String[] ad(String paramString)
    throws Exception
  {
    String[] arrayOfString = new String[4];
    String str1 = paramString.trim();
    int i = 0;
    int j = -1;
    int k = 0;
    String str2 = null;
    j = str1.indexOf('-', i);
    arrayOfString[(k++)] = str1.substring(i, j);
    i = j + 1;
    j = str1.indexOf('-', i);
    arrayOfString[(k++)] = str1.substring(i, j);
    i = j + 1;
    j = str1.indexOf('-', i);
    arrayOfString[(k++)] = str1.substring(i, j);
    i = j + 1;
    if ("user".equals(arrayOfString[0])) {
      str2 = " (user)";
    } else if ("group".equals(arrayOfString[0])) {
      str2 = " (group)";
    } else if ("apprGroup".equals(arrayOfString[0])) {
      str2 = " (approval group)";
    }
    j = str1.lastIndexOf(str2);
    arrayOfString[(k++)] = str1.substring(i, j);
    return arrayOfString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.SortSelectedApprovers
 * JD-Core Version:    0.7.0.1
 */