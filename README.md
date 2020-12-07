# com.jtconnors.cgminerapi
Java version of the cgminer RPC API version 4.10.0, referenced by https://github.com/ckolivas/cgminer/blob/v4.10.0/API-README.  This project enables Java applications and frameworks to access running ```cgminer``` instances.  The API has facilities for both querying and manipulating mining (e.g bitcoin, etherium ...) activity.

Of note, the following maven goals can be executed to clean and build the software:

   - ```mvn clean```
   - ```mvn dependency:copy-dependencies``` - to pull down dependencies, most notably the ```javax.json``` dependency
   - ```mvn package``` - to create the ```target/com.jtconnors.cgminerapi-4.10.0.jar``` file

## Running the Sample Program
A sample program demonstrating a small number of basic API invocations can be found in ```src/main/java/com/jtconnors/cgminerapi/Samples.java``` source file.  The ```Samples``` program must connect up to a running ```cgminer``` instance and accordingly accepts two optional command-line arguments as follows:

  - ```-cgminerHost:HOSTNAME``` - (default: localhost)
  Specify hostname (or IP Address) of the running cgminer instance.  This may have to be modified to match the hostname of your cgminer instance.
  - ```-cgminerPort:PORT_NUMBER```  - (default 4028) 
  Specify the port number used to communicate with a running cgminer instance.  Chances are this will remain unchanged.

The ```Samples``` class can run from maven by issuing:  
- ```mvn exec:java ``` -or-
- ```mvn exec:java@default-cli``` 
  
**Before doing so, you'll most liekly want to modify the aforementioned ```cgminerHost``` argument**.  The most straightforward way of doing this is by editing the ```pom.xml``` file and looking for a property called ```cgminerHost```:
```xml
<properties>
  ...
  <cgminerHost>jtconnors.com</cgminerHost>
  <cgminerPort>4028</cgminerPort>
  ...
</properties>
```
Additionally the following script can be run from a terminal to run the Sample program.  Command-line arguments can be modified inside these scripts, if necessary:
- ```sh/run-samples.sh``` or ```ps1\run-samples.ps1```


## Notes:
- The scripts referred to above have a few available command-line options. To print out the options, add ```-?``` or ```--help``` as an argument to any script.
- The scripts share common properties that can be found in ```env.sh``` or ```env.ps1```. These may need to be slightly modified to match your specific configuration.
- A sample ```Microsoft.PowerShell_profile.ps1``` file has been included to help configure a default Powershell execution environment. A similar file can be generated specific to environments appropriate for running the ```bash(1)``` shell with a ```.bash_login``` or ```.bash_profile file.```

## See also:

- cgminer project on GitHub - https://github.com/ckolivas/cgminer
