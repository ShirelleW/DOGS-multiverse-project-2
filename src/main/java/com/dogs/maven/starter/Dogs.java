package com.dogs.maven.starter;

import java.util.concurrent.atomic.AtomicInteger;
public class Dogs {

  private static final AtomicInteger COUNTER = new AtomicInteger();
  private int id;
  private String name;
  private String breed;
  private int age;

  public Dogs(String name, String breed, int age) {
    this.id = COUNTER.getAndIncrement();
    this.name = name;
    this.breed = breed;
    this.age = age;
  }

  public Dogs() {
    this.id = COUNTER.getAndIncrement();
  }

  public String getName() {
    return name;
  }

  public String getBreed() {
    return breed;
  }

  public int getId() {
    return id;
  }

  public int getAge() {
    return age;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setBreed(String breed) {
    this.breed = breed;
  }

  public void setAge(int age) {
    this.age = age;
  }
}


