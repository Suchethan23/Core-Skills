# PART 2 – Components (Core of React)

## 2.1 Function Components

### What is a Component?

Components are the fundamental building blocks of React.

**Everything you see in a React app is made up of components.**

Instead of thinking in pages or screens, React encourages thinking in **small, reusable UI pieces**.

A component is simply a JavaScript function that:
1. Receives input (props)
2. Returns JSX
3. Describes what should appear on screen

**Example:**
```jsx
function Welcome() {
  return <h1>Hello, World</h1>;
}
```

React uses this function to render UI.

---

### Components Are Just Functions

A React component is not special. It is simply a function that returns React elements.

```javascript
function Greeting({ name }) {
  return <h1>Hello, {name}</h1>;
}
```

**Input:**
```jsx
<Greeting name="Alice" />
```

**Output:**
```jsx
<h1>Hello, Alice</h1>
```

**Same input → Same output**

This predictability is important.

---

### Why Components Exist

Before React, UI code was often:
- Hard to reuse
- Difficult to maintain
- Tightly coupled

Components solve this by enabling:
- **Reusability** - Write once, use everywhere
- **Separation of concerns** - Each component has one job
- **Easier debugging** - Isolate problems to specific components
- **Independent testing** - Test components in isolation

---

### Thinking in Components

A UI can be broken into smaller pieces.

**Example structure:**
```
App
├── Header
│   ├── Logo
│   └── Navigation
├── MainContent
│   ├── Post
│   ├── Post
│   └── Post
└── Footer
```

Each component:
- Has one responsibility
- Can be reused
- Can evolve independently

**Real-world analogy:** Like organizing a house into rooms. Each room (component) has a specific purpose, but together they make a complete house (app).

---

### Components Must Be Capitalized

React treats lowercase tags as HTML elements.

```jsx
<Header />   // Component (React calls the Header function)
<header />   // HTML element (creates <header> DOM element)
```

**Component names must start with a capital letter.**

**Why?** This is how React distinguishes between your components and native HTML tags.

```jsx
// ✅ Correct
function UserProfile() {
  return <div>Profile</div>;
}

// ❌ Wrong - React thinks this is an HTML tag
function userProfile() {
  return <div>Profile</div>;
}
```

---

### Props: Component Inputs

Components receive data through **props** (short for "properties").

```javascript
function User({ name, age }) {
  return (
    <p>
      {name} is {age} years old
    </p>
  );
}
```

**Usage:**
```jsx
<User name="Alice" age={25} />
// Output: Alice is 25 years old

<User name="Bob" age={30} />
// Output: Bob is 30 years old
```

Props allow components to be **dynamic and reusable**.

Same component, different data = different output.

---

### Components Can Be Nested

Components can use other components.

```javascript
function Header() {
  return <header>My App Header</header>;
}

function MainContent() {
  return <main>Main Content Here</main>;
}

function Footer() {
  return <footer>Footer Info</footer>;
}

function App() {
  return (
    <div>
      <Header />
      <MainContent />
      <Footer />
    </div>
  );
}
```

This creates a **tree structure** (component hierarchy).

**Rendering flow:**
1. React calls `App()`
2. `App` returns JSX containing `<Header />`, `<MainContent />`, `<Footer />`
3. React calls `Header()`, `MainContent()`, `Footer()`
4. Each returns their JSX
5. React builds the complete UI tree

---

### One Responsibility Principle

A good component:
- Does **one thing** well
- Is not too large
- Can be understood quickly

**Large components should be split.**

**❌ Bad - component doing too much:**
```javascript
function UserDashboard({ user }) {
  return (
    <div>
      {/* Profile info */}
      <div>
        <img src={user.avatar} />
        <h2>{user.name}</h2>
        <p>{user.bio}</p>
      </div>
      
      {/* Posts list */}
      <div>
        {user.posts.map(post => (
          <div key={post.id}>
            <h3>{post.title}</h3>
            <p>{post.content}</p>
          </div>
        ))}
      </div>
      
      {/* Settings */}
      <div>
        <button>Edit Profile</button>
        <button>Change Password</button>
      </div>
    </div>
  );
}
```

**✅ Good - split into focused components:**
```javascript
function UserProfile({ user }) {
  return (
    <div>
      <img src={user.avatar} />
      <h2>{user.name}</h2>
      <p>{user.bio}</p>
    </div>
  );
}

function Post({ post }) {
  return (
    <div>
      <h3>{post.title}</h3>
      <p>{post.content}</p>
    </div>
  );
}

function PostsList({ posts }) {
  return (
    <div>
      {posts.map(post => (
        <Post key={post.id} post={post} />
      ))}
    </div>
  );
}

function UserSettings() {
  return (
    <div>
      <button>Edit Profile</button>
      <button>Change Password</button>
    </div>
  );
}

function UserDashboard({ user }) {
  return (
    <div>
      <UserProfile user={user} />
      <PostsList posts={user.posts} />
      <UserSettings />
    </div>
  );
}
```

**Benefits:**
- Each component is easier to understand
- Components can be reused elsewhere
- Easier to test each piece
- Easier to modify without breaking other parts

---

### Components Describe UI, Not Behavior

Components define:
- **Structure** - What elements exist
- **Layout** - How they're arranged
- **Data representation** - What data is displayed

Behavior is added through:
- **State** - Component memory (learned in Part 4)
- **Event handlers** - Responding to user actions
- **Effects** - Side effects like API calls (learned in Part 7)

**Example - Structure only:**
```javascript
function Button({ text }) {
  return <button>{text}</button>;
}
```

**Example - Structure + Behavior:**
```javascript
function Button({ text, onClick }) {
  return <button onClick={onClick}>{text}</button>;
}

// Usage
<Button 
  text="Click me" 
  onClick={() => alert('Clicked!')} 
/>
```

---

### Mental Model

Think of components as:

> **"Reusable UI functions that return what the screen should look like."**

**Not classes. Not templates. Just functions.**

**The Component Mental Model:**
```
Input (props) → Function (component) → Output (JSX)
```

Same as:
```
Input (data) → Function (process) → Output (result)
```

**Example:**
```javascript
// Regular function
function add(a, b) {
  return a + b;
}

// React component
function Greeting({ name }) {
  return <h1>Hello, {name}</h1>;
}

// Both are just functions!
```

---

## 2.2 Component Purity

### What is a Pure Component?

A **pure component** is a function where:
- Same inputs (props) **always** produce same output (JSX)
- No side effects during render
- Doesn't modify variables outside its scope

**Pure function example:**
```javascript
function add(a, b) {
  return a + b;
}

add(2, 3)  // Always returns 5
add(2, 3)  // Always returns 5
add(2, 3)  // Always returns 5
```

**Pure component example:**
```javascript
function Greeting({ name }) {
  return <h1>Hello, {name}</h1>;
}

// <Greeting name="Alice" /> always returns <h1>Hello, Alice</h1>
// <Greeting name="Alice" /> always returns <h1>Hello, Alice</h1>
```

---

### Why Purity Matters in React

React's rendering can be:
- **Called multiple times** - React might render your component several times before committing to DOM
- **Paused and resumed** - React might start rendering, pause, then continue later
- **Discarded** - React might throw away a render and start over

**If your component has side effects, these behaviors cause bugs.**

**Example of the problem:**
```javascript
let callCount = 0;

function ImpureCounter() {
  callCount++;  // Side effect!
  console.log('Rendered:', callCount);
  return <div>Component</div>;
}

// In development (Strict Mode), React renders twice
// Console output:
// Rendered: 1
// Rendered: 2
// You expected it once, but it happened twice!
```

---

### Examples of Impure Components

**❌ Impure - Modifying external variable:**
```javascript
let counter = 0;

function BadCounter() {
  counter++;  // Side effect! Modifying external state
  return <div>{counter}</div>;
}

// Problem: Each render changes global state
// React might call this multiple times unpredictably
```

**❌ Impure - Direct DOM manipulation:**
```javascript
function BadComponent() {
  document.title = "New Title";  // Side effect! Don't do this in render
  return <div>Content</div>;
}

// Problem: Title changes every render
// React might render many times
```

**❌ Impure - API calls in render:**
```javascript
function BadComponent() {
  fetch('/api/data');  // Side effect! Network request on every render
  return <div>Content</div>;
}

// Problem: API called every render
// Could make hundreds of unnecessary requests
```

**❌ Impure - Random values:**
```javascript
function BadComponent() {
  const id = Math.random();  // Non-deterministic
  return <div id={id}>Content</div>;
}

// Problem: Same props, different output
// Breaks React's reconciliation
```

**❌ Impure - Mutating props:**
```javascript
function BadComponent({ items }) {
  items.push('new item');  // Mutating prop! Never do this
  return <div>{items.length} items</div>;
}

// Problem: Modifies data that doesn't belong to this component
// Parent component's data gets corrupted
```

---

### What IS Allowed in Pure Components

**✅ Reading props:**
```javascript
function Component({ name, age }) {
  return <div>{name} is {age} years old</div>;
}
```

**✅ Reading state (we'll learn this in Part 4):**
```javascript
function Component() {
  const [count, setCount] = useState(0);
  return <div>{count}</div>;
}
```

**✅ Creating local variables:**
```javascript
function Component({ firstName, lastName }) {
  const fullName = `${firstName} ${lastName}`;  // Local variable - fine!
  return <div>{fullName}</div>;
}
```

**✅ Calculations and transformations:**
```javascript
function Component({ items }) {
  const total = items.reduce((sum, item) => sum + item.price, 0);
  const average = total / items.length;
  return <div>Average: ${average}</div>;
}
```

**✅ Conditional logic:**
```javascript
function Component({ isLoggedIn, user }) {
  const message = isLoggedIn 
    ? `Welcome back, ${user.name}!` 
    : 'Please log in';
  
  return <div>{message}</div>;
}
```

**✅ Array operations that don't mutate:**
```javascript
function Component({ items }) {
  // ✅ map, filter, reduce create NEW arrays
  const filtered = items.filter(item => item.active);
  const mapped = items.map(item => item.name);
  
  return <div>{filtered.length} active items</div>;
}
```

---

### The Rule: Render = Pure Calculation

**Think of rendering as a pure mathematical function:**

```
UI = f(props, state)
```

Same `props` and `state` → Same `UI`

**During render:**
- ✅ Read props
- ✅ Read state
- ✅ Calculate values
- ✅ Return JSX

**NOT during render:**
- ❌ Modify external variables
- ❌ Make API calls
- ❌ Modify DOM
- ❌ Set up subscriptions
- ❌ Start timers

**For side effects, use `useEffect`** (we'll learn this in Part 7).

---

### Strict Mode Helps Catch Impurity

React has a `<StrictMode>` component that helps catch bugs:

```javascript
import { StrictMode } from 'react';

<StrictMode>
  <App />
</StrictMode>
```

**In development, Strict Mode:**
- Renders components twice
- Helps you find accidental side effects
- Only in development (doesn't affect production)

**Example:**
```javascript
let count = 0;

function Component() {
  count++;  // Impure!
  console.log('Count:', count);
  return <div>Component</div>;
}

// Without StrictMode: Count: 1
// With StrictMode: Count: 1, Count: 2
// The double render exposes the side effect!
```

---

### Mental Model for Purity

**Your component is like a recipe:**
- Given the same ingredients (props) → Always produces the same dish (JSX)
- Doesn't affect the kitchen while cooking (no side effects)
- Doesn't change the ingredients you were given (don't mutate props)

**Example:**
```javascript
// Pure component = Pure recipe
function Recipe({ ingredients }) {
  // Read ingredients ✅
  const dish = combine(ingredients);
  
  // Return result ✅
  return <div>{dish}</div>;
  
  // Didn't:
  // - Change the ingredients ✅
  // - Affect anything outside ✅
  // - Make the result unpredictable ✅
}
```

---

