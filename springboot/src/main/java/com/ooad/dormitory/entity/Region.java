package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Region {
    @TableId
    private Integer regionId;
    private String regionName;
}

