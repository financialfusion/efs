package com.ffusion.util;

public abstract interface IUtilConsts
  extends DateConsts
{
  public static final String ERR_SERVICE_INIT_FAILED = "The Util Service could not be initialized. ";
  public static final String ERR_CANNOT_LOAD_CONFIG_XML = " Unable to load Util Service configuration file fieldValidation.xml";
  public static final String ERR_CANNOT_PARSE_CONFIG_XML = " An error occurred while parsing the Util Service configuration file fieldValidationFormats.xml";
  public static final String ERR_CANNOT_LOAD_BLCONFIG_XML = " Unable to load Util Service configuration file bankLookupCountries.xml";
  public static final String ERR_CANNOT_PARSE_BLCONFIG_XML = " An error occurred while parsing the Util Service configuration file bankLookupCountries.xml";
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.IUtilConsts
 * JD-Core Version:    0.7.0.1
 */