package com.ffusion.util;

import com.ffusion.util.des.TripleDESEncrypt;
import com.microstar.xml.HandlerBase;
import com.microstar.xml.XmlHandler;
import com.microstar.xml.XmlParser;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

public class XMLTag
  extends HandlerBase
  implements XmlHandler
{
  private HashMap jdField_for;
  private HashMap jdField_else;
  private ArrayList jdField_case = new ArrayList();
  private String a = "";
  private String jdField_byte = "";
  private XMLTag jdField_new;
  private Stack jdField_if;
  private boolean jdField_char = false;
  private static final String jdField_int = "Encrypt";
  private static final String jdField_try = "ffi";
  private static final String jdField_do = "3des";
  
  public XMLTag(boolean paramBoolean)
  {
    this.jdField_char = paramBoolean;
  }
  
  public XMLTag() {}
  
  public String getTagName()
  {
    return this.a;
  }
  
  public void setTagName(String paramString)
  {
    if (this.jdField_char) {
      this.a = paramString;
    } else {
      this.a = paramString.toUpperCase();
    }
  }
  
  public void setPreserveCase(boolean paramBoolean)
  {
    this.jdField_char = paramBoolean;
  }
  
  public Object getAttribute(String paramString)
  {
    if (this.jdField_for == null) {
      return null;
    }
    return this.jdField_for.get(paramString);
  }
  
  public void setAttribute(String paramString1, String paramString2)
  {
    if (this.jdField_for == null) {
      this.jdField_for = new HashMap();
    }
    this.jdField_for.put(paramString1, paramString2);
  }
  
  public String getTagContent()
  {
    return XMLUtil.XMLDecode(this.jdField_byte);
  }
  
  public void setTagContent(String paramString)
  {
    this.jdField_byte = XMLUtil.XMLEncode(paramString);
  }
  
  public XMLTag getContainedTag(String paramString)
  {
    int i = this.jdField_case.size();
    for (int j = 0; j < i; j++)
    {
      Object localObject = this.jdField_case.get(j);
      if ((localObject instanceof XMLTag))
      {
        XMLTag localXMLTag = (XMLTag)localObject;
        if (localXMLTag.getTagName().equalsIgnoreCase(paramString)) {
          return localXMLTag;
        }
      }
    }
    return null;
  }
  
  public void setContainedTag(XMLTag paramXMLTag)
  {
    this.jdField_case.add(paramXMLTag);
  }
  
  public void removeContainedTag(String paramString)
  {
    int i = this.jdField_case.size();
    for (int j = 0; j < i; j++)
    {
      Object localObject = this.jdField_case.get(j);
      if ((localObject instanceof XMLTag))
      {
        XMLTag localXMLTag = (XMLTag)localObject;
        if (localXMLTag.getTagName().equalsIgnoreCase(paramString))
        {
          this.jdField_case.remove(j);
          return;
        }
      }
    }
  }
  
  public ArrayList getContainedTagList()
  {
    return this.jdField_case;
  }
  
  public HashMap getTagHashMap()
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = this.jdField_case.iterator();
    a(localHashMap, localIterator);
    return localHashMap;
  }
  
  private void a(HashMap paramHashMap, Iterator paramIterator)
  {
    while (paramIterator.hasNext())
    {
      Object localObject = paramIterator.next();
      if ((localObject instanceof XMLTag))
      {
        XMLTag localXMLTag = (XMLTag)localObject;
        if (localXMLTag.getContainedTagList().size() == 0)
        {
          paramHashMap.put(localXMLTag.getTagName(), localXMLTag.getTagContent());
        }
        else
        {
          HashMap localHashMap = new HashMap();
          Iterator localIterator = localXMLTag.getContainedTagList().iterator();
          a(localHashMap, localIterator);
          paramHashMap.put(localXMLTag.getTagName(), localHashMap);
        }
      }
    }
  }
  
  public void build(String paramString)
    throws Exception
  {
    XmlParser localXmlParser = new XmlParser();
    this.jdField_new = this;
    this.jdField_if = new Stack();
    this.jdField_if.push(this.jdField_new);
    localXmlParser.setHandler(this);
    localXmlParser.parse(null, null, new StringReader(paramString));
    this.jdField_for = this.jdField_new.jdField_for;
    this.jdField_case = this.jdField_new.jdField_case;
    this.a = this.jdField_new.a;
    this.jdField_byte = this.jdField_new.jdField_byte;
  }
  
  public void startElement(String paramString)
  {
    this.jdField_new = ((XMLTag)this.jdField_if.peek());
    XMLTag localXMLTag = new XMLTag(this.jdField_char);
    this.jdField_new.setContainedTag(localXMLTag);
    if (this.jdField_else != null)
    {
      localXMLTag.jdField_for = this.jdField_else;
      this.jdField_else = null;
    }
    this.jdField_if.push(localXMLTag);
    if (this.jdField_char) {
      localXMLTag.setTagName(paramString);
    } else {
      localXMLTag.setTagName(paramString.toUpperCase());
    }
    this.jdField_new = localXMLTag;
  }
  
  public void endElement(String paramString)
  {
    this.jdField_new = ((XMLTag)this.jdField_if.pop());
  }
  
  public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    String str1 = new String(paramArrayOfChar, paramInt1, paramInt2);
    str1 = str1.trim();
    if (str1.length() > 0)
    {
      String str2 = (String)this.jdField_new.getAttribute("Encrypt");
      if (str2 != null) {
        if (str2.equalsIgnoreCase("ffi")) {
          try
          {
            str1 = HfnEncrypt.decryptHexEncode(str1);
          }
          catch (Exception localException1) {}
        } else if (str2.equalsIgnoreCase("3des")) {
          try
          {
            str1 = TripleDESEncrypt.decrypt(str1);
          }
          catch (Exception localException2) {}
        }
      }
      this.jdField_new.setTagContent(str1);
    }
  }
  
  public void attribute(String paramString1, String paramString2, boolean paramBoolean)
  {
    if (this.jdField_else == null) {
      this.jdField_else = new HashMap(2);
    }
    if (this.jdField_char) {
      this.jdField_else.put(paramString1, paramString2);
    } else {
      this.jdField_else.put(paramString1.toUpperCase(), paramString2);
    }
  }
  
  public void error(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    System.out.println("FATAL ERROR: " + paramString1);
    System.out.println("  at " + paramString2 + ": line " + paramInt1 + " column " + paramInt2);
    throw new Error(paramString1);
  }
  
  public static String toXML(XMLTag paramXMLTag, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Object localObject;
    if (paramBoolean)
    {
      localStringBuffer.append("<" + paramXMLTag.a);
      if ((paramXMLTag.jdField_for != null) && (paramXMLTag.jdField_for.size() > 0))
      {
        Iterator localIterator = paramXMLTag.jdField_for.entrySet().iterator();
        while (localIterator.hasNext())
        {
          localObject = (Map.Entry)localIterator.next();
          localStringBuffer.append(" " + (String)((Map.Entry)localObject).getKey() + "=\"" + (String)((Map.Entry)localObject).getValue() + "\"");
        }
      }
      localStringBuffer.append(">");
      localStringBuffer.append(paramXMLTag.jdField_byte);
    }
    for (int i = 0; i < paramXMLTag.jdField_case.size(); i++)
    {
      localObject = (XMLTag)paramXMLTag.jdField_case.get(i);
      localStringBuffer.append(toXML((XMLTag)localObject, true));
    }
    if (paramBoolean) {
      localStringBuffer.append("</" + paramXMLTag.a + ">");
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.XMLTag
 * JD-Core Version:    0.7.0.1
 */