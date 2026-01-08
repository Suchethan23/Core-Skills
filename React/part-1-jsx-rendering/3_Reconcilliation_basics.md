
# 1.3 Reconciliation Basics

Reconciliation is React's process of figuring out what changed.
The Two Phases:
1. Render Phase (Pure, can be paused)

React calls your component functions
Builds new Virtual DOM tree
Compares new tree vs old tree
Figures out what needs to change
This phase is interruptible (React can pause it)

2. Commit Phase (Synchronous, can't be paused)

React actually updates the real DOM
Runs layout effects
Updates refs
This phase is fast and synchronous


What React Compares
When you update state, React:

Calls your component again (gets new React elements)
Compares element by element with previous render
Looks for differences

Example:
```js 
// First render
<div>
  <h1>Hello</h1>
  <p>Count: 0</p>
</div>

// After state change
<div>
  <h1>Hello</h1>
  <p>Count: 1</p>
</div>
```

React sees:

✅ <div> - same, keep it
✅ <h1>Hello</h1> - same, keep it
⚠️ <p>Count: 1</p> - text changed, update only this

Only the <p> text gets updated in real DOM.

Why React Compares
Updating the real DOM is slow. Creating JavaScript objects is fast.
So React:

Creates new Virtual DOM (fast, it's just JavaScript objects)
Compares Virtual DOM trees (fast, comparing objects)
Updates only necessary parts of real DOM (as few updates as possible)

This is why React is fast!

Element Type Matters
If the element type changes, React destroys the old one and creates new:
```js
// Before
<div>
  <ComponentA />
</div>

// After
<div>
  <ComponentB />  // Different type!
</div>
```

React will:

Unmount <ComponentA /> (destroy it completely)
Mount <ComponentB /> (create from scratch)

But if type stays same:
```js
// Before
<button className="primary">Click</button>

// After
<button className="secondary">Click</button>
```

React will:

Keep the same <button> DOM element
Just update the className attribute


🎯 Quick Check: Part 1 Understanding
Answer these to test your grasp:

What does JSX actually compile to?
Why can't we use class in JSX?
What's the difference between a render and a DOM update?
If a component's state changes from 5 to 5 (same value), does the DOM update?
Transform this JSX to React.createElement():

jsx   <div className="box">
     <span>Hello</span>
   </div>