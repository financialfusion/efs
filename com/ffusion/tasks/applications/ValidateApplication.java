package com.ffusion.tasks.applications;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Application;
import com.ffusion.beans.applications.Form;
import com.ffusion.beans.applications.FormField;
import com.ffusion.beans.applications.FormFields;
import com.ffusion.beans.util.StateProvinceDefn;
import com.ffusion.beans.util.StateProvinceDefns;
import com.ffusion.beans.util.StringList;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.FieldValidation;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ValidateApplication
  extends BaseTask
  implements Task
{
  protected String failValidationURL;
  protected String errorString = "";
  protected StringList pageFields = new StringList();
  protected ResourceBundle resBundle;
  protected String returnURL = "";
  protected ResourceBundle userResBundle;
  private static final String uF = "<br>";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      Application localApplication = (Application)localHttpSession.getAttribute("Application");
      if (localApplication == null)
      {
        this.error = 7201;
        return this.taskErrorURL;
      }
      localApplication.setErrorString("");
      Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
      this.resBundle = ResourceUtil.getBundle("com.ffusion.beans.applications.resources", localLocale);
      if (this.resBundle == null)
      {
        this.error = 7350;
        DebugLog.log("ValidateApplication Task Error: " + this.error);
        return this.taskErrorURL;
      }
      this.userResBundle = ResourceUtil.getBundle("com.ffusion.beans.user.resources", localLocale);
      if (this.userResBundle == null)
      {
        this.error = 7351;
        DebugLog.log("ValidateApplication Task Error: " + this.error);
        return this.taskErrorURL;
      }
      String str = validate(paramHttpServletRequest, localApplication);
      if (!str.equals(""))
      {
        localApplication.setErrorString(str);
        return this.failValidationURL;
      }
      return this.successURL;
    }
    catch (Exception localException)
    {
      DebugLog.throwing("ValidateApplication Task Exception: ", localException);
      this.error = 7380;
    }
    return this.taskErrorURL;
  }
  
  public void setReturnURL(String paramString)
  {
    this.returnURL = paramString;
  }
  
  public String getReturnURL()
  {
    return this.returnURL;
  }
  
  public void setValidateFailURL(String paramString)
  {
    this.failValidationURL = paramString;
  }
  
  public void setPageFields(String paramString)
  {
    this.pageFields.clear();
    if (paramString.indexOf(",") != -1)
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
      while (localStringTokenizer.hasMoreTokens())
      {
        String str = localStringTokenizer.nextToken();
        this.pageFields.add(Strings.replaceStr(str, " ", ""));
      }
    }
    else
    {
      this.pageFields.add(paramString);
    }
  }
  
  private boolean i(String paramString)
  {
    Iterator localIterator = this.pageFields.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (paramString.equals(str)) {
        return true;
      }
    }
    return false;
  }
  
  protected String validate(HttpServletRequest paramHttpServletRequest, Application paramApplication)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession(false);
    StringBuffer localStringBuffer = new StringBuffer();
    Form localForm = paramApplication.getForm();
    FormFields localFormFields = localForm.getFormFields();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    FormField localFormField1 = localFormFields.getByFieldName("COUNTRY");
    String str1 = null;
    if (localFormField1 != null)
    {
      str1 = localFormField1.getFieldValue();
      if ((str1 == null) || ((str1 != null) && (str1.trim().length() == 0))) {
        if (localFormField1.getRequired().equals("1")) {
          jdMethod_for(localLocale, localStringBuffer, localFormField1.getDisplayName(), "REQUIRED");
        } else {
          str1 = null;
        }
      }
    }
    if (str1 != null) {
      try
      {
        if (!Util.isRegisteredCountry(localSecureUser, str1, new HashMap()))
        {
          jdMethod_for(localLocale, localStringBuffer, localFormField1.getErrorString(), "COUNTRY");
          str1 = null;
        }
      }
      catch (CSILException localCSILException)
      {
        jdMethod_for(localLocale, localStringBuffer, localFormField1.getErrorString(), "COUNTRY");
        str1 = null;
      }
    }
    String str2 = null;
    Iterator localIterator = localFormFields.iterator();
    while (localIterator.hasNext())
    {
      FormField localFormField2 = (FormField)localIterator.next();
      str2 = localFormField2.getFieldName();
      if ((this.pageFields.size() == 0) || (i(localFormField2.getFieldName()))) {
        jdMethod_for(paramHttpServletRequest, localFormFields, localFormField2, localSecureUser, str1, localLocale, localStringBuffer);
      }
    }
    jdMethod_for(localStringBuffer);
    return localStringBuffer.toString();
  }
  
  private void jdMethod_for(Locale paramLocale, StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    if (paramStringBuffer == null) {
      return;
    }
    paramStringBuffer.append("<br>");
    Object[] arrayOfObject = new Object[1];
    if (paramString1 != null) {
      arrayOfObject[0] = paramString1;
    } else {
      arrayOfObject[0] = "";
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.applications.resources", paramString2, arrayOfObject);
    paramStringBuffer.append((String)localLocalizableString.localize(paramLocale));
  }
  
  private void jdMethod_for(HttpServletRequest paramHttpServletRequest, FormFields paramFormFields, FormField paramFormField, SecureUser paramSecureUser, String paramString, Locale paramLocale, StringBuffer paramStringBuffer)
  {
    String str1 = paramFormField.getErrorString();
    String str2 = paramFormField.getFieldValue();
    try
    {
      if ((str2 == null) || (str2.equals(null)) || (str2.trim().equalsIgnoreCase("")))
      {
        if ((paramFormField.getRequired().equals("1")) || (!jdMethod_for(paramFormFields, paramFormField, paramHttpServletRequest))) {
          jdMethod_for(paramLocale, paramStringBuffer, paramFormField.getDisplayName(), "REQUIRED");
        }
      }
      else
      {
        int i = paramFormField.getMinValueInt();
        int j = paramFormField.getMaxValueInt();
        if (((i != -1) && (str2.length() < i)) || ((j != -1) && (str2.length() > j))) {
          jdMethod_for(paramLocale, paramStringBuffer, paramFormField.getDisplayName(), "MINMAX");
        }
        String str3 = paramFormField.getFieldType();
        if (str3 == null) {
          return;
        }
        if (str3.equalsIgnoreCase("SSN"))
        {
          if (paramString != null) {
            try
            {
              String str4 = Util.validateSSNFormat(paramSecureUser, paramString, str2, new HashMap());
              if ((str4 == null) || (str4.equals(""))) {
                jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
              }
            }
            catch (CSILException localCSILException1)
            {
              jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
            }
          }
        }
        else if (str3.equalsIgnoreCase("CURRENCY"))
        {
          if (!Currency.isValid(str2, paramLocale)) {
            jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
          }
        }
        else
        {
          Object localObject1;
          Object localObject2;
          if (str3.equalsIgnoreCase("TIME"))
          {
            localObject1 = this.userResBundle.getString("TimeFormat");
            if (localObject1 != null)
            {
              localObject2 = DateFormatUtil.getFormatter((String)localObject1, paramLocale);
              try
              {
                ((DateFormat)localObject2).parse(str2);
              }
              catch (Exception localException2)
              {
                jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
              }
            }
            else
            {
              jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
            }
          }
          else if (str3.equalsIgnoreCase("DATE"))
          {
            localObject1 = this.userResBundle.getString("DateFormat");
            if (localObject1 != null)
            {
              localObject2 = DateFormatUtil.getFormatter((String)localObject1, paramLocale);
              try
              {
                ((DateFormat)localObject2).parse(str2);
              }
              catch (Exception localException3)
              {
                jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
              }
            }
            else
            {
              jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
            }
          }
          else if (str3.equalsIgnoreCase("NUM"))
          {
            if (!FieldValidation.num(str2, false)) {
              jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
            }
          }
          else if (str3.equalsIgnoreCase("NUM_SPACE"))
          {
            if (!FieldValidation.num(str2, true)) {
              jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
            }
          }
          else if (str3.equalsIgnoreCase("STRNUM"))
          {
            if (!FieldValidation.strnum(str2, "")) {
              jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
            }
          }
          else if (str3.equalsIgnoreCase("STRNUM_SPACE"))
          {
            if (!FieldValidation.strnum(str2, " ")) {
              jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
            }
          }
          else if (str3.equalsIgnoreCase("STRING"))
          {
            if (!FieldValidation.string(str2, false)) {
              jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
            }
          }
          else if (str3.equalsIgnoreCase("STRING_SPACE"))
          {
            if (!FieldValidation.string(str2, true)) {
              jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
            }
          }
          else if (str3.equalsIgnoreCase("STATE"))
          {
            if (paramString != null) {
              try
              {
                localObject1 = Util.getStatesForCountry(paramSecureUser, paramString, new HashMap());
                if (localObject1 != null)
                {
                  if (!((StateProvinceDefns)localObject1).isEmpty())
                  {
                    localObject2 = ((StateProvinceDefns)localObject1).iterator();
                    StateProvinceDefn localStateProvinceDefn = null;
                    int k = 0;
                    while (((Iterator)localObject2).hasNext())
                    {
                      localStateProvinceDefn = (StateProvinceDefn)((Iterator)localObject2).next();
                      if ((localStateProvinceDefn.getStateKey() != null) && (localStateProvinceDefn.getStateKey().equalsIgnoreCase(str2))) {
                        k = 1;
                      }
                    }
                    if (k == 0) {
                      jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
                    }
                  }
                }
                else {
                  jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
                }
              }
              catch (CSILException localCSILException2)
              {
                jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
              }
            }
          }
          else if (str3.equalsIgnoreCase("ZIPCODE"))
          {
            if (paramString != null) {
              try
              {
                String str5 = Util.validateZipCodeFormat(paramSecureUser, paramString, str2, new HashMap());
                if ((str5 == null) || (str5.equals(""))) {
                  jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
                }
              }
              catch (CSILException localCSILException3)
              {
                jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
              }
            }
          }
          else if (str3.equalsIgnoreCase("EMAIL"))
          {
            if (!FieldValidation.email(str2)) {
              jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
            }
          }
          else if (str3.equalsIgnoreCase("PHONE"))
          {
            if (paramString != null) {
              try
              {
                String str6 = Util.validatePhoneFormat(paramSecureUser, paramString, str2, new HashMap());
                if ((str6 == null) || (str6.equals(""))) {
                  jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
                }
              }
              catch (CSILException localCSILException4)
              {
                jdMethod_for(paramLocale, paramStringBuffer, str1, str3.toUpperCase());
              }
            }
          }
          else if ((str3.equalsIgnoreCase("EXACT")) && (!str2.equalsIgnoreCase(paramFormField.getExactValue())))
          {
            jdMethod_for(paramLocale, paramStringBuffer, paramFormField.getExactValue(), str3.toUpperCase());
          }
        }
      }
    }
    catch (Exception localException1)
    {
      DebugLog.throwing("ValidateApplication Task Exception: ", localException1);
    }
  }
  
  private boolean jdMethod_for(FormFields paramFormFields, FormField paramFormField, HttpServletRequest paramHttpServletRequest)
  {
    String str1 = paramFormField.getDependField();
    if ((str1 != null) && (str1.trim().length() > 0))
    {
      FormField localFormField = paramFormFields.getByFieldName(str1);
      String str2 = localFormField.getFieldValue();
      String str3 = paramFormField.getDependValue();
      if ((str3 != null) && (str3.trim().length() > 0))
      {
        if ((str2 != null) && (str2.trim().length() > 0) && (str2.trim().equals(paramFormField.getDependValue()))) {
          return false;
        }
        if (str2 != null)
        {
          str2 = str2.trim();
          if (str2.length() > 0)
          {
            StringTokenizer localStringTokenizer = new StringTokenizer(paramFormField.getDependValue(), "|");
            if (localStringTokenizer.countTokens() == 0)
            {
              if (str2.equals(str3)) {
                return false;
              }
            }
            else {
              while (localStringTokenizer.hasMoreTokens()) {
                if (str2.equals(localStringTokenizer.nextToken())) {
                  return false;
                }
              }
            }
          }
        }
      }
      else if ((str2 != null) && (str2.trim().length() > 0))
      {
        return false;
      }
    }
    return true;
  }
  
  private static void jdMethod_for(StringBuffer paramStringBuffer)
  {
    String str = paramStringBuffer.toString();
    if ((str != null) && (str.trim().length() == 0)) {
      return;
    }
    str = Strings.replaceStr(str, "ñ", "&ntilde;");
    str = Strings.replaceStr(str, "á", "&aacute;");
    str = Strings.replaceStr(str, "é", "&eacute;");
    str = Strings.replaceStr(str, "í", "&iacute;");
    str = Strings.replaceStr(str, "ó", "&oacute;");
    str = Strings.replaceStr(str, "ú", "&uacute;");
    str = Strings.replaceStr(str, "Ñ", "&Ntilde;");
    str = Strings.replaceStr(str, "Á", "&Aacute;");
    str = Strings.replaceStr(str, "É", "&Eacute;");
    str = Strings.replaceStr(str, "Í", "&Iacute;");
    str = Strings.replaceStr(str, "Ó", "&Oacute;");
    str = Strings.replaceStr(str, "Ú", "&Uacute;");
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.ValidateApplication
 * JD-Core Version:    0.7.0.1
 */