package com.ffusion.tasks.portal;

import com.ffusion.beans.portal.Stock;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditStock
  extends AddStock
  implements Task
{
  protected boolean initFlag = false;
  private String l4 = "Stock";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.initFlag)
    {
      if (!initProcess(localHttpSession)) {
        str = this.taskErrorURL;
      }
    }
    else {
      str = processEditStock(paramHttpServletRequest, localHttpSession);
    }
    return str;
  }
  
  protected boolean initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    Stock localStock = (Stock)paramHttpSession.getAttribute(this.l4);
    if (localStock == null)
    {
      this.error = 9040;
    }
    else
    {
      this.error = 0;
      set(localStock);
    }
    return this.error == 0;
  }
  
  protected String processEditStock(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = this.successURL;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = editStock(paramHttpSession);
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String editStock(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    Stock localStock = (Stock)paramHttpSession.getAttribute(this.l4);
    if (localStock == null)
    {
      this.error = 9040;
      str = this.taskErrorURL;
    }
    else
    {
      this.error = 0;
      localStock.set(this);
    }
    return str;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getInitialize()
  {
    return new Boolean(this.initFlag).toString();
  }
  
  public void setStock(String paramString)
  {
    this.l4 = paramString;
  }
  
  public String getStock()
  {
    return this.l4;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.EditStock
 * JD-Core Version:    0.7.0.1
 */