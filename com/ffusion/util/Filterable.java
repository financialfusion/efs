package com.ffusion.util;

public abstract interface Filterable
{
  public static final String ALL_FILTER = "All";
  public static final String ACTIONS = "=<>!";
  public static final String EQUAL_ACTION = "=";
  public static final String EQUAL_ACTION_IGNORE_CASE = "==";
  public static final String NOT_EQUAL_ACTION = "!";
  public static final String NOT_EQUAL_ACTION_IGNORE_CASE = "!!";
  public static final String LESS_THAN_ACTION = "<";
  public static final String EQUAL_LESS_THAN_ACTION = "=<";
  public static final String LESS_THAN_EQUAL_ACTION = "<=";
  public static final String LESS_THAN_ACTION_IGNORE_CASE = "<<";
  public static final String GREATER_THAN_ACTION = ">";
  public static final String EQUAL_GREATER_THAN_ACTION = "=>";
  public static final String GREATER_THAN_EQUAL_ACTION = ">=";
  public static final String GREATER_THAN_ACTION_IGNORE_CASE = ">>";
  
  public abstract boolean isFilterable(String paramString);
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.Filterable
 * JD-Core Version:    0.7.0.1
 */