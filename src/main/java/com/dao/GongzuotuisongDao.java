package com.dao;

import com.entity.GongzuotuisongEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.GongzuotuisongView;

/**
 * 工作推送信息 Dao 接口
 *
 * @author 
 */
public interface GongzuotuisongDao extends BaseMapper<GongzuotuisongEntity> {

   List<GongzuotuisongView> selectListView(Pagination page,@Param("params")Map<String,Object> params);

}
