# System Behavior Under Load


## OS Metrics

Understanding operating system (OS) metrics is fundamental for diagnosing system performance and identifying bottlenecks during load testing. These metrics provide insight into how system resources are being utilized and where potential issues may arise.

### Key OS Metrics

- **CPU Utilization:** The percentage of CPU capacity currently in use. High CPU utilization may indicate that the system is processing a large number of tasks, but consistently high values (e.g., >85%) can signal CPU saturation and potential performance degradation.
	- **CPU Load Average:** Represents the average number of processes waiting to be executed over a period of time (e.g., 1, 5, 15 minutes). A load average higher than the number of CPU cores suggests the system is overloaded.
	- **CPU Cores**: A core is an individual processing unit within a CPU capable of executing its own thread of instructions. Modern CPUs have multiple cores, allowing them to process several tasks in parallel. The total number of cores determines how many processes or threads can be executed simultaneously without waiting.
		- Example: A quad-core (4-core) CPU can handle four independent tasks at the same time, improving multitasking and throughput compared to a single-core processor.
	- **Example:** On a 4-core system, a load average of 6.0 means there are more processes waiting than the CPU can handle efficiently.

- **Memory Usage:** The amount of RAM currently in use by the system and applications. High memory usage can lead to swapping or paging, which significantly slows down performance.
	- **Swapping and Paging:** When available physical memory is exhausted, the operating system moves data to and from disk (swap space or page file) to free up RAM. This process is much slower than accessing RAM directly and can cause severe performance degradation, often observed as increased disk activity and high I/O wait times.
	- **Memory Pressure:** Occurs when the system is consistently close to full memory utilization, forcing the OS to aggressively reclaim or swap memory. Symptoms include slow application response, increased latency, and sometimes application crashes if memory cannot be allocated.
	- **Memory Leak Indicators:** Gradual increase in memory usage over time without release, often leading to eventual exhaustion and system instability. Leaks are typically caused by applications not freeing memory after use.
	- **Example:** A web server’s memory usage grows steadily during a load test, never decreasing, indicating a possible memory leak. In another scenario, a spike in active users causes memory usage to reach 95%, triggering swapping and resulting in much slower response times for all users.

- **Disk I/O:** Measures the rate of read/write operations to disk. High disk I/O can become a bottleneck, especially if the disk is not able to keep up with the demand, leading to increased response times.
	- **Saturation Patterns:** Saturation occurs when the disk subsystem cannot process incoming read/write requests as quickly as they arrive, resulting in a backlog (queue) of pending operations. This is often observed as high disk queue lengths, increased wait times, and consistently high disk utilization (close to 100%). When saturation is present, even small increases in workload can cause disproportionate increases in response time and system latency. Monitoring tools may show disk latency spikes and long I/O wait times as key indicators of saturation.
	- **Example:** During a database load test, disk utilization reaches 100%, the average disk queue length grows, and response times spike as the disk cannot keep up with the volume of requests.

- **Network Utilization:** The amount of data being transmitted and received over network interfaces. High network usage can cause packet loss, retransmissions, and increased latency.
	- **Example:** A spike in network traffic during a test causes some requests to time out due to network saturation.

### Monitoring Tools Overview

- **Task Manager (Windows):** Provides a graphical overview of CPU, memory, disk, and network usage.
- **top/htop (Linux):** Command-line tools for real-time monitoring of system resource usage.
- **perfmon (Windows Performance Monitor):** Advanced tool for tracking detailed performance counters.
- **iostat, vmstat, sar (Linux):** Utilities for monitoring disk I/O, memory, and CPU statistics.

**Example Scenario:**
During a load test, CPU utilization averages 70%, but memory usage steadily increases, eventually causing the system to swap and response times to degrade. Disk I/O remains low, indicating the bottleneck is memory, not CPU or disk.


## Thread & Concurrency

- **Threads**: A thread is the smallest unit of execution within a process. Each thread runs independently but shares the same memory and resources as other threads in the same process. Threads enable concurrent execution of tasks, allowing programs to perform multiple operations at once (e.g., handling user input while processing data in the background). Effective use of threads can improve application responsiveness and throughput, but poor management can lead to issues like contention, deadlocks, or excessive context switching.
	- **Contention**: Contention occurs when multiple threads compete for the same shared resource, such as a lock, CPU, or memory. High contention can lead to threads waiting or blocking, reducing overall system throughput and increasing response times. Effective synchronization and minimizing shared resource usage help reduce contention.
	- **Deadlock**: A deadlock occurs when two or more threads are each waiting for resources held by the other, causing all of them to be stuck indefinitely. This situation typically arises from improper locking order or resource acquisition patterns. Deadlocks can halt progress in part of an application and are often difficult to detect and resolve.
	- **Context Switching**: Context switching is when the operating system pauses one thread and resumes another, saving and restoring their states. While necessary for multitasking, frequent context switches add overhead and can reduce CPU efficiency, especially if there are many threads competing for limited CPU resources.

- **Concurrency**: Concurrency is the ability of a system to manage multiple tasks at the same time, even if they are not literally running simultaneously. It involves structuring programs so that many operations can be in progress, with the system switching between them as needed. Concurrency can be achieved with threads, event loops, or other mechanisms, and is about organizing work to maximize responsiveness and resource utilization. While concurrency enables parallelism, they are not the same: parallelism is about actually executing tasks at the same time, whereas concurrency is about handling many tasks at once, regardless of whether they run in parallel.

- **Thread Pools:** A thread pool is a collection of pre-instantiated, reusable threads managed by the system or application. Instead of creating a new thread for each task, tasks are assigned to available threads in the pool, reducing overhead and improving scalability.
	- **Example:** A web server uses a thread pool to handle incoming HTTP requests, reusing threads for each new connection instead of creating and destroying threads repeatedly.

- **Blocking Behavior:** Blocking occurs when a thread waits for a resource or event (such as I/O completion, a lock, or data availability) before it can proceed. Excessive blocking can reduce system throughput and increase response times, as threads spend time idle rather than doing useful work.
	- **Example:** A thread waiting for a database query to complete is blocked and cannot process other tasks until the query returns.

- **Thread Exhaustion:** Thread exhaustion happens when all threads in a pool are busy or blocked, and no threads are available to handle new tasks. This can lead to request queuing, increased latency, or even application failures if the queue overflows.
	- **Example:** During a load test, a fixed-size thread pool becomes fully occupied, causing new requests to wait in a queue or be rejected.

- **Context Switching:** Context switching is the process by which the operating system saves the state of a currently running thread and loads the state of another thread. While necessary for multitasking, frequent context switches add overhead and can degrade performance, especially if there are many threads competing for CPU time.
	- **Example:** An application with hundreds of active threads may experience high context switch rates, leading to increased CPU usage and reduced efficiency.

- **Synchronous vs Asynchronous Processing:**
	- **Synchronous Processing:** Tasks are performed one after another; a thread waits for each operation to complete before moving to the next. This can lead to blocking and under-utilization of resources.
	- **Asynchronous Processing:** Tasks can be initiated and then completed independently; a thread can start a task and move on to other work while waiting for the result. Asynchronous processing improves resource utilization and responsiveness, especially for I/O-bound operations.
	- **Example:** In a synchronous model, a web server thread waits for a file read to finish before handling another request. In an asynchronous model, the thread initiates the file read and immediately processes other requests, handling the file result when it becomes available.

**Example Scenario:**
During a stress test, a server with a fixed thread pool size of 50 becomes saturated as all threads are blocked waiting for slow database responses. New incoming requests are queued, causing response times to increase sharply. Switching to asynchronous database calls allows threads to handle more requests concurrently, reducing wait times and improving overall throughput.

## JVM Basics

### Heap vs Stack

- **Heap:** The heap is the runtime memory area from which all class instances and arrays are allocated. It is shared among all threads. The heap is managed by the JVM’s garbage collector, which reclaims memory from objects that are no longer referenced. High heap usage can lead to increased garbage collection activity, which may cause application pauses (GC pauses) and impact response times.
	- **Example:** A web application with many active user sessions may see heap usage grow as more objects are created. If the heap becomes full, the JVM triggers garbage collection to free up space, potentially causing noticeable pauses.

- **Stack:** Each thread in a Java application has its own stack, which stores method call frames, local variables, and partial results. The stack is much smaller than the heap and is used for short-lived data. Stack overflows occur when too many nested method calls exhaust the stack space, resulting in a `StackOverflowError`.
	- **Example:** A recursive function without a proper base case can cause a stack overflow by consuming all available stack space.

### GC Fundamentals

- **Garbage Collection (GC):** GC is the process by which the JVM automatically identifies and reclaims memory occupied by objects that are no longer in use. Different GC algorithms (e.g., Serial, Parallel, G1, Z) offer trade-offs between throughput, pause times, and resource usage.
	- **GC Pauses:** During GC, application threads may be paused (stop-the-world events), leading to increased response times or latency spikes. Frequent or long GC pauses can degrade application performance, especially under heavy load.
	- **Tuning GC:** JVM parameters (e.g., heap size, GC algorithm) can be tuned to balance throughput and pause times based on application needs.
	- **Example:** During a load test, frequent full GCs cause noticeable pauses in request processing, indicating the need for heap or GC tuning.

### JVM Memory Regions

- **Young Generation (Short Lived):** Where new objects are allocated. Most objects die young and are quickly collected in minor GCs. The young generation is further divided into Eden and Survivor spaces.
- **Old Generation (Long Lived):** Objects that survive multiple GCs in the young generation are promoted to the old generation. Full GCs clean up this region and are typically more expensive.
- **Metaspace (Java 8+):** Stores class metadata and static information. Metaspace is allocated in native memory, not the heap, and can grow as needed (limited by system memory or JVM settings).
	- **Example:** A large application with many classes or dynamic class loading may see high Metaspace usage, potentially leading to `OutOfMemoryError: Metaspace` if not sized appropriately.

### Object Lifecycle in JVM

- **Creation:** Objects are created on the heap using the `new` keyword or similar mechanisms.
- **Usage:** Objects are referenced and manipulated by application code. As long as references exist, the object remains reachable.
- **Garbage Collection:** When no references to an object remain, it becomes eligible for garbage collection. The exact time of collection is determined by the JVM.
- **Finalization (Deprecated):** The `finalize()` method was once used for cleanup before object removal, but is now deprecated in favor of more reliable resource management techniques (e.g., try-with-resources, explicit close methods).
	- **Example:** A cache object is created and used during a request. Once the request completes and no references remain, the cache object is eventually collected by the JVM.

**Example Scenario:**
During a load test, heap usage steadily increases and frequent full GCs occur, causing response times to spike. Analysis reveals a memory leak in session management code, leading to objects being retained longer than necessary. After fixing the leak and tuning the heap size, GC pauses are reduced and application throughput improves.


## SQL Performance Fundamentals

Understanding SQL performance fundamentals is crucial for diagnosing database bottlenecks and optimizing query efficiency during load testing. Well-designed queries and proper indexing can significantly improve application responsiveness and throughput.

### Query Execution Lifecycle

- **Parsing:** The database parses the SQL statement, checks syntax, and validates object references.
- **Optimization:** The query optimizer determines the most efficient execution plan based on available indexes, statistics, and schema structure.
- **Execution:** The database engine executes the plan, retrieving and processing data as needed.
- **Result Return:** The results are sent back to the application or client.
	- **Example:** A SELECT query is parsed, optimized to use an index, executed, and the result set is returned to the user.

#### Query Parsing Order Of Operations
```sql
FROM
WHERE
GROUP BY
HAVING
SELECT
WINDOW FUNCTION
ORDER BY
OFFSET / FETCH / LIMIT
```

### Full Table Scan vs Index Scan

- **Full Table Scan:** The database reads every row in the table to find matching records. This is resource-intensive and slow for large tables, often causing high disk I/O and increased response times.
- **Index**: An index is a data structure used by databases to quickly locate and access rows in a table based on the values of one or more columns. Indexes work like a book’s table of contents, allowing the database to find data efficiently without scanning every row. Properly designed indexes greatly improve query performance, especially for large tables.
- **Index Scan:** The database uses an index to quickly locate matching rows, reducing the number of reads and improving performance. Proper indexing is key to avoiding unnecessary full table scans.
	- **Example:** Searching for a user by email in a table with an email index results in a fast index scan. Without the index, the database performs a full table scan, slowing down the query.

### Joins vs Subqueries Performance

- **Joins:** Combine rows from two or more tables based on related columns. Joins are generally more efficient than subqueries, especially when proper indexes are present. However, complex joins can increase CPU and memory usage.
- **Subqueries:** Nested queries used to filter or aggregate data. Subqueries can be less efficient, especially if executed repeatedly or if they return large result sets.
	- **Example:** An INNER JOIN between orders and customers retrieves matching records efficiently. A correlated subquery for each order may result in many repeated queries and slower performance.

Poorly optimized subquery (runs the subquery for every row in the outer table, causing repeated work and slow performance):
```sql
SELECT o.id, o.amount
FROM orders o
WHERE o.customer_id IN (
  SELECT c.id
  FROM customers c
  WHERE c.status = 'active'
);
```

Optimized join (retrieves matching rows in a single pass, leveraging indexes and reducing redundant work):
```sql
SELECT o.id, o.amount
FROM orders o
INNER JOIN customers c ON o.customer_id = c.id
WHERE c.status = 'active';
```

### Understanding Query Execution Plans

- **Execution Plan:** A detailed map of how the database will execute a query, including join methods, index usage, and row access strategies. Reviewing execution plans helps identify inefficiencies, such as missing indexes or unnecessary full table scans.
	- **Example:** An execution plan shows a sequential scan on a large table, indicating a missing index. Adding the index changes the plan to an index scan, improving performance.

### Identifying Slow Queries in Logs

- **Slow Query Logs:** Databases often provide logs of queries that exceed a specified execution time threshold. Analyzing these logs helps pinpoint problematic queries and areas for optimization. The mechanism for this will differ between database management systems.
- **Common Causes:** Slow queries are often caused by missing indexes, inefficient joins, large result sets, or poor query structure.
	- **Example:** During a load test, the slow query log reveals several SELECT statements taking multiple seconds due to full table scans. Adding indexes and rewriting queries reduces execution times.

### Practical Tips

- Use indexes to optimize query performance and avoid full table scans.
- Review query execution plans regularly to identify bottlenecks and inefficiencies.
- Monitor slow query logs to detect and address problematic SQL statements.
- Optimize joins and subqueries for efficiency, especially in high-load scenarios.
- Limit result set sizes and avoid unnecessary data retrieval.

**Example Scenario:**
During a performance test, response times spike when a report query runs. Analysis of the execution plan shows a full table scan on a large transactions table. Adding an index on the relevant column and rewriting the query to use a JOIN reduces execution time from 10 seconds to under 1 second, improving overall system throughput.
