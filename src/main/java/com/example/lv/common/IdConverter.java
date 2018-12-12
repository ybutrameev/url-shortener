package com.example.lv.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class IdConverter {
    public static final IdConverter INSTANCE = new IdConverter();

    private IdConverter() {
        initializeCharToIndexTable();
        initializeIndexToCharTable();
    }

    private static HashMap<Character, Integer> charToIndexTable;
    private static List<Character> indexToCharTable;

    private void initializeCharToIndexTable() {
        charToIndexTable = new HashMap<>();
        // 0->a, 1->b, ..., 25->z, ..., 52->0, 61->9
        for (int i = 0; i < 26; ++i) {
            char c = 'a';
            c += i;
            charToIndexTable.put(c, i);
        }
        for (int i = 26; i < 52; ++i) {
            char c = 'A';
            c += (i - 26);
            charToIndexTable.put(c, i);
        }
        for (int i = 52; i < 62; ++i) {
            char c = '0';
            c += (i - 52);
            charToIndexTable.put(c, i);
        }
    }

    private void initializeIndexToCharTable() {
        // 0->a, 1->b, ..., 25->z, ..., 52->0, 61->9
        indexToCharTable = new ArrayList<>();

        for (int i = 0; i < 26; ++i) {
            char c = 'a';
            c += i;
            indexToCharTable.add(c);
        }
        for (int i = 26; i < 52; ++i) {
            char c = 'A';
            c += (i - 26);
            indexToCharTable.add(c);
        }
        for (int i = 52; i < 62; ++i) {
            char c = '0';
            c += (i - 52);
            indexToCharTable.add(c);
        }
    }

    public static String createUniqueId(Long id) {
        List<Integer> base62Id = convertBase10ToBase62Id(id);
//        if(base62Id.isEmpty()) {
//            return "a";
//        }

        StringBuilder uniqueUrlId = new StringBuilder();

        base62Id.forEach(o -> uniqueUrlId.append(indexToCharTable.get(o)));

        return uniqueUrlId.toString();
    }

    private static List<Integer> convertBase10ToBase62Id(Long id) {
        LinkedList<Integer> digits = new LinkedList<>();

        if(id == 0) {
            digits.addFirst(0);
            return digits;
        }

        while (id > 0) {
            int remainder = (int)(id % 62);
            digits.addFirst(remainder);
            id /= 62;
        }

        return digits;
    }

    public static Long getDictionaryKeyFromUniqueId(String uniqueId) {
        List<Character> base62IDs = new ArrayList<>();

        for (int i = 0; i < uniqueId.length(); ++i) {
            base62IDs.add(uniqueId.charAt(i));
        }

        return convertBase62ToBase10Id(base62IDs);
    }

    private static Long convertBase62ToBase10Id(List<Character> ids) {
        long id = 0L;

        for (int i = 0, exp = ids.size() - 1; i < ids.size(); ++i, --exp) {
            int base10 = charToIndexTable.get(ids.get(i));
            id += (base10 * Math.pow(62.0, exp));
        }

        return id;
    }
}
