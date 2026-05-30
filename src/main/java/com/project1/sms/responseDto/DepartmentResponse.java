package com.project1.sms.responseDto;

import com.project1.sms.model.Department;

public record DepartmentResponse(
        String department,
        Long departmentId
) {
    public static DepartmentResponse from(Department department){
        return  new DepartmentResponse(
                department.getDepName(),
                department.getId()
        );

    }
}
