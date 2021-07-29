#Automated trace capturing

This is an application that takes a file with configurations and based on the parameters indicated in the files captures corresponding 
traces and loads them to file.
##Configuration
This application takes one command-line argument as its input. That argument should be
a valid `json` file with a corresponding pathname. It should contain the following fields.  

`hostname`- The name of your host.   
`start` - The time of the start of trace collection in milliseconds.  
`end` - The time of end of trace collection in milliseconds.  
`limit` - The amount of services, the application should capture from.  
`token` - The authorization token of the user.  

The `json` file should have the following format.
~~~
{
  "hostname": "https://meru.wavefront.com",
  "start": 1626280000,
  "end": 1626380000,
  "limit": 100,
  "token": "8112b2ca-67e2-452b-ae0f-b226a72ca01c"
}
~~~
If the `hostname` or `token` fields contain invalid information, the traces will not be returned. 
If the `limit` is less or equal to `0`, or if the difference between `end` and `start`
is less or equal to `0`, the output file will be empty.  
  
If you omit any fields from the structure noted above, those will be replaced 
by default parameters (the ones from the example above).