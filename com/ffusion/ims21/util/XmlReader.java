package com.ffusion.ims21.util;

import com.ffusion.util.HfnEncrypt;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;

public class XmlReader
{
  BufferedInputStream jdField_do;
  int jdField_for = 0;
  int jdField_new = 0;
  byte[] a = new byte[1024];
  String jdField_if = "";
  String jdField_int = null;
  
  public void setCommandString(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public boolean connect2Url(String paramString, boolean paramBoolean)
    throws Exception
  {
    URL localURL = new URL(paramString);
    URLConnection localURLConnection = localURL.openConnection();
    if (paramBoolean)
    {
      localURLConnection.setDoOutput(true);
      OutputStream localOutputStream = localURLConnection.getOutputStream();
      if (this.jdField_int != null) {
        localOutputStream.write(this.jdField_int.getBytes());
      }
    }
    this.jdField_do = new BufferedInputStream(localURLConnection.getInputStream());
    return true;
  }
  
  public boolean connect2File(String paramString)
    throws Exception
  {
    this.jdField_do = new BufferedInputStream(new FileInputStream(paramString));
    return true;
  }
  
  public String getTag()
  {
    if ((this.jdField_for == 0) && (!readBuffer())) {
      return null;
    }
    while (this.jdField_new < this.jdField_for)
    {
      char c = (char)this.a[this.jdField_new];
      this.jdField_if += c;
      if (c == '\n')
      {
        String str = this.jdField_if;
        this.jdField_new += 1;
        this.jdField_if = "";
        return str;
      }
      this.jdField_new += 1;
    }
    this.jdField_for = 0;
    return getTag();
  }
  
  public boolean readBuffer()
  {
    if (this.jdField_do == null) {
      return false;
    }
    try
    {
      this.jdField_new = 0;
      this.jdField_for = 0;
      this.a = new byte[1024];
      for (int j = 0; j < 1024; j++)
      {
        int i = this.jdField_do.read(this.a, j, 1);
        if (i == -1) {
          break;
        }
        this.jdField_for += 1;
      }
    }
    catch (IOException localIOException)
    {
      System.err.println("queryServer - IOException " + localIOException);
      return false;
    }
    if (this.jdField_for == 0) {
      return false;
    }
    byte[] arrayOfByte = new byte[1024];
    arrayOfByte = HfnEncrypt.decrypt(this.a, this.jdField_for);
    this.a = arrayOfByte;
    return true;
  }
  
  public String readProfileBuffer()
  {
    if (this.jdField_do == null) {
      return null;
    }
    byte[] arrayOfByte = new byte[1024];
    try
    {
      for (int i = 0; i < 1024; i++)
      {
        int j = this.jdField_do.read(arrayOfByte, i, 1);
        if (j == -1) {
          break;
        }
      }
    }
    catch (IOException localIOException)
    {
      System.err.println("Profile Server - IOException " + localIOException);
      return null;
    }
    return new String(arrayOfByte);
  }
  
  public static String getFilenameFromPath(String paramString)
  {
    String str = "";
    try
    {
      int i = paramString.lastIndexOf('/');
      if (i == -1) {
        i = paramString.lastIndexOf('\\');
      }
      if (i != -1) {
        str = paramString.substring(i + 1);
      } else {
        str = paramString;
      }
    }
    catch (Throwable localThrowable)
    {
      str = "";
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.ims21.util.XmlReader
 * JD-Core Version:    0.7.0.1
 */