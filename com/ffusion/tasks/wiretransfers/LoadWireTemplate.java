package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransfers;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoadWireTemplate
  extends ModifyWireTransfer
{
  protected String templateId;
  protected String templatesSessionName = "WiresTemplates";
  
  public LoadWireTemplate()
  {
    this.beanSessionName = "WireTransfer";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    WireTransfers localWireTransfers = (WireTransfers)localHttpSession.getAttribute(this.templatesSessionName);
    if (localWireTransfers == null)
    {
      this.error = 12038;
      return this.taskErrorURL;
    }
    if ((this.templateId == null) || (this.templateId.length() == 0))
    {
      this.error = 81;
      return this.taskErrorURL;
    }
    WireTransfer localWireTransfer1 = null;
    WireTransfer localWireTransfer2 = (WireTransfer)localWireTransfers.getByID(this.templateId);
    if (localWireTransfer2 != null)
    {
      localWireTransfer1 = (WireTransfer)localWireTransfer2.clone();
      if (localWireTransfer2.getWireType().equals("RECTEMPLATE")) {
        localWireTransfer1.setWireType("RECURRING");
      } else {
        localWireTransfer1.setWireType("SINGLE");
      }
      localWireTransfer1.setWireSource("TEMPLATE");
      if (localWireTransfer2.getWireDestination().equals("INTERNATIONAL"))
      {
        localWireTransfer1.setAmount(localWireTransfer2.getOrigAmount());
        localWireTransfer1.setAmtCurrencyType(localWireTransfer2.getOrigCurrency());
      }
    }
    else
    {
      localWireTransfer1 = new WireTransfer();
    }
    localHttpSession.setAttribute(this.beanSessionName, localWireTransfer1);
    return str;
  }
  
  public void setTemplateId(String paramString)
  {
    this.templateId = paramString;
  }
  
  public void setTemplatesSessionName(String paramString)
  {
    this.templatesSessionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.LoadWireTemplate
 * JD-Core Version:    0.7.0.1
 */