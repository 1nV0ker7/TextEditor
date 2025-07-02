**TextEditor**
A minimalist, cross-platform text editor built with JavaFX, supporting .txt and basic .pdf file creation. Designed to run on Windows and Linux, this project demonstrates skills in Java, GUI development, file handling, and standalone application deployment.


**Features**
-> Create, open, and save .txt files.
-> Create basic .pdf files with text content.
-> Minimalist UI using JavaFX.
-> Cross-platform compatibility (Windows and Linux).
-> Deployable as a native executable.

**Prerequisites**

-> JDK 17 or later with JavaFX (e.g., Azul Zulu JDK 17 with JavaFX)
-> Maven
-> VS Code with Java Extension Pack (optional)

**Setup (Linux)**

1) Install Azul Zulu JDK with JavaFX:tar -xzf zulu17.58.21-ca-fx-jdk17.0.15-linux_x64.tar.gz
        sudo mv zulu-17.jdk /opt/zulu-17
        echo 'export JAVA_HOME=/opt/zulu-17' >> ~/.bashrc
        echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc
        source ~/.bashrc


2) Clone the repository:
        git clone https://github.com/yourusername/TextEditor.git
        cd TextEditor


3) Build the project:
        mvn clean package


4) Run the JAR:
        java --module-path target/libs --add-modules javafx.controls,javafx.fxml,javafx.graphics -jar target/TextEditor-1.0-SNAPSHOT.jar



**Deployment (Linux)**
## Native Executable ##

1) Build the .deb package:
jpackage --name TextEditor --input target --main-jar TextEditor-1.0-SNAPSHOT.jar --main-class com.invoker.TextEditor --type deb --dest dist --linux-shortcut --linux-menu-group "Utility" --module-path target/libs --add-modules javafx.controls,javafx.fxml,javafx.graphics


2) Install:
sudo dpkg -i dist/TextEditor-1.0.deb
sudo apt-get install -f



**Troubleshooting**

# If compilation errors occur:
  Clear Maven cache: rm -rf ~/.m2/repository
  Run: mvn clean install -U
  Ensure src/main/java/com/package/TextEditor.java exists.


# If errors in VS Code:
Add to ~/.vscode/settings.json:
    {
    "java.configuration.runtimes": [
        {
            "name": "JavaSE-17",
            "path": "/opt/zulu-17",
            "default": true
        }
    ],
    "java.project.referencedLibraries": ["target/libs/*.jar"]
}


Install native libraries: sudo apt-get install openjfx

**Future Enhancements**

-> PDF text extraction.
-> Syntax highlighting for .py, .java files.
-> Custom app icon.

License
1nV0ker
