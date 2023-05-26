package com.dagy.cafemania.categories;

import com.dagy.cafemania.shared.abstracts.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Builder
@Table(name = " t_category")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Category extends AbstractEntity {
    private String name;

    private String description;
}
