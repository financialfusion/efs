package com.ffusion.ims21.servlet;

import com.ffusion.util.logging.DebugLog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImsServlet
  extends HttpServlet
{
  String jdField_do = "";
  String jdField_null = "";
  ServletConfig j = null;
  String jdField_void = "";
  static final String jdField_byte = "PutBin";
  static final String jdField_for = "GetStats";
  static final String k = "GetID";
  static final String jdField_try = "GET_IMSPATH";
  static final String g = "GET_IPMASK";
  static final String a = "BID";
  static final String d = "IMSPATH";
  static final String jdField_int = "IPMASK";
  static final String jdField_long = "FNAME=";
  static final String h = "REMOVEFILE";
  static final String[] b = { ".GIF", ".JPG", ".BMP", ".BIN", ".RTP", ".TXT", ".HTM", ".HTML" };
  static String f = "VER=";
  static String jdField_new = "CLEAR";
  static String i = ".DAT";
  static int jdField_goto = 8;
  static final int jdField_char = 25;
  static final int jdField_if = 25;
  private String c = null;
  private String jdField_else = null;
  private String jdField_case = null;
  private String e = null;
  
  public void init(ServletConfig paramServletConfig)
    throws ServletException
  {
    super.init(paramServletConfig);
    this.j = paramServletConfig;
    this.jdField_do = ensureSlash(paramServletConfig.getInitParameter("IMSPATH"));
    this.jdField_null = paramServletConfig.getInitParameter("IPMASK");
  }
  
  public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {
    if (this.jdField_do == null)
    {
      paramHttpServletResponse.setContentType("text/html; charset=UTF-8");
      paramHttpServletResponse.sendError(404);
      return;
    }
    paramHttpServletResponse.setHeader("Pragma", "no-cache");
    if ((paramHttpServletRequest.getPathInfo() != null) && (paramHttpServletRequest.getPathInfo().equalsIgnoreCase("/ping")))
    {
      a(paramHttpServletResponse, "Servlet Ping<br><br>BID=" + this.c + "<br>ImsPath=" + this.jdField_do);
      return;
    }
    if ((paramHttpServletRequest.getQueryString() != null) && (paramHttpServletRequest.getQueryString().length() > 0))
    {
      try
      {
        Object localObject;
        if (paramHttpServletRequest.getQueryString().indexOf("GET_IMSPATH") != -1)
        {
          paramHttpServletResponse.setStatus(200);
          paramHttpServletResponse.setContentType("text/plain; charset=UTF-8");
          localObject = paramHttpServletResponse.getWriter();
          ((PrintWriter)localObject).println(this.jdField_do);
          ((PrintWriter)localObject).flush();
          ((PrintWriter)localObject).close();
        }
        else if (paramHttpServletRequest.getQueryString().indexOf("GET_IPMASK") != -1)
        {
          paramHttpServletResponse.setStatus(200);
          paramHttpServletResponse.setContentType("text/plain; charset=UTF-8");
          localObject = paramHttpServletResponse.getWriter();
          ((PrintWriter)localObject).println(this.jdField_null);
          ((PrintWriter)localObject).flush();
          ((PrintWriter)localObject).close();
        }
        else
        {
          localObject = paramHttpServletRequest.getPathInfo();
          int m = 200;
          if (((String)localObject).indexOf("PutBin") != -1)
          {
            m = jdField_if(paramHttpServletRequest, paramHttpServletResponse);
          }
          else if (((String)localObject).indexOf("GetStats") != -1)
          {
            m = a(paramHttpServletRequest, paramHttpServletResponse);
          }
          else if (((String)localObject).indexOf("GetID") != -1)
          {
            m = jdField_do(paramHttpServletRequest, paramHttpServletResponse);
          }
          else
          {
            paramHttpServletResponse.sendError(404, "Unknown command");
            return;
          }
          if ((m == 200) && (((String)localObject).indexOf("GetID") == -1)) {
            paramHttpServletResponse.setStatus(m);
          }
        }
      }
      catch (IOException localIOException)
      {
        paramHttpServletResponse.setContentType("text/html; charset=UTF-8");
        paramHttpServletResponse.sendError(500);
        return;
      }
      return;
    }
    paramHttpServletResponse.setContentType("text/html; charset=UTF-8");
    paramHttpServletResponse.sendError(405, "You must Post Data");
  }
  
  public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {
    if (this.jdField_do == null)
    {
      paramHttpServletResponse.setContentType("text/html; charset=UTF-8");
      paramHttpServletResponse.sendError(404);
      return;
    }
    if (paramHttpServletRequest.getPathInfo().length() < 2)
    {
      paramHttpServletResponse.sendError(404, "No command");
      return;
    }
    paramHttpServletResponse.setHeader("Pragma", "no-cache");
    String str = paramHttpServletRequest.getPathInfo();
    int m = 200;
    DebugLog.log("ImsServlet: cmd=" + str);
    if (str.indexOf("PutBin") != -1)
    {
      m = jdField_if(paramHttpServletRequest, paramHttpServletResponse);
    }
    else if (str.indexOf("GetStats") != -1)
    {
      m = a(paramHttpServletRequest, paramHttpServletResponse);
    }
    else if (str.indexOf("GetID") != -1)
    {
      m = jdField_do(paramHttpServletRequest, paramHttpServletResponse);
    }
    else
    {
      if (paramHttpServletRequest.getPathInfo().equalsIgnoreCase("/ping"))
      {
        a(paramHttpServletResponse, "Servlet Ping<br><br>BID=" + this.c + "<br>ImsPath=" + this.jdField_do);
        return;
      }
      paramHttpServletResponse.sendError(404, "Unknown command");
      return;
    }
    if ((m == 200) && (str.indexOf("GetID") == -1)) {
      paramHttpServletResponse.setStatus(m);
    }
  }
  
  private int jdField_if(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {
    if (!jdField_for(paramHttpServletRequest.getRemoteAddr())) {
      return 403;
    }
    if (!jdField_int(paramHttpServletRequest.getQueryString())) {
      return 404;
    }
    String str = jdField_new(paramHttpServletRequest.getQueryString());
    if (!jdField_do(str)) {
      return 404;
    }
    if (jdField_try(paramHttpServletRequest.getQueryString()) == true)
    {
      if (!jdField_if(this.jdField_do + str)) {
        return 400;
      }
      return 200;
    }
    DebugLog.log("ImsServlet.doPutBin: " + str);
    if (!a(this.jdField_do + str, paramHttpServletRequest.getInputStream())) {
      return 404;
    }
    return 200;
  }
  
  private boolean jdField_int(String paramString)
  {
    return (paramString != null) && (paramString.indexOf("BID") != -1);
  }
  
  private String jdField_new(String paramString)
  {
    String str = "";
    if (paramString == null) {
      return null;
    }
    int m = paramString.indexOf("FNAME=");
    str = paramString.substring(m + 6).trim();
    return str;
  }
  
  private boolean jdField_do(String paramString)
  {
    if (paramString == null) {
      return false;
    }
    int m = paramString.indexOf(".");
    if (m == 0) {
      return false;
    }
    paramString = paramString.substring(m);
    if (paramString.length() > 5) {
      return false;
    }
    for (int n = 0; n < b.length; n++) {
      if (paramString.equalsIgnoreCase(b[n]) == true) {
        return true;
      }
    }
    return false;
  }
  
  private boolean jdField_try(String paramString)
  {
    return (paramString != null) && (paramString.indexOf("REMOVEFILE") != -1);
  }
  
  private boolean jdField_if(String paramString)
  {
    File localFile = new File(paramString);
    if (localFile.isFile() == true) {
      return localFile.delete();
    }
    return false;
  }
  
  private boolean a(String paramString, ServletInputStream paramServletInputStream)
  {
    if (paramServletInputStream == null) {
      return false;
    }
    int m = 0;
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString);
      if (localFileOutputStream == null) {
        return false;
      }
      while ((m = paramServletInputStream.read()) != -1) {
        localFileOutputStream.write(m);
      }
      localFileOutputStream.flush();
      paramServletInputStream.close();
      localFileOutputStream.close();
    }
    catch (IOException localIOException)
    {
      return false;
    }
    return true;
  }
  
  private int a(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {
    if (!jdField_for(paramHttpServletRequest.getRemoteAddr())) {
      return 403;
    }
    if (!jdField_if(paramHttpServletRequest)) {
      return 400;
    }
    if (jdField_do(paramHttpServletRequest))
    {
      if (a()) {
        return 200;
      }
      return 500;
    }
    if (!a(paramHttpServletRequest)) {
      return 400;
    }
    this.jdField_case = a(false);
    this.e = a(true);
    if (!a(this.jdField_case))
    {
      if (!a(this.e)) {
        return 404;
      }
    }
    else
    {
      if (!a(this.jdField_case, this.e)) {
        return 500;
      }
      if (!jdField_if(this.jdField_case)) {
        return 500;
      }
    }
    a(this.e, paramHttpServletResponse);
    return 200;
  }
  
  private int jdField_do(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {
    int m = 200;
    String str1 = null;
    str1 = paramHttpServletRequest.getQueryString();
    if (str1 != null)
    {
      String str2 = this.jdField_do;
      int n = str1.indexOf("BID");
      str1 = str1.substring(n + 4);
      str2 = str2 + str1 + ".bin";
      FileInputStream localFileInputStream = null;
      try
      {
        localFileInputStream = new FileInputStream(str2);
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        paramHttpServletResponse.sendError(404, str2);
        return 404;
      }
      if (localFileInputStream != null)
      {
        paramHttpServletResponse.setContentType("application/octet-stream");
        ServletOutputStream localServletOutputStream = paramHttpServletResponse.getOutputStream();
        try
        {
          int i1 = 0;
          while ((i1 = localFileInputStream.read()) != -1) {
            localServletOutputStream.print((char)i1);
          }
        }
        catch (IOException localIOException)
        {
          localServletOutputStream.flush();
          localServletOutputStream.close();
          return 409;
        }
        localFileInputStream.close();
        localServletOutputStream.flush();
        localServletOutputStream.close();
      }
    }
    else
    {
      m = 404;
    }
    return m;
  }
  
  private boolean jdField_if(HttpServletRequest paramHttpServletRequest)
  {
    String str = paramHttpServletRequest.getQueryString();
    int m = str.lastIndexOf("BID");
    if (m == -1) {
      return false;
    }
    m += "BID".length() + 1;
    this.c = str.substring(m);
    int n = this.c.indexOf(",");
    if (n != -1) {
      this.c = this.c.substring(0, n);
    }
    int i1 = this.c.indexOf("/");
    if (i1 != -1) {
      this.c = this.c.substring(0, i1);
    }
    return true;
  }
  
  private boolean a(HttpServletRequest paramHttpServletRequest)
  {
    String str = paramHttpServletRequest.getQueryString();
    int m = str.lastIndexOf(f);
    if (m == -1) {
      return false;
    }
    m += f.length();
    this.jdField_else = str.substring(m);
    int n = this.jdField_else.indexOf(",");
    if (n != -1) {
      this.jdField_else = this.jdField_else.substring(0, n);
    }
    int i1 = this.jdField_else.indexOf("/");
    if (i1 != -1) {
      this.jdField_else = this.jdField_else.substring(0, i1);
    }
    return true;
  }
  
  private boolean jdField_do(HttpServletRequest paramHttpServletRequest)
  {
    return paramHttpServletRequest.getQueryString().lastIndexOf(jdField_new) != -1;
  }
  
  private String a(boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer(ensureSlash(this.jdField_do));
    localStringBuffer.append("ST").append(this.c);
    if (paramBoolean) {
      localStringBuffer.append(".").append(this.jdField_else);
    } else {
      localStringBuffer.append(i);
    }
    return localStringBuffer.toString();
  }
  
  public String ensureSlash(String paramString)
  {
    int m = paramString.lastIndexOf("\\");
    int n = paramString.lastIndexOf("/");
    int i1 = m > n ? m : n;
    if (i1 != paramString.length() - 1) {
      if (m > n) {
        paramString = paramString + "\\";
      } else {
        paramString = paramString + "/";
      }
    }
    return paramString;
  }
  
  private boolean a(String paramString)
  {
    File localFile = new File(paramString);
    return (localFile.exists()) && (!localFile.isDirectory());
  }
  
  private boolean jdField_if(String paramString1, String paramString2)
  {
    boolean bool = true;
    File localFile1 = new File(paramString1);
    File localFile2 = new File(paramString2);
    try
    {
      localFile2.renameTo(localFile1);
    }
    catch (SecurityException localSecurityException)
    {
      bool = false;
    }
    return bool;
  }
  
  private boolean a(String paramString, HttpServletResponse paramHttpServletResponse)
  {
    File localFile = new File(paramString);
    try
    {
      if ((localFile == null) || (!localFile.canRead())) {
        return false;
      }
    }
    catch (SecurityException localSecurityException)
    {
      return false;
    }
    boolean bool = true;
    FileInputStream localFileInputStream = null;
    try
    {
      localFileInputStream = new FileInputStream(localFile);
      int m = 1024;
      byte[] arrayOfByte = new byte[m];
      PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
      while (localFileInputStream.read(arrayOfByte) != -1)
      {
        String str = new String(arrayOfByte);
        localPrintWriter.print(str);
      }
      localPrintWriter.flush();
      localPrintWriter.close();
    }
    catch (Exception localException1)
    {
      bool = false;
    }
    finally
    {
      try
      {
        if (localFileInputStream != null) {
          localFileInputStream.close();
        }
      }
      catch (Exception localException2) {}
    }
    return bool;
  }
  
  private boolean a()
  {
    String str1 = new String(this.jdField_do);
    str1 = ensureSlash(str1);
    File localFile1 = new File(str1);
    String[] arrayOfString = localFile1.list();
    String str2 = null;
    if (arrayOfString == null) {
      return true;
    }
    for (int m = 0; m < arrayOfString.length; m++)
    {
      str2 = new String(this.jdField_do);
      str2 = ensureSlash(str2);
      str2 = str2 + arrayOfString[m];
      if ((str2.lastIndexOf(i) <= 0) && (str2.lastIndexOf("ST" + this.c) != -1)) {
        try
        {
          File localFile2 = new File(str2);
          localFile2.delete();
        }
        catch (Exception localException)
        {
          return false;
        }
      }
    }
    return true;
  }
  
  private boolean a(String paramString1, String paramString2)
  {
    File localFile1 = new File(paramString1);
    File localFile2 = new File(paramString2);
    FileInputStream localFileInputStream = null;
    FileOutputStream localFileOutputStream = null;
    boolean bool = true;
    try
    {
      if ((!localFile1.exists()) || (!localFile1.canRead())) {
        bool = false;
      }
    }
    catch (Exception localException1)
    {
      bool = false;
    }
    try
    {
      if ((localFile2.exists()) && (!localFile2.canWrite())) {
        bool = false;
      }
    }
    catch (Exception localException2)
    {
      bool = false;
    }
    try
    {
      byte[] arrayOfByte = new byte[1024];
      localFileInputStream = new FileInputStream(localFile1);
      localFileOutputStream = new FileOutputStream(localFile2);
      while (localFileInputStream.read(arrayOfByte) > 0) {
        localFileOutputStream.write(arrayOfByte);
      }
    }
    catch (Exception localException3)
    {
      bool = false;
    }
    finally
    {
      try
      {
        if (localFileInputStream != null) {
          localFileInputStream.close();
        }
        if (localFileOutputStream != null) {
          localFileOutputStream.close();
        }
      }
      catch (Exception localException4) {}
    }
    return bool;
  }
  
  private boolean jdField_if(String paramString, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    boolean bool = false;
    int m = this.jdField_null.indexOf("*");
    if (m == -1)
    {
      paramHttpServletResponse.setContentType("text/html; charset=UTF-8");
      paramHttpServletResponse.sendError(403, paramString);
      if (this.jdField_null.equals(paramString)) {
        bool = true;
      }
    }
    else
    {
      String str = this.jdField_null.substring(0, m);
      paramHttpServletResponse.setContentType("text/html; charset=UTF-8");
      paramHttpServletResponse.sendError(200, str);
      if (paramString.indexOf(str) != -1) {
        bool = true;
      }
      paramHttpServletResponse.setContentType("text/plain; charset=UTF-8");
      PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
      localPrintWriter.println("RemoteIpAddr = " + paramString);
      localPrintWriter.println("indexOf = " + String.valueOf(paramString.indexOf(str)));
    }
    return bool;
  }
  
  private boolean jdField_for(String paramString)
  {
    boolean bool = false;
    if (this.jdField_null == null)
    {
      bool = true;
    }
    else
    {
      int m = this.jdField_null.indexOf("*");
      if (m == -1)
      {
        if (this.jdField_null.equals(paramString)) {
          bool = true;
        }
      }
      else
      {
        String str = this.jdField_null.substring(0, m);
        if (paramString.indexOf(str) != -1) {
          bool = true;
        }
      }
    }
    return bool;
  }
  
  private void a(HttpServletResponse paramHttpServletResponse, String paramString)
  {
    try
    {
      PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
      paramHttpServletResponse.setContentType("text/html; charset=UTF-8");
      paramHttpServletResponse.setHeader("Pragma", "no-cache");
      localPrintWriter.println("<html><head><title>ImsBanner Message</title></head>");
      localPrintWriter.println("<body>");
      localPrintWriter.println(paramString);
      localPrintWriter.println("</body></html>");
      localPrintWriter.flush();
      localPrintWriter.close();
    }
    catch (IOException localIOException) {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.ims21.servlet.ImsServlet
 * JD-Core Version:    0.7.0.1
 */