package org.goodbye.config;

public class SecurityContextHolder {
    private static final ThreadLocal<Long> ADMIN_ID_HOLDER = new ThreadLocal<>();

    // 设置当前登录管理员ID（在JWT拦截器中调用）
    public static void setCurrentAdminId(Long adminId) {
        ADMIN_ID_HOLDER.set(adminId);
    }

    // 获取当前登录管理员ID（在填充器中调用）
    public static Long getCurrentAdminId() {
        return ADMIN_ID_HOLDER.get();
    }

    // 清除ThreadLocal（防止内存泄漏）
    public static void clear() {
        ADMIN_ID_HOLDER.remove();
    }
}