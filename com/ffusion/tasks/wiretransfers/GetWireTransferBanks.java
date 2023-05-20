package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.wiretransfers.WireTransferBank;
import com.ffusion.beans.wiretransfers.WireTransferBanks;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetWireTransferBanks
  extends BaseTask
  implements WireTaskDefines
{
  protected static boolean reload = false;
  protected String wireBanks = "WireTransferBanks";
  protected WireTransferBank searchBank = null;
  protected String bankLookupSettings = "BANK_LOOKUP_SETTINGS_MAXIMUM_MATCHES";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    WireTransferBanks localWireTransferBanks = (WireTransferBanks)localHttpSession.getAttribute(this.wireBanks);
    String str1 = this.successURL;
    this.error = 0;
    if (reload)
    {
      localWireTransferBanks = null;
      localHttpSession.removeAttribute(this.wireBanks);
      reload = false;
    }
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 12014;
      return this.taskErrorURL;
    }
    int i = 10;
    String str2 = (String)localHttpSession.getAttribute(this.bankLookupSettings);
    try
    {
      i = Integer.parseInt(str2);
    }
    catch (Exception localException) {}
    try
    {
      localWireTransferBanks = Wire.getWireTransferBanks(localSecureUser, this.searchBank, i, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    Collections.sort(localWireTransferBanks);
    localHttpSession.setAttribute(this.wireBanks, localWireTransferBanks);
    this.searchBank = new WireTransferBank();
    return str1;
  }
  
  public void setReload(String paramString)
  {
    reload = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setBanksName(String paramString)
  {
    this.wireBanks = paramString;
  }
  
  public void setBankName(String paramString)
  {
    this.searchBank.setBankName(paramString);
  }
  
  public String getBankName()
  {
    return this.searchBank.getBankName();
  }
  
  public void setBankCity(String paramString)
  {
    this.searchBank.setCity(paramString);
  }
  
  public String getBankCity()
  {
    return this.searchBank.getCity();
  }
  
  public void setBankState(String paramString)
  {
    this.searchBank.setState(paramString);
  }
  
  public String getBankState()
  {
    return this.searchBank.getState();
  }
  
  public void setBankCountry(String paramString)
  {
    this.searchBank.setCountry(paramString);
  }
  
  public String getBankCountry()
  {
    return this.searchBank.getCountry();
  }
  
  public void setFedRTN(String paramString)
  {
    this.searchBank.setRoutingFedWire(paramString);
  }
  
  public String getFedRTN()
  {
    return this.searchBank.getRoutingFedWire();
  }
  
  public void setSwiftRTN(String paramString)
  {
    this.searchBank.setRoutingSwift(paramString);
  }
  
  public String getSwiftRTN()
  {
    return this.searchBank.getRoutingSwift();
  }
  
  public void setChipsRTN(String paramString)
  {
    this.searchBank.setRoutingChips(paramString);
  }
  
  public String getChipsRTN()
  {
    return this.searchBank.getRoutingChips();
  }
  
  public void setOtherRTN(String paramString)
  {
    this.searchBank.setRoutingOther(paramString);
  }
  
  public String getOtherRTN()
  {
    return this.searchBank.getRoutingOther();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetWireTransferBanks
 * JD-Core Version:    0.7.0.1
 */