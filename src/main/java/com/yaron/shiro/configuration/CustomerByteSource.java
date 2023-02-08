package com.yaron.shiro.configuration;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.util.ByteSource;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Yaron
 * @version 1.0
 * @date 2023/02/06
 * @description 自定义salt实现 实现序列化接口
 */
public class CustomerByteSource implements ByteSource, Serializable {

    private byte[] bytes;
    private String cachedHex;
    private String cachedBase64;

    public CustomerByteSource() {
    }

    public CustomerByteSource(byte[] bytes) {
        this.bytes = bytes;
    }


    public CustomerByteSource(char[] chars) {
        this.bytes = CodecSupport.toBytes(chars);
    }

    public CustomerByteSource(String str){
        this.bytes = CodecSupport.toBytes(str);
    }

    public CustomerByteSource(ByteSource source){
        this.bytes = source.getBytes();
    }

    public CustomerByteSource(File file){
        this.bytes = (new CustomerByteSource.BytesHelper()).getBytes(file);
    }


    public CustomerByteSource(InputStream in){
        this.bytes = (new CustomerByteSource.BytesHelper()).getBytes(in);
    }

    public static boolean isCompatible(Object obj){
        return obj instanceof byte[]
                || obj instanceof char[]
                || obj instanceof String
                || obj instanceof ByteSource
                || obj instanceof File
                || obj instanceof InputStream;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getCachedHex() {
        return cachedHex;
    }

    public void setCachedHex(String cachedHex) {
        this.cachedHex = cachedHex;
    }

    public String getCachedBase64() {
        return cachedBase64;
    }

    public void setCachedBase64(String cachedBase64) {
        this.cachedBase64 = cachedBase64;
    }

    @Override
    public byte[] getBytes() {
        return this.bytes;
    }

    @Override
    public String toHex() {
        if (this.cachedHex == null){
            this.cachedHex = Hex.encodeToString(this.getBytes());
        }

        return this.cachedHex;
    }

    @Override
    public String toBase64() {
        if (this.cachedBase64 == null){
            this.cachedBase64 = Base64.encodeToString(this.getBytes());
        }
        return this.cachedBase64;
    }

    @Override
    public String toString() {
        return this.toBase64();
    }

    @Override
    public int hashCode(){
        return this.bytes != null
                && this.bytes.length != 0
                 ? Arrays.hashCode(this.bytes): 0;
    }

    @Override
    public boolean equals(Object obj){

        if (obj == this){
            return true;
        } else if (obj instanceof ByteSource){
            ByteSource bs = (ByteSource) obj;
            return Arrays.equals(this.getBytes(), bs.getBytes());
        } else {
            return false;
        }

    }

    @Override
    public boolean isEmpty() {
        return this.bytes == null || this.bytes.length == 0;
    }

    private static final class BytesHelper extends CodecSupport{
        private BytesHelper(){}

        public byte[] getBytes(File file){
            return this.toBytes(file);
        }

        public byte[] getBytes(InputStream in){
            return this.toBytes(in);
        }
    }
}
