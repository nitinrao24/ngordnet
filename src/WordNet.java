package main;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordNet {
    private final Map<String, Set<Integer>> wordstoIDs;
    private final Map<Integer, Set<String>> idsToWords;
    private final DirectedGraph graph;
    public WordNet(String synsetsFile, String hyponymsFile) {
        wordstoIDs = new HashMap<>();
        idsToWords = new HashMap<>();
        graph = new DirectedGraph();
        readSynsets(synsetsFile);
        readHyponyms(hyponymsFile);
    }
    private void readSynsets(String synsetsFile) {
        In synsets = new In(synsetsFile);
        while (synsets.hasNextLine()) {
            String l = synsets.readLine();
            String[] parts = l.split(",", 3);
            int id = Integer.parseInt(parts[0]);
            String[] words = parts[1].split(" ");
            graph.addANode(id);
            Set<String> wordsSynset = new HashSet<>();
            for (String w : words) {
                wordsSynset.add(w);
                if (!wordstoIDs.containsKey(w)) {
                    wordstoIDs.put(w, new HashSet<>());
                }
                wordstoIDs.get(w).add(id);
            }
            idsToWords.put(id, wordsSynset);
        }
    }
    private void readHyponyms(String hyponymsFile) {
        In hyponyms = new In(hyponymsFile);
        while (hyponyms.hasNextLine()) {
            String l = hyponyms.readLine();
            String[] parts = l.split(",");
            int from = Integer.parseInt(parts[0]);
            for (int i = 1; i < parts.length; i++) {
                int to = Integer.parseInt(parts[i]);
                graph.addAEdge(from, to);
            }
        }
    }
    public List<String> hyponyms(List<String> words) {
        if (words == null || words.isEmpty()) {
            return new ArrayList<>();
        }
        Set<String> result = null;
        for (String w : words) {
            Set<String> wordHyponyms = hyponymsOfWord(w);
            if (result == null) {
                result = new HashSet<>(wordHyponyms);
            } else {
                result.retainAll(wordHyponyms);
            }

        }
        List<String> resultList = new ArrayList<>(result);
        Collections.sort(resultList);
        return resultList;
    }
    private Set<String> hyponymsOfWord(String w) {
        Set<Integer> ids = wordstoIDs.get(w);
        if (ids == null) {
            return new HashSet<>();
        }
        Set<Integer> possibleIDs = graph.canReach(ids);
        Set<String> result = new HashSet<>();
        for (int id : possibleIDs) {
            result.addAll(idsToWords.get(id));
        }
        return result;
    }
}
