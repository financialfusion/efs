package com.ffusion.webservices.directory;

public class DirectoryException
  extends Exception
{
  public DirectoryException() {}
  
  public DirectoryException(String paramString)
  {
    super(paramString);
  }
  
  public DirectoryException(String paramString, Exception paramException)
  {
    super(paramString + " " + paramException.getMessage());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.directory.DirectoryException
 * JD-Core Version:    0.7.0.1
 */