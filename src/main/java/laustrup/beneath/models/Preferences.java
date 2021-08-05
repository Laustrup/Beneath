package laustrup.beneath.models;

import laustrup.beneath.models.enums.Gender;

public class Preferences {

    private int[] ages;
    private Gender[] genders;
    private int repoId = -1;

    public Preferences(int[] ages, Gender[] genders) {
        this.ages = ages;
        this.genders = genders;
    }

    // Generates automatically ages from the current age
    public Preferences(int age, Gender[] genders) {
        this.ages = autoGenerateAge(age);
        this.genders = genders;
    }

    public Preferences() {
        ages = new int[0];
        genders = new Gender[0];
    }

    public int[] getAges() {
        return ages;
    }

    private int[] autoGenerateAge(int age) {
        int[] ages = new int[age-13];
        int index = 0;
        for (int i = 18; i < age+6; i++) {
            ages[index] = i;
        }
        return ages;
    }

    public boolean isAgeAPreference(int age) {
        for (int i = 0; i < ages.length; i++) {
            if (ages[i]==age) {
                return true;
            }
        }
        return false;
    }

    public void setAges(int[] ages) {
        this.ages = ages;
    }

    public Gender[] getGenders() {
        return genders;
    }

    public boolean isGenderAPreference(Gender gender) {
        for (int i = 0; i < genders.length; i++) {
            if (genders[i]==gender) {
                return true;
            }
        }
        return false;
    }

    public void setGenders(Gender[] genders) {
        this.genders = genders;
    }

    public Gender[] addGender(Gender gender) {
        Gender[] temp = new Gender[genders.length+1];
        for (int i = 0; i < temp.length; i++) {
            if (!(i > genders.length)) {
                temp[i] = genders[i];
            }
            else {
                temp[i] = gender;
            }
        }
        genders = temp;
        return genders;
    }

    // Removes a gender, if the gender input is the only gender of the array, preference is asexual. Returns null if it's not in array
    public Gender[] removeGender(Gender gender) {
        Gender[] current = genders;
        if (!(genders.length < 1)) {
            boolean isInArray = false;
            Gender[] temp = new Gender[genders.length-1];
            for (int i = 0; i < temp.length; i++) {
                if (genders[i] == gender) {
                    isInArray = true;
                    i++;
                }
                if (!isInArray) {
                    temp[i] = genders[i];
                }
                else {
                    temp[i-1] = genders[i];
                }
            }
            if(isInArray) {
                genders = temp;
            }
            else {
                genders = current;
            }
            return genders;
        }
        else {
            if (genders[0]==gender) {
                genders = new Gender[0];
                return genders;
            }
            else {
                return null;
            }
        }
    }

    public int getRepoId() {
        return repoId;
    }

    public void setRepoId(int repoId) {
        this.repoId = repoId;
    }
}
