package com.active4j.hr.topic.until;

/**
 * 抛出异常
 *
 * @author weiZiHao
 * @date 2021/1/21
 */
public class OaTopicException extends RuntimeException {

    private static final long serialVersionUID = -7584694378098185068L;

    public OaTopicException() {
        super();
    }

    public OaTopicException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public OaTopicException(String message, Throwable cause) {
        super(message, cause);
    }

    public OaTopicException(String message) {
        super(message);
    }

    public OaTopicException(Throwable cause) {
        super(cause);
    }

}
