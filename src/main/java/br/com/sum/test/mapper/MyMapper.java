package br.com.sum.test.mapper;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class MyMapper {

    private static ModelMapper mapper = new ModelMapper();

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {

        List<D> destinationList = new ArrayList<D>();

        for (O o : origin) {
            destinationList.add(mapper.map(o, destination));
        }
        return destinationList;
    }
}

