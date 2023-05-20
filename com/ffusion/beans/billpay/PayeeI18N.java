package com.ffusion.beans.billpay;

import com.ffusion.beans.Contact;
import com.ffusion.util.XMLHandler;
import java.text.Collator;
import java.util.Locale;

public class PayeeI18N
  extends Contact
  implements PayeeStatus, Comparable
{
  protected static final String BEAN_NAME = PayeeI18N.class.getName();
  protected String name;
  protected String id;
  protected int error;
  
  public PayeeI18N()
  {
    this.error = 0;
  }
  
  public PayeeI18N(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setName(String paramString)
  {
    if (paramString != null) {
      paramString = paramString.trim();
    }
    this.name = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setID(String paramString)
  {
    this.id = paramString;
  }
  
  public String getID()
  {
    return this.id;
  }
  
  public void setError(int paramInt)
  {
    this.error = paramInt;
  }
  
  public int getErrorValue()
  {
    return this.error;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void set(PayeeI18N paramPayeeI18N)
  {
    if ((this == paramPayeeI18N) || (paramPayeeI18N == null)) {
      return;
    }
    super.set(paramPayeeI18N);
    setName(paramPayeeI18N.getName());
    setID(paramPayeeI18N.getID());
    this.error = paramPayeeI18N.getErrorValue();
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    Payee localPayee = (Payee)paramObject;
    int i;
    if (this == localPayee)
    {
      i = 0;
    }
    else
    {
      i = this.name.compareTo(localPayee.getName());
      if ((i == 0) && (i == 0)) {
        i = this.id.compareTo(localPayee.getID());
      }
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    PayeeI18N localPayeeI18N = (PayeeI18N)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("ID")) && (getID() != null) && (localPayeeI18N.getID() != null)) {
      i = localCollator.compare(getID(), localPayeeI18N.getID());
    } else if ((paramString.equals("NAME")) && (getName() != null) && (localPayeeI18N.getName() != null)) {
      i = localCollator.compare(getName(), localPayeeI18N.getName());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof Payee)) {
      return false;
    }
    Payee localPayee = (Payee)paramObject;
    if ((getID() != null) && (!getID().equals(localPayee.getID()))) {
      return false;
    }
    if ((getName() != null) && (!getName().equals(localPayee.getName()))) {
      return false;
    }
    return super.equals(paramObject);
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    String str = paramString1.toUpperCase();
    if ((str.equals("ID")) && (getID() != null)) {
      return isFilterable(getID(), paramString2, paramString3);
    }
    if ((str.equals("NAME")) && (getName() != null)) {
      return isFilterable(getName(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "PAYEEI18N");
    XMLHandler.appendTag(localStringBuffer, "ID", this.id);
    XMLHandler.appendTag(localStringBuffer, "NAME", this.name);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "PAYEEI18N");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpay.PayeeI18N
 * JD-Core Version:    0.7.0.1
 */