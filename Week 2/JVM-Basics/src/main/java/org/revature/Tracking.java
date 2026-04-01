package org.revature;

public class Tracking {

    public void giveMessage(){
        System.out.println("This will run forever!");
    }

    public static void main(String[] args) {
        /*
            When data is stored there are two primary categories the JVM uses
            for classifying data: short-lived and long-lived data.

            Short-lived data is the initial classification for all storage/memory
            of any data in your application. Short-lived data is also the first
            target for any garbage collection run by the JVM
         */
//        shortLivedExample();
        longLivedExample();
    }

    public static void shortLivedExample(){
        while(true){
            /*
                Because these Tracking objects are created, utilized, and then
                lose their reference (each loop the messageGiver reference is
                referencing a brand-new Tracking object) these Tracking objects
                will always exist in short-lived classification. These are the
                types of objects that are first removed by garbage collection.
                Because these objects lose their references so quickly they will
                be the first to be removed by garbage collection, which will keep
                memory open for the application
             */
            Tracking messageGiver = new Tracking();
            messageGiver.giveMessage();
            System.out.println(messageGiver);
        }
    }

    public static void longLivedExample(){
        /*
            Unlike short-life resources, long-lived resources are of secondary
            priority by the garbage collector to be removed: this classification
            is given to resources that survive multiple rounds of garbage collection
            (the amount of time needed to switch is handled by the JVM). These
            resources are still candidates to be removed from memory via the
            JVM, but they are not prioritized for removal. There is an assumption
            made that any resource that lasts long enough to be moved into the
            long-lived category is more likely than not going to be needed for the
            lifespan of the application, or at least until things ramp down enough
            that the resource can safely be removed during a down period where a
            longer and more thorough garbage collection process can run.
         */
        Tracking longLivedObjected = new Tracking();
        while(true){
            longLivedObjected.giveMessage();
            System.out.println(longLivedObjected);
        }
    }
}
