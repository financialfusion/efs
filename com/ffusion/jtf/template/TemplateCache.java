package com.ffusion.jtf.template;

import com.ffusion.util.Strings;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.servlet.ServletContext;

public class TemplateCache
  implements Serializable
{
  private TemplatePathCache[] a;
  private boolean jdField_if;
  private ArrayList[] jdField_do;
  
  TemplateCache(TemplatePathCache[] paramArrayOfTemplatePathCache, boolean paramBoolean)
  {
    if (paramArrayOfTemplatePathCache == null) {
      throw new IllegalArgumentException();
    }
    this.a = paramArrayOfTemplatePathCache;
    this.jdField_if = paramBoolean;
    this.jdField_do = null;
  }
  
  public TemplateAction getTemplate(ServletContext paramServletContext, String paramString)
    throws IOException, TemplateParseException
  {
    TemplateAction localTemplateAction = null;
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new IllegalArgumentException();
    }
    paramString = Strings.trimChars(paramString, '/');
    for (int i = 0; i < this.a.length; i++)
    {
      if (this.jdField_if) {
        localTemplateAction = this.a[i].getTemplateFromCache(paramString);
      }
      if ((localTemplateAction == null) && (!this.a[i].getCheckedTemplateInPath(paramString))) {
        localTemplateAction = this.a[i].getTemplateFromServer(paramServletContext, this, paramString, this.jdField_do);
      }
      if (localTemplateAction != null) {
        break;
      }
    }
    return localTemplateAction;
  }
  
  public String findTemplatePath(ServletContext paramServletContext, String paramString)
    throws IOException
  {
    String str1 = null;
    String str2 = null;
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new IllegalArgumentException();
    }
    paramString = Strings.trimChars(paramString, '/');
    int i = paramString.indexOf('?');
    if (i != -1)
    {
      str2 = paramString.substring(i);
      paramString = paramString.substring(0, i);
    }
    for (int j = 0; j < this.a.length; j++)
    {
      str1 = this.a[j].getTemplateExistsOnServer(paramServletContext, this, paramString);
      if (str1 != null) {
        break;
      }
    }
    if ((str2 != null) && (str1 != null)) {
      return str1 + str2;
    }
    return str1;
  }
  
  public void setEncodings(ArrayList[] paramArrayOfArrayList)
  {
    this.jdField_do = paramArrayOfArrayList;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.template.TemplateCache
 * JD-Core Version:    0.7.0.1
 */