package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.Contact;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ContactAdapter
  implements ProfileDefines
{
  private static final String aiP = "insert into contact_info(contact_id,street,street2,street3,city,state,postal_code,country,phone,phone2,fax_number,email_address,preferred_lang";
  private static final String aiR = "update contact_info set street=?,street2=?,street3=?,city=?,state=?,postal_code=?,country=?,phone=?,phone2=?,fax_number=?,email_address=?,preferred_lang=?";
  private static final String aiQ = " where contact_id=?";
  private static final String aiS = "delete from contact_info where contact_id=?";
  private static final String aiO = "delete from contact_info where contact_id in (select CONTACT_ID from accounts where directory_id = ?)";
  private static final String aiN = "select c.* from contact_info c where contact_id=?";
  
  public static int addContactTX(Connection paramConnection, Contact paramContact)
    throws Exception
  {
    String str = "ContactAdapter.addContactTX";
    PreparedStatement localPreparedStatement = null;
    int i = 0;
    try
    {
      if (paramContact.getPreferredLanguage() == null) {
        Profile.handleError(str, "The contact has no preferred language", 19);
      }
      i = DBUtil.getNextId(paramConnection, Profile.dbType, "CONTACT_ID");
      StringBuffer localStringBuffer = new StringBuffer("insert into contact_info(contact_id,street,street2,street3,city,state,postal_code,country,phone,phone2,fax_number,email_address,preferred_lang");
      Profile.appendSQLInsertColumns(localStringBuffer, "contact_info", true);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int j = 1;
      j = Profile.setStatementInt(localPreparedStatement, j, i, false);
      j = Profile.setStatementString(localPreparedStatement, j, paramContact.getStreet(), "street", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramContact.getStreet2(), "street2", false);
      j = Profile.setStatementString(localPreparedStatement, j, (String)paramContact.get("STREET3"), "street3", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramContact.getCity(), "city", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramContact.getState(), "state", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramContact.getZipCode(), "postal_code", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramContact.getCountry(), "country", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramContact.getPhone(), "phone", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramContact.getPhone2(), "phone2", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramContact.getFaxPhone(), "fax_number", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramContact.getEmail(), "email_address", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramContact.getPreferredLanguage(), "preferred_lang", false);
      j = Profile.setStatementValues(localPreparedStatement, j, paramContact, "contact_info", false);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
      paramContact.put("CONTACT_ID", Integer.toString(i));
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
    return i;
  }
  
  public static void modifyContactTX(Connection paramConnection, Contact paramContact)
    throws Exception
  {
    String str = "ContactAdapter.modifyContactTX";
    PreparedStatement localPreparedStatement = null;
    StringBuffer localStringBuffer = new StringBuffer("update contact_info set street=?,street2=?,street3=?,city=?,state=?,postal_code=?,country=?,phone=?,phone2=?,fax_number=?,email_address=?,preferred_lang=?");
    try
    {
      if (paramContact.getPreferredLanguage() == null) {
        Profile.handleError(str, "The contact has no preferred language", 19);
      }
      Profile.appendSQLUpdateColumns(localStringBuffer, "contact_info", paramContact);
      localStringBuffer.append(" where contact_id=?");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramContact.getStreet(), "street", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramContact.getStreet2(), "street2", false);
      i = Profile.setStatementString(localPreparedStatement, i, (String)paramContact.get("STREET3"), "street3", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramContact.getCity(), "city", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramContact.getState(), "state", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramContact.getZipCode(), "postal_code", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramContact.getCountry(), "country", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramContact.getPhone(), "phone", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramContact.getPhone2(), "phone2", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramContact.getFaxPhone(), "fax_number", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramContact.getEmail(), "email_address", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramContact.getPreferredLanguage(), "preferred_lang", false);
      i = Profile.setStatementValues(localPreparedStatement, i, paramContact, "contact_info", true);
      i = Profile.setStatementInt(localPreparedStatement, i, Profile.convertToInt((String)paramContact.get("CONTACT_ID")), false);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public static void deleteContactTX(Connection paramConnection, int paramInt)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from contact_info where contact_id=?");
      Profile.setStatementInt(localPreparedStatement, 1, paramInt, false);
      DBUtil.executeUpdate(localPreparedStatement, "delete from contact_info where contact_id=?");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public static void deleteAllAccountContacts(Connection paramConnection, int paramInt)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from contact_info where contact_id in (select CONTACT_ID from accounts where directory_id = ?)");
      Profile.setStatementInt(localPreparedStatement, 1, paramInt, false);
      DBUtil.executeUpdate(localPreparedStatement, "delete from contact_info where contact_id in (select CONTACT_ID from accounts where directory_id = ?)");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public static Contact getContactTX(Connection paramConnection, int paramInt)
    throws Exception
  {
    Contact localContact = new Contact();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select c.* from contact_info c where contact_id=?");
      Profile.setStatementInt(localPreparedStatement, 1, paramInt, false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select c.* from contact_info c where contact_id=?");
      if (localResultSet.next())
      {
        setContactTX(localResultSet, localContact);
        Profile.setXBeanFields(localResultSet, localContact, "contact_info");
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
    return localContact;
  }
  
  public static void setContactTX(ResultSet paramResultSet, Contact paramContact)
    throws Exception
  {
    if (paramResultSet != null)
    {
      paramContact.put("CONTACT_ID", paramResultSet.getString("CONTACT_ID"));
      paramContact.setStreet(Profile.getRSString(paramResultSet, "street"));
      paramContact.setStreet2(Profile.getRSString(paramResultSet, "street2"));
      paramContact.set("STREET3", Profile.getRSString(paramResultSet, "street3"));
      paramContact.setCity(Profile.getRSString(paramResultSet, "city"));
      paramContact.setState(Profile.getRSString(paramResultSet, "state"));
      paramContact.setZipCode(Profile.getRSString(paramResultSet, "postal_code"));
      String str = Profile.getRSString(paramResultSet, "country");
      if (str == null) {
        paramContact.setCountry("USA");
      } else {
        paramContact.setCountry(str);
      }
      paramContact.setPhone(Profile.getRSString(paramResultSet, "phone"));
      paramContact.setPhone2(Profile.getRSString(paramResultSet, "phone2"));
      paramContact.setFaxPhone(Profile.getRSString(paramResultSet, "fax_number"));
      paramContact.setEmail(Profile.getRSString(paramResultSet, "email_address"));
      paramContact.setPreferredLanguage(Profile.getRSString(paramResultSet, "preferred_lang"));
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.ContactAdapter
 * JD-Core Version:    0.7.0.1
 */