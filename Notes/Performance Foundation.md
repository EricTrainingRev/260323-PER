# Performance Foundation Notes

## Standard Development and Testing Lifecycles
- **SDLC (Software Development Life Cycle):** The overall process of developing software, from requirements gathering to maintenance.
	- **Requirements Gathering:** Define what the system should do.
	- **Design:** Architect the system, selecting technologies and environments to be used.
	- **Implementation (Coding):** Develop the software according to design specifications.
	- **Testing:** Validate functional and non-functional requirements.
	- **Deployment:** Release the software to production.
	- **Maintenance:** Monitor, optimize, and update the system to maintain and improve the system over time.
- **STLC (Software Testing Life Cycle):** The subset of SDLC focused on testing activities, including planning, design, execution, and closure.
	- **Requirement Analysis:** Review and analyze requirements to identify testable aspects.
	- **Test Planning:** Define the scope, objectives, resources, schedule, and approach for testing.
	- **Test Case Design:** Create detailed test cases and test data based on requirements and design documents.
	- **Test Environment Setup:** Prepare the hardware, software, and network configurations needed for testing.
	- **Test Execution:** Run the test cases, record results, and log defects as needed.
	- **Test Closure:** Evaluate test completion criteria, document findings, and archive test artifacts for future reference.

The SDLC provides the overall framework for software development, while the STLC ensures that testing is systematically planned and executed within that framework. Together, they help deliver high-quality software by integrating testing activities throughout the development process.

## Functional & Non-Functional Testing
Functional testing ensures that a system operates according to its requirements, producing correct outputs for given inputs. This process is focused on verification: confirming that the software performs as specified and meets the documented expectations.

Non-functional testing, on the other hand, evaluates broader attributes such as performance, scalability, reliability, and security. Performance testing is a key aspect of this category. The emphasis here is on validation, making sure the system not only works correctly but also fulfills user needs and quality standards beyond basic functionality.

When discussing verification and validation, verification refers to checking if the software meets its specified requirements, essentially asking, “Are we building the product right?” This is closely tied to functional testing. Validation, meanwhile, is about determining whether the software meets the needs and expectations of end users, or “Are we building the right product?” This is typically associated with non-functional testing.

## Performance Tests
- **Load Testing:** Measures system behavior under expected user loads. Identifies throughput, response times, and resource utilization.
- **Stress Testing:** Evaluates system stability and error handling under extreme conditions.
- **Spike Testing:** Assesses system response to sudden, sharp increases in load.
- **Endurance (Soak) Testing:** Examines system performance and resource usage over extended periods to detect memory leaks or degradation.

**Example:**
An e-commerce site is load tested with 10,000 concurrent users, stress tested to 50,000 users, spike tested with sudden traffic bursts, and endurance tested over 24 hours.


## Performance Engineering
Performance engineering is the discipline of designing, developing, and testing software with performance as a core quality attribute throughout the entire Software Development Life Cycle (SDLC). It goes beyond traditional performance testing by proactively identifying and addressing performance risks from the earliest stages of a project.

### Shift-Left Approach
The shift-left approach in performance engineering means integrating performance considerations early in the SDLC, starting from requirements, design, and development, rather than waiting until testing or deployment. This helps catch potential bottlenecks and inefficiencies before they become costly to fix.

### Benefits in the SDLC
- Early detection of performance issues reduces remediation costs and project delays.
- Ensures that performance requirements are considered alongside functional requirements.
- Improves user satisfaction by delivering faster, more reliable applications.
- Supports scalability and reliability goals from the outset.
- Reduces risk of production outages or slowdowns by validating performance continuously.

## Performance Metrics Vocabulary
- **Latency:** The time taken to process a single request (usually measured in milliseconds).
- **Response Time:** The total time from sending a request to receiving a response, including network and processing delays.
- **Throughput:** The number of transactions or requests processed per unit of time (e.g., requests per second). A common measurement used to track throughput is Transactions per Second (TPS)
- **Concurrency:** The number of simultaneous users or transactions the system can handle at once.
- **Utilization:** The percentage of system resources (CPU, memory, disk, network, etc.) in use during testing.
- **Error Rate:** The percentage of requests that result in errors (e.g., HTTP 4xx/5xx responses) during a test.
- **Percentiles (e.g., 90th, 95th, 99th):** Metrics that show the value below which a given percentage of observations fall, useful for understanding response time distribution and outliers.
- **Apdex Score:** A standardized metric for user satisfaction based on response time thresholds, ranging from 0 (no users satisfied) to 1 (all users satisfied).
- **Scalability:** The system’s ability to handle increased load by adding resources (horizontal or vertical scaling).
- **Capacity:** The maximum load a system can handle before performance degrades or errors increase.

**Example:**
During a load test, the system shows an average latency of 200ms, a 95th percentile response time of 350ms, supports 5,000 concurrent users, achieves a throughput of 1,000 transactions per second at 70% CPU utilization, and maintains an Apdex score of 0.95 with an error rate below 0.5%.