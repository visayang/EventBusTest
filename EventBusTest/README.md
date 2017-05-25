#基于EventBus和OkHttp搭建的网络框架
    ##简介
    本编文章是基于EventBus3.0和OkHttp搭建的网络框架。一方面利用了EventBus的事件订阅机制，另一方面使用了方便快捷的OkHttp网络请求库，将两者结合起来，更方便快捷，方便日后的开发工作。另外也封装了，网络加载进度对话框，fastjson工具类，网络请求等工具类，方便后来使用。
    #项目结构（本架构的项目结构图）
    ![目录结构](http://upload-images.jianshu.io/upload_images/4130589-e80e82cde75cf72d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    #项目准备
    引入以下几个库
    compile'com.squareup.okhttp:okhttp:2.7.5'
    compile'com.squareup.okio:okio:1.13.0'
    compile'com.alibaba:fastjson:1.2.32'
    compile'org.greenrobot:eventbus:3.0.0'
    compile'com.jakewharton:butterknife:7.0.1'
    我这里暂时使用的是K780数据里面的天气接口进行测试的，暂时没对返回的数据进行页面展示的二次加工。只是搭建这个网络框架环境而已
    #项目介绍
    ##1 BaseActivity（初始化，抽象方法，eventbus的接收）
    ```
    /**
    * Created by Chad on 2017/5/24.
    * Version 1.0
    */
    public abstract classBaseActivityextendsAppCompatActivity {
    protectedRequestTemplatemRequest;
    @Override
    protected voidonCreate(@NullableBundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    beforeSetContentView();
    setContentView(getContentViewId());
    ButterKnife.bind(this);
    EventBusUtils.register(this);
    mRequest=newRequestTemplate(this);
    initProgressBar();
    initData();
    }
    protected abstract voidinitData();
    protected abstract intgetContentViewId();
    /**
    *  主线程运行
    *@paramobject
    */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public voidOnEvent(EventMessage object) {
    onNormEvent((EventMessage) object);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public voidOnEvent(ServiceMessage object) {
    onServiceEvent((ServiceMessage) object);
    }
    /**
    * 子线程运行
    *@paramobject
    */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public voidOnBackGround(BackGroundMessage object) {
    onThreadEvent((BackGroundMessage) object);
    }
    protected abstract voidonThreadEvent(BackGroundMessage object);
    protected abstract voidonServiceEvent(ServiceMessage object);
    protected abstract voidonNormEvent(EventMessage object);
    protected voidbeforeSetContentView() {
    }
    @Override
    protected voidonDestroy() {
    super.onDestroy();
    EventBusUtils.unregister(this);
    }
    /*==============进度条==============*/
    privateProgressHUDmProgressHUD;
    private voidinitProgressBar() {
    if(mProgressHUD==null) {
    mProgressHUD= ProgressHUD.newInstance(this,"正在加载中...",false,null);
    }
    mProgressHUD.setMessage("正在加载中...");
    }
    public voidshowProgressBar() {
    if(mProgressHUD!=null&&mProgressHUD.isShowing())
    return;
    //        initProgressBar();
    mProgressHUD.show();
    }
    public voidshowProgressBar(String message) {
    if(mProgressHUD!=null&&mProgressHUD.isShowing())
    return;
    //        initProgressBar();
    mProgressHUD.setMessage(message);
    mProgressHUD.show();
    }
    public voidstopProgressBar() {
    if(mProgressHUD!=null&&mProgressHUD.isShowing()) {
    mProgressHUD.dismiss();
    }
    }
    }
    ```
    这里提到一点eventbus的使用，这里用的是3.0，发起方调用post函数，接收发接受，具体接收的方法是根据括号内类名的类别来判断的，上面写的那个Main或者Background只是代表它是在哪个线程运行。如果是子线程运行，需要post发起的时候也是在子线程中。这列附上github上eventbus的地址，不懂怎么使用的可以去看下。[EventBus](https://github.com/greenrobot/EventBus)
    ##2 SucApplication（刚开始启动就初始化一些操作，获取context）
    ```
    public classSucApplicationextendsApplication {
    private staticSucApplicationmApplication;
    @Override
    public voidonCreate() {
    super.onCreate();
    mApplication=this;
    }
    public staticSucApplication getApplication() {
    returnmApplication;
    }
    }
    ```
    ##3 utils（工具类的集合）
    ###3.1 EventBusUtils,获取实例。。。
    ###3.2 FastJsonUtils 将json进一步转化
    ###3.3 HttpUtil 将okhttp封装，并配合eventbus进行信息发送
    ###3.4 RequestTemplate 将网络请求的一些参数进行封装，比如post的键值对
    ###3.5 T toast的封装
    ###3.6 Utils  检查网络
    ##4 MainActivity
    ```
    public classMainActivityextendsBaseActivity {
    @Bind(R.id.textView)
    TextViewtextView;
    @Override
    protected voidonCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_main);
    }
    @Override
    protected voidinitData() {
    mRequest.getWeather();
    }
    @Override
    protected intgetContentViewId() {
    returnR.layout.activity_main;
    }
    @Override
    protected voidonThreadEvent(BackGroundMessage object) {
    newThread(newRunnable() {
    @Override
    public voidrun() {
    SystemClock.sleep(5000);
    runOnUiThread(newRunnable() {
    @Override
    public voidrun() {
    T.show("EventBus好美");
    }
    });
    }
    }).start();
    }
    @Override
    protected voidonServiceEvent(ServiceMessage sm) {
    if(sm.isSuccess){
    switch(sm.tag){
    caseRequestTemplate.WEATHER:
    textView.setText(sm.msg);
    break;
    }
    }
    }
    @Override
    protected voidonNormEvent(EventMessage em) {
    switch(em.tag){
    caseEventMessage.NO_NETWORK:
    textView.setText("我是主线程返回的");
    break;
    }
    }
    }
    ```
    这里我写了三个对象，EventMessage,ServiceMessage和BackGroundMessage
    前两个是作用主线程的返回的，最后一个是作用于子线程用的。其实完全可以写一个类，只不过需要在这个类里面有一个字段可以用于区分就可以了。
    ####最后显示的结果是这样子的。完成了基础网络架构的搭建可能数据没处理看起来挺怪异。如有细节部分需要了解，还需去项目中去查看。

    ![Paste_Image.png](http://upload-images.jianshu.io/upload_images/4130589-4eddb4a1daa75e0f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)