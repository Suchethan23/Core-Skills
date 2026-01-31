
## 4.4 Derived State (Anti-patterns)

### What is Derived State?

**Derived state** is state that can be calculated from other state or props.

**You should NOT store it in state.**

---

### Anti-pattern: Storing Derived Values

**❌ BAD - Storing fullName in state:**
```javascript
function UserProfile({ firstName, lastName }) {
  const [fullName, setFullName] = useState(`${firstName} ${lastName}`);
  
  // Problem 1: fullName doesn't update when props change
  // Problem 2: Unnecessary state
  
  return <div>{fullName}</div>;
}
```

**✅ GOOD - Calculate during render:**
```javascript
function UserProfile({ firstName, lastName }) {
  const fullName = `${firstName} ${lastName}`;  // Derived!
  
  return <div>{fullName}</div>;
}
```

---

### When NOT to Use State

**Don't use state if you can calculate the value from:**
- Props
- Other state
- Constants

**Examples:**

**❌ BAD:**
```javascript
function ShoppingCart({ items }) {
  const [total, setTotal] = useState(0);
  
  // Manually update total when items change
  useEffect(() => {
    const sum = items.reduce((acc, item) => acc + item.price, 0);
    setTotal(sum);
  }, [items]);
  
  return <div>Total: ${total}</div>;
}
```

**✅ GOOD:**
```javascript
function ShoppingCart({ items }) {
  const total = items.reduce((acc, item) => acc + item.price, 0);
  
  return <div>Total: ${total}</div>;
}
```

---

### More Examples

**❌ BAD - Storing filtered list:**
```javascript
function TodoList({ todos, filter }) {
  const [filteredTodos, setFilteredTodos] = useState([]);
  
  useEffect(() => {
    const filtered = todos.filter(todo => {
      if (filter === 'active') return !todo.completed;
      if (filter === 'completed') return todo.completed;
      return true;
    });
    setFilteredTodos(filtered);
  }, [todos, filter]);
  
  return (
    <ul>
      {filteredTodos.map(todo => <li key={todo.id}>{todo.text}</li>)}
    </ul>
  );
}
```

**✅ GOOD - Calculate during render:**
```javascript
function TodoList({ todos, filter }) {
  const filteredTodos = todos.filter(todo => {
    if (filter === 'active') return !todo.completed;
    if (filter === 'completed') return todo.completed;
    return true;
  });
  
  return (
    <ul>
      {filteredTodos.map(todo => <li key={todo.id}>{todo.text}</li>)}
    </ul>
  );
}
```

---

### Why Derived State is Bad

**Problems with derived state:**

**1. Synchronization bugs**
```javascript
// State and props can get out of sync
const [fullName, setFullName] = useState(`${firstName} ${lastName}`);
// If firstName prop changes, fullName state is still old value
```

**2. Extra complexity**
```javascript
// Need useEffect to keep in sync
useEffect(() => {
  setFullName(`${firstName} ${lastName}`);
}, [firstName, lastName]);
// Why not just calculate it?
```

**3. More code**
```javascript
// More state = more code = more bugs
```

**4. Performance**
```javascript
// Extra re-renders from setState calls
```

---

### Calculated Values

**Just calculate values during render:**

```javascript
function Component({ items, taxRate }) {
  // All calculated, not state
  const subtotal = items.reduce((sum, item) => sum + item.price, 0);
  const tax = subtotal * taxRate;
  const total = subtotal + tax;
  
  return (
    <div>
      <p>Subtotal: ${subtotal}</p>
      <p>Tax: ${tax}</p>
      <p>Total: ${total}</p>
    </div>
  );
}
```

**Benefits:**
- Always correct (can't get out of sync)
- Less code
- No useEffect needed
- Simpler to understand

---

### When Calculation is Expensive: useMemo

**If calculation is expensive, use `useMemo`:**

```javascript
function Component({ items }) {
  // Expensive calculation
  const total = useMemo(() => {
    console.log('Calculating total...');
    return items.reduce((sum, item) => {
      // Imagine complex calculation here
      return sum + item.price * item.quantity * item.taxRate;
    }, 0);
  }, [items]);  // Only recalculate when items change
  
  return <div>Total: ${total}</div>;
}
```

**But don't optimize prematurely!** 
- Most calculations are fast
- Start with simple calculation
- Use `useMemo` only if performance is an issue

---

### Rule of Thumb

**Ask yourself:**

**"Can I calculate this from props or other state?"**
- **YES** → Don't use state, calculate it
- **NO** → Use state

**Examples:**

```javascript
// ✅ State - can't calculate
const [username, setUsername] = useState('');  // User input

// ❌ Don't need state - can calculate
const isValid = username.length >= 3;  // Calculated

// ✅ State - can't calculate
const [items, setItems] = useState([]);  // User's shopping cart

// ❌ Don't need state - can calculate
const itemCount = items.length;  // Calculated

// ✅ State - can't calculate
const [firstName, setFirstName] = useState('');  // User input
const [lastName, setLastName] = useState('');   // User input

// ❌ Don't need state - can calculate
const fullName = `${firstName} ${lastName}`;  // Calculated
```

---

## Summary: Part 4

### Key Concepts

**1. What State Actually Is**
- State is a component's memory
- Persists between renders
- Each render is a snapshot with fixed values
- State lives outside the component function (in React's memory)
- State updates trigger re-renders

**2. useState Deep Dive**
- Returns `[value, setValue]`
- Initial value only used on first render
- Use lazy initialization for expensive computations
- Setter has two forms: direct value or updater function
- Use updater function when new state depends on old state
- React batches multiple state updates
- Must create new objects/arrays, not mutate

**3. State Update Pitfalls**
- **Stale closures** - Functions close over old state values
  - Solution: Use updater function or add to dependencies
- **Multiple updates** - Multiple sets with same value
  - Solution: Use updater function
- **Async updates** - State updates don't happen immediately
  - Solution: Use updater function
- **Event handler closures** - setTimeout/async see stale values
  - Solution: Use refs or updater functions

**4. Derived State Anti-patterns**
- Don't store what you can calculate
- Calculate during render instead
- Use `useMemo` only for expensive calculations
- Derived state causes synchronization bugs
- Rule: If it can be calculated from props/state, calculate it

---

### Mental Models

**State as Memory:**
```
Regular variable: Reset every render
State: Persists across renders
```

**Snapshot Concept:**
```
Each render = Photo
Props/state in that render = Fixed values
Event handlers see snapshot values
```

**State Updates:**
```
setState called
  ↓
Re-render scheduled
  ↓
Component function runs
  ↓
New JSX returned
  ↓
DOM updated
```

**Updater Function:**
```
setCount(count + 1)        // Uses snapshot value
setCount(prev => prev + 1) // Uses latest value
```

**Derived State:**
```
Can calculate it? → Don't use state
User controls it? → Use state
```

---

### Common Patterns

**1. Form input:**
```javascript
const [value, setValue] = useState('');
<input value={value} onChange={(e) => setValue(e.target.value)} />
```

**2. Toggle:**
```javascript
const [isOpen, setIsOpen] = useState(false);
<button onClick={() => setIsOpen(!isOpen)}>Toggle</button>
```

**3. Counter:**
```javascript
const [count, setCount] = useState(0);
<button onClick={() => setCount(prev => prev + 1)}>+</button>
```

**4. Array operations:**
```javascript
// Add
setItems(prev => [...prev, newItem])

// Remove
setItems(prev => prev.filter(item => item.id !== id))

// Update
setItems(prev => prev.map(item => 
  item.id === id ? { ...item, completed: true } : item
))
```

**5. Object updates:**
```javascript
setUser(prev => ({ ...prev, age: 26 }))
```

---

### Critical Rules

**✅ DO:**
- Use state for values that change over time
- Use updater function when new state depends on old state
- Create new objects/arrays when updating
- Calculate derived values during render
- Use `useMemo` only when calculation is proven to be slow

**❌ DON'T:**
- Mutate state directly
- Expect state to update immediately after setState
- Store derived values in state
- Use state for values that can be calculated
- Optimize prematurely with useMemo everywhere

---

### Debugging State Issues

**Problem: State not updating**
- Check if you're mutating instead of creating new object/array
- Check if you're reading state immediately after setState (snapshot!)
- Verify setter function is actually being called

**Problem: State has stale value**
- Use updater function instead of direct value
- Check closure dependencies in useEffect/callbacks
- Consider using useRef for values needed in async operations

**Problem: Too many re-renders**
- Check if you're calling setState during render
- Verify useEffect dependencies are correct
- Look for infinite loops (setState → effect → setState)

**Problem: State updates lost**
- Check if you're batching correctly
- Verify you're not using wrong snapshot value
- Use updater function for multiple updates

---

## 4.4 Derived State (Anti-patterns)

### What is Derived State?

**Derived state** is state that can be calculated from other state or props.

**You should NOT store it in state.**

---

### Anti-pattern: Storing Derived Values

**❌ BAD - Storing fullName in state:**
```javascript
function UserProfile({ firstName, lastName }) {
  const [fullName, setFullName] = useState(`${firstName} ${lastName}`);
  
  // Problem 1: fullName doesn't update when props change
  // Problem 2: Unnecessary state
  
  return <div>{fullName}</div>;
}
```

**✅ GOOD - Calculate during render:**
```javascript
function UserProfile({ firstName, lastName }) {
  const fullName = `${firstName} ${lastName}`;  // Derived!
  
  return <div>{fullName}</div>;
}
```

---

### When NOT to Use State

**Don't use state if you can calculate the value from:**
- Props
- Other state
- Constants

**Examples:**

**❌ BAD:**
```javascript
function ShoppingCart({ items }) {
  const [total, setTotal] = useState(0);
  
  // Manually update total when items change
  useEffect(() => {
    const sum = items.reduce((acc, item) => acc + item.price, 0);
    setTotal(sum);
  }, [items]);
  
  return <div>Total: ${total}</div>;
}
```

**✅ GOOD:**
```javascript
function ShoppingCart({ items }) {
  const total = items.reduce((acc, item) => acc + item.price, 0);
  
  return <div>Total: ${total}</div>;
}
```

---

### More Examples

**❌ BAD - Storing filtered list:**
```javascript
function TodoList({ todos, filter }) {
  const [filteredTodos, setFilteredTodos] = useState([]);
  
  useEffect(() => {
    const filtered = todos.filter(todo => {
      if (filter === 'active') return !todo.completed;
      if (filter === 'completed') return todo.completed;
      return true;
    });
    setFilteredTodos(filtered);
  }, [todos, filter]);
  
  return (
    <ul>
      {filteredTodos.map(todo => <li key={todo.id}>{todo.text}</li>)}
    </ul>
  );
}
```

**✅ GOOD - Calculate during render:**
```javascript
function TodoList({ todos, filter }) {
  const filteredTodos = todos.filter(todo => {
    if (filter === 'active') return !todo.completed;
    if (filter === 'completed') return todo.completed;
    return true;
  });
  
  return (
    <ul>
      {filteredTodos.map(todo => <li key={todo.id}>{todo.text}</li>)}
    </ul>
  );
}
```

---

### Why Derived State is Bad

**Problems with derived state:**

**1. Synchronization bugs**
```javascript
// State and props can get out of sync
const [fullName, setFullName] = useState(`${firstName} ${lastName}`);
// If firstName prop changes, fullName state is still old value
```

**2. Extra complexity**
```javascript
// Need useEffect to keep in sync
useEffect(() => {
  setFullName(`${firstName} ${lastName}`);
}, [firstName, lastName]);
// Why not just calculate it?
```

**3. More code**
```javascript
// More state = more code = more bugs
```

**4. Performance**
```javascript
// Extra re-renders from setState calls
```

---

### Calculated Values

**Just calculate values during render:**

```javascript
function Component({ items, taxRate }) {
  // All calculated, not state
  const subtotal = items.reduce((sum, item) => sum + item.price, 0);
  const tax = subtotal * taxRate;
  const total = subtotal + tax;
  
  return (
    <div>
      <p>Subtotal: ${subtotal}</p>
      <p>Tax: ${tax}</p>
      <p>Total: ${total}</p>
    </div>
  );
}
```

**Benefits:**
- Always correct (can't get out of sync)
- Less code
- No useEffect needed
- Simpler to understand

---

### When Calculation is Expensive: useMemo

**If calculation is expensive, use `useMemo`:**

```javascript
function Component({ items }) {
  // Expensive calculation
  const total = useMemo(() => {
    console.log('Calculating total...');
    return items.reduce((sum, item) => {
      // Imagine complex calculation here
      return sum + item.price * item.quantity * item.taxRate;
    }, 0);
  }, [items]);  // Only recalculate when items change
  
  return <div>Total: ${total}</div>;
}
```

**But don't optimize prematurely!** 
- Most calculations are fast
- Start with simple calculation
- Use `useMemo` only if performance is an issue

---

### Rule of Thumb

**Ask yourself:**

**"Can I calculate this from props or other state?"**
- **YES** → Don't use state, calculate it
- **NO** → Use state

**Examples:**

```javascript
// ✅ State - can't calculate
const [username, setUsername] = useState('');  // User input

// ❌ Don't need state - can calculate
const isValid = username.length >= 3;  // Calculated

// ✅ State - can't calculate
const [items, setItems] = useState([]);  // User's shopping cart

// ❌ Don't need state - can calculate
const itemCount = items.length;  // Calculated

// ✅ State - can't calculate
const [firstName, setFirstName] = useState('');  // User input
const [lastName, setLastName] = useState('');   // User input

// ❌ Don't need state - can calculate
const fullName = `${firstName} ${lastName}`;  // Calculated
```

---

## Summary: Part 4

### Key Concepts

**1. What State Actually Is**
- State is a component's memory
- Persists between renders
- Each render is a snapshot with fixed values
- State lives outside the component function (in React's memory)
- State updates trigger re-renders

**2. useState Deep Dive**
- Returns `[value, setValue]`
- Initial value only used on first render
- Use lazy initialization for expensive computations
- Setter has two forms: direct value or updater function
- Use updater function when new state depends on old state
- React batches multiple state updates
- Must create new objects/arrays, not mutate

**3. State Update Pitfalls**
- **Stale closures** - Functions close over old state values
  - Solution: Use updater function or add to dependencies
- **Multiple updates** - Multiple sets with same value
  - Solution: Use updater function
- **Async updates** - State updates don't happen immediately
  - Solution: Use updater function
- **Event handler closures** - setTimeout/async see stale values
  - Solution: Use refs or updater functions

**4. Derived State Anti-patterns**
- Don't store what you can calculate
- Calculate during render instead
- Use `useMemo` only for expensive calculations
- Derived state causes synchronization bugs
- Rule: If it can be calculated from props/state, calculate it

---

### Mental Models

**State as Memory:**
```
Regular variable: Reset every render
State: Persists across renders
```

**Snapshot Concept:**
```
Each render = Photo
Props/state in that render = Fixed values
Event handlers see snapshot values
```

**State Updates:**
```
setState called
  ↓
Re-render scheduled
  ↓
Component function runs
  ↓
New JSX returned
  ↓
DOM updated
```

**Updater Function:**
```
setCount(count + 1)        // Uses snapshot value
setCount(prev => prev + 1) // Uses latest value
```

**Derived State:**
```
Can calculate it? → Don't use state
User controls it? → Use state
```

---

### Common Patterns

**1. Form input:**
```javascript
const [value, setValue] = useState('');
<input value={value} onChange={(e) => setValue(e.target.value)} />
```

**2. Toggle:**
```javascript
const [isOpen, setIsOpen] = useState(false);
<button onClick={() => setIsOpen(!isOpen)}>Toggle</button>
```

**3. Counter:**
```javascript
const [count, setCount] = useState(0);
<button onClick={() => setCount(prev => prev + 1)}>+</button>
```

**4. Array operations:**
```javascript
// Add
setItems(prev => [...prev, newItem])

// Remove
setItems(prev => prev.filter(item => item.id !== id))

// Update
setItems(prev => prev.map(item => 
  item.id === id ? { ...item, completed: true } : item
))
```

**5. Object updates:**
```javascript
setUser(prev => ({ ...prev, age: 26 }))
```

---

### Critical Rules

**✅ DO:**
- Use state for values that change over time
- Use updater function when new state depends on old state
- Create new objects/arrays when updating
- Calculate derived values during render
- Use `useMemo` only when calculation is proven to be slow

**❌ DON'T:**
- Mutate state directly
- Expect state to update immediately after setState
- Store derived values in state
- Use state for values that can be calculated
- Optimize prematurely with useMemo everywhere

---

### Debugging State Issues

**Problem: State not updating**
- Check if you're mutating instead of creating new object/array
- Check if you're reading state immediately after setState (snapshot!)
- Verify setter function is actually being called

**Problem: State has stale value**
- Use updater function instead of direct value
- Check closure dependencies in useEffect/callbacks
- Consider using useRef for values needed in async operations

**Problem: Too many re-renders**
- Check if you're calling setState during render
- Verify useEffect dependencies are correct
- Look for infinite loops (setState → effect → setState)

**Problem: State updates lost**
- Check if you're batching correctly
- Verify you're not using wrong snapshot value
- Use updater function for multiple updates

---
