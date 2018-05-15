package com.koh.thrift.swift;

import com.facebook.swift.codec.*;
import com.facebook.swift.codec.ThriftField.Requiredness;
import com.facebook.swift.service.*;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.*;
import java.util.*;

@ThriftService("TestService")
public interface TestService
{
    @ThriftService("TestService")
    public interface Async
    {
        @ThriftMethod(value = "exists")
        ListenableFuture<Boolean> exists(
            @ThriftField(value=1, name="path", requiredness=Requiredness.NONE) final String path
        );

        @ThriftMethod(value = "echo")
        ListenableFuture<String> echo(
            @ThriftField(value=1, name="path", requiredness=Requiredness.NONE) final String path
        );

        @ThriftMethod(value = "getById")
        ListenableFuture<Data> getById(
            @ThriftField(value=1, name="id", requiredness=Requiredness.NONE) final int id
        );
    }
    @ThriftMethod(value = "exists")
    boolean exists(
        @ThriftField(value=1, name="path", requiredness=Requiredness.NONE) final String path
    ) throws org.apache.thrift.TException;

    @ThriftMethod(value = "echo")
    String echo(
        @ThriftField(value=1, name="path", requiredness=Requiredness.NONE) final String path
    ) throws org.apache.thrift.TException;

    @ThriftMethod(value = "getById")
    Data getById(
        @ThriftField(value=1, name="id", requiredness=Requiredness.NONE) final int id
    ) throws org.apache.thrift.TException;
}