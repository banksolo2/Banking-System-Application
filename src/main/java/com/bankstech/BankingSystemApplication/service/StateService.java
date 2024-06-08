package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.State;
import com.bankstech.BankingSystemApplication.model.ResponseMessage;
import com.sun.jdi.BooleanValue;

import java.util.List;

public interface StateService {

    public State getById(Long stateId);

    public State getByName(String name);

    public List<State> all();

    public List<State> options(Long stateId);

    public Boolean isNameExist(String name);

    public Boolean isNameExistOnUpdate(Long stateId,String name);

    public ResponseMessage create(State state);

    public ResponseMessage update(State state);

    public ResponseMessage delete(Long stateId);
}
