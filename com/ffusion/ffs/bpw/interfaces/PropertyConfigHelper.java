/*   1:    */ package com.ffusion.ffs.bpw.interfaces;
/*   2:    */ 
/*   3:    */ import BCD.BinaryHelper;
/*   4:    */ import com.ffusion.ffs.util.FFSProperties;
/*   5:    */ import com.sybase.CORBA.ObjectVal;
/*   6:    */ import org.omg.CORBA.Any;
/*   7:    */ import org.omg.CORBA.BAD_OPERATION;
/*   8:    */ import org.omg.CORBA.ORB;
/*   9:    */ import org.omg.CORBA.StructMember;
/*  10:    */ import org.omg.CORBA.TCKind;
/*  11:    */ import org.omg.CORBA.TypeCode;
/*  12:    */ import org.omg.CORBA.portable.InputStream;
/*  13:    */ import org.omg.CORBA.portable.OutputStream;
/*  14:    */ 
/*  15:    */ public abstract class PropertyConfigHelper
/*  16:    */ {
/*  17:    */   public static TypeCode _type;
/*  18:    */   
/*  19:    */   public static PropertyConfig clone(PropertyConfig paramPropertyConfig)
/*  20:    */   {
/*  21: 16 */     if (paramPropertyConfig == null) {
/*  22: 18 */       return null;
/*  23:    */     }
/*  24: 20 */     PropertyConfig localPropertyConfig = new PropertyConfig();
/*  25: 21 */     localPropertyConfig.serverName = paramPropertyConfig.serverName;
/*  26: 22 */     localPropertyConfig.NoImmediateTransfer = paramPropertyConfig.NoImmediateTransfer;
/*  27: 23 */     localPropertyConfig.SupportImmediatePmt = paramPropertyConfig.SupportImmediatePmt;
/*  28: 24 */     localPropertyConfig.RouteTimedOutToBatch = paramPropertyConfig.RouteTimedOutToBatch;
/*  29: 25 */     localPropertyConfig.UseExtdPayeeID = paramPropertyConfig.UseExtdPayeeID;
/*  30: 26 */     localPropertyConfig.Retries = paramPropertyConfig.Retries;
/*  31: 27 */     localPropertyConfig.Timeout = paramPropertyConfig.Timeout;
/*  32: 28 */     localPropertyConfig.PurgeWakeupTime = paramPropertyConfig.PurgeWakeupTime;
/*  33: 29 */     localPropertyConfig.DayToPurgeClosedInstruction = paramPropertyConfig.DayToPurgeClosedInstruction;
/*  34: 30 */     localPropertyConfig.DayToPurgeEventHistory = paramPropertyConfig.DayToPurgeEventHistory;
/*  35: 31 */     localPropertyConfig.DayToPurgeActivityLog = paramPropertyConfig.DayToPurgeActivityLog;
/*  36: 32 */     localPropertyConfig.MBConn_dbType = paramPropertyConfig.MBConn_dbType;
/*  37: 33 */     localPropertyConfig.MBConn_host = paramPropertyConfig.MBConn_host;
/*  38: 34 */     localPropertyConfig.MBConn_port = paramPropertyConfig.MBConn_port;
/*  39: 35 */     localPropertyConfig.MBConn_user = paramPropertyConfig.MBConn_user;
/*  40: 36 */     localPropertyConfig.MBConn_passwd = paramPropertyConfig.MBConn_passwd;
/*  41: 37 */     localPropertyConfig.MBConn_dbname = paramPropertyConfig.MBConn_dbname;
/*  42: 38 */     localPropertyConfig.MBConn_url = paramPropertyConfig.MBConn_url;
/*  43: 39 */     localPropertyConfig.LogLevel = paramPropertyConfig.LogLevel;
/*  44: 40 */     localPropertyConfig.WireProcessingMode = paramPropertyConfig.WireProcessingMode;
/*  45: 41 */     localPropertyConfig.StartPayeeListID = paramPropertyConfig.StartPayeeListID;
/*  46: 42 */     localPropertyConfig.FundsAllocRetries = paramPropertyConfig.FundsAllocRetries;
/*  47: 43 */     localPropertyConfig.FundsApprovalRetries = paramPropertyConfig.FundsApprovalRetries;
/*  48: 44 */     localPropertyConfig.ConcurrentEventProcesses = paramPropertyConfig.ConcurrentEventProcesses;
/*  49: 45 */     localPropertyConfig.MaxPoolSize = paramPropertyConfig.MaxPoolSize;
/*  50: 46 */     localPropertyConfig.OptimalPoolSize = paramPropertyConfig.OptimalPoolSize;
/*  51: 47 */     localPropertyConfig.DebugLevel = paramPropertyConfig.DebugLevel;
/*  52: 48 */     localPropertyConfig.BatchSize = paramPropertyConfig.BatchSize;
/*  53: 49 */     localPropertyConfig.MinuteNotToRecover = paramPropertyConfig.MinuteNotToRecover;
/*  54: 50 */     localPropertyConfig.EnforceEnrollment = paramPropertyConfig.EnforceEnrollment;
/*  55: 51 */     localPropertyConfig.SupportCluster = paramPropertyConfig.SupportCluster;
/*  56: 52 */     localPropertyConfig.SchWaitTime = paramPropertyConfig.SchWaitTime;
/*  57: 53 */     localPropertyConfig.OneCustPayeeToOnePersonalPayee = paramPropertyConfig.OneCustPayeeToOnePersonalPayee;
/*  58: 54 */     localPropertyConfig.EnforcePayment = paramPropertyConfig.EnforcePayment;
/*  59: 55 */     localPropertyConfig.AutoStartWaitTime = paramPropertyConfig.AutoStartWaitTime;
/*  60: 56 */     localPropertyConfig.ServletDebug = paramPropertyConfig.ServletDebug;
/*  61: 57 */     localPropertyConfig.BPWServ_userName = paramPropertyConfig.BPWServ_userName;
/*  62: 58 */     localPropertyConfig.BPWServ_password = paramPropertyConfig.BPWServ_password;
/*  63: 59 */     localPropertyConfig.BPWServ_protocol = paramPropertyConfig.BPWServ_protocol;
/*  64: 60 */     localPropertyConfig.BPWServ_host = paramPropertyConfig.BPWServ_host;
/*  65: 61 */     localPropertyConfig.BPWServ_port = paramPropertyConfig.BPWServ_port;
/*  66: 62 */     localPropertyConfig.BPWServ_nameContext = paramPropertyConfig.BPWServ_nameContext;
/*  67: 63 */     localPropertyConfig.BPWServ_jndiName = paramPropertyConfig.BPWServ_jndiName;
/*  68: 64 */     localPropertyConfig.BPWServ_dbType = paramPropertyConfig.BPWServ_dbType;
/*  69: 65 */     localPropertyConfig.BPWServ_dbHost = paramPropertyConfig.BPWServ_dbHost;
/*  70: 66 */     localPropertyConfig.BPWServ_dbPort = paramPropertyConfig.BPWServ_dbPort;
/*  71: 67 */     localPropertyConfig.BPWServ_dbUser = paramPropertyConfig.BPWServ_dbUser;
/*  72: 68 */     localPropertyConfig.BPWServ_dbPassword = paramPropertyConfig.BPWServ_dbPassword;
/*  73: 69 */     localPropertyConfig.BPWServ_dbName = paramPropertyConfig.BPWServ_dbName;
/*  74: 70 */     localPropertyConfig.ThreadPoolMaximumSize = paramPropertyConfig.ThreadPoolMaximumSize;
/*  75: 71 */     localPropertyConfig.ThreadPoolInitialSize = paramPropertyConfig.ThreadPoolInitialSize;
/*  76: 72 */     localPropertyConfig.otherProperties = ((FFSProperties)ObjectVal.readObject(ObjectVal.writeObject(paramPropertyConfig.otherProperties)));
/*  77: 73 */     localPropertyConfig.clusterConfig = ServerGeneralConfigHelper.clone(paramPropertyConfig.clusterConfig);
/*  78: 74 */     return localPropertyConfig;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static PropertyConfig read(InputStream paramInputStream)
/*  82:    */   {
/*  83: 80 */     PropertyConfig localPropertyConfig = new PropertyConfig();
/*  84: 81 */     localPropertyConfig.serverName = paramInputStream.read_string();
/*  85: 82 */     localPropertyConfig.NoImmediateTransfer = paramInputStream.read_boolean();
/*  86: 83 */     localPropertyConfig.SupportImmediatePmt = paramInputStream.read_boolean();
/*  87: 84 */     localPropertyConfig.RouteTimedOutToBatch = paramInputStream.read_boolean();
/*  88: 85 */     localPropertyConfig.UseExtdPayeeID = paramInputStream.read_boolean();
/*  89: 86 */     localPropertyConfig.Retries = paramInputStream.read_long();
/*  90: 87 */     localPropertyConfig.Timeout = paramInputStream.read_long();
/*  91: 88 */     localPropertyConfig.PurgeWakeupTime = paramInputStream.read_string();
/*  92: 89 */     localPropertyConfig.DayToPurgeClosedInstruction = paramInputStream.read_long();
/*  93: 90 */     localPropertyConfig.DayToPurgeEventHistory = paramInputStream.read_long();
/*  94: 91 */     localPropertyConfig.DayToPurgeActivityLog = paramInputStream.read_long();
/*  95: 92 */     localPropertyConfig.MBConn_dbType = paramInputStream.read_string();
/*  96: 93 */     localPropertyConfig.MBConn_host = paramInputStream.read_string();
/*  97: 94 */     localPropertyConfig.MBConn_port = paramInputStream.read_string();
/*  98: 95 */     localPropertyConfig.MBConn_user = paramInputStream.read_string();
/*  99: 96 */     localPropertyConfig.MBConn_passwd = paramInputStream.read_string();
/* 100: 97 */     localPropertyConfig.MBConn_dbname = paramInputStream.read_string();
/* 101: 98 */     localPropertyConfig.MBConn_url = paramInputStream.read_string();
/* 102: 99 */     localPropertyConfig.LogLevel = paramInputStream.read_long();
/* 103:100 */     localPropertyConfig.WireProcessingMode = paramInputStream.read_string();
/* 104:101 */     localPropertyConfig.StartPayeeListID = paramInputStream.read_long();
/* 105:102 */     localPropertyConfig.FundsAllocRetries = paramInputStream.read_long();
/* 106:103 */     localPropertyConfig.FundsApprovalRetries = paramInputStream.read_long();
/* 107:104 */     localPropertyConfig.ConcurrentEventProcesses = paramInputStream.read_long();
/* 108:105 */     localPropertyConfig.MaxPoolSize = paramInputStream.read_long();
/* 109:106 */     localPropertyConfig.OptimalPoolSize = paramInputStream.read_long();
/* 110:107 */     localPropertyConfig.DebugLevel = paramInputStream.read_long();
/* 111:108 */     localPropertyConfig.BatchSize = paramInputStream.read_long();
/* 112:109 */     localPropertyConfig.MinuteNotToRecover = paramInputStream.read_long();
/* 113:110 */     localPropertyConfig.EnforceEnrollment = paramInputStream.read_boolean();
/* 114:111 */     localPropertyConfig.SupportCluster = paramInputStream.read_boolean();
/* 115:112 */     localPropertyConfig.SchWaitTime = paramInputStream.read_longlong();
/* 116:113 */     localPropertyConfig.OneCustPayeeToOnePersonalPayee = paramInputStream.read_boolean();
/* 117:114 */     localPropertyConfig.EnforcePayment = paramInputStream.read_boolean();
/* 118:115 */     localPropertyConfig.AutoStartWaitTime = paramInputStream.read_longlong();
/* 119:116 */     localPropertyConfig.ServletDebug = paramInputStream.read_boolean();
/* 120:117 */     localPropertyConfig.BPWServ_userName = paramInputStream.read_string();
/* 121:118 */     localPropertyConfig.BPWServ_password = paramInputStream.read_string();
/* 122:119 */     localPropertyConfig.BPWServ_protocol = paramInputStream.read_string();
/* 123:120 */     localPropertyConfig.BPWServ_host = paramInputStream.read_string();
/* 124:121 */     localPropertyConfig.BPWServ_port = paramInputStream.read_long();
/* 125:122 */     localPropertyConfig.BPWServ_nameContext = paramInputStream.read_string();
/* 126:123 */     localPropertyConfig.BPWServ_jndiName = paramInputStream.read_string();
/* 127:124 */     localPropertyConfig.BPWServ_dbType = paramInputStream.read_string();
/* 128:125 */     localPropertyConfig.BPWServ_dbHost = paramInputStream.read_string();
/* 129:126 */     localPropertyConfig.BPWServ_dbPort = paramInputStream.read_string();
/* 130:127 */     localPropertyConfig.BPWServ_dbUser = paramInputStream.read_string();
/* 131:128 */     localPropertyConfig.BPWServ_dbPassword = paramInputStream.read_string();
/* 132:129 */     localPropertyConfig.BPWServ_dbName = paramInputStream.read_string();
/* 133:130 */     localPropertyConfig.ThreadPoolMaximumSize = paramInputStream.read_long();
/* 134:131 */     localPropertyConfig.ThreadPoolInitialSize = paramInputStream.read_long();
/* 135:132 */     localPropertyConfig.otherProperties = ((FFSProperties)ObjectVal.readObject(BinaryHelper.read(paramInputStream)));
/* 136:133 */     localPropertyConfig.clusterConfig = ServerGeneralConfigHelper.read(paramInputStream);
/* 137:134 */     return localPropertyConfig;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public static void write(OutputStream paramOutputStream, PropertyConfig paramPropertyConfig)
/* 141:    */   {
/* 142:141 */     if (paramPropertyConfig == null) {
/* 143:143 */       paramPropertyConfig = new PropertyConfig();
/* 144:    */     }
/* 145:145 */     paramOutputStream.write_string(paramPropertyConfig.serverName);
/* 146:146 */     paramOutputStream.write_boolean(paramPropertyConfig.NoImmediateTransfer);
/* 147:147 */     paramOutputStream.write_boolean(paramPropertyConfig.SupportImmediatePmt);
/* 148:148 */     paramOutputStream.write_boolean(paramPropertyConfig.RouteTimedOutToBatch);
/* 149:149 */     paramOutputStream.write_boolean(paramPropertyConfig.UseExtdPayeeID);
/* 150:150 */     paramOutputStream.write_long(paramPropertyConfig.Retries);
/* 151:151 */     paramOutputStream.write_long(paramPropertyConfig.Timeout);
/* 152:152 */     paramOutputStream.write_string(paramPropertyConfig.PurgeWakeupTime);
/* 153:153 */     paramOutputStream.write_long(paramPropertyConfig.DayToPurgeClosedInstruction);
/* 154:154 */     paramOutputStream.write_long(paramPropertyConfig.DayToPurgeEventHistory);
/* 155:155 */     paramOutputStream.write_long(paramPropertyConfig.DayToPurgeActivityLog);
/* 156:156 */     paramOutputStream.write_string(paramPropertyConfig.MBConn_dbType);
/* 157:157 */     paramOutputStream.write_string(paramPropertyConfig.MBConn_host);
/* 158:158 */     paramOutputStream.write_string(paramPropertyConfig.MBConn_port);
/* 159:159 */     paramOutputStream.write_string(paramPropertyConfig.MBConn_user);
/* 160:160 */     paramOutputStream.write_string(paramPropertyConfig.MBConn_passwd);
/* 161:161 */     paramOutputStream.write_string(paramPropertyConfig.MBConn_dbname);
/* 162:162 */     paramOutputStream.write_string(paramPropertyConfig.MBConn_url);
/* 163:163 */     paramOutputStream.write_long(paramPropertyConfig.LogLevel);
/* 164:164 */     paramOutputStream.write_string(paramPropertyConfig.WireProcessingMode);
/* 165:165 */     paramOutputStream.write_long(paramPropertyConfig.StartPayeeListID);
/* 166:166 */     paramOutputStream.write_long(paramPropertyConfig.FundsAllocRetries);
/* 167:167 */     paramOutputStream.write_long(paramPropertyConfig.FundsApprovalRetries);
/* 168:168 */     paramOutputStream.write_long(paramPropertyConfig.ConcurrentEventProcesses);
/* 169:169 */     paramOutputStream.write_long(paramPropertyConfig.MaxPoolSize);
/* 170:170 */     paramOutputStream.write_long(paramPropertyConfig.OptimalPoolSize);
/* 171:171 */     paramOutputStream.write_long(paramPropertyConfig.DebugLevel);
/* 172:172 */     paramOutputStream.write_long(paramPropertyConfig.BatchSize);
/* 173:173 */     paramOutputStream.write_long(paramPropertyConfig.MinuteNotToRecover);
/* 174:174 */     paramOutputStream.write_boolean(paramPropertyConfig.EnforceEnrollment);
/* 175:175 */     paramOutputStream.write_boolean(paramPropertyConfig.SupportCluster);
/* 176:176 */     paramOutputStream.write_longlong(paramPropertyConfig.SchWaitTime);
/* 177:177 */     paramOutputStream.write_boolean(paramPropertyConfig.OneCustPayeeToOnePersonalPayee);
/* 178:178 */     paramOutputStream.write_boolean(paramPropertyConfig.EnforcePayment);
/* 179:179 */     paramOutputStream.write_longlong(paramPropertyConfig.AutoStartWaitTime);
/* 180:180 */     paramOutputStream.write_boolean(paramPropertyConfig.ServletDebug);
/* 181:181 */     paramOutputStream.write_string(paramPropertyConfig.BPWServ_userName);
/* 182:182 */     paramOutputStream.write_string(paramPropertyConfig.BPWServ_password);
/* 183:183 */     paramOutputStream.write_string(paramPropertyConfig.BPWServ_protocol);
/* 184:184 */     paramOutputStream.write_string(paramPropertyConfig.BPWServ_host);
/* 185:185 */     paramOutputStream.write_long(paramPropertyConfig.BPWServ_port);
/* 186:186 */     paramOutputStream.write_string(paramPropertyConfig.BPWServ_nameContext);
/* 187:187 */     paramOutputStream.write_string(paramPropertyConfig.BPWServ_jndiName);
/* 188:188 */     paramOutputStream.write_string(paramPropertyConfig.BPWServ_dbType);
/* 189:189 */     paramOutputStream.write_string(paramPropertyConfig.BPWServ_dbHost);
/* 190:190 */     paramOutputStream.write_string(paramPropertyConfig.BPWServ_dbPort);
/* 191:191 */     paramOutputStream.write_string(paramPropertyConfig.BPWServ_dbUser);
/* 192:192 */     paramOutputStream.write_string(paramPropertyConfig.BPWServ_dbPassword);
/* 193:193 */     paramOutputStream.write_string(paramPropertyConfig.BPWServ_dbName);
/* 194:194 */     paramOutputStream.write_long(paramPropertyConfig.ThreadPoolMaximumSize);
/* 195:195 */     paramOutputStream.write_long(paramPropertyConfig.ThreadPoolInitialSize);
/* 196:196 */     BinaryHelper.write(paramOutputStream, ObjectVal.writeObject(paramPropertyConfig.otherProperties));
/* 197:197 */     ServerGeneralConfigHelper.write(paramOutputStream, paramPropertyConfig.clusterConfig);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public static String _idl()
/* 201:    */   {
/* 202:202 */     return "com::ffusion::ffs::bpw::interfaces::PropertyConfig";
/* 203:    */   }
/* 204:    */   
/* 205:    */   public static TypeCode type()
/* 206:    */   {
/* 207:209 */     if (_type == null)
/* 208:    */     {
/* 209:211 */       ORB localORB = ORB.init();
/* 210:212 */       StructMember[] arrayOfStructMember = 
/* 211:213 */         {
/* 212:214 */         new StructMember("serverName", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 213:215 */         new StructMember("NoImmediateTransfer", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 214:216 */         new StructMember("SupportImmediatePmt", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 215:217 */         new StructMember("RouteTimedOutToBatch", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 216:218 */         new StructMember("UseExtdPayeeID", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 217:219 */         new StructMember("Retries", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 218:220 */         new StructMember("Timeout", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 219:221 */         new StructMember("PurgeWakeupTime", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 220:222 */         new StructMember("DayToPurgeClosedInstruction", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 221:223 */         new StructMember("DayToPurgeEventHistory", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 222:224 */         new StructMember("DayToPurgeActivityLog", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 223:225 */         new StructMember("MBConn_dbType", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 224:226 */         new StructMember("MBConn_host", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 225:227 */         new StructMember("MBConn_port", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 226:228 */         new StructMember("MBConn_user", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 227:229 */         new StructMember("MBConn_passwd", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 228:230 */         new StructMember("MBConn_dbname", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 229:231 */         new StructMember("MBConn_url", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 230:232 */         new StructMember("LogLevel", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 231:233 */         new StructMember("WireProcessingMode", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 232:234 */         new StructMember("StartPayeeListID", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 233:235 */         new StructMember("FundsAllocRetries", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 234:236 */         new StructMember("FundsApprovalRetries", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 235:237 */         new StructMember("ConcurrentEventProcesses", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 236:238 */         new StructMember("MaxPoolSize", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 237:239 */         new StructMember("OptimalPoolSize", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 238:240 */         new StructMember("DebugLevel", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 239:241 */         new StructMember("BatchSize", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 240:242 */         new StructMember("MinuteNotToRecover", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 241:243 */         new StructMember("EnforceEnrollment", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 242:244 */         new StructMember("SupportCluster", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 243:245 */         new StructMember("SchWaitTime", localORB.get_primitive_tc(TCKind.tk_longlong), null), 
/* 244:246 */         new StructMember("OneCustPayeeToOnePersonalPayee", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 245:247 */         new StructMember("EnforcePayment", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 246:248 */         new StructMember("AutoStartWaitTime", localORB.get_primitive_tc(TCKind.tk_longlong), null), 
/* 247:249 */         new StructMember("ServletDebug", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 248:250 */         new StructMember("BPWServ_userName", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 249:251 */         new StructMember("BPWServ_password", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 250:252 */         new StructMember("BPWServ_protocol", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 251:253 */         new StructMember("BPWServ_host", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 252:254 */         new StructMember("BPWServ_port", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 253:255 */         new StructMember("BPWServ_nameContext", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 254:256 */         new StructMember("BPWServ_jndiName", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 255:257 */         new StructMember("BPWServ_dbType", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 256:258 */         new StructMember("BPWServ_dbHost", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 257:259 */         new StructMember("BPWServ_dbPort", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 258:260 */         new StructMember("BPWServ_dbUser", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 259:261 */         new StructMember("BPWServ_dbPassword", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 260:262 */         new StructMember("BPWServ_dbName", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 261:263 */         new StructMember("ThreadPoolMaximumSize", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 262:264 */         new StructMember("ThreadPoolInitialSize", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 263:265 */         new StructMember("otherProperties", BinaryHelper.type(), null), 
/* 264:266 */         new StructMember("clusterConfig", ServerGeneralConfigHelper.type(), null) };
/* 265:    */       
/* 266:268 */       _type = localORB.create_struct_tc(id(), "PropertyConfig", arrayOfStructMember);
/* 267:    */     }
/* 268:270 */     return _type;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public static void insert(Any paramAny, PropertyConfig paramPropertyConfig)
/* 272:    */   {
/* 273:277 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 274:278 */     write(localOutputStream, paramPropertyConfig);
/* 275:279 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 276:    */   }
/* 277:    */   
/* 278:    */   public static PropertyConfig extract(Any paramAny)
/* 279:    */   {
/* 280:285 */     if (!paramAny.type().equal(type())) {
/* 281:287 */       throw new BAD_OPERATION();
/* 282:    */     }
/* 283:289 */     return read(paramAny.create_input_stream());
/* 284:    */   }
/* 285:    */   
/* 286:    */   public static String id()
/* 287:    */   {
/* 288:294 */     return "IDL:com/ffusion/ffs/bpw/interfaces/PropertyConfig:1.0";
/* 289:    */   }
/* 290:    */ }


/* Location:           D:\drops\jd\jars\BPWAdmin.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.PropertyConfigHelper
 * JD-Core Version:    0.7.0.1
 */