# 1.1 What JSX Really Is

## First, the truth bomb: JSX is NOT HTML.
It looks like HTML, but it's actually JavaScript in disguise.
When you write this:
```js
const element = <h1>Hello, world!</h1>;
```

Before React can use it, it gets transformed into:

```js 
const element = React.createElement('h1', null, 'Hello, world!');
```
**JSX is syntactic sugar**. It's a prettier way to write React.createElement() calls.

## The Transformation Process
Let's see more examples of JSX → JavaScript transformation:
**Example 1: Simple element**
```js 
<div>Hello</div>
```
**Becomes:**

```js
React.createElement('div', null, 'Hello')
```

**Example 2: With props**

```js 
<button className="primary" disabled>Click me</button>
```
**Becomes:**

```js
React.createElement(
  'button',
  { className: 'primary', disabled: true },
  'Click me'
)
```

**Example 3: Nested elements**

```js
<div>
  <h1>Title</h1>
  <p>Content</p>
</div>
```
**Becomes:**

```js
React.createElement(
  'div',
  null,
  React.createElement('h1', null, 'Title'),
  React.createElement('p', null, 'Content')
)
```

**Example 4: Component**
```js
<MyComponent name="Alice" age={25} />

```
**Becomes:**
```js
React.createElement(MyComponent, { name: 'Alice', age: 25 })
```
**Understanding React.createElement()**
The signature is:
```js
React.createElement(type, props, ...children)
```

**type:** String for HTML elements ('div', 'button') or Component function/class
**props:** Object with properties (or null)
**children:** Everything inside the element

**What it returns:**A plain JavaScript object called a "React element":

```js
{
  type: 'div',
  props: {
    className: 'container',
    children: 'Hello'
  },
  key: null,
  ref: null,
  // ... other internal properties
}
```
This object is a description of what you want to see on screen. It's not a real DOM element yet!

## Why This Matters
### 1. JSX is optional
You could write React without JSX:
```js 
// Without JSX
function App() {
  return React.createElement(
    'div',
    { className: 'app' },
    React.createElement('h1', null, 'Hello'),
    React.createElement('p', null, 'Welcome')
  );
}

// With JSX (much nicer!)
function App() {
  return (
    <div className="app">
      <h1>Hello</h1>
      <p>Welcome</p>
    </div>
  );
}
```

### 2. JSX is JavaScript
This means you can use JavaScript expressions inside:
```js 
const name = 'Alice';
const element = <h1>Hello, {name}!</h1>;

// Inside {}, you can put ANY JavaScript expression:
<div>{2 + 2}</div>                    // 4
<div>{user.name.toUpperCase()}</div>  // ALICE
<div>{isLoggedIn ? 'Hi' : 'Login'}</div>
```
### 3. You can't use statements
❌ This doesn't work:
```js
<div>
  {if (isLoggedIn) { return 'Hi' }}  // WRONG!
</div>
```
**✅ Use expressions instead:**

```js 
<div>
  {isLoggedIn ? 'Hi' : 'Login'}     // Ternary operator
  {isLoggedIn && 'Hi'}               // Logical AND
</div>
```

# 1.2 Why JSX is NOT HTML
## Key differences:
**1. className instead of class** 
```js 
// JSX
<div className="container">

// HTML
<div class="container">
``` 
**Why? class is a reserved word in JavaScript.**

**2. htmlFor instead of for**
```js // JSX
<label htmlFor="name">Name:</label>

// HTML
<label for="name">Name:</label>
``` 

**Why? for is a reserved word in JavaScript.**

**3. Style is an object, not a string**
```js // JSX
<div style={{ color: 'red', fontSize: '16px' }}>

// HTML
<div style="color: red; font-size: 16px;">
```
**Why?** In JSX, style is a JavaScript object. Note: CSS properties become camelCase (fontSize not font-size).
**4. All tags must be closed**
```js // JSX - must close
<img src="photo.jpg" />
<input type="text" />

// HTML - can be self-closing or not
<img src="photo.jpg">
<input type="text">
```

**5. Event handlers are camelCase**
```js // JSX
<button onClick={handleClick}>

// HTML
<button onclick="handleClick()">
```
**Why?** JSX uses JavaScript naming conventions.

**6. Boolean attributes**
```js
 // JSX
<button disabled={true}>    // or just <button disabled>
<button disabled={false}>   // Button is NOT disabled

// HTML
<button disabled>           // disabled
<button>                    // not disabled
```

## 1.3 JSX Rules & Gotchas
**Rule 1: Return a single root element**
❌ This doesn't work:
```js 
function App() {
  return (
    <h1>Title</h1>
    <p>Paragraph</p>
  );
}

//✅ Wrap in a single parent:
function App() {
  return (
    <div>
      <h1>Title</h1>
      <p>Paragraph</p>
    </div>
  );
}
// ✅ Or use a Fragment:
function App() {
  return (
    <>
      <h1>Title</h1>
      <p>Paragraph</p>
    </>
  );
}
// Fragment doesn't create an extra DOM element
```
**Why?** A function can only return one value. JSX must compile to one React.createElement() call.

### Rule 2: Close all tags
```js // ✅ Good
<img src="photo.jpg" />
<br />
<input type="text" />

// ❌ Bad
<img src="photo.jpg">
<br>
```

### Rule 3: camelCase for most things
```js
<div className="box">          // not class
<label htmlFor="input">        // not for
<div onClick={handler}>        // not onclick
<div style={{ backgroundColor: 'blue' }}>  // not background-color
``` 

### Gotcha 1: Curly braces for JavaScript
Inside JSX, use {} to embed JavaScript:
```js <div>
  {userName}              // Variable
  {2 + 2}                 // Expression
  {getGreeting()}         // Function call
  {user.isAdmin && <AdminPanel />}  // Conditional rendering
</div>
Outside the JSX, you're in regular JavaScript:
jsxfunction App() {
  const name = 'Alice';  // Regular JavaScript
  
  return <div>{name}</div>;  // Inside JSX, use {}
}
```

### Gotcha 2: Comments in JSX
```js 
function App() {
  return (
    <div>
      {/* This is a comment in JSX */}
      <h1>Hello</h1>
      
      {
        // You can also use this style
        // but it looks weird
      }
    </div>
  );
}
```
Regular // comments outside JSX work normally.

### Gotcha 3: Strings vs expressions
```js
 // String prop (no curly braces)
<Component name="Alice" />

// Expression prop (with curly braces)
<Component name={userName} />
<Component age={25} />
<Component isActive={true} />
```

### Gotcha 4: Undefined, null, true, false don't render
```js 
<div>{undefined}</div>   // Renders nothing
<div>{null}</div>        // Renders nothing
<div>{true}</div>        // Renders nothing
<div>{false}</div>       // Renders nothing

<div>{0}</div>           // Renders: 0
<div>{''}</div>          // Renders empty string (nothing visible)
<div>{'Hello'}</div>     // Renders: Hello

```

This is useful for conditional rendering:
```js 
{isLoggedIn && <Dashboard />}
// If isLoggedIn is false, nothing renders
```
## 🎯 Practice: Transform These
Before we move on, try to mentally transform these JSX snippets to React.createElement():
1.
```js
<button className="btn" onClick={handleClick}>
  Submit
</button>
```
2.
```js 
<div>
  <Header />
  <main>Content here</main>
</div>
<details>
<summary>Click to see answers</summary>
``` 

1.
```js
React.createElement(
  'button',
  { className: 'btn', onClick: handleClick },
  'Submit'
)
```
2.
```js 
React.createElement(
  'div',
  null,
  React.createElement(Header, null),
  React.createElement('main', null, 'Content here')
)
</details>
```
