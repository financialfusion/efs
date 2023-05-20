package com.ffusion.tasks.util;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.csil.core.BusinessAdmin;
import com.ffusion.util.XMLTag;
import java.util.HashMap;

public class BAIExportSettingsXMLUtil
{
  public static final String BAI_SETTINGS_VERSION_1 = "1.0";
  public static final String CURRENT_BAI_SETTINGS_VERSION = "1.0";
  public static final String BAI_EXPORT_NAMESPACE = "BAI_EXPORT_SETTINGS";
  public static final String BAI_EXPORT_SETTINGS_VERSION_ATTRIBUTE_NAME = "VERSION";
  public static final String SENDER_IDENTIFICATION_TAG = "SENDER_IDENTIFICATION";
  public static final String SENDER_IDENTIFICATION_OPTION_BANK_ROUTING_NUMBER = "1";
  public static final String SENDER_IDENTIFICATION_OPTION_CUSTOM_VALUE = "2";
  public static final String RECEIVER_IDENTIFICATION_TAG = "RECEIVER_IDENTIFICATION";
  public static final String RECEIVER_IDENTIFICATION_OPTION_BANK_ROUTING_NUMBER = "1";
  public static final String RECEIVER_IDENTIFICATION_OPTION_CUSTOM_VALUE = "2";
  public static final String ULTIMATE_RECEIVER_IDENTIFICATION_TAG = "ULTIMATE_RECEIVER_IDENTIFICATION";
  public static final String ULTIMATE_RECEIVER_IDENTIFICATION_OPTION_BANK_ROUTING_NUMBER = "1";
  public static final String ULTIMATE_RECEIVER_IDENTIFICATION_OPTION_CUSTOM_VALUE = "2";
  public static final String ORIGINATOR_IDENTIFICATION_TAG = "ORIGINATOR_IDENTIFICATION";
  public static final String ORIGINATOR_IDENTIFICATION_OPTION_BANK_ROUTING_NUMBER = "1";
  public static final String ORIGINATOR_IDENTIFICATION_OPTION_CUSTOM_VALUE = "2";
  public static final String CUSTOMER_ACCOUNT_NUMBER_TAG = "CUSTOMER_ACCOUNT_NUMBER";
  public static final String CUSTOMER_ACCOUNT_NUMBER_OPTION_ACCOUNT_NUMBER = "1";
  public static final String CUSTOMER_ACCOUNT_NUMBER_OPTION_ACCOUNT_AND_ROUTING_NUMBER = "2";
  public static final String CUSTOM_VALUE_TAG = "CUSTOM_VALUE";
  public static final String OPTION_TYPE_ATTRIBUTE_NAME = "TYPE";
  
  public static HashMap getBAIExportSettings(SecureUser paramSecureUser, Business paramBusiness)
    throws Exception
  {
    String str1 = BusinessAdmin.getAdditionalData(paramSecureUser, paramBusiness, "BAI_EXPORT_SETTINGS", new HashMap());
    HashMap localHashMap = new HashMap();
    if ((str1 != null) && (str1.length() != 0))
    {
      XMLTag localXMLTag1 = new XMLTag();
      localXMLTag1.build(str1);
      if (!localXMLTag1.getTagName().equals("BAI_EXPORT_SETTINGS")) {
        throw new Exception("The 'BAI_EXPORT_SETTINGS' tag is not the opening tag of the XML.");
      }
      String str2 = (String)localXMLTag1.getAttribute("VERSION");
      if (str2 == null) {
        throw new Exception("Settings XML does not contain an XML version number.");
      }
      if (str2.equals("1.0"))
      {
        XMLTag localXMLTag2 = localXMLTag1.getContainedTag("SENDER_IDENTIFICATION");
        if (localXMLTag2 == null) {
          throw new Exception("The sender identification tag does not exist in the XML.");
        }
        String str3 = (String)localXMLTag2.getAttribute("TYPE");
        if (str3 == null) {
          throw new Exception("The 'type' attribute was not found on the sender identification tag.");
        }
        if (str3.equals("1"))
        {
          localHashMap.put("SENDER_ID_TYPE", "1");
        }
        else if (str3.equals("2"))
        {
          localHashMap.put("SENDER_ID_TYPE", "2");
          localXMLTag3 = localXMLTag2.getContainedTag("CUSTOM_VALUE");
          if (localXMLTag3 == null) {
            throw new Exception("The custom data tag was not found within the sender identification tag.");
          }
          str4 = localXMLTag3.getTagContent();
          localHashMap.put("SENDER_ID_CUSTOM_DATA", str4);
        }
        else
        {
          throw new Exception("The sender identification type code was invalid.");
        }
        XMLTag localXMLTag3 = localXMLTag1.getContainedTag("RECEIVER_IDENTIFICATION");
        if (localXMLTag3 == null) {
          throw new Exception("The receiver identification tag does not exist in the XML.");
        }
        String str4 = (String)localXMLTag3.getAttribute("TYPE");
        if (str4 == null) {
          throw new Exception("The 'type' attribute was not found on the receiver identification tag.");
        }
        if (str4.equals("1"))
        {
          localHashMap.put("RECEIVER_ID_TYPE", "1");
        }
        else if (str4.equals("2"))
        {
          localHashMap.put("RECEIVER_ID_TYPE", "2");
          localXMLTag4 = localXMLTag3.getContainedTag("CUSTOM_VALUE");
          if (localXMLTag4 == null) {
            throw new Exception("The custom data tag was not found within the receiver identification tag.");
          }
          str5 = localXMLTag4.getTagContent();
          localHashMap.put("RECEIVER_ID_CUSTOM_DATA", str5);
        }
        else
        {
          throw new Exception("The receiver identification type code was invalid.");
        }
        XMLTag localXMLTag4 = localXMLTag1.getContainedTag("ULTIMATE_RECEIVER_IDENTIFICATION");
        if (localXMLTag4 == null) {
          throw new Exception("The ultimate receiver identification tag does not exist in the XML.");
        }
        String str5 = (String)localXMLTag4.getAttribute("TYPE");
        if (str5 == null) {
          throw new Exception("The 'type' attribute was not found on the ultimate receiver identification tag.");
        }
        if (str5.equals("1"))
        {
          localHashMap.put("ULTIMATE_RECEIVER_ID_TYPE", "1");
        }
        else if (str5.equals("2"))
        {
          localHashMap.put("ULTIMATE_RECEIVER_ID_TYPE", "2");
          localXMLTag5 = localXMLTag4.getContainedTag("CUSTOM_VALUE");
          if (localXMLTag5 == null) {
            throw new Exception("The custom data tag was not found within the ultimate receiver identification tag.");
          }
          str6 = localXMLTag5.getTagContent();
          localHashMap.put("ULTIMATE_RECEIVER_ID_CUSTOM_DATA", str6);
        }
        else
        {
          throw new Exception("The ultimate receiver identification type code was invalid.");
        }
        XMLTag localXMLTag5 = localXMLTag1.getContainedTag("ORIGINATOR_IDENTIFICATION");
        if (localXMLTag5 == null) {
          throw new Exception("The originator identification tag does not exist in the XML.");
        }
        String str6 = (String)localXMLTag5.getAttribute("TYPE");
        if (str6 == null) {
          throw new Exception("The 'type' attribute was not found on the originator identification tag.");
        }
        if (str6.equals("1"))
        {
          localHashMap.put("ORIGINATOR_ID_TYPE", "1");
        }
        else if (str6.equals("2"))
        {
          localHashMap.put("ORIGINATOR_ID_TYPE", "2");
          localXMLTag6 = localXMLTag5.getContainedTag("CUSTOM_VALUE");
          if (localXMLTag6 == null) {
            throw new Exception("The custom data tag was not found within the originator identification tag.");
          }
          str7 = localXMLTag6.getTagContent();
          localHashMap.put("ORIGINATOR_ID_CUSTOM_DATA", str7);
        }
        else
        {
          throw new Exception("The originator identification type code was invalid.");
        }
        XMLTag localXMLTag6 = localXMLTag1.getContainedTag("CUSTOMER_ACCOUNT_NUMBER");
        if (localXMLTag6 == null) {
          throw new Exception("The customer account number tag does not exist in the XML.");
        }
        String str7 = (String)localXMLTag6.getAttribute("TYPE");
        if (str5 == null) {
          throw new Exception("The 'type' attribute was not found on the customer account number tag.");
        }
        if (str7.equals("1")) {
          localHashMap.put("CUSTOMER_ACCOUNT_OPTION", "1");
        } else if (str7.equals("2")) {
          localHashMap.put("CUSTOMER_ACCOUNT_OPTION", "2");
        } else {
          throw new Exception("The customer account number type specified was not recognized.");
        }
      }
      else
      {
        throw new Exception("The version number specified in the BAI settings XML is unknown.");
      }
    }
    else
    {
      localHashMap.put("SENDER_ID_TYPE", "1");
      localHashMap.put("RECEIVER_ID_TYPE", "1");
      localHashMap.put("ULTIMATE_RECEIVER_ID_TYPE", "1");
      localHashMap.put("ORIGINATOR_ID_TYPE", "1");
      localHashMap.put("CUSTOMER_ACCOUNT_OPTION", "1");
    }
    return localHashMap;
  }
  
  public static void setBAIExportSettings(SecureUser paramSecureUser, Business paramBusiness, HashMap paramHashMap)
    throws Exception
  {
    if (paramHashMap == null) {
      throw new Exception("The settings hashmap to be outputed is null.");
    }
    XMLTag localXMLTag1 = new XMLTag();
    localXMLTag1.setTagName("BAI_EXPORT_SETTINGS");
    localXMLTag1.setAttribute("VERSION", "1.0");
    XMLTag localXMLTag2 = new XMLTag();
    localXMLTag2.setTagName("SENDER_IDENTIFICATION");
    String str1 = (String)paramHashMap.get("SENDER_ID_TYPE");
    if (str1 == null) {
      str1 = "1";
    } else if (str1.equals("1")) {
      str1 = "1";
    } else if (str1.equals("2")) {
      str1 = "2";
    } else {
      throw new Exception("The specified sender identification type is invalid.");
    }
    localXMLTag2.setAttribute("TYPE", str1);
    if (str1.equals("2"))
    {
      localObject1 = (String)paramHashMap.get("SENDER_ID_CUSTOM_DATA");
      if (localObject1 == null) {
        throw new Exception("A custom sender identification was specified, but no custom value was provided.");
      }
      localObject2 = new XMLTag();
      ((XMLTag)localObject2).setTagName("CUSTOM_VALUE");
      ((XMLTag)localObject2).setTagContent((String)localObject1);
      localXMLTag2.setContainedTag((XMLTag)localObject2);
    }
    localXMLTag1.setContainedTag(localXMLTag2);
    Object localObject1 = new XMLTag();
    ((XMLTag)localObject1).setTagName("RECEIVER_IDENTIFICATION");
    Object localObject2 = (String)paramHashMap.get("RECEIVER_ID_TYPE");
    if (localObject2 == null) {
      localObject2 = "1";
    } else if (((String)localObject2).equals("1")) {
      localObject2 = "1";
    } else if (((String)localObject2).equals("2")) {
      localObject2 = "2";
    } else {
      throw new Exception("The specified receiver identification type is invalid.");
    }
    ((XMLTag)localObject1).setAttribute("TYPE", (String)localObject2);
    if (((String)localObject2).equals("2"))
    {
      localObject3 = (String)paramHashMap.get("RECEIVER_ID_CUSTOM_DATA");
      if (localObject3 == null) {
        throw new Exception("A custom receiver identification was specified, but no custom value was provided.");
      }
      localObject4 = new XMLTag();
      ((XMLTag)localObject4).setTagName("CUSTOM_VALUE");
      ((XMLTag)localObject4).setTagContent((String)localObject3);
      ((XMLTag)localObject1).setContainedTag((XMLTag)localObject4);
    }
    localXMLTag1.setContainedTag((XMLTag)localObject1);
    Object localObject3 = new XMLTag();
    ((XMLTag)localObject3).setTagName("ULTIMATE_RECEIVER_IDENTIFICATION");
    Object localObject4 = (String)paramHashMap.get("ULTIMATE_RECEIVER_ID_TYPE");
    if (localObject4 == null) {
      localObject4 = "1";
    } else if (((String)localObject4).equals("1")) {
      localObject4 = "1";
    } else if (((String)localObject4).equals("2")) {
      localObject4 = "2";
    } else {
      throw new Exception("The specified ultimate receiver identification type is invalid.");
    }
    ((XMLTag)localObject3).setAttribute("TYPE", (String)localObject4);
    if (((String)localObject4).equals("2"))
    {
      localObject5 = (String)paramHashMap.get("ULTIMATE_RECEIVER_ID_CUSTOM_DATA");
      if (localObject5 == null) {
        throw new Exception("A custom ultimate receiver identification was specified, but no custom value was provided.");
      }
      localObject6 = new XMLTag();
      ((XMLTag)localObject6).setTagName("CUSTOM_VALUE");
      ((XMLTag)localObject6).setTagContent((String)localObject5);
      ((XMLTag)localObject3).setContainedTag((XMLTag)localObject6);
    }
    localXMLTag1.setContainedTag((XMLTag)localObject3);
    Object localObject5 = new XMLTag();
    ((XMLTag)localObject5).setTagName("ORIGINATOR_IDENTIFICATION");
    Object localObject6 = (String)paramHashMap.get("ORIGINATOR_ID_TYPE");
    if (localObject6 == null) {
      localObject6 = "1";
    } else if (((String)localObject6).equals("1")) {
      localObject6 = "1";
    } else if (((String)localObject6).equals("2")) {
      localObject6 = "2";
    } else {
      throw new Exception("The specified originator identification type is invalid.");
    }
    ((XMLTag)localObject5).setAttribute("TYPE", (String)localObject6);
    if (((String)localObject6).equals("2"))
    {
      localObject7 = (String)paramHashMap.get("ORIGINATOR_ID_CUSTOM_DATA");
      if (localObject7 == null) {
        throw new Exception("A custom originator identification was specified, but no custom value was provided.");
      }
      localObject8 = new XMLTag();
      ((XMLTag)localObject8).setTagName("CUSTOM_VALUE");
      ((XMLTag)localObject8).setTagContent((String)localObject7);
      ((XMLTag)localObject5).setContainedTag((XMLTag)localObject8);
    }
    localXMLTag1.setContainedTag((XMLTag)localObject5);
    Object localObject7 = new XMLTag();
    ((XMLTag)localObject7).setTagName("CUSTOMER_ACCOUNT_NUMBER");
    Object localObject8 = (String)paramHashMap.get("CUSTOMER_ACCOUNT_OPTION");
    if (localObject8 == null) {
      localObject8 = "1";
    } else if (((String)localObject8).equals("1")) {
      localObject8 = "1";
    } else if (((String)localObject8).equals("2")) {
      localObject8 = "2";
    } else {
      throw new Exception("The customer account number option provided is not recognized.");
    }
    ((XMLTag)localObject7).setAttribute("TYPE", (String)localObject8);
    localXMLTag1.setContainedTag((XMLTag)localObject7);
    String str2 = XMLTag.toXML(localXMLTag1, true);
    BusinessAdmin.addAdditionalData(paramSecureUser, paramBusiness, "BAI_EXPORT_SETTINGS", str2, new HashMap());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.BAIExportSettingsXMLUtil
 * JD-Core Version:    0.7.0.1
 */