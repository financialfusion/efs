package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.util.BAIExportSettingsXMLUtil;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetBAIExportSettings
  extends BaseTask
  implements UserTask
{
  private String wU = null;
  private String w4 = null;
  private String wJ = null;
  private String wK = null;
  private String wS = null;
  private String wO = null;
  private String wT = null;
  private String wY = null;
  private String w2 = null;
  private String wW = null;
  private String w1 = null;
  private String w3 = null;
  private String wL = null;
  private String wQ = null;
  private String wX = null;
  private String wR = null;
  private String w0 = null;
  private String wN = null;
  private String wZ = null;
  private boolean wV = false;
  private static final String wP = "com.ffusion.util.logging.audit.task";
  private static final String wM = "AuditMessage_SetBAIExportSettings_1";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    if (this.wV)
    {
      str = j();
    }
    else
    {
      if (this.wZ == null)
      {
        DebugLog.log("No business session name was set for the GetBAIExportSettings task.");
        this.error = 3528;
        return this.taskErrorURL;
      }
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      Business localBusiness = null;
      try
      {
        localBusiness = (Business)localHttpSession.getAttribute(this.wZ);
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
      HashMap localHashMap = new HashMap();
      try
      {
        localHashMap.put("SENDER_ID_TYPE", this.wU);
        localHashMap.put("SENDER_ID_CUSTOM_DATA", this.w4);
        localHashMap.put("RECEIVER_ID_TYPE", this.wJ);
        localHashMap.put("RECEIVER_ID_CUSTOM_DATA", this.wK);
        localHashMap.put("ULTIMATE_RECEIVER_ID_TYPE", this.wS);
        localHashMap.put("ULTIMATE_RECEIVER_ID_CUSTOM_DATA", this.wO);
        localHashMap.put("ORIGINATOR_ID_TYPE", this.wT);
        localHashMap.put("ORIGINATOR_ID_CUSTOM_DATA", this.wY);
        localHashMap.put("CUSTOMER_ACCOUNT_OPTION", this.w2);
        BAIExportSettingsXMLUtil.setBAIExportSettings(localSecureUser, localBusiness, localHashMap);
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_SetBAIExportSettings_1", null);
        Initialize.audit(localSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID(), 3212);
        HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 2, localBusiness.getId());
        localHistoryTracker.detectChange(Business.BEAN_NAME, "SENDER_ID_TYPE", getSenderType(this.wW), getSenderType(this.wU), (ILocalizable)null);
        localHistoryTracker.detectChange(Business.BEAN_NAME, "SENDER_ID_CUSTOM_DATA", this.w1, this.w4, (ILocalizable)null);
        localHistoryTracker.detectChange(Business.BEAN_NAME, "RECEIVER_ID_TYPE", getReceiverType(this.w3), getReceiverType(this.wJ), (ILocalizable)null);
        localHistoryTracker.detectChange(Business.BEAN_NAME, "RECEIVER_ID_CUSTOM_DATA", this.wL, this.wK, (ILocalizable)null);
        localHistoryTracker.detectChange(Business.BEAN_NAME, "ULTIMATE_RECEIVER_ID_TYPE", getUltimateReceiverType(this.wQ), getUltimateReceiverType(this.wS), (ILocalizable)null);
        localHistoryTracker.detectChange(Business.BEAN_NAME, "ULTIMATE_RECEIVER_ID_CUSTOM_DATA", this.wX, this.wO, (ILocalizable)null);
        localHistoryTracker.detectChange(Business.BEAN_NAME, "ORIGINATOR_ID_TYPE", getOriginatorType(this.wR), getOriginatorType(this.wT), (ILocalizable)null);
        localHistoryTracker.detectChange(Business.BEAN_NAME, "ORIGINATOR_ID_CUSTOM_DATA", this.w0, this.wY, (ILocalizable)null);
        localHistoryTracker.detectChange(Business.BEAN_NAME, "CUSTOMER_ACCOUNT_OPTION", getAccountNumberType(this.wN), getAccountNumberType(this.w2), (ILocalizable)null);
        try
        {
          HistoryAdapter.addHistory(localHistoryTracker.getHistories());
        }
        catch (ProfileException localProfileException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for SetBAIExportSettings: " + localProfileException.toString());
        }
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
          DebugLog.throwing("The call to set BAI Export Settings failed.", localException);
          this.error = 3527;
          str = this.taskErrorURL;
        }
      }
    }
    return str;
  }
  
  protected String getSenderType(String paramString)
  {
    if (paramString.equals("1")) {
      return "Bank Routing Number";
    }
    if (paramString.equals("2")) {
      return "Custom Value";
    }
    return "";
  }
  
  protected String getReceiverType(String paramString)
  {
    if (paramString.equals("1")) {
      return "Bank Routing Number";
    }
    if (paramString.equals("2")) {
      return "Custom Value";
    }
    return "";
  }
  
  protected String getUltimateReceiverType(String paramString)
  {
    if (paramString.equals("1")) {
      return "Bank Routing Number";
    }
    if (paramString.equals("2")) {
      return "Custom Value";
    }
    return "";
  }
  
  protected String getOriginatorType(String paramString)
  {
    if (paramString.equals("1")) {
      return "Bank Routing Number";
    }
    if (paramString.equals("2")) {
      return "Custom Value";
    }
    return "";
  }
  
  protected String getAccountNumberType(String paramString)
  {
    if (paramString.equals("1")) {
      return "Account Number";
    }
    if (paramString.equals("2")) {
      return "Account and Routing Number";
    }
    return "";
  }
  
  private String j()
  {
    if ((this.wU.equals("2")) && ((this.w4 == null) || (this.w4.equals("")) || (this.w4.length() > 100)))
    {
      this.error = 3530;
      return this.taskErrorURL;
    }
    if ((this.wJ.equals("2")) && ((this.wK == null) || (this.wK.equals("")) || (this.wK.length() > 100)))
    {
      this.error = 3531;
      return this.taskErrorURL;
    }
    if ((this.wS.equals("2")) && ((this.wO == null) || (this.wO.equals("")) || (this.wO.length() > 100)))
    {
      this.error = 3532;
      return this.taskErrorURL;
    }
    if ((this.wT.equals("2")) && ((this.wY == null) || (this.wY.equals("")) || (this.wY.length() > 100)))
    {
      this.error = 3533;
      return this.taskErrorURL;
    }
    return this.successURL;
  }
  
  public void setValidate(String paramString)
  {
    this.wV = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setSenderIDType(String paramString)
  {
    this.wU = paramString;
  }
  
  public void setSenderIDCustom(String paramString)
  {
    this.w4 = paramString;
  }
  
  public void setReceiverIDType(String paramString)
  {
    this.wJ = paramString;
  }
  
  public void setReceiverIDCustom(String paramString)
  {
    this.wK = paramString;
  }
  
  public void setUltimateReceiverIDType(String paramString)
  {
    this.wS = paramString;
  }
  
  public void setUltimateReceiverIDCustom(String paramString)
  {
    this.wO = paramString;
  }
  
  public void setOriginatorIDType(String paramString)
  {
    this.wT = paramString;
  }
  
  public void setOriginatorIDCustom(String paramString)
  {
    this.wY = paramString;
  }
  
  public void setCustomerAccountNumberType(String paramString)
  {
    this.w2 = paramString;
  }
  
  public void setBusinessSessionName(String paramString)
  {
    this.wZ = paramString;
  }
  
  public String getSenderIDType()
  {
    return this.wU;
  }
  
  public String getSenderIDCustom()
  {
    return this.w4;
  }
  
  public String getReceiverIDType()
  {
    return this.wJ;
  }
  
  public String getReceiverIDCustom()
  {
    return this.wK;
  }
  
  public String getUltimateReceiverIDType()
  {
    return this.wS;
  }
  
  public String getUltimateReceiverIDCustom()
  {
    return this.wO;
  }
  
  public String getOriginatorIDType()
  {
    return this.wT;
  }
  
  public String getOriginatorIDCustom()
  {
    return this.wY;
  }
  
  public String getCustomerAccountNumberType()
  {
    return this.w2;
  }
  
  public void setOldSenderIDType(String paramString)
  {
    this.wW = paramString;
  }
  
  public void setOldSenderIDCustom(String paramString)
  {
    this.w1 = paramString;
  }
  
  public void setOldReceiverIDType(String paramString)
  {
    this.w3 = paramString;
  }
  
  public void setOldReceiverIDCustom(String paramString)
  {
    this.wL = paramString;
  }
  
  public void setOldUltimateReceiverIDType(String paramString)
  {
    this.wQ = paramString;
  }
  
  public void setOldUltimateReceiverIDCustom(String paramString)
  {
    this.wX = paramString;
  }
  
  public void setOldOriginatorIDType(String paramString)
  {
    this.wR = paramString;
  }
  
  public void setOldOriginatorIDCustom(String paramString)
  {
    this.w0 = paramString;
  }
  
  public void setOldCustomerAccountNumberType(String paramString)
  {
    this.wN = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.SetBAIExportSettings
 * JD-Core Version:    0.7.0.1
 */