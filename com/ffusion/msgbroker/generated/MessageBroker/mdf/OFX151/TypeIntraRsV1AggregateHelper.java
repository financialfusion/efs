/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*   2:    */ 
/*   3:    */ import com.sybase.CORBA.ObjectVal;
/*   4:    */ import org.omg.CORBA.Any;
/*   5:    */ import org.omg.CORBA.BAD_OPERATION;
/*   6:    */ import org.omg.CORBA.ORB;
/*   7:    */ import org.omg.CORBA.StructMember;
/*   8:    */ import org.omg.CORBA.TCKind;
/*   9:    */ import org.omg.CORBA.TypeCode;
/*  10:    */ import org.omg.CORBA.portable.InputStream;
/*  11:    */ import org.omg.CORBA.portable.OutputStream;
/*  12:    */ 
/*  13:    */ public abstract class TypeIntraRsV1AggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypeIntraRsV1Aggregate clone(TypeIntraRsV1Aggregate paramTypeIntraRsV1Aggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypeIntraRsV1Aggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypeIntraRsV1Aggregate localTypeIntraRsV1Aggregate = new TypeIntraRsV1Aggregate();
/*  23: 21 */     localTypeIntraRsV1Aggregate.CurDef = ((EnumCurrencyEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypeIntraRsV1Aggregate.CurDef)));
/*  24: 22 */     localTypeIntraRsV1Aggregate.SrvrTID = paramTypeIntraRsV1Aggregate.SrvrTID;
/*  25: 23 */     localTypeIntraRsV1Aggregate.XferInfo = TypeXferInfoV1AggregateHelper.clone(paramTypeIntraRsV1Aggregate.XferInfo);
/*  26: 24 */     localTypeIntraRsV1Aggregate.IntraRsV1DateUn = TypeIntraRsV1DateUnHelper.clone(paramTypeIntraRsV1Aggregate.IntraRsV1DateUn);
/*  27: 25 */     localTypeIntraRsV1Aggregate.IntraRsV1DateUnExists = paramTypeIntraRsV1Aggregate.IntraRsV1DateUnExists;
/*  28: 26 */     localTypeIntraRsV1Aggregate.RecSrvrTID = paramTypeIntraRsV1Aggregate.RecSrvrTID;
/*  29: 27 */     localTypeIntraRsV1Aggregate.RecSrvrTIDExists = paramTypeIntraRsV1Aggregate.RecSrvrTIDExists;
/*  30: 28 */     localTypeIntraRsV1Aggregate.XferPrcSts = TypeXferPrcStsAggregateHelper.clone(paramTypeIntraRsV1Aggregate.XferPrcSts);
/*  31: 29 */     localTypeIntraRsV1Aggregate.XferPrcStsExists = paramTypeIntraRsV1Aggregate.XferPrcStsExists;
/*  32: 30 */     return localTypeIntraRsV1Aggregate;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static TypeIntraRsV1Aggregate read(InputStream paramInputStream)
/*  36:    */   {
/*  37: 36 */     TypeIntraRsV1Aggregate localTypeIntraRsV1Aggregate = new TypeIntraRsV1Aggregate();
/*  38: 37 */     localTypeIntraRsV1Aggregate.CurDef = EnumCurrencyEnumHelper.read(paramInputStream);
/*  39: 38 */     localTypeIntraRsV1Aggregate.SrvrTID = paramInputStream.read_string();
/*  40: 39 */     localTypeIntraRsV1Aggregate.XferInfo = TypeXferInfoV1AggregateHelper.read(paramInputStream);
/*  41: 40 */     localTypeIntraRsV1Aggregate.IntraRsV1DateUn = TypeIntraRsV1DateUnHelper.read(paramInputStream);
/*  42: 41 */     localTypeIntraRsV1Aggregate.IntraRsV1DateUnExists = paramInputStream.read_boolean();
/*  43: 42 */     localTypeIntraRsV1Aggregate.RecSrvrTID = paramInputStream.read_string();
/*  44: 43 */     localTypeIntraRsV1Aggregate.RecSrvrTIDExists = paramInputStream.read_boolean();
/*  45: 44 */     localTypeIntraRsV1Aggregate.XferPrcSts = TypeXferPrcStsAggregateHelper.read(paramInputStream);
/*  46: 45 */     localTypeIntraRsV1Aggregate.XferPrcStsExists = paramInputStream.read_boolean();
/*  47: 46 */     return localTypeIntraRsV1Aggregate;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static void write(OutputStream paramOutputStream, TypeIntraRsV1Aggregate paramTypeIntraRsV1Aggregate)
/*  51:    */   {
/*  52: 53 */     if (paramTypeIntraRsV1Aggregate == null) {
/*  53: 55 */       paramTypeIntraRsV1Aggregate = new TypeIntraRsV1Aggregate();
/*  54:    */     }
/*  55: 57 */     EnumCurrencyEnumHelper.write(paramOutputStream, paramTypeIntraRsV1Aggregate.CurDef);
/*  56: 58 */     paramOutputStream.write_string(paramTypeIntraRsV1Aggregate.SrvrTID);
/*  57: 59 */     TypeXferInfoV1AggregateHelper.write(paramOutputStream, paramTypeIntraRsV1Aggregate.XferInfo);
/*  58: 60 */     TypeIntraRsV1DateUnHelper.write(paramOutputStream, paramTypeIntraRsV1Aggregate.IntraRsV1DateUn);
/*  59: 61 */     paramOutputStream.write_boolean(paramTypeIntraRsV1Aggregate.IntraRsV1DateUnExists);
/*  60: 62 */     paramOutputStream.write_string(paramTypeIntraRsV1Aggregate.RecSrvrTID);
/*  61: 63 */     paramOutputStream.write_boolean(paramTypeIntraRsV1Aggregate.RecSrvrTIDExists);
/*  62: 64 */     TypeXferPrcStsAggregateHelper.write(paramOutputStream, paramTypeIntraRsV1Aggregate.XferPrcSts);
/*  63: 65 */     paramOutputStream.write_boolean(paramTypeIntraRsV1Aggregate.XferPrcStsExists);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static String _idl()
/*  67:    */   {
/*  68: 70 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeIntraRsV1Aggregate";
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static TypeCode type()
/*  72:    */   {
/*  73: 77 */     if (_type == null)
/*  74:    */     {
/*  75: 79 */       ORB localORB = ORB.init();
/*  76: 80 */       StructMember[] arrayOfStructMember = 
/*  77: 81 */         {
/*  78: 82 */         new StructMember("CurDef", EnumCurrencyEnumHelper.type(), null), 
/*  79: 83 */         new StructMember("SrvrTID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  80: 84 */         new StructMember("XferInfo", TypeXferInfoV1AggregateHelper.type(), null), 
/*  81: 85 */         new StructMember("IntraRsV1DateUn", TypeIntraRsV1DateUnHelper.type(), null), 
/*  82: 86 */         new StructMember("IntraRsV1DateUnExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  83: 87 */         new StructMember("RecSrvrTID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  84: 88 */         new StructMember("RecSrvrTIDExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  85: 89 */         new StructMember("XferPrcSts", TypeXferPrcStsAggregateHelper.type(), null), 
/*  86: 90 */         new StructMember("XferPrcStsExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  87:    */       
/*  88: 92 */       _type = localORB.create_struct_tc(id(), "TypeIntraRsV1Aggregate", arrayOfStructMember);
/*  89:    */     }
/*  90: 94 */     return _type;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static void insert(Any paramAny, TypeIntraRsV1Aggregate paramTypeIntraRsV1Aggregate)
/*  94:    */   {
/*  95:101 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  96:102 */     write(localOutputStream, paramTypeIntraRsV1Aggregate);
/*  97:103 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static TypeIntraRsV1Aggregate extract(Any paramAny)
/* 101:    */   {
/* 102:109 */     if (!paramAny.type().equal(type())) {
/* 103:111 */       throw new BAD_OPERATION();
/* 104:    */     }
/* 105:113 */     return read(paramAny.create_input_stream());
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static String id()
/* 109:    */   {
/* 110:118 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeIntraRsV1Aggregate:1.0";
/* 111:    */   }
/* 112:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraRsV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */