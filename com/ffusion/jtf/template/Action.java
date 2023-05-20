package com.ffusion.jtf.template;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

abstract interface Action
  extends Serializable
{
  public abstract void process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException;
  
  public abstract String evaluate(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.template.Action
 * JD-Core Version:    0.7.0.1
 */