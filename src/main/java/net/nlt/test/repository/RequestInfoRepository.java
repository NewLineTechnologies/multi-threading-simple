package net.nlt.test.repository;

import net.nlt.test.domain.RequestInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestInfoRepository extends JpaRepository<RequestInfo, Long> {
}
