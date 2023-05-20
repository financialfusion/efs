package com.ffusion.jtf;

import com.ffusion.csil.core.Initialize;
import com.ffusion.jtf.aio.AIO;
import com.ffusion.jtf.tasks.Verbose;
import com.ffusion.jtf.template.TemplateAction;
import com.ffusion.jtf.template.TemplateCache;
import com.ffusion.jtf.template.TemplateCacheManager;
import com.ffusion.jtf.template.TemplateParseException;
import com.ffusion.util.HTMLUtil;
import com.ffusion.util.Reflection;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import com.microstar.xml.HandlerBase;
import com.microstar.xml.XmlParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class JTF
  extends HttpServlet
{
  private static final String jdField_case = "Task";
  private static final String jdField_goto = "Template";
  private static final String O = "FileNotFound";
  private static final String jdField_void = ".jtf";
  private static final String N = "jtfCacheKey";
  public static final String JTF_TEMPLATE_CACHE = "jtfTemplateCache";
  private boolean h = false;
  private static final String b = "initURL";
  private static final String L = "errorURL";
  private static final String jdField_for = "invalidRequestURL";
  private static final String R = "authenticateObject";
  private static final String v = "authenticateURL";
  private static final String l = "extensions";
  private static final String P = "loginPages";
  private static final String j = "useCache";
  private static final String A = "encodeRedirectURL";
  private static final String jdField_int = "locale";
  private static final String C = "maxLoops";
  private static final String d = "encodeCharacters";
  private static final String Q = "GetVersion";
  private static final String jdField_long = "ClearCache";
  private static final String jdField_if = "CacheOn";
  private static final String o = "CacheOff";
  private static final String y = "Verbose";
  private static final String S = "ServerInfo";
  private static final String c = "Monitor";
  private static final String M = "JTF_LOOP_COUNTER";
  public static final String TASK_REDIRECT_SESSION_NAME = "com.ffusion.jtf.JTF.TaskRedirectPageURL";
  public static final String TASK_REDIRECT_URL_SESSION_NAME = "com.ffusion.jtf.JTF.TaskNextURL";
  private static final String I = "URL_ENCRYPTOR";
  private static final String r = "UrlEncryption";
  private AIO m;
  private TemplateCacheManager n;
  private String jdField_try = "500.jtf";
  private String s = "404.jtf";
  private String p;
  private String e;
  private String jdField_null;
  private String[] i;
  private HashMap D = null;
  private boolean B = true;
  private boolean x = true;
  private String k;
  private String jdField_do;
  public static String currentSystemOutURL = null;
  public static String version = null;
  public static String BPWVersion = null;
  private static int G = -1;
  private static StringBuffer jdField_new = null;
  private int F = 15;
  private ArrayList K;
  private String jdField_byte = "ISO-8859-1";
  private String E = "UTF-8";
  private boolean q = false;
  private static String w = "PathExt";
  private static final String jdField_else = "'";
  private static final String u = "&#39;";
  private static final String jdField_char = "com.ffusion.jtf.resources";
  private static final String a = "SERVER_UNABLE_TO_FIND_FILE";
  private static final String g = "SERVER_UNABLE_TO_PROCESS";
  public static final String CLASS = "Class";
  public static final String METHOD = "Method";
  public static final String ALL = "*";
  public static final String _CLASS = "_Class";
  private static boolean J = true;
  private static ArrayList H = null;
  private static ArrayList t = null;
  private static BufferedWriter f = null;
  private static Level z = null;
  
  public void init()
    throws ServletException
  {
    ServletContext localServletContext = getServletContext();
    ServletConfig localServletConfig = getServletConfig();
    jdField_if();
    a(localServletContext, localServletConfig);
    a();
    if ((localServletContext.getMajorVersion() > 2) || ((localServletContext.getMajorVersion() == 2) && (localServletContext.getMinorVersion() >= 2))) {
      log("\t" + localServletConfig.getServletName());
    }
    jdField_if(localServletContext, localServletConfig);
    jdField_if(localServletContext);
    Initialize.initialize();
    jdField_do(localServletContext, localServletConfig);
  }
  
  public void destroy()
  {
    this.m = null;
    this.n = null;
    Initialize.fini();
  }
  
  private void jdField_if()
    throws ServletException
  {
    Random localRandom = new Random(new Date().getTime());
    this.jdField_null = ("jtfCacheKey" + localRandom.nextDouble());
    this.i = new String[1];
    this.i[0] = ".jtf";
    this.K = new ArrayList();
    this.K.add("<");
  }
  
  private void jdField_if(ServletContext paramServletContext)
  {
    a(paramServletContext);
    jdField_do(paramServletContext);
  }
  
  private void a(ServletContext paramServletContext)
  {
    Object localObject = paramServletContext.getAttribute("com.ffusion.jtf.template.TemplateCacheManager");
    if ((localObject instanceof TemplateCacheManager))
    {
      this.n = ((TemplateCacheManager)localObject);
    }
    else
    {
      this.n = new TemplateCacheManager(this.B);
      paramServletContext.setAttribute("com.ffusion.jtf.template.TemplateCacheManager", this.n);
    }
  }
  
  private void jdField_if(ServletContext paramServletContext, ServletConfig paramServletConfig)
    throws ServletException
  {
    try
    {
      this.m = new AIO(paramServletConfig, paramServletContext);
    }
    catch (Throwable localThrowable)
    {
      log("Unable to initialize AIO", localThrowable);
      throw new ServletException(localThrowable.getMessage());
    }
  }
  
  private void jdField_do(ServletContext paramServletContext) {}
  
  private void a(ServletContext paramServletContext, ServletConfig paramServletConfig)
    throws ServletException
  {
    Enumeration localEnumeration = paramServletConfig.getInitParameterNames();
    while (localEnumeration.hasMoreElements())
    {
      String str1 = (String)localEnumeration.nextElement();
      String str2 = paramServletConfig.getInitParameter(str1);
      if (str1.equals("initURL")) {
        a(str2);
      } else if (str1.equals("locale")) {
        a(paramServletContext, str2);
      } else {
        a(paramServletContext, str1, str2);
      }
    }
    if ((this.p != null) && (this.e == null)) {
      throw new ServletException("No authenticate url specified.");
    }
  }
  
  private void a(ServletContext paramServletContext, String paramString)
  {
    Locale localLocale = null;
    try
    {
      if ((paramString != null) && (paramString.length() >= 5))
      {
        String str1 = paramString.substring(0, 2);
        String str2 = paramString.substring(3, 5);
        localLocale = new Locale(str1, str2);
      }
    }
    catch (Exception localException) {}
    if (localLocale == null)
    {
      log("JTF:Invalid init arg: locale: " + paramString + " defaulting to en_us");
      localLocale = Locale.getDefault();
    }
    paramServletContext.setAttribute("java.util.Locale", localLocale);
  }
  
  private void a()
  {
    InputStream localInputStream = ResourceUtil.getResourceAsStream(this, "ffi.version");
    if (localInputStream == null) {
      log("JTF version: unknown, version file not found");
    } else {
      try
      {
        version = Strings.streamToString(localInputStream);
        log("JTF version: " + version);
      }
      catch (Exception localException1) {}
    }
    if (BPWVersion != null) {
      return;
    }
    localInputStream = ResourceUtil.getResourceAsStream(this, "BPWVersion.txt");
    if (localInputStream == null) {
      log("BPW version: unknown, version file not found");
    } else {
      try
      {
        BPWVersion = Strings.streamToString(localInputStream).trim();
        log("BPW version: " + BPWVersion);
      }
      catch (Exception localException2) {}
    }
  }
  
  protected void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {
    a(paramHttpServletRequest, paramHttpServletResponse);
  }
  
  protected void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {
    a(paramHttpServletRequest, paramHttpServletResponse);
  }
  
  private void a(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    Object localObject1 = null;
    if (this.h) {
      localObject1 = new b(paramHttpServletRequest, this.jdField_byte, this.E);
    } else {
      localObject1 = paramHttpServletRequest;
    }
    TemplateCache localTemplateCache = null;
    HttpSession localHttpSession = ((HttpServletRequest)localObject1).getSession();
    Object localObject2 = (UrlEncryptor)localHttpSession.getAttribute("URL_ENCRYPTOR");
    if (localObject2 == null) {
      try
      {
        localObject2 = new UrlEncryptorJCE(this.h);
        localHttpSession.setAttribute("URL_ENCRYPTOR", localObject2);
      }
      catch (Exception localException1)
      {
        log("Unable to instantiate encryption object.");
      }
    }
    if (this.h) {
      ((UrlEncryptor)localObject2).setDesiredEncoding(this.E);
    }
    Object localObject3 = localHttpSession.getValue("java.util.Locale");
    String str1 = ((HttpServletRequest)localObject1).getPathInfo();
    if (str1 == null) {
      str1 = "";
    }
    int i1 = str1.lastIndexOf('/');
    str1 = str1.substring(i1 + 1, str1.length());
    String str2 = paramHttpServletRequest.getParameter("FromExt");
    str2 = str2 == null ? "" : str2;
    if ((!localHttpSession.isNew()) && (this.D != null) && (this.D.containsKey(str1)))
    {
      log("**** Login page being requested, current session being invalidated: '" + ((HttpServletRequest)localObject1).getPathInfo() + "' ****");
      localHttpSession.invalidate();
      localHttpSession = ((HttpServletRequest)localObject1).getSession();
    }
    Integer localInteger = (Integer)localHttpSession.getAttribute("JTF_LOOP_COUNTER");
    try
    {
      int i2 = 0;
      if (localInteger != null) {
        i2 = localInteger.intValue();
      }
      i2++;
      localHttpSession.setAttribute("JTF_LOOP_COUNTER", new Integer(i2));
      if (i2 > this.F)
      {
        log("**** JTF RECURSION DETECTED -- ABORTING ****");
        localHttpSession.invalidate();
        paramHttpServletResponse.sendRedirect(paramHttpServletResponse.encodeRedirectURL(this.s));
        return;
      }
    }
    catch (Exception localException2) {}
    try
    {
      if ((this.p != null) && (localHttpSession.getAttribute(this.p) == null))
      {
        log("**** Trying to access page '" + ((HttpServletRequest)localObject1).getPathInfo() + "' using secure servlet when not authenticated yet. ****");
        if (this.x) {
          paramHttpServletResponse.sendRedirect(paramHttpServletResponse.encodeRedirectURL(this.e));
        } else {
          paramHttpServletResponse.sendRedirect(this.e);
        }
      }
      else
      {
        String str3 = (String)localHttpSession.getAttribute(this.jdField_null);
        if (str3 != null) {
          localTemplateCache = this.n.getTemplateCache(str3);
        }
        if (localTemplateCache == null) {
          localTemplateCache = a((HttpServletRequest)localObject1, paramHttpServletResponse, localHttpSession);
        }
        a((HttpServletRequest)localObject1, paramHttpServletResponse, localHttpSession, localTemplateCache);
      }
    }
    catch (Throwable localThrowable)
    {
      a((HttpServletRequest)localObject1, paramHttpServletResponse, localHttpSession, localTemplateCache, localThrowable);
    }
    try
    {
      localInteger = (Integer)localHttpSession.getAttribute("JTF_LOOP_COUNTER");
      localInteger = new Integer(localInteger.intValue() - 1);
      localHttpSession.setAttribute("JTF_LOOP_COUNTER", localInteger);
    }
    catch (Exception localException3) {}
  }
  
  private void a(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, HttpSession paramHttpSession, TemplateCache paramTemplateCache)
    throws IOException, ServletException
  {
    a(paramHttpServletRequest, paramHttpSession);
    String str1 = null;
    String str2 = (String)paramHttpServletRequest.getAttribute("javax.servlet.include.path_info");
    if (str2 == null) {
      str2 = paramHttpServletRequest.getPathInfo();
    }
    if (str2 == null) {
      str1 = jdField_if(paramHttpServletRequest, paramHttpServletResponse, paramHttpSession, paramTemplateCache);
    } else {
      str1 = jdField_if(paramHttpServletRequest, paramHttpServletResponse, paramHttpSession, paramTemplateCache, str2);
    }
    jdField_do(paramHttpServletRequest, paramHttpServletResponse, paramHttpSession, paramTemplateCache, str1);
  }
  
  private String jdField_if(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, HttpSession paramHttpSession, TemplateCache paramTemplateCache)
    throws IOException, ServletException
  {
    String str1 = null;
    String str2 = paramHttpServletRequest.getParameter("Task");
    if (str2 == null)
    {
      String str3 = paramHttpServletRequest.getParameter("Template");
      if (str3 == null) {
        processInvalidRequest(paramHttpServletRequest, paramHttpServletResponse, paramHttpSession, paramTemplateCache, "No Task or Template Specified");
      } else {
        processTemplate(paramHttpServletRequest, paramHttpServletResponse, paramHttpSession, paramTemplateCache, str3);
      }
    }
    else
    {
      str1 = a(paramHttpServletRequest, paramHttpServletResponse, paramHttpSession, paramTemplateCache, str2);
    }
    return str1;
  }
  
  private String jdField_if(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, HttpSession paramHttpSession, TemplateCache paramTemplateCache, String paramString)
    throws IOException, ServletException
  {
    String str1 = null;
    if (paramString.charAt(0) == '/') {
      paramString = paramString.substring(1);
    }
    String str2 = null;
    for (int i1 = 0; i1 < this.i.length; i1++) {
      if (paramString.endsWith(this.i[i1]))
      {
        str2 = paramString;
        break;
      }
    }
    if (str2 == null) {
      str1 = a(paramHttpServletRequest, paramHttpServletResponse, paramHttpSession, paramTemplateCache, paramString);
    } else {
      processTemplate(paramHttpServletRequest, paramHttpServletResponse, paramHttpSession, paramTemplateCache, str2);
    }
    return str1;
  }
  
  private String a(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, HttpSession paramHttpSession, TemplateCache paramTemplateCache, String paramString)
    throws IOException, ServletException
  {
    String str1 = null;
    Object localObject1 = paramHttpSession.getAttribute(paramString);
    if (localObject1 == null) {
      localObject1 = getServletContext().getAttribute(paramString);
    }
    if ((localObject1 instanceof Task))
    {
      long l1 = System.currentTimeMillis();
      try
      {
        Task localTask = (Task)localObject1;
        paramHttpSession.setAttribute("CurrentTask", localTask);
        Object localObject2 = paramHttpSession.getAttribute(paramString + "_ONCE");
        log("Task -> " + paramString);
        str1 = localTask.process(this, paramHttpServletRequest, paramHttpServletResponse);
        if (localObject2 != null)
        {
          paramHttpSession.removeAttribute(paramString);
          paramHttpSession.removeAttribute(paramString + "_ONCE");
        }
      }
      finally
      {
        PerfLog.log("Task -> " + paramString, l1, true);
      }
    }
    else
    {
      processInvalidRequest(paramHttpServletRequest, paramHttpServletResponse, paramHttpSession, paramTemplateCache, this.k + paramString);
    }
    if ((str1 != null) && (str1.indexOf(".jsp") != -1))
    {
      String str2 = (String)paramHttpSession.getAttribute("com.ffusion.jtf.JTF.TaskRedirectPageURL");
      if (str2 != null)
      {
        paramHttpSession.setAttribute("com.ffusion.jtf.JTF.TaskNextURL", str1);
        str1 = this.jdField_do + this.k + str2;
      }
    }
    return str1;
  }
  
  private void jdField_do(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, HttpSession paramHttpSession, TemplateCache paramTemplateCache, String paramString)
    throws IOException, ServletException
  {
    while ((paramString != null) && (!paramString.equals("")))
    {
      if (paramHttpSession.getAttribute("java.util.Locale") == null) {
        paramTemplateCache = a(paramHttpServletRequest, paramHttpServletResponse, paramHttpSession);
      }
      if ((paramString.startsWith(this.jdField_do + this.k)) && (paramString.indexOf('?') == -1))
      {
        paramString = paramString.substring(this.jdField_do.length() + this.k.length());
        paramString = jdField_if(paramHttpServletRequest, paramHttpServletResponse, paramHttpSession, paramTemplateCache, paramString);
      }
      else
      {
        if (paramString.equals("SE")) {
          paramString = this.jdField_do + this.k + paramHttpSession.getAttribute("com.ffusion.tasks.BaseTask.taskErrorURL");
        } else if (paramString.equals("TE")) {
          paramString = this.jdField_do + this.k + paramHttpSession.getAttribute("com.ffusion.tasks.BaseTask.serviceErrorURL");
        }
        String str = paramString.substring(this.jdField_do.length());
        RequestDispatcher localRequestDispatcher = getServletContext().getRequestDispatcher(str);
        if (localRequestDispatcher == null)
        {
          log("Unable to forward request: " + paramString);
          processInvalidRequest(paramHttpServletRequest, paramHttpServletResponse, paramHttpSession, paramTemplateCache, paramString);
        }
        else
        {
          try
          {
            localRequestDispatcher.forward(paramHttpServletRequest, paramHttpServletResponse);
          }
          catch (IOException localIOException)
          {
            log("ERROR <jtf:processNextURL=\"" + paramString + "\"/> Failed to forward request. " + localIOException.getMessage());
          }
          catch (Throwable localThrowable)
          {
            DebugLog.log(Level.SEVERE, "<jtf:processNextURL=\"" + paramString + "\"/> Failed to forward request. " + localThrowable.getMessage());
          }
        }
        paramString = null;
      }
    }
  }
  
  protected void processTemplate(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, HttpSession paramHttpSession, TemplateCache paramTemplateCache, String paramString)
    throws IOException
  {
    TemplateAction localTemplateAction = null;
    if (paramString.endsWith(".jsp"))
    {
      long l1 = System.currentTimeMillis();
      String str = null;
      try
      {
        if ((paramString == null) || (paramString.length() == 0)) {
          throw new IllegalArgumentException();
        }
        paramHttpServletRequest.setAttribute("jtfTemplateCache", paramTemplateCache);
        str = paramTemplateCache.findTemplatePath(getServletContext(), paramString);
        if (str != null)
        {
          log("JSP Template -> " + str);
          RequestDispatcher localRequestDispatcher = getServletContext().getRequestDispatcher(str);
          if (paramHttpServletRequest.getAttribute("JTF_CONTENT_TYPE") == null) {
            setResponseHeaders(paramHttpServletRequest, paramHttpServletResponse);
          }
          if (localRequestDispatcher != null) {
            localRequestDispatcher.forward(paramHttpServletRequest, paramHttpServletResponse);
          }
        }
        else
        {
          processInvalidRequest(paramHttpServletRequest, paramHttpServletResponse, paramHttpSession, paramTemplateCache, paramString);
        }
      }
      catch (Exception localException)
      {
        DebugLog.log(Level.SEVERE, "JSP -> " + paramString + " " + localException.getMessage() + " " + localException.toString());
        localException.printStackTrace(System.out);
      }
      finally
      {
        PerfLog.log("JSP Page -> " + str, l1, true);
      }
    }
    else
    {
      try
      {
        localTemplateAction = paramTemplateCache.getTemplate(getServletContext(), paramString);
      }
      catch (TemplateParseException localTemplateParseException)
      {
        localTemplateParseException.log(this, paramString);
      }
      if (localTemplateAction == null) {
        processInvalidRequest(paramHttpServletRequest, paramHttpServletResponse, paramHttpSession, paramTemplateCache, paramString);
      } else {
        processTemplateFound(paramHttpServletRequest, paramHttpServletResponse, paramTemplateCache, localTemplateAction, paramString);
      }
    }
  }
  
  protected void setResponseHeaders(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = (String)localHttpSession.getAttribute("JTF_CONTENT_TYPE");
    if (str != null) {
      paramHttpServletResponse.setContentType(str);
    }
    String[] arrayOfString1 = (String[])localHttpSession.getAttribute("HEADER_NAMES");
    if (arrayOfString1 != null)
    {
      String[] arrayOfString2 = (String[])localHttpSession.getAttribute("HEADER_VALUES");
      for (int i1 = 0; (i1 < arrayOfString1.length) && (i1 < arrayOfString2.length); i1++) {
        paramHttpServletResponse.addHeader(arrayOfString1[i1], arrayOfString2[i1]);
      }
    }
    paramHttpServletRequest.setAttribute("JTF_CONTENT_TYPE", Boolean.TRUE);
  }
  
  protected void processTemplateFound(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, TemplateCache paramTemplateCache, TemplateAction paramTemplateAction, String paramString)
    throws IOException
  {
    long l1 = 0L;
    if (G >= 0) {
      l1 = System.currentTimeMillis();
    }
    log("Template -> " + paramString);
    paramHttpServletRequest.setAttribute("TEMPLATE_CACHE", paramTemplateCache);
    paramTemplateAction.startProcess(this, paramHttpServletRequest, paramHttpServletResponse);
    if (l1 > 0L)
    {
      long l2 = System.currentTimeMillis() - l1;
      if (l2 > G)
      {
        DebugLog.log(Level.INFO, "Template-> " + paramString + ": " + l2 + "ms");
        appendMonitorResults(paramString, l2);
      }
    }
  }
  
  protected void processInvalidRequest(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, HttpSession paramHttpSession, TemplateCache paramTemplateCache, String paramString)
    throws IOException
  {
    log("***** Invalid Request: " + paramString);
    Locale localLocale = (Locale)paramHttpSession.getValue("java.util.Locale");
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    Object localObject1;
    Object localObject2;
    Object localObject3;
    if (this.s.endsWith(".jsp"))
    {
      localObject1 = (String)paramHttpSession.getAttribute(w);
      String str1;
      if (localObject1 == null) {
        str1 = this.s;
      } else {
        str1 = (String)localObject1 + this.s;
      }
      try
      {
        log("JSP Template -> " + str1);
        String str2 = paramTemplateCache.findTemplatePath(getServletContext(), str1);
        if (str2 != null)
        {
          localObject2 = getServletContext().getRequestDispatcher(str2);
          if (paramHttpServletRequest.getAttribute("JTF_CONTENT_TYPE") == null) {
            setResponseHeaders(paramHttpServletRequest, paramHttpServletResponse);
          }
          if (localObject2 != null)
          {
            ((RequestDispatcher)localObject2).forward(paramHttpServletRequest, paramHttpServletResponse);
          }
          else
          {
            localObject3 = paramHttpServletResponse.getWriter();
            localObject4 = new Object[] { paramString };
            str3 = ResourceUtil.getString("SERVER_UNABLE_TO_FIND_FILE", "com.ffusion.jtf.resources", localLocale);
            String str4 = MessageFormat.format(str3, (Object[])localObject4);
            ((PrintWriter)localObject3).println(HTMLUtil.encode(str4));
            ((PrintWriter)localObject3).flush();
          }
        }
        else
        {
          localObject2 = paramHttpServletResponse.getWriter();
          localObject3 = new Object[] { paramString };
          localObject4 = ResourceUtil.getString("SERVER_UNABLE_TO_FIND_FILE", "com.ffusion.jtf.resources", localLocale);
          str3 = MessageFormat.format((String)localObject4, (Object[])localObject3);
          ((PrintWriter)localObject2).println(HTMLUtil.encode(str3));
          ((PrintWriter)localObject2).flush();
        }
      }
      catch (Exception localException)
      {
        log("JSP -> " + str1 + " " + localException.getMessage() + " " + localException.toString());
        localException.printStackTrace(System.out);
        localObject2 = paramHttpServletResponse.getWriter();
        localObject3 = new Object[] { paramString };
        Object localObject4 = ResourceUtil.getString("SERVER_UNABLE_TO_FIND_FILE", "com.ffusion.jtf.resources", localLocale);
        String str3 = MessageFormat.format((String)localObject4, (Object[])localObject3);
        ((PrintWriter)localObject2).println(HTMLUtil.encode(str3));
        ((PrintWriter)localObject2).flush();
      }
    }
    else
    {
      localObject1 = null;
      try
      {
        localObject1 = paramTemplateCache.getTemplate(getServletContext(), this.s);
      }
      catch (Throwable localThrowable) {}
      if (localObject1 == null)
      {
        PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
        Object[] arrayOfObject = { paramString };
        localObject2 = ResourceUtil.getString("SERVER_UNABLE_TO_FIND_FILE", "com.ffusion.jtf.resources", localLocale);
        localObject3 = MessageFormat.format((String)localObject2, arrayOfObject);
        localPrintWriter.println(HTMLUtil.encode((String)localObject3));
        localPrintWriter.flush();
      }
      else
      {
        if (paramString == null) {
          paramHttpServletRequest.setAttribute("FileNotFound", "jdField_null");
        } else {
          paramHttpServletRequest.setAttribute("FileNotFound", paramString);
        }
        processTemplateFound(paramHttpServletRequest, paramHttpServletResponse, paramTemplateCache, (TemplateAction)localObject1, this.s);
      }
    }
  }
  
  private void a(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, HttpSession paramHttpSession, TemplateCache paramTemplateCache, Throwable paramThrowable)
    throws IOException
  {
    log(paramThrowable.getMessage(), paramThrowable);
    Object localObject1;
    Object localObject2;
    if (paramTemplateCache == null)
    {
      localObject1 = paramHttpServletResponse.getWriter();
      localObject2 = (Locale)paramHttpSession.getValue("java.util.Locale");
      if (localObject2 == null) {
        localObject2 = Locale.getDefault();
      }
      ((PrintWriter)localObject1).println(HTMLUtil.encode(ResourceUtil.getString("SERVER_UNABLE_TO_PROCESS", "com.ffusion.jtf.resources", (Locale)localObject2)));
      ((PrintWriter)localObject1).flush();
    }
    else
    {
      try
      {
        localObject1 = (String)paramHttpSession.getAttribute(w);
        if (localObject1 == null) {
          localObject2 = this.jdField_try;
        } else {
          localObject2 = (String)localObject1 + this.jdField_try;
        }
        localObject3 = paramTemplateCache.findTemplatePath(getServletContext(), (String)localObject2);
        Object localObject4;
        Object localObject5;
        if (localObject3 != null)
        {
          localObject4 = getServletContext().getRequestDispatcher((String)localObject3);
          if (paramHttpServletRequest.getAttribute("JTF_CONTENT_TYPE") == null) {
            setResponseHeaders(paramHttpServletRequest, paramHttpServletResponse);
          }
          if (localObject4 != null)
          {
            ((RequestDispatcher)localObject4).forward(paramHttpServletRequest, paramHttpServletResponse);
          }
          else
          {
            localObject5 = paramHttpServletResponse.getWriter();
            Locale localLocale = (Locale)paramHttpSession.getValue("java.util.Locale");
            if (localLocale == null) {
              localLocale = Locale.getDefault();
            }
            ((PrintWriter)localObject5).println(HTMLUtil.encode(ResourceUtil.getString("SERVER_UNABLE_TO_PROCESS", "com.ffusion.jtf.resources", localLocale)));
            ((PrintWriter)localObject5).flush();
          }
        }
        else
        {
          localObject4 = paramHttpServletResponse.getWriter();
          localObject5 = (Locale)paramHttpSession.getValue("java.util.Locale");
          if (localObject5 == null) {
            localObject5 = Locale.getDefault();
          }
          ((PrintWriter)localObject4).println(HTMLUtil.encode(ResourceUtil.getString("SERVER_UNABLE_TO_PROCESS", "com.ffusion.jtf.resources", (Locale)localObject5)));
          ((PrintWriter)localObject4).flush();
        }
      }
      catch (Exception localException)
      {
        localObject2 = paramHttpServletResponse.getWriter();
        Object localObject3 = (Locale)paramHttpSession.getValue("java.util.Locale");
        if (localObject3 == null) {
          localObject3 = Locale.getDefault();
        }
        ((PrintWriter)localObject2).println(HTMLUtil.encode(ResourceUtil.getString("SERVER_UNABLE_TO_PROCESS", "com.ffusion.jtf.resources", (Locale)localObject3)));
        ((PrintWriter)localObject2).flush();
      }
    }
  }
  
  private void a(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    ServletContext localServletContext = getServletContext();
    Enumeration localEnumeration = paramHttpServletRequest.getParameterNames();
    while (localEnumeration.hasMoreElements())
    {
      String str1 = (String)localEnumeration.nextElement();
      if ((!str1.equals("Task")) && (!str1.equals("Template")))
      {
        String str2 = str1;
        for (int i1 = str2.indexOf('&'); i1 != -1; i1 = str2.indexOf('&'))
        {
          String str3 = str2.substring(0, i1);
          a(str3, localServletContext, paramHttpSession);
          str2 = str2.substring(i1 + 1);
        }
        a(paramHttpServletRequest, localServletContext, paramHttpSession, str1, str2);
      }
    }
  }
  
  private void a(ServletRequest paramServletRequest, ServletContext paramServletContext, HttpSession paramHttpSession, String paramString1, String paramString2)
  {
    String str1 = null;
    String str2 = null;
    int i1 = paramString2.indexOf('.');
    if (i1 == -1)
    {
      str1 = paramString2;
    }
    else
    {
      str1 = paramString2.substring(0, i1);
      str2 = paramString2.substring(i1 + 1);
    }
    Object localObject = paramHttpSession.getAttribute(str1);
    if (localObject == null) {
      localObject = paramServletContext.getAttribute(str1);
    }
    String[] arrayOfString = paramServletRequest.getParameterValues(paramString1);
    if (arrayOfString != null)
    {
      int i3;
      if (this.q)
      {
        int i2 = Array.getLength(arrayOfString);
        for (i3 = 0; i3 < i2; i3++) {
          try
          {
            if (arrayOfString[i3] != null) {
              arrayOfString[i3] = new String(arrayOfString[i3].getBytes(this.jdField_byte), this.E);
            }
          }
          catch (UnsupportedEncodingException localUnsupportedEncodingException)
          {
            System.out.println(localUnsupportedEncodingException.toString());
          }
        }
      }
      String str3;
      if ((localObject == null) || (str2 == null))
      {
        str3 = jdField_int(arrayOfString[0]);
        paramHttpSession.setAttribute(paramString2, str3);
      }
      else
      {
        for (i3 = 0; i3 < arrayOfString.length; i3++)
        {
          str3 = jdField_int(arrayOfString[i3]);
          a(localObject, paramString2, str2, str3);
        }
      }
    }
  }
  
  private String jdField_int(String paramString)
  {
    if ((this.K != null) && (this.K.size() > 0))
    {
      StringBuffer localStringBuffer = new StringBuffer(paramString);
      Iterator localIterator = this.K.iterator();
      while (localIterator.hasNext())
      {
        char c1 = ((String)localIterator.next()).charAt(0);
        a(localStringBuffer, c1, XMLUtil.XMLEncode(c1));
      }
      return localStringBuffer.toString();
    }
    return paramString;
  }
  
  private void a(StringBuffer paramStringBuffer, char paramChar, String paramString)
  {
    int i1 = 0;
    String str = paramStringBuffer.toString();
    int i2 = str.indexOf(paramChar);
    int i3 = paramString.length();
    while (i2 != -1)
    {
      paramStringBuffer.replace(i2 + i1, i2 + i1 + 1, paramString);
      i2 = str.indexOf(paramChar, i2 + 1);
      i1 += i3 - 1;
    }
  }
  
  private void a(String paramString, ServletContext paramServletContext, HttpSession paramHttpSession)
  {
    int i1 = paramString.indexOf('=');
    if (i1 != -1)
    {
      String str1 = paramString.substring(0, i1);
      String str2 = null;
      String str3 = null;
      int i2 = str1.indexOf('.');
      if (i2 == -1)
      {
        str2 = str1;
      }
      else
      {
        str2 = str1.substring(0, i2);
        str3 = str1.substring(i2 + 1);
      }
      Object localObject = paramHttpSession.getAttribute(str2);
      if (localObject == null) {
        localObject = paramServletContext.getAttribute(str2);
      }
      String str4 = paramString.substring(i1 + 1);
      if ((localObject == null) || (str3 == null)) {
        paramHttpSession.setAttribute(str1, str4);
      } else {
        a(localObject, str1, str3, str4);
      }
    }
  }
  
  private void a(Object paramObject, String paramString1, String paramString2, String paramString3)
  {
    try
    {
      Method localMethod = null;
      String str1 = paramString2;
      if (paramString2.length() > 0) {
        str1 = paramString2.substring(0, 1).toUpperCase() + paramString2.substring(1);
      }
      if (str1.indexOf(".") != -1)
      {
        Object[] arrayOfObject = Reflection.drillDownToObject(paramObject, str1, paramString2);
        paramObject = arrayOfObject[0];
        str1 = (String)arrayOfObject[1];
        paramString2 = (String)arrayOfObject[2];
      }
      int i1 = 0;
      int i2;
      HashMap localHashMap;
      String str2;
      String str3;
      Class localClass;
      if (t != null) {
        for (i2 = 0; i2 < t.size(); i2++)
        {
          localHashMap = (HashMap)t.get(i2);
          str2 = (String)localHashMap.get("Class");
          str3 = (String)localHashMap.get("Method");
          if (str1.equalsIgnoreCase(str3))
          {
            if (str2.equalsIgnoreCase("*"))
            {
              i1 = 1;
              break;
            }
            localClass = (Class)localHashMap.get("_Class");
            if ((localClass != null) && (localClass.isInstance(paramObject)))
            {
              i1 = 1;
              break;
            }
          }
        }
      }
      if ((i1 != 0) && (H != null)) {
        for (i2 = 0; i2 < H.size(); i2++)
        {
          localHashMap = (HashMap)H.get(i2);
          str2 = (String)localHashMap.get("Class");
          str3 = (String)localHashMap.get("Method");
          if (str1.equalsIgnoreCase(str3))
          {
            if (str2.equalsIgnoreCase("*"))
            {
              i1 = 0;
              break;
            }
            localClass = (Class)localHashMap.get("_Class");
            if ((localClass != null) && (localClass.isInstance(paramObject)))
            {
              i1 = 0;
              break;
            }
          }
        }
      }
      if (i1 != 0)
      {
        if (f != null) {
          if (J) {
            a(Level.SEVERE, "JTF Security: Denied " + paramObject.getClass().getName() + " set" + str1 + "(" + paramString3 + ")");
          } else {
            a(Level.SEVERE, "JTF Security: Ignoring -> Denied " + paramObject.getClass().getName() + " set" + str1 + "(" + paramString3 + ")");
          }
        }
        if (!J) {}
      }
      else if (f != null)
      {
        a(Level.INFO, "JTF Security: Allowed " + paramObject.getClass().getName() + " set" + str1 + "(" + paramString3 + ")");
      }
      localMethod = Reflection.findSetMethod(paramObject, str1);
      Object localObject;
      if (localMethod != null)
      {
        localObject = new Object[1];
        localObject[0] = paramString3;
        localMethod.invoke(paramObject, (Object[])localObject);
      }
      if (localMethod == null) {
        if ((paramObject instanceof Map))
        {
          localObject = (Map)paramObject;
          ((Map)localObject).put(paramString2, paramString3);
        }
        else
        {
          log("ERROR No such method " + paramObject.getClass() + ".set" + paramString2);
        }
      }
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      log("ERROR Unable to access method " + paramObject.getClass() + ".set" + paramString2);
    }
    catch (Throwable localThrowable)
    {
      log(localThrowable.getMessage() + " " + localThrowable.getClass().getName());
    }
  }
  
  private void a(Level paramLevel, String paramString)
  {
    try
    {
      if ((paramLevel.intValue() >= z.intValue()) && (f != null))
      {
        f.write(paramString);
        f.write("\r\n");
        f.flush();
      }
    }
    catch (Throwable localThrowable)
    {
      f = null;
      DebugLog.log(Level.SEVERE, "JTF Security: Error while writing to log file. Cause=" + localThrowable.toString());
    }
  }
  
  private TemplateCache a(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, HttpSession paramHttpSession)
    throws ServletException
  {
    String str1 = System.getProperty("file.encoding");
    log("\tDefault encoding = " + str1);
    this.jdField_byte = this.m.getBrowserEncoding();
    if (this.jdField_byte != null) {
      log("browserEncoding = " + this.jdField_byte);
    }
    String str2 = this.m.getContentType(paramHttpServletRequest);
    if (str2 != null)
    {
      paramHttpSession.setAttribute("JTF_CONTENT_TYPE", str2);
      localObject1 = str2.toLowerCase();
      int i1 = ((String)localObject1).indexOf("charset=");
      if (i1 != -1)
      {
        localObject1 = str2.substring(i1 + 8);
        log("charset = " + (String)localObject1);
        this.E = ((String)localObject1);
      }
    }
    Object localObject1 = (UrlEncryptor)paramHttpSession.getAttribute("URL_ENCRYPTOR");
    if (localObject1 == null) {
      try
      {
        localObject1 = new UrlEncryptorJCE(this.h);
        paramHttpSession.setAttribute("URL_ENCRYPTOR", localObject1);
      }
      catch (Exception localException1)
      {
        log("Unable to instantiate encryption object.");
      }
    }
    if (this.h) {
      ((UrlEncryptor)localObject1).setDesiredEncoding(this.E);
    }
    if ((paramHttpServletRequest instanceof b))
    {
      ((b)paramHttpServletRequest).a(this.E);
      ((b)paramHttpServletRequest).jdField_if(this.jdField_byte);
    }
    String[] arrayOfString1 = this.m.getHeaderNames(paramHttpServletRequest);
    if (arrayOfString1 != null)
    {
      paramHttpSession.setAttribute("HEADER_NAMES", arrayOfString1);
      localObject2 = this.m.getHeaderValues(paramHttpServletRequest);
      if (localObject2 != null) {
        paramHttpSession.setAttribute("HEADER_VALUES", localObject2);
      }
    }
    if (paramHttpSession.getAttribute("java.util.Locale") == null)
    {
      localObject2 = null;
      try
      {
        ServletContext localServletContext = getServletContext();
        localObject2 = (Locale)localServletContext.getAttribute("java.util.Locale");
      }
      catch (Exception localException2) {}
      if (localObject2 == null) {
        localObject2 = this.m.getLocale(paramHttpServletRequest);
      }
      if (localObject2 != null) {
        paramHttpSession.setAttribute("java.util.Locale", localObject2);
      }
    }
    Object localObject2 = paramHttpServletRequest.getRequestURI();
    String str3 = paramHttpServletResponse.encodeURL((String)localObject2);
    str3 = str3.substring(((String)localObject2).length());
    paramHttpSession.setAttribute("SessionID", str3);
    String[] arrayOfString2 = this.m.getPaths(paramHttpServletRequest);
    if (arrayOfString2 == null)
    {
      log("Unrecognized UserAgent String: " + paramHttpServletRequest.getHeader("User-Agent"));
      arrayOfString2 = this.m.getGenericPaths(paramHttpServletRequest);
    }
    if (arrayOfString2 == null) {
      throw new ServletException("No template paths");
    }
    log("Template Paths");
    for (int i2 = 0; i2 < arrayOfString2.length; i2++) {
      log("Path[" + i2 + "]=" + arrayOfString2[i2]);
    }
    String str4 = this.n.getTemplateCacheKey(arrayOfString2);
    paramHttpSession.setAttribute(this.jdField_null, str4);
    TemplateCache localTemplateCache = this.n.getTemplateCache(str4);
    ArrayList[] arrayOfArrayList = this.m.getEncodings();
    if (arrayOfArrayList != null)
    {
      this.q = true;
      localTemplateCache.setEncodings(arrayOfArrayList);
      log("Path encodings:");
      for (int i3 = 0; i3 < arrayOfArrayList[0].size(); i3++) {
        log("\t" + (String)arrayOfArrayList[0].get(i3) + "," + (String)arrayOfArrayList[1].get(i3) + "," + (String)arrayOfArrayList[2].get(i3));
      }
    }
    if (this.k == null)
    {
      this.k = (paramHttpServletRequest.getServletPath() + '/');
      String str5 = paramHttpServletRequest.getRequestURI();
      int i4 = str5.indexOf(this.k);
      if (i4 == 0) {
        this.jdField_do = "";
      } else {
        this.jdField_do = str5.substring(0, i4);
      }
    }
    return localTemplateCache;
  }
  
  private void a(String paramString)
  {
    try
    {
      ServletContext localServletContext = getServletContext();
      InputStream localInputStream = localServletContext.getResourceAsStream(paramString);
      InputStreamReader localInputStreamReader = new InputStreamReader(localInputStream);
      BufferedReader localBufferedReader = new BufferedReader(localInputStreamReader);
      a locala = new a(localServletContext);
      XmlParser localXmlParser = new XmlParser();
      localXmlParser.setHandler(locala);
      localXmlParser.parse(null, null, localBufferedReader);
    }
    catch (Throwable localThrowable)
    {
      log("Init failure from xml resource", localThrowable);
    }
  }
  
  private void a(ServletContext paramServletContext, String paramString1, String paramString2)
  {
    if (paramString1.equals("errorURL")) {
      this.jdField_try = paramString2;
    } else if (paramString1.equals("invalidRequestURL")) {
      this.s = paramString2;
    } else if (paramString1.equals("authenticateObject")) {
      this.p = paramString2;
    } else if (paramString1.equals("authenticateURL")) {
      this.e = paramString2;
    } else if (paramString1.equals("extensions")) {
      jdField_if(paramString2);
    } else if (paramString1.equals("loginPages")) {
      jdField_do(paramString2);
    } else if (paramString1.equals("useCache")) {
      this.B = Boolean.valueOf(paramString2).booleanValue();
    } else if (paramString1.equals("UrlEncryption")) {
      this.h = Boolean.valueOf(paramString2).booleanValue();
    } else if (paramString1.equalsIgnoreCase("Verbose")) {
      Verbose.setLogLevel(paramString2);
    } else if (paramString1.equalsIgnoreCase("Monitor")) {
      try
      {
        setMonitor(Integer.parseInt(paramString2));
      }
      catch (NumberFormatException localNumberFormatException1) {}
    } else if (paramString1.equals("encodeRedirectURL")) {
      this.x = Boolean.valueOf(paramString2).booleanValue();
    } else if (paramString1.equalsIgnoreCase("maxLoops")) {
      try
      {
        this.F = Integer.parseInt(paramString2);
      }
      catch (NumberFormatException localNumberFormatException2) {}
    } else if (paramString1.equals("encodeCharacters")) {
      jdField_for(paramString2);
    } else if ((!paramString1.equals("pcl")) && (!paramString1.equals("platforms"))) {
      paramServletContext.setAttribute(paramString1, paramString2);
    }
  }
  
  private void jdField_if(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ";");
    int i1 = localStringTokenizer.countTokens();
    this.i = new String[i1 + 1];
    this.i[0] = ".jtf";
    for (int i2 = 1; i2 <= i1; i2++) {
      this.i[i2] = localStringTokenizer.nextToken();
    }
  }
  
  private void jdField_do(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ";");
    int i1 = localStringTokenizer.countTokens();
    this.D = new HashMap();
    for (int i2 = 1; i2 <= i1; i2++) {
      this.D.put(localStringTokenizer.nextToken(), null);
    }
  }
  
  private void jdField_for(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ";");
    int i1 = localStringTokenizer.countTokens();
    this.K = new ArrayList();
    Object localObject;
    for (int i2 = 0; i2 < i1; i2++)
    {
      localObject = localStringTokenizer.nextToken();
      localObject = ((String)localObject).substring(0, 1);
      if ((!XMLUtil.XMLEncode(((String)localObject).charAt(0)).equals(localObject)) && (!this.K.contains(localObject))) {
        if (((String)localObject).equals("&")) {
          this.K.add(0, localObject);
        } else {
          this.K.add(localObject);
        }
      }
    }
    if (DebugLog.getLogger().isLoggable(Level.FINE))
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localObject = this.K.iterator();
      while (((Iterator)localObject).hasNext())
      {
        localStringBuffer.append((String)((Iterator)localObject).next());
        localStringBuffer.append(" ");
      }
      log("JTF: Encoding characters: " + localStringBuffer.toString());
    }
  }
  
  private void jdField_do(ServletContext paramServletContext, ServletConfig paramServletConfig)
    throws ServletException
  {
    if (t != null) {
      return;
    }
    H = new ArrayList();
    t = new ArrayList();
    Logger localLogger = DebugLog.getLogger();
    z = localLogger.getLevel();
    if (z == null) {
      z = Level.SEVERE;
    }
    String str1 = "jtf_url.log";
    try
    {
      InputStream localInputStream = ResourceUtil.getResourceAsStream(this, "jtf.conf");
      if (localInputStream == null)
      {
        try
        {
          f = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(str1)));
        }
        catch (Throwable localThrowable1)
        {
          DebugLog.log(Level.SEVERE, "JTF Security: Error creating logfile " + str1 + ". Cause=" + localThrowable1.toString());
          f = null;
        }
        a("com.ffusion.beans.FundsTransaction", "ID", t);
        a("com.ffusion.tasks.billpay.EditPayment", "Process", t);
        a("com.ffusion.tasks.banking.EditTransfer", "Process", t);
      }
      else
      {
        localObject1 = new BufferedReader(new InputStreamReader(ResourceUtil.getResourceAsStream(this, "jtf.conf")));
        for (;;)
        {
          localObject2 = ((BufferedReader)localObject1).readLine();
          if (localObject2 == null) {
            break;
          }
          if ((!((String)localObject2).startsWith("//")) && (((String)localObject2).length() >= 10))
          {
            a(Level.INFO, (String)localObject2);
            if (((String)localObject2).startsWith("log "))
            {
              if (f == null)
              {
                int i1 = ((String)localObject2).indexOf(" logfile=");
                if (i1 != -1) {
                  str1 = ((String)localObject2).substring(i1 + 9);
                }
                try
                {
                  f = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(str1)));
                }
                catch (Throwable localThrowable2)
                {
                  DebugLog.log(Level.SEVERE, "JTF Security: Error creating logfile " + str1 + ". Cause=" + localThrowable2.toString());
                  f = null;
                }
              }
            }
            else if (((String)localObject2).startsWith("deny setProperty=false"))
            {
              J = false;
            }
            else
            {
              String str2;
              int i2;
              String str3;
              if (((String)localObject2).startsWith("allow setProperty Class="))
              {
                str2 = ((String)localObject2).substring(24);
                i2 = str2.indexOf(' ');
                str2 = str2.substring(0, i2);
                i2 = ((String)localObject2).indexOf("Method=", i2);
                str3 = ((String)localObject2).substring(i2 + 7);
                a(str2, str3, H);
              }
              else if (((String)localObject2).startsWith("deny setProperty Class="))
              {
                str2 = ((String)localObject2).substring(23);
                i2 = str2.indexOf(' ');
                str2 = str2.substring(0, i2);
                i2 = ((String)localObject2).indexOf("Method=", i2);
                str3 = ((String)localObject2).substring(i2 + 7);
                a(str2, str3, t);
              }
            }
          }
        }
      }
    }
    catch (Exception localException)
    {
      Object localObject1 = new StringWriter();
      Object localObject2 = new PrintWriter((Writer)localObject1);
      ((PrintWriter)localObject2).print("****** Unable in initialize URL Security.  The URL Security will not be functional:");
      localException.printStackTrace((PrintWriter)localObject2);
      DebugLog.log(Level.SEVERE, ((StringWriter)localObject1).toString());
    }
  }
  
  private void a(String paramString1, String paramString2, ArrayList paramArrayList)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("Class", paramString1);
    localHashMap.put("Method", paramString2);
    Class localClass = null;
    try
    {
      if (!paramString1.equals("*"))
      {
        localClass = Class.forName(paramString1);
        localHashMap.put("_Class", localClass);
      }
      paramArrayList.add(localHashMap);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log(Level.SEVERE, "JTF Security: Unable to load class: " + paramString1 + ". Ignoring directive.");
    }
  }
  
  public void setMonitor(int paramInt)
  {
    G = paramInt;
    jdField_new = new StringBuffer();
  }
  
  public static int getMonitor()
  {
    return G;
  }
  
  public String getContextRoot()
  {
    return this.jdField_do;
  }
  
  public String getServletPath()
  {
    return this.k;
  }
  
  public static void appendMonitorResults(String paramString, long paramLong)
  {
    if (jdField_new != null)
    {
      jdField_new.append(paramString + ": " + paramLong + "<br>");
      if (jdField_new.length() > 20000) {
        jdField_new.delete(0, 2000);
      }
    }
  }
  
  public String getMonitorResults()
  {
    if (jdField_new != null) {
      return jdField_new.toString();
    }
    return "";
  }
  
  public void log(String paramString)
  {
    DebugLog.log(Level.FINE, paramString);
  }
  
  public void log(String paramString, Throwable paramThrowable)
  {
    DebugLog.throwing(paramString, paramThrowable);
  }
  
  class a
    extends HandlerBase
  {
    ServletContext a;
    String jdField_if;
    
    a(ServletContext paramServletContext)
    {
      this.a = paramServletContext;
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
      throws Exception
    {
      if (paramInt2 > 0)
      {
        int i = paramInt1 + paramInt2;
        for (int j = 0; j < i; j++) {
          if (!Character.isWhitespace(paramArrayOfChar[j]))
          {
            this.jdField_if = new String(paramArrayOfChar, paramInt1, paramInt2);
            break;
          }
        }
      }
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      this.jdField_if = null;
    }
    
    public void endElement(String paramString)
      throws Exception
    {
      if (this.jdField_if != null)
      {
        JTF.this.a(this.a, paramString, this.jdField_if);
        this.jdField_if = null;
      }
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
      throws Exception
    {
      JTF.this.a(this.a, paramString1, paramString2);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.JTF
 * JD-Core Version:    0.7.0.1
 */