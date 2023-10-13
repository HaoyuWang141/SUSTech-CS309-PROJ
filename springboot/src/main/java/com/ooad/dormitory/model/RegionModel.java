package com.ooad.dormitory.model;

import com.ooad.dormitory.entity.Region;

public class RegionModel {
    private Region region;

    public RegionModel(Region region) {
        this.region = region;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
