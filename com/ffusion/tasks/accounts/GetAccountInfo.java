package com.ffusion.tasks.accounts;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAccountInfo
  extends BaseTask
  implements Task
{
  protected HashMap methods;
  protected String accountsName;
  protected Accounts accounts;
  protected Account account;
  protected String property;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    if (this.accountsName != null)
    {
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      this.accounts = ((Accounts)localHttpSession.getAttribute(this.accountsName));
    }
    else
    {
      this.accounts = null;
    }
    if (this.accounts == null) {
      str = this.taskErrorURL;
    } else {
      str = this.successURL;
    }
    return str;
  }
  
  public void setAccountsName(String paramString)
  {
    this.accountsName = paramString;
  }
  
  public void setID(String paramString)
  {
    if (this.accounts != null) {
      this.account = this.accounts.getByID(paramString);
    }
  }
  
  public void setProperty(String paramString)
  {
    this.property = paramString;
  }
  
  public Object getProperty()
    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
  {
    Object localObject = null;
    if (this.account != null)
    {
      Method localMethod = null;
      if (this.methods == null) {
        this.methods = new HashMap();
      } else {
        localMethod = (Method)this.methods.get(this.property);
      }
      if (localMethod == null)
      {
        localMethod = this.account.getClass().getMethod("get" + this.property, null);
        if (localMethod != null) {
          this.methods.put(this.property, localMethod);
        }
      }
      if (localMethod != null) {
        localObject = localMethod.invoke(this.account, null);
      }
    }
    return localObject;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.GetAccountInfo
 * JD-Core Version:    0.7.0.1
 */