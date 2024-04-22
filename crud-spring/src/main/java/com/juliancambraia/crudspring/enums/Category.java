package com.juliancambraia.crudspring.enums;

public enum Category {
  BACK_END("Back-end"),
  FRONT_END("Front-end");

  private final String name;

  private Category(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }

}
