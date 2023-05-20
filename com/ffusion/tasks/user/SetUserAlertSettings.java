package com.ffusion.tasks.user;

import com.ffusion.beans.Email;
import com.ffusion.beans.Phone;
import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.MapError;
import com.ffusion.util.XMLTag;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetUserAlertSettings
  extends GetUserAlertSettings
  implements UserTask
{
  private String wc;
  protected String _oldSecureAlert;
  protected String _oldDeliveryInfo1;
  protected String _oldDeliveryInfo2;
  protected String _oldDeliveryInfo1To;
  protected String _oldDeliveryInfo2To;
  private static final String wd = "com.ffusion.util.logging.audit.task";
  private static final String we = "AuditMessage_SetUserAlertSettings_";
  private static final String wb = "AuditMessage_SetUserAlertSettings_N_NN_NN";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    this.error = 0;
    String str2 = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = null;
    try
    {
      Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
      if (localLocale == null)
      {
        this.error = 41;
        return getTaskErrorURL();
      }
      if ((!jdMethod_for(localSecureUser, (String)localHttpSession.getAttribute("DeliveryInfo1To"), localLocale)) || (!jdMethod_for(localSecureUser, (String)localHttpSession.getAttribute("DeliveryInfo2To"), localLocale)))
      {
        this.error = 31;
        return getTaskErrorURL();
      }
      XMLTag localXMLTag1 = new XMLTag();
      localXMLTag1.setTagName("UserAlertSettings");
      XMLTag localXMLTag2 = new XMLTag();
      localXMLTag2.setTagName("SecureAlert");
      String str3 = (String)localHttpSession.getAttribute("SecureAlert");
      localXMLTag2.setTagContent(str3);
      localXMLTag1.setContainedTag(localXMLTag2);
      XMLTag localXMLTag3 = new XMLTag();
      localXMLTag3.setTagName("DeliveryInfo1");
      String str4 = (String)localHttpSession.getAttribute("DeliveryInfo1");
      localXMLTag3.setTagContent(str4);
      localXMLTag1.setContainedTag(localXMLTag3);
      XMLTag localXMLTag4 = new XMLTag();
      localXMLTag4.setTagName("DeliveryInfo2");
      String str5 = (String)localHttpSession.getAttribute("DeliveryInfo2");
      localXMLTag4.setTagContent(str5);
      localXMLTag1.setContainedTag(localXMLTag4);
      XMLTag localXMLTag5 = new XMLTag();
      localXMLTag5.setTagName("DeliveryInfo1To");
      String str6 = (String)localHttpSession.getAttribute("DeliveryInfo1To");
      localXMLTag5.setTagContent(str6);
      localXMLTag1.setContainedTag(localXMLTag5);
      XMLTag localXMLTag6 = new XMLTag();
      localXMLTag6.setTagName("DeliveryInfo2To");
      String str7 = (String)localHttpSession.getAttribute("DeliveryInfo2To");
      localXMLTag6.setTagContent(str7);
      localXMLTag1.setContainedTag(localXMLTag6);
      str2 = XMLTag.toXML(localXMLTag1, true);
      UserAdmin.addAdditionalData(localSecureUser, "ALERT_SETTINGS", str2, localHashMap);
      StringBuffer localStringBuffer = new StringBuffer("AuditMessage_SetUserAlertSettings_");
      ArrayList localArrayList = new ArrayList(2);
      if (str3.equals(this._oldSecureAlert)) {
        localStringBuffer.append("N");
      } else if (str3.equalsIgnoreCase("0")) {
        localStringBuffer.append("D");
      } else {
        localStringBuffer.append("E");
      }
      localStringBuffer.append("_");
      if ((!str4.equals(this._oldDeliveryInfo1)) || (!str6.equals(this._oldDeliveryInfo1To)))
      {
        if (str4.equals(this._oldDeliveryInfo1))
        {
          if (str4.equals("0"))
          {
            localStringBuffer.append("NN");
          }
          else
          {
            localStringBuffer.append("N");
            if (str6.equalsIgnoreCase(this._oldDeliveryInfo1To))
            {
              localStringBuffer.append("N");
            }
            else
            {
              localStringBuffer.append("Y");
              localArrayList.add(str6);
            }
          }
        }
        else if (str4.equals("0"))
        {
          localStringBuffer.append("DN");
        }
        else if (this._oldDeliveryInfo1.equals("0"))
        {
          localStringBuffer.append("EY");
          localArrayList.add(str6);
        }
        else
        {
          localStringBuffer.append("N");
          if (str6.equalsIgnoreCase(this._oldDeliveryInfo1To))
          {
            localStringBuffer.append("N");
          }
          else
          {
            localStringBuffer.append("Y");
            localArrayList.add(str6);
          }
        }
      }
      else {
        localStringBuffer.append("NN");
      }
      localStringBuffer.append("_");
      if ((!str5.equals(this._oldDeliveryInfo2)) || (!str7.equals(this._oldDeliveryInfo2To)))
      {
        if (str5.equals(this._oldDeliveryInfo2))
        {
          if (str5.equals("0"))
          {
            localStringBuffer.append("NN");
          }
          else
          {
            localStringBuffer.append("N");
            if (str7.equalsIgnoreCase(this._oldDeliveryInfo2To))
            {
              localStringBuffer.append("N");
            }
            else
            {
              localStringBuffer.append("Y");
              localArrayList.add(str7);
            }
          }
        }
        else if (str5.equals("0"))
        {
          localStringBuffer.append("DN");
        }
        else if (this._oldDeliveryInfo2.equals("0"))
        {
          localStringBuffer.append("EY");
          localArrayList.add(str7);
        }
        else
        {
          localStringBuffer.append("N");
          if (str7.equalsIgnoreCase(this._oldDeliveryInfo2To))
          {
            localStringBuffer.append("N");
          }
          else
          {
            localStringBuffer.append("Y");
            localArrayList.add(str7);
          }
        }
      }
      else {
        localStringBuffer.append("NN");
      }
      String str8 = localStringBuffer.toString();
      LocalizableString localLocalizableString = null;
      if (!str8.equals("AuditMessage_SetUserAlertSettings_N_NN_NN")) {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str8, localArrayList.toArray());
      }
      if (localLocalizableString != null) {
        Initialize.audit(localSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID(), 3212);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
  
  private boolean jdMethod_for(SecureUser paramSecureUser, String paramString, Locale paramLocale)
    throws CSILException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return true;
    }
    if (new Email(paramString).isValid()) {
      return true;
    }
    if (this.wc != null)
    {
      Phone localPhone = new Phone(paramString, true);
      String str = Util.validatePhoneFormat(paramSecureUser, this.wc, paramString, new HashMap());
      if (str == null) {
        return false;
      }
      localPhone.setFormat(str);
      return true;
    }
    return true;
  }
  
  public void setCountry(String paramString)
  {
    this.wc = paramString;
  }
  
  public void setOldSecureAlert(String paramString)
  {
    this._oldSecureAlert = paramString;
  }
  
  public void setOldDeliveryInfo1(String paramString)
  {
    this._oldDeliveryInfo1 = paramString;
  }
  
  public void setOldDeliveryInfo2(String paramString)
  {
    this._oldDeliveryInfo2 = paramString;
  }
  
  public void setOldDeliveryInfo1To(String paramString)
  {
    this._oldDeliveryInfo1To = paramString;
  }
  
  public void setOldDeliveryInfo2To(String paramString)
  {
    this._oldDeliveryInfo2To = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.SetUserAlertSettings
 * JD-Core Version:    0.7.0.1
 */