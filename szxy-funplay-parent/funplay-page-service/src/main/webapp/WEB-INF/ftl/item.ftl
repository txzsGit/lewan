<!DOCTYPE html>
<html>

<head>
    <meta  charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <title>产品详情页</title>
    <link rel="stylesheet" type="text/css" href="css/webbase.css" />
    <link rel="stylesheet" type="text/css" href="css/pages-item.css" />
    <link rel="stylesheet" type="text/css" href="css/pages-zoom.css" />
    <link rel="stylesheet" type="text/css" href="css/widget-cartPanelView.css" />
    <script type="text/javascript" src="plugins/angularjs/angular.min.js">  </script>
    <script type="text/javascript" src="js/base.js">  </script>
    <script type="text/javascript" src="js/controller/itemController.js">  </script>
    <script>
        var skuList=[
            <#list itemList as item>
            {
                "id":${item.id?c},
                "title":"${item.title!''}",
                "price":${item.price?c},
                "spec":${item.spec}
            },
            </#list>
        ]
    </script>
</head>

<body ng-app="funplay" ng-controller="itemController" ng-init="num=1;loadSku()">

<!--页面顶部 开始-->
<div id="nav-bottom">
    <!--顶部-->
    <div class="nav-top">
        <div class="top">
            <div class="py-container">
                <div class="shortcut">
                    <ul class="fl">
                        <li class="f-item">乐玩欢迎您！</li>
                    </ul>
                    <ul class="fr">
                        <li class="f-item">我的订单</li>
                        <li class="f-item space"></li>
                        <li class="f-item"><a href="home.html" target="_blank">我的乐玩</a></li>
                        <li class="f-item space"></li>
                        <li class="f-item">乐玩会员</li>
                        <li class="f-item space"></li>
                        <li class="f-item">企业采购</li>
                        <li class="f-item space"></li>
                        <li class="f-item">关注乐玩</li>
                        <li class="f-item space"></li>
                        <li class="f-item" id="service">
                            <span>客户服务</span>
                            <ul class="service">
                                <li><a href="cooperation.html" target="_blank">合作招商</a></li>
                                <li><a href="shoplogin.html" target="_blank">商家后台</a></li>
                                <li><a href="cooperation.html" target="_blank">合作招商</a></li>
                                <li><a href="#">商家后台</a></li>
                            </ul>
                        </li>
                        <li class="f-item space"></li>
                        <li class="f-item">网站导航</li>
                    </ul>
                </div>
            </div>
        </div>

        <!--头部-->
        <div class="header">
            <div class="py-container">
                <div class="yui3-g Logo">
                    <div class="yui3-u Left logoArea">
                        <h1 style="color:rebeccapurple;">乐玩3C</h1>
                        <h3>商品详情页</h3>
                    </div>
                    <div class="yui3-u Center searchArea">
                        <div class="search">
                            <form action="" class="sui-form form-inline">
                                <!--searchAutoComplete-->
                                <div class="input-append">
                                    <input type="text" id="autocomplete" type="text" class="input-error input-xxlarge" ng-model="keywords" />
                                    <button class="sui-btn btn-xlarge btn-danger" type="button" ng-click="search()">搜索</button>
                                </div>
                            </form>
                        </div>
                        <div class="hotwords">
                            <ul>
                                <li class="f-item">乐玩首发</li>
                                <li class="f-item">华为</li>
                                <li class="f-item">IPhone</li>
                                <li class="f-item">ThinkPad</li>
                                <li class="f-item">小米</li>
                                <li class="f-item">OPPO</li>
                                <li class="f-item">VIVO</li>

                            </ul>
                        </div>
                    </div>
                    <div class="yui3-u Right shopArea">
                        <div class="fr shopcar">
                            <div class="show-shopcar" id="shopcar">
                                <span class="car"></span>
                                <a class="sui-btn btn-default btn-xlarge" href="http://localhost:9107" target="_blank">
                                    <span>我的购物车</span>
                                    <i class="shopnum">0</i>
                                </a>
                                <div class="clearfix shopcarlist" id="shopcarlist" style="display:none">
                                    <p>"啊哦，你的购物车还没有商品哦！"</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="yui3-g NavList">
                    <div class="yui3-u Left all-sort">
                        <h4>全部商品分类</h4>
                    </div>
                    <div class="yui3-u Center navArea">
                        <ul class="nav">
                            <li class="f-item">手机通讯</li>
                            <li class="f-item">电脑办公</li>
                            <li class="f-item">智能数码</li>
                            <li class="f-item">手机营业厅</li>
                            <li class="f-item">二手3C</li>
                            <li class="f-item">乐玩定制</li>
                        </ul>
                    </div>
                    <div class="yui3-u Right"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--页面顶部 结束-->
<#assign  imageList=goodsDesc.itemimages?eval />
<#assign  customList=goodsDesc.customattributeitems?eval />
<#assign  specList=goodsDesc.specificationitems?eval/>
<div class="py-container">
    <div id="item">
        <div class="crumb-wrap">
            <ul class="sui-breadcrumb">
                <li><a href="#">${category1}</a></li>
                <li><a href="#">${category2}</a></li>
                <li><a href="#">${category3}</a></li>
            </ul>
        </div>
        <!--product-info-->
        <div class="product-info">
            <div class="fl preview-wrap">
                <!--放大镜效果-->
                <div class="zoom">
                    <!--默认第一个预览-->
                    <div id="preview" class="spec-preview">
                        <span class="jqzoom">
                      <#if (imageList?size>0)>
                          <img jqimg="${imageList[0].url}" src="${imageList[0].url}"
                               width="400px" height="400px" />
                      </#if>
                            </span>
                    </div>
                    <!--下方的缩略图-->
                    <div class="spec-scroll">
                        <!--左右按钮-->
                        <div class="items">
                            <ul>
                                <#list imageList as image>
                                <li><img src="${image.url}" bimg="${image.url}" onmousemove="preview(this)" /></li>
                                </#list>
                            </ul>
                        </div>
                        <a class="next">&gt;</a>
                    </div>
                </div>
            </div>
            <div class="fr itemInfo-wrap">
                <div class="sku-name">
                    <h4>{{sku.title}}</h4>
                </div>
                <div class="news"><span>${goods.caption}</span></div>
                <div class="summary">
                    <div class="summary-wrap">
                        <div class="fl title">
                            <i>价　　格</i>
                        </div>
                        <div class="fl price">
                            <i>¥</i>
                            <em>{{sku.price}}</em>
                            <span>降价通知</span>
                        </div>
                        <div class="fr remark">
                            <i>累计评价</i><em>66666</em>
                        </div>
                    </div>
                    <div class="summary-wrap">
                        <div class="fl title">
                            <i>促　　销</i>
                        </div>
                        <div class="fl fix-width">
                            <i class="red-bg">加价购</i>
                            <em class="t-gray">满999.00另加20.00元，或满1999.00另加30.00元，或满2999.00另加40.00元，即可在购物车换
                                购热销商品</em>
                        </div>
                    </div>
                </div>
                <div class="support">
                    <div class="summary-wrap">
                        <div class="fl title">
                            <i>支　　持</i>
                        </div>
                        <div class="fl fix-width">
                            <em class="t-gray">以旧换新，闲置手机回收  4G套餐超值抢  礼品购</em>
                        </div>
                    </div>
                    <div class="summary-wrap">
                        <div class="fl title">
                            <i>配 送 至</i>
                        </div>
                        <div class="fl fix-width">
                            <em class="t-gray">满999.00另加20.00元，或满1999.00另加30.00元，或满2999.00另加40.00元，即可在购物车换购热销商品</em>
                        </div>
                    </div>
                </div>
                <div class="clearfix choose">
                    <div id="specification" class="summary-wrap clearfix">
                        <#list specList as spec>
                        <dl>
                            <dt>
                                <div class="fl title">
                                    <i>${spec.attributename}</i>
                                </div>
                            </dt>
                            <#list spec.attributevalue as svl>
                            <dd><a href="javascript:;" class="{{isSelected('${spec.attributename}','${svl}')?'selected':''}}" ng-click="selectSpecification('${spec.attributename}','${svl}')">${svl}<span title="点击取消选择">&nbsp;</span> </a></dd>
                            </#list>
                        </dl>
                        </#list>
                    </div>

                    <div class="summary-wrap">
                        <div class="fl title">
                            <div class="control-group">
                                <div class="controls">
                                    <input autocomplete="off" type="text" value="{{num}}"  minnum="1" class="itxt" />
                                    <a href="javascript:void(0)" class="increment plus" ng-click="addNum(1)" >+</a>
                                    <a href="javascript:void(0)" class="increment mins" ng-click="addNum(-1)">-</a>
                                </div>
                            </div>
                        </div>
                        <div class="fl">
                            <ul class="btn-choose unstyled">
                                <li>
                                    <a target="_blank" class="sui-btn  btn-danger addshopcar" ng-click="addToCart()">加入购物车</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
            <div class="fr detail">
                <div class="tab-main intro">
                    <ul class="sui-nav nav-tabs tab-wraped">
                        <li class="active">
                            <a href="#one" data-toggle="tab">
                                <span>商品介绍</span>
                            </a>
                        </li>
                        <li>
                            <a href="#two" data-toggle="tab">
                                <span>规格与包装</span>
                            </a>
                        </li>
                        <li>
                            <a href="#three" data-toggle="tab">
                                <span>售后保障</span>
                            </a>
                        </li>
                        <li>
                            <a href="#four" data-toggle="tab">
                                <span>商品评价</span>
                            </a>
                        </li>
                        <li>
                            <a href="#five" data-toggle="tab">
                                <span>手机社区</span>
                            </a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                    <div class="tab-content tab-wraped">
                        <div id="one" class="tab-pane active">
                            <ul class="goods-intro unstyled">
                                <#list  customList as item>
                                    <#if item.value??>
                                <li>${item.text}:${item.value}</li>
                                    </#if>
                                </#list>
                            </ul>
                            <div class="intro-detail">
                                ${goodsDesc.introduction}
                            </div>
                        </div>
                        <div id="two" class="tab-pane">
                            <p>${goodsDesc.packagelist}</p>
                        </div>
                        <div id="three" class="tab-pane">
                            <p>${goodsDesc.saleservice}</p>
                        </div>
                        <div id="four" class="tab-pane">
                            <p>商品评价</p>
                        </div>
                        <div id="five" class="tab-pane">
                            <p>手机社区</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--like-->
        <div class="clearfix"></div>
    </div>
</div>
<!-- 底部栏位 -->




<#include "foot.ftl">


<!--页面底部  结束 -->
</body>

</html>