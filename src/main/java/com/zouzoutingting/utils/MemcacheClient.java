package com.zouzoutingting.utils;

import java.io.IOException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.utils.AddrUtil;

/**
 * Created by zhangyong on 16/7/26.
 */
public class MemcacheClient {
    private static MemcacheClient mc = null;
    private static MemcachedClient memcachedClient = null;

    public MemcacheClient() {
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(
                AddrUtil.getAddresses("123.56.242.84:12000"),
                new int[] { 1, 1, 1, 1 });
        // 设置连接池大小，即客户端个数
        builder.setConnectionPoolSize(50);

        // 宕机报警
        builder.setFailureMode(true);

        // 使用二进制文件
        builder.setCommandFactory(new BinaryCommandFactory());

        try {
            memcachedClient = builder.build();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static MemcacheClient getInstance(){
        if(mc==null){
            mc = new MemcacheClient();
        }
        return mc;
    }

    public MemcachedClient getMc(){
        return memcachedClient;
    }
}