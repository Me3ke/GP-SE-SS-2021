package gpse.example.web;

import com.fasterxml.jackson.databind.util.JSONPObject;

public class JSONResponseObject {

    private int status;

    public JSONResponseObject(){

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int statusCode) {
        this.status = statusCode;
    }

}

class JSONConfirmationResponseObject extends JSONResponseObject {

    boolean adminValidation;
    String email;

    public JSONConfirmationResponseObject() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdminValidation() {
        return adminValidation;
    }

    public void setAdminValidation(boolean adminValidation) {
        this.adminValidation = adminValidation;
    }
}
