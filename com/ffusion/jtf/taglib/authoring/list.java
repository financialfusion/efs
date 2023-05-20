package com.ffusion.jtf.taglib.authoring;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class list
  extends BodyTagSupport
{
  private Collection jdField_for;
  private int jdField_if;
  private String[] a;
  private Iterator jdField_do;
  
  public list()
  {
    release();
  }
  
  public void release()
  {
    this.a = null;
  }
  
  public String getItems()
  {
    return "";
  }
  
  public void setItems(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ", ");
    int i = localStringTokenizer.countTokens();
    this.a = new String[i];
    for (int j = 0; j < i; j++) {
      this.a[j] = localStringTokenizer.nextToken();
    }
  }
  
  public int doStartTag()
    throws JspException
  {
    if (jdField_if())
    {
      a();
      return 2;
    }
    return 0;
  }
  
  public int doAfterBody()
    throws JspTagException
  {
    BodyContent localBodyContent = getBodyContent();
    if (localBodyContent != null) {
      try
      {
        String str = localBodyContent.getString();
        localBodyContent.getEnclosingWriter().write(str);
        localBodyContent.clear();
      }
      catch (IOException localIOException)
      {
        throw new JspTagException("Error parsing <JSP:list ... in doAfterBody\r\n" + localIOException);
      }
    }
    if (this.jdField_do.hasNext())
    {
      a();
      return 2;
    }
    return 0;
  }
  
  public int doEndTag()
    throws JspTagException
  {
    return 6;
  }
  
  private void a()
  {
    ServletRequest localServletRequest = this.pageContext.getRequest();
    try
    {
      for (int i = 0; i < this.a.length; i++)
      {
        if (this.jdField_do.hasNext())
        {
          String str = (String)this.jdField_do.next();
          this.pageContext.setAttribute(this.a[i], str);
        }
        else
        {
          this.pageContext.setAttribute(this.a[i], "");
        }
        this.jdField_if += 1;
      }
    }
    catch (Exception localException)
    {
      System.out.println("Error: <author:list> " + localException);
    }
  }
  
  private boolean jdField_if()
    throws JspTagException
  {
    boolean bool = false;
    try
    {
      ArrayList localArrayList = (ArrayList)this.pageContext.findAttribute("jtf_authorInfo");
      if (localArrayList != null) {
        this.jdField_for = localArrayList;
      }
      if (this.jdField_for != null)
      {
        this.jdField_do = this.jdField_for.iterator();
        bool = true;
      }
    }
    catch (Exception localException)
    {
      System.out.println("Error: <author:list> " + localException);
    }
    this.jdField_if = 0;
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.authoring.list
 * JD-Core Version:    0.7.0.1
 */