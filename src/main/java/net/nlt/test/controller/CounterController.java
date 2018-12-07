package net.nlt.test.controller;

import lombok.RequiredArgsConstructor;
import net.nlt.test.domain.RequestInfo;
import net.nlt.test.repository.RequestInfoRepository;
import net.nlt.test.service.CounterService;
import net.nlt.test.service.ThreadService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowCredentials = "false", maxAge = 3600)
public class CounterController {

    private final ThreadService threadService;
    private final CounterService counterService;
    private final RequestInfoRepository requestInfoRepository;

    @PostMapping("/threads")
    @ResponseStatus(HttpStatus.CREATED)
    public void setThreadsValue(@RequestParam Integer producerThreads,
                                @RequestParam Integer consumerThreads,
                                HttpServletRequest request) {

        threadService.createThreads(producerThreads, consumerThreads);

        requestInfoRepository.saveAndFlush(new RequestInfo(request));
    }

    @PutMapping("/counter")
    public void setCounterValue(@RequestParam Integer counterValue) {
        counterService.setCounterValue(counterValue);
    }
}
