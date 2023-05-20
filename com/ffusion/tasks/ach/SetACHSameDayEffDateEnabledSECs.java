package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetACHSameDayEffDateEnabledSECs
  extends BaseTask
  implements Task
{
  protected String sessionName;
  protected int objectType;
  protected String objectID;
  protected String objectName;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashSet localHashSet = (HashSet)localHttpSession.getAttribute(this.sessionName);
    Object localObject = null;
    if (this.objectName != null) {
      localObject = localHttpSession.getAttribute(this.objectName);
    }
    if (localObject != null) {
      localHashMap.put("Object", localObject);
    }
    if (localHashSet == null) {
      localHashSet = new HashSet();
    }
    try
    {
      ACH.setEnabledSECCodes(localSecureUser, this.objectID, this.objectType, localHashSet, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public final void setSessionName(String paramString)
  {
    this.sessionName = paramString;
  }
  
  public final String getSessionName()
  {
    return this.sessionName;
  }
  
  public String getObjectID()
  {
    return this.objectID;
  }
  
  public void setObjectID(String paramString)
  {
    this.objectID = paramString;
  }
  
  public String getObjectName()
  {
    return this.objectName;
  }
  
  public void setObjectName(String paramString)
  {
    this.objectName = paramString;
  }
  
  public void setObjectType(String paramString)
  {
    this.objectType = Integer.parseInt(paramString);
  }
  
  public String getObjectType()
  {
    return String.valueOf(this.objectType);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.SetACHSameDayEffDateEnabledSECs
 * JD-Core Version:    0.7.0.1
 */