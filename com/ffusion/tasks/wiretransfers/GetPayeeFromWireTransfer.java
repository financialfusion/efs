package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPayeeFromWireTransfer
  extends BaseTask
  implements WireTaskDefines
{
  protected String payeeSessionName = "WireTransferPayee";
  protected String wireSessionName = "WireTransfer";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    WireTransfer localWireTransfer = (WireTransfer)localHttpSession.getAttribute(this.wireSessionName);
    if (localWireTransfer == null)
    {
      this.error = 12005;
      return this.taskErrorURL;
    }
    WireTransferPayee localWireTransferPayee = localWireTransfer.getWirePayee();
    if (localWireTransferPayee == null)
    {
      this.error = 12016;
      return this.taskErrorURL;
    }
    localHttpSession.setAttribute(this.payeeSessionName, localWireTransferPayee);
    return str;
  }
  
  public void setPayeeSessionName(String paramString)
  {
    this.payeeSessionName = paramString;
  }
  
  public void setWireSessionName(String paramString)
  {
    this.wireSessionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetPayeeFromWireTransfer
 * JD-Core Version:    0.7.0.1
 */