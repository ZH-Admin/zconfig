package com.ccsu.server.context;

/**
 * @author hangs.zhang
 * @date 2018/7/30
 * *********************
 * function: 快照上下文
 */
public class SnapContext {

    private static ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    public static void set(Object snap) {
        threadLocal.set(snap);
    }

    public static Object get() {
        Object result = threadLocal.get();
        threadLocal.remove();
        return result;
    }

}
