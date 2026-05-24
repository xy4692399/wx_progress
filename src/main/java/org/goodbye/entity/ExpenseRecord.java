package org.goodbye.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("expense_record")
public class ExpenseRecord {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String userId;

    private LocalDate recordDate;

    private String category;

    private BigDecimal amount;

    private String ticketUrl;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}