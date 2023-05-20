package com.ffusion.jtf.template;

import com.ffusion.util.logging.DebugLog;
import com.microstar.xml.HandlerBase;
import com.microstar.xml.XmlParser;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class IncludeAction
  extends TemplateAction
  implements Operators
{
  private static final String T = "<jtf:include> ";
  private static final String C = "</jtf:include>";
  private static final int D = 14;
  private static final String Q = " is an invalid operator.";
  private static final String U = "true";
  private static final int Y = 1;
  private static final String N = "'";
  private static final String G = "\"";
  private static final String S = "page";
  private static final String E = "servlet";
  private static final String L = "value1";
  private static final String K = "value2";
  private static final String O = "operator";
  private static final int X = 0;
  private static final int J = 1;
  private static final int H = 2;
  private static final int R = 4;
  private String I;
  private HashMap F;
  private TemplateAction P;
  private TemplateAction M;
  private int W = 0;
  private int V = 0;
  
  public void process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    if (this.P != null) {
      jdMethod_do(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
    } else {
      a(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
    }
  }
  
  private void a(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    if (this.actions != null) {
      jdMethod_for(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
    } else if (this.I != null) {
      jdMethod_if(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
    }
  }
  
  private void jdMethod_do(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.P.evaluate(paramHttpServlet, paramHttpServletRequest);
    if (str1 != null)
    {
      String str2;
      if (this.M != null) {
        str2 = this.M.evaluate(paramHttpServlet, paramHttpServletRequest);
      } else {
        str2 = "true";
      }
      switch (this.W)
      {
      case 0: 
        if (str1.equals(str2)) {
          a(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
        }
        break;
      case 1: 
        if (!str1.equals(str2)) {
          a(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
        }
        break;
      }
    }
  }
  
  private void jdMethod_for(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    if ((this.V & 0x1) != 0)
    {
      String str1 = evaluate(paramHttpServlet, paramHttpServletRequest);
      String str2 = PropertyValue.Evaluate(evaluate(paramHttpServlet, paramHttpServletRequest), paramHttpServlet, paramHttpServletRequest);
      jdMethod_if(" page=" + str2);
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      TemplateCache localTemplateCache = (TemplateCache)paramHttpServletRequest.getAttribute("TEMPLATE_CACHE");
      if (localTemplateCache != null)
      {
        TemplateAction localTemplateAction = null;
        try
        {
          localTemplateAction = localTemplateCache.getTemplate(paramHttpServlet.getServletContext(), str2);
          if (localTemplateAction != null) {
            localTemplateAction.process(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
          }
        }
        catch (TemplateParseException localTemplateParseException)
        {
          localTemplateParseException.log(paramHttpServlet, str2);
        }
      }
    }
    if ((this.V & 0x4) != 0) {
      super.process(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
    }
  }
  
  private void jdMethod_if(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    RequestDispatcher localRequestDispatcher = paramHttpServlet.getServletContext().getRequestDispatcher(this.I);
    if (localRequestDispatcher != null)
    {
      if (this.F != null)
      {
        Iterator localIterator1 = this.F.keySet().iterator();
        Iterator localIterator2 = this.F.values().iterator();
        while ((localIterator1.hasNext()) && (localIterator2.hasNext()))
        {
          String str1 = (String)localIterator1.next();
          String str2 = (String)localIterator2.next();
          paramHttpServletRequest.setAttribute(str1, str2);
        }
      }
      try
      {
        localRequestDispatcher.include(paramHttpServletRequest, paramHttpServletResponse);
      }
      catch (Throwable localThrowable)
      {
        paramHttpServlet.log("ERROR <jtf:include servlet=\"" + this.I + "\"/>", localThrowable);
      }
    }
  }
  
  protected void parseTagOptions(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString)
    throws TemplateParseException, Throwable
  {
    StringReader localStringReader = new StringReader(paramString);
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
  }
  
  protected int parseInclude(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString, int paramInt)
    throws TemplateParseException
  {
    int i = paramString.indexOf("/>", paramInt) + 2;
    if (i != -1) {
      try
      {
        parseTagOptions(paramServletContext, paramTemplateCache, paramString.substring(paramInt, i));
      }
      catch (TemplateParseException localTemplateParseException)
      {
        throw localTemplateParseException;
      }
      catch (Throwable localThrowable)
      {
        throw new TemplateParseException(paramString, paramInt, localThrowable.getMessage());
      }
    } else {
      throw new TemplateParseException(paramString, paramInt, "<jtf:include> This is invalid XML.");
    }
    return i;
  }
  
  protected int parseInlineInclude(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString, int paramInt)
    throws TemplateParseException
  {
    int i = findTagEnd(paramString, paramInt) - 1;
    int j = i + 1;
    try
    {
      parseTagOptions(paramServletContext, paramTemplateCache, paramString.substring(paramInt, i) + "/>");
    }
    catch (TemplateParseException localTemplateParseException)
    {
      throw localTemplateParseException;
    }
    catch (Throwable localThrowable)
    {
      throw new TemplateParseException(paramString, paramInt, localThrowable.getMessage());
    }
    if (paramString.indexOf("</jtf:include>", j) != -1)
    {
      paramInt = parse(paramServletContext, paramTemplateCache, paramString, j, "include");
      this.V |= 0x4;
    }
    else
    {
      throw new TemplateParseException(paramString, paramInt, "<jtf:include> This is invalid XML.");
    }
    return paramInt;
  }
  
  protected int parse(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString, int paramInt)
    throws TemplateParseException
  {
    int i = paramInt;
    int j = findTagEnd(paramString, paramInt) - 1;
    int k = paramString.indexOf("/>", paramInt);
    if (((k != -1) && (j < k)) || (k == -1)) {
      i = parseInlineInclude(paramServletContext, paramTemplateCache, paramString, paramInt);
    } else {
      i = parseInclude(paramServletContext, paramTemplateCache, paramString, paramInt);
    }
    return i;
  }
  
  private void jdMethod_do(String paramString)
  {
    int i = paramString.indexOf('?');
    if (i == -1)
    {
      this.I = paramString;
    }
    else
    {
      this.I = paramString.substring(0, i);
      paramString = paramString.substring(i + 1);
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=&");
      while (localStringTokenizer.hasMoreTokens())
      {
        String str1 = localStringTokenizer.nextToken();
        if (localStringTokenizer.hasMoreTokens())
        {
          String str2 = localStringTokenizer.nextToken();
          if (this.F == null) {
            this.F = new HashMap();
          }
          this.F.put(str1, str2);
        }
      }
    }
  }
  
  protected int findTagEnd(String paramString, int paramInt)
    throws TemplateParseException
  {
    int i = paramInt;
    int j = i;
    int k = 0;
    while (k == 0)
    {
      int m = paramString.indexOf('>', j);
      if (m != -1) {
        do
        {
          int n = paramString.indexOf("'", j);
          int i1 = paramString.indexOf("\"", j);
          int i2 = -1;
          String str = null;
          if (((i1 < n) && (i1 != -1)) || ((i1 != -1) && (n == -1)))
          {
            i2 = i1;
            str = "\"";
          }
          else if (((n < i1) && (n != -1)) || ((n != -1) && (i1 == -1)))
          {
            i2 = n;
            str = "'";
          }
          if ((i2 == -1) || (m < i2))
          {
            i = m + 1;
            k = 1;
          }
          if (str != null) {
            j = paramString.indexOf(str, i2 + 1);
          }
          if ((j == -1) && (k == 0)) {
            throw new TemplateParseException(paramString, paramInt, "<jtf:include> This is invalid XML.");
          }
          j++;
          if (m < j) {
            break;
          }
        } while (k == 0);
      } else {
        throw new TemplateParseException(paramString, paramInt, "<jtf:include> This is invalid XML.");
      }
    }
    return i;
  }
  
  private void jdMethod_if(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer("\tjtf:include ");
    if (paramString != null) {
      localStringBuffer.append(paramString);
    }
    DebugLog.log(Level.FINEST, localStringBuffer.toString());
  }
  
  private void a(Throwable paramThrowable)
  {
    StringBuffer localStringBuffer = new StringBuffer("\tjtf:include ");
    localStringBuffer.append("\n\tException: ");
    DebugLog.throwing(localStringBuffer.toString(), paramThrowable);
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
        IncludeAction.this.parse(this.jdField_if, this.a, paramString2);
        IncludeAction.access$076(IncludeAction.this, 1);
      }
      else if (paramString1.equals("servlet"))
      {
        IncludeAction.this.jdMethod_do(paramString2);
        IncludeAction.access$076(IncludeAction.this, 2);
      }
      else if (paramString1.equals("value1"))
      {
        IncludeAction.this.P = new TemplateAction();
        IncludeAction.this.P.parse(this.jdField_if, this.a, paramString2);
      }
      else if (paramString1.equals("value2"))
      {
        IncludeAction.this.M = new TemplateAction();
        IncludeAction.this.M.parse(this.jdField_if, this.a, paramString2);
      }
      else if (paramString1.equals("operator"))
      {
        if (paramString2.equals("equals")) {
          IncludeAction.this.W = 0;
        } else if (paramString2.equals("notEquals")) {
          IncludeAction.this.W = 1;
        } else {
          throw new Exception("<jtf:include>  is an invalid operator.");
        }
      }
      else
      {
        throw new Exception("<jtf:include> " + paramString1 + " is an invalid attribute.");
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.template.IncludeAction
 * JD-Core Version:    0.7.0.1
 */