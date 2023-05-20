package com.ffusion.ffs.bpw.interfaces;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class RPPSFIDBAccessor
{
  private static final String a = "";
  private Connection jdField_if;
  
  public RPPSFIDBAccessor(String paramString1, String paramString2, String paramString3, String paramString4)
    throws Exception
  {
    String str = "";
    try
    {
      System.out.println("Registering " + paramString4 + " ...");
      Class.forName(paramString4);
      System.out.println("Creating connection...");
      this.jdField_if = DriverManager.getConnection(paramString1, paramString2, paramString3);
    }
    catch (Exception localException)
    {
      str = "Error setting up DB connection: " + localException.getMessage();
    }
    if (!str.equals("")) {
      throw new Exception(str);
    }
  }
  
  public String add(RPPSFIInfo paramRPPSFIInfo)
    throws Exception
  {
    Statement localStatement = null;
    String str2 = "";
    int i = 0;
    try
    {
      System.out.println("Creating stmt...");
      localStatement = this.jdField_if.createStatement();
      System.out.println("Adding RPPS FI Info...");
      String str1 = "INSERT INTO RRPS_FIInfo(FIRPPSID, FIID, FIRPPSName, RPPSId, RPPSName, Memo, SubmitDate, ActivationDate, FIStatus) VALUES (";
      str1 = str1 + "'" + paramRPPSFIInfo.getFiRPPSId() + "', " + "'" + paramRPPSFIInfo.getFiId() + "', " + "'" + paramRPPSFIInfo.getFiRPPSName() + "', " + "'" + paramRPPSFIInfo.getRppsId() + "', " + "'" + paramRPPSFIInfo.getRppsName() + "', " + "'" + paramRPPSFIInfo.getMemo() + "', " + "'" + paramRPPSFIInfo.getSubmitDate() + "', " + "'" + paramRPPSFIInfo.getActivationDate() + "', " + "'" + paramRPPSFIInfo.getFiStatus() + "')";
      i = localStatement.executeUpdate(str1);
    }
    catch (Exception localException1)
    {
      str2 = "Error adding RPPS FI info: " + localException1.toString() + "\n";
    }
    try
    {
      if (localStatement != null) {
        localStatement.close();
      }
    }
    catch (Exception localException2) {}
    if (str2.equals("")) {
      return paramRPPSFIInfo.getFiRPPSId();
    }
    throw new Exception(str2);
  }
  
  public void delete(String paramString)
    throws Exception
  {
    Statement localStatement = null;
    String str2 = "";
    try
    {
      System.out.println("Creating stmt...");
      localStatement = this.jdField_if.createStatement();
      System.out.println("Deleting RPPS FI Info...");
      String str1 = "DELETE FROM RRPS_FIInfo WHERE FIRPPSId = '" + paramString + "'";
      int i = localStatement.executeUpdate(str1);
    }
    catch (Exception localException1)
    {
      str2 = "Error deleting RPPS FI info: " + localException1.toString() + "\n";
    }
    try
    {
      if (localStatement != null) {
        localStatement.close();
      }
    }
    catch (Exception localException2) {}
    if (!str2.equals("")) {
      throw new Exception(str2);
    }
  }
  
  public void modify(RPPSFIInfo paramRPPSFIInfo)
    throws Exception
  {
    String str = "";
    try
    {
      this.jdField_if.setAutoCommit(false);
      System.out.println("First deleting RPPS FI Info...");
      delete(paramRPPSFIInfo.getFiRPPSId());
      System.out.println("Next adding RPPS FI Info...");
      add(paramRPPSFIInfo);
    }
    catch (Exception localException)
    {
      str = "Error modifying RPPS FI info: " + localException.toString() + "\n";
    }
    this.jdField_if.setAutoCommit(true);
    if (!str.equals("")) {
      throw new Exception(str);
    }
  }
  
  public void cleanup()
  {
    try
    {
      if (this.jdField_if != null) {
        this.jdField_if.close();
      }
    }
    catch (Exception localException) {}
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.RPPSFIDBAccessor
 * JD-Core Version:    0.7.0.1
 */