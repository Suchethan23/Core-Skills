# PART 5 – Events & User Interaction

Events are how users interact with your React application. Understanding how React handles events is crucial for building interactive UIs.

---

## 5.1 Event Handling in React

### Basic Event Handling

In React, you attach event handlers directly to JSX elements.

**HTML (old way):**
```html
<button onclick="handleClick()">Click me</button>
```

**React:**
```javascript
function Component() {
  const handleClick = () => {
    console.log('Button clicked!');
  };
  
  return <button onClick={handleClick}>Click me</button>;
}
```

**Key differences:**
1. React uses camelCase: `onClick`, not `onclick`
2. Pass a function reference: `onClick={handleClick}`, not `onClick="handleClick()"`
3. No need for `return false` to prevent default, use `event.preventDefault()`

---

### Common Events

**Mouse events:**
```javascript
function Component() {
  return (
    <div
      onClick={() => console.log('Clicked')}
      onDoubleClick={() => console.log('Double clicked')}
      onMouseEnter={() => console.log('Mouse entered')}
      onMouseLeave={() => console.log('Mouse left')}
      onMouseMove={() => console.log('Mouse moving')}
    >
      Hover or click me
    </div>
  );
}
```

**Keyboard events:**
```javascript
function Component() {
  return (
    <input
      onKeyDown={(e) => console.log('Key down:', e.key)}
      onKeyUp={(e) => console.log('Key up:', e.key)}
      onKeyPress={(e) => console.log('Key pressed:', e.key)}
    />
  );
}
```

**Form events:**
```javascript
function Component() {
  return (
    <form onSubmit={(e) => console.log('Form submitted')}>
      <input
        onChange={(e) => console.log('Input changed:', e.target.value)}
        onFocus={() => console.log('Input focused')}
        onBlur={() => console.log('Input blurred')}
      />
      <button type="submit">Submit</button>
    </form>
  );
}
```

**Focus events:**
```javascript
function Component() {
  return (
    <input
      onFocus={() => console.log('Focused')}
      onBlur={() => console.log('Blurred')}
    />
  );
}
```

---

### Event Handler Syntax

**Three common patterns:**

**1. Inline arrow function:**
```javascript
<button onClick={() => console.log('Clicked')}>Click</button>
```
- ✅ Good for simple operations
- ⚠️ Creates new function on every render (minor performance impact)

**2. Function reference:**
```javascript
function Component() {
  const handleClick = () => {
    console.log('Clicked');
  };
  
  return <button onClick={handleClick}>Click</button>;
}
```
- ✅ Good for readability
- ✅ Can be reused
- ⚠️ Still creates new function on every render (unless memoized)

**3. Inline with parameters:**
```javascript
function Component() {
  const handleClick = (name) => {
    console.log('Hello,', name);
  };
  
  return (
    <div>
      <button onClick={() => handleClick('Alice')}>Greet Alice</button>
      <button onClick={() => handleClick('Bob')}>Greet Bob</button>
    </div>
  );
}
```

---

### Passing Arguments to Event Handlers

**❌ Common mistake:**
```javascript
// This calls the function immediately! Don't do this
<button onClick={handleClick('Alice')}>Click</button>
```

**✅ Correct approaches:**

**Option 1: Arrow function wrapper:**
```javascript
<button onClick={() => handleClick('Alice')}>Click</button>
```

**Option 2: Bind:**
```javascript
<button onClick={handleClick.bind(null, 'Alice')}>Click</button>
```

**Option 3: Curry the function:**
```javascript
const handleClick = (name) => () => {
  console.log('Hello,', name);
};

<button onClick={handleClick('Alice')}>Click</button>
```

---

### The Event Object

**Event handlers receive an event object as their first parameter:**

```javascript
function Component() {
  const handleClick = (event) => {
    console.log('Event type:', event.type);
    console.log('Target element:', event.target);
    console.log('Current target:', event.currentTarget);
  };
  
  return <button onClick={handleClick}>Click me</button>;
}
```

**Common event properties:**
```javascript
const handleEvent = (e) => {
  e.type           // Event type: 'click', 'change', etc.
  e.target         // Element that triggered the event
  e.currentTarget  // Element the handler is attached to
  e.preventDefault() // Prevent default behavior
  e.stopPropagation() // Stop event bubbling
};
```

---

### Synthetic Events

**React doesn't use native browser events directly. It uses "Synthetic Events".**

**What are Synthetic Events?**

React wraps browser's native events in a cross-browser wrapper called `SyntheticEvent`.

**Why?**
- **Cross-browser compatibility** - Same API across all browsers
- **Performance** - Event pooling (in React <17)
- **Consistency** - Predictable behavior

```javascript
function Component() {
  const handleClick = (event) => {
    // 'event' is a SyntheticEvent, not a native Event
    console.log(event); // SyntheticEvent {type: 'click', ...}
    
    // But it has the same interface as native events
    event.preventDefault();
    event.stopPropagation();
    console.log(event.target);
  };
  
  return <button onClick={handleClick}>Click</button>;
}
```

---

### SyntheticEvent Properties

**The SyntheticEvent has the same interface as native events:**

```javascript
function handleEvent(e) {
  // Event info
  e.type              // 'click', 'submit', 'change', etc.
  e.bubbles           // Boolean: does event bubble?
  e.cancelable        // Boolean: can default be prevented?
  e.timeStamp         // Timestamp when event occurred
  
  // Target info
  e.target            // Element that triggered event
  e.currentTarget     // Element handler is attached to
  
  // Mouse events
  e.clientX           // Mouse X coordinate
  e.clientY           // Mouse Y coordinate
  e.pageX             // Page X coordinate
  e.pageY             // Page Y coordinate
  e.button            // Which mouse button clicked
  
  // Keyboard events
  e.key               // Which key pressed
  e.code              // Physical key code
  e.keyCode           // Deprecated but still available
  e.shiftKey          // Was Shift pressed?
  e.ctrlKey           // Was Ctrl pressed?
  e.altKey            // Was Alt pressed?
  e.metaKey           // Was Meta (Cmd/Win) pressed?
  
  // Form events
  e.target.value      // Input value
  e.target.checked    // Checkbox/radio checked state
  
  // Methods
  e.preventDefault()     // Prevent default behavior
  e.stopPropagation()    // Stop event bubbling
}
```

---

### Accessing Native Event

**If you need the native browser event:**

```javascript
function Component() {
  const handleClick = (syntheticEvent) => {
    const nativeEvent = syntheticEvent.nativeEvent;
    console.log('Native event:', nativeEvent);
  };
  
  return <button onClick={handleClick}>Click</button>;
}
```

**Usually you don't need this.** SyntheticEvent has everything you need.

---

### Event Pooling (React <17 only)

**In React versions before 17, events were pooled for performance.**

**Old behavior (React <17):**
```javascript
function Component() {
  const handleClick = (event) => {
    console.log(event.type); // 'click'
    
    setTimeout(() => {
      console.log(event.type); // null! Event was pooled/reused
    }, 100);
  };
  
  return <button onClick={handleClick}>Click</button>;
}
```

**Solution in old React:**
```javascript
const handleClick = (event) => {
  event.persist(); // Prevents pooling
  
  setTimeout(() => {
    console.log(event.type); // 'click' - works now
  }, 100);
};
```

**⚠️ In React 17+, event pooling is removed.** You don't need `persist()` anymore.

---

### Event Delegation

**React uses event delegation for performance.**

**What is event delegation?**

Instead of attaching event listeners to individual elements, React attaches one listener to the root of your app.

**Traditional approach (without delegation):**
```javascript
// Each button gets its own listener
button1.addEventListener('click', handler);
button2.addEventListener('click', handler);
button3.addEventListener('click', handler);
// 1000 buttons = 1000 listeners
```

**React's approach (with delegation):**
```javascript
// React attaches ONE listener to the root
root.addEventListener('click', (e) => {
  if (e.target matches button) {
    call appropriate handler
  }
});
// 1000 buttons = 1 listener!
```

**Benefits:**
- **Better performance** - Fewer event listeners
- **Less memory** - Less overhead
- **Automatic cleanup** - When components unmount, no need to remove listeners

**You don't need to do anything special. React handles this automatically.**

---

### React 17+ Event Delegation Change

**React <17:** Events delegated to `document`  
**React 17+:** Events delegated to the root container

```javascript
// React 17+
const root = document.getElementById('root');
ReactDOM.createRoot(root).render(<App />);
// Events attached to 'root', not 'document'
```

**Why this change?**
- Better support for multiple React apps on same page
- Better integration with non-React code
- Safer for micro-frontends

**You don't need to change your code. This is internal behavior.**

---

### Preventing Default Behavior

**Use `event.preventDefault()` to stop default browser behavior:**

**Example: Prevent form submission:**
```javascript
function Form() {
  const handleSubmit = (e) => {
    e.preventDefault(); // Don't reload page!
    console.log('Form submitted');
  };
  
  return (
    <form onSubmit={handleSubmit}>
      <input type="text" />
      <button type="submit">Submit</button>
    </form>
  );
}
```

**Example: Prevent link navigation:**
```javascript
function Link() {
  const handleClick = (e) => {
    e.preventDefault(); // Don't navigate!
    console.log('Link clicked, but not navigating');
  };
  
  return <a href="/somewhere" onClick={handleClick}>Click me</a>;
}
```

**❌ In React, don't use `return false`:**
```javascript
// This doesn't work in React!
<a href="/" onClick={() => { return false }}>Link</a>

// Use preventDefault instead
<a href="/" onClick={(e) => e.preventDefault()}>Link</a>
```

---

### Stopping Event Propagation

**Events bubble up through the DOM tree.**

```javascript
function Component() {
  const handleDivClick = () => {
    console.log('Div clicked');
  };
  
  const handleButtonClick = (e) => {
    console.log('Button clicked');
    e.stopPropagation(); // Stop bubbling to div
  };
  
  return (
    <div onClick={handleDivClick}>
      <button onClick={handleButtonClick}>Click me</button>
    </div>
  );
}
```

**Without `stopPropagation()`:**
```
Click button → Console:
  "Button clicked"
  "Div clicked"
```

**With `stopPropagation()`:**
```
Click button → Console:
  "Button clicked"
```

---

### Differences from Native DOM

**1. Naming:**
```javascript
// Native DOM
onclick, onchange, onsubmit

// React
onClick, onChange, onSubmit
```

**2. Handler format:**
```javascript
// Native DOM (string)
<button onclick="handleClick()">

// React (function reference)
<button onClick={handleClick}>
```

**3. Preventing default:**
```javascript
// Native DOM
<a href="/" onclick="return false">

// React
<a href="/" onClick={(e) => e.preventDefault()}>
```

**4. Event object:**
```javascript
// Native DOM - browser event
function handleClick(event) {
  // event is MouseEvent
}

// React - synthetic event
function handleClick(event) {
  // event is SyntheticEvent (wrapper)
}
```

**5. Event listener management:**
```javascript
// Native DOM - manual
element.addEventListener('click', handler);
element.removeEventListener('click', handler);

// React - automatic
<button onClick={handler}>
// React automatically adds/removes listeners
```

---

### Common Patterns

**Pattern 1: Event handler with state:**
```javascript
function Counter() {
  const [count, setCount] = useState(0);
  
  const handleClick = () => {
    setCount(count + 1);
  };
  
  return <button onClick={handleClick}>Count: {count}</button>;
}
```

**Pattern 2: Multiple event handlers:**
```javascript
function Input() {
  const [value, setValue] = useState('');
  
  const handleChange = (e) => {
    setValue(e.target.value);
  };
  
  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      console.log('Enter pressed:', value);
    }
  };
  
  return (
    <input
      value={value}
      onChange={handleChange}
      onKeyPress={handleKeyPress}
    />
  );
}
```

**Pattern 3: Event handler with data:**
```javascript
function TodoList({ todos }) {
  const handleDelete = (id) => {
    console.log('Delete todo:', id);
  };
  
  return (
    <ul>
      {todos.map(todo => (
        <li key={todo.id}>
          {todo.text}
          <button onClick={() => handleDelete(todo.id)}>Delete</button>
        </li>
      ))}
    </ul>
  );
}
```

---
