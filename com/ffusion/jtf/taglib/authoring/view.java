package com.ffusion.jtf.taglib.authoring;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class view
  extends TagSupport
{
  private String jdField_do = null;
  private String jdField_for = null;
  private String jdField_if = null;
  private String a = null;
  
  public view()
  {
    release();
  }
  
  public void release()
  {
    this.jdField_do = null;
    this.jdField_for = null;
    this.jdField_if = null;
    this.a = null;
  }
  
  public void setUrl(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getUrl()
  {
    return this.jdField_do;
  }
  
  public void setRealPath(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getRealPath()
  {
    return this.jdField_for;
  }
  
  public void setPageContents(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getPageContents()
  {
    return this.jdField_if;
  }
  
  public void setLineCount(String paramString)
  {
    this.a = paramString;
  }
  
  public String getLineCount()
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
      if (localArrayList != null)
      {
        String str;
        if (this.jdField_do != null)
        {
          str = this.pageContext.getRequest().getParameter(this.jdField_do);
          this.pageContext.getOut().println(str);
        }
        else
        {
          BufferedReader localBufferedReader;
          if (this.jdField_for != null)
          {
            str = this.pageContext.getRequest().getParameter(this.jdField_for);
            localBufferedReader = null;
            str = this.pageContext.getServletContext().getRealPath("/" + str);
            if (str != null) {
              this.pageContext.getOut().println(str);
            }
          }
          else if (this.jdField_if != null)
          {
            str = this.pageContext.getRequest().getParameter(this.jdField_if);
            this.jdField_for = this.pageContext.getServletContext().getRealPath("/" + str);
            if (this.jdField_for != null)
            {
              localBufferedReader = new BufferedReader(new FileReader(this.jdField_for));
              while ((str = localBufferedReader.readLine()) != null) {
                this.pageContext.getOut().println(str);
              }
              localBufferedReader.close();
            }
          }
          else if (this.a != null)
          {
            str = this.pageContext.getRequest().getParameter(this.a);
            this.jdField_for = this.pageContext.getServletContext().getRealPath("/" + str);
            if (this.jdField_for != null)
            {
              localBufferedReader = new BufferedReader(new FileReader(this.jdField_for));
              for (int i = 2; (str = localBufferedReader.readLine()) != null; i++) {}
              localBufferedReader.close();
              System.out.println("lineCnt=" + i);
              this.pageContext.getOut().println(i);
            }
          }
        }
      }
    }
    catch (Exception localException)
    {
      System.out.println("Error: author\r\n" + localException);
    }
    return 6;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.authoring.view
 * JD-Core Version:    0.7.0.1
 */