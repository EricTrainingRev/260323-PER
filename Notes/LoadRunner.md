# LoadRunner Notes

## PC & VuGen Basics


### Runtime Settings Overview

Runtime settings in VuGen define how each virtual user behaves during test execution, ensuring that scripts accurately simulate real user activity and system usage. Proper configuration of these settings is essential for producing meaningful, actionable performance test results.

#### Purpose of Runtime Settings
- Control the execution flow and behavior of virtual users
- Simulate realistic user actions and pacing
- Manage resource usage and logging for analysis
- Enable network and browser emulation for accurate test conditions

#### Key Runtime Settings Categories
Runtime settings are accessible in VuGen via **Replay > Runtime Settings** or in Controller per group. The main categories include:

- **General:** Think time, error handling
- **Run Logic:** Actions to execute, iteration count
- **Pacing:** Timing between iterations
- **Log:** Logging level and options
- **Network:** Bandwidth and speed simulation
- **Browser:** Browser type, cache, and cookies
- **Preferences:** Startup and cleanup options
- **Content Check:** Text and image checks for validation

These categories allow testers to tailor the test environment to closely match production conditions, improving the reliability of test outcomes.

**Example:**

Setting think time and pacing ensures that virtual users do not send requests in rapid succession, which could overwhelm the server and produce unrealistic results. By configuring network speed, you can simulate real-world conditions such as mobile or remote access.

### Recording

Recording is the foundational step in creating effective performance test scripts with VuGen. By capturing real user interactions, you ensure that your scripts accurately reflect production workflows and reveal genuine performance issues.

#### Purpose of Recording
- Capture user actions and application behavior for realistic test simulation
- Generate scripts that reflect actual business processes
- Provide a baseline for parameterization, correlation, and further script enhancement

#### The Recording Process
The typical recording workflow in VuGen consists of:
1. **Configure:** Select the appropriate protocol, set recording options, and choose the browser or client.
2. **Record:** Perform actions in the application while VuGen captures all relevant requests and responses.
3. **Review:** Examine the generated script, enhance code, and add validation as needed.

**Example:**
> Navigating to a URL is captured as `web_url("Homepage", "URL=https://...");`. Logging in or submitting forms is recorded as `web_submit_data` or `web_submit_form` calls.

#### Recording Modes
VuGen supports several recording approaches, depending on the protocol and application type:

| Mode              | Description                                 | Protocols                        |
|-------------------|---------------------------------------------|----------------------------------|
| Proxy Recording   | Routes traffic through VuGen proxy server   | Web HTTP/HTML, Web Services      |
| Browser-based     | Embeds recorder in browser session          | TruClient, Mobile Web            |
| Native Hooks      | Intercepts application calls directly       | SAP GUI, Citrix, Java            |
| Network Capture   | Captures network traffic at packet level    | Various protocols                |

#### Best Practices for Recording
- Plan your user journey before starting
- Clear browser cache to avoid capturing cached data
- Use realistic test data
- Perform actions at a normal, human pace
- Include think time between actions to simulate real users
- Record complete workflows from login to logout

### Transactions

Transactions are essential for measuring the performance of specific business processes or user actions within a VuGen script. By marking the start and end of key operations, you can collect precise response time metrics and identify bottlenecks in your application.

#### Purpose of Transactions
- Measure response times for critical business functions (e.g., login, search, checkout)
- Track the success or failure of specific operations
- Provide actionable data for performance analysis and reporting

#### Implementing Transactions in VuGen
Transactions are implemented using the `lr_start_transaction` and `lr_end_transaction` functions. Place these calls around the code that represents the business process you want to measure.

**Example:**
```c
// Start timing the search operation
lr_start_transaction("Search_Product");

// Perform search
web_submit_data("Search", ...);

// End timing
lr_end_transaction("Search_Product", LR_AUTO);
```

You can define multiple transactions within a script to measure different steps, such as adding items to a cart or completing a checkout.

#### Best Practices for Transactions
- Clearly define transaction boundaries to measure only the intended operation
- Use meaningful, descriptive names for transactions
- Review transaction results to identify slow or failing operations

### Understanding VuGen Correlation

Correlation is the process of handling dynamic values that change between sessions, such as session IDs, tokens, and other server-generated data. Proper correlation ensures that your scripts remain robust and replay successfully, even as the application generates new values for each user or session.

#### Why Correlation Matters
- Many applications generate unique values for each session or transaction
- Hardcoded values captured during recording will fail on replay
- Correlation extracts these dynamic values at runtime and uses them in subsequent requests

#### The Correlation Problem
When a script replays with hardcoded values, the server may reject requests due to invalid or expired data. Correlation solves this by capturing the correct value from the server response and inserting it into future requests.

**Example Scenario:**
> During recording, the server sends `sessionId = "ABC123"`, which is captured in the script. On replay, the server expects a new value (e.g., `sessionId = "XYZ789"`). Without correlation, the script sends the old value and fails. With correlation, the script captures and uses the new value, ensuring success.

#### Correlation Methods in VuGen
1. **Automatic Correlation:**
	 - VuGen can automatically detect and correlate common dynamic values.
	 - Recommended for most scenarios.
	 - Examples:
        -`set-cookie` header
        - `JSESSIONID`
        - `CFID`
    - NOTE: Vugen is very good about correlating common session data automatically, but always double check by replaying your scripts
2. **Manual Correlation:**
	 - For complex or custom cases, manually define correlation rules using boundaries or regular expressions.
	 - Example:
		 ```c
		 web_reg_save_param_ex(
				 "ParamName=csrfToken",
				 "LB=<input name=\"_csrf\" value=\"",
				 "RB=\"",
				 SEARCH_FILTERS,
				 "Scope=BODY",
				 LAST);
		 ```
         ```c
		 web_reg_save_param_json(
			 "ParamName=sessionId",
			 "QueryString=$.sessionId",
			 SEARCH_FILTERS,
			 LAST);
		 ```

#### Common Values Requiring Correlation
- Session IDs (e.g., `JSESSIONID`)
- CSRF tokens
- View states (e.g., `__VIEWSTATE`)
- Timestamps
- Transaction IDs

#### Best Practices for Correlation
- Identify all dynamic values that change between sessions
- Use automatic correlation where possible, but verify accuracy
- Manually correlate complex or custom values
- Test scripts thoroughly to ensure all correlations are handled

## VuGen Correlation & Parameterization

### Handling Dynamic Data

Handling dynamic data is a core skill in performance scripting with VuGen. Applications often generate values that change with every session or user, such as session IDs, tokens, timestamps, and user-specific data. To ensure scripts replay reliably and simulate real user behavior, you must address two key techniques: correlation and parameterization.

#### Correlation: Capturing Server-Generated Values
Correlation is the process of extracting dynamic values from server responses and using them in subsequent requests. Without correlation, scripts may fail because hardcoded values quickly become invalid.

**Example Problem:**
> During recording, the server sends `sessionId = "ABC123"`. On replay, the server expects a new value (e.g., `sessionId = "XYZ789"`). If the script sends the old value, the server rejects the request.

**Solution:**
Use correlation functions to capture the new value at runtime:
```c
// Extract sessionId from a JSON response using JSON extractor
web_reg_save_param_json(
	"ParamName=sessionId",
	"QueryString=$.sessionId",
	SEARCH_FILTERS,
	LAST);

// Use the captured value in a subsequent request
web_submit_data("NextRequest",
	...
	"Name=sessionId", "Value={sessionId}", ENDITEM,
	...);
```

**Common Values to Correlate:**
- Session IDs (e.g., `JSESSIONID`)
- CSRF tokens
- View states (e.g., `__VIEWSTATE`)
- Timestamps
- Transaction IDs

**Best Practices:**
- Use automatic correlation where possible, but always verify by replaying scripts
- Manually correlate complex or custom values using boundaries or JSONPath
- Test thoroughly to ensure all dynamic data is handled

#### Parameterization: Making Scripts Data-Driven
Parameterization replaces static (hardcoded) values in scripts with dynamic data, such as user credentials, search terms, or product IDs. This makes tests more realistic and prevents server-side caching from skewing results.

**Example:**
```c
// Before: Hardcoded value
web_submit_data("Search",
	"Action=https://shop.example.com/search",
	ITEMDATA,
	"Name=query", "Value=laptop", ENDITEM,
	LAST);

// After: Parameterized value
web_submit_data("Search",
	"Action=https://shop.example.com/search",
	ITEMDATA,
	"Name=query", "Value={SearchTerm}", ENDITEM,
	LAST);
```

**Parameter Types:**
- File (CSV/DAT): Usernames, passwords, product IDs
- Random/Unique Number: Order or transaction IDs
- Date/Time: Timestamps
- User Defined: Custom values for environment or test scenario

**Best Practices:**
- Use realistic, varied data to simulate real users
- Ensure parameter files have enough unique values for the test
- Choose appropriate update methods (sequential, random, unique)

### Script Enhancement

Script enhancement is the process of refining your VuGen scripts to make them more robust, maintainable, and reflective of real-world user behavior. Beyond basic recording and dynamic data handling, script enhancement ensures your tests deliver meaningful results and are easy to update as applications evolve.

#### Modular Script Structure
Organize scripts into logical sections:
- `vuser_init`: Initialization (e.g., login, setup)
- `Action`: Main business workflow (repeatable actions)
- `vuser_end`: Cleanup (e.g., logout, resource release)
For complex workflows, split actions into multiple files (e.g., `Action1.c`, `Action2.c`).

#### Use Think Time and Pacing
Simulate real user behavior by adding think time between actions and controlling pacing between iterations. This avoids unrealistic, machine-gun request patterns.
```c
// Pause for 3 seconds to simulate user reading
lr_think_time(3);
```

#### Parameterize and Correlate Thoroughly
Review scripts to ensure all static values are parameterized and all dynamic values are correlated. This maximizes realism and script reusability.

#### Comment and Document
Add comments to explain complex logic, correlation rules, and parameter usage. Well-documented scripts are easier to maintain and hand off to other testers.

By enhancing your scripts with these practices, you create reliable, maintainable, and insightful performance tests that provide real value to your projects.

### Transaction Response Measurement

Measuring transaction response times is a core objective of performance testing. In VuGen, transactions allow you to capture the duration of key business processes and validate that they meet performance requirements.

#### Defining Transactions
Wrap critical actions or workflows with `lr_start_transaction` and `lr_end_transaction` to measure their response times:
```c
lr_start_transaction("Login");
// Perform login steps
lr_end_transaction("Login", LR_AUTO);
```
You can define multiple transactions in a script to measure each important step (e.g., search, add to cart, checkout).

#### Analyzing Results
After test execution, analyze transaction response times and pass/fail rates in the LoadRunner Analysis tool or Controller. Look for:
- Average, minimum, and maximum response times
- Percentiles (e.g., 90th, 95th)
- Number and percentage of failed transactions

#### Best Practices
- Clearly define transaction boundaries around user-facing actions
- Use descriptive names for easy reporting
- Validate success before ending transactions
- Investigate and address slow or failing transactions

Accurate transaction measurement provides actionable insights into application performance and helps ensure you meet service level objectives (SLOs).

## Controller & Analysis

### Scenario Creation

Scenario creation in LoadRunner Controller is the process of designing and configuring how your performance test will execute. A well-constructed scenario models real-world user activity, defines load patterns, and ensures meaningful results for analysis.

#### Purpose of Scenario Creation
- Simulate realistic user behavior and business processes
- Define how virtual users (VUsers) are distributed across scripts and load generators
- Control test duration, pacing, ramp-up, and ramp-down
- Enable measurement of system performance under various load conditions

#### Scenario Creation Workflow
1. **Select Scenario Type:**
	 - *Manual Scenario*: Assigns VUsers to scripts and load generators manually. Best for complex, multi-script tests.
	 - *Goal-Oriented Scenario*: Focuses on achieving a target (e.g., hits per second, transactions per second, or number of concurrent users). Controller automatically adjusts VUser count to meet the goal.
2. **Add Scripts:** Import VuGen scripts that represent different user journeys or business processes.
3. **Assign Load Generators:** Specify which machines will generate load for each script.
4. **Configure Groups:** Organize VUsers into groups, each with its own script, pacing, and runtime settings.
5. **Set Load Profile:** Define how VUsers are ramped up, sustained, and ramped down (e.g., gradual ramp-up, steady state, ramp-down).
6. **Schedule Actions:** Optionally, use the Scheduler to set precise timings for each group or the entire scenario.
7. **Define Test Duration:** Set how long the test will run (by time, iteration count, or until a goal is reached).
8. **Configure Monitoring:** Add system/resource monitors to track server and application health during the test.

#### Scenario Types
- **Manual Scenario:**
	- Full control over VUser distribution and pacing
	- Suitable for tests with multiple scripts or complex workflows
- **Goal-Oriented Scenario:**
	- Controller dynamically adjusts VUser count to meet a defined goal (e.g., 100 transactions/sec)
	- Useful for capacity planning and SLA validation

#### Best Practices
- Model scenarios based on real user behavior and business requirements
- Use descriptive names for scripts and groups
- Start with a small load to validate scenario setup before scaling up
- Monitor both application and infrastructure during tests
- Document scenario settings for repeatability and analysis

Effective scenario creation is the foundation of reliable performance testing, enabling you to uncover bottlenecks, validate SLAs, and deliver actionable insights to stakeholders.

### Ramp-Up Configuration

Ramp-up configuration defines how virtual users (VUsers) are gradually introduced to the system under test. Proper ramp-up helps simulate real-world load patterns, prevents sudden spikes, and reveals how the application handles increasing traffic.

#### Purpose of Ramp-Up
- Avoid overwhelming the system with an immediate full load
- Observe system behavior as load increases
- Identify bottlenecks that appear only under rising traffic
- Simulate realistic user arrival patterns

#### Common Ramp-Up Strategies
- **Linear Ramp-Up:** Add a fixed number of users at regular intervals (e.g., 10 users every minute)
- **Step Ramp-Up:** Increase users in defined steps, holding steady between increments (e.g., 20 users, hold 5 min, add 20 more)
- **Custom/Scheduled Ramp-Up:** Use the Scheduler to create complex patterns (e.g., slow start, rapid increase, plateau)

#### Configuring Ramp-Up in Controller
1. Open the **Scenario Schedule** (Design view)
2. Choose **VUser Initialization** and **Start** options:
	- *Start VUsers*: All at once or gradually
	- *Initialize*: Before or during ramp-up
3. Set the ramp-up rate (e.g., 5 VUsers every 30 seconds)
4. Optionally, use the **Scheduler** for advanced timing and group-specific ramp-up

**Example:**
> To ramp up 100 users over 10 minutes, set the rate to 10 users every minute. This provides a smooth increase and allows you to monitor system health as load grows.

#### Best Practices
- Start with a gentle ramp-up to catch early failures
- Match ramp-up patterns to expected production usage
- Monitor system metrics (CPU, memory, response time) during ramp-up
- Document ramp-up settings for repeatability

Thoughtful ramp-up configuration ensures your tests are realistic, reduces the risk of false failures, and provides valuable insights into system scalability and stability.

### Throughput vs Hits Per Second

Understanding the difference between throughput and hits per second is essential for interpreting performance test results in LoadRunner. These metrics provide distinct insights into system behavior and capacity.

#### Throughput
- **Definition:** The amount of data transferred between the client and server per unit of time, typically measured in kilobytes (KB) or megabytes (MB) per second.
- **What it Shows:** Network and server resource usage. High throughput indicates large data volumes are being sent/received, which can stress bandwidth and server processing.
- **When to Focus:** When testing file downloads, media streaming, or APIs with large payloads.

#### Hits Per Second
- **Definition:** The number of HTTP requests (hits) sent to the server per second, regardless of the size of each request.
- **What it Shows:** The frequency of interactions between clients and the server. High hits per second means the server is handling many requests, which can stress connection handling and request processing.
- **When to Focus:** When testing web applications, APIs, or scenarios with many small requests.

#### Key Differences
- Throughput measures data volume; hits per second measures request rate.
- A test can have high hits per second but low throughput (many small requests), or high throughput but low hits per second (few large requests).

**Example:**
> Downloading a 10 MB file once produces high throughput but low hits per second. Loading a web page with 100 small images produces high hits per second but may have lower throughput.

#### Best Practices
- Analyze both metrics together for a complete picture of system performance.
- Investigate sudden drops or spikes in either metric—they may indicate bottlenecks or issues.
- Align test goals with the metric most relevant to your application (e.g., throughput for file servers, hits per second for web servers).

### Result Interpretation

Interpreting test results is the final and most critical step in the performance testing process. LoadRunner provides a wealth of data—knowing how to analyze it helps you identify bottlenecks, validate SLAs, and communicate findings to stakeholders.

#### Key Metrics to Review
- **Transaction Response Times:** Average, minimum, maximum, and percentile (e.g., 90th, 95th) response times for each business process.
- **Throughput:** Data volume transferred per second (KB/s or MB/s).
- **Hits Per Second:** Number of HTTP requests sent per second.
- **Error Rate:** Percentage of failed transactions or requests.
- **System Resource Utilization:** CPU, memory, disk, and network usage on servers and load generators.

#### Steps for Effective Result Interpretation
1. **Validate Test Execution:**
	- Confirm the test ran as planned (correct load, duration, no major interruptions).
2. **Check for Errors:**
	- Review error logs and failed transactions. Investigate root causes (application, network, script issues).
3. **Analyze Response Times:**
	- Compare against SLAs or performance targets. Focus on slowest transactions and high percentiles.
4. **Review Throughput and Hits:**
	- Look for drops or spikes that may indicate bottlenecks or instability.
5. **Correlate with System Metrics:**
	- Align performance dips with resource utilization spikes to pinpoint constraints.
6. **Identify Bottlenecks:**
	- Look for patterns: Do response times increase with load? Are errors clustered at peak usage?
7. **Summarize Findings:**
	- Highlight key insights, risks, and recommendations for remediation or further testing.

#### Best Practices
- Use graphs and tables to visualize trends and outliers
- Focus on user-facing transactions and business-critical flows
- Document all findings, including test conditions and anomalies
- Share results with both technical and non-technical stakeholders
