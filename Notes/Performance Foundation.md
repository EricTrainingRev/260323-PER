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

## Key NFR Concepts
- **Performance:** The system’s ability to meet response time, throughput, and resource utilization targets under expected workloads.
- **Scalability:** The capability to handle increased load by adding resources (horizontal or vertical scaling).
- **Reliability:** The ability of the system to operate without failure over a specified period.
- **Availability:** The proportion of time the system is operational and accessible when required for use.
- **Security:** Protection of data and resources from unauthorized access and vulnerabilities.
- **Maintainability:** How easily the system can be updated, fixed, or improved.
- **Usability:** The ease with which users can interact with the system.

NFRs are typically defined early in the project and validated through non-functional testing and monitoring.

## Service Level Topics
A Service Level Agreement (SLA) is a formal contract between a service provider and its customer, outlining specific performance and availability targets, such as 99.9% uptime or a response time under 500 milliseconds. These agreements set clear expectations for the quality of service delivered.

Service Level Objectives (SLOs) are the measurable goals that underpin SLAs. For example, an SLO might specify that 99.95% of requests should be served in under 300 milliseconds. SLOs are often set more stringently than SLAs, providing a buffer that helps ensure contractual obligations are consistently met even if occasional issues arise.

Service Level Indicators (SLIs) are the actual metrics used to track and quantify the level of service provided. An SLI would be the percentage of requests completed within a certain timeframe, offering a concrete way to measure performance against SLOs and SLAs.

As an exmple, an SLA might require 99.9% uptime. To support this, the SLO could be set at 99.95% uptime, and the SLI would be the actual measured uptime over a given period. This structure helps organizations monitor, manage, and improve their service delivery.

## Common Performance Metrics
- **Throughput:** The number of transactions or requests processed per second, indicating system capacity.
- **Response Time:** The time taken to process a request from start to finish.
- **Apdex Score:** A user satisfaction metric based on response time thresholds, ranging from 0 (no users satisfied) to 1 (all users satisfied).
	- score calculation

$$
Apdex = \frac{\text{Number of Satisfied Requests} + (0.5 \times \text{Number of Tolerating Requests})}{\text{Total Number of Requests}}
$$

Percentiles (such as the 90th, 95th, and 99th) are used to understand the distribution of response times and to identify outliers.

**Example:**
An application with a throughput of 2,000 TPS, a 95th percentile response time of 400ms, and an Apdex score of 0.92 meets its SLO for user experience.

## Workload Modeling
Workload modeling is the process of designing realistic test scenarios that accurately represent how users interact with a system. Effective workload models help ensure that performance tests provide meaningful results and uncover potential bottlenecks before production.

### Key Concepts
- **Think Time vs. Pacing:**
	- **Think Time:** The delay between a user receiving a response and sending the next request (simulates real user behavior).
	- **Pacing:** The interval between the start of one iteration and the start of the next, controlling the rate of test execution.
- **Arrival Rate Modeling:** Defines how frequently new users or requests arrive at the system (e.g., users per second). This helps simulate real-world traffic patterns, such as bursts or gradual ramp-ups.
- **User Distribution:** Specifies how different types of users or actions are represented in the test (e.g., 70% browsing, 20% purchasing, 10% support queries).
- **Concurrency vs. Arrival Rate:**
	- **Concurrency:** The number of users or sessions active at the same time.
	- **Arrival Rate:** The rate at which new users or requests enter the system, which may result in varying concurrency depending on session length and think time.

For example, when testing an e-commerce site:
- 100 virtual users ramp up over 10 minutes.
- Each user spends 5 seconds (think time) between actions.
- 80% of actions are browsing, 15% are adding to cart, 5% are checking out.
- Arrival rate is set to 10 new users per minute.

## Web Architecture / HTTP

### HTTP/HTTPS Deep Dive
- **HTTP (Hypertext Transfer Protocol):** The foundational protocol for data communication on the web. Operates over TCP (usually port 80) and is stateless by design, meaning each request is independent and contains all necessary information for the server to process it.
	- **HTTP/1.1:** The most widely used version, supports persistent connections (keep-alive), pipelining, and chunked transfer encoding.
	- **HTTP/2:** Introduces multiplexing (multiple requests/responses in a single connection), header compression, and improved performance over HTTP/1.1. Uses binary framing instead of text-based messages.
	- **HTTP/3:** Based on QUIC (UDP instead of TCP), further reduces latency and improves connection reliability, especially on unreliable networks.
	- **Request/Response Model:** Clients send requests (with method, URI, headers, optional body), and servers respond with status codes, headers, and optional body.
	- **Statelessness:** Each request is processed in isolation; session state must be managed externally (e.g., via cookies or tokens).
- **HTTPS (HTTP Secure):** HTTP layered over SSL/TLS (usually port 443), providing encryption, authentication, and data integrity for secure communication.
	- **TLS Handshake:** Establishes a secure session by negotiating encryption algorithms and exchanging keys.
	- **Certificates:** Digital certificates are used to authenticate the server (and optionally the client) to prevent man-in-the-middle attacks.
	- **Data Encryption:** All data exchanged between client and server is encrypted, protecting against eavesdropping and tampering.

### HTTP Methods
HTTP methods define the type of action a client wants to perform on a resource identified by a URL. They are fundamental to RESTful APIs and web communication, specifying whether the operation is to create, retrieve, update, or delete data, or to perform other protocol-level actions.

- **GET:** Retrieve data from the server (read-only, no side effects).
- **POST:** Submit data to the server (creates or updates resources).
- **PUT:** Replace an existing resource or create it if it does not exist.
- **PATCH:** Partially update an existing resource.
- **DELETE:** Remove a resource from the server.
- **HEAD, OPTIONS, TRACE, CONNECT:** Other methods for metadata retrieval, capability discovery, diagnostics, and tunneling.

### Status Codes
- **1xx (Informational):** Request received, continuing process.
- **2xx (Success):** Request successfully received, understood, and accepted (e.g., 200 OK, 201 Created).
- **3xx (Redirection):** Further action needed to complete the request (e.g., 301 Moved Permanently, 302 Found).
- **4xx (Client Error):** The request contains bad syntax or cannot be fulfilled (e.g., 400 Bad Request, 404 Not Found).
- **5xx (Server Error):** The server failed to fulfill a valid request (e.g., 500 Internal Server Error, 503 Service Unavailable).

### Headers & Cookies
- **Headers:** Key-value pairs sent with HTTP requests and responses, carrying metadata that controls how requests and responses are processed.
	- **Content-Type:** Specifies the media type of the resource or data being sent (e.g., application/json, text/html).
	- **Accept:** Informs the server about the types of data the client can process (e.g., application/json).
	- **Authorization:** Contains credentials for authenticating the client with the server (e.g., Bearer tokens, Basic auth).
	- **User-Agent:** Identifies the client application, browser, or tool making the request.
	- **Cookie:** Sends stored cookies from the client to the server for session management or personalization.
	- **Set-Cookie:** Used by the server to send cookies to the client.
	- **Cache-Control:** Directs caching behavior for requests and responses (e.g., no-cache, max-age).
	- **Location:** Used in redirects to specify the new URL for the client to request.
	- **Host:** Specifies the domain name of the server (useful for virtual hosting).
	- **Referer:** Indicates the URL of the previous web page from which a request was made.
	- **X-Forwarded-For:** Identifies the originating IP address of a client connecting through a proxy or load balancer.
- **Cookies:** Small pieces of data stored on the client and sent with requests to maintain state, track sessions, or store preferences. Cookies are essential for enabling features like user authentication, shopping carts, and personalization in web applications.
	- **Session Cookies:** Temporary cookies that are deleted when the browser is closed; used for session management.
	- **Persistent Cookies:** Remain on the client for a specified duration; used for remembering user preferences or login states.
	- **Secure:** Can only be transmitted over HTTPS connections, enhancing security.
	- **HttpOnly:** Cannot be accessed via JavaScript, reducing the risk of cross-site scripting (XSS) attacks.
	- **SameSite:** Restricts when cookies are sent with cross-site requests, helping prevent cross-site request forgery (CSRF) attacks.
	- **Domain/Path:** Control the scope of URLs to which the cookie is sent.

### REST Architecture
- **REST (Representational State Transfer):** An architectural style for designing networked applications. RESTful services exchange data in machine-friendly formats (such as JSON or XML), sending representations of objects rather than language-specific objects themselves. This enables interoperability between different systems (e.g., a Python app and a Java app) by using standard formats for communication. REST relies on stateless communication, resource-based URIs, and standard HTTP methods.
- **Principles:**
	- **Statelessness:** Each request from client to server must contain all the information needed to understand and process the request; the server does not store session state between requests.
	- **Client-Server Separation:** The client and server are independent; the client handles the user interface and user experience, while the server manages data and business logic.
	- **Cacheability:** Responses must define themselves as cacheable or not to prevent clients from reusing stale or inappropriate data.
	- **Layered System:** The architecture can be composed of hierarchical layers, with each layer only interacting with the adjacent ones, improving scalability and manageability.
	- **Uniform Interface:** A standardized way of interacting with resources, typically using HTTP methods and URIs, simplifies and decouples the architecture.

### JSON Structure
- **JSON (JavaScript Object Notation):** A lightweight, human-readable data format used for data interchange between clients and servers. Supports objects, arrays, strings, numbers, booleans, and null values.

### Session Management Concepts
- **Session:** A series of interactions between a client and server, often requiring state to be maintained for purposes such as user authentication, shopping carts, personalization, or tracking user activity across multiple requests.
- **Session Management:** Techniques include:
	- **Cookies:** Store session identifiers on the client and send them with each request; commonly used for web authentication and maintaining user state.
	- **Tokens (JWT):** JSON Web Tokens are self-contained tokens that encode user/session data and are sent with each request (often in headers); useful for stateless authentication in APIs and single-page applications.
	- **Server-side Session Stores:** The server maintains session data (e.g., in memory, databases, or distributed caches) and references it via a session ID sent to the client; useful for storing sensitive or large session data securely.
	- **URL Parameters:** Session identifiers are included in the URL query string; less secure and less common, but sometimes used when cookies are not available (e.g., in some embedded or legacy systems).

### Stateless vs. Stateful Applications
- **Stateless:** Each request is independent; the server does not retain client context between requests (preferred for scalability and reliability).
- **Stateful:** The server maintains client context across multiple requests (can simplify some workflows but reduces scalability).