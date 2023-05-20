package com.ffusion.jtf.template;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class ObjectAction
  extends TemplateAction
  implements ObjectScopes
{
  private static final String o = "<jtf:object> ";
  private static final String j = "<jtf:object> The id attribute is missing.";
  private static final String n = "<jtf:object> The class attribute is missing.";
  private static final String m = "<jtf:object> Cannot embed object tags within object tags.";
  private static final String q = " is an invalid scope.";
  static final String i = "id=";
  static final String l = "class=";
  static final String g = "scope=";
  private String h;
  private String k;
  private int p = 1;
  
  public void process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    Object localObject = a(paramHttpServlet, paramHttpServletRequest);
    if (localObject != null) {
      super.process(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
    }
  }
  
  public String evaluate(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest)
  {
    return null;
  }
  
  private Object a(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest)
  {
    Object localObject = null;
    try
    {
      Class localClass = Class.forName(this.k);
      localObject = localClass.newInstance();
      switch (this.p)
      {
      case 0: 
        paramHttpServletRequest.setAttribute(this.h, localObject);
        break;
      case 3: 
        paramHttpServletRequest.getSession().setAttribute(this.h + "_ONCE", "");
      case 1: 
        paramHttpServletRequest.getSession().setAttribute(this.h, localObject);
        break;
      case 2: 
        paramHttpServlet.getServletContext().setAttribute(this.h, localObject);
      }
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      a(paramHttpServlet, "Class not found: " + this.k);
    }
    catch (Throwable localThrowable)
    {
      a(paramHttpServlet, "Unable to create object: " + localThrowable.getMessage());
    }
    return localObject;
  }
  
  private void a(HttpServlet paramHttpServlet, String paramString)
  {
    String str = null;
    switch (this.p)
    {
    case 0: 
      str = "request";
      break;
    case 1: 
      str = "session";
      break;
    case 3: 
      str = "process";
      break;
    case 2: 
    default: 
      str = "application";
    }
    paramHttpServlet.log("ERROR <jtf:object class=\"" + this.k + "\" id=\"" + this.h + "\" scope=\"" + str + "\"/> " + paramString);
  }
  
  protected int parse(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString, int paramInt)
    throws TemplateParseException
  {
    int i1 = paramString.indexOf('>', paramInt);
    if (i1 == -1) {
      throw new TemplateParseException(paramString, paramInt, "<jtf:object> This is invalid XML.");
    }
    this.h = jdMethod_if(paramString, paramInt, i1, "id=");
    if (this.h == null) {
      throw new TemplateParseException(paramString, paramInt, "<jtf:object> The id attribute is missing.");
    }
    this.k = jdMethod_if(paramString, paramInt, i1, "class=");
    if (this.k == null) {
      throw new TemplateParseException(paramString, paramInt, "<jtf:object> The class attribute is missing.");
    }
    String str = jdMethod_if(paramString, paramInt, i1, "scope=");
    if ((str != null) && (!a(str))) {
      throw new TemplateParseException(paramString, paramInt, "<jtf:object> " + str + " is an invalid scope.");
    }
    if (paramString.charAt(i1 - 1) == '/') {
      paramInt = i1 + 1;
    } else {
      paramInt = super.parse(paramServletContext, paramTemplateCache, paramString, i1 + 1, "object");
    }
    return paramInt;
  }
  
  protected int addObjectAction(TemplateCache paramTemplateCache, String paramString, int paramInt1, int paramInt2)
    throws TemplateParseException
  {
    throw new TemplateParseException(paramString, paramInt1, "<jtf:object> Cannot embed object tags within object tags.");
  }
  
  private String jdMethod_if(String paramString1, int paramInt1, int paramInt2, String paramString2)
    throws TemplateParseException
  {
    String str = null;
    int i1 = paramString1.indexOf(paramString2, paramInt1);
    if ((i1 != -1) && (i1 < paramInt2))
    {
      i1 += paramString2.length() + 1;
      int i2 = paramString1.indexOf('"', i1 + 1);
      if ((i2 == -1) || (i2 > paramInt2)) {
        throw new TemplateParseException(paramString1, i1, "<jtf:object> This is invalid XML.");
      }
      str = paramString1.substring(i1, i2);
    }
    return str;
  }
  
  private boolean a(String paramString)
  {
    boolean bool = true;
    if (paramString.equals("session")) {
      this.p = 1;
    } else if (paramString.equals("process")) {
      this.p = 3;
    } else if (paramString.equals("application")) {
      this.p = 2;
    } else if (paramString.equals("request")) {
      this.p = 0;
    } else {
      bool = false;
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.template.ObjectAction
 * JD-Core Version:    0.7.0.1
 */