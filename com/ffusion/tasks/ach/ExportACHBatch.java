package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.util.TaskUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExportACHBatch
  extends BaseTask
{
  public static final String CT_TEXT = "text/plain";
  public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
  public static final String FILE_EXT_TEXT = ".txt";
  public static final String CD_ATTACHMENT_FILE = "attachment;filename=";
  private static final String UC = "\r\n";
  private static final String UD = "\n";
  protected String _batchID = null;
  protected String _batchName = "ACHBatch";
  protected String _batchesName = "ACHBatches";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ACHBatch localACHBatch = null;
    Object localObject1;
    if ((this._batchID != null) && (this._batchID.length() > 0))
    {
      localObject1 = (ACHBatches)localHttpSession.getAttribute(this._batchesName);
      if (localObject1 != null) {
        localACHBatch = ((ACHBatches)localObject1).getByID(this._batchID);
      }
    }
    if (localACHBatch == null) {
      localACHBatch = (ACHBatch)localHttpSession.getAttribute(this._batchName);
    }
    if (localACHBatch == null)
    {
      this.error = 16002;
      return this.taskErrorURL;
    }
    try
    {
      localObject1 = new HashMap();
      localObject2 = ACH.exportACHBatch(localSecureUser, localACHBatch, (HashMap)localObject1);
      String str1 = TaskUtil.getOSType(paramHttpServletRequest);
      String str2 = localObject2.toString();
      int i = 0;
      if (str2.indexOf("\r\n") != -1) {
        i = 1;
      }
      if ((str1.compareTo("Windows") != 0) && (i == 1)) {
        str2 = y(str2);
      } else if ((str1.compareTo("Windows") == 0) && (i == 0)) {
        str2 = x(str2);
      }
      paramHttpServletResponse.setContentType("text/plain");
      paramHttpServletResponse.setHeader("Content-Disposition", "attachment;filename=EXPORT.txt");
      ServletOutputStream localServletOutputStream = paramHttpServletResponse.getOutputStream();
      localServletOutputStream.println(str2);
      localServletOutputStream.flush();
      localServletOutputStream.close();
    }
    catch (CSILException localCSILException)
    {
      localCSILException = localCSILException;
      this.error = MapError.mapError(localCSILException, localHttpSession);
      Object localObject2 = super.getServiceErrorURL();
      return localObject2;
    }
    finally {}
    return super.getSuccessURL();
  }
  
  public final void setBatchID(String paramString)
  {
    this._batchID = paramString;
  }
  
  public final void setBatchName(String paramString)
  {
    this._batchName = paramString;
  }
  
  public final String getBatchName()
  {
    return this._batchName;
  }
  
  public final void setBatchesName(String paramString)
  {
    this._batchesName = paramString;
  }
  
  private static String y(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    return jdMethod_int(paramString, "\r\n", "\n");
  }
  
  private static String x(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    return jdMethod_int(paramString, "\n", "\r\n");
  }
  
  private static String jdMethod_int(String paramString1, String paramString2, String paramString3)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
    StringBuffer localStringBuffer = new StringBuffer();
    int i = localStringTokenizer.countTokens();
    if (i == 0) {
      return paramString1;
    }
    while (localStringTokenizer.hasMoreTokens())
    {
      localStringBuffer.append(localStringTokenizer.nextToken());
      localStringBuffer.append(paramString3);
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.ExportACHBatch
 * JD-Core Version:    0.7.0.1
 */