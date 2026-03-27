package org.revature;
/*
    All Java code is facilitated fundamentally through classes and methods.
    - Classes store the data for our application
        - fields = the data we are working with
        - methods = the repeatable actions we want to perform with that data
    - The entrypoint for a Java application is a static main method
 */
public class Classes {
    // This is a field in the class: it is a reference to whatever data we want to work with
    String name = "Billy";
    // note this field is not initialize: it's value is set in the constructor
    int age;
    // static resources are scoped to the Class, not Objects.
    static String species = "Human";
    // this is a constructor: it is called whenever we try and make a "new" object of the class
    public Classes(int age){
        /*
            The line of code below sets the age field of the object being created to the value
            of the age parameter declared as part of the constructor. The "this" keyword helps distinguish
            between the two "age" references. this.age is a reference to the object field, age is a reference
            to the constructor parameter
         */
        this.age = age;
    }

    public void announceSelf(){
        System.out.println("Hello! I am " + name + " and I am " + age + " years old.");
    }

    public static void declareSpecies(){
        System.out.println("All objects of this class are " + species);
    }

    public static void main(String[] args) {
        Classes myObject = new Classes(25);
        System.out.println(myObject.name);
        System.out.println(myObject.age);
        System.out.println(Classes.species);
        myObject.announceSelf();
        declareSpecies();
    }
}
