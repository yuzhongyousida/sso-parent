## 一、工程说明
这是一个简单的同域名下的SSO单点登录功能demo

## 二、工程结构
工程名        |  说明 
-------------|---------------------------------
sso-parent   |parent工程
sso-client   |sso的对外SDK
sso-server   |sso的核心服务（包括登录页面）
sso-demo     |依赖sso的普通服务（即接入SSO的其他服务）

## 三、单点登录的原理说明

#### 3.1、普通的登录认证机制（单服务）
![image](https://github.com/yuzhongyousida/sso-parent/blob/master/sso-server/src/main/resources/images/image-01.png)

如上图所示，我们在浏览器（Browser）中访问一个应用，这个应用需要登录，我们填写完用户名和密码后，完成登录认证。这时，我们在这个用户的session中标记登录状态为yes（已登录），同时在浏览器（Browser）中写入Cookie，这个Cookie是这个用户的唯一标识。下次我们再访问这个应用的时候，请求中会带上这个Cookie，服务端会根据这个Cookie找到对应的session，通过session来判断这个用户是否登录。如果不做特殊配置，这个Cookie的名字叫做sessionid，值在服务端（server）是唯一的。


<br/>

#### 3.2、同域名下的单点登录（本工程中实现的方式）
![image](https://github.com/yuzhongyousida/sso-parent/blob/master/sso-server/src/main/resources/images/image-02.png)

一个企业一般情况下只有一个域名，通过二级域名区分不同的系统。比如我们有个域名叫做：company.com，同时有两个业务系统分别为：app1.company.com和app2.company.com。我们要做单点登录（SSO），需要一个登录系统，叫做：sso.company.com。

我们只要在sso.company.com登录，app1.company.com和app2.company.com就也是登录状态了。通过上面的登陆认证机制，我们可以知道，在sso.company.com中登录了，其实是在sso.company.com的服务端的session中记录了登录状态，同时在浏览器端（Browser）的sso.company.com下写入了Cookie。那么我们怎么才能让app1.company.com和app2.company.com登录呢？这里有两个问题：
> - Cookie是不能跨域的，我们Cookie的domain属性是sso.a.com，在给app1.a.com和app2.a.com发送请求是带不上的。
> - sso、app1和app2是不同的应用，它们的session存在自己的应用内，是不共享的。

<br/>

#### 3.3、跨域名下的单点登录（SSO的标准实现，本工程中未实现）
![image](https://github.com/yuzhongyousida/sso-parent/blob/master/sso-server/src/main/resources/images/image-03.png)

上图是CAS官网上的标准流程，具体流程如下：

- 1、用户访问app系统，app系统是需要登录的，但用户现在没有登录。
- 2、跳转到CAS server，即SSO登录系统，以后图中的CAS Server我们统一叫做SSO系统。 SSO系统也没有登录，弹出用户登录页。
- 3、用户填写用户名、密码，SSO系统进行认证后，将登录状态写入SSO的session，浏览器（Browser）中写入SSO域下的Cookie。
- 4、SSO系统登录完成后会生成一个ST（Service Ticket），然后跳转到app系统，同时将ST作为参数传递给app系统。
- 5、app系统拿到ST后，从后台向SSO发送请求，验证ST是否有效。
- 6、验证通过后，app系统将登录状态写入session并设置app域下的Cookie。

至此，跨域单点登录就完成了。以后我们再访问app系统时，app就是登录的。接下来，我们再看看访问app2系统时的流程。

- 1、用户访问app2系统，app2系统没有登录，跳转到SSO。
- 2、由于SSO已经登录了，不需要重新登录认证。
- 3、SSO生成ST，浏览器跳转到app2系统，并将ST作为参数传递给app2。
- 4、app2拿到ST，后台访问SSO，验证ST是否有效。
- 5、验证成功后，app2将登录状态写入session，并在app2域下写入Cookie。

这样，app2系统不需要走登录流程，就已经是登录了。SSO，app和app2在不同的域，它们之间的session不共享也是没问题的。
