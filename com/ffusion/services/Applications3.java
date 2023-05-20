package com.ffusion.services;

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
import com.ffusion.beans.util.StringList;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.HashMap;

public abstract interface Applications3
{
  public abstract Banks getBanks(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Product getProduct(SecureUser paramSecureUser, Product paramProduct, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Products getProducts(SecureUser paramSecureUser, Product paramProduct, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void modifyProduct(SecureUser paramSecureUser, Product paramProduct, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void deleteProduct(SecureUser paramSecureUser, Product paramProduct, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Application addApplication(SecureUser paramSecureUser, Application paramApplication, String paramString, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Application getApplication(SecureUser paramSecureUser, Application paramApplication, boolean paramBoolean, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Applications getApplications(SecureUser paramSecureUser, Application paramApplication, String paramString1, String paramString2, String paramString3, String paramString4, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void deleteApplication(SecureUser paramSecureUser, Application paramApplication, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Application modifyApplication(SecureUser paramSecureUser, Application paramApplication, String paramString, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Applications modifyApplications(SecureUser paramSecureUser, StringList paramStringList, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void deleteStatus(SecureUser paramSecureUser, Status paramStatus, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Status addStatus(SecureUser paramSecureUser, Status paramStatus, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void modifyStatus(SecureUser paramSecureUser, Status paramStatus, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Status getStatus(SecureUser paramSecureUser, Status paramStatus, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Statuses getStatuses(SecureUser paramSecureUser, Status paramStatus, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Categories getCategories(SecureUser paramSecureUser, Category paramCategory, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Form getForm(SecureUser paramSecureUser, Form paramForm, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract int getApplicationCount(SecureUser paramSecureUser, Application paramApplication, String paramString1, String paramString2, String paramString3, String paramString4, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract ApplicationHistories getHistories(SecureUser paramSecureUser, ApplicationHistory paramApplicationHistory, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void initialize(String paramString)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Applications3
 * JD-Core Version:    0.7.0.1
 */