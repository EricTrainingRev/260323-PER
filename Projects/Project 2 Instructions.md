# Project Instructions: Advanced Performance Testing with Enterprise Resources

## Overview
This document provides step-by-step instructions for conducting advanced performance testing on the target application (Java Spring Boot) using enterprise-grade resources. These include LoadRunner and NeoLoad for test execution, and AppDynamics and Dynatrace for observability and monitoring. The process follows the Software Testing Life Cycle (STLC) and emphasizes best practices for large-scale, production-like environments.

## User Stories Under Test
- As a new user I can register an account to start tracking my todo tasks
- As a new user I can log in and out to securely access my todo items
- As a user I can create and edit todo items to keep track of my work
- As a user I can create and edit subtask items to better organize my primary tasks
- As a user I can see previously completed todo items so I can keep track of my finished tasks

## General Order of Operations (STLC)
1. **Requirement Analysis**
	- Review user stories and application features.
	- Identify performance-critical scenarios (e.g., registration, login, todo CRUD operations).
2. **Test Planning**
	- Define advanced performance goals (e.g., scalability, stability, resource utilization).
	- Select and configure enterprise resources (LoadRunner, NeoLoad, AppDynamics, Dynatrace).
3. **Test Case Design**
	- Design complex test plans for both UI and API endpoints.
	- Parameterize scripts for dynamic and large-scale data.
4. **Test Environment Setup**
	- Deploy and configure a scalable, production-like test environment.
	- Integrate observability tools for real-time monitoring.
5. **Test Execution**
	- Execute test scenarios using LoadRunner and/or NeoLoad.
	- Monitor application and infrastructure in real time with AppDynamics and Dynatrace.
6. **Result Analysis**
	- Collect and analyze test results and monitoring data.
	- Identify bottlenecks, resource constraints, and failure points.
7. **Reporting**
	- Document findings, metrics, and recommendations.
	- Summarize lessons learned and next steps.

## Metrics to Observe and Record
- **Response Time** (average, min, max, percentiles)
- **Throughput** (requests per second)
- **Error Rate** (percentage of failed requests)
- **Concurrent Users** (load profile)
- **Transaction Success/Failure Counts**

## Additional Notes
- Save all test plans, scripts, and result files for reference.
- Export and include dashboards or screenshots from enterprise observability tools.
- Document any deviations from the plan or unexpected findings.

---

## Presenting Enterprise Test Findings: Mini Stakeholder Presentation

When presenting your enterprise performance test results to stakeholders, focus on clarity, key insights, and actionable recommendations. Organize your findings as a concise, visually supported mini-presentation. Use the following structure:

### 1. Executive Summary
Briefly state the purpose of the advanced performance test and the main outcomes (e.g., "The purpose of this performance test was to evaluate the application's scalability and stability under production-like loads using enterprise resources. The main outcomes show that the application scales well up to 1,000 concurrent users, but resource saturation and slowdowns were observed during peak stress periods, particularly in the reporting module.").

### 2. Test Scope & Scenarios
- Summarize what was tested (user stories, endpoints, workflows).
- List the types of tests performed (baseline, load, stress, endurance).
- Specify which enterprise resources were used for each scenario.

### 3. Key Metrics & Visuals
- Present essential metrics: response times, throughput, error rates, resource utilization, and health indicators.
- Use charts, graphs, or screenshots from LoadRunner, NeoLoad, AppDynamics, and Dynatrace dashboards to illustrate trends and highlight issues.

### 4. Findings & Bottlenecks
- Highlight any performance issues, resource constraints, or failure points.
- Reference specific scenarios or user stories where problems were observed.
- Include insights from observability tools (e.g., memory leaks, CPU spikes, slow DB queries).

### 5. Recommendations
- Provide clear, actionable suggestions for improvement (e.g., optimize code, scale infrastructure, tune database, address specific bottlenecks).

### 6. Next Steps
- Outline follow-up actions, retesting plans, or areas for further investigation.

---

**Tips for Effective Stakeholder Presentations:**
- Keep slides or sections brief and focused.
- Use visuals (charts, tables, dashboards, screenshots) to make data easy to understand.
- Summarize technical details in plain language for non-technical stakeholders.
- End with a Q&A or discussion prompt if presenting live.
