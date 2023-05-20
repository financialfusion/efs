/*   1:    */ package com.ffusion.util;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.text.DateFormat;
/*   6:    */ import java.text.DateFormatSymbols;
/*   7:    */ import java.text.DecimalFormat;
/*   8:    */ import java.text.FieldPosition;
/*   9:    */ import java.text.MessageFormat;
/*  10:    */ import java.text.NumberFormat;
/*  11:    */ import java.text.ParseException;
/*  12:    */ import java.text.ParsePosition;
/*  13:    */ import java.util.Calendar;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.Hashtable;
/*  16:    */ import java.util.Locale;
/*  17:    */ import java.util.MissingResourceException;
/*  18:    */ import java.util.ResourceBundle;
/*  19:    */ import java.util.SimpleTimeZone;
/*  20:    */ import java.util.TimeZone;
/*  21:    */ 
/*  22:    */ public class FFIDateFormat
/*  23:    */   extends DateFormat
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   static final String _fldint = "GyMdkHmsSEDFwWahKz";
/*  27:    */   private Locale b;
/*  28:    */   static final long _fldcase = 4774881970558875024L;
/*  29:    */   static final int _flddo = 1;
/*  30:    */   private int _fldgoto;
/*  31:    */   private String c;
/*  32:    */   private DateFormatSymbols _fldlong;
/*  33:    */   private Date d;
/*  34:    */   private transient int _fldvoid;
/*  35:    */   private static final int _fldbyte = 3600000;
/*  36:    */   private static final int _fldfor = 60000;
/*  37:    */   private static final String _fldchar = "GMT+";
/*  38:    */   private static final String e = "GMT-";
/*  39:    */   private static final String _fldnull = "GMT";
/*  40:    */   
/*  41:    */   public FFIDateFormat()
/*  42:    */   {
/*  43: 18 */     this(3, 3, LocaleUtil.getDefaultLocale());
/*  44:    */   }
/*  45:    */   
/*  46:    */   public FFIDateFormat(String s)
/*  47:    */   {
/*  48: 23 */     this(s, LocaleUtil.getDefaultLocale());
/*  49:    */   }
/*  50:    */   
/*  51:    */   public FFIDateFormat(String s, Locale locale)
/*  52:    */   {
/*  53: 28 */     this._fldgoto = 1;
/*  54: 29 */     this.c = s;
/*  55: 30 */     this._fldlong = new DateFormatSymbols(locale);
/*  56: 31 */     a(locale);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public FFIDateFormat(String s, Locale locale, TimeZone timezone)
/*  60:    */   {
/*  61: 36 */     this(s, locale);
/*  62: 37 */     super.setTimeZone(timezone);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public FFIDateFormat(String s, DateFormatSymbols dateformatsymbols)
/*  66:    */   {
/*  67: 42 */     this._fldgoto = 1;
/*  68: 43 */     this.c = s;
/*  69: 44 */     this._fldlong = ((DateFormatSymbols)dateformatsymbols.clone());
/*  70: 45 */     a(LocaleUtil.getDefaultLocale());
/*  71:    */   }
/*  72:    */   
/*  73:    */   FFIDateFormat(int i, int j, Locale locale)
/*  74:    */   {
/*  75: 50 */     this._fldgoto = 1;
/*  76: 51 */     String[] as = (String[])_fldnew.get(locale);
/*  77: 52 */     if (as == null)
/*  78:    */     {
/*  79: 54 */       ResourceBundle resourcebundle = null;
/*  80:    */       try
/*  81:    */       {
/*  82: 57 */         resourcebundle = ResourceBundle.getBundle("java.text.resources.LocaleElements", locale);
/*  83:    */       }
/*  84:    */       catch (MissingResourceException missingresourceexception)
/*  85:    */       {
/*  86:    */         try
/*  87:    */         {
/*  88: 63 */           resourcebundle = ResourceBundle.getBundle("sun.text.resources.LocaleElements", locale);
/*  89:    */         }
/*  90:    */         catch (MissingResourceException missingresourceexception1)
/*  91:    */         {
/*  92: 67 */           resourcebundle = ResourceBundle.getBundle("com.ibm.security.util.text.resources.LocaleElements", locale);
/*  93:    */         }
/*  94:    */       }
/*  95: 70 */       as = resourcebundle.getStringArray("DateTimePatterns");
/*  96: 71 */       _fldnew.put(locale, as);
/*  97:    */     }
/*  98: 73 */     this._fldlong = new DateFormatSymbols(locale);
/*  99: 74 */     if ((i >= 0) && (j >= 0))
/* 100:    */     {
/* 101: 76 */       Object[] aobj = 
/* 102: 77 */         { as[i], as[(j + 4)] };
/* 103: 78 */       this.c = MessageFormat.format(as[8], aobj);
/* 104:    */     }
/* 105: 80 */     else if (i >= 0)
/* 106:    */     {
/* 107: 81 */       this.c = as[i];
/* 108:    */     }
/* 109: 82 */     else if (j >= 0)
/* 110:    */     {
/* 111: 83 */       this.c = as[(j + 4)];
/* 112:    */     }
/* 113:    */     else
/* 114:    */     {
/* 115: 85 */       throw new IllegalArgumentException("No date or time style specified");
/* 116:    */     }
/* 117: 86 */     a(locale);
/* 118:    */   }
/* 119:    */   
/* 120:    */   FFIDateFormat(int i, int j, Locale locale, TimeZone timezone)
/* 121:    */   {
/* 122: 91 */     this(i, j, locale);
/* 123: 92 */     super.setTimeZone(timezone);
/* 124:    */   }
/* 125:    */   
/* 126:    */   private void a(Locale locale)
/* 127:    */   {
/* 128: 97 */     this.calendar = Calendar.getInstance(TimeZone.getDefault(), locale);
/* 129: 98 */     this.b = locale;
/* 130: 99 */     this.numberFormat = NumberFormat.getInstance(locale);
/* 131:100 */     this.numberFormat.setGroupingUsed(false);
/* 132:101 */     if ((this.numberFormat instanceof DecimalFormat)) {
/* 133:102 */       ((DecimalFormat)this.numberFormat).setDecimalSeparatorAlwaysShown(false);
/* 134:    */     }
/* 135:103 */     this.numberFormat.setParseIntegerOnly(true);
/* 136:104 */     this.numberFormat.setMinimumFractionDigits(0);
/* 137:105 */     a();
/* 138:106 */     if ((this.c != null) && (this.c.indexOf('-') > 0)) {
/* 139:    */       try
/* 140:    */       {
/* 141:109 */         if (this.numberFormat.parse(new String("1-")).intValue() == -1) {
/* 142:110 */           a = true;
/* 143:    */         }
/* 144:    */       }
/* 145:    */       catch (ParseException localParseException) {}
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   private void a()
/* 150:    */   {
/* 151:119 */     this.calendar.setTime(new Date());
/* 152:120 */     this.calendar.add(1, -80);
/* 153:121 */     a(this.calendar.getTime());
/* 154:    */   }
/* 155:    */   
/* 156:    */   private void a(Date date)
/* 157:    */   {
/* 158:126 */     this.d = date;
/* 159:127 */     this.calendar.setTime(date);
/* 160:128 */     this._fldvoid = this.calendar.get(1);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void set2DigitYearStart(Date date)
/* 164:    */   {
/* 165:133 */     a(date);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public Date get2DigitYearStart()
/* 169:    */   {
/* 170:138 */     return this.d;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public StringBuffer format(Date date, StringBuffer stringbuffer, FieldPosition fieldposition)
/* 174:    */   {
/* 175:143 */     fieldposition.setBeginIndex(0);
/* 176:144 */     fieldposition.setEndIndex(0);
/* 177:145 */     Calendar calendar = (Calendar)this.calendar.clone();
/* 178:146 */     calendar.setTime(date);
/* 179:147 */     NumberFormat numberformat = (NumberFormat)this.numberFormat.clone();
/* 180:148 */     boolean flag = false;
/* 181:149 */     char i = '\000';
/* 182:150 */     int j = 0;
/* 183:151 */     for (int k = 0; k < this.c.length(); k++)
/* 184:    */     {
/* 185:153 */       char c1 = this.c.charAt(k);
/* 186:154 */       if ((c1 != i) && (j > 0))
/* 187:    */       {
/* 188:156 */         stringbuffer.append(a(i, j, stringbuffer.length(), fieldposition, calendar, numberformat));
/* 189:157 */         j = 0;
/* 190:    */       }
/* 191:159 */       if (c1 == '\'')
/* 192:    */       {
/* 193:161 */         if ((k + 1 < this.c.length()) && (this.c.charAt(k + 1) == '\''))
/* 194:    */         {
/* 195:163 */           stringbuffer.append('\'');
/* 196:164 */           k++;
/* 197:    */         }
/* 198:    */         else
/* 199:    */         {
/* 200:168 */           flag = !flag;
/* 201:    */         }
/* 202:    */       }
/* 203:172 */       else if ((!flag) && (((c1 >= 'a') && (c1 <= 'z')) || ((c1 >= 'A') && (c1 <= 'Z'))))
/* 204:    */       {
/* 205:174 */         i = c1;
/* 206:175 */         j++;
/* 207:    */       }
/* 208:    */       else
/* 209:    */       {
/* 210:179 */         stringbuffer.append(c1);
/* 211:    */       }
/* 212:    */     }
/* 213:183 */     if (j > 0) {
/* 214:184 */       stringbuffer.append(a(i, j, stringbuffer.length(), fieldposition, calendar, numberformat));
/* 215:    */     }
/* 216:185 */     return stringbuffer;
/* 217:    */   }
/* 218:    */   
/* 219:    */   private String a(char c1, int i, int j, FieldPosition fieldposition, Calendar calendar, NumberFormat numberformat)
/* 220:    */     throws IllegalArgumentException
/* 221:    */   {
/* 222:190 */     int k = -1;
/* 223:191 */     int l = 2147483647;
/* 224:192 */     String s = "";
/* 225:193 */     if ((k = "GyMdkHmsSEDFwWahKz".indexOf(c1)) == -1) {
/* 226:194 */       throw new IllegalArgumentException("Illegal pattern character '" + c1 + "'");
/* 227:    */     }
/* 228:195 */     int i1 = _fldtry[k];
/* 229:196 */     int j1 = calendar.get(i1);
/* 230:197 */     switch (k)
/* 231:    */     {
/* 232:    */     case 0: 
/* 233:200 */       s = this._fldlong.getEras()[j1];
/* 234:201 */       break;
/* 235:    */     case 1: 
/* 236:204 */       if (i >= 4) {
/* 237:205 */         s = a(j1, 4, l, numberformat);
/* 238:    */       } else {
/* 239:207 */         s = a(j1, 2, 2, numberformat);
/* 240:    */       }
/* 241:208 */       break;
/* 242:    */     case 2: 
/* 243:211 */       if (i >= 4)
/* 244:    */       {
/* 245:213 */         if ((i >= 5) && (this._fldlong.getMonths().length >= 26)) {
/* 246:214 */           s = this._fldlong.getMonths()[(j1 + 13)];
/* 247:    */         } else {
/* 248:216 */           s = this._fldlong.getMonths()[j1];
/* 249:    */         }
/* 250:    */       }
/* 251:218 */       else if (i == 3) {
/* 252:219 */         s = this._fldlong.getShortMonths()[j1];
/* 253:    */       } else {
/* 254:221 */         s = a(j1 + 1, i, l, numberformat);
/* 255:    */       }
/* 256:222 */       break;
/* 257:    */     case 4: 
/* 258:225 */       if (j1 == 0) {
/* 259:226 */         s = a(calendar.getMaximum(11) + 1, i, l, numberformat);
/* 260:    */       } else {
/* 261:228 */         s = a(j1, i, l, numberformat);
/* 262:    */       }
/* 263:229 */       break;
/* 264:    */     case 9: 
/* 265:232 */       if (i >= 4) {
/* 266:233 */         s = this._fldlong.getWeekdays()[j1];
/* 267:    */       } else {
/* 268:235 */         s = this._fldlong.getShortWeekdays()[j1];
/* 269:    */       }
/* 270:236 */       break;
/* 271:    */     case 14: 
/* 272:239 */       if (_fldif) {
/* 273:240 */         if (j1 == 0) {
/* 274:241 */           j1 = 1;
/* 275:    */         } else {
/* 276:243 */           j1 = 0;
/* 277:    */         }
/* 278:    */       }
/* 279:244 */       s = this._fldlong.getAmPmStrings()[j1];
/* 280:245 */       break;
/* 281:    */     case 15: 
/* 282:248 */       if (j1 == 0) {
/* 283:249 */         s = a(calendar.getLeastMaximum(10) + 1, i, l, numberformat);
/* 284:    */       } else {
/* 285:251 */         s = a(j1, i, l, numberformat);
/* 286:    */       }
/* 287:252 */       break;
/* 288:    */     case 17: 
/* 289:255 */       int k1 = a(calendar.getTimeZone().getID());
/* 290:256 */       if (k1 == -1)
/* 291:    */       {
/* 292:258 */         StringBuffer stringbuffer = new StringBuffer();
/* 293:259 */         j1 = calendar.get(15) + calendar.get(16);
/* 294:260 */         if (j1 < 0)
/* 295:    */         {
/* 296:262 */           stringbuffer.append("GMT-");
/* 297:263 */           j1 = -j1;
/* 298:    */         }
/* 299:    */         else
/* 300:    */         {
/* 301:267 */           stringbuffer.append("GMT+");
/* 302:    */         }
/* 303:269 */         stringbuffer.append(a(j1 / 3600000, 2, 2, numberformat));
/* 304:270 */         stringbuffer.append(':');
/* 305:271 */         stringbuffer.append(a(j1 % 3600000 / 60000, 2, 2, numberformat));
/* 306:272 */         s = stringbuffer.toString();
/* 307:    */       }
/* 308:274 */       else if (calendar.get(16) != 0)
/* 309:    */       {
/* 310:276 */         if (i >= 4) {
/* 311:277 */           s = this._fldlong.getZoneStrings()[k1][3];
/* 312:    */         } else {
/* 313:279 */           s = this._fldlong.getZoneStrings()[k1][4];
/* 314:    */         }
/* 315:    */       }
/* 316:281 */       else if (i >= 4)
/* 317:    */       {
/* 318:282 */         s = this._fldlong.getZoneStrings()[k1][1];
/* 319:    */       }
/* 320:    */       else
/* 321:    */       {
/* 322:284 */         s = this._fldlong.getZoneStrings()[k1][2];
/* 323:    */       }
/* 324:285 */       break;
/* 325:    */     case 3: 
/* 326:    */     case 5: 
/* 327:    */     case 6: 
/* 328:    */     case 7: 
/* 329:    */     case 8: 
/* 330:    */     case 10: 
/* 331:    */     case 11: 
/* 332:    */     case 12: 
/* 333:    */     case 13: 
/* 334:    */     case 16: 
/* 335:    */     default: 
/* 336:298 */       s = a(j1, i, l, numberformat);
/* 337:    */     }
/* 338:301 */     if ((fieldposition.getField() == _fldelse[k]) && (fieldposition.getBeginIndex() == 0) && (fieldposition.getEndIndex() == 0))
/* 339:    */     {
/* 340:303 */       fieldposition.setBeginIndex(j);
/* 341:304 */       fieldposition.setEndIndex(j + s.length());
/* 342:    */     }
/* 343:306 */     return s;
/* 344:    */   }
/* 345:    */   
/* 346:    */   private static String a(long l, int i, int j, NumberFormat numberformat)
/* 347:    */   {
/* 348:311 */     numberformat.setMinimumIntegerDigits(i);
/* 349:312 */     numberformat.setMaximumIntegerDigits(j);
/* 350:313 */     return numberformat.format(l);
/* 351:    */   }
/* 352:    */   
/* 353:    */   public Date parse(String s, ParsePosition parseposition)
/* 354:    */   {
/* 355:318 */     int i = parseposition.getIndex();
/* 356:319 */     int j = i;
/* 357:320 */     boolean[] aflag = 
/* 358:321 */       new boolean[1];
/* 359:322 */     NumberFormat numberformat = NumberFormat.getInstance(this.b);
/* 360:323 */     numberformat.setGroupingUsed(false);
/* 361:324 */     if ((numberformat instanceof DecimalFormat)) {
/* 362:325 */       ((DecimalFormat)numberformat).setDecimalSeparatorAlwaysShown(false);
/* 363:    */     }
/* 364:326 */     numberformat.setParseIntegerOnly(true);
/* 365:327 */     numberformat.setMinimumFractionDigits(0);
/* 366:328 */     Calendar calendar = (Calendar)this.calendar.clone();
/* 367:329 */     calendar.setTime(new Date());
/* 368:330 */     calendar.add(1, -80);
/* 369:331 */     Date date = calendar.getTime();
/* 370:332 */     int k = calendar.get(1);
/* 371:333 */     calendar.clear();
/* 372:334 */     if ((this.c != null) && (this.c.indexOf('-') > 0)) {
/* 373:    */       try
/* 374:    */       {
/* 375:337 */         if (numberformat.parse(new String("1-")).intValue() == -1) {
/* 376:338 */           a = true;
/* 377:    */         }
/* 378:    */       }
/* 379:    */       catch (ParseException localParseException) {}
/* 380:    */     }
/* 381:343 */     boolean flag = false;
/* 382:344 */     char c1 = '\000';
/* 383:345 */     int l = 0;
/* 384:346 */     int i1 = 1;
/* 385:347 */     for (int j1 = 0; j1 < this.c.length(); j1++)
/* 386:    */     {
/* 387:349 */       char c2 = this.c.charAt(j1);
/* 388:350 */       if (flag)
/* 389:    */       {
/* 390:352 */         if (c2 == '\'')
/* 391:    */         {
/* 392:354 */           flag = false;
/* 393:355 */           if (l == 0)
/* 394:    */           {
/* 395:357 */             if ((i >= s.length()) || (c2 != s.charAt(i)))
/* 396:    */             {
/* 397:359 */               parseposition.setIndex(j);
/* 398:360 */               parseposition.setErrorIndex(i);
/* 399:361 */               return null;
/* 400:    */             }
/* 401:363 */             i++;
/* 402:    */           }
/* 403:365 */           l = 0;
/* 404:366 */           i1 = 0;
/* 405:    */         }
/* 406:    */         else
/* 407:    */         {
/* 408:369 */           if ((i >= s.length()) || (c2 != s.charAt(i)))
/* 409:    */           {
/* 410:371 */             parseposition.setIndex(j);
/* 411:372 */             parseposition.setErrorIndex(i);
/* 412:373 */             return null;
/* 413:    */           }
/* 414:375 */           l++;
/* 415:376 */           i++;
/* 416:    */         }
/* 417:    */       }
/* 418:    */       else
/* 419:    */       {
/* 420:379 */         if (c2 == '\'')
/* 421:    */         {
/* 422:381 */           flag = true;
/* 423:382 */           if (l > 0)
/* 424:    */           {
/* 425:384 */             int l1 = i;
/* 426:385 */             i = a(s, i, c1, l, false, aflag, calendar, numberformat, k);
/* 427:386 */             if (i < 0)
/* 428:    */             {
/* 429:388 */               parseposition.setErrorIndex(l1);
/* 430:389 */               parseposition.setIndex(j);
/* 431:390 */               return null;
/* 432:    */             }
/* 433:392 */             l = 0;
/* 434:    */           }
/* 435:394 */           if (i1 == 0)
/* 436:    */           {
/* 437:396 */             int i2 = i;
/* 438:397 */             if ((i >= s.length()) || (c2 != s.charAt(i)))
/* 439:    */             {
/* 440:399 */               parseposition.setErrorIndex(i2);
/* 441:400 */               parseposition.setIndex(j);
/* 442:401 */               return null;
/* 443:    */             }
/* 444:403 */             i++;
/* 445:404 */             l = 1;
/* 446:    */           }
/* 447:    */         }
/* 448:407 */         else if (((c2 >= 'a') && (c2 <= 'z')) || ((c2 >= 'A') && (c2 <= 'Z')))
/* 449:    */         {
/* 450:409 */           if ((c2 != c1) && (l > 0))
/* 451:    */           {
/* 452:411 */             int j2 = i;
/* 453:412 */             i = a(s, i, c1, l, true, aflag, calendar, numberformat, k);
/* 454:413 */             if (i < 0)
/* 455:    */             {
/* 456:415 */               parseposition.setErrorIndex(j2);
/* 457:416 */               parseposition.setIndex(j);
/* 458:417 */               return null;
/* 459:    */             }
/* 460:419 */             c1 = c2;
/* 461:420 */             l = 1;
/* 462:    */           }
/* 463:    */           else
/* 464:    */           {
/* 465:424 */             if (c2 != c1) {
/* 466:425 */               c1 = c2;
/* 467:    */             }
/* 468:426 */             l++;
/* 469:    */           }
/* 470:    */         }
/* 471:429 */         else if (l > 0)
/* 472:    */         {
/* 473:431 */           int k2 = i;
/* 474:432 */           boolean flag1 = false;
/* 475:433 */           if ((a) && (i + l < s.length()) && (s.charAt(i + l) == '-'))
/* 476:    */           {
/* 477:435 */             flag1 = true;
/* 478:436 */             if ((i + l + 1 < s.length()) && (s.charAt(i + l + 1) == '-')) {
/* 479:437 */               l++;
/* 480:    */             }
/* 481:    */           }
/* 482:439 */           i = a(s, i, c1, l, flag1, aflag, calendar, numberformat, k);
/* 483:440 */           if (i < 0)
/* 484:    */           {
/* 485:442 */             parseposition.setErrorIndex(k2);
/* 486:443 */             parseposition.setIndex(j);
/* 487:444 */             return null;
/* 488:    */           }
/* 489:446 */           if ((i >= s.length()) || (c2 != s.charAt(i)))
/* 490:    */           {
/* 491:448 */             parseposition.setErrorIndex(i);
/* 492:449 */             parseposition.setIndex(j);
/* 493:450 */             return null;
/* 494:    */           }
/* 495:452 */           i++;
/* 496:453 */           l = 0;
/* 497:454 */           c1 = '\000';
/* 498:    */         }
/* 499:    */         else
/* 500:    */         {
/* 501:458 */           if ((i >= s.length()) || (c2 != s.charAt(i)))
/* 502:    */           {
/* 503:460 */             parseposition.setErrorIndex(i);
/* 504:461 */             parseposition.setIndex(j);
/* 505:462 */             return null;
/* 506:    */           }
/* 507:464 */           i++;
/* 508:    */         }
/* 509:466 */         i1++;
/* 510:    */       }
/* 511:    */     }
/* 512:469 */     if (l > 0)
/* 513:    */     {
/* 514:471 */       int k1 = i;
/* 515:472 */       i = a(s, i, c1, l, false, aflag, calendar, numberformat, k);
/* 516:473 */       if (i < 0)
/* 517:    */       {
/* 518:475 */         parseposition.setIndex(j);
/* 519:476 */         parseposition.setErrorIndex(k1);
/* 520:477 */         return null;
/* 521:    */       }
/* 522:    */     }
/* 523:480 */     parseposition.setIndex(i);
/* 524:    */     try
/* 525:    */     {
/* 526:484 */       if (aflag[0] != 0)
/* 527:    */       {
/* 528:486 */         Calendar calendar1 = (Calendar)calendar.clone();
/* 529:487 */         Date date1 = calendar.getTime();
/* 530:488 */         if (date1.before(date))
/* 531:    */         {
/* 532:490 */           calendar1.set(1, k + 100);
/* 533:491 */           date1 = calendar1.getTime();
/* 534:    */         }
/* 535:    */       }
/* 536:    */       else
/* 537:    */       {
/* 538:496 */         date1 = calendar.getTime();
/* 539:    */       }
/* 540:    */     }
/* 541:    */     catch (IllegalArgumentException illegalargumentexception)
/* 542:    */     {
/* 543:    */       Date date1;
/* 544:501 */       parseposition.setErrorIndex(i);
/* 545:502 */       parseposition.setIndex(j);
/* 546:503 */       return null;
/* 547:    */     }
/* 548:    */     Date date1;
/* 549:505 */     return date1;
/* 550:    */   }
/* 551:    */   
/* 552:    */   private static int a(String s, int i, int j, String[] as, Calendar calendar)
/* 553:    */   {
/* 554:510 */     int k = 0;
/* 555:511 */     int l = as.length;
/* 556:512 */     if (j == 7) {
/* 557:513 */       k = 1;
/* 558:    */     }
/* 559:514 */     int i1 = 0;
/* 560:515 */     int j1 = -1;
/* 561:516 */     for (; k < l; k++)
/* 562:    */     {
/* 563:518 */       int k1 = as[k].length();
/* 564:519 */       if ((k1 > i1) && (s.regionMatches(true, i, as[k], 0, k1)))
/* 565:    */       {
/* 566:521 */         j1 = k;
/* 567:522 */         i1 = k1;
/* 568:    */       }
/* 569:    */     }
/* 570:526 */     if (j1 >= 0)
/* 571:    */     {
/* 572:528 */       if (j == 2) {
/* 573:529 */         j1 %= 13;
/* 574:    */       }
/* 575:530 */       calendar.set(j, j1);
/* 576:531 */       return i + i1;
/* 577:    */     }
/* 578:535 */     return -i;
/* 579:    */   }
/* 580:    */   
/* 581:    */   private int a(String s, int i, int j)
/* 582:    */   {
/* 583:542 */     for (int k = 1; (k <= 4) && (!s.regionMatches(true, i, this._fldlong.getZoneStrings()[j][k], 0, this._fldlong.getZoneStrings()[j][k].length())); k++) {}
/* 584:544 */     return k <= 4 ? k : -1;
/* 585:    */   }
/* 586:    */   
/* 587:    */   private int a(String s, int i, Calendar calendar)
/* 588:    */   {
/* 589:549 */     int j = a(getTimeZone().getID());
/* 590:550 */     TimeZone timezone = null;
/* 591:551 */     int l = 0;
/* 592:552 */     int i1 = 0;
/* 593:553 */     if ((j != -1) && ((l = a(s, i, j)) > 0))
/* 594:    */     {
/* 595:555 */       timezone = TimeZone.getTimeZone(this._fldlong.getZoneStrings()[j][0]);
/* 596:556 */       i1 = j;
/* 597:    */     }
/* 598:558 */     if (timezone == null)
/* 599:    */     {
/* 600:560 */       int k = a(TimeZone.getDefault().getID());
/* 601:561 */       if ((k != -1) && ((l = a(s, i, k)) > 0))
/* 602:    */       {
/* 603:563 */         timezone = TimeZone.getTimeZone(this._fldlong.getZoneStrings()[k][0]);
/* 604:564 */         i1 = k;
/* 605:    */       }
/* 606:    */     }
/* 607:567 */     if (timezone == null)
/* 608:    */     {
/* 609:569 */       i1 = 0;
/* 610:572 */       while (i1 < this._fldlong.getZoneStrings().length)
/* 611:    */       {
/* 612:574 */         if ((l = a(s, i, i1)) > 0)
/* 613:    */         {
/* 614:576 */           timezone = TimeZone.getTimeZone(this._fldlong.getZoneStrings()[i1][0]);
/* 615:577 */           break;
/* 616:    */         }
/* 617:579 */         i1++;
/* 618:    */       }
/* 619:    */     }
/* 620:582 */     if (timezone != null)
/* 621:    */     {
/* 622:584 */       calendar.set(15, timezone.getRawOffset());
/* 623:    */       try
/* 624:    */       {
/* 625:587 */         if ((timezone instanceof SimpleTimeZone)) {
/* 626:588 */           calendar.set(16, l < 3 ? 0 : ((SimpleTimeZone)timezone).getDSTSavings());
/* 627:    */         } else {
/* 628:590 */           calendar.set(16, l < 3 ? 0 : timezone.getDSTSavings());
/* 629:    */         }
/* 630:    */       }
/* 631:    */       catch (Exception exception)
/* 632:    */       {
/* 633:594 */         calendar.set(16, 0);
/* 634:    */       }
/* 635:596 */       return i + this._fldlong.getZoneStrings()[i1][l].length();
/* 636:    */     }
/* 637:600 */     return 0;
/* 638:    */   }
/* 639:    */   
/* 640:    */   private int a(String s, int i, char c1, int j, boolean flag, boolean[] aflag, Calendar calendar, NumberFormat numberformat, int k)
/* 641:    */   {
/* 642:606 */     Number number = null;
/* 643:607 */     int l = 0;
/* 644:608 */     ParsePosition parseposition = new ParsePosition(0);
/* 645:609 */     int j1 = -1;
/* 646:610 */     if ((j1 = "GyMdkHmsSEDFwWahKz".indexOf(c1)) == -1) {
/* 647:611 */       return -i;
/* 648:    */     }
/* 649:612 */     parseposition.setIndex(i);
/* 650:613 */     int k1 = _fldtry[j1];
/* 651:    */     for (;;)
/* 652:    */     {
/* 653:616 */       if (parseposition.getIndex() >= s.length()) {
/* 654:617 */         return -i;
/* 655:    */       }
/* 656:618 */       char c2 = s.charAt(parseposition.getIndex());
/* 657:619 */       if ((c2 != ' ') && (c2 != '\t')) {
/* 658:    */         break;
/* 659:    */       }
/* 660:621 */       parseposition.setIndex(parseposition.getIndex() + 1);
/* 661:    */     }
/* 662:623 */     if ((j1 == 4) || (j1 == 15) || ((j1 == 2) && (j <= 2)) || (j1 == 1))
/* 663:    */     {
/* 664:625 */       if (flag)
/* 665:    */       {
/* 666:627 */         if (i + j > s.length()) {
/* 667:628 */           return -i;
/* 668:    */         }
/* 669:629 */         number = numberformat.parse(s.substring(0, i + j), parseposition);
/* 670:    */       }
/* 671:    */       else
/* 672:    */       {
/* 673:633 */         number = numberformat.parse(s, parseposition);
/* 674:    */       }
/* 675:635 */       if (number == null) {
/* 676:636 */         return -i;
/* 677:    */       }
/* 678:637 */       l = number.intValue();
/* 679:    */     }
/* 680:639 */     switch (j1)
/* 681:    */     {
/* 682:    */     case 0: 
/* 683:642 */       return a(s, i, 0, this._fldlong.getEras(), calendar);
/* 684:    */     case 1: 
/* 685:645 */       if ((j <= 2) && (parseposition.getIndex() - i == 2) && (Character.isDigit(s.charAt(i))) && (Character.isDigit(s.charAt(i + 1))))
/* 686:    */       {
/* 687:647 */         int l1 = k % 100;
/* 688:648 */         aflag[0] = (l == l1 ? 1 : false);
/* 689:649 */         l += k / 100 * 100 + (l >= l1 ? 0 : 100);
/* 690:    */       }
/* 691:651 */       calendar.set(1, l);
/* 692:652 */       return parseposition.getIndex();
/* 693:    */     case 2: 
/* 694:655 */       if (j <= 2)
/* 695:    */       {
/* 696:657 */         calendar.set(2, l - 1);
/* 697:658 */         return parseposition.getIndex();
/* 698:    */       }
/* 699:660 */       int i2 = 0;
/* 700:661 */       if ((i2 = a(s, i, 2, this._fldlong.getMonths(), calendar)) > 0) {
/* 701:662 */         return i2;
/* 702:    */       }
/* 703:664 */       return a(s, i, 2, this._fldlong.getShortMonths(), calendar);
/* 704:    */     case 4: 
/* 705:667 */       if (l == calendar.getMaximum(11) + 1) {
/* 706:668 */         l = 0;
/* 707:    */       }
/* 708:669 */       calendar.set(11, l);
/* 709:670 */       return parseposition.getIndex();
/* 710:    */     case 9: 
/* 711:673 */       int j2 = 0;
/* 712:674 */       if ((j2 = a(s, i, 7, this._fldlong.getWeekdays(), calendar)) > 0) {
/* 713:675 */         return j2;
/* 714:    */       }
/* 715:677 */       return a(s, i, 7, this._fldlong.getShortWeekdays(), calendar);
/* 716:    */     case 14: 
/* 717:680 */       return a(s, i, 9, this._fldlong.getAmPmStrings(), calendar);
/* 718:    */     case 15: 
/* 719:683 */       if (l == calendar.getLeastMaximum(10) + 1) {
/* 720:684 */         l = 0;
/* 721:    */       }
/* 722:685 */       calendar.set(10, l);
/* 723:686 */       return parseposition.getIndex();
/* 724:    */     case 17: 
/* 725:689 */       byte byte0 = 0;
/* 726:    */       int k2;
/* 727:691 */       if ((s.length() - i >= "GMT".length()) && (s.regionMatches(true, i, "GMT", 0, "GMT".length())))
/* 728:    */       {
/* 729:693 */         calendar.set(16, 0);
/* 730:694 */         parseposition.setIndex(i + "GMT".length());
/* 731:    */         try
/* 732:    */         {
/* 733:697 */           if (s.charAt(parseposition.getIndex()) == '+') {
/* 734:698 */             byte0 = 1;
/* 735:699 */           } else if (s.charAt(parseposition.getIndex()) == '-') {
/* 736:700 */             byte0 = -1;
/* 737:    */           }
/* 738:    */         }
/* 739:    */         catch (StringIndexOutOfBoundsException localStringIndexOutOfBoundsException) {}
/* 740:705 */         if (byte0 == 0)
/* 741:    */         {
/* 742:707 */           calendar.set(15, 0);
/* 743:708 */           return parseposition.getIndex();
/* 744:    */         }
/* 745:710 */         parseposition.setIndex(parseposition.getIndex() + 1);
/* 746:711 */         Number number1 = numberformat.parse(s, parseposition);
/* 747:712 */         if (number1 == null) {
/* 748:713 */           return -i;
/* 749:    */         }
/* 750:714 */         if (s.charAt(parseposition.getIndex()) == ':')
/* 751:    */         {
/* 752:716 */           int k2 = number1.intValue() * 60;
/* 753:717 */           parseposition.setIndex(parseposition.getIndex() + 1);
/* 754:718 */           number1 = numberformat.parse(s, parseposition);
/* 755:719 */           if (number1 == null) {
/* 756:720 */             return -i;
/* 757:    */           }
/* 758:721 */           k2 += number1.intValue();
/* 759:    */         }
/* 760:    */         else
/* 761:    */         {
/* 762:725 */           int k2 = number1.intValue();
/* 763:726 */           if (k2 < 24) {
/* 764:727 */             k2 *= 60;
/* 765:    */           } else {
/* 766:729 */             k2 = k2 % 100 + k2 / 100 * 60;
/* 767:    */           }
/* 768:    */         }
/* 769:    */       }
/* 770:    */       else
/* 771:    */       {
/* 772:734 */         int i1 = a(s, i, calendar);
/* 773:735 */         if (i1 != 0) {
/* 774:736 */           return i1;
/* 775:    */         }
/* 776:737 */         DecimalFormat decimalformat = new DecimalFormat("+####;-####");
/* 777:738 */         decimalformat.setParseIntegerOnly(true);
/* 778:739 */         Number number2 = decimalformat.parse(s, parseposition);
/* 779:740 */         if (number2 == null) {
/* 780:741 */           return -i;
/* 781:    */         }
/* 782:742 */         k2 = number2.intValue();
/* 783:743 */         byte0 = 1;
/* 784:744 */         if (k2 < 0)
/* 785:    */         {
/* 786:746 */           byte0 = -1;
/* 787:747 */           k2 = -k2;
/* 788:    */         }
/* 789:749 */         if (k2 < 24) {
/* 790:750 */           k2 *= 60;
/* 791:    */         } else {
/* 792:752 */           k2 = k2 % 100 + k2 / 100 * 60;
/* 793:    */         }
/* 794:    */       }
/* 795:754 */       if (byte0 != 0)
/* 796:    */       {
/* 797:756 */         k2 *= 60000 * byte0;
/* 798:757 */         if (calendar.getTimeZone().useDaylightTime())
/* 799:    */         {
/* 800:759 */           calendar.set(16, 3600000);
/* 801:760 */           k2 -= 3600000;
/* 802:    */         }
/* 803:762 */         calendar.set(15, k2);
/* 804:763 */         return parseposition.getIndex();
/* 805:    */       }
/* 806:767 */       return -i;
/* 807:    */     }
/* 808:770 */     if (flag)
/* 809:    */     {
/* 810:772 */       if (i + j > s.length()) {
/* 811:773 */         return -i;
/* 812:    */       }
/* 813:774 */       number = numberformat.parse(s.substring(0, i + j), parseposition);
/* 814:    */     }
/* 815:    */     else
/* 816:    */     {
/* 817:778 */       number = numberformat.parse(s, parseposition);
/* 818:    */     }
/* 819:780 */     if (number != null)
/* 820:    */     {
/* 821:782 */       calendar.set(k1, number.intValue());
/* 822:783 */       return parseposition.getIndex();
/* 823:    */     }
/* 824:787 */     return -i;
/* 825:    */   }
/* 826:    */   
/* 827:    */   private static String a(String s, String s1, String s2)
/* 828:    */   {
/* 829:793 */     StringBuffer stringbuffer = new StringBuffer();
/* 830:794 */     boolean flag = false;
/* 831:795 */     for (int i = 0; i < s.length(); i++)
/* 832:    */     {
/* 833:797 */       char c1 = s.charAt(i);
/* 834:798 */       if (flag)
/* 835:    */       {
/* 836:800 */         if (c1 == '\'') {
/* 837:801 */           flag = false;
/* 838:    */         }
/* 839:    */       }
/* 840:803 */       else if (c1 == '\'')
/* 841:    */       {
/* 842:804 */         flag = true;
/* 843:    */       }
/* 844:805 */       else if (((c1 >= 'a') && (c1 <= 'z')) || ((c1 >= 'A') && (c1 <= 'Z')))
/* 845:    */       {
/* 846:807 */         int j = s1.indexOf(c1);
/* 847:808 */         if (j == -1) {
/* 848:809 */           throw new IllegalArgumentException("Illegal pattern  character '" + c1 + "'");
/* 849:    */         }
/* 850:810 */         c1 = s2.charAt(j);
/* 851:    */       }
/* 852:812 */       stringbuffer.append(c1);
/* 853:    */     }
/* 854:815 */     if (flag) {
/* 855:816 */       throw new IllegalArgumentException("Unfinished quote in pattern");
/* 856:    */     }
/* 857:818 */     return stringbuffer.toString();
/* 858:    */   }
/* 859:    */   
/* 860:    */   public String toPattern()
/* 861:    */   {
/* 862:823 */     return this.c;
/* 863:    */   }
/* 864:    */   
/* 865:    */   public String toLocalizedPattern()
/* 866:    */   {
/* 867:828 */     return a(this.c, "GyMdkHmsSEDFwWahKz", this._fldlong.getLocalPatternChars());
/* 868:    */   }
/* 869:    */   
/* 870:    */   public DateFormatSymbols getDateFormatSymbols()
/* 871:    */   {
/* 872:833 */     return (DateFormatSymbols)this._fldlong.clone();
/* 873:    */   }
/* 874:    */   
/* 875:    */   public Object clone()
/* 876:    */   {
/* 877:838 */     FFIDateFormat ffidateformat = (FFIDateFormat)super.clone();
/* 878:839 */     ffidateformat._fldlong = ((DateFormatSymbols)this._fldlong.clone());
/* 879:840 */     return ffidateformat;
/* 880:    */   }
/* 881:    */   
/* 882:    */   public int hashCode()
/* 883:    */   {
/* 884:845 */     return this.c.hashCode();
/* 885:    */   }
/* 886:    */   
/* 887:    */   public boolean equals(Object obj)
/* 888:    */   {
/* 889:850 */     if (!super.equals(obj)) {
/* 890:852 */       return false;
/* 891:    */     }
/* 892:856 */     FFIDateFormat ffidateformat = (FFIDateFormat)obj;
/* 893:857 */     return (this.c.equals(ffidateformat.c)) && (this._fldlong.equals(ffidateformat._fldlong));
/* 894:    */   }
/* 895:    */   
/* 896:    */   private void a(ObjectInputStream objectinputstream)
/* 897:    */     throws IOException, ClassNotFoundException
/* 898:    */   {
/* 899:863 */     objectinputstream.defaultReadObject();
/* 900:864 */     if (this._fldgoto < 1) {
/* 901:865 */       a();
/* 902:    */     } else {
/* 903:867 */       a(this.d);
/* 904:    */     }
/* 905:868 */     this._fldgoto = 1;
/* 906:    */   }
/* 907:    */   
/* 908:    */   private final int a(String s)
/* 909:    */   {
/* 910:873 */     for (int i = 0; i < this._fldlong.getZoneStrings().length; i++) {
/* 911:874 */       if (s.equalsIgnoreCase(this._fldlong.getZoneStrings()[i][0])) {
/* 912:875 */         return i;
/* 913:    */       }
/* 914:    */     }
/* 915:877 */     return -1;
/* 916:    */   }
/* 917:    */   
/* 918:    */   public void setCalendar(Calendar calendar)
/* 919:    */   {
/* 920:882 */     throw new UnsupportedOperationException("setCalender cannot be used with FFIDateFormat.");
/* 921:    */   }
/* 922:    */   
/* 923:    */   public void getCalendar(Calendar calendar)
/* 924:    */   {
/* 925:887 */     throw new UnsupportedOperationException("getCalender cannot be used with FFIDateFormat.");
/* 926:    */   }
/* 927:    */   
/* 928:    */   public void setNumberFormat(NumberFormat numberformat)
/* 929:    */   {
/* 930:892 */     throw new UnsupportedOperationException("setNumberFormat cannot be used with FFIDateFormat.  The numberFormat must be set on construction.");
/* 931:    */   }
/* 932:    */   
/* 933:    */   public void getNumberFormat(NumberFormat numberformat)
/* 934:    */   {
/* 935:897 */     throw new UnsupportedOperationException("getNumberFormat cannot be used with FFIDateFormat.");
/* 936:    */   }
/* 937:    */   
/* 938:    */   public void setTimeZone(TimeZone timezone)
/* 939:    */   {
/* 940:902 */     throw new UnsupportedOperationException("setTimeZone cannot be used with FFIDateFormat.  If you need to change the TimeZone, use SimpleDateFormat.");
/* 941:    */   }
/* 942:    */   
/* 943:    */   public void applyPattern(String s)
/* 944:    */   {
/* 945:907 */     throw new UnsupportedOperationException("applyPattern cannot be used with FFIDateFormat.  The pattern must be set on construction.");
/* 946:    */   }
/* 947:    */   
/* 948:    */   public void applyLocalizedPattern(String s)
/* 949:    */   {
/* 950:912 */     throw new UnsupportedOperationException("applyLocalizedPattern cannot be used with FFIDateFormat.  The pattern must be set on construction.");
/* 951:    */   }
/* 952:    */   
/* 953:    */   public void setDateFormatSymbols(DateFormatSymbols dateformatsymbols)
/* 954:    */   {
/* 955:917 */     throw new UnsupportedOperationException("setDateFormatSymbols cannot be used with FFIDateFormat.  The format symbols must be set on construction.");
/* 956:    */   }
/* 957:    */   
/* 958:948 */   private static boolean _fldif = false;
/* 959:950 */   private static boolean a = false;
/* 960:952 */   private static Hashtable _fldnew = new Hashtable(3);
/* 961:955 */   private static final int[] _fldtry = { 0, 1, 2, 5, 11, 11, 12, 13, 14, 7, 6, 8, 3, 4, 9, 10, 10, 15 };
/* 962:958 */   private static final int[] _fldelse = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };
/* 963:    */ }


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.FFIDateFormat
 * JD-Core Version:    0.7.0.1
 */