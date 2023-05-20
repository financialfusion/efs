package com.ffusion.services;

import com.ffusion.beans.applications.Application;
import com.ffusion.beans.applications.ApplicationHistories;
import com.ffusion.beans.applications.ApplicationHistory;
import com.ffusion.beans.applications.Banks;
import com.ffusion.beans.applications.Categories;
import com.ffusion.beans.applications.Category;
import com.ffusion.beans.applications.Form;
import com.ffusion.beans.applications.Product;
import com.ffusion.beans.applications.ProductAccess;
import com.ffusion.beans.applications.ProductAccesses;
import com.ffusion.beans.applications.Products;
import com.ffusion.beans.applications.Status;
import com.ffusion.beans.applications.Statuses;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.util.StringList;
import java.io.Serializable;

public abstract interface Applications
  extends Serializable
{
  public static final int SERVICE_ERROR_INVALID_SIGNON = 7000;
  public static final int SERVICE_ERROR_NO_DATABASE_CONNECTION = 7001;
  public static final int SERVICE_ERROR_UNABLE_TO_COMPLETE_REQUEST = 7002;
  public static final int SERVICE_ERROR_INVALID_REQUEST = 7003;
  public static final int SERVICE_ERROR_MODIFYING_APPLICATION = 7004;
  public static final int SERVICE_ERROR_DELETING_APPLICATION = 7005;
  public static final int SERVICE_ERROR_GETTING_APPLICATIONS = 7006;
  public static final int SERVICE_ERROR_GETTING_APPLICATION = 7007;
  public static final int SERVICE_ERROR_ADDING_APPLICATION = 7008;
  public static final int SERVICE_ERROR_NO_APPLICATIONS = 7009;
  public static final int SERVICE_ERROR_GETTING_APPLICATION_COUNT = 7010;
  public static final int SERVICE_ERROR_GETTING_CATEGORY = 7020;
  public static final int SERVICE_ERROR_NO_CATEGORIES = 7021;
  public static final int SERVICE_ERROR_GETTING_PRODUCTS = 7030;
  public static final int SERVICE_ERROR_GETTING_PRODUCT = 7031;
  public static final int SERVICE_ERROR_MODIFYING_PRODUCT = 7032;
  public static final int SERVICE_ERROR_DELETING_PRODUCT = 7033;
  public static final int SERVICE_ERROR_NO_PRODUCTS = 7034;
  public static final int SERVICE_ERROR_GETTING_STATUS = 7050;
  public static final int SERVICE_ERROR_DELETING_STATUS = 7051;
  public static final int SERVICE_ERROR_CREATING_STATUS = 7052;
  public static final int SERVICE_ERROR_NO_STATUSES = 7053;
  public static final int SERVICE_ERROR_GETTING_FORM = 7060;
  public static final int SERVICE_ERROR_NO_FORM = 7061;
  public static final int SERVICE_ERROR_GETTING_FORM_FIELDS = 7062;
  public static final int SERVICE_ERROR_ADDING_PRODUCT_ACCESS = 7071;
  public static final int SERVICE_ERROR_DELETING_PRODUCT_ACCESS = 7072;
  public static final int SERVICE_ERROR_GETTING_PRODUCT_ACCESS = 7073;
  public static final int SERVICE_ERROR_NO_PRODUCT_ACCESSES = 7074;
  public static final int SERVICE_ERROR_GETTING_BANKS = 7080;
  public static final int SERVICE_ERROR_NO_BANKS = 7081;
  public static final int SERVICE_ERROR_NO_HISTORY = 7090;
  public static final int SERVICE_ERROR_NOTIFYING_EMPLOYEES = 7091;
  
  public abstract int getBanks(Banks paramBanks);
  
  public abstract int getProduct(Product paramProduct);
  
  public abstract int getProducts(Products paramProducts, Product paramProduct);
  
  public abstract int modifyProduct(Product paramProduct);
  
  public abstract int deleteProduct(Product paramProduct);
  
  public abstract int addProductAccess(ProductAccess paramProductAccess);
  
  public abstract int getProductAccesses(ProductAccesses paramProductAccesses, ProductAccess paramProductAccess);
  
  public abstract int deleteProductAccess(ProductAccess paramProductAccess);
  
  public abstract int addApplication(Application paramApplication, String paramString);
  
  public abstract int getApplication(Application paramApplication, boolean paramBoolean);
  
  public abstract int getApplications(com.ffusion.beans.applications.Applications paramApplications, Application paramApplication, String paramString1, String paramString2, String paramString3, String paramString4);
  
  public abstract int getFullApplications(com.ffusion.beans.applications.Applications paramApplications, Application paramApplication, StringList paramStringList);
  
  public abstract int deleteApplication(Application paramApplication);
  
  public abstract int modifyApplication(Application paramApplication, String paramString);
  
  public abstract int modifyApplications(StringList paramStringList, com.ffusion.beans.applications.Applications paramApplications, String paramString1, String paramString2, String paramString3);
  
  public abstract int deleteStatus(Status paramStatus);
  
  public abstract int addStatus(Status paramStatus, StringBuffer paramStringBuffer);
  
  public abstract int modifyStatus(Status paramStatus);
  
  public abstract int getStatus(Status paramStatus);
  
  public abstract int getStatuses(Statuses paramStatuses, Status paramStatus);
  
  public abstract int getCategories(Categories paramCategories, Category paramCategory);
  
  public abstract int getForm(Form paramForm);
  
  public abstract int getApplicationCount(Application paramApplication, String paramString1, String paramString2, String paramString3, String paramString4, StringBuffer paramStringBuffer);
  
  public abstract int getHistories(ApplicationHistories paramApplicationHistories, ApplicationHistory paramApplicationHistory, String paramString1, String paramString2);
  
  public abstract int signOn(BankEmployee paramBankEmployee);
  
  public abstract void setInitURL(String paramString);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Applications
 * JD-Core Version:    0.7.0.1
 */