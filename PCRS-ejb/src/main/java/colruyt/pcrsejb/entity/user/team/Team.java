package colruyt.pcrsejb.entity.user.team;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import colruyt.pcrsejb.bo.user.team.TeamBo;
import colruyt.pcrsejb.entity.AbstractEntity;

@Entity
@Table(name = "TEAMS")
@NamedQueries({ 
	
	@NamedQuery(name = "TEAM.GETALL", query = "SELECT t FROM Team t"),
	@NamedQuery(name = "TEAM.GETTEAMFORUSER", query = "select t From Team t, User u join t.enrolments e where e.active = :isActive and u = :member"),
	@NamedQuery(name = "TEAM.GETTEAMSOFMANAGER", query = "select t, e, u from Team t, Enrolment e, User u where e.active = true and e.userPrivilege.privilegeType = :privilegeType and u = :teamManager"),
	@NamedQuery(name = "TEAM.GETMANAGEROFTEAM", query = "select t, e, u from Team t, Enrolment e, User u where e.userPrivilege.active = true and e.userPrivilege = :userPrivilege and t = :team"),
	@NamedQuery(name = "TEAM.GETUSERSOFTEAM", query = "select u from User u join u.privileges p where p.active = true and p IN :team")
})
public class Team extends AbstractEntity implements Serializable, Comparable<Team> {
	/*
	 * PROPERTIES
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAMS_SEQ")
	@SequenceGenerator(sequenceName = "TEAMS_SEQ", allocationSize = 1, name = "TEAMS_SEQ")
	@Column(name = "ID")
	private Integer id;
	private String name;
	@OneToMany(cascade= {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
	@JoinColumn(name = "TEAM_ID")
	private Set<Enrolment> enrolments = new HashSet<>();
	/*
	 * CONSTRUCTORS
	 */
	public Team() {
		super();
	}
	public Team(String name, Set<Enrolment> enrolments) {
		super();
		this.name = name;
		this.enrolments = enrolments;
	}
	public Team(Integer id, String name, Set<Enrolment> enrolments) {
		super();
		this.id = id;
		this.name = name;
		this.enrolments = enrolments;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Enrolment> getEnrolments() {
		return enrolments;
	}
	public void setEnrolments(Set<Enrolment> enrolments) {
		this.enrolments = enrolments;
	}
	@Override
	public int compareTo(Team team) {
		if(this.id == team.id) {
			return 0;
		}else {
			return -1;
		}
	}
}
