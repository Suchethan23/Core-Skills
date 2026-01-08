# 1.3 Reconciliation & Diffing (How React Decides What to Update)

Reconciliation is the process React uses to decide:
> **What actually changed between renders?**

Rendering tells React *what the UI should look like*.  
Reconciliation tells React *what needs to be updated in the real DOM*.

---

## Why Reconciliation Exists

Updating the real DOM is expensive.

If React updated the DOM every time a component rendered:
- Performance would be terrible
- Large apps would be unusable

Instead, React:
1. Creates a **Virtual DOM** (plain JavaScript objects)
2. Compares the new tree with the previous one
3. Applies **only the minimum required changes** to the real DOM

This comparison process is called **diffing**.  
The overall update strategy is called **reconciliation**.

---

## Virtual DOM (Clarified)

The Virtual DOM is:
- A tree of JavaScript objects
- Created from `React.createElement` calls
- A description of the UI, not the UI itself

Example Virtual DOM node:

```js
{
  type: 'p',
  props: {
    children: 'Count: 1'
  }
}
```

**Important:** React never compares the real DOM directly. It compares Virtual DOM trees.

---

## The Two Phases of Reconciliation

React updates UI in two distinct phases.

### 1. Render Phase (Pure & Interruptible)

During the render phase, React:
- Calls component functions
- Builds a new Virtual DOM tree
- Compares it with the previous tree
- Figures out what might need to change

**Important characteristics:**
- No DOM updates happen here
- This phase is pure
- React can pause, resume, or restart this phase

**This is why components must not:**
- Modify DOM
- Cause side effects
- Depend on timing

### 2. Commit Phase (Synchronous & Final)

During the commit phase, React:
- Applies actual DOM updates
- Adds / removes DOM nodes
- Updates attributes
- Runs effects and refs

**Important characteristics:**
- DOM is mutated here
- Cannot be interrupted
- Kept as short as possible

---

## What React Compares

React compares element by element between renders.

**Example:**

Previous Render:
```jsx
<div>
  <h1>Hello</h1>
  <p>Count: 0</p>
</div>
```

Next Render:
```jsx
<div>
  <h1>Hello</h1>
  <p>Count: 1</p>
</div>
```

React's comparison result:
- `<div>` → same type → keep
- `<h1>` → same type & content → keep
- `<p>` → same type, different text → update text only

**Result:** Only the text inside `<p>` changes in the real DOM.

---

## Element Type Matters (Very Important)

React uses element type as a primary comparison signal.

### Same Type → Update

```jsx
// Before
<button className="primary">Click</button>

// After
<button className="secondary">Click</button>
```

React:
- Keeps the same `<button>` DOM node
- Updates only the `className`

### Different Type → Replace

```jsx
// Before
<ComponentA />

// After
<ComponentB />
```

React:
- Unmounts `ComponentA`
- Destroys its DOM and state
- Mounts `ComponentB` from scratch

Even if their output looks similar, type difference forces replacement.

---

## Why Keys Are Needed (Preview)

When rendering lists, React must match elements correctly.

**Without keys:**
- React matches by position
- Reordering causes incorrect updates

**With keys:**
- React matches by identity
- Reordering is safe and efficient

(We will cover keys deeply in Part 6.)

---

## Reconciliation Is an Optimization Heuristic

**Important truth:** React does NOT perform a perfect tree diff.

Instead:
- It assumes similar trees between renders
- It uses heuristics for speed
- It prioritizes developer experience + performance balance

This is why:
- Component structure should stay stable
- Conditional rendering must be handled carefully

---

## Common Misconceptions

❌ React updates the entire DOM on every render  
❌ Virtual DOM is faster than real DOM  
❌ Reconciliation compares DOM nodes directly

✅ React updates only what changed  
✅ Virtual DOM allows efficient comparison  
✅ Real DOM updates are minimized

---

## Mental Model Summary

```
JSX
  ↓
React elements (Virtual DOM)
  ↓
Render Phase (compare)
  ↓
Commit Phase (update DOM)
```

**Rendering** decides what UI should be.  
**Reconciliation** decides what DOM needs to change.

---

## Why Components Must Be Pure

During the **Render Phase**, React might:
- Call your component multiple times
- Pause and restart rendering
- Throw away the work and start over

This is why your component functions should NOT:

❌ Modify DOM directly  
❌ Make API calls (without useEffect)  
❌ Set timers  
❌ Depend on timing/order

```javascript
// ❌ BAD - side effect in render
function BadComponent() {
  document.title = 'Hello';  // Don't do this!
  return <div>Content</div>;
}

// ✅ GOOD - pure component
function GoodComponent() {
  return <div>Content</div>;
}
```

---

## Key Takeaways

1. **Reconciliation = React's diffing algorithm** that minimizes DOM updates
2. **Two phases:** Render (pure, interruptible) → Commit (synchronous, final)
3. **Element type changes = complete replacement**, not update
4. **Virtual DOM is just JavaScript objects**, not actual DOM
5. **Components must be pure** because React can call them multiple times
6. **React uses heuristics**, not perfect tree comparison (for performance)