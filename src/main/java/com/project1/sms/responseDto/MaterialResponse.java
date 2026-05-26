package com.project1.sms.responseDto;

import com.project1.sms.model.Material;

import java.time.LocalDateTime;

public record MaterialResponse(Long teacherId,
                               Long materialId,
                               String teacherName,
                               String CourseName,
                               String title,
                               String description,
                               String fileName,
                               String fileType,
                               String fileUrl,
                               LocalDateTime postedAt

) {
    public static MaterialResponse from(Material material){
        return new MaterialResponse(
               material.getId(),
               material.getTeacher().getId(),
               material.getTeacher().getUser().getFirstName() +" "+material.getTeacher().getUser().getMidlName(),
                material.getCourseOffering().getCourse().getCourseName(),
                material.getTitle(),
                material.getDescription(),
                material.getFileName(),
                material.getFileType(),
                material.getFileUrl(),
                material.getPostedAt()
        );
    }

}
