/**
 * @Author: qianyuhang
 * @Date: 2018-4-20
 */
package com.koh.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author qianyuhang
 * @ClassName: Company
 * @Description:
 * @date 2018-4-20
 */
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Company implements Serializable {

    private static final long serialVersionUID = -1;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    /**
     * 名字
     */
    @Column(name = "name", nullable = false)
    private String name;
    /**
     * 年龄
     */
    @Column(name = "age", nullable = false)
    private Integer age;
    /**
     * 地址
     */
    @Column(name = "address", nullable = false)
    private String address;
    /**
     * 工资
     */
    @Column(name = "salary", nullable = false)
    private Float salary;


    public void setId(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return this.id;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }

    public void setAge(Integer value) {
        this.age = value;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAddress(String value) {
        this.address = value;
    }

    public String getAddress() {
        return this.address;
    }

    public void setSalary(Float value) {
        this.salary = value;
    }

    public Float getSalary() {
        return this.salary;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                "name=" + name +
                "age=" + age +
                "address=" + address +
                "salary=" + salary +
                '}';
    }
}

