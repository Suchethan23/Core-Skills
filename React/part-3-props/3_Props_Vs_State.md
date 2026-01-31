## 3.3 Props vs State (Revisited Deeply)

This is one of the most important concepts in React. Understanding when to use props vs state is crucial.

---

### What Are Props?

**Props** are **arguments** passed to a component.
- Passed from parent
- Read-only (immutable)
- Controlled by parent
- Can't be changed by the component receiving them

**Think of props like function parameters:**
```javascript
function greet(name) {  // name is like a prop
  // You can't change 'name', just use it
  return `Hello, ${name}`;
}
```

---

### What Is State? (Preview - Deep dive in Part 4)

**State** is **memory** for a component.
- Belongs to the component
- Can be changed by the component
- Persists between renders
- Changing it causes re-render

**Think of state like a variable that remembers its value:**
```javascript
function Counter() {
  const [count, setCount] = useState(0);  // State
  
  // count persists between renders
  // setCount updates it and re-renders
  return (
    <div>
      <p>{count}</p>
      <button onClick={() => setCount(count + 1)}>+</button>
    </div>
  );
}
```

---

### Key Differences

| Aspect | Props | State |
|--------|-------|-------|
| **Ownership** | Owned by parent | Owned by component |
| **Can change?** | No (read-only) | Yes (via setState) |
| **Who controls?** | Parent | Component itself |
| **Passed down?** | Yes (parent → child) | No (internal) |
| **Triggers re-render?** | When parent changes them | When component updates it |

---

### Ownership

**Props are owned by the parent:**

```javascript
function Parent() {
  const userName = 'Alice';  // Parent owns this data
  
  return <Child name={userName} />;  // Passes to child via props
}

function Child({ name }) {
  // Child doesn't own 'name', just receives it
  // Child can't change it
  return <div>{name}</div>;
}
```

**State is owned by the component:**

```javascript
function Component() {
  const [count, setCount] = useState(0);  // Component owns this
  
  // Component can change it
  const increment = () => setCount(count + 1);
  
  return (
    <div>
      <p>{count}</p>
      <button onClick={increment}>+</button>
    </div>
  );
}
```

---

### Mutability

**Props are immutable (can't be changed):**

```javascript
function Component({ name }) {
  // ❌ Can't do this
  name = 'Changed';
  
  // ✅ Can only read
  return <div>{name}</div>;
}
```

**State is mutable (can be changed):**

```javascript
function Component() {
  const [name, setName] = useState('Alice');
  
  // ✅ Can change state
  const changeName = () => setName('Bob');
  
  return (
    <div>
      <p>{name}</p>
      <button onClick={changeName}>Change</button>
    </div>
  );
}
```

---

### When to Use Props

Use **props** when:

**1. Data comes from parent:**
```javascript
function Parent() {
  const user = { name: 'Alice', age: 25 };
  return <UserCard user={user} />;  // Parent owns, passes down
}

function UserCard({ user }) {
  return <div>{user.name}</div>;  // Receives via props
}
```

**2. Component shouldn't control the data:**
```javascript
function Button({ text, onClick }) {
  // Button doesn't control what text says or what happens on click
  // Parent controls it
  return <button onClick={onClick}>{text}</button>;
}
```

**3. Making component reusable:**
```javascript
function Card({ title, content }) {
  return (
    <div className="card">
      <h3>{title}</h3>
      <p>{content}</p>
    </div>
  );
}

// Reused with different props
<Card title="Post 1" content="Content 1" />
<Card title="Post 2" content="Content 2" />
```

**4. Data doesn't change from this component's perspective:**
```javascript
function UserProfile({ userId, userName }) {
  // This component displays user info
  // It doesn't edit it
  return <div>{userName}</div>;
}
```

---

### When to Use State

Use **state** when:

**1. Data changes over time:**
```javascript
function Counter() {
  const [count, setCount] = useState(0);  // Changes over time
  
  return (
    <div>
      <p>{count}</p>
      <button onClick={() => setCount(count + 1)}>+</button>
    </div>
  );
}
```

**2. Component needs to remember something:**
```javascript
function Form() {
  const [name, setName] = useState('');  // Remembers user input
  
  return (
    <input 
      value={name} 
      onChange={(e) => setName(e.target.value)} 
    />
  );
}
```

**3. Component controls the data:**
```javascript
function Toggle() {
  const [isOn, setIsOn] = useState(false);  // Toggle controls this
  
  return (
    <button onClick={() => setIsOn(!isOn)}>
      {isOn ? 'ON' : 'OFF'}
    </button>
  );
}
```

**4. Changing it should re-render:**
```javascript
function ColorPicker() {
  const [color, setColor] = useState('red');  // When color changes, re-render
  
  return (
    <div>
      <div style={{ backgroundColor: color }}>Preview</div>
      <button onClick={() => setColor('blue')}>Blue</button>
      <button onClick={() => setColor('green')}>Green</button>
    </div>
  );
}
```

---

### Props vs State: Same Data, Different Use Cases

The same piece of data can be **state in one component** and **props in another**.

```javascript
function Parent() {
  // In Parent, userName is STATE
  const [userName, setUserName] = useState('Alice');
  
  return (
    <div>
      <input 
        value={userName}
        onChange={(e) => setUserName(e.target.value)}
      />
      <Child name={userName} />  {/* Passed as PROP to child */}
    </div>
  );
}

function Child({ name }) {
  // In Child, name is a PROP
  // Child just displays it, doesn't own it
  return <div>Hello, {name}</div>;
}
```

**Flow:**
1. `userName` is **state** in `Parent` (Parent owns it)
2. Parent passes it to `Child` as a **prop** called `name`
3. `Child` receives it as a **prop** (Child doesn't own it)

---

### Derived State Anti-Pattern

**❌ Don't store in state what you can calculate from props:**

```javascript
// ❌ BAD - fullName stored in state but can be derived
function UserProfile({ firstName, lastName }) {
  const [fullName, setFullName] = useState(`${firstName} ${lastName}`);
  
  return <div>{fullName}</div>;
}

// Problem: If firstName or lastName props change, fullName state doesn't update
```

**✅ GOOD - Calculate during render:**

```javascript
// ✅ GOOD - Derive from props
function UserProfile({ firstName, lastName }) {
  const fullName = `${firstName} ${lastName}`;  // Calculated each render
  
  return <div>{fullName}</div>;
}
```

**Rule: If you can calculate it from props, don't store it in state.**

---

### When Props and State Work Together

**Pattern: Controlled component**

Parent owns the state, passes it down as props + callback to update:

```javascript
function Parent() {
  const [value, setValue] = useState('');  // Parent owns state
  
  return (
    <Input 
      value={value}           // Pass state as prop
      onChange={setValue}     // Pass updater as prop
    />
  );
}

function Input({ value, onChange }) {
  return (
    <input 
      value={value}
      onChange={(e) => onChange(e.target.value)}
    />
  );
}
```

**Flow:**
1. Parent has `value` state
2. Passes it to `Input` as `value` prop
3. Passes `setValue` to `Input` as `onChange` prop
4. When user types, `Input` calls `onChange`
5. Parent's state updates
6. New `value` prop flows down to `Input`
7. Input displays new value

**This is called a "controlled component" - we'll cover it deeply in Part 5.**

---

### Decision Tree: Props or State?

Ask yourself these questions:

**1. Does it come from parent via props?**
- YES → Use props
- NO → Continue

**2. Does it change over time?**
- NO → Use a regular variable (neither props nor state)
- YES → Continue

**3. Can you calculate it from other props/state?**
- YES → Derive it, don't store it
- NO → Continue

**4. Does changing it need to re-render?**
- NO → Use useRef (covered later)
- YES → Use state

---

### Examples: Props or State?

**Example 1: Current user data**
```javascript
// State in App (top level)
function App() {
  const [user, setUser] = useState({ name: 'Alice', id: 1 });
  return <Profile user={user} />;
}

// Props in Profile
function Profile({ user }) {
  return <div>{user.name}</div>;
}
```

**Example 2: Form input value**
```javascript
function Form() {
  // State - component controls it, changes over time
  const [email, setEmail] = useState('');
  
  return <input value={email} onChange={(e) => setEmail(e.target.value)} />;
}
```

**Example 3: Calculated value**
```javascript
function ShoppingCart({ items }) {
  // Neither state nor separate prop - just calculate it
  const total = items.reduce((sum, item) => sum + item.price, 0);
  
  return <div>Total: ${total}</div>;
}
```

**Example 4: Toggle button**
```javascript
function Toggle() {
  // State - component controls this, it changes
  const [isOn, setIsOn] = useState(false);
  
  return <button onClick={() => setIsOn(!isOn)}>{isOn ? 'ON' : 'OFF'}</button>;
}
```

---

## Summary: Part 3

### Key Concepts

**1. Props Fundamentals**
- Props are arguments passed to components
- Always read-only (immutable)
- Flow from parent → child (one-way)
- Can be any JavaScript value
- Use default parameters for optional props

**2. Passing Data Correctly**
- Primitives are safe (copied by value)
- Objects/arrays are references (can cause re-renders if recreated)
- Avoid creating new objects/functions in render
- Props drilling = passing props through components that don't use them
- Solutions: composition, Context API

**3. Props vs State**
- **Props**: Owned by parent, read-only, passed down
- **State**: Owned by component, mutable, internal
- Same data can be state in parent, props in child
- Don't store in state what you can derive from props
- Use props for data from parent, state for data component controls

---

### Mental Models

**Props = Function Arguments**
```javascript
function greet(name) { }  // name is like a prop
```

**Parent → Child Flow**
```
Parent (owns data)
   ↓ (passes via props)
Child (receives, uses)
```

**Props vs State Decision**
```
From parent? → Props
Changes over time + component owns it? → State
Can calculate from other data? → Neither (derive it)
```

---
