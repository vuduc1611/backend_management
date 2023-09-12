package net.javaguides.springboot.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column (name = "createdAt")
    private String createdAt;

    @Column (name = "completedAt")
    private String completedAt;

    @Column (name = "members")
    private String membersId;

    @Column (name = "ProjectManager")
    private Integer idPM;

    @Column (name = "value")
    private Integer value;
}
