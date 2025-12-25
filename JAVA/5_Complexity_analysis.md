# Complexity Analysis - Complete Guide
## Big O Notation, Time & Space Complexity

---

## Table of Contents

1. [Introduction to Complexity Analysis](#introduction-to-complexity-analysis)
2. [Why Complexity Analysis Matters](#why-complexity-analysis-matters)
3. [Big O Notation](#big-o-notation)
4. [Time Complexity](#time-complexity)
5. [Space Complexity](#space-complexity)
6. [Common Time Complexities](#common-time-complexities)
7. [Best, Average, Worst Case Analysis](#best-average-worst-case-analysis)
8. [Analyzing Code Snippets](#analyzing-code-snippets)
9. [Rules for Calculating Complexity](#rules-for-calculating-complexity)
10. [Complexity Comparison](#complexity-comparison)
11. [Common Patterns and Their Complexities](#common-patterns-and-their-complexities)
12. [Practice Problems](#practice-problems)
13. [Real-World Examples](#real-world-examples)
14. [Common Mistakes](#common-mistakes)
15. [Tips and Tricks](#tips-and-tricks)

---

## Introduction to Complexity Analysis

### What is Complexity Analysis?

**Complexity Analysis** is the process of determining how much time and space an algorithm requires as the input size grows.

```
┌─────────────────────────────────────────┐
│       Complexity Analysis               │
├─────────────────────────────────────────┤
│ • Measures algorithm efficiency         │
│ • Independent of hardware/language      │
│ • Focuses on growth rate                │
│ • Helps compare algorithms              │
│ • Predicts scalability                  │
└─────────────────────────────────────────┘
```

### Two Main Types

1. **Time Complexity** - How execution time grows with input size
2. **Space Complexity** - How memory usage grows with input size

---

## Why Complexity Analysis Matters

### Real-World Impact

```java
// Algorithm 1: O(n²) - Bubble Sort
// For n = 1,000,000 items: ~1 trillion operations

// Algorithm 2: O(n log n) - Merge Sort  
// For n = 1,000,000 items: ~20 million operations

// Difference: 50,000x faster! 🚀
```

### Scalability Example

```
Input Size (n)    O(1)    O(log n)    O(n)     O(n log n)    O(n²)
─────────────────────────────────────────────────────────────────
10                1       3           10       30            100
100               1       7           100      700           10,000
1,000             1       10          1,000    10,000        1,000,000
10,000            1       13          10,000   130,000       100,000,000
100,000           1       17          100,000  1,700,000     10,000,000,000
```

**Key Insight:** As input grows, complexity differences become MASSIVE!

---

## Big O Notation

### What is Big O?

**Big O Notation** describes the **upper bound** (worst case) of an algorithm's growth rate.

```
Formal Definition:
f(n) = O(g(n)) if there exist constants c and n₀ such that:
0 ≤ f(n) ≤ c·g(n) for all n ≥ n₀

Translation: "f grows no faster than g (times some constant)"
```

### Key Characteristics

1. **Describes growth rate**, not exact time
2. **Ignores constants** - O(2n) → O(n)
3. **Ignores lower-order terms** - O(n² + n) → O(n²)
4. **Worst-case by default** (unless specified)
5. **Hardware-independent**

### Common Notation Types

- **Big O (O)** - Upper bound (worst case) ⭐ Most common
- **Big Omega (Ω)** - Lower bound (best case)
- **Big Theta (Θ)** - Tight bound (average case)
- **Little o (o)** - Strict upper bound
- **Little omega (ω)** - Strict lower bound

---

## Time Complexity

### Definition

**Time Complexity** measures how the number of operations grows with input size.

### Important Notes

- NOT actual time (seconds/milliseconds)
- Count of **primitive operations**
- Independent of hardware/language
- Focuses on **asymptotic behavior** (large inputs)

### What Counts as an Operation?

```java
// Each line is typically 1 operation
int x = 5;              // 1 operation (assignment)
int y = x + 10;         // 2 operations (addition + assignment)
if (x > 0) { }          // 1 operation (comparison)
arr[i] = 10;            // 2 operations (array access + assignment)
return x;               // 1 operation (return)
```

---

## Space Complexity

### Definition

**Space Complexity** measures how much memory an algorithm uses as input grows.

### Components

1. **Input Space** - Memory for input (usually not counted)
2. **Auxiliary Space** - Extra memory used by algorithm
3. **Output Space** - Memory for output (sometimes counted)

**Note:** When we say "space complexity", we typically mean **auxiliary space**.

### What Counts as Space?

```java
// Variables: O(1) each
int x = 5;
double y = 3.14;
boolean flag = true;

// Arrays: O(n) where n is size
int[] arr = new int[n];        // O(n)
int[][] matrix = new int[n][n]; // O(n²)

// Recursive call stack: O(depth)
void recursion(int n) {
    if (n == 0) return;
    recursion(n - 1);  // O(n) space for call stack
}
```

---

## Common Time Complexities

### 1. O(1) - Constant Time ⚡

**Definition:** Execution time is constant, regardless of input size.

**Examples:**
```java
// Accessing array element
int value = arr[5];  // O(1)

// Arithmetic operations
int sum = a + b;     // O(1)

// HashMap get/put (average case)
map.get(key);        // O(1)
map.put(key, value); // O(1)

// Stack push/pop
stack.push(item);    // O(1)
stack.pop();         // O(1)
```

**Real Example:**
```java
public static int getFirstElement(int[] arr) {
    return arr[0];  // Always 1 operation
}
```

**Visualization:**
```
Time ↑
     |  _______________
     | |
     | |
     |_|________________ → Input Size
```

---

### 2. O(log n) - Logarithmic Time 🌲

**Definition:** Time grows logarithmically with input size.

**Key Insight:** Reduces problem size by half (or constant factor) each step.

**Examples:**
```java
// Binary Search
public static int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (arr[mid] == target) return mid;
        else if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}
// Each iteration cuts search space in half
// Steps = log₂(n)
```

**Other Examples:**
```java
// Finding power (divide and conquer)
public static int power(int x, int n) {
    if (n == 0) return 1;
    int half = power(x, n / 2);
    if (n % 2 == 0) return half * half;
    return x * half * half;
}

// Tree operations (balanced BST)
// - Search, Insert, Delete in balanced BST

// Finding number of digits
int digits = (int) Math.log10(n) + 1;  // O(log n)
```

**Visualization:**
```
Time ↑
     |        ___
     |      /
     |    /
     |  /
     |/_________________ → Input Size
```

**Growth Rate:**
```
n = 1       → 1 step
n = 10      → 3 steps
n = 100     → 7 steps
n = 1,000   → 10 steps
n = 1,000,000 → 20 steps
```

---

### 3. O(n) - Linear Time 📈

**Definition:** Time grows linearly with input size.

**Examples:**
```java
// Linear search
public static int linearSearch(int[] arr, int target) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == target) return i;
    }
    return -1;
}

// Sum of array
public static int sum(int[] arr) {
    int sum = 0;
    for (int num : arr) {
        sum += num;  // n iterations
    }
    return sum;
}

// Finding max
public static int findMax(int[] arr) {
    int max = arr[0];
    for (int i = 1; i < arr.length; i++) {
        if (arr[i] > max) max = arr[i];
    }
    return max;
}

// Traversing linked list
while (node != null) {
    // process node
    node = node.next;
}
```

**Visualization:**
```
Time ↑
     |           /
     |         /
     |       /
     |     /
     |   /
     | /
     |/_________________ → Input Size
```

---

### 4. O(n log n) - Linearithmic Time 📊

**Definition:** Combination of linear and logarithmic growth.

**Key Insight:** Often seen in efficient sorting algorithms that use divide-and-conquer.

**Examples:**
```java
// Merge Sort
public static void mergeSort(int[] arr, int left, int right) {
    if (left < right) {
        int mid = left + (right - left) / 2;
        
        mergeSort(arr, left, mid);      // T(n/2)
        mergeSort(arr, mid + 1, right); // T(n/2)
        merge(arr, left, mid, right);   // O(n)
    }
}
// Divides array log(n) times, each level takes O(n) to merge
// Total: O(n log n)

// Quick Sort (average case)
// Heap Sort
// Tim Sort (Java's Arrays.sort for objects)

// Building a balanced BST from sorted array
public static TreeNode sortedArrayToBST(int[] arr) {
    return helper(arr, 0, arr.length - 1);
}
private static TreeNode helper(int[] arr, int left, int right) {
    if (left > right) return null;
    int mid = left + (right - left) / 2;
    TreeNode node = new TreeNode(arr[mid]);
    node.left = helper(arr, left, mid - 1);
    node.right = helper(arr, mid + 1, right);
    return node;
}
```

**Visualization:**
```
Time ↑
     |              /
     |            /
     |          /
     |        /
     |      /
     |    /
     |  /
     |/_________________ → Input Size
```

**Growth Rate:**
```
n = 10      → 30 operations
n = 100     → 700 operations
n = 1,000   → 10,000 operations
n = 10,000  → 130,000 operations
```

---

### 5. O(n²) - Quadratic Time 🐌

**Definition:** Time grows quadratically (nested iteration).

**Examples:**
```java
// Bubble Sort
public static void bubbleSort(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n - 1; i++) {           // n times
        for (int j = 0; j < n - i - 1; j++) {   // n times
            if (arr[j] > arr[j + 1]) {
                swap(arr, j, j + 1);
            }
        }
    }
}
// Total: n × n = n² operations

// Selection Sort, Insertion Sort
// Nested loop pattern
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        // O(1) operation
    }
}

// Comparing all pairs
for (int i = 0; i < n; i++) {
    for (int j = i + 1; j < n; j++) {
        // compare arr[i] with arr[j]
    }
}
```

**Visualization:**
```
Time ↑
     |                    /
     |                  /
     |               /
     |            /
     |         /
     |      /
     |   /
     | /
     |/_________________ → Input Size
```

**Growth Rate:**
```
n = 10      → 100 operations
n = 100     → 10,000 operations
n = 1,000   → 1,000,000 operations
n = 10,000  → 100,000,000 operations
```

---

### 6. O(n³) - Cubic Time 🦕

**Examples:**
```java
// Triple nested loop
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        for (int k = 0; k < n; k++) {
            // O(1) operation
        }
    }
}

// Matrix multiplication (naive approach)
public static int[][] multiplyMatrices(int[][] A, int[][] B) {
    int n = A.length;
    int[][] C = new int[n][n];
    
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < n; k++) {
                C[i][j] += A[i][k] * B[k][j];
            }
        }
    }
    return C;
}
```

**Growth Rate:**
```
n = 10      → 1,000 operations
n = 100     → 1,000,000 operations
n = 1,000   → 1,000,000,000 operations (1 billion!)
```

---

### 7. O(2^n) - Exponential Time 💥

**Definition:** Time doubles with each additional input element.

**Examples:**
```java
// Fibonacci (naive recursive)
public static int fibonacci(int n) {
    if (n <= 1) return n;
    return fibonacci(n - 1) + fibonacci(n - 2);
}
// Each call branches into 2 calls
// Tree height = n
// Total calls ≈ 2^n

// All subsets of a set
public static void generateSubsets(int[] arr, int index, List<Integer> current) {
    if (index == arr.length) {
        System.out.println(current);
        return;
    }
    
    // Exclude current element
    generateSubsets(arr, index + 1, current);
    
    // Include current element
    current.add(arr[index]);
    generateSubsets(arr, index + 1, current);
    current.remove(current.size() - 1);
}
// 2^n subsets

// Tower of Hanoi
public static void towerOfHanoi(int n, char from, char to, char aux) {
    if (n == 1) {
        System.out.println("Move disk 1 from " + from + " to " + to);
        return;
    }
    towerOfHanoi(n - 1, from, aux, to);
    System.out.println("Move disk " + n + " from " + from + " to " + to);
    towerOfHanoi(n - 1, aux, to, from);
}
// 2^n - 1 moves
```

**Visualization:**
```
Time ↑
     |                             |
     |                           /
     |                        /
     |                    /
     |               /
     |          /
     |     /
     |  /
     |/_________________ → Input Size
```

**Growth Rate (EXPLOSIVE!):**
```
n = 5   → 32 operations
n = 10  → 1,024 operations
n = 20  → 1,048,576 operations
n = 30  → 1,073,741,824 operations (1 billion!)
n = 40  → 1,099,511,627,776 operations (1 trillion!)
```

---

### 8. O(n!) - Factorial Time 🌋

**Definition:** Most expensive complexity. Practically unsolvable for n > 15.

**Examples:**
```java
// Generate all permutations
public static void permute(int[] arr, int start) {
    if (start == arr.length - 1) {
        System.out.println(Arrays.toString(arr));
        return;
    }
    
    for (int i = start; i < arr.length; i++) {
        swap(arr, start, i);
        permute(arr, start + 1);
        swap(arr, start, i);
    }
}
// n! permutations

// Traveling Salesman Problem (brute force)
// Trying all possible routes
```

**Growth Rate (CATASTROPHIC!):**
```
n = 5   → 120 operations
n = 10  → 3,628,800 operations
n = 15  → 1,307,674,368,000 operations (1.3 trillion!)
n = 20  → 2,432,902,008,176,640,000 operations
```

---

### Complexity Hierarchy (Fastest to Slowest)

```
O(1) < O(log n) < O(n) < O(n log n) < O(n²) < O(n³) < O(2^n) < O(n!)

Excellent ←                                            → Terrible
```

**Visual Comparison:**
```
For n = 100:

O(1)        → 1 operation          ⚡ Instant
O(log n)    → 7 operations         ⚡ Instant
O(n)        → 100 operations       ⚡ Instant
O(n log n)  → 700 operations       ✓ Fast
O(n²)       → 10,000 operations    ⚠ Slow
O(2^n)      → 10^30 operations     ❌ Impossible
O(n!)       → 10^157 operations    ❌ Universe dies first
```

---

## Best, Average, Worst Case Analysis

### Three Cases Explained

```
┌──────────────────────────────────────────┐
│  Best Case (Ω)                           │
│  - Most favorable input                  │
│  - Minimum operations needed             │
│                                          │
│  Average Case (Θ)                        │
│  - Typical/expected input                │
│  - Expected operations                   │
│                                          │
│  Worst Case (O)                          │
│  - Most unfavorable input                │
│  - Maximum operations needed             │
└──────────────────────────────────────────┘
```

### Example 1: Linear Search

```java
public static int linearSearch(int[] arr, int target) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == target) {
            return i;
        }
    }
    return -1;
}
```

**Analysis:**
- **Best Case: O(1)** - Target is first element
- **Average Case: O(n/2) = O(n)** - Target in middle (on average)
- **Worst Case: O(n)** - Target is last element or not present

---

### Example 2: Binary Search

```java
public static int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (arr[mid] == target) return mid;
        else if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}
```

**Analysis:**
- **Best Case: O(1)** - Target is middle element
- **Average Case: O(log n)** - Need to split multiple times
- **Worst Case: O(log n)** - Target at ends or not present

---

### Example 3: Quick Sort

```java
public static void quickSort(int[] arr, int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
    }
}
```

**Analysis:**
- **Best Case: O(n log n)** - Pivot divides evenly every time
- **Average Case: O(n log n)** - Pivot is reasonably balanced
- **Worst Case: O(n²)** - Pivot is smallest/largest every time (sorted array)

---

### Example 4: Insertion Sort

```java
public static void insertionSort(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
        int key = arr[i];
        int j = i - 1;
        
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            j--;
        }
        arr[j + 1] = key;
    }
}
```

**Analysis:**
- **Best Case: O(n)** - Array already sorted (inner loop never runs)
- **Average Case: O(n²)** - Elements partially sorted
- **Worst Case: O(n²)** - Array reverse sorted

---

### Sorting Algorithms Comparison

| Algorithm      | Best Case   | Average Case | Worst Case  | Space  | Stable |
|----------------|-------------|--------------|-------------|--------|--------|
| Bubble Sort    | O(n)        | O(n²)        | O(n²)       | O(1)   | Yes    |
| Insertion Sort | O(n)        | O(n²)        | O(n²)       | O(1)   | Yes    |
| Selection Sort | O(n²)       | O(n²)        | O(n²)       | O(1)   | No     |
| Merge Sort     | O(n log n)  | O(n log n)   | O(n log n)  | O(n)   | Yes    |
| Quick Sort     | O(n log n)  | O(n log n)   | O(n²)       | O(log n)| No    |
| Heap Sort      | O(n log n)  | O(n log n)   | O(n log n)  | O(1)   | No     |
| Counting Sort  | O(n + k)    | O(n + k)     | O(n + k)    | O(k)   | Yes    |
| Radix Sort     | O(nk)       | O(nk)        | O(nk)       | O(n+k) | Yes    |

---

## Analyzing Code Snippets

### Step-by-Step Process

1. **Identify loops and recursion**
2. **Count iterations**
3. **Multiply complexities of nested structures**
4. **Add complexities of sequential structures**
5. **Keep dominant term only**
6. **Drop constants**

---

### Practice: Analyze These Snippets

#### Example 1: Simple Loop

```java
public static void example1(int n) {
    for (int i = 0; i < n; i++) {      // n iterations
        System.out.println(i);          // O(1) operation
    }
}
```

**Analysis:**
- Loop runs `n` times
- Each iteration: O(1)
- **Total: O(n)**

---

#### Example 2: Nested Loops (Same Range)

```java
public static void example2(int n) {
    for (int i = 0; i < n; i++) {          // n iterations
        for (int j = 0; j < n; j++) {      // n iterations
            System.out.println(i + j);      // O(1)
        }
    }
}
```

**Analysis:**
- Outer loop: n times
- Inner loop: n times
- **Total: n × n = O(n²)**

---

#### Example 3: Nested Loops (Different Ranges)

```java
public static void example3(int n, int m) {
    for (int i = 0; i < n; i++) {          // n iterations
        for (int j = 0; j < m; j++) {      // m iterations
            System.out.println(i + j);      // O(1)
        }
    }
}
```

**Analysis:**
- Outer loop: n times
- Inner loop: m times
- **Total: O(n × m)**

---

#### Example 4: Sequential Loops

```java
public static void example4(int n) {
    for (int i = 0; i < n; i++) {      // n iterations
        System.out.println(i);
    }
    
    for (int j = 0; j < n; j++) {      // n iterations
        System.out.println(j);
    }
}
```

**Analysis:**
- First loop: O(n)
- Second loop: O(n)
- Sequential, so add: O(n) + O(n) = O(2n)
- **Drop constant: O(n)**

---

#### Example 5: Loop with Increment by 2

```java
public static void example5(int n) {
    for (int i = 0; i < n; i += 2) {   // n/2 iterations
        System.out.println(i);
    }
}
```

**Analysis:**
- Loop runs `n/2` times
- Each iteration: O(1)
- Total: O(n/2)
- **Drop constant: O(n)**

---

#### Example 6: Logarithmic Loop

```java
public static void example6(int n) {
    for (int i = 1; i < n; i *= 2) {   // log₂(n) iterations
        System.out.println(i);
    }
}
```

**Analysis:**
- i: 1, 2, 4, 8, 16, ..., n
- After k iterations: i = 2^k
- Stop when 2^k >= n → k = log₂(n)
- **Total: O(log n)**

---

#### Example 7: Dividing by 2

```java
public static void example7(int n) {
    while (n > 1) {                    // log₂(n) iterations
        System.out.println(n);
        n = n / 2;
    }
}
```

**Analysis:**
- n: n, n/2, n/4, n/8, ..., 1
- After k iterations: n = n/2^k
- Stop when n/2^k <= 1 → k = log₂(n)
- **Total: O(log n)**

---

#### Example 8: Nested Dependent Loops

```java
public static void example8(int n) {
    for (int i = 0; i < n; i++) {          // n iterations
        for (int j = 0; j < i; j++) {      // 0, 1, 2, ..., n-1 iterations
            System.out.println(i + j);
        }
    }
}
```

**Analysis:**
- Outer loop: n times
- Inner loop: 0 + 1 + 2 + ... + (n-1) = n(n-1)/2
- Total: n(n-1)/2 = (n² - n)/2
- Drop lower term and constant: **O(n²)**

---

#### Example 9: Triple Nested Loop

```java
public static void example9(int n) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < n; k++) {
                System.out.println(i + j + k);
            }
        }
    }
}
```

**Analysis:**
- Three nested loops, each running n times
- **Total: n × n × n = O(n³)**

---

#### Example 10: Complex Sequential

```java
public static void example10(int n) {
    // Part 1
    for (int i = 0; i < n; i++) {          // O(n)
        System.out.println(i);
    }
    
    // Part 2
    for (int i = 0; i < n; i++) {          // O(n)
        for (int j = 0; j < n; j++) {      // O(n)
            System.out.println(i + j);
        }
    }
    
    // Part 3
    for (int i = 1; i < n; i *= 2) {       // O(log n)
        System.out.println(i);
    }
}
```

**Analysis:**
- Part 1: O(n)
- Part 2: O(n²)
- Part 3: O(log n)
- Sequential, so add: O(n) + O(n²) + O(log n)
- **Keep dominant term: O(n²)**

---

#### Example 11: Recursion - Fibonacci (Naive)

```java
public static int fibonacci(int n) {
    if (n <= 1) return n;              // Base case: O(1)
    return fibonacci(n - 1) + fibonacci(n - 2);
}
```

**Analysis:**
- Each call makes 2 recursive calls
- Tree depth: n
- Total nodes in tree: 2^n (approximately)
- **Total: O(2^n)**

**Recursion Tree:**
```
                    fib(5)
                  /        \
           fib(4)            fib(3)
          /      \          /      \
     fib(3)   fib(2)    fib(2)   fib(1)
     /   \     /   \     /   \
 fib(2) fib(1) ...
```

---

#### Example 12: Recursion - Binary Search

```java
public static int binarySearch(int[] arr, int left, int right, int target) {
    if (left > right) return -1;
    
    int mid = left + (right - left) / 2;
    
    if (arr[mid] == target) return mid;
    else if (arr[mid] < target) 
        return binarySearch(arr, mid + 1, right, target);
    else 
        return binarySearch(arr, left, mid - 1, target);
}
```

**Analysis:**
- Recurrence: T(n) = T(n/2) + O(1)
- Divides problem in half each time
- Depth of recursion: log(n)
- **Total: O(log n)**

---

#### Example 13: Recursion - Merge Sort

```java
public static void mergeSort(int[] arr, int left, int right) {
    if (left < right) {
        int mid = left + (right - left) / 2;
        
        mergeSort(arr, left, mid);          // T(n/2)
        mergeSort(arr, mid + 1, right);     // T(n/2)
        mergeSort(arr, left, mid, right);       // O(n)
    }
}
```

**Analysis:**
- Recurrence: T(n) = 2T(n/2) + O(n)
- Master Theorem: a=2, b=2, f(n)=n
- log₂(2) = 1, n^1 = n
- **Total: O(n log n)**

---

#### Example 14: Multiple Conditions

```java
public static void example14(int n) {
    if (n < 100) {                         // O(1) check
        for (int i = 0; i < n; i++) {      // O(n)
            System.out.println(i);
        }
    } else {
        for (int i = 0; i < n; i++) {      // O(n)
            for (int j = 0; j < n; j++) {  // O(n)
                System.out.println(i + j);
            }
        }
    }
}
```

**Analysis:**
- Consider worst case
- Second branch has O(n²)
- **Worst Case: O(n²)**

---

#### Example 15: String Operations

```java
public static void example15(String str) {
    int n = str.length();
    
    for (int i = 0; i < n; i++) {          // O(n)
        String sub = str.substring(0, i);  // O(i) operation
        System.out.println(sub);
    }
}
```

**Analysis:**
- Loop: n iterations
- Each substring operation: O(i) where i varies from 0 to n
- Total: 0 + 1 + 2 + ... + n = n(n+1)/2
- **Total: O(n²)**

---

## Rules for Calculating Complexity

### Rule 1: Drop Constants

```java
// Example
for (int i = 0; i < n; i++) {      // n iterations
    // code
}
for (int i = 0; i < n; i++) {      // n iterations
    // code
}
for (int i = 0; i < n; i++) {      // n iterations
    // code
}

// Analysis: 3n operations
// Complexity: O(3n) → O(n) ✓
```

**Why?** Constants don't affect growth rate for large n.

---

### Rule 2: Drop Non-Dominant Terms

```java
// Example
for (int i = 0; i < n; i++) {              // O(n)
    System.out.println(i);
}

for (int i = 0; i < n; i++) {              // O(n²)
    for (int j = 0; j < n; j++) {
        System.out.println(i + j);
    }
}

// Analysis: O(n) + O(n²) = O(n²) ✓
```

**Why?** For large n, n² dominates n completely.

**Examples:**
- O(n² + n) → O(n²)
- O(n³ + n² + n) → O(n³)
- O(2^n + n²) → O(2^n)
- O(n log n + n) → O(n log n)

---

### Rule 3: Different Inputs → Different Variables

```java
// WRONG Analysis
public static void example(int[] arr1, int[] arr2) {
    for (int i = 0; i < arr1.length; i++) {    // O(n)?
        System.out.println(arr1[i]);
    }
    
    for (int i = 0; i < arr2.length; i++) {    // O(n)?
        System.out.println(arr2[i]);
    }
}
// Time: O(n) + O(n) = O(n) ❌ WRONG!

// CORRECT Analysis
// Let n = arr1.length, m = arr2.length
// Time: O(n) + O(m) = O(n + m) ✓
```

---

### Rule 4: Amortized Analysis

Sometimes an operation is expensive occasionally but cheap most of the time.

```java
// ArrayList.add() example
List<Integer> list = new ArrayList<>();

for (int i = 0; i < n; i++) {
    list.add(i);  // Usually O(1), occasionally O(n) when resizing
}

// Most adds: O(1)
// Occasional resize: O(n) (happens log n times)
// Amortized: O(1) per operation
// Total: O(n) for n additions ✓
```

---

### Rule 5: Recursive Complexity

Use **Master Theorem** or **Recursion Tree Method**.

**Master Theorem:**
For recurrence: T(n) = aT(n/b) + f(n)

```
Compare f(n) with n^(log_b(a)):

Case 1: f(n) = O(n^c) where c < log_b(a)
        → T(n) = Θ(n^(log_b(a)))

Case 2: f(n) = Θ(n^c) where c = log_b(a)
        → T(n) = Θ(n^c log n)

Case 3: f(n) = Ω(n^c) where c > log_b(a)
        → T(n) = Θ(f(n))
```

**Examples:**

```java
// Binary Search: T(n) = T(n/2) + O(1)
// a=1, b=2, f(n)=1
// log₂(1) = 0, n^0 = 1
// Case 2: T(n) = O(log n) ✓

// Merge Sort: T(n) = 2T(n/2) + O(n)
// a=2, b=2, f(n)=n
// log₂(2) = 1, n^1 = n
// Case 2: T(n) = O(n log n) ✓

// Binary Tree Traversal: T(n) = 2T(n/2) + O(1)
// a=2, b=2, f(n)=1
// log₂(2) = 1, n^1 = n > 1
// Case 1: T(n) = O(n) ✓
```

---

## Complexity Comparison

### Visual Growth Comparison

```
For n = 1,000:

O(1)        →              1 operation      ⚡
O(log n)    →             10 operations     ⚡
O(n)        →          1,000 operations     ⚡
O(n log n)  →         10,000 operations     ✓
O(n²)       →      1,000,000 operations     ⚠
O(n³)       →  1,000,000,000 operations     ❌
O(2^n)       →         10^301 operations    ❌
O(n!)       →         10^2567 operations    ❌
```

### Time to Complete (Hypothetical: 1 operation = 1 microsecond)

| n   | O(log n) | O(n) | O(n log n) | O(n²) | O(2^n) | O(n!) |
|-----|----------|------|------------|-------|--------|-------|
| 10  | 0.003 ms | 0.01 ms | 0.03 ms | 0.1 ms | 1 ms | 3.6 ms |
| 20  | 0.004 ms | 0.02 ms | 0.09 ms | 0.4 ms | 1 sec | 77 years |
| 30  | 0.005 ms | 0.03 ms | 0.15 ms | 0.9 ms | 18 min | 10^25 years |
| 100 | 0.007 ms | 0.1 ms | 0.7 ms | 10 ms | 10^23 years | ∞ |

---

## Common Patterns and Their Complexities

### Pattern 1: Single Loop

```java
for (int i = 0; i < n; i++) {
    // O(1) operations
}
// Complexity: O(n)
```

---

### Pattern 2: Nested Loops (Same Size)

```java
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        // O(1) operations
    }
}
// Complexity: O(n²)
```

---

### Pattern 3: Nested Loops (Dependent)

```java
for (int i = 0; i < n; i++) {
    for (int j = i; j < n; j++) {
        // O(1) operations
    }
}
// Iterations: n + (n-1) + (n-2) + ... + 1 = n(n+1)/2
// Complexity: O(n²)
```

---

### Pattern 4: Nested Loops (Logarithmic)

```java
for (int i = 0; i < n; i++) {
    for (int j = 1; j < n; j *= 2) {
        // O(1) operations
    }
}
// Outer: O(n), Inner: O(log n)
// Complexity: O(n log n)
```

---

### Pattern 5: Halving/Doubling

```java
// Halving
for (int i = n; i > 0; i /= 2) {
    // O(1) operations
}
// Complexity: O(log n)

// Doubling
for (int i = 1; i < n; i *= 2) {
    // O(1) operations
}
// Complexity: O(log n)
```

---

### Pattern 6: Square Root Loop

```java
for (int i = 0; i * i < n; i++) {
    // O(1) operations
}
// Iterations: √n
// Complexity: O(√n)
```

---

### Pattern 7: Logarithmic Nested

```java
for (int i = 1; i < n; i *= 2) {
    for (int j = 1; j < n; j *= 2) {
        // O(1) operations
    }
}
// Complexity: O(log n × log n) = O(log² n)
```

---

### Pattern 8: String/Array Operations

```java
// String concatenation in loop
String result = "";
for (int i = 0; i < n; i++) {
    result += "a";  // O(i) operation (creates new string)
}
// Complexity: O(n²)

// StringBuilder (efficient)
StringBuilder sb = new StringBuilder();
for (int i = 0; i < n; i++) {
    sb.append("a");  // O(1) amortized
}
// Complexity: O(n)
```

---

### Pattern 9: Subset Generation

```java
// Generate all subsets (power set)
void generateSubsets(int[] arr, int index, List<Integer> current) {
    if (index == arr.length) {
        // process subset
        return;
    }
    
    generateSubsets(arr, index + 1, current);           // exclude
    current.add(arr[index]);
    generateSubsets(arr, index + 1, current);           // include
    current.remove(current.size() - 1);
}
// Complexity: O(2^n)
```

---

### Pattern 10: Permutation Generation

```java
void permute(int[] arr, int start) {
    if (start == arr.length - 1) {
        // process permutation
        return;
    }
    
    for (int i = start; i < arr.length; i++) {
        swap(arr, start, i);
        permute(arr, start + 1);
        swap(arr, start, i);
    }
}
// Complexity: O(n!)
```

---

## Practice Problems

### Beginner Level

**Problem 1:** What is the time complexity?
```java
public static void mystery1(int n) {
    int sum = 0;
    for (int i = 0; i < n; i++) {
        sum += i;
    }
    System.out.println(sum);
}
```
<details>
<summary>Answer</summary>
**O(n)** - Single loop with n iterations
</details>

---

**Problem 2:** What is the time complexity?
```java
public static void mystery2(int n) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            System.out.println(i + j);
        }
    }
}
```
<details>
<summary>Answer</summary>
**O(n²)** - Nested loops, both running n times
</details>

---

**Problem 3:** What is the time complexity?
```java
public static void mystery3(int n) {
    for (int i = 1; i < n; i *= 2) {
        System.out.println(i);
    }
}
```
<details>
<summary>Answer</summary>
**O(log n)** - i doubles each iteration
</details>

---

### Intermediate Level

**Problem 4:** What is the time complexity?
```java
public static void mystery4(int n) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < i; j++) {
            System.out.println(i + j);
        }
    }
}
```
<details>
<summary>Answer</summary>
**O(n²)** - Inner loop runs 0+1+2+...+(n-1) = n(n-1)/2 times
</details>

---

**Problem 5:** What is the time complexity?
```java
public static void mystery5(int n) {
    int i = 1;
    while (i < n) {
        System.out.println(i);
        i = i * 2;
    }
    
    for (int j = 0; j < n; j++) {
        System.out.println(j);
    }
}
```
<details>
<summary>Answer</summary>
**O(n)** - First loop O(log n), second O(n). Keep dominant: O(n)
</details>

---

**Problem 6:** What is the time complexity?
```java
public static int mystery6(int n) {
    if (n <= 1) return n;
    return mystery6(n - 1) + mystery6(n - 2);
}
```
<details>
<summary>Answer</summary>
**O(2^n)** - Fibonacci recursion, exponential tree
</details>

---

### Advanced Level

**Problem 7:** What is the time complexity?
```java
public static void mystery7(int n) {
    for (int i = 1; i < n; i *= 2) {
        for (int j = 0; j < n; j++) {
            System.out.println(i + j);
        }
    }
}
```
<details>
<summary>Answer</summary>
**O(n log n)** - Outer loop O(log n), inner O(n)
</details>

---

**Problem 8:** What is the time complexity?
```java
public static void mystery8(int n) {
    for (int i = 0; i < n; i++) {
        for (int j = i; j < n; j++) {
            for (int k = j; k < n; k++) {
                System.out.println(i + j + k);
            }
        }
    }
}
```
<details>
<summary>Answer</summary>
**O(n³)** - Three nested dependent loops
</details>

---

**Problem 9:** What is the space complexity?
```java
public static void mystery9(int n) {
    int[] arr = new int[n];
    for (int i = 0; i < n; i++) {
        arr[i] = i * i;
    }
}
```
<details>
<summary>Answer</summary>
**O(n)** - Array of size n
</details>

---

**Problem 10:** What is the time complexity?
```java
public static void mystery10(String str) {
    int n = str.length();
    for (int i = 0; i < n; i++) {
        for (int j = i; j < n; j++) {
            String sub = str.substring(i, j + 1);
            System.out.println(sub);
        }
    }
}
```
<details>
<summary>Answer</summary>
**O(n³)** - Two nested loops O(n²), substring operation O(n)
</details>

---

## Real-World Examples

### Example 1: Social Media Feed

```java
// Load user feed
public List<Post> getUserFeed(User user) {
    List<Post> feed = new ArrayList<>();
    
    // Get user's friends - O(F) where F = number of friends
    List<User> friends = user.getFriends();
    
    // For each friend, get their recent posts
    for (User friend : friends) {              // O(F)
        List<Post> posts = friend.getRecentPosts(10);  // O(1) - limited to 10
        feed.addAll(posts);                    // O(1) - at most 10 posts
    }
    
    // Sort by timestamp
    Collections.sort(feed, (a, b) -> 
        b.getTimestamp().compareTo(a.getTimestamp()));  // O(N log N)
    
    return feed.subList(0, Math.min(50, feed.size()));  // O(1)
}

// Time Complexity: O(F + N log N) where N = F × 10
// If F = 500 friends: O(5000 log 5000) ≈ O(5000 × 12) = O(60,000)
// Very fast! ✓
```

---

### Example 2: E-commerce Product Search

```java
// Naive approach - O(n)
public List<Product> searchProducts(String query, List<Product> products) {
    List<Product> results = new ArrayList<>();
    
    for (Product product : products) {          // O(n)
        if (product.getName().contains(query)) {
            results.add(product);
        }
    }
    
    return results;
}
// For 1 million products: checks all 1 million!

// Optimized with index - O(k) where k = results
public List<Product> searchProductsOptimized(String query) {
    // Use inverted index (HashMap)
    // Pre-built: word → list of products
    return searchIndex.get(query);  // O(1) lookup + O(k) to return results
}
// Much faster! Only returns matching products
```

---

### Example 3: File System Search

```java
// DFS traversal of directory tree
public void searchFiles(File directory, String target) {
    if (directory.isDirectory()) {
        File[] files = directory.listFiles();  // O(1)
        
        for (File file : files) {              // O(branching factor)
            if (file.isDirectory()) {
                searchFiles(file, target);      // Recursive call
            } else if (file.getName().equals(target)) {
                System.out.println("Found: " + file.getPath());
            }
        }
    }
}

// Time: O(n) where n = total files and directories
// Space: O(h) where h = height of directory tree (call stack)
```

---

### Example 4: Autocomplete Suggestions

```java
// Using Trie data structure
class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEndOfWord = false;
}

public List<String> autocomplete(String prefix) {
    TrieNode node = root;
    
    // Navigate to prefix - O(m) where m = prefix length
    for (char c : prefix.toCharArray()) {
        if (!node.children.containsKey(c)) {
            return new ArrayList<>();
        }
        node = node.children.get(c);
    }
    
    // Collect all words with this prefix - O(k) where k = results
    List<String> results = new ArrayList<>();
    collectWords(node, prefix, results);
    
    return results;
}

// Total: O(m + k) - Very efficient!
```

---

## Common Mistakes

### Mistake 1: Ignoring Hidden Costs

```java
// LOOKS like O(n), but is it?
public void processStrings(List<String> strings) {
    for (String str : strings) {              // O(n)
        if (str.length() > 100) {             // O(1)
            String reversed = reverse(str);    // O(m) where m = string length!
            System.out.println(reversed);
        }
    }
}

// Actual complexity: O(n × m) where m = average string length
// NOT O(n)!
```

---

### Mistake 2: Assuming All Operations are O(1)

```java
List<Integer> list = new ArrayList<>();

// ArrayList operations:
list.add(item);           // O(1) amortized
list.get(index);          // O(1)
list.remove(index);       // O(n) - shifts elements!
list.contains(item);      // O(n) - linear search!

// LinkedList operations:
linkedList.add(item);     // O(1)
linkedList.get(index);    // O(n) - must traverse!
linkedList.remove(index); // O(n) - must traverse!
```

---

### Mistake 3: Forgetting About Sorting

```java
// This is NOT O(n)!
public void processData(int[] arr) {
    Arrays.sort(arr);     // O(n log n) ← Don't forget this!
    
    for (int num : arr) { // O(n)
        System.out.println(num);
    }
}

// Total: O(n log n), not O(n)
```

---

### Mistake 4: Recursive Space Complexity

```java
// People often say: "Space is O(1)" ❌
public int sum(int n) {
    if (n == 0) return 0;
    return n + sum(n - 1);
}

// Correct: Space is O(n) due to call stack! ✓
// Each recursive call adds a frame to the stack
```

---

### Mistake 5: Nested Data Structures

```java
// Creating 2D matrix
int[][] matrix = new int[n][n];

// This is NOT O(n)!
// Space: O(n²) for n×n matrix
```

---

## Tips and Tricks

### Tip 1: Identify Loop Structure First

```
Single loop → O(n)
Nested same-size loops → O(n²)
Nested different-size → O(n × m)
Dividing by 2 → O(log n)
Recursive branching → Consider recursion tree
```

---

### Tip 2: Look for Early Exits

```java
// Best case can differ from worst case
public boolean contains(int[] arr, int target) {
    for (int num : arr) {
        if (num == target) return true;  // Early exit!
    }
    return false;
}

// Best: O(1) - found immediately
// Worst: O(n) - not found or at end
```

---

### Tip 3: Amortized Analysis Matters

```java
// ArrayList doubling strategy
// Most adds: O(1)
// Resize adds: O(n) but rare
// Amortized: O(1)

// Total for n adds: O(n), not O(n²)
```

---

### Tip 4: Master Common Patterns

Memorize these:
- Binary Search: O(log n)
- Merge Sort: O(n log n)
- DFS/BFS: O(V + E)
- Dijkstra: O((V + E) log V)
- Dynamic Programming: Often O(n²) or O(n)

---

### Tip 5: Use Big O Cheat Sheet

| Data Structure | Access | Search | Insert | Delete | Space |
|---------------|--------|--------|--------|--------|-------|
| Array         | O(1)   | O(n)   | O(n)   | O(n)   | O(n)  |
| ArrayList     | O(1)   | O(n)   | O(1)*  | O(n)   | O(n)  |
| LinkedList    | O(n)   | O(n)   | O(1)   | O(1)   | O(n)  |
| Stack         | O(n)   | O(n)   | O(1)   | O(1)   | O(n)  |
| Queue         | O(n)   | O(n)   | O(1)   | O(1)   | O(n)  |
| HashSet       | N/A    | O(1)*  | O(1)*  | O(1)*  | O(n)  |
| HashMap       | O(1)*  | O(1)*  | O(1)*  | O(1)*  | O(n)  |
| TreeSet       | N/A    | O(log n)| O(log n)| O(log n)| O(n)  |
| TreeMap       | O(log n)| O(log n)| O(log n)| O(log n)| O(n)  |
| Binary Heap   | O(1)   | O(n)   | O(log n)| O(log n)| O(n)  |
| BST (balanced)| O(log n)| O(log n)| O(log n)| O(log n)| O(n)  |

*Amortized or average case

---

## Summary

### Key Takeaways

1. **Big O describes growth rate**, not exact time
2. **Drop constants and non-dominant terms**
3. **Different inputs need different variables**
4. **Consider both time AND space complexity**
5. **Worst case is default**, but know best/average too
6. **Practice analyzing code snippets**
7. **Know common patterns and their complexities**

---

### Complexity Hierarchy (Best to Worst)

```
O(1) < O(log n) < O(√n) < O(n) < O(n log n) < O(n²) < O(n³) < O(2^n) < O(n!)

Constant < Logarithmic < Root < Linear < Linearithmic < Quadratic < Cubic < Exponential < Factorial
```

---

### When to Use What?

- **O(1)** - Hash tables, array access, stack operations
- **O(log n)** - Binary search, balanced tree operations
- **O(n)** - Linear search, array traversal, counting
- **O(n log n)** - Efficient sorting (merge, heap, quick)
- **O(n²)** - Simple sorting (bubble, insertion), nested comparisons
- **O(2^n)** - Recursive algorithms (subsets, combinations)
- **O(n!)** - Permutations, brute force solutions

---

### Quick Reference Card

```
┌─────────────────────────────────────────────────┐
│  COMPLEXITY QUICK REFERENCE                     │
├─────────────────────────────────────────────────┤
│  Single loop:              O(n)                 │
│  Nested loops (same):      O(n²)                │
│  Nested loops (diff):      O(n × m)             │
│  Dividing by constant:     O(log n)             │
│  Binary search:            O(log n)             │
│  Sorting (efficient):      O(n log n)           │
│  Sorting (simple):         O(n²)                │
│  DFS/BFS on graph:         O(V + E)             │
│  Recursion depth:          O(depth)             │
│  String concatenation:     O(n²)                │
│  StringBuilder append:     O(n)                 │
│  HashMap operations:       O(1) average         │
│  TreeMap operations:       O(log n)             │
└─────────────────────────────────────────────────┘
```

---
