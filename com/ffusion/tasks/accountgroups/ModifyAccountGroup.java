package com.ffusion.tasks.accountgroups;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accountgroups.BusinessAccountGroup;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AccountGroup;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyAccountGroup
  extends BaseTask
  implements AccountGroupsTask
{
  private String aM6 = null;
  private String aM2 = null;
  private String aM5 = "Accounts";
  private String aM4 = "AccountGroup";
  private boolean aM3 = false;
  public static final String ALPHANUMERIC_SET = ".<(+|&!$*);^-/,%_>?_:#@'=\"";
  protected static final int GROUP_NAME_MAX_LENGTH = 1010;
  protected static final int GROUP_ID_MAX_LENGTH = 10;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    BusinessAccountGroup localBusinessAccountGroup1 = (BusinessAccountGroup)localHttpSession.getAttribute(this.aM4);
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.aM5);
    BusinessAccountGroup localBusinessAccountGroup2 = new BusinessAccountGroup(localBusinessAccountGroup1.getId(), this.aM6, this.aM2, localBusinessAccountGroup1.getBusDirId());
    localBusinessAccountGroup2.setAccounts(localAccounts);
    if (jdMethod_int(localBusinessAccountGroup2))
    {
      if (this.aM3) {
        try
        {
          AccountGroup.modifyAccountGroup(localSecureUser, localBusinessAccountGroup2, localBusinessAccountGroup1, localHashMap);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str = this.serviceErrorURL;
        }
      }
    }
    else {
      str = this.taskErrorURL;
    }
    if (this.error == 0) {
      str = this.successURL;
    }
    return str;
  }
  
  private boolean jdMethod_int(BusinessAccountGroup paramBusinessAccountGroup)
  {
    boolean bool = true;
    if ((bool) && (paramBusinessAccountGroup.getBusDirId() == -1))
    {
      this.error = 70000;
      bool = false;
    }
    if ((bool) && (paramBusinessAccountGroup.getId() < 0)) {
      this.error = 70001;
    }
    if (bool) {
      bool = ab(paramBusinessAccountGroup.getName());
    }
    if (bool) {
      bool = ac(paramBusinessAccountGroup.getAcctGroupId());
    }
    if ((bool) && ((paramBusinessAccountGroup.getAccounts() == null) || (paramBusinessAccountGroup.getAccounts().size() <= 0)))
    {
      this.error = 70005;
      bool = false;
    }
    return bool;
  }
  
  private boolean ab(String paramString)
  {
    boolean bool = true;
    bool = (bool) && (paramString != null) && (paramString.length() > 0) && (paramString.length() <= 1010);
    for (int i = 0; (bool) && (i < paramString.length()); i++) {
      bool = (bool) && ((Character.isLetterOrDigit(paramString.charAt(i))) || (paramString.charAt(i) == ' ') || (".<(+|&!$*);^-/,%_>?_:#@'=\"".indexOf(paramString.charAt(i)) != -1));
    }
    if (!bool) {
      this.error = 70002;
    }
    return bool;
  }
  
  private boolean ac(String paramString)
  {
    boolean bool = true;
    bool = (bool) && (paramString != null) && (paramString.length() > 0) && (paramString.length() <= 10);
    for (int i = 0; (bool) && (i < paramString.length()); i++) {
      bool = (bool) && ((Character.isLetterOrDigit(paramString.charAt(i))) || (paramString.charAt(i) == ' ') || (".<(+|&!$*);^-/,%_>?_:#@'=\"".indexOf(paramString.charAt(i)) != -1));
    }
    if (!bool) {
      this.error = 70003;
    }
    return bool;
  }
  
  public void setProcess(String paramString)
  {
    this.aM3 = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setOriginalAccountGroup(String paramString)
  {
    this.aM4 = paramString;
  }
  
  public void setAccountsList(String paramString)
  {
    this.aM5 = paramString;
  }
  
  public void setGroupName(String paramString)
  {
    this.aM6 = paramString;
  }
  
  public void setGroupId(String paramString)
  {
    this.aM2 = paramString;
  }
  
  public String getOriginalAccountGroup()
  {
    return this.aM4;
  }
  
  public String getAccountsList()
  {
    return this.aM5;
  }
  
  public String getGroupName()
  {
    return this.aM6;
  }
  
  public String getGroupId()
  {
    return this.aM2;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accountgroups.ModifyAccountGroup
 * JD-Core Version:    0.7.0.1
 */