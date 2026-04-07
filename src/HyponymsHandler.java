package main;
import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
public class HyponymsHandler extends NgordnetQueryHandler {
    private final NGramMap ngMap;
    private final WordNet wnet;
    public HyponymsHandler(NGramMap ngMap, WordNet wnet) {
        this.ngMap = ngMap;
        this.wnet = wnet;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> totalhyponyms = wnet.hyponyms(q.words());
        if (q.k() == 0) {
            return totalhyponyms.toString();
        }
        int start = q.startYear();
        int end = q.endYear();
        int k = q.k();
        Map<String, Double> counts = new HashMap<>();
        List<String> candidates = new ArrayList<>();
        for (String w : totalhyponyms) {
            double total = totalCount(w, start, end);
            if (total > 0) {
                counts.put(w, total);
                candidates.add(w);
            }
        }
        List<String> result = new ArrayList<>();
        int max = Math.min(k, candidates.size());
        for (int i = 0; i < max; i++) {
            String best = candidates.get(0);
            for (String w : candidates) {
                if (counts.get(w) > counts.get(best)) {
                    best = w;
                }
            }
            result.add(best);
            candidates.remove(best);
        }
        Collections.sort(result);
        return result.toString();
    }
    private double totalCount(String word, int start, int end) {
        double result = 0.0;
        for (double count : ngMap.countHistory(word, start, end).values()) {
            result += count;
        }
        return result;
    }
}
