package com.tharani.githubingestorservice.mapper;


import com.tharani.githubingestorservice.dto.CommitDTO;
import com.tharani.githubingestorservice.repository.model.JpaDeveloperCommit;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommitMapper {

    JpaDeveloperCommit map(CommitDTO commitDTO);

    List<JpaDeveloperCommit> map(List<CommitDTO> commitDTOS);
}
