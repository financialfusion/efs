package com.ffusion.tasks.fx;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.CurrencyUtil;
import com.ffusion.csil.core.FX;
import com.ffusion.fx.FXException;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCurrencyNumDecimals
  extends BaseTask
{
  private String Xf;
  private int Xi;
  private String Xh;
  private HashMap Xg = new HashMap();
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    setBusinessID(localSecureUser.getBusinessID());
    String str2 = CurrencyUtil.getCurrencyCodeByBusinessId(this.Xi);
    try
    {
      if (str2 != null)
      {
        setCurrencyCode(str2);
        int i = FX.getNumDecimals(str2, this.Xg);
        jdMethod_else(i);
      }
    }
    catch (CSILException localCSILException)
    {
      str1 = this.taskErrorURL;
      this.error = localCSILException.getCode();
      if ((localCSILException.childException != null) && ((localCSILException.childException instanceof FXException))) {
        this.error = ((FXException)localCSILException.childException).getErrorCode();
      }
    }
    return str1;
  }
  
  public String getNumDecimals()
  {
    return this.Xh;
  }
  
  private void jdMethod_else(int paramInt)
  {
    this.Xh = ("" + paramInt);
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.Xf = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.Xf;
  }
  
  public void setBusinessID(String paramString)
  {
    if (paramString != null) {
      this.Xi = Integer.parseInt(paramString);
    }
  }
  
  public void setBusinessID(int paramInt)
  {
    this.Xi = paramInt;
  }
  
  public String getBusinessID()
  {
    return "" + this.Xi;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fx.GetCurrencyNumDecimals
 * JD-Core Version:    0.7.0.1
 */