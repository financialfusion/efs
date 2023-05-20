/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*   2:    */ 
/*   3:    */ import com.sybase.ejb.cts.StringSeqHelper;
/*   4:    */ import org.omg.CORBA.Any;
/*   5:    */ import org.omg.CORBA.BAD_OPERATION;
/*   6:    */ import org.omg.CORBA.ORB;
/*   7:    */ import org.omg.CORBA.StructMember;
/*   8:    */ import org.omg.CORBA.TCKind;
/*   9:    */ import org.omg.CORBA.TypeCode;
/*  10:    */ import org.omg.CORBA.portable.InputStream;
/*  11:    */ import org.omg.CORBA.portable.OutputStream;
/*  12:    */ 
/*  13:    */ public abstract class TypePayeeModRqV1AggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypePayeeModRqV1Aggregate clone(TypePayeeModRqV1Aggregate paramTypePayeeModRqV1Aggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypePayeeModRqV1Aggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypePayeeModRqV1Aggregate localTypePayeeModRqV1Aggregate = new TypePayeeModRqV1Aggregate();
/*  23: 21 */     localTypePayeeModRqV1Aggregate.PayeeLstID = paramTypePayeeModRqV1Aggregate.PayeeLstID;
/*  24: 22 */     localTypePayeeModRqV1Aggregate.PayeeModRqV1Cm = TypePayeeModRqV1CmHelper.clone(paramTypePayeeModRqV1Aggregate.PayeeModRqV1Cm);
/*  25: 23 */     localTypePayeeModRqV1Aggregate.PayeeModRqV1CmExists = paramTypePayeeModRqV1Aggregate.PayeeModRqV1CmExists;
/*  26: 24 */     localTypePayeeModRqV1Aggregate.PayAcct = StringSeqHelper.clone(paramTypePayeeModRqV1Aggregate.PayAcct);
/*  27: 25 */     localTypePayeeModRqV1Aggregate.NameOnAcct = StringSeqHelper.clone(paramTypePayeeModRqV1Aggregate.NameOnAcct);
/*  28: 26 */     localTypePayeeModRqV1Aggregate.NameOnAcctExists = paramTypePayeeModRqV1Aggregate.NameOnAcctExists;
/*  29: 27 */     return localTypePayeeModRqV1Aggregate;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static TypePayeeModRqV1Aggregate read(InputStream paramInputStream)
/*  33:    */   {
/*  34: 33 */     TypePayeeModRqV1Aggregate localTypePayeeModRqV1Aggregate = new TypePayeeModRqV1Aggregate();
/*  35: 34 */     localTypePayeeModRqV1Aggregate.PayeeLstID = paramInputStream.read_string();
/*  36: 35 */     localTypePayeeModRqV1Aggregate.PayeeModRqV1Cm = TypePayeeModRqV1CmHelper.read(paramInputStream);
/*  37: 36 */     localTypePayeeModRqV1Aggregate.PayeeModRqV1CmExists = paramInputStream.read_boolean();
/*  38: 37 */     localTypePayeeModRqV1Aggregate.PayAcct = StringSeqHelper.read(paramInputStream);
/*  39: 38 */     localTypePayeeModRqV1Aggregate.NameOnAcct = StringSeqHelper.read(paramInputStream);
/*  40: 39 */     localTypePayeeModRqV1Aggregate.NameOnAcctExists = paramInputStream.read_boolean();
/*  41: 40 */     return localTypePayeeModRqV1Aggregate;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static void write(OutputStream paramOutputStream, TypePayeeModRqV1Aggregate paramTypePayeeModRqV1Aggregate)
/*  45:    */   {
/*  46: 47 */     if (paramTypePayeeModRqV1Aggregate == null) {
/*  47: 49 */       paramTypePayeeModRqV1Aggregate = new TypePayeeModRqV1Aggregate();
/*  48:    */     }
/*  49: 51 */     paramOutputStream.write_string(paramTypePayeeModRqV1Aggregate.PayeeLstID);
/*  50: 52 */     TypePayeeModRqV1CmHelper.write(paramOutputStream, paramTypePayeeModRqV1Aggregate.PayeeModRqV1Cm);
/*  51: 53 */     paramOutputStream.write_boolean(paramTypePayeeModRqV1Aggregate.PayeeModRqV1CmExists);
/*  52: 54 */     StringSeqHelper.write(paramOutputStream, paramTypePayeeModRqV1Aggregate.PayAcct);
/*  53: 55 */     StringSeqHelper.write(paramOutputStream, paramTypePayeeModRqV1Aggregate.NameOnAcct);
/*  54: 56 */     paramOutputStream.write_boolean(paramTypePayeeModRqV1Aggregate.NameOnAcctExists);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static String _idl()
/*  58:    */   {
/*  59: 61 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypePayeeModRqV1Aggregate";
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static TypeCode type()
/*  63:    */   {
/*  64: 68 */     if (_type == null)
/*  65:    */     {
/*  66: 70 */       ORB localORB = ORB.init();
/*  67: 71 */       StructMember[] arrayOfStructMember = 
/*  68: 72 */         {
/*  69: 73 */         new StructMember("PayeeLstID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  70: 74 */         new StructMember("PayeeModRqV1Cm", TypePayeeModRqV1CmHelper.type(), null), 
/*  71: 75 */         new StructMember("PayeeModRqV1CmExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  72: 76 */         new StructMember("PayAcct", StringSeqHelper.type(), null), 
/*  73: 77 */         new StructMember("NameOnAcct", StringSeqHelper.type(), null), 
/*  74: 78 */         new StructMember("NameOnAcctExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  75:    */       
/*  76: 80 */       _type = localORB.create_struct_tc(id(), "TypePayeeModRqV1Aggregate", arrayOfStructMember);
/*  77:    */     }
/*  78: 82 */     return _type;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static void insert(Any paramAny, TypePayeeModRqV1Aggregate paramTypePayeeModRqV1Aggregate)
/*  82:    */   {
/*  83: 89 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  84: 90 */     write(localOutputStream, paramTypePayeeModRqV1Aggregate);
/*  85: 91 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static TypePayeeModRqV1Aggregate extract(Any paramAny)
/*  89:    */   {
/*  90: 97 */     if (!paramAny.type().equal(type())) {
/*  91: 99 */       throw new BAD_OPERATION();
/*  92:    */     }
/*  93:101 */     return read(paramAny.create_input_stream());
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static String id()
/*  97:    */   {
/*  98:106 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePayeeModRqV1Aggregate:1.0";
/*  99:    */   }
/* 100:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeModRqV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */