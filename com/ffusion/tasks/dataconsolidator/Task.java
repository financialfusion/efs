package com.ffusion.tasks.dataconsolidator;

public abstract interface Task
  extends com.ffusion.tasks.Task
{
  public static final String IMPORTEDFILELIST = "ImportedFileList";
  public static final String DEPENDANTFILELIST = "DependantFileList";
  public static final String IMPORTEDFILE = "ImportedFile";
  public static final int ERROR_INVALID_START_DATE = 10041;
  public static final int ERROR_INVALID_END_DATE = 10042;
  public static final int ERROR_END_BEFORE_START_DATE = 10043;
  public static final int ERROR_IMPORTEDFILELIST_NOT_FOUND = 10044;
  public static final int ERROR_IMPORTEDFILE_NOT_FOUND = 10045;
  public static final int ERROR_IMPORTEDFILE_INDEX_NOT_SET = 10046;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.dataconsolidator.Task
 * JD-Core Version:    0.7.0.1
 */