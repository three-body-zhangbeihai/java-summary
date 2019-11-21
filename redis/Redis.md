# Redis

**推荐书籍：《Redis设计与实现》**

**Redis是key-value数据库，支持主从同步，数据存在内存，性能卓越。**

官方网址：http://redis.io

window版只能在github下载：<https://github.com/MicrosoftArchive/redis/releases/tag/win-3.2.100>

linux版可以在官网下载

redis官方文档：<http://www.dba86.com/docs/redis/>

redis命令文档：<http://www.dba86.com/docs/redis/commands.html>

<br>

## 配置文件

**redis.window.config**

**可选数据库个数 16个 （170行）**

```
# Set the number of databases. The default database is DB 0, you can select
# a different one on a per-connection basis using SELECT <dbid> where
# dbid is a number between 0 and 'databases'-1
databases 16
```

<br>

备份策略

**key值改变，redis会备份 （194行）**

```
# Save the DB on disk:
#
#   save <seconds> <changes>
#
#   Will save the DB if both the given number of seconds and the given
#   number of write operations against the DB occurred.
#
#   In the example below the behaviour will be to save:
#   after 900 sec (15 min) if at least 1 key changed
#   after 300 sec (5 min) if at least 10 keys changed
#   after 60 sec if at least 10000 keys changed
#
#   Note: you can disable saving completely by commenting out all "save" lines.
#
#   It is also possible to remove all the previously configured save
#   points by adding a save directive with a single empty string argument
#   like in the following example:
#
#   save ""

save 900 1		
save 300 10
save 60 10000
```

900秒有1个key被改变，就备份

300秒又10个可以被改变，备份

60秒。。。

<br>

**文件存储位置（228行）**

```
# The filename where to dump the DB
dbfilename dump.rdb
```

<br>



**java Client**

java与redis交互各种方式的网址：<https://redis.io/clients#java>

我们一般用jedis：<https://github.com/xetorthio/jedis>

<br>

## 常见命令



**查看redis有几个数据库（默认16个）**

**config get databases**

```
127.0.0.1:6379[9]> config get databases
1) "databases"
2) "16
```

<br>

选中某个数据库，比如选中第一个数据库

**select 1**

```
127.0.0.1:6379[9]> select 1
OK
127.0.0.1:6379[1]>
```

<br>

查看数据库中所有的key

**keys ***

```
127.0.0.1:6379[1]> set a a
OK
127.0.0.1:6379[1]> set b b
OK

127.0.0.1:6379[1]> keys *
1) "a"
2) "b"
```

<br>

**常见命令和可应用的场景**

- **List：双向列表，适用于最新列表，关注列表。**
  - lpush
  - lpop
  - blpop
  - lindex
  - lrange
  - lrem
  - linsert
  - lset
  - rpush
- **Set : 适用于无顺序的集合，点赞点踩，抽奖，已读，共同好友**
  - sadd（添加元素）
  - srem（移除元素）
  - sdiff （返回由第一个集合和所有连续集合之间的差得出的集合成员）
  - smembers （获取集合中的所有元素）
  - sinter
  - scard （返回集合元素的个数）
  - sismember（返回集合中是否存在某个值的布尔值）
  - sunion
  - spop（随机弹出集合里的某个元素）
- **SortedSet：排行版**，（比如，某个网站的文章，阅读量，点赞数，点踩数，评论数等加权综合而成的排行版）
  - zadd
  - zcore
  - zrange
  - zcount
  - zrank
  - zrevrank
- **Hash：对象属性，不定长属性数**
  - hset
  - hget
  - hgetAll
  - hexists
  - hkeys
  - hvals
- **KV：单一数值，验证码，PV，缓存**
  - set
  - setex
  - incr

<br>



## java中使用jedis



**pom.xml 文件中引入jedis**

```xml
<dependency>
	<groupId>redis.clients</groupId>
	<artifactId>jedis</artifactId>
	<version>2.8.0</version>
</dependency>
```

<br>









