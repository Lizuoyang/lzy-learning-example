package com.lzy.example.mqtt.service;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis操作Service
 * Created by macro on 2020/3/3.
 */
public interface RedisService {

    /**
     * 保存属性
     *
     * @param key
     * @param value
     * @param time
     */
    void set(String key, Object value, long time);

    /**
     * 保存属性
     *
     * @param key
     * @param value
     */
    void set(String key, Object value);

    /**
     * 获取属性
     *
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 删除属性
     *
     * @param key
     * @return
     */
    Boolean del(String key);

    /**
     * 批量删除属性
     *
     * @param keys
     * @return
     */
    Long del(Collection<String> keys);

    /**
     * 设置过期时间
     *
     * @param key
     * @param time
     * @return
     */
    Boolean expire(String key, long time);

    /**
     * 获取过期时间
     *
     * @param key
     * @return
     */
    Long getExpire(String key);

    /**
     * 判断是否有该属性
     *
     * @param key
     * @return
     */
    Boolean hasKey(String key);

    /**
     * 按delta递增
     *
     * @param key
     * @param delta
     * @return
     */
    Long incr(String key, long delta);

    /**
     * 按delta递增
     *
     * @param key
     * @param delta
     * @param time
     * @return
     */
    Long incr(String key, long delta, Long time);

    /**
     * 按delta递减
     *
     * @param key
     * @param delta
     * @return
     */
    Long decr(String key, long delta);

    /**
     * 获取Hash结构中的属性
     *
     * @param key
     * @param hashKey
     * @return
     */
    Object hGet(String key, String hashKey);

    /**
     * 向Hash结构中放入一个属性
     *
     * @param key
     * @param hashKey
     * @param value
     * @param time
     * @return
     */
    Boolean hSet(String key, String hashKey, Object value, long time);

    /**
     * 向Hash结构中放入一个属性
     *
     * @param key
     * @param hashKey
     * @param value
     */
    void hSet(String key, String hashKey, Object value);

    /**
     * 直接获取整个Hash结构
     *
     * @param key
     * @return
     */
    Map<Object, Object> hGetAll(String key);

    /**
     * 直接设置整个Hash结构
     *
     * @param key
     * @param map
     * @param time
     * @return
     */
    Boolean hSetAll(String key, Map<String, Object> map, long time);

    /**
     * 直接设置整个Hash结构
     *
     * @param key
     * @param map
     */
    void hSetAll(String key, Map<String, ?> map);

    /**
     * 删除Hash结构中的属性
     *
     * @param key
     * @param hashKey
     */
    void hDel(String key, Object... hashKey);

    /**
     * 判断Hash结构中是否有该属性
     *
     * @param key
     * @param hashKey
     * @return
     */
    Boolean hHasKey(String key, String hashKey);

    /**
     * Hash结构中属性递增
     *
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    Long hIncr(String key, String hashKey, Long delta);

    /**
     * Hash结构中属性递减
     *
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    Long hDecr(String key, String hashKey, Long delta);

    /**
     * 获取Set结构
     *
     * @param key
     * @return
     */
    Set<Object> sMembers(String key);

    /**
     * 向Set结构中添加属性
     *
     * @param key
     * @param values
     * @return
     */
    Long sAdd(String key, Object... values);

    /**
     * 向Set结构中添加属性
     *
     * @param key
     * @param time
     * @param values
     * @return
     */
    Long sAdd(String key, long time, Object... values);

    /**
     * 是否为Set中的属性
     *
     * @param key
     * @param value
     * @return
     */
    Boolean sIsMember(String key, Object value);

    /**
     * 获取Set结构的长度
     *
     * @param key
     * @return
     */
    Long sSize(String key);

    /**
     * 删除Set结构中的属性
     *
     * @param key
     * @param values
     * @return
     */
    Long sRemove(String key, Object... values);

    /**
     * 获取List结构中的属性
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    List<Object> lRange(String key, long start, long end);

    /**
     * 获取List结构的长度
     *
     * @param key
     * @return
     */
    Long lSize(String key);

    /**
     * 根据索引获取List中的属性
     *
     * @param key
     * @param index
     * @return
     */
    Object lIndex(String key, long index);

    /**
     * 向List结构中添加属性
     *
     * @param key
     * @param value
     * @return
     */
    Long lPush(String key, Object value);

    /**
     * 向List结构中添加属性
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    Long lPush(String key, Object value, long time);

    /**
     * 向List结构中批量添加属性
     *
     * @param key
     * @param values
     * @return
     */
    Long lPushAll(String key, Object... values);

    /**
     * 向List结构中批量添加属性
     *
     * @param key
     * @param time
     * @param values
     * @return
     */
    Long lPushAll(String key, Long time, Object... values);

    /**
     * 从List结构中移除属性
     *
     * @param key
     * @param count
     * @param value
     * @return
     */
    Long lRemove(String key, long count, Object value);

    /**
     * 添加一个元素
     *
     * @param key
     * @param value
     * @param score
     */
    void add(String key, String value, double score);

    /**
     * 添加一个组元素
     *
     * @param key
     * @param set
     */
    void addAll(String key, Set set);
    /**
     * 删除元素 zrem
     *
     * @param key
     * @param value
     */
    void remove(String key, String value);

    /**
     * score的增加or减少 zincrby
     *
     * @param key
     * @param value
     * @param score
     */
    Double incrScore(String key, String value, double score);

    /**
     * score的增加or减少 zincrby
     *
     * @param key
     * @param value
     * @param score
     * @param time
     */
    Double incrScore(String key, String value, double score, Long time);

    /**
     * 查询value对应的score   zscore
     *
     * @param key
     * @param value
     * @return
     */
    Double score(String key, String value);

    /**
     * 判断value在zset中的排名  zrank
     *
     * 积分小的在前面
     *
     * @param key
     * @param value
     * @return
     */
    Long rank(String key, String value);

    /**
     * 判断value在zset中的排名  zrank
     *
     * 积分大的在前面
     *
     * @param key
     * @param value
     * @return
     */
    Long revRank(String key, String value);

    /**
     * 查询集合中指定顺序的值， 0 -1 表示获取全部的集合内容  zrange
     *
     * 返回有序的集合，score小的在前面
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set<Object> range(String key, long start, long end);

    /**
     * 查询集合中指定顺序的值和score，0, -1 表示获取全部的集合内容
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set<ZSetOperations.TypedTuple<Object>> rangeWithScore(String key, long start, long end);

    /**
     * 查询集合中指定顺序的值  zrevrange
     *
     * 返回有序的集合中，score大的在前面
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set<Object> revRange(String key, long start, long end);

    /**
     * 根据score的值，来获取满足条件的集合  zrangebyscore
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    Set<Object> sortRange(String key, long min, long max);

    /**
     * 返回集合的长度
     *
     * @param key
     * @return
     */
    Long size(String key);

    /**
     * 根据规则查询key集合
     * @param pattern
     * @return
     */
    Collection<String> keys(String pattern);

    /**
     * 发送消息
     * @param channel
     * @param message
     */
    void convertAndSend(String channel, Object message);

}