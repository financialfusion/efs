package com.ffusion.tasks.cashcon;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.cashcon.Location;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.CashCon;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteLocation
  extends BaseTask
  implements Task
{
  protected String _locationBPWID = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = DeleteLocation.class.getName();
    String str2 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      Location localLocation = CashCon.getLocation(localSecureUser, this._locationBPWID, localHashMap);
      CashCon.deleteLocation(localSecureUser, this._locationBPWID, localHashMap);
      HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 2, Integer.toString(localSecureUser.getBusinessID()));
      localLocation.logDeletion(localHistoryTracker, "Delete cash concentration location with name " + localLocation.getLocationName());
      jdMethod_for(str1, localHistoryTracker);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str2 = this.serviceErrorURL;
    }
    return str2;
  }
  
  public String getLocationBPWID()
  {
    return this._locationBPWID;
  }
  
  public void setLocationBPWID(String paramString)
  {
    this._locationBPWID = paramString;
  }
  
  private static void jdMethod_for(String paramString, HistoryTracker paramHistoryTracker)
  {
    if (paramString == null) {
      paramString = DeleteLocation.class.getName() + ".logHistoryRecord";
    }
    try
    {
      HistoryAdapter.addHistory(paramHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.WARNING, "Add History failed for " + paramString + ": " + localProfileException.toString());
      DebugLog.throwing(paramString, localProfileException);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.DeleteLocation
 * JD-Core Version:    0.7.0.1
 */