package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ResourceUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetStateNamesWithTaxForms
  extends ArrayList
  implements Task
{
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected int error = 0;
  private HashMap Ae;
  private String Ag;
  protected int type = -1;
  private final String Af = "State";
  private final String Ah = "com.ffusion.util.states";
  private final String Ad = "en_US";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    if (size() == 0)
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        if (this.type != -1) {
          localHashMap.put("TaxFormType", "" + this.type);
        }
        this.Ae = ACH.getStateNamesWithTaxForms(localSecureUser, localHashMap);
        if (!localSecureUser.getLocale().toString().equalsIgnoreCase("en_US")) {
          this.Ae = jdMethod_for(localSecureUser.getLocale(), this.Ae);
        }
        clear();
        addAll(this.Ae.keySet());
        Collections.sort(this);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
      }
      if (this.error != 0) {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  private HashMap jdMethod_for(Locale paramLocale, HashMap paramHashMap)
  {
    if (paramHashMap == null) {
      return null;
    }
    if (paramHashMap.isEmpty()) {
      return new HashMap();
    }
    HashMap localHashMap = new HashMap();
    Set localSet = paramHashMap.keySet();
    Iterator localIterator = localSet.iterator();
    String str1 = null;
    while (localIterator.hasNext())
    {
      str1 = (String)localIterator.next();
      String str2 = ResourceUtil.getString("State" + (String)paramHashMap.get(str1), "com.ffusion.util.states", paramLocale);
      localHashMap.put(str2, paramHashMap.get(str1));
    }
    return localHashMap;
  }
  
  public void setState(String paramString)
  {
    this.Ag = paramString;
  }
  
  public String getStateAbbr()
  {
    return (String)this.Ae.get(this.Ag);
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public String getType()
  {
    if (this.type == 2) {
      return "STATE";
    }
    if (this.type == 4) {
      return "CHILDSUPPORT";
    }
    return "";
  }
  
  public void setType(String paramString)
  {
    if (paramString.equalsIgnoreCase("STATE")) {
      this.type = 2;
    } else if (paramString.equalsIgnoreCase("CHILDSUPPORT")) {
      this.type = 4;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetStateNamesWithTaxForms
 * JD-Core Version:    0.7.0.1
 */