# Control Structures & Arrays - Complete Guide
## Mastering Flow Control and Array Operations in Java

---

## Table of Contents

### Part 1: Control Structures
1. [Introduction to Control Flow](#introduction-to-control-flow)
2. [Conditional Statements](#conditional-statements)
   - if Statement
   - if-else Statement
   - if-else-if Ladder
   - Nested if
   - Ternary Operator
   - switch Statement
3. [Loops](#loops)
   - for Loop
   - while Loop
   - do-while Loop
   - Enhanced for Loop (for-each)
   - Nested Loops
4. [Jump Statements](#jump-statements)
   - break
   - continue
   - return
   - labeled break and continue
5. [Control Flow Best Practices](#control-flow-best-practices)

### Part 2: Arrays
1. [Introduction to Arrays](#introduction-to-arrays)
2. [1D Arrays](#1d-arrays)
3. [Array Operations](#array-operations)
4. [2D Arrays](#2d-arrays)
5. [Jagged Arrays](#jagged-arrays)
6. [Array Algorithms](#array-algorithms)
7. [Common Array Problems](#common-array-problems)
8. [Arrays Class Utility](#arrays-class-utility)

---

# PART 1: CONTROL STRUCTURES

---

## Introduction to Control Flow

**Control Flow** determines the order in which statements are executed in a program.

### Types of Control Structures

```
┌─────────────────────────────────────┐
│     Control Flow Statements         │
├─────────────────┬───────────────────┤
│  Sequential     │  Default flow     │
│  (top to down)  │  (no keywords)    │
├─────────────────┼───────────────────┤
│  Selection      │  if, if-else,     │
│  (Decision)     │  switch           │
├─────────────────┼───────────────────┤
│  Iteration      │  for, while,      │
│  (Loops)        │  do-while         │
├─────────────────┼───────────────────┤
│  Jump           │  break, continue, │
│  (Transfer)     │  return           │
└─────────────────┴───────────────────┘
```

---

## Conditional Statements

### 1. if Statement

Executes code block **only if** condition is true.

**Syntax:**
```java
if (condition) {
    // Code executes if condition is true
}
```

**Flow Diagram:**
```
    condition
    ┌───┴───┐
  true     false
    │        │
 execute     │
  block      │
    │        │
    └────┬───┘
      continue
```

**Examples:**

**Example 1: Simple if**
```java
int age = 20;
if (age >= 18) {
    System.out.println("You can vote");
}
// Output: You can vote
```

**Example 2: Multiple statements**
```java
int marks = 85;
if (marks >= 75) {
    System.out.println("Grade: A");
    System.out.println("Excellent!");
    System.out.println("Keep it up!");
}
```

**Example 3: Without braces (single statement)**
```java
int num = 10;
if (num > 0)
    System.out.println("Positive");  // Only this line in if
System.out.println("End");  // Always executes

// ⚠️ Best Practice: Always use braces even for single statement!
if (num > 0) {
    System.out.println("Positive");
}
```

**Common Conditions:**
```java
// Equality
if (x == 10) { }

// Comparison
if (x > 10) { }
if (x < 10) { }
if (x >= 10) { }
if (x <= 10) { }

// Not equal
if (x != 10) { }

// Logical AND
if (x > 0 && x < 100) { }

// Logical OR
if (x < 0 || x > 100) { }

// Negation
if (!(x == 10)) { }  // Same as: if (x != 10)

// Boolean variable
boolean flag = true;
if (flag) { }  // No need for: if (flag == true)
if (!flag) { }  // Check if false
```

---

### 2. if-else Statement

Executes one block if true, another if false.

**Syntax:**
```java
if (condition) {
    // Executes if condition is true
} else {
    // Executes if condition is false
}
```

**Flow Diagram:**
```
      condition
    ┌─────┴─────┐
  true         false
    │            │
 if-block    else-block
    │            │
    └─────┬──────┘
       continue
```

**Examples:**

**Example 1: Even/Odd**
```java
int num = 15;
if (num % 2 == 0) {
    System.out.println("Even");
} else {
    System.out.println("Odd");
}
// Output: Odd
```

**Example 2: Max of two numbers**
```java
int a = 10, b = 20;
if (a > b) {
    System.out.println("Max: " + a);
} else {
    System.out.println("Max: " + b);
}
// Output: Max: 20
```

**Example 3: Pass/Fail**
```java
int marks = 45;
if (marks >= 50) {
    System.out.println("Pass");
} else {
    System.out.println("Fail");
}
// Output: Fail
```

**Example 4: Positive/Negative**
```java
int num = -5;
if (num >= 0) {
    System.out.println("Non-negative");
} else {
    System.out.println("Negative");
}
// Output: Negative
```

---

### 3. if-else-if Ladder

Tests multiple conditions sequentially.

**Syntax:**
```java
if (condition1) {
    // Executes if condition1 is true
} else if (condition2) {
    // Executes if condition1 false and condition2 true
} else if (condition3) {
    // Executes if condition1, condition2 false and condition3 true
} else {
    // Executes if all conditions are false (optional)
}
```

**Important:** 
- Conditions checked **top to bottom**
- **First true** condition executes, rest are skipped
- **else** is optional (default case)

**Examples:**

**Example 1: Grade System**
```java
int marks = 75;

if (marks >= 90) {
    System.out.println("Grade: A+");
} else if (marks >= 80) {
    System.out.println("Grade: A");
} else if (marks >= 70) {
    System.out.println("Grade: B");
} else if (marks >= 60) {
    System.out.println("Grade: C");
} else if (marks >= 50) {
    System.out.println("Grade: D");
} else {
    System.out.println("Grade: F");
}
// Output: Grade: B
```

**Example 2: Max of three numbers**
```java
int a = 15, b = 20, c = 10;

if (a >= b && a >= c) {
    System.out.println("Max: " + a);
} else if (b >= a && b >= c) {
    System.out.println("Max: " + b);
} else {
    System.out.println("Max: " + c);
}
// Output: Max: 20
```

**Example 3: Number Sign**
```java
int num = 0;

if (num > 0) {
    System.out.println("Positive");
} else if (num < 0) {
    System.out.println("Negative");
} else {
    System.out.println("Zero");
}
// Output: Zero
```

**Example 4: Days in Month**
```java
int month = 2;
int year = 2024;

if (month == 2) {
    if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
        System.out.println("29 days");  // Leap year
    } else {
        System.out.println("28 days");
    }
} else if (month == 4 || month == 6 || month == 9 || month == 11) {
    System.out.println("30 days");
} else {
    System.out.println("31 days");
}
```

**Example 5: BMI Calculator**
```java
double weight = 70;  // kg
double height = 1.75;  // meters
double bmi = weight / (height * height);

if (bmi < 18.5) {
    System.out.println("Underweight");
} else if (bmi < 25) {
    System.out.println("Normal weight");
} else if (bmi < 30) {
    System.out.println("Overweight");
} else {
    System.out.println("Obese");
}
```

---

### 4. Nested if Statements

if statements inside other if statements.

**Syntax:**
```java
if (condition1) {
    if (condition2) {
        // Executes if both condition1 AND condition2 are true
    }
}
```

**Examples:**

**Example 1: Eligibility Check**
```java
int age = 20;
boolean hasLicense = true;

if (age >= 18) {
    if (hasLicense) {
        System.out.println("Can drive");
    } else {
        System.out.println("Get license first");
    }
} else {
    System.out.println("Too young to drive");
}
// Output: Can drive
```

**Example 2: Nested with else**
```java
int num = 15;

if (num > 0) {
    if (num % 2 == 0) {
        System.out.println("Positive Even");
    } else {
        System.out.println("Positive Odd");
    }
} else {
    if (num % 2 == 0) {
        System.out.println("Negative Even");
    } else {
        System.out.println("Negative Odd");
    }
}
// Output: Positive Odd
```

**Example 3: Can be replaced with logical AND**
```java
// Nested if
if (age >= 18) {
    if (hasLicense) {
        System.out.println("Can drive");
    }
}

// Better: Single if with AND
if (age >= 18 && hasLicense) {
    System.out.println("Can drive");
}
```

**⚠️ Avoid Deep Nesting:**
```java
// BAD: Hard to read
if (condition1) {
    if (condition2) {
        if (condition3) {
            if (condition4) {
                // Code
            }
        }
    }
}

// GOOD: Use logical operators or early returns
if (condition1 && condition2 && condition3 && condition4) {
    // Code
}

// OR use early returns
if (!condition1) return;
if (!condition2) return;
if (!condition3) return;
if (!condition4) return;
// Code
```

---

### 5. Ternary Operator (Conditional Operator)

Shorthand for simple if-else statements.

**Syntax:**
```java
variable = (condition) ? valueIfTrue : valueIfFalse;
```

**Examples:**

**Example 1: Max of two**
```java
int a = 10, b = 20;
int max = (a > b) ? a : b;
System.out.println("Max: " + max);  // Output: Max: 20
```

**Example 2: Even/Odd**
```java
int num = 15;
String result = (num % 2 == 0) ? "Even" : "Odd";
System.out.println(result);  // Output: Odd
```

**Example 3: Pass/Fail**
```java
int marks = 65;
String status = (marks >= 50) ? "Pass" : "Fail";
```

**Example 4: Nested Ternary (Avoid if complex!)**
```java
int num = 0;
String sign = (num > 0) ? "Positive" : (num < 0) ? "Negative" : "Zero";

// Better as if-else-if for readability!
```

**Example 5: Absolute Value**
```java
int num = -5;
int abs = (num >= 0) ? num : -num;
System.out.println(abs);  // Output: 5
```

**When to use:**
- ✅ Simple conditions
- ✅ Single line assignments
- ❌ Don't nest too many
- ❌ Don't use for complex logic

---

### 6. switch Statement

Multi-way branch statement for equality checks.

**Syntax:**
```java
switch (expression) {
    case value1:
        // Code
        break;
    case value2:
        // Code
        break;
    case value3:
        // Code
        break;
    default:
        // Code (optional)
}
```

**Important Points:**
1. Expression must be: byte, short, int, char, String (Java 7+), enum
2. **Cannot** use: long, float, double, boolean
3. `break` exits the switch (without it, fall-through occurs)
4. `default` executes if no case matches (optional)
5. Cases must be **constant** values (not variables)

**Examples:**

**Example 1: Day of Week**
```java
int day = 3;

switch (day) {
    case 1:
        System.out.println("Monday");
        break;
    case 2:
        System.out.println("Tuesday");
        break;
    case 3:
        System.out.println("Wednesday");
        break;
    case 4:
        System.out.println("Thursday");
        break;
    case 5:
        System.out.println("Friday");
        break;
    case 6:
        System.out.println("Saturday");
        break;
    case 7:
        System.out.println("Sunday");
        break;
    default:
        System.out.println("Invalid day");
}
// Output: Wednesday
```

**Example 2: Grade System**
```java
char grade = 'B';

switch (grade) {
    case 'A':
        System.out.println("Excellent!");
        break;
    case 'B':
        System.out.println("Good job!");
        break;
    case 'C':
        System.out.println("Well done!");
        break;
    case 'D':
        System.out.println("You passed");
        break;
    case 'F':
        System.out.println("Better luck next time");
        break;
    default:
        System.out.println("Invalid grade");
}
// Output: Good job!
```

**Example 3: Fall-through (No break)**
```java
int month = 3;

switch (month) {
    case 12:
    case 1:
    case 2:
        System.out.println("Winter");
        break;
    case 3:
    case 4:
    case 5:
        System.out.println("Spring");
        break;
    case 6:
    case 7:
    case 8:
        System.out.println("Summer");
        break;
    case 9:
    case 10:
    case 11:
        System.out.println("Fall");
        break;
    default:
        System.out.println("Invalid month");
}
// Output: Spring
```

**Example 4: String switch (Java 7+)**
```java
String fruit = "Apple";

switch (fruit) {
    case "Apple":
        System.out.println("Red fruit");
        break;
    case "Banana":
        System.out.println("Yellow fruit");
        break;
    case "Orange":
        System.out.println("Orange fruit");
        break;
    default:
        System.out.println("Unknown fruit");
}
// Output: Red fruit
```

**Example 5: Calculator**
```java
int a = 10, b = 5;
char operator = '+';
int result = 0;

switch (operator) {
    case '+':
        result = a + b;
        break;
    case '-':
        result = a - b;
        break;
    case '*':
        result = a * b;
        break;
    case '/':
        if (b != 0) {
            result = a / b;
        } else {
            System.out.println("Division by zero!");
            break;
        }
        break;
    case '%':
        result = a % b;
        break;
    default:
        System.out.println("Invalid operator");
        break;
}
System.out.println("Result: " + result);
```

**Example 6: Switch Expression (Java 14+)**
```java
int day = 3;
String dayName = switch (day) {
    case 1 -> "Monday";
    case 2 -> "Tuesday";
    case 3 -> "Wednesday";
    case 4 -> "Thursday";
    case 5 -> "Friday";
    case 6 -> "Saturday";
    case 7 -> "Sunday";
    default -> "Invalid";
};
System.out.println(dayName);  // Output: Wednesday
```

**switch vs if-else-if:**

| Feature | switch | if-else-if |
|---------|--------|------------|
| **Use Case** | Equality checks | Any condition |
| **Types** | int, char, String, enum | Any boolean |
| **Readability** | Better for many cases | Better for ranges |
| **Performance** | Can be faster (jump table) | Sequential check |

**When to use switch:**
- ✅ Multiple equality checks
- ✅ Fixed set of values
- ✅ Better readability for many cases

**When to use if-else-if:**
- ✅ Range checks (>, <, >=, <=)
- ✅ Complex conditions
- ✅ Different types of comparisons

---

## Loops

Loops execute a block of code repeatedly.

### Loop Types Comparison

| Loop | Use Case | Entry Check | Exit Check |
|------|----------|-------------|------------|
| **for** | Known iterations | Yes | No |
| **while** | Unknown iterations | Yes | No |
| **do-while** | At least once execution | No | Yes |
| **for-each** | Iterate collections/arrays | Yes | No |

---

### 1. for Loop

Best when number of iterations is **known**.

**Syntax:**
```java
for (initialization; condition; update) {
    // Code to execute
}
```

**Flow:**
```
initialization
     ↓
  condition ─────→ false ─────→ exit loop
     ↓ true
  loop body
     ↓
   update
     ↓
  (back to condition)
```

**Execution Order:**
1. Initialization (once)
2. Condition check
3. Body execution (if true)
4. Update
5. Repeat from step 2

**Examples:**

**Example 1: Print 1 to 10**
```java
for (int i = 1; i <= 10; i++) {
    System.out.print(i + " ");
}
// Output: 1 2 3 4 5 6 7 8 9 10
```

**Example 2: Print 10 to 1**
```java
for (int i = 10; i >= 1; i--) {
    System.out.print(i + " ");
}
// Output: 10 9 8 7 6 5 4 3 2 1
```

**Example 3: Even numbers 0 to 20**
```java
for (int i = 0; i <= 20; i += 2) {
    System.out.print(i + " ");
}
// Output: 0 2 4 6 8 10 12 14 16 18 20
```

**Example 4: Sum of first N numbers**
```java
int n = 10;
int sum = 0;
for (int i = 1; i <= n; i++) {
    sum += i;
}
System.out.println("Sum: " + sum);  // Output: Sum: 55
```

**Example 5: Factorial**
```java
int n = 5;
long factorial = 1;
for (int i = 1; i <= n; i++) {
    factorial *= i;
}
System.out.println("Factorial: " + factorial);  // Output: 120
```

**Example 6: Multiplication Table**
```java
int num = 5;
for (int i = 1; i <= 10; i++) {
    System.out.println(num + " x " + i + " = " + (num * i));
}
// Output:
// 5 x 1 = 5
// 5 x 2 = 10
// ... and so on
```

**Example 7: Multiple variables**
```java
for (int i = 0, j = 10; i < j; i++, j--) {
    System.out.println("i=" + i + ", j=" + j);
}
// Output:
// i=0, j=10
// i=1, j=9
// i=2, j=8
// i=3, j=7
// i=4, j=6
```

**Example 8: Infinite loop**
```java
// Infinite loop - condition always true
for (int i = 0; i < 10; i--) {  // i keeps decreasing!
    System.out.println(i);
}

// Intentional infinite loop
for (;;) {
    System.out.println("Forever");
    // Need break to exit
}
```

**Example 9: Skip initialization or update**
```java
// Initialization outside
int i = 0;
for (; i < 5; i++) {
    System.out.println(i);
}

// Update inside loop
for (int j = 0; j < 5;) {
    System.out.println(j);
    j++;  // Manual update
}
```

**Example 10: Powers of 2**
```java
for (int i = 1; i <= 1024; i *= 2) {
    System.out.print(i + " ");
}
// Output: 1 2 4 8 16 32 64 128 256 512 1024
```

---

### 2. while Loop

Best when number of iterations is **unknown**.

**Syntax:**
```java
while (condition) {
    // Code to execute
}
```

**Flow:**
```
  condition ─────→ false ─────→ exit loop
     ↓ true
  loop body
     ↓
  (back to condition)
```

**Important:** If condition is initially false, loop **never executes**.

**Examples:**

**Example 1: Print 1 to 10**
```java
int i = 1;
while (i <= 10) {
    System.out.print(i + " ");
    i++;
}
// Output: 1 2 3 4 5 6 7 8 9 10
```

**Example 2: Sum until user enters 0**
```java
Scanner sc = new Scanner(System.in);
int sum = 0;
int num = sc.nextInt();

while (num != 0) {
    sum += num;
    num = sc.nextInt();
}
System.out.println("Sum: " + sum);
```

**Example 3: Count digits**
```java
int num = 12345;
int count = 0;

while (num > 0) {
    count++;
    num /= 10;
}
System.out.println("Digits: " + count);  // Output: 5
```

**Example 4: Reverse a number**
```java
int num = 12345;
int reversed = 0;

while (num > 0) {
    int digit = num % 10;
    reversed = reversed * 10 + digit;
    num /= 10;
}
System.out.println("Reversed: " + reversed);  // Output: 54321
```

**Example 5: GCD (Euclidean Algorithm)**
```java
int a = 48, b = 18;

while (b != 0) {
    int temp = b;
    b = a % b;
    a = temp;
}
System.out.println("GCD: " + a);  // Output: 6
```

**Example 6: Find first power of 2 greater than N**
```java
int n = 100;
int power = 1;

while (power <= n) {
    power *= 2;
}
System.out.println("First power > " + n + ": " + power);  // Output: 128
```

**Example 7: Infinite loop**
```java
// Intentional infinite loop
while (true) {
    // Code
    if (exitCondition) {
        break;  // Exit loop
    }
}
```

**Example 8: Reading until EOF**
```java
Scanner sc = new Scanner(System.in);
while (sc.hasNext()) {
    String line = sc.nextLine();
    // Process line
}
```

---

### 3. do-while Loop

Executes **at least once**, then checks condition.

**Syntax:**
```java
do {
    // Code to execute
} while (condition);
```

**Flow:**
```
  loop body
     ↓
  condition ──────→ false ──────→ exit loop
     ↓ true
  (back to loop body)
```

**Key Difference:** Body executes **before** condition check.

**Examples:**

**Example 1: Print 1 to 10**
```java
int i = 1;
do {
    System.out.print(i + " ");
    i++;
} while (i <= 10);
// Output: 1 2 3 4 5 6 7 8 9 10
```

**Example 2: Menu-driven program**
```java
Scanner sc = new Scanner(System.in);
int choice;

do {
    System.out.println("\n1. Add");
    System.out.println("2. Subtract");
    System.out.println("3. Exit");
    System.out.print("Enter choice: ");
    choice = sc.nextInt();
    
    switch (choice) {
        case 1:
            // Add logic
            break;
        case 2:
            // Subtract logic
            break;
        case 3:
            System.out.println("Goodbye!");
            break;
        default:
            System.out.println("Invalid choice");
    }
} while (choice != 3);
```

**Example 3: At least one execution**
```java
int i = 10;

// while loop - doesn't execute
while (i < 5) {
    System.out.println("while: " + i);
}

// do-while loop - executes once
do {
    System.out.println("do-while: " + i);
} while (i < 5);

// Output: do-while: 10
```

**Example 4: Password validation**
```java
Scanner sc = new Scanner(System.in);
String password;

do {
    System.out.print("Enter password: ");
    password = sc.nextLine();
    
    if (!password.equals("secret")) {
        System.out.println("Wrong password. Try again.");
    }
} while (!password.equals("secret"));

System.out.println("Access granted!");
```

**Example 5: Sum until negative**
```java
Scanner sc = new Scanner(System.in);
int sum = 0;
int num;

do {
    num = sc.nextInt();
    if (num >= 0) {
        sum += num;
    }
} while (num >= 0);

System.out.println("Sum: " + sum);
```

---

### 4. Enhanced for Loop (for-each)

Iterates through arrays and collections easily.

**Syntax:**
```java
for (dataType variable : arrayOrCollection) {
    // Code
}
```

**Examples:**

**Example 1: Array iteration**
```java
int[] numbers = {1, 2, 3, 4, 5};

for (int num : numbers) {
    System.out.print(num + " ");
}
// Output: 1 2 3 4 5
```

**Example 2: String array**
```java
String[] names = {"Alice", "Bob", "Charlie"};

for (String name : names) {
    System.out.println("Hello, " + name);
}
// Output:
// Hello, Alice
// Hello, Bob
// Hello, Charlie
```

**Example 3: Sum of array**
```java
int[] numbers = {10, 20, 30, 40, 50};
int sum = 0;

for (int num : numbers) {
    sum += num;
}
System.out.println("Sum: " + sum);  // Output: 150
```

**Example 4: 2D array**
```java
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};

for (int[] row : matrix) {
    for (int num : row) {
        System.out.print(num + " ");
    }
    System.out.println();
}
```

**Limitations:**
- ❌ Cannot modify elements
- ❌ Cannot access index
- ❌ Cannot iterate backwards
- ❌ Cannot skip elements easily

**When to use:**
- ✅ Simple iteration (read-only)
- ✅ Don't need index
- ✅ Cleaner syntax

---

### 5. Nested Loops

Loops inside loops.

**Examples:**

**Example 1: Multiplication Table (1-10)**
```java
for (int i = 1; i <= 10; i++) {
    for (int j = 1; j <= 10; j++) {
        System.out.print((i * j) + "\t");
    }
    System.out.println();
}
```

**Example 2: Pattern - Rectangle**
```java
int rows = 5, cols = 10;

for (int i = 0; i < rows; i++) {
    for (int j = 0; j < cols; j++) {
        System.out.print("* ");
    }
    System.out.println();
}
// Output:
// * * * * * * * * * *
// * * * * * * * * * *
// * * * * * * * * * *
// * * * * * * * * * *
// * * * * * * * * * *
```

**Example 3: Pattern - Right Triangle**
```java
int n = 5;

for (int i = 1; i <= n; i++) {
    for (int j = 1; j <= i; j++) {
        System.out.print("* ");
    }
    System.out.println();
}
// Output:
// *
// * *
// * * *
// * * * *
// * * * * *
```

