package com.example.digibookyteam1.persons.enums;

import java.util.List;

public enum Role {
    BASE_MEMBER(List.of(Feature.BASE_ACCESS)),
    ADMIN(List.of(Feature.BASE_ACCESS, Feature.VIEW_ALL_MEMBERS, Feature.REGISTER_ADMIN_LIBRARIAN, Feature.CREATE_BOOK, Feature.DELETE_BOOK, Feature.UPDATE_BOOK)),
    LIBRARIAN(List.of(Feature.BASE_ACCESS, Feature.CREATE_BOOK, Feature.DELETE_BOOK, Feature.UPDATE_BOOK));

    private List<Feature> features;
    Role(List<Feature> features){
        this.features = features;
    }

    public boolean containsFeature(Feature feature) {
        return features.contains(feature);
    }
}
