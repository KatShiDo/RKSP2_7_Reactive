package mirea.ru.reactive.controller;

import mirea.ru.reactive.entity.Computer;
import mirea.ru.reactive.generated.ComputerData;
import mirea.ru.reactive.repository.ComputerRepository;
import org.checkerframework.checker.units.qual.C;
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

public class ComputerControllerTests {

    @BeforeEach
    public void setUp() {
        ComputerData.generateComputers();
    }

    @Test
    public void getComputerById_givenId_returnsComputer() {
        Computer computer = ComputerData.getComputer1();

        ComputerRepository computerRepository = Mockito.mock(ComputerRepository.class);
        Mockito.when(computerRepository.findById(1L)).thenReturn(Mono.just(computer));
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
        Mockito.when(computerRepository.findAll()).thenReturn(Flux.just(computer1, computer2));
        ComputerController computerController = new ComputerController(computerRepository);
        Flux<Computer> response = computerController.getAllComputers(null);

        assertEquals(2, response.collectList().block().size());
    }

    @Test
    public void createComputer_givenComputer_savesComputer() {
        Computer computer = ComputerData.getComputer1();

        ComputerRepository computerRepository = Mockito.mock(ComputerRepository.class);
        Mockito.when(computerRepository.save(computer)).thenReturn(Mono.just(computer));
        ComputerController computerController = new ComputerController(computerRepository);
        Mono<Computer> response = computerController.createComputer(computer);

        assertEquals(computer, response.block());
    }

    @Test
    public void updateComputer_givenComputer_updatesComputer() {
        Computer computer = ComputerData.getComputer1();
        Computer computerUpdated = new Computer(computer.getId(), "new_cpu", "new_gpu", computer.getRam(), computer.getStorage());

        ComputerRepository computerRepository = Mockito.mock(ComputerRepository.class);
        Mockito.when(computerRepository.findById(1L)).thenReturn(Mono.just(computer));
        Mockito.when(computerRepository.save(computer)).thenReturn(Mono.just(computerUpdated));
        ComputerController computerController = new ComputerController(computerRepository);
        ResponseEntity<Computer> response = computerController.updateComputer(1L, computerUpdated).block();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(computerUpdated, response.getBody());
    }

    @Test
    public void deleteComputer_givenId_deletesComputer() {
        Computer computer = ComputerData.getComputer1();

        ComputerRepository computerRepository = Mockito.mock(ComputerRepository.class);
        Mockito.when(computerRepository.findById(1L)).thenReturn(Mono.just(computer));
        Mockito.when(computerRepository.delete(computer)).thenReturn(Mono.empty());
        ComputerController computerController = new ComputerController(computerRepository);
        ResponseEntity<Void> response = computerController.deleteComputer(1L).block();

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
