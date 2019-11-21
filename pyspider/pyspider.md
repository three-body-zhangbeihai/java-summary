# pyspider

## 安装



启动pyspider之后遇到错误：

```
ValueError: Invalid configuration: - Deprecated option 'domaincontroller': use 'http_authenticator
```

**原因**：

原因是因为WsgiDAV发布了版本 pre-release 3.x。

**解决方法**:

在pyspider的安装目录下，找到webui目录下有个webdav.py文件，我的是在**Anaconda/Lib/site-packages/pyspider/webui/**目录下，在webdav.py 文件中，找到

```
'domaincontroller': NeedAuthController(app),
```

将其替换为

```
'http_authenticator':{
        'HTTPAuthenticator':NeedAuthController(app),
    },
```

保存后，再执行pyspider all，可以成功启动pyspider，访问localhost:5000 也成功了。



## 案例:爬取https://www.v2ex.com

**版本：python3.7**

导入数据库，需要用到 **pymysql**，支持python3.x。

而mysqldb支持python2

<br>

```python
#!/usr/bin/env python
# -*- encoding: utf-8 -*-
# Created on 2019-11-09 01:12:43
# Project: newv2ex

from pyspider.libs.base_handler import *
import pymysql
import random

class Handler(BaseHandler):
    crawl_config = {
    }
    def __init__(self):
        self.db=pymysql.connect(host='localhost',user='root',passwd='wenyan',db='wenda',port=3306,charset='utf8')

    def add_question(self, title, content):
        try:
            cursor = self.db.cursor()
            sql = 'insert into question(title,content,user_id,created_date,comment_count) values("%s", "%s",%d,now(),0)' %(title,content,random.randint(1,10))
            print(sql)
            cursor.execute(sql)
            self.db.commit()
        except Exception, e:
            print e
            self.db.rollback()
        finally:
            self.db.close()
            
    
    @every(minutes=24 * 60)
    # 入口地址
    def on_start(self):
        self.crawl('https://v2ex.com/', callback=self.index_page)# 调用index_page方法
    
    
    @config(age=10 * 24 * 60 * 60)
    def index_page(self, response):
        # 先从tab= 开始，爬取所有的tab标签(对应网站里的"技术","创意","好玩",....
        for each in response.doc('a[href^="https//v2ex.com/?tab="]').items():
            self.crawl(each.attr.href, callback=self.tab_page, validate_cert=False) 

    @config(priority=2)
    def tab_page(self, response):
        # 再爬tab标签的子节点，go标签开始(对应网站里的 "程序员","Python","iDev",...
        for each in response.doc('a[href^="http//v2ex.com/go/"]').items():
            self.crawl(each.attr.href, callback=self.board_page) 
            
    @config(priority=2)
    def board_page(self, response):
        # 
        for each in response.doc('a[href^="http//v2ex.com/t/"]').items():
            url = each.attr.href  # 找到具体每篇文章的链接
            if url.find('#reply')>0:   # 链接后面带的参数是回复的数量，如果有回复，就把链接从reply开始到#都去掉
                url = url[0:url.find('#')]
            self.crawl(each.attr.href, callback=self.detail_page) 
        for each in response.doc('a.page_normal').items():
            self.crawl(each.attr.href, callback=self.borad_page) # 回调自己。
            
    @config(priority=2)
    def detail_page(self, response):
        title = response.doc('h1').text()
        content = response.doc('div.topic_content').html.replace('"','\\"') # 将双引号替换为转义字符。
        # 找到每篇的标题和内容，就应该把他们插入到数据库中。
        # 调用之前的add_quesiton方法
        self.add_quesiton(title,content)
        return {
            "url": response.url,
            "title": response.doc('title').text(),
        }

```

<br>



