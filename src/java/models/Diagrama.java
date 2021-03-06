package models;
// Generated Aug 25, 2012 5:00:02 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Diagrama generated by hbm2java
 */
@Entity
@Table(name="diagrama"
    ,catalog="awmo"
)
public class Diagrama  implements java.io.Serializable {


     private Long id;
     private String name;
     private Set<Classe> classes = new HashSet<Classe>(0);

    public Diagrama() {
    }

	
    public Diagrama(String name) {
        this.name = name;
    }
    public Diagrama(String name, Set<Classe> classes) {
       this.name = name;
       this.classes = classes;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="name", nullable=false, length=100)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
@OneToMany(cascade= CascadeType.REFRESH, fetch=FetchType.EAGER, mappedBy="diagrama")
@OrderBy("id")
    public Set<Classe> getClasses() {
        return this.classes;
    }
    
    public void setClasses(Set<Classe> classes) {
        this.classes = classes;
    }




}


