package com.ffusion.jtf.taglib.authoring;

import java.io.PrintStream;
import java.util.ArrayList;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class clear
  extends TagSupport
{
  private String jdField_if = null;
  private String a = null;
  
  public clear()
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
      if (localArrayList != null) {
        if (this.a != null)
        {
          String str = this.pageContext.getRequest().getParameter(this.a);
          if (str != null) {
            if (this.jdField_if != null)
            {
              if (str.equalsIgnoreCase(this.jdField_if)) {
                localArrayList.clear();
              }
            }
            else {
              localArrayList.clear();
            }
          }
        }
        else
        {
          localArrayList.clear();
        }
      }
    }
    catch (Exception localException)
    {
      System.out.println("Error: <author:clear> " + localException);
    }
    return 6;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.authoring.clear
 * JD-Core Version:    0.7.0.1
 */