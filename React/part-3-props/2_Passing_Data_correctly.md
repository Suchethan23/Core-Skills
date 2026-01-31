
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

