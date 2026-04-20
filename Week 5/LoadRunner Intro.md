# What is LoadRunner?
LoadRunner is actually a collection of three different tools:
1. Virtual User Generator (VuGen)
2. Controller
3. Analysis

This is also the flow of testing: you create your test scripts using VuGen, then run those tests using Controller, and then use Analysis to generate your reports.

## VuGen Notes
- When creating a new test script make sure to also create a solution to go with that script
- the Solution explorer is just that: an explorer. It does not have execution order of operation implications like the JMeter test plan explorer does. Test execution is managed elsewhere.
- VuGen test scripts are written in C: the ui will provide ways to inject the scripting you need for most things we need to do, but power users can write their own functions and utilize the language to enhance their scripts
- VuGen gives you starting setup and teardown scripts: you can make use of these if it makes sense or ignore them
    - the init and end scripts only run once each: init at the start of the test and end at the end of the test
- When setting up your recording you want to make sure to configure your recording filter to only record the requests you need for your test
    - for localhost: `^(?!localhost$).*$`
- After finishing your recording the script you targeted with your recording will be updated and reflect the actions you took during the recording session