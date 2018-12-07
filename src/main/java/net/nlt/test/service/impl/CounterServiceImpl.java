package net.nlt.test.service.impl;

import lombok.RequiredArgsConstructor;
import net.nlt.test.domain.Counter;
import net.nlt.test.domain.CounterInfo;
import net.nlt.test.repository.CounterInfoRepository;
import net.nlt.test.service.CounterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CounterServiceImpl implements CounterService {

    private final Counter counter;
    private final CounterInfoRepository counterInfoRepository;

    @Override
    public void setCounterValue(Integer value) {
        counter.setValue(value);
    }

    @Override
    public  boolean canModifyCounter() {
        return counter.canModify();
    }

    @Override
    @Transactional
    public void saveIfReaching() {
        if (!counter.canModify()) {
            counterInfoRepository.saveAndFlush(new CounterInfo(counter.getLastReachedAt()));
        }
    }
}
