package com.ffusion.jtf.aio;

import com.microstar.xml.HandlerBase;
import com.microstar.xml.XmlParser;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

public class Platforms
  extends HashMap
  implements PlatformDefines
{
  private static final char jdField_if = ';';
  private HashMap jdField_try;
  private HashMap jdField_int;
  private HashMap jdField_new;
  private ArrayList[] jdField_for = null;
  private String jdField_do = "ISO-8859-1";
  
  public void build(URL paramURL)
    throws Exception
  {
    XmlParser localXmlParser = new XmlParser();
    a locala = new a(this);
    this.jdField_try = new HashMap();
    this.jdField_int = new HashMap();
    this.jdField_new = new HashMap();
    clear();
    localXmlParser.setHandler(locala);
    localXmlParser.parse(paramURL.toString(), null, (String)null);
    if (get("GENERIC") == null) {
      throw new Exception("Platforms -- No GENERIC entry found in " + paramURL);
    }
  }
  
  public String[] getPaths(String paramString)
  {
    String[] arrayOfString = null;
    HashMap localHashMap = (HashMap)get(paramString);
    if (localHashMap == null) {
      localHashMap = (HashMap)get("GENERIC");
    }
    if (localHashMap != null)
    {
      String str1 = (String)localHashMap.get("PATHDATAID");
      localHashMap = (HashMap)this.jdField_try.get(str1);
      str1 = (String)localHashMap.get("PATH");
      if (str1 != null)
      {
        ArrayList localArrayList = new ArrayList();
        while (str1.indexOf(';') != -1)
        {
          if (str1.substring(0, str1.indexOf(';')).endsWith("/")) {
            localArrayList.add(str1.substring(0, str1.indexOf(';')));
          } else {
            localArrayList.add(str1.substring(0, str1.indexOf(';')) + "/");
          }
          str1 = str1.substring(str1.indexOf(';') + 1);
        }
        if (str1.endsWith("/")) {
          localArrayList.add(str1);
        } else {
          localArrayList.add(str1 + "/");
        }
        arrayOfString = new String[localArrayList.size()];
        for (int i = 0; i < localArrayList.size(); i++)
        {
          String str2 = (String)localArrayList.get(i);
          arrayOfString[i] = ((String)localArrayList.get(i));
        }
      }
    }
    return arrayOfString;
  }
  
  public String getContentType(String paramString)
  {
    String str = null;
    HashMap localHashMap = (HashMap)get(paramString);
    if (localHashMap == null) {
      localHashMap = (HashMap)get("GENERIC");
    }
    if (localHashMap != null)
    {
      str = (String)localHashMap.get("CONTENTTYPEID");
      localHashMap = (HashMap)this.jdField_int.get(str);
      if (localHashMap == null) {
        str = null;
      } else {
        str = (String)localHashMap.get("VALUE");
      }
    }
    return str;
  }
  
  public ArrayList[] getEncodings()
  {
    return this.jdField_for;
  }
  
  public String getBrowserEncoding()
  {
    return this.jdField_do;
  }
  
  public String[] getHeaderNames(String paramString)
  {
    String[] arrayOfString = null;
    HashMap localHashMap = (HashMap)get(paramString);
    if (localHashMap == null) {
      localHashMap = (HashMap)get("GENERIC");
    }
    if (localHashMap != null)
    {
      String str = (String)localHashMap.get("HEADERID");
      ArrayList[] arrayOfArrayList = (ArrayList[])this.jdField_new.get(str);
      if ((arrayOfArrayList != null) && (arrayOfArrayList[0].size() != 0))
      {
        arrayOfString = new String[arrayOfArrayList[0].size()];
        for (int i = 0; i < arrayOfArrayList[0].size(); i++) {
          arrayOfString[i] = ((String)arrayOfArrayList[0].get(i));
        }
      }
    }
    return arrayOfString;
  }
  
  public String[] getHeaderValues(String paramString)
  {
    String[] arrayOfString = null;
    HashMap localHashMap = (HashMap)get(paramString);
    if (localHashMap == null) {
      localHashMap = (HashMap)get("GENERIC");
    }
    if (localHashMap != null)
    {
      String str = (String)localHashMap.get("HEADERID");
      ArrayList[] arrayOfArrayList = (ArrayList[])this.jdField_new.get(str);
      if (arrayOfArrayList[1].size() != 0)
      {
        arrayOfString = new String[arrayOfArrayList[1].size()];
        for (int i = 0; i < arrayOfArrayList[1].size(); i++) {
          arrayOfString[i] = ((String)arrayOfArrayList[1].get(i));
        }
      }
    }
    return arrayOfString;
  }
  
  public String getValue(String paramString1, String paramString2)
  {
    String str = null;
    HashMap localHashMap = (HashMap)get(paramString1);
    if (localHashMap != null) {
      str = (String)localHashMap.get(paramString2);
    }
    return str;
  }
  
  public String toString()
  {
    String str = "\nPlatforms:\n";
    Object[] arrayOfObject1 = keySet().toArray();
    Object localObject;
    Object[] arrayOfObject2;
    int k;
    for (int i = 0; i < size(); i++)
    {
      str = str + "  " + arrayOfObject1[i].toString() + ":\n";
      localObject = (HashMap)get(arrayOfObject1[i].toString());
      arrayOfObject2 = ((HashMap)localObject).keySet().toArray();
      for (k = 0; k < ((HashMap)localObject).size(); k++) {
        str = str + "    " + arrayOfObject2[k].toString() + "=" + ((HashMap)localObject).get(arrayOfObject2[k].toString()) + "\n";
      }
    }
    str = str + "Paths:\n";
    arrayOfObject1 = this.jdField_try.keySet().toArray();
    for (i = 0; i < this.jdField_try.size(); i++)
    {
      str = str + "  " + arrayOfObject1[i].toString() + ":\n";
      localObject = (HashMap)this.jdField_try.get(arrayOfObject1[i].toString());
      arrayOfObject2 = ((HashMap)localObject).keySet().toArray();
      for (k = 0; k < ((HashMap)localObject).size(); k++) {
        str = str + "    " + arrayOfObject2[k].toString() + "=" + ((HashMap)localObject).get(arrayOfObject2[k].toString()) + "\n";
      }
    }
    str = str + "Content Types:\n";
    arrayOfObject1 = this.jdField_int.keySet().toArray();
    for (i = 0; i < this.jdField_int.size(); i++)
    {
      str = str + "  " + arrayOfObject1[i].toString() + ":\n";
      localObject = (HashMap)this.jdField_int.get(arrayOfObject1[i].toString());
      arrayOfObject2 = ((HashMap)localObject).keySet().toArray();
      for (k = 0; k < ((HashMap)localObject).size(); k++) {
        str = str + "    " + arrayOfObject2[k].toString() + "=" + ((HashMap)localObject).get(arrayOfObject2[k].toString()) + "\n";
      }
    }
    str = str + "Headers:\n";
    arrayOfObject1 = this.jdField_new.keySet().toArray();
    for (i = 0; i < this.jdField_new.size(); i++)
    {
      str = str + "  " + arrayOfObject1[i].toString() + ":\n";
      localObject = (ArrayList[])this.jdField_new.get(arrayOfObject1[i].toString());
      for (int j = 0; j < localObject[0].size(); j++) {
        str = str + "    " + localObject[0].get(j).toString() + "=" + localObject[1].get(j).toString() + "\n";
      }
    }
    return str;
  }
  
  class a
    extends HandlerBase
  {
    String jdField_for;
    Stack jdField_if;
    HashMap a;
    HashMap jdField_do;
    
    public a(HashMap paramHashMap)
    {
      this.jdField_do = paramHashMap;
      this.jdField_if = new Stack();
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
      throws Exception
    {
      String str = (String)this.jdField_if.peek();
      if (this.jdField_if.peek().toString().equalsIgnoreCase("PLATFORMS")) {
        this.a = this.jdField_do;
      } else if (this.jdField_if.peek().toString().equalsIgnoreCase("PATHS")) {
        this.a = Platforms.this.jdField_try;
      } else if (this.jdField_if.peek().toString().equalsIgnoreCase("CONTENT_TYPES")) {
        this.a = Platforms.this.jdField_int;
      } else if (this.jdField_if.peek().toString().equalsIgnoreCase("HEADERS")) {
        this.a = Platforms.this.jdField_new;
      }
      if (this.jdField_if.peek().toString().equalsIgnoreCase("ENCODINGS"))
      {
        if (Platforms.this.jdField_for == null)
        {
          Platforms.this.jdField_for = new ArrayList[3];
          Platforms.this.jdField_for[0] = new ArrayList();
          Platforms.this.jdField_for[1] = new ArrayList();
          Platforms.this.jdField_for[2] = new ArrayList();
        }
        if (paramString1.equalsIgnoreCase("PATH")) {
          Platforms.this.jdField_for[0].add(paramString2);
        } else if (paramString1.equalsIgnoreCase("ENCODING")) {
          Platforms.this.jdField_for[1].add(paramString2);
        } else if (paramString1.equalsIgnoreCase("CHARSET")) {
          Platforms.this.jdField_for[2].add(paramString2);
        } else if (paramString1.equalsIgnoreCase("BROWSER")) {
          Platforms.this.jdField_do = paramString2;
        }
      }
      else
      {
        ArrayList[] arrayOfArrayList;
        if (paramString1.equalsIgnoreCase("ID"))
        {
          this.jdField_for = paramString2;
          if (this.a == Platforms.this.jdField_new)
          {
            arrayOfArrayList = new ArrayList[2];
            arrayOfArrayList[0] = new ArrayList();
            arrayOfArrayList[1] = new ArrayList();
            this.a.put(this.jdField_for, arrayOfArrayList);
          }
          else
          {
            this.a.put(this.jdField_for, new HashMap());
          }
        }
        else if (this.a == Platforms.this.jdField_new)
        {
          arrayOfArrayList = (ArrayList[])this.a.get(this.jdField_for);
          if (paramString1.equalsIgnoreCase("NAME")) {
            arrayOfArrayList[0].add(paramString2);
          } else if (paramString1.equalsIgnoreCase("VALUE")) {
            arrayOfArrayList[1].add(paramString2);
          }
        }
        else
        {
          ((HashMap)this.a.get(this.jdField_for)).put(paramString1.toUpperCase(), paramString2);
        }
      }
    }
    
    public void startElement(String paramString)
    {
      this.jdField_if.push(paramString);
    }
    
    public void endElement(String paramString)
    {
      this.jdField_if.pop();
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str1 = new String(paramArrayOfChar, paramInt1, paramInt2);
      str1 = str1.trim();
      if (str1.length() > 0)
      {
        HashMap localHashMap = (HashMap)this.a.get(this.jdField_for);
        String str2 = (String)localHashMap.get(this.jdField_if.peek());
        if (str2 != null) {
          str2 = str2 + ';' + str1;
        } else {
          str2 = str1;
        }
        localHashMap.put(this.jdField_if.peek(), str2);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.aio.Platforms
 * JD-Core Version:    0.7.0.1
 */