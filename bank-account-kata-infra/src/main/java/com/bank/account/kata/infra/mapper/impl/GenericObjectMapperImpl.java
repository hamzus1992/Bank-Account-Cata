package com.bank.account.kata.infra.mapper.impl;

import com.bank.account.kata.infra.mapper.GenericObjectMapper;
import com.github.dozermapper.core.Mapper;
import lombok.extern.slf4j.Slf4j;

/**
 * A {@link GenericObjectMapper} service implementation.
 *
 * @param <T> the type parameter
 * @param <U> the type parameter
 */
@Slf4j
public class GenericObjectMapperImpl<T, U> implements GenericObjectMapper<T, U> {

    private final Mapper mapper;
    private final Class<U> targetType;

    /**
     * Instantiates a new Generic object mapper.
     *
     * @param mapper     the mapper
     * @param targetType the target type
     */
    public GenericObjectMapperImpl(Mapper mapper, Class<U> targetType) {
        this.mapper = mapper;
        this.targetType = targetType;
    }

    public U convert(T source) {
        try {
            if (source != null) {
                return mapper.map(source, targetType);
            }
        } catch (Exception e) {
            log.debug("Failed to map business client object for item id {}",
                    source, e);
        }
        return null;
    }

}
