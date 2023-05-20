package com.ffusion.checkimaging.adapters;

import com.ffusion.beans.checkimaging.ImageResult;
import com.ffusion.beans.checkimaging.ImageResults;
import com.ffusion.checkimaging.CheckImagingException;
import java.io.IOException;
import java.util.Iterator;

class ImageStatusNotifyThread
  extends Thread
  implements CheckImageDefines
{
  private ImageResults Wq;
  private String Wp;
  private int Wo = 0;
  
  public ImageStatusNotifyThread(ImageResults paramImageResults, String paramString)
  {
    try
    {
      this.Wq = ((ImageResults)paramImageResults.clone());
    }
    catch (Exception localException)
    {
      this.Wo = 1;
    }
    this.Wp = paramString;
  }
  
  public int getInitStatus()
  {
    return this.Wo;
  }
  
  public void run()
  {
    Iterator localIterator = null;
    Thread localThread = null;
    ImageResult localImageResult1 = null;
    ImageResult localImageResult2 = null;
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    if (this.Wo == 0)
    {
      localImageResult1 = new ImageResult();
      localThread = Thread.currentThread();
      i = CheckImagingAdapter.getMaxRetries();
      j = CheckImagingAdapter.getWaitLength();
      k = 1;
      localIterator = this.Wq.iterator();
      while (localIterator.hasNext())
      {
        m = 0;
        localImageResult1 = (ImageResult)localIterator.next();
        while ((k != 0) && (m != i))
        {
          localImageResult2 = new ImageResult();
          try
          {
            localImageResult2 = CheckImagingAdapter.getImageStatus(null, localImageResult1);
          }
          catch (CheckImagingException localCheckImagingException)
          {
            k = 1;
          }
          if ((localImageResult2.getStatus() == "Complete") || (localImageResult2.getStatus() == "AVAILABLE")) {
            k = 0;
          } else {
            try
            {
              Thread.sleep(j);
            }
            catch (Exception localException) {}
          }
          m += 1;
        }
        if (k == 0) {}
      }
      jdMethod_case(k);
    }
  }
  
  private boolean jdMethod_case(int paramInt)
  {
    int i = 0;
    String str1;
    String str2;
    if (paramInt == 0)
    {
      str1 = "Check Image is now available";
      str2 = "The check image that you requested is now available.";
    }
    else
    {
      str1 = "Failed to retrieve check image";
      str2 = "Unable to retrieve requested check image.";
    }
    try
    {
      i = b(str1, str2);
    }
    catch (IOException localIOException)
    {
      i = 1;
    }
    return i == 0;
  }
  
  /* Error */
  private int b(String paramString1, String paramString2)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aconst_null
    //   3: astore 4
    //   5: new 29	javax/mail/internet/InternetAddress
    //   8: dup
    //   9: invokestatic 30	com/ffusion/checkimaging/adapters/CheckImagingAdapter:getEmailFrom	()Ljava/lang/String;
    //   12: invokespecial 31	javax/mail/internet/InternetAddress:<init>	(Ljava/lang/String;)V
    //   15: astore_3
    //   16: aload_0
    //   17: getfield 7	com/ffusion/checkimaging/adapters/ImageStatusNotifyThread:Wp	Ljava/lang/String;
    //   20: iconst_0
    //   21: invokestatic 32	javax/mail/internet/InternetAddress:parse	(Ljava/lang/String;Z)[Ljavax/mail/internet/InternetAddress;
    //   24: astore 4
    //   26: goto +15 -> 41
    //   29: astore 5
    //   31: new 28	java/io/IOException
    //   34: dup
    //   35: ldc 34
    //   37: invokespecial 35	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   40: athrow
    //   41: new 36	java/util/Properties
    //   44: dup
    //   45: invokespecial 37	java/util/Properties:<init>	()V
    //   48: astore 5
    //   50: aload 5
    //   52: ldc 38
    //   54: invokestatic 39	com/ffusion/checkimaging/adapters/CheckImagingAdapter:getSMTPHost	()Ljava/lang/String;
    //   57: invokevirtual 40	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   60: pop
    //   61: aload 5
    //   63: aconst_null
    //   64: invokestatic 41	javax/mail/Session:getInstance	(Ljava/util/Properties;Ljavax/mail/Authenticator;)Ljavax/mail/Session;
    //   67: astore 6
    //   69: aload 6
    //   71: ifnonnull +13 -> 84
    //   74: new 28	java/io/IOException
    //   77: dup
    //   78: ldc 42
    //   80: invokespecial 35	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   83: athrow
    //   84: aconst_null
    //   85: astore 7
    //   87: aconst_null
    //   88: astore 8
    //   90: new 43	javax/mail/internet/MimeMessage
    //   93: dup
    //   94: aload 6
    //   96: invokespecial 44	javax/mail/internet/MimeMessage:<init>	(Ljavax/mail/Session;)V
    //   99: astore 8
    //   101: aload 8
    //   103: ifnonnull +13 -> 116
    //   106: new 28	java/io/IOException
    //   109: dup
    //   110: ldc 45
    //   112: invokespecial 35	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   115: athrow
    //   116: aload 8
    //   118: aload_3
    //   119: invokevirtual 46	javax/mail/Message:setFrom	(Ljavax/mail/Address;)V
    //   122: aload 8
    //   124: getstatic 47	javax/mail/Message$RecipientType:TO	Ljavax/mail/Message$RecipientType;
    //   127: aload 4
    //   129: invokevirtual 48	javax/mail/Message:setRecipients	(Ljavax/mail/Message$RecipientType;[Ljavax/mail/Address;)V
    //   132: aload 8
    //   134: aload_1
    //   135: invokevirtual 49	javax/mail/Message:setSubject	(Ljava/lang/String;)V
    //   138: aload 8
    //   140: new 50	java/util/Date
    //   143: dup
    //   144: invokespecial 51	java/util/Date:<init>	()V
    //   147: invokevirtual 52	javax/mail/Message:setSentDate	(Ljava/util/Date;)V
    //   150: aload 8
    //   152: aload_2
    //   153: ldc 53
    //   155: invokevirtual 54	javax/mail/Message:setContent	(Ljava/lang/Object;Ljava/lang/String;)V
    //   158: aload 8
    //   160: invokevirtual 55	javax/mail/Message:saveChanges	()V
    //   163: aload 6
    //   165: aload_3
    //   166: invokevirtual 56	javax/mail/Session:getTransport	(Ljavax/mail/Address;)Ljavax/mail/Transport;
    //   169: astore 7
    //   171: aload 7
    //   173: invokevirtual 57	javax/mail/Transport:connect	()V
    //   176: aload 7
    //   178: aload 8
    //   180: aload 4
    //   182: invokevirtual 58	javax/mail/Transport:sendMessage	(Ljavax/mail/Message;[Ljavax/mail/Address;)V
    //   185: ldc2_w 59
    //   188: invokestatic 21	java/lang/Thread:sleep	(J)V
    //   191: goto +5 -> 196
    //   194: astore 9
    //   196: jsr +183 -> 379
    //   199: goto +199 -> 398
    //   202: astore 9
    //   204: aload 8
    //   206: ifnonnull +13 -> 219
    //   209: new 28	java/io/IOException
    //   212: dup
    //   213: ldc 45
    //   215: invokespecial 35	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   218: athrow
    //   219: ldc2_w 59
    //   222: invokestatic 21	java/lang/Thread:sleep	(J)V
    //   225: goto +5 -> 230
    //   228: astore 10
    //   230: aload 9
    //   232: astore 10
    //   234: aload 10
    //   236: instanceof 62
    //   239: ifeq +112 -> 351
    //   242: aload 10
    //   244: checkcast 62	javax/mail/SendFailedException
    //   247: astore 11
    //   249: aload 11
    //   251: invokevirtual 63	javax/mail/SendFailedException:getInvalidAddresses	()[Ljavax/mail/Address;
    //   254: astore 12
    //   256: aload 12
    //   258: ifnull +25 -> 283
    //   261: aload 12
    //   263: ifnull +20 -> 283
    //   266: iconst_0
    //   267: istore 13
    //   269: iload 13
    //   271: aload 12
    //   273: arraylength
    //   274: if_icmpge +9 -> 283
    //   277: iinc 13 1
    //   280: goto -11 -> 269
    //   283: aload 11
    //   285: invokevirtual 64	javax/mail/SendFailedException:getValidUnsentAddresses	()[Ljavax/mail/Address;
    //   288: astore 13
    //   290: aload 13
    //   292: ifnull +25 -> 317
    //   295: aload 13
    //   297: ifnull +20 -> 317
    //   300: iconst_0
    //   301: istore 14
    //   303: iload 14
    //   305: aload 13
    //   307: arraylength
    //   308: if_icmpge +9 -> 317
    //   311: iinc 14 1
    //   314: goto -11 -> 303
    //   317: aload 11
    //   319: invokevirtual 65	javax/mail/SendFailedException:getValidSentAddresses	()[Ljavax/mail/Address;
    //   322: astore 14
    //   324: aload 14
    //   326: ifnull +25 -> 351
    //   329: aload 14
    //   331: ifnull +20 -> 351
    //   334: iconst_0
    //   335: istore 15
    //   337: iload 15
    //   339: aload 14
    //   341: arraylength
    //   342: if_icmpge +9 -> 351
    //   345: iinc 15 1
    //   348: goto -11 -> 337
    //   351: aload 10
    //   353: checkcast 66	javax/mail/MessagingException
    //   356: invokevirtual 67	javax/mail/MessagingException:getNextException	()Ljava/lang/Exception;
    //   359: dup
    //   360: astore 10
    //   362: ifnonnull -128 -> 234
    //   365: jsr +14 -> 379
    //   368: goto +30 -> 398
    //   371: astore 16
    //   373: jsr +6 -> 379
    //   376: aload 16
    //   378: athrow
    //   379: astore 17
    //   381: aload 7
    //   383: ifnull +8 -> 391
    //   386: aload 7
    //   388: invokevirtual 68	javax/mail/Transport:close	()V
    //   391: goto +5 -> 396
    //   394: astore 18
    //   396: ret 17
    //   398: iconst_0
    //   399: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	400	0	this	ImageStatusNotifyThread
    //   0	400	1	paramString1	String
    //   0	400	2	paramString2	String
    //   1	165	3	localInternetAddress	javax.mail.internet.InternetAddress
    //   3	178	4	arrayOfInternetAddress	javax.mail.internet.InternetAddress[]
    //   29	1	5	localAddressException	javax.mail.internet.AddressException
    //   48	14	5	localProperties	java.util.Properties
    //   67	97	6	localSession	javax.mail.Session
    //   85	302	7	localTransport	javax.mail.Transport
    //   88	117	8	localMimeMessage	javax.mail.internet.MimeMessage
    //   194	1	9	localInterruptedException1	java.lang.InterruptedException
    //   202	29	9	localException1	Exception
    //   228	1	10	localInterruptedException2	java.lang.InterruptedException
    //   232	129	10	localException2	Exception
    //   247	71	11	localSendFailedException	javax.mail.SendFailedException
    //   254	18	12	arrayOfAddress1	javax.mail.Address[]
    //   267	11	13	i	int
    //   288	18	13	arrayOfAddress2	javax.mail.Address[]
    //   301	11	14	j	int
    //   322	18	14	arrayOfAddress3	javax.mail.Address[]
    //   335	11	15	k	int
    //   371	6	16	localObject1	java.lang.Object
    //   379	1	17	localObject2	java.lang.Object
    //   394	1	18	localMessagingException	javax.mail.MessagingException
    // Exception table:
    //   from	to	target	type
    //   5	26	29	javax/mail/internet/AddressException
    //   185	191	194	java/lang/InterruptedException
    //   90	196	202	java/lang/Exception
    //   219	225	228	java/lang/InterruptedException
    //   90	199	371	finally
    //   202	368	371	finally
    //   371	376	371	finally
    //   381	391	394	javax/mail/MessagingException
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.checkimaging.adapters.ImageStatusNotifyThread
 * JD-Core Version:    0.7.0.1
 */