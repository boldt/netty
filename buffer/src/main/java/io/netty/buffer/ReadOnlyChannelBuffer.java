/*
 * Copyright 2011 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

/**
 * A derived buffer which forbids any write requests to its parent.  It is
 * recommended to use {@link ChannelBuffers#unmodifiableBuffer(ChannelBuffer)}
 * instead of calling the constructor explicitly.
 */
public class ReadOnlyChannelBuffer extends AbstractChannelBuffer implements WrappedChannelBuffer {

    private final ChannelBuffer buffer;

    public ReadOnlyChannelBuffer(ChannelBuffer buffer) {
        if (buffer == null) {
            throw new NullPointerException("buffer");
        }
        this.buffer = buffer;
        setIndex(buffer.readerIndex(), buffer.writerIndex());
    }

    private ReadOnlyChannelBuffer(ReadOnlyChannelBuffer buffer) {
        this.buffer = buffer.buffer;
        setIndex(buffer.readerIndex(), buffer.writerIndex());
    }

    @Override
    public ChannelBuffer unwrap() {
        return buffer;
    }

    @Override
    public ChannelBufferFactory factory() {
        return buffer.factory();
    }

    @Override
    public ByteOrder order() {
        return buffer.order();
    }

    @Override
    public boolean isDirect() {
        return buffer.isDirect();
    }

    @Override
    public boolean hasArray() {
        return false;
    }

    @Override
    public byte[] array() {
        throw new ReadOnlyBufferException();
    }

    @Override
    public int arrayOffset() {
        throw new ReadOnlyBufferException();
    }

    @Override
    public void discardReadBytes() {
        throw new ReadOnlyBufferException();
    }

    @Override
    public void setByte(int index, int value) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public void setBytes(int index, ChannelBuffer src, int srcIndex, int length) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public void setBytes(int index, byte[] src, int srcIndex, int length) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public void setBytes(int index, ByteBuffer src) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public void setShort(int index, int value) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public void setMedium(int index, int value) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public void setInt(int index, int value) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public void setLong(int index, long value) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public int setBytes(int index, InputStream in, int length)
            throws IOException {
        throw new ReadOnlyBufferException();
    }

    @Override
    public int setBytes(int index, ScatteringByteChannel in, int length)
            throws IOException {
        throw new ReadOnlyBufferException();
    }

    @Override
    public int getBytes(int index, GatheringByteChannel out, int length)
            throws IOException {
        return buffer.getBytes(index, out, length);
    }

    @Override
    public void getBytes(int index, OutputStream out, int length)
            throws IOException {
        buffer.getBytes(index, out, length);
    }

    @Override
    public void getBytes(int index, byte[] dst, int dstIndex, int length) {
        buffer.getBytes(index, dst, dstIndex, length);
    }

    @Override
    public void getBytes(int index, ChannelBuffer dst, int dstIndex, int length) {
        buffer.getBytes(index, dst, dstIndex, length);
    }

    @Override
    public void getBytes(int index, ByteBuffer dst) {
        buffer.getBytes(index, dst);
    }

    @Override
    public ChannelBuffer duplicate() {
        return new ReadOnlyChannelBuffer(this);
    }

    @Override
    public ChannelBuffer copy(int index, int length) {
        return buffer.copy(index, length);
    }

    @Override
    public ChannelBuffer slice(int index, int length) {
        return new ReadOnlyChannelBuffer(buffer.slice(index, length));
    }

    @Override
    public byte getByte(int index) {
        return buffer.getByte(index);
    }

    @Override
    public short getShort(int index) {
        return buffer.getShort(index);
    }

    @Override
    public int getUnsignedMedium(int index) {
        return buffer.getUnsignedMedium(index);
    }

    @Override
    public int getInt(int index) {
        return buffer.getInt(index);
    }

    @Override
    public long getLong(int index) {
        return buffer.getLong(index);
    }

    @Override
    public ByteBuffer toByteBuffer(int index, int length) {
        return buffer.toByteBuffer(index, length).asReadOnlyBuffer();
    }

    @Override
    public ByteBuffer[] toByteBuffers(int index, int length) {
        ByteBuffer[] bufs = buffer.toByteBuffers(index, length);
        for (int i = 0; i < bufs.length; i ++) {
            bufs[i] = bufs[i].asReadOnlyBuffer();
        }
        return bufs;
    }

    @Override
    public int capacity() {
        return buffer.capacity();
    }
}
