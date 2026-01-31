# PART 6 – Conditional Rendering & Lists

Conditional rendering and lists are fundamental to building dynamic UIs. You'll use these patterns in almost every React component.

---

## 6.1 Conditional Rendering Patterns

### What is Conditional Rendering?

**Showing different UI based on conditions.**

Just like `if` statements in JavaScript, but in your JSX.

**Simple example:**
```javascript
function Greeting({ isLoggedIn }) {
  if (isLoggedIn) {
    return <h1>Welcome back!</h1>;
  }
  return <h1>Please sign in.</h1>;
}
```

---

### Pattern 1: if/else (Traditional)

**Using regular if statements:**

```javascript
function Component({ isLoading, error, data }) {
  if (isLoading) {
    return <div>Loading...</div>;
  }
  
  if (error) {
    return <div>Error: {error.message}</div>;
  }
  
  return <div>Data: {data}</div>;
}
```

**Pros:**
- ✅ Easy to read
- ✅ Good for multiple conditions
- ✅ Can return early

**Cons:**
- ⚠️ Can't use inline in JSX
- ⚠️ More verbose

---

### Pattern 2: Ternary Operator (`? :`)

**For choosing between two options:**

```javascript
function Greeting({ isLoggedIn }) {
  return (
    <div>
      {isLoggedIn ? (
        <h1>Welcome back!</h1>
      ) : (
        <h1>Please sign in.</h1>
      )}
    </div>
  );
}
```

**Common use cases:**

**Toggle text:**
```javascript
function Button({ isActive }) {
  return (
    <button>
      {isActive ? 'Active' : 'Inactive'}
    </button>
  );
}
```

**Toggle class:**
```javascript
function Box({ isSelected }) {
  return (
    <div className={isSelected ? 'box selected' : 'box'}>
      Content
    </div>
  );
}
```

**Show different components:**
```javascript
function Dashboard({ userRole }) {
  return (
    <div>
      {userRole === 'admin' ? (
        <AdminPanel />
      ) : (
        <UserPanel />
      )}
    </div>
  );
}
```

**Pros:**
- ✅ Inline in JSX
- ✅ Concise for two options
- ✅ Works well for simple conditions

**Cons:**
- ⚠️ Can become hard to read if nested
- ⚠️ Only for two options (true/false)

---

### Pattern 3: Logical AND (`&&`)

**For showing something or nothing:**

```javascript
function Notification({ hasNewMessages, messageCount }) {
  return (
    <div>
      {hasNewMessages && (
        <div>You have {messageCount} new messages!</div>
      )}
    </div>
  );
}
```

**How it works:**
```javascript
true && <Component />   // Renders <Component />
false && <Component />  // Renders nothing
```

**Common use cases:**

**Conditional component:**
```javascript
function App({ isLoggedIn }) {
  return (
    <div>
      {isLoggedIn && <Dashboard />}
    </div>
  );
}
```

**Conditional element:**
```javascript
function Profile({ user }) {
  return (
    <div>
      <h1>{user.name}</h1>
      {user.isPro && <span className="badge">PRO</span>}
    </div>
  );
}
```

**Multiple conditions:**
```javascript
function Post({ post, isAuthor, isAdmin }) {
  return (
    <div>
      <h2>{post.title}</h2>
      {(isAuthor || isAdmin) && (
        <button>Delete</button>
      )}
    </div>
  );
}
```

**Pros:**
- ✅ Very concise
- ✅ Perfect for "show or hide"
- ✅ Inline in JSX

**Cons:**
- ⚠️ Be careful with falsy values (see pitfalls below)

---

### Pattern 4: Early Returns

**Return early from the component function:**

```javascript
function UserProfile({ user }) {
  // Early return for loading
  if (!user) {
    return <div>Loading...</div>;
  }
  
  // Early return for error
  if (user.isBlocked) {
    return <div>This user is blocked.</div>;
  }
  
  // Main render
  return (
    <div>
      <h1>{user.name}</h1>
      <p>{user.bio}</p>
    </div>
  );
}
```

**Why use early returns?**

**Without early returns (nested):**
```javascript
function Component({ data, isLoading, error }) {
  return (
    <div>
      {isLoading ? (
        <div>Loading...</div>
      ) : error ? (
        <div>Error!</div>
      ) : (
        <div>
          {data ? (
            <div>Content: {data}</div>
          ) : (
            <div>No data</div>
          )}
        </div>
      )}
    </div>
  );
}
```

**With early returns (cleaner):**
```javascript
function Component({ data, isLoading, error }) {
  if (isLoading) {
    return <div>Loading...</div>;
  }
  
  if (error) {
    return <div>Error!</div>;
  }
  
  if (!data) {
    return <div>No data</div>;
  }
  
  return <div>Content: {data}</div>;
}
```

**Pros:**
- ✅ Reduces nesting
- ✅ Easier to read
- ✅ Good for guard clauses
- ✅ Handles edge cases first

**Cons:**
- ⚠️ Multiple return statements
- ⚠️ Can't use inline

---

### Pattern 5: Switch Statement

**For multiple conditions:**

```javascript
function StatusMessage({ status }) {
  switch (status) {
    case 'loading':
      return <div>Loading...</div>;
    case 'error':
      return <div>Error occurred</div>;
    case 'success':
      return <div>Success!</div>;
    case 'idle':
      return <div>Ready to start</div>;
    default:
      return <div>Unknown status</div>;
  }
}
```

**Or with object mapping:**
```javascript
function StatusMessage({ status }) {
  const messages = {
    loading: <div>Loading...</div>,
    error: <div>Error occurred</div>,
    success: <div>Success!</div>,
    idle: <div>Ready to start</div>
  };
  
  return messages[status] || <div>Unknown status</div>;
}
```

---

### Pattern 6: Immediately Invoked Function Expression (IIFE)

**For complex logic inline:**

```javascript
function Component({ userRole }) {
  return (
    <div>
      {(() => {
        if (userRole === 'admin') {
          return <AdminPanel />;
        } else if (userRole === 'moderator') {
          return <ModeratorPanel />;
        } else {
          return <UserPanel />;
        }
      })()}
    </div>
  );
}
```

**Usually not recommended** - use early returns or extract to a separate function instead.

---

### Conditional Rendering Pitfalls

**Pitfall 1: Falsy values with `&&`**

**Problem:**
```javascript
function Component({ count }) {
  return (
    <div>
      {count && <div>You have {count} items</div>}
    </div>
  );
}

<Component count={0} />
// Renders: 0
// Not what you wanted!
```

**Why?** 
- `0 && <div>...` evaluates to `0`
- React renders `0` as text
- You see "0" on screen

**Solutions:**

**Option 1: Explicit boolean conversion:**
```javascript
{Boolean(count) && <div>You have {count} items</div>}
// or
{!!count && <div>You have {count} items</div>}
```

**Option 2: Explicit comparison:**
```javascript
{count > 0 && <div>You have {count} items</div>}
```

**Option 3: Use ternary:**
```javascript
{count ? <div>You have {count} items</div> : null}
```

---

**Pitfall 2: Returning undefined**

```javascript
// ❌ BAD - returns undefined if condition is false
function Component({ show }) {
  return show && <div>Content</div>;
}

// ✅ GOOD - always returns JSX
function Component({ show }) {
  if (!show) {
    return null;
  }
  return <div>Content</div>;
}

// ✅ ALSO GOOD
function Component({ show }) {
  return show ? <div>Content</div> : null;
}
```

**React expects components to return:**
- JSX elements
- `null` (renders nothing)
- Strings, numbers
- Arrays of the above

**Not undefined!**

---

**Pitfall 3: String concatenation in className**

```javascript
// ❌ Unnecessary ternary
<div className={isActive ? 'box active' : 'box'}>

// ✅ Better - template literal
<div className={`box ${isActive ? 'active' : ''}`}>

// ✅ Or using a library like classnames
<div className={classNames('box', { active: isActive })}>
```

---

**Pitfall 4: Nested ternaries**

```javascript
// ❌ Hard to read
{isLoading ? (
  <Spinner />
) : error ? (
  <Error />
) : data ? (
  <Content data={data} />
) : (
  <Empty />
)}

// ✅ Better - early returns
if (isLoading) return <Spinner />;
if (error) return <Error />;
if (!data) return <Empty />;
return <Content data={data} />;
```

---

### Best Practices

**1. Choose the right pattern for the situation:**

| Situation | Best Pattern |
|-----------|-------------|
| Show/hide one thing | `&&` |
| Choose between two things | Ternary |
| Multiple conditions | Early returns or switch |
| Guard clauses | Early returns |
| Complex inline logic | Extract to function |

**2. Keep it readable:**
```javascript
// ❌ Too complex
{isLoggedIn && user && user.isPro && user.isActive && !user.isBanned && <Feature />}

// ✅ Better - extract logic
const canAccessFeature = isLoggedIn && user?.isPro && user?.isActive && !user?.isBanned;
{canAccessFeature && <Feature />}
```

**3. Avoid inline functions when possible:**
```javascript
// ❌ Inline logic
{(() => {
  // 20 lines of logic
  return <Component />;
})()}

// ✅ Extract to function
function renderContent() {
  // 20 lines of logic
  return <Component />;
}

return <div>{renderContent()}</div>;
```

---
