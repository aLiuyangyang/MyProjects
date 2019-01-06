package com.example.dell.myprojects.util;

public class Apis {

    public static String LOGIN_URL="user/v1/login";
    public static String REGISTER_URL="user/v1/register";
    public static String CIRCLE_URL="circle/v1/findCircleList?page=%d&count=%d";
    public static String Banner_URL="commodity/v1/bannerShow";
    public static String HOT_URL="commodity/v1/commodityList";
    public static String HOT_CHILD="commodity/v1/findCommodityListByLabel?labelId=%d&page=%d&count=%d";
    public static String SEARCH_URL="commodity/v1/findCommodityByKeyword?keyword=%s&page=%d&count=%d";
    //商品详情
    public static String DETALIS_URL="commodity/v1/findCommodityDetailsById?commodityId=%d";
    //一级列表
    public static String LINKAGE_URL ="commodity/v1/findFirstCategory";
    //二级列表
    public static String ELINKAGEERJI_URL="commodity/v1/findSecondCategory?firstCategoryId=%s";
    //二级列表商品
    public static String ERJISHOP_URL="commodity/v1/findCommodityByCategory?categoryId=%s&page=%d&count=%d";
    //添加点赞
    public static String ADDCRICLE_URI="circle/verify/v1/addCircleGreat?circleId=%d";
    //删除赞
    public static String HIDECRICLE_URI="circle/verify/v1/cancelCircleGreat?circleId=%d";
}
