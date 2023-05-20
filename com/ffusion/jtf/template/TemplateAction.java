package com.ffusion.jtf.template;

import com.ffusion.util.Strings;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TemplateAction
  implements Action
{
  public static final String CONTENT_TYPE = "JTF_CONTENT_TYPE";
  public static final String HEADER_NAMES = "HEADER_NAMES";
  public static final String HEADER_VALUES = "HEADER_VALUES";
  public static final String TEMPLATE_CACHE = "TEMPLATE_CACHE";
  static final String jdField_try = " is an invalid attribute.";
  static final String jdField_null = " end tag was not found.";
  static final String c = "This is invalid XML.";
  static final String jdField_if = "<jtf:forward> cannot be used on a template that has output.";
  static final char jdField_long = '>';
  static final String jdField_byte = "/>";
  static final int jdField_do = 2;
  static final String b = "jtf:";
  static final int jdField_int = 4;
  static final String jdField_char = "comment";
  static final String jdField_case = "forward";
  static final String jdField_void = "getProperty";
  static final String e = "include";
  static final String d = "list";
  static final String jdField_new = "noCache";
  static final String jdField_for = "object";
  static final String jdField_goto = "servlet";
  static final String f = "setProperty";
  static final String jdField_else = "</jtf:comment>";
  protected ArrayList actions;
  protected boolean hasHtmlAction = false;
  protected boolean hasForwardAction = false;
  
  public void startProcess(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    process(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
    if (paramHttpServletRequest.getAttribute("JTF_CONTENT_TYPE") != null) {
      paramHttpServletResponse.getWriter().flush();
    }
  }
  
  public void process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    if (this.actions != null)
    {
      Iterator localIterator = this.actions.iterator();
      while (localIterator.hasNext())
      {
        Action localAction = (Action)localIterator.next();
        localAction.process(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
      }
    }
  }
  
  public String evaluate(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest)
  {
    String str1 = null;
    if (this.actions != null) {
      if (this.actions.size() == 0)
      {
        str1 = "";
      }
      else if (this.actions.size() == 1)
      {
        str1 = ((Action)this.actions.get(0)).evaluate(paramHttpServlet, paramHttpServletRequest);
        if (str1 == null) {
          str1 = "";
        }
      }
      else
      {
        Iterator localIterator = this.actions.iterator();
        StringBuffer localStringBuffer = new StringBuffer();
        while (localIterator.hasNext())
        {
          Action localAction = (Action)localIterator.next();
          String str2 = localAction.evaluate(paramHttpServlet, paramHttpServletRequest);
          if (str2 != null) {
            localStringBuffer.append(str2);
          }
        }
        str1 = localStringBuffer.toString();
      }
    }
    return str1;
  }
  
  protected boolean isCacheable()
  {
    boolean bool = true;
    if (this.actions.size() > 0)
    {
      Action localAction = (Action)this.actions.get(0);
      if ((localAction instanceof NoCacheAction)) {
        bool = false;
      }
    }
    return bool;
  }
  
  protected void parse(ServletContext paramServletContext, TemplateCache paramTemplateCache, InputStream paramInputStream)
    throws IOException, TemplateParseException
  {
    String str = Strings.streamToString(paramInputStream);
    parse(paramServletContext, paramTemplateCache, str);
  }
  
  protected void parse(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString)
    throws TemplateParseException
  {
    parse(paramServletContext, paramTemplateCache, paramString, 0, null);
  }
  
  protected int parse(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString1, int paramInt, String paramString2)
    throws TemplateParseException
  {
    this.actions = new ArrayList();
    int i = 1;
    int j = paramInt;
    for (int k = paramString1.indexOf("jtf:", paramInt); k != -1; k = paramString1.indexOf("jtf:", k))
    {
      i = 1;
      String str = jdField_if(paramString1, k + 4);
      if ((paramString2 != null) && (paramString1.charAt(k - 1) == '/') && (str.equals(paramString2))) {
        break;
      }
      if (str.equals("getProperty"))
      {
        k = addGetPropertyAction(paramServletContext, paramTemplateCache, paramString1, k - 1, j);
      }
      else if (str.equals("setProperty"))
      {
        k = addSetPropertyAction(paramServletContext, paramTemplateCache, paramString1, k - 1, j);
      }
      else if (str.equals("object"))
      {
        k = addObjectAction(paramServletContext, paramTemplateCache, paramString1, k - 1, j);
      }
      else if (str.equals("include"))
      {
        k = addIncludeAction(paramServletContext, paramTemplateCache, paramString1, k - 1, j);
      }
      else if (str.equals("list"))
      {
        k = addListAction(paramServletContext, paramTemplateCache, paramString1, k - 1, j);
      }
      else if (str.equals("forward"))
      {
        k = addForwardAction(paramServletContext, paramTemplateCache, paramString1, k - 1, j);
      }
      else if (str.equals("comment"))
      {
        k = addCommentAction(paramString1, k - 1, j);
      }
      else if (str.equals("noCache"))
      {
        k = addNoCacheAction(paramString1, k - 1, j);
      }
      else
      {
        k++;
        i = 0;
      }
      if (i != 0) {
        j = k;
      }
    }
    k = a(paramString1, k, j, paramString2);
    return k;
  }
  
  protected int addGetPropertyAction(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString, int paramInt1, int paramInt2)
    throws TemplateParseException
  {
    if (paramInt2 < paramInt1) {
      addHtmlAction(paramString, paramInt2, paramInt1);
    }
    GetPropertyAction localGetPropertyAction = new GetPropertyAction();
    paramInt1 = localGetPropertyAction.parse(paramServletContext, paramTemplateCache, paramString, paramInt1);
    this.actions.add(localGetPropertyAction);
    return paramInt1;
  }
  
  protected int addSetPropertyAction(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString, int paramInt1, int paramInt2)
    throws TemplateParseException
  {
    if (paramInt2 < paramInt1) {
      addHtmlAction(paramString, paramInt2, paramInt1);
    }
    SetPropertyAction localSetPropertyAction = new SetPropertyAction();
    paramInt1 = localSetPropertyAction.parse(paramServletContext, paramTemplateCache, paramString, paramInt1);
    this.actions.add(localSetPropertyAction);
    return paramInt1;
  }
  
  protected int addListAction(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString, int paramInt1, int paramInt2)
    throws TemplateParseException
  {
    if (paramInt2 < paramInt1) {
      addHtmlAction(paramString, paramInt2, paramInt1);
    }
    ListAction localListAction = new ListAction();
    paramInt1 = localListAction.parse(paramServletContext, paramTemplateCache, paramString, paramInt1);
    this.actions.add(localListAction);
    return paramInt1;
  }
  
  protected int addObjectAction(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString, int paramInt1, int paramInt2)
    throws TemplateParseException
  {
    if (paramInt2 < paramInt1) {
      addHtmlAction(paramString, paramInt2, paramInt1);
    }
    ObjectAction localObjectAction = new ObjectAction();
    paramInt1 = localObjectAction.parse(paramServletContext, paramTemplateCache, paramString, paramInt1);
    this.actions.add(localObjectAction);
    return paramInt1;
  }
  
  protected int addIncludeAction(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString, int paramInt1, int paramInt2)
    throws TemplateParseException
  {
    if (paramInt2 < paramInt1) {
      addHtmlAction(paramString, paramInt2, paramInt1);
    }
    IncludeAction localIncludeAction = new IncludeAction();
    paramInt1 = localIncludeAction.parse(paramServletContext, paramTemplateCache, paramString, paramInt1);
    this.actions.add(localIncludeAction);
    return paramInt1;
  }
  
  protected int addForwardAction(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString, int paramInt1, int paramInt2)
    throws TemplateParseException
  {
    boolean bool = false;
    if (paramInt2 < paramInt1) {
      bool = addHtmlAction(paramString, paramInt2, paramInt1);
    }
    if ((bool) && (this.hasHtmlAction)) {
      throw new TemplateParseException(paramString, paramInt1, "<jtf:forward> cannot be used on a template that has output.");
    }
    this.hasForwardAction = true;
    ForwardAction localForwardAction = new ForwardAction();
    paramInt1 = localForwardAction.parse(paramServletContext, paramTemplateCache, paramString, paramInt1);
    this.actions.add(localForwardAction);
    return paramInt1;
  }
  
  protected int addCommentAction(String paramString, int paramInt1, int paramInt2)
    throws TemplateParseException
  {
    if (paramInt2 < paramInt1) {
      addHtmlAction(paramString, paramInt2, paramInt1);
    }
    paramInt1 = a(paramString, paramInt1 + 1);
    return paramInt1;
  }
  
  protected int addNoCacheAction(String paramString, int paramInt1, int paramInt2)
    throws TemplateParseException
  {
    if (paramInt2 < paramInt1) {
      addHtmlAction(paramString, paramInt2, paramInt1);
    }
    NoCacheAction localNoCacheAction = new NoCacheAction();
    paramInt1 = localNoCacheAction.parse(paramString, paramInt1);
    this.actions.add(localNoCacheAction);
    return paramInt1;
  }
  
  protected boolean addHtmlAction(String paramString, int paramInt1, int paramInt2)
    throws TemplateParseException
  {
    for (int i = paramInt1; (i < paramInt2) && (Character.isWhitespace(paramString.charAt(i))); i++) {}
    if (i < paramInt2)
    {
      if (this.hasForwardAction) {
        throw new TemplateParseException(paramString, paramInt1, "<jtf:forward> cannot be used on a template that has output.");
      }
      this.hasHtmlAction = true;
      HtmlAction localHtmlAction = new HtmlAction();
      this.actions.add(localHtmlAction);
      localHtmlAction.parse(paramString, paramInt1, paramInt2);
      return true;
    }
    return false;
  }
  
  private int a(String paramString1, int paramInt1, int paramInt2, String paramString2)
    throws TemplateParseException
  {
    if (paramString2 == null)
    {
      int i = paramString1.length();
      if (paramInt2 < i) {
        addHtmlAction(paramString1, paramInt2, i);
      }
    }
    else
    {
      if (paramInt1 == -1) {
        throw new TemplateParseException(paramString1, paramInt2, paramString2 + " end tag was not found.");
      }
      addHtmlAction(paramString1, paramInt2, paramInt1 - 2);
      paramInt1 = paramInt1 + 4 + paramString2.length() + 1;
    }
    return paramInt1;
  }
  
  private int a(String paramString, int paramInt)
    throws TemplateParseException
  {
    int i = paramString.indexOf('\r', paramInt);
    int j = paramString.indexOf("/>", paramInt);
    if ((j != -1) && (j < i))
    {
      paramInt = j + 2;
    }
    else
    {
      int k = paramString.indexOf("</jtf:comment>", paramInt);
      if (k == -1) {
        throw new TemplateParseException(paramString, paramInt, "</jtf:comment> end tag was not found.");
      }
      paramInt = k + "</jtf:comment>".length();
    }
    return paramInt;
  }
  
  private String jdField_if(String paramString, int paramInt)
  {
    int i = paramString.length();
    int j = 0;
    int k = paramInt;
    int m = paramString.charAt(paramInt);
    while ((j == 0) && (paramInt < i)) {
      switch (m)
      {
      case 32: 
      case 47: 
      case 62: 
        j = 1;
        break;
      default: 
        paramInt++;
        m = paramString.charAt(paramInt);
      }
    }
    return paramString.substring(k, paramInt);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.template.TemplateAction
 * JD-Core Version:    0.7.0.1
 */