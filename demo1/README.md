# MyBatis 参数教程

## 引言

今天我们来说 MyBatis 接收参数这一块。我打算这样说给你听，我们先看一下MyBatis源码是如何处理参数的，然后我们通过例子来教你。

![Parameters.png](https://upload-images.jianshu.io/upload_images/5805596-13ba6c24cb1d1cdf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

实际上，我们这一节讲的就是：`Mapper.xml` 如何获取 `Dao` 中的参数呢？

另外，如果你还没有开始学习MyBatis，觉得MyBatis还不错，可以看 **[【MyBatis】学习纪要一：SpringBoot集成MyBatis完成增删查改](https://www.jianshu.com/p/aec59a38c88e)** 这篇教程，起步。

## 源码分析

* 第一步：我们先找到源码。

我先将处理参数的类复制到下面，然后我们再一起来分析。

```java
/**
 *    Copyright 2009-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.apache.ibatis.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

public class ParamNameResolver {

  private static final String GENERIC_NAME_PREFIX = "param";

  /**
   * <p>
   * The key is the index and the value is the name of the parameter.<br />
   * The name is obtained from {@link Param} if specified. When {@link Param} is not specified,
   * the parameter index is used. Note that this index could be different from the actual index
   * when the method has special parameters (i.e. {@link RowBounds} or {@link ResultHandler}).
   * </p>
   * <ul>
   * <li>aMethod(@Param("M") int a, @Param("N") int b) -&gt; {{0, "M"}, {1, "N"}}</li>
   * <li>aMethod(int a, int b) -&gt; {{0, "0"}, {1, "1"}}</li>
   * <li>aMethod(int a, RowBounds rb, int b) -&gt; {{0, "0"}, {2, "1"}}</li>
   * </ul>
   */
  private final SortedMap<Integer, String> names;

  private boolean hasParamAnnotation;

  public ParamNameResolver(Configuration config, Method method) {
    final Class<?>[] paramTypes = method.getParameterTypes();
    final Annotation[][] paramAnnotations = method.getParameterAnnotations();
    final SortedMap<Integer, String> map = new TreeMap<Integer, String>();
    int paramCount = paramAnnotations.length;
    // get names from @Param annotations
    for (int paramIndex = 0; paramIndex < paramCount; paramIndex++) {
      if (isSpecialParameter(paramTypes[paramIndex])) {
        // skip special parameters
        continue;
      }
      String name = null;
      for (Annotation annotation : paramAnnotations[paramIndex]) {
        if (annotation instanceof Param) {
          hasParamAnnotation = true;
          name = ((Param) annotation).value();
          break;
        }
      }
      if (name == null) {
        // @Param was not specified.
        if (config.isUseActualParamName()) {
          name = getActualParamName(method, paramIndex);
        }
        if (name == null) {
          // use the parameter index as the name ("0", "1", ...)
          // gcode issue #71
          name = String.valueOf(map.size());
        }
      }
      map.put(paramIndex, name);
    }
    names = Collections.unmodifiableSortedMap(map);
  }

  private String getActualParamName(Method method, int paramIndex) {
    if (Jdk.parameterExists) {
      return ParamNameUtil.getParamNames(method).get(paramIndex);
    }
    return null;
  }

  private static boolean isSpecialParameter(Class<?> clazz) {
    return RowBounds.class.isAssignableFrom(clazz) || ResultHandler.class.isAssignableFrom(clazz);
  }

  /**
   * Returns parameter names referenced by SQL providers.
   */
  public String[] getNames() {
    return names.values().toArray(new String[0]);
  }

  /**
   * <p>
   * A single non-special parameter is returned without a name.<br />
   * Multiple parameters are named using the naming rule.<br />
   * In addition to the default names, this method also adds the generic names (param1, param2,
   * ...).
   * </p>
   */
  public Object getNamedParams(Object[] args) {
    final int paramCount = names.size();
    if (args == null || paramCount == 0) {
      return null;
    } else if (!hasParamAnnotation && paramCount == 1) {
      return args[names.firstKey()];
    } else {
      final Map<String, Object> param = new ParamMap<Object>();
      int i = 0;
      for (Map.Entry<Integer, String> entry : names.entrySet()) {
        param.put(entry.getValue(), args[entry.getKey()]);
        // add generic param names (param1, param2, ...)
        final String genericParamName = GENERIC_NAME_PREFIX + String.valueOf(i + 1);
        // ensure not to overwrite parameter named with @Param
        if (!names.containsValue(genericParamName)) {
          param.put(genericParamName, args[entry.getKey()]);
        }
        i++;
      }
      return param;
    }
  }
}

```

* 第二步：我们先来看构成方法。

```java
public ParamNameResolver(Configuration config, Method method) {
    final Class<?>[] paramTypes = method.getParameterTypes();
    final Annotation[][] paramAnnotations = method.getParameterAnnotations();
    final SortedMap<Integer, String> map = new TreeMap<Integer, String>();
    int paramCount = paramAnnotations.length;
    // get names from @Param annotations
    for (int paramIndex = 0; paramIndex < paramCount; paramIndex++) {
      if (isSpecialParameter(paramTypes[paramIndex])) {
        // skip special parameters
        continue;
      }
      String name = null;
      for (Annotation annotation : paramAnnotations[paramIndex]) {
        if (annotation instanceof Param) {
          hasParamAnnotation = true;
          name = ((Param) annotation).value();
          break;
        }
      }
      if (name == null) {
        // @Param was not specified.
        if (config.isUseActualParamName()) {
          name = getActualParamName(method, paramIndex);
        }
        if (name == null) {
          // use the parameter index as the name ("0", "1", ...)
          // gcode issue #71
          name = String.valueOf(map.size());
        }
      }
      map.put(paramIndex, name);
    }
    names = Collections.unmodifiableSortedMap(map);
  }
```

这部分代码还是挺好理解的，可以先大概看一篇，然后主要看注释的几处。

1、获取有 `@Param` 注解的值

2、每次解析一个参数，并且保存到map中（key：索引，value：name）
    name：标注了Param注解：注解的值
    没有标注：
          1.全局配置：useActualParamName(jdk1.8):name=参数名
          2.name=map.size():相当于当前元素的索引
    
例如：
    method(@Param("id") id, @Param("name") name, Integer age);
    之后的Map，大概就这样：{0=id, 1=name}


* 第三步：我们具体分析

```java
// args[1, "Tom", 20]

public Object getNamedParams(Object[] args) {
    int paramCount = this.names.size();
    
     // 参数为null，直接返回
    if (args != null && paramCount != 0) {

          // 如果只有一个元素，并且没有 `@Param` 注解，args[0]，单个参数直接返回
        if (!this.hasParamAnnotation && paramCount == 1) {
            return args[(Integer)this.names.firstKey()];
         
           // 多个参数或者有 `@Param` 标注
        } else {
            Map<String, Object> param = new ParamMap();
            int i = 0;
               // 遍历 names 集合{0=id, 1=name}
            for(Iterator var5 = this.names.entrySet().iterator(); var5.hasNext(); ++i) {
               
               // names集合的value作为key；
               // names集合的key又作为取值的参考args[0]:args[1, "Tom"]
               // eg:{id=args[0]:1, name=args[1]:Tome}
                Entry<Integer, String> entry = (Entry)var5.next();
                param.put(entry.getValue(), args[(Integer)entry.getKey()]);
               
                // 额外的将每一个参数也保存到map中，使用新的key：param1 ... paramN
                String genericParamName = "param" + String.valueOf(i + 1);
                if (!this.names.containsValue(genericParamName)) {
                    param.put(genericParamName, args[(Integer)entry.getKey()]);
                }
            }

            return param;
        }
    } else {
        return null;
    }
}
```

通过读源码中的注释，相信你大概懂了吧。如果不懂也没关系，我们通过实例演示给你。

## 演示

这一步，我们先搭建起项目，先看搭建之后的目录结构。如果你已经搭建起来，可以调到下一步。

![module.png](https://upload-images.jianshu.io/upload_images/5805596-e3891725f3d2ffc2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![structure.png](https://upload-images.jianshu.io/upload_images/5805596-1615a3856647f56c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**`application.properties`**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mybatis_demo
spring.datasource.username=root
spring.datasource.password=xfsy2018
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

mybatis.type-aliases-package=com.fengwenyi.mybatis.demo1.domain
mybatis.mapper-locations=classpath:mapper/*.xml
```

**`User`**

```java
package com.fengwenyi.mybatis.demo1.domain;

/**
 * @author Wenyi Feng
 */
public class User {

    private Integer id;
    private String name;
    private Integer age;
    // getter & setter
    // toString
}
```

**`UserDao`**

```java
package com.fengwenyi.mybatis.demo1.dao;

import com.fengwenyi.mybatis.demo1.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Wenyi Feng
 */
@Mapper
public interface UserDao {

    // 查询
    User findById (Integer id);

}
```

**`UserMapper.xml`**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.fengwenyi.mybatis.demo1.dao.UserDao" >
    <resultMap id="BaseResultMap" type="User" >
        <id column="id" property="id" jdbcType="INTEGER" />
        <result column="name" property="name" jdbcType="VARCHAR" />
        <result column="age" property="age" jdbcType="INTEGER" />
    </resultMap>

    <select id="findById" resultMap="BaseResultMap">
        SELECT
          id, name, age
        FROM
          user
        WHERE
          id = #{id}
    </select>

</mapper>
```

**`数据库`**

```sql
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
```

**`UserService`**

```java
package com.fengwenyi.mybatis.demo1.service;

import com.fengwenyi.mybatis.demo1.dao.UserDao;
import com.fengwenyi.mybatis.demo1.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Wenyi Feng
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User findById (Integer id) {
        if (id != null && id > 0) {
            return userDao.findById(id);
        }
        return null;
    }

}
```

看一下测试代码

**`Demo1ApplicationTests`**

```java
package com.fengwenyi.mybatis.demo1;

import com.fengwenyi.mybatis.demo1.domain.User;
import com.fengwenyi.mybatis.demo1.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo1ApplicationTests {

    @Test
    public void contextLoads() {

    }

    @Autowired
    private UserService userService;

    @Test
    public void test1 () {
        User user = userService.findById(1);
        System.out.println(user.toString());
    }

}
```

运行：
> Demo1ApplicationTests > test1()

## 只有一个参数

上面例子就是一个参数标准实例，我们抽样一下。

**`UserDao`**

```java
    User findById (Integer id);
```

**`UserMapper.xml`**

```xml
    <select id="findById" resultMap="BaseResultMap">
        SELECT
          id, name, age
        FROM
          user
        WHERE
          id = #{id}
    </select>
```
一个参数，不论你用什么都可以取到，例如 `id=#{ssss}`。

## 多个参数

**`UserDao`**

```java
    User findByNameAndAge (String name, Integer age);
```
这样写怎么获取参数呢？

你可能会这样，我们写一个例子测试下

**UserMapp.xml**

```xml
    <select id="findByNameAndAge" resultMap="BaseResultMap">
        SELECT
          id, name, age
        FROM
          user
        WHERE
          name = #{name}
          AND
          age = #{age}
    </select>
```

运行会报错：

>Caused by: 
org.apache.ibatis.binding.BindingException: 
Parameter 'name' not found. 
Available parameters are [arg1, arg0, param1, param2]

这样写，不对，那我们该怎么写呢？

```xml
    <select id="findByNameAndAge" resultMap="BaseResultMap">
        SELECT
          id, name, age
        FROM
          user
        WHERE
          name = #{param1}
          AND
          age = #{param2}
    </select>
```

原因，开始我们就说了。

## @Param

我们改善一下

**`UserDao`**

```java
    User findByNameAndAge2 (@Param("name") String name, @Param("age") Integer age);
```

**`UserMapper.xml`**

```xml
    <select id="findByNameAndAge2" resultMap="BaseResultMap">
        SELECT
          id, name, age
        FROM
          user
        WHERE
          name = #{name}
          AND
          age = #{age}
    </select>
```

## POJO

**`UserDao`**

```java
    User findByPojo (User user);
```

**`UserMapper.xml`**

```xml
    <select id="findByPojo" resultMap="BaseResultMap">
        SELECT
          id, name, age
        FROM
          user
        WHERE
          name = #{name}
          AND
          age = #{age}
    </select>
```

## Map

看源码我们已经知道，MyBatis会将我们传递的参数封装成Map，那我们直接传Map会怎么样呢？

**`UserDao`**

```java
    User findByMap (Map<String, Object> map);
```

**`UserMapper.xml`**

```xml
    <select id="findByMap" resultMap="BaseResultMap">
        SELECT
          id, name, age
        FROM
          user
        WHERE
          name = #{name}
          AND
          age = #{age}
    </select>
```

## List

下面这两种是特殊的，但我还是说给你听

```java
    User findByList (List<Object> list);
```

```xml
    <select id="findByList" resultMap="BaseResultMap">
        SELECT
          id, name, age
        FROM
          user
        WHERE
          name = #{list[0]}
          AND
          age = #{list[1]}
    </select>
```

## Array

数组怎么处理

```java
    User findByArray (Object [] array);
```

```xml

    <select id="findByArray" resultMap="BaseResultMap">
        SELECT
          id, name, age
        FROM
          user
        WHERE
          name = #{array[0]}
          AND
          age = #{array[1]}
    </select>
```

## $ AND \#

取值我们都会用 `#{}`。

在我们习惯用的 `${}`，你们觉得这个很特别吗？有没有想过为什么呢？

* \#

**`UserDao`**

```java
    User testGetValue1 (String name);
```

**`UserMapper.xml`**

```xml
    <select id="testGetValue1" resultMap="BaseResultMap">
        SELECT
          id, name, age
        FROM
          user
        WHERE
          name = #{name}
    </select>
```

看下运行日志：

>==>  Preparing: SELECT id, name, age FROM user WHERE name = ? 
==> Parameters: AAA(String)
<==    Columns: id, name, age
<==        Row: 1, AAA, 20
<==      Total: 1

我们继续看

* $

**`UserDao`**

```java
    User testGetValue2 (@Param("name") String name);
```

**`UserMapper.xml`**

```xml
    <select id="testGetValue2" resultMap="BaseResultMap">
        SELECT
          id, name, age
        FROM
          user
        WHERE
          name = '${name}'
    </select>
```

看下sql日志：

>==>  Preparing: SELECT id, name, age FROM user WHERE name = 'AAA' 
==> Parameters: 
<==    Columns: id, name, age
<==        Row: 1, AAA, 20
<==      Total: 1

对比看下，你也许会发现各自的用法。

值得注意的是，这里只是 `$` 的测试示例，在实际中，不推荐这么写。

## $的用法

既然有 `${}`，那应该怎么用呢？我们再看一个例子

**`UserDao`**

```java
    User testGetValue3 (@Param("name") String name);
```

**UserMapper.xml**

```xml
    <select id="testGetValue3" resultMap="BaseResultMap">
        SELECT
          id, name, age
        FROM
          ${name}
        WHERE
          name = "AAA"
    </select>
```

看下sql日志：

>==>  Preparing: SELECT id, name, age FROM user WHERE name = "AAA" 
==> Parameters: 
<==    Columns: id, name, age
<==        Row: 1, AAA, 20
<==      Total: 1

## 总结

到这里就算差不过结束了。总结一下吧：

>参数多时，封装成Map，为了不混乱，我们可以使用 `@Param` 来指定封装时使用的key，使用 `#{key}` 就可以取出Map中的值

## 测试源码

**[MyBatis系列测试源码·参数](https://github.com/fengwenyi/mybatis-demo/tree/master/demo1)**