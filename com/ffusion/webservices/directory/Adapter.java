package com.ffusion.webservices.directory;

import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.db.PoolException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class Adapter
{
  public static final String NAME = "name";
  public static final String DESCRIPTION = "description";
  public static final String SERVICE_ID = "service_id";
  public static final String BINDING_ID = "binding_id";
  public static final String PROTOCOL = "protocol";
  public static final String TRANSPORT = "transport";
  public static final String ADDRESS = "address";
  public static final String PRIORITY = "priority";
  private static final String jdField_do = "insert into sd_service (service_id,name,description) values (?,?,?)";
  private static final String jdField_for = "select * from sd_service ";
  private static final String jdField_long = "select * from sd_service where service_id = ?";
  private static final String jdField_case = "select * from sd_service where name = ?";
  private static final String a = "select * from sd_service where name = ? and description=?";
  private static final String jdField_try = "update sd_service set name=?, description=? where service_id=?";
  private static final String jdField_int = "delete sd_service where service_id = ?";
  private static final String jdField_else = "insert into sd_binding (binding_id, service_id, protocol, transport, address, priority) values (?,?,?,?,?,?)";
  private static final String jdField_byte = "select * from sd_binding where service_id=?";
  private static final String jdField_if = "update sd_binding set protocol=?,transport=?,address=?,priority=? where service_id=? and binding_id=?";
  private static final String jdField_goto = "delete from sd_binding where binding_id = ?";
  private static final String jdField_char = "delete from sd_binding where service_id = ?";
  private static String jdField_new = null;
  
  public Adapter(Properties paramProperties)
    throws DirectoryException
  {
    try
    {
      if (jdField_new == null) {
        jdField_new = ConnectionPool.init(paramProperties);
      }
    }
    catch (PoolException localPoolException)
    {
      throw new DirectoryException("Unable to create connection pool" + localPoolException);
    }
  }
  
  public void addService(Service paramService)
    throws DirectoryException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(jdField_new, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into sd_service (service_id,name,description) values (?,?,?)");
      localPreparedStatement.setInt(1, DBUtil.getNextId(localConnection, ConnectionPool.getDBType(jdField_new), "service_id"));
      localPreparedStatement.setString(2, paramService.getName());
      localPreparedStatement.setString(3, paramService.getDesc());
      DBUtil.execute(localPreparedStatement, "insert into sd_service (service_id,name,description) values (?,?,?)");
    }
    catch (Exception localException)
    {
      throw new DirectoryException("Error Adding Service ", localException);
    }
    finally
    {
      DBUtil.closeAll(jdField_new, localConnection, localPreparedStatement);
    }
  }
  
  public Service getServiceByName(String paramString)
    throws DirectoryException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Service localService = null;
    try
    {
      localConnection = DBUtil.getConnection(jdField_new, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from sd_service where name = ?");
      localPreparedStatement.setString(1, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from sd_service where name = ?");
      if (localResultSet.next())
      {
        localService = new Service();
        localService.setServiceID(localResultSet.getInt("service_id"));
        localService.setName(paramString);
        localService.setDesc(localResultSet.getString("description"));
      }
    }
    catch (Exception localException)
    {
      throw new DirectoryException("Error Getting Service by Name ", localException);
    }
    finally
    {
      DBUtil.closeAll(jdField_new, localConnection, localPreparedStatement, localResultSet);
    }
    if (localService != null) {
      localService.setBindings(getBindings(localService.getServiceID()));
    }
    return localService;
  }
  
  public Service getServiceByNameDesc(String paramString1, String paramString2)
    throws DirectoryException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Service localService = null;
    try
    {
      localConnection = DBUtil.getConnection(jdField_new, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from sd_service where name = ? and description=?");
      localPreparedStatement.setString(1, paramString1);
      localPreparedStatement.setString(2, paramString2);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from sd_service where name = ? and description=?");
      if (localResultSet.next())
      {
        localService = new Service();
        localService.setServiceID(localResultSet.getInt("service_id"));
        localService.setName(paramString1);
        localService.setDesc(paramString2);
      }
    }
    catch (Exception localException)
    {
      throw new DirectoryException("Error Getting Service by Name and Description ", localException);
    }
    finally
    {
      DBUtil.closeAll(jdField_new, localConnection, localPreparedStatement, localResultSet);
    }
    if (localService != null) {
      localService.setBindings(getBindings(localService.getServiceID()));
    }
    return localService;
  }
  
  public Service getService(int paramInt)
    throws DirectoryException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Service localService = null;
    try
    {
      localConnection = DBUtil.getConnection(jdField_new, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from sd_service where service_id = ?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from sd_service where service_id = ?");
      if (localResultSet.next())
      {
        localService = new Service();
        localService.setServiceID(paramInt);
        localService.setName(localResultSet.getString("name"));
        localService.setDesc(localResultSet.getString("description"));
      }
    }
    catch (Exception localException)
    {
      throw new DirectoryException("Error Getting Service by ID ", localException);
    }
    finally
    {
      DBUtil.closeAll(jdField_new, localConnection, localPreparedStatement, localResultSet);
    }
    if (localService != null) {
      localService.setBindings(getBindings(localService.getServiceID()));
    }
    return localService;
  }
  
  public ArrayList getServiceList()
    throws DirectoryException
  {
    Connection localConnection = null;
    Statement localStatement = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList = null;
    try
    {
      localConnection = DBUtil.getConnection(jdField_new, true, 2);
      localStatement = DBUtil.createStatement(localConnection);
      localResultSet = DBUtil.executeQuery(localStatement, "select * from sd_service ");
      while (localResultSet.next())
      {
        if (localArrayList == null) {
          localArrayList = new ArrayList(2);
        }
        Service localService = new Service();
        localService.setServiceID(localResultSet.getInt("service_id"));
        localService.setName(localResultSet.getString("name"));
        localService.setDesc(localResultSet.getString("description"));
        localArrayList.add(localService);
      }
    }
    catch (Exception localException)
    {
      throw new DirectoryException("Error Getting Service List ", localException);
    }
    finally
    {
      DBUtil.closeAll(jdField_new, localConnection, localStatement, localResultSet);
    }
    return localArrayList;
  }
  
  public void modifyService(Service paramService)
    throws DirectoryException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(jdField_new, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update sd_service set name=?, description=? where service_id=?");
      localPreparedStatement.setString(1, paramService.getName());
      localPreparedStatement.setString(2, paramService.getDesc());
      localPreparedStatement.setInt(3, paramService.getServiceID());
      DBUtil.execute(localPreparedStatement, "update sd_service set name=?, description=? where service_id=?");
    }
    catch (Exception localException)
    {
      throw new DirectoryException("Error Modifying Service ", localException);
    }
    finally
    {
      DBUtil.closeAll(jdField_new, localConnection, localPreparedStatement);
    }
  }
  
  public void removeService(int paramInt)
    throws DirectoryException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    removeBindingByServiceID(paramInt);
    try
    {
      localConnection = DBUtil.getConnection(jdField_new, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete sd_service where service_id = ?");
      localPreparedStatement.setInt(1, paramInt);
      DBUtil.execute(localPreparedStatement, "delete sd_service where service_id = ?");
    }
    catch (Exception localException)
    {
      throw new DirectoryException("Error Removing Service ", localException);
    }
    finally
    {
      DBUtil.closeAll(jdField_new, localConnection, localPreparedStatement);
    }
  }
  
  public void addBinding(int paramInt, Binding paramBinding)
    throws DirectoryException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(jdField_new, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into sd_binding (binding_id, service_id, protocol, transport, address, priority) values (?,?,?,?,?,?)");
      localPreparedStatement.setInt(1, DBUtil.getNextId(localConnection, ConnectionPool.getDBType(jdField_new), "binding_id"));
      localPreparedStatement.setInt(2, paramInt);
      localPreparedStatement.setString(3, paramBinding.getProtocol());
      localPreparedStatement.setString(4, paramBinding.getTransport());
      localPreparedStatement.setString(5, paramBinding.getAddress());
      localPreparedStatement.setInt(6, paramBinding.getPriority());
      DBUtil.execute(localPreparedStatement, "insert into sd_binding (binding_id, service_id, protocol, transport, address, priority) values (?,?,?,?,?,?)");
    }
    catch (Exception localException)
    {
      throw new DirectoryException("Error Adding Binding ", localException);
    }
    finally
    {
      DBUtil.closeAll(jdField_new, localConnection, localPreparedStatement);
    }
  }
  
  public ArrayList getBindings(int paramInt)
    throws DirectoryException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList = null;
    try
    {
      localConnection = DBUtil.getConnection(jdField_new, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from sd_binding where service_id=?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from sd_binding where service_id=?");
      while (localResultSet.next())
      {
        if (localArrayList == null) {
          localArrayList = new ArrayList(2);
        }
        Binding localBinding = new Binding();
        localBinding.setBindingID(localResultSet.getInt("binding_id"));
        localBinding.setProtocol(localResultSet.getString("protocol"));
        localBinding.setTransport(localResultSet.getString("transport"));
        localBinding.setAddress(localResultSet.getString("address"));
        localBinding.setPriority(localResultSet.getInt("priority"));
        localArrayList.add(localBinding);
      }
    }
    catch (Exception localException)
    {
      throw new DirectoryException("Error Getting Bindings ", localException);
    }
    finally
    {
      DBUtil.closeAll(jdField_new, localConnection, localPreparedStatement, localResultSet);
    }
    return localArrayList;
  }
  
  public void modifyBinding(int paramInt, Binding paramBinding)
    throws DirectoryException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(jdField_new, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update sd_service set name=?, description=? where service_id=?");
      localPreparedStatement.setString(1, paramBinding.getProtocol());
      localPreparedStatement.setString(2, paramBinding.getTransport());
      localPreparedStatement.setString(3, paramBinding.getAddress());
      localPreparedStatement.setInt(4, paramBinding.getPriority());
      localPreparedStatement.setInt(5, paramInt);
      localPreparedStatement.setInt(6, paramBinding.getBindingID());
      DBUtil.execute(localPreparedStatement, "update sd_service set name=?, description=? where service_id=?");
    }
    catch (Exception localException)
    {
      throw new DirectoryException("Error Modifying Binding ", localException);
    }
    finally
    {
      DBUtil.closeAll(jdField_new, localConnection, localPreparedStatement);
    }
  }
  
  public void removeBinding(int paramInt)
    throws DirectoryException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(jdField_new, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from sd_binding where binding_id = ?");
      localPreparedStatement.setInt(1, paramInt);
      DBUtil.execute(localPreparedStatement, "delete from sd_binding where binding_id = ?");
    }
    catch (Exception localException)
    {
      throw new DirectoryException("Error Removing Binding by Binding ID", localException);
    }
    finally
    {
      DBUtil.closeAll(jdField_new, localConnection, localPreparedStatement);
    }
  }
  
  public void removeBindingByServiceID(int paramInt)
    throws DirectoryException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(jdField_new, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from sd_binding where service_id = ?");
      localPreparedStatement.setInt(1, paramInt);
      DBUtil.execute(localPreparedStatement, "delete from sd_binding where service_id = ?");
    }
    catch (Exception localException)
    {
      throw new DirectoryException("Error Removing Binding by Service ID", localException);
    }
    finally
    {
      DBUtil.closeAll(jdField_new, localConnection, localPreparedStatement);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.directory.Adapter
 * JD-Core Version:    0.7.0.1
 */