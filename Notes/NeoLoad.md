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
