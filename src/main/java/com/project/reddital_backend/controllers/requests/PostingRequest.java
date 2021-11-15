package com.project.reddital_backend.controllers.requests;

import com.project.reddital_backend.exceptions.BadParametersException;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
public class PostingRequest extends Request {

    private String title;

    private String content;

    private String subReddit;

    private String authenticationKey;

    @Override
    public void validate() throws BadParametersException {
        String ans = null;

        if(nullOrEmpty(title)) {
            ans = "title is not valid";
        } else  if(nullOrEmpty(content)) {
            ans = "content is not valid";
        } else  if(nullOrEmpty(subReddit)) {
            ans = "subReddit is not valid";
        } else  if(nullOrEmpty(authenticationKey)) {
            ans = "authenticationKey is not valid";
        }

        if(ans != null){
            throw new BadParametersException(ans);
        }
    }
}
