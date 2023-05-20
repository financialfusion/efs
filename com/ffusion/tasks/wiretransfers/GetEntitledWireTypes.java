package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.WireEntitlementUtil;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.FilteredList;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetEntitledWireTypes
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  FilteredList Do;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 12014;
      return this.taskErrorURL;
    }
    try
    {
      jdMethod_try(localSecureUser);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      localCSILException.printStackTrace();
      return this.serviceErrorURL;
    }
    return str;
  }
  
  private void jdMethod_try(SecureUser paramSecureUser)
    throws CSILException
  {
    this.Do = new FilteredList();
    boolean bool1 = WireEntitlementUtil.checkWireEntitlements(paramSecureUser, "DOMESTIC_FREEFORM_WIRE");
    boolean bool2 = WireEntitlementUtil.checkWireEntitlements(paramSecureUser, "DOMESTIC_TEMPLATED_WIRE");
    if ((bool1) || (bool2)) {
      this.Do.add("DOMESTIC");
    }
    bool1 = WireEntitlementUtil.checkWireEntitlements(paramSecureUser, "INTERNATIONAL_FREEFORM_WIRE");
    bool2 = WireEntitlementUtil.checkWireEntitlements(paramSecureUser, "INTERNATIONAL_TEMPLATED_WIRE");
    if ((bool1) || (bool2)) {
      this.Do.add("INTERNATIONAL");
    }
    bool1 = WireEntitlementUtil.checkWireEntitlements(paramSecureUser, "BOOK_FREEFORM_WIRE");
    bool2 = WireEntitlementUtil.checkWireEntitlements(paramSecureUser, "BOOK_TEMPLATED_WIRE");
    if ((bool1) || (bool2)) {
      this.Do.add("BOOKTRANSFER");
    }
    bool1 = WireEntitlementUtil.checkWireEntitlements(paramSecureUser, "DRAWDOWN_FREEFORM_WIRE");
    bool2 = WireEntitlementUtil.checkWireEntitlements(paramSecureUser, "DRAWDOWN_TEMPLATED_WIRE");
    if ((bool1) || (bool2)) {
      this.Do.add("DRAWDOWN");
    }
    bool1 = WireEntitlementUtil.checkWireEntitlements(paramSecureUser, "FED_FREEFORM_WIRE");
    bool2 = WireEntitlementUtil.checkWireEntitlements(paramSecureUser, "FED_TEMPLATED_WIRE");
    if ((bool1) || (bool2)) {
      this.Do.add("FEDWIRE");
    }
    bool1 = WireEntitlementUtil.checkWireEntitlements(paramSecureUser, "HOST_WIRE");
    if (bool1) {
      this.Do.add("HOST");
    }
  }
  
  public FilteredList getEntitledWireTypes()
  {
    return this.Do;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetEntitledWireTypes
 * JD-Core Version:    0.7.0.1
 */