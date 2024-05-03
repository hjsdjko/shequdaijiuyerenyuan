package com.entity.vo;

import com.entity.GongzuotuisongEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 工作推送信息
 * 手机端接口返回实体辅助类
 * （主要作用去除一些不必要的字段）
 */
@TableName("gongzuotuisong")
public class GongzuotuisongVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */

    @TableField(value = "id")
    private Integer id;


    /**
     * 工作人员
     */

    @TableField(value = "gongzuorenyuan_id")
    private Integer gongzuorenyuanId;


    /**
     * 用户
     */

    @TableField(value = "yonghu_id")
    private Integer yonghuId;


    /**
     * 工作名称
     */

    @TableField(value = "gongzuotuisong_name")
    private String gongzuotuisongName;


    /**
     * 工作类型
     */

    @TableField(value = "gongzuotuisong_types")
    private Integer gongzuotuisongTypes;


    /**
     * 推送时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "insert_time")
    private Date insertTime;


    /**
     * 工作详情
     */

    @TableField(value = "gongzuotuisong_content")
    private String gongzuotuisongContent;


    /**
     * 假删
     */

    @TableField(value = "gongzuotuisong_delete")
    private Integer gongzuotuisongDelete;


    /**
     * 创建时间 show3 listShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "create_time")
    private Date createTime;


    /**
	 * 设置：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 获取：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 设置：工作人员
	 */
    public Integer getGongzuorenyuanId() {
        return gongzuorenyuanId;
    }


    /**
	 * 获取：工作人员
	 */

    public void setGongzuorenyuanId(Integer gongzuorenyuanId) {
        this.gongzuorenyuanId = gongzuorenyuanId;
    }
    /**
	 * 设置：用户
	 */
    public Integer getYonghuId() {
        return yonghuId;
    }


    /**
	 * 获取：用户
	 */

    public void setYonghuId(Integer yonghuId) {
        this.yonghuId = yonghuId;
    }
    /**
	 * 设置：工作名称
	 */
    public String getGongzuotuisongName() {
        return gongzuotuisongName;
    }


    /**
	 * 获取：工作名称
	 */

    public void setGongzuotuisongName(String gongzuotuisongName) {
        this.gongzuotuisongName = gongzuotuisongName;
    }
    /**
	 * 设置：工作类型
	 */
    public Integer getGongzuotuisongTypes() {
        return gongzuotuisongTypes;
    }


    /**
	 * 获取：工作类型
	 */

    public void setGongzuotuisongTypes(Integer gongzuotuisongTypes) {
        this.gongzuotuisongTypes = gongzuotuisongTypes;
    }
    /**
	 * 设置：推送时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 获取：推送时间
	 */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 设置：工作详情
	 */
    public String getGongzuotuisongContent() {
        return gongzuotuisongContent;
    }


    /**
	 * 获取：工作详情
	 */

    public void setGongzuotuisongContent(String gongzuotuisongContent) {
        this.gongzuotuisongContent = gongzuotuisongContent;
    }
    /**
	 * 设置：假删
	 */
    public Integer getGongzuotuisongDelete() {
        return gongzuotuisongDelete;
    }


    /**
	 * 获取：假删
	 */

    public void setGongzuotuisongDelete(Integer gongzuotuisongDelete) {
        this.gongzuotuisongDelete = gongzuotuisongDelete;
    }
    /**
	 * 设置：创建时间 show3 listShow
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 获取：创建时间 show3 listShow
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
