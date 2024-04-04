package com.springboot3.sb3hxh.Converter;

import com.springboot3.sb3hxh.Model.TipoNenModel;
import com.springboot3.sb3hxh.Service.TipoNenService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TipoNenConverter implements Converter<String, TipoNenModel>  {

    private final TipoNenService tipoNenService;

    public TipoNenConverter(TipoNenService tipoNenService) {
        this.tipoNenService = tipoNenService;
    }

    @Override
    public TipoNenModel convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        int id = Integer.parseInt(source);
        return tipoNenService.read(id);
    }

}