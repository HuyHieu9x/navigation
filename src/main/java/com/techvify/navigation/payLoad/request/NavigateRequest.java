package com.techvify.navigation.payLoad.request;

import com.techvify.navigation.validation.NavigateNameUnique;
import javax.validation.constraints.NotBlank;

public class NavigateRequest {
    @NotBlank(message = "{navigate.name.not-blank}")
    @NavigateNameUnique
    private String name;
    @NotBlank(message = "{navigate.link.not-blank}")
    private String link;

    public NavigateRequest() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
