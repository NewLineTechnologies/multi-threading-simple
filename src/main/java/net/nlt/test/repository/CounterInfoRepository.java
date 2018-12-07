package net.nlt.test.repository;

import net.nlt.test.domain.CounterInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CounterInfoRepository extends JpaRepository<CounterInfo, LocalDateTime> {
}
