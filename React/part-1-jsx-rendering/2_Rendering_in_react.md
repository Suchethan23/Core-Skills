
# 1.2 Rendering in React

Now that you understand JSX is just JavaScript objects, let's see how React turns those objects into actual DOM elements on the page.

Root Rendering (createRoot)
Every React app starts with a root:
``` js // index.js or main.js
import { createRoot } from 'react-dom/client';
import App from './App';

// 1. Get the HTML element where React will live
const rootElement = document.getElementById('root');

// 2. Create a React root
const root = createRoot(rootElement);

// 3. Render your app
root.render(<App />);
```

**What happens:**

React takes control of <div id="root"></div>
Everything inside will be managed by React
React renders your <App /> component tree inside

In your HTML file:
html<body>
  <div id="root"></div>
  <!-- React will fill this -->
</body>

Initial Render vs Re-render
Initial Render (First Time):
```js
 root.render(<App />);
```
React calls your App component function
Gets back React elements (JavaScript objects)
Creates real DOM elements
Puts them on the page

Re-render (State Changes):
```js
 function App() {
  const [count, setCount] = useState(0);
  
  return (
    <div>
      <p>{count}</p>
      <button onClick={() => setCount(count + 1)}>+</button>
    </div>
  );
}
```

When you click the button:

setCount is called with new value
React says "this component needs to update"
React calls App() function again
Gets new React elements with new count
Compares new vs old
Updates only what changed in real DOM

Key insight: Your component function runs multiple times. Every state change triggers another run.

What Triggers a Render?
A component re-renders when:

Its state changes

```js 

const [count, setCount] = useState(0);
setCount(1);  // Triggers re-render
``` 

Its props change

```js 
<Child name={userName} />
// If userName changes in parent, Child re-renders
```

Its parent re-renders

```js 
function Parent() {
  const [count, setCount] = useState(0);
  
  return (
    <div>
      <button onClick={() => setCount(count + 1)}>+</button>
      <Child />  {/* Child re-renders when Parent does */}
    </div>
  );
}
```

**Important:** Rendering doesn't mean the DOM actually changes. React might render and realize nothing needs updating.


**Render ≠ DOM Update**

This is **crucial** to understand:

Render = Run the component function, get new JSX
DOM Update = Actually change what's on the page
Example:

```js 
function App() {
  const [count, setCount] = useState(0);
  
  console.log('Component rendered!');  // This logs on EVERY render
  
  return <div>{count}</div>;
}
``` 

If you click a button that calls `setCount(0)` again (same value):
- ✅ Component **renders** (function runs, console.log happens)
- ❌ DOM doesn't **update** (React sees value didn't change)

**The Process:**

1. State changes
2. React renders (runs your function)
3. React compares new output vs old output
4. If different → update DOM
   If same → do nothing
