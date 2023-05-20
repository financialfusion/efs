package com.ffusion.jtf.template;

import com.microstar.xml.HandlerBase;
import com.microstar.xml.XmlParser;
import java.io.StringReader;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

abstract class PropertyAction
  implements Action
{
  public static String GET_PREFIX = "get";
  
  protected Object getObject(String paramString, HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest)
  {
    Object localObject = paramHttpServletRequest.getAttribute(paramString);
    if (localObject == null)
    {
      localObject = paramHttpServletRequest.getSession().getAttribute(paramString);
      if (localObject == null) {
        localObject = paramHttpServlet.getServletContext().getAttribute(paramString);
      }
    }
    return localObject;
  }
  
  protected int parse(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString, int paramInt, HandlerBase paramHandlerBase)
    throws TemplateParseException
  {
    int i = paramString.indexOf("/>", paramInt);
    if (i == -1) {
      throw new TemplateParseException(paramString, paramInt, "This is invalid XML.");
    }
    StringReader localStringReader = new StringReader(paramString.substring(paramInt, i + "/>".length()));
    XmlParser localXmlParser = new XmlParser();
    localXmlParser.setHandler(paramHandlerBase);
    try
    {
      localXmlParser.parse(null, null, localStringReader);
    }
    catch (Throwable localThrowable)
    {
      throw new TemplateParseException(paramString, paramInt, localThrowable.getMessage());
    }
    return i + "/>".length();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.template.PropertyAction
 * JD-Core Version:    0.7.0.1
 */