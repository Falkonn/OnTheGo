package com.alten.onthego.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Picture
 *
 */
@Entity
@Table(name = "Picture")

public class Picture implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "pic_url")
    private String picUrl;
    @Column(name = "user_id")
    private Integer userId;

    public Picture() {

    }

    public Picture(String picUrl, Integer userId) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
