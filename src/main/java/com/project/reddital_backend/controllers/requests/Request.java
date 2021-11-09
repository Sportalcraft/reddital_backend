package com.project.reddital_backend.controllers.requests;

import com.project.reddital_backend.exceptions.BadParametersException;
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
     * check if a string have white spaces
     * @param val the string to check
     * @return true uf it have white spaces, false otherwise
     */
    protected boolean haveWhiteSpaces(String val) {
        if(val == null) {
            return false;
        }

        return !val.equals(removeWhiteSpace(val));
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
     * check if this request is valid or not.
     * @throws BadParametersException if not
     */
    public abstract void validate() throws BadParametersException;
}
