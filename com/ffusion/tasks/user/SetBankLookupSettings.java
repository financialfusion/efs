package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.XMLTag;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetBankLookupSettings
  extends BaseTask
  implements UserTask
{
  public static final String ADDITIONALSETTINGSNAMESPACE = "BANK_LOOKUP_SETTINGS";
  public static final String MAXIMUM_MATCHES_TAG = "MAXIMUM_MATCHES";
  public static final String MAXIMUM_MATCHES_SESSION_NAME = "BANK_LOOKUP_SETTINGS_MAXIMUM_MATCHES";
  private static final String vg = "com.ffusion.util.logging.audit.task";
  private static final String vi = "AuditMessage_SetBankLookupSettings_1";
  private String vh;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    this.error = 0;
    String str2 = null;
    HashMap localHashMap = null;
    try
    {
      XMLTag localXMLTag1 = new XMLTag();
      localXMLTag1.setTagName("BANK_LOOKUP_SETTINGS");
      XMLTag localXMLTag2 = new XMLTag();
      localXMLTag2.setTagName("MAXIMUM_MATCHES");
      String str3 = (String)localHttpSession.getAttribute("BANK_LOOKUP_SETTINGS_MAXIMUM_MATCHES");
      localXMLTag2.setTagContent(str3);
      localXMLTag1.setContainedTag(localXMLTag2);
      str2 = XMLTag.toXML(localXMLTag1, true);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      UserAdmin.addAdditionalData(localSecureUser, "BANK_LOOKUP_SETTINGS", str2, localHashMap);
      HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 1, Integer.toString(localSecureUser.getProfileID()));
      localHistoryTracker.detectChange(BankEmployee.BEAN_NAME, "MAX_BANKLOOKUP", this.vh, str3, (ILocalizable)null);
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for SetBankLookupSettings: " + localProfileException.toString());
      }
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = localSecureUser.getUserName();
      arrayOfObject[1] = str3;
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_SetBankLookupSettings_1", arrayOfObject);
      Initialize.audit(localSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID(), 3212);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
  
  public void setOldValue(String paramString)
  {
    this.vh = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.SetBankLookupSettings
 * JD-Core Version:    0.7.0.1
 */