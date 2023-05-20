package com.ffusion.beans.ach;

import com.ffusion.util.XMLHandler;
import java.io.Serializable;

public class TaxFormBank
  implements Serializable
{
  protected String bankName;
  protected String bankRoutingNumber;
  protected String bankAccountNumber;
  protected String bankAccountType;
  protected String displayName;
  protected String comment = "";
  
  public String getBankName()
  {
    return this.bankName;
  }
  
  public void setBankName(String paramString)
  {
    this.bankName = paramString;
  }
  
  public String getDisplayName()
  {
    return this.displayName;
  }
  
  public void setDisplayName(String paramString)
  {
    this.displayName = paramString;
  }
  
  public String getComment()
  {
    return this.comment;
  }
  
  public void setComment(String paramString)
  {
    this.comment = paramString;
  }
  
  public String getBankRoutingNumber()
  {
    return this.bankRoutingNumber;
  }
  
  public void setBankRoutingNumber(String paramString)
  {
    this.bankRoutingNumber = paramString;
  }
  
  public String getBankAccountType()
  {
    return this.bankAccountType;
  }
  
  public void setBankAccountType(String paramString)
  {
    this.bankAccountType = paramString;
  }
  
  public String getBankAccountNumber()
  {
    return this.bankAccountNumber;
  }
  
  public String getID()
  {
    return this.bankRoutingNumber + "-" + this.bankAccountNumber;
  }
  
  public boolean isValid()
  {
    boolean bool = true;
    if ((getBankAccountNumber() == null) || (getBankAccountNumber().length() == 0)) {
      bool = false;
    }
    if ((getBankRoutingNumber() == null) || (getBankRoutingNumber().length() == 0)) {
      bool = false;
    }
    return bool;
  }
  
  public boolean isEmpty()
  {
    return (getBankAccountNumber() == null) && (getBankAccountType() == null) && (getBankName() == null) && (getBankRoutingNumber() == null);
  }
  
  public void setBankAccountNumber(String paramString)
  {
    this.bankAccountNumber = paramString;
  }
  
  public void set(TaxFormBank paramTaxFormBank)
  {
    if (paramTaxFormBank == null) {
      return;
    }
    this.bankAccountNumber = paramTaxFormBank.getBankAccountNumber();
    this.bankAccountType = paramTaxFormBank.getBankAccountType();
    this.bankRoutingNumber = paramTaxFormBank.getBankRoutingNumber();
    this.bankName = paramTaxFormBank.getBankName();
    this.displayName = paramTaxFormBank.getDisplayName();
    this.comment = paramTaxFormBank.getComment();
  }
  
  public void addOrMerge(TaxFormBank paramTaxFormBank)
  {
    if (paramTaxFormBank == null) {
      return;
    }
    if (paramTaxFormBank.getBankAccountNumber() != null) {
      setBankAccountNumber(paramTaxFormBank.getBankAccountNumber());
    }
    if (paramTaxFormBank.getBankAccountType() != null) {
      setBankAccountType(paramTaxFormBank.getBankAccountType());
    }
    if (paramTaxFormBank.getBankName() != null) {
      setBankName(paramTaxFormBank.getBankName());
    }
    if (paramTaxFormBank.getBankRoutingNumber() != null) {
      setBankRoutingNumber(paramTaxFormBank.getBankRoutingNumber());
    }
    if (paramTaxFormBank.getDisplayName() != null) {
      setDisplayName(paramTaxFormBank.getDisplayName());
    }
    if (paramTaxFormBank.getComment() != null) {
      setComment(paramTaxFormBank.getComment());
    }
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "DEPOSITACCOUNT");
    XMLHandler.appendTag(localStringBuffer, "BANKNAME", getBankName());
    XMLHandler.appendTag(localStringBuffer, "ROUTINGNO", getBankRoutingNumber());
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTNO", getBankAccountNumber());
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTTYPE", getBankAccountType());
    XMLHandler.appendTag(localStringBuffer, "NAME", getDisplayName());
    XMLHandler.appendTag(localStringBuffer, "COMMENT", getComment());
    XMLHandler.appendEndTag(localStringBuffer, "DEPOSITACCOUNT");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str1 = new String(paramArrayOfChar, paramInt1, paramInt2);
      str1 = str1.trim();
      String str2 = getElement();
      if (str2.equalsIgnoreCase("BANKNAME")) {
        TaxFormBank.this.setBankName(str1);
      } else if (str2.equalsIgnoreCase("ROUTINGNO")) {
        TaxFormBank.this.setBankRoutingNumber(str1);
      } else if (str2.equalsIgnoreCase("ACCOUNTNO")) {
        TaxFormBank.this.setBankAccountNumber(str1);
      } else if (str2.equalsIgnoreCase("ACCOUNTTYPE")) {
        TaxFormBank.this.setBankAccountType(str1);
      } else if (str2.equalsIgnoreCase("NAME")) {
        TaxFormBank.this.setDisplayName(str1);
      } else if (str2.equalsIgnoreCase("COMMENT")) {
        TaxFormBank.this.setComment(str1);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.TaxFormBank
 * JD-Core Version:    0.7.0.1
 */