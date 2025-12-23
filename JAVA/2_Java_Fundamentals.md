# Java Fundamentals - Complete Guide
## From Zero to DSA Ready

---

## Table of Contents
1. [Computer Architecture Basics](#computer-architecture-basics)
2. [Number Systems](#number-systems)
3. [Memory & Storage](#memory-and-storage)
4. [Data Types in Java](#data-types-in-java)
5. [Type Conversion & Casting](#type-conversion-and-casting)
6. [Operators in Java](#operators-in-java)
7. [Input/Output in Java](#input-output-in-java)
8. [Common Pitfalls & Best Practices](#common-pitfalls-and-best-practices)
9. [DSA Relevance](#dsa-relevance)

---

## Computer Architecture Basics

### What is a Bit?
- **Bit** = Binary Digit (0 or 1)
- Smallest unit of data in computing
- Represents electrical state: 0 = OFF, 1 = ON
- Foundation of all digital computing

### What is a Byte?
- **1 Byte = 8 Bits**
- Can represent 2^8 = 256 different values (0-255)
- Standard unit for measuring memory and storage
- Example: The letter 'A' is stored as 01000001 (1 byte)

### Computer Architecture: 32-bit vs 64-bit

#### 32-bit Architecture
```
┌─────────────────────────────────────┐
│  CPU Register Width: 32 bits        │
│  Address Bus Width: 32 bits         │
│  Data Bus Width: 32 bits            │
│  Max Addressable RAM: 2^32 = 4 GB   │
│  Pointer Size: 4 bytes              │
└─────────────────────────────────────┘
```

**Characteristics:**
- Can process 32 bits (4 bytes) of data in one CPU cycle
- Memory addresses are 32-bit → limits RAM to 4GB
- Each memory address = 4 bytes
- Total addresses = 2^32 = 4,294,967,296
- 4,294,967,296 addresses × 1 byte each = 4 GB

**Use Cases:**
- Older computers (pre-2010)
- Embedded systems
- Low-power devices
- Legacy software support

#### 64-bit Architecture
```
┌─────────────────────────────────────┐
│  CPU Register Width: 64 bits        │
│  Address Bus Width: 64 bits         │
│  Data Bus Width: 64 bits            │
│  Max Addressable RAM: 2^64 bytes    │
│  Pointer Size: 8 bytes              │
└─────────────────────────────────────┘
```

**Characteristics:**
- Can process 64 bits (8 bytes) of data in one CPU cycle
- Memory addresses are 64-bit → can address 16 Exabytes (EB)
- 2^64 = 18,446,744,073,709,551,616 bytes ≈ 16 EB
- More registers available for computation
- Better performance for large data processing

**Use Cases:**
- Modern computers (standard since 2010)
- Servers and data centers
- High-performance computing
- Applications requiring >4GB RAM

#### Comparison Table

| Feature | 32-bit | 64-bit |
|---------|--------|--------|
| **Register Size** | 32 bits | 64 bits |
| **Max RAM** | 4 GB | 16+ TB (practically) |
| **Pointer Size** | 4 bytes | 8 bytes |
| **Integer Range** | Same | Same |
| **Performance** | Slower for large data | 2-3x faster |
| **Software Compatibility** | Runs 32-bit only | Runs both 32 & 64-bit |
| **Memory per Process** | ~2 GB | Limited by RAM |

#### Java Perspective
**Important:** Java's primitive data type sizes are **platform-independent**!

```java
// These sizes are ALWAYS the same, regardless of 32-bit or 64-bit JVM
byte  → Always 1 byte  (8 bits)
short → Always 2 bytes (16 bits)
int   → Always 4 bytes (32 bits)
long  → Always 8 bytes (64 bits)
```

**Why is this important?**
- Code written on 32-bit machine runs identically on 64-bit
- No need to worry about "int" size changing
- "Write Once, Run Anywhere" (WORA) principle

**What DOES change in 64-bit Java?**
- JVM can allocate >4GB heap memory
- Object references (pointers) are 8 bytes instead of 4
- Overall memory usage slightly higher
- Performance is better for computation-heavy tasks

---

## Number Systems

### Understanding Different Number Systems

#### 1. Decimal (Base-10)
- **Base:** 10
- **Digits:** 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
- **What we use daily**

**Example:** 345
```
  3     4     5
  ↓     ↓     ↓
3×10² + 4×10¹ + 5×10⁰
= 300 + 40 + 5
= 345
```

#### 2. Binary (Base-2)
- **Base:** 2
- **Digits:** 0, 1
- **What computers use**

**Example:** 1011
```
  1     0     1     1
  ↓     ↓     ↓     ↓
1×2³ + 0×2² + 1×2¹ + 1×2⁰
= 8  + 0   + 2   + 1
= 11 (in decimal)
```

#### 3. Octal (Base-8)
- **Base:** 8
- **Digits:** 0, 1, 2, 3, 4, 5, 6, 7
- **Used in Unix file permissions**

**Example:** 17 (octal)
```
  1     7
  ↓     ↓
1×8¹ + 7×8⁰
= 8  + 7
= 15 (in decimal)
```

In Java: `int octal = 017;  // Prefix with 0`

#### 4. Hexadecimal (Base-16)
- **Base:** 16
- **Digits:** 0-9, A-F (A=10, B=11, C=12, D=13, E=14, F=15)
- **Used in colors, memory addresses**

**Example:** 2F (hex)
```
  2      F
  ↓      ↓
2×16¹ + 15×16⁰
= 32  + 15
= 47 (in decimal)
```

In Java: `int hex = 0x2F;  // Prefix with 0x`

---

### Decimal to Binary Conversion

#### Method: Repeated Division by 2

**Step-by-Step Process:**
1. Divide the decimal number by 2
2. Note the remainder (0 or 1)
3. Divide the quotient by 2
4. Repeat until quotient becomes 0
5. Read remainders from **bottom to top**

#### Example 1: Convert 13 to Binary

```
Step 1: 13 ÷ 2 = 6  remainder 1  ← LSB (Least Significant Bit)
Step 2:  6 ÷ 2 = 3  remainder 0
Step 3:  3 ÷ 2 = 1  remainder 1
Step 4:  1 ÷ 2 = 0  remainder 1  ← MSB (Most Significant Bit)

Reading bottom to top: 1101
```

**Verification:**
```
1×2³ + 1×2² + 0×2¹ + 1×2⁰
= 8  +  4  +  0  +  1
= 13 ✓
```

#### Example 2: Convert 25 to Binary

```
25 ÷ 2 = 12  remainder 1  ← LSB
12 ÷ 2 =  6  remainder 0
 6 ÷ 2 =  3  remainder 0
 3 ÷ 2 =  1  remainder 1
 1 ÷ 2 =  0  remainder 1  ← MSB

Binary: 11001
```

**Verification:**
```
1×2⁴ + 1×2³ + 0×2² + 0×2¹ + 1×2⁰
= 16 +  8  +  0  +  0  +  1
= 25 ✓
```

#### Example 3: Convert 100 to Binary

```
100 ÷ 2 = 50  remainder 0  ← LSB
 50 ÷ 2 = 25  remainder 0
 25 ÷ 2 = 12  remainder 1
 12 ÷ 2 =  6  remainder 0
  6 ÷ 2 =  3  remainder 0
  3 ÷ 2 =  1  remainder 1
  1 ÷ 2 =  0  remainder 1  ← MSB

Binary: 1100100
```

#### Quick Trick: Powers of 2
Memorize these for faster mental conversion:

```
2⁰  = 1
2¹  = 2
2²  = 4
2³  = 8
2⁴  = 16
2⁵  = 32
2⁶  = 64
2⁷  = 128
2⁸  = 256
2⁹  = 512
2¹⁰ = 1024  (Remember: 1 KB ≈ 1024 bytes)
2¹⁶ = 65536
2³¹ = 2,147,483,648  (Max int + 1)
```

**Using Powers of 2 for Conversion:**

Convert 13 using powers:
```
13 is closest to 8 (2³), remainder is 5
 5 is closest to 4 (2²), remainder is 1
 1 is          1 (2⁰), remainder is 0

Position: 3 2 1 0
Powers:   8 4 2 1
Binary:   1 1 0 1
```

---

### Binary to Decimal Conversion

#### Method: Positional Notation

**Formula:** Sum of (digit × 2^position) for each bit

#### Example 1: Convert 1011 to Decimal

```
Position:  3  2  1  0  ← Right to left, starting from 0
Binary:    1  0  1  1
Power:     2³ 2² 2¹ 2⁰
Value:     8  4  2  1

Calculation:
1×2³ + 0×2² + 1×2¹ + 1×2⁰
= 8  + 0   + 2   + 1
= 11
```

#### Example 2: Convert 10110 to Decimal

```
Position:  4  3  2  1  0
Binary:    1  0  1  1  0
Power:     2⁴ 2³ 2² 2¹ 2⁰
Value:     16 8  4  2  1

Calculation:
1×2⁴ + 0×2³ + 1×2² + 1×2¹ + 0×2⁰
= 16 + 0   + 4   + 2   + 0
= 22
```

#### Example 3: Convert 11111111 to Decimal (8 bits)

```
Position:  7  6  5  4  3  2  1  0
Binary:    1  1  1  1  1  1  1  1
Value:     128 64 32 16 8  4  2  1

Sum = 128 + 64 + 32 + 16 + 8 + 4 + 2 + 1
    = 255

Quick formula: 2^n - 1
For 8 bits: 2^8 - 1 = 256 - 1 = 255
```

---

### Understanding Bits and Bytes in Memory

#### How a Byte Stores Numbers

**1 Byte = 8 Bits**

```
┌───┬───┬───┬───┬───┬───┬───┬───┐
│ 7 │ 6 │ 5 │ 4 │ 3 │ 2 │ 1 │ 0 │ ← Bit positions
└───┴───┴───┴───┴───┴───┴───┴───┘
  ↑                               ↑
  MSB                            LSB
  (Most Significant)      (Least Significant)
```

**Unsigned (Positive only):**
- Range: 0 to 255 (2^8 - 1)
- All 8 bits used for magnitude

**Signed (Positive & Negative):**
- Range: -128 to 127
- 1 bit for sign, 7 bits for magnitude
- Uses **Two's Complement** representation

#### Two's Complement Representation

**Why Two's Complement?**
- Simplifies arithmetic operations
- Single representation for zero
- Same hardware for addition/subtraction

**How to find Two's Complement:**
1. Invert all bits (0→1, 1→0)
2. Add 1

**Example: Represent -5 in 8 bits**
```
Step 1: Binary of 5 = 00000101
Step 2: Invert bits  = 11111010
Step 3: Add 1        = 11111011

So, -5 = 11111011
```

**Verification:**
```
Sign bit (MSB) is 1 → Negative number
Remaining bits: 1111011

To get magnitude:
Invert: 0000100
Add 1:  0000101 = 5

So, 11111011 = -5 ✓
```

#### Why -128 to 127 (not -127 to 127)?

```
Positive numbers: 0 to 127  (0000000 to 01111111) → 128 values
Negative numbers: -1 to -128 (11111111 to 10000000) → 128 values

Total: 256 values (2^8)

Special case:
10000000 = -128 (most negative number)
01111111 = +127 (most positive number)
```

---

### Binary Arithmetic

#### Addition
```
  0 + 0 = 0
  0 + 1 = 1
  1 + 0 = 1
  1 + 1 = 10  (0 with carry 1)
```

**Example: 1011 + 1101**
```
    1 0 1 1   (11)
  + 1 1 0 1   (13)
  ---------
   1 1 0 0 0  (24)
    ↑
  Carry
```

#### Subtraction
```
  0 - 0 = 0
  1 - 0 = 1
  1 - 1 = 0
  0 - 1 = 1  (with borrow)
```

#### Multiplication
```
  0 × 0 = 0
  0 × 1 = 0
  1 × 0 = 0
  1 × 1 = 1
```

---

## Memory and Storage

### Memory Hierarchy

```
┌────────────────────────────────────────┐
│         CPU Registers                   │  ← Fastest, Smallest
│         (Bytes)                         │
├────────────────────────────────────────┤
│         CPU Cache (L1, L2, L3)         │
│         (KB to MB)                      │
├────────────────────────────────────────┤
│         RAM (Main Memory)               │
│         (GB)                            │
├────────────────────────────────────────┤
│         SSD/Hard Disk                   │
│         (TB)                            │
├────────────────────────────────────────┤
│         Cloud Storage                   │  ← Slowest, Largest
│         (Unlimited)                     │
└────────────────────────────────────────┘
```

### Memory Units

```
1 Bit   = Smallest unit (0 or 1)
1 Byte  = 8 Bits
1 KB    = 1024 Bytes      = 2^10 bytes
1 MB    = 1024 KB         = 2^20 bytes
1 GB    = 1024 MB         = 2^30 bytes
1 TB    = 1024 GB         = 2^40 bytes
1 PB    = 1024 TB         = 2^50 bytes
```

**Why 1024 and not 1000?**
- Computers work in binary (base-2)
- 2^10 = 1024 (closest to 1000)
- This is called **binary prefix** (KiB, MiB, GiB)
- Marketing often uses 1000 (decimal prefix) to show larger numbers

### Stack vs Heap Memory in Java

#### Stack Memory
```
┌─────────────────────────────────┐
│  Method Calls & Local Variables  │
│                                  │
│  main() {                        │
│    int x = 5;  ← Stored here     │
│    func(x);                      │
│  }                               │
│                                  │
│  func(int a) {                   │
│    int b = 10; ← Stored here     │
│  }                               │
└─────────────────────────────────┘
```

**Characteristics:**
- **Size:** Small (typically 1 MB)
- **Speed:** Very fast (LIFO - Last In First Out)
- **Storage:** Method calls, primitive variables, object references
- **Lifetime:** Variables destroyed when method returns
- **Thread-safe:** Each thread has its own stack

#### Heap Memory
```
┌─────────────────────────────────┐
│  Objects & Arrays                │
│                                  │
│  new Student("John")  ← Stored   │
│  new int[100]         ← Stored   │
│  new ArrayList<>()    ← Stored   │
│                                  │
└─────────────────────────────────┘
```

**Characteristics:**
- **Size:** Large (Limited by available RAM)
- **Speed:** Slower than stack
- **Storage:** All objects created with `new`
- **Lifetime:** Until garbage collected
- **Shared:** All threads access same heap

#### Example:
```java
public void example() {
    int x = 5;              // Stack
    int[] arr = new int[3]; // Reference on stack, array on heap
    
    // Stack: x (value 5), arr (reference/address)
    // Heap:  [0, 0, 0] (actual array)
}
```

---

## Data Types in Java

### Primitive vs Reference Types

```
┌──────────────────────────────────────┐
│         Java Data Types               │
├──────────────────┬───────────────────┤
│   Primitive      │   Reference        │
│   (8 types)      │   (Infinite)       │
├──────────────────┼───────────────────┤
│   byte           │   String           │
│   short          │   Arrays           │
│   int            │   Classes          │
│   long           │   Interfaces       │
│   float          │   Enums            │
│   double         │   etc.             │
│   char           │                    │
│   boolean        │                    │
└──────────────────┴───────────────────┘
```

---

### 1. Integer Data Types

#### byte
```java
byte b = 127;
```

**Properties:**
- **Size:** 8 bits (1 byte)
- **Range:** -128 to 127
- **Formula:** -2^7 to 2^7-1
- **Memory:** Smallest integer type
- **Use Case:** 
  - Saving memory in large arrays
  - Working with streams/files (byte data)
  - Network programming (byte packets)

**Example:**
```java
byte age = 25;
byte temperature = -10;

// Overflow example
byte max = 127;
max++;  // Becomes -128 (wraps around)
```

**Binary Representation:**
```
127  = 01111111  (max positive)
-128 = 10000000  (max negative)
0    = 00000000
-1   = 11111111
```

---

#### short
```java
short s = 32767;
```

**Properties:**
- **Size:** 16 bits (2 bytes)
- **Range:** -32,768 to 32,767
- **Formula:** -2^15 to 2^15-1
- **Use Case:** 
  - Rarely used in modern Java
  - Memory-critical applications
  - Legacy code compatibility

**Example:**
```java
short year = 2024;
short price = 15000;

// Range demonstration
short max = Short.MAX_VALUE;  // 32767
short min = Short.MIN_VALUE;  // -32768
```

---

#### int (MOST IMPORTANT)
```java
int i = 2147483647;
```

**Properties:**
- **Size:** 32 bits (4 bytes)
- **Range:** -2,147,483,648 to 2,147,483,647
- **Formula:** -2^31 to 2^31-1
- **Default choice** for integers in Java
- **Use Case:** 
  - 99% of integer operations
  - Loop counters
  - Array indices
  - Most calculations

**Why int is default:**
- Balances memory and range
- Most hardware optimized for 32-bit operations
- Sufficient for most use cases

**Example:**
```java
int count = 100;
int population = 1400000000;  // India's population

// Binary literals (Java 7+)
int binary = 0b1010;  // 10 in decimal

// Underscore for readability (Java 7+)
int million = 1_000_000;

// Range
int max = Integer.MAX_VALUE;  // 2147483647
int min = Integer.MIN_VALUE;  // -2147483648
```

**Important:** 2^31-1 = 2,147,483,647 ≈ **2.1 billion**
- Most DSA problems use int
- If answer can exceed 2.1 billion, use long

---

#### long
```java
long l = 9223372036854775807L;  // Note the 'L'
```

**Properties:**
- **Size:** 64 bits (8 bytes)
- **Range:** -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807
- **Formula:** -2^63 to 2^63-1
- **Suffix:** Must use 'L' or 'l' suffix
- **Use Case:**
  - Large numbers (>2 billion)
  - Time in milliseconds
  - File sizes
  - Large calculations (factorial, combinations)

**Example:**
```java
long bigNumber = 10000000000L;  // 10 billion
long milliseconds = System.currentTimeMillis();

// Without L suffix - ERROR!
// long x = 10000000000;  // Won't compile!

// Correct way
long x = 10000000000L;
```

**DSA Use Cases:**
```java
// Factorial of large numbers
long factorial = 1L;
for (int i = 1; i <= 20; i++) {
    factorial *= i;
}

// Counting operations
long operations = (long) n * n;  // Avoid overflow
```

---

### Integer Type Comparison

| Type | Bytes | Bits | Range | When to Use |
|------|-------|------|-------|-------------|
| `byte` | 1 | 8 | -128 to 127 | Memory-critical, byte data |
| `short` | 2 | 16 | -32K to 32K | Rarely used |
| `int` | 4 | 32 | -2.1B to 2.1B | **Default choice** |
| `long` | 8 | 64 | ±9.2 quintillion | Large numbers |

---

### 2. Floating-Point Data Types

#### float
```java
float f = 3.14159f;  // Note the 'f'
```

**Properties:**
- **Size:** 32 bits (4 bytes)
- **Precision:** 6-7 decimal digits
- **Range:** ±3.4E38 (±3.4 × 10^38)
- **Suffix:** Must use 'f' or 'F'
- **Use Case:**
  - Graphics programming (3D coordinates)
  - Scientific calculations (when precision less critical)
  - Memory-constrained applications

**Example:**
```java
float pi = 3.14159f;
float price = 19.99f;

// Scientific notation
float avogadro = 6.022e23f;  // 6.022 × 10^23

// Without f suffix - ERROR!
// float x = 3.14;  // Treated as double by default
```

**Precision Limitation:**
```java
float a = 1.0f / 3.0f;  // 0.33333334 (rounded)
```

---

#### double (MOST IMPORTANT for decimals)
```java
double d = 3.141592653589793;
```

**Properties:**
- **Size:** 64 bits (8 bytes)
- **Precision:** 15 decimal digits
- **Range:** ±1.7E308 (±1.7 × 10^308)
- **Default** for decimal numbers
- **Use Case:**
  - Scientific calculations
  - Financial calculations (with caution)
  - Most decimal operations

**Example:**
```java
double pi = 3.141592653589793;
double price = 19.99;  // No suffix needed (default)

// Scientific notation
double planck = 6.62607015e-34;  // Planck constant

// Range
double max = Double.MAX_VALUE;
double min = Double.MIN_VALUE;
```

**Important for DSA:**
```java
// Average of two numbers
double avg = (a + b) / 2.0;  // Use 2.0, not 2

// Comparing doubles
double x = 0.1 + 0.2;  // 0.30000000000000004 (not exactly 0.3!)
// Never use == for doubles!
if (Math.abs(x - 0.3) < 1e-9) {  // Use epsilon comparison
    // Consider them equal
}
```

---

### Floating-Point Comparison

| Type | Bytes | Bits | Precision | When to Use |
|------|-------|------|-----------|-------------|
| `float` | 4 | 32 | 6-7 digits | Graphics, memory-critical |
| `double` | 8 | 64 | 15 digits | **Default choice** |

**⚠️ Warning: Never use float/double for money!**
```java
// WRONG!
double money = 0.1 + 0.2;  // 0.30000000000000004

// RIGHT! Use BigDecimal for financial calculations
BigDecimal money = new BigDecimal("0.1").add(new BigDecimal("0.2"));
```

---

### 3. Character Data Type

#### char
```java
char c = 'A';
```

**Properties:**
- **Size:** 16 bits (2 bytes)
- **Range:** 0 to 65,535 (unsigned)
- **Encoding:** Unicode (UTF-16)
- **Syntax:** Single quotes `'A'` not double `"A"`
- **Use Case:**
  - Single character storage
  - Character manipulation
  - String processing

**Example:**
```java
char letter = 'A';
char digit = '5';
char symbol = '$';

// Unicode representation
char heart = '\u2764';  // ❤
char rupee = '\u20B9';  // ₹

// ASCII values
char a = 65;   // 'A'
char b = 97;   // 'a'

// Character arithmetic
char ch = 'A';
char next = (char)(ch + 1);  // 'B'
```

**Important ASCII Values:**
```
'A' to 'Z' : 65 to 90
'a' to 'z' : 97 to 122
'0' to '9' : 48 to 57
Space ' '  : 32
```

**DSA Use Cases:**
```java
// Check if uppercase
if (ch >= 'A' && ch <= 'Z') { }

// Convert to lowercase
char lower = (char)(ch + 32);  // or Character.toLowerCase(ch)

// Convert to uppercase
char upper = (char)(ch - 32);  // or Character.toUpperCase(ch)

// Check if digit
if (ch >= '0' && ch <= '9') { }

// Convert char digit to int
int num = ch - '0';  // '5' - '0' = 5
```

---

### 4. Boolean Data Type

#### boolean
```java
boolean flag = true;
```

**Properties:**
- **Size:** Not precisely defined (JVM dependent, typically 1 bit but may use 1 byte)
- **Values:** Only `true` or `false`
- **NOT 0 or 1** (unlike C/C++)
- **Use Case:**
  - Conditions and flags
  - Control flow
  - State tracking

**Example:**
```java
boolean isValid = true;
boolean found = false;

// Comparison result
boolean isGreater = 10 > 5;  // true

// Logical operations
boolean result = (x > 0) && (y < 10);

// Cannot use numbers as boolean (UNLIKE C++)
// boolean b = 1;  // ERROR in Java!
```

**DSA Use Cases:**
```java
// Visited array in graphs
boolean[] visited = new boolean[n];

// Flag in algorithms
boolean found = false;
for (int num : array) {
    if (num == target) {
        found = true;
        break;
    }
}

// State tracking
boolean isPalindrome = true;
boolean isSorted = true;
```

---

## Complete Data Types Summary Table

| Type | Size | Range | Default | Suffix | Wrapper Class |
|------|------|-------|---------|--------|---------------|
| `byte` | 1 byte | -128 to 127 | 0 | - | Byte |
| `short` | 2 bytes | -32,768 to 32,767 | 0 | - | Short |
| `int` | 4 bytes | -2,147,483,648 to 2,147,483,647 | 0 | - | Integer |
| `long` | 8 bytes | -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807 | 0L | L | Long |
| `float` | 4 bytes | ±3.4E38 | 0.0f | f | Float |
| `double` | 8 bytes | ±1.7E308 | 0.0 | - | Double |
| `char` | 2 bytes | 0 to 65,535 | '\u0000' | - | Character |
| `boolean` | ~1 bit | true or false | false | - | Boolean |

---

## Type Conversion and Casting

### Implicit Type Conversion (Widening)

**Automatic** conversion from smaller to larger type.

**Hierarchy (small → large):**
```
byte → short → int → long → float → double
         ↓
        char
```

**Example:**
```java
byte b = 10;
int i = b;      // Automatic (byte → int)
long l = i;     // Automatic (int → long)
float f = l;    // Automatic (long → float)
double d = f;   // Automatic (float → double)

char c = 'A';
int ascii = c;  // Automatic (char → int) → 65
```

**Rules:**
1. No data loss
2. Destination type larger than source
3. Happens automatically

---

### Explicit Type Conversion (Narrowing)

**Manual** conversion from larger to smaller type using cast operator `(type)`.

**Example:**
```java
double d = 9.7;
int i = (int)d;      // i = 9 (decimal part lost)

long l = 100L;
int i2 = (int)l;     // Manual cast required

float f = 3.14f;
int i3 = (int)f;     // i3 = 3
```

**⚠️ Data Loss Warning:**
```java
int big = 130;
byte small = (byte)big;  // small = -126 (overflow!)

// Why? byte range is -128 to 127
// 130 wraps around to -126
```

---

### Type Promotion in Expressions

**Rules:**
1. byte, short, char promoted to **int** in expressions
2. If any operand is long, whole expression promoted to **long**
3. If any operand is float, whole expression promoted to **float**
4. If any operand is double, whole expression promoted to **double**

**Example:**
```java
byte a = 10;
byte b = 20;
byte c = (byte)(a + b);  // Cast needed! a+b is int

int x = 5;
double y = 2.5;
double result = x + y;  // result is double (int promoted)

// Careful with division!
int num1 = 5;
int num2 = 2;
double div1 = num1 / num2;      // 2.0 (integer division, then stored in double)
double div2 = (double)num1 / num2;  // 2.5 (correct!)
double div3 = num1 / 2.0;       // 2.5 (2.0 makes it double division)
```

---

### Common Casting Scenarios

#### 1. Division with decimal result
```java
int a = 5, b = 2;

// WRONG
double result1 = a / b;  // 2.0 (not 2.5!)

// CORRECT
double result2 = (double)a / b;  // 2.5
double result3 = a / (double)b;  // 2.5
double result4 = a / 2.0;        // 2.5
double result5 = 1.0 * a / b;    // 2.5
```

#### 2. Large number calculations
```java
int n = 100000;
int result1 = n * n;  // OVERFLOW! Result is negative

// CORRECT
long result2 = (long)n * n;  // Cast one operand to long
long result3 = 1L * n * n;   // Multiply by 1L to make it long
```

#### 3. Character conversions
```java
char ch = 'A';
int ascii = ch;           // Implicit (65)
int num = ch - '0';       // For digit chars: '5' - '0' = 5
char upper = (char)(ch - 32);  // 'a' → 'A'
```

---

## Operators in Java

### 1. Arithmetic Operators

| Operator | Name | Example | Result |
|----------|------|---------|--------|
| `+` | Addition | 5 + 3 | 8 |
| `-` | Subtraction | 5 - 3 | 2 |
| `*` | Multiplication | 5 * 3 | 15 |
| `/` | Division | 5 / 2 | 2 (integer division) |
| `%` | Modulus (Remainder) | 5 % 2 | 1 |

**Important Points:**

#### Integer Division
```java
int a = 7 / 2;      // 3 (not 3.5!)
double b = 7 / 2;   // 3.0 (still integer division!)
double c = 7.0 / 2; // 3.5 (correct)
double d = 7 / 2.0; // 3.5 (correct)
```

#### Modulus (%)
```java
10 % 3  = 1   // Remainder when 10 divided by 3
15 % 4  = 3
17 % 5  = 2

// Negative modulus
-10 % 3  = -1  // Sign of dividend
10 % -3  = 1
-10 % -3 = -1
```

**DSA Use Cases for Modulus:**
```java
// Check even/odd
if (n % 2 == 0) { /* even */ }

// Circular array index
int idx = (i % n + n) % n;  // Handles negative indices

// Last digit
int lastDigit = num % 10;

// Cycling through range (0 to k-1)
int cyclic = i % k;

// Modular arithmetic (competitive programming)
int result = (a + b) % MOD;
```

---

### 2. Unary Operators

| Operator | Name | Description | Example |
|----------|------|-------------|---------|
| `+` | Unary plus | Indicates positive (rarely used) | +5 |
| `-` | Unary minus | Negates value | -5 |
| `++` | Increment | Increases by 1 | i++ or ++i |
| `--` | Decrement | Decreases by 1 | i-- or --i |
| `!` | Logical NOT | Inverts boolean | !true = false |

#### Pre vs Post Increment/Decrement

**Pre-increment (++i):** Increment first, then use
```java
int i = 5;
int j = ++i;  // i becomes 6, then j = 6
// Result: i = 6, j = 6
```

**Post-increment (i++):** Use first, then increment
```java
int i = 5;
int j = i++;  // j = 5 (current value), then i becomes 6
// Result: i = 6, j = 5
```

**Examples:**
```java
int x = 5;
System.out.println(x++);  // Prints 5, then x becomes 6
System.out.println(++x);  // x becomes 7, then prints 7

int a = 10;
int b = ++a + a++;  // (11) + 11, then a becomes 12
                    // b = 22, a = 12

int c = 10;
int d = c++ + ++c;  // 10 + 12 (c becomes 11, then 12)
                    // d = 22, c = 12
```

**⚠️ Best Practice:** Avoid complex expressions with increment/decrement!
```java
// CONFUSING - DON'T DO THIS
int x = i++ + ++i + i--;

// CLEAR - DO THIS
i++;
int x = i;
```

**DSA Use Cases:**
```java
// Simple loop counter (post-increment is standard)
for (int i = 0; i < n; i++) { }  // NOT ++i in Java convention

// Reading values
while (i < n) {
    arr[count++] = input.nextInt();  // Assign then increment
}
```

---

### 3. Relational (Comparison) Operators

| Operator | Name | Example | Result |
|----------|------|---------|--------|
| `==` | Equal to | 5 == 5 | true |
| `!=` | Not equal to | 5 != 3 | true |
| `>` | Greater than | 5 > 3 | true |
| `<` | Less than | 5 < 3 | false |
| `>=` | Greater than or equal | 5 >= 5 | true |
| `<=` | Less than or equal | 5 <= 3 | false |

**Important:**
- Result is always **boolean** (true/false)
- Don't confuse `=` (assignment) with `==` (comparison)

**Examples:**
```java
int a = 10, b = 20;
boolean result;

result = (a == b);   // false
result = (a != b);   // true
result = (a < b);    // true
result = (a > b);    // false
result = (a <= 10);  // true
result = (a >= 10);  // true
```

**⚠️ String Comparison Warning:**
```java
String s1 = "hello";
String s2 = "hello";

// WRONG! Compares references, not content
if (s1 == s2) { }  // May work, but unreliable

// CORRECT! Compares content
if (s1.equals(s2)) { }
```

---

### 4. Logical Operators

| Operator | Name | Description | Example |
|----------|------|-------------|---------|
| `&&` | Logical AND | Both must be true | true && false = false |
| `\|\|` | Logical OR | At least one true | true \|\| false = true |
| `!` | Logical NOT | Inverts value | !true = false |

**Truth Tables:**

#### AND (&&)
```
A     B     A && B
true  true  true
true  false false
false true  false
false false false
```

#### OR (||)
```
A     B     A || B
true  true  true
true  false true
false true  true
false false false
```

#### NOT (!)
```
A     !A
true  false
false true
```

**Examples:**
```java
int age = 25;
boolean hasLicense = true;

// Can drive if age >= 18 AND has license
boolean canDrive = (age >= 18) && hasLicense;  // true

// Weekend if Saturday OR Sunday
boolean isWeekend = (day == 6) || (day == 7);

// Not equal
boolean notEqual = !(a == b);  // Same as: a != b
```

---

### Short-Circuit Evaluation

**Important Optimization:**
- `&&`: If first is false, second is **not evaluated**
- `||`: If first is true, second is **not evaluated**

**Examples:**
```java
int x = 5;

// Second condition never checked (x > 10 is false)
if (x > 10 && x < 20) {
    x++;  // This won't execute
}
System.out.println(x);  // 5 (not incremented)

// Second condition never checked (x < 10 is true)
if (x < 10 || x++ < 20) {
    // ...
}
System.out.println(x);  // 5 (x++ never executed)
```

**Practical Use:**
```java
// Avoid NullPointerException
if (str != null && str.length() > 0) {  // Safe!
    // str.length() not called if str is null
}

// Avoid ArrayIndexOutOfBoundsException
if (i < arr.length && arr[i] == target) {  // Safe!
    // arr[i] not accessed if i >= arr.length
}
```

---

### 5. Bitwise Operators

| Operator | Name | Description | Example |
|----------|------|-------------|---------|
| `&` | AND | 1 if both bits are 1 | 5 & 3 = 1 |
| `\|` | OR | 1 if at least one bit is 1 | 5 \| 3 = 7 |
| `^` | XOR | 1 if bits are different | 5 ^ 3 = 6 |
| `~` | NOT | Inverts all bits | ~5 = -6 |
| `<<` | Left Shift | Shift bits left | 5 << 1 = 10 |
| `>>` | Right Shift | Shift bits right | 5 >> 1 = 2 |
| `>>>` | Unsigned Right Shift | Shift right, fill with 0 | -5 >>> 1 |

**Detailed Explanation:**

#### Bitwise AND (&)
```
  5 = 0101
  3 = 0011
  ---------
  1 = 0001  (1 only where both have 1)
```

**Use Cases:**
```java
// Check if even
boolean isEven = (n & 1) == 0;  // Last bit is 0 for even

// Get ith bit
int ithBit = (num >> i) & 1;

// Clear last k bits
num = num & (~((1 << k) - 1));
```

#### Bitwise OR (|)
```
  5 = 0101
  3 = 0011
  ---------
  7 = 0111  (1 if at least one has 1)
```

**Use Cases:**
```java
// Set ith bit to 1
num = num | (1 << i);

// Merge flags
int permissions = READ | WRITE | EXECUTE;
```

#### Bitwise XOR (^)
```
  5 = 0101
  3 = 0011
  ---------
  6 = 0110  (1 if bits are different)
```

**Properties:**
- a ^ a = 0
- a ^ 0 = a
- a ^ b ^ a = b (cancellation)

**Use Cases:**
```java
// Swap without temp variable
a = a ^ b;
b = a ^ b;
a = a ^ b;

// Find unique element (all others appear twice)
int unique = 0;
for (int num : arr) {
    unique ^= num;  // Duplicates cancel out
}

// Toggle ith bit
num = num ^ (1 << i);
```

#### Bitwise NOT (~)
```
  5 = 00000101
  ~ = 11111010  (inverts all bits)
    = -6 (in two's complement)
```

**Formula:** ~n = -(n+1)

#### Left Shift (<<)
```
  5 = 0101
  5 << 1 = 1010 = 10  (multiply by 2)
  5 << 2 = 10100 = 20 (multiply by 4)
```

**Formula:** n << k = n × 2^k

**Use Cases:**
```java
// Fast multiplication by powers of 2
int double_num = n << 1;  // n * 2
int quad_num = n << 2;    // n * 4

// Create mask
int mask = 1 << i;  // Only ith bit is 1

// Powers of 2
int powerOf2 = 1 << k;  // 2^k
```

#### Right Shift (>>)
```
  5 = 0101
  5 >> 1 = 0010 = 2  (divide by 2)
  5 >> 2 = 0001 = 1  (divide by 4)
```

**Formula:** n >> k = n / 2^k (integer division)

**Use Cases:**
```java
// Fast division by powers of 2
int half = n >> 1;     // n / 2
int quarter = n >> 2;  // n / 4

// Get ith bit
int bit = (n >> i) & 1;

// Binary search optimization
int mid = (left + right) >> 1;  // Instead of (left + right) / 2
```

#### Unsigned Right Shift (>>>)
- Fills leftmost bits with 0 (not sign bit)
- Used for logical shift on negative numbers

```java
int n = -5;
System.out.println(n >> 1);   // -3 (sign extended)
System.out.println(n >>> 1);  // 2147483645 (zero filled)
```

---

### Bitwise Tricks for DSA

```java
// 1. Check if power of 2
boolean isPowerOf2 = (n > 0) && ((n & (n - 1)) == 0);

// 2. Count set bits (Brian Kernighan's Algorithm)
int count = 0;
while (n > 0) {
    n = n & (n - 1);  // Removes rightmost set bit
    count++;
}

// 3. Get rightmost set bit
int rightmost = n & (-n);

// 4. Turn off rightmost set bit
n = n & (n - 1);

// 5. Check if ith bit is set
boolean isSet = ((n >> i) & 1) == 1;

// 6. Set ith bit
n = n | (1 << i);

// 7. Clear ith bit
n = n & ~(1 << i);

// 8. Toggle ith bit
n = n ^ (1 << i);

// 9. Check if even/odd
boolean isOdd = (n & 1) == 1;

// 10. Multiply/Divide by 2^k
int mult = n << k;  // n * 2^k
int div = n >> k;   // n / 2^k
```

---

### 6. Assignment Operators

| Operator | Example | Equivalent To |
|----------|---------|---------------|
| `=` | a = 5 | Simple assignment |
| `+=` | a += 5 | a = a + 5 |
| `-=` | a -= 5 | a = a - 5 |
| `*=` | a *= 5 | a = a * 5 |
| `/=` | a /= 5 | a = a / 5 |
| `%=` | a %= 5 | a = a % 5 |
| `&=` | a &= 5 | a = a & 5 |
| `\|=` | a \|= 5 | a = a \| 5 |
| `^=` | a ^= 5 | a = a ^ 5 |
| `<<=` | a <<= 2 | a = a << 2 |
| `>>=` | a >>= 2 | a = a >> 2 |
| `>>>=` | a >>>= 2 | a = a >>> 2 |

**Examples:**
```java
int x = 10;

x += 5;   // x = 15
x -= 3;   // x = 12
x *= 2;   // x = 24
x /= 4;   // x = 6
x %= 4;   // x = 2

x <<= 3;  // x = 16 (2 * 2^3)
x >>= 2;  // x = 4  (16 / 2^2)
```

---

### 7. Ternary Operator (? :)

**Syntax:** `condition ? valueIfTrue : valueIfFalse`

**Examples:**
```java
// Simple if-else replacement
int max = (a > b) ? a : b;

// Nested ternary (avoid if complex!)
int result = (a > b) ? ((a > c) ? a : c) : ((b > c) ? b : c);

// With expressions
String status = (age >= 18) ? "Adult" : "Minor";

// In assignments
int absValue = (num >= 0) ? num : -num;
```

**When to use:**
- Simple conditions
- Single-line assignments
- Don't nest too many (reduces readability)

---

### 8. instanceof Operator

Check if an object is an instance of a class.

```java
String str = "Hello";
boolean result = str instanceof String;  // true

Object obj = "Test";
if (obj instanceof String) {
    String s = (String) obj;  // Safe cast
}
```

---

## Operator Precedence (Highest to Lowest)

| Level | Operators | Associativity |
|-------|-----------|---------------|
| 1 | `()` `[]` `.` | Left to Right |
| 2 | `++` `--` `!` `~` `+` `-` (unary) | Right to Left |
| 3 | `*` `/` `%` | Left to Right |
| 4 | `+` `-` | Left to Right |
| 5 | `<<` `>>` `>>>` | Left to Right |
| 6 | `<` `<=` `>` `>=` `instanceof` | Left to Right |
| 7 | `==` `!=` | Left to Right |
| 8 | `&` | Left to Right |
| 9 | `^` | Left to Right |
| 10 | `\|` | Left to Right |
| 11 | `&&` | Left to Right |
| 12 | `\|\|` | Left to Right |
| 13 | `?:` | Right to Left |
| 14 | `=` `+=` `-=` etc. | Right to Left |

**Examples:**
```java
int result = 2 + 3 * 4;     // 14 (not 20)
int result = (2 + 3) * 4;   // 20 (parentheses first)

boolean b = 5 > 3 && 10 < 20;  // true
boolean c = true || false && false;  // true (AND before OR)
```

**⚠️ Best Practice:** When in doubt, use parentheses!

---

## Input/Output in Java

### Output: System.out

```java
// Print without newline
System.out.print("Hello");
System.out.print(" World");  // Output: Hello World

// Print with newline
System.out.println("Hello");
System.out.println("World");  
// Output:
// Hello
// World

// Formatted output
System.out.printf("Name: %s, Age: %d%n", "John", 25);
// Output: Name: John, Age: 25

// Format specifiers
System.out.printf("%d", 10);      // Integer
System.out.printf("%f", 3.14);    // Float (6 decimal places)
System.out.printf("%.2f", 3.14159);  // 2 decimal places → 3.14
System.out.printf("%s", "text");  // String
System.out.printf("%c", 'A');     // Character
System.out.printf("%b", true);    // Boolean
```

---

### Input: Scanner Class

```java
import java.util.Scanner;

public class InputDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Reading different types
        System.out.print("Enter integer: ");
        int num = sc.nextInt();
        
        System.out.print("Enter double: ");
        double d = sc.nextDouble();
        
        System.out.print("Enter string (one word): ");
        String word = sc.next();
        
        System.out.print("Enter line: ");
        sc.nextLine();  // Consume leftover newline
        String line = sc.nextLine();
        
        System.out.print("Enter character: ");
        char ch = sc.next().charAt(0);
        
        System.out.print("Enter boolean: ");
        boolean bool = sc.nextBoolean();
        
        // Close scanner (good practice)
        sc.close();
    }
}
```

**Common Methods:**
- `nextInt()` - reads integer
- `nextLong()` - reads long
- `nextDouble()` - reads double
- `nextFloat()` - reads float
- `next()` - reads one word (till space)
- `nextLine()` - reads entire line
- `nextBoolean()` - reads boolean

**⚠️ Common Pitfall:**
```java
Scanner sc = new Scanner(System.in);

int num = sc.nextInt();     // Reads number, leaves newline
String line = sc.nextLine(); // Reads the newline (empty string!)

// FIX: Consume the newline
int num = sc.nextInt();
sc.nextLine();  // Consume newline
String line = sc.nextLine();  // Now reads actual input
```

---

### Fast Input/Output (For Competitive Programming)

```java
import java.io.*;
import java.util.*;

class FastIO {
    BufferedReader br;
    StringTokenizer st;
    
    public FastIO() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }
    
    String next() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }
    
    int nextInt() {
        return Integer.parseInt(next());
    }
    
    long nextLong() {
        return Long.parseLong(next());
    }
    
    double nextDouble() {
        return Double.parseDouble(next());
    }
    
    String nextLine() {
        String str = "";
        try {
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
```

---

## Common Pitfalls and Best Practices

### 1. Integer Overflow

**Problem:**
```java
int a = 1000000;
int b = 1000000;
int c = a * b;  // OVERFLOW! Result is negative
```

**Solution:**
```java
long c = (long)a * b;  // Cast one operand to long
```

---

### 2. Floating-Point Precision

**Problem:**
```java
double a = 0.1 + 0.2;  // 0.30000000000000004 (not 0.3!)
if (a == 0.3) {  // FALSE!
    // Won't execute
}
```

**Solution:**
```java
final double EPSILON = 1e-9;
if (Math.abs(a - 0.3) < EPSILON) {  // TRUE
    // Will execute
}
```

---

### 3. Division by Zero

**Integer Division:**
```java
int a = 10 / 0;  // ArithmeticException: / by zero
```

**Floating-Point Division:**
```java
double a = 10.0 / 0;  // Infinity (no exception!)
double b = 0.0 / 0;   // NaN (Not a Number)
```

---

### 4. Array Index Out of Bounds

**Problem:**
```java
int[] arr = new int[5];  // Indices: 0 to 4
int x = arr[5];  // ArrayIndexOutOfBoundsException
```

**Solution:**
```java
if (i >= 0 && i < arr.length) {
    int x = arr[i];  // Safe
}
```

---

### 5. == vs equals() for Strings

**Problem:**
```java
String s1 = new String("hello");
String s2 = new String("hello");
if (s1 == s2) {  // FALSE! Compares references
    //...
}
```

**Solution:**
```java
if (s1.equals(s2)) {  // TRUE! Compares content
    //...
}
```

---

### 6. Integer Division Instead of Double

**Problem:**
```java
double avg = (a + b) / 2;  // If a,b are int, result is int!
```

**Solution:**
```java
double avg = (a + b) / 2.0;  // Use 2.0 to force double division
double avg = (double)(a + b) / 2;  // Or cast
```

---

### 7. Uninitialized Variables

**Problem:**
```java
int x;
System.out.println(x);  // Compile error: variable not initialized
```

**Solution:**
```java
int x = 0;  // Always initialize variables
```

---

### 8. Modifying Loop Counter Inside Loop

**Problem:**
```java
for (int i = 0; i < n; i++) {
    if (condition) {
        i++;  // DON'T DO THIS! Confusing behavior
    }
}
```

**Solution:**
Use while loop if you need complex counter modifications.

---

## DSA Relevance

### Data Types in DSA

**When to use each type:**

| Type | DSA Use Case |
|------|--------------|
| `int` | Array indices, counters, most calculations |
| `long` | Large numbers, factorial, combinations, time calculations |
| `double` | Probabilities, averages, ratios, geometric calculations |
| `char` | String manipulation, character-based problems |
| `boolean` | Flags, visited arrays, state tracking |
| `byte` | Space-critical applications (rare) |

---

### Operators in DSA

**Arithmetic:**
- `/` : Finding middle in binary search
- `%` : Circular arrays, modular arithmetic, even/odd checks
- `+` `-` : Basic calculations

**Bitwise:**
- `&` : Check/clear bits, even/odd check
- `|` : Set bits
- `^` : Find unique elements, swap variables
- `<<` `>>` : Fast multiplication/division by 2^k
- Bit manipulation is crucial for optimization!

**Logical:**
- `&&` `||` : Conditions, short-circuit evaluation
- `!` : Negation

---

### Common DSA Patterns with These Concepts

#### 1. Even/Odd Check
```java
// Using modulus
if (n % 2 == 0) { /* even */ }

// Using bitwise (faster)
if ((n & 1) == 0) { /* even */ }
```

#### 2. Swap Numbers
```java
// Using temp variable
int temp = a;
a = b;
b = temp;

// Using XOR (no temp variable, but less readable)
a = a ^ b;
b = a ^ b;
a = a ^ b;

// Using arithmetic (may overflow)
a = a + b;
b = a - b;
a = a - b;
```

#### 3. Binary Search Middle Calculation
```java
// WRONG! May overflow if left + right > Integer.MAX_VALUE
int mid = (left + right) / 2;

// CORRECT!
int mid = left + (right - left) / 2;

// ALSO CORRECT (using bit shift)
int mid = (left + right) >> 1;  // Divide by 2 using right shift
```

#### 4. Checking Power of 2
```java
// Method 1: Repeated division
boolean isPowerOf2 = false;
int temp = n;
while (temp > 1) {
    if (temp % 2 != 0) break;
    temp /= 2;
}
if (temp == 1) isPowerOf2 = true;

// Method 2: Bitwise (elegant!)
boolean isPowerOf2 = (n > 0) && ((n & (n - 1)) == 0);
```

#### 5. Finding Unique Element (all others appear twice)
```java
int unique = 0;
for (int num : arr) {
    unique ^= num;  // XOR cancels out duplicates
}
return unique;
```

#### 6. Counting Set Bits
```java
// Method 1: Loop through all bits
int count = 0;
while (n > 0) {
    if ((n & 1) == 1) count++;
    n >>= 1;
}

// Method 2: Brian Kernighan's Algorithm (faster)
int count = 0;
while (n > 0) {
    n = n & (n - 1);  // Removes rightmost set bit
    count++;
}

// Method 3: Built-in
int count = Integer.bitCount(n);
```

#### 7. Fast Exponentiation
```java
// Calculate a^b in O(log b) time
long power(long a, long b) {
    long result = 1;
    while (b > 0) {
        if ((b & 1) == 1) {  // If b is odd
            result *= a;
        }
        a *= a;  // Square the base
        b >>= 1; // Divide b by 2
    }
    return result;
}
```

#### 8. GCD (Greatest Common Divisor)
```java
// Euclidean Algorithm using modulus
int gcd(int a, int b) {
    while (b != 0) {
        int temp = b;
        b = a % b;
        a = temp;
    }
    return a;
}

// Recursive version
int gcd(int a, int b) {
    return (b == 0) ? a : gcd(b, a % b);
}
```

#### 9. Checking Palindrome Number
```java
boolean isPalindrome(int n) {
    int original = n;
    int reversed = 0;
    
    while (n > 0) {
        int digit = n % 10;  // Get last digit
        reversed = reversed * 10 + digit;
        n /= 10;  // Remove last digit
    }
    
    return original == reversed;
}
```

#### 10. Sum of Digits
```java
int sumOfDigits(int n) {
    int sum = 0;
    while (n > 0) {
        sum += n % 10;  // Add last digit
        n /= 10;        // Remove last digit
    }
    return sum;
}
```

---

## Memory Efficiency Tips

### Choosing the Right Data Type

**Space Optimization:**
```java
// If values are small (-128 to 127), use byte
byte[] ages = new byte[1000];  // 1 KB instead of 4 KB with int[]

// If counting < 32K, use short
short[] counts = new short[1000];  // 2 KB instead of 4 KB

// Most cases, use int (it's optimized by JVM)
int[] numbers = new int[1000];  // 4 KB

// Only use long when necessary
long[] bigNumbers = new long[1000];  // 8 KB
```

**Trade-offs:**
- Smaller types → Less memory, more complexity
- int is often fastest even for small values (CPU optimization)
- Use smaller types only when memory is critical

---

## Java-Specific Notes

### 1. Wrapper Classes

Every primitive has a wrapper class for use in collections:

| Primitive | Wrapper Class |
|-----------|---------------|
| byte | Byte |
| short | Short |
| int | Integer |
| long | Long |
| float | Float |
| double | Double |
| char | Character |
| boolean | Boolean |

**Usage:**
```java
// Autoboxing (automatic primitive → wrapper)
Integer obj = 5;  // Same as: Integer obj = Integer.valueOf(5);

// Unboxing (automatic wrapper → primitive)
int num = obj;  // Same as: int num = obj.intValue();

// Useful methods
int max = Integer.MAX_VALUE;
int min = Integer.MIN_VALUE;
String binary = Integer.toBinaryString(10);  // "1010"
int parsed = Integer.parseInt("123");  // 123
```

---

### 2. Constants in Java

```java
// final keyword makes variable constant
final int MAX_SIZE = 100;
final double PI = 3.14159;

// Naming convention: ALL_CAPS_WITH_UNDERSCORES
final int BUFFER_SIZE = 1024;

// Can't reassign
// MAX_SIZE = 200;  // Compile error!
```

---

### 3. Type Inference (var keyword - Java 10+)

```java
// Compiler infers type from right side
var num = 10;        // int
var name = "John";   // String
var list = new ArrayList<Integer>();  // ArrayList<Integer>

// ONLY for local variables
// NOT for fields, parameters, or return types
```

---

## Practice Problems

### Level 1: Basic Operators
1. Calculate area of circle given radius
2. Convert Celsius to Fahrenheit
3. Swap two numbers without temp variable
4. Check if number is even/odd using bitwise
5. Find last digit of a number

### Level 2: Number Manipulation
6. Reverse a number
7. Check if palindrome
8. Count digits in a number
9. Sum of digits
10. Check if power of 2

### Level 3: Bitwise Operations
11. Count set bits in a number
12. Find unique element (all others appear twice)
13. Check if ith bit is set
14. Set/clear/toggle ith bit
15. Find position of rightmost set bit

### Level 4: Type Conversion
16. Convert decimal to binary (print as string)
17. Convert binary (string) to decimal
18. Implement fast exponentiation
19. Calculate GCD using modulus
20. Handle integer overflow in multiplication

---

## Quick Reference Card

### Common Conversions
```java
// String to int
int num = Integer.parseInt("123");

// int to String
String str = String.valueOf(123);
String str2 = Integer.toString(123);
String str3 = "" + 123;  // Concatenation trick

// char to int (digit)
char ch = '5';
int digit = ch - '0';  // 5

// int to char (digit)
int num = 5;
char ch = (char)(num + '0');  // '5'

// char case conversion
char upper = Character.toUpperCase('a');  // 'A'
char lower = Character.toLowerCase('A');  // 'a'
char upper2 = (char)('a' - 32);  // 'A' (ASCII trick)
```

### Common Checks
```java
// Even/odd
boolean isEven = (n % 2 == 0);
boolean isEven2 = ((n & 1) == 0);

// Positive/negative
boolean isPositive = (n > 0);
boolean isNegative = (n < 0);

// Character checks
boolean isDigit = (ch >= '0' && ch <= '9');
boolean isUppercase = (ch >= 'A' && ch <= 'Z');
boolean isLowercase = (ch >= 'a' && ch <= 'z');
boolean isLetter = Character.isLetter(ch);

// Power of 2
boolean isPowerOf2 = (n > 0) && ((n & (n - 1)) == 0);

// Divisibility
boolean divisibleBy3 = (n % 3 == 0);
```

### Common Operations
```java
// Absolute value
int abs = Math.abs(n);
int abs2 = (n < 0) ? -n : n;

// Max/Min
int max = Math.max(a, b);
int min = Math.min(a, b);
int max2 = (a > b) ? a : b;

// Square root
double sqrt = Math.sqrt(n);

// Power
double pow = Math.pow(base, exponent);

// Rounding
double rounded = Math.round(3.7);  // 4.0
double floor = Math.floor(3.7);    // 3.0
double ceil = Math.ceil(3.2);      // 4.0
```

---

## Next Steps

After mastering these fundamentals, proceed to:

1. **Control Structures**
   - if-else statements
   - switch-case
   - for, while, do-while loops
   - break and continue

2. **Arrays**
   - 1D and 2D arrays
   - Array operations
   - Common array problems

3. **Strings**
   - String manipulation
   - StringBuilder
   - String algorithms

4. **Methods/Functions**
   - Method declaration
   - Parameters and return types
   - Recursion

5. **Object-Oriented Programming**
   - Classes and Objects
   - Inheritance
   - Polymorphism
   - Encapsulation

---

## Additional Resources

### Books
- "Java: The Complete Reference" by Herbert Schildt
- "Effective Java" by Joshua Bloch
- "Head First Java" by Kathy Sierra

### Online
- Oracle Java Documentation
- GeeksforGeeks Java Tutorial
- LeetCode (for practice problems)
- HackerRank (Java challenges)

### YouTube Channels
- Kunal Kushwaha (Java DSA)
- Apna College
- Abdul Bari (Algorithms)

---

## Summary Checklist

### ✅ You should now understand:
- [ ] Difference between 32-bit and 64-bit systems
- [ ] Binary number system and conversions
- [ ] All 8 primitive data types and their ranges
- [ ] Memory hierarchy (stack vs heap)
- [ ] Type conversion (implicit and explicit)
- [ ] All operator types in Java
- [ ] Operator precedence
- [ ] Bitwise operations and their applications
- [ ] Common pitfalls and how to avoid them
- [ ] Input/output in Java
- [ ] When to use which data type in DSA

### 🎯 Practice Goals:
- [ ] Solve 20 basic problems using these concepts
- [ ] Write programs demonstrating each operator
- [ ] Implement 10 bitwise operation tricks
- [ ] Convert numbers between decimal and binary
- [ ] Handle all edge cases (overflow, division by zero, etc.)

---

## Final Notes

**Key Takeaways:**
1. **int** is your default choice for integers
2. **double** is your default choice for decimals
3. **Bitwise operations** are powerful for optimization
4. Always **watch for overflow** in calculations
5. Never compare **doubles with ==**
6. Use **parentheses** when in doubt about precedence
7. **Practice** is the only way to master these concepts

**Remember:** 
- These fundamentals are the building blocks of ALL programming
- Mastering them will make DSA learning much easier
- Take your time to understand each concept deeply
- Practice, practice, practice!

---

**Good luck with your DSA journey! 🚀**

---

*Last Updated: December 2024*
*For: Elite 3-Month DSA Master Plan*