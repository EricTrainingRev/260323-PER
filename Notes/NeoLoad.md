# NeoLoad Notes


## Recording

Recording is the essential first step in building effective performance test scripts with NeoLoad. By capturing real user interactions, you ensure your scripts accurately reflect production workflows and uncover genuine performance issues.

### Purpose of Recording
- Capture user actions and application behavior for realistic test simulation
- Generate scripts that mirror actual business processes
- Provide a foundation for parameterization, correlation, and script enhancement

### The Recording Process
The typical recording workflow in NeoLoad consists of:
1. **Configure:** Select the appropriate browser or application, set up proxy or browser-based recording, and define recording options.
2. **Record:** Perform actions in the application while NeoLoad captures all relevant HTTP(S) requests and responses.
3. **Review:** Examine the generated user path, enhance the script, and add validations as needed.

**Example:**
> Navigating to a URL is captured as an HTTP request in the user path. Logging in or submitting forms is recorded as a sequence of HTTP requests with associated parameters and payloads.

### Recording Modes
NeoLoad supports several recording approaches, depending on the application type and protocol:

| Mode                | Description                                   | Use Cases                        |
|---------------------|-----------------------------------------------|----------------------------------|
| Proxy Recording     | Routes traffic through NeoLoad proxy server   | Web, Mobile, API                 |
| Browser-based       | Launches a browser with NeoLoad extension     | Web applications                 |
| Native Application  | Captures traffic from desktop/mobile apps     | Thick clients, mobile apps       |
| Manual Import       | Import HAR or Fiddler files                   | Pre-captured traffic             |

### Key Concepts
- **Proxy Recording:** NeoLoad acts as a proxy to intercept and log HTTP(S) traffic between the client and server.
- **Session Handling:** Captured traffic includes session cookies or tokens that must be managed in the user path.
- **Headers and Cookies:** Pay attention to custom headers, authentication tokens, and cookies, as these are critical for accurate test replay.
- **Dynamic Data Correlation:** Identify and extract dynamic values from responses to use in subsequent requests, ensuring scripts remain valid across test runs.

### Best Practices for Recording
- Plan your user journey before starting
- Clear browser cache and cookies to avoid capturing stale data
- Use realistic test data
- Perform actions at a normal, human pace
- Include think time between actions to simulate real users
- Record complete workflows from login to logout


## User Path Definition

User Path Definition in NeoLoad is the process of designing and organizing the sequence of actions that a virtual user will perform during a test. A well-structured user path ensures your performance tests accurately simulate real user behavior and business workflows.

### Purpose of User Path Definition
- Model realistic user journeys through the application
- Define the order and logic of requests and actions
- Enable parameterization, correlation, and validation within the workflow
- Support modular, maintainable, and reusable test design

### Key Concepts
- **Actions:** Each user path consists of a series of actions (e.g., login, browse, purchase) that represent business processes.
- **Containers:** Use containers to group related actions, such as transactions or logical steps, for better organization and reporting.
- **Loops and Conditions:** Add loops, conditions, and logical branches to simulate complex user behavior (e.g., repeat browsing, conditional checkout).
- **Think Time:** Insert think times between actions to mimic real user pacing and avoid unrealistic request bursts.
- **Variables:** Use variables to store and reuse dynamic data (e.g., session IDs, tokens) throughout the user path.

### Typical Workflow
1. **Review Recorded User Path:** After recording, review the generated user path in NeoLoad’s design interface.
2. **Organize Actions:** Group related requests into containers (e.g., Login, Search, Checkout) for clarity and modularity.
3. **Add Logic:** Insert loops, conditions, and logical branches to reflect real user scenarios.
4. **Parameterize and Correlate:** Replace static values with variables and add extractors for dynamic data.
5. **Insert Think Time:** Add delays between actions to simulate user reading or decision time.
6. **Validate Workflow:** Ensure the user path accurately represents the intended business process and is robust against dynamic data changes.

**Example Structure:**
```
User Path: Purchase Workflow
├── Container: Login
│   ├── HTTP Request: Open Login Page
│   └── HTTP Request: Submit Credentials
├── Container: Browse
│   ├── HTTP Request: View Product List
│   └── HTTP Request: View Product Details
├── Container: Purchase
│   ├── HTTP Request: Add to Cart
│   └── HTTP Request: Checkout
└── Container: Logout
	└── HTTP Request: Sign Out
```

### Best Practices for User Path Definition
- Organize actions logically using containers and meaningful names
- Use loops and conditions to simulate realistic, varied user behavior
- Parameterize all user-specific and dynamic data
- Insert think times to avoid unrealistic load patterns
- Regularly review and update user paths as application workflows evolve


## Parameterization

Parameterization in NeoLoad is the process of replacing static (hardcoded) values in your test scripts with dynamic data, such as user credentials, search terms, or product IDs. This makes your tests more realistic, prevents server-side caching from skewing results, and simulates real user variability.

### Purpose of Parameterization
- Simulate real-world user behavior with varied data
- Avoid server-side caching and duplicate data issues
- Enable data-driven testing for broader coverage
- Support scalability by providing unique values for each virtual user

### Key Concepts
- **Variables:** NeoLoad uses variables to store and inject dynamic data into requests (e.g., usernames, passwords, product IDs).
- **Variable Types:** Common types include File (CSV), Random, List, Date/Time, and User-Defined variables.
- **Variable Policies:** Control how data is assigned to users (e.g., sequential, random, unique per user).
- **Parameterization Scope:** Variables can be used at the user path, container, or request level for fine-grained control.

### Typical Workflow
1. **Identify Static Values:** Review your user path for hardcoded values that should be parameterized (e.g., login names, search queries).
2. **Create Variables:** Define variables in NeoLoad (e.g., import a CSV file with user data, create a random number variable).
3. **Replace Static Values:** Substitute hardcoded values in requests with variable references.
4. **Configure Variable Policies:** Set how NeoLoad assigns data to each virtual user (sequential, random, unique, etc.).
5. **Validate Parameterization:** Run the test to ensure variables are correctly injected and the workflow remains valid.

**Example:**
> Instead of using a fixed username in a login request, create a variable linked to a CSV file containing multiple usernames. Reference this variable in the request so each virtual user logs in with a different account.

### Common Variable Types
- **File Variable:** Reads values from a CSV or text file (e.g., usernames, passwords, product IDs)
- **Random Variable:** Generates random numbers or strings for each user or request
- **List Variable:** Selects values from a predefined list
- **Date/Time Variable:** Inserts current or formatted date/time values
- **User-Defined Variable:** Custom values for environment or scenario-specific data

### Best Practices for Parameterization
- Use realistic, varied data to simulate real users
- Ensure data files (CSV) have enough unique values for the test scale
- Choose appropriate variable policies (sequential, random, unique) based on test goals
- Parameterize all user-specific and dynamic data fields
- Validate parameterization by running small-scale tests before full execution


## Correlation

Correlation in NeoLoad is the process of handling dynamic values that change between sessions or users, such as session IDs, tokens, and other server-generated data. Proper correlation ensures your scripts remain robust and replay successfully, even as the application generates new values for each user or session.

### Purpose of Correlation
- Handle dynamic values that change between sessions (e.g., session IDs, CSRF tokens)
- Ensure scripts replay reliably and simulate real user behavior
- Prevent failures caused by hardcoded or expired data
- Support workflows that depend on server-generated values

### Key Concepts
- **Dynamic Values:** Many applications generate unique values for each session or transaction (e.g., authentication tokens, view states, timestamps).
- **Extractors:** NeoLoad uses extractors (e.g., regular expression, JSONPath, XPath) to capture dynamic values from server responses.
- **Variable Reuse:** Captured values are stored in variables and reused in subsequent requests to maintain session continuity and workflow validity.
- **Automatic vs. Manual Correlation:** NeoLoad can automatically detect and suggest correlations, but manual configuration is often needed for complex or custom cases.

### Typical Workflow
1. **Identify Dynamic Fields:** Review recorded requests and responses for values that change between sessions (e.g., session IDs, tokens).
2. **Create Extractors:** Add extractors to capture dynamic values from server responses (e.g., use a JSONPath extractor to capture a token).
3. **Replace Hardcoded Values:** Substitute static values in subsequent requests with variable references to the extracted data.
4. **Validate Correlation:** Replay the script to ensure dynamic values are correctly captured and used, and that the workflow remains valid.

**Example:**
> After logging in, the server returns a JSON response containing a session token. Use a JSONPath extractor to capture the token and reference it in all subsequent requests that require authentication.

### Common Values Requiring Correlation
- Session IDs (e.g., `JSESSIONID`)
- CSRF tokens
- View states (e.g., `__VIEWSTATE`)
- Timestamps
- Transaction IDs

### Best Practices for Correlation
- Identify all dynamic values that change between sessions or users
- Use automatic correlation suggestions as a starting point, but verify accuracy
- Manually configure extractors for complex or custom values
- Test scripts thoroughly to ensure all correlations are handled
- Document correlation rules for maintainability

### Scenario Setup

Scenario Setup in NeoLoad is the process of designing and configuring how your performance test will execute. A well-constructed scenario models real-world user activity, defines load patterns, and ensures meaningful results for analysis.

#### Purpose of Scenario Setup
- Simulate realistic user behavior and business processes
- Define how virtual users (VUs) are distributed across user paths and load generators
- Control test duration, pacing, ramp-up, and ramp-down
- Enable measurement of system performance under various load conditions

#### Key Concepts
- **Populations:** Groups of virtual users assigned to specific user paths and load profiles
- **Load Generators:** Machines that generate load during the test
- **Load Policy:** Defines how users are ramped up, sustained, and ramped down (e.g., gradual ramp-up, steady state, ramp-down)
- **Scheduling:** Set precise timings for each population or the entire scenario
- **Monitoring:** Track server and application health during the test

#### Typical Workflow
1. **Create Populations:** Assign user paths to populations and define the number of virtual users for each
2. **Assign Load Generators:** Specify which machines will generate load for each population
3. **Configure Load Policy:** Set how users are ramped up, maintained, and ramped down (e.g., linear, step, custom)
4. **Schedule Actions:** Optionally, use the scheduler to set timings for each population
5. **Set Test Duration:** Define how long the test will run (by time, iteration count, or until a goal is reached)
6. **Configure Monitoring:** Add monitors to track system and application health during the test

**Example:**
> To ramp up 200 users over 20 minutes, configure a population with a linear ramp-up policy of 10 users per minute. Monitor system metrics throughout the test to identify bottlenecks as load increases.

#### Scenario Types
- **Simple Scenario:** Single population, straightforward load profile
- **Complex Scenario:** Multiple populations, varied user paths, and custom load profiles

#### Best Practices for Scenario Setup
- Model scenarios based on real user behavior and business requirements
- Use descriptive names for populations and user paths
- Start with a small load to validate scenario setup before scaling up
- Monitor both application and infrastructure during tests
- Document scenario settings for repeatability and analysis

Effective scenario setup is the foundation of reliable performance testing, enabling you to uncover bottlenecks, validate SLAs, and deliver actionable insights to stakeholders.


## Load Policy Configuration

Load Policy Configuration in NeoLoad defines how virtual users are introduced, maintained, and removed during a test. Proper load policy setup ensures your tests accurately simulate real-world traffic patterns and reveal system behavior under varying load conditions.

### Purpose of Load Policy Configuration
- Control the rate at which users are ramped up, sustained, and ramped down
- Simulate realistic user arrival and departure patterns
- Identify system bottlenecks and performance thresholds
- Support a variety of test scenarios (e.g., stress, endurance, spike, and baseline tests)

### Key Concepts
- **Ramp-Up:** Gradually increase the number of virtual users to avoid sudden spikes and observe system response to growing load
- **Steady State:** Maintain a constant number of users for a defined period to measure sustained performance
- **Ramp-Down:** Gradually decrease the number of users to observe system recovery and stability
- **Custom Patterns:** Configure step, linear, or custom user arrival patterns to match business requirements

### Typical Workflow
1. **Select Population:** Choose the population(s) to configure load policy for
2. **Set Ramp-Up:** Define how quickly users are added (e.g., 10 users every minute)
3. **Configure Steady State:** Specify the duration and number of users for the sustained load phase
4. **Set Ramp-Down:** Define how users are removed at the end of the test
5. **Combine Patterns:** Use multiple populations or custom schedules for complex scenarios
6. **Validate Load Policy:** Run small-scale tests to ensure the load pattern matches expectations

**Example:**
> To simulate a typical workday, ramp up 100 users over 10 minutes, hold steady for 1 hour, then ramp down over 5 minutes. For a spike test, add all users at once and observe system response.

### Best Practices for Load Policy Configuration
- Start with gentle ramp-up to catch early failures
- Match load patterns to expected production usage
- Monitor system metrics (CPU, memory, response time) throughout all phases
- Document load policy settings for repeatability and analysis
- Use custom patterns for advanced scenarios (e.g., spikes, waves, or gradual increases)


## Runtime Configuration

Runtime Configuration in NeoLoad defines how each virtual user behaves during test execution, ensuring scripts accurately simulate real user activity and system usage. Proper configuration of these settings is essential for producing meaningful, actionable performance test results.

### Purpose of Runtime Configuration
- Control the execution flow and behavior of virtual users
- Simulate realistic user actions, pacing, and think times
- Manage resource usage, error handling, and logging for analysis
- Enable network and browser emulation for accurate test conditions

### Key Concepts
- **Think Time:** Simulate user reading or decision time between actions
- **Pacing:** Control the timing between iterations of a user path
- **Error Handling:** Define how NeoLoad handles errors (e.g., continue, stop user, stop test)
- **Network Emulation:** Simulate different network conditions (e.g., bandwidth, latency)
- **Browser Emulation:** Mimic browser types, cache, and cookie behavior
- **Logging and Monitoring:** Configure the level of detail for logs and enable monitoring for troubleshooting

### Typical Workflow
1. **Set Think Time:** Add realistic delays between actions to mimic user behavior
2. **Configure Pacing:** Define how often each virtual user repeats the user path
3. **Adjust Error Handling:** Choose how errors are managed during test execution
4. **Enable Network/Browser Emulation:** Simulate real-world client environments as needed
5. **Set Logging Preferences:** Adjust log detail for debugging or analysis
6. **Validate Runtime Settings:** Run a small-scale test to ensure settings produce realistic and reliable results

**Example:**
> Add a 3-second think time after each page load to simulate user reading, set pacing to repeat the scenario every 5 minutes, and configure error handling to stop the user on critical failures.

### Best Practices for Runtime Configuration
- Use realistic think times and pacing to avoid unrealistic load patterns
- Match network and browser settings to target user environments
- Set error handling policies appropriate for your test goals
- Monitor logs and system metrics to identify issues early
- Document runtime settings for repeatability and future reference


## SLA Threshold Setup

SLA (Service Level Agreement) Threshold Setup in NeoLoad allows you to define performance targets for your application, such as maximum response times, error rates, or throughput. Setting SLAs ensures your tests measure what matters most to your business and provides clear pass/fail criteria for performance validation.

### Purpose of SLA Threshold Setup
- Define measurable performance goals for critical transactions and business processes
- Automate pass/fail evaluation of test results
- Identify when application performance does not meet business expectations
- Support reporting and communication with stakeholders

### Key Concepts
- **SLA Profiles:** Collections of thresholds applied to populations, user paths, or specific requests
- **Threshold Types:** Common thresholds include response time (average, percentile, max), error rate, and throughput
- **Scope:** SLAs can be set globally, per population, or for individual transactions
- **Automated Evaluation:** NeoLoad automatically checks results against SLAs and flags violations

### Typical Workflow
1. **Identify Critical Metrics:** Determine which transactions, user paths, or populations require SLAs (e.g., login, checkout)
2. **Define Thresholds:** Set specific targets for response time, error rate, or throughput (e.g., 95% of logins < 2s, error rate < 1%)
3. **Assign SLA Profiles:** Apply SLA profiles to relevant populations or requests in your scenario
4. **Run the Test:** Execute your scenario and let NeoLoad evaluate results against the defined SLAs
5. **Review SLA Results:** Analyze which SLAs passed or failed and investigate any violations

**Example:**
> Set an SLA that 95% of checkout transactions must complete in under 3 seconds, and the error rate for login must remain below 1%.

### Best Practices for SLA Threshold Setup
- Align SLAs with business and user expectations
- Use percentiles (e.g., 90th, 95th) for response time thresholds to capture real user experience
- Set realistic, achievable targets based on historical data or industry standards
- Regularly review and update SLAs as application requirements evolve
- Document all SLA settings for transparency and repeatability


## Result Analysis

Result Analysis in NeoLoad is the process of interpreting test results to identify bottlenecks, validate SLAs, and communicate findings to stakeholders. Effective analysis helps you understand system behavior under load and guides performance improvements.

### Purpose of Result Analysis
- Validate that the test executed as planned and met its objectives
- Identify performance bottlenecks, errors, and trends
- Compare results against SLAs or performance targets
- Provide actionable insights for technical and business audiences

### Key Concepts
- **Transaction Response Times:** Analyze average, minimum, maximum, and percentile (e.g., 90th, 95th) response times for each business process
- **Throughput:** Measure data volume transferred per second (KB/s or MB/s)
- **Hits Per Second:** Track the number of HTTP requests sent per second
- **Error Rate:** Monitor the percentage of failed transactions or requests
- **System Resource Utilization:** Correlate application performance with CPU, memory, disk, and network usage
- **Graphs and Tables:** Use NeoLoad’s built-in charts and tables to visualize trends and outliers

### Typical Workflow
1. **Validate Test Execution:** Confirm the test ran as planned (correct load, duration, no major interruptions)
2. **Check for Errors:** Review error logs and failed transactions. Investigate root causes (application, network, script issues)
3. **Analyze Response Times:** Focus on slowest transactions and high percentiles to identify user experience issues
4. **Review Throughput and Hits:** Look for drops or spikes that may indicate bottlenecks or instability
5. **Correlate with System Metrics:** Align performance dips with resource utilization spikes to pinpoint constraints
6. **Identify Bottlenecks:** Look for patterns such as increasing response times with load or errors clustered at peak usage
7. **Summarize Findings:** Highlight key insights, risks, and recommendations for remediation or further testing

**Example:**
> After a test run, you notice a spike in response times and errors during peak load. By correlating these with CPU usage graphs, you identify a server bottleneck that requires scaling or optimization.

### Best Practices for Result Analysis
- Use graphs and tables to visualize trends and outliers
- Focus on user-facing transactions and business-critical flows
- Document all findings, including test conditions and anomalies
- Share results with both technical and non-technical stakeholders
- Archive results and reports for future reference and trend analysis


## Error Diagnostics

Error Diagnostics in NeoLoad is the process of identifying, investigating, and resolving issues that occur during performance testing. Effective diagnostics help you quickly pinpoint root causes, reduce test flakiness, and improve application reliability.

### Purpose of Error Diagnostics
- Detect and categorize errors encountered during test execution
- Investigate root causes of failures (application, network, script, or environment)
- Provide actionable information for troubleshooting and remediation
- Improve the reliability and accuracy of performance tests

### Key Concepts
- **Error Types:** Common errors include HTTP errors (4xx, 5xx), timeouts, connection failures, and application-specific errors
- **Error Logs:** NeoLoad provides detailed logs and error messages for each failed request or transaction
- **Correlation with Metrics:** Analyze errors alongside response times, throughput, and system resource metrics to identify patterns
- **Reproducibility:** Isolate and reproduce errors to confirm root causes and validate fixes

### Typical Workflow
1. **Review Error Summary:** Use NeoLoad’s error summary reports to identify the most frequent and critical errors
2. **Drill Down into Logs:** Examine detailed logs for failed requests, including request/response data and error codes
3. **Correlate with Test Events:** Align error occurrences with load patterns, ramp-up phases, or specific user actions
4. **Investigate Root Causes:** Determine whether errors are due to application bugs, environment issues, network instability, or script problems
5. **Reproduce and Validate:** Attempt to reproduce errors in isolation and validate fixes before re-running large tests

**Example:**
> During a test, you observe a spike in HTTP 500 errors during peak load. By reviewing logs and correlating with CPU usage, you identify a backend service crash as the root cause.

### Best Practices for Error Diagnostics
- Categorize errors by type and frequency to prioritize investigation
- Use detailed logs and request/response data for troubleshooting
- Correlate errors with system and application metrics for deeper insights
- Document all findings and resolutions for future reference
- Continuously refine scripts and environment to reduce recurring errors
