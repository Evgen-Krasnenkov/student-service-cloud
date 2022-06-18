package com.buzz.feignclient;

import com.buzz.dto.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "api-gateway")
public interface AddressFeignClient {
    @GetMapping("address-service/api/address/getById/{id}")
    public AddressResponse getById(@PathVariable long id);
}
