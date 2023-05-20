/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
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
/*  13:    */ public abstract class TypeIntraRsAggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypeIntraRsAggregate clone(TypeIntraRsAggregate paramTypeIntraRsAggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypeIntraRsAggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypeIntraRsAggregate localTypeIntraRsAggregate = new TypeIntraRsAggregate();
/*  23: 21 */     localTypeIntraRsAggregate.CurDef = ((EnumCurrencyEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypeIntraRsAggregate.CurDef)));
/*  24: 22 */     localTypeIntraRsAggregate.SrvrTID = paramTypeIntraRsAggregate.SrvrTID;
/*  25: 23 */     localTypeIntraRsAggregate.XferInfo = TypeXferInfoAggregateHelper.clone(paramTypeIntraRsAggregate.XferInfo);
/*  26: 24 */     localTypeIntraRsAggregate.IntraRsDateUn = TypeIntraRsDateUnHelper.clone(paramTypeIntraRsAggregate.IntraRsDateUn);
/*  27: 25 */     localTypeIntraRsAggregate.IntraRsDateUnExists = paramTypeIntraRsAggregate.IntraRsDateUnExists;
/*  28: 26 */     localTypeIntraRsAggregate.RecSrvrTID = paramTypeIntraRsAggregate.RecSrvrTID;
/*  29: 27 */     localTypeIntraRsAggregate.RecSrvrTIDExists = paramTypeIntraRsAggregate.RecSrvrTIDExists;
/*  30: 28 */     localTypeIntraRsAggregate.XferPrcSts = TypeXferPrcStsAggregateHelper.clone(paramTypeIntraRsAggregate.XferPrcSts);
/*  31: 29 */     localTypeIntraRsAggregate.XferPrcStsExists = paramTypeIntraRsAggregate.XferPrcStsExists;
/*  32: 30 */     return localTypeIntraRsAggregate;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static TypeIntraRsAggregate read(InputStream paramInputStream)
/*  36:    */   {
/*  37: 36 */     TypeIntraRsAggregate localTypeIntraRsAggregate = new TypeIntraRsAggregate();
/*  38: 37 */     localTypeIntraRsAggregate.CurDef = EnumCurrencyEnumHelper.read(paramInputStream);
/*  39: 38 */     localTypeIntraRsAggregate.SrvrTID = paramInputStream.read_string();
/*  40: 39 */     localTypeIntraRsAggregate.XferInfo = TypeXferInfoAggregateHelper.read(paramInputStream);
/*  41: 40 */     localTypeIntraRsAggregate.IntraRsDateUn = TypeIntraRsDateUnHelper.read(paramInputStream);
/*  42: 41 */     localTypeIntraRsAggregate.IntraRsDateUnExists = paramInputStream.read_boolean();
/*  43: 42 */     localTypeIntraRsAggregate.RecSrvrTID = paramInputStream.read_string();
/*  44: 43 */     localTypeIntraRsAggregate.RecSrvrTIDExists = paramInputStream.read_boolean();
/*  45: 44 */     localTypeIntraRsAggregate.XferPrcSts = TypeXferPrcStsAggregateHelper.read(paramInputStream);
/*  46: 45 */     localTypeIntraRsAggregate.XferPrcStsExists = paramInputStream.read_boolean();
/*  47: 46 */     return localTypeIntraRsAggregate;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static void write(OutputStream paramOutputStream, TypeIntraRsAggregate paramTypeIntraRsAggregate)
/*  51:    */   {
/*  52: 53 */     if (paramTypeIntraRsAggregate == null) {
/*  53: 55 */       paramTypeIntraRsAggregate = new TypeIntraRsAggregate();
/*  54:    */     }
/*  55: 57 */     EnumCurrencyEnumHelper.write(paramOutputStream, paramTypeIntraRsAggregate.CurDef);
/*  56: 58 */     paramOutputStream.write_string(paramTypeIntraRsAggregate.SrvrTID);
/*  57: 59 */     TypeXferInfoAggregateHelper.write(paramOutputStream, paramTypeIntraRsAggregate.XferInfo);
/*  58: 60 */     TypeIntraRsDateUnHelper.write(paramOutputStream, paramTypeIntraRsAggregate.IntraRsDateUn);
/*  59: 61 */     paramOutputStream.write_boolean(paramTypeIntraRsAggregate.IntraRsDateUnExists);
/*  60: 62 */     paramOutputStream.write_string(paramTypeIntraRsAggregate.RecSrvrTID);
/*  61: 63 */     paramOutputStream.write_boolean(paramTypeIntraRsAggregate.RecSrvrTIDExists);
/*  62: 64 */     TypeXferPrcStsAggregateHelper.write(paramOutputStream, paramTypeIntraRsAggregate.XferPrcSts);
/*  63: 65 */     paramOutputStream.write_boolean(paramTypeIntraRsAggregate.XferPrcStsExists);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static String _idl()
/*  67:    */   {
/*  68: 70 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeIntraRsAggregate";
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
/*  80: 84 */         new StructMember("XferInfo", TypeXferInfoAggregateHelper.type(), null), 
/*  81: 85 */         new StructMember("IntraRsDateUn", TypeIntraRsDateUnHelper.type(), null), 
/*  82: 86 */         new StructMember("IntraRsDateUnExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  83: 87 */         new StructMember("RecSrvrTID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  84: 88 */         new StructMember("RecSrvrTIDExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  85: 89 */         new StructMember("XferPrcSts", TypeXferPrcStsAggregateHelper.type(), null), 
/*  86: 90 */         new StructMember("XferPrcStsExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  87:    */       
/*  88: 92 */       _type = localORB.create_struct_tc(id(), "TypeIntraRsAggregate", arrayOfStructMember);
/*  89:    */     }
/*  90: 94 */     return _type;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static void insert(Any paramAny, TypeIntraRsAggregate paramTypeIntraRsAggregate)
/*  94:    */   {
/*  95:101 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  96:102 */     write(localOutputStream, paramTypeIntraRsAggregate);
/*  97:103 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static TypeIntraRsAggregate extract(Any paramAny)
/* 101:    */   {
/* 102:109 */     if (!paramAny.type().equal(type())) {
/* 103:111 */       throw new BAD_OPERATION();
/* 104:    */     }
/* 105:113 */     return read(paramAny.create_input_stream());
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static String id()
/* 109:    */   {
/* 110:118 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeIntraRsAggregate:1.0";
/* 111:    */   }
/* 112:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRsAggregateHelper
 * JD-Core Version:    0.7.0.1
 */