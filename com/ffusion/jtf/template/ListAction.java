package com.ffusion.jtf.template;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class ListAction
  extends TemplateAction
{
  private static final String B = "<jtf:list> ";
  private static final String y = "<jtf:list> The collection attribute is missing.";
  private static final String r = "<jtf:list> The items attribute is missing.";
  static final String z = "collection=";
  static final String s = "items=";
  static final String u = "startIndex=";
  static final String x = "endIndex=";
  private TemplateAction t;
  private String[] v;
  private TemplateAction A;
  private TemplateAction w;
  
  public void process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    if ((this.t != null) && (this.v != null))
    {
      Collection localCollection = jdMethod_if(paramHttpServlet, paramHttpServletRequest);
      if (localCollection != null) {
        a(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse, localCollection);
      }
    }
  }
  
  private void a(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Collection paramCollection)
    throws IOException
  {
    Iterator localIterator = paramCollection.iterator();
    Throwable localThrowable1 = 1;
    Throwable localThrowable2 = 2147483647;
    try
    {
      if (this.A != null)
      {
        localThrowable1 = Integer.valueOf(this.A.evaluate(paramHttpServlet, paramHttpServletRequest)).intValue();
        a(localIterator, localThrowable1);
      }
      if (this.w != null) {
        localThrowable2 = Integer.valueOf(this.w.evaluate(paramHttpServlet, paramHttpServletRequest)).intValue();
      }
    }
    catch (Throwable localThrowable3)
    {
      jdMethod_do(paramHttpServlet, paramHttpServletRequest);
    }
    localThrowable3 = localThrowable1;
    while ((localThrowable3 <= localThrowable2) && (localIterator.hasNext()))
    {
      for (int i = 0; i < this.v.length; i++)
      {
        if ((localIterator.hasNext()) && (localThrowable3 <= localThrowable2)) {
          paramHttpServletRequest.setAttribute(this.v[i], localIterator.next());
        } else {
          paramHttpServletRequest.setAttribute(this.v[i], "");
        }
        localThrowable3++;
      }
      super.process(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
    }
  }
  
  private void jdMethod_do(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest)
  {
    String str = this.t.evaluate(paramHttpServlet, paramHttpServletRequest);
    StringBuffer localStringBuffer = new StringBuffer("ERROR <jtf:list collection=\"");
    localStringBuffer.append(str);
    localStringBuffer.append("\" items=\"");
    for (int i = 0; i < this.v.length; i++)
    {
      if (i != 0) {
        localStringBuffer.append(',');
      }
      localStringBuffer.append(this.v[i]);
    }
    localStringBuffer.append("\"/>");
    localStringBuffer.append(" startIndex or endIndex is not a number");
    paramHttpServlet.log(localStringBuffer.toString());
  }
  
  private void a(Iterator paramIterator, int paramInt)
  {
    for (int i = 1; (i < paramInt) && (paramIterator.hasNext()); i++) {
      paramIterator.next();
    }
  }
  
  private Collection jdMethod_if(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest)
  {
    String str = this.t.evaluate(paramHttpServlet, paramHttpServletRequest);
    Object localObject = paramHttpServletRequest.getAttribute(str);
    if (localObject == null)
    {
      localObject = paramHttpServletRequest.getSession().getAttribute(str);
      if (localObject == null) {
        localObject = paramHttpServlet.getServletContext().getAttribute(str);
      }
    }
    if ((localObject instanceof Collection)) {
      return (Collection)localObject;
    }
    return null;
  }
  
  protected int parse(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString, int paramInt)
    throws TemplateParseException
  {
    int i = paramInt;
    int j = 0;
    int k = 0;
    do
    {
      int m = paramString.indexOf("<", i);
      int n = paramString.indexOf(">", i);
      if (m < n)
      {
        i = m + 1;
        j++;
      }
      else
      {
        i = n + 1;
        k++;
      }
    } while (j > k);
    if (i == -1) {
      throw new TemplateParseException(paramString, paramInt, "<jtf:list> This is invalid XML.");
    }
    a(paramServletContext, paramTemplateCache, paramString, paramInt, i);
    a(paramTemplateCache, paramString, paramInt, i);
    jdMethod_if(paramServletContext, paramTemplateCache, paramString, paramInt, i);
    paramInt = parse(paramServletContext, paramTemplateCache, paramString, i, "list");
    return paramInt;
  }
  
  private void a(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString, int paramInt1, int paramInt2)
    throws TemplateParseException
  {
    String str = a(paramString, "collection=", paramInt1, paramInt2);
    if (str == null) {
      throw new TemplateParseException(paramString, paramInt1, "<jtf:list> The collection attribute is missing.");
    }
    this.t = new TemplateAction();
    this.t.parse(paramServletContext, paramTemplateCache, str);
  }
  
  private void a(TemplateCache paramTemplateCache, String paramString, int paramInt1, int paramInt2)
    throws TemplateParseException
  {
    String str = a(paramString, "items=", paramInt1, paramInt2);
    if (str == null) {
      throw new TemplateParseException(paramString, paramInt1, "<jtf:list> The items attribute is missing.");
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(str, ", ");
    int i = localStringTokenizer.countTokens();
    this.v = new String[i];
    for (int j = 0; j < i; j++) {
      this.v[j] = localStringTokenizer.nextToken();
    }
  }
  
  private void jdMethod_if(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString, int paramInt1, int paramInt2)
    throws TemplateParseException
  {
    String str = a(paramString, "startIndex=", paramInt1, paramInt2);
    if (str != null)
    {
      this.A = new TemplateAction();
      this.A.parse(paramServletContext, paramTemplateCache, str);
      str = a(paramString, "endIndex=", paramInt1, paramInt2);
      if (str != null)
      {
        this.w = new TemplateAction();
        this.w.parse(paramServletContext, paramTemplateCache, str);
      }
      Integer localInteger = null;
      int i = -1;
      int j = 0;
      ArrayList localArrayList1 = this.A.actions;
      if (localArrayList1.size() == 1)
      {
        localObject = localArrayList1.iterator();
        while (((Iterator)localObject).hasNext())
        {
          Action localAction1 = (Action)((Iterator)localObject).next();
          str = null;
          if ((localAction1 instanceof HtmlAction))
          {
            str = localAction1.evaluate(null, null);
            if (str == null) {
              throw new TemplateParseException(paramString, paramInt1, "No value for Html Action");
            }
            try
            {
              localInteger = Integer.valueOf(str);
              i = localInteger.intValue();
              j = 1;
            }
            catch (Exception localException1)
            {
              throw new TemplateParseException(paramString, paramInt1, "Start value is not integer type.");
            }
          }
        }
      }
      Object localObject = null;
      int k = -1;
      int m = 0;
      ArrayList localArrayList2 = this.w.actions;
      if (localArrayList2.size() == 1)
      {
        Iterator localIterator = localArrayList2.iterator();
        while (localIterator.hasNext())
        {
          Action localAction2 = (Action)localIterator.next();
          str = null;
          if ((localAction2 instanceof HtmlAction))
          {
            str = localAction2.evaluate(null, null);
            if (str == null) {
              throw new TemplateParseException(paramString, paramInt1, "No value for Html Action.");
            }
            try
            {
              localObject = Integer.valueOf(str);
              k = ((Integer)localObject).intValue();
              m = 1;
            }
            catch (Exception localException2)
            {
              throw new TemplateParseException(paramString, paramInt1, "End value is not integer type.");
            }
          }
        }
      }
      if ((j != 0) && (i < 1)) {
        throw new TemplateParseException(paramString, paramInt1, "Start index of collection cannot be negative.");
      }
      if ((j != 0) && (m != 0) && (k < i)) {
        throw new TemplateParseException(paramString, paramInt1, "End index of collection cannot be less than start index.");
      }
    }
  }
  
  String a(String paramString1, String paramString2, int paramInt1, int paramInt2)
    throws TemplateParseException
  {
    String str = null;
    int i = paramString1.indexOf(paramString2, paramInt1);
    if ((i != -1) && (i < paramInt2))
    {
      i += paramString2.length() + 1;
      int j = paramString1.indexOf('"', i + 1);
      if ((j == -1) || (j > paramInt2)) {
        throw new TemplateParseException(paramString1, i, "<jtf:list> This is invalid XML.");
      }
      str = paramString1.substring(i, j);
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.template.ListAction
 * JD-Core Version:    0.7.0.1
 */