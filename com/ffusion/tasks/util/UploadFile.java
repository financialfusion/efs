package com.ffusion.tasks.util;

import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UploadFile
  extends BaseTask
  implements Task
{
  protected String uploadFileName;
  protected String overWriteFile = "true";
  final int Qf = 512000;
  protected String uploadDirectory = "/upload/";
  public static final String UPLOAD_FILE_NAME = "UploadFileName";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    try
    {
      System.out.println("Start UploadFile");
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      ServletContext localServletContext = paramHttpServlet.getServletContext();
      String str1 = localServletContext.getRealPath("/");
      System.out.println("Docroot: " + str1);
      if ((str1.charAt(str1.length() - 1) == '\\') || (str1.charAt(str1.length() - 1) == '/')) {
        str1 = str1.substring(0, str1.length() - 1);
      }
      String str2 = str1 + this.uploadDirectory;
      System.out.println("uploadPath: " + str2);
      File localFile = new File(str2);
      if (!localFile.exists()) {
        localFile.mkdir();
      }
      String str3 = uploadFile(paramHttpServletRequest, paramHttpServletResponse, str2);
      if (str3.equals("")) {
        return this.taskErrorURL;
      }
      localHttpSession.setAttribute("UploadFileName", str3);
      return this.successURL;
    }
    catch (Exception localException)
    {
      System.out.println("UploadFile Exception: " + localException.getMessage());
    }
    return this.taskErrorURL;
  }
  
  public void setUploadFileName(String paramString)
  {
    this.uploadFileName = paramString;
  }
  
  public void setOverwrite(String paramString)
  {
    this.overWriteFile = paramString;
  }
  
  public String uploadFile(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString)
  {
    ServletInputStream localServletInputStream = null;
    FileOutputStream localFileOutputStream = null;
    try
    {
      String str1 = paramHttpServletRequest.getContentType();
      if ((str1 != null) && (str1.indexOf("multipart/form-data") != -1))
      {
        localServletInputStream = paramHttpServletRequest.getInputStream();
        int i = paramHttpServletRequest.getContentLength();
        byte[] arrayOfByte = new byte[i];
        int j = 0;
        int k = 0;
        int m = 0;
        while (k < i)
        {
          j = localServletInputStream.readLine(arrayOfByte, k, i);
          if (j == -1) {
            break;
          }
          k += j;
        }
        System.out.println("UploadFile: Total bytes read: " + k);
        String str4 = new String(arrayOfByte, "ISO8859_1");
        arrayOfByte = null;
        int n = str1.lastIndexOf("=");
        String str5 = str1.substring(n + 1, str1.length());
        String str6 = str4.substring(str4.indexOf("filename=\"") + 10);
        str6 = str6.substring(0, str6.indexOf("\n"));
        str6 = str6.substring(str6.lastIndexOf("\\") + 1, str6.indexOf("\""));
        int i1 = str4.indexOf("filename=\"");
        i1 = str4.indexOf("\n", i1) + 1;
        i1 = str4.indexOf("\n", i1) + 1;
        i1 = str4.indexOf("\n", i1) + 1;
        int i2 = str4.indexOf(str5, i1) - 4;
        str4 = str4.substring(i1, i2);
        String str7 = new String(paramString + str6);
        System.out.println("UploadFile - fileName: " + str7);
        File localFile = new File(str7);
        if ((localFile.exists()) && (!this.overWriteFile.equalsIgnoreCase("true")))
        {
          System.out.println("Upload file already exists.");
          localObject1 = "";
          return localObject1;
        }
        System.out.println("rootpath: " + paramString);
        Object localObject1 = new File(paramString);
        if (!((File)localObject1).exists()) {
          ((File)localObject1).mkdirs();
        }
        localFileOutputStream = new FileOutputStream(str7);
        localFileOutputStream.write(str4.getBytes("ISO8859_1"), 0, str4.length());
        String str8 = str7;
        return str8;
      }
      System.out.println("Request not multipart/form-data.");
      String str2 = "";
      return str2;
    }
    catch (Exception localException1)
    {
      try
      {
        System.out.println("Exception Error: " + localException1.getMessage());
        localException1.printStackTrace(System.out);
        System.out.println("Error in doPost: " + localException1);
        System.out.println("An unexpected error has occurred.");
        System.out.println("Error description: " + localException1);
      }
      catch (Exception localException2) {}
      String str3 = "";
      return str3;
    }
    finally
    {
      try
      {
        localFileOutputStream.close();
      }
      catch (Exception localException11) {}
      try
      {
        localServletInputStream.close();
      }
      catch (Exception localException12) {}
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.UploadFile
 * JD-Core Version:    0.7.0.1
 */