package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.util.BAIExportSettingsXMLUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBAIExportSettings
  extends BaseTask
  implements UserTask
{
  private String vj = null;
  private String vq = null;
  private String vn = null;
  private String vs = null;
  private String vm = null;
  private String vr = null;
  private String vl = null;
  private String vo = null;
  private String vp = null;
  private String vk = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    if (this.vk == null)
    {
      DebugLog.log("No business session name was set for the GetBAIExportSettings task.");
      this.error = 3528;
      return this.taskErrorURL;
    }
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Business localBusiness = null;
    try
    {
      localBusiness = (Business)localHttpSession.getAttribute(this.vk);
    }
    catch (ClassCastException localClassCastException)
    {
      DebugLog.log("The session name for the business given to the GetBAIExportSettings task did not contain a business object (it contained another type of object).");
    }
    if (localBusiness == null)
    {
      DebugLog.log("Unable to retrieve a business from the specified session name in the GetBAIExportSettings task.");
      this.error = 3529;
      return this.taskErrorURL;
    }
    HashMap localHashMap = null;
    try
    {
      localHashMap = BAIExportSettingsXMLUtil.getBAIExportSettings(localSecureUser, localBusiness);
      this.vj = ((String)localHashMap.get("SENDER_ID_TYPE"));
      if (this.vj.equals("2")) {
        this.vq = ((String)localHashMap.get("SENDER_ID_CUSTOM_DATA"));
      }
      this.vn = ((String)localHashMap.get("RECEIVER_ID_TYPE"));
      if (this.vn.equals("2")) {
        this.vs = ((String)localHashMap.get("RECEIVER_ID_CUSTOM_DATA"));
      }
      this.vm = ((String)localHashMap.get("ULTIMATE_RECEIVER_ID_TYPE"));
      if (this.vm.equals("2")) {
        this.vr = ((String)localHashMap.get("ULTIMATE_RECEIVER_ID_CUSTOM_DATA"));
      }
      this.vl = ((String)localHashMap.get("ORIGINATOR_ID_TYPE"));
      if (this.vl.equals("2")) {
        this.vo = ((String)localHashMap.get("ORIGINATOR_ID_CUSTOM_DATA"));
      }
      this.vp = ((String)localHashMap.get("CUSTOMER_ACCOUNT_OPTION"));
    }
    catch (Exception localException)
    {
      if ((localException instanceof CSILException))
      {
        this.error = MapError.mapError((CSILException)localException);
        str = this.serviceErrorURL;
      }
      else
      {
        DebugLog.throwing("The call to retrieve BAI Export Settings failed.", localException);
        this.error = 3526;
        str = this.taskErrorURL;
      }
    }
    return str;
  }
  
  public void setBusinessSessionName(String paramString)
  {
    this.vk = paramString;
  }
  
  public String getSenderIDType()
  {
    return this.vj;
  }
  
  public String getSenderIDCustom()
  {
    return this.vq;
  }
  
  public String getReceiverIDType()
  {
    return this.vn;
  }
  
  public String getReceiverIDCustom()
  {
    return this.vs;
  }
  
  public String getUltimateReceiverIDType()
  {
    return this.vm;
  }
  
  public String getUltimateReceiverIDCustom()
  {
    return this.vr;
  }
  
  public String getOriginatorIDType()
  {
    return this.vl;
  }
  
  public String getOriginatorIDCustom()
  {
    return this.vo;
  }
  
  public String getCustomerAccountNumberType()
  {
    return this.vp;
  }
  
  public void setSenderIDType(String paramString)
  {
    this.vj = paramString;
  }
  
  public void setSenderIDCustom(String paramString)
  {
    this.vq = paramString;
  }
  
  public void setReceiverIDType(String paramString)
  {
    this.vn = paramString;
  }
  
  public void setReceiverIDCustom(String paramString)
  {
    this.vs = paramString;
  }
  
  public void setUltimateReceiverIDType(String paramString)
  {
    this.vm = paramString;
  }
  
  public void setUltimateReceiverIDCustom(String paramString)
  {
    this.vr = paramString;
  }
  
  public void setOriginatorIDType(String paramString)
  {
    this.vl = paramString;
  }
  
  public void setOriginatorIDCustom(String paramString)
  {
    this.vo = paramString;
  }
  
  public void setCustomerAccountNumberType(String paramString)
  {
    this.vp = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetBAIExportSettings
 * JD-Core Version:    0.7.0.1
 */