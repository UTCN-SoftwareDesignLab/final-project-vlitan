package main.repository;

import main.model.Interval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntervalRepository extends JpaRepository<Interval, Integer> {
}
