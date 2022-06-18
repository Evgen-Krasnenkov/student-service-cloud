package com.buzz.service;

import com.buzz.dto.AddressResponse;
import com.buzz.feignclient.AddressFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CommonService {
    Logger logger = LoggerFactory.getLogger(CommonService.class);
    int count;
    AddressFeignClient addressFeignClient;

    public CommonService(AddressFeignClient addressFeignClient) {
        this.addressFeignClient = addressFeignClient;
    }

    @CircuitBreaker(name = "addressService", fallbackMethod = "fallBackGetAddressByStudentId")
    protected AddressResponse getAddressByStudentId(Long addressId) {
        logger.info("count = {}", count);
        count++;
        return addressFeignClient.getById(addressId);
    }

    protected AddressResponse fallBackGetAddressByStudentId(Long addressId, Throwable throwable) {
        logger.error("Fallback Method worked. Message : {}, Cause : {} ", throwable.getMessage(), throwable.getCause());
        return new AddressResponse();
    }
}
