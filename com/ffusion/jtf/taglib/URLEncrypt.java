package com.ffusion.jtf.taglib;

import com.ffusion.jtf.UrlEncryptor;
import com.ffusion.util.logging.DebugLog;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class URLEncrypt
  extends TagSupport
{
  private String a = null;
  
  public URLEncrypt()
  {
    release();
  }
  
  public void release() {}
  
  public void setUrl(String paramString)
  {
    this.a = paramString;
  }
  
  public String getUrl()
  {
    return this.a;
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
      String str1 = propertyValue.Evaluate(this.a, this.pageContext);
      int i = str1.indexOf("?");
      if ((i > 0) && (i < str1.length() - 1))
      {
        String str2 = str1.substring(0, i + 1);
        String str3 = str1.substring(i + 1);
        str1 = str2 + jdMethod_if(str3);
      }
      this.pageContext.getOut().write(str1);
    }
    catch (Throwable localThrowable)
    {
      a(localThrowable);
    }
    return 6;
  }
  
  private String jdMethod_if(String paramString)
  {
    if ((paramString == null) || (paramString.length() <= 0)) {
      return "";
    }
    UrlEncryptor localUrlEncryptor = (UrlEncryptor)this.pageContext.getSession().getAttribute("URL_ENCRYPTOR");
    return localUrlEncryptor.encrypt(paramString);
  }
  
  private void a(Throwable paramThrowable)
  {
    DebugLog.throwing(a("\n\tException: "), paramThrowable);
  }
  
  private String a(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("\turlEncrypt url=" + this.a);
    localStringBuffer.append(paramString);
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.URLEncrypt
 * JD-Core Version:    0.7.0.1
 */