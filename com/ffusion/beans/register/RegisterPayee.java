package com.ffusion.beans.register;

import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.text.Collator;

public class RegisterPayee
  extends com.ffusion.beans.ExtendABean
  implements Comparable
{
  public static final String REGISTER_PAYEE = "REGISTER_PAYEE";
  public static final String ID = "ID";
  public static final String NAME = "NAME";
  public static final String DEFAULT_CATEGORY = "DEFAULT_CATEGORY";
  protected int id = 0;
  protected String name = null;
  protected int defaultCategory = 0;
  
  public void setId(String paramString)
  {
    this.id = Integer.parseInt(paramString);
  }
  
  public void setId(int paramInt)
  {
    this.id = paramInt;
  }
  
  public String getId()
  {
    return String.valueOf(this.id);
  }
  
  public int getIdValue()
  {
    return this.id;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setDefaultCategory(String paramString)
  {
    this.defaultCategory = Integer.parseInt(paramString);
  }
  
  public void setDefaultCategory(int paramInt)
  {
    this.defaultCategory = paramInt;
  }
  
  public String getDefaultCategory()
  {
    return String.valueOf(this.defaultCategory);
  }
  
  public int getDefaultCategoryValue()
  {
    return this.defaultCategory;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "NAME");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    RegisterPayee localRegisterPayee = (RegisterPayee)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if (paramString.equalsIgnoreCase("ID"))
    {
      if (this.id > localRegisterPayee.getIdValue()) {
        i = 1;
      } else if (this.id < localRegisterPayee.getIdValue()) {
        i = -1;
      } else {
        i = 0;
      }
    }
    else if (paramString.equalsIgnoreCase("NAME")) {
      i = com.ffusion.util.beans.ExtendABean.compareStrings(getName(), localRegisterPayee.getName(), localCollator);
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equalsIgnoreCase("NAME")) && (this.name != null)) {
      return isFilterable(this.name, paramString2, paramString3);
    }
    if (paramString1.equalsIgnoreCase("ID")) {
      return isFilterable(String.valueOf(this.id), paramString2, paramString3);
    }
    if (paramString1.equalsIgnoreCase("DEFAULT_CATEGORY")) {
      return isFilterable(String.valueOf(this.defaultCategory), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ID")) {
      setId(paramString2);
    } else if (paramString1.equals("NAME")) {
      setName(paramString2);
    } else if (paramString1.equals("DEFAULT_CATEGORY")) {
      setDefaultCategory(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public void set(RegisterPayee paramRegisterPayee)
  {
    setId(paramRegisterPayee.getId());
    setName(paramRegisterPayee.getName());
    setDefaultCategory(paramRegisterPayee.getDefaultCategory());
    super.set(paramRegisterPayee);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "REGISTER_PAYEE");
    XMLHandler.appendTag(localStringBuffer, "ID", this.id);
    XMLHandler.appendTag(localStringBuffer, "NAME", this.name);
    XMLHandler.appendTag(localStringBuffer, "DEFAULT_CATEGORY", String.valueOf(this.defaultCategory));
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "REGISTER_PAYEE");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.register.RegisterPayee
 * JD-Core Version:    0.7.0.1
 */