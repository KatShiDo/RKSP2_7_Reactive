package mirea.ru.reactive.repository;

import mirea.ru.reactive.entity.Computer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ComputerRepository extends R2dbcRepository<Computer,Long> {
}
