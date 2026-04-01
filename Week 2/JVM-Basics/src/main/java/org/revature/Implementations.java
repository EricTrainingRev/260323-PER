package org.revature;

public class Implementations {
    /*
        There are 4 different Garbage Collection implementations for Java
        - Serial implementation
        - Parallel implementation
        - G1 implementation*
        - Z implementation

        Serial is the oldest implementation where garbage collection happens on a
        single thread that blocks all other threads while it iteratively checks
        and removes resources from memory that are no longer needed. This particular
        implementation is no longer ideal for modern applications, but it still
        has its place, especially for short-lived applications or applications where
        there are large work loads that then ramp down and provide time for garbage
        collection to run in a blocking-manner.

        Parallel implementation built upon the serial implementation by taking the work
        done in a single thread previously and making it a multi-threaded operation. While
        faster this form of garbage collection still has a high risk of blocking operations
        in your app for an unreasonable amount of time for your end users.

        G1 (Garbage First) implementation break heaps into multiple regions: long-lived
        region, short-lived region, meta-data region. Data first exists within the short-term
        region, and as it survives garbage collection checks it slowly becomes eligible
        to exist in the long-lived region of memory. Long-lived resources are only checked
        and potentially removed on more comprehensive GC runs, so this further reduces the
        risk of garbage collection stalling your application for an unreasonable amount of
        time.

        Z implementation was originally made for Linux machines, but it was later developed
        to work with Mac and Windows as well. The big selling point of the Z implementation
        is that GC only ever blocks the application's threads for no more than 10 miliseconds.
     */
}
