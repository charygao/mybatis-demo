## 写在前面的话

前面学习MyBatis的运行原理，刚学完，回头想想还是非常复杂的，也因为之前没有这样去分析过源码，所以这一节算是卡住了，可能会花一定的时间，为了不卡住学习进度，所以我们继续学习，后面再写插件。

## Introduction to MyBatis Generator

1、我首先告诉你官方网站地址：**[MyBatis Generator](http://www.mybatis.org/generator/index.html)**

2、前面在写测试的时候，就发现了这个问题，要写 `dao` 、 `model` 、 `mpper` ，这在写项目的时候显得是非痛苦，那怎么解决这个问题呢？——答案就是： **`mybatis generator`** 。

3、从第2点就知道了，MyBatis generator的主要作用就是创建 `dao` 、 `model` 、 `mpper` 。

## Quick Start Guide

1、添加依赖

```xml
        <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator</artifactId>
            <version>1.3.6</version>
        </dependency>

        <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-maven-plugin</artifactId>
            <version>1.3.6</version>
        </dependency>
```

2、编写 `generatorConfig.xml`

为了顺利完成这个文件的编写，我们参考官网的 **[XML Configuration Reference](http://www.mybatis.org/generator/configreference/xmlconfig.html#)** 部分进行编写。

另外建议通过搜索引擎编写的更加完美，或者自己想要的。

3、目录

![目录.png](https://upload-images.jianshu.io/upload_images/5805596-131841e810bfc321.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

（因为我用了git，所以红色的不是错误）

4、Running MyBatis Generator

在这里 **[Running MyBatis Generator](http://www.mybatis.org/generator/running/runningWithMaven.html)** ，你可以找到各种运行方式。

如果你是通过搜索引擎的话，可能会找到类似这样的命令

>java -jar mybatis-generator-core-x.x.x.jar -configfile \temp\generatorConfig.xml -overwrite

如果你是看开源项目的话，你会在`pom.xml`看到以下代码：

```xml
   <project ...>
     ...
     <build>
       ...
       <plugins>
        ...
        <plugin>
          <groupId>org.mybatis.generator</groupId>
          <artifactId>mybatis-generator-maven-plugin</artifactId>
          <version>1.3.5</version>
          <executions>
            <execution>
              <id>Generate MyBatis Artifacts</id>
              <goals>
                <goal>generate</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
        ...
      </plugins>
      ...
    </build>
    ...
  </project>
```

不论哪种，你都可以在官网找到用法说明。

那么我们怎么用呢？

```java
package com.fengwenyi.demo.mybatis.generatorconfig;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wenyi Feng
 */
public class RunGenerator {

    @Test
    public void test () {
        try {
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            File configFile = new File("src/main/resources/generatorConfig.xml");
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

我们来看一下效果：

![run位置.png](https://upload-images.jianshu.io/upload_images/5805596-3c5842b8f5bbceea.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![run-generator.png](https://upload-images.jianshu.io/upload_images/5805596-af2a4b96e4eef0aa.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![new-目录.png](https://upload-images.jianshu.io/upload_images/5805596-4b3781a07da6e327.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 我们来尝试一下

1、扫描dao，两种解决方法，扫描dao下所有的mapper接口，或者扫描指定的接口。

2、编写测试代码：

```java
@Autowired
private CityMapper cityMapper;

@Test
public void testCity () {
    List<City> cities = cityMapper.selectByExample(null);
    for (City city : cities)
        System.out.println(city);
}
```

看下测试结果：

![测试结果.png](https://upload-images.jianshu.io/upload_images/5805596-a9a26ee1d4162321.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

到这里就差不多了，写点后记

## 后记

1、你可以参考本节代码（**[generator-config](https://github.com/fengwenyi/mybatis-demo/tree/master/generator-config)**），就会很方便的生成你要相关文件和代码。

2、如果你在前面的MyBatis的基础部分（例如 **[Dynamic SQL](https://www.jianshu.com/p/535f9707d171)** ），你可以参考 `Mapper.xml` 文件进行学习。

## 资料

1、**[generator](https://github.com/mybatis/generator)**

2、 **[MyBatis Generator](http://www.mybatis.org/generator/index.html)**

3、本节测试代码： **[generator-config](https://github.com/fengwenyi/mybatis-demo/tree/master/generator-config)**
