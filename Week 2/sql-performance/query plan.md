# Query Plan Result Set Explained

## Optimal
```sql
SELECT
    e.EmployeeId,
    e.FirstName,
    e.LastName,
    COUNT(c.CustomerId) AS CustomersAssisted
FROM Employee e
JOIN Customer c
    ON e.EmployeeId = c.SupportRepId
GROUP BY
    e.EmployeeId,
    e.FirstName,
    e.LastName
ORDER BY
    CustomersAssisted ASC;
```
### Part 1
- `SCAN e`
    - SQLITE performs a full table scan of the `Employee` table
    - Each row from `Employee` serves as the **outer input** to the join
    - No filtering occurs at this stage because the query must evaluate all employees
    - This step is inexpensive due to the relatively small size of the `Employee` table, grows in expense as the size of the table increases


### Part 2
- `SEARCH c USING COVERING INDEX IFK_CustomerSupportRepId (SupportRepId=?)`
    - For each employee row, SQLITE searches for matching rows in the `Customer` table
    - The search uses the index on `Customer.SupportRepId`
    - The `?` placeholder represents the current `Employee.EmployeeId`
    - Because the index includes all required columns (`SupportRepId`, `CustomerId`):
        - No lookup back to the `Customer` table is required
        - This is a **covering index scan**
        - This step efficiently performs the JOIN and implicitly filters out employees without customers


### Part 3
- `USE TEMP B-TREE FOR ORDER BY`
    - The `ORDER BY CustomersAssisted` clause sorts on an aggregate value
    - Since `CustomersAssisted` does not exist until after grouping SQLITE cannot output rows in sorted order directly
    - A temporary B-tree is created to:
        1. Store the aggregated result set
        2. Sort rows by `CustomersAssisted` in ascending order
        3. Return the final sorted output
    - The temporary sort is low-cost because the result set is small (one row per employee), similar to the first table scan this step grows in computation and time cost the larger the dataset.


### Key Takeaways
- The JOIN + GROUP BY approach:
    - Evaluates customer data **once per employee**
    - Uses indexes efficiently during the join
    - Avoids redundant recalculations
- Aggregation and filtering happen naturally as part of the join process
- This plan minimizes repeated work and produces the most efficient execution strategy for this problem

## SubOptimal (Correlated Subquery)
```sql
SELECT
    e.EmployeeId,
    e.FirstName,
    e.LastName,
    (
        SELECT COUNT(*)
        FROM Customer c
        WHERE c.SupportRepId = e.EmployeeId
    ) AS CustomersAssisted
FROM Employee e
WHERE
    (
        SELECT COUNT(*)
        FROM Customer c
        WHERE c.SupportRepId = e.EmployeeId
    ) > 0
ORDER BY CustomersAssisted ASC;
```

### Part 1
- `SCAN e`
    - SQLITE performs a full table scan of the `Employee` table
    - Each employee row becomes the **input row** for one or more correlated subquery executions

### Part 2
- `CORRELATED SCALAR SUBQUERY 2`
    - Represents the subquery inside the `WHERE` clause
    - This subquery is executed **once per employee row**
    - Its purpose is to determine whether the employee assisted **at least one customer**

- `SEARCH c USING COVERING INDEX IFK_CustomerSupportRepId (SupportRepId=?)`
    - For the current employee, SQLITE searches the `Customer` table
    - Uses the index on `SupportRepId` instead of scanning all customers
    - This lookup returns the count needed for:
        ```sql
        WHERE (subquery) > 0
        ```
- Employees with zero matching customers are filtered out here

### Part 3
- `CORRELATED SCALAR SUBQUERY 1`
    - Represents the subquery inside the `SELECT` list
    - Also executed **once per surviving employee row**
    - Calculates the actual `CustomersAssisted` value returned in the result set

- `SEARCH c USING COVERING INDEX IFK_CustomerSupportRepId (SupportRepId=?)`
    - A second indexed lookup against `Customer`
    - This search repeats work already done by the WHERE‑clause subquery
    - Demonstrates why correlated subqueries are often considered *suboptimal*
    - Same index, same condition, executed a second time per employee

### Part 4
- `USE TEMP B-TREE FOR ORDER BY`
    - SQLITE cannot sort rows as they are produced
    - The `CustomersAssisted` value is computed **after** the subqueries run
    - SQLITE stores the intermediate result set in a temporary B-tree
    - The final output is sorted by `CustomersAssisted` and returned

## Key Takeaway
- The subquery version:
    - Executes **multiple indexed searches per employee**
    - Repeats logically identical work
- The JOIN + GROUP BY version:
    - Aggregates customer data **once**
    - Produces a simpler and more efficient execution plan
- Both queries return the same result set, but the execution strategies are fundamentally different