package com.ffusion.tasks.portal;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.portal.Advisor;
import com.ffusion.beans.portal.Advisors;
import com.ffusion.beans.portal.Calculator;
import com.ffusion.beans.portal.CalculatorCategories;
import com.ffusion.beans.portal.Calculators;
import com.ffusion.beans.portal.Forecast;
import com.ffusion.beans.portal.Forecasts;
import com.ffusion.beans.portal.GeographicUnit;
import com.ffusion.beans.portal.GeographicUnits;
import com.ffusion.beans.portal.News;
import com.ffusion.beans.portal.NewsHeadlines;
import com.ffusion.beans.portal.PortalItems;
import com.ffusion.beans.portal.StockIndexes;
import com.ffusion.beans.portal.Stocks;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PortalInit
  extends BaseTask
  implements Task
{
  public static final String NEWS = "NEWS";
  public static final String STOCKS = "STOCK";
  public static final String FORECASTS = "FORECASTS";
  public static final String CALCULATORS = "CALCULATORS";
  public static final String ADVISORS = "ADVISORS";
  protected static HashMap m_InitHashMap;
  protected String calculatorConfigFile = "calculators.xml";
  protected String advisorConfigFile = "advisors.xml";
  protected String initList = "NEWS,STOCK,FORECASTS,CALCULATORS,ADVISORS";
  protected boolean _initializeAll = false;
  protected boolean validateData = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ServletContext localServletContext = paramHttpServlet.getServletContext();
    try
    {
      HashMap localHashMap = new HashMap(1);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      String str2 = XMLUtil.XMLDecode(UserAdmin.getAdditionalData(localSecureUser, "PORTAL_SETTINGS", localHashMap));
      PortalItems localPortalItems = new PortalItems();
      StringReader localStringReader = null;
      if ((str2 != null) && (str2.length() > 0))
      {
        localStringReader = new StringReader(str2);
        localPortalItems.startXMLParsing(localStringReader);
        localStringReader.close();
      }
      localHttpSession.setAttribute("PortalItems", localPortalItems);
      Object localObject;
      if (!this._initializeAll)
      {
        localObject = "";
        for (int i = 0; i < localPortalItems.size(); i++) {
          localObject = (String)localObject + (String)localPortalItems.get(i) + ",";
        }
        setInitList((String)localObject);
      }
      if (this.initList.indexOf("STOCK") != -1)
      {
        localObject = new Stocks();
        StockIndexes localStockIndexes = new StockIndexes();
        if ((str2 != null) && (str2.length() > 0))
        {
          localStringReader = new StringReader(str2);
          ((Stocks)localObject).startXMLParsing(localStringReader);
          localStringReader.close();
          localStringReader = new StringReader(str2);
          localStockIndexes.startXMLParsing(localStringReader);
          localStringReader.close();
        }
        localHttpSession.setAttribute("StockSettings", localObject);
        localHttpSession.setAttribute("StockIndexSettings", localStockIndexes);
      }
      if (this.initList.indexOf("FORECASTS") != -1) {
        jdMethod_for(localServletContext, localHttpSession, str2);
      }
      if (this.initList.indexOf("NEWS") != -1) {
        jdMethod_try(localHttpSession, str2);
      }
      if (this.initList.indexOf("CALCULATORS") != -1) {
        jdMethod_new(localHttpSession, str2);
      }
      if (this.initList.indexOf("ADVISORS") != -1) {
        jdMethod_int(localHttpSession, str2);
      }
      if (this.error != 0) {
        str1 = this.taskErrorURL;
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
  
  private void jdMethod_try(HttpSession paramHttpSession, String paramString)
  {
    NewsHeadlines localNewsHeadlines1 = new NewsHeadlines();
    localNewsHeadlines1.setCategory("FRONTPAGE");
    localNewsHeadlines1.setMaxHeadlines(0);
    paramHttpSession.setAttribute("NewsFrontpageSettings", localNewsHeadlines1);
    localNewsHeadlines1 = new NewsHeadlines();
    localNewsHeadlines1.setCategory("BUSINESS");
    localNewsHeadlines1.setMaxHeadlines(0);
    paramHttpSession.setAttribute("NewsBusinessSettings", localNewsHeadlines1);
    localNewsHeadlines1 = new NewsHeadlines();
    localNewsHeadlines1.setCategory("SPORTS");
    localNewsHeadlines1.setMaxHeadlines(0);
    paramHttpSession.setAttribute("NewsSportsSettings", localNewsHeadlines1);
    News localNews = new News();
    if ((paramString != null) && (paramString.length() > 0))
    {
      localObject = new StringReader(paramString);
      localNews.startXMLParsing((Reader)localObject);
      try
      {
        ((Reader)localObject).close();
      }
      catch (Exception localException) {}
    }
    Object localObject = localNews.iterator();
    while (((Iterator)localObject).hasNext())
    {
      NewsHeadlines localNewsHeadlines2 = (NewsHeadlines)((Iterator)localObject).next();
      String str = localNewsHeadlines2.getCategory();
      if (str.equals("FRONTPAGE")) {
        paramHttpSession.setAttribute("NewsFrontpageSettings", localNewsHeadlines2);
      } else if (str.equals("BUSINESS")) {
        paramHttpSession.setAttribute("NewsBusinessSettings", localNewsHeadlines2);
      } else if (str.equals("SPORTS")) {
        paramHttpSession.setAttribute("NewsSportsSettings", localNewsHeadlines2);
      }
    }
    paramHttpSession.setAttribute("NewsSettings", localNews);
  }
  
  private void jdMethod_for(ServletContext paramServletContext, HttpSession paramHttpSession, String paramString)
  {
    if (paramServletContext.getAttribute("ForecastCountryCityList") == null) {
      this.error = jdMethod_for("cities.txt", paramServletContext);
    }
    GeographicUnits localGeographicUnits = (GeographicUnits)paramServletContext.getAttribute("ForecastStatesContext");
    if (localGeographicUnits == null)
    {
      localGeographicUnits = getGeographicUnits("states.properties");
      if (localGeographicUnits == null) {
        this.error = 9019;
      } else {
        paramServletContext.setAttribute("ForecastStatesContext", localGeographicUnits);
      }
    }
    localGeographicUnits = (GeographicUnits)paramServletContext.getAttribute("ForecastCountriesContext");
    if (localGeographicUnits == null)
    {
      localGeographicUnits = getGeographicUnits("countries.properties");
      if (localGeographicUnits == null) {
        this.error = 9020;
      } else {
        paramServletContext.setAttribute("ForecastCountriesContext", localGeographicUnits);
      }
    }
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    if (localLocale == null)
    {
      this.error = 9015;
    }
    else
    {
      paramHttpSession.setAttribute("Days", jdMethod_int("days", localLocale));
      Forecasts localForecasts = new Forecasts();
      if ((paramString != null) && (paramString.length() > 0))
      {
        StringReader localStringReader = new StringReader(paramString);
        localForecasts.startXMLParsing(localStringReader);
        try
        {
          localStringReader.close();
        }
        catch (Exception localException) {}
      }
      if (this.validateData == true)
      {
        int i = 0;
        HashMap localHashMap1 = (HashMap)paramServletContext.getAttribute("ForecastCountryCityList");
        HashMap localHashMap2 = (HashMap)paramServletContext.getAttribute("ForecastStateCityList");
        Object localObject;
        for (int j = localForecasts.size() - 1; j >= 0; j--)
        {
          localObject = (Forecast)localForecasts.get(j);
          if (!jdMethod_for(((Forecast)localObject).getCity(), localHashMap1, localHashMap2))
          {
            localForecasts.remove(j);
            int k = paramString.indexOf(((Forecast)localObject).getCity());
            int m = -1;
            if (k != -1)
            {
              m = paramString.indexOf("</FORECAST>", k + ((Forecast)localObject).getCity().length());
              while ((k >= 0) && (paramString.charAt(k) != '<')) {
                k--;
              }
              if ((k > 0) && (m != -1))
              {
                m += 12;
                paramString = paramString.substring(0, k) + paramString.substring(m);
                i = 1;
              }
            }
          }
        }
        if (i != 0) {
          try
          {
            HashMap localHashMap3 = new HashMap(1);
            localObject = (SecureUser)paramHttpSession.getAttribute("SecureUser");
            UserAdmin.addAdditionalData((SecureUser)localObject, "PORTAL_SETTINGS", paramString, localHashMap3);
          }
          catch (CSILException localCSILException) {}
        }
      }
      paramHttpSession.setAttribute("ForecastSettings", localForecasts);
    }
  }
  
  private String[] jdMethod_int(String paramString, Locale paramLocale)
  {
    String[] arrayOfString = new String[7];
    ResourceBundle localResourceBundle = ResourceBundle.getBundle(paramString, paramLocale);
    arrayOfString[0] = localResourceBundle.getString("day.Sunday");
    arrayOfString[1] = localResourceBundle.getString("day.Monday");
    arrayOfString[2] = localResourceBundle.getString("day.Tuesday");
    arrayOfString[3] = localResourceBundle.getString("day.Wednesday");
    arrayOfString[4] = localResourceBundle.getString("day.Thursday");
    arrayOfString[5] = localResourceBundle.getString("day.Friday");
    arrayOfString[6] = localResourceBundle.getString("day.Saturday");
    return arrayOfString;
  }
  
  private int jdMethod_for(String paramString, ServletContext paramServletContext)
  {
    BufferedReader localBufferedReader = null;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    try
    {
      localBufferedReader = jdMethod_else(paramString);
      String str1 = localBufferedReader.readLine();
      String str2 = null;
      String str3 = null;
      String str4 = null;
      while (str1 != null)
      {
        if (str1.endsWith(" US"))
        {
          j = str1.lastIndexOf(" US");
          str2 = str1.substring(j - 2, j);
          str3 = str1.substring(0, j - 3);
          localGeographicUnits = (GeographicUnits)localHashMap1.get(str2);
          if (localGeographicUnits == null)
          {
            localGeographicUnits = new GeographicUnits();
            localHashMap1.put(str2, localGeographicUnits);
          }
          localGeographicUnit = new GeographicUnit();
          localGeographicUnit.setName(str3);
          localGeographicUnit.setAbbr(str1);
          localGeographicUnits.add(localGeographicUnit);
        }
        int j = str1.lastIndexOf(' ');
        str4 = str1.substring(j + 1);
        str3 = str1.substring(0, j);
        GeographicUnits localGeographicUnits = (GeographicUnits)localHashMap2.get(str4);
        if (localGeographicUnits == null)
        {
          localGeographicUnits = new GeographicUnits();
          localHashMap2.put(str4, localGeographicUnits);
        }
        GeographicUnit localGeographicUnit = new GeographicUnit();
        localGeographicUnit.setName(str3);
        localGeographicUnit.setAbbr(str1);
        localGeographicUnits.add(localGeographicUnit);
        str1 = localBufferedReader.readLine();
      }
      try
      {
        localBufferedReader.close();
      }
      catch (Exception localException1) {}
      int i;
      paramServletContext.setAttribute("ForecastCountryCityList", localHashMap2);
    }
    catch (Exception localException2)
    {
      DebugLog.throwing("PortalInitForecasts Task Exception: ", localException2);
      i = this.error = 9018;
      return i;
    }
    finally
    {
      try
      {
        localBufferedReader.close();
      }
      catch (Exception localException4) {}
    }
    paramServletContext.setAttribute("ForecastStateCityList", localHashMap1);
    return 0;
  }
  
  private boolean jdMethod_for(String paramString, HashMap paramHashMap1, HashMap paramHashMap2)
  {
    boolean bool = false;
    String str1 = null;
    GeographicUnits localGeographicUnits = null;
    int i;
    if (paramString.endsWith(" US"))
    {
      i = paramString.lastIndexOf(" US");
      String str2 = paramString.substring(i - 2, i);
      localGeographicUnits = (GeographicUnits)paramHashMap2.get(str2);
    }
    else
    {
      i = paramString.lastIndexOf(' ');
      str1 = paramString.substring(i + 1);
      localGeographicUnits = (GeographicUnits)paramHashMap1.get(str1);
    }
    if ((localGeographicUnits != null) && (localGeographicUnits.getByAbbr(paramString) != null)) {
      bool = true;
    }
    return bool;
  }
  
  public GeographicUnits getGeographicUnits(String paramString)
  {
    BufferedReader localBufferedReader = null;
    localGeographicUnits = new GeographicUnits();
    try
    {
      localBufferedReader = jdMethod_else(paramString);
      String str1 = localBufferedReader.readLine();
      String str2;
      while (str1 != null) {
        if ((str1.startsWith("#")) || (str1.indexOf("=") == -1))
        {
          str1 = localBufferedReader.readLine();
        }
        else
        {
          str2 = str1.substring(0, str1.indexOf("="));
          String str3 = str1.substring(str1.indexOf("=") + 1);
          GeographicUnit localGeographicUnit = new GeographicUnit();
          localGeographicUnit.setName(str2);
          localGeographicUnit.setAbbr(str3);
          localGeographicUnits.add(localGeographicUnit);
          str1 = localBufferedReader.readLine();
        }
      }
      return localGeographicUnits;
    }
    catch (Exception localException2)
    {
      DebugLog.throwing("PortalInitForecasts Task Exception: ", localException2);
      str2 = null;
      return str2;
    }
    finally
    {
      try
      {
        localBufferedReader.close();
      }
      catch (Exception localException4) {}
    }
  }
  
  private BufferedReader jdMethod_else(String paramString)
    throws Exception
  {
    BufferedReader localBufferedReader = null;
    if (m_InitHashMap == null) {
      m_InitHashMap = new HashMap();
    }
    String str = "";
    if ((m_InitHashMap != null) && (m_InitHashMap.get(paramString) != null))
    {
      str = (String)m_InitHashMap.get(paramString);
    }
    else
    {
      InputStream localInputStream = ResourceUtil.getResourceAsStream(this, paramString);
      if (localInputStream == null) {
        return null;
      }
      str = Strings.streamToString(localInputStream, "UTF-8");
      m_InitHashMap.put(paramString, str);
    }
    if ((str == null) || (str.length() == 0)) {
      return null;
    }
    localBufferedReader = new BufferedReader(new StringReader(str));
    return localBufferedReader;
  }
  
  private void jdMethod_new(HttpSession paramHttpSession, String paramString)
  {
    PortalItems localPortalItems = (PortalItems)paramHttpSession.getAttribute("PortalItems");
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    if (localPortalItems == null)
    {
      this.error = 9009;
    }
    else if (localLocale == null)
    {
      this.error = 9015;
    }
    else
    {
      paramHttpSession.setAttribute("CalculatorsMaster", jdMethod_for(this.calculatorConfigFile, localLocale));
      Calculators localCalculators1 = new Calculators();
      if ((paramString != null) && (paramString.length() > 0))
      {
        localObject = new StringReader(paramString);
        localCalculators1.startXMLParsing((Reader)localObject);
        try
        {
          ((StringReader)localObject).close();
        }
        catch (Exception localException) {}
      }
      paramHttpSession.setAttribute("CalculatorsSettings", localCalculators1);
      Object localObject = (CalculatorCategories)paramHttpSession.getAttribute("CalculatorsMaster");
      if (localObject == null)
      {
        this.error = 9017;
      }
      else
      {
        Calculators localCalculators2 = new Calculators();
        paramHttpSession.setAttribute("Calculators", localCalculators2);
        Iterator localIterator = localCalculators1.iterator();
        while (localIterator.hasNext())
        {
          Calculator localCalculator1 = (Calculator)localIterator.next();
          Calculator localCalculator2 = ((CalculatorCategories)localObject).getCalculator(localCalculator1.getID());
          if (localCalculator2 != null) {
            localCalculators2.add(localCalculator2);
          }
        }
      }
    }
  }
  
  private CalculatorCategories jdMethod_for(String paramString, Locale paramLocale)
  {
    BufferedReader localBufferedReader = null;
    localCalculatorCategories = new CalculatorCategories();
    try
    {
      int i = paramString.indexOf(".");
      String str = paramString.substring(0, i) + "_" + paramLocale.toString() + paramString.substring(i);
      try
      {
        localBufferedReader = jdMethod_else(str);
      }
      catch (Exception localException3) {}
      if (localBufferedReader == null) {
        localBufferedReader = jdMethod_else(paramString);
      }
      localCalculatorCategories.startXMLParsing(localBufferedReader);
      return localCalculatorCategories;
    }
    catch (Exception localException2)
    {
      DebugLog.log("PortalInitCalculators Task Exception: ", localException2);
      str = null;
      return str;
    }
    finally
    {
      try
      {
        localBufferedReader.close();
      }
      catch (Exception localException5) {}
    }
  }
  
  public void setCalculatorConfigFile(String paramString)
  {
    this.calculatorConfigFile = paramString;
  }
  
  public String getCalculatorConfigFile()
  {
    return this.calculatorConfigFile;
  }
  
  private void jdMethod_int(HttpSession paramHttpSession, String paramString)
  {
    PortalItems localPortalItems = (PortalItems)paramHttpSession.getAttribute("PortalItems");
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    if (localPortalItems == null)
    {
      this.error = 9009;
    }
    else if (localLocale == null)
    {
      this.error = 9015;
    }
    else
    {
      paramHttpSession.setAttribute("AdvisorsMaster", jdMethod_new(this.advisorConfigFile, localLocale));
      Advisors localAdvisors1 = new Advisors();
      if ((paramString != null) && (paramString.length() > 0))
      {
        localObject = new StringReader(paramString);
        localAdvisors1.startXMLParsing((Reader)localObject);
        try
        {
          ((StringReader)localObject).close();
        }
        catch (Exception localException) {}
      }
      paramHttpSession.setAttribute("AdvisorsSettings", localAdvisors1);
      Object localObject = (Advisors)paramHttpSession.getAttribute("AdvisorsMaster");
      if (localObject == null)
      {
        this.error = 9016;
      }
      else
      {
        Advisors localAdvisors2 = new Advisors();
        paramHttpSession.setAttribute("Advisors", localAdvisors2);
        Iterator localIterator = localAdvisors1.iterator();
        while (localIterator.hasNext())
        {
          Advisor localAdvisor1 = (Advisor)localIterator.next();
          Advisor localAdvisor2 = ((Advisors)localObject).getAdvisor(localAdvisor1.getCategory());
          if (localAdvisor2 != null) {
            localAdvisors2.add(localAdvisor2);
          }
        }
      }
    }
  }
  
  private Advisors jdMethod_new(String paramString, Locale paramLocale)
  {
    BufferedReader localBufferedReader = null;
    localAdvisors = new Advisors();
    try
    {
      int i = paramString.indexOf(".");
      String str = paramString.substring(0, i) + "_" + paramLocale.toString() + paramString.substring(i);
      try
      {
        localBufferedReader = jdMethod_else(str);
      }
      catch (Exception localException3) {}
      if (localBufferedReader == null) {
        localBufferedReader = jdMethod_else(paramString);
      }
      localAdvisors.startXMLParsing(localBufferedReader);
      return localAdvisors;
    }
    catch (Exception localException2)
    {
      DebugLog.throwing("PortalInitAdvisors Task Exception: ", localException2);
      str = null;
      return str;
    }
    finally
    {
      try
      {
        localBufferedReader.close();
      }
      catch (Exception localException5) {}
    }
  }
  
  public void setAdvisorConfigFile(String paramString)
  {
    this.advisorConfigFile = paramString;
  }
  
  public String getConfigFile()
  {
    return this.advisorConfigFile;
  }
  
  public void setInitList(String paramString)
  {
    this.initList = paramString;
  }
  
  public void setValidateData(String paramString)
  {
    this.validateData = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setInitializeAll(String paramString)
  {
    this._initializeAll = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.PortalInit
 * JD-Core Version:    0.7.0.1
 */