package main.repository;

import main.model.Interval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntervalRepository extends JpaRepository<Interval, Integer> {
    List<Interval> findAllBySequence_Id(Integer id);
}
