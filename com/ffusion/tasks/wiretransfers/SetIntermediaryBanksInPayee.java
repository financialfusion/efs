package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.wiretransfers.WireTransferBanks;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetIntermediaryBanksInPayee
  extends BaseTask
  implements WireTaskDefines
{
  protected String payeeSessionName = "WireTransferPayee";
  protected String bankCollectionSessionName = "WireTransferBanks";
  
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
    WireTransferBanks localWireTransferBanks = (WireTransferBanks)localHttpSession.getAttribute(this.bankCollectionSessionName);
    if (localWireTransferBanks == null)
    {
      this.error = 12018;
      return this.taskErrorURL;
    }
    localWireTransferPayee.setIntermediaryBanks(localWireTransferBanks);
    return str;
  }
  
  public void setPayeeSessionName(String paramString)
  {
    this.payeeSessionName = paramString;
  }
  
  public void setBankCollectionSessionName(String paramString)
  {
    this.bankCollectionSessionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.SetIntermediaryBanksInPayee
 * JD-Core Version:    0.7.0.1
 */