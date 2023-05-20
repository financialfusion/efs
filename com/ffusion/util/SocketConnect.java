package com.ffusion.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

public class SocketConnect
{
  protected String urlString;
  protected String certsDirectory;
  protected byte[][] caCerts = (byte[][])null;
  protected Socket socket = null;
  protected InputStream in;
  protected OutputStream out;
  protected int port;
  protected URL url;
  protected int timeout = 0;
  
  public SocketConnect() {}
  
  public SocketConnect(String paramString)
  {
    this();
    this.urlString = paramString.toLowerCase();
    try
    {
      this.url = new URL(paramString);
    }
    catch (MalformedURLException localMalformedURLException) {}
  }
  
  public SocketConnect(String paramString, int paramInt)
  {
    this(paramString);
    this.port = paramInt;
  }
  
  public SocketConnect(String paramString1, String paramString2)
  {
    this(paramString1);
    this.certsDirectory = paramString2;
  }
  
  public SocketConnect(String paramString1, int paramInt, String paramString2)
  {
    this(paramString1);
    this.port = paramInt;
    this.certsDirectory = paramString2;
  }
  
  public InputStream getInputStream()
  {
    return this.in;
  }
  
  public OutputStream getOutputStream()
  {
    return this.out;
  }
  
  public void setUrlString(String paramString)
  {
    this.urlString = paramString;
    try
    {
      URL localURL = new URL(paramString);
    }
    catch (MalformedURLException localMalformedURLException) {}
  }
  
  public String getUrlString()
  {
    return this.urlString;
  }
  
  public URL getUrl()
  {
    return this.url;
  }
  
  public void setCertsDirectory(String paramString)
  {
    this.certsDirectory = paramString;
  }
  
  public String getCertsDirectory()
  {
    return this.certsDirectory;
  }
  
  public void setTimeout(int paramInt)
  {
    this.timeout = paramInt;
  }
  
  public int getTimeout()
  {
    return this.timeout;
  }
  
  public BufferedReader getReader()
  {
    return new BufferedReader(new InputStreamReader(this.in));
  }
  
  public PrintWriter getWriter()
  {
    return new PrintWriter(this.out);
  }
  
  public boolean connect()
  {
    try
    {
      int i = 0;
      int j = this.url.getPort();
      String str1 = this.url.getProtocol();
      String str2 = this.url.getHost();
      if (str1.equals("https")) {
        i = 1;
      } else if ((str1.equals("ftp")) && (j == -1)) {
        j = 21;
      }
      if (j == -1) {
        if (i != 0) {
          j = 443;
        } else {
          j = 80;
        }
      }
      if (i == 0)
      {
        this.socket = new Socket(str2, j);
        this.socket.setSoTimeout(this.timeout);
        this.in = this.socket.getInputStream();
        this.out = this.socket.getOutputStream();
      }
    }
    catch (Exception localException)
    {
      return false;
    }
    return true;
  }
  
  public void close()
  {
    if (this.socket != null) {
      try
      {
        this.socket.close();
      }
      catch (IOException localIOException) {}
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.SocketConnect
 * JD-Core Version:    0.7.0.1
 */