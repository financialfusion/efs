package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.TaxForm;
import com.ffusion.beans.ach.TaxForms;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TaxFormAdapter
  implements ProfileDefines
{
  private static final String ahm = "00";
  private static final String ahn = "select * from tax_forms where directory_id=?";
  private static final String ahl = "insert into tax_forms (directory_id,tax_type,tax_state,tax_code,tax_name,ID) values (?,?,?,?,?,?)";
  private static final String ahk = "delete from tax_forms where directory_id=? and ID=?";
  
  public static TaxForm addTaxForm(SecureUser paramSecureUser, TaxForm paramTaxForm)
    throws ProfileException
  {
    String str1 = "TaxFormAdapter.addTaxForm";
    StringBuffer localStringBuffer = new StringBuffer("insert into tax_forms (directory_id,tax_type,tax_state,tax_code,tax_name,ID) values (?,?,?,?,?,?)");
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into tax_forms (directory_id,tax_type,tax_state,tax_code,tax_name,ID) values (?,?,?,?,?,?)");
      int i = 1;
      localPreparedStatement.setInt(i++, paramSecureUser.getProfileID());
      localPreparedStatement.setInt(i++, paramTaxForm.getTypeValue());
      String str2 = paramTaxForm.getState();
      if (str2 == null) {
        str2 = "00";
      }
      localPreparedStatement.setString(i++, str2);
      localPreparedStatement.setString(i++, "code");
      Profile.setStatementString(localPreparedStatement, i++, paramTaxForm.getName(), "tax_name", false, false, false, true);
      Profile.setStatementString(localPreparedStatement, i++, paramTaxForm.getID(), "ID", false, false, false, true);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
    return paramTaxForm;
  }
  
  public static TaxForms getTaxForms(SecureUser paramSecureUser)
    throws ProfileException
  {
    String str = "TaxFormAdapter.getTaxForms";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    TaxForms localTaxForms = new TaxForms();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from tax_forms where directory_id=?");
      int i = 1;
      localPreparedStatement.setInt(i, paramSecureUser.getProfileID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from tax_forms where directory_id=?");
      while (localResultSet.next())
      {
        TaxForm localTaxForm = new TaxForm();
        localTaxForm.setID(localResultSet.getString("ID"));
        localTaxForm.setTypeValue(localResultSet.getInt("tax_type"));
        localTaxForm.setName(Profile.getRSString(localResultSet, "tax_name"));
        if ((localTaxForm.getTypeValue() == 2) || (localTaxForm.getTypeValue() == 4)) {
          localTaxForm.setState(Profile.getRSString(localResultSet, "tax_state"));
        } else {
          localTaxForm.setState("");
        }
        localTaxForms.add(localTaxForm);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localTaxForms;
  }
  
  public static void deleteTaxForm(SecureUser paramSecureUser, TaxForm paramTaxForm)
    throws ProfileException
  {
    String str = "TaxFormAdapter.deleteTaxForm";
    if ((paramTaxForm == null) || (paramTaxForm.getID() == null)) {
      Profile.handleError(str, "Invalid tax form", 3);
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from tax_forms where directory_id=? and ID=?");
      localPreparedStatement.setInt(1, paramSecureUser.getProfileID());
      localPreparedStatement.setString(2, paramTaxForm.getID());
      DBUtil.executeUpdate(localPreparedStatement, "delete from tax_forms where directory_id=? and ID=?");
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.TaxFormAdapter
 * JD-Core Version:    0.7.0.1
 */