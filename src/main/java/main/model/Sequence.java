package main.model;

import lombok.*;

import javax.annotation.Nonnegative;
import javax.persistence.*;
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sequences")
public class Sequence extends Observable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    @Nonnegative
    private int currentIntervalIndex;

    @Column
    private boolean enabled;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
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


    public void iterateByAmount(Integer amount){
        Interval interval = this.getIntervals().get(this.getCurrentIntervalIndex());
        interval.setCurrentMoment(interval.getCurrentMoment() + amount);
        if (interval.hasCompleted()){
            this.getIntervals().get(this.getCurrentIntervalIndex()).setCurrentMoment(0);
            this.setCurrentIntervalIndex(this.getCurrentIntervalIndex() + 1);
        }
        this.setChanged();
        this.notifyObservers();
    }

    public boolean hasCompleted(){
        return (currentIntervalIndex >= intervals.size());
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        else{
            return super.hashCode();
        }
    }

    @Override
    public boolean equals(Object o){
        if (o == null) return false;
        if (this == o) return true;
        if(o instanceof Sequence){
            Sequence toCompare = (Sequence) o;
            return this.getId().equals(toCompare.getId());
        }
        return false;
    }
}
