package com.ffusion.jtf.taglib;

import com.ffusion.jtf.UrlEncryptor;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class LinkTag
  extends TagSupport
{
  public static final String URL_ENCRYPTOR = "URL_ENCRYPTOR";
  private String jdField_int = new String();
  private String jdField_else = null;
  private String jdField_byte = null;
  private String jdField_for = null;
  private String jdField_void = null;
  private String jdField_null = null;
  private String jdField_try = null;
  private String jdField_goto = null;
  private String jdField_if = null;
  private String jdField_long = null;
  private String jdField_do = null;
  private String jdField_new = null;
  private String jdField_char = null;
  private String jdField_case = null;
  private boolean a = false;
  
  public LinkTag()
  {
    release();
  }
  
  public void release() {}
  
  public void setUrl(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public String getUrl()
  {
    return this.jdField_int;
  }
  
  public void setId(String paramString)
  {
    this.jdField_else = paramString;
  }
  
  public String getId()
  {
    return this.jdField_else;
  }
  
  public void setClassattr(String paramString)
  {
    this.jdField_byte = paramString;
  }
  
  public String getClassattr()
  {
    return this.jdField_byte;
  }
  
  public void setStyle(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getStyle()
  {
    return this.jdField_for;
  }
  
  public void setTitle(String paramString)
  {
    this.jdField_void = paramString;
  }
  
  public String getTitle()
  {
    return this.jdField_void;
  }
  
  public void setLang(String paramString)
  {
    this.jdField_null = paramString;
  }
  
  public String getLang()
  {
    return this.jdField_null;
  }
  
  public void setDir(String paramString)
  {
    this.jdField_try = paramString;
  }
  
  public String getDir()
  {
    return this.jdField_try;
  }
  
  public void setName(String paramString)
  {
    this.jdField_goto = paramString;
  }
  
  public String getName()
  {
    return this.jdField_goto;
  }
  
  public void setTarget(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getTarget()
  {
    return this.jdField_if;
  }
  
  public void setAccesskey(String paramString)
  {
    this.jdField_long = paramString;
  }
  
  public String getAccesskey()
  {
    return this.jdField_long;
  }
  
  public void setTabindex(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getTabindex()
  {
    return this.jdField_do;
  }
  
  public void setOnmouseover(String paramString)
  {
    this.jdField_new = paramString;
  }
  
  public String getOnmouseover()
  {
    return this.jdField_new;
  }
  
  public void setOnmouseout(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public String getOnmouseout()
  {
    return this.jdField_char;
  }
  
  public void setonclick(String paramString)
  {
    this.jdField_case = paramString;
  }
  
  public String getOnclick()
  {
    return this.jdField_case;
  }
  
  private String a(String paramString1, String paramString2, String paramString3)
  {
    String str = paramString1;
    if (paramString1.indexOf(paramString2) != -1)
    {
      StringBuffer localStringBuffer = new StringBuffer(paramString1);
      int i = 0;
      int j = 0;
      int k = paramString2.length();
      int m = localStringBuffer.length();
      while (j != m)
      {
        i = localStringBuffer.toString().indexOf(paramString2, j);
        if (i != -1)
        {
          j = i + k;
          if (j > m) {
            j = m;
          }
          localStringBuffer.replace(i, j, paramString3);
        }
        else
        {
          j = m;
        }
      }
      str = localStringBuffer.toString();
    }
    return str;
  }
  
  public int doStartTag()
    throws JspException
  {
    a("doStartTag() Started.");
    try
    {
      int i = this.jdField_int.indexOf("?");
      String str1 = null;
      String str2 = null;
      if (i == -1)
      {
        str1 = propertyValue.Evaluate(this.jdField_int, this.pageContext);
        str2 = "";
        i = str1.indexOf("?");
        if (i != -1)
        {
          str2 = propertyValue.Evaluate(str1.substring(i + 1, str1.length()), this.pageContext);
          str1 = propertyValue.Evaluate(str1.substring(0, i), this.pageContext);
        }
      }
      else
      {
        str1 = propertyValue.Evaluate(this.jdField_int.substring(0, i), this.pageContext);
        str2 = propertyValue.Evaluate(this.jdField_int.substring(i + 1, this.jdField_int.length()), this.pageContext);
      }
      String str3 = "&#39;";
      String str4 = "'";
      String str5 = "\"";
      str2 = a(str2, str4, str3);
      UrlEncryptor localUrlEncryptor = (UrlEncryptor)this.pageContext.getSession().getAttribute("URL_ENCRYPTOR");
      str2 = localUrlEncryptor.encrypt(str2);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("<a href=");
      localStringBuffer.append(str5);
      localStringBuffer.append(str1);
      localStringBuffer.append("?");
      localStringBuffer.append(str2);
      localStringBuffer.append(str5);
      if (this.jdField_else != null) {
        a(localStringBuffer, "id", this.jdField_else);
      }
      if (this.jdField_byte != null) {
        a(localStringBuffer, "class", propertyValue.Evaluate(this.jdField_byte, this.pageContext));
      }
      if (this.jdField_for != null) {
        a(localStringBuffer, "style", this.jdField_for);
      }
      if (this.jdField_void != null) {
        a(localStringBuffer, "title", this.jdField_void);
      }
      if (this.jdField_null != null) {
        a(localStringBuffer, "lang", this.jdField_null);
      }
      if (this.jdField_try != null) {
        a(localStringBuffer, "dir", this.jdField_try);
      }
      if (this.jdField_goto != null) {
        a(localStringBuffer, "name", this.jdField_goto);
      }
      if (this.jdField_if != null) {
        a(localStringBuffer, "target", this.jdField_if);
      }
      if (this.jdField_long != null) {
        a(localStringBuffer, "AccessKey", this.jdField_long);
      }
      if (this.jdField_do != null) {
        a(localStringBuffer, "tabindex", this.jdField_do);
      }
      if (this.jdField_new != null) {
        a(localStringBuffer, "onmouseover", this.jdField_new);
      }
      if (this.jdField_char != null) {
        a(localStringBuffer, "onmouseout", this.jdField_char);
      }
      if (this.jdField_case != null) {
        a(localStringBuffer, "onclick", this.jdField_case);
      }
      localStringBuffer.append(" >");
      a("buf = " + localStringBuffer);
      try
      {
        this.pageContext.getOut().print(localStringBuffer.toString());
      }
      catch (IOException localIOException) {}
      a("doStartTag() Finished.");
    }
    catch (Throwable localThrowable)
    {
      a(localThrowable);
      if (!TagHandlerUtil.a(localThrowable, this.pageContext))
      {
        this.a = true;
        return 0;
      }
    }
    return 1;
  }
  
  public int doEndTag()
    throws JspTagException
  {
    try
    {
      this.pageContext.getOut().print("</a>");
    }
    catch (IOException localIOException) {}
    if (this.a)
    {
      this.a = false;
      return 5;
    }
    return 6;
  }
  
  private void a(String paramString)
  {
    if (DebugLog.getLogger().isLoggable(Level.FINEST)) {
      DebugLog.log(Level.FINEST, paramString);
    }
  }
  
  private void a(Throwable paramThrowable)
  {
    DebugLog.throwing("Exception: ", paramThrowable);
  }
  
  private static void a(StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString1.length() <= 0)) {
      return;
    }
    char c = '\'';
    int i = paramString2.indexOf("'");
    if (i >= 0)
    {
      int j = paramString2.indexOf("\"");
      if ((j < 0) || (j > i)) {
        c = '"';
      }
    }
    paramStringBuffer.append(' ');
    paramStringBuffer.append(paramString1);
    paramStringBuffer.append("=");
    paramStringBuffer.append(c);
    paramStringBuffer.append(paramString2);
    paramStringBuffer.append(c);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.LinkTag
 * JD-Core Version:    0.7.0.1
 */