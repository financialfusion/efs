package com.ffusion.alert.interfaces;

import com.ffusion.alert.encryption.des.TripleDESEncrypt;
import java.io.Serializable;
import java.util.Properties;

public class AEDBParams
  implements Serializable
{
  public static final String PROP_TYPE = "dbType";
  public static final String PROP_USER = "dbUser";
  public static final String PROP_PASS = "dbPassword";
  public static final String PROP_HOST = "dbHost";
  public static final String PROP_PORT = "dbPort";
  public static final String PROP_DB_NAME = "dbName";
  public static final String PROP_JDBC_URL = "jdbcURL";
  public static final String PROP_HA_CTX_FACTORY = "dbHAContextFactory";
  public static final String PROP_HA_JNDI_URL = "dbHAJNDIURL";
  public static final String PROP_HA_JNDI_CTX = "dbHAJNDIContext";
  public static final String PROP_USER_ENCRYPTED = "dbUserEncrypted";
  public static final String PROP_PASS_ENCRYPTED = "dbPasswordEncrypted";
  public static final String PROP_TYPE_ASA = "ASA";
  public static final String PROP_TYPE_ASE = "ASE";
  public static final String PROP_TYPE_ASE_HA = "ASE:HA";
  public static final String PROP_TYPE_DB2_APP = "DB2:APP";
  public static final String PROP_TYPE_DB2_NET = "DB2:NET";
  public static final String PROP_TYPE_DB2_UD2 = "DB2:UD2";
  public static final String PROP_TYPE_ORA_OCI = "ORACLE:OCI";
  public static final String PROP_TYPE_ORA_THIN = "ORACLE:THIN";
  public static final String PROP_TYPE_MSSQL = "MSSQL";
  public static final int CONN_UNKNOWN = 0;
  public static final int CONN_ASA = 1;
  public static final int CONN_ASE = 2;
  public static final int CONN_DB2 = 3;
  public static final int CONN_ORACLE = 4;
  public static final int CONN_MSSQL = 5;
  public static final int CONN_DB2_UD2 = 6;
  public static final String ENC_ALG_3DES = "3des";
  private String jdField_try;
  private String jdField_if;
  private String jdField_for;
  private String jdField_char;
  private String jdField_do;
  private String jdField_int;
  private String jdField_goto;
  private String jdField_new;
  private boolean jdField_case;
  private boolean jdField_byte;
  private int a;
  private String jdField_else;
  
  public static AEDBParams createHAParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    AEDBParams localAEDBParams = new AEDBParams();
    localAEDBParams.jdField_try = paramString1;
    localAEDBParams.jdField_if = paramString2;
    localAEDBParams.jdField_int = paramString3;
    localAEDBParams.jdField_goto = paramString4;
    localAEDBParams.jdField_new = paramString5;
    localAEDBParams.a = 2;
    localAEDBParams.jdField_byte = true;
    return localAEDBParams;
  }
  
  public static AEDBParams createASEJConnectParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    AEDBParams localAEDBParams = new AEDBParams();
    localAEDBParams.jdField_try = paramString1;
    localAEDBParams.jdField_if = paramString2;
    localAEDBParams.jdField_for = paramString3;
    localAEDBParams.jdField_char = paramString4;
    localAEDBParams.jdField_do = paramString5;
    localAEDBParams.a = 2;
    return localAEDBParams;
  }
  
  public static AEDBParams createASAJConnectParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    AEDBParams localAEDBParams = new AEDBParams();
    localAEDBParams.jdField_try = paramString1;
    localAEDBParams.jdField_if = paramString2;
    localAEDBParams.jdField_for = paramString3;
    localAEDBParams.jdField_char = paramString4;
    localAEDBParams.jdField_do = paramString5;
    localAEDBParams.a = 1;
    return localAEDBParams;
  }
  
  public static AEDBParams createOracleParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, boolean paramBoolean)
  {
    AEDBParams localAEDBParams = new AEDBParams();
    localAEDBParams.jdField_try = paramString1;
    localAEDBParams.jdField_if = paramString2;
    localAEDBParams.jdField_for = paramString3;
    localAEDBParams.jdField_char = paramString4;
    localAEDBParams.jdField_do = paramString5;
    localAEDBParams.jdField_case = paramBoolean;
    localAEDBParams.a = 4;
    return localAEDBParams;
  }
  
  public static AEDBParams createDB2AppParams(String paramString1, String paramString2, String paramString3)
  {
    AEDBParams localAEDBParams = new AEDBParams();
    localAEDBParams.jdField_try = paramString1;
    localAEDBParams.jdField_if = paramString2;
    localAEDBParams.jdField_do = paramString3;
    localAEDBParams.jdField_case = true;
    localAEDBParams.a = 3;
    return localAEDBParams;
  }
  
  public static AEDBParams createDB2NetParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    AEDBParams localAEDBParams = new AEDBParams();
    localAEDBParams.jdField_try = paramString1;
    localAEDBParams.jdField_if = paramString2;
    localAEDBParams.jdField_for = paramString3;
    localAEDBParams.jdField_char = paramString4;
    localAEDBParams.jdField_do = paramString5;
    localAEDBParams.jdField_case = false;
    localAEDBParams.a = 3;
    return localAEDBParams;
  }
  
  public static AEDBParams createDB2UD2Params(String paramString1, String paramString2, String paramString3)
  {
    AEDBParams localAEDBParams = new AEDBParams();
    localAEDBParams.jdField_try = paramString1;
    localAEDBParams.jdField_if = paramString2;
    localAEDBParams.jdField_do = paramString3;
    localAEDBParams.jdField_case = false;
    localAEDBParams.a = 6;
    return localAEDBParams;
  }
  
  public static AEDBParams createMSSQLConnectParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    AEDBParams localAEDBParams = new AEDBParams();
    localAEDBParams.jdField_try = paramString1;
    localAEDBParams.jdField_if = paramString2;
    localAEDBParams.jdField_for = paramString3;
    localAEDBParams.jdField_char = paramString4;
    localAEDBParams.jdField_do = paramString5;
    localAEDBParams.a = 5;
    return localAEDBParams;
  }
  
  public static AEDBParams createParamsFromURL(String paramString1, String paramString2, String paramString3, String paramString4)
    throws IllegalArgumentException
  {
    AEDBParams localAEDBParams = new AEDBParams();
    localAEDBParams.jdField_try = paramString1;
    localAEDBParams.jdField_if = paramString2;
    localAEDBParams.jdField_case = false;
    localAEDBParams.jdField_else = paramString4;
    if (paramString3.equalsIgnoreCase("ASA"))
    {
      if (!paramString4.startsWith("jdbc:sybase:Tds")) {
        throw new IllegalArgumentException("Database type selected does not match database type in JDBC URL");
      }
      localAEDBParams.a = 1;
    }
    else if (paramString3.equalsIgnoreCase("ASE"))
    {
      if (!paramString4.startsWith("jdbc:sybase:Tds")) {
        throw new IllegalArgumentException("Database type selected does not match database type in JDBC URL");
      }
      localAEDBParams.a = 2;
    }
    else if (paramString3.equalsIgnoreCase("DB2:NET"))
    {
      if (!paramString4.startsWith("jdbc:db2://")) {
        throw new IllegalArgumentException("Database type selected does not match database type in JDBC URL");
      }
      localAEDBParams.a = 3;
    }
    else if (paramString3.equalsIgnoreCase("DB2:APP"))
    {
      if ((!paramString4.startsWith("jdbc:db2")) || (paramString4.startsWith("jdbc:db2://"))) {
        throw new IllegalArgumentException("Database type selected does not match database type in JDBC URL");
      }
      localAEDBParams.jdField_case = true;
      localAEDBParams.a = 3;
    }
    else if (paramString3.equalsIgnoreCase("DB2:UD2"))
    {
      if ((!paramString4.startsWith("jdbc:db2")) || (paramString4.startsWith("jdbc:db2://"))) {
        throw new IllegalArgumentException("Database type selected does not match database type in JDBC URL");
      }
      localAEDBParams.a = 6;
    }
    else if (paramString3.equalsIgnoreCase("ORACLE:OCI"))
    {
      if (!paramString4.startsWith("jdbc:oracle:oci")) {
        throw new IllegalArgumentException("Database type selected does not match database type in JDBC URL");
      }
      localAEDBParams.jdField_case = true;
      localAEDBParams.a = 4;
    }
    else if (paramString3.equalsIgnoreCase("ORACLE:THIN"))
    {
      if (!paramString4.startsWith("jdbc:oracle:thin")) {
        throw new IllegalArgumentException("Database type selected does not match database type in JDBC URL");
      }
      localAEDBParams.a = 4;
    }
    else if (paramString3.equalsIgnoreCase("MSSQL"))
    {
      if (!paramString4.startsWith("dbc:JSQLConnect://")) {
        throw new IllegalArgumentException("Database type selected does not match database type in JDBC URL");
      }
      localAEDBParams.a = 5;
    }
    else
    {
      throw new IllegalArgumentException("Unsupported database type " + paramString3);
    }
    return localAEDBParams;
  }
  
  public static AEDBParams createFromProperties(Properties paramProperties)
    throws IllegalArgumentException
  {
    if (paramProperties == null) {
      throw new IllegalArgumentException("Properties cannot be null.");
    }
    AEDBParams localAEDBParams = null;
    String str1 = paramProperties.getProperty("dbUser");
    if (str1 == null) {
      throw new IllegalArgumentException("Missing required property dbUser");
    }
    String str2 = paramProperties.getProperty("dbPassword");
    if (str2 == null) {
      throw new IllegalArgumentException("Missing required property dbPassword");
    }
    String str3 = paramProperties.getProperty("dbUserEncrypted");
    if ((str3 != null) && (str3.trim().length() != 0)) {
      if (str3.equalsIgnoreCase("3des"))
      {
        str1 = TripleDESEncrypt.a(str1);
        if (str1 == null) {
          throw new IllegalArgumentException("Unable to decrypt user name.");
        }
      }
      else
      {
        throw new IllegalArgumentException("Unsupported encryption algorithm " + str3 + " specified for the user name.");
      }
    }
    str3 = paramProperties.getProperty("dbPasswordEncrypted");
    if ((str3 != null) && (str3.trim().length() != 0)) {
      if (str3.equalsIgnoreCase("3des"))
      {
        str2 = TripleDESEncrypt.a(str2);
        if (str2 == null) {
          throw new IllegalArgumentException("Unable to decrypt password.");
        }
      }
      else
      {
        throw new IllegalArgumentException("Unsupported encryption algorithm " + str3 + " specified for the password.");
      }
    }
    String str4 = paramProperties.getProperty("dbType");
    if (str4 == null) {
      throw new IllegalArgumentException("Missing required property dbType");
    }
    String str5;
    if ((paramProperties.getProperty("jdbcURL") != null) && (paramProperties.getProperty("jdbcURL").length() > 0))
    {
      str5 = paramProperties.getProperty("jdbcURL");
      localAEDBParams = createParamsFromURL(str1, str2, str4, str5);
    }
    else
    {
      String str6;
      String str7;
      if (str4.equalsIgnoreCase("ASE:HA"))
      {
        str5 = paramProperties.getProperty("dbHAContextFactory");
        if (str5 == null) {
          throw new IllegalArgumentException("Missing required property dbHAContextFactory");
        }
        str6 = paramProperties.getProperty("dbHAJNDIURL");
        if (str6 == null) {
          throw new IllegalArgumentException("Missing required property dbHAJNDIURL");
        }
        str7 = paramProperties.getProperty("dbHAJNDIContext");
        localAEDBParams = createHAParams(str1, str2, str5, str6, str7);
      }
      else
      {
        str5 = paramProperties.getProperty("dbHost");
        if ((str5 == null) && (!str4.equalsIgnoreCase("DB2:APP")) && (!str4.equalsIgnoreCase("DB2:UD2"))) {
          throw new IllegalArgumentException("Missing required property dbHost");
        }
        str6 = paramProperties.getProperty("dbPort");
        if ((str6 == null) && (!str4.equalsIgnoreCase("DB2:APP")) && (!str4.equalsIgnoreCase("DB2:UD2"))) {
          throw new IllegalArgumentException("Missing required property dbPort");
        }
        str7 = paramProperties.getProperty("dbName");
        if (str7 == null) {
          throw new IllegalArgumentException("Missing required property dbName");
        }
        if (str4.equalsIgnoreCase("ASA")) {
          localAEDBParams = createASAJConnectParams(str1, str2, str5, str6, str7);
        } else if (str4.equalsIgnoreCase("ASE")) {
          localAEDBParams = createASEJConnectParams(str1, str2, str5, str6, str7);
        } else if (str4.equalsIgnoreCase("DB2:APP")) {
          localAEDBParams = createDB2AppParams(str1, str2, str7);
        } else if (str4.equalsIgnoreCase("DB2:NET")) {
          localAEDBParams = createDB2NetParams(str1, str2, str5, str6, str7);
        } else if (str4.equalsIgnoreCase("DB2:UD2")) {
          localAEDBParams = createDB2UD2Params(str1, str2, str7);
        } else if (str4.equalsIgnoreCase("ORACLE:OCI")) {
          localAEDBParams = createOracleParams(str1, str2, str5, str6, str7, true);
        } else if (str4.equalsIgnoreCase("ORACLE:THIN")) {
          localAEDBParams = createOracleParams(str1, str2, str5, str6, str7, false);
        } else if (str4.equalsIgnoreCase("MSSQL")) {
          localAEDBParams = createMSSQLConnectParams(str1, str2, str5, str6, str7);
        } else {
          throw new IllegalArgumentException("Invalid required property dbType: " + str4);
        }
      }
    }
    return localAEDBParams;
  }
  
  public final String getUser()
  {
    return this.jdField_try;
  }
  
  public final String getPassword()
  {
    return this.jdField_if;
  }
  
  public final String getMachine()
  {
    return this.jdField_for;
  }
  
  public final String getPort()
  {
    return this.jdField_char;
  }
  
  public final String getDBName()
  {
    return this.jdField_do;
  }
  
  public final String getCtxFactory()
  {
    return this.jdField_int;
  }
  
  public final String getJNDIURL()
  {
    return this.jdField_goto;
  }
  
  public final String getJNDICtx()
  {
    return this.jdField_new;
  }
  
  public final boolean isNativeDriver()
  {
    return this.jdField_case;
  }
  
  public final boolean isHA()
  {
    return this.jdField_byte;
  }
  
  public int getConnectionType()
  {
    return this.a;
  }
  
  public String getJDBCURL()
  {
    return this.jdField_else;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.interfaces.AEDBParams
 * JD-Core Version:    0.7.0.1
 */