package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.user.UserLocale;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BuildPropertyLocaleHashmaps
  extends BaseTask
  implements Task
{
  public static final String FIRST_CHOICE_PROPERTY_NAMES = "firstChoicePropertyNamesMap";
  public static final String SECOND_CHOICE_PROPERTY_NAMES = "secondChoicePropertyNamesMap";
  private ArrayList aR;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute("UserLocale");
    String str2 = localUserLocale.getCountry();
    String str3 = localUserLocale.getLanguage();
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    Iterator localIterator = this.aR.iterator();
    while (localIterator.hasNext())
    {
      String str4 = (String)localIterator.next();
      localHashMap1.put(str4, str4 + "_" + str3 + "_" + str2);
      localHashMap2.put(str4, str4 + "_" + str3);
    }
    localHttpSession.setAttribute("firstChoicePropertyNamesMap", localHashMap1);
    localHttpSession.setAttribute("secondChoicePropertyNamesMap", localHashMap2);
    return str1;
  }
  
  public void setPropertyName(String paramString)
  {
    if (this.aR == null) {
      this.aR = new ArrayList();
    }
    this.aR.add(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.BuildPropertyLocaleHashmaps
 * JD-Core Version:    0.7.0.1
 */