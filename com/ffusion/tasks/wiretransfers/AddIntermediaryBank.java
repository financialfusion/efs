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

public class AddIntermediaryBank
  extends BaseTask
  implements WireTaskDefines
{
  protected String payeeSessionName = "WireTransferPayee";
  protected String bankSessionName = "WireTransferBank";
  
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
    WireTransferBanks localWireTransferBanks = localWireTransferPayee.getIntermediaryBanks();
    WireTransferBank localWireTransferBank1 = (WireTransferBank)localHttpSession.getAttribute(this.bankSessionName);
    if (localWireTransferBank1 == null)
    {
      this.error = 12019;
      return this.taskErrorURL;
    }
    WireTransferBank localWireTransferBank2 = localWireTransferPayee.getDestinationBank();
    if (localWireTransferBank1.isSame(localWireTransferBank2) == true)
    {
      this.error = 12047;
      return this.taskErrorURL;
    }
    if ((localWireTransferBanks != null) && (localWireTransferBanks.size() > 0) && (localWireTransferBanks.isDuplicateBank(localWireTransferBank1) == true))
    {
      this.error = 12046;
      return this.taskErrorURL;
    }
    localWireTransferBanks.add(localWireTransferBank1);
    if (localWireTransferBank1.getAction() != null) {
      localWireTransferBanks.setFilter("ACTION!del");
    }
    return str;
  }
  
  public void setPayeeSessionName(String paramString)
  {
    this.payeeSessionName = paramString;
  }
  
  public void setBankSessionName(String paramString)
  {
    this.bankSessionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.AddIntermediaryBank
 * JD-Core Version:    0.7.0.1
 */