package com.entity.vo;

import com.entity.ToudiEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 预约面试
 * 手机端接口返回实体辅助类
 * （主要作用去除一些不必要的字段）
 */
@TableName("toudi")
public class ToudiVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */

    @TableField(value = "id")
    private Integer id;


    /**
     * 就业信息
     */

    @TableField(value = "jianli_id")
    private Integer jianliId;


    /**
     * 招聘
     */

    @TableField(value = "zhaopin_id")
    private Integer zhaopinId;


    /**
     * 预约时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "toudi_time")
    private Date toudiTime;


    /**
     * 审核结果
     */

    @TableField(value = "toudi_yesno_types")
    private Integer toudiYesnoTypes;


    /**
     * 审核原因
     */

    @TableField(value = "toudi_yesno_text")
    private String toudiYesnoText;


    /**
     * 添加时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "insert_time")
    private Date insertTime;


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
	 * 设置：就业信息
	 */
    public Integer getJianliId() {
        return jianliId;
    }


    /**
	 * 获取：就业信息
	 */

    public void setJianliId(Integer jianliId) {
        this.jianliId = jianliId;
    }
    /**
	 * 设置：招聘
	 */
    public Integer getZhaopinId() {
        return zhaopinId;
    }


    /**
	 * 获取：招聘
	 */

    public void setZhaopinId(Integer zhaopinId) {
        this.zhaopinId = zhaopinId;
    }
    /**
	 * 设置：预约时间
	 */
    public Date getToudiTime() {
        return toudiTime;
    }


    /**
	 * 获取：预约时间
	 */

    public void setToudiTime(Date toudiTime) {
        this.toudiTime = toudiTime;
    }
    /**
	 * 设置：审核结果
	 */
    public Integer getToudiYesnoTypes() {
        return toudiYesnoTypes;
    }


    /**
	 * 获取：审核结果
	 */

    public void setToudiYesnoTypes(Integer toudiYesnoTypes) {
        this.toudiYesnoTypes = toudiYesnoTypes;
    }
    /**
	 * 设置：审核原因
	 */
    public String getToudiYesnoText() {
        return toudiYesnoText;
    }


    /**
	 * 获取：审核原因
	 */

    public void setToudiYesnoText(String toudiYesnoText) {
        this.toudiYesnoText = toudiYesnoText;
    }
    /**
	 * 设置：添加时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 获取：添加时间
	 */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
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
