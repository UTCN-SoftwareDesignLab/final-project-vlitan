package main.model;

import lombok.*;

import javax.persistence.*;
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sequences")
public class Sequence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "sequence")
    private List<Interval> intervals = new ArrayList<>();

    public void insertInterval(Interval interval, Optional<Integer> optIndex){
        interval.setSequence(this);
        if (optIndex.isPresent()){
           // interval.setIndex(optIndex.get().intValue());
            intervals.add(optIndex.get().intValue(), interval);
        }
        else{
           // interval.setIndex(intervals.size());
            intervals.add(interval);
        }
    }
}
