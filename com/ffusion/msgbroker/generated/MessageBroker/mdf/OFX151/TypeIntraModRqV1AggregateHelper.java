/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*  2:   */ 
/*  3:   */ import com.sybase.CORBA.ObjectVal;
/*  4:   */ import org.omg.CORBA.Any;
/*  5:   */ import org.omg.CORBA.BAD_OPERATION;
/*  6:   */ import org.omg.CORBA.ORB;
/*  7:   */ import org.omg.CORBA.StructMember;
/*  8:   */ import org.omg.CORBA.TCKind;
/*  9:   */ import org.omg.CORBA.TypeCode;
/* 10:   */ import org.omg.CORBA.portable.InputStream;
/* 11:   */ import org.omg.CORBA.portable.OutputStream;
/* 12:   */ 
/* 13:   */ public abstract class TypeIntraModRqV1AggregateHelper
/* 14:   */ {
/* 15:   */   public static TypeCode _type;
/* 16:   */   
/* 17:   */   public static TypeIntraModRqV1Aggregate clone(TypeIntraModRqV1Aggregate paramTypeIntraModRqV1Aggregate)
/* 18:   */   {
/* 19:16 */     if (paramTypeIntraModRqV1Aggregate == null) {
/* 20:18 */       return null;
/* 21:   */     }
/* 22:20 */     TypeIntraModRqV1Aggregate localTypeIntraModRqV1Aggregate = new TypeIntraModRqV1Aggregate();
/* 23:21 */     localTypeIntraModRqV1Aggregate.CurDef = ((EnumCurrencyEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypeIntraModRqV1Aggregate.CurDef)));
/* 24:22 */     localTypeIntraModRqV1Aggregate.CurDefExists = paramTypeIntraModRqV1Aggregate.CurDefExists;
/* 25:23 */     localTypeIntraModRqV1Aggregate.SrvrTID = paramTypeIntraModRqV1Aggregate.SrvrTID;
/* 26:24 */     localTypeIntraModRqV1Aggregate.XferInfo = TypeXferInfoV1AggregateHelper.clone(paramTypeIntraModRqV1Aggregate.XferInfo);
/* 27:25 */     return localTypeIntraModRqV1Aggregate;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public static TypeIntraModRqV1Aggregate read(InputStream paramInputStream)
/* 31:   */   {
/* 32:31 */     TypeIntraModRqV1Aggregate localTypeIntraModRqV1Aggregate = new TypeIntraModRqV1Aggregate();
/* 33:32 */     localTypeIntraModRqV1Aggregate.CurDef = EnumCurrencyEnumHelper.read(paramInputStream);
/* 34:33 */     localTypeIntraModRqV1Aggregate.CurDefExists = paramInputStream.read_boolean();
/* 35:34 */     localTypeIntraModRqV1Aggregate.SrvrTID = paramInputStream.read_string();
/* 36:35 */     localTypeIntraModRqV1Aggregate.XferInfo = TypeXferInfoV1AggregateHelper.read(paramInputStream);
/* 37:36 */     return localTypeIntraModRqV1Aggregate;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public static void write(OutputStream paramOutputStream, TypeIntraModRqV1Aggregate paramTypeIntraModRqV1Aggregate)
/* 41:   */   {
/* 42:43 */     if (paramTypeIntraModRqV1Aggregate == null) {
/* 43:45 */       paramTypeIntraModRqV1Aggregate = new TypeIntraModRqV1Aggregate();
/* 44:   */     }
/* 45:47 */     EnumCurrencyEnumHelper.write(paramOutputStream, paramTypeIntraModRqV1Aggregate.CurDef);
/* 46:48 */     paramOutputStream.write_boolean(paramTypeIntraModRqV1Aggregate.CurDefExists);
/* 47:49 */     paramOutputStream.write_string(paramTypeIntraModRqV1Aggregate.SrvrTID);
/* 48:50 */     TypeXferInfoV1AggregateHelper.write(paramOutputStream, paramTypeIntraModRqV1Aggregate.XferInfo);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public static String _idl()
/* 52:   */   {
/* 53:55 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeIntraModRqV1Aggregate";
/* 54:   */   }
/* 55:   */   
/* 56:   */   public static TypeCode type()
/* 57:   */   {
/* 58:62 */     if (_type == null)
/* 59:   */     {
/* 60:64 */       ORB localORB = ORB.init();
/* 61:65 */       StructMember[] arrayOfStructMember = 
/* 62:66 */         {
/* 63:67 */         new StructMember("CurDef", EnumCurrencyEnumHelper.type(), null), 
/* 64:68 */         new StructMember("CurDefExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 65:69 */         new StructMember("SrvrTID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 66:70 */         new StructMember("XferInfo", TypeXferInfoV1AggregateHelper.type(), null) };
/* 67:   */       
/* 68:72 */       _type = localORB.create_struct_tc(id(), "TypeIntraModRqV1Aggregate", arrayOfStructMember);
/* 69:   */     }
/* 70:74 */     return _type;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public static void insert(Any paramAny, TypeIntraModRqV1Aggregate paramTypeIntraModRqV1Aggregate)
/* 74:   */   {
/* 75:81 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 76:82 */     write(localOutputStream, paramTypeIntraModRqV1Aggregate);
/* 77:83 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 78:   */   }
/* 79:   */   
/* 80:   */   public static TypeIntraModRqV1Aggregate extract(Any paramAny)
/* 81:   */   {
/* 82:89 */     if (!paramAny.type().equal(type())) {
/* 83:91 */       throw new BAD_OPERATION();
/* 84:   */     }
/* 85:93 */     return read(paramAny.create_input_stream());
/* 86:   */   }
/* 87:   */   
/* 88:   */   public static String id()
/* 89:   */   {
/* 90:98 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeIntraModRqV1Aggregate:1.0";
/* 91:   */   }
/* 92:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraModRqV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */