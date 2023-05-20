package com.ffusion.beans.dataconsolidator;

import com.ffusion.util.Sortable;
import com.ffusion.util.beans.ExtendABean;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class BAITypeCodeInfo
  extends BAITypeCodeInfoI18N
  implements Sortable
{
  public static final String SORTFIELD_CODE = "Code";
  public static final String SORTFIELD_TRANSACTION = "Trans";
  public static final String SORTFIELD_LEVEL = "Level";
  public static final String SORTFIELD_DATATYPE = "DataType";
  public static final String SORTFIELD_NUMDECIMALS = "NumDecimals";
  public static final String SORTFIELD_TRANSACTIONTYPE = "TransType";
  public static final String DEFAULT_SUBLEVEL = "Regular";
  private final int jdField_goto;
  private final int jdField_case;
  private final int jdField_new;
  private final int jdField_else;
  private final int jdField_try;
  private final int jdField_char;
  private final ArrayList jdField_int;
  private final HashMap jdField_for = new HashMap(5);
  private String jdField_do;
  
  public BAITypeCodeInfo(int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, ArrayList paramArrayList)
  {
    super(paramString);
    this.jdField_goto = paramInt1;
    this.jdField_case = paramInt2;
    this.jdField_new = paramInt3;
    this.jdField_else = paramInt4;
    this.jdField_try = paramInt5;
    this.jdField_char = paramInt6;
    this.jdField_int = paramArrayList;
    this.jdField_do = "Regular";
  }
  
  public String getDescription(String paramString)
  {
    if ((paramString == null) || ("en_US".equals(paramString))) {
      return super.getDescription();
    }
    BAITypeCodeInfoI18N localBAITypeCodeInfoI18N = (BAITypeCodeInfoI18N)this.jdField_for.get(paramString);
    if (localBAITypeCodeInfoI18N == null) {
      return super.getDescription();
    }
    return localBAITypeCodeInfoI18N.getDescription();
  }
  
  public String getDescription(Locale paramLocale)
  {
    if ((paramLocale == null) || (Locale.US.equals(paramLocale))) {
      return super.getDescription();
    }
    BAITypeCodeInfoI18N localBAITypeCodeInfoI18N = (BAITypeCodeInfoI18N)this.jdField_for.get(paramLocale.toString());
    if (localBAITypeCodeInfoI18N == null) {
      return super.getDescription();
    }
    return localBAITypeCodeInfoI18N.getDescription();
  }
  
  public void setDescription(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || ("en_US".equals(paramString1))) {
      setDescription(paramString2);
    } else {
      this.jdField_for.put(paramString1, new BAITypeCodeInfoI18N(paramString1, paramString2));
    }
  }
  
  public Iterator getLanguages()
  {
    return this.jdField_for.keySet().iterator();
  }
  
  public int getCode()
  {
    return this.jdField_goto;
  }
  
  public int getTransaction()
  {
    return this.jdField_case;
  }
  
  public int getLevel()
  {
    return this.jdField_new;
  }
  
  public int getDataType()
  {
    return this.jdField_else;
  }
  
  public String getSubLevel()
  {
    return this.jdField_do;
  }
  
  public void setSubLevel(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public int getNumDecimals()
  {
    return this.jdField_try;
  }
  
  public int getTransactionType()
  {
    return this.jdField_char;
  }
  
  public ArrayList getModules()
  {
    return this.jdField_int;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer("code = ");
    localStringBuffer.append(this.jdField_goto);
    localStringBuffer.append("\ttransactionType = ");
    localStringBuffer.append(this.jdField_char);
    localStringBuffer.append("\ttransaction = ");
    localStringBuffer.append(this.jdField_case);
    localStringBuffer.append("\tlevel = ");
    localStringBuffer.append(this.jdField_new);
    localStringBuffer.append("\tsublevel = ");
    localStringBuffer.append(this.jdField_do);
    localStringBuffer.append("\tdataType = ");
    localStringBuffer.append(this.jdField_else);
    localStringBuffer.append("\tnumberDecimals = ");
    localStringBuffer.append(this.jdField_try);
    localStringBuffer.append("\tnumber of module = ");
    localStringBuffer.append("\t");
    localStringBuffer.append(super.toString());
    localStringBuffer.append(this.jdField_int == null ? 0 : this.jdField_int.size());
    Iterator localIterator = this.jdField_for.values().iterator();
    while (localIterator.hasNext())
    {
      localStringBuffer.append("\r\n\t");
      localStringBuffer.append(localIterator.next().toString());
    }
    return localStringBuffer.toString();
  }
  
  public int compare(Object paramObject, String paramString)
  {
    BAITypeCodeInfo localBAITypeCodeInfo = (BAITypeCodeInfo)paramObject;
    int i;
    if ("Trans".equals(paramString))
    {
      i = jdMethod_if(this.jdField_case, localBAITypeCodeInfo.getTransaction());
    }
    else if ("Level".equals(paramString))
    {
      i = jdMethod_if(this.jdField_new, localBAITypeCodeInfo.getLevel());
    }
    else if ("DataType".equals(paramString))
    {
      i = jdMethod_if(this.jdField_else, localBAITypeCodeInfo.getDataType());
    }
    else if ("NumDecimals".equals(paramString))
    {
      i = jdMethod_if(this.jdField_try, localBAITypeCodeInfo.getNumDecimals());
    }
    else if ("TransType".equals(paramString))
    {
      i = jdMethod_if(this.jdField_char, localBAITypeCodeInfo.getTransactionType());
    }
    else if ("Code".equals(paramString))
    {
      i = jdMethod_if(this.jdField_goto, localBAITypeCodeInfo.getCode());
    }
    else if ("Desc".equals(paramString))
    {
      Collator localCollator = doGetCollator();
      i = ExtendABean.compareStrings(getDescription(this.locale), localBAITypeCodeInfo.getDescription(this.locale), localCollator);
    }
    else
    {
      i = super.compare(paramObject, paramString);
    }
    if (i > 0) {
      return 1;
    }
    if (i < 0) {
      return -1;
    }
    return 0;
  }
  
  private int jdMethod_if(int paramInt1, int paramInt2)
  {
    if (paramInt1 > paramInt2) {
      return 1;
    }
    if (paramInt1 < paramInt2) {
      return -1;
    }
    return 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.dataconsolidator.BAITypeCodeInfo
 * JD-Core Version:    0.7.0.1
 */