package com.ffusion.tasks.admin;

import com.ffusion.beans.messages.GlobalMessage;
import com.ffusion.beans.messages.GlobalMessageConsts;
import com.ffusion.beans.messages.GlobalMessageFilter;
import com.ffusion.beans.messages.GlobalMessageFilters;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.messages.CreateGlobalMessage;
import com.ffusion.tasks.messages.ModifyGlobalMessage;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GlobalMessageFiltersFormHandler
  extends BaseTask
  implements GlobalMessageConsts
{
  public static String CURRENT_GLOBAL_MESSAGE = "CurrentGlobalMessage";
  public static String COMMAND = "cmd";
  public static String PARAMETER = "param";
  public static final int C_DELETE = 1;
  public static final int C_CONTINUE = 2;
  public static final int C_CANCEL = 3;
  public static final int C_ADDFEATURES = 4;
  public static String P_FEATURES = "features";
  protected String _currentGlobalMessageSessionName = CURRENT_GLOBAL_MESSAGE;
  protected GlobalMessage _gm;
  protected String _continueURL;
  protected String _init = "false";
  protected GlobalMessageFilters _tmpFilters;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Object localObject = localHttpSession.getAttribute(this._currentGlobalMessageSessionName);
    if ((localObject instanceof CreateGlobalMessage)) {
      this._gm = ((CreateGlobalMessage)localObject).getGlobalMessage();
    } else if ((localObject instanceof ModifyGlobalMessage)) {
      this._gm = ((ModifyGlobalMessage)localObject).getGlobalMessage();
    } else if ((localObject instanceof GlobalMessage)) {
      this._gm = ((GlobalMessage)localObject);
    }
    if (this._gm == null) {
      throw new IOException();
    }
    if ((this._init == null) || (this._init.equals("true")))
    {
      this._tmpFilters = new GlobalMessageFilters();
      if (this._gm.get("_filters") != null) {
        this._tmpFilters.addAll((GlobalMessageFilters)this._gm.get("_filters"));
      }
      this._init = "false";
    }
    String str2 = paramHttpServletRequest.getParameter(COMMAND);
    String str3 = paramHttpServletRequest.getParameter(PARAMETER);
    String[] arrayOfString = paramHttpServletRequest.getParameterValues(P_FEATURES);
    String str1 = getSuccessURL();
    if (str2 != null) {
      switch (Integer.parseInt(str2))
      {
      case 1: 
        delete_filter(str3);
        break;
      case 2: 
        str1 = getContinueURL();
        break;
      case 3: 
        resetFilters();
        str1 = getContinueURL();
        break;
      case 4: 
        addFilters(1, arrayOfString);
      }
    }
    return str1;
  }
  
  protected void delete_filter(String paramString)
  {
    GlobalMessageFilters localGlobalMessageFilters = (GlobalMessageFilters)this._gm.get("_filters");
    if (localGlobalMessageFilters == null) {
      return;
    }
    Iterator localIterator = localGlobalMessageFilters.iterator();
    while (localIterator.hasNext()) {
      if (((GlobalMessageFilter)localIterator.next()).getFilterValue().equals(paramString)) {
        localIterator.remove();
      }
    }
    this._gm.put("_filters", localGlobalMessageFilters);
  }
  
  protected void resetFilters()
  {
    this._gm.put("_filters", this._tmpFilters);
  }
  
  protected void addFilters(int paramInt, String[] paramArrayOfString)
  {
    if (paramArrayOfString == null) {
      return;
    }
    GlobalMessageFilters localGlobalMessageFilters = (GlobalMessageFilters)this._gm.get("_filters");
    if (localGlobalMessageFilters == null) {
      localGlobalMessageFilters = new GlobalMessageFilters();
    }
    for (int i = 0; i < paramArrayOfString.length; i++) {
      if (!localGlobalMessageFilters.contains(paramArrayOfString[i])) {
        localGlobalMessageFilters.add(new GlobalMessageFilter(paramInt, paramArrayOfString[i]));
      }
    }
    this._gm.put("_filters", localGlobalMessageFilters);
    this._gm.put("FILTERS_RECIPIENTS_MODIFIED_KEY", new Boolean("true"));
  }
  
  public void setCurrentGlobalMessageSessionName(String paramString)
  {
    this._currentGlobalMessageSessionName = paramString;
  }
  
  protected String getContinueURL()
  {
    return this._continueURL;
  }
  
  public void setContinueURL(String paramString)
  {
    this._continueURL = paramString;
  }
  
  public void setInit(String paramString)
  {
    this._init = paramString;
  }
  
  public GlobalMessage getGm()
  {
    return this._gm;
  }
  
  public void setGm(GlobalMessage paramGlobalMessage)
  {
    this._gm = paramGlobalMessage;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GlobalMessageFiltersFormHandler
 * JD-Core Version:    0.7.0.1
 */