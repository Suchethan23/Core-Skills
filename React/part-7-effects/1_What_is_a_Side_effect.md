# PART 7 – Effects & Side Effects

Effects are how you synchronize your React components with external systems. Understanding effects is crucial for data fetching, subscriptions, and any interaction with the world outside React.

---

## 7.1 What is a Side Effect

### Pure Functions vs Side Effects

**Pure function:**
- Same input → Same output
- No external effects
- No surprises

```javascript
// Pure function
function add(a, b) {
  return a + b;
}

add(2, 3)  // Always returns 5
add(2, 3)  // Always returns 5
```

**Function with side effects:**
- Does something beyond returning a value
- Affects the outside world
- May have unpredictable results

```javascript
// Side effect function
function addAndLog(a, b) {
  console.log('Adding:', a, b);  // Side effect!
  return a + b;
}
```

---

### What Are Side Effects in React?

**Side effects are operations that interact with the outside world:**

- 🌐 **Fetching data** from an API
- ⏰ **Setting up timers** (setTimeout, setInterval)
- 📡 **Subscribing to events** (WebSocket, addEventListener)
- 📝 **Updating the document** (changing document.title)
- 💾 **Reading/writing to localStorage**
- 📊 **Logging to console** (console.log)
- 🎨 **Directly manipulating DOM** (beyond what React does)
- 📤 **Sending analytics** events

**Anything that "reaches out" beyond your component is a side effect.**

---

### Rendering vs Effects

**This is a critical distinction in React.**

**Rendering (pure):**
- Component function runs
- Calculates JSX from props and state
- No side effects allowed
- Can be called multiple times
- Should be predictable

```javascript
function Component({ name }) {
  // ✅ Pure rendering
  const greeting = `Hello, ${name}`;
  return <h1>{greeting}</h1>;
}
```

**Effects (impure):**
- Run *after* rendering
- Can have side effects
- Synchronize with external systems
- Run at specific times (mount, update, unmount)

```javascript
function Component({ userId }) {
  const [user, setUser] = useState(null);
  
  // ✅ Effect - runs after render
  useEffect(() => {
    fetch(`/api/users/${userId}`)
      .then(res => res.json())
      .then(data => setUser(data));
  }, [userId]);
  
  return <div>{user?.name}</div>;
}
```

---

### Why Keep Rendering Pure?

**React's rendering can be:**
- Called multiple times before committing to DOM
- Paused and resumed
- Aborted and restarted

**If you put side effects in render:**

```javascript
// ❌ BAD - Side effect during render
function BadComponent() {
  console.log('Rendering...');  // Called multiple times!
  fetch('/api/data');            // Multiple unnecessary requests!
  document.title = 'Page';       // Set multiple times!
  
  return <div>Content</div>;
}
```

**Problems:**
- API called multiple times unnecessarily
- Console filled with logs
- Unpredictable behavior
- Performance issues

---

### Why Effects Exist

**Effects solve the problem of "when to run side effects safely."**

Without effects, when would you:
- Fetch data after component appears?
- Subscribe to WebSocket after mount?
- Update document title when state changes?
- Clean up timer before component unmounts?

**Effects give you precise control:**

```javascript
useEffect(() => {
  // This runs at the RIGHT time
  // After DOM updates
  // When dependencies change
  
  return () => {
    // This runs at the RIGHT time
    // Before next effect
    // Before unmount
  };
}, [dependencies]);
```

---

### The React Lifecycle with Effects

```
Component mounts
  ↓
Render phase (pure, no side effects)
  ↓
Commit phase (React updates DOM)
  ↓
Effects run ← Your side effects happen here
  ↓
User interacts → State changes
  ↓
Render phase (pure, no side effects)
  ↓
Commit phase (React updates DOM)
  ↓
Cleanup functions run (if dependencies changed)
  ↓
Effects run again ← Your side effects happen here
  ↓
Component unmounts
  ↓
Cleanup functions run ← Your cleanup happens here
```

---

