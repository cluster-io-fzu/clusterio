package org.west2.clusterio.common.protocolPB.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.57.2)",
    comments = "Source: Namenode.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class NamenodeServiceGrpc {

  private NamenodeServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "NamenodeService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksRequestProto,
      org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksResponseProto> getGetBlocksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getBlocks",
      requestType = org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksRequestProto.class,
      responseType = org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksRequestProto,
      org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksResponseProto> getGetBlocksMethod() {
    io.grpc.MethodDescriptor<org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksRequestProto, org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksResponseProto> getGetBlocksMethod;
    if ((getGetBlocksMethod = NamenodeServiceGrpc.getGetBlocksMethod) == null) {
      synchronized (NamenodeServiceGrpc.class) {
        if ((getGetBlocksMethod = NamenodeServiceGrpc.getGetBlocksMethod) == null) {
          NamenodeServiceGrpc.getGetBlocksMethod = getGetBlocksMethod =
              io.grpc.MethodDescriptor.<org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksRequestProto, org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getBlocks"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksResponseProto.getDefaultInstance()))
              .setSchemaDescriptor(new NamenodeServiceMethodDescriptorSupplier("getBlocks"))
              .build();
        }
      }
    }
    return getGetBlocksMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static NamenodeServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NamenodeServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NamenodeServiceStub>() {
        @java.lang.Override
        public NamenodeServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NamenodeServiceStub(channel, callOptions);
        }
      };
    return NamenodeServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static NamenodeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NamenodeServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NamenodeServiceBlockingStub>() {
        @java.lang.Override
        public NamenodeServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NamenodeServiceBlockingStub(channel, callOptions);
        }
      };
    return NamenodeServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static NamenodeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NamenodeServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NamenodeServiceFutureStub>() {
        @java.lang.Override
        public NamenodeServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NamenodeServiceFutureStub(channel, callOptions);
        }
      };
    return NamenodeServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getBlocks(org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksRequestProto request,
        io.grpc.stub.StreamObserver<org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksResponseProto> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetBlocksMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service NamenodeService.
   */
  public static abstract class NamenodeServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return NamenodeServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service NamenodeService.
   */
  public static final class NamenodeServiceStub
      extends io.grpc.stub.AbstractAsyncStub<NamenodeServiceStub> {
    private NamenodeServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NamenodeServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NamenodeServiceStub(channel, callOptions);
    }

    /**
     */
    public void getBlocks(org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksRequestProto request,
        io.grpc.stub.StreamObserver<org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksResponseProto> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetBlocksMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service NamenodeService.
   */
  public static final class NamenodeServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<NamenodeServiceBlockingStub> {
    private NamenodeServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NamenodeServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NamenodeServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksResponseProto getBlocks(org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksRequestProto request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetBlocksMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service NamenodeService.
   */
  public static final class NamenodeServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<NamenodeServiceFutureStub> {
    private NamenodeServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NamenodeServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NamenodeServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksResponseProto> getBlocks(
        org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksRequestProto request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetBlocksMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_BLOCKS = 0;

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

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_BLOCKS:
          serviceImpl.getBlocks((org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksRequestProto) request,
              (io.grpc.stub.StreamObserver<org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksResponseProto>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
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
          getGetBlocksMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksRequestProto,
              org.west2.clusterio.common.protocolPB.NamenodeProtocol.GetBlocksResponseProto>(
                service, METHODID_GET_BLOCKS)))
        .build();
  }

  private static abstract class NamenodeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    NamenodeServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.west2.clusterio.common.protocolPB.NamenodeProtocol.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("NamenodeService");
    }
  }

  private static final class NamenodeServiceFileDescriptorSupplier
      extends NamenodeServiceBaseDescriptorSupplier {
    NamenodeServiceFileDescriptorSupplier() {}
  }

  private static final class NamenodeServiceMethodDescriptorSupplier
      extends NamenodeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    NamenodeServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (NamenodeServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new NamenodeServiceFileDescriptorSupplier())
              .addMethod(getGetBlocksMethod())
              .build();
        }
      }
    }
    return result;
  }
}
