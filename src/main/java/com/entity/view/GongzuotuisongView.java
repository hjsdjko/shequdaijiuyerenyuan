package com.entity.view;

import org.apache.tools.ant.util.DateUtils;
import com.annotation.ColumnInfo;
import com.entity.GongzuotuisongEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import com.utils.DateUtil;

/**
* 工作推送信息
* 后端返回视图实体辅助类
* （通常后端关联的表或者自定义的字段需要返回使用）
*/
@TableName("gongzuotuisong")
public class GongzuotuisongView extends GongzuotuisongEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//当前表
	/**
	* 工作类型的值
	*/
	@ColumnInfo(comment="工作类型的字典表值",type="varchar(200)")
	private String gongzuotuisongValue;

	//级联表 社区工作人员
		/**
		* 社区工作人员名称
		*/

		@ColumnInfo(comment="社区工作人员名称",type="varchar(200)")
		private String gongzuorenyuanName;
		/**
		* 联系方式
		*/

		@ColumnInfo(comment="联系方式",type="varchar(200)")
		private String gongzuorenyuanPhone;
		/**
		* 邮箱
		*/

		@ColumnInfo(comment="邮箱",type="varchar(200)")
		private String gongzuorenyuanEmail;
		/**
		* 逻辑删除
		*/

		@ColumnInfo(comment="逻辑删除",type="int(11)")
		private Integer gongzuorenyuanDelete;
	//级联表 用户
		/**
		* 用户姓名
		*/

		@ColumnInfo(comment="用户姓名",type="varchar(200)")
		private String yonghuName;
		/**
		* 头像
		*/

		@ColumnInfo(comment="头像",type="varchar(255)")
		private String yonghuPhoto;
		/**
		* 用户手机号
		*/

		@ColumnInfo(comment="用户手机号",type="varchar(200)")
		private String yonghuPhone;
		/**
		* 用户身份证号
		*/

		@ColumnInfo(comment="用户身份证号",type="varchar(200)")
		private String yonghuIdNumber;
		/**
		* 邮箱
		*/

		@ColumnInfo(comment="邮箱",type="varchar(200)")
		private String yonghuEmail;
		/**
		* 就业状态
		*/
		@ColumnInfo(comment="就业状态",type="int(11)")
		private Integer jiuyeTypes;
			/**
			* 就业状态的值
			*/
			@ColumnInfo(comment="就业状态的字典表值",type="varchar(200)")
			private String jiuyeValue;
		/**
		* 假删
		*/

		@ColumnInfo(comment="假删",type="int(11)")
		private Integer yonghuDelete;

	//重复字段
			/**
			* 重复字段 的types
			*/
			@ColumnInfo(comment="重复字段 的types",type="Integer")
			private Integer sexTypes;
				@ColumnInfo(comment="重复字段 的值",type="varchar(200)")
				private String sexValue;


	public GongzuotuisongView() {

	}

	public GongzuotuisongView(GongzuotuisongEntity gongzuotuisongEntity) {
		try {
			BeanUtils.copyProperties(this, gongzuotuisongEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	//当前表的
	/**
	* 获取： 工作类型的值
	*/
	public String getGongzuotuisongValue() {
		return gongzuotuisongValue;
	}
	/**
	* 设置： 工作类型的值
	*/
	public void setGongzuotuisongValue(String gongzuotuisongValue) {
		this.gongzuotuisongValue = gongzuotuisongValue;
	}


	//级联表的get和set 社区工作人员

		/**
		* 获取： 社区工作人员名称
		*/
		public String getGongzuorenyuanName() {
			return gongzuorenyuanName;
		}
		/**
		* 设置： 社区工作人员名称
		*/
		public void setGongzuorenyuanName(String gongzuorenyuanName) {
			this.gongzuorenyuanName = gongzuorenyuanName;
		}

		/**
		* 获取： 联系方式
		*/
		public String getGongzuorenyuanPhone() {
			return gongzuorenyuanPhone;
		}
		/**
		* 设置： 联系方式
		*/
		public void setGongzuorenyuanPhone(String gongzuorenyuanPhone) {
			this.gongzuorenyuanPhone = gongzuorenyuanPhone;
		}

		/**
		* 获取： 邮箱
		*/
		public String getGongzuorenyuanEmail() {
			return gongzuorenyuanEmail;
		}
		/**
		* 设置： 邮箱
		*/
		public void setGongzuorenyuanEmail(String gongzuorenyuanEmail) {
			this.gongzuorenyuanEmail = gongzuorenyuanEmail;
		}

		/**
		* 获取： 逻辑删除
		*/
		public Integer getGongzuorenyuanDelete() {
			return gongzuorenyuanDelete;
		}
		/**
		* 设置： 逻辑删除
		*/
		public void setGongzuorenyuanDelete(Integer gongzuorenyuanDelete) {
			this.gongzuorenyuanDelete = gongzuorenyuanDelete;
		}
	//级联表的get和set 用户

		/**
		* 获取： 用户姓名
		*/
		public String getYonghuName() {
			return yonghuName;
		}
		/**
		* 设置： 用户姓名
		*/
		public void setYonghuName(String yonghuName) {
			this.yonghuName = yonghuName;
		}

		/**
		* 获取： 头像
		*/
		public String getYonghuPhoto() {
			return yonghuPhoto;
		}
		/**
		* 设置： 头像
		*/
		public void setYonghuPhoto(String yonghuPhoto) {
			this.yonghuPhoto = yonghuPhoto;
		}

		/**
		* 获取： 用户手机号
		*/
		public String getYonghuPhone() {
			return yonghuPhone;
		}
		/**
		* 设置： 用户手机号
		*/
		public void setYonghuPhone(String yonghuPhone) {
			this.yonghuPhone = yonghuPhone;
		}

		/**
		* 获取： 用户身份证号
		*/
		public String getYonghuIdNumber() {
			return yonghuIdNumber;
		}
		/**
		* 设置： 用户身份证号
		*/
		public void setYonghuIdNumber(String yonghuIdNumber) {
			this.yonghuIdNumber = yonghuIdNumber;
		}

		/**
		* 获取： 邮箱
		*/
		public String getYonghuEmail() {
			return yonghuEmail;
		}
		/**
		* 设置： 邮箱
		*/
		public void setYonghuEmail(String yonghuEmail) {
			this.yonghuEmail = yonghuEmail;
		}
		/**
		* 获取： 就业状态
		*/
		public Integer getJiuyeTypes() {
			return jiuyeTypes;
		}
		/**
		* 设置： 就业状态
		*/
		public void setJiuyeTypes(Integer jiuyeTypes) {
			this.jiuyeTypes = jiuyeTypes;
		}


			/**
			* 获取： 就业状态的值
			*/
			public String getJiuyeValue() {
				return jiuyeValue;
			}
			/**
			* 设置： 就业状态的值
			*/
			public void setJiuyeValue(String jiuyeValue) {
				this.jiuyeValue = jiuyeValue;
			}

		/**
		* 获取： 假删
		*/
		public Integer getYonghuDelete() {
			return yonghuDelete;
		}
		/**
		* 设置： 假删
		*/
		public void setYonghuDelete(Integer yonghuDelete) {
			this.yonghuDelete = yonghuDelete;
		}

	//重复字段
			/**
			* 获取： 重复字段 的types
			*/
			public Integer getSexTypes() {
			return sexTypes;
			}
			/**
			* 设置： 重复字段 的types
			*/
			public void setSexTypes(Integer sexTypes) {
			this.sexTypes = sexTypes;
			}
				public String getSexValue() {
				return sexValue;
				}
				public void setSexValue(String sexValue) {
				this.sexValue = sexValue;
				}

	@Override
	public String toString() {
		return "GongzuotuisongView{" +
			", gongzuotuisongValue=" + gongzuotuisongValue +
			", gongzuorenyuanName=" + gongzuorenyuanName +
			", gongzuorenyuanPhone=" + gongzuorenyuanPhone +
			", gongzuorenyuanEmail=" + gongzuorenyuanEmail +
			", gongzuorenyuanDelete=" + gongzuorenyuanDelete +
			", yonghuName=" + yonghuName +
			", yonghuPhoto=" + yonghuPhoto +
			", yonghuPhone=" + yonghuPhone +
			", yonghuIdNumber=" + yonghuIdNumber +
			", yonghuEmail=" + yonghuEmail +
			", yonghuDelete=" + yonghuDelete +
			"} " + super.toString();
	}
}
