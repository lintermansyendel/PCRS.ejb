package colruyt.pcrsejb.service.bl.user.team;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import colruyt.pcrsejb.entity.user.User;
import colruyt.pcrsejb.entity.user.privilege.PrivilegeType;
import colruyt.pcrsejb.entity.user.privilege.UserPrivilege;
import colruyt.pcrsejb.entity.user.team.Enrolment;
import colruyt.pcrsejb.entity.user.team.Team;
import colruyt.pcrsejb.service.bl.user.IUserServiceBl;
import colruyt.pcrsejb.service.bl.user.privilege.IUserPrivilegeServiceBl;
import colruyt.pcrsejb.service.dl.user.team.ITeamServiceDl;
import colruyt.pcrsejb.util.exceptions.MemberAlreadyHasATeamException;
import colruyt.pcrsejb.util.exceptions.TeamHasNoManagerException;
import colruyt.pcrsejb.util.exceptions.UserIsNotMemberOfTeamException;
import colruyt.pcrsejb.util.exceptions.validation.Team.TeamAlreadyExistsExeption;
import colruyt.pcrsejb.util.exceptions.validation.Team.TeamDoesNotExistExeption;
import colruyt.pcrsejb.util.exceptions.validation.Team.TeamEmptyValidation;
import colruyt.pcrsejb.util.exceptions.validation.Team.TeamhasAManagerException;
import colruyt.pcrsejb.util.exceptions.validations.ValidationException;

@Stateless
public class TeamServiceBl implements Serializable,ITeamServiceBl {

	/**
	 * 
	 */
	@EJB
	private ITeamServiceDl dlService;
	@EJB
	private IUserPrivilegeServiceBl userPrivilegeServiceBl;
	@EJB
	private IEnrolmentServiceBl enrolmentServiceBl;
	@EJB
	private IUserServiceBl userServiceBl;
	
	
	private static final long serialVersionUID = 1L;

	/*public User getManagerOfUser(User u) {
		return null;
	}*/

	@Override
	public Team save(Team element) throws ValidationException{
		if (element.getId() == null) {
			for (Team team : this.getAll()){
	            if(element.getName().equals(team.getName())){
	                throw new TeamAlreadyExistsExeption("The team already exists");
	            }
	        }
	        if(element.getName().equals("")){
	        	throw new TeamEmptyValidation("Team name is empty");
			}
		}
		return this.dlService.save(element);
	}

	@Override
	public Team get(Team element) throws ValidationException{
		Team t = null;
		t = this.dlService.get(element);
		if (t == null){
			throw new  TeamDoesNotExistExeption("Team Does not exist");
		}
		return t;
	}

	@Override
	public List<Team> getAll() {
		return this.dlService.getAll();
	}

	@Override
	public void delete(Team element) throws ValidationException {
		 this.dlService.delete(element);
	}

	@Override
	public User getManagerForUser(User user) throws ValidationException {
		return this.dlService.getManagerForUser(user);
	}

	@Override
	public Team getTeamForUser(User user) throws ValidationException{
		Team t = this.dlService.getTeamForUser(user);
		if (t == null) {
			throw new UserIsNotMemberOfTeamException();
		} 
		return t;
	}

	@Override
	public List<Team> getTeamsOfManager(User manager) {
		return this.dlService.getTeamsOfManager(manager);
	}

	
	public void removeUserFromTeam(Team team, Enrolment enrolment, User user) throws ValidationException {		
		//remove teamEnrolment
		enrolmentServiceBl.delete(enrolment);
		
		//set userPrivilege to unactive
		for(UserPrivilege p : user.getPrivileges()) {
			if(enrolment.getUserPrivilege().getPrivilegeType().equals(p.getPrivilegeType())) {
				p.setActive(false);
			}
		}
		userServiceBl.save(user);
	}

	@Override
	public Enrolment addUserToTeam(Team team, User user, String userPrivilege) throws ValidationException {
		UserPrivilege enrolmentUserPrivilege = userPrivilegeServiceBl.setUserPrivilege(user, userPrivilege);
		
		User manager = null;
		Enrolment enrolment = null;
		if (userPrivilege.equalsIgnoreCase(PrivilegeType.TEAMMANAGER.getShortName())) {
			 try {
				manager = dlService.getManagerOfTeam(team);
			} catch (TeamHasNoManagerException e) {
				
			}
			
			 
		}
		
		 if(manager != null) {
				throw new TeamhasAManagerException();
				
		 }
		
		
		
		enrolment = new Enrolment();
    	enrolment.setUserPrivilege(enrolmentUserPrivilege);
    	enrolment.setActive(true);
    	
    	team.getEnrolments().add(enrolment);
    	
    	save(team);
		return enrolment;
	} 

	@Override
	public List<User> getUsersOfTeam(Team team) {
	
		return this.dlService.getUsersOfTeam(team);
	}
	
	
}
