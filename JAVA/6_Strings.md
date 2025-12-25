# Strings in Java - Complete Guide
## From Basics to Advanced String Algorithms

---

## Table of Contents

1. [Introduction to Strings](#introduction-to-strings)
2. [String Basics](#string-basics)
3. [String Immutability](#string-immutability)
4. [String Creation Methods](#string-creation-methods)
5. [String Pool (Intern Pool)](#string-pool-intern-pool)
6. [Common String Operations](#common-string-operations)
7. [String Comparison](#string-comparison)
8. [StringBuilder and StringBuffer](#stringbuilder-and-stringbuffer)
9. [Character Operations](#character-operations)
10. [Two Pointer Technique on Strings](#two-pointer-technique-on-strings)
11. [Character Frequency Counting](#character-frequency-counting)
12. [Common String Algorithms](#common-string-algorithms)
13. [String Practice Problems](#string-practice-problems)
14. [Performance Considerations](#performance-considerations)
15. [Common Mistakes](#common-mistakes)
16. [Best Practices](#best-practices)

---

## Introduction to Strings

### What is a String?

**String** is a sequence of characters. In Java, String is a **class** (not a primitive type) that represents character sequences.

```
┌────────────────────────────────────┐
│        String Definition           │
├────────────────────────────────────┤
│ • Sequence of characters           │
│ • Immutable (cannot be changed)    │
│ • Stored in String Pool             │
│ • Reference type (object)           │
│ • Most commonly used class          │
└────────────────────────────────────┘
```

### Key Characteristics

| Property | Description |
|----------|-------------|
| **Type** | Reference type (class) |
| **Immutability** | Cannot be modified after creation |
| **Storage** | String pool (for literals) |
| **Thread-Safe** | Yes (due to immutability) |
| **Indexing** | Zero-based (0 to length-1) |

---

## String Basics

### Declaration and Initialization

#### Method 1: String Literal (Most Common)

```java
String str = "Hello";
String name = "Java";
String empty = "";  // Empty string (valid)

// Multi-line strings (Java 15+)
String multiLine = """
    This is a
    multi-line
    string
    """;
```

**Key Point:** String literals are stored in **String Pool**.

---

#### Method 2: Using `new` Keyword

```java
String str1 = new String("Hello");
String str2 = new String("World");

// From character array
char[] chars = {'J', 'a', 'v', 'a'};
String str3 = new String(chars);

// From byte array
byte[] bytes = {72, 101, 108, 108, 111};  // "Hello" in ASCII
String str4 = new String(bytes);
```

**Key Point:** Creates a new object in heap memory.

---

#### Method 3: From Other Data Types

```java
// From int
int num = 123;
String str1 = String.valueOf(num);      // "123"
String str2 = Integer.toString(num);    // "123"
String str3 = "" + num;                 // "123" (implicit conversion)

// From double
double d = 3.14;
String str4 = String.valueOf(d);        // "3.14"

// From boolean
boolean flag = true;
String str5 = String.valueOf(flag);     // "true"

// From char array
char[] chars = {'H', 'i'};
String str6 = new String(chars);        // "Hi"
String str7 = String.valueOf(chars);    // "Hi"
```

---

### String Length

```java
String str = "Hello World";

int length = str.length();  // 11 (NOT length() method like arrays!)
System.out.println(length);

// Empty string
String empty = "";
System.out.println(empty.length());  // 0

// Check if empty
if (str.isEmpty()) {
    System.out.println("String is empty");
}

// Check if blank (empty or only whitespace) - Java 11+
if (str.isBlank()) {
    System.out.println("String is blank");
}
```

**⚠️ Important:** 
- Arrays use `.length` (property)
- Strings use `.length()` (method)

---

### Accessing Characters

```java
String str = "Hello";

// Using charAt()
char first = str.charAt(0);      // 'H'
char last = str.charAt(4);       // 'o'
// char invalid = str.charAt(5);  // StringIndexOutOfBoundsException!

// Safe access
int index = 2;
if (index >= 0 && index < str.length()) {
    char ch = str.charAt(index);
    System.out.println(ch);  // 'l'
}

// Getting all characters
for (int i = 0; i < str.length(); i++) {
    char ch = str.charAt(i);
    System.out.print(ch + " ");
}
// Output: H e l l o

// Convert to char array
char[] chars = str.toCharArray();
for (char ch : chars) {
    System.out.print(ch + " ");
}
```

---

## String Immutability

### What is Immutability?

**Immutable** means once a String object is created, its value **cannot be changed**.

### Demonstration

```java
String str = "Hello";
System.out.println(str);  // "Hello"

str.toUpperCase();  // This creates a NEW string
System.out.println(str);  // Still "Hello" (unchanged!)

// To actually change it, reassign:
str = str.toUpperCase();
System.out.println(str);  // "HELLO" (new object)
```

**Visualization:**
```
Original:  str → "Hello" (object in memory)

After toUpperCase():
           str → "Hello" (original unchanged)
           
           "HELLO" (new object created but not stored)

After reassignment:
           str → "HELLO" (pointing to new object)
           
           "Hello" (original still exists, eligible for GC)
```

---

### Why Immutability?

#### 1. **Security**
```java
// Password cannot be changed after creation
String password = "secret123";
// No method can modify the original string
```

#### 2. **Thread Safety**
```java
// Multiple threads can share the same string safely
String shared = "Shared Resource";
// Thread 1 and Thread 2 can read without synchronization
```

#### 3. **String Pool Efficiency**
```java
String s1 = "Hello";
String s2 = "Hello";
// Both point to same object in pool (memory efficient)
```

#### 4. **Hashcode Caching**
```java
// Hash code computed once and cached
Map<String, Integer> map = new HashMap<>();
map.put("key", 100);  // Hash computed once
```

---

### Implications of Immutability

#### ❌ Bad: String Concatenation in Loop

```java
// INEFFICIENT - Creates new object in each iteration!
String result = "";
for (int i = 0; i < 1000; i++) {
    result += "a";  // Creates 1000 intermediate String objects!
}
// Time: O(n²), Space: O(n²)
```

**What happens:**
```
Iteration 1: "" + "a" → "a" (new object)
Iteration 2: "a" + "a" → "aa" (new object)
Iteration 3: "aa" + "a" → "aaa" (new object)
...
1000 objects created!
```

---

#### ✅ Good: StringBuilder for Concatenation

```java
// EFFICIENT - Modifies internal buffer
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append("a");  // Modifies same object
}
String result = sb.toString();
// Time: O(n), Space: O(n)
```

---

## String Creation Methods

### Comparison: Literal vs new

```java
// Method 1: String Literal (Recommended)
String s1 = "Hello";
String s2 = "Hello";
System.out.println(s1 == s2);  // true (same object from pool)

// Method 2: new Keyword
String s3 = new String("Hello");
String s4 = new String("Hello");
System.out.println(s3 == s4);  // false (different objects)

// Comparison
System.out.println(s1 == s3);      // false (different objects)
System.out.println(s1.equals(s3)); // true (same content)
```

**Memory Diagram:**
```
String Pool:              Heap:
┌──────────┐             ┌──────────┐
│ "Hello"  │←── s1       │ "Hello"  │←── s3
│          │←── s2       ├──────────┤
└──────────┘             │ "Hello"  │←── s4
                         └──────────┘
```

---

## String Pool (Intern Pool)

### What is String Pool?

Special memory region in **Heap** where Java stores **String literals** to optimize memory.

### How It Works

```java
String s1 = "Java";     // Created in String Pool
String s2 = "Java";     // Reuses existing "Java" from pool
String s3 = new String("Java");  // Created in Heap (not pool)

System.out.println(s1 == s2);  // true (same reference)
System.out.println(s1 == s3);  // false (different references)
```

---

### The intern() Method

```java
String s1 = "Hello";
String s2 = new String("Hello");
String s3 = s2.intern();  // Returns reference from pool

System.out.println(s1 == s2);  // false
System.out.println(s1 == s3);  // true (s3 now refers to pool object)

// Use case: Memory optimization when dealing with many duplicate strings
String[] names = new String[1000];
for (int i = 0; i < names.length; i++) {
    names[i] = new String("John").intern();  // All point to same object
}
```

---

## Common String Operations

### 1. Substring

**Extract part of a string**

```java
String str = "Hello World";

// substring(startIndex)
String sub1 = str.substring(6);     // "World"

// substring(startIndex, endIndex) - endIndex is exclusive!
String sub2 = str.substring(0, 5);  // "Hello"
String sub3 = str.substring(6, 11); // "World"

// Common patterns
String first3 = str.substring(0, 3);        // "Hel"
String last3 = str.substring(str.length() - 3);  // "rld"

// Middle characters
String middle = str.substring(3, 8);  // "lo Wo"

// Edge cases
String empty = str.substring(5, 5);   // "" (empty string)
// String error = str.substring(5, 4);  // StringIndexOutOfBoundsException!
```

**Time Complexity:** O(n) where n is the length of substring
**Space Complexity:** O(n) (creates new string)

---

### 2. Concatenation

#### Method 1: Using + Operator

```java
String s1 = "Hello";
String s2 = "World";
String result = s1 + " " + s2;  // "Hello World"

// With other types
String msg = "Age: " + 25;  // "Age: 25"
String calc = "Sum: " + (10 + 20);  // "Sum: 30"
```

**⚠️ Warning:** Inefficient in loops!

---

#### Method 2: Using concat()

```java
String s1 = "Hello";
String s2 = "World";
String result = s1.concat(" ").concat(s2);  // "Hello World"

// Chain multiple
String full = "A".concat("B").concat("C");  // "ABC"
```

---

#### Method 3: Using join() (Java 8+)

```java
// Join with delimiter
String result = String.join(" ", "Hello", "World");  // "Hello World"
String csv = String.join(",", "A", "B", "C");  // "A,B,C"

// From array
String[] words = {"Java", "is", "awesome"};
String sentence = String.join(" ", words);  // "Java is awesome"

// From List
List<String> list = Arrays.asList("One", "Two", "Three");
String joined = String.join(", ", list);  // "One, Two, Three"
```

---

#### Method 4: Using StringBuilder (Best for Multiple Concatenations)

```java
StringBuilder sb = new StringBuilder();
sb.append("Hello");
sb.append(" ");
sb.append("World");
String result = sb.toString();  // "Hello World"

// Method chaining
String result2 = new StringBuilder()
    .append("Java")
    .append(" ")
    .append("Programming")
    .toString();  // "Java Programming"
```

**Performance Comparison:**
```java
// Bad: O(n²) - Creates many intermediate strings
String result = "";
for (int i = 0; i < 10000; i++) {
    result += "a";  // 10000 string objects created!
}

// Good: O(n) - Modifies buffer
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 10000; i++) {
    sb.append("a");  // Single buffer modified
}
String result = sb.toString();
```

---

### 3. String Searching

#### indexOf() - Find First Occurrence

```java
String str = "Hello World Hello";

// Find character
int pos1 = str.indexOf('o');      // 4 (first 'o')
int pos2 = str.indexOf('x');      // -1 (not found)

// Find substring
int pos3 = str.indexOf("World");  // 6
int pos4 = str.indexOf("Hello");  // 0 (first occurrence)

// Find from specific index
int pos5 = str.indexOf("Hello", 1);  // 12 (second occurrence)

// Check if contains
if (str.indexOf("World") != -1) {
    System.out.println("Contains World");
}
```

---

#### lastIndexOf() - Find Last Occurrence

```java
String str = "Hello World Hello";

int pos1 = str.lastIndexOf('o');      // 15 (last 'o')
int pos2 = str.lastIndexOf("Hello");  // 12 (last occurrence)
```

---

#### contains() - Check Substring (Java 5+)

```java
String str = "Hello World";

boolean has1 = str.contains("World");  // true
boolean has2 = str.contains("Java");   // false

// Case-insensitive check
boolean has3 = str.toLowerCase().contains("hello");  // true
```

---

#### startsWith() and endsWith()

```java
String str = "Hello World";

boolean starts1 = str.startsWith("Hello");  // true
boolean starts2 = str.startsWith("World");  // false

boolean ends1 = str.endsWith("World");  // true
boolean ends2 = str.endsWith("Hello");  // false

// startsWith with offset
boolean starts3 = str.startsWith("World", 6);  // true
```

---

### 4. String Transformation

#### Case Conversion

```java
String str = "Hello World";

String upper = str.toUpperCase();    // "HELLO WORLD"
String lower = str.toLowerCase();    // "hello world"

// Original unchanged
System.out.println(str);  // "Hello World"
```

---

#### Trimming

```java
String str = "  Hello World  ";

String trimmed = str.trim();  // "Hello World" (removes leading/trailing spaces)

// strip() - Java 11+ (handles Unicode spaces better)
String str2 = "\u2000Hello\u2000";
String stripped = str2.strip();  // "Hello"

// stripLeading() and stripTrailing() - Java 11+
String left = "  Hello".stripLeading();   // "Hello"
String right = "Hello  ".stripTrailing(); // "Hello"
```

---

#### Replacing

```java
String str = "Hello World";

// Replace single character
String r1 = str.replace('o', 'a');  // "Hella Warld"

// Replace substring
String r2 = str.replace("World", "Java");  // "Hello Java"

// Replace all occurrences
String r3 = "aaa".replace("a", "b");  // "bbb"

// replaceFirst() - Replace first occurrence
String r4 = "Hello Hello".replaceFirst("Hello", "Hi");  // "Hi Hello"

// replaceAll() - With regex
String r5 = "a1b2c3".replaceAll("\\d", "X");  // "aXbXcX"
```

---

### 5. Splitting and Joining

#### split() - String to Array

```java
String str = "apple,banana,orange";

// Split by delimiter
String[] fruits = str.split(",");
// ["apple", "banana", "orange"]

// Split with limit
String[] limited = str.split(",", 2);
// ["apple", "banana,orange"]

// Split by regex
String text = "one1two2three3";
String[] parts = text.split("\\d");  // Split by digits
// ["one", "two", "three", ""]

// Split by multiple delimiters
String text2 = "a,b;c:d";
String[] parts2 = text2.split("[,;:]");
// ["a", "b", "c", "d"]

// Split preserving delimiters
String text3 = "Hello,World";
String[] parts3 = text3.split("(?<=,)");  // Lookahead
```

---

#### Common Split Patterns

```java
// Split by whitespace
String sentence = "Hello World Java";
String[] words = sentence.split("\\s+");  // ["Hello", "World", "Java"]

// Split by newline
String multiLine = "Line1\nLine2\nLine3";
String[] lines = multiLine.split("\\n");

// Split by dot (escape needed!)
String version = "1.2.3";
String[] numbers = version.split("\\.");  // ["1", "2", "3"]
// NOT: version.split(".") - wrong! (. is regex wildcard)

// Split into characters
String word = "Hello";
String[] chars = word.split("");  // ["H", "e", "l", "l", "o"]
```

---

## String Comparison

### 1. Using == (Reference Comparison)

```java
String s1 = "Hello";
String s2 = "Hello";
String s3 = new String("Hello");

System.out.println(s1 == s2);  // true (same object in pool)
System.out.println(s1 == s3);  // false (different objects)
```

**⚠️ Rule:** Use `==` only to check if two variables point to the **same object**, not for content comparison!

---

### 2. Using equals() (Content Comparison) ⭐

```java
String s1 = "Hello";
String s2 = "Hello";
String s3 = new String("Hello");

System.out.println(s1.equals(s2));  // true
System.out.println(s1.equals(s3));  // true (content is same)

// Case-insensitive comparison
String s4 = "hello";
System.out.println(s1.equalsIgnoreCase(s4));  // true
```

**✅ Rule:** Always use `equals()` for content comparison!

---

### 3. Using compareTo() (Lexicographic Comparison)

```java
String s1 = "apple";
String s2 = "banana";
String s3 = "apple";

int result1 = s1.compareTo(s2);  // Negative (s1 < s2)
int result2 = s2.compareTo(s1);  // Positive (s2 > s1)
int result3 = s1.compareTo(s3);  // 0 (equal)

// Case-insensitive
String s4 = "Apple";
int result4 = s1.compareToIgnoreCase(s4);  // 0
```

**Return values:**
- **Negative** if first string comes before second (lexicographically)
- **Zero** if strings are equal
- **Positive** if first string comes after second

**Use case:** Sorting strings
```java
String[] names = {"Zebra", "Apple", "Mango"};
Arrays.sort(names);  // Uses compareTo internally
System.out.println(Arrays.toString(names));
// ["Apple", "Mango", "Zebra"]
```

---

### 4. Comparison Best Practices

```java
// ❌ WRONG
if (str == "Hello") { }  // Don't compare with ==

// ✅ CORRECT
if (str.equals("Hello")) { }
if ("Hello".equals(str)) { }  // Null-safe!

// ❌ WRONG - NullPointerException if str is null
String str = null;
if (str.equals("Hello")) { }  // Throws exception!

// ✅ CORRECT - Null-safe
if ("Hello".equals(str)) { }  // Returns false if str is null
if (Objects.equals(str, "Hello")) { }  // Also null-safe (Java 7+)
```

---

## StringBuilder and StringBuffer

### Why Use StringBuilder?

String is **immutable** → concatenation creates new objects → inefficient for multiple operations.

### StringBuilder vs StringBuffer vs String

| Feature | String | StringBuilder | StringBuffer |
|---------|--------|---------------|--------------|
| Mutability | Immutable | Mutable | Mutable |
| Thread-Safe | Yes | No | Yes (synchronized) |
| Performance | Slow (for concat) | Fast | Slower than StringBuilder |
| Use Case | Fixed strings | Single thread concat | Multi-thread concat |

---

### StringBuilder Examples

#### Creating StringBuilder

```java
// Empty StringBuilder
StringBuilder sb1 = new StringBuilder();

// With initial string
StringBuilder sb2 = new StringBuilder("Hello");

// With initial capacity
StringBuilder sb3 = new StringBuilder(50);

// From String
String str = "Java";
StringBuilder sb4 = new StringBuilder(str);
```

---

#### Common Operations

```java
StringBuilder sb = new StringBuilder();

// 1. append() - Add at end
sb.append("Hello");
sb.append(" ");
sb.append("World");
System.out.println(sb);  // "Hello World"

// Chaining
sb.append("!").append(" ").append("Java");
// "Hello World! Java"

// Append different types
sb.append(123);      // "Hello World! Java123"
sb.append(true);     // "Hello World! Java123true"
sb.append(3.14);     // "Hello World! Java123true3.14"

// 2. insert() - Insert at position
sb.insert(5, "XYZ");  // Insert "XYZ" at index 5

// 3. delete() - Remove characters
sb.delete(5, 8);      // Delete from index 5 to 7 (8 is exclusive)

// 4. deleteCharAt() - Remove single character
sb.deleteCharAt(5);   // Remove character at index 5

// 5. replace() - Replace range
sb.replace(0, 5, "Hi");  // Replace first 5 chars with "Hi"

// 6. reverse() - Reverse the string
sb.reverse();

// 7. Convert to String
String result = sb.toString();
```

---

#### Example: Building HTML

```java
StringBuilder html = new StringBuilder();
html.append("<html>")
    .append("<body>")
    .append("<h1>").append("Welcome").append("</h1>")
    .append("<p>").append("This is a paragraph").append("</p>")
    .append("</body>")
    .append("</html>");

String htmlString = html.toString();
```

---

#### Example: Efficient Concatenation

```java
// Build CSV from array
String[] data = {"John", "25", "Engineer", "USA"};

StringBuilder csv = new StringBuilder();
for (int i = 0; i < data.length; i++) {
    csv.append(data[i]);
    if (i < data.length - 1) {
        csv.append(",");
    }
}
String result = csv.toString();  // "John,25,Engineer,USA"
```

---

### StringBuilder Methods Summary

```java
StringBuilder sb = new StringBuilder("Hello");

// Capacity and length
int capacity = sb.capacity();  // Returns current capacity
int length = sb.length();      // Returns length of content

// Modify
sb.append("World");           // Add at end
sb.insert(5, " ");            // Insert at position
sb.delete(0, 5);              // Delete range
sb.deleteCharAt(0);           // Delete single char
sb.replace(0, 5, "Hi");       // Replace range
sb.reverse();                 // Reverse content

// Access
char ch = sb.charAt(0);       // Get character at index
sb.setCharAt(0, 'X');         // Set character at index
String sub = sb.substring(0, 5);  // Get substring

// Convert
String str = sb.toString();   // Convert to String
```

---

## Character Operations

### Character Class Methods

```java
char ch = 'A';

// Check character type
boolean isLetter = Character.isLetter(ch);      // true
boolean isDigit = Character.isDigit(ch);        // false
boolean isLetterOrDigit = Character.isLetterOrDigit(ch);  // true
boolean isUpperCase = Character.isUpperCase(ch);  // true
boolean isLowerCase = Character.isLowerCase(ch);  // false
boolean isWhitespace = Character.isWhitespace(' ');  // true

// Conversion
char lower = Character.toLowerCase('A');  // 'a'
char upper = Character.toUpperCase('a');  // 'A'

// Numeric value
int value = Character.getNumericValue('5');  // 5
int value2 = Character.getNumericValue('A'); // 10 (hex)
```

---

### Common Character Patterns

```java
// Check if character is vowel
public static boolean isVowel(char ch) {
    ch = Character.toLowerCase(ch);
    return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
}

// Check if character is consonant
public static boolean isConsonant(char ch) {
    return Character.isLetter(ch) && !isVowel(ch);
}

// Check if alphanumeric
public static boolean isAlphanumeric(char ch) {
    return Character.isLetterOrDigit(ch);
}

// Get alphabetic position (A=1, B=2, ...)
public static int getAlphabetPosition(char ch) {
    ch = Character.toUpperCase(ch);
    return ch - 'A' + 1;
}
```

---

### Converting Between char and int

```java
// char to int (ASCII value)
char ch = 'A';
int ascii = (int) ch;  // 65
int ascii2 = ch;       // Implicit conversion: 65

// int to char
int num = 65;
char character = (char) num;  // 'A'

// Character arithmetic
char next = (char) ('A' + 1);  // 'B'
char prev = (char) ('Z' - 1);  // 'Y'

// Distance between characters
int distance = 'Z' - 'A';  // 25
```

---

## Two Pointer Technique on Strings

### Pattern 1: Palindrome Check

```java
public static boolean isPalindrome(String str) {
    int left = 0;
    int right = str.length() - 1;
    
    while (left < right) {
        if (str.charAt(left) != str.charAt(right)) {
            return false;
        }
        left++;
        right--;
    }
    
    return true;
}

// Usage
System.out.println(isPalindrome("racecar"));  // true
System.out.println(isPalindrome("hello"));    // false
```

**Time:** O(n), **Space:** O(1)

---

### Pattern 2: Palindrome (Ignore Non-Alphanumeric)

```java
public static boolean isPalindromeAlphanumeric(String str) {
    int left = 0;
    int right = str.length() - 1;
    
    while (left < right) {
        // Skip non-alphanumeric from left
        while (left < right && !Character.isLetterOrDigit(str.charAt(left))) {
            left++;
        }
        
        // Skip non-alphanumeric from right
        while (left < right && !Character.isLetterOrDigit(str.charAt(right))) {
            right--;
        }
        
        // Compare (case-insensitive)
        if (Character.toLowerCase(str.charAt(left)) != 
            Character.toLowerCase(str.charAt(right))) {
            return false;
        }
        
        left++;
        right--;
    }
    
    return true;
}

// Usage
System.out.println(isPalindromeAlphanumeric("A man, a plan, a canal: Panama"));  // true
```

**Time:** O(n), **Space:** O(1)

---

### Pattern 3: Reverse String

```java
public static String reverseString(String str) {
    char[] chars = str.toCharArray();
    int left = 0;
    int right = chars.length - 1;
    
    while (left < right) {
        // Swap
        char temp = chars[left];
        chars[left] = chars[right];
        chars[right] = temp;
        
        left++;
        right--;
    }
    
    return new String(chars);
}

// Usage
System.out.println(reverseString("Hello"));  // "olleH"
```

**Time:** O(n), **Space:** O(n)

---

### Pattern 4: Reverse Words in String

```java
public static String reverseWords(String str) {
    String[] words = str.trim().split("\\s+");
    int left = 0;
    int right = words.length - 1;
    
    while (left < right) {
        String temp = words[left];
        words[left] = words[right];
        words[right] = temp;
        left++;
        right--;
    }
    
    return String.join(" ", words);
}

// Usage
System.out.println(reverseWords("Hello World Java"));  // "Java World Hello"
```

**Time:** O(n), **Space:** O(n)

---

### Pattern 5: Remove Duplicates

```java
public static String removeDuplicates(String str) {
    if (str.length() <= 1) return str;
    
    char[] chars = str.toCharArray();
    int writeIndex = 1;  // Position to write next unique character
    
    for (int readIndex = 1; readIndex < chars.length; readIndex++) {
        // If current char is different from previous unique char
        if (chars[readIndex] != chars[writeIndex - 1]) {
            chars[writeIndex] = chars[readIndex];
            writeIndex++;
        }
    }
    
    return new String(chars, 0, writeIndex);
}

// Usage
System.out.println(removeDuplicates("aabbccdd"));  // "abcd"
```