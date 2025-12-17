# Complete Java Introduction - Comprehensive Notes

## 1. Why Java? How is it Different from Other Languages?

### Why Java?
- **Platform Independence**: Write Once, Run Anywhere (WORA)
- **Enterprise Standard**: Used by 90% of Fortune 500 companies
- **Rich Ecosystem**: Massive libraries, frameworks (Spring, Hibernate)
- **Strong Community**: 9+ million developers worldwide
- **Career Opportunities**: Highest demand in enterprise, Android, backend systems
- **Robust & Secure**: Strong type checking, exception handling, security features

### Java vs Other Languages

| Feature | Java | C++ | Python | JavaScript |
|---------|------|-----|--------|------------|
| **Memory Management** | Automatic (GC) | Manual | Automatic (GC) | Automatic (GC) |
| **Platform** | Platform Independent | Platform Dependent | Platform Independent | Browser/Node.js |
| **Type System** | Static, Strong | Static, Weak | Dynamic, Strong | Dynamic, Weak |
| **Speed** | Fast (JIT compiled) | Fastest (Compiled) | Slower (Interpreted) | Fast (JIT) |
| **Pointers** | No direct pointers | Yes | No | No |
| **Multiple Inheritance** | No (uses interfaces) | Yes | Yes (mixins) | Yes (prototypes) |
| **Use Cases** | Enterprise, Android | Systems, Gaming | AI/ML, Scripting | Web Development |

**Key Differences:**
- **vs C++**: No pointers, automatic memory management, simpler syntax
- **vs Python**: Statically typed, faster execution, verbose syntax
- **vs JavaScript**: Backend focus, strongly typed, class-based OOP

---

## 2. Features of Java (with Examples)

### A. Platform Independence (WORA - Write Once, Run Anywhere)
**Description**: Java code compiles to bytecode that runs on any platform with JVM

**Example**:
```java
// Same .class file runs on Windows, Mac, Linux
public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
// Compile: javac Hello.java → Hello.class (bytecode)
// Run on any OS: java Hello
```

### B. Object-Oriented Programming (OOP)
**Description**: Everything is an object (except primitives). Supports encapsulation, inheritance, polymorphism

**Example**:
```java
class Animal {
    private String name;  // Encapsulation
    
    public Animal(String name) {
        this.name = name;
    }
    
    public void makeSound() {  // Polymorphism
        System.out.println("Some sound");
    }
}

class Dog extends Animal {  // Inheritance
    public Dog(String name) {
        super(name);
    }
    
    @Override
    public void makeSound() {  // Polymorphism
        System.out.println("Bark!");
    }
}
```

### C. Automatic Memory Management (Garbage Collection)
**Description**: JVM automatically deallocates unused objects, preventing memory leaks

**Example**:
```java
public class GCDemo {
    public static void main(String[] args) {
        String s1 = new String("Hello");
        String s2 = new String("World");
        
        s1 = null;  // Object "Hello" becomes eligible for GC
        // No manual memory deallocation needed
        System.gc();  // Suggests GC to run (not guaranteed)
    }
}
```

### D. Robust & Secure
**Description**: Strong type checking, exception handling, no pointers, security manager

**Example**:
```java
public class RobustDemo {
    public static void main(String[] args) {
        try {
            int[] arr = new int[5];
            arr[10] = 100;  // ArrayIndexOutOfBoundsException
        } catch (Exception e) {
            System.out.println("Error handled: " + e.getMessage());
            // Program continues instead of crashing
        }
    }
}
```

### E. Multithreading
**Description**: Built-in support for concurrent execution

**Example**:
```java
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread running: " + Thread.currentThread().getName());
    }
}

public class ThreadDemo {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        t1.start();  // Runs concurrently
        t2.start();
    }
}
```

### F. Rich Standard Library
**Description**: Extensive built-in APIs for collections, networking, I/O, etc.

**Example**:
```java
import java.util.*;

public class CollectionsDemo {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(2);
        list.add(8);
        Collections.sort(list);  // Built-in sorting
        System.out.println(list);  // [2, 5, 8]
    }
}
```

---

## 3. Java Versions and Key Features

### Java Version Timeline

| Version | Release Year | Key Features |
|---------|--------------|--------------|
| **Java 1.0** | 1996 | Initial release |
| **Java 1.2** | 1998 | Collections Framework, JIT compiler |
| **Java 5** | 2004 | **Generics, Enhanced for-loop, Autoboxing, Enums** |
| **Java 7** | 2011 | Try-with-resources, Diamond operator, String in switch |
| **Java 8** | 2014 | **Lambda Expressions, Stream API, Default methods** |
| **Java 11** | 2018 | **LTS**, var keyword, HTTP Client API |
| **Java 17** | 2021 | **LTS**, Sealed classes, Pattern matching |
| **Java 21** | 2023 | **LTS**, Virtual threads, Record patterns |

### Important Features by Version

#### **Java 5 (2004)** - Major Milestone
```java
// 1. Generics (Type Safety)
List<String> list = new ArrayList<String>();  // Type-safe
// list.add(123);  // Compile error

// 2. Enhanced For Loop
for (String s : list) {
    System.out.println(s);
}

// 3. Autoboxing/Unboxing
Integer num = 5;  // Autoboxing (int → Integer)
int val = num;    // Unboxing (Integer → int)

// 4. Enums
enum Day { MONDAY, TUESDAY, WEDNESDAY }
Day today = Day.MONDAY;
```

#### **Java 8 (2014)** - Revolutionary Update
```java
// 1. Lambda Expressions
List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
nums.forEach(n -> System.out.println(n));  // Lambda

// 2. Stream API
int sum = nums.stream()
              .filter(n -> n % 2 == 0)  // Even numbers
              .mapToInt(n -> n * n)     // Square
              .sum();                   // 2² + 4² = 20

// 3. Default Methods in Interfaces
interface MyInterface {
    default void defaultMethod() {
        System.out.println("Default implementation");
    }
}

// 4. Optional (Avoid NullPointerException)
Optional<String> optional = Optional.ofNullable(null);
String result = optional.orElse("Default Value");
```

#### **Java 11 (2018)** - LTS Release
```java
// 1. var keyword (Local Variable Type Inference)
var list = new ArrayList<String>();  // Type inferred
var num = 10;  // int

// 2. String methods
String str = "  Hello  ";
str.isBlank();     // true/false
str.lines();       // Stream of lines
str.strip();       // Better trim()

// 3. Files.readString() / writeString()
String content = Files.readString(Path.of("file.txt"));
```

#### **Java 17 (2021)** - Current LTS
```java
// 1. Sealed Classes (Restrict inheritance)
sealed class Shape permits Circle, Square {}
final class Circle extends Shape {}
final class Square extends Shape {}
// class Triangle extends Shape {}  // Compile error

// 2. Pattern Matching for instanceof
Object obj = "Hello";
if (obj instanceof String s) {  // s is automatically cast
    System.out.println(s.length());
}

// 3. Records (Immutable data classes)
record Point(int x, int y) {}
Point p = new Point(10, 20);
System.out.println(p.x());  // 10
```

#### **Java 21 (2023)** - Latest LTS
```java
// 1. Virtual Threads (Lightweight concurrency)
Thread.startVirtualThread(() -> {
    System.out.println("Virtual thread");
});

// 2. Record Patterns
record Point(int x, int y) {}
Object obj = new Point(10, 20);
if (obj instanceof Point(int x, int y)) {
    System.out.println("x: " + x + ", y: " + y);
}

// 3. String Templates (Preview)
String name = "John";
int age = 30;
String message = STR."Name: \{name}, Age: \{age}";
```

**Recommendation**: Use **Java 17 or Java 21** (LTS versions) for production

---

## 4. Java Execution Background - JDK and Components

### What is JDK (Java Development Kit)?

**JDK** = Complete development toolkit for Java

```
┌─────────────────────────────────────────┐
│              JDK (Superset)             │
├─────────────────────────────────────────┤
│  ┌───────────────────────────────────┐  │
│  │         JRE (Subset)              │  │
│  │  ┌─────────────────────────────┐  │  │
│  │  │         JVM (Core)          │  │  │
│  │  │  - Executes bytecode        │  │  │
│  │  │  - Memory management        │  │  │
│  │  │  - Garbage collection       │  │  │
│  │  └─────────────────────────────┘  │  │
│  │  + Libraries (java.*, javax.*)    │  │
│  │  + Configuration files            │  │
│  └───────────────────────────────────┘  │
│  + Development Tools:                   │
│    - javac (compiler)                   │
│    - javadoc (documentation)            │
│    - jar (archiver)                     │
│    - jdb (debugger)                     │
└─────────────────────────────────────────┘
```

### JDK Components

#### 1. **Development Tools** (in JDK/bin/)
- **javac**: Compiler (converts .java → .class bytecode)
- **java**: Launcher (executes Java programs)
- **javadoc**: Generates HTML documentation from code comments
- **jar**: Archives multiple .class files into single .jar file
- **jdb**: Debugger for troubleshooting
- **jps**: Lists running Java processes
- **jconsole**: Monitors JVM performance

#### 2. **Java Runtime Environment (JRE)**
- JVM + Standard libraries + Configuration files
- Needed to **run** Java programs (not develop)

#### 3. **Libraries & APIs**
- **java.lang**: Core classes (String, Math, System) - auto-imported
- **java.util**: Collections, utilities (ArrayList, HashMap)
- **java.io**: Input/Output operations
- **java.net**: Networking
- **java.sql**: Database connectivity

---

## 5. JVM (Java Virtual Machine) - The Heart of Java

### What is JVM?

**JVM** is an abstract machine that executes Java bytecode. It's platform-specific (different for Windows, Mac, Linux) but executes the same bytecode.

### Why JVM is Important?

1. **Platform Independence**: Same bytecode runs on any OS
2. **Memory Management**: Automatic garbage collection
3. **Security**: Bytecode verification before execution
4. **Performance**: Just-In-Time (JIT) compilation for speed
5. **Portability**: No need to recompile for different platforms

### JVM Architecture

```
┌────────────────────────────────────────────────┐
│               JVM ARCHITECTURE                 │
├────────────────────────────────────────────────┤
│                                                │
│  ┌──────────────────────────────────────────┐ │
│  │         CLASS LOADER SUBSYSTEM           │ │
│  │  1. Loading → 2. Linking → 3. Initialize│ │
│  └──────────────────────────────────────────┘ │
│                     ↓                          │
│  ┌──────────────────────────────────────────┐ │
│  │          RUNTIME DATA AREAS              │ │
│  │  ┌────────┬────────┬──────┬──────┬─────┐ │ │
│  │  │ Method │  Heap  │ Stack│  PC  │Native│ │ │
│  │  │  Area  │        │      │ Reg  │Stack │ │ │
│  │  └────────┴────────┴──────┴──────┴─────┘ │ │
│  └──────────────────────────────────────────┘ │
│                     ↓                          │
│  ┌──────────────────────────────────────────┐ │
│  │         EXECUTION ENGINE                 │ │
│  │  ┌──────────┬──────────┬───────────────┐ │ │
│  │  │Interpreter│JIT Compiler│Garbage    │ │ │
│  │  │           │            │Collector  │ │ │
│  │  └──────────┴──────────┴───────────────┘ │ │
│  └──────────────────────────────────────────┘ │
│                     ↓                          │
│  ┌──────────────────────────────────────────┐ │
│  │     NATIVE METHOD INTERFACE (JNI)        │ │
│  └──────────────────────────────────────────┘ │
│                     ↓                          │
│  ┌──────────────────────────────────────────┐ │
│  │        NATIVE METHOD LIBRARIES           │ │
│  │         (C/C++ Libraries)                │ │
│  └──────────────────────────────────────────┘ │
│                                                │
└────────────────────────────────────────────────┘
```

### JVM Components Explained

#### 1. **Class Loader Subsystem**
- **Loading**: Reads .class files and loads into memory
- **Linking**: Verifies bytecode, allocates memory, resolves references
- **Initialization**: Executes static initializers

#### 2. **Runtime Data Areas** (Memory Structure)
- **Method Area**: Stores class metadata, static variables, method bytecode
- **Heap**: Stores objects and instance variables (shared by all threads)
- **Stack**: Stores local variables, method calls (one per thread)
- **PC Register**: Tracks current instruction being executed
- **Native Method Stack**: For native (C/C++) method execution

#### 3. **Execution Engine**
- **Interpreter**: Executes bytecode line-by-line (slow)
- **JIT Compiler**: Converts frequently-used bytecode → native machine code (fast)
- **Garbage Collector**: Automatically frees unused memory

---

## 6. How Java Program Executes (Step-by-Step)

### Execution Flow

```
Source Code (.java)
       ↓
   [javac] ← Compile Time
       ↓
Bytecode (.class)
       ↓
   [java] ← Runtime
       ↓
┌──────────────────┐
│  CLASS LOADER    │ → Loads .class files into JVM
└──────────────────┘
       ↓
┌──────────────────┐
│ BYTECODE VERIFIER│ → Checks for illegal code
└──────────────────┘
       ↓
┌──────────────────┐
│   INTERPRETER    │ → Executes bytecode line-by-line
└──────────────────┘
       ↓
┌──────────────────┐
│  JIT COMPILER    │ → Converts hot code to native machine code
└──────────────────┘
       ↓
┌──────────────────┐
│  NATIVE CODE     │ → Direct execution on CPU
└──────────────────┘
       ↓
    OUTPUT
```

### Detailed Execution Steps

```java
// Example: Hello.java
public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
```

**Step 1: Write Source Code**
- Create `Hello.java` file

**Step 2: Compilation (javac Hello.java)**
- Java compiler (javac) converts source code → bytecode
- Creates `Hello.class` (platform-independent bytecode)
- Checks for syntax errors
- Output: Bytecode (0xCA 0xFE 0xBA 0xBE magic number)

**Step 3: Class Loading**
- JVM's class loader loads `Hello.class` into memory
- **Bootstrap CL** → Loads core Java classes (java.lang.*)
- **Extension CL** → Loads extension libraries
- **Application CL** → Loads your application classes

**Step 4: Bytecode Verification**
- Verifier checks bytecode for illegal operations
- Ensures no stack overflow, type safety, access violations

**Step 5: Execution**
- **Interpreter**: Reads bytecode instructions one-by-one
- **JIT Compiler**: Compiles frequently-executed code (hot spots) into native machine code for performance
- Hybrid approach: Interpret first, compile frequently-used code

**Step 6: Runtime**
- `main()` method is entry point
- Creates stack frame for main() in Stack memory
- Loads "Hello World!" string into Heap memory
- `System.out.println()` executes (native method)

**Step 7: Garbage Collection**
- After execution, unused objects are marked for GC
- GC runs in background to free memory

**Step 8: Program Termination**
- JVM exits after main() completes

---

## 7. Java Memory Hierarchy

### Memory Structure Overview

```
┌─────────────────────────────────────────────────┐
│              JVM MEMORY LAYOUT                  │
├─────────────────────────────────────────────────┤
│                                                 │
│  ┌───────────────────────────────────────────┐ │
│  │         HEAP MEMORY (Shared)              │ │
│  │  - Objects                                │ │
│  │  - Instance variables                     │ │
│  │  - Arrays                                 │ │
│  │                                           │ │
│  │  ┌─────────────┬───────────────────────┐ │ │
│  │  │ Young Gen   │    Old Generation     │ │ │
│  │  │ ┌─────┬───┐ │  (Tenured Objects)    │ │ │
│  │  │ │Eden │S0 │ │                       │ │ │
│  │  │ │     │S1 │ │                       │ │ │
│  │  │ └─────┴───┘ │                       │ │ │
│  │  └─────────────┴───────────────────────┘ │ │
│  └───────────────────────────────────────────┘ │
│                                                 │
│  ┌───────────────────────────────────────────┐ │
│  │      METHOD AREA (Shared) / Metaspace     │ │
│  │  - Class metadata (name, modifiers)       │ │
│  │  - Method bytecode                        │ │
│  │  - Static variables                       │ │
│  │  - Runtime constant pool                  │ │
│  └───────────────────────────────────────────┘ │
│                                                 │
│  ┌───────────────────────────────────────────┐ │
│  │      STACK MEMORY (Per Thread)            │ │
│  │  Thread 1  │  Thread 2  │  Thread 3      │ │
│  │  ┌──────┐  │  ┌──────┐  │  ┌──────┐      │ │
│  │  │Frame3│  │  │Frame2│  │  │Frame1│      │ │
│  │  │Frame2│  │  │Frame1│  │  └──────┘      │ │
│  │  │Frame1│  │  └──────┘  │                │ │
│  │  └──────┘  │             │                │ │
│  └───────────────────────────────────────────┘ │
│                                                 │
│  ┌───────────────────────────────────────────┐ │
│  │        PC REGISTER (Per Thread)           │ │
│  │  - Current instruction address            │ │
│  └───────────────────────────────────────────┘ │
│                                                 │
│  ┌───────────────────────────────────────────┐ │
│  │    NATIVE METHOD STACK (Per Thread)       │ │
│  │  - Native (C/C++) method execution        │ │
│  └───────────────────────────────────────────┘ │
│                                                 │
└─────────────────────────────────────────────────┘
```

### Memory Areas Detailed

#### 1. **HEAP MEMORY** (Shared, GC managed)

**What's stored:**
- All objects created with `new`
- Instance variables
- Arrays

**Example:**
```java
class Person {
    String name;      // Instance variable → Heap
    int age;          // Instance variable → Heap
}

public class Demo {
    public static void main(String[] args) {
        Person p = new Person();  // Object → Heap
        int[] arr = new int[5];   // Array → Heap
    }
}
```

**Heap Generations:**
- **Young Generation**: New objects (Eden + Survivor spaces S0, S1)
- **Old Generation**: Long-lived objects promoted from Young Gen
- **Minor GC**: Cleans Young Generation (fast)
- **Major GC**: Cleans Old Generation (slow, "Stop-The-World")

#### 2. **METHOD AREA / METASPACE** (Shared, GC managed in Java 8+)

**What's stored:**
- Class-level data (metadata)
- Static variables
- Method bytecode
- Runtime constant pool

**Example:**
```java
class Calculator {
    static int count = 0;        // Static variable → Method Area
    
    static void increment() {    // Method bytecode → Method Area
        count++;
    }
}

// Class metadata (name, modifiers, fields) → Method Area
```

**Note**: In Java 7 and earlier, called "PermGen" (limited size). Java 8+ uses "Metaspace" (native memory, no size limit).

#### 3. **STACK MEMORY** (Per Thread, NOT GC managed)

**What's stored:**
- Local variables (primitives)
- Method call information
- References to objects (reference → Stack, object → Heap)

**Example:**
```java
public class StackDemo {
    public static void main(String[] args) {     // Frame 1
        int x = 10;           // Primitive → Stack
        String s = "Hello";   // Reference → Stack, Object → Heap
        
        method1(x);           // Frame 2 created
    }
    
    static void method1(int num) {   // Frame 2
        int y = num * 2;      // Local variable → Stack
        method2(y);           // Frame 3 created
    }                         // Frame 2 destroyed
    
    static void method2(int val) {   // Frame 3
        System.out.println(val);
    }                         // Frame 3 destroyed
}
```

**Stack Frame Components:**
- Local variables
- Operand stack
- Frame data (method info, exception handling)

**Stack vs Heap:**
- Stack: Fast, LIFO, limited size, automatic cleanup
- Heap: Slower, random access, larger size, GC cleanup

#### 4. **PC REGISTER** (Per Thread)

**What's stored:**
- Address of current instruction being executed

**Example:**
```java
int a = 10;     // PC → Line 1
int b = 20;     // PC → Line 2
int c = a + b;  // PC → Line 3
```

#### 5. **NATIVE METHOD STACK** (Per Thread)

**What's stored:**
- Native method (C/C++) execution data

**Example:**
```java
// When System.out.println() calls native C method
public native void println(String s);  // Native method
```

---

## 8. Where Objects, Classes, Methods, Variables are Stored

### Complete Storage Breakdown

```java
public class Employee {
    // Static variable → Method Area
    static int employeeCount = 0;
    
    // Instance variables → Heap (per object)
    String name;
    int salary;
    
    // Static method → Method Area (bytecode)
    static void displayCount() {
        System.out.println("Total: " + employeeCount);
    }
    
    // Instance method → Method Area (bytecode)
    void displayInfo() {
        System.out.println("Name: " + name);
    }
    
    public static void main(String[] args) {
        // Local primitive → Stack
        int bonus = 5000;
        
        // Object reference → Stack, Object → Heap
        Employee emp1 = new Employee();
        emp1.name = "John";    // String object → Heap
        emp1.salary = 50000;   // Instance variable → Heap
        
        // Array reference → Stack, Array → Heap
        int[] numbers = {1, 2, 3, 4, 5};
    }
}
```

### Storage Summary Table

| Component | Storage Location | Garbage Collected? | Shared? |
|-----------|------------------|-------------------|---------|
| **Class metadata** | Method Area | Yes (Java 8+) | Yes |
| **Static variables** | Method Area | Yes (Java 8+) | Yes |
| **Static methods (bytecode)** | Method Area | Yes (Java 8+) | Yes |
| **Instance methods (bytecode)** | Method Area | Yes (Java 8+) | Yes |
| **Objects** | Heap | Yes | Yes |
| **Instance variables** | Heap (inside object) | Yes | No |
| **Arrays** | Heap | Yes | Yes |
| **Local primitives** | Stack | No (auto) | No |
| **Object references** | Stack | No (auto) | No |
| **Method parameters** | Stack | No (auto) | No |

### Memory Example Walkthrough

```java
class Car {
    static int totalCars = 0;     // Method Area
    String brand;                  // Heap (per object)
    
    static void showTotal() {      // Method Area (bytecode)
        System.out.println(totalCars);
    }
    
    void display() {               // Method Area (bytecode)
        System.out.println(brand);
    }
}

public class Test {
    public static void main(String[] args) {
        int count = 5;             // Stack (main's frame)
        Car c1 = new Car();        // c1 ref → Stack, Car object → Heap
        c1.brand = "Toyota";       // "Toyota" String → Heap
        
        Car c2 = new Car();        // c2 ref → Stack, Car object → Heap
        c2.brand = "Honda";        // "Honda" String → Heap
    }
}
```

**Memory Layout:**
```
STACK (main thread)           HEAP                    METHOD AREA
┌──────────────┐             ┌─────────────────┐     ┌──────────────────┐
│ main() frame │             │ Car object #1   │     │ Class: Car       │
│ - count: 5   │             │ - brand: ref──→ │     │ - totalCars: 0   │
│ - c1: ref──→ │──────────→  │   "Toyota"      │     │ - showTotal()    │
│ - c2: ref──→ │──┐          └─────────────────┘     │ - display()      │
└──────────────┘  │          ┌─────────────────┐     │                  │
                  │          │ Car object #2   │     │ Class: Test      │
                  └────────→ │ - brand: ref──→ │     │ - main()         │
                             │   "Honda"        │     └──────────────────┘
                             └─────────────────┘
                             ┌─────────────────┐
                             │ String "Toyota" │
                             └─────────────────┘
                             ┌─────────────────┐
                             │ String "Honda"  │
                             └─────────────────┘
```

---

## Quick Reference

### JDK vs JRE vs JVM
- **JDK**: Development (compile + run) = JRE + Dev Tools
- **JRE**: Runtime (run only) = JVM + Libraries
- **JVM**: Executes bytecode (platform-specific)

### Memory Quick Tips
- **Stack**: Fast, small, LIFO, local variables, auto-managed
- **Heap**: Larger, shared, objects, GC-managed
- **Method Area**: Class data, static members, shared

### When to Use What?
- **Primitives** → Stack (faster)
- **Objects** → Heap (flexibility)
- **Static** → Method Area (shared across all instances)

---

## Next Steps

With this foundation, you're ready to:
1. Install JDK 17 or 21
2. Set up your IDE
3. Start coding with Java fundamentals
4. Practice DSA problems

**Ready to write your first Java program?** 🚀