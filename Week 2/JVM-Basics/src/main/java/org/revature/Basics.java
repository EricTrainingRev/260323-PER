package org.revature;

public class Basics {
    /*
        Whenever you start a Java application space is reserved by the JVM for
        stack memory and heap memory. All Java applications have a default starting
        stack to work with: this is provided by the main method of the application
     */
    public static void main(String[] args) {
        String printStatement = "This adds a stack to the stack";
        // this adds the println stack into memory
        System.out.println(printStatement);
        // once execution is finished it is removed from memory and the main method
        // continues its execution
        Basics myObj = new Basics();
        // we can set multiple reference variables to the same object, and
        // only when all of the references to the object are gone will
        // the data be removed via garbage collection
        Basics secondReference = myObj;
        System.out.println(myObj);
        System.out.println(secondReference);


    }
}
