package com.ffusion.beans.wiretransfers;

import com.ffusion.banklookup.beans.FinancialInstitution;
import com.ffusion.beans.Contact;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.ffs.bpw.interfaces.BPWBankInfo;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.logging.DebugLog;

public class WireTransferBank
  extends Contact
  implements WireDefines, Cloneable, Comparable
{
  private String A8;
  private String Bd;
  private String Bf;
  private String Bb;
  private String A7;
  private String Bc;
  private String Ba;
  private String Be;
  private String A9;
  private static final String BEAN_NAME = WireTransferBank.class.getName();
  
  public String getID()
  {
    return this.A8;
  }
  
  public void setID(String paramString)
  {
    this.A8 = paramString;
  }
  
  public String getSrvrBankID()
  {
    return this.Bd;
  }
  
  public void setSrvrBankID(String paramString)
  {
    this.Bd = paramString;
  }
  
  public String getBankName()
  {
    return this.Bf;
  }
  
  public void setBankName(String paramString)
  {
    this.Bf = XMLUtil.XMLDecode(paramString);
  }
  
  public String getComment()
  {
    return this.Bb;
  }
  
  public void setComment(String paramString)
  {
    this.Bb = paramString;
  }
  
  public String getRoutingFedWire()
  {
    return this.A7;
  }
  
  public void setRoutingFedWire(String paramString)
  {
    this.A7 = paramString;
  }
  
  public String getRoutingSwift()
  {
    return this.Bc;
  }
  
  public void setRoutingSwift(String paramString)
  {
    this.Bc = paramString;
  }
  
  public String getRoutingChips()
  {
    return this.Ba;
  }
  
  public void setRoutingChips(String paramString)
  {
    this.Ba = paramString;
  }
  
  public String getRoutingOther()
  {
    return this.Be;
  }
  
  public void setRoutingOther(String paramString)
  {
    this.Be = paramString;
  }
  
  public String getRoutingNumber(String paramString)
  {
    if (paramString.equals("F") == true) {
      return this.A7;
    }
    if (paramString.equals("S") == true) {
      return this.Bc;
    }
    if (paramString.equals("C") == true) {
      return this.Ba;
    }
    if (paramString.equals("O") == true) {
      return this.Be;
    }
    return null;
  }
  
  public String getRoutingNumberValue()
  {
    if ((this.A7 != null) && (this.A7.length() > 0)) {
      return this.A7;
    }
    if ((this.Bc != null) && (this.Bc.length() > 0)) {
      return this.Bc;
    }
    if ((this.Ba != null) && (this.Ba.length() > 0)) {
      return this.Ba;
    }
    if ((this.Be != null) && (this.Be.length() > 0)) {
      return this.Be;
    }
    return "";
  }
  
  public String getRoutingNumber()
  {
    if ((this.A7 != null) && (this.A7.length() > 0)) {
      return "FED_" + this.A7;
    }
    if ((this.Bc != null) && (this.Bc.length() > 0)) {
      return "SWIFT_" + this.Bc;
    }
    if ((this.Ba != null) && (this.Ba.length() > 0)) {
      return "CHIPS_" + this.Ba;
    }
    if ((this.Be != null) && (this.Be.length() > 0)) {
      return "NATIONAL_" + this.Be;
    }
    return "";
  }
  
  public String getAction()
  {
    return this.A9;
  }
  
  public void setAction(String paramString)
  {
    this.A9 = paramString;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if (paramString1.equals("ID")) {
      return isFilterable(String.valueOf(getID()), paramString2, paramString3);
    }
    if (paramString1.equals("BANK_NAME")) {
      return isFilterable(getBankName(), paramString2, paramString3);
    }
    if (paramString1.equals("CITY")) {
      return isFilterable(getCity(), paramString2, paramString3);
    }
    if (paramString1.equals("STATE")) {
      return isFilterable(getState(), paramString2, paramString3);
    }
    if (paramString1.equals("ZIP_CODE")) {
      return isFilterable(getZipCode(), paramString2, paramString3);
    }
    if (paramString1.equals("COUNTRY")) {
      return isFilterable(getCountry(), paramString2, paramString3);
    }
    if (paramString1.equals("ACTION")) {
      return isFilterable(getAction(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "BANK_NAME");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    WireTransferBank localWireTransferBank = (WireTransferBank)paramObject;
    int i = 1;
    if (paramString.equals("ID")) {
      i = getID().compareTo(localWireTransferBank.getID());
    } else if ((paramString.equals("BANK_NAME")) || (paramString.equals("NAME"))) {
      i = getBankName().compareTo(localWireTransferBank.getBankName());
    } else if (paramString.equals("CITY")) {
      i = getCity().compareTo(localWireTransferBank.getCity());
    } else if (paramString.equals("STATE")) {
      i = getState().compareTo(localWireTransferBank.getState());
    } else if (paramString.equals("ZIP_CODE")) {
      i = getZipCode().compareTo(localWireTransferBank.getZipCode());
    } else if (paramString.equals("COUNTRY")) {
      i = getCountry().compareTo(localWireTransferBank.getCountry());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "WIRE_TRANSFER_BANK");
    XMLHandler.appendTag(localStringBuffer, "ID", getID());
    XMLHandler.appendTag(localStringBuffer, "BANK_ID", getSrvrBankID());
    XMLHandler.appendTag(localStringBuffer, "BANK_NAME", getBankName());
    XMLHandler.appendTag(localStringBuffer, "COMMENT", getComment());
    XMLHandler.appendTag(localStringBuffer, "ROUTING_FEDWIRE", getRoutingFedWire());
    XMLHandler.appendTag(localStringBuffer, "ROUTING_CHIPS", getRoutingChips());
    XMLHandler.appendTag(localStringBuffer, "ROUTING_SWIFT", getRoutingSwift());
    XMLHandler.appendTag(localStringBuffer, "ROUTING_OTHER", getRoutingOther());
    XMLHandler.appendTag(localStringBuffer, "ACTION", getAction());
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "WIRE_TRANSFER_BANK");
    return localStringBuffer.toString();
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    if (paramString1.equalsIgnoreCase("ID")) {
      setID(paramString2);
    }
    if (paramString1.equalsIgnoreCase("BANK_ID")) {
      setSrvrBankID(paramString2);
    } else if (paramString1.equalsIgnoreCase("BANK_NAME")) {
      setBankName(paramString2);
    } else if (paramString1.equalsIgnoreCase("COMMENT")) {
      setComment(paramString2);
    } else if (paramString1.equalsIgnoreCase("ROUTING_FEDWIRE")) {
      setRoutingFedWire(paramString2);
    } else if (paramString1.equalsIgnoreCase("ROUTING_CHIPS")) {
      setRoutingChips(paramString2);
    } else if (paramString1.equalsIgnoreCase("ROUTING_SWIFT")) {
      setRoutingSwift(paramString2);
    } else if (paramString1.equalsIgnoreCase("ROUTING_OTHER")) {
      setRoutingOther(paramString2);
    } else if (paramString1.equalsIgnoreCase("ACTION")) {
      setAction(paramString2);
    } else {
      return super.set(paramString1, paramString2);
    }
    return true;
  }
  
  public void set(WireTransferBank paramWireTransferBank)
  {
    if (paramWireTransferBank == null) {
      return;
    }
    super.set(paramWireTransferBank);
    setID(paramWireTransferBank.getID());
    setSrvrBankID(paramWireTransferBank.getSrvrBankID());
    setBankName(paramWireTransferBank.getBankName());
    setComment(paramWireTransferBank.getComment());
    setRoutingFedWire(paramWireTransferBank.getRoutingFedWire());
    setRoutingSwift(paramWireTransferBank.getRoutingSwift());
    setRoutingChips(paramWireTransferBank.getRoutingChips());
    setRoutingOther(paramWireTransferBank.getRoutingOther());
    setAction(paramWireTransferBank.getAction());
  }
  
  public Object clone()
  {
    return super.clone();
  }
  
  public boolean isSame(WireTransferBank paramWireTransferBank)
  {
    if (paramWireTransferBank == null) {
      return false;
    }
    if (paramWireTransferBank.getRoutingNumber().equals(getRoutingNumber())) {
      return (paramWireTransferBank.getBankName().equals(getBankName())) && (paramWireTransferBank.getCity().equals(getCity())) && (paramWireTransferBank.getState().equals(getState())) && (paramWireTransferBank.getCountry().equals(getCountry()));
    }
    return false;
  }
  
  public BPWBankInfo getBankInfo()
  {
    BPWBankInfo localBPWBankInfo = new BPWBankInfo();
    localBPWBankInfo.setExtBankId(getID());
    localBPWBankInfo.setBankId(getSrvrBankID());
    if (isNullOrEmpty(this.Bf)) {
      localBPWBankInfo.setBankName(null);
    } else {
      localBPWBankInfo.setBankName(getBankName());
    }
    if (isNullOrEmpty(this.street)) {
      localBPWBankInfo.setBankAddr1(null);
    } else {
      localBPWBankInfo.setBankAddr1(getStreet());
    }
    if (isNullOrEmpty(this.street2)) {
      localBPWBankInfo.setBankAddr2(null);
    } else {
      localBPWBankInfo.setBankAddr2(getStreet2());
    }
    if (isNullOrEmpty(this.street3)) {
      localBPWBankInfo.setBankAddr3(null);
    } else {
      localBPWBankInfo.setBankAddr3(getStreet3());
    }
    if (isNullOrEmpty(this.city)) {
      localBPWBankInfo.setBankCity(null);
    } else {
      localBPWBankInfo.setBankCity(getCity());
    }
    if (isNullOrEmpty(this.state)) {
      localBPWBankInfo.setBankState(null);
    } else {
      localBPWBankInfo.setBankState(getState());
    }
    if (isNullOrEmpty(getZipCode())) {
      localBPWBankInfo.setBankPSCode(null);
    } else {
      localBPWBankInfo.setBankPSCode(getZipCode());
    }
    if (isNullOrEmpty(this.country)) {
      localBPWBankInfo.setBankCountry(null);
    } else {
      localBPWBankInfo.setBankCountry(getCountry());
    }
    if (isNullOrEmpty(getPhone())) {
      localBPWBankInfo.setBankPhone(null);
    } else {
      localBPWBankInfo.setBankPhone(getPhone());
    }
    if (isNullOrEmpty(this.Bc)) {
      localBPWBankInfo.setSwiftRTN(null);
    } else {
      localBPWBankInfo.setSwiftRTN(getRoutingSwift());
    }
    if (isNullOrEmpty(this.Ba)) {
      localBPWBankInfo.setChipsRTN(null);
    } else {
      localBPWBankInfo.setChipsRTN(getRoutingChips());
    }
    if (isNullOrEmpty(this.A7)) {
      localBPWBankInfo.setFedRTN(null);
    } else {
      localBPWBankInfo.setFedRTN(getRoutingFedWire());
    }
    if (isNullOrEmpty(this.Be)) {
      localBPWBankInfo.setOtherRTN(null);
    } else {
      localBPWBankInfo.setOtherRTN(getRoutingOther());
    }
    if (getAction() == null) {
      localBPWBankInfo.setAction("");
    } else {
      localBPWBankInfo.setAction(getAction());
    }
    return localBPWBankInfo;
  }
  
  public void setBankInfo(BPWBankInfo paramBPWBankInfo)
  {
    if (paramBPWBankInfo == null) {
      return;
    }
    setID(paramBPWBankInfo.getExtBankId());
    setSrvrBankID(paramBPWBankInfo.getBankId());
    setBankName(paramBPWBankInfo.getBankName());
    setStreet(paramBPWBankInfo.getBankAddr1());
    setStreet2(paramBPWBankInfo.getBankAddr2());
    setStreet3(paramBPWBankInfo.getBankAddr3());
    setCity(paramBPWBankInfo.getBankCity());
    setState(paramBPWBankInfo.getBankState());
    setZipCode(paramBPWBankInfo.getBankPSCode());
    setCountry(paramBPWBankInfo.getBankCountry());
    setPhone(paramBPWBankInfo.getBankPhone());
    setRoutingSwift(paramBPWBankInfo.getSwiftRTN());
    setRoutingChips(paramBPWBankInfo.getChipsRTN());
    setRoutingFedWire(paramBPWBankInfo.getFedRTN());
    setRoutingOther(paramBPWBankInfo.getOtherRTN());
    if (paramBPWBankInfo.getAction() == null) {
      setAction("");
    } else {
      setAction(paramBPWBankInfo.getAction());
    }
  }
  
  public void setFI(FinancialInstitution paramFinancialInstitution)
  {
    if (paramFinancialInstitution == null) {
      return;
    }
    setID(Integer.toString(paramFinancialInstitution.getInstitutionId()));
    setBankName(paramFinancialInstitution.getInstitutionName());
    setStreet(paramFinancialInstitution.getStreet());
    setStreet2(paramFinancialInstitution.getStreet2());
    setStreet3(paramFinancialInstitution.getStreet3());
    setCity(paramFinancialInstitution.getCity());
    setState(paramFinancialInstitution.getStateCode());
    setZipCode(paramFinancialInstitution.getZipCode());
    setCountry(paramFinancialInstitution.getCountry());
    setPhone(paramFinancialInstitution.getPhone());
    setRoutingSwift(paramFinancialInstitution.getSwiftBIC());
    setRoutingChips(paramFinancialInstitution.getChipsUID());
    setRoutingFedWire(paramFinancialInstitution.getWireRoutingNumber());
    setRoutingOther(paramFinancialInstitution.getNationalID());
  }
  
  public void setBPWFIInfo(BPWFIInfo paramBPWFIInfo)
  {
    if (paramBPWFIInfo == null) {
      return;
    }
    setID(paramBPWFIInfo.getFIId());
    setBankName(paramBPWFIInfo.getFIName());
    setRoutingFedWire(paramBPWFIInfo.getFIRTN());
    setRoutingChips(paramBPWFIInfo.getChipsRTN());
    setRoutingSwift(paramBPWFIInfo.getSwiftRTN());
    setRoutingOther(paramBPWFIInfo.getOtherRTN());
    setStreet(paramBPWFIInfo.getAddr1());
    setStreet2(paramBPWFIInfo.getAddr2());
    setStreet3(paramBPWFIInfo.getAddr3());
    setCity(paramBPWFIInfo.getCity());
    setState(paramBPWFIInfo.getState());
    setZipCode(paramBPWFIInfo.getPostalCode());
    setPhone(paramBPWFIInfo.getDayPhone());
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, WireTransferBank paramWireTransferBank, String paramString)
  {
    logChanges(paramHistoryTracker, BEAN_NAME, paramWireTransferBank, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, String paramString1, WireTransferBank paramWireTransferBank, String paramString2)
  {
    if (paramWireTransferBank == null)
    {
      DebugLog.log("WARNING: Unable to record history for wire bank modification due to null old bank.");
      return;
    }
    paramHistoryTracker.detectChange(paramString1, "BANK_NAME", paramWireTransferBank.Bf, this.Bf, paramString2);
    paramHistoryTracker.detectChange(paramString1, "ROUTING_FEDWIRE", paramWireTransferBank.A7, this.A7, paramString2);
    paramHistoryTracker.detectChange(paramString1, "ROUTING_CHIPS", paramWireTransferBank.Ba, this.Ba, paramString2);
    paramHistoryTracker.detectChange(paramString1, "ROUTING_SWIFT", paramWireTransferBank.Bc, this.Bc, paramString2);
    paramHistoryTracker.detectChange(paramString1, "ROUTING_OTHER", paramWireTransferBank.Be, this.Be, paramString2);
    super.logChanges(paramHistoryTracker, paramString1, paramWireTransferBank, paramString2);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, WireTransferBank paramWireTransferBank, ILocalizable paramILocalizable)
  {
    logChanges(paramHistoryTracker, BEAN_NAME, paramWireTransferBank, paramILocalizable);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, String paramString, WireTransferBank paramWireTransferBank, ILocalizable paramILocalizable)
  {
    if (paramWireTransferBank == null)
    {
      DebugLog.log("WARNING: Unable to record history for wire bank modification due to null old bank.");
      return;
    }
    paramHistoryTracker.detectChange(paramString, "BANK_NAME", paramWireTransferBank.Bf, this.Bf, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "ROUTING_FEDWIRE", paramWireTransferBank.A7, this.A7, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "ROUTING_CHIPS", paramWireTransferBank.Ba, this.Ba, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "ROUTING_SWIFT", paramWireTransferBank.Bc, this.Bc, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "ROUTING_OTHER", paramWireTransferBank.Be, this.Be, paramILocalizable);
    super.logChanges(paramHistoryTracker, paramString, paramWireTransferBank, paramILocalizable);
  }
  
  public boolean compareBank(WireTransferBank paramWireTransferBank)
  {
    if ((getID() != null) && (paramWireTransferBank.getID() != null) && (getID().compareToIgnoreCase(paramWireTransferBank.getID()) != 0)) {
      return false;
    }
    if ((getSrvrBankID() != null) && (paramWireTransferBank.getSrvrBankID() != null) && (getSrvrBankID().compareToIgnoreCase(paramWireTransferBank.getSrvrBankID()) != 0)) {
      return false;
    }
    if ((getBankName() != null) && (paramWireTransferBank.getBankName() != null) && (getBankName().compareToIgnoreCase(paramWireTransferBank.getBankName()) != 0)) {
      return false;
    }
    if ((getComment() != null) && (paramWireTransferBank.getComment() != null) && (getComment().compareToIgnoreCase(paramWireTransferBank.getComment()) != 0)) {
      return false;
    }
    if ((getRoutingFedWire() != null) && (paramWireTransferBank.getRoutingFedWire() != null) && (getRoutingFedWire().compareToIgnoreCase(paramWireTransferBank.getRoutingFedWire()) != 0)) {
      return false;
    }
    if ((getRoutingSwift() != null) && (paramWireTransferBank.getRoutingSwift() != null) && (getRoutingSwift().compareToIgnoreCase(paramWireTransferBank.getRoutingSwift()) != 0)) {
      return false;
    }
    if ((getRoutingChips() != null) && (paramWireTransferBank.getRoutingChips() != null) && (getRoutingChips().compareToIgnoreCase(paramWireTransferBank.getRoutingChips()) != 0)) {
      return false;
    }
    return (getRoutingOther() == null) || (paramWireTransferBank.getRoutingOther() == null) || (getRoutingOther().compareToIgnoreCase(paramWireTransferBank.getRoutingOther()) == 0);
  }
  
  protected static boolean isNullOrEmpty(String paramString)
  {
    return (paramString == null) || (paramString.length() == 0);
  }
  
  public void setAffiliateBank(AffiliateBank paramAffiliateBank)
  {
    if (paramAffiliateBank == null) {
      return;
    }
    setID(paramAffiliateBank.getId());
    setBankName(paramAffiliateBank.getAffiliateBankName());
    setRoutingFedWire(paramAffiliateBank.getAffiliateRoutingNum());
    setRoutingChips(paramAffiliateBank.getChipsID());
    setRoutingSwift(paramAffiliateBank.getSwiftBIC());
    setRoutingOther(paramAffiliateBank.getNationalID());
    setStreet(paramAffiliateBank.getStreet());
    setStreet2(paramAffiliateBank.getStreet2());
    setStreet3(paramAffiliateBank.getStreet3());
    setCity(paramAffiliateBank.getCity());
    setState(paramAffiliateBank.getState());
    setZipCode(paramAffiliateBank.getZipCode());
    setPhone(paramAffiliateBank.getPhone());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.wiretransfers.WireTransferBank
 * JD-Core Version:    0.7.0.1
 */