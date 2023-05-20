package com.ffusion.util;

import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class XMLUtil
{
  public static final String PROPERTIES_SUFFIX = "_PROPERTIES";
  public static final String LIST_SUFFIX = "_LIST";
  
  public static HashMap tagToHashMap(XMLTag paramXMLTag)
  {
    boolean bool = paramXMLTag.getTagName().endsWith("_LIST");
    HashMap localHashMap = new HashMap();
    ArrayList localArrayList1 = paramXMLTag.getContainedTagList();
    XMLTag localXMLTag = null;
    Iterator localIterator = localArrayList1.iterator();
    while (localIterator.hasNext())
    {
      localXMLTag = (XMLTag)localIterator.next();
      Object localObject;
      if (localXMLTag.getContainedTagList().size() == 0)
      {
        if (bool)
        {
          localObject = (ArrayList)localHashMap.get(localXMLTag.getTagName());
          if (localObject == null)
          {
            localObject = new ArrayList();
            localHashMap.put(localXMLTag.getTagName(), localObject);
          }
          ((ArrayList)localObject).add(localXMLTag.getTagContent());
        }
        else
        {
          localHashMap.put(localXMLTag.getTagName(), localXMLTag.getTagContent());
        }
      }
      else
      {
        localObject = null;
        if (localXMLTag.getTagName().endsWith("_PROPERTIES")) {
          localObject = tagToProperties(localXMLTag);
        } else {
          localObject = tagToHashMap(localXMLTag);
        }
        if (bool)
        {
          ArrayList localArrayList2 = (ArrayList)localHashMap.get(localXMLTag.getTagName());
          if (localArrayList2 == null)
          {
            localArrayList2 = new ArrayList();
            localHashMap.put(localXMLTag.getTagName(), localArrayList2);
          }
          localArrayList2.add(localObject);
        }
        else
        {
          localHashMap.put(localXMLTag.getTagName(), localObject);
        }
      }
    }
    return localHashMap;
  }
  
  public static Properties tagToProperties(XMLTag paramXMLTag)
  {
    Properties localProperties = new Properties();
    ArrayList localArrayList = paramXMLTag.getContainedTagList();
    XMLTag localXMLTag = null;
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      localXMLTag = (XMLTag)localIterator.next();
      localProperties.setProperty(localXMLTag.getTagName(), localXMLTag.getTagContent());
    }
    return localProperties;
  }
  
  public static Object getMultiSetEntry(HashMap paramHashMap, String paramString1, String paramString2, String paramString3)
  {
    Object localObject1 = paramHashMap.get(paramString1);
    if (localObject1 == null) {
      return null;
    }
    if (!(localObject1 instanceof ArrayList)) {
      return localObject1;
    }
    Iterator localIterator = ((ArrayList)localObject1).iterator();
    Object localObject2 = null;
    while (localIterator.hasNext())
    {
      Object localObject3 = localIterator.next();
      if ((localObject3 instanceof String))
      {
        if (paramString3.equals((String)localObject3))
        {
          localObject2 = localObject3;
          break;
        }
      }
      else
      {
        Object localObject4;
        if ((localObject3 instanceof HashMap))
        {
          localObject4 = ((HashMap)localObject3).get(paramString2);
          if ((localObject4 != null) && ((localObject4 instanceof String))) {
            if (paramString3.equals((String)localObject4))
            {
              localObject2 = localObject3;
              break;
            }
          }
        }
        else if ((localObject3 instanceof Properties))
        {
          localObject4 = ((Properties)localObject3).getProperty(paramString2);
          if (localObject4 != null) {
            if (paramString3.equals(localObject4))
            {
              localObject2 = localObject3;
              break;
            }
          }
        }
      }
    }
    return localObject2;
  }
  
  public static String hashMapToXML(HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    a(paramHashMap, localStringBuffer);
    return localStringBuffer.toString();
  }
  
  private static void a(HashMap paramHashMap, StringBuffer paramStringBuffer)
  {
    Iterator localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = localIterator.next().toString();
      String str2 = str1;
      if (str2.indexOf(" ") != -1) {
        str2 = str2.substring(0, str2.indexOf(" "));
      }
      Object localObject = paramHashMap.get(str1);
      if ((localObject instanceof HashMap))
      {
        paramStringBuffer.append("<" + str1 + ">\n");
        a((HashMap)localObject, paramStringBuffer);
      }
      else if ((localObject instanceof String))
      {
        paramStringBuffer.append("<" + str1 + ">");
        paramStringBuffer.append((String)localObject);
      }
      else
      {
        paramStringBuffer.append("<" + str1 + ">");
      }
      paramStringBuffer.append("</" + str2 + ">\n");
    }
  }
  
  public static String XMLEncode(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return "";
    }
    int i = paramString.indexOf('<');
    int j = paramString.indexOf('>');
    int k = paramString.indexOf('&');
    if ((i < 0) && (j < 0) && (k < 0)) {
      return paramString;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    int m = paramString.length();
    int n = 0;
    while ((i >= 0) || (j >= 0) || (k >= 0))
    {
      int i1 = i;
      if ((j != -1) && ((i1 == -1) || (i1 > j))) {
        i1 = j;
      }
      if ((k != -1) && ((i1 == -1) || (i1 > k))) {
        i1 = k;
      }
      localStringBuffer.append(paramString.substring(n, i1));
      char c = paramString.charAt(i1);
      n = i1 + 1;
      switch (c)
      {
      case '<': 
        localStringBuffer.append("&lt;");
        break;
      case '>': 
        localStringBuffer.append("&gt;");
        break;
      case '&': 
        localStringBuffer.append("&amp;");
        break;
      default: 
        localStringBuffer.append(c);
      }
      i = paramString.indexOf('<', n);
      j = paramString.indexOf('>', n);
      k = paramString.indexOf('&', n);
      if ((i < 0) && (j < 0) && (k < 0)) {
        localStringBuffer.append(paramString.substring(n, m));
      }
    }
    return localStringBuffer.toString();
  }
  
  public static String XMLEncode(char paramChar)
  {
    String str;
    switch (paramChar)
    {
    case '<': 
      str = "&lt;";
      break;
    case '>': 
      str = "&gt;";
      break;
    case '&': 
      str = "&amp;";
      break;
    case '\'': 
      str = "&#39;";
      break;
    case '?': 
      str = "&#63;";
      break;
    case '%': 
      str = "&#37;";
      break;
    case '"': 
      str = "&#34;";
      break;
    case '|': 
      str = "&#124;";
      break;
    case '!': 
      str = "&#33;";
      break;
    case '*': 
      str = "&#42;";
      break;
    case '/': 
      str = "&#47;";
      break;
    case '\\': 
      str = "&#92;";
      break;
    default: 
      str = String.valueOf(paramChar);
    }
    return str;
  }
  
  public static String XMLDecode(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return "";
    }
    int i = paramString.indexOf('&');
    if (i < 0) {
      return paramString;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      int j = paramString.length();
      int k = 0;
      while (i != -1)
      {
        localStringBuffer.append(paramString.substring(k, i));
        k = i + 1;
        int m = paramString.indexOf(';', k);
        if (m != -1)
        {
          String str = paramString.substring(i, m + 1);
          if (str.equalsIgnoreCase("&lt;"))
          {
            localStringBuffer.append("<");
            k += 3;
          }
          else if (str.equalsIgnoreCase("&#60;"))
          {
            localStringBuffer.append("<");
            k += 4;
          }
          else if (str.equalsIgnoreCase("&gt;"))
          {
            localStringBuffer.append(">");
            k += 3;
          }
          else if (str.equalsIgnoreCase("&#62;"))
          {
            localStringBuffer.append(">");
            k += 4;
          }
          else if (str.equalsIgnoreCase("&amp;"))
          {
            localStringBuffer.append("&");
            k += 4;
          }
          else if (str.equalsIgnoreCase("&#38;"))
          {
            localStringBuffer.append("&");
            k += 4;
          }
          else if (str.equalsIgnoreCase("&#39;"))
          {
            localStringBuffer.append("'");
            k += 4;
          }
          else if (str.equalsIgnoreCase("&#63;"))
          {
            localStringBuffer.append("?");
            k += 4;
          }
          else if (str.equalsIgnoreCase("&#37;"))
          {
            localStringBuffer.append("%");
            k += 4;
          }
          else if (str.equalsIgnoreCase("&#34;"))
          {
            localStringBuffer.append("\"");
            k += 4;
          }
          else if (str.equalsIgnoreCase("&#124;"))
          {
            localStringBuffer.append("|");
            k += 5;
          }
          else if (str.equalsIgnoreCase("&#33;"))
          {
            localStringBuffer.append("!");
            k += 4;
          }
          else if (str.equalsIgnoreCase("&#42;"))
          {
            localStringBuffer.append("*");
            k += 4;
          }
          else if (str.equalsIgnoreCase("&#47;"))
          {
            localStringBuffer.append("/");
            k += 4;
          }
          else if (str.equalsIgnoreCase("&#92;"))
          {
            localStringBuffer.append("\\");
            k += 4;
          }
          else
          {
            localStringBuffer.append("&");
          }
        }
        else
        {
          localStringBuffer.append("&");
        }
        i = paramString.indexOf('&', k);
        if (i == -1) {
          localStringBuffer.append(paramString.substring(k, j));
        }
      }
    }
    catch (Exception localException) {}
    return localStringBuffer.toString();
  }
  
  public static void addXmlTag(StringBuffer paramStringBuffer, String paramString, boolean paramBoolean)
  {
    if (paramBoolean) {
      paramStringBuffer.append("</");
    } else {
      paramStringBuffer.append("<");
    }
    paramStringBuffer.append(paramString);
    paramStringBuffer.append(">");
  }
  
  public static void addXmlTag(StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    if (paramString2 != null) {
      paramStringBuffer.append("<" + paramString1 + ">" + XMLEncode(paramString2) + "</" + paramString1 + ">");
    }
  }
  
  public static void addXmlTag(StringBuffer paramStringBuffer, String paramString, long paramLong)
  {
    addXmlTag(paramStringBuffer, paramString, paramLong, false);
  }
  
  public static void addXmlTag(StringBuffer paramStringBuffer, String paramString, long paramLong, boolean paramBoolean)
  {
    if ((paramBoolean) || (paramLong != 0L)) {
      addXmlTag(paramStringBuffer, paramString, String.valueOf(paramLong));
    }
  }
  
  public static void addXmlTag(StringBuffer paramStringBuffer, String paramString, Calendar paramCalendar, boolean paramBoolean)
  {
    if (paramCalendar != null)
    {
      String str;
      if (paramBoolean == true) {
        str = "MM-dd-yyyy HH:mm:ss";
      } else {
        str = "MM-dd-yyyy";
      }
      addXmlTag(paramStringBuffer, paramString, DateFormatUtil.getFormatter(str).format(paramCalendar.getTime()));
    }
  }
  
  public static String readXmlToString(Object paramObject, String paramString)
    throws Exception
  {
    String str = null;
    InputStream localInputStream = ResourceUtil.getResourceAsStream(paramObject, paramString);
    if (localInputStream != null) {
      str = Strings.streamToString(localInputStream, "UTF-8");
    }
    return str;
  }
  
  public static XMLTag readXmlToXMLTag(Object paramObject, String paramString)
    throws Exception
  {
    String str = readXmlToString(paramObject, paramString);
    XMLTag localXMLTag = new XMLTag(true);
    localXMLTag.build(str);
    return localXMLTag;
  }
  
  public static HashMap readXmlToHashMap(Object paramObject, String paramString)
    throws Exception
  {
    XMLTag localXMLTag = readXmlToXMLTag(paramObject, paramString);
    HashMap localHashMap = tagToHashMap(localXMLTag);
    return localHashMap;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.XMLUtil
 * JD-Core Version:    0.7.0.1
 */