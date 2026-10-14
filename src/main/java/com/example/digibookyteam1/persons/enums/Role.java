package com.example.digibookyteam1.persons.enums;

import java.util.List;

public enum Role {
    BASE_MEMBER(List.of(Feature.BASE_ACCESS)),
    ADMIN(List.of(Feature.VIEW_ALL_MEMBERS, Feature.REGISTER_ADMIN_LIBRARIAN)),
    LIBRARIAN(List.of(Feature.BASE_ACCESS));

    private List<Feature> features;
    Role(List<Feature> features){
        this.features = features;
    }

    public boolean containsFeature(Feature feature) {
        return features.contains(feature);
    }
}
