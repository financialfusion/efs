package com.ffusion.beans.register;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.io.Reader;
import java.util.Iterator;

public class RegisterCategories
  extends FilteredList
  implements XMLable
{
  public static final String REGISTER_CATEGORIES = "REGISTER_CATEGORIES";
  private RegisterCategory jdField_byte = null;
  
  public boolean add(Object paramObject)
  {
    if ((paramObject instanceof RegisterCategory))
    {
      RegisterCategory localRegisterCategory = (RegisterCategory)paramObject;
      return super.add(localRegisterCategory);
    }
    return false;
  }
  
  public RegisterCategory create()
  {
    RegisterCategory localRegisterCategory = new RegisterCategory();
    super.add(localRegisterCategory);
    return localRegisterCategory;
  }
  
  public void setCurrent(String paramString)
  {
    this.jdField_byte = getById(paramString);
  }
  
  public boolean hasSubcategories()
  {
    if (this.jdField_byte == null) {
      return false;
    }
    if (this.jdField_byte.getParentCategoryValue() != -1) {
      return false;
    }
    String str = this.jdField_byte.getId();
    setFilter("All");
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      RegisterCategory localRegisterCategory = (RegisterCategory)localIterator.next();
      if (localRegisterCategory.getParentCategory().equals(str)) {
        return true;
      }
    }
    return false;
  }
  
  public boolean getHasSubcategories()
  {
    return hasSubcategories();
  }
  
  public RegisterCategories getSubcategories()
  {
    RegisterCategories localRegisterCategories = new RegisterCategories();
    if (this.jdField_byte != null)
    {
      String str = this.jdField_byte.getId();
      setFilter("All");
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        RegisterCategory localRegisterCategory = (RegisterCategory)localIterator.next();
        if (localRegisterCategory.getParentCategory().equals(str)) {
          localRegisterCategories.add(localRegisterCategory);
        }
      }
    }
    return localRegisterCategories;
  }
  
  public boolean hasParent()
  {
    if (this.jdField_byte == null) {
      return false;
    }
    return this.jdField_byte.getParentCategoryValue() != -1;
  }
  
  public boolean getHasParent()
  {
    return hasParent();
  }
  
  public String getParentName()
  {
    if (this.jdField_byte == null) {
      return null;
    }
    if (this.jdField_byte.getParentCategoryValue() == -1) {
      return "";
    }
    RegisterCategory localRegisterCategory = getById(this.jdField_byte.getParentCategory());
    return localRegisterCategory.getName();
  }
  
  public String getName()
  {
    if (this.jdField_byte == null) {
      return null;
    }
    return this.jdField_byte.getName();
  }
  
  public String getId()
  {
    if (this.jdField_byte == null) {
      return null;
    }
    return this.jdField_byte.getId();
  }
  
  public String getType()
  {
    if (this.jdField_byte == null) {
      return null;
    }
    return this.jdField_byte.getType();
  }
  
  public boolean getTaxRelatedValue()
  {
    if (this.jdField_byte == null) {
      return false;
    }
    return this.jdField_byte.getTaxRelatedValue();
  }
  
  public String getTaxRelated()
  {
    if (this.jdField_byte == null) {
      return null;
    }
    return this.jdField_byte.getTaxRelated();
  }
  
  public RegisterCategory getById(String paramString)
  {
    Object localObject = null;
    setFilter("All");
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      RegisterCategory localRegisterCategory = (RegisterCategory)localIterator.next();
      if (localRegisterCategory.getId().equals(paramString))
      {
        localObject = localRegisterCategory;
        break;
      }
    }
    return localObject;
  }
  
  public void removeById(String paramString)
  {
    setFilter("All");
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      RegisterCategory localRegisterCategory = (RegisterCategory)localIterator.next();
      if (localRegisterCategory.getId().equalsIgnoreCase(paramString))
      {
        localIterator.remove();
        break;
      }
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
    XMLHandler.appendBeginTag(localStringBuffer, "REGISTER_CATEGORIES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((RegisterCategory)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "REGISTER_CATEGORIES");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void startXMLParsing(Reader paramReader)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null), paramReader);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  private class a
    extends XMLHandler
  {
    private a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("REGISTER_CATEGORY"))
      {
        RegisterCategory localRegisterCategory = RegisterCategories.this.create();
        localRegisterCategory.continueXMLParsing(getHandler());
      }
    }
    
    a(RegisterCategories.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.register.RegisterCategories
 * JD-Core Version:    0.7.0.1
 */