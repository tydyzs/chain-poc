package org.git.common.exception;

import org.git.core.log.exception.ServiceException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * 异常处理工具类
 *
 * @author chenchuan
 */
public class AssertUtil extends Assert {

	/**
	 * 如果object不为null，则抛出异常ServiceException
	 *
	 * @param object
	 * @param code   错误码
	 * @param msg    错误消息
	 */
	public static void isNull(@Nullable Object object, int code, String msg) {
		if (object != null) {
			throw new ServiceException(new BizResultCode(code, msg));
		}
	}

	/**
	 * 抛出异常ServiceException
	 *
	 * @param code 错误码
	 * @param msg  错误消息
	 */
	public static void throwServiceException(int code, String msg) {
		throw new ServiceException(new BizResultCode(code, msg));
	}
}
