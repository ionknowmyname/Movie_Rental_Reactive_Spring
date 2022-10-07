package com.faithfulolaleru.movieRentalReactive.models;

import com.faithfulolaleru.movieRentalReactive.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "roles-reactive")
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    private String id;

    private RoleName name;
}
