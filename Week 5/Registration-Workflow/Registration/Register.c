Register()
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

	// Make sure to set the name of the transaction: this will show up in your reports later
	lr_start_transaction("Open Login Page");

	web_url("{URL}:{PORT}", 
		"URL={SCHEME}://{URL}:{PORT}/", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t1.inf", 
		"Mode=HTML", 
		LAST);

	// make sure to reference the correct transaction you are ending
	// LR_AUTO tells LoadRunner to automatically determine whether or not the transaction succeeded/failed
	// this will tie in more when we move to the controller and set up our "SLAs" in the controller
	lr_end_transaction("Open Login Page", LR_AUTO);


	web_add_auto_header("Sec-Fetch-Site", 
		"same-origin");
	
	// NOTE: in the think time configuration you are setting the think time for both your debug replay runs and
	// 		 also the controller led performance tests. Keep think time off during replay, turn it back on for
	//		 your actual tests	
	lr_think_time(4);
	
	lr_start_transaction("Open Register Page");

	web_link("Register here", 
		"Text=Register here", 
		"Snapshot=t2.inf", 
		LAST);
	
	lr_end_transaction("Open Register Page", LR_AUTO);


	web_revert_auto_header("Sec-Fetch-User");

	web_revert_auto_header("Upgrade-Insecure-Requests");

	web_add_header("Origin", 
		"{SCHEME}://{URL}:{PORT}");

	web_add_auto_header("Sec-Fetch-Dest", 
		"empty");

	web_add_auto_header("Sec-Fetch-Mode", 
		"cors");

	lr_think_time(4);
	
//  This uses the data from our CSV: works as an example for loading data from an external file
//	web_custom_request("register", 
//		"URL={SCHEME}://{URL}:{PORT}/register", 
//		"Method=POST", 
//		"Resource=0", 
//		"RecContentType=application/json", 
//		"Referer={SCHEME}://{URL}:{PORT}/register.html", 
//		"Snapshot=t3.inf", 
//		"Mode=HTML", 
//		"EncType=application/json", 
//		"Body={\"username\":\"{username}\",\"password\":\"{password}\"}", 
//		LAST);
	
	lr_start_transaction("Register Account");

	web_custom_request("register", 
		"URL={SCHEME}://{URL}:{PORT}/register", 
		"Method=POST", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer={SCHEME}://{URL}:{PORT}/register.html", 
		"Snapshot=t3.inf", 
		"Mode=HTML", 
		"EncType=application/json", 
		"Body={\"username\":\"test-user-{random_num}\",\"password\":\"password{random_num}\"}", 
		LAST);

	lr_end_transaction("Register Account", LR_AUTO);



	return 0;
}