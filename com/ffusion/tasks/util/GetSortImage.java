package com.ffusion.tasks.util;

import com.ffusion.jtf.Task;
import com.ffusion.util.FilteredList;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetSortImage
  implements Task
{
  private String jdField_goto;
  private String jdField_long;
  private String jdField_char;
  private String b;
  private String jdField_else;
  private String jdField_void;
  private FilteredList jdField_null;
  private boolean jdField_case;
  protected String successURL = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this.jdField_goto != null) && (this.jdField_goto.trim().length() > 0)) {
      this.jdField_null = ((FilteredList)localHttpSession.getAttribute(this.jdField_goto));
    }
    return this.successURL;
  }
  
  public String getCollection()
  {
    return this.jdField_goto;
  }
  
  public void setCollection(String paramString)
  {
    this.jdField_goto = null;
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.jdField_goto = paramString;
    }
  }
  
  public String getSortedBy()
  {
    return this.jdField_long;
  }
  
  public void setSortedBy(String paramString)
  {
    this.jdField_long = paramString;
  }
  
  public String getToggleSortedBy()
  {
    int i = 0;
    int j = 0;
    if (((this.jdField_null == null) || (this.jdField_null.getSortedBy() == null) || (this.jdField_null.getSortedBy().length() == 0)) && ((this.jdField_long == null) || (this.jdField_long.length() == 0))) {
      return "";
    }
    String str1 = null;
    if ((this.jdField_null != null) && (this.jdField_null.getSortedBy() != null) && (this.jdField_null.getSortedBy().length() > 0)) {
      str1 = this.jdField_null.getSortedBy();
    } else {
      str1 = this.jdField_long;
    }
    Object localObject = new String();
    StringTokenizer localStringTokenizer = new StringTokenizer(str1, ",");
    while (localStringTokenizer.hasMoreTokens())
    {
      String str2 = (String)localStringTokenizer.nextElement();
      if ((str2 != null) && (str2.trim().equalsIgnoreCase(this.jdField_char.trim())))
      {
        localObject = str2;
        j = 1;
      }
      if ((str2 != null) && (str2.trim().equalsIgnoreCase("REVERSE"))) {
        i = 1;
      }
    }
    if (i == 0) {
      return (String)localObject + ",REVERSE";
    }
    return localObject;
  }
  
  public void setCompare(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public String getAnchor()
  {
    StringBuffer localStringBuffer = new StringBuffer("<a href=\"?");
    if (this.jdField_goto != null) {
      localStringBuffer.append(this.jdField_goto);
    } else {
      localStringBuffer.append(this.jdField_long);
    }
    localStringBuffer.append(".ToggleSortedBy=");
    localStringBuffer.append(this.jdField_char);
    localStringBuffer.append("\">");
    localStringBuffer.append(toString());
    localStringBuffer.append("</a>");
    return localStringBuffer.toString();
  }
  
  public void setNoSort(String paramString)
  {
    this.b = paramString;
  }
  
  public void setAscendingSort(String paramString)
  {
    this.jdField_void = paramString;
  }
  
  public void setDescendingSort(String paramString)
  {
    this.jdField_else = paramString;
  }
  
  public String toString()
  {
    int i = 0;
    int j = 0;
    if (((this.jdField_null == null) || (this.jdField_null.getSortedBy() == null)) && ((this.jdField_long == null) || (this.jdField_long.length() == 0))) {
      return this.b;
    }
    String str1 = null;
    if ((this.jdField_null != null) && (this.jdField_null.getSortedBy() != null)) {
      str1 = this.jdField_null.getSortedBy();
    } else {
      str1 = this.jdField_long;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(str1, ",");
    int k = 0;
    while (localStringTokenizer.hasMoreTokens())
    {
      String str2 = (String)localStringTokenizer.nextElement();
      if ((str2 != null) && (str2.trim().equalsIgnoreCase(this.jdField_char.trim()))) {
        j = 1;
      }
      if ((str2 != null) && (str2.trim().equalsIgnoreCase("REVERSE"))) {
        i = 1;
      } else {
        k++;
      }
    }
    if ((this.jdField_case) && (k > 1)) {
      return this.b;
    }
    if (j == 0) {
      return this.b;
    }
    if (i == 0) {
      return this.jdField_void;
    }
    return this.jdField_else;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public String getSuccessURL()
  {
    return this.successURL;
  }
  
  public void setOnlyShowSingleSortButton(String paramString)
  {
    this.jdField_case = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getSortImageText()
  {
    return toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.GetSortImage
 * JD-Core Version:    0.7.0.1
 */