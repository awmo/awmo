package models;
// Generated Aug 25, 2012 5:00:02 PM by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * RelacaoClasse generated by hbm2java
 */
@Entity
@Table(name="relacao_classe"
    ,catalog="awmo"
)
public class RelacaoClasse  implements java.io.Serializable {


     private Long id;
     private Classe classeByTargetClasseId;
     private Classe classeBySourceClasseId;
     private String relation;
     private String sourceCardinality;
     private String targetCardinality;

    public RelacaoClasse() {
    }

	
    public RelacaoClasse(Classe classeByTargetClasseId, Classe classeBySourceClasseId, String relation) {
        this.classeByTargetClasseId = classeByTargetClasseId;
        this.classeBySourceClasseId = classeBySourceClasseId;
        this.relation = relation;
    }
    public RelacaoClasse(Classe classeByTargetClasseId, Classe classeBySourceClasseId, String relation, String sourceCardinality, String targetCardinality) {
       this.classeByTargetClasseId = classeByTargetClasseId;
       this.classeBySourceClasseId = classeBySourceClasseId;
       this.relation = relation;
       this.sourceCardinality = sourceCardinality;
       this.targetCardinality = targetCardinality;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="target_classe_id", nullable=false)
    public Classe getClasseByTargetClasseId() {
        return this.classeByTargetClasseId;
    }
    
    public void setClasseByTargetClasseId(Classe classeByTargetClasseId) {
        this.classeByTargetClasseId = classeByTargetClasseId;
    }
@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="source_classe_id", nullable=false)
    public Classe getClasseBySourceClasseId() {
        return this.classeBySourceClasseId;
    }
    
    public void setClasseBySourceClasseId(Classe classeBySourceClasseId) {
        this.classeBySourceClasseId = classeBySourceClasseId;
    }
    
    @Column(name="relation", nullable=false, length=50)
    public String getRelation() {
        return this.relation;
    }
    
    public void setRelation(String relation) {
        this.relation = relation;
    }
    
    @Column(name="source_cardinality", length=10)
    public String getSourceCardinality() {
        return this.sourceCardinality;
    }
    
    public void setSourceCardinality(String sourceCardinality) {
        this.sourceCardinality = sourceCardinality;
    }
    
    @Column(name="target_cardinality", length=10)
    public String getTargetCardinality() {
        return this.targetCardinality;
    }
    
    public void setTargetCardinality(String targetCardinality) {
        this.targetCardinality = targetCardinality;
    }




}

