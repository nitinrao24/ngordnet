package main;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DirectedGraph {
    private final Map<Integer, Set<Integer>> edgeMap;
    public DirectedGraph() {
        edgeMap = new HashMap<>();
    }
    public void addANode(int n) {
        if (!(edgeMap.containsKey(n))) {
            edgeMap.put(n, new HashSet<>());
        }
    }
    public void addAEdge(int from, int to) {
        addANode(from);
        addANode(to);
        edgeMap.get(from).add(to);
    }
    public Set<Integer> canReach(Set<Integer> start) {
        Set<Integer> done = new HashSet<>();
        ArrayDeque<Integer> toVisit = new ArrayDeque<>();
        for (int i : start) {
            toVisit.addLast(i);
        }
        while (!(toVisit.isEmpty())) {
            int current = toVisit.removeFirst();
            if (done.contains(current)) {
                continue;
            }
            done.add(current);
            for (int next : edgeMap.getOrDefault(current, new HashSet<>())) {
                if (!(done.contains(next))) {
                    toVisit.addLast(next);
                }
            }
        }
        return done;
    }
}
