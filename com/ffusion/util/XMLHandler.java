package com.ffusion.util;

import com.microstar.xml.HandlerBase;
import com.microstar.xml.XmlParser;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Stack;

public class XMLHandler
  extends HandlerBase
  implements Serializable
{
  public static final String DATEFORMAT = "yyyy.MM.dd.HH.mm.ss.z";
  public static final String XML_TEST_TAG = "<";
  private XMLHandler jdField_if = this;
  private XMLHandler jdField_do;
  private Stack jdField_for = new Stack();
  private Stack a = new Stack();
  
  public void setHandler(XMLHandler paramXMLHandler)
  {
    this.jdField_if = paramXMLHandler;
  }
  
  public XMLHandler getHandler()
  {
    return this.jdField_if;
  }
  
  public void start(XMLHandler paramXMLHandler, String paramString)
    throws Exception
  {
    paramString.trim();
    if (!paramString.startsWith("<"))
    {
      try
      {
        URL localURL = new URL(paramString);
        start(paramXMLHandler, localURL);
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
    }
    else
    {
      StringReader localStringReader = null;
      try
      {
        localStringReader = new StringReader(paramString);
        start(paramXMLHandler, localStringReader);
      }
      finally
      {
        if (localStringReader != null) {
          localStringReader.close();
        }
      }
    }
  }
  
  public void start(XMLHandler paramXMLHandler, Reader paramReader)
    throws Exception
  {
    XmlParser localXmlParser = new XmlParser();
    paramXMLHandler.setHandler(this);
    this.jdField_do = paramXMLHandler;
    localXmlParser.setHandler(this);
    try
    {
      localXmlParser.parse(null, null, paramReader);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void start(XMLHandler paramXMLHandler, URL paramURL)
    throws Exception
  {
    XmlParser localXmlParser = new XmlParser();
    paramXMLHandler.setHandler(this);
    this.jdField_do = paramXMLHandler;
    localXmlParser.setHandler(this);
    try
    {
      localXmlParser.parse(paramURL.toString(), null, (String)null);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void continueWith(XMLHandler paramXMLHandler, String paramString)
  {
    continueWith(paramXMLHandler);
  }
  
  public void continueWith(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.setHandler(this);
    this.jdField_for.push(this.jdField_do);
    this.jdField_do = paramXMLHandler;
  }
  
  public String getElement()
  {
    return (String)this.jdField_if.a.peek();
  }
  
  public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    throws Exception
  {
    if (this.jdField_do != null) {
      this.jdField_do.attribute(paramString1, paramString2, paramBoolean);
    }
  }
  
  public void startElement(String paramString)
    throws Exception
  {
    if (this.jdField_do != null)
    {
      this.a.push(paramString);
      this.jdField_do.startElement(paramString);
      if (this.a.size() != this.jdField_for.size()) {
        this.jdField_for.push(this.jdField_do);
      }
    }
  }
  
  public void endElement(String paramString)
    throws Exception
  {
    if (this.jdField_do != null)
    {
      this.jdField_do.endElement(paramString);
      this.a.pop();
      this.jdField_do = ((XMLHandler)this.jdField_for.pop());
    }
  }
  
  public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws Exception
  {
    if (this.jdField_do != null) {
      this.jdField_do.charData(paramArrayOfChar, paramInt1, paramInt2);
    }
  }
  
  private static void a(StringBuffer paramStringBuffer, String paramString)
  {
    int i = paramString.indexOf('<');
    int j = paramString.indexOf('>');
    int k = paramString.indexOf('&');
    if ((i < 0) && (j < 0) && (k < 0))
    {
      paramStringBuffer.append(paramString);
      return;
    }
    int m = paramString.length();
    for (int n = 0; n < m; n++)
    {
      char c = paramString.charAt(n);
      switch (c)
      {
      case '<': 
        paramStringBuffer.append("&lt;");
        break;
      case '>': 
        paramStringBuffer.append("&gt;");
        break;
      case '&': 
        if ((n + 1 < m) && (paramString.charAt(n + 1) == '#')) {
          paramStringBuffer.append(c);
        } else {
          paramStringBuffer.append("&amp;");
        }
        break;
      default: 
        paramStringBuffer.append(c);
      }
    }
  }
  
  public static void openTag(StringBuffer paramStringBuffer, String paramString)
  {
    paramStringBuffer.append("<");
    paramStringBuffer.append(paramString);
  }
  
  public static void closeTag(StringBuffer paramStringBuffer, boolean paramBoolean)
  {
    if (paramBoolean) {
      paramStringBuffer.append("/");
    }
    paramStringBuffer.append(">\n");
  }
  
  public static void appendAttribute(StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    paramStringBuffer.append(' ');
    paramStringBuffer.append(paramString1);
    paramStringBuffer.append(" = \"");
    a(paramStringBuffer, paramString2);
    paramStringBuffer.append("\"");
  }
  
  public static void appendTagAttribute(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3)
  {
    openTag(paramStringBuffer, paramString1);
    paramStringBuffer.append(' ');
    paramStringBuffer.append(paramString2);
    paramStringBuffer.append(" = \"");
    a(paramStringBuffer, paramString3);
    paramStringBuffer.append("\"");
    closeTag(paramStringBuffer, true);
  }
  
  public static void appendXMLHeader(StringBuffer paramStringBuffer)
  {
    paramStringBuffer.append("<?xml version=\"1.0\"?>\n");
  }
  
  public static void appendTag(StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    if ((paramString2 != null) && (paramString2.length() > 0))
    {
      paramStringBuffer.append('<');
      paramStringBuffer.append(paramString1);
      paramStringBuffer.append('>');
      a(paramStringBuffer, paramString2);
      paramStringBuffer.append("</");
      paramStringBuffer.append(paramString1);
      paramStringBuffer.append(">\n");
    }
  }
  
  public static void appendTag(StringBuffer paramStringBuffer, String paramString, double paramDouble)
  {
    appendTag(paramStringBuffer, paramString, String.valueOf(paramDouble));
  }
  
  public static void appendTag(StringBuffer paramStringBuffer, String paramString, int paramInt)
  {
    appendTag(paramStringBuffer, paramString, String.valueOf(paramInt));
  }
  
  public static void appendBeginTag(StringBuffer paramStringBuffer, String paramString)
  {
    paramStringBuffer.append('<');
    paramStringBuffer.append(paramString);
    paramStringBuffer.append(">\n");
  }
  
  public static void appendEndTag(StringBuffer paramStringBuffer, String paramString)
  {
    paramStringBuffer.append("</");
    paramStringBuffer.append(paramString);
    paramStringBuffer.append(">\n");
  }
  
  public static String format(String paramString)
  {
    return format(paramString, "  ");
  }
  
  public static String format(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str1 = "";
    int i = 0;
    int j = paramString1.length();
    while (i != -1)
    {
      int k = paramString1.indexOf("\n", i);
      if (k != -1)
      {
        String str2 = paramString1.substring(i, k);
        int m = str2.indexOf("<");
        int n = str2.indexOf("</");
        if ((m == n) && (str1.length() >= paramString2.length())) {
          str1 = str1.substring(paramString2.length());
        }
        localStringBuffer.append(str1);
        localStringBuffer.append(str2 + "\n");
        if ((m != n) && (n == -1)) {
          str1 = str1 + paramString2;
        }
        k++;
      }
      i = k;
    }
    return localStringBuffer.toString();
  }
  
  public static GregorianCalendar parseDate(String paramString)
  {
    GregorianCalendar localGregorianCalendar = null;
    try
    {
      Date localDate = DateFormatUtil.getFormatter("yyyy.MM.dd.HH.mm.ss.z").parse(paramString);
      localGregorianCalendar = new GregorianCalendar();
      localGregorianCalendar.setTime(localDate);
    }
    catch (ParseException localParseException) {}
    return localGregorianCalendar;
  }
  
  public static String formatDate(Calendar paramCalendar)
  {
    String str = DateFormatUtil.getFormatter("yyyy.MM.dd.HH.mm.ss.z").format(paramCalendar.getTime());
    return str;
  }
  
  public static void appendTag(StringBuffer paramStringBuffer, String paramString, Calendar paramCalendar)
  {
    if (paramCalendar != null) {
      appendTag(paramStringBuffer, paramString, formatDate(paramCalendar));
    }
  }
  
  public static void appendAggregate(StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    if ((paramString2 == null) || (paramString2.length() <= 0)) {
      return;
    }
    appendBeginTag(paramStringBuffer, paramString1);
    paramStringBuffer.append(paramString2);
    appendEndTag(paramStringBuffer, paramString1);
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.XMLHandler
 * JD-Core Version:    0.7.0.1
 */