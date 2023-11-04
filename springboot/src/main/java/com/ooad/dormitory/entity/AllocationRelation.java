package com.ooad.dormitory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AllocationRelation {
    private Integer entryYear;
    private Integer degree;
    private Integer gender;
    private Integer dormitoryId;
}
