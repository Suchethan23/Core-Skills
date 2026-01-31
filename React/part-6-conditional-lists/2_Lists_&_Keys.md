
## 6.2 Lists & Keys (Critical Topic)

### Rendering Lists

**Use `.map()` to render arrays:**

```javascript
function TodoList() {
  const todos = ['Learn React', 'Build a project', 'Get a job'];
  
  return (
    <ul>
      {todos.map((todo, index) => (
        <li key={index}>{todo}</li>
      ))}
    </ul>
  );
}
```

**How it works:**
```javascript
todos.map((todo, index) => (
  <li key={index}>{todo}</li>
))

// Produces:
[
  <li key={0}>Learn React</li>,
  <li key={1}>Build a project</li>,
  <li key={2}>Get a job</li>
]
```

React renders this array of elements.

---

### Array of Objects

**Most common pattern:**

```javascript
function UserList() {
  const users = [
    { id: 1, name: 'Alice', age: 25 },
    { id: 2, name: 'Bob', age: 30 },
    { id: 3, name: 'Charlie', age: 35 }
  ];
  
  return (
    <ul>
      {users.map(user => (
        <li key={user.id}>
          {user.name} - {user.age} years old
        </li>
      ))}
    </ul>
  );
}
```

**Extracting to a component:**
```javascript
function User({ user }) {
  return (
    <li>
      {user.name} - {user.age} years old
    </li>
  );
}

function UserList() {
  const users = [
    { id: 1, name: 'Alice', age: 25 },
    { id: 2, name: 'Bob', age: 30 },
    { id: 3, name: 'Charlie', age: 35 }
  ];
  
  return (
    <ul>
      {users.map(user => (
        <User key={user.id} user={user} />
      ))}
    </ul>
  );
}
```

**Note:** The `key` goes on the element returned from `.map()`, not inside the component.

---

### What Are Keys?

**Keys are special attributes that help React identify which items have changed, been added, or removed.**

```javascript
<li key="unique-identifier">Content</li>
```

**Think of keys like IDs:**
- Each item in a list needs a unique identifier
- Helps React track items across renders
- Critical for performance and correctness

---

### Why Keys Exist

**Without keys, React uses position to match elements.**

**Example problem:**

```javascript
// Initial render
<ul>
  <li>Alice</li>
  <li>Bob</li>
  <li>Charlie</li>
</ul>

// After adding "David" at the start
<ul>
  <li>David</li>   {/* React thinks this was "Alice" */}
  <li>Alice</li>   {/* React thinks this was "Bob" */}
  <li>Bob</li>     {/* React thinks this was "Charlie" */}
  <li>Charlie</li> {/* React thinks this is new */}
</ul>
```

**React's assumption without keys:**
1. First `<li>` is the same, just text changed "Alice" → "David"
2. Second `<li>` is the same, just text changed "Bob" → "Alice"
3. Third `<li>` is the same, just text changed "Charlie" → "Bob"
4. Fourth `<li>` is new, add it

**This is inefficient and can cause bugs!**

---

**With keys:**

```javascript
// Initial render
<ul>
  <li key="1">Alice</li>
  <li key="2">Bob</li>
  <li key="3">Charlie</li>
</ul>

// After adding "David" at the start
<ul>
  <li key="4">David</li>   {/* New item */}
  <li key="1">Alice</li>   {/* Same item, just moved */}
  <li key="2">Bob</li>     {/* Same item, just moved */}
  <li key="3">Charlie</li> {/* Same item, just moved */}
</ul>
```

**React's understanding with keys:**
1. Item with key="4" is new, create it
2. Item with key="1" already exists, just move it
3. Item with key="2" already exists, just move it
4. Item with key="3" already exists, just move it

**Much more efficient!**

---

### Keys Must Be Unique

**Among siblings (not globally):**

```javascript
function App() {
  return (
    <div>
      {/* These keys only need to be unique within their own list */}
      <ul>
        <li key="1">Item 1</li>
        <li key="2">Item 2</li>
      </ul>
      
      <ul>
        {/* Can reuse keys in a different list */}
        <li key="1">Different Item 1</li>
        <li key="2">Different Item 2</li>
      </ul>
    </div>
  );
}
```

**Keys must be unique among siblings in the same array.**

---

### Good Keys vs Bad Keys

**✅ GOOD - Stable, unique IDs:**

```javascript
// Database ID
<User key={user.id} user={user} />

// UUID
<Item key={item.uuid} item={item} />

// Unique combination
<Post key={`${post.userId}-${post.postId}`} post={post} />

// Generated ID from data
<Comment key={comment.timestamp + comment.userId} comment={comment} />
```

**❌ BAD - Index as key (usually):**

```javascript
// Don't do this if list can change
{items.map((item, index) => (
  <Item key={index} item={item} />
))}
```

**❌ BAD - Random values:**

```javascript
// Never do this - new key every render!
<Item key={Math.random()} item={item} />
<Item key={Date.now()} item={item} />
```

---

### Index as Key: Why It's Bad

**The problem with index as key:**

**Example: Todo list with delete functionality**

```javascript
function TodoList() {
  const [todos, setTodos] = useState([
    { text: 'Learn React' },
    { text: 'Build project' },
    { text: 'Get job' }
  ]);
  
  const deleteTodo = (indexToDelete) => {
    setTodos(todos.filter((_, index) => index !== indexToDelete));
  };
  
  return (
    <ul>
      {todos.map((todo, index) => (
        <li key={index}>
          {todo.text}
          <button onClick={() => deleteTodo(index)}>Delete</button>
        </li>
      ))}
    </ul>
  );
}
```

**What happens:**

**Before deletion:**
```javascript
[
  { text: 'Learn React' },    // index: 0, key: 0
  { text: 'Build project' },  // index: 1, key: 1
  { text: 'Get job' }         // index: 2, key: 2
]
```

**After deleting "Build project" (index 1):**
```javascript
[
  { text: 'Learn React' },    // index: 0, key: 0 ✅ Same
  { text: 'Get job' }         // index: 1, key: 1 ❌ Key changed!
]
```

**React's view:**
- Item with key=0: Still "Learn React" ✅
- Item with key=1: Changed from "Build project" to "Get job" 
- Item with key=2: Deleted

**React thinks the last item changed and was updated, not deleted!**

---

### Index as Key: The Bug

**Real bug example with stateful components:**

```javascript
function TodoList() {
  const [todos, setTodos] = useState([
    { id: 1, text: 'First' },
    { id: 2, text: 'Second' },
    { id: 3, text: 'Third' }
  ]);
  
  const deleteTodo = (id) => {
    setTodos(todos.filter(todo => todo.id !== id));
  };
  
  return (
    <ul>
      {todos.map((todo, index) => (
        <TodoItem 
          key={index}  // ❌ BAD!
          todo={todo}
          onDelete={() => deleteTodo(todo.id)}
        />
      ))}
    </ul>
  );
}

function TodoItem({ todo, onDelete }) {
  const [isEditing, setIsEditing] = useState(false);
  
  return (
    <li>
      {isEditing ? (
        <input defaultValue={todo.text} />
      ) : (
        <span>{todo.text}</span>
      )}
      <button onClick={() => setIsEditing(!isEditing)}>Edit</button>
      <button onClick={onDelete}>Delete</button>
    </li>
  );
}
```

**Bug scenario:**
1. Click "Edit" on "Second" item (sets isEditing=true for key=1)
2. Delete "First" item
3. "Second" is now at index 0, "Third" is now at index 1
4. React keeps state for key=1, but now key=1 is "Third"
5. "Third" item shows as editing instead of "Second"!

**The state got attached to the wrong item because keys changed.**

---

### Index as Key: When It's OK

**Index is acceptable as key ONLY when ALL of these are true:**

1. ✅ List never reorders
2. ✅ List never filters/deletes items
3. ✅ Items are never added to the middle
4. ✅ Items don't have unique IDs
5. ✅ List is static (doesn't change)

**Examples where index is OK:**

**Static list:**
```javascript
function DaysOfWeek() {
  const days = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
  
  return (
    <ul>
      {days.map((day, index) => (
        <li key={index}>{day}</li>
      ))}
    </ul>
  );
}
// OK - list never changes
```

**Read-only list:**
```javascript
function ArticleParagraphs({ paragraphs }) {
  return (
    <div>
      {paragraphs.map((paragraph, index) => (
        <p key={index}>{paragraph}</p>
      ))}
    </div>
  );
}
// OK - just displaying text, no interaction
```

**Default: Use stable IDs, not index.**

---

### Creating Keys from Data

**When you don't have an ID:**

**Option 1: Combine fields:**
```javascript
const users = [
  { firstName: 'Alice', lastName: 'Smith', email: 'alice@example.com' },
  { firstName: 'Bob', lastName: 'Jones', email: 'bob@example.com' }
];

users.map(user => (
  <User key={`${user.firstName}-${user.lastName}-${user.email}`} user={user} />
))
```

**Option 2: Generate IDs when creating data:**
```javascript
const [todos, setTodos] = useState([]);

const addTodo = (text) => {
  const newTodo = {
    id: Date.now(), // Simple ID generation
    text: text,
    completed: false
  };
  setTodos([...todos, newTodo]);
};

return (
  <ul>
    {todos.map(todo => (
      <TodoItem key={todo.id} todo={todo} />
    ))}
  </ul>
);
```

**Option 3: Use a library like `uuid`:**
```javascript
import { v4 as uuidv4 } from 'uuid';

const addTodo = (text) => {
  const newTodo = {
    id: uuidv4(), // Generate UUID
    text: text
  };
  setTodos([...todos, newTodo]);
};
```

---

### Reconciliation with Lists

**How React reconciles lists with keys:**

**Example: Reordering**

```javascript
// Before
<ul>
  <li key="a">Alice</li>
  <li key="b">Bob</li>
  <li key="c">Charlie</li>
</ul>

// After (reordered)
<ul>
  <li key="c">Charlie</li>
  <li key="a">Alice</li>
  <li key="b">Bob</li>
</ul>
```

**React's process:**
1. Compare old list keys: [a, b, c]
2. Compare new list keys: [c, a, b]
3. Identify: c moved to start, a and b shifted
4. Reuse existing DOM nodes, just change order
5. No re-creation of elements

**Efficient!**

---

**Without keys (using index):**

```javascript
// Before
<ul>
  <li key="0">Alice</li>
  <li key="1">Bob</li>
  <li key="2">Charlie</li>
</ul>

// After (reordered)
<ul>
  <li key="0">Charlie</li>   {/* React thinks text changed Alice → Charlie */}
  <li key="1">Alice</li>     {/* React thinks text changed Bob → Alice */}
  <li key="2">Bob</li>       {/* React thinks text changed Charlie → Bob */}
</ul>
```

**React's process:**
1. key="0" changed content from "Alice" to "Charlie" → update text
2. key="1" changed content from "Bob" to "Alice" → update text
3. key="2" changed content from "Charlie" to "Bob" → update text

**Inefficient - updated everything instead of just reordering!**

---

### Keys with Fragments

**If you need to return multiple elements, use Fragment with key:**

```javascript
function Glossary({ items }) {
  return (
    <dl>
      {items.map(item => (
        <Fragment key={item.id}>
          <dt>{item.term}</dt>
          <dd>{item.description}</dd>
        </Fragment>
      ))}
    </dl>
  );
}
```

**Note:** `<Fragment>` needs to be imported, and `<>` shorthand doesn't support keys.

```javascript
import { Fragment } from 'react';

// ✅ Works with key
<Fragment key={item.id}>...</Fragment>

// ❌ Doesn't support key
<>...</>
```

---

### Common List Patterns

**Pattern 1: Map with conditional rendering:**
```javascript
function UserList({ users }) {
  return (
    <ul>
      {users.map(user => (
        user.isActive && (
          <li key={user.id}>{user.name}</li>
        )
      ))}
    </ul>
  );
}

// Better - filter first
function UserList({ users }) {
  const activeUsers = users.filter(user => user.isActive);
  
  return (
    <ul>
      {activeUsers.map(user => (
        <li key={user.id}>{user.name}</li>
      ))}
    </ul>
  );
}
```

**Pattern 2: Nested lists:**
```javascript
function CategorizedList({ categories }) {
  return (
    <div>
      {categories.map(category => (
        <div key={category.id}>
          <h2>{category.name}</h2>
          <ul>
            {category.items.map(item => (
              <li key={item.id}>{item.name}</li>
            ))}
          </ul>
        </div>
      ))}
    </div>
  );
}
```

**Pattern 3: List with actions:**
```javascript
function TodoList({ todos, onToggle, onDelete }) {
  return (
    <ul>
      {todos.map(todo => (
        <li key={todo.id}>
          <input
            type="checkbox"
            checked={todo.completed}
            onChange={() => onToggle(todo.id)}
          />
          <span>{todo.text}</span>
          <button onClick={() => onDelete(todo.id)}>Delete</button>
        </li>
      ))}
    </ul>
  );
}
```

**Pattern 4: Empty state:**
```javascript
function List({ items }) {
  if (items.length === 0) {
    return <p>No items to display</p>;
  }
  
  return (
    <ul>
      {items.map(item => (
        <li key={item.id}>{item.name}</li>
      ))}
    </ul>
  );
}
```

---

### List Rendering Pitfalls

**Pitfall 1: Forgetting keys:**
```javascript
// ❌ Missing key warning
{items.map(item => (
  <li>{item.name}</li>
))}

// ✅ With key
{items.map(item => (
  <li key={item.id}>{item.name}</li>
))}
```

**Pitfall 2: Non-unique keys:**
```javascript
// ❌ Duplicate keys if same name
{items.map(item => (
  <li key={item.name}>{item.name}</li>
))}

// ✅ Use unique ID
{items.map(item => (
  <li key={item.id}>{item.name}</li>
))}
```

**Pitfall 3: Key on wrong element:**
```javascript
// ❌ Key should be on element returned from map
{items.map(item => (
  <TodoItem todo={item}>
    <li key={item.id}>{item.text}</li>
  </TodoItem>
))}

// ✅ Key on element returned from map
{items.map(item => (
  <TodoItem key={item.id} todo={item} />
))}
```

**Pitfall 4: Using key as prop:**
```javascript
// ❌ 'key' is not passed as a prop
function TodoItem({ key, todo }) {  // key is undefined!
  return <li>{todo.text}</li>;
}

// ✅ Use a different name for the prop
function TodoItem({ id, todo }) {
  return <li>{todo.text}</li>;
}

<TodoItem key={todo.id} id={todo.id} todo={todo} />
```

---

## Summary: Part 6

### Key Concepts

**1. Conditional Rendering Patterns**
- **if/else**: Traditional, good for multiple conditions, early returns
- **Ternary (`? :`)**: Inline, two options, concise
- **Logical AND (`&&`)**: Show/hide, watch for falsy values
- **Early returns**: Clean code, handle edge cases first
- **Switch**: Multiple conditions, or use object mapping
- Choose pattern based on use case

**2. Conditional Rendering Pitfalls**
- `&&` with falsy numbers renders the number
- Always return JSX or null, never undefined
- Avoid deeply nested ternaries
- Extract complex logic to functions

**3. Lists with map()**
- Use `.map()` to render arrays
- Each element needs a `key` prop
- Extract to components for complex items
- Filter before mapping when possible

**4. Keys - Critical Concept**
- Keys help React identify which items changed
- Must be unique among siblings
- Use stable, unique IDs from your data
- Don't use index as key (unless static list)
- Index as key causes bugs with reordering/deletion
- Keys enable efficient reconciliation

**5. Why Index as Key is Bad**
- Keys change when items reorder/delete
- React mixes up component state
- Performance problems
- Use only for static, read-only lists

**6. Reconciliation with Lists**
- With keys: React reuses elements, just reorders
- Without keys: React updates content, inefficient
- Keys make list updates performant

---

### Mental Models

**Conditional Rendering Decision:**
```
Show/hide one thing? → &&
Choose between two? → Ternary
Multiple conditions? → Early returns / Switch
Complex logic? → Extract function
```

**Key Selection:**
```
Has database ID? → Use it
Has UUID? → Use it
Can combine unique fields? → Combine them
Static list that never changes? → Index OK
Dynamic list? → Generate ID
Last resort? → Create stable ID when adding item
Never? → Math.random() or Date.now()
```

**List Reconciliation:**
```
Without keys:
  Position-based matching
  Updates everything
  Inefficient
  
With keys:
  Identity-based matching
  Reuses elements
  Efficient
```

---

### Common Patterns Quick Reference

**Conditional Rendering:**

```javascript
// Show/Hide
{isLoggedIn && <Dashboard />}

// Either/Or
{isLoggedIn ? <Dashboard /> : <Login />}

// Early Return
if (!user) return <Loading />;
return <Profile user={user} />;

// Multiple Conditions
switch (status) {
  case 'loading': return <Spinner />;
  case 'error': return <Error />;
  default: return <Content />;
}
```

**List Rendering:**

```javascript
// Basic List
{items.map(item => (
  <li key={item.id}>{item.name}</li>
))}

// Component List
{users.map(user => (
  <UserCard key={user.id} user={user} />
))}

// Filtered List
{items
  .filter(item => item.isActive)
  .map(item => (
    <li key={item.id}>{item.name}</li>
  ))}

// Empty State
{items.length === 0 ? (
  <p>No items</p>
) : (
  <ul>
    {items.map(item => (
      <li key={item.id}>{item.name}</li>
    ))}
  </ul>
)}
```

---

### Critical Rules

**✅ DO:**
- Use `&&` for show/hide single elements
- Use ternary for choosing between two options
- Use early returns for guard clauses
- Always provide keys when rendering lists
- Use unique, stable IDs for keys
- Filter/sort before mapping
- Extract complex list items to components
- Handle empty list state

**❌ DON'T:**
- Use index as key for dynamic lists
- Use Math.random() or Date.now() as keys
- Forget keys when using .map()
- Use duplicate keys
- Nest ternaries more than 2 levels deep
- Use `&&` with numbers without checking for 0
- Put key inside child component (put on element from map)
- Return undefined from components

---

### Decision Trees

**Which Conditional Pattern?**

```
Need to show/hide one thing?
  YES → Use &&
  NO → Continue

Choosing between exactly two things?
  YES → Use ternary
  NO → Continue

Have 3+ conditions to check?
  YES → Use early returns or switch
  NO → Continue

Need complex inline logic?
  YES → Extract to function
```

**Which Key to Use?**

```
Does data have database ID?
  YES → Use item.id
  NO → Continue

Does data have UUID?
  YES → Use UUID
  NO → Continue

Can you combine fields to make unique?
  YES → Use `${field1}-${field2}`
  NO → Continue

Is list completely static (never changes)?
  YES → Index is OK
  NO → Continue

Can you generate ID when creating item?
  YES → Generate stable ID (Date.now(), counter, uuid)
  NO → You need to fix your data structure
```

---

### Debugging Tips

**Problem: React warns about missing keys**
```
Warning: Each child in a list should have a unique "key" prop.
```
**Solution:** Add `key` prop to elements in your `.map()`

---

**Problem: React warns about duplicate keys**
```
Warning: Encountered two children with the same key
```
**Solution:** Ensure keys are unique. Check your ID generation logic.

---

**Problem: List items show wrong data after reordering**
**Cause:** Using index as key
**Solution:** Use stable unique IDs instead of index

---

**Problem: Component state gets mixed up between list items**
**Cause:** Keys are changing (usually index as key)
**Solution:** Use stable IDs that don't change when list reorders

---

**Problem: Rendering "0" on screen unexpectedly**
```javascript
{count && <div>You have {count} messages</div>}
// Renders "0" when count is 0
```
**Solution:** Use explicit comparison or boolean conversion
```javascript
{count > 0 && <div>You have {count} messages</div>}
// or
{Boolean(count) && <div>You have {count} messages</div>}
```

---

**Problem: "undefined" being returned from component**
**Cause:** Conditional rendering not returning anything in some cases
**Solution:** Always return null, JSX, or early return
```javascript
// ❌ Bad
function Component({ show }) {
  return show && <div>Content</div>;
  // Returns undefined when show is false
}

// ✅ Good
function Component({ show }) {
  if (!show) return null;
  return <div>Content</div>;
}
```

---

### Performance Considerations

**Filtering and Mapping:**

```javascript
// ✅ Good - filter once, map once
function List({ items }) {
  const activeItems = items.filter(item => item.isActive);
  
  return (
    <ul>
      {activeItems.map(item => (
        <li key={item.id}>{item.name}</li>
      ))}
    </ul>
  );
}

// ⚠️ Less optimal - filtering on every render
function List({ items }) {
  return (
    <ul>
      {items.filter(item => item.isActive).map(item => (
        <li key={item.id}>{item.name}</li>
      ))}
    </ul>
  );
}
// This works fine for small lists, but for large lists:
// Use useMemo if filtering is expensive
```

**Large Lists:**

```javascript
// For very large lists (1000+ items), consider:
import { FixedSizeList } from 'react-window';

function VirtualizedList({ items }) {
  return (
    <FixedSizeList
      height={600}
      itemCount={items.length}
      itemSize={35}
    >
      {({ index, style }) => (
        <div style={style} key={items[index].id}>
          {items[index].name}
        </div>
      )}
    </FixedSizeList>
  );
}
```

---

### Real-World Examples

**Example 1: Todo List with All Features**

```javascript
function TodoApp() {
  const [todos, setTodos] = useState([
    { id: 1, text: 'Learn React', completed: false },
    { id: 2, text: 'Build project', completed: false }
  ]);
  const [filter, setFilter] = useState('all'); // all, active, completed
  
  const addTodo = (text) => {
    const newTodo = {
      id: Date.now(),
      text,
      completed: false
    };
    setTodos([...todos, newTodo]);
  };
  
  const toggleTodo = (id) => {
    setTodos(todos.map(todo =>
      todo.id === id ? { ...todo, completed: !todo.completed } : todo
    ));
  };
  
  const deleteTodo = (id) => {
    setTodos(todos.filter(todo => todo.id !== id));
  };
  
  // Filter todos based on current filter
  const filteredTodos = todos.filter(todo => {
    if (filter === 'active') return !todo.completed;
    if (filter === 'completed') return todo.completed;
    return true; // 'all'
  });
  
  return (
    <div>
      <h1>Todo List</h1>
      
      {/* Filter buttons */}
      <div>
        <button onClick={() => setFilter('all')}>All</button>
        <button onClick={() => setFilter('active')}>Active</button>
        <button onClick={() => setFilter('completed')}>Completed</button>
      </div>
      
      {/* Todo list */}
      {filteredTodos.length === 0 ? (
        <p>No todos to display</p>
      ) : (
        <ul>
          {filteredTodos.map(todo => (
            <li key={todo.id}>
              <input
                type="checkbox"
                checked={todo.completed}
                onChange={() => toggleTodo(todo.id)}
              />
              <span style={{ 
                textDecoration: todo.completed ? 'line-through' : 'none' 
              }}>
                {todo.text}
              </span>
              <button onClick={() => deleteTodo(todo.id)}>Delete</button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
```

**Example 2: User Dashboard with Conditional UI**

```javascript
function UserDashboard({ user }) {
  // Early return for loading
  if (!user) {
    return <div>Loading user data...</div>;
  }
  
  // Early return for error state
  if (user.error) {
    return <div>Error: {user.error}</div>;
  }
  
  // Main render
  return (
    <div>
      <h1>Welcome, {user.name}</h1>
      
      {/* Conditional badge */}
      {user.isPro && <span className="badge">PRO</span>}
      
      {/* Conditional admin panel */}
      {user.role === 'admin' && <AdminPanel />}
      
      {/* Notifications with count */}
      {user.notifications.length > 0 && (
        <div>
          You have {user.notifications.length} new notifications
        </div>
      )}
      
      {/* Recent activity list */}
      <h2>Recent Activity</h2>
      {user.recentActivity.length === 0 ? (
        <p>No recent activity</p>
      ) : (
        <ul>
          {user.recentActivity.map(activity => (
            <li key={activity.id}>
              {activity.description} - {activity.timestamp}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
```

**Example 3: E-commerce Product List**

```javascript
function ProductList({ products, onAddToCart }) {
  const [sortBy, setSortBy] = useState('name'); // name, price-low, price-high
  const [category, setCategory] = useState('all');
  
  // Filter by category
  let filteredProducts = products.filter(product => 
    category === 'all' || product.category === category
  );
  
  // Sort products
  const sortedProducts = [...filteredProducts].sort((a, b) => {
    if (sortBy === 'name') {
      return a.name.localeCompare(b.name);
    }
    if (sortBy === 'price-low') {
      return a.price - b.price;
    }
    if (sortBy === 'price-high') {
      return b.price - a.price;
    }
    return 0;
  });
  
  return (
    <div>
      {/* Controls */}
      <div>
        <select value={category} onChange={(e) => setCategory(e.target.value)}>
          <option value="all">All Categories</option>
          <option value="electronics">Electronics</option>
          <option value="clothing">Clothing</option>
          <option value="books">Books</option>
        </select>
        
        <select value={sortBy} onChange={(e) => setSortBy(e.target.value)}>
          <option value="name">Name</option>
          <option value="price-low">Price: Low to High</option>
          <option value="price-high">Price: High to Low</option>
        </select>
      </div>
      
      {/* Product grid */}
      {sortedProducts.length === 0 ? (
        <p>No products found</p>
      ) : (
        <div className="product-grid">
          {sortedProducts.map(product => (
            <div key={product.id} className="product-card">
              <img src={product.image} alt={product.name} />
              <h3>{product.name}</h3>
              <p>${product.price.toFixed(2)}</p>
              
              {/* Stock indicator */}
              {product.inStock ? (
                <span className="in-stock">In Stock</span>
              ) : (
                <span className="out-of-stock">Out of Stock</span>
              )}
              
              {/* Sale badge */}
              {product.onSale && (
                <span className="sale-badge">Sale!</span>
              )}
              
              <button 
                onClick={() => onAddToCart(product)}
                disabled={!product.inStock}
              >
                {product.inStock ? 'Add to Cart' : 'Unavailable'}
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
```

---
