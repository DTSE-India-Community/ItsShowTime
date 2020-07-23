package com.huawei.ist.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utility {
    private static List<HashMap<String, Object>> movieHashMapList = new ArrayList<HashMap<String, Object>>();

    public static List<HashMap<String,Object>> getMovieList(){
        movieHashMapList.clear();
        HashMap<String, Object> movieOne = new HashMap<String, Object>();
        movieOne.clear();
        movieOne.put("movie", "Dil Bechara");
        movieOne.put("banner", "Fox Star Studios");
        movieOne.put("director","Mukesh Chhabra");
        movieOne.put("producer", "Fox Star Studios");
        movieOne.put("cast", "Sushant Singh Rajput , Sanjana Sanghi, Saif Ali Khan");
        movieOne.put("poster","poster1");
        movieHashMapList.add(movieOne);

        HashMap<String, Object> movieTwo = new HashMap<String, Object>();
        movieTwo.clear();
        movieTwo.put("movie", "Yaara");
        movieTwo.put("banner", "Azure Entertainment");
        movieTwo.put("director","Tigmanshu Dhulia");
        movieTwo.put("producer", "Sunir Kheterpal , Tigmanshu Dhulia");
        movieTwo.put("cast", "Vidyut Jamwal , Amit Sadh, Vijay Varma");
        movieTwo.put("poster","poster2");
        movieHashMapList.add(movieTwo);

        HashMap<String, Object> movieThree = new HashMap<String, Object>();
        movieThree.clear();
        movieThree.put("movie", "Raat Akeli Hai");
        movieThree.put("banner", "RSVP");
        movieThree.put("director","Honey Trehan");
        movieThree.put("producer", "Ronnie Screwvala, Abhishek Chaubey");
        movieThree.put("cast", "Nawazuddin Siddiqui , Radhika Apte, Tigmanshu Dhulia");
        movieThree.put("poster","poster3");
        movieHashMapList.add(movieThree);

        HashMap<String, Object> movieFour = new HashMap<String, Object>();
        movieFour.clear();
        movieFour.put("movie", "Shakuntala Devi");
        movieFour.put("banner", "Sony Pictures Networks Productions");
        movieFour.put("director","Anu Menon");
        movieFour.put("producer", "Sony Pictures Networks Productions , Vikram Malhotra");
        movieFour.put("cast", "Vidya Balan , Sanya Malhotra, Amit Sadh");
        movieFour.put("poster","poster4");
        movieHashMapList.add(movieFour);

        HashMap<String, Object> movieFive = new HashMap<String, Object>();
        movieFive.clear();
        movieFive.put("movie", "Lootcase");
        movieFive.put("banner", "Fox Star Studios, Soda Films");
        movieFive.put("director","Rajesh Krishnan");
        movieFive.put("producer", "Fox Star Studios");
        movieFive.put("cast", "Kunal Khemu , Rasika Duggal, Gajraj Rao");
        movieFive.put("poster","poster5");
        movieHashMapList.add(movieFive);

        return movieHashMapList;

    }


}
