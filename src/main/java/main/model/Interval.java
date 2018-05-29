package main.model;

import lombok.*;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.Observable;

@Getter
@Setter
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
    private int length;

    @Column
    @Nonnegative
    private int currentMoment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sequence_id", nullable = false)
    private Sequence sequence;

    public boolean hasCompleted(){
        return this.getCurrentMoment() >= this.getLength();
    }

}
