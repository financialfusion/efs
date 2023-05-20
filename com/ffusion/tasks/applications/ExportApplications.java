package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Application;
import com.ffusion.beans.applications.Form;
import com.ffusion.beans.applications.FormField;
import com.ffusion.beans.applications.FormFields;
import com.ffusion.beans.applications.Product;
import com.ffusion.beans.applications.Products;
import com.ffusion.beans.applications.Status;
import com.ffusion.beans.applications.Statuses;
import com.ffusion.beans.util.StringList;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExportApplications
  extends BaseTask
  implements Task
{
  protected StringList appIDs = new StringList();
  protected String delimeter;
  protected String applicationDelimeter;
  protected String otherDelimeter = "";
  protected String otherAppDelimeter = "";
  protected Locale locale;
  protected ResourceBundle resBundle;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (this.delimeter == null)
    {
      this.error = 7390;
      return this.taskErrorURL;
    }
    if ((this.applicationDelimeter == null) && (!this.delimeter.equals("SPREAD_SHEET")))
    {
      this.error = 7391;
      return this.taskErrorURL;
    }
    Products localProducts = (Products)localHttpSession.getAttribute("Products");
    if (localProducts == null)
    {
      this.error = 7250;
      return this.taskErrorURL;
    }
    Statuses localStatuses = (Statuses)localHttpSession.getAttribute("Statuses");
    if (localStatuses == null)
    {
      this.error = 7270;
      return this.taskErrorURL;
    }
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    this.resBundle = ResourceUtil.getBundle("com.ffusion.beans.applications.resources", this.locale);
    if (this.resBundle == null)
    {
      this.error = 7350;
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    return jdMethod_for(paramHttpServletResponse, localSecureUser, localProducts, localStatuses);
  }
  
  public void setAppIDs(String paramString)
  {
    if (paramString.indexOf(",") != -1)
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
      while (localStringTokenizer.hasMoreTokens())
      {
        String str = localStringTokenizer.nextToken();
        this.appIDs.add(Strings.replaceStr(str, " ", ""));
      }
    }
    else
    {
      this.appIDs.add(paramString);
    }
  }
  
  public void setDelimeter(String paramString)
  {
    this.delimeter = paramString;
  }
  
  public void setApplicationDelimeter(String paramString)
  {
    this.applicationDelimeter = paramString;
  }
  
  public void setOtherDelimeter(String paramString)
  {
    this.otherDelimeter = paramString;
  }
  
  public void setOtherAppDelimeter(String paramString)
  {
    this.otherAppDelimeter = paramString;
  }
  
  private String jdMethod_for(HttpServletResponse paramHttpServletResponse, SecureUser paramSecureUser, Products paramProducts, Statuses paramStatuses)
  {
    String str1 = this.successURL;
    HashMap localHashMap = null;
    paramHttpServletResponse.setContentType("application/binary");
    paramHttpServletResponse.setHeader("Content-disposition", "attachment;filename=Export_Multiple");
    ArrayList localArrayList = new ArrayList();
    if (this.delimeter.equals("OTHER")) {
      this.delimeter = this.otherDelimeter;
    }
    if (!this.delimeter.equals("SPREAD_SHEET")) {
      if (this.applicationDelimeter.equals("OTHER")) {
        this.applicationDelimeter = this.otherAppDelimeter;
      } else if (this.applicationDelimeter.equals("NEWLINE")) {
        this.applicationDelimeter = "\r\n";
      }
    }
    String str2 = "\r\n";
    try
    {
      PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
      StringBuffer localStringBuffer = new StringBuffer();
      if (!this.delimeter.equals("SPREAD_SHEET")) {
        localStringBuffer.append(this.resBundle.getString("EXPORT_APPLICATION_RECORDS") + str2 + str2);
      }
      com.ffusion.beans.applications.Applications localApplications = new com.ffusion.beans.applications.Applications(this.locale);
      Iterator localIterator1 = this.appIDs.iterator();
      while (localIterator1.hasNext())
      {
        localObject1 = (String)localIterator1.next();
        localObject2 = new Application(this.locale);
        ((Application)localObject2).setAppID((String)localObject1);
        try
        {
          localObject2 = com.ffusion.csil.core.Applications.getApplication(paramSecureUser, (Application)localObject2, true, localHashMap);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str1 = this.serviceErrorURL;
          return str1;
        }
        localApplications.add(localObject2);
      }
      localApplications.setSortedBy("PRODUCT_ID");
      Object localObject1 = "0";
      Object localObject2 = localApplications.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        Application localApplication = (Application)((Iterator)localObject2).next();
        String str3 = localApplication.getStatusID();
        Status localStatus = paramStatuses.getByID(str3);
        String str4 = localApplication.getProductID();
        Product localProduct = paramProducts.getByID(str4);
        String str5 = localApplication.getBankID();
        Form localForm = localApplication.getForm();
        FormFields localFormFields = localForm.getFormFields();
        Iterator localIterator2 = localFormFields.iterator();
        FormField localFormField;
        if (this.delimeter.equals("SPREAD_SHEET"))
        {
          if (!((String)localObject1).equals(str4))
          {
            localObject1 = str4;
            localStringBuffer.append(str2 + localProduct.getTitle() + str2 + str2);
            localStringBuffer.append(this.resBundle.getString("EXPORT_EMAIL_ADDRESS") + "," + this.resBundle.getString("EXPORT_SSN"));
            localStringBuffer.append("," + this.resBundle.getString("EXPORT_TRACKING_NUMBER") + "," + this.resBundle.getString("EXPORT_APP_ID") + "," + this.resBundle.getString("EXPORT_BANK_ID") + "," + this.resBundle.getString("EXPORT_PRODUCT"));
            localStringBuffer.append("," + this.resBundle.getString("EXPORT_STATUS") + "," + this.resBundle.getString("EXPORT_DATE_SUBMITTED") + "," + this.resBundle.getString("EXPORT_FIRST_NAME") + "," + this.resBundle.getString("EXPORT_LAST_NAME") + ",");
            while (localIterator2.hasNext())
            {
              localFormField = (FormField)localIterator2.next();
              localStringBuffer.append(localFormField.getDisplayName() + ",");
            }
            localStringBuffer.append(str2);
          }
          localStringBuffer.append(localApplication.getEmailAddress() + ",");
          localStringBuffer.append(localApplication.getSsn() + ",");
          localStringBuffer.append(localApplication.getTrackingNumber() + ",");
          localStringBuffer.append(localApplication.getAppID() + ",");
          localStringBuffer.append(str5 + ",");
          localStringBuffer.append(localProduct.getTitle() + ",");
          localStringBuffer.append(localStatus.getName() + ",");
          localStringBuffer.append(localApplication.getCreateDateString() + ",");
          localStringBuffer.append(localApplication.getFirstName() + ",");
          localStringBuffer.append(localApplication.getLastName() + ",");
          localIterator2 = localFormFields.iterator();
          while (localIterator2.hasNext())
          {
            localFormField = (FormField)localIterator2.next();
            localStringBuffer.append(localFormField.getFieldValue() + ",");
          }
          localStringBuffer.append(str2);
        }
        else
        {
          localStringBuffer.append(this.resBundle.getString("EXPORT_PROCESSING_INFO") + str2);
          localStringBuffer.append(this.resBundle.getString("EXPORT_EMAIL_ADDRESS") + this.delimeter + localApplication.getEmailAddress() + this.delimeter + str2);
          localStringBuffer.append(this.resBundle.getString("EXPORT_SSN") + this.delimeter + localApplication.getSsn() + this.delimeter + str2);
          localStringBuffer.append(this.resBundle.getString("EXPORT_TRACKING_NUMBER") + this.delimeter + localApplication.getTrackingNumber() + this.delimeter + str2);
          localStringBuffer.append(this.resBundle.getString("EXPORT_APP_ID") + this.delimeter + localApplication.getAppID() + this.delimeter + str2);
          localStringBuffer.append(this.resBundle.getString("EXPORT_BANK_ID") + this.delimeter + str5 + this.delimeter + str2);
          localStringBuffer.append(this.resBundle.getString("EXPORT_PRODUCT") + this.delimeter + localProduct.getTitle() + this.delimeter + str2);
          localStringBuffer.append(this.resBundle.getString("EXPORT_STATUS") + this.delimeter + localStatus.getName() + this.delimeter + str2);
          localStringBuffer.append(this.resBundle.getString("EXPORT_DATE_SUBMITTED") + this.delimeter + localApplication.getCreateDateString() + this.delimeter + str2);
          localStringBuffer.append(this.resBundle.getString("EXPORT_FIRST_NAME") + this.delimeter + localApplication.getFirstName() + this.delimeter + str2);
          localStringBuffer.append(this.resBundle.getString("EXPORT_LAST_NAME") + this.delimeter + localApplication.getLastName() + this.delimeter + str2);
          while (localIterator2.hasNext())
          {
            localFormField = (FormField)localIterator2.next();
            localStringBuffer.append(localFormField.getDisplayName() + this.delimeter + localFormField.getFieldValue() + this.delimeter + str2);
          }
          localStringBuffer.append(this.applicationDelimeter + str2);
        }
      }
      localPrintWriter.write(localStringBuffer.toString());
      localPrintWriter.flush();
      localPrintWriter.close();
    }
    catch (IOException localIOException)
    {
      DebugLog.throwing("ExportApplications Task Exception: ", localIOException);
      this.error = 7208;
      str1 = this.taskErrorURL;
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.ExportApplications
 * JD-Core Version:    0.7.0.1
 */