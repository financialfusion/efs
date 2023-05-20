package com.ffusion.jtf.template;

import com.ffusion.jtf.JTF;
import com.microstar.xml.HandlerBase;
import com.microstar.xml.XmlParser;
import java.io.IOException;
import java.io.StringReader;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class ForwardAction
  implements Action
{
  private static final String ac = "<jtf:forward> ";
  private static final String aa = "<jtf:forward> The page attribute is missing.";
  private static final String Z = "page";
  private TemplateAction ab;
  
  public void process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = PropertyValue.Evaluate(this.ab.evaluate(paramHttpServlet, paramHttpServletRequest), paramHttpServlet, paramHttpServletRequest);
    if ((paramHttpServlet instanceof JTF))
    {
      localObject = ((JTF)paramHttpServlet).getContextRoot();
      String str2 = ((JTF)paramHttpServlet).getServletPath();
      if (str1.startsWith((String)localObject + str2)) {
        str1 = str1.substring(((String)localObject).length());
      }
    }
    Object localObject = paramHttpServlet.getServletContext().getRequestDispatcher(str1);
    if (localObject == null) {
      paramHttpServlet.log("ERROR <jtf:forward page=\"" + str1 + "\"/> Unable to forward request.");
    } else {
      try
      {
        ((RequestDispatcher)localObject).forward(paramHttpServletRequest, paramHttpServletResponse);
      }
      catch (Throwable localThrowable)
      {
        paramHttpServlet.log("ERROR <jtf:forward page=\"" + str1 + "\"/> Failed to forward request. " + localThrowable.getMessage());
      }
    }
  }
  
  public String evaluate(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest)
  {
    return null;
  }
  
  protected int parse(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString, int paramInt)
    throws TemplateParseException
  {
    int i = paramString.indexOf("/>", paramInt);
    if (i == -1) {
      throw new TemplateParseException(paramString, paramInt, "<jtf:forward> This is invalid XML.");
    }
    StringReader localStringReader = new StringReader(paramString.substring(paramInt, i + 2));
    XmlParser localXmlParser = new XmlParser();
    a locala = new a(paramServletContext, paramTemplateCache);
    localXmlParser.setHandler(locala);
    try
    {
      localXmlParser.parse(null, null, localStringReader);
    }
    catch (TemplateParseException localTemplateParseException)
    {
      throw localTemplateParseException;
    }
    catch (Throwable localThrowable)
    {
      throw new TemplateParseException(paramString, paramInt, localThrowable.getMessage());
    }
    if (this.ab == null) {
      throw new TemplateParseException(paramString, paramInt, "<jtf:forward> The page attribute is missing.");
    }
    return i + 2;
  }
  
  class a
    extends HandlerBase
  {
    private ServletContext jdField_if;
    private TemplateCache a;
    
    a(ServletContext paramServletContext, TemplateCache paramTemplateCache)
    {
      this.jdField_if = paramServletContext;
      this.a = paramTemplateCache;
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
      throws Exception
    {
      if (paramString1.equals("page"))
      {
        ForwardAction.this.ab = new TemplateAction();
        ForwardAction.this.ab.parse(this.jdField_if, this.a, paramString2);
      }
      else
      {
        throw new Exception("<jtf:forward> " + paramString1 + " is an invalid attribute.");
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.template.ForwardAction
 * JD-Core Version:    0.7.0.1
 */