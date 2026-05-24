package org.goodbye.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.goodbye.entity.DailySummary;

@Mapper
public interface DailySummaryMapper extends BaseMapper<DailySummary> {
}