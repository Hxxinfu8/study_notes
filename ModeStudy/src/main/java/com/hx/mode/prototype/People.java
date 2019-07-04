package com.hx.mode.prototype;

import lombok.Data;

@Data
public class People implements Cloneable{
    private String name;
    private Integer age;
    private Experience experience;


    public void setExperience(String company, String address) {
        experience.setCompany(company);
        experience.setAddress(address);
    }

    @Override
    protected People clone(){
        People people = null;
        try {
            people = (People)super.clone();
            people.setName(name);
            people.setAge(age);
            people.setExperience((Experience) experience.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return people;
    }

    public void say() {
        System.out.println("姓名：" + name);
        System.out.println("年龄：" + age);
        System.out.println("工作经历：");
        System.out.println("公司：" + experience.getCompany());
        System.out.println("地址：" + experience.getAddress());
    }
}
