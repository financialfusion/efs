package com.ffusion.tasks.fx;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.FX;
import com.ffusion.fx.FXException;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetNumDecimals
  extends BaseTask
{
  private String WS;
  private int WT;
  
  public void setCurrencyCode(String paramString)
  {
    this.WS = paramString;
  }
  
  private void jdMethod_char(int paramInt)
  {
    this.WT = paramInt;
  }
  
  public int getNumDecimals()
  {
    return this.WT;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HashMap localHashMap = new HashMap();
    try
    {
      int i = FX.getNumDecimals(this.WS, localHashMap);
      jdMethod_char(i);
    }
    catch (CSILException localCSILException)
    {
      str = this.taskErrorURL;
      this.error = localCSILException.getCode();
      if ((localCSILException.childException != null) && ((localCSILException.childException instanceof FXException))) {
        this.error = ((FXException)localCSILException.childException).getErrorCode();
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fx.GetNumDecimals
 * JD-Core Version:    0.7.0.1
 */