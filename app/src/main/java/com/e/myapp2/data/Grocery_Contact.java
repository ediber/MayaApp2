package com.e.myapp2.data;

class Grocery_Contact {
    private String groceryId;
    private String contactPhone;

    public Grocery_Contact(String groceryId, String contactPhone) {
        this.groceryId = groceryId;
        this.contactPhone = contactPhone;
    }

    public String getGroceryId() {
        return groceryId;
    }

    public void setGroceryId(String groceryId) {
        this.groceryId = groceryId;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
