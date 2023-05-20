package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.wiretransfers.WireTransfer;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SaveWireAsTemplate
  extends AddWireTransfer
{
  private String Dg = "AddWireTransfer";
  
  public SaveWireAsTemplate()
  {
    this.initFlag = false;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    if (this.initFlag == true)
    {
      str = initialize(paramHttpServletRequest, localHttpSession);
      set((WireTransfer)localHttpSession.getAttribute(this.Dg));
      if (getWireType().equals("RECURRING")) {
        setWireType("RECTEMPLATE");
      } else {
        setWireType("TEMPLATE");
      }
      setWireLimit(getAmount());
    }
    else
    {
      str = addWireTransfer(localHttpSession);
    }
    return str;
  }
  
  public void setWireToSaveAsTemplate(String paramString)
  {
    this.Dg = paramString;
  }
  
  public String getWireToSaveAsTemplate()
  {
    return this.Dg;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.SaveWireAsTemplate
 * JD-Core Version:    0.7.0.1
 */