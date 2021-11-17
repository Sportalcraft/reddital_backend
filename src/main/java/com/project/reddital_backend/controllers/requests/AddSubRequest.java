package com.project.reddital_backend.controllers.requests;

import com.project.reddital_backend.exceptions.BadParametersException;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
public class AddSubRequest  extends Request{

    private String name;


    @Override
    public void validate() throws BadParametersException {
        String ans = null;

        if(nullOrEmpty(name)) {
            ans = "name of subreddital is not valid";
        }

        if(ans != null){
            throw new BadParametersException(ans);
        }
    }
}
