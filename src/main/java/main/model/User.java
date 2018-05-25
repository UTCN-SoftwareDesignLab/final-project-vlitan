package main.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
//import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column
    @NonNull
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$", message = "Invalid email")
    private String email;

    @Column
    @NonNull
    @Length(min = 4, message = "Password is too short")
    private String password;

    @Column
    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    private List<Sequence> sequences = new ArrayList<>();

    @Column
    private boolean enabled;

    public void insertSequence(Sequence sequence){
        sequence.setUser(this);
        sequences.add(sequence);
    }
}
