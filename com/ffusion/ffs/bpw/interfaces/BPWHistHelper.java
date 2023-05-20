/*   1:    */ package com.ffusion.ffs.bpw.interfaces;
/*   2:    */ 
/*   3:    */ import com.sybase.ejb.cts.StringSeqHelper;
/*   4:    */ import com.sybase.java.lang.ObjectSeqHelper;
/*   5:    */ import org.omg.CORBA.Any;
/*   6:    */ import org.omg.CORBA.BAD_OPERATION;
/*   7:    */ import org.omg.CORBA.ORB;
/*   8:    */ import org.omg.CORBA.StructMember;
/*   9:    */ import org.omg.CORBA.TCKind;
/*  10:    */ import org.omg.CORBA.TypeCode;
/*  11:    */ import org.omg.CORBA.portable.InputStream;
/*  12:    */ import org.omg.CORBA.portable.OutputStream;
/*  13:    */ 
/*  14:    */ public abstract class BPWHistHelper
/*  15:    */ {
/*  16:    */   public static TypeCode _type;
/*  17:    */   
/*  18:    */   public static BPWHist clone(BPWHist paramBPWHist)
/*  19:    */   {
/*  20: 16 */     if (paramBPWHist == null) {
/*  21: 18 */       return null;
/*  22:    */     }
/*  23: 20 */     BPWHist localBPWHist = new BPWHist();
/*  24: 21 */     localBPWHist.custId = paramBPWHist.custId;
/*  25: 22 */     localBPWHist.fiId = paramBPWHist.fiId;
/*  26: 23 */     localBPWHist.payeeId = paramBPWHist.payeeId;
/*  27: 24 */     localBPWHist.acctId = paramBPWHist.acctId;
/*  28: 25 */     localBPWHist.trans = ObjectSeqHelper.clone(paramBPWHist.trans);
/*  29: 26 */     localBPWHist.startDate = paramBPWHist.startDate;
/*  30: 27 */     localBPWHist.endDate = paramBPWHist.endDate;
/*  31: 28 */     localBPWHist.version = paramBPWHist.version;
/*  32: 29 */     localBPWHist.submittedBy = StringSeqHelper.clone(paramBPWHist.submittedBy);
/*  33: 30 */     localBPWHist.userId = StringSeqHelper.clone(paramBPWHist.userId);
/*  34: 31 */     localBPWHist.statusList = StringSeqHelper.clone(paramBPWHist.statusList);
/*  35: 32 */     localBPWHist.transType = paramBPWHist.transType;
/*  36: 33 */     localBPWHist.transScope = paramBPWHist.transScope;
/*  37: 34 */     localBPWHist.dest = StringSeqHelper.clone(paramBPWHist.dest);
/*  38: 35 */     localBPWHist.templateId = paramBPWHist.templateId;
/*  39: 36 */     localBPWHist.statusCode = paramBPWHist.statusCode;
/*  40: 37 */     localBPWHist.statusMsg = paramBPWHist.statusMsg;
/*  41: 38 */     localBPWHist.transSource = paramBPWHist.transSource;
/*  42: 39 */     localBPWHist.minAmount = paramBPWHist.minAmount;
/*  43: 40 */     localBPWHist.maxAmount = paramBPWHist.maxAmount;
/*  44: 41 */     localBPWHist.selectionCriteria = paramBPWHist.selectionCriteria;
/*  45: 42 */     localBPWHist.totalTrans = paramBPWHist.totalTrans;
/*  46: 43 */     localBPWHist.pageSize = paramBPWHist.pageSize;
/*  47: 44 */     localBPWHist.histId = paramBPWHist.histId;
/*  48: 45 */     localBPWHist.cursorId = paramBPWHist.cursorId;
/*  49: 46 */     localBPWHist.requiredStatus = paramBPWHist.requiredStatus;
/*  50: 47 */     localBPWHist.category = paramBPWHist.category;
/*  51: 48 */     return localBPWHist;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static BPWHist read(InputStream paramInputStream)
/*  55:    */   {
/*  56: 54 */     BPWHist localBPWHist = new BPWHist();
/*  57: 55 */     localBPWHist.custId = paramInputStream.read_string();
/*  58: 56 */     localBPWHist.fiId = paramInputStream.read_string();
/*  59: 57 */     localBPWHist.payeeId = paramInputStream.read_string();
/*  60: 58 */     localBPWHist.acctId = paramInputStream.read_string();
/*  61: 59 */     localBPWHist.trans = ObjectSeqHelper.read(paramInputStream);
/*  62: 60 */     localBPWHist.startDate = paramInputStream.read_string();
/*  63: 61 */     localBPWHist.endDate = paramInputStream.read_string();
/*  64: 62 */     localBPWHist.version = paramInputStream.read_string();
/*  65: 63 */     localBPWHist.submittedBy = StringSeqHelper.read(paramInputStream);
/*  66: 64 */     localBPWHist.userId = StringSeqHelper.read(paramInputStream);
/*  67: 65 */     localBPWHist.statusList = StringSeqHelper.read(paramInputStream);
/*  68: 66 */     localBPWHist.transType = paramInputStream.read_string();
/*  69: 67 */     localBPWHist.transScope = paramInputStream.read_string();
/*  70: 68 */     localBPWHist.dest = StringSeqHelper.read(paramInputStream);
/*  71: 69 */     localBPWHist.templateId = paramInputStream.read_string();
/*  72: 70 */     localBPWHist.statusCode = paramInputStream.read_long();
/*  73: 71 */     localBPWHist.statusMsg = paramInputStream.read_string();
/*  74: 72 */     localBPWHist.transSource = paramInputStream.read_string();
/*  75: 73 */     localBPWHist.minAmount = paramInputStream.read_string();
/*  76: 74 */     localBPWHist.maxAmount = paramInputStream.read_string();
/*  77: 75 */     localBPWHist.selectionCriteria = paramInputStream.read_string();
/*  78: 76 */     localBPWHist.totalTrans = paramInputStream.read_longlong();
/*  79: 77 */     localBPWHist.pageSize = paramInputStream.read_longlong();
/*  80: 78 */     localBPWHist.histId = paramInputStream.read_string();
/*  81: 79 */     localBPWHist.cursorId = paramInputStream.read_string();
/*  82: 80 */     localBPWHist.requiredStatus = paramInputStream.read_string();
/*  83: 81 */     localBPWHist.category = paramInputStream.read_string();
/*  84: 82 */     return localBPWHist;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static void write(OutputStream paramOutputStream, BPWHist paramBPWHist)
/*  88:    */   {
/*  89: 89 */     if (paramBPWHist == null) {
/*  90: 91 */       paramBPWHist = new BPWHist();
/*  91:    */     }
/*  92: 93 */     paramOutputStream.write_string(paramBPWHist.custId);
/*  93: 94 */     paramOutputStream.write_string(paramBPWHist.fiId);
/*  94: 95 */     paramOutputStream.write_string(paramBPWHist.payeeId);
/*  95: 96 */     paramOutputStream.write_string(paramBPWHist.acctId);
/*  96: 97 */     ObjectSeqHelper.write(paramOutputStream, paramBPWHist.trans);
/*  97: 98 */     paramOutputStream.write_string(paramBPWHist.startDate);
/*  98: 99 */     paramOutputStream.write_string(paramBPWHist.endDate);
/*  99:100 */     paramOutputStream.write_string(paramBPWHist.version);
/* 100:101 */     StringSeqHelper.write(paramOutputStream, paramBPWHist.submittedBy);
/* 101:102 */     StringSeqHelper.write(paramOutputStream, paramBPWHist.userId);
/* 102:103 */     StringSeqHelper.write(paramOutputStream, paramBPWHist.statusList);
/* 103:104 */     paramOutputStream.write_string(paramBPWHist.transType);
/* 104:105 */     paramOutputStream.write_string(paramBPWHist.transScope);
/* 105:106 */     StringSeqHelper.write(paramOutputStream, paramBPWHist.dest);
/* 106:107 */     paramOutputStream.write_string(paramBPWHist.templateId);
/* 107:108 */     paramOutputStream.write_long(paramBPWHist.statusCode);
/* 108:109 */     paramOutputStream.write_string(paramBPWHist.statusMsg);
/* 109:110 */     paramOutputStream.write_string(paramBPWHist.transSource);
/* 110:111 */     paramOutputStream.write_string(paramBPWHist.minAmount);
/* 111:112 */     paramOutputStream.write_string(paramBPWHist.maxAmount);
/* 112:113 */     paramOutputStream.write_string(paramBPWHist.selectionCriteria);
/* 113:114 */     paramOutputStream.write_longlong(paramBPWHist.totalTrans);
/* 114:115 */     paramOutputStream.write_longlong(paramBPWHist.pageSize);
/* 115:116 */     paramOutputStream.write_string(paramBPWHist.histId);
/* 116:117 */     paramOutputStream.write_string(paramBPWHist.cursorId);
/* 117:118 */     paramOutputStream.write_string(paramBPWHist.requiredStatus);
/* 118:119 */     paramOutputStream.write_string(paramBPWHist.category);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static String _idl()
/* 122:    */   {
/* 123:124 */     return "com::ffusion::ffs::bpw::interfaces::BPWHist";
/* 124:    */   }
/* 125:    */   
/* 126:    */   public static TypeCode type()
/* 127:    */   {
/* 128:131 */     if (_type == null)
/* 129:    */     {
/* 130:133 */       ORB localORB = ORB.init();
/* 131:134 */       StructMember[] arrayOfStructMember = 
/* 132:135 */         {
/* 133:136 */         new StructMember("custId", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 134:137 */         new StructMember("fiId", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 135:138 */         new StructMember("payeeId", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 136:139 */         new StructMember("acctId", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 137:140 */         new StructMember("trans", ObjectSeqHelper.type(), null), 
/* 138:141 */         new StructMember("startDate", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 139:142 */         new StructMember("endDate", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 140:143 */         new StructMember("version", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 141:144 */         new StructMember("submittedBy", StringSeqHelper.type(), null), 
/* 142:145 */         new StructMember("userId", StringSeqHelper.type(), null), 
/* 143:146 */         new StructMember("statusList", StringSeqHelper.type(), null), 
/* 144:147 */         new StructMember("transType", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 145:148 */         new StructMember("transScope", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 146:149 */         new StructMember("dest", StringSeqHelper.type(), null), 
/* 147:150 */         new StructMember("templateId", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 148:151 */         new StructMember("statusCode", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 149:152 */         new StructMember("statusMsg", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 150:153 */         new StructMember("transSource", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 151:154 */         new StructMember("minAmount", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 152:155 */         new StructMember("maxAmount", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 153:156 */         new StructMember("selectionCriteria", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 154:157 */         new StructMember("totalTrans", localORB.get_primitive_tc(TCKind.tk_longlong), null), 
/* 155:158 */         new StructMember("pageSize", localORB.get_primitive_tc(TCKind.tk_longlong), null), 
/* 156:159 */         new StructMember("histId", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 157:160 */         new StructMember("cursorId", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 158:161 */         new StructMember("requiredStatus", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 159:162 */         new StructMember("category", localORB.get_primitive_tc(TCKind.tk_string), null) };
/* 160:    */       
/* 161:164 */       _type = localORB.create_struct_tc(id(), "BPWHist", arrayOfStructMember);
/* 162:    */     }
/* 163:166 */     return _type;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public static void insert(Any paramAny, BPWHist paramBPWHist)
/* 167:    */   {
/* 168:173 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 169:174 */     write(localOutputStream, paramBPWHist);
/* 170:175 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 171:    */   }
/* 172:    */   
/* 173:    */   public static BPWHist extract(Any paramAny)
/* 174:    */   {
/* 175:181 */     if (!paramAny.type().equal(type())) {
/* 176:183 */       throw new BAD_OPERATION();
/* 177:    */     }
/* 178:185 */     return read(paramAny.create_input_stream());
/* 179:    */   }
/* 180:    */   
/* 181:    */   public static String id()
/* 182:    */   {
/* 183:190 */     return "IDL:com/ffusion/ffs/bpw/interfaces/BPWHist:1.0";
/* 184:    */   }
/* 185:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.BPWHistHelper
 * JD-Core Version:    0.7.0.1
 */