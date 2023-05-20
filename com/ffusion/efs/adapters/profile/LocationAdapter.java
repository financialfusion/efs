package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.cashcon.Location;
import com.ffusion.beans.cashcon.LocationSearchCriteria;
import com.ffusion.beans.cashcon.LocationSearchResult;
import com.ffusion.beans.cashcon.LocationSearchResults;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class LocationAdapter
{
  private static final String jdField_for = "insert into location( LOCATION_BPW_ID, DIVISION_ENT_GROUP_ID, LOCATION_NAME, LOCATION_NAME_LOWER, LOCATION_ID, LOCATION_ID_LOWER ) values( ?, ?, ?, ?, ?, ? )";
  private static final String jdField_try = "update location set DIVISION_ENT_GROUP_ID=?, LOCATION_NAME=?, LOCATION_NAME_LOWER=?, LOCATION_ID=?, LOCATION_ID_LOWER=? where LOCATION_BPW_ID=?";
  private static final String jdField_int = "delete from location where LOCATION_BPW_ID=?";
  private static final String b = "l.";
  private static final String jdField_byte = "LOCATION_BPW_ID";
  private static final String jdField_if = "DIVISION_ENT_GROUP_ID";
  private static final String jdField_case = "LOCATION_NAME";
  private static final String jdField_do = "LOCATION_NAME_LOWER";
  private static final String jdField_null = "LOCATION_ID";
  private static final String jdField_long = "LOCATION_ID_LOWER";
  private static final String jdField_char = " l.LOCATION_BPW_ID, l.DIVISION_ENT_GROUP_ID, l.LOCATION_NAME, l.LOCATION_ID ";
  private static final String jdField_goto = "select  l.LOCATION_BPW_ID, l.DIVISION_ENT_GROUP_ID, l.LOCATION_NAME, l.LOCATION_ID , CC_Location.CompId from location l, CC_Location, CC_Company where ";
  private static final String a = "CC_Location.CompId = CC_Company.CompId and l.LOCATION_BPW_ID = CC_Location.LocationId and CC_Location.Status != 'CANCELEDON'";
  private static final String jdField_new = " order by LOCATION_NAME, LOCATION_ID";
  private static final String jdField_else = "select  l.LOCATION_BPW_ID, l.DIVISION_ENT_GROUP_ID, l.LOCATION_NAME, l.LOCATION_ID  from location l where l.DIVISION_ENT_GROUP_ID=? and ( l.LOCATION_NAME_LOWER=? OR l.LOCATION_ID=? ) ";
  private static final String jdField_void = " and l.LOCATION_BPW_ID<>?";
  
  public static LocationSearchResults getLocations(LocationSearchCriteria paramLocationSearchCriteria, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "LocationAdapter.getLocations";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    LocationSearchResults localLocationSearchResults = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("select  l.LOCATION_BPW_ID, l.DIVISION_ENT_GROUP_ID, l.LOCATION_NAME, l.LOCATION_ID , CC_Location.CompId from location l, CC_Location, CC_Company where ");
      boolean bool = false;
      localStringBuffer.append("CC_Location.CompId = CC_Company.CompId and l.LOCATION_BPW_ID = CC_Location.LocationId and CC_Location.Status != 'CANCELEDON'");
      bool = true;
      if (paramLocationSearchCriteria.getDivisionID() > 0) {
        bool = Profile.appendSQLSelectInt(localStringBuffer, "l.", "DIVISION_ENT_GROUP_ID", paramLocationSearchCriteria.getDivisionID(), bool);
      }
      bool = Profile.appendSQLSelectString(localStringBuffer, "l.", "LOCATION_NAME_LOWER", paramLocationSearchCriteria.getLocationNameLowerCase(), true, false, bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "l.", "LOCATION_ID_LOWER", paramLocationSearchCriteria.getLocationIDLowerCase(), true, false, bool);
      localStringBuffer.append(" order by LOCATION_NAME, LOCATION_ID");
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = 1;
      if (paramLocationSearchCriteria.getDivisionID() > 0) {
        i = Profile.setStatementInt(localPreparedStatement, i, paramLocationSearchCriteria.getDivisionID(), true);
      }
      i = Profile.setStatementString(localPreparedStatement, i, paramLocationSearchCriteria.getLocationNameLowerCase(), "LOCATION_NAME_LOWER", true, false, true, true);
      i = Profile.setStatementString(localPreparedStatement, i, paramLocationSearchCriteria.getLocationIDLowerCase(), "LOCATION_ID_LOWER", true, false, true, true);
      if (paramLocationSearchCriteria.getMaxResults() > 0) {
        localPreparedStatement.setMaxRows(paramLocationSearchCriteria.getMaxResults());
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      localLocationSearchResults = new LocationSearchResults();
      while (localResultSet.next())
      {
        LocationSearchResult localLocationSearchResult = localLocationSearchResults.add();
        localLocationSearchResult.setLocationBPWID(localResultSet.getString(1));
        localLocationSearchResult.setDivisionID(localResultSet.getInt(2));
        localLocationSearchResult.setLocationName(localResultSet.getString(3));
        localLocationSearchResult.setLocationID(localResultSet.getString(4));
        localLocationSearchResult.setCompID(localResultSet.getString(5));
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, "LocationAdapter.getLocations");
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localLocationSearchResults;
  }
  
  public static void addLocation(Location paramLocation, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "LocationAdapter.addLocation";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      if ((paramLocation == null) || (paramLocation.getDivisionID() <= 0)) {
        Profile.handleError(new Exception("Invalid division ID when adding Location"), "LocationAdapter.addLocation");
      }
      if ((paramLocation.getLocationNameLowerCase() == null) || (paramLocation.getLocationNameLowerCase().length() == 0)) {
        Profile.handleError(new Exception("Invalid location name when adding Location"), "LocationAdapter.addLocation");
      }
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      if (!a(localConnection, paramLocation.getDivisionID(), paramLocation.getLocationNameLowerCase(), paramLocation.getLocationID(), null)) {
        Profile.handleError(new Exception("Duplicate Location name within division when adding Location"), "LocationAdapter.addLocation");
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into location( LOCATION_BPW_ID, DIVISION_ENT_GROUP_ID, LOCATION_NAME, LOCATION_NAME_LOWER, LOCATION_ID, LOCATION_ID_LOWER ) values( ?, ?, ?, ?, ?, ? )");
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramLocation.getLocationBPWID(), "LOCATION_BPW_ID", true);
      i = Profile.setStatementInt(localPreparedStatement, i, paramLocation.getDivisionID(), true);
      i = Profile.setStatementString(localPreparedStatement, i, paramLocation.getLocationName(), "LOCATION_NAME", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramLocation.getLocationNameLowerCase(), "LOCATION_NAME_LOWER", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramLocation.getLocationID(), "LOCATION_ID", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramLocation.getLocationIDLowerCase(), "LOCATION_ID_LOWER", true);
      DBUtil.executeUpdate(localPreparedStatement, "insert into location( LOCATION_BPW_ID, DIVISION_ENT_GROUP_ID, LOCATION_NAME, LOCATION_NAME_LOWER, LOCATION_ID, LOCATION_ID_LOWER ) values( ?, ?, ?, ?, ?, ? )");
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, "LocationAdapter.addLocation");
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
  }
  
  public static void deleteLocation(String paramString, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "LocationAdapter.deleteLocation";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      if (paramString == null) {
        Profile.handleError(new Exception("Invalid location BPWID when deleting Location"), "LocationAdapter.deleteLocation");
      }
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from location where LOCATION_BPW_ID=?");
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramString, "LOCATION_BPW_ID", true);
      DBUtil.executeUpdate(localPreparedStatement, "delete from location where LOCATION_BPW_ID=?");
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, "LocationAdapter.deleteLocation");
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
  }
  
  public static void modifyLocation(Location paramLocation, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "LocationAdapter.addLocation";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      if ((paramLocation == null) || (paramLocation.getDivisionID() <= 0)) {
        Profile.handleError(new Exception("Invalid division ID when modifying Location"), "LocationAdapter.addLocation");
      }
      if ((paramLocation.getLocationNameLowerCase() == null) || (paramLocation.getLocationNameLowerCase().length() == 0)) {
        Profile.handleError(new Exception("Invalid location name when modifying Location"), "LocationAdapter.addLocation");
      }
      if ((paramLocation.getLocationBPWID() == null) || (paramLocation.getLocationBPWID().length() == 0)) {
        Profile.handleError(new Exception("Invalid location BPWID when modifying Location"), "LocationAdapter.addLocation");
      }
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      if (!a(localConnection, paramLocation.getDivisionID(), paramLocation.getLocationNameLowerCase(), paramLocation.getLocationID(), paramLocation.getLocationBPWID())) {
        Profile.handleError(new Exception("Duplicate location name or location ID within division when modifying Location"), "LocationAdapter.addLocation");
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update location set DIVISION_ENT_GROUP_ID=?, LOCATION_NAME=?, LOCATION_NAME_LOWER=?, LOCATION_ID=?, LOCATION_ID_LOWER=? where LOCATION_BPW_ID=?");
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramLocation.getDivisionID(), true);
      i = Profile.setStatementString(localPreparedStatement, i, paramLocation.getLocationName(), "LOCATION_NAME", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramLocation.getLocationNameLowerCase(), "LOCATION_NAME_LOWER", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramLocation.getLocationID(), "LOCATION_ID", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramLocation.getLocationIDLowerCase(), "LOCATION_ID_LOWER", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramLocation.getLocationBPWID(), "LOCATION_BPW_ID", true);
      DBUtil.executeUpdate(localPreparedStatement, "update location set DIVISION_ENT_GROUP_ID=?, LOCATION_NAME=?, LOCATION_NAME_LOWER=?, LOCATION_ID=?, LOCATION_ID_LOWER=? where LOCATION_BPW_ID=?");
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, "LocationAdapter.addLocation");
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
  }
  
  private static boolean a(Connection paramConnection, int paramInt, String paramString1, String paramString2, String paramString3)
    throws ProfileException
  {
    String str = "LocationAdapter.isUniqueLocation";
    boolean bool = true;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("select  l.LOCATION_BPW_ID, l.DIVISION_ENT_GROUP_ID, l.LOCATION_NAME, l.LOCATION_ID  from location l where l.DIVISION_ENT_GROUP_ID=? and ( l.LOCATION_NAME_LOWER=? OR l.LOCATION_ID=? ) ");
      if ((paramString3 != null) && (paramString3.length() != 0)) {
        localStringBuffer.append(" and l.LOCATION_BPW_ID<>?");
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt, true);
      i = Profile.setStatementString(localPreparedStatement, i, paramString1, "LOCATION_NAME_LOWER", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramString2, "LOCATION_ID", true);
      if ((paramString3 != null) && (paramString3.length() != 0)) {
        i = Profile.setStatementString(localPreparedStatement, i, paramString3, "LOCATION_BPW_ID", false);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next()) {
        bool = false;
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, "LocationAdapter.isUniqueLocation");
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.LocationAdapter
 * JD-Core Version:    0.7.0.1
 */