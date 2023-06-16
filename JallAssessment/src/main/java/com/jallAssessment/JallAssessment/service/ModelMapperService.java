package com.jallAssessment.JallAssessment.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelMapperService {
    @Autowired
    private ModelMapper modelMapper;

    public <T> T mapper(Object obj, Class<T> contentClass){
        return modelMapper.map(obj, contentClass);
    }

    <T> List<T> collectionMapper(List<Object> list, Class<T> contentClass){
        return list.stream().map(obj -> mapper(obj, contentClass)).collect(Collectors.toList());
    }

}

