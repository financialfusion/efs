package com.ffusion.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class FtpUtil
{
  protected static final int HEX_RADIX = 16;
  protected static final int BUFFER = 1024;
  protected static final String FILE_SEPARATOR = "/";
  protected String ftpUrl;
  protected int ftpPort = 21;
  protected String ftpUsername = "anonymous";
  protected String ftpPassword = "anon@mail.com";
  protected String localDirectory = "/";
  protected boolean binaryMode = true;
  protected int socketTimeout = 30000;
  protected Socket connection;
  protected BufferedReader in;
  protected PrintWriter out;
  protected boolean connected = false;
  
  public FtpUtil(String paramString)
  {
    this.ftpUrl = paramString;
  }
  
  public FtpUtil(String paramString1, String paramString2, String paramString3)
  {
    this(paramString1);
    this.ftpUsername = paramString2;
    this.ftpPassword = paramString3;
  }
  
  public FtpUtil(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this(paramString1, paramString2, paramString3);
    setLocalDirectory(paramString4);
  }
  
  public boolean open()
  {
    try
    {
      this.connection = new Socket(this.ftpUrl, this.ftpPort);
      this.connection.setSoTimeout(this.socketTimeout);
      this.in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
      this.out = new PrintWriter(this.connection.getOutputStream());
      if (!sendCommand(null, "220")) {
        return false;
      }
      if (!sendCommand("USER " + this.ftpUsername, "331")) {
        return false;
      }
      if (!sendCommand("PASS " + this.ftpPassword, "230")) {
        return false;
      }
      if (!setTransferMode()) {
        return false;
      }
      this.connected = true;
      return true;
    }
    catch (Exception localException)
    {
      System.out.println("Error opening FTP connection: " + localException);
    }
    return false;
  }
  
  public void close()
  {
    if (!this.connected) {
      return;
    }
    try
    {
      sendCommand("QUIT", "221");
      if (this.connection != null) {
        this.connection.close();
      }
    }
    catch (IOException localIOException) {}
    this.connected = false;
  }
  
  public boolean changeDirectory(String paramString)
  {
    if (!this.connected) {
      return false;
    }
    return sendCommand("CWD " + paramString, "250");
  }
  
  public boolean get(String paramString)
  {
    if (!this.connected) {
      return false;
    }
    Socket localSocket = null;
    FileOutputStream localFileOutputStream = null;
    try
    {
      String str1 = sendCommand("PASV");
      String str2 = getPasvIp(str1);
      int i = getPasvPort(str1);
      localSocket = new Socket(str2, i);
      if (!sendCommand("RETR " + paramString, "125,150"))
      {
        boolean bool2 = false;
        return bool2;
      }
      InputStream localInputStream = localSocket.getInputStream();
      BufferedInputStream localBufferedInputStream = new BufferedInputStream(localInputStream);
      int j = 0;
      byte[] arrayOfByte = new byte[1024];
      localFileOutputStream = new FileOutputStream(this.localDirectory + paramString);
      BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(localFileOutputStream);
      while ((j = localBufferedInputStream.read(arrayOfByte)) > -1) {
        localBufferedOutputStream.write(arrayOfByte, 0, j);
      }
      localBufferedOutputStream.flush();
      localInputStream.close();
      localBufferedInputStream.close();
      if (!sendCommand(null, "226"))
      {
        bool3 = false;
        return bool3;
      }
      boolean bool3 = true;
      return bool3;
    }
    catch (Exception localException1)
    {
      System.out.println("Error during FTP get: " + localException1);
      boolean bool1 = false;
      return bool1;
    }
    finally
    {
      try
      {
        localSocket.close();
      }
      catch (Exception localException10) {}
      try
      {
        localFileOutputStream.close();
      }
      catch (Exception localException11) {}
    }
  }
  
  public boolean put(String paramString, InputStream paramInputStream)
  {
    if (!this.connected) {
      return false;
    }
    Socket localSocket = null;
    try
    {
      String str1 = sendCommand("PASV");
      String str2 = getPasvIp(str1);
      int i = getPasvPort(str1);
      localSocket = new Socket(str2, i);
      if (!sendCommand("STOR " + paramString, "125,150"))
      {
        boolean bool2 = false;
        return bool2;
      }
      OutputStream localOutputStream = localSocket.getOutputStream();
      BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(localOutputStream);
      int j = 0;
      byte[] arrayOfByte = new byte[1024];
      BufferedInputStream localBufferedInputStream = new BufferedInputStream(paramInputStream);
      while ((j = localBufferedInputStream.read(arrayOfByte)) > -1) {
        localBufferedOutputStream.write(arrayOfByte, 0, j);
      }
      localBufferedOutputStream.flush();
      localOutputStream.close();
      localBufferedOutputStream.close();
      if (!sendCommand(null, "226"))
      {
        bool3 = false;
        return bool3;
      }
      boolean bool3 = true;
      return bool3;
    }
    catch (Exception localException1)
    {
      System.out.println("Error during FTP put: " + localException1);
      boolean bool1 = false;
      return bool1;
    }
    finally
    {
      try
      {
        localSocket.close();
      }
      catch (Exception localException10) {}
      try
      {
        paramInputStream.close();
      }
      catch (Exception localException11) {}
    }
  }
  
  public boolean put(String paramString)
  {
    FileInputStream localFileInputStream = null;
    try
    {
      File localFile = new File(this.localDirectory + paramString);
      if (!localFile.exists())
      {
        bool = false;
        return bool;
      }
      localFileInputStream = new FileInputStream(localFile);
      bool = put(paramString, localFileInputStream);
      return bool;
    }
    catch (Exception localException1)
    {
      System.out.println("Error during FTP put: " + localException1);
      boolean bool = false;
      return bool;
    }
    finally
    {
      try
      {
        localFileInputStream.close();
      }
      catch (Exception localException5) {}
    }
  }
  
  public boolean getFileList(ArrayList paramArrayList)
  {
    if (!this.connected) {
      return false;
    }
    Socket localSocket = null;
    try
    {
      String str1 = sendCommand("PASV");
      String str2 = getPasvIp(str1);
      int i = getPasvPort(str1);
      localSocket = new Socket(str2, i);
      if (!sendCommand("LIST", "125,150"))
      {
        boolean bool2 = false;
        return bool2;
      }
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localSocket.getInputStream()));
      String str3;
      while ((str3 = localBufferedReader.readLine()) != null) {
        paramArrayList.add(str3);
      }
      localBufferedReader.close();
      if (!sendCommand(null, "226"))
      {
        boolean bool3 = false;
        return bool3;
      }
      boolean bool1;
      return true;
    }
    catch (Exception localException2)
    {
      System.out.println("Error during FTP directory listing: " + localException2);
      bool1 = false;
      return bool1;
    }
    finally
    {
      try
      {
        localSocket.close();
      }
      catch (Exception localException6) {}
    }
  }
  
  public boolean deleteFile(String paramString)
  {
    return sendCommand("DELE " + paramString, "250");
  }
  
  public boolean deleteFiles(ArrayList paramArrayList)
  {
    boolean bool = true;
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (!deleteFile(str)) {
        bool = false;
      }
    }
    return bool;
  }
  
  public void setFtpUrl(String paramString)
  {
    this.ftpUrl = paramString;
  }
  
  public String getFtpUrl()
  {
    return this.ftpUrl;
  }
  
  public void setFtpPort(int paramInt)
  {
    this.ftpPort = paramInt;
  }
  
  public int getFtpPort()
  {
    return this.ftpPort;
  }
  
  public void setFtpUsername(String paramString)
  {
    this.ftpUsername = paramString;
  }
  
  public String getFtpUsername()
  {
    return this.ftpUsername;
  }
  
  public void setFtpPassword(String paramString)
  {
    this.ftpPassword = paramString;
  }
  
  public String getFtpPassword()
  {
    return this.ftpPassword;
  }
  
  public void setLocalDirectory(String paramString)
  {
    if (!paramString.endsWith("/")) {
      paramString = paramString + "/";
    }
    this.localDirectory = paramString;
  }
  
  public String getLocalDirectory()
  {
    return this.localDirectory;
  }
  
  public void setBinaryMode(boolean paramBoolean)
  {
    this.binaryMode = paramBoolean;
    if (this.connected) {
      setTransferMode();
    }
  }
  
  public boolean getBinaryMode()
  {
    return this.binaryMode;
  }
  
  public void setSocketTimeout(int paramInt)
  {
    this.socketTimeout = paramInt;
  }
  
  public int getSocketTimeout()
  {
    return this.socketTimeout;
  }
  
  public boolean getConnected()
  {
    return this.connected;
  }
  
  protected boolean sendCommand(String paramString1, String paramString2)
  {
    String str1 = sendCommand(paramString1);
    if (str1 == null) {
      return false;
    }
    if ((paramString2 == null) || (paramString2.equals(""))) {
      return true;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString2, ",");
    while (localStringTokenizer.hasMoreTokens())
    {
      String str2 = localStringTokenizer.nextToken();
      while (str1.startsWith(str2))
      {
        if (str1.startsWith(str2 + " ")) {
          return true;
        }
        try
        {
          str1 = this.in.readLine();
        }
        catch (Exception localException)
        {
          System.out.println("Error sending FTP command " + paramString1 + ": " + localException);
          return false;
        }
      }
    }
    return false;
  }
  
  protected String sendCommand(String paramString)
  {
    if ((paramString != null) && (!paramString.equals("")))
    {
      this.out.println(paramString);
      this.out.flush();
    }
    try
    {
      return this.in.readLine();
    }
    catch (Exception localException)
    {
      System.out.println("Error sending FTP command " + paramString + ": " + localException);
    }
    return null;
  }
  
  protected boolean setTransferMode()
  {
    if (this.binaryMode)
    {
      if (!sendCommand("TYPE I", "200")) {
        return false;
      }
    }
    else if (!sendCommand("TYPE A", "200")) {
      return false;
    }
    return true;
  }
  
  protected String getPasvIp(String paramString)
  {
    String str1 = paramString.substring(paramString.indexOf("(") + 1, paramString.indexOf(")"));
    StringBuffer localStringBuffer = new StringBuffer();
    StringTokenizer localStringTokenizer = new StringTokenizer(str1, ",");
    for (int i = 0; localStringTokenizer.hasMoreElements(); i++)
    {
      str2 = localStringTokenizer.nextToken();
      if (i < 4) {
        localStringBuffer.append(str2 + ".");
      }
    }
    String str2 = localStringBuffer.toString().replace(',', '.');
    if (str2.endsWith(".")) {
      str2 = str2.substring(0, str2.length() - 1);
    }
    return str2;
  }
  
  protected int getPasvPort(String paramString)
  {
    String str1 = paramString.substring(paramString.indexOf("(") + 1, paramString.indexOf(")"));
    StringTokenizer localStringTokenizer = new StringTokenizer(str1, ",");
    int i = 0;
    int j = 0;
    for (int k = 0; localStringTokenizer.hasMoreElements(); k++)
    {
      str2 = localStringTokenizer.nextToken();
      if (k == 4) {
        i = Integer.parseInt(str2);
      } else if (k == 5) {
        j = Integer.parseInt(str2);
      }
    }
    String str2 = Integer.toHexString(i);
    String str3 = Integer.toHexString(j);
    if (str3.length() == 1) {
      str3 = "0" + str3;
    }
    String str4 = str2 + str3;
    int m = Integer.parseInt(str4, 16);
    return m;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.FtpUtil
 * JD-Core Version:    0.7.0.1
 */