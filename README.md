# BandsIveSeen
Bands I've Seen is a small application for recording which bands you've seen live at gigs or festivals. It was written entirely in Java (using the Swing Framework for the GUI), with all data stored on a sqlite database.

### Requirements
This application requires JDK 21 or higher to run. You can find the download for OpenJDK 21 [here](https://openjdk.org/projects/jdk/21/). There are no other dependencies required to run the .jar or .exe.

### Setting up the application
Download the appropriate .zip file for your operating system (.exe for Windows only, .Jar for any platform), extract the contents to whatever folder you want, then double click the .exe or .jar to run it! Running the file for the first time will create a new .db file for storing data; this is hardcoded to be in the same directory as the application, so be sure to keep both files in the same location.

As neither the .jar or .exe are currently signed, you'll likely be prompted with security warnings when opening the application. A workaround for this is to download the repository files and build the .jar file yourself with an IDE such as Eclipse. A useful video detailing how to do this can be found [here](https://www.youtube.com/watch?v=jKlyHG-zbjk&).

### Planned improvements
Whilst v1.0.0 is feature complete, there are a few things I'm planning on adding in the future:
- Signing the .jar file to prevent warnings.
- Improving the alignment of some windows.
- Adding statistics such as number of gigs and festivals attended.
- Allowing for more detailed views of individual events.
- Other miscellaneous improvements.
