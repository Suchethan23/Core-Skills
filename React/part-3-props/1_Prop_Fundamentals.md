# PART 3 – Props in Depth

## 3.1 Props Fundamentals

### What Are Props?

**Props** (short for "properties") are how components talk to each other.

They are **arguments passed to components**, just like arguments passed to functions.

**Regular function:**
```javascript
function greet(name) {
  return `Hello, ${name}!`;
}

greet('Alice');  // "Hello, Alice!"
```

**React component:**
```javascript
function Greeting({ name }) {
  return <h1>Hello, {name}!</h1>;
}

<Greeting name="Alice" />  // <h1>Hello, Alice!</h1>
```

**Props are how you make components dynamic and reusable.**

---

### Props Are Read-Only (Immutable)

**The Golden Rule: Never modify props.**

Props are **read-only**. A component must never change its own props.

**❌ NEVER do this:**
```javascript
function BadComponent({ count }) {
  count = count + 1;  // WRONG! Don't modify props
  return <div>{count}</div>;
}

function AnotherBadComponent({ user }) {
  user.name = 'Changed';  // WRONG! Don't mutate props
  return <div>{user.name}</div>;
}
```

**✅ Props are for reading only:**
```javascript
function GoodComponent({ count }) {
  // Just read and use the prop
  return <div>{count}</div>;
}

function AnotherGoodComponent({ user }) {
  // Read the prop value
  return <div>{user.name}</div>;
}
```

---

### Why Props Are Read-Only

**Reason 1: Predictability**

If components could modify props, you'd never know what value a prop has:

```javascript
// If this were allowed...
function BadButton({ text }) {
  text = 'Changed!';  // Modifying prop
  return <button>{text}</button>;
}

<BadButton text="Click me" />

// Parent passed "Click me" but component changed it
// Parent has no idea the value changed
// This breaks the predictable data flow
```

**Reason 2: One-way data flow**

React enforces **unidirectional data flow**: parent → child.

Data flows down through props. It never flows up through prop mutation.

```
Parent (owns the data)
   ↓  (passes via props)
Child (receives, reads only)
```

If child could modify props, data would flow both ways = chaos.

**Reason 3: Reconciliation**

React assumes props are immutable for its reconciliation algorithm.

If props could change, React couldn't efficiently determine what changed.

---

### The Parent → Child Flow

**Data always flows from parent to child through props.**

```javascript
function Parent() {
  const userName = 'Alice';
  
  return <Child name={userName} />;
}

function Child({ name }) {
  return <div>Hello, {name}</div>;
}
```

**Flow:**
1. Parent has data (`userName`)
2. Parent passes it as prop (`name={userName}`)
3. Child receives it through props (`{ name }`)
4. Child displays it

**Child cannot:**
- Change the `name` prop
- Access `userName` variable directly
- Affect parent's data

---

### How Children Communicate Back to Parents

If a child can't modify props, how does it tell the parent something happened?

**Answer: Callback functions passed as props.**

```javascript
function Parent() {
  const handleClick = () => {
    console.log('Button was clicked in child!');
  };
  
  return <Child onButtonClick={handleClick} />;
}

function Child({ onButtonClick }) {
  return <button onClick={onButtonClick}>Click me</button>;
}
```

**Flow:**
1. Parent creates a function (`handleClick`)
2. Parent passes it as a prop (`onButtonClick={handleClick}`)
3. Child receives the function via props
4. Child calls it when something happens (`onClick={onButtonClick}`)
5. Parent's function executes

**The child doesn't modify data. It just says "something happened" by calling the callback.**

---

### Props Syntax Variations

**1. Destructuring in parameters (most common):**
```javascript
function Greeting({ name, age }) {
  return <div>{name} is {age} years old</div>;
}
```

**2. Using props object:**
```javascript
function Greeting(props) {
  return <div>{props.name} is {props.age} years old</div>;
}
```

**3. Mixing destructured and props object:**
```javascript
function Greeting({ name, ...otherProps }) {
  return <div>{name} - {otherProps.age}</div>;
}
```

**Best practice:** Use destructuring for clarity about what props are expected.

---

### Default Props

You can provide default values for props using JavaScript default parameters.

**Method 1: Default parameters (modern approach):**
```javascript
function Button({ 
  text = 'Click me',      // Default value
  variant = 'primary',    // Default value
  size = 'medium'         // Default value
}) {
  return (
    <button className={`btn-${variant} btn-${size}`}>
      {text}
    </button>
  );
}

// Usage
<Button />  
// Uses all defaults: text="Click me", variant="primary", size="medium"

<Button text="Submit" />
// Uses text="Submit", other props use defaults

<Button text="Delete" variant="danger" />
// Uses text="Delete", variant="danger", size="medium" (default)
```

**Method 2: Using OR operator:**
```javascript
function Button({ text, variant, size }) {
  const buttonText = text || 'Click me';
  const buttonVariant = variant || 'primary';
  const buttonSize = size || 'medium';
  
  return (
    <button className={`btn-${buttonVariant} btn-${buttonSize}`}>
      {buttonText}
    </button>
  );
}
```

**Method 3: Nullish coalescing (for falsy values):**
```javascript
function Counter({ count }) {
  // If count is 0, we still want to show it (not default to 10)
  const displayCount = count ?? 10;  // Only default if null/undefined
  
  return <div>{displayCount}</div>;
}

<Counter count={0} />  // Shows: 0 (not 10)
<Counter />            // Shows: 10 (default)
```

**Best practice:** Use default parameters in the function signature for clarity.

---

### Props Can Be Any JavaScript Value

Props can be:

**1. Strings:**
```javascript
<Component name="Alice" />
```

**2. Numbers:**
```javascript
<Component age={25} count={0} />
```

**3. Booleans:**
```javascript
<Component isActive={true} />
<Component disabled={false} />

// Shorthand for true
<Component isActive />  // Same as isActive={true}
```

**4. Arrays:**
```javascript
<Component items={[1, 2, 3]} />
<Component users={['Alice', 'Bob']} />
```

**5. Objects:**
```javascript
<Component user={{ name: 'Alice', age: 25 }} />
<Component config={{ theme: 'dark', lang: 'en' }} />
```

**6. Functions:**
```javascript
<Component onClick={() => console.log('clicked')} />
<Component onSubmit={handleSubmit} />
```

**7. JSX elements:**
```javascript
<Component header={<h1>Title</h1>} />
<Component icon={<Icon name="star" />} />
```

**8. Undefined/null (often used for optional props):**
```javascript
<Component optional={undefined} />
<Component data={null} />
```

---

### Special Props

**1. The `children` prop:**

Content between opening and closing tags automatically becomes the `children` prop.

```javascript
function Card({ children }) {
  return <div className="card">{children}</div>;
}

// These are equivalent:
<Card children={<h1>Title</h1>} />
<Card><h1>Title</h1></Card>
```

**2. The `key` prop (for lists):**

Used by React to identify items in lists (we'll cover deeply in Part 6).

```javascript
{items.map(item => (
  <Item key={item.id} data={item} />
))}
```

**3. The `ref` prop:**

Used to access DOM elements directly (covered later).

```javascript
<input ref={inputRef} />
```

---

## 3.2 Passing Data Correctly

### Primitives vs Objects

Understanding how JavaScript passes values is crucial for React.

**Primitives** (string, number, boolean, null, undefined):
- Passed by **value**
- Create a copy
- Changing the copy doesn't affect the original

**Objects** (objects, arrays, functions):
- Passed by **reference**
- Share the same memory location
- Changing the object affects all references

---

### Passing Primitives

Primitives are straightforward - they're copied.

```javascript
function Parent() {
  const age = 25;
  
  return <Child userAge={age} />;
}

function Child({ userAge }) {
  // userAge is a copy of age
  // Even if we could modify it (we can't - props are read-only),
  // it wouldn't affect Parent's age variable
  return <div>{userAge}</div>;
}
```

**Safe and predictable.**

---

### Passing Objects (Be Careful!)

Objects are passed by reference, which can cause issues.

**Problem: Creating new object on every render**

```javascript
// ❌ BAD - Creates new object every render
function Parent() {
  const user = { name: 'Alice', age: 25 };  // New object each time!
  
  return <Child user={user} />;
}

function Child({ user }) {
  return <div>{user.name}</div>;
}
```

**Why this is bad:**
- Every time `Parent` renders, a new `user` object is created
- React sees it as a different object (different reference)
- `Child` re-renders even if values inside haven't changed
- Performance issue in large apps

**✅ GOOD - Create object outside or use state:**

**Option 1: Define outside component:**
```javascript
const user = { name: 'Alice', age: 25 };  // Created once

function Parent() {
  return <Child user={user} />;
}
```

**Option 2: Use state (we'll learn this in Part 4):**
```javascript
function Parent() {
  const [user] = useState({ name: 'Alice', age: 25 });  // Created once
  
  return <Child user={user} />;
}
```

**Option 3: Use useMemo for computed objects (advanced):**
```javascript
function Parent({ firstName, lastName }) {
  const user = useMemo(() => ({
    name: `${firstName} ${lastName}`,
    age: 25
  }), [firstName, lastName]);  // Only recreate if firstName/lastName change
  
  return <Child user={user} />;
}
```

---

### Passing Arrays

Same issue as objects - arrays are passed by reference.

**❌ BAD - Creates new array every render:**
```javascript
function Parent() {
  const items = [1, 2, 3];  // New array each time
  return <Child items={items} />;
}
```

**✅ GOOD - Define outside or use state:**
```javascript
const items = [1, 2, 3];  // Created once

function Parent() {
  return <Child items={items} />;
}
```

---

### Passing Functions

Functions should be stable references to avoid unnecessary re-renders.

**❌ BAD - Creates new function every render:**
```javascript
function Parent() {
  return <Child onClick={() => console.log('clicked')} />;
  // New function created on every Parent render
}
```

**✅ GOOD - Define outside or use useCallback:**

**Option 1: Define outside:**
```javascript
function Parent() {
  const handleClick = () => console.log('clicked');
  // Still creates new function each render, but we'll fix this with useCallback
  
  return <Child onClick={handleClick} />;
}
```

**Option 2: Use useCallback (advanced, covered in Part 9):**
```javascript
function Parent() {
  const handleClick = useCallback(() => {
    console.log('clicked');
  }, []);  // Function created once
  
  return <Child onClick={handleClick} />;
}
```

**For now:** Don't worry too much about this. It only matters for performance optimization in larger apps.

---

### Props Drilling

**Props drilling** is when you pass props through multiple layers of components that don't use them.

**Example:**
```javascript
function App() {
  const user = { name: 'Alice', age: 25 };
  
  return <Dashboard user={user} />;
}

function Dashboard({ user }) {
  // Dashboard doesn't use user, just passes it down
  return (
    <div>
      <Header user={user} />
    </div>
  );
}

function Header({ user }) {
  // Header doesn't use user either, passes it down
  return (
    <header>
      <UserProfile user={user} />
    </header>
  );
}

function UserProfile({ user }) {
  // Finally used here!
  return <div>{user.name}</div>;
}
```

**The problem:**
- `user` prop drills through `Dashboard` → `Header` → `UserProfile`
- `Dashboard` and `Header` don't use it, just pass it along
- Makes code harder to maintain
- If structure changes, you need to update multiple components

---

### Solutions to Props Drilling

**Solution 1: Component composition (moving components around)**

Instead of passing props down, move the component that needs the data closer to where the data is.

**❌ Before (drilling):**
```javascript
function App() {
  const user = { name: 'Alice' };
  return <Dashboard user={user} />;
}

function Dashboard({ user }) {
  return <Header user={user} />;
}

function Header({ user }) {
  return <UserProfile user={user} />;
}
```

**✅ After (composition):**
```javascript
function App() {
  const user = { name: 'Alice' };
  
  return (
    <Dashboard>
      <Header>
        <UserProfile user={user} />
      </Header>
    </Dashboard>
  );
}

function Dashboard({ children }) {
  return <div className="dashboard">{children}</div>;
}

function Header({ children }) {
  return <header>{children}</header>;
}

// No more drilling!
```

**Solution 2: Context API (covered later)**

Context allows you to share data across the component tree without passing props.

```javascript
// Create context
const UserContext = createContext();

function App() {
  const user = { name: 'Alice' };
  
  return (
    <UserContext.Provider value={user}>
      <Dashboard />
    </UserContext.Provider>
  );
}

function UserProfile() {
  const user = useContext(UserContext);  // Access directly!
  return <div>{user.name}</div>;
}

// No drilling through Dashboard and Header
```

**Solution 3: State management libraries (advanced)**

Libraries like Redux, Zustand, or Jotai can help, but they're overkill for simple cases.

**Best practice:** 
- Start with props
- Use composition when possible
- Use Context for truly global data (theme, user, language)
- Only use state management libraries when necessary

---

### Common Mistakes with Props

**Mistake 1: Mutating props**

```javascript
// ❌ NEVER do this
function BadComponent({ user }) {
  user.name = 'Changed';  // Mutating prop!
  return <div>{user.name}</div>;
}

// ✅ Create a new object if you need to modify
function GoodComponent({ user }) {
  const updatedUser = { ...user, name: 'Changed' };
  return <div>{updatedUser.name}</div>;
}
```

**Mistake 2: Forgetting curly braces for non-strings**

```javascript
// ❌ Wrong - passes string "25" not number 25
<Component age="25" />

// ✅ Correct - passes number 25
<Component age={25} />

// ❌ Wrong - passes string "true"
<Component isActive="true" />

// ✅ Correct - passes boolean true
<Component isActive={true} />
// Or shorthand:
<Component isActive />
```

**Mistake 3: Spreading props without knowing what they are**

```javascript
// ❌ Risky - you don't know what props are being passed
function Component(props) {
  return <div {...props} />;
}

// ✅ Better - explicit about what props are accepted
function Component({ className, onClick, children }) {
  return (
    <div className={className} onClick={onClick}>
      {children}
    </div>
  );
}
```

**Mistake 4: Using object literals in JSX**

```javascript
// ❌ Creates new object every render
<Component style={{ color: 'red' }} />

// ✅ Define outside render
const style = { color: 'red' };
<Component style={style} />

// Or if it truly changes each render, it's fine:
<Component style={{ color: isActive ? 'green' : 'red' }} />
```

**Mistake 5: Not validating props (in larger apps)**

```javascript
// ❌ No validation - easy to pass wrong type
function Component({ age }) {
  return <div>{age}</div>;
}

<Component age="twenty-five" />  // Oops, should be number

// ✅ Use TypeScript or PropTypes for validation
// TypeScript:
function Component({ age }: { age: number }) {
  return <div>{age}</div>;
}

// PropTypes (older approach):
import PropTypes from 'prop-types';

Component.propTypes = {
  age: PropTypes.number.isRequired
};
```

---

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

## What's Next?

In **Part 4**, we'll dive deep into **State & Re-rendering**:
- What state actually is
- `useState` deep dive
- How state updates work
- Why state causes re-renders
- State update pitfalls
- Derived state anti-patterns

This is where React really starts to feel powerful!