package com.hx.mode.prototype;

public class Client {
    public static void main(String[] args){
        People people = new People();
        Experience experience = new Experience("百度", "北京");
        people.setName("A");
        people.setAge(20);
        people.setExperience(experience);
        people.say();

        People clone = people.clone();
        clone.setExperience("斗鱼", "武汉");
        clone.say();
        people.say();
    }
}
