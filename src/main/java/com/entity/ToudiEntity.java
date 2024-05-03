package com.entity;

import com.annotation.ColumnInfo;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;
import java.io.Serializable;
import java.util.*;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.utils.DateUtil;


/**
 * 预约面试
 *
 * @author 
 * @email
 */
@TableName("toudi")
public class ToudiEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;


	public ToudiEntity() {

	}

	public ToudiEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @ColumnInfo(comment="主键",type="int(11)")
    @TableField(value = "id")

    private Integer id;


    /**
     * 就业信息
     */
    @ColumnInfo(comment="就业信息",type="int(11)")
    @TableField(value = "jianli_id")

    private Integer jianliId;


    /**
     * 招聘
     */
    @ColumnInfo(comment="招聘",type="int(11)")
    @TableField(value = "zhaopin_id")

    private Integer zhaopinId;


    /**
     * 预约时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @ColumnInfo(comment="预约时间",type="timestamp")
    @TableField(value = "toudi_time")

    private Date toudiTime;


    /**
     * 审核结果
     */
    @ColumnInfo(comment="审核结果",type="int(11)")
    @TableField(value = "toudi_yesno_types")

    private Integer toudiYesnoTypes;


    /**
     * 审核原因
     */
    @ColumnInfo(comment="审核原因",type="text")
    @TableField(value = "toudi_yesno_text")

    private String toudiYesnoText;


    /**
     * 添加时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @ColumnInfo(comment="添加时间",type="timestamp")
    @TableField(value = "insert_time",fill = FieldFill.INSERT)

    private Date insertTime;


    /**
     * 创建时间  listShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @ColumnInfo(comment="创建时间",type="timestamp")
    @TableField(value = "create_time",fill = FieldFill.INSERT)

    private Date createTime;


    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
    }
    /**
	 * 设置：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 获取：就业信息
	 */
    public Integer getJianliId() {
        return jianliId;
    }
    /**
	 * 设置：就业信息
	 */

    public void setJianliId(Integer jianliId) {
        this.jianliId = jianliId;
    }
    /**
	 * 获取：招聘
	 */
    public Integer getZhaopinId() {
        return zhaopinId;
    }
    /**
	 * 设置：招聘
	 */

    public void setZhaopinId(Integer zhaopinId) {
        this.zhaopinId = zhaopinId;
    }
    /**
	 * 获取：预约时间
	 */
    public Date getToudiTime() {
        return toudiTime;
    }
    /**
	 * 设置：预约时间
	 */

    public void setToudiTime(Date toudiTime) {
        this.toudiTime = toudiTime;
    }
    /**
	 * 获取：审核结果
	 */
    public Integer getToudiYesnoTypes() {
        return toudiYesnoTypes;
    }
    /**
	 * 设置：审核结果
	 */

    public void setToudiYesnoTypes(Integer toudiYesnoTypes) {
        this.toudiYesnoTypes = toudiYesnoTypes;
    }
    /**
	 * 获取：审核原因
	 */
    public String getToudiYesnoText() {
        return toudiYesnoText;
    }
    /**
	 * 设置：审核原因
	 */

    public void setToudiYesnoText(String toudiYesnoText) {
        this.toudiYesnoText = toudiYesnoText;
    }
    /**
	 * 获取：添加时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }
    /**
	 * 设置：添加时间
	 */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 获取：创建时间  listShow
	 */
    public Date getCreateTime() {
        return createTime;
    }
    /**
	 * 设置：创建时间  listShow
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Toudi{" +
            ", id=" + id +
            ", jianliId=" + jianliId +
            ", zhaopinId=" + zhaopinId +
            ", toudiTime=" + DateUtil.convertString(toudiTime,"yyyy-MM-dd") +
            ", toudiYesnoTypes=" + toudiYesnoTypes +
            ", toudiYesnoText=" + toudiYesnoText +
            ", insertTime=" + DateUtil.convertString(insertTime,"yyyy-MM-dd") +
            ", createTime=" + DateUtil.convertString(createTime,"yyyy-MM-dd") +
        "}";
    }
}
