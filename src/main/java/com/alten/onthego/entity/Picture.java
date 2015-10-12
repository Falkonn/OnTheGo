package com.alten.onthego.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Picture
 *
 */
@Entity
@Table(name="Picture")

public class Picture implements Serializable {

	
	private static final long serialVersionUID = 1L;
	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
	    @Column(name = "pic_url")
	    private String picUrl;
	    @Column(name = "user_id")
	    private Integer userId;
	
	public Picture() {
		super();
	}

	public Picture(String picUrl, Integer userId) {
		super();
		this.picUrl = picUrl;
		this.userId = userId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
   
}
