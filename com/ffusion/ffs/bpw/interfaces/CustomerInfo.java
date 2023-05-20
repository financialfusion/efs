package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.bpw.enums.UserType;
import java.io.Serializable;

public class CustomerInfo
  extends BPWInfoBase
  implements Serializable
{
  public String customerID;
  public String firstName;
  public String initial;
  public String lastName;
  public String suffix;
  public String ssn;
  public String jointFirstName;
  public String jointInitial;
  public String jointLastName;
  public String jointSuffix;
  public String addressLine1;
  public String addressLine2;
  public String city;
  public String state;
  public String zipcode;
  public String country;
  public String status;
  public String countryCode1;
  public String phone1;
  public String countryCode2;
  public String phone2;
  public String jointCountryCode1;
  public String jointPhone1;
  public String jointCountryCode2;
  public String jointPhone2;
  public String securityCode;
  public String limit;
  public String sponsorID;
  public String billingPlan;
  public String remoteUserKey;
  public String acctVerification;
  public String submitDate;
  public String email;
  public String taxID;
  public String dateBirth;
  public String custType;
  public String custGroup;
  public String custCategory;
  public String fIID;
  public String extInfo;
  public String allowZeroDayProcess;
  public int ACHCreditLeadDays = 2;
  public int ACHDebitLeadDays = 1;
  public String virtualCustomer;
  private UserType p3;
  
  public CustomerInfo() {}
  
  public CustomerInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18, String paramString19, String paramString20, String paramString21, String paramString22, String paramString23, String paramString24, String paramString25, String paramString26, String paramString27, String paramString28, String paramString29, String paramString30, String paramString31, String paramString32, String paramString33, String paramString34, String paramString35, String paramString36, String paramString37, String paramString38)
  {
    this.customerID = paramString1;
    this.firstName = paramString2;
    this.initial = paramString3;
    this.lastName = paramString4;
    this.suffix = paramString5;
    this.ssn = paramString6;
    this.jointFirstName = paramString7;
    this.jointInitial = paramString8;
    this.jointLastName = paramString9;
    this.jointSuffix = paramString10;
    this.addressLine1 = paramString11;
    this.addressLine2 = paramString12;
    this.city = paramString13;
    this.state = paramString14;
    this.zipcode = paramString15;
    this.country = paramString16;
    this.status = paramString17;
    this.countryCode1 = paramString18;
    this.phone1 = paramString19;
    this.countryCode2 = paramString20;
    this.phone2 = paramString21;
    this.email = paramString22;
    this.jointCountryCode1 = paramString23;
    this.jointPhone1 = paramString24;
    this.jointCountryCode2 = paramString25;
    this.jointPhone2 = paramString26;
    this.securityCode = paramString27;
    this.limit = paramString28;
    this.sponsorID = paramString29;
    this.billingPlan = paramString30;
    this.remoteUserKey = paramString31;
    this.email = paramString22;
    this.dateBirth = paramString32;
    this.custType = paramString33;
    this.custGroup = paramString34;
    this.custCategory = paramString35;
    this.fIID = paramString36;
    this.extInfo = paramString37;
    this.allowZeroDayProcess = paramString38;
  }
  
  public String toString()
  {
    String str = System.getProperty("line.separator");
    return "customerID = " + (this.customerID == null ? "null" : new StringBuffer().append("\"").append(this.customerID).append("\"").toString()) + str + "firstName = " + (this.firstName == null ? "null" : new StringBuffer().append("\"").append(this.firstName).append("\"").toString()) + str + "initial = " + (this.initial == null ? "null" : new StringBuffer().append("\"").append(this.initial).append("\"").toString()) + str + "lastName = " + (this.lastName == null ? "null" : new StringBuffer().append("\"").append(this.lastName).append("\"").toString()) + str + "suffix = " + (this.suffix == null ? "null" : new StringBuffer().append("\"").append(this.suffix).append("\"").toString()) + str + "jointFirstName = " + (this.jointFirstName == null ? "null" : new StringBuffer().append("\"").append(this.jointFirstName).append("\"").toString()) + str + "jointInitial = " + (this.jointInitial == null ? "null" : new StringBuffer().append("\"").append(this.jointInitial).append("\"").toString()) + str + "jointLastName = " + (this.jointLastName == null ? "null" : new StringBuffer().append("\"").append(this.jointLastName).append("\"").toString()) + str + "jointSuffix = " + (this.jointSuffix == null ? "null" : new StringBuffer().append("\"").append(this.jointSuffix).append("\"").toString()) + str + "addressLine1 = " + (this.addressLine1 == null ? "null" : new StringBuffer().append("\"").append(this.addressLine1).append("\"").toString()) + str + "addressLine2 = " + (this.addressLine2 == null ? "null" : new StringBuffer().append("\"").append(this.addressLine2).append("\"").toString()) + str + "city = " + (this.city == null ? "null" : new StringBuffer().append("\"").append(this.city).append("\"").toString()) + str + "state = " + (this.state == null ? "null" : new StringBuffer().append("\"").append(this.state).append("\"").toString()) + str + "zipcode = " + (this.zipcode == null ? "null" : new StringBuffer().append("\"").append(this.zipcode).append("\"").toString()) + str + "country = " + (this.country == null ? "null" : new StringBuffer().append("\"").append(this.country).append("\"").toString()) + str + "status = " + (this.status == null ? "null" : new StringBuffer().append("\"").append(this.status).append("\"").toString()) + str + "countryCode1 = " + (this.countryCode1 == null ? "null" : new StringBuffer().append("\"").append(this.countryCode1).append("\"").toString()) + str + "phone1 = " + (this.phone1 == null ? "null" : new StringBuffer().append("\"").append(this.phone1).append("\"").toString()) + str + "countryCode2 = " + (this.countryCode2 == null ? "null" : new StringBuffer().append("\"").append(this.countryCode2).append("\"").toString()) + str + "phone2 = " + (this.phone2 == null ? "null" : new StringBuffer().append("\"").append(this.phone2).append("\"").toString()) + str + "jointCountryCode1 = " + (this.jointCountryCode1 == null ? "null" : new StringBuffer().append("\"").append(this.jointCountryCode1).append("\"").toString()) + str + "jointPhone1 = " + (this.jointPhone1 == null ? "null" : new StringBuffer().append("\"").append(this.jointPhone1).append("\"").toString()) + str + "jointCountryCode2 = " + (this.jointCountryCode2 == null ? "null" : new StringBuffer().append("\"").append(this.jointCountryCode2).append("\"").toString()) + str + "jointPhone2 = " + (this.jointPhone2 == null ? "null" : new StringBuffer().append("\"").append(this.jointPhone2).append("\"").toString()) + str + "securityCode = " + (this.securityCode == null ? "null" : new StringBuffer().append("\"").append(this.securityCode).append("\"").toString()) + str + "limit = " + this.limit + str + "sponsorID = " + (this.sponsorID == null ? "null" : new StringBuffer().append("\"").append(this.sponsorID).append("\"").toString()) + str + "billingPlan = " + (this.billingPlan == null ? "null" : new StringBuffer().append("\"").append(this.billingPlan).append("\"").toString()) + "remoteUserKey = " + (this.remoteUserKey == null ? "null" : new StringBuffer().append("\"").append(this.remoteUserKey).append("\"").toString()) + str + "birthDate = " + (this.dateBirth == null ? "null" : new StringBuffer().append("\"").append(this.dateBirth).append("\"").toString()) + str + "custType = " + (this.custType == null ? "null" : new StringBuffer().append("\"").append(this.custType).append("\"").toString()) + str + "custGroup = " + (this.custGroup == null ? "null" : new StringBuffer().append("\"").append(this.custGroup).append("\"").toString()) + str + "custCategory = " + (this.custCategory == null ? "null" : new StringBuffer().append("\"").append(this.custCategory).append("\"").toString()) + str + "fIID = " + (this.fIID == null ? "null" : new StringBuffer().append("\"").append(this.fIID).append("\"").toString()) + str + "extInfo = " + (this.extInfo == null ? "null" : new StringBuffer().append("\"").append(this.extInfo).append("\"").toString()) + str + "submitDate = " + (this.submitDate == null ? "null" : new StringBuffer().append("\"").append(this.submitDate).append("\"").toString()) + str + "ACHCreditLeadDays = " + this.ACHCreditLeadDays + str + "ACHDebitLeadDays = " + this.ACHDebitLeadDays + str + "VirtualCustomer = " + (this.virtualCustomer == null ? "null" : new StringBuffer().append("\"").append(this.virtualCustomer).append("\"").toString()) + str + "allowZeroDayProcess = " + (this.allowZeroDayProcess == null ? "null" : new StringBuffer().append("\"").append(this.allowZeroDayProcess).append("\"").toString());
  }
  
  public String getVirtualCustomer()
  {
    return this.virtualCustomer != null ? this.virtualCustomer : "N";
  }
  
  public UserType getUserType()
  {
    return this.p3;
  }
  
  public void setUserType(UserType paramUserType)
  {
    this.p3 = paramUserType;
  }
  
  public UserType getResolvedUserType()
  {
    return getUserType() == null ? UserType.getDefaultUserType() : getUserType();
  }
  
  public String getCustomerID()
  {
    return this.customerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.customerID = paramString;
  }
  
  public CustomerInfo getCustomerInfo()
  {
    return this;
  }
  
  public void setCustomerInfo(CustomerInfo paramCustomerInfo) {}
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CustomerInfo
 * JD-Core Version:    0.7.0.1
 */