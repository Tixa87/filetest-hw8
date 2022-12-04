package ru.tixa.model;

import com.google.gson.annotations.SerializedName;

public class Datajson {

    public static class events_data {
        public static String id;
        public static String client_id;
        public String user_id;

        public static String id() {
            return id;
        }

        public static String client_id() {
            return client_id;
        }
    }
}
