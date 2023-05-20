package com.ffusion.tasks.accounts;

import com.ffusion.tasks.BaseTask;
import com.ffusion.util.ResourceUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAccountTypesByGroup
  extends BaseTask
{
  public static final String RESOURCE_BUNDLE_ACCOUNTS_NAME = "com.ffusion.beans.accounts.resources";
  public static final String ACCOUNT_TYPE_TO_GROUP_PREFIX = "AccountTypeToGroup";
  public static final String ACCOUNT_TYPE_PREFIX = "AccountType";
  protected String _groupID = null;
  protected ArrayList _accountTypesList = new ArrayList();
  protected String _accountTypesListName;
  protected String _accountTypesIDListName = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    if (localLocale == null)
    {
      this.error = 41;
      return this.taskErrorURL;
    }
    Object localObject = ResourceUtil.getBundle("com.ffusion.beans.accounts.resources", localLocale);
    if (localObject == null)
    {
      this.error = 43;
      return this.taskErrorURL;
    }
    localObject = (ArrayList)localHttpSession.getAttribute(this._accountTypesIDListName);
    if ((localObject != null) && (this._groupID != null))
    {
      for (int i = 0; i < ((ArrayList)localObject).size(); i++)
      {
        String str1 = (String)((ArrayList)localObject).get(i);
        String str2 = ResourceUtil.getString("AccountTypeToGroup" + str1, "com.ffusion.beans.accounts.resources", localLocale);
        if (this._groupID.equals(str2))
        {
          String str3 = ResourceUtil.getString("AccountType" + str1, "com.ffusion.beans.accounts.resources", localLocale);
          this._accountTypesList.add(str3);
        }
      }
    }
    else
    {
      this.error = 51;
      return this.taskErrorURL;
    }
    localHttpSession.setAttribute(this._accountTypesListName, this._accountTypesList);
    return this.successURL;
  }
  
  public void setGroupID(String paramString)
  {
    this._groupID = paramString;
  }
  
  public void setAccountTypesListName(String paramString)
  {
    this._accountTypesListName = paramString;
  }
  
  public void setAccountTypesIDListName(String paramString)
  {
    this._accountTypesIDListName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.GetAccountTypesByGroup
 * JD-Core Version:    0.7.0.1
 */