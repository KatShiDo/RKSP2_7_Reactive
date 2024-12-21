package mirea.ru.reactive.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table("computers")
public class Computer {
    public Computer(Long id, String cpu, String gpu, Integer ram, Integer storage) {
        this.id = id;
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
        this.storage = storage;
    }

    private Long id;
    private String cpu;
    private String gpu;
    private Integer ram;
    private Integer storage;

    public Integer getRam() {
        return ram;
    }

    public Integer getStorage() {
        return storage;
    }

    public Long getId() {
        return id;
    }

    public String getCpu() {
        return cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }
}
