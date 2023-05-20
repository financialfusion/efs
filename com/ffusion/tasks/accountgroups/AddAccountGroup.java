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

public class AddAccountGroup
  extends BaseTask
  implements AccountGroupsTask
{
  protected boolean _autoEntitleAccountGroup = true;
  private int aMX = -1;
  private String aMY = null;
  private String aMV = null;
  private String aMW = "Accounts";
  protected static final String ALPHANUMERIC_SET = ".<(+|&!$*);^-/,%_>?_:#@'=\"";
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
    BusinessAccountGroup localBusinessAccountGroup = null;
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.aMW);
    localBusinessAccountGroup = new BusinessAccountGroup();
    localBusinessAccountGroup.setBusDirId(this.aMX);
    localBusinessAccountGroup.setName(this.aMY);
    localBusinessAccountGroup.setAcctGroupId(this.aMV);
    localBusinessAccountGroup.setAccounts(localAccounts);
    if (jdMethod_for(localBusinessAccountGroup)) {
      try
      {
        AccountGroup.addAccountGroup(localSecureUser, localBusinessAccountGroup, this._autoEntitleAccountGroup, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    } else {
      str = this.taskErrorURL;
    }
    if (this.error == 0) {
      str = this.successURL;
    }
    return str;
  }
  
  private boolean jdMethod_for(BusinessAccountGroup paramBusinessAccountGroup)
  {
    boolean bool = true;
    if ((bool) && (paramBusinessAccountGroup.getBusDirId() == -1))
    {
      this.error = 70000;
      bool = false;
    }
    if (bool) {
      bool = Z(paramBusinessAccountGroup.getName());
    }
    if (bool) {
      bool = aa(paramBusinessAccountGroup.getAcctGroupId());
    }
    if ((bool) && ((paramBusinessAccountGroup.getAccounts() == null) || (paramBusinessAccountGroup.getAccounts().size() <= 0)))
    {
      this.error = 70005;
      bool = false;
    }
    return bool;
  }
  
  private boolean Z(String paramString)
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
  
  private boolean aa(String paramString)
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
  
  public void setBusDirectoryId(String paramString)
  {
    try
    {
      this.aMX = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.aMX = -1;
    }
  }
  
  public void setAccountsList(String paramString)
  {
    this.aMW = paramString;
  }
  
  public void setGroupName(String paramString)
  {
    this.aMY = paramString;
  }
  
  public void setGroupId(String paramString)
  {
    this.aMV = paramString;
  }
  
  public String getBusDirectoryId()
  {
    return String.valueOf(this.aMX);
  }
  
  public String getAccountsList()
  {
    return this.aMW;
  }
  
  public String getGroupName()
  {
    return this.aMY;
  }
  
  public String getGroupId()
  {
    return this.aMV;
  }
  
  public void setAutoEntitleAccountGroup(String paramString)
  {
    this._autoEntitleAccountGroup = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getAutoEntitleAccountGroup()
  {
    return new Boolean(this._autoEntitleAccountGroup).toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accountgroups.AddAccountGroup
 * JD-Core Version:    0.7.0.1
 */