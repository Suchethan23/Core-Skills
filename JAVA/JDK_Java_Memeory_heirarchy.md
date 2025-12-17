# Java Development Kit (JDK)

## What is JDK?
The Java Development Kit (JDK) is a complete software development environment provided by Oracle that contains all the tools, libraries, and documentation needed to develop, compile, and run Java applications. It's essentially the toolkit that developers use to write Java programs.

---

## Components of JDK

### Java Compiler (javac)
This tool converts your Java source code (.java files) into bytecode (.class files). The compiler checks your code for syntax errors and produces the compiled bytecode that can run on any JVM.

### Java Virtual Machine (JVM)
The JVM is an abstract computing machine that executes Java bytecode. It's what enables the "Write Once, Run Anywhere" philosophy. The JVM interprets and executes the bytecode, and different operating systems have their own JVM implementation.

### Java Runtime Environment (JRE)
This is included in the JDK and contains the JVM plus essential libraries and tools needed to run Java applications. While JRE can run Java programs, only JDK can compile them.

### Standard Library (Java Class Library)
This includes a vast collection of pre-built classes and packages (like java.lang, java.util, java.io, etc.) that provide ready-to-use functionality for common programming tasks.

### Development Tools
Various utility programs like:
- **jar** — Creates and manages Java Archive files  
- **javadoc** — Generates documentation from your code comments  
- **jdb** — Java debugger for finding and fixing bugs  
- **javap** — Disassembler for analyzing class files

## JDK vs JRE vs JVM (Key Differences)

### JDK (Java Development Kit)
The complete package for developers. It includes the compiler, JRE, and development tools. You need JDK if you want to write and compile Java code.

### JRE (Java Runtime Environment)
Only contains the JVM and libraries needed to run compiled Java programs. It doesn't include the compiler or development tools. You only need JRE if you want to run Java applications (not develop them).

### JVM (Java Virtual Machine)
The abstract machine that executes Java bytecode. It's a part of both JDK and JRE. The JVM is what makes Java platform-independent.

**Simple analogy:**  
Think of JDK as a complete kitchen (where you can cook and prepare food), JRE as a dining area (where you can only eat prepared food), and JVM as the stove (the engine that does the actual work).

---

## How JDK Works
1. You write your Java code in a text editor or IDE and save it as a `.java` file  
2. You use the `javac` compiler (part of JDK) to compile the code:  
   ```bash
   javac HelloWorld.java
