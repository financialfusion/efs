package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.dataconsolidator.BAITypeCodeInfo;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.DataConsolidator;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetTypeCodesForGroup
  extends BaseTask
  implements BusinessTask
{
  private String hE;
  private ArrayList hD = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      com.ffusion.beans.business.Business localBusiness = (com.ffusion.beans.business.Business)localHttpSession.getAttribute("Business");
      int i = localBusiness.getIdValue();
      HashMap localHashMap = new HashMap();
      String str2 = com.ffusion.csil.core.Business.getTypeCodesForGroup(localSecureUser, i, this.hE, localHashMap);
      this.hD = new ArrayList();
      StringTokenizer localStringTokenizer = new StringTokenizer(str2, ",");
      while (localStringTokenizer.hasMoreTokens())
      {
        int j = Integer.parseInt(localStringTokenizer.nextToken());
        BAITypeCodeInfo localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(j);
        this.hD.add(localBAITypeCodeInfo);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 47;
    }
    if (this.error == 0) {
      str1 = this.successURL;
    }
    return str1;
  }
  
  public void setTransactionGroup(String paramString)
  {
    this.hE = paramString;
  }
  
  public ArrayList getCodeInfoList()
  {
    if (this.hD == null) {
      return new ArrayList();
    }
    return this.hD;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.GetTypeCodesForGroup
 * JD-Core Version:    0.7.0.1
 */