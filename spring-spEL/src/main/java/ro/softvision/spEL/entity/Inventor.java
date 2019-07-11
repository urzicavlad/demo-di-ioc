package ro.softvision.spEL.entity;

import java.time.LocalDate;

public class Inventor {

    private String name;
    private String nationality;
    private String[] inventions;
    private LocalDate birthdate;
    private PlaceOfBirth placeOfBirth;

    public Inventor(String name, String nationality) {
        this.name = name;
        this.nationality = nationality;
        this.birthdate = LocalDate.now();
    }

    public Inventor(String name, LocalDate birthdate, String nationality, PlaceOfBirth placeOfBirth) {
        this.name = name;
        this.nationality = nationality;
        this.birthdate = birthdate;
        this.placeOfBirth = placeOfBirth;
    }

    public Inventor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public PlaceOfBirth getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(PlaceOfBirth placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public void setInventions(String[] inventions) {
        this.inventions = inventions;
    }

    public String[] getInventions() {
        return inventions;
    }
}