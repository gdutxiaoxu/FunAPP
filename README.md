
# 基于Android的新闻客户端

## 前言

这个 APP 现在只是一个简单的Demo，还有许多地方需要优化，使用说明也还完善，以后会逐步更新，先凑合着看吧。

## 完成功能
- 首页
- 新闻
- 美图
- 设置

![](http://ww1.sinaimg.cn/large/9fe4afa0gy1fes86f0rjoj20bp0krgq8.jpg)

![](http://ww1.sinaimg.cn/large/9fe4afa0gy1fes8784unhj20bl0kq7dt.jpg)
![](http://ww1.sinaimg.cn/large/9fe4afa0gy1fes8785ie1j20bl0ktgvb.jpg)
![](http://ww1.sinaimg.cn/large/9fe4afa0gy1fes8781243j20bk0kqn02.jpg)
![](http://ww1.sinaimg.cn/large/9fe4afa0gy1fes877smmhj20bk0kqgm7.jpg)
![](http://ww1.sinaimg.cn/large/9fe4afa0gy1fes8783v8vj20bf0knqbh.jpg)

## 开源框架
- 图片加载，使用了Glide 和 Picasso，Picasso和Glide 的 用法 差不多，Glide 的用法可以参考
- 网络框架的使用，使用了retrofit和 Rxjava，具体可以参考
- 日志库的使用Logger，进一步的封装
- 下拉刷新，上拉加载更多的框架
- datading的 使用
- 百度定位SDK

## 技术点
- MVP架构模式的使用
- BaseViewPagerFragment和BaseListFragment 的 封装使用
- BaseMVPActivity的封装使用
- BaseReecyclerHolder和 BaseRecyclerAdapter的 封装使用
- Fragemnt与Activity的通讯 方式
- Fragment的懒加载
- MaterailDesign 的设计与使用
- CoodinatorLayout的使用




## 数据来源

###  新闻
数据来源来自百度开源API，主要 有关于世界 ，科技，体育的新闻

### 美图
数据来源于TnGou（天狗网），


------------


## 图片加载框架Picasso和 Glide的使用 

Glide 官方仓库

https://github.com/bumptech/glide

Glide入门教程——4. 占位图& 淡入淡出动画

http://www.jianshu.com/p/15eaca9cb919

Glide 效果处理的仓库

https://github.com/wasabeef/glide-transformations

至于Picasso的 用法，可以参考

http://www.jianshu.com/p/976c86fa72bc?utm_campaign=haruki&utm_content=note&utm_medium=reader_share&utm_source=qq

---

## Retrofit的 用法

github地址：

https://github.com/square/retrofit

官网文档说明

https://futurestud.io/tutorials/retrofit-optional-path-parameters

### （1）retrofit的入门使用 

[Retrofit使用教程（一）- Retrofit入门详解](http://blog.csdn.net/gdutxiaoxu/article/details/52745491)

RxJava 与 Retrofit 结合的最佳实践


http://gank.io/post/56e80c2c677659311bed9841


### （2）进阶使用 
Retrofit请求数据对错误以及网络异常的处理


http://blog.csdn.net/jdsjlzx/article/details/51882661(Retrofit+RxJava 优雅的处理服务器返回异常、错误)
（这个是拦截Gson处理）

Rx处理服务器请求、缓存的完美封装

http://www.jianshu.com/p/cc064e3d5f21
这个实在Rxjava中处理

[ Rxjava+ReTrofit+okHttp深入浅出-终极封装二（网络请求）](
http://blog.csdn.net/wzgiceman/article/details/51939574)

同时可以查看 该 博客 相关的链接


----

## 日志库Logger的使用

官方地址：https://github.com/orhanobut/logger

### 封装使用

---

## 下拉刷新，上拉加载更多的框架
我这里使用的是这个框架：
https://github.com/bingoogolapple/BGARefreshLayout-Android

---


## databing的使用

1 添加依赖

```
android {
    dataBinding {
        enabled = true
}
}
```

2 使用

比较详细
https://realm.io/cn/news/data-binding-android-boyar-mount/

比较简单
https://www.aswifter.com/2015/07/11/android-data-binding-example/


```
<layout xmlns:android="http://schemas.android.com/apk/res/android"
>

    <data>

        <import type="android.view.View"/>
        <variable
name="chuanyue"
type="com.szl.mobileoa.main.chuanyue.ChuanyueFragment"/>

    </data>
中间添加我们的布局文件
</layout>
```


---

## 百度定位sdk

http://lbsyun.baidu.com/index.php?title=android-locsdk

---
# 技术点

## 技术点
- MVP架构模式的使用
- BaseViewPagerFragment和BaseListFragment 的 封装使用
- BaseMVPActivity的封装使用
- BaseReecyclerHolder和 BaseRecyclerAdapter的 封装使用
- Fragemnt与Activity的通讯 方式
- Fragment的懒加载
- MaterailDesign 的设计与使用
- CoodinatorLayout的使用

## MVP


官方 github   地址：
https://github.com/googlesamples/android-architecture

[参考博客：MVC,MVP,MVVM与架构经验谈](http://www.tianmaying.com/tutorial/AndroidMVC)

[参考博客](http://www.zjutkz.net/2016/04/13/%E9%80%89%E6%8B%A9%E6%81%90%E6%83%A7%E7%97%87%E7%9A%84%E7%A6%8F%E9%9F%B3%EF%BC%81%E6%95%99%E4%BD%A0%E8%AE%A4%E6%B8%85MVC%EF%BC%8CMVP%E5%92%8CMVVM/)

## MaterailDesign的使用

https://github.com/Clans/FloatingActionButton

---

最后推销一下我的各篇博客地址：有兴趣的可以看看

## 2016 博客

[**常用的自定义View例子一(FlowLayout)**](http://blog.csdn.net/gdutxiaoxu/article/details/51765428)

[**自定义View常用例子二（点击展开隐藏控件，九宫格图片控件）**](http://blog.csdn.net/gdutxiaoxu/article/details/51772308)

[**常用的自定义View例子三（MultiInterfaceView多界面处理）**](http://blog.csdn.net/gdutxiaoxu/article/details/51804844)


[**常用的自定义控件四（QuickBarView）**](http://blog.csdn.net/gdutxiaoxu/article/details/51804865)

[**转载请注明原博客地址：**](http://blog.csdn.net/gdutxiaoxu/article/details/51804865)


[**使用ViewDragHelper打造属于自己的DragLayout（抽屉开关 ）**](http://blog.csdn.net/gdutxiaoxu/article/details/51935896) 

[**IntentService使用及源码分析**](http://blog.csdn.net/gdutxiaoxu/article/details/52000680) 

[**HandlerThread源码分析**](http://blog.csdn.net/gdutxiaoxu/article/details/52060291)


[**AsyncTask使用及封装实践**](http://blog.csdn.net/gdutxiaoxu/article/details/57409326)

[**AsyncTask源码分析**](http://blog.csdn.net/gdutxiaoxu/article/details/57416380)


[带你了解Android常见的内存缓存算法](http://blog.csdn.net/gdutxiaoxu/article/details/51914000)

[**装饰者模式及其应用**](http://blog.csdn.net/gdutxiaoxu/article/details/51885105) 



[**观察者设计模式 Vs 事件委托（java）**](http://blog.csdn.net/gdutxiaoxu/article/details/51824769)



[**建造者模式（Builder）及其应用**](http://blog.csdn.net/gdutxiaoxu/article/details/52060291) 


**[ViewPager，ScrollView 嵌套ViewPager滑动冲突解决](http://blog.csdn.net/gdutxiaoxu/article/details/52939127)**


**[ android 监听网络状态的变化及实战](http://blog.csdn.net/gdutxiaoxu/article/details/53008266)**

[**你真的了解View的坐标吗？:**](http://blog.csdn.net/gdutxiaoxu/article/details/53700020)

[**16年，平凡而又收获的一年**](http://blog.csdn.net/gdutxiaoxu/article/details/53958513)

---

## 2017 博客

[**使用第三方框架解耦的一种思路——简单工厂模式的运用**](http://blog.csdn.net/gdutxiaoxu/article/details/54564442)

[**AsyncTask使用及封装实践**](http://blog.csdn.net/gdutxiaoxu/article/details/57409326)

**[java Type 详解](http://blog.csdn.net/gdutxiaoxu/article/details/68926515)**

**[java 反射机制详解](http://blog.csdn.net/gdutxiaoxu/article/details/68947735)**

**[注解使用入门（一）](http://blog.csdn.net/gdutxiaoxu/article/details/52017033)**

**[Android 自定义编译时注解1 - 简单的例子](http://blog.csdn.net/gdutxiaoxu/article/details/70244169)**



