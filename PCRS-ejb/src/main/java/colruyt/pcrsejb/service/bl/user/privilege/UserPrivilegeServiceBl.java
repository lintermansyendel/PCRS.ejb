package colruyt.pcrsejb.service.bl.user.privilege;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import colruyt.pcrsejb.entity.surveyDefinition.survey.SurveyDefinition;
import colruyt.pcrsejb.entity.user.User;
import colruyt.pcrsejb.entity.user.privilege.PrivilegeType;
import colruyt.pcrsejb.entity.user.privilege.SurveyUserPrivilege;
import colruyt.pcrsejb.entity.user.privilege.TeamMemberUserPrivilege;
import colruyt.pcrsejb.entity.user.privilege.UserPrivilege;
import colruyt.pcrsejb.service.bl.user.IUserServiceBl;
import colruyt.pcrsejb.service.dl.user.privilege.IUserPrivilegeServiceDl;
import colruyt.pcrsejb.util.exceptions.MemberAlreadyHasATeamException;

@Stateless
public class UserPrivilegeServiceBl implements Serializable, IUserPrivilegeServiceBl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	IUserPrivilegeServiceDl userPrivilegeServiceDl;
	@EJB
	IUserServiceBl userServiceBl;

	@Override
	public UserPrivilege save(UserPrivilege element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserPrivilege get(UserPrivilege element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserPrivilege> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(UserPrivilege element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User grantUserPrivilegeToUser(User user, UserPrivilege userPrivilege) {
		user.getPrivileges().add(userPrivilege);
		user = userServiceBl.save(user);
		return user;
	}

	@Override
	public void revokeUserPrivilegeToUser(User user, UserPrivilege userPrivilege) {
		UserPrivilege u = null;
		for(UserPrivilege up : user.getPrivileges()) {
			if(up.equals(userPrivilege)) {
				if (userPrivilege.getPrivilegeType().equals(PrivilegeType.TEAMMEMBER)) {
					up.setActive(false);
				}
				else {
					u = up;
				}
			}
		}
		if (u != null) {
			user.getPrivileges().remove(u);
		}
		userServiceBl.save(user);
	}

	@Override
	public UserPrivilege setUserPrivilege(User user, String userPrivilege) throws MemberAlreadyHasATeamException {
		UserPrivilege privilege = null;
		SurveyDefinition surveyDefinition = null;
		
		if(PrivilegeType.TEAMMEMBER.getShortName().equals(userPrivilege)) {
    		List<TeamMemberUserPrivilege> memberPrivs = new ArrayList<>();
    			for (UserPrivilege p : user.getPrivileges()) {
        			if (p.getPrivilegeType().equals(PrivilegeType.TEAMMEMBER)) {
        				if (p.isActive()) {
        					//TODO FACESMESSAGE TOEVOEGEN
        					throw new MemberAlreadyHasATeamException();
        				}
        				memberPrivs.add((TeamMemberUserPrivilege) p);
        			}
        		}
    			for (TeamMemberUserPrivilege p : memberPrivs) {
    				if (p.getSurveyDefinition().getName().equalsIgnoreCase(surveyDefinition.getName())) {
    					privilege = p;
    				}
    			}	
    			if (privilege == null) {
    				privilege = new TeamMemberUserPrivilege();
    	    		privilege.setPrivilegeType(PrivilegeType.TEAMMEMBER);
    	    		//((TeamMemberUserPrivilegeBo) privilege).setStartDateCurrentSurveyDefinition(LocalDate.now());
    	    		((TeamMemberUserPrivilege) privilege).setSurveyDefinition(surveyDefinition);
    			}   	    		
        	}else {    		
    			for (UserPrivilege p : user.getPrivileges()) {
        			if (p.getPrivilegeType().equals(PrivilegeType.TEAMMANAGER)) {
        				privilege = p;
        			}
        		}
        		if (privilege == null) {
        			privilege = new UserPrivilege();
        			privilege.setPrivilegeType(PrivilegeType.TEAMMANAGER);
        		}
        	}
		privilege.setActive(true);
		UserPrivilege newUserPrivilege = null;
		for(UserPrivilege up : user.getPrivileges()) {
			if(up.getPrivilegeType().equals(newUserPrivilege.getPrivilegeType())) {
				//ALS HET TEAMMANAGERPRIVILEGE IS OF MEMBERPRIVILEGE VOOR GESELECTEERDE FUNCTIE
				if (up.getPrivilegeType().equals(PrivilegeType.TEAMMANAGER) ||
						( 
								up.getPrivilegeType().equals(PrivilegeType.TEAMMEMBER) 
								&& ((TeamMemberUserPrivilege)up).getSurveyDefinition().getId() == ((TeamMemberUserPrivilege)newUserPrivilege).getSurveyDefinition().getId())) {
					newUserPrivilege = up;
				}
				
			}
		}
		return newUserPrivilege;
	}

	@Override
	public void revokeUserPrivilegeTypeToUser(User user, PrivilegeType privilegeType, SurveyDefinition surveyDefinition) {
		for(UserPrivilege up : user.getPrivileges()) {
			if((up.getPrivilegeType().equals(privilegeType))) {
				if ((surveyDefinition == null || surveyDefinition.getId() == ((SurveyUserPrivilege)up).getId())) {
					this.revokeUserPrivilegeToUser(user, up);
				}
			}
		}
	}
	
}
