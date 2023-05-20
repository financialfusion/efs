package com.ffusion.tasks.multiuser;

import com.ffusion.beans.Email;
import com.ffusion.beans.Phone;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ZipCode;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.csil.core.Util;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.jtf.Task;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.PasswordUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddSecondaryUser
  extends User
  implements Task
{
  public static final String VALIDATE_FIRST_NAME = "FIRST_NAME";
  public static final String VALIDATE_LAST_NAME = "LAST_NAME";
  public static final String VALIDATE_USER_NAME = "USER_NAME";
  protected int ADD_USER_MAX_NAME_LENGTH = 35;
  protected int ADD_USER_MAX_STREET_LENGTH = 40;
  protected int ADD_USER_MAX_CITY_LENGTH = 20;
  protected int ADD_USER_MAX_ZIPCODE_LENGTH = 11;
  protected int ADD_USER_MAX_STATE_LENGTH = 2;
  protected int ADD_USER_MAX_COUNTRY_LENGTH = 30;
  protected int ADD_USER_MAX_PHONE_LENGTH = 14;
  protected int ADD_USER_MAX_DATAPHONE_LENGTH = 14;
  protected int ADD_USER_MAX_EMAIL_LENGTH = 40;
  protected int ADD_USER_MAX_TITLE_LENGTH = 10;
  protected int ADD_USER_MAX_USERNAME_LENGTH = 18;
  protected int ADD_USER_MAX_PASSWORD_LENGTH = 50;
  protected int ADD_USER_MAX_PREFERRED_LANGUAGE_LENGTH = 5;
  protected int ADD_USER_MAX_ACCOUNT_STATUS_LENGTH = 10;
  public static final String VALIDATE_USER_PASSWORD = "USER_PASSWORD";
  public static final String VALIDATE_USER_CONFIRM_PASSWORD = "USER_CONFIRM_PASSWORD";
  public static final String VERIFY_PHONE = "PHONE";
  public static final String VERIFY_EMAIL_ADDR = "EMAIL";
  public static final String VERIFY_ZIP_CODE = "ZIPCODE";
  public static final String VERIFY_PASSWORD = "PASSWORD";
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String fieldsToValidate;
  protected String fieldsToVerify;
  protected int error = 0;
  protected boolean process = false;
  protected SecureUser sUser;
  protected Locale locale;
  protected String secondaryUsersSessionName = "SecondaryUser";
  protected String confirmPassword;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    this.sUser = ((SecureUser)localHttpSession.getAttribute("SecureUser"));
    this.locale = BaseTask.getLocale(localHttpSession, this.sUser);
    str = validateInput();
    if (this.error == 0)
    {
      str = verifyInputFormat();
      if (((str == this.successURL) || ((str != null) && (str.equals(this.successURL)))) && (this.process)) {
        try
        {
          Users localUsers = null;
          setBankId(this.sUser.getBankID());
          setAffiliateBankID(this.sUser.getAffiliateIDValue());
          UserAdmin.addSecondaryUser(this.sUser, this, new HashMap());
          put("PASSWORD_STATUS", "2");
          UserAdmin.modifyPasswordStatus(this.sUser, this, new HashMap());
          addHistory();
          localUsers = new Users(this.locale);
          localUsers.add(this);
          localHttpSession.setAttribute(this.secondaryUsersSessionName, localUsers);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str = this.serviceErrorURL;
        }
      }
    }
    return str;
  }
  
  public void setProcess(String paramString)
  {
    this.process = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public String getSuccessURL()
  {
    return this.successURL;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public String getTaskErrorURL()
  {
    return this.taskErrorURL;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public String getServiceErrorURL()
  {
    return this.serviceErrorURL;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setErrorCode(int paramInt)
  {
    this.error = paramInt;
  }
  
  public void setPassword(String paramString)
  {
    if (!"****".equals(paramString)) {
      this.password = paramString;
    }
  }
  
  public String getPasswordForDisplay()
  {
    return (this.password == null) || (this.password.length() == 0) ? "" : "****";
  }
  
  public void setConfirmPassword(String paramString)
  {
    if (!"****".equals(paramString)) {
      this.confirmPassword = paramString;
    }
  }
  
  public String getConfirmPassword()
  {
    return this.confirmPassword;
  }
  
  public String getConfirmPasswordForDisplay()
  {
    return (this.confirmPassword == null) || (this.confirmPassword.length() == 0) ? "" : "****";
  }
  
  public String getMaskedPassword()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = getPassword();
    if ((str != null) && (str.length() > 0))
    {
      int i = 0;
      int j = str.length();
      while (i < j)
      {
        localStringBuffer.append('*');
        i++;
      }
    }
    return localStringBuffer.toString();
  }
  
  public void setSecondaryUsersSessionName(String paramString)
  {
    if (paramString == null) {
      this.secondaryUsersSessionName = "SecondaryUsers";
    } else {
      this.secondaryUsersSessionName = paramString;
    }
  }
  
  public void setFieldsToValidate(String paramString)
  {
    this.fieldsToValidate = (paramString == null ? null : paramString.trim());
  }
  
  public String getFieldsToValidate()
  {
    return this.fieldsToValidate;
  }
  
  public void setFieldsToVerify(String paramString)
  {
    this.fieldsToVerify = (paramString == null ? null : paramString.trim());
  }
  
  public String getFieldsToVerify()
  {
    return this.fieldsToVerify;
  }
  
  protected String validateInput()
  {
    String str1 = this.successURL;
    int i = 1;
    if (!validateFieldLength()) {
      return this.taskErrorURL;
    }
    if ((i != 0) && (this.sUser == null))
    {
      this.error = 38;
      str1 = this.taskErrorURL;
      i = 0;
    }
    if ((i != 0) && ((this.secondaryUsersSessionName == null) || (this.secondaryUsersSessionName.length() == 0)))
    {
      this.error = 28013;
      str1 = this.taskErrorURL;
      i = 0;
    }
    if ((i != 0) && (this.fieldsToValidate != null) && (this.fieldsToValidate.length() > 0))
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(this.fieldsToValidate, ",");
      String str2 = null;
      while ((i != 0) && (localStringTokenizer.hasMoreTokens()))
      {
        str2 = localStringTokenizer.nextToken();
        String str3;
        if ("FIRST_NAME".equals(str2))
        {
          str3 = getFirstName();
          if ((str3 == null) || (str3.length() == 0))
          {
            this.error = 28015;
            str1 = this.taskErrorURL;
            i = 0;
          }
        }
        else if ("LAST_NAME".equals(str2))
        {
          str3 = getLastName();
          if ((str3 == null) || (str3.length() == 0))
          {
            this.error = 28016;
            str1 = this.taskErrorURL;
            i = 0;
          }
        }
        else if ("USER_NAME".equals(str2))
        {
          str3 = getUserName();
          if ((str3 == null) || (str3.length() == 0))
          {
            this.error = 28017;
            str1 = this.taskErrorURL;
            i = 0;
          }
          else
          {
            try
            {
              if (UserAdmin.userExists(this.sUser, str3, getBankId(), new HashMap()))
              {
                this.error = 28048;
                str1 = this.taskErrorURL;
                i = 0;
              }
            }
            catch (CSILException localCSILException)
            {
              this.error = MapError.mapError(localCSILException);
              str1 = this.serviceErrorURL;
              i = 0;
            }
          }
        }
        else if ("USER_PASSWORD".equals(str2))
        {
          str3 = getPassword();
          if ((str3 == null) || (str3.length() == 0))
          {
            this.error = 28018;
            str1 = this.taskErrorURL;
            i = 0;
          }
        }
        else if (("USER_CONFIRM_PASSWORD".equals(str2)) && (!jdMethod_int(getPassword(), this.confirmPassword)))
        {
          this.error = 28019;
          str1 = this.taskErrorURL;
          i = 0;
        }
      }
    }
    return str1;
  }
  
  protected String verifyInputFormat()
  {
    String str1 = this.successURL;
    int i = 1;
    if ((i != 0) && (this.fieldsToVerify != null) && (this.fieldsToVerify.length() > 0))
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(this.fieldsToVerify, ",");
      String str2 = null;
      HashMap localHashMap = new HashMap();
      try
      {
        while ((i != 0) && (localStringTokenizer.hasMoreTokens()))
        {
          str2 = localStringTokenizer.nextToken();
          String str3;
          if (("PHONE".equals(str2)) && (getPhone() != null) && (getPhone().length() > 0))
          {
            str3 = Util.validatePhoneFormat(this.sUser, getCountry(), getPhone(), localHashMap);
            if ((str3 == null) || ("".equals(str3)))
            {
              str1 = this.taskErrorURL;
              this.error = 28020;
              i = 0;
            }
            else
            {
              getPhoneValue().setFormat(str3);
            }
          }
          else if (("EMAIL".equals(str2)) && (getEmail() != null))
          {
            if (!getEmailValue().isValid())
            {
              this.error = 28021;
              str1 = this.taskErrorURL;
              i = 0;
            }
          }
          else if (("ZIPCODE".equals(str2)) && (getZipCode() != null) && (getZipCode().length() > 0))
          {
            str3 = Util.validateZipCodeFormat(this.sUser, getCountry(), getZipCode(), localHashMap);
            if ((str3 == null) || ("".equals(str3)))
            {
              this.error = 28022;
              str1 = this.taskErrorURL;
              i = 0;
            }
            else
            {
              getZipCodeValue().setFormat(str3);
            }
          }
          else if (("PASSWORD".equals(str2)) && (getPassword() != null) && (getPassword().length() > 0))
          {
            int j = PasswordUtil.validatePassword(getPassword(), 1);
            if (j != 0)
            {
              this.error = j;
              str1 = this.taskErrorURL;
              i = 0;
            }
          }
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
        i = 0;
      }
    }
    return str1;
  }
  
  protected boolean validateFieldLength()
  {
    if ((getFirstName() != null) && (getFirstName().length() > this.ADD_USER_MAX_NAME_LENGTH)) {
      return setError(160);
    }
    if ((getMiddleName() != null) && (getMiddleName().length() > this.ADD_USER_MAX_NAME_LENGTH)) {
      return setError(161);
    }
    if ((getLastName() != null) && (getLastName().length() > this.ADD_USER_MAX_NAME_LENGTH)) {
      return setError(162);
    }
    if ((this.street != null) && (this.street.length() > this.ADD_USER_MAX_STREET_LENGTH)) {
      return setError(163);
    }
    if ((this.street2 != null) && (this.street2.length() > this.ADD_USER_MAX_STREET_LENGTH)) {
      return setError(163);
    }
    if ((this.city != null) && (this.city.length() > this.ADD_USER_MAX_CITY_LENGTH)) {
      return setError(164);
    }
    if ((this.zipCode != null) && (this.zipCode.getString().length() > this.ADD_USER_MAX_ZIPCODE_LENGTH)) {
      return setError(165);
    }
    if ((this.state != null) && (this.state.length() > this.ADD_USER_MAX_STATE_LENGTH)) {
      return setError(166);
    }
    if ((this.country != null) && (this.country.length() > this.ADD_USER_MAX_COUNTRY_LENGTH)) {
      return setError(167);
    }
    if ((this.phone != null) && (this.phone.getString().length() > this.ADD_USER_MAX_PHONE_LENGTH)) {
      return setError(169);
    }
    if ((this.email != null) && (this.email.getString().length() > this.ADD_USER_MAX_EMAIL_LENGTH)) {
      return setError(171);
    }
    if ((this.title != null) && (this.title.length() > this.ADD_USER_MAX_TITLE_LENGTH)) {
      return setError(173);
    }
    if ((this.userName != null) && (this.userName.length() > this.ADD_USER_MAX_USERNAME_LENGTH)) {
      return setError(175);
    }
    if ((this.password != null) && (this.password.length() > this.ADD_USER_MAX_PASSWORD_LENGTH)) {
      return setError(176);
    }
    if ((this.confirmPassword != null) && (this.confirmPassword.length() > this.ADD_USER_MAX_PASSWORD_LENGTH)) {
      return setError(176);
    }
    if ((this.preferredLanguage != null) && (this.preferredLanguage.length() > this.ADD_USER_MAX_PREFERRED_LANGUAGE_LENGTH)) {
      return setError(181);
    }
    if ((this.accountStatus != null) && (this.accountStatus.length() > this.ADD_USER_MAX_ACCOUNT_STATUS_LENGTH)) {
      return setError(190);
    }
    return true;
  }
  
  private static boolean jdMethod_int(String paramString1, String paramString2)
  {
    boolean bool = false;
    if (paramString1 == paramString2) {
      bool = true;
    } else if ((paramString1 == null) && (paramString2.length() == 0)) {
      bool = true;
    } else if ((paramString2 == null) && (paramString1.length() == 0)) {
      bool = true;
    } else if (paramString1.equals(paramString2)) {
      bool = true;
    }
    return bool;
  }
  
  protected void addHistory()
  {
    HistoryTracker localHistoryTracker = new HistoryTracker(this.sUser, 1, getId());
    logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for com.ffusion.tasks.multiuser.AddSecondaryUser: " + localProfileException.toString());
    }
  }
  
  public boolean setError(int paramInt)
  {
    this.error = paramInt;
    return false;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.multiuser.AddSecondaryUser
 * JD-Core Version:    0.7.0.1
 */