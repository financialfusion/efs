package com.ffusion.tasks.fx;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.beans.fx.FXRateSheet;
import com.ffusion.beans.fx.FXRates;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.FX;
import com.ffusion.fx.FXException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.beans.DateTime;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetFXRateSheet
  extends BaseTask
{
  private String Xa = "FOREIGN_EXCHANGE_RATESHEET";
  private String Xc = "FOREIGN_EXCHANGE_RATES";
  private String W8 = "FXBackURL";
  private String Xd = "BackURL";
  private String W5;
  private int W7 = 0;
  private String W6 = "0";
  private String W4 = null;
  private boolean W9 = false;
  private boolean Xb = false;
  
  public void setFXSessionName(String paramString)
  {
    this.Xa = paramString;
  }
  
  public void setBaseCurrencyCode(String paramString)
  {
    this.W5 = paramString;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute("UserLocale");
    Locale localLocale = localUserLocale.getLocale();
    String str2 = (String)localHttpSession.getAttribute(this.W8);
    localHttpSession.setAttribute(this.Xd, str2);
    try
    {
      localHttpSession.removeAttribute(this.Xa);
      FXRateSheet localFXRateSheet = null;
      Object localObject1;
      Object localObject2;
      if (this.W7 == 3)
      {
        localObject1 = (Business)localHttpSession.getAttribute("Business");
        EntitlementGroups localEntitlementGroups = Entitlements.getChildrenByGroupType(((Business)localObject1).getEntitlementGroupIdValue(), "Business");
        if (localEntitlementGroups.size() == 0)
        {
          this.W6 = ((Business)localObject1).getEntitlementGroupId();
        }
        else
        {
          Iterator localIterator = localEntitlementGroups.iterator();
          if (localIterator.hasNext())
          {
            localObject2 = (EntitlementGroup)localIterator.next();
            this.W6 = Integer.toString(((EntitlementGroup)localObject2).getGroupId());
          }
        }
      }
      if (this.W4 == null)
      {
        localFXRateSheet = FX.getFXRateSheet(localSecureUser, this.W5, this.W7, this.W6, localHashMap);
      }
      else
      {
        localObject1 = null;
        try
        {
          localObject1 = new DateTime(this.W4, localSecureUser.getLocale(), localSecureUser.getDateFormat());
        }
        catch (Exception localException2)
        {
          this.error = 44;
          str1 = this.taskErrorURL;
          return str1;
        }
        localFXRateSheet = FX.getFXRateSheet(localSecureUser, this.W5, (DateTime)localObject1, this.W7, this.W6, localHashMap);
      }
      if (localFXRateSheet != null)
      {
        localObject1 = localFXRateSheet.getRates();
        if (localObject1 != null) {
          ((FXRates)localObject1).setLocale(localLocale);
        }
        FXRates localFXRates = new FXRates(localLocale);
        if (localObject1 != null)
        {
          for (int i = 0; i < ((FXRates)localObject1).size(); i++)
          {
            localObject2 = (FXRate)((FXRates)localObject1).get(i);
            if ((isRemoveEmptyBuyPriceRate()) && ((((FXRate)localObject2).getBuyPrice() == null) || (((FXRate)localObject2).getBuyPrice().getIsZero()) || (((FXRate)localObject2).getBuyPrice().isNegative()))) {
              localFXRates.add(localObject2);
            }
            if ((isRemoveEmptySellPriceRate()) && ((((FXRate)localObject2).getSellPrice() == null) || (((FXRate)localObject2).getSellPrice().getIsZero()) || (((FXRate)localObject2).getSellPrice().isNegative()))) {
              localFXRates.add(localObject2);
            }
            ((FXRate)localObject2).setLocale(localLocale);
          }
          for (i = 0; i < localFXRates.size(); i++)
          {
            localObject2 = (FXRate)localFXRates.get(i);
            ((FXRates)localObject1).remove(localObject2);
          }
        }
        localHttpSession.setAttribute(this.Xa, localFXRateSheet);
        if (localObject1 != null) {
          localHttpSession.setAttribute(this.Xc, localObject1);
        } else {
          localHttpSession.removeAttribute(this.Xc);
        }
      }
      else
      {
        localHttpSession.removeAttribute(this.Xa);
        localHttpSession.removeAttribute(this.Xc);
      }
    }
    catch (CSILException localCSILException)
    {
      str1 = this.serviceErrorURL;
      this.error = localCSILException.getCode();
      if ((localCSILException.childException != null) && ((localCSILException.childException instanceof FXException))) {
        this.error = ((FXException)localCSILException.childException).getErrorCode();
      }
    }
    catch (Exception localException1)
    {
      this.error = 34000;
      str1 = this.taskErrorURL;
    }
    return str1;
  }
  
  public String getObjectID()
  {
    return this.W6;
  }
  
  public void setObjectID(String paramString)
  {
    this.W6 = paramString;
  }
  
  public int getObjectTypeValue()
  {
    return this.W7;
  }
  
  public String getObjectType()
  {
    return Integer.toString(this.W7);
  }
  
  public void setObjectType(String paramString)
  {
    this.W7 = Integer.parseInt(paramString);
  }
  
  public String getAsOfDate()
  {
    return this.W4;
  }
  
  public void setAsOfDate(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.W4 = paramString;
    } else {
      this.W4 = null;
    }
  }
  
  public String getRatesSessionName()
  {
    return this.Xc;
  }
  
  public void setRatesSessionName(String paramString)
  {
    this.Xc = paramString;
  }
  
  public boolean isRemoveEmptyBuyPriceRate()
  {
    return this.W9;
  }
  
  public void setRemoveEmptyBuyPriceRate(String paramString)
  {
    this.W9 = Boolean.valueOf(paramString).booleanValue();
  }
  
  public boolean isRemoveEmptySellPriceRate()
  {
    return this.Xb;
  }
  
  public void setRemoveEmptySellPriceRate(String paramString)
  {
    this.Xb = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fx.GetFXRateSheet
 * JD-Core Version:    0.7.0.1
 */