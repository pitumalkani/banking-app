package com.bankingmanagement.mapper;

import com.bankingmanagement.dto.request.CustomerDTO;
import com.bankingmanagement.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    Customer mapToCutomer(CustomerDTO cusCustomerSaveDto);
    com.bankingmanagement.dto.response.CustomerDTO mapToCustomerDTO(Customer customer);

}
