package com.ffusion.jtf.taglib;

import com.ffusion.util.logging.DebugLog;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class removeProperty
  extends TagSupport
{
  String a;
  String jdField_if;
  
  public removeProperty()
  {
    release();
  }
  
  public void release()
  {
    this.a = null;
  }
  
  public void setName(String paramString)
  {
    this.a = paramString;
  }
  
  public void setStartsWith(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public int doStartTag()
    throws JspException
  {
    return 0;
  }
  
  public int doEndTag()
    throws JspException
  {
    try
    {
      jdField_if("");
      String str1 = propertyValue.Evaluate(this.a, this.pageContext);
      String str2 = propertyValue.Evaluate(this.jdField_if, this.pageContext);
      int i = 0;
      if ((str2 != null) && ("true".equalsIgnoreCase(str2))) {
        i = 1;
      }
      StringTokenizer localStringTokenizer = new StringTokenizer(str1, ",", false);
      while (localStringTokenizer.hasMoreTokens())
      {
        String str3 = localStringTokenizer.nextToken().trim();
        if (str3.length() != 0)
        {
          this.pageContext.removeAttribute(str3);
          this.pageContext.getSession().removeAttribute(str3);
          if (i != 0)
          {
            Enumeration localEnumeration = this.pageContext.getSession().getAttributeNames();
            while (localEnumeration.hasMoreElements())
            {
              String str4 = (String)localEnumeration.nextElement();
              if ((str4.startsWith(str3)) && (str4.length() > str3.length()))
              {
                this.pageContext.removeAttribute(str4);
                this.pageContext.getSession().removeAttribute(str4);
              }
            }
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      a(localThrowable);
      if (!TagHandlerUtil.a(localThrowable, this.pageContext)) {
        return 5;
      }
    }
    return 6;
  }
  
  private void jdField_if(String paramString)
  {
    if (DebugLog.getLogger().isLoggable(Level.FINEST)) {
      DebugLog.log(Level.FINEST, a(paramString));
    }
  }
  
  private void a(Throwable paramThrowable)
  {
    DebugLog.throwing(a("\n\tException: "), paramThrowable);
  }
  
  private String a(String paramString)
  {
    return "\tremoveProperty name=" + this.a + (this.jdField_if != null ? ", StartsWith=" + this.jdField_if : "") + paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.removeProperty
 * JD-Core Version:    0.7.0.1
 */