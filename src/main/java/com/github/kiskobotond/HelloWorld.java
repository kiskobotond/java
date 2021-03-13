package com.github.kiskobotond;

public class HelloWorld {


    public String sayHello() {
        return "Hello Java";
    }

    public static void main(String args[]){
        System.out.println(new HelloWorld().sayHello());
    }
}
