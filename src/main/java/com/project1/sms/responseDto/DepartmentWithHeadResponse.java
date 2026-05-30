package com.project1.sms.responseDto;


public record DepartmentWithHeadResponse(
        Long departmentId,
        String departmentName,
        Long headId,
        String headFullName

) {

}
