package com.ffusion.jtf.taglib;

import com.ffusion.util.Reflection;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class list
  extends BodyTagSupport
{
  private Collection jdField_char;
  private String d;
  private int jdField_byte;
  private String[] jdField_long;
  private String jdField_int = null;
  private String a = null;
  private boolean jdField_try = false;
  private Object[] jdField_if = null;
  private int b = 0;
  private int jdField_for = 0;
  private Iterator jdField_else;
  private int jdField_new;
  private int jdField_do;
  private String jdField_case = null;
  private String[] jdField_void = null;
  private String[] jdField_null = null;
  private String[] jdField_goto = null;
  private boolean c = false;
  
  public list()
  {
    release();
  }
  
  public void release()
  {
    this.jdField_int = null;
    this.a = null;
    this.jdField_long = null;
    this.d = null;
    this.jdField_char = null;
    this.jdField_case = null;
    this.jdField_void = null;
    this.jdField_null = null;
    this.jdField_goto = null;
  }
  
  public void setFilter(String paramString)
  {
    this.jdField_case = paramString;
    StringTokenizer localStringTokenizer1 = new StringTokenizer(paramString, ",;");
    int i = localStringTokenizer1.countTokens();
    this.jdField_void = new String[i];
    this.jdField_null = new String[i];
    this.jdField_goto = new String[i];
    for (int j = 0; j < i; j++)
    {
      String str1 = localStringTokenizer1.nextToken();
      if ((str1 != null) && ("and".equalsIgnoreCase(str1.trim())))
      {
        this.c = true;
      }
      else
      {
        StringTokenizer localStringTokenizer2 = new StringTokenizer(str1, "=!", true);
        if ((localStringTokenizer2.countTokens() == 3) || (localStringTokenizer2.countTokens() == 2))
        {
          this.jdField_void[j] = localStringTokenizer2.nextToken().trim();
          this.jdField_goto[j] = localStringTokenizer2.nextToken();
          String str2 = "";
          if (localStringTokenizer2.countTokens() == 1) {
            str2 = localStringTokenizer2.nextToken().trim();
          }
          if (str2 == null) {
            str2 = "";
          }
          this.jdField_null[j] = str2;
        }
      }
    }
  }
  
  public String getFilter()
  {
    return this.jdField_case;
  }
  
  public void setStartIndex(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public String getStartIndex()
  {
    return this.jdField_int;
  }
  
  public void setEndIndex(String paramString)
  {
    this.a = paramString;
  }
  
  public String getEndIndex()
  {
    return this.a;
  }
  
  public void setColumns(String paramString)
  {
    this.jdField_try = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getColumns()
  {
    return String.valueOf(this.jdField_try);
  }
  
  public String getItems()
  {
    return "";
  }
  
  public void setItems(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ", ");
    int i = localStringTokenizer.countTokens();
    this.jdField_long = new String[i];
    for (int j = 0; j < i; j++) {
      this.jdField_long[j] = localStringTokenizer.nextToken();
    }
  }
  
  public String getCollection()
  {
    return this.d;
  }
  
  public void setCollection(String paramString)
  {
    this.d = paramString;
  }
  
  public int doStartTag()
    throws JspException
  {
    a("");
    if ((jdField_if()) && ((this.jdField_else.hasNext()) || ((this.jdField_try) && (this.jdField_if.length > 0)))) {
      return a();
    }
    return 0;
  }
  
  public int doAfterBody()
    throws JspTagException
  {
    BodyContent localBodyContent = getBodyContent();
    if (localBodyContent != null) {
      try
      {
        String str = localBodyContent.getString();
        localBodyContent.getEnclosingWriter().write(str);
        localBodyContent.clear();
      }
      catch (IOException localIOException)
      {
        throw new JspTagException("Error parsing <JSP:list ... in doAfterBody\r\n" + localIOException);
      }
    }
    if (this.jdField_try)
    {
      if ((this.b < this.jdField_if.length) && (this.b < this.jdField_for)) {
        return a();
      }
    }
    else if ((this.jdField_byte <= this.jdField_do) && (this.jdField_else.hasNext())) {
      return a();
    }
    return 0;
  }
  
  public int doEndTag()
    throws JspTagException
  {
    return 6;
  }
  
  private int a()
  {
    ServletRequest localServletRequest = this.pageContext.getRequest();
    try
    {
      int i;
      if (this.jdField_try)
      {
        i = this.b;
        for (int j = 0; j < this.jdField_long.length; j++)
        {
          if (i < this.jdField_if.length) {
            localServletRequest.setAttribute(this.jdField_long[j], this.jdField_if[i]);
          } else {
            localServletRequest.setAttribute(this.jdField_long[j], "");
          }
          i += this.jdField_for;
        }
        this.b += 1;
      }
      else
      {
        for (i = 0; i < this.jdField_long.length; i++)
        {
          if ((this.jdField_else.hasNext()) && (this.jdField_byte <= this.jdField_do))
          {
            Object localObject = a(this.jdField_else);
            if (localObject == null)
            {
              a(":No more items");
              return 0;
            }
            localServletRequest.setAttribute(this.jdField_long[i], localObject);
          }
          else
          {
            localServletRequest.setAttribute(this.jdField_long[i], "");
          }
          this.jdField_byte += 1;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      a(localThrowable);
      return 0;
    }
    return 2;
  }
  
  private boolean jdField_if()
    throws JspTagException
  {
    boolean bool = false;
    try
    {
      String str1 = propertyValue.Evaluate(this.d, this.pageContext);
      int i = str1.indexOf(".");
      Object localObject1;
      Object localObject2;
      Object localObject4;
      if (i != -1)
      {
        String str2 = str1.substring(0, i);
        localObject1 = this.pageContext.findAttribute(str2);
        if (localObject1 == null) {
          localObject1 = this.pageContext.getSession().getAttribute(str2);
        }
        if (localObject1 != null)
        {
          Method localMethod = null;
          localObject2 = str1.substring(i + 1, i + 2).toUpperCase() + str1.substring(i + 2);
          Object localObject3;
          if (((String)localObject2).indexOf(".") != -1)
          {
            localObject3 = Reflection.drillDownToObject(localObject1, (String)localObject2, (String)localObject2);
            localObject1 = localObject3[0];
            localObject2 = (String)localObject3[1];
          }
          localMethod = Reflection.findGetMethod(localObject1, (String)localObject2);
          Object localObject5;
          if (localMethod != null)
          {
            try
            {
              localObject3 = localMethod.invoke(localObject1, null);
              if (localObject3 != null) {
                this.jdField_char = ((Collection)localObject3);
              }
            }
            catch (Throwable localThrowable3)
            {
              if ((localThrowable3 instanceof InvocationTargetException))
              {
                InvocationTargetException localInvocationTargetException = (InvocationTargetException)localThrowable3;
                localObject5 = localInvocationTargetException.getTargetException().toString();
              }
              else
              {
                localObject5 = localThrowable3.toString();
              }
              a("Unable to invoke method on " + localObject1.getClass() + '.' + localMethod.getName() + " " + (String)localObject5);
            }
          }
          else if ((localObject1 instanceof Map))
          {
            localObject4 = (Map)localObject1;
            localObject5 = ((Map)localObject4).get(localObject2);
            if (localObject5 != null) {
              this.jdField_char = ((Collection)localObject5);
            } else {
              a("No such collection " + localObject1.getClass() + "." + (String)localObject2);
            }
          }
          else
          {
            a("No such collection " + localObject1.getClass() + "." + (String)localObject2);
          }
        }
      }
      int j;
      if (this.jdField_null != null) {
        for (j = 0; j < this.jdField_null.length; j++) {
          if (this.jdField_null[j] != null) {
            this.jdField_null[j] = propertyValue.Evaluate(this.jdField_null[j], this.pageContext);
          }
        }
      }
      if (this.jdField_char == null) {
        this.jdField_char = ((Collection)this.pageContext.findAttribute(str1));
      }
      if (this.jdField_char == null) {
        this.jdField_char = ((Collection)this.pageContext.getSession().getAttribute(str1));
      }
      if (this.jdField_char == null)
      {
        a("***** collection = null");
      }
      else
      {
        this.jdField_else = this.jdField_char.iterator();
        this.jdField_new = 1;
        this.jdField_do = 2147483647;
        try
        {
          if (this.jdField_int != null)
          {
            this.jdField_new = Integer.parseInt(propertyValue.Evaluate(this.jdField_int, this.pageContext));
            a(this.jdField_else, this.jdField_new);
          }
          if (this.a != null) {
            this.jdField_do = Integer.parseInt(propertyValue.Evaluate(this.a, this.pageContext));
          }
          if (this.jdField_try)
          {
            j = 0;
            localObject1 = new ArrayList();
            int k = this.jdField_new;
            while ((k <= this.jdField_do) && (this.jdField_else.hasNext()))
            {
              localObject2 = a(this.jdField_else);
              if (localObject2 == null) {
                break;
              }
              ((ArrayList)localObject1).add(localObject2);
              k++;
              j++;
            }
            int m = 0;
            this.jdField_if = new Object[j];
            while (m < j)
            {
              localObject4 = ((ArrayList)localObject1).get(m);
              this.jdField_if[m] = localObject4;
              m++;
            }
            this.jdField_for = (j / this.jdField_long.length);
            if (j % this.jdField_long.length > 0) {
              this.jdField_for += 1;
            }
            this.b = 0;
          }
          bool = true;
        }
        catch (Throwable localThrowable2)
        {
          a(new Exception(localThrowable2.toString()));
        }
      }
    }
    catch (Throwable localThrowable1)
    {
      a(localThrowable1);
    }
    this.jdField_byte = this.jdField_new;
    return bool;
  }
  
  private void a(Iterator paramIterator, int paramInt)
  {
    for (int i = 1; (i < paramInt) && (paramIterator.hasNext()); i++) {
      a(paramIterator);
    }
  }
  
  private Object a(Iterator paramIterator)
  {
    Object localObject1 = null;
    if ((this.jdField_void != null) && (this.jdField_null != null)) {}
    while (paramIterator.hasNext())
    {
      localObject1 = paramIterator.next();
      Object localObject2 = localObject1;
      Method localMethod = null;
      int i = 1;
      int j = 1;
      for (int k = 0; k < this.jdField_void.length; k++)
      {
        String str1 = this.jdField_void[k];
        if (str1 != null)
        {
          Object localObject3;
          if (str1.indexOf(".") != -1)
          {
            localObject3 = Reflection.drillDownToObject(localObject2, str1, str1);
            localObject2 = localObject3[0];
            str1 = (String)localObject3[1];
          }
          localMethod = Reflection.findGetMethod(localObject2, str1);
          if (localMethod != null)
          {
            try
            {
              localObject3 = localMethod.invoke(localObject2, null);
              if (localObject3 != null)
              {
                boolean bool = localObject3.toString().equals(this.jdField_null[k]);
                int m = 0;
                if ((("=".equals(this.jdField_goto[k])) && (bool)) || (("!".equals(this.jdField_goto[k])) && (!bool))) {
                  m = 1;
                }
                if (j != 0)
                {
                  j = 0;
                  i = m;
                }
                if (!this.c)
                {
                  i = (i != 0) || (m != 0) ? 1 : 0;
                  if (i != 0) {
                    break;
                  }
                }
                else
                {
                  i = (i != 0) && (m != 0) ? 1 : 0;
                  if (i == 0) {
                    break;
                  }
                }
              }
            }
            catch (Throwable localThrowable)
            {
              String str2;
              if ((localThrowable instanceof InvocationTargetException))
              {
                InvocationTargetException localInvocationTargetException = (InvocationTargetException)localThrowable;
                str2 = localInvocationTargetException.getTargetException().toString();
              }
              else
              {
                str2 = localThrowable.toString();
              }
              a("Unable to invoke method on " + localObject1.getClass() + '.' + localMethod.getName() + " " + str2);
            }
          }
          else
          {
            a("Unable to invoke method on " + localObject1.getClass() + ".Get" + str1 + "(method not found, removing Filter)");
            this.jdField_void[k] = null;
          }
        }
      }
      if (i == 0)
      {
        localObject1 = null;
        continue;
        localObject1 = paramIterator.next();
      }
    }
    return localObject1;
  }
  
  private void a(String paramString)
  {
    if (DebugLog.getLogger().isLoggable(Level.FINEST)) {
      DebugLog.log(Level.FINEST, jdField_if(paramString));
    }
  }
  
  private void a(Throwable paramThrowable)
  {
    DebugLog.throwing(jdField_if("\n\tException: "), paramThrowable);
  }
  
  private String jdField_if(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("\tlist collection=");
    localStringBuffer.append(this.d);
    if (this.jdField_int != null) {
      localStringBuffer.append(", startIndex=" + this.jdField_int);
    }
    if (this.a != null) {
      localStringBuffer.append(", endIndex=" + this.a);
    }
    if (this.jdField_try) {
      localStringBuffer.append(", columns=" + this.jdField_try);
    }
    if (this.jdField_case != null) {
      localStringBuffer.append(", filter='" + this.jdField_case + "'");
    }
    localStringBuffer.append(paramString);
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.list
 * JD-Core Version:    0.7.0.1
 */