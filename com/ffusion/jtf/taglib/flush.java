package com.ffusion.jtf.taglib;

import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class flush
  extends TagSupport
{
  public flush()
  {
    release();
  }
  
  public void release() {}
  
  public int doStartTag()
    throws JspException
  {
    return 0;
  }
  
  public int doEndTag()
    throws JspException
  {
    a("");
    try
    {
      this.pageContext.getOut().flush();
    }
    catch (IOException localIOException)
    {
      a(": Exception flushing");
    }
    return 0;
  }
  
  private void a(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer("\tflush");
    if (paramString != null) {
      localStringBuffer.append(paramString);
    }
    DebugLog.log(Level.FINEST, localStringBuffer.toString());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.flush
 * JD-Core Version:    0.7.0.1
 */