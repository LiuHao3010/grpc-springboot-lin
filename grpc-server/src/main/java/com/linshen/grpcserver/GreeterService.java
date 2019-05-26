package com.linshen.grpcserver;


import com.linshen.grpc.lib.GreeterGrpc;
import com.linshen.grpc.lib.GreeterOuterClass;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;

@Slf4j
@GrpcService(GreeterOuterClass.class)
public class GreeterService extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello(GreeterOuterClass.HelloRequest request, StreamObserver<GreeterOuterClass.HelloReply> responseObserver) {
        String message = "Hello " + request.getImage();
        byte[] bytes=request.getImage().toByteArray();
        String path=request.getName();
        try{
                 FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
                 imageOutput.write(bytes, 0, bytes.length);
                 imageOutput.close();
                 System.out.println("Make Picture success,Please find image in " + path);
                 } catch(Exception ex) {
                   System.out.println("Exception: " + ex);
                   ex.printStackTrace();
                 }
        final GreeterOuterClass.HelloReply.Builder replyBuilder = GreeterOuterClass.HelloReply.newBuilder().setMessage(message);
        responseObserver.onNext(replyBuilder.build());
        responseObserver.onCompleted();
    }
}