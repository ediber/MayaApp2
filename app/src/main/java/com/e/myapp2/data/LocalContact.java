package com.e.myapp2.data;

public class LocalContact extends Contact implements Comparable{
    private String name;

    private boolean isSelected;

    public LocalContact(String name, String phoneNumber) {
        super(phoneNumber);
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public int compareTo(Object other) {
        String otherName = ((LocalContact)other).getName();
        String otherLetter = otherName.substring(0,2);
        String letter = name.substring(0,2);
        int res = letter.compareTo(otherLetter);
        return res;
    }
}
