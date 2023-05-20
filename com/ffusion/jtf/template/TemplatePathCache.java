package com.ffusion.jtf.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletContext;

class TemplatePathCache
  implements Serializable
{
  private HashMap jdField_if;
  private String jdField_do;
  private boolean a;
  
  TemplatePathCache(String paramString, boolean paramBoolean)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new IllegalArgumentException();
    }
    this.jdField_if = new HashMap();
    this.jdField_do = paramString;
    this.a = paramBoolean;
  }
  
  protected TemplateAction getTemplateFromCache(String paramString)
  {
    Object localObject = this.jdField_if.get(paramString);
    if ((localObject != null) && ((localObject instanceof TemplateAction))) {
      return (TemplateAction)this.jdField_if.get(paramString);
    }
    return null;
  }
  
  protected boolean getCheckedTemplateInPath(String paramString)
  {
    Object localObject = this.jdField_if.get(paramString);
    return (localObject != null) && (!(localObject instanceof TemplateAction));
  }
  
  protected synchronized String getTemplateExistsOnServer(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString)
    throws IOException
  {
    String str;
    if (this.jdField_do.charAt(0) == '/') {
      str = this.jdField_do + paramString;
    } else {
      str = '/' + this.jdField_do + paramString;
    }
    Object localObject = this.jdField_if.get(paramString);
    if (localObject != null)
    {
      if ((localObject instanceof TemplateAction)) {
        return str;
      }
      return null;
    }
    InputStream localInputStream = paramServletContext.getResourceAsStream(str);
    if (localInputStream != null)
    {
      TemplateAction localTemplateAction = new TemplateAction();
      this.jdField_if.put(paramString, localTemplateAction);
      localInputStream.close();
      return str;
    }
    this.jdField_if.put(paramString, "NA");
    return null;
  }
  
  protected synchronized TemplateAction getTemplateFromServer(ServletContext paramServletContext, TemplateCache paramTemplateCache, String paramString, ArrayList[] paramArrayOfArrayList)
    throws IOException, TemplateParseException
  {
    TemplateAction localTemplateAction = null;
    String str1 = this.jdField_do + paramString;
    String str2 = null;
    if (paramArrayOfArrayList != null)
    {
      localObject = str1.toLowerCase();
      int i = paramArrayOfArrayList[0].size();
      for (int j = 0; j < i; j++) {
        if (((String)localObject).startsWith((String)paramArrayOfArrayList[0].get(j)))
        {
          str2 = (String)paramArrayOfArrayList[1].get(j);
          break;
        }
      }
    }
    Object localObject = paramServletContext.getResourceAsStream(str1);
    if (localObject != null)
    {
      localTemplateAction = new TemplateAction();
      try
      {
        localTemplateAction.parse(paramServletContext, paramTemplateCache, StreamToString((InputStream)localObject, str2));
      }
      catch (IOException localIOException)
      {
        ((InputStream)localObject).close();
        throw localIOException;
      }
      if ((this.a) && (localTemplateAction.isCacheable())) {
        this.jdField_if.put(paramString, localTemplateAction);
      }
      ((InputStream)localObject).close();
    }
    if (localTemplateAction == null) {
      this.jdField_if.put(paramString, "NA");
    }
    return localTemplateAction;
  }
  
  public static String StreamToString(InputStream paramInputStream, String paramString)
    throws IOException
  {
    int i = 4096;
    InputStreamReader localInputStreamReader = null;
    if (paramString != null) {
      localInputStreamReader = new InputStreamReader(paramInputStream, paramString);
    } else {
      localInputStreamReader = new InputStreamReader(paramInputStream);
    }
    StringBuffer localStringBuffer = new StringBuffer();
    BufferedReader localBufferedReader = new BufferedReader(localInputStreamReader, 4096);
    try
    {
      char[] arrayOfChar = new char[4096];
      int j = 0;
      do
      {
        j = localBufferedReader.read(arrayOfChar, 0, 4096);
        if (j != -1) {
          localStringBuffer.append(arrayOfChar, 0, j);
        }
      } while (j != -1);
    }
    catch (IOException localIOException1)
    {
      System.out.println("Error loading template from disk.  Possible unsupported encoding type.");
      if (paramString == null) {
        System.out.println("Java file.encoding is " + System.getProperty("file.encoding"));
      } else {
        System.out.println("platforms.xml specified " + paramString + " where Java file.encoding is " + System.getProperty("file.encoding"));
      }
      System.out.println(localIOException1.toString());
      try
      {
        localBufferedReader.close();
      }
      catch (IOException localIOException2) {}
      throw localIOException1;
    }
    localBufferedReader.close();
    return localStringBuffer.toString();
  }
  
  protected void clearCache()
  {
    this.jdField_if.clear();
  }
  
  protected void setUseCache(boolean paramBoolean)
  {
    this.a = paramBoolean;
    if (!paramBoolean) {
      clearCache();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.template.TemplatePathCache
 * JD-Core Version:    0.7.0.1
 */