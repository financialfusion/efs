/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*   2:    */ 
/*   3:    */ import org.omg.CORBA.BAD_PARAM;
/*   4:    */ import org.omg.CORBA.portable.IDLEntity;
/*   5:    */ 
/*   6:    */ public final class EnumCurrencyEnum
/*   7:    */   implements IDLEntity
/*   8:    */ {
/*   9:    */   private int _enum;
/*  10:    */   public static final int _ADP = 0;
/*  11:    */   
/*  12:    */   private EnumCurrencyEnum(int paramInt)
/*  13:    */   {
/*  14: 18 */     this._enum = paramInt;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public int value()
/*  18:    */   {
/*  19: 23 */     return this._enum;
/*  20:    */   }
/*  21:    */   
/*  22: 28 */   public static final EnumCurrencyEnum ADP = new EnumCurrencyEnum(0);
/*  23:    */   public static final int _AED = 1;
/*  24: 32 */   public static final EnumCurrencyEnum AED = new EnumCurrencyEnum(1);
/*  25:    */   public static final int _AFA = 2;
/*  26: 36 */   public static final EnumCurrencyEnum AFA = new EnumCurrencyEnum(2);
/*  27:    */   public static final int _ALL = 3;
/*  28: 40 */   public static final EnumCurrencyEnum ALL = new EnumCurrencyEnum(3);
/*  29:    */   public static final int _ANG = 4;
/*  30: 44 */   public static final EnumCurrencyEnum ANG = new EnumCurrencyEnum(4);
/*  31:    */   public static final int _AOK = 5;
/*  32: 48 */   public static final EnumCurrencyEnum AOK = new EnumCurrencyEnum(5);
/*  33:    */   public static final int _ARA = 6;
/*  34: 52 */   public static final EnumCurrencyEnum ARA = new EnumCurrencyEnum(6);
/*  35:    */   public static final int _ATS = 7;
/*  36: 56 */   public static final EnumCurrencyEnum ATS = new EnumCurrencyEnum(7);
/*  37:    */   public static final int _AUD = 8;
/*  38: 60 */   public static final EnumCurrencyEnum AUD = new EnumCurrencyEnum(8);
/*  39:    */   public static final int _AWG = 9;
/*  40: 64 */   public static final EnumCurrencyEnum AWG = new EnumCurrencyEnum(9);
/*  41:    */   public static final int _BBD = 10;
/*  42: 68 */   public static final EnumCurrencyEnum BBD = new EnumCurrencyEnum(10);
/*  43:    */   public static final int _BDT = 11;
/*  44: 72 */   public static final EnumCurrencyEnum BDT = new EnumCurrencyEnum(11);
/*  45:    */   public static final int _BEF = 12;
/*  46: 76 */   public static final EnumCurrencyEnum BEF = new EnumCurrencyEnum(12);
/*  47:    */   public static final int _BGL = 13;
/*  48: 80 */   public static final EnumCurrencyEnum BGL = new EnumCurrencyEnum(13);
/*  49:    */   public static final int _BHD = 14;
/*  50: 84 */   public static final EnumCurrencyEnum BHD = new EnumCurrencyEnum(14);
/*  51:    */   public static final int _BIF = 15;
/*  52: 88 */   public static final EnumCurrencyEnum BIF = new EnumCurrencyEnum(15);
/*  53:    */   public static final int _BMD = 16;
/*  54: 92 */   public static final EnumCurrencyEnum BMD = new EnumCurrencyEnum(16);
/*  55:    */   public static final int _BND = 17;
/*  56: 96 */   public static final EnumCurrencyEnum BND = new EnumCurrencyEnum(17);
/*  57:    */   public static final int _BOB = 18;
/*  58:100 */   public static final EnumCurrencyEnum BOB = new EnumCurrencyEnum(18);
/*  59:    */   public static final int _BRC = 19;
/*  60:104 */   public static final EnumCurrencyEnum BRC = new EnumCurrencyEnum(19);
/*  61:    */   public static final int _BSD = 20;
/*  62:108 */   public static final EnumCurrencyEnum BSD = new EnumCurrencyEnum(20);
/*  63:    */   public static final int _BTN = 21;
/*  64:112 */   public static final EnumCurrencyEnum BTN = new EnumCurrencyEnum(21);
/*  65:    */   public static final int _BUK = 22;
/*  66:116 */   public static final EnumCurrencyEnum BUK = new EnumCurrencyEnum(22);
/*  67:    */   public static final int _BWP = 23;
/*  68:120 */   public static final EnumCurrencyEnum BWP = new EnumCurrencyEnum(23);
/*  69:    */   public static final int _BZD = 24;
/*  70:124 */   public static final EnumCurrencyEnum BZD = new EnumCurrencyEnum(24);
/*  71:    */   public static final int _CAD = 25;
/*  72:128 */   public static final EnumCurrencyEnum CAD = new EnumCurrencyEnum(25);
/*  73:    */   public static final int _CHF = 26;
/*  74:132 */   public static final EnumCurrencyEnum CHF = new EnumCurrencyEnum(26);
/*  75:    */   public static final int _CLF = 27;
/*  76:136 */   public static final EnumCurrencyEnum CLF = new EnumCurrencyEnum(27);
/*  77:    */   public static final int _CLP = 28;
/*  78:140 */   public static final EnumCurrencyEnum CLP = new EnumCurrencyEnum(28);
/*  79:    */   public static final int _CNY = 29;
/*  80:144 */   public static final EnumCurrencyEnum CNY = new EnumCurrencyEnum(29);
/*  81:    */   public static final int _COP = 30;
/*  82:148 */   public static final EnumCurrencyEnum COP = new EnumCurrencyEnum(30);
/*  83:    */   public static final int _CRC = 31;
/*  84:152 */   public static final EnumCurrencyEnum CRC = new EnumCurrencyEnum(31);
/*  85:    */   public static final int _CSK = 32;
/*  86:156 */   public static final EnumCurrencyEnum CSK = new EnumCurrencyEnum(32);
/*  87:    */   public static final int _CUP = 33;
/*  88:160 */   public static final EnumCurrencyEnum CUP = new EnumCurrencyEnum(33);
/*  89:    */   public static final int _CVE = 34;
/*  90:164 */   public static final EnumCurrencyEnum CVE = new EnumCurrencyEnum(34);
/*  91:    */   public static final int _CYP = 35;
/*  92:168 */   public static final EnumCurrencyEnum CYP = new EnumCurrencyEnum(35);
/*  93:    */   public static final int _DDM = 36;
/*  94:172 */   public static final EnumCurrencyEnum DDM = new EnumCurrencyEnum(36);
/*  95:    */   public static final int _DEM = 37;
/*  96:176 */   public static final EnumCurrencyEnum DEM = new EnumCurrencyEnum(37);
/*  97:    */   public static final int _DJF = 38;
/*  98:180 */   public static final EnumCurrencyEnum DJF = new EnumCurrencyEnum(38);
/*  99:    */   public static final int _DKK = 39;
/* 100:184 */   public static final EnumCurrencyEnum DKK = new EnumCurrencyEnum(39);
/* 101:    */   public static final int _DOP = 40;
/* 102:188 */   public static final EnumCurrencyEnum DOP = new EnumCurrencyEnum(40);
/* 103:    */   public static final int _DZD = 41;
/* 104:192 */   public static final EnumCurrencyEnum DZD = new EnumCurrencyEnum(41);
/* 105:    */   public static final int _ECS = 42;
/* 106:196 */   public static final EnumCurrencyEnum ECS = new EnumCurrencyEnum(42);
/* 107:    */   public static final int _EGP = 43;
/* 108:200 */   public static final EnumCurrencyEnum EGP = new EnumCurrencyEnum(43);
/* 109:    */   public static final int _ESP = 44;
/* 110:204 */   public static final EnumCurrencyEnum ESP = new EnumCurrencyEnum(44);
/* 111:    */   public static final int _ETB = 45;
/* 112:208 */   public static final EnumCurrencyEnum ETB = new EnumCurrencyEnum(45);
/* 113:    */   public static final int _EUR = 46;
/* 114:212 */   public static final EnumCurrencyEnum EUR = new EnumCurrencyEnum(46);
/* 115:    */   public static final int _FIM = 47;
/* 116:216 */   public static final EnumCurrencyEnum FIM = new EnumCurrencyEnum(47);
/* 117:    */   public static final int _FJD = 48;
/* 118:220 */   public static final EnumCurrencyEnum FJD = new EnumCurrencyEnum(48);
/* 119:    */   public static final int _FKP = 49;
/* 120:224 */   public static final EnumCurrencyEnum FKP = new EnumCurrencyEnum(49);
/* 121:    */   public static final int _FRF = 50;
/* 122:228 */   public static final EnumCurrencyEnum FRF = new EnumCurrencyEnum(50);
/* 123:    */   public static final int _GBP = 51;
/* 124:232 */   public static final EnumCurrencyEnum GBP = new EnumCurrencyEnum(51);
/* 125:    */   public static final int _GHC = 52;
/* 126:236 */   public static final EnumCurrencyEnum GHC = new EnumCurrencyEnum(52);
/* 127:    */   public static final int _GIP = 53;
/* 128:240 */   public static final EnumCurrencyEnum GIP = new EnumCurrencyEnum(53);
/* 129:    */   public static final int _GMD = 54;
/* 130:244 */   public static final EnumCurrencyEnum GMD = new EnumCurrencyEnum(54);
/* 131:    */   public static final int _GNF = 55;
/* 132:248 */   public static final EnumCurrencyEnum GNF = new EnumCurrencyEnum(55);
/* 133:    */   public static final int _GRD = 56;
/* 134:252 */   public static final EnumCurrencyEnum GRD = new EnumCurrencyEnum(56);
/* 135:    */   public static final int _GTQ = 57;
/* 136:256 */   public static final EnumCurrencyEnum GTQ = new EnumCurrencyEnum(57);
/* 137:    */   public static final int _GWP = 58;
/* 138:260 */   public static final EnumCurrencyEnum GWP = new EnumCurrencyEnum(58);
/* 139:    */   public static final int _GYD = 59;
/* 140:264 */   public static final EnumCurrencyEnum GYD = new EnumCurrencyEnum(59);
/* 141:    */   public static final int _HKD = 60;
/* 142:268 */   public static final EnumCurrencyEnum HKD = new EnumCurrencyEnum(60);
/* 143:    */   public static final int _HNL = 61;
/* 144:272 */   public static final EnumCurrencyEnum HNL = new EnumCurrencyEnum(61);
/* 145:    */   public static final int _HTG = 62;
/* 146:276 */   public static final EnumCurrencyEnum HTG = new EnumCurrencyEnum(62);
/* 147:    */   public static final int _HUF = 63;
/* 148:280 */   public static final EnumCurrencyEnum HUF = new EnumCurrencyEnum(63);
/* 149:    */   public static final int _IDR = 64;
/* 150:284 */   public static final EnumCurrencyEnum IDR = new EnumCurrencyEnum(64);
/* 151:    */   public static final int _IEP = 65;
/* 152:288 */   public static final EnumCurrencyEnum IEP = new EnumCurrencyEnum(65);
/* 153:    */   public static final int _ILS = 66;
/* 154:292 */   public static final EnumCurrencyEnum ILS = new EnumCurrencyEnum(66);
/* 155:    */   public static final int _INR = 67;
/* 156:296 */   public static final EnumCurrencyEnum INR = new EnumCurrencyEnum(67);
/* 157:    */   public static final int _IQD = 68;
/* 158:300 */   public static final EnumCurrencyEnum IQD = new EnumCurrencyEnum(68);
/* 159:    */   public static final int _IRR = 69;
/* 160:304 */   public static final EnumCurrencyEnum IRR = new EnumCurrencyEnum(69);
/* 161:    */   public static final int _ISK = 70;
/* 162:308 */   public static final EnumCurrencyEnum ISK = new EnumCurrencyEnum(70);
/* 163:    */   public static final int _ITL = 71;
/* 164:312 */   public static final EnumCurrencyEnum ITL = new EnumCurrencyEnum(71);
/* 165:    */   public static final int _JMD = 72;
/* 166:316 */   public static final EnumCurrencyEnum JMD = new EnumCurrencyEnum(72);
/* 167:    */   public static final int _JOD = 73;
/* 168:320 */   public static final EnumCurrencyEnum JOD = new EnumCurrencyEnum(73);
/* 169:    */   public static final int _JPY = 74;
/* 170:324 */   public static final EnumCurrencyEnum JPY = new EnumCurrencyEnum(74);
/* 171:    */   public static final int _KES = 75;
/* 172:328 */   public static final EnumCurrencyEnum KES = new EnumCurrencyEnum(75);
/* 173:    */   public static final int _KHR = 76;
/* 174:332 */   public static final EnumCurrencyEnum KHR = new EnumCurrencyEnum(76);
/* 175:    */   public static final int _KMF = 77;
/* 176:336 */   public static final EnumCurrencyEnum KMF = new EnumCurrencyEnum(77);
/* 177:    */   public static final int _KPW = 78;
/* 178:340 */   public static final EnumCurrencyEnum KPW = new EnumCurrencyEnum(78);
/* 179:    */   public static final int _KRW = 79;
/* 180:344 */   public static final EnumCurrencyEnum KRW = new EnumCurrencyEnum(79);
/* 181:    */   public static final int _KWD = 80;
/* 182:348 */   public static final EnumCurrencyEnum KWD = new EnumCurrencyEnum(80);
/* 183:    */   public static final int _KYD = 81;
/* 184:352 */   public static final EnumCurrencyEnum KYD = new EnumCurrencyEnum(81);
/* 185:    */   public static final int _LAK = 82;
/* 186:356 */   public static final EnumCurrencyEnum LAK = new EnumCurrencyEnum(82);
/* 187:    */   public static final int _LBP = 83;
/* 188:360 */   public static final EnumCurrencyEnum LBP = new EnumCurrencyEnum(83);
/* 189:    */   public static final int _LKR = 84;
/* 190:364 */   public static final EnumCurrencyEnum LKR = new EnumCurrencyEnum(84);
/* 191:    */   public static final int _LRD = 85;
/* 192:368 */   public static final EnumCurrencyEnum LRD = new EnumCurrencyEnum(85);
/* 193:    */   public static final int _LSL = 86;
/* 194:372 */   public static final EnumCurrencyEnum LSL = new EnumCurrencyEnum(86);
/* 195:    */   public static final int _LUF = 87;
/* 196:376 */   public static final EnumCurrencyEnum LUF = new EnumCurrencyEnum(87);
/* 197:    */   public static final int _LYD = 88;
/* 198:380 */   public static final EnumCurrencyEnum LYD = new EnumCurrencyEnum(88);
/* 199:    */   public static final int _MAD = 89;
/* 200:384 */   public static final EnumCurrencyEnum MAD = new EnumCurrencyEnum(89);
/* 201:    */   public static final int _MGF = 90;
/* 202:388 */   public static final EnumCurrencyEnum MGF = new EnumCurrencyEnum(90);
/* 203:    */   public static final int _MNT = 91;
/* 204:392 */   public static final EnumCurrencyEnum MNT = new EnumCurrencyEnum(91);
/* 205:    */   public static final int _MOP = 92;
/* 206:396 */   public static final EnumCurrencyEnum MOP = new EnumCurrencyEnum(92);
/* 207:    */   public static final int _MRO = 93;
/* 208:400 */   public static final EnumCurrencyEnum MRO = new EnumCurrencyEnum(93);
/* 209:    */   public static final int _MTL = 94;
/* 210:404 */   public static final EnumCurrencyEnum MTL = new EnumCurrencyEnum(94);
/* 211:    */   public static final int _MUR = 95;
/* 212:408 */   public static final EnumCurrencyEnum MUR = new EnumCurrencyEnum(95);
/* 213:    */   public static final int _MVR = 96;
/* 214:412 */   public static final EnumCurrencyEnum MVR = new EnumCurrencyEnum(96);
/* 215:    */   public static final int _MWK = 97;
/* 216:416 */   public static final EnumCurrencyEnum MWK = new EnumCurrencyEnum(97);
/* 217:    */   public static final int _MXP = 98;
/* 218:420 */   public static final EnumCurrencyEnum MXP = new EnumCurrencyEnum(98);
/* 219:    */   public static final int _MYR = 99;
/* 220:424 */   public static final EnumCurrencyEnum MYR = new EnumCurrencyEnum(99);
/* 221:    */   public static final int _MZM = 100;
/* 222:428 */   public static final EnumCurrencyEnum MZM = new EnumCurrencyEnum(100);
/* 223:    */   public static final int _NGN = 101;
/* 224:432 */   public static final EnumCurrencyEnum NGN = new EnumCurrencyEnum(101);
/* 225:    */   public static final int _NIC = 102;
/* 226:436 */   public static final EnumCurrencyEnum NIC = new EnumCurrencyEnum(102);
/* 227:    */   public static final int _NLG = 103;
/* 228:440 */   public static final EnumCurrencyEnum NLG = new EnumCurrencyEnum(103);
/* 229:    */   public static final int _NOK = 104;
/* 230:444 */   public static final EnumCurrencyEnum NOK = new EnumCurrencyEnum(104);
/* 231:    */   public static final int _NPR = 105;
/* 232:448 */   public static final EnumCurrencyEnum NPR = new EnumCurrencyEnum(105);
/* 233:    */   public static final int _NZD = 106;
/* 234:452 */   public static final EnumCurrencyEnum NZD = new EnumCurrencyEnum(106);
/* 235:    */   public static final int _OMR = 107;
/* 236:456 */   public static final EnumCurrencyEnum OMR = new EnumCurrencyEnum(107);
/* 237:    */   public static final int _PAB = 108;
/* 238:460 */   public static final EnumCurrencyEnum PAB = new EnumCurrencyEnum(108);
/* 239:    */   public static final int _PEI = 109;
/* 240:464 */   public static final EnumCurrencyEnum PEI = new EnumCurrencyEnum(109);
/* 241:    */   public static final int _PGK = 110;
/* 242:468 */   public static final EnumCurrencyEnum PGK = new EnumCurrencyEnum(110);
/* 243:    */   public static final int _PHP = 111;
/* 244:472 */   public static final EnumCurrencyEnum PHP = new EnumCurrencyEnum(111);
/* 245:    */   public static final int _PKR = 112;
/* 246:476 */   public static final EnumCurrencyEnum PKR = new EnumCurrencyEnum(112);
/* 247:    */   public static final int _PLZ = 113;
/* 248:480 */   public static final EnumCurrencyEnum PLZ = new EnumCurrencyEnum(113);
/* 249:    */   public static final int _PTE = 114;
/* 250:484 */   public static final EnumCurrencyEnum PTE = new EnumCurrencyEnum(114);
/* 251:    */   public static final int _PYG = 115;
/* 252:488 */   public static final EnumCurrencyEnum PYG = new EnumCurrencyEnum(115);
/* 253:    */   public static final int _QAR = 116;
/* 254:492 */   public static final EnumCurrencyEnum QAR = new EnumCurrencyEnum(116);
/* 255:    */   public static final int _ROL = 117;
/* 256:496 */   public static final EnumCurrencyEnum ROL = new EnumCurrencyEnum(117);
/* 257:    */   public static final int _RWF = 118;
/* 258:500 */   public static final EnumCurrencyEnum RWF = new EnumCurrencyEnum(118);
/* 259:    */   public static final int _SAR = 119;
/* 260:504 */   public static final EnumCurrencyEnum SAR = new EnumCurrencyEnum(119);
/* 261:    */   public static final int _SBD = 120;
/* 262:508 */   public static final EnumCurrencyEnum SBD = new EnumCurrencyEnum(120);
/* 263:    */   public static final int _SCR = 121;
/* 264:512 */   public static final EnumCurrencyEnum SCR = new EnumCurrencyEnum(121);
/* 265:    */   public static final int _SDP = 122;
/* 266:516 */   public static final EnumCurrencyEnum SDP = new EnumCurrencyEnum(122);
/* 267:    */   public static final int _SEK = 123;
/* 268:520 */   public static final EnumCurrencyEnum SEK = new EnumCurrencyEnum(123);
/* 269:    */   public static final int _SGD = 124;
/* 270:524 */   public static final EnumCurrencyEnum SGD = new EnumCurrencyEnum(124);
/* 271:    */   public static final int _SHP = 125;
/* 272:528 */   public static final EnumCurrencyEnum SHP = new EnumCurrencyEnum(125);
/* 273:    */   public static final int _SLL = 126;
/* 274:532 */   public static final EnumCurrencyEnum SLL = new EnumCurrencyEnum(126);
/* 275:    */   public static final int _SOS = 127;
/* 276:536 */   public static final EnumCurrencyEnum SOS = new EnumCurrencyEnum(127);
/* 277:    */   public static final int _SRG = 128;
/* 278:540 */   public static final EnumCurrencyEnum SRG = new EnumCurrencyEnum(128);
/* 279:    */   public static final int _STD = 129;
/* 280:544 */   public static final EnumCurrencyEnum STD = new EnumCurrencyEnum(129);
/* 281:    */   public static final int _SUR = 130;
/* 282:548 */   public static final EnumCurrencyEnum SUR = new EnumCurrencyEnum(130);
/* 283:    */   public static final int _SVC = 131;
/* 284:552 */   public static final EnumCurrencyEnum SVC = new EnumCurrencyEnum(131);
/* 285:    */   public static final int _SYP = 132;
/* 286:556 */   public static final EnumCurrencyEnum SYP = new EnumCurrencyEnum(132);
/* 287:    */   public static final int _SZL = 133;
/* 288:560 */   public static final EnumCurrencyEnum SZL = new EnumCurrencyEnum(133);
/* 289:    */   public static final int _THB = 134;
/* 290:564 */   public static final EnumCurrencyEnum THB = new EnumCurrencyEnum(134);
/* 291:    */   public static final int _TND = 135;
/* 292:568 */   public static final EnumCurrencyEnum TND = new EnumCurrencyEnum(135);
/* 293:    */   public static final int _TOP = 136;
/* 294:572 */   public static final EnumCurrencyEnum TOP = new EnumCurrencyEnum(136);
/* 295:    */   public static final int _TPE = 137;
/* 296:576 */   public static final EnumCurrencyEnum TPE = new EnumCurrencyEnum(137);
/* 297:    */   public static final int _TRL = 138;
/* 298:580 */   public static final EnumCurrencyEnum TRL = new EnumCurrencyEnum(138);
/* 299:    */   public static final int _TTD = 139;
/* 300:584 */   public static final EnumCurrencyEnum TTD = new EnumCurrencyEnum(139);
/* 301:    */   public static final int _TWD = 140;
/* 302:588 */   public static final EnumCurrencyEnum TWD = new EnumCurrencyEnum(140);
/* 303:    */   public static final int _TZS = 141;
/* 304:592 */   public static final EnumCurrencyEnum TZS = new EnumCurrencyEnum(141);
/* 305:    */   public static final int _UGS = 142;
/* 306:596 */   public static final EnumCurrencyEnum UGS = new EnumCurrencyEnum(142);
/* 307:    */   public static final int _USD = 143;
/* 308:600 */   public static final EnumCurrencyEnum USD = new EnumCurrencyEnum(143);
/* 309:    */   public static final int _UYP = 144;
/* 310:604 */   public static final EnumCurrencyEnum UYP = new EnumCurrencyEnum(144);
/* 311:    */   public static final int _VEB = 145;
/* 312:608 */   public static final EnumCurrencyEnum VEB = new EnumCurrencyEnum(145);
/* 313:    */   public static final int _VND = 146;
/* 314:612 */   public static final EnumCurrencyEnum VND = new EnumCurrencyEnum(146);
/* 315:    */   public static final int _VUV = 147;
/* 316:616 */   public static final EnumCurrencyEnum VUV = new EnumCurrencyEnum(147);
/* 317:    */   public static final int _WST = 148;
/* 318:620 */   public static final EnumCurrencyEnum WST = new EnumCurrencyEnum(148);
/* 319:    */   public static final int _YDD = 149;
/* 320:624 */   public static final EnumCurrencyEnum YDD = new EnumCurrencyEnum(149);
/* 321:    */   public static final int _YER = 150;
/* 322:628 */   public static final EnumCurrencyEnum YER = new EnumCurrencyEnum(150);
/* 323:    */   public static final int _YUD = 151;
/* 324:632 */   public static final EnumCurrencyEnum YUD = new EnumCurrencyEnum(151);
/* 325:    */   public static final int _ZAR = 152;
/* 326:636 */   public static final EnumCurrencyEnum ZAR = new EnumCurrencyEnum(152);
/* 327:    */   public static final int _ZMK = 153;
/* 328:640 */   public static final EnumCurrencyEnum ZMK = new EnumCurrencyEnum(153);
/* 329:    */   public static final int _ZRZ = 154;
/* 330:644 */   public static final EnumCurrencyEnum ZRZ = new EnumCurrencyEnum(154);
/* 331:    */   public static final int _ZWD = 155;
/* 332:648 */   public static final EnumCurrencyEnum ZWD = new EnumCurrencyEnum(155);
/* 333:    */   
/* 334:    */   public static EnumCurrencyEnum from_int(int paramInt)
/* 335:    */   {
/* 336:653 */     switch (paramInt)
/* 337:    */     {
/* 338:    */     case 0: 
/* 339:655 */       return ADP;
/* 340:    */     case 1: 
/* 341:656 */       return AED;
/* 342:    */     case 2: 
/* 343:657 */       return AFA;
/* 344:    */     case 3: 
/* 345:658 */       return ALL;
/* 346:    */     case 4: 
/* 347:659 */       return ANG;
/* 348:    */     case 5: 
/* 349:660 */       return AOK;
/* 350:    */     case 6: 
/* 351:661 */       return ARA;
/* 352:    */     case 7: 
/* 353:662 */       return ATS;
/* 354:    */     case 8: 
/* 355:663 */       return AUD;
/* 356:    */     case 9: 
/* 357:664 */       return AWG;
/* 358:    */     case 10: 
/* 359:665 */       return BBD;
/* 360:    */     case 11: 
/* 361:666 */       return BDT;
/* 362:    */     case 12: 
/* 363:667 */       return BEF;
/* 364:    */     case 13: 
/* 365:668 */       return BGL;
/* 366:    */     case 14: 
/* 367:669 */       return BHD;
/* 368:    */     case 15: 
/* 369:670 */       return BIF;
/* 370:    */     case 16: 
/* 371:671 */       return BMD;
/* 372:    */     case 17: 
/* 373:672 */       return BND;
/* 374:    */     case 18: 
/* 375:673 */       return BOB;
/* 376:    */     case 19: 
/* 377:674 */       return BRC;
/* 378:    */     case 20: 
/* 379:675 */       return BSD;
/* 380:    */     case 21: 
/* 381:676 */       return BTN;
/* 382:    */     case 22: 
/* 383:677 */       return BUK;
/* 384:    */     case 23: 
/* 385:678 */       return BWP;
/* 386:    */     case 24: 
/* 387:679 */       return BZD;
/* 388:    */     case 25: 
/* 389:680 */       return CAD;
/* 390:    */     case 26: 
/* 391:681 */       return CHF;
/* 392:    */     case 27: 
/* 393:682 */       return CLF;
/* 394:    */     case 28: 
/* 395:683 */       return CLP;
/* 396:    */     case 29: 
/* 397:684 */       return CNY;
/* 398:    */     case 30: 
/* 399:685 */       return COP;
/* 400:    */     case 31: 
/* 401:686 */       return CRC;
/* 402:    */     case 32: 
/* 403:687 */       return CSK;
/* 404:    */     case 33: 
/* 405:688 */       return CUP;
/* 406:    */     case 34: 
/* 407:689 */       return CVE;
/* 408:    */     case 35: 
/* 409:690 */       return CYP;
/* 410:    */     case 36: 
/* 411:691 */       return DDM;
/* 412:    */     case 37: 
/* 413:692 */       return DEM;
/* 414:    */     case 38: 
/* 415:693 */       return DJF;
/* 416:    */     case 39: 
/* 417:694 */       return DKK;
/* 418:    */     case 40: 
/* 419:695 */       return DOP;
/* 420:    */     case 41: 
/* 421:696 */       return DZD;
/* 422:    */     case 42: 
/* 423:697 */       return ECS;
/* 424:    */     case 43: 
/* 425:698 */       return EGP;
/* 426:    */     case 44: 
/* 427:699 */       return ESP;
/* 428:    */     case 45: 
/* 429:700 */       return ETB;
/* 430:    */     case 46: 
/* 431:701 */       return EUR;
/* 432:    */     case 47: 
/* 433:702 */       return FIM;
/* 434:    */     case 48: 
/* 435:703 */       return FJD;
/* 436:    */     case 49: 
/* 437:704 */       return FKP;
/* 438:    */     case 50: 
/* 439:705 */       return FRF;
/* 440:    */     case 51: 
/* 441:706 */       return GBP;
/* 442:    */     case 52: 
/* 443:707 */       return GHC;
/* 444:    */     case 53: 
/* 445:708 */       return GIP;
/* 446:    */     case 54: 
/* 447:709 */       return GMD;
/* 448:    */     case 55: 
/* 449:710 */       return GNF;
/* 450:    */     case 56: 
/* 451:711 */       return GRD;
/* 452:    */     case 57: 
/* 453:712 */       return GTQ;
/* 454:    */     case 58: 
/* 455:713 */       return GWP;
/* 456:    */     case 59: 
/* 457:714 */       return GYD;
/* 458:    */     case 60: 
/* 459:715 */       return HKD;
/* 460:    */     case 61: 
/* 461:716 */       return HNL;
/* 462:    */     case 62: 
/* 463:717 */       return HTG;
/* 464:    */     case 63: 
/* 465:718 */       return HUF;
/* 466:    */     case 64: 
/* 467:719 */       return IDR;
/* 468:    */     case 65: 
/* 469:720 */       return IEP;
/* 470:    */     case 66: 
/* 471:721 */       return ILS;
/* 472:    */     case 67: 
/* 473:722 */       return INR;
/* 474:    */     case 68: 
/* 475:723 */       return IQD;
/* 476:    */     case 69: 
/* 477:724 */       return IRR;
/* 478:    */     case 70: 
/* 479:725 */       return ISK;
/* 480:    */     case 71: 
/* 481:726 */       return ITL;
/* 482:    */     case 72: 
/* 483:727 */       return JMD;
/* 484:    */     case 73: 
/* 485:728 */       return JOD;
/* 486:    */     case 74: 
/* 487:729 */       return JPY;
/* 488:    */     case 75: 
/* 489:730 */       return KES;
/* 490:    */     case 76: 
/* 491:731 */       return KHR;
/* 492:    */     case 77: 
/* 493:732 */       return KMF;
/* 494:    */     case 78: 
/* 495:733 */       return KPW;
/* 496:    */     case 79: 
/* 497:734 */       return KRW;
/* 498:    */     case 80: 
/* 499:735 */       return KWD;
/* 500:    */     case 81: 
/* 501:736 */       return KYD;
/* 502:    */     case 82: 
/* 503:737 */       return LAK;
/* 504:    */     case 83: 
/* 505:738 */       return LBP;
/* 506:    */     case 84: 
/* 507:739 */       return LKR;
/* 508:    */     case 85: 
/* 509:740 */       return LRD;
/* 510:    */     case 86: 
/* 511:741 */       return LSL;
/* 512:    */     case 87: 
/* 513:742 */       return LUF;
/* 514:    */     case 88: 
/* 515:743 */       return LYD;
/* 516:    */     case 89: 
/* 517:744 */       return MAD;
/* 518:    */     case 90: 
/* 519:745 */       return MGF;
/* 520:    */     case 91: 
/* 521:746 */       return MNT;
/* 522:    */     case 92: 
/* 523:747 */       return MOP;
/* 524:    */     case 93: 
/* 525:748 */       return MRO;
/* 526:    */     case 94: 
/* 527:749 */       return MTL;
/* 528:    */     case 95: 
/* 529:750 */       return MUR;
/* 530:    */     case 96: 
/* 531:751 */       return MVR;
/* 532:    */     case 97: 
/* 533:752 */       return MWK;
/* 534:    */     case 98: 
/* 535:753 */       return MXP;
/* 536:    */     case 99: 
/* 537:754 */       return MYR;
/* 538:    */     case 100: 
/* 539:755 */       return MZM;
/* 540:    */     case 101: 
/* 541:756 */       return NGN;
/* 542:    */     case 102: 
/* 543:757 */       return NIC;
/* 544:    */     case 103: 
/* 545:758 */       return NLG;
/* 546:    */     case 104: 
/* 547:759 */       return NOK;
/* 548:    */     case 105: 
/* 549:760 */       return NPR;
/* 550:    */     case 106: 
/* 551:761 */       return NZD;
/* 552:    */     case 107: 
/* 553:762 */       return OMR;
/* 554:    */     case 108: 
/* 555:763 */       return PAB;
/* 556:    */     case 109: 
/* 557:764 */       return PEI;
/* 558:    */     case 110: 
/* 559:765 */       return PGK;
/* 560:    */     case 111: 
/* 561:766 */       return PHP;
/* 562:    */     case 112: 
/* 563:767 */       return PKR;
/* 564:    */     case 113: 
/* 565:768 */       return PLZ;
/* 566:    */     case 114: 
/* 567:769 */       return PTE;
/* 568:    */     case 115: 
/* 569:770 */       return PYG;
/* 570:    */     case 116: 
/* 571:771 */       return QAR;
/* 572:    */     case 117: 
/* 573:772 */       return ROL;
/* 574:    */     case 118: 
/* 575:773 */       return RWF;
/* 576:    */     case 119: 
/* 577:774 */       return SAR;
/* 578:    */     case 120: 
/* 579:775 */       return SBD;
/* 580:    */     case 121: 
/* 581:776 */       return SCR;
/* 582:    */     case 122: 
/* 583:777 */       return SDP;
/* 584:    */     case 123: 
/* 585:778 */       return SEK;
/* 586:    */     case 124: 
/* 587:779 */       return SGD;
/* 588:    */     case 125: 
/* 589:780 */       return SHP;
/* 590:    */     case 126: 
/* 591:781 */       return SLL;
/* 592:    */     case 127: 
/* 593:782 */       return SOS;
/* 594:    */     case 128: 
/* 595:783 */       return SRG;
/* 596:    */     case 129: 
/* 597:784 */       return STD;
/* 598:    */     case 130: 
/* 599:785 */       return SUR;
/* 600:    */     case 131: 
/* 601:786 */       return SVC;
/* 602:    */     case 132: 
/* 603:787 */       return SYP;
/* 604:    */     case 133: 
/* 605:788 */       return SZL;
/* 606:    */     case 134: 
/* 607:789 */       return THB;
/* 608:    */     case 135: 
/* 609:790 */       return TND;
/* 610:    */     case 136: 
/* 611:791 */       return TOP;
/* 612:    */     case 137: 
/* 613:792 */       return TPE;
/* 614:    */     case 138: 
/* 615:793 */       return TRL;
/* 616:    */     case 139: 
/* 617:794 */       return TTD;
/* 618:    */     case 140: 
/* 619:795 */       return TWD;
/* 620:    */     case 141: 
/* 621:796 */       return TZS;
/* 622:    */     case 142: 
/* 623:797 */       return UGS;
/* 624:    */     case 143: 
/* 625:798 */       return USD;
/* 626:    */     case 144: 
/* 627:799 */       return UYP;
/* 628:    */     case 145: 
/* 629:800 */       return VEB;
/* 630:    */     case 146: 
/* 631:801 */       return VND;
/* 632:    */     case 147: 
/* 633:802 */       return VUV;
/* 634:    */     case 148: 
/* 635:803 */       return WST;
/* 636:    */     case 149: 
/* 637:804 */       return YDD;
/* 638:    */     case 150: 
/* 639:805 */       return YER;
/* 640:    */     case 151: 
/* 641:806 */       return YUD;
/* 642:    */     case 152: 
/* 643:807 */       return ZAR;
/* 644:    */     case 153: 
/* 645:808 */       return ZMK;
/* 646:    */     case 154: 
/* 647:809 */       return ZRZ;
/* 648:    */     case 155: 
/* 649:810 */       return ZWD;
/* 650:    */     }
/* 651:811 */     throw new BAD_PARAM();
/* 652:    */   }
/* 653:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnum
 * JD-Core Version:    0.7.0.1
 */