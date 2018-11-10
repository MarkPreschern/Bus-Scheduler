package model;

/**
 * Represents a person by their name, age, and bus stop location.
 */
public class Person {

  private final String name;
  private final int age;
  private final int[] stopLocation;

  /**
   * Constructor initializes a person given a name, age, and bus stop location
   *
   * @param name         name
   * @param age          age
   * @param stopLocation bus stop location
   */
  public Person(String name, int age, int[] stopLocation) {
    this.name = name;
    this.age = age;
    this.stopLocation = stopLocation;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public int[] getStopLocation() {
    return stopLocation;
  }
}
