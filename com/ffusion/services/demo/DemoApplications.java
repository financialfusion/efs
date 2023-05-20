package com.ffusion.services.demo;

import com.ffusion.beans.applications.Application;
import com.ffusion.beans.applications.ApplicationHistories;
import com.ffusion.beans.applications.ApplicationHistory;
import com.ffusion.beans.applications.Applications;
import com.ffusion.beans.applications.Bank;
import com.ffusion.beans.applications.Banks;
import com.ffusion.beans.applications.Categories;
import com.ffusion.beans.applications.Category;
import com.ffusion.beans.applications.EmployeeNotification;
import com.ffusion.beans.applications.EmployeeNotifications;
import com.ffusion.beans.applications.Form;
import com.ffusion.beans.applications.FormField;
import com.ffusion.beans.applications.FormFields;
import com.ffusion.beans.applications.Product;
import com.ffusion.beans.applications.ProductAccess;
import com.ffusion.beans.applications.ProductAccesses;
import com.ffusion.beans.applications.Products;
import com.ffusion.beans.applications.Status;
import com.ffusion.beans.applications.Statuses;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.history.History;
import com.ffusion.beans.util.StringList;
import com.ffusion.services.Applications2;
import com.ffusion.util.XMLHandler;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class DemoApplications
  extends Service
  implements Applications2
{
  Locale ax = new Locale("en", "US");
  Products aw = new Products();
  Applications ap = new Applications(this.ax);
  Banks ao = new Banks();
  ProductAccesses av = new ProductAccesses();
  Statuses an = new Statuses();
  EmployeeNotifications aq = new EmployeeNotifications();
  Categories ay = new Categories();
  ArrayList ar = new ArrayList();
  FormFields au = new FormFields();
  ApplicationHistories as = new ApplicationHistories(this.ax);
  BankEmployee at;
  
  public int getBanks(Banks paramBanks)
  {
    Iterator localIterator = this.ao.iterator();
    while (localIterator.hasNext())
    {
      Bank localBank = (Bank)localIterator.next();
      if (localBank != null) {
        paramBanks.add(localBank);
      }
    }
    return 0;
  }
  
  public int getProduct(Product paramProduct)
  {
    return 0;
  }
  
  public int getProducts(Products paramProducts, Product paramProduct)
  {
    Iterator localIterator = this.aw.iterator();
    while (localIterator.hasNext())
    {
      Product localProduct = (Product)localIterator.next();
      if (localProduct != null) {
        paramProducts.add(localProduct);
      }
    }
    return 0;
  }
  
  public int modifyProduct(Product paramProduct)
  {
    Iterator localIterator = this.aw.iterator();
    for (int i = 0; localIterator.hasNext(); i++)
    {
      Product localProduct = (Product)localIterator.next();
      if (paramProduct.getProductID() == localProduct.getProductID())
      {
        this.aw.set(i, localProduct);
        return 0;
      }
    }
    return -1;
  }
  
  public int deleteProduct(Product paramProduct)
  {
    this.aw.remove(paramProduct);
    return 0;
  }
  
  public int addProductAccess(ProductAccess paramProductAccess)
  {
    this.av.add(paramProductAccess);
    return 0;
  }
  
  public int getProductAccesses(ProductAccesses paramProductAccesses, ProductAccess paramProductAccess)
  {
    Iterator localIterator = this.av.iterator();
    while (localIterator.hasNext())
    {
      ProductAccess localProductAccess = (ProductAccess)localIterator.next();
      if (localProductAccess != null) {
        paramProductAccesses.add(localProductAccess);
      }
    }
    return 0;
  }
  
  public int deleteProductAccess(ProductAccess paramProductAccess)
  {
    this.av.remove(paramProductAccess);
    return 0;
  }
  
  public int addApplication(Application paramApplication, String paramString)
  {
    this.ap.add(paramApplication);
    return 0;
  }
  
  public int getApplication(Application paramApplication, boolean paramBoolean)
  {
    Application localApplication = (Application)this.ap.get(0);
    if (localApplication != null) {
      paramApplication.setXML(localApplication.getXML());
    } else {
      return -1;
    }
    return 0;
  }
  
  public int getApplications(Applications paramApplications, Application paramApplication, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    Iterator localIterator = this.ap.iterator();
    while (localIterator.hasNext())
    {
      Application localApplication = (Application)localIterator.next();
      if (localApplication != null) {
        paramApplications.add(localApplication);
      }
    }
    return 0;
  }
  
  public int getFullApplications(Applications paramApplications, Application paramApplication, StringList paramStringList)
  {
    Iterator localIterator = this.ap.iterator();
    while (localIterator.hasNext())
    {
      Application localApplication = (Application)localIterator.next();
      if (localApplication != null) {
        paramApplications.add(localApplication);
      }
    }
    return 0;
  }
  
  public int deleteApplication(Application paramApplication)
  {
    this.ap.remove(paramApplication);
    return 0;
  }
  
  public int modifyApplication(Application paramApplication, String paramString)
  {
    Iterator localIterator = this.ap.iterator();
    for (int i = 0; localIterator.hasNext(); i++)
    {
      Application localApplication = (Application)localIterator.next();
      if (localApplication.getAppID() == paramApplication.getAppID())
      {
        this.ap.set(i, paramApplication);
        return 0;
      }
    }
    return -1;
  }
  
  public int modifyApplications(StringList paramStringList, Applications paramApplications, String paramString1, String paramString2, String paramString3)
  {
    return 0;
  }
  
  public int deleteStatus(Status paramStatus)
  {
    this.an.remove(paramStatus);
    return 0;
  }
  
  public int addStatus(Status paramStatus, StringBuffer paramStringBuffer)
  {
    this.an.add(paramStatus);
    return 0;
  }
  
  public int modifyStatus(Status paramStatus)
  {
    Iterator localIterator = this.an.iterator();
    for (int i = 0; localIterator.hasNext(); i++)
    {
      Status localStatus = (Status)localIterator.next();
      if (localStatus.getID() == paramStatus.getID())
      {
        this.an.set(i, paramStatus);
        return 0;
      }
    }
    return -1;
  }
  
  public int getStatus(Status paramStatus)
  {
    Iterator localIterator = this.an.iterator();
    while (localIterator.hasNext())
    {
      Status localStatus = (Status)localIterator.next();
      if (localStatus.getID() == paramStatus.getID())
      {
        paramStatus.setXML(localStatus.getXML());
        return 0;
      }
    }
    return -1;
  }
  
  public int getStatuses(Statuses paramStatuses, Status paramStatus)
  {
    Iterator localIterator = this.an.iterator();
    while (localIterator.hasNext())
    {
      Status localStatus = (Status)localIterator.next();
      paramStatuses.add(localStatus);
    }
    return 0;
  }
  
  public int getCategories(Categories paramCategories, Category paramCategory)
  {
    Iterator localIterator = this.ay.iterator();
    while (localIterator.hasNext())
    {
      Category localCategory = (Category)localIterator.next();
      paramCategories.add(localCategory);
    }
    return 0;
  }
  
  public int getForm(Form paramForm)
  {
    try
    {
      int i = Integer.parseInt(paramForm.getID());
      Form localForm = (Form)this.ar.get(i);
      paramForm.setXML(localForm.getXML());
    }
    catch (Exception localException) {}
    return 0;
  }
  
  public int getFormFields(FormFields paramFormFields, FormField paramFormField)
  {
    Iterator localIterator = this.au.iterator();
    while (localIterator.hasNext())
    {
      FormField localFormField = (FormField)localIterator.next();
      paramFormFields.add(localFormField);
    }
    return 0;
  }
  
  public int getEmployeeNotifications(EmployeeNotifications paramEmployeeNotifications, EmployeeNotification paramEmployeeNotification)
  {
    Iterator localIterator = this.aq.iterator();
    for (int i = 0; localIterator.hasNext(); i++)
    {
      EmployeeNotification localEmployeeNotification = (EmployeeNotification)localIterator.next();
      paramEmployeeNotifications.add(localEmployeeNotification);
    }
    if (i == 0) {
      return 1;
    }
    return 0;
  }
  
  public int addEmployeeNotification(EmployeeNotification paramEmployeeNotification)
  {
    return 0;
  }
  
  public int deleteEmployeeNotification(EmployeeNotification paramEmployeeNotification)
  {
    return 0;
  }
  
  public int getApplicationCount(Application paramApplication, String paramString1, String paramString2, String paramString3, String paramString4, StringBuffer paramStringBuffer)
  {
    int i = this.ap.size();
    paramStringBuffer.append(i);
    return 0;
  }
  
  public int getHistories(ApplicationHistories paramApplicationHistories, ApplicationHistory paramApplicationHistory, String paramString1, String paramString2)
  {
    Iterator localIterator = this.as.iterator();
    while (localIterator.hasNext())
    {
      History localHistory = (History)localIterator.next();
      paramApplicationHistories.add(localHistory);
    }
    return 0;
  }
  
  public int signOn(BankEmployee paramBankEmployee)
  {
    int i = 0;
    if (paramBankEmployee != null) {
      this.at = paramBankEmployee;
    } else {
      i = 1000;
    }
    return i;
  }
  
  public int initialize(String paramString)
  {
    int i = super.initialize(paramString);
    System.out.println("Democom.ffusion.beans.applications.Applications: SetInitURL: " + paramString);
    if (i == 0) {
      i = jdMethod_new();
    }
    return i;
  }
  
  public void setInitURL(String paramString)
  {
    initialize(paramString);
  }
  
  private int jdMethod_new()
  {
    int i = 0;
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), getXMLReader(this, this.m_URL));
    }
    catch (Exception localException)
    {
      System.out.println("**********Democom.ffusion.beans.applications.Applications Service can not find the file in the class path: " + this.m_URL);
      i = 7012;
    }
    catch (Throwable localThrowable)
    {
      System.out.println("**********Democom.ffusion.beans.applications.Applications Service can not find the file in the class path: " + this.m_URL);
      i = 7012;
    }
    return i;
  }
  
  protected class a
    extends XMLHandler
  {
    protected a() {}
    
    public void startElement(String paramString)
    {
      System.out.println("Parsing Democom.ffusion.beans.applications.Applications xml file.  Tag Name: " + paramString);
      if (paramString.equals("PRODUCT_GROUP")) {
        DemoApplications.this.aw.continueXMLParsing(getHandler());
      }
      if (paramString.equals("STATUS_GROUP")) {
        DemoApplications.this.an.continueXMLParsing(getHandler());
      }
      if (paramString.equals("BANKS")) {
        DemoApplications.this.ao.continueXMLParsing(getHandler());
      }
      if (paramString.equals("APPLICATION_GROUP")) {
        DemoApplications.this.ap.continueXMLParsing(getHandler());
      }
      if (paramString.equals("FORM"))
      {
        Form localForm = new Form();
        localForm.continueXMLParsing(getHandler());
        DemoApplications.this.ar.add(localForm);
      }
      if (paramString.equals("CATEGORY_GROUP")) {
        DemoApplications.this.ay.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.DemoApplications
 * JD-Core Version:    0.7.0.1
 */