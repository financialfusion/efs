package com.ffusion.beans;

import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import com.ffusion.util.beans.LocalizableString;
import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;

public class Contact
  extends ExtendABean
  implements Comparable, Serializable
{
  public static final String USER_RESOURCE_BUNDLE = "com.ffusion.beans.user.resources";
  public static final String LANG_RESOURCE_BUNDLE = "com.ffusion.util.languages";
  public static final String KEY_PREFERRED_CONTACT_PREFIX = "PREFERRED_CONTACT.";
  public static final String PREFER_VALUE_EMAIL = "email";
  public static final String PREFER_VALUE_TELEPHONE = "telephone";
  protected static final String BEAN_NAME = Contact.class.getName();
  protected String street;
  protected String street2;
  protected String street3;
  protected String city;
  protected String state;
  protected String stateCode;
  protected String country;
  protected Email email;
  protected Phone phone;
  protected Phone phone2;
  protected ZipCode zipCode;
  protected Phone dataPhone;
  protected Phone faxPhone;
  protected String preferredContactMethod;
  protected String preferredLanguage;
  
  public Contact() {}
  
  public Contact(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setStreet(String paramString)
  {
    if (paramString != null) {
      paramString = paramString.trim();
    }
    this.street = paramString;
  }
  
  public String getStreet()
  {
    return this.street;
  }
  
  public void setStreet2(String paramString)
  {
    if (paramString != null) {
      paramString = paramString.trim();
    }
    this.street2 = paramString;
  }
  
  public String getStreet2()
  {
    return this.street2;
  }
  
  public void setStreet3(String paramString)
  {
    if (paramString != null) {
      paramString = paramString.trim();
    }
    this.street3 = paramString;
  }
  
  public String getStreet3()
  {
    return this.street3;
  }
  
  public void setCity(String paramString)
  {
    if (paramString != null) {
      paramString = paramString.trim();
    }
    this.city = paramString;
  }
  
  public String getCity()
  {
    return this.city;
  }
  
  public void setState(String paramString)
  {
    if (paramString != null) {
      paramString = paramString.trim();
    }
    this.state = paramString;
  }
  
  public void setStateCode(String paramString)
  {
    if (paramString != null) {
      paramString = paramString.trim().toUpperCase();
    }
    this.stateCode = paramString;
  }
  
  public String getState()
  {
    return this.state;
  }
  
  public String getStateCode()
  {
    return this.stateCode;
  }
  
  public void setCountry(String paramString)
  {
    if (paramString != null) {
      paramString = paramString.trim();
    }
    this.country = paramString;
  }
  
  public String getCountry()
  {
    return this.country;
  }
  
  public void setZipCode(String paramString)
  {
    if (paramString == null) {
      this.zipCode = null;
    } else if (this.zipCode == null) {
      this.zipCode = new ZipCode(paramString.trim(), true);
    } else {
      this.zipCode.fromString(paramString.trim());
    }
  }
  
  public void setZipCode(ZipCode paramZipCode)
  {
    this.zipCode = paramZipCode;
  }
  
  public void setZipCodeFormat(String paramString)
  {
    if ((this.zipCode != null) && (paramString != null)) {
      this.zipCode.setFormat(paramString);
    }
  }
  
  public void setZipCodeAllNumbers(String paramString)
  {
    if ((this.zipCode != null) && (paramString != null)) {
      this.zipCode.setAllNumbers(paramString);
    }
  }
  
  public boolean isLastSetZipCodeValid()
  {
    return this.zipCode.isValid();
  }
  
  public String getZipCode()
  {
    if (this.zipCode != null) {
      return this.zipCode.toString();
    }
    return null;
  }
  
  public ZipCode getZipCodeValue()
  {
    return this.zipCode;
  }
  
  public void setPhone(String paramString)
  {
    if (paramString == null) {
      this.phone = null;
    } else if (this.phone == null) {
      this.phone = new Phone(paramString.trim(), true);
    } else {
      this.phone.fromString(paramString.trim());
    }
  }
  
  public void setPhone(Phone paramPhone)
  {
    this.phone = paramPhone;
  }
  
  public void setPhoneAllNumbers(String paramString)
  {
    if ((this.phone != null) && (paramString != null)) {
      this.phone.setAllNumbers(paramString);
    }
  }
  
  public void setPhoneNumberFormat(String paramString)
  {
    if ((this.phone != null) && (paramString != null)) {
      this.phone.setFormat(paramString);
    }
  }
  
  public boolean isLastSetPhoneValid()
  {
    return this.phone.isValid();
  }
  
  public String getPhone()
  {
    if (this.phone != null) {
      return this.phone.toString();
    }
    return null;
  }
  
  public Phone getPhoneValue()
  {
    return this.phone;
  }
  
  public void setPhone2(String paramString)
  {
    if (paramString == null) {
      this.phone2 = null;
    } else if (this.phone2 == null) {
      this.phone2 = new Phone(paramString.trim(), true);
    } else {
      this.phone2.fromString(paramString.trim());
    }
  }
  
  public void setPhone2(Phone paramPhone)
  {
    this.phone2 = paramPhone;
  }
  
  public void setPhoneNumber2Format(String paramString)
  {
    if ((this.phone2 != null) && (paramString != null)) {
      this.phone2.setFormat(paramString);
    }
  }
  
  public void setPhone2AllNumbers(String paramString)
  {
    if ((this.phone != null) && (paramString != null)) {
      this.phone.setAllNumbers(paramString);
    }
  }
  
  public boolean isLastSetPhone2Valid()
  {
    return this.phone2.isValid();
  }
  
  public String getPhone2()
  {
    if (this.phone2 != null) {
      return this.phone2.toString();
    }
    return null;
  }
  
  public Phone getPhone2Value()
  {
    return this.phone2;
  }
  
  public void setEmail(String paramString)
  {
    if (paramString == null) {
      this.email = null;
    } else if (this.email == null) {
      this.email = new Email(paramString.trim());
    } else {
      this.email.fromString(paramString.trim());
    }
  }
  
  public void setEmail(Email paramEmail)
  {
    this.email = paramEmail;
  }
  
  public boolean isLastSetEmailValid()
  {
    return this.email.isValid();
  }
  
  public String getEmail()
  {
    if (this.email != null) {
      return this.email.toString();
    }
    return null;
  }
  
  public Email getEmailValue()
  {
    return this.email;
  }
  
  public void setDataPhone(String paramString)
  {
    if (paramString == null) {
      this.dataPhone = null;
    } else if (this.dataPhone == null) {
      this.dataPhone = new Phone(paramString.trim(), true);
    } else {
      this.dataPhone.fromString(paramString.trim());
    }
  }
  
  public void setDataPhone(Phone paramPhone)
  {
    this.dataPhone = paramPhone;
  }
  
  public void setDataPhoneAllNumbers(String paramString)
  {
    if ((this.dataPhone != null) && (paramString != null)) {
      this.dataPhone.setAllNumbers(paramString);
    }
  }
  
  public void setDataPhoneNumberFormat(String paramString)
  {
    if ((this.dataPhone != null) && (paramString != null)) {
      this.dataPhone.setFormat(paramString);
    }
  }
  
  public boolean isLastSetDataPhoneValid()
  {
    return this.dataPhone.isValid();
  }
  
  public String getDataPhone()
  {
    if (this.dataPhone != null) {
      return this.dataPhone.toString();
    }
    return null;
  }
  
  public Phone getDataPhoneValue()
  {
    return this.dataPhone;
  }
  
  public void setFaxPhone(String paramString)
  {
    if (paramString == null) {
      this.faxPhone = null;
    } else if (this.faxPhone == null) {
      this.faxPhone = new Phone(paramString.trim(), true);
    } else {
      this.faxPhone.fromString(paramString.trim());
    }
  }
  
  public void setFaxPhone(Phone paramPhone)
  {
    this.faxPhone = paramPhone;
  }
  
  public void setFaxPhoneAllNumbers(String paramString)
  {
    if ((this.faxPhone != null) && (paramString != null)) {
      this.faxPhone.setAllNumbers(paramString);
    }
  }
  
  public void setFaxPhoneNumberFormat(String paramString)
  {
    if ((this.faxPhone != null) && (paramString != null)) {
      this.faxPhone.setFormat(paramString);
    }
  }
  
  public boolean isLastSetFaxPhoneValid()
  {
    return this.faxPhone.isValid();
  }
  
  public String getFaxPhone()
  {
    if (this.faxPhone != null) {
      return this.faxPhone.toString();
    }
    return null;
  }
  
  public Phone getFaxPhoneValue()
  {
    return this.faxPhone;
  }
  
  public void setPreferredContactMethod(String paramString)
  {
    if (paramString != null) {
      paramString = paramString.trim();
    }
    this.preferredContactMethod = paramString;
  }
  
  public String getPreferredContactMethod()
  {
    return this.preferredContactMethod;
  }
  
  public void setPreferredLanguage(String paramString)
  {
    this.preferredLanguage = paramString;
  }
  
  public String getPreferredLanguage()
  {
    return this.preferredLanguage;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("STREET")) {
      setStreet(paramString2);
    } else if (paramString1.equals("STREET2")) {
      setStreet2(paramString2);
    } else if (paramString1.equals("STREET3")) {
      setStreet3(paramString2);
    } else if (paramString1.equals("CITY")) {
      setCity(paramString2);
    } else if (paramString1.equals("STATE")) {
      setState(paramString2);
    } else if (paramString1.equals("COUNTRY")) {
      setCountry(paramString2);
    } else if (paramString1.equals("ZIPCODE")) {
      setZipCode(paramString2);
    } else if (paramString1.equals("PHONE")) {
      setPhone(paramString2);
    } else if (paramString1.equals("PHONE2")) {
      setPhone2(paramString2);
    } else if (paramString1.equals("EMAIL")) {
      setEmail(paramString2);
    } else if (paramString1.equals("DATAPHONE")) {
      setDataPhone(paramString2);
    } else if (paramString1.equals("FAXPHONE")) {
      setFaxPhone(paramString2);
    } else if (paramString1.equals("PREFERRED_CONTACT_METHOD")) {
      setPreferredContactMethod(paramString2);
    } else if (paramString1.equals("PREFERRED_LANG")) {
      setPreferredLanguage(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public void set(Contact paramContact)
  {
    setStreet(paramContact.getStreet());
    setStreet2(paramContact.getStreet2());
    setStreet3(paramContact.getStreet3());
    setCity(paramContact.getCity());
    setState(paramContact.getState());
    setStateCode(paramContact.getStateCode());
    setPhone(paramContact.getPhone());
    setPhone2(paramContact.getPhone2());
    setCountry(paramContact.getCountry());
    setEmail(paramContact.getEmail());
    setZipCode(paramContact.getZipCode());
    setDataPhone(paramContact.getDataPhone());
    setFaxPhone(paramContact.getFaxPhone());
    setPreferredContactMethod(paramContact.getPreferredContactMethod());
    setPreferredLanguage(paramContact.getPreferredLanguage());
    super.set(paramContact);
  }
  
  public Object clone()
  {
    try
    {
      Contact localContact = (Contact)super.clone();
      if (this.email != null) {
        localContact.setEmail((Email)this.email.clone());
      }
      if (this.phone != null) {
        localContact.setPhone((Phone)this.phone.clone());
      }
      if (this.phone2 != null) {
        localContact.setPhone2((Phone)this.phone2.clone());
      }
      if (this.zipCode != null) {
        localContact.setZipCode((ZipCode)this.zipCode.clone());
      }
      if (this.dataPhone != null) {
        localContact.setDataPhone((Phone)this.dataPhone.clone());
      }
      if (this.faxPhone != null) {
        localContact.setFaxPhone((Phone)this.faxPhone.clone());
      }
      return localContact;
    }
    catch (Exception localException) {}
    return super.clone();
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    Collator localCollator = doGetCollator();
    Contact localContact = (Contact)paramObject;
    int i;
    if (this == localContact) {
      i = 0;
    } else {
      i = localCollator.compare(getCity(), localContact.getCity());
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Collator localCollator = doGetCollator();
    Contact localContact = (Contact)paramObject;
    int i = 1;
    if ((paramString.equals("CITY")) && (getCity() != null) && (localContact.getCity() != null)) {
      i = localCollator.compare(getCity(), localContact.getCity());
    } else if ((paramString.equals("STREET")) && (getStreet() != null) && (localContact.getStreet() != null)) {
      i = localCollator.compare(getStreet(), localContact.getStreet());
    } else if ((paramString.equals("STATE")) && (getState() != null) && (localContact.getState() != null)) {
      i = localCollator.compare(getState(), localContact.getState());
    } else if ((paramString.equals("COUNTRY")) && (getCountry() != null) && (localContact.getCountry() != null)) {
      i = localCollator.compare(getCountry(), localContact.getCountry());
    } else if ((paramString.equals("ZIPCODE")) && (getZipCode() != null) && (localContact.getZipCode() != null)) {
      i = localCollator.compare(getZipCode(), localContact.getZipCode());
    } else if ((paramString.equals("EMAIL")) && (getEmail() != null) && (localContact.getEmail() != null)) {
      i = localCollator.compare(getEmail(), localContact.getEmail());
    } else if ((paramString.equals("PHONE")) && (getPhone() != null) && (localContact.getPhone() != null)) {
      i = localCollator.compare(getPhone(), localContact.getPhone());
    } else if ((paramString.equals("DATAPHONE")) && (getDataPhone() != null) && (localContact.getDataPhone() != null)) {
      i = localCollator.compare(getDataPhone(), localContact.getDataPhone());
    } else if ((paramString.equals("FAXPHONE")) && (getFaxPhone() != null) && (localContact.getFaxPhone() != null)) {
      i = localCollator.compare(getFaxPhone(), localContact.getFaxPhone());
    } else if ((paramString.equals("PREFERRED_CONTACT_METHOD")) && (getPreferredContactMethod() != null) && (localContact.getPreferredContactMethod() != null)) {
      i = localCollator.compare(getPreferredContactMethod(), localContact.getPreferredContactMethod());
    } else if ((paramString.equals("PREFERRED_LANG")) && (getPreferredLanguage() != null) && (localContact.getPreferredLanguage() != null)) {
      i = localCollator.compare(getPreferredLanguage(), localContact.getPreferredLanguage());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    String str = paramString1.toUpperCase();
    if ((str.equals("CITY")) && (getCity() != null)) {
      return isFilterable(getCity(), paramString2, paramString3);
    }
    if ((str.equals("STATE")) && (getState() != null)) {
      return isFilterable(getState(), paramString2, paramString3);
    }
    if ((str.equals("COUNTRY")) && (getCountry() != null)) {
      return isFilterable(getCountry(), paramString2, paramString3);
    }
    if ((str.equals("ZIPCODE")) && (getZipCode() != null)) {
      return isFilterable(getZipCode(), paramString2, paramString3);
    }
    if ((str.equals("EMAIL")) && (getEmail() != null)) {
      return isFilterable(getEmail(), paramString2, paramString3);
    }
    if ((str.equals("DATAPHONE")) && (getDataPhone() != null)) {
      return isFilterable(getDataPhone(), paramString2, paramString3);
    }
    if ((str.equals("FAXPHONE")) && (getFaxPhone() != null)) {
      return isFilterable(getFaxPhone(), paramString2, paramString3);
    }
    if ((str.equals("PREFERRED_CONTACT_METHOD")) && (getPreferredContactMethod() != null)) {
      return isFilterable(getPreferredContactMethod(), paramString2, paramString3);
    }
    if ((str.equals("PREFERRED_LANG")) && (getPreferredLanguage() != null)) {
      return isFilterable(getPreferredLanguage(), paramString2, paramString3);
    }
    if ((str.equals("STREET")) && (getStreet() != null)) {
      return isFilterable(getStreet(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "CONTACT");
    XMLHandler.appendBeginTag(localStringBuffer, "ADDRESS");
    XMLHandler.appendTag(localStringBuffer, "STREET", this.street);
    XMLHandler.appendTag(localStringBuffer, "STREET2", this.street2);
    XMLHandler.appendTag(localStringBuffer, "STREET3", this.street3);
    XMLHandler.appendTag(localStringBuffer, "CITY", this.city);
    XMLHandler.appendTag(localStringBuffer, "STATE", this.state);
    XMLHandler.appendTag(localStringBuffer, "COUNTRY", this.country);
    if (this.stateCode != null) {
      XMLHandler.appendTag(localStringBuffer, "STATECODE", this.stateCode.toString());
    }
    if (this.zipCode != null) {
      XMLHandler.appendTag(localStringBuffer, "ZIPCODE", this.zipCode.toString());
    }
    XMLHandler.appendEndTag(localStringBuffer, "ADDRESS");
    if (this.phone != null) {
      XMLHandler.appendTag(localStringBuffer, "PHONE", this.phone.toString());
    }
    if (this.phone2 != null) {
      XMLHandler.appendTag(localStringBuffer, "PHONE2", this.phone2.toString());
    }
    if (this.email != null) {
      XMLHandler.appendTag(localStringBuffer, "EMAIL", this.email.toString());
    }
    if (this.dataPhone != null) {
      XMLHandler.appendTag(localStringBuffer, "DATAPHONE", this.dataPhone.toString());
    }
    if (this.faxPhone != null) {
      XMLHandler.appendTag(localStringBuffer, "FAXPHONE", this.faxPhone.toString());
    }
    if (this.preferredContactMethod != null) {
      XMLHandler.appendTag(localStringBuffer, "PREFERRED_CONTACT_METHOD", this.preferredContactMethod.toString());
    }
    if (this.preferredLanguage != null) {
      XMLHandler.appendTag(localStringBuffer, "PREFERRED_LANG", this.preferredLanguage.toString());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "CONTACT");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, Contact paramContact, String paramString)
  {
    logChanges(paramHistoryTracker, BEAN_NAME, paramContact, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, String paramString1, Contact paramContact, String paramString2)
  {
    if (!BEAN_NAME.equals(paramString1)) {
      paramString1 = paramString1 + "," + BEAN_NAME;
    }
    paramHistoryTracker.detectChange(paramString1, "STREET", paramContact.street, this.street, paramString2);
    paramHistoryTracker.detectChange(paramString1, "STREET2", paramContact.street2, this.street2, paramString2);
    paramHistoryTracker.detectChange(paramString1, "STREET3", paramContact.street3, this.street3, paramString2);
    paramHistoryTracker.detectChange(paramString1, "CITY", paramContact.city, this.city, paramString2);
    paramHistoryTracker.detectChange(paramString1, "STATE", paramContact.state, this.state, paramString2);
    paramHistoryTracker.detectChange(paramString1, "COUNTRY", paramContact.country, this.country, paramString2);
    paramHistoryTracker.detectChange(paramString1, "STATECODE", paramContact.stateCode, this.stateCode, paramString2);
    paramHistoryTracker.detectChange(paramString1, "ZIPCODE", paramContact.zipCode, this.zipCode, paramString2);
    paramHistoryTracker.detectChange(paramString1, "PHONE", paramContact.phone, this.phone, paramString2);
    paramHistoryTracker.detectChange(paramString1, "PHONE2", paramContact.phone2, this.phone2, paramString2);
    paramHistoryTracker.detectChange(paramString1, "EMAIL", paramContact.email, this.email, paramString2);
    paramHistoryTracker.detectChange(paramString1, "DATAPHONE", paramContact.dataPhone, this.dataPhone, paramString2);
    paramHistoryTracker.detectChange(paramString1, "FAXPHONE", paramContact.faxPhone, this.faxPhone, paramString2);
    paramHistoryTracker.detectChange(paramString1, "PREFERRED_CONTACT_METHOD", paramContact.preferredContactMethod, this.preferredContactMethod, paramString2);
    paramHistoryTracker.detectChange(paramString1, "PREFERRED_LANG", paramContact.preferredLanguage, this.preferredLanguage, paramString2);
    super.logChanges(paramHistoryTracker, paramContact, paramString2);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "STREET", this.street, paramString);
    paramHistoryTracker.logCreate(BEAN_NAME, "STREET2", this.street2, paramString);
    paramHistoryTracker.logCreate(BEAN_NAME, "STREET3", this.street3, paramString);
    paramHistoryTracker.logCreate(BEAN_NAME, "CITY", this.city, paramString);
    paramHistoryTracker.logCreate(BEAN_NAME, "STATE", this.state, paramString);
    paramHistoryTracker.logCreate(BEAN_NAME, "COUNTRY", this.country, paramString);
    paramHistoryTracker.logCreate(BEAN_NAME, "STATECODE", this.stateCode, paramString);
    paramHistoryTracker.logCreate(BEAN_NAME, "ZIPCODE", this.zipCode, paramString);
    paramHistoryTracker.logCreate(BEAN_NAME, "PHONE", this.phone, paramString);
    paramHistoryTracker.logCreate(BEAN_NAME, "PHONE2", this.phone2, paramString);
    paramHistoryTracker.logCreate(BEAN_NAME, "EMAIL", this.email, paramString);
    paramHistoryTracker.logCreate(BEAN_NAME, "DATAPHONE", this.dataPhone, paramString);
    paramHistoryTracker.logCreate(BEAN_NAME, "FAXPHONE", this.faxPhone, paramString);
    paramHistoryTracker.logCreate(BEAN_NAME, "PREFERRED_CONTACT_METHOD", this.preferredContactMethod, paramString);
    paramHistoryTracker.logCreate(BEAN_NAME, "PREFERRED_LANG", this.preferredLanguage, paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logDelete(BEAN_NAME, "STREET", this.street, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "STREET2", this.street2, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "STREET3", this.street3, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "CITY", this.city, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "STATE", this.state, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "COUNTRY", this.country, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "STATECODE", this.stateCode, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "ZIPCODE", this.zipCode, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "PHONE", this.phone, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "PHONE2", this.phone2, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "EMAIL", this.email, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "DATAPHONE", this.dataPhone, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "FAXPHONE", this.faxPhone, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "PREFERRED_CONTACT_METHOD", this.preferredContactMethod, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "PREFERRED_LANG", this.preferredLanguage, paramString);
    super.logDeletion(paramHistoryTracker, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, Contact paramContact, ILocalizable paramILocalizable)
  {
    logChanges(paramHistoryTracker, BEAN_NAME, paramContact, paramILocalizable);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, String paramString, Contact paramContact, ILocalizable paramILocalizable)
  {
    if (!BEAN_NAME.equals(paramString)) {
      paramString = paramString + "," + BEAN_NAME;
    }
    paramHistoryTracker.detectChange(paramString, "STREET", paramContact.street, this.street, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "STREET2", paramContact.street2, this.street2, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "STREET3", paramContact.street3, this.street3, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "CITY", paramContact.city, this.city, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "STATE", paramContact.state, this.state, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "COUNTRY", paramContact.country, this.country, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "STATECODE", paramContact.stateCode, this.stateCode, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "ZIPCODE", paramContact.zipCode == null ? null : paramContact.zipCode.toString(), this.zipCode == null ? null : this.zipCode.toString(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "PHONE", paramContact.phone == null ? null : paramContact.phone.toString(), this.phone == null ? null : this.phone.toString(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "PHONE2", paramContact.phone2 == null ? null : paramContact.phone2.toString(), this.phone2 == null ? null : this.phone2.toString(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "EMAIL", paramContact.email == null ? null : paramContact.email.toString(), this.email == null ? null : this.email.toString(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "DATAPHONE", paramContact.dataPhone == null ? null : paramContact.dataPhone.toString(), this.dataPhone == null ? null : this.dataPhone.toString(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "FAXPHONE", paramContact.faxPhone == null ? null : paramContact.faxPhone.toString(), this.faxPhone == null ? null : this.faxPhone.toString(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "PREFERRED_CONTACT_METHOD", paramContact.preferredContactMethod == null ? null : new LocalizableString("com.ffusion.beans.user.resources", "PREFERRED_CONTACT." + paramContact.preferredContactMethod, null), this.preferredContactMethod == null ? null : new LocalizableString("com.ffusion.beans.user.resources", "PREFERRED_CONTACT." + this.preferredContactMethod, null), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "PREFERRED_LANG", paramContact.preferredLanguage == null ? null : new LocalizableString("com.ffusion.util.languages", paramContact.preferredLanguage, null), this.preferredLanguage == null ? null : new LocalizableString("com.ffusion.util.languages", this.preferredLanguage, null), paramILocalizable);
    super.logChanges(paramHistoryTracker, paramContact, paramILocalizable);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "STREET", this.street, paramILocalizable);
    paramHistoryTracker.logCreate(BEAN_NAME, "STREET2", this.street2, paramILocalizable);
    paramHistoryTracker.logCreate(BEAN_NAME, "STREET3", this.street3, paramILocalizable);
    paramHistoryTracker.logCreate(BEAN_NAME, "CITY", this.city, paramILocalizable);
    paramHistoryTracker.logCreate(BEAN_NAME, "STATE", this.state, paramILocalizable);
    paramHistoryTracker.logCreate(BEAN_NAME, "COUNTRY", this.country, paramILocalizable);
    paramHistoryTracker.logCreate(BEAN_NAME, "STATECODE", this.stateCode, paramILocalizable);
    paramHistoryTracker.logCreate(BEAN_NAME, "ZIPCODE", this.zipCode == null ? null : this.zipCode.toString(), paramILocalizable);
    paramHistoryTracker.logCreate(BEAN_NAME, "PHONE", this.phone == null ? null : this.phone.toString(), paramILocalizable);
    paramHistoryTracker.logCreate(BEAN_NAME, "PHONE2", this.phone2 == null ? null : this.phone2.toString(), paramILocalizable);
    paramHistoryTracker.logCreate(BEAN_NAME, "EMAIL", this.email == null ? null : this.email.toString(), paramILocalizable);
    paramHistoryTracker.logCreate(BEAN_NAME, "DATAPHONE", this.dataPhone == null ? null : this.dataPhone.toString(), paramILocalizable);
    paramHistoryTracker.logCreate(BEAN_NAME, "FAXPHONE", this.faxPhone == null ? null : this.faxPhone.toString(), paramILocalizable);
    paramHistoryTracker.logCreate(BEAN_NAME, "PREFERRED_CONTACT_METHOD", this.preferredContactMethod == null ? null : new LocalizableString("com.ffusion.beans.user.resources", "PREFERRED_CONTACT." + this.preferredContactMethod, null), paramILocalizable);
    paramHistoryTracker.logCreate(BEAN_NAME, "PREFERRED_LANG", this.preferredLanguage == null ? null : new LocalizableString("com.ffusion.util.languages", this.preferredLanguage, null), paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logDelete(BEAN_NAME, "STREET", this.street, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "STREET2", this.street2, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "STREET3", this.street3, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "CITY", this.city, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "STATE", this.state, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "COUNTRY", this.country, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "STATECODE", this.stateCode, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "ZIPCODE", this.zipCode == null ? null : this.zipCode.toString(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "PHONE", this.phone == null ? null : this.phone.toString(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "PHONE2", this.phone2 == null ? null : this.phone2.toString(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "EMAIL", this.email == null ? null : this.email.toString(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "DATAPHONE", this.dataPhone == null ? null : this.dataPhone.toString(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "FAXPHONE", this.faxPhone == null ? null : this.faxPhone.toString(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "PREFERRED_CONTACT_METHOD", this.preferredContactMethod == null ? null : new LocalizableString("com.ffusion.beans.user.resources", "PREFERRED_CONTACT." + this.preferredContactMethod, null), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "PREFERRED_LANG", this.preferredLanguage == null ? null : new LocalizableString("com.ffusion.util.languages", this.preferredLanguage, null), paramILocalizable);
    super.logDeletion(paramHistoryTracker, paramILocalizable);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.Contact
 * JD-Core Version:    0.7.0.1
 */