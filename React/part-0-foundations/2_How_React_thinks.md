
# How React Thinks

This is the most important mental model. Get this right, and everything else makes sense.

**UI as a Function of State:**

In React, your UI is literally a function:
```js
UI = f(state)
```
## What does this mean?

-State is your data (like isLiked, userName, postCount)
-Your component is a function that takes that state
-It returns what the UI should look like

## Every time state changes, React calls your function again and updates the UI.

Example:
```js 
function Greeting({ name }) {
  return <h1>Hello, {name}!</h1>;
}

// When name = "Alice" → <h1>Hello, Alice!</h1>
// When name = "Bob"   → <h1>Hello, Bob!</h1>
```

Same function, different input, different output. That's it.

---

**Component-Driven Architecture:**

Instead of building one giant page, you build small, reusable pieces (components):
```
App
├── Header
│   ├── Logo
│   └── Navigation
├── Feed
│   └── Post (repeated)
│       ├── Author
│       ├── Content
│       └── LikeButton
└── Sidebar
    └── TrendingTopics
```

Each component:
- Has one job
- Can be reused
- Can be tested independently
- Receives data through **props**

---

**One-Way Data Flow:**

Data flows in ONE direction: **parent → child**

Parent (has data)
   ↓ (passes via props)
Child (receives data)

Children cannot directly change parent's data. They can only:

-Receive data through props
-Tell the parent "something happened" (via callback functions)

This makes code predictable: if something's wrong, you know where to look.

## Re-rendering Philosophy:
Here's the key insight that confuses beginners:
When state changes, React doesn't edit the existing UI. It renders the component again from scratch.
```js
function Counter() {
  const [count, setCount] = useState(0);
  
  return (
    <div>
      <p>Count: {count}</p>
      <button onClick={() => setCount(count + 1)}>+</button>
    </div>
  );
}
```

### What happens when you click the button:

-setCount updates the state
-React says "this component's state changed, let me run it again"
-React calls Counter() function again
-It gets a new result with the new count value
-React compares old UI vs new UI
-React updates only what changed in the real DOM

Critical understanding: Your component function runs many, many times. Every state change = one more run. This is why components should be pure functions (same input = same output).