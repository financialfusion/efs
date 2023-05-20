package com.ffusion.tasks.filemonitor;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.FileMonitor;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.filemonitor.FMFileDescriptions;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetFileDescriptionsTask
  extends BaseTask
{
  public static final String DEFAULT_COLLECTION_NAME = "FM_FILE_DESCRIPTIONS";
  private String aP4 = "FM_FILE_DESCRIPTIONS";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    String str = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      return super.getTaskErrorURL();
    }
    try
    {
      HashMap localHashMap = new HashMap();
      FMFileDescriptions localFMFileDescriptions = FileMonitor.getFileDescriptions(localSecureUser, localHashMap);
      localHttpSession.setAttribute(this.aP4, localFMFileDescriptions);
      str = super.getSuccessURL();
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = super.getServiceErrorURL();
    }
    return str;
  }
  
  public void setCollectionName(String paramString)
  {
    this.aP4 = paramString;
  }
  
  public String getCollectionName()
  {
    return this.aP4;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.filemonitor.GetFileDescriptionsTask
 * JD-Core Version:    0.7.0.1
 */