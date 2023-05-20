package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.GlobalMessageConsts;
import com.ffusion.beans.messages.GlobalMessageSearchCriteria;
import com.ffusion.beans.messages.GlobalMessages;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPendingGlobalMessages
  extends BaseTask
  implements Task
{
  private String sp = "GlobalMessages";
  private String sq = "en_US";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    GlobalMessages localGlobalMessages = null;
    try
    {
      GlobalMessageSearchCriteria localGlobalMessageSearchCriteria = new GlobalMessageSearchCriteria();
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(new Integer(1));
      localGlobalMessageSearchCriteria.setStatuses(localArrayList);
      localGlobalMessageSearchCriteria.setApprovalEmployeeID(String.valueOf(localSecureUser.getProfileID()));
      String str2 = localSecureUser.getAffiliateID();
      if ((!str2.equals("")) && (!str2.equals("0"))) {
        localGlobalMessageSearchCriteria.setAddAffiliateBank(str2);
      }
      for (int i = 0; i < GlobalMessageConsts.MessageTypes.length; i++) {
        localGlobalMessageSearchCriteria.setAddMsgTypes(String.valueOf(GlobalMessageConsts.MessageTypes[i]));
      }
      localGlobalMessageSearchCriteria.setSearchLanguage(getSearchLanguage());
      localGlobalMessages = Messages.getGlobalMessages(localSecureUser, localGlobalMessageSearchCriteria, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    if (this.error == 0) {
      localHttpSession.setAttribute(this.sp, localGlobalMessages);
    }
    return str1;
  }
  
  public void setDestinationSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.sp = paramString;
    }
  }
  
  public String getDestinationSessionKey()
  {
    return this.sp;
  }
  
  public void setSearchLanguage(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.sq = null;
    } else {
      this.sq = paramString;
    }
  }
  
  public String getSearchLanguage()
  {
    return this.sq;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetPendingGlobalMessages
 * JD-Core Version:    0.7.0.1
 */