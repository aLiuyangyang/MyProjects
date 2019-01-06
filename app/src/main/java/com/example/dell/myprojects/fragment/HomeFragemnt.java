package com.example.dell.myprojects.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.myprojects.R;
import com.example.dell.myprojects.adapter.MyChildHotAdapter;
import com.example.dell.myprojects.adapter.MyHotProductAdapter;
import com.example.dell.myprojects.adapter.MyLinkAgeAdapter;
import com.example.dell.myprojects.adapter.MyLinkAgeTwoAdapter;
import com.example.dell.myprojects.adapter.MyMagicfashionAdapter;
import com.example.dell.myprojects.adapter.MyPagerAdapter;
import com.example.dell.myprojects.adapter.MyQualityAdapter;
import com.example.dell.myprojects.adapter.MySearchApadter;
import com.example.dell.myprojects.adapter.MyShopaAdapter;
import com.example.dell.myprojects.adapter.PagerTransformer;
import com.example.dell.myprojects.bean.BannerBean;
import com.example.dell.myprojects.bean.ChildHomeBean;
import com.example.dell.myprojects.bean.HotProBean;
import com.example.dell.myprojects.bean.LinkageBean;
import com.example.dell.myprojects.bean.LinkageTwoBean;
import com.example.dell.myprojects.bean.SearchBean;
import com.example.dell.myprojects.bean.ShopBean;
import com.example.dell.myprojects.presenter.PresenterImpl;
import com.example.dell.myprojects.util.Apis;
import com.example.dell.myprojects.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragemnt extends BaseFragment implements View.OnClickListener,IView {
    @BindView(R.id.home_viewpager)
     ViewPager home_viewpager;
    @BindView(R.id.home_hot_product)
    RecyclerView home_hot_product;
    @BindView(R.id.home_Magic_fashion)
    RecyclerView home_Magic_fashion;
    @BindView(R.id.hot_xrecy)
    XRecyclerView hot_xrecy;
    @BindView(R.id.home_quality_life)
    RecyclerView home_quality_life;
    @BindView(R.id.home_scroll)
    ScrollView home_scroll;
   /* @BindView(R.id.serch_linear)
    LinearLayout serch_linear;*/
    @BindView(R.id.image_homemagic)
    ImageView image_homemagic;
    @BindView(R.id.quality_xrecy)
    XRecyclerView quality_xrecy;
    @BindView(R.id.search_xrecy)
    XRecyclerView search_xrecy;
    @BindView(R.id.quality)
    ImageView quality;
    @BindView(R.id.home_edit)
    EditText home_edit;
    @BindView(R.id.shop_linear)
    LinearLayout shop_linear;
    @BindView(R.id.shop_xrecy)
    XRecyclerView shop_xrecy;
    @BindView(R.id.home_text)
    TextView home_text;
    @BindView(R.id.home_search)
    ImageView home_search;
    @BindView(R.id.classifyrela)
    LinearLayout classifyrela;
    @BindView(R.id.home_childmagic_rela)
    RelativeLayout home_childmagic_rela;
    @BindView(R.id.magic_xrecy)
    XRecyclerView magic_xrecy;
    @BindView(R.id.linkage_one_recy)
    RecyclerView linkage_one_recy;
    @BindView(R.id.linkage_two_recy)
    RecyclerView linkage_two_recy;
    @BindView(R.id.search_none)
    RelativeLayout search_none;
    private ImageView classify;
    private ImageView home_hot_image;
    private RelativeLayout home_childhot_rela,home_childquality_rela;
    private PresenterImpl presenter;
    private List<BannerBean.ResultBean> result;
    private int i;
    private  boolean isReplace=false;
    private  boolean isShow=false;
    private int COUNT=10;
    private int page;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            i++;
            home_viewpager.setCurrentItem(i);
            sendEmptyMessageDelayed(0,2000);
        }
    };
    private MyLinkAgeAdapter linkAgeadapter;
    private MyLinkAgeTwoAdapter linkAgeTwoAdapter;
    private MyPagerAdapter adapter;
    private MyHotProductAdapter hotadapter;
    private MyMagicfashionAdapter magicadapter;
    private MyQualityAdapter qualityadapter;
    private MyChildHotAdapter childHotAdapter;
    private MySearchApadter searchApadter;
    private MyShopaAdapter shopaAdapter;
    private int hotid;
    private int magicid;
    private int qualityid;
    private String name;
    @Override
    protected void initData() {
        //网络请求
        myRequrst();
    }
    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
        page=1;
        presenter=new PresenterImpl(this);
        classify=view.findViewById(R.id.classify);
        classify.setOnClickListener(this);
        home_hot_image=view.findViewById(R.id.home_hot_image);
        home_childhot_rela=view.findViewById(R.id.home_childhot_rela);
        home_childquality_rela=view.findViewById(R.id.home_childquality_rela);
        home_childquality_rela.setOnClickListener(this);
        home_search.setOnClickListener(this);
        home_text.setOnClickListener(this);
        image_homemagic.setOnClickListener(this);
        quality.setOnClickListener(this);
        linkage_two_recy.setOnClickListener(this);

        home_hot_image.setOnClickListener(this);
        name = home_edit.getText().toString();
        //轮播图
        inifBanner();
        //热销商品
        LinearLayoutManager gridLayoutManager=new LinearLayoutManager(getContext());
        gridLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        home_hot_product.setLayoutManager(gridLayoutManager);
        hotadapter=new MyHotProductAdapter(getContext());
        home_hot_product.setAdapter(hotadapter);
        //魔力时尚
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        home_Magic_fashion.setLayoutManager(linearLayoutManager);
        magicadapter=new MyMagicfashionAdapter(getContext());
        home_Magic_fashion.setAdapter(magicadapter);
        //品质生活
        GridLayoutManager qgridLayoutManager=new GridLayoutManager(getContext(),2);
        qgridLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        home_quality_life.setLayoutManager(qgridLayoutManager);
        qualityadapter=new MyQualityAdapter(getContext());
        home_quality_life.setAdapter(qualityadapter);
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }
    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.home_hot_image://热销商品全部商品
              home_scroll.setVisibility(View.GONE);
              home_childquality_rela.setVisibility(View.GONE);
              home_childmagic_rela.setVisibility(View.GONE);
              home_childhot_rela.setVisibility(View.VISIBLE);
              setHotShopp();
              break;
          case R.id.image_homemagic://魔力时尚全部商品
              home_scroll.setVisibility(View.GONE);
              home_childquality_rela.setVisibility(View.GONE);
              home_childmagic_rela.setVisibility(View.VISIBLE);
              home_childhot_rela.setVisibility(View.GONE);
              setMagicXP();
              break;
          case R.id.quality://品质生活全部商品
              home_scroll.setVisibility(View.GONE);
              home_childquality_rela.setVisibility(View.VISIBLE);
              home_childhot_rela.setVisibility(View.GONE);
              home_childmagic_rela.setVisibility(View.GONE);
              setQuality();
              break;
          case R.id.home_search://搜索图
              home_text.setVisibility(View.VISIBLE);
              home_search.setVisibility(View.GONE);
              search_xrecy.setVisibility(View.VISIBLE);
              home_scroll.setVisibility(View.GONE);
              home_edit.setVisibility(View.VISIBLE);
              home_childhot_rela.setVisibility(View.GONE);
              home_childmagic_rela.setVisibility(View.GONE);
              home_childquality_rela.setVisibility(View.GONE);
              search_none.setVisibility(View.GONE);
              break;
          case R.id.home_text://搜索字
              name = home_edit.getText().toString();
              if(!name.equals("")){
                  home_childhot_rela.setVisibility(View.GONE);
                  home_childmagic_rela.setVisibility(View.GONE);
                  home_childquality_rela.setVisibility(View.GONE);
                  search_xrecy.setVisibility(View.VISIBLE);
                  home_edit.setText("");
                  setsearch();
                  if (!isShow){
                      shop_xrecy.setVisibility(View.GONE);
                  }else {
                      shop_xrecy.setVisibility(View.VISIBLE);
                  }
                  isShow=!isShow;
              }else{
                  Toast.makeText(getContext(), "商品名称不能为空", Toast.LENGTH_SHORT).show();
              }

              break;
          case R.id.classify:
              if (!isReplace){
                  classifyrela.setVisibility(View.VISIBLE);
                  search_xrecy.setVisibility(View.GONE);
                  setlinkage();
              }else {
                  classifyrela.setVisibility(View.GONE);
              }
              isReplace=!isReplace;
              break;
              default:break;
      }
    }

    @Override
    public void setDataSuccess(Object data) {
        if (data instanceof BannerBean){//轮播
            BannerBean bannerBean= (BannerBean) data;
            if (bannerBean.getStatus().equals("0000")){
                result = bannerBean.getResult();
                adapter=new MyPagerAdapter(getContext(),result);
                home_viewpager.setAdapter(adapter);
            }
        } else if (data instanceof HotProBean){//页面商品
            HotProBean hotProBean= (HotProBean) data;
            if (hotProBean.getMessage().equals("查询成功")){
                //热销新品
                hotid = hotProBean.getResult().getRxxp().get(0).getId();
                List<HotProBean.ResultBean.RxxpBean.CommodityListBean> rxxp = hotProBean.getResult().getRxxp().get(0).getCommodityList();
                hotadapter.setHotList(rxxp);
                //魔力时尚
                magicid = hotProBean.getResult().getMlss().get(0).getId();
                List<HotProBean.ResultBean.MlssBean.CommodityListBeanXX> commodityList = hotProBean.getResult().getMlss().get(0).getCommodityList();
                magicadapter.setListBeanXXES(commodityList);
                //品质生活
                qualityid = hotProBean.getResult().getPzsh().get(0).getId();
                List<HotProBean.ResultBean.PzshBean.CommodityListBeanX> commodityListq = hotProBean.getResult().getPzsh().get(0).getCommodityList();
                qualityadapter.setListBeanXES(commodityListq);
            }
        }else if (data instanceof ChildHomeBean){//全部商品
            ChildHomeBean childHomeBean= (ChildHomeBean) data;
            List<ChildHomeBean.ResultBean> result = childHomeBean.getResult();
            if (childHomeBean.getStatus().equals("0000")){
                if (page==1){
                    if (result.size()==0){
                        search_none.setVisibility(View.VISIBLE);
                    }else {
                        search_none.setVisibility(View.GONE);
                    }
                    childHotAdapter.setHotlist(result);
                }else {
                    childHotAdapter.addHotlist(result);
                }
                page++;
                hot_xrecy.refreshComplete();
                hot_xrecy.loadMoreComplete();
                magic_xrecy.refreshComplete();
                magic_xrecy.loadMoreComplete();
                quality_xrecy.loadMoreComplete();
                quality_xrecy.refreshComplete();
            }
        }else if (data instanceof SearchBean){//搜索
            SearchBean searchBean= (SearchBean) data;
            if (searchBean.getStatus().equals("0000")){
                List<SearchBean.ResultBean> result = searchBean.getResult();
                if (page==1){
                    searchApadter.setResultBeans(result);
                    if (result.size()==0){
                        search_none.setVisibility(View.VISIBLE);
                    }else {
                        search_none.setVisibility(View.GONE);
                    }
                }else {
                    searchApadter.addResultBeans(result);

                }
                page++;
                search_xrecy.refreshComplete();
                search_xrecy.loadMoreComplete();

            }
        }else if (data instanceof LinkageBean){//一级
            LinkageBean linkageBean= (LinkageBean) data;
            List<LinkageBean.ResultBean> resultBeanList = linkageBean.getResult();
            linkAgeadapter.setList(resultBeanList);
        }else if (data instanceof LinkageTwoBean){//二级
            LinkageTwoBean linkageTwoBean= (LinkageTwoBean) data;
            List<LinkageTwoBean.ResultBean> linkageTwoBeanResult = linkageTwoBean.getResult();
             linkAgeTwoAdapter.setList(linkageTwoBeanResult);
        }else if (data instanceof ShopBean){
            ShopBean shopBean= (ShopBean) data;
            List<ShopBean.ResultBean> result = shopBean.getResult();
            if (page==1){
                shopaAdapter.setList(result);
                if (result.size()==0){
                    search_none.setVisibility(View.VISIBLE);
                }else {
                    search_none.setVisibility(View.GONE);
                }
            }else {
                shopaAdapter.addList(result);
            }
            page++;
            shop_xrecy.loadMoreComplete();
            shop_xrecy.refreshComplete();
        }
    }
    @Override
    public void setDataFail(String ex) {
        Log.i("TAG","ex");
    }
    private void myRequrst() {//网络请求
        presenter.setRequestget(Apis.Banner_URL,BannerBean.class);
        presenter.setRequestget(Apis.HOT_URL,HotProBean.class);
        presenter.setRequestget(Apis.HOT_URL,HotProBean.class);
        presenter.setRequestget(Apis.HOT_URL,HotProBean.class);
    }
    private void childhot() {//热销商品列表
        presenter.setRequestget(String.format(Apis.HOT_CHILD,hotid,page,COUNT),ChildHomeBean.class);
    }
    private void childmagic() {
        presenter.setRequestget(String.format(Apis.HOT_CHILD,magicid,page,COUNT),ChildHomeBean.class);
    }
    private void childquality() {
        presenter.setRequestget(String.format(Apis.HOT_CHILD,qualityid,page,COUNT),ChildHomeBean.class);
    }
    private void homesearch(String name){
        presenter.setRequestget(String.format(Apis.SEARCH_URL,name,page,COUNT),SearchBean.class);
    }
    private void setHotShopp(){
        GridLayoutManager gridLayoutManagerhot=new GridLayoutManager(getContext(),2);
        gridLayoutManagerhot.setOrientation(OrientationHelper.VERTICAL);
        hot_xrecy.setLayoutManager(gridLayoutManagerhot);
        childHotAdapter=new MyChildHotAdapter(getContext());
        hot_xrecy.setAdapter(childHotAdapter);
        hot_xrecy.setPullRefreshEnabled(true);
        hot_xrecy.setLoadingMoreEnabled(true);
        hot_xrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                childhot();
            }
            @Override
            public void onLoadMore() {
                childhot();

            }

        });
        page=1;
        childhot();
    }
    private void setMagicXP() {
        GridLayoutManager gridLayoutManagermagic=new GridLayoutManager(getContext(),2);
        gridLayoutManagermagic.setOrientation(OrientationHelper.VERTICAL);
        magic_xrecy.setLayoutManager(gridLayoutManagermagic);
        childHotAdapter=new MyChildHotAdapter(getContext());
        magic_xrecy.setAdapter(childHotAdapter);
        magic_xrecy.setPullRefreshEnabled(true);
        magic_xrecy.setLoadingMoreEnabled(true);
        magic_xrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                childmagic();
            }
            @Override
            public void onLoadMore() {
                childmagic();
            }
        });
        page=1;
        childmagic();
    }

    private void setQuality() {
        GridLayoutManager gridLayoutManagermagic=new GridLayoutManager(getContext(),2);
        gridLayoutManagermagic.setOrientation(OrientationHelper.VERTICAL);
        quality_xrecy.setLayoutManager(gridLayoutManagermagic);
        childHotAdapter=new MyChildHotAdapter(getContext());
        quality_xrecy.setAdapter(childHotAdapter);
        quality_xrecy.setPullRefreshEnabled(true);
        quality_xrecy.setLoadingMoreEnabled(true);
        quality_xrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                childquality();
            }
            @Override
            public void onLoadMore() {
                childquality();
            }
        });
        page=1;
        childquality();
    }
    private void setsearch() {
        GridLayoutManager gridLayoutManagermagic=new GridLayoutManager(getContext(),2);
        gridLayoutManagermagic.setOrientation(OrientationHelper.VERTICAL);
        search_xrecy.setLayoutManager(gridLayoutManagermagic);
        searchApadter=new MySearchApadter(getContext());
        search_xrecy.setAdapter(searchApadter);
        search_xrecy.setPullRefreshEnabled(true);
        search_xrecy.setLoadingMoreEnabled(true);
        search_xrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                homesearch(name);
            }
            @Override
            public void onLoadMore() {
                homesearch(name);
            }
        });
        page=1;
        homesearch(name);
    }


    private void homeshow(final String cid) {
        GridLayoutManager gridLayoutManagermagic=new GridLayoutManager(getContext(),2);
        gridLayoutManagermagic.setOrientation(OrientationHelper.VERTICAL);
        shop_xrecy.setLayoutManager(gridLayoutManagermagic);
        shopaAdapter=new MyShopaAdapter(getContext());
        shop_xrecy.setAdapter(shopaAdapter);
        shop_xrecy.setLoadingMoreEnabled(true);
        shop_xrecy.setPullRefreshEnabled(true);
        shop_xrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
              presenter.setRequestget(String.format(Apis.ERJISHOP_URL,cid,page,COUNT),ShopBean.class);
            }
            @Override
            public void onLoadMore() {
                presenter.setRequestget(String.format(Apis.ERJISHOP_URL,cid,page,COUNT),ShopBean.class);
            }
        });
        page=1;
        presenter.setRequestget(String.format(Apis.ERJISHOP_URL,cid,page,COUNT),ShopBean.class);
    }

    private void setlinkage() {  //一级列表
        LinearLayoutManager gridLayoutManager=new LinearLayoutManager(getContext());
        gridLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        linkage_one_recy.setLayoutManager(gridLayoutManager);
        linkAgeadapter=new MyLinkAgeAdapter(getContext());
        linkage_one_recy.setAdapter(linkAgeadapter);
        presenter.setRequestget(Apis.LINKAGE_URL,LinkageBean.class);
        setlinkageTwo1();
        linkAgeadapter.setLinkageListener(new MyLinkAgeAdapter.LinkageListener() {
            @Override
            public void lin(String id) {
                setlinkageTwo(id);
            }
        });

    }
    private void setlinkageTwo1() {
        LinearLayoutManager gridLayoutManager=new LinearLayoutManager(getContext());
        gridLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        linkage_two_recy.setLayoutManager(gridLayoutManager);
        linkAgeTwoAdapter=new MyLinkAgeTwoAdapter(getContext());
        linkage_two_recy.setAdapter(linkAgeTwoAdapter);
        presenter.setRequestget(String.format(Apis.ELINKAGEERJI_URL,1001002),LinkageTwoBean.class);
        linkAgeTwoAdapter.setLinkAgeChildLineren(new MyLinkAgeTwoAdapter.LinkAgeChildLineren() {
            @Override
            public void linkageChild(String cid) {
                homeshow(cid);
                classifyrela.setVisibility(View.GONE);
                shop_linear.setVisibility(View.VISIBLE);
                home_scroll.setVisibility(View.GONE);
                search_xrecy.setVisibility(View.GONE);
            }
        });
    }
    private void setlinkageTwo(final String id) { //二级列表
        LinearLayoutManager gridLayoutManager=new LinearLayoutManager(getContext());
        gridLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        linkage_two_recy.setLayoutManager(gridLayoutManager);
        linkAgeTwoAdapter=new MyLinkAgeTwoAdapter(getContext());
        linkage_two_recy.setAdapter(linkAgeTwoAdapter);
        presenter.setRequestget(String.format(Apis.ELINKAGEERJI_URL,id),LinkageTwoBean.class);

        linkAgeTwoAdapter.setLinkAgeChildLineren(new MyLinkAgeTwoAdapter.LinkAgeChildLineren() {
            @Override
            public void linkageChild(String cid) {
                homeshow(cid);
                classifyrela.setVisibility(View.GONE);
                shop_linear.setVisibility(View.VISIBLE);
                home_scroll.setVisibility(View.GONE);
                search_xrecy.setVisibility(View.GONE);
            }
        });
    }
    public void getBackData(boolean b) {//返回监听
        if(b){
            home_text.setVisibility(View.GONE);
            home_search.setVisibility(View.VISIBLE);
            search_xrecy.setVisibility(View.GONE);
            home_edit.setVisibility(View.GONE);
            home_scroll.setVisibility(View.VISIBLE);
            search_none.setVisibility(View.GONE);
            home_childquality_rela.setVisibility(View.GONE);
            home_childhot_rela.setVisibility(View.GONE);
            home_childmagic_rela.setVisibility(View.GONE);
            shop_linear.setVisibility(View.GONE);
        }
    }
    private void inifBanner() {//轮播
        home_viewpager.setPageMargin(10);
        home_viewpager.setOffscreenPageLimit(4);
        home_viewpager.setPageTransformer(true, new PagerTransformer());
        i = home_viewpager.getCurrentItem();
        handler.sendEmptyMessageDelayed(i,2000);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter!=null) {
            presenter.seDestroy();
        }
        if (home_edit!=null){
            home_edit=null;
        }
    }
}
