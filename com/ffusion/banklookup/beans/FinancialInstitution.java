/*   1:    */ package com.ffusion.banklookup.beans;
/*   2:    */ 
/*   3:    */ import com.ffusion.banklookup.FinancialInstitutionException;
/*   4:    */ import com.ffusion.beans.Contact;
/*   5:    */ import com.ffusion.beans.XMLStrings;
/*   6:    */ import com.ffusion.util.RoutingNumberUtil;
/*   7:    */ import com.ffusion.util.XMLHandler;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ 
/*  10:    */ public class FinancialInstitution
/*  11:    */   extends Contact
/*  12:    */   implements XMLStrings
/*  13:    */ {
/*  14:    */   public static final String NULL_ROUTING_NUMBER = "000000000";
/*  15:    */   public static final String NON_NULL_ROUTING_NUMBER = "NON_NULL_ROUTING_NUMBER";
/*  16:    */   public static final String PREFERRED_COUNTRY = "UNITED STATES";
/*  17:    */   public static final String PREFERRED_COUNTRY_CODE = "USA";
/*  18:    */   public static final String UNKNOWN_COUNTRY = "UNKNOWN";
/*  19:    */   private int institutionId;
/*  20: 43 */   private String institutionName = null;
/*  21:    */   private String branchName;
/*  22:    */   private String achRoutingNumber;
/*  23:    */   private String wireRoutingNumber;
/*  24:    */   private String swiftBIC;
/*  25:    */   private String nationalID;
/*  26:    */   private String chipsUID;
/*  27: 50 */   private ArrayList routingNumbers = new ArrayList(1);
/*  28:    */   
/*  29:    */   public FinancialInstitution()
/*  30:    */   {
/*  31: 59 */     this.routingNumbers.add("000000000");
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int getInstitutionId()
/*  35:    */   {
/*  36: 72 */     return this.institutionId;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getInstitutionName()
/*  40:    */   {
/*  41: 83 */     return this.institutionName;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String getBranchName()
/*  45:    */   {
/*  46: 93 */     return this.branchName;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getAchRoutingNumber()
/*  50:    */   {
/*  51:103 */     return this.achRoutingNumber;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getWireRoutingNumber()
/*  55:    */   {
/*  56:113 */     return this.wireRoutingNumber;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public ArrayList getRoutingNumbers()
/*  60:    */   {
/*  61:123 */     return this.routingNumbers;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getSwiftBIC()
/*  65:    */   {
/*  66:133 */     return this.swiftBIC;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getNationalID()
/*  70:    */   {
/*  71:143 */     return this.nationalID;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getChipsUID()
/*  75:    */   {
/*  76:153 */     return this.chipsUID;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setInstitutionId(int institutionId)
/*  80:    */   {
/*  81:168 */     this.institutionId = institutionId;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setInstitutionName(String institutionName)
/*  85:    */   {
/*  86:178 */     this.institutionName = getNormalizedValue(institutionName);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setBranchName(String branchName)
/*  90:    */   {
/*  91:188 */     this.branchName = getNormalizedValue(branchName);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setAchRoutingNumber(String rtn)
/*  95:    */     throws FinancialInstitutionException
/*  96:    */   {
/*  97:203 */     if (rtn != null) {
/*  98:205 */       if (!RoutingNumberUtil.isValidRoutingNumber(rtn)) {
/*  99:206 */         throw new FinancialInstitutionException(10, "Invalid Routing Number " + rtn);
/* 100:    */       }
/* 101:    */     }
/* 102:209 */     this.achRoutingNumber = rtn;
/* 103:210 */     addRoutingNumber(rtn);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setWireRoutingNumber(String rtn)
/* 107:    */     throws FinancialInstitutionException
/* 108:    */   {
/* 109:223 */     if (rtn != null) {
/* 110:225 */       if (!RoutingNumberUtil.isValidRoutingNumber(rtn)) {
/* 111:226 */         throw new FinancialInstitutionException(10, "Invalid Routing Number " + rtn);
/* 112:    */       }
/* 113:    */     }
/* 114:229 */     this.wireRoutingNumber = rtn;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setAchRoutingNumberForSearch(String rtn)
/* 118:    */   {
/* 119:244 */     this.achRoutingNumber = rtn;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setWireRoutingNumberForSearch(String rtn)
/* 123:    */   {
/* 124:256 */     this.wireRoutingNumber = rtn;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void addRoutingNumber(String rtn)
/* 128:    */     throws FinancialInstitutionException
/* 129:    */   {
/* 130:271 */     if (rtn != null)
/* 131:    */     {
/* 132:272 */       if (!RoutingNumberUtil.isValidRoutingNumber(rtn)) {
/* 133:273 */         throw new FinancialInstitutionException(10, "Invalid Routing Number " + rtn);
/* 134:    */       }
/* 135:278 */       if (!existsRoutingNumber(rtn)) {
/* 136:280 */         this.routingNumbers.add(rtn);
/* 137:    */       }
/* 138:282 */       removeRoutingNumber("000000000");
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void removeRoutingNumber(String fedRoutingNumber)
/* 143:    */   {
/* 144:    */     for (;;)
/* 145:    */     {
/* 146:295 */       boolean arrayModified = false;
/* 147:296 */       for (int i = 0; i < this.routingNumbers.size(); i++)
/* 148:    */       {
/* 149:298 */         String rtn = (String)this.routingNumbers.get(i);
/* 150:299 */         if (fedRoutingNumber.equals(rtn))
/* 151:    */         {
/* 152:301 */           this.routingNumbers.remove(i);
/* 153:302 */           arrayModified = true;
/* 154:    */         }
/* 155:    */       }
/* 156:305 */       if (!arrayModified) {
/* 157:    */         break;
/* 158:    */       }
/* 159:    */     }
/* 160:311 */     if (this.routingNumbers.size() == 0)
/* 161:    */     {
/* 162:313 */       this.achRoutingNumber = "000000000";
/* 163:    */       
/* 164:315 */       this.routingNumbers.add("000000000");
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   public boolean existsRoutingNumber(String routingNumber)
/* 169:    */   {
/* 170:327 */     for (int i = 0; i < this.routingNumbers.size(); i++)
/* 171:    */     {
/* 172:329 */       String rtn = (String)this.routingNumbers.get(i);
/* 173:330 */       if (routingNumber.equals(rtn)) {
/* 174:331 */         return true;
/* 175:    */       }
/* 176:    */     }
/* 177:333 */     return false;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setSwiftBIC(String swiftBIC)
/* 181:    */   {
/* 182:343 */     this.swiftBIC = getNormalizedValue(swiftBIC);
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setNationalID(String nationalID)
/* 186:    */   {
/* 187:353 */     this.nationalID = getNormalizedValue(nationalID);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setChipsUID(String chipsUID)
/* 191:    */   {
/* 192:363 */     this.chipsUID = getNormalizedValue(chipsUID);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setStreet(String street)
/* 196:    */   {
/* 197:374 */     if (street != null) {
/* 198:375 */       super.setStreet(street.trim().toUpperCase());
/* 199:    */     } else {
/* 200:377 */       super.setStreet(null);
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setStreet2(String street)
/* 205:    */   {
/* 206:388 */     if (street != null) {
/* 207:389 */       super.setStreet2(street.trim().toUpperCase());
/* 208:    */     } else {
/* 209:391 */       super.setStreet2(null);
/* 210:    */     }
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setStreet3(String street)
/* 214:    */   {
/* 215:402 */     if (street != null) {
/* 216:403 */       super.setStreet3(street.trim().toUpperCase());
/* 217:    */     } else {
/* 218:405 */       super.setStreet3(null);
/* 219:    */     }
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setCity(String city)
/* 223:    */   {
/* 224:416 */     if (city != null) {
/* 225:417 */       super.setCity(city.trim().toUpperCase());
/* 226:    */     } else {
/* 227:419 */       super.setCity(null);
/* 228:    */     }
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setState(String state)
/* 232:    */   {
/* 233:431 */     if (state != null) {
/* 234:432 */       super.setState(state.trim().toUpperCase());
/* 235:    */     } else {
/* 236:434 */       super.setState(null);
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setCountry(String country)
/* 241:    */   {
/* 242:445 */     if (country != null) {
/* 243:446 */       super.setCountry(country.trim().toUpperCase());
/* 244:    */     } else {
/* 245:448 */       super.setCountry(null);
/* 246:    */     }
/* 247:    */   }
/* 248:    */   
/* 249:    */   public int compare(Object o, String field)
/* 250:    */   {
/* 251:462 */     FinancialInstitution fi = (FinancialInstitution)o;
/* 252:463 */     int ret = 1;
/* 253:465 */     if ((field.equals("NAME")) && (getInstitutionName() != null) && (fi.getInstitutionName() != null)) {
/* 254:466 */       ret = getInstitutionName().compareTo(fi.getInstitutionName());
/* 255:    */     } else {
/* 256:468 */       ret = super.compare(o, field);
/* 257:    */     }
/* 258:470 */     return ret;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void set(FinancialInstitution fi)
/* 262:    */     throws FinancialInstitutionException
/* 263:    */   {
/* 264:482 */     super.set(fi);
/* 265:    */     
/* 266:484 */     setInstitutionId(fi.getInstitutionId());
/* 267:485 */     setInstitutionName(fi.getInstitutionName());
/* 268:486 */     setBranchName(fi.getBranchName());
/* 269:487 */     setAchRoutingNumber(fi.getAchRoutingNumber());
/* 270:488 */     setWireRoutingNumber(fi.getWireRoutingNumber());
/* 271:489 */     setSwiftBIC(fi.getSwiftBIC());
/* 272:490 */     setNationalID(fi.getNationalID());
/* 273:491 */     setChipsUID(fi.getChipsUID());
/* 274:492 */     setStreet(fi.getStreet());
/* 275:493 */     setStreet2(fi.getStreet2());
/* 276:494 */     setStreet3(fi.getStreet3());
/* 277:495 */     setCity(fi.getCity());
/* 278:496 */     setState(fi.getState());
/* 279:497 */     setCountry(fi.getCountry());
/* 280:    */     
/* 281:499 */     int size = this.routingNumbers.size();
/* 282:500 */     for (int i = 0; i < size; i++) {
/* 283:501 */       addRoutingNumber((String)this.routingNumbers.get(i));
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   public String getXML()
/* 288:    */   {
/* 289:512 */     StringBuffer sb = new StringBuffer();
/* 290:    */     
/* 291:514 */     XMLHandler.appendBeginTag(sb, "INSTITUTION");
/* 292:    */     
/* 293:    */ 
/* 294:517 */     XMLHandler.appendTag(sb, "BANKNAME", this.institutionName);
/* 295:518 */     XMLHandler.appendTag(sb, "ROUTING_ACH", this.achRoutingNumber);
/* 296:519 */     XMLHandler.appendTag(sb, "ROUTING_WIRE", this.wireRoutingNumber);
/* 297:520 */     XMLHandler.appendTag(sb, "ROUTING_SWIFT", this.swiftBIC);
/* 298:521 */     XMLHandler.appendTag(sb, "ROUTING_OTHER", this.nationalID);
/* 299:522 */     XMLHandler.appendTag(sb, "ROUTING_CHIPS", this.chipsUID);
/* 300:523 */     XMLHandler.appendBeginTag(sb, "ROUTINGNUM");
/* 301:524 */     for (int i = 0; i < this.routingNumbers.size(); i++) {
/* 302:525 */       XMLHandler.appendTag(sb, "ROUTING_FEDWIRE", (String)this.routingNumbers.get(i));
/* 303:    */     }
/* 304:527 */     XMLHandler.appendEndTag(sb, "ROUTINGNUM");
/* 305:528 */     sb.append(super.getXML());
/* 306:529 */     XMLHandler.appendEndTag(sb, "INSTITUTION");
/* 307:    */     
/* 308:531 */     return sb.toString();
/* 309:    */   }
/* 310:    */   
/* 311:    */   public boolean isFilterablePreParsed(String attribute, String action, String value)
/* 312:    */   {
/* 313:557 */     if (attribute.equals("ID")) {
/* 314:558 */       return isFilterable(String.valueOf(getInstitutionId()), action, value);
/* 315:    */     }
/* 316:559 */     if (attribute.equals("BANK_NAME")) {
/* 317:560 */       return isFilterable(getInstitutionName(), action, value);
/* 318:    */     }
/* 319:561 */     if (attribute.equals("CITY")) {
/* 320:562 */       return isFilterable(getCity(), action, value);
/* 321:    */     }
/* 322:563 */     if (attribute.equals("STATE")) {
/* 323:564 */       return isFilterable(getState(), action, value);
/* 324:    */     }
/* 325:565 */     if (attribute.equals("ZIP_CODE")) {
/* 326:566 */       return isFilterable(getZipCode(), action, value);
/* 327:    */     }
/* 328:567 */     if (attribute.equals("COUNTRY")) {
/* 329:568 */       return isFilterable(getCountry(), action, value);
/* 330:    */     }
/* 331:570 */     return super.isFilterablePreParsed(attribute, action, value);
/* 332:    */   }
/* 333:    */   
/* 334:    */   private String getNormalizedValue(String val)
/* 335:    */   {
/* 336:588 */     if (val != null) {
/* 337:589 */       return val.toUpperCase().trim();
/* 338:    */     }
/* 339:591 */     return null;
/* 340:    */   }
/* 341:    */ }


/* Location:           D:\drops\jd\jars\ffiblcommon.jar
 * Qualified Name:     com.ffusion.banklookup.beans.FinancialInstitution
 * JD-Core Version:    0.7.0.1
 */