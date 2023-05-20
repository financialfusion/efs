package com.ffusion.tasks.authentication;

import com.ffusion.beans.authentication.Credential;
import com.ffusion.beans.authentication.Credentials;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class StoreCredentialResponses
  extends BaseTask
  implements Task
{
  private String aKH = "Credentials";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Credentials localCredentials = (Credentials)localHttpSession.getAttribute(this.aKH);
    if (localCredentials == null) {
      return this.successURL;
    }
    int i = 0;
    for (int j = 0; j < localCredentials.size(); j++)
    {
      String str1 = "CredentialResponse_" + j;
      Credential localCredential = (Credential)localCredentials.get(j);
      String str2 = (String)localHttpSession.getAttribute(str1);
      if (validateResponse(localHttpSession, str2)) {
        localCredential.setResponse(str2);
      } else {
        i = 1;
      }
      localHttpSession.removeAttribute(str1);
    }
    if (i != 0)
    {
      this.error = 90004;
      return this.taskErrorURL;
    }
    return this.successURL;
  }
  
  protected boolean validateResponse(HttpSession paramHttpSession, String paramString)
  {
    return true;
  }
  
  public void setCredentialsCollection(String paramString)
  {
    this.aKH = paramString;
  }
  
  public String getCredentialsCollection()
  {
    return this.aKH;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.authentication.StoreCredentialResponses
 * JD-Core Version:    0.7.0.1
 */