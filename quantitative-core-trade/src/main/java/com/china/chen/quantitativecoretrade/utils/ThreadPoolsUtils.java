package com.china.chen.quantitativecoretrade.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolsUtils {

	private static final ThreadFactory FACTORY = new ThreadFactoryBuilder().setNameFormat("__strategy_Pool__%d").build();
	/**权限初始化的线程池信息*/
	public static final ExecutorService STRATEGY_POOL = new ThreadPoolExecutor(1,4,60,TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(20),FACTORY,new ThreadPoolExecutor.AbortPolicy()) ;


}
