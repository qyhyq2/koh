/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.koh.thrift.second;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)", date = "2018-05-09")
public class SecondService {

  public interface Iface {

    public short add(int a, int b) throws org.apache.thrift.TException;

  }

  public interface AsyncIface {

    public void add(int a, int b, org.apache.thrift.async.AsyncMethodCallback<java.lang.Short> resultHandler) throws org.apache.thrift.TException;

  }

  public static class Client extends org.apache.thrift.TServiceClient implements Iface {
    public static class Factory implements org.apache.thrift.TServiceClientFactory<Client> {
      public Factory() {}
      public Client getClient(org.apache.thrift.protocol.TProtocol prot) {
        return new Client(prot);
      }
      public Client getClient(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
        return new Client(iprot, oprot);
      }
    }

    public Client(org.apache.thrift.protocol.TProtocol prot)
    {
      super(prot, prot);
    }

    public Client(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
      super(iprot, oprot);
    }

    public short add(int a, int b) throws org.apache.thrift.TException
    {
      send_add(a, b);
      return recv_add();
    }

    public void send_add(int a, int b) throws org.apache.thrift.TException
    {
      add_args args = new add_args();
      args.setA(a);
      args.setB(b);
      sendBase("add", args);
    }

    public short recv_add() throws org.apache.thrift.TException
    {
      add_result result = new add_result();
      receiveBase(result, "add");
      if (result.isSetSuccess()) {
        return result.success;
      }
      throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT, "add failed: unknown result");
    }

  }
  public static class AsyncClient extends org.apache.thrift.async.TAsyncClient implements AsyncIface {
    public static class Factory implements org.apache.thrift.async.TAsyncClientFactory<AsyncClient> {
      private org.apache.thrift.async.TAsyncClientManager clientManager;
      private org.apache.thrift.protocol.TProtocolFactory protocolFactory;
      public Factory(org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.protocol.TProtocolFactory protocolFactory) {
        this.clientManager = clientManager;
        this.protocolFactory = protocolFactory;
      }
      public AsyncClient getAsyncClient(org.apache.thrift.transport.TNonblockingTransport transport) {
        return new AsyncClient(protocolFactory, clientManager, transport);
      }
    }

    public AsyncClient(org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.transport.TNonblockingTransport transport) {
      super(protocolFactory, clientManager, transport);
    }

    public void add(int a, int b, org.apache.thrift.async.AsyncMethodCallback<java.lang.Short> resultHandler) throws org.apache.thrift.TException {
      checkReady();
      add_call method_call = new add_call(a, b, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class add_call extends org.apache.thrift.async.TAsyncMethodCall<java.lang.Short> {
      private int a;
      private int b;
      public add_call(int a, int b, org.apache.thrift.async.AsyncMethodCallback<java.lang.Short> resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.a = a;
        this.b = b;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("add", org.apache.thrift.protocol.TMessageType.CALL, 0));
        add_args args = new add_args();
        args.setA(a);
        args.setB(b);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public java.lang.Short getResult() throws org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new java.lang.IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        return (new Client(prot)).recv_add();
      }
    }

  }

  public static class Processor<I extends Iface> extends org.apache.thrift.TBaseProcessor<I> implements org.apache.thrift.TProcessor {
    private static final org.slf4j.Logger _LOGGER = org.slf4j.LoggerFactory.getLogger(Processor.class.getName());
    public Processor(I iface) {
      super(iface, getProcessMap(new java.util.HashMap<java.lang.String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
    }

    protected Processor(I iface, java.util.Map<java.lang.String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends Iface> java.util.Map<java.lang.String,  org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> getProcessMap(java.util.Map<java.lang.String, org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      processMap.put("add", new add());
      return processMap;
    }

    public static class add<I extends Iface> extends org.apache.thrift.ProcessFunction<I, add_args> {
      public add() {
        super("add");
      }

      public add_args getEmptyArgsInstance() {
        return new add_args();
      }

      protected boolean isOneway() {
        return false;
      }

      @Override
      protected boolean handleRuntimeExceptions() {
        return false;
      }

      public add_result getResult(I iface, add_args args) throws org.apache.thrift.TException {
        add_result result = new add_result();
        result.success = iface.add(args.a, args.b);
        result.setSuccessIsSet(true);
        return result;
      }
    }

  }

  public static class AsyncProcessor<I extends AsyncIface> extends org.apache.thrift.TBaseAsyncProcessor<I> {
    private static final org.slf4j.Logger _LOGGER = org.slf4j.LoggerFactory.getLogger(AsyncProcessor.class.getName());
    public AsyncProcessor(I iface) {
      super(iface, getProcessMap(new java.util.HashMap<java.lang.String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>>()));
    }

    protected AsyncProcessor(I iface, java.util.Map<java.lang.String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends AsyncIface> java.util.Map<java.lang.String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase,?>> getProcessMap(java.util.Map<java.lang.String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      processMap.put("add", new add());
      return processMap;
    }

    public static class add<I extends AsyncIface> extends org.apache.thrift.AsyncProcessFunction<I, add_args, java.lang.Short> {
      public add() {
        super("add");
      }

      public add_args getEmptyArgsInstance() {
        return new add_args();
      }

      public org.apache.thrift.async.AsyncMethodCallback<java.lang.Short> getResultHandler(final org.apache.thrift.server.AbstractNonblockingServer.AsyncFrameBuffer fb, final int seqid) {
        final org.apache.thrift.AsyncProcessFunction fcall = this;
        return new org.apache.thrift.async.AsyncMethodCallback<java.lang.Short>() {
          public void onComplete(java.lang.Short o) {
            add_result result = new add_result();
            result.success = o;
            result.setSuccessIsSet(true);
            try {
              fcall.sendResponse(fb, result, org.apache.thrift.protocol.TMessageType.REPLY,seqid);
            } catch (org.apache.thrift.transport.TTransportException e) {
              _LOGGER.error("TTransportException writing to internal frame buffer", e);
              fb.close();
            } catch (java.lang.Exception e) {
              _LOGGER.error("Exception writing to internal frame buffer", e);
              onError(e);
            }
          }
          public void onError(java.lang.Exception e) {
            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
            org.apache.thrift.TSerializable msg;
            add_result result = new add_result();
            if (e instanceof org.apache.thrift.transport.TTransportException) {
              _LOGGER.error("TTransportException inside handler", e);
              fb.close();
              return;
            } else if (e instanceof org.apache.thrift.TApplicationException) {
              _LOGGER.error("TApplicationException inside handler", e);
              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
              msg = (org.apache.thrift.TApplicationException)e;
            } else {
              _LOGGER.error("Exception inside handler", e);
              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
              msg = new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.INTERNAL_ERROR, e.getMessage());
            }
            try {
              fcall.sendResponse(fb,msg,msgType,seqid);
            } catch (java.lang.Exception ex) {
              _LOGGER.error("Exception writing to internal frame buffer", ex);
              fb.close();
            }
          }
        };
      }

      protected boolean isOneway() {
        return false;
      }

      public void start(I iface, add_args args, org.apache.thrift.async.AsyncMethodCallback<java.lang.Short> resultHandler) throws org.apache.thrift.TException {
        iface.add(args.a, args.b,resultHandler);
      }
    }

  }

  public static class add_args implements org.apache.thrift.TBase<add_args, add_args._Fields>, java.io.Serializable, Cloneable, Comparable<add_args>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("add_args");

    private static final org.apache.thrift.protocol.TField A_FIELD_DESC = new org.apache.thrift.protocol.TField("a", org.apache.thrift.protocol.TType.I32, (short)1);
    private static final org.apache.thrift.protocol.TField B_FIELD_DESC = new org.apache.thrift.protocol.TField("b", org.apache.thrift.protocol.TType.I32, (short)2);

    private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new add_argsStandardSchemeFactory();
    private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new add_argsTupleSchemeFactory();

    public int a; // required
    public int b; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      A((short)1, "a"),
      B((short)2, "b");

      private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

      static {
        for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // A
            return A;
          case 2: // B
            return B;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(java.lang.String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final java.lang.String _fieldName;

      _Fields(short thriftId, java.lang.String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public java.lang.String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    private static final int __A_ISSET_ID = 0;
    private static final int __B_ISSET_ID = 1;
    private byte __isset_bitfield = 0;
    public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.A, new org.apache.thrift.meta_data.FieldMetaData("a", org.apache.thrift.TFieldRequirementType.DEFAULT,
              new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
      tmpMap.put(_Fields.B, new org.apache.thrift.meta_data.FieldMetaData("b", org.apache.thrift.TFieldRequirementType.DEFAULT,
              new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
      metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(add_args.class, metaDataMap);
    }

    public add_args() {
    }

    public add_args(
            int a,
            int b)
    {
      this();
      this.a = a;
      setAIsSet(true);
      this.b = b;
      setBIsSet(true);
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public add_args(add_args other) {
      __isset_bitfield = other.__isset_bitfield;
      this.a = other.a;
      this.b = other.b;
    }

    public add_args deepCopy() {
      return new add_args(this);
    }

    @Override
    public void clear() {
      setAIsSet(false);
      this.a = 0;
      setBIsSet(false);
      this.b = 0;
    }

    public int getA() {
      return this.a;
    }

    public add_args setA(int a) {
      this.a = a;
      setAIsSet(true);
      return this;
    }

    public void unsetA() {
      __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __A_ISSET_ID);
    }

    /** Returns true if field a is set (has been assigned a value) and false otherwise */
    public boolean isSetA() {
      return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __A_ISSET_ID);
    }

    public void setAIsSet(boolean value) {
      __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __A_ISSET_ID, value);
    }

    public int getB() {
      return this.b;
    }

    public add_args setB(int b) {
      this.b = b;
      setBIsSet(true);
      return this;
    }

    public void unsetB() {
      __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __B_ISSET_ID);
    }

    /** Returns true if field b is set (has been assigned a value) and false otherwise */
    public boolean isSetB() {
      return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __B_ISSET_ID);
    }

    public void setBIsSet(boolean value) {
      __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __B_ISSET_ID, value);
    }

    public void setFieldValue(_Fields field, java.lang.Object value) {
      switch (field) {
        case A:
          if (value == null) {
            unsetA();
          } else {
            setA((java.lang.Integer)value);
          }
          break;

        case B:
          if (value == null) {
            unsetB();
          } else {
            setB((java.lang.Integer)value);
          }
          break;

      }
    }

    public java.lang.Object getFieldValue(_Fields field) {
      switch (field) {
        case A:
          return getA();

        case B:
          return getB();

      }
      throw new java.lang.IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new java.lang.IllegalArgumentException();
      }

      switch (field) {
        case A:
          return isSetA();
        case B:
          return isSetB();
      }
      throw new java.lang.IllegalStateException();
    }

    @Override
    public boolean equals(java.lang.Object that) {
      if (that == null)
        return false;
      if (that instanceof add_args)
        return this.equals((add_args)that);
      return false;
    }

    public boolean equals(add_args that) {
      if (that == null)
        return false;
      if (this == that)
        return true;

      boolean this_present_a = true;
      boolean that_present_a = true;
      if (this_present_a || that_present_a) {
        if (!(this_present_a && that_present_a))
          return false;
        if (this.a != that.a)
          return false;
      }

      boolean this_present_b = true;
      boolean that_present_b = true;
      if (this_present_b || that_present_b) {
        if (!(this_present_b && that_present_b))
          return false;
        if (this.b != that.b)
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      int hashCode = 1;

      hashCode = hashCode * 8191 + a;

      hashCode = hashCode * 8191 + b;

      return hashCode;
    }

    @Override
    public int compareTo(add_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = java.lang.Boolean.valueOf(isSetA()).compareTo(other.isSetA());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetA()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.a, other.a);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = java.lang.Boolean.valueOf(isSetB()).compareTo(other.isSetB());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetB()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.b, other.b);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      scheme(iprot).read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      scheme(oprot).write(oprot, this);
    }

    @Override
    public java.lang.String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder("add_args(");
      boolean first = true;

      sb.append("a:");
      sb.append(this.a);
      first = false;
      if (!first) sb.append(", ");
      sb.append("b:");
      sb.append(this.b);
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
      try {
        // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
        __isset_bitfield = 0;
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class add_argsStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
      public add_argsStandardScheme getScheme() {
        return new add_argsStandardScheme();
      }
    }

    private static class add_argsStandardScheme extends org.apache.thrift.scheme.StandardScheme<add_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, add_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
            break;
          }
          switch (schemeField.id) {
            case 1: // A
              if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                struct.a = iprot.readI32();
                struct.setAIsSet(true);
              } else {
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // B
              if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                struct.b = iprot.readI32();
                struct.setBIsSet(true);
              } else {
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, add_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        oprot.writeFieldBegin(A_FIELD_DESC);
        oprot.writeI32(struct.a);
        oprot.writeFieldEnd();
        oprot.writeFieldBegin(B_FIELD_DESC);
        oprot.writeI32(struct.b);
        oprot.writeFieldEnd();
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class add_argsTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
      public add_argsTupleScheme getScheme() {
        return new add_argsTupleScheme();
      }
    }

    private static class add_argsTupleScheme extends org.apache.thrift.scheme.TupleScheme<add_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, add_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
        java.util.BitSet optionals = new java.util.BitSet();
        if (struct.isSetA()) {
          optionals.set(0);
        }
        if (struct.isSetB()) {
          optionals.set(1);
        }
        oprot.writeBitSet(optionals, 2);
        if (struct.isSetA()) {
          oprot.writeI32(struct.a);
        }
        if (struct.isSetB()) {
          oprot.writeI32(struct.b);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, add_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
        java.util.BitSet incoming = iprot.readBitSet(2);
        if (incoming.get(0)) {
          struct.a = iprot.readI32();
          struct.setAIsSet(true);
        }
        if (incoming.get(1)) {
          struct.b = iprot.readI32();
          struct.setBIsSet(true);
        }
      }
    }

    private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
      return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
    }
  }

  public static class add_result implements org.apache.thrift.TBase<add_result, add_result._Fields>, java.io.Serializable, Cloneable, Comparable<add_result>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("add_result");

    private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.I16, (short)0);

    private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new add_resultStandardSchemeFactory();
    private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new add_resultTupleSchemeFactory();

    public short success; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      SUCCESS((short)0, "success");

      private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

      static {
        for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 0: // SUCCESS
            return SUCCESS;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(java.lang.String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final java.lang.String _fieldName;

      _Fields(short thriftId, java.lang.String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public java.lang.String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    private static final int __SUCCESS_ISSET_ID = 0;
    private byte __isset_bitfield = 0;
    public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT,
              new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
      metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(add_result.class, metaDataMap);
    }

    public add_result() {
    }

    public add_result(
            short success)
    {
      this();
      this.success = success;
      setSuccessIsSet(true);
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public add_result(add_result other) {
      __isset_bitfield = other.__isset_bitfield;
      this.success = other.success;
    }

    public add_result deepCopy() {
      return new add_result(this);
    }

    @Override
    public void clear() {
      setSuccessIsSet(false);
      this.success = 0;
    }

    public short getSuccess() {
      return this.success;
    }

    public add_result setSuccess(short success) {
      this.success = success;
      setSuccessIsSet(true);
      return this;
    }

    public void unsetSuccess() {
      __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __SUCCESS_ISSET_ID);
    }

    /** Returns true if field success is set (has been assigned a value) and false otherwise */
    public boolean isSetSuccess() {
      return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __SUCCESS_ISSET_ID);
    }

    public void setSuccessIsSet(boolean value) {
      __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __SUCCESS_ISSET_ID, value);
    }

    public void setFieldValue(_Fields field, java.lang.Object value) {
      switch (field) {
        case SUCCESS:
          if (value == null) {
            unsetSuccess();
          } else {
            setSuccess((java.lang.Short)value);
          }
          break;

      }
    }

    public java.lang.Object getFieldValue(_Fields field) {
      switch (field) {
        case SUCCESS:
          return getSuccess();

      }
      throw new java.lang.IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new java.lang.IllegalArgumentException();
      }

      switch (field) {
        case SUCCESS:
          return isSetSuccess();
      }
      throw new java.lang.IllegalStateException();
    }

    @Override
    public boolean equals(java.lang.Object that) {
      if (that == null)
        return false;
      if (that instanceof add_result)
        return this.equals((add_result)that);
      return false;
    }

    public boolean equals(add_result that) {
      if (that == null)
        return false;
      if (this == that)
        return true;

      boolean this_present_success = true;
      boolean that_present_success = true;
      if (this_present_success || that_present_success) {
        if (!(this_present_success && that_present_success))
          return false;
        if (this.success != that.success)
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      int hashCode = 1;

      hashCode = hashCode * 8191 + success;

      return hashCode;
    }

    @Override
    public int compareTo(add_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = java.lang.Boolean.valueOf(isSetSuccess()).compareTo(other.isSetSuccess());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSuccess()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, other.success);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      scheme(iprot).read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      scheme(oprot).write(oprot, this);
    }

    @Override
    public java.lang.String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder("add_result(");
      boolean first = true;

      sb.append("success:");
      sb.append(this.success);
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
      try {
        // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
        __isset_bitfield = 0;
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class add_resultStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
      public add_resultStandardScheme getScheme() {
        return new add_resultStandardScheme();
      }
    }

    private static class add_resultStandardScheme extends org.apache.thrift.scheme.StandardScheme<add_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, add_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
            break;
          }
          switch (schemeField.id) {
            case 0: // SUCCESS
              if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
                struct.success = iprot.readI16();
                struct.setSuccessIsSet(true);
              } else {
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, add_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.isSetSuccess()) {
          oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
          oprot.writeI16(struct.success);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class add_resultTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
      public add_resultTupleScheme getScheme() {
        return new add_resultTupleScheme();
      }
    }

    private static class add_resultTupleScheme extends org.apache.thrift.scheme.TupleScheme<add_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, add_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
        java.util.BitSet optionals = new java.util.BitSet();
        if (struct.isSetSuccess()) {
          optionals.set(0);
        }
        oprot.writeBitSet(optionals, 1);
        if (struct.isSetSuccess()) {
          oprot.writeI16(struct.success);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, add_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
        java.util.BitSet incoming = iprot.readBitSet(1);
        if (incoming.get(0)) {
          struct.success = iprot.readI16();
          struct.setSuccessIsSet(true);
        }
      }
    }

    private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
      return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
    }
  }

}
