# ngordnet
Ngordnet: Graph-Based WordNet Query Engine 📚
🚀 Overview

Ngordnet is a high-performance semantic query engine that models language as a directed graph and enables efficient retrieval of hyponyms (word descendants) using graph traversal algorithms. The system extends beyond static graph queries by integrating temporal word frequency data, allowing ranked results based on real-world usage trends.

At its core, this project combines:

Graph theory (directed graphs, BFS traversal)
Data structures (hash maps, sets, queues)
Algorithmic design (multi-source traversal, set intersection)
Data analytics (time-series aggregation using N-grams)

The result is a system capable of answering both:

Structural queries → "What are all hyponyms of a word?"
Analytical queries → "What are the top k most frequent hyponyms over time?"
✨ Key Features
🔗 Graph-Based Semantic Modeling
Constructs a directed graph of WordNet synsets
Supports multi-source BFS traversal to compute reachability
Handles polysemy (words belonging to multiple synsets)
⚡ Efficient Hyponym Queries
Single-word queries return full descendant sets
Multi-word queries compute set intersections of hyponyms
Ensures deduplication and correctness via hash-based structures
📈 Temporal Ranking with N-Grams
Integrates time-series word frequency data
Computes aggregate counts over a year range
Returns top-k most relevant words based on real-world usage
🧠 Clean Separation of Concerns
WordNet → graph construction + traversal logic
DirectedGraph → reusable BFS engine
HyponymsHandler → query parsing + ranking logic
NGramMap → time-series frequency retrieval
🏗️ System Architecture
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
🔍 How It Works
Step 1: Build the Graph
Parse synsets and map:
word → set of IDs
ID → set of words
Construct adjacency list from hyponym relationships
Step 2: Graph Traversal
Perform multi-source BFS starting from all synsets containing the input word(s)
Collect all reachable nodes (descendants)
Step 3: Word Extraction
Convert reachable synset IDs → corresponding words
Deduplicate using sets
Step 4: Query Logic
Case 1: k = 0
Return all hyponyms
Ignore time range
Sort alphabetically
Case 2: k > 0

Compute total frequency:

sum(countHistory(word, startYear, endYear))
Filter out zero-frequency words
Select top k by frequency
Sort final results alphabetically
🧩 Algorithms & Data Structures
Component	Technique
Graph traversal	Breadth-First Search (BFS)
Multi-source traversal	Queue initialization with multiple nodes
Word mapping	HashMap<String, Set<Integer>>
Deduplication	HashSet
Ranking	Greedy top-k selection
Time-series aggregation	TreeMap iteration
⚡ Performance Considerations
BFS traversal runs in O(V + E)
Hash-based lookups provide O(1) average access
Efficient handling of large datasets (80k+ synsets)
Avoids redundant computation via set operations
📁 Project Structure
ngordnet/
├── WordNet.java              # Core semantic graph logic
├── DirectedGraph.java        # BFS traversal engine
├── HyponymsHandler.java      # Query processing + ranking
├── AutograderBuddy.java      # System initialization
├── NGramMap.java             # Time-series word frequency
├── synsets.txt               # WordNet data
├── hyponyms.txt              # Graph edges
├── word_history.csv          # N-gram data
├── year_history.csv          # Year mappings
├── README.md
🧪 Example Queries

Input:

words = ["food", "cake"], k = 5, years = [1950, 1990]

Output:

[cake, cookie, kiss, snap, wafer]
