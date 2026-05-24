package org.goodbye.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;

@Configuration
public class MyBatisPlusConfig {

    // 乐观锁插件（处理version字段）
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    // 公共字段自动填充器
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            // 插入时填充
            @Override
            public void insertFill(MetaObject metaObject) {
                // 填充创建时间和修改时间为当前时间
                this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
                this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
                
                // 填充默认值：isDeleted=0，isEnabled=1，version=1
                this.strictInsertFill(metaObject, "isDeleted", Boolean.class, false);
                this.strictInsertFill(metaObject, "isEnabled", Boolean.class, true);
                this.strictInsertFill(metaObject, "version", Integer.class, 1);

                // 从ThreadLocal中获取当前登录管理员ID（创建人）
                Long currentAdminId = SecurityContextHolder.getCurrentAdminId();
                if (currentAdminId != null) {
                    this.strictInsertFill(metaObject, "createBy", Long.class, currentAdminId);
                    this.strictInsertFill(metaObject, "updateBy", Long.class, currentAdminId);
                }
            }

            // 更新时填充
            @Override
            public void updateFill(MetaObject metaObject) {
                // 填充修改时间为当前时间
                this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
                
                // 从ThreadLocal中获取当前登录管理员ID（修改人）
                Long currentAdminId = SecurityContextHolder.getCurrentAdminId();
                if (currentAdminId != null) {
                    this.strictUpdateFill(metaObject, "updateBy", Long.class, currentAdminId);
                }
            }
        };
    }
}