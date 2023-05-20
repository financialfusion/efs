package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.User;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetTransactionSearchSettings
  extends BaseTask
  implements UserTask
{
  protected String oldValue;
  protected String newValue;
  protected String nameSpace = "TRANSACTION_SEARCH";
  protected String tag = "MAXIMUM_MATCHES";
  protected String sessionName = this.nameSpace + "_" + this.tag;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    this.error = 0;
    String str2 = null;
    HashMap localHashMap = null;
    if (Strings.compareTo(this.oldValue, this.newValue) != 0) {
      try
      {
        XMLTag localXMLTag1 = new XMLTag();
        localXMLTag1.setTagName(this.nameSpace);
        XMLTag localXMLTag2 = new XMLTag();
        localXMLTag2.setTagName(this.tag);
        localXMLTag2.setTagContent(this.newValue);
        localXMLTag1.setContainedTag(localXMLTag2);
        str2 = XMLTag.toXML(localXMLTag1, true);
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        UserAdmin.addAdditionalData(localSecureUser, this.nameSpace, str2, localHashMap);
        HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 1, Integer.toString(localSecureUser.getProfileID()));
        localHistoryTracker.detectChange(User.BEAN_NAME, "TRANSACTION_SEARCH", this.oldValue, this.newValue, (ILocalizable)null);
        try
        {
          HistoryAdapter.addHistory(localHistoryTracker.getHistories());
        }
        catch (ProfileException localProfileException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for SetTransactionSearchSettings: " + localProfileException.toString());
        }
        localHttpSession.setAttribute(this.sessionName, this.newValue);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
    }
    return str1;
  }
  
  public void setOldValue(String paramString)
  {
    this.oldValue = paramString;
  }
  
  public void setNewValue(String paramString)
  {
    this.newValue = paramString;
  }
  
  public void setNameSpace(String paramString)
  {
    this.nameSpace = paramString;
  }
  
  public void setTag(String paramString)
  {
    this.tag = paramString;
  }
  
  public void setSessionName(String paramString)
  {
    this.sessionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.SetTransactionSearchSettings
 * JD-Core Version:    0.7.0.1
 */