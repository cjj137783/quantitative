package com.china.chen.quantitativecoretrade.response.common;

import org.slf4j.helpers.MessageFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author LY
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageException extends RuntimeException {

    private Integer code;
    private String message;
    private String debugMessage;
    private Boolean isI18n = true;
    private Object[] params;

    public MessageException(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public MessageException(Integer code, String message, String debugMessage) {
        super();
        this.code = code;
        this.message = message;
        this.debugMessage = debugMessage;
    }

    public MessageException(Integer code, String message, Object[] params) {
        super();
        this.code = code;
        this.message = message;
        this.params = params;
    }

    public MessageException(Integer code, String message, String debugMessage, Object[] params) {
        super();
        this.code = code;
        this.message = message;
        this.debugMessage = debugMessage;
        this.params = params;
    }

    public static MessageException badRequest(String paramType) {
        return MessageException.builder().code(400).isI18n(false).message(paramType + " invalid").build();
    }

    public static MessageException forbidden() {
        return MessageException.builder().code(403).isI18n(false).message("access barred").build();
    }

    public MessageException setDebugMessage(String debugMessage, Object... params) {
        this.debugMessage = MessageFormatter.arrayFormat(debugMessage, params).getMessage();
        return this;
    }
}
