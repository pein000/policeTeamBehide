package com.fee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by qiuwei on 2016/3/3.
 */
@Entity()
@Table(name="police_admin_user")
public class UserEntity {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
