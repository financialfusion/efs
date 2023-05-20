package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.user.CustomTag;
import com.ffusion.beans.user.CustomTags;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Locale;

public class IMSAdapter
  implements ProfileDefines
{
  private static final String afh = "tag_name";
  private static final String VALUE = "value";
  private static final String afj = "active_date";
  private static final String afi = "expire_date";
  private static final String afl = "insert into custom_ims_tag (directory_id,tag_name,value,active_date,expire_date) values(?,?,?,?,?)";
  private static final String afk = "select * from custom_ims_tag where directory_id=?";
  private static final String aff = "update custom_ims_tag set value=?,active_date=?,expire_date=? where directory_id=? and tag_name=?";
  private static final String afg = "delete from custom_ims_tag where directory_id=?";
  
  public static boolean addImsTag(int paramInt, CustomTags paramCustomTags)
    throws ProfileException
  {
    String str1 = "IMSAdapter.addImsTag";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    if (!Profile.isValidId(paramInt)) {
      throw new ProfileException(str1, 3505, "Directory ID required");
    }
    int i = paramCustomTags.size();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into custom_ims_tag (directory_id,tag_name,value,active_date,expire_date) values(?,?,?,?,?)");
      for (int j = 0; j < i; j++)
      {
        CustomTag localCustomTag = (CustomTag)paramCustomTags.get(j);
        String str2 = localCustomTag.getTagName();
        if (str2.length() == 0) {
          throw new ProfileException(str1, 3, "Invalid Tag Name in addImsTag()");
        }
        localPreparedStatement.setInt(1, paramInt);
        Profile.setStatementString(localPreparedStatement, 2, str2, "tag_name", false);
        Profile.setStatementString(localPreparedStatement, 3, localCustomTag.getValue(), "value", false);
        Profile.setStatementValue(localPreparedStatement, 4, localCustomTag.getActiveDateValue(), "active_date", 93, false);
        Profile.setStatementValue(localPreparedStatement, 5, localCustomTag.getExpireDateValue(), "expire_date", 93, false);
        DBUtil.executeUpdate(localPreparedStatement, "insert into custom_ims_tag (directory_id,tag_name,value,active_date,expire_date) values(?,?,?,?,?)");
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
    return true;
  }
  
  public static CustomTags getImsTag(int paramInt, CustomTag paramCustomTag)
    throws ProfileException
  {
    String str1 = "IMSAdapter.getImsTag";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    if (!Profile.isValidId(paramInt)) {
      throw new ProfileException(str1, 3505, "Directory ID required");
    }
    CustomTags localCustomTags = new CustomTags(Locale.getDefault());
    StringBuffer localStringBuffer = new StringBuffer("select * from custom_ims_tag where directory_id=?");
    String str2 = null;
    if (paramCustomTag != null) {
      str2 = paramCustomTag.getTagName();
    }
    Profile.appendSQLSelectString(localStringBuffer, "tag_name", str2, true);
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, paramInt);
      Profile.setStatementString(localPreparedStatement, 2, str2, "tag_name", true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        CustomTag localCustomTag = localCustomTags.add();
        localCustomTag.setTagName(Profile.getRSString(localResultSet, "tag_name"));
        localCustomTag.setValue(Profile.getRSString(localResultSet, "value"));
        Timestamp localTimestamp = localResultSet.getTimestamp("active_date");
        if (localTimestamp != null) {
          localCustomTag.setActiveDate(new DateTime(localTimestamp, Locale.getDefault()));
        }
        localTimestamp = localResultSet.getTimestamp("expire_date");
        if (localTimestamp != null) {
          localCustomTag.setExpireDate(new DateTime(localTimestamp, Locale.getDefault()));
        }
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localCustomTags;
  }
  
  public static boolean modifyImsTag(int paramInt, CustomTag paramCustomTag)
    throws ProfileException
  {
    String str1 = "IMSAdapter.modifyImsTag";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    if (!Profile.isValidId(paramInt)) {
      throw new ProfileException(str1, 3505, "Directory ID required");
    }
    String str2 = paramCustomTag.getTagName();
    if ((str2 == null) || (str2.length() == 0)) {
      throw new ProfileException(str1, 3, "Tag Name required");
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update custom_ims_tag set value=?,active_date=?,expire_date=? where directory_id=? and tag_name=?");
      Profile.setStatementString(localPreparedStatement, 1, paramCustomTag.getValue(), "value", false);
      Profile.setStatementValue(localPreparedStatement, 2, paramCustomTag.getActiveDateValue(), "active_date", 93, false);
      Profile.setStatementValue(localPreparedStatement, 3, paramCustomTag.getExpireDateValue(), "expire_date", 93, false);
      localPreparedStatement.setInt(4, paramInt);
      Profile.setStatementString(localPreparedStatement, 5, str2, "tag_name", false);
      DBUtil.executeUpdate(localPreparedStatement, "update custom_ims_tag set value=?,active_date=?,expire_date=? where directory_id=? and tag_name=?");
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
    return true;
  }
  
  public static boolean deleteImsTag(int paramInt, CustomTag paramCustomTag)
    throws ProfileException
  {
    String str = "IMSAdapter.deleteImsTag";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    if (!Profile.isValidId(paramInt)) {
      throw new ProfileException(str, 3505, "Directory ID required");
    }
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("delete from custom_ims_tag where directory_id=?");
      Profile.appendSQLSelectString(localStringBuffer, "tag_name", paramCustomTag.getTagName(), true);
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, paramInt);
      Profile.setStatementString(localPreparedStatement, 2, paramCustomTag.getTagName(), "tag_name", true);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.IMSAdapter
 * JD-Core Version:    0.7.0.1
 */