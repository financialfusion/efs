package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.WireEntitlementUtil;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckWireEntitlements
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected boolean isEntitled = false;
  protected String operationName = null;
  protected String wireDestination = null;
  protected String wireSource = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 12014;
      return this.taskErrorURL;
    }
    String str2 = jdMethod_long(this.wireDestination, this.wireSource);
    if (str2 != null) {
      this.operationName = str2;
    }
    try
    {
      this.isEntitled = WireEntitlementUtil.checkWireEntitlements(localSecureUser, this.operationName);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    return str1;
  }
  
  public void setOperationName(String paramString)
  {
    this.operationName = paramString;
  }
  
  public String getOperationName()
  {
    return this.operationName;
  }
  
  public String getIsEntitled()
  {
    return String.valueOf(this.isEntitled);
  }
  
  public String getWireDestination()
  {
    return this.wireDestination;
  }
  
  public void setWireDestination(String paramString)
  {
    this.wireDestination = paramString;
  }
  
  public String getWireSource()
  {
    return this.wireSource;
  }
  
  public void setWireSource(String paramString)
  {
    this.wireSource = paramString;
  }
  
  private static String jdMethod_long(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null)) {
      return null;
    }
    if (paramString2.equals("FREEFORM"))
    {
      if (paramString1.equals("DOMESTIC")) {
        return "DOMESTIC_FREEFORM_WIRE";
      }
      if (paramString1.equals("INTERNATIONAL")) {
        return "INTERNATIONAL_FREEFORM_WIRE";
      }
      if (paramString1.equals("BOOKTRANSFER")) {
        return "BOOK_FREEFORM_WIRE";
      }
      if (paramString1.equals("DRAWDOWN")) {
        return "DRAWDOWN_FREEFORM_WIRE";
      }
      if (paramString1.equals("FEDWIRE")) {
        return "FED_FREEFORM_WIRE";
      }
      return null;
    }
    if (paramString2.equals("TEMPLATE"))
    {
      if (paramString1.equals("DOMESTIC")) {
        return "DOMESTIC_TEMPLATED_WIRE";
      }
      if (paramString1.equals("INTERNATIONAL")) {
        return "INTERNATIONAL_TEMPLATED_WIRE";
      }
      if (paramString1.equals("BOOKTRANSFER")) {
        return "BOOK_TEMPLATED_WIRE";
      }
      if (paramString1.equals("DRAWDOWN")) {
        return "DRAWDOWN_TEMPLATED_WIRE";
      }
      if (paramString1.equals("FEDWIRE")) {
        return "FED_TEMPLATED_WIRE";
      }
      return null;
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.CheckWireEntitlements
 * JD-Core Version:    0.7.0.1
 */