# guava-rate_limit-demo
guava做限流demo
经常听别人说guava，今天手痒写了一个demo -_-
常用的限流算法由：漏桶算法和令牌桶算法。

一、漏桶算法
漏桶作为计量工具（The Leaky Bucket Algorithm as a Meter）时，可以用于流量整形（Traffic Shaping）和流量控制（TrafficPolicing），漏桶算法的描述如下：

一个固定容量的漏桶，按照常量固定速率流出水滴；
如果桶是空的，则不需流出水滴；
可以以任意速率流入水滴到漏桶；
如果流入水滴超出了桶的容量，则流入的水滴溢出了（被丢弃），而漏桶容量是不变的。
漏桶(Leaky Bucket)算法思路很简单,水(请求)先进入到漏桶里,漏桶以一定的速度出水(接口有响应速率),当水流入速度过大会直接溢出(访问频率超过接口响应速率),
然后就拒绝请求,可以看出漏桶算法能强行限制数据的传输速率.示意图如下:
![](https://github.com/mason-Yu/guava-rate_limit-demo/blob/master/image/bucket.jpg)


可见这里有两个变量,一个是桶的大小,支持流量突发增多时可以存多少的水(burst),另一个是水桶漏洞的大小(rate)。

因为漏桶的漏出速率是固定的参数,所以,即使网络中不存在资源冲突(没有发生拥塞),漏桶算法也不能使流突发(burst)到端口速率.因此,
漏桶算法对于存在突发特性的流量来说缺乏效率.

二、令牌桶算法
令牌桶算法是一个存放固定容量令牌的桶，按照固定速率往桶里添加令牌。令牌桶算法的描述如下：
![](https://github.com/mason-Yu/guava-rate_limit-demo/blob/master/image/token_bucket.jpg)

假设限制2r/s，则按照500毫秒的固定速率往桶中添加令牌；
桶中最多存放b个令牌，当桶满时，新添加的令牌被丢弃或拒绝；
当一个n个字节大小的数据包到达，将从桶中删除n个令牌，接着数据包被发送到网络上；
如果桶中的令牌不足n个，则不会删除令牌，且该数据包将被限流（要么丢弃，要么缓冲区等待）。
令牌桶算法(Token Bucket)和 Leaky Bucket 效果一样但方向相反的算法,更加容易理解.随着时间流逝,系统会按恒定1/QPS时间间隔(如果QPS=100,则间隔是10ms)往桶里加入Token(想象和漏洞漏水相反,有个水龙头在不断的加水),如果桶已经满了就不再加了.新请求来临时,会各自拿走一个Token,如果没有Token可拿了就阻塞或者拒绝服务



令牌桶的另外一个好处是可以方便的改变速度. 一旦需要提高速率,则按需提高放入桶中的令牌的速率. 一般会定时(比如100毫秒)往桶中增加一定数量的令牌, 有些变种算法则实时的计算应该增加的令牌的数量
