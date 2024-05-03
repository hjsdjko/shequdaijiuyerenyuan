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
 * 工作推送信息
 *
 * @author 
 * @email
 */
@TableName("gongzuotuisong")
public class GongzuotuisongEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;


	public GongzuotuisongEntity() {

	}

	public GongzuotuisongEntity(T t) {
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
     * 工作人员
     */
    @ColumnInfo(comment="工作人员",type="int(11)")
    @TableField(value = "gongzuorenyuan_id")

    private Integer gongzuorenyuanId;


    /**
     * 用户
     */
    @ColumnInfo(comment="用户",type="int(11)")
    @TableField(value = "yonghu_id")

    private Integer yonghuId;


    /**
     * 工作名称
     */
    @ColumnInfo(comment="工作名称",type="varchar(200)")
    @TableField(value = "gongzuotuisong_name")

    private String gongzuotuisongName;


    /**
     * 工作类型
     */
    @ColumnInfo(comment="工作类型",type="int(11)")
    @TableField(value = "gongzuotuisong_types")

    private Integer gongzuotuisongTypes;


    /**
     * 推送时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @ColumnInfo(comment="推送时间",type="timestamp")
    @TableField(value = "insert_time",fill = FieldFill.INSERT)

    private Date insertTime;


    /**
     * 工作详情
     */
    @ColumnInfo(comment="工作详情",type="text")
    @TableField(value = "gongzuotuisong_content")

    private String gongzuotuisongContent;


    /**
     * 假删
     */
    @ColumnInfo(comment="假删",type="int(11)")
    @TableField(value = "gongzuotuisong_delete")

    private Integer gongzuotuisongDelete;


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
	 * 获取：工作人员
	 */
    public Integer getGongzuorenyuanId() {
        return gongzuorenyuanId;
    }
    /**
	 * 设置：工作人员
	 */

    public void setGongzuorenyuanId(Integer gongzuorenyuanId) {
        this.gongzuorenyuanId = gongzuorenyuanId;
    }
    /**
	 * 获取：用户
	 */
    public Integer getYonghuId() {
        return yonghuId;
    }
    /**
	 * 设置：用户
	 */

    public void setYonghuId(Integer yonghuId) {
        this.yonghuId = yonghuId;
    }
    /**
	 * 获取：工作名称
	 */
    public String getGongzuotuisongName() {
        return gongzuotuisongName;
    }
    /**
	 * 设置：工作名称
	 */

    public void setGongzuotuisongName(String gongzuotuisongName) {
        this.gongzuotuisongName = gongzuotuisongName;
    }
    /**
	 * 获取：工作类型
	 */
    public Integer getGongzuotuisongTypes() {
        return gongzuotuisongTypes;
    }
    /**
	 * 设置：工作类型
	 */

    public void setGongzuotuisongTypes(Integer gongzuotuisongTypes) {
        this.gongzuotuisongTypes = gongzuotuisongTypes;
    }
    /**
	 * 获取：推送时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }
    /**
	 * 设置：推送时间
	 */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 获取：工作详情
	 */
    public String getGongzuotuisongContent() {
        return gongzuotuisongContent;
    }
    /**
	 * 设置：工作详情
	 */

    public void setGongzuotuisongContent(String gongzuotuisongContent) {
        this.gongzuotuisongContent = gongzuotuisongContent;
    }
    /**
	 * 获取：假删
	 */
    public Integer getGongzuotuisongDelete() {
        return gongzuotuisongDelete;
    }
    /**
	 * 设置：假删
	 */

    public void setGongzuotuisongDelete(Integer gongzuotuisongDelete) {
        this.gongzuotuisongDelete = gongzuotuisongDelete;
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
        return "Gongzuotuisong{" +
            ", id=" + id +
            ", gongzuorenyuanId=" + gongzuorenyuanId +
            ", yonghuId=" + yonghuId +
            ", gongzuotuisongName=" + gongzuotuisongName +
            ", gongzuotuisongTypes=" + gongzuotuisongTypes +
            ", insertTime=" + DateUtil.convertString(insertTime,"yyyy-MM-dd") +
            ", gongzuotuisongContent=" + gongzuotuisongContent +
            ", gongzuotuisongDelete=" + gongzuotuisongDelete +
            ", createTime=" + DateUtil.convertString(createTime,"yyyy-MM-dd") +
        "}";
    }
}
