package laustrup.beneath.services;

import laustrup.beneath.models.User;

public class Analyst {

    // Checks if length of input is too long for the field in db,
    // if true it returns the allowed length, otherwise "Length is allowed"
    public String isLengthAllowedInDb(String input, String column) {

        String[] slicedColumn = new String[3];
        boolean columnIsSliced = false;

        if (column.contains("_")) {
        slicedColumn = input.split("_");
        columnIsSliced = true;
        }

        String res = checkInputForDb(input,column,slicedColumn,columnIsSliced);

        if (!(res.equals("Length is allowed"))) {
            return res;
        }
        return "Length is allowed";
    }
    private String checkInputForDb(String input, String column, String[] slicedColumn, boolean columnIsSliced) {
        if (input.length() > 500 && (column.equals("user_description") || column.equals("user_description"))) {
            return "Length is too long, limit is 500 characters";
        }
        if (input.length() > 50 && (column.equals("email") || columnIsSliced && slicedColumn[0].equals("email"))) {
            return "Length is too long, limit is 50 characters";
        }
        if (input.length() > 50 && (column.equals("education") || column.equals("work"))) {
            return "Length is too long, limit is 50 characters";
        }
        if (input.length() > 30 && columnIsSliced && slicedColumn[1].equals("title")) {
            return "Length is too long, limit is 30 characters";
        }
        if (input.length() > 20 && column.equals("password")) {
            return "Length is too long, limit is 15 characters";
        }
        if (input.length() > 15 && (column.equals("username") || column.equals("author"))) {
            return "Length is too long, limit is 15 characters";
        }
        return "Length is allowed";
    }

    public boolean isEmailValid(String address) {
        if (address.contains("@")) {
            String[] splitted = address.split("@");

            String splittedEnd = splitted[1];
            String afterDot = new String();
            boolean addToString = false;

            for (int i = 0; i < splittedEnd.length();i++) {
                if (addToString) {
                    afterDot += splittedEnd.charAt(i);
                }
                if (splittedEnd.charAt(i) == '.') {
                    addToString = true;
                }
            }

            if (address.contains(".") && splitted.length < 3 && afterDot.length() < 4) {
                return true;
            }
        }
        return false;
    }

    public boolean isDateTimeCorrectFormat(String dateTime) {
        if(dateTime.contains("-")&&dateTime.contains(":")&&dateTime.contains(" ")&&dateTime.length()<19) {
            String[] datesAndTime = dateTime.split(" ");
            String[] dates = datesAndTime[0].split("-");
            String[] times = datesAndTime[1].split(":");

            int year;
            int month;
            int day;

            int hours;
            int minutes;
            int seconds;

            if (!(dates.length==3&&times.length==3)) {
                return false;
            }

            try {
                year = Integer.parseInt(dates[0]);
                month = Integer.parseInt(dates[1]);
                day = Integer.parseInt(dates[2]);

                hours = Integer.parseInt(times[0]);
                minutes = Integer.parseInt(times[1]);
                seconds = Integer.parseInt(times[2]);
            }
            catch (java.lang.Exception e) {
                System.out.println("Couldn't parse dates and times...\n" + e.getMessage());
                return false;
            }

            if (year>10000||month>12||day>31||hours>23||minutes>59||seconds>59) {
                return false;
            }
            return true;
        }
        return false;
    }
    public boolean isDateCorrectFormat(String date) {
        if(date.contains("-")&&date.contains(":")&&date.contains(" ")&&date.length()<19) {
            String[] datesAndTime = date.split(" ");
            String[] dates = datesAndTime[0].split("-");
            String[] times = datesAndTime[1].split(":");

            int year;
            int month;
            int day;

            if (!(dates.length==3&&times.length==3)) {
                return false;
            }

            try {
                year = Integer.parseInt(dates[0]);
                month = Integer.parseInt(dates[1]);
                day = Integer.parseInt(dates[2]);
            }
            catch (java.lang.Exception e) {
                System.out.println("Couldn't parse dates and times...\n" + e.getMessage());
                return false;
            }

            if (year>10000||month>12||day>31) {
                return false;
            }
            return true;
        }
        return false;
    }
    public boolean isEighteenOrAbove(User user) {
        if (user.getAge() >= 18) {
            return true;
        }
        return false;
    }

    public boolean allowLogin(String email, String password) {
        User user = new Gatekeeper().getUser(email);

        if (user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

}
