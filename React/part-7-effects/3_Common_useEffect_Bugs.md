
## 7.3 Common `useEffect` Bugs

### Bug 1: Infinite Loops

**Problem: Effect causes re-render, which causes effect, which causes re-render...**

**Example 1: Missing dependency array**

```javascript
// ❌ INFINITE LOOP!
function Component() {
  const [count, setCount] = useState(0);
  
  useEffect(() => {
    setCount(count + 1);  // Updates state
  }); // No dependency array - runs after every render!
  
  return <div>{count}</div>;
}
```

**What happens:**
```
Render (count: 0)
  → Effect runs
  → setCount(1)
  → Render (count: 1)
  → Effect runs
  → setCount(2)
  → Render (count: 2)
  → ... INFINITE LOOP!
```

**Solution: Add dependency array**

```javascript
// ✅ FIXED
useEffect(() => {
  setCount(count + 1);
}, []); // Only runs once
```

---

**Example 2: Object/array dependency**

```javascript
// ❌ INFINITE LOOP!
function Component() {
  const [data, setData] = useState(null);
  const options = { method: 'GET' };  // New object every render!
  
  useEffect(() => {
    fetch('/api/data', options)
      .then(res => res.json())
      .then(data => setData(data));
  }, [options]); // options is different every time!
  
  return <div>{data}</div>;
}
```

**What happens:**
```
Render
  → options = new object {}
  → Effect sees options changed
  → Fetches data
  → setData updates state
  → Render
  → options = new object {} (different reference!)
  → Effect sees options changed
  → ... INFINITE LOOP!
```

**Solution: Move object outside or use useMemo**

```javascript
// ✅ FIXED - Option 1: Move outside
const options = { method: 'GET' };

function Component() {
  const [data, setData] = useState(null);
  
  useEffect(() => {
    fetch('/api/data', options)
      .then(res => res.json())
      .then(data => setData(data));
  }, [options]); // Same object every time
  
  return <div>{data}</div>;
}

// ✅ FIXED - Option 2: Use useMemo
function Component() {
  const [data, setData] = useState(null);
  
  const options = useMemo(() => ({ 
    method: 'GET' 
  }), []);
  
  useEffect(() => {
    fetch('/api/data', options)
      .then(res => res.json())
      .then(data => setData(data));
  }, [options]);
  
  return <div>{data}</div>;
}

// ✅ FIXED - Option 3: Don't depend on options
function Component() {
  const [data, setData] = useState(null);
  
  useEffect(() => {
    fetch('/api/data', { method: 'GET' })
      .then(res => res.json())
      .then(data => setData(data));
  }, []); // No dependencies
  
  return <div>{data}</div>;
}
```

---

**Example 3: State update causes dependency change**

```javascript
// ❌ INFINITE LOOP!
function Component() {
  const [count, setCount] = useState(0);
  const [data, setData] = useState(null);
  
  useEffect(() => {
    const newData = { count: count, timestamp: Date.now() };
    setData(newData);  // Updates state
  }, [data]); // Depends on data, which we're updating!
  
  return <div>{data?.count}</div>;
}
```

**Solution: Remove circular dependency**

```javascript
// ✅ FIXED
useEffect(() => {
  const newData = { count: count, timestamp: Date.now() };
  setData(newData);
}, [count]); // Depend on count, not data
```

---

### Bug 2: Missing Dependencies

**Problem: Effect uses values that aren't in dependency array**

**React's ESLint plugin will warn you about this!**

```javascript
// ⚠️ Warning: missing dependency 'userId'
function Component({ userId }) {
  const [user, setUser] = useState(null);
  
  useEffect(() => {
    fetch(`/api/users/${userId}`)  // Uses userId
      .then(res => res.json())
      .then(data => setUser(data));
  }, []); // But userId not in array!
  
  return <div>{user?.name}</div>;
}
```

**Problem:** If userId changes, effect doesn't run, so you're fetching the wrong user.

**Solution: Include all dependencies**

```javascript
// ✅ FIXED
useEffect(() => {
  fetch(`/api/users/${userId}`)
    .then(res => res.json())
    .then(data => setUser(data));
}, [userId]); // Include userId
```

---

**Example with functions:**

```javascript
// ⚠️ Warning: missing dependency 'fetchUser'
function Component({ userId }) {
  const [user, setUser] = useState(null);
  
  const fetchUser = () => {
    fetch(`/api/users/${userId}`)
      .then(res => res.json())
      .then(data => setUser(data));
  };
  
  useEffect(() => {
    fetchUser();  // Calls fetchUser
  }, []); // But fetchUser not in array!
  
  return <div>{user?.name}</div>;
}
```

**Problem:** fetchUser is recreated every render and captures new userId, but effect doesn't re-run.

**Solution 1: Include function in dependencies**

```javascript
// ⚠️ This causes the function issue again
useEffect(() => {
  fetchUser();
}, [fetchUser]); // fetchUser changes every render
```

**Solution 2: Use useCallback**

```javascript
// ✅ FIXED with useCallback
const fetchUser = useCallback(() => {
  fetch(`/api/users/${userId}`)
    .then(res => res.json())
    .then(data => setUser(data));
}, [userId]); // Stable function, changes only when userId changes

useEffect(() => {
  fetchUser();
}, [fetchUser]);
```

**Solution 3: Move function inside effect**

```javascript
// ✅ FIXED - simplest solution
useEffect(() => {
  const fetchUser = () => {
    fetch(`/api/users/${userId}`)
      .then(res => res.json())
      .then(data => setUser(data));
  };
  
  fetchUser();
}, [userId]);
```

---

### Bug 3: Stale Closures in Effects

**Problem: Effect closes over old values**

```javascript
function Component() {
  const [count, setCount] = useState(0);
  
  useEffect(() => {
    const timer = setInterval(() => {
      console.log('Count:', count);  // Always logs 0!
      setCount(count + 1);            // Always sets to 1!
    }, 1000);
    
    return () => clearInterval(timer);
  }, []); // Empty array - effect only runs once
  
  return <div>{count}</div>;
}
```

**What happens:**
- Effect runs once with count=0
- Interval function closes over count=0
- Every second: count + 1 = 0 + 1 = 1
- Count stuck at 1!

**Solution: Use functional state update**

```javascript
// ✅ FIXED
useEffect(() => {
  const timer = setInterval(() => {
    setCount(prevCount => prevCount + 1);  // Uses latest value
  }, 1000);
  
  return () => clearInterval(timer);
}, []);
```

---

### Bug 4: Race Conditions

**Problem: Multiple async operations complete in wrong order**

```javascript
function UserProfile({ userId }) {
  const [user, setUser] = useState(null);
  
  useEffect(() => {
    fetch(`/api/users/${userId}`)
      .then(res => res.json())
      .then(data => setUser(data));
  }, [userId]);
  
  return <div>{user?.name}</div>;
}
```

**Problem scenario:**
```
1. userId = 1, start fetching user 1 (slow: 5 seconds)
2. userId = 2, start fetching user 2 (fast: 1 second)
3. User 2 data arrives, setUser(user2) ✅
4. User 1 data arrives, setUser(user1) ❌ Wrong user!
```

**Solution: Use cleanup to ignore stale responses**

```javascript
// ✅ FIXED with ignore flag
useEffect(() => {
  let ignore = false;
  
  fetch(`/api/users/${userId}`)
    .then(res => res.json())
    .then(data => {
      if (!ignore) {  // Only update if not stale
        setUser(data);
      }
    });
  
  return () => {
    ignore = true;  // Mark as stale on cleanup
  };
}, [userId]);
```

**Or use AbortController:**

```javascript
// ✅ FIXED with AbortController
useEffect(() => {
  const controller = new AbortController();
  
  fetch(`/api/users/${userId}`, {
    signal: controller.signal
  })
    .then(res => res.json())
    .then(data => setUser(data))
    .catch(err => {
      if (err.name !== 'AbortError') {
        console.error(err);
      }
    });
  
  return () => {
    controller.abort();  // Cancel ongoing request
  };
}, [userId]);
```

---

### Bug 5: Overusing Effects

**Problem: Using effects when you don't need them**

**Anti-pattern: Updating state based on prop changes**

```javascript
// ❌ BAD - Unnecessary effect
function Component({ items }) {
  const [filteredItems, setFilteredItems] = useState([]);
  
  useEffect(() => {
    setFilteredItems(items.filter(item => item.active));
  }, [items]);
  
  return <ul>{filteredItems.map(...)}</ul>;
}
```

**Solution: Calculate during render**

```javascript
// ✅ GOOD - No effect needed
function Component({ items }) {
  const filteredItems = items.filter(item => item.active);
  
  return <ul>{filteredItems.map(...)}</ul>;
}
```

---

**Anti-pattern: Updating state based on another state**

```javascript
// ❌ BAD - Unnecessary effect
function Component({ price, quantity }) {
  const [total, setTotal] = useState(0);
  
  useEffect(() => {
    setTotal(price * quantity);
  }, [price, quantity]);
  
  return <div>Total: ${total}</div>;
}
```

**Solution: Calculate during render**

```javascript
// ✅ GOOD - No effect needed
function Component({ price, quantity }) {
  const total = price * quantity;
  
  return <div>Total: ${total}</div>;
}
```

---

**Anti-pattern: Resetting state on prop change**

```javascript
// ❌ BAD - Causes extra render
function Form({ userId }) {
  const [name, setName] = useState('');
  
  useEffect(() => {
    setName('');  // Reset form when userId changes
  }, [userId]);
  
  return <input value={name} onChange={(e) => setName(e.target.value)} />;
}
```

**Solution: Use key to reset component**

```javascript
// ✅ GOOD - React handles reset
function Parent({ userId }) {
  return <Form key={userId} />;
  // When userId changes, React unmounts old Form and mounts new one
}

function Form() {
  const [name, setName] = useState('');
  return <input value={name} onChange={(e) => setName(e.target.value)} />;
}
```

---

**When you DON'T need effects:**
- ❌ Transforming data for rendering (calculate during render)
- ❌ Handling user events (use event handlers)
- ❌ Resetting state on prop change (use key)
- ❌ Updating one state based on another (derive during render)
- ❌ Sharing logic (extract to custom hook or regular function)

**When you DO need effects:**
- ✅ Fetching data
- ✅ Setting up subscriptions
- ✅ Synchronizing with external systems
- ✅ Timers and intervals
- ✅ DOM manipulation React doesn't handle
- ✅ Logging/analytics
- ✅ Browser APIs (localStorage, etc.)

---

### Bug 6: Effects Running Twice in Development

**In React 18+ Strict Mode, effects run twice in development:**

```javascript
function Component() {
  useEffect(() => {
    console.log('Effect running');
    
    return () => {
      console.log('Cleanup running');
    };
  }, []);
  
  return <div>Component</div>;
}

// Development console:
// Effect running
// Cleanup running
// Effect running

// Production console:
// Effect running
```

**Why?** React tests that your cleanup works correctly.

**This is NOT a bug!** It helps you find cleanup issues early.

**If you see problems:**
- Your effect needs proper cleanup
- Fix the cleanup, don't try to prevent double-running

---

## Summary: Part 7

### Key Concepts

**1. What is a Side Effect**
- Operations that interact with outside world
- Fetching data, timers, subscriptions, DOM manipulation
- Rendering must be pure (no side effects)
- Effects run after rendering
- Effects are for synchronizing with external systems

**2. useEffect Deep Dive**
- Syntax: `useEffect(effectFn, dependencies)`
- Runs after render and DOM update
- Three dependency patterns:
  - No array: runs every render
  - Empty array `[]`: runs once on mount
  - With deps `[a, b]`: runs when deps change
- Cleanup function: runs before next effect or unmount
- Multiple effects OK: separate concerns
- Dependencies compared with `Object.is()`

**3. Cleanup Function**
- Return function from effect for cleanup
- Runs before next effect (if deps changed)
- Runs before unmount
- Essential for: timers, subscriptions, event listeners
- Prevents memory leaks

**4. Common Bugs**
- **Infinite loops**: caused by missing deps or updating deps
- **Missing dependencies**: effect uses values not in array
- **Stale closures**: effect closes over old values
- **Race conditions**: async operations complete out of order
- **Overusing effects**: calculating during render is better
- **Double-running in dev**: intentional, tests cleanup

---

### Mental Models

**Rendering vs Effects:**
```
Render (Pure)          Effect (Impure)
├─ Calculate JSX       ├─ Fetch data
├─ Read props/state    ├─ Set up subscriptions
├─ Return UI           ├─ Timers
└─ No side effects     └─ DOM manipulation
```

**Effect Timeline:**
```
Component mounts
  → Render
  → DOM updates
  → Effect runs
  
Dependency changes
  → Render
  → DOM updates
  → Cleanup runs
  → Effect runs
  
Component unmounts
  → Cleanup runs
```

**Dependency Decision:**
```
Does effect use this value?
  YES → Include in deps
  NO → Don't include

Is it stable (won't change)?
  YES → Safe to include
  NO → May cause re-runs (use useCallback/useMemo if needed)
```

---

### Common Patterns

**Pattern 1: Fetch data on mount**
```javascript
useEffect(() => {
  fetch('/api/data')
    .then(res => res.json())
    .then(data => setData(data));
}, []);
```

**Pattern 2: Fetch data when prop changes**
```javascript
useEffect(() => {
  fetch(`/api/users/${userId}`)
    .then(res => res.json())
    .then(data => setUser(data));
}, [userId]);
```

**Pattern 3: Subscribe and cleanup**
```javascript
useEffect(() => {
  const subscription = subscribeToData();
  
  return () => {
    subscription.unsubscribe();
  };
}, []);
```

**Pattern 4: Timer with cleanup**
```javascript
useEffect(() => {
  const timer = setInterval(() => {
    setCount(c => c + 1);
  }, 1000);
  
  return () => clearInterval(timer);
}, []);
```

**Pattern 5: Event listener**
```javascript
useEffect(() => {
  const handleClick = () => console.log('Clicked');
  
  window.addEventListener('click', handleClick);
  
  return () => {
    window.removeEventListener('click', handleClick);
  };
}, []);
```

**Pattern 6: Update document title**
```javascript
useEffect(() => {
  document.title = `You clicked ${count} times`;
}, [count]);
```

**Pattern 7: Abort fetch on cleanup**
```javascript
useEffect(() => {
  const controller = new AbortController();
  
  fetch('/api/data', { signal: controller.signal })
    .then(res => res.json())
    .then(data => setData(data))
    .catch(err => {
      if (err.name !== 'AbortError') {
        console.error(err);
      }
    });
  
  return () => controller.abort();
}, []);
```

---

### Critical Rules

**✅ DO:**
- Keep rendering pure (no side effects)
- Use effects for external system synchronization
- Include all dependencies in the array
- Provide cleanup function when needed
- Use functional updates for state in effects with empty deps
- Handle race conditions with cleanup
- Use multiple effects to separate concerns
- Trust React's ESLint plugin warnings

**❌ DON'T:**
- Put side effects directly in render
- Ignore dependency warnings
- Create new objects/arrays in dependencies
- Update state based on itself without functional update
- Forget cleanup for subscriptions/timers
- Use effects for calculations (calculate during render)
- Use effects for event handlers
- Try to prevent double-running in Strict Mode

---

### Decision Trees

**Do I need useEffect?**

```
Is this a side effect (fetch, subscription, timer, DOM manipulation)?
  NO → Don't use useEffect, handle in render or event handler
  YES → Continue

Can this be handled in an event handler?
  YES → Use event handler, not useEffect
  NO → Continue

Is this transforming data for rendering?
  YES → Calculate during render, not useEffect
  NO → Continue

Is this synchronizing with external system?
  YES → Use useEffect ✅
```

**What dependencies should I include?**

```
Does effect use this value?
  NO → Don't include
  YES → Continue

Is it a prop or state?
  YES → Include it
  NO → Continue

Is it derived from props/state?
  YES → Include it
  NO → Continue

Is it a function that uses props/state?
  YES → Include it (or wrap in useCallback)
  NO → Don't include (stable value)
```

**Do I need cleanup?**

```
Did I set up a subscription?
  YES → Clean up in return function
  NO → Continue

Did I start a timer?
  YES → Clear timer in return function
  NO → Continue

Did I add an event listener?
  YES → Remove listener in return function
  NO → Continue

Did I start an async operation that might complete after unmount?
  YES → Cancel/ignore in return function
  NO → Probably don't need cleanup
```

---

### Debugging Tips

**Problem: Infinite loop**

**Check:**
1. Do you have a dependency array?
2. Are you updating a value that's in the dependencies?
3. Are you creating new objects/arrays in dependencies?
4. Are you updating state without functional update?

**Solution:**
```javascript
// If updating state based on itself, use functional update
setCount(prev => prev + 1)  // ✅

// If depending on objects, move outside or use useMemo
const options = useMemo(() => ({ method: 'GET' }), [])  // ✅

// If calculating derived value, don't use effect at all
const total = price * quantity  // ✅
```

---

**Problem: Effect not running when it should**

**Check:**
1. Are all used values in the dependency array?
2. Is the dependency actually changing?
3. Are you comparing by reference (objects/arrays)?

**Solution:**
```javascript
// Include all dependencies
useEffect(() => {
  doSomething(userId, filter);
}, [userId, filter]);  // Both included

// For objects, ensure stable reference
const [user] = useState({ name: 'Alice' });  // Stable
```

---

**Problem: Effect running too often**

**Check:**
1. Are dependencies changing on every render?
2. Are you creating new objects/functions?
3. Do you have no dependency array?

**Solution:**
```javascript
// Move stable values outside component
const options = { method: 'GET' };

function Component() {
  useEffect(() => {
    fetch('/api', options);
  }, []);  // options stable, empty deps OK
}

// Or use useMemo/useCallback
const fetchData = useCallback(() => {
  fetch('/api');
}, []);  // Stable function
```

---

**Problem: Stale values in effect**

**Check:**
1. Are you using empty dependency array `[]`?
2. Is the effect closing over old values?

**Solution:**
```javascript
// Use functional state update
useEffect(() => {
  setInterval(() => {
    setCount(prev => prev + 1);  // ✅ Uses latest
  }, 1000);
}, []);

// Or include dependency
useEffect(() => {
  const timer = setTimeout(() => {
    console.log(count);  // Uses current count
  }, 1000);
  return () => clearTimeout(timer);
}, [count]);  // Re-run when count changes
```

---

**Problem: Race condition**

**Symptom:** Wrong data displayed when rapid prop changes

**Solution:**
```javascript
useEffect(() => {
  let ignore = false;
  
  fetch(`/api/users/${userId}`)
    .then(res => res.json())
    .then(data => {
      if (!ignore) setUser(data);
    });
  
  return () => {
    ignore = true;
  };
}, [userId]);
```

---

### Performance Considerations

**Effects and re-renders:**

```javascript
// ⚠️ This causes extra render
function Component({ data }) {
  const [processed, setProcessed] = useState(null);
  
  useEffect(() => {
    setProcessed(processData(data));  // Extra render!
  }, [data]);
  
  return <div>{processed}</div>;
}

// ✅ Better - calculate during render
function Component({ data }) {
  const processed = processData(data);  // No extra render
  
  return <div>{processed}</div>;
}

// ✅ Or use useMemo if expensive
function Component({ data }) {
  const processed = useMemo(() => processData(data), [data]);
  
  return <div>{processed}</div>;
}
```

**Batching state updates in effects:**

React automatically batches state updates in effects:

```javascript
useEffect(() => {
  setCount(1);
  setName('Alice');
  setActive(true);
  // All batched into one re-render ✅
}, []);
```

---

### Real-World Examples

**Example 1: Complete data fetching pattern**

```javascript
function UserProfile({ userId }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  
  useEffect(() => {
    let ignore = false;
    
    setLoading(true);
    setError(null);
    
    fetch(`/api/users/${userId}`)
      .then(res => {
        if (!res.ok) throw new Error('Failed to fetch');
        return res.json();
      })
      .then(data => {
        if (!ignore) {
          setUser(data);
          setLoading(false);
        }
      })
      .catch(err => {
        if (!ignore) {
          setError(err.message);
          setLoading(false);
        }
      });
    
    return () => {
      ignore = true;
    };
  }, [userId]);
  
  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;
  if (!user) return <div>No user found</div>;
  
  return (
    <div>
      <h1>{user.name}</h1>
      <p>{user.email}</p>
    </div>
  );
}
```

---

**Example 2: WebSocket subscription**

```javascript
function LiveFeed({ channel }) {
  const [messages, setMessages] = useState([]);
  const [isConnected, setIsConnected] = useState(false);
  
  useEffect(() => {
    const ws = new WebSocket(`wss://api.example.com/${channel}`);
    
    ws.onopen = () => {
      console.log('Connected');
      setIsConnected(true);
    };
    
    ws.onmessage = (event) => {
      const message = JSON.parse(event.data);
      setMessages(prev => [...prev, message]);
    };
    
    ws.onerror = (error) => {
      console.error('WebSocket error:', error);
    };
    
    ws.onclose = () => {
      console.log('Disconnected');
      setIsConnected(false);
    };
    
    // Cleanup: Close connection
    return () => {
      ws.close();
    };
  }, [channel]);  // Reconnect when channel changes
  
  return (
    <div>
      <div>Status: {isConnected ? '🟢 Connected' : '🔴 Disconnected'}</div>
      <ul>
        {messages.map((msg, index) => (
          <li key={index}>{msg.text}</li>
        ))}
      </ul>
    </div>
  );
}
```

---

**Example 3: Auto-save with debounce**

```javascript
function Editor({ documentId }) {
  const [content, setContent] = useState('');
  const [lastSaved, setLastSaved] = useState(null);
  
  // Auto-save effect with debounce
  useEffect(() => {
    const timer = setTimeout(() => {
      // Save to API
      fetch(`/api/documents/${documentId}`, {
        method: 'PUT',
        body: JSON.stringify({ content }),
        headers: { 'Content-Type': 'application/json' }
      })
        .then(() => setLastSaved(new Date()))
        .catch(err => console.error('Save failed:', err));
    }, 1000);  // Wait 1 second after typing stops
    
    return () => clearTimeout(timer);
  }, [content, documentId]);
  
  return (
    <div>
      <textarea
        value={content}
        onChange={(e) => setContent(e.target.value)}
        rows={10}
        cols={50}
      />
      {lastSaved && (
        <div>Last saved: {lastSaved.toLocaleTimeString()}</div>
      )}
    </div>
  );
}
```

---

**Example 4: Intersection Observer (scroll detection)**

```javascript
function LazyImage({ src, alt }) {
  const [isVisible, setIsVisible] = useState(false);
  const imgRef = useRef(null);
  
  useEffect(() => {
    const observer = new IntersectionObserver(
      ([entry]) => {
        if (entry.isIntersecting) {
          setIsVisible(true);
          observer.disconnect();  // Stop observing after visible
        }
      },
      { threshold: 0.1 }
    );
    
    if (imgRef.current) {
      observer.observe(imgRef.current);
    }
    
    return () => {
      observer.disconnect();
    };
  }, []);
  
  return (
    <div ref={imgRef}>
      {isVisible ? (
        <img src={src} alt={alt} />
      ) : (
        <div style={{ height: '200px', background: '#eee' }}>
          Loading...
        </div>
      )}
    </div>
  );
}
```

---

**Example 5: Local storage sync**

```javascript
function useLocalStorage(key, initialValue) {
  // Initialize from localStorage or use initial value
  const [value, setValue] = useState(() => {
    const stored = localStorage.getItem(key);
    return stored ? JSON.parse(stored) : initialValue;
  });
  
  // Sync to localStorage when value changes
  useEffect(() => {
    localStorage.setItem(key, JSON.stringify(value));
  }, [key, value]);
  
  return [value, setValue];
}

// Usage
function App() {
  const [theme, setTheme] = useLocalStorage('theme', 'light');
  
  return (
    <div className={theme}>
      <button onClick={() => setTheme(theme === 'light' ? 'dark' : 'light')}>
        Toggle Theme
      </button>
    </div>
  );
}
```

---

**Example 6: Mouse position tracker**

```javascript
function useMousePosition() {
  const [position, setPosition] = useState({ x: 0, y: 0 });
  
  useEffect(() => {
    const handleMouseMove = (e) => {
      setPosition({ x: e.clientX, y: e.clientY });
    };
    
    window.addEventListener('mousemove', handleMouseMove);
    
    return () => {
      window.removeEventListener('mousemove', handleMouseMove);
    };
  }, []);
  
  return position;
}

// Usage
function MouseTracker() {
  const { x, y } = useMousePosition();
  
  return (
    <div>
      Mouse position: {x}, {y}
    </div>
  );
}
```

---

**Example 7: Document title sync**

```javascript
function useDocumentTitle(title) {
  useEffect(() => {
    const prevTitle = document.title;
    document.title = title;
    
    // Cleanup: Restore previous title
    return () => {
      document.title = prevTitle;
    };
  }, [title]);
}

// Usage
function UserProfile({ user }) {
  useDocumentTitle(`${user.name}'s Profile`);
  
  return <div>{user.name}</div>;
}
```

---

### Advanced Patterns

**Pattern: Custom hook with effect**

```javascript
function useFetch(url) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  
  useEffect(() => {
    let ignore = false;
    
    setLoading(true);
    
    fetch(url)
      .then(res => res.json())
      .then(data => {
        if (!ignore) {
          setData(data);
          setLoading(false);
        }
      })
      .catch(err => {
        if (!ignore) {
          setError(err);
          setLoading(false);
        }
      });
    
    return () => {
      ignore = true;
    };
  }, [url]);
  
  return { data, loading, error };
}

// Usage
function Component() {
  const { data, loading, error } = useFetch('/api/users');
  
  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error.message}</div>;
  return <div>{JSON.stringify(data)}</div>;
}
```

---

**Pattern: Effect with multiple dependencies**

```javascript
function SearchResults({ query, category, sortBy }) {
  const [results, setResults] = useState([]);
  
  useEffect(() => {
    const params = new URLSearchParams({
      q: query,
      category,
      sort: sortBy
    });
    
    fetch(`/api/search?${params}`)
      .then(res => res.json())
      .then(data => setResults(data));
  }, [query, category, sortBy]);  // Re-fetch when any changes
  
  return (
    <ul>
      {results.map(item => (
        <li key={item.id}>{item.title}</li>
      ))}
    </ul>
  );
}
```

---

**Pattern: Conditional effect**

```javascript
function Component({ shouldFetch, userId }) {
  const [data, setData] = useState(null);
  
  useEffect(() => {
    if (!shouldFetch) return;  // Conditional execution
    
    fetch(`/api/users/${userId}`)
      .then(res => res.json())
      .then(data => setData(data));
  }, [shouldFetch, userId]);
  
  return <div>{data?.name}</div>;
}
```

---

## Comparison: Effects vs Other Approaches

### Effects vs Event Handlers

**Use event handler when:**
- Responding to user interaction
- Action triggered by specific event

```javascript
// ✅ Event handler - user clicked button
function Component() {
  const handleClick = () => {
    fetch('/api/like');
  };
  
  return <button onClick={handleClick}>Like</button>;
}

// ❌ Don't use effect for this
function Component() {
  const [clicked, setClicked] = useState(false);
  
  useEffect(() => {
    if (clicked) {
      fetch('/api/like');
    }
  }, [clicked]);
  
  return <button onClick={() => setClicked(true)}>Like</button>;
}
```

---

### Effects vs Derived State

**Use calculation when:**
- Value can be computed from existing state/props
- No external system involved

```javascript
// ✅ Calculate during render
function ShoppingCart({ items }) {
  const total = items.reduce((sum, item) => sum + item.price, 0);
  return <div>Total: ${total}</div>;
}

// ❌ Don't use effect for this
function ShoppingCart({ items }) {
  const [total, setTotal] = useState(0);
  
  useEffect(() => {
    setTotal(items.reduce((sum, item) => sum + item.price, 0));
  }, [items]);
  
  return <div>Total: ${total}</div>;
}
```

---

### When to Use useEffect

**✅ DO use useEffect for:**

1. **Fetching data**
   ```javascript
   useEffect(() => {
     fetch('/api/data').then(/*...*/);
   }, []);
   ```

2. **Subscriptions**
   ```javascript
   useEffect(() => {
     const sub = subscribe();
     return () => sub.unsubscribe();
   }, []);
   ```

3. **Timers**
   ```javascript
   useEffect(() => {
     const timer = setInterval(/*...*/);
     return () => clearInterval(timer);
   }, []);
   ```

4. **DOM manipulation React doesn't handle**
   ```javascript
   useEffect(() => {
     inputRef.current.focus();
   }, []);
   ```

5. **Synchronizing with external systems**
   ```javascript
   useEffect(() => {
     map.setCenter(location);
   }, [location]);
   ```

**❌ DON'T use useEffect for:**

1. **Transforming data for rendering**
   ```javascript
   // ❌ Don't
   useEffect(() => {
     setFiltered(items.filter(/*...*/));
   }, [items]);
   
   // ✅ Do
   const filtered = items.filter(/*...*/);
   ```

2. **Event handlers**
   ```javascript
   // ❌ Don't
   useEffect(() => {
     if (submitted) doSubmit();
   }, [submitted]);
   
   // ✅ Do
   const handleSubmit = () => doSubmit();
   ```

3. **Initializing state**
   ```javascript
   // ❌ Don't
   useEffect(() => {
     setData(computeInitial());
   }, []);
   
   // ✅ Do
   const [data] = useState(() => computeInitial());
   ```

---

## Final Checklist

Before using useEffect, ask yourself:

- [ ] Is this actually a side effect?
- [ ] Could this be calculated during render instead?
- [ ] Could this be handled in an event handler?
- [ ] Have I included all dependencies?
- [ ] Do I need cleanup?
- [ ] Am I handling race conditions?
- [ ] Am I avoiding infinite loops?
- [ ] Is this the simplest solution?

---