package com.bankingmanagement.mapper;

import com.bankingmanagement.dto.response.AccountDTO;
import com.bankingmanagement.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDTO mapToAccountDTO(Account account);

}
