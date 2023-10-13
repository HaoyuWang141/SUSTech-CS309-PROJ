package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class Layout {
    @TableId(type = IdType.AUTO)
    private Integer layoutId;
    private String layoutName;
    private String description;
    private String imageUrl;

    public Layout() {
    }

    public Layout(Integer layoutId, String layoutName, String description, String imageUrl) {
        this.layoutId = layoutId;
        this.layoutName = layoutName;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Integer getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Integer layoutId) {
        this.layoutId = layoutId;
    }

    public String getLayoutName() {
        return layoutName;
    }

    public void setLayoutName(String layoutName) {
        this.layoutName = layoutName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

