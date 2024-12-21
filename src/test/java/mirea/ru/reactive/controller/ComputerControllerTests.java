package mirea.ru.reactive.controller;

import mirea.ru.reactive.entity.Computer;
import mirea.ru.reactive.generated.ComputerData;
import mirea.ru.reactive.repository.ComputerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
public class ComputerControllerTests {

    @BeforeEach
    public void setUp() {
        ComputerData.generateComputers();
    }

    @Test
    public void getComputerById_givenId_returnsComputer() {
        Computer computer = ComputerData.getComputer1();

        ComputerRepository computerRepository = Mockito.mock(ComputerRepository.class);
        when(computerRepository.findById(1L)).thenReturn(Mono.just(computer));

        ComputerController computerController = new ComputerController(computerRepository);
        ResponseEntity<Computer> response = computerController.getComputerById(1L).block();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(computer, response.getBody());
    }

    @Test
    public void getAllComputers_givenNothing_returnsAllComputers() {
        Computer computer1 = ComputerData.getComputer1();
        Computer computer2 = ComputerData.getComputer2();

        ComputerRepository computerRepository = Mockito.mock(ComputerRepository.class);
        when(computerRepository.findAll()).thenReturn(Flux.just(computer1, computer2));
        ComputerController computerController = new ComputerController(computerRepository);

        Flux<Computer> response = computerController.getAllComputers(null);

        assertEquals(2, response.collectList().block().size());
    }
}
