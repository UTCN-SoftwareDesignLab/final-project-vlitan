package main.repository;

import main.model.Sequence;
import org.hibernate.boot.model.source.spi.JpaCallbackSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SequenceRepository extends JpaRepository<Sequence, Integer> {
    List<Sequence> findAllByUserEmail(String email);
    Optional<Sequence> findByUserIdAndEnabled(Integer id, boolean enabled);
}
