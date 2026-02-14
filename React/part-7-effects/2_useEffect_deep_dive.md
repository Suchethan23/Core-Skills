## 7.2 `useEffect` Deep Dive

### Basic Syntax

```javascript
useEffect(() => {
  // Effect code here
  
  return () => {
    // Cleanup code here (optional)
  };
}, [dependencies]);
```

**Three parts:**
1. **Effect function** - The side effect to run
2. **Cleanup function** (optional) - Cleanup before next effect or unmount
3. **Dependency array** - When to run the effect

---

### When useEffect Runs

**Effects run AFTER the component renders and the DOM is updated.**

```javascript
function Component() {
  console.log('1. Rendering...');
  
  useEffect(() => {
    console.log('3. Effect running!');
  });
  
  console.log('2. Render complete');
  
  return <div>Content</div>;
}

// Console output:
// 1. Rendering...
// 2. Render complete
// 3. Effect running!
```

**Timeline:**
```
1. Component function runs
2. JSX returned
3. React updates DOM
4. Browser paints screen
5. Effect runs ← After everything else
```

---

### The Dependency Array

**The second argument to useEffect controls when it runs.**

**Three patterns:**

**1. No dependency array - Runs after EVERY render:**
```javascript
useEffect(() => {
  console.log('Runs after every render');
});
// Runs on mount and after every update
```

**2. Empty dependency array - Runs ONCE on mount:**
```javascript
useEffect(() => {
  console.log('Runs once on mount');
}, []);
// Only runs when component first appears
```

**3. With dependencies - Runs when dependencies change:**
```javascript
useEffect(() => {
  console.log('Runs when userId changes');
}, [userId]);
// Runs on mount and whenever userId changes
```

---

### Understanding Dependencies

**React compares dependencies between renders:**

```javascript
function Component({ userId }) {
  const [count, setCount] = useState(0);
  
  useEffect(() => {
    console.log('userId changed:', userId);
  }, [userId]);
  // Runs when userId is different from last render
  
  return <button onClick={() => setCount(count + 1)}>Count: {count}</button>;
}
```

**Scenario:**
```
Initial render: userId = 1
  → Effect runs

User clicks button: count changes (0 → 1), userId still 1
  → Effect does NOT run (userId unchanged)

Parent changes userId: userId = 2
  → Effect runs (userId changed)
```

---

### Dependency Comparison

**React uses `Object.is()` comparison:**

```javascript
// Primitives - compared by value
Object.is(1, 1)         // true
Object.is('a', 'a')     // true
Object.is(true, true)   // true

// Objects/arrays - compared by reference
Object.is({}, {})           // false (different objects)
Object.is([], [])           // false (different arrays)

const obj = {};
Object.is(obj, obj)         // true (same reference)
```

**This affects objects and arrays:**

```javascript
function Component() {
  const user = { name: 'Alice' };  // New object every render!
  
  useEffect(() => {
    console.log('Effect running');
  }, [user]);
  // Runs on EVERY render because user is a new object each time
}
```

**Solution:**
```javascript
function Component() {
  const [user] = useState({ name: 'Alice' });  // Stable reference
  
  useEffect(() => {
    console.log('Effect running');
  }, [user]);
  // Now only runs when user state actually changes
}
```

---

### Pattern 1: Run Once on Mount

```javascript
function Component() {
  useEffect(() => {
    // This runs once when component mounts
    console.log('Component mounted');
    
    // Example: Fetch initial data
    fetch('/api/data')
      .then(res => res.json())
      .then(data => console.log(data));
    
    // Example: Set up subscription
    const subscription = subscribeToUpdates();
    
    // Example: Document title
    document.title = 'My App';
  }, []); // Empty array = run once
  
  return <div>Content</div>;
}
```

**Common use cases:**
- Fetch initial data
- Set up subscriptions
- Initialize third-party libraries
- Add global event listeners
- Set document title

---

### Pattern 2: Run When Dependencies Change

```javascript
function UserProfile({ userId }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  
  useEffect(() => {
    // This runs whenever userId changes
    setLoading(true);
    
    fetch(`/api/users/${userId}`)
      .then(res => res.json())
      .then(data => {
        setUser(data);
        setLoading(false);
      });
  }, [userId]); // Runs when userId changes
  
  if (loading) return <div>Loading...</div>;
  return <div>{user.name}</div>;
}
```

**When parent changes userId:**
```
userId changes: 1 → 2
  ↓
Component re-renders
  ↓
Effect runs with new userId
  ↓
Fetches new user data
  ↓
Updates state
  ↓
Component re-renders with new data
```

---

### Pattern 3: Run on Every Render (Rarely Needed)

```javascript
function Component({ value }) {
  useEffect(() => {
    // Runs after EVERY render
    console.log('Component rendered with value:', value);
  }); // No dependency array
  
  return <div>{value}</div>;
}
```

**⚠️ Use sparingly!** Usually you want to depend on specific values.

---

### The Cleanup Function

**Return a function from useEffect to clean up:**

```javascript
useEffect(() => {
  // Effect: Set up something
  
  return () => {
    // Cleanup: Tear down that something
  };
}, [dependencies]);
```

**When cleanup runs:**
1. Before the effect runs again (if dependencies changed)
2. Before component unmounts

---

### Cleanup Example: Timer

```javascript
function Timer() {
  const [seconds, setSeconds] = useState(0);
  
  useEffect(() => {
    console.log('Setting up timer');
    
    const timer = setInterval(() => {
      setSeconds(s => s + 1);
    }, 1000);
    
    // Cleanup: Clear the timer
    return () => {
      console.log('Cleaning up timer');
      clearInterval(timer);
    };
  }, []); // Run once on mount
  
  return <div>Seconds: {seconds}</div>;
}
```

**Timeline:**
```
Component mounts
  → Effect runs, timer starts
  → Timer ticks every second
  
Component unmounts
  → Cleanup runs, timer cleared
  → No more ticks (no memory leak!)
```

**Without cleanup:**
```javascript
// ❌ BAD - Memory leak!
useEffect(() => {
  const timer = setInterval(() => {
    setSeconds(s => s + 1);
  }, 1000);
  // No cleanup - timer keeps running even after unmount!
}, []);
```

---

### Cleanup Example: Event Listener

```javascript
function WindowSize() {
  const [width, setWidth] = useState(window.innerWidth);
  
  useEffect(() => {
    const handleResize = () => {
      setWidth(window.innerWidth);
    };
    
    // Set up listener
    window.addEventListener('resize', handleResize);
    
    // Cleanup: Remove listener
    return () => {
      window.removeEventListener('resize', handleResize);
    };
  }, []);
  
  return <div>Window width: {width}px</div>;
}
```

---

### Cleanup Example: Subscription

```javascript
function ChatRoom({ roomId }) {
  const [messages, setMessages] = useState([]);
  
  useEffect(() => {
    // Connect to chat room
    const connection = connectToChatRoom(roomId);
    
    connection.on('message', (message) => {
      setMessages(msgs => [...msgs, message]);
    });
    
    // Cleanup: Disconnect
    return () => {
      connection.disconnect();
    };
  }, [roomId]); // Reconnect when roomId changes
  
  return (
    <div>
      {messages.map(msg => <p key={msg.id}>{msg.text}</p>)}
    </div>
  );
}
```

**What happens when roomId changes:**
```
roomId changes: 'general' → 'sports'
  ↓
Component re-renders
  ↓
Cleanup runs: Disconnect from 'general'
  ↓
Effect runs: Connect to 'sports'
```

---

### Cleanup Example: Fetch with AbortController

```javascript
function UserProfile({ userId }) {
  const [user, setUser] = useState(null);
  
  useEffect(() => {
    const controller = new AbortController();
    
    fetch(`/api/users/${userId}`, {
      signal: controller.signal
    })
      .then(res => res.json())
      .then(data => setUser(data))
      .catch(err => {
        if (err.name === 'AbortError') {
          console.log('Fetch aborted');
        }
      });
    
    // Cleanup: Abort ongoing fetch
    return () => {
      controller.abort();
    };
  }, [userId]);
  
  return <div>{user?.name}</div>;
}
```

**Why abort?** If userId changes before fetch completes, you don't want the old data updating state.

---

### Multiple Effects

**You can use multiple useEffect calls:**

```javascript
function Component({ userId }) {
  const [user, setUser] = useState(null);
  const [posts, setPosts] = useState([]);
  
  // Effect 1: Fetch user data
  useEffect(() => {
    fetch(`/api/users/${userId}`)
      .then(res => res.json())
      .then(data => setUser(data));
  }, [userId]);
  
  // Effect 2: Fetch user posts
  useEffect(() => {
    fetch(`/api/users/${userId}/posts`)
      .then(res => res.json())
      .then(data => setPosts(data));
  }, [userId]);
  
  // Effect 3: Update document title
  useEffect(() => {
    if (user) {
      document.title = `${user.name}'s Profile`;
    }
  }, [user]);
  
  return <div>...</div>;
}
```

**Benefits of multiple effects:**
- Separation of concerns
- Each effect handles one responsibility
- Easier to understand and maintain

---
