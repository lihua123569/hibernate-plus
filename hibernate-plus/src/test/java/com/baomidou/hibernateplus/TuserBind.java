package com.baomidou.hibernateplus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.baomidou.framework.entity.Convert;

@Entity
@Table(name = "gs_user_bind")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TuserBind extends Convert {

	private static final long serialVersionUID = 1L;
	protected Long id;

	// 高手用户ID
	private Integer gsUserId;
	// 绑定类型 1.微信 2.QQ
	private Integer bindType;
	// 绑定详情,如微信为openid
	private String bindDetails;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "gs_user_id")
	public Integer getGsUserId() {
		return gsUserId;
	}

	public void setGsUserId(Integer gsUserId) {
		this.gsUserId = gsUserId;
	}

	@Column(name = "bind_type")
	public Integer getBindType() {
		return bindType;
	}

	public void setBindType(Integer bindType) {
		this.bindType = bindType;
	}

	@Column(name = "bind_details")
	public String getBindDetails() {
		return bindDetails;
	}

	public void setBindDetails(String bindDetails) {
		this.bindDetails = bindDetails;
	}
}