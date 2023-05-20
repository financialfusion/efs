package com.ffusion.jtf.template;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class NoCacheAction
  implements Action
{
  private static final String a = "<jtf:noCache> ";
  
  public void process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {}
  
  public String evaluate(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest)
  {
    return null;
  }
  
  protected int parse(String paramString, int paramInt)
    throws TemplateParseException
  {
    int i = paramString.indexOf("/>", paramInt);
    if (i == -1) {
      throw new TemplateParseException(paramString, paramInt, "<jtf:noCache> This is invalid XML.");
    }
    return i + 2;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.template.NoCacheAction
 * JD-Core Version:    0.7.0.1
 */