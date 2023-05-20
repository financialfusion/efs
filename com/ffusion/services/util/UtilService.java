package com.ffusion.services.util;

import com.ffusion.banklookup.FinancialInstitutionException;
import com.ffusion.banklookup.adapters.FinancialInstitutionAdapter;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.util.CountryDefn;
import com.ffusion.beans.util.CountryDefns;
import com.ffusion.beans.util.CountryFormatDefn;
import com.ffusion.beans.util.FieldValidationDefn;
import com.ffusion.beans.util.FieldValidationDefns;
import com.ffusion.beans.util.LanguageDefn;
import com.ffusion.beans.util.LanguageDefns;
import com.ffusion.beans.util.StateProvinceDefn;
import com.ffusion.beans.util.StateProvinceDefns;
import com.ffusion.beans.util.UtilException;
import com.ffusion.services.IUtilService2;
import com.ffusion.util.Qsort;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class UtilService
  implements IUtilService2
{
  private String jdField_try;
  private HashMap jdField_int;
  private HashMap jdField_for;
  private HashMap jdField_if;
  private HashMap a;
  private Locale jdField_byte = Locale.US;
  private static final char jdField_do = 'N';
  private static final char jdField_new = 'A';
  
  public void initialize(HashMap paramHashMap)
    throws UtilException
  {
    String str1 = "com.ffusion.services.util.UtilService.initialize";
    try
    {
      DebugLog.log(Level.INFO, str1 + ": Processing Util Service config xml " + "fieldValidation.xml");
      InputStream localInputStream = null;
      localObject1 = null;
      try
      {
        localInputStream = ResourceUtil.getResourceAsStream(this, "fieldValidation.xml");
        localObject1 = Strings.streamToString(localInputStream, "UTF-8");
      }
      catch (Exception localException2)
      {
        throw new UtilException(1, " Unable to load Util Service configuration file fieldValidation.xml");
      }
      localObject2 = null;
      try
      {
        localObject2 = new XMLTag(true);
        ((XMLTag)localObject2).build((String)localObject1);
        localObject1 = null;
        this.jdField_int = new HashMap();
        this.jdField_for = new HashMap();
      }
      catch (Exception localException3)
      {
        throw new UtilException(1, " An error occurred while parsing the Util Service configuration file fieldValidationFormats.xml", localException3);
      }
      localObject3 = ((XMLTag)localObject2).getContainedTag("CountryList");
      if (localObject3 == null) {
        throw new UtilException(1, str1 + " No '" + "CountryList" + "' tag found in file " + "fieldValidation.xml");
      }
      ArrayList localArrayList = ((XMLTag)localObject3).getContainedTagList();
      for (int i = 0; i < localArrayList.size(); i++)
      {
        XMLTag localXMLTag2 = (XMLTag)localArrayList.get(i);
        CountryFormatDefn localCountryFormatDefn = new CountryFormatDefn();
        StateProvinceDefns localStateProvinceDefns = new StateProvinceDefns();
        if (localXMLTag2.getContainedTag("CountryCode") == null) {
          throw new UtilException(1, str1 + " No '" + "CountryCode" + "' tag found in file " + "fieldValidation.xml");
        }
        String str2 = localXMLTag2.getContainedTag("CountryCode").getTagContent();
        XMLTag localXMLTag3 = localXMLTag2.getContainedTag("PhoneFormatList");
        Object localObject7;
        if (localXMLTag3 != null)
        {
          localObject4 = localXMLTag3.getContainedTagList();
          for (int j = 0; j < ((ArrayList)localObject4).size(); j++)
          {
            XMLTag localXMLTag4 = (XMLTag)((ArrayList)localObject4).get(j);
            if (localXMLTag4.getContainedTag("Name") == null) {
              throw new UtilException(1, str1 + " No '" + "Name" + "' tag found in file " + "fieldValidation.xml");
            }
            if (localXMLTag4.getContainedTag("Mask") == null) {
              throw new UtilException(1, str1 + " No '" + "Mask" + "' tag found in file " + "fieldValidation.xml");
            }
            localObject7 = new FieldValidationDefn();
            ((FieldValidationDefn)localObject7).setName(localXMLTag4.getContainedTag("Name").getTagContent());
            ((FieldValidationDefn)localObject7).setMask(localXMLTag4.getContainedTag("Mask").getTagContent());
            localCountryFormatDefn.addPhoneFormat((FieldValidationDefn)localObject7);
          }
        }
        Object localObject4 = localXMLTag2.getContainedTag("ZipCodeFormatList");
        Object localObject8;
        if (localObject4 != null)
        {
          localObject5 = ((XMLTag)localObject4).getContainedTagList();
          for (int k = 0; k < ((ArrayList)localObject5).size(); k++)
          {
            localObject7 = (XMLTag)((ArrayList)localObject5).get(k);
            if (((XMLTag)localObject7).getContainedTag("Name") == null) {
              throw new UtilException(1, str1 + " No '" + "Name" + "' tag found in file " + "fieldValidation.xml");
            }
            if (((XMLTag)localObject7).getContainedTag("Mask") == null) {
              throw new UtilException(1, str1 + " No '" + "Mask" + "' tag found in file " + "fieldValidation.xml");
            }
            localObject8 = new FieldValidationDefn();
            ((FieldValidationDefn)localObject8).setName(((XMLTag)localObject7).getContainedTag("Name").getTagContent());
            ((FieldValidationDefn)localObject8).setMask(((XMLTag)localObject7).getContainedTag("Mask").getTagContent());
            localCountryFormatDefn.addZipCodeFormat((FieldValidationDefn)localObject8);
          }
        }
        Object localObject5 = localXMLTag2.getContainedTag("SSNFormatList");
        if (localObject5 != null)
        {
          localObject6 = ((XMLTag)localObject5).getContainedTagList();
          for (int m = 0; m < ((ArrayList)localObject6).size(); m++)
          {
            localObject8 = (XMLTag)((ArrayList)localObject6).get(m);
            if (((XMLTag)localObject8).getContainedTag("Name") == null) {
              throw new UtilException(1, str1 + " No '" + "Name" + "' tag found in file " + "fieldValidation.xml");
            }
            if (((XMLTag)localObject8).getContainedTag("Mask") == null) {
              throw new UtilException(1, str1 + " No '" + "Mask" + "' tag found in file " + "fieldValidation.xml");
            }
            FieldValidationDefn localFieldValidationDefn = new FieldValidationDefn();
            localFieldValidationDefn.setName(((XMLTag)localObject8).getContainedTag("Name").getTagContent());
            localFieldValidationDefn.setMask(((XMLTag)localObject8).getContainedTag("Mask").getTagContent());
            localCountryFormatDefn.addSSNFormat(localFieldValidationDefn);
          }
        }
        Object localObject6 = localXMLTag2.getContainedTag("StateKeyList");
        if (localObject6 == null) {
          throw new UtilException(1, str1 + " No '" + "StateKeyList" + "' tag found in file " + "fieldValidation.xml");
        }
        StringTokenizer localStringTokenizer = new StringTokenizer(((XMLTag)localObject6).getTagContent(), ",");
        while (localStringTokenizer.hasMoreTokens())
        {
          localObject8 = new StateProvinceDefn();
          ((StateProvinceDefn)localObject8).setStateKey(localStringTokenizer.nextToken());
          ((StateProvinceDefn)localObject8).setCountryCode(str2);
          localStateProvinceDefns.add(localObject8);
        }
        this.jdField_for.put(str2, localStateProvinceDefns);
        this.jdField_int.put(str2, localCountryFormatDefn);
      }
      XMLTag localXMLTag1 = ((XMLTag)localObject2).getContainedTag("BankEmployeeCountryCode");
      this.jdField_try = localXMLTag1.getTagContent();
      a();
      jdField_if();
      DebugLog.log(Level.INFO, str1 + ": Util service initialized");
    }
    catch (UtilException localUtilException)
    {
      throw localUtilException;
    }
    catch (Exception localException1)
    {
      localObject1 = new StringWriter();
      localObject2 = new PrintWriter((Writer)localObject1);
      localException1.printStackTrace((PrintWriter)localObject2);
      localObject3 = str1 + ": An error occurred while initializing the Util Service: " + ((StringWriter)localObject1).toString();
      DebugLog.log(Level.SEVERE, (String)localObject3);
      throw new UtilException(0, (String)localObject3, localException1);
    }
    catch (Throwable localThrowable)
    {
      Object localObject1 = new StringWriter();
      Object localObject2 = new PrintWriter((Writer)localObject1);
      localThrowable.printStackTrace((PrintWriter)localObject2);
      Object localObject3 = str1 + ": An error occurred while initializing the Util Service: " + ((StringWriter)localObject1).toString();
      DebugLog.log(Level.SEVERE, (String)localObject3);
      throw new UtilException(0, (String)localObject3, localThrowable);
    }
  }
  
  private void jdField_if()
    throws UtilException
  {
    String str1 = "com.ffusion.services.util.UtilService.initializeLanguages";
    DebugLog.log(Level.INFO, str1 + ": Processing Util Service language config xml " + "languages.xml");
    InputStream localInputStream = null;
    String str2 = null;
    try
    {
      localInputStream = ResourceUtil.getResourceAsStream(this, "languages.xml");
      str2 = Strings.streamToString(localInputStream, "UTF-8");
    }
    catch (Exception localException1)
    {
      this.a = new HashMap();
      this.a.put(this.jdField_byte.toString(), new LanguageDefn(this.jdField_byte, true));
      return;
    }
    XMLTag localXMLTag = null;
    try
    {
      localXMLTag = new XMLTag(true);
      localXMLTag.build(str2);
      str2 = null;
      this.a = new HashMap();
    }
    catch (Exception localException2)
    {
      this.a = new HashMap();
      this.a.put(this.jdField_byte.toString(), new LanguageDefn(this.jdField_byte, true));
      return;
    }
    HashMap localHashMap = XMLUtil.tagToHashMap(localXMLTag);
    if (!localHashMap.containsKey("LANGUAGE_LIST")) {
      throw new UtilException(1, str1 + " No '" + "LANGUAGE_LIST" + "' tag found in the file " + "languages.xml");
    }
    ArrayList localArrayList = (ArrayList)((HashMap)localHashMap.get("LANGUAGE_LIST")).get("LANGUAGE");
    for (int i = 0; i < localArrayList.size(); i++)
    {
      localObject1 = (HashMap)localArrayList.get(i);
      str4 = (String)((HashMap)localObject1).get("LOCALE");
      localObject2 = new LanguageDefn();
      ((LanguageDefn)localObject2).setLanguage(str4);
      ((LanguageDefn)localObject2).setIsDefault(false);
      this.a.put(str4, localObject2);
    }
    String str3 = (String)localHashMap.get("DEFAULT_LANGUAGE");
    if (str3 == null) {
      throw new UtilException(1, str1 + " No '" + "DEFAULT_LANGUAGE" + "' tag found in the file " + "languages.xml");
    }
    if (this.a.get(str3) == null) {
      throw new UtilException(1, str1 + " The default language is not in the list of supported languages in the file " + "languages.xml");
    }
    Object localObject1 = new StringTokenizer(str3, "_");
    String str4 = null;
    Object localObject2 = null;
    if (((StringTokenizer)localObject1).hasMoreTokens()) {
      str4 = ((StringTokenizer)localObject1).nextToken();
    }
    if (((StringTokenizer)localObject1).hasMoreTokens()) {
      localObject2 = ((StringTokenizer)localObject1).nextToken();
    }
    if (str4 == null) {
      throw new UtilException(9, str1 + " The default language is invalid");
    }
    if (localObject2 == null) {
      this.jdField_byte = new Locale(str4);
    } else {
      this.jdField_byte = new Locale(str4, (String)localObject2);
    }
    ((LanguageDefn)this.a.get(str3)).setIsDefault(true);
  }
  
  private void a()
    throws UtilException
  {
    String str1 = "com.ffusion.services.util.UtilService.parseBankLookupConfigXML";
    DebugLog.log(Level.INFO, str1 + ": Processing Util Service config xml " + "bankLookupCountries.xml");
    InputStream localInputStream = null;
    String str2 = null;
    try
    {
      localInputStream = ResourceUtil.getResourceAsStream(this, "bankLookupCountries.xml");
      str2 = Strings.streamToString(localInputStream, "UTF-8");
    }
    catch (Exception localException1)
    {
      throw new UtilException(1, " Unable to load Util Service configuration file bankLookupCountries.xml");
    }
    XMLTag localXMLTag1 = null;
    try
    {
      localXMLTag1 = new XMLTag(true);
      localXMLTag1.build(str2);
      str2 = null;
      this.jdField_if = new HashMap();
    }
    catch (Exception localException2)
    {
      throw new UtilException(1, " An error occurred while parsing the Util Service configuration file bankLookupCountries.xml", localException2);
    }
    XMLTag localXMLTag2 = localXMLTag1.getContainedTag("CountryList");
    if (localXMLTag2 == null) {
      throw new UtilException(1, str1 + " No '" + "CountryList" + "' tag found in file " + "bankLookupCountries.xml");
    }
    ArrayList localArrayList = localXMLTag2.getContainedTagList();
    for (int i = 0; i < localArrayList.size(); i++)
    {
      XMLTag localXMLTag3 = (XMLTag)localArrayList.get(i);
      if (localXMLTag3.getContainedTag("CountryCode") == null) {
        throw new UtilException(1, str1 + " No '" + "CountryCode" + "' tag found in file " + "bankLookupCountries.xml");
      }
      String str3 = localXMLTag3.getContainedTag("CountryName").getTagContent();
      String str4 = localXMLTag3.getContainedTag("CountryCode").getTagContent();
      this.jdField_if.put(str3, str4);
    }
  }
  
  public ArrayList getValidPhoneFormats(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws UtilException
  {
    String str1 = "com.ffusion.services.util.UtilService.getValidPhoneFormats";
    String str2 = null;
    if (paramSecureUser == null)
    {
      localObject = str1 + ": Invalid user.";
      DebugLog.log(Level.SEVERE, (String)localObject);
      throw new UtilException(5, (String)localObject);
    }
    if ((paramSecureUser.getUserType() == 2) && (paramString == null)) {
      str2 = this.jdField_try;
    } else {
      str2 = paramString;
    }
    if (str2 == null)
    {
      localObject = str1 + ": Country Code is null.";
      DebugLog.log(Level.SEVERE, (String)localObject);
      throw new UtilException(4, (String)localObject);
    }
    Object localObject = (CountryFormatDefn)this.jdField_int.get(str2);
    if (localObject == null) {
      return null;
    }
    FieldValidationDefns localFieldValidationDefns = ((CountryFormatDefn)localObject).getPhoneFormats();
    if (localFieldValidationDefns == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = localFieldValidationDefns.iterator();
    while (localIterator.hasNext())
    {
      FieldValidationDefn localFieldValidationDefn = (FieldValidationDefn)localIterator.next();
      localArrayList.add(localFieldValidationDefn.getName());
    }
    return localArrayList;
  }
  
  public ArrayList getValidZipCodeFormats(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws UtilException
  {
    String str1 = "com.ffusion.services.util.UtilService.getValidZipCodeFormats";
    String str2 = null;
    if (paramSecureUser == null)
    {
      localObject = str1 + ": Invalid user.";
      DebugLog.log(Level.SEVERE, (String)localObject);
      throw new UtilException(5, (String)localObject);
    }
    if ((paramSecureUser.getUserType() == 2) && (paramString == null)) {
      str2 = this.jdField_try;
    } else {
      str2 = paramString;
    }
    if (str2 == null)
    {
      localObject = str1 + ": Country Code is null.";
      DebugLog.log(Level.SEVERE, (String)localObject);
      throw new UtilException(4, (String)localObject);
    }
    Object localObject = (CountryFormatDefn)this.jdField_int.get(str2);
    if (localObject == null) {
      return null;
    }
    FieldValidationDefns localFieldValidationDefns = ((CountryFormatDefn)localObject).getZipCodeFormats();
    if (localFieldValidationDefns == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = localFieldValidationDefns.iterator();
    while (localIterator.hasNext())
    {
      FieldValidationDefn localFieldValidationDefn = (FieldValidationDefn)localIterator.next();
      localArrayList.add(localFieldValidationDefn.getName());
    }
    return localArrayList;
  }
  
  public ArrayList getValidSSNFormats(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws UtilException
  {
    String str1 = "com.ffusion.services.util.UtilService.getValidSSNFormats";
    String str2 = null;
    if (paramSecureUser == null)
    {
      localObject = str1 + ": Invalid user.";
      DebugLog.log(Level.SEVERE, (String)localObject);
      throw new UtilException(5, (String)localObject);
    }
    if ((paramSecureUser.getUserType() == 2) && (paramString == null)) {
      str2 = this.jdField_try;
    } else {
      str2 = paramString;
    }
    if (str2 == null)
    {
      localObject = str1 + ": Country Code is null.";
      DebugLog.log(Level.SEVERE, (String)localObject);
      throw new UtilException(4, (String)localObject);
    }
    Object localObject = (CountryFormatDefn)this.jdField_int.get(str2);
    if (localObject == null) {
      return null;
    }
    FieldValidationDefns localFieldValidationDefns = ((CountryFormatDefn)localObject).getSSNFormats();
    if (localFieldValidationDefns == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = localFieldValidationDefns.iterator();
    while (localIterator.hasNext())
    {
      FieldValidationDefn localFieldValidationDefn = (FieldValidationDefn)localIterator.next();
      localArrayList.add(localFieldValidationDefn.getName());
    }
    return localArrayList;
  }
  
  public String validatePhoneFormat(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws UtilException
  {
    String str1 = "com.ffusion.services.util.UtilService.validatePhoneFormat";
    String str2 = null;
    String str3 = null;
    if (paramString2 == null)
    {
      localObject = str1 + ": Phone number is null.";
      DebugLog.log(Level.SEVERE, (String)localObject);
      throw new UtilException(6, (String)localObject);
    }
    if ((paramSecureUser != null) && (paramSecureUser.getUserType() == 2) && (paramString1 == null)) {
      str3 = this.jdField_try;
    } else {
      str3 = paramString1;
    }
    if (str3 == null)
    {
      localObject = str1 + ": Country Code is null.";
      DebugLog.log(Level.SEVERE, (String)localObject);
      throw new UtilException(4, (String)localObject);
    }
    Object localObject = (CountryFormatDefn)this.jdField_int.get(str3);
    if (localObject == null) {
      return "";
    }
    FieldValidationDefns localFieldValidationDefns = ((CountryFormatDefn)localObject).getPhoneFormats();
    if ((localFieldValidationDefns == null) || (localFieldValidationDefns.isEmpty())) {
      return "";
    }
    str2 = a(paramString2, localFieldValidationDefns);
    return str2;
  }
  
  public String validateZipCodeFormat(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws UtilException
  {
    String str1 = "com.ffusion.services.util.UtilService.validateZipCodeFormat";
    String str2 = null;
    String str3 = null;
    if (paramString2 == null)
    {
      localObject = str1 + ": Zip Code is null.";
      DebugLog.log(Level.SEVERE, (String)localObject);
      throw new UtilException(7, (String)localObject);
    }
    if ((paramSecureUser != null) && (paramSecureUser.getUserType() == 2) && (paramString1 == null)) {
      str3 = this.jdField_try;
    } else {
      str3 = paramString1;
    }
    if (str3 == null)
    {
      localObject = str1 + ": Country Code is null.";
      DebugLog.log(Level.SEVERE, (String)localObject);
      throw new UtilException(4, (String)localObject);
    }
    Object localObject = (CountryFormatDefn)this.jdField_int.get(str3);
    if (localObject == null) {
      return "";
    }
    FieldValidationDefns localFieldValidationDefns = ((CountryFormatDefn)localObject).getZipCodeFormats();
    if ((localFieldValidationDefns == null) || (localFieldValidationDefns.isEmpty())) {
      return "";
    }
    str2 = a(paramString2, localFieldValidationDefns);
    return str2;
  }
  
  public String validateSSNFormat(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws UtilException
  {
    String str1 = "com.ffusion.services.util.UtilService.validateSSNFormat";
    String str2 = null;
    String str3 = null;
    if (paramString2 == null)
    {
      localObject = str1 + ": SSN is null.";
      DebugLog.log(Level.SEVERE, (String)localObject);
      throw new UtilException(8, (String)localObject);
    }
    if ((paramSecureUser != null) && (paramSecureUser.getUserType() == 2) && (paramString1 == null)) {
      str3 = this.jdField_try;
    } else {
      str3 = paramString1;
    }
    if (str3 == null)
    {
      localObject = str1 + ": Country Code is null.";
      DebugLog.log(Level.SEVERE, (String)localObject);
      throw new UtilException(4, (String)localObject);
    }
    Object localObject = (CountryFormatDefn)this.jdField_int.get(str3);
    if (localObject == null) {
      return "";
    }
    FieldValidationDefns localFieldValidationDefns = ((CountryFormatDefn)localObject).getSSNFormats();
    if ((localFieldValidationDefns == null) || (localFieldValidationDefns.isEmpty())) {
      return "";
    }
    str2 = a(paramString2, localFieldValidationDefns);
    return str2;
  }
  
  private String a(String paramString, FieldValidationDefns paramFieldValidationDefns)
  {
    Object localObject = null;
    if ((paramString == null) || (paramFieldValidationDefns == null)) {
      return null;
    }
    Iterator localIterator = paramFieldValidationDefns.iterator();
    while (localIterator.hasNext())
    {
      FieldValidationDefn localFieldValidationDefn = (FieldValidationDefn)localIterator.next();
      String str = localFieldValidationDefn.getMask();
      if (a(paramString, str))
      {
        localObject = str;
        break;
      }
    }
    return localObject;
  }
  
  private boolean a(String paramString1, String paramString2)
  {
    boolean bool = true;
    if ((paramString1 != null) && (paramString2 != null) && (paramString1.length() == paramString2.length())) {
      for (int i = 0; i < paramString2.length(); i++)
      {
        char c1 = Character.toUpperCase(paramString2.charAt(i));
        char c2 = paramString1.charAt(i);
        if (c1 == 'A')
        {
          if (!Character.isLetter(c2))
          {
            bool = false;
            break;
          }
        }
        else if (c1 == 'N')
        {
          if (!Character.isDigit(c2))
          {
            bool = false;
            break;
          }
        }
        else if (c2 != c1)
        {
          bool = false;
          break;
        }
      }
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isRegisteredCountry(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws UtilException
  {
    String str = "com.ffusion.services.util.UtilService.isRegisteredCountry";
    if (paramString == null)
    {
      localObject = str + ": Country Code is null.";
      DebugLog.log(Level.SEVERE, (String)localObject);
      throw new UtilException(4, (String)localObject);
    }
    Object localObject = (CountryFormatDefn)this.jdField_int.get(paramString);
    return localObject != null;
  }
  
  public StateProvinceDefns getStatesForCountry(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws UtilException
  {
    if ((paramSecureUser != null) && (paramSecureUser.getUserType() == 2) && (paramString == null)) {
      paramString = this.jdField_try;
    }
    StateProvinceDefns localStateProvinceDefns1 = (StateProvinceDefns)this.jdField_for.get(paramString);
    if ((localStateProvinceDefns1 == null) || (localStateProvinceDefns1.size() == 0)) {
      return null;
    }
    StateProvinceDefns localStateProvinceDefns2 = new StateProvinceDefns();
    Iterator localIterator = localStateProvinceDefns1.iterator();
    Object localObject2;
    while (localIterator.hasNext())
    {
      localObject1 = (StateProvinceDefn)localIterator.next();
      localObject2 = new StateProvinceDefn();
      ((StateProvinceDefn)localObject2).setCountryCode(((StateProvinceDefn)localObject1).getCountryCode());
      ((StateProvinceDefn)localObject2).setStateKey(((StateProvinceDefn)localObject1).getStateKey());
      localStateProvinceDefns2.add(localObject2);
    }
    Object localObject1 = null;
    if (paramSecureUser != null)
    {
      localObject1 = paramSecureUser.getLocale();
    }
    else if ((paramHashMap != null) && (paramHashMap.containsKey("LOCALE")))
    {
      localObject2 = paramHashMap.get("LOCALE");
      if ((localObject2 != null) && ((localObject2 instanceof Locale))) {
        localObject1 = (Locale)localObject2;
      } else {
        localObject1 = Locale.getDefault();
      }
    }
    else
    {
      localObject1 = Locale.getDefault();
    }
    localStateProvinceDefns2.setLocale((Locale)localObject1);
    Qsort.sortSortables(localStateProvinceDefns2, "SORT_BY_NAME", 1);
    return localStateProvinceDefns2;
  }
  
  public StateProvinceDefn getStateProvinceDefnForState(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws UtilException
  {
    if ((paramSecureUser.getUserType() == 2) && (paramString2 == null)) {
      paramString2 = this.jdField_try;
    }
    StateProvinceDefns localStateProvinceDefns = (StateProvinceDefns)this.jdField_for.get(paramString2);
    if ((localStateProvinceDefns == null) || (localStateProvinceDefns.size() == 0)) {
      return null;
    }
    StateProvinceDefn localStateProvinceDefn1 = null;
    Iterator localIterator = localStateProvinceDefns.iterator();
    while (localIterator.hasNext())
    {
      StateProvinceDefn localStateProvinceDefn2 = (StateProvinceDefn)localIterator.next();
      if (localStateProvinceDefn2.getStateKey().equalsIgnoreCase(paramString1))
      {
        localStateProvinceDefn1 = new StateProvinceDefn();
        localStateProvinceDefn1.setStateKey(localStateProvinceDefn2.getStateKey());
        localStateProvinceDefn1.setCountryCode(localStateProvinceDefn2.getCountryCode());
        localStateProvinceDefn1.setLocale(paramSecureUser.getLocale());
        break;
      }
    }
    return localStateProvinceDefn1;
  }
  
  public LanguageDefns getLanguageList(SecureUser paramSecureUser, HashMap paramHashMap)
    throws UtilException
  {
    return new LanguageDefns(this.a.values());
  }
  
  public LanguageDefn getLanguage(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws UtilException
  {
    LanguageDefn localLanguageDefn = (LanguageDefn)this.a.get(paramString);
    if (localLanguageDefn == null) {
      throw new UtilException(9, "The requested language: '" + paramString + "' is not supported");
    }
    return localLanguageDefn;
  }
  
  public Locale getDefaultLanguage(HashMap paramHashMap)
    throws UtilException
  {
    return this.jdField_byte;
  }
  
  public String getCodeForBankLookupCountryName(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws UtilException
  {
    String str = (String)this.jdField_if.get(paramString);
    return str;
  }
  
  public CountryDefns getBankLookupStandardCountryNames(SecureUser paramSecureUser, HashMap paramHashMap)
    throws UtilException
  {
    ArrayList localArrayList = null;
    try
    {
      localArrayList = FinancialInstitutionAdapter.getCountries();
    }
    catch (FinancialInstitutionException localFinancialInstitutionException)
    {
      throw new UtilException("Cannot retrieve countries from BankLookup", localFinancialInstitutionException);
    }
    CountryDefns localCountryDefns = new CountryDefns();
    if (localArrayList != null)
    {
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        CountryDefn localCountryDefn = new CountryDefn();
        String str1 = localIterator.next().toString();
        String str2 = getCodeForBankLookupCountryName(paramSecureUser, str1, paramHashMap);
        if (str2 == null)
        {
          DebugLog.log("Cannot retrieve country code for " + str1);
        }
        else
        {
          localCountryDefn.setBankLookupCountry(str1);
          localCountryDefn.setCountryCode(str2);
          localCountryDefns.add(localCountryDefn);
        }
      }
    }
    localCountryDefns.setLocale(paramSecureUser.getLocale());
    Qsort.sortSortables(localCountryDefns, "SORT_BY_NAME", 1);
    return localCountryDefns;
  }
  
  public CountryDefns getCountryList(SecureUser paramSecureUser, HashMap paramHashMap)
    throws UtilException
  {
    Locale localLocale = null;
    if (paramSecureUser != null)
    {
      localLocale = paramSecureUser.getLocale();
    }
    else if ((paramHashMap != null) && (paramHashMap.containsKey("LOCALE")))
    {
      localObject = paramHashMap.get("LOCALE");
      if ((localObject != null) && ((localObject instanceof Locale))) {
        localLocale = (Locale)localObject;
      } else {
        localLocale = Locale.getDefault();
      }
    }
    else
    {
      localLocale = Locale.getDefault();
    }
    Object localObject = ResourceUtil.getBundle("com.ffusion.util.states", localLocale);
    if (localObject == null) {
      throw new UtilException("Cannot find resource bundle com.ffusion.util.states.");
    }
    String str1 = null;
    try
    {
      str1 = ((ResourceBundle)localObject).getString("CountryList");
    }
    catch (MissingResourceException localMissingResourceException)
    {
      throw new UtilException("Cannot find CountryList property in resource bundle com.ffusion.util.states.");
    }
    CountryDefns localCountryDefns = new CountryDefns();
    StringTokenizer localStringTokenizer = new StringTokenizer(str1, ",");
    while (localStringTokenizer.hasMoreTokens())
    {
      CountryDefn localCountryDefn = new CountryDefn();
      String str2 = localStringTokenizer.nextToken();
      localCountryDefn.setCountryCode(str2);
      localCountryDefns.add(localCountryDefn);
    }
    localCountryDefns.setLocale(localLocale);
    Qsort.sortSortables(localCountryDefns, "SORT_BY_NAME", 1);
    return localCountryDefns;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.util.UtilService
 * JD-Core Version:    0.7.0.1
 */