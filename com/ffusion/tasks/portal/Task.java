package com.ffusion.tasks.portal;

public abstract interface Task
  extends com.ffusion.tasks.Task
{
  public static final String FORECAST_SESSION = "Forecast";
  public static final String FORECASTDAY_SESSION = "ForecastDay";
  public static final String FORECAST_SETTINGS_SESSION = "ForecastSettings";
  public static final String FORECAST_SETTINGS_TEMP_SESSION = "ForecastSettingsTemp";
  public static final String FORECASTS_SESSION = "Forecasts";
  public static final String FORECASTS_EDIT_SESSION = "ForecastsEdit";
  public static final String CITY_LIST_CONTEXT = "ForecastCityList";
  public static final String COUNTRY_CITY_LIST_CONTEXT = "ForecastCountryCityList";
  public static final String STATE_CITY_LIST_CONTEXT = "ForecastStateCityList";
  public static final String STATES_CONTEXT = "ForecastStatesContext";
  public static final String STATES_SESSION = "ForecastStates";
  public static final String STATE_CITIES_SESSION = "ForecastStateCities";
  public static final String COUNTRIES_CONTEXT = "ForecastCountriesContext";
  public static final String COUNTRIES_SESSION = "ForecastCountries";
  public static final String COUNTRY_CITIES_SESSION = "ForecastCountryCities";
  public static final String DAYS_SESSION = "Days";
  public static final String UNIT_SESSION = "GeographicUnit";
  public static final String UNITS_SESSION = "GeographicUnits";
  public static final String NEWS_SESSION = "News";
  public static final String NEWS_SETTINGS_SESSION = "NewsSettings";
  public static final String NEWS_FRONTPAGE_SESSION = "NewsFrontpage";
  public static final String NEWS_FRONTPAGE_SETTINGS_SESSION = "NewsFrontpageSettings";
  public static final String NEWS_BUSINESS_SESSION = "NewsBusiness";
  public static final String NEWS_BUSINESS_SETTINGS_SESSION = "NewsBusinessSettings";
  public static final String NEWS_SPORTS_SESSION = "NewsSports";
  public static final String NEWS_SPORTS_SETTINGS_SESSION = "NewsSportsSettings";
  public static final String NEWS_FRONTPAGE = "NEWSFRONTPAGE";
  public static final String NEWS_BUSINESS = "NEWSBUSINESS";
  public static final String NEWS_SPORTS = "NEWSSPORTS";
  public static final String HEADLINE_SESSION = "NewsHeadline";
  public static final String NEWS_STORY_SESSION = "NewsStory";
  public static final String NEWS_HEADLINES_SESSION = "NewsHeadlines";
  public static final String NEWS_HEADLINE_SETTINGS_SESSION = "NewsHeadlineSettings";
  public static final String FRONTPAGE = "FRONTPAGE";
  public static final String BUSINESS = "BUSINESS";
  public static final String SPORTS = "SPORTS";
  public static final String PORTAL_ITEMS_SESSION = "PortalItems";
  public static final String STOCK_SESSION = "Stock";
  public static final String STOCK_INDEX_SESSION = "StockIndex";
  public static final String STOCK_INDEX_SETTINGS_SESSION = "StockIndexSettings";
  public static final String STOCK_INDEXES_SESSION = "StockIndexes";
  public static final String STOCK_INDEXES_EDIT_SESSION = "StockIndexesEdit";
  public static final String STOCK_NEWS_SESSION = "PortalStockNews";
  public static final String STOCK_SETTINGS_SESSION = "StockSettings";
  public static final String STOCKS_SESSION = "Stocks";
  public static final String STOCKS_EDIT_SESSION = "StocksEdit";
  public static final String PORTAL_CONTENT_SESSION = "PortalContent";
  public static final String PORTAL_DATA_SESSION = "PortalData";
  public static final String PORTAL_CONTENT_PROVIDER_SESSION = "PortalContentProvider";
  public static final String CALCULATORS_SESSION = "Calculators";
  public static final String CALCULATORS_MASTER_SESSION = "CalculatorsMaster";
  public static final String CALCULATOR_SETTINGS_SESSION = "CalculatorsSettings";
  public static final String CALCULATORS_EDIT_SESSION = "CalculatorsEdit";
  public static final String ADVISORS_SESSION = "Advisors";
  public static final String ADVISORS_MASTER_SESSION = "AdvisorsMaster";
  public static final String ADVISORS_SETTINGS_SESSION = "AdvisorsSettings";
  public static final String ADVISORS_EDIT_SESSION = "AdvisorsEdit";
  public static final String NO_STATE_OR_COUNTRY_SELECTED = "UN";
  public static final int ERROR_CLASS_NOT_FOUND = 9000;
  public static final int ERROR_CLASS_NAME_NOT_SPECIFIED = 9001;
  public static final int ERROR_UNABLE_TO_CREATE_SERVICE = 9002;
  public static final int ERROR_ADVISORS_EDIT_NOT_IN_SESSION = 9003;
  public static final int ERROR_CALCULATORS_EDIT_NOT_IN_SESSION = 9004;
  public static final int ERROR_FORECASTS_EDIT_NOT_IN_SESSION = 9005;
  public static final int ERROR_NEWS_FRONTPAGE_SETTINGS_NOT_IN_SESSION = 9006;
  public static final int ERROR_NEWS_BUSINESS_SETTINGS_NOT_IN_SESSION = 9007;
  public static final int ERROR_NEWS_SPORTS_SETTINGS_NOT_IN_SESSION = 9008;
  public static final int ERROR_PORTAL_ITEMS_NOT_IN_SESSION = 9009;
  public static final int ERROR_STOCKS_EDIT_NOT_IN_SESSION = 9010;
  public static final int ERROR_COUNTRIES_NOT_IN_SESSION = 9011;
  public static final int ERROR_CITY_LIST_NOT_IN_CONTEXT = 9012;
  public static final int ERROR_STATES_NOT_IN_SESSION = 9013;
  public static final int ERROR_PORTAL_SETTINGS_NOT_IN_SESSION = 9014;
  public static final int ERROR_LOCALE_NOT_IN_SESSION = 9015;
  public static final int ERROR_ADVISORS_MASTER_NOT_IN_SESSION = 9016;
  public static final int ERROR_CALCULATORS_MASTER_NOT_IN_SESSION = 9017;
  public static final int ERROR_CITIES_FILE_NOT_LOADED = 9018;
  public static final int ERROR_STATES_FILE_NOT_LOADED = 9019;
  public static final int ERROR_COUNTRIES_FILE_NOT_LOADED = 9020;
  public static final int ERROR_PORTAL_DATA_NOT_IN_SESSION = 9021;
  public static final int ERROR_FORECAST_SETTINGS_NOT_IN_SESSION = 9022;
  public static final int ERROR_DAYS_NOT_IN_SESSION = 9023;
  public static final int ERROR_STATES_NOT_IN_CONTEXT = 9024;
  public static final int ERROR_COUNTRIES_NOT_IN_CONTEXT = 9025;
  public static final int ERROR_NEWS_SETTINGS_NOT_IN_SESSION = 9026;
  public static final int ERROR_STOCK_SETTINGS_NOT_IN_SESSION = 9027;
  public static final int ERROR_STOCK_INDEX_SETTINGS_NOT_IN_SESSION = 9028;
  public static final int ERROR_CALCULATOR_SETTINGS_NOT_IN_SESSION = 9029;
  public static final int ERROR_ADVISORS_SETTINGS_NOT_IN_SESSION = 9030;
  public static final int ERROR_MALFORMED_NEWS_URL = 9031;
  public static final int ERROR_UNABLE_TO_READ_NEWS = 9032;
  public static final int ERROR_STOCK_INDEX_EDIT_NOT_IN_SESSION = 9033;
  public static final int ERROR_UNABLE_TO_RETRIEVE_STOCKS = 9034;
  public static final int ERROR_UNABLE_TO_RETRIEVE_FORECASTS = 9035;
  public static final int ERROR_UNABLE_TO_RETRIEVE_NEWS = 9036;
  public static final int ERROR_CONTENT_PROVIDER_NOT_IN_SESSION = 9037;
  public static final int ERROR_STOCK_COLLECTION_NOT_FOUND = 9038;
  public static final int ERROR_STOCK_NOT_FOUND_IN_COLLECTION = 9039;
  public static final int ERROR_STOCK_NOT_FOUND_IN_SESSION = 9040;
  public static final int ERROR_STOCK_INVALID_SYMBOL = 9041;
  public static final int ERROR_STOCK_NO_ROOM_MUST_DELETE_FIRST = 9042;
  public static final int ERROR_STOCK_INVALID_SHARES = 9043;
  public static final int ERROR_STOCK_INVALID_PRICE = 9044;
  public static final int ERROR_INVALID_PORTAL_GROUP_NAMES = 9045;
  public static final int ERROR_INVALID_PORTAL_CONTENT_PREFERNEC_LEVEL = 9046;
  public static final int ERROR_DUPLICATE_PORTAL_CONTENT_PREFERNEC_LEVEL = 9047;
  public static final String ENTITLEMENT_ADDSTOCK = "AddStock";
  public static final String ENTITLEMENT_DELETESTOCK = "DeleteStock";
  public static final String ENTITLEMENT_EDITADVISORS = "EditAdvisors";
  public static final String ENTITLEMENT_EDITCALCULATORS = "EditCalculators";
  public static final String ENTITLEMENT_EDITFORECASTS = "EditForecasts";
  public static final String ENTITLEMENT_EDITGENERIC = "EditGeneric";
  public static final String ENTITLEMENT_EDITNEWS = "EditNews";
  public static final String ENTITLEMENT_EDITSTOCK = "EditStock";
  public static final String ENTITLEMENT_EDITSTOCKS = "EditStocks";
  public static final String ENTITLEMENT_GETCOUNTRYCITIES = "GetCountryCities";
  public static final String ENTITLEMENT_GETSTATECITIES = "GetStateCities";
  public static final String ENTITLEMENT_MOVEITEM = "MoveItem";
  public static final String ENTITLEMENT_SAVESETTINGS = "SaveSettings";
  public static final String ENTITLEMENT_SETSTOCK = "SetStock";
  public static final String ENTITLEMENT_STOCKLOOKUP = "StockLookup";
  public static final String ENTITLEMENT_VIEWNEWSSTORY = "ViewNewsStory";
  public static final String ENTITLEMENT_VIEWSTOCKNEWS = "ViewStockNews";
  public static final String ENTITLEMENT_PORTALREFRESH = "PortalRefresh";
  public static final String ENTITLEMENT_PORTALREFRESHFORECASTS = "PortalRefreshForecasts";
  public static final String ENTITLEMENT_PORTALREFRESHNEWS = "PortalRefreshNews";
  public static final String ENTITLEMENT_PORTALREFRESHSTOCKS = "PortalRefreshStocks";
  public static final String ENTITLEMENT_PORTALREFRESHALL = "PortalRefreshAll";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.Task
 * JD-Core Version:    0.7.0.1
 */