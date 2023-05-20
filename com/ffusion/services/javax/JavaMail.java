package com.ffusion.services.javax;

import com.ffusion.beans.messages.GlobalMessage;
import com.ffusion.beans.messages.GlobalMessageConsts;
import com.ffusion.beans.messages.Messages;
import com.ffusion.efs.adapters.profile.MessageAdapter;
import com.ffusion.efs.adapters.profile.ResultSetWrapper;
import com.ffusion.efs.adapters.profile.UserWrapper;
import com.ffusion.services.Messaging2;
import com.ffusion.services.MessagingFilters;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import javax.mail.Address;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMail
  implements Messaging2
{
  public static final int ERROR_INTERRUPTED_EXCEPTION = 600500;
  public static final int ERROR_EMAIL_TO_OR_FROINVALID = 600501;
  public static final int ERROR_PROPERTIES_NULL = 600502;
  public static final int ERROR_INVALID_SMTP_HOST = 600503;
  public static final int ERROR_NO_ABLE_TO_CREATE_MIME_MESSAGE = 600504;
  public static final int ERROR_EMAIL_TRANSMIT_EXCEPTION = 600505;
  public static final int ERROR_INVALID_ADDRESSES = 600506;
  public static final int ERROR_VALID_UNSENT_ADDRESSES = 600507;
  public static final int ERROR_VALID_SENT_ADDRESSES = 600508;
  public static final int ERROR_MESSAGING_IO_EXCEPTION = 600509;
  public static final int ERROR_UNKNOWN_ERROR = 600510;
  public static final int ERROR_INVALID_GROUPID = 600511;
  public static final String PERSONAL_FROM_NAME = "PERSONAL_FROM_NAME";
  protected static final String MAIL_HOST = "MAIL_HOST";
  protected static final String PORT = "PORT";
  protected static final String MAX_RECIPIENTS = "MAX_RECIPIENTS";
  protected int mPort = 25;
  protected String mHost = null;
  protected int mMaxRecipient = 255;
  public static final String SERVICE_INIT_XML = "javamailSettings.xml";
  
  private void a(String paramString1, String paramString2)
  {
    if (paramString1.equalsIgnoreCase("MAIL_HOST")) {
      this.mHost = paramString2;
    } else if (paramString1.equalsIgnoreCase("PORT")) {
      this.mPort = Integer.parseInt(paramString2);
    } else if (paramString1.equalsIgnoreCase("MAX_RECIPIENTS")) {
      this.mMaxRecipient = Integer.parseInt(paramString2);
    }
  }
  
  public void setInitURL(String paramString)
  {
    initialize(paramString);
  }
  
  public int initialize(String paramString)
  {
    int i = 0;
    if ((paramString == null) || (paramString.length() == 0)) {
      paramString = "javamailSettings.xml";
    }
    try
    {
      InputStream localInputStream = ResourceUtil.getResourceAsStream(this, paramString);
      String str = Strings.streamToString(localInputStream);
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), str);
    }
    catch (Throwable localThrowable)
    {
      i = 8;
    }
    return i;
  }
  
  public void setSettings(String paramString) {}
  
  public String getSettings()
  {
    return "";
  }
  
  public int signOn(String paramString1, String paramString2)
  {
    return 0;
  }
  
  public void setUserName(String paramString) {}
  
  public void setPassword(String paramString) {}
  
  public int changePIN(String paramString1, String paramString2)
  {
    return 0;
  }
  
  public int getMessages(Messages paramMessages)
  {
    return 0;
  }
  
  public int getMessages(Object paramObject, Messages paramMessages)
  {
    return 2;
  }
  
  public int markMessageAsRead(Object paramObject)
  {
    return 2;
  }
  
  public int getNumberOfUnreadMessages(Object paramObject)
  {
    return 2;
  }
  
  public int sendMessage(com.ffusion.beans.messages.Message paramMessage)
  {
    int i = 0;
    try
    {
      i = a(this.mHost, paramMessage.getTo(), paramMessage.getFrom(), paramMessage.getSubject(), paramMessage.getMemo(), (String)paramMessage.get("PERSONAL_FROM_NAME"));
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log(Level.FINE, "Error in send Message: " + localThrowable);
      i = 8505;
    }
    return i;
  }
  
  public int sendMessage(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    int i = 0;
    if (paramString3 == null) {
      paramString3 = "";
    }
    if ((paramString4 == null) || (paramString4.length() == 0)) {
      return 8007;
    }
    try
    {
      i = a(this.mHost, paramString1, paramString2, paramString3, paramString4, null);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log(Level.FINE, "Error in send Message: " + localThrowable);
      i = 8505;
    }
    return i;
  }
  
  public int sendMessage(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    int i = 0;
    if (paramString3 == null) {
      paramString3 = "";
    }
    if ((paramString4 == null) || (paramString4.length() == 0)) {
      return 8007;
    }
    try
    {
      i = a(this.mHost, paramString1, paramString2, paramString3, paramString4, paramString5);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log(Level.FINE, "Error in send Message: " + localThrowable);
      i = 8505;
    }
    return i;
  }
  
  public int deleteMessage(Object paramObject)
  {
    return 2;
  }
  
  public int sendMessage(com.ffusion.beans.messages.Message paramMessage, Object paramObject)
  {
    return 2;
  }
  
  /* Error */
  private int a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
    throws java.io.IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 7
    //   3: aconst_null
    //   4: astore 8
    //   6: aload_2
    //   7: ifnull +10 -> 17
    //   10: aload_2
    //   11: invokevirtual 12	java/lang/String:length	()I
    //   14: ifne +13 -> 27
    //   17: new 39	java/io/IOException
    //   20: dup
    //   21: ldc 40
    //   23: invokespecial 41	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   26: athrow
    //   27: aload 6
    //   29: ifnull +11 -> 40
    //   32: aload 6
    //   34: invokevirtual 12	java/lang/String:length	()I
    //   37: ifne +16 -> 53
    //   40: new 42	javax/mail/internet/InternetAddress
    //   43: dup
    //   44: aload_3
    //   45: invokespecial 43	javax/mail/internet/InternetAddress:<init>	(Ljava/lang/String;)V
    //   48: astore 7
    //   50: goto +15 -> 65
    //   53: new 42	javax/mail/internet/InternetAddress
    //   56: dup
    //   57: aload_3
    //   58: aload 6
    //   60: invokespecial 44	javax/mail/internet/InternetAddress:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   63: astore 7
    //   65: aload_2
    //   66: iconst_0
    //   67: invokestatic 45	javax/mail/internet/InternetAddress:parse	(Ljava/lang/String;Z)[Ljavax/mail/internet/InternetAddress;
    //   70: astore 8
    //   72: goto +71 -> 143
    //   75: astore 9
    //   77: new 47	java/io/StringWriter
    //   80: dup
    //   81: invokespecial 48	java/io/StringWriter:<init>	()V
    //   84: astore 10
    //   86: aload 9
    //   88: new 49	java/io/PrintWriter
    //   91: dup
    //   92: aload 10
    //   94: invokespecial 50	java/io/PrintWriter:<init>	(Ljava/io/Writer;)V
    //   97: invokevirtual 51	javax/mail/internet/AddressException:printStackTrace	(Ljava/io/PrintWriter;)V
    //   100: new 32	java/lang/StringBuffer
    //   103: dup
    //   104: invokespecial 33	java/lang/StringBuffer:<init>	()V
    //   107: ldc 52
    //   109: invokevirtual 35	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   112: aload 10
    //   114: invokevirtual 53	java/io/StringWriter:toString	()Ljava/lang/String;
    //   117: invokevirtual 35	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   120: invokevirtual 37	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   123: astore 11
    //   125: getstatic 31	java/util/logging/Level:FINE	Ljava/util/logging/Level;
    //   128: aload 11
    //   130: invokestatic 38	com/ffusion/util/logging/DebugLog:log	(Ljava/util/logging/Level;Ljava/lang/String;)V
    //   133: new 39	java/io/IOException
    //   136: dup
    //   137: ldc 54
    //   139: invokespecial 41	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   142: athrow
    //   143: new 55	java/util/Properties
    //   146: dup
    //   147: invokespecial 56	java/util/Properties:<init>	()V
    //   150: astore 9
    //   152: aload 9
    //   154: ifnonnull +13 -> 167
    //   157: new 39	java/io/IOException
    //   160: dup
    //   161: ldc 57
    //   163: invokespecial 41	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   166: athrow
    //   167: aload 9
    //   169: ldc 58
    //   171: aload_3
    //   172: invokevirtual 59	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   175: pop
    //   176: aload 9
    //   178: ldc 60
    //   180: aload_1
    //   181: invokevirtual 59	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   184: pop
    //   185: aload 9
    //   187: aconst_null
    //   188: invokestatic 61	javax/mail/Session:getInstance	(Ljava/util/Properties;Ljavax/mail/Authenticator;)Ljavax/mail/Session;
    //   191: astore 10
    //   193: aload 10
    //   195: ifnonnull +13 -> 208
    //   198: new 39	java/io/IOException
    //   201: dup
    //   202: ldc 62
    //   204: invokespecial 41	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   207: athrow
    //   208: aconst_null
    //   209: astore 11
    //   211: aconst_null
    //   212: astore 12
    //   214: new 63	javax/mail/internet/MimeMessage
    //   217: dup
    //   218: aload 10
    //   220: invokespecial 64	javax/mail/internet/MimeMessage:<init>	(Ljavax/mail/Session;)V
    //   223: astore 12
    //   225: aload 12
    //   227: ifnonnull +13 -> 240
    //   230: new 39	java/io/IOException
    //   233: dup
    //   234: ldc 65
    //   236: invokespecial 41	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   239: athrow
    //   240: aload 12
    //   242: aload 7
    //   244: invokevirtual 66	javax/mail/internet/MimeMessage:setFrom	(Ljavax/mail/Address;)V
    //   247: aload 12
    //   249: getstatic 67	javax/mail/Message$RecipientType:TO	Ljavax/mail/Message$RecipientType;
    //   252: aload 8
    //   254: invokevirtual 68	javax/mail/internet/MimeMessage:setRecipients	(Ljavax/mail/Message$RecipientType;[Ljavax/mail/Address;)V
    //   257: aload 12
    //   259: aload 4
    //   261: ldc 69
    //   263: invokevirtual 70	javax/mail/internet/MimeMessage:setSubject	(Ljava/lang/String;Ljava/lang/String;)V
    //   266: aload 12
    //   268: new 71	java/util/Date
    //   271: dup
    //   272: invokespecial 72	java/util/Date:<init>	()V
    //   275: invokevirtual 73	javax/mail/internet/MimeMessage:setSentDate	(Ljava/util/Date;)V
    //   278: aload 12
    //   280: aload 5
    //   282: ldc 69
    //   284: invokevirtual 74	javax/mail/internet/MimeMessage:setText	(Ljava/lang/String;Ljava/lang/String;)V
    //   287: aload 10
    //   289: aload 7
    //   291: invokevirtual 75	javax/mail/Session:getTransport	(Ljavax/mail/Address;)Ljavax/mail/Transport;
    //   294: astore 11
    //   296: aload 11
    //   298: invokevirtual 76	javax/mail/Transport:connect	()V
    //   301: aload 11
    //   303: aload 12
    //   305: aload 8
    //   307: invokevirtual 77	javax/mail/Transport:sendMessage	(Ljavax/mail/Message;[Ljavax/mail/Address;)V
    //   310: ldc2_w 78
    //   313: invokestatic 80	java/lang/Thread:sleep	(J)V
    //   316: goto +34 -> 350
    //   319: astore 13
    //   321: getstatic 31	java/util/logging/Level:FINE	Ljava/util/logging/Level;
    //   324: new 32	java/lang/StringBuffer
    //   327: dup
    //   328: invokespecial 33	java/lang/StringBuffer:<init>	()V
    //   331: ldc 82
    //   333: invokevirtual 35	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   336: aload 13
    //   338: invokevirtual 83	java/lang/InterruptedException:getMessage	()Ljava/lang/String;
    //   341: invokevirtual 35	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   344: invokevirtual 37	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   347: invokestatic 38	com/ffusion/util/logging/DebugLog:log	(Ljava/util/logging/Level;Ljava/lang/String;)V
    //   350: jsr +422 -> 772
    //   353: goto +438 -> 791
    //   356: astore 13
    //   358: new 47	java/io/StringWriter
    //   361: dup
    //   362: invokespecial 48	java/io/StringWriter:<init>	()V
    //   365: astore 14
    //   367: aload 13
    //   369: new 49	java/io/PrintWriter
    //   372: dup
    //   373: aload 14
    //   375: invokespecial 50	java/io/PrintWriter:<init>	(Ljava/io/Writer;)V
    //   378: invokevirtual 85	java/lang/Exception:printStackTrace	(Ljava/io/PrintWriter;)V
    //   381: new 32	java/lang/StringBuffer
    //   384: dup
    //   385: invokespecial 33	java/lang/StringBuffer:<init>	()V
    //   388: ldc 86
    //   390: invokevirtual 35	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   393: aload 14
    //   395: invokevirtual 53	java/io/StringWriter:toString	()Ljava/lang/String;
    //   398: invokevirtual 35	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   401: invokevirtual 37	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   404: astore 15
    //   406: getstatic 31	java/util/logging/Level:FINE	Ljava/util/logging/Level;
    //   409: aload 15
    //   411: invokestatic 38	com/ffusion/util/logging/DebugLog:log	(Ljava/util/logging/Level;Ljava/lang/String;)V
    //   414: aload 12
    //   416: ifnonnull +13 -> 429
    //   419: new 39	java/io/IOException
    //   422: dup
    //   423: ldc 65
    //   425: invokespecial 41	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   428: athrow
    //   429: ldc2_w 78
    //   432: invokestatic 80	java/lang/Thread:sleep	(J)V
    //   435: goto +48 -> 483
    //   438: astore 16
    //   440: aload 16
    //   442: new 49	java/io/PrintWriter
    //   445: dup
    //   446: aload 14
    //   448: invokespecial 50	java/io/PrintWriter:<init>	(Ljava/io/Writer;)V
    //   451: invokevirtual 87	java/lang/InterruptedException:printStackTrace	(Ljava/io/PrintWriter;)V
    //   454: getstatic 31	java/util/logging/Level:FINE	Ljava/util/logging/Level;
    //   457: new 32	java/lang/StringBuffer
    //   460: dup
    //   461: invokespecial 33	java/lang/StringBuffer:<init>	()V
    //   464: ldc 82
    //   466: invokevirtual 35	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   469: aload 14
    //   471: invokevirtual 53	java/io/StringWriter:toString	()Ljava/lang/String;
    //   474: invokevirtual 35	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   477: invokevirtual 37	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   480: invokestatic 38	com/ffusion/util/logging/DebugLog:log	(Ljava/util/logging/Level;Ljava/lang/String;)V
    //   483: aload 13
    //   485: astore 16
    //   487: aload 16
    //   489: instanceof 88
    //   492: ifeq +252 -> 744
    //   495: getstatic 31	java/util/logging/Level:FINE	Ljava/util/logging/Level;
    //   498: new 32	java/lang/StringBuffer
    //   501: dup
    //   502: invokespecial 33	java/lang/StringBuffer:<init>	()V
    //   505: ldc 82
    //   507: invokevirtual 35	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   510: aload 16
    //   512: invokevirtual 89	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   515: invokevirtual 35	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   518: invokevirtual 37	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   521: invokestatic 38	com/ffusion/util/logging/DebugLog:log	(Ljava/util/logging/Level;Ljava/lang/String;)V
    //   524: aload 16
    //   526: checkcast 88	javax/mail/SendFailedException
    //   529: astore 17
    //   531: aload 17
    //   533: invokevirtual 90	javax/mail/SendFailedException:getInvalidAddresses	()[Ljavax/mail/Address;
    //   536: astore 18
    //   538: aload 18
    //   540: ifnull +62 -> 602
    //   543: getstatic 31	java/util/logging/Level:FINE	Ljava/util/logging/Level;
    //   546: ldc 91
    //   548: invokestatic 38	com/ffusion/util/logging/DebugLog:log	(Ljava/util/logging/Level;Ljava/lang/String;)V
    //   551: aload 18
    //   553: ifnull +49 -> 602
    //   556: iconst_0
    //   557: istore 19
    //   559: iload 19
    //   561: aload 18
    //   563: arraylength
    //   564: if_icmpge +38 -> 602
    //   567: getstatic 31	java/util/logging/Level:FINE	Ljava/util/logging/Level;
    //   570: new 32	java/lang/StringBuffer
    //   573: dup
    //   574: invokespecial 33	java/lang/StringBuffer:<init>	()V
    //   577: ldc 92
    //   579: invokevirtual 35	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   582: aload 18
    //   584: iload 19
    //   586: aaload
    //   587: invokevirtual 36	java/lang/StringBuffer:append	(Ljava/lang/Object;)Ljava/lang/StringBuffer;
    //   590: invokevirtual 37	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   593: invokestatic 38	com/ffusion/util/logging/DebugLog:log	(Ljava/util/logging/Level;Ljava/lang/String;)V
    //   596: iinc 19 1
    //   599: goto -40 -> 559
    //   602: aload 17
    //   604: invokevirtual 93	javax/mail/SendFailedException:getValidUnsentAddresses	()[Ljavax/mail/Address;
    //   607: astore 19
    //   609: aload 19
    //   611: ifnull +62 -> 673
    //   614: getstatic 31	java/util/logging/Level:FINE	Ljava/util/logging/Level;
    //   617: ldc 94
    //   619: invokestatic 38	com/ffusion/util/logging/DebugLog:log	(Ljava/util/logging/Level;Ljava/lang/String;)V
    //   622: aload 19
    //   624: ifnull +49 -> 673
    //   627: iconst_0
    //   628: istore 20
    //   630: iload 20
    //   632: aload 19
    //   634: arraylength
    //   635: if_icmpge +38 -> 673
    //   638: getstatic 31	java/util/logging/Level:FINE	Ljava/util/logging/Level;
    //   641: new 32	java/lang/StringBuffer
    //   644: dup
    //   645: invokespecial 33	java/lang/StringBuffer:<init>	()V
    //   648: ldc 92
    //   650: invokevirtual 35	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   653: aload 19
    //   655: iload 20
    //   657: aaload
    //   658: invokevirtual 36	java/lang/StringBuffer:append	(Ljava/lang/Object;)Ljava/lang/StringBuffer;
    //   661: invokevirtual 37	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   664: invokestatic 38	com/ffusion/util/logging/DebugLog:log	(Ljava/util/logging/Level;Ljava/lang/String;)V
    //   667: iinc 20 1
    //   670: goto -40 -> 630
    //   673: aload 17
    //   675: invokevirtual 95	javax/mail/SendFailedException:getValidSentAddresses	()[Ljavax/mail/Address;
    //   678: astore 20
    //   680: aload 20
    //   682: ifnull +62 -> 744
    //   685: getstatic 31	java/util/logging/Level:FINE	Ljava/util/logging/Level;
    //   688: ldc 96
    //   690: invokestatic 38	com/ffusion/util/logging/DebugLog:log	(Ljava/util/logging/Level;Ljava/lang/String;)V
    //   693: aload 20
    //   695: ifnull +49 -> 744
    //   698: iconst_0
    //   699: istore 21
    //   701: iload 21
    //   703: aload 20
    //   705: arraylength
    //   706: if_icmpge +38 -> 744
    //   709: getstatic 31	java/util/logging/Level:FINE	Ljava/util/logging/Level;
    //   712: new 32	java/lang/StringBuffer
    //   715: dup
    //   716: invokespecial 33	java/lang/StringBuffer:<init>	()V
    //   719: ldc 92
    //   721: invokevirtual 35	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   724: aload 20
    //   726: iload 21
    //   728: aaload
    //   729: invokevirtual 36	java/lang/StringBuffer:append	(Ljava/lang/Object;)Ljava/lang/StringBuffer;
    //   732: invokevirtual 37	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   735: invokestatic 38	com/ffusion/util/logging/DebugLog:log	(Ljava/util/logging/Level;Ljava/lang/String;)V
    //   738: iinc 21 1
    //   741: goto -40 -> 701
    //   744: aload 16
    //   746: checkcast 97	javax/mail/MessagingException
    //   749: invokevirtual 98	javax/mail/MessagingException:getNextException	()Ljava/lang/Exception;
    //   752: dup
    //   753: astore 16
    //   755: ifnonnull -268 -> 487
    //   758: jsr +14 -> 772
    //   761: goto +30 -> 791
    //   764: astore 22
    //   766: jsr +6 -> 772
    //   769: aload 22
    //   771: athrow
    //   772: astore 23
    //   774: aload 11
    //   776: ifnull +8 -> 784
    //   779: aload 11
    //   781: invokevirtual 99	javax/mail/Transport:close	()V
    //   784: goto +5 -> 789
    //   787: astore 24
    //   789: ret 23
    //   791: iconst_0
    //   792: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	793	0	this	JavaMail
    //   0	793	1	paramString1	String
    //   0	793	2	paramString2	String
    //   0	793	3	paramString3	String
    //   0	793	4	paramString4	String
    //   0	793	5	paramString5	String
    //   0	793	6	paramString6	String
    //   1	289	7	localInternetAddress	InternetAddress
    //   4	302	8	arrayOfInternetAddress	InternetAddress[]
    //   75	12	9	localAddressException	AddressException
    //   150	36	9	localProperties	Properties
    //   84	204	10	localObject1	Object
    //   123	657	11	localObject2	Object
    //   212	203	12	localMimeMessage	MimeMessage
    //   319	18	13	localInterruptedException1	InterruptedException
    //   356	128	13	localException1	Exception
    //   365	105	14	localStringWriter	StringWriter
    //   404	6	15	str	String
    //   438	3	16	localInterruptedException2	InterruptedException
    //   485	269	16	localException2	Exception
    //   529	145	17	localSendFailedException	SendFailedException
    //   536	47	18	arrayOfAddress1	Address[]
    //   557	40	19	i	int
    //   607	47	19	arrayOfAddress2	Address[]
    //   628	40	20	j	int
    //   678	47	20	arrayOfAddress3	Address[]
    //   699	40	21	k	int
    //   764	6	22	localObject3	Object
    //   772	1	23	localObject4	Object
    //   787	1	24	localMessagingException	MessagingException
    // Exception table:
    //   from	to	target	type
    //   27	72	75	javax/mail/internet/AddressException
    //   310	316	319	java/lang/InterruptedException
    //   214	350	356	java/lang/Exception
    //   429	435	438	java/lang/InterruptedException
    //   214	353	764	finally
    //   356	761	764	finally
    //   764	769	764	finally
    //   774	784	787	javax/mail/MessagingException
  }
  
  public int sendBulkMessage(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, String paramString4)
  {
    return sendBulkMessage(paramString1, 0, paramInt1, paramInt2, paramString2, paramString3, paramString4, null);
  }
  
  public int sendBulkMessage(String paramString1, int paramInt1, int paramInt2, int paramInt3, String paramString2, String paramString3, String paramString4, HashMap paramHashMap)
  {
    int i = 0;
    for (int j = 0; j < GlobalMessageConsts.GroupTypes.length; j++) {
      if (paramInt2 == GlobalMessageConsts.GroupTypes[j])
      {
        i = 1;
        break;
      }
    }
    if (i == 0) {
      return 600511;
    }
    try
    {
      GlobalMessage localGlobalMessage = (GlobalMessage)paramHashMap.get("GLOBAL_MESSAGE_KEY");
      ResultSetWrapper localResultSetWrapper = null;
      if (localGlobalMessage.getToGroupTypeValue() == 4) {
        localResultSetWrapper = MessageAdapter.getSpecificCustomersOfGroupType(null, localGlobalMessage);
      } else {
        localResultSetWrapper = MessageAdapter.getCustomersByGroupType(null, paramString1, paramInt1, paramInt2, paramInt3);
      }
      return a(localResultSetWrapper, paramString2, paramString3, paramString4, paramHashMap);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 8505;
  }
  
  private int a(ResultSetWrapper paramResultSetWrapper, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
  {
    int i = 0;
    InternetAddress localInternetAddress = null;
    InternetAddress[] arrayOfInternetAddress = null;
    GlobalMessage localGlobalMessage = (GlobalMessage)paramHashMap.get("GLOBAL_MESSAGE_KEY");
    String str1 = paramString2;
    String str2 = paramString3;
    try
    {
      localInternetAddress = new InternetAddress(paramString1);
    }
    catch (AddressException localAddressException)
    {
      localObject1 = new StringWriter();
      localAddressException.printStackTrace(new PrintWriter((Writer)localObject1));
      localObject2 = "Email transmit 4.5 " + ((StringWriter)localObject1).toString();
      DebugLog.log(Level.FINE, (String)localObject2);
      return 600501;
    }
    Properties localProperties = new Properties();
    localProperties.put("mail.smtp.user", paramString1);
    localProperties.put("mail.smtp.host", this.mHost);
    Object localObject1 = Session.getInstance(localProperties, null);
    if (localObject1 == null) {
      return 600503;
    }
    Object localObject2 = null;
    MimeMessage localMimeMessage = null;
    try
    {
      paramResultSetWrapper.setOrderByLanguage(true);
      localMimeMessage = new MimeMessage((Session)localObject1);
      if (localMimeMessage == null)
      {
        int j = 600504;
        return j;
      }
      localMimeMessage.setFrom(localInternetAddress);
      localMimeMessage.setSentDate(new Date());
      localObject2 = ((Session)localObject1).getTransport(localInternetAddress);
      ((Transport)localObject2).connect();
      localObject4 = new ArrayList();
      UserWrapper localUserWrapper = paramResultSetWrapper.getNextUser();
      localObject5 = (MessagingFilters)paramHashMap.get("messaginFilterKey");
      localObject6 = null;
      String str4 = null;
      while (localUserWrapper != null)
      {
        str4 = localUserWrapper.getPreferredLanguage();
        if ((str4 != null) && (localObject6 != null) && (!str4.equals(localObject6)))
        {
          arrayOfInternetAddress = (InternetAddress[])((ArrayList)localObject4).toArray(new InternetAddress[0]);
          localMimeMessage.setRecipients(Message.RecipientType.BCC, arrayOfInternetAddress);
          ((Transport)localObject2).sendMessage(localMimeMessage, arrayOfInternetAddress);
          try
          {
            Thread.sleep(5L);
          }
          catch (InterruptedException localInterruptedException2)
          {
            StringWriter localStringWriter = new StringWriter();
            localInterruptedException2.printStackTrace(new PrintWriter(localStringWriter));
            String str5 = "Email exception - " + localStringWriter.toString();
            DebugLog.log(Level.FINE, str5);
          }
          localObject4 = new ArrayList();
        }
        if (localGlobalMessage != null)
        {
          str2 = localGlobalMessage.getMsgText(str4);
          str1 = localGlobalMessage.getSubject(str4);
        }
        localMimeMessage.setSubject(str1);
        localMimeMessage.setText(str2, "UTF-8");
        String str3 = localUserWrapper.getName();
        localObject3 = localUserWrapper.getEmail();
        if (localObject5 != null)
        {
          if (((MessagingFilters)localObject5).checkFiltersForDirID(localGlobalMessage, localUserWrapper.getDirectoryID(), paramHashMap)) {
            a(str3, (String)localObject3, (ArrayList)localObject4, arrayOfInternetAddress, localMimeMessage, (Transport)localObject2);
          }
        }
        else {
          a(str3, (String)localObject3, (ArrayList)localObject4, arrayOfInternetAddress, localMimeMessage, (Transport)localObject2);
        }
        localUserWrapper = paramResultSetWrapper.getNextUser();
        localObject6 = str4;
      }
      paramResultSetWrapper.close();
      if (((ArrayList)localObject4).size() != 0)
      {
        arrayOfInternetAddress = (InternetAddress[])((ArrayList)localObject4).toArray(new InternetAddress[0]);
        localMimeMessage.setRecipients(Message.RecipientType.BCC, arrayOfInternetAddress);
        ((Transport)localObject2).sendMessage(localMimeMessage, arrayOfInternetAddress);
      }
    }
    catch (Exception localException1)
    {
      Object localObject5;
      Object localObject6;
      Object localObject3 = new StringWriter();
      localException1.printStackTrace(new PrintWriter((Writer)localObject3));
      Object localObject4 = "sendBulkEmail " + ((StringWriter)localObject3).toString();
      DebugLog.log(Level.FINE, (String)localObject4);
      if (localMimeMessage == null) {
        i = 600505;
      }
      try
      {
        Thread.sleep(5L);
      }
      catch (InterruptedException localInterruptedException1)
      {
        DebugLog.log(Level.FINE, "Email exception - " + localInterruptedException1.getMessage());
      }
      Exception localException2 = localException1;
      do
      {
        if ((localException2 instanceof SendFailedException))
        {
          i = 600505;
          DebugLog.log(Level.FINE, "Email exception - " + localException2.getMessage());
          localObject5 = (SendFailedException)localException2;
          localObject6 = ((SendFailedException)localObject5).getInvalidAddresses();
          if (localObject6 != null)
          {
            DebugLog.log(Level.FINE, "    ** Invalid Addresses");
            if (localObject6 != null) {
              for (int k = 0; k < localObject6.length; k++) {
                DebugLog.log(Level.FINE, "         " + localObject6[k]);
              }
            }
          }
          Address[] arrayOfAddress1 = ((SendFailedException)localObject5).getValidUnsentAddresses();
          if (arrayOfAddress1 != null)
          {
            DebugLog.log(Level.FINE, "    ** ValidUnsent Addresses");
            if (arrayOfAddress1 != null) {
              for (int m = 0; m < arrayOfAddress1.length; m++) {
                DebugLog.log(Level.FINE, "         " + arrayOfAddress1[m]);
              }
            }
          }
          Address[] arrayOfAddress2 = ((SendFailedException)localObject5).getValidSentAddresses();
          if (arrayOfAddress2 != null)
          {
            DebugLog.log(Level.FINE, "    ** ValidSent Addresses");
            if (arrayOfAddress2 != null) {
              for (int n = 0; n < arrayOfAddress2.length; n++) {
                DebugLog.log(Level.FINE, "         " + arrayOfAddress2[n]);
              }
            }
          }
        }
      } while ((localException2 = ((MessagingException)localException2).getNextException()) != null);
    }
    finally
    {
      try
      {
        if (localObject2 != null) {
          ((Transport)localObject2).close();
        }
      }
      catch (MessagingException localMessagingException) {}
    }
    return i;
  }
  
  private void a(String paramString1, String paramString2, ArrayList paramArrayList, InternetAddress[] paramArrayOfInternetAddress, javax.mail.Message paramMessage, Transport paramTransport)
    throws Exception
  {
    if ((paramString2 != null) && (paramString2.length() > 0))
    {
      paramArrayList.add(new InternetAddress(paramString2, paramString1));
      if (paramArrayList.size() == this.mMaxRecipient)
      {
        paramArrayOfInternetAddress = (InternetAddress[])paramArrayList.toArray(new InternetAddress[0]);
        paramMessage.setRecipients(Message.RecipientType.BCC, paramArrayOfInternetAddress);
        paramTransport.sendMessage(paramMessage, paramArrayOfInternetAddress);
        try
        {
          Thread.sleep(5L);
        }
        catch (InterruptedException localInterruptedException)
        {
          DebugLog.log(Level.FINE, "Email exception - " + localInterruptedException.getMessage());
        }
        paramArrayList = new ArrayList();
      }
    }
  }
  
  public class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      JavaMail.this.a(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.javax.JavaMail
 * JD-Core Version:    0.7.0.1
 */