# AWS CloudWatch

## Monitoring EC2 Instances

### Overview
Amazon CloudWatch provides native monitoring and observability capabilities for Amazon EC2 instances, enabling visibility into compute performance, resource utilization, health, and operational behavior. CloudWatch integrates directly with EC2, offering infrastructure-level metrics by default and deeper host and application insights when the CloudWatch Agent is deployed. Its architecture emphasizes managed, scalable telemetry collection tightly coupled with AWS services.

### Core Components
- **CloudWatch Metrics:** Time-series metrics automatically collected for EC2 instances, such as CPU utilization, disk I/O, network throughput, and status checks.
    - *Default Metrics:* CPUUtilization, NetworkIn/Out, DiskRead/WriteOps, and instance status checks.
    - *Custom Metrics:* Additional OS-level or application metrics published via the CloudWatch Agent or API.
- **CloudWatch Agent:** An optional, unified agent installed on EC2 instances to collect detailed system-level metrics and logs.
    - *Capabilities:* Memory usage, disk space, filesystem metrics, process-level metrics, and log forwarding.
- **CloudWatch Logs:** Centralized log storage and analysis for system logs, application logs, and custom log sources from EC2.
- **CloudWatch Alarms:** Threshold- or anomaly-based alarms that evaluate metrics and trigger notifications or automated actions.
- **Amazon EventBridge (CloudWatch Events):** Enables event-driven responses to EC2 state changes such as instance start, stop, terminate, or reboot.
- **IAM Roles for EC2:** Grant permissions for instances to publish metrics and logs securely to CloudWatch without embedding credentials.

### Data Flow
1. **Instance Launch:** When an EC2 instance is launched, CloudWatch automatically begins collecting basic infrastructure metrics at a 5-minute interval (1-minute with detailed monitoring enabled).
2. **Agent-Based Collection (Optional):** The CloudWatch Agent is installed and configured to collect OS-level metrics and logs.
3. **Telemetry Ingestion:** Metrics and logs are securely published to CloudWatch endpoints using instance IAM roles.
4. **Storage & Analysis:** CloudWatch stores time-series metrics and log data, applies statistical aggregation, and enables querying and visualization.
5. **Alerting & Automation:** CloudWatch Alarms evaluate metrics and trigger SNS notifications, Auto Scaling actions, or event-driven workflows.

### Monitoring Capabilities
- **Infrastructure Health:** Monitor CPU pressure, network saturation, disk I/O bottlenecks, and instance reachability.
- **Operating System Visibility:** Gain insight into memory utilization, disk capacity, swap usage, and filesystem health via the CloudWatch Agent.
- **Log Centralization:** Aggregate application and system logs from multiple EC2 instances into log groups for searching and retention.
- **Anomaly Detection:** Use CloudWatch’s built-in anomaly detection to create dynamic thresholds based on historical behavior.
- **Elastic Scaling Integration:** Feed metrics directly into EC2 Auto Scaling groups to scale instances based on demand.

### Visualization & Analysis
- **CloudWatch Dashboards:** Create customizable dashboards to visualize EC2 metrics across regions, Auto Scaling groups, or environments.
- **Metric Math:** Combine and transform EC2 metrics to derive higher-level indicators such as total fleet CPU utilization.
- **Logs Insights:** Run structured queries against EC2 logs to troubleshoot errors, performance issues, or operational events.
- **Cross-Service Correlation:** Correlate EC2 metrics with other AWS services such as ELB, RDS, or EBS for end-to-end infrastructure analysis.

### Example
In a web application hosted on an EC2 Auto Scaling group, CloudWatch collects CPU utilization and network traffic metrics by default. The CloudWatch Agent is installed to capture memory usage and application logs. A CloudWatch Alarm detects sustained high CPU and triggers an Auto Scaling policy to launch additional instances. At the same time, Logs Insights queries reveal increased request latency recorded in application logs, helping engineers confirm that scaling resolved the performance issue.

## Dashboards & Alarms

### Overview
CloudWatch Dashboards and Alarms provide visualization and alerting layers on top of collected metrics and logs, enabling teams to monitor EC2 health, performance trends, and operational thresholds in near real time. Dashboards offer centralized views across resources and accounts, while alarms enable proactive notification and automated remediation based on metric behavior.

### CloudWatch Dashboards
- **Custom Dashboards:** User-defined dashboards composed of metric graphs, numbers, text widgets, and alarms.
- **Cross-Resource Visibility:** Visualize metrics across multiple EC2 instances, Auto Scaling groups, regions, and even accounts.
- **Real-Time Monitoring:** Dashboards refresh automatically and can display data at granularity down to 1 minute.
- **Metric Math & Aggregation:** Combine metrics (e.g., average CPU across a fleet) or derive higher-level indicators.
- **Sharing & Access Control:** Dashboards can be shared with IAM-controlled access or published for read-only visibility.

**Common EC2 Dashboard Use Cases**
- Fleet-level CPU, memory, and network utilization
- Instance status checks and availability tracking
- EBS disk throughput and I/O latency
- Auto Scaling group capacity and scaling activity

### CloudWatch Alarms
- **Threshold Alarms:** Trigger when a metric crosses a static threshold for a defined number of evaluation periods.
- **Anomaly Detection Alarms:** Use machine learning models to detect metric behavior that deviates from normal baselines.
- **Composite Alarms:** Combine multiple alarms into higher-level health signals to reduce alert noise.
- **Evaluation Logic:** Alarms evaluate metrics using statistics such as Average, Sum, Minimum, or Maximum.

### Alarm Actions
- **Notifications:** Send alerts via Amazon SNS to email, SMS, or incident management tools.
- **Auto Scaling Actions:** Increase or decrease EC2 instance count based on demand.
- **EC2 Actions:** Recover impaired instances or stop/terminate instances automatically.
- **Event-Driven Automation:** Alarms can trigger workflows through EventBridge or AWS Systems Manager.

### Best Practices
- Alarm on symptoms (latency, errors, saturation) rather than raw metric spikes alone.
- Use anomaly detection for variable workloads with seasonal patterns.
- Aggregate metrics at the service or Auto Scaling group level to avoid per-instance alert fatigue.
- Pair alarms with dashboards for fast visual context during incident response.

### Example
A CloudWatch dashboard displays average CPU, memory usage, and request count for an EC2 Auto Scaling group. A high-CPU alarm with anomaly detection triggers when usage exceeds normal patterns and sends a notification via SNS. At the same time, a scaling policy adds instances automatically. Engineers use the dashboard to confirm recovery and validate system stability.