package main.model;

import lombok.*;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.persistence.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "intervals")
public class Interval {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    @Column
    //@Nonnull
    @Nonnegative
    private Integer length;

//    @Column
//    @Nonnegative
//    private Integer index;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sequence_id", nullable = false)
    private Sequence sequence;
}
