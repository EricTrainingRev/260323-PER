Action()
{

	web_set_sockets_option("SSL_VERSION", "AUTO");

	web_add_header("Sec-Fetch-Site", 
		"none");

	web_add_auto_header("Sec-Fetch-Dest", 
		"document");

	web_add_auto_header("Sec-Fetch-Mode", 
		"navigate");

	web_add_auto_header("Sec-Fetch-User", 
		"?1");

	web_add_auto_header("Upgrade-Insecure-Requests", 
		"1");

	web_add_auto_header("sec-ch-ua", 
		"\"Google Chrome\";v=\"147\", \"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"147\"");

	web_add_auto_header("sec-ch-ua-mobile", 
		"?0");

	web_add_auto_header("sec-ch-ua-platform", 
		"\"Windows\"");

	lr_start_transaction("open login");

	web_url("localhost:8080", 
		"URL=http://localhost:8080/", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t1.inf", 
		"Mode=HTML", 
		LAST);

		lr_end_transaction("open login", LR_AUTO);


	web_add_auto_header("Sec-Fetch-Site", 
		"same-origin");

	lr_think_time(4);
		
	lr_start_transaction("open registration");

	web_link("Register here", 
		"Text=Register here", 
		"Snapshot=t2.inf", 
		LAST);

	lr_end_transaction("open registration", LR_AUTO);


	web_revert_auto_header("Sec-Fetch-User");

	web_revert_auto_header("Upgrade-Insecure-Requests");

	web_add_auto_header("Sec-Fetch-Mode", 
		"cors");

	web_add_auto_header("Sec-Fetch-Dest", 
		"empty");

	web_add_header("Origin", 
		"http://localhost:8080");
	
	lr_think_time(4);

	// LAST tells the script the argument list is done	
	web_reg_save_param_json("ParamName=username","QueryString=$.username",LAST);
	
	lr_start_transaction("register account");

	web_custom_request("register", 
		"URL=http://localhost:8080/register", 
		"Method=POST", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=http://localhost:8080/register.html", 
		"Snapshot=t3.inf", 
		"Mode=HTML", 
		"EncType=application/json", 
		"Body={\"username\":\"admin-{random_num}\",\"password\":\"admin\"}", 
		LAST);

		lr_end_transaction("register account", LR_AUTO);


	web_add_auto_header("Sec-Fetch-Mode", 
		"navigate");

	web_add_auto_header("Sec-Fetch-Dest", 
		"document");

	web_add_header("Sec-Fetch-User", 
		"?1");

	web_add_header("Upgrade-Insecure-Requests", 
		"1");
		
	lr_think_time(4);

	lr_start_transaction("open login");

	web_url("index.html", 
		"URL=http://localhost:8080/index.html", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://localhost:8080/register.html", 
		"Snapshot=t4.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("open login", LR_AUTO);


	web_add_auto_header("Sec-Fetch-Mode", 
		"cors");

	web_add_auto_header("Sec-Fetch-Dest", 
		"empty");

	web_add_header("Origin", 
		"http://localhost:8080");
	
	lr_think_time(4);
	
	lr_start_transaction("login");

	web_custom_request("login", 
		"URL=http://localhost:8080/login", 
		"Method=POST", 
		"Resource=0", 
		"Referer=http://localhost:8080/index.html", 
		"Snapshot=t5.inf", 
		"Mode=HTML", 
		"EncType=application/json", 
		"Body={\"username\":\"{username}\",\"password\":\"admin\"}", 
		LAST);

	lr_end_transaction("login", LR_AUTO);


	web_add_auto_header("Sec-Fetch-Mode", 
		"navigate");

	web_add_auto_header("Sec-Fetch-Dest", 
		"document");

	web_add_header("Sec-Fetch-User", 
		"?1");

	web_add_header("Upgrade-Insecure-Requests", 
		"1");

	lr_start_transaction("open dashboard");

	web_url("dashboard.html", 
		"URL=http://localhost:8080/dashboard.html", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://localhost:8080/index.html", 
		"Snapshot=t6.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("open dashboard", LR_AUTO);


	web_add_auto_header("Sec-Fetch-Dest", 
		"empty");

	web_add_auto_header("Sec-Fetch-Mode", 
		"cors");

	lr_start_transaction("get todos");

	web_url("todo", 
		"URL=http://localhost:8080/todo", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=http://localhost:8080/dashboard.html", 
		"Snapshot=t7.inf", 
		"Mode=HTML", 
		LAST);

		lr_end_transaction("get todos", LR_AUTO);


	web_add_header("Origin", 
		"http://localhost:8080");
	
	lr_think_time(4);

	lr_start_transaction("logout");

	web_custom_request("logout", 
		"URL=http://localhost:8080/logout", 
		"Method=POST", 
		"Resource=0", 
		"Referer=http://localhost:8080/dashboard.html", 
		"Snapshot=t8.inf", 
		"Mode=HTML", 
		"EncType=", 
		LAST);

	lr_end_transaction("logout", LR_AUTO);


	web_add_auto_header("Sec-Fetch-Dest", 
		"document");

	web_add_auto_header("Sec-Fetch-Mode", 
		"navigate");

	web_add_header("Sec-Fetch-User", 
		"?1");

	web_add_header("Upgrade-Insecure-Requests", 
		"1");

	lr_start_transaction("open login");

	web_url("index.html_2", 
		"URL=http://localhost:8080/index.html", 
		"Resource=0", 
		"Referer=http://localhost:8080/dashboard.html", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("open login", LR_AUTO);


	return 0;
}