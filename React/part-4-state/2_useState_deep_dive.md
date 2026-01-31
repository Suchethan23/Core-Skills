
## 4.2 `useState` Deep Dive

### The Basics

```javascript
const [state, setState] = useState(initialValue);
```

**Returns an array with two elements:**
1. `state` - Current state value
2. `setState` - Function to update state

**Array destructuring lets you name them whatever you want:**
```javascript
const [count, setCount] = useState(0);
const [name, setName] = useState('Alice');
const [isOpen, setIsOpen] = useState(false);
const [items, setItems] = useState([]);
```

---

### Initialization

**The argument to `useState` is the initial value:**

```javascript
useState(0)           // Initial value: 0
useState('Alice')     // Initial value: 'Alice'
useState(false)       // Initial value: false
useState([])          // Initial value: empty array
useState({ name: 'Alice' })  // Initial value: object
```

**Important: Initial value is only used on the first render.**

```javascript
function Component() {
  const [count, setCount] = useState(0);
  
  // First render: count = 0 (from initial value)
  // Second render: count = 1 (from React's memory, initial value ignored)
  // Third render: count = 2 (from React's memory, initial value ignored)
}
```

---

### Lazy Initialization

If initial state requires expensive computation, use a function:

**❌ BAD - Computation runs on every render:**
```javascript
function Component() {
  const [data, setData] = useState(expensiveComputation());
  // expensiveComputation() runs on EVERY render
  // Even though result is only used on first render
}
```

**✅ GOOD - Computation runs only once:**
```javascript
function Component() {
  const [data, setData] = useState(() => expensiveComputation());
  // Function runs only on first render
}

// Example:
const [todos, setTodos] = useState(() => {
  const saved = localStorage.getItem('todos');
  return saved ? JSON.parse(saved) : [];
});
```

**The function form is called "lazy initialization".**

---

### Multiple State Variables

You can call `useState` multiple times:

```javascript
function Form() {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [age, setAge] = useState(0);
  const [isSubscribed, setIsSubscribed] = useState(false);
  
  return (
    <form>
      <input value={name} onChange={(e) => setName(e.target.value)} />
      <input value={email} onChange={(e) => setEmail(e.target.value)} />
      {/* ... */}
    </form>
  );
}
```

**Each state variable is independent.**

---

### Or Use an Object (When Related)

If state values are related, you can use an object:

```javascript
function Form() {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    age: 0
  });
  
  const updateField = (field, value) => {
    setFormData({
      ...formData,
      [field]: value
    });
  };
  
  return (
    <form>
      <input 
        value={formData.name} 
        onChange={(e) => updateField('name', e.target.value)} 
      />
      {/* ... */}
    </form>
  );
}
```

**Trade-off:** Simpler code, but updating requires spreading the object.

---

### Setter Function Behavior

**Two ways to update state:**

**1. Direct value (most common):**
```javascript
const [count, setCount] = useState(0);

setCount(5);           // Set to 5
setCount(count + 1);   // Set to count + 1
```

**2. Updater function (for complex updates):**
```javascript
const [count, setCount] = useState(0);

setCount(prevCount => prevCount + 1);  // Function receives previous value
```

---

### When to Use Updater Functions

**Use updater function when:**
- New state depends on previous state
- Making multiple updates
- Update happens asynchronously

**Example problem:**
```javascript
function Counter() {
  const [count, setCount] = useState(0);
  
  const incrementThree = () => {
    setCount(count + 1);  // count is 0, sets to 1
    setCount(count + 1);  // count is still 0, sets to 1
    setCount(count + 1);  // count is still 0, sets to 1
  };
  
  return <button onClick={incrementThree}>+3</button>;
}
```

**Result:** Count only increases by 1, not 3!

**Why?** All three `setCount` calls see `count = 0` (the snapshot value).

**Solution: Use updater function:**
```javascript
function Counter() {
  const [count, setCount] = useState(0);
  
  const incrementThree = () => {
    setCount(prev => prev + 1);  // prev = 0, sets to 1
    setCount(prev => prev + 1);  // prev = 1, sets to 2
    setCount(prev => prev + 1);  // prev = 2, sets to 3
  };
  
  return <button onClick={incrementThree}>+3</button>;
}
```

**Now it works!** Each update gets the result of the previous update.

---

### How Updater Functions Work

**When you use an updater function:**

```javascript
setCount(prev => prev + 1);
```

**React:**
1. Queues this update
2. When processing, calls your function with **latest state value**
3. Uses the returned value as the new state

**Visual:**
```
Initial: count = 0

setCount(prev => prev + 1)
  → React calls function with prev = 0
  → Function returns 1
  → Queued update: set count to 1

setCount(prev => prev + 1)
  → React calls function with prev = 1 (latest)
  → Function returns 2
  → Queued update: set count to 2

After all updates: count = 2
```

---

### Batching

**React batches multiple state updates for performance.**

**Example:**
```javascript
function Component() {
  const [count, setCount] = useState(0);
  const [flag, setFlag] = useState(false);
  
  const handleClick = () => {
    setCount(count + 1);  // Update 1
    setFlag(!flag);       // Update 2
    // React batches these into ONE re-render
  };
  
  return <button onClick={handleClick}>Click</button>;
}
```

**Without batching:** 2 state changes = 2 re-renders  
**With batching:** 2 state changes = 1 re-render  

**React automatically batches updates in:**
- Event handlers
- useEffect
- Timers (in React 18+)
- Async functions (in React 18+)

---

### Batching Example

```javascript
function Component() {
  const [count, setCount] = useState(0);
  
  console.log('Rendering, count:', count);
  
  const handleClick = () => {
    console.log('Before updates, count:', count);
    
    setCount(count + 1);
    setCount(count + 1);
    setCount(count + 1);
    
    console.log('After updates, count:', count);
  };
  
  return <button onClick={handleClick}>Click</button>;
}
```

**Console output when clicking:**
```
Before updates, count: 0
After updates, count: 0     ← Still 0! Snapshot concept
Rendering, count: 1         ← Only ONE re-render
```

**Key points:**
- All three `setCount` calls are batched
- Only one re-render happens
- Count goes from 0 → 1 (not 3, because all see count = 0)

---

### Replacing vs Updating Objects

**For objects/arrays, you must create new ones:**

**❌ WRONG - Mutating state:**
```javascript
const [user, setUser] = useState({ name: 'Alice', age: 25 });

const updateAge = () => {
  user.age = 26;  // MUTATING! React won't detect change
  setUser(user);  // Same reference, React thinks nothing changed
};
```

**✅ CORRECT - Creating new object:**
```javascript
const [user, setUser] = useState({ name: 'Alice', age: 25 });

const updateAge = () => {
  setUser({ ...user, age: 26 });  // New object with updated age
};
```

**For arrays:**

**❌ WRONG:**
```javascript
const [items, setItems] = useState([1, 2, 3]);

const addItem = () => {
  items.push(4);  // MUTATING!
  setItems(items);
};
```

**✅ CORRECT:**
```javascript
const [items, setItems] = useState([1, 2, 3]);

const addItem = () => {
  setItems([...items, 4]);  // New array
};

// Or:
const addItem = () => {
  setItems(prev => [...prev, 4]);
};
```

---

### State Updates Are Asynchronous

**State updates don't happen immediately.**

```javascript
function Component() {
  const [count, setCount] = useState(0);
  
  const handleClick = () => {
    console.log('Before:', count);  // 0
    
    setCount(5);
    
    console.log('After:', count);   // Still 0! Not 5!
    
    // State update is scheduled, not immediate
    // Re-render happens later
  };
  
  return <button onClick={handleClick}>Click</button>;
}
```

**Why asynchronous?**
- Performance optimization (batching)
- React can optimize when to re-render
- Prevents unnecessary renders

---
