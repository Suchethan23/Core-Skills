
## 5.2 Forms & Inputs

Forms are one of the most common ways users interact with your app.

React has two approaches to handling forms:
1. **Controlled components** (React controls the value)
2. **Uncontrolled components** (DOM controls the value)

---

### Controlled Components

**In controlled components, React state is the "single source of truth".**

**Basic controlled input:**
```javascript
function ControlledInput() {
  const [value, setValue] = useState('');
  
  const handleChange = (e) => {
    setValue(e.target.value);
  };
  
  return (
    <div>
      <input 
        type="text" 
        value={value}                    // React controls this
        onChange={handleChange}          // Update state on change
      />
      <p>You typed: {value}</p>
    </div>
  );
}
```

**Flow:**
```
1. User types 'A'
2. onChange fires
3. setValue('A') called
4. Component re-renders
5. Input value set to 'A'
```

---

### Why Controlled Components?

**Benefits:**

**1. React state is the single source of truth:**
```javascript
function Form() {
  const [email, setEmail] = useState('');
  
  // You always know what the value is
  console.log('Current email:', email);
  
  return <input value={email} onChange={(e) => setEmail(e.target.value)} />;
}
```

**2. Easy to transform input:**
```javascript
function UppercaseInput() {
  const [value, setValue] = useState('');
  
  const handleChange = (e) => {
    setValue(e.target.value.toUpperCase()); // Always uppercase
  };
  
  return <input value={value} onChange={handleChange} />;
}
```

**3. Easy to validate:**
```javascript
function EmailInput() {
  const [email, setEmail] = useState('');
  const [error, setError] = useState('');
  
  const handleChange = (e) => {
    const value = e.target.value;
    setEmail(value);
    
    // Validate
    if (!value.includes('@')) {
      setError('Invalid email');
    } else {
      setError('');
    }
  };
  
  return (
    <div>
      <input value={email} onChange={handleChange} />
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </div>
  );
}
```

**4. Easy to disable/enable:**
```javascript
function ConditionalInput() {
  const [value, setValue] = useState('');
  const [isEnabled, setIsEnabled] = useState(false);
  
  return (
    <div>
      <input
        value={value}
        onChange={(e) => setValue(e.target.value)}
        disabled={!isEnabled}
      />
      <button onClick={() => setIsEnabled(!isEnabled)}>
        {isEnabled ? 'Disable' : 'Enable'}
      </button>
    </div>
  );
}
```

---

### Controlled Component Examples

**Text input:**
```javascript
const [text, setText] = useState('');

<input
  type="text"
  value={text}
  onChange={(e) => setText(e.target.value)}
/>
```

**Textarea:**
```javascript
const [message, setMessage] = useState('');

<textarea
  value={message}
  onChange={(e) => setMessage(e.target.value)}
/>
```

**Select dropdown:**
```javascript
const [selected, setSelected] = useState('option1');

<select value={selected} onChange={(e) => setSelected(e.target.value)}>
  <option value="option1">Option 1</option>
  <option value="option2">Option 2</option>
  <option value="option3">Option 3</option>
</select>
```

**Checkbox:**
```javascript
const [isChecked, setIsChecked] = useState(false);

<input
  type="checkbox"
  checked={isChecked}
  onChange={(e) => setIsChecked(e.target.checked)}
/>
```

**Radio buttons:**
```javascript
const [selected, setSelected] = useState('option1');

<div>
  <label>
    <input
      type="radio"
      value="option1"
      checked={selected === 'option1'}
      onChange={(e) => setSelected(e.target.value)}
    />
    Option 1
  </label>
  
  <label>
    <input
      type="radio"
      value="option2"
      checked={selected === 'option2'}
      onChange={(e) => setSelected(e.target.value)}
    />
    Option 2
  </label>
</div>
```

---

### Multiple Inputs Pattern

**Handle multiple inputs with one handler:**

```javascript
function Form() {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    age: ''
  });
  
  const handleChange = (e) => {
    const { name, value } = e.target;
    
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };
  
  return (
    <form>
      <input
        name="username"
        value={formData.username}
        onChange={handleChange}
      />
      <input
        name="email"
        value={formData.email}
        onChange={handleChange}
      />
      <input
        name="age"
        value={formData.age}
        onChange={handleChange}
      />
    </form>
  );
}
```

**Key point:** Use `name` attribute to identify which field changed.

---

### Form Submission

**Handle form submission:**

```javascript
function Form() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  
  const handleSubmit = (e) => {
    e.preventDefault(); // Prevent page reload!
    
    console.log('Submitting:', { email, password });
    
    // API call, validation, etc.
  };
  
  return (
    <form onSubmit={handleSubmit}>
      <input
        type="email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <input
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <button type="submit">Submit</button>
    </form>
  );
}
```

**Important:** Always call `e.preventDefault()` in `onSubmit` handler to prevent page reload.

---

### Uncontrolled Components

**In uncontrolled components, the DOM is the source of truth.**

**Basic uncontrolled input:**
```javascript
function UncontrolledInput() {
  const inputRef = useRef(null);
  
  const handleSubmit = (e) => {
    e.preventDefault();
    // Get value from DOM
    console.log('Value:', inputRef.current.value);
  };
  
  return (
    <form onSubmit={handleSubmit}>
      <input type="text" ref={inputRef} />
      <button type="submit">Submit</button>
    </form>
  );
}
```

**Flow:**
```
1. User types in input
2. DOM stores the value
3. React doesn't track changes
4. On submit, read value from DOM via ref
```

---

### Uncontrolled with Default Value

**Use `defaultValue` (not `value`) for uncontrolled components:**

```javascript
function UncontrolledInput() {
  const inputRef = useRef(null);
  
  return (
    <div>
      <input
        type="text"
        defaultValue="Initial value"
        ref={inputRef}
      />
      <button onClick={() => console.log(inputRef.current.value)}>
        Get Value
      </button>
    </div>
  );
}
```

**`defaultValue` vs `value`:**
- `value` = Controlled (React manages)
- `defaultValue` = Uncontrolled (DOM manages, React sets initial value)

---

### Uncontrolled Component Examples

**Checkbox:**
```javascript
function UncontrolledCheckbox() {
  const checkboxRef = useRef(null);
  
  const handleSubmit = () => {
    console.log('Checked:', checkboxRef.current.checked);
  };
  
  return (
    <div>
      <input
        type="checkbox"
        defaultChecked={false}
        ref={checkboxRef}
      />
      <button onClick={handleSubmit}>Submit</button>
    </div>
  );
}
```

**File input (always uncontrolled):**
```javascript
function FileInput() {
  const fileRef = useRef(null);
  
  const handleSubmit = () => {
    const files = fileRef.current.files;
    console.log('Selected files:', files);
  };
  
  return (
    <div>
      <input type="file" ref={fileRef} />
      <button onClick={handleSubmit}>Upload</button>
    </div>
  );
}
```

**Note:** File inputs are ALWAYS uncontrolled. You can't set their value from JavaScript for security reasons.

---

### Controlled vs Uncontrolled: When to Use Which

**Use Controlled Components when:**

✅ You need to:
- Validate input as user types
- Transform input (e.g., uppercase, formatting)
- Disable submit button based on input
- Show conditional UI based on input
- Have multiple fields that interact
- Implement instant search/filtering
- Store form state in parent component

**Example:**
```javascript
function PasswordForm() {
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  
  const passwordsMatch = password === confirmPassword;
  
  return (
    <form>
      <input
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <input
        type="password"
        value={confirmPassword}
        onChange={(e) => setConfirmPassword(e.target.value)}
      />
      {!passwordsMatch && <p>Passwords don't match!</p>}
      <button disabled={!passwordsMatch}>Submit</button>
    </form>
  );
}
```

---

**Use Uncontrolled Components when:**

✅ You:
- Only need the value on submit
- Are integrating with non-React code
- Have a very simple form
- Are working with file inputs (required)
- Want slightly better performance (no re-renders on every keystroke)

**Example:**
```javascript
function SimpleForm() {
  const nameRef = useRef(null);
  const emailRef = useRef(null);
  
  const handleSubmit = (e) => {
    e.preventDefault();
    
    const data = {
      name: nameRef.current.value,
      email: emailRef.current.value
    };
    
    console.log('Form data:', data);
  };
  
  return (
    <form onSubmit={handleSubmit}>
      <input type="text" ref={nameRef} />
      <input type="email" ref={emailRef} />
      <button type="submit">Submit</button>
    </form>
  );
}
```

---

### Comparison Table

| Aspect | Controlled | Uncontrolled |
|--------|-----------|--------------|
| **Value source** | React state | DOM |
| **Attribute** | `value` | `defaultValue` |
| **Access value** | From state | From ref |
| **When value updates** | Every keystroke (re-render) | Only when you read it |
| **Validation** | Easy (on every change) | On submit only |
| **Transform input** | Easy | Harder |
| **Performance** | Slightly slower (re-renders) | Slightly faster |
| **React way** | ✅ Preferred | ⚠️ Use when needed |
| **Code** | More code | Less code |

---

### Best Practices

**✅ DO:**
- Use controlled components by default
- Use `e.preventDefault()` in form submit handlers
- Use uncontrolled for file inputs
- Use `name` attributes to identify inputs
- Group related state into an object
- Validate on both client and server

**❌ DON'T:**
- Mix controlled and uncontrolled for same input
- Forget `e.preventDefault()` on form submit
- Put every input in separate state (group when related)
- Trust only client-side validation
- Use `value` with `defaultValue` (they conflict)

---

### Common Mistakes

**Mistake 1: Missing onChange (controlled input becomes uncontrolled)**
```javascript
// ❌ BAD - value with no onChange = read-only input
<input type="text" value={text} />

// ✅ GOOD
<input type="text" value={text} onChange={(e) => setText(e.target.value)} />
```

**Mistake 2: Mixing controlled and uncontrolled**
```javascript
// ❌ BAD - Can't use both!
<input type="text" value={text} defaultValue="initial" />

// ✅ GOOD - Choose one
<input type="text" value={text} onChange={handleChange} />
// OR
<input type="text" defaultValue="initial" ref={inputRef} />
```

**Mistake 3: Forgetting preventDefault**
```javascript
// ❌ BAD - Form reloads page
const handleSubmit = (e) => {
  console.log('Submit');
};

// ✅ GOOD
const handleSubmit = (e) => {
  e.preventDefault();
  console.log('Submit');
};
```

**Mistake 4: Using checked instead of value for checkboxes**
```javascript
// ❌ WRONG - 'value' doesn't work for checkboxes
<input
  type="checkbox"
  value={isChecked}
  onChange={(e) => setIsChecked(e.target.value)}
/>

// ✅ CORRECT - Use 'checked'
<input
  type="checkbox"
  checked={isChecked}
  onChange={(e) => setIsChecked(e.target.checked)}
/>
```

**Mistake 5: Not using name for multiple inputs**
```javascript
// ❌ BAD - Separate handlers for each input
<input value={name} onChange={(e) => setName(e.target.value)} />
<input value={email} onChange={(e) => setEmail(e.target.value)} />
<input value={age} onChange={(e) => setAge(e.target.value)} />

// ✅ GOOD - One handler with 'name' attribute
const handleChange = (e) => {
  setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));
};

<input name="name" value={formData.name} onChange={handleChange} />
<input name="email" value={formData.email} onChange={handleChange} />
<input name="age" value={formData.age} onChange={handleChange} />
```

---

## Summary: Part 5

### Key Concepts

**1. Event Handling in React**
- Use camelCase: `onClick`, `onChange`, `onSubmit`
- Pass function reference, not string
- Use `e.preventDefault()` to prevent default behavior
- Use `e.stopPropagation()` to stop event bubbling
- Event handlers receive SyntheticEvent (cross-browser wrapper)
- React uses event delegation for performance
- Synthetic events have same interface as native events

**2. Synthetic Events**
- Cross-browser compatibility layer
- Same API everywhere
- Access native event via `e.nativeEvent` if needed
- Event pooling removed in React 17+
- Automatic cleanup when component unmounts

**3. Event Delegation**
- React attaches one listener to root, not each element
- Better performance (fewer listeners)
- Automatic cleanup
- Changed from `document` to root in React 17+

**4. Forms - Controlled Components**
- React state is single source of truth
- Use `value` and `onChange`
- Easy to validate, transform, and control
- Re-renders on every keystroke
- Preferred React approach

**5. Forms - Uncontrolled Components**
- DOM is single source of truth
- Use `defaultValue` and `ref`
- Access value when needed (e.g., on submit)
- No re-renders on keystroke
- Use for simple forms and file inputs

---

### Mental Models

**Event Flow:**
```
User interacts
  ↓
Event fires
  ↓
React's root listener catches it (event delegation)
  ↓
React creates SyntheticEvent
  ↓
Your handler called with SyntheticEvent
  ↓
State updated (if needed)
  ↓
Component re-renders
```

**Controlled Component Flow:**
```
User types
  ↓
onChange fires
  ↓
setState called
  ↓
Component re-renders
  ↓
Input value updated from state
```

**Uncontrolled Component Flow:**
```
User types
  ↓
DOM stores value
  ↓
No React re-render
  ↓
Access value via ref when needed
```

---

### Common Patterns

**Pattern 1: Simple form**
```javascript
const [formData, setFormData] = useState({ name: '', email:</parameter>
<parameter name="new_str">**Pattern 1: Simple controlled form**
```javascript
function Form() {
  const [formData, setFormData] = useState({ 
    name: '', 
    email: '' 
  });
  
  const handleChange = (e) => {
    setFormData(prev => ({
      ...prev,
      [e.target.name]: e.target.value
    }));
  };
  
  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(formData);
  };
  
  return (
    <form onSubmit={handleSubmit}>
      <input name="name" value={formData.name} onChange={handleChange} />
      <input name="email" value={formData.email} onChange={handleChange} />
      <button type="submit">Submit</button>
    </form>
  );
}
```

**Pattern 2: Input with validation**
```javascript
function ValidatedInput() {
  const [value, setValue] = useState('');
  const [error, setError] = useState('');
  
  const handleChange = (e) => {
    const newValue = e.target.value;
    setValue(newValue);
    
    if (newValue.length < 3) {
      setError('Must be at least 3 characters');
    } else {
      setError('');
    }
  };
  
  return (
    <div>
      <input value={value} onChange={handleChange} />
      {error && <span style={{ color: 'red' }}>{error}</span>}
    </div>
  );
}
```

**Pattern 3: Dynamic form fields**
```javascript
function DynamicForm() {
  const [items, setItems] = useState(['']);
  
  const handleChange = (index, value) => {
    setItems(prev => prev.map((item, i) => 
      i === index ? value : item
    ));
  };
  
  const addField = () => {
    setItems(prev => [...prev, '']);
  };
  
  return (
    <div>
      {items.map((item, index) => (
        <input
          key={index}
          value={item}
          onChange={(e) => handleChange(index, e.target.value)}
        />
      ))}
      <button onClick={addField}>Add Field</button>
    </div>
  );
}
```

**Pattern 4: Debounced input (performance)**
```javascript
function SearchInput() {
  const [searchTerm, setSearchTerm] = useState('');
  const [debouncedTerm, setDebouncedTerm] = useState('');
  
  useEffect(() => {
    const timer = setTimeout(() => {
      setDebouncedTerm(searchTerm);
    }, 500);
    
    return () => clearTimeout(timer);
  }, [searchTerm]);
  
  useEffect(() => {
    if (debouncedTerm) {
      // Make API call with debouncedTerm
      console.log('Searching for:', debouncedTerm);
    }
  }, [debouncedTerm]);
  
  return (
    <input
      value={searchTerm}
      onChange={(e) => setSearchTerm(e.target.value)}
      placeholder="Search..."
    />
  );
}
```

**Pattern 5: Form with submit disabled**
```javascript
function Form() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  
  const isValid = email.includes('@') && password.length >= 6;
  
  const handleSubmit = (e) => {
    e.preventDefault();
    console.log({ email, password });
  };
  
  return (
    <form onSubmit={handleSubmit}>
      <input
        type="email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <input
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <button type="submit" disabled={!isValid}>
        Submit
      </button>
    </form>
  );
}
```

---

### Quick Reference

**Event naming:**
```javascript
onClick, onDoubleClick
onChange, onInput
onSubmit
onKeyDown, onKeyUp, onKeyPress
onFocus, onBlur
onMouseEnter, onMouseLeave, onMouseMove
onDragStart, onDragEnd, onDrop
```

**Preventing default:**
```javascript
const handleSubmit = (e) => {
  e.preventDefault();
  // Your code
};
```

**Stopping propagation:**
```javascript
const handleClick = (e) => {
  e.stopPropagation();
  // Your code
};
```

**Controlled input template:**
```javascript
const [value, setValue] = useState('');

<input
  value={value}
  onChange={(e) => setValue(e.target.value)}
/>
```

**Uncontrolled input template:**
```javascript
const inputRef = useRef(null);

<input ref={inputRef} defaultValue="initial" />

// Access: inputRef.current.value
```

---

### Decision Tree: Controlled or Uncontrolled?

Do you need to validate as user types?
YES → Controlled
NO → Continue
Do you need to transform input (uppercase, format, etc.)?
YES → Controlled
NO → Continue
Do you need to disable/enable based on input?
YES → Controlled
NO → Continue
Do multiple inputs interact with each other?
YES → Controlled
NO → Continue
Is it a file input?
YES → Uncontrolled (required)
NO → Continue
Is it a very simple form (just submit, no validation)?
YES → Either (uncontrolled is simpler)
NO → Controlled (when in doubt, use controlled)

---

### Critical Rules

**✅ DO:**
- Use controlled components as default
- Always use `e.preventDefault()` for form submit
- Use camelCase for event names
- Pass function references, not function calls
- Use `name` attribute for multiple inputs
- Use `checked` for checkboxes/radios, not `value`
- Group related form state into an object

**❌ DON'T:**
- Mix controlled and uncontrolled for same input
- Use `value` without `onChange`
- Use both `value` and `defaultValue`
- Forget to call `e.preventDefault()` on form submit
- Call functions in event handlers: `onClick={func()}` ❌
- Use `e.persist()` in React 17+ (not needed)
- Trust client-side validation alone (always validate server-side too)

---
