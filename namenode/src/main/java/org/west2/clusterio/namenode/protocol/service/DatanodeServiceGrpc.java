package org.west2.clusterio.namenode.protocol.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.59.1)",
    comments = "Source: Datanode.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class DatanodeServiceGrpc {

  private DatanodeServiceGrpc() {}

  public static final String SERVICE_NAME = "DatanodeService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.west2.clusterio.namenode.protocol.DatanodeProtocol.RegisterDatanodeRequestProto,
      org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto> getRegisterDatanodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerDatanode",
      requestType = org.west2.clusterio.namenode.protocol.DatanodeProtocol.RegisterDatanodeRequestProto.class,
      responseType = org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.west2.clusterio.namenode.protocol.DatanodeProtocol.RegisterDatanodeRequestProto,
      org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto> getRegisterDatanodeMethod() {
    io.grpc.MethodDescriptor<org.west2.clusterio.namenode.protocol.DatanodeProtocol.RegisterDatanodeRequestProto, org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto> getRegisterDatanodeMethod;
    if ((getRegisterDatanodeMethod = DatanodeServiceGrpc.getRegisterDatanodeMethod) == null) {
      synchronized (DatanodeServiceGrpc.class) {
        if ((getRegisterDatanodeMethod = DatanodeServiceGrpc.getRegisterDatanodeMethod) == null) {
          DatanodeServiceGrpc.getRegisterDatanodeMethod = getRegisterDatanodeMethod =
              io.grpc.MethodDescriptor.<org.west2.clusterio.namenode.protocol.DatanodeProtocol.RegisterDatanodeRequestProto, org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registerDatanode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.west2.clusterio.namenode.protocol.DatanodeProtocol.RegisterDatanodeRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto.getDefaultInstance()))
              .setSchemaDescriptor(new DatanodeServiceMethodDescriptorSupplier("registerDatanode"))
              .build();
        }
      }
    }
    return getRegisterDatanodeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatRequestProto,
      org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto> getHeartbeatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "heartbeat",
      requestType = org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatRequestProto.class,
      responseType = org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatRequestProto,
      org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto> getHeartbeatMethod() {
    io.grpc.MethodDescriptor<org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatRequestProto, org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto> getHeartbeatMethod;
    if ((getHeartbeatMethod = DatanodeServiceGrpc.getHeartbeatMethod) == null) {
      synchronized (DatanodeServiceGrpc.class) {
        if ((getHeartbeatMethod = DatanodeServiceGrpc.getHeartbeatMethod) == null) {
          DatanodeServiceGrpc.getHeartbeatMethod = getHeartbeatMethod =
              io.grpc.MethodDescriptor.<org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatRequestProto, org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "heartbeat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto.getDefaultInstance()))
              .setSchemaDescriptor(new DatanodeServiceMethodDescriptorSupplier("heartbeat"))
              .build();
        }
      }
    }
    return getHeartbeatMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportRequestProto,
      org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportResponseProto> getBlockReportMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "blockReport",
      requestType = org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportRequestProto.class,
      responseType = org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportRequestProto,
      org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportResponseProto> getBlockReportMethod() {
    io.grpc.MethodDescriptor<org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportRequestProto, org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportResponseProto> getBlockReportMethod;
    if ((getBlockReportMethod = DatanodeServiceGrpc.getBlockReportMethod) == null) {
      synchronized (DatanodeServiceGrpc.class) {
        if ((getBlockReportMethod = DatanodeServiceGrpc.getBlockReportMethod) == null) {
          DatanodeServiceGrpc.getBlockReportMethod = getBlockReportMethod =
              io.grpc.MethodDescriptor.<org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportRequestProto, org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "blockReport"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportResponseProto.getDefaultInstance()))
              .setSchemaDescriptor(new DatanodeServiceMethodDescriptorSupplier("blockReport"))
              .build();
        }
      }
    }
    return getBlockReportMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DatanodeServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DatanodeServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DatanodeServiceStub>() {
        @Override
        public DatanodeServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DatanodeServiceStub(channel, callOptions);
        }
      };
    return DatanodeServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DatanodeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DatanodeServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DatanodeServiceBlockingStub>() {
        @Override
        public DatanodeServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DatanodeServiceBlockingStub(channel, callOptions);
        }
      };
    return DatanodeServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DatanodeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DatanodeServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DatanodeServiceFutureStub>() {
        @Override
        public DatanodeServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DatanodeServiceFutureStub(channel, callOptions);
        }
      };
    return DatanodeServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void registerDatanode(org.west2.clusterio.namenode.protocol.DatanodeProtocol.RegisterDatanodeRequestProto request,
        io.grpc.stub.StreamObserver<org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterDatanodeMethod(), responseObserver);
    }

    /**
     */
    default void heartbeat(org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatRequestProto request,
        io.grpc.stub.StreamObserver<org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHeartbeatMethod(), responseObserver);
    }

    /**
     */
    default void blockReport(org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportRequestProto request,
        io.grpc.stub.StreamObserver<org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportResponseProto> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getBlockReportMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service DatanodeService.
   */
  public static abstract class DatanodeServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return DatanodeServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service DatanodeService.
   */
  public static final class DatanodeServiceStub
      extends io.grpc.stub.AbstractAsyncStub<DatanodeServiceStub> {
    private DatanodeServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected DatanodeServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DatanodeServiceStub(channel, callOptions);
    }

    /**
     */
    public void registerDatanode(org.west2.clusterio.namenode.protocol.DatanodeProtocol.RegisterDatanodeRequestProto request,
        io.grpc.stub.StreamObserver<org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterDatanodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void heartbeat(org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatRequestProto request,
        io.grpc.stub.StreamObserver<org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHeartbeatMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void blockReport(org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportRequestProto request,
        io.grpc.stub.StreamObserver<org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportResponseProto> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getBlockReportMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service DatanodeService.
   */
  public static final class DatanodeServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<DatanodeServiceBlockingStub> {
    private DatanodeServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected DatanodeServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DatanodeServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto registerDatanode(org.west2.clusterio.namenode.protocol.DatanodeProtocol.RegisterDatanodeRequestProto request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterDatanodeMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto heartbeat(org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatRequestProto request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHeartbeatMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportResponseProto blockReport(org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportRequestProto request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getBlockReportMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service DatanodeService.
   */
  public static final class DatanodeServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<DatanodeServiceFutureStub> {
    private DatanodeServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected DatanodeServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DatanodeServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto> registerDatanode(
        org.west2.clusterio.namenode.protocol.DatanodeProtocol.RegisterDatanodeRequestProto request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterDatanodeMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto> heartbeat(
        org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatRequestProto request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHeartbeatMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportResponseProto> blockReport(
        org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportRequestProto request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getBlockReportMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_DATANODE = 0;
  private static final int METHODID_HEARTBEAT = 1;
  private static final int METHODID_BLOCK_REPORT = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTER_DATANODE:
          serviceImpl.registerDatanode((org.west2.clusterio.namenode.protocol.DatanodeProtocol.RegisterDatanodeRequestProto) request,
              (io.grpc.stub.StreamObserver<org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto>) responseObserver);
          break;
        case METHODID_HEARTBEAT:
          serviceImpl.heartbeat((org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatRequestProto) request,
              (io.grpc.stub.StreamObserver<org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto>) responseObserver);
          break;
        case METHODID_BLOCK_REPORT:
          serviceImpl.blockReport((org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportRequestProto) request,
              (io.grpc.stub.StreamObserver<org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportResponseProto>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getRegisterDatanodeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.west2.clusterio.namenode.protocol.DatanodeProtocol.RegisterDatanodeRequestProto,
              org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto>(
                service, METHODID_REGISTER_DATANODE)))
        .addMethod(
          getHeartbeatMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatRequestProto,
              org.west2.clusterio.namenode.protocol.DatanodeProtocol.HeartbeatResponseProto>(
                service, METHODID_HEARTBEAT)))
        .addMethod(
          getBlockReportMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportRequestProto,
              org.west2.clusterio.namenode.protocol.DatanodeProtocol.BlockReportResponseProto>(
                service, METHODID_BLOCK_REPORT)))
        .build();
  }

  private static abstract class DatanodeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DatanodeServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.west2.clusterio.namenode.protocol.DatanodeProtocol.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DatanodeService");
    }
  }

  private static final class DatanodeServiceFileDescriptorSupplier
      extends DatanodeServiceBaseDescriptorSupplier {
    DatanodeServiceFileDescriptorSupplier() {}
  }

  private static final class DatanodeServiceMethodDescriptorSupplier
      extends DatanodeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DatanodeServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (DatanodeServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DatanodeServiceFileDescriptorSupplier())
              .addMethod(getRegisterDatanodeMethod())
              .addMethod(getHeartbeatMethod())
              .addMethod(getBlockReportMethod())
              .build();
        }
      }
    }
    return result;
  }
}
