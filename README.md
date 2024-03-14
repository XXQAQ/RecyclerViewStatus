# 这是一款针对RecyclerView的多状态管理开源框架


开发中经常会遇到列表的空、加载、错误等状态的切换需求。而市面上针对RecyclerView的多状态管理大概有以下两种方案

1. 在RecyclerView的上层节点添加新的父控件，当需要显示为内容布局的时候则将RecyclerView进行显示；当显示其他状态布局的时候则将RecyclerView隐藏并显示对应的容器

2. 直接在RecyclerView的Adapter中定义多中itemViewType，然后在onBindViewHolder中判断其itemViewType，再进行相应的处理。

两种方法都有一些弊端，第一种方法的问题在于：由于添加了新的节点所以并不适用于某些特定的布局结构；而第二种方法由于侵入了原本的业务代码导致adapter身兼多职，代码维护效率极差。

那有没有一种更好的方式，能在不影响布局层级的前提下兼顾维护效率？

**本框架就是为此而生，并且使用理念极其简单：**



## 以下为使用文档

在你的Activity/Fragment中直接创建RecyclerViewStatus作为成员变量

`

        RecyclerViewStatus recyclerViewStatus = new RecyclerViewStatus();
`

PS：正式使用前确保 recyclerView已调用 setAdapter 和 setLayoutManager

`

        //确保recyclerView setAdapter和setLayoutManager后调用attachToRecyclerView
        recyclerViewStatus.attachToRecyclerView(recyclerView);
`

构造您所需要的StateInflate即完成全部状态的初始化

首个参数为状态id(自定义)，第二个参数为布局id，第三个等同于onBindViewHolder的回调（可不给）

`

        recyclerViewStatus.initStates(Arrays.asList(
                new StateInflate("empty",R.layout.empty_layout),
                new StateInflate<String>("error", R.layout.error_layout, new StateInflate.Block<String>() {
                    @Override
                    public void onBind(StateAdapter.StateViewHolder viewHolder, String s) {
                        viewHolder.getTextView(R.id.errorText).setText(s);
                    }
                }),
                new StateInflate("loading",R.layout.loading_layout)
        ));
`

接下来，只需要在你业务需要的场合调用showContent或者showState，即可切换至不同的状态

`

        //显示原内容
        recyclerViewStatus.showContent();

        //显示加载状态
        recyclerViewStatus.showState("loading");
        
        //显示错误状态
        recyclerViewStatus.showState("error","网络异常，无法获取网络");
`

## 实现原理

RecyclerViewStatus底层是基于切换recyclerView的Adapter和LayoutManager实现内容布局到状态布局的切换，并且在内部管理了一个ConcatAdapter，动态add或remove对应状态的Adapter，从而实现具体状态的切换。
