package com.ffusion.tasks.banklookup;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.FileImporter;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoadBankInformation
  extends BaseTask
  implements BankLookupTask
{
  public static String FEDERAL_RESERVE_ACH_ABA_LISTING = "Federal Reserve ACH ABA Listing";
  public static String FEDERAL_RESERVE_WIRE_ABA_LISTING = "Federal Reserve Wire ABA Listing";
  public static String SWIFT_BIC_DIRECTORY = "SWIFT BIC Directory";
  public static String SWIFT_BIC_DIRECTORY_UPDATE = "SWIFT BIC Directory Update";
  private String FL = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      FileImporter.processFiles(localSecureUser, this.FL, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      str = this.successURL;
    }
    return str;
  }
  
  public void setFileType(String paramString)
  {
    this.FL = paramString;
  }
  
  public String getFileType()
  {
    return this.FL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banklookup.LoadBankInformation
 * JD-Core Version:    0.7.0.1
 */