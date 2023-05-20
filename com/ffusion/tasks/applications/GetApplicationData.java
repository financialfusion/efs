package com.ffusion.tasks.applications;

import com.ffusion.beans.applications.Application;
import com.ffusion.beans.applications.Form;
import com.ffusion.beans.applications.FormField;
import com.ffusion.beans.applications.FormFields;
import com.ffusion.beans.util.StringList;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.Strings;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetApplicationData
  extends BaseTask
  implements Task
{
  protected StringList pageFields = new StringList();
  
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
      Form localForm = localApplication.getForm();
      HashMap localHashMap = localApplication.getFieldValues();
      FormFields localFormFields = localForm.getFormFields();
      Iterator localIterator = localFormFields.iterator();
      String str1 = (String)localHashMap.get("first_name");
      if (str1 == null) {
        str1 = (String)localHashMap.get("FIRST_NAME");
      }
      if (str1 != null) {
        localApplication.setFirstName(str1);
      }
      str1 = (String)localHashMap.get("last_name");
      if (str1 == null) {
        str1 = (String)localHashMap.get("LAST_NAME");
      }
      if (str1 != null) {
        localApplication.setLastName(str1);
      }
      str1 = (String)localHashMap.get("ssn");
      if (str1 == null) {
        str1 = (String)localHashMap.get("SSN");
      }
      if (str1 != null) {
        localApplication.setSsn(str1);
      }
      str1 = (String)localHashMap.get("email_address");
      if (str1 == null) {
        str1 = (String)localHashMap.get("EMAIL_ADDRESS");
      }
      if (str1 != null) {
        localApplication.setEmailAddress(str1);
      }
      while (localIterator.hasNext())
      {
        FormField localFormField = (FormField)localIterator.next();
        String str2 = localFormField.getFieldName();
        str1 = (String)localHashMap.get(str2);
        if ((localFormField.getControlType().equalsIgnoreCase("checkbox")) && (!jdMethod_for(paramHttpServletRequest, str2)) && ((this.pageFields.size() == 0) || (h(str2))))
        {
          localFormField.setFieldValue("");
          localHashMap.put(str2, "");
        }
        else
        {
          localFormField.setFieldValue(str1);
        }
      }
      localApplication.setStatusID("1");
      return this.successURL;
    }
    catch (Exception localException)
    {
      DebugLog.throwing("GetApplicationData Task Exception: ", localException);
      this.error = 7380;
    }
    return this.taskErrorURL;
  }
  
  private boolean jdMethod_for(HttpServletRequest paramHttpServletRequest, String paramString)
  {
    Enumeration localEnumeration = paramHttpServletRequest.getParameterNames();
    while (localEnumeration.hasMoreElements())
    {
      String str1 = (String)localEnumeration.nextElement();
      int i = str1.indexOf(paramString);
      if (i != -1)
      {
        int j = str1.indexOf('&', i);
        String str2 = str1.substring(i, j);
        if (str2.equalsIgnoreCase(paramString)) {
          return true;
        }
      }
    }
    return false;
  }
  
  public void setPageFields(String paramString)
  {
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
  
  private boolean h(String paramString)
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
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.GetApplicationData
 * JD-Core Version:    0.7.0.1
 */