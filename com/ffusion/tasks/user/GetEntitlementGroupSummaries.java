package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetEntitlementGroupSummaries
  extends BaseTask
  implements UserTask
{
  private int w7 = 4;
  private EntitlementGroupMember xb;
  private static final int w9 = -1;
  private static final int w8 = -2;
  private static final int xa = 0;
  private static final int xd = 1;
  private static final int xc = 2;
  private static final int w5 = 3;
  private static final int w6 = 4;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    this.xb = EntitlementsUtil.getEntitlementGroupMember((SecureUser)localHttpSession.getAttribute("SecureUser"));
    EntitlementGroups localEntitlementGroups2 = new EntitlementGroups();
    ArrayList localArrayList = new ArrayList();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    try
    {
      EntitlementGroups localEntitlementGroups1 = Entitlements.getGroupsAdministeredBy(this.xb);
      int[] arrayOfInt = new int[localEntitlementGroups1.size()];
      for (int i = 0; i < arrayOfInt.length; i++) {
        arrayOfInt[i] = -1;
      }
      jdMethod_for(localArrayList, localEntitlementGroups1, localEntitlementGroups2, localEntitlementGroups1, arrayOfInt, 0, localLocale);
      localHttpSession.setAttribute("GroupSummaries", localArrayList);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  private void jdMethod_for(ArrayList paramArrayList, EntitlementGroups paramEntitlementGroups1, EntitlementGroups paramEntitlementGroups2, EntitlementGroups paramEntitlementGroups3, int[] paramArrayOfInt, int paramInt, Locale paramLocale)
    throws CSILException
  {
    for (int i = 0; i < paramEntitlementGroups1.size(); i++)
    {
      EntitlementGroup localEntitlementGroup = paramEntitlementGroups1.getGroup(i);
      ArrayList localArrayList1;
      int k;
      if (paramEntitlementGroups2.contains(localEntitlementGroup))
      {
        localArrayList1 = paramEntitlementGroups3.indexOf(localEntitlementGroup);
        int j = paramArrayOfInt[localArrayList1];
        if (j != -1)
        {
          k = paramArrayList.size();
          for (int m = j + 1; m < paramArrayOfInt.length; m++) {
            if (paramArrayOfInt[m] != -2)
            {
              k = paramArrayOfInt[m];
              break;
            }
          }
          int n;
          for (m = j; m < k; m++)
          {
            localArrayList3 = (ArrayList)paramArrayList.get(j);
            StringBuffer localStringBuffer3 = new StringBuffer();
            for (n = 0; n < paramInt * this.w7; n++) {
              localStringBuffer3.append("&nbsp;");
            }
            localStringBuffer3.append(localArrayList3.get(0));
            localArrayList3.set(0, localStringBuffer3);
            paramArrayList.add(localArrayList3);
            paramArrayList.remove(j);
          }
          m = 0;
          ArrayList localArrayList3 = localArrayList1;
          for (ArrayList localArrayList4 = localArrayList1 + 1; localArrayList4 < paramArrayOfInt.length; localArrayList4++) {
            if (paramArrayOfInt[localArrayList4] != -2)
            {
              if (paramArrayOfInt[localArrayList4] == -1) {
                break;
              }
              n = paramArrayOfInt[localArrayList4] - paramArrayOfInt[localArrayList3];
              if (m == 0) {
                paramArrayOfInt[localArrayList4] = paramArrayOfInt[localArrayList3];
              } else {
                paramArrayOfInt[localArrayList4] -= m;
              }
              m = n;
              localArrayList3 = localArrayList4;
            }
          }
          paramArrayOfInt[localArrayList1] = -2;
        }
      }
      else
      {
        paramEntitlementGroups2.add(localEntitlementGroup);
        if ((paramEntitlementGroups1.equals(paramEntitlementGroups3)) && (paramEntitlementGroups3.contains(localEntitlementGroup)))
        {
          localArrayList1 = paramEntitlementGroups3.indexOf(localEntitlementGroup);
          paramArrayOfInt[localArrayList1] = paramArrayList.size();
        }
        ArrayList localArrayList2 = new ArrayList(4);
        StringBuffer localStringBuffer1 = new StringBuffer();
        for (k = 0; k < paramInt * this.w7; k++) {
          localStringBuffer1.append("&nbsp;");
        }
        localArrayList2.add(localStringBuffer1.toString());
        StringBuffer localStringBuffer2 = new StringBuffer();
        localStringBuffer2.append(localEntitlementGroup.getGroupName() + " (" + ReportConsts.getEntGroupType(localEntitlementGroup.getEntGroupType(), paramLocale) + ")");
        localArrayList2.add(localStringBuffer2.toString());
        if (Entitlements.canAdminister(new EntitlementAdmin(this.xb, localEntitlementGroup.getGroupId()))) {
          localArrayList2.add(String.valueOf(localEntitlementGroup.getGroupId()));
        } else {
          localArrayList2.add("0");
        }
        localArrayList2.add(String.valueOf(Entitlements.getNumMembers(localEntitlementGroup.getGroupId())));
        paramArrayList.add(localArrayList2);
        EntitlementGroups localEntitlementGroups = Entitlements.getChildrenEntitlementGroups(localEntitlementGroup.getGroupId());
        if (localEntitlementGroups.size() > 0) {
          jdMethod_for(paramArrayList, localEntitlementGroups, paramEntitlementGroups2, paramEntitlementGroups3, paramArrayOfInt, paramInt + 1, paramLocale);
        }
      }
    }
  }
  
  public void setNumSpaces(String paramString)
  {
    this.w7 = Integer.parseInt(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetEntitlementGroupSummaries
 * JD-Core Version:    0.7.0.1
 */