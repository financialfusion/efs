package com.ffusion.tasks.systemadmin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.systemadmin.DRKey;
import com.ffusion.beans.systemadmin.DRSetting;
import com.ffusion.beans.systemadmin.DRSettingKeys;
import com.ffusion.beans.systemadmin.DRSettings;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.beans.util.LastRequest;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.SystemAdmin;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.systemadmin.SAConstants;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetDataRetentionSettings
  extends BaseTask
  implements SAConstants, SystemAdminTask
{
  private int U5 = -1;
  private int U9 = -1;
  private String U7 = "DRSettings";
  private String U6 = null;
  private String Vb = "DataRetentionLastRequest";
  private static final String Va = "com.ffusion.util.logging.audit.task";
  private static final String U3 = "AuditMessage_SetDataRetentionSettings_1.1";
  private static final String U2 = "AuditMessage_SetDataRetentionSettings_1.2";
  private static final String U4 = "AuditMessage_SetDataRetentionSettings_1.3";
  private static final String U8 = "AuditMessage_SetDataRetentionSettings_1.4";
  private static final String U1 = "AuditMessage_SetDataRetentionSettings_1.5";
  private boolean U0 = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    DRSettingKeys localDRSettingKeys = new DRSettingKeys();
    HistoryTracker localHistoryTracker = null;
    int i = -1;
    SecureUser localSecureUser = null;
    HashMap localHashMap1 = null;
    HashMap localHashMap2 = new HashMap();
    DRSettings localDRSettings = null;
    this.error = 0;
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localDRSettings = (DRSettings)localHttpSession.getAttribute(this.U7);
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    if (this.U5 == -1)
    {
      this.error = 38100;
      return this.taskErrorURL;
    }
    if (this.U9 == -1)
    {
      this.error = 38101;
      return this.taskErrorURL;
    }
    if (localDRSettings == null)
    {
      this.error = 38102;
      return this.taskErrorURL;
    }
    LastRequest localLastRequest = new LastRequest(paramHttpServletRequest);
    localHttpSession.setAttribute(this.Vb, localLastRequest);
    localHttpSession.setAttribute("UseLastRequest", "true");
    String str2 = null;
    Iterator localIterator = localDRSettingKeys.iterator();
    Object localObject1;
    Object localObject2;
    Object localObject3;
    DRSetting localDRSetting;
    while (localIterator.hasNext())
    {
      localObject1 = (DRKey)localIterator.next();
      localObject2 = new StringBuffer();
      localObject3 = null;
      ((StringBuffer)localObject2).append(((DRKey)localObject1).getDataType());
      ((StringBuffer)localObject2).append(((DRKey)localObject1).getDataClass());
      localObject3 = paramHttpServletRequest.getParameter(((StringBuffer)localObject2).toString());
      if ((localObject3 != null) && (((String)localObject3).length() > 0))
      {
        int j = 0;
        localDRSetting = null;
        try
        {
          j = Integer.parseInt((String)localObject3);
        }
        catch (Exception localException)
        {
          this.error = 38103;
          return this.taskErrorURL;
        }
        if (j < 0)
        {
          this.error = 38103;
          return this.taskErrorURL;
        }
        localDRSetting = new DRSetting();
        localDRSetting.setDataKey((DRKey)localObject1);
        localDRSetting.setRetentionDays(j);
        localHashMap2.put(localObject1, localDRSetting);
      }
    }
    if (this.U0 == true)
    {
      try
      {
        SystemAdmin.setDataRetentionSettings(localSecureUser, this.U9, this.U5, localHashMap2, localHashMap1);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
      String str3 = "AuditMessage_SetDataRetentionSettings_1.1";
      switch (this.U9)
      {
      case 0: 
        str3 = "AuditMessage_SetDataRetentionSettings_1.1";
        break;
      case 1: 
        str3 = "AuditMessage_SetDataRetentionSettings_1.2";
        i = 20;
        break;
      case 2: 
        str3 = "AuditMessage_SetDataRetentionSettings_1.3";
        i = 3;
        break;
      case 3: 
        str3 = "AuditMessage_SetDataRetentionSettings_1.4";
        i = 4;
        break;
      case 4: 
        str3 = "AuditMessage_SetDataRetentionSettings_1.5";
        i = 2;
        break;
      }
      if (this.U9 != 0)
      {
        localObject1 = new Object[1];
        localObject1[0] = this.U6;
        localObject2 = new LocalizableString("com.ffusion.util.logging.audit.task", str3, (Object[])localObject1);
      }
      else
      {
        localObject2 = new LocalizableString("com.ffusion.util.logging.audit.task", str3, null);
      }
      str2 = TrackingIDGenerator.GetNextID();
      if (this.U9 == 4)
      {
        localObject3 = (Business)localHttpSession.getAttribute("ModifyBusiness");
        Initialize.audit(localSecureUser, (ILocalizable)localObject2, ((Business)localObject3).getIdValue(), str2, 5280);
      }
      else
      {
        Initialize.audit(localSecureUser, (ILocalizable)localObject2, str2, 5280);
      }
      if (i != -1)
      {
        localHistoryTracker = new HistoryTracker(localSecureUser, i, getDataRetentionID());
        localObject3 = localDRSettingKeys.iterator();
        while (((Iterator)localObject3).hasNext())
        {
          DRKey localDRKey = (DRKey)((Iterator)localObject3).next();
          localDRSetting = null;
          String str4 = null;
          String str5 = null;
          localDRSetting = (DRSetting)localHashMap2.get(localDRKey);
          if (localDRSetting == null) {
            str5 = "";
          } else {
            str5 = localDRSetting.getRetentionDaysString();
          }
          localDRSettings.setDataType(localDRKey.getDataType());
          localDRSettings.setDataClass(localDRKey.getDataClass());
          str4 = localDRSettings.getDRSetting();
          if (!str4.equals(str5))
          {
            StringBuffer localStringBuffer = new StringBuffer();
            localStringBuffer.append("displayName_").append(localDRKey.getDataType()).append(localDRKey.getDataClass());
            Object[] arrayOfObject = new Object[2];
            arrayOfObject[0] = localHistoryTracker.buildLocalizableComment(16);
            arrayOfObject[1] = new LocalizableString("com.ffusion.beans.systemadmin.resources", localStringBuffer.toString(), null);
            LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.history.resources", "DATA_RETENTION", arrayOfObject);
            localHistoryTracker.logChange(DRSetting.BEAN_NAME, "DRSETTING", str4, str5, localLocalizableString);
          }
        }
        try
        {
          HistoryAdapter.addHistory(localHistoryTracker.getHistories());
        }
        catch (ProfileException localProfileException)
        {
          DebugLog.log(Level.SEVERE, "Add history failed for SetDataRetentionSettings: " + localProfileException);
        }
      }
    }
    if ((str1 == null) || ((str1 != null) && (!str1.equals(this.taskErrorURL))))
    {
      localHttpSession.removeAttribute(this.Vb);
      localHttpSession.removeAttribute("UseLastRequest");
    }
    return str1;
  }
  
  public String getDataRetentionID()
  {
    return Integer.toString(this.U5);
  }
  
  public String getDataRetentionType()
  {
    return Integer.toString(this.U9);
  }
  
  public String getDRSettingsSessionName()
  {
    return this.U7;
  }
  
  public String getEntity()
  {
    return this.U6;
  }
  
  public void setDataRetentionID(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.U5 = -1;
    } else {
      try
      {
        this.U5 = Integer.parseInt(paramString);
      }
      catch (Exception localException)
      {
        this.U5 = -1;
      }
    }
  }
  
  public void setDataRetentionType(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.U9 = -1;
    } else {
      try
      {
        this.U9 = Integer.parseInt(paramString);
      }
      catch (Exception localException)
      {
        this.U9 = -1;
      }
    }
  }
  
  public void setDRSettingsSessionName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.U7 = "DRSettings";
    } else {
      this.U7 = paramString;
    }
  }
  
  public void setEntity(String paramString)
  {
    this.U6 = paramString;
  }
  
  public void setLastRequestSessionName(String paramString)
  {
    this.Vb = paramString;
  }
  
  public String getLastRequestSessionName()
  {
    return this.Vb;
  }
  
  public void setProcess(String paramString)
  {
    this.U0 = new Boolean(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.systemadmin.SetDataRetentionSettings
 * JD-Core Version:    0.7.0.1
 */