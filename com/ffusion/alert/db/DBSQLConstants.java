package com.ffusion.alert.db;

public abstract interface DBSQLConstants
{
  public static final int eW = 4000;
  public static final String eX = " ";
  public static final byte[] eO = { 0 };
  public static final int eM = 255;
  public static final String eV = " {null|null||null|null} ";
  public static final String eK = " {int|numeric(8,0)|int|int|int} ";
  public static final String eR = " {identity not null primary key|identity not null primary key|not null primary key generated always as identity|not null primary key|identity not null primary key}  ";
  public static final String eP = " varchar{|||2|}";
  public static final String eJ = " {references||references|references|references} ";
  public static final String eN = " {on delete cascade||on delete cascade|on delete cascade|on delete cascade} ";
  public static final String eL = " {on delete cascade||on delete cascade|on delete cascade|} ";
  public static final String eT = " {long varchar|text|clob(1G)|clob|text} ";
  public static final String eY = " {long binary|image|blob(1G)|blob|image} ";
  public static final String eZ = " varchar{|||2|}(255) ";
  public static final String eS = " {?|?|?|rpad(?,255)|?} ";
  public static final String eQ = " {bigint|numeric(19,0)|bigint|number(19,0)|bigint} ";
  public static final String eU = "\r\n";
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.db.DBSQLConstants
 * JD-Core Version:    0.7.0.1
 */