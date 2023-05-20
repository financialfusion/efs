package com.ffusion.jtf.taglib.authoring;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class update
  extends BodyTagSupport
{
  private Collection jdField_int;
  private int jdField_if;
  private Iterator jdField_do;
  private String jdField_for = null;
  private String a = null;
  
  public update()
  {
    release();
  }
  
  public void release()
  {
    this.jdField_for = null;
    this.a = null;
  }
  
  public void setPage(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getPage()
  {
    return this.jdField_for;
  }
  
  public void setPageContents(String paramString)
  {
    this.a = paramString;
  }
  
  public String getPageContents()
  {
    return this.a;
  }
  
  public int doStartTag()
    throws JspException
  {
    System.out.println("doStartTag");
    this.pageContext.setAttribute("status", "failed");
    this.pageContext.setAttribute("statusMessage", "");
    a();
    return 2;
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
        throw new JspTagException("Error: <author:list ... in doAfterBody\r\n" + localIOException);
      }
    }
    return 0;
  }
  
  private void a()
  {
    try
    {
      HttpSession localHttpSession = this.pageContext.getSession();
      ArrayList localArrayList = (ArrayList)localHttpSession.getAttribute("jtf_authorInfo");
      if ((localArrayList != null) && (this.jdField_for != null) && (this.a != null))
      {
        this.a = this.pageContext.getRequest().getParameter(this.a);
        this.jdField_for = this.pageContext.getRequest().getParameter(this.jdField_for);
        String str = this.pageContext.getServletContext().getRealPath("/" + this.jdField_for);
        if (str != null)
        {
          BufferedWriter localBufferedWriter = new BufferedWriter(new FileWriter(str));
          if (localBufferedWriter != null)
          {
            localBufferedWriter.write(this.a);
            localBufferedWriter.close();
            this.pageContext.setAttribute("status", "success");
            this.pageContext.setAttribute("statusMessage", "Page updated successfully.");
          }
        }
      }
    }
    catch (Exception localException)
    {
      System.out.println("Error: <author:update> " + localException);
      this.pageContext.setAttribute("statusMessage", localException.toString());
    }
  }
  
  public int doEndTag()
    throws JspTagException
  {
    return 6;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.authoring.update
 * JD-Core Version:    0.7.0.1
 */