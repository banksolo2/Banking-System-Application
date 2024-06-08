package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.State;
import com.bankstech.BankingSystemApplication.model.ResponseMessage;
import com.bankstech.BankingSystemApplication.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class StateServiceImpl implements StateService {
    @Autowired
    private StateRepository stateRepository;

    @Override
    public State getById(Long stateId) {
        return stateRepository.findById(stateId).orElse(new State());
    }

    @Override
    public State getByName(String name) {
        return stateRepository.findByNameIgnoreCase(name).orElse(new State());
    }

    @Override
    public List<State> all() {
        return stateRepository.findAll();
    }

    @Override
    public List<State> options(Long stateId) {
        return stateRepository.options(stateId);
    }

    @Override
    public Boolean isNameExist(String name) {
        return stateRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public Boolean isNameExistOnUpdate(Long stateId, String name) {
        return stateRepository.existsByNameOnUpdate(stateId,name);
    }

    @Override
    public ResponseMessage create(State state) {
        if(Objects.isNull(state.getName()) || state.getName().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Name field required")
                    .build();
        }
        Boolean isNameExist = isNameExist(state.getName());
        if(isNameExist){
            return ResponseMessage.builder()
                    .type("error")
                    .message("State name "+state.getName()+" already exist")
                    .build();
        }
        state.setCode(state.getName().replace(" ","_").toUpperCase());
        stateRepository.save(state);

        return ResponseMessage.builder()
                .type("success")
                .message("State details created")
                .build();
    }

    @Override
    public ResponseMessage update(State state) {
        if(Objects.isNull(state.getName()) || state.getName().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Name field required")
                    .build();
        }
        Boolean isNameExist = stateRepository.existsByNameOnUpdate(state.getStateId(),state.getName());
        if(isNameExist){
            return ResponseMessage.builder()
                    .type("error")
                    .message("State name "+state.getName()+" already exist")
                    .build();
        }
        state.setCode(state.getName().replace(" ","_").toUpperCase());
        stateRepository.save(state);

        return ResponseMessage.builder()
                .type("success")
                .message("State details updated")
                .build();
    }

    @Override
    public ResponseMessage delete(Long stateId) {
        stateRepository.deleteById(stateId);
        return ResponseMessage.builder()
                .type("success")
                .message("State details deleted")
                .build();
    }
}
