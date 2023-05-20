package com.ffusion.tasks.fileImport;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fileimporter.MappingDefinition;
import com.ffusion.beans.fileimporter.OutputFormat;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.FileImporter;
import com.ffusion.csil.handlers.FileImporterHandler;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FileUploadTask
  extends BaseTask
{
  private static final String P8 = "filename=\"";
  private static final String P5 = "Content-Type:";
  private static final String P6 = "UTF-8";
  private static final int P7 = 131072;
  public static final int DEFAULT_FILE_MAX_SIZE = 4194304;
  protected String _fileType = null;
  protected String _transactionsOnly = null;
  protected String _strictACHValidation = null;
  protected int _maxFileSize = 4194304;
  protected boolean processFlag = true;
  
  public void setFileType(String paramString)
  {
    this._fileType = paramString;
  }
  
  public String getFileType()
  {
    return this._fileType;
  }
  
  public void setTransactionsOnly(String paramString)
  {
    this._transactionsOnly = paramString;
  }
  
  public void setStrictACHValidation(String paramString)
  {
    this._strictACHValidation = paramString;
  }
  
  public int getMaxFileSize()
  {
    return this._maxFileSize;
  }
  
  public void setMaxFileSize(int paramInt)
  {
    this._maxFileSize = paramInt;
  }
  
  public void setMaxFileSize(String paramString)
  {
    this._maxFileSize = Integer.parseInt(paramString);
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public int validateInput(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    MappingDefinition localMappingDefinition = null;
    localMappingDefinition = (MappingDefinition)localHttpSession.getAttribute("MappingDefinition");
    if (((localMappingDefinition == null) || (localMappingDefinition.getMappingID() == 0)) && (this._fileType.equals("Custom"))) {
      this.error = 75;
    }
    localMappingDefinition = null;
    return 0;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ServletInputStream localServletInputStream = null;
    this.error = validateInput(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
    if (this.error != 0) {
      return super.getTaskErrorURL();
    }
    if (!this.processFlag) {
      return super.getSuccessURL();
    }
    String str1 = paramHttpServletRequest.getContentType();
    if ((str1 == null) || (str1.indexOf("multipart/form-data") < 0))
    {
      this.error = 55;
      return super.getTaskErrorURL();
    }
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(131072);
    int i = 0;
    try
    {
      localServletInputStream = paramHttpServletRequest.getInputStream();
      if (localServletInputStream == null)
      {
        this.error = 58;
        return super.getTaskErrorURL();
      }
      i = paramHttpServletRequest.getContentLength();
      if (i > this._maxFileSize)
      {
        this.error = 57;
        return super.getTaskErrorURL();
      }
      if (i < 0) {
        i = 0;
      }
      byte[] arrayOfByte1 = new byte[131072];
      while (i < this._maxFileSize)
      {
        int j = localServletInputStream.read(arrayOfByte1);
        if (j <= 0) {
          break;
        }
        localByteArrayOutputStream.write(arrayOfByte1, 0, j);
        i += j;
      }
      arrayOfByte1 = null;
      if (i >= this._maxFileSize)
      {
        if (localServletInputStream.read() != -1)
        {
          this.error = 57;
          return super.getTaskErrorURL();
        }
      }
      else if (i <= 0)
      {
        this.error = 59;
        return super.getTaskErrorURL();
      }
    }
    catch (IOException localIOException)
    {
      this.error = 54;
      return super.getTaskErrorURL();
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      localByteArrayOutputStream = null;
      this.error = 56;
      return super.getTaskErrorURL();
    }
    String str2 = null;
    String str3 = new String(localByteArrayOutputStream.toByteArray(), "UTF-8");
    localByteArrayOutputStream = null;
    int k = -1;
    int m = -1;
    k = str3.indexOf("\n");
    if (k <= 0)
    {
      this.error = 60;
      return super.getTaskErrorURL();
    }
    String str4 = str3.substring(0, k + 1);
    k = str3.indexOf("filename=\"");
    if (k < 0)
    {
      this.error = 60;
      return super.getTaskErrorURL();
    }
    k += "filename=\"".length();
    m = str3.indexOf("\"", k);
    if (m < k)
    {
      this.error = 60;
      return super.getTaskErrorURL();
    }
    str2 = str3.substring(k, m);
    if (str2.length() <= 0)
    {
      this.error = 61;
      return super.getTaskErrorURL();
    }
    for (int n = str2.length() - 1; n >= 0; n--)
    {
      int i1 = str2.charAt(n);
      if ((i1 == 47) || (i1 == 92))
      {
        str2 = str2.endsWith("\"") ? str2.substring(n + 1, str2.length() - 1) : str2.substring(n + 1);
        break;
      }
    }
    k = str3.indexOf("\n", str3.indexOf("Content-Type:", m));
    if (k < 0)
    {
      this.error = 60;
      return super.getTaskErrorURL();
    }
    k++;
    if (str3.startsWith("\n", k)) {
      k++;
    } else if (str3.startsWith("\r\n", k)) {
      k += "\r\n".length();
    }
    m = str3.indexOf(str4, k);
    if (m <= k)
    {
      this.error = 60;
      return super.getTaskErrorURL();
    }
    if (str3.charAt(m - 1) == '\n')
    {
      m--;
      if (str3.charAt(m - 1) == '\r') {
        m--;
      }
      if (m <= k)
      {
        this.error = 60;
        return super.getTaskErrorURL();
      }
    }
    str3 = str3.substring(k, m);
    byte[] arrayOfByte2 = str3.getBytes("UTF-8");
    str3 = null;
    try
    {
      HashMap localHashMap1 = new HashMap();
      MappingDefinition localMappingDefinition = (MappingDefinition)localHttpSession.getAttribute("MappingDefinition");
      OutputFormat localOutputFormat = null;
      ArrayList localArrayList = new ArrayList();
      if ((localMappingDefinition != null) && (localMappingDefinition.getMappingID() != 0))
      {
        localHashMap1.put("MappingName", localMappingDefinition.getName());
        localHashMap1.put("MappingDefinition", localMappingDefinition);
        localOutputFormat = FileImporterHandler.getOutputFormat(localSecureUser, localMappingDefinition.getOutputFormatName(), localHashMap1);
        if (localOutputFormat != null) {
          localArrayList = localOutputFormat.getCategories();
        }
      }
      else if (this._fileType.equals("Custom"))
      {
        this.error = 75;
        return super.getTaskErrorURL();
      }
      localHashMap1.put("BankId", localHttpSession.getAttribute("BankId"));
      localHashMap1.put("ProductName", localHttpSession.getAttribute("product"));
      if (localHttpSession.getAttribute("ACHCOMPANIES") != null) {
        localHashMap1.put("ACHCOMPANIES", localHttpSession.getAttribute("ACHCOMPANIES"));
      }
      if (localHttpSession.getAttribute("ACHCOMPANY") != null) {
        localHashMap1.put("ACHCOMPANY", localHttpSession.getAttribute("ACHCOMPANY"));
      }
      if (this._transactionsOnly != null) {
        localHashMap1.put("transactionsOnly", Boolean.valueOf(this._transactionsOnly).toString());
      }
      if (this._strictACHValidation != null) {
        localHashMap1.put("strictACHValidation", Boolean.valueOf(this._strictACHValidation).toString());
      }
      if (((this._fileType.equals("Transfer Import")) || (("Custom".equalsIgnoreCase(this._fileType)) && (localArrayList.contains("TRANSFER")))) && (localHttpSession.getAttribute("TransferAccounts") != null)) {
        localHashMap1.put("TransferAccounts", localHttpSession.getAttribute("TransferAccounts"));
      }
      if ((this._fileType.equals("Payment Import")) || (("Custom".equalsIgnoreCase(this._fileType)) && (localArrayList.contains("PAYMENT"))))
      {
        if (localHttpSession.getAttribute("BillPayAccounts") != null) {
          localHashMap1.put("BillPayAccounts", localHttpSession.getAttribute("BillPayAccounts"));
        }
        if (localHttpSession.getAttribute("Payees") != null) {
          localHashMap1.put("Payees", localHttpSession.getAttribute("Payees"));
        }
      }
      String str5 = paramHttpServletRequest.getRemoteHost();
      localHashMap1.put("HOST_NAME", str5);
      HashMap localHashMap2 = FileImporter.upload(localSecureUser, arrayOfByte2, this._fileType, str2, localHashMap1);
      if (localHashMap1.get("ImportErrors") != null) {
        localHttpSession.setAttribute("ImportErrors", localHashMap1.get("ImportErrors"));
      }
      localHttpSession.setAttribute("UploadResults", localHashMap2);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, localHttpSession);
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  private static void s(HttpSession paramHttpSession)
  {
    System.out.println("Dump of session attributes:");
    Enumeration localEnumeration = paramHttpSession.getAttributeNames();
    ArrayList localArrayList = new ArrayList();
    while (localEnumeration.hasMoreElements()) {
      localArrayList.add(localEnumeration.nextElement());
    }
    Collections.sort(localArrayList);
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject = paramHttpSession.getAttribute(str);
      System.out.println("  " + str + ": " + localObject);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fileImport.FileUploadTask
 * JD-Core Version:    0.7.0.1
 */