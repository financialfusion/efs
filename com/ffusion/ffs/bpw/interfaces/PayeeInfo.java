package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;
import java.util.HashMap;

public class PayeeInfo
  extends BPWInfoBase
  implements Serializable
{
  public String PayeeID;
  public String ExtdPayeeID;
  public int PayeeType;
  public String PayeeName;
  public String Addr1;
  public String Addr2;
  public String Addr3;
  public String City;
  public String State;
  public String Zipcode;
  public String Country;
  public String Phone;
  public String Extension;
  public int RouteID;
  public String LinkPayeeID;
  public String Status;
  public String DisbursementType;
  public String PayeeLevelType;
  public String NickName;
  public String ContactName;
  public int DaysToPay = -1;
  public String SubmitDate;
  public String TranID;
  public String FIID;
  public PayeeRouteInfo PayeeRouteInfo = null;
  public HashMap PayeeNamesI18N;
  public static final String DEFAULT_EXTD_PAYEE_ID = "0";
  
  public PayeeInfo() {}
  
  public PayeeInfo(String paramString1, String paramString2, int paramInt1, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, int paramInt2, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18, int paramInt3)
  {
    this.PayeeID = paramString1;
    this.ExtdPayeeID = paramString2;
    this.PayeeType = paramInt1;
    this.PayeeName = paramString3;
    this.Addr1 = paramString4;
    this.Addr2 = paramString5;
    this.Addr3 = paramString6;
    this.City = paramString7;
    this.State = paramString8;
    this.Zipcode = paramString9;
    this.Country = paramString10;
    this.Phone = paramString11;
    this.RouteID = paramInt2;
    this.LinkPayeeID = paramString13;
    this.Status = paramString14;
    this.DisbursementType = paramString15;
    this.PayeeLevelType = paramString16;
    this.NickName = paramString17;
    this.ContactName = paramString18;
    this.DaysToPay = paramInt3;
  }
  
  public PayeeInfo(String paramString1, String paramString2, int paramInt1, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, int paramInt2, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18, int paramInt3, String paramString19, PayeeRouteInfo paramPayeeRouteInfo, HashMap paramHashMap)
  {
    this.PayeeID = paramString1;
    this.ExtdPayeeID = paramString2;
    this.PayeeType = paramInt1;
    this.PayeeName = paramString3;
    this.Addr1 = paramString4;
    this.Addr2 = paramString5;
    this.Addr3 = paramString6;
    this.City = paramString7;
    this.State = paramString8;
    this.Zipcode = paramString9;
    this.Country = paramString10;
    this.Phone = paramString11;
    this.RouteID = paramInt2;
    this.LinkPayeeID = paramString13;
    this.Status = paramString14;
    this.DisbursementType = paramString15;
    this.PayeeLevelType = paramString16;
    this.NickName = paramString17;
    this.ContactName = paramString18;
    this.DaysToPay = paramInt3;
    this.FIID = paramString19;
    this.PayeeRouteInfo = paramPayeeRouteInfo;
    this.PayeeNamesI18N = paramHashMap;
  }
  
  public PayeeInfo(String paramString1, String paramString2, int paramInt1, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, int paramInt2, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18, int paramInt3, String paramString19)
  {
    this.PayeeID = paramString1;
    this.ExtdPayeeID = paramString2;
    this.PayeeType = paramInt1;
    this.PayeeName = paramString3;
    this.Addr1 = paramString4;
    this.Addr2 = paramString5;
    this.Addr3 = paramString6;
    this.City = paramString7;
    this.State = paramString8;
    this.Zipcode = paramString9;
    this.Country = paramString10;
    this.Phone = paramString11;
    this.RouteID = paramInt2;
    this.LinkPayeeID = paramString13;
    this.Status = paramString14;
    this.DisbursementType = paramString15;
    this.PayeeLevelType = paramString16;
    this.NickName = paramString17;
    this.ContactName = paramString18;
    this.DaysToPay = paramInt3;
    this.TranID = paramString19;
  }
  
  public PayeeInfo(String paramString1, String paramString2, int paramInt1, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, int paramInt2, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18, int paramInt3, String paramString19, String paramString20, PayeeRouteInfo paramPayeeRouteInfo, HashMap paramHashMap)
  {
    this.PayeeID = paramString1;
    this.ExtdPayeeID = paramString2;
    this.PayeeType = paramInt1;
    this.PayeeName = paramString3;
    this.Addr1 = paramString4;
    this.Addr2 = paramString5;
    this.Addr3 = paramString6;
    this.City = paramString7;
    this.State = paramString8;
    this.Zipcode = paramString9;
    this.Country = paramString10;
    this.Phone = paramString11;
    this.RouteID = paramInt2;
    this.LinkPayeeID = paramString13;
    this.Status = paramString14;
    this.DisbursementType = paramString15;
    this.PayeeLevelType = paramString16;
    this.NickName = paramString17;
    this.ContactName = paramString18;
    this.DaysToPay = paramInt3;
    this.TranID = paramString19;
    this.FIID = paramString20;
    this.PayeeRouteInfo = paramPayeeRouteInfo;
    this.PayeeNamesI18N = paramHashMap;
  }
  
  public PayeeRouteInfo getPayeeRouteInfo()
  {
    return this.PayeeRouteInfo;
  }
  
  public void setPayeeRouteInfo(PayeeRouteInfo paramPayeeRouteInfo)
  {
    this.PayeeRouteInfo = paramPayeeRouteInfo;
  }
  
  public String toString()
  {
    String str1 = System.getProperty("line.separator");
    String str2 = null;
    switch (this.PayeeType)
    {
    case 0: 
      str2 = "GLOBAL";
      break;
    case 1: 
      str2 = "NATIONAL";
      break;
    case 2: 
      str2 = "REGIONAL";
      break;
    case 3: 
      str2 = "PERSONAL";
      break;
    }
    return "PayeeID = " + (this.PayeeID == null ? "null" : new StringBuffer().append("\"").append(this.PayeeID).append("\"").toString()) + str1 + "ExtdPayeeID = " + (this.ExtdPayeeID == null ? "null" : new StringBuffer().append("\"").append(this.ExtdPayeeID).append("\"").toString()) + str1 + "PayeeType = " + str2 + str1 + "PayeeName = " + (this.PayeeName == null ? "null" : new StringBuffer().append("\"").append(this.PayeeName).append("\"").toString()) + str1 + "Addr1 = " + (this.Addr1 == null ? "null" : new StringBuffer().append("\"").append(this.Addr1).append("\"").toString()) + str1 + "Addr2 = " + (this.Addr2 == null ? "null" : new StringBuffer().append("\"").append(this.Addr2).append("\"").toString()) + str1 + "Addr3 = " + (this.Addr3 == null ? "null" : new StringBuffer().append("\"").append(this.Addr3).append("\"").toString()) + str1 + "City = " + (this.City == null ? "null" : new StringBuffer().append("\"").append(this.City).append("\"").toString()) + str1 + "State = " + (this.State == null ? "null" : new StringBuffer().append("\"").append(this.State).append("\"").toString()) + str1 + "Zipcode = " + (this.Zipcode == null ? "null" : new StringBuffer().append("\"").append(this.Zipcode).append("\"").toString()) + str1 + "Country = " + (this.Country == null ? "null" : new StringBuffer().append("\"").append(this.Country).append("\"").toString()) + str1 + "Phone = " + (this.Phone == null ? "null" : new StringBuffer().append("\"").append(this.Phone).append("\"").toString()) + str1 + "RouteID = " + this.RouteID + str1 + "LinkPayeeID = " + (this.LinkPayeeID == null ? "null" : new StringBuffer().append("\"").append(this.LinkPayeeID).append("\"").toString()) + str1 + "Status = " + (this.Status == null ? "null" : new StringBuffer().append("\"").append(this.Status).append("\"").toString()) + str1 + "DisbursementType = " + (this.DisbursementType == null ? "null" : new StringBuffer().append("\"").append(this.DisbursementType).append("\"").toString()) + str1 + "PayeeLevelType = " + (this.PayeeLevelType == null ? "null" : new StringBuffer().append("\"").append(this.PayeeLevelType).append("\"").toString()) + str1 + "NickName = " + (this.NickName == null ? "null" : new StringBuffer().append("\"").append(this.NickName).append("\"").toString()) + str1 + "ContactName = " + (this.ContactName == null ? "null" : new StringBuffer().append("\"").append(this.ContactName).append("\"").toString()) + str1 + "DaysToPay= " + this.DaysToPay + str1 + "SubmitDate = " + (this.SubmitDate == null ? "null" : new StringBuffer().append("\"").append(this.SubmitDate).append("\"").toString()) + str1 + "TranID = " + (this.TranID == null ? "null" : new StringBuffer().append("\"").append(this.TranID).append("\"").toString()) + str1 + "FIID = " + (this.FIID == null ? "null" : new StringBuffer().append("\"").append(this.FIID).append("\"").toString());
  }
  
  public void trim()
  {
    if (this.PayeeID != null) {
      this.PayeeID = this.PayeeID.trim();
    }
    if (this.ExtdPayeeID != null) {
      this.ExtdPayeeID = this.ExtdPayeeID.trim();
    }
    if (this.PayeeName != null) {
      this.PayeeName = this.PayeeName.trim();
    }
    if (this.Addr1 != null) {
      this.Addr1 = this.Addr1.trim();
    }
    if (this.Addr2 != null) {
      this.Addr2 = this.Addr2.trim();
    }
    if (this.Addr3 != null) {
      this.Addr3 = this.Addr3.trim();
    }
    if (this.City != null) {
      this.City = this.City.trim();
    }
    if (this.State != null) {
      this.State = this.State.trim();
    }
    if (this.Zipcode != null) {
      this.Zipcode = this.Zipcode.trim();
    }
    if (this.Country != null) {
      this.Country = this.Country.trim();
    }
    if (this.Phone != null) {
      this.Phone = this.Phone.trim();
    }
    if (this.LinkPayeeID != null) {
      this.LinkPayeeID = this.LinkPayeeID.trim();
    }
    if (this.Status != null) {
      this.Status = this.Status.trim();
    }
    if (this.DisbursementType != null) {
      this.DisbursementType = this.DisbursementType.trim();
    }
    if (this.PayeeLevelType != null) {
      this.PayeeLevelType = this.PayeeLevelType.trim();
    }
    if (this.NickName != null) {
      this.NickName = this.NickName.trim();
    }
    if (this.ContactName != null) {
      this.ContactName = this.ContactName.trim();
    }
    if (this.TranID != null) {
      this.TranID = this.TranID.trim();
    }
    if (this.FIID != null) {
      this.FIID = this.FIID.trim();
    }
  }
  
  public int getRouteID()
  {
    return (this.PayeeRouteInfo != null) && (this.PayeeRouteInfo.RouteID != 0) ? this.PayeeRouteInfo.RouteID : this.RouteID;
  }
  
  public void setRouteID(int paramInt)
  {
    this.RouteID = paramInt;
    if (this.PayeeRouteInfo == null) {
      this.PayeeRouteInfo = new PayeeRouteInfo();
    }
    this.PayeeRouteInfo.RouteID = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.PayeeInfo
 * JD-Core Version:    0.7.0.1
 */