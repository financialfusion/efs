package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.MarketSegment;
import com.ffusion.beans.business.MarketSegments;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Business;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyMarketSegment
  extends AddMarketSegment
{
  protected boolean initFlag = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.hJ = 0;
    String str = this.hG;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    boolean bool = true;
    if ((bool) && (this.initFlag)) {
      bool = init(localHttpSession);
    }
    if (bool)
    {
      this.hL = ((MarketSegments)localHttpSession.getAttribute(this.marketSegmentsCollectionName));
      if ((this.hL == null) || (this.hL.size() == 0))
      {
        this.hJ = 4110;
        return this.hH;
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      if (validateInput(localHttpSession))
      {
        if (this.hM)
        {
          this.hM = false;
          try
          {
            HashMap localHashMap = null;
            MarketSegment localMarketSegment = (MarketSegment)localHttpSession.getAttribute("OldMarketSegment");
            if (localMarketSegment == null)
            {
              this.hJ = 4112;
              return this.hH;
            }
            Business.modifyMarketSegment(localSecureUser, this, localMarketSegment, localHashMap);
          }
          catch (CSILException localCSILException)
          {
            this.hJ = MapError.mapError(localCSILException);
            str = this.hI;
          }
          if (this.hJ == 0)
          {
            localHttpSession.setAttribute(this.hF, this);
            this.hL.removeById(getIdValue());
            this.hL.add(this);
            localHttpSession.removeAttribute("OldMarketSegment");
          }
        }
      }
      else {
        str = this.hH;
      }
    }
    else
    {
      str = this.hH;
    }
    return str;
  }
  
  public boolean init(HttpSession paramHttpSession)
  {
    MarketSegment localMarketSegment1 = (MarketSegment)paramHttpSession.getAttribute("MarketSegment");
    if (localMarketSegment1 == null)
    {
      this.hJ = 4112;
      return false;
    }
    set(localMarketSegment1);
    MarketSegment localMarketSegment2 = new MarketSegment();
    localMarketSegment2.set(localMarketSegment1);
    paramHttpSession.setAttribute("OldMarketSegment", localMarketSegment2);
    this.initFlag = false;
    return true;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.ModifyMarketSegment
 * JD-Core Version:    0.7.0.1
 */