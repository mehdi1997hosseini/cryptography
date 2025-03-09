package ir.smarttrustco.cryptography.basic;

import java.util.List;

public interface BaseMapper<E, D> {
    E toEntity(D d);
    List<E> toEntity(List<D> ds);
    D toDto(E e);
    List<D> toDto(List<E> es);
}
