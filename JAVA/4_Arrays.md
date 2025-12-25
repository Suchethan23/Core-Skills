# Arrays in Java - Complete Guide
## From Basics to Advanced Array Algorithms

---

## Table of Contents

1. [Introduction to Arrays](#introduction-to-arrays)
2. [Why Arrays?](#why-arrays)
3. [Memory Representation](#memory-representation)
4. [1D Arrays](#1d-arrays)
5. [Array Operations](#array-operations)
6. [2D Arrays](#2d-arrays)
7. [Jagged Arrays](#jagged-arrays)
8. [Array Algorithms](#array-algorithms)
9. [Common Array Problems](#common-array-problems)
10. [Arrays Class Utility](#arrays-class-utility)
11. [Memory Considerations](#memory-considerations)
12. [Common Mistakes](#common-mistakes)
13. [Best Practices](#best-practices)

---

## Introduction to Arrays

### What is an Array?

**Array** is a **fixed-size**, **contiguous** memory structure that stores elements of the **same data type**.

```
┌────────────────────────────────────┐
│        Array Definition            │
├────────────────────────────────────┤
│ • Collection of similar elements   │
│ • Fixed size (cannot grow/shrink)  │
│ • Contiguous memory allocation     │
│ • Zero-based indexing              │
│ • Random access in O(1) time       │
│ • Homogeneous (same type)          │
└────────────────────────────────────┘
```

### Key Characteristics

| Property | Description |
|----------|-------------|
| **Size** | Fixed at creation, cannot change |
| **Type** | All elements must be same type |
| **Indexing** | Zero-based (0 to length-1) |
| **Access** | O(1) - Direct access by index |
| **Memory** | Contiguous block |
| **Dimension** | Can be 1D, 2D, 3D, etc. |

---

## Why Arrays?

### Problem Without Arrays

Imagine storing marks of 100 students:

```java
// Nightmare! 😱
int marks1 = 85;
int marks2 = 90;
int marks3 = 78;
int marks4 = 92;
// ... 96 more variables!
int marks100 = 88;

// How to calculate average? Total? Find max?
// How to process them in loop?
```

### Solution With Arrays

```java
// Beautiful! ✨
int[] marks = new int[100];

// Easy to process
int sum = 0;
for (int i = 0; i < marks.length; i++) {
    sum += marks[i];
}
double average = sum / 100.0;
```

### Advantages of Arrays

1. **Organization** - Store related data together
2. **Easy Access** - Access any element in O(1) time
3. **Looping** - Easy to iterate through elements
4. **Memory Efficiency** - Contiguous allocation is cache-friendly
5. **Multi-dimensional** - Can represent matrices, tables, etc.

### Limitations of Arrays

1. **Fixed Size** - Cannot grow or shrink after creation
2. **Homogeneous** - All elements must be same type
3. **Memory Waste** - If you allocate more than needed
4. **Insertion/Deletion** - Expensive O(n) operation

---

## Memory Representation

### How Arrays are Stored in Memory

```
Array: int[] arr = {10, 20, 30, 40, 50};

Memory Layout (Conceptual):
┌──────────────────────────────────────────────┐
│  Base Address: 1000                          │
├──────┬──────┬──────┬──────┬──────────────────┤
│  10  │  20  │  30  │  40  │  50              │
├──────┼──────┼──────┼──────┼──────────────────┤
│ 1000 │ 1004 │ 1008 │ 1012 │ 1016             │
└──────┴──────┴──────┴──────┴──────────────────┘
Index:  [0]    [1]    [2]    [3]    [4]

Formula: Address of arr[i] = Base Address + (i × size of data type)
Example: arr[2] → 1000 + (2 × 4) = 1008
```

### Stack vs Heap for Arrays

```java
public void method() {
    int[] arr = new int[5];
    // arr (reference): Stack
    // Actual array: Heap
}

Stack:                  Heap:
┌──────────┐           ┌───────────────┐
│ arr      │──────────>│ [0,0,0,0,0]   │
│ (ref)    │           │ (actual array)│
└──────────┘           └───────────────┘
```

**Important Points:**
- Array **reference** stored on **Stack**
- Array **object** stored on **Heap**
- Reference destroyed when method exits
- Object remains until Garbage Collected

---

## 1D Arrays

### Declaration

**Syntax:**
```java
dataType[] arrayName;     // Preferred (Java style)
dataType arrayName[];     // Valid (C style)
```

**Examples:**
```java
int[] numbers;           // Array of integers
String[] names;          // Array of strings
double[] prices;         // Array of doubles
boolean[] flags;         // Array of booleans
char[] letters;          // Array of characters

// Multiple declarations
int[] a, b, c;          // All are arrays
int[] x, y[];           // x is 1D, y is 2D array
```

**Important:** Declaration **does not** create the array, just the reference!

```java
int[] arr;              // Reference created (on stack)
// arr is null at this point - no array object yet!
```

---

### Initialization

#### Method 1: Using `new` Keyword

```java
int[] arr = new int[5];  // Creates array of size 5

// Default values assigned automatically:
System.out.println(Arrays.toString(arr));
// Output: [0, 0, 0, 0, 0]
```

**Default Values by Type:**

| Data Type | Default Value |
|-----------|---------------|
| `int`, `short`, `byte`, `long` | `0` |
| `float`, `double` | `0.0` |
| `boolean` | `false` |
| `char` | `'\u0000'` (null character) |
| Object types | `null` |

**Examples:**
```java
String[] names = new String[3];
// Output: [null, null, null]

boolean[] flags = new boolean[4];
// Output: [false, false, false, false]

double[] prices = new double[3];
// Output: [0.0, 0.0, 0.0]
```

---

#### Method 2: Array Literal (Initialize with Values)

```java
// Direct initialization
int[] arr = {10, 20, 30, 40, 50};
// Size is automatically 5

String[] names = {"Alice", "Bob", "Charlie"};
// Size is automatically 3

double[] prices = {19.99, 29.99, 39.99};

boolean[] flags = {true, false, true};

// Empty array
int[] empty = {};  // Size is 0
```

**Advantage:** Concise and readable when you know the values upfront.

---

#### Method 3: Separate Declaration and Initialization

```java
// Declare first
int[] arr;

// Initialize later
arr = new int[5];

// OR initialize with values
arr = new int[]{10, 20, 30, 40, 50};

// Cannot use shorthand after declaration:
// arr = {10, 20, 30};  // ERROR!
```

---

#### Method 4: Anonymous Array

```java
// Used when passing array to method
printArray(new int[]{1, 2, 3, 4, 5});

public static void printArray(int[] arr) {
    for (int num : arr) {
        System.out.print(num + " ");
    }
}
```

---

### Array Length

**Property:** `arrayName.length` (NOT a method - no parentheses!)

```java
int[] arr = {10, 20, 30, 40, 50};

System.out.println(arr.length);  // 5 (NOT arr.length())

// Length is read-only - cannot be changed
// arr.length = 10;  // ERROR!
```

**Common Usage:**
```java
// Loop from 0 to length-1
for (int i = 0; i < arr.length; i++) {
    System.out.println(arr[i]);
}

// Last element
int last = arr[arr.length - 1];

// Check if empty
if (arr.length == 0) {
    System.out.println("Empty array");
}
```

---

### Accessing Elements

**Syntax:**
```java
arrayName[index]
```

**Valid Indices:** `0` to `length - 1`

**Examples:**
```java
int[] arr = {10, 20, 30, 40, 50};

// Reading elements
int first = arr[0];     // 10
int third = arr[2];     // 30
int last = arr[4];      // 50 (or arr[arr.length-1])

System.out.println(arr[0]);  // 10
System.out.println(arr[2]);  // 30

// Modifying elements
arr[0] = 15;    // Change first element to 15
arr[2] = 35;    // Change third element to 35

System.out.println(Arrays.toString(arr));
// Output: [15, 20, 35, 40, 50]
```

**⚠️ ArrayIndexOutOfBoundsException:**
```java
int[] arr = {10, 20, 30};

// Valid indices: 0, 1, 2
System.out.println(arr[3]);  // ERROR! Index 3 is out of bounds
System.out.println(arr[-1]); // ERROR! Negative index not allowed

// Always check bounds!
if (index >= 0 && index < arr.length) {
    System.out.println(arr[index]);  // Safe
}
```

---

### Traversing Arrays

#### Method 1: Standard for Loop

**When to use:** Need index, modify elements, reverse traversal

```java
int[] arr = {10, 20, 30, 40, 50};

// Forward traversal
for (int i = 0; i < arr.length; i++) {
    System.out.print(arr[i] + " ");
}
// Output: 10 20 30 40 50

// Modify elements
for (int i = 0; i < arr.length; i++) {
    arr[i] = arr[i] * 2;  // Double each element
}

// Access index
for (int i = 0; i < arr.length; i++) {
    System.out.println("Element at index " + i + ": " + arr[i]);
}
```

---

#### Method 2: Enhanced for Loop (for-each)

**When to use:** Read-only traversal, don't need index

```java
int[] arr = {10, 20, 30, 40, 50};

for (int num : arr) {
    System.out.print(num + " ");
}
// Output: 10 20 30 40 50

// Calculate sum
int sum = 0;
for (int num : arr) {
    sum += num;
}
System.out.println("Sum: " + sum);  // 150
```

**Limitations:**
- ❌ Cannot modify array elements
- ❌ Cannot access index
- ❌ Cannot iterate backwards
- ❌ Cannot skip elements easily

```java
// This does NOT modify the array!
for (int num : arr) {
    num = num * 2;  // Only modifies local variable 'num'
}
```

---

#### Method 3: While Loop

**When to use:** Complex iteration logic

```java
int[] arr = {10, 20, 30, 40, 50};

int i = 0;
while (i < arr.length) {
    System.out.print(arr[i] + " ");
    i++;
}
// Output: 10 20 30 40 50

// With complex condition
int i = 0;
while (i < arr.length && arr[i] < 30) {
    System.out.print(arr[i] + " ");
    i++;
}
// Output: 10 20
```

---

#### Method 4: Reverse Traversal

```java
int[] arr = {10, 20, 30, 40, 50};

// Backwards
for (int i = arr.length - 1; i >= 0; i--) {
    System.out.print(arr[i] + " ");
}
// Output: 50 40 30 20 10
```

---

#### Method 5: Skipping Elements

```java
int[] arr = {10, 20, 30, 40, 50, 60, 70, 80};

// Print every other element
for (int i = 0; i < arr.length; i += 2) {
    System.out.print(arr[i] + " ");
}
// Output: 10 30 50 70

// Print elements at even indices
for (int i = 0; i < arr.length; i++) {
    if (i % 2 == 0) {
        System.out.print(arr[i] + " ");
    }
}
// Output: 10 30 50 70
```

---

### Taking Array Input

#### Method 1: Scanner (Most Common)

```java
import java.util.Scanner;

public class ArrayInput {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Input size
        System.out.print("Enter array size: ");
        int n = sc.nextInt();
        
        // Create array
        int[] arr = new int[n];
        
        // Input elements
        System.out.println("Enter " + n + " elements:");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        
        // Display array
        System.out.println("Array elements:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        
        sc.close();
    }
}

/*
Sample Input/Output:
Enter array size: 5
Enter 5 elements:
10 20 30 40 50
Array elements:
10 20 30 40 50
*/
```

---

#### Method 2: Input with Validation

```java
Scanner sc = new Scanner(System.in);

System.out.print("Enter array size: ");
int n = sc.nextInt();

// Validate size
if (n <= 0) {
    System.out.println("Invalid size!");
    return;
}

int[] arr = new int[n];

System.out.println("Enter " + n + " positive numbers:");
for (int i = 0; i < n; i++) {
    arr[i] = sc.nextInt();
    
    // Validate input
    if (arr[i] < 0) {
        System.out.println("Invalid! Enter positive number:");
        i--;  // Retry same index
    }
}
```

---

#### Method 3: Input in Single Line

```java
Scanner sc = new Scanner(System.in);

System.out.print("Enter size: ");
int n = sc.nextInt();

int[] arr = new int[n];

System.out.println("Enter " + n + " numbers (space-separated):");
for (int i = 0; i < n; i++) {
    arr[i] = sc.nextInt();
}

/*
Input: 5
Input: 10 20 30 40 50
*/
```

---

#### Method 4: BufferedReader (Faster for Large Input)

```java
import java.io.*;

public class FastInput {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        
        String[] input = br.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(input[i]);
        }
        
        // Display
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }
}
```

---

### Printing Arrays

#### Method 1: Manual Loop

```java
int[] arr = {10, 20, 30, 40, 50};

for (int num : arr) {
    System.out.print(num + " ");
}
System.out.println();
// Output: 10 20 30 40 50
```

---

#### Method 2: Arrays.toString()

**Simplest and Most Common:**

```java
int[] arr = {10, 20, 30, 40, 50};

System.out.println(Arrays.toString(arr));
// Output: [10, 20, 30, 40, 50]

// For different types
String[] names = {"Alice", "Bob", "Charlie"};
System.out.println(Arrays.toString(names));
// Output: [Alice, Bob, Charlie]

double[] prices = {19.99, 29.99, 39.99};
System.out.println(Arrays.toString(prices));
// Output: [19.99, 29.99, 39.99]
```

**⚠️ Common Mistake:**
```java
int[] arr = {10, 20, 30};

// WRONG - Prints memory address
System.out.println(arr);  // Output: [I@15db9742

// CORRECT - Prints array contents
System.out.println(Arrays.toString(arr));  // Output: [10, 20, 30]
```

---

#### Method 3: Custom Format

```java
int[] arr = {10, 20, 30, 40, 50};

// With arrow separator
for (int i = 0; i < arr.length; i++) {
    System.out.print(arr[i]);
    if (i < arr.length - 1) {
        System.out.print(" -> ");
    }
}
System.out.println();
// Output: 10 -> 20 -> 30 -> 40 -> 50

// With commas
String result = "";
for (int i = 0; i < arr.length; i++) {
    result += arr[i];
    if (i < arr.length - 1) {
        result += ", ";
    }
}
System.out.println(result);
// Output: 10, 20, 30, 40, 50
```

---

## Array Operations

### 1. Finding Maximum Element

#### Method 1: Iterative

```java
public static int findMax(int[] arr) {
    // Handle empty array
    if (arr == null || arr.length == 0) {
        throw new IllegalArgumentException("Array is empty");
    }
    
    int max = arr[0];  // Assume first element is max
    
    for (int i = 1; i < arr.length; i++) {
        if (arr[i] > max) {
            max = arr[i];
        }
    }
    
    return max;
}

// Usage
int[] arr = {15, 42, 7, 89, 23, 56};
int max = findMax(arr);
System.out.println("Maximum: " + max);  // Output: 89
```

**Time Complexity:** O(n)  
**Space Complexity:** O(1)

---

#### Method 2: Using Enhanced for Loop

```java
public static int findMax(int[] arr) {
    int max = Integer.MIN_VALUE;  // Start with smallest possible value
    
    for (int num : arr) {
        if (num > max) {
            max = num;
        }
    }
    
    return max;
}
```

---

#### Method 3: Java 8 Streams

```java
import java.util.Arrays;

int[] arr = {15, 42, 7, 89, 23, 56};
int max = Arrays.stream(arr).max().getAsInt();
System.out.println("Maximum: " + max);  // Output: 89
```

---

#### Method 4: Finding Max with Index

```java
public static int findMaxIndex(int[] arr) {
    int maxIndex = 0;
    
    for (int i = 1; i < arr.length; i++) {
        if (arr[i] > arr[maxIndex]) {
            maxIndex = i;
        }
    }
    
    return maxIndex;
}

// Usage
int[] arr = {15, 42, 7, 89, 23};
int index = findMaxIndex(arr);
System.out.println("Max at index: " + index);  // Output: 3
System.out.println("Max value: " + arr[index]);  // Output: 89
```

---

### 2. Finding Minimum Element

```java
public static int findMin(int[] arr) {
    if (arr == null || arr.length == 0) {
        throw new IllegalArgumentException("Array is empty");
    }
    
    int min = arr[0];
    
    for (int i = 1; i < arr.length; i++) {
        if (arr[i] < min) {
            min = arr[i];
        }
    }
    
    return min;
}

// Alternative: Using Math.min()
public static int findMinAlt(int[] arr) {
    int min = Integer.MAX_VALUE;
    
    for (int num : arr) {
        min = Math.min(min, num);
    }
    
    return min;
}

// Usage
int[] arr = {15, 42, 7, 89, 23, 3};
System.out.println("Minimum: " + findMin(arr));  // Output: 3
```

**Time Complexity:** O(n)  
**Space Complexity:** O(1)

---

### 3. Finding Second Largest Element

```java
public static int findSecondLargest(int[] arr) {
    if (arr.length < 2) {
        throw new IllegalArgumentException("Array must have at least 2 elements");
    }
    
    int max = Integer.MIN_VALUE;
    int secondMax = Integer.MIN_VALUE;
    
    for (int num : arr) {
        if (num > max) {
            secondMax = max;  // Previous max becomes second max
            max = num;
        } else if (num > secondMax && num != max) {
            secondMax = num;
        }
    }
    
    if (secondMax == Integer.MIN_VALUE) {
        throw new IllegalArgumentException("No second largest element found");
    }
    
    return secondMax;
}

// Usage
int[] arr = {15, 42, 7, 89, 23, 56};
System.out.println("Second Largest: " + findSecondLargest(arr));  // Output: 56
```

---

### 4. Sum of Array Elements

```java
public static int sum(int[] arr) {
    int sum = 0;
    
    for (int num : arr) {
        sum += num;
    }
    
    return sum;
}

// Usage
int[] arr = {10, 20, 30, 40, 50};
System.out.println("Sum: " + sum(arr));  // Output: 150

// Alternative: Java 8 Stream
int sum = Arrays.stream(arr).sum();
```

**Time Complexity:** O(n)  
**Space Complexity:** O(1)

---

### 5. Average of Array Elements

```java
public static double average(int[] arr) {
    if (arr.length == 0) {
        return 0.0;
    }
    
    return (double) sum(arr) / arr.length;
}

// Usage
int[] arr = {10, 20, 30, 40, 50};
System.out.println("Average: " + average(arr));  // Output: 30.0

// Alternative: Java 8 Stream
double avg = Arrays.stream(arr).average().orElse(0.0);
```

---

### 6. Count Occurrences of Element

```java
public static int countOccurrences(int[] arr, int target) {
    int count = 0;
    
    for (int num : arr) {
        if (num == target) {
            count++;
        }
    }
    
    return count;
}

// Usage
int[] arr = {1, 2, 3, 2, 4, 2, 5};
System.out.println("Count of 2: " + countOccurrences(arr, 2));  // Output: 3
```

---

### 7. Searching in Arrays

#### A. Linear Search

**Time Complexity:** O(n)  
**Space Complexity:** O(1)  
**Use When:** Array is unsorted

```java
public static int linearSearch(int[] arr, int target) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == target) {
            return i;  // Return index
        }
    }
    return -1;  // Not found
}

// Usage
int[] arr = {10, 20, 30, 40, 50};
int index = linearSearch(arr, 30);

if (index != -1) {
    System.out.println("Found at index: " + index);  // Output: 2
} else {
    System.out.println("Not found");
}
```

**Variation: Find All Occurrences**
```java
public static List<Integer> linearSearchAll(int[] arr, int target) {
    List<Integer> indices = new ArrayList<>();
    
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == target) {
            indices.add(i);
        }
    }
    
    return indices;
}

// Usage
int[] arr = {1, 2, 3, 2, 4, 2, 5};
List<Integer> indices = linearSearchAll(arr, 2);
System.out.println("Found at indices: " + indices);  // Output: [1, 3, 5]
```

---

#### B. Binary Search

**Time Complexity:** O(log n)  
**Space Complexity:** O(1)  
**⚠️ Prerequisite:** Array MUST be sorted!

**Iterative Implementation:**
```java
public static int binarySearch(int[] arr, int target) {
    int left = 0;
    int right = arr.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;  // Avoid overflow
        
        if (arr[mid] == target) {
            return mid;  // Found
        } else if (arr[mid] < target) {
            left = mid + 1;  // Search right half
        } else {
            right = mid - 1;  // Search left half
        }
    }
    
    return -1;  // Not found
}

// Usage
int[] arr = {10, 20, 30, 40, 50};  // Must be sorted!
int index = binarySearch(arr, 30);
System.out.println("Found at index: " + index);  // Output: 2
```

**Recursive Implementation:**
```java
public static int binarySearchRecursive(int[] arr, int target, int left, int right) {
    if (left > right) {
        return -1;  // Not found
    }
    
    int mid = left + (right - left) / 2;
    
    if (arr[mid] == target) {
        return mid;
    } else if (arr[mid] < target) {
        return binarySearchRecursive(arr, target, mid + 1, right);
    } else {
        return binarySearchRecursive(arr, target, left, mid - 1);
    }
}

// Usage
int[] arr = {10, 20, 30, 40, 50};
int index = binarySearchRecursive(arr, 30, 0, arr.length - 1);
System.out.println("Found at index: " + index);  // Output: 2
```

**Built-in Binary Search:**
```java
import java.util.Arrays;

int[] arr = {10, 20, 30, 40, 50};
int index = Arrays.binarySearch(arr, 30);
System.out.println("Found at index: " + index);  // Output: 2

// If not found, returns: -(insertion point) - 1
int notFound = Arrays.binarySearch(arr, 25);
System.out.println(notFound);  // Output: -3 (would be inserted at index 2)
```

---

### 8. Reversing an Array

#### Method 1: Using Extra Space

**Time:** O(n), **Space:** O(n)

```java
public static int[] reverse(int[] arr) {
    int[] reversed = new int[arr.length];
    
    for (int i = 0; i < arr.length; i++) {
        reversed[i] = arr[arr.length - 1 - i];
    }
    
    return reversed;
}

// Usage
int[] arr = {10, 20, 30, 40, 50};
int[] rev = reverse(arr);
System.out.println(Arrays.toString(rev));  // Output: [50, 40, 30, 20, 10]
```

---

#### Method 2: In-Place (Two Pointers) ⭐

**Time:** O(n), **Space:** O(1)

```java
public static void reverseInPlace(int[] arr) {
    int left = 0;
    int right = arr.length - 1;
    
    while (left < right) {
        // Swap arr[left] and arr[right]
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
        
        left++;
        right--;
    }
}

// Usage
int[] arr = {10, 20, 30, 40, 50};
reverseInPlace(arr);
System.out.println(Arrays.toString(arr));  // Output: [50, 40, 30, 20, 10]
```

---

#### Method 3: Swap Without Temp Variable

```java
public static void reverseInPlaceNoTemp(int[] arr) {
    int left = 0;
    int right = arr.length - 1;
    
    while (left < right) {
        // Swap using XOR
        arr[left] = arr[left] ^ arr[right];
        arr[right] = arr[left] ^ arr[right];
        arr[left] = arr[left] ^ arr[right];
        
        left++;
        right--;
    }
}
```

---

#### Method 4: Reverse a Range

```java
public static void reverse(int[] arr, int start, int end) {
    while (start < end) {
        int temp = arr[start];
        arr[start] = arr[end];
        arr[end] = temp;
        start++;
        end--;
    }
}

// Usage: Reverse middle portion
int[] arr = {10, 20, 30, 40, 50};
reverse(arr, 1, 3);  // Reverse from index 1 to 3
System.out.println(Arrays.toString(arr));  // Output: [10, 40, 30, 20, 50]
```

---

### 9. Rotating an Array

#### A. Rotate Right by k Positions

**Method 1: Using Extra Array**

**Time:** O(n), **Space:** O(n)

```java
public static void rotateRight(int[] arr, int k) {
    int n = arr.length;
    k = k % n;  // Handle k > n
    
    int[] temp = new int[n];
    
    // Place elements at rotated positions
    for (int i = 0; i < n; i++) {
        temp[(i + k) % n] = arr[i];
    }
    
    // Copy back
    for (int i = 0; i < n; i++) {
        arr[i] = temp[i];
    }
}

// Usage
int[] arr = {1, 2, 3, 4, 5};
rotateRight(arr, 2);
System.out.println(Arrays.toString(arr));

```

---

#### B. Rotate Left by k Positions

```java
public static void rotateLeft(int[] arr, int k) {
    int n = arr.length;
    k = k % n;
    
    // Rotate left by k = Rotate right by (n-k)
    rotateRight(arr, n - k);
}

// OR directly:
public static void rotateLeft(int[] arr, int k) {
    int n = arr.length;
    k = k % n;
    
    reverse(arr, 0, k - 1);      // Reverse first k
    reverse(arr, k, n - 1);      // Reverse remaining
    reverse(arr, 0, n - 1);      // Reverse all
}

// Usage
int[] arr = {1, 2, 3, 4, 5};
rotateLeft(arr, 2);
System.out.println(Arrays.toString(arr));  // Output: [3, 4, 5, 1, 2]
```

---

### 10. Copying Arrays

#### Method 1: Manual Copy

```java
int[] original = {1, 2, 3, 4, 5};
int[] copy = new int[original.length];

for (int i = 0; i < original.length; i++) {
    copy[i] = original[i];
}
```

---

#### Method 2: Arrays.copyOf() ⭐

```java
int[] original = {1, 2, 3, 4, 5};

// Copy entire array
int[] copy = Arrays.copyOf(original, original.length);

// Copy first 3 elements
int[] partial = Arrays.copyOf(original, 3);
// Result: [1, 2, 3]

// Extend with zeros
int[] extended = Arrays.copyOf(original, 7);
// Result: [1, 2, 3, 4, 5, 0, 0]
```

---

#### Method 3: Arrays.copyOfRange()

```java
int[] original = {1, 2, 3, 4, 5};

// Copy from index 1 to 4 (exclusive)
int[] range = Arrays.copyOfRange(original, 1, 4);
// Result: [2, 3, 4]

// Copy from index 2 to end
int[] fromIndex = Arrays.copyOfRange(original, 2, original.length);
// Result: [3, 4, 5]
```

---

#### Method 4: System.arraycopy()

```java
int[] original = {1, 2, 3, 4, 5};
int[] copy = new int[original.length];

System.arraycopy(original, 0, copy, 0, original.length);
//               (src, srcPos, dest, destPos, length)

// Copy portion
int[] partial = new int[3];
System.arraycopy(original, 1, partial, 0, 3);
// Copies original[1], original[2], original[3] to partial
// Result: [2, 3, 4]
```

---

#### Method 5: clone()

```java
int[] original = {1, 2, 3, 4, 5};
int[] copy = original.clone();

// Modifying copy doesn't affect original
copy[0] = 99;
System.out.println(Arrays.toString(original));  // [1, 2, 3, 4, 5]
System.out.println(Arrays.toString(copy));      // [99, 2, 3, 4, 5]
```

**⚠️ Important:** These create **shallow copies**!
- For primitive arrays: Works perfectly
- For object arrays: Only references are copied

```java
String[] original = {"A", "B", "C"};
String[] copy = original.clone();
// Works fine (Strings are immutable)

// But for mutable objects:
int[][] matrix = {{1, 2}, {3, 4}};
int[][] copy = matrix.clone();  // Shallow copy!
copy[0][0] = 99;  // Also changes original[0][0]!
```

---

### 11. Comparing Arrays

#### Method 1: Arrays.equals() ⭐

```java
int[] arr1 = {1, 2, 3};
int[] arr2 = {1, 2, 3};
int[] arr3 = {1, 2, 4};

// WRONG - Compares references, not content!
System.out.println(arr1 == arr2);  // false (different objects)

// CORRECT - Compares content
System.out.println(Arrays.equals(arr1, arr2));  // true
System.out.println(Arrays.equals(arr1, arr3));  // false
```

---

#### Method 2: Manual Comparison

```java
public static boolean areEqual(int[] arr1, int[] arr2) {
    // Check lengths
    if (arr1.length != arr2.length) {
        return false;
    }
    
    // Compare elements
    for (int i = 0; i < arr1.length; i++) {
        if (arr1[i] != arr2[i]) {
            return false;
        }
    }
    
    return true;
}
```

---

#### Method 3: Deep Comparison (for multi-dimensional)

```java
int[][] arr1 = {{1, 2}, {3, 4}};
int[][] arr2 = {{1, 2}, {3, 4}};

// Arrays.equals() doesn't work for 2D!
System.out.println(Arrays.equals(arr1, arr2));  // false

// Use Arrays.deepEquals()
System.out.println(Arrays.deepEquals(arr1, arr2));  // true
```

---

### 12. Sorting Arrays

#### Method 1: Arrays.sort() (Built-in) ⭐

**Time Complexity:** O(n log n) - Dual-Pivot Quicksort

```java
int[] arr = {50, 20, 40, 10, 30};

// Sort in ascending order
Arrays.sort(arr);
System.out.println(Arrays.toString(arr));
// Output: [10, 20, 30, 40, 50]

// Sort a range (from index 1 to 4)
int[] arr2 = {50, 20, 40, 10, 30};
Arrays.sort(arr2, 1, 4);  // Sort elements at index 1, 2, 3
System.out.println(Arrays.toString(arr2));
// Output: [50, 10, 20, 40, 30]
```

---

#### Method 2: Descending Order

**Note:** Arrays.sort() with Comparator only works with **Object arrays**, not primitives!

```java
// For Integer array (wrapper class)
Integer[] arr = {50, 20, 40, 10, 30};

Arrays.sort(arr, Collections.reverseOrder());
System.out.println(Arrays.toString(arr));
// Output: [50, 40, 30, 20, 10]

// For int array (primitive), reverse after sorting
int[] primitiveArr = {50, 20, 40, 10, 30};
Arrays.sort(primitiveArr);
reverseInPlace(primitiveArr);  // Use our reverse method
System.out.println(Arrays.toString(primitiveArr));
// Output: [50, 40, 30, 20, 10]
```

---

#### Method 3: Custom Sorting

```java
// Sort by absolute value
Integer[] arr = {-5, 2, -3, 8, -1, 4};

Arrays.sort(arr, (a, b) -> Math.abs(a) - Math.abs(b));
System.out.println(Arrays.toString(arr));
// Output: [-1, 2, -3, 4, -5, 8]

// Sort by last digit
Integer[] arr2 = {123, 456, 789, 234, 567};

Arrays.sort(arr2, (a, b) -> (a % 10) - (b % 10));
System.out.println(Arrays.toString(arr2));
// Output: [123, 234, 456, 567, 789]
```

---

### 13. Checking if Array is Sorted

```java
public static boolean isSorted(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
        if (arr[i] < arr[i - 1]) {
            return false;
        }
    }
    return true;
}

// Usage
int[] arr1 = {10, 20, 30, 40, 50};
System.out.println(isSorted(arr1));  // true

int[] arr2 = {10, 30, 20, 40};
System.out.println(isSorted(arr2));  // false
```

**Variation: Check Descending Order**
```java
public static boolean isSortedDescending(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
        if (arr[i] > arr[i - 1]) {
            return false;
        }
    }
    return true;
}
```

---

## 2D Arrays

### Introduction to 2D Arrays

**2D Array** = Array of arrays (Matrix/Table structure)

```
Visualization:
     Col0  Col1  Col2  Col3
Row0:  1     2     3     4
Row1:  5     6     7     8
Row2:  9    10    11    12

Syntax: arr[row][column]
Example: arr[1][2] = 7
```

---

### Declaration and Initialization

#### Method 1: Using new Keyword

```java
// Declare and allocate
int[][] matrix = new int[3][4];  // 3 rows, 4 columns

// Default values (all zeros)
System.out.println(Arrays.deepToString(matrix));
// Output: [[0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]]
```

---

#### Method 2: Array Literal

```java
int[][] matrix = {
    {1, 2, 3, 4},
    {5, 6, 7, 8},
    {9, 10, 11, 12}
};

// Compact form (less readable)
int[][] matrix2 = {{1,2,3}, {4,5,6}, {7,8,9}};
```

---

#### Method 3: Row by Row

```java
int[][] matrix = new int[3][];  // Only rows specified

matrix[0] = new int[]{1, 2, 3, 4};
matrix[1] = new int[]{5, 6, 7, 8};
matrix[2] = new int[]{9, 10, 11, 12};
```

---

#### Method 4: Separate Declaration and Initialization

```java
int[][] matrix;
matrix = new int[3][4];

// OR
matrix = new int[][] {
    {1, 2, 3},
    {4, 5, 6}
};
```

---

### Getting Dimensions

```java
int[][] matrix = {
    {1, 2, 3, 4},
    {5, 6, 7, 8},
    {9, 10, 11, 12}
};

int rows = matrix.length;           // 3
int cols = matrix[0].length;        // 4

System.out.println("Rows: " + rows);
System.out.println("Columns: " + cols);
```

---

### Accessing Elements

```java
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};

// Reading
int element = matrix[1][2];  // Row 1, Column 2 → 6
System.out.println(element);  // 6

// Modifying
matrix[0][0] = 99;  // Change first element
matrix[2][1] = 88;  // Change element at row 2, col 1

System.out.println(Arrays.deepToString(matrix));
// Output: [[99, 2, 3], [4, 5, 6], [7, 88, 9]]
```

---

### Traversing 2D Arrays

#### Method 1: Nested for Loops (Row-wise)

```java
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};

// Row by row
for (int i = 0; i < matrix.length; i++) {
    for (int j = 0; j < matrix[i].length; j++) {
        System.out.print(matrix[i][j] + " ");
    }
    System.out.println();
}
// Output:
// 1 2 3
// 4 5 6
// 7 8 9
```

---

#### Method 2: Enhanced for Loop

```java
for (int[] row : matrix) {
    for (int num : row) {
        System.out.print(num + " ");
    }
    System.out.println();
}
```

---

#### Method 3: Column-wise Traversal

```java
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};

// Column by column
for (int j = 0; j < matrix[0].length; j++) {
    for (int i = 0; i < matrix.length; i++) {
        System.out.print(matrix[i][j] + " ");
    }
    System.out.println();
}
// Output:
// 1 4 7
// 2 5 8
// 3 6 9
```

---

### Taking 2D Array Input

```java
import java.util.Scanner;

public class Matrix2DInput {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter rows and columns: ");
        int rows = sc.nextInt();
        int cols = sc.nextInt();
        
        int[][] matrix = new int[rows][cols];
        
        System.out.println("Enter elements:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        
        // Display
        System.out.println("Matrix:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        
        sc.close();
    }
}

/*
Sample Input/Output:
Enter rows and columns: 2 3
Enter elements:
1 2 3
4 5 6
Matrix:
1 2 3
4 5 6
*/
```

---

### Printing 2D Array

```java
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6}
};

// Method 1: Manual
for (int[] row : matrix) {
    for (int num : row) {
        System.out.print(num + " ");
    }
    System.out.println();
}

// Method 2: Arrays.deepToString()
System.out.println(Arrays.deepToString(matrix));
// Output: [[1, 2, 3], [4, 5, 6]]

// Method 3: Row by row with Arrays.toString()
for (int[] row : matrix) {
    System.out.println(Arrays.toString(row));
}
// Output:
// [1, 2, 3]
// [4, 5, 6]
```

---

### Common 2D Array Operations

#### 1. Sum of All Elements

```java
public static int sum2D(int[][] matrix) {
    int sum = 0;
    
    for (int[] row : matrix) {
        for (int num : row) {
            sum += num;
        }
    }
    
    return sum;
}

// Usage
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};
System.out.println("Sum: " + sum2D(matrix));  // Output: 45
```

---

#### 2. Row-wise Sum

```java
public static void rowSum(int[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
        int sum = 0;
        for (int j = 0; j < matrix[i].length; j++) {
            sum += matrix[i][j];
        }
        System.out.println("Row " + i + " sum: " + sum);
    }
}

// Usage
int[][] matrix = {
    {1, 2, 3},      // Sum = 6
    {4, 5, 6},      // Sum = 15
    {7, 8, 9}       // Sum = 24
};
rowSum(matrix);
// Output:
// Row 0 sum: 6
// Row 1 sum: 15
// Row 2 sum: 24
```

---

#### 3. Column-wise Sum

```java
public static void colSum(int[][] matrix) {
    int cols = matrix[0].length;
    
    for (int j = 0; j < cols; j++) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][j];
        }
        System.out.println("Column " + j + " sum: " + sum);
    }
}

// Usage
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};
colSum(matrix);
// Output:
// Column 0 sum: 12
// Column 1 sum: 15
// Column 2 sum: 18
```

---

#### 4. Transpose of Matrix

```java
public static int[][] transpose(int[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;
    
    int[][] transposed = new int[cols][rows];
    
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            transposed[j][i] = matrix[i][j];
        }
    }
    
    return transposed;
}

// Usage
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6}
};

int[][] result = transpose(matrix);
// Result:
// 1 4
// 2 5
// 3 6

System.out.println(Arrays.deepToString(result));
```

**In-place Transpose (Square Matrix only):**
```java
public static void transposeInPlace(int[][] matrix) {
    int n = matrix.length;  // Assume square matrix
    
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            // Swap matrix[i][j] and matrix[j][i]
            int temp = matrix[i][j];
            matrix[i][j] = matrix[j][i];
            matrix[j][i] = temp;
        }
    }
}
```

---

#### 5. Search in 2D Array

```java
public static boolean search2D(int[][] matrix, int target) {
    for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[i].length; j++) {
            if (matrix[i][j] == target) {
                System.out.println("Found at [" + i + "][" + j + "]");
                return true;
            }
        }
    }
    return false;
}

// Usage
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};
search2D(matrix, 5);  // Output: Found at [1][1]
```

---

#### 6. Diagonal Sum (Square Matrix)

```java
public static void diagonalSum(int[][] matrix) {
    int n = matrix.length;  // Assume square matrix
    
    int primarySum = 0;
    int secondarySum = 0;
    
    for (int i = 0; i < n; i++) {
        primarySum += matrix[i][i];              // Primary diagonal
        secondarySum += matrix[i][n - 1 - i];    // Secondary diagonal
    }
    
    System.out.println("Primary diagonal sum: " + primarySum);
    System.out.println("Secondary diagonal sum: " + secondarySum);
}

// Usage
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};
diagonalSum(matrix);
// Output:
// Primary diagonal sum: 15 (1+5+9)
// Secondary diagonal sum: 15 (3+5+7)
```

---

#### 7. Matrix Addition

```java
public static int[][] addMatrices(int[][] A, int[][] B) {
    int rows = A.length;
    int cols = A[0].length;
    
    // Check dimensions
    if (rows != B.length || cols != B[0].length) {
        throw new IllegalArgumentException("Matrix dimensions must match");
    }
    
    int[][] result = new int[rows][cols];
    
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            result[i][j] = A[i][j] + B[i][j];
        }
    }
    
    return result;
}

// Usage
int[][] A = {{1, 2}, {3, 4}};
int[][] B = {{5, 6}, {7, 8}};
int[][] C = addMatrices(A, B);
// Result: [[6, 8], [10, 12]]
```

---

#### 8. Matrix Multiplication

```java
public static int[][] multiplyMatrices(int[][] A, int[][] B) {
    int rowsA = A.length;
    int colsA = A[0].length;
    int rowsB = B.length;
    int colsB = B[0].length;
    
    // Check if multiplication possible
    if (colsA != rowsB) {
        throw new IllegalArgumentException("Cannot multiply: incompatible dimensions");
    }
    
    int[][] result = new int[rowsA][colsB];
    
    for (int i = 0; i < rowsA; i++) {
        for (int j = 0; j < colsB; j++) {
            for (int k = 0; k < colsA; k++) {
                result[i][j] += A[i][k] * B[k][j];
            }
        }
    }
    
    return result;
}

// Usage
int[][] A = {{1, 2}, {3, 4}};  // 2x2
int[][] B = {{5, 6}, {7, 8}};  // 2x2
int[][] C = multiplyMatrices(A, B);  // 2x2
// C[0][0] = 1*5 + 2*7 = 19
// C[0][1] = 1*6 + 2*8 = 22
// C[1][0] = 3*5 + 4*7 = 43
// C[1][1] = 3*6 + 4*8 = 50
// Result: [[19, 22], [43, 50]]
```

**Time Complexity:** O(n³) for n×n matrices

---

#### 9. Rotate Matrix 90° Clockwise

```java
public static void rotate90Clockwise(int[][] matrix) {
    int n = matrix.length;
    
    // Step 1: Transpose
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            int temp = matrix[i][j];
            matrix[i][j] = matrix[j][i];
            matrix[j][i] = temp;
        }
    }
    
    // Step 2: Reverse each row
    for (int i = 0; i < n; i++) {
        int left = 0, right = n - 1;
        while (left < right) {
            int temp = matrix[i][left];
            matrix[i][left] = matrix[i][right];
            matrix[i][right] = temp;
            left++;
            right--;
        }
    }
}

// Usage
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};
rotate90Clockwise(matrix);
// Result:
// 7 4 1
// 8 5 2
// 9 6 3
```

---

#### 10. Spiral Matrix Traversal

```java
public static void spiralTraversal(int[][] matrix) {
    if (matrix.length == 0) return;
    
    int top = 0, bottom = matrix.length - 1;
    int left = 0, right = matrix[0].length - 1;
    
    while (top <= bottom && left <= right) {
        // Traverse right
        for (int i = left; i <= right; i++) {
            System.out.print(matrix[top][i] + " ");
        }
        top++;
        
        // Traverse down
        for (int i = top; i <= bottom; i++) {
            System.out.print(matrix[i][right] + " ");
        }
        right--;
        
        // Traverse left
        if (top <= bottom) {
            for (int i = right; i >= left; i--) {
                System.out.print(matrix[bottom][i] + " ");
            }
            bottom--;
        }
        
        // Traverse up
        if (left <= right) {
            for (int i = bottom; i >= top; i--) {
                System.out.print(matrix[i][left] + " ");
            }
            left++;
        }
    }
}

// Usage
int[][] matrix = {
    {1,  2,  3,  4},
    {5,  6,  7,  8},
    {9, 10, 11, 12}
};
spiralTraversal(matrix);
// Output: 1 2 3 4 8 12 11 10 9 5 6 7
```

---

## Jagged Arrays

**Jagged Array** = 2D array where rows can have **different lengths**.

### Declaration and Initialization

```java
// Method 1: Specify rows only
int[][] jagged = new int[3][];  // 3 rows, columns not specified

jagged[0] = new int[]{1, 2};
jagged[1] = new int[]{3, 4, 5, 6};
jagged[2] = new int[]{7};

// Method 2: Direct initialization
int[][] jagged = {
    {1, 2},
    {3, 4, 5, 6},
    {7}
};

// Visualization:
// Row 0: [1, 2]
// Row 1: [3, 4, 5, 6]
// Row 2: [7]
```

---

### Traversing Jagged Arrays

```java
int[][] jagged = {
    {1, 2},
    {3, 4, 5, 6},
    {7}
};

// Important: Use jagged[i].length for each row!
for (int i = 0; i < jagged.length; i++) {
    for (int j = 0; j < jagged[i].length; j++) {  // Variable length!
        System.out.print(jagged[i][j] + " ");
    }
    System.out.println();
}
// Output:
// 1 2
// 3 4 5 6
// 7

// Enhanced for loop
for (int[] row : jagged) {
    for (int num : row) {
        System.out.print(num + " ");
    }
    System.out.println();
}
```

---

### Use Cases for Jagged Arrays

1. **Pascal's Triangle**
```java
int[][] pascal = new int[5][];
for (int i = 0; i < 5; i++) {
    pascal[i] = new int[i + 1];
    pascal[i][0] = pascal[i][i] = 1;
    for (int j = 1; j < i; j++) {
        pascal[i][j] = pascal[i-1][j-1] + pascal[i-1][j];
    }
}
// Output:
// 1
// 1 1
// 1 2 1
// 1 3 3 1
// 1 4 6 4 1
```

2. **Variable-length data** (student scores with different number of tests)
```java
int[][] studentScores = {
    {85, 90, 78},           // Student 1: d printArray(int[] arr) {
    for (int num : arr) {
        System.out.print(num + " ");
    }
}
```
---

## Array Algorithms

### 1. Two Pointer Technique

**Use Cases:** Palindrome check, pair sum, remove duplicates, merge sorted arrays

#### A. Pair Sum (Sorted Array)

```java
public static boolean findPairWithSum(int[] arr, int target) {
    int left = 0;
    int right = arr.length - 1;
    
    while (left < right) {
        int sum = arr[left] + arr[right];
        
        if (sum == target) {
            System.out.println("Pair found: " + arr[left] + ", " + arr[right]);
            return true;
        } else if (sum < target) {
            left++;
        } else {
            right--;
        }
    }
    
    return false;
}

// Usage
int[] arr = {1, 2, 3, 4, 5, 6};
findPairWithSum(arr, 9);  // Output: Pair found: 3, 6
```

**Time:** O(n), **Space:** O(1)

---

#### B. Remove Duplicates from Sorted Array

```java
public static int removeDuplicates(int[] arr) {
    if (arr.length == 0) return 0;
    
    int i = 0;  // Pointer for unique elements
    
    for (int j = 1; j < arr.length; j++) {
        if (arr[j] != arr[i]) {
            i++;
            arr[i] = arr[j];
        }
    }
    
    return i + 1;  // New length
}

// Usage
int[] arr = {1, 1, 2, 2, 3, 4, 4, 5};
int newLength = removeDuplicates(arr);
System.out.println("New length: " + newLength);  // 5
System.out.println(Arrays.toString(Arrays.copyOf(arr, newLength)));
// Output: [1, 2, 3, 4, 5]
```

**Time:** O(n), **Space:** O(1)

---

#### C. Dutch National Flag (Sort 0s, 1s, 2s)

```java
public static void sortColors(int[] arr) {
    int low = 0, mid = 0, high = arr.length - 1;
    
    while (mid <= high) {
        if (arr[mid] == 0) {
            swap(arr, low, mid);
            low++;
            mid++;
        } else if (arr[mid] == 1) {
            mid++;
        } else {  // arr[mid] == 2
            swap(arr, mid, high);
            high--;
        }
    }
}

private static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
}

// Usage
int[] arr = {2, 0, 1, 2, 1, 0, 1, 2, 0};
sortColors(arr);
System.out.println(Arrays.toString(arr));
// Output: [0, 0, 0, 1, 1, 1, 2, 2, 2]
```

**Time:** O(n), **Space:** O(1)

---

### 2. Sliding Window Technique

**Use Cases:** Maximum/minimum sum of k consecutive elements, longest substring

#### A. Maximum Sum of k Consecutive Elements

```java
public static int maxSumK(int[] arr, int k) {
    if (arr.length < k) return -1;
    
    // Calculate sum of first window
    int windowSum = 0;
    for (int i = 0; i < k; i++) {
        windowSum += arr[i];
    }
    
    int maxSum = windowSum;
    
    // Slide the window
    for (int i = k; i < arr.length; i++) {
        windowSum = windowSum - arr[i - k] + arr[i];
        maxSum = Math.max(maxSum, windowSum);
    }
    
    return maxSum;
}

// Usage
int[] arr = {1, 4, 2, 10, 23, 3, 1, 0, 20};
System.out.println(maxSumK(arr, 4));  // Output: 39 (10+23+3+1+0+20)
```

**Time:** O(n), **Space:** O(1)

---

#### B. Longest Subarray with Sum ≤ k

```java
public static int longestSubarrayWithSumK(int[] arr, int k) {
    int start = 0, sum = 0, maxLength = 0;
    
    for (int end = 0; end < arr.length; end++) {
        sum += arr[end];
        
        while (sum > k && start <= end) {
            sum -= arr[start];
            start++;
        }
        
        maxLength = Math.max(maxLength, end - start + 1);
    }
    
    return maxLength;
}

// Usage
int[] arr = {1, 2, 3, 4, 5};
System.out.println(longestSubarrayWithSumK(arr, 8));  // Output: 3 (1+2+3 or 3+4)
```

**Time:** O(n), **Space:** O(1)

---

### 3. Kadane's Algorithm (Maximum Subarray Sum)

```java
public static int maxSubarraySum(int[] arr) {
    int maxSoFar = arr[0];
    int maxEndingHere = arr[0];
    
    for (int i = 1; i < arr.length; i++) {
        maxEndingHere = Math.max(arr[i], maxEndingHere + arr[i]);
        maxSoFar = Math.max(maxSoFar, maxEndingHere);
    }
    
    return maxSoFar;
}

// Usage
int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
System.out.println("Max subarray sum: " + maxSubarraySum(arr));
// Output: 6 (subarray [4, -1, 2, 1])
```

**Time:** O(n), **Space:** O(1)

**With Indices:**
```java
public static void maxSubarraySumWithIndices(int[] arr) {
    int maxSoFar = arr[0];
    int maxEndingHere = arr[0];
    int start = 0, end = 0, tempStart = 0;
    
    for (int i = 1; i < arr.length; i++) {
        if (arr[i] > maxEndingHere + arr[i]) {
            maxEndingHere = arr[i];
            tempStart = i;
        } else {
            maxEndingHere = maxEndingHere + arr[i];
        }
        
        if (maxEndingHere > maxSoFar) {
            maxSoFar = maxEndingHere;
            start = tempStart;
            end = i;
        }
    }
    
    System.out.println("Max sum: " + maxSoFar);
    System.out.println("From index " + start + " to " + end);
}
```

---

### 4. Prefix Sum Technique

**Use Cases:** Range sum queries, subarray sum problems

#### A. Build Prefix Sum Array

```java
public static int[] buildPrefixSum(int[] arr) {
    int[] prefix = new int[arr.length];
    prefix[0] = arr[0];
    
    for (int i = 1; i < arr.length; i++) {
        prefix[i] = prefix[i - 1] + arr[i];
    }
    
    return prefix;
}

// Usage
int[] arr = {1, 2, 3, 4, 5};
int[] prefix = buildPrefixSum(arr);
System.out.println(Arrays.toString(prefix));
// Output: [1, 3, 6, 10, 15]
```

---

#### B. Range Sum Query

```java
public static int rangeSum(int[] prefix, int left, int right) {
    if (left == 0) {
        return prefix[right];
    }
    return prefix[right] - prefix[left - 1];
}

// Usage
int[] arr = {1, 2, 3, 4, 5};
int[] prefix = buildPrefixSum(arr);
System.out.println(rangeSum(prefix, 1, 3));  // Sum from index 1 to 3 = 2+3+4 = 9
```

**Time:** Build - O(n), Query - O(1)

---

### 5. Moore's Voting Algorithm (Majority Element)

Find element that appears more than n/2 times.

```java
public static int findMajorityElement(int[] arr) {
    int candidate = arr[0];
    int count = 1;
    
    // Find candidate
    for (int i = 1; i < arr.length; i++) {
        if (arr[i] == candidate) {
            count++;
        } else {
            count--;
        }
        
        if (count == 0) {
            candidate = arr[i];
            count = 1;
        }
    }
    
    // Verify candidate
    count = 0;
    for (int num : arr) {
        if (num == candidate) {
            count++;
        }
    }
    
    if (count > arr.length / 2) {
        return candidate;
    }
    
    return -1;  // No majority element
}

// Usage
int[] arr = {2, 2, 1, 1, 1, 2, 2};
System.out.println("Majority element: " + findMajorityElement(arr));
// Output: 2
```

**Time:** O(n), **Space:** O(1)

---

### 6. Fast and Slow Pointer (Floyd's Cycle Detection)

```java
public static boolean hasCycle(int[] arr) {
    // For arrays, typically used to detect cycles in linked structures
    // Example: Find duplicate in array with values 1 to n
    
    int slow = arr[0];
    int fast = arr[0];
    
    do {
        slow = arr[slow];
        fast = arr[arr[fast]];
    } while (slow != fast);
    
    // Find the entrance of the cycle
    slow = arr[0];
    while (slow != fast) {
        slow = arr[slow];
        fast = arr[fast];
    }
    
    return true;
}
```

---

## Common Array Problems

### 1. Find Missing Number (1 to N)

```java
public static int findMissingNumber(int[] arr, int n) {
    // Method 1: Using sum formula
    int expectedSum = n * (n + 1) / 2;
    int actualSum = 0;
    
    for (int num : arr) {
        actualSum += num;
    }
    
    return expectedSum - actualSum;
}

// Method 2: Using XOR
public static int findMissingNumberXOR(int[] arr, int n) {
    int xor1 = 0, xor2 = 0;
    
    for (int i = 0; i < arr.length; i++) {
        xor1 ^= arr[i];
    }
    
    for (int i = 1; i <= n; i++) {
        xor2 ^= i;
    }
    
    return xor1 ^ xor2;
}

// Usage
int[] arr = {1, 2, 4, 5, 6};  // Missing: 3
System.out.println(findMissingNumber(arr, 6));  // Output: 3
```

---

### 2. Find Duplicate Number

```java
public static int findDuplicate(int[] arr) {
    // Method 1: Using frequency array
    boolean[] seen = new boolean[arr.length];
    
    for (int num : arr) {
        if (seen[num]) {
            return num;
        }
        seen[num] = true;
    }
    
    return -1;
}

// Method 2: Floyd's Algorithm (O(1) space)
public static int findDuplicateOptimal(int[] arr) {
    int slow = arr[0];
    int fast = arr[0];
    
    // Find intersection point
    do {
        slow = arr[slow];
        fast = arr[arr[fast]];
    } while (slow != fast);
    
    // Find entrance to cycle (duplicate)
    slow = arr[0];
    while (slow != fast) {
        slow = arr[slow];
        fast = arr[fast];
    }
    
    return slow;
}
```

---

### 3. Move Zeros to End

```java
public static void moveZerosToEnd(int[] arr) {
    int nonZeroIndex = 0;
    
    // Move all non-zero elements to front
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] != 0) {
            arr[nonZeroIndex++] = arr[i];
        }
    }
    
    // Fill remaining with zeros
    while (nonZeroIndex < arr.length) {
        arr[nonZeroIndex++] = 0;
    }
}

// Usage
int[] arr = {0, 1, 0, 3, 12};
moveZerosToEnd(arr);
System.out.println(Arrays.toString(arr));
// Output: [1, 3, 12, 0, 0]
```

**Time:** O(n), **Space:** O(1)

---

### 4. Merge Two Sorted Arrays

```java
public static int[] mergeSortedArrays(int[] arr1, int[] arr2) {
    int[] result = new int[arr1.length + arr2.length];
    int i = 0, j = 0, k = 0;
    
    while (i < arr1.length && j < arr2.length) {
        if (arr1[i] <= arr2[j]) {
            result[k++] = arr1[i++];
        } else {
            result[k++] = arr2[j++];
        }
    }
    
    // Copy remaining elements
    while (i < arr1.length) {
        result[k++] = arr1[i++];
    }
    
    while (j < arr2.length) {
        result[k++] = arr2[j++];
    }
    
    return result;
}

// Usage
int[] arr1 = {1, 3, 5, 7};
int[] arr2 = {2, 4, 6, 8};
int[] merged = mergeSortedArrays(arr1, arr2);
System.out.println(Arrays.toString(merged));
// Output: [1, 2, 3, 4, 5, 6, 7, 8]
```

**Time:** O(n + m), **Space:** O(n + m)

---

### 5. Find Intersection of Two Arrays

```java
public static List<Integer> findIntersection(int[] arr1, int[] arr2) {
    Set<Integer> set = new HashSet<>();
    List<Integer> result = new ArrayList<>();
    
    for (int num : arr1) {
        set.add(num);
    }
    
    for (int num : arr2) {
        if (set.contains(num)) {
            result.add(num);
            set.remove(num);  // Avoid duplicates
        }
    }
    
    return result;
}

// For sorted arrays (more efficient)
public static List<Integer> findIntersectionSorted(int[] arr1, int[] arr2) {
    List<Integer> result = new ArrayList<>();
    int i = 0, j = 0;
    
    while (i < arr1.length && j < arr2.length) {
        if (arr1[i] == arr2[j]) {
            result.add(arr1[i]);
            i++;
            j++;
        } else if (arr1[i] < arr2[j]) {
            i++;
        } else {
            j++;
        }
    }
    
    return result;
}
```

---

### 6. Leaders in an Array

Element is a leader if it's greater than all elements to its right.

```java
public static List<Integer> findLeaders(int[] arr) {
    List<Integer> leaders = new ArrayList<>();
    int maxFromRight = arr[arr.length - 1];
    leaders.add(maxFromRight);
    
    for (int i = arr.length - 2; i >= 0; i--) {
        if (arr[i] > maxFromRight) {
            leaders.add(arr[i]);
            maxFromRight = arr[i];
        }
    }
    
    Collections.reverse(leaders);
    return leaders;
}

// Usage
int[] arr = {16, 17, 4, 3, 5, 2};
System.out.println(findLeaders(arr));
// Output: [17, 5, 2]
```

**Time:** O(n), **Space:** O(k) where k is number of leaders

---

### 7. Stock Buy and Sell (Maximum Profit)

```java
public static int maxProfit(int[] prices) {
    int minPrice = Integer.MAX_VALUE;
    int maxProfit = 0;
    
    for (int price : prices) {
        minPrice = Math.min(minPrice, price);
        maxProfit = Math.max(maxProfit, price - minPrice);
    }
    
    return maxProfit;
}

// Usage
int[] prices = {7, 1, 5, 3, 6, 4};
System.out.println("Max profit: " + maxProfit(prices));
// Output: 5 (buy at 1, sell at 6)
```

**Time:** O(n), **Space:** O(1)

---

### 8. Trapping Rain Water

```java
public static int trapRainWater(int[] height) {
    if (height.length == 0) return 0;
    
    int left = 0, right = height.length - 1;
    int leftMax = 0, rightMax = 0;
    int water = 0;
    
    while (left < right) {
        if (height[left] < height[right]) {
            if (height[left] >= leftMax) {
                leftMax = height[left];
            } else {
                water += leftMax - height[left];
            }
            left++;
        } else {
            if (height[right] >= rightMax) {
                rightMax = height[right];
            } else {
                water += rightMax - height[right];
            }
            right--;
        }
    }
    
    return water;
}

// Usage
int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
System.out.println("Water trapped: " + trapRainWater(height));
// Output: 6
```

**Time:** O(n), **Space:** O(1)

---

## Arrays Class Utility

### Important Methods

```java
import java.util.Arrays;

int[] arr = {5, 2, 8, 1, 9};

// 1. Sort
Arrays.sort(arr);  // [1, 2, 5, 8, 9]

// 2. Binary Search (requires sorted array)
int index = Arrays.binarySearch(arr, 5);  // Returns index

// 3. Fill array with value
Arrays.fill(arr, 0);  // All elements become 0

// 4. Fill range
Arrays.fill(arr, 1, 4, 10);  // Fill index 1 to 3 with 10

// 5. Copy array
int[] copy = Arrays.copyOf(arr, arr.length);

// 6. Copy range
int[] range = Arrays.copyOfRange(arr, 1, 4);  // Index 1 to 3

// 7. Compare arrays
boolean equal = Arrays.equals(arr, copy);

// 8. Deep compare (for multi-dimensional)
boolean deepEqual = Arrays.deepEquals(matrix1, matrix2);

// 9. Convert to String
String str = Arrays.toString(arr);

// 10. Deep String (for multi-dimensional)
String deepStr = Arrays.deepToString(matrix);

// 11. Stream operations (Java 8+)
int sum = Arrays.stream(arr).sum();
double avg = Arrays.stream(arr).average().orElse(0.0);
int max = Arrays.stream(arr).max().getAsInt();
int min = Arrays.stream(arr).min().getAsInt();

// 12. Parallel sort (for large arrays)
Arrays.parallelSort(arr);

// 13. Set all elements using generator
Arrays.setAll(arr, i -> i * i);  // arr[i] = i²

// 14. Parallel prefix (cumulative operation)
Arrays.parallelPrefix(arr, (a, b) -> a + b);  // Prefix sum

// 15. Compare arrays lexicographically
int result = Arrays.compare(arr1, arr2);
// Returns: negative if arr1 < arr2, 0 if equal, positive if arr1 > arr2

// 16. Mismatch (find first difference)
int mismatchIndex = Arrays.mismatch(arr1, arr2);
```

---

## Memory Considerations

### 1. Memory Usage

```java
// Primitive array
int[] arr = new int[1000];
// Memory: 4000 bytes + object overhead (~24 bytes) = ~4024 bytes

// Object array
String[] names = new String[1000];
// Memory: 8000 bytes (references) + overhead + actual String objects
```

### 2. Array Size Limits

```java
// Maximum array size: Integer.MAX_VALUE (2³¹ - 1)
// Practical limit: JVM heap size and system memory

// Large array example
int[] large = new int[10_000_000];  // 40 MB
```

### 3. Memory Leaks

```java
// Problem: Holding references prevents garbage collection
public class LeakExample {
    private static List<int[]> cache = new ArrayList<>();
    
    public static void addToCache(int[] data) {
        cache.add(data);  // Never cleared - memory leak!
    }
}

// Solution: Clear when done
cache.clear();
```

---

## Common Mistakes

### 1. Array Index Out of Bounds

```java
// WRONG
int[] arr = new int[5];
arr[5] = 10;  // ERROR! Valid indices: 0-4

// CORRECT
if (index >= 0 && index < arr.length) {
    arr[index] = 10;
}
```

### 2. Null Pointer Exception

```java
// WRONG
int[] arr = null;
System.out.println(arr.length);  // NullPointerException!

// CORRECT
if (arr != null) {
    System.out.println(arr.length);
}
```

### 3. Comparing with ==

```java
// WRONG - Compares references
int[] arr1 = {1, 2, 3};
int[] arr2 = {1, 2, 3};
if (arr1 == arr2) { }  // Always false!

// CORRECT - Compares content
if (Arrays.equals(arr1, arr2)) { }
```

### 4. Modifying Array in Enhanced for Loop

```java
// DOESN'T WORK
for (int num : arr) {
    num = num * 2;  // Only modifies local variable
}

// CORRECT
for (int i = 0; i < arr.length; i++) {
    arr[i] = arr[i] * 2;
}
```

### 5. Shallow Copy Issue

```java
// PROBLEM
int[][] original = {{1, 2}, {3, 4}};
int[][] copy = original.clone();  // Shallow copy
copy[0][0] = 99;  // Also changes original[0][0]!

// SOLUTION: Deep copy
int[][] deepCopy = new int[original.length][];
for (int i = 0; i < original.length; i++) {
    deepCopy[i] = original[i].clone();
}
```

---

## Best Practices

### 1. Use Enhanced for Loop When Possible

```java
// Good for read-only operations
for (int num : arr) {
    System.out.println(num);
}
```

### 2. Validate Input

```java
public static int findMax(int[] arr) {
    if (arr == null || arr.length == 0) {
        throw new IllegalArgumentException("Array cannot be null or empty");
    }
    // ... rest of code
}
```

### 3. Use Arrays Utility Methods

```java
// Instead of manual loops
Arrays.sort(arr);
Arrays.fill(arr, 0);
System.out.println(Arrays.toString(arr));
```

### 4. Consider ArrayList for Dynamic Size

```java
// If size changes frequently, use ArrayList
List<Integer> list = new ArrayList<>();
list.add(1);
list.add(2);
list.remove(0);
```

### 5. Use Appropriate Data Structures

```java
// Fast lookup: HashSet
// Sorted: TreeSet
// Frequency count: HashMap
// FIFO: Queue
// LIFO: Stack
```

### 6. Document Assumptions

```java
/**
 * Finds max element in array
 * @param arr non-null, non-empty array
 * @return maximum element
 * @throws IllegalArgumentException if arr is null or empty
 */
public static int findMax(int[] arr) {
    // ...
}
```

---

## Practice Problems

### Beginner Level
1. Find the sum and average of array elements
2. Count even and odd numbers
3. Reverse an array
4. Find maximum and minimum
5. Linear search
6. Check if array is palindrome
7. Count occurrences of an element
8. Merge two arrays
9. Copy array elements
10. Remove duplicates from sorted array

### Intermediate Level
1. Binary search implementation
2. Rotate array by k positions
3. Find missing number in sequence
4. Find duplicate number
5. Move zeros to end
6. Leaders in an array
7. Maximum subarray sum (Kadane's)
8. Stock buy and sell
9. Pair sum problem
10. Sort 0s, 1s, and 2s

### Advanced Level
1. Trapping rain water
2. Longest consecutive sequence
3. Product of array except self
4. Find all subarrays with given sum
5. Minimum platforms required
6. Merge overlapping intervals
7. Spiral matrix traversal
8. Search in row-wise and column-wise sorted matrix
9. Count inversions in array
10. Maximum circular subarray sum

---

## Additional Advanced Topics

### 1. Bit Manipulation with Arrays

```java
// XOR of all elements
public static int xorAll(int[] arr) {
    int xor = 0;
    for (int num : arr) {
        xor ^= num;
    }
    return xor;
}

// Find two non-repeating elements
public static int[] findTwoNonRepeating(int[] arr) {
    int xor = 0;
    for (int num : arr) {
        xor ^= num;
    }
    
    int rightmostSetBit = xor & -xor;
    int x = 0, y = 0;
    
    for (int num : arr) {
        if ((num & rightmostSetBit) != 0) {
            x ^= num;
        } else {
            y ^= num;
        }
    }
    
    return new int[]{x, y};
}
```

### 2. Dynamic Programming with Arrays

```java
// Longest Increasing Subsequence
public static int lengthOfLIS(int[] arr) {
    if (arr.length == 0) return 0;
    
    int[] dp = new int[arr.length];
    Arrays.fill(dp, 1);
    int maxLength = 1;
    
    for (int i = 1; i < arr.length; i++) {
        for (int j = 0; j < i; j++) {
            if (arr[i] > arr[j]) {
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
        maxLength = Math.max(maxLength, dp[i]);
    }
    
    return maxLength;
}
```

### 3. Greedy Algorithms

```java
// Minimum number of jumps to reach end
public static int minJumps(int[] arr) {
    if (arr.length <= 1) return 0;
    if (arr[0] == 0) return -1;
    
    int maxReach = arr[0];
    int steps = arr[0];
    int jumps = 1;
    
    for (int i = 1; i < arr.length; i++) {
        if (i == arr.length - 1) return jumps;
        
        maxReach = Math.max(maxReach, i + arr[i]);
        steps--;
        
        if (steps == 0) {
            jumps++;
            if (i >= maxReach) return -1;
            steps = maxReach - i;
        }
    }
    
    return -1;
}
```

---

## Summary

### Key Takeaways

1. **Arrays are fixed-size** - Choose wisely when creating
2. **Zero-based indexing** - Valid indices: 0 to length-1
3. **Contiguous memory** - Fast random access O(1)
4. **Use Arrays utility class** - Built-in methods save time
5. **Know your algorithms** - Two pointers, sliding window, Kadane's, etc.
6. **Practice, practice, practice** - Arrays are fundamental to DSA

### Time Complexities Quick Reference

| Operation | Time Complexity |
|-----------|----------------|
| Access by index | O(1) |
| Search (unsorted) | O(n) |
| Search (sorted) | O(log n) |
| Insert/Delete at end | O(1) |
| Insert/Delete at middle | O(n) |
| Sorting | O(n log n) |

### Space Complexities Quick Reference

| Structure | Space |
|-----------|-------|
| 1D Array | O(n) |
| 2D Array | O(m × n) |
| Jagged Array | Varies |
| Auxiliary space for most algorithms | O(1) to O(n) |

---
