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

**Time:** O(n), **Space:** O(n)

---

### Pattern 6: Two Sum (Sorted String Array)

```java
public static boolean twoSumStrings(String str, char target) {
    // Assuming str contains sorted characters
    int left = 0;
    int right = str.length() - 1;
    
    while (left < right) {
        int sum = str.charAt(left) + str.charAt(right);
        
        if (sum == target) {
            System.out.println("Pair: " + str.charAt(left) + ", " + str.charAt(right));
            return true;
        } else if (sum < target) {
            left++;
        } else {
            right--;
        }
    }
    
    return false;
}
```

---

### Pattern 7: Move Vowels to End

```java
public static String moveVowelsToEnd(String str) {
    char[] chars = str.toCharArray();
    int left = 0;
    int right = chars.length - 1;
    
    while (left < right) {
        // Find vowel from left
        while (left < right && !isVowel(chars[left])) {
            left++;
        }
        
        // Find consonant from right
        while (left < right && isVowel(chars[right])) {
            right--;
        }
        
        // Swap
        if (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
    }
    
    return new String(chars);
}

private static boolean isVowel(char ch) {
    ch = Character.toLowerCase(ch);
    return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
}

// Usage
System.out.println(moveVowelsToEnd("hello"));  // "hlloe"
```

---

### Pattern 8: Valid Palindrome with Deletions

```java
public static boolean validPalindrome(String str) {
    int left = 0;
    int right = str.length() - 1;
    
    while (left < right) {
        if (str.charAt(left) != str.charAt(right)) {
            // Try deleting left or right character
            return isPalindromeRange(str, left + 1, right) || 
                   isPalindromeRange(str, left, right - 1);
        }
        left++;
        right--;
    }
    
    return true;
}

private static boolean isPalindromeRange(String str, int left, int right) {
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
System.out.println(validPalindrome("abca"));  // true (remove 'b' or 'c')
```

---

## Character Frequency Counting

### Method 1: Using Array (For ASCII Characters)

```java
public static void countFrequency(String str) {
    int[] freq = new int[256];  // ASCII characters
    
    // Count frequency
    for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        freq[ch]++;
    }
    
    // Print frequency
    for (int i = 0; i < 256; i++) {
        if (freq[i] > 0) {
            System.out.println((char) i + ": " + freq[i]);
        }
    }
}

// Usage
countFrequency("hello");
// Output:
// e: 1
// h: 1
// l: 2
// o: 1
```

**Time:** O(n), **Space:** O(1) - fixed size array

---

### Method 2: Using Array (For Lowercase Letters Only)

```java
public static int[] countLetters(String str) {
    int[] freq = new int[26];  // a-z
    
    for (int i = 0; i < str.length(); i++) {
        char ch = Character.toLowerCase(str.charAt(i));
        if (ch >= 'a' && ch <= 'z') {
            freq[ch - 'a']++;
        }
    }
    
    return freq;
}

// Print frequency
public static void printFrequency(int[] freq) {
    for (int i = 0; i < 26; i++) {
        if (freq[i] > 0) {
            System.out.println((char) ('a' + i) + ": " + freq[i]);
        }
    }
}

// Usage
String str = "Hello World";
int[] freq = countLetters(str);
printFrequency(freq);
```

**Time:** O(n), **Space:** O(1) - fixed 26 size

---

### Method 3: Using HashMap (Most Flexible)

```java
import java.util.*;

public static Map<Character, Integer> countFrequencyMap(String str) {
    Map<Character, Integer> freq = new HashMap<>();
    
    for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        freq.put(ch, freq.getOrDefault(ch, 0) + 1);
    }
    
    return freq;
}

// Usage
String str = "hello world";
Map<Character, Integer> freq = countFrequencyMap(str);

for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}

// Or using Java 8 streams
freq.forEach((ch, count) -> System.out.println(ch + ": " + count));
```

**Time:** O(n), **Space:** O(k) where k = unique characters

---

### Method 4: Using Java 8 Streams

```java
import java.util.*;
import java.util.stream.*;

public static Map<Character, Long> countFrequencyStream(String str) {
    return str.chars()
              .mapToObj(c -> (char) c)
              .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
}

// Usage
Map<Character, Long> freq = countFrequencyStream("hello");
freq.forEach((ch, count) -> System.out.println(ch + ": " + count));
```

---

### Common Frequency Problems

#### Problem 1: Find First Non-Repeating Character

```java
public static char firstNonRepeating(String str) {
    Map<Character, Integer> freq = new HashMap<>();
    
    // Count frequency
    for (char ch : str.toCharArray()) {
        freq.put(ch, freq.getOrDefault(ch, 0) + 1);
    }
    
    // Find first with frequency 1
    for (char ch : str.toCharArray()) {
        if (freq.get(ch) == 1) {
            return ch;
        }
    }
    
    return '\0';  // Not found
}

// Usage
System.out.println(firstNonRepeating("leetcode"));    // 'l'
System.out.println(firstNonRepeating("loveleetcode")); // 'v'
```

**Time:** O(n), **Space:** O(k)

---

#### Problem 2: Check if Two Strings are Anagrams

```java
public static boolean areAnagrams(String s1, String s2) {
    if (s1.length() != s2.length()) {
        return false;
    }
    
    int[] freq = new int[26];
    
    // Count s1
    for (char ch : s1.toCharArray()) {
        freq[ch - 'a']++;
    }
    
    // Subtract s2
    for (char ch : s2.toCharArray()) {
        freq[ch - 'a']--;
    }
    
    // Check if all zeros
    for (int count : freq) {
        if (count != 0) {
            return false;
        }
    }
    
    return true;
}

// Alternative: Using sorting
public static boolean areAnagramsSort(String s1, String s2) {
    if (s1.length() != s2.length()) {
        return false;
    }
    
    char[] arr1 = s1.toCharArray();
    char[] arr2 = s2.toCharArray();
    
    Arrays.sort(arr1);
    Arrays.sort(arr2);
    
    return Arrays.equals(arr1, arr2);
}

// Usage
System.out.println(areAnagrams("listen", "silent"));  // true
System.out.println(areAnagrams("hello", "world"));    // false
```

**Frequency Method:** Time O(n), Space O(1)  
**Sorting Method:** Time O(n log n), Space O(n)

---

#### Problem 3: Find Most Frequent Character

```java
public static char mostFrequent(String str) {
    int[] freq = new int[256];
    
    for (char ch : str.toCharArray()) {
        freq[ch]++;
    }
    
    char maxChar = ' ';
    int maxCount = 0;
    
    for (int i = 0; i < 256; i++) {
        if (freq[i] > maxCount) {
            maxCount = freq[i];
            maxChar = (char) i;
        }
    }
    
    return maxChar;
}

// Usage
System.out.println(mostFrequent("hello"));  // 'l'
```

---

#### Problem 4: Check if String has All Unique Characters

```java
// Method 1: Using HashSet
public static boolean hasAllUniqueChars(String str) {
    Set<Character> seen = new HashSet<>();
    
    for (char ch : str.toCharArray()) {
        if (seen.contains(ch)) {
            return false;
        }
        seen.add(ch);
    }
    
    return true;
}

// Method 2: Using boolean array (for lowercase letters)
public static boolean hasAllUniqueCharsArray(String str) {
    if (str.length() > 26) return false;  // Pigeonhole principle
    
    boolean[] seen = new boolean[26];
    
    for (char ch : str.toCharArray()) {
        int index = ch - 'a';
        if (seen[index]) {
            return false;
        }
        seen[index] = true;
    }
    
    return true;
}

// Method 3: Using bit manipulation (most efficient)
public static boolean hasAllUniqueCharsBit(String str) {
    int checker = 0;
    
    for (char ch : str.toCharArray()) {
        int val = ch - 'a';
        if ((checker & (1 << val)) > 0) {
            return false;
        }
        checker |= (1 << val);
    }
    
    return true;
}

// Usage
System.out.println(hasAllUniqueChars("abcdef"));   // true
System.out.println(hasAllUniqueChars("hello"));    // false
```

---

#### Problem 5: Group Anagrams

```java
public static List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> map = new HashMap<>();
    
    for (String str : strs) {
        // Create frequency key
        char[] freq = new char[26];
        for (char ch : str.toCharArray()) {
            freq[ch - 'a']++;
        }
        String key = new String(freq);
        
        // Group by key
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<>());
        }
        map.get(key).add(str);
    }
    
    return new ArrayList<>(map.values());
}

// Usage
String[] words = {"eat", "tea", "tan", "ate", "nat", "bat"};
List<List<String>> groups = groupAnagrams(words);
// Output: [["eat", "tea", "ate"], ["tan", "nat"], ["bat"]]
```

---

## Common String Algorithms

### 1. Check if String is Rotation

```java
public static boolean isRotation(String s1, String s2) {
    if (s1.length() != s2.length()) {
        return false;
    }
    
    // Key insight: if s2 is rotation of s1,
    // then s2 will be substring of s1+s1
    String doubled = s1 + s1;
    return doubled.contains(s2);
}

// Usage
System.out.println(isRotation("waterbottle", "erbottlewat"));  // true
System.out.println(isRotation("hello", "lohel"));              // true
```

**Time:** O(n), **Space:** O(n)

---

### 2. Longest Substring Without Repeating Characters

```java
public static int lengthOfLongestSubstring(String str) {
    Map<Character, Integer> map = new HashMap<>();
    int maxLength = 0;
    int start = 0;
    
    for (int end = 0; end < str.length(); end++) {
        char ch = str.charAt(end);
        
        // If character already seen, move start
        if (map.containsKey(ch)) {
            start = Math.max(start, map.get(ch) + 1);
        }
        
        map.put(ch, end);
        maxLength = Math.max(maxLength, end - start + 1);
    }
    
    return maxLength;
}

// Usage
System.out.println(lengthOfLongestSubstring("abcabcbb"));  // 3 ("abc")
System.out.println(lengthOfLongestSubstring("bbbbb"));     // 1 ("b")
System.out.println(lengthOfLongestSubstring("pwwkew"));    // 3 ("wke")
```

**Time:** O(n), **Space:** O(min(n, k)) where k is charset size

---

### 3. Longest Common Prefix

```java
public static String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) {
        return "";
    }
    
    String prefix = strs[0];
    
    for (int i = 1; i < strs.length; i++) {
        while (strs[i].indexOf(prefix) != 0) {
            prefix = prefix.substring(0, prefix.length() - 1);
            if (prefix.isEmpty()) {
                return "";
            }
        }
    }
    
    return prefix;
}

// Usage
String[] words = {"flower", "flow", "flight"};
System.out.println(longestCommonPrefix(words));  // "fl"
```

**Time:** O(S) where S is sum of all characters, **Space:** O(1)

---

### 4. String Compression

```java
public static String compress(String str) {
    if (str == null || str.isEmpty()) {
        return str;
    }
    
    StringBuilder compressed = new StringBuilder();
    int count = 1;
    
    for (int i = 1; i < str.length(); i++) {
        if (str.charAt(i) == str.charAt(i - 1)) {
            count++;
        } else {
            compressed.append(str.charAt(i - 1));
            compressed.append(count);
            count = 1;
        }
    }
    
    // Add last character
    compressed.append(str.charAt(str.length() - 1));
    compressed.append(count);
    
    // Return shorter string
    String result = compressed.toString();
    return result.length() < str.length() ? result : str;
}

// Usage
System.out.println(compress("aabcccccaaa"));  // "a2b1c5a3"
System.out.println(compress("abc"));          // "abc" (no compression)
```

**Time:** O(n), **Space:** O(n)

---

### 5. Longest Palindromic Substring

```java
public static String longestPalindrome(String str) {
    if (str == null || str.length() < 2) {
        return str;
    }
    
    int start = 0, maxLength = 0;
    
    for (int i = 0; i < str.length(); i++) {
        // Check odd length palindromes
        int len1 = expandAroundCenter(str, i, i);
        // Check even length palindromes
        int len2 = expandAroundCenter(str, i, i + 1);
        
        int len = Math.max(len1, len2);
        
        if (len > maxLength) {
            maxLength = len;
            start = i - (len - 1) / 2;
        }
    }
    
    return str.substring(start, start + maxLength);
}

private static int expandAroundCenter(String str, int left, int right) {
    while (left >= 0 && right < str.length() && 
           str.charAt(left) == str.charAt(right)) {
        left--;
        right++;
    }
    return right - left - 1;
}

// Usage
System.out.println(longestPalindrome("babad"));  // "bab" or "aba"
System.out.println(longestPalindrome("cbbd"));   // "bb"
```

**Time:** O(n²), **Space:** O(1)

---

### 6. String to Integer (atoi)

```java
public static int myAtoi(String str) {
    if (str == null || str.isEmpty()) {
        return 0;
    }
    
    int i = 0, n = str.length();
    
    // Skip leading whitespace
    while (i < n && str.charAt(i) == ' ') {
        i++;
    }
    
    if (i == n) return 0;
    
    // Check sign
    int sign = 1;
    if (str.charAt(i) == '+' || str.charAt(i) == '-') {
        sign = (str.charAt(i) == '-') ? -1 : 1;
        i++;
    }
    
    // Convert digits
    long result = 0;
    while (i < n && Character.isDigit(str.charAt(i))) {
        result = result * 10 + (str.charAt(i) - '0');
        
        // Check overflow
        if (result * sign > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (result * sign < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        
        i++;
    }
    
    return (int) (result * sign);
}

// Usage
System.out.println(myAtoi("42"));          // 42
System.out.println(myAtoi("   -42"));      // -42
System.out.println(myAtoi("4193 with words"));  // 4193
```

---

### 7. Valid Parentheses

```java
public static boolean isValidParentheses(String str) {
    Stack<Character> stack = new Stack<>();
    
    for (char ch : str.toCharArray()) {
        if (ch == '(' || ch == '{' || ch == '[') {
            stack.push(ch);
        } else {
            if (stack.isEmpty()) {
                return false;
            }
            
            char top = stack.pop();
            if ((ch == ')' && top != '(') ||
                (ch == '}' && top != '{') ||
                (ch == ']' && top != '[')) {
                return false;
            }
        }
    }
    
    return stack.isEmpty();
}

// Usage
System.out.println(isValidParentheses("()[]{}"));    // true
System.out.println(isValidParentheses("([)]"));      // false
System.out.println(isValidParentheses("{[]}"));      // true
```

**Time:** O(n), **Space:** O(n)

---

### 8. Reverse Words in a String (In-Place Style)

```java
public static String reverseWordsInPlace(String str) {
    // Convert to char array
    char[] chars = str.trim().toCharArray();
    
    // Reverse entire string
    reverse(chars, 0, chars.length - 1);
    
    // Reverse each word
    int start = 0;
    for (int i = 0; i < chars.length; i++) {
        if (chars[i] == ' ') {
            reverse(chars, start, i - 1);
            start = i + 1;
        }
    }
    
    // Reverse last word
    reverse(chars, start, chars.length - 1);
    
    // Remove extra spaces
    return cleanSpaces(chars);
}

private static void reverse(char[] chars, int left, int right) {
    while (left < right) {
        char temp = chars[left];
        chars[left] = chars[right];
        chars[right] = temp;
        left++;
        right--;
    }
}

private static String cleanSpaces(char[] chars) {
    int i = 0, j = 0;
    
    while (j < chars.length) {
        // Skip spaces
        while (j < chars.length && chars[j] == ' ') j++;
        
        // Copy word
        while (j < chars.length && chars[j] != ' ') {
            chars[i++] = chars[j++];
        }
        
        // Add single space
        while (j < chars.length && chars[j] == ' ') j++;
        if (j < chars.length) chars[i++] = ' ';
    }
    
    return new String(chars, 0, i);
}

// Usage
System.out.println(reverseWordsInPlace("the sky is blue"));  // "blue is sky the"
```

---

## String Practice Problems

### Beginner Level

**Problem 1:** Count vowels and consonants
```java
public static void countVowelsConsonants(String str) {
    int vowels = 0, consonants = 0;
    str = str.toLowerCase();
    
    for (char ch : str.toCharArray()) {
        if (Character.isLetter(ch)) {
            if (isVowel(ch)) {
                vowels++;
            } else {
                consonants++;
            }
        }
    }
    
    System.out.println("Vowels: " + vowels);
    System.out.println("Consonants: " + consonants);
}
```

---

**Problem 2:** Remove all spaces
```java
public static String removeSpaces(String str) {
    return str.replaceAll("\\s+", "");
    
    // Or manually
    StringBuilder sb = new StringBuilder();
    for (char ch : str.toCharArray()) {
        if (ch != ' ') {
            sb.append(ch);
        }
    }
    return sb.toString();
}
```

---

**Problem 3:** Capitalize first letter of each word
```java
public static String capitalizeWords(String str) {
    char[] chars = str.toCharArray();
    boolean capitalizeNext = true;
    
    for (int i = 0; i < chars.length; i++) {
        if (Character.isWhitespace(chars[i])) {
            capitalizeNext = true;
        } else if (capitalizeNext) {
            chars[i] = Character.toUpperCase(chars[i]);
            capitalizeNext = false;
        }
    }
    
    return new String(chars);
}

// Usage
System.out.println(capitalizeWords("hello world"));  // "Hello World"
```

---

### Intermediate Level

**Problem 4:** Check if strings are rotations of each other
```java
public static boolean areRotations(String s1, String s2) {
    return s1.length() == s2.length() && (s1 + s1).contains(s2);
}
```

---

**Problem 5:** Find all permutations of a string
```java
public static void permute(String str, String prefix) {
    if (str.length() == 0) {
        System.out.println(prefix);
        return;
    }
    
    for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        String remaining = str.substring(0, i) + str.substring(i + 1);
        permute(remaining, prefix + ch);
    }
}

// Usage
permute("ABC", "");
// Output: ABC, ACB, BAC, BCA, CAB, CBA
```

---

**Problem 6:** Longest common substring
```java
public static String longestCommonSubstring(String s1, String s2) {
    int maxLength = 0;
    int endIndex = 0;
    int[][] dp = new int[s1.length() + 1][s2.length() + 1];
    
    for (int i = 1; i <= s1.length(); i++) {
        for (int j = 1; j <= s2.length(); j++) {
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                dp[i][j] = dp[i - 1][j - 1] + 1;
                if (dp[i][j] > maxLength) {
                    maxLength = dp[i][j];
                    endIndex = i;
                }
            }
        }
    }
    
    return s1.substring(endIndex - maxLength, endIndex);
}
```

---

### Advanced Level

**Problem 7:** Minimum window substring
```java
public static String minWindow(String s, String t) {
    if (s.length() < t.length()) return "";
    
    Map<Character, Integer> map = new HashMap<>();
    for (char ch : t.toCharArray()) {
        map.put(ch, map.getOrDefault(ch, 0) + 1);
    }
    
    int required = map.size();
    int left = 0, right = 0;
    int formed = 0;
    Map<Character, Integer> windowCounts = new HashMap<>();
    
    int[] ans = {-1, 0, 0};  // length, left, right
    
    while (right < s.length()) {
        char ch = s.charAt(right);
        windowCounts.put(ch, windowCounts.getOrDefault(ch, 0) + 1);
        
        if (map.containsKey(ch) && windowCounts.get(ch).intValue() == map.get(ch).intValue()) {
            formed++;
        }
        
        while (left <= right && formed == required) {
            ch = s.charAt(left);
            
            if (ans[0] == -1 || right - left + 1 < ans[0]) {
                ans[0] = right - left + 1;
                ans[1] = left;
                ans[2] = right;
            }
            
            windowCounts.put(ch, windowCounts.get(ch) - 1);
            if (map.containsKey(ch) && windowCounts.get(ch) < map.get(ch)) {
                formed--;
            }
            
            left++;
        }
        
        right++;
    }
    
    return ans[0] == -1 ? "" : s.substring(ans[1], ans[2] + 1);
}
```

---

## Performance Considerations

### 1. String Concatenation Performance

```java
// ❌ BAD - O(n²) time
public static String badConcat(int n) {
    String result = "";
    for (int i = 0; i < n; i++) {
        result += "a";  // Creates new string each time
    }
    return result;
}

// ✅ GOOD - O(n) time
public static String goodConcat(int n) {
    StringBuilder sb = new StringBuilder(n);  // Pre-allocate capacity
    for (int i = 0; i < n; i++) {
        sb.append("a");
    }
    return sb.toString();
}

// Benchmark for n = 10,000:
// Bad: ~500ms
// Good: ~1ms
// 500x faster!
```

---

### 2. String Comparison Performance

```java
// ❌ Slower - Creates substring objects
if (str.substring(0, 5).equals("Hello")) { }

// ✅ Faster - No object creation
if (str.startsWith("Hello")) { }

// ❌ Slower for large strings
if (str.equals(otherStr)) { }

// ✅ Faster if likely different
if (str.length() != otherStr.length() || !str.equals(otherStr)) { }
```

---

### 3. Character Operations Performance

```java
// ❌ Slower - Method call overhead
if (Character.toLowerCase(ch) == 'a') { }

// ✅ Faster - Direct comparison
if (ch == 'a' || ch == 'A') { }

// For multiple checks
if (ch >= 'a' && ch <= 'z') { }  // Lowercase
if (ch >= 'A' && ch <= 'Z') { }  // Uppercase
if (ch >= '0' && ch <= '9') { }  // Digit
```

---

### 4. Pre-allocate StringBuilder Capacity

```java
// ❌ Default capacity (16), may need resizing
StringBuilder sb = new StringBuilder();

// ✅ Pre-allocate if you know approximate size
StringBuilder sb = new StringBuilder(1000);

// For known size
int size = array.length * 10;  // Estimate
StringBuilder sb = new StringBuilder(size);
```

---

## Common Mistakes

### Mistake 1: Using == Instead of equals()

```java
// ❌ WRONG
String s1 = "Hello";
String s2 = new String("Hello");
if (s1 == s2) { }  // false! Compares references

// ✅ CORRECT
if (s1.equals(s2)) { }  // true! Compares content
```

---

### Mistake 2: Null Pointer Exception

```java
// ❌ WRONG - NPE if str is null
String str = null;
if (str.equals("Hello")) { }  // NullPointerException!

// ✅ CORRECT - Null-safe
if ("Hello".equals(str)) { }  // false, no exception
if (str != null && str.equals("Hello")) { }
```

---

### Mistake 3: Modifying String in Loop

```java
// ❌ WRONG - Inefficient
String result = "";
for (String s : strings) {
    result += s;  // O(n²)
}

// ✅ CORRECT - Efficient
StringBuilder sb = new StringBuilder();
for (String s : strings) {
    sb.append(s);  // O(n)
}
String result = sb.toString();
```

---

### Mistake 4: substring() Index Confusion

```java
String str = "Hello";

// ❌ WRONG - endIndex is exclusive!
str.substring(0, 5);  // "Hello" (NOT error!)
// str.substring(0, 6);  // StringIndexOutOfBoundsException!

// ✅ CORRECT - Remember: [start, end)
str.substring(0, str.length());  // Entire string
str.substring(1, str.length