package com.xiaoshu.enumeration;

/**
 * 消息队列名称
 */
public interface EnumsMQName {


	String DEAD_JSSDK_INVALID = "dead-letter-queue-JSSDK-Invalid";

    String DEAD_ORDER_CHECK = "dead-letter-queue-orderCheck";

    String DEAD_LETTER = "dead-letter-queue";

    String COMMON_JSSDK_INVALID = "common-queue-JSSDK-Invalid";

    String COMMON_QUEUE = "common-queue";
}
