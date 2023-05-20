package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.wiretransfers.WireTransferBank;
import com.ffusion.beans.wiretransfers.WireTransferBanks;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RemoveIntermediaryBank
  extends BaseTask
  implements WireTaskDefines
{
  protected String payeeSessionName = "WireTransferPayee";
  protected String bankIndex = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    WireTransferPayee localWireTransferPayee = (WireTransferPayee)localHttpSession.getAttribute(this.payeeSessionName);
    if (localWireTransferPayee == null)
    {
      this.error = 12016;
      return this.taskErrorURL;
    }
    if (this.bankIndex == null)
    {
      this.error = 81;
      return this.taskErrorURL;
    }
    int i;
    try
    {
      i = Integer.parseInt(this.bankIndex);
    }
    catch (Exception localException)
    {
      this.error = 81;
      return this.taskErrorURL;
    }
    WireTransferBanks localWireTransferBanks = localWireTransferPayee.getIntermediaryBanks();
    WireTransferBank localWireTransferBank = (WireTransferBank)localWireTransferBanks.get(i);
    if (localWireTransferBank == null)
    {
      this.error = 12019;
      return this.taskErrorURL;
    }
    if (localWireTransferBank.getAction() == null)
    {
      localWireTransferBanks.remove(i);
    }
    else if (localWireTransferBank.getAction().equals("add"))
    {
      localWireTransferBanks.remove(i);
      localWireTransferBanks.setFilter("ACTION!del");
    }
    else
    {
      localWireTransferBank.setAction("del");
      localWireTransferBanks.setFilter("ACTION!del");
    }
    return str;
  }
  
  public void setPayeeSessionName(String paramString)
  {
    this.payeeSessionName = paramString;
  }
  
  public void setBankIndex(String paramString)
  {
    this.bankIndex = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.RemoveIntermediaryBank
 * JD-Core Version:    0.7.0.1
 */