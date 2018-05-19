package com.xiaoshu.enumeration;

/**
 * 消息队列名称
 */
public interface EnumsMQName {

//	String DEAD_JSSDK_INVALID = "dead-letter-queue-JSSDK-Invalid";
//
//    String DEAD_ORDER_CHECK = "dead-letter-queue-orderCheck";
//
//    String DEAD_LETTER = "dead-letter-queue";
//
//    String COMMON_JSSDK_INVALID = "common-queue-JSSDK-Invalid";
//
//    String COMMON_QUEUE = "common-queue";

    /* 测试
    String DEAD_JSSDK_INVALID = "TEST_ssm_queue-dead-jssdkInvalid";

    String DEAD_TEN_SECONDS = "TEST_ssm_queue-dead-TenSeconds";

    String DEAD_LETTER = "TEST_ssm_queue-dead";

    String COMMON_JSSDK_INVALID = "TEST_ssm_queue-common-jssdkInvalid";

    String COMMON_QUEUE = "TEST_ssm_queue-common";
    */

    /* 正式 */
    String DEAD_JSSDK_INVALID = "ssm_queue-dead-jssdkInvalid";

    String DEAD_TEN_SECONDS = "ssm_queue-dead-TenSeconds";

    String DEAD_LETTER = "ssm_queue-dead";

    String COMMON_JSSDK_INVALID = "ssm_queue-common-jssdkInvalid";

    String COMMON_QUEUE = "ssm_queue-common";


}
