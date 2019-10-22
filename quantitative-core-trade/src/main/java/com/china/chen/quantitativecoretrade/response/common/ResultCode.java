package com.china.chen.quantitativecoretrade.response.common;

/**
 * 公共返回值枚举
 *
 * @author LY
 */
public enum ResultCode {
    /** */
    SUCCESS(200,"成功"),
    FAIL(300,"失败"),
    BAD_REQUEST(400,"请求参数错误"),
    SERVER_ERROR(500,"服务器错误"),

	;

	private int code;
    private String msg ;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg ;
    }

    public int getCode() {
        return code;
    }


	public String getMsg() {
		return msg;
	}

	public static boolean isResultCode(Integer code) {
        if (code == null) {
            return false;
        }
        for (ResultCode codeEnum : ResultCode.values()) {
            if (codeEnum.getCode() == code) {
                return true;
            }
        }
        return false;
    }


	public static String getValueByCode(Integer code) {
		if (code == null) {
			return "";
		} else {
			ResultCode[] var1 = values();
			int var2 = var1.length;

			for(int var3 = 0; var3 < var2; ++var3) {
				ResultCode e = var1[var3];
				if (e.getCode() == code.intValue()) {
					return e.getMsg();
				}
			}

			return "";
		}
	}


	public static String getValue(Integer code) {
		for (ResultCode codeEnum : ResultCode.values()) {
			if (codeEnum.getCode() == code) {
				return codeEnum.msg;
			}
		}
		return null;
	}
}
