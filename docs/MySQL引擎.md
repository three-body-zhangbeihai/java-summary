# MySQL存储引擎



## 一、 数据库存储引擎

​	数据库存储引擎是**数据库底层软件组件**，数据库管理系统（DBMS）使用数据引擎进行创建、查询、更新和删除数据操作。

MySQL提供了多个不同个的存储引擎，包括处理 **事务安全表** 的引擎 和 处理 **非事务安全表**  的引擎。MySQL中，不需要在整个服务器使用同一种存储引擎，针对具体的要求，可以对每个表用不同的存储引擎。

MySQL5.7 支持的引擎：**InnoDB，MyISAM**，Memory，Merge，Archive，Federated，CSV，BLACKHOLE 等。



### 1.查看系统支持的存储引擎



可以通过使用 下列语句查看系统所支持的引擎类型。<br>

```mysql
SHOW ENGINES；
```

<br>

**结果如下：**(主要关注 **InnoDB** 引擎 和 **MyISAM**引擎)

| Engine             | Support     | Comment                                                      | Transactions | XA     | Savepoints |
| ------------------ | ----------- | ------------------------------------------------------------ | ------------ | ------ | ---------- |
| **InnoDB**         | **DEFAULT** | **Supports transactions**, **row-level locking, and foreign keys** | **YES**      | YES    | YES        |
| MRG_MYISAM         | YES         | Collection of identical MyISAM tables                        | NO           | NO     | NO         |
| MEMORY             | YES         | Hash based, stored in memory, useful for temporary tables    | NO           | NO     | NO         |
| BLACKHOLE          | YES         | /dev/null storage engine (anything you write to it disappears) | NO           | NO     | NO         |
| **MyISAM**         | **YES**     | MyISAM storage engine                                        | **NO**       | NO     | NO         |
| CSV                | YES         | CSV storage engine                                           | NO           | NO     | NO         |
| ARCHIVE            | YES         | Archive storage engine                                       | NO           | NO     | NO         |
| PERFORMANCE_SCHEMA | YES         | Performance Schema                                           | NO           | NO     | NO         |
| FEDERATED          | NO          | Federated MySQL storage engine                               | (NULL)       | (NULL) | (NULL)     |

<br>

### 2. 查看当前数据的默认引擎

**使用：**

```mysql
show variable like '%storage_engine%';
```

<br>

**结果如下：**

| Variable_name                    | Value      |
| -------------------------------- | ---------- |
| **default_storage_engine**       | **InnoDB** |
| default_tmp_storage_engine       | InnoDB     |
| disabled_storage_engines         |            |
| internal_tmp_disk_storage_engine | InnoDB     |

<br>



### 3. 查看某张表的具体结构和引擎

**使用**：(以某张表 **t_user**为例，**下同**)

```mysql
show create table t_user;
```

<br>

**结果如下：**

除了显示表创建的语句外，在最后显示 **`ENGINE=InnoDB DEFAULT CHARSET=utf8`**。

<br>

### 4. 修改某张表的存储引擎

**使用：**

```mysql
alter table t_user engine=MyISAM;
```

通过 **`show create table t_user;`**可以查看引擎变成了 **MyISAM**了。

<br>



## 二、 InnoDB引擎和 MyISAM引擎（表级）

### 1.  InnoDB

<br>

InnoDB 是 **事务型数据库** 的首选引擎，**支持事务安全表（ACID）**，支持 **行锁** 和 **外键**。MySQL5.5之后，InnoDB作为默认存储引擎。

<br>

#### 1.1 数据存储结构

MySQL 使用 InnoDB 存储 表时，会将 **表的定义** 和 **数据索引** 等信息分开存储，其中前者存储在 **.frm**  文件中，后者存储在 **.ibd** 文件中。<br>



**.frm**: **表结构的定义** 存放的位置

**.ibd**:  **索引和数据文件** 存放的位置(InnoDB Data)

<br>

在MySQL**安装目录下**的**Data目录下**，以I**nnoDB为引擎**的表，可以找到如下图。

![image](images/InnoDB磁盘存储结构图.png)



<br>

#### 1.2 主要特性：

1. InnoDB 引擎提供了对数据库 **ACID**事务的支持，并且实现了 **SQL标准的四种隔离级别。**

   【**未提交读**(Read uncommitted)，**已提交读**(Read committed)，**可重复读**(Repeatable read)，**可序列化**(Serializable)】

2. 该引擎还提供了**行级锁**和**外键约束**
3. 使用行级锁也不是绝对的，如果在执行一个SQL语句时MySQL不能确定要扫描的范围，InnoDB表同样会锁全表。
4. 它的设计目标是处理大容量数据库系统，它本身其实就是基于MySQL后台的完整数据库系统。
5. MySQL运行时Innodb会在**内存中建立缓冲池**，用于缓冲**数据和索引**。
6. 该引擎不支持**FULLTEXT**类型的索引（**不支持全文索引**）。
7. 它没有保存表的行数，当 **`SELECT COUNT(*) FROM TABLE`** 时**需要扫描全表**。
8. 当需要使用数据库事务时，该引擎当然是首选。
9. 由于锁的粒度更小，写操作不会锁定全表，所以在**并发较高**时，使用Innodb引擎会**提升效率。**
10. InnoDB 是**聚集索引**，MyISAM 是 **非聚集索引**。

<br>



### 2. MyISAM



MyISAM基于 **ISAM**的存储引擎，并对其扩展。是在 **Web、数据存储**和其他应用环境下最常使用的存储引擎之一。拥有 **较高的插入、查询速度**，但 **不支持事务**。在MySQL 5.5 之前，是默认存储引擎。

<br>

#### 2.1 存储结构

**.frm**:  **表结构定义** 存放的位置；

**.MYI**:  **表索引** 存放的位置；(MyISAM Index)

**.MYD**: **表数据** 存放的位置；(MyISAM Data)

<br>

**存储结构图**：

![imag](images/MyISAM磁盘存储结构图.png)

<br>



#### 2.2 主要特性

1. 但是它没有提供对数据库事务的支持，也**不支持行级锁和外键，**
2. 因此当 **`INSERT`** (插入)或 **`UPDATE`** (更新)数据时即写操作需要**锁定整个表**，效率便会**低**一些。
3. MyIASM中**存储了表的行数**，于是 **`SELECT COUNT(*) FROM TABLE`** 时只需要直接读取已经保存好的值而不需要进行全表扫描。
4. 如果表的读操作远远多于写操作且不需要数据库事务的支持，那么MyIASM也是很好的选择。

<br>



### 3. 主要区别

1. MyIASM是非事务安全的，而InnoDB是事务安全的
2. MyIASM锁的粒度是**表级**的，而InnoDB支持**行级锁**
3. MyIASM**支持**全文类型索引，而InnoDB**不支持**全文索引
4. MyIASM相对简单，**效率上要优于InnoDB**，小型应用可以考虑使用MyIASM
5. MyIASM表保存成**文件形式**，**跨平台使用更加方便**

<br>

### 4. 应用场景：

1. MyIASM管理非事务表，提供高速存储和检索以及全文搜索能力，如果再应用中执行大量select操作，应该选择MyIASM
2. InnoDB用于事务处理，具有ACID事务支持等特性，如果在应用中执行大量insert和update操作，应该选择InnoDB





<br><br>

## 参考

[mysql存储引擎MyISAM和InnoDB](https://blog.51cto.com/13760226/2170592)<br>

[Mysql存储引擎比较](https://blog.csdn.net/len9596/article/details/80206532)<br>

[面试宝典系列-mysql引擎Innodb和MyISAM的区别](https://my.oschina.net/suyain/blog/1925807)<br>

《MySQL5.7 从入门到精通》书籍





  


