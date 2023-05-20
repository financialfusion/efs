package com.ffusion.util.beans;

import com.ffusion.util.Filterable;
import com.ffusion.util.Localeable;
import com.ffusion.util.Reflection;
import com.ffusion.util.Sortable;
import com.ffusion.util.Stringable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.Collator;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;

public abstract class ExtendABean
  extends LocaleableBean
  implements Filterable, Sortable, Localeable, XMLable, Map, Cloneable, Serializable, XMLStrings
{
  public static final String STRING = "STRING";
  public static final String CURRENCY = "CURRENCY";
  public static final String DATETIME = "DATETIME";
  public static final String PHONE = "PHONE";
  public static final String EMAIL = "EMAIL";
  public static final String SOCIAL_SECURITY = "SOCIAL_SECURITY";
  public static final String ZIP_CODE = "ZIP_CODE";
  public static final String BIRTH_DATE = "BIRTH_DATE";
  public static final String PAGER = "PAGER";
  public static final String CLASS = "class";
  public static final String ELEMENT = "ELEMENT";
  public static final String KEY = "KEY";
  public static final String VALUE = "VALUE";
  public static final String VERSION = "VERSION";
  static final String[] jdField_byte = { "<ELEMENT> Tag", "<KEY> Tag", "<VALUE> Tag", "KEY Text", "VALUE Text" };
  public static final String VALUEINFO_CLASS = "com.ffusion.ffs.bpw.interfaces.ValueInfo";
  public static final String VALUEINFO_SETVALUE_METHODNAME = "setValue";
  protected String currentKey;
  protected String objtype = "STRING";
  protected String datetype = "SHORT";
  protected String currentClass;
  protected HashMap map = new HashMap();
  protected int xmlversion;
  
  public ExtendABean()
  {
    this(null);
  }
  
  public ExtendABean(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public Object get(Object paramObject)
  {
    Object localObject = this.map.get(paramObject);
    if (localObject == null) {
      localObject = getProperty(localObject, paramObject.toString());
    }
    if ((localObject != null) && ((localObject instanceof DateTime))) {
      ((DateTime)localObject).setFormat(this.datetype);
    }
    return localObject;
  }
  
  public Object put(Object paramObject1, Object paramObject2)
  {
    if ((paramObject1 == null) || (paramObject2 == null)) {
      return null;
    }
    Object localObject;
    if ((paramObject2 instanceof String))
    {
      localObject = this.map.get(paramObject1);
      if (localObject == null) {
        localObject = setProperty(paramObject1.toString(), paramObject2.toString());
      } else if ((localObject instanceof Stringable)) {
        ((Stringable)localObject).setString(paramObject2.toString());
      } else {
        localObject = this.map.put(paramObject1, paramObject2);
      }
    }
    else
    {
      localObject = this.map.put(paramObject1, paramObject2);
    }
    return localObject;
  }
  
  protected Object getProperty(Object paramObject, String paramString)
  {
    String str1 = paramString;
    String str2 = null;
    if ((paramString == null) || (paramString.length() == 0)) {
      return paramObject;
    }
    int i = paramString.indexOf(".");
    if (i != -1)
    {
      str1 = paramString.substring(0, i);
      str2 = paramString.substring(i + 1);
    }
    if (paramObject == null)
    {
      paramObject = this.map.get(str1);
      if (paramObject == null) {
        paramObject = getObjectProperty(this, str1);
      }
      if (paramObject != null) {
        return getProperty(paramObject, str2);
      }
      return null;
    }
    Object localObject = getObjectProperty(paramObject, str1);
    if (localObject != null) {
      return getProperty(localObject, str2);
    }
    if ((paramObject instanceof Map)) {
      return getProperty(((Map)paramObject).get(str1), str2);
    }
    if ((paramObject instanceof List)) {
      try
      {
        return getProperty(((List)paramObject).get(Integer.valueOf(str1).intValue()), str2);
      }
      catch (Exception localException) {}
    }
    return null;
  }
  
  protected static Object getObjectProperty(Object paramObject, String paramString)
  {
    Method localMethod = null;
    if ((paramString == null) || (paramString.length() == 0)) {
      return paramObject;
    }
    if (paramObject != null)
    {
      localMethod = Reflection.findGetMethod(paramObject, paramString);
      if (localMethod != null) {
        try
        {
          return localMethod.invoke(paramObject, null);
        }
        catch (Exception localException) {}
      }
    }
    return null;
  }
  
  protected Object setProperty(String paramString1, String paramString2)
  {
    String str1 = "";
    String str2 = paramString1;
    Object localObject = null;
    if ((paramString1 == null) || (paramString2 == null)) {
      return null;
    }
    int i = paramString1.lastIndexOf(".");
    if (i != -1)
    {
      str1 = paramString1.substring(i + 1);
      paramString1 = paramString1.substring(0, i);
      localObject = get(paramString1);
      if ((localObject != null) && (str1.length() > 0))
      {
        Method localMethod = Reflection.findSetMethod(localObject, str1);
        if (localMethod != null)
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = paramString2;
          try
          {
            localMethod.invoke(localObject, arrayOfObject);
          }
          catch (Exception localException) {}
        }
        else if ((localObject instanceof List))
        {
          updateListObject((List)localObject, str1, paramString2);
        }
      }
    }
    else
    {
      this.map.put(str2, paramString2);
    }
    return null;
  }
  
  protected static boolean updateListObject(List paramList, String paramString, Object paramObject)
  {
    boolean bool = false;
    if (paramList != null) {
      try
      {
        int i = Integer.valueOf(paramString).intValue();
        if (i < paramList.size())
        {
          paramList.remove(i);
          paramList.add(i, paramObject);
        }
        else
        {
          paramList.add(paramObject);
        }
        bool = true;
      }
      catch (Exception localException) {}
    }
    return bool;
  }
  
  public boolean containsKey(Object paramObject)
  {
    return this.map.containsKey(paramObject);
  }
  
  public boolean containsValue(Object paramObject)
  {
    return this.map.containsValue(paramObject);
  }
  
  public void clear()
  {
    this.map.clear();
  }
  
  public Object remove(Object paramObject)
  {
    return this.map.remove(paramObject);
  }
  
  public int size()
  {
    return this.map.size();
  }
  
  public Collection values()
  {
    return this.map.values();
  }
  
  public Set entrySet()
  {
    return this.map.entrySet();
  }
  
  public Object clone()
  {
    try
    {
      ExtendABean localExtendABean = (ExtendABean)super.clone();
      if (localExtendABean.getHash() != null) {
        localExtendABean.map = ((HashMap)localExtendABean.getHash().clone());
      }
      if (localExtendABean.locale != null) {
        localExtendABean.locale = ((Locale)localExtendABean.locale.clone());
      }
      return localExtendABean;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public Set keySet()
  {
    return this.map.keySet();
  }
  
  public boolean equals(Object paramObject)
  {
    return paramObject.equals(this.map);
  }
  
  public void putAll(Map paramMap)
  {
    this.map.putAll(paramMap);
  }
  
  public boolean isEmpty()
  {
    return this.map.isEmpty();
  }
  
  public int hashCode()
  {
    return this.map.hashCode();
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public void setCurrentClass(String paramString)
  {
    this.currentClass = paramString;
  }
  
  public String getCurrentClass()
  {
    return this.currentClass;
  }
  
  private void a(String paramString1, String paramString2)
  {
    if ((paramString1 == null) && (paramString1.length() > 0)) {
      return;
    }
    this.currentKey = paramString1;
    if (this.map.get(paramString1) == null)
    {
      int i = paramString1.indexOf(".");
      if (i != -1) {
        paramString1 = paramString1.substring(0, i);
      }
      if (this.map.get(paramString1) == null)
      {
        Object localObject = createObject(this.currentClass, paramString2);
        if (localObject == null) {
          localObject = "Could not create object:" + this.currentClass;
        }
        this.map.put(paramString1, localObject);
      }
    }
  }
  
  public void setKey(String paramString)
  {
    a(paramString, null);
  }
  
  public String getKey()
  {
    return this.currentKey;
  }
  
  public void setCreateKey(String paramString)
  {
    if ((paramString == null) && (paramString.length() > 0)) {
      return;
    }
    String str = null;
    int i = paramString.indexOf(",");
    if (i != -1)
    {
      str = paramString.substring(i + 1);
      paramString = paramString.substring(0, i);
    }
    remove(paramString);
    a(paramString, str);
  }
  
  public void setRemoveKey(String paramString)
  {
    remove(paramString);
  }
  
  public void setValue(String paramString)
  {
    put(this.currentKey, paramString);
  }
  
  public String getValue()
  {
    String str = "";
    Object localObject = get(this.currentKey);
    if (localObject != null) {
      str = localObject.toString();
    }
    return str;
  }
  
  public void setObjectType(String paramString)
  {
    this.objtype = paramString;
  }
  
  public HashMap getHash()
  {
    return this.map;
  }
  
  public void setHash(HashMap paramHashMap)
  {
    this.map.clear();
    this.map.putAll(paramHashMap);
  }
  
  public void setHashEntry(String paramString)
  {
    this.currentKey = paramString;
  }
  
  public void setHashValue(String paramString)
  {
    setHashValue(this.currentKey, paramString);
  }
  
  public void setHashValue(String paramString1, String paramString2)
  {
    this.map.put(paramString1, paramString2);
  }
  
  public String getHashEntry(String paramString)
  {
    setHashEntry(paramString);
    return getHashEntry();
  }
  
  public String getHashValue()
  {
    return getHashEntry();
  }
  
  public String getHashEntry()
  {
    String str = "";
    Object localObject = get(this.currentKey);
    if (localObject != null) {
      if ((localObject instanceof DateTime))
      {
        DateTime localDateTime = (DateTime)localObject;
        localDateTime.setFormat(this.datetype);
        str = localDateTime.toString();
      }
      else
      {
        str = localObject.toString();
      }
    }
    return str;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    setHashValue(paramString1, paramString2);
    return true;
  }
  
  public void set(ExtendABean paramExtendABean)
  {
    if ((paramExtendABean != null) && (paramExtendABean.getHash() != null)) {
      setHash((HashMap)paramExtendABean.getHash().clone());
    }
    if (paramExtendABean.locale != null) {
      this.locale = ((Locale)paramExtendABean.locale.clone());
    }
    this.datetype = paramExtendABean.getDateFormat();
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=<>!", true);
    boolean bool = false;
    if ((localStringTokenizer.countTokens() == 3) || (localStringTokenizer.countTokens() == 4))
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      String str3 = localStringTokenizer.nextToken();
      if (localStringTokenizer.countTokens() == 1)
      {
        str2 = str2 + str3;
        str3 = localStringTokenizer.nextToken();
      }
      bool = isFilterablePreParsed(str1, str2, str3);
    }
    return bool;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    String str = null;
    Object localObject = null;
    boolean bool = false;
    localObject = get(paramString1);
    if (localObject == null)
    {
      int i = paramString1.lastIndexOf(".");
      if (i != -1)
      {
        str = paramString1.substring(0, i);
        paramString1 = paramString1.substring(i + 1);
        localObject = get(str);
      }
    }
    if (localObject != null) {
      if ((localObject instanceof Filterable)) {
        bool = ((Filterable)localObject).isFilterable(paramString1 + paramString2 + paramString3);
      } else if (!(localObject instanceof String)) {
        bool = isFilterable(paramString3, paramString2, localObject);
      } else {
        bool = isFilterable(localObject.toString(), paramString2, paramString3);
      }
    }
    return bool;
  }
  
  public static boolean isFilterable(String paramString1, String paramString2, String paramString3)
  {
    boolean bool = false;
    if (paramString1 == null) {
      return (paramString2.equals("!")) || (paramString2.equals("!!"));
    }
    if (paramString2.equals("=")) {
      bool = paramString1.equals(paramString3);
    } else if (paramString2.equals("==")) {
      bool = paramString1.equalsIgnoreCase(paramString3);
    } else if (paramString2.equals("!")) {
      bool = !paramString1.equals(paramString3);
    } else if (paramString2.equals("!!")) {
      bool = !paramString1.equalsIgnoreCase(paramString3);
    } else if (paramString2.equals("<")) {
      bool = paramString1.compareTo(paramString3) < 0;
    } else if (paramString2.equals("<<")) {
      bool = paramString1.compareToIgnoreCase(paramString3) < 0;
    } else if (paramString2.equals(">")) {
      bool = paramString1.compareTo(paramString3) > 0;
    } else if (paramString2.equals(">>")) {
      bool = paramString1.compareToIgnoreCase(paramString3) > 0;
    }
    return bool;
  }
  
  public static boolean isFilterable(int paramInt, String paramString1, String paramString2)
  {
    boolean bool = false;
    Integer localInteger1 = new Integer(paramInt);
    Integer localInteger2 = null;
    try
    {
      localInteger2 = new Integer(paramString2);
    }
    catch (NumberFormatException localNumberFormatException) {}
    if (localInteger2 == null) {
      return isFilterable(localInteger1.toString(), paramString1, paramString2);
    }
    if (paramString1.equals("=")) {
      bool = localInteger1.equals(localInteger2);
    } else if (paramString1.equals("==")) {
      bool = localInteger1.equals(localInteger2);
    } else if (paramString1.equals("!")) {
      bool = !localInteger1.equals(localInteger2);
    } else if (paramString1.equals("!!")) {
      bool = !localInteger1.equals(localInteger2);
    } else if (paramString1.equals("<")) {
      bool = localInteger1.compareTo(localInteger2) < 0;
    } else if (paramString1.equals("<<")) {
      bool = localInteger1.compareTo(localInteger2) < 0;
    } else if (paramString1.equals(">")) {
      bool = localInteger1.compareTo(localInteger2) > 0;
    } else if (paramString1.equals(">>")) {
      bool = localInteger1.compareTo(localInteger2) > 0;
    }
    return bool;
  }
  
  public boolean isFilterable(String paramString1, String paramString2, Object paramObject)
  {
    boolean bool = false;
    if ((paramObject instanceof Comparable))
    {
      Object localObject = createObject(paramObject.getClass().getName(), paramString1);
      if (localObject != null) {
        if ((paramString2.equals("=")) || (paramString2.equals("=="))) {
          bool = ((Comparable)paramObject).compareTo(localObject) == 0;
        } else if ((paramString2.equals("!")) || (paramString2.equals("!!"))) {
          bool = ((Comparable)paramObject).compareTo(localObject) != 0;
        } else if ((paramString2.equals("<")) || (paramString2.equals("<<"))) {
          bool = ((Comparable)paramObject).compareTo(localObject) < 0;
        } else if ((paramString2.equals(">")) || (paramString2.equals(">>"))) {
          bool = ((Comparable)paramObject).compareTo(localObject) > 0;
        } else if ((paramString2.equals("=<")) || (paramString2.equals("<="))) {
          bool = ((Comparable)paramObject).compareTo(localObject) <= 0;
        } else if ((paramString2.equals("=>")) || (paramString2.equals(">="))) {
          bool = ((Comparable)paramObject).compareTo(localObject) >= 0;
        }
      }
    }
    else
    {
      bool = isFilterable(paramString1, paramString2, paramObject.toString());
    }
    return bool;
  }
  
  public static int numStringCompare(String paramString1, String paramString2)
  {
    int i = 1;
    if (paramString1 == null) {
      paramString1 = "";
    }
    if (paramString2 == null) {
      paramString2 = "";
    }
    try
    {
      long l1 = 1L;
      long l2 = Long.parseLong(paramString1);
      long l3 = Long.parseLong(paramString2);
      l1 = l2 - l3;
      i = l1 < 0L ? -1 : l1 == 0L ? 0 : 1;
    }
    catch (Exception localException)
    {
      i = paramString1.compareTo(paramString2);
    }
    return i;
  }
  
  public static boolean areFieldStringEqual(String paramString1, String paramString2)
  {
    if (paramString1 == paramString2) {
      return true;
    }
    if ((paramString1 == null) || (paramString2 == null)) {
      return false;
    }
    return paramString1.equalsIgnoreCase(paramString2);
  }
  
  public static void appendString(StringBuffer paramStringBuffer, String paramString)
  {
    paramStringBuffer.append('"');
    paramStringBuffer.append(paramString);
    paramStringBuffer.append('"');
  }
  
  public static String generateString(Object paramObject)
  {
    if (paramObject == null) {
      return "";
    }
    return paramObject.toString();
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ExtendABean localExtendABean = (ExtendABean)paramObject;
    String str = null;
    Object localObject1 = null;
    Object localObject2 = null;
    int i = 1;
    int j = paramString.lastIndexOf(".");
    if (j != -1)
    {
      str = paramString.substring(0, j);
      paramString = paramString.substring(j + 1);
    }
    else
    {
      str = paramString;
    }
    localObject1 = getProperty(this, str);
    localObject2 = getProperty(localExtendABean, str);
    if (localObject1 == localObject2) {
      return 0;
    }
    if ((localObject1 != null) && (localObject2 != null))
    {
      if ((localObject1 instanceof Sortable)) {
        i = ((Sortable)localObject1).compare(localObject2, paramString);
      } else if ((localObject1 instanceof String)) {
        i = doGetCollator().compare((String)localObject1, (String)localObject2);
      } else if ((localObject1 instanceof Comparable)) {
        i = ((Comparable)localObject1).compareTo(localObject2);
      }
    }
    else {
      return localObject1 == null ? -1 : 1;
    }
    return i;
  }
  
  public final void setExtendABeanXML(String paramString)
  {
    this.xmlversion = 0;
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new InternalXMLHandler(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void setXML(String paramString)
  {
    setExtendABeanXML(paramString);
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getFormattedXML()
  {
    String str = getXML();
    return XMLHandler.format(str);
  }
  
  public final String getExtendABeanXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (size() > 0)
    {
      XMLHandler.openTag(localStringBuffer, "EXTENDEDDATA");
      XMLHandler.appendAttribute(localStringBuffer, "VERSION", "2");
      XMLHandler.closeTag(localStringBuffer, false);
      Iterator localIterator = entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        a(localStringBuffer, (String)localEntry.getKey(), localEntry.getValue(), true);
      }
      XMLHandler.appendEndTag(localStringBuffer, "EXTENDEDDATA");
    }
    return localStringBuffer.toString();
  }
  
  public String getXML()
  {
    return getExtendABeanXML();
  }
  
  private void a(StringBuffer paramStringBuffer, String paramString, Object paramObject, boolean paramBoolean)
  {
    if ((paramStringBuffer != null) && (paramString != null) && (paramObject != null))
    {
      if (paramBoolean)
      {
        XMLHandler.openTag(paramStringBuffer, "ELEMENT");
        XMLHandler.closeTag(paramStringBuffer, false);
        paramStringBuffer.append("<KEY><![CDATA[" + paramString + "]]></" + "KEY" + ">\n");
        XMLHandler.openTag(paramStringBuffer, "VALUE");
        if (!(paramObject instanceof String)) {
          XMLHandler.appendAttribute(paramStringBuffer, "class", paramObject.getClass().getName());
        }
        paramStringBuffer.append(">");
      }
      else
      {
        XMLHandler.openTag(paramStringBuffer, paramString);
        if (!(paramObject instanceof String)) {
          XMLHandler.appendAttribute(paramStringBuffer, "class", paramObject.getClass().getName());
        }
        paramStringBuffer.append(">");
      }
      if ((paramObject instanceof XMLable))
      {
        paramStringBuffer.append("\n");
        paramStringBuffer.append(((XMLable)paramObject).getXML());
      }
      else
      {
        Object localObject1;
        if ((paramObject instanceof List))
        {
          paramStringBuffer.append("\n");
          localObject1 = ((List)paramObject).toArray();
          for (int i = 0; i < localObject1.length; i++) {
            a(paramStringBuffer, Integer.toString(i), localObject1[i], false);
          }
        }
        else if ((paramObject instanceof Map))
        {
          paramStringBuffer.append("\n");
          localObject1 = ((Map)paramObject).keySet().iterator();
          String str = null;
          Object localObject2 = null;
          while (((Iterator)localObject1).hasNext())
          {
            str = (String)((Iterator)localObject1).next();
            localObject2 = ((Map)paramObject).get(str);
            a(paramStringBuffer, str, localObject2, true);
          }
        }
        else if ((paramObject instanceof String))
        {
          if (((String)paramObject).length() > 0) {
            paramStringBuffer.append("<![CDATA[" + paramObject.toString() + "]]>");
          }
        }
        else if (paramObject.getClass().getName().equals("com.ffusion.ffs.bpw.interfaces.ValueInfo"))
        {
          paramStringBuffer.append("<![CDATA[" + paramObject.toString() + "]]>");
        }
        else
        {
          paramStringBuffer.append(paramObject.toString());
        }
      }
      if (paramBoolean)
      {
        XMLHandler.appendEndTag(paramStringBuffer, "VALUE");
        XMLHandler.appendEndTag(paramStringBuffer, "ELEMENT");
      }
      else
      {
        XMLHandler.appendEndTag(paramStringBuffer, paramString);
      }
    }
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    if ((this instanceof ExtendABean)) {
      paramXMLHandler.continueWith(new InternalXMLHandler());
    } else {
      paramXMLHandler.continueWith(new ExtendedDataXMLHandler());
    }
  }
  
  protected Object createObject(String paramString1, String paramString2)
  {
    Object localObject = null;
    if (paramString1 != null) {
      try
      {
        Class localClass = Class.forName(paramString1);
        if (paramString2 != null)
        {
          Class[] arrayOfClass = new Class[1];
          arrayOfClass[0] = Class.forName("java.lang.String");
          Constructor localConstructor = localClass.getConstructor(arrayOfClass);
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = paramString2;
          localObject = localConstructor.newInstance(arrayOfObject);
        }
        else
        {
          localObject = localClass.newInstance();
        }
        if ((localObject != null) && ((localObject instanceof Localeable))) {
          ((Localeable)localObject).setLocale(this.locale);
        }
      }
      catch (Exception localException) {}
    }
    return localObject;
  }
  
  protected Object createXMLObject(XMLHandler paramXMLHandler, String paramString1, String paramString2)
  {
    Object localObject = null;
    if (paramString1 != null)
    {
      localObject = createObject(paramString1, paramString2);
      if (localObject != null)
      {
        if ((localObject instanceof Localeable)) {
          ((Localeable)localObject).setLocale(this.locale);
        }
        if ((localObject instanceof XMLable)) {
          ((XMLable)localObject).continueXMLParsing(paramXMLHandler);
        } else if ((localObject instanceof List)) {
          paramXMLHandler.continueWith(new ListXMLHandler((List)localObject));
        } else if ((localObject instanceof Map))
        {
          if (this.xmlversion == 2) {
            paramXMLHandler.continueWith(new Map2XMLHandler((Map)localObject));
          } else {
            paramXMLHandler.continueWith(new MapXMLHandler((Map)localObject));
          }
        }
        else if (localObject.getClass().getName().equals("com.ffusion.ffs.bpw.interfaces.ValueInfo")) {
          paramXMLHandler.continueWith(new a(localObject));
        }
      }
      else
      {
        localObject = "Could not create object:" + paramString1;
      }
    }
    return localObject;
  }
  
  public static int compareStrings(String paramString1, String paramString2)
  {
    return compareStrings(paramString1, paramString2, null);
  }
  
  public static int compareStrings(String paramString1, String paramString2, Collator paramCollator)
  {
    if (paramString1 == paramString2) {
      return 0;
    }
    if (paramString1 == null) {
      return -1;
    }
    if (paramString2 == null) {
      return 1;
    }
    if (paramCollator == null) {
      return paramString1.compareTo(paramString2);
    }
    return paramCollator.compare(paramString1, paramString2);
  }
  
  public static int compareStringsIgnoreCase(String paramString1, String paramString2)
  {
    if ((paramString1 == null) && (paramString2 == null)) {
      return 0;
    }
    if (paramString1 == null) {
      return -1;
    }
    if (paramString2 == null) {
      return 1;
    }
    return paramString1.compareToIgnoreCase(paramString2);
  }
  
  public static int compareBoolean(String paramString1, String paramString2)
  {
    if ((paramString1 == null) && (paramString2 == null)) {
      return 0;
    }
    if (paramString1 == null) {
      return -1;
    }
    if (paramString2 == null) {
      return 1;
    }
    return paramString1.compareTo(paramString2);
  }
  
  class Map2XMLHandler
    extends ExtendABean.MapXMLHandler
  {
    protected static final int ELEMENT_TAG = 0;
    protected static final int KEY_TAG = 1;
    protected static final int VALUE_TAG = 2;
    protected static final int KEY_DATA = 3;
    protected static final int VALUE_DATA = 4;
    protected int expectedEvent = 0;
    protected String key;
    
    public Map2XMLHandler(Map paramMap)
    {
      super(paramMap);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if ((this.expectedEvent == 0) && (paramString.equals("ELEMENT")))
      {
        this.expectedEvent = 1;
      }
      else if ((this.expectedEvent == 1) && (paramString.equals("KEY")))
      {
        this.expectedEvent = 3;
      }
      else if ((this.expectedEvent == 2) && (paramString.equals("VALUE")))
      {
        if (this.jdField_byte != null)
        {
          super.startElement(this.key);
          if (this.jdField_try == null) {
            this.expectedEvent = 0;
          } else {
            this.expectedEvent = 4;
          }
        }
        else
        {
          this.expectedEvent = 4;
        }
      }
      else
      {
        String str = "Expecting " + ExtendABean.byte[this.expectedEvent] + " recieved <" + paramString + ">";
        throw new Exception(str);
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {
      if (paramString.equals("VALUE")) {
        this.expectedEvent = 0;
      }
      super.endElement(paramString);
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
      throws Exception
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      if (str.length() > 0) {
        if (this.expectedEvent == 3)
        {
          this.key = str;
          this.expectedEvent = 2;
        }
        else
        {
          Object localObject;
          if (this.expectedEvent == 4)
          {
            if ((this.jdField_case != null) && (this.jdField_case.equals(this.key)))
            {
              localObject = ExtendABean.this.createXMLObject(getHandler(), this.jdField_try, str);
              if (localObject != null) {
                handleCreate(this.key, localObject);
              }
              this.jdField_case = null;
            }
            else
            {
              handleCreate(this.key, str);
            }
            this.expectedEvent = 0;
          }
          else
          {
            localObject = "Expecting " + ExtendABean.byte[this.expectedEvent] + " recieved char data \"" + str + "\"";
            throw new Exception((String)localObject);
          }
        }
      }
    }
  }
  
  class MapXMLHandler
    extends ExtendABean.ExtendedDataXMLHandler
  {
    Map jdField_else = null;
    
    public MapXMLHandler(Map paramMap)
    {
      super();
      this.jdField_else = paramMap;
    }
    
    public void handleCreate(String paramString, Object paramObject)
    {
      this.jdField_else.put(paramString, paramObject);
    }
    
    public void handleSet(String paramString1, String paramString2)
    {
      this.jdField_else.put(paramString1, paramString2);
    }
  }
  
  class ListXMLHandler
    extends ExtendABean.ExtendedDataXMLHandler
  {
    List jdField_char = null;
    
    public ListXMLHandler(List paramList)
    {
      super();
      this.jdField_char = paramList;
    }
    
    public void handleCreate(String paramString, Object paramObject)
    {
      ExtendABean.updateListObject(this.jdField_char, paramString, paramObject);
    }
    
    public void handleSet(String paramString1, String paramString2)
    {
      ExtendABean.updateListObject(this.jdField_char, paramString1, paramString2);
    }
  }
  
  class a
    extends XMLHandler
  {
    Object jdField_void = null;
    protected static final int jdField_null = 0;
    protected static final int jdField_goto = 1;
    protected int jdField_long = 0;
    
    public a(Object paramObject)
    {
      this.jdField_void = paramObject;
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
      throws Exception
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      if ((str.length() > 0) && (this.jdField_long == 0)) {
        try
        {
          Class[] arrayOfClass = { new Object().getClass() };
          Method localMethod = this.jdField_void.getClass().getMethod("setValue", arrayOfClass);
          localMethod.invoke(this.jdField_void, new Object[] { str });
        }
        catch (Exception localException)
        {
          DebugLog.log(Level.FINE, "Failed to setValue on ValueInfo object when parsing XML");
        }
      }
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      this.jdField_long = 1;
    }
  }
  
  class ExtendedDataXMLHandler
    extends XMLHandler
  {
    String jdField_byte = null;
    String jdField_try = null;
    String jdField_case = null;
    
    ExtendedDataXMLHandler() {}
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
      throws Exception
    {
      if (paramString1.equals("class")) {
        this.jdField_byte = paramString2;
      }
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      this.jdField_case = paramString;
      this.jdField_try = null;
      if (this.jdField_byte != null)
      {
        Object localObject = ExtendABean.this.createXMLObject(getHandler(), this.jdField_byte, null);
        if (localObject != null) {
          handleCreate(paramString, localObject);
        }
        if (!this.jdField_byte.equals(localObject.getClass().getName())) {
          this.jdField_try = this.jdField_byte;
        }
      }
      else
      {
        this.jdField_case = null;
      }
      this.jdField_byte = null;
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
      throws Exception
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      if (str.length() > 0) {
        if ((this.jdField_case != null) && (this.jdField_case.equals(getElement())))
        {
          Object localObject = ExtendABean.this.createXMLObject(getHandler(), this.jdField_try, str);
          if (localObject != null) {
            handleCreate(getElement(), localObject);
          }
          this.jdField_case = null;
        }
        else
        {
          handleSet(getElement(), str);
        }
      }
    }
    
    public void handleCreate(String paramString, Object paramObject)
    {
      ExtendABean.this.put(paramString, paramObject);
    }
    
    public void handleSet(String paramString1, String paramString2)
    {
      ExtendABean.this.set(paramString1, paramString2);
    }
  }
  
  public class InternalXMLHandler
    extends XMLHandler
  {
    public InternalXMLHandler() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("EXTENDEDDATA")) {
        if (ExtendABean.this.xmlversion == 2) {
          getHandler().continueWith(new ExtendABean.Map2XMLHandler(ExtendABean.this, ExtendABean.this.map));
        } else {
          getHandler().continueWith(new ExtendABean.ExtendedDataXMLHandler(ExtendABean.this));
        }
      }
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
      throws Exception
    {
      if (paramString1.equals("VERSION")) {
        ExtendABean.this.xmlversion = Integer.parseInt(paramString2);
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      if (str.length() > 0) {
        ExtendABean.this.set(getElement(), str);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.ExtendABean
 * JD-Core Version:    0.7.0.1
 */