package com.ffusion.ims21.servlet;

import com.ffusion.beans.Balance;
import com.ffusion.beans.Currency;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.user.OneToOneUser;
import com.ffusion.ims21.servlet.parse.BannerStats;
import com.ffusion.ims21.servlet.parse.ImsMsg;
import com.ffusion.ims21.servlet.parse.MessageGrabber;
import com.ffusion.ims21.servlet.parse.MessagePack;
import com.ffusion.ims21.util.AcctKey;
import com.ffusion.ims21.util.DemoKey;
import com.ffusion.ims21.util.XmlReader;
import com.ffusion.util.logging.DebugLog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;
import java.util.logging.Level;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUtils;

public class ImsBanner
  extends HttpServlet
{
  static final String jdField_int = "acct";
  static final String B = "actionUrl";
  static final String A = "BID";
  static final String p = "refresh";
  static final String s = "target";
  static final String x = "no";
  static final String o = "id";
  static final String jdField_try = "msgid";
  static final String q = "IMS";
  static final String b = "DEFAULTURL";
  static final String v = "STATSINTERVAL";
  static final String e = "FFI Banner servlet";
  static final String r = "debug";
  static final String jdField_if = "des";
  static final String jdField_char = "msg";
  static final String a = "txtmsg";
  static final String f = "type";
  static final String jdField_else = "alt";
  static final String jdField_byte = "numofrows";
  static final String z = "Accounts";
  static final String h = "IMSPATH";
  static final String d = "/getActionUrl";
  static final String G = "/getCookies";
  static final String jdField_goto = "/getClickUrl";
  static final String u = "/getInit";
  static final String y = "/getTextMessage";
  static final String jdField_for = "/getTextMessage2";
  static final String m = "/getMessage";
  static final String D = "/getMessage2";
  static final String g = "/getStats";
  static final String C = "/reload";
  static final String E = "/pushStats";
  static final String c = "/about";
  static final String t = "/dump";
  String l = null;
  String F = null;
  String jdField_void = null;
  String jdField_do = null;
  String j = null;
  MessageGrabber jdField_long = null;
  int jdField_case = 0;
  int k = 0;
  ServletConfig w = null;
  String n = "";
  boolean jdField_new = false;
  String jdField_null = "BankingAccounts";
  protected static HashMap gImageCache = new HashMap();
  protected static Object gLocker = new Object();
  static final String i = "bid3";
  
  public void init(ServletConfig paramServletConfig)
    throws ServletException
  {
    super.init(paramServletConfig);
    this.w = paramServletConfig;
    this.jdField_new = a();
  }
  
  private boolean a()
  {
    try
    {
      this.l = this.w.getInitParameter("IMS");
      this.F = this.w.getInitParameter("BID");
      this.jdField_void = this.w.getInitParameter("DEFAULTURL");
      this.jdField_do = this.w.getInitParameter("STATSINTERVAL");
      this.j = ensureSlash(this.w.getInitParameter("IMSPATH"));
      gImageCache = new HashMap();
      DebugLog.log("ImsBanner.initServlet");
      DebugLog.log("\tIMS=" + this.l);
      DebugLog.log("\tBID=" + this.F);
      DebugLog.log("\tDEFAULTURL=" + this.jdField_void);
      DebugLog.log("\tSTATSINTERVAL=" + this.jdField_do);
      DebugLog.log("\tIMSPATH=" + this.j);
      DebugLog.log("\tIMSPATH=" + gImageCache);
      if (this.w.getInitParameter("Accounts") != null) {
        this.jdField_null = this.w.getInitParameter("Accounts");
      }
      int i1 = 60;
      if (this.jdField_do != null) {
        try
        {
          Integer.parseInt(this.jdField_do);
        }
        catch (Throwable localThrowable2) {}
      }
      this.jdField_long = new MessageGrabber(this.l, this.F, i1, this.j);
      this.k = 0;
      this.jdField_case = this.jdField_long.startIms();
    }
    catch (Throwable localThrowable1)
    {
      this.n = localThrowable1.getMessage();
      return false;
    }
    return true;
  }
  
  public void destroy()
  {
    try
    {
      this.jdField_long.stopIms();
    }
    catch (Exception localException)
    {
      this.n = (this.n + "PushStats error =" + localException);
    }
  }
  
  public String getServletInfo()
  {
    return "FFI Banner servlet";
  }
  
  public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {
    doGet(paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {
    this.k += 1;
    String str1 = null;
    if (str1 == null) {
      str1 = (String)paramHttpServletRequest.getAttribute("CMD");
    }
    if (str1 == null) {
      str1 = paramHttpServletRequest.getParameter("CMD");
    }
    if (str1 == null) {
      str1 = paramHttpServletRequest.getPathInfo();
    } else {
      str1 = "/" + str1;
    }
    if (str1 == null)
    {
      DebugLog.log("ImsBanner received no valid command.");
      str1 = "";
    }
    DebugLog.log("ImsBanner command:" + str1);
    if (str1.equalsIgnoreCase("/ping"))
    {
      a(paramHttpServletResponse, 50 + "ImsBanner Servlet Ping<br>IMS=" + this.l + "<br>BID=" + this.F + "<br>defaultUrl=" + this.jdField_void + "<br>Initialized=" + this.jdField_new);
      this.n = "";
      return;
    }
    if (str1.equalsIgnoreCase("/about"))
    {
      a(paramHttpServletRequest, paramHttpServletResponse);
      return;
    }
    if ((!this.jdField_new) && (!jdField_do(paramHttpServletRequest, paramHttpServletResponse)))
    {
      paramHttpServletResponse.sendError(500, this.n);
      return;
    }
    if (str1.equalsIgnoreCase("/getActionUrl"))
    {
      jdField_for(paramHttpServletRequest, paramHttpServletResponse);
      return;
    }
    if (str1.equalsIgnoreCase("/getCookies"))
    {
      jdField_if(paramHttpServletRequest, paramHttpServletResponse);
      return;
    }
    if (str1.equalsIgnoreCase("/getInit"))
    {
      jdField_byte(paramHttpServletRequest, paramHttpServletResponse);
      return;
    }
    if (str1.equalsIgnoreCase("/getTextMessage"))
    {
      a(paramHttpServletRequest, paramHttpServletResponse, false);
      return;
    }
    if (str1.equalsIgnoreCase("/getTextMessage2"))
    {
      a(paramHttpServletRequest, paramHttpServletResponse, true);
      return;
    }
    if (str1.equalsIgnoreCase("/getMessage"))
    {
      jdField_if(paramHttpServletRequest, paramHttpServletResponse, false);
      return;
    }
    if (str1.equalsIgnoreCase("/getMessage2"))
    {
      jdField_if(paramHttpServletRequest, paramHttpServletResponse, true);
      return;
    }
    if (str1.equalsIgnoreCase("/getStats"))
    {
      jdField_try(paramHttpServletRequest, paramHttpServletResponse);
      return;
    }
    if (str1.equalsIgnoreCase("/getClickUrl"))
    {
      jdField_char(paramHttpServletRequest, paramHttpServletResponse);
      return;
    }
    if (str1.equalsIgnoreCase("/reload"))
    {
      jdField_do(paramHttpServletRequest, paramHttpServletResponse);
      String str2 = paramHttpServletRequest.getQueryString();
      if ((str2 != null) && (str2.indexOf("debug") != -1)) {
        a(paramHttpServletResponse, "<pre>" + this.jdField_long.getMessagePack() + "</pre>");
      } else {
        a(paramHttpServletResponse, "<h3><font color=black>Reload Complete!</font></h3>");
      }
      return;
    }
    if (str1.equalsIgnoreCase("/pushStats"))
    {
      jdField_case(paramHttpServletRequest, paramHttpServletResponse);
      a(paramHttpServletResponse, "<h3><font color=blue>Push Stats Complete!</font></h3>");
      return;
    }
    if (str1.equalsIgnoreCase("/dump"))
    {
      jdField_int(paramHttpServletRequest, paramHttpServletResponse);
      return;
    }
    a(paramHttpServletResponse, "<font color= red><h3>Error! Unknown Command</h1></font><br>url=" + HttpUtils.getRequestURL(paramHttpServletRequest));
  }
  
  private void a(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    StringBuffer localStringBuffer = new StringBuffer(150);
    localStringBuffer.append("<center><h1><font color=blue>About ImsBanner 3.0</font></center></h1>");
    a(paramHttpServletResponse, localStringBuffer.toString());
  }
  
  private void jdField_if(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession(true);
    if (localHttpSession.isNew())
    {
      a(paramHttpServletResponse, "new session");
    }
    else
    {
      StringBuffer localStringBuffer1 = new StringBuffer("Session Values ");
      Enumeration localEnumeration = localHttpSession.getAttributeNames();
      if (localEnumeration != null)
      {
        int i1 = 0;
        StringBuffer localStringBuffer2 = new StringBuffer();
        while (localEnumeration.hasMoreElements())
        {
          localObject = (String)localEnumeration.nextElement();
          i1++;
          localStringBuffer2.append("<b>" + (String)localObject + "</b> = " + localHttpSession.getAttribute((String)localObject));
          localStringBuffer2.append("<br>");
        }
        localStringBuffer1.append("size = " + i1 + "<br>");
        localStringBuffer1.append(localStringBuffer2.toString());
        localStringBuffer1.append("<p>Cookies<p>");
        Object localObject = paramHttpServletRequest.getCookies();
        if (localObject != null) {
          for (int i2 = 0; i2 < localObject.length; i2++) {
            localStringBuffer1.append("<br>" + localObject[i2].getName() + "=" + localObject[i2].getValue());
          }
        }
      }
      a(paramHttpServletResponse, localStringBuffer1.toString());
    }
  }
  
  private void jdField_byte(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    a(paramHttpServletResponse, "GetInit Errors=" + this.n + "<br>Message Count =" + this.jdField_case + "<br>url=" + this.l + "<br>bankId=" + this.F + "<br>refCount=" + this.k + "<br>" + this.jdField_long.getMessagePack());
  }
  
  private void jdField_for(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    String str = jdField_do(paramHttpServletRequest, paramHttpServletResponse, true);
    if (str == null) {
      try
      {
        paramHttpServletResponse.setStatus(204);
      }
      catch (Exception localException) {}
    } else {
      try
      {
        paramHttpServletResponse.sendRedirect(str);
      }
      catch (IOException localIOException) {}
    }
  }
  
  private void jdField_char(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = jdField_do(paramHttpServletRequest, paramHttpServletResponse, false);
    paramHttpServletResponse.setContentType("text/plain; charset=UTF-8");
    PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
    localPrintWriter.println(str);
    localPrintWriter.close();
  }
  
  private String jdField_do(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, boolean paramBoolean)
  {
    String str1 = paramHttpServletRequest.getParameter("id");
    String str2 = paramHttpServletRequest.getParameter("type");
    HttpSession localHttpSession = paramHttpServletRequest.getSession(true);
    Object localObject1 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    ImsMsg localImsMsg = null;
    Object localObject3;
    if (!localHttpSession.isNew())
    {
      localObject2 = localHttpSession.getAttributeNames();
      if (localObject2 != null) {
        while (((Enumeration)localObject2).hasMoreElements())
        {
          localObject3 = (String)((Enumeration)localObject2).nextElement();
          b localb;
          if (str2 == null)
          {
            if (((String)localObject3).equals("msg" + str1))
            {
              localb = (b)localHttpSession.getAttribute((String)localObject3);
              localImsMsg = localb.a();
              str4 = localb.jdField_for();
              str5 = localb.jdField_do();
              str3 = localImsMsg.getMessageId();
            }
          }
          else if (((String)localObject3).equals("txtmsg" + str1))
          {
            localb = (b)localHttpSession.getAttribute((String)localObject3);
            localImsMsg = localb.a();
            str4 = localb.jdField_for();
            str5 = localb.jdField_do();
            str3 = localImsMsg.getMessageId();
          }
        }
      }
    }
    if (localImsMsg == null)
    {
      localObject2 = getCookiePost(paramHttpServletRequest, str1);
      if (localObject2 != null)
      {
        localImsMsg = ((b)localObject2).a();
        str4 = ((b)localObject2).jdField_for();
        str5 = ((b)localObject2).jdField_do();
        str3 = localImsMsg.getMessageId();
      }
    }
    if (localImsMsg == null)
    {
      localObject2 = paramHttpServletRequest.getHeader("Referer:");
      if (localObject2 != null) {
        localObject1 = localObject2;
      }
      DebugLog.log("ImsBanner - cannot find message from session or hash");
      return localObject1;
    }
    Object localObject2 = localImsMsg.getActionUrl();
    if ((localObject2 != null) && (((String)localObject2).length() > 5))
    {
      localObject1 = localObject2;
    }
    else if ((this.jdField_void != null) && (this.jdField_void.length() > 5))
    {
      localObject1 = this.jdField_void;
    }
    else
    {
      localObject3 = paramHttpServletRequest.getHeader("Referer:");
      if (localObject3 != null) {
        localObject1 = localObject3;
      }
    }
    if (paramBoolean)
    {
      localObject3 = new DemoKey(localHttpSession.getAttribute("User"));
      a(localImsMsg, str4, str5, (DemoKey)localObject3);
    }
    return localObject1;
  }
  
  private void a(ImsMsg paramImsMsg, String paramString1, String paramString2, DemoKey paramDemoKey)
  {
    if (paramImsMsg != null)
    {
      long l1 = 0L;
      int i1 = 0;
      try
      {
        i1 = Integer.parseInt(paramImsMsg.getMessageId());
        if (paramString1 != null) {
          try
          {
            l1 = Long.parseLong(paramString1, 16);
          }
          catch (NumberFormatException localNumberFormatException) {}
        }
      }
      catch (Exception localException) {}
      this.jdField_long.getBannerStats().addStat(i1, 0, 1, l1, paramString2, paramDemoKey);
    }
  }
  
  private int[] jdField_new(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    int i1 = 0;
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "-,");
    int i2 = localStringTokenizer.countTokens();
    if (i2 == 0) {
      return null;
    }
    int[] arrayOfInt = new int[i2];
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      int i3 = 0;
      try
      {
        i3 = Integer.parseInt(str, 16);
      }
      catch (NumberFormatException localNumberFormatException) {}
      arrayOfInt[(i1++)] = i3;
    }
    return arrayOfInt;
  }
  
  private int[] a(Accounts paramAccounts)
  {
    if (paramAccounts == null) {
      return null;
    }
    int i1 = paramAccounts.size();
    if (i1 == 0) {
      return null;
    }
    int[] arrayOfInt = new int[i1];
    int i2 = 0;
    Iterator localIterator = paramAccounts.iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      Balance localBalance = localAccount.getCurrentBalance();
      int i3 = 0;
      if ((localBalance != null) && (localBalance.getAmountValue() != null)) {
        i3 = (int)localBalance.getAmountValue().doubleValue();
      }
      AcctKey localAcctKey = new AcctKey();
      localAcctKey.setAcctBal(i3);
      localAcctKey.setAcctId(localAccount.getTypeValue());
      arrayOfInt[(i2++)] = localAcctKey.getValue();
    }
    return arrayOfInt;
  }
  
  private String jdField_else(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    ImsMsg localImsMsg = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession(true);
    String str = paramHttpServletRequest.getParameter("id");
    b localb = (b)localHttpSession.getAttribute("msg" + str);
    if (localb != null) {
      localImsMsg = localb.a();
    }
    if (localImsMsg == null) {
      return null;
    }
    return localImsMsg.getMessageId();
  }
  
  private String jdField_new(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    ImsMsg localImsMsg = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession(true);
    String str = paramHttpServletRequest.getParameter("id");
    Object localObject = null;
    for (int i1 = 0;; i1++)
    {
      b localb = (b)localHttpSession.getAttribute("txtmsg" + str + i1);
      if (localb == null) {
        break;
      }
      localObject = localb;
    }
    if (localObject != null) {
      localImsMsg = localObject.a();
    }
    if (localImsMsg == null) {
      return null;
    }
    return localImsMsg.getMessageId();
  }
  
  private boolean jdField_do(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    try
    {
      this.jdField_long.pushStats();
    }
    catch (Exception localException)
    {
      this.n = (this.n + "PushStats error =" + localException);
    }
    this.jdField_new = a();
    return this.jdField_new;
  }
  
  private void jdField_case(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    try
    {
      try
      {
        this.jdField_long.pushStats();
      }
      catch (Exception localException1)
      {
        this.n = (this.n + "PushStats error =" + localException1);
        paramHttpServletResponse.sendError(500, localException1.toString());
        return;
      }
      paramHttpServletResponse.setStatus(200);
    }
    catch (Exception localException2)
    {
      this.n = (this.n + "PushStats error =" + localException2);
    }
  }
  
  private void jdField_try(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.jdField_long.getStats();
    if ((str == null) || (str.length() == 0)) {
      str = " ";
    }
    paramHttpServletResponse.setContentType("text/plain; charset=UTF-8");
    paramHttpServletResponse.setContentLength(str.length());
    ServletOutputStream localServletOutputStream = paramHttpServletResponse.getOutputStream();
    try
    {
      localServletOutputStream.print(str);
    }
    catch (IOException localIOException)
    {
      paramHttpServletResponse.sendError(409, "Error Reading File");
    }
    localServletOutputStream.close();
  }
  
  private HashMap jdField_do(String paramString)
  {
    HashMap localHashMap = new HashMap();
    if (paramString != null)
    {
      paramString = paramString.replace('_', ' ');
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
      while (localStringTokenizer.hasMoreTokens()) {
        localHashMap.put(localStringTokenizer.nextToken(), "");
      }
    }
    return localHashMap;
  }
  
  private void a(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, boolean paramBoolean)
    throws IOException
  {
    int i1 = 1;
    String str1 = paramHttpServletRequest.getParameter("numofrows");
    if (str1 == null) {
      str1 = (String)paramHttpServletRequest.getAttribute("numofrows");
    }
    if (str1 != null) {
      i1 = new Integer(str1).intValue();
    }
    String str2 = paramHttpServletRequest.getParameter("sep");
    if (str2 == null) {
      str2 = (String)paramHttpServletRequest.getAttribute("sep");
    }
    if (str2 == null) {
      str2 = "<br>";
    }
    String str3 = paramHttpServletRequest.getParameter("target");
    String str4 = paramHttpServletRequest.getParameter("acct");
    String str5 = paramHttpServletRequest.getParameter("id");
    if (str3 == null) {
      str3 = (String)paramHttpServletRequest.getAttribute("target");
    }
    if (str4 == null) {
      str4 = (String)paramHttpServletRequest.getAttribute("acct");
    }
    if (str5 == null) {
      str5 = (String)paramHttpServletRequest.getAttribute("id");
    }
    if (str5 == null) {
      str5 = "";
    }
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("des");
    if (arrayOfString == null)
    {
      str6 = (String)paramHttpServletRequest.getAttribute("des");
      if ((str6 != null) && (str6.indexOf(",") != -1))
      {
        int i2 = 0;
        localObject1 = new Vector();
        StringTokenizer localStringTokenizer = new StringTokenizer(str6, ",");
        while (localStringTokenizer.hasMoreTokens()) {
          ((Vector)localObject1).add(localStringTokenizer.nextToken());
        }
        arrayOfString = new String[((Vector)localObject1).size()];
        for (int i4 = 0; i4 < ((Vector)localObject1).size(); i4++) {
          arrayOfString[i4] = ((String)((Vector)localObject1).get(i4));
        }
      }
      else if (str6 != null)
      {
        arrayOfString = new String[1];
        arrayOfString[0] = str6;
      }
    }
    String str6 = null;
    Vector localVector = new Vector();
    Object localObject1 = jdField_new(str4);
    int i3 = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession(true);
    long l1 = 0L;
    if ((str3 == null) || (str3.equals("")))
    {
      i3 = 1;
    }
    else if (str3.equals("false"))
    {
      i3 = 0;
    }
    else
    {
      try
      {
        l1 = Long.parseLong(str3, 16);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        DebugLog.throwing("ImsBanner Exception: ", localNumberFormatException);
      }
      i3 = 1;
    }
    DemoKey localDemoKey = null;
    if (l1 == 0L)
    {
      localDemoKey = new DemoKey(localHttpSession.getAttribute("User"));
      l1 = localDemoKey.getValue();
    }
    HashMap localHashMap = null;
    Object localObject2;
    Object localObject3;
    if (i3 != 0)
    {
      localObject2 = paramHttpServletRequest.getParameter("tag");
      if (localObject2 == null) {
        localObject2 = (String)paramHttpServletRequest.getAttribute("tag");
      }
      if (localObject2 != null) {
        localHashMap = jdField_do((String)localObject2);
      }
      if ((localHashMap == null) || (localHashMap.size() < 1))
      {
        localObject3 = localHttpSession.getAttribute("User");
        if ((localObject3 instanceof OneToOneUser)) {
          localHashMap = ((OneToOneUser)localObject3).getHash();
        } else {
          localHashMap = null;
        }
      }
    }
    else
    {
      l1 = 0L;
      localHashMap = null;
    }
    if (localObject1 == null)
    {
      localObject2 = (Accounts)localHttpSession.getAttribute(this.jdField_null);
      localObject1 = a((Accounts)localObject2);
    }
    try
    {
      localVector = this.jdField_long.getTextMessages(localHashMap, l1, (int[])localObject1, jdField_new(paramHttpServletRequest, paramHttpServletResponse), i1, arrayOfString, localDemoKey);
      if (localVector == null)
      {
        localObject2 = paramHttpServletResponse.getWriter();
        paramHttpServletResponse.setContentType("text/html; charset=UTF-8");
        a(paramHttpServletResponse);
        ((PrintWriter)localObject2).println("");
        return;
      }
    }
    catch (Exception localException1)
    {
      a(paramHttpServletResponse, "getTextMessage Exception " + localException1);
      return;
    }
    try
    {
      if (localVector != null)
      {
        for (int i5 = 0; i5 < localVector.size(); i5++)
        {
          localObject3 = new b((ImsMsg)localVector.get(i5), str3, str4);
          localHttpSession.setAttribute("txtmsg" + str5 + i5, localObject3);
          if (paramBoolean) {
            a(localHttpSession, paramHttpServletRequest, paramHttpServletResponse, (b)localObject3);
          }
        }
        if (paramBoolean)
        {
          SimpleDateFormat localSimpleDateFormat = (SimpleDateFormat)DateFormat.getDateTimeInstance();
          localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
          localSimpleDateFormat.applyPattern("EE, d MMM yyyy HH:mm:ss z");
          localObject3 = Calendar.getInstance();
          ((Calendar)localObject3).add(10, 2);
          paramHttpServletResponse.setHeader("Expires", localSimpleDateFormat.format(((Calendar)localObject3).getTime()));
        }
      }
    }
    catch (Exception localException2)
    {
      a(paramHttpServletResponse, "Target=" + str3 + " acct=" + str4 + " putValue " + localException2);
      return;
    }
    try
    {
      if (localVector != null) {
        for (int i6 = 0; i6 < localVector.size(); i6++) {
          this.jdField_long.getBannerStats().addStat(Integer.parseInt(((ImsMsg)localVector.elementAt(i6)).getMessageId()), 1, 0, l1, str4, localDemoKey);
        }
      }
    }
    catch (Exception localException3)
    {
      DebugLog.throwing("ImsBanner Exception: ", localException3);
    }
    a(paramHttpServletRequest, paramHttpServletResponse, str5, localVector, str2);
  }
  
  private void a(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1, Vector paramVector, String paramString2)
    throws IOException
  {
    String str1 = "";
    int i2 = 0;
    int i3 = 0;
    paramHttpServletResponse.setStatus(200);
    if (i3 == 0) {
      a(paramHttpServletResponse);
    }
    int i4 = 0;
    if (paramVector != null)
    {
      i4 = paramVector.size();
      str1 = ((ImsMsg)paramVector.elementAt(0)).getUrl();
      String str3 = str1.substring(str1.lastIndexOf(".")).trim();
      if ((i3 == 0) && ((str3.equals(".html")) || (str3.equals(".htm")) || (str3.equals(".txt"))))
      {
        paramHttpServletResponse.setContentType("text/html; charset=UTF-8");
        i2 = 1;
      }
    }
    PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
    for (int i5 = 0; i5 < i4; i5++)
    {
      int i1 = 0;
      ImsMsg localImsMsg = (ImsMsg)paramVector.elementAt(i5);
      try
      {
        str1 = ((ImsMsg)paramVector.elementAt(i5)).getUrl();
        StringBuffer localStringBuffer = a(str1);
        if ((i2 == 0) && (i5 == 0))
        {
          i2 = 1;
          paramHttpServletResponse.setContentType("text/html; charset=UTF-8");
        }
        String str2 = localImsMsg.getActionUrl();
        if ((str2 != null) && (!str2.trim().equals("")))
        {
          i1 = 1;
          localPrintWriter.println("<a href='/servlet/ImsBanner/getActionUrl?id=" + paramString1 + i5 + "&type=text'>");
          localPrintWriter.println("<a href='" + paramHttpServletRequest.getContextPath() + "/servlet/ImsBanner/getActionUrl?id=" + paramString1 + i5 + "&type=text'>");
        }
        localPrintWriter.write(localStringBuffer.toString());
        if (i1 != 0) {
          localPrintWriter.println("</a>");
        }
        if (i5 + 1 < i4) {
          localPrintWriter.println(paramString2);
        }
      }
      catch (Exception localException)
      {
        DebugLog.throwing("ImsBanner Exception (returnText): " + str1, localException);
      }
    }
    localPrintWriter.flush();
  }
  
  private StringBuffer a(String paramString)
  {
    DebugLog.log("ImsBanner.getTextFromDisk: " + paramString);
    StringBuffer localStringBuffer = new StringBuffer();
    BufferedReader localBufferedReader = null;
    String str1 = this.j + paramString;
    try
    {
      localBufferedReader = new BufferedReader(new FileReader(str1));
      for (;;)
      {
        String str2 = localBufferedReader.readLine();
        if (str2 == null) {
          break;
        }
        localStringBuffer.append(str2);
      }
    }
    catch (Throwable localThrowable1)
    {
      DebugLog.log(Level.WARNING, "ImsBanner.getTextFromDisk: unable to load file from disk - " + str1 + " " + localThrowable1.getMessage());
    }
    finally
    {
      if (localBufferedReader != null) {
        try
        {
          localBufferedReader.close();
        }
        catch (Throwable localThrowable2) {}
      }
    }
    return localStringBuffer;
  }
  
  private void jdField_if(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, boolean paramBoolean)
    throws IOException
  {
    DebugLog.log("ImsBanner.getMessage");
    boolean bool = true;
    String str1 = paramHttpServletRequest.getParameter("refresh");
    String str2 = paramHttpServletRequest.getParameter("target");
    String str3 = paramHttpServletRequest.getParameter("acct");
    String str4 = paramHttpServletRequest.getParameter("id");
    DebugLog.log("\trefresh=" + str1);
    DebugLog.log("\ttarget=" + str2);
    DebugLog.log("\tacct=" + str3);
    DebugLog.log("\tid=" + str4);
    if ((str1 != null) && (str1.equals("no"))) {
      bool = false;
    }
    int[] arrayOfInt = jdField_new(str3);
    int i1 = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession(true);
    long l1 = 0L;
    if ((str2 == null) || (str2.equals("")))
    {
      i1 = 1;
    }
    else if (str2.equals("false"))
    {
      i1 = 0;
    }
    else
    {
      try
      {
        l1 = Long.parseLong(str2, 16);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        DebugLog.throwing("ImsBanner Exception (getMessage): ", localNumberFormatException);
      }
      i1 = 1;
    }
    DemoKey localDemoKey = null;
    if (l1 == 0L)
    {
      localDemoKey = new DemoKey(localHttpSession.getAttribute("User"));
      l1 = localDemoKey.getValue();
    }
    HashMap localHashMap = null;
    if (i1 != 0)
    {
      localObject1 = paramHttpServletRequest.getParameter("tag");
      if (localObject1 == null) {
        localObject1 = (String)paramHttpServletRequest.getAttribute("tag");
      }
      if (localObject1 != null) {
        localHashMap = jdField_do((String)localObject1);
      }
      if ((localHashMap == null) || (localHashMap.size() < 1))
      {
        Object localObject2 = localHttpSession.getAttribute("User");
        if ((localObject2 instanceof OneToOneUser)) {
          localHashMap = ((OneToOneUser)localObject2).getHash();
        } else {
          localHashMap = null;
        }
      }
    }
    else
    {
      l1 = 0L;
      localHashMap = null;
    }
    if (arrayOfInt == null)
    {
      localObject1 = (Accounts)localHttpSession.getAttribute(this.jdField_null);
      arrayOfInt = a((Accounts)localObject1);
    }
    Object localObject1 = null;
    try
    {
      localObject1 = this.jdField_long.getBanner(localHashMap, l1, arrayOfInt, jdField_else(paramHttpServletRequest, paramHttpServletResponse), paramHttpServletRequest.getParameterValues("des"), bool, localDemoKey);
    }
    catch (Exception localException1)
    {
      DebugLog.throwing("ImsBanner Exception (getMessage): ", localException1);
      localObject1 = null;
    }
    if (localObject1 == null)
    {
      localObject1 = this.jdField_long.getBanner(localHashMap, l1, arrayOfInt, jdField_else(paramHttpServletRequest, paramHttpServletResponse), paramHttpServletRequest.getParameterValues("alt"), bool, localDemoKey);
      if (localObject1 == null)
      {
        jdField_if(paramHttpServletResponse, null);
        return;
      }
    }
    try
    {
      b localb = new b((ImsMsg)localObject1, str2, str3);
      localHttpSession.setAttribute("msg" + str4, localb);
      if (paramBoolean)
      {
        SimpleDateFormat localSimpleDateFormat = (SimpleDateFormat)DateFormat.getDateTimeInstance();
        a(localHttpSession, paramHttpServletRequest, paramHttpServletResponse, localb);
        localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        localSimpleDateFormat.applyPattern("EE, d MMM yyyy HH:mm:ss z");
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.add(10, 2);
        paramHttpServletResponse.setHeader("Expires", localSimpleDateFormat.format(localCalendar.getTime()));
      }
    }
    catch (Exception localException2)
    {
      DebugLog.throwing("ImsBanner Exception (getMessage): ", localException2);
      jdField_if(paramHttpServletResponse, null);
      return;
    }
    try
    {
      this.jdField_long.getBannerStats().addStat(Integer.parseInt(((ImsMsg)localObject1).getMessageId()), 1, 0, l1, str3, localDemoKey);
    }
    catch (Exception localException3) {}
    jdField_if(paramHttpServletResponse, ((ImsMsg)localObject1).getUrl());
  }
  
  private void jdField_if(HttpServletResponse paramHttpServletResponse, String paramString)
  {
    ServletOutputStream localServletOutputStream = null;
    DebugLog.log("ImsBanner.returnImage: loading " + paramString);
    byte[] arrayOfByte = null;
    String str = XmlReader.getFilenameFromPath(paramString);
    try
    {
      if ((str == null) || (str.length() < 1)) {
        str = "1x1.jpg";
      }
      arrayOfByte = jdField_for(str);
      paramHttpServletResponse.setStatus(200);
      paramHttpServletResponse.setContentType(jdField_int(str));
      a(paramHttpServletResponse);
      localServletOutputStream = paramHttpServletResponse.getOutputStream();
      if (arrayOfByte != null) {
        localServletOutputStream.write(arrayOfByte);
      }
      localServletOutputStream.flush();
      localServletOutputStream.close();
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  private byte[] jdField_for(String paramString)
  {
    byte[] arrayOfByte = null;
    arrayOfByte = (byte[])gImageCache.get(paramString);
    if (arrayOfByte == null)
    {
      arrayOfByte = jdField_if(paramString);
      if (arrayOfByte != null)
      {
        gImageCache.put(paramString, arrayOfByte);
        DebugLog.log("\tAdding image to cache: " + paramString);
      }
    }
    else
    {
      DebugLog.log("\tfound image in cache: " + paramString);
    }
    return arrayOfByte;
  }
  
  private byte[] jdField_if(String paramString)
  {
    DebugLog.log("\tImsBanner.getImageFromDisk: " + paramString);
    byte[] arrayOfByte = null;
    FileInputStream localFileInputStream = null;
    int i1;
    try
    {
      File localFile = new File(this.j + paramString);
      i1 = (int)localFile.length();
      localFileInputStream = new FileInputStream(localFile);
      arrayOfByte = new byte[i1];
      int i2 = 0;
      int i3 = i1;
      while (i3 > 0)
      {
        int i4 = localFileInputStream.read(arrayOfByte, i2, i1 - i2);
        if (i4 < 1) {
          break;
        }
        i3 -= i4;
      }
    }
    catch (Throwable localThrowable1)
    {
      DebugLog.log(Level.WARNING, "ImsBanner.getImageFromDisk: unable to load file from disk :" + this.j + paramString + "\r\n" + localThrowable1.getMessage());
    }
    finally
    {
      if (localFileInputStream != null) {
        try
        {
          localFileInputStream.close();
        }
        catch (Throwable localThrowable2) {}
      }
    }
    if (arrayOfByte == null)
    {
      DebugLog.log("ImsBanner.getImageFromDisk: image not found.  Generating 1x1.gif");
      int[] arrayOfInt = { 71, 73, 70, 56, 57, 97, 1, 0, 1, 0, 128, 255, 0, 192, 192, 192, 0, 0, 0, 33, 249, 4, 1, 0, 0, 0, 0, 44, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 2, 68, 1, 0, 59 };
      arrayOfByte = new byte[arrayOfInt.length];
      for (i1 = 0; i1 < arrayOfInt.length; i1++) {
        arrayOfByte[i1] = ((byte)arrayOfInt[i1]);
      }
    }
    return arrayOfByte;
  }
  
  private String jdField_int(String paramString)
    throws IOException
  {
    String str1 = "image/gif";
    int i1 = paramString.lastIndexOf('.');
    if (i1 != -1)
    {
      String str2 = paramString.substring(i1 + 1).toLowerCase();
      if (str2.equals("gif")) {
        str1 = "image/gif";
      } else if ((str2.equals("jpg")) || (str2.equals("jpeg"))) {
        str1 = "image/jpeg";
      } else if (str2.equals("bmp")) {
        str1 = "image/bmp";
      } else if ((str2.equals("tiff")) || (str2.equals("tif"))) {
        str1 = "image/tiff";
      }
    }
    return str1;
  }
  
  private void a(HttpServletResponse paramHttpServletResponse)
  {
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    paramHttpServletResponse.setDateHeader("Date", localGregorianCalendar.getTime().getTime());
    paramHttpServletResponse.setDateHeader("Expires", localGregorianCalendar.getTime().getTime());
    paramHttpServletResponse.setHeader("Pragma", "no-cache");
    paramHttpServletResponse.setHeader("Cache-Control", "no-cache");
    paramHttpServletResponse.setHeader("Cache-Control", "no-store");
  }
  
  private void a(HttpServletResponse paramHttpServletResponse, String paramString)
  {
    try
    {
      paramHttpServletResponse.setContentType("text/html; charset=UTF-8");
      paramHttpServletResponse.setHeader("Pragma", "no-cache");
      PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
      localPrintWriter.println("<html><head><title>ImsBanner Message</title></head>");
      localPrintWriter.println("<body>");
      localPrintWriter.println(paramString);
      localPrintWriter.println("</body></html>");
      localPrintWriter.flush();
      localPrintWriter.close();
    }
    catch (IOException localIOException) {}
  }
  
  private void jdField_int(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = "";
    String str2 = paramHttpServletRequest.getParameter("id");
    Vector localVector = this.jdField_long.getMessagePack().getMessageVector();
    for (int i1 = 0; i1 < localVector.size(); i1++)
    {
      ImsMsg localImsMsg = (ImsMsg)localVector.elementAt(i1);
      str1 = str1 + localImsMsg.getMessageId() + ",";
      if (str2.equals(localImsMsg.getMessageId()))
      {
        a(paramHttpServletResponse, "<pre>" + localImsMsg.toString() + "</pre>");
        return;
      }
    }
    a(paramHttpServletResponse, "<pre>Id not found=" + str2 + "\n" + str1 + "</pre>");
  }
  
  private void a(HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, b paramb)
  {
    if (paramb.jdField_for() != null) {
      a(paramHttpServletResponse, "bid3T", paramb.jdField_for());
    }
    if (paramb.jdField_do() != null) {
      a(paramHttpServletResponse, "bid3A", paramb.jdField_do());
    }
    String str1 = "";
    a locala = new a(null);
    Enumeration localEnumeration = paramHttpSession.getAttributeNames();
    if (localEnumeration != null) {
      while (localEnumeration.hasMoreElements())
      {
        str2 = (String)localEnumeration.nextElement();
        if (str2.indexOf("msg") == 0)
        {
          String str3 = str2.substring("msg".length());
          b localb = (b)paramHttpSession.getAttribute(str2);
          ImsMsg localImsMsg = localb.a();
          locala.a(str3, localImsMsg.getMessageId());
        }
      }
    }
    String str2 = locala.a();
    a(paramHttpServletResponse, "bid3", str2);
  }
  
  public b getCookiePost(HttpServletRequest paramHttpServletRequest, String paramString)
  {
    String str1 = a(paramHttpServletRequest, "bid3");
    a locala = new a(str1);
    if (paramString == null) {
      return null;
    }
    String str2 = locala.jdField_if(paramString);
    if (str2 == null) {
      return null;
    }
    ImsMsg localImsMsg = this.jdField_long.getImsMsg(str2);
    String str3 = a(paramHttpServletRequest, "bid3T");
    String str4 = a(paramHttpServletRequest, "bid3A");
    return new b(localImsMsg, str3, str4);
  }
  
  private void a(HttpServletResponse paramHttpServletResponse, String paramString1, String paramString2)
  {
    Cookie localCookie = new Cookie(paramString1, paramString2);
    if (paramString2 != null)
    {
      localCookie.setMaxAge(180);
      localCookie.setPath("/");
    }
    paramHttpServletResponse.addCookie(localCookie);
  }
  
  private String a(HttpServletRequest paramHttpServletRequest, String paramString)
  {
    Cookie[] arrayOfCookie = paramHttpServletRequest.getCookies();
    if (arrayOfCookie != null) {
      for (int i1 = 0; i1 < arrayOfCookie.length; i1++) {
        if (arrayOfCookie[i1].getName().equals(paramString)) {
          return arrayOfCookie[i1].getValue();
        }
      }
    }
    return null;
  }
  
  public String ensureSlash(String paramString)
  {
    int i1 = paramString.lastIndexOf("\\");
    int i2 = paramString.lastIndexOf("/");
    int i3 = i1 > i2 ? i1 : i2;
    if (i3 != paramString.length() - 1) {
      if (i1 > i2) {
        paramString = paramString + "\\";
      } else {
        paramString = paramString + "/";
      }
    }
    return paramString;
  }
  
  class a
    implements Serializable
  {
    Hashtable a = new Hashtable();
    
    public a(String paramString)
    {
      if (paramString != null) {
        a(paramString);
      }
    }
    
    void a(String paramString)
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "xy");
      while (localStringTokenizer.hasMoreTokens())
      {
        String str1 = localStringTokenizer.nextToken();
        if (localStringTokenizer.hasMoreTokens())
        {
          String str2 = localStringTokenizer.nextToken();
          this.a.put(str1, str2);
        }
      }
    }
    
    void a(String paramString1, String paramString2)
    {
      this.a.put(paramString1, paramString2);
    }
    
    public String jdMethod_if(String paramString)
    {
      Object localObject = this.a.get(paramString);
      if (localObject != null) {
        return (String)localObject;
      }
      return null;
    }
    
    public String a()
    {
      String str1 = null;
      Enumeration localEnumeration = this.a.keys();
      while (localEnumeration.hasMoreElements())
      {
        String str2 = (String)localEnumeration.nextElement();
        if (str1 == null) {
          str1 = str2 + "x" + (String)this.a.get(str2);
        } else {
          str1 = str1 + "y" + str2 + "x" + (String)this.a.get(str2);
        }
      }
      return str1;
    }
    
    public String toString()
    {
      StringBuffer localStringBuffer = new StringBuffer();
      Enumeration localEnumeration = this.a.keys();
      while (localEnumeration.hasMoreElements())
      {
        String str = (String)localEnumeration.nextElement();
        localStringBuffer.append("\nKey = ").append(str).append(" value =").append((String)this.a.get(str));
      }
      return localStringBuffer.toString();
    }
  }
  
  public class b
    implements Serializable
  {
    ImsMsg jdField_do;
    String a;
    String jdField_if;
    
    public b(ImsMsg paramImsMsg, String paramString1, String paramString2)
    {
      this.jdField_do = paramImsMsg;
      this.a = paramString1;
      this.jdField_if = paramString2;
    }
    
    public ImsMsg a()
    {
      return this.jdField_do;
    }
    
    public String jdField_if()
    {
      if (this.jdField_do != null) {
        return this.jdField_do.getMessageId();
      }
      return null;
    }
    
    public String jdMethod_for()
    {
      return this.a;
    }
    
    public String jdField_do()
    {
      return this.jdField_if;
    }
    
    public String toString()
    {
      return a() + jdMethod_for() + jdField_do();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.ims21.servlet.ImsBanner
 * JD-Core Version:    0.7.0.1
 */