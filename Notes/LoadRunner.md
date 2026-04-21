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