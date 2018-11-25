package colruyt.pcrsejb.entity.surveys.rating;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import colruyt.pcrsejb.entity.AbstractEntity;
import colruyt.pcrsejb.entity.competence.Competence;

@Entity
@Table(name="RATINGS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="RATINGTYPE")
@DiscriminatorValue(value="REGULAR")
public class Rating extends AbstractEntity implements Serializable {
	/*
	 * PROPERTIES
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RATINGSS_SEQ")
    @SequenceGenerator(sequenceName = "RATINGSS_SEQ", allocationSize = 1, name = "RATINGSS_SEQ")
	@Column(name="ID")
	private Integer id;
	@Column(name="RATING_LEVEL")
    private Integer level;
    private Boolean energy;
    @ManyToOne
    private Competence competence;
    /*
     * CONSTRUCTORS
     */
    public Rating(){
    	super();
    }
    public Rating(Integer level, Boolean energy, Competence competence){
        setLevel(level);
        setEnergy(energy);
        setCompetence(competence);
    }   
	public Rating(Integer id, Integer level, Boolean energy, Competence competence) {
		super();
		this.id = id;
		this.level = level;
		this.energy = energy;
		this.competence = competence;
	}
	/*
	 * GETTERS AND SETTERS
	 */
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Boolean getEnergy() {
		return energy;
	}
	public void setEnergy(Boolean energy) {
		this.energy = energy;
	}
	public Competence getCompetence() {
		return competence;
	}
	public void setCompetence(Competence competence) {
		this.competence = competence;
	}
}
