package com.bank.account.kata.infra.mapper;

/**
 * The interface Generic object mapper.
 *
 * @param <T> the type parameter
 * @param <U> the type parameter
 */
public interface GenericObjectMapper<T, U> {

    /**
     * Map to target object.
     *
     * @param source the source object
     * @return target u
     */
    U convert(T source);

}