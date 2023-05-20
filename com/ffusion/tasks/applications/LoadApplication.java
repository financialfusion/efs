package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Application;
import com.ffusion.beans.applications.Form;
import com.ffusion.beans.applications.FormField;
import com.ffusion.beans.applications.FormFields;
import com.ffusion.beans.applications.Product;
import com.ffusion.beans.applications.Products;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Applications;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoadApplication
  extends BaseTask
  implements Task
{
  protected String formID;
  protected String formName;
  protected String bankID;
  protected String emailAddressRequired = "false";
  protected String firstNameRequired = "false";
  protected String lastNameRequired = "false";
  protected String ssnRequired = "false";
  protected ResourceBundle resBundle;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HashMap localHashMap = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    Application localApplication = new Application(localLocale);
    localApplication.setBankID(this.bankID);
    Form localForm = new Form();
    this.resBundle = ResourceUtil.getBundle("com.ffusion.beans.applications.resources", localLocale);
    if (this.resBundle == null)
    {
      DebugLog.log("LoadApplication Task Error: resBundleource bundle not found");
      this.error = 7350;
      return this.taskErrorURL;
    }
    if ((this.formID == null) && (this.formName == null))
    {
      this.error = 7202;
      return this.taskErrorURL;
    }
    localForm.setID(this.formID);
    localForm.setName(this.formName);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      localForm = Applications.getForm(localSecureUser, localForm, localHashMap);
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
      str = this.serviceErrorURL;
      return this.serviceErrorURL;
    }
    localApplication.setForm(localForm);
    jdMethod_for(localForm.getFormFields());
    Product localProduct = new Product();
    localProduct.setFormID(localForm.getID());
    localProduct.setBankID(this.bankID);
    Products localProducts = null;
    int i = 0;
    try
    {
      localProducts = Applications.getProducts(localSecureUser, localProduct, localHashMap);
      Iterator localIterator = localProducts.iterator();
      while (localIterator.hasNext())
      {
        localProduct = (Product)localIterator.next();
        if (localProduct.getFormID().equals(localForm.getID())) {
          i = 1;
        }
      }
    }
    catch (CSILException localCSILException2)
    {
      this.error = MapError.mapError(localCSILException2);
      str = this.serviceErrorURL;
      return this.serviceErrorURL;
    }
    if (i == 0)
    {
      this.error = 7252;
      return this.taskErrorURL;
    }
    localApplication.setProductID(localProduct.getProductID());
    localApplication.setCategoryID(localProduct.getCategoryID());
    localApplication.setStatusID("1");
    localHttpSession.setAttribute("Application", localApplication);
    return str;
  }
  
  public void setFormID(String paramString)
  {
    this.formID = paramString;
  }
  
  public void setFormName(String paramString)
  {
    this.formName = paramString;
  }
  
  public void setBankID(String paramString)
  {
    this.bankID = paramString;
  }
  
  public void setEmailAddressRequired(String paramString)
  {
    this.emailAddressRequired = paramString;
  }
  
  public void setFirstNameRequired(String paramString)
  {
    this.firstNameRequired = paramString;
  }
  
  public void setLastNameRequired(String paramString)
  {
    this.lastNameRequired = paramString;
  }
  
  public void setSSNRequired(String paramString)
  {
    this.ssnRequired = paramString;
  }
  
  private void jdMethod_for(FormFields paramFormFields)
  {
    if ((this.ssnRequired.equalsIgnoreCase("true")) && (paramFormFields.getByID("803") == null)) {
      paramFormFields.add(0, new FormField("803", "ssn", this.resBundle.getString("SSN_DISPLAY"), "SSN", "1", this.resBundle.getString("SSN_ERROR"), "text"));
    }
    if ((this.emailAddressRequired.equalsIgnoreCase("true")) && (paramFormFields.getByID("802") == null)) {
      paramFormFields.add(0, new FormField("802", "email_address", this.resBundle.getString("EMAIL_ADDRESS_DISPLAY"), "EMAIL", "1", this.resBundle.getString("EMAIL_ADDRESS_ERROR"), "text"));
    }
    if ((this.lastNameRequired.equalsIgnoreCase("true")) && (paramFormFields.getByID("801") == null)) {
      paramFormFields.add(0, new FormField("801", "last_name", this.resBundle.getString("LAST_NAME_DISPLAY"), "STRNUM_SPACE", "1", this.resBundle.getString("LAST_NAME_ERROR"), "text"));
    }
    if ((this.firstNameRequired.equalsIgnoreCase("true")) && (paramFormFields.getByID("800") == null)) {
      paramFormFields.add(0, new FormField("800", "first_name", this.resBundle.getString("FIRST_NAME_DISPLAY"), "STRNUM_SPACE", "1", this.resBundle.getString("FIRST_NAME_ERROR"), "text"));
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.LoadApplication
 * JD-Core Version:    0.7.0.1
 */