package com.zouzoutingting.utils;

import net.rubyeye.xmemcached.exception.MemcachedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.SystemProfileValueSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeoutException;

/**
 * Created by zhangyong on 16/7/27.
 */
public class MemcacheTest {
    @Test
    public void testMemcache(){
        MemcacheClient memcacheClient = MemcacheClient.getInstance();
        try {
            memcacheClient.getMc().set("zlex", 36000, "set/get");
            assert ("set/get".equals(memcacheClient.getMc().get("zlex")));
            System.out.println(memcacheClient.getMc().get("zlex"));

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
    }
}
