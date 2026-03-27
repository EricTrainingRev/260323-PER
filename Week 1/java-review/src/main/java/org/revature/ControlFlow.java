package org.revature;

public class ControlFlow {
    /*
        Control flow is just a way of referencing the various options you have
        for facilitating code execution. This is your loops and your conditional
        statements
     */
    public static void main(String[] args) {
        // CONDITIONAL STATEMENTS
        /*
            If blocks are your most basic conditional check: if the statement
            inside the if parentheses results in a true boolean the code will
            execute, otherwise it is skipped
         */
        if(true){
            System.out.println("if the logical check is true this will execute");
        }
        if(5<10){
            System.out.println("5 is less than 10, so this executes");
        }
        // if the condition is false you can have an "else" block trigger
        if(false){
            System.out.println("this will never execute");
        } else {
            System.out.println("this executes if the if statement returns false");
        }
        /*
            If you have multiple conditions to check and you only want one
            of them to trigger code execution you can make use of else if blocks
         */
        if(false){
            System.out.println("this will not execute");
        } else if(true){
            System.out.println("the second check returns true, so this executes");
        } else {
            System.out.println("this will not execute");
        }

        // LOOPS
        /*
            There are two primary options for looping your code: for loops when you know how many iterations you need,
            and while loops when you are not sure how many iterations you need
         */
        /*
            For loops have three different statements in their parenthesis:
            1. initializing a control variable
            2. setting the conditions under which the loop continues
            3. determining what happens to the control variable after a loop iteration
         */
        for(int x = 1; x <= 10; x++){
            System.out.println(x);
        }

        /*
            While loops are less structured than for loops: you don't set a control variable within the loop, you
            just provide the conditions under which the loop progresses
         */
//        while(true){
//            System.out.println("this will print forever!");
//        }
        int y = 0;
        while(y < 20){
            System.out.println(y);
            y++;
        }
        /*
            do while loops flip the order of operations: instead of checking the boolean results and then executing code
            the code is executed first, and then the logic is checked.
         */
        do{
            System.out.println("Execute code first!");
        } while(false);

    }
}
