package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.business.MarketSegment;
import com.ffusion.beans.business.MarketSegments;
import com.ffusion.beans.user.Users;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Business;
import com.ffusion.csil.core.UserAdmin;
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

public class DeleteMarketSegment
  extends BaseTask
  implements BusinessTask
{
  int hj;
  protected String userType = "Business";
  protected String marketSegmentsCollectionName = "MarketSegments";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Object localObject;
    try
    {
      HashMap localHashMap = null;
      if (this.userType.equalsIgnoreCase("Business"))
      {
        localObject = Business.getBusinessesByMarketSegment(localSecureUser, this.hj, localHashMap);
        if (((Businesses)localObject).size() > 0)
        {
          this.error = 4114;
          localHttpSession.setAttribute("BusinessesByMarketSegment", localObject);
          return str;
        }
      }
      else
      {
        localObject = UserAdmin.getUsersByMarketSegment(localSecureUser, this.hj, localHashMap);
        if (((Users)localObject).size() > 0)
        {
          this.error = 4114;
          localHttpSession.setAttribute("UsersByMarketSegment", localObject);
          return str;
        }
      }
      Business.deleteMarketSegment(localSecureUser, this.hj, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      MarketSegments localMarketSegments = (MarketSegments)localHttpSession.getAttribute(this.marketSegmentsCollectionName);
      if (localMarketSegments != null)
      {
        localObject = localMarketSegments.getById(this.hj);
        if (localObject != null)
        {
          HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 3, Integer.toString(this.hj));
          ((MarketSegment)localObject).logDeletion(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
          try
          {
            HistoryAdapter.addHistory(localHistoryTracker.getHistories());
          }
          catch (ProfileException localProfileException)
          {
            DebugLog.log(Level.SEVERE, "Add History failed for DeleteMarketSegment: " + localProfileException.toString());
          }
        }
        localMarketSegments.removeById(this.hj);
        localHttpSession.setAttribute(this.marketSegmentsCollectionName, localMarketSegments);
      }
    }
    return str;
  }
  
  public void setMarketSegmentId(String paramString)
  {
    try
    {
      this.hj = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setUserType(String paramString)
  {
    this.userType = paramString;
  }
  
  public void setMarketSegmentsCollectionName(String paramString)
  {
    this.marketSegmentsCollectionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.DeleteMarketSegment
 * JD-Core Version:    0.7.0.1
 */