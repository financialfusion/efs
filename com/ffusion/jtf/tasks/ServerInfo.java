package com.ffusion.jtf.tasks;

import com.ffusion.jtf.Task;
import com.ffusion.util.DateFormatUtil;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServerInfo
  implements Task
{
  public static final String SESSION = "SESSION";
  public static final String SYSTEM = "SYSTEM";
  public static final String ATTRIBUTES = "ATTRIBUTES";
  public static final String PARAMETERS = "PARAMETERS";
  public static final String CONTEXT = "CONTEXT";
  public static final String SERVLETLOG = "SERVLETLOG";
  public static final String VIEWDIR = "VIEWDIR";
  public static final String VIEWJAR = "VIEWJAR";
  private String a5Z;
  private int a52;
  private String a5Y;
  private String a55;
  private String a54;
  private String a51;
  private String a53 = "                                ";
  private String a50 = "            ";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
    localPrintWriter.println("<html>");
    localPrintWriter.println("<head>");
    localPrintWriter.println("\t<title>ServerInfo</title>");
    localPrintWriter.println("</head>");
    localPrintWriter.println("<META HTTP-EQUIV=\"PRAGMA\" content=\"NO-CACHE\">");
    localPrintWriter.println("<META HTTP-EQUIV=\"CACHE-CONTROL\" content=\"NO-CACHE\">");
    localPrintWriter.println("<body topmargin=\"0\" marginheight=\"0\" marginwidth=\"0\" leftmargin=\"0\" bgcolor=\"#ffffff\" link=\"#000000\" vlink=\"#000000\" alink=\"#6666cc\">");
    localPrintWriter.println("<div align=\"left\"><!--textarea rows=\"20\" cols=\"70\" wrap=\"off\"-->");
    localPrintWriter.println("<xmp>");
    processServletEngine(paramHttpServlet, localPrintWriter);
    localPrintWriter.println();
    processSystem(paramHttpServletRequest, localPrintWriter);
    localPrintWriter.println();
    processInitParameters(paramHttpServlet, localPrintWriter);
    localPrintWriter.println("</xmp>");
    localPrintWriter.println("<!--/textarea--></div>");
    localPrintWriter.println("</body>");
    localPrintWriter.println("</html>");
    localPrintWriter.flush();
    return "";
  }
  
  public void processSession(HttpServletRequest paramHttpServletRequest)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      Enumeration localEnumeration = localHttpSession.getAttributeNames();
      ArrayList localArrayList = new ArrayList();
      if (localEnumeration != null) {
        while (localEnumeration.hasMoreElements()) {
          localArrayList.add(localEnumeration.nextElement());
        }
      }
      jdMethod_goto(localArrayList);
      localStringBuffer.append("Session Values:\n");
      for (int i = 0; i < localArrayList.size(); i++)
      {
        localStringBuffer.append((String)localArrayList.get(i));
        localStringBuffer.append("=");
        localStringBuffer.append(localHttpSession.getAttribute((String)localArrayList.get(i)));
        localStringBuffer.append("\n");
      }
      this.a54 = localStringBuffer.toString();
    }
    catch (Exception localException)
    {
      localStringBuffer.append("******* Unable to show all session values.  Exception while processing session. ******* ");
      this.a54 = localStringBuffer.toString();
    }
  }
  
  public void processSystem(HttpServletRequest paramHttpServletRequest, PrintWriter paramPrintWriter)
  {
    Enumeration localEnumeration = System.getProperties().propertyNames();
    ArrayList localArrayList = new ArrayList();
    if (localEnumeration != null) {
      while (localEnumeration.hasMoreElements()) {
        localArrayList.add((String)localEnumeration.nextElement());
      }
    }
    jdMethod_goto(localArrayList);
    paramPrintWriter.println("System Property Values");
    paramPrintWriter.flush();
    String str = null;
    for (int i = 0; i < localArrayList.size(); i++)
    {
      str = "   " + (String)localArrayList.get(i) + " = " + System.getProperties().getProperty((String)localArrayList.get(i));
      paramPrintWriter.println(str);
      paramPrintWriter.flush();
    }
  }
  
  public void processAttributes(HttpServletRequest paramHttpServletRequest)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Enumeration localEnumeration = paramHttpServletRequest.getAttributeNames();
    ArrayList localArrayList = new ArrayList();
    if (localEnumeration != null) {
      while (localEnumeration.hasMoreElements()) {
        localArrayList.add((String)localEnumeration.nextElement());
      }
    }
    jdMethod_goto(localArrayList);
    localStringBuffer.append("Request Attribute Values:\n");
    for (int i = 0; i < localArrayList.size(); i++)
    {
      localStringBuffer.append((String)localArrayList.get(i));
      localStringBuffer.append("=");
      localStringBuffer.append(paramHttpServletRequest.getAttribute((String)localArrayList.get(i)));
      localStringBuffer.append("\n");
    }
    this.a54 = localStringBuffer.toString();
  }
  
  public void processServletEngine(HttpServlet paramHttpServlet, PrintWriter paramPrintWriter)
  {
    paramPrintWriter.println();
    paramPrintWriter.println("Servlet Engine Info");
    String str = paramHttpServlet.getServletInfo();
    paramPrintWriter.println("   Servlet Info = " + paramHttpServlet.getServletInfo());
    ServletContext localServletContext = paramHttpServlet.getServletContext();
    ServletConfig localServletConfig = paramHttpServlet.getServletConfig();
    paramPrintWriter.println("   Servlet Context = " + localServletContext.toString());
    paramPrintWriter.println("   Servlet Major Version = " + localServletContext.getMajorVersion());
    paramPrintWriter.println("   Servlet Minor Version = " + localServletContext.getMinorVersion());
    paramPrintWriter.println();
  }
  
  public void processInitParameters(HttpServlet paramHttpServlet, PrintWriter paramPrintWriter)
  {
    paramPrintWriter.println();
    paramPrintWriter.println("Servlet Init Parameters");
    Enumeration localEnumeration = paramHttpServlet.getInitParameterNames();
    while (localEnumeration.hasMoreElements())
    {
      String str1 = (String)localEnumeration.nextElement();
      String str2 = paramHttpServlet.getInitParameter(str1);
      paramPrintWriter.println("   " + str1 + " = " + str2);
    }
    paramPrintWriter.println();
  }
  
  public void processContext(HttpServlet paramHttpServlet)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Enumeration localEnumeration = paramHttpServlet.getServletContext().getAttributeNames();
    ArrayList localArrayList = new ArrayList();
    if (localEnumeration != null) {
      while (localEnumeration.hasMoreElements()) {
        localArrayList.add((String)localEnumeration.nextElement());
      }
    }
    jdMethod_goto(localArrayList);
    localStringBuffer.append("Context Attribute Values:\n");
    for (int i = 0; i < localArrayList.size(); i++)
    {
      localStringBuffer.append((String)localArrayList.get(i));
      localStringBuffer.append("=");
      localStringBuffer.append(paramHttpServlet.getServletContext().getAttribute((String)localArrayList.get(i)));
      localStringBuffer.append("\n");
    }
    this.a54 = localStringBuffer.toString();
  }
  
  public void processViewDir(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      File localFile = new File(paramString);
      if (localFile.isDirectory())
      {
        File[] arrayOfFile = localFile.listFiles();
        for (int i = 0; i < arrayOfFile.length; i++)
        {
          Date localDate = new Date(arrayOfFile[i].lastModified());
          localStringBuffer.append(arrayOfFile[i].getName());
          localStringBuffer.append(this.a53.substring(0, this.a53.length() - arrayOfFile[i].getName().length()));
          localStringBuffer.append(this.a50.substring(0, this.a50.length() - String.valueOf(arrayOfFile[i].length()).length()));
          localStringBuffer.append(String.valueOf(arrayOfFile[i].length()));
          localStringBuffer.append("  ");
          localStringBuffer.append(DateFormatUtil.getFormatter("yyyy.MM.dd hh:mm:ss").format(localDate));
          localStringBuffer.append("\n");
        }
        this.a54 = localStringBuffer.toString();
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.a54 = ("Could not get directory:" + paramString);
    }
  }
  
  public void processViewJar(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      ZipFile localZipFile = new ZipFile(paramString);
      Enumeration localEnumeration = localZipFile.entries();
      int i = 0;
      if (localEnumeration != null) {
        while (localEnumeration.hasMoreElements())
        {
          ZipEntry localZipEntry1 = (ZipEntry)localEnumeration.nextElement();
          if (localZipEntry1.getName().length() > i) {
            i = localZipEntry1.getName().length();
          }
        }
      }
      for (int j = 0; j < i + 2; j++) {
        localStringBuffer.append(" ");
      }
      String str = localStringBuffer.toString();
      localStringBuffer.setLength(0);
      localEnumeration = localZipFile.entries();
      if (localEnumeration != null) {
        while (localEnumeration.hasMoreElements())
        {
          ZipEntry localZipEntry2 = (ZipEntry)localEnumeration.nextElement();
          Date localDate = new Date(localZipEntry2.getTime());
          localStringBuffer.append(localZipEntry2.getName());
          localStringBuffer.append(str.substring(0, str.length() - localZipEntry2.getName().length()));
          localStringBuffer.append(this.a50.substring(0, this.a50.length() - String.valueOf(localZipEntry2.getSize()).length()));
          localStringBuffer.append(String.valueOf(localZipEntry2.getSize()));
          localStringBuffer.append("  ");
          localStringBuffer.append(DateFormatUtil.getFormatter("yyyy.MM.dd hh:mm:ss").format(localDate));
          localStringBuffer.append("\n");
        }
      }
      this.a54 = localStringBuffer.toString();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.a54 = ("Could not find:" + paramString);
    }
  }
  
  private void jdMethod_goto(ArrayList paramArrayList)
  {
    int i = paramArrayList.size();
    for (int j = 0; j < i; j++)
    {
      Object localObject = (String)paramArrayList.get(j);
      for (int k = j; k < i; k++)
      {
        String str = (String)paramArrayList.get(k);
        if (str.compareTo((String)localObject) < 0)
        {
          paramArrayList.set(j, str);
          paramArrayList.set(k, localObject);
          localObject = str;
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.tasks.ServerInfo
 * JD-Core Version:    0.7.0.1
 */