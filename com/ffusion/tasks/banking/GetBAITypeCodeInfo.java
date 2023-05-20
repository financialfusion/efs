package com.ffusion.tasks.banking;

import com.ffusion.beans.dataconsolidator.BAITypeCodeInfo;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.DataConsolidator;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBAITypeCodeInfo
  extends BaseTask
{
  private String aQe = "";
  private BAITypeCodeInfo aQc = null;
  private Locale aQd = null;
  
  public void setTypeCode(String paramString)
  {
    this.aQe = paramString;
    this.aQc = null;
    this.aQd = null;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    int i;
    try
    {
      i = Integer.parseInt(this.aQe);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 47;
      return this.taskErrorURL;
    }
    try
    {
      this.aQc = DataConsolidator.getBAITypeCodeInfo(i);
    }
    catch (CSILException localCSILException)
    {
      this.aQc = null;
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    this.aQd = ((Locale)paramHttpServletRequest.getSession().getAttribute("java.util.Locale"));
    return this.successURL;
  }
  
  public BAITypeCodeInfo getTypeCodeInfo()
  {
    if (this.aQc == null)
    {
      this.error = 63;
      return null;
    }
    return this.aQc;
  }
  
  public String getDescription()
  {
    if (this.aQc == null)
    {
      this.error = 63;
      return "";
    }
    return this.aQc.getDescription(this.aQd);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetBAITypeCodeInfo
 * JD-Core Version:    0.7.0.1
 */