package com.ffusion.jtf.aio;

import com.ffusion.util.logging.DebugLog;
import com.microstar.xml.HandlerBase;
import com.microstar.xml.XmlParser;
import gnu.regexp.RE;
import gnu.regexp.REException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;

public class PlatformClassificationLayer
  extends ArrayList
  implements PlatformDefines
{
  private static final char a = ';';
  
  public void build(URL paramURL)
    throws Exception
  {
    int i = 0;
    XmlParser localXmlParser = new XmlParser();
    a locala = new a();
    clear();
    localXmlParser.setHandler(locala);
    localXmlParser.parse(paramURL.toString(), null, (String)null);
    for (int j = 0; j < size(); j++)
    {
      HashMap localHashMap = (HashMap)get(j);
      if (((String)localHashMap.get("ID")).equalsIgnoreCase("GENERIC")) {
        i = 1;
      }
    }
    if (i == 0) {
      throw new Exception("PlatformClassificationLayer: No GENERIC entry found in " + paramURL);
    }
  }
  
  public String getID(String paramString)
  {
    Object localObject = null;
    if (paramString != null) {
      for (int i = 0; i < size(); i++)
      {
        HashMap localHashMap = (HashMap)get(i);
        String str2 = (String)localHashMap.get("ID");
        String str1 = (String)localHashMap.get("NAME");
        str1 = (String)localHashMap.get("SIGNATURE");
        if (str1 != null) {
          try
          {
            RE localRE = new RE(str1);
            if (localRE.isMatch(paramString))
            {
              localObject = str2;
              break;
            }
          }
          catch (REException localREException)
          {
            DebugLog.log(Level.INFO, "User-Agent string not found.  Defaulting to generic browser type");
          }
        }
      }
    }
    return localObject;
  }
  
  public String toString()
  {
    String str = "\nPlatformClassificationLayer:\n";
    for (int i = 0; i < size(); i++)
    {
      str = str + "  " + i + ":\n";
      HashMap localHashMap = (HashMap)get(i);
      Object[] arrayOfObject = localHashMap.keySet().toArray();
      for (int j = 0; j < localHashMap.size(); j++) {
        str = str + "    " + arrayOfObject[j].toString() + "=" + localHashMap.get(arrayOfObject[j].toString()) + "\n";
      }
    }
    return str;
  }
  
  class a
    extends HandlerBase
  {
    Stack jdField_if = new Stack();
    HashMap a = new HashMap();
    
    public a() {}
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
      throws Exception
    {
      this.a.put(paramString1.toUpperCase(), paramString2);
    }
    
    public void startElement(String paramString)
    {
      this.jdField_if.push(paramString);
    }
    
    public void endElement(String paramString)
    {
      this.jdField_if.pop();
      if (paramString.equalsIgnoreCase("PLATFORM"))
      {
        PlatformClassificationLayer.this.add(this.a);
        this.a = new HashMap();
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str1 = new String(paramArrayOfChar, paramInt1, paramInt2);
      str1 = str1.trim();
      if (str1.length() > 0)
      {
        String str2 = (String)this.a.get(this.jdField_if.peek());
        if (str2 != null) {
          str2 = str2 + ';' + str1;
        } else {
          str2 = str1;
        }
        this.a.put(this.jdField_if.peek(), str2);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.aio.PlatformClassificationLayer
 * JD-Core Version:    0.7.0.1
 */