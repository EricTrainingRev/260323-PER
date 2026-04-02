/*
	Unlike a programing language like Java that will execute your code (after it is compiled) line by
	line, there is a differnce between how an SQL query is written and how the database engine parses
	and executes the query you sent.
	
	The general (non-exhaustive) order of execution:
	- FROM: 	despite coming later in most written queries, the FROM statement is actually the first
				part of any query the database parses. If there is any issue with the table data there is
				no point in parsing any other parts of your query
				
	- WHERE:	Once the table is known the database checks if there are any conditions for what rows
				of data are needed as part of the query so that any data returned matches your WHERE
				condition
				
	- GROUP BY:	Once the WHERE filtering is complete then database engine proceeds to perform grouping
				based on our GROUP BY statement
				
	- HAVING:	After grouping is done any further filtering of results needs to be handled, which is
				where your HAVING statement comes into play. This is also why there are both the WHERE
				and HAVING keywords: they perform filtering at different parts of the query execution
				process
	
	- SELECT:	After all that organizing, the database finally grabs all the records that meet the
				criteria of your select statement based on all the previous filtering done
				
	- DISTINCT:	Any duplicates are removed if distinct values are requested
	
	- ORDER BY:	After distinction is determined any ordering of the data is performed
	
	- LIMIT/OFFSET:	Once the ordering is done any final limitations or offsets to the final result set
					are determined before the result set is returned to the user
*/

/*
 	If you ever want to see how a database will handle a query you make you can pre-pend your query with
 	EXPLAIN QUERY PLAN. This tells the database engine you want to get a result set back that explains
 	how the database engine would handle the query instead of actually running the query. It provides
 	a quick snapshot you can peruse to assess whether your query is written optimally or not
 	
 	This is possible because database engines include something called a query optimizer: this tool is
 	used as part of the query parsing process and the optimizer comes up with a plan to execute your
 	query when it is sent to the database. You can see the plan for a query's execution by using the
 	EXPLAIN QUERY PLAN statement
 */
-- JOIN EXAMPLE
EXPLAIN QUERY PLAN
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
















