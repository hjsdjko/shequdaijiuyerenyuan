package com.entity.model;

import com.entity.GongzuotuisongEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;


/**
 * 工作推送信息
 * 接收传参的实体类
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了）
 * 取自ModelAndView 的model名称
 */
public class GongzuotuisongModel implements Serializable {
    private static final long serialVersionUID = 1L;




    /**
     * 主键
     */
    private Integer id;


    /**
     * 工作人员
     */
    private Integer gongzuorenyuanId;


    /**
     * 用户
     */
    private Integer yonghuId;


    /**
     * 工作名称
     */
    private String gongzuotuisongName;


    /**
     * 工作类型
     */
    private Integer gongzuotuisongTypes;


    /**
     * 推送时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date insertTime;


    /**
     * 工作详情
     */
    private String gongzuotuisongContent;


    /**
     * 假删
     */
    private Integer gongzuotuisongDelete;


    /**
     * 创建时间 show3 listShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
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
	 * 获取：创建时间 show3 listShow
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 设置：创建时间 show3 listShow
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    }
