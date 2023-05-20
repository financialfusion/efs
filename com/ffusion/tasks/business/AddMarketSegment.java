package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.MarketSegment;
import com.ffusion.beans.business.MarketSegments;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.core.Business;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddMarketSegment
  extends MarketSegment
  implements BusinessTask
{
  String hH = "TE";
  String hI = "SE";
  String hG;
  String hK;
  boolean hM = false;
  int hJ = 0;
  String hF = "MarketSegment";
  MarketSegments hL = null;
  protected String marketSegmentsCollectionName = "MarketSegments";
  protected String marketSegmentGroup;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.hJ = 0;
    String str = this.hG;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.hL = ((MarketSegments)localHttpSession.getAttribute(this.marketSegmentsCollectionName));
    if (this.hL == null)
    {
      this.hJ = 4110;
      return this.hH;
    }
    if (this.marketSegmentGroup == null)
    {
      this.hJ = 4161;
      return this.hH;
    }
    if (validateInput(localHttpSession))
    {
      if (this.hM)
      {
        this.hM = false;
        try
        {
          HashMap localHashMap = null;
          SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
          setBankId(localSecureUser.getBankID());
          int i = -1;
          if ((this.marketSegmentGroup.equals("Consumers")) || (this.marketSegmentGroup.equals("Business")))
          {
            EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroupByNameAndSvcBureau(this.marketSegmentGroup, "UserType", localSecureUser.getBankIDValue());
            if (localEntitlementGroup != null)
            {
              i = localEntitlementGroup.getGroupId();
            }
            else
            {
              this.hJ = 4160;
              return this.hH;
            }
          }
          else
          {
            this.hJ = 4162;
            return this.hH;
          }
          Business.addMarketSegment(localSecureUser, this, i, localHashMap);
        }
        catch (CSILException localCSILException)
        {
          this.hJ = MapError.mapError(localCSILException);
          str = this.hI;
        }
        if (this.hJ == 0)
        {
          this.hL.add(this);
          localHttpSession.setAttribute(this.hF, this);
        }
      }
    }
    else {
      str = this.hH;
    }
    return str;
  }
  
  public void setProcess(String paramString)
  {
    this.hM = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.hK = null;
    if (!paramString.equalsIgnoreCase("")) {
      this.hK = paramString.toUpperCase();
    }
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.hH = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.hI = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.hG = paramString;
  }
  
  public void setMarketSegmentSessionName(String paramString)
  {
    this.hF = paramString;
  }
  
  public boolean setError(int paramInt)
  {
    this.hJ = paramInt;
    return false;
  }
  
  public String getError()
  {
    return String.valueOf(this.hJ);
  }
  
  public int getErrorValue()
  {
    return this.hJ;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.hK == null) {
      return bool;
    }
    if (this.hK.indexOf("MARKETSEGMENTNAME") != -1)
    {
      if ((this.marketSegmentName == null) || (this.marketSegmentName.trim().length() == 0))
      {
        bool = setError(4109);
      }
      else
      {
        MarketSegment localMarketSegment = (MarketSegment)paramHttpSession.getAttribute("OldMarketSegment");
        int i = 1;
        if ((localMarketSegment != null) && (localMarketSegment.getMarketSegmentName().equals(getMarketSegmentName()))) {
          i = 0;
        }
        if ((i == 1) && (this.hL.getByMarketSegmentName(this.marketSegmentName) != null)) {
          bool = setError(4111);
        }
      }
    }
    else if ((this.hK.indexOf("SERVICESADMINGROUPID") != -1) && ((this.servicesAdminGroupId == null) || (this.servicesAdminGroupId.trim().length() == 0))) {
      bool = setError(4119);
    }
    this.hK = null;
    return bool;
  }
  
  public String getMarketSegmentsCollectionName()
  {
    return this.marketSegmentsCollectionName;
  }
  
  public void setMarketSegmentsCollectionName(String paramString)
  {
    this.marketSegmentsCollectionName = paramString;
  }
  
  public String getGroup()
  {
    return this.marketSegmentGroup;
  }
  
  public void setGroup(String paramString)
  {
    this.marketSegmentGroup = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.AddMarketSegment
 * JD-Core Version:    0.7.0.1
 */