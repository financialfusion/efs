package com.ffusion.services.profile;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Application;
import com.ffusion.beans.applications.ApplicationHistories;
import com.ffusion.beans.applications.ApplicationHistory;
import com.ffusion.beans.applications.Applications;
import com.ffusion.beans.applications.Banks;
import com.ffusion.beans.applications.Categories;
import com.ffusion.beans.applications.Category;
import com.ffusion.beans.applications.Form;
import com.ffusion.beans.applications.Product;
import com.ffusion.beans.applications.Products;
import com.ffusion.beans.applications.Status;
import com.ffusion.beans.applications.Statuses;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.util.StringList;
import com.ffusion.csil.handlers.Messages;
import com.ffusion.efs.adapters.profile.ApplicationAdapter;
import com.ffusion.efs.adapters.profile.BankEmployeeAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.Applications3;
import com.ffusion.services.javax.JavaMail;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;
import java.util.logging.Level;

public class ApplicationService
  implements Applications3
{
  public Banks getBanks(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return ApplicationAdapter.getBanks();
  }
  
  public Product getProduct(SecureUser paramSecureUser, Product paramProduct, HashMap paramHashMap)
    throws ProfileException
  {
    return ApplicationAdapter.getProducts(paramProduct).getByID(paramProduct.getProductID());
  }
  
  public Products getProducts(SecureUser paramSecureUser, Product paramProduct, HashMap paramHashMap)
    throws ProfileException
  {
    return ApplicationAdapter.getProducts(paramProduct);
  }
  
  public void modifyProduct(SecureUser paramSecureUser, Product paramProduct, HashMap paramHashMap)
    throws ProfileException
  {
    ApplicationAdapter.modifyProduct(paramProduct);
  }
  
  public void deleteProduct(SecureUser paramSecureUser, Product paramProduct, HashMap paramHashMap)
    throws ProfileException
  {
    ApplicationAdapter.deleteProduct(paramProduct);
  }
  
  public Application addApplication(SecureUser paramSecureUser, Application paramApplication, String paramString, HashMap paramHashMap)
    throws ProfileException
  {
    Application localApplication = ApplicationAdapter.addApplication(paramApplication, paramString);
    try
    {
      BankEmployee localBankEmployee = new BankEmployee(localApplication.getLocale());
      localBankEmployee.setId(localApplication.getOwner());
      localBankEmployee = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee);
      if ((localBankEmployee.getNotify() != null) && (localBankEmployee.getNotify().equals("1")))
      {
        String str1 = (String)paramHashMap.get("EMPLOYEE_EMAIL_ADDRESS");
        String str2 = (String)paramHashMap.get("EMPLOYEE_EMAIL_SUBJECT");
        String str3 = (String)paramHashMap.get("EMPLOYEE_EMAIL_BODY");
        Messages._mailService.sendMessage(localBankEmployee.getEmail(), str1, str2, str3);
      }
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.INFO, "An email notification for the application submission could not be sent to the bank employee " + localException.getMessage());
    }
    return localApplication;
  }
  
  public Application getApplication(SecureUser paramSecureUser, Application paramApplication, boolean paramBoolean, HashMap paramHashMap)
    throws ProfileException
  {
    int i;
    try
    {
      i = Integer.valueOf(paramApplication.getAppID()).intValue();
    }
    catch (Exception localException)
    {
      throw new ProfileException(3633, localException);
    }
    return ApplicationAdapter.getApplicationById(i, paramApplication.getSearchLanguage(), paramApplication.getLocale(), new HashMap());
  }
  
  public Applications getApplications(SecureUser paramSecureUser, Application paramApplication, String paramString1, String paramString2, String paramString3, String paramString4, HashMap paramHashMap)
    throws ProfileException
  {
    return ApplicationAdapter.getApplicationList(paramApplication, paramString1, paramString2, paramString3, paramString4);
  }
  
  public void deleteApplication(SecureUser paramSecureUser, Application paramApplication, HashMap paramHashMap)
    throws ProfileException
  {
    int i;
    try
    {
      i = Integer.valueOf(paramApplication.getAppID()).intValue();
    }
    catch (Exception localException)
    {
      throw new ProfileException(3633, localException);
    }
    ApplicationAdapter.deleteApplication(i);
  }
  
  public Application modifyApplication(SecureUser paramSecureUser, Application paramApplication, String paramString, HashMap paramHashMap)
    throws ProfileException
  {
    return ApplicationAdapter.modifyApplication(paramApplication, paramString, paramSecureUser.getProfileID());
  }
  
  public Applications modifyApplications(SecureUser paramSecureUser, StringList paramStringList, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws ProfileException
  {
    int i;
    try
    {
      i = Integer.valueOf(paramString2).intValue();
    }
    catch (Exception localException1)
    {
      throw new ProfileException(3631, localException1);
    }
    int j;
    try
    {
      j = Integer.valueOf(paramString1).intValue();
    }
    catch (Exception localException2)
    {
      throw new ProfileException(3749, localException2);
    }
    Applications localApplications = ApplicationAdapter.modifyApplications(paramStringList, j, i, paramString3, paramSecureUser.getProfileID());
    localApplications.setLocale(paramSecureUser.getLocale());
    return localApplications;
  }
  
  public void deleteStatus(SecureUser paramSecureUser, Status paramStatus, HashMap paramHashMap)
    throws ProfileException
  {
    ApplicationAdapter.deleteStatus(paramStatus);
  }
  
  public Status addStatus(SecureUser paramSecureUser, Status paramStatus, HashMap paramHashMap)
    throws ProfileException
  {
    return ApplicationAdapter.addProductStatus(paramStatus);
  }
  
  public void modifyStatus(SecureUser paramSecureUser, Status paramStatus, HashMap paramHashMap)
    throws ProfileException
  {
    ApplicationAdapter.modifyStatus(paramStatus);
  }
  
  public Status getStatus(SecureUser paramSecureUser, Status paramStatus, HashMap paramHashMap)
    throws ProfileException
  {
    return ApplicationAdapter.getStatus(paramStatus).getByID(paramStatus.getID());
  }
  
  public Statuses getStatuses(SecureUser paramSecureUser, Status paramStatus, HashMap paramHashMap)
    throws ProfileException
  {
    return ApplicationAdapter.getProductStatuses(paramStatus.getBankID());
  }
  
  public Categories getCategories(SecureUser paramSecureUser, Category paramCategory, HashMap paramHashMap)
    throws ProfileException
  {
    Categories localCategories = ApplicationAdapter.getCategories(paramCategory.getBankID());
    localCategories.setLocale(paramSecureUser.getLocale());
    return localCategories;
  }
  
  public Form getForm(SecureUser paramSecureUser, Form paramForm, HashMap paramHashMap)
    throws ProfileException
  {
    return ApplicationAdapter.getFormById(paramForm);
  }
  
  public int getApplicationCount(SecureUser paramSecureUser, Application paramApplication, String paramString1, String paramString2, String paramString3, String paramString4, HashMap paramHashMap)
    throws ProfileException
  {
    return ApplicationAdapter.getApplicationCount(paramApplication, paramString1, paramString2);
  }
  
  public ApplicationHistories getHistories(SecureUser paramSecureUser, ApplicationHistory paramApplicationHistory, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException
  {
    return ApplicationAdapter.getApplicationHistories(paramApplicationHistory, paramString1, paramString2);
  }
  
  public void initialize(String paramString)
    throws ProfileException
  {}
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.profile.ApplicationService
 * JD-Core Version:    0.7.0.1
 */