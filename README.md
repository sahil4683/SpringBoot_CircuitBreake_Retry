
ALL Actuator : http://localhost:8080/actuator
-----------------------------------------------------------------------------

API : http://localhost:8080/Retry
Retry Instances : http://localhost:8080/actuator/retries
Retry Events : http://localhost:8080/actuator/retryevents
-----------------------------------------------------------------------------

API : http://localhost:8080/CircuitBreaker
CircuitBreakers : http://localhost:8080/actuator/circuitbreakers
CircuitBreakers Events : http://localhost:8080/actuator/circuitbreakerevents
-----------------------------------------------------------------------------







Base Configuration (configs.default)
registerHealthIndicator: true

Enables health indicators for the circuit breaker.
The health status will be available through Spring Boot Actuator endpoints (e.g., /actuator/health).
failure-rate-threshold: 50

The percentage of failed calls (out of total calls in the sliding window) that will trip the circuit breaker to the Open state.
In this case, if 50% or more calls fail, the circuit breaker will open.
slow-call-rate-threshold: 50

Specifies the percentage of slow calls (as defined by slow-call-duration-threshold) that will also contribute to tripping the circuit breaker.
If 50% of calls are slow, the circuit breaker opens.
slow-call-duration-threshold: 2s

Defines the threshold for a "slow" call.
Calls taking longer than 2 seconds will be counted as slow.
permitted-number-of-calls-in-half-open-state: 2

When the circuit breaker transitions from Open to Half-Open, it allows a limited number of test calls (2 in this case) to determine if the system has recovered.
Based on the result of these calls, the circuit breaker either transitions to Closed or back to Open.
sliding-window-type: COUNT_BASED

Specifies how the circuit breaker calculates its statistics:
COUNT_BASED: Uses a fixed number of calls (defined by sliding-window-size) for evaluation.
TIME_BASED: Uses a time duration to collect call metrics.
sliding-window-size: 100

Defines the size of the sliding window when COUNT_BASED.
Here, the circuit breaker evaluates the last 100 calls for failure and slow-call thresholds.
minimum-number-of-calls: 3

The minimum number of calls required in the sliding window before the circuit breaker starts evaluating failure or slow-call thresholds.
Prevents premature circuit breaker actions when there are insufficient calls.
wait-duration-in-open-state: 10s

The time the circuit breaker remains in the Open state before transitioning to Half-Open.
During this period, all calls fail immediately without attempting execution.
Instance-Specific Configuration (instances.studentService)
base-config: default

Specifies that the studentService instance inherits settings from the default base configuration.
Allows customizations for specific instances without repeating the entire configuration.
failure-rate-threshold: 40

Overrides the failure-rate-threshold for studentService.
The circuit breaker for this service will trip when 40% of calls in the sliding window fail, instead of 50%.
wait-duration-in-open-state: 10s

Retains the default wait duration of 10 seconds for studentService before transitioning from Open to Half-Open.






-------------------------------------------



Configuration Breakdown
1. resilience4j.retry.instances.default
This defines the default retry configuration to be applied. You can reference this configuration for specific services or apply it globally.
2. maxAttempts: 3
Specifies the maximum number of attempts (including the initial attempt) to execute a failed call.
In this case, Resilience4j will try a maximum of 3 times:
1 initial call.
2 retry attempts if the initial attempt fails.
3. waitDuration: 10s
The duration between retry attempts.
In this case, Resilience4j waits for 10 seconds before attempting another retry.
4. enableExponentialBackoff: true
Enables exponential backoff for retry attempts.
With exponential backoff, the wait duration increases exponentially between retry attempts.
5. exponentialBackoffMultiplier: 2
The multiplier used for exponential backoff.
In this case, the wait duration doubles with each retry attempt:
1st attempt: Initial call (no delay).
2nd attempt: Wait 10 seconds (as specified by waitDuration).
3rd attempt: Wait 20 seconds (10 × 2).
Retry Behavior Summary
When a call fails:

The initial attempt is made.
If the call fails:
Retry 1: Wait for 10 seconds and retry.
If the call fails again:
Retry 2: Wait for 20 seconds (due to exponential backoff) and retry.
If the call fails a third time:
Stop retries (since maxAttempts is 3).
Propagate the failure.
