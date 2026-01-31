
## 4.3 State Update Pitfalls

### Stale Closures

**One of the most common React bugs.**

**The problem:**
```javascript
function Counter() {
  const [count, setCount] = useState(0);
  
  useEffect(() => {
    const timer = setInterval(() => {
      console.log('Count:', count);
      setCount(count + 1);  // BUG! Always adds to 0
    }, 1000);
    
    return () => clearInterval(timer);
  }, []);  // Empty dependency array
  
  return <div>{count}</div>;
}
```

**What happens:**
```
1 second:  count = 1 (0 + 1)
2 seconds: count = 1 (0 + 1)  ← Still 1!
3 seconds: count = 1 (0 + 1)  ← Still 1!
```

**Why?** The timer function "closes over" `count = 0` from the first render.

---

### Understanding Closures

**JavaScript closure refresher:**

```javascript
function outer() {
  const x = 5;
  
  function inner() {
    console.log(x);  // Closes over x
  }
  
  return inner;
}

const myFunc = outer();
myFunc();  // Logs: 5
```

`inner` "remembers" `x` from when it was created.

**In React:**
```javascript
function Counter() {
  const [count, setCount] = useState(0);
  
  useEffect(() => {
    // This function closes over count = 0
    const timer = setInterval(() => {
      console.log(count);  // Always logs 0
    }, 1000);
    
    return () => clearInterval(timer);
  }, []);  // Runs only once, when count = 0
}
```

---

### Solution 1: Use Updater Function

```javascript
function Counter() {
  const [count, setCount] = useState(0);
  
  useEffect(() => {
    const timer = setInterval(() => {
      setCount(prev => prev + 1);  // ✅ Uses latest value
    }, 1000);
    
    return () => clearInterval(timer);
  }, []);
  
  return <div>{count}</div>;
}
```

**Now it works!** Updater function receives the latest state.

---

### Solution 2: Include Dependency

```javascript
function Counter() {
  const [count, setCount] = useState(0);
  
  useEffect(() => {
    const timer = setInterval(() => {
      console.log(count);  // Now logs current count
      setCount(count + 1);
    }, 1000);
    
    return () => clearInterval(timer);
  }, [count]);  // Re-run when count changes
  
  return <div>{count}</div>;
}
```

**Warning:** This recreates the timer every second. Usually not ideal.

---

### Multiple Updates Problem

**Problem:**
```javascript
function Component() {
  const [count, setCount] = useState(0);
  
  const handleClick = () => {
    setCount(count + 1);
    setCount(count + 1);
    setCount(count + 1);
  };
  
  return <button onClick={handleClick}>+3</button>;
}
```

**Result:** Count increases by 1, not 3.

**Why?** All three updates see `count = 0` (snapshot).

**Solution:**
```javascript
const handleClick = () => {
  setCount(prev => prev + 1);
  setCount(prev => prev + 1);
  setCount(prev => prev + 1);
};
```

---

### Async Updates Problem

**Problem:**
```javascript
function Component() {
  const [count, setCount] = useState(0);
  
  const handleClick = async () => {
    await someAsyncFunction();
    setCount(count + 1);  // Uses stale count!
  };
  
  return <button onClick={handleClick}>Click</button>;
}
```

If component re-rendered while waiting, `count` is stale.

**Solution:**
```javascript
const handleClick = async () => {
  await someAsyncFunction();
  setCount(prev => prev + 1);  // ✅ Uses latest
};
```

---

### Event Handlers and Closures

**Problem:**
```javascript
function Component() {
  const [count, setCount] = useState(0);
  
  const handleClick = () => {
    setTimeout(() => {
      console.log('Count:', count);  // Stale!
      alert(`Count is ${count}`);
    }, 3000);
  };
  
  return (
    <div>
      <p>{count}</p>
      <button onClick={() => setCount(count + 1)}>+</button>
      <button onClick={handleClick}>Show in 3s</button>
    </div>
  );
}
```

**Scenario:**
1. count = 0
2. Click "Show in 3s" (setTimeout queued with count = 0)
3. Click "+" three times (count = 3)
4. After 3 seconds: alert shows "Count is 0"

**The setTimeout closed over count = 0.**

---

### Solution: Use Ref for Latest Value

```javascript
function Component() {
  const [count, setCount] = useState(0);
  const countRef = useRef(count);
  
  // Keep ref in sync with state
  useEffect(() => {
    countRef.current = count;
  }, [count]);
  
  const handleClick = () => {
    setTimeout(() => {
      alert(`Count is ${countRef.current}`);  // Latest value!
    }, 3000);
  };
  
  return (
    <div>
      <p>{count}</p>
      <button onClick={() => setCount(count + 1)}>+</button>
      <button onClick={handleClick}>Show in 3s</button>
    </div>
  );
}
```

---

### Common Pitfall: State in Event Handler

**Problem:**
```javascript
function Form() {
  const [email, setEmail] = useState('');
  
  const handleSubmit = (e) => {
    e.preventDefault();
    
    // This might be stale if form re-rendered
    console.log('Email:', email);
  };
  
  return (
    <form onSubmit={handleSubmit}>
      <input value={email} onChange={(e) => setEmail(e.target.value)} />
      <button>Submit</button>
    </form>
  );
}
```

**Usually fine, but can be problematic in complex scenarios.**

**Better: Get value from event or form:**
```javascript
const handleSubmit = (e) => {
  e.preventDefault();
  const formData = new FormData(e.target);
  const email = formData.get('email');
  console.log('Email:', email);
};
```

---
