package com.ffusion.ims21.servlet.parse;

import com.ffusion.ims21.util.DateCalc;
import com.ffusion.ims21.util.DemoKey;
import com.ffusion.ims21.util.UrlDoc;
import com.ffusion.ims21.util.XmlReader;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

public class MessageGrabber
{
  String jdField_case;
  String jdField_int;
  MessagePack jdField_for = null;
  String jdField_new = null;
  String jdField_do;
  Object jdField_else = new Object();
  String jdField_try = "";
  int jdField_char = 60;
  b jdField_byte = null;
  BannerStats jdField_if = null;
  String a = "";
  public static final int PRIORITY_HIGH = 2;
  public static final int PRIORITY_NORMAL = 1;
  public static final int PRIORITY_LOW = 0;
  
  public MessageGrabber(String paramString1, String paramString2, int paramInt, String paramString3)
  {
    DebugLog.log("MessageGrabber: imsUrl=" + paramString1);
    this.jdField_case = paramString1;
    this.jdField_int = paramString2;
    this.jdField_char = (paramInt > 15 ? paramInt : 15);
    this.a = paramString3;
    this.jdField_for = new MessagePack();
    this.jdField_if = new BannerStats(this);
  }
  
  public int startIms()
    throws Exception
  {
    synchronized (this.jdField_else)
    {
      UrlDoc localUrlDoc = new UrlDoc();
      localUrlDoc.loadDoc(this.jdField_case, this.jdField_int);
      this.jdField_do = localUrlDoc.getCommandUrl("getImsMsgs");
      if (this.jdField_do == null) {
        throw new Exception("GetIms id failed imsUrl=" + this.jdField_case + "<br>bankId=" + this.jdField_int + "<br>doc=" + localUrlDoc.toString());
      }
      this.jdField_new = localUrlDoc.getCommandUrl("putImsStats");
      String str = this.a + XmlReader.getFilenameFromPath(this.jdField_do);
      int i = this.jdField_for.loadMessages(str);
      if (this.jdField_do == null) {
        return -1;
      }
      a();
      return i;
    }
  }
  
  public void stopIms()
    throws Exception
  {
    jdField_if();
  }
  
  private void a()
  {
    if (this.jdField_byte == null)
    {
      this.jdField_byte = new b(this.jdField_char);
      this.jdField_byte.start();
    }
  }
  
  private void jdField_if()
  {
    if (this.jdField_byte != null) {
      this.jdField_byte = null;
    }
  }
  
  public MessagePack getMessagePack()
  {
    synchronized (this.jdField_else)
    {
      return this.jdField_for;
    }
  }
  
  public int getMessageCount()
  {
    return getMessagePack().getMessageVector().size();
  }
  
  private Vector a(Vector paramVector, int[] paramArrayOfInt)
  {
    Vector localVector = null;
    if (paramVector != null)
    {
      localVector = new Vector();
      for (int i = 0; i < paramVector.size(); i++)
      {
        ImsMsg localImsMsg = (ImsMsg)paramVector.elementAt(i);
        if (localImsMsg.checkAccountRules(paramArrayOfInt)) {
          localVector.addElement(localImsMsg);
        }
      }
    }
    return localVector;
  }
  
  private Vector a(HashMap paramHashMap, long paramLong, String[] paramArrayOfString, DemoKey paramDemoKey)
  {
    Vector localVector1 = getMessagePack().getMessageVector();
    if (localVector1 == null) {
      return null;
    }
    int i = localVector1.size();
    if (i == 0) {
      return null;
    }
    DemoKey localDemoKey = paramDemoKey;
    if (localDemoKey == null) {
      localDemoKey = new DemoKey(paramLong);
    }
    Vector localVector2 = new Vector();
    for (int j = 0; j < i; j++)
    {
      ImsMsg localImsMsg = (ImsMsg)localVector1.elementAt(j);
      if ((localImsMsg.isValidDestination(paramArrayOfString)) && (localImsMsg.checkState(localDemoKey))) {
        if (localImsMsg.getBirthDayMessage())
        {
          int k = 0;
          DateCalc localDateCalc = new DateCalc();
          if ((localDemoKey.getBirthMonth() != localDateCalc.getMonth()) || (localDemoKey.getBirthDay() != localDateCalc.getDay())) {}
        }
        else if ((localImsMsg.getTargeting() == 0L) && (!localImsMsg.hasCustomTags()))
        {
          localVector2.addElement(localImsMsg);
        }
        else if (localImsMsg.getTargetingKey().qualifies(localDemoKey))
        {
          if (localImsMsg.hasCustomTags())
          {
            if ((paramHashMap != null) && (localImsMsg.compareTags(paramHashMap))) {
              localVector2.addElement(localImsMsg);
            }
          }
          else {
            localVector2.addElement(localImsMsg);
          }
        }
      }
    }
    return localVector2;
  }
  
  public Vector getTextMessages(long paramLong, int[] paramArrayOfInt, int paramInt, String[] paramArrayOfString)
  {
    return getTextMessages(null, paramLong, paramArrayOfInt, null, paramInt, paramArrayOfString, null);
  }
  
  public Vector getTextMessages(HashMap paramHashMap, long paramLong, int[] paramArrayOfInt, int paramInt, String[] paramArrayOfString)
  {
    return getTextMessages(paramHashMap, paramLong, paramArrayOfInt, null, paramInt, paramArrayOfString, null);
  }
  
  public Vector getTextMessages(HashMap paramHashMap, long paramLong, int[] paramArrayOfInt, String paramString, int paramInt, String[] paramArrayOfString, DemoKey paramDemoKey)
  {
    Vector localVector1 = null;
    ImsMsg localImsMsg = null;
    Vector localVector2 = a(a(paramHashMap, paramLong, paramArrayOfString, paramDemoKey), paramArrayOfInt);
    if ((localVector2 != null) && (localVector2.size() > 0))
    {
      Vector localVector3 = 0;
      localVector1 = new Vector();
      Vector localVector5;
      if (paramString == null)
      {
        if (paramInt > localVector2.size()) {
          paramInt = localVector2.size();
        }
        Vector localVector4 = a(localVector2);
        localVector5 = a(localVector2, 2);
        Vector localVector6 = a(localVector2, 1);
        Vector localVector7 = a(localVector2, 0);
        localVector2 = jdField_if(localVector4, localVector5, localVector6, localVector7);
        for (int k = 0; k < paramInt; k++) {
          localVector1.addElement((ImsMsg)localVector2.elementAt(k));
        }
      }
      else
      {
        for (int i = 0; i < localVector2.size(); i++)
        {
          localImsMsg = (ImsMsg)localVector2.elementAt(i);
          if (localImsMsg.getMessageId().equals(paramString))
          {
            localVector3 = i + 1;
            break;
          }
        }
        i = 0;
        int j;
        for (localVector5 = localVector3; i < paramInt; j++)
        {
          if (localVector5 >= localVector2.size()) {
            j = 0;
          }
          localVector1.addElement((ImsMsg)localVector2.elementAt(j));
          i++;
        }
      }
    }
    return localVector1;
  }
  
  public ImsMsg getBanner(long paramLong, int[] paramArrayOfInt, String paramString, String[] paramArrayOfString, boolean paramBoolean)
  {
    return getBanner(null, paramLong, paramArrayOfInt, paramString, paramArrayOfString, paramBoolean, null);
  }
  
  public ImsMsg getBanner(HashMap paramHashMap, long paramLong, int[] paramArrayOfInt, String paramString, String[] paramArrayOfString, boolean paramBoolean)
  {
    return getBanner(paramHashMap, paramLong, paramArrayOfInt, paramString, paramArrayOfString, paramBoolean, null);
  }
  
  public ImsMsg getBanner(HashMap paramHashMap, long paramLong, int[] paramArrayOfInt, String paramString, String[] paramArrayOfString, boolean paramBoolean, DemoKey paramDemoKey)
  {
    ImsMsg localImsMsg = null;
    Vector localVector1 = a(a(paramHashMap, paramLong, paramArrayOfString, paramDemoKey), paramArrayOfInt);
    if ((localVector1 != null) && (localVector1.size() > 0))
    {
      int i = 0;
      if (paramString == null)
      {
        Vector localVector2 = a(localVector1);
        Vector localVector3 = a(localVector1, 2);
        Vector localVector4 = a(localVector1, 1);
        Vector localVector5 = a(localVector1, 0);
        localVector1 = jdField_do(localVector2, localVector3, localVector4, localVector5);
        String str = ((ImsMsg)localVector1.elementAt(0)).getMessageId();
        i = a(str, localVector1);
      }
      else
      {
        for (int j = 0; j < localVector1.size(); j++)
        {
          localImsMsg = (ImsMsg)localVector1.elementAt(j);
          if (localImsMsg.getMessageId().equals(paramString))
          {
            if (!paramBoolean) {
              break;
            }
            i = j + 1;
            break;
          }
        }
      }
      if (i >= localVector1.size()) {
        i = 0;
      }
      localImsMsg = (ImsMsg)localVector1.elementAt(i);
      DebugLog.log("\t" + localImsMsg.toString());
    }
    return localImsMsg;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < getMessageCount(); i++) {
      localStringBuffer.append(getMessagePack().getNextMessage().toString()).append("<br>\n");
    }
    return localStringBuffer.toString();
  }
  
  public String getStats()
  {
    return this.jdField_if.getStats();
  }
  
  public void pushStats()
    throws Exception
  {
    pushStats(this.jdField_if.getStats());
  }
  
  public void pushStats(String paramString)
    throws Exception
  {
    if ((paramString != null) && (!a(paramString)))
    {
      this.jdField_try = paramString;
      throw new Exception("put stats failed!");
    }
  }
  
  private boolean a(String paramString)
  {
    if (this.jdField_int != null)
    {
      String str = this.a;
      str = str + "ST" + this.jdField_int + ".DAT";
      RandomAccessFile localRandomAccessFile = null;
      try
      {
        localRandomAccessFile = new RandomAccessFile(str, "rw");
        localRandomAccessFile.seek(localRandomAccessFile.length());
        localRandomAccessFile.writeBytes(paramString);
        localRandomAccessFile.close();
      }
      catch (IOException localIOException1)
      {
        try
        {
          if (localRandomAccessFile != null) {
            localRandomAccessFile.close();
          }
        }
        catch (IOException localIOException2) {}
        return false;
      }
    }
    return true;
  }
  
  public String getActionUrl(String paramString)
  {
    Vector localVector = getMessagePack().getMessageVector();
    int i = localVector.size();
    String str = null;
    if (i != 0) {
      if (paramString == null)
      {
        ImsMsg localImsMsg1 = (ImsMsg)localVector.elementAt(0);
        str = localImsMsg1.getActionUrl();
      }
      else
      {
        for (int j = 0; j < i; j++)
        {
          ImsMsg localImsMsg2 = (ImsMsg)localVector.elementAt(j);
          if (localImsMsg2.getMessageId().equals(paramString)) {
            str = localImsMsg2.getActionUrl();
          }
        }
      }
    }
    return str;
  }
  
  public ImsMsg getImsMsg(String paramString)
  {
    Object localObject = null;
    Vector localVector = getMessagePack().getMessageVector();
    int i = localVector.size();
    if (i != 0) {
      for (int j = 0; j < i; j++)
      {
        ImsMsg localImsMsg = (ImsMsg)localVector.elementAt(j);
        if (localImsMsg.getMessageId().equals(paramString)) {
          localObject = localImsMsg;
        }
      }
    }
    return localObject;
  }
  
  public BannerStats getBannerStats()
  {
    return this.jdField_if;
  }
  
  private String jdField_if(Vector paramVector)
  {
    if ((paramVector == null) || (paramVector.size() == 0)) {
      return "-1";
    }
    Random localRandom = new Random();
    int i = localRandom.nextInt(paramVector.size());
    ImsMsg localImsMsg = (ImsMsg)paramVector.get(i);
    return localImsMsg.getMessageId();
  }
  
  private int a(String paramString, Vector paramVector)
  {
    int i = 0;
    for (int j = 0; j < paramVector.size(); j++)
    {
      ImsMsg localImsMsg = (ImsMsg)paramVector.elementAt(j);
      if (localImsMsg.getMessageId().equals(paramString))
      {
        i = j;
        break;
      }
    }
    return i;
  }
  
  private Vector a(Vector paramVector)
  {
    Vector localVector = new Vector();
    for (int i = 0; i < paramVector.size(); i++)
    {
      ImsMsg localImsMsg = (ImsMsg)paramVector.elementAt(i);
      if (localImsMsg.getBirthDayMessage() == true) {
        localVector.addElement(localImsMsg);
      }
    }
    return localVector;
  }
  
  private Vector a(Vector paramVector, int paramInt)
  {
    Vector localVector = new Vector();
    for (int i = 0; i < paramVector.size(); i++)
    {
      ImsMsg localImsMsg = (ImsMsg)paramVector.elementAt(i);
      if ((localImsMsg.getPriority() == paramInt) && (!localImsMsg.getBirthDayMessage())) {
        localVector.addElement(localImsMsg);
      }
    }
    return localVector;
  }
  
  private Vector jdField_do(Vector paramVector1, Vector paramVector2, Vector paramVector3, Vector paramVector4)
  {
    String str1 = jdField_if(paramVector2);
    String str2 = jdField_if(paramVector3);
    String str3 = jdField_if(paramVector4);
    a(paramVector2, str1);
    a(paramVector3, str2);
    a(paramVector4, str3);
    Vector localVector = a(paramVector1, paramVector2, paramVector3, paramVector4);
    return localVector;
  }
  
  private Vector jdField_if(Vector paramVector1, Vector paramVector2, Vector paramVector3, Vector paramVector4)
  {
    a locala = new a(null);
    Collections.sort(paramVector2, locala);
    Collections.sort(paramVector3, locala);
    Collections.sort(paramVector4, locala);
    Vector localVector = a(paramVector1, paramVector2, paramVector3, paramVector4);
    return localVector;
  }
  
  private Vector a(Vector paramVector1, Vector paramVector2, Vector paramVector3, Vector paramVector4)
  {
    Vector localVector = new Vector();
    for (int i = 0; i < paramVector1.size(); i++) {
      localVector.addElement(paramVector1.elementAt(i));
    }
    for (i = 0; i < paramVector2.size(); i++) {
      localVector.addElement(paramVector2.elementAt(i));
    }
    for (i = 0; i < paramVector3.size(); i++) {
      localVector.addElement(paramVector3.elementAt(i));
    }
    for (i = 0; i < paramVector4.size(); i++) {
      localVector.addElement(paramVector4.elementAt(i));
    }
    return localVector;
  }
  
  private void a(Vector paramVector, String paramString)
  {
    if (paramVector.size() > 0)
    {
      int i = a(paramString, paramVector);
      if (i != 0)
      {
        Vector localVector = new Vector();
        localVector.addElement(paramVector.elementAt(i));
        for (int j = i + 1; j < paramVector.size(); j++) {
          localVector.addElement(paramVector.elementAt(j));
        }
        for (j = 0; j < i; j++) {
          localVector.addElement(paramVector.elementAt(j));
        }
        for (j = 0; j < localVector.size(); j++) {
          paramVector.set(j, localVector.elementAt(j));
        }
      }
    }
  }
  
  private class a
    implements Comparator
  {
    private a() {}
    
    public int compare(Object paramObject1, Object paramObject2)
    {
      int i = Integer.parseInt(((ImsMsg)paramObject1).getMessageId());
      int j = Integer.parseInt(((ImsMsg)paramObject2).getMessageId());
      if (i < j) {
        return 1;
      }
      if (i > j) {
        return -1;
      }
      return 0;
    }
    
    a(MessageGrabber.1 param1)
    {
      this();
    }
  }
  
  private class b
    extends Thread
  {
    int a;
    Calendar jdField_if;
    
    public b(int paramInt)
    {
      this.a = (paramInt * 60000);
      this.jdField_if = Calendar.getInstance();
    }
    
    public void run()
    {
      try
      {
        for (;;)
        {
          sleep(this.a);
          MessageGrabber.this.pushStats();
          Calendar localCalendar = Calendar.getInstance();
          if (localCalendar.get(6) != this.jdField_if.get(6)) {
            MessageGrabber.this.startIms();
          }
        }
      }
      catch (Exception localException) {}
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.ims21.servlet.parse.MessageGrabber
 * JD-Core Version:    0.7.0.1
 */