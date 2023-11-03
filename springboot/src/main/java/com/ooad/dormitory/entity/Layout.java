package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Layout {
    @TableId(type = IdType.AUTO)
    private Integer layoutId;
    private String layoutName;
    private String description;
    private String imageUrl;
}

