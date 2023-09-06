package org.example.GameBody;

import org.example.FrontEnd.FrontEnd;

import java.util.*;
import java.util.stream.Collectors;

public class GameBody {
    private static final GameBody gameBody = new GameBody();
    private String lastLetter;
    private Set<String> allCities;
    private String city;

    public String getCity() {
        return city;
    }

    public void setLastLetter(String lastLetter) {
        this.lastLetter = lastLetter;
    }

    public void setAllCities(Set<String> allCities) {
        this.allCities = new HashSet<>(allCities);
        this.allCities = this.allCities.stream()
                .map(n -> n = n.toLowerCase())
                .collect(Collectors.toSet());
    }

    public Set<String> getAllCities() {
        return allCities;
    }

    private GameBody() {
    }

    public static GameBody gameBody() {
        return gameBody;
    }

    public List<String> matchCity() {
        ArrayList<String> listOfCities = new ArrayList<>(allCities);
        List<String> matchingCities = listOfCities.stream()
                .filter(n -> String.valueOf(n.charAt(0)).equals(lastLetter))
                .collect(Collectors.toList());
        return matchingCities;
    }

    public ArrayList<String> notUsed() {
        ArrayList<String> notUsedCities = new ArrayList<>();
        for (String city : gameBody().matchCity()) {
            if (!FrontEnd.listOfCities.contains(city)) {
                notUsedCities.add(city);
            }
        }
        return notUsedCities;
    }

    public String makeMove() {
        Optional<ArrayList<String>> optional = Optional.of(gameBody().notUsed());
        ArrayList<String> surrender = new ArrayList<>();
        surrender.add("здаюсь");
        Optional<ArrayList<String>> optional2 = Optional.of(surrender);
        city = optional.or(() -> optional2).get().get(0);
        FrontEnd.listOfCities.add(city);
        return city;
    }
}
