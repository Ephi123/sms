//package fileManagment.file.entity;
//
//import fileManagment.file.responseDto.StudentResultDto;
//import jakarta.persistence.ColumnResult;
//import jakarta.persistence.ConstructorResult;
//import jakarta.persistence.Entity;
//import jakarta.persistence.SqlResultSetMapping;
//
//@SqlResultSetMapping(name = "StudentResultDtoMapping",
//        classes =
//                {
//                        @ConstructorResult(
//                                targetClass = StudentResultDto.class,
//                                columns = {
//                                        @ColumnResult(name ="uid", type = String.class),
//                                        @ColumnResult(name ="fName", type = String.class),
//                                        @ColumnResult(name ="lName", type = String.class),
//                                        @ColumnResult(name ="room", type = String.class),
//                                        @ColumnResult(name ="sem", type = Integer.class),
//                                        @ColumnResult(name ="ay", type = Integer.class),
//                                        @ColumnResult(name ="average", type = Double.class),
//                                        @ColumnResult(name ="std_rank", type = Integer.class)
//                                }
//                        )
//                })
//@Entity
//public class StudentResultEntity {
//}
