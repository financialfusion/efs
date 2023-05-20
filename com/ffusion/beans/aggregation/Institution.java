package com.ffusion.beans.aggregation;

import com.ffusion.beans.Contact;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.util.Locale;

public class Institution
  extends Contact
  implements Comparable, InstitutionTypes
{
  private String HP;
  private String HN;
  private String HM;
  private String HO;
  private String HL;
  private String HR;
  private String HJ;
  private int HI;
  private String HK;
  private InstitutionAccountTypes HQ;
  protected String trackingID;
  
  protected Institution()
  {
    this.HQ = new InstitutionAccountTypes();
    this.HI = 0;
  }
  
  protected Institution(Locale paramLocale)
  {
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.locale = paramLocale;
    this.HQ = new InstitutionAccountTypes(paramLocale);
    this.HI = 0;
  }
  
  public String getTrackingID()
  {
    return this.trackingID;
  }
  
  public void setTrackingID(String paramString)
  {
    this.trackingID = paramString;
  }
  
  public void setID(String paramString)
  {
    this.HP = paramString;
  }
  
  public String getID()
  {
    return this.HP;
  }
  
  public void setName(String paramString)
  {
    this.HN = paramString;
  }
  
  public String getName()
  {
    return this.HN;
  }
  
  public void setGroupID(String paramString)
  {
    this.HM = paramString;
  }
  
  public String getGroupID()
  {
    return this.HM;
  }
  
  public void setCustomerSupportPhoneNumber(String paramString)
  {
    this.HO = paramString;
  }
  
  public String getCustomerSupportPhoneNumber()
  {
    return this.HO;
  }
  
  public void setTechnicalSupportPhoneNumber(String paramString)
  {
    this.HL = paramString;
  }
  
  public String getTechnicalSupportPhoneNumber()
  {
    return this.HL;
  }
  
  public void setFax(String paramString)
  {
    this.HR = paramString;
  }
  
  public String getFax()
  {
    return this.HR;
  }
  
  public void setURL(String paramString)
  {
    this.HJ = paramString;
  }
  
  public String getURL()
  {
    return this.HJ;
  }
  
  public void setType(int paramInt)
  {
    this.HI = paramInt;
  }
  
  public void setType(String paramString)
  {
    try
    {
      this.HI = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this.HI = 0;
    }
  }
  
  public int getTypeValue()
  {
    return this.HI;
  }
  
  public String getType()
  {
    return String.valueOf(this.HI);
  }
  
  public void setTaxID(String paramString)
  {
    this.HK = paramString;
  }
  
  public String getTaxID()
  {
    return this.HK;
  }
  
  public void setInstitutionAccountTypes(InstitutionAccountTypes paramInstitutionAccountTypes)
  {
    this.HQ = paramInstitutionAccountTypes;
  }
  
  public InstitutionAccountTypes getInstitutionAccountTypes()
  {
    return this.HQ;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ID")) {
      this.HP = paramString2;
    } else if (paramString1.equals("NAME")) {
      this.HN = paramString2;
    } else if (paramString1.equals("GROUP_ID")) {
      this.HM = paramString2;
    } else if (paramString1.equals("FAX")) {
      this.HR = paramString2;
    } else if (paramString1.equals("URL")) {
      this.HJ = paramString2;
    } else if (paramString1.equals("TYPE")) {
      setType(paramString2);
    } else if (paramString1.equals("TAX_ID")) {
      this.HK = paramString2;
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Institution localInstitution = (Institution)paramObject;
    int i = 1;
    if ((paramString.equals("ID")) && (getID() != null) && (localInstitution.getID() != null)) {
      i = getID().compareTo(localInstitution.getID());
    } else if ((paramString.equals("NAME")) && (getName() != null) && (localInstitution.getName() != null)) {
      i = getName().compareTo(localInstitution.getName());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("ID")) && (getID() != null)) {
      return isFilterable(getID(), paramString2, paramString3);
    }
    if ((paramString1.equals("NAME")) && (getName() != null)) {
      return isFilterable(getName(), paramString2, paramString3);
    }
    if (paramString1.equals("TYPE")) {
      return isFilterable(getType(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void set(Institution paramInstitution)
  {
    setID(paramInstitution.getID());
    setName(paramInstitution.getName());
    setGroupID(paramInstitution.getGroupID());
    setFax(paramInstitution.getFax());
    setURL(paramInstitution.getURL());
    setType(paramInstitution.getType());
    setTaxID(paramInstitution.getTaxID());
    setInstitutionAccountTypes(paramInstitution.getInstitutionAccountTypes());
    super.set(paramInstitution);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "INSTITUTION");
    XMLHandler.appendTag(localStringBuffer, "ID", this.HP);
    XMLHandler.appendTag(localStringBuffer, "NAME", this.HN);
    XMLHandler.appendTag(localStringBuffer, "GROUP_ID", this.HM);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendTag(localStringBuffer, "FAX", this.HR);
    XMLHandler.appendTag(localStringBuffer, "URL", this.HJ);
    XMLHandler.appendTag(localStringBuffer, "TYPE", this.HI);
    XMLHandler.appendTag(localStringBuffer, "TAX_ID", this.HK);
    localStringBuffer.append(this.HQ.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "INSTITUTION");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends ExtendABean.InternalXMLHandler
  {
    boolean jdField_int;
    
    public a()
    {
      super();
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("INSTITUTIONACCOUNTTYPES")) {
        Institution.this.HQ.continueXMLParsing(getHandler());
      } else {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.aggregation.Institution
 * JD-Core Version:    0.7.0.1
 */