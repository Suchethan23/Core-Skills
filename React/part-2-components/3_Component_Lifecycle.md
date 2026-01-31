
## 2.4 Component Lifecycle (Conceptual)

### The Three Lifecycle Stages

Even with function components, there's still a **lifecycle** - stages components go through.

**1. Mount (Birth)**
- Component is created
- Added to the DOM for the first time
- Initialization happens
- Runs **once** when component appears

**2. Update (Life)**
- Props or state change
- Component re-renders
- Can happen **many times**

**3. Unmount (Death)**
- Component is removed from DOM
- Cleanup happens
- Runs **once** when component disappears

---

### Visual Timeline

```
User opens page
    ↓
MOUNT ━━━━━━━━━━━━━━ Component appears
    ↓
UPDATE ━━━━━━━━━━━━━ State changes (re-render)
    ↓
UPDATE ━━━━━━━━━━━━━ Props change (re-render)
    ↓
UPDATE ━━━━━━━━━━━━━ Parent re-renders
    ↓
UNMOUNT ━━━━━━━━━━━━ User navigates away
```

---

### Example Scenario

```javascript
function ChatRoom({ roomId }) {
  // MOUNT: User joins chat room
  // - Connect to server
  // - Subscribe to messages
  
  // UPDATE: roomId prop changes
  // - Disconnect from old room
  // - Connect to new room
  
  // UNMOUNT: User leaves chat
  // - Disconnect from server
  // - Clean up subscription
  
  return <div>Chat Room: {roomId}</div>;
}
```

**User journey:**
1. Opens app → `ChatRoom` mounts (connects to "general" room)
2. Clicks "Sports" → props change → updates (disconnects from "general", connects to "sports")
3. Clicks "Music" → props change → updates (disconnects from "sports", connects to "music")
4. Closes app → `ChatRoom` unmounts (disconnects from "music")

---

### Lifecycle Without Classes

In function components, lifecycle is handled with **hooks**.

**Quick preview (we'll learn these deeply in Part 7):**

**On Mount:**
```javascript
function Component() {
  useEffect(() => {
    console.log('Component mounted!');
    // Run once when component first appears
  }, []);  // Empty array = run only on mount
  
  return <div>Hello</div>;
}
```

**On Update:**
```javascript
function Component({ userId }) {
  useEffect(() => {
    console.log('userId changed to:', userId);
    // Run every time userId changes
  }, [userId]);  // Run when userId changes
  
  return <div>User: {userId}</div>;
}
```

**On Unmount (Cleanup):**
```javascript
function Component() {
  useEffect(() => {
    const timer = setInterval(() => console.log('tick'), 1000);
    
    return () => {
      clearInterval(timer);  // Cleanup when unmounting
      console.log('Component unmounted!');
    };
  }, []);
  
  return <div>Timer running</div>;
}
```

**Don't worry about the syntax yet!** Just understand the **concept** of these three stages.

---

### Why Lifecycle Matters

Different actions belong to different stages:

**On Mount:**
- Fetch data from API
- Set up event listeners
- Start timers
- Initialize third-party libraries
- Focus an input field

**On Update:**
- Fetch new data when props change
- Update based on new state
- Sync with external systems
- Recalculate derived values

**On Unmount:**
- Clean up timers
- Cancel pending API requests
- Remove event listeners
- Close connections
- Save data before leaving

---

### Common Mistakes

**❌ Doing mount logic on every render:**
```javascript
function BadComponent() {
  // This runs on EVERY render, not just mount!
  fetch('/api/data');
  
  return <div>Data</div>;
}
```

**✅ Correct - only on mount:**
```javascript
function GoodComponent() {
  useEffect(() => {
    fetch('/api/data');
  }, []);  // Empty array = only on mount
  
  return <div>Data</div>;
}
```

---

**❌ Not cleaning up:**
```javascript
function BadComponent() {
  useEffect(() => {
    const timer = setInterval(() => console.log('tick'), 1000);
    // Missing cleanup! Timer keeps running after unmount
  }, []);
  
  return <div>Timer</div>;
}
```

**✅ Correct - cleanup:**
```javascript
function GoodComponent() {
  useEffect(() => {
    const timer = setInterval(() => console.log('tick'), 1000);
    
    return () => {
      clearInterval(timer);  // Cleanup!
    };
  }, []);
  
  return <div>Timer</div>;
}
```

---

### Lifecycle in Class Components (Reference Only)

You don't need to learn these, but for reference if you see old code:

```javascript
class OldComponent extends React.Component {
  componentDidMount() {
    // After component added to DOM (mount)
  }
  
  componentDidUpdate(prevProps, prevState) {
    // After component re-renders (update)
  }
  
  componentWillUnmount() {
    // Before component removed (unmount)
  }
  
  render() {
    return <div>Content</div>;
  }
}
```

**Modern equivalent with hooks is simpler:**
```javascript
function ModernComponent() {
  useEffect(() => {
    // componentDidMount logic
    
    return () => {
      // componentWillUnmount logic
    };
  }, []);
  
  useEffect(() => {
    // componentDidUpdate logic
  });
  
  return <div>Content</div>;
}
```

---

## Summary: Part 2

### Key Concepts

**1. Components = Functions**
- JavaScript functions that return JSX
- Must start with capital letter
- Receive props as input
- Should be pure (no side effects in render)

**2. Component Purity**
- Same props → Same output
- No side effects during render
- Don't modify external variables
- Don't mutate props
- Use `useEffect` for side effects

**3. Composition**
- Build complex UIs from simple components
- Extract repeated patterns
- Use props for variations
- `children` prop for flexible content
- Single responsibility principle

**4. Lifecycle**
- **Mount** - Component appears (once)
- **Update** - Component re-renders (many times)
- **Unmount** - Component disappears (once)
- Managed with hooks in function components

---

### Mental Models

**Component = UI Function**
```
props → [Component] → JSX
```

**Composition = LEGO Blocks**
```
Small components + Small components = Complex UI
```

**Lifecycle = Birth → Life → Death**
```
Mount → Update → Update → Update → Unmount
```

---

