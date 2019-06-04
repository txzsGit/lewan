package com.funplay.pojo;

import java.io.Serializable;
import java.util.Date;

public class PayLog implements Serializable {
    private String outtradeno;//支付订单号

    private Date createtime;//创建时间

    private Date paytime;//支付完成时间

    private Long totalfee;//支付金额（分）

    private String userid;//用户id

    private String transactionid;//交易订单号

    private String tradestate;//交易状态

    private String orderlist;//订单编号列表

    private String paytype;//支付类型

    public String getOuttradeno() {
        return outtradeno;
    }

    public void setOuttradeno(String outtradeno) {
        this.outtradeno = outtradeno;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getPaytime() {
        return paytime;
    }

    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }

    public Long getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(Long totalfee) {
        this.totalfee = totalfee;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getTradestate() {
        return tradestate;
    }

    public void setTradestate(String tradestate) {
        this.tradestate = tradestate;
    }

    public String getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(String orderlist) {
        this.orderlist = orderlist;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }
}
