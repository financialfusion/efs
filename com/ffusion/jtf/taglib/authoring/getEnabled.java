package com.ffusion.jtf.taglib.authoring;

import java.io.PrintStream;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class getEnabled
  extends TagSupport
{
  public getEnabled()
  {
    release();
  }
  
  public void release() {}
  
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
        this.pageContext.getOut().println("true");
      } else {
        this.pageContext.getOut().println("false");
      }
    }
    catch (Exception localException)
    {
      System.out.println("Error: <author:getEnabled> " + localException);
    }
    return 6;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.authoring.getEnabled
 * JD-Core Version:    0.7.0.1
 */