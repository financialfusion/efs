package com.ffusion.tasks.applications;

import com.ffusion.beans.applications.Application;
import com.ffusion.beans.applications.Form;
import com.ffusion.beans.applications.FormField;
import com.ffusion.beans.applications.FormFields;
import com.ffusion.beans.applications.Product;
import com.ffusion.beans.applications.Products;
import com.ffusion.beans.applications.Status;
import com.ffusion.beans.applications.Statuses;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExportApplication
  extends BaseTask
  implements Task
{
  protected String appID = "-1";
  protected String delimeter;
  protected String otherDelimeter = "";
  protected ResourceBundle resBundle;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.delimeter == null)
    {
      this.error = 7390;
      return this.taskErrorURL;
    }
    Application localApplication = (Application)localHttpSession.getAttribute("Application");
    if (localApplication == null)
    {
      this.error = 7201;
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
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    this.resBundle = ResourceUtil.getBundle("com.ffusion.beans.applications.resources", localLocale);
    if (this.resBundle == null)
    {
      this.error = 7350;
      return this.taskErrorURL;
    }
    boolean bool = jdMethod_for(localHttpSession, paramHttpServletResponse, localApplication, localProducts, localStatuses);
    if (bool) {
      return this.successURL;
    }
    this.error = 7207;
    return this.taskErrorURL;
  }
  
  public void setAppID(String paramString)
  {
    this.appID = paramString;
  }
  
  public void setDelimeter(String paramString)
  {
    this.delimeter = paramString;
  }
  
  public void setOtherDelimeter(String paramString)
  {
    this.otherDelimeter = paramString;
  }
  
  private boolean jdMethod_for(HttpSession paramHttpSession, HttpServletResponse paramHttpServletResponse, Application paramApplication, Products paramProducts, Statuses paramStatuses)
  {
    String str1 = "\r\n";
    paramHttpServletResponse.setContentType("application/binary");
    paramHttpServletResponse.setHeader("Content-disposition", "attachment;filename=Export");
    if (this.delimeter.equals("OTHER")) {
      this.delimeter = this.otherDelimeter;
    }
    try
    {
      PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
      StringBuffer localStringBuffer = new StringBuffer();
      if (!this.delimeter.equals("SPREAD_SHEET")) {
        localStringBuffer.append(this.resBundle.getString("EXPORT_APPLICATION_RECORD") + str1 + str1);
      }
      String str2 = paramApplication.getStatusID();
      Status localStatus = paramStatuses.getByID(str2);
      String str3 = paramApplication.getProductID();
      Product localProduct = paramProducts.getByID(str3);
      String str4 = paramApplication.getBankID();
      Form localForm = paramApplication.getForm();
      FormFields localFormFields = localForm.getFormFields();
      Iterator localIterator = localFormFields.iterator();
      FormField localFormField;
      if (this.delimeter.equals("SPREAD_SHEET"))
      {
        this.delimeter = ",";
        localStringBuffer.append(this.resBundle.getString("EXPORT_EMAIL_ADDRESS") + "," + this.resBundle.getString("EXPORT_SSN"));
        localStringBuffer.append("," + this.resBundle.getString("EXPORT_TRACKING_NUMBER") + "," + this.resBundle.getString("EXPORT_APP_ID") + "," + this.resBundle.getString("EXPORT_BANK_ID") + "," + this.resBundle.getString("EXPORT_PRODUCT"));
        localStringBuffer.append("," + this.resBundle.getString("EXPORT_STATUS") + "," + this.resBundle.getString("EXPORT_DATE_SUBMITTED") + "," + this.resBundle.getString("EXPORT_FIRST_NAME") + "," + this.resBundle.getString("EXPORT_LAST_NAME") + ",");
        while (localIterator.hasNext())
        {
          localFormField = (FormField)localIterator.next();
          localStringBuffer.append(localFormField.getDisplayName() + this.delimeter);
        }
        localStringBuffer.append(str1);
        localStringBuffer.append(paramApplication.getEmailAddress() + this.delimeter);
        localStringBuffer.append(paramApplication.getSsn() + this.delimeter);
        localStringBuffer.append(paramApplication.getTrackingNumber() + this.delimeter);
        localStringBuffer.append(paramApplication.getAppID() + this.delimeter);
        localStringBuffer.append(str4 + this.delimeter);
        localStringBuffer.append(localProduct.getTitle() + this.delimeter);
        localStringBuffer.append(localStatus.getName() + this.delimeter);
        localStringBuffer.append(paramApplication.getCreateDateString() + this.delimeter);
        localStringBuffer.append(paramApplication.getFirstName() + this.delimeter);
        localStringBuffer.append(paramApplication.getLastName() + this.delimeter);
        localIterator = localFormFields.iterator();
        while (localIterator.hasNext())
        {
          localFormField = (FormField)localIterator.next();
          localStringBuffer.append(localFormField.getFieldValue() + this.delimeter);
        }
      }
      localStringBuffer.append(this.resBundle.getString("EXPORT_FIRST_NAME") + this.delimeter + paramApplication.getFirstName() + this.delimeter + str1);
      localStringBuffer.append(this.resBundle.getString("EXPORT_LAST_NAME") + this.delimeter + paramApplication.getLastName() + this.delimeter + str1);
      localStringBuffer.append(this.resBundle.getString("EXPORT_EMAIL_ADDRESS") + this.delimeter + paramApplication.getEmailAddress() + this.delimeter + str1);
      localStringBuffer.append(this.resBundle.getString("EXPORT_SSN") + this.delimeter + paramApplication.getSsn() + this.delimeter + str1);
      localStringBuffer.append(this.resBundle.getString("EXPORT_TRACKING_NUMBER") + this.delimeter + paramApplication.getTrackingNumber() + this.delimeter + str1);
      localStringBuffer.append(this.resBundle.getString("EXPORT_APP_ID") + this.delimeter + paramApplication.getAppID() + this.delimeter + str1);
      localStringBuffer.append(this.resBundle.getString("EXPORT_BANK_ID") + this.delimeter + str4 + this.delimeter + str1);
      localStringBuffer.append(this.resBundle.getString("EXPORT_PRODUCT") + this.delimeter + localProduct.getTitle() + this.delimeter + str1);
      localStringBuffer.append(this.resBundle.getString("EXPORT_STATUS") + this.delimeter + localStatus.getName() + this.delimeter + str1);
      localStringBuffer.append(this.resBundle.getString("EXPORT_DATE_SUBMITTED") + this.delimeter + paramApplication.getCreateDateString() + this.delimeter + str1 + str1);
      while (localIterator.hasNext())
      {
        localFormField = (FormField)localIterator.next();
        localStringBuffer.append(localFormField.getDisplayName() + this.delimeter + localFormField.getFieldValue() + this.delimeter + str1);
      }
      localPrintWriter.write(localStringBuffer.toString());
      localPrintWriter.flush();
      localPrintWriter.close();
    }
    catch (Exception localException)
    {
      DebugLog.throwing("ExportApplication Task Exception: ", localException);
      return false;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.ExportApplication
 * JD-Core Version:    0.7.0.1
 */