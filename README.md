# 项目背景
> &emsp;近些年，随着农业现代化的快速发展和农村经济体制的不断完善，我国政府高度重视农业经济的发展，因此兴建现代化的农业工程对我国宏观经济的发展意义重大。俗话说：“民以食为天”，可见，粮食是人类最基本的生存资料。结合我国的具体国情加以分析，由于我国是个农业大国，首要的问题就是解决人口吃饭问题，但与此同时农业灾害也一直在影响着我国的农业发展。据资料显示，近10年来，农业灾害对我国造成的经济损失每年都在1000亿以上。农作物病虫害是我国的主要农业灾害之一，其发生范围和严重程度对我国国民经济、特别是农业生产上造成了重大损失。而且病虫害问题会直接影响农作物的质量和产量，进而影响到农户的收益。传统的应对方案，主要是求助当地农资经销商或农技专家。但农资经销商的卖药目的性强，未必会给出客观的建议，而农技专家资源则极度稀缺，很难找到。
因此，对农作物进行准确的病害识别并推荐合适的防治措施，不仅对于农业生产意义重大，对于改善整个社会经济也有帮助。<br>[查看出处](https://mp.weixin.qq.com/s/rsxmHmYztbsrUD_c678AJw)

# 项目介绍
「智农有道」从这一痛点切入，将Deep Learning图像识别技术和地图相册的实时全景结合融入app，让我们的app可以独自识别出相应病害。用户一旦遇到作物病害问题，可以直接拿出手机打开我们的「智农有道」并对植株叶片或者果实进行拍照，通过训练好的模型，app会自动匹配出相似度最高的疾病，对应详情和解决方法也会相应展示给用户，让用户更快速的诊断病害并找到解决防治方案。

<div align="center">

# 智农有道

<img src="https://upload-images.jianshu.io/upload_images/9140378-9e97dfce5f8b7537.png" height="100px" width="100px"/>

## 一款专为农户设计的农作物病虫害识别APP

</div>

## 功能介绍

|||||
|:--|:--|:--|:--|
|“拍照识别病虫害”：用户可以对发病的作物叶片或者果实进行拍照，裁剪对应发病区域，等待2~3秒即可以返回匹配率最高的三个疾病，并将识别率标记到对应的疾病卡片上，显示在照片的上方。用户可以通过拍照识别结构卡片，会显示对应疾病的具体信息、危害病症、传播途径、发病条件和治理方法。帮助用户快速找到解决方法，对症下药。|![拍照识别-拍照.png](https://upload-images.jianshu.io/upload_images/9140378-93581bf1475821a5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![拍照识别-结果分析.png](https://upload-images.jianshu.io/upload_images/9140378-b97c530ee02a717f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)||
|“大数据 农技学习”：该界面罗列了100多种蔬菜、粮棉油、水果、经济作物等几类常见的农作物，并使用TabLayout + Fragment滑动布局加载这些农作物。可以使大量数据流畅的展示给用户。用户通过点击对应疾病的卡片，获取当前病虫害的典型照片以及具体信息，包括：对应症状、病原、传播途径和发病条件、防治方法等等。为解决大多数农民用户不会拼音、难以识字的问题，该界面也配备了多方言的语音朗读，解决南北语言差异问题的同时也构成了良好的交互界面，方便用户使用。 |![农技学习-展示列表.png](https://upload-images.jianshu.io/upload_images/9140378-2998fe742b6c60d4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![农技学习-玉米.png](https://upload-images.jianshu.io/upload_images/9140378-2ccbedfbb5e38010.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![农技学习-疾病描述.png](https://upload-images.jianshu.io/upload_images/9140378-32085020bdf7ba9b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|
|“病害区域规划”：农户可以对特定种植区域进行标记划分，我们分为了绿色(作物长势良好)、红色(作物患病较多)、蓝色(作物成熟待收)三种特定围栏。用户只需点击“创建围栏”按钮并输入标题、长(m)和宽(m)，确认后会根据算法将实际距离转换为地图LatLng距离标记到地图上，当用户下一次进入该区域时，app会产生震动并播报“您已进入**区域”，为用户方便、精确、高效的管理区域化种植提供一份保障。|![地图-疾病围栏.png](https://upload-images.jianshu.io/upload_images/9140378-b17cc0226dfe3709.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![指定地点路径规划.png](https://upload-images.jianshu.io/upload_images/9140378-f48bf9f2adb5824c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![地图-导航.png](https://upload-images.jianshu.io/upload_images/9140378-64d4d29e8203d355.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)||
|“农业实时资讯”： 用户在这个界面查看最新的农业头条、农业政策、会展以及一些疾病防治和养殖技巧。使用户可以方便快速的掌握一些有关农耕的政策、技巧，及时做好农作物疾病预防的准备。点开每一条新闻，会进入到新闻的详情界面，我们在这里设计了顶部轮播标题和三个可以折叠的悬浮按钮，呈现一个Material Design的设计感。并且内置了“语音播报”的功能，可以在文章的任意文字位置进行播报，并且同时提供多方言选择。|![新闻主页.png](https://upload-images.jianshu.io/upload_images/9140378-f911d5dc09b0dc01.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![新闻详情.png](https://upload-images.jianshu.io/upload_images/9140378-903f989e4f840792.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)||
|“农业问答”：用户可以点击“发表农友圈”按钮，选择对应模块、病害图片和具体信息进行发表，其他的用户或者专家可以给予一些建议和帮助，并且我们的官方账号会在疾病易发期发布关于疾病防治的方法和常用的病害用药推荐。用户可以根据最近的情况，制作合理整治方案。|![农友圈-发表问题.png](https://upload-images.jianshu.io/upload_images/9140378-e199ef6aa472f8bd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![农友圈-发表问题2.png](https://upload-images.jianshu.io/upload_images/9140378-f212a60d503d19a4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)||
|“拍照历史”：用户的每次拍照获取的病虫害图片，会实时上传服务器同步加载到该界面，并且以时间轴的方式展示出来，用户通过点击对应条目，即可在地图的相应位置上显示出来，让用户对最近的记录一目了然。|![地图显示.png](https://upload-images.jianshu.io/upload_images/9140378-1b18d5522570f09a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![拍照时间轴.png](https://upload-images.jianshu.io/upload_images/9140378-ef9614a7e81ecc21.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)||
|“在线问答”：我们将我们训练的自定义语料加载到科大讯飞AIUI智能语音引擎，用户可以通过文字或者语音录入的方式发送信息进行提问，等待语音引擎加载完毕后，将优质回答以文字和语音播报的形式反馈给用户。这种在线问答可以及时为用户提供对应病害的解决方法。|![农业助手-2.png](https://upload-images.jianshu.io/upload_images/9140378-bc424c455376bce1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|||

## 数据来源

+ [AI Challenger提供进5万张开源叶片数据集](https://zhuanlan.zhihu.com/p/28509667)
+ [中国科学数据网 大田作物病害识别研究图像数据集](http://csdata.org/p/290/)
+ [中国农业种质信息网](http://www.cgris.net/)
+ [第一农经网](http://www.1nongjing.com/)

## 团队介绍和人员分工

**所在学校**  中北大学 </br>
**团队名称**  太行智农小队 </br>
**团队人员及分工** </br>

+ 队长 王浩  APP框架、Deep Learning模型搭建，协同过滤算法实现</br>
+ 队员 冯小涛  后台服务器搭建、数据库的设计与开发</br>
+ 队员 王红亮  数据获取、网络爬虫的编写</br>
+ 队员 王琨茹  APP原型设计、界面优化、美工</br>
+ 队员 朱江游  图标图片提供、色彩搭配设计、项目功能策划</br>

## 其他

使用到的第三方接口和开源代码:

+ 科大讯飞AIUI语音引擎
+ 百度地图开放接口
+ 瀑布时间轴：'com.vivian.widgets:TimeLineItemDecoration:1.5'
+ 悬浮按钮：'com.getbase:floatingactionbutton:1.10.1'
+ 网络图片加载：'com.github.bumptech.glide:glide:4.5.0'
+ 圆角图片：'de.hdodenhof:circleimageview:3.0.1'
