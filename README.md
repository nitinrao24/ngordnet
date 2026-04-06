# Ngordnet: Graph-Based WordNet Query Engine 📚

## 🚀 Overview
Ngordnet is a high-performance semantic query engine that models language as a directed graph and enables efficient retrieval of hyponyms (word descendants) using graph traversal algorithms. The system extends beyond static graph queries by integrating temporal word frequency data, allowing ranked results based on real-world usage trends.

At its core, this project combines graph theory, data structures, and time-series analytics to answer both structural and analytical queries about language.

---

## ✨ Key Features

### 🔗 Graph-Based Semantic Modeling
- Constructs a directed graph of WordNet synsets
- Supports multi-source BFS traversal for efficient reachability
- Handles polysemy (words belonging to multiple meanings/synsets)

### ⚡ Efficient Hyponym Queries
- Single-word queries return full descendant sets
- Multi-word queries compute intersection of hyponyms
- Uses hash-based structures for fast lookup and deduplication

### 📈 Temporal Ranking with N-Grams
- Integrates time-series word frequency data
- Computes aggregate counts over a given year range
- Returns top-k most relevant words based on real-world usage

### 🧠 Clean System Design
- Modular architecture separating graph logic, query handling, and data processing
- Avoids global state for correctness across multiple runs
- Designed to scale to large datasets (80k+ nodes)

---

## 🏗️ System Architecture

```text
User Query
   ↓
HyponymsHandler
   ├── WordNet (Graph Traversal)
   │       └── DirectedGraph (BFS)
   └── NGramMap (Time-Series Data)
   ↓
Rank + Filter + Sort
   ↓
Final Output
```

---

## 🔍 How It Works

### Step 1: Graph Construction
- Parse synsets into mappings:
  - word → set of IDs
  - ID → set of words
- Build adjacency list from hyponym relationships

### Step 2: Graph Traversal
- Perform multi-source BFS from all synsets containing the input word(s)
- Collect all reachable descendant nodes

### Step 3: Word Extraction
- Convert synset IDs into corresponding words
- Deduplicate using sets

### Step 4: Query Handling

#### Case 1: k = 0
- Return all hyponyms
- Ignore time range
- Sort alphabetically

#### Case 2: k > 0
- Compute total frequency for each word:
  - sum(countHistory(word, startYear, endYear))
- Filter out zero-frequency words
- Select top k by frequency
- Sort final results alphabetically

---

## 🧩 Algorithms & Data Structures

| Component | Technique |
|----------|----------|
| Graph traversal | Breadth-First Search (BFS) |
| Multi-source traversal | Queue initialization with multiple nodes |
| Word mapping | HashMap<String, Set<Integer>> |
| Deduplication | HashSet |
| Ranking | Greedy top-k selection |
| Time-series aggregation | TreeMap iteration |

---

## ⚡ Performance

- BFS traversal: O(V + E)
- Hash-based lookups: O(1) average
- Efficient handling of large datasets (80k+ synsets)
- Minimal redundant computation via set operations

---

## 📁 Project Structure

```text
ngordnet/
├── WordNet.java
├── DirectedGraph.java
├── HyponymsHandler.java
├── AutograderBuddy.java
├── NGramMap.java
├── data/
│   ├── synsets.txt
│   ├── hyponyms.txt
│   ├── word_history.csv
│   ├── year_history.csv
├── README.md
```

---

## 🔒 Notes

- Handles edge cases such as missing words and zero-frequency results
- Returns fewer than k results if not enough valid words exist
- Ensures consistent results across multiple queries without shared state

---

## 🧪 Example Queries

### Example 1: All Hyponyms of a Word

**Input**
```text
words = ["change"], k = 0
```

**Output**
```text
[aad, alteration, change, demotion, increase, jump, leap, modification, saltation, transition, variation]
```

---

### Example 2: Multi-Word Query (Intersection)

**Input**
```text
words = ["change", "adjustment"], k = 0
```

**Output**
```text
[alteration, change, modification, variation]
```

---

### Example 3: Top-k Ranked Hyponyms by Usage

**Input**
```text
words = ["food", "cake"], k = 5, startYear = 1950, endYear = 1990
```

**Output**
```text
[cake, cookie, kiss, snap, wafer]
