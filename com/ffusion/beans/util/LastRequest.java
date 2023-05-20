package com.ffusion.beans.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;

public class LastRequest
{
  private static String jdField_if = "checked ";
  private static String jdField_int = "selected ";
  private static String jdField_new = "disabled ";
  private HashMap jdField_for = new HashMap();
  private boolean a;
  private String jdField_case = "";
  private String jdField_byte = "";
  private String jdField_else = "";
  private boolean jdField_do;
  private String jdField_char = "";
  private String jdField_goto = "";
  private boolean jdField_try;
  
  public LastRequest(HttpServletRequest paramHttpServletRequest)
  {
    this.a = false;
    Enumeration localEnumeration = paramHttpServletRequest.getParameterNames();
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      String[] arrayOfString = paramHttpServletRequest.getParameterValues(str);
      this.jdField_for.put(str, arrayOfString);
    }
  }
  
  public LastRequest()
  {
    this.a = true;
  }
  
  public void setName(String paramString)
  {
    this.jdField_case = paramString;
  }
  
  public String getName()
  {
    return this.jdField_case;
  }
  
  public String getNameTag()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    jdField_if(localStringBuffer, this.jdField_case);
    return localStringBuffer.toString();
  }
  
  public void setTextDefaultValue(String paramString)
  {
    this.jdField_byte = paramString;
  }
  
  public String getTextValue()
  {
    String str = a(this.jdField_case);
    if (str == null) {
      str = this.jdField_byte;
    }
    return str;
  }
  
  public void setTextValue(String paramString)
  {
    this.a = false;
    String[] arrayOfString1 = (String[])this.jdField_for.get(this.jdField_case);
    ArrayList localArrayList = new ArrayList();
    if (arrayOfString1 != null) {
      for (int i = 0; i < arrayOfString1.length; i++) {
        localArrayList.add(arrayOfString1[i]);
      }
    }
    localArrayList.add(paramString);
    String[] arrayOfString2 = (String[])localArrayList.toArray(new String[0]);
    this.jdField_for.put(this.jdField_case, arrayOfString2);
  }
  
  public String getTextValueTag()
  {
    return jdField_if(getTextValue());
  }
  
  public String getTextAttribs()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    jdField_if(localStringBuffer, this.jdField_case);
    a(localStringBuffer, getTextValue());
    return localStringBuffer.toString();
  }
  
  public void setCheckboxValue(String paramString)
  {
    this.jdField_else = paramString;
  }
  
  public String getCheckboxValue()
  {
    return this.jdField_else;
  }
  
  public String getCheckboxValueTag()
  {
    return jdField_if(getCheckboxValue());
  }
  
  public void setCheckboxChecked(String paramString)
  {
    this.jdField_do = new Boolean(paramString).booleanValue();
  }
  
  public String getChecked()
  {
    if (this.a) {
      return this.jdField_do ? jdField_if : "";
    }
    if (a((String[])this.jdField_for.get(this.jdField_case), this.jdField_else)) {
      return jdField_if;
    }
    return "";
  }
  
  private static boolean a(String[] paramArrayOfString, String paramString)
  {
    if ((paramArrayOfString == null) || (paramString == null)) {
      return false;
    }
    for (int i = 0; i < paramArrayOfString.length; i++) {
      if (paramString.equals(paramArrayOfString[i])) {
        return true;
      }
    }
    return false;
  }
  
  public String getCheckboxAttribs()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    jdField_if(localStringBuffer, this.jdField_case);
    a(localStringBuffer, getCheckboxValue());
    localStringBuffer.append(getChecked());
    return localStringBuffer.toString();
  }
  
  public void setSelectValue(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public String getSelectValue()
  {
    return this.jdField_char;
  }
  
  public String getSelectValueTag()
  {
    return jdField_if(getSelectValue());
  }
  
  public void setSelectDefaultValue(String paramString)
  {
    this.jdField_goto = paramString;
  }
  
  public String getSelected()
  {
    String str1 = getValueSelected();
    if (((str1 == null) && (this.jdField_char == null)) || ((str1 != null) && (str1.equals(this.jdField_char)))) {
      return jdField_int;
    }
    if ((str1 != null) && (str1.indexOf(",") > 0))
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(str1, ",");
      String str2 = null;
      while (localStringTokenizer.hasMoreTokens())
      {
        str2 = localStringTokenizer.nextToken();
        if ((str2 != null) && (str2.equals(this.jdField_char))) {
          return jdField_int;
        }
      }
    }
    return "";
  }
  
  public String getValueSelected()
  {
    if (this.a) {
      return this.jdField_goto;
    }
    return a(this.jdField_case);
  }
  
  public String getSelectAttribs()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    a(localStringBuffer, this.jdField_char);
    localStringBuffer.append(getSelected());
    return localStringBuffer.toString();
  }
  
  public void setDisabledDefault(String paramString)
  {
    this.jdField_try = new Boolean(paramString).booleanValue();
  }
  
  public String getDisabled()
  {
    if (this.a) {
      return this.jdField_try ? jdField_new : "";
    }
    if (this.jdField_for.containsKey(this.jdField_case)) {
      return "";
    }
    return jdField_new;
  }
  
  private static String jdField_if(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    a(localStringBuffer, paramString);
    return localStringBuffer.toString();
  }
  
  private static void a(StringBuffer paramStringBuffer, String paramString)
  {
    paramStringBuffer.append("value=\"").append(paramString).append("\" ");
  }
  
  private static void jdField_if(StringBuffer paramStringBuffer, String paramString)
  {
    paramStringBuffer.append("name=\"").append(paramString).append("\" ");
  }
  
  private String a(String paramString)
  {
    String[] arrayOfString = (String[])this.jdField_for.get(paramString);
    if ((arrayOfString != null) && (arrayOfString.length > 0)) {
      return arrayOfString[0];
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.LastRequest
 * JD-Core Version:    0.7.0.1
 */