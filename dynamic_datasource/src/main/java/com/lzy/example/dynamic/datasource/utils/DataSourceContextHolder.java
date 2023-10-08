package com.lzy.example.dynamic.datasource.utils;

/**
 * <p>
 * 数据源上下文
 * </p>
 *
 * @author lzy
 * @since 2023/9/14 14:20
 */
public class DataSourceContextHolder {
    /**
     * 提供线程局部变量，存放数据源，支持独立初始化副本功能。
     */
    private static final ThreadLocal<String> DATASOURCE_HOLDER = new ThreadLocal<>();

    /**
     * 设置当前线程的数据源
     * @param datasourceName 数据源名称
     */
    public static void setDataSource(String datasourceName) {
        DATASOURCE_HOLDER.set(datasourceName);
    }

    /**
     * 获取当前线程的数据源
     * @return {@link String}
     */
    public static String getDataSource() {
        return DATASOURCE_HOLDER.get();
    }

    /**
     * 删除当前线程的数据源
     */
    public static void removeDataSource() {
        DATASOURCE_HOLDER.remove();
    }
}
