## 2.3 Component Composition

### What is Composition?

**Composition** is building complex UIs from simple, reusable components.

Instead of one giant component, you compose many small ones.

**Think LEGO blocks:**
- Each block is simple
- Combine them to build complex structures
- Can rearrange and reuse blocks

---

### Reusability Principles

**Principle 1: Extract repeated patterns**

If you're writing similar JSX multiple times, extract a component.

**❌ Before - repetition:**
```javascript
function Dashboard() {
  return (
    <div>
      <div className="stat-card">
        <h3>Users</h3>
        <p className="big-number">1,234</p>
      </div>
      
      <div className="stat-card">
        <h3>Posts</h3>
        <p className="big-number">5,678</p>
      </div>
      
      <div className="stat-card">
        <h3>Comments</h3>
        <p className="big-number">9,012</p>
      </div>
    </div>
  );
}
```

**✅ After - extracted component:**
```javascript
function StatCard({ title, value }) {
  return (
    <div className="stat-card">
      <h3>{title}</h3>
      <p className="big-number">{value}</p>
    </div>
  );
}

function Dashboard() {
  return (
    <div>
      <StatCard title="Users" value="1,234" />
      <StatCard title="Posts" value="5,678" />
      <StatCard title="Comments" value="9,012" />
    </div>
  );
}
```

**Benefits:**
- Less code duplication
- Easier to update styling (change once, applies everywhere)
- Can reuse `StatCard` on other pages

---

**Principle 2: Props control variations**

Use props to make components flexible.

**Example: Button with variations**
```javascript
function Button({ text, variant, size, onClick }) {
  const className = `btn btn-${variant} btn-${size}`;
  
  return (
    <button className={className} onClick={onClick}>
      {text}
    </button>
  );
}

// Usage - same component, different looks
<Button text="Save" variant="primary" size="large" onClick={handleSave} />
<Button text="Cancel" variant="secondary" size="small" onClick={handleCancel} />
<Button text="Delete" variant="danger" size="medium" onClick={handleDelete} />
```

**One component, many variations through props.**

---

**Principle 3: Default props for common cases**

```javascript
function Button({ 
  text, 
  variant = 'primary',    // Default
  size = 'medium',        // Default
  onClick 
}) {
  return (
    <button className={`btn btn-${variant} btn-${size}`} onClick={onClick}>
      {text}
    </button>
  );
}

// Can omit defaults
<Button text="Click me" onClick={handleClick} />
// Uses variant="primary" and size="medium"
```

---

### The `children` Prop

`children` is a special prop representing content between opening/closing tags.

**Basic usage:**
```javascript
function Card({ children }) {
  return (
    <div className="card">
      {children}
    </div>
  );
}

// Usage
<Card>
  <h2>Title</h2>
  <p>Card content goes here</p>
</Card>
```

Everything between `<Card>` and `</Card>` becomes `children`.

---

**Why `children` is powerful:**

**1. Wrapper components**
```javascript
function Container({ children }) {
  return (
    <div style={{ maxWidth: '1200px', margin: '0 auto' }}>
      {children}
    </div>
  );
}

<Container>
  <Header />
  <MainContent />
  <Footer />
</Container>
```

**2. Modal/Dialog patterns**
```javascript
function Modal({ children }) {
  return (
    <div className="modal-backdrop">
      <div className="modal-content">
        {children}
      </div>
    </div>
  );
}

<Modal>
  <h1>Confirm Action</h1>
  <p>Are you sure?</p>
  <button>Yes</button>
  <button>No</button>
</Modal>
```

**3. Layout components**
```javascript
function Panel({ title, children }) {
  return (
    <div className="panel">
      <div className="panel-header">
        <h3>{title}</h3>
      </div>
      <div className="panel-body">
        {children}
      </div>
    </div>
  );
}

<Panel title="User Settings">
  <label>Username:</label>
  <input type="text" />
  <button>Save</button>
</Panel>
```

---

### Composition Example: Building a Card

**Start simple:**
```javascript
function Card({ children }) {
  return <div className="card">{children}</div>;
}
```

**Add header and body:**
```javascript
function CardHeader({ children }) {
  return <div className="card-header">{children}</div>;
}

function CardBody({ children }) {
  return <div className="card-body">{children}</div>;
}

function Card({ children }) {
  return <div className="card">{children}</div>;
}
```

**Compose them:**
```javascript
<Card>
  <CardHeader>
    <h2>User Profile</h2>
  </CardHeader>
  <CardBody>
    <p>Name: Alice</p>
    <p>Email: alice@example.com</p>
  </CardBody>
</Card>
```

**Flexible and reusable!**

---
