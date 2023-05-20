package com.ffusion.alert.db.lang;

import com.ffusion.alert.db.IAEErrConstants;
import java.util.ListResourceBundle;

public class AEErrStrings
  extends ListResourceBundle
  implements IAEErrConstants
{
  private static final String fF = System.getProperty("line.separator");
  static final Object[][] fE = { { "MSG_CREATING_REP", "Creating repository" }, { "MSG_DELETING_REP", "Deleting repository" }, { "ERR_JDBC_DRIVER_NOT_FOUND", "Could not connect to database because JDBC driver \"{0}\" was not found." }, { "ERR_COULD_NOT_CONNECT_TO_DB", "Could not connect to database using driver \"{0}\" and url \"{1}\"" }, { "ERR_UNKNOWN_CONNECTION_TYPE", "Unknown database connection type." }, { "ERR_NO_REPOSITORY", "A repository does not exist in this database." }, { "ERR_REPOSITORY_TOO_OLD", "This repository requires an older version of the Universal Alerts Engine" }, { "ERR_REPOSITORY_TOO_NEW", "This repository requires a newer version of the Universal Alerts Engine" }, { "ERR_CREATING_REPOSITORY", "A database error occured while creating the repository." }, { "ERR_DESTROYING_REPOSITORY", "A database error occured while destroying the repository." }, { "ERR_INVALID_NAME_OR_PASS", "The name or password specified is invalid." }, { "ERR_NAME_EXISTS", "The specified name already exists." }, { "ERR_BAD_ENCODING", "Encoding \"{0}\" is not supported. Using platform default encoding instead." }, { "ERR_MALFORMED_SERVER_URL", "The url \"{0}\" is malformed.  Could not connect to server." }, { "ERR_BAD_SERVER_INFO", "The {0} field has not been set for server {1}." }, { "PARAM_SERVER_INFO_ADMIN_JNDI", "admin JNDI" }, { "PARAM_SERVER_INFO_CLIENT_JNDI", "client JNDI" }, { "PARAM_SERVER_INFO_CTX_FACTORY", "context factory" } };
  
  public Object[][] getContents()
  {
    return fE;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.db.lang.AEErrStrings
 * JD-Core Version:    0.7.0.1
 */