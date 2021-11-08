package com.project.reddital_backend.controllers.requests;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class Request {

    /**
     * remove white spaces from the given string
     * @param val the string to operate in
     * @return the clean string, null if input is null
     */
    protected String removeWhiteSpace(String val){
        if(val == null) {
            return null;
        }
        return val.replaceAll("\\s+","");
    }

    /**
     * check if a string is empty or null
     * @param val the string to check
     * @return true if null or "", false otherwise
     */
    protected boolean nullOrEmpty(String val) {
        return val == null || val.equals("");
    }

    /**
     * return if this request is valid or not.
     * @return the error message, null if valid
     */
    public abstract String validate();

    /**
     * remove whitespaces from all fields
     */
    public abstract void clean();
}
