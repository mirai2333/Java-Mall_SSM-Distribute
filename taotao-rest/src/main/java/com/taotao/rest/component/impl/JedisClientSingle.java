package com.taotao.rest.component.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.rest.component.JedisClient;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Redis单机版实现类
 * @author Mirai
 *
 */
public class JedisClientSingle implements JedisClient {

	@Autowired
	private JedisPool jedisPool;

	@Override
	public String set(String key, String value) {
		Jedis resource = jedisPool.getResource();
		String result = resource.set(key, value);
		resource.close();
		return result;
	}

	@Override
	public String get(String key) {
		Jedis resource = jedisPool.getResource();
		String result = resource.get(key);
		resource.close();
		return result;
	}

	@Override
	public Long hset(String key, String item, String value) {
		Jedis resource = jedisPool.getResource();
		Long result = resource.hset(key, item, value);
		resource.close();
		return result;
	}

	@Override
	public String hget(String key, String item) {
		Jedis resource = jedisPool.getResource();
		String result = resource.hget(key, item);
		resource.close();
		return result;
	}

	@Override
	public Long incr(String key) {
		Jedis resource = jedisPool.getResource();
		Long result = resource.incr(key);
		resource.close();
		return result;
	}

	@Override
	public Long decr(String key) {
		Jedis resource = jedisPool.getResource();
		Long result = resource.decr(key);
		resource.close();
		return result;
	}

	@Override
	public Long expire(String key, int second) {
		Jedis resource = jedisPool.getResource();
		Long result = resource.expire(key, second);
		resource.close();
		return result;
	}

	@Override
	public Long ttl(String key) {
		Jedis resource = jedisPool.getResource();
		Long result = resource.ttl(key);
		resource.close();
		return result;
	}

	@Override
	public Long hdel(String key, String item) {
		Jedis resource = jedisPool.getResource();
		Long result = resource.hdel(key, item);
		resource.close();
		return result;
	}

	@Override
	public Long del(String key) {
		Jedis resource = jedisPool.getResource();
		Long result = resource.del(key);
		resource.close();
		return result;
	}

}
