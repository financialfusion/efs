/*   1:    */ package com.ffusion.banksim.db;
/*   2:    */ 
/*   3:    */ import com.ffusion.banksim.interfaces.BSDBParams;
/*   4:    */ import java.io.ByteArrayInputStream;
/*   5:    */ import java.io.ByteArrayOutputStream;
/*   6:    */ import java.io.PrintWriter;
/*   7:    */ import java.io.StringReader;
/*   8:    */ import java.io.StringWriter;
/*   9:    */ import java.math.BigDecimal;
/*  10:    */ import java.sql.Date;
/*  11:    */ import java.sql.PreparedStatement;
/*  12:    */ import java.sql.SQLException;
/*  13:    */ import java.sql.Time;
/*  14:    */ import java.sql.Timestamp;
/*  15:    */ 
/*  16:    */ public class DBSqlUtils
/*  17:    */   implements DBSQLConstants
/*  18:    */ {
/*  19:    */   static boolean a(BSDBParams paramBSDBParams, String paramString)
/*  20:    */   {
/*  21: 29 */     if (paramBSDBParams.getConnectionType() != 4) {
/*  22: 29 */       return false;
/*  23:    */     }
/*  24: 30 */     if ((paramString == null) || (paramString.length() <= 4000)) {
/*  25: 30 */       return false;
/*  26:    */     }
/*  27: 31 */     return true;
/*  28:    */   }
/*  29:    */   
/*  30:    */   static boolean a(BSDBParams paramBSDBParams, byte[] paramArrayOfByte)
/*  31:    */   {
/*  32: 40 */     if (paramBSDBParams.getConnectionType() != 4) {
/*  33: 40 */       return false;
/*  34:    */     }
/*  35: 41 */     if ((paramArrayOfByte == null) || (paramArrayOfByte.length <= 4000)) {
/*  36: 41 */       return false;
/*  37:    */     }
/*  38: 42 */     return true;
/*  39:    */   }
/*  40:    */   
/*  41:    */   static boolean jdMethod_if(BSDBParams paramBSDBParams, String paramString)
/*  42:    */   {
/*  43: 50 */     if (paramBSDBParams.isNativeDriver()) {
/*  44: 50 */       return false;
/*  45:    */     }
/*  46: 51 */     return a(paramBSDBParams, paramString);
/*  47:    */   }
/*  48:    */   
/*  49:    */   static boolean jdMethod_if(BSDBParams paramBSDBParams, byte[] paramArrayOfByte)
/*  50:    */   {
/*  51: 59 */     if (paramBSDBParams.isNativeDriver()) {
/*  52: 59 */       return false;
/*  53:    */     }
/*  54: 60 */     return a(paramBSDBParams, paramArrayOfByte);
/*  55:    */   }
/*  56:    */   
/*  57:    */   static void a(PreparedStatement paramPreparedStatement, Object[] paramArrayOfObject, BSDBParams paramBSDBParams)
/*  58:    */     throws SQLException
/*  59:    */   {
/*  60: 73 */     paramPreparedStatement.clearParameters();
/*  61: 75 */     if (paramArrayOfObject != null) {
/*  62: 79 */       for (int i = 0; i < paramArrayOfObject.length; i++)
/*  63:    */       {
/*  64: 80 */         Object localObject1 = paramArrayOfObject[i];
/*  65: 81 */         if (localObject1 == null)
/*  66:    */         {
/*  67: 82 */           paramPreparedStatement.setString(i + 1, null);
/*  68:    */         }
/*  69:    */         else
/*  70:    */         {
/*  71:    */           Object localObject2;
/*  72: 83 */           if ((localObject1 instanceof String))
/*  73:    */           {
/*  74: 84 */             localObject2 = (String)localObject1;
/*  75: 85 */             if (a(paramBSDBParams, (String)localObject2)) {
/*  76: 91 */               paramPreparedStatement.setCharacterStream(i + 1, new StringReader((String)localObject2), ((String)localObject2).length());
/*  77: 92 */             } else if ((paramBSDBParams.getConnectionType() == 4) && (((String)localObject2).length() == 0)) {
/*  78: 96 */               paramPreparedStatement.setString(i + 1, " ");
/*  79:    */             } else {
/*  80: 98 */               paramPreparedStatement.setString(i + 1, (String)localObject2);
/*  81:    */             }
/*  82:    */           }
/*  83:100 */           else if ((localObject1 instanceof Integer))
/*  84:    */           {
/*  85:101 */             paramPreparedStatement.setInt(i + 1, ((Integer)localObject1).intValue());
/*  86:    */           }
/*  87:102 */           else if ((localObject1 instanceof Long))
/*  88:    */           {
/*  89:103 */             paramPreparedStatement.setLong(i + 1, ((Long)localObject1).longValue());
/*  90:    */           }
/*  91:104 */           else if ((localObject1 instanceof ByteArrayOutputStream))
/*  92:    */           {
/*  93:105 */             paramPreparedStatement.setBytes(i + 1, ((ByteArrayOutputStream)localObject1).toByteArray());
/*  94:    */           }
/*  95:106 */           else if ((localObject1 instanceof Float))
/*  96:    */           {
/*  97:107 */             paramPreparedStatement.setFloat(i + 1, ((Float)localObject1).floatValue());
/*  98:    */           }
/*  99:108 */           else if ((localObject1 instanceof Double))
/* 100:    */           {
/* 101:109 */             paramPreparedStatement.setDouble(i + 1, ((Double)localObject1).doubleValue());
/* 102:    */           }
/* 103:110 */           else if ((localObject1 instanceof BigDecimal))
/* 104:    */           {
/* 105:111 */             paramPreparedStatement.setBigDecimal(i + 1, (BigDecimal)localObject1);
/* 106:    */           }
/* 107:112 */           else if ((localObject1 instanceof Timestamp))
/* 108:    */           {
/* 109:113 */             paramPreparedStatement.setTimestamp(i + 1, (Timestamp)localObject1);
/* 110:    */           }
/* 111:114 */           else if ((localObject1 instanceof Date))
/* 112:    */           {
/* 113:115 */             paramPreparedStatement.setDate(i + 1, (Date)localObject1);
/* 114:    */           }
/* 115:116 */           else if ((localObject1 instanceof Time))
/* 116:    */           {
/* 117:117 */             paramPreparedStatement.setTime(i + 1, (Time)localObject1);
/* 118:    */           }
/* 119:118 */           else if ((localObject1 instanceof byte[]))
/* 120:    */           {
/* 121:119 */             localObject2 = (byte[])localObject1;
/* 122:120 */             if (a(paramBSDBParams, (byte[])localObject2)) {
/* 123:123 */               paramPreparedStatement.setBinaryStream(i + 1, new ByteArrayInputStream((byte[])localObject2), localObject2.length);
/* 124:    */             } else {
/* 125:125 */               paramPreparedStatement.setBytes(i + 1, (byte[])localObject2);
/* 126:    */             }
/* 127:    */           }
/* 128:    */           else
/* 129:    */           {
/* 130:128 */             throw new SQLException("Error: unsupported datatype");
/* 131:    */           }
/* 132:    */         }
/* 133:    */       }
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   public static String parseStmt(String paramString, int paramInt)
/* 138:    */   {
/* 139:152 */     StringBuffer localStringBuffer = new StringBuffer(paramString.length());
/* 140:    */     
/* 141:154 */     int k = 0;
/* 142:    */     for (;;)
/* 143:    */     {
/* 144:156 */       int i = paramString.indexOf("{", k);
/* 145:157 */       if (i == -1) {
/* 146:    */         break;
/* 147:    */       }
/* 148:158 */       int m = paramString.indexOf("}", i);
/* 149:159 */       localStringBuffer = localStringBuffer.append(paramString.substring(k, i));
/* 150:160 */       int j = i;
/* 151:161 */       int i2 = 1;
/* 152:162 */       for (int n = 1; n < paramInt; n++)
/* 153:    */       {
/* 154:163 */         j = paramString.indexOf("|", j + 1);
/* 155:164 */         if ((j == -1) || (j > m))
/* 156:    */         {
/* 157:165 */           i2 = 0;
/* 158:166 */           break;
/* 159:    */         }
/* 160:168 */         if (paramString.charAt(j - 1) == '!') {
/* 161:169 */           n--;
/* 162:    */         }
/* 163:    */       }
/* 164:174 */       if (i2 == 0)
/* 165:    */       {
/* 166:175 */         k = m + 1;
/* 167:    */       }
/* 168:    */       else
/* 169:    */       {
/* 170:179 */         j++;
/* 171:    */         for (;;)
/* 172:    */         {
/* 173:182 */           int i1 = paramString.indexOf("|", j);
/* 174:183 */           if ((i1 == -1) || (i1 > m))
/* 175:    */           {
/* 176:186 */             localStringBuffer = localStringBuffer.append(paramString.substring(j, m));
/* 177:187 */             break;
/* 178:    */           }
/* 179:188 */           if (paramString.charAt(i1 - 1) == '!')
/* 180:    */           {
/* 181:191 */             localStringBuffer = localStringBuffer.append(paramString.substring(j, i1 - 1));
/* 182:192 */             localStringBuffer = localStringBuffer.append('|');
/* 183:    */           }
/* 184:    */           else
/* 185:    */           {
/* 186:196 */             localStringBuffer = localStringBuffer.append(paramString.substring(j, i1));
/* 187:197 */             break;
/* 188:    */           }
/* 189:199 */           j = i1 + 1;
/* 190:    */         }
/* 191:201 */         k = m + 1;
/* 192:    */       }
/* 193:    */     }
/* 194:    */     String str;
/* 195:205 */     if (k > 0)
/* 196:    */     {
/* 197:206 */       localStringBuffer = localStringBuffer.append(paramString.substring(k, paramString.length()));
/* 198:207 */       str = localStringBuffer.toString();
/* 199:    */     }
/* 200:    */     else
/* 201:    */     {
/* 202:209 */       str = paramString;
/* 203:    */     }
/* 204:211 */     return str;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public static final SQLException getRealSQLException(SQLException paramSQLException)
/* 208:    */   {
/* 209:220 */     StringWriter localStringWriter = new StringWriter();
/* 210:221 */     PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
/* 211:222 */     paramSQLException.printStackTrace(localPrintWriter);
/* 212:223 */     localPrintWriter.flush();
/* 213:224 */     localPrintWriter.close();
/* 214:225 */     return new SQLException(localStringWriter.toString(), paramSQLException.getSQLState(), paramSQLException.getErrorCode());
/* 215:    */   }
/* 216:    */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.db.DBSqlUtils
 * JD-Core Version:    0.7.0.1
 */