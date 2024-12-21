package mirea.ru.reactive.controller;

import lombok.RequiredArgsConstructor;
import mirea.ru.reactive.entity.Computer;
import mirea.ru.reactive.exception.CustomException;
import mirea.ru.reactive.repository.ComputerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/computer")
public class ComputerController {

    private final ComputerRepository computerRepository;

    @Autowired
    public ComputerController(ComputerRepository computerRepository) {
        this.computerRepository = computerRepository;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Computer>> getComputerById(@PathVariable Long id) {
        return computerRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<Computer> getAllComputers(@RequestParam(name = "min_ram", required = false) Integer minRam) {
        Flux<Computer> computers = computerRepository.findAll();

        if (minRam != null && minRam > 0) {
            computers = computers.filter(c -> c.getRam() >= minRam);
        }

        return computers
                .map(this::transformComputer)
                .onErrorResume(e -> Flux.error(new CustomException("Failed to fetch computers", e)))
                .onBackpressureBuffer();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Computer> createComputer(@RequestBody Computer computer) {
        return computerRepository.save(computer);
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<Computer>> updateComputer(@PathVariable Long id, @RequestBody Computer computer) {
        return computerRepository
                .findById(id)
                .flatMap(existingComputer -> {
                    existingComputer.setRam(computer.getRam());
                    existingComputer.setCpu(computer.getCpu());
                    existingComputer.setGpu(computer.getGpu());
                    existingComputer.setStorage(computer.getStorage());
                    return computerRepository.save(existingComputer);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteComputer(@PathVariable Long id) {
        return computerRepository
                .findById(id)
                .flatMap(existingComputer ->
                        computerRepository
                                .delete(existingComputer)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private Computer transformComputer(Computer computer) {
        computer.setGpu(computer.getGpu().toUpperCase());
        computer.setCpu(computer.getCpu().toUpperCase());
        return computer;
    }
}
