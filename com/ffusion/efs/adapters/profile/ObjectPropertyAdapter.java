package com.ffusion.efs.adapters.profile;

import com.ffusion.util.Strings;
import com.ffusion.util.db.ConnectionHolder;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class ObjectPropertyAdapter
  implements ProfileDefines
{
  public static final String SQL_GET_OBJECT_PROPERTY_VALUES = " Select PROP_NAME, SEQ_NO, PROP_VALUE  From OBJECT_PROPERTIES  Where OBJECT_TYPE = ? AND OBJECT_ID = ?  Order by PROP_NAME, SEQ_NO";
  public static final String SQL_GET_OBJECT_PROPERTY_VALUE = " Select SEQ_NO, PROP_VALUE  From OBJECT_PROPERTIES  Where OBJECT_TYPE = ? AND OBJECT_ID = ? AND PROP_NAME = ?  Order by SEQ_NO ";
  public static final String SQL_ADD_OBJECT_PROPERTY_VALUE = "insert into OBJECT_PROPERTIES(OBJECT_TYPE, OBJECT_ID, PROP_NAME, PROP_VALUE,SEQ_NO ) values(?,?,?,?,?)";
  public static final String SQL_DELETE_OBJECT_PROPERTY_VALUE = "Delete from OBJECT_PROPERTIES  Where OBJECT_TYPE = ? and OBJECT_ID = ? and PROP_NAME = ? ";
  public static final String SQL_GET_OBJECT_PROPERTY_VALUES_LIKE_PROP_NAME_1 = " Select PROP_NAME, SEQ_NO, PROP_VALUE  From OBJECT_PROPERTIES  Where OBJECT_TYPE = ? AND OBJECT_ID = ? AND PROP_NAME LIKE '";
  public static final String SQL_GET_OBJECT_PROPERTY_VALUES_LIKE_PROP_NAME_2 = "%' Order by PROP_NAME, SEQ_NO";
  public static final String SQL_DELETE_OBJECT_PROPERTY_VALUE_LIKE_PROP_NAME_1 = " Delete from OBJECT_PROPERTIES  Where OBJECT_TYPE = ? and OBJECT_ID = ? and PROP_NAME LIKE '";
  public static final String SQL_DELETE_OBJECT_PROPERTY_VALUE_LIKE_PROP_NAME_2 = "%'";
  
  private static HashMap jdMethod_int(ResultSet paramResultSet)
    throws Exception
  {
    int i = 0;
    String str1 = null;
    String str2 = null;
    int j = 0;
    HashMap localHashMap = new HashMap();
    StringBuffer localStringBuffer = null;
    while (paramResultSet.next())
    {
      i = paramResultSet.getInt("SEQ_NO");
      str1 = paramResultSet.getString("PROP_NAME");
      if (str2 == null)
      {
        str2 = str1;
        localStringBuffer = new StringBuffer(paramResultSet.getString("PROP_VALUE"));
      }
      else if (!str2.equals(str1))
      {
        localHashMap.put(str2, localStringBuffer.toString());
        j = 1;
        str2 = str1;
        localStringBuffer = new StringBuffer(paramResultSet.getString("PROP_VALUE"));
        j = 0;
      }
      else
      {
        localStringBuffer.append(paramResultSet.getString("PROP_VALUE"));
      }
    }
    if ((localStringBuffer != null) && (localStringBuffer.length() > 0) && (j == 0)) {
      localHashMap.put(str1, localStringBuffer.toString());
    }
    return localHashMap;
  }
  
  public static HashMap getObjectPropertyValues(Connection paramConnection, int paramInt, String paramString)
    throws ProfileException
  {
    String str = "ObjectPropertyAdapter.getObjectPropertyValues";
    Profile.isInitialized();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    HashMap localHashMap = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer(" Select PROP_NAME, SEQ_NO, PROP_VALUE  From OBJECT_PROPERTIES  Where OBJECT_TYPE = ? AND OBJECT_ID = ?  Order by PROP_NAME, SEQ_NO");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, paramInt);
      localPreparedStatement.setString(2, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      localHashMap = jdMethod_int(localResultSet);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localHashMap;
  }
  
  public static HashMap getObjectPropertyValues(int paramInt, String paramString)
    throws ProfileException
  {
    String str = "ObjectPropertyAdapter.getObjectPropertyValues";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    HashMap localHashMap = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localHashMap = getObjectPropertyValues(localConnection, paramInt, paramString);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
    return localHashMap;
  }
  
  protected static String getObjectPropertyValue(Connection paramConnection, PreparedStatement paramPreparedStatement, int paramInt, String paramString1, String paramString2)
    throws ProfileException
  {
    String str1 = "ObjectPropertyAdapter.getObjectPropertyValue";
    Profile.isInitialized();
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer1 = null;
    String str2 = null;
    try
    {
      paramPreparedStatement.setInt(1, paramInt);
      paramPreparedStatement.setString(2, paramString1);
      paramPreparedStatement.setString(3, paramString2);
      StringBuffer localStringBuffer2 = new StringBuffer(" Select SEQ_NO, PROP_VALUE  From OBJECT_PROPERTIES  Where OBJECT_TYPE = ? AND OBJECT_ID = ? AND PROP_NAME = ?  Order by SEQ_NO ");
      localResultSet = DBUtil.executeQuery(paramPreparedStatement, localStringBuffer2.toString());
      int i = 0;
      while (localResultSet.next())
      {
        i = localResultSet.getInt("SEQ_NO");
        if (localStringBuffer1 == null) {
          localStringBuffer1 = new StringBuffer(localResultSet.getString("PROP_VALUE"));
        } else {
          localStringBuffer1.append(localResultSet.getString("PROP_VALUE"));
        }
      }
      if ((localStringBuffer1 != null) && (localStringBuffer1.length() > 0)) {
        str2 = localStringBuffer1.toString();
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
    return str2;
  }
  
  public static String getObjectPropertyValue(int paramInt, String paramString1, String paramString2)
    throws ProfileException
  {
    String str1 = "ObjectPropertyAdapter.getObjectPropertyValue";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str2 = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      StringBuffer localStringBuffer = new StringBuffer(" Select SEQ_NO, PROP_VALUE  From OBJECT_PROPERTIES  Where OBJECT_TYPE = ? AND OBJECT_ID = ? AND PROP_NAME = ?  Order by SEQ_NO ");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      str2 = getObjectPropertyValue(localConnection, localPreparedStatement, paramInt, paramString1, paramString2);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
    return str2;
  }
  
  public static String getObjectPropertyValue(ConnectionHolder paramConnectionHolder, int paramInt, String paramString1, String paramString2)
    throws ProfileException
  {
    String str1 = "ObjectPropertyAdapter.getObjectPropertyValue";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str2 = null;
    try
    {
      if (paramConnectionHolder.isInitialized())
      {
        localConnection = paramConnectionHolder.getConnection();
        localPreparedStatement = paramConnectionHolder.getPreparedStatement();
      }
      else
      {
        localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
        StringBuffer localStringBuffer = new StringBuffer(" Select SEQ_NO, PROP_VALUE  From OBJECT_PROPERTIES  Where OBJECT_TYPE = ? AND OBJECT_ID = ? AND PROP_NAME = ?  Order by SEQ_NO ");
        localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
        paramConnectionHolder.initialize(Profile.poolName, localConnection, localPreparedStatement);
      }
      str2 = getObjectPropertyValue(localConnection, localPreparedStatement, paramInt, paramString1, paramString2);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    return str2;
  }
  
  public static void addObjectProperty(Connection paramConnection, int paramInt, String paramString1, String paramString2, String paramString3)
    throws ProfileException
  {
    String str1 = "ObjectPropertyAdapter.addObjectProperty";
    Profile.isInitialized();
    PreparedStatement localPreparedStatement = null;
    Object localObject1 = null;
    Object localObject2 = null;
    String str2 = null;
    ResultSet localResultSet = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer(" Select SEQ_NO, PROP_VALUE  From OBJECT_PROPERTIES  Where OBJECT_TYPE = ? AND OBJECT_ID = ? AND PROP_NAME = ?  Order by SEQ_NO ");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      String str3 = getObjectPropertyValue(paramConnection, localPreparedStatement, paramInt, paramString1, paramString2);
      if (str3 != null) {
        throw new ProfileException(str1, 3753, "ObjectType: " + paramInt + "  ObjectId: " + paramString1 + " propName: " + paramString2);
      }
      DBUtil.closeStatement(localPreparedStatement);
      if (paramString3 != null)
      {
        localStringBuffer = new StringBuffer("insert into OBJECT_PROPERTIES(OBJECT_TYPE, OBJECT_ID, PROP_NAME, PROP_VALUE,SEQ_NO ) values(?,?,?,?,?)");
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
        int i = 0;
        int j = 0;
        int k = 0;
        int m = paramString3.length();
        while (k != m)
        {
          k += 1024;
          if (k > m) {
            k = m;
          }
          if (k != m) {
            k = Strings.getLastNonSpaceCharIndex(paramString3, j, k);
          }
          str2 = paramString3.substring(j, k);
          if (str2.length() > 0)
          {
            localPreparedStatement.setInt(1, paramInt);
            localPreparedStatement.setString(2, paramString1);
            localPreparedStatement.setString(3, paramString2);
            localPreparedStatement.setString(4, str2);
            localPreparedStatement.setInt(5, i);
            DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
            i++;
          }
          j = k;
        }
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  public static void addObjectProperty(int paramInt, String paramString1, String paramString2, String paramString3)
    throws ProfileException
  {
    String str = "ObjectPropertyAdapter.addObjectProperty";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      addObjectProperty(localConnection, paramInt, paramString1, paramString2, paramString3);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
  }
  
  public static void modifyObjectProperty(Connection paramConnection, int paramInt, String paramString1, String paramString2, String paramString3)
    throws ProfileException
  {
    String str = "ObjectPropertyAdapter.modifyObjectProperty";
    Profile.isInitialized();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      deleteObjectProperty(paramConnection, paramInt, paramString1, paramString2);
      addObjectProperty(paramConnection, paramInt, paramString1, paramString2, paramString3);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(paramConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  public static void modifyObjectProperty(int paramInt, String paramString1, String paramString2, String paramString3)
    throws ProfileException
  {
    String str = "ObjectPropertyAdapter.modifyObjectProperty";
    PreparedStatement localPreparedStatement = null;
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      modifyObjectProperty(localConnection, paramInt, paramString1, paramString2, paramString3);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
  }
  
  public static void deleteObjectProperty(Connection paramConnection, int paramInt, String paramString1, String paramString2)
    throws ProfileException
  {
    String str = "ObjectPropertyAdapter.deleteObjectProperty";
    Profile.isInitialized();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Object localObject1 = null;
    Object localObject2 = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("Delete from OBJECT_PROPERTIES  Where OBJECT_TYPE = ? and OBJECT_ID = ? and PROP_NAME = ? ");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, paramInt);
      localPreparedStatement.setString(2, paramString1);
      localPreparedStatement.setString(3, paramString2);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  public static void deleteObjectProperty(int paramInt, String paramString1, String paramString2)
    throws ProfileException
  {
    String str = "ObjectPropertyAdapter.deleteObjectProperty";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      deleteObjectProperty(localConnection, paramInt, paramString1, paramString2);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
  }
  
  public static HashMap getObjectPropertyValuesFromRoot(Connection paramConnection, int paramInt, String paramString1, String paramString2)
    throws ProfileException
  {
    String str1 = "ObjectPropertyAdapter.getObjectPropertyValuesFromRoot";
    Profile.isInitialized();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    HashMap localHashMap = null;
    try
    {
      String str2 = " Select PROP_NAME, SEQ_NO, PROP_VALUE  From OBJECT_PROPERTIES  Where OBJECT_TYPE = ? AND OBJECT_ID = ? AND PROP_NAME LIKE '" + paramString2 + "%' Order by PROP_NAME, SEQ_NO";
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, str2);
      localPreparedStatement.setInt(1, paramInt);
      localPreparedStatement.setString(2, paramString1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      localHashMap = jdMethod_int(localResultSet);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localHashMap;
  }
  
  public static HashMap getObjectPropertyValuesFromRoot(int paramInt, String paramString1, String paramString2)
    throws ProfileException
  {
    String str = "ObjectPropertyAdapter.getObjectPropertyValuesFromRoot";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    HashMap localHashMap = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localHashMap = getObjectPropertyValuesFromRoot(localConnection, paramInt, paramString1, paramString2);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
    return localHashMap;
  }
  
  public static void deleteObjectPropertiesFromRoot(Connection paramConnection, int paramInt, String paramString1, String paramString2)
    throws ProfileException
  {
    String str1 = "ObjectPropertyAdapter.deleteObjectPropertiesFromRoot";
    Profile.isInitialized();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      String str2 = " Delete from OBJECT_PROPERTIES  Where OBJECT_TYPE = ? and OBJECT_ID = ? and PROP_NAME LIKE '" + paramString2 + "%'";
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, str2);
      localPreparedStatement.setInt(1, paramInt);
      localPreparedStatement.setString(2, paramString1);
      DBUtil.executeUpdate(localPreparedStatement, str2);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  public static void deleteObjectPropertiesFromRoot(int paramInt, String paramString1, String paramString2)
    throws ProfileException
  {
    String str = "ObjectPropertyAdapter.deleteObjectPropertiesFromRoot";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      deleteObjectPropertiesFromRoot(localConnection, paramInt, paramString1, paramString2);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.ObjectPropertyAdapter
 * JD-Core Version:    0.7.0.1
 */