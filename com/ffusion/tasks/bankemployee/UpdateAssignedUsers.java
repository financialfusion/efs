package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.user.UserTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateAssignedUsers
  extends BaseTask
  implements BankEmployeeTask, UserTask
{
  private String t8;
  private static final String t7 = "ASSIGNED_USER_ADDED";
  private static final String t6 = "ASSIGNED_USER_REMOVED";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    Users localUsers1 = (Users)localHttpSession.getAttribute("AssignedUsers");
    Users localUsers2 = (Users)localHttpSession.getAttribute("TempAssignedUsers");
    if ((localUsers1 == null) || (localUsers2 == null))
    {
      this.error = 5508;
      str1 = this.taskErrorURL;
    }
    else
    {
      Iterator localIterator1 = localUsers1.iterator();
      Object localObject2;
      Object localObject3;
      Object localObject5;
      while (localIterator1.hasNext())
      {
        int i = 0;
        User localUser = (User)localIterator1.next();
        localObject2 = localUser.getId();
        localObject3 = localUsers2.iterator();
        Object localObject4;
        while (((Iterator)localObject3).hasNext())
        {
          localObject4 = (User)((Iterator)localObject3).next();
          localObject5 = ((User)localObject4).getId();
          if (((String)localObject5).equals(localObject2))
          {
            i = 1;
            break;
          }
        }
        if (i == 0)
        {
          localUser.setPersonalBanker("0");
          localUser.put("TARGET_FIELDS", "PERSONALBANKER");
          localUser.put("PERSONAL_BANKER_NAME", "Not Assigned");
          try
          {
            localObject4 = (SecureUser)localHttpSession.getAttribute("SecureUser");
            UserAdmin.modifyUser((SecureUser)localObject4, localUser, null);
          }
          catch (CSILException localCSILException2)
          {
            this.error = MapError.mapError(localCSILException2);
            str1 = this.serviceErrorURL;
            break;
          }
          localIterator1.remove();
          localArrayList1.add(localUser.getFirstName() + " " + localUser.getLastName());
        }
      }
      if (str1.equals(this.successURL))
      {
        localObject1 = localUsers2.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          int j = 0;
          localObject2 = (User)((Iterator)localObject1).next();
          localObject3 = ((User)localObject2).getId();
          Iterator localIterator2 = localUsers1.iterator();
          while (localIterator2.hasNext())
          {
            localObject5 = (User)localIterator2.next();
            String str2 = ((User)localObject5).getId();
            if (str2.equals(localObject3))
            {
              j = 1;
              break;
            }
          }
          if ((j == 0) && (this.t8 != null))
          {
            ((User)localObject2).setPersonalBanker(this.t8);
            ((User)localObject2).put("TARGET_FIELDS", "PERSONALBANKER");
            try
            {
              localObject5 = (SecureUser)localHttpSession.getAttribute("SecureUser");
              UserAdmin.modifyUser((SecureUser)localObject5, (User)localObject2, null);
            }
            catch (CSILException localCSILException3)
            {
              this.error = MapError.mapError(localCSILException3);
              str1 = this.serviceErrorURL;
              break;
            }
            localUsers1.add(localObject2);
            localArrayList2.add(((User)localObject2).getFirstName() + " " + ((User)localObject2).getLastName());
          }
        }
      }
      Object localObject1 = (Locale)localHttpSession.getAttribute("java.util.Locale");
      Histories localHistories = new Histories((Locale)localObject1);
      jdMethod_for(localHttpSession, localArrayList1, localArrayList2, localHistories);
      if (localHistories.size() > 0) {
        try
        {
          localObject2 = null;
          localObject3 = (SecureUser)localHttpSession.getAttribute("SecureUser");
          BankEmployeeAdmin.addHistory((SecureUser)localObject3, localHistories, (HashMap)localObject2);
        }
        catch (CSILException localCSILException1)
        {
          this.error = MapError.mapError(localCSILException1);
        }
      }
    }
    return str1;
  }
  
  private void jdMethod_for(HttpSession paramHttpSession, ArrayList paramArrayList1, ArrayList paramArrayList2, Histories paramHistories)
  {
    BankEmployee localBankEmployee = new BankEmployee((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    localBankEmployee.setId(this.t8);
    try
    {
      HashMap localHashMap = null;
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      localBankEmployee = BankEmployeeAdmin.getBankEmployeeById(localSecureUser, localBankEmployee, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    int i;
    if (paramArrayList1 != null) {
      for (i = 0; i < paramArrayList1.size(); i++) {
        paramHistories.add(AddBankEmployee.buildHistory(paramHttpSession, localBankEmployee, "ASSIGNED_USER_REMOVED", (String)paramArrayList1.get(i), "", ""));
      }
    }
    if (paramArrayList2 != null) {
      for (i = 0; i < paramArrayList2.size(); i++) {
        paramHistories.add(AddBankEmployee.buildHistory(paramHttpSession, localBankEmployee, "ASSIGNED_USER_ADDED", "", (String)paramArrayList2.get(i), ""));
      }
    }
  }
  
  public void setEmployeeId(String paramString)
  {
    this.t8 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.UpdateAssignedUsers
 * JD-Core Version:    0.7.0.1
 */