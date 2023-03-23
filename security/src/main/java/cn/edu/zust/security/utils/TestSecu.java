package cn.edu.zust.security.utils;

import cn.edu.zust.common.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

public class TestSecu {

    @Resource
    private RedisTemplate redisTemplate;

    public static void main(String[] args) {

    }

}
