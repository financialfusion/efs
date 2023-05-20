package com.ffusion.beans.applications;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.util.UserUtil;
import com.ffusion.util.Filterable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.io.Serializable;
import java.text.Collator;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class Application
  extends ExtendABean
  implements Serializable, Filterable
{
  protected String appID;
  protected String bankID = "";
  protected String productID = "";
  protected String categoryID = "";
  protected String customerID;
  protected String trackingNumber = "";
  protected String firstName = "";
  protected String lastName = "";
  protected String emailAddress = "";
  protected String ssn = "";
  protected String owner = "";
  protected DateTime createDate;
  protected Form form = new Form();
  protected String statusID;
  protected HashMap fieldValues = new HashMap();
  protected String currentFieldName;
  protected String radioValue;
  protected String errorString;
  protected String trackingID;
  protected int affiliateBankID = -1;
  protected String userName = "";
  protected String searchLanguage = "en_US";
  
  protected Application() {}
  
  public Application(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public String getTrackingID()
  {
    return this.trackingID;
  }
  
  public void setTrackingID(String paramString)
  {
    this.trackingID = paramString;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.createDate != null) {
      this.createDate.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.createDate != null) {
      this.createDate.setFormat(paramString);
    }
  }
  
  public String getAppID()
  {
    return this.appID;
  }
  
  public void setAppID(String paramString)
  {
    this.appID = paramString;
  }
  
  public String getBankID()
  {
    return this.bankID;
  }
  
  public void setBankID(String paramString)
  {
    this.bankID = paramString;
  }
  
  public String getCategoryID()
  {
    return this.categoryID;
  }
  
  public void setCategoryID(String paramString)
  {
    this.categoryID = paramString;
  }
  
  public String getProductID()
  {
    return this.productID;
  }
  
  public void setProductID(String paramString)
  {
    this.productID = paramString;
  }
  
  public String getCustomerID()
  {
    return this.customerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.customerID = paramString;
  }
  
  public String getTrackingNumber()
  {
    return this.trackingNumber;
  }
  
  public void setTrackingNumber(String paramString)
  {
    this.trackingNumber = paramString;
  }
  
  public String getFirstName()
  {
    return this.firstName;
  }
  
  public void setFirstName(String paramString)
  {
    this.firstName = paramString;
  }
  
  public String getLastName()
  {
    return this.lastName;
  }
  
  public void setLastName(String paramString)
  {
    this.lastName = paramString;
  }
  
  public String getEmailAddress()
  {
    return this.emailAddress;
  }
  
  public void setEmailAddress(String paramString)
  {
    this.emailAddress = paramString;
  }
  
  public String getSsn()
  {
    return this.ssn;
  }
  
  public void setSsn(String paramString)
  {
    this.ssn = paramString;
  }
  
  public String getOwner()
  {
    return this.owner;
  }
  
  public void setOwner(String paramString)
  {
    this.owner = paramString;
  }
  
  public DateTime getCreateDate()
  {
    return this.createDate;
  }
  
  public String getCreateDateString()
  {
    if (this.createDate != null) {
      return this.createDate.toString();
    }
    return null;
  }
  
  public void setCreateDate(DateTime paramDateTime)
  {
    this.createDate = paramDateTime;
  }
  
  public void setCreateDate(Calendar paramCalendar)
  {
    this.createDate = new DateTime(paramCalendar, this.locale);
  }
  
  public void setCreateDateString(String paramString)
  {
    try
    {
      if (this.createDate == null) {
        this.createDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.createDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public Form getForm()
  {
    return this.form;
  }
  
  public void setForm(Form paramForm)
  {
    this.form = paramForm;
  }
  
  public String getStatusID()
  {
    return this.statusID;
  }
  
  public void setStatusID(String paramString)
  {
    this.statusID = paramString;
  }
  
  public String getCurrentFieldValue()
  {
    if (this.currentFieldName != null) {
      return (String)this.fieldValues.get(this.currentFieldName);
    }
    return "";
  }
  
  public String getCurrentFieldName()
  {
    return this.currentFieldName;
  }
  
  public void setCurrentFieldName(String paramString)
  {
    this.currentFieldName = paramString;
  }
  
  public void setFieldValue(String paramString)
  {
    this.fieldValues.put(this.currentFieldName, paramString);
  }
  
  public void setRadioValue(String paramString)
  {
    this.radioValue = paramString;
  }
  
  public String getRadioValue()
  {
    String str = (String)this.fieldValues.get(this.currentFieldName);
    if ((str != null) && (str.equalsIgnoreCase(this.radioValue))) {
      return "checked";
    }
    return "";
  }
  
  public void setErrorString(String paramString)
  {
    this.errorString = paramString;
  }
  
  public String getErrorString()
  {
    return this.errorString;
  }
  
  public HashMap getFieldValues()
  {
    return this.fieldValues;
  }
  
  public void setFieldValues(HashMap paramHashMap)
  {
    this.fieldValues = paramHashMap;
  }
  
  public void setAffiliateBankID(int paramInt)
  {
    this.affiliateBankID = paramInt;
  }
  
  public void setAffiliateBankID(String paramString)
  {
    this.affiliateBankID = Integer.parseInt(paramString, 10);
  }
  
  public int getAffiliateBankID()
  {
    return this.affiliateBankID;
  }
  
  public String getAffiliateBankIDStr()
  {
    return String.valueOf(this.affiliateBankID);
  }
  
  public void setSearchLanguage(String paramString)
  {
    this.searchLanguage = paramString;
  }
  
  public String getSearchLanguage()
  {
    return this.searchLanguage;
  }
  
  public void set(Application paramApplication)
  {
    setAppID(paramApplication.getAppID());
    setBankID(paramApplication.getBankID());
    setCategoryID(paramApplication.getCategoryID());
    setProductID(paramApplication.getProductID());
    setCustomerID(paramApplication.getCustomerID());
    setStatusID(paramApplication.getStatusID());
    setTrackingNumber(paramApplication.getTrackingNumber());
    setFirstName(paramApplication.getFirstName());
    setLastName(paramApplication.getLastName());
    setEmailAddress(paramApplication.getEmailAddress());
    setSsn(paramApplication.getSsn());
    setOwner(paramApplication.getOwner());
    if (paramApplication.getCreateDate() != null) {
      setCreateDate((DateTime)paramApplication.getCreateDate().clone());
    }
    if (paramApplication.getForm() != null)
    {
      Form localForm = new Form();
      localForm.set(paramApplication.getForm());
      setForm(localForm);
    }
    if (paramApplication.getFieldValues() != null) {
      setFieldValues((HashMap)paramApplication.getFieldValues().clone());
    }
    setCurrentFieldName(paramApplication.getCurrentFieldName());
    setRadioValue(paramApplication.getRadioValue());
    setErrorString(paramApplication.getErrorString());
    super.set(paramApplication);
  }
  
  public void setMember(String paramString1, String paramString2)
  {
    this.fieldValues.put(paramString1, paramString2);
    this.fieldValues.put(paramString1.toLowerCase(), paramString2);
    if (paramString1.equalsIgnoreCase("APP_ID")) {
      this.appID = paramString2;
    } else if (paramString1.equalsIgnoreCase("CATEGORY_ID")) {
      this.categoryID = paramString2;
    } else if (paramString1.equalsIgnoreCase("PRODUCT_ID")) {
      this.productID = paramString2;
    } else if (paramString1.equalsIgnoreCase("STATUS_ID")) {
      this.statusID = paramString2;
    } else if (paramString1.equalsIgnoreCase("CUSTOMER_ID")) {
      this.customerID = paramString2;
    } else if (paramString1.equalsIgnoreCase("BANK_ID")) {
      this.bankID = paramString2;
    } else if (paramString1.equalsIgnoreCase("CREATE_DATE")) {
      setCreateDateString(paramString2);
    } else if (paramString1.equalsIgnoreCase("EMAIL_ADDRESS")) {
      this.emailAddress = paramString2;
    } else if (paramString1.equalsIgnoreCase("SSN")) {
      this.ssn = paramString2;
    } else if (paramString1.equalsIgnoreCase("FIRST_NAME")) {
      this.firstName = paramString2;
    } else if (paramString1.equalsIgnoreCase("LAST_NAME")) {
      this.lastName = paramString2;
    } else if (paramString1.equalsIgnoreCase("TRACKING_NUMBER")) {
      this.trackingNumber = paramString2;
    } else if (paramString1.equalsIgnoreCase("TRACKING_NO")) {
      this.trackingNumber = paramString2;
    } else if (paramString1.equalsIgnoreCase("OWNER")) {
      this.owner = paramString2;
    } else if (paramString1.equalsIgnoreCase("EMPLOYEE_ID")) {
      this.owner = paramString2;
    } else if (paramString1.equalsIgnoreCase("FORM_ID")) {
      this.form.setID(paramString2);
    } else {
      super.set(paramString1, paramString2);
    }
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "APPLICATION");
    XMLHandler.appendTag(localStringBuffer, "APP_ID", this.appID);
    XMLHandler.appendTag(localStringBuffer, "CATEGORY_ID", this.categoryID);
    XMLHandler.appendTag(localStringBuffer, "PRODUCT_ID", "PRODUCT_ID");
    XMLHandler.appendTag(localStringBuffer, "CUSTOMER_ID", this.customerID);
    XMLHandler.appendTag(localStringBuffer, "STATUS_ID", this.statusID);
    XMLHandler.appendTag(localStringBuffer, "TRACKING_NUMBER", this.trackingNumber);
    XMLHandler.appendTag(localStringBuffer, "FORM_ID", this.form.getID());
    XMLHandler.appendTag(localStringBuffer, "FIRST_NAME", this.firstName);
    XMLHandler.appendTag(localStringBuffer, "LAST_NAME", this.lastName);
    XMLHandler.appendTag(localStringBuffer, "SSN", this.ssn);
    if (this.createDate != null) {
      XMLHandler.appendTag(localStringBuffer, "CREATE_DATE", this.createDate.toString());
    }
    XMLHandler.appendTag(localStringBuffer, "EMAIL_ADDRESS", this.emailAddress);
    XMLHandler.appendTag(localStringBuffer, "USER_NAME", this.userName);
    localStringBuffer.append(this.form.getXML());
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "APPLICATION");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
      this.form.setXML(paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Application localApplication = (Application)paramObject;
    int i = 0;
    if (paramObject == this) {
      return 0;
    }
    if ((paramString.equals("APP_ID")) && (localApplication.getAppID() != null) && (getAppID() != null))
    {
      i = getAppID().compareTo(localApplication.getAppID());
    }
    else if ((paramString.equals("BANK_ID")) && (localApplication.getBankID() != null) && (getBankID() != null))
    {
      i = getBankID().compareTo(localApplication.getBankID());
    }
    else if ((paramString.equals("CATEGORY_ID")) && (localApplication.getCategoryID() != null) && (getCategoryID() != null))
    {
      i = getCategoryID().compareTo(localApplication.getCategoryID());
    }
    else if ((paramString.equals("CREATE_DATE")) && (localApplication.getCreateDate() != null) && (getCreateDate() != null))
    {
      i = getCreateDate().compare(localApplication.getCreateDate());
    }
    else if ((paramString.equals("CUSTOMER_ID")) && (localApplication.getCustomerID() != null) && (getCustomerID() != null))
    {
      i = getCustomerID().compareTo(localApplication.getCustomerID());
    }
    else
    {
      Collator localCollator;
      if ((paramString.equals("EMAIL_ADDRESS")) && (localApplication.getEmailAddress() != null) && (getEmailAddress() != null))
      {
        localCollator = doGetCollator();
        i = localCollator.compare(getEmailAddress(), localApplication.getEmailAddress());
      }
      else if ((paramString.equals("FIRST_NAME")) && (localApplication.getFirstName() != null) && (getFirstName() != null))
      {
        localCollator = doGetCollator();
        i = localCollator.compare(getFirstName(), localApplication.getFirstName());
      }
      else if ((paramString.equals("LAST_NAME")) && (localApplication.getLastName() != null) && (getLastName() != null))
      {
        localCollator = doGetCollator();
        i = localCollator.compare(getLastName(), localApplication.getLastName());
      }
      else if ((paramString.equals("PRODUCT_ID")) && (localApplication.getProductID() != null) && (getProductID() != null))
      {
        i = getProductID().compareTo(localApplication.getProductID());
      }
      else if ((paramString.equals("SSN")) && (localApplication.getSsn() != null) && (getSsn() != null))
      {
        i = getSsn().compareTo(localApplication.getSsn());
      }
      else if ((paramString.equals("STATUS_ID")) && (localApplication.getStatusID() != null) && (getStatusID() != null))
      {
        i = getStatusID().compareTo(localApplication.getStatusID());
      }
      else if ((paramString.equals("TRACKING_NUMBER")) && (localApplication.getTrackingNumber() != null) && (getTrackingNumber() != null))
      {
        i = getTrackingNumber().compareTo(localApplication.getTrackingNumber());
      }
      else if ((paramString.equals("OWNER")) && (localApplication.getOwner() != null) && (getOwner() != null))
      {
        localCollator = doGetCollator();
        i = localCollator.compare(getOwner(), localApplication.getOwner());
      }
      else
      {
        i = super.compare(paramObject, paramString);
      }
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if (paramString1.equalsIgnoreCase("PRODUCT_ID")) {
      return isFilterable(getProductID(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public String getFullName()
  {
    return UserUtil.getFullName(getFirstName(), getLastName(), this.locale);
  }
  
  public String getSortableFullName()
  {
    return UserUtil.getSortableFullName(getFirstName(), getLastName(), this.locale);
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String paramString)
  {
    this.userName = paramString;
  }
  
  class a
    extends ExtendABean.InternalXMLHandler
  {
    a()
    {
      super();
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("FORM")) {
        Application.this.form.continueXMLParsing(getHandler());
      } else {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.applications.Application
 * JD-Core Version:    0.7.0.1
 */