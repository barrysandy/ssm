package com.xiaoshu.tools.sendMsg;

/**
 * 短信模板类型
 */
public interface EnumsTemplateType {

    /**
     * 短信模板ID：0
     * 单一商品购买（未满足开团人数购买短信）
     */
    String SINGLE_BUY  = "SINGLE_BUY";

    /**
     * 短信模板ID：1
     * 单一商品购买（满足开团人数，群发开团短信）
     */
    String SINGLE_BUY_GROUP_SUC_MASS = "SINGLE_BUY_GROUP_SUC_MASS";

    /**
     * 短信模板ID：2
     * 单一商品购买（满足开团人数，合并的购买短信）
     */
    String SINGLE_BUY_GROUP_SUC_TOBUY = "SINGLE_BUY_GROUP_SUC_TOBUY";

    /**
     * 短信模板ID：3
     * 单一商品购买（未满足开团人数，时间到达，群发失败短信）
     */
    String SINGLE_BUY_GROUP_FIAL_MASS = "SINGLE_BUY_GROUP_FIAL_MASS";




    /**
     * 短信模板ID：4
     * 组团商品购买（当前团未满短信）
     */
    String GROUP_BUY = "GROUP_BUY";


    /**
     * 短信模板ID：5
     * 组团商品购买（当前团满群发组团成功短信）
     */
    String GROUP_BUY_SUC_MASS = "GROUP_BUY_SUC_MASS";


    /**
     * 短信模板ID：6
     * 组团商品购买（当前团满短信时，合并购买短信）
     */
    String GROUP_BUY_SUC_TOBUY = "GROUP_BUY_SUC_TOBUY";


    /**
     * 短信模板ID：7
     * 组团商品购买（未满足开团人数，时间到达，群发失败短信）
     */
    String GROUP_BUY_FIAL_MASS = "GROUP_BUY_FIAL_MASS";

    /**
     * 短信模板ID：8
     * REFUND 退款
     */
    String REFUND = "REFUND";

    /**
     * 短信模板ID：9
     * WINNING 中奖短信
     */
    String WINNING = "WINNING";

    /**
     * 短信模板ID：10
     * REFUND 退款失败
     */
    String REFUND_FAIL = "REFUND_FAIL";

    /**
     * 短信模板ID：11
     * MEETING_MSG_ALL 发送会议通知短信
     */
    String MEETING_MSG_ALL = "MEETING_MSG_ALL";
}
