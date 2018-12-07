package net.nlt.test.service;

public interface CounterService {

    void setCounterValue(Integer value);

    boolean canModifyCounter();

    void saveIfReaching();
}
