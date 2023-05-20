package com.ffusion.tasks.fx;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.FX;
import com.ffusion.fx.FXException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.beans.DateTime;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ConvertToBaseCurrency
  extends BaseTask
{
  private String WQ = "FOREIGN_EXCHANGE_BASE_CURRENCY_AMOUNT";
  private DateTime WL;
  private String WO = null;
  private String WP = "FXBackURL";
  private String WM = "BackURL";
  private int WR = 0;
  private String WK = null;
  private String WN = null;
  
  public void setFXSessionName(String paramString)
  {
    this.WQ = paramString;
  }
  
  public void setDate(DateTime paramDateTime)
  {
    this.WL = paramDateTime;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    Currency localCurrency = (Currency)localHttpSession.getAttribute("FOREIGN_EXCHANGE_CURRENCY_AMOUNT");
    String str2 = (String)localHttpSession.getAttribute(this.WP);
    localHttpSession.setAttribute(this.WM, str2);
    try
    {
      BigDecimal localBigDecimal = new BigDecimal(this.WN);
    }
    catch (Exception localException)
    {
      str1 = this.taskErrorURL;
      this.error = 34107;
      return str1;
    }
    try
    {
      if (this.WR != -1)
      {
        localHashMap.put("OBJECT_TYPE", getObjectType());
        if (this.WR == 3)
        {
          localObject1 = (Business)localHttpSession.getAttribute("Business");
          localObject2 = Entitlements.getChildrenByGroupType(((Business)localObject1).getEntitlementGroupIdValue(), "Business");
          if (((EntitlementGroups)localObject2).size() == 0)
          {
            this.WK = ((Business)localObject1).getEntitlementGroupId();
          }
          else
          {
            Iterator localIterator = ((EntitlementGroups)localObject2).iterator();
            if (localIterator.hasNext())
            {
              EntitlementGroup localEntitlementGroup = (EntitlementGroup)localIterator.next();
              this.WK = Integer.toString(localEntitlementGroup.getGroupId());
            }
          }
        }
        localHashMap.put("OBJECT_ID", this.WK);
      }
      Object localObject1 = (SecureUser)localHttpSession.getAttribute("SecureUser");
      localHttpSession.removeAttribute(this.WQ);
      Object localObject2 = null;
      if (this.WO != null) {
        localHashMap.put("TARGET_CURRENCY_CODE", this.WO);
      }
      if (this.WL == null) {
        localObject2 = FX.convertToBaseCurrency((SecureUser)localObject1, localCurrency, localHashMap);
      } else {
        localObject2 = FX.convertToBaseCurrency((SecureUser)localObject1, localCurrency, this.WL, localHashMap);
      }
      if (localObject2 != null) {
        localHttpSession.setAttribute(this.WQ, localObject2);
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
    return str1;
  }
  
  public String getTargetCurrencyCode()
  {
    return this.WO;
  }
  
  public void setTargetCurrencyCode(String paramString)
  {
    this.WO = paramString;
  }
  
  public String getObjectID()
  {
    return this.WK;
  }
  
  public void setObjectID(String paramString)
  {
    this.WK = paramString;
  }
  
  public int getObjectTypeValue()
  {
    return this.WR;
  }
  
  public String getObjectType()
  {
    return Integer.toString(this.WR);
  }
  
  public void setObjectType(String paramString)
  {
    this.WR = Integer.parseInt(paramString);
  }
  
  public void setFromAmount(String paramString)
  {
    this.WN = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fx.ConvertToBaseCurrency
 * JD-Core Version:    0.7.0.1
 */