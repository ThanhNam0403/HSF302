package sum25.hsf302.sedo.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pc_builds")
@Getter
@Setter
public class PCBuild {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "cpu_id")
    private ComputerDevice cpu;

    @ManyToOne
    @JoinColumn(name = "mainboard_id")
    private ComputerDevice mainboard;

    @ManyToOne
    @JoinColumn(name = "ram_id")
    private ComputerDevice ram;

    @ManyToOne
    @JoinColumn(name = "gpu_id")
    private ComputerDevice gpu;

    @ManyToOne
    @JoinColumn(name = "ssd_id")
    private ComputerDevice ssd;

    @ManyToOne
    @JoinColumn(name = "hdd_id")
    private ComputerDevice hdd;

    @ManyToOne
    @JoinColumn(name = "psu_id")
    private ComputerDevice psu;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private ComputerDevice pcCase;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne @JoinColumn(name = "cooling_id")
    private ComputerDevice cooling;

    @ManyToOne @JoinColumn(name = "monitor_id")
    private ComputerDevice monitor;

    @ManyToOne @JoinColumn(name = "keyboard_id")
    private ComputerDevice keyboard;

    @ManyToOne @JoinColumn(name = "mouse_id")
    private ComputerDevice mouse;

    @ManyToOne @JoinColumn(name = "headphones_id")
    private ComputerDevice headphones;

    @ManyToOne @JoinColumn(name = "speakers_id")
    private ComputerDevice speakers;



}
