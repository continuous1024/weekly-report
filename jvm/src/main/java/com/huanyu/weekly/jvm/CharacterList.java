package com.huanyu.weekly.jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharacterList {
    public static void main(String[] args) {
        List<Character> characters = Arrays.asList('a', 'b', 'c');
        for (String line : getPailie(characters)) {
            System.out.println(line);
        }
    }

    private static List<String> getPailie(List<Character> characters) {
        List<String> result = new ArrayList<String>();

        getOnePailie(result, characters, 0, characters.size() - 1);

        return result;
    }

    private static void getOnePailie(List<String> result, List<Character> characters, int start, int end) {
        if (start == end) {
            result.add(characters.toString());
        }

        for (int i = start; i <= end; i++) {
            swapCharacter(characters, start, i);
            getOnePailie(result, characters, start + 1, end);
            swapCharacter(characters, start, i);
        }
    }

    private static void swapCharacter(List<Character> characters, int start, int swapIndex) {
        Character temp = characters.get(swapIndex);
        characters.set(swapIndex, characters.get(start));
        characters.set(start, temp);
    }

}
