# konux-case-study

To run application:
In a console, run 'java -jar LogApp.jar' in the main Git folder.

To open and study applications:
- For the Java application:
	- Use Intellij to open the project
	- In case you are using other IDE or no IDE at all, you can build the final JAR file, but also include all the JARs and source files from lib.
	- It is important to have the C++ DLL file (LogApp_Cpp_Part.dll) in the same location as the final JAR file
	- In order to stop the application, you can use 'CTRL-C' combination in the console
	
- For the C++ application:
	- Use Visual Studio 2013 to open the project
	- In case you are using other IDE or no IDE at all, you can build a DLL application for C++, using all source files and 'libprotobuf' from lib as library
	- After you finish, you can move the DLL to the location of the JAR file