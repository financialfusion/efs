package com.ffusion.jtf.template;

import com.ffusion.jtf.JTF;
import com.ffusion.util.Reflection;
import com.microstar.xml.HandlerBase;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class GetPropertyAction
  extends PropertyAction
{
  private static final String ah = "<jtf:getProperty> ";
  private static final String ad = "<jtf:getProperty> The name attribute is missing.";
  private static final String ag = "name";
  private static final String ae = "property";
  private static final String af = "get";
  protected TemplateAction name;
  protected TemplateAction property;
  protected transient Method method;
  
  public void process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = PropertyValue.Evaluate(evaluate(paramHttpServlet, paramHttpServletRequest), paramHttpServlet, paramHttpServletRequest);
    if (str != null)
    {
      PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
      localPrintWriter.print(str);
    }
  }
  
  public String evaluate(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest)
  {
    String str1 = null;
    String str2 = this.name.evaluate(paramHttpServlet, paramHttpServletRequest);
    Object localObject1 = getObject(str2, paramHttpServlet, paramHttpServletRequest);
    if (localObject1 != null) {
      if (this.property == null)
      {
        str1 = localObject1.toString();
      }
      else
      {
        Object localObject2;
        Object localObject3;
        Object localObject4;
        if ((this.method == null) || (!this.method.equals(localObject1)))
        {
          localObject2 = this.property.evaluate(paramHttpServlet, paramHttpServletRequest);
          localObject3 = localObject2;
          if (((String)localObject2).length() > 0) {
            localObject3 = ((String)localObject2).substring(0, 1).toUpperCase() + ((String)localObject2).substring(1);
          }
          if (((String)localObject2).indexOf(".") != -1)
          {
            localObject4 = Reflection.drillDownToObject(localObject1, (String)localObject3, (String)localObject2);
            localObject1 = localObject4[0];
            localObject3 = (String)localObject4[1];
            localObject2 = (String)localObject4[2];
          }
          this.method = Reflection.findGetMethod(localObject1, (String)localObject3);
          if (this.method == null) {
            if ((localObject1 instanceof Map))
            {
              localObject4 = (Map)localObject1;
              Object localObject5 = ((Map)localObject4).get(localObject2);
              if (localObject5 == null)
              {
                if ((paramHttpServlet instanceof JTF)) {
                  ((JTF)paramHttpServlet).log("ERROR <jtf:getProperty name=\"" + str2 + "\" property=\"" + (String)localObject2 + "\"/>  No such method " + localObject1.getClass() + ".get" + (String)localObject2 + ".  Additionally, Map Object returns a null value.");
                }
              }
              else {
                str1 = localObject5.toString();
              }
            }
            else if ((paramHttpServlet instanceof JTF))
            {
              ((JTF)paramHttpServlet).log("ERROR <jtf:getProperty name=\"" + str2 + "\" property=\"" + (String)localObject2 + "\"/>  No such method " + localObject1.getClass() + ".get" + (String)localObject2);
            }
          }
        }
        if (this.method != null) {
          try
          {
            localObject2 = this.method.invoke(localObject1, null);
            if (localObject2 != null) {
              str1 = localObject2.toString();
            }
          }
          catch (Throwable localThrowable)
          {
            if ((localThrowable instanceof InvocationTargetException))
            {
              localObject4 = (InvocationTargetException)localThrowable;
              localObject3 = ((InvocationTargetException)localObject4).getTargetException().toString();
            }
            else
            {
              localObject3 = localThrowable.toString();
            }
            localObject4 = this.property.evaluate(paramHttpServlet, paramHttpServletRequest);
            if ((paramHttpServlet instanceof JTF)) {
              ((JTF)paramHttpServlet).log("ERROR <jtf:getProperty name=\"" + str2 + "\" property=\"" + (String)localObject4 + "\"/>  Unable to invoke method on " + localObject1.getClass() + '.' + this.method.getName() + " " + (String)localObject3);
            }
          }
        }
      }
    }
    return str1;
  }
  
  protected int parse(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString, int paramInt)
    throws TemplateParseException
  {
    int i = parse(paramServletContext, paramTemplateCache, paramString, paramInt, new a(paramServletContext, paramTemplateCache));
    if (this.name == null) {
      throw new TemplateParseException(paramString, paramInt, "<jtf:getProperty> The name attribute is missing.");
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
        GetPropertyAction.this.name = new TemplateAction();
        GetPropertyAction.this.name.parse(this.jdField_if, this.a, paramString2);
      }
      else if (paramString1.equals("property"))
      {
        GetPropertyAction.this.property = new TemplateAction();
        GetPropertyAction.this.property.parse(this.jdField_if, this.a, paramString2);
      }
      else
      {
        throw new Exception("<jtf:getProperty> " + paramString1 + " is an invalid attribute.");
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.template.GetPropertyAction
 * JD-Core Version:    0.7.0.1
 */