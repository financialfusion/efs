package com.ffusion.jtf.taglib;

import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class author
  extends TagSupport
{
  private String a = null;
  
  public author()
  {
    release();
  }
  
  public void release()
  {
    this.a = null;
  }
  
  public void setPage(String paramString)
  {
    this.a = paramString;
  }
  
  public String getPage()
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
    jdMethod_if("");
    try
    {
      HttpSession localHttpSession = this.pageContext.getSession();
      ArrayList localArrayList = (ArrayList)localHttpSession.getAttribute("jtf_authorInfo");
      if ((this.a != null) && (localArrayList != null)) {
        localArrayList.add(this.a);
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
  
  private void jdMethod_if(String paramString)
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
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("author page=");
    localStringBuffer.append(this.a);
    localStringBuffer.append(paramString);
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.author
 * JD-Core Version:    0.7.0.1
 */