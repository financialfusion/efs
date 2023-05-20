package com.ffusion.tasks.util;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ConvertTransTypesToBAITypes
  extends BaseTask
{
  public static final String COLLECTION_SESSION_NAME = "BAI_TYPE_CODES";
  private String Qd = null;
  private String Qe = "BAI_TYPE_CODES";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    try
    {
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      HashMap localHashMap = new HashMap();
      ArrayList localArrayList = Banking.convertTransTypesToBAITypes(localSecureUser, this.Qd, localHashMap);
      localHttpSession.setAttribute(this.Qe, localArrayList);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      return this.taskErrorURL;
    }
    return this.successURL;
  }
  
  public void setTransactionTypes(String paramString)
  {
    this.Qd = paramString;
  }
  
  public void setSessionName(String paramString)
  {
    this.Qe = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.ConvertTransTypesToBAITypes
 * JD-Core Version:    0.7.0.1
 */