package com.ffusion.services.ofx;

import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.XMLHandler;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Calendar;

class Connect
  implements Serializable
{
  private static final String jdField_new = " HTTP/1.0\r\nConnection:Keep-Alive\r\nPragma:no-cache\r\nContent-type:application/x-ofx\r\nUser-Agent:HFN 0100\r\nAccept:application/x-ofx\r\nHost:";
  private static final String jdField_do = "\r\nContent-Length:";
  private static final String jdField_case = "OFXHEADER:100\r\nDATA:OFXSGML\r\nVERSION:102\r\nSECURITY:NONE\r\nENCODING:USASCII\r\nCHARSET:1252\r\nCOMPRESSION:NONE\r\n";
  private static final String jdField_char = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n";
  private static final String jdField_else = "<?OFX OFXHEADER=\"200\" VERSION=\"200\" SECURITY=\"NONE\" OLDFILEUID=\"";
  private static final String jdField_int = "\" NEWFILEUID=\"";
  private static final String jdField_for = "\"?>\r\n";
  private a jdField_if = null;
  private Base jdField_byte;
  private String jdField_try;
  private static final String a = "yyyy/MM/dd HH:mm:ss.SSS";
  
  protected Connect(Base paramBase, String paramString1, String paramString2)
    throws Exception
  {
    this.jdField_byte = paramBase;
    if (this.jdField_if == null) {
      a(paramString1, paramString2);
    }
    if (paramBase.errorRecovery == true) {
      this.jdField_try = "0";
    } else {
      this.jdField_try = "NONE";
    }
  }
  
  protected Connect() {}
  
  private void a(String paramString1, String paramString2)
    throws Exception
  {
    String str1 = paramString1.toLowerCase();
    boolean bool;
    if (str1.startsWith("https://"))
    {
      paramString1 = "http://" + paramString1.substring(8);
      bool = true;
    }
    else
    {
      bool = false;
    }
    URL localURL = new URL(paramString1);
    int i = localURL.getPort();
    if (i == -1) {
      if (bool) {
        i = 443;
      } else {
        i = 80;
      }
    }
    String str2 = "POST " + localURL.getFile() + " HTTP/1.0\r\nConnection:Keep-Alive\r\nPragma:no-cache\r\nContent-type:application/x-ofx\r\nUser-Agent:HFN 0100\r\nAccept:application/x-ofx\r\nHost:" + localURL.getHost() + "\r\nContent-Length:";
    this.jdField_if = new a(bool, localURL.getHost(), i, str2, null);
  }
  
  protected Socket open()
    throws UnknownHostException, IOException
  {
    Socket localSocket = null;
    this.jdField_byte.fireMessage("****************** Opening Connection ******************");
    this.jdField_byte.fireMessage(this.jdField_if.jdField_if);
    if (!this.jdField_if.jdField_int) {
      localSocket = new Socket(this.jdField_if.jdField_for, this.jdField_if.jdField_do);
    }
    this.jdField_byte.fireMessage("****************** Connection is open ******************");
    return localSocket;
  }
  
  protected void sendMessage(char[] paramArrayOfChar, int paramInt, Socket paramSocket)
    throws IOException
  {
    if (paramInt > 0)
    {
      String str1 = "";
      if (this.jdField_byte.errorRecovery == true) {
        str1 = this.jdField_byte.getUniqueSeqNum();
      } else {
        str1 = "NONE";
      }
      StringBuffer localStringBuffer1;
      if (this.jdField_byte.getOFXVersion() < 200)
      {
        localStringBuffer1 = new StringBuffer("OFXHEADER:100\r\nDATA:OFXSGML\r\nVERSION:102\r\nSECURITY:NONE\r\nENCODING:USASCII\r\nCHARSET:1252\r\nCOMPRESSION:NONE\r\n");
        localStringBuffer1.append("OLDFILEUID:");
        localStringBuffer1.append(this.jdField_try);
        localStringBuffer1.append("\r\n");
        localStringBuffer1.append("NEWFILEUID:");
        localStringBuffer1.append(str1);
        localStringBuffer1.append("\r\n\r\n");
      }
      else
      {
        localStringBuffer1 = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n");
        localStringBuffer1.append("<?OFX OFXHEADER=\"200\" VERSION=\"200\" SECURITY=\"NONE\" OLDFILEUID=\"");
        localStringBuffer1.append(this.jdField_try);
        localStringBuffer1.append("\" NEWFILEUID=\"");
        localStringBuffer1.append(str1);
        localStringBuffer1.append("\"?>\r\n");
      }
      StringBuffer localStringBuffer2 = new StringBuffer(localStringBuffer1.toString());
      localStringBuffer1.append(paramArrayOfChar, 0, paramInt);
      if (this.jdField_byte.getOFXVersion() >= 200) {}
      String str2 = localStringBuffer1.toString();
      this.jdField_byte.fireMessage("Time before send: " + DateFormatUtil.getFormatter("yyyy/MM/dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime()));
      BufferedWriter localBufferedWriter = new BufferedWriter(new OutputStreamWriter(paramSocket.getOutputStream()));
      localBufferedWriter.write(this.jdField_if.a);
      localBufferedWriter.write(Integer.toString(str2.length()) + "\r\n\r\n");
      localBufferedWriter.write(str2);
      localBufferedWriter.flush();
      this.jdField_try = str1;
      if (this.jdField_byte.getOFXVersion() < 200)
      {
        StringBuffer localStringBuffer3 = new StringBuffer();
        Base.OFX_Formatter(localStringBuffer3, new String(paramArrayOfChar), "");
        localStringBuffer2.append(localStringBuffer3);
        str2 = localStringBuffer2.toString();
      }
      a(str2);
    }
  }
  
  protected char[] receiveMessage(Socket paramSocket)
    throws IOException
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramSocket.getInputStream()));
    this.jdField_byte.fireMessage("Time before receive: " + DateFormatUtil.getFormatter("yyyy/MM/dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime()));
    int i = jdField_if(localBufferedReader);
    char[] arrayOfChar = a(localBufferedReader, i);
    this.jdField_byte.fireMessage("Time after receive: " + DateFormatUtil.getFormatter("yyyy/MM/dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime()));
    this.jdField_byte.fireMessage("****************** Received Message ******************");
    Object localObject;
    if (this.jdField_byte.getOFXVersion() >= 200)
    {
      localObject = new String(arrayOfChar);
      int j = ((String)localObject).indexOf("<OFX>");
      String str = "";
      if (j != -1)
      {
        str = ((String)localObject).substring(0, j);
        localObject = XMLHandler.format(((String)localObject).substring(j));
      }
      this.jdField_byte.fireMessage(str + XMLHandler.format((String)localObject));
    }
    else
    {
      localObject = new StringBuffer();
      Base.OFX_Formatter((StringBuffer)localObject, new String(arrayOfChar), "");
      this.jdField_byte.fireMessage(((StringBuffer)localObject).toString());
    }
    return arrayOfChar;
  }
  
  protected void close(Socket paramSocket)
  {
    try
    {
      if (paramSocket != null)
      {
        paramSocket.close();
        paramSocket = null;
      }
    }
    catch (Throwable localThrowable) {}
    this.jdField_byte.fireMessage("****************** Close Connection ******************");
  }
  
  private void a(String paramString)
  {
    this.jdField_byte.fireMessage("****************** Sent Message ******************");
    this.jdField_byte.fireMessage(this.jdField_if.a);
    int i;
    Object localObject;
    if (this.jdField_byte.getOFXVersion() >= 200)
    {
      i = paramString.indexOf("<OFX>");
      localObject = "";
      if (i != -1)
      {
        localObject = paramString.substring(0, i);
        paramString = XMLHandler.format(paramString.substring(i));
        this.jdField_byte.fireMessage(localObject);
      }
    }
    if (this.jdField_byte.getDebugService())
    {
      this.jdField_byte.fireMessage(paramString);
    }
    else
    {
      i = paramString.indexOf("<USERID>");
      if (i != -1)
      {
        i += 8;
        localObject = new StringBuffer(paramString.substring(0, i));
        for (int j = paramString.charAt(i); (j != 13) && (j != 10) && (j != 60); j = paramString.charAt(i)) {
          i++;
        }
        ((StringBuffer)localObject).append("********");
        int k = i;
        i = paramString.indexOf("<USERPASS>", i);
        if (i != -1)
        {
          i += 10;
          ((StringBuffer)localObject).append(paramString.substring(k, i));
          for (j = paramString.charAt(i); (j != 13) && (j != 10) && (j != 60); j = paramString.charAt(i)) {
            i++;
          }
          ((StringBuffer)localObject).append("********");
          ((StringBuffer)localObject).append(paramString.substring(i));
          this.jdField_byte.fireMessage(((StringBuffer)localObject).toString());
        }
      }
    }
  }
  
  private int jdField_if(BufferedReader paramBufferedReader)
    throws IOException
  {
    int i = -1;
    String str1 = paramBufferedReader.readLine();
    this.jdField_byte.fireMessage("Time after first read: " + DateFormatUtil.getFormatter("yyyy/MM/dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime()));
    while (str1 != null)
    {
      str1 = str1.toLowerCase();
      int j = str1.indexOf("content-length:");
      if (j != -1)
      {
        j += 15;
        String str2 = str1.substring(j);
        str2 = str2.trim();
        i = Integer.valueOf(str2).intValue();
      }
      else
      {
        j = str1.indexOf("http/");
        if (j != -1)
        {
          j = str1.indexOf(' ', j);
          int k = str1.indexOf(' ', j + 1);
          String str3 = str1.substring(j + 1, k);
          int m = Integer.valueOf(str3).intValue();
          if (m != 0) {
            if (m == 500) {
              this.jdField_byte.logError(4);
            } else if (m == 400) {
              this.jdField_byte.logError(2);
            } else if ((m != 200) && (m != 100)) {
              this.jdField_byte.logError(4);
            }
          }
        }
        if (str1.length() == 0) {
          break;
        }
      }
      str1 = paramBufferedReader.readLine();
    }
    return i;
  }
  
  private char[] a(BufferedReader paramBufferedReader, int paramInt)
    throws IOException
  {
    char[] arrayOfChar = null;
    if (paramInt > 0)
    {
      arrayOfChar = new char[paramInt];
      int i = 0;
      while (paramInt > 0)
      {
        int j = paramBufferedReader.read(arrayOfChar, i, paramInt);
        paramInt -= j;
        i += j;
      }
    }
    else
    {
      arrayOfChar = a(paramBufferedReader);
    }
    return arrayOfChar;
  }
  
  private char[] a(BufferedReader paramBufferedReader)
    throws IOException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (String str = paramBufferedReader.readLine(); str != null; str = paramBufferedReader.readLine())
    {
      localStringBuffer.append(str);
      localStringBuffer.append("\n");
    }
    return localStringBuffer.toString().toCharArray();
  }
  
  class a
    implements Serializable
  {
    private boolean jdField_int;
    private String jdField_for;
    private int jdField_do;
    private String a;
    private String jdField_if;
    
    private a(boolean paramBoolean, String paramString1, int paramInt, String paramString2)
    {
      this.jdField_int = paramBoolean;
      this.jdField_for = paramString1;
      this.jdField_do = paramInt;
      this.a = paramString2;
      this.jdField_if = ("Open socket on host " + this.jdField_for + ", port " + this.jdField_do);
    }
    
    a(boolean paramBoolean, String paramString1, int paramInt, String paramString2, Connect.1 param1)
    {
      this(paramBoolean, paramString1, paramInt, paramString2);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ofx.Connect
 * JD-Core Version:    0.7.0.1
 */