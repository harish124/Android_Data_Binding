package com.example.latlongmapsspeech.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Country {
    public static final String countryName = "India";
    public static final String countryKey = "country";

    private static final float minConfidenceLevel = 0.4f;
    //Madhya Pradesh's lat and long used below
    private static final double lattitude = 22.784318;
    private static final double longitude = 79.823116;

    private Hashtable<String, String> countriesAndMsgs;

    public Country(Hashtable<String, String> countriesAndMsgs) {
        this.countriesAndMsgs = countriesAndMsgs;
    }

    public String matchCounryWithMinConfid(ArrayList<String> userWords, float[] confidLevels) {
        if (userWords == null && confidLevels == null) {
            return countryName;
        }

        int noOfUserWords = userWords.size();

        Enumeration<String> countries;

        for (int index = 0; index < confidLevels.length && index < noOfUserWords; index++) {
            if (confidLevels[index] < minConfidenceLevel) {
                break;
            }
            String acceptedUserWord = userWords.get(index);
            countries = countriesAndMsgs.keys();

            while (countries.hasMoreElements()) {
                String selectedCountry = countries.nextElement();
                if (acceptedUserWord.equalsIgnoreCase(selectedCountry)) {
                    return acceptedUserWord;
                }
            }
        }

        return countryName;
    }

    public String getCountryInfo(String country) {
        return countriesAndMsgs.get(country);
    }
}
