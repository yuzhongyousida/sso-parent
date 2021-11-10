package org.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.*;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Set;


/**
 * @author wangteng05
 * @description: JedisUtil类
 * @date 2021/10/28 15:55
 */
public class JedisUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(JedisUtil.class);


    private static JedisPool jedisPool = null;

    /**
     * 默认缓存时间（单位：秒）：1h
     */
    private static final int DEFAULT_CACHE_SECONDS = 1*60*60;


//    static {
//        initJedisPool();
//    }

    /**
     * 初始化
     */
    public static void initJedisPool() {
        // 读取配置文件内容
        ResourceBundle resourceBundle = ResourceBundle.getBundle("jedis");
        String jedisHost = resourceBundle.getString("jedis.host");
        int jedisPort = Integer.parseInt(resourceBundle.getString("jedis.port"));
        int maxTotal = Integer.parseInt(resourceBundle.getString("jedis.maxTotal"));
        int maxIdle = Integer.parseInt(resourceBundle.getString("jedis.maxIdle"));
        String password = resourceBundle.getString("jedis.password");

        // 设置jedisPoolConfig
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setTestOnBorrow(true);

        // 初始化jedisPool
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, jedisHost, jedisPort, 2000, password);
    }


    /**
     * 获取redis操作对象
     * @return
     */
    public synchronized static Jedis getJedis() {
        if (jedisPool==null){
            initJedisPool();
        }
        return jedisPool.getResource();
    }

    /**
     * 关闭连接
     */
    public static void closeRedis(Jedis jedis){
        if (jedis != null){
            jedis.close();
        }
    }



    public static boolean setex(Object key, Object value, int expire) {
        Jedis redis = null;
        try {
            redis = getJedis();
            redis.setex(serialize(key), expire, serialize(value));
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        } finally {
            closeRedis(redis);
        }
    }

    public static boolean setex(String key, String value, int expire) {
        Jedis redis = null;
        try {
            redis = getJedis();
            redis.setex(key, expire, value);
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        } finally {
            closeRedis(redis);
        }
    }

    public static Boolean setDefaultExpire(Object key, Object value) {
        return setex(key, value, DEFAULT_CACHE_SECONDS);
    }


    public static void set(String key, String value) {
        Jedis redis = getJedis();
        try {
            redis.set(key, value);
        } finally {
            closeRedis(redis);
        }
    }

    public static void set(Object key, Object value) {
        Jedis redis = getJedis();
        try {
            redis.set(serialize(key), serialize(value));
        } finally {
            closeRedis(redis);
        }
    }

    public static String get(String key) {
        Jedis redis = getJedis();
        try {
            return redis.get(key);
        } finally {
            closeRedis(redis);
        }
    }


    public static Object get(Object key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] obj = jedis.get(serialize(key));
            return obj == null ? null : deSerialize(obj);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } finally {
            closeRedis(jedis);
        }
    }

    public static Object getObj(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] obj = jedis.get(serialize(key));
            return obj == null ? null : deSerialize(obj);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } finally {
            closeRedis(jedis);
        }
    }


    public static String getStr(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.get(key);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } finally {
            closeRedis(jedis);
        }
    }

    public static byte[] get(byte[] obj) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.get(obj);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } finally {
            closeRedis(jedis);
        }
    }

    public static boolean exists(Object key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.exists(serialize(key));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        } finally {
            closeRedis(jedis);
        }
    }

    public static boolean existsKey(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.exists(key);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        } finally {
            closeRedis(jedis);
        }
    }

    public static boolean delObj(Object key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.del(serialize(key));
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        } finally {
            closeRedis(jedis);
        }
    }

    public static boolean delStr(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.del(key);
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        } finally {
            closeRedis(jedis);
        }
    }


    /**
     * 清空数据库并执行持久化操作
     */
    public static void flushAll() {
        Jedis jedis = null;
        try {
            jedis = getJedis();

            // flushAll() 清空数据库并执行持久化操作
            jedis.flushAll();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            closeRedis(jedis);
        }
    }

    /**
     * 清空数据库,但是不执行持久化操作
     */
    public static void flushDB() {
        Jedis jedis = null;
        try {
            jedis = getJedis();

            // flushDB() 清空数据库,但是不执行持久化操作
            jedis.flushDB();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            closeRedis(jedis);
        }
    }


    /**
     * key值的模糊查询
     * @param pattern
     * @return
     */
    public static Set<String> keys(String pattern) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.keys("*" + pattern + "*");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptySet();
        } finally {
            closeRedis(jedis);
        }
    }


    /**
     * 序列化：object-->byte[] (由于jedis中不支持直接存储object所以转换成byte[]存入)
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                oos.close();
                baos.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 反序列化：byte[] -->Object
     * @param bytes
     * @return
     */
    public static Object deSerialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                bais.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return null;
    }


}
