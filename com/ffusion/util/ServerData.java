package com.ffusion.util;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;

public class ServerData
{
  private final int jdField_if = 60000;
  private String a = "";
  
  public ServerData(String paramString)
  {
    this.a = paramString;
  }
  
  public HashMap getData(String paramString)
  {
    HashMap localHashMap = null;
    if (this.a.equals("")) {
      return null;
    }
    SocketConnect localSocketConnect = new SocketConnect(this.a);
    localSocketConnect.setTimeout(60000);
    if (localSocketConnect.connect()) {
      try
      {
        PrintWriter localPrintWriter = localSocketConnect.getWriter();
        BufferedReader localBufferedReader = localSocketConnect.getReader();
        String str1 = localSocketConnect.getUrl().getFile();
        int i = paramString.length();
        localPrintWriter.println("POST " + str1 + " HTTP/1.0");
        localPrintWriter.println("Content-Length: " + i);
        localPrintWriter.println("");
        localPrintWriter.print(paramString);
        localPrintWriter.flush();
        StringBuffer localStringBuffer = new StringBuffer();
        for (String str2 = localBufferedReader.readLine(); (str2 != null) && (!str2.equals("")); str2 = localBufferedReader.readLine()) {}
        for (str2 = localBufferedReader.readLine(); str2 != null; str2 = localBufferedReader.readLine()) {
          localStringBuffer.append(str2 + "\n");
        }
        String str3 = localStringBuffer.toString();
        XMLTag localXMLTag = new XMLTag();
        localXMLTag.build(str3);
        localHashMap = localXMLTag.getTagHashMap();
      }
      catch (Exception localException)
      {
        System.out.println("Error in ServerData/getData: " + localException);
      }
      finally
      {
        localSocketConnect.close();
      }
    }
    return localHashMap;
  }
  
  public String getServerUrl()
  {
    return this.a;
  }
  
  public void setServerUrl(String paramString)
  {
    this.a = paramString;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.ServerData
 * JD-Core Version:    0.7.0.1
 */