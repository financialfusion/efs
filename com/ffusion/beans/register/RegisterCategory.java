package com.ffusion.beans.register;

import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import java.text.Collator;

public class RegisterCategory
  extends com.ffusion.beans.ExtendABean
  implements Comparable
{
  public static final String REGISTER_CATEGORY = "REGISTER_CATEGORY";
  public static final int DEFAULT_REGISTER_CATEGORY = 0;
  public static final int RETAIL_CATEGORY_INCOME = 0;
  public static final int RETAIL_CATEGORY_EXPENSE = 1;
  public static final int BUSINESS_CATEGORY_INCOME = 2;
  public static final int BUSINESS_CATEGORY_EXPENSE = 3;
  public static final int NO_PARENT_CATEGORY = -1;
  public static final String ID = "ID";
  public static final String NAME = "NAME";
  public static final String DESCRIPTION = "DESCRIPTION";
  public static final String TYPE = "TYPE";
  public static final String TAX_RELATED = "TAX_RELATED";
  public static final String PARENT_CATEGORY = "PARENT_CATEGORY";
  public static final String IS_DEFAULT_CATEGORY = "IS_DEFAULT_CATEGORY";
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.register.resources";
  private int aVH = 0;
  private String aVJ = null;
  private String aVI = null;
  private int aVD = 1;
  private boolean aVG = false;
  private int aVE = -1;
  private boolean aVF = false;
  
  public RegisterCategory() {}
  
  public RegisterCategory(int paramInt)
  {
    this.aVH = paramInt;
  }
  
  public RegisterCategory(String paramString1, String paramString2, int paramInt1, boolean paramBoolean, int paramInt2)
  {
    this.aVJ = paramString1;
    this.aVI = paramString2;
    this.aVD = paramInt1;
    this.aVG = paramBoolean;
    this.aVE = paramInt2;
  }
  
  public RegisterCategory(String paramString, int paramInt)
  {
    this.aVJ = paramString;
    this.aVD = paramInt;
  }
  
  public void setId(String paramString)
  {
    this.aVH = Integer.parseInt(paramString);
  }
  
  public void setId(int paramInt)
  {
    this.aVH = paramInt;
  }
  
  public String getId()
  {
    return String.valueOf(this.aVH);
  }
  
  public int getIdValue()
  {
    return this.aVH;
  }
  
  public void setName(String paramString)
  {
    this.aVJ = paramString;
  }
  
  public String getName()
  {
    if (this.aVF) {
      return ResourceUtil.getString("DefCategory" + getId(), "com.ffusion.beans.register.resources", this.locale);
    }
    return this.aVJ;
  }
  
  public void setDescription(String paramString)
  {
    this.aVI = paramString;
  }
  
  public String getDescription()
  {
    if (this.aVF) {
      return ResourceUtil.getString("DefCategoryDesc" + getId(), "com.ffusion.beans.register.resources", this.locale);
    }
    return this.aVI;
  }
  
  public void setType(String paramString)
  {
    this.aVD = Integer.parseInt(paramString);
  }
  
  public void setType(int paramInt)
  {
    this.aVD = paramInt;
  }
  
  public String getType()
  {
    return String.valueOf(this.aVD);
  }
  
  public int getTypeValue()
  {
    return this.aVD;
  }
  
  public void setTaxRelated(String paramString)
  {
    this.aVG = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setTaxRelated(boolean paramBoolean)
  {
    this.aVG = paramBoolean;
  }
  
  public String getTaxRelated()
  {
    return String.valueOf(this.aVG);
  }
  
  public boolean getTaxRelatedValue()
  {
    return this.aVG;
  }
  
  public void setParentCategory(String paramString)
  {
    this.aVE = Integer.parseInt(paramString);
  }
  
  public void setParentCategory(int paramInt)
  {
    this.aVE = paramInt;
  }
  
  public String getParentCategory()
  {
    return String.valueOf(this.aVE);
  }
  
  public int getParentCategoryValue()
  {
    return this.aVE;
  }
  
  public void setIsDefaultCategory(String paramString)
  {
    this.aVF = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setIsDefaultCategory(boolean paramBoolean)
  {
    this.aVF = paramBoolean;
  }
  
  public String getIsDefaultCategory()
  {
    return String.valueOf(this.aVF);
  }
  
  public boolean getIsDefaultCategoryValue()
  {
    return this.aVF;
  }
  
  public void set(RegisterCategory paramRegisterCategory)
  {
    setId(paramRegisterCategory.getId());
    setName(paramRegisterCategory.getName());
    setDescription(paramRegisterCategory.getDescription());
    setType(paramRegisterCategory.getTypeValue());
    setTaxRelated(paramRegisterCategory.getTaxRelatedValue());
    setParentCategory(paramRegisterCategory.getParentCategoryValue());
    setIsDefaultCategory(paramRegisterCategory.getIsDefaultCategoryValue());
    super.set(paramRegisterCategory);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ID")) {
      setId(paramString2);
    } else if (paramString1.equals("NAME")) {
      setName(paramString2);
    } else if (paramString1.equals("DESCRIPTION")) {
      setDescription(paramString2);
    } else if (paramString1.equals("TYPE")) {
      setType(paramString2);
    } else if (paramString1.equals("TAX_RELATED")) {
      setTaxRelated(paramString2);
    } else if (paramString1.equals("PARENT_CATEGORY")) {
      setParentCategory(paramString2);
    } else if (paramString1.equals("IS_DEFAULT_CATEGORY")) {
      setIsDefaultCategory(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "NAME");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    RegisterCategory localRegisterCategory = (RegisterCategory)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if (paramString.equalsIgnoreCase("ID"))
    {
      if (this.aVH > localRegisterCategory.getIdValue()) {
        i = 1;
      } else if (this.aVH < localRegisterCategory.getIdValue()) {
        i = -1;
      } else {
        i = 0;
      }
    }
    else if ((paramString.equalsIgnoreCase("NAME")) && (this.aVJ != null) && (localRegisterCategory.getName() != null)) {
      i = com.ffusion.util.beans.ExtendABean.compareStrings(this.aVJ, localRegisterCategory.getName(), localCollator);
    } else if (paramString.equalsIgnoreCase("DESCRIPTION")) {
      i = com.ffusion.util.beans.ExtendABean.compareStrings(this.aVI, localRegisterCategory.getDescription(), localCollator);
    } else if (paramString.equalsIgnoreCase("TYPE"))
    {
      if (this.aVD > localRegisterCategory.getTypeValue()) {
        i = 1;
      } else if (this.aVD < localRegisterCategory.getTypeValue()) {
        i = -1;
      } else {
        i = 0;
      }
    }
    else if (paramString.equalsIgnoreCase("PARENT_CATEGORY"))
    {
      if (this.aVE > localRegisterCategory.getParentCategoryValue()) {
        i = 1;
      } else if (this.aVE < localRegisterCategory.getParentCategoryValue()) {
        i = -1;
      } else {
        i = 0;
      }
    }
    else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    String str = paramString1.toUpperCase();
    if (str.equals("ID")) {
      return isFilterable(String.valueOf(this.aVH), paramString2, paramString3);
    }
    if ((str.equals("NAME")) && (this.aVJ != null)) {
      return isFilterable(this.aVJ, paramString2, paramString3);
    }
    if (str.equals("DESCRIPTION")) {
      return isFilterable(this.aVI, paramString2, paramString3);
    }
    if (str.equals("TYPE")) {
      return isFilterable(String.valueOf(this.aVD), paramString2, paramString3);
    }
    if (str.equals("TAX_RELATED")) {
      return isFilterable(String.valueOf(this.aVG), paramString2, paramString3);
    }
    if (str.equals("PARENT_CATEGORY")) {
      return isFilterable(String.valueOf(this.aVE), paramString2, paramString3);
    }
    if (str.equals("IS_DEFAULT_CATEGORY")) {
      return isFilterable(String.valueOf(this.aVF), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "REGISTER_CATEGORY");
    XMLHandler.appendTag(localStringBuffer, "ID", this.aVH);
    XMLHandler.appendTag(localStringBuffer, "NAME", this.aVJ);
    XMLHandler.appendTag(localStringBuffer, "DESCRIPTION", this.aVI);
    XMLHandler.appendTag(localStringBuffer, "TYPE", this.aVD);
    XMLHandler.appendTag(localStringBuffer, "TAX_RELATED", String.valueOf(this.aVG));
    XMLHandler.appendTag(localStringBuffer, "PARENT_CATEGORY", this.aVE);
    XMLHandler.appendTag(localStringBuffer, "IS_DEFAULT_CATEGORY", String.valueOf(this.aVF));
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "REGISTER_CATEGORY");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.register.RegisterCategory
 * JD-Core Version:    0.7.0.1
 */