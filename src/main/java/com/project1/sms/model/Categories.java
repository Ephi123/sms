package com.project1.sms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Categories extends Auditable{

     private String bookCategory;

     @OneToMany(mappedBy = "categories")
     List<Books> booksList = new ArrayList<>();

     public Categories(String bookCategory){
         this.bookCategory = bookCategory;

    }

}
