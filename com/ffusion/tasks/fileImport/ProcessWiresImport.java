package com.ffusion.tasks.fileImport;

import com.ffusion.beans.XMLStrings;
import com.ffusion.beans.ach.ACHClassCode;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcessWiresImport
  extends BaseTask
  implements ACHClassCode, XMLStrings
{
  protected String importCollectionSessionName = "ImportWireTransfers";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    String str = this.successURL;
    HashMap localHashMap = (HashMap)localHttpSession.getAttribute("UploadResults");
    if (localHashMap == null)
    {
      this.error = 23002;
      return this.taskErrorURL;
    }
    WireTransfers localWireTransfers = (WireTransfers)localHashMap.get("ImportWireTransfers");
    localHttpSession.setAttribute(this.importCollectionSessionName, localWireTransfers);
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fileImport.ProcessWiresImport
 * JD-Core Version:    0.7.0.1
 */