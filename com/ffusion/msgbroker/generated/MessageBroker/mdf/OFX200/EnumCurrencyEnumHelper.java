/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*   2:    */ 
/*   3:    */ import org.omg.CORBA.Any;
/*   4:    */ import org.omg.CORBA.BAD_OPERATION;
/*   5:    */ import org.omg.CORBA.ORB;
/*   6:    */ import org.omg.CORBA.TypeCode;
/*   7:    */ import org.omg.CORBA.portable.InputStream;
/*   8:    */ import org.omg.CORBA.portable.OutputStream;
/*   9:    */ 
/*  10:    */ public abstract class EnumCurrencyEnumHelper
/*  11:    */ {
/*  12:    */   public static TypeCode _type;
/*  13:    */   
/*  14:    */   public static EnumCurrencyEnum read(InputStream paramInputStream)
/*  15:    */   {
/*  16: 16 */     return EnumCurrencyEnum.from_int(paramInputStream.read_ulong());
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static void write(OutputStream paramOutputStream, EnumCurrencyEnum paramEnumCurrencyEnum)
/*  20:    */   {
/*  21: 23 */     if (paramEnumCurrencyEnum == null)
/*  22:    */     {
/*  23: 25 */       paramOutputStream.write_ulong(0);
/*  24: 26 */       return;
/*  25:    */     }
/*  26: 28 */     paramOutputStream.write_ulong(paramEnumCurrencyEnum.value());
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static String _idl()
/*  30:    */   {
/*  31: 33 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::EnumCurrencyEnum";
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static TypeCode type()
/*  35:    */   {
/*  36: 40 */     if (_type == null)
/*  37:    */     {
/*  38: 42 */       ORB localORB = ORB.init();
/*  39: 43 */       String[] arrayOfString = 
/*  40: 44 */         {
/*  41: 45 */         "ADP", 
/*  42: 46 */         "AED", 
/*  43: 47 */         "AFA", 
/*  44: 48 */         "ALL", 
/*  45: 49 */         "ANG", 
/*  46: 50 */         "AOK", 
/*  47: 51 */         "ARA", 
/*  48: 52 */         "ATS", 
/*  49: 53 */         "AUD", 
/*  50: 54 */         "AWG", 
/*  51: 55 */         "BBD", 
/*  52: 56 */         "BDT", 
/*  53: 57 */         "BEF", 
/*  54: 58 */         "BGL", 
/*  55: 59 */         "BHD", 
/*  56: 60 */         "BIF", 
/*  57: 61 */         "BMD", 
/*  58: 62 */         "BND", 
/*  59: 63 */         "BOB", 
/*  60: 64 */         "BRC", 
/*  61: 65 */         "BSD", 
/*  62: 66 */         "BTN", 
/*  63: 67 */         "BUK", 
/*  64: 68 */         "BWP", 
/*  65: 69 */         "BZD", 
/*  66: 70 */         "CAD", 
/*  67: 71 */         "CHF", 
/*  68: 72 */         "CLF", 
/*  69: 73 */         "CLP", 
/*  70: 74 */         "CNY", 
/*  71: 75 */         "COP", 
/*  72: 76 */         "CRC", 
/*  73: 77 */         "CSK", 
/*  74: 78 */         "CUP", 
/*  75: 79 */         "CVE", 
/*  76: 80 */         "CYP", 
/*  77: 81 */         "DDM", 
/*  78: 82 */         "DEM", 
/*  79: 83 */         "DJF", 
/*  80: 84 */         "DKK", 
/*  81: 85 */         "DOP", 
/*  82: 86 */         "DZD", 
/*  83: 87 */         "ECS", 
/*  84: 88 */         "EGP", 
/*  85: 89 */         "ESP", 
/*  86: 90 */         "ETB", 
/*  87: 91 */         "EUR", 
/*  88: 92 */         "FIM", 
/*  89: 93 */         "FJD", 
/*  90: 94 */         "FKP", 
/*  91: 95 */         "FRF", 
/*  92: 96 */         "GBP", 
/*  93: 97 */         "GHC", 
/*  94: 98 */         "GIP", 
/*  95: 99 */         "GMD", 
/*  96:100 */         "GNF", 
/*  97:101 */         "GRD", 
/*  98:102 */         "GTQ", 
/*  99:103 */         "GWP", 
/* 100:104 */         "GYD", 
/* 101:105 */         "HKD", 
/* 102:106 */         "HNL", 
/* 103:107 */         "HTG", 
/* 104:108 */         "HUF", 
/* 105:109 */         "IDR", 
/* 106:110 */         "IEP", 
/* 107:111 */         "ILS", 
/* 108:112 */         "INR", 
/* 109:113 */         "IQD", 
/* 110:114 */         "IRR", 
/* 111:115 */         "ISK", 
/* 112:116 */         "ITL", 
/* 113:117 */         "JMD", 
/* 114:118 */         "JOD", 
/* 115:119 */         "JPY", 
/* 116:120 */         "KES", 
/* 117:121 */         "KHR", 
/* 118:122 */         "KMF", 
/* 119:123 */         "KPW", 
/* 120:124 */         "KRW", 
/* 121:125 */         "KWD", 
/* 122:126 */         "KYD", 
/* 123:127 */         "LAK", 
/* 124:128 */         "LBP", 
/* 125:129 */         "LKR", 
/* 126:130 */         "LRD", 
/* 127:131 */         "LSL", 
/* 128:132 */         "LUF", 
/* 129:133 */         "LYD", 
/* 130:134 */         "MAD", 
/* 131:135 */         "MGF", 
/* 132:136 */         "MNT", 
/* 133:137 */         "MOP", 
/* 134:138 */         "MRO", 
/* 135:139 */         "MTL", 
/* 136:140 */         "MUR", 
/* 137:141 */         "MVR", 
/* 138:142 */         "MWK", 
/* 139:143 */         "MXP", 
/* 140:144 */         "MYR", 
/* 141:145 */         "MZM", 
/* 142:146 */         "NGN", 
/* 143:147 */         "NIC", 
/* 144:148 */         "NLG", 
/* 145:149 */         "NOK", 
/* 146:150 */         "NPR", 
/* 147:151 */         "NZD", 
/* 148:152 */         "OMR", 
/* 149:153 */         "PAB", 
/* 150:154 */         "PEI", 
/* 151:155 */         "PGK", 
/* 152:156 */         "PHP", 
/* 153:157 */         "PKR", 
/* 154:158 */         "PLZ", 
/* 155:159 */         "PTE", 
/* 156:160 */         "PYG", 
/* 157:161 */         "QAR", 
/* 158:162 */         "ROL", 
/* 159:163 */         "RWF", 
/* 160:164 */         "SAR", 
/* 161:165 */         "SBD", 
/* 162:166 */         "SCR", 
/* 163:167 */         "SDP", 
/* 164:168 */         "SEK", 
/* 165:169 */         "SGD", 
/* 166:170 */         "SHP", 
/* 167:171 */         "SLL", 
/* 168:172 */         "SOS", 
/* 169:173 */         "SRG", 
/* 170:174 */         "STD", 
/* 171:175 */         "SUR", 
/* 172:176 */         "SVC", 
/* 173:177 */         "SYP", 
/* 174:178 */         "SZL", 
/* 175:179 */         "THB", 
/* 176:180 */         "TND", 
/* 177:181 */         "TOP", 
/* 178:182 */         "TPE", 
/* 179:183 */         "TRL", 
/* 180:184 */         "TTD", 
/* 181:185 */         "TWD", 
/* 182:186 */         "TZS", 
/* 183:187 */         "UGS", 
/* 184:188 */         "USD", 
/* 185:189 */         "UYP", 
/* 186:190 */         "VEB", 
/* 187:191 */         "VND", 
/* 188:192 */         "VUV", 
/* 189:193 */         "WST", 
/* 190:194 */         "YDD", 
/* 191:195 */         "YER", 
/* 192:196 */         "YUD", 
/* 193:197 */         "ZAR", 
/* 194:198 */         "ZMK", 
/* 195:199 */         "ZRZ", 
/* 196:200 */         "ZWD" };
/* 197:    */       
/* 198:202 */       _type = localORB.create_enum_tc(id(), "EnumCurrencyEnum", arrayOfString);
/* 199:    */     }
/* 200:204 */     return _type;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public static void insert(Any paramAny, EnumCurrencyEnum paramEnumCurrencyEnum)
/* 204:    */   {
/* 205:211 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 206:212 */     write(localOutputStream, paramEnumCurrencyEnum);
/* 207:213 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 208:    */   }
/* 209:    */   
/* 210:    */   public static EnumCurrencyEnum extract(Any paramAny)
/* 211:    */   {
/* 212:219 */     if (!paramAny.type().equal(type())) {
/* 213:221 */       throw new BAD_OPERATION();
/* 214:    */     }
/* 215:223 */     return read(paramAny.create_input_stream());
/* 216:    */   }
/* 217:    */   
/* 218:    */   public static String id()
/* 219:    */   {
/* 220:228 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/EnumCurrencyEnum:1.0";
/* 221:    */   }
/* 222:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnumHelper
 * JD-Core Version:    0.7.0.1
 */