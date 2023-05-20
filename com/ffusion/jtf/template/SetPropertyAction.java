package com.ffusion.jtf.template;

import com.ffusion.jtf.JTF;
import com.ffusion.util.Reflection;
import com.microstar.xml.HandlerBase;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class SetPropertyAction
  extends GetPropertyAction
{
  private static final String ak = "<jtf:setProperty> ";
  private static final String ai = "<jtf:setProperty> The name attribute is missing.";
  private static final String an = "<jtf:setProperty> The value attribute is missing.";
  private static final String ao = "name";
  private static final String aj = "property";
  private static final String am = "value";
  private static final String al = "set";
  protected TemplateAction value;
  
  public void process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.name.evaluate(paramHttpServlet, paramHttpServletRequest);
    if (this.property == null)
    {
      a(str, paramHttpServlet, paramHttpServletRequest);
    }
    else
    {
      Object localObject = getObject(str, paramHttpServlet, paramHttpServletRequest);
      if (localObject != null) {
        setPropertyValue(localObject, paramHttpServlet, paramHttpServletRequest);
      }
    }
  }
  
  public String evaluate(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest)
  {
    return null;
  }
  
  protected void setPropertyValue(Object paramObject, HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest)
  {
    String str1;
    Object localObject1;
    String str2;
    if ((this.method == null) || (!this.method.equals(paramObject)))
    {
      str1 = this.property.evaluate(paramHttpServlet, paramHttpServletRequest);
      this.method = Reflection.findSetMethod(paramObject, str1);
      if (this.method == null) {
        if ((paramObject instanceof Map))
        {
          localObject1 = (Map)paramObject;
          ((Map)localObject1).put(str1, this.value.evaluate(paramHttpServlet, paramHttpServletRequest));
        }
        else
        {
          localObject1 = this.name.evaluate(paramHttpServlet, paramHttpServletRequest);
          str2 = this.value.evaluate(paramHttpServlet, paramHttpServletRequest);
          if ((paramHttpServlet instanceof JTF)) {
            ((JTF)paramHttpServlet).log("ERROR <jtf:setProperty name=\"" + (String)localObject1 + "\" property=\"" + str1 + "\" value=\"" + str2 + "\"/>  No such method " + paramObject.getClass() + ".set" + str1);
          }
        }
      }
    }
    if (this.method != null)
    {
      str1 = this.value.evaluate(paramHttpServlet, paramHttpServletRequest);
      try
      {
        localObject1 = new Object[1];
        localObject1[0] = str1;
        this.method.invoke(paramObject, (Object[])localObject1);
      }
      catch (Throwable localThrowable)
      {
        if ((localThrowable instanceof InvocationTargetException))
        {
          localObject2 = (InvocationTargetException)localThrowable;
          str2 = ((InvocationTargetException)localObject2).getTargetException().toString();
        }
        else
        {
          str2 = localThrowable.toString();
        }
        Object localObject2 = this.name.evaluate(paramHttpServlet, paramHttpServletRequest);
        String str3 = this.property.evaluate(paramHttpServlet, paramHttpServletRequest);
        if ((paramHttpServlet instanceof JTF)) {
          ((JTF)paramHttpServlet).log("ERROR <jtf:setProperty name=\"" + (String)localObject2 + "\" property=\"" + str3 + "\" value=\"" + str1 + "\"/>  Unable to access method " + paramObject.getClass() + '.' + this.method.getName() + " " + str2);
        }
      }
    }
  }
  
  private void a(String paramString, HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest)
  {
    String str = PropertyValue.Evaluate(this.value.evaluate(paramHttpServlet, paramHttpServletRequest), paramHttpServlet, paramHttpServletRequest);
    Object localObject = paramHttpServletRequest.getAttribute(paramString);
    if (localObject != null)
    {
      paramHttpServletRequest.setAttribute(paramString, str);
    }
    else
    {
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      localObject = localHttpSession.getAttribute(paramString);
      if (localObject != null)
      {
        localHttpSession.setAttribute(paramString, str);
      }
      else
      {
        ServletContext localServletContext = paramHttpServlet.getServletContext();
        localObject = localServletContext.getAttribute(paramString);
        if (localObject != null) {
          localServletContext.setAttribute(paramString, str);
        } else {
          localHttpSession.setAttribute(paramString, str);
        }
      }
    }
  }
  
  protected int parse(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString, int paramInt)
    throws TemplateParseException
  {
    int i = parse(paramServletContext, paramTemplateCache, paramString, paramInt, new a(paramServletContext, paramTemplateCache));
    if (this.name == null) {
      throw new TemplateParseException(paramString, paramInt, "<jtf:setProperty> The name attribute is missing.");
    }
    if (this.value == null) {
      throw new TemplateParseException(paramString, paramInt, "<jtf:setProperty> The value attribute is missing.");
    }
    return i;
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
      if (paramString1.equals("name"))
      {
        SetPropertyAction.this.name = new TemplateAction();
        SetPropertyAction.this.name.parse(this.jdField_if, this.a, paramString2);
      }
      else if (paramString1.equals("property"))
      {
        SetPropertyAction.this.property = new TemplateAction();
        SetPropertyAction.this.property.parse(this.jdField_if, this.a, paramString2);
      }
      else if (paramString1.equals("value"))
      {
        SetPropertyAction.this.value = new TemplateAction();
        SetPropertyAction.this.value.parse(this.jdField_if, this.a, paramString2);
      }
      else
      {
        throw new Exception("<jtf:setProperty> " + paramString1 + " is an invalid attribute.");
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.template.SetPropertyAction
 * JD-Core Version:    0.7.0.1
 */