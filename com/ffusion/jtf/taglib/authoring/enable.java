package com.ffusion.jtf.taglib.authoring;

import java.io.PrintStream;
import java.util.ArrayList;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class enable
  extends TagSupport
{
  private String jdField_if = null;
  private String a = null;
  
  public enable()
  {
    release();
  }
  
  public void release()
  {
    this.jdField_if = null;
    this.a = null;
  }
  
  public void setEquals(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getEquals()
  {
    return this.jdField_if;
  }
  
  public void setRequestVar(String paramString)
  {
    this.a = paramString;
  }
  
  public String getRequestVar()
  {
    return this.a;
  }
  
  public int doStartTag()
    throws JspException
  {
    return 0;
  }
  
  public int doEndTag()
    throws JspException
  {
    try
    {
      HttpSession localHttpSession = this.pageContext.getSession();
      ArrayList localArrayList = (ArrayList)localHttpSession.getAttribute("jtf_authorInfo");
      if (localArrayList == null)
      {
        int i = 0;
        if (this.a != null)
        {
          String str = this.pageContext.getRequest().getParameter(this.a);
          if (str != null) {
            if (this.jdField_if != null)
            {
              if (str.equalsIgnoreCase(this.jdField_if)) {
                i = 1;
              }
            }
            else {
              i = 1;
            }
          }
        }
        else
        {
          i = 1;
        }
        if (i != 0)
        {
          localArrayList = new ArrayList();
          localHttpSession.setAttribute("jtf_authorInfo", localArrayList);
        }
      }
    }
    catch (Exception localException)
    {
      System.out.println("Error: <author:enable> " + localException);
    }
    return 6;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.authoring.enable
 * JD-Core Version:    0.7.0.1
 */