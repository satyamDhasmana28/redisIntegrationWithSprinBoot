package com.satyam.RedisSpringIntegration.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satyam.RedisSpringIntegration.Configuration.UserConfig;
import com.satyam.RedisSpringIntegration.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    RedisTemplate<String,User> redisTemplate;
    @Autowired
    ObjectMapper objectMapper;
//    this is to add user in redis by key:value(String:User)
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody() User user){
        String userId = user.getId();
        redisTemplate.opsForValue().set(userId,user);
        return new ResponseEntity<>(user.getName()+" added in Redis", HttpStatus.OK);
    }

    @PostMapping("/lpush")
    public void lpush(@RequestParam("key")String key, @RequestBody()User user){
        redisTemplate.opsForList().leftPush(key,user);
    }

    @PostMapping("/rpush")
    public void rpush(@RequestParam("key") String key, @RequestBody()User user){
        redisTemplate.opsForList().rightPush(key,user);
    }
    @GetMapping("/lpop")
    public List<User> lpop(@RequestParam("key")String key, @RequestParam("count")int count){
        List<User> userList = redisTemplate.opsForList().leftPop(key,count);
        return userList;
    }

    @PostMapping("/rpop")
    public List<User> rpop(@RequestParam("key") String key, @RequestParam("count")int count){
        return redisTemplate.opsForList().rightPop(key,count);
    }

    @PostMapping("/hmset")
    public String hmset(@RequestParam("key")String key,@RequestBody User user){
        Map userMap = objectMapper.convertValue(user, Map.class);
        redisTemplate.opsForHash().putAll(key,userMap);
        return "added";
    }

    @GetMapping("hmset/get/{key}/{attribute}")
    public String hmsetGetAttribute(@PathVariable("key")String key,@PathVariable("attribute") String attribute){
         Map userMap = redisTemplate.opsForHash().entries(key);
         return (String)userMap.get(attribute);
    }

}
