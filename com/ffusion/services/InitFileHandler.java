package com.ffusion.services;

import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.microstar.xml.HandlerBase;
import com.microstar.xml.XmlHandler;
import com.microstar.xml.XmlParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class InitFileHandler
{
  protected static HashMap m_InitHashMap = null;
  
  public int initialize(String paramString, HandlerBase paramHandlerBase)
  {
    int i = 0;
    try
    {
      if (m_InitHashMap == null) {
        m_InitHashMap = new HashMap();
      }
      ArrayList localArrayList = null;
      if ((m_InitHashMap != null) && (m_InitHashMap.get(paramString) != null)) {
        localArrayList = (ArrayList)m_InitHashMap.get(paramString);
      }
      Object localObject1;
      String str;
      Object localObject2;
      Object localObject3;
      if (localArrayList == null)
      {
        localObject1 = ResourceUtil.getResourceAsStream(this, paramString);
        if (localObject1 == null)
        {
          i = 7;
        }
        else
        {
          str = Strings.streamToString((InputStream)localObject1);
          BufferedReader localBufferedReader = new BufferedReader(new StringReader(str));
          localObject2 = new a();
          localObject3 = new XmlParser();
          ((XmlParser)localObject3).setHandler((XmlHandler)localObject2);
          ((XmlParser)localObject3).parse(null, null, localBufferedReader);
          localArrayList = ((a)localObject2).a();
          m_InitHashMap.put(paramString, localArrayList);
        }
      }
      if (localArrayList != null)
      {
        localObject1 = localArrayList.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          str = (String)((Iterator)localObject1).next();
          int j = str.indexOf("=");
          localObject2 = str.substring(0, j);
          localObject3 = str.substring(j + 1);
          paramHandlerBase.attribute((String)localObject2, (String)localObject3, true);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace(System.out);
      i = 8;
    }
    return i;
  }
  
  public class a
    extends HandlerBase
  {
    private ArrayList a = new ArrayList();
    
    public a() {}
    
    public ArrayList a()
    {
      return this.a;
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (this.a != null) {
        this.a.add(paramString1 + "=" + paramString2);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.InitFileHandler
 * JD-Core Version:    0.7.0.1
 */