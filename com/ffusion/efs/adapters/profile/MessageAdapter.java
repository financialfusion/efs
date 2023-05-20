package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.bcreport.CSRTeamPerformanceRpt;
import com.ffusion.beans.bcreport.CasePerformanceData;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.messages.GlobalMessage;
import com.ffusion.beans.messages.GlobalMessageConsts;
import com.ffusion.beans.messages.GlobalMessageFilter;
import com.ffusion.beans.messages.GlobalMessageFilters;
import com.ffusion.beans.messages.GlobalMessageRecipient;
import com.ffusion.beans.messages.GlobalMessageRecipients;
import com.ffusion.beans.messages.GlobalMessageSearchCriteria;
import com.ffusion.beans.messages.GlobalMessageToGroup;
import com.ffusion.beans.messages.GlobalMessages;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.MessageCount;
import com.ffusion.beans.messages.MessageCounts;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportHeading;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.beans.user.User;
import com.ffusion.beans.util.LanguageDefn;
import com.ffusion.beans.util.LanguageDefns;
import com.ffusion.beans.util.UserUtil;
import com.ffusion.csil.handlers.UtilHandler;
import com.ffusion.reporting.RptException;
import com.ffusion.services.MessagingFilters;
import com.ffusion.services.javax.JavaMail;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

public class MessageAdapter
  implements ProfileDefines, GlobalMessageConsts
{
  private static final long anq = 86400000L;
  private static final long an2 = 604800000L;
  private static final long akC = 2419200000L;
  private static final String YES = "YES";
  private static final String anH = "TRUE";
  private static final String amj = "SUMMARY_LIST";
  private static final String amh = "750";
  private static final int aom = 125;
  private static final int ak1 = 8;
  private static final String am3 = "EMPLOYEE_NAME";
  private static final String CUSTOMER_NAME = "CUSTOMER_NAME";
  private static final String alC = "MESSAGE_COUNT";
  private static final String NAME = "NAME";
  private static final String CUSTOMER_ID = "CUSTID";
  private static final String an3 = " m.queue_id";
  private static final String amU = " m.create_date ";
  private static final String amz = " m.queue_id ";
  private static final String alZ = " m.employee_id ";
  private static final String al4 = "m.directory_id";
  private static final String ali = " m.employee_id IN ";
  private static final int amp = 256;
  private static final String anX = "gm";
  private static final String anM = "gmI18N";
  private static final String anh = "SELECT m.queue_id, COUNT(*) AS total FROM message m, queue q WHERE m.queue_id = q.queue_id AND m.create_date >= ? AND m.create_date <= ? ";
  private static final String alJ = "SELECT m.queue_id, COUNT(*) AS total FROM message m, queue q WHERE m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date >= ? AND m.create_date <= ? ";
  private static final String anS = "SELECT m.queue_id, COUNT(*) AS total FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ?) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND mi.from_type = 0)) ";
  private static final String ane = "SELECT m.queue_id, COUNT(*) AS total FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ?) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND mi.from_type = 0)) ";
  private static final String alz = "SELECT m.queue_id, COUNT(*) AS total FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (m.close_date IS NOT NULL AND m.close_date >= ? AND m.close_date <= ?) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) ";
  private static final String anZ = "SELECT COUNT(*) AS total FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (m.close_date IS NOT NULL AND m.close_date >= ? AND m.close_date <= ?) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) AND m.queue_id = ?";
  private static final String am7 = "SELECT m.queue_id, COUNT(*) AS total FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (m.close_date IS NOT NULL AND m.close_date >= ? AND m.close_date <= ?) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) ";
  private static final String alK = "SELECT COUNT(*) AS total FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (m.close_date IS NOT NULL AND m.close_date >= ? AND m.close_date <= ?) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) AND m.queue_id = ?";
  private static final String alA = "SELECT m.queue_id, AVG( TIMESTAMPDIFF( 2, CHAR(mi.read_date - m.create_date))) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ";
  private static final String alV = "SELECT m.queue_id, AVG( TIMESTAMPDIFF( 2, CHAR(mi.read_date - m.create_date))) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ";
  private static final String akx = "SELECT m.queue_id, MIN( TIMESTAMPDIFF( 2, CHAR(mi.read_date - m.create_date))), MAX( TIMESTAMPDIFF( 2, CHAR(mi.read_date - m.create_date))) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ";
  private static final String amI = "SELECT m.queue_id, MIN( TIMESTAMPDIFF( 2, CHAR(mi.read_date - m.create_date))), MAX( TIMESTAMPDIFF( 2, CHAR(mi.read_date - m.create_date))) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ";
  private static final String anR = "SELECT m.queue_id, AVG(CAST(mi.read_date as date) - CAST(m.create_date as date)) * 86400 FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ";
  private static final String anO = "SELECT m.queue_id, AVG( CAST(mi.read_date as date) - CAST(m.create_date as date) ) * 86400 FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ";
  private static final String ama = "SELECT m.queue_id, MIN( CAST(mi.read_date as date) - CAST(m.create_date as date) ) * 86400, MAX( CAST(mi.read_date as date) - CAST(m.create_date as date) ) * 86400 FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ";
  private static final String alL = "SELECT m.queue_id, MIN( CAST(mi.read_date as date) - CAST(m.create_date as date) ) * 86400, MAX( CAST(mi.read_date as date) - CAST(m.create_date as date) ) * 86400 FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ";
  private static final String any = "SELECT m.queue_id, AVG( DATEDIFF (second, m.create_date, mi.read_date)) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ";
  private static final String anQ = "SELECT m.queue_id, AVG( DATEDIFF (second, m.create_date, mi.read_date)) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ";
  private static final String amO = "SELECT m.queue_id, MIN( DATEDIFF (second, m.create_date, mi.read_date)), MAX( DATEDIFF (second, m.create_date, mi.read_date)) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ";
  private static final String akZ = "SELECT m.queue_id, MIN( DATEDIFF (second, m.create_date, mi.read_date)), MAX( DATEDIFF (second, m.create_date, mi.read_date)) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ";
  private static final String all = "SELECT m.queue_id, AVG( mi.read_date - m.create_date ) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ";
  private static final String an7 = "SELECT m.queue_id, AVG( mi.read_date - m.create_date ) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ";
  private static final String al9 = "SELECT m.queue_id, MIN( mi.read_date - m.create_date ), MAX( mi.read_date - m.create_date ) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ";
  private static final String aoj = "SELECT m.queue_id, MIN( mi.read_date - m.create_date ), MAX( mi.read_date - m.create_date ) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ";
  private static final String aou = "SELECT m.queue_id, mi.read_date, m.create_date FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ?";
  private static final String anu = "SELECT mi.read_date, m.create_date FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND q.queue_id = ?";
  private static final String amM = "SELECT m.queue_id, mi.read_date, m.create_date FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ";
  private static final String amR = "SELECT mi.read_date, m.create_date FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND q.queue_id = ?";
  private static final String ant = "SELECT m.queue_id, COUNT(m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND NOT EXISTS (SELECT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) ";
  private static final String akE = "SELECT COUNT(m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND NOT EXISTS (SELECT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) AND q.queue_id = ?";
  private static final String an9 = "SELECT m.queue_id, COUNT(m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND m.employee_id = ? AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND NOT EXISTS (SELECT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) ";
  private static final String alR = "SELECT COUNT(m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND m.employee_id = ? AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND NOT EXISTS (SELECT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) AND q.queue_id = ?";
  private static final String amf = "SELECT m.queue_id, COUNT(message_id) AS total FROM message m, queue q WHERE m.queue_id = q.queue_id AND m.status = 5 AND m.close_date >= ? AND m.close_date <= ? AND NOT EXISTS (SELECT message_id FROM message_item mi WHERE m.message_id = mi.message_id AND mi.message_type = 3) ";
  private static final String alh = "SELECT m.queue_id, COUNT(message_id) AS total FROM message m, queue q WHERE m.queue_id = q.queue_id AND m.employee_id = ? AND m.status = 5 AND m.close_date >= ? AND m.close_date <= ? AND NOT EXISTS (SELECT message_id FROM message_item mi WHERE m.message_id = mi.message_id AND mi.message_type = 3) ";
  private static final String ak8 = "SELECT m.queue_id, COUNT(m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND m.message_id IN (SELECT DISTINCT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) ";
  private static final String ald = "SELECT COUNT(m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND m.message_id IN (SELECT DISTINCT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) AND q.queue_id = ? ";
  private static final String akv = "SELECT m.queue_id, COUNT(m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND m.employee_id = ? AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND m.message_id IN (SELECT DISTINCT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) ";
  private static final String al7 = "SELECT COUNT(m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND m.employee_id = ? AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND m.message_id IN (SELECT DISTINCT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) AND q.queue_id = ? ";
  private static final String ann = "SELECT m.queue_id, COUNT(message_id) AS total FROM message m, queue q WHERE m.queue_id = q.queue_id AND m.employee_id = ? AND m.status = 5 AND m.close_date >= ? AND m.close_date <= ? AND message_id IN (SELECT DISTINCT message_id FROM message_item mi WHERE m.message_id = mi.message_id AND mi.message_type = 3 AND mi.from_id = ?) ";
  private static final String akI = "SELECT m.queue_id, COUNT(m.message_id) AS total FROM message m, queue q WHERE m.queue_id = q.queue_id AND m.employee_id != ? AND m.status = 5 AND m.close_date >= ? AND m.close_date <= ? AND message_id IN (SELECT DISTINCT message_id FROM message_item mi WHERE m.message_id = mi.message_id AND mi.message_type = 3 AND mi.to_id = ?) ";
  private static final String aot = "SELECT m.queue_id, COUNT(DISTINCT m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND m.employee_id != ? AND mi.message_type IN (3, 5) AND mi.create_date >= ? AND mi.create_date <= ? AND mi.from_id = ? AND m.message_id IN (SELECT DISTINCT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3 AND mi2.to_id = ?) ";
  private static final String alv = "SELECT COUNT(DISTINCT m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND m.employee_id != ? AND mi.message_type IN (3, 5) AND mi.create_date >= ? AND mi.create_date <= ? AND mi.from_id = ? AND m.message_id IN (SELECT DISTINCT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3 AND mi2.to_id = ?) AND q.queue_id = ?";
  private static final String an6 = "SELECT queue_id, queue_name FROM queue WHERE queue_type=0";
  private static final String anF = "SELECT queue_id, queue_name FROM queue_i18n WHERE";
  private static final String amq = "ORDER BY queue_name";
  private static final String amV = "select c1.directory_id from customer c1 where c1.affiliate_bank_id=";
  private static final String an1 = "select c2.directory_id from customer c2, business_employee be, business b where c2.customer_type='1' and c2.directory_id=be.directory_id and be.business_id=b.directory_id and b.affiliate_bank_id=";
  private static final String akU = "INSERT INTO GLOBAL_MSG_FILTERS (GLOBAL_MSG_ID, FILTER_TYPE, FILTER_VALUE) VALUES (?, ?, ?)";
  private static final String ank = "INSERT INTO GLOBAL_MSG_RCPTS (RECIPIENT_LIST_ID, RECIPIENT_TYPE, DIRECTORY_ID) VALUES(?, ?, ?)";
  private static final String ale = "SELECT FILTER_TYPE, FILTER_VALUE FROM GLOBAL_MSG_FILTERS WHERE GLOBAL_MSG_ID = ?";
  private static final String alk = "SELECT RCPT.RECIPIENT_LIST_ID, RCPT.RECIPIENT_TYPE, RCPT.DIRECTORY_ID, CUSTDIR.cust_id, BUS.business_name, BUS.affiliate_bank_id FROM GLOBAL_MSG_RCPTS RCPT, customer_directory CUSTDIR, business BUS  WHERE RCPT.RECIPIENT_LIST_ID = ? AND RECIPIENT_TYPE = 2 AND (CUSTDIR.directory_id = RCPT.DIRECTORY_ID AND BUS.directory_id = RCPT.DIRECTORY_ID)";
  private static final String alg = " SELECT RCPT.RECIPIENT_LIST_ID, RCPT.RECIPIENT_TYPE, RCPT.DIRECTORY_ID, CUSTDIR.cust_id, CUST.last_name, CUST.first_name, CUST.affiliate_bank_id, CUST.user_name   FROM GLOBAL_MSG_RCPTS RCPT, customer_directory CUSTDIR, customer CUST   WHERE RCPT.RECIPIENT_LIST_ID = ? AND RECIPIENT_TYPE = 1    AND (CUSTDIR.directory_id = RCPT.DIRECTORY_ID  AND CUST.directory_id = RCPT.DIRECTORY_ID)";
  private static final String akW = "DELETE FROM GLOBAL_MSG_FILTERS WHERE GLOBAL_MSG_ID = ?";
  private static final String amK = "DELETE FROM GLOBAL_MSG_RCPTS WHERE RECIPIENT_LIST_ID = ?";
  private static final String aoc = "RECIPIENT_LIST_ID";
  private static final int anB = 0;
  private static final int al0 = 1;
  private static final int an8 = 2;
  private static final int alH = 3;
  private static final int ak0 = 4;
  private static final int alG = 5;
  private static final int alm = 6;
  private static final int aok = 7;
  private static final int akX = 8;
  private static final int amN = 9;
  private static final int am4 = 10;
  private static final int aod = 11;
  private static final int anP = 12;
  private static final int al1 = 13;
  private static final int alQ = 14;
  private static final int amd = 15;
  private static final int aoo = 16;
  private static final int alO = 17;
  private static final int alj = 18;
  private static final int anz = 19;
  private static final int anj = 20;
  private static final int alD = 21;
  private static final int amn = 22;
  private boolean akT = false;
  private boolean alX = false;
  private boolean alI = false;
  private boolean aoh = false;
  private boolean akw = false;
  private boolean al8 = false;
  private boolean an4 = false;
  private boolean akz = false;
  private boolean amD = false;
  private boolean ang = false;
  private boolean amZ = false;
  private boolean amo = false;
  private boolean anT = false;
  private boolean alb = false;
  private boolean amx = false;
  private boolean am8 = false;
  private boolean amc = false;
  private boolean alr = false;
  private boolean aon = false;
  private boolean amG = false;
  private boolean anW = false;
  protected static final int FIELD_TO = 1;
  protected static final int FIELD_FROM = 2;
  private static final String alE = "0";
  private static final String alx = "1";
  private static final String akN = "INSERT into message_comment (comment_id,seq,text) values(?,?,?)";
  private static final String ano = "INSERT into message_item (item_id,message_id,body_id,comment_id,to_id,to_type,from_id,from_type,message_type,create_date) values (?,?,?,?,?,?,?,?,?,?)";
  private static final String aog = "INSERT into message_body (body_id,seq,text) values(?,?,?)";
  private static final String alw = "INSERT into message (message_id, employee_id, directory_id, queue_id, subject, status, create_date, bank_id, case_num) values (?,?,?,?,?,?,?,?,?)";
  private static final String al3 = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, customer_directory cd ";
  private static final String al6 = " WHERE m.directory_id = c.directory_id and m.directory_id = cd.directory_id";
  private static final String ak9 = " and m.queue_id!=0";
  private static final String anw = " and m.queue_id = ?";
  private static final String anV = "SELECT business_name FROM business";
  private static final String akJ = "SELECT c.first_name,c.last_name,c.email_address,c.user_name,cd.cust_id FROM customer c, customer_directory cd WHERE c.directory_id=cd.directory_id";
  private static final String anY = "SELECT first_name,last_name,email_address,user_name,bank_employee_id FROM bank_employee";
  private static final String and = "SELECT text FROM message_body where body_id = ? order by seq";
  private static final String akL = "SELECT text FROM message_comment where comment_id = ? order by seq";
  private static final String amW = " AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id )) ";
  private static final String akV = "SELECT mi.item_id, mi.message_id, mi.body_id, mi.comment_id, mi.to_id, mi.to_type, mi.from_id, mi.from_type, mi.create_date, mi.delete_date, mi.read_date, mi.message_type, m.subject, m.case_num, m.status FROM message m, message_item mi WHERE m.message_id=mi.message_id";
  private static final String amJ = "SELECT mi.item_id, mi.message_id, mi.body_id, mi.comment_id, mi.to_id, mi.to_type, mi.from_id, mi.from_type, mi.create_date, mi.delete_date, mi.read_date, mi.message_type, m.subject, m.case_num, m.status FROM message m, message_item mi WHERE m.message_id=mi.message_id and mi.delete_date is null";
  private static final String ak2 = "SELECT queue_name FROM queue";
  private static final String anN = "SELECT count(mi.item_id) AS MESSAGE_COUNT FROM message_item mi WHERE mi.read_date is null and mi.delete_date is null";
  private static final String akR = "SELECT count(mi.item_id) AS MESSAGE_COUNT FROM message_item mi WHERE mi.read_date is null and mi.delete_date is null and not mi.message_type = 8";
  private static final String alY = "UPDATE message_item SET read_date=?";
  private static final String akH = "UPDATE message_item SET delete_date=?";
  private static final String aoe = "SELECT COUNT( mi.item_id ) AS MESSAGE_COUNT FROM message_item mi WHERE mi.message_id = ? AND mi.to_id = ? AND mi.to_type = ? AND mi.read_date is null AND mi.delete_date is null";
  private static final String alt = "SELECT m.directory_id, mi.item_id, mi.from_id, mi.message_type FROM message_item mi, message m WHERE m.message_id = mi.message_id AND mi.item_id IN ( SELECT MAX( mi.item_id ) FROM message_item mi WHERE mi.message_id = ? )";
  private static final String amS = "SELECT MAX( mi.item_id ) FROM message_item mi WHERE mi.message_id = ?";
  private static final String amY = "SELECT COUNT( m.message_id ) AS MESSAGE_COUNT FROM message m WHERE ( NOT m.status = 5 )  AND ( m.employee_id = ? )";
  private static final String akK = "SELECT COUNT( m.message_id ) AS MESSAGE_COUNT FROM message m WHERE ( NOT m.status = 5 )  AND ( m.employee_id = ? ) AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id )) ";
  private static final String anI = "SELECT m.queue_id, COUNT( m.message_id ) AS MESSAGE_COUNT FROM message m WHERE ( NOT m.status = 5 )  AND m.employee_id = ? GROUP BY m.queue_id";
  private static final String alU = "SELECT m.queue_id, COUNT( m.message_id ) AS MESSAGE_COUNT FROM message m WHERE ( NOT m.status = 5 )  AND m.employee_id = ? AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id ))  GROUP BY m.queue_id";
  private static final String anl = "SELECT m.employee_id, COUNT( m.message_id ) AS MESSAGE_COUNT FROM message m WHERE ( NOT m.status = 5 )  AND m.queue_id = ? GROUP BY m.employee_id";
  private static final String akO = "SELECT count(distinct m.message_id) FROM message m, message_item mi WHERE m.employee_id != ? AND m.status != 5 AND mi.message_type = 3 AND mi.to_id = ? AND mi.to_type = 0 AND mi.delete_date is null AND m.message_id = mi.message_id";
  private static final String amr = "SELECT count(distinct m.message_id) FROM message m, message_item mi WHERE m.employee_id != ? AND m.status != 5 AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id ))  AND mi.message_type = 3 AND mi.to_id = ? AND mi.to_type = 0 AND mi.delete_date is null AND m.message_id = mi.message_id";
  private static final String alF = "SELECT  count(distinct m.message_id) FROM message m, message_item mi WHERE mi.message_id = m.message_id AND m.employee_id = ? AND (m.status = 5 OR m.status = 9 OR m.status = 12) AND mi.to_type = 1 AND mi.from_id != ? AND mi.message_type <> 9";
  private static final String aoi = "SELECT  count(distinct m.message_id) FROM message m, message_item mi WHERE mi.message_id = m.message_id AND m.employee_id = ? AND (m.status = 5 OR m.status = 9 OR m.status = 12) AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id ))  AND mi.to_type = 1 AND mi.from_id != ? AND mi.message_type <> 9";
  private static final String anJ = "SELECT q.queue_id, q.queue_name, COUNT( distinct m.message_id ) AS MESSAGE_COUNT FROM message m, queue q WHERE m.employee_id = ?  AND m.status = 1 AND q.queue_id = m.queue_id GROUP BY q.queue_id, q.queue_name";
  private static final String ala = "SELECT q.queue_id, q.queue_name, COUNT( distinct m.message_id ) AS MESSAGE_COUNT FROM message m, message_item mi, queue_i18n q WHERE m.employee_id = ?  AND m.status = 1 AND m.message_id = mi.message_id AND q.queue_id = m.queue_id GROUP BY q.queue_id, q.queue_name";
  private static final String alq = "SELECT q.queue_id, q.queue_name, COUNT( distinct m.message_id ) AS MESSAGE_COUNT FROM message m, queue q WHERE m.employee_id = ?  AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id ))  AND m.status = 1 AND q.queue_id = m.queue_id GROUP BY q.queue_id, q.queue_name";
  private static final String alc = "SELECT q.queue_id, q.queue_name, COUNT( distinct m.message_id ) AS MESSAGE_COUNT FROM message m, message_item mi, queue_i18n q WHERE m.employee_id = ?  AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id ))  AND m.status = 1 AND m.message_id = mi.message_id AND q.queue_id = m.queue_id GROUP BY q.queue_id, q.queue_name";
  private static final String akM = "SELECT c.first_name AS first_name, c.last_name AS last_name,c.user_name AS user_name, COUNT( m.message_id ) AS MESSAGE_COUNT, cd.cust_id AS CUSTID FROM customer c, message m, customer_directory cd WHERE c.personal_banker = ? AND c.directory_id = m.directory_id AND c.directory_id = cd.directory_id AND ( NOT m.status = 5 ) GROUP BY c.first_name, c.last_name, c.user_name, cd.cust_id";
  private static final String amw = "SELECT b.business_name AS NAME, COUNT( m.message_id ) AS MESSAGE_COUNT, cd.cust_id AS CUSTID FROM business b, business_employee be, message m, customer_directory cd WHERE b.personal_banker = ? AND b.business_id = be.business_id AND b.directory_id = cd.directory_id AND be.directory_id = m.directory_id AND ( NOT m.status = 5 ) GROUP BY b.business_name, cd.cust_id";
  private static final String amF = "SELECT c.first_name AS first_name, c.last_name AS last_name,c.user_name AS user_name, COUNT( m.message_id ) AS MESSAGE_COUNT, cd.cust_id AS CUSTID FROM customer c, message m, customer_directory cd WHERE c.personal_banker = ? AND c.affiliate_bank_id = ? AND c.directory_id = m.directory_id AND c.directory_id = cd.directory_id AND ( NOT m.status = 5 ) GROUP BY c.first_name, c.last_name, c.user_name, cd.cust_id";
  private static final String akP = "SELECT b.business_name AS NAME, COUNT( m.message_id ) AS MESSAGE_COUNT, cd.cust_id AS CUSTID FROM business b, business_employee be, message m, customer_directory cd WHERE b.personal_banker = ? AND b.affiliate_bank_id = ? AND b.business_id = be.business_id AND be.directory_id = m.directory_id AND b.directory_id = cd.directory_id AND ( NOT m.status = 5 ) GROUP BY b.business_name, cd.cust_id";
  private static final String ana = "SELECT be.bank_employee_id AS bank_employee_id, be.first_name AS first_name, be.last_name AS last_name, be.user_name AS user_name, COUNT( m.message_id ) AS MESSAGE_COUNT FROM bank_employee be, message m, message_item mi WHERE ( m.status = 9 OR m.status = 12 ) AND mi.to_id = ? AND mi.to_type = 0 AND mi.from_type = 0 AND mi.from_id = be.employee_id AND m.message_id = mi.message_id AND mi.item_id IN ( SELECT MAX( mi.item_id ) FROM message_item mi WHERE mi.to_id = ? AND mi.to_type = 0 AND mi.from_type = 0 GROUP BY mi.message_id) GROUP BY be.bank_employee_id, be.first_name, be.last_name, be.user_name";
  private static final String aln = "SELECT be.bank_employee_id AS bank_employee_id, be.first_name AS first_name, be.last_name AS last_name, be.user_name AS user_name, COUNT( m.message_id ) AS MESSAGE_COUNT FROM bank_employee be, message m, message_item mi WHERE ( m.status = 9 OR m.status = 12 ) AND mi.to_id = ? AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id ))  AND mi.to_type = 0 AND mi.from_type = 0 AND mi.from_id = be.employee_id AND m.message_id = mi.message_id AND mi.item_id IN ( SELECT MAX( mi.item_id ) FROM message_item mi WHERE mi.to_id = ? AND mi.to_type = 0 AND mi.from_type = 0 GROUP BY mi.message_id) GROUP BY be.bank_employee_id, be.first_name, be.last_name, be.user_name";
  private static final String anA = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, customer_directory cd WHERE m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND ( NOT m.status = 5 ) AND ( m.employee_id = ? )";
  private static final String ak6 = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, customer_directory cd WHERE m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND ( NOT m.status = 5 ) AND ( m.employee_id = ? ) AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id )) ";
  private static final String aop = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, message_item mi, customer_directory cd WHERE m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND mi.message_id = m.message_id AND m.employee_id = ? AND (m.status = 5 OR m.status = 9 OR m.status = 12) AND mi.to_type = 1 AND mi.from_id != ?";
  private static final String aly = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, message_item mi, customer_directory cd WHERE m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND mi.message_id = m.message_id AND m.employee_id = ? AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id ))  AND (m.status = 5 OR m.status = 9 OR m.status = 12) AND mi.to_type = 1 AND mi.from_id != ?";
  private static final String akD = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, message_item mi, customer_directory cd WHERE m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND m.employee_id != ?  AND m.status != 5 AND mi.message_type = 3 AND mi.delete_date is null AND mi.message_id = m.message_id AND mi.to_id = ? AND mi.to_type = 0";
  private static final String aol = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, message_item mi, customer_directory cd WHERE m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND m.employee_id != ?  AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id ))  AND m.status != 5 AND mi.message_type = 3 AND mi.delete_date is null AND mi.message_id = m.message_id AND mi.to_id = ? AND mi.to_type = 0";
  private static final String als = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, message_item mi, customer_directory cd WHERE m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND m.employee_id = ? AND m.status = 1";
  private static final String alf = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, message_item mi, customer_directory cd WHERE m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND m.employee_id = ? AND m.status = 1 AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id )) ";
  private static final String amC = "SELECT m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, customer_directory cd WHERE c.directory_id = m.directory_id AND c.directory_id = cd.directory_id AND ( NOT m.status = 5 )";
  private static final String amB = " UNION SELECT m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM business b, business_employee be, message m, customer c, customer_directory cd WHERE b.business_id = be.business_id AND be.directory_id = m.directory_id AND be.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND ( NOT m.status = 5 )";
  private static final String ans = "SELECT m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, customer_directory cd WHERE c.personal_banker = ? AND c.directory_id = m.directory_id AND c.directory_id = cd.directory_id AND ( NOT m.status = 5 ) UNION SELECT m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM business b, business_employee be, message m, customer c, customer_directory cd WHERE b.personal_banker = ? AND b.business_id = be.business_id AND be.directory_id = m.directory_id AND be.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND ( NOT m.status = 5 )";
  private static final String aob = "SELECT m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, customer_directory cd WHERE c.personal_banker = ? AND c.affiliate_bank_id = ? AND c.directory_id = m.directory_id AND c.directory_id = cd.directory_id AND ( NOT m.status = 5 ) UNION SELECT m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM business b, business_employee be, message m, customer c, customer_directory cd WHERE b.personal_banker = ? AND b.affiliate_bank_id = ? AND b.business_id = be.business_id AND be.directory_id = m.directory_id AND be.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND ( NOT m.status = 5 )";
  private static final String akF = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM message m, message_item mi, customer c, customer_directory cd WHERE ( m.status = 9 OR m.status = 12 ) AND mi.to_id = ? AND mi.to_type = 0 AND mi.from_type = 0 AND m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND m.message_id = mi.message_id AND mi.item_id IN ( SELECT MAX( mi.item_id )                     FROM message_item mi                     where mi.to_type = 0                     AND mi.from_type = 0                     GROUP BY mi.message_id)";
  private static final String alT = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM message m, message_item mi, customer c, customer_directory cd WHERE ( m.status = 9 OR m.status = 12 )  AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id ))  AND mi.to_id = ? AND mi.to_type = 0 AND mi.from_type = 0 AND m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND m.message_id = mi.message_id AND mi.item_id IN ( SELECT MAX( mi.item_id )                     FROM message_item mi                     where mi.to_type = 0                     AND mi.from_type = 0                     GROUP BY mi.message_id)";
  private static final String ami = "SELECT approval_provider FROM bank_employee WHERE employee_id = ?";
  private static final String aoq = "SELECT msg_approval_provider FROM bank_employee WHERE employee_id = ?";
  private static final String alN = "SELECT personal_banker FROM customer WHERE directory_id=? AND ( NOT personal_banker is null) AND ( NOT personal_banker = 0) UNION SELECT b.personal_banker FROM business b, business_employee be WHERE b.business_id = be.business_id AND ( NOT b.personal_banker is null) AND ( NOT b.personal_banker = 0) AND be.directory_id = ?";
  private static final String amL = "SELECT q.employee_id from queue_members q, bank_employee b where q.queue_id=? and b.employee_status=? and q.status=0 and q.employee_id = b.employee_id";
  private static final String anC = "SELECT q.employee_id from queue_members q, bank_employee b where q.queue_id=? and b.employee_status=? and q.status=0 and q.employee_id = b.employee_id and ( (b.affiliate_bank_id = ? or exists  (select afas.AFFILIATE_BANK_ID from AFFILIATE_ASSIGN afas where afas.EMPLOYEE_ID=b.employee_id and afas.AFFILIATE_BANK_ID=?))   or ( b.affiliate_bank_id = 0 and not exists (select afas.AFFILIATE_BANK_ID from AFFILIATE_ASSIGN afas where afas.EMPLOYEE_ID=b.employee_id and afas.AFFILIATE_BANK_ID=?)) )";
  private static final String akA = "select c.affiliate_bank_id from customer c where c.directory_id=? and c.customer_type='2' UNION select b.affiliate_bank_id from customer c, business_employee be, business b where c.directory_id=? and c.customer_type='1' and c.directory_id=be.directory_id and be.business_id=b.directory_id";
  private static final String al5 = "SELECT employee_status, supervisor FROM bank_employee WHERE employee_id=?";
  private static final String anv = "INSERT into global_message (global_msg_id,to_group_id,to_group_name,from_id,from_name,subject,global_body_id,create_date,status,approval_id,approved_date,bank_id,affiliate_bank_id,record_type,msg_type,to_group_type,color,priority,display_from_date,display_to_date,template_name, send_now)values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  private static final String alP = "INSERT into global_message_i18n (to_group_name, from_name, subject,global_msg_id, language) VALUES (?,?,?,?,?)";
  private static final String am1 = "INSERT into global_msg_body (global_body_id,seq,text) values(?,?,?)";
  private static final String aov = "INSERT into global_msg_body_i18n (global_body_id,seq,text,language) values(?,?,?,?)";
  private static final String ani = "UPDATE global_message set  to_group_id=?,to_group_name=?,from_id=?,from_name=?,subject=?,global_body_id=?,create_date=?,status=?,approval_id=?,approved_date=?,bank_id=?,affiliate_bank_id=?,record_type=?,msg_type=?,to_group_type=?,color=?,priority=?,display_from_date=?,display_to_date=?,template_name=?, send_now=? WHERE global_msg_id=?";
  private static final String ams = "UPDATE global_message_i18n set to_group_name=?,from_name=?,subject=? WHERE global_msg_id=? and language=?";
  private static final String anU = "UPDATE global_message set status=? WHERE global_msg_id=?";
  private static final String anp = "DELETE from global_msg_body where global_body_id=?";
  private static final String ame = "DELETE from global_msg_body_i18n where global_body_id=?";
  private static final String akQ = "SELECT gm.global_msg_id, gm.to_group_id, gm.to_group_name, gm.from_id, gm.from_name, gm.subject, gm.global_body_id, gm.create_date, gm.status, gm.approval_id, gm.approved_date, gm.bank_id, gm.affiliate_bank_id, gm.record_type, gm.msg_type, gm.to_group_type, gm.color, gm.priority, gm.display_from_date, gm.display_to_date, gm.template_name, gm.send_now FROM global_message gm";
  private static final String anL = "SELECT gm.global_msg_id, gm.to_group_id, gm.to_group_name, gm.from_id, gm.from_name, gm.subject, gm.global_body_id, gm.create_date, gm.status, gm.approval_id, gm.approved_date, gm.bank_id, gm.affiliate_bank_id, gm.record_type, gm.msg_type, gm.to_group_type, gm.color, gm.priority, gm.display_from_date, gm.display_to_date, gm.template_name, gm.send_now FROM global_message gm left outer join global_message_i18n gmI18N on gm.global_msg_id=gmI18N.global_msg_id ";
  private static final String amm = "SELECT gm.global_msg_id, gm.language, gm.to_group_name, gm.from_name, gm.subject FROM global_message_i18n gm";
  private static final String ak4 = "SELECT gm.global_msg_id, gm.to_group_id, gm.to_group_name, gm.from_id, gm.from_name, gm.subject, gm.global_body_id, gm.create_date, gm.status, gm.approval_id, gm.approved_date, gm.bank_id, gm.affiliate_bank_id, gm.record_type, gm.msg_type, gm.to_group_type, gm.color, gm.priority, gm.display_from_date, gm.display_to_date, gm.template_name, gm.send_now FROM global_message gm WHERE gm.global_msg_id=?";
  private static final String aml = "SELECT text FROM global_msg_body where global_body_id=?";
  private static final String alB = "SELECT text FROM global_msg_body_i18n where global_body_id=? AND language=?";
  private static final String amg = "UPDATE message_item SET message_type=?";
  private static final String anK = "UPDATE message m SET m.status = ? WHERE m.message_id = ?";
  private static final String am5 = ",business b, business_employee be ";
  private static final String amv = "(c.directory_id = be.directory_id) and (be.business_id = b.business_id)";
  private static final String amP = "ORDER BY mi.create_date desc, mi.item_id desc";
  private static final String anr = "m.employee_id=?";
  private static final String amk = "((m.employee_id=?) or (m.employee_id in (select employee_id from bank_employee where supervisor=?)))";
  private static final String amu = "select * from message where message_id=? order by create_date desc";
  private static String anE = "update message set modified_date=?";
  private static final String amQ = "select * from message_item where item_id=?";
  private static final String anG = "select * from message_item where message_id=? order by create_date desc, message_type desc";
  private static final String alS = "select count(mi.item_id) from message m, message_item mi where m.message_id=? and  m.message_id = mi.message_id and mi.read_date is null and mi.delete_date is null and mi.to_id=? and mi.to_type=?";
  private static final String alM = "select message_id from message where directory_id=?";
  private static final String akG = "select item_id from message_item where (from_id=? and from_type=?) or (to_id=? and to_type=?)";
  private static final String amX = "select message_id from message where employee_id=? ";
  private static final String amt = "select m.subject,m.case_num,m.status,m.close_date,mi.* from message m, message_item mi where m.message_id=mi.message_id and mi.delete_date is null";
  private static final String amE = " and mi.to_id=? and mi.to_type=1";
  private static final String aos = " and mi.from_id=? and mi.from_type=1";
  private static final String alu = " and ((mi.to_id=? and mi.to_type=1) or (mi.from_id=? and mi.from_type=1))";
  private static final String an0 = "select distinct m.*,c.email_address,c.first_name,c.last_name, c.user_name from customer c, message m, message_item mi where m.message_id = mi.message_id and m.directory_id = c.directory_id";
  private static final String anm = "select count (distinct m.message_id) from message m";
  private static final String anb = "select c.directory_id from customer c, customer_directory cd where c.directory_id = cd.directory_id andc.bank_id=? andcd.account_status=1";
  private static final String akS = "SELECT COUNT(message_id) FROM message WHERE employee_id=? AND ( NOT status = 9 ) AND ( NOT status = 12 ) AND ( NOT status = 5 )";
  private static final String am0 = "select autoreply_text from queue where queue_id=?";
  private static final String an5 = "select autoreply_text from queue_i18n where queue_id=? and language=?";
  private static final String alp = "update message_item set delete_date = ? where message_id = ? and to_id = ? and message_type = 3 and to_type = 0";
  private static final String amy = "RPT_STMTCASESNEW";
  private static final String aky = "RPT_STMTCASESOPENED";
  private static final String ak7 = "RPT_STMTCASESOPEN";
  private static final String aor = "RPT_STMTCASESOPENQUEUEID";
  private static final String akY = "RPT_STMTTIMETOOPENAVG";
  private static final String ak3 = "RPT_STMTTIMETOOPENRANGE";
  private static final String aoa = "RPT_STMTTIMETOOPENSTDDEVIATION";
  private static final String anc = "RPT_STMTTIMETOOPENSTDDEVIATIONBYQUEUEID";
  private static final String amA = "RPT_STMTSINGLEAGENTINPROG";
  private static final String anf = "RPT_STMTSINGLEAGENTINPROGQUEUEID";
  private static final String am6 = "RPT_STMTSINGLEAGENTCLOSED";
  private static final String ak5 = "RPT_STMTHELPREQINPROG";
  private static final String anD = "RPT_STMTHELPREQINPROGQUEUEID";
  private static final String alo = "RPT_STMTHELPREQCLOSED";
  private static final String amT = "RPT_STMTHELPPROVIDED";
  private static final String amb = "RPT_STMTHELPPROVIDEDQUEUEID";
  private static final String am2 = "RPT_STMTHELPPROVIDEDCLOSED";
  private ArrayList amH = new ArrayList();
  private b alW = new b(null);
  private static final String aof = "userKey";
  private static final String al2 = "retrieveMessageFiltersRecipients(GlobalMessage globalMessage)";
  private static final String akB = "com.ffusion.beans.messages.resources";
  private static final String am9 = "HELP_CASE_DISPLAY_HELP_REQUESTED";
  private static final String anx = "HELP_CASE_DISPLAY_ASSISTANCE_PROVIDED";
  
  public MessageAdapter()
  {
    this.alW.start();
  }
  
  public Message sendMessage(SecureUser paramSecureUser, Message paramMessage)
    throws ProfileException
  {
    Profile.isInitialized();
    return addMessage(paramSecureUser, null, paramMessage);
  }
  
  public MessageThread sendMessageThread(SecureUser paramSecureUser, Message paramMessage)
    throws ProfileException
  {
    Profile.isInitialized();
    MessageThread localMessageThread = new MessageThread();
    addMessage(paramSecureUser, localMessageThread, paramMessage);
    return localMessageThread;
  }
  
  public void sendReply(SecureUser paramSecureUser, Message paramMessage)
    throws ProfileException
  {
    Profile.isInitialized();
    addMessage(paramSecureUser, null, paramMessage);
  }
  
  public void sendInternalQuery(SecureUser paramSecureUser, Message paramMessage, MessageThread paramMessageThread)
    throws ProfileException
  {
    Profile.isInitialized();
    paramMessage.put("MESSAGE_ID", paramMessageThread.getThreadID());
    addMessage(paramSecureUser, null, paramMessage);
  }
  
  protected Message addMessage(SecureUser paramSecureUser, MessageThread paramMessageThread, Message paramMessage)
    throws ProfileException
  {
    String str1 = "MessageAdapter.addMessage";
    Connection localConnection = null;
    int i = 0;
    String str2 = paramSecureUser.getBankID();
    if (paramMessage.get("BANK_ID") != null) {
      str2 = (String)paramMessage.get("BANK_ID");
    }
    if (!Profile.isValidId(str2)) {
      Profile.handleError(str1, "Invalid Bank Id", 4022);
    }
    int j = paramMessage.getFromTypeValue();
    if ((j != 0) && (j != 1)) {
      Profile.handleError(str1, "Invalid from Type", 3815);
    }
    int k = Profile.convertToInt(paramMessage.getFrom());
    if (!Profile.isValidId(k))
    {
      k = paramSecureUser.getProfileID();
      j = mapMessageFromTypeFromUserType(paramSecureUser);
      if (!Profile.isValidId(k)) {
        Profile.handleError(str1, "Invalid from Id", 3814);
      }
    }
    int m = Profile.convertToInt(paramMessage.getTo());
    int n = paramMessage.getToTypeValue();
    if ((n != 0) && (n != 1) && (n != 2)) {
      Profile.handleError(str1, "Invalid toType", 3816);
    }
    int i1 = Profile.convertToInt((String)paramMessage.get("MESSAGE_ID"));
    if (!Profile.isValidId(i1)) {
      i = 1;
    }
    int i2 = paramMessage.getTypeValue();
    if (i2 <= 0) {
      if (i == 1) {
        i2 = 1;
      } else {
        Profile.handleError(str1, "Invalid Message Type", 3817);
      }
    }
    String str3 = paramMessage.getMemo();
    if ((i == 1) && ((str3 == null) || (str3.trim().length() == 0))) {
      Profile.handleError(str1, "Message text is required.", 3818);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      if (i == 0)
      {
        i3 = mapThreadStatusFromMessageType(i2);
        if (i3 == 6) {
          i3 = -1;
        }
        modifyMessageThreadStatus(localConnection, i1, i3);
        if ((str3 == null) || (str3.trim().length() == 0))
        {
          DBUtil.commit(localConnection);
          Message localMessage1 = paramMessage;
          return localMessage1;
        }
      }
      int i3 = addMessageBody(localConnection, str3);
      int i4 = 0;
      if ((paramMessage.getComment() != null) && (paramMessage.getComment().trim().length() > 0)) {
        i4 = addMessageComment(localConnection, paramMessage.getComment());
      }
      int i5 = 0;
      if (i == 1)
      {
        int i6 = 0;
        int i7 = 0;
        if (j == 0)
        {
          i6 = k;
        }
        else if (n == 0)
        {
          i6 = m;
        }
        else if (n == 2)
        {
          i5 = m;
          i6 = routeMessage(localConnection, i5, j == 1 ? k : 0);
          m = i6;
          n = 0;
        }
        else
        {
          Profile.handleError(str1, "To or from must be an employee", 3);
        }
        if (j == 1) {
          i7 = k;
        } else if (n == 1) {
          i7 = m;
        } else {
          Profile.handleError(str1, "To or from must be a customer", 3);
        }
        String str4 = paramMessage.getSubject();
        if ((str4 == null) || (str4.length() == 0)) {
          str4 = "none";
        }
        String str5 = paramMessage.getCaseNum();
        if ((str5 == null) || (str5.length() == 0)) {
          str5 = null;
        }
        int i8 = 1;
        if ((i2 == 8) || (j == 0)) {
          i8 = 5;
        }
        MessageThread localMessageThread = addNewMessage(localConnection, str2, i5, i6, i7, str4, i8, str5);
        i1 = Profile.convertToInt(localMessageThread.getThreadID());
        paramMessage.set("MESSAGE_ID", localMessageThread.getThreadID());
        paramMessage.setMsgThreadID(localMessageThread.getThreadID());
        paramMessage.setCaseNum(localMessageThread.getThreadID());
        if (paramMessageThread != null) {
          paramMessageThread.set(localMessageThread);
        }
      }
      Message localMessage2 = addMessageItem(localConnection, i1, i2, i3, i4, m, n, k, j);
      paramMessage.setID(localMessage2.getID());
      if ((i == 1) && (j == 1) && (i5 != 0)) {
        messageAutoReply(localConnection, i1, i5, k, m);
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, null);
    }
    return paramMessage;
  }
  
  public Message sendInternalMessage(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage)
    throws ProfileException
  {
    String str = "MessageAdapter.sendInternalMessage";
    Connection localConnection = null;
    int i = paramMessage.getTypeValue();
    int j = -1;
    if (i == 5)
    {
      paramMessage.setToType(String.valueOf(1));
      j = 5;
    }
    else if (i == 3)
    {
      paramMessage.setToType(String.valueOf(0));
      j = 3;
    }
    else
    {
      Profile.handleError(str, "Invalid Message Type", 3);
    }
    int k = 0;
    int m = 0;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      if ((paramMessage.getMemo() != null) && (paramMessage.getMemo().trim().length() > 0)) {
        k = addMessageBody(localConnection, paramMessage.getMemo());
      }
      if ((paramMessage.getComment() != null) && (paramMessage.getComment().trim().length() > 0)) {
        m = addMessageComment(localConnection, paramMessage.getComment());
      }
      if (j == 5)
      {
        n = msgApprovalRequired(localConnection, paramSecureUser.getProfileID());
        if (n == -1) {
          Profile.handleError(str, "Could not find ID of approval employee.", 3);
        }
        if (n != 0)
        {
          j = 9;
          paramMessage.setTo(n);
          paramMessage.setToType(String.valueOf(0));
        }
      }
      addMessage(localConnection, paramMessage, k, m);
      int n = Profile.convertToInt(paramMessage.getMsgThreadID());
      modifyMessageThreadStatus(localConnection, n, j);
      jdMethod_for(localConnection, paramSecureUser, paramMessage);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, null);
    }
    return paramMessage;
  }
  
  private static void jdMethod_for(Connection paramConnection, SecureUser paramSecureUser, Message paramMessage)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      int i = Integer.parseInt(paramMessage.getMsgThreadID());
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select * from message where message_id=? order by create_date desc");
      localPreparedStatement.setInt(1, i);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from message where message_id=? order by create_date desc");
      int j = 0;
      if (localResultSet.next()) {
        j = localResultSet.getInt("employee_id");
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      localResultSet = null;
      if (j != paramSecureUser.getProfileID())
      {
        Timestamp localTimestamp = DBUtil.getCurrentTimestamp();
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "update message_item set delete_date = ? where message_id = ? and to_id = ? and message_type = 3 and to_type = 0");
        localPreparedStatement.setTimestamp(1, localTimestamp);
        localPreparedStatement.setInt(2, i);
        localPreparedStatement.setInt(3, paramSecureUser.getProfileID());
        DBUtil.executeUpdate(localPreparedStatement, "update message_item set delete_date = ? where message_id = ? and to_id = ? and message_type = 3 and to_type = 0");
      }
    }
    catch (Exception localException)
    {
      throw localException;
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  public Message sendReplyMessage(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
    throws ProfileException
  {
    String str1 = "MessageAdapter.sendReplyMessage";
    Connection localConnection = null;
    int i = paramMessage.getTypeValue();
    int j = -1;
    if (i == 4) {
      j = 4;
    } else if (i == 5) {
      j = 5;
    } else {
      Profile.handleError(str1, "Invalid Message Type", 3);
    }
    if ((paramMessage.getMemo() == null) || (paramMessage.getMemo().length() == 0)) {
      Profile.handleError(str1, "Invalid memo", 3);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      int k = 0;
      k = addMessageBody(localConnection, paramMessage.getMemo());
      int m = 0;
      if ((paramMessage.getComment() != null) && (paramMessage.getComment().trim().length() > 0)) {
        m = addMessageComment(localConnection, paramMessage.getComment());
      }
      int n = msgApprovalRequired(localConnection, paramSecureUser.getProfileID());
      if (n == -1) {
        Profile.handleError(str1, "Could not find ID of approval employee.", 3);
      }
      if (n != 0)
      {
        if (paramBoolean) {
          j = 12;
        } else {
          j = 9;
        }
        paramMessage.setTo(n);
        paramMessage.setToType(String.valueOf(0));
      }
      addMessage(localConnection, paramMessage, k, m);
      int i1 = Profile.convertToInt(paramMessage.getMsgThreadID());
      modifyMessageThreadStatus(localConnection, i1, j);
      jdMethod_for(localConnection, paramSecureUser, paramMessage);
      DBUtil.commit(localConnection);
      if ((paramBoolean) && (j != 12) && (j != 9))
      {
        HashMap localHashMap = new HashMap();
        getCustomerNameByID(localConnection, Profile.convertToInt(paramMessage.getTo()), paramMessage.getLocale(), localHashMap);
        String str2 = (String)localHashMap.get("EMAIL_ADDRESS");
        if (str2 == null) {
          DebugLog.log(Level.INFO, str1 + ": An email notification could not be sent " + "because the recipient's email address is missing.");
        } else {
          com.ffusion.csil.handlers.Messages._mailService.sendMessage(str2, paramString1, paramString2, paramString3);
        }
      }
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, null);
    }
    return paramMessage;
  }
  
  protected MessageThread addNewMessage(Connection paramConnection, String paramString1, int paramInt1, int paramInt2, int paramInt3, String paramString2, int paramInt4, String paramString3)
    throws Exception
  {
    MessageThread localMessageThread = new MessageThread();
    localMessageThread.setQueueID(String.valueOf(paramInt1));
    localMessageThread.setEmployeeID(String.valueOf(paramInt2));
    localMessageThread.setDirectoryID(String.valueOf(paramInt3));
    localMessageThread.setSubject(paramString2);
    localMessageThread.setThreadStatus(String.valueOf(paramInt4));
    localMessageThread.setCaseNum(paramString3);
    localMessageThread.set("BANK_ID", paramString1);
    addMessageThread(paramConnection, localMessageThread, paramString1);
    return localMessageThread;
  }
  
  protected Message addMessageItem(Connection paramConnection, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
    throws Exception
  {
    Message localMessage = new Message();
    localMessage.setMsgThreadID(String.valueOf(paramInt1));
    localMessage.setTo(String.valueOf(paramInt5));
    localMessage.setToType(String.valueOf(paramInt6));
    localMessage.setFrom(String.valueOf(paramInt7));
    localMessage.setFromType(String.valueOf(paramInt8));
    localMessage.setType(String.valueOf(paramInt2));
    return addMessage(paramConnection, localMessage, paramInt3, paramInt4);
  }
  
  public MessageThread getMessageThread(SecureUser paramSecureUser, int paramInt)
    throws ProfileException
  {
    String str = "MessageAdapter.getMessageThread";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    if (!Profile.isValidId(paramInt)) {
      Profile.handleError(str, "Invalid Message Id", 4);
    }
    int i = paramSecureUser.getProfileID();
    MessageThread localMessageThread = new MessageThread();
    Locale localLocale = localMessageThread.getLocale();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from message where message_id=? order by create_date desc");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from message where message_id=? order by create_date desc");
      if (localResultSet.next() == true)
      {
        localMessageThread.setThreadID(String.valueOf(localResultSet.getInt("message_id")));
        localMessageThread.set("BANK_ID", Profile.getRSString(localResultSet, "bank_id"));
        int j = localResultSet.getInt("employee_id");
        localMessageThread.setEmployeeID(String.valueOf(j));
        int k = localResultSet.getInt("directory_id");
        localMessageThread.setDirectoryID(String.valueOf(k));
        int m = localResultSet.getInt("queue_id");
        localMessageThread.setQueueID(String.valueOf(m));
        localMessageThread.setSubject(Profile.getRSString(localResultSet, "subject"));
        localMessageThread.setThreadStatus(String.valueOf(localResultSet.getInt("status")));
        localMessageThread.setCreateDate(localResultSet.getTimestamp("create_date"));
        localMessageThread.setClosedDate(Profile.convertSqlTimestamp(localResultSet.getTimestamp("close_date")));
        localMessageThread.put("MODIFIED_DATE", localResultSet.getTimestamp("modified_date"));
        localMessageThread.setEmployeeName(getIdName(localConnection, j, 0, localLocale, null));
        HashMap localHashMap = new HashMap();
        localMessageThread.setDirectoryName(getIdName(localConnection, k, 1, localLocale, localHashMap));
        localMessageThread.setDirectoryEmail((String)localHashMap.get("EMAIL_ADDRESS"));
        localMessageThread.setQueueName(getIdName(localConnection, m, 2, localLocale, null));
        localMessageThread.setMessages(getMessageItems(localConnection, paramInt, i, 0, paramSecureUser));
        if ((Profile.convertToInt(localMessageThread.getThreadStatus()) == 1) && (j == i)) {
          modifyMessageThreadStatus(localConnection, paramInt, 2);
        }
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
    return localMessageThread;
  }
  
  public MessageThreads getMessageThreads(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "MessageAdapter.getMessageThreads";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("select distinct m.*,c.email_address,c.first_name,c.last_name, c.user_name from customer c, message m, message_item mi where m.message_id = mi.message_id and m.directory_id = c.directory_id");
    MessageThreads localMessageThreads = new MessageThreads(Locale.getDefault());
    String str2 = paramSecureUser.getBankID();
    if ((paramHashMap != null) && (paramHashMap.get("BANK_ID") != null)) {
      str2 = (String)paramHashMap.get("BANK_ID");
    }
    if ((str2 == null) || (str2.length() == 0)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    int i = paramSecureUser.getProfileID();
    java.sql.Date localDate1 = null;
    java.sql.Date localDate2 = null;
    int j = -1;
    int k = 0;
    if (paramHashMap != null)
    {
      localDate1 = Profile.convertToDate(paramHashMap.get("FROM_DATE"));
      localDate2 = Profile.getNextDay(Profile.convertToDate(paramHashMap.get("TO_DATE")));
      str3 = (String)paramHashMap.get("STATUS");
      if (str3 != null) {
        j = Profile.convertToInt(str3);
      }
      str3 = (String)paramHashMap.get("ALL");
      if ((str3 != null) && (str3.equalsIgnoreCase("true"))) {
        k = 1;
      }
    }
    String str3 = "";
    if (j == 0) {
      str3 = "";
    } else if (j != -1) {
      str3 = " and m.status=" + j;
    } else if ((localDate1 == null) && (localDate2 == null)) {
      str3 = " AND ( NOT m.status = 5 )  AND ( NOT m.status = 9 ) AND ( NOT m.status = 12 )";
    } else {
      str3 = "";
    }
    if (!str2.equals("*")) {
      Profile.appendSQLSelectString(localStringBuffer, "m.", "bank_id", str2, false, false, true, false);
    }
    if (k == 0)
    {
      localStringBuffer.append(" and (((m.employee_id=?" + str3 + ")");
      localStringBuffer.append(" or (mi.to_id=? and mi.to_type=0" + str3 + "))");
      localStringBuffer.append(" or (m.employee_id in (select employee_id from bank_employee where supervisor=?)" + str3 + "))");
    }
    else
    {
      localStringBuffer.append(str3);
    }
    Profile.appendSQLSelectDate(localStringBuffer, "m.", "create_date", localDate1, ">=", true);
    Profile.appendSQLSelectDate(localStringBuffer, "m.", "create_date", localDate2, "<=", true);
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int m = 1;
      if (!str2.equals("*")) {
        m = Profile.setStatementString(localPreparedStatement, m, str2, "bank_id", false, false, true, false);
      }
      if (k == 0)
      {
        localPreparedStatement.setInt(m++, i);
        localPreparedStatement.setInt(m++, i);
        localPreparedStatement.setInt(m++, i);
      }
      m = Profile.setStatementDate(localPreparedStatement, m, localDate1, true);
      m = Profile.setStatementDate(localPreparedStatement, m, localDate2, true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next() == true)
      {
        MessageThread localMessageThread = localMessageThreads.create();
        localMessageThread.setThreadID(String.valueOf(localResultSet.getInt("message_id")));
        localMessageThread.set("BANK_ID", Profile.getRSString(localResultSet, "bank_id"));
        int n = localResultSet.getInt("employee_id");
        localMessageThread.setEmployeeID(String.valueOf(n));
        int i1 = localResultSet.getInt("directory_id");
        localMessageThread.setDirectoryID(String.valueOf(i1));
        int i2 = localResultSet.getInt("queue_id");
        localMessageThread.setQueueID(String.valueOf(i2));
        localMessageThread.setSubject(Profile.getRSString(localResultSet, "subject"));
        localMessageThread.setThreadStatus(String.valueOf(localResultSet.getInt("status")));
        localMessageThread.setCreateDate(localResultSet.getTimestamp("create_date"));
        localMessageThread.setClosedDate(Profile.convertSqlTimestamp(localResultSet.getTimestamp("close_date")));
        localMessageThread.put("MODIFIED_DATE", localResultSet.getTimestamp("modified_date"));
        localMessageThread.setDirectoryEmail(localResultSet.getString("email_address"));
        localMessageThread.setDirectoryName(buildDirectoryName(Profile.getRSString(localResultSet, "first_name"), Profile.getRSString(localResultSet, "last_name"), Profile.getRSString(localResultSet, "user_name")));
        if (k == 0) {
          localMessageThread.setNewThreadStatus(String.valueOf(getUnreadItemCount(localConnection, localResultSet.getInt("message_id"), i)));
        }
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localMessageThreads;
  }
  
  public String getMessageThreadsCount(SecureUser paramSecureUser, MessageThreads paramMessageThreads, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "MessageAdapter.getMessageThreadsCount";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("select count (distinct m.message_id) from message m");
    String str2 = null;
    String str3 = paramSecureUser.getBankID();
    if ((str3 == null) || (str3.length() == 0)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    int i = -1;
    String str4 = null;
    if (paramMessageThreads.size() > 0)
    {
      localObject1 = (MessageThread)paramMessageThreads.get(0);
      str4 = ((MessageThread)localObject1).getThreadStatus();
    }
    if (str4 != null) {
      i = Profile.convertToInt(str4);
    }
    Object localObject1 = "";
    if (i > 0) {
      localObject1 = " and m.status=" + i;
    }
    Profile.appendSQLSelectString(localStringBuffer, "m.", "bank_id", str3, false, false, false);
    localStringBuffer.append((String)localObject1);
    int j = paramSecureUser.getAffiliateIDValue();
    if (j > 0) {
      localStringBuffer.append(" AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id )) ");
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int k = 1;
      k = Profile.setStatementString(localPreparedStatement, k, str3, "bank_id", true);
      if (j > 0)
      {
        localPreparedStatement.setInt(k++, j);
        localPreparedStatement.setInt(k++, j);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next() == true) {
        str2 = String.valueOf(localResultSet.getInt(1));
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return str2;
  }
  
  public com.ffusion.beans.messages.Messages getMessageList(SecureUser paramSecureUser, boolean paramBoolean1, boolean paramBoolean2)
    throws ProfileException
  {
    String str1 = "MessageAdapter.getMessageList";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str2 = paramSecureUser.getBankID();
    int i = paramSecureUser.getProfileID();
    com.ffusion.beans.messages.Messages localMessages = new com.ffusion.beans.messages.Messages(paramSecureUser.getLocale());
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("select m.subject,m.case_num,m.status,m.close_date,mi.* from message m, message_item mi where m.message_id=mi.message_id and mi.delete_date is null");
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      if ((paramBoolean1) && (!paramBoolean2)) {
        localStringBuffer.append(" and mi.to_id=? and mi.to_type=1");
      } else if ((paramBoolean2) && (!paramBoolean1)) {
        localStringBuffer.append(" and mi.from_id=? and mi.from_type=1");
      } else {
        localStringBuffer.append(" and ((mi.to_id=? and mi.to_type=1) or (mi.from_id=? and mi.from_type=1))");
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int j = 1;
      if (paramBoolean1) {
        localPreparedStatement.setInt(j++, i);
      }
      if (paramBoolean2) {
        localPreparedStatement.setInt(j++, i);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      HashMap localHashMap1 = new HashMap();
      HashMap localHashMap2 = new HashMap();
      Locale localLocale = jdMethod_for(paramSecureUser, null);
      while (localResultSet.next() == true)
      {
        Message localMessage = localMessages.createNoAdd();
        localMessage.setID(String.valueOf(localResultSet.getInt("item_id")));
        localMessage.setSubject(Profile.getRSString(localResultSet, "subject"));
        localMessage.setDate(localResultSet.getTimestamp("create_date"));
        localMessage.setArchivedDate(localResultSet.getTimestamp("delete_date"));
        localMessage.setReadDate(localResultSet.getTimestamp("read_date"));
        localMessage.setMemo(getMessageBody(localConnection, localResultSet.getInt("body_id")));
        localMessage.setType(localResultSet.getInt("message_type"));
        localMessage.set("MESSAGE_ID", String.valueOf(localResultSet.getInt("message_id")));
        localMessage.set("STATUS", String.valueOf(localResultSet.getInt("status")));
        localMessage.set("CLOSE_DATE", Profile.convertSqlTimestamp(localResultSet.getTimestamp("close_date")));
        localMessage.setCaseNum(Profile.getRSString(localResultSet, "case_num"));
        int k = localResultSet.getInt("to_id");
        int m = localResultSet.getInt("to_type");
        setToOrFromFields(localConnection, k, m, localMessage, localHashMap1, localHashMap2, 1, paramSecureUser.getLocale());
        int n = localResultSet.getInt("from_id");
        int i1 = localResultSet.getInt("from_type");
        if (i1 == 3) {
          setToOrFromFields(localConnection, n, i1, localMessage, localHashMap1, localHashMap2, 2, localLocale);
        } else {
          setToOrFromFields(localConnection, n, i1, localMessage, localHashMap1, localHashMap2, 2, paramSecureUser.getLocale());
        }
        localMessages.add(localMessage);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localMessages;
  }
  
  public com.ffusion.beans.messages.Messages getMessageList(SecureUser paramSecureUser)
    throws ProfileException
  {
    return getMessageList(paramSecureUser, true, true);
  }
  
  protected int getUnreadItemCount(Connection paramConnection, int paramInt1, int paramInt2)
    throws Exception
  {
    int i = 0;
    ResultSet localResultSet = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select count(mi.item_id) from message m, message_item mi where m.message_id=? and  m.message_id = mi.message_id and mi.read_date is null and mi.delete_date is null and mi.to_id=? and mi.to_type=?");
      localPreparedStatement.setInt(1, paramInt1);
      localPreparedStatement.setInt(2, paramInt2);
      localPreparedStatement.setInt(3, 0);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(mi.item_id) from message m, message_item mi where m.message_id=? and  m.message_id = mi.message_id and mi.read_date is null and mi.delete_date is null and mi.to_id=? and mi.to_type=?");
      if (localResultSet.next() == true) {
        i = localResultSet.getInt(1);
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return i;
  }
  
  protected com.ffusion.beans.messages.Messages getMessageItems(Connection paramConnection, int paramInt1, int paramInt2, int paramInt3, Locale paramLocale)
    throws Exception
  {
    ResultSet localResultSet = null;
    com.ffusion.beans.messages.Messages localMessages = new com.ffusion.beans.messages.Messages();
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select * from message_item where message_id=? order by create_date desc, message_type desc");
      localPreparedStatement.setInt(1, paramInt1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from message_item where message_id=? order by create_date desc, message_type desc");
      HashMap localHashMap1 = new HashMap();
      HashMap localHashMap2 = new HashMap();
      while (localResultSet.next() == true)
      {
        Message localMessage = localMessages.create();
        int n = localResultSet.getInt("item_id");
        localMessage.setID(String.valueOf(n));
        localMessage.set("MESSAGE_ID", String.valueOf(localResultSet.getInt("message_id")));
        int i = localResultSet.getInt("to_id");
        int j = localResultSet.getInt("to_type");
        setToOrFromFields(paramConnection, i, j, localMessage, localHashMap1, localHashMap2, 1, paramLocale);
        int k = localResultSet.getInt("from_id");
        int m = localResultSet.getInt("from_type");
        setToOrFromFields(paramConnection, k, m, localMessage, localHashMap1, localHashMap2, 2, paramLocale);
        localMessage.setDate(Profile.convertSqlTimestamp(localResultSet.getTimestamp("create_date")));
        localMessage.setArchivedDate(localResultSet.getTimestamp("delete_date"));
        Timestamp localTimestamp = localResultSet.getTimestamp("read_date");
        localMessage.setReadDate(localTimestamp);
        localMessage.setType(localResultSet.getInt("message_type"));
        localMessage.setMemo(getMessageBody(paramConnection, localResultSet.getInt("body_id")));
        if ((i == paramInt2) && (j == paramInt3) && (localTimestamp == null)) {
          modifyMessageDate(paramConnection, n, true);
        }
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localMessages;
  }
  
  protected com.ffusion.beans.messages.Messages getMessageItems(Connection paramConnection, int paramInt1, int paramInt2, int paramInt3, SecureUser paramSecureUser)
    throws Exception
  {
    ResultSet localResultSet = null;
    com.ffusion.beans.messages.Messages localMessages = new com.ffusion.beans.messages.Messages();
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select * from message_item where message_id=? order by create_date desc, message_type desc");
      localPreparedStatement.setInt(1, paramInt1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from message_item where message_id=? order by create_date desc, message_type desc");
      HashMap localHashMap1 = new HashMap();
      HashMap localHashMap2 = new HashMap();
      Locale localLocale = jdMethod_for(paramSecureUser, null);
      while (localResultSet.next() == true)
      {
        Message localMessage = localMessages.create();
        int n = localResultSet.getInt("item_id");
        localMessage.setID(String.valueOf(n));
        localMessage.set("MESSAGE_ID", String.valueOf(localResultSet.getInt("message_id")));
        int i = localResultSet.getInt("to_id");
        int j = localResultSet.getInt("to_type");
        setToOrFromFields(paramConnection, i, j, localMessage, localHashMap1, localHashMap2, 1, paramSecureUser.getLocale());
        int k = localResultSet.getInt("from_id");
        int m = localResultSet.getInt("from_type");
        if (m == 3) {
          setToOrFromFields(paramConnection, k, m, localMessage, localHashMap1, localHashMap2, 2, localLocale);
        } else {
          setToOrFromFields(paramConnection, k, m, localMessage, localHashMap1, localHashMap2, 2, paramSecureUser.getLocale());
        }
        localMessage.setDate(Profile.convertSqlTimestamp(localResultSet.getTimestamp("create_date")));
        localMessage.setArchivedDate(localResultSet.getTimestamp("delete_date"));
        Timestamp localTimestamp = localResultSet.getTimestamp("read_date");
        localMessage.setReadDate(localTimestamp);
        localMessage.setType(localResultSet.getInt("message_type"));
        localMessage.setMemo(getMessageBody(paramConnection, localResultSet.getInt("body_id")));
        if ((i == paramInt2) && (j == paramInt3) && (localTimestamp == null)) {
          modifyMessageDate(paramConnection, n, true);
        }
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localMessages;
  }
  
  public Message getMessage(SecureUser paramSecureUser, int paramInt)
    throws Exception
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Message localMessage = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from message_item where item_id=?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from message_item where item_id=?");
      com.ffusion.beans.messages.Messages localMessages = new com.ffusion.beans.messages.Messages();
      Locale localLocale = jdMethod_for(paramSecureUser, null);
      if (localResultSet.next() == true)
      {
        localMessage = localMessages.createNoAdd();
        localMessage.setID(String.valueOf(paramInt));
        localMessage.set("MESSAGE_ID", String.valueOf(localResultSet.getInt("message_id")));
        int i = localResultSet.getInt("to_id");
        int j = localResultSet.getInt("to_type");
        setToOrFromFields(localConnection, i, j, localMessage, null, null, 1, paramSecureUser.getLocale());
        int k = localResultSet.getInt("from_id");
        int m = localResultSet.getInt("from_type");
        if (m == 3) {
          setToOrFromFields(localConnection, k, m, localMessage, null, null, 2, localLocale);
        } else {
          setToOrFromFields(localConnection, k, m, localMessage, null, null, 2, paramSecureUser.getLocale());
        }
        localMessage.setDate(localResultSet.getTimestamp("create_date"));
        localMessage.setArchivedDate(localResultSet.getTimestamp("delete_date"));
        Timestamp localTimestamp = localResultSet.getTimestamp("read_date");
        localMessage.setReadDate(localTimestamp);
        localMessage.setType(localResultSet.getInt("message_type"));
        localMessage.setMemo(getMessageBody(localConnection, localResultSet.getInt("body_id")));
        if ((i == paramSecureUser.getProfileID()) && (j == 1) && (localTimestamp == null)) {
          modifyMessageDate(localConnection, paramInt, true);
        }
      }
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localMessage;
  }
  
  protected void setToOrFromFields(Connection paramConnection, int paramInt1, int paramInt2, Message paramMessage, HashMap paramHashMap1, HashMap paramHashMap2, int paramInt3, Locale paramLocale)
    throws ProfileException
  {
    String str1 = "MessageAdapter.setToOrFromFields";
    String str2 = "";
    if ((paramInt3 == 2) && (paramInt2 == 3))
    {
      try
      {
        String str3 = paramLocale.getLanguage() + "_" + paramLocale.getCountry().toUpperCase();
        GlobalMessage localGlobalMessage = getGlobalMsgByIdTX(paramConnection, paramInt1, paramLocale, str3, null);
        GlobalMessages localGlobalMessages = new GlobalMessages();
        localGlobalMessages.add(localGlobalMessage);
        populateI18NGlobalMsgListTX(paramConnection, localGlobalMessages, str3, null);
        paramInt1 = localGlobalMessage.getFromIDValue();
        paramInt2 = 0;
        str2 = localGlobalMessage.getFromName(str3);
      }
      catch (Exception localException)
      {
        Profile.handleError(str1, "Unable to retrieve Global Message", 4);
      }
    }
    else
    {
      HashMap localHashMap = null;
      if (paramInt2 == 1) {
        localHashMap = paramHashMap1;
      } else if (paramInt2 == 0) {
        localHashMap = paramHashMap2;
      }
      str2 = getPersonalName(paramConnection, paramInt1, paramInt2, paramMessage.getLocale(), localHashMap, null);
    }
    if (paramInt3 == 1)
    {
      paramMessage.set("TO_ID", String.valueOf(paramInt1));
      paramMessage.set("TO_TYPE", String.valueOf(paramInt2));
      paramMessage.setTo(str2);
      paramMessage.setToName(str2);
      paramMessage.setToType(String.valueOf(paramInt2));
    }
    else if (paramInt3 == 2)
    {
      paramMessage.set("FROM_ID", String.valueOf(paramInt1));
      paramMessage.set("FROM_TYPE", String.valueOf(paramInt2));
      paramMessage.setFrom(str2);
      paramMessage.setFromName(str2);
      paramMessage.setFromType(String.valueOf(paramInt2));
    }
  }
  
  public void modifyMessageThread(MessageThread paramMessageThread)
    throws ProfileException
  {
    String str = "MessageAdapter.modifyMessageThread";
    Profile.isInitialized();
    Connection localConnection = null;
    int i = Profile.convertToInt(paramMessageThread.getThreadID());
    if (!Profile.isValidId(i)) {
      Profile.handleError(str, "Invalid Message Id", 4);
    }
    try
    {
      int j = 0;
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      int k = Profile.convertToInt(paramMessageThread.getThreadStatus());
      if (Profile.isValidId(k) == true)
      {
        j = 1;
        modifyMessageThreadStatus(localConnection, i, k);
      }
      int m = Profile.convertToInt(paramMessageThread.getEmployeeID());
      if (Profile.isValidId(m) == true)
      {
        j = 1;
        modifyMessageThreadOwner(localConnection, i, m);
      }
      if (j == 0) {
        Profile.handleError(str, "No fields to modify", 3);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  public static void modifyMessageThreadStatus(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws Exception
  {
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      modifyMessageThreadStatus(localConnection, paramInt1, paramInt2);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  protected static void modifyMessageThreadStatus(Connection paramConnection, int paramInt1, int paramInt2)
    throws Exception
  {
    Timestamp localTimestamp = DBUtil.getCurrentTimestamp();
    StringBuffer localStringBuffer = new StringBuffer();
    PreparedStatement localPreparedStatement = null;
    try
    {
      localStringBuffer.append(anE);
      if (paramInt2 != -1) {
        localStringBuffer.append(",status=?,close_date=? ");
      }
      Profile.appendSQLSelectInt(localStringBuffer, "message_id", paramInt1, false);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      localPreparedStatement.setTimestamp(i++, localTimestamp);
      if (paramInt2 != -1)
      {
        i = Profile.setStatementInt(localPreparedStatement, i, paramInt2, true);
        if (paramInt2 == 5) {
          i = Profile.setStatementDate(localPreparedStatement, i, localTimestamp, true);
        } else {
          localPreparedStatement.setNull(i++, 93);
        }
      }
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt1, false);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  protected void modifyMessageThreadOwner(Connection paramConnection, int paramInt1, int paramInt2)
    throws Exception
  {
    Timestamp localTimestamp = DBUtil.getCurrentTimestamp();
    StringBuffer localStringBuffer = new StringBuffer();
    PreparedStatement localPreparedStatement = null;
    try
    {
      localStringBuffer.append(anE);
      localStringBuffer.append(",employee_id=?");
      Profile.appendSQLSelectInt(localStringBuffer, "message_id", paramInt1, false);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      localPreparedStatement.setTimestamp(i++, localTimestamp);
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt2, true);
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt1, false);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public boolean hasMailReferencesTX(Connection paramConnection, int paramInt1, int paramInt2)
    throws Exception
  {
    boolean bool = false;
    Profile.isInitialized();
    StringBuffer localStringBuffer = new StringBuffer();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      int i;
      if (paramInt2 == 0)
      {
        localStringBuffer.append("select item_id from message_item where (from_id=? and from_type=?) or (to_id=? and to_type=?)");
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select item_id from message_item where (from_id=? and from_type=?) or (to_id=? and to_type=?)");
        i = 1;
        i = Profile.setStatementInt(localPreparedStatement, i, paramInt1, false);
        i = Profile.setStatementInt(localPreparedStatement, i, paramInt2, false);
        i = Profile.setStatementInt(localPreparedStatement, i, paramInt1, false);
        i = Profile.setStatementInt(localPreparedStatement, i, paramInt2, false);
      }
      else
      {
        localStringBuffer.append("select message_id from message where directory_id=?");
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select message_id from message where directory_id=?");
        i = 1;
        i = Profile.setStatementInt(localPreparedStatement, i, paramInt1, false);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next() == true) {
        bool = true;
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return bool;
  }
  
  public boolean hasMessageReferencesEmployeeTX(Connection paramConnection, int paramInt)
    throws Exception
  {
    boolean bool = false;
    Profile.isInitialized();
    StringBuffer localStringBuffer = new StringBuffer();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localStringBuffer.append("select message_id from message where employee_id=? ");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select message_id from message where employee_id=? ");
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt, false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next() == true) {
        bool = true;
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return bool;
  }
  
  protected int routeMessage(Connection paramConnection, int paramInt1, int paramInt2)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i = 0;
    try
    {
      int k;
      int m;
      if (paramInt2 != 0)
      {
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "SELECT personal_banker FROM customer WHERE directory_id=? AND ( NOT personal_banker is null) AND ( NOT personal_banker = 0) UNION SELECT b.personal_banker FROM business b, business_employee be WHERE b.business_id = be.business_id AND ( NOT b.personal_banker is null) AND ( NOT b.personal_banker = 0) AND be.directory_id = ?");
        int j = 1;
        j = Profile.setStatementInt(localPreparedStatement, j, paramInt2, false);
        j = Profile.setStatementInt(localPreparedStatement, j, paramInt2, false);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT personal_banker FROM customer WHERE directory_id=? AND ( NOT personal_banker is null) AND ( NOT personal_banker = 0) UNION SELECT b.personal_banker FROM business b, business_employee be WHERE b.business_id = be.business_id AND ( NOT b.personal_banker is null) AND ( NOT b.personal_banker = 0) AND be.directory_id = ?");
        if (localResultSet.next() == true) {
          i = localResultSet.getInt("personal_banker");
        }
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        localPreparedStatement = null;
        localResultSet = null;
        if (i != 0)
        {
          localPreparedStatement = DBUtil.prepareStatement(paramConnection, "SELECT employee_status, supervisor FROM bank_employee WHERE employee_id=?");
          k = 1;
          k = Profile.setStatementInt(localPreparedStatement, k, i, false);
          localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT employee_status, supervisor FROM bank_employee WHERE employee_id=?");
          if (localResultSet.next())
          {
            m = localResultSet.getInt("employee_status");
            if (m != 0) {
              i = localResultSet.getInt("supervisor");
            }
          }
          DBUtil.closeAll(localPreparedStatement, localResultSet);
          localPreparedStatement = null;
          localResultSet = null;
        }
      }
      if (i == 0)
      {
        k = 0;
        if (paramInt2 > 0)
        {
          localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select c.affiliate_bank_id from customer c where c.directory_id=? and c.customer_type='2' UNION select b.affiliate_bank_id from customer c, business_employee be, business b where c.directory_id=? and c.customer_type='1' and c.directory_id=be.directory_id and be.business_id=b.directory_id");
          m = 1;
          m = Profile.setStatementInt(localPreparedStatement, m, paramInt2, false);
          m = Profile.setStatementInt(localPreparedStatement, m, paramInt2, false);
          localResultSet = DBUtil.executeQuery(localPreparedStatement, "select c.affiliate_bank_id from customer c where c.directory_id=? and c.customer_type='2' UNION select b.affiliate_bank_id from customer c, business_employee be, business b where c.directory_id=? and c.customer_type='1' and c.directory_id=be.directory_id and be.business_id=b.directory_id");
          if (localResultSet.next()) {
            k = localResultSet.getInt("affiliate_bank_id");
          }
          DBUtil.closeAll(localPreparedStatement, localResultSet);
          localPreparedStatement = null;
          localResultSet = null;
        }
        String str;
        if (k > 0) {
          str = "SELECT q.employee_id from queue_members q, bank_employee b where q.queue_id=? and b.employee_status=? and q.status=0 and q.employee_id = b.employee_id and ( (b.affiliate_bank_id = ? or exists  (select afas.AFFILIATE_BANK_ID from AFFILIATE_ASSIGN afas where afas.EMPLOYEE_ID=b.employee_id and afas.AFFILIATE_BANK_ID=?))   or ( b.affiliate_bank_id = 0 and not exists (select afas.AFFILIATE_BANK_ID from AFFILIATE_ASSIGN afas where afas.EMPLOYEE_ID=b.employee_id and afas.AFFILIATE_BANK_ID=?)) )";
        } else {
          str = "SELECT q.employee_id from queue_members q, bank_employee b where q.queue_id=? and b.employee_status=? and q.status=0 and q.employee_id = b.employee_id";
        }
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, str);
        m = 1;
        m = Profile.setStatementInt(localPreparedStatement, m, paramInt1, false);
        m = Profile.setStatementInt(localPreparedStatement, m, 0, false);
        if (k > 0)
        {
          m = Profile.setStatementInt(localPreparedStatement, m, k, false);
          m = Profile.setStatementInt(localPreparedStatement, m, k, false);
          m = Profile.setStatementInt(localPreparedStatement, m, k, false);
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, str);
        i = getAvailableEmployeeId(paramConnection, localResultSet, "SELECT COUNT(message_id) FROM message WHERE employee_id=? AND ( NOT status = 9 ) AND ( NOT status = 12 ) AND ( NOT status = 5 )");
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        localPreparedStatement = null;
        localResultSet = null;
        if (i == 0)
        {
          localPreparedStatement = DBUtil.prepareStatement(paramConnection, str);
          int n = 1;
          n = Profile.setStatementInt(localPreparedStatement, n, paramInt1, false);
          n = Profile.setStatementInt(localPreparedStatement, n, 2, false);
          if (k > 0)
          {
            n = Profile.setStatementInt(localPreparedStatement, n, k, false);
            n = Profile.setStatementInt(localPreparedStatement, n, k, false);
            n = Profile.setStatementInt(localPreparedStatement, n, k, false);
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, str);
          i = getAvailableEmployeeId(paramConnection, localResultSet, "SELECT COUNT(message_id) FROM message WHERE employee_id=? AND ( NOT status = 9 ) AND ( NOT status = 12 ) AND ( NOT status = 5 )");
          DBUtil.closeAll(localPreparedStatement, localResultSet);
          localPreparedStatement = null;
          localResultSet = null;
        }
        if (i == 0) {
          i = 1;
        }
      }
    }
    catch (Exception localException)
    {
      i = 1;
      throw new Exception(localException.getMessage());
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return i;
  }
  
  protected int getAvailableEmployeeId(Connection paramConnection, ResultSet paramResultSet, String paramString)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int j = 0;
    int k = 0;
    int m = 9999999;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, paramString);
      while (paramResultSet.next())
      {
        k = 0;
        int i = paramResultSet.getInt("employee_id");
        localPreparedStatement.setInt(1, i);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, paramString);
        if (localResultSet.next() == true) {
          k = localResultSet.getInt(1);
        }
        if (k < m)
        {
          j = i;
          m = k;
        }
        DBUtil.closeResultSet(localResultSet);
        localResultSet = null;
        if (m == 0) {
          break;
        }
      }
    }
    catch (Exception localException)
    {
      j = 0;
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return j;
  }
  
  protected void messageAutoReply(Connection paramConnection, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws Exception
  {
    User localUser = CustomerAdapter.getUserById(paramInt3);
    String str1 = localUser.getPreferredLanguage();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      if (str1.equalsIgnoreCase("en_US"))
      {
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select autoreply_text from queue where queue_id=?");
        localPreparedStatement.setInt(1, paramInt2);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select autoreply_text from queue where queue_id=?");
      }
      else
      {
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select autoreply_text from queue_i18n where queue_id=? and language=?");
        localPreparedStatement.setInt(1, paramInt2);
        localPreparedStatement.setString(2, str1);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select autoreply_text from queue_i18n where queue_id=? and language=?");
      }
      if (localResultSet.next() == true)
      {
        String str2 = localResultSet.getString(1);
        if ((str2 != null) && (str2.trim().length() > 0))
        {
          if (Profile.properties.getProperty("AUTOREPLY_REFERENCE", "").equalsIgnoreCase("true")) {
            str2 = str2 + " " + paramInt1;
          }
          int i = addMessageBody(paramConnection, str2);
          addMessageItem(paramConnection, paramInt1, 7, i, 0, paramInt3, 1, paramInt4, 0);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw localException;
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  private static boolean jdMethod_for(StringBuffer paramStringBuffer, boolean paramBoolean)
  {
    if (!paramBoolean) {
      paramStringBuffer.append(" where ");
    } else {
      paramStringBuffer.append(" and ");
    }
    return true;
  }
  
  public MessageThreads findMessageThreads(SecureUser paramSecureUser, HashMap paramHashMap, User paramUser, Business paramBusiness, MessageThread paramMessageThread, DateTime paramDateTime1, DateTime paramDateTime2)
    throws ProfileException
  {
    String str = "MessageAdapter.findMessageThreads";
    Profile.isInitialized();
    Connection localConnection = null;
    StringBuffer localStringBuffer = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str, "Invalid Bank Id", 4);
    }
    MessageThreads localMessageThreads = new MessageThreads(paramSecureUser.getLocale());
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localStringBuffer = new StringBuffer("SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, customer_directory cd ");
      if ((paramBusiness != null) && (paramBusiness.getBusinessName() != null) && (paramBusiness.getBusinessName().length() > 0)) {
        localStringBuffer.append(",business b, business_employee be ");
      }
      localStringBuffer.append(" WHERE m.directory_id = c.directory_id and m.directory_id = cd.directory_id");
      boolean bool = true;
      bool = jdMethod_for(localStringBuffer, paramSecureUser, paramHashMap, paramUser, paramBusiness, paramMessageThread, paramDateTime1, paramDateTime2, bool);
      int i = paramSecureUser.getAffiliateIDValue();
      if ((paramUser != null) && (paramUser.getAffiliateBankID() > 0)) {
        i = paramUser.getAffiliateBankID();
      }
      if (i > 0) {
        localStringBuffer.append(" AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id )) ");
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      if (Profile.useMaxRows(paramHashMap)) {
        localPreparedStatement.setMaxRows(Profile.getMaxRowCount(paramHashMap));
      }
      int j = 1;
      j = jdMethod_for(localPreparedStatement, paramSecureUser, paramHashMap, paramUser, paramBusiness, paramMessageThread, paramDateTime1, paramDateTime2, j);
      if (i > 0)
      {
        localPreparedStatement.setInt(j++, i);
        localPreparedStatement.setInt(j++, i);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next() == true) {
        localMessageThreads.add(processMessageThreadsRS(paramSecureUser, localResultSet));
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
    return localMessageThreads;
  }
  
  public void createNewCase(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage, String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
    throws ProfileException
  {
    String str1 = "MessageAdapter.createNewCase";
    Profile.isInitialized();
    Connection localConnection = null;
    String str2 = paramSecureUser.getBankID();
    if (!Profile.isValidId(str2)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      int i = 0;
      String str3 = paramMessage.getMemo();
      if ((str3 != null) && (str3.trim().length() > 0)) {
        i = addMessageBody(localConnection, str3);
      } else {
        Profile.handleError(new Exception("No text in message"), str1);
      }
      int j = 0;
      String str4 = paramMessage.getComment();
      if ((str4 != null) && (str4.trim().length() > 0)) {
        j = addMessageComment(localConnection, str4);
      }
      MessageThread localMessageThread = createNewCaseMessageThread(localConnection, paramSecureUser, str2, paramString1, paramMessage);
      paramMessage.setMsgThreadID(localMessageThread.getThreadID());
      paramMessage.setCaseNum(localMessageThread.getCaseNum());
      int k = 0;
      String str5 = (String)paramMessage.get("NO_APPROVAL_REQUIRED");
      if ((str5 == null) || (!str5.equalsIgnoreCase("true"))) {
        k = msgApprovalRequired(localConnection, paramSecureUser.getProfileID());
      }
      if (k == -1) {
        Profile.handleError(str1, "Could not find ID of approval employee.", 3);
      }
      int m = 2;
      if (k != 0)
      {
        if (paramBoolean) {
          m = 12;
        } else {
          m = 9;
        }
        paramMessage.setTo(k);
        paramMessage.setToType(String.valueOf(0));
      }
      Message localMessage = createNewCaseMessage(localConnection, i, j, paramMessage);
      if (m != 2)
      {
        int n = Profile.convertToInt(localMessage.getMsgThreadID());
        modifyMessageThreadStatus(localConnection, n, m);
      }
      DBUtil.commit(localConnection);
      if ((paramBoolean) && (m != 12) && (m != 9))
      {
        HashMap localHashMap = new HashMap();
        getCustomerNameByID(localConnection, Profile.convertToInt(localMessage.getTo()), localMessage.getLocale(), localHashMap);
        String str6 = (String)localHashMap.get("EMAIL_ADDRESS");
        if (str6 == null) {
          DebugLog.log(Level.INFO, str1 + ": An email notification could not be sent " + "because the recipient's email address is missing.");
        } else {
          com.ffusion.csil.handlers.Messages._mailService.sendMessage(str6, paramString2, paramString3, paramString4);
        }
      }
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  protected MessageThread createNewCaseMessageThread(Connection paramConnection, SecureUser paramSecureUser, String paramString1, String paramString2, Message paramMessage)
    throws Exception
  {
    String str1 = "MessageAdapter.createNewMessageThread";
    int i = Profile.convertToInt(paramMessage.getTo());
    if (!Profile.isValidId(i)) {
      Profile.handleError(str1, "Invalid to ID", 4);
    }
    int j;
    if (paramMessage.getTypeValue() == 9) {
      j = 0;
    } else {
      j = i;
    }
    int k = Profile.convertToInt(paramMessage.getFrom());
    if (!Profile.isValidId(k)) {
      Profile.handleError(str1, "Invalid from ID", 4);
    }
    int m = k;
    String str2 = paramMessage.getSubject();
    if ((str2 == null) || (str2.trim().length() == 0)) {
      str2 = "None";
    }
    int n;
    if ((paramMessage.getTypeValue() == 9) || (paramMessage.getTypeValue() == 5)) {
      n = 5;
    } else {
      n = 2;
    }
    MessageThread localMessageThread = new MessageThread(paramSecureUser.getLocale());
    localMessageThread.setEmployeeID(String.valueOf(m));
    localMessageThread.setDirectoryID(String.valueOf(j));
    localMessageThread.setQueueID(String.valueOf(paramString2));
    localMessageThread.setSubject(str2);
    localMessageThread.setThreadStatus(String.valueOf(n));
    localMessageThread = addMessageThread(paramConnection, localMessageThread, paramString1);
    return localMessageThread;
  }
  
  protected Message createNewCaseMessage(Connection paramConnection, int paramInt1, int paramInt2, Message paramMessage)
    throws Exception
  {
    String str = "MessageAdapter.createNewMessageThread";
    if (!Profile.isValidId(paramMessage.getMsgThreadID())) {
      Profile.handleError(str, "Invalid Thread ID", 4);
    }
    if (!Profile.isValidId(paramInt1)) {
      Profile.handleError(str, "Invalid Body ID", 4);
    }
    if (!Profile.isValidId(paramMessage.getTo())) {
      Profile.handleError(str, "Invalid To ID", 4);
    }
    if (!Profile.isValidId(paramMessage.getFrom())) {
      Profile.handleError(str, "Invalid From ID", 4);
    }
    int i = paramMessage.getFromTypeValue();
    if (i != 0) {
      Profile.handleError(str, "Invalid From Type", 4);
    }
    int j = paramMessage.getTypeValue();
    if ((j < 0) || (j >= 12)) {
      Profile.handleError(str, "Invalid Message Type", 3);
    }
    return addMessage(paramConnection, paramMessage, paramInt1, paramInt2);
  }
  
  protected int addMessageBody(Connection paramConnection, String paramString)
    throws Exception
  {
    String str = "MessageAdapter.addMessageBody";
    if ((paramString == null) || (paramString.length() == 0)) {
      return -1;
    }
    PreparedStatement localPreparedStatement = null;
    int i = DBUtil.getNextId(paramConnection, Profile.dbType, "BODY_ID");
    ArrayList localArrayList = Profile.breakupMessage(paramString);
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "INSERT into message_body (body_id,seq,text) values(?,?,?)");
      for (int j = 0; j < localArrayList.size(); j++)
      {
        int k = 1;
        jdMethod_int(localPreparedStatement, i, j + 1, (String)localArrayList.get(j), k);
        DBUtil.executeUpdate(localPreparedStatement, "INSERT into message_body (body_id,seq,text) values(?,?,?)");
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
    return i;
  }
  
  protected String getMessageBody(Connection paramConnection, int paramInt)
    throws ProfileException
  {
    String str = "MessageAdapter.getMessageBody";
    StringBuffer localStringBuffer1 = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    if (paramInt <= 0) {
      Profile.handleError(str, "Invalid Message Body Id", 4);
    }
    StringBuffer localStringBuffer2 = new StringBuffer();
    try
    {
      localStringBuffer1 = new StringBuffer("SELECT text FROM message_body where body_id = ? order by seq");
      int i = 0;
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer1.toString());
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer1.toString());
      while (localResultSet.next() == true) {
        localStringBuffer2.append(Profile.getRSString(localResultSet, "text"));
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localStringBuffer2.toString();
  }
  
  protected int addMessageComment(Connection paramConnection, String paramString)
    throws Exception
  {
    String str = "MessageAdapter.addMessageComment";
    if ((paramString == null) || (paramString.length() == 0)) {
      return -1;
    }
    PreparedStatement localPreparedStatement = null;
    int i = DBUtil.getNextId(paramConnection, Profile.dbType, "COMMENT_ID");
    ArrayList localArrayList = Profile.breakupMessage(paramString);
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "INSERT into message_comment (comment_id,seq,text) values(?,?,?)");
      for (int j = 0; j < localArrayList.size(); j++)
      {
        int k = 1;
        jdMethod_for(localPreparedStatement, i, j + 1, (String)localArrayList.get(j), k);
        DBUtil.executeUpdate(localPreparedStatement, "INSERT into message_comment (comment_id,seq,text) values(?,?,?)");
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
    return i;
  }
  
  protected String getMessageComment(Connection paramConnection, int paramInt)
    throws ProfileException
  {
    String str = "MessageAdapter.getMessageComment";
    StringBuffer localStringBuffer1 = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    if (paramInt <= 0) {
      Profile.handleError(str, "Invalid Message Comment Id", 4);
    }
    StringBuffer localStringBuffer2 = new StringBuffer();
    try
    {
      localStringBuffer1 = new StringBuffer("SELECT text FROM message_comment where comment_id = ? order by seq");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer1.toString());
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer1.toString());
      while (localResultSet.next() == true) {
        localStringBuffer2.append(Profile.getRSString(localResultSet, "text"));
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localStringBuffer2.toString();
  }
  
  protected MessageThread addMessageThread(Connection paramConnection, MessageThread paramMessageThread, String paramString)
    throws Exception
  {
    String str1 = "MessageAdapter.addMessageThread";
    PreparedStatement localPreparedStatement = null;
    try
    {
      String str2 = paramMessageThread.getQueueID();
      if ((str2 != null) && (str2.length() > 0) && (!str2.equals("0")))
      {
        localObject1 = new MessageQueue();
        ((MessageQueue)localObject1).setQueueID(str2);
        ((MessageQueue)localObject1).setBankId(paramString);
        ((MessageQueue)localObject1).setQueueType(null);
        MessageQueues localMessageQueues = MessageQueueAdapter.getQueues((MessageQueue)localObject1);
        if ((localMessageQueues == null) || (localMessageQueues.size() == 0)) {
          Profile.handleError(str1, "Non-existant queue.", 8626);
        }
      }
      Object localObject1 = DBUtil.getCurrentTimestamp();
      paramMessageThread.setCreateDate((Timestamp)localObject1);
      int i = DBUtil.getNextId(paramConnection, Profile.dbType, "MESSAGE_ID");
      paramMessageThread.setThreadID(String.valueOf(i));
      if (paramMessageThread.getCaseNum() == null) {
        paramMessageThread.setCaseNum(String.valueOf(i));
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "INSERT into message (message_id, employee_id, directory_id, queue_id, subject, status, create_date, bank_id, case_num) values (?,?,?,?,?,?,?,?,?)");
      int j = 1;
      j = jdMethod_for(localPreparedStatement, paramMessageThread, paramString, j);
      DBUtil.executeUpdate(localPreparedStatement, "INSERT into message (message_id, employee_id, directory_id, queue_id, subject, status, create_date, bank_id, case_num) values (?,?,?,?,?,?,?,?,?)");
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
    return paramMessageThread;
  }
  
  protected Message addMessage(Connection paramConnection, Message paramMessage, int paramInt1, int paramInt2)
    throws Exception
  {
    String str = "MessageAdapter.addMessage";
    PreparedStatement localPreparedStatement = null;
    try
    {
      Timestamp localTimestamp = DBUtil.getCurrentTimestamp();
      int i = DBUtil.getNextId(paramConnection, Profile.dbType, "ITEM_ID");
      paramMessage.setID(String.valueOf(i));
      paramMessage.setCreateDate(localTimestamp);
      int j = paramMessage.getFromTypeValue();
      if ((j != 0) && (j != 1) && (j != 3)) {
        Profile.handleError(str, "Invalid fromType", 3);
      }
      int k = paramMessage.getToTypeValue();
      if ((k != 0) && (k != 1)) {
        Profile.handleError(str, "Invalid toType", 3);
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "INSERT into message_item (item_id,message_id,body_id,comment_id,to_id,to_type,from_id,from_type,message_type,create_date) values (?,?,?,?,?,?,?,?,?,?)");
      int m = 1;
      m = jdMethod_for(localPreparedStatement, paramMessage, paramInt1, paramInt2, m);
      DBUtil.executeUpdate(localPreparedStatement, "INSERT into message_item (item_id,message_id,body_id,comment_id,to_id,to_type,from_id,from_type,message_type,create_date) values (?,?,?,?,?,?,?,?,?,?)");
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
    return paramMessage;
  }
  
  public com.ffusion.beans.messages.Messages getMessages(SecureUser paramSecureUser, HashMap paramHashMap, String paramString)
    throws ProfileException
  {
    String str = "MessageAdapter.getMessages";
    Profile.isInitialized();
    Connection localConnection = null;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str, "Invalid Bank Id", 4);
    }
    if (!Profile.isValidId(paramString)) {
      Profile.handleError(str, "Invalid Thread Id", 4);
    }
    MessageThread localMessageThread = new MessageThread(paramSecureUser.getLocale());
    com.ffusion.beans.messages.Messages localMessages = new com.ffusion.beans.messages.Messages(paramSecureUser.getLocale());
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localMessageThread.setThreadID(paramString);
      localMessageThread.setMessages(localMessages);
      getMessagesForMessageThread(localConnection, paramSecureUser, paramHashMap, localMessageThread);
      if (Profile.convertToInt(localMessageThread.getThreadStatus()) == 1) {
        modifyMessageThreadStatus(localConnection, Profile.convertToInt(paramString), 2);
      }
      Iterator localIterator = localMessages.iterator();
      while (localIterator.hasNext() == true)
      {
        Message localMessage = (Message)localIterator.next();
        if (((localMessage.getReadDate() == null) || (localMessage.getReadDate().length() == 0)) && (localMessage.getTo() != null) && (localMessage.getTo().compareTo(Integer.toString(paramSecureUser.getProfileID())) == 0) && (localMessage.getToType() != null) && (localMessage.getToTypeValue() == paramSecureUser.getUserType())) {
          modifyMessageDate(localConnection, Profile.convertToInt(localMessage.getID()), true);
        }
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    return localMessages;
  }
  
  protected void getMessagesForMessageThread(Connection paramConnection, SecureUser paramSecureUser, HashMap paramHashMap, MessageThread paramMessageThread)
    throws ProfileException
  {
    String str = "MessageAdapter.getMessagesForMessageThread";
    StringBuffer localStringBuffer = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    com.ffusion.beans.messages.Messages localMessages = paramMessageThread.getMessages();
    try
    {
      if (paramSecureUser.getUserType() == 1) {
        localStringBuffer = new StringBuffer("SELECT mi.item_id, mi.message_id, mi.body_id, mi.comment_id, mi.to_id, mi.to_type, mi.from_id, mi.from_type, mi.create_date, mi.delete_date, mi.read_date, mi.message_type, m.subject, m.case_num, m.status FROM message m, message_item mi WHERE m.message_id=mi.message_id and mi.delete_date is null");
      } else {
        localStringBuffer = new StringBuffer("SELECT mi.item_id, mi.message_id, mi.body_id, mi.comment_id, mi.to_id, mi.to_type, mi.from_id, mi.from_type, mi.create_date, mi.delete_date, mi.read_date, mi.message_type, m.subject, m.case_num, m.status FROM message m, message_item mi WHERE m.message_id=mi.message_id");
      }
      boolean bool = true;
      bool = jdMethod_for(localStringBuffer, paramSecureUser, paramHashMap, paramMessageThread.getThreadID(), bool);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = jdMethod_for(localPreparedStatement, paramSecureUser, paramHashMap, paramMessageThread.getThreadID(), i);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      HashMap localHashMap1 = new HashMap(20);
      HashMap localHashMap2 = new HashMap(20);
      while (localResultSet.next() == true)
      {
        paramMessageThread.setThreadStatus(String.valueOf(localResultSet.getInt("status")));
        localMessages.add(processMessagesRS(paramConnection, paramSecureUser, localResultSet, localHashMap1, localHashMap2, paramSecureUser.getLocale()));
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  public String getIdName(Connection paramConnection, int paramInt1, int paramInt2, Locale paramLocale, boolean paramBoolean1, boolean paramBoolean2, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "MessageAdapter.getIdName";
    Profile.isInitialized();
    try
    {
      if (paramInt2 == 0) {
        return getEmployeeNameByID(paramConnection, paramInt1, paramLocale, paramBoolean1, paramBoolean2, paramHashMap);
      }
      if (paramInt2 == 1) {
        return getCustomerNameByID(paramConnection, paramInt1, paramLocale, paramHashMap);
      }
      if (paramInt2 == 2) {
        return getQueueNameByID(paramConnection, paramInt1);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    return "";
  }
  
  public String getIdName(Connection paramConnection, int paramInt1, int paramInt2, Locale paramLocale, HashMap paramHashMap)
    throws ProfileException
  {
    return getIdName(paramConnection, paramInt1, paramInt2, paramLocale, true, false, paramHashMap);
  }
  
  protected String getPersonalName(Connection paramConnection, int paramInt1, int paramInt2, Locale paramLocale, HashMap paramHashMap1, boolean paramBoolean1, boolean paramBoolean2, HashMap paramHashMap2)
    throws ProfileException
  {
    String str = null;
    if (paramHashMap1 != null) {
      str = (String)paramHashMap1.get(String.valueOf(paramInt1));
    }
    if ((str == null) || (str.trim().length() == 0))
    {
      str = getIdName(paramConnection, paramInt1, paramInt2, paramLocale, paramBoolean1, paramBoolean2, paramHashMap2);
      if ((paramHashMap1 != null) && (str.trim().length() != 0)) {
        paramHashMap1.put(String.valueOf(paramInt1), str);
      }
    }
    if (str == null) {
      return "";
    }
    return str;
  }
  
  protected String getPersonalName(Connection paramConnection, int paramInt1, int paramInt2, Locale paramLocale, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ProfileException
  {
    return getPersonalName(paramConnection, paramInt1, paramInt2, paramLocale, paramHashMap1, true, true, paramHashMap2);
  }
  
  protected String getBusinessNameByID(Connection paramConnection, int paramInt)
    throws Exception
  {
    String str1 = "MessageAdapter.getBusinessNameByID";
    StringBuffer localStringBuffer1 = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    if (!Profile.isValidId(paramInt)) {
      return "";
    }
    StringBuffer localStringBuffer2 = new StringBuffer();
    try
    {
      localStringBuffer1 = new StringBuffer("SELECT business_name FROM business");
      boolean bool = false;
      bool = jdMethod_else(localStringBuffer1, paramInt, bool);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer1.toString());
      int i = 1;
      i = jdMethod_for(localPreparedStatement, paramInt, i);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer1.toString());
      if (localResultSet.next() == true)
      {
        String str2 = Profile.getRSString(localResultSet, "business_name");
        if (str2 == null) {
          str2 = "";
        }
        localStringBuffer2.append(str2);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localStringBuffer2.toString();
  }
  
  protected String getCustomerNameByID(Connection paramConnection, int paramInt, Locale paramLocale, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "MessageAdapter.getCustomerNameByID";
    StringBuffer localStringBuffer = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    if (!Profile.isValidId(paramInt)) {
      return "";
    }
    String str2 = null;
    try
    {
      localStringBuffer = new StringBuffer("SELECT c.first_name,c.last_name,c.email_address,c.user_name,cd.cust_id FROM customer c, customer_directory cd WHERE c.directory_id=cd.directory_id");
      boolean bool = true;
      bool = jdMethod_long(localStringBuffer, paramInt, bool);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = jdMethod_try(localPreparedStatement, paramInt, i);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next() == true)
      {
        str2 = UserUtil.getFullNameWithLoginId(Profile.getRSString(localResultSet, "first_name"), Profile.getRSString(localResultSet, "last_name"), Profile.getRSString(localResultSet, "user_name"), Profile.getRSString(localResultSet, "cust_id"), paramLocale);
        if (paramHashMap != null) {
          paramHashMap.put("EMAIL_ADDRESS", Profile.getRSString(localResultSet, "email_address"));
        }
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return str2;
  }
  
  protected String buildDirectoryName(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramString1 != null) {
      localStringBuffer.append(paramString1).append(" ");
    }
    if (paramString2 != null) {
      localStringBuffer.append(paramString2).append(" ");
    }
    if (paramString3 != null) {
      localStringBuffer.append("(").append(paramString3).append(")");
    }
    return localStringBuffer.toString();
  }
  
  protected String getEmployeeNameByID(Connection paramConnection, int paramInt, Locale paramLocale, boolean paramBoolean1, boolean paramBoolean2, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "MessageAdapter.getEmployeeNameByID";
    StringBuffer localStringBuffer = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    if (!Profile.isValidId(paramInt)) {
      return "";
    }
    String str2 = null;
    try
    {
      localStringBuffer = new StringBuffer("SELECT first_name,last_name,email_address,user_name,bank_employee_id FROM bank_employee");
      boolean bool = false;
      bool = jdMethod_case(localStringBuffer, paramInt, bool);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = jdMethod_new(localPreparedStatement, paramInt, i);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next() == true)
      {
        if (paramBoolean1)
        {
          if (paramBoolean2) {
            str2 = UserUtil.getSortableFullNameWithLoginId(Profile.getRSString(localResultSet, "first_name"), Profile.getRSString(localResultSet, "last_name"), Profile.getRSString(localResultSet, "user_name"), Profile.getRSString(localResultSet, "bank_employee_id"), paramLocale);
          } else {
            str2 = UserUtil.getSortableFullName(Profile.getRSString(localResultSet, "first_name"), Profile.getRSString(localResultSet, "last_name"), paramLocale);
          }
        }
        else if (paramBoolean2) {
          str2 = UserUtil.getFullNameWithLoginId(Profile.getRSString(localResultSet, "first_name"), Profile.getRSString(localResultSet, "last_name"), Profile.getRSString(localResultSet, "user_name"), Profile.getRSString(localResultSet, "bank_employee_id"), paramLocale);
        } else {
          str2 = UserUtil.getFullName(Profile.getRSString(localResultSet, "first_name"), Profile.getRSString(localResultSet, "last_name"), paramLocale);
        }
        if (paramHashMap != null) {
          paramHashMap.put("EMAIL_ADDRESS", Profile.getRSString(localResultSet, "email_address"));
        }
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return str2;
  }
  
  protected String getQueueNameByID(Connection paramConnection, int paramInt)
    throws Exception
  {
    String str1 = "MessageAdapter.getQueueNameByID";
    StringBuffer localStringBuffer1 = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    if (!Profile.isValidId(paramInt)) {
      return "";
    }
    StringBuffer localStringBuffer2 = new StringBuffer();
    try
    {
      localStringBuffer1 = new StringBuffer("SELECT queue_name FROM queue");
      boolean bool = false;
      bool = jdMethod_char(localStringBuffer1, paramInt, bool);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer1.toString());
      int i = 1;
      i = jdMethod_int(localPreparedStatement, paramInt, i);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer1.toString());
      if (localResultSet.next() == true)
      {
        String str2 = Profile.getRSString(localResultSet, "queue_name");
        if (str2 == null) {
          str2 = "";
        }
        localStringBuffer2.append(str2);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localStringBuffer2.toString();
  }
  
  public int getUnreadMessageCount(SecureUser paramSecureUser)
    throws ProfileException
  {
    String str = "MessageAdapter.getUnreadMessageCount";
    Profile.isInitialized();
    Connection localConnection = null;
    StringBuffer localStringBuffer = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i = 0;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str, "Invalid Bank Id", 4);
    }
    if (paramSecureUser.getProfileID() <= 0) {
      Profile.handleError(str, "Invalid User ID", 4);
    }
    if (paramSecureUser.getUserType() != 1) {
      Profile.handleError(str, "Incorrect User Type", 3);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localStringBuffer = new StringBuffer("SELECT count(mi.item_id) AS MESSAGE_COUNT FROM message_item mi WHERE mi.read_date is null and mi.delete_date is null");
      boolean bool = true;
      bool = jdMethod_for(localStringBuffer, paramSecureUser, bool);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int j = 1;
      j = jdMethod_for(localPreparedStatement, paramSecureUser, j);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next() == true) {
        i = localResultSet.getInt("MESSAGE_COUNT");
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
    return i;
  }
  
  public int getUnreadMessageCountExcludingAlerts(SecureUser paramSecureUser)
    throws ProfileException
  {
    String str = "MessageAdapter.getUnreadMessageCountExcludingAlerts";
    Profile.isInitialized();
    Connection localConnection = null;
    StringBuffer localStringBuffer = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i = 0;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str, "Invalid Bank Id", 4);
    }
    if (paramSecureUser.getProfileID() <= 0) {
      Profile.handleError(str, "Invalid User ID", 4);
    }
    if (paramSecureUser.getUserType() != 1) {
      Profile.handleError(str, "Incorrect User Type", 3);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localStringBuffer = new StringBuffer("SELECT count(mi.item_id) AS MESSAGE_COUNT FROM message_item mi WHERE mi.read_date is null and mi.delete_date is null and not mi.message_type = 8");
      boolean bool = true;
      bool = jdMethod_for(localStringBuffer, paramSecureUser, bool);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int j = 1;
      j = jdMethod_for(localPreparedStatement, paramSecureUser, j);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next() == true) {
        i = localResultSet.getInt("MESSAGE_COUNT");
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
    return i;
  }
  
  public void deleteMessage(int paramInt)
    throws ProfileException
  {
    String str = "MessageAdapter.deleteMessage";
    Profile.isInitialized();
    Connection localConnection = null;
    if (!Profile.isValidId(paramInt)) {
      Profile.handleError(str, "Invalid Message Id", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      modifyMessageDate(localConnection, paramInt, false);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  public void markMessageRead(int paramInt)
    throws ProfileException
  {
    String str = "MessageAdapter.markMessageRead";
    Profile.isInitialized();
    Connection localConnection = null;
    if (!Profile.isValidId(paramInt)) {
      Profile.handleError(str, "Invalid Message Id", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      modifyMessageDate(localConnection, paramInt, true);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  protected void modifyMessageDate(Connection paramConnection, int paramInt, boolean paramBoolean)
    throws Exception
  {
    String str = "MessageAdapter.modifyMessageDate";
    StringBuffer localStringBuffer = null;
    PreparedStatement localPreparedStatement = null;
    if (!Profile.isValidId(paramInt)) {
      Profile.handleError(str, "Invalid Message Id", 4);
    }
    try
    {
      Timestamp localTimestamp = DBUtil.getCurrentTimestamp();
      if (paramBoolean)
      {
        localStringBuffer = new StringBuffer("UPDATE message_item SET read_date=?");
        bool = false;
      }
      else
      {
        localStringBuffer = new StringBuffer("UPDATE message_item SET delete_date=?");
        bool = false;
      }
      boolean bool = jdMethod_goto(localStringBuffer, paramInt, bool);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = jdMethod_for(localPreparedStatement, localTimestamp, paramInt, i);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public int approvalRequired(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return globalMsgApprovalRequired(paramSecureUser, paramHashMap);
  }
  
  public int globalMsgApprovalRequired(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "MessageAdapter.GlobalMsgApprovalRequired";
    Profile.isInitialized();
    Connection localConnection = null;
    int i = 0;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      i = globalMsgApprovalRequired(localConnection, paramSecureUser.getProfileID());
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    return i;
  }
  
  public int msgApprovalRequired(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "MessageAdapter.MsgApprovalRequired";
    Profile.isInitialized();
    Connection localConnection = null;
    int i = 0;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      i = msgApprovalRequired(localConnection, paramSecureUser.getProfileID());
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    return i;
  }
  
  protected int globalMsgApprovalRequired(Connection paramConnection, int paramInt)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "SELECT approval_provider FROM bank_employee WHERE employee_id = ?");
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt, false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT approval_provider FROM bank_employee WHERE employee_id = ?");
      if (localResultSet.next() == true)
      {
        j = localResultSet.getInt("approval_provider");
        int k;
        if (j > 0)
        {
          k = j;
          return k;
        }
        if (j == 0)
        {
          k = 0;
          return k;
        }
      }
      int j = -1;
      return j;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw localException;
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  protected int msgApprovalRequired(Connection paramConnection, int paramInt)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "SELECT msg_approval_provider FROM bank_employee WHERE employee_id = ?");
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt, false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT msg_approval_provider FROM bank_employee WHERE employee_id = ?");
      if (localResultSet.next() == true)
      {
        j = localResultSet.getInt("msg_approval_provider");
        int k;
        if (j > 0)
        {
          k = j;
          return k;
        }
        if (j == 0)
        {
          k = 0;
          return k;
        }
      }
      int j = -1;
      return j;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw localException;
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  private static boolean jdMethod_for(StringBuffer paramStringBuffer, SecureUser paramSecureUser, HashMap paramHashMap, User paramUser, Business paramBusiness, MessageThread paramMessageThread, DateTime paramDateTime1, DateTime paramDateTime2, boolean paramBoolean)
  {
    String str1 = paramSecureUser.getBankID();
    if ((paramMessageThread != null) && (paramMessageThread.get("BANK_ID") != null)) {
      str1 = (String)paramMessageThread.get("BANK_ID");
    }
    if (!str1.equals("*")) {
      paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "m.", "bank_id", str1, false, false, paramBoolean, false);
    }
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "m.", "case_num", paramMessageThread.getCaseNum(), true, false, paramBoolean);
    if (paramHashMap != null)
    {
      str2 = (String)paramHashMap.get("RETRIEVE_FOR");
      if (str2 != null) {
        if (str2.equals("INDIVIDUAL") == true)
        {
          paramBoolean = jdMethod_for(paramStringBuffer, paramBoolean);
          paramStringBuffer.append("m.employee_id=?");
        }
        else if (str2.equals("TEAM") == true)
        {
          paramBoolean = jdMethod_for(paramStringBuffer, paramBoolean);
          paramStringBuffer.append("((m.employee_id=?) or (m.employee_id in (select employee_id from bank_employee where supervisor=?)))");
        }
      }
    }
    if (paramUser != null)
    {
      paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "c.", "low_first_name", paramUser.getFirstNameLowerCase(), true, false, paramBoolean);
      paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "c.", "low_last_name", paramUser.getLastNameLowerCase(), true, false, paramBoolean);
      paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "c.", "user_name", paramUser.getUserName(), true, false, paramBoolean);
    }
    if (paramBusiness != null)
    {
      str2 = paramBusiness.getBusinessNameLowerCase();
      if ((str2 != null) && (str2.length() > 0))
      {
        paramBoolean = jdMethod_for(paramStringBuffer, paramBoolean);
        paramStringBuffer.append("(c.directory_id = be.directory_id) and (be.business_id = b.business_id)");
        paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "b.", "low_business_name", str2, true, false, paramBoolean);
      }
    }
    String str2 = paramMessageThread.getThreadStatus();
    if ((str2 == null) || (str2.equalsIgnoreCase("0")) || (str2.length() == 0))
    {
      paramBoolean = jdMethod_for(paramStringBuffer, paramBoolean);
      paramStringBuffer.append("m.status != 5");
    }
    else
    {
      paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "m.", "status", str2, paramBoolean);
    }
    if (paramDateTime1 != null) {
      paramBoolean = Profile.appendSQLSelectDate(paramStringBuffer, "m.", "create_date", paramDateTime1.getTime(), ">=", paramBoolean);
    }
    if (paramDateTime2 != null) {
      paramBoolean = Profile.appendSQLSelectDate(paramStringBuffer, "m.", "create_date", paramDateTime2.getTime(), "<", paramBoolean);
    }
    if (paramMessageThread.getQueueID() != null) {
      paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "m.", "queue_id", paramMessageThread.getQueueID(), false, false, true);
    } else {
      paramStringBuffer.append(" and m.queue_id!=0");
    }
    return paramBoolean;
  }
  
  private static boolean jdMethod_else(StringBuffer paramStringBuffer, int paramInt, boolean paramBoolean)
  {
    paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "business_id", paramInt, paramBoolean);
    return paramBoolean;
  }
  
  private static boolean jdMethod_long(StringBuffer paramStringBuffer, int paramInt, boolean paramBoolean)
  {
    paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "c.", "directory_id", paramInt, paramBoolean);
    return paramBoolean;
  }
  
  private static boolean jdMethod_case(StringBuffer paramStringBuffer, int paramInt, boolean paramBoolean)
  {
    paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "employee_id", paramInt, paramBoolean);
    return paramBoolean;
  }
  
  private static boolean jdMethod_for(StringBuffer paramStringBuffer, SecureUser paramSecureUser, HashMap paramHashMap, String paramString, boolean paramBoolean)
  {
    String str = paramSecureUser.getBankID();
    if ((paramHashMap != null) && (paramHashMap.get("BANK_ID") != null)) {
      str = (String)paramHashMap.get("BANK_ID");
    }
    if (!str.equals("*")) {
      paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "m.", "bank_id", str, false, false, paramBoolean);
    }
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "m.", "message_id", paramString, false, false, paramBoolean);
    if (!paramBoolean)
    {
      paramStringBuffer.append(" where ");
      paramBoolean = true;
    }
    paramStringBuffer.append("ORDER BY mi.create_date desc, mi.item_id desc");
    return paramBoolean;
  }
  
  private static boolean jdMethod_char(StringBuffer paramStringBuffer, int paramInt, boolean paramBoolean)
  {
    paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "queue_id", paramInt, paramBoolean);
    return paramBoolean;
  }
  
  private static boolean jdMethod_for(StringBuffer paramStringBuffer, SecureUser paramSecureUser, boolean paramBoolean)
  {
    paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "to_id", paramSecureUser.getProfileID(), paramBoolean);
    paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "to_type", paramSecureUser.getUserType(), paramBoolean);
    return paramBoolean;
  }
  
  private static boolean jdMethod_goto(StringBuffer paramStringBuffer, int paramInt, boolean paramBoolean)
  {
    paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "item_id", paramInt, paramBoolean);
    return paramBoolean;
  }
  
  private static boolean jdMethod_new(StringBuffer paramStringBuffer, int paramInt, boolean paramBoolean)
  {
    paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "personal_banker", paramInt, paramBoolean);
    return paramBoolean;
  }
  
  private static boolean jdMethod_byte(StringBuffer paramStringBuffer, int paramInt, boolean paramBoolean)
  {
    paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "be.", "business_id", paramInt, paramBoolean);
    return paramBoolean;
  }
  
  private static int jdMethod_for(PreparedStatement paramPreparedStatement, int paramInt1, int paramInt2, String paramString, int paramInt3)
    throws Exception
  {
    paramInt3 = Profile.setStatementInt(paramPreparedStatement, paramInt3, paramInt1, true);
    paramInt3 = Profile.setStatementInt(paramPreparedStatement, paramInt3, paramInt2, true);
    paramInt3 = Profile.setStatementString(paramPreparedStatement, paramInt3, paramString, "text", false, false, true, true);
    return paramInt3;
  }
  
  private static int jdMethod_for(PreparedStatement paramPreparedStatement, Message paramMessage, int paramInt1, int paramInt2, int paramInt3)
    throws Exception
  {
    paramInt3 = Profile.setStatementInt(paramPreparedStatement, paramInt3, Profile.convertToInt(paramMessage.getID()), true);
    paramInt3 = Profile.setStatementInt(paramPreparedStatement, paramInt3, Profile.convertToInt(paramMessage.getMsgThreadID()), true);
    paramInt3 = Profile.setStatementInt(paramPreparedStatement, paramInt3, paramInt1, false);
    paramInt3 = Profile.setStatementInt(paramPreparedStatement, paramInt3, paramInt2, false);
    paramInt3 = Profile.setStatementInt(paramPreparedStatement, paramInt3, paramMessage.getToValue(), true);
    paramInt3 = Profile.setStatementInt(paramPreparedStatement, paramInt3, paramMessage.getToTypeValue(), false);
    paramInt3 = Profile.setStatementInt(paramPreparedStatement, paramInt3, paramMessage.getFromValue(), true);
    paramInt3 = Profile.setStatementInt(paramPreparedStatement, paramInt3, paramMessage.getFromTypeValue(), false);
    paramInt3 = Profile.setStatementInt(paramPreparedStatement, paramInt3, paramMessage.getTypeValue(), true);
    paramInt3 = Profile.setStatementDate(paramPreparedStatement, paramInt3, paramMessage.getCreateDateAsTimestamp(), true);
    return paramInt3;
  }
  
  private static int jdMethod_int(PreparedStatement paramPreparedStatement, int paramInt1, int paramInt2, String paramString, int paramInt3)
    throws Exception
  {
    paramInt3 = Profile.setStatementInt(paramPreparedStatement, paramInt3, paramInt1, true);
    paramInt3 = Profile.setStatementInt(paramPreparedStatement, paramInt3, paramInt2, true);
    paramInt3 = Profile.setStatementString(paramPreparedStatement, paramInt3, paramString, "text", false, false, true, true);
    return paramInt3;
  }
  
  private static int jdMethod_for(PreparedStatement paramPreparedStatement, MessageThread paramMessageThread, String paramString, int paramInt)
    throws Exception
  {
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, Profile.convertToInt(paramMessageThread.getThreadID()), true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, Profile.convertToInt(paramMessageThread.getEmployeeID()), true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, Profile.convertToInt(paramMessageThread.getDirectoryID()), false);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, Profile.convertToInt(paramMessageThread.getQueueID()), false);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramMessageThread.getSubject(), "subject", false, false, true, true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, Profile.convertToInt(paramMessageThread.getThreadStatus()), true);
    paramInt = Profile.setStatementDate(paramPreparedStatement, paramInt, paramMessageThread.getCreateDateAsTimestamp(), true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramString, "bank_id", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramMessageThread.getCaseNum(), "case_num", true);
    return paramInt;
  }
  
  private static int jdMethod_for(PreparedStatement paramPreparedStatement, SecureUser paramSecureUser, HashMap paramHashMap, User paramUser, Business paramBusiness, MessageThread paramMessageThread, DateTime paramDateTime1, DateTime paramDateTime2, int paramInt)
    throws Exception
  {
    String str1 = paramSecureUser.getBankID();
    if ((paramMessageThread != null) && (paramMessageThread.get("BANK_ID") != null)) {
      str1 = (String)paramMessageThread.get("BANK_ID");
    }
    if (!str1.equals("*")) {
      paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, str1, "bank_id", false, false, true, false);
    }
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramMessageThread.getCaseNum(), "case_num", true, false, true);
    Object localObject;
    if (paramHashMap != null)
    {
      str2 = (String)paramHashMap.get("RETRIEVE_FOR");
      if (str2 != null)
      {
        localObject = paramMessageThread.getEmployeeID();
        if (str2.equals("INDIVIDUAL") == true)
        {
          paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, (String)localObject, false);
        }
        else if (str2.equals("TEAM") == true)
        {
          paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, (String)localObject, false);
          paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, (String)localObject, false);
        }
      }
    }
    if (paramUser != null)
    {
      paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getFirstNameLowerCase(), "low_first_name", true, false, true);
      paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getLastNameLowerCase(), "low_last_name", true, false, true);
      paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getUserName(), "user_name", true, false, true);
    }
    if (paramBusiness != null) {
      paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusiness.getBusinessNameLowerCase(), "low_business_name", true, false, true, true);
    }
    String str2 = paramMessageThread.getThreadStatus();
    if ((str2 != null) && (!str2.equalsIgnoreCase("0")) && (str2.length() > 0)) {
      paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, str2, true);
    }
    if (paramDateTime1 != null)
    {
      localObject = new Timestamp(paramDateTime1.getTime().getTime());
      paramInt = Profile.setStatementDate(paramPreparedStatement, paramInt, localObject, true);
    }
    if (paramDateTime2 != null)
    {
      localObject = (DateTime)paramDateTime2.clone();
      ((DateTime)localObject).add(5, 1);
      paramDateTime2.set(11, 0);
      paramDateTime2.set(12, 0);
      paramDateTime2.set(13, 0);
      Timestamp localTimestamp = new Timestamp(((DateTime)localObject).getTime().getTime());
      paramInt = Profile.setStatementDate(paramPreparedStatement, paramInt, localTimestamp, true);
    }
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, Profile.convertToInt(paramMessageThread.getQueueID()), true);
    return paramInt;
  }
  
  private static int jdMethod_for(PreparedStatement paramPreparedStatement, int paramInt1, int paramInt2)
    throws Exception
  {
    paramInt2 = Profile.setStatementInt(paramPreparedStatement, paramInt2, paramInt1, true);
    return paramInt2;
  }
  
  private static int jdMethod_try(PreparedStatement paramPreparedStatement, int paramInt1, int paramInt2)
    throws Exception
  {
    paramInt2 = Profile.setStatementInt(paramPreparedStatement, paramInt2, paramInt1, true);
    return paramInt2;
  }
  
  private static int jdMethod_new(PreparedStatement paramPreparedStatement, int paramInt1, int paramInt2)
    throws Exception
  {
    paramInt2 = Profile.setStatementInt(paramPreparedStatement, paramInt2, paramInt1, true);
    return paramInt2;
  }
  
  private static int jdMethod_for(PreparedStatement paramPreparedStatement, SecureUser paramSecureUser, HashMap paramHashMap, String paramString, int paramInt)
    throws Exception
  {
    String str = paramSecureUser.getBankID();
    if ((paramHashMap != null) && (paramHashMap.get("BANK_ID") != null)) {
      str = (String)paramHashMap.get("BANK_ID");
    }
    if (!str.equals("*")) {
      paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, str, "bank_id", true);
    }
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, Profile.convertToInt(paramString), true);
    return paramInt;
  }
  
  private static int jdMethod_int(PreparedStatement paramPreparedStatement, int paramInt1, int paramInt2)
    throws Exception
  {
    paramInt2 = Profile.setStatementInt(paramPreparedStatement, paramInt2, paramInt1, true);
    return paramInt2;
  }
  
  private static int jdMethod_for(PreparedStatement paramPreparedStatement, SecureUser paramSecureUser, int paramInt)
    throws Exception
  {
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramSecureUser.getProfileID(), true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramSecureUser.getUserType(), true);
    return paramInt;
  }
  
  private static int jdMethod_for(PreparedStatement paramPreparedStatement, Timestamp paramTimestamp, int paramInt1, int paramInt2)
    throws Exception
  {
    paramInt2 = Profile.setStatementDate(paramPreparedStatement, paramInt2, paramTimestamp, true);
    paramInt2 = Profile.setStatementInt(paramPreparedStatement, paramInt2, paramInt1, true);
    return paramInt2;
  }
  
  private static int jdMethod_byte(PreparedStatement paramPreparedStatement, int paramInt1, int paramInt2)
    throws Exception
  {
    paramInt2 = Profile.setStatementInt(paramPreparedStatement, paramInt2, paramInt1, true);
    return paramInt2;
  }
  
  private static int jdMethod_case(PreparedStatement paramPreparedStatement, int paramInt1, int paramInt2)
    throws Exception
  {
    paramInt2 = Profile.setStatementInt(paramPreparedStatement, paramInt2, paramInt1, true);
    return paramInt2;
  }
  
  protected Message processMessagesRS(Connection paramConnection, SecureUser paramSecureUser, ResultSet paramResultSet, HashMap paramHashMap1, HashMap paramHashMap2, Locale paramLocale)
    throws Exception
  {
    Message localMessage = new Message(paramSecureUser.getLocale());
    localMessage.setID(String.valueOf(paramResultSet.getInt("item_id")));
    localMessage.setMsgThreadID(String.valueOf(paramResultSet.getInt("message_id")));
    int i = paramResultSet.getInt("to_id");
    int j = paramResultSet.getInt("to_type");
    localMessage.setTo(String.valueOf(i));
    localMessage.setToType(String.valueOf(j));
    int k = paramResultSet.getInt("from_id");
    int m = paramResultSet.getInt("from_type");
    localMessage.setFrom(String.valueOf(k));
    localMessage.setFromType(String.valueOf(m));
    localMessage.setType(String.valueOf(paramResultSet.getInt("message_type")));
    Timestamp localTimestamp = null;
    localTimestamp = paramResultSet.getTimestamp("create_date");
    if (localTimestamp != null) {
      localMessage.setCreateDate(localTimestamp);
    }
    localTimestamp = paramResultSet.getTimestamp("read_date");
    if (localTimestamp != null) {
      localMessage.setReadDate(localTimestamp);
    }
    localTimestamp = paramResultSet.getTimestamp("delete_date");
    if (localTimestamp != null) {
      localMessage.setArchivedDate(localTimestamp);
    }
    localMessage.setSubject(Profile.getRSString(paramResultSet, "subject"));
    localMessage.setCaseNum(Profile.getRSString(paramResultSet, "case_num"));
    int n = paramResultSet.getInt("body_id");
    int i1 = paramResultSet.getInt("comment_id");
    if (Profile.isValidId(n)) {
      localMessage.setMemo(getMessageBody(paramConnection, n));
    }
    if (Profile.isValidId(i1)) {
      localMessage.setComment(getMessageComment(paramConnection, i1));
    }
    String str1 = null;
    if (j == 0) {
      str1 = getPersonalName(paramConnection, i, j, localMessage.getLocale(), paramHashMap2, null);
    } else if (j == 1) {
      str1 = getPersonalName(paramConnection, i, j, localMessage.getLocale(), paramHashMap1, null);
    }
    localMessage.setToName(str1);
    String str2 = null;
    if (m == 0)
    {
      str2 = getPersonalName(paramConnection, k, m, localMessage.getLocale(), paramHashMap2, null);
    }
    else if (m == 1)
    {
      str2 = getPersonalName(paramConnection, k, m, localMessage.getLocale(), paramHashMap1, null);
    }
    else if (m == 3)
    {
      Locale localLocale = jdMethod_for(paramSecureUser, null);
      String str3 = paramLocale.getLanguage() + "_" + paramLocale.getCountry().toUpperCase();
      GlobalMessage localGlobalMessage = getGlobalMsgByIdTX(paramConnection, k, paramLocale, str3, null);
      GlobalMessages localGlobalMessages = new GlobalMessages();
      localGlobalMessages.add(localGlobalMessage);
      populateI18NGlobalMsgListTX(paramConnection, localGlobalMessages, str3, null);
      localMessage.setFrom(localGlobalMessage.getFromID());
      localMessage.setFromType(String.valueOf(0));
      str2 = localGlobalMessage.getFromName(str3);
    }
    localMessage.setFromName(str2);
    return localMessage;
  }
  
  protected MessageThread processMessageThreadsRS(SecureUser paramSecureUser, ResultSet paramResultSet)
    throws Exception
  {
    MessageThread localMessageThread = new MessageThread(paramSecureUser.getLocale());
    localMessageThread.setThreadID(String.valueOf(paramResultSet.getInt("message_id")));
    localMessageThread.setQueueID(String.valueOf(paramResultSet.getInt("queue_id")));
    localMessageThread.setEmployeeID(String.valueOf(paramResultSet.getInt("employee_id")));
    localMessageThread.setDirectoryID(String.valueOf(paramResultSet.getInt("directory_id")));
    localMessageThread.setSubject(Profile.getRSString(paramResultSet, "subject"));
    localMessageThread.setThreadStatus(String.valueOf(paramResultSet.getInt("status")));
    Timestamp localTimestamp = null;
    localTimestamp = paramResultSet.getTimestamp("create_date");
    if (localTimestamp != null) {
      localMessageThread.setCreateDate(localTimestamp);
    }
    localTimestamp = paramResultSet.getTimestamp("modified_date");
    if (localTimestamp != null) {
      localMessageThread.setReadDate(localTimestamp);
    }
    localTimestamp = paramResultSet.getTimestamp("close_date");
    if (localTimestamp != null) {
      localMessageThread.setClosedDate(localTimestamp);
    }
    localMessageThread.setCaseNum(Profile.getRSString(paramResultSet, "case_num"));
    localMessageThread.setDirectoryName(UserUtil.getFullNameWithLoginId(Profile.getRSString(paramResultSet, "first_name"), Profile.getRSString(paramResultSet, "last_name"), Profile.getRSString(paramResultSet, "user_name"), Profile.getRSString(paramResultSet, "cust_id"), localMessageThread.getLocale()));
    localMessageThread.setDirectoryEmail(Profile.getRSString(paramResultSet, "email_address"));
    return localMessageThread;
  }
  
  protected String getUnreadThreadMessageCount(Connection paramConnection, SecureUser paramSecureUser, HashMap paramHashMap, String paramString)
    throws ProfileException
  {
    String str = "MessageAdapter.getUnreadThreadMessageCount";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i = 0;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "SELECT COUNT( mi.item_id ) AS MESSAGE_COUNT FROM message_item mi WHERE mi.message_id = ? AND mi.to_id = ? AND mi.to_type = ? AND mi.read_date is null AND mi.delete_date is null");
      localPreparedStatement.setInt(1, Profile.convertToInt(paramString));
      localPreparedStatement.setInt(2, paramSecureUser.getProfileID());
      if (paramSecureUser.getUserType() == 2) {
        localPreparedStatement.setInt(3, 0);
      } else {
        localPreparedStatement.setInt(3, 1);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT COUNT( mi.item_id ) AS MESSAGE_COUNT FROM message_item mi WHERE mi.message_id = ? AND mi.to_id = ? AND mi.to_type = ? AND mi.read_date is null AND mi.delete_date is null");
      if (localResultSet.next()) {
        i = localResultSet.getInt(1);
      } else {
        throw new Exception("Invalid ResultSet returned.");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return Integer.toString(i);
  }
  
  public void approveMessage(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage)
    throws ProfileException
  {
    approveMessage(paramSecureUser, paramHashMap, paramMessage, null, null, null, false);
  }
  
  public void approveMessage(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
    throws ProfileException
  {
    Profile.isInitialized();
    String str1 = "MessageAdapter.approveMessage";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str1, "Invalid BankID", 4);
    }
    if (!Profile.isValidId(paramSecureUser.getProfileID())) {
      Profile.handleError(str1, "Invalid user ID", 4);
    }
    String str2 = paramMessage.getMemo();
    if ((str2 == null) || (str2.trim().length() == 0)) {
      Profile.handleError(str1, "Message text is required.", 3610);
    }
    try
    {
      Message localMessage = null;
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT m.directory_id, mi.item_id, mi.from_id, mi.message_type FROM message_item mi, message m WHERE m.message_id = mi.message_id AND mi.item_id IN ( SELECT MAX( mi.item_id ) FROM message_item mi WHERE mi.message_id = ? )");
      String str3 = paramMessage.getMsgThreadID();
      localPreparedStatement.setInt(1, Profile.convertToInt(str3));
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT m.directory_id, mi.item_id, mi.from_id, mi.message_type FROM message_item mi, message m WHERE m.message_id = mi.message_id AND mi.item_id IN ( SELECT MAX( mi.item_id ) FROM message_item mi WHERE mi.message_id = ? )");
      if (localResultSet.next() == true)
      {
        int i = localResultSet.getInt("directory_id");
        int j = localResultSet.getInt("item_id");
        int k = localResultSet.getInt("from_id");
        int m = localResultSet.getInt("message_type");
        int n = addMessageBody(localConnection, str2);
        localMessage = new Message();
        localMessage.setMsgThreadID(str3);
        localMessage.setTo(String.valueOf(i));
        localMessage.setToType(String.valueOf(1));
        localMessage.setFrom(String.valueOf(k));
        localMessage.setFromType(String.valueOf(0));
        localMessage.setType(String.valueOf(m));
        addMessage(localConnection, localMessage, n, 0);
        modifyMessageType(localConnection, j, 11);
        int i1;
        if ((m == 4) || (m == 1))
        {
          i1 = 4;
        }
        else if (m == 5)
        {
          i1 = 5;
        }
        else
        {
          DBUtil.rollback(localConnection);
          throw new Exception("Unexpected Message type found.");
        }
        modifyMessageThreadStatus(localConnection, Profile.convertToInt(str3), i1);
      }
      else
      {
        DBUtil.rollback(localConnection);
        throw new Exception("Invalid ResultSet returned.");
      }
      DBUtil.commit(localConnection);
      if (paramBoolean)
      {
        HashMap localHashMap = new HashMap();
        getCustomerNameByID(localConnection, Profile.convertToInt(localMessage.getTo()), localMessage.getLocale(), localHashMap);
        String str4 = (String)localHashMap.get("EMAIL_ADDRESS");
        if (str4 == null) {
          DebugLog.log(Level.INFO, str1 + ": An email notification could not be sent " + "because the recipient's email address is missing.");
        } else {
          com.ffusion.csil.handlers.Messages._mailService.sendMessage(str4, paramString1, paramString2, paramString3);
        }
      }
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      localException.printStackTrace();
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  public void denyMessage(SecureUser paramSecureUser, HashMap paramHashMap, MessageThread paramMessageThread)
    throws ProfileException
  {
    Profile.isInitialized();
    String str = "MessageAdapter.denyMessage";
    Profile.handleError(str, "Not Implemented", 13);
  }
  
  public MessageCounts getAssignedMessageCountsByEmployee(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws ProfileException
  {
    return getAssignedMessageCountsByEmployee(paramSecureUser, paramString, paramHashMap, 0);
  }
  
  public MessageCounts getAssignedMessageCountsByEmployee(SecureUser paramSecureUser, String paramString, HashMap paramHashMap, int paramInt)
    throws ProfileException
  {
    Profile.isInitialized();
    String str1 = "MessageAdapter.getAssignedMessageCountsByEmployee";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    MessageCounts localMessageCounts = null;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str1, "Invalid Bank ID", 4);
    }
    if (!Profile.isValidId(paramString)) {
      Profile.handleError(str1, "Invalid Employee ID", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      String str2;
      if (paramInt > 0) {
        str2 = "SELECT m.queue_id, COUNT( m.message_id ) AS MESSAGE_COUNT FROM message m WHERE ( NOT m.status = 5 )  AND m.employee_id = ? AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id ))  GROUP BY m.queue_id";
      } else {
        str2 = "SELECT m.queue_id, COUNT( m.message_id ) AS MESSAGE_COUNT FROM message m WHERE ( NOT m.status = 5 )  AND m.employee_id = ? GROUP BY m.queue_id";
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      localPreparedStatement.setInt(1, Profile.convertToInt(paramString));
      if (paramInt > 0)
      {
        localPreparedStatement.setInt(2, paramInt);
        localPreparedStatement.setInt(3, paramInt);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      localMessageCounts = new MessageCounts();
      while (localResultSet.next() == true)
      {
        int i = localResultSet.getInt("queue_id");
        int j = localResultSet.getInt("MESSAGE_COUNT");
        if (j > 0)
        {
          MessageCount localMessageCount = new MessageCount();
          localMessageCount.setIdentifier(i);
          localMessageCount.setName(String.valueOf(i));
          localMessageCount.setCount(j);
          localMessageCounts.add(localMessageCount);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localMessageCounts;
  }
  
  public MessageCounts getAssignedMessageCountsByQueue(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws ProfileException
  {
    Profile.isInitialized();
    String str1 = "MessageAdapter.getAssignedMessageCountsByQueue";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    MessageCounts localMessageCounts = null;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str1, "Invalid Bank ID", 4);
    }
    if (!Profile.isValidId(paramString)) {
      Profile.handleError(str1, "Invalid Case Type ID", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      String str2 = "SELECT m.employee_id, COUNT( m.message_id ) AS MESSAGE_COUNT FROM message m WHERE ( NOT m.status = 5 )  AND m.queue_id = ? GROUP BY m.employee_id";
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      localPreparedStatement.setInt(1, Profile.convertToInt(paramString));
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      localMessageCounts = new MessageCounts();
      while (localResultSet.next() == true)
      {
        int i = localResultSet.getInt("employee_id");
        int j = localResultSet.getInt("MESSAGE_COUNT");
        if (j > 0)
        {
          MessageCount localMessageCount = new MessageCount();
          localMessageCount.setIdentifier(i);
          localMessageCount.setName(String.valueOf(i));
          localMessageCount.setCount(j);
          localMessageCounts.add(localMessageCount);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localMessageCounts;
  }
  
  public int getAssignedMessageCount(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    Profile.isInitialized();
    String str1 = "MessageAdapter.getAssignedMessageCount";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i = 0;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str1, "Invalid Bank ID", 4);
    }
    if (!Profile.isValidId(paramSecureUser.getProfileID())) {
      Profile.handleError(str1, "Invalid user ID", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      int j = paramSecureUser.getAffiliateIDValue();
      String str2;
      if (j > 0) {
        str2 = "SELECT COUNT( m.message_id ) AS MESSAGE_COUNT FROM message m WHERE ( NOT m.status = 5 )  AND ( m.employee_id = ? ) AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id )) ";
      } else {
        str2 = "SELECT COUNT( m.message_id ) AS MESSAGE_COUNT FROM message m WHERE ( NOT m.status = 5 )  AND ( m.employee_id = ? )";
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      localPreparedStatement.setInt(1, paramSecureUser.getProfileID());
      if (j > 0)
      {
        localPreparedStatement.setInt(2, j);
        localPreparedStatement.setInt(3, j);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      if (localResultSet.next()) {
        i = localResultSet.getInt("MESSAGE_COUNT");
      } else {
        throw new Exception("Invalid ResultSet returned.");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return i;
  }
  
  public MessageCounts getMessageCountsByHelpCasesProvidedAndClosed(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    Profile.isInitialized();
    String str1 = "MessageAdapter.getMessageCountsByHelpCasesProvidedAndClosed";
    String str2 = ResourceUtil.getString("HELP_CASE_DISPLAY_ASSISTANCE_PROVIDED", "com.ffusion.beans.messages.resources", paramSecureUser.getLocale());
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    MessageCounts localMessageCounts = null;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str1, "Invalid Bank ID", 4);
    }
    if (!Profile.isValidId(paramSecureUser.getProfileID())) {
      Profile.handleError(str1, "Invalid user ID", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      int i = paramSecureUser.getAffiliateIDValue();
      String str3;
      if (i > 0) {
        str3 = "SELECT  count(distinct m.message_id) FROM message m, message_item mi WHERE mi.message_id = m.message_id AND m.employee_id = ? AND (m.status = 5 OR m.status = 9 OR m.status = 12) AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id ))  AND mi.to_type = 1 AND mi.from_id != ? AND mi.message_type <> 9";
      } else {
        str3 = "SELECT  count(distinct m.message_id) FROM message m, message_item mi WHERE mi.message_id = m.message_id AND m.employee_id = ? AND (m.status = 5 OR m.status = 9 OR m.status = 12) AND mi.to_type = 1 AND mi.from_id != ? AND mi.message_type <> 9";
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str3);
      int j = 1;
      localPreparedStatement.setInt(j++, paramSecureUser.getProfileID());
      if (i > 0)
      {
        localPreparedStatement.setInt(j++, i);
        localPreparedStatement.setInt(j++, i);
      }
      localPreparedStatement.setInt(j++, paramSecureUser.getProfileID());
      localMessageCounts = new MessageCounts();
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str3);
      MessageCount localMessageCount = null;
      int k = 0;
      if (localResultSet.next())
      {
        k = localResultSet.getInt(1);
        if (k > 0)
        {
          localMessageCount = new MessageCount();
          localMessageCount.setCount(String.valueOf(k));
          localMessageCount.setName(str2);
          localMessageCounts.add(localMessageCount);
        }
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      localResultSet = null;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localMessageCounts;
  }
  
  public MessageCounts getMessageCountsByHelpCases(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    Profile.isInitialized();
    String str1 = "MessageAdapter.getMessageCountsByHelpCases";
    String str2 = ResourceUtil.getString("HELP_CASE_DISPLAY_HELP_REQUESTED", "com.ffusion.beans.messages.resources", paramSecureUser.getLocale());
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    MessageCounts localMessageCounts = null;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str1, "Invalid Bank ID", 4);
    }
    if (!Profile.isValidId(paramSecureUser.getProfileID())) {
      Profile.handleError(str1, "Invalid user ID", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      int i = paramSecureUser.getAffiliateIDValue();
      String str3;
      if (i > 0) {
        str3 = "SELECT count(distinct m.message_id) FROM message m, message_item mi WHERE m.employee_id != ? AND m.status != 5 AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id ))  AND mi.message_type = 3 AND mi.to_id = ? AND mi.to_type = 0 AND mi.delete_date is null AND m.message_id = mi.message_id";
      } else {
        str3 = "SELECT count(distinct m.message_id) FROM message m, message_item mi WHERE m.employee_id != ? AND m.status != 5 AND mi.message_type = 3 AND mi.to_id = ? AND mi.to_type = 0 AND mi.delete_date is null AND m.message_id = mi.message_id";
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str3);
      int j = 1;
      localPreparedStatement.setInt(j++, paramSecureUser.getProfileID());
      if (i > 0)
      {
        localPreparedStatement.setInt(j++, i);
        localPreparedStatement.setInt(j++, i);
      }
      localPreparedStatement.setInt(j++, paramSecureUser.getProfileID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str3);
      localMessageCounts = new MessageCounts();
      MessageCount localMessageCount = null;
      int k = 0;
      if (localResultSet.next())
      {
        k = localResultSet.getInt(1);
        if (k > 0)
        {
          localMessageCount = new MessageCount();
          localMessageCount.setCount(String.valueOf(k));
          localMessageCount.setName(str2);
          localMessageCounts.add(localMessageCount);
        }
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      localResultSet = null;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localMessageCounts;
  }
  
  public MessageCounts getMessageCountsByNewCases(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    Profile.isInitialized();
    String str1 = "MessageAdapter.getMessageCountsByNewCases";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    MessageCounts localMessageCounts = null;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str1, "Invalid Bank ID", 4);
    }
    if (!Profile.isValidId(paramSecureUser.getProfileID())) {
      Profile.handleError(str1, "Invalid user ID", 4);
    }
    String str2 = paramSecureUser.getLocaleLanguage();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      int i = paramSecureUser.getAffiliateIDValue();
      String str3;
      if (i > 0)
      {
        if (str2.equalsIgnoreCase("en_US")) {
          str3 = "SELECT q.queue_id, q.queue_name, COUNT( distinct m.message_id ) AS MESSAGE_COUNT FROM message m, queue q WHERE m.employee_id = ?  AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id ))  AND m.status = 1 AND q.queue_id = m.queue_id GROUP BY q.queue_id, q.queue_name";
        } else {
          str3 = "SELECT q.queue_id, q.queue_name, COUNT( distinct m.message_id ) AS MESSAGE_COUNT FROM message m, message_item mi, queue_i18n q WHERE m.employee_id = ?  AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id ))  AND m.status = 1 AND m.message_id = mi.message_id AND q.queue_id = m.queue_id GROUP BY q.queue_id, q.queue_name";
        }
      }
      else if (str2.equalsIgnoreCase("en_US")) {
        str3 = "SELECT q.queue_id, q.queue_name, COUNT( distinct m.message_id ) AS MESSAGE_COUNT FROM message m, queue q WHERE m.employee_id = ?  AND m.status = 1 AND q.queue_id = m.queue_id GROUP BY q.queue_id, q.queue_name";
      } else {
        str3 = "SELECT q.queue_id, q.queue_name, COUNT( distinct m.message_id ) AS MESSAGE_COUNT FROM message m, message_item mi, queue_i18n q WHERE m.employee_id = ?  AND m.status = 1 AND m.message_id = mi.message_id AND q.queue_id = m.queue_id GROUP BY q.queue_id, q.queue_name";
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str3);
      localPreparedStatement.setInt(1, paramSecureUser.getProfileID());
      if (i > 0)
      {
        localPreparedStatement.setInt(2, i);
        localPreparedStatement.setInt(3, i);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str3);
      localMessageCounts = new MessageCounts();
      MessageCount localMessageCount = null;
      String str4 = null;
      String str5 = null;
      while (localResultSet.next())
      {
        str4 = localResultSet.getString("queue_name");
        str5 = Integer.toString(localResultSet.getInt("MESSAGE_COUNT"));
        localMessageCount = new MessageCount();
        localMessageCount.setName(str4);
        localMessageCount.setCount(str5);
        localMessageCounts.add(localMessageCount);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localMessageCounts;
  }
  
  public MessageCounts getMessageCountsByPersonalBanker(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    Profile.isInitialized();
    String str1 = "MessageAdapter.getMessageCountsByPersonalBanker";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    MessageCounts localMessageCounts = null;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str1, "Invalid Bank ID", 4);
    }
    if (!Profile.isValidId(paramSecureUser.getProfileID())) {
      Profile.handleError(str1, "Invalid user ID", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      int i = paramSecureUser.getAffiliateIDValue();
      String str2;
      String str3;
      if (i > 0)
      {
        str2 = "SELECT c.first_name AS first_name, c.last_name AS last_name,c.user_name AS user_name, COUNT( m.message_id ) AS MESSAGE_COUNT, cd.cust_id AS CUSTID FROM customer c, message m, customer_directory cd WHERE c.personal_banker = ? AND c.affiliate_bank_id = ? AND c.directory_id = m.directory_id AND c.directory_id = cd.directory_id AND ( NOT m.status = 5 ) GROUP BY c.first_name, c.last_name, c.user_name, cd.cust_id";
        str3 = "SELECT b.business_name AS NAME, COUNT( m.message_id ) AS MESSAGE_COUNT, cd.cust_id AS CUSTID FROM business b, business_employee be, message m, customer_directory cd WHERE b.personal_banker = ? AND b.affiliate_bank_id = ? AND b.business_id = be.business_id AND be.directory_id = m.directory_id AND b.directory_id = cd.directory_id AND ( NOT m.status = 5 ) GROUP BY b.business_name, cd.cust_id";
      }
      else
      {
        str2 = "SELECT c.first_name AS first_name, c.last_name AS last_name,c.user_name AS user_name, COUNT( m.message_id ) AS MESSAGE_COUNT, cd.cust_id AS CUSTID FROM customer c, message m, customer_directory cd WHERE c.personal_banker = ? AND c.directory_id = m.directory_id AND c.directory_id = cd.directory_id AND ( NOT m.status = 5 ) GROUP BY c.first_name, c.last_name, c.user_name, cd.cust_id";
        str3 = "SELECT b.business_name AS NAME, COUNT( m.message_id ) AS MESSAGE_COUNT, cd.cust_id AS CUSTID FROM business b, business_employee be, message m, customer_directory cd WHERE b.personal_banker = ? AND b.business_id = be.business_id AND b.directory_id = cd.directory_id AND be.directory_id = m.directory_id AND ( NOT m.status = 5 ) GROUP BY b.business_name, cd.cust_id";
      }
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, str2);
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, str3);
      int j = 1;
      int k = 1;
      localPreparedStatement1.setInt(j++, paramSecureUser.getProfileID());
      localPreparedStatement2.setInt(k++, paramSecureUser.getProfileID());
      if (i > 0)
      {
        localPreparedStatement1.setInt(j++, i);
        localPreparedStatement2.setInt(k++, i);
      }
      localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, str2);
      localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, str3);
      localMessageCounts = new MessageCounts();
      MessageCount localMessageCount = null;
      String str4 = null;
      String str5 = null;
      String str6 = null;
      String str7 = null;
      String str8 = null;
      String str9 = null;
      StringBuffer localStringBuffer = new StringBuffer();
      while (localResultSet1.next())
      {
        str5 = localResultSet1.getString("FIRST_NAME");
        str6 = localResultSet1.getString("LAST_NAME");
        str7 = localResultSet1.getString("USER_NAME");
        str8 = Integer.toString(localResultSet1.getInt("MESSAGE_COUNT"));
        str9 = localResultSet1.getString("CUSTID");
        str4 = UserUtil.getFullNameWithLoginId(str5, str6, str7, str9, paramSecureUser.getLocale());
        localMessageCount = new MessageCount();
        localMessageCount.setName(str4);
        localMessageCount.setCount(str8);
        localMessageCounts.add(localMessageCount);
      }
      while (localResultSet2.next())
      {
        str4 = localResultSet2.getString("NAME");
        str8 = Integer.toString(localResultSet2.getInt("MESSAGE_COUNT"));
        str9 = localResultSet2.getString("CUSTID");
        localStringBuffer.delete(0, localStringBuffer.length());
        localStringBuffer.append(str4);
        if (str9 != null)
        {
          str9 = str9.trim();
          if (str9.length() > 0) {
            localStringBuffer.append("(").append(str9).append(")");
          }
        }
        localMessageCount = new MessageCount();
        localMessageCount.setName(localStringBuffer.toString());
        localMessageCount.setCount(str8);
        localMessageCounts.add(localMessageCount);
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      Profile.handleError(localException1, str1);
    }
    finally
    {
      try
      {
        DBUtil.closeResultSet(localResultSet1);
        DBUtil.closeStatement(localPreparedStatement1);
      }
      catch (Exception localException2) {}
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement2, localResultSet2);
    }
    return localMessageCounts;
  }
  
  public MessageCounts getMessageCountsByPending(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    Profile.isInitialized();
    String str1 = "MessageAdapter.getMessageCountsByPending";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    MessageCounts localMessageCounts = null;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str1, "Invalid Bank ID", 4);
    }
    if (!Profile.isValidId(paramSecureUser.getProfileID())) {
      Profile.handleError(str1, "Invalid user ID", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      int i = paramSecureUser.getAffiliateIDValue();
      int j = 1;
      String str2;
      if (i > 0) {
        str2 = "SELECT be.bank_employee_id AS bank_employee_id, be.first_name AS first_name, be.last_name AS last_name, be.user_name AS user_name, COUNT( m.message_id ) AS MESSAGE_COUNT FROM bank_employee be, message m, message_item mi WHERE ( m.status = 9 OR m.status = 12 ) AND mi.to_id = ? AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id ))  AND mi.to_type = 0 AND mi.from_type = 0 AND mi.from_id = be.employee_id AND m.message_id = mi.message_id AND mi.item_id IN ( SELECT MAX( mi.item_id ) FROM message_item mi WHERE mi.to_id = ? AND mi.to_type = 0 AND mi.from_type = 0 GROUP BY mi.message_id) GROUP BY be.bank_employee_id, be.first_name, be.last_name, be.user_name";
      } else {
        str2 = "SELECT be.bank_employee_id AS bank_employee_id, be.first_name AS first_name, be.last_name AS last_name, be.user_name AS user_name, COUNT( m.message_id ) AS MESSAGE_COUNT FROM bank_employee be, message m, message_item mi WHERE ( m.status = 9 OR m.status = 12 ) AND mi.to_id = ? AND mi.to_type = 0 AND mi.from_type = 0 AND mi.from_id = be.employee_id AND m.message_id = mi.message_id AND mi.item_id IN ( SELECT MAX( mi.item_id ) FROM message_item mi WHERE mi.to_id = ? AND mi.to_type = 0 AND mi.from_type = 0 GROUP BY mi.message_id) GROUP BY be.bank_employee_id, be.first_name, be.last_name, be.user_name";
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      localPreparedStatement.setInt(j++, paramSecureUser.getProfileID());
      if (i > 0)
      {
        localPreparedStatement.setInt(j++, i);
        localPreparedStatement.setInt(j++, i);
      }
      localPreparedStatement.setInt(j++, paramSecureUser.getProfileID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      localMessageCounts = new MessageCounts();
      MessageCount localMessageCount = null;
      String str3 = null;
      String str4 = null;
      String str5 = null;
      String str6 = null;
      String str7 = null;
      String str8 = null;
      while (localResultSet.next())
      {
        str3 = localResultSet.getString("first_name");
        str4 = localResultSet.getString("last_name");
        str5 = localResultSet.getString("user_name");
        str6 = localResultSet.getString("bank_employee_id");
        str7 = UserUtil.getFullNameWithLoginId(str3, str4, str5, str6, paramSecureUser.getLocale());
        str8 = Integer.toString(localResultSet.getInt("MESSAGE_COUNT"));
        localMessageCount = new MessageCount();
        localMessageCount.setName(str7);
        localMessageCount.setCount(str8);
        localMessageCounts.add(localMessageCount);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localMessageCounts;
  }
  
  public MessageThreads getThreadsByAssigned(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    Profile.isInitialized();
    String str1 = "MessageAdapter.getThreadsByAssigned";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    MessageThreads localMessageThreads = null;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str1, "Invalid Bank ID", 4);
    }
    if (!Profile.isValidId(paramSecureUser.getProfileID())) {
      Profile.handleError(str1, "Invalid user ID", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      int i = paramSecureUser.getAffiliateIDValue();
      String str2;
      if (i > 0) {
        str2 = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, customer_directory cd WHERE m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND ( NOT m.status = 5 ) AND ( m.employee_id = ? ) AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id )) ";
      } else {
        str2 = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, customer_directory cd WHERE m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND ( NOT m.status = 5 ) AND ( m.employee_id = ? )";
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      if (Profile.useMaxRows(paramHashMap)) {
        localPreparedStatement.setMaxRows(Profile.getMaxRowCount(paramHashMap));
      }
      localPreparedStatement.setInt(1, paramSecureUser.getProfileID());
      if (i > 0)
      {
        localPreparedStatement.setInt(2, i);
        localPreparedStatement.setInt(3, i);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      localMessageThreads = new MessageThreads(paramSecureUser.getLocale());
      MessageThread localMessageThread = null;
      String str3 = null;
      while (localResultSet.next())
      {
        localMessageThread = processMessageThreadsRS(paramSecureUser, localResultSet);
        str3 = getUnreadThreadMessageCount(localConnection, paramSecureUser, paramHashMap, localMessageThread.getThreadID());
        localMessageThread.setNewThreadStatus(str3);
        localMessageThreads.add(localMessageThread);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localMessageThreads;
  }
  
  public MessageThreads getThreadsByHelpProvidedAndClosed(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    Profile.isInitialized();
    String str1 = "MessageAdapter.getThreadsByHelpProvidedAndClosed";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    MessageThreads localMessageThreads = null;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str1, "Invalid Bank ID", 4);
    }
    if (!Profile.isValidId(paramSecureUser.getProfileID())) {
      Profile.handleError(str1, "Invalid user ID", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      int i = paramSecureUser.getAffiliateIDValue();
      String str2;
      if (i > 0) {
        str2 = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, message_item mi, customer_directory cd WHERE m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND mi.message_id = m.message_id AND m.employee_id = ? AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id ))  AND (m.status = 5 OR m.status = 9 OR m.status = 12) AND mi.to_type = 1 AND mi.from_id != ?";
      } else {
        str2 = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, message_item mi, customer_directory cd WHERE m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND mi.message_id = m.message_id AND m.employee_id = ? AND (m.status = 5 OR m.status = 9 OR m.status = 12) AND mi.to_type = 1 AND mi.from_id != ?";
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      if (Profile.useMaxRows(paramHashMap)) {
        localPreparedStatement.setMaxRows(Profile.getMaxRowCount(paramHashMap));
      }
      int j = 1;
      localPreparedStatement.setInt(j++, paramSecureUser.getProfileID());
      if (i > 0)
      {
        localPreparedStatement.setInt(j++, i);
        localPreparedStatement.setInt(j++, i);
      }
      localPreparedStatement.setInt(j++, paramSecureUser.getProfileID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      localMessageThreads = new MessageThreads(paramSecureUser.getLocale());
      MessageThread localMessageThread = null;
      String str3 = null;
      while (localResultSet.next())
      {
        localMessageThread = processMessageThreadsRS(paramSecureUser, localResultSet);
        str3 = getUnreadThreadMessageCount(localConnection, paramSecureUser, paramHashMap, localMessageThread.getThreadID());
        localMessageThread.setNewThreadStatus(str3);
        localMessageThreads.add(localMessageThread);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localMessageThreads;
  }
  
  public MessageThreads getThreadsByHelpRequested(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    Profile.isInitialized();
    String str1 = "MessageAdapter.getThreadsByHelpRequested";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    MessageThreads localMessageThreads = null;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str1, "Invalid Bank ID", 4);
    }
    if (!Profile.isValidId(paramSecureUser.getProfileID())) {
      Profile.handleError(str1, "Invalid user ID", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      int i = paramSecureUser.getAffiliateIDValue();
      String str2;
      if (i > 0) {
        str2 = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, message_item mi, customer_directory cd WHERE m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND m.employee_id != ?  AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id ))  AND m.status != 5 AND mi.message_type = 3 AND mi.delete_date is null AND mi.message_id = m.message_id AND mi.to_id = ? AND mi.to_type = 0";
      } else {
        str2 = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, message_item mi, customer_directory cd WHERE m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND m.employee_id != ?  AND m.status != 5 AND mi.message_type = 3 AND mi.delete_date is null AND mi.message_id = m.message_id AND mi.to_id = ? AND mi.to_type = 0";
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      if (Profile.useMaxRows(paramHashMap)) {
        localPreparedStatement.setMaxRows(Profile.getMaxRowCount(paramHashMap));
      }
      int j = 1;
      localPreparedStatement.setInt(j++, paramSecureUser.getProfileID());
      if (i > 0)
      {
        localPreparedStatement.setInt(j++, i);
        localPreparedStatement.setInt(j++, i);
      }
      localPreparedStatement.setInt(j++, paramSecureUser.getProfileID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      localMessageThreads = new MessageThreads(paramSecureUser.getLocale());
      MessageThread localMessageThread = null;
      String str3 = null;
      while (localResultSet.next())
      {
        localMessageThread = processMessageThreadsRS(paramSecureUser, localResultSet);
        str3 = getUnreadThreadMessageCount(localConnection, paramSecureUser, paramHashMap, localMessageThread.getThreadID());
        localMessageThread.setNewThreadStatus(str3);
        localMessageThreads.add(localMessageThread);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localMessageThreads;
  }
  
  public MessageThreads getThreadsByNewCases(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    Profile.isInitialized();
    String str1 = "MessageAdapter.getThreadsByNewCases";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    MessageThreads localMessageThreads = null;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str1, "Invalid Bank ID", 4);
    }
    if (!Profile.isValidId(paramSecureUser.getProfileID())) {
      Profile.handleError(str1, "Invalid user ID", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      int i = paramSecureUser.getAffiliateIDValue();
      String str2;
      if (i > 0) {
        str2 = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, message_item mi, customer_directory cd WHERE m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND m.employee_id = ? AND m.status = 1 AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id )) ";
      } else {
        str2 = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, message_item mi, customer_directory cd WHERE m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND m.employee_id = ? AND m.status = 1";
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      if (Profile.useMaxRows(paramHashMap)) {
        localPreparedStatement.setMaxRows(Profile.getMaxRowCount(paramHashMap));
      }
      localPreparedStatement.setInt(1, paramSecureUser.getProfileID());
      if (i > 0)
      {
        localPreparedStatement.setInt(2, i);
        localPreparedStatement.setInt(3, i);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, message_item mi, customer_directory cd WHERE m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND m.employee_id = ? AND m.status = 1");
      localMessageThreads = new MessageThreads(paramSecureUser.getLocale());
      MessageThread localMessageThread = null;
      String str3 = null;
      while (localResultSet.next())
      {
        localMessageThread = processMessageThreadsRS(paramSecureUser, localResultSet);
        str3 = getUnreadThreadMessageCount(localConnection, paramSecureUser, paramHashMap, localMessageThread.getThreadID());
        localMessageThread.setNewThreadStatus(str3);
        localMessageThreads.add(localMessageThread);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localMessageThreads;
  }
  
  public MessageThreads getThreadsByPersonalBanker(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    Profile.isInitialized();
    String str1 = "MessageAdapter.getThreadsByPersonalBanker";
    Connection localConnection = null;
    Statement localStatement = null;
    ResultSet localResultSet = null;
    MessageThreads localMessageThreads = null;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str1, "Invalid Bank ID", 4);
    }
    if (!Profile.isValidId(paramSecureUser.getProfileID())) {
      Profile.handleError(str1, "Invalid user ID", 4);
    }
    try
    {
      StringBuffer localStringBuffer = new StringBuffer();
      int i = paramSecureUser.getAffiliateIDValue();
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localStringBuffer.append("SELECT m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM customer c, message m, customer_directory cd WHERE c.directory_id = m.directory_id AND c.directory_id = cd.directory_id AND ( NOT m.status = 5 )");
      localStringBuffer.append(" AND c.personal_banker = ");
      localStringBuffer.append(paramSecureUser.getProfileID());
      localStringBuffer.append(' ');
      if (i > 0)
      {
        localStringBuffer.append(" AND c.affiliate_bank_id = ");
        localStringBuffer.append(i);
        localStringBuffer.append(' ');
      }
      localStringBuffer.append(" UNION SELECT m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM business b, business_employee be, message m, customer c, customer_directory cd WHERE b.business_id = be.business_id AND be.directory_id = m.directory_id AND be.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND ( NOT m.status = 5 )");
      localStringBuffer.append(" AND b.personal_banker = ");
      localStringBuffer.append(paramSecureUser.getProfileID());
      localStringBuffer.append(' ');
      if (i > 0)
      {
        localStringBuffer.append(" AND b.affiliate_bank_id = ");
        localStringBuffer.append(i);
        localStringBuffer.append(' ');
      }
      localStatement = DBUtil.createStatement(localConnection);
      if (Profile.useMaxRows(paramHashMap)) {
        localStatement.setMaxRows(Profile.getMaxRowCount(paramHashMap));
      }
      localResultSet = DBUtil.executeQuery(localStatement, localStringBuffer.toString());
      localMessageThreads = new MessageThreads(paramSecureUser.getLocale());
      MessageThread localMessageThread = null;
      String str2 = null;
      while (localResultSet.next())
      {
        localMessageThread = processMessageThreadsRS(paramSecureUser, localResultSet);
        str2 = getUnreadThreadMessageCount(localConnection, paramSecureUser, paramHashMap, localMessageThread.getThreadID());
        localMessageThread.setNewThreadStatus(str2);
        localMessageThreads.add(localMessageThread);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localStatement, localResultSet);
    }
    return localMessageThreads;
  }
  
  public MessageThreads getThreadsByPending(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    Profile.isInitialized();
    String str1 = "MessageAdapter.getThreadsByPending";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    MessageThreads localMessageThreads = null;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str1, "Invalid Bank ID", 4);
    }
    if (!Profile.isValidId(paramSecureUser.getProfileID())) {
      Profile.handleError(str1, "Invalid user ID", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      int i = paramSecureUser.getAffiliateIDValue();
      String str2;
      if (i > 0) {
        str2 = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM message m, message_item mi, customer c, customer_directory cd WHERE ( m.status = 9 OR m.status = 12 )  AND (m.directory_id IN (SELECT cm.directory_id FROM customer cm WHERE cm.customer_type = '2' AND cm.affiliate_bank_id = ?) OR m.directory_id IN (SELECT ben.directory_id FROM business_employee ben, business bn WHERE bn.affiliate_bank_id = ? AND ben.business_id = bn.directory_id ))  AND mi.to_id = ? AND mi.to_type = 0 AND mi.from_type = 0 AND m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND m.message_id = mi.message_id AND mi.item_id IN ( SELECT MAX( mi.item_id )                     FROM message_item mi                     where mi.to_type = 0                     AND mi.from_type = 0                     GROUP BY mi.message_id)";
      } else {
        str2 = "SELECT distinct m.message_id, m.employee_id, m.directory_id, m.queue_id, m.subject, m.status, m.modified_date, m.create_date, m.close_date, m.bank_id, m.case_num, c.email_address, c.first_name, c.last_name, c.user_name, cd.cust_id FROM message m, message_item mi, customer c, customer_directory cd WHERE ( m.status = 9 OR m.status = 12 ) AND mi.to_id = ? AND mi.to_type = 0 AND mi.from_type = 0 AND m.directory_id = c.directory_id AND c.directory_id = cd.directory_id AND m.message_id = mi.message_id AND mi.item_id IN ( SELECT MAX( mi.item_id )                     FROM message_item mi                     where mi.to_type = 0                     AND mi.from_type = 0                     GROUP BY mi.message_id)";
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      if (Profile.useMaxRows(paramHashMap)) {
        localPreparedStatement.setMaxRows(Profile.getMaxRowCount(paramHashMap));
      }
      int j = 1;
      if (i > 0)
      {
        localPreparedStatement.setInt(j++, i);
        localPreparedStatement.setInt(j++, i);
      }
      localPreparedStatement.setInt(j++, paramSecureUser.getProfileID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      localMessageThreads = new MessageThreads(paramSecureUser.getLocale());
      MessageThread localMessageThread = null;
      String str3 = null;
      while (localResultSet.next())
      {
        localMessageThread = processMessageThreadsRS(paramSecureUser, localResultSet);
        str3 = getUnreadThreadMessageCount(localConnection, paramSecureUser, paramHashMap, localMessageThread.getThreadID());
        localMessageThread.setNewThreadStatus(str3);
        localMessageThreads.add(localMessageThread);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localMessageThreads;
  }
  
  public void reassignThread(SecureUser paramSecureUser, HashMap paramHashMap, MessageThread paramMessageThread, String paramString)
    throws ProfileException
  {
    Profile.isInitialized();
    String str = "MessageAdapter.reassignThread";
    Connection localConnection = null;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str, "Invalid Bank ID", 4);
    }
    if (!Profile.isValidId(paramSecureUser.getProfileID())) {
      Profile.handleError(str, "Invalid user ID", 4);
    }
    if (!Profile.isValidId(paramString)) {
      Profile.handleError(str, "Invalid user ID", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      modifyMessageThreadOwner(localConnection, Profile.convertToInt(paramMessageThread.getThreadID()), Profile.convertToInt(paramString));
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  protected int mapMessageFromTypeFromUserType(SecureUser paramSecureUser)
  {
    int i = paramSecureUser.getUserType();
    if (i == 2) {
      return 0;
    }
    return 1;
  }
  
  protected int mapThreadStatusFromMessageType(int paramInt)
  {
    int i;
    switch (paramInt)
    {
    case 1: 
      i = 1;
      break;
    case 2: 
      i = 2;
      break;
    case 5: 
      i = 5;
      break;
    case 6: 
      i = 6;
      break;
    case 8: 
      i = 8;
      break;
    case 3: 
    case 4: 
    case 7: 
    case 9: 
    case 10: 
    case 11: 
      i = 2;
      break;
    default: 
      i = 2;
    }
    return i;
  }
  
  public void createGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "MessageAdapter.createGlobalMessage";
    Profile.isInitialized();
    Connection localConnection = null;
    String str2 = paramSecureUser.getBankID();
    if (!Profile.isValidId(str2)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    if (!jdMethod_for(paramGlobalMessage, paramSecureUser)) {
      Profile.handleError(str1, "GlobalMessage Text is required.", 3610);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      int i = addGlobalMsgBodyTX(localConnection, paramGlobalMessage, paramSecureUser, paramHashMap);
      paramGlobalMessage.setGlobalMsgBodyID(i);
      Timestamp localTimestamp = DBUtil.getCurrentTimestamp();
      paramGlobalMessage.setCreateDate(localTimestamp);
      if (Integer.parseInt(paramGlobalMessage.getToGroupType()) == 4) {
        paramGlobalMessage.setToGroupID(jdMethod_char(localConnection, paramGlobalMessage));
      }
      addGlobalMsgTX(localConnection, paramGlobalMessage, paramSecureUser, paramHashMap);
      jdMethod_byte(localConnection, paramGlobalMessage);
      if ((paramGlobalMessage.getRecordTypeValue() != 2) && (paramGlobalMessage.getStatusValue() == 2))
      {
        paramGlobalMessage.setApprovedDate(localTimestamp);
        if ((paramGlobalMessage.getSendNowValue()) && ((paramGlobalMessage.getMsgTypeValue() == 1) || (paramGlobalMessage.getMsgTypeValue() == 2))) {
          paramGlobalMessage.setStatus(4);
        }
        modifyGlobalMsgTX(localConnection, paramGlobalMessage, paramSecureUser, paramHashMap);
      }
      DBUtil.commit(localConnection);
      if (paramGlobalMessage.getStatusValue() == 4)
      {
        a locala = new a(paramGlobalMessage, paramSecureUser.getBankID(), paramArrayList, paramHashMap);
        synchronized (this.amH)
        {
          this.amH.add(locala);
          this.amH.notifyAll();
        }
      }
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  public void deleteGlobalMessage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "MessageAdapter.deleteGlobalMessage";
    Profile.isInitialized();
    Connection localConnection = null;
    String str2 = paramSecureUser.getBankID();
    if (!Profile.isValidId(str2)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      modifyGlobalMsgStatusTX(localConnection, paramInt, 3);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  public void modifyGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "MessageAdapter.modifyGlobalMessage";
    Profile.isInitialized();
    Connection localConnection = null;
    String str2 = paramSecureUser.getBankID();
    if (!Profile.isValidId(str2)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      int i = modifyGlobalMsgBodyTX(localConnection, paramGlobalMessage, paramSecureUser, paramHashMap);
      paramGlobalMessage.setGlobalMsgBodyID(i);
      if (Integer.parseInt(paramGlobalMessage.getToGroupType()) == 4) {
        jdMethod_case(localConnection, paramGlobalMessage);
      }
      modifyGlobalMsgTX(localConnection, paramGlobalMessage, paramSecureUser, paramHashMap);
      jdMethod_byte(localConnection, paramGlobalMessage);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  public GlobalMessages getGlobalMessages(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "MessageAdapter.getGlobalMessages";
    Profile.isInitialized();
    Connection localConnection = null;
    String str2 = paramSecureUser.getBankID();
    if (!Profile.isValidId(str2)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    GlobalMessages localGlobalMessages = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      if (paramGlobalMessage == null) {
        paramGlobalMessage = new GlobalMessage();
      }
      paramHashMap.put("userKey", paramSecureUser);
      localGlobalMessages = getGlobalMsgsTX(localConnection, paramGlobalMessage, paramSecureUser.getLocale(), paramHashMap);
      populateI18NGlobalMsgListTX(localConnection, localGlobalMessages, paramGlobalMessage.getSearchLanguage(), paramHashMap);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    return localGlobalMessages;
  }
  
  public GlobalMessages getGlobalMessages(SecureUser paramSecureUser, GlobalMessageSearchCriteria paramGlobalMessageSearchCriteria, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "MessageAdapter.getGlobalMessages";
    Profile.isInitialized();
    Connection localConnection = null;
    String str2 = paramSecureUser.getBankID();
    if (!Profile.isValidId(str2)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    GlobalMessages localGlobalMessages = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      if (paramGlobalMessageSearchCriteria == null) {
        paramGlobalMessageSearchCriteria = new GlobalMessageSearchCriteria(Locale.getDefault());
      }
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      paramHashMap.put("userKey", paramSecureUser);
      localGlobalMessages = getGlobalMsgsTX(localConnection, str2, paramGlobalMessageSearchCriteria, paramSecureUser.getLocale(), paramHashMap);
      populateI18NGlobalMsgListTX(localConnection, localGlobalMessages, paramGlobalMessageSearchCriteria.getSearchLanguage(), paramHashMap);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    return localGlobalMessages;
  }
  
  public GlobalMessages getGlobalMessages(GlobalMessage paramGlobalMessage, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "MessageAdapter.getGlobalMessages";
    Profile.isInitialized();
    Connection localConnection = null;
    GlobalMessages localGlobalMessages = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      if (paramGlobalMessage == null) {
        paramGlobalMessage = new GlobalMessage();
      }
      localGlobalMessages = getGlobalMsgsTX(localConnection, paramGlobalMessage, paramGlobalMessage.getLocale(), paramHashMap);
      populateI18NGlobalMsgListTX(localConnection, localGlobalMessages, paramGlobalMessage.getSearchLanguage(), paramHashMap);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    return localGlobalMessages;
  }
  
  public GlobalMessages getGlobalMessages(GlobalMessageSearchCriteria paramGlobalMessageSearchCriteria, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "MessageAdapter.getGlobalMessages";
    Profile.isInitialized();
    Connection localConnection = null;
    GlobalMessages localGlobalMessages = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      if (paramGlobalMessageSearchCriteria == null) {
        paramGlobalMessageSearchCriteria = new GlobalMessageSearchCriteria();
      }
      localGlobalMessages = getGlobalMsgsTX(localConnection, null, paramGlobalMessageSearchCriteria, paramGlobalMessageSearchCriteria.getLocale(), paramHashMap);
      populateI18NGlobalMsgListTX(localConnection, localGlobalMessages, paramGlobalMessageSearchCriteria.getSearchLanguage(), paramHashMap);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    return localGlobalMessages;
  }
  
  public GlobalMessage getGlobalMessageById(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "MessageAdapter.GetGlobalMessage";
    Profile.isInitialized();
    Connection localConnection = null;
    String str2 = paramSecureUser.getBankID();
    if (!Profile.isValidId(str2)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    GlobalMessage localGlobalMessage = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      localGlobalMessage = getGlobalMsgByIdTX(localConnection, paramInt, paramSecureUser.getLocale(), null, paramHashMap);
      GlobalMessages localGlobalMessages = new GlobalMessages();
      localGlobalMessages.add(localGlobalMessage);
      populateI18NGlobalMsgListTX(localConnection, localGlobalMessages, null, paramHashMap);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    return localGlobalMessage;
  }
  
  public GlobalMessages getGlobalLoginMessages(String paramString, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    return getGlobalLoginMessages(paramString, 0, paramInt, paramHashMap);
  }
  
  public GlobalMessages getGlobalLoginMessages(String paramString, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "MessageAdapter.getGlobalLoginMessages";
    Profile.isInitialized();
    Connection localConnection = null;
    if (!Profile.isValidId(paramString)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    GlobalMessages localGlobalMessages = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      if (!paramHashMap.containsKey("LANGUAGE")) {
        paramHashMap.put("LANGUAGE", "en_US");
      }
      localGlobalMessages = getGlobalLoginMsgsTX(localConnection, paramString, paramInt1, paramInt2, paramHashMap);
      String str2 = (String)paramHashMap.get("LANGUAGE");
      populateI18NGlobalMsgListTX(localConnection, localGlobalMessages, str2, paramHashMap);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    return localGlobalMessages;
  }
  
  public void sendGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "MessageAdapter.SendGlobalMessage";
    Profile.isInitialized();
    Connection localConnection = null;
    String str2 = paramSecureUser.getBankID();
    if (!Profile.isValidId(str2)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    if (paramGlobalMessage.getApprovalEmployeeIDValue() != paramSecureUser.getProfileID()) {
      Profile.handleError(str1, "Approver ID and user ID do not match", 4);
    }
    if (!jdMethod_for(paramGlobalMessage, paramSecureUser)) {
      Profile.handleError(str1, "GlobalMessage Text is required.", 3610);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      String str3 = (String)paramHashMap.get("TextModified");
      if ((str3 != null) && (str3.equals("Y")))
      {
        int i = modifyGlobalMsgBodyTX(localConnection, paramGlobalMessage, paramSecureUser, paramHashMap);
        paramGlobalMessage.setGlobalMsgBodyID(i);
      }
      paramGlobalMessage.setStatus(2);
      Timestamp localTimestamp = DBUtil.getCurrentTimestamp();
      paramGlobalMessage.setApprovedDate(localTimestamp);
      if ((paramGlobalMessage.getSendNowValue()) && ((paramGlobalMessage.getMsgTypeValue() == 1) || (paramGlobalMessage.getMsgTypeValue() == 2))) {
        paramGlobalMessage.setStatus(4);
      }
      modifyGlobalMsgTX(localConnection, paramGlobalMessage, paramSecureUser, null);
      jdMethod_case(localConnection, paramGlobalMessage);
      DBUtil.commit(localConnection);
      if ((paramGlobalMessage.getSendNowValue()) && ((paramGlobalMessage.getMsgTypeValue() == 1) || (paramGlobalMessage.getMsgTypeValue() == 2)))
      {
        a locala = new a(paramGlobalMessage, paramSecureUser.getBankID(), paramArrayList, paramHashMap);
        synchronized (this.amH)
        {
          this.amH.add(locala);
          this.amH.notifyAll();
        }
      }
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  public void sendGlobalMessage(GlobalMessage paramGlobalMessage, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "MessageAdapter.SendGlobalMessage";
    Profile.isInitialized();
    Connection localConnection = null;
    if (!jdMethod_for(paramGlobalMessage, new SecureUser())) {
      Profile.handleError(str1, "GlobalMessage Text is required.", 3610);
    }
    String str2 = paramGlobalMessage.getBankId();
    if (!Profile.isValidId(str2)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      paramGlobalMessage.setStatus(4);
      paramGlobalMessage.setToGroupID(jdMethod_char(localConnection, paramGlobalMessage));
      modifyGlobalMsgTX(localConnection, paramGlobalMessage, new SecureUser(), new HashMap());
      jdMethod_byte(localConnection, paramGlobalMessage);
      DBUtil.commit(localConnection);
      a locala = new a(paramGlobalMessage, paramGlobalMessage.getBankId(), null, paramHashMap);
      synchronized (this.amH)
      {
        this.amH.add(locala);
        this.amH.notifyAll();
      }
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  protected int addGlobalMsgBodyTX(Connection paramConnection, String paramString)
    throws Exception
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return -1;
    }
    PreparedStatement localPreparedStatement = null;
    int i = DBUtil.getNextId(paramConnection, Profile.dbType, "GLOBAL_BODY_ID");
    ArrayList localArrayList = Profile.breakupMessage(paramString);
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "INSERT into global_msg_body (global_body_id,seq,text) values(?,?,?)");
      for (int j = 0; j < localArrayList.size(); j++)
      {
        int k = 1;
        jdMethod_int(localPreparedStatement, i, j + 1, (String)localArrayList.get(j), k);
        DBUtil.executeUpdate(localPreparedStatement, "INSERT into global_msg_body (global_body_id,seq,text) values(?,?,?)");
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
    return i;
  }
  
  protected int addGlobalMsgBodyTX(Connection paramConnection, GlobalMessage paramGlobalMessage, SecureUser paramSecureUser, HashMap paramHashMap)
    throws Exception
  {
    int i = DBUtil.getNextId(paramConnection, Profile.dbType, "GLOBAL_BODY_ID");
    LanguageDefns localLanguageDefns = UtilHandler.getLanguageList(paramSecureUser, paramHashMap);
    if (localLanguageDefns != null)
    {
      Iterator localIterator = localLanguageDefns.iterator();
      while (localIterator.hasNext())
      {
        String str1 = ((LanguageDefn)localIterator.next()).getLanguage();
        String str2 = paramGlobalMessage.getMsgText(str1);
        if ((str2 == null) || (str2.length() == 0)) {
          return -1;
        }
        ArrayList localArrayList = Profile.breakupMessage(str2);
        jdMethod_for(paramConnection, i, localArrayList, str1);
      }
    }
    return i;
  }
  
  private void jdMethod_for(Connection paramConnection, int paramInt, ArrayList paramArrayList, String paramString)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      String str;
      if (paramString.equals("en_US")) {
        str = "INSERT into global_msg_body (global_body_id,seq,text) values(?,?,?)";
      } else {
        str = "INSERT into global_msg_body_i18n (global_body_id,seq,text,language) values(?,?,?,?)";
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, str);
      for (int i = 0; i < paramArrayList.size(); i++)
      {
        int j = 1;
        j = jdMethod_int(localPreparedStatement, paramInt, i + 1, (String)paramArrayList.get(i), j);
        if (!paramString.equals("en_US")) {
          j = Profile.setStatementString(localPreparedStatement, j, paramString, "language", false, false, true, true);
        }
        DBUtil.executeUpdate(localPreparedStatement, str);
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  protected void deleteGlobalMsgBodyTX(Connection paramConnection, int paramInt)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "DELETE from global_msg_body where global_body_id=?");
      Profile.setStatementInt(localPreparedStatement, 1, paramInt, false);
      DBUtil.executeUpdate(localPreparedStatement, "DELETE from global_msg_body where global_body_id=?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "DELETE from global_msg_body_i18n where global_body_id=?");
      Profile.setStatementInt(localPreparedStatement, 1, paramInt, false);
      DBUtil.executeUpdate(localPreparedStatement, "DELETE from global_msg_body_i18n where global_body_id=?");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  protected int modifyGlobalMsgBodyTX(Connection paramConnection, int paramInt, String paramString)
    throws Exception
  {
    deleteGlobalMsgBodyTX(paramConnection, paramInt);
    int i = addGlobalMsgBodyTX(paramConnection, paramString);
    return i;
  }
  
  protected int modifyGlobalMsgBodyTX(Connection paramConnection, GlobalMessage paramGlobalMessage, SecureUser paramSecureUser, HashMap paramHashMap)
    throws Exception
  {
    deleteGlobalMsgBodyTX(paramConnection, paramGlobalMessage.getGlobalMsgBodyIDValue());
    int i = addGlobalMsgBodyTX(paramConnection, paramGlobalMessage, paramSecureUser, paramHashMap);
    return i;
  }
  
  protected int addGlobalMsgTX(Connection paramConnection, GlobalMessage paramGlobalMessage)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    int i = DBUtil.getNextId(paramConnection, Profile.dbType, "GLOBAL_MSG_ID");
    try
    {
      paramGlobalMessage.setGlobalMsgID(i);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "INSERT into global_message (global_msg_id,to_group_id,to_group_name,from_id,from_name,subject,global_body_id,create_date,status,approval_id,approved_date,bank_id,affiliate_bank_id,record_type,msg_type,to_group_type,color,priority,display_from_date,display_to_date,template_name, send_now)values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
      int j = 1;
      j = jdMethod_new(localPreparedStatement, paramGlobalMessage, j);
      DBUtil.executeUpdate(localPreparedStatement, "INSERT into global_message (global_msg_id,to_group_id,to_group_name,from_id,from_name,subject,global_body_id,create_date,status,approval_id,approved_date,bank_id,affiliate_bank_id,record_type,msg_type,to_group_type,color,priority,display_from_date,display_to_date,template_name, send_now)values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
    return i;
  }
  
  protected int addGlobalMsgTX(Connection paramConnection, GlobalMessage paramGlobalMessage, SecureUser paramSecureUser, HashMap paramHashMap)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    int i = DBUtil.getNextId(paramConnection, Profile.dbType, "GLOBAL_MSG_ID");
    try
    {
      paramGlobalMessage.setGlobalMsgID(i);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "INSERT into global_message (global_msg_id,to_group_id,to_group_name,from_id,from_name,subject,global_body_id,create_date,status,approval_id,approved_date,bank_id,affiliate_bank_id,record_type,msg_type,to_group_type,color,priority,display_from_date,display_to_date,template_name, send_now)values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
      int j = 1;
      j = jdMethod_new(localPreparedStatement, paramGlobalMessage, j);
      DBUtil.executeUpdate(localPreparedStatement, "INSERT into global_message (global_msg_id,to_group_id,to_group_name,from_id,from_name,subject,global_body_id,create_date,status,approval_id,approved_date,bank_id,affiliate_bank_id,record_type,msg_type,to_group_type,color,priority,display_from_date,display_to_date,template_name, send_now)values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
      DBUtil.closeStatement(localPreparedStatement);
      LanguageDefns localLanguageDefns = UtilHandler.getLanguageList(paramSecureUser, paramHashMap);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "INSERT into global_message_i18n (to_group_name, from_name, subject,global_msg_id, language) VALUES (?,?,?,?,?)");
      if (localLanguageDefns != null)
      {
        Iterator localIterator = localLanguageDefns.iterator();
        while (localIterator.hasNext())
        {
          String str = ((LanguageDefn)localIterator.next()).getLanguage();
          if (!str.equals("en_US"))
          {
            j = 1;
            j = jdMethod_int(localPreparedStatement, paramGlobalMessage, str, j);
            DBUtil.executeUpdate(localPreparedStatement, "INSERT into global_message_i18n (to_group_name, from_name, subject,global_msg_id, language) VALUES (?,?,?,?,?)");
          }
        }
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
    return i;
  }
  
  protected void modifyGlobalMsgTX(Connection paramConnection, GlobalMessage paramGlobalMessage)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "UPDATE global_message set  to_group_id=?,to_group_name=?,from_id=?,from_name=?,subject=?,global_body_id=?,create_date=?,status=?,approval_id=?,approved_date=?,bank_id=?,affiliate_bank_id=?,record_type=?,msg_type=?,to_group_type=?,color=?,priority=?,display_from_date=?,display_to_date=?,template_name=?, send_now=? WHERE global_msg_id=?");
      int i = 1;
      i = jdMethod_int(localPreparedStatement, paramGlobalMessage, i);
      DBUtil.executeUpdate(localPreparedStatement, "UPDATE global_message set  to_group_id=?,to_group_name=?,from_id=?,from_name=?,subject=?,global_body_id=?,create_date=?,status=?,approval_id=?,approved_date=?,bank_id=?,affiliate_bank_id=?,record_type=?,msg_type=?,to_group_type=?,color=?,priority=?,display_from_date=?,display_to_date=?,template_name=?, send_now=? WHERE global_msg_id=?");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  protected void modifyGlobalMsgTX(Connection paramConnection, GlobalMessage paramGlobalMessage, SecureUser paramSecureUser, HashMap paramHashMap)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "UPDATE global_message set  to_group_id=?,to_group_name=?,from_id=?,from_name=?,subject=?,global_body_id=?,create_date=?,status=?,approval_id=?,approved_date=?,bank_id=?,affiliate_bank_id=?,record_type=?,msg_type=?,to_group_type=?,color=?,priority=?,display_from_date=?,display_to_date=?,template_name=?, send_now=? WHERE global_msg_id=?");
      int i = 1;
      i = jdMethod_int(localPreparedStatement, paramGlobalMessage, i);
      DBUtil.executeUpdate(localPreparedStatement, "UPDATE global_message set  to_group_id=?,to_group_name=?,from_id=?,from_name=?,subject=?,global_body_id=?,create_date=?,status=?,approval_id=?,approved_date=?,bank_id=?,affiliate_bank_id=?,record_type=?,msg_type=?,to_group_type=?,color=?,priority=?,display_from_date=?,display_to_date=?,template_name=?, send_now=? WHERE global_msg_id=?");
      DBUtil.closeStatement(localPreparedStatement);
      LanguageDefns localLanguageDefns = UtilHandler.getLanguageList(paramSecureUser, paramHashMap);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "UPDATE global_message_i18n set to_group_name=?,from_name=?,subject=? WHERE global_msg_id=? and language=?");
      if (localLanguageDefns != null)
      {
        Iterator localIterator = localLanguageDefns.iterator();
        while (localIterator.hasNext())
        {
          String str = ((LanguageDefn)localIterator.next()).getLanguage();
          if (!str.equals("en_US"))
          {
            i = 1;
            i = jdMethod_for(localPreparedStatement, paramGlobalMessage, str, i);
            DBUtil.executeUpdate(localPreparedStatement, "UPDATE global_message_i18n set to_group_name=?,from_name=?,subject=? WHERE global_msg_id=? and language=?");
          }
        }
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public static ResultSetWrapper getCustomersByGroupType(Connection paramConnection, String paramString, int paramInt1, int paramInt2, int paramInt3)
    throws ProfileException
  {
    String str = "MessageAdapter.getUsersByGroupType";
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    if (!Profile.isValidId(paramString)) {
      Profile.handleError(str, "Invalid Bank Id", 4);
    }
    ResultSetWrapper localResultSetWrapper = null;
    if (paramConnection == null) {
      localResultSetWrapper = new ResultSetWrapper(Profile.poolName, paramString, paramInt1, paramInt2, paramInt3);
    } else {
      localResultSetWrapper = new ResultSetWrapper(paramConnection, paramString, paramInt1, paramInt2, paramInt3);
    }
    return localResultSetWrapper;
  }
  
  protected void modifyGlobalMsgStatusTX(Connection paramConnection, int paramInt1, int paramInt2)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "UPDATE global_message set status=? WHERE global_msg_id=?");
      int i = 1;
      localPreparedStatement.setInt(i++, paramInt2);
      localPreparedStatement.setInt(i++, paramInt1);
      DBUtil.executeUpdate(localPreparedStatement, "UPDATE global_message set status=? WHERE global_msg_id=?");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  protected void sendGlobalMsgTX(GlobalMessage paramGlobalMessage, String paramString, ArrayList paramArrayList)
    throws Exception
  {
    sendGlobalMsgTX(paramGlobalMessage, paramString, paramArrayList, null);
  }
  
  protected void sendGlobalMsgTX(GlobalMessage paramGlobalMessage, String paramString, ArrayList paramArrayList, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "MessageAdapter.sendGlobalMsgTX";
    PreparedStatement localPreparedStatement = null;
    Connection localConnection = null;
    ResultSetWrapper localResultSetWrapper = null;
    try
    {
      int i = 0;
      HashMap localHashMap1 = new HashMap();
      HashMap localHashMap2 = new HashMap();
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "INSERT into message_item (item_id,message_id,body_id,comment_id,to_id,to_type,from_id,from_type,message_type,create_date) values (?,?,?,?,?,?,?,?,?,?)");
      Message localMessage = paramGlobalMessage.getMessage();
      int j = localMessage.getFromTypeValue();
      if ((j != 0) && (j != 1) && (j != 3)) {
        Profile.handleError(str1, "Invalid fromType", 3);
      }
      int k = localMessage.getToTypeValue();
      if ((k != 0) && (k != 1)) {
        Profile.handleError(str1, "Invalid toType", 3);
      }
      Timestamp localTimestamp = DBUtil.getCurrentTimestamp();
      localMessage.setCreateDate(localTimestamp);
      MessagingFilters localMessagingFilters = (MessagingFilters)paramHashMap.get("messaginFilterKey");
      int n;
      Object localObject1;
      String str3;
      Object localObject2;
      if (paramArrayList != null)
      {
        int m = 0;
        while (m < paramArrayList.size())
        {
          n = 0;
          while ((n < 256) && (m < paramArrayList.size()))
          {
            int i1;
            try
            {
              i1 = Integer.parseInt((String)paramArrayList.get(m));
            }
            catch (NumberFormatException localNumberFormatException)
            {
              break label403;
            }
            localObject1 = CustomerAdapter.getUserById(i1);
            str3 = ((User)localObject1).getPreferredLanguage();
            localObject2 = (Integer)localHashMap1.get(str3);
            if (localObject2 == null)
            {
              localObject2 = new Integer(addMessageBody(localConnection, paramGlobalMessage.getMsgText(str3)));
              localHashMap1.put(str3, localObject2);
            }
            String str4 = (String)localHashMap2.get(str3);
            if (str4 == null)
            {
              MessageThread localMessageThread = addMessageThread(localConnection, paramGlobalMessage.getMessageThread(str3), paramString);
              str4 = localMessageThread.getThreadID();
              localHashMap2.put(str3, str4);
            }
            localMessage.setMsgThreadID(str4);
            localMessage.setMemo(paramGlobalMessage.getMsgText(str3));
            if (localMessagingFilters != null)
            {
              if (localMessagingFilters.checkFiltersForDirID(paramGlobalMessage, Integer.parseInt((String)paramArrayList.get(m)), paramHashMap)) {
                jdMethod_for(localMessage, paramArrayList, m, localConnection, localPreparedStatement, ((Integer)localObject2).intValue(), i);
              }
            }
            else {
              jdMethod_for(localMessage, paramArrayList, m, localConnection, localPreparedStatement, ((Integer)localObject2).intValue(), i);
            }
            label403:
            n++;
            m++;
          }
          localPreparedStatement.executeBatch();
        }
      }
      else
      {
        if (paramGlobalMessage.getToGroupTypeValue() == 4) {
          localResultSetWrapper = getSpecificCustomersOfGroupType(localConnection, paramGlobalMessage);
        } else {
          localResultSetWrapper = getCustomersByGroupType(localConnection, paramGlobalMessage.getBankId(), paramGlobalMessage.getAffiliateBankIdValue(), paramGlobalMessage.getToGroupTypeValue(), paramGlobalMessage.getToGroupIDValue());
        }
        UserWrapper localUserWrapper = localResultSetWrapper.getNextUser();
        for (n = 0; localUserWrapper != null; n = 0)
        {
          String str2 = localUserWrapper.getPreferredLanguage();
          localObject1 = (Integer)localHashMap1.get(str2);
          if (localObject1 == null)
          {
            localObject1 = new Integer(addMessageBody(localConnection, paramGlobalMessage.getMsgText(str2)));
            localHashMap1.put(str2, localObject1);
          }
          str3 = (String)localHashMap2.get(str2);
          if (str3 == null)
          {
            localObject2 = addMessageThread(localConnection, paramGlobalMessage.getMessageThread(str2), paramString);
            str3 = ((MessageThread)localObject2).getThreadID();
            localHashMap2.put(str2, str3);
          }
          localMessage.setMsgThreadID(str3);
          localMessage.setMemo(paramGlobalMessage.getMsgText(str2));
          int i2 = localUserWrapper.getDirectoryID();
          if (localMessagingFilters != null)
          {
            if (localMessagingFilters.checkFiltersForDirID(paramGlobalMessage, i2, paramHashMap)) {
              jdMethod_for(localMessage, i2, localConnection, localPreparedStatement, ((Integer)localObject1).intValue(), i);
            }
          }
          else {
            jdMethod_for(localMessage, i2, localConnection, localPreparedStatement, ((Integer)localObject1).intValue(), i);
          }
          localUserWrapper = localResultSetWrapper.getNextUser();
          n++;
          if ((localUserWrapper == null) || (n >= 256)) {
            localPreparedStatement.executeBatch();
          }
        }
        localResultSetWrapper.close();
      }
      modifyGlobalMsgStatusTX(localConnection, paramGlobalMessage.getGlobalMsgIDValue(), 6);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      modifyGlobalMsgStatusTX(localConnection, paramGlobalMessage.getGlobalMsgIDValue(), 5);
      DBUtil.commit(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      if (localResultSetWrapper != null)
      {
        localResultSetWrapper.close();
        localResultSetWrapper = null;
      }
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  private static void jdMethod_for(Message paramMessage, ArrayList paramArrayList, int paramInt1, Connection paramConnection, PreparedStatement paramPreparedStatement, int paramInt2, int paramInt3)
    throws Exception
  {
    paramMessage.setTo((String)paramArrayList.get(paramInt1));
    int i = DBUtil.getNextId(paramConnection, Profile.dbType, "ITEM_ID");
    paramMessage.setID(String.valueOf(i));
    int j = 1;
    j = jdMethod_for(paramPreparedStatement, paramMessage, paramInt2, paramInt3, j);
    paramPreparedStatement.addBatch();
  }
  
  private static void jdMethod_for(Message paramMessage, int paramInt1, Connection paramConnection, PreparedStatement paramPreparedStatement, int paramInt2, int paramInt3)
    throws Exception
  {
    paramMessage.setTo(String.valueOf(paramInt1));
    int i = DBUtil.getNextId(paramConnection, Profile.dbType, "ITEM_ID");
    paramMessage.setID(String.valueOf(i));
    int j = 1;
    j = jdMethod_for(paramPreparedStatement, paramMessage, paramInt2, paramInt3, j);
    paramPreparedStatement.addBatch();
  }
  
  protected GlobalMessages getGlobalMsgsTX(Connection paramConnection, GlobalMessage paramGlobalMessage, Locale paramLocale, HashMap paramHashMap)
    throws Exception
  {
    StringBuffer localStringBuffer = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    boolean bool1 = false;
    if ((paramGlobalMessage.getSearchLanguage() == null) || (paramGlobalMessage.getSearchLanguage().equals("en_US"))) {
      bool1 = true;
    }
    GlobalMessages localGlobalMessages = new GlobalMessages(paramLocale);
    try
    {
      localStringBuffer = new StringBuffer("SELECT gm.global_msg_id, gm.to_group_id, gm.to_group_name, gm.from_id, gm.from_name, gm.subject, gm.global_body_id, gm.create_date, gm.status, gm.approval_id, gm.approved_date, gm.bank_id, gm.affiliate_bank_id, gm.record_type, gm.msg_type, gm.to_group_type, gm.color, gm.priority, gm.display_from_date, gm.display_to_date, gm.template_name, gm.send_now FROM global_message gm left outer join global_message_i18n gmI18N on gm.global_msg_id=gmI18N.global_msg_id ");
      boolean bool2 = false;
      bool2 = jdMethod_for(localStringBuffer, paramGlobalMessage, bool2);
      String str = (String)paramHashMap.get("GlobalMsgEntitledMsgTypes");
      if (str != null)
      {
        if (bool2) {
          localStringBuffer.append(" and ");
        } else {
          localStringBuffer.append(" where ");
        }
        localStringBuffer.append(DBUtil.generateSQLNumericInClause(str, "gm.msg_type"));
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = jdMethod_for(localPreparedStatement, paramGlobalMessage, i);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      HashMap localHashMap = new HashMap();
      GlobalMessage localGlobalMessage = null;
      while (localResultSet.next() == true)
      {
        localGlobalMessage = processGlobalMsgRS(paramConnection, localResultSet, paramLocale, localHashMap, bool1, paramHashMap);
        retrieveMessageFiltersRecipients(paramConnection, localGlobalMessage, paramLocale);
        jdMethod_for(localGlobalMessages, localGlobalMessage, paramHashMap);
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localGlobalMessages;
  }
  
  private static void jdMethod_for(GlobalMessages paramGlobalMessages, GlobalMessage paramGlobalMessage, HashMap paramHashMap)
  {
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    SecureUser localSecureUser = (SecureUser)paramHashMap.get("userKey");
    MessagingFilters localMessagingFilters = (MessagingFilters)paramHashMap.get("messaginFilterKey");
    if ((localSecureUser != null) && (localSecureUser.getUserType() != 2))
    {
      if (localMessagingFilters != null)
      {
        if (localMessagingFilters.checkFiltersForSecureUser(paramGlobalMessage, localSecureUser, paramHashMap)) {
          paramGlobalMessages.add(paramGlobalMessage);
        }
      }
      else {
        paramGlobalMessages.add(paramGlobalMessage);
      }
    }
    else {
      paramGlobalMessages.add(paramGlobalMessage);
    }
  }
  
  protected GlobalMessages getGlobalMsgsTX(Connection paramConnection, String paramString, GlobalMessageSearchCriteria paramGlobalMessageSearchCriteria, Locale paramLocale, HashMap paramHashMap)
    throws Exception
  {
    StringBuffer localStringBuffer = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    boolean bool1 = false;
    if ((paramGlobalMessageSearchCriteria.getSearchLanguage() == null) || (paramGlobalMessageSearchCriteria.getSearchLanguage().equals("en_US"))) {
      bool1 = true;
    }
    GlobalMessages localGlobalMessages = new GlobalMessages(paramLocale);
    try
    {
      localStringBuffer = new StringBuffer("SELECT gm.global_msg_id, gm.to_group_id, gm.to_group_name, gm.from_id, gm.from_name, gm.subject, gm.global_body_id, gm.create_date, gm.status, gm.approval_id, gm.approved_date, gm.bank_id, gm.affiliate_bank_id, gm.record_type, gm.msg_type, gm.to_group_type, gm.color, gm.priority, gm.display_from_date, gm.display_to_date, gm.template_name, gm.send_now FROM global_message gm");
      boolean bool2 = false;
      bool2 = Profile.appendSQLSelectString(localStringBuffer, "gm.", "bank_id", paramString, false, false, bool2);
      java.util.Date localDate1 = paramGlobalMessageSearchCriteria.getCreateDateStart();
      if (localDate1 != null) {
        bool2 = Profile.appendSQLSelectDate(localStringBuffer, "gm.", "create_date", localDate1, ">=", bool2);
      }
      java.util.Date localDate2 = paramGlobalMessageSearchCriteria.getCreateDateEnd();
      if (localDate2 != null) {
        bool2 = Profile.appendSQLSelectDate(localStringBuffer, "gm.", "create_date", localDate2, "<=", bool2);
      }
      ArrayList localArrayList1 = paramGlobalMessageSearchCriteria.getStatuses();
      if (localArrayList1 != null)
      {
        if (bool2)
        {
          localStringBuffer.append(" and ");
        }
        else
        {
          localStringBuffer.append(" where\t");
          bool2 = true;
        }
        localStringBuffer.append("gm.status IN (");
        for (int i = 0; i < localArrayList1.size(); i++) {
          localStringBuffer.append(localArrayList1.get(i) + ",");
        }
        localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
        localStringBuffer.append(") ");
      }
      ArrayList localArrayList2 = paramGlobalMessageSearchCriteria.getAffiliateBanks();
      if (localArrayList2 != null)
      {
        if (bool2)
        {
          localStringBuffer.append(" and ");
        }
        else
        {
          localStringBuffer.append(" where ");
          bool2 = true;
        }
        localStringBuffer.append("gm.affiliate_bank_id in (");
        for (j = 0; j < localArrayList2.size(); j++) {
          localStringBuffer.append(localArrayList2.get(j) + ",");
        }
        localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
        localStringBuffer.append(") ");
      }
      bool2 = Profile.appendSQLSelectInt(localStringBuffer, "gm.", "record_type", paramGlobalMessageSearchCriteria.getRecordType(), bool2);
      int j = paramGlobalMessageSearchCriteria.getApprovalEmployeeID();
      if (j > 0) {
        bool2 = Profile.appendSQLSelectInt(localStringBuffer, "gm.", "approval_id", j, bool2);
      }
      ArrayList localArrayList3 = paramGlobalMessageSearchCriteria.getMsgTypes();
      if (localArrayList3 != null)
      {
        if (bool2)
        {
          localStringBuffer.append(" and ");
        }
        else
        {
          localStringBuffer.append(" where\t");
          bool2 = true;
        }
        localStringBuffer.append("gm.msg_type IN\t(");
        for (int k = 0; k < localArrayList3.size(); k++) {
          DBUtil.trimSQLNumericLiteral(localArrayList3.get(k).toString(), localStringBuffer).append(",");
        }
        localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
        localStringBuffer.append(") ");
      }
      ArrayList localArrayList4 = paramGlobalMessageSearchCriteria.getToGroups();
      if ((localArrayList4 != null) && (localArrayList4.size() > 0))
      {
        if (bool2)
        {
          localStringBuffer.append(" and (");
        }
        else
        {
          localStringBuffer.append(" where (");
          bool2 = true;
        }
        localObject1 = null;
        for (m = 0; m < localArrayList4.size(); m++)
        {
          localObject1 = (GlobalMessageToGroup)localArrayList4.get(m);
          if (m != 0) {
            localStringBuffer.append(" or ");
          }
          localStringBuffer.append("(gm.to_group_type=" + ((GlobalMessageToGroup)localObject1).getToGroupType() + " and " + "gm." + "to_group_id" + "=" + ((GlobalMessageToGroup)localObject1).getToGroupID() + ")");
        }
        localStringBuffer.append(") ");
      }
      Object localObject1 = paramGlobalMessageSearchCriteria.getDisplayDate();
      if (localObject1 != null)
      {
        bool2 = Profile.appendSQLSelectDate(localStringBuffer, "gm.", "display_from_date", (java.util.Date)localObject1, "<=", bool2);
        bool2 = Profile.appendSQLSelectDate(localStringBuffer, "gm.", "display_to_date", (java.util.Date)localObject1, ">=", bool2);
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      if (Profile.useMaxRows(paramHashMap)) {
        localPreparedStatement.setMaxRows(Profile.getMaxRowCount(paramHashMap));
      }
      int m = 1;
      m = Profile.setStatementString(localPreparedStatement, m, paramString, "bank_id", true);
      m = Profile.setStatementDate(localPreparedStatement, m, paramGlobalMessageSearchCriteria.getCreateDateStartAsTimestamp(), true);
      m = Profile.setStatementDate(localPreparedStatement, m, paramGlobalMessageSearchCriteria.getCreateDateEndAsTimestamp(), true);
      m = Profile.setStatementInt(localPreparedStatement, m, paramGlobalMessageSearchCriteria.getRecordType(), true);
      m = Profile.setStatementInt(localPreparedStatement, m, paramGlobalMessageSearchCriteria.getApprovalEmployeeID(), true);
      m = Profile.setStatementDate(localPreparedStatement, m, paramGlobalMessageSearchCriteria.getDisplayDateAsTimestamp(), true);
      m = Profile.setStatementDate(localPreparedStatement, m, paramGlobalMessageSearchCriteria.getDisplayDateAsTimestamp(), true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      HashMap localHashMap = new HashMap();
      GlobalMessage localGlobalMessage = null;
      while (localResultSet.next() == true)
      {
        localGlobalMessage = processGlobalMsgRS(paramConnection, localResultSet, paramLocale, localHashMap, bool1, paramHashMap);
        retrieveMessageFiltersRecipients(paramConnection, localGlobalMessage, paramLocale);
        jdMethod_for(localGlobalMessages, localGlobalMessage, paramHashMap);
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localGlobalMessages;
  }
  
  protected GlobalMessage getGlobalMsgByIdTX(Connection paramConnection, int paramInt, Locale paramLocale)
    throws Exception
  {
    return getGlobalMsgByIdTX(paramConnection, paramInt, paramLocale, null, null);
  }
  
  protected GlobalMessage getGlobalMsgByIdTX(Connection paramConnection, int paramInt, Locale paramLocale, String paramString, HashMap paramHashMap)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    boolean bool = false;
    if ((paramString == null) || (paramString.equals("en_US"))) {
      bool = true;
    }
    GlobalMessage localGlobalMessage = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "SELECT gm.global_msg_id, gm.to_group_id, gm.to_group_name, gm.from_id, gm.from_name, gm.subject, gm.global_body_id, gm.create_date, gm.status, gm.approval_id, gm.approved_date, gm.bank_id, gm.affiliate_bank_id, gm.record_type, gm.msg_type, gm.to_group_type, gm.color, gm.priority, gm.display_from_date, gm.display_to_date, gm.template_name, gm.send_now FROM global_message gm WHERE gm.global_msg_id=?");
      Profile.setStatementInt(localPreparedStatement, 1, paramInt, false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT gm.global_msg_id, gm.to_group_id, gm.to_group_name, gm.from_id, gm.from_name, gm.subject, gm.global_body_id, gm.create_date, gm.status, gm.approval_id, gm.approved_date, gm.bank_id, gm.affiliate_bank_id, gm.record_type, gm.msg_type, gm.to_group_type, gm.color, gm.priority, gm.display_from_date, gm.display_to_date, gm.template_name, gm.send_now FROM global_message gm WHERE gm.global_msg_id=?");
      if (localResultSet.next() == true)
      {
        localGlobalMessage = processGlobalMsgRS(paramConnection, localResultSet, Locale.getDefault(), null, bool, null);
        retrieveMessageFiltersRecipients(paramConnection, localGlobalMessage, paramLocale);
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localGlobalMessage;
  }
  
  protected String getGlobalMsgBodyTX(Connection paramConnection, int paramInt)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "SELECT text FROM global_msg_body where global_body_id=?");
      Profile.setStatementInt(localPreparedStatement, 1, paramInt, false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT text FROM global_msg_body where global_body_id=?");
      while (localResultSet.next() == true) {
        localStringBuffer.append(Profile.getRSString(localResultSet, "text"));
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localStringBuffer.toString();
  }
  
  protected String getI18NGlobalMsgBodyTX(Connection paramConnection, int paramInt, String paramString)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "SELECT text FROM global_msg_body_i18n where global_body_id=? AND language=?");
      Profile.setStatementInt(localPreparedStatement, 1, paramInt, false);
      Profile.setStatementString(localPreparedStatement, 2, paramString, "language", false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT text FROM global_msg_body_i18n where global_body_id=? AND language=?");
      while (localResultSet.next() == true) {
        localStringBuffer.append(Profile.getRSString(localResultSet, "text"));
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localStringBuffer.toString();
  }
  
  protected GlobalMessages getGlobalLoginMsgsTX(Connection paramConnection, String paramString, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws Exception
  {
    StringBuffer localStringBuffer = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    boolean bool1 = true;
    if ((paramHashMap != null) && (paramHashMap.containsKey("LANGUAGE")))
    {
      localObject1 = (String)paramHashMap.get("LANGUAGE");
      if ((localObject1 != null) && (!((String)localObject1).equals("en_US"))) {
        bool1 = false;
      }
    }
    Object localObject1 = new GlobalMessages();
    try
    {
      localStringBuffer = new StringBuffer("SELECT gm.global_msg_id, gm.to_group_id, gm.to_group_name, gm.from_id, gm.from_name, gm.subject, gm.global_body_id, gm.create_date, gm.status, gm.approval_id, gm.approved_date, gm.bank_id, gm.affiliate_bank_id, gm.record_type, gm.msg_type, gm.to_group_type, gm.color, gm.priority, gm.display_from_date, gm.display_to_date, gm.template_name, gm.send_now FROM global_message gm");
      boolean bool2 = false;
      bool2 = Profile.appendSQLSelectString(localStringBuffer, "gm.", "bank_id", paramString, false, false, bool2);
      if (bool2)
      {
        localStringBuffer.append(" and ");
      }
      else
      {
        localStringBuffer.append(" where ");
        bool2 = true;
      }
      localStringBuffer.append("gm.affiliate_bank_id IN (");
      localStringBuffer.append(paramInt1);
      if (paramInt1 != 0) {
        localStringBuffer.append(",0");
      }
      localStringBuffer.append(") ");
      if (bool2)
      {
        localStringBuffer.append(" and ");
      }
      else
      {
        localStringBuffer.append(" where\t");
        bool2 = true;
      }
      localStringBuffer.append("gm.msg_type IN\t(6) ");
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.set(11, 0);
      localCalendar.set(12, 0);
      localCalendar.set(13, 0);
      localCalendar.set(14, 0);
      java.util.Date localDate = localCalendar.getTime();
      bool2 = Profile.appendSQLSelectDate(localStringBuffer, "gm.", "display_from_date", localDate, "<=", bool2);
      bool2 = Profile.appendSQLSelectDate(localStringBuffer, "gm.", "display_to_date", localDate, ">=", bool2);
      if (bool2)
      {
        localStringBuffer.append(" and ");
      }
      else
      {
        localStringBuffer.append(" where\t");
        bool2 = true;
      }
      localStringBuffer.append("gm.status=2");
      if (bool2)
      {
        localStringBuffer.append(" and ");
      }
      else
      {
        localStringBuffer.append(" where\t");
        bool2 = true;
      }
      localStringBuffer.append("gm.record_type=1");
      if (bool2)
      {
        localStringBuffer.append(" and ");
      }
      else
      {
        localStringBuffer.append(" where\t");
        bool2 = true;
      }
      localStringBuffer.append("gm.to_group_type IN (");
      if (paramInt2 == 1) {
        localStringBuffer.append("0,2");
      } else if (paramInt2 == 2) {
        localStringBuffer.append("0,3");
      }
      localStringBuffer.append(") ");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramString, "bank_id", false);
      i = Profile.setStatementDate(localPreparedStatement, i, new Timestamp(localDate.getTime()), true);
      i = Profile.setStatementDate(localPreparedStatement, i, new Timestamp(localDate.getTime()), true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      HashMap localHashMap = new HashMap();
      while (localResultSet.next() == true) {
        ((GlobalMessages)localObject1).add(processGlobalMsgRS(paramConnection, localResultSet, Locale.getDefault(), localHashMap, bool1, paramHashMap));
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localObject1;
  }
  
  protected void populateI18NGlobalMsgListTX(Connection paramConnection, GlobalMessages paramGlobalMessages, String paramString, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "MessageAdapter.populateI18NGlobalMsgListTX";
    StringBuffer localStringBuffer1 = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    if (paramGlobalMessages.isEmpty()) {
      return;
    }
    if ((paramString != null) && (paramString.equals("en_US"))) {
      return;
    }
    StringBuffer localStringBuffer2 = jdMethod_for(paramGlobalMessages);
    if (localStringBuffer2 == null) {
      return;
    }
    localStringBuffer1 = new StringBuffer("SELECT gm.global_msg_id, gm.language, gm.to_group_name, gm.from_name, gm.subject FROM global_message_i18n gm");
    localStringBuffer1.append(" where " + localStringBuffer2.toString());
    try
    {
      if ((paramString != null) && (paramString.length() != 0))
      {
        boolean bool = true;
        bool = Profile.appendSQLSelectString(localStringBuffer1, "gm.", "language", paramString, false, false, bool);
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer1.toString());
        int i = 1;
        i = Profile.setStatementString(localPreparedStatement, i, paramString, "language", false);
      }
      else
      {
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer1.toString());
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer1.toString());
      while (localResultSet.next() == true)
      {
        String str2 = Profile.getRSString(localResultSet, "language");
        GlobalMessage localGlobalMessage = paramGlobalMessages.getByID(localResultSet.getInt("global_msg_id"));
        if (localGlobalMessage == null) {
          Profile.handleError(str1, "Could not find global message to populate i18n info.", 3823);
        }
        localGlobalMessage.setToGroupName(str2, Profile.getRSString(localResultSet, "to_group_name"));
        String str3 = Profile.getRSString(localResultSet, "from_name");
        if ((str3 != null) && (!str3.equals(" "))) {
          localGlobalMessage.setFromName(str2, Profile.getRSString(localResultSet, "from_name"));
        }
        localGlobalMessage.setSubject(str2, Profile.getRSString(localResultSet, "subject"));
        String str4 = getI18NGlobalMsgBodyTX(paramConnection, localGlobalMessage.getGlobalMsgBodyIDValue(), str2);
        localGlobalMessage.setMsgText(str2, str4);
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  protected GlobalMessage processGlobalMsgRS(Connection paramConnection, ResultSet paramResultSet, Locale paramLocale, HashMap paramHashMap)
    throws Exception
  {
    return processGlobalMsgRS(paramConnection, paramResultSet, paramLocale, paramHashMap, true, null);
  }
  
  protected GlobalMessage processGlobalMsgRS(Connection paramConnection, ResultSet paramResultSet, Locale paramLocale, HashMap paramHashMap1, boolean paramBoolean, HashMap paramHashMap2)
    throws Exception
  {
    GlobalMessage localGlobalMessage = new GlobalMessage(paramLocale);
    localGlobalMessage.setGlobalMsgID(paramResultSet.getInt("global_msg_id"));
    localGlobalMessage.setToGroupID(paramResultSet.getInt("to_group_id"));
    localGlobalMessage.setFromID(paramResultSet.getInt("from_id"));
    localGlobalMessage.setGlobalMsgBodyID(paramResultSet.getInt("global_body_id"));
    localGlobalMessage.setRecordType(paramResultSet.getInt("record_type"));
    localGlobalMessage.setMsgType(paramResultSet.getInt("msg_type"));
    localGlobalMessage.setToGroupType(paramResultSet.getInt("to_group_type"));
    localGlobalMessage.setColor(paramResultSet.getInt("color"));
    localGlobalMessage.setPriority(paramResultSet.getInt("priority"));
    localGlobalMessage.setTemplateName(Profile.getRSString(paramResultSet, "template_name"));
    localGlobalMessage.setBankId(Profile.getRSString(paramResultSet, "bank_id"));
    localGlobalMessage.setAffiliateBankId(paramResultSet.getInt("affiliate_bank_id"));
    String str1 = Profile.getRSString(paramResultSet, "send_now");
    if (str1.equals("1")) {
      localGlobalMessage.setSendNow(true);
    } else {
      localGlobalMessage.setSendNow(false);
    }
    Timestamp localTimestamp = null;
    localTimestamp = paramResultSet.getTimestamp("create_date");
    if (localTimestamp != null) {
      localGlobalMessage.setCreateDate(localTimestamp);
    }
    localGlobalMessage.setStatus(paramResultSet.getInt("status"));
    localGlobalMessage.setApprovalEmployeeID(paramResultSet.getInt("approval_id"));
    localTimestamp = paramResultSet.getTimestamp("approved_date");
    if (localTimestamp != null) {
      localGlobalMessage.setApprovedDate(localTimestamp);
    }
    localTimestamp = paramResultSet.getTimestamp("display_from_date");
    if (localTimestamp != null) {
      localGlobalMessage.setDisplayFromDate(localTimestamp);
    }
    localTimestamp = paramResultSet.getTimestamp("display_to_date");
    if (localTimestamp != null) {
      localGlobalMessage.setDisplayToDate(localTimestamp);
    }
    String str2 = getPersonalName(paramConnection, localGlobalMessage.getFromIDValue(), 0, localGlobalMessage.getLocale(), paramHashMap1, true, true, null);
    localGlobalMessage.setFromIDName(str2);
    String str3;
    if (localGlobalMessage.getStatusValue() == 2)
    {
      str3 = getPersonalName(paramConnection, localGlobalMessage.getApprovalEmployeeIDValue(), 0, localGlobalMessage.getLocale(), paramHashMap1, null);
      localGlobalMessage.setApprovedByName(str3);
    }
    if (paramBoolean)
    {
      localGlobalMessage.setToGroupName("en_US", Profile.getRSString(paramResultSet, "to_group_name"));
      str3 = Profile.getRSString(paramResultSet, "from_name");
      if ((str3 != null) && (!str3.equals(" "))) {
        localGlobalMessage.setFromName("en_US", str3);
      }
      localGlobalMessage.setSubject("en_US", Profile.getRSString(paramResultSet, "subject"));
      String str4 = getGlobalMsgBodyTX(paramConnection, localGlobalMessage.getGlobalMsgBodyIDValue());
      localGlobalMessage.setMsgText("en_US", str4);
    }
    return localGlobalMessage;
  }
  
  private static int jdMethod_new(PreparedStatement paramPreparedStatement, GlobalMessage paramGlobalMessage, int paramInt)
    throws Exception
  {
    jdMethod_for(paramGlobalMessage, "en_US");
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getGlobalMsgIDValue(), false);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getToGroupIDValue(), false);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getToGroupName("en_US"), "to_group_name", false);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getFromIDValue(), false);
    String str = paramGlobalMessage.getFromName("en_US");
    if ((str == null) || (str.length() == 0)) {
      str = " ";
    }
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, str, "from_name", false, false, false, true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getSubject("en_US"), "subject", false, false, false, true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getGlobalMsgBodyIDValue(), false);
    paramInt = Profile.setStatementDate(paramPreparedStatement, paramInt, paramGlobalMessage.getCreateDateAsTimestamp(), false);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getStatusValue(), false);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getApprovalEmployeeIDValue(), false);
    paramInt = Profile.setStatementDate(paramPreparedStatement, paramInt, paramGlobalMessage.getApprovedDateAsTimestamp(), false);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getBankId(), "bank_id", false, false, false, true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getAffiliateBankIdValue(), false);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getRecordTypeValue(), false);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getMsgTypeValue(), false);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getToGroupTypeValue(), false);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getColorValue(), false);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getPriorityValue(), false);
    paramInt = Profile.setStatementDate(paramPreparedStatement, paramInt, paramGlobalMessage.getDisplayFromDateAsTimestamp(), false);
    paramInt = Profile.setStatementDate(paramPreparedStatement, paramInt, paramGlobalMessage.getDisplayToDateAsTimestamp(), false);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getTemplateName(), "template_name", false, false, false, true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getSendNowValue() ? "1" : "0", "send_now", false, false, false, true);
    return paramInt;
  }
  
  private static int jdMethod_int(PreparedStatement paramPreparedStatement, GlobalMessage paramGlobalMessage, String paramString, int paramInt)
    throws Exception
  {
    jdMethod_for(paramGlobalMessage, paramString);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getToGroupName(paramString), "to_group_name", false);
    String str = paramGlobalMessage.getFromName(paramString);
    if ((str == null) || (str.length() == 0)) {
      str = " ";
    }
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, str, "from_name", false, false, false, true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getSubject(paramString), "subject", false, false, false, true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getGlobalMsgIDValue(), false);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramString, "language", false);
    return paramInt;
  }
  
  private static int jdMethod_int(PreparedStatement paramPreparedStatement, GlobalMessage paramGlobalMessage, int paramInt)
    throws Exception
  {
    jdMethod_for(paramGlobalMessage, "en_US");
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getToGroupIDValue(), false);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getToGroupName("en_US"), "to_group_name", false);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getFromIDValue(), false);
    String str = paramGlobalMessage.getFromName("en_US");
    if ((str == null) || (str.length() == 0)) {
      str = " ";
    }
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, str, "from_name", false, false, false, true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getSubject("en_US"), "subject", false, false, false, true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getGlobalMsgBodyIDValue(), false);
    paramInt = Profile.setStatementDate(paramPreparedStatement, paramInt, paramGlobalMessage.getCreateDateAsTimestamp(), false);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getStatusValue(), false);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getApprovalEmployeeIDValue(), false);
    paramInt = Profile.setStatementDate(paramPreparedStatement, paramInt, paramGlobalMessage.getApprovedDateAsTimestamp(), false);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getBankId(), "bank_id", false, false, false, true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getAffiliateBankIdValue(), false);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getRecordTypeValue(), false);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getMsgTypeValue(), false);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getToGroupTypeValue(), false);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getColorValue(), false);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getPriorityValue(), false);
    paramInt = Profile.setStatementDate(paramPreparedStatement, paramInt, paramGlobalMessage.getDisplayFromDateAsTimestamp(), false);
    paramInt = Profile.setStatementDate(paramPreparedStatement, paramInt, paramGlobalMessage.getDisplayToDateAsTimestamp(), false);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getTemplateName(), "template_name", false, false, false, true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getSendNowValue() ? "1" : "0", "send_now", false, false, false, true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getGlobalMsgIDValue(), false);
    return paramInt;
  }
  
  private static int jdMethod_for(PreparedStatement paramPreparedStatement, GlobalMessage paramGlobalMessage, String paramString, int paramInt)
    throws Exception
  {
    jdMethod_for(paramGlobalMessage, paramString);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getToGroupName(paramString), "to_group_name", false);
    String str = paramGlobalMessage.getFromName(paramString);
    if ((str == null) || (str.length() == 0)) {
      str = " ";
    }
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, str, "from_name", false, false, false, true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getSubject(paramString), "subject", false, false, false, true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getGlobalMsgIDValue(), false);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramString, "language", false);
    return paramInt;
  }
  
  private static boolean jdMethod_for(StringBuffer paramStringBuffer, GlobalMessage paramGlobalMessage, boolean paramBoolean)
    throws Exception
  {
    paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "gm.", "affiliate_bank_id", paramGlobalMessage.getAffiliateBankIdValue(), paramBoolean);
    paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "gm.", "to_group_id", paramGlobalMessage.getToGroupIDValue(), paramBoolean);
    paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "gm.", "from_id", paramGlobalMessage.getFromIDValue(), paramBoolean);
    paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "gm.", "status", paramGlobalMessage.getStatusValue(), paramBoolean);
    paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "gm.", "approval_id", paramGlobalMessage.getApprovalEmployeeIDValue(), paramBoolean);
    DateTime localDateTime1 = paramGlobalMessage.getCreateDateValue();
    if (localDateTime1 != null) {
      paramBoolean = Profile.appendSQLSelectDate(paramStringBuffer, "gm.", "create_date", localDateTime1.getTime(), "=", paramBoolean);
    }
    DateTime localDateTime2 = paramGlobalMessage.getApprovedDateValue();
    if (localDateTime2 != null) {
      paramBoolean = Profile.appendSQLSelectDate(paramStringBuffer, "gm.", "approved_date", localDateTime2.getTime(), "=", paramBoolean);
    }
    paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "gm.", "record_type", paramGlobalMessage.getRecordTypeValue(), paramBoolean);
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "gm.", "template_name", paramGlobalMessage.getTemplateName(), false, false, paramBoolean);
    Object localObject;
    if (paramGlobalMessage.getSearchLanguage() == null)
    {
      localObject = UtilHandler.getLanguageList(new SecureUser(), new HashMap());
      Iterator localIterator = ((LanguageDefns)localObject).iterator();
      String str = "gm";
      int i = 0;
      while (localIterator.hasNext())
      {
        LanguageDefn localLanguageDefn = (LanguageDefn)localIterator.next();
        if (localLanguageDefn != null)
        {
          if (!localLanguageDefn.getLanguage().equals("en_US")) {
            str = "gmI18N";
          }
          paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, str + ".", "to_group_name", paramGlobalMessage.getToGroupName(localLanguageDefn.getLanguage()), true, true, paramBoolean);
          paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, str + ".", "from_name", paramGlobalMessage.getFromName(localLanguageDefn.getLanguage()), true, true, paramBoolean);
          paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, str + ".", "subject", paramGlobalMessage.getSubject(localLanguageDefn.getLanguage()), true, true, paramBoolean);
        }
      }
    }
    else
    {
      localObject = "gm";
      if (!paramGlobalMessage.getLanguage().equals("en_US")) {
        localObject = "gmI18N";
      }
      paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, (String)localObject + ".", "to_group_name", paramGlobalMessage.getToGroupName(paramGlobalMessage.getLanguage()), true, true, paramBoolean);
      paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, (String)localObject + ".", "from_name", paramGlobalMessage.getFromName(paramGlobalMessage.getLanguage()), true, true, paramBoolean);
      paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, (String)localObject + ".", "subject", paramGlobalMessage.getSubject(paramGlobalMessage.getLanguage()), true, true, paramBoolean);
    }
    return paramBoolean;
  }
  
  private static int jdMethod_for(PreparedStatement paramPreparedStatement, GlobalMessage paramGlobalMessage, int paramInt)
    throws Exception
  {
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getAffiliateBankIdValue(), true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getToGroupIDValue(), true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getFromIDValue(), true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getStatusValue(), true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getApprovalEmployeeIDValue(), true);
    paramInt = Profile.setStatementDate(paramPreparedStatement, paramInt, paramGlobalMessage.getCreateDateAsTimestamp(), true);
    paramInt = Profile.setStatementDate(paramPreparedStatement, paramInt, paramGlobalMessage.getApprovedDateAsTimestamp(), true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramGlobalMessage.getRecordTypeValue(), true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getTemplateName(), "template_name", true);
    if (paramGlobalMessage.getSearchLanguage() == null)
    {
      LanguageDefns localLanguageDefns = UtilHandler.getLanguageList(new SecureUser(), new HashMap());
      Iterator localIterator = localLanguageDefns.iterator();
      while (localIterator.hasNext())
      {
        LanguageDefn localLanguageDefn = (LanguageDefn)localIterator.next();
        if (localLanguageDefn != null)
        {
          paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getToGroupName(localLanguageDefn.getLanguage()), "to_group_name", true, true, true);
          paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getFromName(localLanguageDefn.getLanguage()), "from_name", true, true, true);
          paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getSubject(localLanguageDefn.getLanguage()), "subject", true, true, true);
        }
      }
    }
    else
    {
      paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getToGroupName(paramGlobalMessage.getLanguage()), "to_group_name", true, true, true);
      paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getFromName(paramGlobalMessage.getLanguage()), "from_name", true, true, true);
      paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramGlobalMessage.getSubject(paramGlobalMessage.getLanguage()), "subject", true, true, true);
    }
    return paramInt;
  }
  
  public com.ffusion.beans.messages.Messages getMessagesByThread(SecureUser paramSecureUser, HashMap paramHashMap, MessageThread paramMessageThread)
    throws ProfileException
  {
    String str = "MessageAdapter.getMessagesByThread";
    Profile.isInitialized();
    Connection localConnection = null;
    if (!Profile.isValidId(paramSecureUser.getBankID())) {
      Profile.handleError(str, "Invalid Bank Id", 4);
    }
    if (!Profile.isValidId(paramMessageThread.getThreadID())) {
      Profile.handleError(str, "Invalid Thread Id", 4);
    }
    com.ffusion.beans.messages.Messages localMessages = new com.ffusion.beans.messages.Messages(paramSecureUser.getLocale());
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      paramMessageThread.setMessages(localMessages);
      getMessagesForMessageThread(localConnection, paramSecureUser, paramHashMap, paramMessageThread);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    return localMessages;
  }
  
  public ReportResult getBCReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BCReportException, RptException, Exception
  {
    String str1 = "MessageAdapter.getBCReportData";
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    if (localProperties1 == null)
    {
      localObject1 = "A search criteria object was not found within the given report criteria.  Without a search criteria object, a report cannot be run.";
      throw new Exception((String)localObject1);
    }
    Object localObject1 = paramReportCriteria.getReportOptions();
    if (localObject1 == null)
    {
      str2 = "The report criteria used in a call to getBCReportData did not contain a valid report options object.  This object is required for report retrieval.";
      throw new Exception(str2);
    }
    this.akT = "TRUE".equals(((Properties)localObject1).getProperty("NUM_CASES_OPENED"));
    this.alX = "TRUE".equals(((Properties)localObject1).getProperty("TIME_TO_OPEN_AVG"));
    this.alI = "TRUE".equals(((Properties)localObject1).getProperty("TIME_TO_OPEN_RANGE"));
    this.aoh = "TRUE".equals(((Properties)localObject1).getProperty("TIME_TO_OPEN_STD_DEV"));
    this.akw = "TRUE".equals(((Properties)localObject1).getProperty("CASE_RES_NUM_SINGLE_AGENT_IN_PROG"));
    this.al8 = "TRUE".equals(((Properties)localObject1).getProperty("CASE_RES_PERCENT_SINGLE_AGENT_IN_PROG"));
    this.an4 = "TRUE".equals(((Properties)localObject1).getProperty("CASE_RES_NUM_SINGLE_AGENT_CLOSED"));
    this.akz = "TRUE".equals(((Properties)localObject1).getProperty("CASE_RES_PERCENT_SINGLE_AGENT_CLOSED"));
    this.amD = "TRUE".equals(((Properties)localObject1).getProperty("CASE_RES_NUM_HELP_REQ_IN_PROG"));
    this.ang = "TRUE".equals(((Properties)localObject1).getProperty("CASE_RES_PERCENT_HELP_REQ_IN_PROG"));
    this.amZ = "TRUE".equals(((Properties)localObject1).getProperty("CASE_RES_NUM_HELP_REQ_CLOSED"));
    this.amo = "TRUE".equals(((Properties)localObject1).getProperty("CASE_RES_PERCENT_HELP_REQ_CLOSED"));
    this.anT = "TRUE".equals(((Properties)localObject1).getProperty("CASE_RES_NUM_HELP_PROVIDED_CLOSED"));
    this.alb = "TRUE".equals(((Properties)localObject1).getProperty("CASE_RES_PERCENT_HELP_PROVIDED_CLOSED"));
    this.amx = ((this.akT) || (this.al8) || (this.akz) || (this.ang) || (this.amo));
    this.am8 = ((this.alX) || (this.aoh));
    this.amc = ((this.akw) || (this.al8));
    this.alr = ((this.an4) || (this.akz));
    this.aon = ((this.amD) || (this.ang));
    this.amG = ((this.amZ) || (this.amo));
    this.anW = ((this.anT) || (this.alb));
    String str2 = ((Properties)localObject1).getProperty("REPORTTYPE");
    if (str2 == null)
    {
      localObject2 = "The report options contained within the Report Criteria used in a call to MessageAdapter.getBCReportData does not contain a report type.";
      throw new Exception((String)localObject2);
    }
    Object localObject2 = paramSecureUser.getLocale();
    ReportResult localReportResult = new ReportResult((Locale)localObject2);
    paramReportCriteria.setLocale((Locale)localObject2);
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    ArrayList localArrayList3 = new ArrayList();
    String str3 = String.valueOf(paramSecureUser.getProfileID());
    String str4 = null;
    String str5 = null;
    int i = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    BankEmployees localBankEmployees = null;
    Connection localConnection = null;
    HashMap localHashMap = new HashMap();
    try
    {
      Properties localProperties2 = paramReportCriteria.getSearchCriteria();
      localBankEmployees = (BankEmployees)paramHashMap.get("BankEmployeesForReport");
      if ((str2.equals("CSR Team Performance Report")) || (str2.equals("My Organization's Performance Report"))) {
        str4 = getTeamMembersForReport(paramReportCriteria, localBankEmployees);
      } else {
        setBankEmployeeNamesForReport(paramReportCriteria, localBankEmployees);
      }
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      getCaseTypes(localConnection, paramReportCriteria, localArrayList2, localArrayList3);
      String str6 = localProperties2.getProperty("Affiliate Bank");
      String str7 = null;
      int j = 0;
      if ((str6 != null) && (str6.length() > 0))
      {
        if (!str6.equals("AllAffiliateBanks"))
        {
          j = Integer.parseInt(str6);
          str7 = (String)paramHashMap.get("AffiliateBankNameForReport");
        }
        else
        {
          str7 = ReportConsts.getText(10048, (Locale)localObject2);
        }
      }
      else {
        str7 = ReportConsts.getText(10048, (Locale)localObject2);
      }
      paramReportCriteria.setDisplayValue("Affiliate Bank", str7);
      String str8 = jdMethod_for(localProperties2, localArrayList1, str4, localArrayList2, j, localStringBuffer, paramHashMap);
      jdMethod_for(localConnection, localHashMap, str8, localStringBuffer.toString(), " m.queue_id", str2);
      calculateDateRanges(localArrayList1);
      setPageWidth(localArrayList1, (Properties)localObject1);
      str5 = ((Properties)localObject1).getProperty("MAXDISPLAYSIZE");
      localReportResult.init(paramReportCriteria);
      Object localObject3;
      if (str2.equals("CSR Team Performance Report")) {
        i = jdMethod_int(localConnection, localHashMap, localArrayList1, localArrayList2, localArrayList3, localBankEmployees, str5, localReportResult);
      } else if (str2.equals("My Organization's Performance Report")) {
        i = jdMethod_for(localConnection, localHashMap, localArrayList1, localArrayList2, localArrayList3, localBankEmployees, str5, localReportResult);
      } else if (str2.equals("CSR Performance Report")) {
        i = jdMethod_new(localConnection, localHashMap, localArrayList1, localArrayList2, localArrayList3, localBankEmployees, str5, localReportResult);
      } else if (str2.equals("My Performance Report"))
      {
        if ((localBankEmployees.size() == 1) && (((BankEmployee)localBankEmployees.get(0)).getId().equals(str3)))
        {
          i = jdMethod_for(localConnection, localHashMap, localArrayList1, localArrayList2, localArrayList3, (BankEmployee)localBankEmployees.get(0), str5, localReportResult);
        }
        else
        {
          DebugLog.log(Level.INFO, str1 + ": Insanity check for My Performance Report failed.");
          localObject3 = new BankEmployee(Locale.getDefault());
          ((BankEmployee)localObject3).setId(str3);
          ((BankEmployee)localObject3).setUserName(paramSecureUser.getUserName());
          i = jdMethod_for(localConnection, localHashMap, localArrayList1, localArrayList2, localArrayList3, (BankEmployee)localObject3, str5, localReportResult);
        }
      }
      else {
        throw new RptException(7);
      }
      if ((str5 != null) && (i >= Integer.parseInt(str5)))
      {
        localObject3 = new HashMap();
        ((HashMap)localObject3).put("TRUNCATED", new Integer(i));
        localReportResult.setProperties((HashMap)localObject3);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.out);
      localReportResult.setError(localException);
      Profile.handleError(localException, str1);
    }
    finally
    {
      Iterator localIterator = localHashMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str9 = (String)localIterator.next();
        PreparedStatement localPreparedStatement = (PreparedStatement)localHashMap.get(str9);
        if (localPreparedStatement != null) {
          DBUtil.closeStatement(localPreparedStatement);
        }
      }
      DBUtil.closeAll(Profile.poolName, localConnection, null, null);
      localReportResult.fini();
    }
    return localReportResult;
  }
  
  private int jdMethod_int(Connection paramConnection, HashMap paramHashMap, ArrayList paramArrayList1, ArrayList paramArrayList2, ArrayList paramArrayList3, BankEmployees paramBankEmployees, String paramString, ReportResult paramReportResult)
    throws Exception
  {
    PreparedStatement localPreparedStatement1 = (PreparedStatement)paramHashMap.get("RPT_STMTCASESNEW");
    PreparedStatement localPreparedStatement2 = (PreparedStatement)paramHashMap.get("RPT_STMTCASESOPENED");
    PreparedStatement localPreparedStatement3 = (PreparedStatement)paramHashMap.get("RPT_STMTCASESOPEN");
    PreparedStatement localPreparedStatement4 = (PreparedStatement)paramHashMap.get("RPT_STMTCASESOPENQUEUEID");
    PreparedStatement localPreparedStatement5 = (PreparedStatement)paramHashMap.get("RPT_STMTTIMETOOPENAVG");
    PreparedStatement localPreparedStatement6 = (PreparedStatement)paramHashMap.get("RPT_STMTTIMETOOPENRANGE");
    PreparedStatement localPreparedStatement7 = (PreparedStatement)paramHashMap.get("RPT_STMTTIMETOOPENSTDDEVIATION");
    PreparedStatement localPreparedStatement8 = (PreparedStatement)paramHashMap.get("RPT_STMTTIMETOOPENSTDDEVIATIONBYQUEUEID");
    PreparedStatement localPreparedStatement9 = (PreparedStatement)paramHashMap.get("RPT_STMTSINGLEAGENTINPROG");
    PreparedStatement localPreparedStatement10 = (PreparedStatement)paramHashMap.get("RPT_STMTSINGLEAGENTINPROGQUEUEID");
    PreparedStatement localPreparedStatement11 = (PreparedStatement)paramHashMap.get("RPT_STMTSINGLEAGENTCLOSED");
    PreparedStatement localPreparedStatement12 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPREQINPROG");
    PreparedStatement localPreparedStatement13 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPREQINPROGQUEUEID");
    PreparedStatement localPreparedStatement14 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPREQCLOSED");
    PreparedStatement localPreparedStatement15 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPPROVIDED");
    PreparedStatement localPreparedStatement16 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPPROVIDEDQUEUEID");
    PreparedStatement localPreparedStatement17 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPPROVIDEDCLOSED");
    ResultSet localResultSet = null;
    HashMap localHashMap = new HashMap();
    ArrayList localArrayList1 = null;
    CasePerformanceData localCasePerformanceData = null;
    Integer[] arrayOfInteger = null;
    Timestamp localTimestamp1 = null;
    Timestamp localTimestamp2 = null;
    Timestamp localTimestamp3 = null;
    Timestamp localTimestamp4 = null;
    int i = 0;
    ArrayList localArrayList2 = (ArrayList)paramArrayList1.clone();
    jdMethod_for(localHashMap, (ArrayList)paramArrayList1.clone(), paramArrayList2, paramArrayList3);
    while ((localArrayList2 != null) && (!localArrayList2.isEmpty()))
    {
      localTimestamp1 = (Timestamp)localArrayList2.remove(0);
      localTimestamp2 = (Timestamp)localArrayList2.remove(0);
      if (localTimestamp3 == null) {
        localTimestamp3 = localTimestamp1;
      }
      localTimestamp4 = localTimestamp2;
      localCasePerformanceData = new CasePerformanceData();
      localCasePerformanceData.setCaseType("Total");
      if (this.amx)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement1, 16, null, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 16, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement2, 1, null, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 1, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement3, 17, null, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 17, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.am8)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement5, 2, null, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 2, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.alI)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement6, 3, null, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 3, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.aoh)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement7, 4, null, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 4, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.amc)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement9, 5, null, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 5, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.al8) {
        jdMethod_for(null, localHashMap, 6, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
      }
      if (this.alr)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement11, 7, null, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 7, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if ((this.amG) && (paramBankEmployees != null))
      {
        localObject1 = paramBankEmployees.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = new Integer(((BankEmployee)((Iterator)localObject1).next()).getId());
          localResultSet = jdMethod_for(paramConnection, localPreparedStatement14, 11, (Integer)localObject2, localTimestamp1, localTimestamp2);
          jdMethod_for(localResultSet, localHashMap, 11, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
          DBUtil.closeResultSet(localResultSet);
        }
      }
      if (this.akz) {
        jdMethod_for(null, localHashMap, 8, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
      }
      if (this.aon)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement12, 9, null, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 9, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.ang) {
        jdMethod_for(null, localHashMap, 10, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
      }
      if (this.amo) {
        jdMethod_for(null, localHashMap, 12, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
      }
      if ((this.anW) && (paramBankEmployees != null))
      {
        localObject1 = paramBankEmployees.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = new Integer(((BankEmployee)((Iterator)localObject1).next()).getId());
          localResultSet = jdMethod_for(paramConnection, localPreparedStatement17, 13, (Integer)localObject2, localTimestamp1, localTimestamp2);
          jdMethod_for(localResultSet, localHashMap, 13, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
          DBUtil.closeResultSet(localResultSet);
          localResultSet = jdMethod_for(paramConnection, localPreparedStatement15, 21, (Integer)localObject2, localTimestamp1, localTimestamp2);
          jdMethod_for(localResultSet, localHashMap, 21, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
          DBUtil.closeResultSet(localResultSet);
        }
      }
      if (this.alb) {
        jdMethod_for(null, localHashMap, 14, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
      }
      localArrayList1 = (ArrayList)localHashMap.get("SUMMARY_LIST");
      localArrayList1.set(i, localCasePerformanceData);
      i++;
    }
    Object localObject1 = null;
    Object localObject2 = null;
    arrayOfInteger = new Integer[paramBankEmployees.size()];
    int j = 0;
    Iterator localIterator = paramBankEmployees.iterator();
    while (localIterator.hasNext()) {
      arrayOfInteger[(j++)] = new Integer(((BankEmployee)localIterator.next()).getId());
    }
    localIterator = localHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      localObject3 = localIterator.next();
      if (!"SUMMARY_LIST".equals(localObject3))
      {
        localObject1 = (ArrayList)localHashMap.get(localObject3);
        if (localObject1 != null)
        {
          localObject2 = jdMethod_for((ArrayList)localObject1, paramConnection, localTimestamp3, localTimestamp4, (Integer)localObject3, null, arrayOfInteger, localPreparedStatement8, localPreparedStatement4, localPreparedStatement10, localPreparedStatement13, localPreparedStatement16);
          ((ArrayList)localObject1).add(localObject2);
        }
      }
    }
    Object localObject3 = paramReportResult.getLocale();
    int k = paramArrayList1.size() / 2 + 2;
    jdMethod_for(paramReportResult, paramArrayList1, k, (Locale)localObject3);
    ReportResult localReportResult = new ReportResult((Locale)localObject3);
    paramReportResult.addSubReport(localReportResult);
    int m = 0;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    Integer localInteger = null;
    if (paramString == null) {
      i2 = 1;
    } else {
      try
      {
        i1 = Integer.parseInt(paramString);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        String str = "The report option: MaxDisplaySize, specified for the performance report is not a valid number ";
        throw new Exception(str);
      }
    }
    while ((n < paramArrayList2.size()) && ((i2 != 0) || (m < i1)))
    {
      localInteger = (Integer)paramArrayList2.get(n);
      m += jdMethod_for(localReportResult, (ArrayList)localHashMap.get(localInteger), k, false, (String)paramArrayList3.get(n));
      n++;
    }
    m += jdMethod_for(localReportResult, (ArrayList)localHashMap.get("SUMMARY_LIST"), k, true, null);
    return m;
  }
  
  private int jdMethod_for(Connection paramConnection, HashMap paramHashMap, ArrayList paramArrayList1, ArrayList paramArrayList2, ArrayList paramArrayList3, BankEmployees paramBankEmployees, String paramString, ReportResult paramReportResult)
    throws Exception
  {
    PreparedStatement localPreparedStatement1 = (PreparedStatement)paramHashMap.get("RPT_STMTCASESNEW");
    PreparedStatement localPreparedStatement2 = (PreparedStatement)paramHashMap.get("RPT_STMTCASESOPENED");
    PreparedStatement localPreparedStatement3 = (PreparedStatement)paramHashMap.get("RPT_STMTCASESOPEN");
    PreparedStatement localPreparedStatement4 = (PreparedStatement)paramHashMap.get("RPT_STMTCASESOPENQUEUEID");
    PreparedStatement localPreparedStatement5 = (PreparedStatement)paramHashMap.get("RPT_STMTTIMETOOPENAVG");
    PreparedStatement localPreparedStatement6 = (PreparedStatement)paramHashMap.get("RPT_STMTTIMETOOPENRANGE");
    PreparedStatement localPreparedStatement7 = (PreparedStatement)paramHashMap.get("RPT_STMTTIMETOOPENSTDDEVIATION");
    PreparedStatement localPreparedStatement8 = (PreparedStatement)paramHashMap.get("RPT_STMTTIMETOOPENSTDDEVIATIONBYQUEUEID");
    PreparedStatement localPreparedStatement9 = (PreparedStatement)paramHashMap.get("RPT_STMTSINGLEAGENTINPROG");
    PreparedStatement localPreparedStatement10 = (PreparedStatement)paramHashMap.get("RPT_STMTSINGLEAGENTINPROGQUEUEID");
    PreparedStatement localPreparedStatement11 = (PreparedStatement)paramHashMap.get("RPT_STMTSINGLEAGENTCLOSED");
    PreparedStatement localPreparedStatement12 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPREQINPROG");
    PreparedStatement localPreparedStatement13 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPREQINPROGQUEUEID");
    PreparedStatement localPreparedStatement14 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPREQCLOSED");
    PreparedStatement localPreparedStatement15 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPPROVIDED");
    PreparedStatement localPreparedStatement16 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPPROVIDEDQUEUEID");
    PreparedStatement localPreparedStatement17 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPPROVIDEDCLOSED");
    CSRTeamPerformanceRpt localCSRTeamPerformanceRpt = new CSRTeamPerformanceRpt();
    ResultSet localResultSet = null;
    HashMap localHashMap = new HashMap();
    ArrayList localArrayList1 = null;
    CasePerformanceData localCasePerformanceData = null;
    Integer[] arrayOfInteger = null;
    Timestamp localTimestamp1 = null;
    Timestamp localTimestamp2 = null;
    Timestamp localTimestamp3 = null;
    Timestamp localTimestamp4 = null;
    int i = 0;
    ArrayList localArrayList2 = (ArrayList)paramArrayList1.clone();
    jdMethod_for(localHashMap, (ArrayList)paramArrayList1.clone(), paramArrayList2, paramArrayList3);
    while ((localArrayList2 != null) && (!localArrayList2.isEmpty()))
    {
      localTimestamp1 = (Timestamp)localArrayList2.remove(0);
      localTimestamp2 = (Timestamp)localArrayList2.remove(0);
      if (localTimestamp3 == null) {
        localTimestamp3 = localTimestamp1;
      }
      localTimestamp4 = localTimestamp2;
      localCasePerformanceData = new CasePerformanceData();
      localCasePerformanceData.setCaseType("Total");
      if (this.amx)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement1, 16, null, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 16, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement2, 1, null, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 1, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement3, 17, null, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 17, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.am8)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement5, 2, null, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 2, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.alI)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement6, 3, null, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 3, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.aoh)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement7, 4, null, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 4, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.amc)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement9, 5, null, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 5, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.al8) {
        jdMethod_for(null, localHashMap, 6, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
      }
      if (this.alr)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement11, 7, null, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 7, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if ((this.amG) && (paramBankEmployees != null))
      {
        localObject1 = paramBankEmployees.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = new Integer(((BankEmployee)((Iterator)localObject1).next()).getId());
          localResultSet = jdMethod_for(paramConnection, localPreparedStatement14, 11, (Integer)localObject2, localTimestamp1, localTimestamp2);
          jdMethod_for(localResultSet, localHashMap, 11, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
          DBUtil.closeResultSet(localResultSet);
        }
      }
      if (this.akz) {
        jdMethod_for(null, localHashMap, 8, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
      }
      if (this.aon)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement12, 9, null, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 9, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.ang) {
        jdMethod_for(null, localHashMap, 10, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
      }
      if (this.amo) {
        jdMethod_for(null, localHashMap, 12, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
      }
      if ((this.anW) && (paramBankEmployees != null))
      {
        localObject1 = paramBankEmployees.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = new Integer(((BankEmployee)((Iterator)localObject1).next()).getId());
          localResultSet = jdMethod_for(paramConnection, localPreparedStatement15, 21, (Integer)localObject2, localTimestamp1, localTimestamp2);
          jdMethod_for(localResultSet, localHashMap, 21, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
          DBUtil.closeResultSet(localResultSet);
          localResultSet = jdMethod_for(paramConnection, localPreparedStatement17, 13, (Integer)localObject2, localTimestamp1, localTimestamp2);
          jdMethod_for(localResultSet, localHashMap, 13, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
          DBUtil.closeResultSet(localResultSet);
        }
      }
      if (this.alb) {
        jdMethod_for(null, localHashMap, 14, i, localCasePerformanceData, localTimestamp1, localTimestamp2);
      }
      localArrayList1 = (ArrayList)localHashMap.get("SUMMARY_LIST");
      localArrayList1.set(i, localCasePerformanceData);
      i++;
    }
    Object localObject1 = null;
    Object localObject2 = null;
    arrayOfInteger = new Integer[paramBankEmployees.size()];
    int j = 0;
    Iterator localIterator = paramBankEmployees.iterator();
    while (localIterator.hasNext()) {
      arrayOfInteger[(j++)] = new Integer(((BankEmployee)localIterator.next()).getId());
    }
    localIterator = localHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      Object localObject3 = localIterator.next();
      if (!"SUMMARY_LIST".equals(localObject3))
      {
        localObject1 = (ArrayList)localHashMap.get(localObject3);
        if (localObject1 != null)
        {
          localObject2 = jdMethod_for((ArrayList)localObject1, paramConnection, localTimestamp3, localTimestamp4, (Integer)localObject3, null, arrayOfInteger, localPreparedStatement8, localPreparedStatement4, localPreparedStatement10, localPreparedStatement13, localPreparedStatement16);
          ((ArrayList)localObject1).add(localObject2);
        }
      }
    }
    int k = 0;
    Locale localLocale = paramReportResult.getLocale();
    int m = paramArrayList1.size() / 2 + 2;
    jdMethod_for(paramReportResult, paramArrayList1, m, localLocale);
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    Integer localInteger = null;
    if (paramString == null) {
      i3 = 1;
    } else {
      try
      {
        i2 = Integer.parseInt(paramString);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        String str = "The report option: MaxDisplaySize, specified for the performance report is not a valid number ";
        throw new Exception(str);
      }
    }
    while ((i1 < paramArrayList2.size()) && ((i3 != 0) || (n < i2)))
    {
      localInteger = (Integer)paramArrayList2.get(i1);
      k += jdMethod_for(localReportResult, (ArrayList)localHashMap.get(localInteger), m, false, (String)paramArrayList3.get(i1));
      i1++;
    }
    k += jdMethod_for(localReportResult, (ArrayList)localHashMap.get("SUMMARY_LIST"), m, true, null);
    return k;
  }
  
  private int jdMethod_new(Connection paramConnection, HashMap paramHashMap, ArrayList paramArrayList1, ArrayList paramArrayList2, ArrayList paramArrayList3, BankEmployees paramBankEmployees, String paramString, ReportResult paramReportResult)
    throws Exception
  {
    PreparedStatement localPreparedStatement1 = (PreparedStatement)paramHashMap.get("RPT_STMTCASESNEW");
    PreparedStatement localPreparedStatement2 = (PreparedStatement)paramHashMap.get("RPT_STMTCASESOPENED");
    PreparedStatement localPreparedStatement3 = (PreparedStatement)paramHashMap.get("RPT_STMTCASESOPEN");
    PreparedStatement localPreparedStatement4 = (PreparedStatement)paramHashMap.get("RPT_STMTCASESOPENQUEUEID");
    PreparedStatement localPreparedStatement5 = (PreparedStatement)paramHashMap.get("RPT_STMTTIMETOOPENAVG");
    PreparedStatement localPreparedStatement6 = (PreparedStatement)paramHashMap.get("RPT_STMTTIMETOOPENRANGE");
    PreparedStatement localPreparedStatement7 = (PreparedStatement)paramHashMap.get("RPT_STMTTIMETOOPENSTDDEVIATION");
    PreparedStatement localPreparedStatement8 = (PreparedStatement)paramHashMap.get("RPT_STMTTIMETOOPENSTDDEVIATIONBYQUEUEID");
    PreparedStatement localPreparedStatement9 = (PreparedStatement)paramHashMap.get("RPT_STMTSINGLEAGENTINPROG");
    PreparedStatement localPreparedStatement10 = (PreparedStatement)paramHashMap.get("RPT_STMTSINGLEAGENTINPROGQUEUEID");
    PreparedStatement localPreparedStatement11 = (PreparedStatement)paramHashMap.get("RPT_STMTSINGLEAGENTCLOSED");
    PreparedStatement localPreparedStatement12 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPREQINPROG");
    PreparedStatement localPreparedStatement13 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPREQINPROGQUEUEID");
    PreparedStatement localPreparedStatement14 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPREQCLOSED");
    PreparedStatement localPreparedStatement15 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPPROVIDED");
    PreparedStatement localPreparedStatement16 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPPROVIDEDQUEUEID");
    PreparedStatement localPreparedStatement17 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPPROVIDEDCLOSED");
    int i = 0;
    Locale localLocale = paramReportResult.getLocale();
    int j = paramArrayList1.size() / 2 + 2;
    jdMethod_for(paramReportResult, paramArrayList1, j, localLocale);
    Iterator localIterator1 = paramBankEmployees.iterator();
    while (localIterator1.hasNext())
    {
      BankEmployee localBankEmployee = (BankEmployee)localIterator1.next();
      Integer localInteger1 = new Integer(localBankEmployee.getId());
      ReportResult localReportResult = new ReportResult(localLocale);
      paramReportResult.addSubReport(localReportResult);
      ReportHeading localReportHeading = new ReportHeading(localLocale);
      localReportHeading.setLabel(ReportConsts.getText(2219, localLocale) + " " + localBankEmployee.getFullNameWithLoginId() + ":");
      localReportResult.setHeading(localReportHeading);
      HashMap localHashMap = new HashMap();
      jdMethod_for(localHashMap, (ArrayList)paramArrayList1.clone(), paramArrayList2, paramArrayList3);
      ArrayList localArrayList1 = null;
      CasePerformanceData localCasePerformanceData1 = null;
      Timestamp localTimestamp1 = null;
      Timestamp localTimestamp2 = null;
      Timestamp localTimestamp3 = null;
      Timestamp localTimestamp4 = null;
      int k = 0;
      ResultSet localResultSet = null;
      ArrayList localArrayList2 = (ArrayList)paramArrayList1.clone();
      while ((localArrayList2 != null) && (!localArrayList2.isEmpty()))
      {
        localTimestamp1 = (Timestamp)localArrayList2.remove(0);
        localTimestamp2 = (Timestamp)localArrayList2.remove(0);
        if (localTimestamp3 == null) {
          localTimestamp3 = localTimestamp1;
        }
        localTimestamp4 = localTimestamp2;
        localCasePerformanceData1 = new CasePerformanceData();
        localCasePerformanceData1.setCaseType("Total");
        if (this.amx)
        {
          localResultSet = jdMethod_for(paramConnection, localPreparedStatement1, 16, localInteger1, localTimestamp1, localTimestamp2);
          jdMethod_for(localResultSet, localHashMap, 16, k, localCasePerformanceData1, localTimestamp1, localTimestamp2);
          DBUtil.closeResultSet(localResultSet);
          localResultSet = jdMethod_for(paramConnection, localPreparedStatement2, 1, localInteger1, localTimestamp1, localTimestamp2);
          jdMethod_for(localResultSet, localHashMap, 1, k, localCasePerformanceData1, localTimestamp1, localTimestamp2);
          DBUtil.closeResultSet(localResultSet);
          localResultSet = jdMethod_for(paramConnection, localPreparedStatement3, 17, localInteger1, localTimestamp1, localTimestamp2);
          jdMethod_for(localResultSet, localHashMap, 17, k, localCasePerformanceData1, localTimestamp1, localTimestamp2);
          DBUtil.closeResultSet(localResultSet);
        }
        if (this.am8)
        {
          localResultSet = jdMethod_for(paramConnection, localPreparedStatement5, 2, localInteger1, localTimestamp1, localTimestamp2);
          jdMethod_for(localResultSet, localHashMap, 2, k, localCasePerformanceData1, localTimestamp1, localTimestamp2);
          DBUtil.closeResultSet(localResultSet);
        }
        if (this.alI)
        {
          localResultSet = jdMethod_for(paramConnection, localPreparedStatement6, 3, localInteger1, localTimestamp1, localTimestamp2);
          jdMethod_for(localResultSet, localHashMap, 3, k, localCasePerformanceData1, localTimestamp1, localTimestamp2);
          DBUtil.closeResultSet(localResultSet);
        }
        if (this.aoh)
        {
          localResultSet = jdMethod_for(paramConnection, localPreparedStatement7, 4, localInteger1, localTimestamp1, localTimestamp2);
          jdMethod_for(localResultSet, localHashMap, 4, k, localCasePerformanceData1, localTimestamp1, localTimestamp2);
          DBUtil.closeResultSet(localResultSet);
        }
        if (this.amc)
        {
          localResultSet = jdMethod_for(paramConnection, localPreparedStatement9, 5, localInteger1, localTimestamp1, localTimestamp2);
          jdMethod_for(localResultSet, localHashMap, 5, k, localCasePerformanceData1, localTimestamp1, localTimestamp2);
          DBUtil.closeResultSet(localResultSet);
        }
        if (this.al8) {
          jdMethod_for(null, localHashMap, 6, k, localCasePerformanceData1, localTimestamp1, localTimestamp2);
        }
        if (this.alr)
        {
          localResultSet = jdMethod_for(paramConnection, localPreparedStatement11, 7, localInteger1, localTimestamp1, localTimestamp2);
          jdMethod_for(localResultSet, localHashMap, 7, k, localCasePerformanceData1, localTimestamp1, localTimestamp2);
          DBUtil.closeResultSet(localResultSet);
        }
        if (this.amG)
        {
          localResultSet = jdMethod_for(paramConnection, localPreparedStatement14, 11, localInteger1, localTimestamp1, localTimestamp2);
          jdMethod_for(localResultSet, localHashMap, 11, k, localCasePerformanceData1, localTimestamp1, localTimestamp2);
          DBUtil.closeResultSet(localResultSet);
        }
        if (this.akz) {
          jdMethod_for(null, localHashMap, 8, k, localCasePerformanceData1, localTimestamp1, localTimestamp2);
        }
        if (this.aon)
        {
          localResultSet = jdMethod_for(paramConnection, localPreparedStatement12, 9, localInteger1, localTimestamp1, localTimestamp2);
          jdMethod_for(localResultSet, localHashMap, 9, k, localCasePerformanceData1, localTimestamp1, localTimestamp2);
          DBUtil.closeResultSet(localResultSet);
        }
        if (this.ang) {
          jdMethod_for(null, localHashMap, 10, k, localCasePerformanceData1, localTimestamp1, localTimestamp2);
        }
        if (this.amo) {
          jdMethod_for(null, localHashMap, 12, k, localCasePerformanceData1, localTimestamp1, localTimestamp2);
        }
        if (this.anW)
        {
          localResultSet = jdMethod_for(paramConnection, localPreparedStatement17, 13, localInteger1, localTimestamp1, localTimestamp2);
          jdMethod_for(localResultSet, localHashMap, 13, k, localCasePerformanceData1, localTimestamp1, localTimestamp2);
          DBUtil.closeResultSet(localResultSet);
          localResultSet = jdMethod_for(paramConnection, localPreparedStatement15, 21, localInteger1, localTimestamp1, localTimestamp2);
          jdMethod_for(localResultSet, localHashMap, 21, k, localCasePerformanceData1, localTimestamp1, localTimestamp2);
          DBUtil.closeResultSet(localResultSet);
        }
        if (this.alb) {
          jdMethod_for(null, localHashMap, 14, k, localCasePerformanceData1, localTimestamp1, localTimestamp2);
        }
        localArrayList1 = (ArrayList)localHashMap.get("SUMMARY_LIST");
        localArrayList1.set(k, localCasePerformanceData1);
        k++;
      }
      ArrayList localArrayList3 = null;
      CasePerformanceData localCasePerformanceData2 = null;
      Iterator localIterator2 = localHashMap.keySet().iterator();
      while (localIterator2.hasNext())
      {
        Object localObject = localIterator2.next();
        if (!"SUMMARY_LIST".equals(localObject))
        {
          localArrayList3 = (ArrayList)localHashMap.get(localObject);
          if (localArrayList3 != null)
          {
            localCasePerformanceData2 = jdMethod_for(localArrayList3, paramConnection, localTimestamp3, localTimestamp4, (Integer)localObject, localInteger1, new Integer[] { localInteger1 }, localPreparedStatement8, localPreparedStatement4, localPreparedStatement10, localPreparedStatement13, localPreparedStatement16);
            localArrayList3.add(localCasePerformanceData2);
          }
        }
      }
      int m = 0;
      int n = 0;
      int i1 = 0;
      int i2 = 0;
      Integer localInteger2 = null;
      if (paramString == null) {
        i2 = 1;
      } else {
        try
        {
          i1 = Integer.parseInt(paramString);
        }
        catch (NumberFormatException localNumberFormatException)
        {
          String str = "The report option: MaxDisplaySize, specified for the performance report is not a valid number ";
          throw new Exception(str);
        }
      }
      while ((n < paramArrayList2.size()) && ((i2 != 0) || (m < i1)))
      {
        localInteger2 = (Integer)paramArrayList2.get(n);
        i += jdMethod_for(localReportResult, (ArrayList)localHashMap.get(localInteger2), j, false, (String)paramArrayList3.get(n));
        n++;
      }
      i += jdMethod_for(localReportResult, (ArrayList)localHashMap.get("SUMMARY_LIST"), j, true, null);
    }
    return i;
  }
  
  private int jdMethod_for(Connection paramConnection, HashMap paramHashMap, ArrayList paramArrayList1, ArrayList paramArrayList2, ArrayList paramArrayList3, BankEmployee paramBankEmployee, String paramString, ReportResult paramReportResult)
    throws Exception
  {
    PreparedStatement localPreparedStatement1 = (PreparedStatement)paramHashMap.get("RPT_STMTCASESNEW");
    PreparedStatement localPreparedStatement2 = (PreparedStatement)paramHashMap.get("RPT_STMTCASESOPENED");
    PreparedStatement localPreparedStatement3 = (PreparedStatement)paramHashMap.get("RPT_STMTCASESOPEN");
    PreparedStatement localPreparedStatement4 = (PreparedStatement)paramHashMap.get("RPT_STMTCASESOPENQUEUEID");
    PreparedStatement localPreparedStatement5 = (PreparedStatement)paramHashMap.get("RPT_STMTTIMETOOPENAVG");
    PreparedStatement localPreparedStatement6 = (PreparedStatement)paramHashMap.get("RPT_STMTTIMETOOPENRANGE");
    PreparedStatement localPreparedStatement7 = (PreparedStatement)paramHashMap.get("RPT_STMTTIMETOOPENSTDDEVIATION");
    PreparedStatement localPreparedStatement8 = (PreparedStatement)paramHashMap.get("RPT_STMTTIMETOOPENSTDDEVIATIONBYQUEUEID");
    PreparedStatement localPreparedStatement9 = (PreparedStatement)paramHashMap.get("RPT_STMTSINGLEAGENTINPROG");
    PreparedStatement localPreparedStatement10 = (PreparedStatement)paramHashMap.get("RPT_STMTSINGLEAGENTINPROGQUEUEID");
    PreparedStatement localPreparedStatement11 = (PreparedStatement)paramHashMap.get("RPT_STMTSINGLEAGENTCLOSED");
    PreparedStatement localPreparedStatement12 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPREQINPROG");
    PreparedStatement localPreparedStatement13 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPREQINPROGQUEUEID");
    PreparedStatement localPreparedStatement14 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPREQCLOSED");
    PreparedStatement localPreparedStatement15 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPPROVIDED");
    PreparedStatement localPreparedStatement16 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPPROVIDEDQUEUEID");
    PreparedStatement localPreparedStatement17 = (PreparedStatement)paramHashMap.get("RPT_STMTHELPPROVIDEDCLOSED");
    ResultSet localResultSet = null;
    HashMap localHashMap = new HashMap();
    ArrayList localArrayList1 = null;
    CasePerformanceData localCasePerformanceData1 = null;
    Integer localInteger1 = new Integer(paramBankEmployee.getId());
    Timestamp localTimestamp1 = null;
    Timestamp localTimestamp2 = null;
    Timestamp localTimestamp3 = null;
    Timestamp localTimestamp4 = null;
    int i = 0;
    ArrayList localArrayList2 = (ArrayList)paramArrayList1.clone();
    jdMethod_for(localHashMap, (ArrayList)paramArrayList1.clone(), paramArrayList2, paramArrayList3);
    while ((localArrayList2 != null) && (!localArrayList2.isEmpty()))
    {
      localTimestamp1 = (Timestamp)localArrayList2.remove(0);
      localTimestamp2 = (Timestamp)localArrayList2.remove(0);
      if (localTimestamp3 == null) {
        localTimestamp3 = localTimestamp1;
      }
      localTimestamp4 = localTimestamp2;
      localCasePerformanceData1 = new CasePerformanceData();
      localCasePerformanceData1.setCaseType("Total");
      if (this.amx)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement1, 16, localInteger1, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 16, i, localCasePerformanceData1, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement2, 1, localInteger1, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 1, i, localCasePerformanceData1, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement3, 17, localInteger1, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 17, i, localCasePerformanceData1, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.am8)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement5, 2, localInteger1, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 2, i, localCasePerformanceData1, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.alI)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement6, 3, localInteger1, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 3, i, localCasePerformanceData1, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.aoh)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement7, 4, localInteger1, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 4, i, localCasePerformanceData1, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.amc)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement9, 5, localInteger1, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 5, i, localCasePerformanceData1, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.al8) {
        jdMethod_for(null, localHashMap, 6, i, localCasePerformanceData1, localTimestamp1, localTimestamp2);
      }
      if (this.alr)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement11, 7, localInteger1, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 7, i, localCasePerformanceData1, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.amG)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement14, 11, localInteger1, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 11, i, localCasePerformanceData1, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.akz) {
        jdMethod_for(null, localHashMap, 8, i, localCasePerformanceData1, localTimestamp1, localTimestamp2);
      }
      if (this.aon)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement12, 9, localInteger1, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 9, i, localCasePerformanceData1, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.ang) {
        jdMethod_for(null, localHashMap, 10, i, localCasePerformanceData1, localTimestamp1, localTimestamp2);
      }
      if (this.amo) {
        jdMethod_for(null, localHashMap, 12, i, localCasePerformanceData1, localTimestamp1, localTimestamp2);
      }
      if (this.anW)
      {
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement17, 13, localInteger1, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 13, i, localCasePerformanceData1, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
        localResultSet = jdMethod_for(paramConnection, localPreparedStatement15, 21, localInteger1, localTimestamp1, localTimestamp2);
        jdMethod_for(localResultSet, localHashMap, 21, i, localCasePerformanceData1, localTimestamp1, localTimestamp2);
        DBUtil.closeResultSet(localResultSet);
      }
      if (this.alb) {
        jdMethod_for(null, localHashMap, 14, i, localCasePerformanceData1, localTimestamp1, localTimestamp2);
      }
      localArrayList1 = (ArrayList)localHashMap.get("SUMMARY_LIST");
      localArrayList1.set(i, localCasePerformanceData1);
      i++;
    }
    ArrayList localArrayList3 = null;
    CasePerformanceData localCasePerformanceData2 = null;
    Iterator localIterator = localHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if (!"SUMMARY_LIST".equals(localObject))
      {
        localArrayList3 = (ArrayList)localHashMap.get(localObject);
        if (localArrayList3 != null)
        {
          localCasePerformanceData2 = jdMethod_for(localArrayList3, paramConnection, localTimestamp3, localTimestamp4, (Integer)localObject, localInteger1, new Integer[] { localInteger1 }, localPreparedStatement8, localPreparedStatement4, localPreparedStatement10, localPreparedStatement13, localPreparedStatement16);
          localArrayList3.add(localCasePerformanceData2);
        }
      }
    }
    int j = 0;
    Locale localLocale = paramReportResult.getLocale();
    int k = paramArrayList1.size() / 2 + 2;
    jdMethod_for(paramReportResult, paramArrayList1, k, localLocale);
    Integer localInteger2 = new Integer(paramBankEmployee.getId());
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    int m = 0;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    Integer localInteger3 = null;
    if (paramString == null) {
      i2 = 1;
    } else {
      try
      {
        i1 = Integer.parseInt(paramString);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        String str = "The report option: MaxDisplaySize, specified for the performance report is not a valid number ";
        throw new Exception(str);
      }
    }
    while ((n < paramArrayList2.size()) && ((i2 != 0) || (m < i1)))
    {
      localInteger3 = (Integer)paramArrayList2.get(n);
      j += jdMethod_for(localReportResult, (ArrayList)localHashMap.get(localInteger3), k, false, (String)paramArrayList3.get(n));
      n++;
    }
    j += jdMethod_for(localReportResult, (ArrayList)localHashMap.get("SUMMARY_LIST"), k, true, null);
    return j;
  }
  
  private static void jdMethod_for(ReportResult paramReportResult, int paramInt, Locale paramLocale)
    throws Exception
  {
    ReportColumn localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(jdMethod_for(paramInt, true));
    paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    localReportColumn.setWidthAsPercent(jdMethod_for(paramInt, false));
    for (int i = 1; i < paramInt; i++) {
      paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    }
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumColumns(paramInt);
    localReportDataDimensions.setNumRows(-1);
    paramReportResult.setDataDimensions(localReportDataDimensions);
  }
  
  protected void modifyMessageType(Connection paramConnection, int paramInt1, int paramInt2)
    throws Exception
  {
    String str = "MessageAdapter.modifyMessageType";
    StringBuffer localStringBuffer = null;
    PreparedStatement localPreparedStatement = null;
    if (!Profile.isValidId(paramInt1)) {
      Profile.handleError(str, "Invalid Message Id", 4);
    }
    try
    {
      localStringBuffer = new StringBuffer("UPDATE message_item SET message_type=?");
      boolean bool = false;
      bool = jdMethod_try(localStringBuffer, paramInt1, bool);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = jdMethod_for(localPreparedStatement, paramInt2, paramInt1, i);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private static boolean jdMethod_try(StringBuffer paramStringBuffer, int paramInt, boolean paramBoolean)
  {
    paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "item_id", paramInt, paramBoolean);
    return paramBoolean;
  }
  
  private static int jdMethod_for(PreparedStatement paramPreparedStatement, int paramInt1, int paramInt2, int paramInt3)
    throws Exception
  {
    paramInt3 = Profile.setStatementInt(paramPreparedStatement, paramInt3, paramInt1, true);
    paramInt3 = Profile.setStatementInt(paramPreparedStatement, paramInt3, paramInt2, true);
    return paramInt3;
  }
  
  private static String jdMethod_for(Properties paramProperties, ArrayList paramArrayList1, String paramString, ArrayList paramArrayList2, int paramInt, StringBuffer paramStringBuffer)
    throws Exception
  {
    return jdMethod_for(paramProperties, paramArrayList1, paramString, paramArrayList2, paramInt, paramStringBuffer, null);
  }
  
  private static String jdMethod_for(Properties paramProperties, ArrayList paramArrayList1, String paramString, ArrayList paramArrayList2, int paramInt, StringBuffer paramStringBuffer, HashMap paramHashMap)
    throws Exception
  {
    StringBuffer localStringBuffer1 = new StringBuffer();
    StringBuffer localStringBuffer2 = new StringBuffer();
    if (paramString != null)
    {
      localStringBuffer1.append(" AND ");
      localStringBuffer1.append(DBUtil.generateSQLNumericInClause(paramString, " m.employee_id "));
    }
    if (paramInt != 0)
    {
      localStringBuffer2.append(" AND (");
      localStringBuffer2.append("m.directory_id");
      localStringBuffer2.append(" IN (");
      localStringBuffer2.append("select c1.directory_id from customer c1 where c1.affiliate_bank_id=");
      localStringBuffer2.append(paramInt);
      localStringBuffer2.append(") OR ");
      localStringBuffer2.append("m.directory_id");
      localStringBuffer2.append(" IN (");
      localStringBuffer2.append("select c2.directory_id from customer c2, business_employee be, business b where c2.customer_type='1' and c2.directory_id=be.directory_id and be.business_id=b.directory_id and b.affiliate_bank_id=");
      localStringBuffer2.append(paramInt);
      localStringBuffer2.append(")) ");
    }
    Object localObject;
    if (paramProperties != null)
    {
      localObject = paramProperties.getProperty("StartDate");
      String str = paramProperties.getProperty("EndDate");
      if ((localObject != null) && (((String)localObject).length() != 0) && (str != null) && (str.length() != 0)) {
        try
        {
          paramArrayList1.add(getTimestampFromString((String)localObject));
          paramArrayList1.add(getTimestampFromString(str));
        }
        catch (ParseException localParseException)
        {
          throw new Exception("Either the start or end date is not in MM/dd/yyyy HH:mm:ss format.");
        }
      }
    }
    if ((paramArrayList2 != null) && (paramArrayList2.size() != 0))
    {
      localObject = new StringBuffer();
      for (int i = 0; i < paramArrayList2.size(); i++)
      {
        ((StringBuffer)localObject).append((Integer)paramArrayList2.get(i));
        ((StringBuffer)localObject).append(",");
      }
      ((StringBuffer)localObject).deleteCharAt(((StringBuffer)localObject).length() - 1);
      localStringBuffer2.append(" AND");
      localStringBuffer2.append(" m.queue_id ");
      localStringBuffer2.append(" IN (" + ((StringBuffer)localObject).toString() + ")");
    }
    paramStringBuffer.append(localStringBuffer2);
    localStringBuffer1.append(localStringBuffer2);
    return localStringBuffer1.toString();
  }
  
  protected void calculateDateRanges(ArrayList paramArrayList)
    throws Exception
  {
    Timestamp localTimestamp1 = null;
    Timestamp localTimestamp2 = (Timestamp)paramArrayList.remove(0);
    Timestamp localTimestamp3 = (Timestamp)paramArrayList.remove(0);
    paramArrayList.clear();
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(localTimestamp2);
    while (localTimestamp3.getTime() - localCalendar.getTimeInMillis() > 604800000L)
    {
      paramArrayList.add(localTimestamp2);
      localCalendar.add(5, 7);
      localCalendar.add(14, -1);
      localTimestamp1 = new Timestamp(localCalendar.getTime().getTime());
      paramArrayList.add(localTimestamp1);
      localCalendar.add(14, 1);
      localTimestamp2 = new Timestamp(localCalendar.getTime().getTime());
    }
    paramArrayList.add(localTimestamp2);
    paramArrayList.add(localTimestamp3);
  }
  
  protected static Timestamp getTimestampFromString(String paramString)
    throws ParseException
  {
    java.util.Date localDate = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").parse(paramString);
    return new Timestamp(localDate.getTime());
  }
  
  protected Timestamp getTimestampFromString(String paramString1, String paramString2)
    throws ParseException
  {
    java.util.Date localDate = DateFormatUtil.getFormatter(paramString2 + " " + "HH:mm:ss").parse(paramString1);
    return new Timestamp(localDate.getTime());
  }
  
  private static ResultSet jdMethod_for(Connection paramConnection, PreparedStatement paramPreparedStatement, int paramInt, Integer paramInteger, Timestamp paramTimestamp1, Timestamp paramTimestamp2)
    throws Exception
  {
    ResultSet localResultSet = null;
    try
    {
      int i = 1;
      if (paramInteger != null) {
        paramPreparedStatement.setInt(i++, paramInteger.intValue());
      }
      switch (paramInt)
      {
      case 16: 
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        break;
      case 1: 
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        break;
      case 17: 
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        break;
      case 18: 
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        break;
      case 2: 
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        break;
      case 3: 
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        break;
      case 4: 
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        break;
      case 15: 
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        break;
      case 5: 
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        break;
      case 19: 
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        break;
      case 7: 
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        break;
      case 9: 
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        break;
      case 20: 
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        break;
      case 11: 
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setInt(i++, paramInteger.intValue());
        break;
      case 13: 
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setInt(i++, paramInteger.intValue());
        break;
      case 21: 
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setInt(i++, paramInteger.intValue());
        paramPreparedStatement.setInt(i++, paramInteger.intValue());
        break;
      case 22: 
        paramPreparedStatement.setTimestamp(i++, paramTimestamp1);
        paramPreparedStatement.setTimestamp(i++, paramTimestamp2);
        paramPreparedStatement.setInt(i++, paramInteger.intValue());
        paramPreparedStatement.setInt(i++, paramInteger.intValue());
        break;
      case 6: 
      case 8: 
      case 10: 
      case 12: 
      case 14: 
      default: 
        throw new Exception("Invalid query type specified: " + paramInt);
      }
      localResultSet = DBUtil.executeQuery(paramPreparedStatement, "");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new Exception(localException.getMessage());
    }
    return localResultSet;
  }
  
  private static void jdMethod_for(Connection paramConnection, HashMap paramHashMap, String paramString1, String paramString2, String paramString3, String paramString4)
    throws Exception
  {
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    PreparedStatement localPreparedStatement3 = null;
    PreparedStatement localPreparedStatement4 = null;
    PreparedStatement localPreparedStatement5 = null;
    PreparedStatement localPreparedStatement6 = null;
    PreparedStatement localPreparedStatement7 = null;
    PreparedStatement localPreparedStatement8 = null;
    PreparedStatement localPreparedStatement9 = null;
    PreparedStatement localPreparedStatement10 = null;
    PreparedStatement localPreparedStatement11 = null;
    PreparedStatement localPreparedStatement12 = null;
    PreparedStatement localPreparedStatement13 = null;
    PreparedStatement localPreparedStatement14 = null;
    PreparedStatement localPreparedStatement15 = null;
    PreparedStatement localPreparedStatement16 = null;
    PreparedStatement localPreparedStatement17 = null;
    try
    {
      if ((paramString4.equals("CSR Performance Report")) || (paramString4.equals("My Performance Report")))
      {
        localPreparedStatement1 = jdMethod_for(paramConnection, localPreparedStatement1, "SELECT m.queue_id, COUNT(*) AS total FROM message m, queue q WHERE m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date >= ? AND m.create_date <= ? ", paramString2, paramString3);
        localPreparedStatement4 = jdMethod_for(paramConnection, localPreparedStatement4, "SELECT m.queue_id, COUNT(*) AS total FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ?) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND mi.from_type = 0)) ", paramString2, paramString3);
        localPreparedStatement2 = jdMethod_for(paramConnection, localPreparedStatement2, "SELECT m.queue_id, COUNT(*) AS total FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (m.close_date IS NOT NULL AND m.close_date >= ? AND m.close_date <= ?) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) ", paramString2, paramString3);
        localPreparedStatement3 = jdMethod_for(paramConnection, localPreparedStatement3, "SELECT COUNT(*) AS total FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (m.close_date IS NOT NULL AND m.close_date >= ? AND m.close_date <= ?) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) AND m.queue_id = ?", paramString1, null);
        if ((Profile.dbType.equalsIgnoreCase("DB2:APP")) || (Profile.dbType.equalsIgnoreCase("DB2:UN2")) || (Profile.dbType.equalsIgnoreCase("DB2:NET")) || (Profile.dbType.equalsIgnoreCase("DB2:AS390")))
        {
          localPreparedStatement5 = jdMethod_for(paramConnection, localPreparedStatement5, "SELECT m.queue_id, AVG( TIMESTAMPDIFF( 2, CHAR(mi.read_date - m.create_date))) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString2, paramString3);
          localPreparedStatement6 = jdMethod_for(paramConnection, localPreparedStatement6, "SELECT m.queue_id, MIN( TIMESTAMPDIFF( 2, CHAR(mi.read_date - m.create_date))), MAX( TIMESTAMPDIFF( 2, CHAR(mi.read_date - m.create_date))) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString1, paramString3);
        }
        else if ((Profile.dbType.equalsIgnoreCase("ORACLE:THIN")) || (Profile.dbType.equalsIgnoreCase("ORACLE:OCI8")))
        {
          localPreparedStatement5 = jdMethod_for(paramConnection, localPreparedStatement5, "SELECT m.queue_id, AVG( CAST(mi.read_date as date) - CAST(m.create_date as date) ) * 86400 FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString1, paramString3);
          localPreparedStatement6 = jdMethod_for(paramConnection, localPreparedStatement6, "SELECT m.queue_id, MIN( CAST(mi.read_date as date) - CAST(m.create_date as date) ) * 86400, MAX( CAST(mi.read_date as date) - CAST(m.create_date as date) ) * 86400 FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString1, paramString3);
        }
        else if (Profile.dbType.equalsIgnoreCase("MSSQL:THIN"))
        {
          localPreparedStatement5 = jdMethod_for(paramConnection, localPreparedStatement5, "SELECT m.queue_id, AVG( DATEDIFF (second, m.create_date, mi.read_date)) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString1, paramString3);
          localPreparedStatement6 = jdMethod_for(paramConnection, localPreparedStatement6, "SELECT m.queue_id, MIN( DATEDIFF (second, m.create_date, mi.read_date)), MAX( DATEDIFF (second, m.create_date, mi.read_date)) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString1, paramString3);
        }
        else if (Profile.dbType.equalsIgnoreCase("ASE"))
        {
          localPreparedStatement5 = jdMethod_for(paramConnection, localPreparedStatement5, "SELECT m.queue_id, AVG( DATEDIFF (second, m.create_date, mi.read_date)) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString1, paramString3);
          localPreparedStatement6 = jdMethod_for(paramConnection, localPreparedStatement6, "SELECT m.queue_id, MIN( DATEDIFF (second, m.create_date, mi.read_date)), MAX( DATEDIFF (second, m.create_date, mi.read_date)) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString1, paramString3);
        }
        else
        {
          localPreparedStatement5 = jdMethod_for(paramConnection, localPreparedStatement5, "SELECT m.queue_id, AVG( mi.read_date - m.create_date ) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString1, paramString3);
          localPreparedStatement6 = jdMethod_for(paramConnection, localPreparedStatement6, "SELECT m.queue_id, MIN( mi.read_date - m.create_date ), MAX( mi.read_date - m.create_date ) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString1, paramString3);
        }
        localPreparedStatement7 = jdMethod_for(paramConnection, localPreparedStatement7, "SELECT m.queue_id, mi.read_date, m.create_date FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString1, null);
        localPreparedStatement8 = jdMethod_for(paramConnection, localPreparedStatement8, "SELECT mi.read_date, m.create_date FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.employee_id = ? AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND q.queue_id = ?", paramString1, null);
        localPreparedStatement9 = jdMethod_for(paramConnection, localPreparedStatement9, "SELECT m.queue_id, COUNT(m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND m.employee_id = ? AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND NOT EXISTS (SELECT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) ", paramString1, paramString3);
        localPreparedStatement10 = jdMethod_for(paramConnection, localPreparedStatement10, "SELECT COUNT(m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND m.employee_id = ? AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND NOT EXISTS (SELECT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) AND q.queue_id = ?", paramString1, null);
        localPreparedStatement11 = jdMethod_for(paramConnection, localPreparedStatement11, "SELECT m.queue_id, COUNT(message_id) AS total FROM message m, queue q WHERE m.queue_id = q.queue_id AND m.employee_id = ? AND m.status = 5 AND m.close_date >= ? AND m.close_date <= ? AND NOT EXISTS (SELECT message_id FROM message_item mi WHERE m.message_id = mi.message_id AND mi.message_type = 3) ", paramString1, paramString3);
        localPreparedStatement12 = jdMethod_for(paramConnection, localPreparedStatement12, "SELECT m.queue_id, COUNT(m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND m.employee_id = ? AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND m.message_id IN (SELECT DISTINCT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) ", paramString1, paramString3);
        localPreparedStatement13 = jdMethod_for(paramConnection, localPreparedStatement13, "SELECT COUNT(m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND m.employee_id = ? AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND m.message_id IN (SELECT DISTINCT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) AND q.queue_id = ? ", paramString1, null);
      }
      else
      {
        localPreparedStatement1 = jdMethod_for(paramConnection, localPreparedStatement1, "SELECT m.queue_id, COUNT(*) AS total FROM message m, queue q WHERE m.queue_id = q.queue_id AND m.create_date >= ? AND m.create_date <= ? ", paramString1, paramString3);
        localPreparedStatement4 = jdMethod_for(paramConnection, localPreparedStatement4, "SELECT m.queue_id, COUNT(*) AS total FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ?) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND mi.from_type = 0)) ", paramString1, paramString3);
        localPreparedStatement2 = jdMethod_for(paramConnection, localPreparedStatement2, "SELECT m.queue_id, COUNT(*) AS total FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (m.close_date IS NOT NULL AND m.close_date >= ? AND m.close_date <= ?) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) ", paramString1, paramString3);
        localPreparedStatement3 = jdMethod_for(paramConnection, localPreparedStatement3, "SELECT COUNT(*) AS total FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (m.close_date IS NOT NULL AND m.close_date >= ? AND m.close_date <= ?) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) AND m.queue_id = ?", paramString1, null);
        if ((Profile.dbType.equalsIgnoreCase("DB2:APP")) || (Profile.dbType.equalsIgnoreCase("DB2:UN2")) || (Profile.dbType.equalsIgnoreCase("DB2:NET")) || (Profile.dbType.equalsIgnoreCase("DB2:AS390")))
        {
          localPreparedStatement5 = jdMethod_for(paramConnection, localPreparedStatement5, "SELECT m.queue_id, AVG( TIMESTAMPDIFF( 2, CHAR(mi.read_date - m.create_date))) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString1, paramString3);
          localPreparedStatement6 = jdMethod_for(paramConnection, localPreparedStatement6, "SELECT m.queue_id, MIN( TIMESTAMPDIFF( 2, CHAR(mi.read_date - m.create_date))), MAX( TIMESTAMPDIFF( 2, CHAR(mi.read_date - m.create_date))) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString1, paramString3);
        }
        else if ((Profile.dbType.equalsIgnoreCase("ORACLE:THIN")) || (Profile.dbType.equalsIgnoreCase("ORACLE:OCI8")))
        {
          localPreparedStatement5 = jdMethod_for(paramConnection, localPreparedStatement5, "SELECT m.queue_id, AVG(CAST(mi.read_date as date) - CAST(m.create_date as date)) * 86400 FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString1, paramString3);
          localPreparedStatement6 = jdMethod_for(paramConnection, localPreparedStatement6, "SELECT m.queue_id, MIN( CAST(mi.read_date as date) - CAST(m.create_date as date) ) * 86400, MAX( CAST(mi.read_date as date) - CAST(m.create_date as date) ) * 86400 FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString1, paramString3);
        }
        else if (Profile.dbType.equalsIgnoreCase("MSSQL:THIN"))
        {
          localPreparedStatement5 = jdMethod_for(paramConnection, localPreparedStatement5, "SELECT m.queue_id, AVG( DATEDIFF (second, m.create_date, mi.read_date)) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString1, paramString3);
          localPreparedStatement6 = jdMethod_for(paramConnection, localPreparedStatement6, "SELECT m.queue_id, MIN( DATEDIFF (second, m.create_date, mi.read_date)), MAX( DATEDIFF (second, m.create_date, mi.read_date)) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString1, paramString3);
        }
        else if (Profile.dbType.equalsIgnoreCase("ASE"))
        {
          localPreparedStatement5 = jdMethod_for(paramConnection, localPreparedStatement5, "SELECT m.queue_id, AVG( DATEDIFF (second, m.create_date, mi.read_date)) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString1, paramString3);
          localPreparedStatement6 = jdMethod_for(paramConnection, localPreparedStatement6, "SELECT m.queue_id, MIN( DATEDIFF (second, m.create_date, mi.read_date)), MAX( DATEDIFF (second, m.create_date, mi.read_date)) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString1, paramString3);
        }
        else
        {
          localPreparedStatement5 = jdMethod_for(paramConnection, localPreparedStatement5, "SELECT m.queue_id, AVG( mi.read_date - m.create_date ) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString1, paramString3);
          localPreparedStatement6 = jdMethod_for(paramConnection, localPreparedStatement6, "SELECT m.queue_id, MIN( mi.read_date - m.create_date ), MAX( mi.read_date - m.create_date ) FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? ", paramString1, paramString3);
        }
        localPreparedStatement7 = jdMethod_for(paramConnection, localPreparedStatement7, "SELECT m.queue_id, mi.read_date, m.create_date FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ?", paramString1, null);
        localPreparedStatement8 = jdMethod_for(paramConnection, localPreparedStatement8, "SELECT mi.read_date, m.create_date FROM message m, message_item mi, queue q WHERE m.message_id = mi.message_id AND m.queue_id = q.queue_id AND m.create_date IS NOT NULL AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND mi.from_type != 0 AND mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND q.queue_id = ?", paramString1, null);
        localPreparedStatement9 = jdMethod_for(paramConnection, localPreparedStatement9, "SELECT m.queue_id, COUNT(m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND NOT EXISTS (SELECT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) ", paramString1, paramString3);
        localPreparedStatement10 = jdMethod_for(paramConnection, localPreparedStatement10, "SELECT COUNT(m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND NOT EXISTS (SELECT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) AND q.queue_id = ?", paramString1, null);
        localPreparedStatement11 = jdMethod_for(paramConnection, localPreparedStatement11, "SELECT m.queue_id, COUNT(message_id) AS total FROM message m, queue q WHERE m.queue_id = q.queue_id AND m.status = 5 AND m.close_date >= ? AND m.close_date <= ? AND NOT EXISTS (SELECT message_id FROM message_item mi WHERE m.message_id = mi.message_id AND mi.message_type = 3) ", paramString1, paramString3);
        localPreparedStatement12 = jdMethod_for(paramConnection, localPreparedStatement12, "SELECT m.queue_id, COUNT(m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND m.message_id IN (SELECT DISTINCT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) ", paramString1, paramString3);
        localPreparedStatement13 = jdMethod_for(paramConnection, localPreparedStatement13, "SELECT COUNT(m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND mi.item_id = (SELECT MIN(item_id) FROM message_item WHERE m.message_id = message_id) AND m.message_id IN (SELECT DISTINCT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3) AND ((mi.read_date IS NOT NULL AND mi.read_date >= ? AND mi.read_date <= ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date >= ? AND m.create_date <= ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0) OR (mi.read_date IS NOT NULL AND mi.read_date < ? AND (m.close_date IS NULL OR m.close_date > ?)) OR (m.create_date IS NOT NULL AND m.create_date < ? AND (m.close_date IS NULL OR m.close_date > ?) AND mi.from_type = 0)) AND q.queue_id = ? ", paramString1, null);
      }
      if ((paramString4.equals("My Organization's Performance Report")) || (paramString4.equals("CSR Team Performance Report")))
      {
        localPreparedStatement17 = jdMethod_for(paramConnection, localPreparedStatement17, "SELECT m.queue_id, COUNT(m.message_id) AS total FROM message m, queue q WHERE m.queue_id = q.queue_id AND m.employee_id != ? AND m.status = 5 AND m.close_date >= ? AND m.close_date <= ? AND message_id IN (SELECT DISTINCT message_id FROM message_item mi WHERE m.message_id = mi.message_id AND mi.message_type = 3 AND mi.to_id = ?) ", paramString2, paramString3);
        localPreparedStatement15 = jdMethod_for(paramConnection, localPreparedStatement15, "SELECT m.queue_id, COUNT(DISTINCT m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND m.employee_id != ? AND mi.message_type IN (3, 5) AND mi.create_date >= ? AND mi.create_date <= ? AND mi.from_id = ? AND m.message_id IN (SELECT DISTINCT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3 AND mi2.to_id = ?) ", paramString2, paramString3);
        localPreparedStatement16 = jdMethod_for(paramConnection, localPreparedStatement16, "SELECT COUNT(DISTINCT m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND m.employee_id != ? AND mi.message_type IN (3, 5) AND mi.create_date >= ? AND mi.create_date <= ? AND mi.from_id = ? AND m.message_id IN (SELECT DISTINCT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3 AND mi2.to_id = ?) AND q.queue_id = ?", paramString2, null);
      }
      else
      {
        localPreparedStatement17 = jdMethod_for(paramConnection, localPreparedStatement17, "SELECT m.queue_id, COUNT(m.message_id) AS total FROM message m, queue q WHERE m.queue_id = q.queue_id AND m.employee_id != ? AND m.status = 5 AND m.close_date >= ? AND m.close_date <= ? AND message_id IN (SELECT DISTINCT message_id FROM message_item mi WHERE m.message_id = mi.message_id AND mi.message_type = 3 AND mi.to_id = ?) ", paramString2, paramString3);
        localPreparedStatement15 = jdMethod_for(paramConnection, localPreparedStatement15, "SELECT m.queue_id, COUNT(DISTINCT m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND m.employee_id != ? AND mi.message_type IN (3, 5) AND mi.create_date >= ? AND mi.create_date <= ? AND mi.from_id = ? AND m.message_id IN (SELECT DISTINCT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3 AND mi2.to_id = ?) ", paramString2, paramString3);
        localPreparedStatement16 = jdMethod_for(paramConnection, localPreparedStatement16, "SELECT COUNT(DISTINCT m.message_id) AS total FROM message m, message_item mi, queue q WHERE m.queue_id = q.queue_id AND m.message_id = mi.message_id AND m.employee_id != ? AND mi.message_type IN (3, 5) AND mi.create_date >= ? AND mi.create_date <= ? AND mi.from_id = ? AND m.message_id IN (SELECT DISTINCT message_id FROM message_item mi2 WHERE m.message_id = mi2.message_id AND mi2.message_type = 3 AND mi2.to_id = ?) AND q.queue_id = ?", paramString2, null);
      }
      localPreparedStatement14 = jdMethod_for(paramConnection, localPreparedStatement14, "SELECT m.queue_id, COUNT(message_id) AS total FROM message m, queue q WHERE m.queue_id = q.queue_id AND m.employee_id = ? AND m.status = 5 AND m.close_date >= ? AND m.close_date <= ? AND message_id IN (SELECT DISTINCT message_id FROM message_item mi WHERE m.message_id = mi.message_id AND mi.message_type = 3 AND mi.from_id = ?) ", paramString1, paramString3);
      paramHashMap.put("RPT_STMTCASESNEW", localPreparedStatement1);
      paramHashMap.put("RPT_STMTCASESOPEN", localPreparedStatement2);
      paramHashMap.put("RPT_STMTCASESOPENQUEUEID", localPreparedStatement3);
      paramHashMap.put("RPT_STMTCASESOPENED", localPreparedStatement4);
      paramHashMap.put("RPT_STMTTIMETOOPENAVG", localPreparedStatement5);
      paramHashMap.put("RPT_STMTTIMETOOPENRANGE", localPreparedStatement6);
      paramHashMap.put("RPT_STMTTIMETOOPENSTDDEVIATION", localPreparedStatement7);
      paramHashMap.put("RPT_STMTTIMETOOPENSTDDEVIATIONBYQUEUEID", localPreparedStatement8);
      paramHashMap.put("RPT_STMTSINGLEAGENTINPROG", localPreparedStatement9);
      paramHashMap.put("RPT_STMTSINGLEAGENTINPROGQUEUEID", localPreparedStatement10);
      paramHashMap.put("RPT_STMTSINGLEAGENTCLOSED", localPreparedStatement11);
      paramHashMap.put("RPT_STMTHELPREQINPROG", localPreparedStatement12);
      paramHashMap.put("RPT_STMTHELPREQINPROGQUEUEID", localPreparedStatement13);
      paramHashMap.put("RPT_STMTHELPREQCLOSED", localPreparedStatement14);
      paramHashMap.put("RPT_STMTHELPPROVIDED", localPreparedStatement15);
      paramHashMap.put("RPT_STMTHELPPROVIDEDQUEUEID", localPreparedStatement16);
      paramHashMap.put("RPT_STMTHELPPROVIDEDCLOSED", localPreparedStatement17);
    }
    catch (Exception localException)
    {
      throw localException;
    }
  }
  
  private static PreparedStatement jdMethod_for(Connection paramConnection, PreparedStatement paramPreparedStatement, String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    StringBuffer localStringBuffer = null;
    try
    {
      localStringBuffer = new StringBuffer(paramString1);
      if ((paramString2 != null) && (paramString2.length() != 0)) {
        localStringBuffer.append(paramString2);
      }
      if ((paramString3 != null) && (paramString3.length() != 0))
      {
        localStringBuffer.append(" GROUP BY ");
        localStringBuffer.append(paramString3);
      }
      paramPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      throw new Exception(localException.getMessage());
    }
    return paramPreparedStatement;
  }
  
  protected void setBankEmployeeNamesForReport(ReportCriteria paramReportCriteria, BankEmployees paramBankEmployees)
    throws Exception
  {
    StringBuffer localStringBuffer = new StringBuffer();
    BankEmployee localBankEmployee = null;
    if (paramBankEmployees != null)
    {
      Iterator localIterator = paramBankEmployees.iterator();
      while (localIterator.hasNext())
      {
        localBankEmployee = (BankEmployee)localIterator.next();
        localStringBuffer.append(localBankEmployee.getFullNameWithLoginId());
        localStringBuffer.append(", ");
      }
      if (localStringBuffer.length() > 1)
      {
        localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
        localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
        paramReportCriteria.setDisplayValue("Agent", localStringBuffer.toString());
      }
    }
  }
  
  protected String getTeamMembersForReport(ReportCriteria paramReportCriteria, BankEmployees paramBankEmployees)
    throws Exception
  {
    StringBuffer localStringBuffer1 = new StringBuffer();
    StringBuffer localStringBuffer2 = new StringBuffer();
    BankEmployee localBankEmployee = null;
    if (paramBankEmployees == null) {
      throw new Exception("No bank employees exist for a CSR Team Performance or My Performance Report ");
    }
    Iterator localIterator = paramBankEmployees.iterator();
    while (localIterator.hasNext())
    {
      localBankEmployee = (BankEmployee)localIterator.next();
      localStringBuffer1.append(localBankEmployee.getFullNameWithLoginId());
      localStringBuffer1.append(", ");
      localStringBuffer2.append(localBankEmployee.getId());
      localStringBuffer2.append(", ");
    }
    if (localStringBuffer1.length() > 1)
    {
      localStringBuffer1.deleteCharAt(localStringBuffer1.length() - 1);
      localStringBuffer1.deleteCharAt(localStringBuffer1.length() - 1);
      paramReportCriteria.setDisplayValue("Agent", localStringBuffer1.toString());
    }
    if (localStringBuffer2.length() > 1)
    {
      localStringBuffer2.deleteCharAt(localStringBuffer2.length() - 1);
      localStringBuffer2.deleteCharAt(localStringBuffer2.length() - 1);
    }
    return localStringBuffer2.toString();
  }
  
  protected void getCaseTypes(Connection paramConnection, ReportCriteria paramReportCriteria, ArrayList paramArrayList1, ArrayList paramArrayList2)
    throws Exception
  {
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    String str = localProperties.getProperty("CaseType");
    if ((str == null) || (str.length() == 0)) {
      throw new Exception("No case types were selected for a Performance Report ");
    }
    StringBuffer localStringBuffer1 = new StringBuffer();
    if ((paramReportCriteria.getLocale() == null) || (paramReportCriteria.getLocale().equals(Locale.US)))
    {
      localStringBuffer1.append("SELECT queue_id, queue_name FROM queue WHERE queue_type=0");
    }
    else
    {
      localStringBuffer1.append("SELECT queue_id, queue_name FROM queue_i18n WHERE");
      localStringBuffer1.append(" language='");
      localStringBuffer1.append(paramReportCriteria.getLocaleLanguage());
      localStringBuffer1.append("'");
    }
    StringBuffer localStringBuffer2 = new StringBuffer();
    ResultSet localResultSet = null;
    Statement localStatement = null;
    localStringBuffer1.append(" AND ");
    localStringBuffer1.append(DBUtil.generateSQLNumericInClause(str, "queue_id"));
    localStringBuffer1.append("ORDER BY queue_name");
    try
    {
      localStatement = DBUtil.createStatement(paramConnection);
      localResultSet = DBUtil.executeQuery(localStatement, localStringBuffer1.toString());
      while (localResultSet.next())
      {
        paramArrayList1.add(new Integer(localResultSet.getInt(1)));
        paramArrayList2.add(localResultSet.getString(2));
        localStringBuffer2.append(localResultSet.getString(2));
        localStringBuffer2.append(", ");
      }
      if (localStringBuffer2.length() > 1)
      {
        localStringBuffer2.deleteCharAt(localStringBuffer2.length() - 1);
        localStringBuffer2.deleteCharAt(localStringBuffer2.length() - 1);
        paramReportCriteria.setDisplayValue("CaseType", localStringBuffer2.toString());
      }
      if ((paramArrayList1.size() < 1) || (paramArrayList2.size() < 1)) {
        throw new Exception("No case types were found for a Performance report");
      }
    }
    catch (Exception localException)
    {
      throw localException;
    }
    finally
    {
      DBUtil.closeAll(localStatement, localResultSet);
    }
  }
  
  private static void jdMethod_for(ReportResult paramReportResult, ArrayList paramArrayList, int paramInt, Locale paramLocale)
    throws Exception
  {
    String str1 = ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", paramLocale);
    if (str1 == null) {
      str1 = "MM/dd/yyyy";
    }
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumColumns(paramInt);
    localReportDataDimensions.setNumRows(-1);
    localReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn1 = new ReportColumn(paramLocale);
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(ReportConsts.getText(2216, paramLocale));
    localReportColumn1.setLabels(localArrayList1);
    localReportColumn1.setDataType("java.lang.String");
    localReportColumn1.setWidthAsPercent(jdMethod_for(paramInt, true));
    localReportResult.addColumn(localReportColumn1);
    ReportColumn localReportColumn2 = new ReportColumn(paramLocale);
    int i = 0;
    while (i < paramArrayList.size())
    {
      localObject1 = (Timestamp)paramArrayList.get(i++);
      localObject2 = (Timestamp)paramArrayList.get(i++);
      String str2 = DateFormatUtil.getFormatter(str1).format((java.util.Date)localObject1);
      String str3 = DateFormatUtil.getFormatter(str1).format((java.util.Date)localObject2);
      ArrayList localArrayList2 = new ArrayList();
      localArrayList2.add(ReportConsts.getText(2217, paramLocale) + " " + String.valueOf(i / 2));
      localArrayList2.add(str2 + "-");
      localArrayList2.add(str3);
      localReportColumn2.setLabels(localArrayList2);
      localReportColumn2.setDataType("java.lang.String");
      localReportColumn2.setWidthAsPercent(jdMethod_for(paramInt, false));
      localReportResult.addColumn((ReportColumn)localReportColumn2.clone());
    }
    Object localObject1 = new ReportColumn(paramLocale);
    Object localObject2 = new ArrayList();
    ((ArrayList)localObject2).add(ReportConsts.getText(2218, paramLocale));
    ((ReportColumn)localObject1).setLabels((ArrayList)localObject2);
    ((ReportColumn)localObject1).setDataType("java.lang.String");
    ((ReportColumn)localObject1).setWidthAsPercent(jdMethod_for(paramInt, false));
    localReportResult.addColumn((ReportColumn)localObject1);
  }
  
  protected void setPageWidth(ArrayList paramArrayList, Properties paramProperties)
  {
    int i = paramArrayList.size() / 2;
    if (i > 8)
    {
      int j = (i + 3) * 90;
      int k = 125;
      try
      {
        k = Integer.parseInt(paramProperties.getProperty("PAGEWIDTH_TEXT"));
      }
      catch (Throwable localThrowable) {}
      int m = (k + 9) / 10;
      int n = k + (i - 8) * m;
      paramProperties.setProperty("PAGEWIDTH_TEXT", String.valueOf(n));
      paramProperties.setProperty("PAGEWIDTH", String.valueOf(j));
    }
    else
    {
      paramProperties.setProperty("PAGEWIDTH", "750");
    }
  }
  
  private static int jdMethod_for(int paramInt, boolean paramBoolean)
  {
    if (paramInt > 10)
    {
      int i = 160 / paramInt;
      if (i == 0) {
        i = 1;
      }
      if (paramBoolean) {
        return i;
      }
      return (100 - i) / (paramInt - 1);
    }
    if (paramBoolean) {
      return 25;
    }
    return 75 / (paramInt - 1);
  }
  
  private static void jdMethod_for(HashMap paramHashMap, ArrayList paramArrayList1, ArrayList paramArrayList2, ArrayList paramArrayList3)
    throws Exception
  {
    ArrayList localArrayList1 = null;
    CasePerformanceData localCasePerformanceData = null;
    Timestamp localTimestamp1 = null;
    Timestamp localTimestamp2 = null;
    ArrayList localArrayList2 = null;
    Integer localInteger = null;
    for (int i = 0; i < paramArrayList2.size(); i++)
    {
      localInteger = (Integer)paramArrayList2.get(i);
      localArrayList1 = new ArrayList();
      localArrayList2 = (ArrayList)paramArrayList1.clone();
      String str = (String)paramArrayList3.get(i);
      while ((localArrayList2 != null) && (!localArrayList2.isEmpty()))
      {
        localTimestamp1 = (Timestamp)localArrayList2.remove(0);
        localTimestamp2 = (Timestamp)localArrayList2.remove(0);
        localCasePerformanceData = new CasePerformanceData();
        localCasePerformanceData.setCaseType(str);
        localCasePerformanceData.setStartDate(localTimestamp1.getTime());
        localCasePerformanceData.setEndDate(localTimestamp2.getTime());
        localArrayList1.add(localCasePerformanceData);
      }
      paramHashMap.put(localInteger, localArrayList1);
    }
    try
    {
      localArrayList1 = new ArrayList();
      localArrayList2 = (ArrayList)paramArrayList1.clone();
      while ((localArrayList2 != null) && (!localArrayList2.isEmpty()))
      {
        localTimestamp1 = (Timestamp)localArrayList2.remove(0);
        localTimestamp2 = (Timestamp)localArrayList2.remove(0);
        localCasePerformanceData = new CasePerformanceData();
        localCasePerformanceData.setCaseType("Total");
        localCasePerformanceData.setStartDate(localTimestamp1.getTime());
        localCasePerformanceData.setEndDate(localTimestamp2.getTime());
        localArrayList1.add(localCasePerformanceData);
      }
      paramHashMap.put("SUMMARY_LIST", localArrayList1);
    }
    catch (Exception localException)
    {
      throw new Exception(localException.getMessage());
    }
  }
  
  private void jdMethod_for(ResultSet paramResultSet, HashMap paramHashMap, int paramInt1, int paramInt2, CasePerformanceData paramCasePerformanceData, Timestamp paramTimestamp1, Timestamp paramTimestamp2)
    throws Exception
  {
    CasePerformanceData localCasePerformanceData = null;
    ArrayList localArrayList = null;
    Iterator localIterator = null;
    int i = 0;
    int j = 0;
    int k = 0;
    Integer localInteger1;
    int n;
    Object localObject1;
    Object localObject2;
    Integer localInteger6;
    switch (paramInt1)
    {
    case 16: 
      while (paramResultSet.next())
      {
        localInteger1 = new Integer(paramResultSet.getInt(1));
        localArrayList = (ArrayList)paramHashMap.get(localInteger1);
        localCasePerformanceData = (CasePerformanceData)localArrayList.get(paramInt2);
        j = paramResultSet.getInt(2);
        localCasePerformanceData.setNewCasesCount(j);
        i += j;
        localArrayList.set(paramInt2, localCasePerformanceData);
      }
      paramCasePerformanceData.setNewCasesCount(i);
      break;
    case 1: 
      while (paramResultSet.next())
      {
        localInteger1 = new Integer(paramResultSet.getInt(1));
        localArrayList = (ArrayList)paramHashMap.get(localInteger1);
        localCasePerformanceData = (CasePerformanceData)localArrayList.get(paramInt2);
        j = paramResultSet.getInt(2);
        localCasePerformanceData.setOpenedCasesCount(j);
        i += j;
        localArrayList.set(paramInt2, localCasePerformanceData);
      }
      paramCasePerformanceData.setOpenedCasesCount(i);
      break;
    case 17: 
      while (paramResultSet.next())
      {
        localInteger1 = new Integer(paramResultSet.getInt(1));
        localArrayList = (ArrayList)paramHashMap.get(localInteger1);
        localCasePerformanceData = (CasePerformanceData)localArrayList.get(paramInt2);
        j = paramResultSet.getInt(2);
        localCasePerformanceData.setOpenCasesCount(j);
        i += j;
        localArrayList.set(paramInt2, localCasePerformanceData);
      }
      paramCasePerformanceData.setOpenCasesCount(i);
      break;
    case 2: 
      k = 0;
      int m = 0;
      while (paramResultSet.next())
      {
        Integer localInteger2 = new Integer(paramResultSet.getInt(1));
        localArrayList = (ArrayList)paramHashMap.get(localInteger2);
        localCasePerformanceData = (CasePerformanceData)localArrayList.get(paramInt2);
        m += paramResultSet.getInt(2);
        k++;
        localCasePerformanceData.setTimeToOpenAvg(paramResultSet.getInt(2));
        localArrayList.set(paramInt2, localCasePerformanceData);
      }
      if (k != 0)
      {
        n = m / k;
        paramCasePerformanceData.setTimeToOpenAvg(n);
      }
      break;
    case 3: 
      n = 0;
      int i1 = 0;
      int i2 = 0;
      while (paramResultSet.next())
      {
        localObject1 = new Integer(paramResultSet.getInt(1));
        localArrayList = (ArrayList)paramHashMap.get(localObject1);
        localCasePerformanceData = (CasePerformanceData)localArrayList.get(paramInt2);
        int i3 = paramResultSet.getInt(2);
        int i4 = paramResultSet.getInt(3);
        if (n == 0)
        {
          n = 1;
          i1 = i3;
          i2 = i4;
        }
        else
        {
          if (i3 < i1) {
            i1 = i3;
          }
          if (i4 > i2) {
            i2 = i4;
          }
        }
        localCasePerformanceData.setTimeToOpenMin(i3);
        localCasePerformanceData.setTimeToOpenMax(i4);
        localArrayList.set(paramInt2, localCasePerformanceData);
      }
      paramCasePerformanceData.setTimeToOpenMin(i1);
      paramCasePerformanceData.setTimeToOpenMax(i2);
      break;
    case 4: 
      localObject1 = new HashMap();
      e locale1 = new e(paramCasePerformanceData.getTimeToOpenAvg());
      Object localObject3;
      while (paramResultSet.next())
      {
        localObject2 = new Integer(paramResultSet.getInt(1));
        localObject3 = (e)((HashMap)localObject1).get(localObject2);
        if (localObject3 == null)
        {
          localArrayList = (ArrayList)paramHashMap.get(localObject2);
          localCasePerformanceData = (CasePerformanceData)localArrayList.get(paramInt2);
          localObject3 = new e(localCasePerformanceData.getTimeToOpenAvg());
          ((HashMap)localObject1).put(localObject2, localObject3);
        }
        int i8 = (int)((paramResultSet.getTimestamp(2).getTime() - paramResultSet.getTimestamp(3).getTime()) / 1000L);
        ((e)localObject3).a(i8);
        locale1.a(i8);
      }
      localObject2 = ((HashMap)localObject1).keySet().iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject3 = ((Iterator)localObject2).next();
        localArrayList = (ArrayList)paramHashMap.get(localObject3);
        localCasePerformanceData = (CasePerformanceData)localArrayList.get(paramInt2);
        e locale2 = (e)((HashMap)localObject1).get(localObject3);
        localCasePerformanceData.setTimeToOpenStdDev(locale2.a());
      }
      paramCasePerformanceData.setTimeToOpenStdDev(locale1.a());
      break;
    case 5: 
      while (paramResultSet.next())
      {
        localObject2 = new Integer(paramResultSet.getInt(1));
        localArrayList = (ArrayList)paramHashMap.get(localObject2);
        localCasePerformanceData = (CasePerformanceData)localArrayList.get(paramInt2);
        j = paramResultSet.getInt(2);
        localCasePerformanceData.setResInProgSingleCount(j);
        i += j;
        localArrayList.set(paramInt2, localCasePerformanceData);
      }
      paramCasePerformanceData.setResInProgSingleCount(i);
      break;
    case 6: 
      int i5 = 0;
      k = 0;
      localIterator = paramHashMap.values().iterator();
      int i6;
      while (localIterator.hasNext())
      {
        localArrayList = (ArrayList)localIterator.next();
        if (localArrayList != null)
        {
          localCasePerformanceData = (CasePerformanceData)localArrayList.get(paramInt2);
          i6 = 0;
          if (localCasePerformanceData.getOpenCasesCount() != 0)
          {
            i6 = (int)(100.0D * localCasePerformanceData.getResInProgSingleCount() / localCasePerformanceData.getOpenCasesCount());
            k += localCasePerformanceData.getOpenCasesCount();
            i5 += localCasePerformanceData.getResInProgSingleCount();
          }
          localCasePerformanceData.setResInProgSinglePct(i6);
          localArrayList.set(paramInt2, localCasePerformanceData);
        }
      }
      if (k != 0)
      {
        i6 = (int)(100.0D * i5 / k);
        paramCasePerformanceData.setResInProgSinglePct(i6);
      }
      break;
    case 7: 
      while (paramResultSet.next())
      {
        Integer localInteger3 = new Integer(paramResultSet.getInt(1));
        localArrayList = (ArrayList)paramHashMap.get(localInteger3);
        localCasePerformanceData = (CasePerformanceData)localArrayList.get(paramInt2);
        j = paramResultSet.getInt(2);
        localCasePerformanceData.setResClosedSingleCount(j);
        i += j;
        localArrayList.set(paramInt2, localCasePerformanceData);
      }
      paramCasePerformanceData.setResClosedSingleCount(i);
      break;
    case 8: 
      int i7 = 0;
      k = 0;
      localIterator = paramHashMap.values().iterator();
      int i9;
      while (localIterator.hasNext())
      {
        localArrayList = (ArrayList)localIterator.next();
        if (localArrayList != null)
        {
          localCasePerformanceData = (CasePerformanceData)localArrayList.get(paramInt2);
          i9 = 0;
          if (localCasePerformanceData.getOpenCasesCount() != 0)
          {
            i9 = (int)(100.0D * localCasePerformanceData.getResClosedSingleCount() / localCasePerformanceData.getOpenCasesCount());
            k += localCasePerformanceData.getOpenCasesCount();
            i7 += localCasePerformanceData.getResClosedSingleCount();
          }
          localCasePerformanceData.setResClosedSinglePct(i9);
          localArrayList.set(paramInt2, localCasePerformanceData);
        }
      }
      if (k != 0)
      {
        i9 = (int)(100.0D * i7 / k);
        paramCasePerformanceData.setResClosedSinglePct(i9);
      }
      break;
    case 9: 
      while (paramResultSet.next())
      {
        Integer localInteger4 = new Integer(paramResultSet.getInt(1));
        localArrayList = (ArrayList)paramHashMap.get(localInteger4);
        localCasePerformanceData = (CasePerformanceData)localArrayList.get(paramInt2);
        j = paramResultSet.getInt(2);
        localCasePerformanceData.setResInProgHelpRqCount(j);
        i += j;
        localArrayList.set(paramInt2, localCasePerformanceData);
      }
      paramCasePerformanceData.setResInProgHelpRqCount(i);
      break;
    case 10: 
      int i10 = 0;
      k = 0;
      localIterator = paramHashMap.values().iterator();
      int i11;
      while (localIterator.hasNext())
      {
        localArrayList = (ArrayList)localIterator.next();
        if (localArrayList != null)
        {
          localCasePerformanceData = (CasePerformanceData)localArrayList.get(paramInt2);
          i11 = 0;
          if (localCasePerformanceData.getOpenCasesCount() != 0)
          {
            i11 = (int)(100.0D * localCasePerformanceData.getResInProgHelpRqCount() / localCasePerformanceData.getOpenCasesCount());
            k += localCasePerformanceData.getResInProgHelpRqCount();
            i += localCasePerformanceData.getOpenCasesCount();
          }
          localCasePerformanceData.setResInProgHelpRqPct(i11);
          localArrayList.set(paramInt2, localCasePerformanceData);
        }
      }
      if (k != 0)
      {
        i11 = (int)(100.0D * k / i);
        paramCasePerformanceData.setResInProgHelpRqPct(i11);
      }
      break;
    case 11: 
      while (paramResultSet.next())
      {
        Integer localInteger5 = new Integer(paramResultSet.getInt(1));
        localArrayList = (ArrayList)paramHashMap.get(localInteger5);
        localCasePerformanceData = (CasePerformanceData)localArrayList.get(paramInt2);
        j = paramResultSet.getInt(2) + localCasePerformanceData.getResClosedHelpRqCount();
        localCasePerformanceData.setResClosedHelpRqCount(j);
        i += paramResultSet.getInt(2);
      }
      paramCasePerformanceData.setResClosedHelpRqCount(i + paramCasePerformanceData.getResClosedHelpRqCount());
      break;
    case 12: 
      k = 0;
      int i12 = 0;
      localIterator = paramHashMap.values().iterator();
      int i13;
      while (localIterator.hasNext())
      {
        localArrayList = (ArrayList)localIterator.next();
        if (localArrayList != null)
        {
          localCasePerformanceData = (CasePerformanceData)localArrayList.get(paramInt2);
          i13 = 0;
          if (localCasePerformanceData.getOpenCasesCount() != 0)
          {
            i13 = (int)(100.0D * localCasePerformanceData.getResClosedHelpRqCount() / localCasePerformanceData.getOpenCasesCount());
            k += localCasePerformanceData.getResClosedHelpRqCount();
            i += localCasePerformanceData.getOpenCasesCount();
          }
          localCasePerformanceData.setResClosedHelpRqPct(i13);
        }
      }
      if (k != 0)
      {
        i13 = (int)(100.0D * k / i);
        paramCasePerformanceData.setResClosedHelpRqPct(i13);
      }
      break;
    case 21: 
      while (paramResultSet.next())
      {
        localInteger6 = new Integer(paramResultSet.getInt(1));
        localArrayList = (ArrayList)paramHashMap.get(localInteger6);
        localCasePerformanceData = (CasePerformanceData)localArrayList.get(paramInt2);
        j = localCasePerformanceData.getResHelpedCount() + paramResultSet.getInt(2);
        localCasePerformanceData.setResHelpedCount(j);
        i += paramResultSet.getInt(2);
      }
      paramCasePerformanceData.setResHelpedCount(i + paramCasePerformanceData.getResHelpedCount());
      break;
    case 13: 
      while (paramResultSet.next())
      {
        localInteger6 = new Integer(paramResultSet.getInt(1));
        localArrayList = (ArrayList)paramHashMap.get(localInteger6);
        localCasePerformanceData = (CasePerformanceData)localArrayList.get(paramInt2);
        j = localCasePerformanceData.getResHelpedAndClosedCount() + paramResultSet.getInt(2);
        localCasePerformanceData.setResHelpedAndClosedCount(j);
        i += paramResultSet.getInt(2);
      }
      paramCasePerformanceData.setResHelpedAndClosedCount(i + paramCasePerformanceData.getResHelpedAndClosedCount());
      break;
    case 14: 
      k = 0;
      localIterator = paramHashMap.values().iterator();
      int i14;
      while (localIterator.hasNext())
      {
        localArrayList = (ArrayList)localIterator.next();
        if (localArrayList != null)
        {
          localCasePerformanceData = (CasePerformanceData)localArrayList.get(paramInt2);
          i14 = 0;
          if (localCasePerformanceData.getResHelpedCount() != 0)
          {
            i14 = (int)(100.0D * localCasePerformanceData.getResHelpedAndClosedCount() / localCasePerformanceData.getResHelpedCount());
            k += localCasePerformanceData.getResHelpedCount();
            i += localCasePerformanceData.getResHelpedAndClosedCount();
          }
          localCasePerformanceData.setResHelpedAndClosedPct(i14);
        }
      }
      if (paramCasePerformanceData.getResHelpedCount() != 0)
      {
        i14 = (int)(100.0D * i / k);
        paramCasePerformanceData.setResHelpedAndClosedPct(i14);
      }
      break;
    }
  }
  
  private CasePerformanceData jdMethod_for(ArrayList paramArrayList, Connection paramConnection, Timestamp paramTimestamp1, Timestamp paramTimestamp2, Integer paramInteger1, Integer paramInteger2, Integer[] paramArrayOfInteger, PreparedStatement paramPreparedStatement1, PreparedStatement paramPreparedStatement2, PreparedStatement paramPreparedStatement3, PreparedStatement paramPreparedStatement4, PreparedStatement paramPreparedStatement5)
    throws Exception
  {
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    float f1 = 0.0F;
    int i5 = 0;
    float f2 = 0.0F;
    int i6 = 0;
    float f3 = 0.0F;
    int i7 = 0;
    float f4 = 0.0F;
    int i8 = 0;
    float f5 = 0.0F;
    int i9 = 0;
    CasePerformanceData localCasePerformanceData1 = null;
    CasePerformanceData localCasePerformanceData2 = new CasePerformanceData();
    for (int i10 = 0; i10 < paramArrayList.size(); i10++)
    {
      localCasePerformanceData1 = (CasePerformanceData)paramArrayList.get(i10);
      i5 += localCasePerformanceData1.getResClosedSingleCount();
      i7 += localCasePerformanceData1.getResClosedHelpRqCount();
      i8 += localCasePerformanceData1.getResHelpedAndClosedCount();
      j += localCasePerformanceData1.getNewCasesCount();
      k += localCasePerformanceData1.getOpenedCasesCount();
      n += localCasePerformanceData1.getTimeToOpenAvg() * localCasePerformanceData1.getOpenedCasesCount();
      if (i10 == 0) {
        i2 = localCasePerformanceData1.getTimeToOpenMin();
      }
      if (localCasePerformanceData1.getTimeToOpenMin() < i2) {
        i2 = localCasePerformanceData1.getTimeToOpenMin();
      }
      if (localCasePerformanceData1.getTimeToOpenMax() > i3) {
        i3 = localCasePerformanceData1.getTimeToOpenMax();
      }
    }
    if (k != 0) {
      i1 = n / k;
    }
    ResultSet localResultSet1;
    if (this.amx)
    {
      i10 = paramInteger2 == null ? 13 : 14;
      paramPreparedStatement2.setInt(i10, paramInteger1.intValue());
      localResultSet1 = jdMethod_for(paramConnection, paramPreparedStatement2, 18, paramInteger2, paramTimestamp1, paramTimestamp2);
      if (localResultSet1.next()) {
        m = localResultSet1.getInt(1);
      } else {
        m = 0;
      }
      DBUtil.closeResultSet(localResultSet1);
    }
    if (this.amc)
    {
      i10 = paramInteger2 == null ? 11 : 12;
      paramPreparedStatement3.setInt(i10, paramInteger1.intValue());
      localResultSet1 = jdMethod_for(paramConnection, paramPreparedStatement3, 19, paramInteger2, paramTimestamp1, paramTimestamp2);
      if (localResultSet1.next()) {
        i4 = localResultSet1.getInt(1);
      } else {
        i4 = 0;
      }
      DBUtil.closeResultSet(localResultSet1);
    }
    if (this.aon)
    {
      i10 = paramInteger2 == null ? 11 : 12;
      paramPreparedStatement4.setInt(i10, paramInteger1.intValue());
      localResultSet1 = jdMethod_for(paramConnection, paramPreparedStatement4, 20, paramInteger2, paramTimestamp1, paramTimestamp2);
      if (localResultSet1.next()) {
        i6 = localResultSet1.getInt(1);
      } else {
        i6 = 0;
      }
      DBUtil.closeResultSet(localResultSet1);
    }
    if (this.anW)
    {
      i10 = 6;
      paramPreparedStatement5.setInt(i10, paramInteger1.intValue());
      for (int i11 = 0; i11 < paramArrayOfInteger.length; i11++)
      {
        ResultSet localResultSet3 = jdMethod_for(paramConnection, paramPreparedStatement5, 22, paramArrayOfInteger[i11], paramTimestamp1, paramTimestamp2);
        if (localResultSet3.next()) {
          i9 += localResultSet3.getInt(1);
        } else {
          i9 += 0;
        }
        DBUtil.closeResultSet(localResultSet3);
      }
    }
    e locale = new e(i1);
    ResultSet localResultSet2;
    int i12;
    if (paramInteger2 != null)
    {
      if (this.aoh)
      {
        paramPreparedStatement1.setInt(4, paramInteger1.intValue());
        localResultSet2 = jdMethod_for(paramConnection, paramPreparedStatement1, 15, paramInteger2, paramTimestamp1, paramTimestamp2);
        while (localResultSet2.next())
        {
          i12 = (int)((localResultSet2.getTimestamp(1).getTime() - localResultSet2.getTimestamp(2).getTime()) / 1000L);
          locale.a(i12);
        }
        DBUtil.closeResultSet(localResultSet2);
      }
    }
    else if (this.aoh)
    {
      paramPreparedStatement1.setInt(3, paramInteger1.intValue());
      localResultSet2 = jdMethod_for(paramConnection, paramPreparedStatement1, 15, null, paramTimestamp1, paramTimestamp2);
      while (localResultSet2.next())
      {
        i12 = (int)((localResultSet2.getTimestamp(1).getTime() - localResultSet2.getTimestamp(2).getTime()) / 1000L);
        locale.a(i12);
      }
      DBUtil.closeResultSet(localResultSet2);
    }
    if (m != 0)
    {
      f1 = (int)(100.0D * i4 / m);
      f2 = (int)(100.0D * i5 / m);
      f3 = (int)(100.0D * i6 / m);
      f4 = (int)(100.0D * i7 / m);
    }
    if (i9 != 0) {
      f5 = (int)(100.0D * i8 / i9);
    }
    localCasePerformanceData2.setStartDate(null);
    localCasePerformanceData2.setEndDate(null);
    localCasePerformanceData2.setNewCasesCount(j);
    localCasePerformanceData2.setOpenedCasesCount(k);
    localCasePerformanceData2.setOpenCasesCount(m);
    localCasePerformanceData2.setResInProgSingleCount(i4);
    localCasePerformanceData2.setResClosedSingleCount(i5);
    localCasePerformanceData2.setResInProgHelpRqCount(i6);
    localCasePerformanceData2.setResClosedHelpRqCount(i7);
    localCasePerformanceData2.setResHelpedAndClosedCount(i8);
    localCasePerformanceData2.setResHelpedCount(i9);
    localCasePerformanceData2.setResInProgSinglePct(f1);
    localCasePerformanceData2.setResClosedSinglePct(f2);
    localCasePerformanceData2.setResInProgHelpRqPct(f3);
    localCasePerformanceData2.setResClosedHelpRqPct(f4);
    localCasePerformanceData2.setResHelpedAndClosedPct(f5);
    localCasePerformanceData2.setTimeToOpenAvg(i1);
    localCasePerformanceData2.setTimeToOpenMin(i2);
    localCasePerformanceData2.setTimeToOpenMax(i3);
    localCasePerformanceData2.setTimeToOpenStdDev(locale.a());
    return localCasePerformanceData2;
  }
  
  private int jdMethod_for(ReportResult paramReportResult, ArrayList paramArrayList, int paramInt, boolean paramBoolean, String paramString)
    throws Exception
  {
    int i = 0;
    Locale localLocale = paramReportResult.getLocale();
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    ReportHeading localReportHeading = new ReportHeading(localLocale);
    if (paramBoolean) {
      localReportHeading.setLabel(ReportConsts.getText(2218, localLocale));
    } else {
      localReportHeading.setLabel(paramString);
    }
    localReportResult.setSectionHeading(localReportHeading);
    jdMethod_for(localReportResult, paramInt, localLocale);
    Object localObject1;
    Object localObject2;
    Object localObject3;
    if (this.akT)
    {
      localObject1 = null;
      localObject2 = null;
      ReportDataItem localReportDataItem1 = null;
      localObject1 = new ReportRow(localLocale);
      localObject2 = new ReportDataItems(localLocale);
      localReportDataItem1 = ((ReportDataItems)localObject2).add();
      localReportDataItem1.setData(ReportConsts.getText(2221, localLocale));
      for (int k = 0; k < paramArrayList.size(); k++)
      {
        localObject3 = (CasePerformanceData)paramArrayList.get(k);
        localReportDataItem1 = ((ReportDataItems)localObject2).add();
        localReportDataItem1.setData(Integer.toString(((CasePerformanceData)localObject3).getNewCasesCount()));
      }
      if (paramBoolean)
      {
        localReportDataItem1 = ((ReportDataItems)localObject2).add();
        localReportDataItem1 = null;
      }
      ((ReportRow)localObject1).setDataItems((ReportDataItems)localObject2);
      localReportResult.addRow((ReportRow)localObject1);
      i += 1;
      localObject1 = new ReportRow(localLocale);
      ((ReportRow)localObject1).set("CELLBACKGROUND", "reportDataShaded");
      localObject2 = new ReportDataItems(localLocale);
      localReportDataItem1 = ((ReportDataItems)localObject2).add();
      localReportDataItem1.setData(ReportConsts.getText(2200, localLocale));
      for (k = 0; k < paramArrayList.size(); k++)
      {
        localObject3 = (CasePerformanceData)paramArrayList.get(k);
        localReportDataItem1 = ((ReportDataItems)localObject2).add();
        localReportDataItem1.setData(String.valueOf(((CasePerformanceData)localObject3).getOpenedCasesCount()));
      }
      if (paramBoolean)
      {
        localReportDataItem1 = ((ReportDataItems)localObject2).add();
        localReportDataItem1 = null;
      }
      ((ReportRow)localObject1).setDataItems((ReportDataItems)localObject2);
      localReportResult.addRow((ReportRow)localObject1);
      i++;
      localObject1 = new ReportRow(localLocale);
      localObject2 = new ReportDataItems(localLocale);
      localReportDataItem1 = ((ReportDataItems)localObject2).add();
      localReportDataItem1.setData(ReportConsts.getText(2222, localLocale));
      for (k = 0; k < paramArrayList.size(); k++)
      {
        localObject3 = (CasePerformanceData)paramArrayList.get(k);
        localReportDataItem1 = ((ReportDataItems)localObject2).add();
        localReportDataItem1.setData(Integer.toString(((CasePerformanceData)localObject3).getOpenCasesCount()));
      }
      if (paramBoolean)
      {
        localReportDataItem1 = ((ReportDataItems)localObject2).add();
        localReportDataItem1 = null;
      }
      ((ReportRow)localObject1).setDataItems((ReportDataItems)localObject2);
      localReportResult.addRow((ReportRow)localObject1);
      i += 1;
    }
    int j;
    ReportDataItems localReportDataItems;
    ReportDataItem localReportDataItem2;
    int m;
    CasePerformanceData localCasePerformanceData;
    if ((this.alX) || (this.alI) || (this.aoh))
    {
      localObject1 = new ReportResult(localLocale);
      paramReportResult.addSubReport((ReportResult)localObject1);
      localObject2 = new ReportHeading(localLocale);
      ((ReportHeading)localObject2).setLabel(ReportConsts.getText(2201, localLocale));
      ((ReportResult)localObject1).setSectionHeading((ReportHeading)localObject2);
      jdMethod_for((ReportResult)localObject1, paramInt, localLocale);
      j = 0;
      if (this.alX)
      {
        localObject3 = new ReportRow(localLocale);
        localReportDataItems = new ReportDataItems(localLocale);
        localReportDataItem2 = localReportDataItems.add();
        localReportDataItem2.setData(ReportConsts.getText(2202, localLocale));
        for (m = 0; m < paramArrayList.size(); m++)
        {
          localCasePerformanceData = (CasePerformanceData)paramArrayList.get(m);
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2.setData(e(localCasePerformanceData.getTimeToOpenAvg()));
        }
        if (paramBoolean)
        {
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2 = null;
        }
        ((ReportRow)localObject3).setDataItems(localReportDataItems);
        ((ReportResult)localObject1).addRow((ReportRow)localObject3);
        i++;
        j++;
      }
      if (this.alI)
      {
        localObject3 = new ReportRow(localLocale);
        if (j % 2 == 1) {
          ((ReportRow)localObject3).set("CELLBACKGROUND", "reportDataShaded");
        }
        localReportDataItems = new ReportDataItems(localLocale);
        localReportDataItem2 = localReportDataItems.add();
        localReportDataItem2.setData(ReportConsts.getText(2203, localLocale));
        for (m = 0; m < paramArrayList.size(); m++)
        {
          localCasePerformanceData = (CasePerformanceData)paramArrayList.get(m);
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2.setData(e(localCasePerformanceData.getTimeToOpenMin()) + " " + ReportConsts.getText(2220, localLocale) + " " + e(localCasePerformanceData.getTimeToOpenMax()));
        }
        if (paramBoolean)
        {
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2 = null;
        }
        ((ReportRow)localObject3).setDataItems(localReportDataItems);
        ((ReportResult)localObject1).addRow((ReportRow)localObject3);
        i++;
        j++;
      }
      if (this.aoh)
      {
        localObject3 = new ReportRow(localLocale);
        if (j % 2 == 1) {
          ((ReportRow)localObject3).set("CELLBACKGROUND", "reportDataShaded");
        }
        localReportDataItems = new ReportDataItems(localLocale);
        localReportDataItem2 = localReportDataItems.add();
        localReportDataItem2.setData(ReportConsts.getText(2204, localLocale));
        for (m = 0; m < paramArrayList.size(); m++)
        {
          localCasePerformanceData = (CasePerformanceData)paramArrayList.get(m);
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2.setData(e(localCasePerformanceData.getTimeToOpenStdDev()));
        }
        if (paramBoolean)
        {
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2 = null;
        }
        ((ReportRow)localObject3).setDataItems(localReportDataItems);
        ((ReportResult)localObject1).addRow((ReportRow)localObject3);
        i++;
        j++;
      }
    }
    if ((this.akw) || (this.al8) || (this.an4) || (this.akz) || (this.amD) || (this.ang) || (this.amZ) || (this.amo) || (this.anT) || (this.alb))
    {
      localObject1 = new ReportResult(localLocale);
      paramReportResult.addSubReport((ReportResult)localObject1);
      localObject2 = new ReportHeading(localLocale);
      ((ReportHeading)localObject2).setLabel(ReportConsts.getText(2205, localLocale));
      ((ReportResult)localObject1).setSectionHeading((ReportHeading)localObject2);
      jdMethod_for((ReportResult)localObject1, paramInt, localLocale);
      j = 0;
      if (this.akw)
      {
        localObject3 = new ReportRow(localLocale);
        localReportDataItems = new ReportDataItems(localLocale);
        localReportDataItem2 = localReportDataItems.add();
        localReportDataItem2.setData(ReportConsts.getText(2206, localLocale));
        for (m = 0; m < paramArrayList.size(); m++)
        {
          localCasePerformanceData = (CasePerformanceData)paramArrayList.get(m);
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2.setData(String.valueOf(localCasePerformanceData.getResInProgSingleCount()));
        }
        if (paramBoolean)
        {
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2 = null;
        }
        ((ReportRow)localObject3).setDataItems(localReportDataItems);
        ((ReportResult)localObject1).addRow((ReportRow)localObject3);
        i++;
        j++;
      }
      if (this.al8)
      {
        localObject3 = new ReportRow(localLocale);
        if (j % 2 == 1) {
          ((ReportRow)localObject3).set("CELLBACKGROUND", "reportDataShaded");
        }
        localReportDataItems = new ReportDataItems(localLocale);
        localReportDataItem2 = localReportDataItems.add();
        localReportDataItem2.setData(ReportConsts.getText(2207, localLocale));
        for (m = 0; m < paramArrayList.size(); m++)
        {
          localCasePerformanceData = (CasePerformanceData)paramArrayList.get(m);
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2.setData(localCasePerformanceData.getResInProgSinglePct() + "%");
        }
        if (paramBoolean)
        {
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2 = null;
        }
        ((ReportRow)localObject3).setDataItems(localReportDataItems);
        ((ReportResult)localObject1).addRow((ReportRow)localObject3);
        i++;
        j++;
      }
      if (this.an4)
      {
        localObject3 = new ReportRow(localLocale);
        if (j % 2 == 1) {
          ((ReportRow)localObject3).set("CELLBACKGROUND", "reportDataShaded");
        }
        localReportDataItems = new ReportDataItems(localLocale);
        localReportDataItem2 = localReportDataItems.add();
        localReportDataItem2.setData(ReportConsts.getText(2208, localLocale));
        for (m = 0; m < paramArrayList.size(); m++)
        {
          localCasePerformanceData = (CasePerformanceData)paramArrayList.get(m);
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2.setData(String.valueOf(localCasePerformanceData.getResClosedSingleCount()));
        }
        if (paramBoolean)
        {
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2 = null;
        }
        ((ReportRow)localObject3).setDataItems(localReportDataItems);
        ((ReportResult)localObject1).addRow((ReportRow)localObject3);
        i++;
        j++;
      }
      if (this.akz)
      {
        localObject3 = new ReportRow(localLocale);
        if (j % 2 == 1) {
          ((ReportRow)localObject3).set("CELLBACKGROUND", "reportDataShaded");
        }
        localReportDataItems = new ReportDataItems(localLocale);
        localReportDataItem2 = localReportDataItems.add();
        localReportDataItem2.setData(ReportConsts.getText(2209, localLocale));
        for (m = 0; m < paramArrayList.size(); m++)
        {
          localCasePerformanceData = (CasePerformanceData)paramArrayList.get(m);
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2.setData(localCasePerformanceData.getResClosedSinglePct() + "%");
        }
        if (paramBoolean)
        {
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2 = null;
        }
        ((ReportRow)localObject3).setDataItems(localReportDataItems);
        ((ReportResult)localObject1).addRow((ReportRow)localObject3);
        i++;
        j++;
      }
      if (this.amD)
      {
        localObject3 = new ReportRow(localLocale);
        if (j % 2 == 1) {
          ((ReportRow)localObject3).set("CELLBACKGROUND", "reportDataShaded");
        }
        localReportDataItems = new ReportDataItems(localLocale);
        localReportDataItem2 = localReportDataItems.add();
        localReportDataItem2.setData(ReportConsts.getText(2210, localLocale));
        for (m = 0; m < paramArrayList.size(); m++)
        {
          localCasePerformanceData = (CasePerformanceData)paramArrayList.get(m);
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2.setData(String.valueOf(localCasePerformanceData.getResInProgHelpRqCount()));
        }
        if (paramBoolean)
        {
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2 = null;
        }
        ((ReportRow)localObject3).setDataItems(localReportDataItems);
        ((ReportResult)localObject1).addRow((ReportRow)localObject3);
        i++;
        j++;
      }
      if (this.ang)
      {
        localObject3 = new ReportRow(localLocale);
        if (j % 2 == 1) {
          ((ReportRow)localObject3).set("CELLBACKGROUND", "reportDataShaded");
        }
        localReportDataItems = new ReportDataItems(localLocale);
        localReportDataItem2 = localReportDataItems.add();
        localReportDataItem2.setData(ReportConsts.getText(2211, localLocale));
        for (m = 0; m < paramArrayList.size(); m++)
        {
          localCasePerformanceData = (CasePerformanceData)paramArrayList.get(m);
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2.setData(localCasePerformanceData.getResInProgHelpRqPct() + "%");
        }
        if (paramBoolean)
        {
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2 = null;
        }
        ((ReportRow)localObject3).setDataItems(localReportDataItems);
        ((ReportResult)localObject1).addRow((ReportRow)localObject3);
        i++;
        j++;
      }
      if (this.amZ)
      {
        localObject3 = new ReportRow(localLocale);
        if (j % 2 == 1) {
          ((ReportRow)localObject3).set("CELLBACKGROUND", "reportDataShaded");
        }
        localReportDataItems = new ReportDataItems(localLocale);
        localReportDataItem2 = localReportDataItems.add();
        localReportDataItem2.setData(ReportConsts.getText(2212, localLocale));
        for (m = 0; m < paramArrayList.size(); m++)
        {
          localCasePerformanceData = (CasePerformanceData)paramArrayList.get(m);
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2.setData(String.valueOf(localCasePerformanceData.getResClosedHelpRqCount()));
        }
        if (paramBoolean)
        {
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2 = null;
        }
        ((ReportRow)localObject3).setDataItems(localReportDataItems);
        ((ReportResult)localObject1).addRow((ReportRow)localObject3);
        i++;
        j++;
      }
      if (this.amo)
      {
        localObject3 = new ReportRow(localLocale);
        if (j % 2 == 1) {
          ((ReportRow)localObject3).set("CELLBACKGROUND", "reportDataShaded");
        }
        localReportDataItems = new ReportDataItems(localLocale);
        localReportDataItem2 = localReportDataItems.add();
        localReportDataItem2.setData(ReportConsts.getText(2213, localLocale));
        for (m = 0; m < paramArrayList.size(); m++)
        {
          localCasePerformanceData = (CasePerformanceData)paramArrayList.get(m);
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2.setData(localCasePerformanceData.getResClosedHelpRqPct() + "%");
        }
        if (paramBoolean)
        {
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2 = null;
        }
        ((ReportRow)localObject3).setDataItems(localReportDataItems);
        ((ReportResult)localObject1).addRow((ReportRow)localObject3);
        i++;
        j++;
      }
      if (this.anT)
      {
        localObject3 = null;
        localReportDataItems = null;
        localReportDataItem2 = null;
        localCasePerformanceData = null;
        localObject3 = new ReportRow(localLocale);
        if (j % 2 == 1) {
          ((ReportRow)localObject3).set("CELLBACKGROUND", "reportDataShaded");
        }
        localReportDataItems = new ReportDataItems(localLocale);
        localReportDataItem2 = localReportDataItems.add();
        localReportDataItem2.setData(ReportConsts.getText(2223, localLocale));
        for (m = 0; m < paramArrayList.size(); m++)
        {
          localCasePerformanceData = (CasePerformanceData)paramArrayList.get(m);
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2.setData(Integer.toString(localCasePerformanceData.getResHelpedCount()));
        }
        if (paramBoolean)
        {
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2 = null;
        }
        ((ReportRow)localObject3).setDataItems(localReportDataItems);
        ((ReportResult)localObject1).addRow((ReportRow)localObject3);
        i += 1;
        j++;
        localObject3 = new ReportRow(localLocale);
        if (j % 2 == 1) {
          ((ReportRow)localObject3).set("CELLBACKGROUND", "reportDataShaded");
        }
        localReportDataItems = new ReportDataItems(localLocale);
        localReportDataItem2 = localReportDataItems.add();
        localReportDataItem2.setData(ReportConsts.getText(2214, localLocale));
        for (m = 0; m < paramArrayList.size(); m++)
        {
          localCasePerformanceData = (CasePerformanceData)paramArrayList.get(m);
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2.setData(String.valueOf(localCasePerformanceData.getResHelpedAndClosedCount()));
        }
        if (paramBoolean)
        {
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2 = null;
        }
        ((ReportRow)localObject3).setDataItems(localReportDataItems);
        ((ReportResult)localObject1).addRow((ReportRow)localObject3);
        i++;
        j++;
      }
      if (this.alb)
      {
        localObject3 = new ReportRow(localLocale);
        if (j % 2 == 1) {
          ((ReportRow)localObject3).set("CELLBACKGROUND", "reportDataShaded");
        }
        localReportDataItems = new ReportDataItems(localLocale);
        localReportDataItem2 = localReportDataItems.add();
        localReportDataItem2.setData(ReportConsts.getText(2215, localLocale));
        for (m = 0; m < paramArrayList.size(); m++)
        {
          localCasePerformanceData = (CasePerformanceData)paramArrayList.get(m);
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2.setData(localCasePerformanceData.getResHelpedAndClosedPct() + "%");
        }
        if (paramBoolean)
        {
          localReportDataItem2 = localReportDataItems.add();
          localReportDataItem2 = null;
        }
        ((ReportRow)localObject3).setDataItems(localReportDataItems);
        ((ReportResult)localObject1).addRow((ReportRow)localObject3);
        i++;
        j++;
      }
    }
    return i;
  }
  
  private static String e(int paramInt)
  {
    int i = paramInt / 60;
    int j = i % 60;
    String str = "";
    if (j < 10) {
      str = "0" + Integer.toString(j);
    } else {
      str = Integer.toString(j);
    }
    return Integer.toString(i / 60) + ":" + str;
  }
  
  protected void retrieveMessageFiltersRecipients(Connection paramConnection, GlobalMessage paramGlobalMessage, Locale paramLocale)
    throws Exception
  {
    jdMethod_try(paramConnection, paramGlobalMessage);
    jdMethod_for(paramConnection, paramGlobalMessage, paramLocale);
    paramGlobalMessage.put("loaded", new Boolean(true));
  }
  
  private static void jdMethod_try(Connection paramConnection, GlobalMessage paramGlobalMessage)
    throws Exception
  {
    int i = 1;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "SELECT FILTER_TYPE, FILTER_VALUE FROM GLOBAL_MSG_FILTERS WHERE GLOBAL_MSG_ID = ?");
      localPreparedStatement.setInt(i++, paramGlobalMessage.getGlobalMsgIDValue());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT FILTER_TYPE, FILTER_VALUE FROM GLOBAL_MSG_FILTERS WHERE GLOBAL_MSG_ID = ?");
      GlobalMessageFilters localGlobalMessageFilters = null;
      while (localResultSet.next())
      {
        if (localGlobalMessageFilters == null) {
          localGlobalMessageFilters = new GlobalMessageFilters();
        }
        localGlobalMessageFilters.add(new GlobalMessageFilter(localResultSet.getInt(1), localResultSet.getString(2)));
      }
      if (localGlobalMessageFilters != null) {
        paramGlobalMessage.put("_filters", localGlobalMessageFilters);
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  private static void jdMethod_for(Connection paramConnection, GlobalMessage paramGlobalMessage, Locale paramLocale)
    throws Exception
  {
    int i = 1;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "SELECT RCPT.RECIPIENT_LIST_ID, RCPT.RECIPIENT_TYPE, RCPT.DIRECTORY_ID, CUSTDIR.cust_id, BUS.business_name, BUS.affiliate_bank_id FROM GLOBAL_MSG_RCPTS RCPT, customer_directory CUSTDIR, business BUS  WHERE RCPT.RECIPIENT_LIST_ID = ? AND RECIPIENT_TYPE = 2 AND (CUSTDIR.directory_id = RCPT.DIRECTORY_ID AND BUS.directory_id = RCPT.DIRECTORY_ID)");
      localPreparedStatement.setInt(i++, paramGlobalMessage.getToGroupIDValue());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT RCPT.RECIPIENT_LIST_ID, RCPT.RECIPIENT_TYPE, RCPT.DIRECTORY_ID, CUSTDIR.cust_id, BUS.business_name, BUS.affiliate_bank_id FROM GLOBAL_MSG_RCPTS RCPT, customer_directory CUSTDIR, business BUS  WHERE RCPT.RECIPIENT_LIST_ID = ? AND RECIPIENT_TYPE = 2 AND (CUSTDIR.directory_id = RCPT.DIRECTORY_ID AND BUS.directory_id = RCPT.DIRECTORY_ID)");
      GlobalMessageRecipients localGlobalMessageRecipients = null;
      while (localResultSet.next())
      {
        if (localGlobalMessageRecipients == null) {
          localGlobalMessageRecipients = new GlobalMessageRecipients();
        }
        localGlobalMessageRecipients.add(new GlobalMessageRecipient(localResultSet.getInt(1), localResultSet.getInt(2), localResultSet.getInt(3), localResultSet.getString(4), localResultSet.getString(5), localResultSet.getInt(6)));
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      i = 1;
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, " SELECT RCPT.RECIPIENT_LIST_ID, RCPT.RECIPIENT_TYPE, RCPT.DIRECTORY_ID, CUSTDIR.cust_id, CUST.last_name, CUST.first_name, CUST.affiliate_bank_id, CUST.user_name   FROM GLOBAL_MSG_RCPTS RCPT, customer_directory CUSTDIR, customer CUST   WHERE RCPT.RECIPIENT_LIST_ID = ? AND RECIPIENT_TYPE = 1    AND (CUSTDIR.directory_id = RCPT.DIRECTORY_ID  AND CUST.directory_id = RCPT.DIRECTORY_ID)");
      localPreparedStatement.setInt(i++, paramGlobalMessage.getToGroupIDValue());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, " SELECT RCPT.RECIPIENT_LIST_ID, RCPT.RECIPIENT_TYPE, RCPT.DIRECTORY_ID, CUSTDIR.cust_id, CUST.last_name, CUST.first_name, CUST.affiliate_bank_id, CUST.user_name   FROM GLOBAL_MSG_RCPTS RCPT, customer_directory CUSTDIR, customer CUST   WHERE RCPT.RECIPIENT_LIST_ID = ? AND RECIPIENT_TYPE = 1    AND (CUSTDIR.directory_id = RCPT.DIRECTORY_ID  AND CUST.directory_id = RCPT.DIRECTORY_ID)");
      while (localResultSet.next())
      {
        if (localGlobalMessageRecipients == null) {
          localGlobalMessageRecipients = new GlobalMessageRecipients();
        }
        String str = UserUtil.getFullNameWithLoginId(localResultSet.getString(6), localResultSet.getString(5), localResultSet.getString(8), String.valueOf(localResultSet.getInt(3)), paramLocale);
        localGlobalMessageRecipients.add(new GlobalMessageRecipient(localResultSet.getInt(1), localResultSet.getInt(2), localResultSet.getInt(3), localResultSet.getString(4), str, localResultSet.getInt(7)));
      }
      if (localGlobalMessageRecipients != null) {
        paramGlobalMessage.put("_recipients", localGlobalMessageRecipients);
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  private static void jdMethod_int(Connection paramConnection, GlobalMessage paramGlobalMessage)
    throws Exception
  {
    jdMethod_byte(paramConnection, paramGlobalMessage);
    paramGlobalMessage.setToGroupID(jdMethod_char(paramConnection, paramGlobalMessage));
  }
  
  private static void jdMethod_byte(Connection paramConnection, GlobalMessage paramGlobalMessage)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      if (paramGlobalMessage.containsKey("_filters"))
      {
        jdMethod_new(paramConnection, paramGlobalMessage);
        GlobalMessageFilters localGlobalMessageFilters = (GlobalMessageFilters)paramGlobalMessage.get("_filters");
        GlobalMessageFilter localGlobalMessageFilter = null;
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "INSERT INTO GLOBAL_MSG_FILTERS (GLOBAL_MSG_ID, FILTER_TYPE, FILTER_VALUE) VALUES (?, ?, ?)");
        int i = localGlobalMessageFilters.size();
        int j = 1;
        for (int k = 0; k < i; k++)
        {
          j = 1;
          localGlobalMessageFilter = (GlobalMessageFilter)localGlobalMessageFilters.get(k);
          localPreparedStatement.setInt(j++, paramGlobalMessage.getGlobalMsgIDValue());
          localPreparedStatement.setInt(j++, localGlobalMessageFilter.getFilterType());
          localPreparedStatement.setString(j++, localGlobalMessageFilter.getFilterValue());
          localPreparedStatement.addBatch();
        }
        localPreparedStatement.executeBatch();
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private static int jdMethod_char(Connection paramConnection, GlobalMessage paramGlobalMessage)
    throws Exception
  {
    int i = paramGlobalMessage.getToGroupIDValue();
    PreparedStatement localPreparedStatement = null;
    try
    {
      if (paramGlobalMessage.containsKey("_recipients"))
      {
        jdMethod_for(paramConnection, paramGlobalMessage);
        GlobalMessageRecipients localGlobalMessageRecipients = (GlobalMessageRecipients)paramGlobalMessage.get("_recipients");
        GlobalMessageRecipient localGlobalMessageRecipient = null;
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "INSERT INTO GLOBAL_MSG_RCPTS (RECIPIENT_LIST_ID, RECIPIENT_TYPE, DIRECTORY_ID) VALUES(?, ?, ?)");
        int j = localGlobalMessageRecipients.size();
        int k = 1;
        i = DBUtil.getNextId(paramConnection, Profile.dbType, "RECIPIENT_LIST_ID");
        for (int m = 0; m < j; m++)
        {
          k = 1;
          localGlobalMessageRecipient = (GlobalMessageRecipient)localGlobalMessageRecipients.get(m);
          localPreparedStatement.setInt(k++, i);
          localPreparedStatement.setInt(k++, localGlobalMessageRecipient.getRcptType());
          localPreparedStatement.setInt(k++, localGlobalMessageRecipient.getRcptDirID());
          localPreparedStatement.addBatch();
        }
        localPreparedStatement.executeBatch();
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
    return i;
  }
  
  private static void jdMethod_new(Connection paramConnection, GlobalMessage paramGlobalMessage)
    throws Exception
  {
    int i = 1;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "DELETE FROM GLOBAL_MSG_FILTERS WHERE GLOBAL_MSG_ID = ?");
      localPreparedStatement.setInt(i++, paramGlobalMessage.getGlobalMsgIDValue());
      localPreparedStatement.executeUpdate();
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private static void jdMethod_for(Connection paramConnection, GlobalMessage paramGlobalMessage)
    throws Exception
  {
    int i = 1;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "DELETE FROM GLOBAL_MSG_RCPTS WHERE RECIPIENT_LIST_ID = ?");
      localPreparedStatement.setInt(i++, paramGlobalMessage.getToGroupIDValue());
      localPreparedStatement.executeUpdate();
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private static void jdMethod_case(Connection paramConnection, GlobalMessage paramGlobalMessage)
    throws Exception
  {
    jdMethod_byte(paramConnection, paramGlobalMessage);
    if (paramGlobalMessage.getToGroupTypeValue() == 4) {
      paramGlobalMessage.setToGroupID(jdMethod_char(paramConnection, paramGlobalMessage));
    }
  }
  
  public static ResultSetWrapper getSpecificCustomersOfGroupType(Connection paramConnection, GlobalMessage paramGlobalMessage)
    throws ProfileException
  {
    String str = "getSpecificCustomersOfGroupType(Connection conn, GlobalMessage globalMessage)";
    ResultSetWrapper localResultSetWrapper = null;
    if (!Profile.isValidId(paramGlobalMessage.getBankId())) {
      Profile.handleError(str, "Invalid Bank Id", 4);
    }
    if (paramConnection == null) {
      localResultSetWrapper = new ResultSetWrapper(Profile.poolName, paramGlobalMessage);
    } else {
      localResultSetWrapper = new ResultSetWrapper(paramConnection, paramGlobalMessage);
    }
    return localResultSetWrapper;
  }
  
  public int getMessageDirectoryID(int paramInt)
    throws Exception
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT m.directory_id, mi.item_id, mi.from_id, mi.message_type FROM message_item mi, message m WHERE m.message_id = mi.message_id AND mi.item_id IN ( SELECT MAX( mi.item_id ) FROM message_item mi WHERE mi.message_id = ? )");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT m.directory_id, mi.item_id, mi.from_id, mi.message_type FROM message_item mi, message m WHERE m.message_id = mi.message_id AND mi.item_id IN ( SELECT MAX( mi.item_id ) FROM message_item mi WHERE mi.message_id = ? )");
      if (localResultSet.next() == true)
      {
        i = localResultSet.getInt("directory_id");
      }
      else
      {
        DBUtil.rollback(localConnection);
        throw new Exception("Invalid ResultSet returned.");
      }
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    return i;
  }
  
  private StringBuffer jdMethod_for(GlobalMessages paramGlobalMessages)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    Iterator localIterator = paramGlobalMessages.iterator();
    while (localIterator.hasNext())
    {
      int j = ((GlobalMessage)localIterator.next()).getGlobalMsgIDValue();
      if (j != 0)
      {
        i = 1;
        if (localStringBuffer.length() != 0) {
          localStringBuffer.append(",");
        }
        localStringBuffer.append(j);
      }
    }
    if (i == 0) {
      return null;
    }
    return DBUtil.generateSQLNumericInClause(localStringBuffer.toString(), "global_msg_id");
  }
  
  private boolean jdMethod_for(GlobalMessage paramGlobalMessage, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "MessageAdapter.HasValidMsgText";
    boolean bool = false;
    try
    {
      LanguageDefns localLanguageDefns = UtilHandler.getLanguageList(paramSecureUser, new HashMap());
      if (localLanguageDefns != null)
      {
        Iterator localIterator = localLanguageDefns.iterator();
        while (localIterator.hasNext())
        {
          LanguageDefn localLanguageDefn = (LanguageDefn)localIterator.next();
          String str2 = localLanguageDefn.getLanguage();
          String str3 = paramGlobalMessage.getMsgText(str2);
          if ((str3 != null) && (str3.trim().length() != 0))
          {
            bool = true;
            break;
          }
        }
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    return bool;
  }
  
  private Locale jdMethod_for(SecureUser paramSecureUser, HashMap paramHashMap)
    throws Exception
  {
    Locale localLocale;
    if (paramSecureUser.getUserType() == 1)
    {
      User localUser = CustomerAdapter.getUser(paramSecureUser);
      String str = localUser.getPreferredLanguage();
      localLocale = new Locale(str.substring(0, 2), str.substring(3, 5));
    }
    else
    {
      localLocale = paramSecureUser.getLocale();
    }
    return localLocale;
  }
  
  private static void jdMethod_for(GlobalMessage paramGlobalMessage, String paramString)
    throws ProfileException
  {
    if ((paramGlobalMessage.getToGroupName(paramString) == null) || (paramGlobalMessage.getToGroupName(paramString).length() == 0)) {
      throw new ProfileException(3820);
    }
    if ((paramGlobalMessage.getSubject(paramString) == null) || (paramGlobalMessage.getSubject(paramString).length() == 0)) {
      throw new ProfileException(3821);
    }
    if ((paramGlobalMessage.getMsgText(paramString) == null) || (paramGlobalMessage.getMsgText(paramString).length() == 0)) {
      throw new ProfileException(3822);
    }
  }
  
  private class c
  {
    protected int jdField_for = 0;
    protected int h = 0;
    protected int jdField_else = 0;
    protected MessageAdapter.d jdField_void = new MessageAdapter.d(MessageAdapter.this, 0, 0);
    protected int jdField_if = 0;
    protected int b = 0;
    protected MessageAdapter.e jdField_new = null;
    protected int jdField_try = 0;
    protected int c = 0;
    protected int a = 0;
    protected int jdField_byte = 0;
    protected int d = 0;
    protected int jdField_case = 0;
    protected int jdField_null = 0;
    protected int jdField_int = 0;
    protected int jdField_char = 0;
    protected int jdField_goto = 0;
    protected int g = 0;
    protected int jdField_do = 0;
    protected int jdField_long = 0;
    protected int e = 0;
    protected int f = 0;
    
    public c() {}
    
    public int jdField_goto()
    {
      return this.jdField_for;
    }
    
    public void jdField_new(int paramInt)
    {
      this.jdField_for += paramInt;
    }
    
    public int jdField_void()
    {
      if (this.jdField_else == 0) {
        return 0;
      }
      return this.h / this.jdField_else;
    }
    
    public void jdField_if(int paramInt)
    {
      this.h += paramInt;
      this.jdField_else += 1;
    }
    
    public MessageAdapter.d jdField_try()
    {
      return this.jdField_void;
    }
    
    public void a(MessageAdapter.d paramd)
    {
      this.jdField_void.a(paramd);
    }
    
    public void a(int paramInt)
    {
      this.jdField_if += paramInt;
      this.b += 1;
    }
    
    public int jdField_new()
    {
      if (this.b == 0) {
        return 0;
      }
      return this.jdField_if / this.b;
    }
    
    public int jdField_for()
    {
      if (this.jdField_new == null) {
        return 0;
      }
      return this.jdField_new.a();
    }
    
    public void jdField_byte(int paramInt)
    {
      if (this.jdField_new == null) {
        this.jdField_new = new MessageAdapter.e(MessageAdapter.this, jdField_new());
      }
      this.jdField_new.a(paramInt);
    }
    
    public int jdField_byte()
    {
      return this.jdField_try;
    }
    
    public void jdField_do(int paramInt)
    {
      this.jdField_try += paramInt;
    }
    
    public int jdField_else()
    {
      if (this.a == 0) {
        return 0;
      }
      return 100 * this.c / this.a;
    }
    
    public void jdField_do(int paramInt1, int paramInt2)
    {
      this.c += paramInt1;
      this.a += paramInt2;
    }
    
    public int a()
    {
      return this.jdField_byte;
    }
    
    public void jdField_for(int paramInt)
    {
      this.jdField_byte += paramInt;
    }
    
    public int jdField_null()
    {
      if (this.jdField_case == 0) {
        return 0;
      }
      return 100 * this.d / this.jdField_case;
    }
    
    public void a(int paramInt1, int paramInt2)
    {
      this.d += paramInt1;
      this.jdField_case += paramInt2;
    }
    
    public int jdField_int()
    {
      return this.jdField_null;
    }
    
    public void jdField_try(int paramInt)
    {
      this.jdField_null += paramInt;
    }
    
    public int jdField_if()
    {
      if (this.jdField_char == 0) {
        return 0;
      }
      return 100 * this.jdField_int / this.jdField_char;
    }
    
    public void jdField_int(int paramInt1, int paramInt2)
    {
      this.jdField_int += paramInt1;
      this.jdField_char += paramInt2;
    }
    
    public int jdField_char()
    {
      return this.jdField_goto;
    }
    
    public void jdField_int(int paramInt)
    {
      this.jdField_goto += paramInt;
    }
    
    public int jdField_do()
    {
      if (this.jdField_do == 0) {
        return 0;
      }
      return 100 * this.g / this.jdField_do;
    }
    
    public void jdField_if(int paramInt1, int paramInt2)
    {
      this.g += paramInt1;
      this.jdField_do += paramInt2;
    }
    
    public int jdField_long()
    {
      return this.jdField_long;
    }
    
    public void jdField_case(int paramInt)
    {
      this.jdField_long += paramInt;
    }
    
    public int jdField_case()
    {
      if (this.f == 0) {
        return 0;
      }
      return 100 * this.e / this.f;
    }
    
    public void jdField_for(int paramInt1, int paramInt2)
    {
      this.e += paramInt1;
      this.f += paramInt2;
    }
  }
  
  private class d
  {
    int jdField_if = 0;
    int jdField_for = 0;
    boolean a = false;
    boolean jdField_do = false;
    
    public d(int paramInt1, int paramInt2)
    {
      this.jdField_if = paramInt1;
      this.jdField_for = paramInt2;
      this.a = false;
      this.jdField_do = false;
    }
    
    public void a(d paramd)
    {
      if (!this.a)
      {
        this.jdField_if = paramd.jdField_if;
        this.a = true;
      }
      else if (paramd.jdField_if < this.jdField_if)
      {
        this.jdField_if = paramd.jdField_if;
      }
      if (!this.jdField_do)
      {
        this.jdField_for = paramd.jdField_for;
        this.jdField_do = true;
      }
      else if (paramd.jdField_for > this.jdField_for)
      {
        this.jdField_for = paramd.jdField_for;
      }
    }
    
    public String a(String paramString)
    {
      String str1 = String.valueOf(this.jdField_if);
      String str2 = String.valueOf(this.jdField_for);
      return str1 + " " + paramString + " " + str2;
    }
  }
  
  private class e
  {
    protected int jdField_do = 0;
    protected int jdField_if = 0;
    protected int a = 0;
    
    public e(int paramInt)
    {
      this.a = paramInt;
    }
    
    public void a(int paramInt)
    {
      int i = paramInt - this.a;
      this.jdField_do += i * i;
      this.jdField_if += 1;
    }
    
    public int a()
    {
      if (this.jdField_if > 1) {
        return (int)Math.sqrt(this.jdField_do / (this.jdField_if - 1));
      }
      return 0;
    }
  }
  
  private class a
    implements Runnable
  {
    private GlobalMessage jdField_for;
    private String jdField_do;
    private ArrayList jdField_if;
    private HashMap a;
    
    a(GlobalMessage paramGlobalMessage, String paramString, ArrayList paramArrayList, HashMap paramHashMap)
    {
      this.jdField_for = paramGlobalMessage;
      this.jdField_do = paramString;
      this.jdField_if = paramArrayList;
      this.a = paramHashMap;
    }
    
    public void run()
    {
      if (this.jdField_for.getMsgTypeValue() == 1)
      {
        try
        {
          MessageAdapter.this.sendGlobalMsgTX(this.jdField_for, this.jdField_do, this.jdField_if, this.a);
        }
        catch (Exception localException1) {}
      }
      else if (this.jdField_for.getMsgTypeValue() == 2)
      {
        this.a.put("GLOBAL_MESSAGE_KEY", this.jdField_for);
        int i = com.ffusion.csil.handlers.Messages._mailService.sendBulkMessage(this.jdField_for.getBankId(), this.jdField_for.getAffiliateBankIdValue(), this.jdField_for.getToGroupTypeValue(), this.jdField_for.getToGroupIDValue(), this.jdField_for.getFromName("en_US"), this.jdField_for.getSubject("en_US"), this.jdField_for.getMsgText("en_US"), this.a);
        int j = 6;
        if (i != 0) {
          j = 5;
        }
        Connection localConnection = null;
        try
        {
          localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
          MessageAdapter.this.modifyGlobalMsgStatusTX(localConnection, this.jdField_for.getGlobalMsgIDValue(), j);
        }
        catch (Exception localException2)
        {
          DebugLog.log(Level.FINE, "SendMessageOpertation.run(): " + localException2.getMessage());
        }
        finally
        {
          DBUtil.returnConnection(Profile.poolName, localConnection);
        }
      }
    }
  }
  
  private class b
    extends Thread
  {
    private b() {}
    
    public void run()
    {
      for (;;)
      {
        synchronized (MessageAdapter.this.amH)
        {
          try
          {
            if (MessageAdapter.this.amH.size() == 0) {
              MessageAdapter.this.amH.wait();
            }
          }
          catch (InterruptedException localInterruptedException) {}
        }
        ??? = null;
        int i = 0;
        for (;;)
        {
          synchronized (MessageAdapter.this.amH)
          {
            i = MessageAdapter.this.amH.size();
            if (i > 0) {
              ??? = (Runnable)MessageAdapter.this.amH.remove(0);
            }
          }
          if (??? == null) {
            break;
          }
          ((Runnable)???).run();
          ??? = null;
        }
      }
    }
    
    b(MessageAdapter.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.MessageAdapter
 * JD-Core Version:    0.7.0.1
 */