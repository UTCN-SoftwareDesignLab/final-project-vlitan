package main.repository;

import main.model.Sequence;
import org.hibernate.boot.model.source.spi.JpaCallbackSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SequenceRepository extends JpaRepository<Sequence, Integer> {
}
