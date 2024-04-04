package com.springboot3.sb3hxh.Converter;

import com.springboot3.sb3hxh.Model.TipoHunterModel;
import com.springboot3.sb3hxh.Service.TipoHunterService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TipoHunterConverter implements Converter<String, TipoHunterModel> {

    private final TipoHunterService tipoHunterService;

    public TipoHunterConverter(TipoHunterService tipoHunterService) {
        this.tipoHunterService = tipoHunterService;
    }

    @Override
    public TipoHunterModel convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        int id = Integer.parseInt(source);
        return tipoHunterService.read(id);
    }

}