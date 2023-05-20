/*   1:    */ package com.ffusion.banksim.interfaces;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public class BSDBParams
/*   6:    */   implements Serializable
/*   7:    */ {
/*   8:    */   public static final int CONN_UNKNOWN = 0;
/*   9:    */   public static final int CONN_ASA = 1;
/*  10:    */   public static final int CONN_ASE = 2;
/*  11:    */   public static final int CONN_DB2 = 3;
/*  12:    */   public static final int CONN_ORACLE = 4;
/*  13:    */   public static final int CONN_DB2390 = 5;
/*  14:    */   public static final int CONN_MSSQL = 6;
/*  15:    */   public static final int CONN_DB2_UN2 = 7;
/*  16:    */   public static final String CONNSTR_ASA = "ASA";
/*  17:    */   public static final String CONNSTR_ASE = "ASE";
/*  18:    */   public static final String CONNSTR_DB2 = "DB2";
/*  19:    */   public static final String CONNSTR_DB2_UN2 = "DB2:UN2";
/*  20:    */   public static final String CONNSTR_ORACLE = "ORACLE";
/*  21:    */   public static final String CONNSTR_DB2390 = "DB2390";
/*  22:    */   public static final String CONNSTR_MSSQL = "MSSQL";
/*  23:    */   private String jdField_int;
/*  24:    */   private String jdField_if;
/*  25:    */   private String a;
/*  26:    */   private String jdField_do;
/*  27:    */   private String jdField_try;
/*  28:    */   private String jdField_for;
/*  29:    */   private boolean jdField_new;
/*  30:    */   private boolean jdField_char;
/*  31:    */   private int jdField_byte;
/*  32:    */   
/*  33:    */   public static BSDBParams createHAParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
/*  34:    */   {
/*  35: 59 */     BSDBParams localBSDBParams = new BSDBParams();
/*  36: 60 */     localBSDBParams.jdField_int = paramString1;
/*  37: 61 */     localBSDBParams.jdField_if = paramString2;
/*  38: 62 */     localBSDBParams.jdField_do = paramString3;
/*  39: 63 */     localBSDBParams.jdField_try = paramString4;
/*  40: 64 */     localBSDBParams.jdField_for = paramString5;
/*  41: 65 */     localBSDBParams.jdField_byte = 2;
/*  42: 66 */     localBSDBParams.jdField_char = true;
/*  43: 67 */     return localBSDBParams;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static BSDBParams createASEJConnectParams(String paramString1, String paramString2, String paramString3)
/*  47:    */   {
/*  48: 78 */     BSDBParams localBSDBParams = new BSDBParams();
/*  49: 79 */     localBSDBParams.jdField_int = paramString1;
/*  50: 80 */     localBSDBParams.jdField_if = paramString2;
/*  51: 81 */     localBSDBParams.a = paramString3;
/*  52: 82 */     localBSDBParams.jdField_byte = 2;
/*  53: 83 */     return localBSDBParams;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static BSDBParams createASEJConnectParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
/*  57:    */   {
/*  58: 96 */     if ((paramString5 != null) && (paramString5.length() > 0)) {
/*  59: 97 */       return createASEJConnectParams(paramString1, paramString2, "jdbc:sybase:Tds:" + paramString3 + ":" + paramString4 + "/" + paramString5);
/*  60:    */     }
/*  61:101 */     return createASEJConnectParams(paramString1, paramString2, "jdbc:sybase:Tds:" + paramString3 + ":" + paramString4);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static BSDBParams createMSSQLParams(String paramString1, String paramString2, String paramString3)
/*  65:    */   {
/*  66:116 */     BSDBParams localBSDBParams = new BSDBParams();
/*  67:117 */     localBSDBParams.jdField_int = paramString1;
/*  68:118 */     localBSDBParams.jdField_if = paramString2;
/*  69:119 */     localBSDBParams.a = paramString3;
/*  70:120 */     localBSDBParams.jdField_byte = 6;
/*  71:121 */     return localBSDBParams;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static BSDBParams createMSSQLParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
/*  75:    */   {
/*  76:133 */     if ((paramString5 != null) && (paramString5.length() > 0)) {
/*  77:134 */       return createMSSQLParams(paramString1, paramString2, "jdbc:JSQLConnect://" + paramString3 + ":" + paramString4 + "/database=" + paramString5);
/*  78:    */     }
/*  79:138 */     return createMSSQLParams(paramString1, paramString2, "jdbc:JSQLConnect://" + paramString3 + ":" + paramString4);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static BSDBParams createASAJConnectParams(String paramString1, String paramString2, String paramString3)
/*  83:    */   {
/*  84:152 */     BSDBParams localBSDBParams = new BSDBParams();
/*  85:153 */     localBSDBParams.jdField_int = paramString1;
/*  86:154 */     localBSDBParams.jdField_if = paramString2;
/*  87:155 */     localBSDBParams.a = paramString3;
/*  88:156 */     localBSDBParams.jdField_byte = 1;
/*  89:157 */     return localBSDBParams;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static BSDBParams createASAJConnectParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
/*  93:    */   {
/*  94:170 */     if ((paramString5 != null) && (paramString5.length() > 0)) {
/*  95:171 */       return createASAJConnectParams(paramString1, paramString2, "jdbc:sybase:Tds:" + paramString3 + ":" + paramString4 + "/" + paramString5);
/*  96:    */     }
/*  97:175 */     return createASAJConnectParams(paramString1, paramString2, "jdbc:sybase:Tds:" + paramString3 + ":" + paramString4);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static BSDBParams createOracleParams(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
/* 101:    */   {
/* 102:191 */     BSDBParams localBSDBParams = new BSDBParams();
/* 103:192 */     localBSDBParams.jdField_int = paramString1;
/* 104:193 */     localBSDBParams.jdField_if = paramString2;
/* 105:194 */     localBSDBParams.a = paramString3;
/* 106:195 */     localBSDBParams.jdField_new = paramBoolean;
/* 107:196 */     localBSDBParams.jdField_byte = 4;
/* 108:197 */     return localBSDBParams;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static BSDBParams createOracleParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, boolean paramBoolean)
/* 112:    */   {
/* 113:212 */     if (paramBoolean) {
/* 114:213 */       return createOracleParams(paramString1, paramString2, "jdbc:oracle:oci8:@(description=(address=(host=" + paramString3 + ")(protocol=tcp)" + "(port=" + paramString4 + "))(connect_data=" + "(sid=" + paramString5 + ")))", paramBoolean);
/* 115:    */     }
/* 116:222 */     return createOracleParams(paramString1, paramString2, "jdbc:oracle:thin:@" + paramString3 + ":" + paramString4 + ":" + paramString5, paramBoolean);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public static BSDBParams createDB2AppParams(String paramString1, String paramString2, String paramString3)
/* 120:    */   {
/* 121:238 */     BSDBParams localBSDBParams = new BSDBParams();
/* 122:239 */     localBSDBParams.jdField_int = paramString1;
/* 123:240 */     localBSDBParams.jdField_if = paramString2;
/* 124:241 */     localBSDBParams.a = paramString3;
/* 125:242 */     localBSDBParams.jdField_new = true;
/* 126:243 */     localBSDBParams.jdField_byte = 3;
/* 127:244 */     return localBSDBParams;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static BSDBParams createDB2UN2Params(String paramString1, String paramString2, String paramString3)
/* 131:    */   {
/* 132:254 */     BSDBParams localBSDBParams = new BSDBParams();
/* 133:255 */     localBSDBParams.jdField_int = paramString1;
/* 134:256 */     localBSDBParams.jdField_if = paramString2;
/* 135:257 */     localBSDBParams.a = paramString3;
/* 136:258 */     localBSDBParams.jdField_new = true;
/* 137:259 */     localBSDBParams.jdField_byte = 7;
/* 138:260 */     return localBSDBParams;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static BSDBParams createDB2AppParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
/* 142:    */   {
/* 143:272 */     return createDB2AppParams(paramString1, paramString2, "jdbc:db2:" + paramString5);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public static BSDBParams createDB2UN2Params(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
/* 147:    */   {
/* 148:286 */     return createDB2UN2Params(paramString1, paramString2, "jdbc:db2:" + paramString5);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public static BSDBParams createDB2NetParams(String paramString1, String paramString2, String paramString3)
/* 152:    */   {
/* 153:298 */     BSDBParams localBSDBParams = new BSDBParams();
/* 154:299 */     localBSDBParams.jdField_int = paramString1;
/* 155:300 */     localBSDBParams.jdField_if = paramString2;
/* 156:301 */     localBSDBParams.a = paramString3;
/* 157:302 */     localBSDBParams.jdField_new = false;
/* 158:303 */     localBSDBParams.jdField_byte = 3;
/* 159:304 */     return localBSDBParams;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public static BSDBParams createDB2NetParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
/* 163:    */   {
/* 164:316 */     return createDB2NetParams(paramString1, paramString2, "jdbc:db2://" + paramString3 + ":" + paramString4 + "/" + paramString5);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public static BSDBParams createDB2390Params(String paramString1, String paramString2, String paramString3)
/* 168:    */   {
/* 169:329 */     BSDBParams localBSDBParams = new BSDBParams();
/* 170:330 */     localBSDBParams.jdField_int = paramString1;
/* 171:331 */     localBSDBParams.jdField_if = paramString2;
/* 172:332 */     localBSDBParams.a = paramString3;
/* 173:333 */     localBSDBParams.jdField_new = false;
/* 174:334 */     localBSDBParams.jdField_byte = 5;
/* 175:335 */     return localBSDBParams;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public static BSDBParams createDB2390Params(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
/* 179:    */   {
/* 180:347 */     return createDB2390Params(paramString1, paramString2, "jdbc:db2os390://" + paramString3 + ":" + paramString4 + "/" + paramString5);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public final String getUser()
/* 184:    */   {
/* 185:357 */     return this.jdField_int;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public final String getPassword()
/* 189:    */   {
/* 190:361 */     return this.jdField_if;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public final String getDBUrl()
/* 194:    */   {
/* 195:365 */     return this.a;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public final String getCtxFactory()
/* 199:    */   {
/* 200:369 */     return this.jdField_do;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public final String getJNDIURL()
/* 204:    */   {
/* 205:373 */     return this.jdField_try;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public final String getJNDICtx()
/* 209:    */   {
/* 210:377 */     return this.jdField_for;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public final boolean isNativeDriver()
/* 214:    */   {
/* 215:381 */     return this.jdField_new;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public final boolean isHA()
/* 219:    */   {
/* 220:385 */     return this.jdField_char;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public int getConnectionType()
/* 224:    */   {
/* 225:389 */     return this.jdField_byte;
/* 226:    */   }
/* 227:    */   
/* 228:392 */   private String jdField_case = null;
/* 229:    */   
/* 230:    */   public String getDBDriver()
/* 231:    */   {
/* 232:393 */     return this.jdField_case;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setDBDriver(String paramString)
/* 236:    */   {
/* 237:394 */     this.jdField_case = paramString;
/* 238:    */   }
/* 239:    */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.interfaces.BSDBParams
 * JD-Core Version:    0.7.0.1
 */