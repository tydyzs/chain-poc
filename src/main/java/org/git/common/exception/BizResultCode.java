
package org.git.common.exception;

import org.git.core.tool.api.IResultCode;

public class BizResultCode implements IResultCode {

	int code;//错误码
	String message;//错误消息

	public BizResultCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public int getCode() {
		return this.code;
	}
}
